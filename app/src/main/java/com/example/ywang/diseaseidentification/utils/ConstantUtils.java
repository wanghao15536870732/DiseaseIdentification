package com.example.ywang.diseaseidentification.utils;

public class ConstantUtils {

    public static String[] questionList = new String[]{
            "现在快去观察你的农作物的外形" +
                    "\n下面会用到哦",
            "是否存在分枝,分蘖",
            "茎叶颜色情况",
            "老叶片基部叶脉间颜色情况",
            "叶片整体大小情况",
            "整体植株情况",
            "最近施肥日期状况",
            "揭晓结果"
    };

    public static String[][] answerList = new String[][]{
            {"开始吧"},
            {"较多", "较少", "很少"},
            {"红色或紫红色", "深浓", "褪淡、黄化"},
            {"退绿呈黄白色", "似半透明", "红紫色"},
            {"肥大而粗糙", "纤细而光滑"},
            {"生长过旺，分枝过多", "叶色加深，茎杆嫩弱", "并无异常"},
            {"一个月前", "一周前", "最近几天"},
            {"查看"}
    };
}
