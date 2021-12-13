package com.example.wonderland_can301cw1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.litepal.LitePal;

import java.util.Date;
import java.util.List;

public class MakeNewPostActivity extends AppCompatActivity {

    private String[] mPostStrs = new String[2];
    private ImageView nav_head;
    private TextView nav_name;
    private TextView nav_mail;

    private void initNavi(){
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        if(navView.getHeaderCount()>0){
            View header = navView.getHeaderView(0);
            nav_head = header.findViewById(R.id.icon_image);
            nav_name = header.findViewById(R.id.username);
            nav_mail = header.findViewById(R.id.mail);
        }
        CurrentUser currentUser = LitePal.findFirst(CurrentUser.class);
        User naviUser = LitePal.find(User.class,currentUser.getUser_id());
        nav_head.setImageResource(naviUser.getImage());
        nav_name.setText(naviUser.getName());
        nav_mail.setText(naviUser.getEmail());
        DrawerLayout mDrawerlayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        ImageView personal_menu = (ImageView) findViewById(R.id.top_right);
        personal_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerlayout.openDrawer(GravityCompat.END);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_make_new_post);

        initNavi();
        Intent intent = getIntent();
        int data = intent.getIntExtra("cat_id",-1);
        List<Category> cats = LitePal
                .where("id = ?", String.valueOf(data)).find(Category.class);
        Category cat_name=cats.get(0);
        EditText titleText =(EditText) findViewById(R.id.Post_Title);
        EditText contentText = (EditText)findViewById(R.id.Post_Content);

        Button ensureButton = (Button)findViewById(R.id.Post_Ensure);
        ensureButton.setOnClickListener(v -> {

            String title = titleText.getText().toString().trim();
            String content = contentText.getText().toString().trim();
            if (title == "")
            {
                Toast.makeText(MakeNewPostActivity.this, "Empty Title !", Toast.LENGTH_SHORT).show();
                return;
            }

            if (content == "")
            {
                Toast.makeText(MakeNewPostActivity.this, "Empty Content !", Toast.LENGTH_SHORT).show();
                return;
            }

            Post newPost = new Post();
            CurrentUser currentUser = LitePal.findFirst(CurrentUser.class);
            newPost.setUser(LitePal.find(User.class,currentUser.getUser_id()));
            newPost.setCreate_time(new Date());
            newPost.setTitle(title);
            newPost.setContent(content);
            newPost.setCategory(cat_name);
            newPost.save();

            Intent returnIntent = new Intent();
            mPostStrs[0] = title;
            mPostStrs[1] = content;
            returnIntent.putExtra("Post_Return",mPostStrs);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        });
    }
}