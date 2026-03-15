package com.tphy.hospitaldoctor.common.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class AppFragment extends Fragment {

    protected FragmentActivity mActivity;
    private View view;
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutID(), container, false);
        context = getContext();
        initData(getArguments());
        initView(view, savedInstanceState);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releaseView();
    }

    protected void releaseView() {
        view = null;
    }

    protected abstract int getLayoutID();

    protected abstract void initData(Bundle arguments);

    protected abstract void initView(View view, Bundle savedInstanceState);

    protected BaseAppCompatActivity getHoldingActivity() {
        if (getActivity() instanceof BaseAppCompatActivity) {
            return (BaseAppCompatActivity) getActivity();
        } else {
            throw new ClassCastException("activity must extends BaseActivity");
        }
    }
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        int orientation = context.getResources().getConfiguration().orientation;
//        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
//            initPviewsAndEvents();
//        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            initLviewsAndEvents();
//        }
//    }
//
//    protected abstract void initLviewsAndEvents();
//
//    protected abstract void initPviewsAndEvents();

}
