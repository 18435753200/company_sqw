package com.mall.supplier.service.impl;

import com.mall.category.api.rpc.BankService;
import com.mall.category.model.BankBranch;
import com.mall.customer.service.UserService;
import com.mall.mybatis.utility.PageBean;
import com.mall.supplier.dao.SupplierMapper;
import com.mall.supplier.dao.SupplierOfflineStoreAttrMapper;
import com.mall.supplier.dao.SupplierOfflineStoreMapper;
import com.mall.supplier.dao.SupplierPartnerAreaMapper;
import com.mall.supplier.dao.SupplierPartnerMapper;
import com.mall.supplier.dao.SupplierProductMapper;
import com.mall.supplier.dao.SupplierUserMapper;
import com.mall.supplier.dto.CompanyDto;
import com.mall.supplier.model.*;
import com.mall.supplier.service.SupplierDiscountService;
import com.mall.supplier.service.SupplierManagerService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SupplierManagerServiceImpl implements SupplierManagerService {

	Logger logger = Logger.getLogger(SupplierManagerServiceImpl.class);

	@Autowired
	private SupplierMapper supplierMapper;

	@Autowired
	private SupplierUserMapper supplierUserMapper;

	@Autowired
	private SupplierPartnerMapper supplierPartnerMapper;

	@Autowired
	private SupplierProductMapper supplierProductMapper;
	
	@Autowired
	private SupplierPartnerAreaMapper supplierPartnerAreaMapper;
	
	@Autowired
	private SupplierDiscountService supplierDiscountService;
	
	@Autowired
	private BankService bankService;

	@Resource
    private UserService userService;
	
	@Autowired
	SupplierOfflineStoreMapper supplierOfflineStoreMapper;
	
	@Autowired
	SupplierOfflineStoreAttrMapper supplierOfflineStoreAttrMapper;

	// @Autowired
	// private SupplierService supplierService;

	@Override
	public int register(Long supplierId, SupplierUser user, SupplierProduct product, SupplierPartner partner,SupplierPartnerArea partnerArea) {
		user.setSupplierId(supplierId);
		supplierUserMapper.insert(user);
		partner.setSupplierId(supplierId);
		supplierPartnerMapper.insert(partner);
		product.setSupplierId(supplierId);
		if(partnerArea.getCityId()!=null){
			partnerArea.setSupplierId(supplierId);
			supplierPartnerAreaMapper.insertSelective(partnerArea);
		}
		return supplierProductMapper.insert(product);
	}

	@Override
	public long insert(Supplier supplier) {
		supplierMapper.insertSelective(supplier);
		return supplier.getSupplierId();
	}
	
	

	// 删除供应商，将status置为0
	@Override
	public void delete(Long supplierId) {
		supplierMapper.deleteByPrimaryKey(supplierId);
	}

	@Override
	public void deleteALL(Long supplierId) {
		Supplier supplier = new Supplier();
		supplier.setSupplierId(supplierId);
		supplier.setStatus(3);// 删除

		SupplierProduct product = new SupplierProduct();
		product.setSupplierId(supplierId);

		supplierMapper.updateByPrimaryKeySelective(supplier);
		// supplierProductMapper.deleteBySupplierId(supplierId);
		SupplierUser supplierUser = supplierUserMapper.findAdminUserByMerchantId(supplierId);
		// supplierUserRoleMapper.deleteByUserId(supplierUser.getUserId());
		supplierUserMapper.deleteByPrimaryKey(supplierUser.getUserId());
	}

	@Override
	public List<Long> getSupplierIdsByName(String name) {
		return supplierMapper.getSupplierIdsByName(name);
	};

	@Override
	public Supplier findSupplier(Long id) {
		return supplierMapper.selectByPrimaryKey(id);
	}

	@Override
	public PageBean<Supplier> getPageList(PageBean<Supplier> pageBean) {
		logger.info("-------------getPageList start-----------------");
		List<Supplier> list = new ArrayList<Supplier>();
		list = supplierMapper.getPageList(pageBean);
		pageBean.setResult(list);
		return pageBean;
	}
	@Override
	public PageBean<Supplier> getPageList1(PageBean<Supplier> pageBean) {
		logger.info("-------------getPageList start-----------------");
		List<Supplier> list = new ArrayList<Supplier>();
		list = supplierMapper.getPageList1(pageBean);
		pageBean.setResult(list);
		return pageBean;
	}
	@Override
	public PageBean<Supplier> getPageList2(PageBean<Supplier> pageBean) {
		logger.info("-------------getPageList start-----------------");
		List<Supplier> list = new ArrayList<Supplier>();
		list = supplierMapper.getPageList2(pageBean);
		pageBean.setResult(list);
		return pageBean;
	}
	
	@Override
	public PageBean<Supplier> getPageListAgent(PageBean<Supplier> pageBean) {
		logger.info("-------------getPageList start-----------------");
		List<Supplier> list = new ArrayList<Supplier>();
		list = supplierMapper.getPageListAgent(pageBean);
		pageBean.setResult(list);
		return pageBean;
	}
	
	@Override
	public PageBean<Supplier> getAgentSupplierList(PageBean<Supplier> pageBean) {
		logger.info("-------------getPageList start-----------------");
		List<Supplier> list = new ArrayList<Supplier>();
		list = supplierMapper.getPageListAgentSupplier(pageBean);
		pageBean.setResult(list);
		return pageBean;
	}
	
	@Override
	public int checkMerchant(Supplier supplier, SupplierUser record) {
		supplierMapper.updateByPrimaryKeySelective(supplier);
		if (record.getStatus() == 1) {
			return supplierUserMapper.updateAdminUserByMerchantId(record);
		} else {
//			return supplierUserMapper.deleteAdminUserByMerchantId(record.getSupplierId());
			return 1;
		}
	}

	@Override
	public SupplierUser findAdminUserByMerchantId(Long supplierId) {
		return supplierUserMapper.findAdminUserByMerchantId(supplierId);
	}

	@Override
	public SupplierProduct getProductBySupplierId(Long supplierId) {
		return supplierProductMapper.getProductBySupplierId(supplierId);
	}

	@Override
	public int updateSupplierBaseInfo(Supplier supplier, SupplierProduct record) {
		supplierMapper.updateByPrimaryKeySelective(supplier);

		int status = supplierProductMapper.updateProductBySupplierId(record);

		// 更新wms中间表
		// supplierService.realTimeSaveSupplier(supplier);

		return status;
	}

	@Override
	public int updateSupplierBaseInfo(Supplier supplier, SupplierProduct record, SupplierUser user) {
		supplierMapper.updateByPrimaryKeySelective(supplier);
		supplierUserMapper.updateByPrimaryKeySelective(user);
		int status = supplierProductMapper.updateProductBySupplierId(record);

		// 更新wms中间表
		// supplierService.realTimeUpdateSupplier(supplier);

		return status;
	}

	@Override
	public List<Supplier> getSubSuppliersByPid(Long parentId) {
		return supplierMapper.getSubSuppliersByPid(parentId);
	}

	@Override
	public List<Long> getSubSupplierIdsByPid(Long parentId) {
		return supplierMapper.getSubSupplierIdsByPid(parentId);
	}
	
	@Override
	public List<Long> getAllSupplierIdsByPid(String parentId) {
		return supplierMapper.getAllSupplierIdsByPid(parentId);
	}


	@Override
	public boolean isSubSupplier(Long supplierId) {
		boolean flag = false;
		Supplier supplier = supplierMapper.selectByPrimaryKey(supplierId);
		if (null != supplier && supplier.getType() == 1) {
			flag = true;
		}
		return flag;
	}

	@Override
	public Map<Long, String> getIdNameMapBySupplierIds(List<Long> supplierIds) {
		Map<Long, String> rs = new HashMap<Long, String>();

		List<Map<String, Object>> idNameMapBySupplierIds = supplierMapper.getIdNameMapBySupplierIds(supplierIds);
		if (null != idNameMapBySupplierIds & idNameMapBySupplierIds.size() > 0) {
			for (Map<String, Object> map : idNameMapBySupplierIds) {
				String supplierId = map.get("supplierId").toString();
				rs.put(Long.parseLong(supplierId), (String) map.get("name"));
			}
		}
		return rs;
	}

	@Override
	public List<Supplier> findAllSupplier(String supplierName) {
		return supplierMapper.findAllSupplier(supplierName);
	}

	@Override
	public List<Supplier> findAllSupplierByNameAndType(String supplierName, Integer supplyType) {
		return supplierMapper.findAllSupplierByNameAndType(supplierName, supplyType);
	}

	@Override
	public List<Supplier> findAllSupplier() {
		return supplierMapper.findAllSuppliers();
	};

	@Override
	public List<Supplier> findVirtualSupplier(String supplierName, Integer supplyType) {
		return supplierMapper.findVirtualSupplier(supplierName, supplyType);
	}

	@Override
	public int getSuppliersByName(String name) {
		return supplierMapper.getSuppliersByName(name);
	}

	@Override
	public List<Supplier> findOnkeySendSupplierBySupplierIds(List<Long> supplierIds) {
		return supplierMapper.getOnkeySendSupplierBySupplierIds(supplierIds);
	}

	public int updateSupplierCodeByType(Supplier newSupplier) {
		return supplierMapper.updateByPrimaryKeySelective(newSupplier);
	}

	public int getSupplierCodeNumByType(String supplierCodeType) {
		return supplierMapper.getSupplierCodeId(supplierCodeType);
	}

	public int updateSupplierCodeNumByType(SupplierCodeType supplierCodetType) {
		return supplierMapper.updateSupplierCodeNumByType(supplierCodetType);
	}

	@Override
	public int updateBackupInfo(Supplier supplier) {
		return supplierMapper.updateBackupInfo(supplier);
	}

	@Override
	public int updateProductBackupInfo(SupplierProduct product) {
		return supplierProductMapper.updateProductBySupplierId(product);
	}

	@Override
	public void updateModifyInfo(Supplier supplier) {
		supplierMapper.updateByPrimaryKeySelective(supplier);
	}

	@Override
	public String checkSupplierCodeIsExists(String supplierCode) {
		String flag = "0"; // 不存在
		Supplier supplier = supplierMapper.getSupplierBySupplierCode(supplierCode);
		if (supplier != null) {
			flag = "1";
		}
		return flag;
	}

	@Override
	public Supplier getSupplierBySupplierCode(String sjSupplierId) {
		Supplier supplier = supplierMapper.getSupplierBySupplierCode(sjSupplierId);
		if (supplier != null) {
			return supplier;
		}
		return null;
	}

	/**
	 * 查询我推荐的企业
	 * 
	 * @return
	 */
	public PageBean<CompanyDto> getTjEnterpriseList(PageBean<Supplier> pageBean) {
		PageBean<CompanyDto> page = new PageBean<CompanyDto>();
		logger.info("-------------getTjEnterpriseList start-----------------");
		List<Supplier> list = new ArrayList<Supplier>();
		list = supplierMapper.getPageList(pageBean);
		List<CompanyDto> companyList = new ArrayList<CompanyDto>();
		CompanyDto companyDto = null;
		for (Supplier supplier : list) {
			companyDto = new CompanyDto();
			companyDto.setCompanyId(supplier.getSupplierId());
			if(StringUtils.isEmpty(supplier.getLogoImgurl())){
				companyDto.setCompanyLogo("http://image01.zhongjumall.com/group1/M00/00/31/CgAAElgxxt-Aa7U1AAAjS6o85T0082.jpg");
			}else{
				companyDto.setCompanyLogo("http://image01.zhongjumall.com/" + supplier.getLogoImgurl());
			}
			companyDto.setCompanyName(supplier.getName());
			companyDto.setContactPhone(supplier.getPhone());
			companyDto.setContactName(supplier.getContact());
			companyList.add(companyDto);
		}
		page.setResult(companyList);
		page.setOrder(pageBean.getOrder());
		page.setPage(pageBean.getPage());
		page.setPageSize(pageBean.getPageSize());
		page.setParameter(pageBean.getParameter());
		page.setTotalCount(pageBean.getTotalCount());
		return page;
	}

	public void findSupplierExistAndModifyTj(String newMobile, String oldMobile) {
		List<Supplier> supplierList = supplierMapper.getSupplierByTjUser(oldMobile);
		if (supplierList != null && supplierList.size() > 0) {
			for (Supplier supplier : supplierList) {
				Supplier newSupplier = new Supplier();
				newSupplier.setSupplierId(supplier.getSupplierId());
				newSupplier.setUserTj(newMobile);
				try {
					int result = supplierMapper.newUpdateByPrimaryKeySelective(newSupplier);
				} catch (Exception e) {
					logger.error("修改邀请码出现异常!!!supplierId==>" + supplier.getSupplierId(), e);
					logger.error("新邀请码邀请码为：newMobile==>" + newMobile);
					logger.error("老邀请码为：oldMobile==>" + oldMobile);
					e.printStackTrace();
				}
			}
		}
	}
	
	public BigDecimal getNoPayHqq(String supplierId) {
		Supplier supplier = supplierMapper.selectByPrimaryKey(Long.valueOf(supplierId));
		if(supplier == null){
			return BigDecimal.valueOf(0D).setScale(0, BigDecimal.ROUND_HALF_UP);
		}else if(supplier.getNoPayhqq() == null){
			return BigDecimal.valueOf(0D).setScale(0, BigDecimal.ROUND_HALF_UP);
		}
		return supplier.getNoPayhqq().setScale(0, BigDecimal.ROUND_HALF_UP);
	}
	
	public int updateRemainBalanceAmount(String supplierId,
			BigDecimal remainBalanceAmount) {
		return supplierMapper.updateRemainBAmount(supplierId,remainBalanceAmount);
	}
	
	public String getHqqPayPwd(String supplierId) {
		Supplier supplier = supplierMapper.selectByPrimaryKey(Long.valueOf(supplierId));
		return (supplier == null)?"":(StringUtils.isNotBlank(supplier.getHqqPwd())?supplier.getHqqPwd():"");
	}
	
	public Supplier getSupplier(Supplier supplier) {
		Supplier supp = supplierMapper.findSupplier(supplier);
		return supp;
	}
	
	public int updateHqqPwdBySupplierId(String supplierId, String newHqqPwd) {
		
		return supplierMapper.updateHqqPwdBySupplierId(supplierId,newHqqPwd);
	}

	public List<Supplier> findAllSupplierByCommpanyQy(String companyQy) {
		return supplierMapper.findAllSupplierByCommpanyQy(companyQy);
	}

	public PageBean<Supplier> getSupplierRelationBySupplierCodeOrName(
			PageBean<Supplier> paramPage) {
		Supplier supplier = (Supplier) paramPage.getParameter();
		List<Supplier> list = supplierMapper.getSupplierRelationBySupplierCode(supplier,(paramPage.getPage()-1)*paramPage.getPageSize(),paramPage.getPageSize());
		paramPage.setResult(list);
		return paramPage;
	}

	public int getSupplierCounts(Supplier supplier) {
		List<Supplier> list = supplierMapper.getSupplierList(supplier);
		return list.size();
	}

	@Override
	public void updateSupplier(Supplier supplier) {
		supplierMapper.updateSupplier(supplier);
		
	}

	@Override
	public void updatetoSupplier(Supplier supplier) {
		supplierMapper.updatetoSupplier(supplier);
		
	}

	@Override
	public void saveupdateSupplier(Supplier supplier) {
		supplierMapper.saveupdateSupplier(supplier);
		
	}
	
	
    
	@Override
	public int findSupplierStatusByUserId(Long userId) {
		try {
            Supplier supplier = supplierMapper.getSupplierByuserId(userId,5).get(0);
			if(supplier.getOrganizationType()==5 && supplier.getStatus()!=5 && supplier.getActiveStatus()==1)
				return 3;//正常使用
			if(supplier.getOrganizationType()==5 && supplier.getStatus()==5)
				return 2;//冻结
			if(supplier.getOrganizationType()==5 && supplier.getActiveStatus()==-1)
				return 1;//正在审核或未通过或者过期
		} catch (Exception e) {
			return 0;
		}
		return 0;//未注册
	}
	
	@Override
	public int findHuiYuanSupplierStatusByUserId(Long userId) {
		Supplier supplier = supplierMapper.getSupplierByuserIdForhqy(userId);
		try {
			if(supplier.getOrganizationType()==6 && supplier.getStatus() !=5 && supplier.getActiveStatus()==1)
				return 3;//正常使用
			if(supplier.getOrganizationType()==6 && supplier.getStatus()==5)
				return 2;//冻结
			if(supplier.getOrganizationType()==6 && supplier.getActiveStatus() == -1)
				return 1;//正在审核或未通过或者过期
		} catch (Exception e) {
			return 0;
		}
		return 0;//未注册
	}

	@Override
	public Integer getMerchantConfiguration(Long supplierId) {
		return 70;
	}

	@Override
	public void update(Long supplierid) {
		supplierMapper.updateActive(supplierid);
		
	}
	
	@Override
	public int selectCountUser(Long userId) {
		int i = supplierMapper.selectCount(userId);
		return i;
	}

	@Override
	public int updateInfo(Supplier supplier) {
		return supplierMapper.updateInfo(supplier);
	
	}

	@Override
	public List<Supplier> findSuppliersByUserIdAndSupplierId(Long userId, int type) {
		return supplierMapper.getSupplierByuserId(userId,type);
	}



	@Override
	public PageBean<Supplier> getPageListPartner(PageBean<Supplier> pageBean) {
		List<Supplier> supplier = supplierMapper.getPageListPartner(pageBean);
		pageBean.setResult(supplier);
		return pageBean;
	}

	
	
	
	
	@Override
	public List<SupplierPartnerArea> findAreaOperators(Long province,
			Long city, Long country, Integer parentType) {
		List<SupplierPartnerArea> supplierPartnerAreas = supplierPartnerAreaMapper
				.findAreaOperators(province, city, country, parentType);
		if (supplierPartnerAreas != null && supplierPartnerAreas.size() > 0) {
			for (SupplierPartnerArea supplierPartnerArea : supplierPartnerAreas) {
				if (supplierPartnerArea.getStatus() == 1) {
					if (supplierPartnerArea.getEndDateTime() != null) {
						if (supplierPartnerArea.getEndDateTime().getTime() < new Date()
								.getTime()) {
							//过期
							supplierPartnerArea.setStatus(2);
						}
					}
				}
			}
		} 
		return supplierPartnerAreas;
	}
	
	
	@Override
	public BigDecimal findDiscountByUserId(Long userId) {
	    try {
            String supplierId = userService.findUserById(userId).getSupplierId();
            Supplier supplier = findSupplier(Long.valueOf(supplierId));
            BigDecimal discount = supplier.getDiscount().divide(new BigDecimal(100));
            return discount;
        }catch (Exception e){
	        return new BigDecimal(1);
        }
	}

	@Override
	public List<SupplierPartnerArea> findPartnerArea(Long supplierId, Integer partnerType) {
		 List<SupplierPartnerArea> area = supplierPartnerAreaMapper
		.findPartnerArea(supplierId, partnerType);
		return area;
	}


	@Override
	public PageBean<Supplier> getPageListLine(PageBean<Supplier> pageBean) {
		logger.info("-------------getPageList start-----------------");
		List<Supplier> list = new ArrayList<Supplier>();
		list = supplierMapper.getPageListLine(pageBean);
		pageBean.setResult(list);
		return pageBean;
		
	}
	
	@Override
	public PageBean<Supplier> getPageListOffLine(PageBean<Supplier> pageBean) {
		logger.info("-------------getPageListOffLine start-----------------");
		List<Supplier> list = new ArrayList<Supplier>();
		list = supplierMapper.getPageListOffLine(pageBean);
		pageBean.setResult(list);
		return pageBean;
		
	}


	public Supplier findParentSupplier(Long supplierId){
		//根据supplierId获取当前商家 获得 parentId
		Long parentId = supplierMapper.selectByPrimaryKey(supplierId).getParentId();
		//如果parentId为0 说明为顶层代理商 返回null
		if (parentId==0){
			return null;
		}
		//若果不为0 则根据parentId查出上级代理商
		Supplier parentSupplier = supplierMapper.selectByPrimaryKey(parentId);
		return parentSupplier;
	}

	@Override
	public int register2(Long supplierId, SupplierPartner partner) {
		partner.setSupplierId(supplierId);
		int insert = supplierPartnerMapper.insert(partner);
		return insert;
	}

	
	@Override
	public Integer updateSupplierPartnerAera(SupplierPartnerArea supplierPartnerArea) {
		return supplierPartnerAreaMapper.updateSupplierPartnerAera(supplierPartnerArea);
	}

	@Override
	public int updataSupplier(Supplier supplier) {
		return  supplierMapper.newUpdateByPrimaryKeySelective(supplier);
	}

	@Override
	public int insertSupplierPartnerArea(SupplierPartnerArea supplierPartnerArea) {
		return supplierPartnerAreaMapper.insertSelective(supplierPartnerArea);
	}

	@Override
	public void updateSupplierParentId(List<Long> list, Long parentId) {
		supplierMapper.updateSupplierParentId(list,parentId);
	}

	//注册代理
	@Override
	public Long registerAgentSupplier(AgentSupplier agentSupplier,Long parentId,Long creatorId,String userName,String password,String encryptPassword, Integer agentLevel) {
		
		Supplier supplier=new Supplier();
		BeanUtils.copyProperties(agentSupplier, supplier);
		
		//添加代理注册区域(同时添加到supplier数据中，方便条件查询的使用)
		SupplierPartnerArea partnerArea = new SupplierPartnerArea();
		/*partnerArea.setType(3);
		partnerArea.setProvinceId(provinceid);
		supplier.setProvinceId(Integer.valueOf(provinceid.toString()));
		if(cityId!=null){
			partnerArea.setType(2);
			partnerArea.setCityId(cityId);
			supplier.setCityId(Integer.valueOf(cityId.toString()));
		}
		if(areaid!=null){
			partnerArea.setType(1);
			partnerArea.setCountyId(areaid);
			supplier.setAreaId(Integer.valueOf(areaid.toString()));
		}
		partnerArea.setPartnerType(2);
		partnerArea.setStatus(1);*/
		
		//首先进行supplier对象的添加
		supplier.setParentId(parentId);
		supplier.setStatus(0);
		supplier.setActiveStatus(-1);	//该企业注册未激活置为-1
		//设置pop用户为51
		supplier.setSupplyType(51);
		supplier.setCreateTime(new Date());
		supplier.setOrganizationType(12);
		supplier.setType(agentLevel);
		supplier.setCreateBy(creatorId.toString());
		
		
		BankBranch bankbranch = bankService.findBankBranchByCode(supplier.getAccoutBankno());
		if(bankbranch!=null){
			supplier.setAccoutBankname((bankbranch.getBankBranchName()));
		}
		
		//存储到数据库
		long supplierId = this.insert(supplier);
		
		
		
		//添加登录pop账号
		SupplierUser user=new SupplierUser();
		user.setLoginName(userName);
		user.setPassword(encryptPassword);
		user.setRecordPwd(password);	//记录明文，为了unicorn创建账号发送短信用	(就是后台审核发短信用的)	
		user.setIsAdmin(1);
		user.setStatus(1);
		user.setCreateTime(new Date());
		
		//添加代理合作商
		SupplierPartner partner=new SupplierPartner();
		//添加代理商产品
		SupplierProduct product=new SupplierProduct();
		
		Integer registerId = this.register(supplierId, user, product, partner, partnerArea);
		
		return Long.parseLong(registerId.toString());
	}

	//注册线上商家
	@Override
	public Long registerOnLineSupplier(OnLineSupplier onLineSupplier, String userName, String encryptPassword,String password,SupplierProduct product ) {
		
		Supplier supplier=new Supplier();
		BeanUtils.copyProperties(onLineSupplier, supplier);
		
		//首先进行supplier对象的添加
		supplier.setStatus(0);
		supplier.setActiveStatus(-1);	//该企业注册未激活置为-1
		//设置pop用户为51
		supplier.setSupplyType(51);
		supplier.setCreateTime(new Date());
		supplier.setOrganizationType(0);
		
		
		BankBranch bankbranch = bankService.findBankBranchByCode(supplier.getAccoutBankno());
		if(bankbranch!=null){
			supplier.setAccoutBankname((bankbranch.getBankBranchName()));
		}
		
		//存储到数据库
		long supplierId = this.insert(supplier);
		
		
		
		//添加登录pop账号
		SupplierUser user=new SupplierUser();
		user.setLoginName(userName);
		user.setPassword(encryptPassword);
		user.setRecordPwd(password);	//记录明文，为了unicorn创建账号发送短信用		
		user.setIsAdmin(1);
		user.setStatus(1);
		user.setCreateTime(new Date());
		
		//添加代理合作商
		SupplierPartner partner=new SupplierPartner();
		SupplierPartnerArea partnerArea = new SupplierPartnerArea();
		
		Integer registerId = this.register(supplierId, user, product, partner, partnerArea);
		
		return Long.parseLong(registerId.toString());
	}
	
	
	//添加线下商家
	@Override
	public long registerOffLineSupplier(OffLineSupplier offLineSupplier, Long proxySupplierid, Integer companyType,Integer companyCategory,Long creatorid, String userName,
			String password, String encryptPassword,String disCount, Long inProvinceid, Long inCityId, Long inAreaid) {
		
		
		Supplier supplier=new Supplier();
		BeanUtils.copyProperties(offLineSupplier, supplier);
		
		// 设置pop用户为51
		supplier.setSupplyType(51);
		supplier.setType(12); // 注册的时候默认给-1值
		supplier.setActiveStatus(-1); // 该企业注册未激活置为-1
		supplier.setOrganizationType(1);
		supplier.setStatus(0);
		supplier.setParentId(proxySupplierid);
		supplier.setCreateTime(new Date());
		supplier.setCreateBy(creatorid.toString());
		//添加企业类型
		supplier.setCompanyBizType(companyType);
		supplier.setCompanyBizCategory(companyCategory);
		BankBranch bankbranch = bankService.findBankBranchByCode(supplier.getAccoutBankno());
		if(bankbranch!=null){
			supplier.setAccoutBankname((bankbranch.getBankBranchName()));
		}
		//存储到数据库,获取supplier的id
				Long supplierId = this.insert(supplier);
		
		//添加折扣
		supplierDiscountService.setSalesDiscount(supplierId, new BigDecimal(disCount));
		
		
		
		//添加省市区数据(给supplier表和地区表中都添加数据)
		SupplierPartnerArea partnerArea = new SupplierPartnerArea();
		
//		partnerArea.setProvinceId(Long.parseLong(inProvinceid.toString()));
//		supplier.setProvinceId(Integer.valueOf(inCityId.toString()));
////		---------------------
//		partnerArea.setCityId(Long.parseLong(inCityId.toString()));
//		supplier.setCityId((Integer.valueOf(inCityId.toString())));
////		---------------------
//		partnerArea.setCountyId(Long.parseLong(inAreaid.toString()));
//		supplier.setAreaId(Integer.valueOf(inAreaid.toString()));
////		--------------------
//		partnerArea.setStatus(1);
		
		
		SupplierUser user = new SupplierUser();
		user.setIsAdmin(1);
		user.setStatus(1);
		user.setLoginName(userName);
		user.setPassword(encryptPassword);
		user.setRecordPwd(password);//记录明文，为了unicorn创建账号发送短信用
		
		
		
		//添加代理合作商
		SupplierPartner partner=new SupplierPartner();
		//添加代理商产品
		SupplierProduct product=new SupplierProduct();
		
		Integer registerId = this.register(supplierId, user, product, partner, partnerArea);
		
		return Long.parseLong(registerId.toString());
	}


	//会员注册线下商家
	public long userRegisterOffLineSupplier(OffLineSupplier offLineSupplier, Long proxySupplierid, Integer companyType,Integer companyCategory,Long creatorid, String userName,
			String password, String encryptPassword,String disCount,Long userId,Long inProvinceid, Long inCityId, Long inAreaid) {
		Supplier supplier=new Supplier();
		BeanUtils.copyProperties(offLineSupplier, supplier);
		
		// 设置pop用户为51
		supplier.setSupplyType(51);
		supplier.setType(12); // 注册的时候默认给-1值
		supplier.setActiveStatus(-1); // 该企业注册未激活置为-1
		supplier.setOrganizationType(1);
		supplier.setStatus(0);
		supplier.setParentId(proxySupplierid);
		supplier.setCreateTime(new Date());
		supplier.setCreateBy(creatorid.toString());
		//添加企业类型
		supplier.setCompanyBizType(companyType);
		supplier.setCompanyBizCategory(companyCategory);
		/*Supplier mpSupplier = this.findSupplier(proxySupplierid);
		if(mpSupplier.getOrganizationType()==12){
			//是MP
			if(mpSupplier.getType().equals(1650)){
				SupplierPartnerArea mpParnSupplierPartnerArea = this.findPartnerArea(mpSupplier.getParentId(), 2);
				 //判断商家是否与MP的上级代理所在区域一致
				 if(mpParnSupplierPartnerArea!=null){
					 if(mpParnSupplierPartnerArea.getProvinceId()!=null){
						 if(!mpParnSupplierPartnerArea.getProvinceId().equals(inProvinceid)){
							 return -1;
						 }
					 }
					 if(mpParnSupplierPartnerArea.getCityId()!=null){
						 if(!mpParnSupplierPartnerArea.getCityId().equals(inCityId)){
							 return -1;
						 }
					 }
					 if(mpParnSupplierPartnerArea.getCountyId()!=null){
						 if(!mpParnSupplierPartnerArea.getCountyId().equals(inAreaid)){
							 return -1;
						 }
					 }
				 }
			}else{
				SupplierPartnerArea mpParnSupplierPartnerArea = this.findPartnerArea(mpSupplier.getSupplierId(), 2);
				 //判断商家是否与MP的上级代理所在区域一致
				 if(mpParnSupplierPartnerArea!=null){
					 if(mpParnSupplierPartnerArea.getProvinceId()!=null){
						 if(!mpParnSupplierPartnerArea.getProvinceId().equals(inProvinceid)){
							 return -1;
						 }
					 }
					 if(mpParnSupplierPartnerArea.getCityId()!=null){
						 if(!mpParnSupplierPartnerArea.getCityId().equals(inCityId)){
							 return -1;
						 }
					 }
					 if(mpParnSupplierPartnerArea.getCountyId()!=null){
						 if(!mpParnSupplierPartnerArea.getCountyId().equals(inAreaid)){
							 return -1;
						 }
					 }
			}
				}
			}*/
		supplier.setProvinceId(inProvinceid.intValue());
		supplier.setCityId(inCityId.intValue());
		supplier.setAreaId(inAreaid.intValue());
		BankBranch bankbranch = bankService.findBankBranchByCode(supplier.getAccoutBankno());
		if(bankbranch!=null){
			supplier.setAccoutBankname((bankbranch.getBankBranchName()));
		}
		//存储到数据库,获取supplier的id
		Long supplierId = this.insert(supplier);
		userService.setUserSupplierId(userId, supplierId,(long)1);
		//添加折扣
		supplierDiscountService.setSalesDiscount(supplierId, new BigDecimal(disCount));
		
		
		
		//添加省市区数据(给supplier表和地区表中都添加数据)
		SupplierPartnerArea partnerArea = new SupplierPartnerArea();
		
		SupplierUser user = new SupplierUser();
		user.setIsAdmin(1);
		user.setStatus(1);
		user.setLoginName(userName);
		user.setPassword(encryptPassword);
		user.setRecordPwd(password);//记录明文，为了unicorn创建账号发送短信用
		
		
		
		//添加代理合作商
		SupplierPartner partner=new SupplierPartner();
		//添加代理商产品
		SupplierProduct product=new SupplierProduct();
		
		this.register(supplierId, user, product, partner, partnerArea);
		
		return supplierId;
	}

	@Override
	public void updateSupplierWithdrawStatus(Long supplierId, Integer withdrawStatus) {
		supplierMapper.updateSupplierWithdrawStatus(supplierId,withdrawStatus);
	}

	@Override
	public PageBean<CompanyDto> findTjSupplier(PageBean<Supplier> pageBean) {
		PageBean<Supplier> supplierPageBean = userService.findTjSupplier(pageBean);
		List<Supplier> supplierList = supplierPageBean.getResult();
		List<CompanyDto> companyDtoList = new ArrayList<CompanyDto>();
		CompanyDto companyDto = null;
		for (Supplier supplier : supplierList) {
			companyDto = new CompanyDto();
			companyDto.setCompanyId(supplier.getSupplierId());
			if(StringUtils.isEmpty(supplier.getLogoImgurl())){
				companyDto.setCompanyLogo("http://image01.zhongjumall.com/group1/M00/00/31/CgAAElgxxt-Aa7U1AAAjS6o85T0082.jpg");
			}else{
				companyDto.setCompanyLogo("http://image01.zhongjumall.com/" + supplier.getLogoImgurl());
			}
			companyDto.setCompanyName(supplier.getName());
			companyDto.setContactPhone(supplier.getPhone());
			companyDto.setContactName(supplier.getContact());
			companyDtoList.add(companyDto);
		}
		PageBean<CompanyDto> companyDtoPageBean = new PageBean<CompanyDto>();
		companyDtoPageBean.setResult(companyDtoList);
		companyDtoPageBean.setOrder(supplierPageBean.getOrder());
		companyDtoPageBean.setPage(supplierPageBean.getPage());
		companyDtoPageBean.setPageSize(supplierPageBean.getPageSize());
		companyDtoPageBean.setParameter(supplierPageBean.getParameter());
		companyDtoPageBean.setTotalCount(supplierPageBean.getTotalCount());

		return companyDtoPageBean;
	}

	@Override
	public PageBean<Supplier> findSupplierByArea(Integer provinceId, Integer cityId, Integer areaId, Integer page, Integer pageSize) {
		PageBean<Supplier> pageBean = new PageBean<Supplier>();
		pageBean.setPage(page);
		pageBean.setPageSize(pageSize);
		Supplier supplier = new Supplier();
		supplier.setOrganizationType(1);	//线下商家
		supplier.setType(12);	////普通商家
		supplier.setProvinceId(provinceId);
		supplier.setCityId(cityId);
		supplier.setAreaId(areaId);
		pageBean.setParameter(supplier);
		List<Supplier> list = supplierMapper.findSupplierByAreaPageList(pageBean);
		pageBean.setResult(list);
		return pageBean;
	}

	@Override
	public PageBean<Supplier> getPageListAll(PageBean<Supplier> pageBean) {
		logger.info("-------------getPageList start-----------------");
		List<Supplier> list = new ArrayList<Supplier>();
		list = supplierMapper.getPageListAll(pageBean);
		pageBean.setResult(list);
		return pageBean;
	}

	@Override
	public PageBean<Supplier> getPageListAllyinlian(PageBean<Supplier> pageBean) {
		logger.info("-------------getPageList start-----------------");
		List<Supplier> list = new ArrayList<Supplier>();
		list = supplierMapper.getPageListAllyinlian(pageBean);
		pageBean.setResult(list);
		return pageBean;
	}

	@Override
	public Integer delectArea(Long supplierId, Long provinceId, Long cityId, Long countyId) {
		return supplierPartnerAreaMapper.delectArea(supplierId, provinceId, cityId, countyId);
		
		
	}

	@Override
	public SupplierDetail findSupplierDetailBySupplierId(Integer supplierId) {
		SupplierDetail supplerDetail = supplierOfflineStoreMapper.findSupplierDetailBySupplierId(supplierId);
		if(null!=supplerDetail){
			List<SupplierDetailAttr>  list=supplierOfflineStoreAttrMapper.findSupplierOfflineStoreAttrByStroeId(supplerDetail.getId());
			if(list.size()>0){
				supplerDetail.setAttrList(list);
			}
		}
		return supplerDetail;
	}

	@Override
	public Integer insertDetail(SupplierDetail supplierDetail) {
		supplierOfflineStoreMapper.insertDetail(supplierDetail);
		if(supplierDetail.getAttrList()!=null){
			List<SupplierDetailAttr> attrList = supplierDetail.getAttrList();
			for(SupplierDetailAttr supplierDetailAttr: attrList){
				supplierDetailAttr.setStoreId(supplierDetail.getId());
				supplierOfflineStoreAttrMapper.insertDetailAttr(supplierDetailAttr);
			}
		}
		return supplierDetail.getId();
	}	
	
	public void updateShopTop(SupplierDetail supplierDetail){
		supplierOfflineStoreMapper.updateShopTop(supplierDetail);
	}
}
