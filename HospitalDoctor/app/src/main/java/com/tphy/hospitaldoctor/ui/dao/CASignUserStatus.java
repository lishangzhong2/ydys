package com.tphy.hospitaldoctor.ui.dao;

import android.arch.persistence.room.Entity;


import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Copyright (C) 2021 北京天鹏恒宇科技发展有限公司 版权所有
 * Copyright (C) 2021 TPHY.Co.,Ltd.  All rights reserved
 * 文件名：(com.tphy.hospitaldoctor.ui.dao)
 * 文件功能描述：CA签名用户状态表
 * author：wcx
 * time：2021/7/13 21:39
 */
@Entity
public class CASignUserStatus {

    @Index(unique = true)
    @Id
    private  String czydm ;//操作员代码
    private String msspid;//msspid
    private String yxbz;//有效标志
    private String username;//操作员名称
    private String cert;
    private String active_time;

    @Generated(hash = 1566476081)
    public CASignUserStatus(String czydm, String msspid, String yxbz,
            String username, String cert, String active_time) {
        this.czydm = czydm;
        this.msspid = msspid;
        this.yxbz = yxbz;
        this.username = username;
        this.cert = cert;
        this.active_time = active_time;
    }

    @Generated(hash = 1505625932)
    public CASignUserStatus() {
    }

    public String getCzydm() {
        return czydm;
    }

    public void setCzydm(String czydm) {
        this.czydm = czydm;
    }

    public String getMsspid() {
        return msspid;
    }

    public void setMsspid(String msspid) {
        this.msspid = msspid;
    }

    public String getYxbz() {
        return yxbz;
    }

    public void setYxbz(String yxbz) {
        this.yxbz = yxbz;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCert() {
        return cert;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }

    public String getActive_time() {
        return active_time;
    }

    public void setActive_time(String active_time) {
        this.active_time = active_time;
    }
}
