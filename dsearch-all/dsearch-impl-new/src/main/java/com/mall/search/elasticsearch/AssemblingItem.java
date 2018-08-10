package com.mall.search.elasticsearch;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.common.json.ParseException;
import com.mall.dsearch.vo.AttrTerm;
import com.mall.dsearch.vo.CatalogAttr;
import com.mall.dsearch.vo.CatalogAttrVal;
import com.mall.dsearch.vo.PlainProduct;
import com.mall.dsearch.vo.SearchRequest;
import com.mall.search.cache.CacheFactory;
import com.mall.search.cache.JedisCateAndAttrByOne;
import com.mall.search.constant.Constant;
import com.mall.search.utils.StringUtils;
import com.mall.search.vo.ResponseItem;

/**
 * 装配返回值
 * 
 * @author doublell
 *
 */
public class AssemblingItem {

	private static final Logger LOGGER = LoggerFactory.getLogger(AssemblingItem.class);

	/**
	 * 返回值拼装
	 * 
	 * @param searchResponse
	 * @param searchRequest
	 * @return
	 */
	public static ResponseItem responseBuilder(org.elasticsearch.action.search.SearchResponse searchResponse,
			SearchRequest searchRequest) {
		String prefix = searchRequest.isB2C() == true ? "b2c" : "b2b";
		ResponseItem responseItem = null;
		if (searchResponse.getHits().getTotalHits() > 0) {
			responseItem = new ResponseItem();
			List<PlainProduct> items = new ArrayList<PlainProduct>();
			Map<String, String> productCountrys = new HashMap<String, String>();

			// 概要商品的装载
			SearchHit[] searchHits = searchResponse.getHits().getHits();
			// 老业务兼容，新版中去掉
			// TODO
			if (searchHits.length > 0) {
				for (SearchHit searchHit : searchHits) {
					PlainProduct item = buildItem(searchHit, prefix, searchRequest);
					if (null != item) {
						productCountrys.put(item.getCyid(), item.getCountryImg());
					}
					items.add(item);
				}
			}

			// 概要商品拼装
			responseItem.setProductCountrys(productCountrys);
			responseItem.setProductItemList(items);
		}
		return responseItem;
	}

	/**
	 * 构建-拼装商品
	 *
	 * @param searchHit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static PlainProduct buildItem(SearchHit searchHit, String prefix, SearchRequest searchRequest) {
		PlainProduct item = null;
		if (null != searchHit) {
			item = new PlainProduct();

			// 获取es 中的对应field值

			List<String> iconsTxT = (ArrayList<String>) searchHit.getSource().get("product_iconsTxT");
			List<String> tagName = (ArrayList<String>) searchHit.getSource().get("tagName");
			List<String> productType_code = (ArrayList<String>) searchHit.getSource().get("productType_code");
			String pid = (String) searchHit.getSource().get("pid");
			// B2B 商品名称
			String b2bpname = (String) searchHit.getSource().get("b2b_pname");
			// B2C 商品名称
			String b2cPname = (String) searchHit.getSource().get("b2c_pname");
			// 源产地
			String cyid = (String) searchHit.getSource().get("cyid");
			// 库存
			Integer inventory = (Integer) searchHit.getSource().get("stock");
			// B2B 最小销售价
			Double msp = ((Double) searchHit.getSource().get("msp"));
			// B2B 最大销售价
			Double maxsp = (Double) searchHit.getSource().get("maxsp");
			// 品牌
			String brandName = (String) searchHit.getSource().get("brandName");
			// B2C 市场价
			Double domestic_price = (Double) searchHit.getSource().get("domestic_price");
			Double cash_hqj = (Double) searchHit.getSource().get("cashHqj");
			// 折扣
			Double discount = (Double) searchHit.getSource().get("discount");
			Double hqj = (Double) searchHit.getSource().get("hqj");
			String prodType = (String) searchHit.getSource().get("prod_type");
			// 翼支付渠道取bestoay_price价格
			Double unit_price = 0.0;
			if (searchRequest.getEntryType() != null && searchRequest.getEntryType().equals(Constant.CHANNEL_BESTPAY)) {
				// B2C 翼支付 现价
				unit_price = (Double) searchHit.getSource().get("bestoay_price");
			} else if (searchRequest.getEntryType() != null
					&& searchRequest.getEntryType().equals(Constant.CHANNEL_WAP_B2B)) {
				unit_price = (Double) searchHit.getSource().get("dealer_price");
				domestic_price = (Double) searchHit.getSource().get("retail_price");
			} else {
				// B2C 现价
				unit_price = (Double) searchHit.getSource().get("unit_price");
			}
			// 促销类型
			String promotion = (String) searchHit.getSource().get("promotion");
			// 货源
			Integer b2csupply = (Integer) searchHit.getSource().get("b2cSupply");
			// 货币汇率
			String moneyRate = (String) searchHit.getSource().get("moneyRate");
			// 货币符号
			String moneySymbols = (String) searchHit.getSource().get("moneySymbols");
			// 保税区
			String bondedZone = (String) searchHit.getSource().get("bondedZone");
			// 源产国的图片路径
			String countryImg = (String) searchHit.getSource().get("countryImg");
			// 国家名
			String cyName = (String) searchHit.getSource().get("cyName");
			String imageurl = (String) searchHit.getSource().get("imageUrl");
			String imageAlt = (String) searchHit.getSource().get("imageAlt");
			String supplierId = (String) searchHit.getSource().get("sid");
			String supplierCode = (String) searchHit.getSource().get("supplier_code");
			String supplierName = (String) searchHit.getSource().get("supplier_name");
			String supplierNameJc = (String) searchHit.getSource().get("supplier_name_jc");
			String supplierKfTel = (String) searchHit.getSource().get("supplier_kf_tel");
			String companyRegion = (String) searchHit.getSource().get("company_region");
			String supplierLogoImgurl = (String) searchHit.getSource().get("supplier_logo_imgurl");

			// Integer proSales = (Integer)
			// searchHit.getSource().get("proSales");
			// String prefix=searchRequest.isB2C()==true?"b2c":"b2b";
			@SuppressWarnings("rawtypes")
			Map promotionPriceMap = (Map) searchHit.getSource().get("promotion_price");
			String priceStr = AssemblingItem.getPromotionPrice(searchRequest);
			Double promotion_price = (Double) promotionPriceMap.get(priceStr);

			HighlightField highlightField = searchHit.getHighlightFields().get(prefix + "_pname");
			String highText = "";
			if (highlightField != null) {
				Text[] fragments = highlightField.getFragments();
				for (Text t : fragments) {
					highText += t.string();
				}
			}
			// 拼装field 到 PlainProduct类中
			item.setPid(pid);

			item.setProd_type(prodType);
			item.setPname(b2bpname);
			item.setB2cPname(b2cPname);
			item.setInventory(inventory);
			item.setCyid(cyid);
			item.setCyName(cyName);
			item.setMsp(Float.parseFloat("" + msp.doubleValue()));
			item.setMaxsp(Float.parseFloat("" + maxsp.doubleValue()));
			item.setBrandName(brandName);
			item.setBondedZone(bondedZone);
			item.setPromotion(promotion);
			item.setCountryImg(countryImg);
			item.setImageurl(imageurl);
			item.setImageAlt(imageAlt);
			item.setSupplierId(supplierId);
			item.setSupplierCode(supplierCode);
			item.setSupplierKfTel(supplierKfTel);
			item.setSupplierNameJc(supplierNameJc);
			item.setSupplierName(supplierName);
			item.setCompanyRegion(companyRegion);
			item.setSupplierLogoImgurl(supplierLogoImgurl);

			if (StringUtils.isEmpty(highText)) {
				if (prefix.equals("b2c")) {
					item.setHighlightedPname(item.getB2cPname());
				} else {
					item.setHighlightedPname(item.getPname());
				}
			} else {
				item.setHighlightedPname(highText);
			}
			if (iconsTxT != null) {
				item.setProduct_iconsTxT(iconsTxT);
			}
			if (tagName != null) {
				item.setTagName(tagName);
			}
			if (productType_code != null) {
				item.setProductType_code(productType_code);
			}

			// 鑫网价
			BigDecimal unitPrice = null;
			if (null != unit_price) {
				unitPrice = new BigDecimal(unit_price);
				item.setUnit_price(unitPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
			}

			// 促销价格 - 返回客户端
			if (null != promotionPriceMap) {
				if (null != promotion_price) {
					BigDecimal promotionUnitPrice = new BigDecimal(promotion_price);
					item.setPromotion_price(promotionUnitPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
				}
			}
			// 是否有促销
			if (null != item.getPromotion_price()) {
				if (item.getUnit_price().compareTo(item.getPromotion_price()) != 0) {
					item.setBoolPromotion(true);
				} else {
					item.setBoolPromotion(false);
				}
			}
			// 市场价
			if (null != domestic_price) {
				BigDecimal domesticPrice = new BigDecimal(domestic_price);
				item.setDomestic_price(domesticPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
			}
			if (null != cash_hqj) {
				BigDecimal cashHqj = new BigDecimal(cash_hqj);
				item.setCash_Hqj(cashHqj.setScale(2, BigDecimal.ROUND_HALF_UP));
			}
			if (null != hqj) {
				BigDecimal bigDecimal = new BigDecimal(hqj);
				item.setHqj(bigDecimal);

			}

			// 货币汇率换算
			if (moneyRate != null && unitPrice != null) {
				Double moneyRate_d = Double.valueOf(moneyRate);
				BigDecimal bigDecimal = new BigDecimal(moneyRate_d);
				BigDecimal multiply = bigDecimal.multiply(unitPrice);
				item.setExchange(moneySymbols + multiply.setScale(2, BigDecimal.ROUND_HALF_UP));
			}

			// 判断商品类型
			if (b2csupply != null) {
				item.setB2csupply(b2csupply);
				if (1 == b2csupply) {
					item.setProStyleDescribe(Constant.STR_COUNTRY_DELIVERY);
				} else if (12 == b2csupply) {
					item.setProStyleDescribe(Constant.STR_BONDZONE_DELIVERY);
				} else if (50 == b2csupply) {
					item.setProStyleDescribe(Constant.STR_TM_DELIVERY);
				} else if (51 == b2csupply) {
					item.setProStyleDescribe(Constant.STR_POP_DELIVERY);
				} else if (11 == b2csupply) {
					item.setProStyleDescribe(Constant.STR_DIRECT_DELIVERY);
				}
			}
			// 判断库存
			if (inventory != null && inventory > 0) {
				item.setXinventory(true);
			} else {
				item.setXinventory(false);
			}
		}
		return item;
	}

	/**
	 * 属性-拼装
	 *
	 * @param searchResponse_
	 * @param responseItem
	 * @return
	 */
	public static LinkedHashMap<CatalogAttr, LinkedHashSet<CatalogAttrVal>> responseterm(
			org.elasticsearch.action.search.SearchResponse searchResponse_, ResponseItem responseItem,
			SearchRequest searchRequest) {

		LinkedHashMap<CatalogAttr, LinkedHashSet<CatalogAttrVal>> termMap = new LinkedHashMap<CatalogAttr, LinkedHashSet<CatalogAttrVal>>();

		// 品牌名
		/*
		 * LinkedHashSet<CatalogAttrVal> attrVals = facetdefult(responseItem,
		 * searchResponse_, "by_brandName");
		 * LOGGER.info("品牌分组数量："+attrVals.size()); CatalogAttrVal attrval = new
		 * CatalogAttrVal(); if(searchRequest.getBrandName()!=null &&
		 * !"".equals(searchRequest.getBrandName())){
		 * 
		 * String key = searchRequest.getBrandName();
		 * LOGGER.info("品牌分组数量-查询品牌名称：" + searchRequest.getBrandName());
		 * attrval.setId(key); attrval.setName(key); attrVals.add(attrval);
		 * LOGGER.info("品牌分组数量-从请求中添加："+attrVals.size()); }
		 */

		termMap.put(facetattr(Constant.FIELD_BRAND_NAME, searchRequest),
				facetdefult(responseItem, searchResponse_, "by_brandName"));
		// 企业
		termMap.put(facetattr(Constant.FIELD_SID, searchRequest),
				facetdefult(responseItem, searchResponse_, "by_supplier_name"));

		// 价格区间
		if (searchRequest.isB2C() == false) {
			termMap.put(facetattr(Constant.FIELD_MSP, searchRequest),
					facetdefult(responseItem, searchResponse_, "msp"));
			facetB2battr(termMap, responseItem, searchResponse_);
		} else {
			// 货源
			termMap.put(facetattr(Constant.FIELD_B2C_SUPPLY, searchRequest),
					facetdefult(responseItem, searchResponse_, "by_b2cSupply"));
			termMap.put(facetattr(Constant.FIELD_UNIT_PRICE, searchRequest),
					facetdefult(responseItem, searchResponse_, "price"));
		}

		// 过滤出已经选择的属性值，同时把已经选择的属性删除
		// doFilter(termMap,searchRequest);

		return termMap;

	}

	/**
	 * 创建属性
	 *
	 * @param name
	 * @return
	 */
	private static CatalogAttr facetattr(String name, SearchRequest searchRequest) {
		CatalogAttr catalogAttr = null;
		if (name.equals(Constant.FIELD_BRAND_NAME)) {
			catalogAttr = new CatalogAttr();
			catalogAttr.setName(Constant.STR_BRANDNAME);
			catalogAttr.setSortval(-4);
			catalogAttr.setBrandName(true);
		} else if (name.equals(Constant.FIELD_B2C_SUPPLY)) {
			catalogAttr = new CatalogAttr();
			catalogAttr.setName(Constant.STR_SUPPLY);
			catalogAttr.setSortval(0);
			catalogAttr.setB2csupply(true);
		}
		/*
		 * else if (name.equals(Constant.FIELD_CYID)) { catalogAttr = new
		 * CatalogAttr(); catalogAttr.setName(Constant.STR_COUNTRY_ORIGIN);
		 * catalogAttr.setSortval(-2); catalogAttr.setCyid(true); }
		 */
		else if (name.equals(Constant.FIELD_SID)) {
			catalogAttr = new CatalogAttr();
			catalogAttr.setName(Constant.STR_SUPPLIER_ORIGIN);
			catalogAttr.setSortval(-2);
			catalogAttr.setSupplierId(true);
		} else if (name.equals(Constant.FIELD_UNIT_PRICE) || name.equals(Constant.FIELD_MSP)) {
			catalogAttr = new CatalogAttr();
			catalogAttr.setName(Constant.STR_PRICE);
			catalogAttr.setSortval(-3);
			catalogAttr.setPriceRange(true);
		}
		return catalogAttr;
	}

	/**
	 * 创建属性值属性值
	 *
	 * @param searchResponse
	 * @param term
	 * @return
	 */
	private static LinkedHashSet<CatalogAttrVal> facetdefult(ResponseItem responseItem,
			org.elasticsearch.action.search.SearchResponse searchResponse, String term) {

		LinkedHashSet<CatalogAttrVal> attrVals = new LinkedHashSet<CatalogAttrVal>();

		if (term.equals("price") || term.equals("msp")) {
			Range agg = searchResponse.getAggregations().get(term);
			for (Range.Bucket entry : agg.getBuckets()) {
				int count = (int) entry.getDocCount();
				if (count == 0) {
					continue;
				}
				String key = entry.getKeyAsString(); // Range as key
				LOGGER.debug(key);
				CatalogAttrVal attrval = setPriceVal(key, count);
				attrVals.add(attrval);
			}
		} else {
			Terms agg = searchResponse.getAggregations().get(term);
			for (Terms.Bucket entry : agg.getBuckets()) {
				if ("by_brandName".equals(term)) {
					CatalogAttrVal attrval = new CatalogAttrVal();
					String key = (String) entry.getKey();
					attrval.setId(key);
					attrval.setName(key);
					attrVals.add(attrval);
				} else if ("by_b2cSupply".equals(term)) {
					CatalogAttrVal attrval = new CatalogAttrVal();
					int key = Integer.parseInt(((Long) entry.getKey()).toString());
					attrval.setSortval(key);
					if (1 == key) {
						attrval.setName(Constant.STR_COUNTRY_DELIVERY);
						attrval.setId("1");
					} else if (11 == key) {
						attrval.setName(Constant.STR_DIRECT_DELIVERY);
						attrval.setId("11");
					} else if (12 == key) {
						attrval.setName(Constant.STR_BONDZONE_DELIVERY);
						attrval.setId("12");
					} else if (51 == key) {
						attrval.setName(Constant.STR_POP_DELIVERY);
						attrval.setId("51");
					} else if (21 == key) {
						attrval.setName("韩国直邮");
						attrval.setId("21");
					}
					attrVals.add(attrval);
				} else if ("by_supplier_name".equals(term)) {
					CatalogAttrVal attrval = new CatalogAttrVal();
					String key = (String) entry.getKey();
					attrval.setName(key);
					attrval.setId(key);
					attrVals.add(attrval);
				}
			}
		}

		return attrVals;
	}

	/**
	 * 价格区间-附属属性值
	 *
	 * @param str
	 * @param it
	 * @return
	 */
	private static CatalogAttrVal setPriceVal(String str, int it) {
		CatalogAttrVal attrval = new CatalogAttrVal();
		attrval.setSortval(it);
		if (str.equals("*-50.0")) {
			attrval.setName("0-50");
			attrval.setId("0-50");
		} else if (str.equals("50.0-100.0")) {
			attrval.setName("50-100");
			attrval.setId("50-100");
		} else if (str.equals("100.0-200.0")) {
			attrval.setName("100-200");
			attrval.setId("100-200");
		} else if (str.equals("200.0-300.0")) {
			attrval.setName("200-300");
			attrval.setId("200-300");
		} else if (str.equals("300.0-500.0")) {
			attrval.setName("300-500");
			attrval.setId("300-500");
		} else if (str.equals("500.0-800.0")) {
			attrval.setName("500-800");
			attrval.setId("500-800");
		} else if (str.equals("800.0-*")) {
			attrval.setName("800以上");
			attrval.setId("800-");
		}
		return attrval;
	}

	/**
	 * 解析b2b属性 封装属性值
	 */
	public static void facetB2battr(Map<CatalogAttr, LinkedHashSet<CatalogAttrVal>> termMap, ResponseItem responseItem,
			org.elasticsearch.action.search.SearchResponse searchResponse_) {
		Terms agg = searchResponse_.getAggregations().get("by_b2b_dispat");
		for (Terms.Bucket entry : agg.getBuckets()) {
			CatalogAttrVal attrval = null;// 类目属性
			String dispat = (String) entry.getKey();
			if (!dispat.equals("") && dispat != "") {
				try {
					// 缓存中拿类目属性
					attrval = (CatalogAttrVal) CacheFactory.getCacheOper("pb_").get(dispat);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				if (null == attrval) {
					// 查库操作
					JedisCateAndAttrByOne.startCaches("pb_" + dispat);
					try {
						attrval = (CatalogAttrVal) CacheFactory.getCacheOper("pb_").get(dispat);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				CatalogAttr attr = new CatalogAttr();
				attr.setName(attrval.getImgUrl());
				attr.setSortval(10);
				LinkedHashSet<CatalogAttrVal> linkedHashSet = termMap.get(attr);
				if (null == linkedHashSet) {
					attrval.setImgUrl(null);
					linkedHashSet = new LinkedHashSet<CatalogAttrVal>();
					linkedHashSet.add(attrval);
					termMap.put(attr, linkedHashSet);
				} else {
					attrval.setImgUrl(null);
					linkedHashSet.add(attrval);
					termMap.put(attr, linkedHashSet);
				}
			}
		}
	}

	// 清掉过滤属性
	private static void doFilter(LinkedHashMap<CatalogAttr, LinkedHashSet<CatalogAttrVal>> termMap,
			SearchRequest searchRequest) {
		CatalogAttr catalogAttr_ = null;
		if (StringUtils.isNotEmpty(searchRequest.getCyid())) {
			Set<CatalogAttr> catelogattr = termMap.keySet();
			for (CatalogAttr catalogAttr : catelogattr) {
				if (catalogAttr.getName().equals(Constant.STR_COUNTRY_ORIGIN)) {
					catalogAttr_ = catalogAttr;
				}
			}

			termMap.remove(catalogAttr_);
		}
		if (StringUtils.isNotEmpty(searchRequest.getSupplierId())) {
			Set<CatalogAttr> catelogattr = termMap.keySet();
			for (CatalogAttr catalogAttr : catelogattr) {
				if (catalogAttr.getName().equals(Constant.STR_SUPPLIER_ORIGIN)) {
					catalogAttr_ = catalogAttr;
				}
			}

			termMap.remove(catalogAttr_);
		}
		if (StringUtils.isNotEmpty(searchRequest.getBrandName())) {
			Set<CatalogAttr> catelogattr = termMap.keySet();
			for (CatalogAttr catalogAttr : catelogattr) {
				if (catalogAttr.getName().equals(Constant.STR_BRANDNAME)) {
					catalogAttr_ = catalogAttr;
				}
			}

			termMap.remove(catalogAttr_);
		}
		if (StringUtils.isNotEmpty(searchRequest.getPriceRange())) {
			Set<CatalogAttr> catelogattr = termMap.keySet();
			for (CatalogAttr catalogAttr : catelogattr) {
				if (catalogAttr.getName().equals(Constant.STR_PRICE)) {
					catalogAttr_ = catalogAttr;
				}
			}

			termMap.remove(catalogAttr_);
		}

	}

	/**
	 * 选中属性
	 *
	 * @param searchRequest
	 * @return
	 */
	public static LinkedList<AttrTerm> attrTerm(SearchRequest searchRequest) {
		LinkedList<AttrTerm> terms = new LinkedList<AttrTerm>();

		if (StringUtils.isNotEmpty(searchRequest.getBrandName())) {
			LinkedList<AttrTerm> terms_Bn = new LinkedList<AttrTerm>();
			if (searchRequest.getBrandName().contains(",")) {
				String[] brandNameArr = searchRequest.getBrandName().split(",");
				for (int i = 0; i < brandNameArr.length; i++) {
					AttrTerm attrterm = new AttrTerm();
					attrterm.setBrandName(true);
					attrterm.setAttrName(Constant.STR_BRANDNAME); // 品牌
					attrterm.setAttrValName(brandNameArr[i]);
					attrterm.setId(searchRequest.getBrandName());
					terms_Bn.addFirst(attrterm);
				}
			} else {
				AttrTerm attrterm = new AttrTerm();
				attrterm.setBrandName(true);
				attrterm.setAttrName(Constant.STR_BRANDNAME);// 品牌
				attrterm.setAttrValName(searchRequest.getBrandName());
				attrterm.setId(searchRequest.getBrandName());
				terms_Bn.addFirst(attrterm);
			}
			terms.addFirst(buildAttrTerm(terms_Bn));
		}
		if (StringUtils.isNotEmpty(searchRequest.getB2csupply())) {
			AttrTerm attrterm = new AttrTerm();
			attrterm.setB2csupply(true);
			attrterm.setAttrName(searchRequest.getB2csupply());
			terms.addFirst(attrterm);
		}
		if (StringUtils.isNotEmpty(searchRequest.getCyid())) {
			LinkedList<AttrTerm> terms_Cy = new LinkedList<AttrTerm>();
			if (searchRequest.getCyid().contains(",")) {
				String[] cityArr = searchRequest.getCyid().split(",");
				for (int i = 0; i < cityArr.length; i++) {
					AttrTerm attrterm = new AttrTerm();
					attrterm.setCyid(true);
					attrterm.setAttrName(Constant.STR_COUNTRY_ORIGIN);
					attrterm.setAttrValName(cityArr[i]);
					attrterm.setId(searchRequest.getCyid());
					terms_Cy.addFirst(attrterm);
				}
			} else {
				AttrTerm attrterm = new AttrTerm();
				attrterm.setCyid(true);
				attrterm.setAttrName(Constant.STR_COUNTRY_ORIGIN); // 原产国
				attrterm.setAttrValName(searchRequest.getCyid());
				attrterm.setId(searchRequest.getCyid());
				terms_Cy.addFirst(attrterm);
			}
			terms.addFirst(buildAttrTerm(terms_Cy));
		}
		// 企业
		if (StringUtils.isNotEmpty(searchRequest.getSupplierId())) {
			LinkedList<AttrTerm> terms_Cy = new LinkedList<AttrTerm>();
			if (searchRequest.getSupplierId().contains(",")) {
				String[] cityArr = searchRequest.getSupplierId().split(",");
				for (int i = 0; i < cityArr.length; i++) {
					AttrTerm attrterm = new AttrTerm();
					attrterm.setSupplierId(true);
					attrterm.setAttrName(Constant.STR_SUPPLIER_ORIGIN);
					attrterm.setAttrValName(cityArr[i]);
					attrterm.setId(searchRequest.getSupplierId());
					terms_Cy.addFirst(attrterm);
				}
			} else {
				AttrTerm attrterm = new AttrTerm();
				attrterm.setSupplierId(true);
				attrterm.setAttrName(Constant.STR_COUNTRY_ORIGIN); // 企业
				attrterm.setAttrValName(searchRequest.getSupplierId());
				attrterm.setId(searchRequest.getSupplierId());
				terms_Cy.addFirst(attrterm);
			}
			terms.addFirst(buildAttrTerm(terms_Cy));
		}
		if (StringUtils.isNotEmpty(searchRequest.getPriceRange())) {
			LinkedList<AttrTerm> terms_Pr = new LinkedList<AttrTerm>();
			AttrTerm attrterm = new AttrTerm();
			attrterm.setPrice(true);
			attrterm.setAttrName(Constant.STR_PRICE); // 价格
			attrterm.setAttrValName(searchRequest.getPriceRange());
			attrterm.setId(searchRequest.getPriceRange());
			terms_Pr.addFirst(attrterm);

			terms.addFirst(buildAttrTerm(terms_Pr));
		}

		return terms;
	}

	// 处理多选（品牌、原产国、企业）
	private static AttrTerm buildAttrTerm(LinkedList<AttrTerm> terms) {
		StringBuilder sbId = new StringBuilder();
		StringBuilder sbName = new StringBuilder();
		String attrName = null;
		boolean brandName = false, cyid = false, price = false, inventory = false, b2csupply = false,
				supplierId = false;

		for (AttrTerm attrTerm : terms) {
			attrName = attrTerm.getAttrName();
			brandName = attrTerm.isBrandName();
			cyid = attrTerm.isCyid();
			price = attrTerm.isPrice();
			inventory = attrTerm.isInventory();
			b2csupply = attrTerm.isB2csupply();
			supplierId = attrTerm.isSupplierId();
			sbId.append(attrTerm.getId() + ",");
			sbName.append(attrTerm.getAttrValName() + ",");
		}

		AttrTerm attrTerm = new AttrTerm();
		// String id=null;
		String name = null;
		/*
		 * if(sbId.toString()!=null&&!"".equals(sbId.toString())){ id
		 * =sbId.toString().substring(0, sbId.length()-1); }
		 */

		if (sbName.toString() != null && !"".equals(sbName.toString())) {
			name = sbName.toString().substring(0, sbName.length() - 1);
		}
		attrTerm.setId(name);
		attrTerm.setAttrValName(name);
		attrTerm.setAttrName(attrName);
		attrTerm.setBrandName(brandName);
		attrTerm.setCyid(cyid);
		attrTerm.setPrice(price);
		attrTerm.setInventory(inventory);
		attrTerm.setB2csupply(b2csupply);
		attrTerm.setSupplierId(supplierId);

		return attrTerm;
	}

	/**
	 * 接收web端渠道，分析渠道对应的索引值
	 * 
	 * @param searchRequest
	 * @return
	 */
	public static String getWayPrice(SearchRequest searchRequest) {
		String sortTypeStr = "";
		if (searchRequest.getEntryType().equals(Constant.CHANNEL_WAP)) {
			sortTypeStr = Constant.PROMOTION_PRICE_WAP;
		} else if (searchRequest.getEntryType().equals(Constant.CHANNEL_PC)) {
			sortTypeStr = Constant.PROMOTION_PRICE_PC;
		} else if (searchRequest.getEntryType().equals(Constant.CHANNEL_APP)) {
			sortTypeStr = Constant.PROMOTION_PRICE_APP;
		} else if (searchRequest.getEntryType().equals(Constant.CHANNEL_YUEME)) {
			sortTypeStr = Constant.PROMOTION_PRICE_YM;
		} else if (searchRequest.getEntryType().equals(Constant.CHANNEL_BOC_BEIFEN)) {
			sortTypeStr = Constant.PROMOTION_PRICE_BF;
		} else if (searchRequest.getEntryType().equals(Constant.CHANNEL_BESTPAY)) {
			sortTypeStr = Constant.PROMOTION_PRICE_BESTPAY;
		} else if (searchRequest.getEntryType().equals(Constant.CHANNEL_ZHANGJU)) {
			sortTypeStr = Constant.PROMOTION_PRICE_ZJ;
		} else if (searchRequest.getEntryType().equals(Constant.CHANNEL_BOC_NINGBO)) {
			sortTypeStr = Constant.PROMOTION_PRICE_NB;
		} else {
			sortTypeStr = Constant.FIELD_UNIT_PRICE;
		}

		return sortTypeStr;

	}

	/**
	 * 接收web端渠道，促销价格
	 * 
	 * @param searchRequest
	 * @return
	 */
	public static String getPromotionPrice(SearchRequest searchRequest) {
		String sortTypePrice = "";
		if (searchRequest.getEntryType().equals(Constant.CHANNEL_WAP)) {
			sortTypePrice = Constant.PRICE_WAP;
		} else if (searchRequest.getEntryType().equals(Constant.CHANNEL_PC)) {
			sortTypePrice = Constant.PRICE_PC;
		} else if (searchRequest.getEntryType().equals(Constant.CHANNEL_APP)) {
			sortTypePrice = Constant.PRICE_APP;
		} else if (searchRequest.getEntryType().equals(Constant.CHANNEL_YUEME)) {
			sortTypePrice = Constant.PRICE_YM;
		} else if (searchRequest.getEntryType().equals(Constant.CHANNEL_BOC_BEIFEN)) {
			sortTypePrice = Constant.PRICE_BF;
		} else if (searchRequest.getEntryType().equals(Constant.CHANNEL_BESTPAY)) {
			sortTypePrice = Constant.PRICE_BESTPAY;
		} else if (searchRequest.getEntryType().equals(Constant.CHANNEL_ZHANGJU)) {
			sortTypePrice = Constant.PRICE_ZJ;
		} else if (searchRequest.getEntryType().equals(Constant.CHANNEL_BOC_NINGBO)) {
			sortTypePrice = Constant.PRICE_NB;
		} else {
			sortTypePrice = Constant.FIELD_UNIT_PRICE;
		}
		return sortTypePrice;

	}
}
