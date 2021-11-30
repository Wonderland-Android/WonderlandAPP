package com.example.wonderland_can301cw1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

public class ViewPostsActivity extends AppCompatActivity {
    ImageButton Btn1;
    private CardView post1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_posts);
        Btn1=(ImageButton)findViewById(R.id.new_post);
        Btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ViewPostsActivity.this,MakeNewPostActivity.class);
                startActivity(intent);
            }
        });
        post1 = (CardView)  findViewById(R.id.card1);
        post1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewPostsActivity.this, PostDetailActivity.class);
                startActivity(intent);
            }
        });
    }
}