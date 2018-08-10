package com.mall.vo;

import com.mall.customer.dto.SubFirstUserDto;
import com.mall.customer.dto.UserGroupDto;
import com.mall.mybatis.utility.PageBean;
import com.mall.supplier.dto.CompanyDto;

public class TeamVO {

	// 我的推荐人
	private UserGroupDto userGroupDto;

	// 我的推荐企业列表
	private PageBean<CompanyDto> supplierList;

	// 下级一级推荐人
	private PageBean<SubFirstUserDto> subFirstUserList;

	public UserGroupDto getUserGroupDto() {
		return userGroupDto;
	}

	public void setUserGroupDto(UserGroupDto userGroupDto) {
		this.userGroupDto = userGroupDto;
	}

	public PageBean<CompanyDto> getSupplierList() {
		return supplierList;
	}

	public void setSupplierList(PageBean<CompanyDto> supplierList) {
		this.supplierList = supplierList;
	}

	public PageBean<SubFirstUserDto> getSubFirstUserList() {
		return subFirstUserList;
	}

	public void setSubFirstUserList(PageBean<SubFirstUserDto> subFirstUserList) {
		this.subFirstUserList = subFirstUserList;
	}

}
