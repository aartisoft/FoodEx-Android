package com.korlab.cosmocalendar.view.delegate;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.korlab.cosmocalendar.adapter.MonthAdapter;
import com.korlab.cosmocalendar.adapter.viewholder.DayHolder;
import com.korlab.cosmocalendar.model.Day;
import com.korlab.cosmocalendar.selection.BaseSelectionManager;
import com.korlab.cosmocalendar.selection.MultipleSelectionManager;
import com.korlab.cosmocalendar.view.CalendarView;
import com.korlab.cosmocalendar.R;

public class DayDelegate extends BaseDelegate {

    private MonthAdapter monthAdapter;

    public DayDelegate(CalendarView calendarView, MonthAdapter monthAdapter) {
        this.calendarView = calendarView;
        this.monthAdapter = monthAdapter;
    }

    public DayHolder onCreateDayHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_day, parent, false);
        return new DayHolder(view, calendarView);
    }

    public void onBindDayHolder(final RecyclerView.Adapter daysAdapter, final Day day,
                                final DayHolder holder, final int position) {
        final BaseSelectionManager selectionManager = monthAdapter.getSelectionManager();
        holder.bind(day, selectionManager);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!day.isDisabled()) {
                    selectionManager.toggleDay(day);
                    if (selectionManager instanceof MultipleSelectionManager) {
                        daysAdapter.notifyItemChanged(position);
                    } else {
                        monthAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
