package com.example.ywang.diseaseidentification.adapter;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.bean.NineGridModel;
import com.example.ywang.diseaseidentification.view.CommentListTextView;

import java.util.List;

public class NineGridAdapter extends  RecyclerView.Adapter<NineGridAdapter.ViewHolder> {

    private Context mContext;
    private List<NineGridModel> mList;
    protected LayoutInflater inflater;
    private View convertView;
    NineGridModel nineGridModel;

    public NineGridAdapter(Context context){
        mContext = context;
        inflater = LayoutInflater.from( context );
    }

    public void setList(List<NineGridModel> list){
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        convertView = inflater.inflate( R.layout.item_nine_grid,parent,false );
        ViewHolder viewHolder = new ViewHolder( convertView );
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        nineGridModel = mList.get( position );
        holder.layout.setIsShowAll( nineGridModel.isShowAll );
        holder.layout.setUrlList( nineGridModel.urlList );
        convertView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nineGridModel = mList.get( position );

            }
        } );

        Glide.with( mContext ).load(nineGridModel.imageUri).into( holder.mImageView );
        holder.contact_name.setText( nineGridModel.name );
        holder.time.setText( nineGridModel.time );
        holder.mImageView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        } );
        holder.question_detail.setText( nineGridModel.detail );
        holder.comment.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        } );
        holder.mCommentListTextView.setMaxlines (6);
        holder.mCommentListTextView.setMoreStr ("查看更多");
        holder.mCommentListTextView.setNameColor (Color.parseColor ("#fe671e"));
        holder.mCommentListTextView.setCommentColor (Color.parseColor ("#242424"));
        holder.mCommentListTextView.setTalkStr ("回复");
        holder.mCommentListTextView.setTalkColor (Color.parseColor ("#242424"));
        if(nineGridModel.mCommentInfos.size() != 0){
            holder.mCommentListTextView.setData (nineGridModel.mCommentInfos);
        }
    }

    @Override
    public int getItemCount() {
        return getListSize( mList );
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        NineGridLayout layout;
        ImageView mImageView,comment;
        TextView contact_name,time,question_detail;
        CommentListTextView mCommentListTextView;

        public ViewHolder(View itemView) {
            super( itemView );
            layout = (NineGridLayout) itemView.findViewById( R.id.layout_nine_grid);
            mImageView = (ImageView) itemView.findViewById( R.id.image );
            contact_name = (TextView) itemView.findViewById( R.id.name );
            time = (TextView) itemView.findViewById( R.id.time );
            question_detail = (TextView) itemView.findViewById( R.id.detail);
            comment = (ImageView) itemView.findViewById( R.id.comment );
            mCommentListTextView = (CommentListTextView) itemView.findViewById (R.id.commentlist);
        }
    }

    private int getListSize(List<NineGridModel> list){
        if (list == null || list.size() == 0){
            return 0;
        }
        return list.size();
    }

}
