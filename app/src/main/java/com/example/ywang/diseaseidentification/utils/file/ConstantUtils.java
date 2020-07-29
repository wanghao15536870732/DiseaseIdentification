package com.example.ywang.diseaseidentification.utils.file;

import android.content.Context;
import com.example.ywang.diseaseidentification.bean.baseData.DiseaseData;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ConstantUtils {

    public static List scoreList = new ArrayList<>();

    public static void getCSV(Context mContext,int source){
        InputStream inputStream = mContext.getResources().openRawResource(source);
        CSVFile csvFile = new CSVFile(inputStream);
        scoreList = csvFile.read();
    }

    public static List getCSVFile(Context mContext, int source){
        InputStream inputStream = mContext.getResources().openRawResource(source);
        CSVFile csvFile = new CSVFile(inputStream);
        return csvFile.read();
    }

    /*所有农作物*/
    public static DiseaseData[] CropList1 = {
            new DiseaseData("白菜","http://img4.agronet.com.cn/Users/100/161/960/20111014203409664.jpg","baicai"),
            new DiseaseData("菠菜","https://cn.bing.com/th?id=OIP.WJdzeRI-0mzdZfLiUQ7x5AHaGM&pid=Api&rs=1","bocai"),
            new DiseaseData("葱","http://www.danbaoli-blog.cn/wp-content/uploads/2014/05/%E8%91%B1.jpg","cong"),
            new DiseaseData("冬瓜","http://www.nren8.com/uploads/allimg/c160517/14634433QD0-3Y07.jpg","donggua"),
            new DiseaseData("番茄","https://cn.bing.com/th?id=OIP.rQHM7AeukvOzlJb881poAAHaFV&pid=Api&rs=1","fanqie"),
            new DiseaseData("胡萝卜","http://pic.nipic.com/2007-12-26/20071226115243355_2.jpg","huluobo"),
            new DiseaseData("花椰菜","https://pic.pimg.tw/djwang714/1522465865-1481450252.jpg","huayecai"),
            new DiseaseData("黄瓜","https://i3.meishichina.com/attachment/201405/6/13993619805178.jpg","huanggua"),
            new DiseaseData("芥菜","https://cn.bing.com/th?id=OIP.EsyYdiSUyNIcz3NoNNtkqQHaHa&pid=Api&rs=1","qicai"),
            new DiseaseData("韭菜","https://image.tcmwiki.com/image/%E9%9F%AD%E8%8F%9C/%E9%9F%AD%E8%8F%9C.jpg","jiucai"),
            new DiseaseData("苦瓜","https://cn.bing.com/th?id=OIP.Vra2ca7lhgQVK_8va2dKOgHaE7&pid=Api&rs=1","kugua"),
            new DiseaseData("辣椒","https://a4.att.hudong.com/80/60/01300000996113130148608272547.jpg","lajiao"),
            new DiseaseData("扁豆","http://www.114nz.com/all_images/tuku/fl/200909281658185273.jpg","biandou"),
            new DiseaseData("萝卜","http://s1.cdn.xiangha.com/shicai/201508/071034157627.jpg/MTAwMHgw","luobo"),
            new DiseaseData("南瓜","http://www.114nz.com/all_images/tuku/fl/200909281658327975.jpg","nangua"),
            new DiseaseData("茄子","https://cn.bing.com/th?id=OIP.W7wtukTpIHdOzkWPQHML-QHaEz&pid=Api&rs=1","qiezi"),
            new DiseaseData("芹菜","https://tse4-mm.cn.bing.net/th/id/OIP._QnHjyMwFS3-fNxHjQO54wHaFn?pid=Api&rs=1","qincai"),
            new DiseaseData("丝瓜","https://cn.bing.com/th?id=OIP.peqdszbb-5l8HoEoE4TPWgHaE7&pid=Api&rs=1","sigua"),
            new DiseaseData("蒜","http://pichk.daydaycook.com/production/images/20171204/146d0211-ebd6-444e-b315-77ac69bdc607","suan"),
            new DiseaseData("甜椒","http://www.114nz.com/all_images/tuku/fl/200909281703553699.jpg","tianjiao"),
            new DiseaseData("豌豆","http://img.juimg.com/tuku/yulantu/130805/328505-130P5222T277.jpg","wandou"),
            new DiseaseData("莴苣","https://cn.bing.com/th?id=OIP.4LqxEDsg_7PJ9L137qCHFQHaFE&pid=Api&rs=1","woju"),
            new DiseaseData("西葫芦","https://www.zhifure.com/upload/images/2018/1/12155342782.jpg","xihulu"),
            new DiseaseData("香菜","http://www.39ynt.com/doe/js/php/upload/20171129/15119542744622.jpeg","xiangcai"),
            new DiseaseData("洋葱","http://sdbdfyy.com/uploads/allimg/120327/1-12032GRG2V1.jpg","yangcong"),
            new DiseaseData("芥蓝","http://pic.baike.soso.com/p/20140319/20140319135542-1237064598.jpg","jielan"),
            new DiseaseData("莲藕","https://cn.bing.com/th?id=OIP.GpB7f1uzpE8B0kQkc2whDAHaFj&pid=Api&rs=1","lianou"),
            new DiseaseData("生菜","https://www.zhifure.com/upload/images/2018/1/22163742153.jpg","shengcai"),
            new DiseaseData("茴香","https://www.zhifure.com/upload/images/2018/1/29163950835.jpg","hunxiang"),
            new DiseaseData("甜菜","http://5b0988e595225.cdn.sohucs.com/images/20181217/8e72863e295d4fecb3a551164e7598eb.jpeg","tiancai"),
            new DiseaseData("小白菜","https://cn.bing.com/th?id=OIP.SylOjxs67YhdgrQ5rW7RQwHaHa&pid=Api&rs=1","xiaobaicai"),
            new DiseaseData("苋菜","https://cn.bing.com/th?id=OIP.5ng1FtMeK1P7YXg_w83igQHaFj&pid=Api&rs=1","jiecai")
    };

    public static DiseaseData[] CropList2 = {
            new DiseaseData("芝麻","https://cn.bing.com/th?id=OIP.zoHsUXJWvHAPxSugbIHgBgHaHf&pid=Api&rs=1","zhima"),
            new DiseaseData("花生","http://img02.tooopen.com/images/20150819/tooopen_sy_138895664793.jpg","huasheng"),
            new DiseaseData("茶叶","https://www.1616n.com/upload/resources/image/2017/07/27/656649.jpg","chaye"),
            new DiseaseData("蓖麻","http://pic.baike.soso.com/p/20130408/20130408154305-69638433.jpg","bima"),
            new DiseaseData("烟草","https://cn.bing.com/th?id=OIP.e-GBr6tb7QxIYCs17zi7GAHaFj&pid=Api&rs=1","yancao"),
            new DiseaseData("甘薯","http://a4.att.hudong.com/54/12/01300000180919121697121473963.jpg","ganshu"),
            new DiseaseData("向日葵","http://a4.att.hudong.com/66/91/01000000000000119089130220666.jpg","xiangrikui"),
            new DiseaseData("木薯","https://image.tcmwiki.com/image/%E6%9C%A8%E8%96%AF/%E6%9C%A8%E8%96%AF.jpg","mushu"),
            new DiseaseData("桑树","http://www.114nz.com/all_images/tuku/fl/200909290904597902.jpg","sangshu"),
            new DiseaseData("花类药材","http://www.114nz.com/all_images/tuku/fl/200912301644032610.jpg","hualeiyaocai"),
            new DiseaseData("根茎类药材","http://www.114nz.com/all_images/tuku/fl/200912301646281135.jpg","genjigenjingleiyaocai"),
            new DiseaseData("叶用药材","http://www.114nz.com/all_images/tuku/fl/200912301651595845.jpg","caoleiyaocaijiyeyongyaocai"),
            new DiseaseData("皮类药材","http://www.114nz.com/all_images/tuku/fl/200912301655472810.jpg","pileiyaocaibinghai"),
    };

    public static DiseaseData[] CropList3 = {
            new DiseaseData("苹果","http://game.hg0355.com/game/xpg/logo.jpg","pingguo"),
            new DiseaseData("葡萄","http://file06.16sucai.com/2016/0919/61d2ad640028401f16d7d1fd94d16d2b.jpg","putao"),
            new DiseaseData("梨","https://www.olive-hitomawashi.com/column/80cce6747bed9ee9440d89af18b47ba6931a3448.jpg","li"),
            new DiseaseData("桃","https://cn.bing.com/th?id=OIP.V4cSc4-0EWyjTLFQMsJRegHaE8&pid=Api&rs=1","tao"),
            new DiseaseData("草莓","https://cn.bing.com/th?id=OIP.Qbnce6uj0xNxfER2hr_yMAHaEo&pid=Api&rs=1","caomei"),
            new DiseaseData("西瓜","http://d6.yihaodianimg.com/N00/M02/5E/A4/CgQCtlGZ2NWAbSV6AAJPZJxjNOk06200.jpg","xigua"),
            new DiseaseData("甘蔗","http://pic7.photophoto.cn/20080612/0020033078507471_b.jpg","ganzhe"),
            new DiseaseData("甜瓜","http://pic.qqtn.com/up/2017-5/201705091441151745527.png","tiangua"),
            new DiseaseData("香蕉","http://www.114nz.com/all_images/tuku/fl/200909281554147130.jpg","xiangjiao"),
            new DiseaseData("枸杞","http://www.114nz.com/all_images/tuku/fl/200909281555065163.jpg","gouqi"),
            new DiseaseData("杏","http://www.114nz.com/all_images/tuku/fl/200909281556594769.jpg","xing"),
            new DiseaseData("枣","http://www.114nz.com/all_images/tuku/fl/200909281557141963.jpg","zao"),
            new DiseaseData("荔枝","http://www.114nz.com/all_images/tuku/fl/200909281557478894.jpg","lizhi"),
            new DiseaseData("樱桃","http://www.114nz.com/all_images/tuku/fl/200909281555139318.jpg","yingtao"),
            new DiseaseData("芒果","http://www.114nz.com/all_images/tuku/fl/200909281600085021.jpg","mangguo"),
            new DiseaseData("柑橘","http://www.114nz.com/all_images/tuku/fl/200909281601598480.jpg","ganju"),
            new DiseaseData("橙","http://www.114nz.com/all_images/tuku/fl/200909281602256447.jpg","cheng"),
            new DiseaseData("柠檬","http://www.114nz.com/all_images/tuku/fl/200909281602131190.jpg","ningmeng"),
            new DiseaseData("柚","http://www.114nz.com/all_images/tuku/fl/200909281602472379.jpg","you"),
            new DiseaseData("菠萝","http://www.114nz.com/all_images/tuku/fl/200909281603521220.jpg","boluo"),
            new DiseaseData("椰子","http://www.114nz.com/all_images/tuku/fl/200909281604068316.jpg","yezi"),
    };


    public static DiseaseData[] CropList4 = {
            new DiseaseData("玉米","https://cn.bing.com/th?id=OIP.BTA-AyZROm9sj9JFrKpE6gHaF7&pid=Api&rs=1","yumi"),
            new DiseaseData("水稻","https://cn.bing.com/th?id=OIP.DuAkfbnHhXVjgpbMKMkyswHaFM&pid=Api&rs=1","shuidao"),
            new DiseaseData("大麦","https://cn.bing.com/th?id=OIP.pxuy8n3hgCrTgPod0KQQ_wHaDt&pid=Api&rs=1","damai"),
            new DiseaseData("小麦","https://tse2-mm.cn.bing.net/th/id/OIP.8lf0N-6ucKpoCuUlxq3ZugHaFj?pid=Api&rs=1","xiaomai"),
            new DiseaseData("棉花","http://img.qnong.com.cn/uploadfile/2016/0309/20160309080932770.jpg","mianhua"),
            new DiseaseData("土豆","https://cn.bing.com/th?id=OIP.fBmV-BZLMMTMi_qm3MnFxQHaGR&pid=Api&rs=1","tudou"),
            new DiseaseData("油菜","https://tse1-mm.cn.bing.net/th/id/OIP.H6JoKOeqsNqhr0_Ku9-XpgHaFj?pid=Api&rs=1","youcai"),
            new DiseaseData("大豆","https://cn.bing.com/th?id=OIP.Eww7Cgag6EG3M6pnpqLM2QHaE8&pid=Api&rs=1","dadou"),
            new DiseaseData("高粱","https://cn.bing.com/th?id=OIP._H_lgGLAmCzRXkjTIfs1AgHaFj&pid=Api&rs=1","gaoliang"),
            new DiseaseData("绿豆","https://cn.bing.com/th?id=OIP.TGNgu0X_WDX0ZOOHRttNQQHaGU&pid=Api&rs=1","lvdou"),
            new DiseaseData("蚕豆","https://cn.bing.com/th?id=OIP._6280Hq3xW9viFKxTKzemAHaFQ&pid=Api&rs=1","candou"),
            new DiseaseData("荞麦","https://static.baicaolu.com/uploads/201507/1436285026i8NwYdcR.jpg","qiaomai"),
            new DiseaseData("麻类","http://n.sinaimg.cn/translate/88/w500h388/20181201/Vlp1-hpevhcm5855739.jpg","malei"),
    };
}
