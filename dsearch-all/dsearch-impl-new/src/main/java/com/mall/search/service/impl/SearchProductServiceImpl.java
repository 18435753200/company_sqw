package com.mall.search.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.sort.SortOrder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.json.ParseException;
import com.mall.dsearch.po.SearchCateDispTree;
import com.mall.dsearch.vo.AttrTerm;
import com.mall.dsearch.vo.CatalogAttr;
import com.mall.dsearch.vo.CatalogAttrVal;
import com.mall.dsearch.vo.CatalogNode;
import com.mall.dsearch.vo.PlainProduct;
import com.mall.dsearch.vo.SearchRequest;
import com.mall.dsearch.vo.SearchResponse;
import com.mall.search.cache.CacheFactory;
import com.mall.search.cache.JedisCateAndAttrByOne;
import com.mall.search.constant.Constant;
import com.mall.search.elasticsearch.AssemblingItem;
import com.mall.search.elasticsearch.EasticSearchQuery;
import com.mall.search.service.SearchProductService;
import com.mall.search.utils.ElasticSearchClientUtil;
import com.mall.search.vo.ResponseItem;

@Service("searchProductServiceImpl")
public class SearchProductServiceImpl implements SearchProductService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchProductServiceImpl.class);

	public static void main(String[] args) {

//		System.out.println(System.currentTimeMillis());

//		SearchRequest sr = new SearchRequest();
		// sr.setDiscount("0-1");
		// sr.setCdid("001001,001002");
//		sr.setFcode("cmd:xfg");
		// sr.setFcode("cmdxfg.star五星.discount1-2");
//		sr.setPriceRange("100-200");
		// sr.setKeyword("9086283411030281");
		/* sr.setFamily("ftag"); */
		/*
		 * sr.setCyid("zlkcs,广东平易环保科技有限公司"); sr.setCash("xjzfbl");
		 */
		// sr.setPid("2916896502667926");
//		sr.setB2C(true);
		// sr.setEntryType("1");
		// sr.setCyid("意大利");
		// sr.setBrandName("爱迪生");
		// sr.setCdid("054");
		// sr.setPriceRange("100-200");
		// sr.setSortType(4);
//		SearchProductServiceImpl searchProductService = new SearchProductServiceImpl();
//		SearchResponse searchResponse = searchProductService.searchProduct(sr);
//		searchResponse.getTerms();
//		System.out.println("aabbb");
	}

	public SearchResponse searchProduct(SearchRequest searchRequest) {

		// 分2部分判断（B2B、B2C）
		boolean isB2C = searchRequest.isB2C();
		LOGGER.debug("接收外部参数是哪个站(true-B2C||false-B2B)：" + searchRequest.isB2C());
		TransportClient transportClient = ElasticSearchClientUtil.getTransportClient();
		String index = ElasticSearchClientUtil.INDEX_NOW;
		SearchRequestBuilder requestBuilder = transportClient.prepareSearch(index)
				.setTypes(ElasticSearchClientUtil.INDEX_TYPE);
		LOGGER.info("渠道标示：" + searchRequest.getEntryType());
		// 构建搜索条件
		List<BoolQueryBuilder> queryBuilder = EasticSearchQuery.build(searchRequest);
		// 排序字段（价格、库存...）
		Integer sortType = searchRequest.getSortType();
		// 通过类目查询 是没有相关性排序的 使用默认的销量、库存和创建时间排序（库存作为第一排序，如有相同库存，销量作为第二排序）
		// if((sortType == null||sortType
		// ==0)&&(StringUtils.isNotEmpty(searchRequest.getCdid()))){
		LOGGER.info("当前排序sortType：" + sortType);
		if (sortType == null || sortType == 0) {
			LOGGER.info("默认排序");
			// .addSort("isStock", SortOrder.DESC)
			requestBuilder.addSort("salesVolume", SortOrder.DESC).addSort(Constant.FIELD_CT, SortOrder.DESC);
		}
		if (sortType != null && sortType != 0) {
			LOGGER.info("系统排序");
			requestBuilder
					.addSort(getSortType(sortType, searchRequest), sortType % 2 == 0 ? SortOrder.ASC : SortOrder.DESC)
					.addSort(Constant.FIELD_CT, SortOrder.DESC);
		}
		// 分页
		requestBuilder.setSize(searchRequest.getPageSize());
		LOGGER.info("分页尺寸size:" + searchRequest.getPageSize());
		requestBuilder.setFrom(searchRequest.getPageSize() * (searchRequest.getPageno() - 1));
		LOGGER.info("分页,从第几条:" + (searchRequest.getPageSize() * (searchRequest.getPageno() - 1)));
		// 高亮
		String prefix = isB2C == true ? "b2c" : "b2b";
		requestBuilder.addHighlightedField(prefix + "_pname").setHighlighterPreTags("<font color='red'>")
				.setHighlighterPostTags("</font>");

		// 分组（品牌、价格、企业、类目、货源类型）
		SearchRequestBuilder requestBuilders = EasticSearchQuery.group(requestBuilder, isB2C, searchRequest);
		// requestBuilder.setPreference("_primary"); //防止结果震荡问题，性能会打折扣

		// 先按品类、品牌查询
		org.elasticsearch.action.search.SearchResponse searchResponse = requestBuilders.setQuery(queryBuilder.get(0))
				.get();
		LOGGER.info("按品类、品牌查询");
		LOGGER.debug(requestBuilder.toString());
		/*
		 * if(searchResponse.getHits().getTotalHits() == 0l){
		 * System.out.println("aaaaaaaaaaaaaaaaaaaaa"); SearchRequestBuilder
		 * requestBuilder1 =
		 * transportClient.prepareSearch(index).setTypes(ElasticSearchClientUtil
		 * .INDEX_TYPE); //通过类目查询 是没有相关性排序的
		 * 使用默认的销量、库存和创建时间排序（库存作为第一排序，如有相同库存，销量作为第二排序） //if((sortType ==
		 * null||sortType
		 * ==0)&&(StringUtils.isNotEmpty(searchRequest.getCdid()))){ if(sortType
		 * == null||sortType ==0){ //.addSort("stock",SortOrder.DESC)
		 * requestBuilder1.addSort("salesVolume",
		 * SortOrder.DESC).addSort(Constant.FIELD_CT, SortOrder.DESC); } if
		 * (sortType != null&&sortType!=0) {
		 * requestBuilder1.addSort(getSortType(sortType,searchRequest), sortType
		 * % 2 == 0 ? SortOrder.ASC : SortOrder.DESC).addSort(Constant.FIELD_CT,
		 * SortOrder.DESC); } //分页
		 * requestBuilder1.setSize(searchRequest.getPageSize());
		 * LOGGER.info("分页尺寸pagesize:" + searchRequest.getPageSize());
		 * requestBuilder1.setFrom(searchRequest.getPageSize() *
		 * (searchRequest.getPageno() - 1)); LOGGER.info("分页,从第几条:" +
		 * (searchRequest.getPageSize() * (searchRequest.getPageno() - 1)));
		 * //高亮
		 * 
		 * requestBuilder1.addHighlightedField(prefix+"_pname").
		 * setHighlighterPreTags("<font color='red'>"
		 * ).setHighlighterPostTags("</font>");
		 * 
		 * //分组（品牌、价格、企业、类目、货源类型） SearchRequestBuilder requestBuilders1 =
		 * EasticSearchQuery.group(requestBuilder1, isB2C, searchRequest);
		 * //再按商品名称、属性、商品详描查询 searchResponse =
		 * requestBuilders1.setQuery(queryBuilder.get(1)).get();
		 * LOGGER.debug(requestBuilder1.toString());
		 * LOGGER.info("关键词按品类、品牌搜索无数据，修改为按商品名称、属性、商品详描查询");
		 * 
		 * }
		 */

		// LOGGER.debug(searchResponse.toString());
		LOGGER.debug("获取命中数量：" + searchResponse.getHits().getTotalHits());

		// 能翻页的总数
		int hits = Integer.valueOf(String.valueOf(searchResponse.getHits().getTotalHits()));
		int pageSum = hits % searchRequest.getPageSize() == 0 ? hits % searchRequest.getPageSize()
				: hits % searchRequest.getPageSize() + 1;
		LOGGER.info("分页,总页数:" + pageSum);
		// 返回SearchResponse类（拼装）
		ResponseItem responseItem = null;
		if (searchResponse.getHits().getHits().length == 0) {
			responseItem = new ResponseItem();
			responseItem.setProductCountrys(new HashMap<String, String>());
			responseItem.setProductItemList(new LinkedList<PlainProduct>());
		} else {
			// responseItem = responseBuilder(searchResponse,
			// searchRequest);设置高亮
			responseItem = AssemblingItem.responseBuilder(searchResponse, searchRequest);
		}

		SearchResponse searchResponse_ = new SearchResponse();

		// 属性分组
		LinkedHashMap<CatalogAttr, LinkedHashSet<CatalogAttrVal>> termMap = AssemblingItem.responseterm(searchResponse,
				responseItem, searchRequest);

		// 类目分组
		List<CatalogNode> catalogs = null;
		try {
			catalogs = catalogs(searchResponse, responseItem, isB2C);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// 选中属性
		LinkedList<AttrTerm> terms = AssemblingItem.attrTerm(searchRequest);

		// 返回web结果拼装
		searchResponse_.setHits(hits);
		searchResponse_.setNumFetch(pageSum);
		searchResponse_.setItems(responseItem.getProductItemList());
		searchResponse_.setMatrix(termMap);
		searchResponse_.setTerms(terms);
		searchResponse_.setCatalogs(catalogs);

		return searchResponse_;
	}

	/**
	 * 类目获取
	 *
	 * @param searchResponse_
	 * @param responseItem
	 * @param isB2C
	 * @return
	 * @throws ParseException
	 */
	private List<CatalogNode> catalogs(org.elasticsearch.action.search.SearchResponse searchResponse_,
			ResponseItem responseItem, boolean isB2C) throws ParseException {
		List<CatalogNode> catelogs = new ArrayList<CatalogNode>();
		List<String> cateLists = new ArrayList<String>();

		// 类目
		Terms agg = searchResponse_.getAggregations().get("by_cdid");
		for (Terms.Bucket entry : agg.getBuckets()) {
			cateLists.add((String) entry.getKey());
		}

		// 排序
		Collections.sort(cateLists);

		// 放入类目树
		for (String cateid : cateLists) {
			// 类目从缓存中取
			SearchCateDispTree sdt = (SearchCateDispTree) CacheFactory.getCacheOper("cate_").get(cateid);
			if (null == sdt) {
				// 查库操作
				JedisCateAndAttrByOne.startCaches("cate_" + cateid);
				sdt = (SearchCateDispTree) CacheFactory.getCacheOper("cate_").get(cateid);
			}
			if (sdt == null) {
				continue;
			}
			CatalogNode catalogNodes = new CatalogNode();
			catalogNodes.setId(cateid);
			catalogNodes.setName(sdt.getCateName());
			catelogs.add(catalogNodes);
		}
		List<CatalogNode> treeNode = new ArrayList<CatalogNode>();
		for (CatalogNode node : catelogs) {
			if (node.getId().length() == 3) {
				getChilds(node, catelogs);
				treeNode.add(node);
			}
		}
		return treeNode;
	}

	private void getChilds(CatalogNode pNode, List<CatalogNode> list) {
		for (CatalogNode current : list) {
			if (current.getId().length() > 3
					&& current.getId().substring(0, current.getId().length() - 3).equals(pNode.getId())) {
				current.setParent(pNode);
				if (current.getId().length() < 12) {
					getChilds(current, list);
				}
				pNode.getChildren().add(current);
			}
		}
	}

	/**
	 * 搜索排序值
	 * 
	 * @param sortType
	 * @param searchRequest
	 * @return
	 */
	private String getSortType(int sortType, SearchRequest searchRequest) {
		String sortTypeStr = "";
		switch (sortType) {
		case 1:
		case 2:
			// sortTypeStr = "b2c_opt";
			sortTypeStr = "ct";
			break;
		case 3:
		case 4:
			sortTypeStr = "unit_price";
			sortTypeStr = AssemblingItem.getWayPrice(searchRequest); // 根据渠道获取对应促销的价格
			break;
		case 5:
		case 6:
			sortTypeStr = "stock";
			break;
		case 7:
		case 8:
			sortTypeStr = "salesVolume";
			break;
		}

		return sortTypeStr;
	}

}
