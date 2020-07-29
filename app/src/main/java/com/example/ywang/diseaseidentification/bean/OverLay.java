package com.example.ywang.diseaseidentification.bean;

public class OverLay {
    private String AreaId;
    private String UserId;
    private String AreaName;
    private String AreaLength;
    private String AreaWidth;
    private double AreaLat;
    private double AreaLon;
    private int AreaType;

    public OverLay() {
    }

    public OverLay(String areaId, String userId, String areaName,
                   String areaLength, String areaWidth, double areaLat, double areaLon,int type) {
        AreaId = areaId;
        UserId = userId;
        AreaName = areaName;
        AreaLength = areaLength;
        AreaWidth = areaWidth;
        AreaLat = areaLat;
        AreaLon = areaLon;
        AreaType = type;
    }

    public int getAreaType() {
        return AreaType;
    }

    public void setAreaType(int areaType) {
        AreaType = areaType;
    }

    public String getAreaId() {
        return AreaId;
    }

    public void setAreaId(String areaId) {
        AreaId = areaId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getAreaName() {
        return AreaName;
    }

    public void setAreaName(String areaName) {
        AreaName = areaName;
    }

    public String getAreaLength() {
        return AreaLength;
    }

    public void setAreaLength(String areaLength) {
        AreaLength = areaLength;
    }

    public String getAreaWidth() {
        return AreaWidth;
    }

    public void setAreaWidth(String areaWidth) {
        AreaWidth = areaWidth;
    }

    public Double getAreaLat() {
        return AreaLat;
    }

    public void setAreaLat(Double areaLat) {
        AreaLat = areaLat;
    }

    public Double getAreaLon() {
        return AreaLon;
    }

    public void setAreaLon(Double areaLon) {
        AreaLon = areaLon;
    }
}
