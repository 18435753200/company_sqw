package com.mall.supplier.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.supplier.dao.SupplierAreaRegionMapper;
import com.mall.supplier.dao.SupplierAreaTplMapper;
import com.mall.supplier.dao.SupplierMapper;
import com.mall.supplier.dao.SupplierMenuMapper;
import com.mall.supplier.dao.SupplierRoleMapper;
import com.mall.supplier.dao.SupplierRolePopedomMapper;
import com.mall.supplier.dao.SupplierUserMapper;
import com.mall.supplier.dao.SupplierUserRoleMapper;
import com.mall.supplier.model.SuplierAreaTpl;
import com.mall.supplier.model.SuplierAreaRegion;
import com.mall.supplier.model.SupplierMenu;
import com.mall.supplier.model.SupplierRole;
import com.mall.supplier.model.SupplierRolePopedom;
import com.mall.supplier.model.SupplierUserRole;
import com.mall.supplier.service.SupplierRoleManagerService;

@Service
@Transactional
public class SupplierRoleManagerServiceImpl implements SupplierRoleManagerService {

	@Autowired
	SupplierMapper supplierMapper;
	
	@Autowired 
	SupplierUserMapper supplierUserMapper;
	
	@Autowired 
	SupplierRoleMapper supplierRoleMapper;
	
	@Autowired 
	SupplierMenuMapper supplierMenuMapper;
	
	@Autowired 
	SupplierRolePopedomMapper supplierRolePopedomMapper;
	
	@Autowired 
	SupplierUserRoleMapper supplierUserRoleMapper;
	
	@Autowired
	SupplierAreaTplMapper supplierAreaTplMapper;
	
	@Autowired
	SupplierAreaRegionMapper supplierAreaRegionMapper;

	public SupplierRole getRoleById(long roleid) {
		return supplierRoleMapper.selectByPrimaryKey(roleid);
	}


	public int addRole(SupplierRole role) {
		return  supplierRoleMapper.insertSelective(role);
	}

	public int updateRole(SupplierRole role) {
		return supplierRoleMapper.updateByPrimaryKeySelective(role);
	}
    
	public int deleteRoleById(long roleid) {
	    supplierRolePopedomMapper.deleteByRolePopedomId(roleid);
		return  supplierRoleMapper.deleteByPrimaryKey(roleid);
	}


	public List<SupplierRole> selectRolesBySupplierId(long sid) {
		return supplierRoleMapper.selectRolesBySupplierId(sid);
	}

	public List<SupplierMenu> getMenusByUserId(long userId) {
		return supplierMenuMapper.findMenusByUserId(userId);
	}


	public List<SupplierMenu> findMenusByRoleId(long roleId) {
		return supplierMenuMapper.findMenusByRoleId(roleId);
	}


	public List<SupplierMenu> findAllMenus() {
		return supplierMenuMapper.findAll();
	}


	public int updateRolePopedomByRoleId(List<SupplierRolePopedom> list) {
		supplierRolePopedomMapper.deleteByRolePopedomId(list.get(0).getRoleId());
		return supplierRolePopedomMapper.insertRolePopedom(list);
	}

	public int addUserRole(SupplierUserRole userRole) {
		return supplierUserRoleMapper.insertSelective(userRole);
	}

	public int updateUserRole(SupplierUserRole userRole){
		return supplierUserRoleMapper.updateByUserId(userRole);
	}
	
	public int countRoleByName(String name){
		return supplierRoleMapper.countRoleByName(name);
	}
	
	public int countRoleByNameAndMerchId(String name,long sid){
		return supplierRoleMapper.countRoleByNameAndMerchId(name,sid);
	}

	public int countUserByRoleId(long roleId) {
		return supplierUserRoleMapper.countUserByRoleId(roleId);
	}

	public List<SupplierRole> selectRolesBySupplierIds(List<Long> sids) {
		return supplierRoleMapper.selectRolesBySupplierIds(sids);
	}


	@Override
	public int addLimitMould(SuplierAreaTpl tpl) {
		return  supplierAreaTplMapper.insertMould(tpl);
		
	}


	@Override
	public List<SuplierAreaTpl> selectMouldsBySupplierIds(List<Long> sids) {
	
		return supplierAreaTplMapper.selectMouldsBySupplierIds(sids);
	}
	
	@Override
	public List<SuplierAreaRegion> findAgentCountyByMould(Long mid) {
		
		
		return supplierAreaRegionMapper.findRegionByMenuId(mid);
	}


	@Override
	public int countLimitByNameAndMerchId(String name, long sid) {
		
		return supplierAreaTplMapper.countLimit(name, sid);
	}


	@Override
	public int updateMouldRegionByMouldId(List<SuplierAreaRegion> list) {
		supplierAreaRegionMapper.deleteByMouldId(list.get(0).getTplId());
		return supplierAreaRegionMapper.insertMouldRegion(list);
	}

	@Override
	public int countDelMould(Long mid) {
		
		return supplierAreaRegionMapper.countDelMould(mid);
	}

	@Override
	public int deleteMould(Long mid) {
		
		supplierAreaRegionMapper.deleteByMouldId(mid);
		return supplierAreaTplMapper.deleteMould(mid);
	}

 
	@Override
	public int updateTplName(SuplierAreaTpl tpl) {
		
		return supplierAreaTplMapper.updateTplName(tpl);
	}


	@Override
	public List<SupplierMenu> findAllMenuByMenuOwner(Integer type) {
		// TODO Auto-generated method stub
		return supplierMenuMapper.findAllMenuByMenuOwner(type);
	}
	

}
