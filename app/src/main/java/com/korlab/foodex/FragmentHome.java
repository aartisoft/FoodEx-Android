package com.korlab.foodex;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.util.Consumer;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.google.firebase.Timestamp;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.hedgehog.ratingbar.RatingBar;
import com.korlab.foodex.Components.CalendarMeal;
import com.korlab.foodex.Components.CalendarMealAdapter;
import com.korlab.foodex.Components.HistoryCard;
import com.korlab.foodex.Data.Dish;
import com.korlab.foodex.Data.Meal;
import com.korlab.foodex.Data.ProgramDay;
import com.korlab.foodex.FireServer.FireRequest;
import com.korlab.foodex.Technical.Helper;
import com.korlab.foodex.UI.CustomPagerTransformer;
import com.korlab.foodex.UI.MaterialButton;
import com.korlab.foodex.UI.WrapContentHeightViewPager;
import com.korlab.foodex.UI.group.GroupItemDecoration;
import com.korlab.foodex.UI.group.GroupRecyclerView;
import com.korlab.foodex.Day.DayAdapter;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import kotlin.Unit;


public class FragmentHome extends Fragment {
    public static final String ARG_PAGE_HOME = "ARG_PAGE_HOME";

    private int mPage;
    private MainMenu activity;
    private Map<Date, ProgramDay> programDays;
    private boolean isVisibleAll = false;

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
        activity = MainMenu.getInstance();
        FireRequest.Companion.callFunction("getMonthDays", new HashMap<String, Object>(), this::onSuccessGotMonthDays, this::onFailGotMonthDays);

//        initProgramDays();

        switch (mPage) {
            case 1:
                view = inflater.inflate(R.layout.fragment_home_dashboard, container, false);
                WrapContentHeightViewPager viewPager;
                viewPager = view.findViewById(R.id.week_view_pager);
                TextView dateText = view.findViewById(R.id.date);
                TextView mealTime = view.findViewById(R.id.meal_time);
                ImageView prev = view.findViewById(R.id.prev);
                ImageView next = view.findViewById(R.id.next);
                DotsIndicator dotsIndicator = view.findViewById(R.id.dots_indicator);
                MaterialRippleLayout buttonFeedback = view.findViewById(R.id.button_feedback);

                ProgramDay programDay;
                java.util.Calendar calendar = java.util.Calendar.getInstance();

                Date date = new Date(calendar.get(java.util.Calendar.YEAR), calendar.get(java.util.Calendar.MONTH), calendar.get(java.util.Calendar.DATE));
                Helper.log("getTime " + date.toString());
                Helper.log("programDays.get(date) " + programDays.get(date));

                if (programDays.get(date) != null) {
                    programDay = programDays.get(date);
                } else {
                    Helper.log("push empty data");
                    List<Meal> mealList = new ArrayList<>();
                    mealList.add(new Meal(Meal.Type.BREAKFAST, new ArrayList<>()));
                    mealList.add(new Meal(Meal.Type.BRUNCH, new ArrayList<>()));
                    mealList.add(new Meal(Meal.Type.LUNCH, new ArrayList<>()));
                    mealList.add(new Meal(Meal.Type.AFTERNOONMEALS, new ArrayList<>()));
                    mealList.add(new Meal(Meal.Type.SECONDAFTERNOONMEALS, new ArrayList<>()));
                    mealList.add(new Meal(Meal.Type.DINNER, new ArrayList<>()));
                    programDay = new ProgramDay(date, mealList);
                }
                drawText(programDay.getMeals().get(0), date, dateText, mealTime);
                viewPager.setAdapter(new DayAdapter(LayoutInflater.from(getActivity()), programDay));
                viewPager.setPageTransformer(false, new CustomPagerTransformer(activity, 130));
                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int i, float v, int i1) {
                    }

                    @Override
                    public void onPageSelected(int i) {
                        drawText(programDay.getMeals().get(i), date, dateText, mealTime);
                    }

                    @Override
                    public void onPageScrollStateChanged(int i) {
                    }
                });
                dotsIndicator.setViewPager(viewPager);
                prev.setOnClickListener(v -> viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true));
                next.setOnClickListener(v -> viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true));
                buttonFeedback.setOnClickListener(v -> showDialogFeedbackForDay());
                break;
//            case 2:
//                view = inflater.inflate(R.layout.fragment_home_history, container, false);
//
//
//                ViewGroup root;
//                TextView buttonAllHistory;
//                root = view.findViewById(R.id.root);
//                buttonAllHistory = view.findViewById(R.id.button_all_history);
//
//                updateDayCardsHistory(root, isVisibleAll);
//
//                buttonAllHistory.setOnClickListener(v -> {
//                    buttonAllHistory.setVisibility(View.GONE);
//                    isVisibleAll = !isVisibleAll;
//                    updateDayCardsHistory(root, isVisibleAll);
//                });
//
//
//                Helper.log("Fragment Home Your Menu#" + mPage);
//                break;
            case 2:
                view = inflater.inflate(R.layout.fragment_home_history_calendar, container, false);

                //////////////////////////////
                recyclerView = view.findViewById(R.id.recyclerView);
                LinearLayoutManager manager = new LinearLayoutManager(getActivity().getBaseContext());
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(manager);
                recyclerView.addItemDecoration(new GroupItemDecoration<String, CalendarMeal>());
                mTextMonthDay = view.findViewById(R.id.tv_month_day);
                mTextYear = view.findViewById(R.id.tv_year);
                mTextLunar = view.findViewById(R.id.tv_lunar);
                mCalendarView = view.findViewById(R.id.calendarView);
                mTextCurrentDay = view.findViewById(R.id.tv_current_day);
                mCalendarView.setSelectSingleMode();
                updateDayCardsCalendar(new Date(mCalendarView.getCurYear()-1900, mCalendarView.getCurMonth()-1, mCalendarView.getCurDay()));
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
                    mYear = year - 1900;
                    mMonth = mCalendarView.getSelectedCalendar().getMonth();
//                    mTextMonthDay.setText(" " + months.get(mMonth - 1));
//                    drawDaysOnCalendar();
                });
                mCalendarView.setOnMonthChangeListener((year, month) -> {
                    mYear = year - 1900;
                    mMonth = month;
//                    mTextMonthDay.setText(" " + months.get(mMonth - 1));
//                    drawDaysOnCalendar();
                });
                mCalendarView.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() {
                    @Override
                    public void onCalendarOutOfRange(Calendar calendar) {
                    }

                    @Override
                    public void onCalendarSelect(Calendar calendar, boolean isClick) {
                        mTextLunar.setVisibility(View.VISIBLE);
                        mTextYear.setVisibility(View.VISIBLE);
                        mTextMonthDay.setText(mCalendarView.getSelectedCalendar().getDay() + " " + months.get(mCalendarView.getSelectedCalendar().getMonth() - 1));
                        mTextYear.setText(String.valueOf(mCalendarView.getSelectedCalendar().getYear()));
                        int year = mCalendarView.getSelectedCalendar().getYear();
                        int month = mCalendarView.getSelectedCalendar().getMonth();
                        int day = mCalendarView.getSelectedCalendar().getDay();
                        updateDayCardsCalendar(new Date(year-1900, month-1, day));
                    }
                });
                mTextYear.setText(String.valueOf(mCalendarView.getSelectedCalendar().getYear()));
                mYear = mCalendarView.getSelectedCalendar().getYear() - 1900;
                mMonth = mCalendarView.getSelectedCalendar().getMonth();
                mTextMonthDay.setText(mCalendarView.getSelectedCalendar().getDay() + " " + months.get(mCalendarView.getSelectedCalendar().getMonth() - 1));
                mTextLunar.setText("Year");
                mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));


//                drawDaysOnCalendar();
                //////////////////////////////
                break;
        }
        return view;
    }

    ArrayList monthDaysHashMap;

    private kotlin.Unit onSuccessGotMonthDays(HashMap<String, Object> data) {
        Helper.log("onSuccessGotMonthDays data: ");
        Helper.logObjectToJson(data);
        monthDaysHashMap = (ArrayList) data.get("dietDays");
        for (int i = 0; i < monthDaysHashMap.size(); i++) {
            Date date = new Date((Long) monthDaysHashMap.get(i));
            Helper.log("" + date);
            programDays.put(date, new ProgramDay(date, new ArrayList<>()));
        }
        drawDaysOnCalendar();
        return Unit.INSTANCE;
    }

    private kotlin.Unit onFailGotMonthDays() {
        Helper.log("onFailGotMonthDays");

        return Unit.INSTANCE;
    }

    static int ratingDeliveryStars = 0;
    static int ratingFoodStars = 0;

    public void showDialogFeedbackForDay() {
        final Dialog dialog = new Dialog(MainMenu.getInstance());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_feedback_day);
        dialog.setCancelable(true);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(R.color.transparent);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        MaterialButton ok = dialog.findViewById(R.id.ok);
        MaterialButton cancel = dialog.findViewById(R.id.cancel);
        RatingBar ratingDelivery = dialog.findViewById(R.id.rating_delivery);
        RatingBar ratingFood = dialog.findViewById(R.id.rating_food);
        EditText comment = dialog.findViewById(R.id.comment);


        ratingDelivery.setOnRatingChangeListener(ratingCount -> ratingDeliveryStars = (int) ratingCount);
        ratingFood.setOnRatingChangeListener(ratingCount -> ratingFoodStars = (int) ratingCount);


        ok.setOnClickListener((v) -> {
            Map<String, Object> ratingHashMap = new HashMap<>();
            ratingHashMap.put("deliveryRating", ratingDeliveryStars);
            ratingHashMap.put("foodRating", ratingFoodStars);
            ratingHashMap.put("comment", comment.getText().toString());
            ratingHashMap.put("date", new Date().getTime()/1000);
            FireRequest.Companion.callFunction("submitDietDayFeedback", (HashMap<String, Object>) ratingHashMap, this::onSuccessSendDayFeedback, this::onFailSendDayFeedback);
            dialog.dismiss();
        });
        cancel.setOnClickListener(v -> dialog.dismiss());

        dialog.getWindow().setAttributes(lp);
        Window window = dialog.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.show();
    }
    private kotlin.Unit onSuccessSendDayFeedback(HashMap<String, Object> stringObjectHashMap) {
        Helper.log("onSuccessSendDayFeedback");
        return Unit.INSTANCE;
    }

    private kotlin.Unit onFailSendDayFeedback() {
        Helper.log("onFailSendDayFeedback");
        return Unit.INSTANCE;
    }
//    private void initProgramDays() {
//        for (int i = 0; i < 29; i += 1) {
//            List<Meal> mealList = new ArrayList<>();
//            List<Dish> dishListBreakfast = new ArrayList<>();
//            // Breakfast
//            dishListBreakfast.add(new Dish("Fruit noodles", 0, 187, 23, 34, 56));
//            dishListBreakfast.add(new Dish("Pumpkin puree soup with ginger", 1, 187, 23, 34, 56));
//            dishListBreakfast.add(new Dish("Veal tongue with asparagus in cream sauce", 2, 187, 23, 34, 56));
//            dishListBreakfast.add(new Dish("Rice mix with vegetables", 3, 187, 23, 34, 56));
//            dishListBreakfast.add(new Dish("Wellness Natural Balance", 4, 187, 23, 34, 56));
//            mealList.add(new Meal(Meal.Type.BREAKFAST, dishListBreakfast));
//
//            // Brunch
//            List<Dish> dishListBrunch = new ArrayList<>();
//            dishListBrunch.add(new Dish("Fruit noodles", 0, 187, 23, 34, 56));
//            dishListBrunch.add(new Dish("Pumpkin puree soup with ginger", 1, 187, 23, 34, 56));
//            dishListBrunch.add(new Dish("Veal tongue with asparagus in cream sauce", 2, 187, 23, 34, 56));
//            dishListBrunch.add(new Dish("Rice mix with vegetables", 3, 187, 23, 34, 56));
//            dishListBrunch.add(new Dish("Wellness Natural Balance", 4, 187, 23, 34, 56));
//            mealList.add(new Meal(Meal.Type.BRUNCH, dishListBrunch));
//
//            // Lunch
//            List<Dish> dishListLunch = new ArrayList<>();
//            dishListLunch.add(new Dish("Fruit noodles", 0, 187, 23, 34, 56));
//            dishListLunch.add(new Dish("Pumpkin puree soup with ginger", 1, 187, 23, 34, 56));
//            dishListLunch.add(new Dish("Veal tongue with asparagus in cream sauce", 2, 187, 23, 34, 56));
//            dishListLunch.add(new Dish("Rice mix with vegetables", 3, 187, 23, 34, 56));
//            dishListLunch.add(new Dish("Wellness Natural Balance", 4, 187, 23, 34, 56));
//            mealList.add(new Meal(Meal.Type.LUNCH, dishListLunch));
//
//            // Afternoon meals
//            List<Dish> dishListAfternoonMeals = new ArrayList<>();
//            dishListAfternoonMeals.add(new Dish("Fruit noodles", 0, 187, 23, 34, 56));
//            dishListAfternoonMeals.add(new Dish("Pumpkin puree soup with ginger", 1, 187, 23, 34, 56));
//            dishListAfternoonMeals.add(new Dish("Veal tongue with asparagus in cream sauce", 2, 187, 23, 34, 56));
//            dishListAfternoonMeals.add(new Dish("Rice mix with vegetables", 3, 187, 23, 34, 56));
//            dishListAfternoonMeals.add(new Dish("Wellness Natural Balance", 4, 187, 23, 34, 56));
//            mealList.add(new Meal(Meal.Type.AFTERNOONMEALS, dishListAfternoonMeals));
//
//            // Second afternoon meals
//            List<Dish> dishListSecondAfternoonMeals = new ArrayList<>();
//            dishListSecondAfternoonMeals.add(new Dish("Fruit noodles", 0, 187, 23, 34, 56));
//            dishListSecondAfternoonMeals.add(new Dish("Pumpkin puree soup with ginger", 1, 187, 23, 34, 56));
//            dishListSecondAfternoonMeals.add(new Dish("Veal tongue with asparagus in cream sauce", 2, 187, 23, 34, 56));
//            dishListSecondAfternoonMeals.add(new Dish("Rice mix with vegetables", 3, 187, 23, 34, 56));
//            dishListSecondAfternoonMeals.add(new Dish("Wellness Natural Balance", 4, 187, 23, 34, 56));
//            mealList.add(new Meal(Meal.Type.SECONDAFTERNOONMEALS, dishListSecondAfternoonMeals));
//
//            // Dinner
//            List<Dish> dishListDinner = new ArrayList<>();
//            dishListDinner.clear();
//            dishListDinner.add(new Dish("Fruit noodles", 0, 187, 23, 34, 56));
//            dishListDinner.add(new Dish("Pumpkin puree soup with ginger", 1, 187, 23, 34, 56));
//            dishListDinner.add(new Dish("Veal tongue with asparagus in cream sauce", 2, 187, 23, 34, 56));
//            dishListDinner.add(new Dish("Rice mix with vegetables", 3, 187, 23, 34, 56));
//            dishListDinner.add(new Dish("Wellness Natural Balance", 4, 187, 23, 34, 56));
//            mealList.add(new Meal(Meal.Type.DINNER, dishListDinner));
//            Date date = new Date(2019, 3, i + 1);
//            Date date2 = new Date(2019, 4, i + 1);
//            Date date3 = new Date(2019, 5, i + 1);
//            programDays.put(date, new ProgramDay(date, mealList));
//            programDays.put(date2, new ProgramDay(date2, mealList));
//            programDays.put(date3, new ProgramDay(date3, mealList));
//            Helper.setProgramDaysData(programDays);
//        }
//    }

    private void drawText(Meal meal, Date date, TextView mealText, TextView mealTime) {
        mealText.setText(date.getDate() + " " + months.get(date.getMonth()));
        mealTime.setText("" + meal.getDayTime().getTime());
    }

//    private void updateDayCardsHistory(ViewGroup root, boolean isVisibleAll) {
//        root.removeAllViews();
//        for (int i = programDays.size(); i > (isVisibleAll ? 0 : programDays.size() - 5); i--) {
//            HistoryCard hc = new HistoryCard(getActivity().getBaseContext(), "id_" + i);
//            hc.setHeader("<font color='" + getResources().getColor(R.color.dark_text)
//                    + "'><b>" + "Day #" + i + "</b> " + " of the meal program </font>" + "<b>" + "«Individual menu»" + "</b>");
//            hc.setTitle("Title lorem ipsum sit dolor amet" + i);
//            hc.setCost(i + 2 * 750 / 78);
//            hc.setContent("Comment lorem ipsum sit dolor amet " + i);
//            hc.setDate("29 Jan 2019");
//            root.addView(hc.getView());
//        }
//    }

    private Map<String, Calendar> mapDaysOnCalendar;

    private void drawDaysOnCalendar() {
        Helper.log("drawDaysOnCalendar======================================");
        mapDaysOnCalendar = new HashMap<>();
        for (int i = 0; i < monthDaysHashMap.size(); i++) {
            long timestamp = (Long) monthDaysHashMap.get(i);
            Helper.log("timestamp: " + timestamp);
            Date date = new Date(timestamp);
            try {
                Helper.log("Try get day: " + date);
                int day = date.getDate();
                mapDaysOnCalendar.put(getSchemeCalendar(date.getYear() + 1900, date.getMonth(), day, 0x993ecd8e, "").toString(),
                        getSchemeCalendar(date.getYear() + 1900, date.getMonth(), day, 0x993ecd8e, ""));
            } catch (Exception e) {
                Helper.log("For " + date.getYear() + " " + date.getMonth() + " " + i + " program not found. Error: " + e);
            }
        }
        if (mCalendarView != null) {
            Helper.log("setSchemeDate: ");
            Helper.logObjectToJson(mapDaysOnCalendar);
            mCalendarView.setSchemeDate(mapDaysOnCalendar);
        } else {
            Helper.log("mCalendarView null");
        }
    }


    private TextView mTextMonthDay;
    private TextView mTextYear;
    private TextView mTextLunar;
    private TextView mTextCurrentDay;
    private CalendarView mCalendarView;
    private int mYear, mMonth;
    private List<String> months = Helper.getTranslate(Helper.Translate.months, MainMenu.getInstance());
    private List<String> weekDays = Helper.getTranslate(Helper.Translate.weekDays, MainMenu.getInstance());
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
        HashMap<String, Object> dateHashMap = new HashMap<>();
        dateHashMap.put("date", date.getTime()/1000);
        FireRequest.Companion.callFunction("getDayMeal", dateHashMap, this::onSuccessGotDayMeal, this::onFailGotDayMeal);
    }

    private kotlin.Unit onSuccessGotDayMeal(HashMap<String, Object> data) {
        Helper.log("onSuccessGotDayMeal data: ");
        Helper.logObjectToJson(data);
        ArrayList mealsArrayList = (ArrayList) data.get("meals");
        long timestamp = Long.parseLong(Objects.requireNonNull(data.get("date")).toString())*1000;
//        long timestampMs = timestamp*1000;
        Date date = new Date(timestamp);
        Helper.log("timestampMs: "+ timestamp);
        List<Meal> mealList = new ArrayList<>();
        for (int i = 0; i < mealsArrayList.size(); i++) {
            HashMap<String, Object> mealHashMap = (HashMap<String, Object>) mealsArrayList.get(i);
            int dayTime = (int) mealHashMap.get("dayTime");
            Helper.log("dayTime: " + dayTime);
            ArrayList dishesArrayList = (ArrayList) mealHashMap.get("dishes");
            List<Dish> dishList = new ArrayList<>();
            for (int j = 0; j < dishesArrayList.size(); j++) {
                HashMap<String, Object> dishHashMap = (HashMap<String, Object>) dishesArrayList.get(j);
                int carbo = (int) dishHashMap.get("carbo");
                int fats = (int) dishHashMap.get("fats");
                int proteins = (int) dishHashMap.get("proteins");
                int dishType = (int) dishHashMap.get("dishType");
                String name = (String) dishHashMap.get("name");
                int weight = (int) dishHashMap.get("weight");
                int calories = (int) dishHashMap.get("calories");
                Dish dish = new Dish(name, dishType, calories, proteins, fats, carbo);
                dishList.add(dish);
            }
            Meal meal = new Meal(Meal.Type.Companion.getType(dayTime), dishList);
            mealList.add(meal);
        }
        programDays.put(date, new ProgramDay(date, mealList));
        CalendarMealAdapter calendarMealAdapter = new CalendarMealAdapter(getActivity().getBaseContext());
        calendarMealAdapter.init(programDays.get(date));
        recyclerView.setAdapter(calendarMealAdapter);
        recyclerView.notifyDataSetChanged();
//        drawDaysOnCalendar();
        return Unit.INSTANCE;
    }

    private kotlin.Unit onFailGotDayMeal() {
        Helper.log("onFailGotDayMeal");
        return Unit.INSTANCE;
    }
}
