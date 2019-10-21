package com.example.ywang.diseaseidentification.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.adapter.disease.DiseasesAdapter;
import com.example.ywang.diseaseidentification.bean.baseData.DiseaseData;
import com.example.ywang.diseaseidentification.utils.file.ConstantUtils;
import com.example.zhouwei.library.CustomPopWindow;
import com.luck.picture.lib.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

public class CropDetailActivity extends AppCompatActivity {

    //下拉刷新列表
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private DiseasesAdapter adapter;
    private List<DiseaseData> mList = new ArrayList<>();
    private String name;
    private Toolbar toolbar;
    private CustomPopWindow mCustomPopWindow;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.crop_menu,menu);
        return true;
    }

    private void initDiseases(){
        mList.clear();
        if(ConstantUtils.scoreList.size() != 0){
            List<String[]> list = ConstantUtils.scoreList;
            for(int i = 1;i < list.size();i ++) {
                mList.add(new DiseaseData(list.get(i)[0],list.get(i)[1],list.get(i)[2]));
            }
        }else {
            for (int i = 0; i < ConstantUtils.datas.length; i++) {
                mList.add(ConstantUtils.datas[i]);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_map:
                View contentView = LayoutInflater.from(this).inflate(R.layout.pop_map,null);
                //处理popWindow 显示内容
                handleLogic(contentView);
                //创建并显示popWindow
                mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                        .setView(contentView)
                        .create()
                        .showAsDropDown(findViewById(R.id.action_map),0,20);
                break;
        }
        return true;
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

    /**
     * 处理弹出显示内容、点击事件等逻辑
     * @param contentView
     */
    private void handleLogic(View contentView){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCustomPopWindow != null){
                    mCustomPopWindow.dissmiss();
                }
                switch (v.getId()){

                }
            }
        };
        PhotoView resource = contentView.findViewById(R.id.crop_resource);
        resource.setOnClickListener(listener);
        PhotoView density = contentView.findViewById(R.id.crop_density);
        density.setOnClickListener(listener);
        Glide.with(this).load("http://www.cgris.net/cropmap/food.gif").into(resource);
        Glide.with(this).load("http://www.cgris.net/cropmap/food.gif").into(density);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ConstantUtils.scoreList.clear();
    }
}
