package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;

/**
 * Created by ${王琪} on ${2017/11/28}.
 */

public class UsageDict implements Serializable {
    /* "DM": "206",
        "PYDM": "pggz",
        "MC": "膀胱灌注",
        "YZBZ": "0"*/
    private String DM;
    private String PYDM;
    private String MC;
    private String YZBZ;
    private boolean ischecked;

    public boolean isIschecked() {
        return ischecked;
    }

    public void setIschecked(boolean ischecked) {
        this.ischecked = ischecked;
    }

    public UsageDict(String DM, String MC) {
        this.DM = DM;
        this.MC = MC;
    }

    public String getDM() {
        return DM;
    }

    public void setDM(String DM) {
        this.DM = DM;
    }

    public String getPYDM() {
        return PYDM;
    }

    public void setPYDM(String PYDM) {
        this.PYDM = PYDM;
    }

    public String getMC() {
        return MC;
    }

    public void setMC(String MC) {
        this.MC = MC;
    }

    public String getYZBZ() {
        return YZBZ;
    }

    public void setYZBZ(String YZBZ) {
        this.YZBZ = YZBZ;
    }
}
