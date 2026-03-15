package com.tphy.hospitaldoctor.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tphy.hospitaldoctor.R;
import com.tphy.hospitaldoctor.ui.bean.AudioInfor;
import com.tphy.hospitaldoctor.ui.bean.FreqencyDict;
import com.tphy.hospitaldoctor.views.SwipeDragLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2021-7-9.
 */

public class FrequencyAdapter extends BaseAdapter{
    private Context context;
    private List<FreqencyDict> FrequencyDicts;


    public FrequencyAdapter(Context context, List<FreqencyDict> frequencyInfors) {
        this.context = context;
        this.FrequencyDicts = frequencyInfors;
    }

    @Override
    public int getCount() {
        return FrequencyDicts.size();
    }

    @Override
    public Object getItem(int position) {
        return FrequencyDicts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View inflate = LayoutInflater.from(context).inflate(R.layout.item_yizhu_offenuse, parent, false);
        TextView frequentMC = inflate.findViewById(R.id.FrequentMC);
        frequentMC.setText(FrequencyDicts.get(position).getMC1());
        return inflate;
    }

    public void setFrequencyDicts(List<FreqencyDict> frequencyDicts) {
        FrequencyDicts = frequencyDicts;
        notifyDataSetChanged();
    }
}
