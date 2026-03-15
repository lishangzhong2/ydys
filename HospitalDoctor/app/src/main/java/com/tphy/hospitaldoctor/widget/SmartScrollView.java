package com.tphy.hospitaldoctor.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by chenwenping on 17/3/30.
 */

public class SmartScrollView extends ScrollView {

    public SmartScrollView(Context context) {
        super(context);
    }

    public SmartScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmartScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
/*滑动到底部监听*/
    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
//        Log.e("WQ", "scrollY==" + scrollY + "  scrollX==" + scrollX + "   clampeX==" + clampedX + "   clampedY" + clampedY);
        if(scrollY != 0 && null != mScrollViewBottomListener){
            mScrollViewBottomListener.onScrollViewBottomListener(clampedY);
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//        Log.e("WQ", "l==" + l + "  t==" + t + "   oldl==" + oldl + "   oldt" + oldt);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public interface ScrollViewBottomListener {
        void onScrollViewBottomListener(boolean isBottom);
    }

    private ScrollViewBottomListener mScrollViewBottomListener;

    public void setScrollViewBottomListener(ScrollViewBottomListener scrollViewBottomListener) {
        mScrollViewBottomListener = scrollViewBottomListener;
    }
}
