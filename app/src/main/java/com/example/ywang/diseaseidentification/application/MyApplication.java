package com.example.ywang.diseaseidentification.application;


import android.app.Application;
import android.os.Environment;
import android.util.Log;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.example.ywang.diseaseidentification.utils.RecognitionManager;
import com.example.ywang.diseaseidentification.utils.SynthesisManager;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.VoiceWakeuper;
import com.iflytek.cloud.util.ResourceUtil;

public class MyApplication extends Application {

    String TAG = "MyApplication";
    // 语音合成对象
    public SpeechSynthesizer mTts;
    // 语音唤醒对象
    public VoiceWakeuper mIvw;
    // 语音听写对象
    public SpeechRecognizer mIat;

    // 默认云端发音人
    public static String voicerCloud = "xiaoyan";
    public String mEngineType = SpeechConstant.TYPE_CLOUD;

    // 设置门限值 ： 门限值越低越容易被唤醒
    private int curThresh = 1450;
    private String keep_alive = "1";
    private String ivwNetMode = "1";

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
    }

}
