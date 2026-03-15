package com.tphy.hospitaldoctor.ui.bean;

/**
 * Created by Administrator on 2021-7-20.
 */
/*
jldm:剂量代码,就是剂量和剂量单位两列拼在一起
cs:医生使用的剂量与单位的次数
 */
public class DesageDict {

    private String JLDM;
    private String JL;
    private int cs;
    private boolean ischecked;

    public String getJLDM() {
        return JLDM;
    }

    public void setJLDM(String JLDM) {
        this.JLDM = JLDM;
    }

    public String getJL() {
        return JL;
    }

    public void setJL(String JL) {
        this.JL = JL;
    }

    public int getCs() {
        return cs;
    }

    public void setCs(int cs) {
        this.cs = cs;
    }

    public boolean isIschecked() {
        return ischecked;
    }

    public void setIschecked(boolean ischecked) {
        this.ischecked = ischecked;
    }
}
