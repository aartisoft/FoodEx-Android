package com.korlab.foodex;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.korlab.foodex.Components.Article;
import com.korlab.foodex.Components.ArticleAdapter;
import com.korlab.foodex.Components.CalendarCard;
import com.korlab.foodex.Components.HistoryCard;
import com.korlab.foodex.Data.Meal;
import com.korlab.foodex.Data.ProgramDay;
import com.korlab.foodex.Technical.Helper;
import com.korlab.foodex.UI.group.GroupItemDecoration;
import com.korlab.foodex.UI.group.GroupRecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FragmentHome extends Fragment {
    public static final String ARG_PAGE_HOME = "ARG_PAGE_HOME";

    private int mPage;
    private Map<Date, ProgramDay> programDays;

    public static FragmentHome newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_HOME, page);
        FragmentHome fragment = new FragmentHome();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPage = getArguments().getInt(ARG_PAGE_HOME);
        }
    }

    GroupRecyclerView recyclerView;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        TextView textView;
        programDays = new HashMap<>();

        for(int i=0; i< 29; i++) {
            List<Meal> mealList = new ArrayList<>();
            mealList.add(new Meal("Breakfast", 1900+i,87,23,190));
            mealList.add(new Meal("Brunch", 1900+i,87,23,190));
            mealList.add(new Meal("Lunch", 1900+i,87,23,190));
            mealList.add(new Meal("Afternoon meals", 1900+i,87,23,190));
            mealList.add(new Meal("Dinner", 1900+i,87,23,190));
            programDays.put(new Date(2019,4,1+i), new ProgramDay(new Date(2019,4,17), mealList));
        }

        switch (mPage) {
            case 1:
                view = inflater.inflate(R.layout.fragment_home_dashboard, container, false);

                break;
            case 2:
                view = inflater.inflate(R.layout.fragment_home_history, container, false);



                boolean isVisibleAll = false;
                ViewGroup root;
                root = view.findViewById(R.id.root);





                for(int i=0; i< (isVisibleAll ? programDays.size() : 5); i++) {
                    HistoryCard hc = new HistoryCard(getActivity().getBaseContext(), "id_" + i);
                    hc.setHeader("<font color='" + getResources().getColor(R.color.dark_text)
                            + "'><b>" + "Day #" + i + "</b> " + " of the meal program </font>" + "<b>" + "«Individual menu»"+ "</b>");
                    hc.setTitle("Title lorem ipsum sit dolor amet" + i);
                    hc.setCost(i+2*750/78);
                    hc.setContent("Comment lorem ipsum sit dolor amet " + i);
                    hc.setDate("29 Jan 2019");
                    root.addView(hc.getView());
                }
                Helper.log("Fragment Home Your Menu#" + mPage);
                break;
            case 3:
                view = inflater.inflate(R.layout.fragment_home_history_calendar, container, false);

                //////////////////////////////
                recyclerView = view.findViewById(R.id.recyclerView);
                LinearLayoutManager manager = new LinearLayoutManager(getActivity().getBaseContext());
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(manager);
                recyclerView.addItemDecoration(new GroupItemDecoration<String, Article>());

                mTextMonthDay = view.findViewById(R.id.tv_month_day);
                mTextYear = view.findViewById(R.id.tv_year);
                mTextLunar = view.findViewById(R.id.tv_lunar);
                mCalendarView = view.findViewById(R.id.calendarView);
                mTextCurrentDay = view.findViewById(R.id.tv_current_day);
                mCalendarView.setSelectSingleMode();
//                mCalendarView.getSelectedCalendar().setDay(dateBirthday[0]);
//                mCalendarView.getSelectedCalendar().setMonth(dateBirthday[1]);
//                mCalendarView.getSelectedCalendar().setYear(dateBirthday[2]);
//                mCalendarView.scrollToCalendar(dateBirthday[2],dateBirthday[1],dateBirthday[0]);

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
                view.findViewById(R.id.fl_current).setOnClickListener(v -> mCalendarView.scrollToCurrent());

                mCalendarLayout = view.findViewById(R.id.calendarLayout);
                mCalendarView.setOnYearChangeListener(year -> mTextMonthDay.setText(String.valueOf(year)));
                mCalendarView.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() {
                    @Override
                    public void onCalendarOutOfRange(Calendar calendar) {

                    }

                    @Override
                    public void onCalendarSelect(Calendar calendar, boolean isClick) {
                        mTextLunar.setVisibility(View.VISIBLE);
                        mTextYear.setVisibility(View.VISIBLE);
                        mTextMonthDay.setText(mCalendarView.getSelectedCalendar().getDay() + " " + months[mCalendarView.getSelectedCalendar().getMonth() - 1]);
                        mTextYear.setText(String.valueOf(mCalendarView.getSelectedCalendar().getYear()));
                        int year = mCalendarView.getSelectedCalendar().getYear();
                        int month = mCalendarView.getSelectedCalendar().getMonth();
                        int day = mCalendarView.getSelectedCalendar().getDay();
                        updateDayCards(new Date(year, month, day));
                    }
                });
                mTextYear.setText(String.valueOf(mCalendarView.getSelectedCalendar().getYear()));
                mYear = mCalendarView.getCurYear();
                mTextMonthDay.setText(mCalendarView.getSelectedCalendar().getDay() + " " + months[mCalendarView.getSelectedCalendar().getMonth() - 1]);
                mTextLunar.setText("Year");
                mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));

                initProgramsDays();
                //////////////////////////////
                break;


        }
        return view;
    }

    private void initProgramsDays() {
        int year = mCalendarView.getCurYear();
        int month = mCalendarView.getCurMonth();
        Map<String, Calendar> map = new HashMap<>();
        map.put(getSchemeCalendar(year, month, 1, 0x993ecd8e, "Раз").toString(),
                getSchemeCalendar(year, month, 1, 0x993ecd8e, "Раз"));
        map.put(getSchemeCalendar(year, month, 2, 0x993ecd8e, "Раз").toString(),
                getSchemeCalendar(year, month, 2, 0x993ecd8e, "Раз"));
        map.put(getSchemeCalendar(year, month, 3, 0x993ecd8e, "Раз").toString(),
                getSchemeCalendar(year, month, 3, 0x993ecd8e, "Раз"));
        map.put(getSchemeCalendar(year, month, 4, 0x993ecd8e, "Раз").toString(),
                getSchemeCalendar(year, month, 4, 0x993ecd8e, "Раз"));
        map.put(getSchemeCalendar(year, month, 5, 0x993ecd8e, "Раз").toString(),
                getSchemeCalendar(year, month, 5, 0x993ecd8e, "Раз"));
        map.put(getSchemeCalendar(year, month, 6, 0x993ecd8e, "Раз").toString(),
                getSchemeCalendar(year, month, 6, 0x993ecd8e, "Раз"));
        map.put(getSchemeCalendar(year, month, 7, 0x99ff0000, "Раз").toString(),
                getSchemeCalendar(year, month, 7, 0x99ff0000, "Раз"));
        map.put(getSchemeCalendar(year, month, 8, 0x993ecd8e, "Раз").toString(),
                getSchemeCalendar(year, month, 8, 0x993ecd8e, "Раз"));
        map.put(getSchemeCalendar(year, month, 9, 0x993ecd8e, "Раз").toString(),
                getSchemeCalendar(year, month, 9, 0x993ecd8e, "Раз"));
        mCalendarView.setSchemeDate(map);
    }


    private TextView mTextMonthDay;
    private TextView mTextYear;
    private TextView mTextLunar;
    private TextView mTextCurrentDay;
    private CalendarView mCalendarView;
    private int mYear;
    private String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private CalendarLayout mCalendarLayout;


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

    private void updateDayCards(Date date) {


        ArticleAdapter articleAdapter = new ArticleAdapter(getActivity().getBaseContext());
        articleAdapter.init(programDays.get(date));
        recyclerView.setAdapter(articleAdapter);
        recyclerView.notifyDataSetChanged();

//        ArticleAdapter articleAdapter = new ArticleAdapter(getActivity().getBaseContext());
//        articleAdapter.init(programDays.get(date));
//        recyclerView.setAdapter(articleAdapter);
//        recyclerView.notifyDataSetChanged();
    }
}
