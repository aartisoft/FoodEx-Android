package com.korlab.foodex;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.korlab.foodex.Technical.Helper;

public class SplashScreen extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Helper.setStatusBarColor(getWindow(), Color.parseColor("#fefcfe"));
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashScreen.this, Authorize.class));
            finish();
        }, 1600);
    }
}
