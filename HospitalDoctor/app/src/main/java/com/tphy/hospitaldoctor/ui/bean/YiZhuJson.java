package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;

/**
 * Created by ${王琪} on ${2017/11/28}.
 */

public class YiZhuJson implements Serializable {
    /*[{"zyh":"88880",
    "yzh":"10016",
    "ysdm":"0000",
    "kjks":"0439",
    "lx":"1",
    "yzdm":"2662",
    "jl":"25",
    "jldw":"ml"
    ,"dw":"g",
    "yf":"004",
    "pl":"30",
    "bz2":"备注无",
    "sl":"3",
    "dw2":"瓶",
    "cpbz":"",
    "zbybz":"0",
    "YBBZ":"0"}]*/
    private String zyh;
    private String yzh;
    private String ysdm;
    private String kjks;
    private String lx;
    private String yzdm;
    private String jl;
    private String jldw;
    private String dw;
    private String yf;
    private String pl;
    private String bz2;
    private String sl;
    private String dw2;
    private String cpbz;
    private String zbybz;
    private String YBBZ;
    private String psjg;
    private String yzsj;

    public YiZhuJson(String zyh, String yzh, String ysdm, String kjks, String lx, String yzdm, String jl, String jldw, String dw, String yf, String pl, String bz2, String sl, String dw2, String cpbz, String zbybz, String YBBZ,String psjg,String yzsj) {
        this.zyh = zyh;
        this.yzh = yzh;
        this.ysdm = ysdm;
        this.kjks = kjks;
        this.lx = lx;
        this.yzdm = yzdm;
        this.jl = jl;
        this.jldw = jldw;
        this.dw = dw;
        this.yf = yf;
        this.pl = pl;
        this.bz2 = bz2;
        this.sl = sl;
        this.dw2 = dw2;
        this.cpbz = cpbz;
        this.zbybz = zbybz;
        this.YBBZ = YBBZ;
        this.psjg = psjg;
        this.yzsj = yzsj;
    }

    public String getPsjg() {
        return psjg;
    }

    public String getZyh() {
        return zyh;
    }

    public String getYzh() {
        return yzh;
    }

    public String getYsdm() {
        return ysdm;
    }

    public String getKjks() {
        return kjks;
    }

    public String getLx() {
        return lx;
    }

    public String getYzdm() {
        return yzdm;
    }

    public String getJl() {
        return jl;
    }

    public String getJldw() {
        return jldw;
    }

    public String getDw() {
        return dw;
    }

    public String getYf() {
        return yf;
    }

    public String getPl() {
        return pl;
    }

    public String getBz2() {
        return bz2;
    }

    public String getSl() {
        return sl;
    }

    public String getDw2() {
        return dw2;
    }

    public String getCpbz() {
        return cpbz;
    }

    public String getZbybz() {
        return zbybz;
    }

    public String getYBBZ() {
        return YBBZ;
    }

    public String getYzsj() {
        return yzsj;
    }

    public void setBz2(String bz2) {
        this.bz2 = bz2;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }

    public void setPl(String pl) {
        this.pl = pl;
    }
}
