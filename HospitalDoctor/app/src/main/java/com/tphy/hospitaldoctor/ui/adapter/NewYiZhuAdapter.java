package com.tphy.hospitaldoctor.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tphy.hospitaldoctor.R;
import com.tphy.hospitaldoctor.ui.bean.YiZhuBean;
import com.tphy.hospitaldoctor.views.SwipeDragLayout;

import java.util.ArrayList;
import java.util.List;

public class NewYiZhuAdapter extends BaseAdapter {

    private Context context;
    private List<YiZhuBean> yiZhuBeanList;
    private OnItemClickListener onItemClickListener;
    private EditListener editListener;

    public NewYiZhuAdapter(Context context, List<YiZhuBean> yiZhuBeanList) {
        this.context = context;
        this.yiZhuBeanList = yiZhuBeanList;
    }

    public void refreshData(List<YiZhuBean> beans) {
        yiZhuBeanList = beans;
        notifyDataSetChanged();
    }

    public void addItem(YiZhuBean yiZhuBean) {
        yiZhuBeanList.add(0, yiZhuBean);
        notifyDataSetChanged();
    }

    public void addNewList(List<YiZhuBean> list) {
        for (int i = 0; i < list.size(); i++) {
            YiZhuBean yiZhuBean = list.get(i);
            yiZhuBeanList.add(0, yiZhuBean);
        }
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setEditListener(EditListener editListener) {
        this.editListener = editListener;
    }

    public List<YiZhuBean> getCheckedList() {
        List<YiZhuBean> checkedList = new ArrayList<>();
        for (int i = 0; i < yiZhuBeanList.size(); i++) {
            YiZhuBean yiZhuBean = yiZhuBeanList.get(i);
            if (yiZhuBean.isChecked()) {
                checkedList.add(yiZhuBean);
            }
        }
        return checkedList;
    }


    @Override
    public int getCount() {
        return yiZhuBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return yiZhuBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_yizhu_lsit, parent, false);
            holder = new ViewHolder();
            holder.tv_state = convertView.findViewById(R.id.item_tv_state);
            holder.tv_startTime = convertView.findViewById(R.id.item_tv_startTime);
            holder.tv_group = convertView.findViewById(R.id.item_tv_group);
            holder.tv_yizhu = convertView.findViewById(R.id.item_tv_yizhu);
            holder.tv_docSig = convertView.findViewById(R.id.item_tv_docSig);
            holder.tv_nursig = convertView.findViewById(R.id.item_tv_nursig);
            holder.tv_endTime = convertView.findViewById(R.id.item_tv_endTime);
            holder.tv_endDoc = convertView.findViewById(R.id.item_tv_endDoc);
            holder.tv_nurCheck = convertView.findViewById(R.id.item_tv_nurCheck);
            holder.tv_num = convertView.findViewById(R.id.item_tv_num);
            holder.yizhu_ll = convertView.findViewById(R.id.item_yizhu_ll);
            holder.checkBox = convertView.findViewById(R.id.item_check);
//            holder.test = convertView.findViewById(R.id.test);
            holder.swipeDragLayout = convertView.findViewById(R.id.swipeDrag);
            holder.swipetv = convertView.findViewById(R.id.swipetv);
            holder.viewIndicator = convertView.findViewById(R.id.viewIndicator);
            holder.iv_slide = convertView.findViewById(R.id.item_iv_slide);
            holder.item_tv_cazxsj = convertView.findViewById(R.id.item_tv_cazxsj);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position % 2 == 0) {
            holder.yizhu_ll.setBackgroundColor(context.getResources().getColor(android.R.color.white));
        } else {
            holder.yizhu_ll.setBackgroundColor(context.getResources().getColor(R.color.listItemBg));
        }
        final YiZhuBean yiZhuInfo = yiZhuBeanList.get(position);
        final String state = yiZhuInfo.getYZZT();
        String startTime = yiZhuInfo.getXSYZSJ();
        String group = yiZhuInfo.getZBZ();
        String yizhu = yiZhuInfo.getYZMC();
        String doctorSignature = yiZhuInfo.getYSMC();
        String nurseSignature = yiZhuInfo.getZXHSMC();
        String endTime = yiZhuInfo.getXSTZSJ();
        String endDoctor = yiZhuInfo.getTZYSMC();
        String nurseCheck = yiZhuInfo.getTZHSMC();
        String lxdm = yiZhuInfo.getLXDM();
        String jl = yiZhuInfo.getJL();
        String dw = yiZhuInfo.getDW();
        String yfmc = yiZhuInfo.getYFMC();
        String plzwmc = yiZhuInfo.getPLZWMC();
        final String num = yiZhuInfo.getYZH();
        boolean show = yiZhuInfo.isShow();
        if (show) {
            holder.checkBox.setVisibility(View.VISIBLE);
        } else {
            holder.checkBox.setVisibility(View.GONE);
        }
        boolean checked = yiZhuInfo.isChecked();
        holder.checkBox.setChecked(checked);
        holder.tv_startTime.setText(startTime);
        holder.tv_group.setText(group);
        holder.tv_yizhu.setText(yizhu + "\t\t" + jl + dw+ "\t\t\t"+yfmc+ "\t\t\t"+plzwmc);
        holder.tv_docSig.setText(doctorSignature);
        holder.tv_nursig.setText(nurseSignature);
        holder.tv_endTime.setText(endTime);
        holder.tv_endDoc.setText(endDoctor);
        holder.tv_nurCheck.setText(nurseCheck);
        holder.tv_num.setText(num);
        if (yiZhuInfo.getCQYZBZ().equals("1")){
            holder.item_tv_cazxsj.setVisibility(View.GONE);
        }else {
            holder.item_tv_cazxsj.setVisibility(View.VISIBLE);
            holder.item_tv_cazxsj.setText(yiZhuInfo.getCZZXR()+"\n"+yiZhuInfo.getCAZXSJ());
        }
        switch (state) {
            case "0":
                holder.tv_state.setText("新增");
                holder.viewIndicator.setBackgroundColor(context.getResources().getColor(R.color.xijian));
                break;
            case "1":
                holder.tv_state.setText("保存");
                holder.viewIndicator.setBackgroundColor(context.getResources().getColor(R.color.baocun));
                break;
            case "2":
                holder.tv_state.setText("提交");
                holder.viewIndicator.setBackgroundColor(context.getResources().getColor(R.color.tijiao));

                break;
            case "3":
                holder.tv_state.setText("审核未通过");
                holder.viewIndicator.setBackgroundColor(context.getResources().getColor(R.color.weitongguo));
                break;
            case "4":
                holder.tv_state.setText("审核通过");
                holder.viewIndicator.setBackgroundColor(context.getResources().getColor(R.color.tongguo));
                break;
            case "5":
                holder.tv_state.setText("已生成");
                holder.viewIndicator.setBackgroundColor(context.getResources().getColor(R.color.yishengcheng));
                break;
            case "6":
                holder.tv_state.setText("已执行");
                holder.viewIndicator.setBackgroundColor(context.getResources().getColor(R.color.yizhixing));
                break;
            case "7":
                holder.tv_state.setText("已停止");
                holder.viewIndicator.setBackgroundColor(context.getResources().getColor(R.color.yitingzhi));
                break;
            case "8":
                holder.tv_state.setText("已撤销");
                holder.viewIndicator.setBackgroundColor(context.getResources().getColor(R.color.yitingzhi));
                break;
            case "10":
                holder.tv_state.setText("作废");
                holder.viewIndicator.setBackgroundColor(context.getResources().getColor(R.color.zuofei));
                break;
            case "11":
                holder.tv_state.setText("待保存");
                holder.viewIndicator.setBackgroundColor(context.getResources().getColor(android.R.color.white));
                break;
        }
        switch (lxdm) {
            case "1":
                holder.tv_yizhu.setTextColor(context.getResources().getColor(R.color.yaopin));
                break;
            case "2":
                holder.tv_yizhu.setTextColor(context.getResources().getColor(R.color.shoushu));
                break;
            case "3":
                holder.tv_yizhu.setTextColor(context.getResources().getColor(R.color.jiancha));
                break;
            case "5":
                holder.tv_yizhu.setTextColor(context.getResources().getColor(R.color.huli));
                break;
            case "6":
                holder.tv_yizhu.setTextColor(context.getResources().getColor(R.color.fuzhu));
                break;
            case "7":
                holder.tv_yizhu.setTextColor(context.getResources().getColor(R.color.qita));
                break;
            case "8":
                holder.tv_yizhu.setTextColor(context.getResources().getColor(R.color.zhiliao));
                break;
        }
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()) {
                    setChecked(position, true);
                } else {
                    setChecked(position, false);
                }
                notifyDataSetChanged();
            }

        });

        holder.yizhu_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onclick(holder.yizhu_ll, position);
            }
        });

        holder.swipeDragLayout.setSwipeDirection(SwipeDragLayout.DIRECTION_LEFT);
        holder.swipeDragLayout.addListener(new SwipeDragLayout.SwipeListener() {
            @Override
            public void onUpdate(SwipeDragLayout layout, float offsetRatio, float offset) {
//                if (state.equals("6")) {
//                    holder.swipetv.setText("停止/撤销");
//                    holder.swipetv.setBackgroundColor(context.getResources().getColor(R.color.yitingzhi));
//                } else if (state.equals("1") || state.equals("3")) {
//                    holder.swipetv.setText("删除");
//                    holder.swipetv.setBackgroundColor(context.getResources().getColor(R.color.textRed));
//                } else {
                    holder.swipetv.setText("不可操作");
                    holder.swipetv.setBackgroundColor(context.getResources().getColor(R.color.noEdit));
//                }
            }

            @Override
            public void onOpened(SwipeDragLayout layout) {
//                holder.iv_slide.setVisibility(View.GONE);
            }

            @Override
            public void onClosed(SwipeDragLayout layout) {
//                if (holder.iv_slide.getVisibility() == View.GONE) {
//                    holder.iv_slide.setVisibility(View.VISIBLE);
//                }
            }
        });
        holder.swipetv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editListener != null) {
                    int type;
                    String s = holder.swipetv.getText().toString();
                    if (s.equals("删除")) {
                        type = 1;
                    } else if (s.equals("不可操作")) {
                        type = 0;
                    } else {
                        type = 2;
                    }
                    editListener.edit(holder.swipetv, position, type);
                }
            }
        });
        return convertView;
    }

    public List<YiZhuBean> getAllList() {

        return this.yiZhuBeanList;
    }

    public class ViewHolder {

        //        @BindView(R.id.item_tv_state)
        TextView tv_state;
        //        @BindView(R.id.item_tv_startTime)
        TextView tv_startTime;
        //        @BindView(R.id.item_tv_group)
        TextView tv_group;
        //        @BindView(R.id.item_tv_yizhu)
        TextView tv_yizhu;
        //        @BindView(R.id.item_tv_docSig)
        TextView tv_docSig;
        //        @BindView(R.id.item_tv_nursig)
        TextView tv_nursig;
        //        @BindView(R.id.item_tv_endTime)
        TextView tv_endTime;
        //        @BindView(R.id.item_tv_endDoc)
        TextView tv_endDoc;
        //        @BindView(R.id.item_tv_nurCheck)
        TextView tv_nurCheck;
        //        @BindView(R.id.item_tv_num)
        TextView tv_num;
        //        @BindView(R.id.item_yizhu_ll)
        LinearLayout yizhu_ll;

        CheckBox checkBox;

        View viewIndicator;
//        TextView test;

        SwipeDragLayout swipeDragLayout;
        TextView swipetv;
        ImageView iv_slide;
        TextView item_tv_cazxsj;
//        @BindColor(R.color.listItemBg)
//        int itemBg;
//        @BindColor(android.R.color.white)
//        int colorWhite;
//        @BindDrawable(R.drawable.item_yizhu_selector)
//        Drawable selector1;
//        @BindDrawable(R.drawable.item_yizhu_selector2)
//        Drawable selector2;

//        public ViewHolder(View itemView) {
//            ButterKnife.bind(this, itemView);
//        }
    }

    private void setChecked(int position, boolean checked) {
        YiZhuBean yiZhuBean = yiZhuBeanList.get(position);
        String yzh1 = yiZhuBean.getYZH();
        yiZhuBean.setChecked(checked);
        for (int i = 0; i < yiZhuBeanList.size(); i++) {
            if (i != position) {
                YiZhuBean bean = yiZhuBeanList.get(i);
                String yzh = bean.getYZH();
                if (yzh1.equals(yzh)) {
                    bean.setChecked(checked);
                }
            }
        }
    }

    public interface OnItemClickListener {
        void onclick(View view, int position);
    }

    public interface  EditListener {
        void edit(View view, int position, int type);
    }


}
