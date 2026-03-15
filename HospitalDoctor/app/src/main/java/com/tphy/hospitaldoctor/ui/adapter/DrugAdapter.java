package com.tphy.hospitaldoctor.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tphy.hospitaldoctor.R;
import com.tphy.hospitaldoctor.ui.bean.DrugCodeInfor;

import java.util.List;

/**
 * Created by ${王琪} on ${2017/11/28}.
 */

public class DrugAdapter extends BaseAdapter {
    private List<DrugCodeInfor> drugCodeInfors;
    private Context context;

    public DrugAdapter(List<DrugCodeInfor> drugCodeInfors, Context context) {
        this.drugCodeInfors = drugCodeInfors;
        this.context = context;
    }

    public void setDrugCodeInfors(List<DrugCodeInfor> drugCodeInfors) {
        this.drugCodeInfors = drugCodeInfors;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_luru_drug, parent, false);
            holder = new ViewHolder();
            holder.iv_drugPic = convertView.findViewById(R.id.item_iv_drugPic);
            holder.tv_drugName = convertView.findViewById(R.id.item_tv_drugName);
            holder.tv_gg = convertView.findViewById(R.id.item_tv_gg);
            holder.tv_yf = convertView.findViewById(R.id.item_tv_yf);
            holder.tv_kf = convertView.findViewById(R.id.item_tv_kf);
            holder.iv_yblx = convertView.findViewById(R.id.item_iv_ybmc);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position < drugCodeInfors.size()) {
            DrugCodeInfor drugCodeInfor = drugCodeInfors.get(position);
            String mc = drugCodeInfor.getMC();
            String gg = drugCodeInfor.getGG();
            String jlbl = drugCodeInfor.getJLBL();
            String ybmc = drugCodeInfor.getYBMC();
            String kc = drugCodeInfor.getKC();
            holder.tv_gg.setText("规格：" + gg);
            holder.tv_drugName.setText(mc);
//        holder.tv_yf.setText();
            holder.tv_kf.setText("库存：" + kc + "\t\t" + ybmc);
            if (ybmc.contains("甲")) {
                holder.iv_yblx.setBackground(context.getResources().getDrawable(R.mipmap.ic_jia));
            } else if (ybmc.contains("乙")) {
                holder.iv_yblx.setBackground(context.getResources().getDrawable(R.mipmap.ic_yi));
            } else if (ybmc.contains("丙")) {
                holder.iv_yblx.setBackground(context.getResources().getDrawable(R.mipmap.ic_bing));
            } else {
                holder.iv_yblx.setBackground(null);
            }
        }
        return convertView;
    }

    public class ViewHolder {
        private ImageView iv_drugPic;
        private TextView tv_drugName;
        private TextView tv_gg;
        private TextView tv_yf;
        private TextView tv_kf;
        private ImageView iv_yblx;
    }
}
