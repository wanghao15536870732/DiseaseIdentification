package com.example.ywang.diseaseidentification.adapter.disease;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.bean.CropBean;

import de.hdodenhof.circleimageview.CircleImageView;

public class ExpandableAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private String[] groups;
    private CropBean[][] children;

    public ExpandableAdapter(Context mContext,String[] groups, CropBean[][] children) {
        this.mContext =mContext;
        this.groups = groups;
        this.children = children;
    }

    @Override
    public int getGroupCount() {
        return groups.length;
    }

    @Override
    public int getChildrenCount(int i) {
        return children[i].length;
    }

    @Override
    public Object getGroup(int i) {
        return groups[i];
    }

    @Override
    public Object getChild(int i, int i1) {
        return children[i][i1];
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        TextView textView = getGenericView(24);
        textView.setText(getGroup(i).toString());
        return textView;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        View newView = View.inflate(mContext,R.layout.item_main_left,viewGroup);
        CircleImageView image = newView.findViewById(R.id.left_image);
        TextView textView = newView.findViewById(R.id.item_main_left_type);
        CropBean bean = (CropBean) getChild(i,i1);
        Glide.with(mContext).load(bean.getUrl()).into(image);
        textView.setText(bean.getTitle());
        return newView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    //自定义的创建TextView
    public TextView getGenericView(int mTextSize) {
        // Layout parameters for the ExpandableListView
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView textView = new TextView(mContext);
        textView.setLayoutParams(lp);
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        textView.setPadding(42, 12, 12, 12);
        textView.setTextSize(mTextSize);
        textView.setTextColor(Color.BLACK);
        return textView;
    }
}
