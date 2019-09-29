package com.example.ywang.diseaseidentification.bean.baseData;

public class cardData {
    private String urls;
    private String time;
    private String content;

    public cardData(String urls, String time, String content, String utu) {
        this.urls = urls;
        this.time = time;
        this.content = content;
        this.utu = utu;
    }

    private String utu;

    public String getUtu() {
        return utu;
    }

    public void setUtu(String utu) {
        this.utu = utu;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
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
}
