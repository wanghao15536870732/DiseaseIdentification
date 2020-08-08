package com.example.ywang.diseaseidentification.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.bean.baseData.NewsBean;
import com.example.ywang.diseaseidentification.utils.network.WebUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NewsListAdapter adapter;
    private List<NewsBean> mNewsBeans = new ArrayList<>();

    public static NewsFragment newInstance(String mUrl) {
        Bundle bundle = new Bundle();
        bundle.putString("url", mUrl);
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private String mUrl;
    private boolean mIsRefreshing = false;
    private NewsBean[] news = new NewsBean[100];

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mUrl = bundle.getString("url");
        }
        recyclerView = view.findViewById(R.id.recyclerView_news);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout_news);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorPrimaryDark, R.color.colorAccent);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                initData();
                swipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        });
        adapter = new NewsListAdapter(mNewsBeans);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return mIsRefreshing;
            }
        });
        return view;
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL(mUrl);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    //设置连接超时和读取超时的毫秒数
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);

                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    String line;
                    mNewsBeans.clear();
                    mIsRefreshing = true;
                    Log.e("newsbean ", String.valueOf(mNewsBeans.size()));
                    while ((line = reader.readLine()) != null) {
                        int num = line.charAt(0) - '0';
                        String author = reader.readLine();
                        String time = reader.readLine();
                        String title = reader.readLine();
                        String content = reader.readLine();
                        String img_url = reader.readLine();
                        String main_url = reader.readLine();
                        NewsBean newsBean = new NewsBean(num, author, time, title, content, img_url, main_url);
                        news[num - 1] = newsBean;
                        mNewsBeans.add(newsBean);
                    }
                    Log.e("newsbean", String.valueOf(mNewsBeans.size()));
                    mIsRefreshing = false;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    @Override
    public void onRefresh() {
        mNewsBeans.clear();
        mIsRefreshing = true;
        initData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                        mIsRefreshing = false;
                    }
                });
            }
        }).start();
    }

    class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

        private Context mContext;
        private List<NewsBean> mNewsList;

        NewsListAdapter(List<NewsBean> items) {
            this.mNewsList = items;
        }

        @NonNull
        @Override
        public NewsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
            final ViewHolder holder = new ViewHolder(view);
            mContext = parent.getContext();
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getAdapterPosition();
                    NewsBean newsBean = mNewsList.get(position);
                    WebUtil.openWeb(mContext, newsBean.getTitle(), newsBean.getMain_url(), newsBean.getContent());
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            NewsBean newsBean = mNewsList.get(position);
            holder.time.setText(newsBean.getTime());
            holder.author.setText(newsBean.getAuthor());
            holder.title.setText(newsBean.getTitle());
            Glide.with(mContext).load(newsBean.getUrl()).into(holder.mImageView);
        }

        @Override
        public int getItemCount() {
            return mNewsBeans.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView title, time, author;
            private ImageView mImageView;

            ViewHolder(View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.item_news_title);
                author = itemView.findViewById(R.id.item_news_source);
                time = itemView.findViewById(R.id.item_news_date);
                mImageView = itemView.findViewById(R.id.img_news);
            }
        }
    }
}
