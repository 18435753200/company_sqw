package com.mall.config;

import java.util.Map;

import javax.sql.DataSource;
/**
 * 数据源配置管理
 * @author DOUBLELL
 *
 */
public class DataSourceConfiguration {
	
	private Map<String, DataSource> dataSources;

	public DataSourceConfiguration(Map<String, DataSource> dataSources) {
		super();
		this.dataSources = dataSources;
	}

	public Map<String, DataSource> getDataSources() {
		return dataSources;
	}

}
