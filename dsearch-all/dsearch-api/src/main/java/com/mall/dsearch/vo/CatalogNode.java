package com.mall.dsearch.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CatalogNode implements Serializable,Comparable<CatalogNode>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6062168364193335150L;
	private String id;
	private String name;//类目名称
	private int count;
	private CatalogNode parent;//父类目
	private List<CatalogNode> children = new ArrayList<CatalogNode>();//子类目集合
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
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<CatalogNode> getChildren() {
		return children;
	}
	public void setChildren(List<CatalogNode> children) {
		this.children = children;
	}
	public CatalogNode getParent() {
		return parent;
	}
	public void setParent(CatalogNode parent) {
		this.parent = parent;
	}
	@Override
	public int compareTo(CatalogNode o) {
		return o.getCount() - this.getCount();
	}

}
