package com.example.ywang.diseaseidentification.view.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.baidu.lbsapi.BMapManager;
import com.baidu.lbsapi.model.BaiduPanoData;
import com.baidu.lbsapi.panoramaview.OnTabMarkListener;
import com.baidu.lbsapi.panoramaview.PanoramaRequest;
import com.baidu.lbsapi.panoramaview.PanoramaView;
import com.baidu.lbsapi.panoramaview.PanoramaViewListener;
import com.baidu.lbsapi.panoramaview.TextMarker;
import com.baidu.lbsapi.tools.Point;
import com.baidu.pano.platform.plugin.indooralbum.IndoorAlbumCallback;
import com.baidu.pano.platform.plugin.indooralbum.IndoorAlbumPlugin;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.application.MyApplication;

public class PanoramaActivity extends AppCompatActivity {

    private PanoramaView mPanoramaView;
    private double mLatitude,mLongitude;
    private Toolbar toolbar;
    private String uid;  //当前全景图的uid

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication app = (MyApplication) this.getApplication();
        if (app.mBMapManager == null) {
            app.mBMapManager = new BMapManager(app);
            app.mBMapManager.init(new MyApplication.MyGeneralListener());
        }
        setContentView(R.layout.activity_panorama);
        mPanoramaView = (PanoramaView) findViewById(R.id.panorama);

        Intent intent = getIntent();
        mLatitude = intent.getDoubleExtra("latitude",0);
        mLongitude = intent.getDoubleExtra("longitude",0);
        uid = intent.getStringExtra("uid");
        Toast.makeText(app, uid, Toast.LENGTH_SHORT).show();
        Log.e("mLatitude:",String.valueOf(mLatitude));
        Log.e("mLongitude:",String.valueOf(mLongitude));
        mPanoramaView.setShowTopoLink(true); //是否显示邻接街景箭头（有邻接全景的时候）
        mPanoramaView.setPanoramaImageLevel(PanoramaView.ImageDefinition.ImageDefinitionHigh); //設置較高清晰度
        mPanoramaView.setPanoramaViewListener(new PanoramaViewListener() {
            @Override
            public void onDescriptionLoadEnd(String s) {

            }

            @Override
            public void onLoadPanoramaBegin() {

            }

            @Override
            public void onLoadPanoramaEnd(String s) {

            }

            @Override
            public void onLoadPanoramaError(String s) {

            }

            @Override
            public void onMessage(String s, int i) {

            }

            @Override
            public void onCustomMarkerClick(String s) {

            }

            @Override
            public void onMoveStart() {

            }

            @Override
            public void onMoveEnd() {

            }
        });
        mPanoramaView.setPanorama(mLongitude,mLatitude);
        PanoramaRequest request = PanoramaRequest.getInstance(PanoramaActivity.this);
        BaiduPanoData locationPanoramaData = request.getPanoramaInfoByLatLon(mLongitude,mLatitude);

        // 默认相册
        IndoorAlbumPlugin.getInstance().init();
        IndoorAlbumCallback.EntryInfo info = new IndoorAlbumCallback.EntryInfo();
        info.setEnterPid(locationPanoramaData.getPid());

        IndoorAlbumPlugin.getInstance().loadAlbumView(mPanoramaView,info);
        mPanoramaView.setPanoramaByUid(uid, PanoramaView.PANOTYPE_INTERIOR);

        toolbar = (Toolbar) findViewById(R.id.toolbar_panorama);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("附近全景圖");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_panorama, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.item_add:
                TextMarker marker = new TextMarker();
                marker.setMarkerPosition(new Point(mLongitude,mLatitude));
                marker.setMarkerHeight(20.3f);
                marker.setFontColor(0xFFFF0000);
                marker.setText("你好marker");
                marker.setFontSize(12);
                marker.setBgColor(0xFFFFFFFF);
                marker.setPadding(10, 20, 15, 25);
                marker.setOnTabMarkListener(new OnTabMarkListener() {
                    @Override
                    public void onTab() {
                        Toast.makeText(PanoramaActivity.this,
                                "标注已被点击", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPanoramaView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPanoramaView.onResume();
    }

    @Override
    protected void onDestroy() {
        mPanoramaView.destroy();
        super.onDestroy();
    }
}
