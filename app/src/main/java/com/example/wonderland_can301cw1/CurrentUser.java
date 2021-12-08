package com.example.wonderland_can301cw1;

import org.litepal.crud.LitePalSupport;

public class CurrentUser extends LitePalSupport {

    private long user_id;

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

}
