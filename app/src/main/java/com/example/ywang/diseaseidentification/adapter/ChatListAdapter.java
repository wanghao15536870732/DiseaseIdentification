package com.example.ywang.diseaseidentification.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.bean.ChatListData;

import java.util.List;

public class ChatListAdapter extends BaseAdapter {

    //左边的type
    public static final int VALUE_LEFT_TEXT = 1;
    //左边的type
    public static final int VALUE_RIGHT_TEXT = 2;

    private Context context;
    private LayoutInflater inflater;
    //实体类
    private ChatListAdapter data;
    private List<ChatListData> mList;

    public ChatListAdapter(Context context,List<ChatListData> mList){
        this.context = context;
        this.mList = mList;
        //获取系统服务
        inflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolderLeftText viewHolderLeftText = null;
        ViewHolderRightText viewHolderRightText = null;
        //获取当前要显示的type，根据这个type来区分数据的加载
        int type = getItemViewType(i);
        if(view == null){
            switch (type){
                case VALUE_LEFT_TEXT:
                    viewHolderLeftText = new ViewHolderLeftText();
                    view = inflater.inflate(R.layout.left_item,viewGroup,false);
                    viewHolderLeftText.tv_left_text = (TextView) view.findViewById(R.id.tv_left_text);
                    view.setTag(viewHolderLeftText);
                    break;
                case VALUE_RIGHT_TEXT:
                    viewHolderRightText = new ViewHolderRightText();
                    view = inflater.inflate(R.layout.right_item,viewGroup,false);
                    viewHolderRightText.tv_right_text = (TextView) view.findViewById(R.id.tv_right_text);
                    view.setTag(viewHolderRightText);
                    break;
            }
        }else {
            switch (type){
                case VALUE_LEFT_TEXT:
                    viewHolderLeftText = (ViewHolderLeftText) view.getTag();
                    break;
                case VALUE_RIGHT_TEXT:
                    viewHolderRightText = (ViewHolderRightText) view.getTag();
                    break;
            }
        }

        //赋值
        ChatListData data = mList.get(i);
        switch(type){
            case VALUE_LEFT_TEXT:
                viewHolderLeftText.tv_left_text.setText(data.getText());
                break;
            case VALUE_RIGHT_TEXT:
                viewHolderRightText.tv_right_text.setText(data.getText());
                break;
        }
        return view;
    }

    //根据数据源的position来返回要显示的item
    @Override
    public int getItemViewType(int position) {
        ChatListData data = mList.get(position);
        int type = data.getType();
        return type;
    }

    //返回所有Layout数据
    @Override
    public int getViewTypeCount() {
        return 3;
    }

    //左边的文本
    class ViewHolderLeftText{
        private TextView tv_left_text;
    }

    class ViewHolderRightText{
        private TextView tv_right_text;
    }
}
