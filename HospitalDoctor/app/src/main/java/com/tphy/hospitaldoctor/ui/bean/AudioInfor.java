package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;

/**
 * Created by ${王琪} on ${2017/11/28}.
 */

public class AudioInfor implements Serializable {

    private String LSH;
    private String ZYH;
    private String YSDM;
    private String YYMC;
    private String YYDZ;
    private String YYNR;
    private String LRSJ;
    private String SRM1;
    private String BZ;
    private String YYLX;
    private char STATE;

    public char getSTATE() {
        return STATE;
    }

    public void setSTATE(char STATE) {
        this.STATE = STATE;
    }

    public String getYYLX() {
        return YYLX;
    }

    public void setYYLX(String YYLX) {
        this.YYLX = YYLX;
    }

    public String getLSH() {
        return LSH;
    }

    public void setLSH(String LSH) {
        this.LSH = LSH;
    }

    public String getZYH() {
        return ZYH;
    }

    public void setZYH(String ZYH) {
        this.ZYH = ZYH;
    }

    public String getYSDM() {
        return YSDM;
    }

    public void setYSDM(String YSDM) {
        this.YSDM = YSDM;
    }

    public String getYYMC() {
        return YYMC;
    }

    public void setYYMC(String YYMC) {
        this.YYMC = YYMC;
    }

    public String getYYDZ() {
        return YYDZ;
    }

    public void setYYDZ(String YYDZ) {
        this.YYDZ = YYDZ;
    }

    public String getYYNR() {
        return YYNR;
    }

    public void setYYNR(String YYNR) {
        this.YYNR = YYNR;
    }

    public String getLRSJ() {
        return LRSJ;
    }

    public void setLRSJ(String LRSJ) {
        this.LRSJ = LRSJ;
    }

    public String getSRM1() {
        return SRM1;
    }

    public void setSRM1(String SRM1) {
        this.SRM1 = SRM1;
    }

    public String getBZ() {
        return BZ;
    }

    public void setBZ(String BZ) {
        this.BZ = BZ;
    }
}
