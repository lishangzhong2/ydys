package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;

/**
 * Created by ${王琪} on ${2017/11/28}.
 */

public class XiJunResult implements Serializable {
    /* "细菌名称": "奇异变形杆菌",
        "抗生素名称": "氨苄西林",
        "结果": "≥32",
        "敏感度": "R",
        "耐药": "",
        "中介": "",
        "敏感": "",
        "标本号": "20180111-003_001"*/
    private String 细菌名称;
    private String 抗生素名称;
    private String 结果;
    private String 敏感度;
    private String 耐药;
    private String 中介;
    private String 敏感;
    private String 标本号;

    public String get细菌名称() {
        return 细菌名称;
    }

    public void set细菌名称(String 细菌名称) {
        this.细菌名称 = 细菌名称;
    }

    public String get抗生素名称() {
        return 抗生素名称;
    }

    public void set抗生素名称(String 抗生素名称) {
        this.抗生素名称 = 抗生素名称;
    }

    public String get结果() {
        return 结果;
    }

    public void set结果(String 结果) {
        this.结果 = 结果;
    }

    public String get敏感度() {
        return 敏感度;
    }

    public void set敏感度(String 敏感度) {
        this.敏感度 = 敏感度;
    }

    public String get耐药() {
        return 耐药;
    }

    public void set耐药(String 耐药) {
        this.耐药 = 耐药;
    }

    public String get中介() {
        return 中介;
    }

    public void set中介(String 中介) {
        this.中介 = 中介;
    }

    public String get敏感() {
        return 敏感;
    }

    public void set敏感(String 敏感) {
        this.敏感 = 敏感;
    }

    public String get标本号() {
        return 标本号;
    }

    public void set标本号(String 标本号) {
        this.标本号 = 标本号;
    }
}
