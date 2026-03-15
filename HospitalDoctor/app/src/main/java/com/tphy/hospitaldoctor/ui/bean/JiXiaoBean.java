package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;

public class JiXiaoBean implements Serializable {
    /*"KSDM": "科室代码",
    "CZYDM": "医师代码",
    "KHXM": "考核项目名称",
    "SJZ": "实际值",
    "BZZ": "标准值",
    "ZBBZ": "指标标准",
    "SRM": "备用1",
    "SXH": "顺序号"

*/

    private String KSDM;
    private String CZYDM;
    private String KHXM;
    private String SJZ;
    private String BZZ;
    private String ZBBZ;
    private String SRM;
    private String SXH;

//    public JiXiaoBean(String KSDM, String CZYDM, String KHXM, String SJZ, String BZZ, String ZBBZ, String SRM, String SXH) {
//        this.KSDM = KSDM;
//        this.CZYDM = CZYDM;
//        this.KHXM = KHXM;
//        this.SJZ = SJZ;
//        this.BZZ = BZZ;
//        this.ZBBZ = ZBBZ;
//        this.SRM = SRM;
//        this.SXH = SXH;
//    }


    public void setKSDM(String KSDM) {
        this.KSDM = KSDM;
    }

    public void setCZYDM(String CZYDM) {
        this.CZYDM = CZYDM;
    }

    public void setKHXM(String KHXM) {
        this.KHXM = KHXM;
    }

    public void setSJZ(String SJZ) {
        this.SJZ = SJZ;
    }

    public void setBZZ(String BZZ) {
        this.BZZ = BZZ;
    }

    public void setZBBZ(String ZBBZ) {
        this.ZBBZ = ZBBZ;
    }

    public void setSRM(String SRM) {
        this.SRM = SRM;
    }

    public void setSXH(String SXH) {
        this.SXH = SXH;
    }

    public String getKSDM() {
        return KSDM;
    }

    public String getCZYDM() {
        return CZYDM;
    }

    public String getKHXM() {
        return KHXM;
    }

    public String getSJZ() {
        return SJZ;
    }

    public String getBZZ() {
        return BZZ;
    }

    public String getZBBZ() {
        return ZBBZ;
    }

    public String getSRM() {
        return SRM;
    }

    public String getSXH() {
        return SXH;
    }
}
