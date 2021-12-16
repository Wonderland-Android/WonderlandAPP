package com.example.wonderland_can301cw1;

import org.litepal.crud.LitePalSupport;

public class LikesManage extends LitePalSupport {
    private int id;

    public long getId() {
        return id;
    }

    private int user_id;
    private int post_id;
    private int comment_id;

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }
}
