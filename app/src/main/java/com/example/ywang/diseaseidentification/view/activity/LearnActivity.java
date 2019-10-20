package com.example.ywang.diseaseidentification.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.adapter.disease.LeftAdapter;
import com.example.ywang.diseaseidentification.adapter.disease.MultiAdapter;
import com.example.ywang.diseaseidentification.adapter.disease.RightAdapter;
import com.example.ywang.diseaseidentification.bean.CropBean;
import com.example.ywang.diseaseidentification.bean.CropItem;
import com.example.ywang.diseaseidentification.bean.baseData.DiseaseData;
import com.example.ywang.diseaseidentification.utils.file.ConstantUtils;

import java.util.ArrayList;
import java.util.List;

public class LearnActivity extends AppCompatActivity {

    private RecyclerView mLeftRvRecyclerView;
    private RecyclerView mRightRvRecyclerView;
    private LeftAdapter leftAdapter;
    private RightAdapter rightAdapter;

    private List<CropBean> cropBeanList;
    private List<CropItem> cropItemList;
    private ImageView back;

    private RecyclerView mRecyclerView;
    private List<String> allData;
    private List<String> selectData = new ArrayList<>();
    private MultiAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        mLeftRvRecyclerView = (RecyclerView) findViewById(R.id.main_left_rv);
        mRightRvRecyclerView = (RecyclerView) findViewById(R.id.main_right_rv);
        mRecyclerView = findViewById(R.id.main_multi_check);
        initData();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MultiAdapter(allData);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MultiAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(!mAdapter.isSelected.get(position)){
                    mAdapter.isSelected.put(position, true); // 修改map的值保存状态
                    mAdapter.notifyItemChanged(position);
                    selectData.add(allData.get(position));

                }else {
                    mAdapter.isSelected.put(position, false); // 修改map的值保存状态
                    mAdapter.notifyItemChanged(position);
                    selectData.remove(allData.get(position));
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        leftAdapter = new LeftAdapter(cropBeanList);
        rightAdapter = new RightAdapter(cropItemList);
        mLeftRvRecyclerView.setAdapter(leftAdapter);
        mRightRvRecyclerView.setAdapter(rightAdapter);
        mLeftRvRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        GridLayoutManager manager = new GridLayoutManager(this,3);
        mRightRvRecyclerView.setLayoutManager(manager);

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
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void initData(){
        cropBeanList = new ArrayList<>();
        for(int i = 0;i < ConstantUtils.mList.size(); i++ ){
            DiseaseData data = ConstantUtils.mList.get(i);
            CropBean crop = new CropBean();
            crop.setUrl(data.getImageUrl());
            crop.setTitle(data.getContent());
            cropItemList = new ArrayList<>();
            cropItemList.add(new CropItem("https://upload-images.jianshu.io/upload_images/9140378-a5428a598e98769d.png","叶"));
            cropItemList.add(new CropItem("https://upload-images.jianshu.io/upload_images/9140378-be3f3fef607e7526.png","花"));
            cropItemList.add(new CropItem("https://upload-images.jianshu.io/upload_images/9140378-404409164ec6d8d1.png","根"));
            cropItemList.add(new CropItem("https://upload-images.jianshu.io/upload_images/9140378-523faa0544cb8971.png","茎"));
            cropItemList.add(new CropItem("https://upload-images.jianshu.io/upload_images/9140378-b5fdc46a7bc0b7bd.png","果"));
            cropItemList.add(new CropItem("https://upload-images.jianshu.io/upload_images/9140378-6528a63eee161045.png","植株"));
            crop.setmList(cropItemList);
            cropBeanList.add(crop);
        }
        allData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            allData.add("测试" + i);
        }
    }
}
