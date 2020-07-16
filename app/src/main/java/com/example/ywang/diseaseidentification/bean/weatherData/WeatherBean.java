package com.example.ywang.diseaseidentification.bean.weatherData;

public class WeatherBean {

    public static final String CLEAR_DAY = "晴（白天）"; //晴（白天）
    public static final String CLEAR_DAY_NIGHT = "晴（夜间）"; //晴（夜间）

    public static final String PARTLY_CLOUDY_DAY = "多云（白天）"; //多云（白天）
    public static final String PARTLY_CLOUDY_NIGHT = "多云（夜晚）"; //多云（夜间）

    public static final String CLOUDY = "阴天"; //阴天

    public static final String RAIN = "小雨";
    public static final String MODERATE_RAIN = "中雨";
    public static final String HEAVY_RAIN = "大雨";

    public static final String LIGHT_SNOW = "小雪";
    public static final String MODERATE_SNOW = "中雪";
    public static final String HEAVY_SNOW = "大雪";

    public static final String WIND = "大风";


    public String weather;  //天气，取值为上面12种,其他待添加
    public int temperature; //温度值
    public String temperatureStr; //温度的描述值
    public String time; //时间值

    public WeatherBean(String weather, int temperature,String time) {
        this.weather = weather;
        this.temperature = temperature;
        this.time = time;
        this.temperatureStr = temperature + "°";
    }

    public WeatherBean(String weather, int temperature, String temperatureStr, String time) {
        this.weather = weather;
        this.temperature = temperature;
        this.temperatureStr = temperatureStr;
        this.time = time;
    }

    public static String[] getAllWeathers(){
        String[] str = {CLEAR_DAY,CLEAR_DAY_NIGHT,PARTLY_CLOUDY_DAY,
                PARTLY_CLOUDY_NIGHT,CLOUDY,RAIN,MODERATE_RAIN,HEAVY_RAIN,
                LIGHT_SNOW,MODERATE_SNOW,HEAVY_SNOW,WIND};
        return str;
    }
}