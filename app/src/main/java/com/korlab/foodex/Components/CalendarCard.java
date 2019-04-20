package com.korlab.foodex.Components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.korlab.foodex.R;
import com.korlab.foodex.Technical.ViewAnimation;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.util.Objects;

public class CalendarCard extends LinearLayout {
    private final SharedPreferences sp;
    private String orderId;
    private TextView header;
    private TextView date;
    private TextView content;
    private TextView title;

    private LinearLayout layout, toggle_to_more;
    private Context mContext;
    private LinearLayout bt_toggle_text;
    private TextView toggle_text;
    private boolean show = false;

    private void initComponent() {


        toggle_text.setText("More");

//        bt_toggle_text.setVisibility(View.GONE);


    }




    public CalendarCard getView() {
        return this;
    }

    public CalendarCard(Context context, String id) {
        super(context);

        sp = PreferenceManager.getDefaultSharedPreferences(context);
//        Helper.setSelectedTheme(sp, context);

        this.orderId = id;
        mContext = context;
        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(service);
        layout = (LinearLayout) Objects.requireNonNull(li).inflate(R.layout.component_calendar_card, this, true);

        header = layout.findViewById(R.id.header);
        //title = layout.findViewById(R.id.title);
        date = layout.findViewById(R.id.date);
        content = layout.findViewById(R.id.content);

        bt_toggle_text = findViewById(R.id.bt_toggle_text);
        toggle_to_more = findViewById(R.id.toggle_to_more);
        toggle_text = findViewById(R.id.toggle_text);

        initComponent();
    }

    public void setHeader(String text) {
        header.setVisibility(VISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            header.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
        } else {
            header.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);
        }
    }

    public void setTitle(String text) {
//        title.setVisibility(VISIBLE);
//        title.setText(text);
    }

    public void setCost(int value) {
//        cost.setVisibility(VISIBLE);
//        cost.setText(String.valueOf(value));
//        bonuses.setText(String.valueOf((int) value / 100)); // 1 percent
    }

    public void setDate(String text) {
        date.setVisibility(VISIBLE);
        date.setText(text);
    }

    public void setContent(String text) {
//        content.setVisibility(VISIBLE);
//        bt_toggle_text.setVisibility(VISIBLE);
//        content.setText(text);
    }
}