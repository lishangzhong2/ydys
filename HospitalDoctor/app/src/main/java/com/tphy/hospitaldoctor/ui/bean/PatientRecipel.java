package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;

/**
 * Created by tphy on 2018/3/23.
 */

public class PatientRecipel implements Serializable {
    /*"ZYH ": "住院号",
    "DOC_ID": "医生代码",
	"CFSJ": "处方时间",
	"DH": “处方单号”,
	"CFZT": "处方状态",
	"QYYFDM": "取药药房代码",
	"KFMC": "药房名称",
	"JYFSMC": "煎熬方式名称",
	"JYFS": "煎药方式代码 ",
	"FYYQMC": "服用要求名称",
	"FYYQDM": "服用要求代码",
	"YSZT": "医师嘱托",
	"CFFS": "处方付数",
	"GYPLDM": "给药频率代码",
	"GYPLMC": "给药频率名称",
	"YPYFMC": "药品用法名称",
	"YPYFDM": "药品用法代码"

	//新增
	//YFCFZT: 药房的处方状态,涉及到出药
*/
    private String ZYH;
    private String DOC_ID;

    public void setCFSJ(String CFSJ) {
        this.CFSJ = CFSJ;
    }

    private String CFSJ;
    private String DH;
    private String CFZT;
    private String QYYFDM;
    private String KFMC;
    private String JYFSMC;
    private String JYFS;
    private String FYYQMC;
    private String FYYQDM;
    private String YSZT;
    private String CFFS;
    private String GYPLDM;
    private String GYPLMC;
    private String YPYFMC;
    private String YPYFDM;
    private boolean isHerbalChecked;
    //新增
    private String YFCFZT;

    public PatientRecipel(String ZYH, String DOC_ID, String CFSJ, String DH, String CFZT, String QYYFDM, String KFMC, String JYFSMC, String JYFS, String FYYQMC, String FYYQDM, String YSZT, String CFFS, String GYPLDM, String GYPLMC, String YPYFMC, String YPYFDM, boolean isHerbalChecked,String YFCFZT) {
        this.ZYH = ZYH;
        this.DOC_ID = DOC_ID;
        this.CFSJ = CFSJ;
        this.DH = DH;
        this.CFZT = CFZT;
        this.QYYFDM = QYYFDM;
        this.KFMC = KFMC;
        this.JYFSMC = JYFSMC;
        this.JYFS = JYFS;
        this.FYYQMC = FYYQMC;
        this.FYYQDM = FYYQDM;
        this.YSZT = YSZT;
        this.CFFS = CFFS;
        this.GYPLDM = GYPLDM;
        this.GYPLMC = GYPLMC;
        this.YPYFMC = YPYFMC;
        this.YPYFDM = YPYFDM;
        this.isHerbalChecked = isHerbalChecked;
        this.YFCFZT = YFCFZT;
    }

    public String getZYH() {
        return ZYH;
    }

    public String getDOC_ID() {
        return DOC_ID;
    }

    public String getCFSJ() {
        return CFSJ;
    }

    public String getDH() {
        return DH;
    }

    public String getCFZT() {
        return CFZT;
    }

    public String getQYYFDM() {
        return QYYFDM;
    }

    public String getKFMC() {
        return KFMC;
    }

    public String getJYFSMC() {
        return JYFSMC;
    }

    public String getJYFS() {
        return JYFS;
    }

    public String getFYYQMC() {
        return FYYQMC;
    }

    public String getFYYQDM() {
        return FYYQDM;
    }

    public String getYSZT() {
        return YSZT;
    }

    public String getCFFS() {
        return CFFS;
    }

    public String getGYPLDM() {
        return GYPLDM;
    }

    public String getGYPLMC() {
        return GYPLMC;
    }

    public String getYPYFMC() {
        return YPYFMC;
    }

    public String getYPYFDM() {
        return YPYFDM;
    }

    public boolean isHerbalChecked() {
        return isHerbalChecked;
    }

    public void setHerbalChecked(boolean checked) {
        isHerbalChecked = checked;
    }

    public String getYFCFZT() {
        return YFCFZT;
    }

    public void setYFCFZT(String YFCFZT) {
        this.YFCFZT = YFCFZT;
    }
}
