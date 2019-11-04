package com.example.ywang.diseaseidentification.adapter.disease;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.bean.CropBean;
import com.example.ywang.diseaseidentification.bean.CropItem;

import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RightAdapter extends BaseQuickAdapter<CropItem>{

    private int selectPos = 0;
    public RightAdapter(List<CropItem> data) {
        super( R.layout.crop_part_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CropItem cropItem) {
        if(selectPos == helper.getAdapterPosition()){
            helper.convertView.setBackgroundColor( Color.parseColor("#FFFFFF"));
            helper.setTextColor(R.id.crop_text, Color.parseColor("#40a5f3"));
        }else{
            helper.convertView.setBackgroundColor(Color.parseColor("#F4F4F4"));
            helper.setTextColor(R.id.crop_text, Color.parseColor("#333333"));
        }
        helper.setText(R.id.crop_text,cropItem.getTitle());
        ImageView imageView = helper.convertView.findViewById(R.id.crop_src);
        Glide.with(helper.getConvertView().getContext()).load(cropItem.getUrl()).into(imageView);
    }

    public int getSelectPos() {
        return selectPos;
    }

    public void setSelectPos(int selectPos) {
        this.selectPos = selectPos;
    }
}