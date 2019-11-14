package com.example.ywang.diseaseidentification.bean.baseData;

public class ResultData {
    private String name;
    private String percent;
    private String content;
    private String imageUrl;
    private String link;

    public ResultData(String name, String percent, String content, String imageUrl, String link) {
        this.name = name;
        this.percent = percent;
        this.content = content;
        this.imageUrl = imageUrl;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
