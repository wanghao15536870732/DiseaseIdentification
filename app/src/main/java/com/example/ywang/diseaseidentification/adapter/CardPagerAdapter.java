package com.example.ywang.diseaseidentification.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.bean.CardItem;
import com.example.ywang.diseaseidentification.view.activity.DetailActivity;
import java.util.ArrayList;
import java.util.List;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private Context mContext;
    private Bitmap imageBitmap;
    private List<CardView> mViews;
    private List<CardItem> mData;
    private float mBaseElevation;

    public CardPagerAdapter(Context context,Bitmap bitmap) {
        mContext = context;
        imageBitmap = bitmap;
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
        TextView scoreTextView = view.findViewById(R.id.score_text);
        Button reTakeBtn = view.findViewById(R.id.re_take_btn);
        Button moreBtn = view.findViewById(R.id.more_btn);
        titleTextView.setText(item.getTitle());
        contentTextView.setText(item.getText());
        scoreTextView.setText(String.valueOf(item.getScore()));
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
            moreBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    View child_view = View.inflate(mContext, R.layout.dialog_select_pic, null);
                    final TextView hintText = child_view.findViewById(R.id.hint_text);
                    ImageView picImg = child_view.findViewById(R.id.pic_img);
                    picImg.setBackground(new BitmapDrawable(imageBitmap));
                    hintText.setText("输入你认为的病害种类");
                    builder.setView(child_view);
                    builder.setTitle("病害反馈");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(mContext, "感谢您的反馈！", Toast.LENGTH_SHORT).show();
                        }
                    }).show();
                }
            });

            reTakeBtn.setVisibility(View.VISIBLE);
            reTakeBtn.setText("重新拍照");
        }
    }
}
