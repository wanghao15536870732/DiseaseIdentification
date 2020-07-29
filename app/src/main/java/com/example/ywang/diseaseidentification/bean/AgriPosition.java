package com.example.ywang.diseaseidentification.bean;

import java.util.List;

public class AgriPosition {

    /**
     * diseaseFeatureList : {皱缩、畸形、扭曲、隆起病斑}
     * name: 叶
     * agriProductId : 3
     * id : 158
     */

    private String name;
    private int agriProductId;
    private int id;
    private List<DiseaseFeatureListBean> diseaseFeatureList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAgriProductId() {
        return agriProductId;
    }

    public void setAgriProductId(int agriProductId) {
        this.agriProductId = agriProductId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<DiseaseFeatureListBean> getDiseaseFeatureList() {
        return diseaseFeatureList;
    }

    public void setDiseaseFeatureList(List<DiseaseFeatureListBean> diseaseFeatureList) {
        this.diseaseFeatureList = diseaseFeatureList;
    }

    public static class DiseaseFeatureListBean {
        /**
         * diseasePositon :
         * name : 皱缩
         * diseasePositonId : 158
         * agriProductId : 3
         * id : 4866
         */

        private String diseasePositon;
        private String name;
        private int diseasePositonId;
        private int agriProductId;
        private int id;

        public String getDiseasePositon() {
            return diseasePositon;
        }

        public void setDiseasePositon(String diseasePositon) {
            this.diseasePositon = diseasePositon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getDiseasePositonId() {
            return diseasePositonId;
        }

        public void setDiseasePositonId(int diseasePositonId) {
            this.diseasePositonId = diseasePositonId;
        }

        public int getAgriProductId() {
            return agriProductId;
        }

        public void setAgriProductId(int agriProductId) {
            this.agriProductId = agriProductId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
