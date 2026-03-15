package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;

/**
 * Created by ${王琪} on ${2017/11/28}.
 */

public class CareFileType implements Serializable {
    /*"CCODE": "AB",
        "CNAME": "病历",
        "CTYPE": "2",
        "PCODE": "00",
        "SNO": 1*/
    private String CCODE;
    private String CNAME;
    private String CTYPE;
    private String PCODE;
    private String SNO;

    public String getCCODE() {
        return CCODE;
    }

    public void setCCODE(String CCODE) {
        this.CCODE = CCODE;
    }

    public String getCNAME() {
        return CNAME;
    }

    public void setCNAME(String CNAME) {
        this.CNAME = CNAME;
    }

    public String getCTYPE() {
        return CTYPE;
    }

    public void setCTYPE(String CTYPE) {
        this.CTYPE = CTYPE;
    }

    public String getPCODE() {
        return PCODE;
    }

    public void setPCODE(String PCODE) {
        this.PCODE = PCODE;
    }

    public String getSNO() {
        return SNO;
    }

    public void setSNO(String SNO) {
        this.SNO = SNO;
    }
}
