package com.example.wonderland_can301cw1;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

public class Category extends LitePalSupport {
    private int id;
    private String name;
    private List<Post> posts = new ArrayList<Post>();

    public List<Post> getPosts() {
        return LitePal.where("category_id = ?", String.valueOf(id)).find(Post.class);
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
