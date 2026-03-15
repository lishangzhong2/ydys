package com.tphy.hospitaldoctor.ui.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tphy.hospitaldoctor.R;
import com.tphy.hospitaldoctor.ui.bean.DesageDict;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2021-7-20.
 */

public class DesageListAdapter extends RecyclerView.Adapter<DesageListAdapter.DesageListHolder>{
    private Context context;
    private List<DesageDict> DesageDicts;
    private OnItemClickListener onItemClickListener;

    public DesageListAdapter(Context context, List<DesageDict> desageDicts) {
        this.context = context;
        DesageDicts = desageDicts;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public DesageListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.frequency_list_yizhu, parent, false);
        return new DesageListHolder(view);
    }


    @Override
    public void onBindViewHolder(final DesageListHolder holder, final int position) {
        final DesageDict desageDict = DesageDicts.get(position);
        String jl = desageDict.getJLDM();
        holder.frequencyText.setText(jl);
        if (desageDict.isIschecked()){
            holder.frequencyText.setBackgroundResource(R.drawable.bg_blue_checked);
        }else{
            holder.frequencyText.setBackgroundResource(R.drawable.bg_maincard_selector);
        }
        holder.frequencyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null) {
                    onItemClickListener.onClick(holder.itemView,holder.getAdapterPosition());
                    for(int i = 0;i<DesageDicts.size();i++){
                        if(i==position){
                            DesageDicts.get(i).setIschecked(true);
                        }else{
                            DesageDicts.get(i).setIschecked(false);
                        }
                    }
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return DesageDicts.size();
    }


    public class DesageListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.frequencyText)
        TextView frequencyText;
        @BindView(R.id.frequencyLayout)
        ConstraintLayout frequencyLayout;
        public DesageListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }

    public void setDesageDicts(List<DesageDict> desageDicts) {
        DesageDicts = desageDicts;
        notifyDataSetChanged();
    }

    public int getChooseDesagePosition() {
        for(int i = 0;i<DesageDicts.size();i++){
            if(DesageDicts.get(i).isIschecked()){
                return  i;
            }
        }
        return 0;
    }
}
