package com.example.ywang.diseaseidentification.view.activity;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.baidu.mapapi.animation.Animation;
import com.baidu.mapapi.animation.ScaleAnimation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.bumptech.glide.Glide;
import com.example.ywang.diseaseidentification.R;

public class TimeRecordActivity extends AppCompatActivity {

    private MapView mMapView = null;
    BaiduMap mBaiduMap = null;
    private Double lat,lng;
    private String imageUrl,locationDescribe;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_record);
        mMapView = findViewById(R.id.live_map_test);
        mBaiduMap = mMapView.getMap();
        imageView = findViewById(R.id.player);
        Intent intent = getIntent();
        lat = intent.getDoubleExtra( "latitude",0);
        lng = intent.getDoubleExtra( "longitude",0);
        imageUrl = intent.getStringExtra("image");
        locationDescribe = intent.getStringExtra("describe");
        Glide.with(this).load(imageUrl).into(imageView);
        initMap();
        //系统设置权限
        Toolbar toolbar = (Toolbar) findViewById(R.id.live_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        if(locationDescribe != ""){
            collapsingToolbar.setTitle( locationDescribe );
        }else {
            collapsingToolbar.setTitle( "中北大学主楼" );
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//设置透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//设置透明导航栏
        }
    }

    private void initMap(){
        LatLng myLocation = new LatLng(lat,lng);
        MyLocationData locData = new MyLocationData.Builder()
                .latitude(lat).longitude(lng).build();
        mBaiduMap.setMyLocationData(locData);
        //放大到标尺50m
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.zoomTo(18.0f);
        mBaiduMap.setMapStatus(mapStatusUpdate);
        MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(myLocation);
        mBaiduMap.animateMapStatus(status);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_gcoding);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(myLocation).icon(bitmap);
        Marker marker = (Marker) mBaiduMap.addOverlay(option);
        Animation markerAnimation = new ScaleAnimation(0, 1, 0, 1); //初始化生长效果动画
        markerAnimation.setDuration(1000);  //设置动画时间 单位毫秒
        marker.setAnimation(markerAnimation);
        marker.startAnimation();  //添加动画
        marker.setTitle(locationDescribe);  //设置window标题
        Button button = new Button(getApplicationContext());
        button.setBackgroundResource(R.drawable.popup);
        button.setText(locationDescribe);
        //构造InfoWindow
        //point 描述的位置点
        //-100 InfoWindow相对于point在y轴的偏移量
        InfoWindow mInfoWindow = new InfoWindow(button, myLocation, -100);
        marker.showInfoWindow(mInfoWindow);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected( item );
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时必须调用mMapView. onResume ()
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时必须调用mMapView. onPause ()
        mMapView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时必须调用mMapView.onDestroy()
        mMapView.onDestroy();
    }
}
