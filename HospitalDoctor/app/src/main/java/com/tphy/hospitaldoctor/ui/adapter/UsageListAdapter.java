package com.tphy.hospitaldoctor.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.tphy.hospitaldoctor.R;
import com.tphy.hospitaldoctor.ui.bean.UsageDict;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2021-7-13.
 */

    public class UsageListAdapter extends RecyclerView.Adapter<UsageListAdapter.UsageListHolder>{
        private Context context;
        private List<UsageDict> UsageDicts;
        private OnItemClickListener onItemClickListener;


        public UsageListAdapter(Context context, List<UsageDict> usageDicts) {
            this.context = context;
            UsageDicts = usageDicts;
        }
        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public UsageListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.frequency_list_yizhu, parent, false);
            return new UsageListHolder(view);
        }

        @Override
        public void onBindViewHolder(final UsageListHolder holder, final int position) {
            final UsageDict usageDict = UsageDicts.get(position);
            final String mc = usageDict.getMC();
            holder.frequencyText.setText(mc);
            if (usageDict.isIschecked()){
                holder.frequencyText.setBackgroundResource(R.drawable.bg_blue_checked);
            }else{
                holder.frequencyText.setBackgroundResource(R.drawable.bg_maincard_selector);
            }
            holder.frequencyText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onClick(holder.itemView, holder.getAdapterPosition());
                        for (int i = 0; i < UsageDicts.size(); i++) {
                            if (i==position){
                                UsageDicts.get(i).setIschecked(true);
                            }else {
                                UsageDicts.get(i).setIschecked(false);
                            }
                        }
                        notifyDataSetChanged();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return UsageDicts.size();
        }


        public class UsageListHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.frequencyText)
            TextView frequencyText;
            public UsageListHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

        }

        public interface OnItemClickListener {
            void onClick(View view, int position);
        }

    public void setUsageDicts(List<UsageDict> usageDicts) {
        UsageDicts = usageDicts;
        notifyDataSetChanged();
    }

    public int getChooseUsagePosition() {
        for(int i = 0;i<UsageDicts.size();i++){
            if(UsageDicts.get(i).isIschecked()){
                return  i;
            }
        }
        return 0;
    }
}


