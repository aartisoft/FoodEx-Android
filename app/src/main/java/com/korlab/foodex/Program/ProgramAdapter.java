package com.korlab.foodex.Program;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.korlab.foodex.Data.Program;
import com.korlab.foodex.MainMenu;
import com.korlab.foodex.R;

import java.util.List;

public class ProgramAdapter extends ArrayAdapter<Program> {

    Context mContext;

    private static class ViewHolder {
        ImageView image;
        TextView name;
        TextView smallDescription;
        TextView price;
    }

    public ProgramAdapter(List<Program> data, Context context) {
        super(context, R.layout.component_program_card, data);
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Program item = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.component_program_card, parent, false);
            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.smallDescription = convertView.findViewById(R.id.small_description);
            viewHolder.image = convertView.findViewById(R.id.image);
            viewHolder.price = convertView.findViewById(R.id.price);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Glide.with(MainMenu.getInstance().getBaseContext()).load(item.getImage()).into(viewHolder.image);
        viewHolder.name.setText(item.getName());
        viewHolder.smallDescription.setText(item.getSmallDescription());
        viewHolder.price.setText("$6.32");
        return convertView;
    }
}