package com.example.ywang.diseaseidentification.bean.weatherData;

import java.util.Date;

public class DailyWeather {

    /**
     * cond_code_d : 100
     * cond_code_n : 305
     * cond_txt_d : 晴
     * cond_txt_n : 小雨
     * date : 2020-05-03
     * hum : 68
     * mr : 14:22
     * ms : 03:12
     * pcpn : 1.0
     * pop : 55
     * pres : 906
     * sr : 05:29
     * ss : 19:23
     * tmp_max : 36
     * tmp_min : 10
     * uv_index : 11
     * vis : 25
     * wind_deg : 80
     * wind_dir : 东风
     * wind_sc : 3-4
     * wind_spd : 13
     */

    private String cond_code_d;
    private String cond_code_n;
    private String cond_txt_d;
    private String cond_txt_n;
    private Date date;
    private String tmp_max;
    private String tmp_min;

    public DailyWeather(String cond_code_d, String cond_code_n, String cond_txt_d, String cond_txt_n, Date date, String tmp_max, String tmp_min) {
        this.cond_code_d = cond_code_d;
        this.cond_code_n = cond_code_n;
        this.cond_txt_d = cond_txt_d;
        this.cond_txt_n = cond_txt_n;
        this.date = date;
        this.tmp_max = tmp_max;
        this.tmp_min = tmp_min;
    }

    public String getCond_code_d() {
        return cond_code_d;
    }

    public void setCond_code_d(String cond_code_d) {
        this.cond_code_d = cond_code_d;
    }

    public String getCond_code_n() {
        return cond_code_n;
    }

    public void setCond_code_n(String cond_code_n) {
        this.cond_code_n = cond_code_n;
    }

    public String getCond_txt_d() {
        return cond_txt_d;
    }

    public void setCond_txt_d(String cond_txt_d) {
        this.cond_txt_d = cond_txt_d;
    }

    public String getCond_txt_n() {
        return cond_txt_n;
    }

    public void setCond_txt_n(String cond_txt_n) {
        this.cond_txt_n = cond_txt_n;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTmp_max() {
        return tmp_max;
    }

    public void setTmp_max(String tmp_max) {
        this.tmp_max = tmp_max;
    }

    public String getTmp_min() {
        return tmp_min;
    }

    public void setTmp_min(String tmp_min) {
        this.tmp_min = tmp_min;
    }
}
