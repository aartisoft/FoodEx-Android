package com.korlab.foodex.UI;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.korlab.foodex.Data.Message;
import com.korlab.foodex.R;

import java.util.Objects;

public class MessageCard extends LinearLayout {
    private TextView text;
    private TextView date;
    private LinearLayout container;
    private CardView backgroundMessage;

    private LinearLayout layout;
    private Context mContext;

    private void initComponent() {
        text = layout.findViewById(R.id.text);
        date = layout.findViewById(R.id.date);
        container = layout.findViewById(R.id.container);
        backgroundMessage = layout.findViewById(R.id.background_message);
    }

    public MessageCard getView() {
        return this;
    }

    public MessageCard(Context context) {
        super(context);
        mContext = context;
        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(service);
        layout = (LinearLayout) Objects.requireNonNull(li).inflate(R.layout.component_message_card, this, true);
        initComponent();
    }

    public void setText(String val) {
        text.setVisibility(VISIBLE);
        text.setText(val);
    }

    public void setDate(String val) {
        date.setVisibility(VISIBLE);
        date.setText(val);
    }

    public void setSender(Message.Sender sender) {
        switch (sender) {
            case BOT:
                setPosition(container, Gravity.LEFT);
                backgroundMessage.setCardBackgroundColor(getResources().getColor(R.color.white));
                text.setTextColor(getResources().getColor(R.color.dark_text));
                break;
            case MANAGER:
                setPosition(container, Gravity.LEFT);
                backgroundMessage.setCardBackgroundColor(getResources().getColor(R.color.white));
                text.setTextColor(getResources().getColor(R.color.dark_text));
                break;
            case CLIENT:
                setPosition(container, Gravity.RIGHT);
                backgroundMessage.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                text.setTextColor(getResources().getColor(R.color.white));
                break;
        }
    }
    private void setPosition(LinearLayout view, int position) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = position;
        view.setLayoutParams(params);
    }
}