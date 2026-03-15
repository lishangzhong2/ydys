package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2021-8-2.
 */

public class HerbalDrugOthers implements Serializable {
//药品代码
    private String YPDM;
    //批次
    private String PC;
    //批号
    private String PH;
    //零售价
    private String LSJ;
    //产地代码
    private String CDDM;

    public HerbalDrugOthers() {
    }

    public HerbalDrugOthers(String YPDM, String PC, String PH, String LSJ, String CDDM) {

        this.YPDM = YPDM;
        this.PC = PC;
        this.PH = PH;
        this.LSJ = LSJ;
        this.CDDM = CDDM;
    }

    public String getYPDM() {
        return YPDM;
    }

    public void setYPDM(String YPDM) {
        this.YPDM = YPDM;
    }

    public String getPC() {
        return PC;
    }

    public void setPC(String PC) {
        this.PC = PC;
    }

    public String getPH() {
        return PH;
    }

    public void setPH(String PH) {
        this.PH = PH;
    }

    public String getLSJ() {
        return LSJ;
    }

    public void setLSJ(String LSJ) {
        this.LSJ = LSJ;
    }

    public String getCDDM() {
        return CDDM;
    }

    public void setCDDM(String CDDM) {
        this.CDDM = CDDM;
    }
}
