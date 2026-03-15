package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2021-8-3.
 */

public class JianZhuJiLiang implements Serializable {

    private String YYJL;

    private int JL;

    private String JLDW;

    public String getYYJL() {
        return YYJL;
    }

    public void setYYJL(String YYJL) {
        this.YYJL = YYJL;
    }

    public int getJL() {
        return JL;
    }

    public void setJL(int JL) {
        this.JL = JL;
    }

    public String getJLDW() {
        return JLDW;
    }

    public void setJLDW(String JLDW) {
        this.JLDW = JLDW;
    }

    public JianZhuJiLiang(String YYJL, int JL, String JLDW) {
        this.YYJL = YYJL;
        this.JL = JL;
        this.JLDW = JLDW;
    }
}
