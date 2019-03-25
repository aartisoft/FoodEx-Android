package com.korlab.foodex;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.korlab.foodex.Data.User;
import com.korlab.foodex.Technical.Helper;

import com.korlab.foodex.UI.InputCodeLayout;

import spencerstudios.com.bungeelib.Bungee;

public class AuthorizeVerification extends AppCompatActivity {

    private AuthorizeVerification instance;
    public AuthorizeVerification getInstance() {
        return instance;
    }

    private TextView timer, textPhone;
    private ProgressBar progress;
    private InputCodeLayout inputCodeLayout;
    private User user;

    @Override
    public void onBackPressed()
    {
        Helper.showExitDialog(getInstance());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorize_verification);
        instance = this;
        Helper.setStatusBarColor(getWindow(), ContextCompat.getColor(getBaseContext(), R.color.white));
        Helper.setStatusBarIconWhite(getWindow());
        findView();
        user = Helper.fromJson(getIntent().getStringExtra("user"), User.class);
        textPhone.setText(user.getPhone());
        new Handler().postDelayed(() -> Helper.showKeyboard(getInstance(), inputCodeLayout), 100);
        countDownTimer(60);
        inputCodeLayout.setOnInputCompleteListener(code -> {
            Helper.log(code);
            Helper.hideKeyboard(getInstance(), inputCodeLayout);
            Intent intent = new Intent(getInstance(), InfoFullName.class);
            intent.putExtra("user", Helper.toJson(user));
            startActivity(intent);
            Bungee.slideLeft(getInstance());
            finish();
        });
    }

    private void findView() {
        timer = findViewById(R.id.timer);
        textPhone = findViewById(R.id.text_phone);
        progress = findViewById(R.id.progress);
        inputCodeLayout = findViewById(R.id.input_code);
    }

    private void countDownTimer(int s) {
        new Thread(() -> {
            for (int i = s; i >= 0; i--) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) { }
                int minutes = i / 60;
                int seconds = i % 60;
                int pr = 100 - (i * 100 / s);
                runOnUiThread(() -> {
                    progress.setProgress(pr);
                    timer.setText(String.format("%02d:%02d", minutes, seconds));
                });
            }
        }).start();
    }
}
