package com.korlab.cosmocalendar.view.delegate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.korlab.cosmocalendar.adapter.viewholder.OtherDayHolder;
import com.korlab.cosmocalendar.model.Day;
import com.korlab.cosmocalendar.view.CalendarView;
import com.korlab.cosmocalendar.R;

public class OtherDayDelegate {

    private CalendarView calendarView;

    public OtherDayDelegate(CalendarView calendarView) {
        this.calendarView = calendarView;
    }

    public OtherDayHolder onCreateDayHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_other_day, parent, false);
        return new OtherDayHolder(view, calendarView);
    }

    public void onBindDayHolder(Day day, OtherDayHolder holder, int position) {
        holder.bind(day);
    }
}
