package com.example.ywang.diseaseidentification.bean.baseData;

public class TabBean {

    private String name;
    private int id;

    public TabBean(String name, int id) {
        this.name = name;
        this.id = id;
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

    @Override
    public String toString() {
        return "TabBean{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}