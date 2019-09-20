package com.example.ywang.diseaseidentification.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.bean.ThumbViewInfo;
import com.example.ywang.diseaseidentification.utils.ImageLoaderUtil;
import com.example.ywang.diseaseidentification.view.activity.DisplayImageActivity;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

public class NineGridLayout extends BaseNineGridLayout {

    protected static final int MAX_W_H_RATIO = 3;

    public NineGridLayout(Context context) {
        super(context);
    }

    public NineGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean displayOneImage(final RatioImageView imageView, String url, final int parentWidth) {
        ImageLoaderUtil.displayImage(mContext, imageView, url, ImageLoaderUtil.getPhotoImageOption(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap bitmap) {
                int w = bitmap.getWidth();
                int h = bitmap.getHeight();

                int newW;
                int newH;
                if (h > w * MAX_W_H_RATIO) {//h:w = 5:3
                    newW = parentWidth / 2;
                    newH = newW * 5 / 3;
                } else if (h < w) {//h:w = 2:3
                    newW = parentWidth * 2 / 3;
                    newH = newW * 2 / 3;
                } else {//newH:h = newW :w
                    newW = parentWidth / 2;
                    newH = h * newW / w;
                }
                setOneImageLayoutParams(imageView, newW, newH);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
        return false;
    }

    @Override
    protected void displayImage(RatioImageView imageView, String url) {
        ImageLoaderUtil.getImageLoader(mContext).displayImage(url, imageView, ImageLoaderUtil.getPhotoImageOption());
    }

    @Override
    protected void onClickImage(int position, String url, List<String> urlList,View view) {
        //List<String> 转 List<ThumbViewInfo>
        ArrayList<ThumbViewInfo> mList = new ArrayList<>();
        for(int i = 0;i < urlList.size(); i++){
            mList.add(new ThumbViewInfo(urlList.get(i),i));
        }
        Intent intent = new Intent(getContext(), DisplayImageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("images", mList);
        bundle.putInt("position",position);
        intent.putExtras(bundle);
        getContext().startActivity(intent);
    }

}
