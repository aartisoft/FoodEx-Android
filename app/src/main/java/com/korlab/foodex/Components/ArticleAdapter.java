package com.korlab.foodex.Components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.korlab.foodex.Data.ProgramDay;
import com.korlab.foodex.MainMenu;
import com.korlab.foodex.R;
import com.korlab.foodex.Technical.Helper;
import com.korlab.foodex.Technical.ViewAnimation;
import com.korlab.foodex.UI.CardDish;
import com.korlab.foodex.UI.group.GroupRecyclerAdapter;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ArticleAdapter extends GroupRecyclerAdapter<String, Article> {

    private boolean isAnimate = false;

    public ArticleAdapter(Context context) {
        super(context);

    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new ArticleViewHolder(mInflater.inflate(R.layout.component_calendar_card, parent, false));
    }

    @Override
    protected void onBindViewHolder(RecyclerView.ViewHolder holder, Article item, int position) {
        ArticleViewHolder h = (ArticleViewHolder) holder;
        h.proteins.setAnimationDuration(1000);
        h.proteins.setCharacterList(TickerUtils.getDefaultNumberList());
        h.fats.setAnimationDuration(1000);
        h.fats.setCharacterList(TickerUtils.getDefaultNumberList());
        h.carbo.setAnimationDuration(1000);
        h.carbo.setCharacterList(TickerUtils.getDefaultNumberList());

        h.proteins.setText(Integer.toString(0), false);
        h.fats.setText(Integer.toString(0), false);
        h.carbo.setText(Integer.toString(0), false);

        //Helper.logObjectToJson(item);
//        Helper.log("=============Start check==============");
        if(item.getDate() != null) {
//            Helper.log("Pushed item");
            h.calendarCardEmpty.setVisibility(View.GONE);
            h.calendarCard.setVisibility(View.VISIBLE);
            h.header.setText(item.getHeader());
            h.header.setTextColor(item.getColor());

            h.date.setText(item.getTime() + ", " + item.getDate().getDate() + " " + months.get(item.getDate().getMonth()) + " " + (item.getDate().getYear()));
            h.image.setImageDrawable(item.getImage());
            h.colorView.setBackgroundColor(item.getColor());

            h.wrapperCategories.removeAllViews();
            for(int i = 0; i<item.getListDish().size(); i++) {
                h.wrapperCategories.addView(new CardDish(MainMenu.getInstance().getBaseContext(), dishTypes.get(item.getListDish().get(i).getDishType()), item.getListDish().get(i).getName(), item.getColor()));
            }

            h.toggle_text.setText("More");
            h.lyt_expand_text.setVisibility(View.GONE);
            h.bt_toggle_text.setOnClickListener(view -> toggleSectionText(h, true));
            h.calendarCard.setOnClickListener(view -> toggleSectionText(h, false));
        } else {
//            Helper.log("Empty item");
            h.calendarCard.setVisibility(View.GONE);
            h.calendarCardEmpty.setVisibility(View.VISIBLE);
        }



    }

    private class ArticleViewHolder extends RecyclerView.ViewHolder {
        private boolean show = false;
        private TextView header, date;
        private ImageView image;
        private View colorView;
        private LinearLayout calendarCard, calendarCardEmpty, wrapperCategories;
        private TickerView proteins, fats, carbo;

        private LinearLayout bt_toggle_text;
        private View lyt_expand_text;
        private TextView toggle_text;
        boolean clickExpand = false;

        private ArticleViewHolder(View itemView) {
            super(itemView);
            calendarCard = itemView.findViewById(R.id.calendar_card);
            calendarCardEmpty = itemView.findViewById(R.id.calendar_card_empty);
            header = itemView.findViewById(R.id.header);
            date = itemView.findViewById(R.id.date);
            image = itemView.findViewById(R.id.image);
            colorView = itemView.findViewById(R.id.color_view);
            wrapperCategories = itemView.findViewById(R.id.wrapper_categories);
            proteins = itemView.findViewById(R.id.proteins);
            fats = itemView.findViewById(R.id.fats);
            carbo = itemView.findViewById(R.id.carbo);

            bt_toggle_text = itemView.findViewById(R.id.bt_toggle_text);
            lyt_expand_text = itemView.findViewById(R.id.lyt_expand_text);
            toggle_text = itemView.findViewById(R.id.toggle_text);
        }
    }

    String[] dayTimes = {"8:00", "10:00", "12:00", "15:00", "17:00", "19:00"};
    String[] dayTimesName = {"Breakfast", "Brunch", "Lunch", "Afternoon meals", "Second afternoon meals", "Dinner"};
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

    List<String> months = Helper.getTranslate(Helper.Translate.months, MainMenu.getInstance());

    public List<Article> init(ProgramDay programDay) {
        LinkedHashMap<String, List<Article>> map = new LinkedHashMap<>();
        List<Article> list = new ArrayList<>();
        if(programDay != null) {
            for (int i = 0; i < programDay.getMeals().size(); i++) {
                Article article = new Article();
                article.setHeader(dayTimesName[programDay.getMeals().get(i).getDayTime()]);

                int sumCalorie = 0;
                for (int j = 0; j < programDay.getMeals().get(i).getDishList().size(); j++) {
                    sumCalorie += programDay.getMeals().get(i).getDishList().get(j).getCalories();
                }
                article.setCalorie(sumCalorie);
                article.setListDish(programDay.getMeals().get(i).getDishList());

                article.setTime(dayTimes[programDay.getMeals().get(i).getDayTime()]);
                article.setDate(programDay.getDate());
                article.setImage(dayTimesImage[programDay.getMeals().get(i).getDayTime()]);
                article.setColor(dayTimesColor[programDay.getMeals().get(i).getDayTime()]);
                list.add(article);
            }
        } else {
            list.add(new Article());
        }

//        Helper.logObjectToJson(list);
        List<String> titles = new ArrayList<>();
        map.put("", list);
        titles.add("");
        resetGroups(map, titles);
        return list;
    }


    @SuppressLint("SetTextI18n")
    private void toggleSectionText(ArticleViewHolder h, boolean isToggleButton) {
        Helper.log("toggleSectionText isAnimate: " + isAnimate);
        if(!isAnimate) {
            isAnimate = true;
            if (isToggleButton) {
                if (!h.show) {
                    h.show = true;
                    if (!h.clickExpand) {
                        h.clickExpand = true;
                    }
                    h.toggle_text.setText("Less");
                    ViewAnimation.expand(h.lyt_expand_text, () -> {
                        h. proteins.setText(Integer.toString(74), true);
                        h.fats.setText(Integer.toString(36), true);
                        h.carbo.setText(Integer.toString(171), true);
                        isAnimate = false;
                    });
                } else {
                    h.show = false;
                    h.toggle_text.setText("More");
                    ViewAnimation.collapse(h.lyt_expand_text);
                    isAnimate = false;
                }
            } else {
                if (!h.show) {
                    h.show = true;
                    if (!h.clickExpand) {
                        h.clickExpand = true;
                    }
                    h.toggle_text.setText("Less");
                    ViewAnimation.expand(h.lyt_expand_text, () -> {
                        h. proteins.setText(Integer.toString(74), true);
                        h.fats.setText(Integer.toString(36), true);
                        h.carbo.setText(Integer.toString(171), true);
                        isAnimate = false;
                    });
                } else {
                    isAnimate = false;
                }
            }
        }

    }

}
