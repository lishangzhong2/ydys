package com.tphy.hospitaldoctor.ui.bean;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tphy.hospitaldoctor.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tphy on 2018/3/23.
 */

public class RecipelDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<PatientRecipelDetails> list;
    private final LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;
    private GridLayoutManager mLayoutManager;
    private int VIEW_TYPE_ONE =1;
    private int VIEW_TYPE_FOUR =4;

    public RecipelDetailsAdapter(Context context, List<PatientRecipelDetails> list,GridLayoutManager mLayoutManager) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
        this.mLayoutManager = mLayoutManager;
    }

    public void refreshData(List<PatientRecipelDetails> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType==VIEW_TYPE_ONE) {
            view = inflater.inflate(R.layout.item_herbaldetails_list, parent, false);
            return new DetailsHolder(view);
        }else {
            view = inflater.inflate(R.layout.herbal_block_list, parent, false);
            return new DetailsFourHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder1, final int position) {

        PatientRecipelDetails patientRecipelDetails = list.get(position);
        String ypmc = patientRecipelDetails.getYPMC();
        String jiliang = patientRecipelDetails.getDOSE();
        String dw = patientRecipelDetails.getDW();
        String bz = patientRecipelDetails.getBZ();
        String pc = patientRecipelDetails.getPC();
        String ph = patientRecipelDetails.getPH();
        String lsj = patientRecipelDetails.getLSJ();
        boolean ischecked = patientRecipelDetails.isIschecked();
        if (holder1.getItemViewType()==VIEW_TYPE_ONE) {
            final DetailsHolder holder = (DetailsHolder) holder1;
            holder.drugName.setText((position + 1) + "." + "\t" + ypmc);
            holder.info.setText("剂量：" + jiliang + dw);
            holder.recipelNo.setText(" ");
            holder.pinyinNo.setText("备注：" + bz);
            holder.pc.setText("批次：" + pc + "\t\t批号：" + ph);
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != onItemClickListener) {
                        onItemClickListener.itemClick(holder.relativeLayout, position);
                    }
                }
            });
            if(list.get(position).getYPMC().equals("")) {
                holder.herbal_pluslist.setVisibility(View.VISIBLE);
                holder.drugName.setVisibility(View.INVISIBLE);
                holder.info.setVisibility(View.INVISIBLE);
                holder.pinyinNo.setVisibility(View.INVISIBLE);
            }else{
                holder.herbal_pluslist.setVisibility(View.INVISIBLE);
                holder.drugName.setVisibility(View.VISIBLE);
                holder.info.setVisibility(View.VISIBLE);
                holder.pinyinNo.setVisibility(View.VISIBLE);
            }
            if (ischecked) {
                holder.relativeLayout.setBackgroundColor(Color.LTGRAY);
            } else {
                holder.relativeLayout.setBackgroundColor(Color.WHITE);
            }
        }else{
            final DetailsFourHolder holder = (DetailsFourHolder) holder1;
            if(!bz.equals("")) {
                holder.block_bz.setText("(" + bz + ")");
            }else{
                holder.block_bz.setText(bz);
            }
            holder.block_jldw.setText(jiliang+dw);
            holder.block_drugname.setText(ypmc);
            holder.block_relative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != onItemClickListener) {
                        onItemClickListener.itemClick(holder.block_relative, position);
                    }
                }
            });
            if(list.get(position).getYPMC().equals("")) {
                holder.herbal_plus.setVisibility(View.VISIBLE);
                holder.block_bz.setVisibility(View.INVISIBLE);
                holder.block_drugname.setVisibility(View.INVISIBLE);
                holder.block_jldw.setVisibility(View.INVISIBLE);
            }else{
                holder.herbal_plus.setVisibility(View.INVISIBLE);
                holder.block_bz.setVisibility(View.VISIBLE);
                holder.block_drugname.setVisibility(View.VISIBLE);
                holder.block_jldw.setVisibility(View.VISIBLE);
            }
            if (ischecked) {
                holder.block_relative.setBackgroundColor(Color.LTGRAY);
            } else {
                holder.block_relative.setBackgroundColor(Color.WHITE);
            }

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DetailsHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_herbal_drugName)
        TextView drugName;
        @BindView(R.id.item_herbal_info)
        TextView info;
        @BindView(R.id.item_herbal_recipelNo)
        TextView recipelNo;
        @BindView(R.id.item_herbal_pinyinNo)
        TextView pinyinNo;
        @BindView(R.id.item_herbal_pc)
        TextView pc;
        @BindView(R.id.relative)
        RelativeLayout relativeLayout;
        @BindColor(R.color.listItemBg)
        int itemBg;
        @BindColor(android.R.color.white)
        int colorWhite;
        @BindView(R.id.herbal_iv_pluslist)
        ImageView herbal_pluslist;

        public DetailsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class DetailsFourHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.block_bz)
        TextView block_bz;
        @BindView(R.id.block_drugname)
        TextView block_drugname;
        @BindView(R.id.block_jldw)
        TextView block_jldw;
        @BindView(R.id.block_relative)
        RelativeLayout block_relative;
        @BindView(R.id.herbal_iv_plus)
        ImageView herbal_plus;
        public DetailsFourHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void itemClick(View view, int position);
    }

    public void updateList(List<PatientRecipelDetails> patientRecipels) {
        this.list= patientRecipels;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {

        int spanCount = mLayoutManager.getSpanCount();

        if (spanCount == VIEW_TYPE_ONE) {
            return VIEW_TYPE_ONE;
        } else {
            return VIEW_TYPE_FOUR;
        }
    }
}
