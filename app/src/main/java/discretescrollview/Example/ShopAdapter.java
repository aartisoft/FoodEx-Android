package discretescrollview.Example;

import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.korlab.foodex.R;
import com.korlab.foodex.Technical.Helper;

import java.util.List;

import discretescrollview.DiscreteScrollView;

/**
 * Created by yarolegovich on 07.03.2017.
 */

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    private RecyclerView parentRecycler;
    private View parentView;
    private DiscreteScrollView discreteScrollView;
    private List<Item> data;
    private int min, max, current;
    private boolean isAnimation = false;

    public void setCurrent(int current) {
        this.current = current;
    }
    public ShopAdapter(List<Item> data, View parent, DiscreteScrollView itemPicker) {
        this.data = data;
        min = 0;
        current = 0;
        max = data.size()-1;
        parentView = parent;
        discreteScrollView = itemPicker;
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        parentRecycler = recyclerView;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_shop_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private CardView card;
        private ImageView next, prev;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            card = itemView.findViewById(R.id.card);
            next = parentView.findViewById(R.id.next);
            prev = parentView.findViewById(R.id.prev);
            Helper.log("[" + min + "-" + max + "]");

            next.setOnClickListener(v -> {
                Helper.log("Current: " + current);
                if(current<max && !isAnimation) {
                    isAnimation = true;
                    current++;
                    int position = current;
                    parentRecycler.smoothScrollToPosition(position);
                    new Handler().postDelayed(() -> {
                        parentRecycler.scrollToPosition(position);
                        parentRecycler.getAdapter().notifyDataSetChanged();
                        isAnimation = false;
                    },500);
                    Helper.log("Next to: " + position);
                }
            });
            prev.setOnClickListener(v -> {
                Helper.log("Current: " + current);
                if(current>min && !isAnimation) {
                    isAnimation = true;
                    current--;
                    int position = current;
                    parentRecycler.smoothScrollToPosition(position);
                    new Handler().postDelayed(() -> {
                        parentRecycler.scrollToPosition(position);
                        parentRecycler.getAdapter().notifyDataSetChanged();
                        isAnimation = false;
                    },500);
                    Helper.log("Back to: " + position);
                }
            });
        }
    }
}
