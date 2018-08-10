package com.mall.dsearch.vo;

import java.io.Serializable;

//import org.apache.solr.client.solrj.beans.Field; 
public class SearchCateAttrValCode implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1144741360756064373L;
//@Field("id")
	private String id;
//@Field("cate_disp_id")
private String cate_disp_id;//展示类目ID
//@Field("attr_val_code_id")
    private String attr_val_code_id;//属性属性值ID
//@Field("attr_name")
    private String attr_name;//属性名称
//@Field("attrval_name")
    private String attrval_name;//属性值名称
//@Field("attr_sortval")
    private Integer attr_sortval;//属性排序
//@Field("attrval_sortval")
    private Integer attrval_sortval;//属性值排序
//@Field("brandvalid")
    private String brandvalid;//valid
//@Field("pic_url")
    private String pic_url;
    
   // private String dispat;//属性属性值ID集合

    public String getCateDispId() {
        return cate_disp_id;
    }

    public void setCateDispId(String cateDispId) {
        this.cate_disp_id = cateDispId == null ? null : cateDispId.trim();
    }

    public String getAttrName() {
        return attr_name;
    }

    public void setAttrName(String attrName) {
        this.attr_name = attrName == null ? null : attrName.trim();
    }

    public String getAttrvalName() {
        return attrval_name;
    }

    public void setAttrvalName(String attrvalName) {
        this.attrval_name = attrvalName == null ? null : attrvalName.trim();
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAttrValCodeId() {
		return attr_val_code_id;
	}

	public void setAttrValCodeId(String attrValCodeId) {
		this.attr_val_code_id = attrValCodeId;
	}

	public Integer getAttrSortval() {
		return attr_sortval;
	}

	public void setAttrSortval(Integer attrSortval) {
		this.attr_sortval = attrSortval;
	}

	public Integer getAttrvalSortval() {
		return attrval_sortval;
	}

	public void setAttrvalSortval(Integer attrvalSortval) {
		this.attrval_sortval = attrvalSortval;
	}

/*	public String getDispat() {
		return dispat;
	}

	public void setDispat(String dispat) {
		this.dispat = dispat;
	}*/

	public String getBrandvalid() {
		return brandvalid;
	}

	public void setBrandvalid(String brandvalid) {
		this.brandvalid = brandvalid;
	}

	public String getPicUrl() {
        return pic_url;
    }

    public void setPicUrl(String picUrl) {
        this.pic_url = picUrl == null ? null : picUrl.trim();
    }
}