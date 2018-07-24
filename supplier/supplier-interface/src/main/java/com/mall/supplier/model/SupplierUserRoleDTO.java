package com.mall.supplier.model;

import java.io.Serializable;

/**
 * 
 * @author zhange
 *
 */
public class SupplierUserRoleDTO implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long linkId;
	
	private Long userId;
	
    private Long roleId;
    
	private String username;
	
	private String rolename;
	
	private String userpassword;    
	
    private Integer isAdmin;
	
	public Integer getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}
	public String getUserpassword() {
		return userpassword;
	}
	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}
	public SupplierUserRoleDTO() {
		super();
	}
	public SupplierUserRoleDTO(String username, String rolename) {
		super();
		this.username = username;
		this.rolename = rolename;
	}
	public SupplierUserRoleDTO(Long linkId, Long userId, Long roleId,
			String username, String rolename) {
		super();
		this.linkId = linkId;
		this.userId = userId;
		this.roleId = roleId;
		this.username = username;
		this.rolename = rolename;
	}
	
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	
    
}