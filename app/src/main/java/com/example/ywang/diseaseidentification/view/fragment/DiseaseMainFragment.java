package com.example.ywang.diseaseidentification.view.fragment;

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
import com.example.ywang.diseaseidentification.bean.baseData.DiseaseData;
import com.example.ywang.diseaseidentification.utils.file.ConstantUtils;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.youth.banner.transformer.AccordionTransformer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DiseaseMainFragment extends Fragment implements TabLayout.OnTabSelectedListener {

    private TitleAdapter mAdapter;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private String[] mTitles = {"蔬菜", "粮棉油", "水果", "经济作物"};
    private List<DiseaseData> mList = new ArrayList<>();
    private List<DiseaseData> mList2 = new ArrayList<>();
    private List<DiseaseData> mList3 = new ArrayList<>();
    private List<DiseaseData> mList4 = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();


    public static DiseaseMainFragment newInstance() {
        Bundle bundle = new Bundle();
        DiseaseMainFragment fragment = new DiseaseMainFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_disease_main, container, false);
        initCrops();
        mFragments.add(CropFragment.newInstance(mList, 6));
        mFragments.add(CropFragment.newInstance(mList4, 6));
        mFragments.add(CropFragment.newInstance(mList3, 6));
        mFragments.add(CropFragment.newInstance(mList2, 6));
        mViewPager = view.findViewById(R.id.vp_content);
        mTabLayout = view.findViewById(R.id.tl_tabs);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mAdapter = new TitleAdapter(getActivity().getSupportFragmentManager(), mFragments, mTitles);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setPageTransformer(true, new AccordionTransformer());
        mViewPager.setCurrentItem(0);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setOnTabSelectedListener(this);
        mTabLayout.setupWithViewPager(mViewPager);
        return view;
    }

    private void initCrops() {
        mList.addAll(Arrays.asList(ConstantUtils.CropList1));
        mList2.addAll(Arrays.asList(ConstantUtils.CropList2));
        mList3.addAll(Arrays.asList(ConstantUtils.CropList3));
        mList4.addAll(Arrays.asList(ConstantUtils.CropList4));
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        if (null == view) {
            tab.setCustomView(R.layout.tab_layout_text);
        }
        final TextView new_view = tab.getCustomView().findViewById(android.R.id.text1);
        ObjectAnimator anim = ObjectAnimator
                .ofFloat(new_view, "", 1.0F, 1.2F)
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
                .ofFloat(new_view, "", 1.0F, 1.0F)
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
}
