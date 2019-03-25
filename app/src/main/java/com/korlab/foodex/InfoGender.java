package com.korlab.foodex;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.korlab.foodex.Data.User;
import com.korlab.foodex.Technical.Helper;
import com.korlab.foodex.UI.MaterialButton;


import java.util.Objects;

import spencerstudios.com.bungeelib.Bungee;

public class InfoGender extends AppCompatActivity {
    private InfoGender instance;
    public InfoGender getInstance() {
        return instance;
    }
    private MaterialButton buttonNext;
    private ImageView male, female;
    private User user;
    private boolean isMale = false,
            isNext = false;

    @Override
    public void onBackPressed()
    {
        Helper.showExitDialog(getInstance());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_gender);
        instance = this;

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
//        Helper.hideKeyboard(getInstance(), getInstance());

        Helper.setStatusBarColor(getWindow(), ContextCompat.getColor(getBaseContext(), R.color.white));
        findView();
        user = Helper.fromJson(getIntent().getStringExtra("user"), User.class);
        Helper.logObjectToJson(user);

        buttonNext.setBorderColor(getResources().getColor(R.color.colorPrimaryVeryLight));
        buttonNext.setButtonColor(getResources().getColor(R.color.colorPrimaryVeryLight));
        buttonNext.setEnabled(false);

        male.setOnClickListener((v) -> {
            toggleGender(true);
        });
        female.setOnClickListener((v) -> {
            toggleGender(false);
        });
        buttonNext.setOnClickListener((v) -> {
            user.setMale(isMale);
            Intent intent = new Intent(getInstance(), InfoWeight.class);
            intent.putExtra("user", Helper.toJson(user));
            startActivity(intent);
            Bungee.slideLeft(getInstance());
        });
    }

    private void toggleGender(boolean var) {
        buttonNext.setBorderColor(getResources().getColor(R.color.colorPrimary));
        buttonNext.setButtonColor(getResources().getColor(R.color.colorPrimary));
        buttonNext.setEnabled(true);


        isNext = true;
        if (var) {
            isMale = true;
            male.setImageDrawable(getDrawable(R.drawable.male_enable));
            female.setImageDrawable(getDrawable(R.drawable.female_disable));
        } else {
            isMale = false;
            male.setImageDrawable(getDrawable(R.drawable.male_disable));
            female.setImageDrawable(getDrawable(R.drawable.female_enable));
        }
    }

    private void findView() {
        buttonNext = findViewById(R.id.next);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
    }
}
