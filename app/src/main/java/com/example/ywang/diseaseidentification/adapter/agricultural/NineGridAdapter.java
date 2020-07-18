package com.example.ywang.diseaseidentification.adapter.agricultural;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.bean.NineGridModel;

import java.util.List;

public class NineGridAdapter extends RecyclerView.Adapter<NineGridAdapter.ViewHolder> {

    private Context mContext;
    private List<NineGridModel> mList;
    protected LayoutInflater inflater;
    private View convertView;
    NineGridModel nineGridModel;

    public NineGridAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    public void setList(List<NineGridModel> list) {
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        convertView = inflater.inflate(R.layout.item_nine_grid, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        nineGridModel = mList.get(position);
        holder.layout.setIsShowAll(nineGridModel.isShowAll());
        holder.layout.setUrlList(nineGridModel.getUrlList());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nineGridModel = mList.get(position);
            }
        });

        Glide.with(mContext).load(nineGridModel.getImageUri()).into(holder.mImageView);
        holder.contact_name.setText(nineGridModel.getName());
        holder.time.setText(nineGridModel.getTime());

        SpannableString spanString = new SpannableString("删除");
        spanString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "删除", Toast.LENGTH_SHORT).show();
            }
        }, 0, 1, Spanned.SPAN_MARK_MARK);

        holder.delete_text.setText(spanString);
        holder.delete_text.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件

        holder.question_detail.setText(nineGridModel.getDetail());
        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog dialog = new BottomSheetDialog(mContext,R.style.BottomSheetStyle);
                View commentView = LayoutInflater.from(mContext).inflate(R.layout.comment_dialog_layout, null);
                final EditText commentText = commentView.findViewById(R.id.dialog_comment_et);
                final Button bt_comment = commentView.findViewById(R.id.dialog_comment_bt);
                dialog.setContentView(commentView);
                /**
                 * 解决bsd显示不全的情况
                 */
                View parent = (View) commentView.getParent();
                BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
                commentView.measure(0, 0);
                behavior.setPeekHeight(commentView.getMeasuredHeight());

                bt_comment.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        String commentContent = commentText.getText().toString().trim();
                        if (!TextUtils.isEmpty(commentContent)) {
                            dialog.dismiss();
                        } else {
                            Toast.makeText(mContext, "评论内容不能为空", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return getListSize(mList);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        NineGridLayout layout;
        ImageView mImageView, comment;
        TextView contact_name, time, question_detail, delete_text;


        public ViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout_nine_grid);
            mImageView = itemView.findViewById(R.id.image);
            contact_name = itemView.findViewById(R.id.name);
            time = itemView.findViewById(R.id.time);
            question_detail = itemView.findViewById(R.id.detail);
            comment = itemView.findViewById(R.id.comment);
            delete_text = itemView.findViewById(R.id.delete_text);
        }
    }

    private int getListSize(List<NineGridModel> list) {
        if (list == null || list.size() == 0) {
            return 0;
        }
        return list.size();
    }

}
