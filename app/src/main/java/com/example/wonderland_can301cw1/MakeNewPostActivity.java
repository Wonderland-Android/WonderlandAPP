package com.example.wonderland_can301cw1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.litepal.LitePal;

import java.util.Date;

public class MakeNewPostActivity extends AppCompatActivity {

    private String[] mPostStrs = new String[2];

    public static void actionStart(Context context, long category_id){
        category_id=category_id;
        Intent intent = new Intent(context, PostDetailActivity.class);
        intent.putExtra("category_id", category_id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_new_post);

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