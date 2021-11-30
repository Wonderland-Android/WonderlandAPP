package com.example.wonderland_can301cw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    private EditText fill_in_email;
    private EditText register_username;
    private EditText register_password;
    private EditText confirm_password;
    private Button sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        fill_in_email = (EditText) findViewById(R.id.fill_in_email);
        register_username = (EditText) findViewById(R.id.register_username);
        register_password = (EditText) findViewById(R.id.register_password);
        confirm_password = (EditText) findViewById(R.id.confirm_password);
        sign_up = (Button) findViewById(R.id.sign_up);
        sign_up.setOnClickListener(new RegisterButton());

    }
    public class RegisterButton implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            String user_email = fill_in_email.getText().toString().trim();
            String username = register_username.getText().toString().trim();
            String password = register_password.getText().toString().trim();
            String conf_password = confirm_password.getText().toString().trim();
            switch (v.getId()) {
                case R.id.sign_up:
                    // Judge whether there are empty spaces
                    if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(conf_password) || TextUtils.isEmpty(user_email)) {
                        Toast.makeText(SignUp.this, "You didn't fill in all the necessary information!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        // Judge the e-mail format is correct or not
                        int index1=user_email.lastIndexOf("@");
                        int index2=user_email.lastIndexOf(".");
                        if (index1 != -1 && index2 > index1) {
                        // e-mail format is correct,next step:whether the two passwords are same
                            if(TextUtils.equals(password, conf_password)){
                                // boolean a =SaveInfo.SaveInformation(SignUp.this,username,password,conf_password,user_email);

                                //Toast.makeText(SignUp.this, "E-mail address is not correct!", Toast.LENGTH_SHORT).show();
                                Toast.makeText(SignUp.this,"Register successfully,now you can log in with the account",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(SignUp.this, "The two passwords must be the same", Toast.LENGTH_SHORT).show();
                            }
                        }
                        // e-mail format is not correct
                        else {
                            Toast.makeText(SignUp.this, "E-mail address is not correct!", Toast.LENGTH_SHORT).show();
                        }

                    }
                    break;



            }
        }

    }

}

