package com.mall.supplier.model;

import java.io.Serializable;
import java.util.Date;

/**
 *<p>操作log日志</p>
 * @author zw
 * @date 2016年11月29日
 */
public class SupplierOperateLog implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8342835362735267207L;

	private Long id;
	/**
	 * 操作人
	 */
	private String operator;
	/**
	 * 操作人IP
	 */
	private String ip;
	/**
	 * 操作内容
	 */
	private String operatorContent;
	/**
	 * 功能描述
	 */
	private String description;
	/**
	 * 操作时间
	 */
	private Date createTime;

	public SupplierOperateLog() {
		super();
		this.createTime = new Date();
	}

	public SupplierOperateLog(Long id, String operator, String ip,
			String operatorContent, String description, Date createTime) {
		super();
		this.id = id;
		this.operator = operator;
		this.ip = ip;
		this.operatorContent = operatorContent;
		this.description = description;
		this.createTime = createTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getOperatorContent() {
		return operatorContent;
	}

	public void setOperatorContent(String operatorContent) {
		this.operatorContent = operatorContent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}