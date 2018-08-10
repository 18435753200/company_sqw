package com.mall.dsearch.vo;

import java.io.Serializable;

/**
 * 
 * @author 张聪聪
 *
 */
public class SearchSupplierRequest implements Serializable {

	private static final long serialVersionUID = -8942590072529299833L;
	/**
	 * 请求关键词
	 */
	private String keyWord;

	/**
	 * 当前页号，从1开始
	 */
	private Integer pageNo = 1;
	/**
	 * 每页显示的个数
	 */
	private int pageSize = 10;
	
	/**
	 * 排序类型,默认按照相关度排序
	 */
	private Integer sortType = 0;
	
	/**
	 * 省id
	 * @return
	 */
	private Integer provinceId;
	
	
	/**
	 * 省name
	 * @return
	 */
	private String provinceName;
	
	/**
	 * 市id
	 * @return
	 */
	private Integer cityId;
	
	/**
	 * 市Name
	 * @return
	 */
	private String cityName;
	
	/**
	 * 区县id
	 * @return
	 */
	private Integer countryId;
	
	/**
	 * 区县Name
	 * @return
	 */
	private String countryName;
	
	/**
	 * 类目code  
	 * 最好不要使用code查询,因为code上边还分 (企业 ,个体工商户 ,事业单位)三种,当传递的是code值时,就会隐形的加上某个条件,
	 * 这时分组起的作用就变了
	 * @return
	 */
	private Integer sectorCode;
	
	
	/**
	 * 类目Name
	 * @return
	 */
	private String sectorCodeName;
	
	/**
	 * 商家类型
	 * @return
	 */
	private Integer organizationType=1;

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getSortType() {
		return sortType;
	}

	public void setSortType(Integer sortType) {
		this.sortType = sortType;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public Integer getSectorCode() {
		return sectorCode;
	}

	public void setSectorCode(Integer sectorCode) {
		this.sectorCode = sectorCode;
	}

	public String getSectorCodeName() {
		return sectorCodeName;
	}

	public void setSectorCodeName(String sectorCodeName) {
		this.sectorCodeName = sectorCodeName;
	}

	public Integer getOrganizationType() {
		return organizationType;
	}

	public void setOrganizationType(Integer organizationType) {
		this.organizationType = organizationType;
	}
	
	
	
	
	
	
	





}
