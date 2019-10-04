package com.example.ywang.diseaseidentification.bean;

import com.example.ywang.diseaseidentification.view.CommentListTextView;

import java.util.ArrayList;
import java.util.List;

public class NineGridModel {
    public List<String> urlList = new ArrayList<>();
    public boolean isShowAll = false;
    public String detail;
    public int image;
    public String imageUri;
    public String name;
    public String time;
    public String start;
    public List<CommentListTextView.CommentInfo> mCommentInfos = new ArrayList<> ();
}
