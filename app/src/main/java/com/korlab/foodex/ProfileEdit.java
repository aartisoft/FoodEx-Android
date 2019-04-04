package com.korlab.foodex;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.korlab.foodex.Technical.Helper;
import com.korlab.foodex.UI.DatePicker.DPMode;
import com.korlab.foodex.UI.DatePicker.DatePicker;
import com.korlab.foodex.UI.MaterialButton;
import com.korlab.foodex.UI.MaterialEditText;
import com.korlab.foodex.UI.Toolbar;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;


public class ProfileEdit extends AppCompatActivity {
    private ProfileEdit instance;
    private LinearLayout toolbarContainer, buttonBirthday;
    private MaterialEditText inputBirthday;


    private int[] dateBirthday;

    public ProfileEdit getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        instance = this;

        Helper.setStatusBarColor(getWindow(), ContextCompat.getColor(getBaseContext(), R.color.white));
        Helper.setStatusBarIconWhite(getWindow());

        findView();
        toolbarContainer.addView(new Toolbar(getInstance(), false, "Edit Profile",
                getInstance().getDrawable(R.drawable.profile_arrow_left), null));
        dateBirthday = new int[]{14, 7, 2018};
        buttonBirthday.setOnClickListener((v) -> showCalendarDialog(getInstance(), dateBirthday));



    }

    private void findView() {
        toolbarContainer = findViewById(R.id.toolbar_container);
        buttonBirthday = findViewById(R.id.button_birthday);
        inputBirthday = findViewById(R.id.input_birthday);
    }



    private String formatDate(String value) {
        String[] arrayMonth = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        String[] split = value.split("-");
        int year = Integer.parseInt(split[0]);
        String month = arrayMonth[Integer.parseInt(split[1])-1];
        int day = Integer.parseInt(split[2]);
        return formatZero(day) + " " + month + ", " + year;
    }

    private String formatZero(int value) {
        if(value>=1 && value<=9) return "0" + value;
        return String.valueOf(value);
    }

    public void showCalendarDialog(Activity activity, int[] dateBirthday) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_calendar);
        dialog.setCancelable(true);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(R.color.transparent);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        DatePicker picker = dialog.findViewById(R.id.main_dp);
        picker.setMode(DPMode.SINGLE);
//        picker.setDate(1998, 11);
//        picker.setDate(1998, 11);

        picker.setDate(dateBirthday[2], dateBirthday[1]);
        picker.setHolidayDisplay(false);
        picker.setDeferredDisplay(false);
        picker.setFestivalDisplay(false);
        picker.setOnDatePickedListener(date -> {
            Helper.log("Pick date: " + date);
            inputBirthday.setText(formatDate(date));
            dialog.cancel();
        });


        dialog.getWindow().setAttributes(lp);
        Window window = dialog.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        dialog.show();
    }
}
