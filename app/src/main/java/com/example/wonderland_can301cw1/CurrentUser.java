package com.example.wonderland_can301cw1;

import org.litepal.crud.LitePalSupport;

public class CurrentUser extends LitePalSupport {
    private int id;

    public int getId() {
        return id;
    }

    private int user_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

}
