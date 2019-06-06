package com.korlab.foodex;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.korlab.foodex.Data.User;
import com.korlab.foodex.FireServer.Auth;
import com.korlab.foodex.Technical.Helper;
import com.korlab.foodex.UI.MaterialButton;

import com.korlab.foodex.UI.MaterialEditText;

import kotlin.Unit;
import spencerstudios.com.bungeelib.Bungee;

public class Authorize extends AppCompatActivity {

    private static Authorize instance;

    public static Authorize getInstance() {
        return instance;
    }

    private TextView buttonRecoveryPassword;
    private LinearLayout inputWrapperEmail, inputWrapperRecoveryEmail, inputWrapperPhone;
    private MaterialEditText inputPhone, inputEmail, inputRecoveryEmail, inputPassword;
    private MaterialButton buttonContinue, buttomSwitchEmailPhone, buttonGoogle;
    private boolean isEmail = false, isRecovery = false, isProgressAuth = false;
    private User user;

    @Override
    public void onBackPressed() {
        Helper.showDialog(getInstance(), LayoutInflater.from(getInstance().getBaseContext()).inflate(R.layout.dialog_exit, null), (v) -> this.finishAffinity(), null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.log("onCreate Authorize");
        if (Auth.INSTANCE.isUserSigned()) {
            Helper.log("User already authorized");
            Intent intent = new Intent(this, MainMenu.class);
            startActivity(intent);
            finish();
        }
        else {
            // TODO: 5/28/2019 check internet
            Helper.checkInternet(instance, false);
        }
        setContentView(R.layout.activity_authorize);
        instance = this;
        Helper.setStatusBarColor(getWindow(), ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));


        findView();
        user = new User();
        Helper.disableButton(getInstance(), buttonContinue);
        buttonContinue.setOnClickListener((v) -> {
            if (isRecovery) {
                // TODO: 4/15/2019 recovery password by Email
            } else {
                if (isEmail) {
                    user.setEmail(inputEmail.getText().toString().replace(" ", ""));
                    // TODO: 5/24/2019 Authorize by Email
                } else {
                    user.setPhoneNumber(inputPhone.getText().toString().replace(" ", ""));
                    startAuthPhone(user.getPhoneNumber());
                }
            }
        });
        buttomSwitchEmailPhone.setOnClickListener((v) -> switchButton());
        buttonGoogle.setOnClickListener((v) -> {
            // TODO: 4/15/2019 Authorize by Gmail
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
        inputEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                validateInput();
            }
        });
        inputPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                validateInput();
            }
        });
        inputRecoveryEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                validateInput();
            }
        });


        buttonRecoveryPassword.setOnClickListener(v -> switchRecoveryPassword());
    }

    private void switchRecoveryPassword() {
        isRecovery = !isRecovery;
        if (isRecovery) {
            buttonRecoveryPassword.setText("Back to the login form");
            buttonContinue.setText("Send recovery email");
            inputWrapperRecoveryEmail.setVisibility(View.VISIBLE);
            inputWrapperPhone.setVisibility(View.GONE);
            inputWrapperEmail.setVisibility(View.GONE);
        } else {
            buttonRecoveryPassword.setText("Recover password via email");
            buttonContinue.setText("Continue");
            inputWrapperRecoveryEmail.setVisibility(View.GONE);
            if (isEmail) {
                inputWrapperEmail.setVisibility(View.VISIBLE);
                inputWrapperPhone.setVisibility(View.GONE);
            } else {
                inputWrapperEmail.setVisibility(View.GONE);
                inputWrapperPhone.setVisibility(View.VISIBLE);
            }
        }
        validateInput();
    }

    private void switchButton() {
        isEmail = !isEmail;
        if (isRecovery)
            switchRecoveryPassword();
        if (isEmail) {
            inputWrapperEmail.setVisibility(View.VISIBLE);
            inputWrapperPhone.setVisibility(View.GONE);
            buttomSwitchEmailPhone.setText("Phone");
        } else {
            inputWrapperEmail.setVisibility(View.GONE);
            inputWrapperPhone.setVisibility(View.VISIBLE);
            buttomSwitchEmailPhone.setText("Email");
        }
        validateInput();
    }

    private void findView() {
        inputWrapperPhone = findViewById(R.id.input_wrapper_ph);
        inputWrapperEmail = findViewById(R.id.input_wrapper_em);
        inputWrapperRecoveryEmail = findViewById(R.id.input_wrapper_recovery_em);
        inputPhone = findViewById(R.id.input_ph);
        inputEmail = findViewById(R.id.input_em);
        inputRecoveryEmail = findViewById(R.id.input_recovery_em);
        inputPassword = findViewById(R.id.input_password);
        buttonContinue = findViewById(R.id.button_continue);
        buttomSwitchEmailPhone = findViewById(R.id.button_switch_email_phone);
        buttonGoogle = findViewById(R.id.button_google);
        buttonRecoveryPassword = findViewById(R.id.button_recovery_password);
    }

    private void validateInput() {
        if (isRecovery) {
            Helper.log("isRecovery");
            if (inputRecoveryEmail.length() >= 5 && Helper.isEmailValid(inputRecoveryEmail.getText().toString()))
                Helper.enableButton(getInstance(), buttonContinue);
            else
                Helper.disableButton(getInstance(), buttonContinue);
        } else {
            if (isEmail) {
                if (inputEmail.length() >= 5 && inputPassword.length() >= 6 && Helper.isEmailValid(inputEmail.getText().toString()))
                    Helper.enableButton(getInstance(), buttonContinue);
                else
                    Helper.disableButton(getInstance(), buttonContinue);
            } else {
                if (inputPhone.length() >= 17)
                    Helper.enableButton(getInstance(), buttonContinue);
                else
                    Helper.disableButton(getInstance(), buttonContinue);
            }
        }
    }

    private void startAuthPhone(String phone) {
        if(!isProgressAuth) {
            isProgressAuth = true;
            Helper.setUserData(user);
            Auth.INSTANCE.authPhone(phone, this::onCorrectCodeGot, this::onFailCodeGot, AuthorizeVerification::onRightSms, AuthorizeVerification::onWrongSms);
            new Handler().postDelayed(() -> isProgressAuth = false, 3000);
        }
    }

    private kotlin.Unit onCorrectCodeGot() {
        Helper.log("Success send sms to: " + user.getPhoneNumber());
        launchNextActivity();
        return Unit.INSTANCE;
    }

    private kotlin.Unit onFailCodeGot(String error) {
        Helper.log("Fail send sms to: " + user.getPhoneNumber());
        return Unit.INSTANCE;
    }

    public void launchNextActivity() {
        startActivity(new Intent(getInstance(), AuthorizeVerification.class));
        Bungee.slideLeft(getInstance());
    }
}