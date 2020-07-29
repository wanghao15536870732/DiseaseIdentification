package com.example.ywang.diseaseidentification.bean;

import java.util.ArrayList;
import java.util.List;

public class AgriProduct {

    /**
     * img : https://wnd.agri114.cn/productimg/zbdq/baicai.jpg
     * releaseTime :
     * name : 白菜
     * id : 1
     * category : 蔬菜
     * type : 1
     * status : 0
     */

    private String img;
    private String releaseTime;
    private String name;
    private int id;
    private String category;
    private int type;
    private int status;
    private List<AgriPosition> diseasePositionList = new ArrayList<>();

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<AgriPosition> getDiseasePositionList() {
        return diseasePositionList;
    }

    public void setDiseasePositionList(List<AgriPosition> diseasePositionList) {
        this.diseasePositionList = diseasePositionList;
    }

    @Override
    public String toString() {
        return "AgriProduct{" +
                "img='" + img + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", category='" + category +
                '}';
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
