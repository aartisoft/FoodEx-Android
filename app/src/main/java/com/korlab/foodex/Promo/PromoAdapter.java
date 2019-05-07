package com.korlab.foodex.Promo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.korlab.foodex.Data.Promo;
import com.korlab.foodex.MainMenu;
import com.korlab.foodex.R;
import com.korlab.foodex.Technical.Helper;

import java.util.List;

public class PromoAdapter extends PagerAdapter {
    private Context mContext;
    private LayoutInflater inflater;

    private RecyclerView parentRecycler;
//    private View parentView;
//    private ViewPager viewPager;
    private List<Promo> data;
//    private int min, max, current;
//    private boolean isAnimation = false;

//    public void setCurrent(int current) {
//        this.current = current;
//    }


//    public PromoAdapter(Context context) {
//        mContext = context;
//    }

    public PromoAdapter(LayoutInflater inflater, List<Promo> data) {
        this.data = data;
        this.inflater = inflater;
//        min = 0;
//        current = 0;
//        max = data.size()-1;
    }

    @Override
    public int getCount() {
        return data.size();
    }


    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.item_promo_card, container, false);
        view.setTag(position);
        container.addView(view);

//        ImageViewTouch imageView = view.findViewById(R.id.image);
//////        imageView.setImageBitmap(pagerArr.get(position).getBitmap());
////        imageView.setImageBitmap(pagerArr.get(position).getBitmap(), null, -1, -1);
////        return view;

        Promo item = data.get(position);
        Helper.log(position+": ");
        Helper.logObjectToJson(item);

        ImageView image;
        CardView card;
        TextView name, date;
        ImageView next, prev;


        image = view.findViewById(R.id.image);
        name = view.findViewById(R.id.promo_name);
        date = view.findViewById(R.id.promo_date);
        card = view.findViewById(R.id.card);

        name.setText(item.getName());
        date.setText(item.getDate().getDay() + " " + Helper.getTranslate(Helper.Translate.months, MainMenu.getInstance()).get(item.getDate().getMonth() - 1));
        image.setImageBitmap(item.getImage());
//        Glide.with(view.getContext())
//                .load(item.getImage())
//                .into(image);
//        promoName.setText(position+"");

        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        item customPagerEnum = item.values()[position];
//        return mContext.getString(customPagerEnum.getTitleResId());
//    }

//
//    @Override
//    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//        parentRecycler = recyclerView;
//    }

//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        View v = inflater.inflate(R.layout.item_promo_card, parent, false);
//        return new ViewHolder(v);
//    }

//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.image.setImageResource(data.get(position).getImage());
//        Glide.with(holder.itemView.getContext())
//                .load(data.get(position).getImage())
//                .into(holder.image);
//        holder.card.post(() -> {

//
//            int h = 0, w = 0;
//            if(holder.card.getWidth() > holder.card.getHeight())
//                h = holder.card.getHeight();
//            else
//                w = holder.card.getWidth();
//                holder.card.getLayoutParams().height = h - 20;

//            holder.card.setLayoutParams(new CardView.LayoutParams(
//                    CardView.LayoutParams.WRAP_CONTENT, 100));
//            holder.card.setMinimumHeight(100);

//            holder.card.getLayoutParams().width = 200;
//            holder.card.requestLayout();
//        });
//    }

//    @Override
//    public int getItemCount() {
//        return data.size();
//    }

//    class ViewHolder extends RecyclerView.ViewHolder {
//
//        private ImageView image;
//        private CardView card;
//        private ImageView next, prev;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            image = itemView.findViewById(R.id.image);
//            card = itemView.findViewById(R.id.card);
////            next = parentView.findViewById(R.id.next);
////            prev = parentView.findViewById(R.id.prev);
////            Helper.log("[" + min + "-" + max + "]");
////
////            next.setOnClickListener(v -> {
////                Helper.log("Current: " + current);
////                if(current<max && !isAnimation) {
////                    isAnimation = true;
////                    current++;
////                    int position = current;
////                    parentRecycler.smoothScrollToPosition(position);
////                    new Handler().postDelayed(() -> {
////                        parentRecycler.scrollToPosition(position);
////                        parentRecycler.getAdapter().notifyDataSetChanged();
////                        isAnimation = false;
////                    },500);
////                    Helper.log("Next to: " + position);
////                }
////            });
////            prev.setOnClickListener(v -> {
////                Helper.log("Current: " + current);
////                if(current>min && !isAnimation) {
////                    isAnimation = true;
////                    current--;
////                    int position = current;
////                    parentRecycler.smoothScrollToPosition(position);
////                    new Handler().postDelayed(() -> {
////                        parentRecycler.scrollToPosition(position);
////                        parentRecycler.getAdapter().notifyDataSetChanged();
////                        isAnimation = false;
////                    },500);
////                    Helper.log("Back to: " + position);
////                }
////            });
//        }
//    }
}
