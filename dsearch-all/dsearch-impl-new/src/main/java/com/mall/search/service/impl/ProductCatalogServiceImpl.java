package com.mall.search.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.mall.dsearch.po.SearchCateDispTree;
import com.mall.search.cache.CacheFactory;
import com.mall.search.constant.Constant;
import com.mall.search.vo.TdCateDisp;
/**
 * 类目缓存
 * @author Administrator
 *
 */
public class ProductCatalogServiceImpl {
	private static final Logger LOGGER = Logger.getLogger(ProductCatalogServiceImpl.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void initCatalogCache(String param) {
		LOGGER.info("类目:"+param+" 开始加入缓存...");
		String sql = "";
		
		sql = "select cate_disp_id,cate_pub_id, disp_name, disp_name_cn, par_cate_disp_id, sortval, leaf,type from td_cate_disp where valid = '1' and display = '1' and cate_disp_id = "+param;
		
		List<TdCateDisp> tdCateDispList = jdbcTemplate.query(sql.toString(), new RowMapper<TdCateDisp>(){
			
			public TdCateDisp mapRow(ResultSet rs, int rowNum) throws SQLException {
				TdCateDisp disp = new TdCateDisp();
				disp.setCate_disp_id(rs.getString("cate_disp_id"));
				disp.setDisp_name(rs.getString("disp_name"));
				disp.setDisp_name_cn(rs.getString("disp_name_cn"));
				disp.setPar_cate_disp_id(rs.getString("par_cate_disp_id"));
				disp.setSortval(rs.getInt("sortval"));
				disp.setLeaf(rs.getString("leaf"));
				disp.setType(rs.getString("type"));
				return disp;
			}
		});
		Map<String,List<String>> mapcate = new HashMap<String,List<String>>();
		Map<String,List<String>> mapcate_ = new HashMap<String,List<String>>();
		for (TdCateDisp tdCateDisp : tdCateDispList) {

			String catePids = "";
			String rootCid = "";
			String cateDispId = "";
			String catePubId = "";
			String cateName = "";
			String type = "";
			cateDispId = tdCateDisp.getCate_disp_id();
			catePubId = tdCateDisp.getCate_pub_id();
			cateName = tdCateDisp.getDisp_name_cn();
			type = tdCateDisp.getType();
			if(catePubId == null){
				catePubId = "";
			}
			if(cateName == null){
				cateName = "";
			}

			SearchCateDispTree soCateDispTree = new SearchCateDispTree();
			soCateDispTree.setCateDispId(cateDispId);
			soCateDispTree.setCatePubId(catePubId);
			soCateDispTree.setCateName(cateName);
			soCateDispTree.setCatePids(catePids);
			soCateDispTree.setPhy(""); //旧业务是从产品线中取出（目前没发现用到）
			soCateDispTree.setRootCid(rootCid);
			soCateDispTree.setCateType(type);
			
			
			//类目放入缓存中
			CacheFactory.getCacheOper("cate_").put(cateDispId, soCateDispTree, Constant.JEDIS_CATELOG_LOES_TIME); 
		}
		LOGGER.info("类目:"+param+" 加入缓存结束...");
	}
	
	/**
	 * 搜索类目
	 * @param param
	 */
	public String getCategoryByKeyword(String param, String b2cOb2b) {
		param = param.toUpperCase();
		LOGGER.info("类目:"+param+" 开始按关键词名称搜索类目...");
		String sql = "";
		String cateDispId = "";
		
		sql = "select cate_disp_id,cate_pub_id, disp_name, disp_name_cn, par_cate_disp_id, sortval, leaf,type from td_cate_disp where valid = '1' and display = '1' and upper(disp_name_cn) = '"+param +"' and type='"+b2cOb2b+"' order by leaf,cate_disp_id, sortval desc";
		
		List<TdCateDisp> tdCateDispList = jdbcTemplate.query(sql.toString(), new RowMapper<TdCateDisp>(){
			
			public TdCateDisp mapRow(ResultSet rs, int rowNum) throws SQLException {
				TdCateDisp disp = new TdCateDisp();
				disp.setCate_disp_id(rs.getString("cate_disp_id"));//显示类目ID
				disp.setDisp_name(rs.getString("disp_name"));//类目英文名
				disp.setDisp_name_cn(rs.getString("disp_name_cn"));//类目中文名
				disp.setPar_cate_disp_id(rs.getString("par_cate_disp_id"));//父类目ID
				disp.setSortval(rs.getInt("sortval"));//类目显示优先 值越大，越优先
				disp.setLeaf(rs.getString("leaf"));//是否叶子节点 0目录节点 1叶节点
				disp.setType(rs.getString("type"));//type b2b b2c
				return disp;
			}
		});
		 
		if(tdCateDispList != null && tdCateDispList.size() >0 ){
			cateDispId = tdCateDispList.get(0).getCate_disp_id();
		}
			
		LOGGER.info("类目:根据关键词查询"+param+" 结束...");
		
		return cateDispId;
	}
	
	/**
	 * 搜索品牌
	 * @param param
	 */
	public String getBrandByKeyword(String param) {
		param = param.toUpperCase();
		LOGGER.info("品牌:"+param+" 开始按关键词名称搜索品牌...");
		String sql = "";
		String dispNameCn = "";
		
		sql = "select name_cn disp_name_cn from td_brand where status=1  and upper(name_cn)= '"+ param +"'";
		
		List<TdCateDisp> tdCateDispList = jdbcTemplate.query(sql.toString(), new RowMapper<TdCateDisp>(){
			
			public TdCateDisp mapRow(ResultSet rs, int rowNum) throws SQLException {
				TdCateDisp disp = new TdCateDisp();
				
				disp.setDisp_name_cn(rs.getString("disp_name_cn"));//类目中文名
				
				return disp;
			}
		});
		 
		if(tdCateDispList != null && tdCateDispList.size() >0 ){
			dispNameCn = tdCateDispList.get(0).getDisp_name_cn();
		}
			
		LOGGER.info("品牌:根据关键词查询"+param+" 结束...");
		
		return dispNameCn;
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
