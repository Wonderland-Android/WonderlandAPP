package com.example.wonderland_can301cw1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MakeNewPostActivity extends AppCompatActivity {

    private String[] mPostStrs = new String[2];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_new_post);

        EditText titleText = findViewById(R.id.Post_Title);
        EditText contentText = findViewById(R.id.Post_Content);

        Button ensureButton = (Button)findViewById(R.id.Post_Ensure);
        ensureButton.setOnClickListener(v -> {
            String title = titleText.toString();
            String content = contentText.toString();
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

            Intent returnIntent = new Intent();
            mPostStrs[0] = title;
            mPostStrs[1] = content;
            returnIntent.putExtra("Post_Return",mPostStrs);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        });
    }
}