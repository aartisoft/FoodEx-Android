package com.korlab.foodex;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.korlab.foodex.Data.Address;
import com.korlab.foodex.Data.User;
import com.korlab.foodex.Technical.Helper;
import com.korlab.foodex.UI.MaterialButton;
import com.korlab.foodex.UI.MaterialEditText;
import com.korlab.foodex.UI.Toolbar;
import com.uniquestudio.library.CircleCheckBox;

import java.util.List;
import java.util.Objects;


public class ProfileEdit extends AppCompatActivity {
    private LinearLayout toolbarContainer;

    private MaterialEditText inputName, inputLastname, inputMiddlename, inputBirthday, inputGrowth, inputWeight, inputEmail, inputPhone, inputNote;
    private MaterialEditText inputWeekdaysStreet, inputWeekdaysHouse, inputWeekdaysApartment, inputWeekendsStreet, inputWeekendsHouse, inputWeekendsApartment;
    private CircleCheckBox inputManCheckbox, inputWomanCheckbox;
    private LinearLayout inputMan, inputWoman, buttonDelivery, buttonBirthday;
    private TextView inputDeliveryType;

    private ImageView toolbarLeft, toolbarRight;
    private List<String> arrayMonth;
    private String[] deliveryTypeArray = {"Package", "Bag", "Other"};

    private int[] dateBirthday;
    private boolean gender;
    private int deliveryType;
    private User user;

    private static ProfileEdit instance;

    public static ProfileEdit getInstance() {
        return instance;
    }

    @Override
    public void onBackPressed() {
        Helper.showDialog(getInstance(), LayoutInflater.from(getInstance().getBaseContext()).inflate(R.layout.dialog_without_saving, null), this::onPositive, this::onNegative);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        instance = this;
        arrayMonth = Helper.getTranslate(Helper.Translate.months, getInstance());
        Helper.setStatusBarColor(getWindow(), ContextCompat.getColor(getBaseContext(), R.color.white));
        Helper.setStatusBarIconWhite(getWindow());

        findView();
        toolbarContainer.addView(new Toolbar(getInstance(), false, "Edit Profile",
                getInstance().getDrawable(R.drawable.toolbar_arrow_left), getInstance().getDrawable(R.drawable.toolbar_check)));
        toolbarLeft = findViewById(R.id.toolbar_left_icon);
        toolbarRight = findViewById(R.id.toolbar_right_icon);
        user = Helper.fromJson(getIntent().getStringExtra("user"), User.class);

        dateBirthday = new int[]{user.getBirthdayDay(), user.getBirthdayMonth(), user.getBirthdayYear()};
        buttonBirthday.setOnClickListener((v) -> showCalendarDialog(getInstance(), dateBirthday));
        toolbarLeft.setOnClickListener(v -> Helper.showDialog(getInstance(), LayoutInflater.from(getInstance().getBaseContext()).inflate(R.layout.dialog_without_saving, null),
                this::onPositive, this::onNegative));
        toolbarRight.setOnClickListener(v -> {
            updateUserData();
            super.finish();
        });

        buttonDelivery.setOnClickListener(v -> {
            showDeliveryTypeDialog();
        });

        inputGrowth.addTextChangedListener(new TextWatcher() {
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override public void beforeTextChanged(CharSequence c, int start, int count, int after) { }
            @Override public void afterTextChanged(Editable e) { checkMask(e, inputGrowth, user.getGrowthMetrics() ? "inch" : "cm"); }
        });
        inputWeight.addTextChangedListener(new TextWatcher() {
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override public void beforeTextChanged(CharSequence c, int start, int count, int after) { }
            @Override public void afterTextChanged(Editable e) { checkMask(e, inputWeight, user.getWeightMetrics() ? "lb" : "kg"); }
        });

        setListenerChangeSex();
        drawUserData();
    }

    private void showDeliveryTypeDialog() {
        final Dialog dialog = new Dialog(getInstance());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_delivery_type);
        dialog.setCancelable(true);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(R.color.transparent);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        RadioGroup radioGroup = dialog.findViewById(R.id.input_radio);
        RadioButton radioPackage = dialog.findViewById(R.id.radio_package);
        RadioButton radioBag = dialog.findViewById(R.id.radio_bag);
        RadioButton radioOther = dialog.findViewById(R.id.radio_other);
        MaterialButton ok = dialog.findViewById(R.id.ok);
        MaterialButton cancel = dialog.findViewById(R.id.cancel);

        switch(deliveryType) {
            case 0: radioPackage.setChecked(true);break;
            case 1: radioBag.setChecked(true);break;
            case 2: radioOther.setChecked(true);break;
        }

        ok.setOnClickListener((v) -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            Helper.log("selectedId: " + selectedId);

            switch(selectedId){
                case R.id.radio_package: deliveryType = 0;break;
                case R.id.radio_bag: deliveryType = 1;break;
                case R.id.radio_other: deliveryType = 2;break;
            }
            inputDeliveryType.setText(deliveryTypeArray[deliveryType]);
            dialog.dismiss();
        });
        cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.getWindow().setAttributes(lp);
        Window window = dialog.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        dialog.show();
    }

    private void updateUserData() {
        Address addressWeekdays = new Address(
                inputWeekdaysStreet.getText().toString(),
                inputWeekdaysHouse.getText().toString(),
                inputWeekdaysApartment.getText().toString());

        Address addressWeekends = new Address(
                inputWeekendsStreet.getText().toString(),
                inputWeekendsHouse.getText().toString(),
                inputWeekendsApartment.getText().toString());

        user.setFirstName(inputName.getText().toString());
        user.setLastName(inputLastname.getText().toString());
        user.setMiddleName(inputMiddlename.getText().toString());
        user.setBirthdayDay(dateBirthday[0]);
        user.setBirthdayMonth(dateBirthday[1]);
        user.setBirthdayYear(dateBirthday[2]);
        user.setGender(gender);
        user.setGrowth(Integer.parseInt(inputGrowth.getText().toString().replaceAll("[^0-9]","")));
        user.setWeight(Integer.parseInt(inputWeight.getText().toString().replaceAll("[^0-9]","")));
        user.setEmail(inputEmail.getText().toString());
        user.setPhone(inputPhone.getText().toString());
        user.setNote(inputNote.getText().toString());
        user.setWeekdaysAddress(addressWeekdays);
        user.setWeekendsAddress(addressWeekends);
        user.setDeliveryType(deliveryType);
        Helper.logObjectToJson(user);
    }

    private void onPositive(Object o) {
        super.finish();
        Helper.log("onPositive");
    }

    private void onNegative(Object o) {
        Helper.log("onNegative");
    }

    private void checkMask(Editable e, MaterialEditText inputEdit, String metricsEdit) {
        String s = e.toString();
        if (s.length() > 0) {
            if (!s.endsWith(" " + metricsEdit)) {
                if (!s.equals(s + " " + metricsEdit)) {
                    s = s.replaceAll("[^\\d.]", "");
                    inputEdit.setText(s + " " + metricsEdit);
                } else
                    inputEdit.setSelection(s.length() - (" " + metricsEdit).length());
            } else {
                inputEdit.setSelection(s.length() - (" " + metricsEdit).length());
                if (s.equals(" " + metricsEdit))
                    inputEdit.setText("");
            }
        }
    }

    private void setListenerChangeSex() {
        inputMan.setOnClickListener(v -> changeGender(false));
        inputWoman.setOnClickListener(v -> changeGender(true));
        inputManCheckbox.setOnClickListener(v -> changeGender(false));
        inputWomanCheckbox.setOnClickListener(v -> changeGender(true));
    }

    private void drawUserData() {
        inputName.setText(user.getFirstName());
        inputLastname.setText(user.getLastName());
        inputMiddlename.setText(user.getMiddleName());
        inputBirthday.setText(dateBirthday[0] + " " + arrayMonth.get(dateBirthday[1]-1) + ", " + dateBirthday[2]);
        if(user.getGender()) inputWomanCheckbox.setChecked(true);
        else inputManCheckbox.setChecked(true);
        inputGrowth.setText(""+user.getGrowth());
        inputWeight.setText(""+user.getWeight());
        inputEmail.setText(""+user.getEmail());
        inputPhone.setText(""+user.getPhone());
        inputNote.setText(""+user.getNote());

        deliveryType = user.getDeliveryType();
        inputDeliveryType.setText(deliveryTypeArray[deliveryType]);

        inputWeekdaysStreet.setText(user.getWeekdaysAddress().getStreet());
        inputWeekdaysHouse.setText(user.getWeekdaysAddress().getHouse());
        inputWeekdaysApartment.setText(user.getWeekdaysAddress().getApartment());

        inputWeekendsStreet.setText(user.getWeekendsAddress().getStreet());
        inputWeekendsHouse.setText(user.getWeekendsAddress().getHouse());
        inputWeekendsApartment.setText(user.getWeekendsAddress().getApartment());
    }

    private void changeGender(boolean value) {
        gender = value;
        if(gender) {
            if(inputManCheckbox.isChecked()) {
                inputManCheckbox.setChecked(false);
                inputWomanCheckbox.setChecked(true);
            }
        } else {
            if(!inputManCheckbox.isChecked()) {
                inputManCheckbox.setChecked(true);
                inputWomanCheckbox.setChecked(false);
            }
        }
    }

    private void findView() {
        toolbarContainer = findViewById(R.id.toolbar_container);

        inputWeekdaysStreet = findViewById(R.id.input_weekdays_street);
        inputWeekdaysHouse = findViewById(R.id.input_weekdays_house);
        inputWeekdaysApartment = findViewById(R.id.input_weekdays_apartment);
        inputWeekendsStreet = findViewById(R.id.input_weekends_street);
        inputWeekendsHouse = findViewById(R.id.input_weekends_house);
        inputWeekendsApartment = findViewById(R.id.input_weekends_apartment);
        buttonDelivery = findViewById(R.id.button_delivery);
        buttonDelivery = findViewById(R.id.button_delivery);
        inputDeliveryType = findViewById(R.id.input_delivery_type);

        inputName = findViewById(R.id.input_n);
        inputLastname = findViewById(R.id.input_l);
        inputMiddlename = findViewById(R.id.input_m);
        buttonBirthday = findViewById(R.id.button_birthday);
        inputBirthday = findViewById(R.id.input_birthday);
        inputManCheckbox = findViewById(R.id.input_man_checkbox);
        inputWomanCheckbox = findViewById(R.id.input_woman_checkbox);
        inputMan = findViewById(R.id.input_man);
        inputWoman = findViewById(R.id.input_woman);
        inputGrowth = findViewById(R.id.input_growth);
        inputWeight = findViewById(R.id.input_weight);
        inputEmail = findViewById(R.id.input_e);
        inputPhone = findViewById(R.id.input_p);
        inputNote = findViewById(R.id.input_note);
    }

    private String formatDate(int[] dateBirthday) {
        return formatZero(dateBirthday[0]) + " " + arrayMonth.get(dateBirthday[1]-1) + ", " + dateBirthday[2];
    }
    private String formatZero(int value) {
        if(value>=1 && value<=9) return "0" + value;
        return String.valueOf(value);
    }
    private TextView mTextMonthDay;
    private TextView mTextYear;
    private TextView mTextLunar;
    private TextView mTextCurrentDay;
    private CalendarView mCalendarView;
    private int mYear;
    private boolean isSelectYear = false, isSelectMonth = false;
    private CalendarLayout mCalendarLayout;
    public void showCalendarDialog(Activity activity, int[] dateBirthday) {

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_calendar);
        dialog.setCancelable(true);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(R.color.transparent);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        //////////////////////////////
        mTextMonthDay = dialog.findViewById(R.id.tv_month_day);
        mTextYear = dialog.findViewById(R.id.tv_year);
        mTextLunar = dialog.findViewById(R.id.tv_lunar);
        mCalendarView = dialog.findViewById(R.id.calendarView);
        mTextCurrentDay = dialog.findViewById(R.id.tv_current_day);

        mCalendarView.getSelectedCalendar().setDay(dateBirthday[0]);
        mCalendarView.getSelectedCalendar().setMonth(dateBirthday[1]);
        mCalendarView.getSelectedCalendar().setYear(dateBirthday[2]);
        mCalendarView.scrollToCalendar(dateBirthday[2],dateBirthday[1],dateBirthday[0]);

        mTextMonthDay.setOnClickListener(v -> {
            if (!mCalendarLayout.isExpand()) {
                mCalendarLayout.expand();
                return;
            }
            mCalendarView.showYearSelectLayout(dateBirthday[2]);
            mTextLunar.setVisibility(View.GONE);
            mTextYear.setVisibility(View.GONE);
            mTextMonthDay.setText(String.valueOf(mYear));
            isSelectYear = true;
        });
        dialog.findViewById(R.id.fl_current).setOnClickListener(v -> mCalendarView.scrollToCurrent());

        mCalendarLayout = dialog.findViewById(R.id.calendarLayout);
        mCalendarView.setOnYearChangeListener(year -> mTextMonthDay.setText(String.valueOf(year)));
        mCalendarView.setOnMonthChangeListener((year, month) -> isSelectMonth = true);
        mCalendarView.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() {
            @Override
            public void onCalendarOutOfRange(Calendar calendar) {

            }

            @Override
            public void onCalendarSelect(Calendar calendar, boolean isClick) {
                mTextLunar.setVisibility(View.VISIBLE);
                mTextYear.setVisibility(View.VISIBLE);
                mTextMonthDay.setText(mCalendarView.getSelectedCalendar().getDay() + " " + arrayMonth.get(mCalendarView.getSelectedCalendar().getMonth() - 1));
                mTextYear.setText(String.valueOf(mCalendarView.getSelectedCalendar().getYear()));
                if(!isSelectYear && !isSelectMonth) {
                    dateBirthday[0] = mCalendarView.getSelectedCalendar().getDay();
                    dateBirthday[1] = mCalendarView.getSelectedCalendar().getMonth();
                    dateBirthday[2] = mCalendarView.getSelectedCalendar().getYear();
                    new Handler().postDelayed(() -> {
                        inputBirthday.setText(formatDate(dateBirthday));
                        dialog.cancel();
                    }, 100);
                } else {
                    if(isSelectYear)
                        isSelectYear = false;
                    if(isSelectMonth)
                        isSelectMonth = false;
                }
            }
        });
        mTextYear.setText(String.valueOf(mCalendarView.getSelectedCalendar().getYear()));
        mYear = mCalendarView.getCurYear();
        mTextMonthDay.setText(mCalendarView.getSelectedCalendar().getDay() + " " + arrayMonth.get(mCalendarView.getSelectedCalendar().getMonth() - 1));
        mTextLunar.setText("Year");
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));
        //////////////////////////////

        dialog.getWindow().setAttributes(lp);
        Window window = dialog.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        dialog.show();
    }
    @SuppressWarnings("all")
    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);
        calendar.setScheme(text);
        return calendar;
    }

}
