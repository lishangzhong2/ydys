package com.tphy.hospitaldoctor.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tphy.hospitaldoctor.R;
import com.tphy.hospitaldoctor.ui.bean.TaoyizhuBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaoYizhuAdapter extends RecyclerView.Adapter<TaoYizhuAdapter.TaoyizhuHolder> {

    private LayoutInflater inflater;
    private List<TaoyizhuBean> taoyizhuBeanList;
    private Context context;

    public TaoYizhuAdapter(List<TaoyizhuBean> taoyizhuBeans, Context context) {
        this.taoyizhuBeanList = taoyizhuBeans;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public List<TaoyizhuBean> getCheckedItems() {
        List<TaoyizhuBean> list = new ArrayList<>();
        for (int i = 0; i < taoyizhuBeanList.size(); i++) {
            TaoyizhuBean taoyizhuBean = taoyizhuBeanList.get(i);
            boolean checked = taoyizhuBean.isChecked();
            if (checked) {
                list.add(taoyizhuBean);
            }
        }
        return list;
    }

    public void updateData(List<TaoyizhuBean> taoyizhuBeanList) {
        this.taoyizhuBeanList = taoyizhuBeanList;
        notifyDataSetChanged();
    }

    @Override
    public TaoyizhuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_taoyizhu, parent, false);
        return new TaoyizhuHolder(view);
    }

    @Override
    public void onBindViewHolder(final TaoyizhuHolder holder, int position) {
        if (position % 2 == 0) {
            holder.taoyizhuLayout.setBackgroundColor(context.getResources().getColor(android.R.color.white));
        } else {
            holder.taoyizhuLayout.setBackgroundColor(context.getResources().getColor(R.color.listItemBg));
        }
        final TaoyizhuBean taoyizhuBean = taoyizhuBeanList.get(position);
        String group_flag = taoyizhuBean.getGROUP_FLAG();
        String mc = taoyizhuBean.getMC();
        String wzbz = taoyizhuBean.getWZBZ();
        String gg = taoyizhuBean.getGG();
        String dose = taoyizhuBean.getDOSE();
        String unit = taoyizhuBean.getUNIT();
        String ypyfmc = taoyizhuBean.getYPYFMC();
        String gyplmc = taoyizhuBean.getGYPLMC();
        boolean checked = taoyizhuBean.isChecked();
        holder.item_check.setChecked(checked);
        holder.item_tv_yzmc.setText(mc+"\t\t"+wzbz);
        holder.item_tv_zbz.setText(group_flag);
        holder.item_tv_gg.setText(gg);
        holder.item_tv_jl.setText(dose);
        holder.item_tv_dw.setText(unit);
        holder.item_tv_yf.setText(ypyfmc);
        holder.item_tv_pl.setText(gyplmc);
        holder.item_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.item_check.isChecked()) {
                    taoyizhuBean.setChecked(true);
                } else {
                    taoyizhuBean.setChecked(false);
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return taoyizhuBeanList.size();
    }

    public class TaoyizhuHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_check)
        CheckBox item_check;
        @BindView(R.id.item_tv_zbz)
        TextView item_tv_zbz;
        @BindView(R.id.item_tv_yzmc)
        TextView item_tv_yzmc;
        @BindView(R.id.item_tv_gg)
        TextView item_tv_gg;
        @BindView(R.id.item_tv_jl)
        TextView item_tv_jl;
        @BindView(R.id.item_tv_dw)
        TextView item_tv_dw;
        @BindView(R.id.item_tv_yf)
        TextView item_tv_yf;
        @BindView(R.id.item_tv_pl)
        TextView item_tv_pl;
        @BindView(R.id.taoyizhuLayout)
        LinearLayout taoyizhuLayout;


        public TaoyizhuHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
