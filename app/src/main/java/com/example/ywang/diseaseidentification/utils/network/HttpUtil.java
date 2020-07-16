package com.example.ywang.diseaseidentification.utils.network;

import android.annotation.SuppressLint;
import android.util.Log;
import com.example.ywang.diseaseidentification.bean.weatherData.DailyWeather;
import com.example.ywang.diseaseidentification.bean.weatherData.Weather;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

    public static List<Weather> handleResponse(String response, String param) {
        List<Weather> weatherList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject hourlyData = jsonObject.getJSONObject("result").getJSONObject("hourly");
            if(param.equals("skycon")){
                JSONArray hourlyArray = hourlyData.getJSONArray("skycon");
                for (int i = 0; i < hourlyArray.length(); i++) {
                    JSONObject jsoni = hourlyArray.getJSONObject(i);
                    Date datetime = parseString(jsoni.getString("datetime"));
                    Weather weather = new Weather(jsoni.getString("value"),datetime);
                    weatherList.add(weather);
                }
            }else if (param.equals("temperature")){
                JSONArray hourlyArray = hourlyData.getJSONArray("temperature");
                for (int i = 0; i < hourlyArray.length(); i++) {
                    JSONObject jsoni = hourlyArray.getJSONObject(i);
                    Date datetime = parseString(jsoni.getString("datetime"));
                    float temp = Float.parseFloat(jsoni.getString("value"));
                    Weather weather = new Weather(temp,datetime);
                    weatherList.add(weather);
                }
            }
            return weatherList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weatherList;
    }

    public static List<DailyWeather> handleDailyResponse(String response){
        List<DailyWeather> dailyWeathers = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject weatherData = jsonObject.getJSONArray("HeWeather6").getJSONObject(0);
            JSONArray dailyList = weatherData.getJSONArray("daily_forecast");
            for (int i = 0; i < dailyList.length(); i++) {
                JSONObject dailyData = dailyList.getJSONObject(i);
                String cond_code_d = dailyData.getString("cond_code_d");
                String cond_code_n = dailyData.getString("cond_code_n");
                String cond_txt_d = dailyData.getString("cond_txt_d");
                String cond_txt_n = dailyData.getString("cond_txt_n");
                String dateStr = dailyData.getString("date");
                DateFormat formator = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = formator.parse(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.e("date",dateStr);
                String tmp_max = dailyData.getString("tmp_max");
                String tmp_min = dailyData.getString("tmp_min");
                DailyWeather dailyWeather = new DailyWeather(cond_code_d,
                        cond_code_n,cond_txt_d,cond_txt_n,date,tmp_max,tmp_min);
                dailyWeathers.add(dailyWeather);
            }
            return dailyWeathers;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dailyWeathers;
    }

    private static Date parseString(String publishDate){
        @SuppressLint("SimpleDateFormat")
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            return format.parse(publishDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }
}
