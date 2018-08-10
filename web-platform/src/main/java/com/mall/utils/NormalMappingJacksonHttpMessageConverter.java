/**  
 * @Project: web-platform
 * @Title: NormalMappingJacksonHttpMessageConverter.java
 * @Package: com.mall.utils
 * @Description: AJAX操作面向对象辅助类
 * @author: QIJJ 
 * @since: 2015-7-21 下午2:11:32
 * @Copyright: 2015. All rights reserved.
 * @Version: v1.0   
 */
package com.mall.utils;

import java.io.IOException;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

/**
 * @ClassName NormalMappingJacksonHttpMessageConverter
 * @Description: AJAX操作面向对象辅助类
 * @author: QIJJ
 * @since: 2015-7-21 下午2:11:32
 */
public class NormalMappingJacksonHttpMessageConverter extends MappingJacksonHttpMessageConverter {

	@Override
	protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
		return super.readInternal(clazz, inputMessage);
	}

	@Override
	protected void writeInternal(Object obj, HttpOutputMessage message) throws IOException, HttpMessageNotWritableException {
		message.getHeaders().set("Cache-Control", "no-cache");
		message.getHeaders().set("Content-Type", "application/json");
		super.writeInternal(obj, message);
	}

} 
