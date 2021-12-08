package com.example.wonderland_can301cw1;

import org.litepal.LitePal;
import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Post extends LitePalSupport {
    private Date create_time;
    private int id;
    private int user_id;
    private int category_id;
    @Column(nullable = false)
    private String title;
    private String content;
    private int likes;
    private List<Comment>comments = new ArrayList<Comment>();
    private User user;
    private Category category;

    public Category getCategory() {
       return LitePal.find(Category.class,category_id);
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return LitePal.find(User.class,user_id);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId(){ return id; }

    public List<Comment> getComments() {
        return LitePal.where("post_id = ?", String.valueOf(id)).find(Comment.class);
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
