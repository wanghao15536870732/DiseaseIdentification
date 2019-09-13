package com.example.ywang.diseaseidentification.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.example.ywang.diseaseidentification.R;

public class SnackBarUtil {
    public static void showSnackBar(CharSequence text, View view, Context context) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT)
                .setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setActionTextColor(context.getResources().getColor( R.color.white))
                .show();
    }

    public static void showSnackBar(int resId, View view, Context context) {
        showSnackBar(context.getResources().getText(resId), view, context);
    }
}