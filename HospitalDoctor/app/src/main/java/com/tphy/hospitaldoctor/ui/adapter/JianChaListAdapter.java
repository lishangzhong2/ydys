package com.tphy.hospitaldoctor.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tphy.hospitaldoctor.R;
import com.tphy.hospitaldoctor.ui.bean.JianChaInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class JianChaListAdapter extends RecyclerView.Adapter<JianChaListAdapter.JianChaListHolder> {
    private Context context;
    private List<JianChaInfo> jianChaInfos;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public JianChaListAdapter(Context context, List<JianChaInfo> jianChaInfos) {
        this.context = context;
        this.jianChaInfos = jianChaInfos;
    }

    public void refreshData(List<JianChaInfo> jianChaInfoList) {
        this.jianChaInfos = jianChaInfoList;
        notifyDataSetChanged();
    }

    @Override

    public JianChaListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_jiancha_list, parent, false);
        return new JianChaListHolder(view);
    }

    @Override
    public void onBindViewHolder(final JianChaListHolder holder, final int position) {
        JianChaInfo jianChaInfo = jianChaInfos.get(position);
        String exam_name = jianChaInfo.getEXAM_NAME();
        boolean checked = jianChaInfo.isChecked();
        if (exam_name.equals("")) {
            exam_name = "未命名";
        }
        //holder.textView.setChecked(checked);
        if (checked){
            holder.ll_title.setBackground(context.getResources().getDrawable(R.drawable.menu_item_checked));
        }else {
            holder.ll_title.setBackground(context.getResources().getDrawable(R.drawable.menu_item_normal));
        }
        holder.tv_mc.setText(exam_name);
        holder.tv_sqsj.setText(jianChaInfo.getEXAM_DATE_TIME());

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //  onClicks(holder.textView,position);
                if (null != onItemClickListener) {
                    onItemClickListener.onClick(holder.textView, position);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return jianChaInfos.size();
    }

    public class JianChaListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.jiancha_title)
        CheckBox textView;
        @BindView(R.id.jiancha_title1)
        TextView tv_mc;
        @BindView(R.id.item_tv_reportDate)
        TextView tv_sqsj;
        @BindView(R.id.ll_jianitem)
        LinearLayout ll_title;


        public JianChaListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }

}
