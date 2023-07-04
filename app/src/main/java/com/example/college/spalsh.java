package com.example.college;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class spalsh extends AppCompatActivity {

    public static String TAG= spalsh.class.getSimpleName();
    private ImageView splashLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);
        splashLogo = findViewById(R.id.imageView2);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }



       // Animation fadeIn = FadeInAnimation.create();
       // splashLogo.setAnimation(fadeIn);
     //   splashLogo.setVisibility(View.VISIBLE);
        Intent iNext=new Intent(spalsh.this,MainActivity.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(iNext);
                finish();
            }
        },3000);

    }
}