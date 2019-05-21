package com.korlab.foodex;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.balysv.materialripple.MaterialRippleLayout;
import com.korlab.foodex.Data.User;
import com.korlab.foodex.Technical.Helper;
import com.korlab.foodex.UI.MaterialButton;
import com.korlab.foodex.UI.MessageCard;
import com.korlab.foodex.UI.Toolbar;

import java.sql.Date;

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
    private boolean isBotChat = true, isOpenMenu = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        instance = this;

        Helper.setStatusBarColor(getWindow(), ContextCompat.getColor(getBaseContext(), R.color.white));
        Helper.setStatusBarIconWhite(getWindow());

        findView();
        toolbarContainer.addView(new Toolbar(getInstance(), false, "Chat",
                getInstance().getDrawable(R.drawable.toolbar_arrow_left), null));
        toolbarLeft = findViewById(R.id.toolbar_left_icon);
        toolbarLeft.setOnClickListener(v -> super.finish());

        if(isBotChat) {
            inputMessage.setEnabled(false);
            inputMessage.setHint("You can only write to the manager");
        }

        for(int i=0; i<MainMenu.getInstance().listChat.get(0).getMessages().size(); i++) { // todo 0
            MessageCard hc = new MessageCard(getBaseContext());
            hc.setText(MainMenu.getInstance().listChat.get(0).getMessages().get(i).getText());
            Date date = MainMenu.getInstance().listChat.get(0).getMessages().get(i).getDate();
            hc.setDate(date.getDate() + " " + date.getMonth() + ", " + date.getYear());
            hc.setSender(MainMenu.getInstance().listChat.get(0).getMessages().get(i).getSender());
            backgroundChat.addView(hc.getView());
        }

        buttonAttach.setOnClickListener(v -> {
            toggleMenu();
        });
        buttonContinue.setOnClickListener(v -> {
            hideMenu();
        });
        buttonCancel.setOnClickListener(v->{
            hideMenu();
        });
        backgroundChat.setOnClickListener(v->{
            hideMenu();
        });
    }

    private void toggleMenu() {
        if(isBotChat){
            if(isOpenMenu) {
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


    }
}
