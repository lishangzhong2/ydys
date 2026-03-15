package com.tphy.hospitaldoctor.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tphy.hospitaldoctor.R;
import com.tphy.hospitaldoctor.ui.bean.DrugCodeInfor;

import java.util.List;

/**
 * Created by ${王琪} on ${2017/11/28}.
 */

public class YZNRAdaper extends BaseAdapter {
    private Context context;
    private List<DrugCodeInfor> drugCodeInfors;

    public YZNRAdaper(Context context, List<DrugCodeInfor> drugCodeInfors) {
        this.context = context;
        this.drugCodeInfors = drugCodeInfors;
    }

    public void refreshData(List<DrugCodeInfor> list) {
        this.drugCodeInfors = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return drugCodeInfors.size();
    }

    @Override
    public Object getItem(int position) {
        return drugCodeInfors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_yznr_list, parent, false);
            holder = new ViewHolder();
            holder.yznr_mc = convertView.findViewById(R.id.yznr_mc);
            holder.yznr_gg = convertView.findViewById(R.id.yznr_gg);
            holder.yznr_lx = convertView.findViewById(R.id.yznr_lx);
            holder.yznr_jlbl = convertView.findViewById(R.id.yznr_jlbl);
            holder.yznr_ll = convertView.findViewById(R.id.yznr_ll);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DrugCodeInfor drugCodeInfor = drugCodeInfors.get(position);
        String mc = drugCodeInfor.getMC();
        String gg = drugCodeInfor.getGG();
        String jlbl = drugCodeInfor.getJLBL();
        String ybmc = drugCodeInfor.getYBMC();
        if (position % 2 == 0) {
            holder.yznr_ll.setBackgroundColor(context.getResources().getColor(android.R.color.white));
        } else {
            holder.yznr_ll.setBackgroundColor(context.getResources().getColor(R.color.itemBg));
        }
        holder.yznr_mc.setText(mc);
        holder.yznr_gg.setText(gg);
        holder.yznr_lx.setText(ybmc);
        holder.yznr_jlbl.setText(jlbl);
        return convertView;
    }

    public class ViewHolder {
        /**
         * 名称
         */
        private TextView yznr_mc;
        /**
         * 规格
         */
        private TextView yznr_gg;
        /**
         * 医保类型
         */
        private TextView yznr_lx;
        /**
         * JLBL
         */
        private TextView yznr_jlbl;
        private LinearLayout yznr_ll;
    }
}
