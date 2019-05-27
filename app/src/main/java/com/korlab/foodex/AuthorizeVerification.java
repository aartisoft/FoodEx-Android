package com.korlab.foodex;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.korlab.foodex.Data.User;
import com.korlab.foodex.FireServer.Auth;
import com.korlab.foodex.FireServer.FireRequest;
import com.korlab.foodex.Technical.Helper;
import com.korlab.foodex.UI.InputCodeLayout;
import com.korlab.foodex.UI.MaterialButton;

import java.util.HashMap;

import kotlin.Unit;
import spencerstudios.com.bungeelib.Bungee;

public class AuthorizeVerification extends AppCompatActivity {
    private static int TIME_OUT = 60;

    private TextView timer, textPhone, buttonWrong, buttonResetCode;
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
        Helper.showDialog(getInstance(), LayoutInflater.from(getInstance().getBaseContext()).inflate(R.layout.dialog_exit, null), (v) -> this.finishAffinity(), null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorize_verification);
        instance = this;
        Helper.setStatusBarColor(getWindow(), ContextCompat.getColor(getBaseContext(), R.color.white));
        Helper.setStatusBarIconWhite(getWindow());

        findView();
        user = Helper.getUserData();
//        if (user.getPhoneNumber().equals("")) {
//            Helper.setUserData(user);
//            startActivity(new Intent(getInstance(), InfoFullName.class));
//            Bungee.slideLeft(getInstance());
//            finish();
//        }
        textPhone.setText(user.getPhoneNumber());
        countDownTimer(TIME_OUT);
        inputCodeLayout.setOnInputCompleteListener(code -> {
            Helper.hideKeyboard(getInstance(), inputCodeLayout);
//            Helper.setUserData(user);
            Auth.INSTANCE.checkEnteredCodeVerification(
                    code,
                    AuthorizeVerification::onRightSms,
                    AuthorizeVerification::onWrongSms);
        });
        buttonWrong.setOnClickListener(v -> {
            instance = null;
            super.finish();
        });
        buttonResetCode.setOnClickListener(v -> inputCodeLayout.clear());
        buttonResend.setOnClickListener(v -> resendSms());
    }

    private void resendSms() {
        if (isButtonResend) {
            isButtonResend = false;
            buttonResend.setTextColor(getResources().getColor(R.color.dark_text));
            countDownTimer(TIME_OUT);
            // TODO: 5/24/2019 resend sms from firebase
        }
    }

    private void findView() {
        timer = findViewById(R.id.timer);
        buttonWrong = findViewById(R.id.button_wrong);
        buttonResetCode = findViewById(R.id.button_reset_code);
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
                if (seconds == 0 && minutes == 0) {
                    isButtonResend = true;
                    if(getInstance() != null)
                        buttonResend.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
            }
        });
    }

    public static Unit onRightSms() {
        Helper.log("Right sms code");
        FireRequest.Companion.getData("customers",Auth.INSTANCE.getRealUserId(), AuthorizeVerification::onSuccessGotUser, AuthorizeVerification::onFailGotUser);
        return Unit.INSTANCE;
    }

    public static Unit onWrongSms() {
        Helper.log("Wrong sms code");
        return Unit.INSTANCE;
    }

    // Check registered user
    private static kotlin.Unit onSuccessGotUser(HashMap<?, ?> userHashMap) {

        Helper.log("Success find user in DB. Phone: " + userHashMap);
//        Helper.setUserData(user);
        launchActivity(MainMenu.class);
        return Unit.INSTANCE;
    }

    private static kotlin.Unit onFailGotUser() {
        Helper.log("Fail find this user in DB. Start process register");
        launchActivity(InfoFullName.class);
        return Unit.INSTANCE;
    }

    private static void launchActivity(Class<?> activityClass) {
        if(getInstance() == null) {
            startActivity(Authorize.getInstance(), activityClass);
        } else {
            startActivity(getInstance(), activityClass);
        }
    }

    private static void startActivity(Activity activity, Class<?> activityClass) {
        activity.startActivity(new Intent(activity, activityClass));
        Bungee.slideLeft(activity);
        instance = null;
        activity.finish();
    }

}
