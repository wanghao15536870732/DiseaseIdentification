package com.example.ywang.diseaseidentification.bean;

public class MapImg {
    private int photoId;
    private String userId;
    private String diseaseName;
    private Double lat;
    private Double log;
    private String photoSrc;

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLog() {
        return log;
    }

    public void setLog(Double log) {
        this.log = log;
    }

    public String getPhotoSrc() {
        return photoSrc;
    }

    public void setPhotoSrc(String photoSrc) {
        this.photoSrc = photoSrc;
    }

    @Override
    public String toString() {
        return "MapImg{" +
                "photoId=" + photoId +
                ", userId='" + userId + '\'' +
                ", diseaseName='" + diseaseName + '\'' +
                ", lat=" + lat +
                ", log=" + log +
                ", photoSrc='" + photoSrc + '\'' +
                '}';
    }
}
