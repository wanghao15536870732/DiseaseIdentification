package com.example.ywang.diseaseidentification.utils.file;

import android.content.Context;

import com.example.ywang.diseaseidentification.bean.baseData.DiseaseData;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ConstantUtils {

    public static List<DiseaseData> mList = new ArrayList<>();
    public static List<DiseaseData> mList2 = new ArrayList<>();
    public static List<DiseaseData> mList3 = new ArrayList<>();
    public static List<DiseaseData> mList4 = new ArrayList<>();

    public static List<String[]> scoreList = new ArrayList<>();

    public static void getCSV(Context mContext,int source){
        InputStream inputStream = mContext.getResources().openRawResource(source);
        CSVFile csvFile = new CSVFile(inputStream);
        scoreList = csvFile.read();
    }

    public static DiseaseData[] datas = {
            new DiseaseData("玉米圆斑病","http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/images/0224%20%E7%8E%89%E7%B1%B3%E5%9C%86%E6%96%91%E7%97%85%E7%A9%97%E8%85%90%E7%97%87%E7%8A%B6.jpg",
                    "http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/0224%20%E7%8E%89%E7%B1%B3%E5%9C%86%E6%96%91%E7%97%85.htm"),
            new DiseaseData("玉米干腐病","http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/images/0225%20%E7%8E%89%E7%B1%B3%E5%B9%B2%E8%85%90%E7%97%85%E6%9E%9C%E7%A9%97%E5%8F%97%E5%AE%B3%E7%8A%B6.jpg",
                    "http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/0225%20%E7%8E%89%E7%B1%B3%E5%B9%B2%E8%85%90%E7%97%85.htm"),
            new DiseaseData("玉米丝核菌穗腐病","http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/images/0226%20%E7%8E%89%E7%B1%B3%E4%B8%9D%E6%A0%B8%E8%8F%8C%E7%A9%97%E8%85%90%E7%97%85%E5%8F%8A%E5%A4%96%E8%8B%9E%E5%8F%B6%E4%B8%8A%E5%B0%8F%E8%8F%8C%E6%A0%B8.jpg",
                    "http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/0226%20%E7%8E%89%E7%B1%B3%E4%B8%9D%E6%A0%B8%E8%8F%8C%E7%A9%97%E8%85%90%E7%97%85.htm"),
            new DiseaseData("玉米镰刀菌穗粒腐病","http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/images/0227%20%E7%8E%89%E7%B1%B3%E8%B5%A4%E9%9C%89%E8%8F%8C%E7%A9%97%E8%85%90%EF%BC%88%E5%B7%A6%EF%BC%89%E5%92%8C%E9%95%B0%E5%88%80%E8%8F%8C%E7%A9%97%E8%85%90%E7%97%85.jpg",
                    "http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/0227(%E5%8F%B3)%20%E7%8E%89%E7%B1%B3%E9%95%B0%E5%88%80%E8%8F%8C%E7%A9%97%E7%B2%92%E8%85%90%E7%97%85.htm"),
            new DiseaseData("玉米赤霉病","http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/images/0227%20%E7%8E%89%E7%B1%B3%E8%B5%A4%E9%9C%89%E8%8F%8C%E7%A9%97%E8%85%90%EF%BC%88%E5%B7%A6%EF%BC%89%E5%92%8C%E9%95%B0%E5%88%80%E8%8F%8C%E7%A9%97%E8%85%90%E7%97%85.jpg",
                    "http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/0227(%E5%B7%A6)%20%E7%8E%89%E7%B1%B3%E8%B5%A4%E9%9C%89%E7%97%85.htm"),
            new DiseaseData("玉米枝孢穗腐病","http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/images/0228%20%E7%8E%89%E7%B1%B3%E6%9E%9D%E5%AD%A2%E8%8F%8C%E7%A9%97%E8%85%90%E7%97%85.jpg",
                    "http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/0228%20%E7%8E%89%E7%B1%B3%E6%9E%9D%E5%AD%A2%E7%A9%97%E8%85%90%E7%97%85.htm"),
            new DiseaseData("玉米斑枯病","http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/images/0233%20%E7%8E%89%E7%B1%B3%E6%96%91%E6%9E%AF%E7%97%85%E7%97%85%E5%8F%B6.jpg",
                    "http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/0233%20%E7%8E%89%E7%B1%B3%E6%96%91%E6%9E%AF%E7%97%85.htm"),
            new DiseaseData("玉米全蚀病","http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/images/0234%20%E7%8E%89%E7%B1%B3%E5%85%A8%E8%9A%80%E7%97%85%E6%A0%B9%E9%83%A8%E7%97%87%E7%8A%B6.jpg",
                    "http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/0234-0235%20%E7%8E%89%E7%B1%B3%E5%85%A8%E8%9A%80%E7%97%85.htm"),
            new DiseaseData("玉米线虫病","http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/images/0245%20%E7%8E%89%E7%B1%B3-%E7%BA%BF%E8%99%AB%E4%B8%BA%E5%AE%B3%E7%8E%89%E7%B1%B3%E7%94%B0%E9%97%B4%E5%8F%97%E5%AE%B3%E7%8A%B6.jpg",
                    "http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/0245-0247%20%E7%8E%89%E7%B1%B3%E7%BA%BF%E8%99%AB%E7%97%85.htm"),
            new DiseaseData("玉米缺素症","http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/images/0249%20%E7%8E%89%E7%B1%B3%E6%88%90%E6%A0%AA%E7%BC%BA%E6%B0%AE%E7%97%87%E7%8A%B6%E7%8A%B6.jpg",
                    "http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/0248-0262%20%E7%8E%89%E7%B1%B3%E7%BC%BA%E7%B4%A0%E7%97%87.htm"),
            new DiseaseData("玉米空秆","http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/images/0263%20%E7%8E%89%E7%B1%B3%E7%A9%BA%E7%A7%86.jpg",
                    "http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/0263%20%E7%8E%89%E7%B1%B3%E7%A9%BA%E7%A7%86.htm"),
            new DiseaseData("玉米倒伏","http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/images/0264%20%E7%8E%89%E7%B1%B3%E5%80%92%E4%BC%8F.jpg",
                    "http://www.cgris.net/disease/03%EF%BC%8D%E7%8E%89%E7%B1%B3/0264%20%E7%8E%89%E7%B1%B3%E5%80%92%E4%BC%8F.htm"),
    };
}
