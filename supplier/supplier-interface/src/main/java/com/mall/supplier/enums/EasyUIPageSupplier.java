package com.mall.supplier.enums;

import java.io.Serializable;
import java.util.List;

import com.mall.supplier.model.Supplier;
import com.mall.supplier.model.Suppliernew;

public class EasyUIPageSupplier implements Serializable{
	
	/*{"total":3156,rows:[{},{}]}*/
	//定义返回总计录数
	private Integer startpage;
	private Integer endpage;
	private String supplier_code;
	private String name;
	private Integer status;
	private String userid;
	private String userMobile;
	public String getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	private Integer total;
	private List<Suppliernew> rows;


	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getSupplier_code() {
		return supplier_code;
	}
	public void setSupplier_code(String supplier_code) {
		this.supplier_code = supplier_code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStartpage() {
		return startpage;
	}
	public void setStartpage(Integer startpage) {
		this.startpage = startpage;
	}
	public Integer getEndpage() {
		return endpage;
	}
	public void setEndpage(Integer endpage) {
		this.endpage = endpage;
	}
	
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<Suppliernew> getRows() {
		return rows;
	}
	public void setRows(List<Suppliernew> rows) {
		this.rows = rows;
	}
	@Override
	public String toString() {
		return "EasyUIPage [total=" + total + ", rows=" + rows + "]";
	}
	
}
