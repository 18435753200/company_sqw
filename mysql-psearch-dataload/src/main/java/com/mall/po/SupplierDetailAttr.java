package com.mall.po;

import java.io.Serializable;

public class SupplierDetailAttr implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3689564433533224987L;

	private Integer id;
	
	private Integer storeId;
	//1,经营环境 2,门店推荐(用于区分属于那种照片类型)
	private Integer attrType;
	//名称(相当于给图片设置一个名字,类似于html中的title)
	private String  attrName;
	//数据值(就是图片的url)
	private String attrValue;
	//数据说明(对于图片的详细说明)
	private String memo;
	//详细外链url 链接地址(就是这个图片可以对应一个路径,例如推荐照片点击可以跳转到该照片对应的商品)
	private String url;
	//排序
	private Integer sort;
	
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public Integer getAttrType() {
		return attrType;
	}

	public void setAttrType(Integer attrType) {
		this.attrType = attrType;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getAttrValue() {
		return attrValue;
	}

	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "SupplierDetailAttr [id=" + id + ", storeId=" + storeId + ", attrType=" + attrType + ", attrName="
				+ attrName + ", attrValue=" + attrValue + ", memo=" + memo + ", url=" + url + ", sort=" + sort
				+ ", status=" + status + "]";
	}
	
	
}
