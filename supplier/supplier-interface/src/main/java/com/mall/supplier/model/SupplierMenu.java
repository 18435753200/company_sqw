package com.mall.supplier.model;

import java.io.Serializable;
import java.util.Date;

public class SupplierMenu implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1067645809995946553L;

	private Long menuId;

    private Long supplierId;

    private String menuCode;

    private String name;

    private Integer type;

    private Integer parentMenuId;

    private Integer menuOwner;

    private String exeName;

    private String spaces;

    private Integer exeAttr;

    private String description;

    private String url;

    private Integer sort;

    private Date createTime;

    private String createBy;

    private Date lastModifyTime;

    private Integer lastModifyBy;

    private Integer status;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode == null ? null : menuCode.trim();
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

    public Integer getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(Integer parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    public Integer getMenuOwner() {
        return menuOwner;
    }

    public void setMenuOwner(Integer menuOwner) {
        this.menuOwner = menuOwner;
    }

    public String getExeName() {
        return exeName;
    }

    public void setExeName(String exeName) {
        this.exeName = exeName == null ? null : exeName.trim();
    }

    public String getSpaces() {
        return spaces;
    }

    public void setSpaces(String spaces) {
        this.spaces = spaces == null ? null : spaces.trim();
    }

    public Integer getExeAttr() {
        return exeAttr;
    }

    public void setExeAttr(Integer exeAttr) {
        this.exeAttr = exeAttr;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
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