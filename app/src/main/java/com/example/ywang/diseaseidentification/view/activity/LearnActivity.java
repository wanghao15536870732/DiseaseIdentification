package com.example.ywang.diseaseidentification.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.adapter.LeftAdapter;
import com.example.ywang.diseaseidentification.adapter.RightAdapter;
import com.example.ywang.diseaseidentification.bean.CropBean;
import com.example.ywang.diseaseidentification.bean.CropItem;
import com.example.ywang.diseaseidentification.bean.baseData.DiseaseData;
import com.example.ywang.diseaseidentification.utils.ConstantUtils;

import java.util.ArrayList;
import java.util.List;

public class LearnActivity extends AppCompatActivity {

    private RecyclerView mLeftRvRecyclerView;
    private RecyclerView mRightRvRecyclerView;
    private LeftAdapter leftAdapter;
    private RightAdapter rightAdapter;

    private List<CropBean> cropBeanList;
    private List<CropItem> cropItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        mLeftRvRecyclerView = (RecyclerView) findViewById(R.id.main_left_rv);
        mRightRvRecyclerView = (RecyclerView) findViewById(R.id.main_right_rv);
        initData();
        leftAdapter = new LeftAdapter(cropBeanList);
        rightAdapter = new RightAdapter(cropItemList);
        mLeftRvRecyclerView.setAdapter(leftAdapter);
        mRightRvRecyclerView.setAdapter(rightAdapter);
        mLeftRvRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRightRvRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mLeftRvRecyclerView.addOnItemTouchListener(new SimpleClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                CropBean drugBean = cropBeanList.get(i);
                cropItemList.clear();
                cropItemList.addAll(drugBean.getmList());
                leftAdapter.setSelectPos(i);
                leftAdapter.notifyDataSetChanged();
                rightAdapter.notifyDataSetChanged();
            }

            @Override
            public void onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

            }

            @Override
            public void onItemChildLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

            }
        });
    }

    private void initData(){
        cropBeanList = new ArrayList<>();
        cropItemList = new ArrayList<>();
        for(int i = 0;i < ConstantUtils.mList.size(); i++ ){
            DiseaseData data = ConstantUtils.mList.get(i);
            CropBean crop = new CropBean();
            crop.setUrl(data.getImageUrl());
            crop.setTitle(data.getContent());
            crop.setmList(cropItemList);
            cropBeanList.add(crop);
        }
    }
}
