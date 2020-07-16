package com.example.ywang.diseaseidentification.bean.weatherData;

import java.util.Date;

public class Weather {
    private float temp; //温度
    private String skycon; //天气状况
    private Date datetime; //时间

    Weather(){};

    public Weather(String skycon, Date datetime) {
        this.skycon = skycon;
        this.datetime = datetime;
    }

    public Weather(float temp, Date datetime) {
        this.temp = temp;
        this.datetime = datetime;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public String getSkycon() {
        return skycon;
    }

    public void setSkycon(String skycon) {
        this.skycon = skycon;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
}
