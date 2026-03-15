package com.tphy.hospitaldoctor.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tphy.hospitaldoctor.R;
import com.tphy.hospitaldoctor.ui.bean.PatientRecipel;

import java.util.List;

public class PatientRecipleAdapter extends BaseAdapter {
    private Context context;
    private List<PatientRecipel> patientRecipels;
    private LayoutInflater layoutInflater;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PatientRecipleAdapter(String name) {
        this.name = name;
    }

    public PatientRecipleAdapter(Context context, List<PatientRecipel> patientRecipels) {
        this.context = context;
        this.patientRecipels = patientRecipels;
        this.layoutInflater = LayoutInflater.from(context);
    }
    public void refreshData(List<PatientRecipel> list) {
        this.patientRecipels = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return patientRecipels.size();
    }

    @Override
    public Object getItem(int position) {
        return patientRecipels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        PatientRecipel herbalPatientRecipel = patientRecipels.get(position);
        String cfzt1 = herbalPatientRecipel.getCFZT();
        String yfcfzt = herbalPatientRecipel.getYFCFZT()==null?"":herbalPatientRecipel.getYFCFZT();
        boolean herbalChecked = herbalPatientRecipel.isHerbalChecked();
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.yizhu_chufangliebiao_list, parent, false);
            holder = new Holder();
            holder.textView = convertView.findViewById(R.id.cflbText);
            holder.cflbText= convertView.findViewById(R.id.cflbState);
            holder.cflbColor = convertView.findViewById(R.id.cflbColor);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        if (herbalChecked) {
            holder.textView.setBackgroundColor(Color.LTGRAY);
            holder.cflbText.setBackgroundColor(Color.LTGRAY);
        } else {
            holder.textView.setBackgroundColor(Color.WHITE);
            holder.cflbText.setBackgroundColor(Color.WHITE);
        }
        if(yfcfzt.equals("发药")) {
            holder.cflbText.setText("发药");
            holder.cflbColor.setBackgroundColor(Color.BLUE);
        }else{
            if(cfzt1.equals("2")) {
                holder.cflbText.setText("提交");
                holder.cflbColor.setBackgroundColor(Color.GREEN);
            } else{
                holder.cflbText.setText("保存");
                holder.cflbColor.setBackgroundColor(Color.YELLOW);
            }
        }
        PatientRecipel patientRecipel = patientRecipels.get(position);
        String cfzt = patientRecipel.getCFZT();
        String cfsj = patientRecipel.getCFSJ();
        holder.textView.setText(cfsj);
//        if (position == 0) {
//            listView.performItemClick(listView.getChildAt(position), position, listView.getItemIdAtPosition(position));
//        }
        return convertView;
    }

    public void updateList(List<PatientRecipel> patientRecipels) {
        this.patientRecipels = patientRecipels;
        notifyDataSetChanged();
    }

    public class Holder {
        TextView textView;
        TextView cflbText;
        TextView cflbColor;
    }

}
