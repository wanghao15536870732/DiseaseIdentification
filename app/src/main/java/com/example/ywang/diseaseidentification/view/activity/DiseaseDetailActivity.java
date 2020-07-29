package com.example.ywang.diseaseidentification.view.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.util.Xml;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.view.fragment.AgricultureNewsFragment;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class DiseaseDetailActivity extends AppCompatActivity {

    private List<String> images = new ArrayList<>();
    private String contentStr = "";
    private WebView webView;
    private TextView textView;
    private FloatingActionButton actionButton;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_detail);
        Toolbar toolbar = findViewById(R.id.toolbar_disease_detail);
        setSupportActionBar(toolbar);

        String content = getIntent().getStringExtra("content");
        List<String> list = getIntent().getStringArrayListExtra("imgList");
        images.addAll(list);

        String title = getIntent().getStringExtra("title");
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(title);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        Log.e("test", content);
        Log.e("test", list.toString());
        initBanner();
        webView = findViewById(R.id.disease_web_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//允许使用js
        webView.loadData(content, "text/html", Xml.Encoding.UTF_8.toString());
        contentStr = parseHtml(content);
        textView = findViewById(R.id.disease_text_view);
        textView.setText(contentStr);
        actionButton = findViewById(R.id.read_content);
        actionButton.setTag("stop");
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (actionButton.getTag() == "play") {
                    actionButton.setTag("stop");
                    actionButton.setImageResource(R.drawable.ic_play);
                    if (SpeechSynthesizer.getSynthesizer().isSpeaking())
                        SpeechSynthesizer.getSynthesizer().pauseSpeaking();
                }else if (actionButton.getTag() == "stop"){
                    actionButton.setTag("play");
                    actionButton.setImageResource(R.drawable.ic_pause);
                    SpeechSynthesizer(contentStr);
                    SpeechSynthesizer.getSynthesizer().resumeSpeaking();
                }
            }
        });
    }

    private String parseHtml(String content) {
        Document doc = Jsoup.parse(content);
        Elements elements = doc.select("p");
        StringBuilder contentStr = new StringBuilder();
        for (Element element : elements) {
            contentStr.append(element.text() + "\n\n");
        }
        Log.e("test", contentStr.toString());
        return contentStr.toString();
    }


    private void initBanner() {
        Banner banner = findViewById(R.id.banner_disease);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new AgricultureNewsFragment.GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        banner.setBannerAnimation(Transformer.DepthPage);
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        //设置轮播时间
        banner.setDelayTime(2500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.start();
    }

    /*-------------------------------语音合成--------------------------*/
    public void SpeechSynthesizer(String text) {
        //1.创建SpeechSynthesizer对象, 第二个参数：本地合成时传InitListener
        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer(DiseaseDetailActivity.this, null);
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端

        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人

        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        //设置合成音调
        mTts.setParameter(SpeechConstant.PITCH, "50");
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~sunny
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");
        //3.开始合成
        int code = mTts.startSpeaking(text, mSynListener);

        if (code != ErrorCode.SUCCESS) {
            if (code == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED) {
                //上面的语音配置对象为初始化时：
                Toast.makeText(DiseaseDetailActivity.this, "语音组件未安装", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(DiseaseDetailActivity.this, "语音合成失败,错误码: " + code, Toast.LENGTH_LONG).show();
            }
        }
    }

    //合成监听器
    private SynthesizerListener mSynListener = new SynthesizerListener() {
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {
        }

        //缓冲进度回调
        //percent为缓冲进度0~sunny，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
        }

        //开始播放
        public void onSpeakBegin() {
        }

        //暂停播放
        public void onSpeakPaused() {
        }

        //播放进度回调
        //percent为播放进度0~sunny,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            SpannableStringBuilder style = new SpannableStringBuilder(contentStr);
            style.setSpan(new BackgroundColorSpan(Color.parseColor("#f49d6b")),
                    beginPos, endPos + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(style);
        }

        //恢复播放回调接口
        public void onSpeakResumed() {
        }

        //会话事件回调接口
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (SpeechSynthesizer.getSynthesizer() != null) {
            SpeechSynthesizer.getSynthesizer().destroy();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
