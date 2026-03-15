package com.tphy.hospitaldoctor.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tphy.hospitaldoctor.R;
import com.tphy.hospitaldoctor.ui.bean.JiXiaoBean;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class JixiaoAdapter extends RecyclerView.Adapter<JixiaoAdapter.JixiaoHolder> {

    private List<JiXiaoBean> jiXiaoBeanList;
    private LayoutInflater inflater;
    private Context context;

    public JixiaoAdapter(Context context, List<JiXiaoBean> jiXiaoBeanList) {
        this.context = context;
        this.jiXiaoBeanList = jiXiaoBeanList;
        inflater = LayoutInflater.from(context);
    }

    public void updateData(List<JiXiaoBean> jiXiaoBeanList) {
        this.jiXiaoBeanList = jiXiaoBeanList;
        notifyDataSetChanged();
    }

    @Override
    public JixiaoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_jianyan_list, parent, false);
        return new JixiaoHolder(view);
    }

    @Override
    public void onBindViewHolder(JixiaoHolder holder, int position) {
        if (position % 2 == 0) {
            holder.linear.setBackgroundColor(holder.colorWhite);
        } else {
            holder.linear.setBackgroundColor(holder.itemBg);
        }
        JiXiaoBean jiXiaoBean = jiXiaoBeanList.get(position);
        String khxm = jiXiaoBean.getKHXM();
        String sjz = jiXiaoBean.getSJZ();
        String bzz = jiXiaoBean.getBZZ();
        String zbbz = jiXiaoBean.getZBBZ();
        String srm = jiXiaoBean.getSRM();
        if (sjz.equals("--")) {
            holder.tv_ZWMC.setText(khxm);
            holder.tv_JG.setText( sjz);
            holder.tv_DW.setText(bzz);
            holder.tv_RANGE.setText("--");
        } else {
            if (!TextUtils.isEmpty(sjz) && !TextUtils.isEmpty(bzz)) {
                double shijizhi = Double.parseDouble(sjz);
                double biaozhuzhi = Double.parseDouble(bzz);
                BigDecimal sjDecimal = new BigDecimal(shijizhi);
                BigDecimal bzDecimal = new BigDecimal(biaozhuzhi);
                int i = sjDecimal.compareTo(bzDecimal);
                if (srm.equals("0")) {
                    if (i < 0) {
                        holder.tv_JG.setTextColor(context.getResources().getColor(R.color.textRed));
                    } else if (i == 0) {
                        holder.tv_JG.setTextColor(context.getResources().getColor(R.color.textBlack));
                    } else {
                        holder.tv_JG.setTextColor(context.getResources().getColor(R.color.textGreen));
                    }
                } else {
                    if (i < 0) {
                        holder.tv_JG.setTextColor(context.getResources().getColor(R.color.textGreen));
                    } else if (i == 0) {
                        holder.tv_JG.setTextColor(context.getResources().getColor(R.color.textBlack));
                    } else {
                        holder.tv_JG.setTextColor(context.getResources().getColor(R.color.textRed));
                    }
                }
            }
            String flag = "";
            if (zbbz.equals("2") || zbbz.equals("3")) {
                flag = "%";
            }
            String words = "";
            switch (zbbz) {
                case "1":
                    words = "量指标";
                    break;
                case "2":
                    words = "比指标";
                    break;
                case "3":
                    words = "率指标";
                    break;
                case "4":
                    words = "药房指标";
                    break;
            }
            holder.tv_ZWMC.setText(khxm);
            holder.tv_JG.setText(String.format("%s%s", sjz, flag));
            holder.tv_DW.setText(String.format("%s%s", bzz, flag));
            holder.tv_RANGE.setText(words);
        }

        holder.tv_ZT.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return jiXiaoBeanList.size();
    }

    public class JixiaoHolder extends RecyclerView.ViewHolder {
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

        public JixiaoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
