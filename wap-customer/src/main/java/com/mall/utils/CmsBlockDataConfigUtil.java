package com.mall.utils;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mall.pay.common.StringUtils;

public class CmsBlockDataConfigUtil {
private static Properties properties;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CmsBlockDataConfigUtil.class);
	
	static class SingletonHolder {
		static CmsBlockDataConfigUtil instance = new CmsBlockDataConfigUtil();
		static {
			try {
				properties = new Properties();
				InputStream resourceAsStream = CmsBlockDataConfigUtil.class.getClassLoader().getResourceAsStream("/cmsblockdataconfig.properties");
				properties.load(resourceAsStream);
			} catch (Exception e) {
				LOGGER.error(e.getMessage(),e);
			}
		}
	}

	public static CmsBlockDataConfigUtil getInstance() {
		return SingletonHolder.instance;
	}

	/**
	 * cms block id
	 * @return
	 */
	public  String findCmsBlockId(String key) {
		String value = "";
		try {
			if(StringUtils.isNotNull(key))
				value =  properties.getProperty(key);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally{
			LOGGER.info("CmsBlockDataConfigUtil key = "+key+" CmsBlockDataId  = "+value);
		}
		return value;
	}
			}
