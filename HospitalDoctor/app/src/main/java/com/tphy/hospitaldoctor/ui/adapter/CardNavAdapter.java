package com.tphy.hospitaldoctor.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tphy.hospitaldoctor.R;
import com.tphy.hospitaldoctor.ui.bean.CardNavBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${王琪} on ${2017/11/28}.
 */

public class CardNavAdapter extends RecyclerView.Adapter<CardNavAdapter.CardNavHolder> {
    private List<CardNavBean> cardNavBeanList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public CardNavAdapter(List<CardNavBean> cardNavBeanList, Context context) {
        this.cardNavBeanList = cardNavBeanList;
        this.context = context;
    }

    public void refresh(List<CardNavBean> list) {
        this.cardNavBeanList = list;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public CardNavHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_nav_item, parent, false);
        return new CardNavHolder(view);
    }

    @Override
    public void onBindViewHolder(final CardNavHolder holder, final int position) {
        CardNavBean cardNavBean = cardNavBeanList.get(position);
        String end = cardNavBean.getEnd();
        String start = cardNavBean.getStart();
        holder.card_start.setText(start);
        holder.card_end.setText(end);
        holder.card_nav_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onItemClickListener) {
                    onItemClickListener.onItemClick(holder.card_nav_item, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardNavBeanList.size();
    }

    public class CardNavHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_start)
        TextView card_start;
        @BindView(R.id.card_end)
        TextView card_end;
        @BindView(R.id.card_nav_item)
        LinearLayout card_nav_item;

        public CardNavHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


}
