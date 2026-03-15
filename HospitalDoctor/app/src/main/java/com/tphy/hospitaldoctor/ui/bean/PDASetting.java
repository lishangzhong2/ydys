package com.tphy.hospitaldoctor.ui.bean;

/**
 * Created by Android Studio.
 * User: tphy_ydys
 * Date: 2020/4/26
 * Time: 10:10
 */

public class PDASetting {

    /**
     * DM : 21
     * MC : 新生儿科室代码（英文逗号分隔）
     * BZ : 1009
     */

    private String DM;
    private String MC;
    private String BZ;

    public String getDM() {
        return DM;
    }

    public void setDM(String DM) {
        this.DM = DM;
    }

    public String getMC() {
        return MC;
    }

    public void setMC(String MC) {
        this.MC = MC;
    }

    public String getBZ() {
        return BZ;
    }

    public void setBZ(String BZ) {
        this.BZ = BZ;
    }
}
