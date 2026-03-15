package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;

public class TaoyizhuBean implements Serializable {
    /*"YZLX": "医嘱类型",
	"DM": "医嘱代码",
	"PYDM": "拼音代码",
	"MC": "医嘱名称",
	"KC": "库存",
	"LSJ": "零售价",
	"YBMC": "医保名称",
	"YLSY": "医嘱内容",
	"GG": "规格",
	"GROUP_MA_CODE": "套医嘱代码",
	"ORDER_NO": 顺序号,
	"DOSE": "剂量",
	"UNIT": "单位",
	"USAGE": "用法代码",
	"YPYFMC": "用法名称",
	"FREQUENCY": "频率代码",
	"GYPLMC": "频率名称",
	"WZBZ": "文字医嘱备注",
	"GROUP_NO": "组号",
	"GROUP_FLAG": "组标志",
	"SKINTEST_FLAG": "皮试标志",
	"SELFPROVIDED_MEDICINE_FLAG": "自备药标志",
	"TRANSFER_BLOOD_FLAG": "输血标志"
*/
    private String YZLX;
    private String DM;
    private String PYDM;
    private String MC;
    private String KC;
    private String LSJ;
    private String YBMC;
    private String YLSY;
    private String GG;
    private String GROUP_MA_CODE;
    private String ORDER_NO;
    private String DOSE;
    private String UNIT;
    private String USAGE;
    private String YPYFMC;
    private String FREQUENCY;
    private String GYPLMC;
    private String WZBZ;
    private String GROUP_NO;
    private String GROUP_FLAG;
    private String SKINTEST_FLAG;
    private String SELFPROVIDED_MEDICINE_FLAG;
    private String TRANSFER_BLOOD_FLAG;
    private String AMOUNT;
    private String YBBZ;
    private boolean checked;

    public TaoyizhuBean(String YZLX, String DM, String PYDM, String MC, String KC, String LSJ, String YBMC, String YLSY, String GG, String GROUP_MA_CODE, String ORDER_NO, String DOSE, String UNIT, String USAGE, String YPYFMC, String FREQUENCY, String GYPLMC, String WZBZ, String GROUP_NO, String GROUP_FLAG, String SKINTEST_FLAG, String SELFPROVIDED_MEDICINE_FLAG, String TRANSFER_BLOOD_FLAG,String AMOUNT,String YBBZ) {
        this.YZLX = YZLX;
        this.DM = DM;
        this.PYDM = PYDM;
        this.MC = MC;
        this.KC = KC;
        this.LSJ = LSJ;
        this.YBMC = YBMC;
        this.YLSY = YLSY;
        this.GG = GG;
        this.GROUP_MA_CODE = GROUP_MA_CODE;
        this.ORDER_NO = ORDER_NO;
        this.DOSE = DOSE;
        this.UNIT = UNIT;
        this.USAGE = USAGE;
        this.YPYFMC = YPYFMC;
        this.FREQUENCY = FREQUENCY;
        this.GYPLMC = GYPLMC;
        this.WZBZ = WZBZ;
        this.GROUP_NO = GROUP_NO;
        this.GROUP_FLAG = GROUP_FLAG;
        this.SKINTEST_FLAG = SKINTEST_FLAG;
        this.SELFPROVIDED_MEDICINE_FLAG = SELFPROVIDED_MEDICINE_FLAG;
        this.TRANSFER_BLOOD_FLAG = TRANSFER_BLOOD_FLAG;
        this.AMOUNT=AMOUNT;
        this.YBBZ = YBBZ;
    }

    public String getYZLX() {
        return YZLX;
    }

    public String getDM() {
        return DM;
    }

    public String getPYDM() {
        return PYDM;
    }

    public String getMC() {
        return MC;
    }

    public String getKC() {
        return KC;
    }

    public String getLSJ() {
        return LSJ;
    }

    public String getYBMC() {
        return YBMC;
    }

    public String getYLSY() {
        return YLSY;
    }

    public String getGG() {
        return GG;
    }

    public String getGROUP_MA_CODE() {
        return GROUP_MA_CODE;
    }

    public String getORDER_NO() {
        return ORDER_NO;
    }

    public String getDOSE() {
        return DOSE;
    }

    public String getUNIT() {
        return UNIT;
    }

    public String getUSAGE() {
        return USAGE;
    }

    public String getYPYFMC() {
        return YPYFMC;
    }

    public String getFREQUENCY() {
        return FREQUENCY;
    }

    public String getGYPLMC() {
        return GYPLMC;
    }

    public String getWZBZ() {
        return WZBZ;
    }

    public String getGROUP_NO() {
        return GROUP_NO;
    }

    public String getGROUP_FLAG() {
        return GROUP_FLAG;
    }

    public String getSKINTEST_FLAG() {
        return SKINTEST_FLAG;
    }

    public String getSELFPROVIDED_MEDICINE_FLAG() {
        return SELFPROVIDED_MEDICINE_FLAG;
    }

    public String getTRANSFER_BLOOD_FLAG() {
        return TRANSFER_BLOOD_FLAG;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getAMOUNT() {
        return AMOUNT;
    }

    public String getYBBZ() {
        return YBBZ;
    }
}
