package com.mall.supplier.model;

import java.io.Serializable;
import java.util.Date;

public class SupplierRole implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8342835362735267207L;

	private Long roleId;

    private Long supplierId;

    private String name;

    private Integer type;

    private Integer isAdminCreate;

    private String createBy;

    private Date lastModifyTime;

    private Integer lastModifyBy;

    private Integer status;
    
    

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
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

    public Integer getIsAdminCreate() {
        return isAdminCreate;
    }

    public void setIsAdminCreate(Integer isAdminCreate) {
        this.isAdminCreate = isAdminCreate;
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

    public Integer getLastModifyBy() {
        return lastModifyBy;
    }

    public void setLastModifyBy(Integer lastModifyBy) {
        this.lastModifyBy = lastModifyBy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}