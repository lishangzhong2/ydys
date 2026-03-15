package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;

/**
 * Created by ${王琪} on ${2017/11/28}.
 */

public class JianChaInfo implements Serializable{

    private String ZYH;
    private String EXAM_NO;
    private String EXAM_CLASS;
    private String EXAM_SUB_CLASS;
    private String REPORTER;
    private String EXAM_DATE_TIME;
    private String PERFORMED_BY;
    private String REQ_PHYSICIAN;
    private String REQ_DATE_TIME;
    private String REQ_DEPT;
    private String EXAM_NAME;
    private String RESULT_STATUS;
//    private String CCBZ;//彩超标志
//    public String getCCBZ() {
//        return CCBZ;
//    }
//
//    public void setCCBZ(String CCBZ) {
//        this.CCBZ = CCBZ;
//    }


    private boolean checked;

    public void setZYH(String ZYH) {
        this.ZYH = ZYH;
    }

    public void setEXAM_NO(String EXAM_NO) {
        this.EXAM_NO = EXAM_NO;
    }

    public void setEXAM_CLASS(String EXAM_CLASS) {
        this.EXAM_CLASS = EXAM_CLASS;
    }

    public void setEXAM_SUB_CLASS(String EXAM_SUB_CLASS) {
        this.EXAM_SUB_CLASS = EXAM_SUB_CLASS;
    }

    public void setREPORTER(String REPORTER) {
        this.REPORTER = REPORTER;
    }

    public void setEXAM_DATE_TIME(String EXAM_DATE_TIME) {
        this.EXAM_DATE_TIME = EXAM_DATE_TIME;
    }

    public void setPERFORMED_BY(String PERFORMED_BY) {
        this.PERFORMED_BY = PERFORMED_BY;
    }

    public void setREQ_PHYSICIAN(String REQ_PHYSICIAN) {
        this.REQ_PHYSICIAN = REQ_PHYSICIAN;
    }

    public void setREQ_DATE_TIME(String REQ_DATE_TIME) {
        this.REQ_DATE_TIME = REQ_DATE_TIME;
    }

    public void setREQ_DEPT(String REQ_DEPT) {
        this.REQ_DEPT = REQ_DEPT;
    }

    public void setEXAM_NAME(String EXAM_NAME) {
        this.EXAM_NAME = EXAM_NAME;
    }

    public void setRESULT_STATUS(String RESULT_STATUS) {
        this.RESULT_STATUS = RESULT_STATUS;
    }

    public String getZYH() {
        return ZYH;
    }

    public String getEXAM_NO() {
        return EXAM_NO;
    }

    public String getEXAM_CLASS() {
        return EXAM_CLASS;
    }

    public String getEXAM_SUB_CLASS() {
        return EXAM_SUB_CLASS;
    }

    public String getREPORTER() {
        return REPORTER;
    }

    public String getEXAM_DATE_TIME() {
        return EXAM_DATE_TIME;
    }

    public String getPERFORMED_BY() {
        return PERFORMED_BY;
    }

    public String getREQ_PHYSICIAN() {
        return REQ_PHYSICIAN;
    }

    public String getREQ_DATE_TIME() {
        return REQ_DATE_TIME;
    }

    public String getREQ_DEPT() {
        return REQ_DEPT;
    }

    public String getEXAM_NAME() {
        return EXAM_NAME;
    }

    public String getRESULT_STATUS() {
        return RESULT_STATUS;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
