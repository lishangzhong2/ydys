package com.tphy.hospitaldoctor.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tphy.hospitaldoctor.R;
import com.tphy.hospitaldoctor.ui.bean.YiZhuBean;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${王琪} on ${2017/11/28}.
 */

public class LuRuAdapter  extends RecyclerView.Adapter<LuRuAdapter.LuruHolder> {
    private Context context;
    private List<YiZhuBean> yiZhuBeanList;

    public LuRuAdapter(Context context, List<YiZhuBean> yiZhuInfos) {
        this.context = context;
        this.yiZhuBeanList = yiZhuInfos;
    }

    public void refreshData(List<YiZhuBean> beans) {
        yiZhuBeanList = beans;
        notifyDataSetChanged();
    }

    @Override

    public LuruHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_luru_list, parent, false);
        return new LuruHolder(view);
    }

    @Override
    public void onBindViewHolder(LuruHolder holder, int position) {
        if (position % 2 == 0) {
            holder.yizhu_ll.setBackgroundColor(holder.colorWhite);
        } else {
            holder.yizhu_ll.setBackgroundColor(holder.itemBg);
        }
        YiZhuBean yiZhuInfo = yiZhuBeanList.get(position);
//        String state = yiZhuInfo.getYZZT();
        String startTime = yiZhuInfo.getXSYZSJ();
//        String group = yiZhuInfo.getZBZ();
        String yizhu = yiZhuInfo.getYZMC();
//        String doctorSignature = yiZhuInfo.getYSMC();
//        String nurseSignature = yiZhuInfo.getZXHSMC();
//        String endTime = yiZhuInfo.getTZSJ();
//        String endDoctor = yiZhuInfo.getTZYSMC();
//        String nurseCheck = yiZhuInfo.getTZHSMC();
//        String num = yiZhuInfo.getYZH();
        holder.tv_startTime.setText(startTime);
        holder.tv_yizhu.setText(yizhu);

    }

    @Override
    public int getItemCount() {
        return yiZhuBeanList.size();
    }

    public class LuruHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.luru_tv_startTime)
        TextView tv_startTime;
        @BindView(R.id.luru_tv_yizhu)
        TextView tv_yizhu;
        @BindColor(R.color.itemBg)
        int itemBg;
        @BindColor(android.R.color.white)
        int colorWhite;
        @BindView(R.id.luru_ll)
        LinearLayout yizhu_ll;
        public LuruHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
