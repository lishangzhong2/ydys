package com.tphy.hospitaldoctor.utils;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

/**
 * Created by Administrator on 2021-8-9.
 */

public class Mypopmenu extends PopupMenu {
    public Mypopmenu(Context context, View anchor) {
        super(context, anchor);
    }

    public void setOnMeunItemPositionListener(OnMeunItemPositionListener onMeunItemPositionListener,int pos ) {
        if (null!=this.onMeunItemPositionListener) {
            this.onMeunItemPositionListener.OnMeunItemPositionClick(onMeunItemPositionListener, pos);
        }
    }

    private OnMeunItemPositionListener onMeunItemPositionListener;

    public OnMeunItemPositionListener getOnMeunItemPositionListener() {
        return onMeunItemPositionListener;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Mypopmenu(Context context, View anchor, int gravity) {
        super(context, anchor, gravity);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public Mypopmenu(Context context, View anchor, int gravity, int popupStyleAttr, int popupStyleRes) {
        super(context, anchor, gravity, popupStyleAttr, popupStyleRes);
    }

    @Override
    public void setOnMenuItemClickListener(OnMenuItemClickListener listener) {
        super.setOnMenuItemClickListener(listener);
    }

    public interface  OnMeunItemPositionListener {
        void OnMeunItemPositionClick(OnMeunItemPositionListener menuItem,int pos);
    }
}
