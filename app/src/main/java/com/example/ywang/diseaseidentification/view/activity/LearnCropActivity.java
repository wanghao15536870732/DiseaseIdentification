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
        ConstantUtils.mList = mList;
        ConstantUtils.mList2 = mList2;
        ConstantUtils.mList3 = mList3;
        ConstantUtils.mList4 = mList4;
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
        mList.add(new DiseaseData("白菜","http://img4.agronet.com.cn/Users/100/161/960/20111014203409664.jpg","baicai"));
        mList.add(new DiseaseData("菠菜","https://cn.bing.com/th?id=OIP.WJdzeRI-0mzdZfLiUQ7x5AHaGM&pid=Api&rs=1","bocai"));
        mList.add(new DiseaseData("葱","http://www.danbaoli-blog.cn/wp-content/uploads/2014/05/%E8%91%B1.jpg","cong"));
        mList.add(new DiseaseData("冬瓜","http://www.nren8.com/uploads/allimg/c160517/14634433QD0-3Y07.jpg","donggua"));
        mList.add(new DiseaseData("番茄","https://cn.bing.com/th?id=OIP.rQHM7AeukvOzlJb881poAAHaFV&pid=Api&rs=1","fanqie"));
        mList.add(new DiseaseData("胡萝卜","http://pic.nipic.com/2007-12-26/20071226115243355_2.jpg","huluobo"));
        mList.add(new DiseaseData("花椰菜","https://pic.pimg.tw/djwang714/1522465865-1481450252.jpg","huayecai"));
        mList.add(new DiseaseData("黄瓜","https://i3.meishichina.com/attachment/201405/6/13993619805178.jpg","huanggua"));
        mList.add(new DiseaseData("芥菜","https://cn.bing.com/th?id=OIP.EsyYdiSUyNIcz3NoNNtkqQHaHa&pid=Api&rs=1","qicai"));
        mList.add(new DiseaseData("韭菜","https://image.tcmwiki.com/image/%E9%9F%AD%E8%8F%9C/%E9%9F%AD%E8%8F%9C.jpg","jiucai"));
        mList.add(new DiseaseData("苦瓜","https://cn.bing.com/th?id=OIP.Vra2ca7lhgQVK_8va2dKOgHaE7&pid=Api&rs=1","kugua"));
        mList.add(new DiseaseData("辣椒","http://pic47.nipic.com/20140905/10395918_204118362000_2.jpg","lajiao"));
        mList.add(new DiseaseData("芦荟","https://cn.bing.com/th?id=OIP.vSwxH5iwvTgznzeX-x-AOwHaFj&pid=Api&rs=1","luhui"));
        mList.add(new DiseaseData("萝卜","http://s1.cdn.xiangha.com/shicai/201508/071034157627.jpg/MTAwMHgw","luobo"));
        mList.add(new DiseaseData("南瓜","http://pic8.nipic.com/20100623/2419558_212717027825_2.jpg","nangua"));
        mList.add(new DiseaseData("茄子","https://cn.bing.com/th?id=OIP.W7wtukTpIHdOzkWPQHML-QHaEz&pid=Api&rs=1","qiezi"));
        mList.add(new DiseaseData("芹菜","https://img3.utuku.china.com/500x0/ent/20180330/842389a7-e797-4d52-a35f-7b2bc3b297b9.jpg","qincai"));
        mList.add(new DiseaseData("丝瓜","https://cn.bing.com/th?id=OIP.peqdszbb-5l8HoEoE4TPWgHaE7&pid=Api&rs=1","sigua"));
        mList.add(new DiseaseData("蒜","http://pichk.daydaycook.com/production/images/20171204/146d0211-ebd6-444e-b315-77ac69bdc607","suan"));
        mList.add(new DiseaseData("甜椒","https://cn.bing.com/th?id=OIP.tkS02qLWVhpIkr0EuSZHdgHaFj&pid=Api&rs=1","tianjiao"));
        mList.add(new DiseaseData("豌豆","http://img.juimg.com/tuku/yulantu/130805/328505-130P5222T277.jpg","wandou"));
        mList.add(new DiseaseData("莴苣","https://cn.bing.com/th?id=OIP.4LqxEDsg_7PJ9L137qCHFQHaFE&pid=Api&rs=1","woju"));
        mList.add(new DiseaseData("西葫芦","https://www.zhifure.com/upload/images/2018/1/12155342782.jpg","xihulu"));
        mList.add(new DiseaseData("香菜","http://www.39ynt.com/doe/js/php/upload/20171129/15119542744622.jpeg","xiangcai"));
        mList.add(new DiseaseData("洋葱","http://sdbdfyy.com/uploads/allimg/120327/1-12032GRG2V1.jpg","yangcong"));
        mList.add(new DiseaseData("芥蓝","http://pic.baike.soso.com/p/20140319/20140319135542-1237064598.jpg","jielan"));
        mList.add(new DiseaseData("莲藕","https://cn.bing.com/th?id=OIP.GpB7f1uzpE8B0kQkc2whDAHaFj&pid=Api&rs=1","lianou"));
        mList.add(new DiseaseData("生菜","https://www.zhifure.com/upload/images/2018/1/22163742153.jpg","shengcai"));
        mList.add(new DiseaseData("茴香","https://www.zhifure.com/upload/images/2018/1/29163950835.jpg","hunxiang"));
        mList.add(new DiseaseData("甜菜","http://5b0988e595225.cdn.sohucs.com/images/20181217/8e72863e295d4fecb3a551164e7598eb.jpeg","tiancai"));
        mList.add(new DiseaseData("小白菜","https://cn.bing.com/th?id=OIP.SylOjxs67YhdgrQ5rW7RQwHaHa&pid=Api&rs=1","xiaobaicai"));
        mList.add(new DiseaseData("苋菜","https://cn.bing.com/th?id=OIP.5ng1FtMeK1P7YXg_w83igQHaFj&pid=Api&rs=1","jiecai"));





        mList2.add(new DiseaseData("芝麻","https://cn.bing.com/th?id=OIP.zoHsUXJWvHAPxSugbIHgBgHaHf&pid=Api&rs=1","zhima"));
        mList2.add(new DiseaseData("花生","http://img02.tooopen.com/images/20150819/tooopen_sy_138895664793.jpg","huasheng"));
        mList2.add(new DiseaseData("茶叶","https://www.1616n.com/upload/resources/image/2017/07/27/656649.jpg","chaye"));
        mList2.add(new DiseaseData("蓖麻","http://pic.baike.soso.com/p/20130408/20130408154305-69638433.jpg","bima"));
        mList2.add(new DiseaseData("烟草","https://cn.bing.com/th?id=OIP.e-GBr6tb7QxIYCs17zi7GAHaFj&pid=Api&rs=1","yancao"));
        mList2.add(new DiseaseData("甘薯","http://a4.att.hudong.com/54/12/01300000180919121697121473963.jpg","ganshu"));
        mList2.add(new DiseaseData("向日葵","http://a4.att.hudong.com/66/91/01000000000000119089130220666.jpg","xiangrikui"));
        mList2.add(new DiseaseData("木薯","https://image.tcmwiki.com/image/%E6%9C%A8%E8%96%AF/%E6%9C%A8%E8%96%AF.jpg","mushu"));




        mList3.add(new DiseaseData("苹果","http://game.hg0355.com/game/xpg/logo.jpg","pingguo"));
        mList3.add(new DiseaseData("葡萄","http://file06.16sucai.com/2016/0919/61d2ad640028401f16d7d1fd94d16d2b.jpg","putao"));
        mList3.add(new DiseaseData("梨","https://www.olive-hitomawashi.com/column/80cce6747bed9ee9440d89af18b47ba6931a3448.jpg","li"));
        mList3.add(new DiseaseData("桃","https://cn.bing.com/th?id=OIP.V4cSc4-0EWyjTLFQMsJRegHaE8&pid=Api&rs=1","tao"));
        mList3.add(new DiseaseData("草莓","https://cn.bing.com/th?id=OIP.Qbnce6uj0xNxfER2hr_yMAHaEo&pid=Api&rs=1","caomei"));
        mList3.add(new DiseaseData("西瓜","http://d6.yihaodianimg.com/N00/M02/5E/A4/CgQCtlGZ2NWAbSV6AAJPZJxjNOk06200.jpg","xigua"));
        mList3.add(new DiseaseData("甘蔗","http://pic7.photophoto.cn/20080612/0020033078507471_b.jpg","ganzhe"));
        mList3.add(new DiseaseData("甜瓜","http://pic.qqtn.com/up/2017-5/201705091441151745527.png","tiangua"));



        mList4.add(new DiseaseData("玉米","https://cn.bing.com/th?id=OIP.BTA-AyZROm9sj9JFrKpE6gHaF7&pid=Api&rs=1","yumi"));
        mList4.add(new DiseaseData("水稻","https://cn.bing.com/th?id=OIP.DuAkfbnHhXVjgpbMKMkyswHaFM&pid=Api&rs=1","shuidao"));
        mList4.add(new DiseaseData("大麦","https://cn.bing.com/th?id=OIP.pxuy8n3hgCrTgPod0KQQ_wHaDt&pid=Api&rs=1","damai"));
        mList4.add(new DiseaseData("小麦","http://www.alnaturia.com/wp-content/uploads/2017/07/DSC_0212.jpg","xiaomai"));
        mList4.add(new DiseaseData("棉花","http://img.qnong.com.cn/uploadfile/2016/0309/20160309080932770.jpg","mianhua"));
        mList4.add(new DiseaseData("土豆","https://cn.bing.com/th?id=OIP.fBmV-BZLMMTMi_qm3MnFxQHaGR&pid=Api&rs=1","tudou"));
        mList4.add(new DiseaseData("油菜","http://imgs.nmplus.hk/wp-content/uploads/2016/02/%E6%B2%B9%E8%8F%9C%E8%8A%B107.jpg","youcai"));
        mList4.add(new DiseaseData("大豆","https://cn.bing.com/th?id=OIP.Eww7Cgag6EG3M6pnpqLM2QHaE8&pid=Api&rs=1","dadou"));
        mList4.add(new DiseaseData("高粱","https://cn.bing.com/th?id=OIP._H_lgGLAmCzRXkjTIfs1AgHaFj&pid=Api&rs=1","gaoliang"));
        mList4.add(new DiseaseData("绿豆","https://cn.bing.com/th?id=OIP.TGNgu0X_WDX0ZOOHRttNQQHaGU&pid=Api&rs=1","lvdou"));
        mList4.add(new DiseaseData("蚕豆","https://cn.bing.com/th?id=OIP._6280Hq3xW9viFKxTKzemAHaFQ&pid=Api&rs=1","candou"));
        mList4.add(new DiseaseData("荞麦","https://static.baicaolu.com/uploads/201507/1436285026i8NwYdcR.jpg","qiaomai"));
        mList4.add(new DiseaseData("麻类","http://n.sinaimg.cn/translate/88/w500h388/20181201/Vlp1-hpevhcm5855739.jpg","malei"));

    }
}
