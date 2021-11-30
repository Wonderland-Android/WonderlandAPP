package com.example.wonderland_can301cw1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{
    private List<Comment> mCommentList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View commentView;
        ImageView comment_user_image;
        TextView comment_user_name;
        TextView comment_content;

        public ViewHolder(@NonNull View view) {
            super(view);
            commentView = view;
            comment_user_image = (ImageView) view.findViewById(R.id.comment_user_image);
            comment_user_name = (TextView) view.findViewById(R.id.comment_user_name);
            comment_content = (TextView) view.findViewById(R.id.comment_content);
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
        holder.comment_user_image.setImageResource(R.drawable.face2);
        holder.comment_user_name.setText(user.getName());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.commentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
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
        return holder;
    }


    @Override
    public int getItemCount() {
        return mCommentList.size();
    }
}
