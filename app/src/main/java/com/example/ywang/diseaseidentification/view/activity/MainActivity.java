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
import com.example.ywang.diseaseidentification.view.HorizontalChart;
import com.example.ywang.diseaseidentification.view.fragment.FourthFragment;
import com.example.ywang.diseaseidentification.view.fragment.MainFragment;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.view.fragment.DiseaseMapFragment;
import com.example.ywang.diseaseidentification.view.fragment.AgricultureNewsFragment;
import com.example.ywang.diseaseidentification.view.KickBackAnimator;
import com.example.zhouwei.library.CustomPopWindow;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.mingle.widget.ShapeLoadingDialog;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;
import com.next.easynavigation.constant.Anim;
import com.next.easynavigation.utils.NavigationUtil;
import com.next.easynavigation.view.EasyNavigationBar;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import me.panpf.swsv.CircularLayout;
import me.panpf.swsv.SpiderWebScoreView;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener,View.OnClickListener{

    private String[] tabText = {"首页", "地图", "","农讯", "农业圈"};

    private int[] normalIcon = {R.mipmap.index, R.mipmap.find,
            R.mipmap.add_image,R.mipmap.message, R.mipmap.me};
    //选中时icon
    private int[] selectIcon = {R.mipmap.index1, R.mipmap.find1,
            R.mipmap.add_image,R.mipmap.message1, R.mipmap.me1};

    //fragment列表
    private List<Fragment> fragments = new ArrayList<>();
    //底部导航栏
    private EasyNavigationBar navigationBar;
    private FragmentManager fragmentManager;

    //弹出窗包含view
    private LinearLayout menuLayout;
    private View cancelImageView;

    //弹出窗口图片和文字集合
    private int [] menuItems = {R.mipmap.menu_take_pic,R.mipmap.menu_select_pic};
    private String [] menuTextItems = {"拍照","相册"};
    private Handler mHandler = new Handler();

    private static final int PERMISSION_CODE = 100;
    private static final int TAKE_PICTURE = 1;
    private static final int SELECT_PICTURE = 2;

    private Uri imageUri;
    private File outputImage;
    private FlowingDrawer mDrawer;  //侧滑栏控件
    private Toolbar toolbar; //自定义Toolbar
    private ImageView mMenu,mBack,album;
    private SpiderWebScoreView spiderWebScoreView;  //蛛网控件
    private CircularLayout circularLayout;
    private CircleImageView avatar,addBtn;
    private List<LocalMedia> selectList = new ArrayList<>();

    private Score[] scores = new Score[]{
            new Score(7.0f,R.drawable.corn,"玉米"),
            new Score(2.0f,R.drawable.corn,"玉米"),
            new Score(3.0f,R.drawable.corn,"玉米"),
            new Score(5.0f,R.drawable.corn,"玉米"),
            new Score(8.0f,R.drawable.corn,"玉米"),
            new Score(2.0f,R.drawable.corn,"玉米"),
    };

    private CustomPopWindow mCustomPopWindow;
    private HorizontalChart horizontalChart;
    private ArrayList<Float> monthCountList = new ArrayList<Float>();
    private ShapeLoadingDialog shapeLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationBar = (EasyNavigationBar) findViewById(R.id.navigationBar);
        fragmentManager = getSupportFragmentManager();

        fragments.add(new MainFragment());
        fragments.add(new DiseaseMapFragment());
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
                        Log.i("MainActivity",String .valueOf(position));
                        if (position == 2){
                            showMenu();
                            return true;
                        }
                        return false;
                    }
                })
                .mode(EasyNavigationBar.MODE_ADD)
                .anim(Anim.ZoomIn)
                .build();
        navigationBar.setAddViewLayout(createWeiBoView());
        mDrawer = (FlowingDrawer) findViewById(R.id.drawer_layout);
        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
        mDrawer.setOnDrawerStateChangeListener(new ElasticDrawer.OnDrawerStateChangeListener() {
            @Override
            public void onDrawerStateChange(int oldState, int newState) {

            }

            @Override
            public void onDrawerSlide(float openRatio, int offsetPixels) {

            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mMenu = (ImageView) findViewById(R.id.avatar);
        mBack = (ImageView) findViewById(R.id.back_menu);
        album = (ImageView) findViewById(R.id.album);
        addBtn = (CircleImageView) findViewById(R.id.add_main);
        addBtn.setOnClickListener(this);
        avatar = (CircleImageView) findViewById(R.id.menu_avatar);
        horizontalChart = (HorizontalChart) findViewById(R.id.horizontal_chart);
        horizontalChart.setRefresh(true);
        monthCountList.clear();
        monthCountList.add(10f);
        monthCountList.add(30f);
        monthCountList.add(20f);
        monthCountList.add(60f);
        monthCountList.add(45f);
        horizontalChart.SetDate(monthCountList);
        horizontalChart.invalidate();
        horizontalChart.requestLayout();
        horizontalChart.setRefresh(false);
        avatar.setOnClickListener(this);
        album.setOnClickListener(this);
        //setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
        mMenu.setOnClickListener(this);
        mBack.setOnClickListener(this);
        spiderWebScoreView = (SpiderWebScoreView) findViewById(R.id.spiderWeb);
        circularLayout = (CircularLayout) findViewById(R.id.layout_circular);
        setup(spiderWebScoreView,circularLayout,scores);
        requestPermission();

    }

    @SuppressLint("SetTextI18n")
    private void setup(SpiderWebScoreView spiderWebScoreView, CircularLayout circularLayout, Score... scores){
        float[] scoreArray = new float[scores.length];
        for(int w = 0; w < scores.length; w++){
            scoreArray[w] = scores[w].score;
        }
        spiderWebScoreView.setScores(10f, scoreArray);

        circularLayout.removeAllViews();
        for(Score score : scores){
            TextView scoreTextView = (TextView)
                    LayoutInflater.from(getBaseContext()).inflate(R.layout.score, circularLayout, false);
            scoreTextView.setText(score.title);
            if(score.iconId != 0){
                scoreTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, score.iconId, 0);
            }
            circularLayout.addView(scoreTextView);
        }
    }

    //仿微博弹出菜单
    private View createWeiBoView(){
        ViewGroup view = (ViewGroup) View.inflate(this,R.layout.layout_add_view,null);
        menuLayout = view.findViewById(R.id.icon_group);
        cancelImageView = view.findViewById(R.id.cancel_iv);
        cancelImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeAnimation();
            }
        });
        for (int i = 0; i < 2; i++) {
            View itemView = (ViewGroup) View.inflate(MainActivity.this,R.layout.item_icon,null);
            ImageView menuImage = (ImageView) itemView.findViewById(R.id.menu_icon_im);
            TextView menuText = (TextView) itemView.findViewById(R.id.menu_text_tx);
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
                    if (index == 0){
                        if (ContextCompat.checkSelfPermission(MainActivity.this,
                                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                                    Manifest.permission.CAMERA
                            },TAKE_PICTURE);
                        }else {
                            takePicture();
                        }
                    }else{
                        //Toast.makeText(MainActivity.this, "你点击了相册！", Toast.LENGTH_SHORT).show();
                        if (ContextCompat.checkSelfPermission(MainActivity.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                            },SELECT_PICTURE);
                        }else{
                            openAlbum();
                        }
                    }
                }
            });
        }
        return view;
    }

    private void showMenu(){
        startAnimation();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                cancelImageView.animate().rotation(90).setDuration(400);
            }
        });
        //菜单项弹出动画
        for (int i = 0;i < menuLayout.getChildCount();i ++){
            final View child = menuLayout.getChildAt(i);
            child.setVisibility(View.INVISIBLE);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    child.setVisibility(View.VISIBLE);
                    ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child,"translationY",600,0);
                    fadeAnim.setDuration(500);
                    KickBackAnimator kickBackAnimator = new KickBackAnimator();
                    kickBackAnimator.setDuration(500);
                    fadeAnim.setEvaluator(kickBackAnimator);
                    fadeAnim.start();
                }
            },i * 50 + 100);
        }
    }


    //圆形扩展
    private void startAnimation(){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                        //水平中心位置
                        int x = NavigationUtil.getScreenWidth(MainActivity.this)/ 2;
                        //竖直方向-25的位置
                        int y = (int)(NavigationUtil.getScreenHeith(MainActivity.this) -
                                NavigationUtil.dip2px(MainActivity.this,25));
                        //定义揭露动画
                        Animator animator = ViewAnimationUtils.createCircularReveal(
                                navigationBar.getAddViewLayout(),x,y,0,navigationBar.getAddViewLayout().getHeight());
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
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    //关闭动画
    private void closeAnimation(){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                cancelImageView.animate().rotation(0).setDuration(400);
            }
        });

        try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                int x = NavigationUtil.getScreenWidth(MainActivity.this) / 2;
                int y = (int)(NavigationUtil.getScreenHeith(MainActivity.this) -
                        NavigationUtil.dip2px(MainActivity.this,25));
                //与入场动画相反
                Animator animator = ViewAnimationUtils.createCircularReveal(
                        navigationBar.getAddViewLayout(),x,y,
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
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //所有权限统一申请
    private void requestPermission(){
        String[] permissions = new String[]{
                Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_PHONE_STATE

        };
        try{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                for (int i = 0; i < permissions.length; i++) {
                    int permission = ActivityCompat.checkSelfPermission(this,permissions[i]);
                    if (permission != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(this,permissions,PERMISSION_CODE);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void takePicture(){
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

    private void openAlbum(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/");
        startActivityForResult(intent,SELECT_PICTURE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 100:  //所有权限
                if (grantResults.length > 0){
                    //循环遍历
                    for(int result : grantResults){
                        if (result != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this, "必须同意所有权限才能使用该功能", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                }
                break;
            case 1: //拍照权限
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    takePicture();
                }else {
                    Toast.makeText(this, "必须同意所有权限才能使用该功能", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else {
                    Toast.makeText(this, "必须同意所有权限才能使用该功能", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;

        }
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, final Intent data) {
        closeAnimation();
        switch (requestCode){
            case TAKE_PICTURE:
                if(resultCode == RESULT_OK){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            AipImageClassify client = new AipImageClassify(MyApplication.APP_ID, MyApplication.API_KEY, MyApplication.SECRET_KEY);
                            // 可选：设置网络连接参数
                            client.setConnectionTimeoutInMillis(2000);
                            client.setSocketTimeoutInMillis(60000);

                            // 传入可选参数调用接口
                            HashMap<String, String> options = new HashMap<String, String>();
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
                shapeLoadingDialog = new ShapeLoadingDialog(this);
                shapeLoadingDialog.setLoadingText("正在识别中...");
                if(resultCode == RESULT_OK){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    shapeLoadingDialog.show();
                                }
                            });
                            String path = "";
                            if(Build.VERSION.SDK_INT >= 19){
                                path = handleImageOnKitKat(data);
                            }else {
                                path = handleImageBeforeKitkat(data);
                            }
                            Log.e("result",path);
                            AipImageClassify client = new AipImageClassify(MyApplication.APP_ID, MyApplication.API_KEY, MyApplication.SECRET_KEY);
                            // 可选：设置网络连接参数
                            client.setConnectionTimeoutInMillis(2000);
                            client.setSocketTimeoutInMillis(60000);

                            // 传入可选参数调用接口
                            HashMap<String, String> options = new HashMap<String, String>();
                            options.put("baike_num", "5");
                            final JSONObject res = client.plantDetect(path, options);
                            Log.e("res",res.toString());
                            Intent intent = new Intent(MainActivity.this,ResultActivity.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    shapeLoadingDialog.dismiss();
                                }
                            });
                            if(Build.VERSION.SDK_INT >= 19){
                                intent.putExtra("imagePath",handleImageOnKitKat(data));
                            }else {
                                intent.putExtra("imagePath",handleImageBeforeKitkat(data));
                            }
                            intent.putExtra("result",res.toString());
                            startActivity(intent);
                        }
                    }).start();
                }
                break;
            case PictureConfig.CHOOSE_REQUEST:
                if(resultCode == RESULT_OK) {
                    shapeLoadingDialog = new ShapeLoadingDialog(this);
                    shapeLoadingDialog.setLoadingText("正在识别中...");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    shapeLoadingDialog.show();
                                }
                            });
                            // 图片选择结果回调
                            selectList = PictureSelector.obtainMultipleResult(data);
                            LocalMedia media = selectList.get(0);
                            Log.i("图片-----》", media.getPath());
                            Log.e("result",media.getPath());
                            AipImageClassify client = new AipImageClassify(MyApplication.APP_ID, MyApplication.API_KEY, MyApplication.SECRET_KEY);
                            // 可选：设置网络连接参数
                            client.setConnectionTimeoutInMillis(2000);
                            client.setSocketTimeoutInMillis(60000);

                            // 传入可选参数调用接口
                            HashMap<String, String> options = new HashMap<String, String>();
                            options.put("baike_num", "5");
                            final JSONObject res = client.plantDetect(media.getPath(), options);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    shapeLoadingDialog.dismiss();
                                }
                            });
                            Intent intent = new Intent(MainActivity.this,ResultActivity.class);
                            intent.putExtra("imagePath",media.getPath());
                            intent.putExtra("result",res.toString());
                            startActivity(intent);
                        }
                    }).start();

                }
                break;
            default:
                break;
        }
    }

    //4.4版本以上,选择相册中的图片不在返回图片真是的Uri了
    @TargetApi(19)
    private String handleImageOnKitKat(Intent data){
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this,uri)){
            //如果是Document类型的Uri,则通过document id 进行处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            //如果是content类型的Uri,则使用普通方式处理
            imagePath = getImagePath(uri,null);
        }else if ("file".equalsIgnoreCase(uri.getScheme())){

            //如果是file类型的Uri,直接获取图片路径即可
            imagePath = uri.getPath();
        }
        return imagePath;
    }

    //4.4版本以下的，选择相册的图片返回真实的Uri
    private String handleImageBeforeKitkat(Intent data){
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        return imagePath;
    }

    private String getImagePath(Uri uri,String selection){
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if (cursor != null){
            //如果是从第一个开始查起的
            if (cursor.moveToFirst()){
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
        switch (item.getItemId()){
            default:
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.avatar:
                mDrawer.openMenu();
                break;
            case R.id.back_menu:
                mDrawer.closeMenu();
                break;
            case R.id.album:
                startActivity(new Intent(MainActivity.this,AlbumActivity.class));
                break;
            case R.id.add_main:
                View contentView = LayoutInflater.from(this).inflate(R.layout.pop_menu,null);
                //处理popWindow 显示内容
                handleLogic(contentView);
                //创建并显示popWindow
                mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                        .setView(contentView)
                        .create()
                        .showAsDropDown(addBtn,0,20);
                break;
            default:
                break;
        }
    }

    /**
     * 处理弹出显示内容、点击事件等逻辑
     * @param contentView
     */
    private void handleLogic(View contentView){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCustomPopWindow != null){
                    mCustomPopWindow.dissmiss();
                }
                switch (v.getId()){
                    case R.id.menu_robot:
                        startActivity(new Intent(MainActivity.this,RobotActivity.class));
                        break;
                    case R.id.menu_dynamic:
                        startActivity(new Intent(MainActivity.this,AddDynamicActivity.class));
                        break;
                }
            }
        };
        contentView.findViewById(R.id.menu_robot).setOnClickListener(listener);
        contentView.findViewById(R.id.menu_dynamic).setOnClickListener(listener);
    }

    //蛛网评分组件
    private static class Score{
        private float score;
        private String title;
        private int iconId;

        private Score(float score, int iconId,String title) {
            this.score = score;
            this.iconId = iconId;
            this.title = title;
        }

        private Score(float score) {
            this.score = score;
        }
    }
}
