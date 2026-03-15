package com.tphy.hospitaldoctor.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.tphy.hospitaldoctor.R;

/**
 * Created by ${王琪} on ${2017/11/28}.
 */

public class AudioRecordingDialog extends Dialog {
    public AudioRecordingDialog(@NonNull Context context) {
        this(context, R.style.CustomDialogTheme);
    }

    public AudioRecordingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_audio_layout);
    }
}
