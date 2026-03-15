package com.tphy.hospitaldoctor.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tphy.hospitaldoctor.R;
import com.tphy.hospitaldoctor.ui.bean.PatientInfo;
import com.tphy.hospitaldoctor.utils.Common;

import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

import static java.lang.System.in;

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.HomeListHolder> {

    private List<PatientInfo> patientInfos;
    private Context context;
    private OnItemClickedListener onItemClickedListener;
//    private int TYPE_NORMAL = 0;
//    private int TYPE_FOOT = 1;

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

    public HomeListAdapter(List<PatientInfo> patientInfos, Context context) {
        this.patientInfos = patientInfos;
        this.context = context;
    }

    public void refreshData(List<PatientInfo> patientInfos) {
        this.patientInfos = patientInfos;
        notifyDataSetChanged();
    }

    public List<PatientInfo> getPatientInfos() {
        return patientInfos;
    }

    @Override
    public HomeListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (viewType == TYPE_FOOT) {
//            View view = LayoutInflater.from(context).inflate(R.layout.footer_loadmore, parent, false);
//            return new HomeListHolder(view,viewType);
//        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_home_list, parent, false);
            return new HomeListHolder(view,viewType);
//        }
    }

    @Override
    public void onBindViewHolder(final HomeListHolder holder, final int position) {
//        if (position < patientInfos.size()) {
            PatientInfo patientInfo = patientInfos.get(position);
            String zzjh = patientInfo.getZzjh();
            String cwh = patientInfo.getCwh();
            String hljb = patientInfo.getHljb();
            String hzxm = patientInfo.getHzxm();
            String ksmc = patientInfo.getKsmc();
            String nl = patientInfo.getHznl();
            String nldw = patientInfo.getNldw();
            String ryzd = patientInfo.getRyzd();
            String ye = patientInfo.getYe();
            String yllx = patientInfo.getYllx();
            String zyh = patientInfo.getZyh();
            String zzys = patientInfo.getZzys();
            String hzxb = patientInfo.getHzxb();
            String rysj = patientInfo.getRysj();
            String gchs = patientInfo.getGchs();
            String gmyw = patientInfo.getGmyw();
            String xx = patientInfo.getXx();
            String state = patientInfo.getSTATE();
            String finalGCHS = Common.getSpecificString(gchs, ",", 0);
            String format = Common.formatHomeCardTime(rysj);
            final boolean selected = patientInfo.isSelected();
            if (selected) {
                holder.iv_select.setVisibility(View.VISIBLE);
            } else {
                holder.iv_select.setVisibility(View.GONE);
            }
            holder.tv_cwh.setText(cwh);
            holder.tv_ksmc.setText(ksmc);
            holder.tv_zzys.setText(zzys);
            holder.tv_zyh.setText(zyh);
            holder.tv_hzxm.setText(hzxm);
            holder.tv_gchs.setText(finalGCHS);
            holder.tv_hznl.setText(nl + nldw);
            holder.tv_ryzd.setText(ryzd);
            holder.tv_ye.setText(ye);
            holder.tv_yllx.setText(yllx);
            holder.tv_time.setText(format);
            if (zzjh.equals("0")) {
                holder.iv_zhongzheng.setVisibility(View.GONE);
            } else {
                holder.iv_zhongzheng.setVisibility(View.VISIBLE);
            }
            switch (hzxb) {
                case "男":
                    holder.iv_photo.setBackground(context.getResources().getDrawable(R.mipmap.ic_boy));
                    break;
                case "女":
                    holder.iv_photo.setBackground(context.getResources().getDrawable(R.mipmap.ic_girl));
                    break;
            }
            switch (hljb) {
                case "一级护理":
                    holder.iv_level.setBackground(holder.ic_level_one);
                    break;
                case "二级护理":
                    holder.iv_level.setBackground(holder.ic_level_two);
                    break;
                case "三级护理":
                    holder.iv_level.setBackground(holder.ic_level_three);
                    break;
                case "四级护理":
                    holder.iv_level.setBackground(holder.ic_level_special);
                    break;
            }
            if(state.equals("0")) {
                startFlick(holder.tv_hzxm);
                startFlick(holder.tv_cwh);
            }else{
                stopFlick( holder.tv_hzxm );
                stopFlick( holder.tv_cwh);
            }
            if (xx.equals("")){
                holder.tv_xx.setVisibility(View.GONE);
            }else {
                if (xx.contains("A")||xx.contains("B")||xx.contains("O")) {
                    holder.tv_xx.setText(xx+"型");
                    holder.tv_xx.setBackground(context.getResources().getDrawable(R.drawable.bg_bednum));
                }else {
                    holder.tv_xx.setText(xx);
                    holder.tv_xx.setBackground(context.getResources().getDrawable(R.drawable.bg_bednum_grey));
                }
                holder.tv_xx.setVisibility(View.VISIBLE);
            }

                //过敏药物
            if (!gmyw.equals("")){
                holder.card_tv1.setVisibility(View.VISIBLE);
                holder.tv_ryzd.setText(Html.fromHtml("<font color=\"gray\">诊断：</font><font color=\"black\">"+ryzd+"</font><font color=\"gray\">  过敏药物：</font><font color=\"black\">"+gmyw+"</font>"));
            }else {
                holder.card_tv1.setVisibility(View.GONE);
                holder.tv_ryzd.setText(Html.fromHtml("<font color=\"gray\">诊断：</font><font color=\"black\">"+ryzd+"</font>"));
            }

            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != onItemClickedListener) {
                        onItemClickedListener.onItemClick(view, position, patientInfos.get(position));
                    }

                    notifyDataSetChanged();
                }
            });
//        }
    }

    @Override
    public int getItemCount() {
//        if (!patientInfos.isEmpty()) {
//            return patientInfos.size() + 1;
//        }
        return patientInfos.size();
    }

//    @Override
//    public int getItemViewType(int position) {
//        if (position >= patientInfos.size()) {
//            return TYPE_FOOT;
//        }
//        return TYPE_NORMAL;
//    }

    public class HomeListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_tv_cwh)
        TextView tv_cwh;
        @BindView(R.id.card_tv_ksmc)
        TextView tv_ksmc;
        @BindView(R.id.card_iv_zzys)
        ImageView iv_zzys;
        @BindView(R.id.card_tv_zzys)
        TextView tv_zzys;
        @BindView(R.id.card_tv_zyh)
        TextView tv_zyh;
        @BindView(R.id.card_tv_hzxm)
        TextView tv_hzxm;
        @BindView(R.id.card_tv_time)
        TextView tv_time;
        @BindView(R.id.card_tv_gchs)
        TextView tv_gchs;
        @BindView(R.id.card_tv_hznl)
        TextView tv_hznl;
        @BindView(R.id.card_tv_ryzd)
        TextView tv_ryzd;
        @BindView(R.id.card_tv_ye)
        TextView tv_ye;
        @BindView(R.id.card_tv_yllx)
        TextView tv_yllx;
        @BindView(R.id.card_iv_zhongzheng)
        ImageView iv_zhongzheng;
        @BindDrawable(R.mipmap.ic_male)
        Drawable ic_male;
        @BindDrawable(R.mipmap.ic_female)
        Drawable ic_female;
        @BindDrawable(R.mipmap.ic_level_one)
        Drawable ic_level_one;
        @BindDrawable(R.mipmap.ic_level_two)
        Drawable ic_level_two;
        @BindDrawable(R.mipmap.ic_level_three)
        Drawable ic_level_three;
        @BindDrawable(R.mipmap.ic_level_special)
        Drawable ic_level_special;
        @BindView(R.id.card)
        RelativeLayout card;
        @BindView(R.id.card_iv_select)
        ImageView iv_select;
        @BindView(R.id.card_iv_level)
        ImageView iv_level;
        @BindView(R.id.card_iv_photo)
        ImageView iv_photo;
        @BindView(R.id.card_tv1)
        ImageView card_tv1;
        @BindView(R.id.card_tv_xx)
        TextView tv_xx;
//        @BindView(R.id.loadmore_text)
//        TextView loadmore_text;

        public HomeListHolder(View itemView,int viewType) {
            super(itemView);
//            if (viewType == TYPE_NORMAL) {

                ButterKnife.bind(this, itemView);
//            } else {
//                loadmore_text = itemView.findViewById(R.id.loadmore_text);
//            }

        }
    }


    public interface OnItemClickedListener {

        void onItemClick(View view, int position, PatientInfo patientInfo);
    }
    /**
     * 开启View闪烁效果
     *
     * */
    private void startFlick( View view ) {
        if (null == view) {
            return;
        }
        Animation alphaAnimation = new AlphaAnimation(1, 0.1f);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setInterpolator(new LinearInterpolator());
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        view.startAnimation(alphaAnimation);
    }

    /**
     * 取消View闪烁效果
     *
     * */
    private void stopFlick( View view ){
        if( null == view ){
            return;
        }
        view.clearAnimation( );
    }

}
