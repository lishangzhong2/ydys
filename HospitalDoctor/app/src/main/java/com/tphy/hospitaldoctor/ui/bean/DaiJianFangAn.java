package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2021-8-3.
 */

public class DaiJianFangAn implements Serializable {

        private String DJFAMC;


        private String DJFADM;

    public DaiJianFangAn(String DJFAMC, String DJFADM) {
        this.DJFAMC = DJFAMC;
        this.DJFADM = DJFADM;
    }

    public String getDJFAMC() {
        return DJFAMC;
    }

    public void setDJFAMC(String DJFAMC) {
        this.DJFAMC = DJFAMC;
    }

    public String getDJFADM() {
        return DJFADM;
    }

    public void setDJFADM(String DJFADM) {
        this.DJFADM = DJFADM;
    }
}
