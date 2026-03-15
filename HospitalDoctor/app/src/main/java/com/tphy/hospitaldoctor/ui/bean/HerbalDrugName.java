package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2021-7-28.
 */
//中草药新增处方明细查询时候用的实体类
public class HerbalDrugName implements Serializable{

    //药品名称
    private String YPMC;
    //单位
    private String DW;
    //药品代码
    private String YPDM;
    //数量(库存)
    private String SL;

    private boolean isChecked;
//规格
    private String GG;
//同一科室下不同医生选择的药的次数
    private int CSS;

    public int getCSS() {
        return CSS;
    }

    public void setCSS(int CSS) {
        this.CSS = CSS;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getGG() {
        return GG;
    }

    public void setGG(String GG) {
        this.GG = GG;
    }

    public String getDW() {
        return DW;
    }

    public void setDW(String DW) {
        this.DW = DW;
    }

    public String getYPDM() {
        return YPDM;
    }

    public void setYPDM(String YPDM) {
        this.YPDM = YPDM;
    }

    public String getSL() {
        return SL;
    }

    public void setSL(String SL) {
        this.SL = SL;
    }

    public String getYPMC() {
        return YPMC;
    }

    public void setYPMC(String YPMC) {
        this.YPMC = YPMC;
    }
}
