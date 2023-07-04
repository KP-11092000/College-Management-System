package com.example.imca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.imca.faculty.UpdateFaculty;
import com.example.imca.notice.DeleteNoticeActivity;
import com.example.imca.notice.UploadNotice;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    CardView uploadNotice,addGalleryImage,addEBook,faculty,deleteNotice,logout;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences=this.getSharedPreferences("login",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        if(sharedPreferences.getString("isLogin","false").equals("false"))
        {
            openLogin();
        }
        logout=findViewById(R.id.logout);
        logout.setOnClickListener(this);
        uploadNotice=findViewById(R.id.addNotice);
        deleteNotice=findViewById(R.id.deleteNotice);
        addGalleryImage=findViewById(R.id.addGalleryImage);
        addEBook=findViewById(R.id.addEBook);
        faculty=findViewById(R.id.faculty);
        uploadNotice.setOnClickListener(this);
        addGalleryImage.setOnClickListener(this);
        addEBook.setOnClickListener(this);
        faculty.setOnClickListener(this);
        deleteNotice.setOnClickListener(this);
    }

    private void openLogin() {
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId())
        {

            case R.id.addNotice:
                 intent=new Intent(MainActivity.this, UploadNotice.class);
                startActivity(intent);
                break;
            case R.id.addGalleryImage:
                 intent=new Intent(MainActivity.this,UploadImage.class);
                startActivity(intent);
                break;
            case R.id.addEBook:
                intent=new Intent(MainActivity.this,UploadPdfActivity.class);
                startActivity(intent);
                break;
            case R.id.faculty:
                intent=new Intent(MainActivity.this, UpdateFaculty.class);
                startActivity(intent);
                break;
            case R.id.deleteNotice:
                intent=new Intent(MainActivity.this, DeleteNoticeActivity.class);
                startActivity(intent);
                break;
            case R.id.logout:
                editor.putString("isLogin","false");
                editor.commit();
                openLogin();
                break;

        }
    }
}