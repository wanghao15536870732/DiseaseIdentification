package com.example.ywang.diseaseidentification.bean;

import java.util.ArrayList;
import java.util.List;

public class DiseaseResult {

    /**
     * features :
     * score : 4.300524711608887
     * img : http://wnd.agri114.cn/file/disease/1445158999169.jpg;http://wnd.agri114.cn/file/disease/1445158999170.jpg;
     *      http://wnd.agri114.cn/file/disease/1445158999172.jpg;http://wnd.agri114.cn/file/disease/1445158999173.jpg;
     *      http://wnd.agri114.cn/file/disease/1445158999174.jpg
     * productId : 0
     * featurePosition :
     * id : 3878
     * title : 白菜链格孢黑斑病
     * featureDetail :
     * content : <p>报检植物：白菜</p><p>报检类型：病害</p><p>检测认定：白菜链格孢黑斑病</p><p>中文名称：白菜链格孢黑斑病
     *      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p><p>中文别名：</p><p>病原中文名称：萝卜链格孢</p><p>病原分类地位：真菌界、半知菌类、丝孢纲、丝孢目、
     *      暗丛梗孢科、链格孢属</p><p>病害类型：真菌</p><p>主要寄主：十字花科</p><p>危害部位：叶片</p><p>传播因子：留种母株、种子、病残体、土壤</p><p>
     *      防治指标：初见病叶或有发病中心时</p><p>检疫地位：非检疫对象</p><p>危害症状：</p><p>&nbsp;&nbsp;&nbsp;萝卜链格孢引起的结球白菜、普通白菜等
     *      黑斑病......
     * featureId : 0
     * productName :
     * productAlias :
     */

    private double score;
    private List<String> img = new ArrayList<>();
    private int id;
    private String title;
    private String content;

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
