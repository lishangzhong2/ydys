package com.tphy.hospitaldoctor.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tphy.hospitaldoctor.R;
import com.tphy.hospitaldoctor.ui.bean.PatientInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainCardAdapter extends RecyclerView.Adapter<MainCardAdapter.MainCardHolder> {
    private Context context;
    private List<PatientInfo> patientInfos;
    private OnItemClickListener onItemClickListener;

    public MainCardAdapter(Context context, List<PatientInfo> patientInfos) {
        this.context = context;
        this.patientInfos = patientInfos;
    }

    public void refreshDate(List<PatientInfo> patientInfos) {
        this.patientInfos = patientInfos;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MainCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_card_list, parent, false);
        return new MainCardHolder(view);
    }

    @Override
    public void onBindViewHolder(final MainCardHolder holder, final int position) {
        PatientInfo patientInfo = patientInfos.get(position);
        String cwh = patientInfo.getCwh();
        String hljb = patientInfo.getHljb();
        String hzxm = patientInfo.getHzxm();
        String hzxb = patientInfo.getHzxb();
        String ryzd = patientInfo.getRyzd();
        String yllx = patientInfo.getYllx();
        boolean selected = patientInfo.isSelected();
        holder.card_bg.setSelected(selected);
        holder.tv_cwh.setText(cwh);
        holder.tv_hzxm.setText(hzxm);
        holder.tv_ryzd.setText(ryzd);

        switch (hzxb) {
            case "男":
                holder.iv_gender.setBackground(context.getResources().getDrawable(R.mipmap.ic_male));
                break;
            case "女":
                holder.iv_gender.setBackground(context.getResources().getDrawable(R.mipmap.ic_female));
                break;
        }
        switch (hljb) {
            case "一级护理":
                holder.iv_level.setBackground(context.getResources().getDrawable(R.mipmap.level_one_ver));
                break;
            case "二级护理":
                holder.iv_level.setBackground(context.getResources().getDrawable(R.mipmap.level_two_ver));
                break;
            case "三级护理":
                holder.iv_level.setBackground(context.getResources().getDrawable(R.mipmap.level_three_ver));
                break;
            case "四级护理":
                holder.iv_level.setBackground(context.getResources().getDrawable(R.mipmap.level_special_ver));
                break;
            case "":
                holder.iv_level.setBackground(null);
                break;
        }
        if (yllx.contains("自费")) {
            holder.tv_yllx.setText("自费");
        } else if (yllx.contains("职工")) {
            holder.tv_yllx.setText("职工");
        } else if (yllx.contains("居民")) {
            holder.tv_yllx.setText("居民");
        }
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onItemClickListener) {
                    onItemClickListener.onClick(holder.itemLayout, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return patientInfos.size();
    }

    public class MainCardHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.main_card_item)
        LinearLayout itemLayout;
        @BindView(R.id.main_card_bg)
        LinearLayout card_bg;
        @BindView(R.id.tv_cwh)
        TextView tv_cwh;
        @BindView(R.id.tv_hzxm)
        TextView tv_hzxm;
        @BindView(R.id.tv_ryzd)
        TextView tv_ryzd;
        @BindView(R.id.tv_yllx)
        TextView tv_yllx;
        @BindView(R.id.iv_hljb)
        ImageView iv_level;
        @BindView(R.id.iv_gender)
        ImageView iv_gender;

        public MainCardHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }


}
