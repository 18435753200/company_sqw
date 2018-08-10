package com.mall.dsearch.vo;

public enum CatalogLevel {

	//一级类目
	C1("c1"),
	//二级类目
	C2("c2"),
	//三级类目
	C3("c3"),
	//四级类目
	C4("c4"),
	//最后一级类目
	LC("lc"),
	
	
	//一级类目
	B2cC1("b2cC1"),
	//二级类目
	B2cC2("b2cC2"),
	//三级类目
	B2cC3("b2cC3"),
	//四级类目
	B2cC4("b2cC4"),
	//最后一级类目
	B2cLC("b2cLC");
	private String level;
	private CatalogLevel(String level) {
		this.level = level;
	}
	public String getLevel() {
		return level;
	}
	
}
