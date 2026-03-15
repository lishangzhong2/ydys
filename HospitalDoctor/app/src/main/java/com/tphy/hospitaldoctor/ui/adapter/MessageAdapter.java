package com.tphy.hospitaldoctor.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tphy.hospitaldoctor.R;
import com.tphy.hospitaldoctor.ui.bean.MessageInfo;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${王琪} on ${2017/11/28}.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {

    private Context context;
    private List<MessageInfo> messageInfos;
    private OnItemClickListener onItemClickListener;

    public MessageAdapter(Context context, List<MessageInfo> messageInfos) {
        this.context = context;
        this.messageInfos = messageInfos;
    }

    public void refreshData(List<MessageInfo> messageInfos) {
        this.messageInfos = messageInfos;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_audio_list, parent, false);
        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(final MessageHolder holder, final int position) {
        if (position % 2 == 0) {
            holder.bw_ll.setBackgroundColor(holder.colorWhite);
        } else {
            holder.bw_ll.setBackgroundColor(holder.itemBg);
        }
        MessageInfo messageInfo = messageInfos.get(position);
        String msg_mc = messageInfo.getMSG_MC();
        String msg_fbsj = messageInfo.getMSG_FBSJ();
        String msg_fsr = messageInfo.getYSMC();
        holder.tv_topic.setText(msg_mc);
        holder.tv_time.setText(msg_fbsj);
        holder.tv_bz.setText(msg_fsr);
        holder.bw_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onItemClickListener) {
                    onItemClickListener.onItemClick(holder.bw_ll,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return messageInfos.size();
    }

    public class MessageHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.audio_item_topic)
        TextView tv_topic;
        @BindView(R.id.audio_item_bz)
        TextView tv_bz;
        @BindView(R.id.audio_item_time)
        TextView tv_time;
        @BindView(R.id.audio_item_type)
        TextView tv_type;
        @BindView(R.id.item_bw_ll)
        RelativeLayout bw_ll;
        @BindColor(R.color.listItemBg)
        int itemBg;
        @BindColor(android.R.color.white)
        int colorWhite;

        public MessageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}

