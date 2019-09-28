package com.example.ywang.diseaseidentification.bean.baseData;
import java.util.ArrayList;
import java.util.List;

public class DynamicBean {
    public String user = null;
    public String content = null;
    public String time = null;
    public String type = null;
    public List<String> url = new ArrayList<>();
    public int img_num = 0;
    public static List<String> mUrl = new ArrayList<>();

    public DynamicBean(){}

    public DynamicBean(String user, String content, String time, String type, List<String> url, int img_num) {
        this.user = user;
        this.content = content;
        this.time = time;
        this.type = type;
        this.url = url;
        this.img_num = img_num;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }

    public int getImg_num() {
        return img_num;
    }

    public void setImg_num(int img_num) {
        this.img_num = img_num;
    }
}