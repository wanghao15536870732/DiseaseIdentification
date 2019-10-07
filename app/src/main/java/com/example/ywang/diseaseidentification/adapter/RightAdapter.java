package com.example.ywang.diseaseidentification.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.bean.CropItem;
import com.example.ywang.diseaseidentification.view.activity.CropDetailActivity;

import java.util.List;

public class RightAdapter extends RecyclerView.Adapter<RightAdapter.ViewHolder>{

    private List<CropItem> mDrugItems;

    public RightAdapter(List<CropItem> items){
        mDrugItems = items;
    }
    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from( viewGroup.getContext())
                .inflate( R.layout.crop_item,viewGroup,false);
        context = viewGroup.getContext();
        final ViewHolder holder = new ViewHolder( view );
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final CropItem cropItem = mDrugItems.get( i );
        Glide.with(context).load(cropItem.getUrl()).into(viewHolder.mImageView);
        viewHolder.title.setText( cropItem.getTitle() );
        viewHolder.content.setText( cropItem.getContent() );
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CropDetailActivity.class);
                intent.putExtra(CropDetailActivity.EXTRA_NAME,cropItem.getTitle());
                intent.putExtra(CropDetailActivity.EXTRA_IMG,cropItem.getUrl());
                context.startActivity( intent );
            }
        });
    }


    @Override
    public int getItemCount() {
        return mDrugItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView title,content;
        private ImageView mImageView;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            title = (TextView) itemView.findViewById( R.id.drug_title );
            content = (TextView) itemView.findViewById( R.id.drug_info );
            mImageView = (ImageView) itemView.findViewById( R.id.drug_src);
        }
    }
}