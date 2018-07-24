package com.mall.supplier.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.mall.supplier.model.Supplier;

public class EasyUIPageOrder implements Serializable{
	
	/*{"total":3156,rows:[{},{}]}*/
	//定义返回总计录数
	private Integer startpage;
	private Integer endpage;
	private Date startTime;
    private Date endTime;
    private String companyQy;
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getCompanyQy() {
		return companyQy;
	}
	public void setCompanyQy(String companyQy) {
		this.companyQy = companyQy;
	}
	private Integer total;
	private List<Supplier> rows;
	
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

	public List<Supplier> getRows() {
		return rows;
	}
	public void setRows(List<Supplier> rows) {
		this.rows = rows;
	}
	@Override
	public String toString() {
		return "EasyUIPage [total=" + total + ", rows=" + rows + "]";
	}
	
}
