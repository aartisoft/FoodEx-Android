package com.korlab.foodex;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.korlab.foodex.Chats.ChatsAdapter;
import com.korlab.foodex.Data.Chat;
import com.korlab.foodex.Data.User;
import com.korlab.foodex.Technical.Helper;
import com.korlab.foodex.UI.CustomViewPager;
import com.korlab.foodex.UI.ReadableBottomBar;

import java.util.List;

public class MainMenu extends AppCompatActivity {
    private static MainMenu instance;
    public User user;

    public static MainMenu getInstance() {
        return instance;
    }
    public List<Chat> listChat;
    public ChatsAdapter chatsAdapter;

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }

    private ReadableBottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        instance = this;
        Helper.setStatusBarColor(getWindow(), ContextCompat.getColor(getBaseContext(), R.color.white));
        Helper.setStatusBarIconWhite(getWindow());

        String json = "{\"birthdayDay\":9,\"birthdayMonth\":11,\"birthdayYear\":1998,\"deliveryType\":1,\"email\":\"xom9ik.code@gmail.com\",\"firstName\":\"Maxim\",\"gender\":false,\"growth\":170,\"growthMetrics\":false,\"lastName\":\"Romanyuta\",\"middleName\":\"Olegovich\",\"phone\":\"+380959483523\",\"weekdaysAddress\":{\"apartment\":\"2/F\",\"house\":\"77\",\"street\":\"Universitetksa\"},\"weekendsAddress\":{\"apartment\":\"1\",\"house\":\"30\",\"street\":\"Aprel`ska\"},\"weight\":60,\"weightMetrics\":false}";
        user = Helper.fromJson(json, User.class);
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
