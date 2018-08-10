package com.mall.constant;

/**
 *
 * 第三方账户OAuth2常量信息。不建议使用CommonConstant,便于抽离，迁移至服务
 *
 * @see CommonConstant
 * @author zhoushuyi
 * @date 2018/6/26
 */
public final class ThirdAccountConstant {

    /**
     * 第三方账户标识
     */
    public static final String ACCOUNT_ALIPAY = "alipay";
    public static final String ACCOUNT_WEIXIN = "weixin";


    /**
     * 第三方授权code标识
     */
    public static final String AUTH_CODE_ALIPAY = "AUTH_CODE_ALIPAY";
    // 目前用的都是CommonConstant中的WEIXIN_JSAPI_CODE
    public static final String AUTH_CODE_WEIXIN = "AUTH_CODE_WEIXIN";

    /**
     * 第三方accesstoken
     */
    public static final String ACCESS_TOKEN_ALIPAY = "ACCESS_TOKEN_ALIPAY";


    /**
     * 第三方授权openID标识
     */
    public static final String AUTH_OPENID_ALIPAY = "AUTH_OPENID_ALIPAY";

    /**
     * 数据库前缀
     */
    public static final String PREFIX_WEIXIN = "wx";
    public static final String PREFIX_ALIPAY = "ali";



    /**
     * 第三方授权code openid 有效时间
     */
    public static final Integer AUTH_CODE_VAILED_TIME = 7 * 3600 * 24;
    public static final Integer AUTH_OPENID_VAILED_TIME = 7 * 3600 * 24;


    public static final Integer ACCESS_TOKEN_VAILED_TIME = 2 * 3600;//两小时

    /**
     * 完成第三方参数转换
     * @param con
     * @return
     */
    public static String convertConstant(String con) {
        if (con == null) {
            return null;
        }
        if (PREFIX_ALIPAY.equals(con)) {
            return ACCOUNT_ALIPAY;
        } else if (PREFIX_WEIXIN.equals(con)) {
            return ACCOUNT_WEIXIN;
        } else if (ACCOUNT_ALIPAY.equals(con)) {
            return PREFIX_ALIPAY;
        } else if (ACCOUNT_WEIXIN.equals(con)) {
            return PREFIX_WEIXIN;
        }
        return null;
    }
}
