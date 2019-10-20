package com.example.ywang.diseaseidentification.utils.network;

import android.content.Context;
import android.content.Intent;

import com.example.ywang.diseaseidentification.view.activity.WebUIActivity;

public class WebUtil {

    /**
     * 打开网页
     *
     * @param context
     * @param title
     * @param url
     */

    public static void openWeb(Context context, String title, String url,String content) {
        Intent intent = new Intent(context, WebUIActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        intent.putExtra("content",content);
        context.startActivity(intent);
    }
}