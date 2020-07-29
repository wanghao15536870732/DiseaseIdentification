package com.example.ywang.diseaseidentification.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.adapter.disease.DiseasesAdapter;
import com.example.ywang.diseaseidentification.bean.DiseaseResult;
import com.example.ywang.diseaseidentification.bean.baseData.DiseaseData;
import com.example.ywang.diseaseidentification.utils.file.ConstantUtils;
import com.example.ywang.diseaseidentification.utils.network.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SelfCheckResultActivity extends AppCompatActivity {

    private static String address = "http://wnd.agri114.cn/wndms/json/reFindDiseases.action?start=1&pageSize=10";
    private List<DiseaseResult> diseaseList = new ArrayList<>();
    private List<DiseaseData> mList = new ArrayList<>();
    private SelfAdapter adapter;
    private DiseasesAdapter diseaseAdapter;
    private RecyclerView recyclerDisease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_check_result);
        Toolbar toolbar = findViewById(R.id.toolbar_self_learn);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("自测结果");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        final RecyclerView recyclerView = findViewById(R.id.recycler_slef);
        int agriProductId = getIntent().getIntExtra("agriProductId", 0);
        String diseaseFeatures = getIntent().getStringExtra("diseaseFeatures");
        String agriName = getIntent().getStringExtra("agriName");
        final TextView moreText = findViewById(R.id.more_text);
        if (!agriName.equals("")){
            recyclerDisease = findViewById(R.id.recycler_disease);
            ConstantUtils.getCSV(SelfCheckResultActivity.this,getResource(agriName));
            initDiseases();
            GridLayoutManager layoutManager = new GridLayoutManager(this,3);
            //让recyclerView的布局采用网格式布局
            recyclerDisease.setLayoutManager(layoutManager);
            diseaseAdapter = new DiseasesAdapter(mList);
            recyclerDisease.setAdapter(diseaseAdapter);
        }
        moreText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recyclerDisease.getVisibility() == View.GONE){
                    recyclerDisease.setVisibility(View.VISIBLE);
                    moreText.setText("收起其他推荐病症");
                }else {
                    recyclerDisease.setVisibility(View.GONE);
                    moreText.setText("上述结果偏差太大？点击查看其他相似病征");
                }
            }
        });
        Log.e("test", address + "&agriProductId=" + agriProductId +
                "&diseaseFeatures=" + diseaseFeatures);
        HttpUtil.sendOkHttpRequest(address + "&agriProductId=" + agriProductId +
                "&diseaseFeatures=" + diseaseFeatures, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SelfCheckResultActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject datas = new JSONObject(response.body().string());
                    JSONArray dataArray = datas.getJSONArray("data");
                    for (int m = 0; m < dataArray.length(); m++) {
                        JSONObject diseaseItem = dataArray.getJSONObject(m);
                        DiseaseResult result = new DiseaseResult();
                        result.setId(diseaseItem.getInt("id"));
                        result.setTitle(diseaseItem.getString("title"));
                        result.setScore(diseaseItem.getDouble("score"));
                        if (!diseaseItem.getString("img").equals("")) {
                            result.setImg(Arrays.asList(diseaseItem.getString("img").split(";")));
                        }
                        result.setContent(diseaseItem.getString("content"));
                        diseaseList.add(result);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                        Log.e("test", result.getTitle() + " : " + result.getScore());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(this);
        adapter = new SelfAdapter(diseaseList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }

    private void initDiseases(){
        mList.clear();
        List<String[]> list = ConstantUtils.scoreList;
        for(int i = 1;i < list.size();i ++) {
            mList.add(new DiseaseData(list.get(i)[0],list.get(i)[1],list.get(i)[2]));
        }
    }

    class SelfAdapter extends RecyclerView.Adapter<SelfAdapter.ViewHolder> {

        private List<DiseaseResult> datas;
        private Context mContext;

        SelfAdapter(List<DiseaseResult> datas) {
            this.datas = datas;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            CardView cardView;
            TextView diseaseName, percent;

            ViewHolder(View itemView) {
                super(itemView);
                cardView = (CardView) itemView;
                diseaseName = itemView.findViewById(R.id.text_self);
                percent = itemView.findViewById(R.id.text_percent);
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (mContext == null) {
                mContext = parent.getContext();
            }
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.self_result, parent, false);
            final ViewHolder holder = new ViewHolder(view);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    DiseaseResult result = diseaseList.get(position);
                    Intent intent = new Intent(SelfCheckResultActivity.this,DiseaseDetailActivity.class);
                    ArrayList list = new ArrayList<>(result.getImg());
                    intent.putExtra("title",result.getTitle());
                    intent.putStringArrayListExtra("imgList", list);
                    intent.putExtra("content",result.getContent());
                    startActivity(intent);
                }
            });
            return holder;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            DiseaseResult result = datas.get(position);
            holder.diseaseName.setText(result.getTitle());
            //使用Glide来加载图片
            holder.percent.setText("匹配率：" + getAverage(diseaseList, position) + "%");
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }
    }

    public int getAverage(List<DiseaseResult> paramList, int paramInt) {
        ArrayList localArrayList = new ArrayList();
        Iterator localIterator = paramList.iterator();
        while (localIterator.hasNext()) {
            localArrayList.add(((DiseaseResult) localIterator.next()).getScore());
        }
        double d = (Double) Collections.max(localArrayList);
        return (int) Math.round((paramList.get(paramInt).getScore() - 0.0D) / (d + 0.2D - 0.0D) * 100.0D);
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

    private int getResource(String imageName){
        Class raw = R.raw.class;
        try {
            Field field = raw.getField(imageName);
            return field.getInt(imageName);
        } catch (NoSuchFieldException e) {//如果没有在"mipmap"下找到imageName,将会返回0
            return R.raw.yumi;
        } catch (IllegalAccessException e) {
            return R.raw.yumi;
        }
    }
}
