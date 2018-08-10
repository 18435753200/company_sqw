package com.mall.dsearch.vo;


/**
 * 查询词搜索字段
 *
 */ 
public  enum SearchScope{
	PNAME(new String[]{"pname"}), //标题
	PNAME_PID(new String[]{"pname","pid"}), //标题
	PNAME_PDESC(new String[]{"pname","pdesc"}), //标题/短描 
	
	PNAME_PDESC_PKEYS(new String[]{"pname","brandName","subBrandName"}) ;//标题/短描/关键词
	
	private String[] scope;
	
	private SearchScope( String[] scope ){
		this.scope=scope;
	}

	public String[] getScope() {
		return scope;
	}

	public void setScope(String[] scope) {
		this.scope = scope;
	}
}
