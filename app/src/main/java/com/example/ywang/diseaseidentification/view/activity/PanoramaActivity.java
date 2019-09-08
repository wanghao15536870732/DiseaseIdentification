package com.example.ywang.diseaseidentification.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.baidu.lbsapi.BMapManager;
import com.baidu.lbsapi.panoramaview.PanoramaView;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.application.MyApplication;

public class PanoramaActivity extends AppCompatActivity {

    private PanoramaView mPanoramaView;
    private double mLatitude,mLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panorama);
        mPanoramaView = (PanoramaView) findViewById(R.id.panorama);

        Intent intent = getIntent();
        mLatitude = intent.getDoubleExtra("latitude",0);
        mLongitude = intent.getDoubleExtra("longitude",0);
        Log.e("mLatitude:",String.valueOf(mLatitude));
        Log.e("mLongitude:",String.valueOf(mLongitude));

        MyApplication app = (MyApplication) this.getApplication();
        if (app.mBMapManager == null) {
            app.mBMapManager = new BMapManager(app);
            app.mBMapManager.init(new MyApplication.MyGeneralListener());
        }

        mPanoramaView.setPanorama(mLongitude,mLatitude);
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
