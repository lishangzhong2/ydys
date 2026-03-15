package com.tphy.hospitaldoctor.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tphy.hospitaldoctor.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tphy on 2018/3/5.
 */

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestHolder>{
    private List<String> strings;
    private Context context;

    public void setStrings(List<String> strings) {
        this.strings = strings;
        notifyDataSetChanged();
    }

    public TestAdapter(List<String> strings, Context context) {
        this.strings = strings;
        this.context = context;
    }

    @Override
    public TestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_spinner, parent, false);
        return new TestHolder(view);
    }

    @Override
    public void onBindViewHolder(TestHolder holder, int position) {
        String s = strings.get(position);
        holder.textView.setText(s);
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    public class TestHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.testtext)
        TextView textView;
        public TestHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
