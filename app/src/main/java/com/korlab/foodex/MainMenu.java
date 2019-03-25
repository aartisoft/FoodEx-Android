package com.korlab.foodex;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.korlab.foodex.Technical.Helper;
import com.korlab.foodex.UI.CustomViewPager;
import com.korlab.foodex.UI.NavigationTabStrip;
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




        CustomViewPager viewPager = findViewById(R.id.viewpagerMainMenu);
        viewPager.setAdapter(new FragmentMainMenuAdapter(getSupportFragmentManager(), MainMenu.this));
//        viewPager.setOnTouchListener((v, event) -> true);
        viewPager.setPagingEnabled(false);
        bottomBar.setOnItemSelectListener(index -> {
            Helper.log("Bottom Bar index: " + index);
            viewPager.setCurrentItem(index);
        });
    }

    private void findView() {
        bottomBar = findViewById(R.id.bottom_bar);
    }
}
