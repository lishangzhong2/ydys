package com.tphy.hospitaldoctor.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tphy.hospitaldoctor.R;
import com.tphy.hospitaldoctor.ui.bean.XueTangBean;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: tphy_ydys
 * Date: 2019/12/26
 * Time: 16:49
 */

public class XueTangAdapter extends RecyclerView.Adapter<XueTangAdapter.ViewHolder> {
    Context context;
    List<XueTangBean> list;
    boolean nfmbz ;

    public XueTangAdapter(Context context, List<XueTangBean> list,boolean nfbbz) {
        this.context = context;
        this.list = list;
        this.nfmbz = nfbbz;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_xuetang, parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        XueTangBean xueTangBean = list.get(position);
        if (position % 2 == 0) {
            holder.ll_root.setBackgroundColor(holder.colorWhite);
        } else {
            holder.ll_root.setBackgroundColor(holder.itemBg);
        }
        holder.tv_czsj.setText(xueTangBean.getCZSJ());
        holder.tv_hzxm.setText(xueTangBean.getCWH() + "床\n" + xueTangBean.getHZXM());
        holder.tv_3am.setText(xueTangBean.getA8());
        holder.tv_zcq.setText(xueTangBean.getA1());
        holder.tv_zch.setText(xueTangBean.getA2());
        holder.tv_wcq.setText(xueTangBean.getA3());
        holder.tv_wch.setText(xueTangBean.getA4());
        holder.tv_wancq.setText(xueTangBean.getA5());
        holder.tv_wanch.setText(xueTangBean.getA6());
        holder.tv_sq.setText(xueTangBean.getA7());
        holder.tv_xt.setText(xueTangBean.getA9());
        if (nfmbz){
            holder.tv_sjxt.setVisibility(View.VISIBLE);
            holder.tv_2am.setVisibility(View.VISIBLE);
            holder.tv_2am.setText(xueTangBean.getA10());
            holder.tv_sjxt.setText(xueTangBean.getA11());
        }else {
            holder.tv_sjxt.setVisibility(View.GONE);
            holder.tv_2am.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public void update(List<XueTangBean> xuetangInforList,boolean nfmbz) {
        this.list = xuetangInforList;
        this.nfmbz = nfmbz;
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_czsj)
        TextView tv_czsj;
        @BindView(R.id.tv_hzxm)
        TextView tv_hzxm;
        @BindView(R.id.tv_3am)
        TextView tv_3am;
        @BindView(R.id.tv_2am)
        TextView tv_2am;
        @BindView(R.id.tv_zcq)
        TextView tv_zcq;
        @BindView(R.id.tv_zch)
        TextView tv_zch;
        @BindView(R.id.tv_wcq)
        TextView tv_wcq;
        @BindView(R.id.tv_wch)
        TextView tv_wch;
        @BindView(R.id.tv_wancq)
        TextView tv_wancq;
        @BindView(R.id.tv_wanch)
        TextView tv_wanch;
        @BindView(R.id.tv_sq)
        TextView tv_sq;
        @BindView(R.id.tv_xt)
        TextView tv_xt;
        @BindView(R.id.tv_sjxt)
        TextView tv_sjxt;
        @BindView(R.id.ll_root)
        LinearLayout ll_root;

        @BindColor(R.color.listItemBg)
        int itemBg;
        @BindColor(android.R.color.white)
        int colorWhite;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }
}
