package com.korlab.foodex;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.korlab.foodex.Data.Program;
import com.korlab.foodex.Data.Promo;
import com.korlab.foodex.Program.ProgramAdapter;
import com.korlab.foodex.Promo.PromoAdapter;
import com.korlab.foodex.Technical.Helper;
import com.korlab.foodex.UI.CustomPagerTransformer;
import com.korlab.foodex.UI.CustomViewPager;
import com.korlab.foodex.UI.MenuRow;
import com.korlab.foodex.UI.NavigationTabStrip;
import com.korlab.foodex.UI.Toolbar;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
        activity = MainMenu.getInstance();
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
                    @Override public void onStartTabSelected(String title, int index) { viewPagerHome.setCurrentItem(index); }
                    @Override public void onEndTabSelected(String title, int index) { }
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

                ViewPager viewPager;
                viewPager = view.findViewById(R.id.promo_view_pager);
                DotsIndicator dotsIndicator = view.findViewById(R.id.dots_indicator);
                List<Promo> pagerArr = new ArrayList<>();

                pagerArr.add(new Promo(1, "+100 UAH to your account for every kilogram dropped!", new Date(2019, 3, 14), "https://media.foodexhub.com.ua/images/Promo/2.jpg"));
                pagerArr.add(new Promo(2, "Loyalty Club FoodEx", new Date(2019, 3, 14), "https://foodexhub.com.ua/images/oldpromo/14877871_325929737775565_251872099_n.jpg"));
                pagerArr.add(new Promo(3, "Order delivery of 2 programs to one address - get a discount!", new Date(2019, 3, 14), "https://media.foodexhub.com.ua/promopage/summer_ration.jpg"));
                pagerArr.add(new Promo(4, "Invite Friends - get +150 UAH from each order!", new Date(2019, 3, 14), "https://media.foodexhub.com.ua/images/Promo/petrushka.jpg"));
                pagerArr.add(new Promo(5, "Order 3 salads +1 salad for free!", new Date(2019, 3, 14), "https://media.foodexhub.com.ua/promopage/stakan-v-mashine.jpg"));
                pagerArr.add(new Promo(6, "Become a FoodEx Partner", new Date(2019, 3, 14), "https://media.foodexhub.com.ua/images/Promo/sale.jpg"));

                viewPager.setAdapter(new PromoAdapter(LayoutInflater.from(getActivity()), pagerArr));
                viewPager.setPageTransformer(false, new CustomPagerTransformer(activity, 180));
                dotsIndicator.setViewPager(viewPager);

//                viewPager.setOffscreenPageLimit(10);
//                Slide
//                slidePromoPage(viewPager, pagerArr.size());


                ListView listProgram = view.findViewById(R.id.list_program);
                List<Program> programs = new ArrayList<>();
                programs.add(new Program("Express Program of Loosing Weight",
                        "To get the result in the shortest term.",
                        "https://media.foodexhub.com.ua/images/smi/ekspress1.jpg"));
                programs.add(new Program("Smooth Loosing Weight",
                        "For comfortable loosing weight",
                        "https://media.foodexhub.com.ua/images/smi/plavnoe1.jpg"));
                programs.add(new Program("Sports Menu",
                        "For those with active life style and intensive gym trainings",
                        "https://media.foodexhub.com.ua/images/smi/sport1.jpg"));
                programs.add(new Program("Sport-PRO",
                        "For those with active life style, hard trainings and sports",
                        "https://media.foodexhub.com.ua/admin/sport-pro-1.jpg"));
                programs.add(new Program("Balanced Eating",
                        "To maintain good physical form and stick to healthy eating",
                        "https://media.foodexhub.com.ua/images/smi/balans1.jpg"));
                programs.add(new Program("Meat-free Menu",
                        "The ration is saturated with vegetable food including seafood",
                        "https://media.foodexhub.com.ua/images/smi/bezmyasa1.jpg"));
                programs.add(new Program("Vegetarian Menu",
                        "Balanced eating for vegetarians",
                        "https://media.foodexhub.com.ua/images/smi/vegan1.jpg"));
                programs.add(new Program("Individual Menu",
                        "Developed specially for YOU by doctor-dietician and the Chef",
                        "https://media.foodexhub.com.ua/images/smi/ind1.jpg"));
                programs.add(new Program("Smart Lunch",
                        "Healthy food in your office",
                        "https://media.foodexhub.com.ua/images/smi/smart1.jpg"));
                programs.add(new Program("2 weeks with Discipline",
                        "Impressive loose of weight during 14 days (right eating + trainings)",
                        "https://media.foodexhub.com.ua/programList/sofia1.png"));
                programs.add(new Program("Diet No 5",
                        "Well-balanced program according to the diet “Table No 5”",
                        "https://media.foodexhub.com.ua/admin/%D0%BF%D1%80%D0%BE%D0%B3%D1%80%D0%B0%D0%BC%D0%BC%D0%B0-%D0%BC%D0%B0%D1%81%D1%81%D0%B0%D0%B6%D0%B8%D1%81%D1%82%D1%8B.jpg"));
                programs.add(new Program("Diabetes Mellitus",
                        "Well-balanced program according to the diet “Table No 9”",
                        "https://media.foodexhub.com.ua/images/smi/stol51.jpg"));
                programs.add(new Program("Gluten-free Menu",
                        "For those with medical prescriptions or personal desire to stick to gluten-free diet",
                        "https://media.foodexhub.com.ua/images/smi/diabet1.jpg"));
                programs.add(new Program("Lactose Free",
                        "Lactose-free ration for people with a lactose intolerance",
                        "https://media.foodexhub.com.ua/images/smi/bezgluten11.jpg"));
                programs.add(new Program("For pregnant women and nursing mothers",
                        "Well-balanced eating during pregnancy and breast feeding",
                        "https://media.foodexhub.com.ua/programList/lacto1.jpg"));
                programs.add(new Program("Kids’ Menu “Smart Kids”",
                        "For business parents who care about full-scale healthy eating of a child",
                        "https://media.foodexhub.com.ua/images/smi/berem1.jpg"));
                programs.add(new Program("Gift certificate",
                        "Gift certificate FoodEx for your friends",
                        "https://media.foodexhub.com.ua/images/smi/detskoe1.jpg"));
                programs.add(new Program("theBODYology",
                        "Online weight loss program for women in the menu on the dietitian + video training",
                        "https://media.foodexhub.com.ua/images/smi/thebodyology1.jpg"));


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
                        "Logout"
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

    private void slidePromoPage(ViewPager viewPager, int size) {
        new Handler().postDelayed(() -> {
            viewPager.setCurrentItem(size>viewPager.getCurrentItem()+2 ? viewPager.getCurrentItem()+1 : 0,true);
            slidePromoPage(viewPager, size);
        }, 5000);
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
        if (!isClickDelay) {
            isClickDelay = true;
            Helper.log("processClickMenu: " + v.getClass().getSimpleName());
            switch (v.getTag().toString()) {
                case "0":
                    Helper.setUserData(MainMenu.getInstance().user);
                    startActivity(new Intent(activity, ProfileEdit.class));
                    Bungee.slideLeft(activity);
                    Helper.log("click: " + "profile_edit");
                    break;
                case "1":
                    Helper.log("click: " + "profile_feedback");
                    Helper.showDialog(activity, LayoutInflater.from(activity.getBaseContext()).inflate(R.layout.dialog_feedback, null), this::onPositive, this::onNegative);
                    break;
                case "2":
                    Helper.log("click: " + "profile_friends");
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = "I invite you to join FoodEx!\n\n" +
                            "Join FoodEx Extra and you will be as happy and full of energy as I am." +
                            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." +
                            "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat." +
                            "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. \n\n" +
                            "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n\n" +
                            "Link: https://foodexhub.com.ua";
//                    sharingIntent.putExtra(Intent.EXTRA_TITLE, "I invite you to join FoodEx!");
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
//                    sharingIntent.putExtra(Intent.EXTRA_STREAM, "https://foodexhub.com.ua/dist/images/logo_foodex.png");
//                    sharingIntent.setType("image/*");
                    startActivity(Intent.createChooser(sharingIntent, "Share via"));
                    break;
                case "3":
                    Helper.log("click: " + "profile_discount");
                    break;
                case "4":
                    Helper.log("click: " + "profile_blog");
                    break;
                case "5":
                    Helper.log("click: " + "profile_allergies");
                    Helper.showDialog(activity, LayoutInflater.from(activity.getBaseContext()).inflate(R.layout.dialog_allergies, null), this::onPositive, this::onNegative);
                    break;
                case "6":
                    Helper.log("click: " + "profile_exit");
                    activity.finish();
                    break;

            }
            new Handler().postDelayed(() -> isClickDelay = false, 1000);
        }
    }

    private void onPositive(Object o) {
        Helper.log("onPositive");
    }

    private void onNegative(Object o) {
        Helper.log("onNegative");
    }
}
