package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;

/**
 * Created by tphy on 2018/3/23.
 */

public class PatientRecipelDetails implements Serializable {
    /*"DH": “处方单号”,
        "YPDM": "药品代码",
        "DOSE": "剂量",
        "DW": "单位",
        "BZ": "备注",
        "PC": "批次 ",
        "PH": "批号",
        "SXH": "顺序号",
        "CF_FLAG": "处方分割标志",
        "LSJ": "零售价",
        "CDDM": "产地代码",
        "YPMC": "药品名称"

    */
    private String DH;
    private String YPDM;
    private String DOSE;
    private String DW;
    private String BZ;
    private String PC;
    private String PH;
    private String SXH;
    private String CN_FLAG;
    private String LSJ;
    private String CDDM;
    private String YPMC;
    //新增是否点击
    private boolean ischecked;
    private String GG;

    public PatientRecipelDetails(String DH, String YPDM, String DOSE, String DW, String BZ, String PC, String PH, String SXH, String CN_FLAG, String LSJ, String CDDM, String YPMC) {
        this.DH = DH;
        this.YPDM = YPDM;
        this.DOSE = DOSE;
        this.DW = DW;
        this.BZ = BZ;
        this.PC = PC;
        this.PH = PH;
        this.SXH = SXH;
        this.CN_FLAG = CN_FLAG;
        this.LSJ = LSJ;
        this.CDDM = CDDM;
        this.YPMC = YPMC;
    }

    public void setDH(String DH) {
        this.DH = DH;
    }

    public void setYPDM(String YPDM) {
        this.YPDM = YPDM;
    }

    public void setDOSE(String DOSE) {
        this.DOSE = DOSE;
    }

    public void setDW(String DW) {
        this.DW = DW;
    }

    public void setBZ(String BZ) {
        this.BZ = BZ;
    }

    public void setPC(String PC) {
        this.PC = PC;
    }

    public void setPH(String PH) {
        this.PH = PH;
    }

    public void setSXH(String SXH) {
        this.SXH = SXH;
    }

    public void setCN_FLAG(String CN_FLAG) {
        this.CN_FLAG = CN_FLAG;
    }

    public void setLSJ(String LSJ) {
        this.LSJ = LSJ;
    }

    public void setCDDM(String CDDM) {
        this.CDDM = CDDM;
    }

    public void setYPMC(String YPMC) {
        this.YPMC = YPMC;
    }

    public String getDH() {
        return DH;
    }

    public String getYPDM() {
        return YPDM;
    }

    public String getDOSE() {
        return DOSE;
    }

    public String getDW() {
        return DW;
    }

    public String getBZ() {
        return BZ;
    }

    public String getPC() {
        return PC;
    }

    public String getPH() {
        return PH;
    }

    public String getSXH() {
        return SXH;
    }

    public String getCN_FLAG() {
        return CN_FLAG;
    }

    public String getLSJ() {
        return LSJ;
    }

    public String getCDDM() {
        return CDDM;
    }

    public String getYPMC() {
        return YPMC;
    }

    public boolean isIschecked() {
        return ischecked;
    }

    public void setIschecked(boolean ischecked) {
        this.ischecked = ischecked;
    }

    public String getGG() {
        return GG;
    }

    public void setGG(String GG) {
        this.GG = GG;
    }
}
