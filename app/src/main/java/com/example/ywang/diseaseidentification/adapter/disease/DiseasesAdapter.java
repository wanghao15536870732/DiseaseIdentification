package com.example.ywang.diseaseidentification.adapter.disease;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.bean.baseData.DiseaseData;
import com.example.ywang.diseaseidentification.utils.network.WebUtil;
import com.example.ywang.diseaseidentification.view.activity.DiseaseActivity;

import java.util.List;

public class DiseasesAdapter extends RecyclerView.Adapter<DiseasesAdapter.ViewHolder> {

    private Context mContext;
    private List<DiseaseData> mDiseaseList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView diseaseView;
        TextView diseaseName;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            diseaseView= (ImageView) itemView.findViewById(R.id.disease_image);
            diseaseName = (TextView) itemView.findViewById(R.id.disease_name);
        }
    }

    public DiseasesAdapter(List<DiseaseData> fruitList){
        mDiseaseList = fruitList;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.disease_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                DiseaseData data = mDiseaseList.get(position);
                Intent intent = new Intent(mContext, DiseaseActivity.class);
                intent.putExtra("name",data.getContent());
                intent.putExtra("image",data.getImageUrl());
                intent.putExtra("html",data.getCSVName());
                mContext.startActivity(intent);
                //WebUtil.openWeb(mContext,data.getContent(),data.getCSVName(),data.getContent());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DiseaseData data = mDiseaseList.get(position);
        holder.diseaseName.setText(data.getContent());
        //使用Glide来加载图片
        Glide.with(mContext).load(data.getImageUrl()).into(holder.diseaseView);

    }

    @Override
    public int getItemCount() {
        return mDiseaseList.size();
    }

}