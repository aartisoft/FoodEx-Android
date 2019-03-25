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


import spencerstudios.com.bungeelib.Bungee;

public class InfoGrowth extends AppCompatActivity {
    private InfoGrowth instance;
    public InfoGrowth getInstance() {
        return instance;
    }
    private User user;
    private MaterialButton buttonNext;
    private ImageView image;
    private WheelView wvGrowth, wvGrowthMetrics;
    private int mGrowth, mGrowthMetrics;
    private String growthStringCm[], growthStringInch[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_growth);
        instance = this;
        Helper.setStatusBarColor(getWindow(), ContextCompat.getColor(getBaseContext(), R.color.white));
        findView();
        user = Helper.fromJson(getIntent().getStringExtra("user"), User.class);
        changeImage(user.isMale());
        growthStringCm = new String[300];
        growthStringInch = new String[118];
        for(int i=1; i<=300; i++) growthStringCm[i-1] = String.valueOf(i);
        for(int i=1; i<=118; i++) growthStringInch[i-1] = String.valueOf(i);
        wvGrowth.setEntries(growthStringCm);
        wvGrowth.setCurrentIndex(159);
        mGrowth = 160;
        wvGrowth.setOnWheelChangedListener((wheel, oldIndex, newIndex) -> {
            mGrowth = Integer.parseInt((String) wvGrowth.getItem(newIndex));
        });


        wvGrowthMetrics.setEntries(new String[] {"cm", "inch"});
        wvGrowthMetrics.setCurrentIndex(0);
        wvGrowthMetrics.setOnWheelChangedListener((wheel, oldIndex, newIndex) -> {
            mGrowthMetrics = newIndex;
            changeMetrics(mGrowth, newIndex);
        });
        buttonNext.setOnClickListener((v)->{
            user.setGrowth(mGrowth);
            user.setGrowthMetrics((mGrowthMetrics != 0));
            Intent intent = new Intent(getInstance(), InfoBirthday.class);
            Helper.logObjectToJson(user);
            intent.putExtra("user", Helper.toJson(user));
            startActivity(intent);
            Bungee.slideLeft(getInstance());
        });
    }

    private void changeImage(boolean isMale) {
        if(isMale) {
            image.setImageDrawable(getDrawable(R.drawable.man_growth));
        } else {
            image.setImageDrawable(getDrawable(R.drawable.woman_growth));
        }
    }

    private void changeMetrics(int value, int newIndex) {
        if(newIndex == 0) {
            int newValue = (int) Math.ceil(value/0.39370078740157);
            Helper.log("change to Cm: " + value + " inch => " + newValue + " cm");
            wvGrowth.setEntries(growthStringCm);
            wvGrowth.setCurrentIndex(newValue-1, true);
        } else {
            int newValue = (int) Math.floor(value*0.39370078740157);
            Helper.log("change to Inch: " + value + " cm => " + newValue + " inch");
            wvGrowth.setEntries(growthStringInch);
            wvGrowth.setCurrentIndex(newValue-1, true);
        }

    }


    private void findView() {
        buttonNext = findViewById(R.id.next);
        wvGrowth = findViewById(R.id.wv_growth);
        wvGrowthMetrics = findViewById(R.id.wv_growth_metrics);
        image = findViewById(R.id.image);
    }
}
