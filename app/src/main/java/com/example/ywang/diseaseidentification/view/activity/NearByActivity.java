package com.example.ywang.diseaseidentification.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.utils.baidumap.OverlayManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.Inflater;

public class NearByActivity extends AppCompatActivity implements OnGetPoiSearchResultListener {

    private MapView mMapView = null;
    private BaiduMap mBaiduMap = null;
    private PoiSearch mPoiSearch = null;
    private Marker nowMarker;
    private CardView cardView;

    private EditText mEditText;
    public SuggestionSearch mSuggestionSearch;
    private TextView mCancelText;
    private MapSearchAdapter mMapSearchAdapter;
    List<SuggestionResult.SuggestionInfo> infoList;

    /*经度纬度*/
    private double mLatitude;
    private double mLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_by);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().addFlags( WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.white));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        mMapView = findViewById(R.id.near_by_map_view);
        cardView = findViewById(R.id.result_card);
        mEditText = findViewById(R.id.ams_et);
        mCancelText = findViewById(R.id.ams_back);
        initSearchListener();
        initMap();
    }

    private void initMap(){
        mBaiduMap = mMapView.getMap();
        mMapView.showScaleControl(true);
        mMapView.showZoomControls(false);
        mLatitude = getIntent().getDoubleExtra("latitude", 38.0136);
        mLongitude = getIntent().getDoubleExtra("longitude", 112.444);
        LatLng latLng = new LatLng(mLatitude, mLongitude);
        MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.animateMapStatus(status);

        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.my_marker);

        OverlayOptions option = new MarkerOptions()
                .position(latLng) //必传参数
                .icon(bitmap) //必传参数
                .draggable(true)
                .flat(true)
                .animateType(MarkerOptions.MarkerAnimateType.grow);
        mBaiduMap.addOverlay(option);

        //放大到标尺50m
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.zoomTo(13.0f);
        mBaiduMap.setMapStatus(mapStatusUpdate);

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return false;
            }
        });

        // 初始化搜索模块，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
        //设置请求参数
        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption()
                .keyword("农资")//检索关键字
                .location(new LatLng(mLatitude, mLongitude))//检索位置
                .pageNum(0)//分页编号，默认是0页
                .pageCapacity(20)//设置每页容量，默认10条
                .scope(1)
                .radius(20000);//附近检索半径
        //发起请求
        mPoiSearch.searchNearby(nearbySearchOption);
        //释放检索对象
        //mPoiSearch.destroy();
    }

    private void initSearchListener(){
        final RecyclerView recyclerView = findViewById(R.id.res_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mMapSearchAdapter = new MapSearchAdapter();
        recyclerView.setAdapter(mMapSearchAdapter);
        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 这里的s表示改变之前的内容，通常start和count组合，可以在s中读取本次改变字段中被改变的内容。而after表示改变后新的内容的数量。
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 这里的s表示改变之后的内容，通常start和count组合，可以在s中读取本次改变字段中新的内容。而before表示被改变的内容的数量。
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 表示最终内容
                String mapInput = mEditText.getText().toString().trim();
                if (!TextUtils.isEmpty(mapInput)) {
                    //搜索关键词
                    mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
                            .keyword(mapInput).city("邢台")
                    );
                }
            }
        };
        mEditText.addTextChangedListener(tw);
        initSearch();
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(mEditText, 0);

        mCancelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditText.setText("");
                infoList.clear();
                mMapSearchAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 搜索
     */
    public void initSearch() {
        //关键词搜索
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(new OnGetSuggestionResultListener() {
            @Override
            public void onGetSuggestionResult(SuggestionResult suggestionResult) {
                //未找到相关结果
                if (suggestionResult == null || suggestionResult.getAllSuggestions() == null) {
                    return;
                }
                infoList = suggestionResult.getAllSuggestions();

                //关键搜索时，数据有时候没有经纬度，和地址信息,需要剔除
                Iterator<SuggestionResult.SuggestionInfo> itParent = infoList.iterator();
                while (itParent.hasNext()) {
                    SuggestionResult.SuggestionInfo ss = itParent.next();
                    if (ss.pt == null || TextUtils.isEmpty(ss.district)) {
                        itParent.remove();
                    }
                }
                mMapSearchAdapter.setDatas(infoList);
                mMapSearchAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        if (poiResult == null || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            Toast.makeText(NearByActivity.this, "未找到结果", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
            NearByPoiOverlay overlay = new NearByPoiOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(poiResult);
            overlay.addToMap();
            overlay.zoomToSpan();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
        if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(NearByActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        } else {
            String title = poiDetailResult.getName();
            Button button = new Button(getApplicationContext());
            button.setBackgroundResource(R.drawable.popup);
            button.setText(title);

            //构造InfoWindow
            InfoWindow mInfoWindow = new InfoWindow(button, nowMarker.getPosition(), -100);
            mBaiduMap.hideInfoWindow();
            nowMarker.showInfoWindow(mInfoWindow);
            if(cardView.getVisibility() == View.GONE){
                cardView.setVisibility(View.VISIBLE);
            }
            TextView text_title = findViewById(R.id.near_title);
            final TextView text_number = findViewById(R.id.near_number);
            TextView text_address = findViewById(R.id.near_address);
            TextView text_distance = findViewById(R.id.near_distance);
            text_title.setText(title);
            text_address.setText(poiDetailResult.getAddress());
            text_distance.setText(getDistance(poiDetailResult.getLocation(),
                    new LatLng(mLatitude,mLongitude)) + " km");
            text_number.setText(poiDetailResult.getTelephone());
            if(!text_number.getText().toString().equals("")){
                findViewById(R.id.near_tell_image).setVisibility(View.VISIBLE);
                findViewById(R.id.near_tell).setVisibility(View.VISIBLE);
                findViewById(R.id.near_tell).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ActivityCompat.checkSelfPermission( NearByActivity.this, Manifest.permission.CALL_PHONE )
                                == PackageManager.PERMISSION_GRANTED) {
                            Intent contact_intent = new Intent( Intent.ACTION_DIAL );
                            Uri data = Uri.parse( "tel:" + text_number.getText().toString());
                            contact_intent.setData( data );
                            startActivity( contact_intent );
                        }else {
                            ActivityCompat.requestPermissions(NearByActivity.this,
                                    new String[]{Manifest.permission.CALL_PHONE}, 100);
                        }
                    }
                });
            }else {
                findViewById(R.id.near_tell_image).setVisibility(View.GONE);
                findViewById(R.id.near_tell).setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }

    public double getDistance(LatLng start, LatLng end) {
        double lon1 = (Math.PI / 180) * start.longitude;
        double lon2 = (Math.PI / 180) * end.longitude;
        double lat1 = (Math.PI / 180) * start.latitude;
        double lat2 = (Math.PI / 180) * end.latitude;

        // 地球半径
        double R = 6371;
        // 两点间距离 km，如果想要米的话，结果*1000就可以了
        double distance =  Math.acos(Math.sin(lat1) * Math.sin(lat2) +
                Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1)) * R;
        BigDecimal b = new BigDecimal(distance);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private class MapSearchAdapter extends RecyclerView.Adapter<MapSearchAdapter.ViewHolder>{

        List<SuggestionResult.SuggestionInfo> infoList = new ArrayList<>();


        private void setDatas(List<SuggestionResult.SuggestionInfo> infoList){
            this.infoList = infoList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(NearByActivity.this).inflate(R.layout.item_map,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            SuggestionResult.SuggestionInfo info = infoList.get(position);
            holder.titleView.setText(info.getDistrict() + info.getKey());
        }

        @Override
        public int getItemCount() {
            return infoList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView titleView;

            public ViewHolder(View view) {
                super(view);
                titleView = view.findViewById(R.id.im_bigtv);
            }
        }
    }

    private class NearByPoiOverlay extends OverlayManager {
        private PoiResult mPoiResult = null;
        List<OverlayOptions> markerList = new ArrayList<>();

        NearByPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        public void setData(PoiResult poiResult) {
            this.mPoiResult = poiResult;
        }

        @Override
        public List<OverlayOptions> getOverlayOptions() {
            for (int i = 0; i < mPoiResult.getAllPoi().size(); i++) {
                Bundle bundle = new Bundle();
                bundle.putInt("index", i);

                BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.nong);
                OverlayOptions options = new MarkerOptions()
                        .extraInfo(bundle)
                        .icon(bitmapDescriptor)
                        .animateType(MarkerOptions.MarkerAnimateType.grow)
                        .position(mPoiResult.getAllPoi().get(i).location);
                markerList.add(options);
            }
            return markerList;
        }


        @Override
        public boolean onMarkerClick(Marker marker) {
            if (marker.getExtraInfo() != null) {
                int index = marker.getExtraInfo().getInt("index");
                PoiInfo poi = mPoiResult.getAllPoi().get(index);
                nowMarker = marker;
                mPoiSearch.searchPoiDetail((new PoiDetailSearchOption()).poiUid(poi.uid));
                return true;
            }
            return false;
        }

        @Override
        public boolean onPolylineClick(Polyline polyline) {
            return false;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放POI检索实例；
        if (mSuggestionSearch != null) {
            mSuggestionSearch.destroy();
        }
        // 释放检索对象
        mPoiSearch.destroy();
        // 清空地图所有的覆盖物
        mBaiduMap.clear();
        // 释放地图
        mMapView.onDestroy();
    }
}
