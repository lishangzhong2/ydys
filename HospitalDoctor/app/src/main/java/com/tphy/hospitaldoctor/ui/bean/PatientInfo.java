package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ${王琪} on ${2017/11/28}.
 */

public class PatientInfo implements Serializable {

    private String cwh;
    private String ksmc;
    private String zzys;
    private String zyh;
    private String hzxm;
    private String hljb;
    private String hznl;
    private String nldw;
    private String ryzd;
    private String ye;
    private String yllx;
    private String hzxb;

    private String blh;
    private String zycs;
    private String ksdm;
    private String bmdm;
    private String rysj;
    private String yj;
    private String fy;
    private String cwdm;
    private String zrys;
    private String zgys;
    private String cybz;
    private String hlks;
    private String gchs;
    private String gchsdm;
    private String lxrxm;
    private String lxrdh;
    private String zzjh;
    private String ocwh;
    private String bmmc;
    private String xx;
    private String gmyw;
    private boolean isSelected;
    private String STATE;

    public PatientInfo(String cwh, String ksmc, String zzys, String zyh, String hzxm, String hljb, String nl, String nldw, String ryzd, String ye, String yllx, String hzxb, String blh, String zycs, String ksdm, String bmdm, String rysj, String yj, String fy, String cwdm, String zrys, String zgys, String cybz, String hlks, String gchs, String gchsdm, String lxrxm, String lxrdh, String zzjh, String ocwh, String bmmc,String xx,String gmyw, boolean isSelected, String STATE) {
        this.cwh = cwh;
        this.ksmc = ksmc;
        this.zzys = zzys;
        this.zyh = zyh;
        this.hzxm = hzxm;
        this.hljb = hljb;
        this.hznl = nl;
        this.nldw = nldw;
        this.ryzd = ryzd;
        this.ye = ye;
        this.yllx = yllx;
        this.hzxb = hzxb;
        this.blh = blh;
        this.zycs = zycs;
        this.ksdm = ksdm;
        this.bmdm = bmdm;
        this.rysj = rysj;
        this.yj = yj;
        this.fy = fy;
        this.cwdm = cwdm;
        this.zrys = zrys;
        this.zgys = zgys;
        this.cybz = cybz;
        this.hlks = hlks;
        this.gchs = gchs;
        this.gchsdm = gchsdm;
        this.lxrxm = lxrxm;
        this.lxrdh = lxrdh;
        this.zzjh = zzjh;
        this.ocwh = ocwh;
        this.bmmc = bmmc;
        this.xx = xx;
        this.gmyw = gmyw;
        this.isSelected = isSelected;
        this.STATE = STATE;
    }

    public String getCwh() {
        return cwh;
    }

    public String getKsmc() {
        return ksmc;
    }

    public String getZzys() {
        return zzys;
    }

    public String getZyh() {
        return zyh;
    }

    public String getHzxm() {
        return hzxm;
    }

    public String getHljb() {
        return hljb;
    }

    public String getHznl() {
        return hznl;
    }

    public String getNldw() {
        return nldw;
    }

    public String getRyzd() {
        return ryzd;
    }

    public String getYe() {
        return ye;
    }

    public String getYllx() {
        return yllx;
    }

    public String getHzxb() {
        return hzxb;
    }

    public String getBlh() {
        return blh;
    }

    public String getZycs() {
        return zycs;
    }

    public String getKsdm() {
        return ksdm;
    }

    public String getBmdm() {
        return bmdm;
    }

    public String getRysj() {
        return rysj;
    }

    public String getYj() {
        return yj;
    }

    public String getFy() {
        return fy;
    }

    public String getCwdm() {
        return cwdm;
    }

    public String getZrys() {
        return zrys;
    }

    public String getZgys() {
        return zgys;
    }

    public String getCybz() {
        return cybz;
    }

    public String getHlks() {
        return hlks;
    }

    public String getGchs() {
        return gchs;
    }

    public String getGchsdm() {
        return gchsdm;
    }

    public String getLxrxm() {
        return lxrxm;
    }

    public String getLxrdh() {
        return lxrdh;
    }

    public String getZzjh() {
        return zzjh;
    }

    public String getOcwh() {
        return ocwh;
    }

    public String getBmmc() {
        return bmmc;
    }

    public String getXx() {
        return xx;
    }

    public void setXx(String xx) {
        this.xx = xx;
    }

    public String getGmyw() {
        return gmyw;
    }

    public void setGmyw(String gmyw) {
        this.gmyw = gmyw;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getSTATE() {
        return STATE;
    }

    public void setSTATE(String STATE) {
        this.STATE = STATE;
    }
}
