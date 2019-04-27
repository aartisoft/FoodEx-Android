package discretescrollview.Example;

import android.content.Context;
import android.content.SharedPreferences;

import com.korlab.foodex.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by yarolegovich on 07.03.2017.
 */

public class Shop {

    private static final String STORAGE = "shop";

    public static Shop get() {
        return new Shop();
    }

//    private SharedPreferences storage;

    private Shop() {
//        storage = App.getInstance().getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
    }

    public List<Item> getData() {
        return Arrays.asList(
                new Item(1, "Monday", "22 Apr, 2019", R.drawable.birthday),
                new Item(2, "Tuesday", "23 Apr, 2019", R.drawable.birthday),
                new Item(3, "Wednesday", "24 Apr, 2019", R.drawable.birthday),
                new Item(4, "Thursday", "25 Apr, 2019", R.drawable.birthday),
                new Item(5, "Friday", "26 Apr, 2019", R.drawable.birthday),
                new Item(6, "Saturday", "27 Apr, 2019", R.drawable.birthday),
                new Item(7, "Sunday", "28 Apr, 2019", R.drawable.birthday));
    }

    public boolean isRated(int itemId) {
        return true;
//        return storage.getBoolean(String.valueOf(itemId), false);
    }

    public void setRated(int itemId, boolean isRated) {
//        storage.edit().putBoolean(String.valueOf(itemId), isRated).apply();
    }
}
