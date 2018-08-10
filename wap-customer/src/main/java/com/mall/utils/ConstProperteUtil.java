package com.mall.utils;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConstProperteUtil {
	
	private static Properties properties;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConstProperteUtil.class);
	
	static class SingletonHolder {
		static ConstProperteUtil instance = new ConstProperteUtil();
		static {
			try {
				properties = new Properties();
				InputStream resourceAsStream = ConstProperteUtil.class.getClassLoader().getResourceAsStream("/cpsConstants.properties");
				properties.load(resourceAsStream);
			} catch (Exception e) {
				LOGGER.error(e.getMessage(),e);
			}
		}
	}

	public static ConstProperteUtil getInstance() {
		return SingletonHolder.instance;
	}

	/**
	 * cps 推广域
	 * @return
	 */
	public String findCpsDomain() {
		String url = "";
		try {
			url =  properties.getProperty("CPS_DOMAIN");
		} catch (Exception e) {
			url = ".testwap2.zhongjumall.com";
		} finally{
			LOGGER.info("CPS_DOMAIN = "+url);
		}
		return url;
	}
			
	/**
	 * cps 领克特 来源标示
	 * @return
	 */
	public String findSrcLkt() {
		String url = "";
		try {
			url =  properties.getProperty("CPS_SRC_LKT");
		} catch (Exception e) {
			url = "LTINFO";
		} finally{
			LOGGER.info("CPS_SRC_LKT = "+url);
		}
		return url;
	}

	/**
	 * cps 领克特 cookie 有效期
	 * @return
	 */
	public String findCpsRdLkt() {
		String url = "";
		try {
			url =  properties.getProperty("CPS_RD_LKT");
		} catch (Exception e) {
			url = "15";
		} finally{
			LOGGER.info("CPS_RD_LKT = "+url);
		}
		return url;
	}
		
	/**
	 * cps 领克特 回传数据地址
	 * @return
	 */
	public String findCpsHcurlLkt() {
		String url = "";
		try {
			url =  properties.getProperty("CPS_HCURL_LKT");
		} catch (Exception e) {
			url = "http://service.linktech.cn/purchase_cps.php";
		} finally{
			LOGGER.info("CPS_HCURL_LKT = "+url);
		}
		return url;
	}
	
	/**
	 * 广告主在linktech的id
	 * @return
	 */
	public String findCpsMerchantIdLkt() {
		String url = "";
		try {
			url =  properties.getProperty("CPS_MERCHANT_ID_LKT");
		} catch (Exception e) {
			url = "bolcps";
		} finally{
			LOGGER.info("CPS_MERCHANT_ID_LKT = "+url);
		}
		return url;
	}
			
	/**
	 * 购买人账号(手机号)
	 * @return
	 */
	public String findCpsMbrIdLkt() {
		String url = "";
		try {
			url =  properties.getProperty("CPS_MBR_ID_LKT");
		} catch (Exception e) {
			url = "11111111111";
		} finally{
			LOGGER.info("CPS_MBR_ID_LKT = "+url);
		}
		return url;
	}
	
	/**
	 * 领克特查询key
	 * @return
	 */
	public String findCpsFindKeyLkt() {
		String url = "";
		try {
			url =  properties.getProperty("CPS_FINDKEY_LKT");
		} catch (Exception e) {
			url = "87F9268688FB75C4109CA73340D03C8CAA";
		} finally{
			LOGGER.info("CPS_FINDKEY_LKT = "+url);
		}
		return url;
	}
	
	/**
	 * 领克特查询开始时间
	 * @return
	 */
	public String findCpsStartTimeLkt() {
		String url = "";
		try {
			url =  properties.getProperty("CPS_STARTTMIE_LKT");
		} catch (Exception e) {
			url = "15:00:00";
		} finally{
			LOGGER.info("CPS_STARTTMIE_LKT = "+url);
		}
		return url;
	}
	
	/**
	 * 领克特查询结束时间
	 * @return
	 */
	public String findCpsEndTimeLkt() {
		String url = "";
		try {
			url =  properties.getProperty("CPS_ENDTMIE_LKT");
		} catch (Exception e) {
			url = "23:59:59";
		} finally{
			LOGGER.info("CPS_ENDTMIE_LKT = "+url);
		}
		return url;
	}
	 
	/**
	 * 亿起发 来源标示
	 * @return
	 */
	public String findCpsSrcYqf() {
		String url = "";
		try {
			url =  properties.getProperty("CPS_SRC_YQF");
		} catch (Exception e) {
			url = "YIQIFAWAP";
		} finally{
			LOGGER.info("CPS_SRC_YQF = "+url);
		}
		return url;
	}

	/**
	 * 亿起发 cookie 保存时长：天
	 * @return
	 */
	public String findCpsRdYqf() {
		String url = "";
		try {
			url =  properties.getProperty("CPS_RD_YQF");
		} catch (Exception e) {
			url = "15";
		} finally{
			LOGGER.error("CPS_RD_YQF = "+url);
		}
		return url;
	}
	
	/**
	 * 亿起发 实时数据回传地址
	 * @return
	 */
	public String findCpsHcurlYqf() {
		String url = "";
		try {
			url =  properties.getProperty("CPS_HCURL_YQF");
		} catch (Exception e) {
			url = "http://o.yiqifa.com/servlet/handleCpsInterIn";
		} finally{
			LOGGER.error("CPS_HCURL_YQF = "+url);
		}
		return url;
	}
	
	/**
	 * 亿起发分配  需要与领克特确认
	 * @return
	 */
	public String findCpsInteridYqf() {
		String url = "";
		try {
			url =  properties.getProperty("CPS_INTERID_YQF");
		} catch (Exception e) {
			url = "123456";
		} finally{
			LOGGER.error("CPS_INTERID_YQF = "+url);
		}
		return url;
	}
	
	/**
	 * 亿起发 查询接口key
	 * @return
	 */
	public String findCpsFindKeyYqf() {
		String url = "";
		try {
			url =  properties.getProperty("CPS_FINDKEY_YQF");
		} catch (Exception e) {
			url = "87F927994FB75C4109CA73340D03CDHJK";
		} finally{
			LOGGER.error("CPS_FINDKEY_YQF = "+url);
		}
		return url;
	}
	
	/**
	 * 亿起发 查询接口 允许开始时间
	 * @return
	 */
	public String findCpsStartTimeYqf() {
		String url = "";
		try {
			url =  properties.getProperty("CPS_STARTTMIE_YQF");
		} catch (Exception e) {
			url = "15:00:00";
		} finally{
			LOGGER.error("CPS_STARTTMIE_YQF = "+url);
		}
		return url;
	}
	
	/**
	 * 亿起发 查询接口 允许时间段结束时间
	 * @return
	 */
	public String findCpsEndTimeYqf() {
		String url = "";
		try {
			url =  properties.getProperty("CPS_ENDTMIE_YQF");
		} catch (Exception e) {
			url = "15:00:00";
		} finally{
			LOGGER.error("CPS_ENDTMIE_YQF = "+url);
		}
		return url;
	}
}
