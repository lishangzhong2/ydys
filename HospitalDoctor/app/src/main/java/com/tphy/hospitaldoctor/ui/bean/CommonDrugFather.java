package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;

/**
 * Created by tphy on 2018/3/21.
 */

public class CommonDrugFather implements Serializable {
    /*"CYCFH": "20171007075833",
    "CYCFMC": "6床",
	"KSDM": "9011",
	"YSDM": "607",
	"LRSJ": "2017/10/7 7:59:00",
	"JB": 0
*/
    private String CYCFH;
    private String CYCFMC;
    private String KSDM;
    private String YSDM;
    private String LRSJ;
    private String JB;
    private boolean isSelected;

    public CommonDrugFather(String CYCFH, String CYCFMC, String KSDM, String YSDM, String LRSJ, String JB) {
        this.CYCFH = CYCFH;
        this.CYCFMC = CYCFMC;
        this.KSDM = KSDM;
        this.YSDM = YSDM;
        this.LRSJ = LRSJ;
        this.JB = JB;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getCYCFH() {
        return CYCFH;
    }

    public String getCYCFMC() {
        return CYCFMC;
    }

    public String getKSDM() {
        return KSDM;
    }

    public String getYSDM() {
        return YSDM;
    }

    public String getLRSJ() {
        return LRSJ;
    }

    public String getJB() {
        return JB;
    }
}
