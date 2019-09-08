package com.example.ywang.diseaseidentification.utils;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.ywang.diseaseidentification.bean.baseEnum.AccenteEnum;
import com.example.ywang.diseaseidentification.bean.baseEnum.LanguageEnum;
import com.example.ywang.diseaseidentification.bean.baseEnum.SpeechPeopleEnum;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

public class SynthesisManager {
    private final String TAG = "SynthesisManager";
    private Context mContext;
    private SpeechSynthesizer mTts;

    //设置语言
    public SynthesisManager setLanguage(LanguageEnum arg) {
        mTts.setParameter( SpeechConstant.LANGUAGE,arg.toString());
        return this;
    }
    //设置方言
    public SynthesisManager setAccent(AccenteEnum accent) {
        mTts.setParameter( SpeechConstant.ACCENT,accent.toString());
        return this;
    }
    //设置发音人
    public SynthesisManager setSpeechPeople(SpeechPeopleEnum speechPeople) {
        mTts.setParameter( SpeechConstant.VOICE_NAME,speechPeople.toString());
        return this;
    }
    //设置语速 0-100 默认50
    public SynthesisManager setSpeed(String speed) {
        mTts.setParameter( SpeechConstant.SPEED,speed);
        return this;
    }
    //设置音量 0-100 默认50
    public SynthesisManager setVolume(String volume) {
        mTts.setParameter( SpeechConstant.VOLUME,volume);
        return this;
    }
    //设置合成音调 0-100 默认50
    public SynthesisManager setPitch(String pitch) {
        mTts.setParameter( SpeechConstant.PITCH,pitch);
        return this;
    }
    //音频采样率 8000-16000  默认16000
    public SynthesisManager setSampleRate(String sampleRate) {
        mTts.setParameter( SpeechConstant.SAMPLE_RATE,sampleRate);
        return this;
    }

    private SynthesisManager(){}
    //通过静态内部类获取实例
    public static  SynthesisManager getSingleton(){
        return SingletonHolder.singleton;
    }
    //静态内部类里实例化StaticClassSingleton对象
    public static class SingletonHolder{
        private static final SynthesisManager singleton = new      SynthesisManager();
    }


    /**开始语音
     * @param msg  合成的内容
     */
    public SynthesisManager startSpeaking(String msg){
        mTts.startSpeaking(msg, new SynthesizerListener() {
            //会话结束回调接口，没有错误时，error为null
            public void onCompleted(SpeechError error) {
                Log.d(TAG,"话结束回调");
                listen.onCompleted(error);
                onSepakingStateListen.onCompleted(error);
            }
            //缓冲进度回调
            //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
            public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
                Log.d(TAG,"缓冲进度" + percent);
                listen.onBufferProgress(percent,beginPos,endPos, info);
            }
            //开始播放
            public void onSpeakBegin() {
                Log.d(TAG,"开始播放" );
                listen.onSpeakBegin();
                onSepakingStateListen.onSpeakBegin();
            }
            //暂停播放
            public void onSpeakPaused() {
                Log.d(TAG,"暂停播放" );
                listen.onSpeakPaused();
                onSepakingStateListen.onSpeakPaused();
            }
            //播放进度回调
            //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
            public void onSpeakProgress(int percent, int beginPos, int endPos) {
                Log.d(TAG,"播放进度" + percent);
                listen.onSpeakProgress( percent,  beginPos,  endPos);
            }
            //恢复播放回调接口
            public void onSpeakResumed() {
                Log.d(TAG,"恢复播放回调接口");
                listen.onSpeakResumed();
            }
            //会话事件回调接口
            public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
                Log.d(TAG,"onEvent");
                // listen.onEvent( arg0,  arg1,  arg2,  arg3);
            }
        });
        return this;
    }

    /**
     * 停止语音
     */
    public void stopSpeaking(){
        mTts.stopSpeaking();
    }

    /**初始化
     * @param
     */
    public SynthesisManager init(Context mContext, String appID){
        this.mContext = mContext;
        //配置讯飞AppId
        SpeechUtility.createUtility(mContext, SpeechConstant.APPID +"="+appID);
        //配置参数
        //实例化语音合成对象
        if(mTts == null){
            mTts = SpeechSynthesizer.createSynthesizer(mContext, null);
        }
        return this;
    }

    //语音合成监听回调
    public interface speakingListen{
        //会话结束回调接口，没有错误时，error为null
        void onCompleted(SpeechError error);
        //缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        void onBufferProgress(int percent, int beginPos, int endPos, String info);
        //开始播放
        void onSpeakBegin();
        //暂停播放
        void onSpeakPaused();
        //播放进度回调
        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        void onSpeakProgress(int percent, int beginPos, int endPos);
        //恢复播放回调接口
        void onSpeakResumed();
        //会话事件回调接口
        void onEvent(int arg0, int arg1, int arg2, Bundle arg3);
    }

    private speakingListen listen;

    //设置语音合成监听回调
    public void setSpeakingListen(speakingListen listen){
        this.listen = listen;
    }

    //语音合成状态回调
    public interface onSpeakingStateListen{
        //开始播放
        void onSpeakBegin();
        //暂停播放
        void onSpeakPaused();
        //会话结束回调接口，没有错误时，error为null
        void onCompleted(SpeechError error);

    }
    private onSpeakingStateListen onSepakingStateListen;
    //设置语音合成状态监听回调
    public void setSepakingStateListen(onSpeakingStateListen onSepakingStateListen){
        this.onSepakingStateListen = onSepakingStateListen;
    }

    //设置解析方向  本地 云端
    public void setEngineType(String type){
        mTts.setParameter( SpeechConstant.ENGINE_TYPE, type); //设置云端
    }
}
