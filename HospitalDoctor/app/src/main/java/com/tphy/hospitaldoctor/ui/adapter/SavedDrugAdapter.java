package com.tphy.hospitaldoctor.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tphy.hospitaldoctor.R;
import com.tphy.hospitaldoctor.ui.bean.SavedYizhu;

import java.util.List;

/**
 * Created by ${王琪} on ${2017/11/28}.
 */

public class SavedDrugAdapter extends BaseAdapter {

    private List<SavedYizhu> list;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private OnItemDeleteListener onItemDeleteListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemDeleteListener(OnItemDeleteListener onItemDeleteListener) {
        this.onItemDeleteListener = onItemDeleteListener;
    }

    public List<SavedYizhu> getList() {
        return list;
    }

    public SavedDrugAdapter(List<SavedYizhu> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void updateList(List<SavedYizhu> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final SavedYizhu savedYizhu = list.get(position);
        final String yzmc = savedYizhu.getName();
        String xsyzsj = savedYizhu.getTime();
        String gg = savedYizhu.getGg();
        final String sl = savedYizhu.getSl();
        final boolean canEditCount = savedYizhu.isCanEditCount();
        final String bz = savedYizhu.getBz();
        final String dm = savedYizhu.getDm();
        boolean reGetData = savedYizhu.isReGetData();
        final ViewHolder holder;
        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_saved_drugs, parent, false);
            holder = new ViewHolder();
            holder.tv_name = convertView.findViewById(R.id.drug_tv_name);
            holder.tv_time = convertView.findViewById(R.id.drug_tv_time);
            holder.tv_infor = convertView.findViewById(R.id.drug_tv_info);
            holder.tv_count = convertView.findViewById(R.id.drug_tv_count);
            holder.savedParent = convertView.findViewById(R.id.savedParent);
            holder.tv_name2 = convertView.findViewById(R.id.drug2_tv_name);
            holder.editText = convertView.findViewById(R.id.drug2_edit);
            holder.tv_count2 = convertView.findViewById(R.id.drug2_tv_count);
            holder.iv_add = convertView.findViewById(R.id.drug2_iv_add);
            holder.iv_decrease = convertView.findViewById(R.id.drug2_iv_decrease);
            holder.layout2 = convertView.findViewById(R.id.layout2);
            holder.tv_delete = convertView.findViewById(R.id.drug2_tv_delete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (canEditCount) {
            holder.savedParent.setVisibility(View.GONE);
            holder.layout2.setVisibility(View.VISIBLE);
        } else {
            holder.savedParent.setVisibility(View.VISIBLE);
            holder.layout2.setVisibility(View.GONE);
        }
        if (!sl.equals("0")) {
            holder.tv_delete.setVisibility(View.GONE);
        }
        holder.tv_name.setText(yzmc);
        holder.tv_name2.setText(yzmc);
        holder.tv_time.setText(xsyzsj);
        holder.tv_infor.setText("规格：" + gg);
        holder.tv_count.setText("数量：" + sl);
        holder.tv_count2.setText(sl);
        if (!dm.equals("3") || yzmc.equals("文字医嘱")) {
            holder.editText.setEnabled(true);
            holder.editText.setText(bz);
        } else {
            holder.editText.setEnabled(false);
            holder.editText.setText("");
        }


        holder.savedParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onItemClickListener) {
                    onItemClickListener.onItemClick(holder.savedParent, position);
                }
            }
        });
        holder.iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = holder.tv_count2.getText().toString();
                String calculate = calculate(s, 1);
                savedYizhu.setSl(calculate);
                holder.tv_count2.setText(calculate);
                if (!calculate.equals("0")) {
                    holder.tv_delete.setVisibility(View.GONE);
                }
            }
        });
        holder.iv_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = holder.tv_count2.getText().toString();
                String calculate = calculate(s, -1);
                holder.tv_count2.setText(calculate);
                savedYizhu.setSl(calculate);
                if (calculate.equals("0")) {
                    holder.tv_delete.setVisibility(View.VISIBLE);
                } else {
                    holder.tv_delete.setVisibility(View.GONE);
                }
            }
        });
        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onItemDeleteListener) {
                    onItemDeleteListener.onDeleteClick(holder.tv_delete, position);
                }
            }
        });
        holder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                if (!dm.equals("3") || yzmc.equals("文字医嘱")) {
//                    String bz = s.toString();
//                    list.get(position).setBz(bz);
//                }
            }
        });
//        if (!dm.equals("3") || yzmc.equals("文字医嘱")) {
//            if (reGetData) {
//                String s = holder.editText.getText().toString();
//                savedYizhu.setBz(s);
//            }
//        }
        return convertView;
    }

    public class ViewHolder {
        private TextView tv_name;
        private TextView tv_time;
        private TextView tv_infor;
        private TextView tv_count;
        private RelativeLayout savedParent;
        private TextView tv_name2;
        private EditText editText;
        private ImageView iv_add;
        private ImageView iv_decrease;
        private TextView tv_count2;
        private RelativeLayout layout2;
        private TextView tv_delete;
    }
//
//    public class ViewHolder2 {
//        private TextView tv_name2;
//        private TextView tv_time2;
//        private TextView tv_infor2;
//
//        private TextView tv_count;
//        private ImageView iv_add;
//        private ImageView iv_decrease;
//    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemDeleteListener {
        void onDeleteClick(View view, int position);
    }


    private String calculate(String count, int num) {
        int countNum = Integer.parseInt(count);
        int newNum = countNum + num;
        if (newNum < 0) {
            newNum = 0;
        }
        String newCount = String.valueOf(newNum);
        return newCount;
    }

    public List<SavedYizhu> getSavedYizhus() {
        return list;
    }

//    class MyWatcher implements TextWatcher {
//
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count,
//                                      int after) {
//            // TODO Auto-generated method stub
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before,
//                                  int count) {
//            // TODO Auto-generated method stub
//
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//            text[index]=s.toString();//为输入的位置内容设置数组管理器，防止item重用机制导致的上下内容一样的问题
//        }
//
//    }

}
