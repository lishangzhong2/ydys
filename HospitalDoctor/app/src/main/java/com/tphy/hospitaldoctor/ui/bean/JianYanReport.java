package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;

/**
 * Created by ${王琪} on ${2017/11/28}.
 */

public class JianYanReport implements Serializable {

    /*{
        "SQDH": "123",
        "BBH": "11",
        "JCXMMC": "血红蛋白",
        "MC": "血常规",
        "JGSJ": "2017/12/14 0:00:00",
        "SQKS": "0439"
    }*/


//    private String SQDH;
//    private String BBH;
//    private String JCXMMC;
//    private String MC;
//    private String JGSJ;
//    private String SQKS;
//    private String xjbz;
//
//    public String getXjbz() {
//        return xjbz;
//    }
//
//    public void setXjbz(String xjbz) {
//        this.xjbz = xjbz;
//    }
//
//    public String getSQDH() {
//        return SQDH;
//    }
//
//    public void setSQDH(String SQDH) {
//        this.SQDH = SQDH;
//    }
//
//    public String getBBH() {
//        return BBH;
//    }
//
//    public void setBBH(String BBH) {
//        this.BBH = BBH;
//    }
//
//    public String getJCXMMC() {
//        return JCXMMC;
//    }
//
//    public void setJCXMMC(String JCXMMC) {
//        this.JCXMMC = JCXMMC;
//    }
//
//    public String getMC() {
//        return MC;
//    }
//
//    public void setMC(String MC) {
//        this.MC = MC;
//    }
//
//    public String getJGSJ() {
//        return JGSJ;
//    }
//
//    public void setJGSJ(String JGSJ) {
//        this.JGSJ = JGSJ;
//    }
//
//    public String getSQKS() {
//        return SQKS;
//    }
//
//    public void setSQKS(String SQKS) {
//        this.SQKS = SQKS;
//    }

       /*"zyh": "52629",
        "sqdh": 2188,
        "bbh": 104,
        "mc": "血清 ",
        "jcxmmc": "降钙素原定量",
        "jgsj": "2013/6/21 10:55:46",
        "sqks": "脑外科",
        "sbh": "5",
        "xjbz": "检验"*/

    private String zyh;
    private String sqdh;
    private String bbh;
    private String mc;
    private String jcxmmc;
    private String jgsj;
    private String sqks;
    private String sbh;
    private String xjbz;

    public String getZyh() {
        return zyh;
    }

    public void setZyh(String zyh) {
        this.zyh = zyh;
    }

    public String getSqdh() {
        return sqdh;
    }

    public void setSqdh(String sqdh) {
        this.sqdh = sqdh;
    }

    public String getBbh() {
        return bbh;
    }

    public void setBbh(String bbh) {
        this.bbh = bbh;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getJcxmmc() {
        return jcxmmc;
    }

    public void setJcxmmc(String jcxmmc) {
        this.jcxmmc = jcxmmc;
    }

    public String getJgsj() {
        return jgsj;
    }

    public void setJgsj(String jgsj) {
        this.jgsj = jgsj;
    }

    public String getSqks() {
        return sqks;
    }

    public void setSqks(String sqks) {
        this.sqks = sqks;
    }

    public String getSbh() {
        return sbh;
    }

    public void setSbh(String sbh) {
        this.sbh = sbh;
    }

    public String getXjbz() {
        return xjbz;
    }

    public void setXjbz(String xjbz) {
        this.xjbz = xjbz;
    }
}
