package com.example.ywang.diseaseidentification.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.bean.baseData.cardData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SportCardsAdapter extends RecyclerView.Adapter<SportCardsAdapter.SportCardViewHolder> {

    private final List<cardData> mItems = new ArrayList<>();
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public SportCardsAdapter(Context context) {
        mContext = context;
    }

    public boolean add(cardData item) {
        boolean isAdded = mItems.add(item);
        if (isAdded) {
            notifyDataSetChanged();
        }
        return isAdded;
    }

    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

    public boolean addAll(Collection<cardData> items) {
        boolean isAdded = mItems.addAll(items);
        if (isAdded) {
            notifyDataSetChanged();
        }
        return isAdded;
    }

    @NonNull
    @Override
    public SportCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new SportCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SportCardViewHolder holder, int position) {
        cardData item = mItems.get(position);
        Glide.with(mContext).load(item.getUrls()).into(holder.ivSportPreview);
        holder.tvTime.setText(item.getTime());
        holder.tvDayPart.setText(item.getUtu());
        holder.tvSportTitle.setText(item.getContent());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClicked(holder.getAdapterPosition(), holder.ivSportPreview);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    cardData getModelByPos(int pos) {
        return mItems.get(pos);

    }

    public interface OnItemClickListener {
        void onItemClicked(int pos, View view);
    }

    class SportCardViewHolder extends RecyclerView.ViewHolder {

        final TextView tvSportTitle;
        final TextView tvTime;
        final TextView tvDayPart;
        ImageView ivSportPreview;

        SportCardViewHolder(View itemView) {
            super(itemView);
            tvSportTitle = (TextView) itemView.findViewById(R.id.tvSportTitle);
            ivSportPreview = (ImageView) itemView.findViewById(R.id.ivSportPreview);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            tvDayPart = (TextView) itemView.findViewById(R.id.tvDayPart);
        }
    }
}
