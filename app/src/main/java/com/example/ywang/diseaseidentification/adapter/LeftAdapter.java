package com.example.ywang.diseaseidentification.adapter;


import android.graphics.Color;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.bean.CropBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class  LeftAdapter extends BaseQuickAdapter<CropBean> {
    private int selectPos = 0;
    public LeftAdapter( List<CropBean> data) {
        super( R.layout.item_main_left, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CropBean bean) {
        if(selectPos == helper.getAdapterPosition()){
            helper.setVisible(R.id.item_main_left_bg,true);
            helper.convertView.setBackgroundColor( Color.parseColor("#FFFFFF"));
            helper.setTextColor(R.id.item_main_left_type, Color.parseColor("#40a5f3"));
        }else{
            helper.convertView.setBackgroundColor(Color.parseColor("#f7f7f7"));
            helper.setTextColor(R.id.item_main_left_type, Color.parseColor("#333333"));
            helper.setVisible(R.id.item_main_left_bg,false);
        }
        helper.setText(R.id.item_main_left_type,bean.getTitle());
        CircleImageView circleImageView = helper.convertView.findViewById(R.id.left_image);
        Glide.with(helper.getConvertView().getContext()).load(bean.getUrl()).into(circleImageView);
    }


    public int getSelectPos() {
        return selectPos;
    }

    public void setSelectPos(int selectPos) {
        this.selectPos = selectPos;
    }
}