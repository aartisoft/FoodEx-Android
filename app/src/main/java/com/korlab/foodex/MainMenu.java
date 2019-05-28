package com.korlab.foodex;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.Timestamp;
import com.korlab.foodex.Chats.ChatsAdapter;
import com.korlab.foodex.Data.Chat;
import com.korlab.foodex.Data.Growth;
import com.korlab.foodex.Data.Name;
import com.korlab.foodex.Data.User;
import com.korlab.foodex.Data.Weight;
import com.korlab.foodex.FireServer.Auth;
import com.korlab.foodex.FireServer.FireRequest;
import com.korlab.foodex.Technical.Helper;
import com.korlab.foodex.UI.CustomViewPager;
import com.korlab.foodex.UI.ReadableBottomBar;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import kotlin.Unit;

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
        FireRequest.Companion.getData("customers", Auth.INSTANCE.getRealUserId(), this::onSuccessGotUser, this::onFailGotUser);

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

    // Check registered user
    private kotlin.Unit onSuccessGotUser(HashMap<String, Object> userHashMap) {
        Helper.log("Success find user in DB.");
        Helper.logObjectToJson(userHashMap);

        user = new User();
        user.setDeliveryType(Integer.parseInt(Objects.requireNonNull(userHashMap.get("deliveryType")).toString()));
        user.setGender(Boolean.parseBoolean(Objects.requireNonNull(userHashMap.get("gender")).toString()));
        user.setPhoneNumber(Objects.requireNonNull(userHashMap.get("phoneNumber")).toString());

        long timestamp = ((Timestamp) Objects.requireNonNull(userHashMap.get("birthday"))).getSeconds()*1000;
        Helper.log("timestamp: " + timestamp);
        if(timestamp<0) {
            user.setBirthday(new Date(timestamp));
        } else {
            user.setBirthday(new Date(timestamp));
        }
        user.setRegistration(new Date(((Timestamp) Objects.requireNonNull(userHashMap.get("registration"))).getSeconds()));

        try {
            HashMap<String, String> nameMap = (HashMap<String, String>) Objects.requireNonNull(userHashMap.get("name"));
            String first = Objects.requireNonNull(nameMap.get("first"));
            String last = Objects.requireNonNull(nameMap.get("last"));
            String middle = Objects.requireNonNull(nameMap.get("middle"));
            user.setName(new Name(first,last,middle));
        } catch (Exception e) {
            Helper.log("Error convert name");
        }
        try {
            HashMap<String, Long> growthMap = (HashMap<String, Long>) Objects.requireNonNull(userHashMap.get("growth"));
            int value = Objects.requireNonNull(growthMap.get("value")).intValue();
            int type = Objects.requireNonNull(growthMap.get("type")).intValue();
            user.setGrowth(new Growth(value,type));
        } catch (Exception e) {
            Helper.log("Error convert growth: " + e);
        }
        try {
            HashMap<String, Long> weightMap = (HashMap<String, Long>) Objects.requireNonNull(userHashMap.get("weight"));
            int value = Objects.requireNonNull(weightMap.get("value")).intValue();
            int type = Objects.requireNonNull(weightMap.get("type")).intValue();
            user.setWeight(new Weight(value,type));
        } catch (Exception e) {
            Helper.log("Error convert weight: " + e);
        }

        Helper.log("User after parse info: ");
        Helper.logObjectToJson(user);
        Helper.setUserData(user);
        return Unit.INSTANCE;
    }

    private kotlin.Unit onFailGotUser() {
        Helper.log("Fail find this user in DB. Logout user");
        Helper.logoutUser(getInstance());
        return Unit.INSTANCE;
    }
}
