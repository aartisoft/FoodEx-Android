package com.korlab.foodex;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.korlab.foodex.Data.Message;
import com.korlab.foodex.Data.User;
import com.korlab.foodex.Technical.Helper;
import com.korlab.foodex.UI.MaterialButton;
import com.korlab.foodex.UI.MessageCard;
import com.korlab.foodex.UI.Toolbar;

import java.sql.Date;
import java.util.List;

public class BotManagerChat extends AppCompatActivity {
    private User user;

    private static BotManagerChat instance;

    public static BotManagerChat getInstance() {
        return instance;
    }

    private ImageView toolbarLeft;
    private LinearLayout toolbarContainer, backgroundChat;
    private CardView botAttachMenu;
    private MaterialRippleLayout buttonSend, buttonAttach, buttonCancel, buttonContinue;
    private EditText inputMessage;
    private ScrollView scrollView;
    private boolean isOpenMenu = false;
    private Message.Sender typeChat;
    private int chatPosition = 0;
    private List<String> months = Helper.getTranslate(Helper.Translate.months, MainMenu.getInstance());

    private RadioGroup radioGroup;
    private int requestType = 0;

    public String botMoveMessage;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        instance = this;

        Helper.setStatusBarColor(getWindow(), ContextCompat.getColor(getBaseContext(), R.color.white));
        Helper.setStatusBarIconWhite(getWindow());
        chatPosition = getIntent().getExtras().getInt("chatPosition");
        findView();
        toolbarContainer.addView(new Toolbar(getInstance(), false, "Chat",
                getInstance().getDrawable(R.drawable.toolbar_arrow_left), null));
        toolbarLeft = findViewById(R.id.toolbar_left_icon);
        toolbarLeft.setOnClickListener(v -> super.finish());

        typeChat = MainMenu.getInstance().listChat.get(chatPosition).getType();
        switch (typeChat) {
            case MANAGER:
                inputMessage.setEnabled(true);
                inputMessage.setHint("Enter the message...");
                break;
            case BOT:
                inputMessage.setEnabled(false);
                inputMessage.setHint("You can only write to the manager");
                break;
        }

        drawMessages(0);

        buttonAttach.setOnClickListener(v -> toggleMenu());
        buttonContinue.setOnClickListener(v -> {
            hideMenu();
            int selectedId = radioGroup.getCheckedRadioButtonId();
            Helper.log("selectedId: " + selectedId);

            switch (selectedId) {
                case R.id.radio_manager_recall:
                    Helper.showDialog(getInstance(), LayoutInflater.from(getInstance().getBaseContext()).inflate(R.layout.dialog_bot_manager_recall, null), this::onPositiveManagerRecall, this::onNegativeManagerRecall);
                    requestType = 0;
                    break;
                case R.id.radio_courier_recall:
                    Helper.showDialog(getInstance(), LayoutInflater.from(getInstance().getBaseContext()).inflate(R.layout.dialog_bot_courier_recall, null), this::onPositiveCourierRecall, this::onNegativeCourierRecall);
                    requestType = 1;
                    break;
                case R.id.radio_move_date_delivery:
                    startActivity(new Intent(getInstance(), PlanPauseMove.class));
                    requestType = 2;
                    break;
                case R.id.radio_change_delivery_location:
                    requestType = 3;
                    break;
                case R.id.radio_change_current_plan:
                    requestType = 4;
                    break;
                case R.id.radio_cancel_delivery:
                    requestType = 5;
                    break;
            }

        });
        buttonCancel.setOnClickListener(v -> hideMenu());
        backgroundChat.setOnClickListener(v -> hideMenu());
        buttonSend.setOnTouchListener((v, event) -> {
            if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                sendMessage(Message.Sender.CLIENT, inputMessage.getText().toString());
                inputMessage.setText("");
            }
            return false;
        });
        inputMessage.setOnTouchListener((v, event) -> {
            scrollToBottom();
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(botMoveMessage != null) {
            sendMessage(Message.Sender.BOT,botMoveMessage);
        }
    }

    private void onPositiveManagerRecall(Object o) {
        sendMessage(Message.Sender.BOT, "The application has been sent. Our manager will call you shortly. Please wait");
    }
    private void onNegativeManagerRecall(Object o) {
    }
    private void onPositiveCourierRecall(Object o) {
        sendMessage(Message.Sender.BOT, "The application has been sent. Courier will call you back soon");
    }
    private void onNegativeCourierRecall(Object o) {
    }




    private void drawMessages(int startPosition) {
        for (int i = startPosition; i < MainMenu.getInstance().listChat.get(chatPosition).getMessages().size(); i++) {
            MessageCard hc = new MessageCard(getBaseContext());
            hc.setText(MainMenu.getInstance().listChat.get(chatPosition).getMessages().get(i).getText());
            Date date = MainMenu.getInstance().listChat.get(chatPosition).getMessages().get(i).getDate();
            hc.setDate(date.getDate() + " " + months.get(date.getMonth() - 1) + " " + date.getYear() + ", " + "10:27");
            hc.setSender(MainMenu.getInstance().listChat.get(chatPosition).getMessages().get(i).getSender());
            backgroundChat.addView(hc.getView());
            scrollToBottom();
        }
    }

    private void sendMessage(Message.Sender sender, String text) {
        Message message = new Message(sender, new Date(2019, 10, 2), text);
        MainMenu.getInstance().listChat.get(chatPosition).getMessages().add(message);
        drawMessages(MainMenu.getInstance().listChat.get(chatPosition).getMessages().size() - 1);
    }

    private void scrollToBottom() {
        new Handler().postDelayed(() -> {
            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
        }, 500);
    }

    private void toggleMenu() {
        if (typeChat == Message.Sender.BOT) {
            if (isOpenMenu) {
                hideMenu();
            } else {
                showMenu();
            }
        }
    }

    private void showMenu() {
        botAttachMenu.setVisibility(View.VISIBLE);
        isOpenMenu = true;
    }

    private void hideMenu() {
        botAttachMenu.setVisibility(View.GONE);
        isOpenMenu = false;
    }

    private void findView() {
        toolbarContainer = findViewById(R.id.toolbar_container);
        buttonAttach = findViewById(R.id.button_attach);
        buttonSend = findViewById(R.id.button_send);
        inputMessage = findViewById(R.id.input_message);
        botAttachMenu = findViewById(R.id.bot_attach_menu);
        buttonContinue = findViewById(R.id.button_continue);
        buttonCancel = findViewById(R.id.button_cancel);
        backgroundChat = findViewById(R.id.background_chat);
        scrollView = findViewById(R.id.scroll_view);

        radioGroup = findViewById(R.id.input_radio);

    }
}
