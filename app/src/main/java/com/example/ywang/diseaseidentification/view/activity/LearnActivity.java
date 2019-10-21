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
import java.util.Arrays;
import java.util.List;

public class LearnActivity extends AppCompatActivity {

    private RecyclerView mLeftRvRecyclerView;
    private RecyclerView mRightRvRecyclerView;
    private LeftAdapter leftAdapter;
    private RightAdapter rightAdapter;

    private List<CropBean> cropBeanList;
    private List<CropItem> cropItemList;

    private RecyclerView mRecyclerView;
    private List<String> allData;
    private List<String> selectData = new ArrayList<>();
    private MultiAdapter mAdapter;
    private List<DiseaseData> mList;

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
                    mAdapter.isSelected.put(position,false); // 修改map的值保存状态
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
                CropBean cropBean = cropBeanList.get(i);
                cropItemList.clear();
                cropItemList.addAll(cropBean.getmList());
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
        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void initData(){
        cropBeanList = new ArrayList<>();
        mList = new ArrayList<>();
        mList.addAll(Arrays.asList(ConstantUtils.CropList1));
        mList.addAll(Arrays.asList(ConstantUtils.CropList2));
        mList.addAll(Arrays.asList(ConstantUtils.CropList3));
        mList.addAll(Arrays.asList(ConstantUtils.CropList4));
        for(int i = 0;i < mList.size(); i++ ){
            DiseaseData data = mList.get(i);
            CropBean crop = new CropBean();
            crop.setUrl(data.getImageUrl());
            crop.setTitle(data.getContent());
            cropItemList = new ArrayList<>();
            cropItemList.addAll(Arrays.asList(ConstantUtils.items));
            crop.setmList(cropItemList);
            cropBeanList.add(crop);
        }
        allData = new ArrayList<>();
        allData.add("病斑");
        allData.add("黑褐色");
        allData.add("枯死");
        allData.add("水渍状斑点");
        allData.add("暗绿色");
        allData.add("坏死大斑");
        allData.add("脱落");
        allData.add("畸形");
        allData.add("轮纹状");
        allData.add("灰褐色霉");
        allData.add("黄褐色病斑");
        allData.add("圆形或近圆形病斑");
        allData.add("油渍状");
        allData.add("枯萎");
        allData.add("破裂病斑");
        allData.add("病斑密布");
        allData.add("粘液");
        allData.add("不规则病斑");
        allData.add("褐色病斑");
        allData.add("凹陷病斑");
        allData.add("棉絮状菌丝体");
        for (int i = 0; i < 20; i++) {
            allData.add("测试" + i);
        }
    }
}
