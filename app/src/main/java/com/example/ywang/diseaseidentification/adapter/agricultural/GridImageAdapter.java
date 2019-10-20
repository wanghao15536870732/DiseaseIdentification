package com.example.ywang.diseaseidentification.adapter.agricultural;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.ywang.diseaseidentification.R;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DateUtils;

import com.luck.picture.lib.tools.StringUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GridImageAdapter extends RecyclerView.Adapter<GridImageAdapter.ViewHolder>{
    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_PICTURE = 2;
    private LayoutInflater mInflater;
    private List<LocalMedia> mList = new ArrayList<>();  //本地Media
    private int selectMax = 9; //图片最大张数
    private Context mContext;


    //点击添加图片跳转
    private onAddPicClickListener mOnAddPicClickListener;

    public interface onAddPicClickListener{
        void onAddPicClick();
    }

    public GridImageAdapter(Context context,onAddPicClickListener onAddPicClickListener){
        this.mContext = context;
        mInflater = LayoutInflater.from( context );
        this.mOnAddPicClickListener = onAddPicClickListener;
    }

    public void setList(List<LocalMedia> list) {
        this.mList = list;
    }

    public void setSelectMax(int selectMax) {
        this.selectMax = selectMax;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate( R.layout.filter_image,parent,false );
        final ViewHolder viewHolder = new ViewHolder( view );
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        //少于8张，显示继续添加的图标
        if(getItemViewType( position ) == TYPE_CAMERA){
            holder.mImageView.setImageResource( R.drawable.addimg_1x );
            holder.mImageView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnAddPicClickListener.onAddPicClick();
                }
            } );
            holder.mLayout.setVisibility( View.INVISIBLE );
        }else {
            holder.mLayout.setVisibility( View.VISIBLE );
            holder.mLayout.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = holder.getAdapterPosition();
                    if(index != RecyclerView.NO_POSITION){
                        mList.remove( index );
                        notifyItemRemoved( index );
                        notifyItemChanged( index,mList.size());
                    }
                }
            } );
            LocalMedia media = mList.get( position );
            int mimeType = media.getMimeType();
            String path = "";
            if(media.isCut() && !media.isCompressed()){
                //裁减过的照片
                path = media.getCutPath();
            }else if (media.isCompressed() || (media.isCut() && media.isCompressed())){
                //压缩过的照片
                path = media.getCompressPath();
            }else{
                //原图
                path = media.getPath();
            }

            //压缩图片
            if(media.isCompressed()){
                Log.i("compress image result:", new File(media.getCompressPath()).length() / 1024 + "k");
                Log.i("压缩地址::", media.getCompressPath());
            }
            int pictureType = PictureMimeType.isPictureType( media.getPictureType() );
            if (media.isCut()){
                Log.i("裁剪地址::",media.getCutPath());
            }
            long duration = media.getDuration();
            holder.mTextView.setVisibility( pictureType == PictureConfig.TYPE_VIDEO ?
                    View.VISIBLE : View.GONE);
            //音频
            if (mimeType == PictureMimeType.ofAudio()){
                holder.mTextView.setVisibility( View.VISIBLE );
                Drawable drawable = ContextCompat.getDrawable( mContext,R.drawable.picture_audio );
                StringUtils.modifyTextViewDrawable( holder.mTextView,drawable,0 );
            }else {
                Drawable drawable = ContextCompat.getDrawable( mContext,R.drawable.video_icon );
                StringUtils.modifyTextViewDrawable( holder.mTextView,drawable,0);
            }
            holder.mTextView.setText( DateUtils.timeParse( duration ) );
            if(mimeType == PictureMimeType.ofAudio()){
                holder.mImageView.setImageResource( R.drawable.audio_placeholder );
            }else {
                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder( R.color.white)
                        .diskCacheStrategy( DiskCacheStrategy.ALL );
                Glide.with( holder.itemView.getContext() )
                        .load( path)
                        .apply( options )
                        .into( holder.mImageView );
            }
            //图片点击事件
            if(mItemClickListener != null){
                holder.itemView.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int adapterPosition = holder.getAdapterPosition();
                        mItemClickListener.onItemClick( adapterPosition,v );
                    }
                } );
            }
        }
    }

    protected OnItemClickListener mItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position,View v);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        if (mList.size() < selectMax){
            return mList.size() + 1;
        }else {
            return mList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView mImageView;
        LinearLayout mLayout;
        TextView mTextView;

        public ViewHolder(View itemView) {
            super( itemView );
            mImageView = (ImageView) itemView.findViewById( R.id.fiv_image );
            mLayout = (LinearLayout) itemView.findViewById( R.id.fiv );
            mTextView = (TextView) itemView.findViewById( R.id.fiv_text );
        }
    }

    private boolean isShowAddItem(int position){
        int size = mList.size();
        return position == size;
    }

    @Override
    public int getItemViewType(int position) {
        if(isShowAddItem( position )){
            return TYPE_CAMERA;
        }else{
            return TYPE_PICTURE;
        }
    }
}