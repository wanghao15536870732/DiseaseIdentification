package com.example.ywang.diseaseidentification.adapter.disease;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.bean.baseData.DiseaseData;
import com.example.ywang.diseaseidentification.utils.file.ConstantUtils;
import com.example.ywang.diseaseidentification.view.activity.CropDetailActivity;

import java.lang.reflect.Field;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CropAdapter extends RecyclerView.Adapter<CropAdapter.ViewHolder> {

    private Context mContext;
    private List<DiseaseData> mDiseaseList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout layout;
        CircleImageView cropView;
        TextView name;
        String pinYin;

        ViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.linear_crop);
            cropView = itemView.findViewById(R.id.crop_image);
            name = itemView.findViewById(R.id.crop_name);
        }
    }

    public CropAdapter(List<DiseaseData> fruitList){
        mDiseaseList = fruitList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.crop_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = holder.pinYin;
                ConstantUtils.getCSV(mContext,getResource(name));
                Intent intent = new Intent(mContext, CropDetailActivity.class);
                intent.putExtra("name",holder.name.getText().toString());
                intent.putExtra("pinyin",holder.pinYin);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DiseaseData data = mDiseaseList.get(position);
        holder.pinYin = data.getCSVName();
        holder.name.setText(data.getContent());
        //使用Glide来加载图片
        Glide.with(mContext).load(data.getImageUrl()).into(holder.cropView);
    }

    @Override
    public int getItemCount() {
        return mDiseaseList.size();
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