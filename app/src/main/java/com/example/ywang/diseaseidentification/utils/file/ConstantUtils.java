package com.example.ywang.diseaseidentification.utils.file;

import android.content.Context;

import com.example.ywang.diseaseidentification.bean.CropItem;
import com.example.ywang.diseaseidentification.bean.baseData.DiseaseData;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ConstantUtils {

    public static List<String[]> scoreList = new ArrayList<>();

    public static void getCSV(Context mContext,int source){
        InputStream inputStream = mContext.getResources().openRawResource(source);
        CSVFile csvFile = new CSVFile(inputStream);
        scoreList = csvFile.read();
    }

    /*默认显示玉米的少量疾病*/
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
            new DiseaseData("辣椒","http://pic47.nipic.com/20140905/10395918_204118362000_2.jpg","lajiao"),
            new DiseaseData("芦荟","https://cn.bing.com/th?id=OIP.vSwxH5iwvTgznzeX-x-AOwHaFj&pid=Api&rs=1","luhui"),
            new DiseaseData("萝卜","http://s1.cdn.xiangha.com/shicai/201508/071034157627.jpg/MTAwMHgw","luobo"),
            new DiseaseData("南瓜","http://pic8.nipic.com/20100623/2419558_212717027825_2.jpg","nangua"),
            new DiseaseData("茄子","https://cn.bing.com/th?id=OIP.W7wtukTpIHdOzkWPQHML-QHaEz&pid=Api&rs=1","qiezi"),
            new DiseaseData("芹菜","https://img3.utuku.china.com/500x0/ent/20180330/842389a7-e797-4d52-a35f-7b2bc3b297b9.jpg","qincai"),
            new DiseaseData("丝瓜","https://cn.bing.com/th?id=OIP.peqdszbb-5l8HoEoE4TPWgHaE7&pid=Api&rs=1","sigua"),
            new DiseaseData("蒜","http://pichk.daydaycook.com/production/images/20171204/146d0211-ebd6-444e-b315-77ac69bdc607","suan"),
            new DiseaseData("甜椒","https://cn.bing.com/th?id=OIP.tkS02qLWVhpIkr0EuSZHdgHaFj&pid=Api&rs=1","tianjiao"),
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
            new DiseaseData("木薯","https://image.tcmwiki.com/image/%E6%9C%A8%E8%96%AF/%E6%9C%A8%E8%96%AF.jpg","mushu")
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
    };


    public static DiseaseData[] CropList4 = {
            new DiseaseData("玉米","https://cn.bing.com/th?id=OIP.BTA-AyZROm9sj9JFrKpE6gHaF7&pid=Api&rs=1","yumi"),
            new DiseaseData("水稻","https://cn.bing.com/th?id=OIP.DuAkfbnHhXVjgpbMKMkyswHaFM&pid=Api&rs=1","shuidao"),
            new DiseaseData("大麦","https://cn.bing.com/th?id=OIP.pxuy8n3hgCrTgPod0KQQ_wHaDt&pid=Api&rs=1","damai"),
            new DiseaseData("小麦","http://www.alnaturia.com/wp-content/uploads/2017/07/DSC_0212.jpg","xiaomai"),
            new DiseaseData("棉花","http://img.qnong.com.cn/uploadfile/2016/0309/20160309080932770.jpg","mianhua"),
            new DiseaseData("土豆","https://cn.bing.com/th?id=OIP.fBmV-BZLMMTMi_qm3MnFxQHaGR&pid=Api&rs=1","tudou"),
            new DiseaseData("油菜","http://imgs.nmplus.hk/wp-content/uploads/2016/02/%E6%B2%B9%E8%8F%9C%E8%8A%B107.jpg","youcai"),
            new DiseaseData("大豆","https://cn.bing.com/th?id=OIP.Eww7Cgag6EG3M6pnpqLM2QHaE8&pid=Api&rs=1","dadou"),
            new DiseaseData("高粱","https://cn.bing.com/th?id=OIP._H_lgGLAmCzRXkjTIfs1AgHaFj&pid=Api&rs=1","gaoliang"),
            new DiseaseData("绿豆","https://cn.bing.com/th?id=OIP.TGNgu0X_WDX0ZOOHRttNQQHaGU&pid=Api&rs=1","lvdou"),
            new DiseaseData("蚕豆","https://cn.bing.com/th?id=OIP._6280Hq3xW9viFKxTKzemAHaFQ&pid=Api&rs=1","candou"),
            new DiseaseData("荞麦","https://static.baicaolu.com/uploads/201507/1436285026i8NwYdcR.jpg","qiaomai"),
            new DiseaseData("麻类","http://n.sinaimg.cn/translate/88/w500h388/20181201/Vlp1-hpevhcm5855739.jpg","malei"),
    };

    /*根、茎、叶、花、果实、植株*/
    public static CropItem[] items = {
        new CropItem("https://upload-images.jianshu.io/upload_images/9140378-a5428a598e98769d.png","叶"),
        new CropItem("https://upload-images.jianshu.io/upload_images/9140378-be3f3fef607e7526.png","花"),
        new CropItem("https://upload-images.jianshu.io/upload_images/9140378-404409164ec6d8d1.png","根"),
        new CropItem("https://upload-images.jianshu.io/upload_images/9140378-523faa0544cb8971.png","茎"),
        new CropItem("https://upload-images.jianshu.io/upload_images/9140378-b5fdc46a7bc0b7bd.png","果"),
        new CropItem("https://upload-images.jianshu.io/upload_images/9140378-6528a63eee161045.png","植株"),
    };

    public static String[][] Disease = {
            {"病斑","病斑","黑褐色","枯死","水渍状斑点","暗绿色","坏死大斑","脱落","畸形","轮纹状","灰褐色霉", "黄褐色病斑", "圆形或近圆形病斑", "油渍状", "枯萎", "破裂病斑", "病斑密布", "粘液", "不规则病斑","褐色病斑","凹陷病斑","棉絮状菌丝体"},
            {"膨大","肿胀","歪扭","绿叶变态","白色霉","孢子","圆形或近圆形病斑","畸形","弯曲","肥大","纺锤形病斑","梭形丙班","灰白色病斑","灰褐色病斑","不规则病斑","绿色","长菱形病斑","黑色病斑","黑色霉","破裂","椭圆形病斑","软化","腐烂"},
            {"膨大","孢子","芽管","黄色孢子","球形孢子","肿瘤","矮小","黄色","维管束变褐色","长条形病斑","枯萎","肿胀","刘状物",""},
            {"膨大","孢子","芽管"},
            {"膨大","孢子","芽管"},
            {"膨大","孢子","芽管"},
    };

}
