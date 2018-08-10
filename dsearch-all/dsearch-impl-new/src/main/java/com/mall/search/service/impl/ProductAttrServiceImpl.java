package com.mall.search.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.mall.dsearch.vo.CatalogAttrVal;
import com.mall.search.cache.CacheFactory;
import com.mall.search.constant.Constant;
import com.mall.search.vo.TdAttrValCode;
/**
 * 商品属性（更新缓存）
 * @author Administrator
 *
 */
public class ProductAttrServiceImpl{
	private static final Logger LOGGER = Logger.getLogger(ProductAttrServiceImpl.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void initProductAttrCache(String param){
		LOGGER.info("init productAttrCache start ...");
		String id=param.substring(12);
		String sql = "select id, attrname, attrvalname from td_attr_val_code where id = "+id;
	    
		List<TdAttrValCode> attrValCodes = jdbcTemplate.query(sql.toString(), new RowMapper<TdAttrValCode>(){
			public TdAttrValCode mapRow(ResultSet rs,int rowNum) throws SQLException {
				TdAttrValCode attrValCode = new TdAttrValCode();
				attrValCode.setId(rs.getLong("id"));
				attrValCode.setAttrname(rs.getString("attrname"));
				attrValCode.setAttrvalname(rs.getString("attrvalname"));
				return attrValCode;
			}
			
		});

		
		TdAttrValCode tdAttrValCode = attrValCodes.get(0);
		CatalogAttrVal cateAttrVal = new CatalogAttrVal();
		cateAttrVal.setId(param);
		cateAttrVal.setName(tdAttrValCode.getAttrvalname());
		//临时占用该字段 放入缓存中，方便属性归集
		cateAttrVal.setImgUrl(tdAttrValCode.getAttrname());
		//把属性放入缓存中
		CacheFactory.getCacheOper("pb_").put(param, cateAttrVal,  Constant.JEDIS_PROATTR_LOES_TIME);

		LOGGER.info("init productAttrCache end ...");
	}
	
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
