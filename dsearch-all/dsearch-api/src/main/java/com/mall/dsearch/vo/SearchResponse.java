package com.mall.dsearch.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;



/**
 * 返回结果
 * @author DOUBLELL
 *
 */
public class SearchResponse implements Serializable{
	
	private static final long serialVersionUID = -7578544787952401329L;
	
	/** 命中总数  **/
	private int hits;
	
	/** 能取到（翻页）的总数 **/
	private int numFetch;
	
    /** 本页包含的结果集 **/
    private List<PlainProduct> items = new ArrayList<PlainProduct>();
    
	/**
     * 属性--属性值
     */
    private Map<CatalogAttr,LinkedHashSet<CatalogAttrVal>> matrix = new HashMap<CatalogAttr, LinkedHashSet<CatalogAttrVal>>();
    /**
     * 已选属性值
     */
    private LinkedList<AttrTerm> terms = new LinkedList<AttrTerm>();
    /**
     * 类目
     */
    private List<CatalogNode> catalogs = new ArrayList<CatalogNode>();
    /**
     * 最有相关度的类目id
     */
    private String cdid = new String();
    /**
     * 相关搜索返回
     */
    private LinkedList<String> correlation = new LinkedList<String>();
    /**
     * 关于品牌首字母返回
     */
/*    Map<String, String> pinYinMatrix = new HashMap<String, String>();
	public Map<String, String> getPinYinMatrix() {
		return pinYinMatrix;
	}

	public void setPinYinMatrix(Map<String, String> pinYinMatrix) {
		this.pinYinMatrix = pinYinMatrix;
	}*/

	public LinkedList<String> getCorrelation() {
		return correlation;
	}

	public void setCorrelation(LinkedList<String> correlation) {
		this.correlation = correlation;
	}

	public String getCdid() {
		return cdid;
	}

	public void setCdid(String cdid) {
		this.cdid = cdid;
	}

	public List<CatalogNode> getCatalogs() {
		return catalogs;
	}

	public void setCatalogs(List<CatalogNode> catalogs) {
		this.catalogs = catalogs;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public int getNumFetch() {
		return numFetch;
	}

	public void setNumFetch(int numFetch) {
		this.numFetch = numFetch;
	}

	public List<PlainProduct> getItems() {
		return items;
	}

	public void setItems(List<PlainProduct> items) {
		this.items = items;
	}

	public Map<CatalogAttr, LinkedHashSet<CatalogAttrVal>> getMatrix() {
		return matrix;
	}

	public void setMatrix(Map<CatalogAttr, LinkedHashSet<CatalogAttrVal>> matrix) {
		this.matrix = matrix;
	}

	public LinkedList<AttrTerm> getTerms() {
		return terms;
	}

	public void setTerms(LinkedList<AttrTerm> terms) {
		this.terms = terms;
	}
}
