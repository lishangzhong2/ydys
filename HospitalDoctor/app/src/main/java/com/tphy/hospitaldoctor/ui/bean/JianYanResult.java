package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;

/**
 * Created by ${王琪} on ${2017/11/28}.
 */

public class JianYanResult implements Serializable {
    private String ZWMC;
    private String JG;
    private String DW;
    private String RANGE;
    private String ZT;
    private String YWMC;

    public String getZWMC() {
        return ZWMC;
    }

    public void setZWMC(String ZWMC) {
        this.ZWMC = ZWMC;
    }

    public String getJG() {
        return JG;
    }

    public void setJG(String JG) {
        this.JG = JG;
    }

    public String getDW() {
        return DW;
    }

    public void setDW(String DW) {
        this.DW = DW;
    }

    public String getRANGE() {
        return RANGE;
    }

    public void setRANGE(String RANGE) {
        this.RANGE = RANGE;
    }

    public String getZT() {
        return ZT;
    }

    public void setZT(String ZT) {
        this.ZT = ZT;
    }

    public String getYWMC() {
        return YWMC;
    }

    public void setYWMC(String YWMC) {
        this.YWMC = YWMC;
    }
}
