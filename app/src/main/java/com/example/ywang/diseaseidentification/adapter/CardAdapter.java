package com.example.ywang.diseaseidentification.adapter;

import android.support.v7.widget.CardView;

public interface CardAdapter {
    int MAX_ELEVATION_FACTOR = 10;
    float getBaseElevation();
    CardView getCardViewAt(int position);
    int getCount();
}
