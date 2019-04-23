package com.korlab.foodex;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.korlab.foodex.Technical.Helper;
import com.korlab.foodex.UI.CustomViewPager;
import com.korlab.foodex.UI.MenuRow;
import com.korlab.foodex.UI.NavigationTabStrip;
import com.korlab.foodex.UI.Toolbar;

import java.util.ArrayList;
import java.util.List;

import spencerstudios.com.bungeelib.Bungee;


public class FragmentMainMenu extends Fragment {
    public static final String ARG_PAGE_MAIN_MENU = "ARG_PAGE_MAIN_MENU";

    private int mPage;
    private NavigationTabStrip navigationTabStrip;

    private List<MenuRow> menuRows;
    private MainMenu activity;
    private LinearLayout toolbarRight;
    private boolean isClickDelay = false;

    public static FragmentMainMenu newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_MAIN_MENU, page);
        FragmentMainMenu fragment = new FragmentMainMenu();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPage = getArguments().getInt(ARG_PAGE_MAIN_MENU);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        LinearLayout toolbarContainer;
        activity = (MainMenu) getActivity();
        switch (mPage) {
            case 1:
                view = inflater.inflate(R.layout.fragment_home, container, false);
                CustomViewPager viewPagerHome = view.findViewById(R.id.viewpager_home);

                FragmentHomeAdapter fragmentHomeAdapter = new FragmentHomeAdapter(activity.getSupportFragmentManager(), activity.getApplicationContext());
                viewPagerHome.setAdapter(fragmentHomeAdapter);
                viewPagerHome.setPagingEnabled(false);
                viewPagerHome.setOffscreenPageLimit(10);
                toolbarContainer = view.findViewById(R.id.toolbar_container);
                toolbarContainer.addView(new Toolbar(activity, true, "Home", activity.getDrawable(R.drawable.toolbar_move), activity.getDrawable(R.drawable.toolbar_pause)).getView());

                navigationTabStrip = view.findViewById(R.id.tabs);
                navigationTabStrip.setTitles("Dashboard", "History", "Diet calendar");
                navigationTabStrip.setTabIndex(0, true);
                navigationTabStrip.setStripColor(getResources().getColor(R.color.colorPrimary));
                navigationTabStrip.setTypeface("fonts/rns_bold.otf");
                navigationTabStrip.setOnTabStripSelectedIndexListener(new NavigationTabStrip.OnTabStripSelectedIndexListener() {
                    @Override
                    public void onStartTabSelected(String title, int index) {
                        Helper.log("======Top Bar Index: " + index);
                        viewPagerHome.setCurrentItem(index);
                    }

                    @Override
                    public void onEndTabSelected(String title, int index) {

                    }
                });

                break;
            case 2:
                view = inflater.inflate(R.layout.fragment_weight, container, false);
                TextView textFragmentWeight = view.findViewById(R.id.textFragmentWeight);
                textFragmentWeight.setText("Fragment Weight#" + mPage);
                toolbarContainer = view.findViewById(R.id.toolbar_container);
                toolbarContainer.addView(new Toolbar(activity, false, "Weight Control", null, activity.getDrawable(R.drawable.toolbar_share)).getView());
//                view.findViewById(R.id.toolbar_right).setOnClickListener((v) -> {
//                    Helper.log("click+");
//                });
                break;
            case 3:
                view = inflater.inflate(R.layout.fragment_manager, container, false);
                TextView textFragmentManager = view.findViewById(R.id.textFragmentManager);
                textFragmentManager.setText("Fragment Manager#" + mPage);
                toolbarContainer = view.findViewById(R.id.toolbar_container);
                toolbarContainer.addView(new Toolbar(activity, false, "Personal Manager", null, activity.getDrawable(R.drawable.toolbar_filter)).getView());
                break;
            case 4:
                view = inflater.inflate(R.layout.fragment_payment, container, false);
                TextView textFragmentPurchases = view.findViewById(R.id.textFragmentPurchases);
                textFragmentPurchases.setText("Fragment Purchases#" + mPage);
                toolbarContainer = view.findViewById(R.id.toolbar_container);
                toolbarContainer.addView(new Toolbar(activity, false, "Your Purchases", null, activity.getDrawable(R.drawable.toolbar_filter)).getView());
                break;
            case 5:
                view = inflater.inflate(R.layout.fragment_profile, container, false);
                toolbarContainer = view.findViewById(R.id.toolbar_container);
                toolbarContainer.addView(new Toolbar(activity, false, "Profile", null, activity.getDrawable(R.drawable.toolbar_settings)).getView());
                toolbarRight = view.findViewById(R.id.toolbar_right);
                Helper.log("Settings setOnClickListener");

                toolbarRight.setOnClickListener(v -> {
                    Helper.log("Settings click");
                    startActivity(new Intent(activity, ProfileSettings.class));
                });

                LinearLayout menuContainer = view.findViewById(R.id.menu_container);
                String[] menuHeader = {"Feedback",
                        "Friends",
                        "Discount",
                        "Blog",
                        "Allergies and Antipathies",
                        "Exit"
                };
                Drawable[] menuIcon = {
                        activity.getDrawable(R.drawable.profile_feedback),
                        activity.getDrawable(R.drawable.profile_friends),
                        activity.getDrawable(R.drawable.profile_discount),
                        activity.getDrawable(R.drawable.profile_blog),
                        activity.getDrawable(R.drawable.profile_allergies),
                        activity.getDrawable(R.drawable.profile_exit)
                };
                menuRows = new ArrayList<>();
                for (int i = 0; i < menuHeader.length; i++) {
                    MenuRow menuRow = new MenuRow(activity, menuHeader[i], menuIcon[i]).getView();
                    menuRow.getRoot().setTag(i + 1);
                    menuRow.getRoot().setOnClickListener(this::processClickMenu);
                    menuRows.add(menuRow.getView());
                    menuContainer.addView(menuRow.getView());
                }
                LinearLayout buttonProfileEdit = view.findViewById(R.id.button_profile_edit);
                buttonProfileEdit.setTag(0);
                buttonProfileEdit.setOnClickListener(this::processClickMenu);
                break;
        }

        return view;
    }

    private void processClickMenu(View v) {
        if(!isClickDelay) {
            isClickDelay = true;
            Helper.log("processClickMenu: " + v.getClass().getSimpleName());
            switch (v.getTag().toString()) {
                case "0":
                    String json = "{\"birthdayDay\":9,\"birthdayMonth\":11,\"birthdayYear\":1998,\"deliveryType\":1,\"email\":\"xom9ik.code@gmail.com\",\"firstName\":\"Maxim\",\"gender\":false,\"growth\":170,\"growthMetrics\":false,\"lastName\":\"Romanyuta\",\"middleName\":\"Olegovich\",\"phone\":\"+380959483523\",\"weekdaysAddress\":{\"apartment\":\"2/F\",\"house\":\"77\",\"street\":\"Universitetksa\"},\"weekendsAddress\":{\"apartment\":\"1\",\"house\":\"30\",\"street\":\"Aprel`ska\"},\"weight\":60,\"weightMetrics\":false}";
                    startActivity(new Intent(activity, ProfileEdit.class).putExtra("user", json));
                    Bungee.slideLeft(activity);
                    Helper.log("click: " + "profile_edit");
                    break;
                case "1":
                    Helper.log("click: " + "profile_feedback");
                    break;
                case "2":
                    Helper.log("click: " + "profile_friends");
                    break;
                case "3":
                    Helper.log("click: " + "profile_discount");
                    break;
                case "4":
                    Helper.log("click: " + "profile_blog");
                    break;
                case "5":
                    Helper.log("click: " + "profile_allergies");
                    break;
                case "6":
                    Helper.log("click: " + "profile_exit");
                    break;

            }
            new Handler().postDelayed(() -> isClickDelay = false,1000);
        }
    }
}
