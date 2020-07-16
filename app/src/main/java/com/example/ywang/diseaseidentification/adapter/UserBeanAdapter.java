package com.example.ywang.diseaseidentification.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.bean.baseData.UserBean;

import java.util.ArrayList;
import java.util.List;

public class UserBeanAdapter extends BaseAdapter {

    private Context mContext;

    private List<UserBean> mUserBeanList;

    public UserBeanAdapter(Context context){
        mUserBeanList = new ArrayList<>();
        mContext = context;
    }

    public void replaceData(@NonNull List<UserBean> userBeans) {
        mUserBeanList.clear();
        mUserBeanList.addAll(userBeans);
    }

    @Override
    public int getCount() {
        return mUserBeanList.size();
    }

    @Override
    public UserBean getItem(int position) {
        return mUserBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = View.inflate(mContext, R.layout.item_user_bean,null);

            holder = new ViewHolder();
            holder.mAccountView = convertView.findViewById(R.id.tv_item_account);
            holder.mHeadIconView = convertView.findViewById(R.id.iv_item_head_icon);
            holder.mClearView = convertView.findViewById(R.id.iv_item_clear_account);

            holder.mHeadIconView.setVisibility(View.INVISIBLE);
            holder.mClearView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //移除点击的条目并刷新列表
                    mUserBeanList.remove(position);
                    notifyDataSetChanged();
                }
            });


            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        UserBean user = getItem(position);
        holder.mAccountView.setText(user.getUserId());


        return convertView;
    }



    private class ViewHolder{
        private TextView mAccountView;
        private ImageView mHeadIconView;
        private ImageView mClearView;
    }
}