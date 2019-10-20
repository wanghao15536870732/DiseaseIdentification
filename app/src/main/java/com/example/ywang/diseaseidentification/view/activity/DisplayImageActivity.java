package com.example.ywang.diseaseidentification.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.adapter.agricultural.DisplayImageAdapter;
import com.example.ywang.diseaseidentification.bean.ThumbViewInfo;
import java.util.ArrayList;
import java.util.List;

public class DisplayImageActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private String imgUrl;
    private int currentPosition;
    private DisplayImageAdapter adapter;
    private List<String> Urls = new ArrayList<>();
    private TextView mImageCount;
    private ImageView back;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);
        Intent intent = getIntent();
        if (intent != null){
            Bundle bundle = intent.getExtras();
            ArrayList<ThumbViewInfo> mList = intent.getParcelableArrayListExtra("images");
            for(int i = 0;i < mList.size();i ++){
                Urls.add(mList.get(i).getUrl());
            }
            currentPosition = intent.getIntExtra("position",0);
            imgUrl = mList.get(currentPosition).getUrl();
            Log.e("list",imgUrl);
            Log.e("list",String.valueOf(mList.get(currentPosition).getIndex()));

            viewPager = (ViewPager) findViewById(R.id.view_pager);
            adapter = new DisplayImageAdapter(Urls,this);
            mImageCount = (TextView) findViewById(R.id.page_text);
            mImageCount.setText(currentPosition + 1 + "/" + mList.size());

            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(currentPosition,false);
            viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    currentPosition = position;
                    mImageCount.setText(currentPosition + 1 + "/" + Urls.size());
                }
            });
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Window window = getWindow();
                window.addFlags( WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS );
                window.setStatusBarColor( getResources().getColor( R.color.black) );
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            }
            back = findViewById(R.id.page_back);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }
}
