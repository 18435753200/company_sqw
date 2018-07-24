package com.mall.supplier.model;

import java.io.Serializable;

public class SupplierUserRole implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -104243704813197453L;

	private Long linkId;

    private Long userId;

    private Long roleId;

    public Long getLinkId() {
        return linkId;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}