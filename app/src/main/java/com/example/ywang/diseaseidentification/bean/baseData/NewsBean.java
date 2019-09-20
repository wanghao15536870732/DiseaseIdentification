package com.example.ywang.diseaseidentification.bean.baseData;

public class NewsBean {

    private int id;
    private String author;
    private String time;
    private String title;
    private String content;
    private String Url;
    private String main_url;

    public NewsBean(int id, String author, String time, String title, String content, String url,String main_url) {
        this.id = id;
        this.author = author;
        this.time = time;
        this.title = title;
        this.content = content;
        this.Url = url;
        this.main_url = main_url;
    }

    public String getMain_url() {
        return main_url;
    }

    public void setMain_url(String main_url) {
        this.main_url = main_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
