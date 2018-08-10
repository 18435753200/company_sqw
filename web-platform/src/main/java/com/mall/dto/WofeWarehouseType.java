package com.mall.dto;

public enum WofeWarehouseType {
	beijing("北京仓","WMWHSE1"), milan("米兰仓","WMWHSE2");
	
	private WofeWarehouseType(String id, String name) {
		this.id = id;
		this.name = name;
	}
	private String id;
	private String name;
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
}
