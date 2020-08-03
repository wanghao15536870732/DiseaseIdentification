package com.example.ywang.diseaseidentification.view.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ywang.diseaseidentification.R;

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
                        .inflate(R.layout.result_card_item, layout, false);
                TextView content = view.findViewById(R.id.disease_text);
                TextView title = view.findViewById(R.id.disease_main);
                if (contentList.get(index).getTitle().equals("")) {
                    title.setVisibility(View.GONE);
                } else {
                    title.setText(contentList.get(index).getTitle());
                }
                content.setText(contentList.get(index).getContent());
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