package com.tphy.hospitaldoctor.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tphy.hospitaldoctor.R;
import com.tphy.hospitaldoctor.ui.bean.JianYanResult;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${王琪} on ${2017/11/28}.
 */

public class JianYanAdapter extends RecyclerView.Adapter<JianYanAdapter.JianYanListHolder> {
    private Context context;
    private List<JianYanResult> jianYanResults;


    public JianYanAdapter(Context context, List<JianYanResult> jianYanResults) {
        this.context = context;
        this.jianYanResults = jianYanResults;
    }

    public void refreshData(List<JianYanResult> list) {
        this.jianYanResults = list;
        notifyDataSetChanged();
    }

    @Override
    public JianYanListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_jianyan_list, parent, false);
        return new JianYanListHolder(view);
    }

    @Override
    public void onBindViewHolder(JianYanListHolder holder, int position) {
        if (position % 2 == 0) {
            holder.linear.setBackgroundColor(holder.colorWhite);
        } else {
            holder.linear.setBackgroundColor(holder.itemBg);
        }
        JianYanResult jianYanResult = jianYanResults.get(position);
        String zwmc = jianYanResult.getZWMC();
        String jg = jianYanResult.getJG();
        String dw = jianYanResult.getDW();
        String range = jianYanResult.getRANGE();
        String zt = jianYanResult.getZT();
//        String ywmc = jianYanResult.getYWMC();
        holder.tv_ZWMC.setText(zwmc);
        holder.tv_JG.setText(jg);
        holder.tv_DW.setText(dw);
        holder.tv_RANGE.setText(range);
        holder.tv_ZT.setText(zt);
        if (zt.contains("↑")) {
            holder.tv_ZT.setTextColor(context.getResources().getColor(R.color.textRed));
        } else if (zt.contains("↓")) {
            holder.tv_ZT.setTextColor(context.getResources().getColor(R.color.textGreen));
        } else {
            holder.tv_ZT.setTextColor(context.getResources().getColor(R.color.textBlack));
        }
//        holder.tv_YWMC.setText(ywmc);
    }

    @Override
    public int getItemCount() {
        return jianYanResults.size();
    }

    public class JianYanListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.jianyan_tv_ZWMC)
        TextView tv_ZWMC;
        @BindView(R.id.jianyan_tv_JG)
        TextView tv_JG;
        @BindView(R.id.jianyan_tv_DW)
        TextView tv_DW;
        @BindView(R.id.jianyan_tv_RANGE)
        TextView tv_RANGE;
        @BindView(R.id.jianyan_tv_ZT)
        TextView tv_ZT;
//        @BindView(R.id.jianyan_tv_YWMC)
//        TextView tv_YWMC;
        @BindView(R.id.item_baogao_ll)
        LinearLayout linear;
        @BindColor(R.color.listItemBg)
        int itemBg;
        @BindColor(android.R.color.white)
        int colorWhite;

        public JianYanListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
