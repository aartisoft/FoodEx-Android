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
import android.widget.EditText;

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

    private EditText inputSurname, inputName, inputMiddle;
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

        Helper.addRedAsterisk(inputSurname);
        Helper.addRedAsterisk(inputName);
        Helper.addRedAsterisk(inputMiddle);

        Helper.disableButton(getInstance(), buttonNext);

        buttonNext.setOnClickListener((v) -> {
            user.setFirstName(inputSurname.getText().toString());
            user.setLastName(inputName.getText().toString());
            user.setMiddleName(inputMiddle.getText().toString());
            startActivity(new Intent(getInstance(), InfoGender.class).putExtra("user", Helper.toJson(user)));
            Bungee.slideLeft(getInstance());
        });
        inputSurname.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override public void afterTextChanged(Editable s) { validateInput(); }
        });
        inputName.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override public void afterTextChanged(Editable s) { validateInput(); }
        });
        inputMiddle.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override public void afterTextChanged(Editable s) { validateInput(); }
        });
    }

    private void findView() {
        buttonNext = findViewById(R.id.next);
        inputSurname = findViewById(R.id.input_s);
        inputName = findViewById(R.id.input_n);
        inputMiddle = findViewById(R.id.input_m);
    }

    private void validateInput() {
        if(inputSurname.length() >= 2 && inputName.length() >=2 && inputMiddle.length() >= 2)
            Helper.enableButton(getInstance(), buttonNext);
        else
            Helper.disableButton(getInstance(), buttonNext);
    }

}
