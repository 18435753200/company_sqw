package com.mall.dsearch.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author grady.song
 *
 */
public class Catalog implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2393233820634888757L;
	private String id;//展示类目ID
	private String name;//展示类目名称
	private boolean phy;//
	private boolean root;//是否为根
	private String pid;//发布类目ID
	private List<Catalog> parents = new ArrayList<Catalog>();
	private List<Catalog> children = new ArrayList<Catalog>();
	
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
	
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Catalog( String id ){
		this.id=id;
	}
	
	public Catalog( String id,String name ){
		this.id=id;
		this.name=name;
	}
	
	public Catalog( String id,String name,boolean phy ){
		this.id=id;
		this.name=name;
		this.phy=phy;
	}
	
	public Catalog( String id,String name,Catalog parent ){
		this.id=id;
		this.name=name;
		this.parents.add(parent);
	}
	public List<Catalog> getParents() {
		return parents;
	}
	public void setParents(List<Catalog> parents) {
		this.parents = parents;
	}
	public List<Catalog> getChildren() {
		return children;
	}
	public void setChildren(List<Catalog> children) {
		this.children = children;
	}

	public boolean isPhy() {
		return phy;
	}

	public void setPhy(boolean phy) {
		this.phy = phy;
	}

	public boolean isRoot() {
		return root;
	}

	public void setRoot(boolean root) {
		this.root = root;
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
		Catalog other = (Catalog) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
