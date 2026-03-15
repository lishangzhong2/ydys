package com.tphy.hospitaldoctor.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tphy.hospitaldoctor.R;
import com.tphy.hospitaldoctor.ui.bean.FreqencyDict;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2021-7-13.
 */

public class FrequencyListAdapter extends RecyclerView.Adapter<FrequencyListAdapter.FrequencyListHolder>{
    private Context context;
    private List<FreqencyDict> FrequencyDicts;
    private OnItemClickListener onItemClickListener;

    public FrequencyListAdapter(Context context, List<FreqencyDict> frequencyDicts) {
        this.context = context;
        FrequencyDicts = frequencyDicts;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public FrequencyListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.frequency_list_yizhu, parent, false);
        return new FrequencyListHolder(view);
    }


    @Override
    public void onBindViewHolder(final FrequencyListHolder holder, final int position) {
        final FreqencyDict freqencyDict = FrequencyDicts.get(position);
        String mc1 = freqencyDict.getMC1();
        holder.frequencyText.setText(mc1);
        if (freqencyDict.isIschecked()){
            holder.frequencyText.setBackgroundResource(R.drawable.bg_blue_checked);
        }else{
            holder.frequencyText.setBackgroundResource(R.drawable.bg_maincard_selector);
        }
        holder.frequencyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null) {
                    onItemClickListener.onClick(holder.itemView,holder.getAdapterPosition());
                    for(int i = 0;i<FrequencyDicts.size();i++){
                        if(i==position){
                            FrequencyDicts.get(i).setIschecked(true);
                        }else{
                            FrequencyDicts.get(i).setIschecked(false);
                        }
                    }
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return FrequencyDicts.size();
    }


    public class FrequencyListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.frequencyText)
        TextView frequencyText;
        @BindView(R.id.frequencyLayout)
        ConstraintLayout frequencyLayout;
        public FrequencyListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }

    public void setFrequencyDicts(List<FreqencyDict> frequencyDicts) {
        FrequencyDicts = frequencyDicts;
        notifyDataSetChanged();
    }

    public int getChooseFrequencyPosition() {
        for(int i = 0;i<FrequencyDicts.size();i++){
            if(FrequencyDicts.get(i).isIschecked()){
                return  i;
            }
        }
        return 0;
    }
}
