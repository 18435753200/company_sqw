package com.mall.supplier.dao;

import java.util.List;
import java.util.Map;

import com.mall.mybatis.utility.PageBean;
import com.mall.supplier.model.SupplierUser;
import com.mall.supplier.model.SupplierUserRoleDTO;


public interface SupplierUserMapper {
	
    int deleteByPrimaryKey(Long userId);

    long insert(SupplierUser record);

    int insertSelective(SupplierUser record);

    SupplierUser selectByPrimaryKey(Long userId);
    
    SupplierUserRoleDTO selectUserRoleDTOByUserId(Long userId);
    
    int updateByPrimaryKeySelective(SupplierUser record);

    int updateByPrimaryKey(SupplierUser record);
    
    SupplierUser login(Map<String ,String> map);
    
    int countUserByName(Map<String ,String> map);
    
    List<SupplierUserRoleDTO> findUsersBySupplierId(Long supplierId);
    
    int deleteAll(List<Long> list);
    
    int updateAdminUserByMerchantId(SupplierUser record);
    
    int deleteAdminUserByMerchantId(Long supplierId);

	List<SupplierUser> getPageList(PageBean<SupplierUser> pageBean);

	SupplierUser selectUserByName(String loginName);

	SupplierUser findAdminUserByMerchantId(Long supplierId);
	
	@Deprecated
	List<SupplierUserRoleDTO> findSubSuppliersBySupplierId(Long supplierId);
	
	int isSubSupplier(Long userId);

	SupplierUser getIsAdminUserBySupplierId(Long supplierId);

	void updateSupplier(int parseInt);

	void frozenSupplier(int parseInt);
}