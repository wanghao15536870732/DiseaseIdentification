package com.example.ywang.diseaseidentification.adapter.disease;

import android.content.Context;
import android.content.Intent;
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
import com.example.ywang.diseaseidentification.view.activity.ExcelActivity;

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

        public ViewHolder(View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.linear_crop);
            cropView = (CircleImageView) itemView.findViewById(R.id.crop_image);
            name = (TextView) itemView.findViewById(R.id.crop_name);
        }
    }

    public CropAdapter(List<DiseaseData> fruitList){
        mDiseaseList = fruitList;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.crop_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = holder.name.getText().toString();
                if (name.equals("水稻")){
                    ConstantUtils.getCSV(mContext,R.raw.shuidao);
                }else if(name.equals("玉米")){
                    ConstantUtils.getCSV(mContext,R.raw.yumi);
                }else if(name.equals("蚕豆")){
                    ConstantUtils.getCSV(mContext,R.raw.candou);
                }else if(name.equals("大豆")){
                    ConstantUtils.getCSV(mContext,R.raw.dadou);
                }else if(name.equals("大麦")){
                    ConstantUtils.getCSV(mContext,R.raw.damai);
                }else if(name.equals("花生")){
                    ConstantUtils.getCSV(mContext,R.raw.huasheng);
                }else if(name.equals("绿豆")){
                    ConstantUtils.getCSV(mContext,R.raw.lvdou);
                }else if(name.equals("麻类")){
                    ConstantUtils.getCSV(mContext,R.raw.malei);
                }else if(name.equals("棉花")){
                    ConstantUtils.getCSV(mContext,R.raw.mianhua);
                }else if(name.equals("荞麦")){
                    ConstantUtils.getCSV(mContext,R.raw.qiaomai);
                }else if(name.equals("豌豆")){
                    ConstantUtils.getCSV(mContext,R.raw.wandou);
                }else if(name.equals("燕麦")){
                    ConstantUtils.getCSV(mContext,R.raw.yanmai);
                }else if(name.equals("油菜")){
                    ConstantUtils.getCSV(mContext,R.raw.youcai);
                }
                Intent intent = new Intent(mContext, CropDetailActivity.class);
                intent.putExtra("name",holder.name.getText().toString());
                intent.putExtra("pinyin",holder.pinYin);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DiseaseData data = mDiseaseList.get(position);
        holder.pinYin = data.getLink();
        holder.name.setText(data.getContent());
        //使用Glide来加载图片
        Glide.with(mContext).load(data.getImageUrl()).into(holder.cropView);

    }

    @Override
    public int getItemCount() {
        return mDiseaseList.size();
    }

}