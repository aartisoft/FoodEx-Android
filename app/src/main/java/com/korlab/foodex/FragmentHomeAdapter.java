package com.korlab.foodex;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.korlab.foodex.Technical.Helper;

public class FragmentHomeAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "Tab1", "Tab2", "Tab3"};
    private Context context;

    public FragmentHomeAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override public int getCount() {
        return PAGE_COUNT;
    }

    @Override public Fragment getItem(int position) {
        position+=1;
//        Helper.log("==========FragmentHome.newInstance: " + position);
        return FragmentHome.newInstance(position);
    }

    @Override public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
