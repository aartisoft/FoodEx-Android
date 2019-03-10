package com.korlab.foodex.Technical;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

public class Singleton extends AppCompatActivity {
    private static Activity mInstance;

    public static void setInstance(Activity activity) {
        Helper.log("Set instance for " + activity.getClass().getSimpleName());
        if (mInstance == null) {
            mInstance = activity;
        }
    }

    public static Activity getInstance() {
        Helper.log("Get instance for " + mInstance.getClass().getSimpleName());
        return mInstance;
    }
}