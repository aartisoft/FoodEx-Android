package com.korlab.foodex;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.korlab.foodex.Technical.Helper;
import com.korlab.foodex.Technical.Singleton;

public class AuthorizeVerification extends Singleton {

    private TextView timer;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorize_verification);
        setInstance(this);
        Helper.setStatusBarColor(getWindow(), ContextCompat.getColor(getBaseContext(), R.color.white));
        findView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        countDownTimer(60);
    }

    private void findView() {
        timer = findViewById(R.id.timer);
        progress = findViewById(R.id.progress);
    }

    private void countDownTimer(int s) {
        Thread thread = new Thread(() -> {
            int countdownSeconds = s;
            for (int i = countdownSeconds; i >= 0; i--) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                int minutes = i / 60;
                int seconds = i % 60;
                int pr = 100-(i*100/s);
                Helper.log("i: " + i + "*" + 100 + "/" + s + "=" + pr);
                String str = String.format("%02d:%02d", minutes, seconds);
                runOnUiThread(() -> {
                    progress.setProgress(pr);
                    timer.setText(str);
                });
                Helper.log("str: " + str);
                Helper.log("sec: " + i);
            }
        });
        thread.start();
    }
}
