package com.korlab.foodex.Components;

import android.graphics.drawable.Drawable;

import com.korlab.foodex.Data.Dish;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@SuppressWarnings("all")
public class CalendarMeal implements Serializable {
    private int id;
    private String header;
    private String time;
    private Date date;
    private int calorie;
    private Drawable image;
    private int color;
    private int type;
    private List<Dish> dishList;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getHeader() { return header; }
    public void setHeader(String header) {
        this.header = header;
    }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public int getCalorie() {
        return calorie;
    }
    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    public Drawable getImage() { return image; }
    public void setImage(Drawable image) { this.image = image; }


    public int getColor() { return color; }
    public void setColor(int color) { this.color = color; }

    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

    public void setListDish(List<Dish> dishList) {
        this.dishList = dishList;
    }
    public List<Dish> getListDish() {
        return dishList;
    }
}
