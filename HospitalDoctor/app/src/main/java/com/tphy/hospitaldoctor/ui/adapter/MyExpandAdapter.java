package com.tphy.hospitaldoctor.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by tphy on 2018/3/15.
 */

public class MyExpandAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<Map<String, String>> groupData;
    private int groupLayout;
    private String[] groupFrom;
    private int[] groupTo;
    private List<List<Map<String, String>>> childData;
    private int childLayout;
    private String[] childFrom;
    private int[] childTo;


    public MyExpandAdapter(Context context, List<Map<String, String>> groupData, int groupLayout, String[] groupFrom, int[] groupTo, List<List<Map<String, String>>> childData, int childLayout, String[] childFrom, int[] childTo) {
        this.context = context;
        this.groupData = groupData;
        this.groupLayout = groupLayout;
        this.groupFrom = groupFrom;
        this.groupTo = groupTo;
        this.childData = childData;
        this.childLayout = childLayout;
        this.childFrom = childFrom;
        this.childTo = childTo;
    }

    @Override
    public int getGroupCount() {
        return groupData.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return childData.size();
    }

    @Override
    public Object getGroup(int i) {
        return groupData.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return childData.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        View gv;
        if (view == null) {
            gv = LayoutInflater.from(context).inflate(groupLayout, viewGroup, false);
        } else {
            gv = view;
        }
        bindGroupView(gv, groupData.get(i), groupFrom, groupTo);
        return gv;
    }

    private void bindGroupView(View gv, Map<String, String> map,
                               String[] groupFrom, int[] groupTo) {
        // TODO Auto-generated method stub
//        ImageView iv = (ImageView) gv.findViewById(groupTo[0]);
//        iv.setImageResource((Integer) map.get(groupFrom[0]));
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        View cv;
        if (view == null)
            cv = LayoutInflater.from(context).inflate(childLayout, viewGroup, false);
        else
            cv = view;
        bindChildView(cv, childData.get(i).get(i1),
                childFrom, childTo);
        return cv;
    }

    private void bindChildView(View cv, Map<String, String> map,
                               String[] childFrom, int[] childTo) {
        // TODO Auto-generated method stub
//        TextView tv = (TextView) cv.findViewById(childTo[0]);
//        tv.setText((String) map.get(childFrom[0]));
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
