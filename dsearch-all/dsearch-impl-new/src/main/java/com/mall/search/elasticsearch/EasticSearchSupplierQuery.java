package com.mall.search.elasticsearch;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mall.dsearch.vo.SearchSupplierRequest;
import com.mall.search.constant.Constant;
import com.mall.search.utils.StringUtils;


public class EasticSearchSupplierQuery {

	private static final Logger LOGGER = LoggerFactory.getLogger(EasticSearchSupplierQuery.class);

	/**
	 * 构建搜索条件
	 * 
	 * @param searchRequest
	 * @param keyWordSearchFlag
	 *            ture 启用关键词搜索 false 不启用关键词搜索
	 * @return
	 */
	public static List<BoolQueryBuilder> build(SearchSupplierRequest searchSupplierRequest) {
		// 关键词搜索1
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

		
		//商家类型
		if(searchSupplierRequest.getOrganizationType()!=null){
			boolQueryBuilder.must(QueryBuilders.multiMatchQuery(searchSupplierRequest.getOrganizationType(),"organizationType").operator(Operator.AND));
		}
		
		//省id
		if(searchSupplierRequest.getProvinceId()!=null){
			boolQueryBuilder.must(QueryBuilders.multiMatchQuery(searchSupplierRequest.getProvinceId(),"provinceId").operator(Operator.AND));
		}
		
		//省Name
		if(searchSupplierRequest.getProvinceName()!=null && !searchSupplierRequest.getProvinceName().equals("")){
			boolQueryBuilder.must(QueryBuilders.multiMatchQuery(searchSupplierRequest.getProvinceName(),"provinceName").operator(Operator.AND));
		}
		//市id
		if(searchSupplierRequest.getCityId()!=null){
			boolQueryBuilder.must(QueryBuilders.multiMatchQuery(searchSupplierRequest.getCityId(),"cityId").operator(Operator.AND));
		}
		
		//市Name
		if(searchSupplierRequest.getCityName()!=null && !searchSupplierRequest.getCityName().equals("")){
			boolQueryBuilder.must(QueryBuilders.multiMatchQuery(searchSupplierRequest.getCityName(),"cityName").operator(Operator.AND));
		}
		
		//区id
		if(searchSupplierRequest.getCountryId()!=null){
			boolQueryBuilder.must(QueryBuilders.multiMatchQuery(searchSupplierRequest.getCountryId(),"areaId").operator(Operator.AND));
		}
		
		//市Name
		if(searchSupplierRequest.getCountryName()!=null && !searchSupplierRequest.getCountryName().equals("")){
			boolQueryBuilder.must(QueryBuilders.multiMatchQuery(searchSupplierRequest.getCountryName(),"areaName").operator(Operator.AND));
		}
		
		//类目id
		if(searchSupplierRequest.getSectorCode()!=null){
			boolQueryBuilder.must(QueryBuilders.multiMatchQuery(searchSupplierRequest.getSectorCode(),"companyBizCategory").operator(Operator.AND));
		}
		
		/**
		 * 根据类目Name查询,根据类目类目转义成多个词,这时查询的就是包含这多个词的查询数据
		 */
		//一个filed字段对应一个值的查询
//		if(searchSupplierRequest.getSectorCodeName()!=null){
//			boolQueryBuilder.must(QueryBuilders.multiMatchQuery(searchSupplierRequest.getSectorCodeName(),"companyBizCategoryName").operator(Operator.AND));
//		}
		//一个filed字符对应多个值的查询
		if (searchSupplierRequest.getSectorCodeName()!=null && !searchSupplierRequest.getSectorCodeName().equals("")) {
			//进行查询词转换
			String[] strs = WorkTransform.strChangeToArray(searchSupplierRequest.getSectorCodeName());
			BoolQueryBuilder shouldQueryBuilder = new BoolQueryBuilder();
			for (int i = 0; i < strs.length; i++) {
				shouldQueryBuilder.should(QueryBuilders.matchQuery("companyBizCategoryMore", strs[i]));
			}
				boolQueryBuilder.must(shouldQueryBuilder);
		}
		


		LOGGER.info("搜索最后执行脚本boolQueryBuilder：" + boolQueryBuilder.toString());
		
		List<BoolQueryBuilder> listBoolQueryBuilder = new ArrayList<BoolQueryBuilder>();
		
		listBoolQueryBuilder.add(boolQueryBuilder);
		
		return listBoolQueryBuilder;
	}
	
	
	/**
	 * 搜索分组
	 * 
	 * @param requestBuilder
	 * @return
	 */
	public static SearchRequestBuilder group(SearchRequestBuilder requestBuilder,SearchSupplierRequest searchSupplierRequest) {
		// 类目id
		requestBuilder.addAggregation(AggregationBuilders.terms("by_category").field("companyBizCategory").size(1000));

		// 类目name
		requestBuilder.addAggregation(AggregationBuilders.terms("by_categoryName").field("companyBizCategoryName").size(1000));

		return requestBuilder;
	}

}
