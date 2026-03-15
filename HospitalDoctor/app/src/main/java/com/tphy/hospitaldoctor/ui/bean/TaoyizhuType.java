package com.tphy.hospitaldoctor.ui.bean;

import java.io.Serializable;
import java.util.List;

public class TaoyizhuType implements Serializable {
    private String CUSTOM_CATEGORY_NAME;
    private List<Child> CUSTOM_CATEGORY_LIST;

    public String getCUSTOM_CATEGORY_NAME() {
        return CUSTOM_CATEGORY_NAME;
    }

    public void setCUSTOM_CATEGORY_NAME(String CUSTOM_CATEGORY_NAME) {
        this.CUSTOM_CATEGORY_NAME = CUSTOM_CATEGORY_NAME;
    }

    public List<Child> getCUSTOM_CATEGORY_LIST() {
        return CUSTOM_CATEGORY_LIST;
    }

    public void setCUSTOM_CATEGORY_LIST(List<Child> CUSTOM_CATEGORY_LIST) {
        this.CUSTOM_CATEGORY_LIST = CUSTOM_CATEGORY_LIST;
    }

    public class Child {
        /*"GROUP_MA_CODE": "套医嘱代码",
		"GROUP_MA_NAME": "套医嘱名称",
		"APPLY_RANGE": "套医嘱级别",
		"DOCTOR_CODE": "套医嘱医生代码",
		"DEPT_CODE": "套医嘱的科室代码",
		"CREATEPERSON": "套医嘱的创建人",
		"MA_FLAG": "长期套医嘱/临时套医嘱标志",
		"CREATEDATE": "套医嘱的创建时间",
		"CUSTOM_CATEGORY": "套医嘱部门医嘱名称"
*/
        private String GROUP_MA_CODE;
        private String GROUP_MA_NAME;
        private String APPLY_RANGE;
        private String DOCTOR_CODE;
        private String DEPT_CODE;
        private String CREATEPERSON;
        private String MA_FLAG;
        private String CREATEDATE;
        private String CUSTOM_CATEGORY;

        public String getGROUP_MA_CODE() {
            return GROUP_MA_CODE;
        }

        public void setGROUP_MA_CODE(String GROUP_MA_CODE) {
            this.GROUP_MA_CODE = GROUP_MA_CODE;
        }

        public String getGROUP_MA_NAME() {
            return GROUP_MA_NAME;
        }

        public void setGROUP_MA_NAME(String GROUP_MA_NAME) {
            this.GROUP_MA_NAME = GROUP_MA_NAME;
        }

        public String getAPPLY_RANGE() {
            return APPLY_RANGE;
        }

        public void setAPPLY_RANGE(String APPLY_RANGE) {
            this.APPLY_RANGE = APPLY_RANGE;
        }

        public String getDOCTOR_CODE() {
            return DOCTOR_CODE;
        }

        public void setDOCTOR_CODE(String DOCTOR_CODE) {
            this.DOCTOR_CODE = DOCTOR_CODE;
        }

        public String getDEPT_CODE() {
            return DEPT_CODE;
        }

        public void setDEPT_CODE(String DEPT_CODE) {
            this.DEPT_CODE = DEPT_CODE;
        }

        public String getCREATEPERSON() {
            return CREATEPERSON;
        }

        public void setCREATEPERSON(String CREATEPERSON) {
            this.CREATEPERSON = CREATEPERSON;
        }

        public String getMA_FLAG() {
            return MA_FLAG;
        }

        public void setMA_FLAG(String MA_FLAG) {
            this.MA_FLAG = MA_FLAG;
        }

        public String getCREATEDATE() {
            return CREATEDATE;
        }

        public void setCREATEDATE(String CREATEDATE) {
            this.CREATEDATE = CREATEDATE;
        }

        public String getCUSTOM_CATEGORY() {
            return CUSTOM_CATEGORY;
        }

        public void setCUSTOM_CATEGORY(String CUSTOM_CATEGORY) {
            this.CUSTOM_CATEGORY = CUSTOM_CATEGORY;
        }
    }
}
