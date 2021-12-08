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

import org.litepal.LitePal;

import java.util.List;

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
            switch (v.getId()){
                case R.id.sign_up:
                    // Judge whether the user didn't fill all the four text fields
                    // User has not filled all the four text fields
                    if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(conf_password) || TextUtils.isEmpty(user_email)) {
                        Toast.makeText(SignUp.this, "You didn't fill in all the necessary information!", Toast.LENGTH_SHORT).show();
                    }
                    // User has filled all the four text fields
                    else{
                        int index1=user_email.lastIndexOf("@");
                        int index2=user_email.lastIndexOf(".");
                        // Judge whether the e-mail format is correct
                        // E-mail format is correct
                        if (index1 != -1 && index2 > index1) {
                           // Judge whether the e-mail or the username is registered or not
                            List<User> username_users = LitePal
                                    .where("name = ?", username).find(User.class);
                            List<User> email_users = LitePal
                                    .where("email = ?", user_email).find(User.class);
                            // Username or e-mail has already been registered
                            if(!username_users.isEmpty() || !email_users.isEmpty()){
                                Toast.makeText(SignUp.this, "E-mail or username has already registered!", Toast.LENGTH_SHORT).show();
                            }
                            // Username and e-mail hasn't been registered yet
                            else{
                              // Judge whether the length of the password follows the rule of 6-12
                              // The rule is followed
                              if(password.length()>=6&&password.length()<=16){
                                  // Judge whether the two passwords are the same
                                  // Passwords are same
                                  if(TextUtils.equals(password, conf_password)){
                                      // Save the info into the database
                                      User newUser = new User();
                                      newUser.setName(username);
                                      newUser.setPassword(password);
                                      newUser.setEmail(user_email);
                                      newUser.save();
                                      Toast.makeText(SignUp.this,"Register successfully,now you can log in with the account",Toast.LENGTH_SHORT).show();

                                  }
                                  // Passwords are different
                                  else{
                                      Toast.makeText(SignUp.this, "Two passwords should be the same!", Toast.LENGTH_SHORT).show();
                                  }

                              }
                              // The rule is not followed
                              else{
                                  Toast.makeText(SignUp.this, "Please reset your password between 6 and 16 characters", Toast.LENGTH_SHORT).show();
                              }
                            }
                        }
                        // E-mail format is not correct
                        else{
                            Toast.makeText(SignUp.this, "E-mail address is not correct!", Toast.LENGTH_SHORT).show();
                        }
                    }
            }

        }

    }











}

