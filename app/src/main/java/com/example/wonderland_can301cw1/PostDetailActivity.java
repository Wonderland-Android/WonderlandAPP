package com.example.wonderland_can301cw1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zhuang.likeviewlibrary.LikeView;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class PostDetailActivity extends AppCompatActivity {
    private TextView postdetail_title;
    private ImageView postdetail_user_image;
    private TextView postdetail_user_name;
    private TextView postdetail_content;
    private TextView postDate;
    private LikeView post_likes;
    private List<Comment> commentList = new ArrayList<>();
    private CommentAdapter adapter;
    private TextView commentsNumber;
    private Post post;



    public static void actionStart(Context context, long post_id){
        Intent intent = new Intent(context, PostDetailActivity.class);
        intent.putExtra("post_id", post_id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar !=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        Intent intent = getIntent();
        post = LitePal.find(Post.class,intent.getLongExtra("post_id",3));
        User user = post.getUser();
        postdetail_title = (TextView) findViewById(R.id.postdetail_title);
        postdetail_content = (TextView) findViewById(R.id.postdetail_content);
        postdetail_user_image = (ImageView) findViewById(R.id.postdetail_user_image);
        postdetail_user_name = (TextView)  findViewById(R.id.postdetail_user_name);
        post_likes = (LikeView) findViewById(R.id.post_likeView);
        postDate = (TextView) findViewById(R.id.postdetail_date);
        post_likes.setLikeCount(post.getLikes());
        post_likes.setOnLikeListeners(new LikeView.OnLikeListeners() {
            @Override
            public void like(boolean isCancel) {
                if(isCancel){
                    int likes = post.getLikes();
                    post.setLikes(likes-1);
                    post.update(post.getId());
                    post_likes.setLikeCount(likes-1);
                }else{
                    int likes = post.getLikes();
                    post.setLikes(likes+1);
                    post.update(post.getId());
                    post_likes.setLikeCount(likes+1);
                }
            }
        });
        postdetail_title.setText(post.getTitle());
        postdetail_content.setText(post.getContent());
        postdetail_content.setMovementMethod(ScrollingMovementMethod.getInstance());
        postdetail_user_name.setText(user.getName());
        postdetail_user_image.setImageResource(user.getImage());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimeCountUtil timeCountUtil = new TimeCountUtil();
        postDate.setText(timeCountUtil.timeCount(post.getCreate_time()));
        postDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentString = postDate.getText().toString();
                if(currentString.substring(currentString.length()-3).equals("ago")){
                    postDate.setText(df.format(post.getCreate_time()));
                }else{
                    postDate.setText(timeCountUtil.timeCount(post.getCreate_time()));
                }
            }
        });

        commentList = post.getComments();
        int commentNum = commentList.size();
        commentsNumber =(TextView) findViewById(R.id.comments_number);
        commentsNumber.setText(" "+commentNum+" comments in total");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.comment_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CommentAdapter(commentList);
        recyclerView.setAdapter(adapter);

        FloatingActionButton commentButton = (FloatingActionButton) findViewById(R.id.comment_button);
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CommentDialog commentDialog = new CommentDialog(PostDetailActivity.this);
                commentDialog.setHintText("Reply...")
                        .setOnBtnCommitClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String comment = commentDialog.getContent();
                                Comment newComment = new Comment();
                                CurrentUser currentUser = LitePal.findFirst(CurrentUser.class);
                                newComment.setUser(LitePal.find(User.class,currentUser.getUser_id()));
                                newComment.setPost(post);
                                newComment.setCreate_time(new Date());
                                newComment.setContent(comment);
                                newComment.save();
                                refreshComments();
                                commentDialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }

    private void refreshComments(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Post post = LitePal.find(Post.class,3);
                        commentList.clear();
                        List<Comment> newCommentList =post.getComments();
                        commentList.addAll(newCommentList);
                        int commentNum = commentList.size();
                        commentsNumber.setText(" "+commentNum+" comments in total");
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();

    }

}