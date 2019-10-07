package com.example.ywang.diseaseidentification.view.activity;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ywang.diseaseidentification.R;

public class CropDetailActivity extends AppCompatActivity {


    public static final String EXTRA_NAME = "drugs_title";
    public static final String EXTRA_IMG = "drugs_img";

    private FloatingActionButton floatingActionButton, changevoicebuttton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_detail);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//设置透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//设置透明导航栏
        }

        Intent intent = getIntent();
        final String cropsTitle = intent.getStringExtra(EXTRA_NAME);
        final String cropsImg = intent.getStringExtra(EXTRA_IMG);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.news_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(cropsTitle);
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(CropDetailActivity.this).load(cropsImg).into(imageView);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.speech_outer);
        changevoicebuttton = (FloatingActionButton) findViewById(R.id.change_voice);
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
}
