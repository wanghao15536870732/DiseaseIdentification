package com.example.ywang.diseaseidentification;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.ywang.diseaseidentification.utils.MyOrientationListener;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

public class SecondFragment extends Fragment {

    private MapView mMapView = null;
    private BaiduMap mBaiduMap = null;
    private LocationClient mLocationClient = null;
    private MyLocationConfiguration.LocationMode mLocationMode;

    private FloatingActionsMenu mFloatingActionsMenu;
    private FloatingActionButton mapTypeBtn,locModeBtn,posQuickBtn;

    private boolean isFirstLoc = true;
    /*自定义图标*/
    private BitmapDescriptor mIconLocation;
    private MyOrientationListener mMyOrientationListener;
    private float mCurrentX;
    /*经度纬度*/
    private double mLatitude;
    private double mLongitude;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        //获取地图控件引用
        mMapView = (MapView) view.findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        //开启地图的定位图层
        mBaiduMap.setMyLocationEnabled(true);
        initFloatButton(view);
        initMap();
        return view;
    }

    private void initMap(){
        //不显示地图上的比例尺
        mMapView.showScaleControl(true);
        mMapView.showZoomControls(false);

        //放大到标尺50m
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.zoomTo(18.0f);
        mBaiduMap.setMapStatus(mapStatusUpdate);

        //定位初始化
        mLocationMode = MyLocationConfiguration.LocationMode.NORMAL; //定位模式
        mLocationClient = new LocationClient(getContext());
        //通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);  //打开Gps
        option.setCoorType("bd0911"); //设置坐标类型
        option.setScanSpan(1000);
        //设置LocationClientOptions
        mLocationClient.setLocOption(option);
        //注册LocationListener监听器
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
        //开启地图定位图层
        mLocationClient.start();

        //初始化图标
        mIconLocation = BitmapDescriptorFactory.fromResource(R.mipmap.navi_map_gps_locked);
        mMyOrientationListener = new MyOrientationListener(getContext());
        mMyOrientationListener.setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
            @Override
            public void onOrientationChanged(float x) {
                mCurrentX = x;
            }
        });
        mMyOrientationListener.start();
    }

    private void initFloatButton(View view){
        mFloatingActionsMenu = (FloatingActionsMenu) view.findViewById(R.id.map_actions_menu);
        mapTypeBtn = (FloatingActionButton) view.findViewById(R.id.change_map_type);
        mapTypeBtn.setIcon(R.drawable.ic_map_normal);
        mapTypeBtn.setTitle("卫星地图");
        mapTypeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBaiduMap.getMapType() == BaiduMap.MAP_TYPE_NORMAL){
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                    mapTypeBtn.setIcon(R.drawable.ic_map_statellite);
                    mapTypeBtn.setTitle("普通地图");
                }else{
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                    mapTypeBtn.setIcon(R.drawable.ic_map_normal);
                    mapTypeBtn.setTitle("卫星地图");
                }
                mFloatingActionsMenu.toggle();
            }
        });
        locModeBtn = (FloatingActionButton) view.findViewById(R.id.change_map_model);
        locModeBtn.setIcon(R.drawable.map_mode_compass);
        locModeBtn.setTitle("罗盘模式");
        locModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mLocationMode == MyLocationConfiguration.LocationMode.NORMAL){
                    mLocationMode = MyLocationConfiguration.LocationMode.COMPASS;
                    locModeBtn.setIcon(R.drawable.map_mode_normal);
                    locModeBtn.setTitle("定位模式");
                }else {
                    mLocationMode = MyLocationConfiguration.LocationMode.NORMAL;
                    locModeBtn.setIcon(R.drawable.map_mode_compass);
                    locModeBtn.setTitle("罗盘模式");
                }
                mFloatingActionsMenu.toggle();
            }
        });

        posQuickBtn = (FloatingActionButton) view.findViewById(R.id.position_quick);
        posQuickBtn.setIcon(R.drawable.ic_map_position);
        posQuickBtn.setTitle("快速定位");
        posQuickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LatLng latLng = new LatLng(mLatitude,mLongitude);
                MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(latLng);
                mBaiduMap.animateMapStatus(status);
                mFloatingActionsMenu.toggle();
            }
        });
    }

    public class MyLocationListener extends BDAbstractLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //mapView 销毁后不再处理新接受的位置
            if (bdLocation == null || mMapView == null){
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                    //开始获取方向信息，顺时针0-360
                    .direction(mCurrentX)
                    .latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);

            MyLocationConfiguration configuration = new MyLocationConfiguration(mLocationMode,true,mIconLocation);
            mBaiduMap.setMyLocationConfiguration(configuration);

            /*更新经纬度*/
            mLatitude = bdLocation.getLatitude();
            mLongitude = bdLocation.getLongitude();

            if (isFirstLoc){  //第一次定位
                LatLng latLng = new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
                MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(latLng);
                mBaiduMap.animateMapStatus(status);
                isFirstLoc = false;
            }
        }
    }
    
    @Override
    public void onStart() {
        super.onStart();
        mMyOrientationListener.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        mLocationClient.stop();
        mMyOrientationListener.stop();
    }

    @Override
    public void onResume() {
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
        super.onResume();
    }
    @Override
    public void onPause() {
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

}