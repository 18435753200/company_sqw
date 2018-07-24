package com.mall.config;

import com.mall.common.ConstantManage;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *  加载配置bean
 *  @author doublell
 */
public class BeanConfiguration {

	//加载数据源上下文
	private static ClassPathXmlApplicationContext context;
	public static void init(){
		context = new ClassPathXmlApplicationContext(new String[] { "datasource.xml" });
		DataSourceFactory.getDataSource(ConstantManage.DBNAME);
	}

	public static <T> T getBean(Class<T> clazz) {
		return context.getBean(clazz);
	}

	public static Object getBean(String name) {
		return context.getBean(name);
	}

	public static <T> T getBean(String name, Class<T> clazz) {
		return context.getBean(name, clazz);
	}
}
