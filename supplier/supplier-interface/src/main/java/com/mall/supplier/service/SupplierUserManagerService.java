package com.mall.supplier.service;

import java.util.List;
import java.util.Map;

import com.mall.mybatis.utility.PageBean;
import com.mall.supplier.enums.EasyUIPageSupplier;
import com.mall.supplier.model.Supplier;
import com.mall.supplier.model.SupplierUser;
import com.mall.supplier.model.SupplierUserRoleDTO;
import com.mall.supplier.model.Suppliernew;


/**
 * 用户相关接口
 * @author wangdj
 */
public interface SupplierUserManagerService {
	


	/**
	 * 添加一个用户
	 * @param User
	 * @return 添加的用户id
	 */
	public long add(SupplierUser user);
	
	
	/**
	 * 商户用户
	 * @param username
	 * @param password 此处为未经过md5加密的密码
	 * @return 返回登录成功的用户id
	 * @throws RuntimeException 登录失败抛出此异常，登录失败原因可通过getMessage()方法获取
	 */
	public SupplierUser  login(Map<String,String> map);


	/**
	 * 根据Id获取用户
	 * @return
	 */
	public SupplierUser getUserById(Long id);

	/**
	 * 修改用户信息 
	 * @param User
	 */
	public int update(SupplierUser user);
	
	
	/**
	 * 删除用户
	 * @param id
	 * @throws RuntimeException  当删除最后一个管理员时
	 */
	public int delete(Long id);
	
	/**
	 * 获取分页数据列表
	 * @param pmap
	 * @return
	 */
	public PageBean<SupplierUser> getPageList(PageBean<SupplierUser> pageBean);
	/**
	 * 验证用户名个数
	 * @return
	 */
	public int getUserByName(Map<String,String> pmap);
	
	/**
	 * 用户名查询用户
	 * @return
	 */
	public SupplierUser getUserByName(String name);
	
	
	/**
	 * 查询角色用户关联表
	 * @param supplierId
	 * @return
	 */
	List<SupplierUserRoleDTO> findUsersBySupplierId(Long supplierId);
	
	
	 /**
     * 批量删除用户角色
     * @param ids 用户id的一个list集合
     * @return
     */
    int deleteAll(List<Long> ids);
    
    /**
     * 根据商户ID查询所有子供应商用户
     * @param supplierId 商户ID
     * @return 子供应商用户list集合
     */
	public  List<SupplierUserRoleDTO> findSubSuppliersBySupplierId(Long supplierId);
	 /**
     * 当前用户是否是子供应商
     * @param userId 用户ID
     * @return 是 否
     */
	public boolean isSubSupplier(Long userId);

	/**
	 * <p>查询是管理员的用户</p>
	 * @param supplierId
	 * @return
	 * @auth:zw
	 */
	public SupplierUser getIsAdminUserBySupplierId(Long supplierId);

	//冻结代理商功能
	public void updateSupplier(String string);


	public void frozenSupplier(String string);


	public EasyUIPageSupplier querySupplier(Suppliernew supplier, Integer startpage, Integer rows);
    
}
