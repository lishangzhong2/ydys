package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;

/**
 * Created by tphy on 2018/3/21.
 */

public class CommonDrugChild implements Serializable {
    /*"MA_CODE": "医嘱代码",
    "ZCYCF_MA_CODE": "常用处方编号",
	"YPMC": "陈皮",
	"SXH": 顺序号,
	"JL": "2",
	"DW": "小袋",
	"BZ": "",
	"CYCFMC": "王庆尔",
	"GYPLMC": "2/日",给药频率名称
	"GYPLDM": "02",
	"YPYFDM": "050",
	"YPYFMC": "口服",
	"JYFSDM": "11",
	"JYFSMC": "水煮",
	"FYYQ": "002",服用要求
	"FYYQMC": "饭后服用",
	"FS": "3",付数
	"YSZT": ""医师嘱托
*/
    private String MA_CODE;
    private String ZCYCF_MA_CODE;
    private String YPMC;
    private String SXH;
    private String JL;
    private String DW;
    private String BZ;
    private String CYCFMC;
    private String GYPLMC;
    private String GYPLDM;
    private String YPYFDM;
    private String YPYFMC;
    private String JYFSDM;
    private String JYFSMC;
    private String FYYQ;
    private String FYYQMC;
    private String FS;
    private String YSZT;
    private boolean isChecked;
    private boolean isPicked;

    public CommonDrugChild(String MA_CODE, String ZCYCF_MA_CODE, String YPMC, String SXH, String JL, String DW, String BZ, String CYCFMC, String GYPLMC, String GYPLDM, String YPYFDM, String YPYFMC, String JYFSDM, String JYFSMC, String FYYQ, String FYYQMC, String FS, String YSZT, boolean isChecked) {
        this.MA_CODE = MA_CODE;
        this.ZCYCF_MA_CODE = ZCYCF_MA_CODE;
        this.YPMC = YPMC;
        this.SXH = SXH;
        this.JL = JL;
        this.DW = DW;
        this.BZ = BZ;
        this.CYCFMC = CYCFMC;
        this.GYPLMC = GYPLMC;
        this.GYPLDM = GYPLDM;
        this.YPYFDM = YPYFDM;
        this.YPYFMC = YPYFMC;
        this.JYFSDM = JYFSDM;
        this.JYFSMC = JYFSMC;
        this.FYYQ = FYYQ;
        this.FYYQMC = FYYQMC;
        this.FS = FS;
        this.YSZT = YSZT;
        this.isChecked = isChecked;
    }

    public String getMA_CODE() {
        return MA_CODE;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getZCYCF_MA_CODE() {
        return ZCYCF_MA_CODE;
    }

    public String getYPMC() {
        return YPMC;
    }

    public String getSXH() {
        return SXH;
    }

    public String getJL() {
        return JL;
    }

    public String getDW() {
        return DW;
    }

    public String getBZ() {
        return BZ;
    }

    public String getCYCFMC() {
        return CYCFMC;
    }

    public String getGYPLMC() {
        return GYPLMC;
    }

    public String getGYPLDM() {
        return GYPLDM;
    }

    public String getYPYFDM() {
        return YPYFDM;
    }

    public String getYPYFMC() {
        return YPYFMC;
    }

    public String getJYFSDM() {
        return JYFSDM;
    }

    public String getJYFSMC() {
        return JYFSMC;
    }

    public String getFYYQ() {
        return FYYQ;
    }

    public String getFYYQMC() {
        return FYYQMC;
    }

    public String getFS() {
        return FS;
    }

    public String getYSZT() {
        return YSZT;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public boolean isPicked() {
        return isPicked;
    }

    public void setPicked(boolean picked) {
        isPicked = picked;
    }
}
