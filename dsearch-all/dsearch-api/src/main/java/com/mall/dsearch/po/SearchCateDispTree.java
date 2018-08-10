package com.mall.dsearch.po;

import java.io.Serializable;

public class SearchCateDispTree implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String cate_disp_id;// 展示类目ID
	private String cate_pub_id;// 发布类目ID
	private String cate_name;// 展示类目名称
	private String cate_pids;// crosslink展示类目ID
	private String phy;//
	private String root_cid;// 根类目ID
	private String cateType;

	public String getCatePubId() {
		return cate_pub_id;
	}

	public void setCatePubId(String catePubId) {
		this.cate_pub_id = catePubId;
	}

	/*
	 * public Long getId() { return id; }
	 * 
	 * public void setId(Long id) { this.id = id; }
	 */

	public String getCateDispId() {
		return cate_disp_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setCateDispId(String cateDispId) {
		this.cate_disp_id = cateDispId == null ? null : cateDispId.trim();
	}

	public String getCateName() {
		return cate_name;
	}

	public void setCateName(String cateName) {
		this.cate_name = cateName == null ? null : cateName.trim();
	}

	public String getCatePids() {
		return cate_pids;
	}

	public void setCatePids(String catePids) {
		this.cate_pids = catePids == null ? null : catePids.trim();
	}

	public String getPhy() {
		return phy;
	}

	public void setPhy(String phy) {
		this.phy = phy;
	}

	public String getRootCid() {
		return root_cid;
	}

	public void setRootCid(String rootCid) {
		this.root_cid = rootCid;
	}

	public String getCateType() {
		return cateType;
	}

	public void setCateType(String cateType) {
		this.cateType = cateType;
	}

	/*
	 * public Byte getPhy() { return phy; }
	 * 
	 * public void setPhy(Byte phy) { this.phy = phy; }
	 * 
	 * public Byte getRootCid() { return rootCid; }
	 */

}