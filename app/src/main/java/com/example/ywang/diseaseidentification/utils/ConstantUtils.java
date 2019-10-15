package com.example.ywang.diseaseidentification.utils;

import android.content.Context;

import com.example.ywang.diseaseidentification.R;
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
}
