package com.mall.search.elasticsearch;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mall.dsearch.vo.SearchSupplierDetailResponse;
import com.mall.dsearch.vo.SearchSupplierRequest;
import com.mall.dsearch.vo.Supplier;
import com.mall.dsearch.vo.SupplierDetail;
import com.mall.dsearch.vo.SupplierDetailAttr;
import com.mall.search.vo.ResponseSupplierItem;

/**
 * 装配返回值
 * 
 */
public class AssemblingSupplierItem {

	private static final Logger LOGGER = LoggerFactory.getLogger(AssemblingSupplierItem.class);
	
	public static final List<String> LIST=new ArrayList<>();
	
	public static final List<String> LIST_FIXED=new ArrayList<>();
	
	
	static{
		String [] strs={"餐饮食品","母婴玩具","生活家居","机票旅游","娱乐健身服务","医疗","时尚","房地产","生活咨询服务","快递货运服务",
				"网络虚拟服务","交通运输服务类","软件","票务","公共事业缴费","线下零售","机械电子","收藏宠物","通信","金融","数字娱乐",
				"苗木绿化","装饰","教育培训","线下零售","书籍音像","书籍音像文具","公益","保险","数码","预付卡","众筹","平台商","彩票",
				"直销","电商团购","境外","其他生活缴费","其他"};
		for (String str : strs) {
			LIST.add(str);
		}

//		String [] strsFixed={"美食","时尚购物","休闲娱乐","酒店住宿","美容美发","景点","教育培训","医疗"};
		String [] strsFixed={"餐饮","便利店","超市","商场","酒店","电影院","景点","休闲娱乐","教育培训"};
		for (String str : strsFixed) {
			LIST_FIXED.add(str);
		}
	}

	/**
	 * 商家数据返回值拼装
	 * 
	 * @param searchResponse
	 * @param searchRequest
	 * @return
	 */
	public static ResponseSupplierItem responseBuilder(org.elasticsearch.action.search.SearchResponse searchResponse,
			SearchSupplierRequest searchSupplierRequest) {
		ResponseSupplierItem responseSupplierItem = null;
		if (searchResponse.getHits().getTotalHits() > 0) {
			responseSupplierItem = new ResponseSupplierItem();
			List<Supplier> items = new ArrayList<Supplier>();
//			Set<String> companyBizCategorys = new HashSet<String>();

			LOGGER.info("进行商家数据装载");
			// 商家数据的装载
			SearchHit[] searchHits = searchResponse.getHits().getHits();
			if (searchHits.length > 0) {
				for (SearchHit searchHit : searchHits) {
					Supplier item = buildItem(searchHit);
					if (null != item && item.getCompanyBizCategoryName()!=null && item.getCompanyBizCategory()!=null) {
//						companyBizCategorys.add(item.getCompanyBizCategoryName());
					}
					items.add(item);
				}
			}

//			responseSupplierItem.setCompanyBizCategorys(companyBizCategorys);
			responseSupplierItem.setSupplierItemList(items);
		}
		return responseSupplierItem;
	}
	

	/**
	 * 商家店铺详细数据拼装
	 * @param searchResponse
	 * @return
	 */
	public static SearchSupplierDetailResponse responseDetailBuilder(SearchResponse searchResponse) {
		SearchSupplierDetailResponse searchSupplierDetailResponse = null;
		if (searchResponse.getHits().getTotalHits() > 0) {
			searchSupplierDetailResponse=new SearchSupplierDetailResponse();
			LOGGER.info("商家店铺详细数据装载");
			SearchHit[] searchHits = searchResponse.getHits().getHits();
			if (searchHits.length > 0) {
				for (SearchHit searchHit : searchHits) {
//					SupplierDetail item = buildDetailItem(searchHit);
					Supplier supplier = buildItem(searchHit);
//					supplier.setSupplierDetail(item);
					searchSupplierDetailResponse.setSupplier(supplier);
				}
			}
		}
		return searchSupplierDetailResponse;
	}

	
	/**
	 *	构建店铺详情实体
	 * @param searchHit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static SupplierDetail buildDetailItem(SearchHit searchHit) {
		SupplierDetail item = null;
		if (null != searchHit) {
			item = new SupplierDetail();

			// 获取es 中的对应field值             
			Map<String,Object> supplierDetail = (HashMap<String,Object>)searchHit.getSource().get("supplierDetail");
			// id
			item.setId((Integer)supplierDetail.get("id"));
			// supplierId
			item.setSupplierId((Integer) supplierDetail.get("supplierId"));
			// 公司简称/公司营业名称
			item.setNameJC((String) supplierDetail.get("nameJC"));
			// 企业经营门店照片上传文件
			item.setCompanyStoreLogo((String) supplierDetail.get("companyStoreLogo"));
			// 企业logo上传文件
			item.setLogoImgurl((String) supplierDetail.get("logoImgurl"));
			// 联系人
			item.setContact((String) supplierDetail.get("contact"));
			// 联系人电话
			item.setContactTel((String) supplierDetail.get("contactTel"));
			// 联系人手机
			item.setPhone((String) supplierDetail.get("phone"));
			// 经营特色
			item.setJyTS((String) supplierDetail.get("jyTS"));
			// 经营时间
			item.setJySJ((String) supplierDetail.get("jySJ"));
			// 主营业务
			item.setMainBusiness((String) supplierDetail.get("mainBusiness"));
			// 经纬度类型
			item.setLocationType((Integer) supplierDetail.get("locationType"));
			// 纬度
			Double locationLat=(Double)supplierDetail.get("locationLat");
			if(locationLat!=null){
				item.setLocationLat(BigDecimal.valueOf(locationLat));
			}
			// 经度
			Double locationLng=(Double)supplierDetail.get("locationLng");
			if(locationLng!=null){
				item.setLocationLng(BigDecimal.valueOf(locationLng));
			}
			// 地图定位地址
			item.setLocationPoiaddress((String) supplierDetail.get("locationPoiaddress"));
			// 地图定位名称
			item.setLocationPoiname((String) supplierDetail.get("locationPoiname"));
			// 地图定位城市
			item.setLocationCityname((String) supplierDetail.get("locationCityname"));
			// 是可用 0, 不可用
			item.setStatus((Integer) supplierDetail.get("status"));
			
			//详情页面图片
			List<Map<String, Object>> sda = (ArrayList<Map<String,Object>>)supplierDetail.get("attrList");
			List<SupplierDetailAttr> list=new ArrayList<>();
			if(null!=sda && sda.size()>0){
				for (Map<String, Object> map : sda) {
					SupplierDetailAttr supplierDetailAttr=new SupplierDetailAttr();
					supplierDetailAttr.setId((Integer)map.get("id"));
					supplierDetailAttr.setStoreId((Integer)map.get(("storeId")));
					supplierDetailAttr.setAttrName((String)map.get("attrName"));
					supplierDetailAttr.setAttrType((Integer)map.get("attrType"));
					supplierDetailAttr.setAttrValue((String)map.get("attrValue"));
					supplierDetailAttr.setMemo((String)map.get("memo"));
					supplierDetailAttr.setSort((Integer)map.get("sort"));
					supplierDetailAttr.setStatus((Integer)map.get("status"));
					supplierDetailAttr.setUrl((String)map.get("url"));
					list.add(supplierDetailAttr);
				}
			}
			item.setAttrList(list);
		}
		return item;
	}
	
	

	/**
	 *	构建店铺详情实体
	 * @param searchHit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static List<SupplierDetailAttr> buildDetailAttrItem(SearchHit searchHit) {
		List<SupplierDetailAttr> item = null;
		if (null != searchHit) {

			// 获取es 中的对应field值             
			List<Map<String, Object>> sda = (ArrayList<Map<String,Object>>)searchHit.getSource().get("attrList");
			
			//详情页面图片
			List<SupplierDetailAttr> list=new ArrayList<>();
			if(null!=sda && sda.size()>0){
				for (Map<String, Object> map : sda) {
					SupplierDetailAttr supplierDetailAttr=new SupplierDetailAttr();
					supplierDetailAttr.setId((Integer)map.get("id"));
					supplierDetailAttr.setStoreId((Integer)map.get(("storeId")));
					supplierDetailAttr.setAttrName((String)map.get("attrName"));
					supplierDetailAttr.setAttrType((Integer)map.get("attrType"));
					supplierDetailAttr.setAttrValue((String)map.get("attrValue"));
					supplierDetailAttr.setMemo((String)map.get("memo"));
					supplierDetailAttr.setSort((Integer)map.get("sort"));
					supplierDetailAttr.setStatus((Integer)map.get("status"));
					supplierDetailAttr.setUrl((String)map.get("url"));
					list.add(supplierDetailAttr);
				}
			}
			item=list;
		}
		return item;
	}
	
	

	/**
	 * 构建-拼装商品
	 *
	 * @param searchHit
	 * @return
	 */
	private static Supplier buildItem(SearchHit searchHit) {
		Supplier item = null;
		if (null != searchHit) {
			item = new Supplier();

			// 获取es 中的对应field值             		          
			
			//id
			Long supplierId = Long.valueOf((Integer) searchHit.getSource().get("supplierId"));
			//供应商编码
			String supplierCode = (String) searchHit.getSource().get("supplierCode");
			// 公司名称
			String name = (String) searchHit.getSource().get("name");
			// 公司简称
			String nameJC = (String) searchHit.getSource().get("nameJC");
			//
			Integer organizationType = (Integer) searchHit.getSource().get("organizationType");
			// 商家类型
			Integer type = (Integer) searchHit.getSource().get("type");
			// 省id
			Integer provinceId = (Integer) searchHit.getSource().get("provinceId");
			// 市id
			Integer cityId = (Integer) searchHit.getSource().get("cityId");
			// 区id
			Integer areaId = (Integer) searchHit.getSource().get("areaId");
			// 省name
			String provinceName = (String) searchHit.getSource().get("provinceName");
			// 市name
			String cityName = (String) searchHit.getSource().get("cityName");
			// 区name
			String areaName = (String) searchHit.getSource().get("areaName");
			// 企业 2 个体工商户 3事业单位
			Integer companyBizType = (Integer) searchHit.getSource().get("companyBizType");
//	        // 企业 2 个体工商户 3事业单位                      
			String companyBizName = (String) searchHit.getSource().get("companyBizName");
			// 企业所属类目(参考企业分类字典数据)
			Integer companyBizCategory = (Integer) searchHit.getSource().get("companyBizCategory");
			// 企业所属类目(参考企业分类字典数据)
			String companyBizCategoryName = (String) searchHit.getSource().get("companyBizCategoryName");
			// 公司信息
			String companyInfo = (String) searchHit.getSource().get("companyInfo");
			// 公司描述
			String companyDescribe = (String) searchHit.getSource().get("companyDescribe");
			// 企业logo上传文件
			String logoImgurl = (String) searchHit.getSource().get("logoImgurl");
			// 企业经营门头店照片
			String companyStoreLogo = (String) searchHit.getSource().get("companyStoreLogo");
			// 办公地址/营业地址
			String address = (String) searchHit.getSource().get("address");
			// 联系人
			String contact = (String) searchHit.getSource().get("contact");
			// 业务联系人电话
			String contactTel = (String) searchHit.getSource().get("contactTel");
			// 联系人手机
			String phone = (String) searchHit.getSource().get("phone");
			// 入住区域类型 （字典：1 自营、2 、非自营）
			String companyRegion = (String) searchHit.getSource().get("companyRegion");
			// 企业经理人
			String manager = (String) searchHit.getSource().get("manager");
			// 经理人电话
			String managerTel = (String) searchHit.getSource().get("managerTel");
			// 头像地址
			String icon = (String) searchHit.getSource().get("icon");
			// 入驻区域（字典：1 自营、2 孵化、3 高新、4普通）
			String companyQy = (String) searchHit.getSource().get("companyQy");
			// id
			Integer sdId = (Integer) searchHit.getSource().get("sdId");
			// 经营特色
			String sdJyTS = (String) searchHit.getSource().get("sdJyTS");
			// 经营时间
			String sdJySJ = (String) searchHit.getSource().get("sdJySJ");
			// 主营业务id
			Integer sdMainBusinessId = (Integer) searchHit.getSource().get("sdMainBusinessId");
			// 主营业务
			String sdMainBusiness = (String) searchHit.getSource().get("sdMainBusiness");
			// 经纬度类型
			Integer sdLocationType = (Integer) searchHit.getSource().get("sdLocationType");
			// 纬度
			Double sdLocationLat = (Double) searchHit.getSource().get("sdLocationLat");
			// 经度
			Double sdLocationLng=(Double)searchHit.getSource().get("sdLocationLng");
			// 地图定位地址
			String sdLocationPoiaddress = (String) searchHit.getSource().get("sdLocationPoiaddress");
			// 地图定位名称
			String sdLocationPoiname = (String) searchHit.getSource().get("sdLocationPoiname");
			// 地图定位城市
			String sdLocationCityname = (String) searchHit.getSource().get("sdLocationCityname");
			// 是可用 0, 不可用
			Integer sdStatus = (Integer) searchHit.getSource().get("sdStatus");
			
			
			
			item.setSupplierId(supplierId);
			item.setSupplierCode(supplierCode);
			item.setName(name);
			item.setNameJC(nameJC);
			item.setOrganizationType(organizationType);
			item.setType(type);
			item.setProvinceId(provinceId);
			item.setCityId(cityId);
			item.setAreaId(areaId);
			item.setProvinceName(provinceName);
			item.setCityName(cityName);
			item.setAreaName(areaName);
			item.setCompanyBizType(companyBizType);
			item.setCompanyBizName(companyBizName);
			item.setCompanyBizCategory(companyBizCategory);
			item.setCompanyBizCategoryName(companyBizCategoryName);
			item.setCompanyInfo(companyInfo);
			item.setCompanyDescribe(companyDescribe);
			item.setLogoImgurl(logoImgurl);
			item.setCompanyStoreLogo(companyStoreLogo);
			item.setAddress(address);
			item.setContact(contact);
			item.setContactTel(contactTel);
			item.setPhone(phone);
			item.setCompanyRegion(companyRegion);
			item.setManager(manager);
			item.setManagerTel(managerTel);
			item.setIcon(icon);
			item.setCompanyQy(companyQy);
			
			//装载店铺详情数据
			item.setSdId(sdId);
			item.setSdJySJ(sdJySJ);
			item.setSdJyTS(sdJyTS);
			item.setSdMainBusinessId(sdMainBusinessId);
			item.setSdMainBusiness(sdMainBusiness);
			item.setSdLocationType(sdLocationType);
			if(sdLocationLat!=null){
				item.setSdLocationLat(BigDecimal.valueOf(sdLocationLat));
			}
			if(sdLocationLng!=null){
				item.setSdLocationLng(BigDecimal.valueOf(sdLocationLng));
			}
			item.setSdLocationPoiaddress(sdLocationPoiaddress);
			item.setSdLocationPoiname(sdLocationPoiname);
			item.setSdLocationCityname(sdLocationCityname);
			item.setSdStatus(sdStatus);
			
			//装载店铺详情页面图片数据
			List<SupplierDetailAttr> list = buildDetailAttrItem(searchHit);
			item.setAttrList(list);
			
			//装载店铺详情页面数据
//			SupplierDetail supplierDetail = buildDetailItem(searchHit);
//			item.setSupplierDetail(supplierDetail);
			
		}
		return item;
	}



}
