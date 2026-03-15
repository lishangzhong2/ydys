package com.tphy.hospitaldoctor.bjca;

/**
 * Created by Android Studio.
 * User: tphy_ydys
 * Date: 2021/3/12
 * Time: 11:31
 */

public class CaResGetAuthCode {


    /**
     * status : 200
     * message : SUCCESS
     * transId : 1234567
     * data : {"authCode":"{\"id\":\"APP_E39718EFFD514DB293F934E649B747A9\",\"sUrl\":\"https://coss-dev.isignet.cn:18759/coss/mobile/v1/getServiceInfo\",\"o\":\"KHE0DgQEUHoQBVALLzdnYAxSSVA0S0BUGndxEhM7OTM3FWF1YxphcSU1HQMMHS4QChMJY2NxLQ==\",\"cv\":\"2.0\"}","code":"5a3bd40c"}
     */

    private int status;
    private String message;
    private String transId;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * authCode : {"id":"APP_E39718EFFD514DB293F934E649B747A9","sUrl":"https://coss-dev.isignet.cn:18759/coss/mobile/v1/getServiceInfo","o":"KHE0DgQEUHoQBVALLzdnYAxSSVA0S0BUGndxEhM7OTM3FWF1YxphcSU1HQMMHS4QChMJY2NxLQ==","cv":"2.0"}
         * code : 5a3bd40c
         */

        private String authCode;
        private String code;

        public String getAuthCode() {
            return authCode;
        }

        public void setAuthCode(String authCode) {
            this.authCode = authCode;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
