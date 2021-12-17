package com.example.myfinaltask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.window.SplashScreen;

public class MySplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_splash_screen);
//        getSupportActionBar().hide();

        Thread thread=new Thread(){
            public void  run(){
                try {
                    sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    Intent intent=new Intent(MySplashScreen.this, MyLoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };thread.start();
    }
}