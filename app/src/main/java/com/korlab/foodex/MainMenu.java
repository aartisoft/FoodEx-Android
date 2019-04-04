package com.korlab.foodex;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.korlab.foodex.Technical.Helper;
import com.korlab.foodex.UI.CustomViewPager;
import com.korlab.foodex.UI.ReadableBottomBar;

public class MainMenu extends AppCompatActivity {
    private MainMenu instance;

    public MainMenu getInstance() {
        return instance;
    }

    private ReadableBottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        instance = this;
        Helper.setStatusBarColor(getWindow(), ContextCompat.getColor(getBaseContext(), R.color.white));
        Helper.setStatusBarIconWhite(getWindow());
        findView();

        CustomViewPager viewPager = findViewById(R.id.viewpager_main_menu);
        viewPager.setAdapter(new FragmentMainMenuAdapter(getSupportFragmentManager(), MainMenu.this));
        viewPager.setPagingEnabled(false);
        viewPager.setOffscreenPageLimit(10);

        bottomBar.setOnItemSelectListener(index -> {
            Helper.log("Bottom Bar index: " + index);
            viewPager.setCurrentItem(index, false);
            viewPager.invalidate();

        });
    }

    private void findView() {
        bottomBar = findViewById(R.id.bottom_bar);
    }
}
