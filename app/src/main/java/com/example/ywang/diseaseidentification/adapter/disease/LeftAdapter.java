package com.example.ywang.diseaseidentification.adapter.disease;


import android.graphics.Color;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.bean.AgriProduct;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class  LeftAdapter extends BaseQuickAdapter<AgriProduct> {
    private int selectPos = 0;
    public LeftAdapter(List<AgriProduct> data) {
        super( R.layout.item_main_left, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AgriProduct product) {
        if(selectPos == helper.getAdapterPosition()){
            helper.setVisible(R.id.item_main_left_bg,true);
            helper.convertView.findViewById(R.id.left_relative).setBackgroundColor( Color.parseColor("#FFFFFF"));
            helper.setTextColor(R.id.item_main_left_type, Color.parseColor("#469F32"));
        }else{
            helper.setVisible(R.id.item_main_left_bg,false);
            helper.convertView.findViewById(R.id.left_relative).setBackgroundColor(Color.parseColor("#f7f7f7"));
            helper.setTextColor(R.id.item_main_left_type, Color.parseColor("#333333"));
        }
        helper.setText(R.id.item_main_left_type,product.getName());
        CircleImageView circleImageView = helper.convertView.findViewById(R.id.left_image);
        if (product.getImg().equals("")) {
            circleImageView.setImageResource(R.drawable.image_placeholder);
        } else {
            Glide.with(helper.getConvertView().getContext()).load(product.getImg()).into(circleImageView);
        }
    }


    public int getSelectPos() {
        return selectPos;
    }

    public void setSelectPos(int selectPos) {
        this.selectPos = selectPos;
    }
}