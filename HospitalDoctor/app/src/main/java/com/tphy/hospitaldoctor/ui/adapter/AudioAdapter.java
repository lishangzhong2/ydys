package com.tphy.hospitaldoctor.ui.adapter;

import android.bluetooth.BluetoothA2dp;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tphy.hospitaldoctor.R;
import com.tphy.hospitaldoctor.ui.activity.MainActivity;
import com.tphy.hospitaldoctor.ui.bean.AudioInfor;
import com.tphy.hospitaldoctor.views.SwipeDragLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${王琪} on ${2017/11/28}.
 */

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.AudioHolder> {

    private Context context;
    private List<AudioInfor> audioInfors;
    private OnItemClickListener onItemClickListener;
    private OnItemFinishClickListener onItemFinishClickListener;
    private OnItemDeleteClickListener onItemDeleteClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public AudioAdapter(Context context, List<AudioInfor> audioInfors) {
        this.context = context;
        this.audioInfors = audioInfors;
    }

    public void refreshData(List<AudioInfor> list) {
        this.audioInfors = list;
        notifyDataSetChanged();
    }

    @Override
    public AudioHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_audio_list, parent, false);
        return new AudioHolder(view);
    }

    @Override
    public void onBindViewHolder(final AudioHolder holder, final int position) {
        final AudioInfor audioInfor = audioInfors.get(position);
        String yymc = audioInfor.getYYMC();
        String bz = audioInfor.getBZ();
        String yylx = audioInfor.getYYLX();
        String lrsj = audioInfor.getLRSJ();
        String yynr = audioInfor.getYYNR();
        char state = audioInfor.getSTATE();
        if(state == '1') {
            holder.audio_item_state.setBackgroundColor(Color.BLUE);
        }
        else{
            holder.audio_item_state.setBackgroundColor(Color.YELLOW);
        }
        switch (yylx) {
            case "tt":
                holder.tv_type.setText("文字备忘");
                holder.tv_type.setTextColor(Color.parseColor("#4bb2cc"));
                break;
            case "tp":
                holder.tv_type.setText("图片备忘");
                holder.tv_type.setTextColor(Color.parseColor("#4bb2cc"));
                break;
            case "yy":
                holder.tv_type.setText("语音备忘");
                holder.tv_type.setTextColor(Color.parseColor("#4b66cc"));
                break;
        }
        holder.tv_topic.setText(yymc);
        holder.tv_bz.setText(bz);
        holder.tv_time.setText(lrsj);
//        holder.audio_item_state.setText(state+"");
        holder.bw_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onItemClickListener) {
                    onItemClickListener.onClick(holder.bw_ll, position);
                }
            }
        });
        //holder.swipeDragLayout.close();
        holder.swipeDragLayout.setSwipeDirection(SwipeDragLayout.DIRECTION_LEFT);

        holder.swipetv_de.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (null!=onItemDeleteClickListener) {
                    Log.e("wcx", "onClick: "+position );
                    onItemDeleteClickListener.onClick1(v, position);
                    MainActivity mainActivity = new MainActivity();
                    mainActivity.setChangeBeiWang(true);
                }
            }
        });
        holder.finish_bw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (null!=onItemFinishClickListener) {
                    Log.e("wcx", "onClick: "+position );
                    onItemFinishClickListener.onClick2(v, position);
                    MainActivity mainActivity = new MainActivity();
                    mainActivity.setChangeBeiWang(true);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return audioInfors.size();
    }
    public void setOnItemDeleteClickListener(OnItemDeleteClickListener onItemDeleteClickListener) {
        this.onItemDeleteClickListener = onItemDeleteClickListener;
    }
    public void setOnItemFinishClickListener(OnItemFinishClickListener onItemFinishClickListener) {
        this.onItemFinishClickListener = onItemFinishClickListener;
    }
    public class AudioHolder extends RecyclerView.ViewHolder {
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
        @BindView(R.id.swipeDrag)
        SwipeDragLayout swipeDragLayout;
        @BindView(R.id.swipetv)
        TextView swipetv_de;
        @BindView(R.id.finish_bw)
        TextView finish_bw;
        @BindView(R.id.audio_item_state)
        TextView audio_item_state;
//        @BindColor(R.color.listItemBg)
//        int itemBg;
//        @BindColor(android.R.color.white)
//        int colorWhite;
        public AudioHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }


    public interface OnItemClickListener {
        void onClick(View view, int position);
    }

    public interface  OnItemFinishClickListener{
        void onClick2(View view,int position);
    }
    public interface  OnItemDeleteClickListener{
        void onClick1(View view,int position);
    }

}
