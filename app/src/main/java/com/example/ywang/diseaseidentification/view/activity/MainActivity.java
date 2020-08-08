package com.example.ywang.diseaseidentification.view.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.aip.imageclassify.AipImageClassify;
import com.example.ywang.diseaseidentification.application.MyApplication;
import com.example.ywang.diseaseidentification.bean.weatherData.DailyWeather;
import com.example.ywang.diseaseidentification.bean.weatherData.Weather;
import com.example.ywang.diseaseidentification.bean.weatherData.WeatherBean;
import com.example.ywang.diseaseidentification.utils.Classifier;
import com.example.ywang.diseaseidentification.utils.Utils;
import com.example.ywang.diseaseidentification.utils.network.HttpUtil;
import com.example.ywang.diseaseidentification.view.MiuiWeatherView;
import com.example.ywang.diseaseidentification.view.fragment.AgricultureNewsFragment;
import com.example.ywang.diseaseidentification.view.fragment.FourthFragment;
import com.example.ywang.diseaseidentification.view.fragment.MainFragment;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.view.fragment.DiseaseMapFragment;
import com.example.ywang.diseaseidentification.view.KickBackAnimator;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;
import com.next.easynavigation.constant.Anim;
import com.next.easynavigation.utils.NavigationUtil;
import com.next.easynavigation.view.EasyNavigationBar;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener, View.OnClickListener {

    private String[] tabText = {"首页", "地图", "", "农讯", "农业圈"};

    private int[] normalIcon = {R.mipmap.index, R.mipmap.find,
            R.mipmap.add_image, R.mipmap.message, R.mipmap.me};
    //选中时icon
    private int[] selectIcon = {R.mipmap.index1, R.mipmap.find1,
            R.mipmap.add_image, R.mipmap.message1, R.mipmap.me1};

    //fragment列表
    private List<Fragment> fragments = new ArrayList<>();
    //底部导航栏
    private EasyNavigationBar navigationBar;

    //弹出窗包含view
    private LinearLayout menuLayout;
    private View cancelImageView;

    //弹出窗口图片和文字集合
    private int[] menuItems = {R.mipmap.menu_take_pic, R.mipmap.menu_select_pic};
    private String[] menuTextItems = {"拍照", "相册"};
    private Handler mHandler = new Handler();

    private static final int PERMISSION_CODE = 100;
    private static final int TAKE_PICTURE = 1;
    private static final int SELECT_PICTURE = 2;
    private static final int DYNAMIC = 222;
    private File outputImage;
    private FlowingDrawer mDrawer;  //侧滑栏控件
    private ImageView mMenu, mBack, addBtn, searchBtn;
    private CircleImageView avatar;
    private List<LocalMedia> selectList = new ArrayList<>();
    private MiuiWeatherView weatherView;
    private SwipeRefreshLayout mainRefresh;
    List<WeatherBean> datas = new ArrayList<>();
    private List<DailyWeather> dailyWeatherList;
    private LinearLayout forecastLayout;
    private Classifier classifier;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationBar = findViewById(R.id.navigationBar);
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragments.add(new MainFragment());
        fragments.add(DiseaseMapFragment.newInstance());
        fragments.add(AgricultureNewsFragment.newInstance());
        fragments.add(FourthFragment.newInstance());

        navigationBar.titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .fragmentList(fragments)
                .fragmentManager(fragmentManager)
                .addLayoutRule(EasyNavigationBar.RULE_BOTTOM)
                .addLayoutBottom(200)
                .onTabClickListener(new EasyNavigationBar.OnTabClickListener() {
                    @Override
                    public boolean onTabClickEvent(View view, int position) {
                        Log.i("MainActivity", String.valueOf(position));
                        if (position == 2) {
                            showMenu();
                            return true;
                        }
                        return false;
                    }
                })
                .mode(EasyNavigationBar.MODE_ADD)
                .anim(Anim.FadeIn)
                .build();
        navigationBar.setAddViewLayout(createWeiBoView());
        navigationBar.smoothScroll(false);

        mDrawer = findViewById(R.id.drawer_layout);
        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_NONE);

        mDrawer.setOnDrawerStateChangeListener(new ElasticDrawer.OnDrawerStateChangeListener() {
            @Override
            public void onDrawerStateChange(int oldState, int newState) {

            }

            @Override
            public void onDrawerSlide(float openRatio, int offsetPixels) {

            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        mMenu = findViewById(R.id.avatar);
        mBack = findViewById(R.id.back_menu);
        addBtn = findViewById(R.id.history_main);
        addBtn.setOnClickListener(this);
        avatar = findViewById(R.id.menu_avatar);
        avatar.setOnClickListener(this);
        toolbar.setOnMenuItemClickListener(this);
        mMenu.setOnClickListener(this);
        mBack.setOnClickListener(this);
        searchBtn = findViewById(R.id.search_main);
        searchBtn.setOnClickListener(this);
        requestPermission();
        requestWeather();
        requestDailyWeather();
        mainRefresh = findViewById(R.id.main_swipe_refresh);
        weatherView = findViewById(R.id.weather);
        forecastLayout = findViewById(R.id.forecast_layout);
        mainRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather();
                requestDailyWeather();
            }
        });
        classifier = new Classifier(Utils.assetFilePath(this, "resnet50.pt"));
    }

    //仿微博弹出菜单
    private View createWeiBoView() {
        ViewGroup view = (ViewGroup) View.inflate(this, R.layout.layout_add_view, null);
        TextView dateView = view.findViewById(R.id.date_tv);
        TextView dayView = view.findViewById(R.id.day_tv);
        TextView weekView = view.findViewById(R.id.week_tv);
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        int dow = calendar.get(Calendar.DAY_OF_WEEK);
        dateView.setText(String.valueOf(month) + "/" + String.valueOf(year));
        dayView.setText(String.valueOf(day));
        weekView.setText(getWeekOfDate(dow - 1));

        menuLayout = view.findViewById(R.id.icon_group);
        cancelImageView = view.findViewById(R.id.cancel_iv);
        cancelImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeAnimation();
            }
        });
        for (int i = 0; i < 2; i++) {
            View itemView = View.inflate(MainActivity.this, R.layout.item_icon, null);
            ImageView menuImage = itemView.findViewById(R.id.menu_icon_im);
            TextView menuText = itemView.findViewById(R.id.menu_text_tx);
            menuImage.setImageResource(menuItems[i]);
            menuText.setText(menuTextItems[i]);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            itemView.setLayoutParams(params);
            itemView.setVisibility(View.GONE);
            menuLayout.addView(itemView);
            final int index = i; //保存当前位置
            menuImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (index == 0) {
                        if (ContextCompat.checkSelfPermission(MainActivity.this,
                                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                                    Manifest.permission.CAMERA
                            }, TAKE_PICTURE);
                        } else {
                            takePicture();
                        }
                    } else {
                        //Toast.makeText(MainActivity.this, "你点击了相册！", Toast.LENGTH_SHORT).show();
                        if (ContextCompat.checkSelfPermission(MainActivity.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                            }, SELECT_PICTURE);
                        } else {
                            openAlbum();
                        }
                    }
                }
            });
        }
        return view;
    }

    private void showMenu() {
        startAnimation();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                cancelImageView.animate().rotation(90).setDuration(400);
            }
        });
        //菜单项弹出动画
        for (int i = 0; i < menuLayout.getChildCount(); i++) {
            final View child = menuLayout.getChildAt(i);
            child.setVisibility(View.INVISIBLE);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    child.setVisibility(View.VISIBLE);
                    ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 600, 0);
                    fadeAnim.setDuration(500);
                    KickBackAnimator kickBackAnimator = new KickBackAnimator();
                    kickBackAnimator.setDuration(500);
                    fadeAnim.setEvaluator(kickBackAnimator);
                    fadeAnim.start();
                }
            }, i * 50 + 100);
        }
    }

    public static String getWeekOfDate(int day_of_week) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        if (day_of_week < 0)
            day_of_week = 0;
        return weekDays[day_of_week];
    }


    //圆形扩展
    private void startAnimation() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        //水平中心位置
                        int x = NavigationUtil.getScreenWidth(MainActivity.this) / 2;
                        //竖直方向-25的位置
                        int y = (NavigationUtil.getScreenHeith(MainActivity.this) -
                                NavigationUtil.dip2px(MainActivity.this, 25));
                        //定义揭露动画
                        Animator animator = ViewAnimationUtils.createCircularReveal(
                                navigationBar.getAddViewLayout(), x, y, 0, navigationBar.getAddViewLayout().getHeight());
                        animator.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                            }

                            @Override
                            public void onAnimationStart(Animator animation) {
                                navigationBar.getAddViewLayout().setVisibility(View.VISIBLE);
                            }
                        });
                        animator.setDuration(300);
                        animator.start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //关闭动画
    private void closeAnimation() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                cancelImageView.animate().rotation(0).setDuration(400);
            }
        });

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int x = NavigationUtil.getScreenWidth(MainActivity.this) / 2;
                int y = (NavigationUtil.getScreenHeith(MainActivity.this) -
                        NavigationUtil.dip2px(MainActivity.this, 25));
                //与入场动画相反
                Animator animator = ViewAnimationUtils.createCircularReveal(
                        navigationBar.getAddViewLayout(), x, y,
                        navigationBar.getAddViewLayout().getHeight(), 0);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        navigationBar.getAddViewLayout().setVisibility(View.GONE);
                    }
                });
                animator.setDuration(300);
                animator.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //所有权限统一申请
    private void requestPermission() {
        String[] permissions = new String[]{
                Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CALL_PHONE};
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                for (String permission1 : permissions) {
                    int permission = ActivityCompat.checkSelfPermission(this, permission1);
                    if (permission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, permissions, PERMISSION_CODE);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void takePicture() {
        // 单独拍照
        PictureSelector.create(MainActivity.this)
                .openGallery(PictureMimeType.ofAll())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(3)// 每行显示个数
                .selectionMode(
                        PictureConfig.SINGLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .previewVideo(true)// 是否可预览视频
                .enablePreviewAudio(true) // 是否可播放音频
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .enableCrop(true)// 是否裁剪
                .compress(true)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(0, 0)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
                .isGif(true)// 是否显示gif图片
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否圆形裁剪
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .selectionMedia(selectList)// 是否传入已选图片
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/");
        startActivityForResult(intent, SELECT_PICTURE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 100:  //所有权限
                if (grantResults.length > 0) {
                    //循环遍历
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用该功能", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                }
                break;
            case 1: //拍照权限
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePicture();
                } else {
                    Toast.makeText(this, "必须同意所有权限才能使用该功能", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "必须同意所有权限才能使用该功能", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;

        }
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        closeAnimation();
        fragments.get(1).onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            AipImageClassify client = new AipImageClassify(MyApplication.APP_ID, MyApplication.API_KEY, MyApplication.SECRET_KEY);
                            // 可选：设置网络连接参数
                            client.setConnectionTimeoutInMillis(2000);
                            client.setSocketTimeoutInMillis(60000);

                            // 传入可选参数调用接口
                            HashMap<String, String> options = new HashMap<>();
                            options.put("baike_num", "5");
                            final JSONObject res = client.plantDetect(outputImage.getPath(), options);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, res.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).start();
                }
                break;
            case SELECT_PICTURE:
                if (resultCode == RESULT_OK) {
                    String path = handleImageOnKitKat(data);
                    Intent intent = new Intent(this, MainResultActivity.class);
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    intent.putExtra("image_path", path);
                    String predict = classifier.predict(bitmap);
                    intent.putExtra("predict", predict);
                    startActivity(intent);
                }
                break;
            case PictureConfig.CHOOSE_REQUEST:
                if (resultCode == RESULT_OK) {
                    selectList = PictureSelector.obtainMultipleResult(data);
                    LocalMedia media = selectList.get(0);
                    Log.i("图片-----》", media.getPath());
                    Intent intent = new Intent(this, MainResultActivity.class);
                    Bitmap bitmap = BitmapFactory.decodeFile(media.getPath());
                    intent.putExtra("image_path", media.getPath());
                    String predict = classifier.predict(bitmap);
                    intent.putExtra("predict", predict);
                    startActivity(intent);
                }
                break;
            case DYNAMIC:
                if (getIntent().getBooleanExtra("dynamic", false)) {
                    navigationBar.selectTab(4);
                }
                break;
            default:
                break;
        }
    }

    //4.4版本以上,选择相册中的图片不在返回图片真是的Uri了
    @TargetApi(19)
    private String handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果是Document类型的Uri,则通过document id 进行处理
            String docId = DocumentsContract.getDocumentId(uri);
            assert uri != null;
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的Uri,则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {

            //如果是file类型的Uri,直接获取图片路径即可
            imagePath = uri.getPath();
        }
        return imagePath;
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            //如果是从第一个开始查起的
            if (cursor.moveToFirst()) {
                //获取储存下的所有图片
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            //关闭查找
            cursor.close();
        }
        //返回路径
        return path;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            default:
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.avatar:
                mDrawer.openMenu();
                break;
            case R.id.back_menu:
                mDrawer.closeMenu();
                break;
            case R.id.history_main:
                startActivity(new Intent(this, AlbumActivity.class));
                break;
            case R.id.search_main:
                Toast.makeText(this, "该功能还在开发当中...", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }


    private void requestWeather() {
        String weatherUrl = "https://api.caiyunapp.com/v2.5/" + "5tfptDlcSmL3socE/" +
                "112.453582,38.02132/" + "hourly.json";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mainRefresh.setRefreshing(false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                List<Weather> skyconList = HttpUtil.handleResponse(responseText, "skycon");
                List<Weather> tempList = HttpUtil.handleResponse(responseText, "temperature");
                if (!skyconList.isEmpty() && !tempList.isEmpty()) {
                    datas.clear();
                    for (int i = 0; i < 6; i++) {
                        Weather skyCon = skyconList.get(i);
                        Weather temp = tempList.get(i);
                        String weather = skyCon.getSkycon();
                        String time = skyCon.getDatetime().getHours() < 10 ?
                                "0" + skyCon.getDatetime().getHours() + ":00" :
                                skyCon.getDatetime().getHours() + ":00";
                        int temperature = (int) temp.getTemp();
                        WeatherBean weatherBean = new WeatherBean(parseWeather(weather), temperature, time);
                        datas.add(weatherBean);
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        weatherView.setData(datas);
                        weatherView.notifyDataSetChanged();
                        mainRefresh.setRefreshing(false);
                    }
                });
            }
        });
    }

    private void requestDailyWeather() {
        String weatherUrl = "https://free-api.heweather.net/s6/weather/forecast?location=" + "taiyuan" +
                "&key=551f547c64b24816acfed8471215cd0e";

        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                dailyWeatherList = HttpUtil.handleDailyResponse(responseText);
                runOnUiThread(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        forecastLayout.removeAllViews();
                        for (DailyWeather forecast : dailyWeatherList) {
                            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.forecast_item, forecastLayout, false);
                            TextView dateText = view.findViewById(R.id.date_text);

                            TextView infoText = view.findViewById(R.id.info_text);
                            TextView tmpText = view.findViewById(R.id.tmp);
                            Date date = forecast.getDate();
                            String mm = (date.getMonth() + 1) + "";
                            if (Integer.valueOf(mm).intValue() < 10) {
                                mm = "0" + mm;
                            }
                            String day = date.getDate() + "";
                            if (Integer.valueOf(day).intValue() < 10)
                                day = "0" + day;
                            dateText.setText(mm + "月" + day + "日");

                            infoText.setText(forecast.getCond_txt_d());
                            tmpText.setText(forecast.getTmp_max() + "°" + " / " + forecast.getTmp_min() + "°");
                            forecastLayout.addView(view);
                        }
                        mainRefresh.setRefreshing(false);
                    }
                });
            }
        });
    }

    private String parseWeather(String weather) {
        String result = "晴（白天）";
        switch (weather) {
            case "CLEAR_DAY":
                result = WeatherBean.CLEAR_DAY;
                break;
            case "CLEAR_DAY_NIGHT":
                result = WeatherBean.CLEAR_DAY_NIGHT;
                break;
            case "PARTLY_CLOUDY_DAY":
                result = WeatherBean.PARTLY_CLOUDY_DAY;
                break;
            case "PARTLY_CLOUDY_NIGHT":
                result = WeatherBean.PARTLY_CLOUDY_NIGHT;
                break;
            case "CLOUDY":
                result = WeatherBean.CLOUDY;
                break;
            case "RAIN":
                result = WeatherBean.RAIN;
                break;
            case "MODERATE_RAIN":
                result = WeatherBean.MODERATE_RAIN;
                break;
            case "HEAVY_RAIN":
                result = WeatherBean.HEAVY_RAIN;
                break;
            case "WIND":
                result = WeatherBean.WIND;
                break;
        }
        return result;
//        LIGHT_SNOW = "小雪";
//        MODERATE_SNOW = "中雪";
//        HEAVY_SNOW = "大雪";
    }
}
