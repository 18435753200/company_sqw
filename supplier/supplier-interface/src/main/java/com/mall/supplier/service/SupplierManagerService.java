package com.mall.supplier.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


import com.mall.mybatis.utility.PageBean;
import com.mall.supplier.dto.CompanyDto;
import com.mall.supplier.model.AgentSupplier;
import com.mall.supplier.model.OffLineSupplier;
import com.mall.supplier.model.OnLineSupplier;
import com.mall.supplier.model.Supplier;
import com.mall.supplier.model.SupplierCodeType;
import com.mall.supplier.model.SupplierDetail;
import com.mall.supplier.model.SupplierPartner;
import com.mall.supplier.model.SupplierPartnerArea;
import com.mall.supplier.model.SupplierProduct;
import com.mall.supplier.model.SupplierUser;
/**
 * 商户登录接口
 * @author wangdj
 *
 */
public interface SupplierManagerService{
	
	
	public int selectCountUser(Long userId);
	
	
	
	public void update(Long supplierid);
	/**`
	 * 商户注册
	 * @param product TODO
	 * @param partner TODO
	 */
	public long insert(Supplier supplier);
	
	/**
	 * 商户关联信息注册
	 * @param product TODO
	 * @param partner TODO
	 */
	public int register(Long supplierId, SupplierUser user, SupplierProduct product, SupplierPartner partner,SupplierPartnerArea partnerArea);
	
	/**
	 * 根据id删除某个商户(供应商)，将status置为0 3
	 */
	public void delete(Long supplierId);
	
	/**
	 * 根据id删除商户和商户管理员
	 */
	public void deleteALL(Long supplierId);
 
	/**
	 * 根据用户名获取供应商ID列表,模糊查询
	 * @return
	 */
	public List<Long> getSupplierIdsByName(String name);
	
	/**
	 * 根据ID 供应商
	 * @return
	 */
	public Supplier findSupplier(Long id);

	/**
	 *  查询审核商户分页
	 * @return
	 */
	//普通商家列表
	public PageBean<Supplier> getPageList(PageBean<Supplier> pageBean);
	//家庭号商户列表
	public PageBean<Supplier> getPageList1(PageBean<Supplier> pageBean);
	//会员商家列表
	public PageBean<Supplier> getPageList2(PageBean<Supplier> pageBean);
	//后台代理商戶列表
	public PageBean<Supplier> getPageListAgent(PageBean<Supplier> pageBean);
	//pop代理商查看列表
	public PageBean<Supplier> getAgentSupplierList(PageBean<Supplier> pageBean);
	//合作商列表
	public PageBean<Supplier> getPageListPartner(PageBean<Supplier> pageBean);
	//线下商家列表
	public PageBean<Supplier> getPageListLine(PageBean<Supplier> pageBean);
	//线下商家列表(Pop使用)
	public PageBean<Supplier> getPageListOffLine(PageBean<Supplier> pageBean);
	//全部类型商家
	public PageBean<Supplier> getPageListAll(PageBean<Supplier> pageBean);
	//银联商务查询商家类型
	public PageBean<Supplier> getPageListAllyinlian(PageBean<Supplier> pageBean);
		

    /**
     * 审核商户 1通过 2不通过(不通过商户信息保留 用户信息删除)
     * @return
     */
	public int checkMerchant(Supplier supplier,SupplierUser record);
    
    /**
     * 根据商户ID审核 注册时的管理员 
     * @return
     */
    public SupplierUser findAdminUserByMerchantId(Long supplierId);
    
    

	/**
	 * 根据供应商ID获取商品信息
	 * @return
	 */
	public SupplierProduct getProductBySupplierId(Long supplierId);
	
	/**
	 * 基本信息修改
	 * 修改商户信息同时供应商ID更新商品信息
     * @return
	 */
	public int updateSupplierBaseInfo(Supplier supplier,SupplierProduct record);
	
	/**
	 * 基本信息修改
	 * 修改商户信息同时供应商ID更新商品信息和用户信息
     * @return
	 */
	public int updateSupplierBaseInfo(Supplier supplier,SupplierProduct record,SupplierUser user);
	/**
	 * 根据pid 查询所有子供应商
     * @return
	 */
	public List<Supplier>  getSubSuppliersByPid(Long parentId);
	/**
	 * 根据pid 查询所有子供应商Id
     * @return
	 */
	public List<Long>  getSubSupplierIdsByPid(Long parentId);
	
	/**
	 * 根据上级id查询所有的下级id
	 * @return
	 */
	public List<Long> getAllSupplierIdsByPid(String id);
	/**
	 * 根据supplierId 查询商户是否是子供应商 1子供应商
     * @return
	 */
	public boolean  isSubSupplier(Long supplierId);
	/**
	 * 根据supplierId 查询商户Id : name的map集合
     * @return
	 */
	public Map<Long, String> getIdNameMapBySupplierIds(List<Long> supplierIds);
	
	/**
	 * 查询所有供应商商户,通过名称模糊查询
	 * @return
	 */
	public List<Supplier> findAllSupplier(String supplierName);
	
	/**
	 * 通过名称、供应商类型查询供应商商户
	 * @return
	 */
	public List<Supplier> findAllSupplierByNameAndType(String supplierName, Integer supplyType);
	
	/**
	 * 查询所有供应商商户
	 * @return
	 */
	public List<Supplier> findAllSupplier();
    
	/**
	 * 查询特卖的供应商
	 * @param supplierName
	 * @return
	 */
	public List<Supplier> findVirtualSupplier(String supplierName, Integer supplyType);

	/**
	 * 通过 商户名称 查询 返回记录
	 * @param name
	 * @return
	 */
	public int getSuppliersByName(String name);
	/**
	 * 一件代发供应商
	 * @param supplierIds
	 * @return
	 */
	public List<Supplier> findOnkeySendSupplierBySupplierIds(List<Long> supplierIds);
	
	/**
	 * 根据supplier_id更新企业编号supplier_code
	 */
	int updateSupplierCodeByType(Supplier newSupplier);
	
	/**
	 * 根据企业类型获取supplieCodeNum
	 * @param supplierCodeType
	 * @return
	 */
	int getSupplierCodeNumByType(String supplierCodeType);
	
	/**
	 * 根据企业类型修改对应的supplierCodeNum
	 * @param supplierCodeType
	 * @return
	 */
	int updateSupplierCodeNumByType(SupplierCodeType supplierCodeType);

	public int updateBackupInfo(Supplier supplier);
	public int updateInfo(Supplier supplier);
	public int updateProductBackupInfo(SupplierProduct product);

	public void updateModifyInfo(Supplier supplier);

	public String checkSupplierCodeIsExists(String supplierCode);
	
	/**
	 * <p>根据企业代码获取企业对象</p>
	 * @param sjSupplierId
	 * @return
	 */
	public Supplier getSupplierBySupplierCode(String sjSupplierId);
	
	/**
	 *  查询我推荐的企业
	 * @return
	 */
	public PageBean<CompanyDto> getTjEnterpriseList(PageBean<Supplier> pageBean);
	
	/**
	 * <p>根据手机号检测是否被用作邀请码，如有并修改新的</p>
	 * @param oldMobile
	 * @auth:zw
	 */
	public void findSupplierExistAndModifyTj(String newMobile,String oldMobile);
	/**
	 * <p>根据supplierId获取企业的未支付红旗券</p>
	 * @param supplierId
	 * @return
	 * @auth:zw
	 */
	public BigDecimal getNoPayHqq(String supplierId);
	/**
	 * <p>更新该企业剩余欠款额度</p>
	 * @param supplierId
	 * @param remainBalanceAmount
	 * @return
	 * @auth:zw
	 */
	public int updateRemainBalanceAmount(String supplierId,BigDecimal remainBalanceAmount);
	/**
	 * <p>根据企业supplierId 获取红旗券支付密码</p>
	 * @param supplierId
	 * @return
	 * @auth:zw
	 */
	public String getHqqPayPwd(String supplierId);
	/**
	 * <p>根据所传参数获取企业对象</p>
	 * @param supplier
	 * @return
	 * @auth:zw
	 */
	public Supplier getSupplier(Supplier supplier);
	/**
	 * <p>修改红旗券支付密码</p>
	 * @param supplierId
	 * @param newHqqPwd
	 * @return
	 * @auth:zw
	 */
	public int updateHqqPwdBySupplierId(String supplierId,String newHqqPwd);
	/**
	 * <p>根据入驻区域获取企业接口</p>
	 * @param companyQy
	 * @return
	 * @auth:zw
	 */
	public List<Supplier> findAllSupplierByCommpanyQy(String companyQy);
	/**
	 * <p>获取企业关系</p>
	 * @param paramPage
	 * @return
	 * @auth:zw
	 */
	public PageBean<Supplier> getSupplierRelationBySupplierCodeOrName(
			PageBean<Supplier> paramPage);
	/**
	 * <p>获取企业关系总数</p>
	 * @param supp
	 * @return
	 * @auth:zw
	 */
	public int getSupplierCounts(Supplier supplier);


	public void updateSupplier(Supplier supplier);

	public void updatetoSupplier(Supplier supplier);

	public void saveupdateSupplier(Supplier supplier);
	
	/**
	 * 根据userid查看家庭号状态
	 * 0未注册或是企业号 1正在审核或未通过或者过期2冻结3正常使用
	 * @author litao
	 * @param userId
	 * @return
	 */
	public int findSupplierStatusByUserId(Long userId);
	
	/**
	 * 根据userid查看会员企业号状态
	 * 0未注册或是企业号 1正在审核或未通过或者过期2冻结3正常使用
	 * @author litao
	 * @param userId
	 * @return
	 */
	public int findHuiYuanSupplierStatusByUserId(Long userId);


    /**
     * 扫二维码获取商家收款配置
     *@author litao
     * @param supplierId 商家id
     * @return 现金支付比例
     */
    public Integer getMerchantConfiguration(Long supplierId);

	/**
	 * 根据userid和企业类型查找
     * @param userId 用户id
     * @param type 0普通商户  1 线下商家   5家庭号  6会员企业号  11 区域运营商家   12 区域代理商
	 */
    public List<Supplier> findSuppliersByUserIdAndSupplierId(Long userId,int type);



	
	/**
	 * 查询地区合作人信息
	 * @param 入参 省id，市id，区id，区域类型1运营2代理
	 */
	public List<SupplierPartnerArea> findAreaOperators(Long province,Long city,Long country,Integer parentType);
	
    /**
     * 根据用户id查找折扣
     * @param userId
     * @return 返回小数
     * 查用户折扣券折扣的接口
     */
	public BigDecimal findDiscountByUserId(Long userId);
	
	/**
	 * 14合作伙伴合作地区接口：（子朋）
          入参：（合作伙伴id，合作类型）区域类型1运营2代理
          返回：  地区信息
	 */
	public List<SupplierPartnerArea> findPartnerArea(Long supplierId,Integer partnerType);

	/**
	 * 查上级代理商接口：（新加）
	 * @param supplierId 商家id
	 * @return 上级代理商
	 */
	public Supplier findParentSupplier(Long supplierId);

	int register2(Long supplierId, SupplierPartner partner);
	
	/**
	 * 修改代理地区:
	 * 
	 * @param supplierPartnerArea
	 */
	public Integer updateSupplierPartnerAera(SupplierPartnerArea supplierPartnerArea);


	/**
	 * 更新supplier数据
	 * @param supplier
	 * @return
	 */
	public int updataSupplier(Supplier supplier);
	
	/**
	 * 添加代理区域
	 */
	public int  insertSupplierPartnerArea(SupplierPartnerArea supplierPartnerArea);
	/**
	 * 修改上级代理接口
	 */
	public void updateSupplierParentId(List<Long> list,Long parentId);


	/**
	 * 注册代理接口
	 * @param agentSupplier 代理实体类
	 * @param parentId 上级id
	 * @param creatorId 创建人id
	 * @param userName 登录pop的用户名
	 * @param password 登录pop的密码
	 * @param encryptPassword 登录pop的加密密码
	 * @param agentLevel 代理级别
	 * @return
	 */
	public Long registerAgentSupplier(AgentSupplier agentSupplier,Long parentId,Long creatorId,String userName,String password,String encryptPassword, Integer agentLevel);

	
	/**
	 * 注册线上商家接口
	 * @param agentSupplier 线上商家实体类
	 * @param userName 登录pop的用户名
	 * @param encryptPassword 登录pop的加密密码
	 * @param product  商品实体类
	 * @return
	 */
	public Long registerOnLineSupplier(OnLineSupplier onLineSupplier,String userName,String encryptPassword,String password,SupplierProduct product);

	/**
	 * 注册线下商家(电脑端注册使用)
	 * @param offLineSupplier 线下商家实体类
	 * @param proxySupplierid 上级（MP）ID
	 * @param companyType 公司类型
	 * @param companyCategory 公司经营类目
	 * @param creatorid 创建人id
	 * @param userName 登录pop的用户名
	 * @param password 登录pop的密码
	 * @param encryptPassword 登录pop的加密密码
	 * @param disCount 折扣
	 * @param inProvinceid 省
	 * @param inCityId 市
	 * @param inAreaid 区
	 * @return
	 */
	public long registerOffLineSupplier(OffLineSupplier offLineSupplier ,Long proxySupplierid,Integer companyType,Integer companyCategory,Long creatorid, String userName, String password,String encryptPassword,String disCount, Long inProvinceid, Long inCityId, Long inAreaid);
	
	/**
	 * 注册线下商家(手机端注册使用)
	 * @param offLineSupplier 线下商家实体类(手机端注册使用)
	 * @param proxySupplierid 上级（MP）ID
	 * @param companyType 公司类型
	 * @param companyCategory 公司经营类目
	 * @param creatorid 创建人id
	 * @param userName 登录pop的用户名
	 * @param password 登录pop的密码
	 * @param encryptPassword 登录pop的加密密码
	 * @param disCount 折扣
	 * @param userId  小号id
	 * @param inProvinceid 省
	 * @param inCityId 市
	 * @param inAreaid 区
	 * @return
	 */
	long userRegisterOffLineSupplier(OffLineSupplier offLineSupplier, Long proxySupplierid, Integer companyType,
			Integer companyCategory, Long creatorid, String userName, String password, String encryptPassword,
			String disCount, Long userId,Long inProvinceid, Long inCityId, Long inAreaid);
	
//	
	
//	public long registerOnLineSupplier(Supplier supplier ,Long tuiUserid,  String userName, String pwd,String disCount);

	/**
	 * 根据商户id 改变 线下企业二维码收款状态
	 * @param supplierId 商家id
	 * @param withdrawStatus 线下企业二维码收款状态
	 */
	public void updateSupplierWithdrawStatus(Long supplierId,Integer withdrawStatus);

	public PageBean<CompanyDto> findTjSupplier(PageBean<Supplier> pageBean);

	/**
	 * 根据省市区查询线下商家
	 * @param provinceId	省id
	 * @param cityId	市id
	 * @param areaId	区id
	 * @param page	页码数
	 * @param pageSize	页条数
	 * @return
	 */
	public PageBean<Supplier> findSupplierByArea(Integer provinceId,Integer cityId,Integer areaId,Integer page,Integer pageSize);
	/**
	 * 根据supplierid省id 市id 区id删除代理地区
	 */
	public Integer delectArea(Long supplierId,Long provinceId,Long cityId,Long countyId);
	
	/**
	 * 根据商家id查询商家店铺情况
	 * @param supplierId
	 * @return
	 */
	public SupplierDetail findSupplierDetailBySupplierId(Integer supplierId);
	
	/**
	 * 添加一条商家店铺详细情况
	 * @param supplierDetail
	 * @return
	 */
	public Integer insertDetail(SupplierDetail supplierDetail);
	
	public void updateShopTop(SupplierDetail supplierDetail);
}
	
