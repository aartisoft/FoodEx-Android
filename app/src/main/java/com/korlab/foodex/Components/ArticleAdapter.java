package com.korlab.foodex.Components;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.korlab.foodex.Data.ProgramDay;
import com.korlab.foodex.R;
import com.korlab.foodex.Technical.Helper;
import com.korlab.foodex.UI.group.GroupRecyclerAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 适配器
 * Created by huanghaibin on 2017/12/4.
 */

public class ArticleAdapter extends GroupRecyclerAdapter<String, Article> {



    public ArticleAdapter(Context context) {
        super(context);

    }


    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new ArticleViewHolder(mInflater.inflate(R.layout.component_calendar_card, parent, false));
    }

    @Override
    protected void onBindViewHolder(RecyclerView.ViewHolder holder, Article item, int position) {
        ArticleViewHolder h = (ArticleViewHolder) holder;

        h.mCalendarCard.animate().alpha(0).setDuration(1000).setInterpolator(new DecelerateInterpolator()).withEndAction(() -> {
            h.mCalendarCard.animate().alpha(1).setDuration(1000).setInterpolator(new AccelerateInterpolator()).start();
        }).start();


        h.mTextTitle.setText(item.getTitle());
        h.mTextContent.setText(""+item.getCalorie());
    }

    private class ArticleViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextTitle,
                mTextContent;
        private CardView mCalendarCard;
        private ArticleViewHolder(View itemView) {
            super(itemView);
            mCalendarCard = itemView.findViewById(R.id.calendar_card);
            mTextTitle = itemView.findViewById(R.id.header);
            mTextContent = itemView.findViewById(R.id.date);
        }
    }


    private Article create(String title, int calorie) {
        Article article = new Article();
        article.setTitle(title);
        article.setCalorie(calorie);
        return article;
    }

    public List<Article> init(ProgramDay programDay) {
        LinkedHashMap<String, List<Article>> map = new LinkedHashMap<>();


        List<Article> list = new ArrayList<>();
        for(int i=0; i<programDay.getMeals().size(); i++) {
            list.add(create(programDay.getMeals().get(i).getName(),
                    programDay.getMeals().get(i).getCalorie()));
        }
        Helper.logObjectToJson(list);

        List<String> titles = new ArrayList<>();
        map.put("", list);
        titles.add("");
        resetGroups(map,titles);
        return list;
    }
}
