package com.tphy.hospitaldoctor.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tphy.hospitaldoctor.R;
import com.tphy.hospitaldoctor.ui.bean.CaDoBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: wangchixaing
 * Date: 2020/09/29
 * Time: 14:41
 */

public class YzbgItemAdapter extends RecyclerView.Adapter<YzbgItemAdapter.SFViewHolder> {

    private List<CaDoBean>  list;
    private Context context;

    private  OnItemChangedListener onItemChangedListener;

    public void setOnItemChangedListener(OnItemChangedListener onItemChangedListener) {
        this.onItemChangedListener = onItemChangedListener;
    }

    public YzbgItemAdapter(List<CaDoBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public YzbgItemAdapter.SFViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.nursing_sfmx_item, parent, false);
        return new SFViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull final YzbgItemAdapter.SFViewHolder holder, final int position) {
        final CaDoBean sBean = list.get(position);

        holder.tvsl.setVisibility(View.VISIBLE);
        holder.tvbx.setVisibility(View.GONE);
        holder.mNusingadapterSl.setVisibility(View.GONE);
        //yyyy-mm-dd hh:mi:ss
        holder.tvsl.setText(sBean.getYzsj().substring(5,16).replace(" ","\n"));
        holder.cb_bx.setVisibility(View.GONE);

        if (sBean.getZxhs().equals("")){
            holder.mNusingadapterXmmc.setTextColor(Color.RED);
        }else {
            holder.mNusingadapterXmmc.setTextColor(Color.BLUE);
        }
        holder.mCbChecked.setChecked(sBean.isChecked());
        holder.mNusingadapterXmmc.setText(Html.fromHtml(sBean.getYzmc()));

        holder.mCbChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               checkedPosition(sBean,position,holder);
            }
        });
        holder.mNusingadapterXmmc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedPosition(sBean,position,holder);
            }
        });


    }

    private void checkedPosition(CaDoBean sBean, int position, SFViewHolder holder) {
        for (int i = 0; i < list.size(); i++) {
            CaDoBean bean = list.get(i);
            if (bean.getYzh().equals(sBean.getYzh()) &&
                    bean.getZyh().equals(sBean.getZyh())){
                bean.setChecked(holder.mCbChecked.isChecked());
            }
        }
        if (null!=onItemChangedListener){
            onItemChangedListener.onChanged(sBean.getZyh()+sBean.getYzh());
        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateList(ArrayList<CaDoBean> careSFInfoBeans) {
        this.list = careSFInfoBeans;
        notifyDataSetChanged();

    }

    public void setAllChecked(boolean allChecked) {
        for (int i = 0; i <  this.list.size(); i++) {
            this.list.get(i).setChecked(allChecked);
        }
        notifyDataSetChanged();
    }

    public class SFViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cb_checked)
        CheckBox mCbChecked;

        @BindView(R.id.nusingadapter_xmmc)
        TextView mNusingadapterXmmc;

        @BindView(R.id.nusingadapter_sl)
        TextView mNusingadapterSl;

        @BindView(R.id.tvsl)
        TextView tvsl;

        @BindView(R.id.tvbx)
        TextView tvbx;

        @BindView(R.id.item_linear)
        RelativeLayout mItemLinear;

        @BindView(R.id.cb_bx)
        CheckBox cb_bx;


        public SFViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }



    }

    public List<CaDoBean> getallCheckedlist(){
        ArrayList<CaDoBean> careSFInfoBeans = new ArrayList<>();
        for (CaDoBean be:list) {
            if (be.isChecked()){
                careSFInfoBeans.add(be);
            }
        }
        return careSFInfoBeans;
    }

    public List<CaDoBean> getalllist(){
      return this.list;
    }

    public  interface  OnItemChangedListener {
        void  onChanged(String yzkey);
    }
}
