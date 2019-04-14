package com.korlab.cosmocalendar.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import com.korlab.cosmocalendar.model.Day;
import com.korlab.cosmocalendar.view.CalendarView;
import com.korlab.cosmocalendar.R;

public class OtherDayHolder extends BaseDayHolder {

    public OtherDayHolder(View itemView, CalendarView calendarView) {
        super(itemView, calendarView);
        tvDay = (TextView) itemView.findViewById(R.id.tv_day_number);
    }

    public void bind(Day day) {
        tvDay.setText(String.valueOf(day.getDayNumber()));
        tvDay.setTextColor(calendarView.getOtherDayTextColor());
    }
}
