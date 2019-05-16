package com.korlab.foodex;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class FragmentMainMenuAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 5;
    private Context context;

    public FragmentMainMenuAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentMainMenu.newInstance(position + 1);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
