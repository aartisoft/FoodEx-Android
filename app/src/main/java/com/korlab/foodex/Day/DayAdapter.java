package com.korlab.foodex.Day;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.korlab.foodex.Components.CalendarMeal;
import com.korlab.foodex.Data.Meal;
import com.korlab.foodex.Data.ProgramDay;
import com.korlab.foodex.MainMenu;
import com.korlab.foodex.R;
import com.korlab.foodex.Technical.Helper;
import com.korlab.foodex.UI.CardDish;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DayAdapter extends PagerAdapter {
    private List<String> months = Helper.getTranslate(Helper.Translate.months, MainMenu.getInstance());
    String[] dayTimes = {"8:00", "10:00", "12:00", "15:00", "17:00", "19:00"};
    String[] dayTimesName = {"Breakfast", "Brunch", "Lunch", "Afternoon calendarMeals", "Second afternoon calendarMeals", "Dinner"};
    int[] dayTimesColor = {0xFFE58F9F, 0xFFE89754, 0xFFFFC871, 0xFFF46C4D, 0xFFA17DA8, 0xFF608EC6};
    Drawable[] dayTimesImage = {
            MainMenu.getInstance().getResources().getDrawable(R.drawable.diet_card_breakfast),
            MainMenu.getInstance().getResources().getDrawable(R.drawable.diet_card_brunch),
            MainMenu.getInstance().getResources().getDrawable(R.drawable.diet_card_lunch),
            MainMenu.getInstance().getResources().getDrawable(R.drawable.diet_card_afternoon_meals),
            MainMenu.getInstance().getResources().getDrawable(R.drawable.diet_card_second_afternoon_meals),
            MainMenu.getInstance().getResources().getDrawable(R.drawable.diet_card_dinner)
    };
    List<String> dishTypes = Helper.getTranslate(Helper.Translate.dishTypes, MainMenu.getInstance());
    List<CalendarMeal> list;

    private Context mContext;
    private LayoutInflater inflater;

    private ProgramDay programDay;
    private ImageView image;
    private CardView card;
    private TextView header, date;
    private View colorViewLeft,colorViewRight;
    private LinearLayout calendarCard, calendarCardEmpty, wrapperCategories;
    private TickerView proteins, fats, carbo;

    public DayAdapter(LayoutInflater inflater, ProgramDay programDay) {
        this.programDay = programDay;
        this.inflater = inflater;
        list = new ArrayList<>();
        if(programDay != null) {
            for (int i = 0; i < programDay.getMeals().size(); i++) {
                CalendarMeal calendarMeal = new CalendarMeal();
                calendarMeal.setHeader(dayTimesName[programDay.getMeals().get(i).getDayTime().ordinal()]);

                int sumCalorie = 0;
                for (int j = 0; j < programDay.getMeals().get(i).getDishList().size(); j++) {
                    sumCalorie += programDay.getMeals().get(i).getDishList().get(j).getCalories();
                }
                calendarMeal.setCalorie(sumCalorie);
                calendarMeal.setListDish(programDay.getMeals().get(i).getDishList());

                calendarMeal.setTime(dayTimes[programDay.getMeals().get(i).getDayTime().ordinal()]);
                calendarMeal.setDate(programDay.getDate());
                calendarMeal.setImage(dayTimesImage[programDay.getMeals().get(i).getDayTime().ordinal()]);
                calendarMeal.setColor(dayTimesColor[programDay.getMeals().get(i).getDayTime().ordinal()]);
                list.add(calendarMeal);
            }
        } else {
            list.add(new CalendarMeal());
        }
    }

    @Override
    public int getCount() {
        return programDay.getMeals().size();
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.component_meal_card, container, false);
        view.setTag(position);
        container.addView(view);

        CalendarMeal calendarMeal = list.get(position);
        if(calendarMeal != null) {
            Helper.log(position+": ");
//            Helper.logObjectToJson(calendarMeal);
            findView(view);
            header.setText(calendarMeal.getHeader());
            header.setTextColor(calendarMeal.getColor());
            date.setText(programDay.getDate().getDate() + " " + months.get(programDay.getDate().getMonth()) + " " + (programDay.getDate().getYear()));
            image.setImageDrawable(calendarMeal.getImage());
            colorViewLeft.setBackgroundColor(calendarMeal.getColor());
            colorViewRight.setBackgroundColor(calendarMeal.getColor());
            wrapperCategories.removeAllViews();
            for(int i = 0; i<calendarMeal.getListDish().size(); i++) {
                wrapperCategories.addView(new CardDish(MainMenu.getInstance().getBaseContext(), dishTypes.get(calendarMeal.getListDish().get(i).getDishType()), calendarMeal.getListDish().get(i).getName(), calendarMeal.getColor()));
            }
            proteins.setAnimationDuration(1000);
            proteins.setCharacterList(TickerUtils.getDefaultNumberList());
            fats.setAnimationDuration(1000);
            fats.setCharacterList(TickerUtils.getDefaultNumberList());
            carbo.setAnimationDuration(1000);
            carbo.setCharacterList(TickerUtils.getDefaultNumberList());

            proteins.setText(Integer.toString(0), false);
            fats.setText(Integer.toString(0), false);
            carbo.setText(Integer.toString(0), false);

            proteins.setText(Integer.toString(74), true);
            fats.setText(Integer.toString(36), true);
            carbo.setText(Integer.toString(171), true);




//            image = view.findViewById(R.id.image);
//            name = view.findViewById(R.id.promo_name);
//            date = view.findViewById(R.id.promo_date);
//            card = view.findViewById(R.id.card);
//            name.setText(calendarMeal.getDayTime().toString()+""); // прием пищи, 1 блюдо
//            date.setText(calendarMeal.getDayTime().name()+""); // прием пищи, 1 блюдо

//            for(int i=0; i<calendarMeal.getDishList().size(); i++) {
//                Dish dish = calendarMeal.getDishList().get(i);
//                name.setText(disget()); // прием пищи, 1 блюдо
//                date.setText(disget.getDay() + " " + Helper.getTranslate(Helper.Translate.months, MainMenu.getInstance()).get(calendarMeal.getDate().getMonth() - 1));
//            }
//        image.setImageBitmap(calendarMeal.getImage());
//        promoName.setText(position+"");
//        Helper.log(calendarMeal.getImage());
//        Glide.with(MainMenu.getInstance().getBaseContext()).load(calendarMeal.getImage()).into(image);
        }


        return view;
    }

    private void findView(View view) {
        calendarCard = view.findViewById(R.id.calendar_card);
        calendarCardEmpty = view.findViewById(R.id.calendar_card_empty);
        header = view.findViewById(R.id.header);
        date = view.findViewById(R.id.date);
        image = view.findViewById(R.id.image);
        colorViewLeft = view.findViewById(R.id.color_view_left);
        colorViewRight = view.findViewById(R.id.color_view_right);
        wrapperCategories = view.findViewById(R.id.wrapper_categories);
        proteins = view.findViewById(R.id.proteins);
        fats = view.findViewById(R.id.fats);
        carbo = view.findViewById(R.id.carbo);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
