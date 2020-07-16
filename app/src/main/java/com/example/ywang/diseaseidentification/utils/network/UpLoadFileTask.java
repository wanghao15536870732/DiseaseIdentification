package com.example.ywang.diseaseidentification.utils.network;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.ywang.diseaseidentification.bean.baseData.DynamicBean;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UpLoadFileTask extends AsyncTask<List<String>, Void, List<String>> {

    /**
     * 可变长的输入参数，与AsyncTask.exucute()对应
     */
    private ProgressDialog dialog;
    @SuppressLint("StaticFieldLeak")
    private Context mContext;

    private static final String requestURL = "http://121.199.19.77:8080/show/ImageUploadServlet";
    private DynamicBean bean;

    public UpLoadFileTask(Context context,DynamicBean bean) {
        this.mContext = context;
        this.bean = bean;
    }

    @Override
    protected void onPostExecute(List<String> result) {
        try {
            if ((null != dialog) && dialog.isShowing()) {
                dialog.dismiss();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (dialog == null) {
            dialog = new ProgressDialog(mContext).show(mContext,"发表动态中","正在上传中...");
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected List<String> doInBackground(List<String>... params) {
        List<String> resultList = new ArrayList<>();
        for (String path: params[0]) {
            File file = new File(path);
            resultList.add(UploadUtils.uploadFile(file, requestURL,bean));
        }
        return resultList;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate();
    }

}