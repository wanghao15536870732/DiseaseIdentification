package com.example.ywang.diseaseidentification.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.adapter.DiseasesAdapter;
import com.example.ywang.diseaseidentification.bean.baseData.DiseaseData;
import com.example.ywang.diseaseidentification.utils.ConstantUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CropDetailActivity extends AppCompatActivity {


    //下拉刷新列表
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private DiseasesAdapter adapter;
    private List<DiseaseData> mList = new ArrayList<>();
    private String name;
    private Toolbar toolbar;

    private DiseaseData[] datas = {
            new DiseaseData("玉米圆斑病","http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/images/0224%20%E7%8E%89%E7%B1%B3%E5%9C%86%E6%96%91%E7%97%85%E7%A9%97%E8%85%90%E7%97%87%E7%8A%B6.jpg",
                    "http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/0224%20%E7%8E%89%E7%B1%B3%E5%9C%86%E6%96%91%E7%97%85.htm"),
            new DiseaseData("玉米干腐病","http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/images/0225%20%E7%8E%89%E7%B1%B3%E5%B9%B2%E8%85%90%E7%97%85%E6%9E%9C%E7%A9%97%E5%8F%97%E5%AE%B3%E7%8A%B6.jpg",
                    "http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/0225%20%E7%8E%89%E7%B1%B3%E5%B9%B2%E8%85%90%E7%97%85.htm"),
            new DiseaseData("玉米丝核菌穗腐病","http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/images/0226%20%E7%8E%89%E7%B1%B3%E4%B8%9D%E6%A0%B8%E8%8F%8C%E7%A9%97%E8%85%90%E7%97%85%E5%8F%8A%E5%A4%96%E8%8B%9E%E5%8F%B6%E4%B8%8A%E5%B0%8F%E8%8F%8C%E6%A0%B8.jpg",
                    "http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/0226%20%E7%8E%89%E7%B1%B3%E4%B8%9D%E6%A0%B8%E8%8F%8C%E7%A9%97%E8%85%90%E7%97%85.htm"),
            new DiseaseData("玉米镰刀菌穗粒腐病","http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/images/0227%20%E7%8E%89%E7%B1%B3%E8%B5%A4%E9%9C%89%E8%8F%8C%E7%A9%97%E8%85%90%EF%BC%88%E5%B7%A6%EF%BC%89%E5%92%8C%E9%95%B0%E5%88%80%E8%8F%8C%E7%A9%97%E8%85%90%E7%97%85.jpg",
                    "http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/0227(%E5%8F%B3)%20%E7%8E%89%E7%B1%B3%E9%95%B0%E5%88%80%E8%8F%8C%E7%A9%97%E7%B2%92%E8%85%90%E7%97%85.htm"),
            new DiseaseData("玉米赤霉病","http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/images/0227%20%E7%8E%89%E7%B1%B3%E8%B5%A4%E9%9C%89%E8%8F%8C%E7%A9%97%E8%85%90%EF%BC%88%E5%B7%A6%EF%BC%89%E5%92%8C%E9%95%B0%E5%88%80%E8%8F%8C%E7%A9%97%E8%85%90%E7%97%85.jpg",
                    "http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/0227(%E5%B7%A6)%20%E7%8E%89%E7%B1%B3%E8%B5%A4%E9%9C%89%E7%97%85.htm"),
            new DiseaseData("玉米枝孢穗腐病","http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/images/0228%20%E7%8E%89%E7%B1%B3%E6%9E%9D%E5%AD%A2%E8%8F%8C%E7%A9%97%E8%85%90%E7%97%85.jpg",
                    "http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/0228%20%E7%8E%89%E7%B1%B3%E6%9E%9D%E5%AD%A2%E7%A9%97%E8%85%90%E7%97%85.htm"),
            new DiseaseData("玉米斑枯病","http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/images/0233%20%E7%8E%89%E7%B1%B3%E6%96%91%E6%9E%AF%E7%97%85%E7%97%85%E5%8F%B6.jpg",
                    "http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/0233%20%E7%8E%89%E7%B1%B3%E6%96%91%E6%9E%AF%E7%97%85.htm"),
            new DiseaseData("玉米全蚀病","http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/images/0234%20%E7%8E%89%E7%B1%B3%E5%85%A8%E8%9A%80%E7%97%85%E6%A0%B9%E9%83%A8%E7%97%87%E7%8A%B6.jpg",
                    "http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/0234-0235%20%E7%8E%89%E7%B1%B3%E5%85%A8%E8%9A%80%E7%97%85.htm"),
            new DiseaseData("玉米线虫病","http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/images/0245%20%E7%8E%89%E7%B1%B3-%E7%BA%BF%E8%99%AB%E4%B8%BA%E5%AE%B3%E7%8E%89%E7%B1%B3%E7%94%B0%E9%97%B4%E5%8F%97%E5%AE%B3%E7%8A%B6.jpg",
                    "http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/0245-0247%20%E7%8E%89%E7%B1%B3%E7%BA%BF%E8%99%AB%E7%97%85.htm"),
            new DiseaseData("玉米缺素症","http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/images/0249%20%E7%8E%89%E7%B1%B3%E6%88%90%E6%A0%AA%E7%BC%BA%E6%B0%AE%E7%97%87%E7%8A%B6%E7%8A%B6.jpg",
                    "http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/0248-0262%20%E7%8E%89%E7%B1%B3%E7%BC%BA%E7%B4%A0%E7%97%87.htm"),
            new DiseaseData("玉米空秆","http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/images/0263%20%E7%8E%89%E7%B1%B3%E7%A9%BA%E7%A7%86.jpg",
                    "http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/0263%20%E7%8E%89%E7%B1%B3%E7%A9%BA%E7%A7%86.htm"),
            new DiseaseData("玉米倒伏","http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/images/0264%20%E7%8E%89%E7%B1%B3%E5%80%92%E4%BC%8F.jpg",
                    "http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/0264%20%E7%8E%89%E7%B1%B3%E5%80%92%E4%BC%8F.htm"),
    };

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar_crop1);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        actionBar.setTitle(name);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //网格式布局，产生2列数据
        initDiseases();
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        //让recyclerView的布局采用网格式布局
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DiseasesAdapter(mList);
        recyclerView.setAdapter(adapter);
        //设置下拉刷新进度条的颜色为绿色
        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruit();
            }
        });
    }

    //更新水果的布局
    private void refreshFruit(){
        //开启一个新的线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    //线程沉睡2 秒，否则刷新就立即结束了，从而看不到刷新过程
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initDiseases();
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private void initDiseases(){
        mList.clear();
        if(ConstantUtils.scoreList.size() != 0){
            List<String[]> list = ConstantUtils.scoreList;
            for(int i = 1;i < list.size();i ++) {
                mList.add(new DiseaseData(list.get(i)[0],list.get(i)[1],list.get(i)[2]));
            }
        }else {
            for (int i = 0; i < datas.length; i++) {
                mList.add(datas[i]);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ConstantUtils.scoreList.clear();
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
