package com.example.wonderland_can301cw1;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.Date;

public class Comment extends LitePalSupport {
    private int id;
    private int user_id;
    private int post_id;
    private Date create_time;
    private String content;
    private int likes;

    private User user;
    private Post post;

    public User getUser() {
        return LitePal.find(User.class,user_id);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return LitePal.find(Post.class,post_id);
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
