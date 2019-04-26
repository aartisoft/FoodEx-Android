package com.korlab.foodex;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.korlab.foodex.Components.Article;
import com.korlab.foodex.Components.ArticleAdapter;
import com.korlab.foodex.Components.HistoryCard;
import com.korlab.foodex.Data.Dish;
import com.korlab.foodex.Data.Meal;
import com.korlab.foodex.Data.ProgramDay;
import com.korlab.foodex.Technical.Helper;
import com.korlab.foodex.UI.group.GroupItemDecoration;
import com.korlab.foodex.UI.group.GroupRecyclerView;

import java.util.ArrayList;
import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentHome extends Fragment {
    public static final String ARG_PAGE_HOME = "ARG_PAGE_HOME";

    private int mPage;
    private Map<Date, ProgramDay> programDays;
    boolean isVisibleAll = false;

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
        programDays = new HashMap<>();

        for (int i = 0; i < 29; i+=3) {
            List<Meal> mealList = new ArrayList<>();
            List<Dish> dishListBreakfast = new ArrayList<>();
            // Breakfast
            dishListBreakfast.add(new Dish("1Fruit noodles", 0, 187, 23, 34, 56));
            dishListBreakfast.add(new Dish("1Pumpkin puree soup with ginger", 1, 187, 23, 34, 56));
            dishListBreakfast.add(new Dish("1Veal tongue with asparagus in cream sauce", 2, 187, 23, 34, 56));
            dishListBreakfast.add(new Dish("1Rice mix with vegetables", 3, 187, 23, 34, 56));
            dishListBreakfast.add(new Dish("1Wellness Natural Balance", 4, 187, 23, 34, 56));
            mealList.add(new Meal(0, dishListBreakfast));

            // Brunch
            List<Dish> dishListBrunch = new ArrayList<>();
            dishListBrunch.add(new Dish("2Fruit noodles", 0, 187, 23, 34, 56));
            dishListBrunch.add(new Dish("2Pumpkin puree soup with ginger", 1, 187, 23, 34, 56));
            dishListBrunch.add(new Dish("2Veal tongue with asparagus in cream sauce", 2, 187, 23, 34, 56));
            dishListBrunch.add(new Dish("2Rice mix with vegetables", 3, 187, 23, 34, 56));
            dishListBrunch.add(new Dish("2Wellness Natural Balance", 4, 187, 23, 34, 56));
            mealList.add(new Meal(1, dishListBrunch));

            // Lunch
            List<Dish> dishListLunch = new ArrayList<>();
            dishListLunch.add(new Dish("3Fruit noodles", 0, 187, 23, 34, 56));
            dishListLunch.add(new Dish("3Pumpkin puree soup with ginger", 1, 187, 23, 34, 56));
            dishListLunch.add(new Dish("3Veal tongue with asparagus in cream sauce", 2, 187, 23, 34, 56));
            dishListLunch.add(new Dish("3Rice mix with vegetables", 3, 187, 23, 34, 56));
            dishListLunch.add(new Dish("3Wellness Natural Balance", 4, 187, 23, 34, 56));
            mealList.add(new Meal(2, dishListLunch));

            // Afternoon meals
            List<Dish> dishListAfternoonMeals = new ArrayList<>();
            dishListAfternoonMeals.add(new Dish("4Fruit noodles", 0, 187, 23, 34, 56));
            dishListAfternoonMeals.add(new Dish("4Pumpkin puree soup with ginger", 1, 187, 23, 34, 56));
            dishListAfternoonMeals.add(new Dish("4Veal tongue with asparagus in cream sauce", 2, 187, 23, 34, 56));
            dishListAfternoonMeals.add(new Dish("4Rice mix with vegetables", 3, 187, 23, 34, 56));
            dishListAfternoonMeals.add(new Dish("4Wellness Natural Balance", 4, 187, 23, 34, 56));
            mealList.add(new Meal(3, dishListAfternoonMeals));

            // Second afternoon meals
            List<Dish> dishListSecondAfternoonMeals = new ArrayList<>();
            dishListSecondAfternoonMeals.add(new Dish("5Fruit noodles", 0, 187, 23, 34, 56));
            dishListSecondAfternoonMeals.add(new Dish("5Pumpkin puree soup with ginger", 1, 187, 23, 34, 56));
            dishListSecondAfternoonMeals.add(new Dish("5Veal tongue with asparagus in cream sauce", 2, 187, 23, 34, 56));
            dishListSecondAfternoonMeals.add(new Dish("5Rice mix with vegetables", 3, 187, 23, 34, 56));
            dishListSecondAfternoonMeals.add(new Dish("5Wellness Natural Balance", 4, 187, 23, 34, 56));
            mealList.add(new Meal(4, dishListSecondAfternoonMeals));

            // Dinner
            List<Dish> dishListDinner = new ArrayList<>();
            dishListDinner.clear();
            dishListDinner.add(new Dish("Fruit noodles", 0, 187, 23, 34, 56));
            dishListDinner.add(new Dish("Pumpkin puree soup with ginger", 1, 187, 23, 34, 56));
            dishListDinner.add(new Dish("Veal tongue with asparagus in cream sauce", 2, 187, 23, 34, 56));
            dishListDinner.add(new Dish("Rice mix with vegetables", 3, 187, 23, 34, 56));
            dishListDinner.add(new Dish("Wellness Natural Balance", 4, 187, 23, 34, 56));
            mealList.add(new Meal(5, dishListDinner));
            Date date = new Date(2019, 4, i + 1);
            programDays.put(date, new ProgramDay(date, mealList));
            programDays.put(new Date(2019, 3, i + 1), new ProgramDay(new Date(2019, 3, i + 1), mealList));
        }

        switch (mPage) {
            case 1:
                view = inflater.inflate(R.layout.fragment_home_dashboard, container, false);
                break;
            case 2:
                view = inflater.inflate(R.layout.fragment_home_history, container, false);


                ViewGroup root;
                TextView buttonAllHistory;
                root = view.findViewById(R.id.root);
                buttonAllHistory = view.findViewById(R.id.button_all_history);

                updateDayCardsHistory(root, isVisibleAll);

                buttonAllHistory.setOnClickListener(v -> {
                    isVisibleAll = !isVisibleAll;
                    updateDayCardsHistory(root, isVisibleAll);
                });


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
                updateDayCardsCalendar(new Date(mCalendarView.getCurYear(), mCalendarView.getCurMonth(), mCalendarView.getCurDay()));

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
                mCalendarView.setOnYearChangeListener(year -> {
                    mTextMonthDay.setText(String.valueOf(year));
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
                    @Override public void onCalendarOutOfRange(Calendar calendar) {}
                    @Override
                    public void onCalendarSelect(Calendar calendar, boolean isClick) {
                        mTextLunar.setVisibility(View.VISIBLE);
                        mTextYear.setVisibility(View.VISIBLE);
                        mTextMonthDay.setText(mCalendarView.getSelectedCalendar().getDay() + " " + months.get(mCalendarView.getSelectedCalendar().getMonth() - 1));
                        mTextYear.setText(String.valueOf(mCalendarView.getSelectedCalendar().getYear()));
                        int year = mCalendarView.getSelectedCalendar().getYear();
                        int month = mCalendarView.getSelectedCalendar().getMonth();
                        int day = mCalendarView.getSelectedCalendar().getDay();
                        updateDayCardsCalendar(new Date(year, month, day));
                    }
                });
                mTextYear.setText(String.valueOf(mCalendarView.getSelectedCalendar().getYear()));

                mYear = mCalendarView.getSelectedCalendar().getYear();
                mMonth = mCalendarView.getSelectedCalendar().getMonth();
                mTextMonthDay.setText(mCalendarView.getSelectedCalendar().getDay() + " " + months.get(mCalendarView.getSelectedCalendar().getMonth() - 1));
                mTextLunar.setText("Year");
                mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));

                initProgramsDays(mYear, mMonth);
                //////////////////////////////
                break;


        }
        return view;
    }

    private void updateDayCardsHistory(ViewGroup root, boolean isVisibleAll) {
        root.removeAllViews();
        for (int i = programDays.size(); i > (isVisibleAll ? 0 : programDays.size() - 5); i--) {
            HistoryCard hc = new HistoryCard(getActivity().getBaseContext(), "id_" + i);
            hc.setHeader("<font color='" + getResources().getColor(R.color.dark_text)
                    + "'><b>" + "Day #" + i + "</b> " + " of the meal program </font>" + "<b>" + "«Individual menu»" + "</b>");
            hc.setTitle("Title lorem ipsum sit dolor amet" + i);
            hc.setCost(i + 2 * 750 / 78);
            hc.setContent("Comment lorem ipsum sit dolor amet " + i);
            hc.setDate("29 Jan 2019");
            root.addView(hc.getView());
        }
    }

    private void initProgramsDays(int year, int month) {

        Helper.log("initProgramsDays======================================");

        Map<String, Calendar> map = new HashMap<>();


        for(int i=1; i<Helper.getMaxNumbersInMonth(year, month); i++) {
            try {
                int day = programDays.get(new Date(year,month,i)).getDate().getDate();
                map.put(getSchemeCalendar(year, month, day, 0x993ecd8e, "Раз").toString(),
                        getSchemeCalendar(year, month, day, 0x993ecd8e, "Раз"));
            } catch (Exception e) {
                Helper.log("For " + year + " " + month + " " + i + " program not found");
            }
        }
//
//        map.put(getSchemeCalendar(year, month, 1, 0x993ecd8e, "Раз").toString(),
//                getSchemeCalendar(year, month, 1, 0x993ecd8e, "Раз"));
//        map.put(getSchemeCalendar(year, month, 4, 0x993ecd8e, "Раз").toString(),
//                getSchemeCalendar(year, month, 4, 0x993ecd8e, "Раз"));
//        map.put(getSchemeCalendar(year, month, 6, 0x993ecd8e, "Раз").toString(),
//                getSchemeCalendar(year, month, 6, 0x993ecd8e, "Раз"));
//        map.put(getSchemeCalendar(year, month, 7, 0x993ecd8e, "Раз").toString(),
//                getSchemeCalendar(year, month, 7, 0x993ecd8e, "Раз"));
//        map.put(getSchemeCalendar(year, month, 8, 0x993ecd8e, "Раз").toString(),
//                getSchemeCalendar(year, month, 8, 0x993ecd8e, "Раз"));
//        map.put(getSchemeCalendar(year, month, 12, 0x993ecd8e, "Раз").toString(),
//                getSchemeCalendar(year, month, 12, 0x993ecd8e, "Раз"));
//        map.put(getSchemeCalendar(year, month, 14, 0x99ff0000, "Раз").toString(),
//                getSchemeCalendar(year, month, 14, 0x99ff0000, "Раз"));
//        map.put(getSchemeCalendar(year, month, 15, 0x993ecd8e, "Раз").toString(),
//                getSchemeCalendar(year, month, 15, 0x993ecd8e, "Раз"));
//        map.put(getSchemeCalendar(year, month, 16, 0x993ecd8e, "Раз").toString(),
//                getSchemeCalendar(year, month, 16, 0x993ecd8e, "Раз"));
//        map.put(getSchemeCalendar(year, month, 19, 0x993ecd8e, "Раз").toString(),
//                getSchemeCalendar(year, month, 19, 0x993ecd8e, "Раз"));
//        map.put(getSchemeCalendar(year, month, 21, 0x993ecd8e, "Раз").toString(),
//                getSchemeCalendar(year, month, 21, 0x993ecd8e, "Раз"));
//        map.put(getSchemeCalendar(year, month, 22, 0x993ecd8e, "Раз").toString(),
//                getSchemeCalendar(year, month, 22, 0x993ecd8e, "Раз"));
//        map.put(getSchemeCalendar(year, month, 23, 0x993ecd8e, "Раз").toString(),
//                getSchemeCalendar(year, month, 23, 0x993ecd8e, "Раз"));
//        map.put(getSchemeCalendar(year, month, 24, 0x993ecd8e, "Раз").toString(),
//                getSchemeCalendar(year, month, 24, 0x993ecd8e, "Раз"));
//        map.put(getSchemeCalendar(year, month, 25, 0x993ecd8e, "Раз").toString(),
//                getSchemeCalendar(year, month, 25, 0x993ecd8e, "Раз"));
        mCalendarView.setSchemeDate(map);
    }


    private TextView mTextMonthDay;
    private TextView mTextYear;
    private TextView mTextLunar;
    private TextView mTextCurrentDay;
    private CalendarView mCalendarView;
    private int mYear, mMonth;
    private List<String> months = Helper.getTranslate(Helper.Translate.months, MainMenu.getInstance());
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

    private void updateDayCardsCalendar(Date date) {
        ArticleAdapter articleAdapter = new ArticleAdapter(getActivity().getBaseContext());
        articleAdapter.init(programDays.get(date));
        recyclerView.setAdapter(articleAdapter);
        recyclerView.notifyDataSetChanged();
    }
}
