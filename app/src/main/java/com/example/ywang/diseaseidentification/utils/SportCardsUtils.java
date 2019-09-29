package com.example.ywang.diseaseidentification.utils;

import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.bean.baseData.cardData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SportCardsUtils {
    public static Collection<cardData> generateSportCards() {
        List<cardData> sportCardModels = new ArrayList<>(5);
        sportCardModels.add(new cardData("http://img01.tooopen.com/Downs/images/2011/7/14/sy_20110714085435378316.jpg",
                "7:00","还是挺健康的","PM"));
        sportCardModels.add(new cardData("http://img01.tooopen.com/Downs/images/2011/7/14/sy_20110714091944361316.jpg",
                "7:00","还是挺健康的","PM"));
        sportCardModels.add(new cardData("https://cn.bing.com/th?id=OIP.eUQwhJ07aHh-eO726qtj7wAAAA&pid=Api&rs=1",
                "7:00","还是挺健康的","PM"));
        sportCardModels.add(new cardData("http://www.kunlanbio.com/Upload/PicFiles/2015923917274352.jpg",
                "7:00","还是挺健康的","PM"));
        sportCardModels.add(new cardData("http://a4.att.hudong.com/01/36/01300000822820133661361396245.jpg",
                "7:00","还是挺健康的","PM"));
        sportCardModels.add(new cardData("http://pic16.huitu.com/res/20140213/24417_20140213172554096200_1.jpg",
                "7:00","还是挺健康的","PM"));
        return sportCardModels;
    }
}
