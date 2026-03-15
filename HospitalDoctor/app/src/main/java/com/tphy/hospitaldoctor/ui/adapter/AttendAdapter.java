package com.tphy.hospitaldoctor.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tphy.hospitaldoctor.R;
import com.tphy.hospitaldoctor.ui.bean.AttendenceAdvance;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${王琪} on ${2017/11/28}.
 */

public class AttendAdapter extends RecyclerView.Adapter<AttendAdapter.AttendHolder> {
    private Context context;
    private List<AttendenceAdvance> attendInfors;
    private final LayoutInflater inflater;

    public AttendAdapter(Context context, List<AttendenceAdvance> attendInfors) {
        this.context = context;
        this.attendInfors = attendInfors;
        inflater = LayoutInflater.from(context);
    }

    public void refreshData(List<AttendenceAdvance> attendInfors) {
        this.attendInfors = attendInfors;
        notifyDataSetChanged();
    }

    @Override
    public AttendHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_attend_list, parent, false);
        return new AttendHolder(view);
    }

    @Override
    public void onBindViewHolder(AttendHolder holder, int position) {
        if (position % 2 == 0) {
            holder.linear.setBackgroundColor(holder.itemBg);
        } else {
            holder.linear.setBackgroundColor(holder.colorWhite);
        }
        AttendenceAdvance attendInfor = attendInfors.get(position);
        String czsj = attendInfor.getCzsj();
        String ksdm = attendInfor.getKsdm();
        String swStatus = attendInfor.getSwStatus();
        String swxm = attendInfor.getSwxm();
        String ysdm = attendInfor.getYsdm();
        String xwStatus = attendInfor.getXwStatus();
        String xwxm = attendInfor.getXwxm();
        String wsStatus = attendInfor.getWsStatus();
        String wsxm = attendInfor.getWsxm();
        holder.tv_date.setText(czsj);
        setStatusImg(holder, swStatus, holder.iv_swbz);
        setStatusImg(holder, xwStatus, holder.iv_xwbz);
        setStatusImg(holder, wsStatus, holder.iv_wsbz);
    }

    @Override
    public int getItemCount() {
        return attendInfors.size();
    }

    public class AttendHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.attenLinear)
        LinearLayout linear;
        @BindView(R.id.attend_item_date)
        TextView tv_date;
        @BindView(R.id.item_iv_swbz)
        ImageView iv_swbz;
        @BindView(R.id.item_iv_xwbz)
        ImageView iv_xwbz;
        @BindView(R.id.item_iv_wsbz)
        ImageView iv_wsbz;
        @BindColor(R.color.listItemBg)
        int itemBg;
        @BindColor(android.R.color.white)
        int colorWhite;
        @BindDrawable(R.mipmap.ic_duigou)
        Drawable duigou;
        @BindDrawable(R.mipmap.ic_circle)
        Drawable circle;
        @BindDrawable(R.mipmap.ic_cuocha)
        Drawable cuocha;

        public AttendHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void setStatusImg(AttendHolder holder, String status, ImageView imageView) {
        if (status.equals("上班")) {
            imageView.setBackgroundDrawable(holder.duigou);
        } else if (status.equals("请假")) {
            imageView.setBackgroundDrawable(holder.circle);
        } else if (status.equals("缺勤")) {
            imageView.setBackgroundDrawable(holder.cuocha);
        }
    }

}
