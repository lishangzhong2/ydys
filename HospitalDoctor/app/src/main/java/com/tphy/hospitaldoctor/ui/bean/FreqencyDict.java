package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;

/**
 * Created by ${王琪} on ${2017/11/28}.
 */

public class FreqencyDict implements Serializable {
    /* "DM": "34",
        "PYDM": "biw",
        "MC": "biw",
        "MC1": "2/周",
        "CS": 2,
        "ZWPY": "",
        "ZQ": 7*/
    private String DM;
    private String PYDM;
    private String MC;
    private String MC1;
    private String CS;
    private String ZWPY;
    private String ZQ;
    private boolean ischecked;

    public boolean isIschecked() {
        return ischecked;
    }

    public void setIschecked(boolean ischecked) {
        this.ischecked = ischecked;
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

    public String getMC1() {
        return MC1;
    }

    public void setMC1(String MC1) {
        this.MC1 = MC1;
    }

    public String getCS() {
        return CS;
    }

    public void setCS(String CS) {
        this.CS = CS;
    }

    public String getZWPY() {
        return ZWPY;
    }

    public void setZWPY(String ZWPY) {
        this.ZWPY = ZWPY;
    }

    public String getZQ() {
        return ZQ;
    }

    public void setZQ(String ZQ) {
        this.ZQ = ZQ;
    }
}
