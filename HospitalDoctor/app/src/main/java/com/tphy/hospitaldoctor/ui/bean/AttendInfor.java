package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;

/**
 * Created by ${王琪} on ${2017/11/28}.
 */

public class AttendInfor implements Serializable {
    /*"KSDM": "9014",
        "YSDM": "767",
        "XM": "上午",
        "STATUS": "上班",
        "CZSJ": "2018/2/26 0:01:01"*/
    private String KSDM;
    private String YSDM;
    private String XM;
    private String STATUS;
    private String CZSJ;

    public String getKSDM() {
        return KSDM;
    }

    public void setKSDM(String KSDM) {
        this.KSDM = KSDM;
    }

    public String getYSDM() {
        return YSDM;
    }

    public void setYSDM(String YSDM) {
        this.YSDM = YSDM;
    }

    public String getXM() {
        return XM;
    }

    public void setXM(String XM) {
        this.XM = XM;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getCZSJ() {
        return CZSJ;
    }

    public void setCZSJ(String CZSJ) {
        this.CZSJ = CZSJ;
    }
}
