package com.example.ywang.diseaseidentification.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.bean.baseData.NewsBean;
import com.example.ywang.diseaseidentification.utils.WebUtil;
import com.example.ywang.diseaseidentification.view.activity.LearnActivity;
import com.example.ywang.diseaseidentification.view.activity.LearnCropActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private List<String> images = new ArrayList<>();
    private Banner banner;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NewsListAdapter adapter;
    private List<NewsBean> mNewsBeans = new ArrayList<>();

    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        images.add("https://upload-images.jianshu.io/upload_images/9140378-3e03388792e59668.png");
        images.add("http://222.211.94.245:8084/tyfoSrvEx/imagedeal?path=T1qEhbBCZT1R4IlZUK.jpeg");
        images.add("https://upload-images.jianshu.io/upload_images/9140378-fee642eb9d12409f.png");
        view.findViewById(R.id.learn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivity(new Intent(getActivity(), LearnCropActivity.class));
            }
        });
        view.findViewById(R.id.self_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivity(new Intent(getActivity(), LearnActivity.class));
            }
        });
        banner = (Banner) view.findViewById(R.id.banner);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.start();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_main);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout_main);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorPrimaryDark, R.color.colorAccent);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        });

        adapter = new NewsListAdapter(mNewsBeans);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        return view;
    }

    private void init(){
        mNewsBeans.add(new NewsBean(0,"新华社","2019-08-30","2019年农业补贴政策","123",
                "http://upload.pig66.com/toutiao/1952035-20fd1903d35c2d5e3b53ed56d75ef272?x-oss-process=image/resize,m_lfit,w_500,limit_1/auto-orient,1/quality,Q_90","https://zhuanlan.zhihu.com/p/63290647"));
        mNewsBeans.add(new NewsBean(1,"山东财政","2019-07-26","财政惠农暖人心！山东印发省级农业政策补贴和粮食风险基金管理暂行办法 ",
                "123","http://5b0988e595225.cdn.sohucs.com/images/20190727/7a10ac43ade343e88735cec43d14d656.jpeg","http://www.sohu.com/a/329618719_99958888"));
        mNewsBeans.add(new NewsBean(2,"农家参谋","2019-04-1","2019年农业有“三增”政策，种植这3类作物不愁销路 ",
                "123","http://dingyue.ws.126.net/jeDwB7=5uKVevbtI41GzDLGvCD3QMxFrRQorR6BZneXGR1554088348876compressflag.png","http://www.sohu.com/a/307085673_279353"));
        mNewsBeans.add(new NewsBean(3,"","","2019年农业补贴最新政策 农业补贴什么时候发放？ ","123",
                "https://cn.bing.com/th?id=OIP.GYLrYUNB1tKoqjumP4GM2wAAAA&pid=Api&rs=1","http://www.pig66.com/huinong/2019/0603/18011296.html"));

    }

    @Override
    public void onRefresh() {
        mNewsBeans.clear();
        init();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */

            //Glide 加载图片简单用法
            Glide.with(context).load(path).into(imageView);

            //用fresco加载图片简单用法，记得要写下面的createImageView方法
            Uri uri = Uri.parse((String) path);
            imageView.setImageURI(uri);
        }

        //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
        @Override
        public ImageView createImageView(Context context) {
            //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
            ImageView simpleDrawView = new ImageView(context);
            return simpleDrawView;
        }
    }

    class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder>{

        private Context mContext;
        private List<NewsBean> mNewsList;

        NewsListAdapter(List<NewsBean> items){
            this.mNewsList = items;
        }

        @Override
        public NewsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news,parent,false);
            final NewsListAdapter.ViewHolder holder = new NewsListAdapter.ViewHolder(view);
            mContext = parent.getContext();
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getAdapterPosition();
                    NewsBean newsBean = mNewsList.get(position);
                    WebUtil.openWeb(mContext, newsBean.getTitle(), newsBean.getMain_url(),newsBean.getContent());
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            NewsBean newsBean = mNewsList.get(position);
            holder.time.setText( newsBean.getTime() );
            holder.author.setText( newsBean.getAuthor() );
            holder.title.setText( newsBean.getTitle() );
            Glide.with(mContext).load( newsBean.getUrl() ).into( holder.mImageView );
        }

        @Override
        public int getItemCount() {
            return mNewsBeans.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            private TextView title,time,author;
            private ImageView mImageView;

            public ViewHolder(View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById( R.id.item_news_title );
                author = (TextView) itemView.findViewById( R.id.item_news_source );
                time = (TextView) itemView.findViewById( R.id.item_news_date );
                mImageView = (ImageView) itemView.findViewById( R.id.img_news);
            }
        }
    }
}
