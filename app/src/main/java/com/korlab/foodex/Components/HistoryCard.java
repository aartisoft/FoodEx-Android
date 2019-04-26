package com.korlab.foodex.Components;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.widget.AppCompatButton;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.korlab.foodex.R;
import com.korlab.foodex.Technical.ViewAnimation;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import kotlin.Unit;

public class HistoryCard extends LinearLayout {
    private final SharedPreferences sp;
    private String orderId;
    private TextView header;
    private TextView date;
    private TextView content;
    private TextView title;
    private TickerView calories, proteins, fats, carbo;

    private LinearLayout layout, toggle_to_more;
    private Context mContext;
    private LinearLayout bt_toggle_text;
    private View lyt_expand_text;
    private TextView toggle_text;
    private boolean show = false, isAnimate = false;

    private void initComponent() {


        toggle_text.setText("More");

        lyt_expand_text.setVisibility(View.GONE);
//        bt_toggle_text.setVisibility(View.GONE);
        bt_toggle_text.setOnClickListener(view -> toggleSectionText(true));
        toggle_to_more.setOnClickListener(view -> toggleSectionText(false));

        calories.setAnimationDuration(2000);
        calories.setCharacterList(TickerUtils.getDefaultNumberList());
        proteins.setAnimationDuration(1000);
        proteins.setCharacterList(TickerUtils.getDefaultNumberList());
        fats.setAnimationDuration(1000);
        fats.setCharacterList(TickerUtils.getDefaultNumberList());
        carbo.setAnimationDuration(1000);
        carbo.setCharacterList(TickerUtils.getDefaultNumberList());


        calories.setText(Integer.toString(0), false);
        proteins.setText(Integer.toString(0), false);
        fats.setText(Integer.toString(0), false);
        carbo.setText(Integer.toString(0), false);
    }

//    private void showDialog() {
//        final Dialog dialog = new Dialog(History.getInstance());
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.component_dialog_feedback);
//        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(R.color.transparent);
//        dialog.setCancelable(true);
//        AnimationSet shake;
//        Animation shakeView;
//        shakeView = AnimationUtils.loadAnimation(History.getInstance(), R.anim.shake_view);
//        shake = new AnimationSet(false);
//        shake.addAnimation(shakeView);
//
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
//        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//
//        RatingBar ratingBar = dialog.findViewById(R.id.ratingbar);
//        EditText textFeedback = dialog.findViewById(R.id.text_feedback);
//        EditText textFeedbackPositive = dialog.findViewById(R.id.text_feedback_positive);
//        EditText textFeedbackNegative = dialog.findViewById(R.id.text_feedback_negative);
//        AppCompatButton btnSend = dialog.findViewById(R.id.bt_send);
//        ratingBar.setStar(rating);
//        ratingBar.setOnRatingChangeListener(
//                ratingCount -> {
//                    rating = (int) ratingCount;
//                    Log.d("UIDebug", "new rating: " + rating);
//                }
//        );
//        btnSend.setOnClickListener((v) -> {
//            showFeedback = true;
//            mRatingBar.setmClickable(false);
//            if (textFeedback.getText().length() > 5) {
//                sendFeedback(rating, textFeedback.getText().toString(), textFeedbackPositive.getText().toString(), textFeedbackNegative.getText().toString());
//                dialog.dismiss();
//            } else {
//                textFeedback.startAnimation(shake);
//            }
//        });
//        dialog.show();
//        dialog.getWindow().setAttributes(lp);
//    }


    boolean clickExpand = false;

    @SuppressLint("SetTextI18n")
    private void toggleSectionText(boolean isToggleButton) {
        if(!isAnimate) {
            isAnimate = true;
            if (isToggleButton) {
                if (!show) {
                    show = true;
                    if (!clickExpand) {
                        clickExpand = true;
                    }
                    toggle_text.setText("Less");
                    ViewAnimation.expand(lyt_expand_text, () -> {
                        calories.setText(Integer.toString(1546), true);
                        proteins.setText(Integer.toString(74), true);
                        fats.setText(Integer.toString(36), true);
                        carbo.setText(Integer.toString(171), true);
                        isAnimate = false;
                    });
                } else {
                    show = false;
                    toggle_text.setText("More");
                    ViewAnimation.collapse(lyt_expand_text);
                    isAnimate = false;
                }
            } else {
                if (!show) {
                    show = true;
                    if (!clickExpand) {
                        clickExpand = true;
                    }
                    toggle_text.setText("Less");
                    ViewAnimation.expand(lyt_expand_text, () -> {
                        calories.setText(Integer.toString(1546), true);
                        proteins.setText(Integer.toString(74), true);
                        fats.setText(Integer.toString(36), true);
                        carbo.setText(Integer.toString(171), true);
                        isAnimate = false;
                    });
                } else {
                    isAnimate = false;
                }
            }
        }
    }

    public HistoryCard getView() {
        return this;
    }

    public HistoryCard(Context context, String id) {
        super(context);

        sp = PreferenceManager.getDefaultSharedPreferences(context);
//        Helper.setSelectedTheme(sp, context);

        this.orderId = id;
        mContext = context;
        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(service);
        layout = (LinearLayout) Objects.requireNonNull(li).inflate(R.layout.component_history_card, this, true);

        header = layout.findViewById(R.id.header);
        //title = layout.findViewById(R.id.title);
        date = layout.findViewById(R.id.date);
        content = layout.findViewById(R.id.content);
        calories = layout.findViewById(R.id.calories);
        proteins = layout.findViewById(R.id.proteins);
        fats = layout.findViewById(R.id.fats);
        carbo = layout.findViewById(R.id.carbo);

        bt_toggle_text = findViewById(R.id.bt_toggle_text);
        toggle_to_more = findViewById(R.id.toggle_to_more);
        lyt_expand_text = findViewById(R.id.lyt_expand_text);
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