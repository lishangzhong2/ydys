package com.tphy.hospitaldoctor.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tphy.hospitaldoctor.R;
import com.tphy.hospitaldoctor.ui.bean.CommonDrugChild;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tphy on 2018/3/22.
 */

public class RecipelChildAdapter extends RecyclerView.Adapter<RecipelChildAdapter.RecipelChildHolder> {
    private List<CommonDrugChild> list;
    private final LayoutInflater inflater;
    private OnCheckedListener onCheckedListener;

    public RecipelChildAdapter(Context context, List<CommonDrugChild> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    public void refreshData(List<CommonDrugChild> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public List<CommonDrugChild> getCheckedItem() {
        List<CommonDrugChild> checkedList = new ArrayList<>();
        for (int i = 0; i < this.list.size(); i++) {
            CommonDrugChild commonDrugChild = this.list.get(i);
            if (commonDrugChild.isChecked()) {
                checkedList.add(commonDrugChild);
            }
        }
        return checkedList;
    }

    public void setOnCheckedListener(OnCheckedListener onCheckedListener) {
        this.onCheckedListener = onCheckedListener;
    }

    @Override
    public RecipelChildHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_herbalchild_list, parent, false);
        return new RecipelChildHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecipelChildHolder holder, final int position) {
        CommonDrugChild commonDrugChild = list.get(position);
        boolean checked = commonDrugChild.isChecked();
        String ypmc = commonDrugChild.getYPMC();
        String zcycf_ma_code = commonDrugChild.getZCYCF_MA_CODE();
        String sxh = commonDrugChild.getSXH();
        String gyplmc = commonDrugChild.getGYPLMC();
        String ypyfmc = commonDrugChild.getYPYFMC();
        String jyfsmc = commonDrugChild.getJYFSMC();
        String fyyqmc = commonDrugChild.getFYYQMC();
        String fs = commonDrugChild.getFS();
        String yszt = commonDrugChild.getYSZT();
        boolean isPicked = commonDrugChild.isPicked();
        holder.drugName.setText(sxh + "\t" + ypmc);
        holder.info.setText(gyplmc + "\t\t" + ypyfmc + "\t\t" + jyfsmc + "\t\t" + fyyqmc + "\t\t付数：" + fs + "\t\t" + yszt);
        holder.recipelNo.setText("处方编码："+zcycf_ma_code);
        holder.checkbox.setChecked(checked);
        holder.checkbox.setEnabled(!isPicked);
        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onCheckedListener) {
                    onCheckedListener.checked(holder.checkbox, position,holder.checkbox.isChecked());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecipelChildHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_herbal_checkbox)
        CheckBox checkbox;
        @BindView(R.id.item_herbal_drugName)
        TextView drugName;
        @BindView(R.id.item_herbal_info)
        TextView info;
        @BindView(R.id.item_herbal_recipelNo)
        TextView recipelNo;
        @BindView(R.id.item_herbal_pinyinNo)
        TextView pinyinNo;
        @BindView(R.id.relative)
        RelativeLayout relativeLayout;
        @BindColor(R.color.listItemBg)
        int itemBg;
        @BindColor(android.R.color.white)
        int colorWhite;

        public RecipelChildHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnCheckedListener {
        void checked(View view, int position,boolean ischecked);
    }

}
