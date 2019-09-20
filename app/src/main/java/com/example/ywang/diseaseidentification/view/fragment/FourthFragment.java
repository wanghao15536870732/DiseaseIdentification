package com.example.ywang.diseaseidentification.view.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.adapter.NineGridAdapter;
import com.example.ywang.diseaseidentification.bean.NineGridModel;

import java.util.ArrayList;
import java.util.List;

public class FourthFragment extends Fragment {

    private View view;
    private LinearLayout commentLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private NineGridAdapter mAdapter;
    private List<NineGridModel> mList = new ArrayList<>();

    public static FourthFragment newInstance(){
        Bundle bundle = new Bundle();
        FourthFragment fragment = new FourthFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fourth,container,false);
        initData();
        initView(view);
        return view;
    }

    private void initView(View view){
        mRecyclerView = (RecyclerView) view.findViewById(R.id.dynamic_recycler_view);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new NineGridAdapter(getContext());
        mAdapter.setList(mList);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData(){
        for (int i = 0;i < 5;i ++){

            NineGridModel model = new NineGridModel();

            for(int j = 0;j < 9;j++) {
                model.urlList.add("https://upload-images.jianshu.io/upload_images/9140378-8529cec786fef4d6.png");
            }
            model.imageUri = "https://upload-images.jianshu.io/upload_images/9140378-2561c9ef7633683e.png";
            model.time = "今天 20:50";
            model.name = "李太阳";
            model.detail = "在外语系学习外语和出国学习外语有何差别，最近在学德语，有没有什么应该注意的问题？";
            mList.add(model);
        }
    }
}
