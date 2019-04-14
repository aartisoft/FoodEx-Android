package com.korlab.cosmocalendar.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import com.korlab.cosmocalendar.model.Day;
import com.korlab.cosmocalendar.utils.Constants;
import com.korlab.cosmocalendar.view.CalendarView;
import com.korlab.cosmocalendar.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DayOfWeekHolder extends BaseDayHolder {

    private SimpleDateFormat mDayOfWeekFormatter;

    public DayOfWeekHolder(View itemView, CalendarView calendarView) {
        super(itemView, calendarView);
        tvDay = (TextView) itemView.findViewById(R.id.tv_day_name);
        mDayOfWeekFormatter = new SimpleDateFormat(Constants.DAY_NAME_FORMAT, Locale.getDefault());
    }

    public void bind(Day day) {
        tvDay.setText(mDayOfWeekFormatter.format(day.getCalendar().getTime()));
        tvDay.setTextColor(calendarView.getWeekDayTitleTextColor());
    }
}