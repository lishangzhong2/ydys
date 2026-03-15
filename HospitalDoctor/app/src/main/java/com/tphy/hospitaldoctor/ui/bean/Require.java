package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;

/**
 * Created by tphy on 2018/3/22.
 */

public class Require implements Serializable {
    /*"DM": "001",
        "PYDM": "kf",
        "MC": "口服",
        "SXH": 1

*/
    private String DM;
    private String PYDM;
    private String MC;
    private String SXH;

    public Require(String DM, String PYDM, String MC, String SXH) {
        this.DM = DM;
        this.PYDM = PYDM;
        this.MC = MC;
        this.SXH = SXH;
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

    public String getSXH() {
        return SXH;
    }
}
