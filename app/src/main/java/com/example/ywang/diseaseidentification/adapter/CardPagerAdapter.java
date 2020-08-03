package com.example.ywang.diseaseidentification.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.bean.CardItem;
import com.example.ywang.diseaseidentification.view.activity.DetailActivity;

import java.util.ArrayList;
import java.util.List;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private Context mContext;
    private List<CardView> mViews;
    private List<CardItem> mData;
    private float mBaseElevation;

    public CardPagerAdapter(Context context) {
        mContext = context;
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public void addCardItem(CardItem item) {
        mViews.add(null);
        mData.add(item);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.card_item_main, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(final CardItem item, View view) {
        TextView titleTextView = view.findViewById(R.id.titleTextView);
        TextView contentTextView = view.findViewById(R.id.contentTextView);
        Button reTakeBtn = view.findViewById(R.id.re_take_btn);
        Button moreBtn = view.findViewById(R.id.more_btn);
        titleTextView.setText(item.getTitle());
        contentTextView.setText(item.getText());
        if(item.isScore_show()){
            view.findViewById(R.id.score_label).setVisibility(View.VISIBLE);
            reTakeBtn.setVisibility(View.GONE);
            moreBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!item.getText().equals("")){
                        Intent intent = new Intent(mContext, DetailActivity.class);
                        intent.putExtra("link",item.getLink());
                        intent.putExtra("title",item.getTitle());
                        mContext.startActivity(intent);
                    }
                }
            });
        }else {
            view.findViewById(R.id.score_label).setVisibility(View.GONE);
            moreBtn.setText("错误反馈");
            reTakeBtn.setVisibility(View.VISIBLE);
            reTakeBtn.setText("重新拍照");
        }
    }
}
