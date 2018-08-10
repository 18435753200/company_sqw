package com.mall.dsearch.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
/**
 * 商品概要属性
 * @author DOUBLELL
 *
 */
public class PlainProduct implements Serializable{

	private static final long serialVersionUID = 5824468317741299992L;
	private String pid;
	private String pname;
	private String highlightedPname;//高亮名字
	private String b2cPname;//b2c商品名称
	private Integer inventory;//库存
	private String brandName;//品牌名称
	private String subBrandName;//子品牌名称
	private String cyid;//源产地
	private Float msp;	//最小销售价
	private Float maxsp;	//最大销售价
	private String imageurl; //主图
	private String leadingtime;//备货期
	private String cdid;
	private String measureName;//单位：如件，箱
	private boolean isXinventory;//是否有现货库存
	private String exchange;//货币转换
	private BigDecimal unit_price;//真实价格
	private BigDecimal domestic_price;//参考价格
	private String cyName;//国家名
	private String countryImg;//源产国的图片路径
	private String bondedZone;//保税区(之前用，现在改为proStyleDescribe来展现)
	private String moneySymbols;//货币符号
	private String moneyRate;//货币汇率
	private int b2csupply;//B2C商品类型
	private String proStyleDescribe;//B2C商品类型描述
	private String  promotion;//促销类型
	private String b2cCdid;
	private String imageAlt;
	private String supplierId;//企业Id
	private String supplierCode;//企业代码
	private String supplierName;//企业名称
	private String supplierNameJc;//企业简称
	private String supplierKfTel;//客服电话
	private String companyRegion;//入驻区域
	private String supplierLogoImgurl;//企业logo
	
	private int proSales;//销量
	private BigDecimal promotion_price;//促销价
	private BigDecimal cash_Hqj;
	private BigDecimal hqj;
	private String prod_type;
	private List<String> productType_code;
	private List<String> product_iconsTxT;
	private List<String> tagName;
	
	public BigDecimal getHqj() {
		return hqj;
	}
	public void setHqj(BigDecimal hqj) {
		this.hqj = hqj;
	}
	private boolean boolPromotion;// 是否有促销
	/*	private String minWholesaleQty;
	private String merchantName;*/
	
	
	public String getPid() {
		return pid;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getSupplierNameJc() {
		return supplierNameJc;
	}
	public void setSupplierNameJc(String supplierNameJc) {
		this.supplierNameJc = supplierNameJc;
	}
	public String getSupplierKfTel() {
		return supplierKfTel;
	}
	public void setSupplierKfTel(String supplierKfTel) {
		this.supplierKfTel = supplierKfTel;
	}
	public String getCompanyRegion() {
		return companyRegion;
	}
	public void setCompanyRegion(String companyRegion) {
		this.companyRegion = companyRegion;
	}
	public String getSupplierLogoImgurl() {
		return supplierLogoImgurl;
	}
	public void setSupplierLogoImgurl(String supplierLogoImgurl) {
		this.supplierLogoImgurl = supplierLogoImgurl;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getHighlightedPname() {
		return highlightedPname;
	}
	public void setHighlightedPname(String highlightedPname) {
		this.highlightedPname = highlightedPname;
	}
	public String getB2cPname() {
		return b2cPname;
	}
	public void setB2cPname(String b2cPname) {
		this.b2cPname = b2cPname;
	}
	public Integer getInventory() {
		return inventory;
	}
	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getSubBrandName() {
		return subBrandName;
	}
	public void setSubBrandName(String subBrandName) {
		this.subBrandName = subBrandName;
	}
	public String getCyid() {
		return cyid;
	}
	public void setCyid(String cyid) {
		this.cyid = cyid;
	}
	public Float getMsp() {
		return msp;
	}
	public void setMsp(Float msp) {
		this.msp = msp;
	}
	public Float getMaxsp() {
		return maxsp;
	}
	public void setMaxsp(Float maxsp) {
		this.maxsp = maxsp;
	}
	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	public String getLeadingtime() {
		return leadingtime;
	}
	public void setLeadingtime(String leadingtime) {
		this.leadingtime = leadingtime;
	}
	public String getCdid() {
		return cdid;
	}
	public void setCdid(String cdid) {
		this.cdid = cdid;
	}
	public String getMeasureName() {
		return measureName;
	}
	public void setMeasureName(String measureName) {
		this.measureName = measureName;
	}
	public boolean isXinventory() {
		return isXinventory;
	}
	public void setXinventory(boolean isXinventory) {
		this.isXinventory = isXinventory;
	}
	public String getExchange() {
		return exchange;
	}
	public void setExchange(String exchange) {
		this.exchange = exchange;
	}
	public BigDecimal getUnit_price() {
		return unit_price;
	}
	public void setUnit_price(BigDecimal unit_price) {
		this.unit_price = unit_price;
	}
	public BigDecimal getDomestic_price() {
		return domestic_price;
	}
	public void setDomestic_price(BigDecimal domestic_price) {
		this.domestic_price = domestic_price;
	}
	public String getCyName() {
		return cyName;
	}
	public void setCyName(String cyName) {
		this.cyName = cyName;
	}
	public String getCountryImg() {
		return countryImg;
	}
	public void setCountryImg(String countryImg) {
		this.countryImg = countryImg;
	}
	public String getBondedZone() {
		return bondedZone;
	}
	public void setBondedZone(String bondedZone) {
		this.bondedZone = bondedZone;
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
	public int getB2csupply() {
		return b2csupply;
	}
	public void setB2csupply(int b2csupply) {
		this.b2csupply = b2csupply;
	}
	public String getProStyleDescribe() {
		return proStyleDescribe;
	}
	public void setProStyleDescribe(String proStyleDescribe) {
		this.proStyleDescribe = proStyleDescribe;
	}
	public String getPromotion() {
		return promotion;
	}
	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}
	public String getB2cCdid() {
		return b2cCdid;
	}
	public void setB2cCdid(String b2cCdid) {
		this.b2cCdid = b2cCdid;
	}
	public String getImageAlt() {
		return imageAlt;
	}
	public void setImageAlt(String imageAlt) {
		this.imageAlt = imageAlt;
	}
	public int getProSales() {
		return proSales;
	}
	public void setProSales(int proSales) {
		this.proSales = proSales;
	}
	public BigDecimal getPromotion_price() {
		return promotion_price;
	}
	public void setPromotion_price(BigDecimal promotion_price) {
		this.promotion_price = promotion_price;
	}
	public boolean isBoolPromotion() {
		return boolPromotion;
	}
	public void setBoolPromotion(boolean boolPromotion) {
		this.boolPromotion = boolPromotion;
	}
	public BigDecimal getCash_Hqj() {
		return cash_Hqj;
	}
	public void setCash_Hqj(BigDecimal cash_Hqj) {
		this.cash_Hqj = cash_Hqj;
	}
	public String getProd_type() {
		return prod_type;
	}
	public void setProd_type(String prod_type) {
		this.prod_type = prod_type;
	}
	public List<String> getProductType_code() {
		return productType_code;
	}
	public void setProductType_code(List<String> productType_code) {
		this.productType_code = productType_code;
	}
	public List<String> getProduct_iconsTxT() {
		return product_iconsTxT;
	}
	public void setProduct_iconsTxT(List<String> product_iconsTxT) {
		this.product_iconsTxT = product_iconsTxT;
	}
	public List<String> getTagName() {
		return tagName;
	}
	public void setTagName(List<String> tagName) {
		this.tagName = tagName;
	}
	
}
