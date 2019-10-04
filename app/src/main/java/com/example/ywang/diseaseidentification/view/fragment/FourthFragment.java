package com.example.ywang.diseaseidentification.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.adapter.NineGridAdapter;
import com.example.ywang.diseaseidentification.bean.NineGridModel;
import com.example.ywang.diseaseidentification.bean.baseData.DynamicBean;
import com.example.ywang.diseaseidentification.view.CommentListTextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FourthFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout SwipeRefreshLayout;
    private NineGridAdapter mAdapter;
    private List<NineGridModel> mList = new ArrayList<>();
    public boolean flag = false;
    final List<DynamicBean> list = new ArrayList<>();


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
        SwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.scrollView_dynamic);
        SwipeRefreshLayout.setOnRefreshListener(this);
        SwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                SwipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        });
        mLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new NineGridAdapter(getContext());
        mAdapter.setList(mList);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.scrollToPosition(mLayoutManager.findLastVisibleItemPosition());

    }

    private void initData(){
//        mList.clear();
        get_all();

    }

    public void get_all(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("http://101.37.79.26:8080/show/GetAllServlet");
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("POST");
                    //设置连接超时和读取超时的毫秒数
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);

                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    String line;
                    list.clear();
                    while ((line = reader.readLine()) != null) {
                        DynamicBean bean = new DynamicBean();
                        bean.setUser(line);
                        bean.setContent(reader.readLine());
                        bean.setTime(reader.readLine());
                        bean.setType(reader.readLine());
                        int num = reader.readLine().charAt(0) - '0';
                        bean.setImg_num(num);
                        for (int i = 0; i < num; i++) {
                            bean.url.add(reader.readLine());
                        }
                        list.add(bean);
                    }
                    flag = true;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if(reader!=null){
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
        if(flag){
            flag = false;
            mList.clear();
            for (int i = 0;i < list.size();i ++) {
                DynamicBean bean = list.get(i);
                NineGridModel model = new NineGridModel();
                for (int j = 0; j < bean.getImg_num(); j++) {
                    model.urlList.add(bean.getUrl().get(j));
                }
                model.imageUri = "https://upload-images.jianshu.io/upload_images/9140378-2561c9ef7633683e.png";
                model.time = bean.getTime();
                model.name = bean.getUser();
                model.detail = bean.getContent();
                List<CommentListTextView.CommentInfo> mCommentInfos = new ArrayList<> ();
                if(i == list.size() - 1){
                    mCommentInfos.add (new CommentListTextView.CommentInfo ().setID (1111).setComment ("你这个应该多浇水，我家的葡萄也这样").setNickname ("李建军").setTonickname ("赵四"));
                    mCommentInfos.add (new CommentListTextView.CommentInfo ().setID (2222).setComment ("浇过水了，没有效果...").setNickname ("我"));
                    mCommentInfos.add (new CommentListTextView.CommentInfo ().setID (3333).setComment ("应该不是这个问题").setNickname ("农民7436").setTonickname ("李建军"));
                    model.mCommentInfos = mCommentInfos;
                }else if (i == list.size() - 2){
                    mCommentInfos.add (new CommentListTextView.CommentInfo ().setID (1111).setComment ("看照片上你这片玉米这是得了穗腐病了？").setNickname ("农民2501").setTonickname ("农民7436"));
                    mCommentInfos.add (new CommentListTextView.CommentInfo ().setID (2222).setComment ("哎，一言难尽啊～这病传染太快了。").setNickname ("农民7436"));
                    mCommentInfos.add (new CommentListTextView.CommentInfo ().setID (3333).setComment ("赶紧打药吧，成熟后记得及时采收").setNickname ("农民2501").setTonickname ("农民7436"));
                    model.mCommentInfos = mCommentInfos;
                }
                mList.add(model);
            }




        }
    }

    @Override
    public void onRefresh() {
        mList.clear();
        get_all();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
                SwipeRefreshLayout.setRefreshing(false);
                mRecyclerView.smoothScrollToPosition(mList.size() + 1);
            }
        });
    }
}
