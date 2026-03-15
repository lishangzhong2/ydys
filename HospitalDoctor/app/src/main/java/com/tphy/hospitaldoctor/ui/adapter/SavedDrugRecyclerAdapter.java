package com.tphy.hospitaldoctor.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.tphy.hospitaldoctor.R;
import com.tphy.hospitaldoctor.ui.bean.FreqencyDict;
import com.tphy.hospitaldoctor.ui.bean.SavedYizhu;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedDrugRecyclerAdapter extends RecyclerView.Adapter<SavedDrugRecyclerAdapter.SavedHolder> {
    private  String[] plItems;
    private List<SavedYizhu> list;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private OnItemDeleteListener onItemDeleteListener;
    private List<FreqencyDict> freqencyDicts;
    private String isLong;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemDeleteListener(OnItemDeleteListener onItemDeleteListener) {
        this.onItemDeleteListener = onItemDeleteListener;
    }


    public SavedDrugRecyclerAdapter(List<SavedYizhu> list, Context context,List<FreqencyDict> freqencyDicts, String isLong) {
        this.list = list;
        this.context = context;
        this.freqencyDicts = freqencyDicts;
        plItems = new String[freqencyDicts.size()];
        for (int i = 0; i < freqencyDicts.size(); i++) {
            plItems[i] = freqencyDicts.get(i).getMC1();
        }
        this.isLong = isLong;
    }

    public void updateList(List<SavedYizhu> list, List<FreqencyDict> freqencyDicts, String isLong) {
        this.list = list;
        this.freqencyDicts = freqencyDicts;
        plItems = new String[freqencyDicts.size()];
        for (int i = 0; i < freqencyDicts.size(); i++) {
            plItems[i] = freqencyDicts.get(i).getMC1();
        }
        this.isLong = isLong;
        notifyDataSetChanged();
    }

    @Override
    public SavedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_saved_drugs, parent, false);
        return new SavedHolder(view);
    }

    @Override
    public void onBindViewHolder(final SavedHolder holder, final int position) {
        final SavedYizhu savedYizhu = list.get(position);
        final String yzmc = savedYizhu.getName();
        String xsyzsj = savedYizhu.getTime();
        String gg = savedYizhu.getGg();
        final String sl = savedYizhu.getSl();
        final boolean canEditCount = savedYizhu.isCanEditCount();
        final String bz = savedYizhu.getBz();
        final String dm = savedYizhu.getDm();
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
        if (isLong.equals("true")&&savedYizhu.getYizhuType().equals("治疗医嘱")){
            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.mysimple_spinner_item, plItems);
            adapter.setDropDownViewResource(R.layout.mysimple_spinner_dropdown_item);
            holder.luru_sp_zl_pl.setAdapter(adapter);

            int plPositionTemp = savedYizhu.getPlPosition();
            holder.luru_sp_zl_pl.setSelection(plPositionTemp);
            holder.tv_gypl.setVisibility(View.VISIBLE);
            holder.luru_sp_zl_pl.setVisibility(View.VISIBLE);
        }else {
            holder.tv_gypl.setVisibility(View.GONE);
            holder.luru_sp_zl_pl.setVisibility(View.GONE);
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
                if (!dm.equals("3") || yzmc.equals("文字医嘱")) {
                    String bz = s.toString();
                    savedYizhu.setBz(bz);
                }
            }
        });

        holder.luru_sp_zl_pl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String dm1 = freqencyDicts.get(position).getDM();
                savedYizhu.setPlPosition(position);
                savedYizhu.setPldm(dm1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SavedHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.drug_tv_name)
        TextView tv_name;
        @BindView(R.id.drug_tv_time)
        TextView tv_time;
        @BindView(R.id.drug_tv_info)
        TextView tv_infor;
        @BindView(R.id.drug_tv_count)
        TextView tv_count;
        @BindView(R.id.savedParent)
        RelativeLayout savedParent;
        @BindView(R.id.drug2_tv_name)
        TextView tv_name2;
        @BindView(R.id.drug2_edit)
        EditText editText;
        @BindView(R.id.drug2_tv_count)
        TextView tv_count2;
        @BindView(R.id.drug2_iv_add)
        ImageView iv_add;
        @BindView(R.id.drug2_iv_decrease)
        ImageView iv_decrease;
        @BindView(R.id.layout2)
        RelativeLayout layout2;
        @BindView(R.id.drug2_tv_delete)
        TextView tv_delete;
        @BindView(R.id.tv_glpl)
        TextView tv_gypl;
        @BindView(R.id.luru_sp_zl_pl)
        Spinner luru_sp_zl_pl;
//        @BindView(R.id.drug_tv_name)
//        TextView tv_name;

        public SavedHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

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
}
