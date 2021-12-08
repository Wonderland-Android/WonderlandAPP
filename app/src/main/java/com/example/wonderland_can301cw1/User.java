package com.example.wonderland_can301cw1;

import org.litepal.LitePal;
import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

public class User extends LitePalSupport {
    private int id;

    @Column(unique = true,nullable = false)
    private String name;

    private String password;
    private String email;
    private int image;
    @Column(defaultValue = "No Description")
    private String bio;

    private List<Post> posts = new ArrayList<Post>();
    private List<Comment>comments = new ArrayList<Comment>();

    public List<Post> getPosts() {
        return LitePal.where("user_id = ?", String.valueOf(id)).find(Post.class);
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Comment> getComments() {
       return LitePal.where("user_id = ?", String.valueOf(id)).find(Comment.class);
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void setId(){this.id=id;}

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }


}
