package com.mall.supplier.service;
import java.util.List;

import com.mall.supplier.model.SuplierAreaRegion;
import com.mall.supplier.model.SuplierAreaTpl;
import com.mall.supplier.model.SupplierMenu;
import com.mall.supplier.model.SupplierRole;
import com.mall.supplier.model.SupplierRolePopedom;
import com.mall.supplier.model.SupplierUserRole;



/**
 * 角色管理接口
 */
public interface SupplierRoleManagerService{
	
	
	public int updateTplName(SuplierAreaTpl tpl);
	public int deleteMould(Long mid);
	public int countDelMould(Long mid);
	
	public int updateMouldRegionByMouldId(List<SuplierAreaRegion> list);
	/**
	 * 根据模板查所限购的地区
	 */
	public List<SuplierAreaRegion> findAgentCountyByMould(Long mid);
	
	public int countLimitByNameAndMerchId(String name,long sid);
	
	public List<SuplierAreaTpl> selectMouldsBySupplierIds(List<Long> sids);
	
	public int addLimitMould(SuplierAreaTpl tpl);
	
	/**
	 * 添加一个角色
	 * @param role 角色实体
	 * @param menus 此角色的权限集合
	 */
	public int addRole(SupplierRole role);
	
	
	
	/**
	 * 修改角色<br>
	 * 将会同时修改此属于此角色用户的权限
	 * @param role  角色实体
	 * @param menus 此角色的权限集合
	 */
	public int updateRole(SupplierRole role);
	
	
	/**
	 * 删除某角色
	 * @param roleid
	 */
	public int deleteRoleById(long roleid);
	
	
	/**
	 * 读取某个角色信息，同时读取此角色权限
	 * @param roleid
	 * @return 权限id存于role.actids数组中
	 */
	public SupplierRole getRoleById(long roleid);
	
	
	/**
	 * 根据供应商的id查询当前拥有的角色
	 * @param sid
	 * @return
	 */
	public List<SupplierRole> selectRolesBySupplierId(long sid);
	/**
	 * 根据供应商的ids查询当前拥有的角色
	 * @param sids
	 * @return
	 */
	public List<SupplierRole> selectRolesBySupplierIds(List<Long> sids);
	
	/**
	 * 根据角色   查权限
	 * @param sid
	 * @return
	 */
	List<SupplierMenu> findMenusByRoleId(long roleId);
	
	/**
	 * 查询所有的权限
	 * @return
	 */
	List<SupplierMenu> findAllMenus();
	
	
	 /**
     * 更新角色的权限 其实就是先删除再插入的操作
     * @param list
     * @return
     */
    int updateRolePopedomByRoleId(List<SupplierRolePopedom> list);
	
	

	/**
	 * 根据用户的id查询当前拥有的权限
	 * @param sid
	 * @return
	 */
	public List<SupplierMenu> getMenusByUserId(long userId);
    /**
    * 添加用户角色
    * @param userRole
    * @return 
    */
	public int addUserRole(SupplierUserRole userRole);

	 /**
    * 更新用户角色关联
    * @param list
    * @return
    */
	public int updateUserRole(SupplierUserRole userRole);

	/**
	 * 根据角色名查询角色是否存在
	 * @param sid
	 * @return
	 */
	
	public int countRoleByName(String name);


	/**
	 * 根据角色名和商户id查询角色是否存在
	 * @param sid
	 * @return
	 */
	
	public int countRoleByNameAndMerchId(String name,long sid);


	 /**
    * 更新用户角色关联
    * @param list
    * @return
    */
	public int countUserByRoleId(long roleId);
	/**
	 * 根据供应商type查询拥有的权限
	 * @param type
	 * @return
	 */
	public List<SupplierMenu> findAllMenuByMenuOwner(Integer type);

}
