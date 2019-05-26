package com.korlab.foodex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.cncoderx.wheelview.WheelView;
import com.korlab.foodex.Data.User;
import com.korlab.foodex.Technical.Helper;
import com.korlab.foodex.UI.MaterialButton;


import java.util.Date;
import java.util.Calendar;

import spencerstudios.com.bungeelib.Bungee;

public class InfoBirthday extends AppCompatActivity {
    private static InfoBirthday instance;

    public static InfoBirthday getInstance() {
        return instance;
    }

    private User user;
    private MaterialButton buttonNext;
    private ImageView image;
    private WheelView wvYear, wvMonth, wvDay;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_birthday);
        instance = this;
        Helper.setStatusBarColor(getWindow(), ContextCompat.getColor(getBaseContext(), R.color.white));
        findView();
        user = Helper.getUserData();

        String[] birthdayStringYear = new String[119];
        for (int i = 0; i <= 118; i++) birthdayStringYear[i] = String.valueOf(i + 1900);
        wvYear.setEntries(birthdayStringYear);
        wvMonth.setEntries(Helper.getTranslate(Helper.Translate.months, getInstance()));
        wvYear.setOnWheelChangedListener((wheel, oldIndex, newIndex) -> {
            String text = (String) wvYear.getItem(newIndex);
            mYear = Integer.parseInt(text);
            updateDayEntries();
        });
        wvMonth.setOnWheelChangedListener((wheel, oldIndex, newIndex) -> {
            mMonth = newIndex + 1;
            updateDayEntries();
        });
        wvDay.setOnWheelChangedListener((wheel, oldIndex, newIndex) -> {
            mDay = newIndex + 1;
        });

        buttonNext.setOnClickListener((v) -> {
            Date date = new Date(mYear, mMonth, mDay);
            user.setBirthday(date);
            Helper.logObjectToJson(user);
            Helper.setUserData(user);
            // TODO: 5/24/2019 send userData to firebase
            startActivity(new Intent(getInstance(), MainMenu.class));
            Bungee.slideLeft(getInstance());
            super.finish();
            InfoGrowth.getInstance().finish();
            InfoWeight.getInstance().finish();
            InfoGender.getInstance().finish();
        });
        mYear = 1990;
        mMonth = 0;
        mDay = 0;

        wvMonth.setCurrentIndex(mMonth);
        wvYear.setCurrentIndex(mYear - 1900);
        updateDayEntries();
    }

    private void updateDayEntries() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, mYear);
        calendar.set(Calendar.MONTH, mMonth - 1);
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        String[] birthdayStringDays = new String[days];
        for (int i = 0; i < days; i++) {
            birthdayStringDays[i] = String.valueOf(i + 1);
        }
        int tempDay = mDay <= days ? mDay : days;
        wvDay.setEntries(birthdayStringDays);
        wvDay.setCurrentIndex(tempDay - 1);
    }


    private void findView() {
        buttonNext = findViewById(R.id.next);
        wvDay = findViewById(R.id.wv_day);
        wvMonth = findViewById(R.id.wv_month);
        wvYear = findViewById(R.id.wv_year);
        image = findViewById(R.id.image);
    }
}
