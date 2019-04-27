package com.korlab.foodex.Promo;

import com.korlab.foodex.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by yarolegovich on 07.03.2017.
 */

public class Promo {

    private static final String STORAGE = "shop";

    public static Promo get() {
        return new Promo();
    }

//    private SharedPreferences storage;

    private Promo() {
//        storage = App.getInstance().getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
    }

    public List<PromoItem> getData() {
        return Arrays.asList(
                new PromoItem(1, "Monday", "22 Apr, 2019", R.drawable.promo_1),
                new PromoItem(2, "Tuesday", "23 Apr, 2019", R.drawable.promo_2),
                new PromoItem(3, "Wednesday", "24 Apr, 2019", R.drawable.promo_3),
                new PromoItem(4, "Thursday", "25 Apr, 2019", R.drawable.promo_4),
                new PromoItem(5, "Friday", "26 Apr, 2019", R.drawable.promo_5),
                new PromoItem(6, "Saturday", "27 Apr, 2019", R.drawable.promo_6));
    }

    public boolean isRated(int itemId) {
        return true;
//        return storage.getBoolean(String.valueOf(itemId), false);
    }

    public void setRated(int itemId, boolean isRated) {
//        storage.edit().putBoolean(String.valueOf(itemId), isRated).apply();
    }
}
