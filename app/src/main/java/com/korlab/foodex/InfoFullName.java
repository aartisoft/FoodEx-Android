package com.korlab.foodex;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;

import com.korlab.foodex.Data.User;
import com.korlab.foodex.Technical.Helper;
import com.korlab.foodex.UI.MaterialButton;
import com.korlab.foodex.UI.MaterialEditText;

import spencerstudios.com.bungeelib.Bungee;

public class InfoFullName extends AppCompatActivity {

    private static InfoFullName instance;

    public static InfoFullName getInstance() {
        return instance;
    }

    private MaterialEditText inputFirstName, inputLastName, inputMiddleName;
    private TextInputLayout inputLayoutFirstName, inputLayoutLastName, inputLayoutMiddleName;
    private MaterialButton buttonNext;

    private User user;

    @Override
    public void onBackPressed() {
        Helper.showDialog(getInstance(), LayoutInflater.from(getInstance().getBaseContext()).inflate(R.layout.dialog_exit, null), (v)-> this.finishAffinity(), null);
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

        Helper.disableButton(getInstance(), buttonNext);

        buttonNext.setOnClickListener((v) -> {
            user.setFirstName(inputFirstName.getText().toString());
            user.setLastName(inputLastName.getText().toString());
            user.setMiddleName(inputMiddleName.getText().toString());
            Intent intent = new Intent(getInstance(), InfoGender.class);
            intent.putExtra("user", Helper.toJson(user));
            startActivity(intent);
            Bungee.slideLeft(getInstance());
        });
        inputFirstName.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override public void afterTextChanged(Editable s) { validateInput(); }
        });
        inputLastName.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override public void afterTextChanged(Editable s) { validateInput(); }
        });
        inputMiddleName.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override public void afterTextChanged(Editable s) { validateInput(); }
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

    private void validateInput() {
        if(inputFirstName.length() >= 2 && inputLastName.length() >=2 && inputMiddleName.length() >= 2)
            Helper.enableButton(getInstance(), buttonNext);
        else
            Helper.disableButton(getInstance(), buttonNext);
    }

}
