package com.example.wonderland_can301cw1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zhuang.likeviewlibrary.LikeView;

import java.text.SimpleDateFormat;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{
    private List<Comment> mCommentList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View commentView;
        ImageView comment_user_image;
        TextView comment_user_name;
        TextView comment_content;
        LikeView comment_likes;
        TextView comment_date;

        public ViewHolder(@NonNull View view) {
            super(view);
            commentView = view;
            comment_user_image = (ImageView) view.findViewById(R.id.comment_user_image);
            comment_user_name = (TextView) view.findViewById(R.id.comment_user_name);
            comment_content = (TextView) view.findViewById(R.id.comment_content);
            comment_likes = (LikeView) view.findViewById(R.id.comment_likeView);
            comment_date = (TextView) view.findViewById(R.id.comment_date);
        }

    }
    public CommentAdapter(List<Comment> commentList){
        mCommentList = commentList;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = mCommentList.get(position);
        holder.comment_content.setText(comment.getContent());
        User user = comment.getUser();
        System.out.println(user);
        holder.comment_user_image.setImageResource(user.getImage());
        holder.comment_user_name.setText(user.getName());
        holder.comment_likes.setLikeCount(comment.getLikes());
        TimeCountUtil timeCountUtil = new TimeCountUtil();
        holder.comment_date.setText(timeCountUtil.timeCount(comment.getCreate_time()));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.commentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAbsoluteAdapterPosition();
                Comment comment = mCommentList.get(position);
                Toast.makeText(v.getContext(),"you clicked view "+comment.getContent(),Toast.LENGTH_SHORT).show();
            }
        });
        holder.comment_user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Comment comment = mCommentList.get(position);
                Toast.makeText(v.getContext(),"you clicked image "+comment.getUser().getName(),Toast.LENGTH_SHORT).show();
            }
        });
        holder.comment_likes.setOnLikeListeners(new LikeView.OnLikeListeners() {
            @Override
            public void like(boolean isCancel) {
                int position = holder.getAbsoluteAdapterPosition();
                Comment comment = mCommentList.get(position);
                if(isCancel){
                    int likes = comment.getLikes();
                    comment.setLikes(likes-1);
                    comment.update(comment.getId());
                    holder.comment_likes.setLikeCount(likes-1);
                }else{
                    int likes = comment.getLikes();
                    comment.setLikes(likes+1);
                    comment.update(comment.getId());
                    holder.comment_likes.setLikeCount(likes+1);
                }
            }
        });
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimeCountUtil timeCountUtil = new TimeCountUtil();
        holder.comment_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAbsoluteAdapterPosition();
                Comment comment = mCommentList.get(position);
                String currentString = holder.comment_date.getText().toString();
                if(currentString.substring(currentString.length()-3).equals("ago")||currentString.equals("yesterday")){
                    holder.comment_date.setText(df.format(comment.getCreate_time()));
                }else{
                    holder.comment_date.setText(timeCountUtil.timeCount(comment.getCreate_time()));
                }
            }
        });

        return holder;
    }


    @Override
    public int getItemCount() {
        return mCommentList.size();
    }
}
