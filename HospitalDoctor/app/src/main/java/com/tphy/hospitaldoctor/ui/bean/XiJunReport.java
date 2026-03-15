package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;

/**
 * Created by ${王琪} on ${2017/11/28}.
 */

public class XiJunReport implements Serializable {
    private String name;
    private String result;
    private String unit;
    private String range;
    private String resultReference;

    public XiJunReport(String name, String result, String unit, String range, String resultReference) {
        this.name = name;
        this.result = result;
        this.unit = unit;
        this.range = range;
        this.resultReference = resultReference;
    }

    public String getName() {
        return name;
    }

    public String getResult() {
        return result;
    }

    public String getUnit() {
        return unit;
    }

    public String getRange() {
        return range;
    }

    public String getResultReference() {
        return resultReference;
    }
}
