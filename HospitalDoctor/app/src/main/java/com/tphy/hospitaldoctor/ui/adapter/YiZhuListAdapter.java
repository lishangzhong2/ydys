package com.tphy.hospitaldoctor.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tphy.hospitaldoctor.R;
import com.tphy.hospitaldoctor.ui.bean.YiZhuBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${王琪} on ${2017/11/28}.
 */

public class YiZhuListAdapter extends RecyclerView.Adapter<YiZhuListAdapter.YiZhuListHolder> {

    private Context context;
    private List<YiZhuBean> yiZhuBeanList;
    private OnCheckedChangeListener onCheckedChangeListener;

    public YiZhuListAdapter(Context context, List<YiZhuBean> yiZhuInfos) {
        this.context = context;
        this.yiZhuBeanList = yiZhuInfos;
    }

    public void refreshData(List<YiZhuBean> beans) {
        yiZhuBeanList = beans;
        notifyDataSetChanged();
    }

    public List<YiZhuBean> getCheckedList() {
        List<YiZhuBean> checkedList = new ArrayList<>();
        for (int i = 0; i < yiZhuBeanList.size(); i++) {
            YiZhuBean yiZhuBean = yiZhuBeanList.get(i);
            if (yiZhuBean.isChecked()) {
                checkedList.add(yiZhuBean);
            }
        }
        return checkedList;
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    @Override

    public YiZhuListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_yizhu_list, parent, false);
        return new YiZhuListHolder(view);
    }

    @Override
    public void onBindViewHolder(YiZhuListHolder holder, final int position) {
        if (position % 2 == 0) {
            holder.yizhu_ll.setBackgroundColor(holder.colorWhite);
        } else {
            holder.yizhu_ll.setBackgroundColor(holder.itemBg);
        }
        final YiZhuBean yiZhuInfo = yiZhuBeanList.get(position);
        String state = yiZhuInfo.getYZZT();
        String startTime = yiZhuInfo.getXSYZSJ();
        String group = yiZhuInfo.getZBZ();
        String yizhu = yiZhuInfo.getYZMC();
        String doctorSignature = yiZhuInfo.getYSMC();
        String nurseSignature = yiZhuInfo.getZXHSMC();
        String endTime = yiZhuInfo.getXSTZSJ();
        String endDoctor = yiZhuInfo.getTZYSMC();
        String nurseCheck = yiZhuInfo.getTZHSMC();
        final String num = yiZhuInfo.getYZH();
        boolean show = yiZhuInfo.isShow();
        if (show) {
            holder.checkBox.setVisibility(View.VISIBLE);
        } else {
            holder.checkBox.setVisibility(View.GONE);
        }
        boolean checked = yiZhuInfo.isChecked();
        holder.checkBox.setChecked(checked);
        holder.tv_state.setText(state);
        holder.tv_startTime.setText(startTime);
        holder.tv_group.setText(group);
        holder.tv_yizhu.setText(yizhu);
        holder.tv_docSig.setText(doctorSignature);
        holder.tv_nursig.setText(nurseSignature);
        holder.tv_endTime.setText(endTime);
        holder.tv_endDoc.setText(endDoctor);
        holder.tv_nurCheck.setText(nurseCheck);
        holder.tv_num.setText(num);
        int colorText = 0;
        switch (state) {
            case "0":
                holder.tv_state.setText("新增");
                colorText = context.getResources().getColor(R.color.textBlack);
                break;
            case "1":
                colorText = context.getResources().getColor(R.color.textBlack);
                holder.tv_state.setText("保存");
                break;
            case "2":
                colorText = context.getResources().getColor(R.color.textBlack);
                holder.tv_state.setText("提交");
                break;
            case "3":
                colorText = context.getResources().getColor(R.color.textBlack);
                holder.tv_state.setText("审核未通过");
                break;
            case "4":
                colorText = context.getResources().getColor(R.color.textBlack);
                holder.tv_state.setText("审核通过");
                break;
            case "5":
                colorText = context.getResources().getColor(R.color.textBlack);
                holder.tv_state.setText("已生成");
                break;
            case "6":
                colorText = context.getResources().getColor(R.color.textBlack);
                holder.tv_state.setText("已执行");
                break;
            case "7":
                holder.tv_state.setText("已停止");
                colorText = context.getResources().getColor(R.color.textRed);
                break;
            case "10":
                colorText = context.getResources().getColor(R.color.textBlack);
                holder.tv_state.setText("作废");
                break;
        }

        holder.tv_state.setTextColor(colorText);
        holder.tv_startTime.setTextColor(colorText);
        holder.tv_group.setTextColor(colorText);
        holder.tv_yizhu.setTextColor(colorText);
        holder.tv_docSig.setTextColor(colorText);
        holder.tv_nursig.setTextColor(colorText);
        holder.tv_endTime.setTextColor(colorText);
        holder.tv_endDoc.setTextColor(colorText);
        holder.tv_nurCheck.setTextColor(colorText);
        holder.tv_num.setTextColor(colorText);

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                yiZhuInfo.setChecked(isChecked);
//                for (int i = 0; i < yiZhuBeanList.size(); i++) {
//                    if (i != position) {
//                        YiZhuBean bean = yiZhuBeanList.get(i);
//                        String yzh = bean.getYZH();
//                        if (num.equals(yzh)) {
//                            bean.setChecked(isChecked);
//                        }
//                    }
//                }
//                notifyDataSetChanged();
                onCheckedChangeListener.onChecked(buttonView, isChecked, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return yiZhuBeanList.size();
    }

    public class YiZhuListHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_tv_state)
        TextView tv_state;
        @BindView(R.id.item_tv_startTime)
        TextView tv_startTime;
        @BindView(R.id.item_tv_group)
        TextView tv_group;
        @BindView(R.id.item_tv_yizhu)
        TextView tv_yizhu;
        @BindView(R.id.item_tv_docSig)
        TextView tv_docSig;
        @BindView(R.id.item_tv_nursig)
        TextView tv_nursig;
        @BindView(R.id.item_tv_endTime)
        TextView tv_endTime;
        @BindView(R.id.item_tv_endDoc)
        TextView tv_endDoc;
        @BindView(R.id.item_tv_nurCheck)
        TextView tv_nurCheck;
        @BindView(R.id.item_tv_num)
        TextView tv_num;
        @BindView(R.id.item_check)
        CheckBox checkBox;
        @BindView(R.id.item_yizhu_ll)
        LinearLayout yizhu_ll;
        @BindColor(R.color.listItemBg)
        int itemBg;
        @BindColor(android.R.color.white)
        int colorWhite;

        //        @BindColor(R.color.colorYizhuBg)
//        int colorBg;
        @BindDrawable(R.drawable.item_yizhu_selector)
        Drawable selector1;
        @BindDrawable(R.drawable.item_yizhu_selector2)
        Drawable selector2;

        public YiZhuListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public interface OnCheckedChangeListener {
        void onChecked(CompoundButton buttonView, boolean isChecked, int position);
    }

}
