package com.korlab.foodex;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.korlab.foodex.Technical.Helper;
import com.korlab.foodex.UI.CustomViewPager;
import com.korlab.foodex.UI.NavigationTabStrip;

import java.util.Objects;

public class FragmentMainMenu extends Fragment {
    public static final String ARG_PAGE_MAIN_MENU = "ARG_PAGE_MAIN_MENU";

    private int mPage;
    private NavigationTabStrip navigationTabStrip;

    public static FragmentMainMenu newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_MAIN_MENU, page);
        FragmentMainMenu fragment = new FragmentMainMenu();
        fragment.setArguments(args);
        return fragment;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPage = getArguments().getInt(ARG_PAGE_MAIN_MENU);
        }
    }

    @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = null;
//        Helper.log("==========Main Menu onCreateView: " + mPage);
        switch(mPage) {
            case 1:
                view = inflater.inflate(R.layout.fragment_home, container, false);
//                TextView fragmentHome = view.findViewById(R.id.fragment_home_text);
//                fragmentHome.setText(fragmentHome.getText()+"++");

                CustomViewPager viewPagerHome = view.findViewById(R.id.viewpagerHome);
                MainMenu activity = (MainMenu) getActivity();
                FragmentHomeAdapter fragmentHomeAdapter = new FragmentHomeAdapter(activity.getSupportFragmentManager(), activity.getApplicationContext());

                viewPagerHome.setAdapter(fragmentHomeAdapter);
                viewPagerHome.setPagingEnabled(false);
                fragmentHomeAdapter.notifyDataSetChanged();

                Helper.log("notifyDataSetChanged");

                navigationTabStrip = view.findViewById(R.id.tabs);
                navigationTabStrip.setTitles("Dashboard", "Your Menu", "Pause Menu");
                navigationTabStrip.setTabIndex(0, true);
                navigationTabStrip.setStripColor(getResources().getColor(R.color.colorPrimary));
                navigationTabStrip.setTypeface("fonts/rns_bold.otf");
                navigationTabStrip.setOnTabStripSelectedIndexListener(new NavigationTabStrip.OnTabStripSelectedIndexListener() {
                    @Override
                    public void onStartTabSelected(String title, int index) {
//                        index++;
                        Helper.log("======Top Bar Index: " + index);
                        viewPagerHome.setCurrentItem(index);
                    }

                    @Override
                    public void onEndTabSelected(String title, int index) {

                    }
                });
//                textView = view.findViewById(R.id.text_view);
//                textView.setText("Fragment Home#" + mPage);
                break;
            case 2: view = inflater.inflate(R.layout.fragment_weight, container, false);
                TextView textFragmentWeight = view.findViewById(R.id.textFragmentWeight);
                textFragmentWeight.setText("Fragment Weight#" + mPage);
                break;
            case 3: view = inflater.inflate(R.layout.fragment_manager, container, false);
                TextView textFragmentManager = view.findViewById(R.id.textFragmentManager);
                textFragmentManager.setText("Fragment Manager#" + mPage);
                break;
            case 4: view = inflater.inflate(R.layout.fragment_payment, container, false);
                TextView textFragmentPurchases = view.findViewById(R.id.textFragmentPurchases);
                textFragmentPurchases.setText("Fragment Purchases#" + mPage);
                break;
            case 5: view = inflater.inflate(R.layout.fragment_profile, container, false);
                TextView textFragmentProfile = view.findViewById(R.id.textFragmentProfile);
                textFragmentProfile.setText("Fragment Profile#" + mPage);
                break;
        }

        return view;
    }
}
