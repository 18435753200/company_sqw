package com.mall.supplier.model;

import java.io.Serializable;
import java.util.Date;

public class SupplierBrandDTO implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -372890796256233907L;

	/**
     * 
     */
    private Long brandId;

    /**
     * .供应商ID
     */
    private Long supplierId;
    
    /**
     * .子供应商ID
     */
    private Long subSupplierId;

    /**
     * .供应商名称
     */
    private String supplierName;
    /**
     * .子供应商名称
     */
    private String subSupplierName;
    /**
     * 品牌名称
     */
    private String name;

    /**
     * 0:普通代理；1:独家代理；2:自主品牌
     */
    private Integer type;

    /**
     * 系统品牌id
     */
    private Long systemBrandId;

    /**
     * 品牌描述
     */
    private String description;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 
     */
    private Date lastModifyTime;

    /**
     * 
     */
    private String lastModifyBy;

    /**
     * 审核意见
     */
    private String remark;

    /**
     * 审核时间
     */
    private Date auditTime;

    /**
     * 0:不可用；1:正常；2:审核不通过；3:删除
     */
    private Integer status;
	/**
     * 资质文件
     */
    private String qualification;
    /**
     * 创建时间查询开始.
     */
    private Date startTime;
    /**
     * 创建时间查询结束.
     */
    private Date endTime;

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }
    
    public Long getSubSupplierId() {
		return subSupplierId;
	}

	public void setSubSupplierId(Long subSupplierId) {
		this.subSupplierId = subSupplierId;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getSystemBrandId() {
        return systemBrandId;
    }

    public void setSystemBrandId(Long systemBrandId) {
        this.systemBrandId = systemBrandId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public String getLastModifyBy() {
        return lastModifyBy;
    }

    public void setLastModifyBy(String lastModifyBy) {
        this.lastModifyBy = lastModifyBy == null ? null : lastModifyBy.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
    public String getSubSupplierName() {
		return subSupplierName;
	}

	public void setSubSupplierName(String subSupplierName) {
		this.subSupplierName = subSupplierName;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

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
}