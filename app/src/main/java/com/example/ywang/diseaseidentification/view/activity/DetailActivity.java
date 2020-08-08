package com.example.ywang.diseaseidentification.view.activity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ywang.diseaseidentification.R;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout layout;
    private ImageView background;
    private CollapsingToolbarLayout collapsingToolbar;
    private String texts = "";
    private TextView contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//设置透明状态栏
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        String link = getIntent().getStringExtra("link");
        String title = getIntent().getStringExtra("title");
        collapsingToolbar.setTitle(title);
        new DataTask().execute(link);
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.disease_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbar = findViewById(R.id.disease_collapsing_toolbar);
        layout = findViewById(R.id.diseases);
        background = findViewById(R.id.disease_background);
        FloatingActionButton voice_change = findViewById(R.id.voice_change);
        voice_change.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    private class DataTask extends AsyncTask<String, String, List<DetailItem>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<DetailItem> doInBackground(String... strings) {
            publishProgress("正在分析图片");
            return parseHtmlData(strings[0]);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List<DetailItem> contentList) {
            super.onPostExecute(contentList);
            int index;
            layout.removeAllViews();
            for (index = 0; index < contentList.size() - 1; index++) {
                View view = LayoutInflater.from(DetailActivity.this)
                        .inflate(R.layout.card_item, layout, false);
                TextView title = view.findViewById(R.id.disease_main);
                final TextView content = view.findViewById(R.id.disease_text);
                final ImageView voiceImg = view.findViewById(R.id.disease_voice);
                if (contentList.get(index).getTitle().equals("")) {
                    title.setVisibility(View.GONE);
                } else {
                    title.setText(contentList.get(index).getTitle());
                }
                content.setText(contentList.get(index).getContent());
                voiceImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (voiceImg.getTag() == "play") {
                            voiceImg.setTag("stop");
                            voiceImg.setImageResource(R.drawable.voice_play);
                            if (SpeechSynthesizer.getSynthesizer().isSpeaking())
                                SpeechSynthesizer.getSynthesizer().pauseSpeaking();
                        }else if (voiceImg.getTag() == "stop"){
                            voiceImg.setTag("play");
                            voiceImg.setImageResource(R.drawable.voice_play_on);
                            texts = content.getText().toString();
                            contentView = content;
                            SpeechSynthesizer(texts);
                            SpeechSynthesizer.getSynthesizer().resumeSpeaking();
                        }
                    }
                });
                layout.addView(view);
            }
            String imageUrl = contentList.get(index).getImageUrl();
            Glide.with(DetailActivity.this).load(imageUrl).into(background);
        }
    }

    private List<DetailItem> parseHtmlData(String link) {
        List<String> titleList = new ArrayList<>();
        List<DetailItem> detailList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(link).get();
            Elements titles = doc.select("div.main-content").select("h2.title-text");
            titleList.add("");
            for (Element element : titles) {
                element.select("span").remove();
                titleList.add(element.text());
                Log.e("detail", element.text());
            }

            Elements contents = doc.select("div.main-content").select("div.para");
            for (int i = 0; i < contents.size(); i++) {
                if (i < titleList.size()) {
                    DetailItem item = new DetailItem(titleList.get(i), contents.get(i).text());
                    detailList.add(item);
                } else {
                    DetailItem item = new DetailItem("", contents.get(i).text());
                    detailList.add(item);
                }
            }
            Elements image = doc.select("div.side-content").select("div.summary-pic");
            DetailItem imageItem = new DetailItem();
            imageItem.setImageUrl(image.select("img").attr("src"));
            detailList.add(imageItem);

            for (DetailItem item : detailList) {
                Log.e("detail", item.getTitle() + ":" + item.getContent() + ":" + item.getImageUrl());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return detailList;
    }

    /*-------------------------------语音合成--------------------------*/
    public void SpeechSynthesizer(String text){
        //1.创建SpeechSynthesizer对象, 第二个参数：本地合成时传InitListener
        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer(DetailActivity.this, null);
        // 清空参数
        mTts.setParameter( SpeechConstant.PARAMS, null);
        mTts.setParameter( SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
        mTts.setParameter( SpeechConstant.VOICE_NAME, "xiaoyan" );//设置发音人
        mTts.setParameter( SpeechConstant.SPEED, "50");//设置语速
        //设置合成音调
        mTts.setParameter( SpeechConstant.PITCH, "50");
        mTts.setParameter( SpeechConstant.VOLUME, "80");//设置音量，范围0~sunny
        mTts.setParameter( SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter( SpeechConstant.KEY_REQUEST_FOCUS, "true");
        //3.开始合成
        int code = mTts.startSpeaking(text, mSynListener);

        if (code != ErrorCode.SUCCESS) {
            if (code == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED) {
                //上面的语音配置对象为初始化时：
                Toast.makeText(DetailActivity.this, "语音组件未安装", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(DetailActivity.this, "语音合成失败,错误码: " + code, Toast.LENGTH_LONG).show();
            }
        }
    }

    //合成监听器
    private SynthesizerListener mSynListener = new SynthesizerListener() {
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) { }
        //缓冲进度回调
        //percent为缓冲进度0~sunny，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) { }
        //开始播放
        public void onSpeakBegin() { }
        //暂停播放
        public void onSpeakPaused() { }
        //播放进度回调
        //percent为播放进度0~sunny,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            SpannableStringBuilder style = new SpannableStringBuilder(texts);
            style.setSpan(new BackgroundColorSpan(Color.parseColor("#f49d6b")),
                    beginPos,endPos + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            contentView.setText(style);
        }
        //恢复播放回调接口
        public void onSpeakResumed() { }
        //会话事件回调接口
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) { }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(SpeechSynthesizer.getSynthesizer() != null){
            SpeechSynthesizer.getSynthesizer().destroy();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class DetailItem {
        String title;
        String content;
        String imageUrl;

        DetailItem() {
            this.title = "";
            this.content = "";
            this.imageUrl = "";
        }

        DetailItem(String title, String content) {
            this.title = title;
            this.content = content;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}