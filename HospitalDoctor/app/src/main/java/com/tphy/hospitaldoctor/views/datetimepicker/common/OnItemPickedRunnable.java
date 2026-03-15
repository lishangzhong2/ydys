package com.tphy.hospitaldoctor.views.datetimepicker.common;


import com.tphy.hospitaldoctor.views.datetimepicker.listener.OnItemPickListener;
import com.tphy.hospitaldoctor.views.datetimepicker.widget.WheelView;

final public class OnItemPickedRunnable implements Runnable {
    final private WheelView wheelView;
    private OnItemPickListener onItemPickListener;
    public OnItemPickedRunnable(WheelView wheelView, OnItemPickListener onItemPickListener) {
        this.wheelView = wheelView;
        this.onItemPickListener = onItemPickListener;
    }

    @Override
    public final void run() {
        onItemPickListener.onItemPicked(wheelView.getCurrentPosition(),wheelView.getCurrentItem());
    }
}
