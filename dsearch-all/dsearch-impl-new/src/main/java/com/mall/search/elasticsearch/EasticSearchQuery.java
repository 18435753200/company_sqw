package com.mall.search.elasticsearch;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.script.ScriptScoreFunctionBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mall.dsearch.vo.SearchRequest;
import com.mall.search.constant.Constant;
import com.mall.search.utils.StringUtils;

/**
 * 搜索类
 * 
 * @author doublell
 *
 */
public class EasticSearchQuery {

	private static final Logger LOGGER = LoggerFactory.getLogger(EasticSearchQuery.class);

	/**
	 * 构建搜索条件
	 * 
	 * @param searchRequest
	 * @param keyWordSearchFlag
	 *            ture 启用关键词搜索 false 不启用关键词搜索
	 * @return
	 */
	public static List<BoolQueryBuilder> build(SearchRequest searchRequest) {
		// 关键词搜索1
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		// 关键词搜索2
		BoolQueryBuilder boolQueryBuilder1 = QueryBuilders.boolQuery();
		// 默认根据库存给商品加评分
		boolQueryBuilder.must(QueryBuilders.functionScoreQuery()
				.add(new ScriptScoreFunctionBuilder(new Script("if(doc['stock'].value>0){10} else{0}")))
				.boostMode("sum"));
		boolQueryBuilder1.must(QueryBuilders.functionScoreQuery()
				.add(new ScriptScoreFunctionBuilder(new Script("if(doc['stock'].value>0){10} else{0}")))
				.boostMode("sum"));

		String prefix = searchRequest.isB2C() ? "b2c" : "b2b";
		boolean isQuery = false;

		// 关键词不为空 先按照 类目 品牌配匹
		if (StringUtils.isNotEmpty(searchRequest.getKeyword())) {

			isQuery = true;
			if ("易物".equals(searchRequest.getKeyword())) {
				searchRequest.setKeyword("制造商");
				boolQueryBuilder.must(QueryBuilders.multiMatchQuery(searchRequest.getKeyword(), prefix + "_cdidNames",
						"brandNameFc", "supplier_name", prefix + "_searchable", prefix + "_pname", "pid", "attrNameVal",
						"b2cProdDesc", "tagName", "family").operator(Operator.AND));
			}
			if ("幸福购".equals(searchRequest.getKeyword())) {
				searchRequest.setKeyword("制造商");
				boolQueryBuilder.must(QueryBuilders.multiMatchQuery(searchRequest.getKeyword(), prefix + "_cdidNames",
						"brandNameFc", "supplier_name", prefix + "_searchable", prefix + "_pname", "pid", "attrNameVal",
						"b2cProdDesc", "tagName", "cash").operator(Operator.AND));
			}
			// 同时组装按照 商品关键字、属性、商品详描 类目、品牌搜索
			// 按照类目、品牌
			/*
			 * boolQueryBuilder.must(QueryBuilders.multiMatchQuery(searchRequest
			 * .getKeyword(),prefix+"_cdidNames",
			 * "brandNameFc","supplier_name").operator(Operator.AND));
			 * //商品关键字、属性、商品详描
			 * boolQueryBuilder1.must(QueryBuilders.multiMatchQuery(
			 * searchRequest.getKeyword(),prefix + "_searchable",
			 * prefix+"_pname","pid","attrNameVal","b2cProdDesc").operator(
			 * Operator.AND));
			 */

			if (!searchRequest.getKeyword().equals("制造商")) {
				boolQueryBuilder.must(QueryBuilders.multiMatchQuery(searchRequest.getKeyword(), prefix + "_cdidNames",
						"brandNameFc", "supplier_name", prefix + "_searchable", prefix + "_pname", "pid", "attrNameVal",
						"b2cProdDesc", "tagName").operator(Operator.AND));
			}
		}

		// 类目搜索
		/*
		 * if (StringUtils.isNotEmpty(searchRequest.getCdid())) { if (isQuery ==
		 * false) { isQuery = true;
		 * boolQueryBuilder.must(QueryBuilders.matchQuery(prefix + "_cdids",
		 * searchRequest.getCdid()));
		 * boolQueryBuilder1.must(QueryBuilders.matchQuery(prefix + "_cdids",
		 * searchRequest.getCdid())); } else {
		 * boolQueryBuilder.filter(QueryBuilders.matchQuery(prefix + "_cdids",
		 * searchRequest.getCdid()));
		 * boolQueryBuilder1.filter(QueryBuilders.matchQuery(prefix + "_cdids",
		 * searchRequest.getCdid())); } }
		 */

		if (StringUtils.isNotEmpty(searchRequest.getCdid())) {
			String[] cdids = searchRequest.getCdid().split(",");
			BoolQueryBuilder shouldQueryBuilder = new BoolQueryBuilder();
			for (int i = 0; i < cdids.length; i++) {
				shouldQueryBuilder.should(QueryBuilders.matchQuery(prefix + "_cdids", cdids[i]));
			}
			if (isQuery == false) {
				isQuery = true;
				boolQueryBuilder.must(shouldQueryBuilder);
				boolQueryBuilder1.must(shouldQueryBuilder);
			} else {
				boolQueryBuilder.filter(shouldQueryBuilder);
				boolQueryBuilder1.filter(shouldQueryBuilder);
			}
		}

		// 品牌搜索
		if (StringUtils.isNotEmpty(searchRequest.getBrandName())) {

			String[] brandNames = searchRequest.getBrandName().split(",");
			BoolQueryBuilder shouldQueryBuilder = new BoolQueryBuilder();
			for (int i = 0; i < brandNames.length; i++) {
				shouldQueryBuilder.should(QueryBuilders.matchQuery(Constant.FIELD_BRAND_NAME, brandNames[i]));
			}
			// 区分是否为必要条件
			if (isQuery == false) {
				isQuery = true;
				boolQueryBuilder.must(shouldQueryBuilder);
				boolQueryBuilder1.must(shouldQueryBuilder);
			} else {
				boolQueryBuilder.filter(shouldQueryBuilder);
				boolQueryBuilder1.filter(shouldQueryBuilder);
			}
		}

		// 标签搜索
		if (StringUtils.isNotEmpty(searchRequest.getTagName())) {

			String[] tagNames = searchRequest.getTagName().split(",");
			BoolQueryBuilder shouldQueryBuilder = new BoolQueryBuilder();
			for (int i = 0; i < tagNames.length; i++) {
				shouldQueryBuilder.should(QueryBuilders.matchQuery(Constant.FIELD_TAG_NAME, tagNames[i]));
			}
			// 区分是否为必要条件
			if (isQuery == false) {
				isQuery = true;
				boolQueryBuilder.must(shouldQueryBuilder);
				boolQueryBuilder1.must(shouldQueryBuilder);
			} else {
				boolQueryBuilder.filter(shouldQueryBuilder);
				boolQueryBuilder1.filter(shouldQueryBuilder);
			}
		}
		// 现金标签搜索
		/*
		 * if( StringUtils.isNotEmpty(searchRequest.getFcode()) &&
		 * "cmdxfg".equals(searchRequest.getFcode())){
		 * 
		 * 
		 * 
		 * 
		 * BoolQueryBuilder shouldQueryBuilder = new BoolQueryBuilder();
		 * shouldQueryBuilder.should(QueryBuilders.matchQuery("cash", "制造商"));
		 * //区分是否为必要条件 if (isQuery == false) { isQuery = true;
		 * boolQueryBuilder.must(shouldQueryBuilder);
		 * boolQueryBuilder1.must(shouldQueryBuilder); } else {
		 * boolQueryBuilder.filter(shouldQueryBuilder);
		 * boolQueryBuilder1.filter(shouldQueryBuilder); }
		 * 
		 * }
		 */

		// 家庭号商品搜索
		/*
		 * if( StringUtils.isNotEmpty(searchRequest.getFcode()) &&
		 * "cmdyw".equals(searchRequest.getFcode())){
		 * 
		 * 
		 * 
		 * 
		 * BoolQueryBuilder shouldQueryBuilder = new BoolQueryBuilder();
		 * shouldQueryBuilder.should(QueryBuilders.matchQuery("family", "制造商"));
		 * //区分是否为必要条件 if (isQuery == false) { isQuery = true;
		 * boolQueryBuilder.must(shouldQueryBuilder);
		 * boolQueryBuilder1.must(shouldQueryBuilder); } else {
		 * boolQueryBuilder.filter(shouldQueryBuilder);
		 * boolQueryBuilder1.filter(shouldQueryBuilder); }
		 * 
		 * }
		 */

		/**
		 * 企業類型標籤
		 */
		/*
		 * if(StringUtils.isNotEmpty(searchRequest.getFcode()) &&
		 * "tag".equals(searchRequest.getFcode().substring(0, 3))){
		 * BoolQueryBuilder shouldQueryBuilder = new BoolQueryBuilder(); String
		 * pcode=searchRequest.getFcode().substring(3,
		 * searchRequest.getFcode().length());
		 * shouldQueryBuilder.should(QueryBuilders.matchQuery("productType_code"
		 * ,pcode)); if (isQuery == false) { isQuery = true;
		 * boolQueryBuilder.must(shouldQueryBuilder);
		 * boolQueryBuilder1.must(shouldQueryBuilder); } else {
		 * boolQueryBuilder.filter(shouldQueryBuilder);
		 * boolQueryBuilder1.filter(shouldQueryBuilder); } }
		 */

		// 家庭号服务
		/*
		 * if(StringUtils.isNotEmpty(searchRequest.getFcode()) &&
		 * "tagjtb".equals(searchRequest.getFcode())){ BoolQueryBuilder
		 * shouldQueryBuilder = new BoolQueryBuilder();
		 * shouldQueryBuilder.should(QueryBuilders.matchQuery("productType_code"
		 * ,"jtb" )); if (isQuery == false) { isQuery = true;
		 * boolQueryBuilder.must(shouldQueryBuilder);
		 * boolQueryBuilder1.must(shouldQueryBuilder); } else {
		 * boolQueryBuilder.filter(shouldQueryBuilder);
		 * boolQueryBuilder1.filter(shouldQueryBuilder); } }
		 * 
		 * 
		 * if(StringUtils.isNotEmpty(searchRequest.getFcode()) &&
		 * "tagqyb".equals(searchRequest.getFcode())){ BoolQueryBuilder
		 * shouldQueryBuilder = new BoolQueryBuilder();
		 * shouldQueryBuilder.should(QueryBuilders.matchQuery("productType_code"
		 * ,"qyb" )); if (isQuery == false) { isQuery = true;
		 * boolQueryBuilder.must(shouldQueryBuilder);
		 * boolQueryBuilder1.must(shouldQueryBuilder); } else {
		 * boolQueryBuilder.filter(shouldQueryBuilder);
		 * boolQueryBuilder1.filter(shouldQueryBuilder); } }
		 * 
		 * 
		 * if(StringUtils.isNotEmpty(searchRequest.getFcode()) &&
		 * "tagjhb".equals(searchRequest.getFcode())){ BoolQueryBuilder
		 * shouldQueryBuilder = new BoolQueryBuilder();
		 * shouldQueryBuilder.should(QueryBuilders.matchQuery("productType_code"
		 * ,"jhb" )); if (isQuery == false) { isQuery = true;
		 * boolQueryBuilder.must(shouldQueryBuilder);
		 * boolQueryBuilder1.must(shouldQueryBuilder); } else {
		 * boolQueryBuilder.filter(shouldQueryBuilder);
		 * boolQueryBuilder1.filter(shouldQueryBuilder); } }
		 * 
		 * 
		 * if(StringUtils.isNotEmpty(searchRequest.getFcode()) &&
		 * "taghqb".equals(searchRequest.getFcode())){ BoolQueryBuilder
		 * shouldQueryBuilder = new BoolQueryBuilder();
		 * shouldQueryBuilder.should(QueryBuilders.matchQuery("productType_code"
		 * ,"hqb" )); if (isQuery == false) { isQuery = true;
		 * boolQueryBuilder.must(shouldQueryBuilder);
		 * boolQueryBuilder1.must(shouldQueryBuilder); } else {
		 * boolQueryBuilder.filter(shouldQueryBuilder);
		 * boolQueryBuilder1.filter(shouldQueryBuilder); } }
		 */

		/**
		 * fcode以star开头的组合查询
		 */
		if (StringUtils.isNotEmpty(searchRequest.getFcode())
				&& "sta".equals(searchRequest.getFcode().substring(0, 3))) {

			String tagName = searchRequest.getFcode();
			String[] tag = tagName.split("\\.");
			for (int i = 0; i < tag.length; i++) {
				BoolQueryBuilder shouldQueryBuilder = new BoolQueryBuilder();
				if ((tag[i].substring(0, 3)).equalsIgnoreCase("dis")) {
					String s = tag[i].substring(8, tag[i].length());
					String[] dis = s.split("-");
					String dis_pf = StringUtils.isEmpty(dis[0]) ? "0" : dis[0];
					String disKey = "discount";
					if (dis.length == 1) {
						boolQueryBuilder.filter(QueryBuilders.rangeQuery(disKey).from(dis_pf));
					} else {
						boolQueryBuilder.filter(QueryBuilders.rangeQuery(disKey).from(dis_pf).to(dis[1])
								.includeLower(false).includeUpper(true));
					}

					if (isQuery == false) {
						isQuery = true;
						boolQueryBuilder.must(shouldQueryBuilder);
						boolQueryBuilder1.must(shouldQueryBuilder);
					} else {
						boolQueryBuilder.filter(shouldQueryBuilder);
						boolQueryBuilder1.filter(shouldQueryBuilder);
					}
				}
				if ((tag[i].substring(0, 3)).equalsIgnoreCase("sta")) {
					String t = tag[i].substring(4, tag[i].length());
					shouldQueryBuilder.should(QueryBuilders.matchQuery("tagName", t));
					if (isQuery == false) {
						isQuery = true;
						boolQueryBuilder.must(shouldQueryBuilder);
						boolQueryBuilder1.must(shouldQueryBuilder);
					} else {
						boolQueryBuilder.filter(shouldQueryBuilder);
						boolQueryBuilder1.filter(shouldQueryBuilder);
					}
				}
				if ((tag[i].substring(0, 3)).equalsIgnoreCase("tag")) {
					String t = tag[i].substring(3, tag[i].length());
					shouldQueryBuilder.should(QueryBuilders.matchQuery("productType_code", t));
					if (isQuery == false) {
						isQuery = true;
						boolQueryBuilder.must(shouldQueryBuilder);
						boolQueryBuilder1.must(shouldQueryBuilder);
					} else {
						boolQueryBuilder.filter(shouldQueryBuilder);
						boolQueryBuilder1.filter(shouldQueryBuilder);
					}
				}
				if ((tag[i].substring(0, 3)).equalsIgnoreCase("cmd")) {
					String t = tag[i].substring(3, tag[i].length());
					if (t.equalsIgnoreCase("xfg")) {
						shouldQueryBuilder.should(QueryBuilders.matchQuery("cash", "制造商"));
					}
					if (t.equalsIgnoreCase("yw")) {
						shouldQueryBuilder.should(QueryBuilders.matchQuery("family", "制造商"));
					}
					if (t.equalsIgnoreCase("pt")) {
						shouldQueryBuilder.should(QueryBuilders.matchQuery("normal", "制造商"));
					}
					if (isQuery == false) {
						isQuery = true;
						boolQueryBuilder.must(shouldQueryBuilder);
						boolQueryBuilder1.must(shouldQueryBuilder);
					} else {
						boolQueryBuilder.filter(shouldQueryBuilder);
						boolQueryBuilder1.filter(shouldQueryBuilder);
					}
				}

			}

		}

		if (StringUtils.isNotEmpty(searchRequest.getFcode())
				&& "cmd".equals(searchRequest.getFcode().substring(0, 3))) {

			String tagName = searchRequest.getFcode();
			String[] tag = tagName.split("\\.");
			for (int i = 0; i < tag.length; i++) {
				BoolQueryBuilder shouldQueryBuilder = new BoolQueryBuilder();
				if ((tag[i].substring(0, 3)).equalsIgnoreCase("dis")) {
					String s = tag[i].substring(8, tag[i].length());
					String[] dis = s.split("-");
					String dis_pf = StringUtils.isEmpty(dis[0]) ? "0" : dis[0];
					String disKey = "discount";
					if (dis.length == 1) {
						boolQueryBuilder.filter(QueryBuilders.rangeQuery(disKey).from(dis_pf));
					} else {
						boolQueryBuilder.filter(QueryBuilders.rangeQuery(disKey).from(dis_pf).to(dis[1])
								.includeLower(false).includeUpper(true));
					}
					if (isQuery == false) {
						isQuery = true;
						boolQueryBuilder.must(shouldQueryBuilder);
						boolQueryBuilder1.must(shouldQueryBuilder);
					} else {
						boolQueryBuilder.filter(shouldQueryBuilder);
						boolQueryBuilder1.filter(shouldQueryBuilder);
					}
				}
				if ((tag[i].substring(0, 3)).equalsIgnoreCase("sta")) {
					String t = tag[i].substring(4, tag[i].length());
					shouldQueryBuilder.should(QueryBuilders.matchQuery("tagName", t));
					if (isQuery == false) {
						isQuery = true;
						boolQueryBuilder.must(shouldQueryBuilder);
						boolQueryBuilder1.must(shouldQueryBuilder);
					} else {
						boolQueryBuilder.filter(shouldQueryBuilder);
						boolQueryBuilder1.filter(shouldQueryBuilder);
					}
				}
				if ((tag[i].substring(0, 3)).equalsIgnoreCase("tag")) {
					String t = tag[i].substring(3, tag[i].length());
					shouldQueryBuilder.should(QueryBuilders.matchQuery("productType_code", t));
					if (isQuery == false) {
						isQuery = true;
						boolQueryBuilder.must(shouldQueryBuilder);
						boolQueryBuilder1.must(shouldQueryBuilder);
					} else {
						boolQueryBuilder.filter(shouldQueryBuilder);
						boolQueryBuilder1.filter(shouldQueryBuilder);
					}
				}
				if ((tag[i].substring(0, 3)).equalsIgnoreCase("cmd")) {
					String t = tag[i].substring(3, tag[i].length());
					if (t.equalsIgnoreCase("xfg")) {
						shouldQueryBuilder.should(QueryBuilders.matchQuery("cash", "制造商"));
					}
					if (t.equalsIgnoreCase("yw")) {
						shouldQueryBuilder.should(QueryBuilders.matchQuery("family", "制造商"));
					}
					if (t.equalsIgnoreCase("pt")) {
						shouldQueryBuilder.should(QueryBuilders.matchQuery("normal", "制造商"));
					}
					if (isQuery == false) {
						isQuery = true;
						boolQueryBuilder.must(shouldQueryBuilder);
						boolQueryBuilder1.must(shouldQueryBuilder);
					} else {
						boolQueryBuilder.filter(shouldQueryBuilder);
						boolQueryBuilder1.filter(shouldQueryBuilder);
					}

				}

			}

		}

		if (StringUtils.isNotEmpty(searchRequest.getFcode())
				&& "dis".equals(searchRequest.getFcode().substring(0, 3))) {

			String tagName = searchRequest.getFcode();
			String[] tag = tagName.split("\\.");
			for (int i = 0; i < tag.length; i++) {
				BoolQueryBuilder shouldQueryBuilder = new BoolQueryBuilder();
				if ((tag[i].substring(0, 3)).equalsIgnoreCase("dis")) {
					String s = tag[i].substring(8, tag[i].length());
					String[] dis = s.split("-");
					String dis_pf = StringUtils.isEmpty(dis[0]) ? "0" : dis[0];
					String disKey = "discount";
					if (dis.length == 1) {
						boolQueryBuilder.filter(QueryBuilders.rangeQuery(disKey).from(dis_pf));
					} else {
						boolQueryBuilder.filter(QueryBuilders.rangeQuery(disKey).from(dis_pf).to(dis[1])
								.includeLower(false).includeUpper(true));
					}
					if (isQuery == false) {
						isQuery = true;
						boolQueryBuilder.must(shouldQueryBuilder);
						boolQueryBuilder1.must(shouldQueryBuilder);
					} else {
						boolQueryBuilder.filter(shouldQueryBuilder);
						boolQueryBuilder1.filter(shouldQueryBuilder);
					}
				}
				if ((tag[i].substring(0, 3)).equalsIgnoreCase("sta")) {
					String t = tag[i].substring(4, tag[i].length());
					shouldQueryBuilder.should(QueryBuilders.matchQuery("tagName", t));
					if (isQuery == false) {
						isQuery = true;
						boolQueryBuilder.must(shouldQueryBuilder);
						boolQueryBuilder1.must(shouldQueryBuilder);
					} else {
						boolQueryBuilder.filter(shouldQueryBuilder);
						boolQueryBuilder1.filter(shouldQueryBuilder);
					}
				}
				if ((tag[i].substring(0, 3)).equalsIgnoreCase("tag")) {
					String t = tag[i].substring(3, tag[i].length());
					shouldQueryBuilder.should(QueryBuilders.matchQuery("productType_code", t));
					if (isQuery == false) {
						isQuery = true;
						boolQueryBuilder.must(shouldQueryBuilder);
						boolQueryBuilder1.must(shouldQueryBuilder);
					} else {
						boolQueryBuilder.filter(shouldQueryBuilder);
						boolQueryBuilder1.filter(shouldQueryBuilder);
					}
				}
				if ((tag[i].substring(0, 3)).equalsIgnoreCase("cmd")) {
					String t = tag[i].substring(3, tag[i].length());
					if (t.equalsIgnoreCase("xfg")) {
						shouldQueryBuilder.should(QueryBuilders.matchQuery("cash", "制造商"));
					}
					if (t.equalsIgnoreCase("yw")) {
						shouldQueryBuilder.should(QueryBuilders.matchQuery("family", "制造商"));
					}
					if (t.equalsIgnoreCase("pt")) {
						shouldQueryBuilder.should(QueryBuilders.matchQuery("normal", "制造商"));
					}
					if (isQuery == false) {
						isQuery = true;
						boolQueryBuilder.must(shouldQueryBuilder);
						boolQueryBuilder1.must(shouldQueryBuilder);
					} else {
						boolQueryBuilder.filter(shouldQueryBuilder);
						boolQueryBuilder1.filter(shouldQueryBuilder);
					}

				}

			}

		}

		if (StringUtils.isNotEmpty(searchRequest.getFcode())
				&& "tag".equals(searchRequest.getFcode().substring(0, 3))) {

			String tagName = searchRequest.getFcode();
			String[] tag = tagName.split("\\.");
			for (int i = 0; i < tag.length; i++) {
				BoolQueryBuilder shouldQueryBuilder = new BoolQueryBuilder();
				if ((tag[i].substring(0, 3)).equalsIgnoreCase("dis")) {
					String s = tag[i].substring(8, tag[i].length());
					String[] dis = s.split("-");
					String dis_pf = StringUtils.isEmpty(dis[0]) ? "0" : dis[0];
					String disKey = "discount";
					if (dis.length == 1) {
						boolQueryBuilder.filter(QueryBuilders.rangeQuery(disKey).from(dis_pf));
					} else {
						boolQueryBuilder.filter(QueryBuilders.rangeQuery(disKey).from(dis_pf).to(dis[1])
								.includeLower(false).includeUpper(true));
					}
					if (isQuery == false) {
						isQuery = true;
						boolQueryBuilder.must(shouldQueryBuilder);
						boolQueryBuilder1.must(shouldQueryBuilder);
					} else {
						boolQueryBuilder.filter(shouldQueryBuilder);
						boolQueryBuilder1.filter(shouldQueryBuilder);
					}
				}
				if ((tag[i].substring(0, 3)).equalsIgnoreCase("sta")) {
					String t = tag[i].substring(4, tag[i].length());
					shouldQueryBuilder.should(QueryBuilders.matchQuery("tagName", t));
					if (isQuery == false) {
						isQuery = true;
						boolQueryBuilder.must(shouldQueryBuilder);
						boolQueryBuilder1.must(shouldQueryBuilder);
					} else {
						boolQueryBuilder.filter(shouldQueryBuilder);
						boolQueryBuilder1.filter(shouldQueryBuilder);
					}
				}
				if ((tag[i].substring(0, 3)).equalsIgnoreCase("tag")) {
					String t = tag[i].substring(3, tag[i].length());
					shouldQueryBuilder.should(QueryBuilders.matchQuery("productType_code", t));
					if (isQuery == false) {
						isQuery = true;
						boolQueryBuilder.must(shouldQueryBuilder);
						boolQueryBuilder1.must(shouldQueryBuilder);
					} else {
						boolQueryBuilder.filter(shouldQueryBuilder);
						boolQueryBuilder1.filter(shouldQueryBuilder);
					}
				}
				if ((tag[i].substring(0, 3)).equalsIgnoreCase("cmd")) {
					String t = tag[i].substring(3, tag[i].length());
					if (t.equalsIgnoreCase("xfg")) {
						shouldQueryBuilder.should(QueryBuilders.matchQuery("cash", "制造商"));
					}
					if (t.equalsIgnoreCase("yw")) {
						shouldQueryBuilder.should(QueryBuilders.matchQuery("family", "制造商"));
					}
					if (t.equalsIgnoreCase("pt")) {
						shouldQueryBuilder.should(QueryBuilders.matchQuery("normal", "制造商"));
					}
					if (isQuery == false) {
						isQuery = true;
						boolQueryBuilder.must(shouldQueryBuilder);
						boolQueryBuilder1.must(shouldQueryBuilder);
					} else {
						boolQueryBuilder.filter(shouldQueryBuilder);
						boolQueryBuilder1.filter(shouldQueryBuilder);
					}
				}

			}

		}
		/*
		 * if(StringUtils.isNotEmpty(searchRequest.getFcode()) &&
		 * "sta".equals(searchRequest.getFcode().substring(0, 3))){
		 * BoolQueryBuilder shouldQueryBuilder = new BoolQueryBuilder(); String
		 * tagName=searchRequest.getFcode().substring(4,
		 * searchRequest.getFcode().length()); String[] tag =
		 * tagName.split(","); for(int i = 0;i<tag.length;i++){
		 * shouldQueryBuilder.should(QueryBuilders.matchQuery("tagName",tag[i]))
		 * ; }
		 * 
		 * if (isQuery == false) { isQuery = true;
		 * boolQueryBuilder.must(shouldQueryBuilder);
		 * boolQueryBuilder1.must(shouldQueryBuilder); } else {
		 * boolQueryBuilder.filter(shouldQueryBuilder);
		 * boolQueryBuilder1.filter(shouldQueryBuilder); } }
		 */

		/*
		 * if(StringUtils.isNotEmpty(searchRequest.getFcode()) &&
		 * "star0".equals(searchRequest.getFcode())){ BoolQueryBuilder
		 * shouldQueryBuilder = new BoolQueryBuilder();
		 * shouldQueryBuilder.should(QueryBuilders.matchQuery("tagName","激活" ));
		 * if (isQuery == false) { isQuery = true;
		 * boolQueryBuilder.must(shouldQueryBuilder);
		 * boolQueryBuilder1.must(shouldQueryBuilder); } else {
		 * boolQueryBuilder.filter(shouldQueryBuilder);
		 * boolQueryBuilder1.filter(shouldQueryBuilder); } }
		 * 
		 * if(StringUtils.isNotEmpty(searchRequest.getFcode()) &&
		 * "star1".equals(searchRequest.getFcode())){ BoolQueryBuilder
		 * shouldQueryBuilder = new BoolQueryBuilder();
		 * shouldQueryBuilder.should(QueryBuilders.matchQuery("tagName","一星" ));
		 * if (isQuery == false) { isQuery = true;
		 * boolQueryBuilder.must(shouldQueryBuilder);
		 * boolQueryBuilder1.must(shouldQueryBuilder); } else {
		 * boolQueryBuilder.filter(shouldQueryBuilder);
		 * boolQueryBuilder1.filter(shouldQueryBuilder); } }
		 * 
		 * if(StringUtils.isNotEmpty(searchRequest.getFcode()) &&
		 * "star2".equals(searchRequest.getFcode())){ BoolQueryBuilder
		 * shouldQueryBuilder = new BoolQueryBuilder();
		 * shouldQueryBuilder.should(QueryBuilders.matchQuery("tagName","二星" ));
		 * if (isQuery == false) { isQuery = true;
		 * boolQueryBuilder.must(shouldQueryBuilder);
		 * boolQueryBuilder1.must(shouldQueryBuilder); } else {
		 * boolQueryBuilder.filter(shouldQueryBuilder);
		 * boolQueryBuilder1.filter(shouldQueryBuilder); } }
		 * 
		 * if(StringUtils.isNotEmpty(searchRequest.getFcode()) &&
		 * "star3".equals(searchRequest.getFcode())){ BoolQueryBuilder
		 * shouldQueryBuilder = new BoolQueryBuilder();
		 * shouldQueryBuilder.should(QueryBuilders.matchQuery("tagName","三星" ));
		 * if (isQuery == false) { isQuery = true;
		 * boolQueryBuilder.must(shouldQueryBuilder);
		 * boolQueryBuilder1.must(shouldQueryBuilder); } else {
		 * boolQueryBuilder.filter(shouldQueryBuilder);
		 * boolQueryBuilder1.filter(shouldQueryBuilder); } }
		 * if(StringUtils.isNotEmpty(searchRequest.getFcode()) &&
		 * "star4".equals(searchRequest.getFcode())){ BoolQueryBuilder
		 * shouldQueryBuilder = new BoolQueryBuilder();
		 * shouldQueryBuilder.should(QueryBuilders.matchQuery("tagName","四星" ));
		 * if (isQuery == false) { isQuery = true;
		 * boolQueryBuilder.must(shouldQueryBuilder);
		 * boolQueryBuilder1.must(shouldQueryBuilder); } else {
		 * boolQueryBuilder.filter(shouldQueryBuilder);
		 * boolQueryBuilder1.filter(shouldQueryBuilder); } }
		 * if(StringUtils.isNotEmpty(searchRequest.getFcode()) &&
		 * "star5".equals(searchRequest.getFcode())){ BoolQueryBuilder
		 * shouldQueryBuilder = new BoolQueryBuilder();
		 * shouldQueryBuilder.should(QueryBuilders.matchQuery("tagName","五星" ));
		 * if (isQuery == false) { isQuery = true;
		 * boolQueryBuilder.must(shouldQueryBuilder);
		 * boolQueryBuilder1.must(shouldQueryBuilder); } else {
		 * boolQueryBuilder.filter(shouldQueryBuilder);
		 * boolQueryBuilder1.filter(shouldQueryBuilder); } }
		 */

		// 普通商品搜索
		/*
		 * if( StringUtils.isNotEmpty(searchRequest.getFcode()) &&
		 * "cmdpt".equals(searchRequest.getFcode())){
		 * 
		 * 
		 * 
		 * 
		 * BoolQueryBuilder shouldQueryBuilder = new BoolQueryBuilder();
		 * shouldQueryBuilder.should(QueryBuilders.matchQuery("normal", "制造商"));
		 * //区分是否为必要条件 if (isQuery == false) { isQuery = true;
		 * boolQueryBuilder.must(shouldQueryBuilder);
		 * boolQueryBuilder1.must(shouldQueryBuilder); } else {
		 * boolQueryBuilder.filter(shouldQueryBuilder);
		 * boolQueryBuilder1.filter(shouldQueryBuilder); }
		 * 
		 * }
		 */

		// 企业 sid
		/*
		 * String supplierId=searchRequest.getSupplierId();
		 * if(StringUtils.isEmpty(supplierId)){
		 * supplierId=searchRequest.getSupplierName(); }
		 * LOGGER.info("按企业搜索supplierId：" + supplierId); if
		 * (StringUtils.isNotEmpty(supplierId)) {
		 * boolQueryBuilder.must(QueryBuilders.matchQuery(Constant.
		 * FIELD_SUPPLY_NAME, supplierId));
		 * boolQueryBuilder1.must(QueryBuilders.matchQuery(Constant.
		 * FIELD_SUPPLY_NAME, supplierId)); }
		 */
		if (StringUtils.isNotEmpty(searchRequest.getSupplierId())) {

			String[] supplierName = searchRequest.getSupplierId().split(",");
			BoolQueryBuilder shouldQueryBuilder = new BoolQueryBuilder();
			for (int i = 0; i < supplierName.length; i++) {
				shouldQueryBuilder.should(QueryBuilders.matchQuery("supplier_name", supplierName[i]));
			}
			// 区分是否为必要条件
			if (isQuery == false) {
				isQuery = true;
				boolQueryBuilder.must(shouldQueryBuilder);
				boolQueryBuilder1.must(shouldQueryBuilder);
			} else {
				boolQueryBuilder.filter(shouldQueryBuilder);
				boolQueryBuilder1.filter(shouldQueryBuilder);
			}
		}

		// 多选 ，cyid
		if (StringUtils.isNotEmpty(searchRequest.getCyid())) {

			String[] cyid = searchRequest.getCyid().split(",");
			BoolQueryBuilder shouldQueryBuilder = new BoolQueryBuilder();
			for (int i = 0; i < cyid.length; i++) {
				shouldQueryBuilder.should(QueryBuilders.matchQuery("supplier_name", cyid[i]));
			}
			// 区分是否为必要条件
			if (isQuery == false) {
				isQuery = true;
				boolQueryBuilder.must(shouldQueryBuilder);
				boolQueryBuilder1.must(shouldQueryBuilder);
			} else {
				boolQueryBuilder.filter(shouldQueryBuilder);
				boolQueryBuilder1.filter(shouldQueryBuilder);
			}
		}

		// 价格区间
		if (StringUtils.isNotEmpty(searchRequest.getPriceRange())) {

			String[] price_split = searchRequest.getPriceRange().split("-");
			String price_pf = StringUtils.isEmpty(price_split[0]) ? "0" : price_split[0];
			// 价格尾部为空 133-
			String priceKey = searchRequest.isB2C() == true ? "unit_price" : "msp";
			// 促销价格
			if (priceKey.equals("unit_price")) {
				priceKey = AssemblingItem.getWayPrice(searchRequest);
			}
			if (price_split.length == 1) {
				boolQueryBuilder.filter(QueryBuilders.rangeQuery(priceKey).from(price_pf));
				boolQueryBuilder1.filter(QueryBuilders.rangeQuery(priceKey).from(price_pf));
			} else {
				boolQueryBuilder.filter(QueryBuilders.rangeQuery(priceKey).from(price_pf).to(price_split[1]));
				boolQueryBuilder1.filter(QueryBuilders.rangeQuery(priceKey).from(price_pf).to(price_split[1]));
			}
		}

		/**
		 * 折扣區間
		 */
		if (StringUtils.isNotEmpty(searchRequest.getDiscount())) {
			String[] dis = searchRequest.getDiscount().split("-");
			String dis_pf = StringUtils.isEmpty(dis[0]) ? "0" : dis[0];
			String disKey = "discount";
			if (dis.length == 1) {
				boolQueryBuilder.filter(QueryBuilders.rangeQuery(disKey).from(dis_pf));
			} else {
				boolQueryBuilder.filter(QueryBuilders.rangeQuery(disKey).from(dis_pf).to(dis[1]).includeLower(false)
						.includeUpper(true));
			}
		}

		// 是否是卓越
		boolean isZY = searchRequest.isZy();
		if (isZY) {
			searchRequest.setB2csupply(Constant.THIRTY_ONE);
		}
		// 货源
		if (StringUtils.isNotEmpty(searchRequest.getB2csupply())) {
			if (isQuery == false) {
				isQuery = true;
				boolQueryBuilder
						.must(QueryBuilders.matchQuery(Constant.FIELD_B2C_SUPPLY, searchRequest.getB2csupply()));
				boolQueryBuilder1
						.must(QueryBuilders.matchQuery(Constant.FIELD_B2C_SUPPLY, searchRequest.getB2csupply()));
			} else {
				boolQueryBuilder
						.filter(QueryBuilders.matchQuery(Constant.FIELD_B2C_SUPPLY, searchRequest.getB2csupply()));
				boolQueryBuilder1
						.filter(QueryBuilders.matchQuery(Constant.FIELD_B2C_SUPPLY, searchRequest.getB2csupply()));
			}
		}
		/*
		 * 特殊服务--you understand if you searchRequest.entryType a value equal to
		 * 10 then blocked supply of type 12 commodity and
		 * searchRequest.entryType to 10,the supply cannot be 12, otherwise the
		 * query result to 0 翼支付 不能看保税区的商品
		 */
		// LOGGER.info("渠道标示："+searchRequest.getEntryType());
		/*
		 * if(searchRequest.getEntryType()!=null&&searchRequest.getEntryType().
		 * equals(Constant.CHANNEL_BESTPAY)){
		 * LOGGER.info("翼支付渠道禁止获取保税区商品限制：已限制");
		 * boolQueryBuilder.mustNot(QueryBuilders.matchQuery(Constant.
		 * FIELD_B2C_SUPPLY, Constant.SPECIAL_SUPPLY_TWELVE));
		 * boolQueryBuilder1.mustNot(QueryBuilders.matchQuery(Constant.
		 * FIELD_B2C_SUPPLY, Constant.SPECIAL_SUPPLY_TWELVE)); }
		 * if(searchRequest.getEntryType()!=null&&searchRequest.getEntryType().
		 * equals(Constant.CHANNEL_WAP_B2B)){ LOGGER.info(
		 * "B2B WAP渠道禁止获取保税区商品限制：已限制");
		 * boolQueryBuilder.mustNot(QueryBuilders.matchQuery(Constant.
		 * FIELD_B2C_SUPPLY, Constant.SPECIAL_SUPPLY_TWELVE));
		 * boolQueryBuilder1.mustNot(QueryBuilders.matchQuery(Constant.
		 * FIELD_B2C_SUPPLY, Constant.SPECIAL_SUPPLY_TWELVE)); }
		 */
		// pc 1 wap 3 8 翼支付 10 pc端限制品牌
		/*
		 * if(searchRequest.getEntryType()!=null&&searchRequest.getEntryType().
		 * equals(Constant.CHANNEL_PC)){ String tBrang =
		 * "健安喜,医食同源,瑞思Swisse,双心,纳世凯尔,Blackmores 澳佳宝,Blackmores澳佳宝,Swisse,Allergy Research Group,健康加号,Nutricology,小林制药,铁元,Altapharma,Nature's Way,佳思敏,明治,Blackmores/澳佳宝,freezeframe,乐康膏,地球妈妈,新谷,柯得仕,红印,自然之宝,ARG,HYDRODOL,Life Space,MYMI,Ostelin Kids,PERLES DE PEAU,佰澳朗德,山本汉方,欧缇丽,美的,澳佳宝"
		 * ;
		 * 
		 * String[] bNames = tBrang.split(","); for (int i = 0; i <
		 * bNames.length; i++) {
		 * boolQueryBuilder.mustNot(QueryBuilders.matchQuery(Constant.
		 * FIELD_BRAND_NAME, bNames[i]));
		 * boolQueryBuilder1.mustNot(QueryBuilders.matchQuery(Constant.
		 * FIELD_BRAND_NAME, bNames[i])); } LOGGER.info("pc端品牌限制：已限制"); }
		 */
		// 商品ID
		if (StringUtils.isNotEmpty(searchRequest.getPid())) {
			boolQueryBuilder.filter(QueryBuilders.matchQuery(Constant.FIELD_PID, searchRequest.getPid()));
			boolQueryBuilder1.filter(QueryBuilders.matchQuery(Constant.FIELD_PID, searchRequest.getPid()));
		}
		// 属性
		if (StringUtils.isNotEmpty(searchRequest.getAttrcodes())) {
			boolQueryBuilder.filter(QueryBuilders.matchQuery(Constant.FIELD_B2B_DISPAT, searchRequest.getAttrcodes()));
			boolQueryBuilder1.filter(QueryBuilders.matchQuery(Constant.FIELD_B2B_DISPAT, searchRequest.getAttrcodes()));
		}
		// 入驻区域搜索
		if (StringUtils.isNotEmpty(searchRequest.getCompanyRegion())) {
			boolQueryBuilder
					.filter(QueryBuilders.matchQuery(Constant.FIELD_COMPANY_REGION, searchRequest.getCompanyRegion()));
			boolQueryBuilder1
					.filter(QueryBuilders.matchQuery(Constant.FIELD_COMPANY_REGION, searchRequest.getCompanyRegion()));
		}
		if (StringUtils.isEmpty(searchRequest.getPid())) {
			boolQueryBuilder.filter(QueryBuilders.matchQuery(prefix + "_istate", 1));
			boolQueryBuilder1.filter(QueryBuilders.matchQuery(prefix + "_istate", 1));
		}
		LOGGER.info("搜索最后执行脚本boolQueryBuilder：" + boolQueryBuilder.toString());
		LOGGER.info("搜索最后执行脚本boolQueryBuilder1：" + boolQueryBuilder1.toString());
		List<BoolQueryBuilder> listBoolQueryBuilder = new ArrayList<BoolQueryBuilder>();
		listBoolQueryBuilder.add(boolQueryBuilder);
		listBoolQueryBuilder.add(boolQueryBuilder1);
		return listBoolQueryBuilder;
	}

	/**
	 * 搜索分组
	 * 
	 * @param requestBuilder
	 * @param isB2C
	 * @return
	 */
	public static SearchRequestBuilder group(SearchRequestBuilder requestBuilder, boolean isB2C,
			SearchRequest searchRequest) {
		String prefix = isB2C ? "b2c" : "b2b";
		// 类目
		requestBuilder.addAggregation(AggregationBuilders.terms("by_cdid").field(prefix + "_cdids").size(1000));
		// 原产国
		// requestBuilder.addAggregation(AggregationBuilders.terms("by_cyName").field("cyid").size(100));
		// 企业
		requestBuilder.addAggregation(AggregationBuilders.terms("by_supplier_name").field("supplier_name").size(100));
		// 品牌
		requestBuilder.addAggregation(AggregationBuilders.terms("by_brandName").field("brandName").size(100));
		// 价格区间

		if (isB2C == false) {
			// 类目属性
			requestBuilder.addAggregation(AggregationBuilders.terms("by_b2b_dispat").field("b2b_dispat").size(100));
			requestBuilder.addAggregation(AggregationBuilders.range("msp").field("unit_price").addUnboundedTo(50)
					.addRange(50, 100).addRange(100, 200).addRange(200, 300).addRange(300, 500).addRange(500, 800)
					.addUnboundedFrom(800));
		}
		if (isB2C == true) {
			String price = "unit_price";
			// 促销价格
			price = AssemblingItem.getWayPrice(searchRequest);
			// 货源类型
			requestBuilder.addAggregation(AggregationBuilders.terms("by_b2cSupply").field("b2cSupply"));
			requestBuilder.addAggregation(AggregationBuilders.range("price").field(price).addUnboundedTo(50)
					.addRange(50, 100).addRange(100, 200).addRange(200, 300).addRange(300, 500).addRange(500, 800)
					.addUnboundedFrom(800));
		}

		return requestBuilder;
	}
}
