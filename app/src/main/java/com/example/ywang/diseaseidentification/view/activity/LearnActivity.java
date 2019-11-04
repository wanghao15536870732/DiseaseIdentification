package com.example.ywang.diseaseidentification.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private MultiAdapter mMultiAdapter;
    private List<DiseaseData> mList;
    private TextView submit;
    private CropBean cropBean;

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

        mMultiAdapter = new MultiAdapter(allData);
        mRecyclerView.setAdapter(mMultiAdapter);

        leftAdapter = new LeftAdapter(cropBeanList);
        rightAdapter = new RightAdapter(cropItemList);
        mLeftRvRecyclerView.setAdapter(leftAdapter);
        mRightRvRecyclerView.setAdapter(rightAdapter);
        mLeftRvRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        GridLayoutManager manager = new GridLayoutManager(this,3);
        mRightRvRecyclerView.setLayoutManager(manager);
        initRecyclerView();

        //最终的多选条目
        mMultiAdapter.setOnItemClickListener(new MultiAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(!mMultiAdapter.isSelected.get(position)){
                    mMultiAdapter.isSelected.put(position, true); // 修改map的值保存状态
                    mMultiAdapter.notifyItemChanged(position);
                    selectData.add(allData.get(position));
                }else {
                    mMultiAdapter.isSelected.put(position,false); // 修改map的值保存状态
                    mMultiAdapter.notifyItemChanged(position);
                    selectData.remove(allData.get(position));
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        submit = (TextView) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LearnActivity.this,selectData.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRecyclerView(){
        mLeftRvRecyclerView.addOnItemTouchListener(new SimpleClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                cropBean = cropBeanList.get(i);
                cropItemList.clear();
                cropItemList.addAll(cropBean.getmList());
                leftAdapter.setSelectPos(i);
                leftAdapter.notifyDataSetChanged();
                rightAdapter.notifyDataSetChanged();
                mMultiAdapter.notifyDataSetChanged();
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

        mRightRvRecyclerView.addOnItemTouchListener(new SimpleClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                rightAdapter.setSelectPos(i);
                allData.clear();
                allData.addAll(Arrays.asList(ConstantUtils.Disease[i]));
                mMultiAdapter.init();
                mMultiAdapter.notifyDataSetChanged();
                rightAdapter.notifyDataSetChanged();
                leftAdapter.notifyDataSetChanged();
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
        //加载左列表的全部数据
        mList = new ArrayList<>();
        mList.addAll(Arrays.asList(ConstantUtils.CropList1));
        mList.addAll(Arrays.asList(ConstantUtils.CropList2));
        mList.addAll(Arrays.asList(ConstantUtils.CropList3));
        mList.addAll(Arrays.asList(ConstantUtils.CropList4));

        for(int i = 0;i < mList.size(); i++ ){
            DiseaseData data = mList.get(i);  //获取左边列表的单个作物
            CropBean crop = new CropBean(); // 转化为CropBean
            crop.setUrl(data.getImageUrl());
            crop.setTitle(data.getContent());
            cropItemList = new ArrayList<>();
            cropItemList.addAll(Arrays.asList(ConstantUtils.items));
            crop.setmList(cropItemList);
            cropBeanList.add(crop);  //添加到
        }
        allData = new ArrayList<>();
        allData.addAll(Arrays.asList(ConstantUtils.Disease[0]));
    }
}
