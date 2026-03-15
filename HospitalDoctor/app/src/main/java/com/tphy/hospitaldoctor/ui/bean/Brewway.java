package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;

/**
 * Created by tphy on 2018/3/22.
 */

public class Brewway implements Serializable {
    /*"JYFSDM": "11",
        "JYFSMC": "水煮",
        "PYM": "sz"
*/
    private String JYFSDM;
    private String JYFSMC;
    private String PYM;

    public Brewway(String JYFSDM, String JYFSMC, String PYM) {
        this.JYFSDM = JYFSDM;
        this.JYFSMC = JYFSMC;
        this.PYM = PYM;
    }

    public String getJYFSDM() {
        return JYFSDM;
    }

    public String getJYFSMC() {
        return JYFSMC;
    }

    public String getPYM() {
        return PYM;
    }
}
