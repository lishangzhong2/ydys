package com.tphy.hospitaldoctor.common.base;

import android.app.Application;
import android.support.multidex.MultiDexApplication;
import com.tphy.hospitaldoctor.utils.CrashHandler;
import com.tphy.hospitaldoctor.utils.FileUtils;

/**
 * Created by GongWen on 17/1/13.
 */

public class MainApplication extends MultiDexApplication {
    private static MainApplication INSTANCE;
    public  static  String sign_currentuserpin;
    public static  String ydhlURL = "http://10.10.0.254/tphis/eqwebservice/eqservice.asmx";

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        initCrashBug();
    }

    public static MainApplication getInstance() {

        return INSTANCE;
    }

    /**
     * 异常捕获 初始化
     */
    private void initCrashBug() {
        //异常捕获机制
        CrashHandler.getInstance().init(getApplicationContext());
        FileUtils.init(getApplicationContext());
    }

    public static void setSign_currentuserpin(String sign_currentuserpin) {
        MainApplication.sign_currentuserpin = sign_currentuserpin;
    }

    public static String getSign_currentuserpin() {
        return sign_currentuserpin;
    }

}
