package com.tphy.hospitaldoctor.bjca;

/**
 * Created by Android Studio.
 * User: tphy_ydys
 * Date: 2021/3/13
 * Time: 16:14
 */

public class CAResAddSignJob {


    /**
     * status : 200
     * message : SUCCESS
     * data : {"signDataId":"SD_4ff8dec3-10b0-422e-9671-5447cc18e52e","qrCode":"{\"id\":\"APP_E39718EFFD514DB293F934E649B747A9\",\"sUrl\":\"https://coss-dev.isignet.cn:18759/coss/mobile/v1/getServiceInfo\",\"o\":\"KHE0DgQEUHoQY3VneTU1aAsVBkFtAwBTCGBnYWIKXVxEdwMdBAx5ZDAzXkgAR3JXEh0aOSojNU1KRyEJdX4TFG8lNiIcGQocYggSABZ9cS4=\",\"cv\":\"2.0\"}"}
     */

    private int status;
    private String message;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * signDataId : SD_4ff8dec3-10b0-422e-9671-5447cc18e52e
         * qrCode : {"id":"APP_E39718EFFD514DB293F934E649B747A9","sUrl":"https://coss-dev.isignet.cn:18759/coss/mobile/v1/getServiceInfo","o":"KHE0DgQEUHoQY3VneTU1aAsVBkFtAwBTCGBnYWIKXVxEdwMdBAx5ZDAzXkgAR3JXEh0aOSojNU1KRyEJdX4TFG8lNiIcGQocYggSABZ9cS4=","cv":"2.0"}
         */

        private String signDataId;
        private String qrCode;

        public String getSignDataId() {
            return signDataId;
        }

        public void setSignDataId(String signDataId) {
            this.signDataId = signDataId;
        }

        public String getQrCode() {
            return qrCode;
        }

        public void setQrCode(String qrCode) {
            this.qrCode = qrCode;
        }
    }
}
