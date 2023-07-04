package com.example.imca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText user_email,user_password;
    private TextView tvShow;
    private RelativeLayout loginBtn;
    String email,password;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences=this.getSharedPreferences("login",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        if(sharedPreferences.getString("isLogin","false").equals("yes"))
        {
            openDash();
        }
        user_email=findViewById(R.id.user_email);
        user_password=findViewById(R.id.user_password);
        tvShow=findViewById(R.id.txt_show);
        loginBtn=findViewById(R.id.login_btn);
        tvShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user_password.getInputType()==144)
                {
                    user_password.setInputType(129);
                    tvShow.setText("Hide");
                }
                else
                {
                    user_email.setInputType(144);
                    tvShow.setText("Show");
                }
                user_password.setSelection(user_password.getText().length());
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });
    }

    private void validateData() {
        email=user_email.getText().toString();
        password=user_password.getText().toString();
        if(email.isEmpty())
        {
            user_email.setError("Email Required");
            user_email.requestFocus();
        }
        else if(password.isEmpty())
        {
            user_password.setError("Password Required");
            user_password.requestFocus();
        }else if(email.equals("admin@gmail.com")&& password.equals("12345"))
        {
            editor.putString("isLogin","yes");
            editor.commit();
            openDash();
        }
        else
        {
            Toast.makeText(this, "Please check Email and Password Again", Toast.LENGTH_SHORT).show();
        }
    }

    private void openDash() {

        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        finish();
    }
}