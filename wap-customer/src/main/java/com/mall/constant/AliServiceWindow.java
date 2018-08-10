package com.mall.constant;


import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author zhoushuyi
 * @date 2018/6/25
 */
public final class AliServiceWindow {

    private String  PID;
    private String  appId;
    private String  appPrivateKey;
    private String  appPublicKey;
    private String  alipayPublicKey;
    private String  alipayGateway;
    private String  alipayGrantType;

    private String alipayAuthorizeCode;






    public String getAlipayAuthorizeCode() {
        return alipayAuthorizeCode;
    }

    public void setAlipayAuthorizeCode(String alipayAuthorizeCode) {
        this.alipayAuthorizeCode = alipayAuthorizeCode;
    }

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppPrivateKey() {
        return appPrivateKey;
    }

    public void setAppPrivateKey(String appPrivateKey) {
        this.appPrivateKey = appPrivateKey;
    }

    public String getAppPublicKey() {
        return appPublicKey;
    }

    public void setAppPublicKey(String appPublicKey) {
        this.appPublicKey = appPublicKey;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public void setAlipayPublicKey(String alipayPublicKey) {
        this.alipayPublicKey = alipayPublicKey;
    }

    public String getAlipayGateway() {
        return alipayGateway;
    }

    public void setAlipayGateway(String alipayGateway) {
        this.alipayGateway = alipayGateway;
    }

    public String getAlipayGrantType() {
        return alipayGrantType;
    }

    public void setAlipayGrantType(String alipayGrantType) {
        this.alipayGrantType = alipayGrantType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("PID", PID)
                .append("appId", appId)
                .append("appPrivateKey", appPrivateKey)
                .append("appPublicKey", appPublicKey)
                .append("alipayPublicKey", alipayPublicKey)
                .append("alipayGateway", alipayGateway)
                .append("alipayGrantType", alipayGrantType)
                .toString();
    }
}
