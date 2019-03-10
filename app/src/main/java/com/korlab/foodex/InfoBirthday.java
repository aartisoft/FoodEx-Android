package com.korlab.foodex;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.cncoderx.wheelview.WheelView;
import com.korlab.foodex.Data.User;
import com.korlab.foodex.Technical.Helper;
import com.korlab.foodex.UI.MaterialButton;
import com.korlab.foodex.Technical.Singleton;

import java.util.Calendar;

public class InfoBirthday extends Singleton {

    private User user;
    private MaterialButton buttonNext;
    private ImageView image;
    private WheelView wvYear, wvMonth, wvDay;
    private int mYear, mMonth, mDay;
    private String birthdayStringYear[], birthdayStringMonth[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_birthday);
        setInstance(this);
        Helper.setStatusBarColor(getWindow(), ContextCompat.getColor(getBaseContext(), R.color.white));
        findView();
        user = Helper.fromJson(getIntent().getStringExtra("user"), User.class);

        birthdayStringYear = new String[119];
        for (int i = 0; i <= 118; i++) birthdayStringYear[i] = String.valueOf(i + 1900);
        wvYear.setEntries(birthdayStringYear);
        wvMonth.setEntries(new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"});
        wvYear.setOnWheelChangedListener((wheel, oldIndex, newIndex) -> {
            String text = (String) wvYear.getItem(newIndex);
            mYear = Integer.parseInt(text.substring(0, text.length() - 1));
            updateDayEntries();
        });
        wvMonth.setOnWheelChangedListener((wheel, oldIndex, newIndex) -> {
            mMonth = newIndex + 1;
            updateDayEntries();
        });
        wvDay.setOnWheelChangedListener((wheel, oldIndex, newIndex) -> {
            mDay = newIndex + 1;
        });

        buttonNext.setOnClickListener((v) -> {
            user.setBirthdayDay(mDay);
            user.setBirthdayMonth(mMonth);
            user.setBirthdayYear(mYear);
//            Intent intent = new Intent(getInstance(), InfoWeight.class);
            Helper.logObjectToJson(user);
//            intent.putExtra("user", Helper.toJson(user));
//            startActivity(intent);
//            Bungee.slideLeft(getInstance());
        });
        mYear = 1990;
        mMonth = 0;
        mDay = 0;

        wvMonth.setCurrentIndex(mMonth);
        wvYear.setCurrentIndex(mYear-1900);
        updateDayEntries();
    }

    private void updateDayEntries() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, mYear);
        calendar.set(Calendar.MONTH, mMonth - 1);

        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        String[] birthdayStringDays = new String[days];

        for (int i = 0; i < days; i++) {
            birthdayStringDays[i] = String.valueOf(i + 1);
        }
        Helper.log(
                "mYear: " + mYear +
                        "mMonth: " + mMonth +
                        "mDay: " + mDay
        );
//        Helper.logObjectToJson(birthdayStringDays);
        int tempDay = mDay <= days ? mDay : days;
        wvDay.setEntries(birthdayStringDays);

        Helper.log(
                "set day: " + tempDay
        );
        wvDay.setCurrentIndex(tempDay-1);
    }


    private void findView() {
        buttonNext = findViewById(R.id.next);
        wvDay = findViewById(R.id.wv_day);
        wvMonth = findViewById(R.id.wv_month);
        wvYear = findViewById(R.id.wv_year);
        image = findViewById(R.id.image);
    }
}
