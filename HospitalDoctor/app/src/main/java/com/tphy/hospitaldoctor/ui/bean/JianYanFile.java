package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ${王琪} on ${2017/11/28}.
 */

public class JianYanFile implements Serializable {
    private String name;
    private List<JianYanReport> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<JianYanReport> getList() {
        return list;
    }

    public void setList(List<JianYanReport> list) {
        this.list = list;
    }
}
