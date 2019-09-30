package com.example.ywang.diseaseidentification.view.fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

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
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.utils.MyOrientationListener;
import com.example.ywang.diseaseidentification.utils.PoiOverlay;
import com.example.ywang.diseaseidentification.view.activity.PanoramaActivity;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * 地图fragment
 */
public class DiseaseMapFragment extends Fragment implements BaiduMap.OnMarkerClickListener,OnGetPoiSearchResultListener{

    private MapView mMapView = null;
    private BaiduMap mBaiduMap = null;
    private LocationClient mLocationClient = null;
    private MyLocationConfiguration.LocationMode mLocationMode;

    private FloatingActionsMenu mFloatingActionsMenu;
    private FloatingActionButton mapTypeBtn,locModeBtn,posQuickBtn,panoramaBtn;

    private boolean isFirstLoc = true;
    /*自定义图标*/
    private BitmapDescriptor mIconLocation;
    private MyOrientationListener mMyOrientationListener;
    private float mCurrentX;
    /*经度纬度*/
    private double mLatitude;
    private double mLongitude;
    private List<LatLng> points = new ArrayList<>();

    private PoiSearch mPoiSearch = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_disease, container, false);
        //获取地图控件引用
        mMapView = (MapView) view.findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        //开启地图的定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setIndoorEnable(true);
        initMap();
        initFloatButton(view);
        // 初始化搜索模块，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
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
        option.setCoorType("bd09ll"); //设置坐标类型
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
        mBaiduMap.setOnMarkerClickListener(this); //设置Marker点击事件
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

        panoramaBtn = (FloatingActionButton) view.findViewById(R.id.position_panorama);
        panoramaBtn.setIcon(R.drawable.ic_search);
        panoramaBtn.setTitle("搜索全景图");
        panoramaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchButtonProcess();
//                addOverlaysToMap();
                mFloatingActionsMenu.toggle();
            }
        });
    }

    /**
     * 响应城市内搜索按钮点击事件
     */
    public void searchButtonProcess() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = View.inflate(getContext(), R.layout.dialog_select_indoor, null);
        final EditText city = (EditText) view.findViewById(R.id.et_city_indorr);
        final EditText key = (EditText) view.findViewById(R.id.et_key_indoor);
        builder.setView(view);
        builder.setTitle("搜索地点");
        builder.setPositiveButton("搜索", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mPoiSearch.searchInCity((new PoiCitySearchOption())
                        .city(city.getText().toString()).keyword(key.getText().toString()));
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    @Override
    public void onGetPoiResult(PoiResult result) {
        if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            Toast.makeText(getContext(), "未找到结果", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            mBaiduMap.clear();
            PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(result);
            overlay.addToMap();
            overlay.zoomToSpan();
            return;
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {
            // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
            String strInfo = "在";
            for (CityInfo cityInfo : result.getSuggestCityList()) {
                strInfo += cityInfo.city;
                strInfo += ",";
            }
            strInfo += "找到结果";
            Toast.makeText(getContext(), strInfo, Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult result) {
        if (result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(getContext(), "抱歉，未找到结果", Toast.LENGTH_SHORT)
                    .show();
        } else {
            LatLng latLng = result.getLocation();
            double latitude = latLng.latitude;
            double longitude = latLng.longitude;
            Intent intent = new Intent(getContext(), PanoramaActivity.class);
            intent.putExtra("latitude", latitude);
            intent.putExtra("longitude", longitude);
            intent.putExtra("uid",result.getUid());
            startActivity(intent);
        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }

    private class MyPoiOverlay extends PoiOverlay {

        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            PoiInfo poi = getPoiResult().getAllPoi().get(index);
            mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
                    .poiUid(poi.uid));
            return true;
        }
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

    public void addOverlaysToMap(){
        LatLng point = new LatLng(mLatitude,mLatitude);
        //构建marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_gcoding);
        //构建MarkerOption,用于在地图上添加Marker
        OverlayOptions options = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        Overlay marker = mBaiduMap.addOverlay(options);

        points.add(point);
        if(points.size() == 4){
            OverlayOptions mOverlayOptions = new PolylineOptions()
                    .width(10)
                    .color(0xAAFF0000)
                    .points(points);
            //在地图上绘制
            Overlay mPolyline = mBaiduMap.addOverlay(mOverlayOptions);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
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