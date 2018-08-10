package com.mall.dsearch.vo;

import java.io.Serializable;

public class CatalogAttrVal implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3407968552248916210L;
	private String id;
	private String name;
	private int sortval;//排序
	private int count;//数量
	private String imgUrl;//源产国图片
	
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSortval() {
		return sortval;
	}

	public void setSortval(int sortval) {
		this.sortval = sortval;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CatalogAttrVal other = (CatalogAttrVal) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
