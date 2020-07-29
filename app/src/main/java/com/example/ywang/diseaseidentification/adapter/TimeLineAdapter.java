package com.example.ywang.diseaseidentification.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.bean.HistoryItem;
import com.example.ywang.diseaseidentification.view.activity.TimeRecordActivity;

import java.util.List;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.ViewHolder> {

    private Context mContext;
    private List<HistoryItem> mList;

    public void setList(List<HistoryItem> list) {
        mList = list;
    }

    public TimeLineAdapter(Context context) {
        mContext = context;
    }

    public TimeLineAdapter(Context context,List<HistoryItem> list) {
        mContext = context;
        mList=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final HistoryItem historyItem = mList.get(position);
        holder.textView.setText(historyItem.getTime());
        Glide.with(mContext).load(historyItem.getImageUrl()).into(holder.imageView);
        holder.content_text.setText(historyItem.getContent());
        holder.weather_text.setText(historyItem.getWeather());
        holder.tmp_text.setText(historyItem.getTmp());
        holder.city.setText(historyItem.getCity());
        holder.detial.setText(historyItem.getDetail());
        holder.album_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TimeRecordActivity.class);
                intent.putExtra("latitude", historyItem.getLatLng().latitude);
                intent.putExtra("longitude", historyItem.getLatLng().longitude);
                intent.putExtra("image", historyItem.getImageUrl());
                intent.putExtra("describe", historyItem.getDescrip());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView,weather;
        TextView textView;
        TextView weather_text,tmp_text,content_text,city,detial;
        CardView album_card;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.image_album);
            textView = (TextView) view.findViewById(R.id.text);
            weather = (ImageView) view.findViewById(R.id.weather);
            weather_text = view.findViewById(R.id.weather_text);
            tmp_text = view.findViewById(R.id.tmp_text);
            content_text = view.findViewById(R.id.content_text);
            city = view.findViewById(R.id.album_location_city);
            detial = view.findViewById(R.id.album_location_detail);
            album_card = view.findViewById(R.id.album_card);
        }
    }
}
