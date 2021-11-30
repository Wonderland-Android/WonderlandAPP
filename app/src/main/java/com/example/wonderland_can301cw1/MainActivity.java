package com.example.wonderland_can301cw1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;

    private EditText fill_username;
    private EditText fill_password;
    private Button sign_up;
    private Button sign_in;
    private CheckBox checkbox;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);


        fill_username = (EditText) findViewById(R.id.fill_username);
        fill_password = (EditText) findViewById(R.id.fill_password);
        sign_up = (Button) findViewById(R.id.sign_up);
        sign_in=  (Button) findViewById(R.id.sign_in);
        sign_up.setOnClickListener(new MyButton());
        sign_in.setOnClickListener(new MyButton());

        Button databaseButton = (Button) findViewById(R.id.manage_database);
        databaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ManageDatabase.class);
                startActivity(intent);
            }
        });
        }





    public class MyButton implements View.OnClickListener{
        @Override
        public void onClick(View view){
            String username =fill_username.getText().toString().trim();
            String password =fill_password.getText().toString().trim();
            switch (view.getId()) {
                case R.id.sign_in:
                    System.out.println("-----------------------------");
                    if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
                        Toast.makeText(MainActivity.this,"Password or username cannot be empty",Toast.LENGTH_SHORT).show();
                    }
                    else {

                        Toast.makeText(MainActivity.this,"Log in successfully",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, NaviBarActivity.class);
                        startActivity(intent);
                        }
                    break;
                case R.id.sign_up:
                    Intent intent = new Intent(MainActivity.this,SignUp.class);
                    startActivity(intent);
                    break;
                    }


            }

        }

    }




