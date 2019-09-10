package com.example.ywang.diseaseidentification.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.view.activity.PanoramaActivity;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

public class MainFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        TextView text = (TextView) view.findViewById(R.id.text);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpeechSynthesizer("你好");
            }
        });
        return view;
    }

    /*语音合成*/
    public void SpeechSynthesizer(String text){
        //1.创建SpeechSynthesizer对象, 第二个参数：本地合成时传InitListener
        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer(getContext(), null);
        //1.清空参数
        mTts.setParameter( SpeechConstant.PARAMS, null);
        mTts.setParameter( SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
        mTts.setParameter( SpeechConstant.VOICE_NAME, "xiaoyan" );//设置发音人
        mTts.setParameter( SpeechConstant.SPEED, "50");//设置语速
        //2.设置合成音调
        mTts.setParameter( SpeechConstant.PITCH, "50");
        mTts.setParameter( SpeechConstant.VOLUME, "80");//设置音量，范围0~100
        mTts.setParameter( SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter( SpeechConstant.KEY_REQUEST_FOCUS, "true");
        //3.开始合成
        int code = mTts.startSpeaking(text, mSynListener);

        if (code != ErrorCode.SUCCESS) {
            if (code == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED) {
                //上面的语音配置对象为初始化时：
                Toast.makeText(getContext(), "语音组件未安装", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "语音合成失败,错误码: " + code, Toast.LENGTH_LONG).show();
            }
        }
    }

    /*合成监听器*/
    private SynthesizerListener mSynListener = new SynthesizerListener() {
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {}

        //缓冲进度回调
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) { }

        //开始播放
        public void onSpeakBegin() { }

        //暂停播放
        public void onSpeakPaused() { }

        //播放进度回调
        public void onSpeakProgress(int percent, int beginPos, int endPos) { }

        //恢复播放回调接口
        public void onSpeakResumed() { }

        //会话事件回调接口
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) { }
    };

}
