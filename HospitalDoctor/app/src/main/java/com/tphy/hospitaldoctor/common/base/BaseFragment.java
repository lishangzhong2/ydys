package com.tphy.hospitaldoctor.common.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.tphy.hospitaldoctor.common.config.Constant;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BaseFragment extends AppFragment {

    protected Unbinder unbinder;
    protected SharedPreferences preferences;
    //    protected String code;
    protected Context context;
    protected String czydm;
    protected String czymc;
    protected String ksdm;
    protected String ksmc;
    protected String jb;
    protected String TAG = "WQ";
    protected int mScreenWidth = 0;
    protected int mScreenHeight = 0;
    protected float mScreenDensity = 0.0f;

    /*update code_czydm set padid = '' where czydm = '767';
      commit;*/

    /*select * from ydys_config for update*/
    @Override
    protected int getLayoutID() {
        return 0;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getHoldingActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        mScreenDensity = displayMetrics.density;
        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;
        int orientation = context.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            setPortraitView();
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setLandScapeView();
        }

    }

    protected void setLandScapeView() {

    }

    protected void setPortraitView() {

    }


    @Override
    protected void initData(Bundle arguments) {
        preferences = getActivity().getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
        czydm = preferences.getString("czydm", "");
        czymc = preferences.getString("czymc", "");
        ksdm = preferences.getString("ksdm", "");
        ksmc = preferences.getString("ksmc", "");
        jb = preferences.getString("jb", "");
        context = getContext();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int orientation = context.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            initPviewsAndEvents();
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            initLviewsAndEvents();
        }
    }

    /*横屏*/
    protected void initLviewsAndEvents() {

    }

    /*竖屏*/
    protected void initPviewsAndEvents() {

    }


}
