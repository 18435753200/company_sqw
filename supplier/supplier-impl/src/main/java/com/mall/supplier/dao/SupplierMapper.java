package com.mall.supplier.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.mall.mybatis.utility.PageBean;
import com.mall.supplier.enums.EasyUIPageSupplier;
import com.mall.supplier.model.Supplier;
import com.mall.supplier.model.SupplierCodeType;
import com.mall.supplier.model.Suppliernew;

public interface SupplierMapper {
	int selectCount(Long userId);
	
	void updateActive(Long supplierId);
	
	int insert2(Long userId);
	
    int deleteByPrimaryKey(Long supplierId);

    Long insert(Supplier record);

    Long insertSelective(Supplier record);

    Supplier selectByPrimaryKey(Long supplierId);

    int updateByPrimaryKeySelective(Supplier record);

    int updateByPrimaryKey(Supplier record);
    
    List<Long> getSupplierIdsByName(@Param(value = "name") String  name );
    
    List<Supplier> getPageList(PageBean<Supplier> pageBean);
    
    List<Supplier> getSubSuppliersByPid(Long parentId);

	List<Long> getSubSupplierIdsByPid(Long parentId);

	List<Map<String, Object>> getIdNameMapBySupplierIds(List<Long> supplierIds);

	List<Supplier> findAllSupplier(String supplierName);
	
	List<Supplier> findAllSupplierByNameAndType(@Param("supplierName")String supplierName, @Param("supplyType")Integer supplyType);
	
	List<Supplier> findAllSuppliers();
   
	List<Supplier> findVirtualSupplier(@Param("supplierName")String supplierName, @Param("supplyType")Integer supplyType);
	
	public int getSuppliersByName(@Param(value = "name") String  name);
	
	/**
	 * 查询POP一件代发商家
	 * @param supplierIds
	 * @return
	 */
	List<Supplier> getOnkeySendSupplierBySupplierIds(List<Long> supplierIds);

	int getSupplierCodeId(String supplierCodeName);
	int updateSupplierCodeNumByType(SupplierCodeType supplierCodeType);
	int updateBackupInfo(Supplier supplier);
	int updateInfo(Supplier supplier);
	Supplier getSupplierBySupplierCode(String supplierCode);
	
	/**
	 * 查询我推荐的企业
	 * @param userId
	 * @return
	 */
	List<Supplier> getTjEnterpriseList(String userId);
	List<Supplier> getSupplierByTjUser(String oldMobile);
	int newUpdateByPrimaryKeySelective(Supplier record);
	/**
	 * <p>更新该企业剩余欠款额度</p>
	 * @param supplierId
	 * @param remainBalanceAmount
	 * @return
	 * @auth:zw
	 */
	int updateRemainBAmount(@Param("supplierId")String supplierId, @Param("remainBalanceAmount")BigDecimal remainBalanceAmount);
	/**
	 * <p>获取企业对象</p>
	 * @param supplier
	 * @return
	 * @auth:zw
	 */
	Supplier findSupplier(Supplier supplier);
	/**
	 * <p>更新红旗券支付密码</p>
	 * @param supplierId
	 * @param newHqqPwd
	 * @return
	 * @auth:zw
	 */
	int updateHqqPwdBySupplierId(@Param("supplierId")String supplierId, @Param("newHqqPwd")String newHqqPwd);
	/**
	 * <p>根据入驻区域获取企业接口</p>
	 * @param companyQy
	 * @return
	 * @auth:zw
	 */
	List<Supplier> findAllSupplierByCommpanyQy(String companyQy);
	/**
	 * <p>获取企业关系接口</p>
	 * @param supplier
	 * @param i
	 * @param pageSize
	 * @return
	 * @auth:zw
	 */
	List<Supplier> getSupplierRelationBySupplierCode(@Param("supplier")Supplier supplier, @Param("page")int page,
			@Param("pageSize")int pageSize);
	/**
	 * <p>获取企业关系集合</p>
	 * @param supplier
	 * @return
	 * @auth:zw
	 */
	List<Supplier> getSupplierList(@Param("supplier")Supplier supplier);
	
	int countByExample(EasyUIPageSupplier example);
	
	List<Suppliernew> selectByExample(EasyUIPageSupplier example);

	void updatebyId(int parseInt);

	void frozenSupplier(int parseInt);

	List<Supplier> getPageList1(PageBean<Supplier> pageBean);
	List<Supplier> getPageList2(PageBean<Supplier> pageBean);
	List<Supplier> getPageListAgent(PageBean<Supplier> pageBean);
	List<Supplier> getPageListPartner(PageBean<Supplier> pageBean);
	List<Supplier> getPageListLine(PageBean<Supplier> pageBean);
	List<Supplier> getPageListOffLine(PageBean<Supplier> pageBean);
	List<Supplier> getPageListAll(PageBean<Supplier> pageBean);
	List<Supplier> getPageListAllyinlian(PageBean<Supplier> pageBean);
	
	void updateSupplier(Supplier supplier);

	void updatetoSupplier(Supplier supplier);

	void saveupdateSupplier(Supplier supplier);
    
	/**
	 *
	 * @param userId
	 * @param organizationType
	 * @return
	 * @author litao
	 */
    List<Supplier> getSupplierByuserId(@Param("userId")Long userId, @Param("organizationType") Integer organizationType);
    
	/**
	 * 只能用于查询会员企业号
	 * @param userId
	 * @return
	 * @author litao
	 */
    Supplier getSupplierByuserIdForhqy(Long userId);

	/**
	 * 修改上级代理
	 * @param list
	 * @param parentId
	 */
	void updateSupplierParentId(@Param("list")List<Long> list, @Param("parentId")Long parentId);

	/**
	 * pop查看代理列表数据
	 * @param pageBean
	 * @return
	 */
	List<Supplier> getPageListAgentSupplier(PageBean<Supplier> pageBean);

	List<Long> getAllSupplierIdsByPid(String parentId);

	Integer updateSupplierWithdrawStatus(@Param("supplierId")Long supplierId,@Param("withdrawStatus")Integer withdrawStatus);

	List<Supplier> findSupplierByAreaPageList(PageBean<Supplier> pageBean);
}