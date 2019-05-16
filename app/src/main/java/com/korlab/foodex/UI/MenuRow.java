package com.korlab.foodex.UI;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.korlab.foodex.R;

import java.util.Objects;

public class MenuRow extends LinearLayout {
    private LinearLayout root;
    private TextView header;
    private ImageView icon;

    private LinearLayout layout;
    private Context mContext;

    private void initComponent() {
        root = findViewById(R.id.root);
        header = findViewById(R.id.header);
        icon = findViewById(R.id.icon);
    }

    public MenuRow getView() {
        return this;
    }

    public MenuRow(Context context, String header, Drawable icon) {
        super(context);
        mContext = context;
        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(service);
        layout = (LinearLayout) Objects.requireNonNull(li).inflate(R.layout.component_row_menu, this, true);
        initComponent();

        this.setHeader(header);
        this.setIcon(icon);
    }

    public void setHeader(String text) {
        header.setText(text);
    }

    public void setIcon(Drawable drawableLeft) {
        if(drawableLeft != null) {
            icon.setVisibility(VISIBLE);
            icon.setImageDrawable(drawableLeft);
        }
        else icon.setVisibility(GONE);
    }

    public View getRoot() {
        return root;
    }
}