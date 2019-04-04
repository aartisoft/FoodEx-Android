package com.korlab.foodex;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentHomeAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private Context context;

    public FragmentHomeAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentHome.newInstance(position + 1);
    }
}
