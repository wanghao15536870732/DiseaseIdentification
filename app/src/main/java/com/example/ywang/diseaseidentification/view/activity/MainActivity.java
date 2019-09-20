package com.example.ywang.diseaseidentification.view.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
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
import com.example.ywang.diseaseidentification.view.fragment.FourthFragment;
import com.example.ywang.diseaseidentification.view.fragment.MainFragment;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.view.fragment.SecondFragment;
import com.example.ywang.diseaseidentification.view.fragment.ThirdFragment;
import com.example.ywang.diseaseidentification.view.KickBackAnimator;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;
import com.next.easynavigation.constant.Anim;
import com.next.easynavigation.utils.NavigationUtil;
import com.next.easynavigation.view.EasyNavigationBar;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.panpf.swsv.CircularLayout;
import me.panpf.swsv.SpiderWebScoreView;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener,View.OnClickListener{

    private String[] tabText = {"首页", "发现", "","消息", "农业圈"};

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
    private int [] menuItems = {R.mipmap.menu_take_pic,R.mipmap.menu_select_pic,R.mipmap.ic_star};
    private String [] menuTextItems = {"拍照","相册","动态"};
    private Handler mHandler = new Handler();

    private static final int PERMISSION_CODE = 100;
    private static final int TAKE_PICTURE = 1;
    private static final int SELECT_PICTURE = 2;

    private Uri imageUri;

    private FlowingDrawer mDrawer;  //侧滑栏控件
    private Toolbar toolbar; //自定义Toolbar
    private ImageView mMenu,mBack,mRobotBtn;  //菜单按钮
    private SpiderWebScoreView spiderWebScoreView;  //蛛网控件
    private CircularLayout circularLayout;

    private Score[] scores = new Score[]{
            new Score(7.0f,R.drawable.corn,"玉米"),
            new Score(2.0f,R.drawable.corn,"玉米"),
            new Score(3.0f,R.drawable.corn,"玉米"),
            new Score(5.0f,R.drawable.corn,"玉米"),
            new Score(8.0f,R.drawable.corn,"玉米"),
            new Score(2.0f,R.drawable.corn,"玉米"),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationBar = (EasyNavigationBar) findViewById(R.id.navigationBar);
        fragmentManager = getSupportFragmentManager();

        fragments.add(new MainFragment());
        fragments.add(new SecondFragment());
        fragments.add(new ThirdFragment());
        fragments.add(new FourthFragment());

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
        mRobotBtn = (ImageView) findViewById(R.id.robot);
        //setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
        mMenu.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mRobotBtn.setOnClickListener(this);
        spiderWebScoreView = (SpiderWebScoreView) findViewById(R.id.spiderWeb);
        circularLayout = (CircularLayout) findViewById(R.id.layout_circular);
        setup(spiderWebScoreView,circularLayout,scores);

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
        for (int i = 0; i < 3; i++) {
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
                    }else if (index == 1){
                        //Toast.makeText(MainActivity.this, "你点击了相册！", Toast.LENGTH_SHORT).show();
                        if (ContextCompat.checkSelfPermission(MainActivity.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                            },SELECT_PICTURE);
                        }else{
                            openAlbum();
                        }
                    }else {
                        startActivity(new Intent(MainActivity.this,AddDynamicActivity.class));
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
                Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE
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
        File outputImage = new File(getExternalCacheDir(),"take_image.jpg");
        try{
            if (outputImage.exists()){
                outputImage.delete();
            }
            outputImage.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        //如果Android版本高于7.0
        if (Build.VERSION.SDK_INT >= 24){
            //调用FileProvider的getUriForFile()方法将照片解析成Uri对象
            imageUri = FileProvider.getUriForFile(MainActivity.this,
                    "com.example.ywang.diseaseidentification.fileprovider",outputImage);
        }else {
            imageUri = Uri.fromFile(outputImage);
        }

        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,TAKE_PICTURE);
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
                            finish();
                        }
                    }
                }else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
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
            case R.id.robot:
                startActivity(new Intent(MainActivity.this,RobotActivity.class));
                break;
            default:
                break;
        }
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
