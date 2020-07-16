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
import com.youth.banner.transformer.AccordionTransformer;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements TabLayout.OnTabSelectedListener{

    private List<String> images = new ArrayList<>();
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
        mFragments.add(NewsFragment.newInstance("http://121.199.19.77:8080/show/GetNewsThreeServlet"));
        mFragments.add(NewsFragment.newInstance("http://121.199.19.77:8080/show/GetNewsThreeServlet"));
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
        images.add("https://upload-images.jianshu.io/upload_images/9140378-3e03388792e59668.png");
        images.add("http://img8.agronet.com.cn/Users/100/617/663/2019911901105591.jpg");
        images.add("http://www.moa.gov.cn/xw/shipin/202005/W020200521362744644607.png");
        Banner banner = view.findViewById(R.id.banner_main);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new AgricultureNewsFragment.GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
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
