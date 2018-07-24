package com.mall.config;

import javax.sql.DataSource;

public class DataSourceFactory {
	//获取数据源信息
	public static DataSource getDataSource(String name) {
		DataSourceConfiguration configuration = BeanConfiguration.getBean(DataSourceConfiguration.class);
		return configuration.getDataSources().get(name);
	}

}
