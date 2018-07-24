package com.mall.supplier.model;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Date;

public class SupplierStore implements Serializable{

	/**
	 * POP 供应商第三方
	 * model 地址封装
	 */
	private static final long serialVersionUID = -6078553200774960865L;
	
	private int id;
	
	//供应商Id
	private int supplierId;
	
	//店铺名称
	private String storeName;
	
	//模版类型，用户选择的那个模版
	private int templateType;
	
	//发布线上的店铺模版内容
	private String onlineContent;
	
	//预览店铺模版内容
	private String previewContent;
	
	//创建人,登录帐号Id，supplier_user表ID
	private int createBy;
	
	//创建时间
	private Date createTime;
	
	//修改时间
	private Date updateTime;
	
	//修改人,登录帐号Id，supplier_user表ID
	private int updateBy;

	// 店铺访问地址
	private String storeUrl;

	// 一键代发店铺
	private Integer isOnekeySend;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public int getTemplateType() {
		return templateType;
	}

	public void setTemplateType(int templateType) {
		this.templateType = templateType;
	}

	
	
	public String getOnlineContent() {
		return onlineContent;
	}

	public void setOnlineContent(String onlineContent) {
		this.onlineContent = onlineContent;
	}

	public String getPreviewContent() {
		return previewContent;
	}

	public void setPreviewContent(String previewContent) {
		this.previewContent = previewContent;
	}

	public int getCreateBy() {
		return createBy;
	}

	public void setCreateBy(int createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(int updateBy) {
		this.updateBy = updateBy;
	}

	public String getStoreUrl() {
		return "/store/" + this.getSupplierId() + "/" + this.getTemplateType() + "/index.html";
	}

	public void setStoreUrl(String storeUrl) {
		this.storeUrl = storeUrl;
	}

	public Integer getIsOnekeySend() {
		return isOnekeySend;
	}

	public void setIsOnekeySend(Integer isOnekeySend) {
		this.isOnekeySend = isOnekeySend;
	}
}
