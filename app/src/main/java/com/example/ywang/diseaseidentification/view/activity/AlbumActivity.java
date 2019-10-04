package com.example.ywang.diseaseidentification.view.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.baidu.mapapi.model.LatLng;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.adapter.ItemDecoration;
import com.example.ywang.diseaseidentification.adapter.TimeLineAdapter;
import com.example.ywang.diseaseidentification.bean.Item;

import java.util.ArrayList;
import java.util.List;

public class AlbumActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;;
    private List<Item> mList = new ArrayList<>();
    private TimeLineAdapter mAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.addItemDecoration(new ItemDecoration(this,100));
        initData();
        mAdapter = new TimeLineAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);

        toolbar = (Toolbar) findViewById(R.id.toolbar_album);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("我的相册");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    private void initData(){

        LatLng latLng = new LatLng(38.021138,112.455709);
        Item item1 = new Item("http://img01.tooopen.com/Downs/images/2011/7/14/sy_20110714110422194316.jpg","2019-10-03",
                "这天的天气不错",latLng,"晴朗","7° - 14°","太原","尖草坪区","中北大学主楼");
        mList.add(item1);

        LatLng latLng1 = new LatLng(38.013872,112.468427);
        Item item2 = new Item("http://img01.tooopen.com/Downs/images/2011/8/8/sy_20110808173542492016.jpg","2019-10-01",
                "今年的小麦长势感觉挺好，快收麦子了，拍个照记录一下",latLng1,"多云","8° - 16°","太原","尖草坪区","尖草坪区中华傅山园旅游景区(新兰路北)");
        mList.add(item2);

        LatLng latLng2 = new LatLng(38.017283,112.446218);
        Item item3 = new Item("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=15701706205" +
                "91&di=d5c5fe60fa4a7b0de7e391a52afb035c&imgtype=0&src=http%3A%2F%2Fstatic1.keepcdn.com%2Fpicture%2F2018%2F" +
                "07%2F26%2F09%2F353c01b33054632fb98b9b4a2a592eadc0877065_1500x1501.jpg",
                "2019-9-20",
                "刚运动完，路过一片柳树林",latLng2,"晴朗","7° - 16°","太原","中北大学","新兰路733号窦大夫祠附近");
        mList.add(item3);

        LatLng latLng3 = new LatLng(37.558965,112.374293);
        Item item4 = new Item("http://pic.nipic.com/2008-01-19/2008119124120703_2.jpg","2019-9-27",
                "葡萄快成熟了，开起来也没有啥病，挺健康的",latLng3,"晴朗","6° - 22°","太原","上兰村","太原市清徐县东青堆南街 东青堆培忠葡萄采摘大棚");
        mList.add(item4);


        LatLng latLng4 = new LatLng(38.4,112.8);
        Item item5 = new Item("http://www.haonongzi.com/pic/news/20170110094219630.jpg","2019-9-23",
                "到了给农作物打药的季节了，虽然下了点小雨，不过不影响",latLng4,"晴朗","6° - 22°","小雨","尖草坪区","");
        mList.add(item5);

        LatLng latLng5 = new LatLng(38.4,112.8);
        Item item6 = new Item("http://img67.nongjx.com/9/20170520/636308751422414322128.jpg","2019-9-17",
                "现在发展的真快，已经开始用上无人机打药了，真方便！",latLng5,"晴朗","8° - 22°","太原","尖草坪区","");
        mList.add(item6);

        LatLng latLng6 = new LatLng(38.4,112.8);
        Item item7 = new Item("http://www.guoshu.cn/file/upload/201707/31/1052247524750.jpg","2019-9-14",
                "今年小麦真是大丰收！",latLng6,"晴朗","7° - 14°","太原","尖草坪区","");
        mList.add(item7);
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
}
