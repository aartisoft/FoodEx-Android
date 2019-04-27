package com.korlab.foodex.Program;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.korlab.foodex.Data.Program;
import com.korlab.foodex.R;

import java.util.List;

public class ProgramAdapter extends ArrayAdapter<Program> {

    private List<Program> dataSet;
    Context mContext;

    private static class ViewHolder {
        ImageView image;
        TextView name;
        TextView smallDescription;
        TextView price;
    }

    public ProgramAdapter(List<Program> data, Context context) {
        super(context, R.layout.component_program_card, data);
        this.dataSet = data;
        this.mContext=context;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Program dataModel = getItem(position);
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

        lastPosition = position;

        viewHolder.image.setImageBitmap(dataModel.getImage());
        viewHolder.name.setText(dataModel.getName());
        viewHolder.smallDescription.setText(dataModel.getSmallDescription());
        viewHolder.price.setText("$6.32");
        return convertView;
    }
}