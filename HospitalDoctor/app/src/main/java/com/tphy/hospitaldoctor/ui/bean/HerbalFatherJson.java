package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;

/**
 * Created by tphy on 2018/3/22.
 */

public class HerbalFatherJson implements Serializable {
    /*{zyh:‘住院号’,
    ysdm:‘医生代码’,
    yfdm:‘药房代码’,
    kjks:‘开据科室’,
    xzks:‘病人现住科室’,
    jyfs:‘煎药方式’，
    fyyq:'服药要求',
    .yszt:'医生嘱托'，
    dh:'单号'，cffs:'
    处方付数'，pl:
    ’频率‘，yf:'
    用法'}*/
    private String zyh;
    private String ysdm;
    private String yfdm;
    private String kjks;
    private String xzks;
    private String jyfs;
    private String fyyq;
    private String yszt;
    private String dh;
    private String cffs;
    private String pl;
    private String yf;

    //新增
    private String djfa;
    private String jzjl;

    public HerbalFatherJson(String zyh, String ysdm, String yfdm, String kjks, String xzks, String jyfs, String fyyq, String yszt, String dh, String cffs, String pl, String yf,String djfa,String jzjl) {
        this.zyh = zyh;
        this.ysdm = ysdm;
        this.yfdm = yfdm;
        this.kjks = kjks;
        this.xzks = xzks;
        this.jyfs = jyfs;
        this.fyyq = fyyq;
        this.yszt = yszt;
        this.dh = dh;
        this.cffs = cffs;
        this.pl = pl;
        this.yf = yf;
        this.djfa = djfa;
        this.jzjl = jzjl;
    }
}
