package com.example.ywang.diseaseidentification.view.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.cocosw.bottomsheet.BottomSheet;
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
import java.util.ArrayList;
import java.util.List;

public class DiseaseActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout layout;
    private ImageView background;
    private CollapsingToolbarLayout collapsingToolbar;
    private final List<String> diseaseList = new ArrayList<>();
    private String VOICE = null;
    private boolean isFirst = true; //第一次播放时进行语音合成
    private String texts = "";
    private TextView contentView;
    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease);
        initView();
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String url = intent.getStringExtra("html");
        String img_url = intent.getStringExtra("image");
        Glide.with(DiseaseActivity.this).load(img_url).into(background);
        parseHtml(url);
        collapsingToolbar.setTitle(name);
    }

    private void initView(){
        Toolbar toolbar = findViewById(R.id.disease_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbar = findViewById(R.id.disease_collapsing_toolbar);
        layout = findViewById(R.id.diseases);
        background = findViewById(R.id.disease_background);
        FloatingActionButton voice_change = findViewById(R.id.voice_change);
        voice_change.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//设置透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//设置透明导航栏
        }
    }


    private void parseHtml(final String diseaseUrl){
        final String[] urls = diseaseUrl.split("\\.");
        final String urlSuffixes = urls[urls.length - 1];
        final boolean isHtml = urlSuffixes.equals("html");

        final String[] resourceList = diseaseUrl.split("/");
        final StringBuilder resourceLink = new StringBuilder();
        for (int k = 0;k < resourceList.length - 1;k++){
            resourceLink.append(resourceList[k]).append("/");
        }

        new Thread(new Runnable() {
            List<String> imageUrls = new ArrayList<>();
            @Override
            public void run() {
                try {
                    //从一个URL加载一个Document对象。
                    Document doc = Jsoup.connect(diseaseUrl).get();
                    if (isHtml){
                        final Elements elements = doc.select("div.content").select("p");
                        for (Element element : elements) {
                            Log.e("html",element.text());
                            diseaseList.add(element.text());
                        }
                    }else {
                        final Elements elements = doc.select("p");
                        for (Element element : elements) {
                            Log.e("html",element.text());
                            diseaseList.add(element.text());
                        }

                        doc = Jsoup.connect(diseaseUrl).get();
                        final Elements image_elements = doc.select("img");
                        for (Element element : image_elements){

                            Log.e("html",resourceLink.toString()
                                    + element.attr("src"));
                            imageUrls.add(resourceLink.toString()
                                    + element.attr("src"));
                        }
                    }
                    changeView(diseaseList,imageUrls,isHtml);
                }catch(Exception e) {
                    Log.e("html", e.toString());
                }
            }
        }).start();
    }

    private void changeView(final List<String> diseaseList,final List<String> imageUrls,
                            final boolean isHtml){
        runOnUiThread(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                layout.removeAllViews();
                int imageIndex = 0;
                for (int i = 0;i < diseaseList.size();i ++) {
                    String text = diseaseList.get(i);
                    if (!text.equals("")) {
                        String splitText = isHtml ? "：" : " ";
                        String[] resultList = text.split(splitText);
                        View view = LayoutInflater.from(DiseaseActivity.this)
                                .inflate(R.layout.card_item, layout, false);
                        final TextView content = view.findViewById(R.id.disease_text);
                        TextView title = view.findViewById(R.id.disease_main);
                        ImageView image = view.findViewById(R.id.disease_images);
                        final ImageView voice = view.findViewById(R.id.disease_voice);
                        voice.setTag("stop");
                        if (i > 1 && imageIndex < imageUrls.size()){
                            Glide.with(DiseaseActivity.this)
                                    .load(imageUrls.get(imageIndex))
                                    .into(image);
                            image.setVisibility(View.VISIBLE);
                            imageIndex ++;
                        }

                        content.setText("    ");
                        if (resultList.length > 1) {
                            title.setText(resultList[0]);
                            for (int j = 1; j < resultList.length; j++) {
                                content.append(resultList[j]);
                            }
                        } else {
                            content.setText(resultList[0]);
                        }
                        // 调整作物种植网数据
                        int ex_index = 1;
                        while ((i + ex_index < diseaseList.size()) && diseaseList.get(i + ex_index)
                                .split(splitText).length < 2){
                            content.append(text + diseaseList.get(i + ex_index));
                            i++;
                            ex_index++;
                        }
                        voice.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (voice.getTag() == "play") {
                                    voice.setTag("stop");
                                    voice.setImageResource(R.drawable.voice_play);
                                    if (SpeechSynthesizer.getSynthesizer().isSpeaking())
                                        SpeechSynthesizer.getSynthesizer().pauseSpeaking();
                                }else if (voice.getTag() == "stop"){
                                    voice.setTag("play");
                                    voice.setImageResource(R.drawable.voice_play_on);
                                    texts = content.getText().toString();
                                    contentView = content;
                                    SpeechSynthesizer(texts);
                                    SpeechSynthesizer.getSynthesizer().resumeSpeaking();
                                }
                            }
                        });
                        layout.addView(view);
                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.voice_change:
                new BottomSheet.Builder(DiseaseActivity.this)
                        .title("选择声音种类")
                        .sheet(R.menu.change_voice)
                        .listener(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MenuItem menuItem = mMenu.findItem(which);
                                Toast.makeText(DiseaseActivity.this,menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                                switch (which) {
                                    case R.id.xiaoyan:
                                        VOICE = "xiaoyan";
                                        break;
                                    case R.id.xiaoyu:
                                        VOICE = "xiaoyu";
                                        break;
                                    case R.id.henry:
                                        VOICE = "henry";
                                        break;
                                    case R.id.vimary:
                                        VOICE = "vimary";
                                        break;
                                    case R.id.xiaomei:
                                        VOICE = "xiaomei";
                                        break;
                                    case R.id.vixl:
                                        VOICE = "vixl";
                                        break;
                                    case R.id.xiaorong:
                                        VOICE = "xiaorong";
                                        break;
                                    case R.id.xiaokun:
                                        VOICE = "xiaokun";
                                        break;
                                    case R.id.xiaoqiang:
                                        VOICE = "xiaoqiang";
                                        break;
                                    case R.id.vixying:
                                        VOICE = "vixying";
                                        break;
                                    case R.id.nannan:
                                        VOICE = "nannan";
                                        break;
                                    case R.id.vils:
                                        VOICE = "vils";
                                        break;
                                    case R.id.xiaoxin:
                                        VOICE = "xiaoxin";
                                        break;
                                    case R.id.cancel_chose:
                                        break;
                                }
                            }
                        }).show();
                break;
            default:
                break;
        }
    }

    /*-------------------------------语音合成--------------------------*/
    public void SpeechSynthesizer(String text){
        //1.创建SpeechSynthesizer对象, 第二个参数：本地合成时传InitListener
        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer(DiseaseActivity.this, null);
        // 清空参数
        mTts.setParameter( SpeechConstant.PARAMS, null);
        mTts.setParameter( SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
        if (VOICE != null){
            mTts.setParameter( SpeechConstant.VOICE_NAME, VOICE);//设置发音人
        }else {
            mTts.setParameter( SpeechConstant.VOICE_NAME, "xiaoyan" );//设置发音人
        }

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
                Toast.makeText(DiseaseActivity.this, "语音组件未安装", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(DiseaseActivity.this, "语音合成失败,错误码: " + code, Toast.LENGTH_LONG).show();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.change_voice,menu);
        mMenu = menu;
        return  super.onCreateOptionsMenu(menu);
    }
}
