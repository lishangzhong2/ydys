package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;

/**
 * Created by tphy on 2018/3/25.
 */

public class AttendenceAdvance implements Serializable {
    private String ksdm;
    private String ysdm;
    private String swxm;
    private String swStatus;
    private String xwxm;
    private String xwStatus;
    private String wsxm;
    private String wsStatus;
    private String czsj;

    public AttendenceAdvance(String ksdm, String ysdm, String swxm, String swStatus, String xwxm, String xwStatus, String wsxm, String wsStatus, String czsj) {
        this.ksdm = ksdm;
        this.ysdm = ysdm;
        this.swxm = swxm;
        this.swStatus = swStatus;
        this.xwxm = xwxm;
        this.xwStatus = xwStatus;
        this.wsxm = wsxm;
        this.wsStatus = wsStatus;
        this.czsj = czsj;
    }

    public String getKsdm() {
        return ksdm;
    }

    public String getYsdm() {
        return ysdm;
    }

    public String getSwxm() {
        return swxm;
    }

    public String getSwStatus() {
        return swStatus;
    }

    public String getXwxm() {
        return xwxm;
    }

    public String getXwStatus() {
        return xwStatus;
    }

    public String getWsxm() {
        return wsxm;
    }

    public String getWsStatus() {
        return wsStatus;
    }

    public String getCzsj() {
        return czsj;
    }
}
