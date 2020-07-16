package com.example.ywang.diseaseidentification.bean;

import java.util.ArrayList;
import java.util.List;

public class NineGridModel {
    public List<String> urlList = new ArrayList<>();
    public boolean isShowAll = false;
    public String detail;
    public int image;
    public String imageUri;
    public String name;
    public String time;
    public String start;
    public List<String> mCommentInfos = new ArrayList<> ();


    public List<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<String> urlList) {
        this.urlList = urlList;
    }

    public boolean isShowAll() {
        return isShowAll;
    }

    public void setShowAll(boolean showAll) {
        isShowAll = showAll;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public List<String> getmCommentInfos() {
        return mCommentInfos;
    }

    public void setmCommentInfos(List<String> mCommentInfos) {
        this.mCommentInfos = mCommentInfos;
    }
}
