package com.tphy.hospitaldoctor.ui.bean;

/**
 * Copyright (C) 2021 北京天鹏恒宇科技发展有限公司 版权所有
 * Copyright (C) 2021 TPHY.Co.,Ltd.  All rights reserved
 * 文件名：(com.tphy.hospitaldoctor.ui.bean)
 * 文件功能描述：xxx
 * author：tphy_ydys
 * time：2021/7/16 19:23
 */

public class CaDoBean {
    //医嘱名称
    private String yzmc;
    //
    private String zxdh;
    //医嘱类型
    private String yzlx;
    //医嘱数量
    private String yzsl;
    //
    private String zxsl;
    //住院号
    private String zyh;
    //医嘱号
    private String yzh;
    //
    private String zxhs;
    //
    private String jcxm ;
    //医嘱时间
    private String yzsj ;
    //是否点击
    private boolean checked;


    public String getYzmc() {
        return yzmc;
    }

    public void setYzmc(String yzmc) {
        this.yzmc = yzmc;
    }

    public String getZxdh() {
        return zxdh;
    }

    public void setZxdh(String zxdh) {
        this.zxdh = zxdh;
    }

    public String getYzlx() {
        return yzlx;
    }

    public void setYzlx(String yzlx) {
        this.yzlx = yzlx;
    }

    public String getYzsl() {
        return yzsl;
    }

    public void setYzsl(String yzsl) {
        this.yzsl = yzsl;
    }

    public String getZxsl() {
        return zxsl;
    }

    public void setZxsl(String zxsl) {
        this.zxsl = zxsl;
    }

    public String getZyh() {
        return zyh;
    }

    public void setZyh(String zyh) {
        this.zyh = zyh;
    }

    public String getYzh() {
        return yzh;
    }

    public void setYzh(String yzh) {
        this.yzh = yzh;
    }

    public String getZxhs() {
        return zxhs;
    }

    public void setZxhs(String zxhs) {
        this.zxhs = zxhs;
    }

    public String getJcxm() {
        return jcxm;
    }

    public void setJcxm(String jcxm) {
        this.jcxm = jcxm;
    }

    public String getYzsj() {
        return yzsj;
    }

    public void setYzsj(String yzsj) {
        this.yzsj = yzsj;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public CaDoBean(String yzmc, String zxdh, String yzlx, String yzsl, String zxsl, String zyh, String yzh, String zxhs, String jcxm, String yzsj, boolean checked) {
        this.yzmc = yzmc;
        this.zxdh = zxdh;
        this.yzlx = yzlx;
        this.yzsl = yzsl;
        this.zxsl = zxsl;
        this.zyh = zyh;
        this.yzh = yzh;
        this.zxhs = zxhs;
        this.jcxm = jcxm;
        this.yzsj = yzsj;
        this.checked = checked;
    }
}
