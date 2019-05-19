package com.korlab.foodex;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.korlab.foodex.Data.ProgramDay;
import com.korlab.foodex.UI.MaterialButton;
import com.korlab.foodex.UI.Toolbar;
import com.korlab.foodex.Data.User;
import com.korlab.foodex.Technical.Helper;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class PlanPauseMove extends AppCompatActivity {
    private User user;
    private Map<Date, ProgramDay> programDays;

    private static PlanPauseMove instance;
    public static PlanPauseMove getInstance() {
        return instance;
    }
    private LinearLayout toolbarContainer;
    private ImageView toolbarLeft;
    private MaterialButton buttonPause, buttonMoveFrom,buttonMoveTo;
    private Date datePause, dateMoveFrom, dateMoveTo;

    private TextView mTextMonthDay;
    private TextView mTextYear;
    private TextView mTextLunar;
    private TextView mTextCurrentDay;
    private CalendarView mCalendarView;
    private int mYear, mMonth;
    private List<String> months = Helper.getTranslate(Helper.Translate.months, MainMenu.getInstance());
    private CalendarLayout mCalendarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_pause_move);
        instance = this;
        Helper.setStatusBarColor(getWindow(), ContextCompat.getColor(getBaseContext(), R.color.white));
        Helper.setStatusBarIconWhite(getWindow());
        user = Helper.getUserData();
        programDays = Helper.getProgramDaysData();
        findView();
        toolbarContainer.addView(new Toolbar(getInstance(), false, "Plan Control",
                getInstance().getDrawable(R.drawable.toolbar_arrow_left), null));
        toolbarLeft = findViewById(R.id.toolbar_left_icon);
        toolbarLeft.setOnClickListener(v -> super.finish());

        buttonPause.setOnClickListener(v->{
            datePause = new Date(mCalendarView.getSelectedCalendar().getYear(), mCalendarView.getSelectedCalendar().getMonth(), mCalendarView.getSelectedCalendar().getDay());
            showConfirmationDialog(datePause, null, null);
        });
        buttonMoveFrom.setOnClickListener(v->{
            int year = mCalendarView.getSelectedCalendar().getYear();
            int month = mCalendarView.getSelectedCalendar().getMonth();
            int day = mCalendarView.getSelectedCalendar().getDay();
            dateMoveFrom = new Date(year, month, day);
            mCalendarView.getSelectedCalendar().setSchemeColor(R.color.email);
            buttonMoveFrom.setVisibility(View.GONE);
            buttonMoveTo.setVisibility(View.VISIBLE);
            buttonPause.setEnabled(false);
            buttonPause.setTextColor(getResources().getColor(R.color.dark_text));
            buttonPause.setBorderColor(getResources().getColor(R.color.dark_text));
            Map<String, Calendar> map = new HashMap<>();
            map.put(getSchemeCalendar(year, month, day, 0x99ff0000, "Раз").toString(),
                    getSchemeCalendar(year, month, day, 0x99ff0000, "Раз"));
            mCalendarView.setSchemeDate(map);
        });
        buttonMoveTo.setOnClickListener(v->{
            dateMoveTo = new Date(mCalendarView.getSelectedCalendar().getYear(), mCalendarView.getSelectedCalendar().getMonth(), mCalendarView.getSelectedCalendar().getDay());
            showConfirmationDialog(null, dateMoveFrom, dateMoveTo);
        });

        initCalendar();
    }

    private void showConfirmationDialog(Date datePause, Date dateMoveFrom, Date dateMoveTo) {
        final Dialog dialog = new Dialog(getInstance());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_plan_control);
        dialog.setCancelable(true);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(R.color.transparent);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        TextView dialogHeader = dialog.findViewById(R.id.dialog_header);
        TextView dialogContent = dialog.findViewById(R.id.dialog_content);
        MaterialButton ok = dialog.findViewById(R.id.ok);
        MaterialButton cancel = dialog.findViewById(R.id.cancel);

        if(datePause != null) {
            dialogHeader.setText("Pause plan");
            dialogContent.setText("Do you really want to suspend the plan from "+months.get(datePause.getMonth() - 1)+" "+datePause.getDate()+"?");
        } else {
            dialogHeader.setText("Move plan");
            dialogContent.setText("Do you really want to postpone the day of food from " + months.get(dateMoveFrom.getMonth() - 1) + " " + dateMoveFrom.getDate()
                    + " to " + months.get(dateMoveTo.getMonth() - 1) + " " + dateMoveTo.getDate() + "?");
        }


        ok.setOnClickListener((v) -> {
            restoreButtonAndCalendar(dateMoveFrom);
            dialog.dismiss();
            super.finish();
        });
        cancel.setOnClickListener(v -> {
            restoreButtonAndCalendar(dateMoveFrom);
            dialog.dismiss();
        });

        dialog.setOnDismissListener(dialog1 -> restoreButtonAndCalendar(dateMoveFrom));
        dialog.getWindow().setAttributes(lp);
        Window window = dialog.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.show();
    }

    public void restoreButtonAndCalendar(Date dateMoveFrom) {
        buttonMoveFrom.setVisibility(View.VISIBLE);
        buttonMoveTo.setVisibility(View.GONE);
        buttonPause.setEnabled(true);
        buttonPause.setTextColor(getResources().getColor(R.color.email));
        buttonPause.setBorderColor(getResources().getColor(R.color.email));
        if(dateMoveFrom != null) {
            Helper.log(dateMoveFrom.getYear() + " " + dateMoveFrom.getMonth());
            initProgramsDays(dateMoveFrom.getYear(), dateMoveFrom.getMonth());
        }
    }


    private void initCalendar() {
        mCalendarView.setSelectSingleMode();
        mTextMonthDay.setOnClickListener(v -> {
            if (!mCalendarLayout.isExpand()) {
                mCalendarLayout.expand();
                return;
            }
            mCalendarView.showYearSelectLayout(mCalendarView.getCurYear());
            mTextLunar.setVisibility(View.GONE);
            mTextYear.setVisibility(View.GONE);
            mTextMonthDay.setText(String.valueOf(mYear));
        });
        findViewById(R.id.fl_current).setOnClickListener(v -> mCalendarView.scrollToCurrent());
        mCalendarLayout = findViewById(R.id.calendarLayout);
        mCalendarView.setOnYearChangeListener(year -> {
            mYear = year;
            mMonth = mCalendarView.getSelectedCalendar().getMonth();
            initProgramsDays(mYear, mMonth);
        });
        mCalendarView.setOnMonthChangeListener((year, month) -> {
            mYear = year;
            mMonth = month;
            initProgramsDays(mYear, mMonth);
        });
        mCalendarView.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() {
            @Override public void onCalendarOutOfRange(Calendar calendar) { }

            @Override
            public void onCalendarSelect(Calendar calendar, boolean isClick) {
                mTextLunar.setVisibility(View.VISIBLE);
                mTextYear.setVisibility(View.VISIBLE);
                mTextMonthDay.setText(mCalendarView.getSelectedCalendar().getDay() + " " + months.get(mCalendarView.getSelectedCalendar().getMonth() - 1));
                mTextYear.setText(String.valueOf(mCalendarView.getSelectedCalendar().getYear()));
            }
        });
        mTextYear.setText(String.valueOf(mCalendarView.getSelectedCalendar().getYear()));
        mYear = mCalendarView.getSelectedCalendar().getYear();
        mMonth = mCalendarView.getSelectedCalendar().getMonth();
        mTextMonthDay.setText(mCalendarView.getSelectedCalendar().getDay() + " " + months.get(mCalendarView.getSelectedCalendar().getMonth() - 1));
        mTextLunar.setText("Year");
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));
        initProgramsDays(mYear, mMonth);
    }

    private void findView() {
        toolbarContainer = findViewById(R.id.toolbar_container);
        mTextMonthDay = findViewById(R.id.tv_month_day);
        mTextYear = findViewById(R.id.tv_year);
        mTextLunar = findViewById(R.id.tv_lunar);
        mCalendarView = findViewById(R.id.calendarView);
        mTextCurrentDay = findViewById(R.id.tv_current_day);
        buttonPause = findViewById(R.id.button_pause);
        buttonMoveFrom = findViewById(R.id.button_move_from);
        buttonMoveTo = findViewById(R.id.button_move_to);
    }
    private void initProgramsDays(int year, int month) {
//        Helper.log("initProgramsDays======================================");
        Map<String, Calendar> map = new HashMap<>();
        for (int i = 1; i < Helper.getMaxNumbersInMonth(year, month); i++) {
            try {
                int day = programDays.get(new Date(year, month, i)).getDate().getDate();
                map.put(getSchemeCalendar(year, month, day, 0x993ecd8e, "Раз").toString(),
                        getSchemeCalendar(year, month, day, 0x993ecd8e, "Раз"));
            } catch (Exception e) {
//                Helper.log("For " + year + " " + month + " " + i + " program not found");
            }
        }
        mCalendarView.setSchemeDate(map);
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
