package com.tphy.hospitaldoctor.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tphy.hospitaldoctor.R;
import com.tphy.hospitaldoctor.ui.bean.XiJunReport;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by ${王琪} on ${2017/11/28}.
 */

public class XiJunReportAdapter extends RecyclerView.Adapter<XiJunReportAdapter.XiJunReportHolder> {
    private Context context;
    private List<XiJunReport> xiJunReports;


    public XiJunReportAdapter(Context context, List<XiJunReport> xiJunReportList) {
        this.context = context;
        this.xiJunReports = xiJunReportList;
    }

    @Override
    public XiJunReportHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_xijun_list, parent, false);
        return new XiJunReportHolder(view);
    }

    @Override
    public void onBindViewHolder(XiJunReportHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return xiJunReports.size();
    }

    public class XiJunReportHolder extends RecyclerView.ViewHolder {

        public XiJunReportHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
