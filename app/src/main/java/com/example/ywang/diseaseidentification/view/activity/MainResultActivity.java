package com.example.ywang.diseaseidentification.view.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.adapter.CardPagerAdapter;
import com.example.ywang.diseaseidentification.bean.CardItem;
import com.example.ywang.diseaseidentification.utils.ShadowTransformer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainResultActivity extends AppCompatActivity {
    private Bitmap imageBitmap;
    private ViewPager mViewPager;
    private CardPagerAdapter mCardAdapter;
    private String[] predict_result;
    private List<String> linkList = new ArrayList<>();
    private ImageView mIvScan;
    private Animation mTop2Bottom, mBottom2Top;
    private boolean isNeedAnimation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_main);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//设置透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//设置透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setNavigationBarColor(getResources().getColor(android.R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        mViewPager = findViewById(R.id.viewPager_result);
        mCardAdapter = new CardPagerAdapter(MainResultActivity.this);
        if (getIntent().getBundleExtra("image_data") != null) {
            imageBitmap = (Bitmap) getIntent().getBundleExtra("image_data").get("data");
        } else if (getIntent().getStringExtra("image_path") != null) {
            String path = getIntent().getStringExtra("image_path");
            imageBitmap = BitmapFactory.decodeFile(path);
        }
        initScanLine();
        String pred = getIntent().getStringExtra("predict");
        ImageView imageView = findViewById(R.id.image);
        //imageView.setImageBitmap(imageBitmap);
        imageView.setBackground(new BitmapDrawable(imageBitmap));
        assert pred != null;
        predict_result = pred.split(";");
        parseData(predict_result);
    }

    private void parseData(String[] predict_result) {
        for (int i = 0; i < predict_result.length; i += 2) {
            String label = "";
            if (predict_result[i].contains("一般") || predict_result[i].contains("健康")
                    || predict_result[i].contains("严重")) {
                label = predict_result[i].substring(0, predict_result[i].length() - 2);
            } else if (predict_result[i].contains("病")) {
                label = predict_result[i].split("病")[0] + "病";
            }
            linkList.add("https://baike.baidu.com/item/" + label);
        }
        new DataTask().execute(linkList);
    }


    private class DataTask extends AsyncTask<List<String>, String, List<String>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mIvScan.setVisibility(View.VISIBLE);
            mIvScan.startAnimation(mTop2Bottom);
        }

        @SafeVarargs
        @Override
        protected final List<String> doInBackground(List<String>... strings) {
            publishProgress("正在分析图片");
            return parseHtmlData(strings[0]);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List<String> contentList) {
            super.onPostExecute(contentList);
            isNeedAnimation = false;
            ArrayList localArrayList = new ArrayList();
            for (int m = 1; m < predict_result.length; m += 2) {
                localArrayList.add(Double.parseDouble(predict_result[m]));
            }
            double d = (Double) Collections.max(localArrayList);
            for (int i = 0, j = 0; i < predict_result.length; i += 2, j++) {
                CardItem cardItem = new CardItem(predict_result[i], contentList.get(j), true);
                cardItem.setLink(linkList.get(j));
                double score = Double.parseDouble(predict_result[i + 1]);
                Log.e("score", String.valueOf(score));
                double rec = (score - 0.0D) / (d + 0.2D - 0.0D);
                BigDecimal b = new BigDecimal(rec);
                double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                cardItem.setScore(f1);
                mCardAdapter.addCardItem(cardItem);
            }
            CardItem finalCard = new CardItem("以上结果都不是？", "", false);
            finalCard.setImage_show(false);
            mCardAdapter.addCardItem(finalCard);

            ShadowTransformer mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
            mViewPager.setAdapter(mCardAdapter);
            mViewPager.setPageTransformer(false, mCardShadowTransformer);
            mViewPager.setOffscreenPageLimit(3);
            mCardShadowTransformer.enableScaling(true);
        }
    }

    private void initScanLine() {
        mIvScan = findViewById(R.id.scan_line);
        mTop2Bottom = new TranslateAnimation(TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.RELATIVE_TO_PARENT, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0.7f);

        mBottom2Top = new TranslateAnimation(TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.RELATIVE_TO_PARENT, 0.7f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0f);

        mBottom2Top.setRepeatMode(Animation.RESTART);
        mBottom2Top.setInterpolator(new LinearInterpolator());
        mBottom2Top.setDuration(1500);
        mBottom2Top.setFillEnabled(true);//使其可以填充效果从而不回到原地
        mBottom2Top.setFillAfter(true);//不回到起始位置
        //如果不添加setFillEnabled和setFillAfter则动画执行结束后会自动回到远点
        mBottom2Top.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isNeedAnimation) {
                    mIvScan.setRotationX(180);
                    mIvScan.startAnimation(mTop2Bottom);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mTop2Bottom.setRepeatMode(Animation.RESTART);
        mTop2Bottom.setInterpolator(new LinearInterpolator());
        mTop2Bottom.setDuration(1500);
        mTop2Bottom.setFillEnabled(true);
        mTop2Bottom.setFillAfter(true);
        mTop2Bottom.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isNeedAnimation) {
                    mIvScan.setRotationX(0);
                    mIvScan.startAnimation(mBottom2Top);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private List<String> parseHtmlData(List<String> linkList) {
        List<String> contentList = new ArrayList<>();
        for (String link : linkList) {
            try {
                //从一个URL加载一个Document对象。
                Document doc = Jsoup.connect(link).get();
                //选择所在节点
                Elements elements = doc.select("div.main-content").select("div.lemma-summary");
                String content = elements.get(0).select("div.para").text();
                Log.e("content", content);
                if (content.equals("")) {
                    contentList.add("");
                } else {
                    contentList.add(content);
                }
            } catch (Exception e) {
                Log.e("error1", e.toString());
                contentList.add("");
            }
        }
        return contentList;
    }
}
