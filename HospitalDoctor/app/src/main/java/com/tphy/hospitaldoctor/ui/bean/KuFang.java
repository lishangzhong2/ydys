package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;

/**
 * Created by tphy on 2018/3/22.
 */

public class KuFang implements Serializable {
    /*"KFDM": "0305",
        "KFMC": "门诊中药房",
        "KZYFDM": "1111"
*/
    private String KFDM;
    private String KFMC;
    private String KZYFDM;

    public KuFang(String KFDM, String KFMC, String KZYFDM) {
        this.KFDM = KFDM;
        this.KFMC = KFMC;
        this.KZYFDM = KZYFDM;
    }

    public String getKFDM() {
        return KFDM;
    }

    public String getKFMC() {
        return KFMC;
    }

    public String getKZYFDM() {
        return KZYFDM;
    }
}
