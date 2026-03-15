package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;

/**
 * Created by ${王琪} on ${2017/11/28}.
 */

public class FileType implements Serializable {
    /*"INP_NO": "88880",
        "FILE_NO": 14,
        "TOPIC": "首 次 病 程 记 录",
        "CREATOR_NAME": "侯海生",
        "CREATOR_ID": "0064",
        "CREATE_DATE_TIME": "2017/10/13 21:30:14",
        "MR_CODE": "AC99130",
        "FILE_FLAG": "T",
        "LAST_MODIFY_TIME": "2017/10/13 21:50:03",
"CNAME": "病历病程",
        "CCODE ": "AB"*/
    private String INP_NO;
    private String FILE_NO;
    private String TOPIC;
    private String CREATOR_NAME;
    private String CREATOR_ID;
    private String CREATE_DATE_TIME;
    private String MR_CODE;
    private String FILE_FLAG;
    private String LAST_MODIFY_TIME;
    private String CNAME;
    private String CCODE;

    public String getCNAME() {
        return CNAME;
    }

    public void setCNAME(String CNAME) {
        this.CNAME = CNAME;
    }

    public String getCCODE() {
        return CCODE;
    }

    public void setCCODE(String CCODE) {
        this.CCODE = CCODE;
    }

    public String getINP_NO() {
        return INP_NO;
    }

    public void setINP_NO(String INP_NO) {
        this.INP_NO = INP_NO;
    }

    public String getFILE_NO() {
        return FILE_NO;
    }

    public void setFILE_NO(String FILE_NO) {
        this.FILE_NO = FILE_NO;
    }

    public String getTOPIC() {
        return TOPIC;
    }

    public void setTOPIC(String TOPIC) {
        this.TOPIC = TOPIC;
    }

    public String getCREATOR_NAME() {
        return CREATOR_NAME;
    }

    public void setCREATOR_NAME(String CREATOR_NAME) {
        this.CREATOR_NAME = CREATOR_NAME;
    }

    public String getCREATOR_ID() {
        return CREATOR_ID;
    }

    public void setCREATOR_ID(String CREATOR_ID) {
        this.CREATOR_ID = CREATOR_ID;
    }

    public String getCREATE_DATE_TIME() {
        return CREATE_DATE_TIME;
    }

    public void setCREATE_DATE_TIME(String CREATE_DATE_TIME) {
        this.CREATE_DATE_TIME = CREATE_DATE_TIME;
    }

    public String getMR_CODE() {
        return MR_CODE;
    }

    public void setMR_CODE(String MR_CODE) {
        this.MR_CODE = MR_CODE;
    }

    public String getFILE_FLAG() {
        return FILE_FLAG;
    }

    public void setFILE_FLAG(String FILE_FLAG) {
        this.FILE_FLAG = FILE_FLAG;
    }

    public String getLAST_MODIFY_TIME() {
        return LAST_MODIFY_TIME;
    }

    public void setLAST_MODIFY_TIME(String LAST_MODIFY_TIME) {
        this.LAST_MODIFY_TIME = LAST_MODIFY_TIME;
    }
}
