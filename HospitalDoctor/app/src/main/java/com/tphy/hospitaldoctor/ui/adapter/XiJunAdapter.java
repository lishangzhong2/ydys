package com.tphy.hospitaldoctor.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tphy.hospitaldoctor.R;
import com.tphy.hospitaldoctor.ui.bean.XiJunResult;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class XiJunAdapter extends RecyclerView.Adapter<XiJunAdapter.XiJunHolder> {
    private Context context;
    private List<XiJunResult> xiJunResultList;

    public XiJunAdapter(Context context, List<XiJunResult> xiJunResultList) {
        this.context = context;
        this.xiJunResultList = xiJunResultList;
    }

    public void refreshData(List<XiJunResult> xiJunResultList) {
        this.xiJunResultList = xiJunResultList;
    }

    @Override
    public XiJunHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_xijun_list, parent, false);
        return new XiJunHolder(view);
    }

    @Override
    public void onBindViewHolder(XiJunHolder holder, int position) {
        if (position % 2 == 0) {
            holder.linear.setBackgroundColor(holder.colorWhite);
        } else {
            holder.linear.setBackgroundColor(holder.itemBg);
        }
        XiJunResult xiJunResult = xiJunResultList.get(position);
        String xjmc = xiJunResult.get细菌名称();
        String kssmc = xiJunResult.get抗生素名称();
        String jg = xiJunResult.get结果();
        String mgd = xiJunResult.get敏感度();
        String ny = xiJunResult.get耐药();
        String zj = xiJunResult.get中介();
        String mg = xiJunResult.get敏感();
        String bbh = xiJunResult.get标本号();
        holder.tv_xjmc.setText(xjmc);
        holder.tv_kssmc.setText(kssmc);
        holder.tv_jg.setText(jg);
        holder.tv_mgd.setText(mgd);
        holder.tv_ny.setText(ny);
        holder.tv_zj.setText(zj);
        holder.tv_mg.setText(mg);
        holder.tv_bbh.setText(bbh);
    }

    @Override
    public int getItemCount() {
        return xiJunResultList.size();
    }

    public class XiJunHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_tv_xjmc)
        TextView tv_xjmc;
        @BindView(R.id.item_tv_kssmc)
        TextView tv_kssmc;
        @BindView(R.id.item_tv_jg)
        TextView tv_jg;
        @BindView(R.id.item_tv_mgd)
        TextView tv_mgd;
        @BindView(R.id.item_tv_ny)
        TextView tv_ny;
        @BindView(R.id.item_tv_zj)
        TextView tv_zj;
        @BindView(R.id.item_tv_mg)
        TextView tv_mg;
        @BindView(R.id.item_tv_bbh)
        TextView tv_bbh;
        @BindView(R.id.item_xijun_ll)
        LinearLayout linear;
        @BindColor(R.color.listItemBg)
        int itemBg;
        @BindColor(android.R.color.white)
        int colorWhite;

        public XiJunHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
