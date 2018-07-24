package com.mall.po;

import java.util.List;
import java.util.Map;

/**
 * 商品搜索类(根据业务需求和页面展示定义)
 * @author DOUBLELL
 *
 */
public class DealerProInfo {
	
	
	



	/**
	 * 商品id
	 */
	private String pid;
	/**
	 * 商品名称
	 */
	private String b2b_pname;
	
	/**
	 * 商品名称
	 */
	private String b2c_pname;
	
	/**
	 * 供应商code
	 */
	private String supplier_code;
	
	/**
	 * 供应商名称
	 */
	private String supplier_name;
	
	/**
	 * 供应商简称
	 */
	private String supplier_name_jc;
	
	/**
	 * 供应商客服电话
	 */
	private String supplier_kf_tel;
	
	/**
	 * 供应商入驻区域
	 */
	private String company_region;
	
	/**
	 * 供应商logo imgurl
	 */
	private String supplier_logo_imgurl;
	
	/**
	 * 原产地id：cyid
	 */
	private String cyid;
	/**
	 * 供应商id：supplierid
	 */
	private String sid;

	/**
	 * 经销商id：DealerID
	 */
	private String did;

	/**
	 * 商户sku编码:skuCode
	 */
	private String sc;

	/**
	 * 库存量
	 */
	private int stock;
	/**
	 * b2b上下架状态
	 */
	private int b2b_istate;
	
	/**
	 * b2c上下架状态
	 */
	private int b2c_istate;


	/**
	 * 发布类目叶子类目编号cate_pub_id
	 */
	private String cpid;

	/**
	 * 商品展示类目b2bcate_disp_id
	 */
	private List<String> b2b_cdids;
	/**
	 * 商品展示类目b2ccate_disp_id
	 */
	private List<String> b2c_cdids;
	/**
	 * 商品展示类目名称 b2bcate_disp_name(中、英文 空格分隔)
	 */
	private List<String> b2b_cdidNames;
	/**
	 * 商品展示类目名称b2ccate_disp_name(中、英文 空格分隔)
	 */
	private List<String> b2c_cdidNames;

	/**
	 * 所属产品线Id:prodLineId
	 */
	private String lineid;

	/**
	 * b2b_opt 上架时间
	 */
	private Long b2b_opt;
	
	/**
	 * b2c_opt 上架时间
	 */
	private Long b2c_opt;

	/**
	 * 创建时间createdDate
	 */
	private Long ct;
	/**
	 * 商品短描述
	 */
	private String pdesc;

	/**
	 * 最小销售价 min_seller_price msp 最小市场价 b2b
	 */
	private float msp;

	/**
	 * 最大销售价 max_seller_price  maxsp 最大市场价 b2b
	 */
	private float maxsp;

	/**
	 * 商品主图的url
	 */
	private String imageUrl;

	/**
	 * 品牌ID：brandId
	 */
	private String brandId;
	/**
	 * 品牌名：brandName
	 */
	private String brandName;
	
	/**
	 * 品牌名：brandNameFc分词
	 */
	private String brandNameFc;
	
	/**
	 * 子品牌名：subBrandName
	 */
	//private String subBrandName;
	/**
	 * 商品单位：measureName
	 */
	//private String measureName;

	/**
	 * 国旗链接图
	 */
	private String countryImg;
	/**
	 * 国家名
	 */
	private String cyName;
	/**
	 * 保税区
	 */
	private String bondedZone;
	/**
	 * 翼支付价格  b2c 最小 多个ski
	 */
	private float bestoay_price;

	/**
	 * 真实价格  b2c 最小 多个ski
	 */
	private float unit_price;  
	
	/**
	 * 真实价格  b2b dealer_price b2b wap端售价 dealer_price
	 */
	private float dealer_price; 
	
	/**
	 * 真实价格  b2b retail_price; b2b wap端建议售价 retail_price
	 */
	private float retail_price; 
	
	/**
	 * 参考价格  市场价 最大
	 */
	private float domestic_price;
	
	private String prod_type; 
	/**
	 * prod_type为6时，cashHqj为现金数额
	 */
	private float cashHqj;
	/**
	 * 折扣
	 */
	private float discount;
	private float hqj;
	public float getHqj() {
		return hqj;
	}
	public void setHqj(float hqj) {
		this.hqj = hqj;
	}
	/**
	 * 货币符号
	 */
	private String moneySymbols;
	/**
	 * 汇率
	 */
	private String moneyRate;
	/**
	 * 货源类型 （1.海外直邮 2.保税区发货 3.国内发货 51.国际发货（POP商品））
	 */
	private int b2cSupply;
	/**
	 * 增加促销信息
	 */
	private String promotion;
	private String imageAlt;
	private String b2b_dispat;
	
	/**
	 * 销量
	 */
	private int salesVolume; 
	
	//促销价格（pc、wap、app）
	private float pc_unit_price;
	private float wap_unit_price;
	private float app_unit_price;
	
	private int isStock = 0;//库存排序 0没有库存 1有库存
	
	private Map<String,Float> promotion_price; //促销价格
	
	/**
	 * 增加商品属性信息
	 */
	private String attrNameVal;
	
	
	private List<String> tagName;
	
	private List<String> cash;
	private List<String> family;
	private List<String> normal;
	private List<String> productType_code;
	private List<String> product_iconsTxT;
	
	
	public List<String> getProductType_code() {
		return productType_code;
	}
	public void setProductType_code(List<String> productType_code) {
		this.productType_code = productType_code;
	}
	public List<String> getFamily() {
		return family;
	}
	public void setFamily(List<String> family) {
		this.family = family;
	}
	public List<String> getCash() {
		return cash;
	}
	public void setCash(List<String> cash) {
		this.cash = cash;
	}
	/**
	 * 增加商品详描信息
	 */
	private String b2cProdDesc;
	
	public String getSupplier_kf_tel() {
		return supplier_kf_tel;
	}
	public void setSupplier_kf_tel(String supplier_kf_tel) {
		this.supplier_kf_tel = supplier_kf_tel;
	}
	public String getSupplier_code() {
		return supplier_code;
	}
	public void setSupplier_code(String supplier_code) {
		this.supplier_code = supplier_code;
	}
	public String getSupplier_name() {
		return supplier_name;
	}
	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}
	public String getSupplier_name_jc() {
		return supplier_name_jc;
	}
	public void setSupplier_name_jc(String supplier_name_jc) {
		this.supplier_name_jc = supplier_name_jc;
	}
	public String getCompany_region() {
		return company_region;
	}
	public void setCompany_region(String company_region) {
		this.company_region = company_region;
	}
	public String getSupplier_logo_imgurl() {
		return supplier_logo_imgurl;
	}
	public void setSupplier_logo_imgurl(String supplier_logo_imgurl) {
		this.supplier_logo_imgurl = supplier_logo_imgurl;
	}
	public float getDealer_price() {
		return dealer_price;
	}
	public void setDealer_price(float dealer_price) {
		this.dealer_price = dealer_price;
	}
	public float getRetail_price() {
		return retail_price;
	}
	public void setRetail_price(float retail_price) {
		this.retail_price = retail_price;
	}
	public String getBrandNameFc() {
		return brandNameFc;
	}
	public void setBrandNameFc(String brandNameFc) {
		this.brandNameFc = brandNameFc;
	}
	public String getB2cProdDesc() {
		return b2cProdDesc;
	}
	public void setB2cProdDesc(String b2cProdDesc) {
		this.b2cProdDesc = b2cProdDesc;
	}
	public float getBestoay_price() {
		return bestoay_price;
	}
	public void setBestoay_price(float bestoay_price) {
		this.bestoay_price = bestoay_price;
	}
	public String getAttrNameVal() {
		return attrNameVal;
	}
	public void setAttrNameVal(String attrNameVal) {
		this.attrNameVal = attrNameVal;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getB2b_pname() {
		return b2b_pname;
	}
	public void setB2b_pname(String b2b_pname) {
		this.b2b_pname = b2b_pname;
	}
	public String getB2c_pname() {
		return b2c_pname;
	}
	public void setB2c_pname(String b2c_pname) {
		this.b2c_pname = b2c_pname;
	}
	public String getCyid() {
		return cyid;
	}
	public void setCyid(String cyid) {
		this.cyid = cyid;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getDid() {
		return did;
	}
	public void setDid(String did) {
		this.did = did;
	}
	public String getSc() {
		return sc;
	}
	public void setSc(String sc) {
		this.sc = sc;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public int getB2b_istate() {
		return b2b_istate;
	}
	public void setB2b_istate(int b2b_istate) {
		this.b2b_istate = b2b_istate;
	}
	public int getB2c_istate() {
		return b2c_istate;
	}
	public void setB2c_istate(int b2c_istate) {
		this.b2c_istate = b2c_istate;
	}
	public String getCpid() {
		return cpid;
	}
	public void setCpid(String cpid) {
		this.cpid = cpid;
	}
	public List<String> getB2b_cdids() {
		return b2b_cdids;
	}
	public void setB2b_cdids(List<String> b2b_cdids) {
		this.b2b_cdids = b2b_cdids;
	}
	public List<String> getB2c_cdids() {
		return b2c_cdids;
	}
	public void setB2c_cdids(List<String> b2c_cdids) {
		this.b2c_cdids = b2c_cdids;
	}
	public String getLineid() {
		return lineid;
	}
	public void setLineid(String lineid) {
		this.lineid = lineid;
	}
	public Long getB2b_opt() {
		return b2b_opt;
	}
	public void setB2b_opt(Long b2b_opt) {
		this.b2b_opt = b2b_opt;
	}
	public Long getB2c_opt() {
		return b2c_opt;
	}
	public void setB2c_opt(Long b2c_opt) {
		this.b2c_opt = b2c_opt;
	}
	public Long getCt() {
		return ct;
	}
	public void setCt(Long ct) {
		this.ct = ct;
	}
	public String getPdesc() {
		return pdesc;
	}
	public void setPdesc(String pdesc) {
		this.pdesc = pdesc;
	}
	public float getMsp() {
		return msp;
	}
	public void setMsp(float msp) {
		this.msp = msp;
	}
	public float getMaxsp() {
		return maxsp;
	}
	public void setMaxsp(float maxsp) {
		this.maxsp = maxsp;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getCountryImg() {
		return countryImg;
	}
	public void setCountryImg(String countryImg) {
		this.countryImg = countryImg;
	}
	public String getCyName() {
		return cyName;
	}
	public void setCyName(String cyName) {
		this.cyName = cyName;
	}
	public String getBondedZone() {
		return bondedZone;
	}
	public void setBondedZone(String bondedZone) {
		this.bondedZone = bondedZone;
	}
	public float getUnit_price() {
		return unit_price;
	}
	public void setUnit_price(float unit_price) {
		this.unit_price = unit_price;
	}
	public float getDomestic_price() {
		return domestic_price;
	}
	public void setDomestic_price(float domestic_price) {
		this.domestic_price = domestic_price;
	}
	public String getMoneySymbols() {
		return moneySymbols;
	}
	public void setMoneySymbols(String moneySymbols) {
		this.moneySymbols = moneySymbols;
	}
	public String getMoneyRate() {
		return moneyRate;
	}
	public void setMoneyRate(String moneyRate) {
		this.moneyRate = moneyRate;
	}
	public int getB2cSupply() {
		return b2cSupply;
	}
	public void setB2cSupply(int b2cSupply) {
		this.b2cSupply = b2cSupply;
	}
	public String getPromotion() {
		return promotion;
	}
	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}
	public String getImageAlt() {
		return imageAlt;
	}
	public void setImageAlt(String imageAlt) {
		this.imageAlt = imageAlt;
	}
	public String getB2b_dispat() {
		return b2b_dispat;
	}
	public void setB2b_dispat(String b2b_dispat) {
		this.b2b_dispat = b2b_dispat;
	}
	public int getSalesVolume() {
		return salesVolume;
	}
	public void setSalesVolume(int salesVolume) {
		this.salesVolume = salesVolume;
	}
	
	public float getPc_unit_price() {
		return pc_unit_price;
	}
	public void setPc_unit_price(float pc_unit_price) {
		this.pc_unit_price = pc_unit_price;
	}
	public float getWap_unit_price() {
		return wap_unit_price;
	}
	public void setWap_unit_price(float wap_unit_price) {
		this.wap_unit_price = wap_unit_price;
	}
	public float getApp_unit_price() {
		return app_unit_price;
	}
	public void setApp_unit_price(float app_unit_price) {
		this.app_unit_price = app_unit_price;
	}
	
	public int getIsStock() {
		return isStock;
	}
	public void setIsStock(int isStock) {
		this.isStock = isStock;
	}
	

	public Map<String, Float> getPromotion_price() {
		return promotion_price;
	}
	public void setPromotion_price(Map<String, Float> promotion_price) {
		this.promotion_price = promotion_price;
	}
	
	public List<String> getB2b_cdidNames() {
		return b2b_cdidNames;
	}
	public void setB2b_cdidNames(List<String> b2b_cdidNames) {
		this.b2b_cdidNames = b2b_cdidNames;
	}
	public List<String> getB2c_cdidNames() {
		return b2c_cdidNames;
	}
	public void setB2c_cdidNames(List<String> b2c_cdidNames) {
		this.b2c_cdidNames = b2c_cdidNames;
	}

	public List<String> getTagName() {
		return tagName;
	}
	public void setTagName(List<String> tagName) {
		this.tagName = tagName;
	}

	public float getCashHqj() {
		return cashHqj;
	}
	public void setCashHqj(float cashHqj) {
		this.cashHqj = cashHqj;
	}
	
	
	public String getProd_type() {
		return prod_type;
	}
	public void setProd_type(String prod_type) {
		this.prod_type = prod_type;
	}
	public List<String> getNormal() {
		return normal;
	}
	public void setNormal(List<String> normal) {
		this.normal = normal;
	}
	
	public List<String> getProduct_iconsTxT() {
		return product_iconsTxT;
	}
	public void setProduct_iconsTxT(List<String> product_iconsTxT) {
		this.product_iconsTxT = product_iconsTxT;
	}

	public float getDiscount() {
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	@Override
	public String toString() {
		return "DealerProInfo [pid=" + pid + ", b2b_pname=" + b2b_pname + ", b2c_pname=" + b2c_pname
				+ ", supplier_code=" + supplier_code + ", supplier_name=" + supplier_name + ", supplier_name_jc="
				+ supplier_name_jc + ", supplier_kf_tel=" + supplier_kf_tel + ", company_region=" + company_region
				+ ", supplier_logo_imgurl=" + supplier_logo_imgurl + ", cyid=" + cyid + ", sid=" + sid + ", did=" + did
				+ ", sc=" + sc + ", stock=" + stock + ", b2b_istate=" + b2b_istate + ", b2c_istate=" + b2c_istate
				+ ", cpid=" + cpid + ", b2b_cdids=" + b2b_cdids + ", b2c_cdids=" + b2c_cdids + ", b2b_cdidNames="
				+ b2b_cdidNames + ", b2c_cdidNames=" + b2c_cdidNames + ", lineid=" + lineid + ", b2b_opt=" + b2b_opt
				+ ", b2c_opt=" + b2c_opt + ", ct=" + ct + ", pdesc=" + pdesc + ", msp=" + msp + ", maxsp=" + maxsp
				+ ", imageUrl=" + imageUrl + ", brandId=" + brandId + ", brandName=" + brandName + ", brandNameFc="
				+ brandNameFc + ", countryImg=" + countryImg + ", cyName=" + cyName + ", bondedZone=" + bondedZone
				+ ", bestoay_price=" + bestoay_price + ", unit_price=" + unit_price + ", dealer_price=" + dealer_price
				+ ", retail_price=" + retail_price + ", domestic_price=" + domestic_price + ", prod_type=" + prod_type
				+ ", cashHqj=" + cashHqj + ", discount=" + discount + ", hqj=" + hqj + ", moneySymbols=" + moneySymbols
				+ ", moneyRate=" + moneyRate + ", b2cSupply=" + b2cSupply + ", promotion=" + promotion + ", imageAlt="
				+ imageAlt + ", b2b_dispat=" + b2b_dispat + ", salesVolume=" + salesVolume + ", pc_unit_price="
				+ pc_unit_price + ", wap_unit_price=" + wap_unit_price + ", app_unit_price=" + app_unit_price
				+ ", isStock=" + isStock + ", promotion_price=" + promotion_price + ", attrNameVal=" + attrNameVal
				+ ", tagName=" + tagName + ", cash=" + cash + ", family=" + family + ", normal=" + normal
				+ ", productType_code=" + productType_code + ", product_iconsTxT=" + product_iconsTxT + ", b2cProdDesc="
				+ b2cProdDesc + "]";
	}

	
	
	
	
	
	

}
