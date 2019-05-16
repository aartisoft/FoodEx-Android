package com.korlab.foodex.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.korlab.foodex.R;

import java.util.Objects;

public class CardDish extends LinearLayout {
    private TextView type, dishName;

    private void initComponent() {
        type = findViewById(R.id.type);
        dishName = findViewById(R.id.dish_name);
    }

    public CardDish getView() {
        return this;
    }

    public CardDish(Context context, String type, String dishName, int color) {
        super(context);
        Context mContext = context;
        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(service);
        LinearLayout layout = (LinearLayout) Objects.requireNonNull(li).inflate(R.layout.component_calendar_card_dish, this, true);
        initComponent();

        this.setType(type, color);
        this.setDishName(dishName);
    }

    public void setType(String text, int color) {
        type.setText(text + ":");
        type.setTextColor(color);
    }

    public void setDishName(String text) {
        dishName.setText(text);
    }
}