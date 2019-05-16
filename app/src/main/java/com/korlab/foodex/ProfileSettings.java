package com.korlab.foodex;

import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.balysv.materialripple.MaterialRippleLayout;
import com.korlab.foodex.Data.User;
import com.korlab.foodex.Technical.Helper;
import com.korlab.foodex.UI.Toolbar;
import com.suke.widget.SwitchButton;

public class ProfileSettings extends AppCompatActivity {
    private User user;

    private static ProfileSettings instance;

    public static ProfileSettings getInstance() {
        return instance;
    }

    private ImageView toolbarLeft;
    private LinearLayout toolbarContainer;
    private MaterialRippleLayout wrapperPromo, wrapperProgram, wrapperManagers, wrapperMeal, wrapperVibrations, wrapperSounds;
    private SwitchButton togglePromo, toggleProgram, toggleManagers, toggleMeal, toggleVibrations, toggleSounds;
    private boolean isPromo = false, isProgram = false, isManagers = false, isMeal = false, isVibrations = false, isSounds = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
        instance = this;

        Helper.setStatusBarColor(getWindow(), ContextCompat.getColor(getBaseContext(), R.color.white));
        Helper.setStatusBarIconWhite(getWindow());

        findView();
        toolbarContainer.addView(new Toolbar(getInstance(), false, "Settings",
                getInstance().getDrawable(R.drawable.toolbar_arrow_left), null));
        toolbarLeft = findViewById(R.id.toolbar_left_icon);
        toolbarLeft.setOnClickListener(v -> super.finish());

        wrapperPromo.setOnClickListener(v -> {
            isPromo = !isPromo;
            if(!isPromo)
                wrapperPromo.setRippleColor(ContextCompat.getColor(getInstance(), R.color.colorPrimary));
            else
                wrapperPromo.setRippleColor(ContextCompat.getColor(getInstance(), R.color.black));
            togglePromo.setChecked(isPromo);
        });
        wrapperProgram.setOnClickListener(v -> {
            isProgram = !isProgram;
            if(!isProgram)
                wrapperProgram.setRippleColor(ContextCompat.getColor(getInstance(), R.color.colorPrimary));
            else
                wrapperProgram.setRippleColor(ContextCompat.getColor(getInstance(), R.color.black));
            toggleProgram.setChecked(isProgram);
        });
        wrapperManagers.setOnClickListener(v -> {
            isManagers = !isManagers;
            if(!isManagers)
                wrapperManagers.setRippleColor(ContextCompat.getColor(getInstance(), R.color.colorPrimary));
            else
                wrapperManagers.setRippleColor(ContextCompat.getColor(getInstance(), R.color.black));
            toggleManagers.setChecked(isManagers);
        });
        wrapperMeal.setOnClickListener(v -> {
            isMeal = !isMeal;
            if(!isMeal)
                wrapperMeal.setRippleColor(ContextCompat.getColor(getInstance(), R.color.colorPrimary));
            else
                wrapperMeal.setRippleColor(ContextCompat.getColor(getInstance(), R.color.black));
            toggleMeal.setChecked(isMeal);
        });
        wrapperVibrations.setOnClickListener(v -> {
            isVibrations = !isVibrations;
            if(!isVibrations)
                wrapperVibrations.setRippleColor(ContextCompat.getColor(getInstance(), R.color.colorPrimary));
            else
                wrapperVibrations.setRippleColor(ContextCompat.getColor(getInstance(), R.color.black));
            toggleVibrations.setChecked(isVibrations);
        });
        wrapperSounds.setOnClickListener(v -> {
            isSounds = !isSounds;
            if(!isSounds)
                wrapperSounds.setRippleColor(ContextCompat.getColor(getInstance(), R.color.colorPrimary));
            else
                wrapperSounds.setRippleColor(ContextCompat.getColor(getInstance(), R.color.black));
            toggleSounds.setChecked(isSounds);
        });
    }

    private void findView() {
        toolbarContainer = findViewById(R.id.toolbar_container);

        wrapperPromo = findViewById(R.id.wrapper_promo);
        wrapperProgram = findViewById(R.id.wrapper_program);
        wrapperManagers = findViewById(R.id.wrapper_managers);
        wrapperMeal = findViewById(R.id.wrapper_meal);
        wrapperVibrations = findViewById(R.id.wrapper_vibrations);
        wrapperSounds = findViewById(R.id.wrapper_sounds);

        togglePromo = findViewById(R.id.toggle_promo);
        toggleProgram = findViewById(R.id.toggle_program);
        toggleManagers = findViewById(R.id.toggle_managers);
        toggleMeal = findViewById(R.id.toggle_meal);
        toggleVibrations = findViewById(R.id.toggle_vibrations);
        toggleSounds = findViewById(R.id.toggle_sounds);

    }
}
