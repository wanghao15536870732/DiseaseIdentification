package com.example.ywang.diseaseidentification.adapter.disease;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.bean.AgriPosition;
import java.util.HashMap;
import java.util.List;

public class MultiAdapter extends RecyclerView.Adapter {

    private List<AgriPosition.DiseaseFeatureListBean> beanList;
    public static HashMap<Integer, Boolean> isSelected;

    public MultiAdapter(List<AgriPosition.DiseaseFeatureListBean> list) {
        this.beanList = list;
        init();
    }

    // 初始化 设置所有item都为未选择
    public void init() {
        isSelected = new HashMap<>();
        for (int i = 0; i < beanList.size(); i++) {
            isSelected.put(i, false);
        }
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new MultiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MultiViewHolder){
            final MultiViewHolder viewHolder = (MultiViewHolder) holder;
            AgriPosition.DiseaseFeatureListBean bean = beanList.get(position);
            viewHolder.mTvName.setText(bean.getName());
            viewHolder.itemView.setSelected(isSelected.get(position));

            if (mOnItemClickListener != null) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(viewHolder.itemView, viewHolder.getAdapterPosition());
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    class MultiViewHolder extends RecyclerView.ViewHolder{
        TextView mTvName;

        public MultiViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
}
