package com.mall.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Multimap;
import com.mall.common.ConstantManage;
import com.mall.dao.AttrDao;
import com.mall.dao.DealerProInfoDao;
import com.mall.dao.impl.AttrDaoImpl;
import com.mall.dao.impl.DealerProInfoDaoImpl;

import com.mall.dealer.product.po.ProductTags;
import com.mall.po.AttrCode;
import com.mall.po.BrandName;
import com.mall.po.CashRatio;
import com.mall.po.DealerProInfo;
import com.mall.po.DealerProInfoBase;
import com.mall.po.DealerProInfoBaseC;
import com.mall.po.DealerProInfoSaleSetting;
import com.mall.po.DealerProInfoSku;
import com.mall.po.Family;
import com.mall.po.ProInfoMain;
import com.mall.po.ProductDetailB2c;
import com.mall.po.SupplierInfo;
import com.mall.po.TagName;
/*import com.mall.promotion.dto.activity.ProductPromotionDto;
import com.mall.promotion.dto.activity.PromotionPriceDto;
import com.mall.promotion.dto.ruleSkuDto.SkuConditionB2BDto;*/
import com.mall.service.DealerProInfoService;
import com.mall.util.RemoteServiceSingleton;
import com.mall.util.StringUtils;

/**
 * 数据收集主service
 * 
 */
public class DealerProInfoServiceImpl implements DealerProInfoService {

	private static Logger logger = LoggerFactory.getLogger(DealerProInfoServiceImpl.class);

	// @Autowired
	// private ProductByPromotionInfoService productByPromotionInfoService;

	/*
	 * @Autowired private ProductTagsService ProductTagsService;
	 */

	public List<DealerProInfo> getDealerProInfo(String pid) {
		DealerProInfoDao dealerProInfoDao = new DealerProInfoDaoImpl();
		AttrDao attrDao = new AttrDaoImpl();
		
		List<DealerProInfo> list = new ArrayList<DealerProInfo>();
		
		try {
			long s = System.currentTimeMillis();
			int i = 0;
			List<ProInfoMain> proInfos = dealerProInfoDao.getDealerProInfoMain(ConstantManage.DBNAME); // 主表
			logger.info(i++ + ":" + (System.currentTimeMillis() - s));
			s = System.currentTimeMillis();
			logger.info(i++ + ":" + (System.currentTimeMillis() - s));
			s = System.currentTimeMillis();
			// 获取供应商信息
			Map<String, SupplierInfo> supplierInfos = dealerProInfoDao.getSupplierInfo(ConstantManage.DBNAME4);
			logger.info(i++ + ":" + (System.currentTimeMillis() - s));
			s = System.currentTimeMillis();
			// 获取商品详描地址
			Map<String, String> proDescUrls = dealerProInfoDao.getDealerProDesc(ConstantManage.DBNAME); // 主表
			logger.info(i++ + ":" + (System.currentTimeMillis() - s));
			s = System.currentTimeMillis();
			
			Map<String, DealerProInfoBase> baseMap_b = dealerProInfoDao.getDealerProInfoBase(ConstantManage.DBNAME); // b2b副表
			logger.info(i++ + ":" + (System.currentTimeMillis() - s));
			s = System.currentTimeMillis();
			Map<String, DealerProInfoBaseC> baseMap_c = dealerProInfoDao.getDealerProInfoBase_c(ConstantManage.DBNAME); // b2c副表
			logger.info(i++ + ":" + (System.currentTimeMillis() - s));
			s = System.currentTimeMillis();
			Multimap<String, DealerProInfoSku> skuMap = dealerProInfoDao.getDealerProInfoSku(ConstantManage.DBNAME); // sku信息表
			logger.info(i++ + ":" + (System.currentTimeMillis() - s));
			s = System.currentTimeMillis();
			Map<String, DealerProInfoSaleSetting> saleSettingMap = dealerProInfoDao
					.getDealerProInfoSaleSetting(ConstantManage.DBNAME); // 销售信息
			logger.info(i++ + ":" + (System.currentTimeMillis() - s));
			s = System.currentTimeMillis();
			Map<String, BrandName> brandNameMap = dealerProInfoDao.getBrandName(ConstantManage.DBNAME2);// 品牌
			logger.info(i++ + ":" + (System.currentTimeMillis() - s));
			s = System.currentTimeMillis();
			Map<String, BrandName> cyMap = dealerProInfoDao.getCyid(ConstantManage.DBNAME2);
			logger.info(i++ + ":" + (System.currentTimeMillis() - s));
			s = System.currentTimeMillis();
			Map<String, ProductDetailB2c> pdb2cMap = dealerProInfoDao.getB2cName(ConstantManage.DBNAME);
			logger.info(i++ + ":" + (System.currentTimeMillis() - s));
			s = System.currentTimeMillis();
			Map<String, BrandName> MoneyUnitMap = dealerProInfoDao.getMoneyUnit(ConstantManage.DBNAME2);
			logger.info(i++ + ":" + (System.currentTimeMillis() - s));
			s = System.currentTimeMillis();
			// 获取商品b2c价格
			Map<String, BrandName> b2cPriceMap = dealerProInfoDao.getB2cPrice(ConstantManage.DBNAME);
			logger.info(i++ + ":" + (System.currentTimeMillis() - s));
			s = System.currentTimeMillis();
			// b2b展示类目、发布类目
			Map<String, List<BrandName>> b2bCateDispMap = dealerProInfoDao.getb2bCateDispId(ConstantManage.DBNAME2);
			logger.info(i++ + ":" + (System.currentTimeMillis() - s));
			s = System.currentTimeMillis();
			// b2c展示类目、发布类目
			Map<String, List<BrandName>> b2cCateDispMap = dealerProInfoDao.getb2cCateDispId(ConstantManage.DBNAME2);
			logger.info(i++ + ":" + (System.currentTimeMillis() - s));
			s = System.currentTimeMillis();
			// 库存信息
			Map<String, BrandName> stockMap = dealerProInfoDao.getStock(ConstantManage.DBNAME3);
			logger.info(i++ + ":" + (System.currentTimeMillis() - s));
			s = System.currentTimeMillis();
			Map<String, List<AttrCode>> attrMap = attrDao.getNames(ConstantManage.DBNAME);
			logger.info(i++ + ":" + (System.currentTimeMillis() - s));
			s = System.currentTimeMillis();
			Map<String, AttrCode> attrcodeMap = attrDao.getAttrCode(ConstantManage.DBNAME2);
			logger.info(i++ + ":" + (System.currentTimeMillis() - s));
			s = System.currentTimeMillis();
			Multimap<String, String> crossLink = dealerProInfoDao.getCrossLinkCdid(ConstantManage.DBNAME2);

			logger.info(i++ + ":" + (System.currentTimeMillis() - s));
			s = System.currentTimeMillis();
			// 销售量
			Map<String, Integer> productSale = dealerProInfoDao.getProSales(ConstantManage.DBNAME3);
			logger.info(i++ + ":" + (System.currentTimeMillis() - s));
			s = System.currentTimeMillis();

			// 促销价格（pc、wap、app）获取sku 对应 price集合
			Collection<BrandName> skuids = b2cPriceMap.values();
			List<Long> skuids_ = new ArrayList<Long>();
			for (BrandName brandName : skuids) {
				skuids_.add(brandName.getSkuid());
			}
			logger.info("skuids " + skuids_.size());
			// Map<Long, PromotionPriceDto> wapSkuPriceMAP =
			// getSkuPriceMap(skuids_,ConstantManage.CHANNEL_WAP);
			// Map<Long, PromotionPriceDto> pcSkuPriceMAP =
			// getSkuPriceMap(skuids_,ConstantManage.CHANNEL_PC);
			// Map<Long, PromotionPriceDto> appSkuPriceMAP =
			// getSkuPriceMap(skuids_,ConstantManage.CHANNEL_APP);
			// Map<Long, ProductPromotionDto> bestpaySkuPriceMAP =
			// getSkuPriceMapBeast(baseMap_c,proInfos,skuMap,skuids_,ConstantManage.CHANNEL_BESTPAY);
			// Map<Long, PromotionPriceDto> zjSkuPriceMAP =
			// getSkuPriceMap(skuids_,ConstantManage.CHANNEL_ZHANGJU);
			// Map<Long, PromotionPriceDto> ymSkuPriceMAP =
			// getSkuPriceMap(skuids_,ConstantManage.CHANNEL_YUEME);
			// Map<Long, PromotionPriceDto> bfSkuPriceMAP =
			// getSkuPriceMap(skuids_,ConstantManage.CHANNEL_BOC_BEIFEN);
			// Map<Long, PromotionPriceDto> nbSkuPriceMAP =
			// getSkuPriceMap(skuids_,ConstantManage.CHANNEL_BOC_NINGBO);
			logger.info(i++ + "促销接口:" + (System.currentTimeMillis() - s));
			s = System.currentTimeMillis();
			logger.info("select db end:");
			s = System.currentTimeMillis();
			if (proInfos != null) {
				for (ProInfoMain proInfoMain : proInfos) {
					// pid 现实中 不为 没有意义的值
					if (null != proInfoMain.getProductid() && proInfoMain.getProductid().equals("0")) {
						continue;
					}

					DealerProInfo dealerProInfo = new DealerProInfo();
					String productid = proInfoMain.getProductid();
					if (StringUtils.isNotEmpty(proInfoMain.getSupplierid())) {
						SupplierInfo supplierInfo = supplierInfos.get(proInfoMain.getSupplierid());
						if(supplierInfo != null){
						dealerProInfo.setSupplier_code(
								supplierInfo.getSupplierCode() == null ? "" : supplierInfo.getSupplierCode().trim());// 公司代码
						dealerProInfo
								.setSupplier_name(supplierInfo.getName() == null ? "" : supplierInfo.getName().trim());// 公司名称
						dealerProInfo.setSupplier_name_jc(supplierInfo.getCompanySimpleName() == null ? ""
								: supplierInfo.getCompanySimpleName().trim());// 公司简称
						dealerProInfo.setSupplier_kf_tel(supplierInfo.getCustomerServerTele() == null ? ""
								: supplierInfo.getCustomerServerTele().trim());// 客服电话
						dealerProInfo.setCompany_region(
								supplierInfo.getSettledArea() == null ? "" : supplierInfo.getSettledArea().trim());// 入驻区域
						dealerProInfo.setSupplier_logo_imgurl(supplierInfo.getSupplierLogoImgurl() == null ? ""
								: supplierInfo.getSupplierLogoImgurl().trim()); // 企业logo
					   }else{
						   continue;
					   }
					}
					/*
					 * if(taglist != null ){
					 * 
					 * for (TagName tag : taglist) {
					 * dealerProInfo.setTagName(tag.getTagName());
					 * dealerProInfo.setPid(tag.getProductId()); }
					 * 
					 * 
					 * }
					 */

					/**
					 * b2cCateDispMap b2c展示类目 发布类目关联信息
					 */
					mergeProductInfo(dealerProInfo, proInfoMain, brandNameMap, cyMap, pdb2cMap, MoneyUnitMap,
							b2cPriceMap, b2bCateDispMap, b2cCateDispMap, crossLink, productSale, proDescUrls);
					mergeAttrCode(dealerProInfo, attrMap, attrcodeMap, ConstantManage.DBNAME2);
					
					// 合并商品副表
					if (baseMap_b.get(productid) != null) {
						mergeProductInfoBase_b(dealerProInfo, baseMap_b.get(productid));
					}
					if (baseMap_c.get(productid) != null) {
						mergeProductInfoBase_c(dealerProInfo, baseMap_c.get(productid));
					}
					if (skuMap != null) {
						mergeProductInfoSku(dealerProInfo, skuMap.get(productid), productid, stockMap);
					}
					if (saleSettingMap.get(productid) != null) {
						mergeProductInfoSaleSetting(dealerProInfo, saleSettingMap.get(productid));
					}
					
					dealerProInfo.setPromotion("0");
					
					List<TagName> taglist = dealerProInfoDao.getTag(ConstantManage.DBNAME, productid);
					
					if (taglist != null) {
						List<String> newListv = new ArrayList<String>();
						for (TagName tag : taglist) {
							newListv.add(tag.getTagName());
						}
						dealerProInfo.setTagName(newListv);
					}
					
					List<CashRatio> cashList = dealerProInfoDao.getCash(ConstantManage.DBNAME, productid);
					if (cashList != null) {
						List<String> cashListnew = new ArrayList<>();
						for (CashRatio cashRatio : cashList) {
							cashListnew.add(cashRatio.getCashName());
						}
						dealerProInfo.setCash(cashListnew);
					}
					
					List<Family> fam = dealerProInfoDao.getFam(ConstantManage.DBNAME, productid);
					if (fam != null) {
						List<String> famListnew = new ArrayList<>();
						for (Family family : fam) {
							famListnew.add(family.getProdType());
						}
						dealerProInfo.setFamily(famListnew);
					}
					
					List<Family> normal = dealerProInfoDao.getNormal(ConstantManage.DBNAME, productid);
					if (normal != null) {
						List<String> normalListnew = new ArrayList<>();
						for (Family nor : normal) {
							normalListnew.add(nor.getProdType());
						}
						dealerProInfo.setNormal(normalListnew);
					}
					
					List<Family> getjth = dealerProInfoDao.getjth(ConstantManage.DBNAME, productid);
					if (getjth != null) {
						String iconStr = "";
						List<String> iconListnew = new ArrayList<>();
						List<String> jthListnew = new ArrayList<>();
						for (Family jth : getjth) {
							if (jth.getProdType() != null && jth.getProdType().equalsIgnoreCase("jhb")) {
								iconStr = "激活";
								iconListnew.add(iconStr);
							}
							jthListnew.add(jth.getProdType());
						}
						dealerProInfo.setProductType_code(jthListnew);
						dealerProInfo.setProduct_iconsTxT(iconListnew);
					}
					list.add(dealerProInfo);
				}
			}
			
			logger.info("create product time:" + (System.currentTimeMillis() - s));
		} catch (Exception e) {
			logger.error("getDealerProInfo.Exception:", e);
		} finally {
		}
		logger.info("is ok-----------------------------------------------------------------------");
		return list;
	}

	/**
	 * 合并产品基本信息
	 * 
	 * @param dealerProInfo
	 * @param proInfoMain
	 * @throws Exception
	 */
	/*
	 * private void mergeProductInfo(DealerProInfo dealerProInfo,ProInfoMain
	 * proInfoMain,Map<String,BrandName> brandNameMap, Map<String,BrandName>
	 * cyMap,Map<String,ProductDetailB2c>
	 * productDetailB2cMap,Map<String,BrandName> MoneyUnitMap,
	 * Map<String,BrandName> B2cPriceMap,Map<String, List<BrandName>>
	 * b2bNameMap,Map<String, List<BrandName>> b2cNameMap,
	 * Multimap<String,String> crossLink,Map<String,Integer>
	 * productSale,Map<Long, PromotionPriceDto> wapPromationPriceMap, Map<Long,
	 * PromotionPriceDto> pcPromationPriceMap,Map<Long, PromotionPriceDto>
	 * appPromationPriceMap, Map<Long, ProductPromotionDto>
	 * bestpaySkuPriceMap,Map<Long, PromotionPriceDto> zjSkuPriceMap, Map<Long,
	 * PromotionPriceDto> ymSkuPriceMap,Map<Long, PromotionPriceDto>
	 * bfSkuPriceMap, Map<Long, PromotionPriceDto>
	 * nbSkuPriceMap,Map<String,String> proDescUrls) throws Exception{
	 * 
	 * List<String> b2bCdids = new ArrayList<String>();//b2b展示类目id集合
	 * List<String> b2cCdids = new ArrayList<String>();//b2c展示类目id集合
	 * List<String> b2bCdidNames = new ArrayList<String>();//b2b展示类目id集合
	 * List<String> b2cCdidNames = new ArrayList<String>();//b2c展示类目id集合
	 * SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 * dealerProInfo.setPid(proInfoMain.getProductid());
	 * 
	 * //加入商品详描proDescUrls String prodDescUrl =
	 * proDescUrls.get(proInfoMain.getProductid()); if( prodDescUrl!=null &&
	 * prodDescUrl.length()>10 ){
	 * dealerProInfo.setB2cProdDesc(FileLoad.get(prodDescUrl)); }
	 * dealerProInfo.setB2cProdDesc("");
	 * 
	 * if(null != cyMap){ BrandName cyName =
	 * cyMap.get(proInfoMain.getCountry_id()); if(null != cyName){
	 * dealerProInfo.setCyid(cyName.getBrandName());
	 * dealerProInfo.setCountryImg(cyName.getSubBrandName());
	 * dealerProInfo.setCyName(cyName.getBrandId()); } } if (null !=
	 * productDetailB2cMap) { ProductDetailB2c pdB2c =
	 * productDetailB2cMap.get(proInfoMain.getProductid()); if(null != pdB2c){
	 * dealerProInfo.setB2c_pname(pdB2c.getB2cProductName());
	 * dealerProInfo.setBondedZone(pdB2c.getOrigin_Country());
	 * dealerProInfo.setB2cSupply(Integer.parseInt(pdB2c.getB2csupply())); if
	 * (null != MoneyUnitMap) { BrandName MoneyUnit =
	 * MoneyUnitMap.get(pdB2c.getB2cMoneyUnitId());
	 * dealerProInfo.setMoneySymbols(MoneyUnit.getBrandName());
	 * dealerProInfo.setMoneyRate(MoneyUnit.getSubBrandName()); } } } if (null
	 * != B2cPriceMap) { BrandName b2cPrice =
	 * B2cPriceMap.get(proInfoMain.getProductid()); if(null != b2cPrice){
	 * dealerProInfo.setUnit_price(Float.parseFloat(b2cPrice.getBrandName()));
	 * dealerProInfo.setDomestic_price(Float.parseFloat(b2cPrice.getSubBrandName
	 * ()));
	 * dealerProInfo.setDealer_price(Float.parseFloat(b2cPrice.getDealer_price()
	 * ));
	 * dealerProInfo.setRetail_price(Float.parseFloat(b2cPrice.getRetail_price()
	 * )); //现金比例
	 * 
	 * ProductTags tags =
	 * RemoteServiceSingleton.getInstance().getProductTagsService().
	 * LimitContentOrNull(Long.parseLong(proInfoMain.getProductid()), "xjzfbl",
	 * 5); if(tags==null){ if(b2cPrice.getCashHqj()!=null){
	 * dealerProInfo.setCashHqj(Float.parseFloat(b2cPrice.getCashHqj())); }
	 * dealerProInfo.setProd_type(b2cPrice.getProdType()); BigDecimal b1 = new
	 * BigDecimal(Float.toString(Float.parseFloat(b2cPrice.getBrandName())));
	 * BigDecimal b2 =new BigDecimal("0"); BigDecimal b3 =new BigDecimal("100");
	 * BigDecimal multiply =new BigDecimal("0");
	 * if(b2cPrice.getCashHqj()!=null){ b2 = new
	 * BigDecimal(Float.toString(Float.parseFloat(b2cPrice.getCashHqj())));
	 * BigDecimal discount = b2.divide(b1,2, RoundingMode.HALF_UP); multiply =
	 * discount.multiply(b3); } double ceil =
	 * Math.ceil(b1.subtract(b2).doubleValue());
	 * 
	 * dealerProInfo.setHqj((float)ceil);
	 * 
	 * 
	 * dealerProInfo.setDiscount(multiply.floatValue()); } if(tags!=null){
	 * dealerProInfo.setProd_type(b2cPrice.getProdType()); BigDecimal b2 =new
	 * BigDecimal("0"); BigDecimal b1 = new
	 * BigDecimal(Float.toString(Float.parseFloat(b2cPrice.getBrandName())));
	 * BigDecimal b4 =new BigDecimal("10"); BigDecimal b3 = new
	 * BigDecimal(Float.toString(Float.parseFloat("100"))); BigDecimal divide2 =
	 * new BigDecimal("0"); if(b2cPrice.getCashHqj()!=null){ b2 =new
	 * BigDecimal(b2cPrice.getCashHqj()); divide2 = b2; }
	 * 
	 * BigDecimal bigDecimal = b3.subtract(b2);
	 * 
	 * BigDecimal divide = bigDecimal.divide(b3); BigDecimal multiply =
	 * b1.multiply(divide); BigDecimal hqj = multiply.setScale(0,
	 * BigDecimal.ROUND_HALF_UP); BigDecimal subtract = b1.subtract(hqj);
	 * BigDecimal setScale = subtract.setScale(2);
	 * dealerProInfo.setHqj(hqj.floatValue()); BigDecimal cc = cash.setScale(2,
	 * 4); dealerProInfo.setCashHqj(setScale.floatValue());
	 * 
	 * 
	 * dealerProInfo.setDiscount(divide2.floatValue());
	 * 
	 * }
	 * 
	 * 
	 * //设置商品翼支付价格
	 * dealerProInfo.setBestoay_price(Float.parseFloat(b2cPrice.getDefiningValue
	 * ()));
	 * 
	 * }
	 * 
	 * //List<Map<String,String>> promotionPrices = new
	 * ArrayList<Map<String,String>>(); Map<String,Float> ppMap = new
	 * HashMap<String,Float>(); if(null!=wapPromationPriceMap){
	 * PromotionPriceDto wapPrice=
	 * wapPromationPriceMap.get(b2cPrice.getSkuid()); ppMap.put("wapPrice",
	 * dealerProInfo.getUnit_price()-wapPrice.getDiscountPrice().floatValue());
	 * //dealerProInfo.setWap_unit_price(dealerProInfo.getUnit_price()-wapPrice.
	 * getDiscountPrice().floatValue()); } if(null!=pcPromationPriceMap){
	 * PromotionPriceDto pcPrice= pcPromationPriceMap.get(b2cPrice.getSkuid());
	 * ppMap.put("pcPrice",
	 * dealerProInfo.getUnit_price()-pcPrice.getDiscountPrice().floatValue());
	 * //dealerProInfo.setPc_unit_price(dealerProInfo.getUnit_price()-pcPrice.
	 * getDiscountPrice().floatValue()); } if(null!=appPromationPriceMap){
	 * PromotionPriceDto appPrice=
	 * appPromationPriceMap.get(b2cPrice.getSkuid()); ppMap.put("appPrice",
	 * dealerProInfo.getUnit_price()-appPrice.getDiscountPrice().floatValue());
	 * //dealerProInfo.setApp_unit_price(dealerProInfo.getUnit_price()-appPrice.
	 * getDiscountPrice().floatValue()); } //翼支付减去折扣后的价格
	 * if(null!=bestpaySkuPriceMap){ ProductPromotionDto bestpayPrice=
	 * bestpaySkuPriceMap.get(b2cPrice.getSkuid()); if(bestpayPrice != null){
	 * //logger.info(b2cPrice.getSkuid()+","+dealerProInfo.getBestoay_price()+
	 * ","+bestpayPrice.getStraightDownPrice().floatValue()); //翼支付折扣后的价格
	 * ppMap.put("bestpayPrice",
	 * dealerProInfo.getBestoay_price()-bestpayPrice.getStraightDownPrice().
	 * floatValue()); }else{ ppMap.put("bestpayPrice",
	 * dealerProInfo.getBestoay_price()); }
	 * 
	 * } if(null!=zjSkuPriceMap){ PromotionPriceDto zjPrice=
	 * zjSkuPriceMap.get(b2cPrice.getSkuid()); ppMap.put("zjPrice",
	 * dealerProInfo.getUnit_price()-zjPrice.getDiscountPrice().floatValue()); }
	 * if(null!=ymSkuPriceMap){ PromotionPriceDto ymPrice=
	 * ymSkuPriceMap.get(b2cPrice.getSkuid()); ppMap.put("ymPrice",
	 * dealerProInfo.getUnit_price()-ymPrice.getDiscountPrice().floatValue()); }
	 * if(null!=bfSkuPriceMap){ PromotionPriceDto bfPrice=
	 * bfSkuPriceMap.get(b2cPrice.getSkuid()); ppMap.put("bfPrice",
	 * dealerProInfo.getUnit_price()-bfPrice.getDiscountPrice().floatValue()); }
	 * if(null!=nbSkuPriceMap){ PromotionPriceDto nbPrice=
	 * nbSkuPriceMap.get(b2cPrice.getSkuid()); ppMap.put("nbPrice",
	 * dealerProInfo.getUnit_price()-nbPrice.getDiscountPrice().floatValue()); }
	 * 
	 * dealerProInfo.setPromotion_price(ppMap); }
	 * 
	 * dealerProInfo.setSid(proInfoMain.getSupplierid());
	 * dealerProInfo.setB2b_istate(Integer.parseInt(proInfoMain.getB2b_istate())
	 * );
	 * dealerProInfo.setB2c_istate(Integer.parseInt(proInfoMain.getB2c_istate())
	 * ); dealerProInfo.setImageAlt(proInfoMain.getImageAlt());
	 * dealerProInfo.setCpid(proInfoMain.getCate_pub_id());
	 * 
	 * 
	 * if(null != b2bNameMap){ List<BrandName> b2bNameList =
	 * b2bNameMap.get(proInfoMain.getCate_pub_id());
	 * if(b2bNameList!=null&&b2bNameList.size()>0){ for (BrandName param_b :
	 * b2bNameList) { b2bCdids.addAll(handingCdids(param_b.getBrandName()));
	 * b2bCdidNames.addAll(handingCdidNames(param_b.getBrandName(),"b2b")); }
	 * dealerProInfo.setB2b_cdids(b2bCdids);
	 * dealerProInfo.setB2b_cdidNames(b2bCdidNames); }else{
	 * dealerProInfo.setB2b_cdids(null); dealerProInfo.setB2b_cdidNames(null); }
	 * } //b2c展示类目ID、名称处理 if(null != b2cNameMap){ //通过发布类目得到展示类目列表
	 * List<BrandName> b2cNameList =
	 * b2cNameMap.get(proInfoMain.getCate_pub_id());
	 * if(b2cNameList!=null&&b2cNameList.size()>0){ for (BrandName param_c :
	 * b2cNameList) { b2cCdids.addAll(handingCdids(param_c.getBrandName()));
	 * b2cCdidNames.addAll(handingCdidNames(param_c.getBrandName(),"b2c")); }
	 * dealerProInfo.setB2c_cdids(b2cCdids);
	 * dealerProInfo.setB2c_cdidNames(b2cCdidNames); }else{
	 * dealerProInfo.setB2c_cdids(null); dealerProInfo.setB2c_cdidNames(null); }
	 * }
	 * 
	 * Collection<String> pubIds =crossLink.get(proInfoMain.getCate_pub_id());
	 * if(pubIds!=null){ for(String id:pubIds){
	 * b2cCdids.addAll(handingCdids(id)); }
	 * dealerProInfo.setB2c_cdids(b2cCdids); }
	 * 
	 * dealerProInfo.setLineid(proInfoMain.getProd_line_id());
	 * 
	 * //商品销量
	 * 
	 * Integer proQty = productSale.get(proInfoMain.getProductid());
	 * if(null!=proQty){ dealerProInfo.setSalesVolume(proQty.intValue()); }else{
	 * dealerProInfo.setSalesVolume(0); }
	 * 
	 * //上下架时间问题(存在争议)
	 * dealerProInfo.setB2b_opt(getMillis(sdf.parse(proInfoMain.getOperatetime()
	 * )));
	 * dealerProInfo.setB2c_opt(getMillis(sdf.parse(proInfoMain.getOperatetime()
	 * )));
	 * dealerProInfo.setCt(getMillis(sdf.parse(proInfoMain.getOperatetime())));
	 * //B2B创建时间 dealerProInfo.setImageUrl(proInfoMain.getImageurl());
	 * if(brandNameMap != null){ BrandName brandName =
	 * brandNameMap.get(proInfoMain.getBrand_id()); if(null != brandName){
	 * dealerProInfo.setBrandId(brandName.getBrandId());
	 * dealerProInfo.setBrandName(brandName.getBrandName());
	 * dealerProInfo.setBrandNameFc(brandName.getBrandName()); } }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * }
	 */

	private void mergeProductInfo(DealerProInfo dealerProInfo, ProInfoMain proInfoMain,
			Map<String, BrandName> brandNameMap, Map<String, BrandName> cyMap,
			Map<String, ProductDetailB2c> productDetailB2cMap, Map<String, BrandName> MoneyUnitMap,
			Map<String, BrandName> B2cPriceMap, Map<String, List<BrandName>> b2bNameMap,
			Map<String, List<BrandName>> b2cNameMap, Multimap<String, String> crossLink,
			Map<String, Integer> productSale, Map<String, String> proDescUrls) throws Exception {

		List<String> b2bCdids = new ArrayList<String>();// b2b展示类目id集合
		List<String> b2cCdids = new ArrayList<String>();// b2c展示类目id集合
		List<String> b2bCdidNames = new ArrayList<String>();// b2b展示类目id集合
		List<String> b2cCdidNames = new ArrayList<String>();// b2c展示类目id集合
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dealerProInfo.setPid(proInfoMain.getProductid());

		// 加入商品详描proDescUrls
		/*
		 * String prodDescUrl = proDescUrls.get(proInfoMain.getProductid()); if(
		 * prodDescUrl!=null && prodDescUrl.length()>10 ){
		 * dealerProInfo.setB2cProdDesc(FileLoad.get(prodDescUrl)); }
		 */
		dealerProInfo.setB2cProdDesc("");
		
		if (null != cyMap) {
			BrandName cyName = cyMap.get(proInfoMain.getCountry_id());
			if (null != cyName) {
				dealerProInfo.setCyid(cyName.getBrandName());
				dealerProInfo.setCountryImg(cyName.getSubBrandName());
				dealerProInfo.setCyName(cyName.getBrandId());
			}
		}
		
		if (null != productDetailB2cMap) {
			ProductDetailB2c pdB2c = productDetailB2cMap.get(proInfoMain.getProductid());
			if (null != pdB2c) {
				dealerProInfo.setB2c_pname(pdB2c.getB2cProductName());
				dealerProInfo.setBondedZone(pdB2c.getOrigin_Country());
				dealerProInfo.setB2cSupply(Integer.parseInt(pdB2c.getB2csupply()));
				if (null != MoneyUnitMap) {
					BrandName MoneyUnit = MoneyUnitMap.get(pdB2c.getB2cMoneyUnitId());
					dealerProInfo.setMoneySymbols(MoneyUnit.getBrandName());
					dealerProInfo.setMoneyRate(MoneyUnit.getSubBrandName());
				}
			}
		}
		
		if (null != B2cPriceMap) {
			BrandName b2cPrice = B2cPriceMap.get(proInfoMain.getProductid());
			if (null != b2cPrice) {
				dealerProInfo.setUnit_price(Float.parseFloat(b2cPrice.getBrandName()));
				dealerProInfo.setDomestic_price(Float.parseFloat(b2cPrice.getSubBrandName()));
				dealerProInfo.setDealer_price(Float.parseFloat(b2cPrice.getDealer_price()));
				dealerProInfo.setRetail_price(Float.parseFloat(b2cPrice.getRetail_price()));
				// 现金比例
				ProductTags tags = RemoteServiceSingleton.getInstance().getProductTagsService()
						.LimitContentOrNull(Long.parseLong(proInfoMain.getProductid()), "xjzfbl", 5);
				if (tags == null) {
					if (b2cPrice.getCashHqj() != null) {
						dealerProInfo.setCashHqj(Float.parseFloat(b2cPrice.getCashHqj()));
					}
					dealerProInfo.setProd_type(b2cPrice.getProdType());
					BigDecimal b1 = new BigDecimal(Float.toString(Float.parseFloat(b2cPrice.getBrandName())));
					BigDecimal b2 = new BigDecimal("0");
					BigDecimal b3 = new BigDecimal("100");
					BigDecimal multiply = new BigDecimal("0");
					if (b2cPrice.getCashHqj() != null) {
						b2 = new BigDecimal(Float.toString(Float.parseFloat(b2cPrice.getCashHqj())));
						BigDecimal discount = b2.divide(b1, 2, RoundingMode.HALF_UP);
						multiply = discount.multiply(b3);
					}
					double ceil = Math.ceil(b1.subtract(b2).doubleValue());
					
					dealerProInfo.setHqj((float) ceil);
					dealerProInfo.setDiscount(multiply.floatValue());
				}
				if (tags != null) {
					dealerProInfo.setProd_type(b2cPrice.getProdType());
					BigDecimal b2 = new BigDecimal("0");
					BigDecimal b1 = new BigDecimal(Float.toString(Float.parseFloat(b2cPrice.getBrandName())));
					BigDecimal b4 = new BigDecimal("10");
					BigDecimal b3 = new BigDecimal(Float.toString(Float.parseFloat("100")));
					BigDecimal divide2 = new BigDecimal("0");
					if (b2cPrice.getCashHqj() != null) {
						b2 = new BigDecimal(b2cPrice.getCashHqj());
						divide2 = b2;
					}

					BigDecimal bigDecimal = b3.subtract(b2);

					BigDecimal divide = bigDecimal.divide(b3);
					BigDecimal multiply = b1.multiply(divide);
					BigDecimal hqj = multiply.setScale(0, BigDecimal.ROUND_HALF_UP);
					BigDecimal subtract = b1.subtract(hqj);
					BigDecimal setScale = subtract.setScale(2);
					dealerProInfo.setHqj(hqj.floatValue());
					/* BigDecimal cc = cash.setScale(2, 4) */;
					dealerProInfo.setCashHqj(setScale.floatValue());
					dealerProInfo.setDiscount(divide2.floatValue());
				}
				// 设置商品翼支付价格
				dealerProInfo.setBestoay_price(Float.parseFloat(b2cPrice.getDefiningValue()));
			}
			
			// List<Map<String,String>> promotionPrices = new
			// ArrayList<Map<String,String>>();
			Map<String, Float> ppMap = new HashMap<String, Float>();
			ppMap.put("wapPrice", dealerProInfo.getUnit_price());
			// dealerProInfo.setWap_unit_price(dealerProInfo.getUnit_price()-wapPrice.getDiscountPrice().floatValue());
			ppMap.put("pcPrice", dealerProInfo.getUnit_price());
			// dealerProInfo.setPc_unit_price(dealerProInfo.getUnit_price()-pcPrice.getDiscountPrice().floatValue());
			ppMap.put("appPrice", dealerProInfo.getUnit_price());
			// dealerProInfo.setApp_unit_price(dealerProInfo.getUnit_price()-appPrice.getDiscountPrice().floatValue());
			// 翼支付减去折扣后的价格
			ppMap.put("bestpayPrice", dealerProInfo.getBestoay_price());
			ppMap.put("zjPrice", dealerProInfo.getUnit_price());
			ppMap.put("ymPrice", dealerProInfo.getUnit_price());
			ppMap.put("bfPrice", dealerProInfo.getUnit_price());
			ppMap.put("nbPrice", dealerProInfo.getUnit_price());
			dealerProInfo.setPromotion_price(ppMap);
		}
		dealerProInfo.setSid(proInfoMain.getSupplierid());
		dealerProInfo.setB2b_istate(Integer.parseInt(proInfoMain.getB2b_istate()));
		dealerProInfo.setB2c_istate(Integer.parseInt(proInfoMain.getB2c_istate()));
		dealerProInfo.setImageAlt(proInfoMain.getImageAlt());
		dealerProInfo.setCpid(proInfoMain.getCate_pub_id());
		if (null != b2bNameMap) {
			List<BrandName> b2bNameList = b2bNameMap.get(proInfoMain.getCate_pub_id());
			if (b2bNameList != null && b2bNameList.size() > 0) {
				for (BrandName param_b : b2bNameList) {
					b2bCdids.addAll(handingCdids(param_b.getBrandName()));
					b2bCdidNames.addAll(handingCdidNames(param_b.getBrandName(), "b2b"));
				}
				dealerProInfo.setB2b_cdids(b2bCdids);
				dealerProInfo.setB2b_cdidNames(b2bCdidNames);
			} else {
				dealerProInfo.setB2b_cdids(null);
				dealerProInfo.setB2b_cdidNames(null);
			}
		}
		// b2c展示类目ID、名称处理
		if (null != b2cNameMap) {
			// 通过发布类目得到展示类目列表
			List<BrandName> b2cNameList = b2cNameMap.get(proInfoMain.getCate_pub_id());
			if (b2cNameList != null && b2cNameList.size() > 0) {
				for (BrandName param_c : b2cNameList) {
					b2cCdids.addAll(handingCdids(param_c.getBrandName()));
					b2cCdidNames.addAll(handingCdidNames(param_c.getBrandName(), "b2c"));
				}
				dealerProInfo.setB2c_cdids(b2cCdids);
				dealerProInfo.setB2c_cdidNames(b2cCdidNames);
			} else {
				dealerProInfo.setB2c_cdids(null);
				dealerProInfo.setB2c_cdidNames(null);
			}
		}

		Collection<String> pubIds = crossLink.get(proInfoMain.getCate_pub_id());
		if (pubIds != null) {
			for (String id : pubIds) {
				b2cCdids.addAll(handingCdids(id));
			}
			dealerProInfo.setB2c_cdids(b2cCdids);
		}

		dealerProInfo.setLineid(proInfoMain.getProd_line_id());

		// 商品销量

		Integer proQty = productSale.get(proInfoMain.getProductid());
		if (null != proQty) {
			dealerProInfo.setSalesVolume(proQty.intValue());
		} else {
			dealerProInfo.setSalesVolume(0);
		}

		// 上下架时间问题(存在争议)
		dealerProInfo.setB2b_opt(getMillis(sdf.parse(proInfoMain.getOperatetime())));
		dealerProInfo.setB2c_opt(getMillis(sdf.parse(proInfoMain.getOperatetime())));
		dealerProInfo.setCt(getMillis(sdf.parse(proInfoMain.getOperatetime()))); // B2B创建时间
		dealerProInfo.setImageUrl(proInfoMain.getImageurl());
		if (brandNameMap != null) {
			BrandName brandName = brandNameMap.get(proInfoMain.getBrand_id());
			if (null != brandName) {
				dealerProInfo.setBrandId(brandName.getBrandId());
				dealerProInfo.setBrandName(brandName.getBrandName());
				dealerProInfo.setBrandNameFc(brandName.getBrandName());
			}
		}

	}

	public static long getMillis(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getTimeInMillis();
	}

	/**
	 * 展示类目属性
	 * 
	 * @param dealerProInfo
	 * @param proInfoMain
	 * @param l2sid
	 * @throws Exception
	 */
	private void mergeAttrCode(DealerProInfo dealerProInfo, Map<String, List<AttrCode>> attrMap,
			Map<String, AttrCode> attrcodeMap, String dbName2) throws Exception {

		List<AttrCode> attrCodes = attrMap.get(dealerProInfo.getPid());

		StringBuilder codeBuffer = new StringBuilder();
		StringBuilder attrNameValBuffer = new StringBuilder();
		if (null == attrCodes) {
			return;
		}
		for (AttrCode attrCode : attrCodes) {

			// 目前对比老搜索中的属性值改（通过条件筛选出唯一一个）
			// AttrCode code =
			// attrDao.getAttrCode(attrCode.getAttrname(),attrCode.getAttrvalname(),
			// dbName2);
			AttrCode codes = attrcodeMap.get(attrCode.getAttrname() + attrCode.getAttrvalname());
			attrNameValBuffer.append(attrCode.getAttrname() + "," + attrCode.getAttrvalname() + ",");

			if (codes != null) {
				if (dealerProInfo.getB2b_cdids() != null && dealerProInfo.getB2b_cdids().size() > 0) {

					if (dealerProInfo.getB2b_cdids().get(0).length() == 3) {
						codeBuffer.append(dealerProInfo.getB2b_cdids().get(0) + "000000000" + codes.getId() + " ");
					} else if (dealerProInfo.getB2b_cdids().get(0).length() == 6) {
						codeBuffer.append(dealerProInfo.getB2b_cdids().get(0) + "000000" + codes.getId() + " ");
					} else if (dealerProInfo.getB2b_cdids().get(0).length() == 9) {
						codeBuffer.append(dealerProInfo.getB2b_cdids().get(0) + "000" + codes.getId() + " ");
					} else if (dealerProInfo.getB2b_cdids().get(0).length() == 12) {
						codeBuffer.append(dealerProInfo.getB2b_cdids().get(0) + codes.getId() + " ");
					}
				}

			}
		}
		if (!"".equals(codeBuffer.toString())) {
			dealerProInfo.setB2b_dispat(codeBuffer.toString());
		}
		if (!"".equals(attrNameValBuffer.toString())) {
			dealerProInfo.setAttrNameVal(attrNameValBuffer.toString());
		}
	}

	/**
	 * 查询产品的标题信息（商品副表b2b）
	 * 
	 * @param dealerProInfo
	 * @param dealerProInfoBase
	 */
	private void mergeProductInfoBase_b(DealerProInfo dealerProInfo, DealerProInfoBase dealerProInfoBase) {
		dealerProInfo.setB2b_pname(dealerProInfoBase.getProductname());
		dealerProInfo.setPdesc(dealerProInfoBase.getShortdescription());
	}

	/**
	 * 查询产品的标题信息（商品副表b2c）
	 * 
	 * @param dealerProInfo
	 * @param dealerProInfoBase
	 * @throws Exception
	 */
	private void mergeProductInfoBase_c(DealerProInfo dealerProInfo, DealerProInfoBaseC dealerProInfoBasec)
			throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dealerProInfo.setB2c_pname(dealerProInfoBasec.getB2cProductName());
		dealerProInfo.setB2cSupply(Integer.parseInt(dealerProInfoBasec.getB2cSupply()));
		dealerProInfo.setCt(getMillis(sdf.parse(dealerProInfoBasec.getCreateTime()))); // b2c
																						// 创建时间
		// dealerProInfo.setPdesc(dealerProInfoBase.getShortdescription());
	}

	/**
	 * 查询sku,sku_code,库存量,用空格做为分格符便于分词
	 * 
	 * @param dealerProInfo
	 * @param dealerProInfoSkus
	 * @throws SQLException
	 */
	private void mergeProductInfoSku(DealerProInfo dealerProInfo, Collection<DealerProInfoSku> dealerProInfoSkus,
			String productid, Map<String, BrandName> stockMap) throws Exception {
		// String stockId = null;
		StringBuffer skuCode = new StringBuffer();
		for (DealerProInfoSku dealerProInfoSku : dealerProInfoSkus) {
			// 调用库存（skuid）
			if (StringUtils.isNotEmpty(dealerProInfoSku.getSku_code())) {
				skuCode.append(dealerProInfoSku.getSku_code() + " ");
			}
		}
		if (null != stockMap) {
			BrandName stock = stockMap.get(productid);
			if (null != stock) {
				dealerProInfo.setStock(Integer.parseInt(stock.getBrandName()));
				dealerProInfo.setIsStock(1); // 库存 boolean
			} else {
				dealerProInfo.setStock(0);
			}
		}
		dealerProInfo.setSc(skuCode.toString());
	}

	/**
	 * 商品销售信息
	 * 
	 * @param dealerProInfo
	 * @param dealerProInfoSaleSetting
	 *            B2B 的销售信息
	 */
	private void mergeProductInfoSaleSetting(DealerProInfo dealerProInfo,
			DealerProInfoSaleSetting dealerProInfoSaleSetting) {
		// 最小销售价
		dealerProInfo.setMsp(Float.parseFloat(dealerProInfoSaleSetting.getMin_buyer_price()));
		// 最大销售价
		dealerProInfo.setMaxsp(Float.parseFloat(dealerProInfoSaleSetting.getMax_buyer_price()));
	}

	/**
	 * 处理类目
	 * 
	 * @param cdids
	 * @return
	 */
	private List<String> handingCdids(String cdids) {
		List<String> cdids_ = new ArrayList<String>();
		if (cdids.length() == ConstantManage.THREE) {
			cdids_.add(cdids);
		} else if (cdids.length() == ConstantManage.SIX) {
			cdids_.add(cdids.substring(0, cdids.length() - 3));
			cdids_.add(cdids);
		} else if (cdids.length() == ConstantManage.NINE) {
			cdids_.add(cdids.substring(0, cdids.length() - 3));
			cdids_.add(cdids.substring(0, cdids.length() - 6));
			cdids_.add(cdids);
		} else if (cdids.length() == ConstantManage.TWELVE) {
			cdids_.add(cdids.substring(0, cdids.length() - 3));
			cdids_.add(cdids.substring(0, cdids.length() - 6));
			cdids_.add(cdids.substring(0, cdids.length() - 9));
			cdids_.add(cdids);
		}
		return cdids_;
	}

	/**
	 * 处理类目名称
	 * 
	 * @param cdidNames
	 * @return
	 */
	private List<String> handingCdidNames(String cdids, String isB2c) {
		DealerProInfoDao dealerProInfoDao = new DealerProInfoDaoImpl();
		String cdids_ = "";
		if (cdids.length() == ConstantManage.THREE) {
			cdids_ = "'" + cdids + "'";
		} else if (cdids.length() == ConstantManage.SIX) {
			cdids_ = "'" + cdids.substring(0, cdids.length() - 3) + "','" + cdids + "'";
		} else if (cdids.length() == ConstantManage.NINE) {
			cdids_ = "'" + cdids.substring(0, cdids.length() - 6) + "','" + cdids.substring(0, cdids.length() - 3)
					+ "','" + cdids + "'";
		} else if (cdids.length() == ConstantManage.TWELVE) {
			cdids_ = "'" + cdids.substring(0, cdids.length() - 9) + "','" + cdids.substring(0, cdids.length() - 6)
					+ "','" + cdids.substring(0, cdids.length() - 3) + "','" + cdids + "'";
		}
		List<String> cdidNames_ = new ArrayList<String>();
		try {
			cdidNames_ = dealerProInfoDao.getb2cCateDispNamesByIds(ConstantManage.DBNAME2, isB2c, cdids_);
		} catch (Exception e) {
			logger.error("handingCdidNames.Exception:", e);
		} finally {
		}
		return cdidNames_;
	}
	/**
	 * 调用促销接口 返回对应sku的促销价格
	 * 
	 * @param skuids
	 * @param type
	 * @return
	 */
	/*
	 * private Map<Long,PromotionPriceDto> getSkuPriceMap(List<Long>
	 * skuids,String type){ Map<Long,PromotionPriceDto> skuPriceMap = new
	 * HashMap<Long,PromotionPriceDto>(); List<PromotionPriceDto>
	 * skuPromationPrice = null; List<Long> skuids1 = new ArrayList<Long>();
	 * if(null!=skuids){ try { for(int i=0;i<skuids.size();i++){
	 * skuids1.add(skuids.get(i)); if(i==0 && skuids.size()==1){
	 * logger.info("其他渠道过促销前：" + i); // skuPromationPrice =
	 * RemoteServiceSingleton.getInstance().getProductByPromotionInfoService().
	 * getPromotionPriceBySkuId(skuids1, 4, type); // for (PromotionPriceDto
	 * promotionPriceDto : skuPromationPrice) { // Long skuid =
	 * promotionPriceDto.getSkuId(); // skuPriceMap.put(skuid,
	 * promotionPriceDto); // } logger.info("其他渠道过促销累计：" +
	 * skuPromationPrice.size()); skuids1 = new ArrayList<Long>(); }else if(i
	 * !=0 && i%1000 == 0 || i !=0 && i == skuids.size()-1){
	 * logger.info("其他渠道过促销前：" + i); // skuPromationPrice =
	 * RemoteServiceSingleton.getInstance().getProductByPromotionInfoService().
	 * getPromotionPriceBySkuId(skuids1, 4, type); // for (PromotionPriceDto
	 * promotionPriceDto : skuPromationPrice) { // Long skuid =
	 * promotionPriceDto.getSkuId(); // skuPriceMap.put(skuid,
	 * promotionPriceDto); // } logger.info("过促销累计：" + skuPriceMap.size());
	 * skuids1 = new ArrayList<Long>(); } } //skuPromationPrice =
	 * RemoteServiceSingleton.getInstance().getProductByPromotionInfoService().
	 * getPromotionPriceBySkuId(skuids, 4, type); } catch (Exception e) {
	 * logger.error(e.getMessage(),e); } }
	 * 
	 * for (PromotionPriceDto promotionPriceDto : skuPromationPrice) { Long
	 * skuid = promotionPriceDto.getSkuId(); skuPriceMap.put(skuid,
	 * promotionPriceDto); }
	 * 
	 * return skuPriceMap; }
	 */

	/**
	 * 调用促销接口 返回对应sku的促销价格
	 * 
	 * @param skuids
	 * @param type
	 * @return
	 */
	/*
	 * private Map<Long,ProductPromotionDto> getSkuPriceMapBeast(Map<String,
	 * DealerProInfoBaseC> baseMap_c,List<ProInfoMain>
	 * proInfoMains,Multimap<String, DealerProInfoSku> skuMap,List<Long>
	 * skuids,String type){ logger.info(
	 * "----------------------翼支付促销价获取开始-----------------------------");
	 * Map<Long,ProductPromotionDto> skuPriceMap = new
	 * HashMap<Long,ProductPromotionDto>(); List<ProductPromotionDto>
	 * productPromotionInfoByB2C = new ArrayList<ProductPromotionDto>();
	 * List<ProductPromotionDto> productPromotionInfoByB2CTemp = null;
	 * //.getProductPromotionInfoByB2C(listDto,userIds,ACCESS_MODE)
	 * if(null!=skuids){ List<SkuConditionB2BDto> listDto = new
	 * ArrayList<SkuConditionB2BDto>(); List<SkuConditionB2BDto> listDto1 = new
	 * ArrayList<SkuConditionB2BDto>(); Map<String,String> map1 = new
	 * HashMap<String,String>(); Map<String,Short> map2 = new
	 * HashMap<String,Short>(); Long userGade = 4l;// 会员等级 //货源种类
	 * 
	 * 跨境购 ：10+ 直邮:海外直邮 11 备货:由国内保税区发货 12 一般贸易：0-9 国内现货(不是跨境购商品) 1 (默认值) 韩国直邮：21
	 * 
	 * Short supply = 1;//渠道 // 访问方式 Long accessMode = 3l;
	 * Collection<DealerProInfoSku> cDealer = skuMap.values(); //获取productid
	 * 放到map中 for (DealerProInfoSku dealerProInfoSku : cDealer) {
	 * map1.put(dealerProInfoSku.getSku_id(), dealerProInfoSku.getProductid());
	 * } Collection<DealerProInfoBaseC> cSupplys = baseMap_c.values();
	 * //获取supply 放到map2中 for (DealerProInfoBaseC dealerProInfoBaseC : cSupplys)
	 * { map2.put(dealerProInfoBaseC.getProductid(),
	 * Short.valueOf(dealerProInfoBaseC.getB2cSupply())); } //组装
	 * List<SkuConditionB2BDto> listDto String productIdStr = "";
	 * skuids.clear(); skuids.add(145993468465317175l);
	 * skuids.add(145993468465394882l); for(int i=0; i<skuids.size(); i++){
	 * productIdStr = map1.get(skuids.get(i) + ""); //得到商品对象 for (ProInfoMain
	 * proInfoMain : proInfoMains) {
	 * if(proInfoMain.getProductid().equals(productIdStr)){
	 * 
	 * supply = map2.get(productIdStr);
	 * 
	 * if(supply != null){ try{ listDto.add(new
	 * SkuConditionB2BDto(skuids.get(i), proInfoMain.getCate_pub_id(),
	 * proInfoMain.getBrand_id(), userGade, accessMode,supply, 10l));
	 * }catch(Exception e){ logger.info("组装 listDto 对象异常：skuid"
	 * +skuids.get(i)+",proInfoMain.getCate_pub_id():"+proInfoMain.
	 * getCate_pub_id()+",proInfoMain.getBrand_id():"+proInfoMain.getBrand_id()+
	 * ",userGade:"+userGade+",accessMode:"+accessMode+",supply:"+supply+",渠道："+
	 * 10); } } break; } } }
	 * 
	 * try { logger.info("共计商品数：" + listDto.size()); for(int
	 * i=0;i<listDto.size();i++){ listDto1.add(listDto.get(i)); if(i==0 &&
	 * listDto.size()==1){ logger.info("过促销前：" + i);
	 * productPromotionInfoByB2CTemp =
	 * RemoteServiceSingleton.getInstance().getProductByPromotionInfoService().
	 * getProductPromotionInfoByB2C(listDto1,0l,"10"); logger.info("过促销取到：" +
	 * productPromotionInfoByB2CTemp.size());
	 * productPromotionInfoByB2C.addAll(productPromotionInfoByB2CTemp);
	 * logger.info("过促销累计：" + productPromotionInfoByB2C.size()); listDto1 = new
	 * ArrayList<SkuConditionB2BDto>(); }else if(i !=0 && i%100 == 0 || i !=0 &&
	 * i == listDto.size()-1){ logger.info("过促销前：" + i);
	 * productPromotionInfoByB2CTemp =
	 * RemoteServiceSingleton.getInstance().getProductByPromotionInfoService().
	 * getProductPromotionInfoByB2C(listDto1,0l,"10"); logger.info("过促销取到：" +
	 * productPromotionInfoByB2CTemp.size());
	 * productPromotionInfoByB2C.addAll(productPromotionInfoByB2CTemp);
	 * logger.info("过促销累计：" + productPromotionInfoByB2C.size()); listDto1 = new
	 * ArrayList<SkuConditionB2BDto>(); } } //listDto1.add(new
	 * SkuConditionB2BDto(143865694986316786l, "020002005003",
	 * "143081402367033892", userGade, accessMode,supply, 10l));
	 * //productPromotionInfoByB2C =
	 * RemoteServiceSingleton.getInstance().getProductByPromotionInfoService().
	 * getProductPromotionInfoByB2C(listDto1,0l,"10"); //skuPromationPrice =
	 * RemoteServiceSingleton.getInstance().getProductByPromotionInfoService().
	 * getPromotionPriceBySkuId(skuids, 4, type); } catch (Exception e) {
	 * logger.error(e.getMessage(),e); } }
	 * 
	 * for (ProductPromotionDto promotionPriceDto : productPromotionInfoByB2C) {
	 * Long skuid = promotionPriceDto.getSkuid();
	 * //logger.info("结果："+promotionPriceDto.getSkuid()
	 * +",直降："+promotionPriceDto.getStraightDownPrice()); skuPriceMap.put(skuid,
	 * promotionPriceDto); }
	 * logger.info("----------------------翼支付促销价获取结束-共："+skuPriceMap.size());
	 * return skuPriceMap; }
	 */

}
