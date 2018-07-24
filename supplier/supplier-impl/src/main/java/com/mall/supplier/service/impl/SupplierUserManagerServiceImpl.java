package com.mall.supplier.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.mybatis.utility.PageBean;
import com.mall.supplier.dao.SupplierMapper;
import com.mall.supplier.dao.SupplierUserMapper;
import com.mall.supplier.dao.SupplierUserRoleMapper;
import com.mall.supplier.enums.EasyUIPageSupplier;
import com.mall.supplier.model.Supplier;
import com.mall.supplier.model.SupplierUser;
import com.mall.supplier.model.SupplierUserRoleDTO;
import com.mall.supplier.model.Suppliernew;
import com.mall.supplier.service.SupplierUserManagerService;

import javassist.compiler.ast.NewExpr;

@Service
@Transactional
public class SupplierUserManagerServiceImpl implements SupplierUserManagerService {
	
	@Autowired
	SupplierMapper supplierMapper;
	
	@Autowired
    SupplierUserMapper supplierUserMapper;
	
	@Autowired
    SupplierUserRoleMapper supplierUserRolemapper;
	
	public long add(SupplierUser user) {
		supplierUserMapper.insert(user);
		return user.getUserId();
	}

	public SupplierUser login(Map<String,String> map ) {
		return  supplierUserMapper.login(map);
	}
	public SupplierUser getUserById(Long  id) {
		return supplierUserMapper.selectByPrimaryKey(id);
	}

	public int update(SupplierUser user) {
		return supplierUserMapper.updateByPrimaryKeySelective(user);
	}

	/**
	 * 获取分页数据列表
	 * @param pmap 封装好的包含user page对象的查询参数
	 * @return
	 */
	public PageBean<SupplierUser> getPageList(PageBean<SupplierUser> pageBean) {
		List<SupplierUser> list = new ArrayList<SupplierUser>();
		list =  supplierUserMapper.getPageList(pageBean);
		pageBean.setResult(list);
		return pageBean;
	}
	/**
	 * 验证用户名个数
	 * @return
	 */
	public int getUserByName(Map<String,String> pmap){
		return supplierUserMapper.countUserByName(pmap);
	}
	public SupplierUser getUserByName(String name){
		return supplierUserMapper.selectUserByName(name);
	}
	public int delete(Long id) {
		supplierUserRolemapper.deleteByUserId(id);
		return supplierUserMapper.deleteByPrimaryKey(id);
	}
	public List<SupplierUserRoleDTO> findUsersBySupplierId(Long supplierId) {
		return supplierUserMapper.findUsersBySupplierId(supplierId);
	}
	public int deleteAll(List<Long> ids) {
		supplierUserRolemapper.deleteAllByUserId(ids);
		return supplierUserMapper.deleteAll(ids);
	}
	
	public  List<SupplierUserRoleDTO> findSubSuppliersBySupplierId(Long supplierId){
		return supplierUserMapper.findSubSuppliersBySupplierId(supplierId);
	};
	
	public boolean isSubSupplier(Long userId){
		return supplierUserMapper.isSubSupplier(userId)>0;
	}

	public SupplierUser getIsAdminUserBySupplierId(Long supplierId) {
		return supplierUserMapper.getIsAdminUserBySupplierId(supplierId);
	}

	@Override
	public EasyUIPageSupplier querySupplier(Suppliernew supplier, Integer page, Integer rows) {
		EasyUIPageSupplier easyUIPage = new EasyUIPageSupplier();
		easyUIPage.setStartpage(page);
		easyUIPage.setEndpage(rows);
		easyUIPage.setSupplier_code(supplier.getSupplier_code());
		easyUIPage.setName(supplier.getName());
		easyUIPage.setStatus(supplier.getStatus());
		easyUIPage.setUserid(supplier.getUserid());
		easyUIPage.setUserMobile(supplier.getUserMobile());
		int total = supplierMapper.countByExample(easyUIPage);
		List<Suppliernew> list = supplierMapper.selectByExample(easyUIPage);
		easyUIPage.setTotal(total);
		easyUIPage.setRows(list);
		return easyUIPage;
	}

	@Override
	public void updateSupplier(String string) {
		supplierMapper.updatebyId(Integer.parseInt(string));
		
	}

	@Override
	public void frozenSupplier(String string) {
		supplierMapper.frozenSupplier(Integer.parseInt(string));
		
	}	
}
