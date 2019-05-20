package com.korlab.foodex.Chats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.korlab.foodex.Data.Chat;
import com.korlab.foodex.Data.Program;
import com.korlab.foodex.MainMenu;
import com.korlab.foodex.R;
import com.korlab.foodex.UI.CircleTransform;

import java.util.List;

public class ChatsAdapter extends ArrayAdapter<Chat> {

    Context mContext;
    List<Chat> data;

    private static class ViewHolder {
        ImageView image;
        TextView name;
        TextView dateLastMessage;
        TextView subject;
        TextView countUnreadMessages;
        TextView previewMessage;
    }

    public ChatsAdapter(List<Chat> data, Context context) {
        super(context, R.layout.component_chat, data);
        this.mContext = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Chat item = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.component_chat, parent, false);
            viewHolder.image = convertView.findViewById(R.id.image);
            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.dateLastMessage = convertView.findViewById(R.id.date_last_message);
            viewHolder.subject = convertView.findViewById(R.id.subject);
            viewHolder.countUnreadMessages = convertView.findViewById(R.id.count_unread_messages);
            viewHolder.previewMessage = convertView.findViewById(R.id.preview_message);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Glide.with(MainMenu.getInstance().getBaseContext())
                .load(item.getImage())
                .bitmapTransform(new CircleTransform(MainMenu.getInstance().getBaseContext()))
                .into(viewHolder.image);
        viewHolder.name.setText(item.getName());
        viewHolder.dateLastMessage.setText("14 мин. назад");
        viewHolder.subject.setText(item.getSubject());
        viewHolder.countUnreadMessages.setText(""+item.getCountUnreadMessage());
        viewHolder.previewMessage.setText(item.getPreviewMessage());
        return convertView;
    }

}