package com.example.ywang.diseaseidentification.utils.network;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.ywang.diseaseidentification.bean.baseData.DynamicBean;
import java.io.File;

public class UpLoadFileTask extends AsyncTask<String, Void, String> {

    /**
     * 可变长的输入参数，与AsyncTask.exucute()对应
     */
    private ProgressDialog dialog;
    @SuppressLint("StaticFieldLeak")
    private Context mContext = null;

    private static final String requestURL = "http://101.37.79.26:8080/show/ImageUploadServlet";
    private DynamicBean bean;

    public UpLoadFileTask(Context context,DynamicBean bean) {
        this.mContext = context;
        this.bean = bean;
        dialog = ProgressDialog.show(mContext, "正在加载...", "系统正在处理您的请求");
    }

    @Override
    protected void onPostExecute(String result) {
        // 返回HTML页面的内容
        dialog.dismiss();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected String doInBackground(String... params) {
        File file = new File(params[0]);
        return UploadUtils.uploadFile(file, requestURL,bean);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate();
    }

}