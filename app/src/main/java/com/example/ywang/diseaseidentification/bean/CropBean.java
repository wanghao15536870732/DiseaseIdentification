package com.example.ywang.diseaseidentification.bean;

import java.util.List;

public class CropBean {
    private String title;
    private String url;
    private List<CropItem> mList;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<CropItem> getmList() {
        return mList;
    }

    public void setmList(List<CropItem> mList) {
        this.mList = mList;
    }
}
