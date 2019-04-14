package com.korlab.cosmocalendar.view.delegate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.korlab.cosmocalendar.adapter.viewholder.DayOfWeekHolder;
import com.korlab.cosmocalendar.model.Day;
import com.korlab.cosmocalendar.view.CalendarView;
import com.korlab.cosmocalendar.R;

public class DayOfWeekDelegate extends BaseDelegate {

    public DayOfWeekDelegate(CalendarView calendarView) {
        this.calendarView = calendarView;
    }

    public DayOfWeekHolder onCreateDayHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_day_of_week, parent, false);
        return new DayOfWeekHolder(view, calendarView);
    }

    public void onBindDayHolder(Day day, DayOfWeekHolder holder, int position) {
        holder.bind(day);
    }
}
