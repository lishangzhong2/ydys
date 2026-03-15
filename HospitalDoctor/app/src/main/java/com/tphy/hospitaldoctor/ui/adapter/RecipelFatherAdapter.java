package com.tphy.hospitaldoctor.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.tphy.hospitaldoctor.R;
import com.tphy.hospitaldoctor.ui.bean.CommonDrugFather;

import java.util.List;

/**
 * Created by tphy on 2018/3/22.
 */

public class RecipelFatherAdapter extends BaseAdapter {
    private Context context;
    private List<CommonDrugFather> commonDrugFathers;
    private final LayoutInflater inflater;
//    private OnItemClickListener onItemClickListener;

    public RecipelFatherAdapter(Context context, List<CommonDrugFather> commonDrugFathers) {
        this.context = context;
        this.commonDrugFathers = commonDrugFathers;
        inflater = LayoutInflater.from(context);
    }

//    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//        this.onItemClickListener = onItemClickListener;
//    }

    public void refreshData(List<CommonDrugFather> list) {
        commonDrugFathers = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return commonDrugFathers.size();
    }

    @Override
    public Object getItem(int position) {
        return commonDrugFathers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;
        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.simple_list_item, parent, false);
            holder = new ViewHolder();
            holder.checkBox = convertView.findViewById(R.id.simple_checkbox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CommonDrugFather commonDrugFather = commonDrugFathers.get(position);
        boolean selected = commonDrugFather.isSelected();
        holder.checkBox.setChecked(selected);
        String cycfmc = commonDrugFather.getCYCFMC();
        holder.checkBox.setText(cycfmc);
//        parent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != onItemClickListener) {
//                    onItemClickListener.itemClick(parent,position);
//                }
//            }
//        });
        return convertView;
    }

    public class ViewHolder {
        CheckBox checkBox;
    }

//    public interface OnItemClickListener {
//        void itemClick(View view, int position);
//    }

}
