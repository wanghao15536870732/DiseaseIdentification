package com.example.ywang.diseaseidentification.bean.baseData;

import android.os.Parcel;
import android.os.Parcelable;

public class DiseaseData implements Parcelable{
    private String content;
    private String imageUrl;
    private String link;

    public DiseaseData(){};

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(content);
        parcel.writeSerializable(imageUrl);
        parcel.writeSerializable(link);
    }

    public static final Parcelable.Creator<DiseaseData> CREATOR = new Creator<DiseaseData>() {
        @Override
        public DiseaseData createFromParcel(Parcel source) {
            DiseaseData slideshowBean = new DiseaseData();
            slideshowBean.content = source.readString();
            slideshowBean.imageUrl = source.readString();
            slideshowBean.link = source.readString();
            return slideshowBean;
        }

        @Override
        public DiseaseData[] newArray(int size) {
            return new DiseaseData[size];
        }
    };

}
