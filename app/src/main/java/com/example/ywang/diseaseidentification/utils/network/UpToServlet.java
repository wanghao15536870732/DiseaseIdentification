package com.example.ywang.diseaseidentification.utils.network;
import android.util.Log;
import android.widget.Toast;

import com.example.ywang.diseaseidentification.bean.baseData.DynamicBean;
import com.example.ywang.diseaseidentification.view.activity.AddDynamicActivity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class UpToServlet {

    private DynamicBean bean;
    public static boolean SUCCESS = false;

    UpToServlet(DynamicBean bean) {
        this.bean = bean;
        Log.e("sss","*******************************");
        Log.e("sss","*******************************" + String.valueOf(bean.getUrl().size()));
    }

    public void sendRequestWithHttpURLConnection(){

        final String[] text = new String[5];
        final String[] img_url = new String[10];
        //初始化img_url
        for(int i = 0;i < 9;i++){
            img_url[i] = "";
        }
        text[0] = bean.getUser();
        text[1] = bean.getTime();
        text[2] = bean.getContent();
        text[3] = bean.getType();

        try {
            text[0] = URLEncoder.encode(text[0],"utf-8");
            text[1] = URLEncoder.encode(text[1],"utf-8");
            text[2] = URLEncoder.encode(text[2],"utf-8");
            text[3] = URLEncoder.encode(text[3],"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        for(int i = 0;i < bean.getUrl().size();i++){
            img_url[i] = bean.getUrl().get(i);
            Log.e("img_url",img_url[i]);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("http://121.199.19.77:8080/show/addShowServlet");
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("contentType", "GBK");
                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());

                    out.writeBytes("user="+ text[0] +"&time="+text[1] +"&laosao="+text[2] +"&type="+text[3]
                            +"&img_0="+img_url[0]+"&img_1="+img_url[1]+"&img_2="+img_url[2]+"&img_3="+img_url[3]
                            +"&img_4="+img_url[4]+ "&img_5=" +img_url[5]+"&img_6="+img_url[6]+"&img_7="+img_url[7]
                            +"&img_8="+img_url[8]);
                    Log.e("img_url","user="+ text[0] +"&time="+text[1] +"&laosao="+text[2] +"&type="+text[3]
                            +"&img_0="+img_url[0]+"&img_1="+img_url[1]+"&img_2="+img_url[2]+"&img_3="+img_url[3]
                            +"&img_4="+img_url[4]+ "&img_5=" +img_url[5]+"&img_6="+img_url[6]+"&img_7="+img_url[7]
                            +"&img_8="+img_url[8]);

                    out.flush();
                    out.close();

                    //设置连接超时和读取超时的毫秒数
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);

                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in,"GBK"));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine())!=null){
                        response.append(line);
                    }
                    //将 StringBuilder转为String
                    String r = response.toString();
                    Log.e("Servlet",r );
                    SUCCESS = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if(reader != null){
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    };
}