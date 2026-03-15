package com.tphy.hospitaldoctor.bjca;

/**
 * Created by Android Studio.
 * User: tphy_ydys
 * Date: 2021/3/12
 * Time: 14:39
 */

public class CaResQueryUserInfo {

    /**
     * status : 200
     * message : SUCCESS
     * transId : 1234567
     * data : {"userId":"5079691c3f4257da881456d15fe4f9dc5d4dac1062560a8577acba38e7fe1bb3","userName":"王翅翔","orgId":"root"}
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
         * userId : 5079691c3f4257da881456d15fe4f9dc5d4dac1062560a8577acba38e7fe1bb3
         * userName : 王翅翔
         * orgId : root
         */

        private String userId;
        private String userName;
        private String orgId;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }
    }
}
