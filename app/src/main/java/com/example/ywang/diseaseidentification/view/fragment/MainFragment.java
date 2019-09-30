package com.example.ywang.diseaseidentification.view.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.adapter.DiseasesAdapter;
import com.example.ywang.diseaseidentification.bean.baseData.DiseaseData;
import com.example.ywang.diseaseidentification.view.activity.PanoramaActivity;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainFragment extends Fragment {

    //下拉刷新列表
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private DiseasesAdapter adapter;
    private List<DiseaseData> mList = new ArrayList<>();

    private DiseaseData[] datas = {
            new DiseaseData("大斑病","https://gss1.bdstatic.com/9vo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike180%2C5%2C5%2C180%2C60/sign=bde395b9e6cd7b89fd6132d16e4d29c2/728da9773912b31bd9b5585a8618367adbb4e1ec.jpg",
                    "https://baike.baidu.com/item/%E7%8E%89%E7%B1%B3%E5%A4%A7%E6%96%91%E7%97%85/2203039"),
            new DiseaseData("轮纹病","https://gss2.bdstatic.com/-fo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike933%2C5%2C5%2C933%2C330/sign=45c0f254fe36afc31a013737d27080a1/ac6eddc451da81cb7f543c595b66d016082431f7.jpg",
                    "https://baike.baidu.com/item/%E8%BD%AE%E7%BA%B9%E7%97%85"),
            new DiseaseData("霜霉病","https://gss2.bdstatic.com/-fo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike272%2C5%2C5%2C272%2C90/sign=0ba766569325bc313f5009ca3fb6e6d4/fc1f4134970a304e034d1362dbc8a786c9175c3e.jpg",
                    "https://baike.baidu.com/item/%E9%9C%9C%E9%9C%89%E7%97%85"),
            new DiseaseData("穗腐病","http://a1.att.hudong.com/17/62/01300000582386130208621125243.jpg",
                    "https://baike.baidu.com/item/%E7%A9%97%E8%85%90%E7%97%85"),
    };

    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        //网格式布局，产生2列数据
        initDiseases();
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
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
        return view;
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
                getActivity().runOnUiThread(new Runnable() {
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
        for (int i = 0; i < 4; i++) {
            Random random = new Random();
            int index = random.nextInt(datas.length);
            mList.add(datas[index]);
        }
    }
}
