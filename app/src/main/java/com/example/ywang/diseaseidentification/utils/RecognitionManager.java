package com.example.ywang.diseaseidentification.utils;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.example.ywang.diseaseidentification.bean.baseEnum.AccenteEnum;
import com.example.ywang.diseaseidentification.bean.baseEnum.DictationResult;
import com.example.ywang.diseaseidentification.bean.baseEnum.DomainEnum;
import com.example.ywang.diseaseidentification.bean.baseEnum.LanguageEnum;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import java.util.List;

public class RecognitionManager {
    private Context mContext;
    private final String TAG = "RecognitionManager";
    private SpeechRecognizer mIat;

    public RecognitionManager setDomain(DomainEnum domain) {
        mIat.setParameter( SpeechConstant.DOMAIN, domain.toString());
        return this;
    }

    public RecognitionManager setLanguage(LanguageEnum language) {
        mIat.setParameter( SpeechConstant.LANGUAGE, language.toString());
        return this;
    }

    public RecognitionManager setAccent(AccenteEnum accent) {
        mIat.setParameter( SpeechConstant.ACCENT, accent.toString());
        return this;
    }

    public RecognitionManager setAudioPath(String audioPath) {
        //保存音频文件到本地（有需要的话）   仅支持pcm和wav，且需要自行添加读写SD卡权限
        mIat.setParameter( SpeechConstant.ASR_AUDIO_PATH, audioPath);
        return this;
    }

    private RecognitionManager() {
    }

    //通过静态内部类获取实例
    public static RecognitionManager getSingleton() {
        return SingletonHolder.singleton;
    }

    //静态内部类里实例化StaticClassSingleton对象
    public static class SingletonHolder {
        private static final RecognitionManager singleton = new RecognitionManager();
    }


    public RecognitionManager init(Context mContext, String appID) {
        SpeechUtility.createUtility(mContext, SpeechConstant.APPID + "=" + appID);
        this.mContext = mContext;
        if (mIat == null) {
            //1.创建SpeechRecognizer对象，第二个参数：本地识别时传InitListener
            mIat = SpeechRecognizer.createRecognizer(mContext, null);
        }
        return this;
    }

    /**
     * 语音识别回调监听
     */
    public interface onRecognitionListen {
        void result(String msg);
        void error(String errorMsg);
        void onBeginOfSpeech();
        void onVolumeChanged(int volume, byte[] data);
        void onEndOfSpeech();
    }

    //有动画效果
    private RecognizerDialog iatDialog;

    public void startRecognitionWithDialog(Context mContext, final onRecognitionListen listen) {
        if (this.listen == null) {
            this.listen = listen;
        }
        // ②初始化有交互动画的语音识别器
        iatDialog = new RecognizerDialog(mContext, mInitListener);
        //③设置监听，实现听写结果的回调
        iatDialog.setListener( mRecognizersDialog);
        //开始听写，需将sdk中的assets文件下的文件夹拷入项目的assets文件夹下（没有的话自己新建）
        iatDialog.show();
    }

    private RecognizerDialogListener mRecognizersDialog = new RecognizerDialogListener() {
        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            analyzeJson(recognizerResult,b);
                  /*  etText.setText(result);
                    //获取焦点
                    etText.requestFocus();
                    //将光标定位到文字最后，以便修改
                    etText.setSelection(result.length());*/
        }

        @Override
        public void onError(SpeechError speechError) {
            listen.error(speechError.getMessage());
            speechError.getPlainDescription(true);
        }
    };

    /**
     * 关闭语音识别对话框;
     */
    public void closeRecognitionDialog() {
        if (iatDialog != null) {
            iatDialog.dismiss();
            Log.d(TAG, "closeRecognitionDialog");
        } else {
            Log.d(TAG, "closeRecognitionDialog,iatDialog=null");
        }
    }

    private InitListener mInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                Log.d(TAG, "初始化失败，错误码：" + code);
            }
        }
    };

    private onRecognitionListen listen;

    public void startRecognition(onRecognitionListen listen) {
        if (this.listen == null) {
            this.listen = listen;
        }
       /* //2.设置听写参数，详见SDK中《MSC Reference Manual》文件夹下的SpeechConstant类
        mIat.setParameter(SpeechConstant.DOMAIN,"iat");
        mIat.setParameter(SpeechConstant.LANGUAGE,"zh_cn");
        mIat.setParameter(SpeechConstant.ACCENT,"mandarin ");
        //保存音频文件到本地（有需要的话）   仅支持pcm和wav，且需要自行添加读写SD卡权限
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/mIat.wav");*/
        // 3.开始听写
        mIat.startListening(mRecoListener);
    }


    //听写监听器
    private RecognizerListener mRecoListener = new RecognizerListener() {
        //听写结果回调接口(返回Json格式结果，用户可参见附录13.1)；
        // 一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加；
        // 关于解析Json的代码可参见Demo中JsonParser类；
        // isLast等于true时会话结束。
        public void onResult(RecognizerResult results, boolean isLast) {
            Log.d(TAG, "result:" + results.getResultString());
            analyzeJson(results,isLast);
        }

        //会话发生错误回调接口
        public void onError(SpeechError error) {
            //打印错误码描述
            Log.d(TAG, "error:" + error.getPlainDescription(true));
            listen.result(error.getMessage());
        }
        //开始录音
        public void onBeginOfSpeech() {
            Log.d(TAG, "开始录音");
            listen.onBeginOfSpeech();
        }

        //    volume音量值0~30，data音频数据
        public void onVolumeChanged(int volume, byte[] data) {
            Log.d(TAG, "音量为：" + volume);
            listen.onVolumeChanged(volume,data);
        }

        //结束录音
        public void onEndOfSpeech() {
            Log.d(TAG, "结束录音");
            listen.onEndOfSpeech();
        }

        //扩展用接口
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
        }
    };

    private String resultJson = "[";//放置在外边做类的变量则报错，会造成json格式不对（？）
    private String result = "";

    private void analyzeJson(RecognizerResult recognizerResult, boolean isLast) {
        if (!isLast) {
            resultJson += recognizerResult.getResultString() + ",";
        } else {
            resultJson += recognizerResult.getResultString() + "]";
        }
        if (isLast) {
            //解析语音识别后返回的json格式的结果
            Gson gson = new Gson();
            List<DictationResult> resultList = gson.fromJson(resultJson,
                    new TypeToken<List<DictationResult>>() {
                    }.getType());
            for (int i = 0; i < resultList.size() - 1; i++) {
                result += resultList.get(i).toString();
            }
            listen.result(result);
        }
    }
}
