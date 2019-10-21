package com.example.ywang.diseaseidentification.view.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.adapter.TitleAdapter;
import com.example.ywang.diseaseidentification.bean.baseData.DiseaseData;
import com.example.ywang.diseaseidentification.utils.file.ConstantUtils;
import com.example.ywang.diseaseidentification.view.fragment.CropFragment;
import com.youth.banner.transformer.CubeOutTransformer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LearnCropActivity extends AppCompatActivity {

    private TitleAdapter mAdapter;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> mFragments = new ArrayList<>();
    private Toolbar toolbar;
    private String[] mTitles = {"蔬菜","粮棉油","水果","经济作物",};
    private List<DiseaseData> mList = new ArrayList<>();
    private List<DiseaseData> mList2 = new ArrayList<>();
    private List<DiseaseData> mList3 = new ArrayList<>();
    private List<DiseaseData> mList4 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_crop);
        toolbar = (Toolbar) findViewById(R.id.toolbar_crop);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("农作物病害一览");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        initCrops();
        mFragments.add(CropFragment.newInstance(mList));
        mFragments.add(CropFragment.newInstance(mList4));
        mFragments.add(CropFragment.newInstance(mList3));
        mFragments.add(CropFragment.newInstance(mList2));
        mViewPager = (ViewPager) findViewById( R.id.viewPager_crop );
        mTabLayout = (TabLayout) findViewById( R.id.tabLayout_crop);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mAdapter = new TitleAdapter(getSupportFragmentManager(), mFragments, mTitles);
        mViewPager.setAdapter(mAdapter);
        //CubeInTransformer 内旋
        //FlipHorizontalTransformer 像翻书一样
        //AccordionTransformer  风琴 拉压
        mViewPager.setPageTransformer(true,new CubeOutTransformer());
        mViewPager.setCurrentItem(0);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initCrops(){
        mList.addAll(Arrays.asList(ConstantUtils.CropList1));
        mList2.addAll(Arrays.asList(ConstantUtils.CropList2));
        mList3.addAll(Arrays.asList(ConstantUtils.CropList3));
        mList4.addAll(Arrays.asList(ConstantUtils.CropList4));
    }
}
