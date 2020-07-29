package com.example.ywang.diseaseidentification.adapter.disease;

import android.graphics.Color;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.bean.AgriPosition;
import java.util.List;

public class RightAdapter extends BaseQuickAdapter<AgriPosition> {

    private int selectPos = 0;

    public RightAdapter(List<AgriPosition> data) {
        super(R.layout.crop_part_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AgriPosition agriItem) {
        ImageView imageView = helper.convertView.findViewById(R.id.crop_src);
        int selectId = 0;
        int normalId = 0;
        switch (agriItem.getName()) {
            case "叶":
                selectId = R.mipmap.ye;
                normalId = R.mipmap.ye_no;
                break;
            case "花":
                selectId = R.mipmap.flower;
                normalId = R.mipmap.flower_no;
                break;
            case "根":
                selectId = R.mipmap.root;
                normalId = R.mipmap.root_no;
                break;
            case "茎":
                selectId = R.mipmap.jing;
                normalId = R.mipmap.jing_no;
                break;
            case "果":
                selectId = R.mipmap.guo;
                normalId = R.mipmap.guo_no;
                break;
            case "植株":
                selectId = R.mipmap.plant;
                normalId = R.mipmap.plant_no;
                break;
            case "树干":
                selectId = R.mipmap.gan;
                normalId = R.mipmap.gan_no;
                break;
            case "树枝":
                selectId = R.mipmap.leaf;
                normalId = R.mipmap.leaf_no;
                break;
        }
        if (selectPos == helper.getAdapterPosition()) {
            helper.convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            //helper.setTextColor(R.id.crop_text, Color.parseColor("#40a5f3"));
            imageView.setImageResource(selectId);
        } else {
            helper.convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            //helper.setTextColor(R.id.crop_text, Color.parseColor("#333333"));
            imageView.setImageResource(normalId);
        }
        //helper.setText(R.id.crop_text,agriItem.getName());
        //Glide.with(helper.getConvertView().getContext()).load(agriItem.getUrl()).into(imageView);
        //imageView.setImageResource(R.drawable.image_placeholder);
    }

    public int getSelectPos() {
        return selectPos;
    }

    public void setSelectPos(int selectPos) {
        this.selectPos = selectPos;
    }
}