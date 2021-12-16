package com.example.wonderland_can301cw1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ManageDatabase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_database);
        Button createDatabase = (Button) findViewById(R.id.create_db);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connector.getDatabase();
            }
        });

        Button addData = (Button) findViewById(R.id.add_db);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String printTxtPath = getApplicationContext().getFilesDir().getAbsolutePath();
                System.out.println(printTxtPath);

                Category cat1=new Category();
                cat1.setName("Postgraduate Studies");

                Category cat2=new Category();
                cat2.setName("In-class Sources");
                Category cat3=new Category();
                cat3.setName("Language Tests");
                Category cat4=new Category();
                cat4.setName("Research and Internship");
                cat1.save();
                cat2.save();
                cat3.save();
                cat4.save();


                User caballo = new User();
                caballo.setName("Caballo");
                caballo.setPassword("123456");
                caballo.setBio("Year 4 ICS Student");
                caballo.setImage(R.drawable.face2);

                caballo.save();

                User ethan = new User();
                ethan.setName("Ethan");
                ethan.setPassword("123456");
                ethan.setBio("Year 4 ICS Student");
                ethan.setImage(R.drawable.face1);
                ethan.save();

                User lullaby = new User();
                lullaby.setName("Lullaby");
                lullaby.setPassword("123456");
                lullaby.setBio("Year 4 ICS Student");
                lullaby.setImage(R.drawable.face3);
                lullaby.save();
                User calvin = new User();
                calvin.setName("Calvin");
                calvin.setPassword("123456");
                calvin.setBio("Year 4 ICS Student");
                calvin.setImage(R.drawable.face4);
                calvin.save();
                User ssumire=new User();
                ssumire.setName("Ssumire");
                ssumire.setPassword("123456");
                ssumire.setBio("Year 4 ICS Student");
                ssumire.setImage(R.drawable.face5);
                ssumire.save();

                Post post1 = new Post();
                Post post2 = new Post();
                Post post3 = new Post();
                Post post4 = new Post();
//                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                Date date = new Date(System.currentTimeMillis());

                post1.setCreate_time(date);
                post1.setTitle("Asking help for postgraduate schools selection");
                post1.setContent("Here is my background:\n" +
                        "GPA:3.8/4.0 TOEFL:99(R27 L27 W23 S22) GRE:320(Q:169 V:151)\n"+"" +
                        "With RA in XJTLU and Internship in Seasun Company\n"+"" +
                        "Wanna major in CS masters, please help with the school selection ,thx!"
                );
                post1.setUser(caballo);
                post1.setCategory(cat1);
                post1.save();

                post2.setCreate_time(date);
                post2.setTitle("Question for postgraduate materials preparation ");
                post2.setContent("What is the difference between PS and SOP?");
                post2.setUser(lullaby);
                post2.setCategory(cat1);
                post2.save();


                post3.setCreate_time(date);
                post3.setTitle("Experience on TOEFL tests");
                post3.setContent("I will share my experience in how to prepare for TOEFL test in this post");
                post3.setUser(ethan);
                post3.setCategory(cat3);
                post3.save();

                post4.setCreate_time(date);
                post4.setTitle("Problems for INT305 Lecture 6");
                post4.setContent("What is the function of max pooling layer in CNN?");
                post4.setUser(calvin);
                post4.setCategory(cat2);
                post4.save();


                Comment comment1 = new Comment();
                comment1.setContent("Can you share more details of your background?");
                comment1.setPost(post1);
                comment1.setCreate_time(new Date());
                comment1.setUser(ssumire);
                comment1.save();
                Comment comment2 = new Comment();
                comment2.setContent("Which countries are you considering?");
                comment2.setCreate_time(new Date());
                comment2.setPost(post1);
                comment2.setUser(lullaby);
                comment2.save();


            }
        });
        Button updateData = (Button) findViewById(R.id.update_db);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post postToUpdate = new Post();
                postToUpdate.setContent("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh" +
                        "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh" +
                        "哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈啊哈哈哈哈哈哈哈哈哈哈哈" +
                        "哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈啊哈哈哈哈哈"); // raise the price
                postToUpdate.update(3);
            }
        });
        Button deleteButton = (Button) findViewById(R.id.delete_db);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LitePal.deleteAll(Comment.class);
                LitePal.deleteAll(Post.class);
                LitePal.deleteAll(User.class);
                LitePal.deleteAll(Category.class);
            }
        });

        Button queryButton = (Button) findViewById(R.id.query_db);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Post> posts = LitePal.findAll(Post.class);
                for (Post post:posts) {
                    User user = post.getUser();
                    List<Comment> comments = post.getComments();
                    for (Comment comment:comments) {
                        Log.d("MainActivity", comment.getContent());
                    }
                    Log.d("MainActivity", user.getName());
                    Log.d("MainActivity", post.getTitle());
                }
                List<User> users = LitePal.findAll(User.class);
                for (User user:users) {
                    Log.d("MainActivity", "user name is "+user.getName());
                    Log.d("MainActivity", "book author is "+user.getPassword());
                    Log.d("MainActivity", "user bio is "+user.getBio());
                }

            }
        });
    }
}