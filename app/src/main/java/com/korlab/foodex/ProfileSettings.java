package com.korlab.foodex;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.korlab.foodex.Data.User;
import com.korlab.foodex.Technical.Helper;
import com.korlab.foodex.UI.Toolbar;

public class ProfileSettings extends AppCompatActivity {
    private User user;

    private static ProfileSettings instance;
    public static ProfileSettings getInstance() {
        return instance;
    }
    private LinearLayout toolbarContainer, toolbarLeft;

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
        toolbarLeft = findViewById(R.id.toolbar_left);
        toolbarLeft.setOnClickListener(v -> super.finish());
    }

    private void findView() {
        toolbarContainer = findViewById(R.id.toolbar_container);
    }
}
