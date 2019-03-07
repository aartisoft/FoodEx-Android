package com.korlab.foodex;

import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.rengwuxian.materialedittext.MaterialEditText;

public class Authorize extends Singleton {

    private MaterialEditText inputPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.log("onCreate Authorize");
        setContentView(R.layout.activity_authorize);
        setInstance(this);
        Helper.setStatusBarColor(getWindow(), ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));
        findView();

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
            }
        });

        inputPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (inputPhone.getText().toString().length() == 0) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                inputPhone.setText("+380 ");
                                inputPhone.setSelection(inputPhone.getText().toString().length());
                            }
                        }, 250);
                    }
                } else {
                    inputPhone.setText("");
                }
            }
        });
    }

    private void findView() {
        inputPhone = findViewById(R.id.input_phone);
    }
}