package com.korlab.foodex;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;

import com.korlab.foodex.Data.User;
import com.korlab.foodex.Technical.Helper;
import com.korlab.foodex.UI.MaterialButton;
import com.korlab.foodex.UI.MaterialEditText;

import spencerstudios.com.bungeelib.Bungee;

public class InfoFullName extends AppCompatActivity {

    private InfoFullName instance;
    public InfoFullName getInstance() {
        return instance;
    }

    private MaterialEditText inputFirstName, inputLastName, inputMiddleName;
    private TextInputLayout inputLayoutFirstName, inputLayoutLastName, inputLayoutMiddleName;
    private MaterialButton buttonNext;

    private User user;

    @Override
    public void onBackPressed()
    {
        Helper.showExitDialog(getInstance());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_full_name);
        instance = this;
        Helper.setStatusBarColor(getWindow(), ContextCompat.getColor(getBaseContext(), R.color.white));
        Helper.setStatusBarIconWhite(getWindow());
        findView();
        user = Helper.fromJson(getIntent().getStringExtra("user"), User.class);
        int red = ResourcesCompat.getColor(getResources(), R.color.google, null);
        Helper.setUpHintColor(inputFirstName, inputLayoutFirstName, red);
        Helper.setUpHintColor(inputLastName, inputLayoutLastName, red);
        Helper.setUpHintColor(inputMiddleName, inputLayoutMiddleName, red);
//        new Handler().postDelayed(() -> Helper.showKeyboard(getInstance(), inputFirstName), 100);

        buttonNext.setOnClickListener((v)->{
            user.setFirstName(inputFirstName.getText().toString());
            user.setLastName(inputLastName.getText().toString());
            user.setMiddleName(inputMiddleName.getText().toString());
            Intent intent = new Intent(getInstance(), InfoGender.class);
            intent.putExtra("user", Helper.toJson(user));
            startActivity(intent);
            Bungee.slideLeft(getInstance());
            finish();
        });
    }

    private void findView() {
        inputFirstName = findViewById(R.id.input_f);
        inputLastName = findViewById(R.id.input_l);
        inputMiddleName = findViewById(R.id.input_m);
        inputLayoutFirstName = findViewById(R.id.input_layout_f);
        inputLayoutLastName = findViewById(R.id.input_layout_l);
        inputLayoutMiddleName = findViewById(R.id.input_layout_m);
        buttonNext = findViewById(R.id.next);
    }

}
