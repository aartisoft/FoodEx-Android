package com.korlab.foodex.Promo;

/**
 * Created by yarolegovich on 07.03.2017.
 */

public class PromoItem {

    private final int id;
    private final String name;
    private final String price;
    private final int image;

    public PromoItem(int id, String name, String price, int image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getImage() {
        return image;
    }
}
