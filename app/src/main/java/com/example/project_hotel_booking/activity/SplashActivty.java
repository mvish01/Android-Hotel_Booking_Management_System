package com.example.project_hotel_booking.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.project_hotel_booking.R;

public class SplashActivty extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activty);
        imageView = findViewById(R.id.imageView);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.hotel_anim);
        imageView.setAnimation(animation);
        animation.start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    if(getSharedPreferences("project",MODE_PRIVATE).getBoolean("login_status",false))
                        startActivity(new Intent(SplashActivty.this, MainActivity.class));
                    else
                        startActivity(new Intent(SplashActivty.this, Login_Activity.class));
                    finish();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

}
