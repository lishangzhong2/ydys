package com.tphy.hospitaldoctor.widget;

import android.content.Context;
import android.util.AttributeSet;

public class AlwaysMarqueeTextView extends android.support.v7.widget.AppCompatTextView {
    public AlwaysMarqueeTextView(Context context) {
        super(context);
    }

    public AlwaysMarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlwaysMarqueeTextView(Context context, AttributeSet attrs,
                                 int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean isFocused() {
        return true;
    }

    /*<com.tphy.widget.AlwaysMarqueeTextView
        android:id="@+id/act_bedlist_item_twyc"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_below="@+id/act_bedlist_item_hzxb"
        android:layout_marginBottom="5dp"
        android:layout_marginLeft="9dp"
        android:layout_marginRight="@dimen/padding7"
        android:layout_marginTop="@dimen/padding7"
        android:layout_toRightOf="@id/act_bedlist_item_zyh"
        android:ellipsize="marquee"
        android:focusableInTouchMode="true"
        android:marqueeRepeatLimit="marquee_forever"
        android:singleLine="true"
        android:textColor="#ff6363"
        android:textSize="15sp" />*/
}
