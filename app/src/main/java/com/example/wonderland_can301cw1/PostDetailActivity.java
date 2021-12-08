package com.example.wonderland_can301cw1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.litepal.LitePal;

import java.util.List;

public class PostDetailActivity extends AppCompatActivity {
    private TextView postdetail_title;
    private ImageView postdetail_user_image;
    private TextView postdetail_user_name;
    private TextView postdetail_content;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        Post post = LitePal.find(Post.class,1);
        User user = post.getUser();
        postdetail_title = (TextView) findViewById(R.id.postdetail_title);
        postdetail_content = (TextView) findViewById(R.id.postdetail_content);
        postdetail_user_image = (ImageView) findViewById(R.id.postdetail_user_image);
        postdetail_user_name = (TextView)  findViewById(R.id.postdetail_user_name);
        postdetail_title.setText(post.getTitle());
        postdetail_content.setText(post.getContent());
        postdetail_content.setMovementMethod(ScrollingMovementMethod.getInstance());
        postdetail_user_name.setText(user.getName());
        postdetail_user_image.setImageResource(R.drawable.face1);

        List<Comment> commentList = post.getComments();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.comment_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        CommentAdapter adapter = new CommentAdapter(commentList);
        recyclerView.setAdapter(adapter);

    }

}