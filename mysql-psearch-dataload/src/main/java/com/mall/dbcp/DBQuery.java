package com.mall.dbcp;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.KeyedHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mall.config.DataSourceFactory;
/**
 * DB层
 * @author DOUBLELL
 *
 * @param <T>
 */
@SuppressWarnings("rawtypes")
public class DBQuery<T> {
	private static Logger logger = LoggerFactory.getLogger(DBQuery.class);
	
	private Class clazz;
	
	@SuppressWarnings("unused")
	private RowProcessor mapClazz;

	public DBQuery( Class clazz) {
		this.clazz = clazz;
	}

	public DBQuery(RowProcessor clazz) {
		this.mapClazz = clazz;
	}

	
	public T query(String sql, String name) throws SQLException {
		return query(sql, null, name);
	}

	public T query(String sql, Object param, String name) throws SQLException {
		return query(sql, new Object[] { param }, name);
	}

	@SuppressWarnings("unchecked")
	public T query(String sql, Object[] params, String name)throws SQLException {
		log(sql, params, name);
		DataSource dataSource = DataSourceFactory.getDataSource(name);
		QueryRunner runner = new QueryRunner(dataSource);
		return (T) runner.query(sql, new BeanHandler(clazz), params);
	}

	public List<T> queryList(String sql, String name) throws SQLException {
		return queryList(sql, null, name);
	}

	public List<T> queryList(String sql, Object param, String name,String dbName) throws SQLException {
		return queryList(sql, new Object[] { param }, dbName);
	}

	@SuppressWarnings("unchecked")
	public List<T> queryList(String sql, Object[] params, String name)throws SQLException {
		log(sql, params, name);
		DataSource dataSource = DataSourceFactory.getDataSource(name);
		QueryRunner runner = new QueryRunner(dataSource);
		return (List<T>) runner.query(sql, new BeanListHandler(clazz), params);
	}

	@SuppressWarnings("unchecked")
	public Map<String, T> queryMap(String sql, Object[] params, String name,String key) throws SQLException {
		log(sql, params, name);
		DataSource dataSource = DataSourceFactory.getDataSource(name);
		QueryRunner runner = new QueryRunner(dataSource);
		return (Map<String, T>) runner.query(sql, new KeyedHandler(key), params);
	}

	// sql日志
	private void log(String sql, Object[] params, String name) {
		if (logger.isDebugEnabled()) {
			logger.debug("sql:[" + sql + "] params:" + Arrays.toString(params)+ " datasource name is:" + name);
		}
	}
}
