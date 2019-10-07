package com.example.ywang.diseaseidentification.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.adapter.LeftAdapter;
import com.example.ywang.diseaseidentification.adapter.RightAdapter;
import com.example.ywang.diseaseidentification.bean.CropBean;
import com.example.ywang.diseaseidentification.bean.CropItem;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    private RecyclerView mLeftRvRecyclerView;
    private RecyclerView mRightRvRecyclerView;
    private LeftAdapter leftAdapter;
    private RightAdapter rightAdapter;

    private List<CropBean> cropBeanList;
    private List<CropItem> cropItemList;

    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        mLeftRvRecyclerView = (RecyclerView) view.findViewById(R.id.main_left_rv);
        mRightRvRecyclerView = (RecyclerView) view.findViewById(R.id.main_right_rv);
        initData();
        leftAdapter = new LeftAdapter(cropBeanList);
        rightAdapter = new RightAdapter(cropItemList);
        mLeftRvRecyclerView.setAdapter(leftAdapter);
        mRightRvRecyclerView.setAdapter(rightAdapter);
        mLeftRvRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRightRvRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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
        return view;
    }

    private void initData(){
        cropBeanList = new ArrayList<>();
        cropItemList = new ArrayList<>();
        CropBean cropBean = new CropBean();
        cropBean.setTitle("玉米常患病");
        CropItem item = new CropItem("https://gss1.bdstatic.com/9vo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike180%2C5%2C5%2C180%2C60/sign=bde395b9e6cd7b89fd6132d16e4d29c2/728da9773912b31bd9b5585a8618367adbb4e1ec.jpg",
                "大斑病","");
        cropItemList.add(item);
        CropItem item1 = new CropItem("https://gss1.bdstatic.com/9vo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike180%2C5%2C5%2C180%2C60/sign=bde395b9e6cd7b89fd6132d16e4d29c2/728da9773912b31bd9b5585a8618367adbb4e1ec.jpg",
                "轮纹病","");
        cropItemList.add(item1);
        CropItem item2 = new CropItem("https://gss2.bdstatic.com/-fo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike272%2C5%2C5%2C272%2C90/sign=0ba766569325bc313f5009ca3fb6e6d4/fc1f4134970a304e034d1362dbc8a786c9175c3e.jpg",
                "霜霉病","");
        cropItemList.add(item2);
        CropItem item3 = new CropItem("http://a1.att.hudong.com/17/62/01300000582386130208621125243.jpg",
                "穗腐病","");
        cropBean.setmList(cropItemList);
        cropBeanList.add(cropBean);
    }

}
