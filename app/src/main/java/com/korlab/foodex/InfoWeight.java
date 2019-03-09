package com.korlab.foodex;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.cncoderx.wheelview.OnWheelChangedListener;
import com.cncoderx.wheelview.WheelView;
import com.korlab.foodex.Data.User;

import spencerstudios.com.bungeelib.Bungee;

public class InfoWeight extends Singleton {

    private User user;
    private MaterialButton buttonNext;
    private ImageView image;
    private WheelView wvWeight, wvWeightMetrics;
    private int mWeight, mWeightMetrics;
    private String weightStringKg[], weightStringLb[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_weight);
        setInstance(this);
        Helper.setStatusBarColor(getWindow(), ContextCompat.getColor(getBaseContext(), R.color.white));
        findView();
        user = Helper.fromJson(getIntent().getStringExtra("user"), User.class);
        changeImage(user.isMale());

        weightStringKg = new String[400];
        weightStringLb = new String[880];
        for (int i = 1; i <= 400; i++) weightStringKg[i - 1] = String.valueOf(i);
        for (int i = 1; i <= 880; i++) weightStringLb[i - 1] = String.valueOf(i);
        wvWeight.setEntries(weightStringKg);
        wvWeight.setCurrentIndex(59);
        mWeight = 60;
        wvWeight.setOnWheelChangedListener((wheel, oldIndex, newIndex) -> {
            mWeight = Integer.parseInt((String) wvWeight.getItem(newIndex));
        });


        wvWeightMetrics.setEntries(new String[]{"kg", "lb"});
        wvWeightMetrics.setCurrentIndex(0);
        wvWeightMetrics.setOnWheelChangedListener((wheel, oldIndex, newIndex) -> {
            mWeightMetrics = newIndex;
            changeMetrics(mWeight, newIndex);
        });
        buttonNext.setOnClickListener((v) -> {
            user.setWeight(mWeight);
            user.setWeightMetrics((mWeightMetrics != 0));
            Intent intent = new Intent(getInstance(), InfoGrowth.class);
            intent.putExtra("user", Helper.toJson(user));
            startActivity(intent);
            Bungee.slideLeft(getInstance());
        });
    }

    private void changeImage(boolean isMale) {
        if (isMale) {
            image.setImageDrawable(getDrawable(R.drawable.man_weight));
        } else {
            image.setImageDrawable(getDrawable(R.drawable.woman_weight));
        }
    }

    private void changeMetrics(int value, int newIndex) {
        if (newIndex == 0) {
            int newValue = (int) Math.ceil(value / 2.2);
            Helper.log("change to KG: " + value + " lb => " + newValue + " kg");
            wvWeight.setEntries(weightStringKg);
            wvWeight.setCurrentIndex(newValue - 1, true);
        } else {
            int newValue = (int) Math.floor(value * 2.2);
            Helper.log("change to LB: " + value + " kg => " + newValue + " lb");
            wvWeight.setEntries(weightStringLb);
            wvWeight.setCurrentIndex(newValue - 1, true);
        }

    }


    private void findView() {
        buttonNext = findViewById(R.id.next);
        wvWeight = findViewById(R.id.wv_weight);
        wvWeightMetrics = findViewById(R.id.wv_weight_metrics);
        image = findViewById(R.id.image);
    }
}