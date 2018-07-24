package com.mall.supplier.model;

import java.io.Serializable;

public class SupplierRolePopedom  implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7333760160292990656L;

	private Long linkId;

    private Long roleId;

    private Long menuId;

    public Long getLinkId() {
        return linkId;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
}