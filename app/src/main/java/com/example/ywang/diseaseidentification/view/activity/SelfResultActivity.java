package com.example.ywang.diseaseidentification.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.bean.baseData.DiseaseData;
import com.example.ywang.diseaseidentification.utils.file.ConstantUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SelfResultActivity extends AppCompatActivity {

    private List<DiseaseData> list = new ArrayList<>();
    public static List<String[]> scoreList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_result);
        Toolbar toolbar = findViewById(R.id.toolbar_self_learn);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("自测结果");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.recycler_slef);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        scoreList = ConstantUtils.getCSVFile(this,getResource(name));

        for(int i = 1;i < 4;i ++){
            DiseaseData data = new DiseaseData();
            Random random = new Random();
            data.setContent(scoreList.get(random.nextInt(scoreList.size()))[0]);
            data.setImageUrl(scoreList.get(random.nextInt(scoreList.size()))[1]);
            data.setCSVName(scoreList.get(random.nextInt(scoreList.size()))[2]);
            list.add(data);
        }

        LinearLayoutManager manager = new LinearLayoutManager(this);
        SelfAdapter adapter = new SelfAdapter(list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }

    class SelfAdapter extends RecyclerView.Adapter<SelfAdapter.ViewHolder> {

        private List<DiseaseData> datas;
        private Context mContext;

        SelfAdapter(List<DiseaseData> datas) {
            this.datas = datas;
        }

        class ViewHolder extends RecyclerView.ViewHolder{

            CardView cardView;
            ImageView diseaseView;
            TextView diseaseName,percent;

            ViewHolder(View itemView) {
                super(itemView);
                cardView = (CardView) itemView;
                diseaseView = itemView.findViewById(R.id.img_self);
                diseaseName = itemView.findViewById(R.id.text_self);
                percent = itemView.findViewById(R.id.text_percent);
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (mContext == null){
                mContext = parent.getContext();
            }
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.self_result,parent,false);
            final ViewHolder holder = new ViewHolder(view);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    DiseaseData data = datas.get(position);
                    Intent intent = new Intent(mContext, DiseaseActivity.class);
                    intent.putExtra("name",data.getContent());
                    intent.putExtra("image",data.getImageUrl());
                    intent.putExtra("html",data.getCSVName());
                    SelfResultActivity.this.startActivity(intent);
                }
            });
            return holder;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            DiseaseData da = datas.get(position);
            holder.diseaseName.setText(da.getContent());
            //使用Glide来加载图片
            Glide.with(mContext).load(da.getImageUrl()).into(holder.diseaseView);
            Random rand = new Random();
            int first = rand.nextInt(85) + 15;
            int second = rand.nextInt(70) + 15;
            int third = rand.nextInt(55) + 15;
            switch (position){
                case 0:
                    holder.percent.setText(String.valueOf(first) + "%");
                    break;
                case 1:
                    holder.percent.setText(String.valueOf(second) + "%");
                    break;
                case 2:
                    holder.percent.setText(String.valueOf(third) + "%");
                    break;
                default:
                    holder.percent.setText("");
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }
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
