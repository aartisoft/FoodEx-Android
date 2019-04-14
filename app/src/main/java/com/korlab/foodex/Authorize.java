package com.korlab.foodex;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;

import com.korlab.foodex.Data.User;
import com.korlab.foodex.Technical.Helper;
import com.korlab.foodex.UI.MaterialButton;

import com.korlab.foodex.UI.MaterialEditText;

import spencerstudios.com.bungeelib.Bungee;

public class Authorize extends AppCompatActivity {

    private static Authorize instance;

    public static Authorize getInstance() {
        return instance;
    }

    private MaterialEditText inputPhone;
    private MaterialButton buttonContinue, buttonFacebook, buttonGoogle;
    private User user;

    @Override
    public void onBackPressed() {
        Helper.showDialog(getInstance(), LayoutInflater.from(getInstance().getBaseContext()).inflate(R.layout.dialog_exit, null), (v)-> this.finishAffinity(), null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.log("onCreate Authorize");
        setContentView(R.layout.activity_authorize);
        instance = this;
        Helper.setStatusBarColor(getWindow(), ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));
        findView();
        user = new User();
        Helper.disableButton(getInstance(), buttonContinue);
        buttonContinue.setOnClickListener((v) -> {
            user.setPhone(inputPhone.getText().toString().replace(" ", ""));
            Intent intent = new Intent(getInstance(), AuthorizeVerification.class);
            intent.putExtra("user", Helper.toJson(user));
            startActivity(intent);
            Bungee.slideLeft(getInstance());
        });
//        buttonFacebook.setOnClickListener((v) -> {
//            startActivity(new Intent(getInstance(), AuthorizeVerification.class));
//            Bungee.slideLeft(getInstance());
//        });
//        buttonGoogle.setOnClickListener((v) -> {
//            startActivity(new Intent(getInstance(), AuthorizeVerification.class));
//            Bungee.slideLeft(getInstance());
//        });

        inputPhone.addTextChangedListener(new TextWatcher() {
            private int characterAction = -1;
            private int actionPosition = 0;

            private boolean lock;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (count == 0 && after == 1) {
                    characterAction = 1;
                } else if (count == 1 && after == 0) {
                    if (s.charAt(start) == ' ' && start > 0) {
                        characterAction = 3;
                        actionPosition = start - 1;
                    } else {
                        characterAction = 2;
                    }
                } else {
                    characterAction = -1;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (lock) return;
                String startText = "+380";
                if (!s.toString().startsWith(startText)) {
                    inputPhone.setText(startText);
                    inputPhone.setSelection(startText.length());
                }
                int start = inputPhone.getSelectionStart();
                String phoneChars = "1234567890+";
                String str = inputPhone.getText().toString();
                if (characterAction == 3) {
                    str = str.substring(0, actionPosition) + str.substring(actionPosition + 1, str.length());
                    start--;
                }
                StringBuilder builder = new StringBuilder(str.length());
                for (int a = 0; a < str.length(); a++) {
                    String ch = str.substring(a, a + 1);
                    if (phoneChars.contains(ch)) builder.append(ch);
                }
                lock = true;
                String hint = "---- -- --- -- --";
                int a = 0;
                while (a < builder.length()) {
                    if (a < hint.length()) {
                        if (hint.charAt(a) == ' ') {
                            builder.insert(a, ' ');
                            a++;
                            if (start == a && characterAction != 2 && characterAction != 3) start++;
                        }
                    } else {
                        builder.insert(a, ' ');
                        if (start == a + 1 && characterAction != 2 && characterAction != 3) start++;
                        break;
                    }
                    a++;
                }
                s.replace(0, s.length(), builder);
                if (start >= 0)
                    inputPhone.setSelection((start <= inputPhone.length()) ? start : inputPhone.getText().toString().length());
                lock = false;
                validateInput();
            }
        });

        inputPhone.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                if (inputPhone.getText().toString().length() == 0) {
                    new Handler().postDelayed(() -> {
                        inputPhone.setText("+380 ");
                        inputPhone.setSelection(inputPhone.getText().toString().length());
                    }, 250);
                }
            } else {
                inputPhone.setText("");
            }
        });
    }

    private void findView() {
        inputPhone = findViewById(R.id.input_ph);
        buttonContinue = findViewById(R.id.button_continue);
        buttonFacebook = findViewById(R.id.button_facebook);
        buttonGoogle = findViewById(R.id.button_google);
    }

    private void validateInput() {
        if(inputPhone.length() >= 17)
            Helper.enableButton(getInstance(), buttonContinue);
        else
            Helper.disableButton(getInstance(), buttonContinue);
    }
}