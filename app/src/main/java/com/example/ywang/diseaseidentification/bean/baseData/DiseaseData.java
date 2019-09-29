package com.example.ywang.diseaseidentification.bean.baseData;

public class DiseaseData {
    private String content;
    private String imageUrl;
    private String link;

    public DiseaseData(String content, String imageUrl, String link) {
        this.content = content;
        this.imageUrl = imageUrl;
        this.link = link;
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
