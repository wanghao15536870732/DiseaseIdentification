package com.example.ywang.diseaseidentification.bean.baseData;

import android.os.Parcel;
import android.os.Parcelable;

public class DiseaseData implements Parcelable{
    private String name;
    private String imageUrl;
    private String csvName;

    public DiseaseData(){}

    public DiseaseData(String name, String imageUrl, String csvName) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.csvName = csvName;
    }

    public String getContent() {
        return name;
    }

    public void setContent(String content) {
        this.name = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCSVName() {
        return csvName;
    }

    public void setCSVName(String link) {
        this.csvName = link;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(name);
        parcel.writeSerializable(imageUrl);
        parcel.writeSerializable(csvName);
    }

    public static final Parcelable.Creator<DiseaseData> CREATOR = new Creator<DiseaseData>() {
        @Override
        public DiseaseData createFromParcel(Parcel source) {
            DiseaseData slideshowBean = new DiseaseData();
            slideshowBean.name = source.readString();
            slideshowBean.imageUrl = source.readString();
            slideshowBean.csvName = source.readString();
            return slideshowBean;
        }

        @Override
        public DiseaseData[] newArray(int size) {
            return new DiseaseData[size];
        }
    };

}
