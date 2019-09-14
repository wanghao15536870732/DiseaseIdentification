package com.example.ywang.diseaseidentification.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.baidu.lbsapi.BMapManager;
import com.baidu.lbsapi.MKGeneralListener;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.example.ywang.diseaseidentification.bean.ChatListData;
import com.example.ywang.diseaseidentification.utils.RecognitionManager;
import com.example.ywang.diseaseidentification.utils.SynthesisManager;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {

    public static String TAG = "Init";
    public BMapManager mBMapManager;
    private static MyApplication mInstance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        SpeechUtility.createUtility(this,SpeechConstant.APPID + "=5d7360d3");
        RecognitionManager.getSingleton().init(this,"5d7360d3");
        SynthesisManager.getSingleton().init(this,"5d7360d3");
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
        mInstance = this;
        initEngineManager(this);
    }

    public void initEngineManager(Context context) {
        if (mBMapManager == null) {
            mBMapManager = new BMapManager(context);
        }

        if (!mBMapManager.init(new MyGeneralListener())) {
            Toast.makeText(MyApplication.getInstance().getApplicationContext(), "BMapManager初始化错误!",
                    Toast.LENGTH_LONG).show();
        }
        Log.d(TAG, "initEngineManager");
    }

    public static MyApplication getInstance() {
        return mInstance;
    }

    // 常用事件监听，用来处理通常的网络错误，授权验证错误等
    public static class MyGeneralListener implements MKGeneralListener {
        @Override
        public void onGetPermissionState(int iError) {
            //非零值表示key验证未通过
            if (iError != 0) { // 授权Key错误：
                Log.d(TAG, "请在AndroidManifest.xml中输入正确的授权Key,并检查您的网络连接是否正常！error: " + iError);
            } else {
                Log.d(TAG, "key认证成功");
            }
        }
    }
}
