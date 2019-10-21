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
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.bean.CropItem;

import java.util.HashMap;
import java.util.List;

public class RightAdapter extends RecyclerView.Adapter<RightAdapter.ViewHolder>{

    private Context context;
    private List<CropItem> mCropItems;

    public RightAdapter(List<CropItem> items){
        mCropItems = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from( viewGroup.getContext())
                .inflate( R.layout.crop_part_item,viewGroup,false);
        context = viewGroup.getContext();
        final ViewHolder holder = new ViewHolder( view );
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder,final int i) {
        final CropItem cropItem = mCropItems.get( i );
        Glide.with(context).load(cropItem.getUrl()).into(viewHolder.mImageView);
        viewHolder.title.setText( cropItem.getTitle() );
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mCropItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private ImageView mImageView;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            title = (TextView) itemView.findViewById( R.id.crop_text );
            mImageView = (ImageView) itemView.findViewById( R.id.crop_src);
        }
    }
}