package com.korlab.foodex;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.korlab.foodex.Technical.Helper;

public class FragmentHome extends Fragment {
    public static final String ARG_PAGE_HOME = "ARG_PAGE_HOME";

    private int mPage;

    public static FragmentHome newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_HOME, page);
        FragmentHome fragment = new FragmentHome();
        fragment.setArguments(args);
        return fragment;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPage = getArguments().getInt(ARG_PAGE_HOME);
        }
    }

    @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        TextView textView;
//        Helper.log("==========Fragment Home onCreateView: " + mPage);
        switch(mPage) {
            case 1:
                view = inflater.inflate(R.layout.fragment_home_dashboard, container, false);


                textView = view.findViewById(R.id.text_view);
                Helper.log("Fragment Home Dashboard#" + mPage);
                textView.setText("Fragment Home Dashboard#" + mPage);
                break;
            case 2:
                view = inflater.inflate(R.layout.fragment_home_dashboard, container, false);

                TextView fragmentHomeDashboard = view.findViewById(R.id.fragment_home_dashboard_text);
                fragmentHomeDashboard.setText(fragmentHomeDashboard.getText()+"++");

                textView = view.findViewById(R.id.text_view);
                Helper.log("Fragment Home Your Menu#" + mPage);
                textView.setText("Fragment Home Your Menu#" + mPage);
                break;
            case 3: view = inflater.inflate(R.layout.fragment_home_dashboard, container, false);
                textView = view.findViewById(R.id.text_view);
                Helper.log("Fragment Home Pause Menu#" + mPage);
                textView.setText("Fragment Home Pause Menu#" + mPage);
                break;
        }
        return view;
    }
}
