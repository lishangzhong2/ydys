package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;

/**
 * Created by hp on 2018/1/2.
 */

public class KeShi implements Serializable {

    private String DEPT_ID;
    private String DEPT_NAME;
    private String TIME_LIMIT;
    private String KSRS;

    public String getDEPT_ID() {
        return DEPT_ID;
    }


    public String getDEPT_NAME() {
        return DEPT_NAME;
    }

    public String getTIME_LIMIT() {
        return TIME_LIMIT;
    }

    public String getKSRS() {
        return KSRS;
    }


    public KeShi(String DEPT_ID, String DEPT_NAME, String TIME_LIMIT, String KSRS) {
        this.DEPT_ID = DEPT_ID;
        this.DEPT_NAME = DEPT_NAME;
        this.TIME_LIMIT = TIME_LIMIT;
        this.KSRS = KSRS;
    }
}
