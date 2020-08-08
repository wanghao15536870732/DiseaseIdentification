package com.example.ywang.diseaseidentification.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.adapter.TitleAdapter;
import com.example.ywang.diseaseidentification.view.activity.AddDynamicActivity;
import com.example.ywang.diseaseidentification.view.activity.LearnActivity;
import com.example.ywang.diseaseidentification.view.activity.LearnCropActivity;
import com.example.ywang.diseaseidentification.view.activity.RobotActivity;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.AccordionTransformer;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements TabLayout.OnTabSelectedListener{

    private List<String> images = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private String[] mTitles = {"问答","政策","病虫害库"};
    private List<Fragment> mFragments = new ArrayList<>();
    private TabLayout tabLayout;

    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        initBanner(view);
        initItemOnclick(view);
        tabLayout = view.findViewById(R.id.main_tab_layout);
        ViewPager viewPager = view.findViewById(R.id.home_viewpager);
        mFragments.add(NewsFragment.newInstance("http://121.199.19.77:8080/show/GetNewsTwoServlet"));
        mFragments.add(NewsFragment.newInstance("http://121.199.19.77:8080/test/GetZhengceServlet"));
        mFragments.add(DiseaseMainFragment.newInstance());

        TitleAdapter mAdapter = new TitleAdapter(getFragmentManager(), mFragments, mTitles);
        viewPager.setAdapter(mAdapter);
        viewPager.setPageTransformer(true,new AccordionTransformer());
        viewPager.setCurrentItem(0);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setOnTabSelectedListener(this);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        if (null == view) {
            tab.setCustomView(R.layout.tab_layout_text);
        }
        final TextView new_view = tab.getCustomView().findViewById(android.R.id.text1);
        ObjectAnimator anim = ObjectAnimator
                .ofFloat(new_view, "", 1.0F, 1.4F)
                .setDuration(200);
        anim.start();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                new_view.setTextSize(16 * cVal);
            }
        });
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        if (null == view) {
            tab.setCustomView(R.layout.tab_layout_text);
        }
        final TextView new_view =  tab.getCustomView().findViewById(android.R.id.text1);
        ObjectAnimator anim = ObjectAnimator
                .ofFloat(new_view, "", 1.0F, 0.9F)
                .setDuration(200);
        anim.start();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                new_view.setTextSize(16 * cVal);
            }
        });
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void initBanner(View view){
        images.add("http://www.ippcaas.cn/images/content/2020-06/20200618162443169644.jpg");
        images.add("http://www.vanzol.com/uploads/allimg/20200716/1594878594680680.png");
        images.add("https://s1.ax1x.com/2020/07/17/UyGo8A.md.jpg");
        images.add("https://s1.ax1x.com/2020/07/17/Uy0iAx.md.png");
        titles.add("植物病虫害生物学国家重点实验室2019年度学术委员会年会成功召开");
        titles.add("厦门果蔬农业展将于2020年9月8日-11日（四天）在厦门会展中心隆重举办");
        titles.add("40种农作物常见病虫害症状及防治方法");
        titles.add("马铃薯晚疫病全国生长分布图");
        Banner banner = view.findViewById(R.id.banner_main);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new AgricultureNewsFragment.GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        banner.setBannerTitles(titles);
        //banner设置方法全部调用完毕时最后调用
        banner.setBannerAnimation(Transformer.DepthPage);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置轮播时间
        banner.setDelayTime(2500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });
        banner.start();
    }


    private void initItemOnclick(View view){
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
        view.findViewById(R.id.online).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivity(new Intent(getActivity(), RobotActivity.class));
            }
        });
        view.findViewById(R.id.dynamic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dynamic_itent = new Intent(getActivity(), AddDynamicActivity.class);
                getActivity().startActivityForResult(dynamic_itent,222);
            }
        });
    }
}
