package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;

/**
 * Created by ${王琪} on ${2017/11/28}.
 */

public class SavedYizhu implements Serializable {
    private String name;
    private String time;
    private String gg;
    private String kucun;
    private int yfPosition;
    private String jl;
    private int plPosition;
    private String sl;
    private boolean zby;
    private boolean ps;
    private String bz;
    private String jlbl;
    private String yizhuType;
    private boolean enable;
    private String yzdm;
    private boolean canEditCount;
    private String dm;
    private String YBBZ;
    private boolean reGetData;
    private String pldm;
    private String dw;
    private String dw2;

    public SavedYizhu(String name, String time, String gg, String kucun, int yfPosition, String jl, int plPosition, String sl, boolean zby, boolean ps, String bz, String jlbl, String yizhuType, boolean enable, String yzdm, boolean canEditCount, String dm, String ybbz, String pldm, String dw, String dw2) {
        this.name = name;
        this.time = time;
        this.gg = gg;
        this.kucun = kucun;
        this.yfPosition = yfPosition;
        this.jl = jl;
        this.plPosition = plPosition;
        this.sl = sl;
        this.zby = zby;
        this.ps = ps;
        this.bz = bz;
        this.jlbl = jlbl;
        this.yizhuType = yizhuType;
        this.enable = enable;
        this.yzdm = yzdm;
        this.canEditCount = canEditCount;
        this.dm = dm;
        this.YBBZ = ybbz;
        this.pldm = pldm;
        this.dw = dw;
        this.dw2 = dw2;

    }


    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public String getDw2() {
        return dw2;
    }

    public void setDw2(String dw2) {
        this.dw2 = dw2;
    }

    public boolean isReGetData() {
        return reGetData;
    }

    public void setReGetData(boolean reGetData) {
        this.reGetData = reGetData;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getGg() {
        return gg;
    }

    public String getKucun() {
        return kucun;
    }

    public int getYfPosition() {
        return yfPosition;
    }

    public String getJl() {
        return jl;
    }

    public int getPlPosition() {
        return plPosition;
    }

    public String getSl() {
        return sl;
    }

    public boolean isZby() {
        return zby;
    }

    public boolean isPs() {
        return ps;
    }

    public String getBz() {
        return bz;
    }

    public String getJlbl() {
        return jlbl;
    }

    public String getYizhuType() {
        return yizhuType;
    }

    public boolean isEnable() {
        return enable;
    }

    public String getYzdm() {
        return yzdm;
    }

    public boolean isCanEditCount() {
        return canEditCount;
    }

    public String getDm() {
        return dm;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setGg(String gg) {
        this.gg = gg;
    }

    public void setKucun(String kucun) {
        this.kucun = kucun;
    }

    public void setYfPosition(int yfPosition) {
        this.yfPosition = yfPosition;
    }

    public void setJl(String jl) {
        this.jl = jl;
    }

    public void setPlPosition(int plPosition) {
        this.plPosition = plPosition;
    }

    public void setZby(boolean zby) {
        this.zby = zby;
    }

    public void setPs(boolean ps) {
        this.ps = ps;
    }

    public void setJlbl(String jlbl) {
        this.jlbl = jlbl;
    }

    public void setYizhuType(String yizhuType) {
        this.yizhuType = yizhuType;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void setYzdm(String yzdm) {
        this.yzdm = yzdm;
    }

    public void setCanEditCount(boolean canEditCount) {
        this.canEditCount = canEditCount;
    }

    public String getYBBZ() {
        return YBBZ;
    }


    public String getPldm() {
        return pldm;
    }

    public void setPldm(String pldm) {
        this.pldm = pldm;
    }

}
