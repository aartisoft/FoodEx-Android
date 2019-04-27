package com.korlab.foodex;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.korlab.foodex.Data.Program;
import com.korlab.foodex.Program.ProgramAdapter;
import com.korlab.foodex.Promo.PromoItem;
import com.korlab.foodex.Promo.Promo;
import com.korlab.foodex.Promo.PromoAdapter;
import com.korlab.foodex.Technical.Helper;
import com.korlab.foodex.UI.CustomViewPager;
import com.korlab.foodex.UI.MenuRow;
import com.korlab.foodex.UI.NavigationTabStrip;
import com.korlab.foodex.UI.Toolbar;

import java.util.ArrayList;
import java.util.List;

import discretescrollview.DSVOrientation;
import discretescrollview.DiscreteScrollView;
import discretescrollview.transform.ScaleTransformer;
import spencerstudios.com.bungeelib.Bungee;


public class FragmentMainMenu extends Fragment {
    public static final String ARG_PAGE_MAIN_MENU = "ARG_PAGE_MAIN_MENU";

    private int mPage;
    private NavigationTabStrip navigationTabStrip;

    private List<MenuRow> menuRows;
    private MainMenu activity;
    private ImageView toolbarRight;
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
//                toolbarContainer.addView(new Toolbar(activity, true, "Home", activity.getDrawable(R.drawable.toolbar_move), activity.getDrawable(R.drawable.toolbar_pause)).getView());
                toolbarContainer.addView(new Toolbar(activity, true, "Home", null, null));

                navigationTabStrip = view.findViewById(R.id.tabs);
                navigationTabStrip.setTitles("Dashboard", "History", "Calendar");
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
                break;
            case 3:
                view = inflater.inflate(R.layout.fragment_manager, container, false);
                TextView textFragmentManager = view.findViewById(R.id.textFragmentManager);
                textFragmentManager.setText("Fragment Manager#" + mPage);
                toolbarContainer = view.findViewById(R.id.toolbar_container);
                toolbarContainer.addView(new Toolbar(activity, false, "Personal Manager", null, activity.getDrawable(R.drawable.toolbar_filter)).getView());
                break;
            case 4:
                view = inflater.inflate(R.layout.fragment_purchaces, container, false);
                toolbarContainer = view.findViewById(R.id.toolbar_container);
                toolbarContainer.addView(new Toolbar(activity, false, "Purchases", null, activity.getDrawable(R.drawable.toolbar_basket)).getView());
                List<PromoItem> data;
                Promo promo;
                TextView currentItemName;
                TextView currentItemPrice;
                DiscreteScrollView itemPicker;
//                currentItemName = view.findViewById(R.id.item_name);
//                currentItemPrice = view.findViewById(R.id.item_price);
                promo = Promo.get();
                data = promo.getData();
                itemPicker = view.findViewById(R.id.item_picker);
                PromoAdapter promoAdapter = new PromoAdapter(data, view, itemPicker);
                itemPicker.setAdapter(promoAdapter);
                itemPicker.addOnItemChangedListener((viewHolder, adapterPosition) -> {
                    promoAdapter.setCurrent(adapterPosition);
//                    currentItemName.setText(data.get(adapterPosition).getName());
//                    currentItemPrice.setText(data.get(adapterPosition).getPrice());
                });
                itemPicker.setOrientation(DSVOrientation.HORIZONTAL);
                itemPicker.setItemTransitionTimeMillis(150);
                itemPicker.setItemTransformer(new ScaleTransformer.Builder().setMinScale(0.8f).build());

                ListView listProgram = view.findViewById(R.id.list_program);

                List<Program> programs = new ArrayList<>();

                programs.add(new Program("Express Program of Loosing Weight",
                        "To get the result in the shortest term.",
                        ((BitmapDrawable) activity.getDrawable(R.drawable.program_1)).getBitmap()));
                programs.add(new Program("Smooth Loosing Weight",
                        "For comfortable loosing weight",
                        ((BitmapDrawable) activity.getDrawable(R.drawable.program_2)).getBitmap()));
                programs.add(new Program("Sports Menu",
                        "For those with active life style and intensive gym trainings",
                        ((BitmapDrawable) activity.getDrawable(R.drawable.program_3)).getBitmap()));
                programs.add(new Program("Sport-PRO",
                        "For those with active life style, hard trainings and sports",
                        ((BitmapDrawable) activity.getDrawable(R.drawable.program_4)).getBitmap()));
                programs.add(new Program("Balanced Eating",
                        "To maintain good physical form and stick to healthy eating",
                        ((BitmapDrawable) activity.getDrawable(R.drawable.program_5)).getBitmap()));
                programs.add(new Program("Meat-free Menu",
                        "The ration is saturated with vegetable food including seafood",
                        ((BitmapDrawable) activity.getDrawable(R.drawable.program_6)).getBitmap()));
                programs.add(new Program("Vegetarian Menu",
                        "Balanced eating for vegetarians",
                        ((BitmapDrawable) activity.getDrawable(R.drawable.program_7)).getBitmap()));
                programs.add(new Program("Individual Menu",
                        "Developed specially for YOU by doctor-dietician and the Chef",
                        ((BitmapDrawable) activity.getDrawable(R.drawable.program_8)).getBitmap()));
                programs.add(new Program("Smart Lunch",
                        "Healthy food in your office",
                        ((BitmapDrawable) activity.getDrawable(R.drawable.program_9)).getBitmap()));
                programs.add(new Program("2 weeks with Discipline",
                        "Impressive loose of weight during 14 days (right eating + trainings)",
                        ((BitmapDrawable) activity.getDrawable(R.drawable.program_10)).getBitmap()));
                programs.add(new Program("Diet No 5",
                        "Well-balanced program according to the diet “Table No 5”",
                        ((BitmapDrawable) activity.getDrawable(R.drawable.program_11)).getBitmap()));
                programs.add(new Program("Diabetes Mellitus",
                        "Well-balanced program according to the diet “Table No 9”",
                        ((BitmapDrawable) activity.getDrawable(R.drawable.program_12)).getBitmap()));
                programs.add(new Program("Gluten-free Menu",
                        "For those with medical prescriptions or personal desire to stick to gluten-free diet",
                        ((BitmapDrawable) activity.getDrawable(R.drawable.program_13)).getBitmap()));
                programs.add(new Program("Lactose Free",
                        "Lactose-free ration for people with a lactose intolerance",
                        ((BitmapDrawable) activity.getDrawable(R.drawable.program_14)).getBitmap()));
                programs.add(new Program("For pregnant women and nursing mothers",
                        "Well-balanced eating during pregnancy and breast feeding",
                        ((BitmapDrawable) activity.getDrawable(R.drawable.program_15)).getBitmap()));
                programs.add(new Program("Kids’ Menu “Smart Kids”",
                        "For business parents who care about full-scale healthy eating of a child",
                        ((BitmapDrawable) activity.getDrawable(R.drawable.program_16)).getBitmap()));
                programs.add(new Program("Gift certificate",
                        "Gift certificate FoodEx for your friends",
                        ((BitmapDrawable) activity.getDrawable(R.drawable.program_17)).getBitmap()));
                programs.add(new Program("theBODYology",
                        "Online weight loss program for women in the menu on the dietitian + video training",
                        ((BitmapDrawable) activity.getDrawable(R.drawable.program_18)).getBitmap()));



                ProgramAdapter adapter = new ProgramAdapter(programs, getActivity().getBaseContext());

                listProgram.setAdapter(adapter);
                listProgram.setDivider(null);
                listProgram.setDividerHeight(0);
                listProgram.setOnTouchListener((v, event) -> {
//                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                });
                setListViewHeightBasedOnChildren(listProgram);





                break;
            case 5:
                view = inflater.inflate(R.layout.fragment_profile, container, false);
                toolbarContainer = view.findViewById(R.id.toolbar_container);
                toolbarContainer.addView(new Toolbar(activity, false, "Profile", null, activity.getDrawable(R.drawable.toolbar_settings)).getView());
                toolbarRight = view.findViewById(R.id.toolbar_right_icon);
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
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
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
