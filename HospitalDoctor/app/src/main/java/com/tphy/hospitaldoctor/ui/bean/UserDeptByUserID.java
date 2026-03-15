package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;

/**
 * Created by ${王琪} on ${2017/11/28}.
 */

public class UserDeptByUserID implements Serializable {

    /*"DEPT_ID": "0122",
        "DEPT_NAME": "急诊",
        "TIME_LIMIT": ""*/
    private String DEPT_ID;
    private String DEPT_NAME;
    private String TIME_LIMIT;

    public String getDEPT_ID() {
        return DEPT_ID;
    }

    public void setDEPT_ID(String DEPT_ID) {
        this.DEPT_ID = DEPT_ID;
    }

    public String getDEPT_NAME() {
        return DEPT_NAME;
    }

    public void setDEPT_NAME(String DEPT_NAME) {
        this.DEPT_NAME = DEPT_NAME;
    }

    public String getTIME_LIMIT() {
        return TIME_LIMIT;
    }

    public void setTIME_LIMIT(String TIME_LIMIT) {
        this.TIME_LIMIT = TIME_LIMIT;
    }
}
