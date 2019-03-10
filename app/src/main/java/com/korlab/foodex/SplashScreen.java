package com.korlab.foodex;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import com.korlab.foodex.Technical.Helper;
import com.korlab.foodex.Technical.Singleton;

public class SplashScreen extends Singleton {

    private final int SPLASH_DISPLAY_LENGTH = 1600;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Helper.setStatusBarColor(getWindow(), Color.parseColor("#fefcfe"));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashScreen.this, Authorize.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
