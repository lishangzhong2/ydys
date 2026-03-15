package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;

/**
 * Created by ${王琪} on ${2017/11/28}.
 */

public class MessageInfo implements Serializable {
    /*"MSG_ID": "0000000020",
        "MSG_MC": "new Message",
        "MSG_FBSJ": "2018/1/23 16:54:10",
        "MSG_URL": "http://192.168.1.69/txydys/MDManager/MdMessageShow.aspx?msgid=0000000020",
        "MSG_FSR": "767",
        "MSG_YYDR": ""*/

    private String MSG_ID;
    private String MSG_MC;
    private String MSG_FBSJ;
    private String MSG_URL;
    private String MSG_FSR;
    private String MSG_YYDR;
    private String YSMC;

    public String getYSMC() {
        return YSMC;
    }

    public void setYSMC(String YSMC) {
        this.YSMC = YSMC;
    }

    public String getMSG_ID() {
        return MSG_ID;
    }

    public void setMSG_ID(String MSG_ID) {
        this.MSG_ID = MSG_ID;
    }

    public String getMSG_MC() {
        return MSG_MC;
    }

    public void setMSG_MC(String MSG_MC) {
        this.MSG_MC = MSG_MC;
    }

    public String getMSG_FBSJ() {
        return MSG_FBSJ;
    }

    public void setMSG_FBSJ(String MSG_FBSJ) {
        this.MSG_FBSJ = MSG_FBSJ;
    }

    public String getMSG_URL() {
        return MSG_URL;
    }

    public void setMSG_URL(String MSG_URL) {
        this.MSG_URL = MSG_URL;
    }

    public String getMSG_FSR() {
        return MSG_FSR;
    }

    public void setMSG_FSR(String MSG_FSR) {
        this.MSG_FSR = MSG_FSR;
    }

    public String getMSG_YYDR() {
        return MSG_YYDR;
    }

    public void setMSG_YYDR(String MSG_YYDR) {
        this.MSG_YYDR = MSG_YYDR;
    }
}
