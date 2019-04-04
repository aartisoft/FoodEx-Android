package com.korlab.foodex.UI;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.korlab.foodex.R;

import java.util.Objects;

public class Toolbar extends LinearLayout {
    private LinearLayout wrapperTabs;
    private TextView toolbarHeader;
    private ImageView toolbarLeftIcon, toolbarRightIcon;

    private LinearLayout layout;
    private Context mContext;

    private void initComponent() {
        wrapperTabs = findViewById(R.id.wrapper_tabs);
        toolbarHeader = findViewById(R.id.toolbar_header);
        toolbarLeftIcon = findViewById(R.id.toolbar_left);
        toolbarRightIcon = findViewById(R.id.toolbar_right);
    }

    public Toolbar getView() {
        return this;
    }

    public Toolbar(Context context, boolean isVisibleTabs, String text, Drawable drawableLeft, Drawable drawableRight) {
        super(context);
        mContext = context;
        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(service);
        layout = (LinearLayout) Objects.requireNonNull(li).inflate(R.layout.component_toolbar, this, true);
        initComponent();

        this.setVisibleTabs(isVisibleTabs);
        this.setHeader(text);
        this.setLeftIcon(drawableLeft);
        this.setRightIcon(drawableRight);
    }

    public void setVisibleTabs(boolean isVisible) {
        if (isVisible) wrapperTabs.setVisibility(VISIBLE);
        else wrapperTabs.setVisibility(GONE);
    }

    public void setHeader(String text) {
        toolbarHeader.setText(text);
    }

    public void setLeftIcon(Drawable drawableLeft) {
        if (drawableLeft != null) {
            toolbarLeftIcon.setVisibility(VISIBLE);
            toolbarLeftIcon.setImageDrawable(drawableLeft);
        } else toolbarLeftIcon.setVisibility(GONE);
    }

    public void setRightIcon(Drawable drawableRight) {
        if (drawableRight != null) {
            toolbarRightIcon.setVisibility(VISIBLE);
            toolbarRightIcon.setImageDrawable(drawableRight);
        } else toolbarRightIcon.setVisibility(GONE);
    }
}