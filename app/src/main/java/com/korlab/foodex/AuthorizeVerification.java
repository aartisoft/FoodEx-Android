package com.korlab.foodex;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.korlab.foodex.Data.User;
import com.korlab.foodex.Technical.Helper;
import com.korlab.foodex.UI.InputCodeLayout;
import com.korlab.foodex.UI.MaterialButton;

import spencerstudios.com.bungeelib.Bungee;

public class AuthorizeVerification extends AppCompatActivity {
    private static int TIME_OUT = 60;

    private TextView timer, textPhone, buttonWrong;
    private ProgressBar progress;
    private MaterialButton buttonResend;
    private InputCodeLayout inputCodeLayout;
    private User user;

    private boolean isButtonResend;

    private static AuthorizeVerification instance;
    public static AuthorizeVerification getInstance() {
        return instance;
    }

    @Override
    public void onBackPressed() {
        Helper.showDialog(getInstance(), LayoutInflater.from(getInstance().getBaseContext()).inflate(R.layout.dialog_exit, null), (v)-> this.finishAffinity(), null);
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
        if(user.getPhone().equals("")) {
            startActivity(new Intent(getInstance(), InfoFullName.class).putExtra("user", Helper.toJson(user)));
            Bungee.slideLeft(getInstance());
            finish();
        }
        textPhone.setText(user.getPhone());
        countDownTimer(TIME_OUT);
        inputCodeLayout.setOnInputCompleteListener(code -> {
            Helper.log(code);
            Helper.hideKeyboard(getInstance(), inputCodeLayout);
            startActivity(new Intent(getInstance(), InfoFullName.class).putExtra("user", Helper.toJson(user)));
            Bungee.slideLeft(getInstance());
            finish();
        });
//        inputCodeLayout.requestFocus();
//        Helper.showKeyboard(getInstance(), inputCodeLayout);
        buttonWrong.setOnClickListener(v -> super.finish());
        buttonResend.setOnClickListener(v -> resendSms());
    }

    private void resendSms() {
        if(isButtonResend) {
            isButtonResend = false;
            buttonResend.setTextColor(getResources().getColor(R.color.dark_text));
            countDownTimer(TIME_OUT);
        }
    }

    private void findView() {
        timer = findViewById(R.id.timer);
        buttonWrong = findViewById(R.id.button_wrong);
        buttonResend = findViewById(R.id.button_resend);
        textPhone = findViewById(R.id.text_phone);
        progress = findViewById(R.id.progress);
        inputCodeLayout = findViewById(R.id.input_code);
    }

    private void countDownTimer(int s) {
        new Thread(() -> {
            for (int i = s; i >= 0; i--) {
                int minutes = i / 60;
                int seconds = i % 60;
                int pr = 100 - (i * 100 / s);
                runOnUiThread(() -> {
                    progress.setProgress(pr);
                    timer.setText(String.format("%02d:%02d", minutes, seconds));
                });
                if(seconds == 0 && minutes == 0) {
                    isButtonResend = true;
                    buttonResend.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                try { Thread.sleep(1000); } catch (InterruptedException ignored) { }
            }
        }).start();
    }
}
