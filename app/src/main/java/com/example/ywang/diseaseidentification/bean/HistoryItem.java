package com.example.ywang.diseaseidentification.bean;

import com.baidu.mapapi.model.LatLng;

public class HistoryItem {
    private String imageUrl;
    private String time;
    private String content;
    private LatLng latLng;
    private String weather;
    private String tmp;
    private String city;
    private String detail;
    private String descrip;

    public HistoryItem(String imageUrl, String time, String content, LatLng latLng, String weather, String tmp, String city, String detail, String descrip) {
        this.imageUrl = imageUrl;
        this.time = time;
        this.content = content;
        this.latLng = latLng;
        this.weather = weather;
        this.tmp = tmp;
        this.city = city;
        this.detail = detail;
        this.descrip = descrip;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }
}
