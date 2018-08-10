package com.mall.search.vo;

public class TdAttrValCode {
	private Long id;

	private String attrname;

	private String attrvalname;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAttrname() {
		return attrname;
	}

	public void setAttrname(String attrname) {
		this.attrname = attrname == null ? null : attrname.trim();
	}

	public String getAttrvalname() {
		return attrvalname;
	}

	public void setAttrvalname(String attrvalname) {
		this.attrvalname = attrvalname == null ? null : attrvalname.trim();
	}
}