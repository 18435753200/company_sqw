package com.mall.search.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.MatchQueryBuilder.Operator;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mall.dsearch.vo.SearchSupplierDetailRequest;
import com.mall.dsearch.vo.SearchSupplierDetailResponse;
import com.mall.dsearch.vo.SearchSupplierRequest;
import com.mall.dsearch.vo.SearchSupplierResponse;
import com.mall.dsearch.vo.Supplier;
import com.mall.search.constant.Constant;
import com.mall.search.elasticsearch.AssemblingSupplierItem;
import com.mall.search.elasticsearch.EasticSearchSupplierQuery;
import com.mall.search.service.SearchSupplierService;
import com.mall.search.utils.ElasticSearchClientUtil;
import com.mall.search.vo.ResponseSupplierItem;

@Service("searchSupplierServiceImpl")
public class SearchSupplierServiceImpl implements SearchSupplierService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchSupplierServiceImpl.class);

	public static void main(String[] args) {
		SearchSupplierService s = new SearchSupplierServiceImpl();
		SearchSupplierRequest searchSupplierRequest = new SearchSupplierRequest();
		SearchSupplierDetailRequest searchSupplierDetailRequest=new SearchSupplierDetailRequest();
		searchSupplierDetailRequest.setSupplierId(3);
//		searchSupplierRequest.setOrganizationType(1);
//		searchSupplierRequest.setProvinceId(19);
//		searchSupplierRequest.setPageNo(1);
//		searchSupplierRequest.setSectorCode(113);
//		searchSupplierRequest.setSectorCodeName("商场");
//		searchSupplierRequest.setCityName("北京市");
//		searchSupplierRequest.setCityId(424);
//		s.searchSupplier(searchSupplierRequest);
		s.searchSupplierDetail(searchSupplierDetailRequest);
	}

	@Override
	public SearchSupplierResponse searchSupplier(SearchSupplierRequest searchSupplierRequest) {
		// 获取es的客户端
		TransportClient transportClient = ElasticSearchClientUtil.getTransportClient();
		// 根据索引的主片名和索引类型去获取查询执行类
		SearchRequestBuilder requestBuilder = transportClient.prepareSearch(ElasticSearchClientUtil.INDEX_NOW_ONE)
				.setTypes(ElasticSearchClientUtil.INDEX_TYPE_SUPPLIER);

		
		//构建搜索条件
		List<BoolQueryBuilder> build = EasticSearchSupplierQuery.build(searchSupplierRequest);
		
		//排序条件,按时间降序
		requestBuilder.addSort("createtimeDate", SortOrder.DESC);
		//分页(每页大小)
		requestBuilder.setSize(searchSupplierRequest.getPageSize());
		LOGGER.info("分页尺寸size:" + searchSupplierRequest.getPageSize());
		//分页(相当于第几页)
		requestBuilder.setFrom(searchSupplierRequest.getPageSize() * (searchSupplierRequest.getPageNo() - 1));
		LOGGER.info("分页,从第几条开始:" + (searchSupplierRequest.getPageSize() * (searchSupplierRequest.getPageNo() - 1)));
		//分组(企业所属类目)
		SearchRequestBuilder group = EasticSearchSupplierQuery.group(requestBuilder, searchSupplierRequest);

		
		LOGGER.info("查询条件:"+requestBuilder.toString());
		org.elasticsearch.action.search.SearchResponse searchResponse = group.setQuery(build.get(0)).get();
		
		// 能翻页的总数
		Integer hits = Integer.valueOf(String.valueOf(searchResponse.getHits().getTotalHits()));
		LOGGER.info("获取命中数量：" + hits);
		
		Integer pageSum = hits / searchSupplierRequest.getPageSize() == 0 ? hits / searchSupplierRequest.getPageSize()
				: hits / searchSupplierRequest.getPageSize() + 1;
		LOGGER.info("分页,总页数:" + pageSum);
		
		//把返回的数据进行拼装
		ResponseSupplierItem responseSupplierItem = null;
		if (searchResponse.getHits().getHits().length == 0) {
			responseSupplierItem = new ResponseSupplierItem();
			responseSupplierItem.setCompanyBizCategorys(new ArrayList<String>());
			responseSupplierItem.setSupplierItemList(new ArrayList<Supplier>());
		} else {
			responseSupplierItem = AssemblingSupplierItem.responseBuilder(searchResponse, searchSupplierRequest);
		}
		
		// 类目分组 
		LOGGER.info("类目分组");
		category(searchSupplierRequest,searchResponse,responseSupplierItem);
		
		//封装数据
		SearchSupplierResponse searchSupplierResponse=new SearchSupplierResponse();
		searchSupplierResponse.setHits(hits);
		searchSupplierResponse.setNumFetch(pageSum);
		searchSupplierResponse.setItems(responseSupplierItem.getSupplierItemList());
		searchSupplierResponse.setCompanyBizCategorys(responseSupplierItem.getCompanyBizCategorys());
		
		System.out.println(searchSupplierResponse.getItems());
		
		//搜索数据展示
		SearchHits hits1 = searchResponse.getHits();
		if (hits1.totalHits() > 0) {
			for (SearchHit searchHit : hits1) {
				System.out.println("total>>>>" + hits1.totalHits() + "|source>>>" + searchHit.getSourceAsString());
			}
		}
		return searchSupplierResponse;
	}
	
	
	@Override
	public SearchSupplierDetailResponse searchSupplierDetail(SearchSupplierDetailRequest searchSupplierDetailRequest) {
		// 获取es的客户端
		TransportClient transportClient = ElasticSearchClientUtil.getTransportClient();
		// 根据索引的主片名和索引类型去获取查询执行类
		SearchRequestBuilder requestBuilder = transportClient.prepareSearch(ElasticSearchClientUtil.INDEX_NOW_ONE)
				.setTypes(ElasticSearchClientUtil.INDEX_TYPE_SUPPLIER);
		//构建搜索条件
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		boolQuery.must(QueryBuilders.multiMatchQuery(searchSupplierDetailRequest.getSupplierId(),"supplierId").operator(Operator.AND));
		SearchResponse searchResponse = requestBuilder.setQuery(boolQuery).get();
		
		//把返回的数据进行拼装
		SearchSupplierDetailResponse SearchSupplierDetailResponse = null;
		if (searchResponse.getHits().getHits().length == 0) {
			SearchSupplierDetailResponse=new SearchSupplierDetailResponse();
		} else {
			SearchSupplierDetailResponse = AssemblingSupplierItem.responseDetailBuilder(searchResponse);
		}
		System.out.println(SearchSupplierDetailResponse);
		return SearchSupplierDetailResponse;
	}

	
	
	//对类目进行分组
	private void category(SearchSupplierRequest searchSupplierRequest, SearchResponse searchResponse,
			ResponseSupplierItem responseSupplierItem) {
		/**
		 * 原来通过聚合查询目录结构.
		 */
//		Set<String> set = new HashSet<>();
//		Terms agg = searchResponse.getAggregations().get("by_categoryName");
//		for (Terms.Bucket entry : agg.getBuckets()) {
//			String key = entry.getKey().toString();
//			set.add(key);
//		}
//		List<String> list=new ArrayList<>();
	
//		for(String str:AssemblingSupplierItem.LIST){
//			for (String string : set) {
//				if(str.equals(string)){
//					list.add(string);
//				}
//			}
//		}
//		System.out.println("类目排序结果:======"+list);
		//原来是通过集合类进行类目的排序,现在通过自己规定的顺序进行  ↑↑
//		System.out.println("排序前:======"+list);
//		Collections.sort(list);
//		System.out.println("排序后:======"+list);
//		Collections.reverse(list);
//		System.out.println("倒序后:======"+list);
		
		
		/**
		 * 写死的目录结构
		 */
		List<String> list=AssemblingSupplierItem.LIST_FIXED;
		//把传递过来的类目条件放在类目列表的首位
		List<String> newList=null;
		if(searchSupplierRequest.getSectorCodeName()!=null && !searchSupplierRequest.getSectorCodeName().equals("")){
			newList=new ArrayList<String>();
			for(int i=0;i<list.size();i++){
				if(searchSupplierRequest.getSectorCodeName().equals(list.get(i))){
					continue;
				}
				newList.add(list.get(i));
			}
			newList.add(0, searchSupplierRequest.getSectorCodeName());
		}else{
			newList=list;
		}
		responseSupplierItem.setCompanyBizCategorys(newList);
	}

	

}
