package com.example.wonderland_can301cw1;

import android.graphics.drawable.Drawable;

import java.util.Date;

public class Card {
    private int id;
    private int profile;
    private String username;
    private String title;
    private int likeNum;
    private Date time;

    public Card(int id,int profile, String username, String title, int likeNum, Date time) {
        this.id = id;
        this.profile = profile;
        this.username = username;
        this.title = title;
        this.likeNum = likeNum;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public int getProfile() {
        return profile;
    }

    public String getUsername() {
        return username;
    }

    public String getTitle() {
        return title;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public Date getTime() {
        return time;
    }
}
