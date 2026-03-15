package com.tphy.hospitaldoctor.bjca;

import java.util.List;

/**
 * Created by Android Studio.
 * User: tphy_ydys
 * Date: 2021/3/13
 * Time: 14:12
 */

public class CAResQueryCert
{

    /**
     * status : 200
     * message : SUCCESS
     * data : {"certInfos":[{"deviceId":"868464041696906cn.org.bjca.signet.component.core.coreproj","algoPolicy":"SM2","useage":"SIGN","cert":"MIIDAzCCAqagAwIBAgISIAKP0522MfZ4ek6egYgbfxaSMAwGCCqBHM9VAYN1BQAwPjELMAkGA1UEBgwCQ04xDTALBgNVBAoMBEJKQ0ExDTALBgNVBAsMBEJKQ0ExETAPBgNVBAMMCExPQ0FMU00yMB4XDTE5MDkxNjAzNTEwNFoXDTI0MDkxNjAzNTEwNFowcDELMAkGA1UEBgwCQ04xDzANBgNVBAMMBueOi+eEsTFQME4GCgmSJomT8ixkAQEMQDk4MDBkODE4NTZkZDU1MmQ5YTRmY2RlNzAwYmQ0YzRhMTVhYjgzNGY5MDE2MWZmMjhmMmI5OGFkYmQwZDcyMTgwWTATBgcqhkjOPQIBBggqgRzPVQGCLQNCAAQjWdy5fNF/xLy9qznWSDb7V9D08Cdg30KO/wMUuALie5LhQh7Mh1jwf6aK7IKz00Fh6NAMKMDnOZZmvRGeCdLoo4IBTjCCAUowCwYDVR0PBAQDAgbAMAkGA1UdEwQCMAAwEwYDVR0lBAwwCgYIKwYBBQUHAwMwgYUGA1UdHwR+MHwwOKA2oDSGMmh0dHBzOi8vY3JsLmlzaWduZXQuY24vY3JsL0xPQ0FMU00yL0xPQ0FMU00yXzAuY3JsMECgPqA8hjpodHRwczovL2NybC5pc2lnbmV0LmNuL2NybC9MT0NBTFNNMi9pbmMvTE9DQUxTTTJfaW5jXzAuY3JsMB0GA1UdDgQWBBTWx/oPhawn8JfOGft7idMMK+TPHzAfBgNVHSMEGDAWgBQ78fsiTPugyfgIrS+tCqf3oTpz1zBTBgNVHSAETDBKMEgGCiqBHIbvMgYEAQEwOjA4BggrBgEFBQcCARYsaHR0cHM6Ly9jcmwuaXNpZ25ldC5jbi9jcHMvTE9DQUxTTTIvY3BzLmh0bWwwDAYIKoEcz1UBg3UFAANJADBGAiEAx1hXq/eCShn/2SvresyxvoeIHsboULmRSX+brZsBV/UCIQDcb4yHzVM55hufPbBRhpzbPEOyRpXq2AH6clFt8XGGWw=="}]}
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
        private List<CertInfosBean> certInfos;

        public List<CertInfosBean> getCertInfos() {
            return certInfos;
        }

        public void setCertInfos(List<CertInfosBean> certInfos) {
            this.certInfos = certInfos;
        }

        public static class CertInfosBean {
            /**
             * deviceId : 868464041696906cn.org.bjca.signet.component.core.coreproj
             * algoPolicy : SM2
             * useage : SIGN
             * cert : MIIDAzCCAqagAwIBAgISIAKP0522MfZ4ek6egYgbfxaSMAwGCCqBHM9VAYN1BQAwPjELMAkGA1UEBgwCQ04xDTALBgNVBAoMBEJKQ0ExDTALBgNVBAsMBEJKQ0ExETAPBgNVBAMMCExPQ0FMU00yMB4XDTE5MDkxNjAzNTEwNFoXDTI0MDkxNjAzNTEwNFowcDELMAkGA1UEBgwCQ04xDzANBgNVBAMMBueOi+eEsTFQME4GCgmSJomT8ixkAQEMQDk4MDBkODE4NTZkZDU1MmQ5YTRmY2RlNzAwYmQ0YzRhMTVhYjgzNGY5MDE2MWZmMjhmMmI5OGFkYmQwZDcyMTgwWTATBgcqhkjOPQIBBggqgRzPVQGCLQNCAAQjWdy5fNF/xLy9qznWSDb7V9D08Cdg30KO/wMUuALie5LhQh7Mh1jwf6aK7IKz00Fh6NAMKMDnOZZmvRGeCdLoo4IBTjCCAUowCwYDVR0PBAQDAgbAMAkGA1UdEwQCMAAwEwYDVR0lBAwwCgYIKwYBBQUHAwMwgYUGA1UdHwR+MHwwOKA2oDSGMmh0dHBzOi8vY3JsLmlzaWduZXQuY24vY3JsL0xPQ0FMU00yL0xPQ0FMU00yXzAuY3JsMECgPqA8hjpodHRwczovL2NybC5pc2lnbmV0LmNuL2NybC9MT0NBTFNNMi9pbmMvTE9DQUxTTTJfaW5jXzAuY3JsMB0GA1UdDgQWBBTWx/oPhawn8JfOGft7idMMK+TPHzAfBgNVHSMEGDAWgBQ78fsiTPugyfgIrS+tCqf3oTpz1zBTBgNVHSAETDBKMEgGCiqBHIbvMgYEAQEwOjA4BggrBgEFBQcCARYsaHR0cHM6Ly9jcmwuaXNpZ25ldC5jbi9jcHMvTE9DQUxTTTIvY3BzLmh0bWwwDAYIKoEcz1UBg3UFAANJADBGAiEAx1hXq/eCShn/2SvresyxvoeIHsboULmRSX+brZsBV/UCIQDcb4yHzVM55hufPbBRhpzbPEOyRpXq2AH6clFt8XGGWw==
             */

            private String deviceId;
            private String algoPolicy;
            private String useage;
            private String cert;

            public String getDeviceId() {
                return deviceId;
            }

            public void setDeviceId(String deviceId) {
                this.deviceId = deviceId;
            }

            public String getAlgoPolicy() {
                return algoPolicy;
            }

            public void setAlgoPolicy(String algoPolicy) {
                this.algoPolicy = algoPolicy;
            }

            public String getUseage() {
                return useage;
            }

            public void setUseage(String useage) {
                this.useage = useage;
            }

            public String getCert() {
                return cert;
            }

            public void setCert(String cert) {
                this.cert = cert;
            }
        }
    }
}
