package com.korlab.foodex;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.korlab.foodex.Data.User;
import com.korlab.foodex.Technical.Helper;
import com.korlab.foodex.UI.MaterialButton;

import spencerstudios.com.bungeelib.Bungee;

public class InfoGender extends AppCompatActivity {
    private static InfoGender instance;

    public static InfoGender getInstance() {
        return instance;
    }

    private MaterialButton buttonNext;
    private ImageView man, woman;
    private User user;
    private boolean gender = false, isNext = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_gender);
        instance = this;

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        Helper.setStatusBarColor(getWindow(), ContextCompat.getColor(getBaseContext(), R.color.white));
        findView();
        user = Helper.getUserData();
        Helper.logObjectToJson(user);

        Helper.disableButton(getInstance(), buttonNext);

        man.setOnClickListener((v) -> toggleGender(true));
        woman.setOnClickListener((v) -> toggleGender(false));

        buttonNext.setOnClickListener((v) -> {
            user.setGender(gender);
            Helper.setUserData(user);
            startActivity(new Intent(getInstance(), InfoWeight.class));
            Bungee.slideLeft(getInstance());
        });
    }

    private void toggleGender(boolean var) {
        Helper.enableButton(getInstance(), buttonNext);

        isNext = true;
        if (var) {
            gender = false;
            man.setImageDrawable(getDrawable(R.drawable.man_enable));
            woman.setImageDrawable(getDrawable(R.drawable.woman_disable));
        } else {
            gender = true;
            man.setImageDrawable(getDrawable(R.drawable.man_disable));
            woman.setImageDrawable(getDrawable(R.drawable.woman_enable));
        }
    }

    private void findView() {
        buttonNext = findViewById(R.id.next);
        man = findViewById(R.id.man);
        woman = findViewById(R.id.woman);
    }
}
