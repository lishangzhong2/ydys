package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;

/**
 * Created by tphy on 2018/3/22.
 */

public class HerbalChildJson implements Serializable {

    /*ypdm:'药品代码'，
    jl:'剂量'；
    dw:'单位'，
    bz：'备注'，
    pc:'批次'，
    ph:'批号'，
    sxh：‘顺序号
    ’，lsj:'零售价'
    ，cddm:‘产地代码’*/

    private String ypdm;
    private String jl;
    private String dw;
    private String bz;
    private String pc;
    private String ph;
    private String sxh;
    private String lsj;
    private String cddm;

    public String getYpdm() {
        return ypdm;
    }

    public void setYpdm(String ypdm) {
        this.ypdm = ypdm;
    }

    public String getJl() {
        return jl;
    }

    public void setJl(String jl) {
        this.jl = jl;
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getPc() {
        return pc;
    }

    public void setPc(String pc) {
        this.pc = pc;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getSxh() {
        return sxh;
    }

    public void setSxh(String sxh) {
        this.sxh = sxh;
    }

    public String getLsj() {
        return lsj;
    }

    public void setLsj(String lsj) {
        this.lsj = lsj;
    }

    public String getCddm() {
        return cddm;
    }

    public void setCddm(String cddm) {
        this.cddm = cddm;
    }

    public HerbalChildJson() {
    }

    public HerbalChildJson(String ypdm, String jl, String dw, String bz, String pc, String ph, String sxh, String lsj, String cddm) {
        this.ypdm = ypdm;

        this.jl = jl;
        this.dw = dw;
        this.bz = bz;
        this.pc = pc;
        this.ph = ph;
        this.sxh = sxh;
        this.lsj = lsj;
        this.cddm = cddm;
    }
}
