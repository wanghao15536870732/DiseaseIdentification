package com.example.ywang.diseaseidentification.view.fragment;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.adapter.agricultural.NineGridAdapter;
import com.example.ywang.diseaseidentification.bean.NineGridModel;
import com.example.ywang.diseaseidentification.bean.baseData.DynamicBean;

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
    private SwipeRefreshLayout swipeRefreshLayout;
    private NineGridAdapter mAdapter;
    private String postUrl = "http://121.199.19.77:8080/test/GetAllServlet";

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
        initView(view);
        return view;
    }

    private void initView(View view){
        mRecyclerView = view.findViewById(R.id.dynamic_recycler_view);
        mAdapter = new NineGridAdapter(getContext());
        mLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,true);
        mLayoutManager.setStackFromEnd(true);
        swipeRefreshLayout = view.findViewById(R.id.scrollView_dynamic);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        new DataAsync().execute(postUrl);
    }

    @SuppressLint("StaticFieldLeak")
    class DataAsync extends AsyncTask<String,String,List<DynamicBean>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            swipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected List<DynamicBean> doInBackground(String... strings) {
            List<DynamicBean> dynamicList = new ArrayList<>();
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("contentType", "utf-8");
                //设置连接超时和读取超时的毫秒数
                connection.setConnectTimeout(8000);
                connection.setReadTimeout(8000);

                InputStream in = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    DynamicBean bean = new DynamicBean();
                    bean.setDynamic_id(line);
                    bean.setUser(reader.readLine());
                    bean.setContent(reader.readLine());
                    bean.setTime(reader.readLine());
                    bean.setType(reader.readLine());
                    int num = reader.readLine().charAt(0) - '0';
                    bean.setImg_num(num);
                    for (int i = 0; i < num; i++) {
                        bean.url.add(reader.readLine());
                    }
                    dynamicList.add(bean);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(reader != null){
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
            return dynamicList;
        }

        @Override
        protected void onPostExecute(List<DynamicBean> dynamicBeans) {
            super.onPostExecute(dynamicBeans);
            List<NineGridModel> mList = new ArrayList<>();
            for (int i = 0;i < dynamicBeans.size();i ++) {
                DynamicBean bean = dynamicBeans.get(i);
                NineGridModel model = new NineGridModel();
                for (int j = 0; j < bean.getImg_num(); j++) {
                    model.urlList.add(bean.getUrl().get(j));
                }
                model.setImageUri("https://upload-images.jianshu.io/" +
                        "upload_images/9140378-2561c9ef7633683e.png");
                model.setTime(bean.getTime());
                model.setName(bean.getUser());
                model.setDetail(bean.getContent());
                mList.add(model);
            }

            mAdapter.setList(mList);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1);
            swipeRefreshLayout.setRefreshing(false);
        }
    }


    @Override
    public void onRefresh() {
        new DataAsync().execute(postUrl);
    }
}
