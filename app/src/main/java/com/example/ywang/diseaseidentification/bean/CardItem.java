package com.example.ywang.diseaseidentification.bean;

public class CardItem {
    private String mTextResource;
    private String mTitleResource;
    private boolean score_show = true;
    private boolean image_show = false;
    private String link;

    public CardItem(String title, String text,boolean isShow) {
        mTitleResource = title;
        mTextResource = text;
        score_show = isShow;
    }

    public CardItem(String title, String text,boolean isShow,String href) {
        mTitleResource = title;
        mTextResource = text;
        score_show = isShow;
        link = href;
    }

    public boolean isImage_show() {
        return image_show;
    }

    public void setImage_show(boolean image_show) {
        this.image_show = image_show;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isScore_show() {
        return score_show;
    }

    public void setScore_show(boolean score_show) {
        this.score_show = score_show;
    }

    public String getText() {
        return mTextResource;
    }

    public String getTitle() {
        return mTitleResource;
    }
}
