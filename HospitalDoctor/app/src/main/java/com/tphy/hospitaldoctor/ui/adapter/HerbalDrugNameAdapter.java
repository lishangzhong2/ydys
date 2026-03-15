package com.tphy.hospitaldoctor.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tphy.hospitaldoctor.R;

import com.tphy.hospitaldoctor.ui.bean.HerbalDrugName;
import com.tphy.hospitaldoctor.ui.bean.PatientRecipelDetails;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2021-7-28.
 */
public class HerbalDrugNameAdapter extends RecyclerView.Adapter<HerbalDrugNameAdapter.HerbalDrugNameHolder> {
    private Context context;
    private List<HerbalDrugName> HerbalDrugNameList;
    private OnItemClickListener onItemClickListener;

    public HerbalDrugNameAdapter(Context context, List<HerbalDrugName> herbalDrugNameList) {
        this.context = context;
        HerbalDrugNameList = herbalDrugNameList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public HerbalDrugNameHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.frequency_list_yizhu, parent, false);
        return new HerbalDrugNameHolder(view);
    }


    @Override
    public void onBindViewHolder(final HerbalDrugNameHolder holder, final int position) {
        final HerbalDrugName herbalDrugName = HerbalDrugNameList.get(position);
        String mc = herbalDrugName.getYPMC();
        String dw = herbalDrugName.getDW();
        String sl = herbalDrugName.getSL();
        String gg = herbalDrugName.getGG();
//        patientRecipelDetails.get
        holder.frequencyText.setText(mc + "(" + gg + ")" + "\n" + "库存: " + sl);
        if (herbalDrugName.isChecked()){
            holder.frequencyText.setBackgroundResource(R.drawable.bg_blue_checked);
        }else{
            holder.frequencyText.setBackgroundResource(R.drawable.bg_maincard_selector);
        }
        holder.frequencyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(holder.itemView, holder.getAdapterPosition());
                    for (int i = 0; i < HerbalDrugNameList.size(); i++) {
                        if (i == position) {
                            HerbalDrugNameList.get(i).setChecked(true);
                        } else {
                            HerbalDrugNameList.get(i).setChecked(false);
                        }
                    }
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return HerbalDrugNameList.size();
    }


    public class HerbalDrugNameHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.frequencyText)
        TextView frequencyText;
        @BindView(R.id.frequencyLayout)
        ConstraintLayout frequencyLayout;

        public HerbalDrugNameHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
//    public void refreshData(List<PatientRecipelDetails> list) {
//        this.patientRecipels = list;
//        notifyDataSetChanged();
//    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }

    public void setHerbalDrugNameList(List<HerbalDrugName> herbalDrugNameList) {
        HerbalDrugNameList = herbalDrugNameList;
        notifyDataSetChanged();
    }
}