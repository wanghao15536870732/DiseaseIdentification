package com.example.ywang.diseaseidentification.view.activity;

import android.content.Context;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.adapter.disease.DiseasesAdapter;
import com.example.ywang.diseaseidentification.bean.baseData.DiseaseData;
import com.example.ywang.diseaseidentification.utils.file.ConstantUtils;
import com.example.ywang.diseaseidentification.utils.network.WebUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SelfResultActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<DiseaseData> list = new ArrayList<>();
    private Toolbar toolbar;
    public static List<String[]> scoreList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_result);
        toolbar = (Toolbar) findViewById(R.id.toolbar_self_learn);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("自测结果");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        recyclerView = findViewById(R.id.recycler_slef);

        scoreList = ConstantUtils.getCSVFile(this,R.raw.shuidao);

        for(int i = 1;i < 4;i ++){
            DiseaseData data = new DiseaseData();
            data.setContent(scoreList.get(i)[0]);
            data.setImageUrl(scoreList.get(i)[1]);
            data.setLink(scoreList.get(i)[2]);
            list.add(data);
        }

        LinearLayoutManager manager = new LinearLayoutManager(this);
        SelfAdapter adapter = new SelfAdapter(list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }

    class SelfAdapter extends RecyclerView.Adapter<SelfAdapter.ViewHolder> {

        private List<DiseaseData> data;
        private Context mContext;

        public SelfAdapter(List<DiseaseData> data) {
            this.data = data;
        }

        class ViewHolder extends RecyclerView.ViewHolder{

            CardView cardView;
            ImageView diseaseView;
            TextView diseaseName,percent;

            public ViewHolder(View itemView) {
                super(itemView);
                cardView = (CardView) itemView;
                diseaseView = (ImageView) itemView.findViewById(R.id.img_self);
                diseaseName = (TextView) itemView.findViewById(R.id.text_self);
                percent = (TextView) itemView.findViewById(R.id.text_percent);
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
                    DiseaseData da = data.get(position);
                    WebUtil.openWeb(mContext,da.getContent(),da.getLink(),da.getContent());
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            DiseaseData da = data.get(position);
            holder.diseaseName.setText(da.getContent());
            //使用Glide来加载图片
            Glide.with(mContext).load(da.getImageUrl()).into(holder.diseaseView);
            switch (position){
                case 0:
                    holder.percent.setText("83%");
                    break;
                case 1:
                    holder.percent.setText("78%");
                    break;
                case 2:
                    holder.percent.setText("65%");
                    break;
                default:
                    holder.percent.setText("");
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
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
}
