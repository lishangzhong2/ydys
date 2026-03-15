package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;

/**
 * Created by ${王琪} on ${2017/11/28}.
 */

public class KuCun implements Serializable{
    /*"YFMC": "西药库",
        "YFID": "0302",
        "KC": 0,
        "DW": "袋"*/
    private String YFMC;
    private String YFID;
    private String KC;
    private String DW;

    public String getYFMC() {
        return YFMC;
    }

    public void setYFMC(String YFMC) {
        this.YFMC = YFMC;
    }

    public String getYFID() {
        return YFID;
    }

    public void setYFID(String YFID) {
        this.YFID = YFID;
    }

    public String getKC() {
        return KC;
    }

    public void setKC(String KC) {
        this.KC = KC;
    }

    public String getDW() {
        return DW;
    }

    public void setDW(String DW) {
        this.DW = DW;
    }
}
