package com.mall.controller.user;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Null;

import net.sf.json.JSONArray;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.csource.upload.UploadFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mall.architect.redis.JedisManager;
import com.mall.authority.client.constant.ConfigConstant;
import com.mall.authority.client.constant.ResponseContant;
import com.mall.authority.client.enums.SystemCodeEnum;
import com.mall.authority.client.util.CookieUtil;
import com.mall.authority.client.util.UserUtil;
import com.mall.bean.authority.Module;
import com.mall.bean.authority.User;
import com.mall.bean.authority.request.UserForm;
import com.mall.bean.authority.response.MenuBean;
import com.mall.bean.authority.response.UserBean;
import com.mall.category.api.own.TeSectorsService;
import com.mall.category.api.rpc.BankService;
import com.mall.category.api.rpc.BaseDataServiceRpc;
import com.mall.category.model.BankBranch;
import com.mall.category.model.BankCode;
import com.mall.category.po.AgentCity;
import com.mall.category.po.AgentCounty;
import com.mall.category.po.AgentProvince;
import com.mall.category.po.TcCountry;
import com.mall.category.po.TdCatePub;
import com.mall.category.po.TeSectors;
/*import com.mall.dealer.model.Dealer;
import com.mall.dealer.model.DealerFile;
import com.mall.dealer.model.DealerUser;*/
import com.mall.mybatis.utility.PageBean;
import com.mall.platform.model.PlatformChannel;
import com.mall.platform.model.PlatformDto;
import com.mall.platform.model.PlatformMenu;
import com.mall.platform.model.PlatformRole;
import com.mall.platform.model.PlatformSale;
import com.mall.platform.model.PlatformSysLog;
import com.mall.platform.model.PlatformUser;
import com.mall.platform.model.PlatformUserRole;
import com.mall.platform.model.PlatformUserRoleDTO;
/*import com.mall.retailer.model.Retailer;
import com.mall.retailer.model.RetailerFile;
import com.mall.retailer.model.RetailerQueryDto;
import com.mall.retailer.model.RetailerUser;*/
import com.mall.retailer.order.po.Order;
import com.mall.sendemail.SendHtmlMail;
import com.mall.stock.po.Warehouse;
import com.mall.supplier.enums.SupplierSouce;
import com.mall.supplier.model.Supplier;
import com.mall.supplier.model.SupplierCodeType;
import com.mall.supplier.model.SupplierPartner;
import com.mall.supplier.model.SupplierPartnerArea;
import com.mall.supplier.model.SupplierProduct;
import com.mall.supplier.model.SupplierRegionSettings;
import com.mall.supplier.model.SupplierSalesDiscount;
import com.mall.supplier.model.SupplierStore;
import com.mall.supplier.model.SupplierUser;
import com.mall.supplier.service.SupplierDiscountService;
import com.mall.supplier.service.SupplierManagerService;
import com.mall.supplier.service.SupplierRegionService;
import com.mall.supplier.service.SupplierStoreService;
import com.mall.supplier.service.SupplierUserManagerService;
import com.mall.controller.base.BaseController;
import com.mall.dto.SupplierSouces;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Common;
import com.mall.utils.Constants;
import com.mall.utils.MD5;
import com.mall.utils.ProduceUserName;
import com.mall.utils.SendSMSUtil;

/**
 * 
 * @author zhouzb
 *
 */
@Controller
public class PlatformUserController extends BaseController {

	/**
	 * . LOGGER
	 */

	private static final Logger LOGGER = LoggerFactory.getLogger(PlatformUserController.class);

	@Autowired
	private SupplierStoreService supplierStoreService;

	@Autowired
	private SupplierRegionService supplierRegionService;

	@Autowired
	private SupplierManagerService supplierManagerService;

	@Autowired
	private BaseDataServiceRpc baseDataServiceRpc2;

	@Autowired
	private SupplierUserManagerService supplierUserManagerService;
     
	@Autowired
	private TeSectorsService teSectorsService;
	
	@Autowired
	private SupplierDiscountService supplierDiscountService;
	
	@Autowired
	private  BankService  bankService;
	/*
	 * @Autowired private TeSectorsService teSectorsService;
	 */
	/**
	 * . 跳转用户登录页面
	 * 
	 * @param User
	 * @return 添加的用户id
	 */
	@RequestMapping("/login")
	public String toLogin() {
		return "/login/login";
	}

	/**
	 * . 跳转用户登录页面
	 * 
	 * @param User
	 * @return 添加的用户id
	 */
	@RequestMapping("/user/loginUI")
	public String toLoginUI() {
		return "/login/login";
	}

	/**
	 * 备案时服务异常跳转 不发送邮件
	 * 
	 */
	@RequestMapping("/user/toError")
	public String toError() {
		return "/user/error500";/// errorpage/error500
	}

	/**
	 * 获取用户列表.
	 * 
	 * @param map
	 *            Map<String,Object>
	 * @param paramUser
	 *            PlatformUser
	 * @param paramPage
	 *            PageBean<PlatformUser>
	 * @param request
	 *            HttpServletRequest
	 * @return
	 */
	@RequestMapping("/user/list")
	public String userList(Map<String, Object> map, PlatformUser paramUser, PageBean<PlatformUser> paramPage,
			HttpServletRequest request) {

		try {

			paramUser.setStatus(Constants.NUM1);
			paramUser.setPlatformId(getCurrentPlatformId());
			paramPage.setSortFields("user_id");
			paramPage.setOrder("asc");
			paramPage.setPageSize(Constants.PAGESIZE);
			paramPage.setParameter(paramUser);

			paramPage = RemoteServiceSingleton.getInstance().getPlatformUserManagerService().getPageList(paramPage);

			map.put("page", paramPage);

			LOGGER.info(JSONObject.toJSON(paramPage).toString());

			List<PlatformUserRoleDTO> voList = RemoteServiceSingleton.getInstance().getPlatformUserManagerService()
					.findUsersByPlatformId(getCurrentPlatformId());

			Map<String, String> mapvo = new HashMap<String, String>();

			for (PlatformUserRoleDTO vo : voList) {
				if (vo == null) {
					continue;
				}
				mapvo.put(vo.getUsername(), vo.getRolename());
			}

			map.put("mapvo", mapvo);

			List<PlatformRole> roles = RemoteServiceSingleton.getInstance().getPlatformRoleManagerService()
					.findAllRole();

			map.put("roles", roles);

			if (null != roles) {
				Map<String, String> rolemap = new HashMap<String, String>();
				for (PlatformRole role : roles) {
					if (role == null) {
						continue;
					}
					rolemap.put(role.getName(), role.getRoleId() + "");
				}
				map.put("rolemap", rolemap);
			}
		} catch (Exception e) {

			LOGGER.error(e.getMessage() + e);
			LOGGER.error("商户Id:" + getCurrentPlatformId());
			LOGGER.error("用户:" + getCurrentUser().getUsername());
			LOGGER.error("用户ID:" + getCurrentUser().getId());

		}
		return "user/list";

	}

	/**
	 * 获取用户系统操作记录列表.
	 * 
	 * @param map
	 *            Map<String,Object>
	 * @param paramUser
	 *            PlatformUser
	 * @param paramPage
	 *            PageBean<PlatformUser>
	 * @param request
	 *            HttpServletRequest
	 * @return
	 */
	@RequestMapping("/user/listsyslog")
	public String listsyslog(Map<String, Object> map, PlatformSysLog paramSysLog, PageBean<PlatformSysLog> paramPage,
			HttpServletRequest request) {

		try {
			Date date = new Date();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String endtime = simpleDateFormat.format(date);
			long time = date.getTime();
			String iString = "2592000000";
			Long long1 = Long.valueOf(iString);
			long startdate = time - long1;
			String string = simpleDateFormat.format(startdate);
			if (paramSysLog.getsTime() == null) {
				paramSysLog.setsTime(string);
			}
			if (paramSysLog.geteTime() == null) {
				paramSysLog.seteTime(endtime);
			}
			// paramSysLog.setStatus(Constants.NUM1);
			// paramSysLog.setPlatformId(getCurrentPlatformId());
			paramPage.setSortFields("id");
			paramPage.setOrder("desc");
			paramPage.setPageSize(20);
			// paramPage.setPageSize(Constants.PAGESIZE);
			paramPage.setParameter(paramSysLog);

			paramPage = RemoteServiceSingleton.getInstance().getPlatformUserManagerService()
					.getSysLogPageList(paramPage);

			map.put("page", paramPage);

			LOGGER.info(JSONObject.toJSON(paramPage).toString());

		} catch (Exception e) {
			LOGGER.error(e.getMessage() + e);
		}
		return "user/listsyslog";

	}

	/**
	 * 注册用户名验证.
	 * 
	 * @param pin
	 *            String
	 * @param response
	 *            HttpServletResponse
	 */
	@RequestMapping("/user/isPinEngaged")
	@ResponseBody
	public String isPinEngaged(String pin, HttpServletResponse response) {
		int count = 1;
		try {
			Map<String, String> pmap = new LinkedHashMap<String, String>();

			if (!Common.isEmpty(pin)) {
				pmap.put("loginName", pin);
				count = RemoteServiceSingleton.getInstance().getPlatformUserManagerService().getUserByName(pmap);
			}
			LOGGER.info(count + "");
		} catch (Exception e) {
			LOGGER.error(e.getMessage() + e);
			LOGGER.error("商户Id:" + getCurrentPlatformId());
			LOGGER.error("用户:" + getCurrentUser().getUsername());
			LOGGER.error("用户ID:" + getCurrentUser().getId());
		}
		return count + "";
	}

	/**
	 * 商户用户.
	 * 
	 * @param loginname
	 *            返回登录成功的用户id
	 * @param password
	 *            此处为未经过md5加密的密码
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return URL
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/user/login")
	public String loginUser(String loginname, String password, HttpServletRequest request,
			HttpServletResponse response) {
		String checkValue = request.getParameter("passwordCheck");
		UserForm userform = new UserForm();
		userform.setUsername(loginname);
		userform.setPassword(password);
		userform.setSystemCode(SystemCodeEnum.WOFE.getName());

		try {
			UserBean userbean = RemoteServiceSingleton.getInstance().getAuthorityClient().login(userform);
			User user = userbean.getUser() == null ? null : userbean.getUser();

			Map<String, String> authorMap = userbean.getAuthorityKey() == null ? new HashMap<String, String>()
					: userbean.getAuthorityKey();

			String authroityKey = authorMap.get(SystemCodeEnum.WOFE.getName());

			LOGGER.info("loginUser attr authroityKey:" + authroityKey);
			if (authroityKey == null) {
				request.setAttribute("loginname", loginname);
				request.setAttribute("message", "不是该系统用户");
				return ResponseContant.LOGIN_PAGE;
			}

			if (user == null) {
				request.setAttribute("loginname", loginname);
				UserUtil.userOpRecord(null, loginname, request);
				request.setAttribute("message", "您输入的账户名和密码不匹配，请重新输入");
				return ResponseContant.LOGIN_PAGE;
			}

			String cookieValue = userbean.getCookieValue();

			// 获取当前用户下有权限的菜单
			// 是否登陆成功，成功跳转到首页，没有跳转到登陆页面
			if (user != null) {
				CookieUtil.setCookie(response, ConfigConstant.MEMBER, cookieValue, ConfigConstant.MAX_AGE_ONE_MONTH);
				CookieUtil.setCookie(response, ConfigConstant.AUTHORITY_KEY, authroityKey,
						ConfigConstant.MAX_AGE_ONE_MONTH);

				// JedisManager.setObject(sid,ConfigConstant.OUT_TIME_1800,
				// user);
				// 过滤掉 当前用户没有的菜单项
				userform.setId(user.getId());
				userform.setAuthorityKey(authroityKey);
				MenuBean menuBean = RemoteServiceSingleton.getInstance().getAuthorityClient()
						.findUserAuthorityMenu(userform);

				/** 当前用户加载的系统tree */
				List<Module> listModule = menuBean.getModuleList() == null ? new ArrayList<Module>()
						: menuBean.getModuleList();
				if (listModule.isEmpty()) {
					request.setAttribute("loginname", loginname);
					UserUtil.userOpRecord(null, loginname, request);
					request.setAttribute("message", "您的账号没有权限访问此页面!");
					return ResponseContant.LOGIN_PAGE;
				}

				List<Module> listAllModule = menuBean.getAllModuleList();
				List<String> listAllUrl = new ArrayList<String>();
				for (Module mm : listAllModule) {
					listAllUrl.add(mm.getUrl());
				}

				JedisManager.setObject("listAllUrl" + user.getUsername(), 18000, listAllUrl);
				request.setAttribute("loginUser", user);

				UserUtil.userOpRecord(user.getId(), user.getUsername(), request);
				return "redirect:/platform/welcome";
			} else {
				request.setAttribute("loginname", loginname);
				if (checkValue != null) {
					request.setAttribute("password", password);
					request.setAttribute("checkValue", checkValue);
				}
				UserUtil.userOpRecord(null, loginname, request);
				request.setAttribute("message", "名字或密码不存在或错误!");
				return ResponseContant.LOGIN_PAGE;
			}
		} catch (Exception e) {
			UserUtil.userOpRecord(null, loginname, request);
			LOGGER.error("loginUser error:" + e.getStackTrace());
			request.setAttribute("message", "登陆异常!" + e.getMessage());
			return ResponseContant.LOGIN_PAGE;
		}
	}

	/**
	 * 添加一个用户.
	 * 
	 * @param user
	 *            User
	 * @param roleId
	 *            Long
	 * @return 添加的用户id
	 */
	@RequestMapping("/user/save")
	public String saveUser(PlatformUser user, Long roleId) {
		try {
			Map<String, String> pmap = new LinkedHashMap<String, String>();
			int count = 1;
			if (!Common.isEmpty(user.getName())) {
				pmap.put("loginName", user.getName());
				pmap.put("status", "1");
				count = RemoteServiceSingleton.getInstance().getPlatformUserManagerService().getUserByName(pmap);
				if (!(count > 0)) {
					user.setIsAdmin(Constants.NUM0);
					user.setStatus(Constants.NUM1);
					user.setPassword(MD5.encrypt(user.getPassword()));
					user.setPlatformId(getCurrentPlatformId());
					Long userId = RemoteServiceSingleton.getInstance().getPlatformUserManagerService().add(user);
					PlatformUserRole userRole = new PlatformUserRole();
					userRole.setRoleId(roleId);
					userRole.setUserId(userId);
					RemoteServiceSingleton.getInstance().getPlatformRoleManagerService().addUserRole(userRole);
				}
			}
		} catch (Exception e) {
			LOGGER.error("添加一个用户. 异常  msg：" + e.getMessage());
			LOGGER.error("商户Id:" + getCurrentPlatformId());
			LOGGER.error("用户:" + getCurrentUser().getUsername());
			LOGGER.error("用户ID:" + getCurrentUser().getId());
		}
		return "redirect:/user/list";

	}

	/**
	 * 修改用户信息 .
	 * 
	 * @param user
	 *            PlatformUser
	 * @param roleId
	 *            Long
	 * @return
	 */
	@RequestMapping("/user/update")
	public String updateUser(PlatformUser user, Long roleId) {

		LOGGER.info("roleId=" + roleId + "  Uname =" + user.getName() + " pwd=" + user.getPassword());

		try {
			if (!Common.isEmpty(user.getName())) {
				user.setIsAdmin(0);
				user.setStatus(1);
				user.setPassword(MD5.encrypt(user.getPassword()));
				user.setPlatformId(getCurrentPlatformId());
				RemoteServiceSingleton.getInstance().getPlatformUserManagerService().update(user);
				PlatformUserRole userRole = new PlatformUserRole();
				userRole.setRoleId(roleId);
				userRole.setUserId(user.getUserId());
				RemoteServiceSingleton.getInstance().getPlatformRoleManagerService().updateUserRole(userRole);

			}
		} catch (Exception e) {
			LOGGER.error("修改用户信息 . 异常  msg：" + e.getMessage());
			LOGGER.error("商户Id:" + getCurrentPlatformId());
			LOGGER.error("用户:" + getCurrentUser().getUsername());
			LOGGER.error("用户ID:" + getCurrentUser().getId());
		}
		return "redirect:/user/list";

	}

	/**
	 * 删除单个用户.
	 * 
	 * @param id
	 *            Long
	 * @return URL
	 */
	@RequestMapping("/user/delete")
	public String deleteUserById(Long id) {
		try {
			RemoteServiceSingleton.getInstance().getPlatformUserManagerService().delete(id);
		} catch (Exception e) {
			LOGGER.error("删除单个用户. 异常  msg：" + e.getMessage());
		}

		return "redirect:/user/list";
	}

	/**
	 * 批量删除用户.
	 * 
	 * @param nn
	 *            Long[]
	 * @return URL
	 */
	@RequestMapping("/user/deleteByIds")
	public String deleteUserById(Long[] nn) {
		try {
			List<Long> asList = Arrays.asList(nn);
			RemoteServiceSingleton.getInstance().getPlatformUserManagerService().deleteAll(asList);
		} catch (Exception e) {
			LOGGER.error("删除多个用户. 异常  msg：" + e.getMessage());
			LOGGER.error("商户Id:" + getCurrentPlatformId());
			LOGGER.error("用户:" + getCurrentUser().getUsername());
			LOGGER.error("用户ID:" + getCurrentUser().getId());
		}

		return "redirect:/user/list";
	}

	@RequestMapping("/user/updateCheck1")
	@ResponseBody
	public String userCheck1(Long id, String reason) {
		try {
			Supplier supplier = new Supplier();
			supplier.setSupplierId(id);
			supplier.setAuditTime(new Date());
			supplier.setCheckFailReason(reason);
			int supplierCodeNum = RemoteServiceSingleton.getInstance().getSupplierManagerService()
					.getSupplierCodeNumByType("QJ");
			boolean supplierCodeFlag = true;
			while (supplierCodeFlag) {
				String numCode = "";
				if (supplierCodeNum < 10) {
					numCode = "000" + supplierCodeNum;
				} else if (supplierCodeNum >= 10 && supplierCodeNum < 100) {
					numCode = "00" + supplierCodeNum;
				} else if (supplierCodeNum >= 100 && supplierCodeNum < 1000) {
					numCode = "0" + supplierCodeNum;
				} else {
					numCode = supplierCodeNum + "";
				}
				String isExist = RemoteServiceSingleton.getInstance().getSupplierManagerService()
						.checkSupplierCodeIsExists("QJ" + numCode);
				if (isExist.equals("0")) { // 不存在
					supplierCodeFlag = false;
				} else {
					supplierCodeNum = supplierCodeNum + 1;
				}
				supplier.setSupplierCode("QJ" + numCode);
			}
			Integer newSupplierCodeNum = supplierCodeNum + 1;
			SupplierCodeType newSupplierCodeType = new SupplierCodeType();
			newSupplierCodeType.setSupplierCodeNum(newSupplierCodeNum);
			newSupplierCodeType.setSupplierCodeType("QJ");
			RemoteServiceSingleton.getInstance().getSupplierManagerService()
					.updateSupplierCodeNumByType(newSupplierCodeType);

			SupplierManagerService supplierManagerService = RemoteServiceSingleton.getInstance()
					.getSupplierManagerService();
			supplierManagerService.updateSupplier(supplier);
			supplier = supplierManagerService.findSupplier(id);
			SupplierStore temp = new SupplierStore();
			temp.setSupplierId(id.intValue());
			temp.setStoreName(supplier.getName());
			temp.setCreateBy(0);
			supplierStoreService.insertStore(temp);

			Warehouse warehouse = new Warehouse();
			warehouse.setWarehouseCode(Integer.valueOf(supplier.getSupplierId().toString()));
			warehouse.setWarehouseType(Short.parseShort(7 + ""));
			warehouse.setWarehouseLevelCode(Short.parseShort(1 + ""));
			warehouse.setWarehouseLevelName("一级库房");
			warehouse.setWarehouseStatus(Short.parseShort(0 + ""));
			warehouse.setWarehouseName("第三方国际仓库" + supplier.getName());
			warehouse.setWarehouseTypeName("第三方国际商品仓库");
			warehouse.setDealerId(7735L);// 北京世纪新干线网络技术有限公司
			warehouse.setProvinceId(0l);
			warehouse.setCityId(0l);
			warehouse.setAreaId(0l);
			warehouse.setAddress("北京 北京市 朝阳区 霄云中心B座");
			warehouse.setContact("POP");
			warehouse.setTelephone(supplier.getPhone() != null ? Long.parseLong(supplier.getPhone()) : 0L);
			warehouse.setChannelCode(1l);
			warehouse.setChannelName("海外直邮");
			String paymentType = "3";
			// 3 走国内支付 目前只有支付宝国内支付
			if (org.apache.commons.lang3.StringUtils.isBlank(paymentType)) {
				// paymentType = "1";
				paymentType = "3";
			}
			warehouse.setAccountType(Integer.parseInt(paymentType));
			RemoteServiceSingleton.getInstance().getStockWofeService().insertWarehouse(warehouse);

			return "1";
		} catch (Exception e) {
			return "0";
		}
	}
	
	@RequestMapping("/user/updateCheckLineOK")
	@ResponseBody
	public String userCheckLineOK(Long id, String reason) {
		try {
			Supplier supplier = new Supplier();
			supplier.setSupplierId(id);
			supplier.setAuditTime(new Date());
			supplier.setCheckFailReason(reason);
			int supplierCodeNum = RemoteServiceSingleton.getInstance().getSupplierManagerService()
					.getSupplierCodeNumByType("QJ");
			boolean supplierCodeFlag = true;
			while (supplierCodeFlag) {
				String numCode = "";
				if (supplierCodeNum < 10) {
					numCode = "000" + supplierCodeNum;
				} else if (supplierCodeNum >= 10 && supplierCodeNum < 100) {
					numCode = "00" + supplierCodeNum;
				} else if (supplierCodeNum >= 100 && supplierCodeNum < 1000) {
					numCode = "0" + supplierCodeNum;
				} else {
					numCode = supplierCodeNum + "";
				}
				String isExist = RemoteServiceSingleton.getInstance().getSupplierManagerService()
						.checkSupplierCodeIsExists("QJ" + numCode);
				if (isExist.equals("0")) { // 不存在
					supplierCodeFlag = false;
				} else {
					supplierCodeNum = supplierCodeNum + 1;
				}
				supplier.setSupplierCode("QJ" + numCode);
			}
			Integer newSupplierCodeNum = supplierCodeNum + 1;
			SupplierCodeType newSupplierCodeType = new SupplierCodeType();
			newSupplierCodeType.setSupplierCodeNum(newSupplierCodeNum);
			newSupplierCodeType.setSupplierCodeType("QJ");
			RemoteServiceSingleton.getInstance().getSupplierManagerService()
					.updateSupplierCodeNumByType(newSupplierCodeType);

			SupplierManagerService supplierManagerService = RemoteServiceSingleton.getInstance()
					.getSupplierManagerService();
			supplierManagerService.updateSupplier(supplier);
			supplier = supplierManagerService.findSupplier(id);
			SupplierStore temp = new SupplierStore();
			temp.setSupplierId(id.intValue());
			temp.setStoreName(supplier.getName());
			temp.setCreateBy(0);
			supplierStoreService.insertStore(temp);

			Warehouse warehouse = new Warehouse();
			warehouse.setWarehouseCode(Integer.valueOf(supplier.getSupplierId().toString()));
			warehouse.setWarehouseType(Short.parseShort(7 + ""));
			warehouse.setWarehouseLevelCode(Short.parseShort(1 + ""));
			warehouse.setWarehouseLevelName("一级库房");
			warehouse.setWarehouseStatus(Short.parseShort(0 + ""));
			warehouse.setWarehouseName("第三方国际仓库" + supplier.getName());
			warehouse.setWarehouseTypeName("第三方国际商品仓库");
			warehouse.setDealerId(7735L);// 北京世纪新干线网络技术有限公司
			warehouse.setProvinceId(0l);
			warehouse.setCityId(0l);
			warehouse.setAreaId(0l);
			warehouse.setAddress("北京 北京市 朝阳区 霄云中心B座");
			warehouse.setContact("POP");
			warehouse.setTelephone(supplier.getPhone() != null ? Long.parseLong(supplier.getPhone()) : 0L);
			warehouse.setChannelCode(1l);
			warehouse.setChannelName("海外直邮");
			String paymentType = "3";
			// 3 走国内支付 目前只有支付宝国内支付
			if (org.apache.commons.lang3.StringUtils.isBlank(paymentType)) {
				// paymentType = "1";
				paymentType = "3";
			}
			warehouse.setAccountType(Integer.parseInt(paymentType));
			RemoteServiceSingleton.getInstance().getStockWofeService().insertWarehouse(warehouse);

			return "1";
		} catch (Exception e) {
			return "0";
		}
	}
	
	
	@RequestMapping("/user/updateCheckLineNO")
	@ResponseBody
	public String updateCheckLineNO(String id, String reason) {
		try {
			SupplierManagerService supplierManagerService = RemoteServiceSingleton.getInstance()
					.getSupplierManagerService();
			Supplier supplier = new Supplier();
			supplier.setSupplierId(Long.parseLong(id));
			supplier.setAuditTime(new Date());
			supplier.setCheckFailReason(reason);
			supplierManagerService.updatetoSupplier(supplier);
			return "1";
		} catch (Exception e) {
			return "0";
		}
	}

	@RequestMapping("/user/updateCheck3")
	@ResponseBody
	public String userCheck3(Long id, String reason, String companyQyId, String sjSupplierCodeId, Integer typeId,
			BigDecimal noPayHqqVal, BigDecimal fanliPriceVal) {
		try {
			Supplier supplier = new Supplier();
			supplier.setSupplierId(id);
			supplier.setAuditTime(new Date());
			supplier.setCheckFailReason(reason);
			supplier.setCompanyQy(companyQyId);
			Supplier modifySjSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
					.getSupplierBySupplierCode(sjSupplierCodeId);
			supplier.setSjSupplierId(modifySjSupplier.getSupplierId().toString());
			supplier.setType(typeId);
			supplier.setNoPayhqq(noPayHqqVal);
			supplier.setFanliPrice(fanliPriceVal);

			int supplierCodeNum = RemoteServiceSingleton.getInstance().getSupplierManagerService()
					.getSupplierCodeNumByType("QH");
			boolean supplierCodeFlag = true;
			while (supplierCodeFlag) {
				String numCode = "";
				if (supplierCodeNum < 10) {
					numCode = "000" + supplierCodeNum;
				} else if (supplierCodeNum >= 10 && supplierCodeNum < 100) {
					numCode = "00" + supplierCodeNum;
				} else if (supplierCodeNum >= 100 && supplierCodeNum < 1000) {
					numCode = "0" + supplierCodeNum;
				} else {
					numCode = supplierCodeNum + "";
				}
				String isExist = RemoteServiceSingleton.getInstance().getSupplierManagerService()
						.checkSupplierCodeIsExists("QH" + numCode);
				if (isExist.equals("0")) { // 不存在
					supplierCodeFlag = false;
				} else {
					supplierCodeNum = supplierCodeNum + 1;
				}
				supplier.setSupplierCode("QH" + numCode);
			}
			Integer newSupplierCodeNum = supplierCodeNum + 1;
			SupplierCodeType newSupplierCodeType = new SupplierCodeType();
			newSupplierCodeType.setSupplierCodeNum(newSupplierCodeNum);
			newSupplierCodeType.setSupplierCodeType("QH");
			RemoteServiceSingleton.getInstance().getSupplierManagerService()
					.updateSupplierCodeNumByType(newSupplierCodeType);

			SupplierManagerService supplierManagerService = RemoteServiceSingleton.getInstance()
					.getSupplierManagerService();
			supplierManagerService.updateSupplier(supplier);
			Supplier findSupplier = supplierManagerService.findSupplier(id);
			if (!StringUtils.isEmpty(findSupplier.getManagerBackup())
					&& !(findSupplier.getManager().equals(findSupplier.getManagerBackup()))) {
				supplier.setManager(findSupplier.getManagerBackup());
			}
			if (!StringUtils.isEmpty(findSupplier.getManagerTelBackup())
					&& !(findSupplier.getManagerTel().equals(findSupplier.getManagerTelBackup()))) {
				supplier.setManagerTel(findSupplier.getManagerTelBackup());
			}
			if (!StringUtils.isEmpty(findSupplier.getKfTelBackup())
					&& !(findSupplier.getKfTel().equals(findSupplier.getKfTelBackup()))) {
				supplier.setKfTel(findSupplier.getKfTelBackup());
			}
			if (!StringUtils.isEmpty(findSupplier.getAddressBackup())
					&& !(findSupplier.getAddress().equals(findSupplier.getAddressBackup()))) {
				supplier.setAddress(findSupplier.getAddressBackup());
			}
			if (!StringUtils.isEmpty(findSupplier.getContactBackup())
					&& !(findSupplier.getContact().equals(findSupplier.getContactBackup()))) {
				supplier.setContact(findSupplier.getContactBackup());
			}
			if (!StringUtils.isEmpty(findSupplier.getPhoneBackup())
					&& !(findSupplier.getPhone().equals(findSupplier.getPhoneBackup()))) {
				supplier.setPhone(findSupplier.getPhoneBackup());
			}
			if (!StringUtils.isEmpty(findSupplier.getEmailBackup())
					&& !(findSupplier.getEmail().equals(findSupplier.getEmailBackup()))) {
				supplier.setEmail(findSupplier.getEmailBackup());
			}
			if (!StringUtils.isEmpty(findSupplier.getFaxBackup())
					&& !(findSupplier.getFax().equals(findSupplier.getFaxBackup()))) {
				supplier.setFax(findSupplier.getFaxBackup());
			}
			if (!StringUtils.isEmpty(findSupplier.getCountryAreaBackup())
					&& !(findSupplier.getCountryArea().equals(findSupplier.getCountryAreaBackup()))) {
				supplier.setCountryArea(findSupplier.getCountryAreaBackup());
			}
			if (!StringUtils.isEmpty(findSupplier.getPostBackup())
					&& !((String.valueOf(findSupplier.getPost()).equals(findSupplier.getPostBackup())))) {
				supplier.setPost(Integer.valueOf(findSupplier.getPostBackup()));
			}
			if (!StringUtils.isEmpty(findSupplier.getLogoImgurlBackup())
					&& !(findSupplier.getLogoImgurl().equals(findSupplier.getLogoImgurlBackup()))) {
				supplier.setLogoImgurl(findSupplier.getLogoImgurlBackup());
			}
			supplier.setSupplyType(51);
			supplier.setIsOnekeySend(1);
			supplier.setAuditTime(new Date());
			supplier.setModifyStatus(0);
			supplierManagerService.updateModifyInfo(supplier);
			SupplierStore temp = new SupplierStore();
			temp.setSupplierId(id.intValue());
			temp.setStoreName(findSupplier.getName());
			temp.setCreateBy(0);
			supplierStoreService.insertStore(temp);

			Warehouse warehouse = new Warehouse();
			warehouse.setWarehouseCode(Integer.valueOf(supplier.getSupplierId().toString()));
			warehouse.setWarehouseType(Short.parseShort(7 + ""));
			warehouse.setWarehouseLevelCode(Short.parseShort(1 + ""));
			warehouse.setWarehouseLevelName("一级库房");
			warehouse.setWarehouseStatus(Short.parseShort(0 + ""));
			warehouse.setWarehouseName("第三方国际仓库" + supplier.getName());
			warehouse.setWarehouseTypeName("第三方国际商品仓库");
			warehouse.setDealerId(7735L);// 北京世纪新干线网络技术有限公司
			warehouse.setProvinceId(0l);
			warehouse.setCityId(0l);
			warehouse.setAreaId(0l);
			warehouse.setAddress("北京 北京市 朝阳区 霄云中心B座");
			warehouse.setContact("POP");
			warehouse.setTelephone(findSupplier.getPhone() != null ? Long.parseLong(findSupplier.getPhone()) : 0L);
			warehouse.setChannelCode(1l);
			warehouse.setChannelName("海外直邮");
			String paymentType = "3";
			// 3 走国内支付 目前只有支付宝国内支付
			if (org.apache.commons.lang3.StringUtils.isBlank(paymentType)) {
				// paymentType = "1";
				paymentType = "3";
			}
			warehouse.setAccountType(Integer.parseInt(paymentType));
			RemoteServiceSingleton.getInstance().getStockWofeService().insertWarehouse(warehouse);

			return "1";
		} catch (Exception e) {
			return "0";
		}
	}

	@RequestMapping("/user/updateCheck2")
	@ResponseBody
	public String userCheck2(String id, String reason) {
		try {
			SupplierManagerService supplierManagerService = RemoteServiceSingleton.getInstance()
					.getSupplierManagerService();
			Supplier supplier = new Supplier();
			supplier.setSupplierId(Long.parseLong(id));
			supplier.setAuditTime(new Date());
			supplier.setCheckFailReason(reason);
			supplierManagerService.updatetoSupplier(supplier);
			return "1";
		} catch (Exception e) {
			return "0";
		}
	}

	@RequestMapping("/user/updateCheck4")
	@ResponseBody
	public String userCheck4(String id, String reason) {
		try {
			SupplierManagerService supplierManagerService = RemoteServiceSingleton.getInstance()
					.getSupplierManagerService();
			Supplier supplier = new Supplier();
			supplier.setSupplierId(Long.parseLong(id));
			supplier.setAuditTime(new Date());
			supplier.setCheckFailReason(reason);
			supplierManagerService.updatetoSupplier(supplier);
			return "1";
		} catch (Exception e) {
			return "0";
		}
	}

	@RequestMapping("/user/saveupdate")
	@ResponseBody
	public String saveupdate(Supplier supplier) {
		try {
			SupplierManagerService supplierManagerService = RemoteServiceSingleton.getInstance()
					.getSupplierManagerService();
			supplierManagerService.saveupdateSupplier(supplier);
			return Constants.SUCCESS;

		} catch (Exception e) {
			return Constants.ERROR;
		}

	}

	// 冻结
	@RequestMapping("/user/dongAgent")
	@ResponseBody
	public void freezeAgent(String id) {
		supplierUserManagerService.updateSupplier(id);
	}

	//解冻
	@RequestMapping("/user/jieDongAgent")
	@ResponseBody
	public void jieDongAgent(String id ){
		supplierUserManagerService.frozenSupplier(id);
	}
	
    //修改商家
	@RequestMapping("/user/saveupdateLine")
	@ResponseBody
	public String saveupdateLine(Supplier supplier,BigDecimal salesDiscount) {
		try {
            //保存supplier对象
			int updataSupplier = supplierManagerService.updataSupplier(supplier);
			//保存省市区
			Long supplierId = supplier.getSupplierId();
			Integer provinceId2 = supplier.getProvinceId();
			Integer cityId2 = supplier.getCityId();
			Integer areaId2 = supplier.getAreaId();
			SupplierPartnerArea supplierPartnerArea = new SupplierPartnerArea();
			supplierPartnerArea.setSupplierId(supplierId);
			long provinceId = provinceId2.longValue();
			supplierPartnerArea.setProvinceId(provinceId);
			if (cityId2 != null) {
				long cityId = cityId2.longValue();
				supplierPartnerArea.setCityId(cityId);
			}
			if (areaId2 != null) {
				long areaId = areaId2.longValue();
				supplierPartnerArea.setCountyId(areaId);
			}

			// 通过supplierId去判断
			SupplierPartnerArea supplierPartnerArea2 = supplierManagerService.findPartnerArea(supplierId, null);
			if (supplierPartnerArea2 != null) {
				Integer row = supplierManagerService.updateSupplierPartnerAera(supplierPartnerArea);
			} else {
				supplierPartnerArea.setStatus(1);
				int row = supplierManagerService.insertSupplierPartnerArea(supplierPartnerArea);
			}

			//保存折扣
			int i = supplierDiscountService.setSalesDiscount(supplierId, salesDiscount);
			return Constants.SUCCESS;
		} catch (Exception e) {
			return Constants.ERROR;

		}

	}
	//修改文件上传
		@RequestMapping("/user/fileUpload")
		@ResponseBody
		public String fileUpload(Supplier supplier,MultipartFile idCardFrontTo, MultipartFile idCardVersoTo,
				MultipartFile businessLicenseTo, HttpServletRequest request, MultipartFile companyStoreLogoTo,
				MultipartFile companyLegitimacy, MultipartFile companyDetailed, String name, Long supplierId) {
			
				String idCardFrontUrl = "";
				String idCardVersoUrl = "";
				String businessLicenseUrl = "";
				try {
					// 身份证正面
					if (null != idCardFrontTo && !"".equals(Common.getFileExt2(idCardFrontTo.getOriginalFilename()))) {
						idCardFrontUrl = UploadFileUtil.uploadFile(idCardFrontTo.getInputStream(),
								Common.getFileExt2(idCardFrontTo.getOriginalFilename()), null);
					}
					// 身份证反面
					if (null != idCardVersoTo && !"".equals(Common.getFileExt2(idCardVersoTo.getOriginalFilename()))) {
						idCardVersoUrl = UploadFileUtil.uploadFile(idCardVersoTo.getInputStream(),
								Common.getFileExt2(idCardVersoTo.getOriginalFilename()), null);
					}
					// 营业执照
					if (null != businessLicenseTo
							&& !"".equals(Common.getFileExt2(businessLicenseTo.getOriginalFilename()))) {
						businessLicenseUrl = UploadFileUtil.uploadFile(businessLicenseTo.getInputStream(),
								Common.getFileExt2(businessLicenseTo.getOriginalFilename()), null);
					}
				} catch (Exception e) {
					LOGGER.error("注册：上传文件到图片服务器出错！", e);
				}
				if (null != idCardFrontTo && !"".equals(Common.getFileExt2(idCardFrontTo.getOriginalFilename()))) {
					supplier.setIdCardFront(idCardFrontUrl);
				}
				if (null != idCardVersoTo && !"".equals(Common.getFileExt2(idCardVersoTo.getOriginalFilename()))) {
					supplier.setIdCardVerso(idCardVersoUrl);
				}
				if (null != businessLicenseTo && !"".equals(Common.getFileExt2(businessLicenseTo.getOriginalFilename()))) {
					supplier.setBusinessLicense(businessLicenseUrl);
				}
				// 默认图片logo地址
				supplier.setLogoImgurl("group1/M00/00/31/CgAAElgxxt-Aa7U1AAAjS6o85T0082.jpg");
			
			String companyStoreLogoUrl = "";
			String companyLegitimacyUrl = "";
			String companyDetailedUrl = "";
			try {
				// 经营门头照
				if (null != companyStoreLogoTo
						&& !"".equals(Common.getFileExt2(companyStoreLogoTo.getOriginalFilename()))) {
					companyStoreLogoUrl = UploadFileUtil.uploadFile(companyStoreLogoTo.getInputStream(),
							Common.getFileExt2(companyStoreLogoTo.getOriginalFilename()), null);
				}
				// 证明企业合法性的证明文件
				if (null != companyLegitimacy && !"".equals(Common.getFileExt2(companyLegitimacy.getOriginalFilename()))) {
					companyLegitimacyUrl = UploadFileUtil.uploadFile(companyLegitimacy.getInputStream(),
							Common.getFileExt2(companyLegitimacy.getOriginalFilename()), null);
				}
				// 公司详情文件
				if (null != companyDetailed && !"".equals(Common.getFileExt2(companyDetailed.getOriginalFilename()))) {
					companyDetailedUrl = UploadFileUtil.uploadFile(companyDetailed.getInputStream(),
							Common.getFileExt2(companyDetailed.getOriginalFilename()), null);
				}
			} catch (Exception e) {
				LOGGER.error("注册：上传文件到图片服务器出错！", e);
			}
			if (null != companyStoreLogoTo && !"".equals(Common.getFileExt2(companyStoreLogoTo.getOriginalFilename()))) {
				supplier.setCompanyStoreLogo(companyStoreLogoUrl);
			}
			if (null != companyLegitimacy && !"".equals(Common.getFileExt2(companyLegitimacy.getOriginalFilename()))) {
				supplier.setCompanyLegitimacyUrl(companyLegitimacyUrl);
			}
			if (null != companyDetailed && !"".equals(Common.getFileExt2(companyDetailed.getOriginalFilename()))) {
				supplier.setCompanyDetailedUrl(companyDetailedUrl);
			}
			supplier.setSupplierId(supplierId);
			int result = RemoteServiceSingleton.getInstance().getSupplierManagerService().updataSupplier(supplier);
			return "/views/user/supplierInfoAgent.jsp";
		}
   //修改代理
	@RequestMapping("/user/saveupdateAgent")
	@ResponseBody
	public String saveupdateAgent(Supplier supplier) {
		try {

			BankBranch bank = bankService.findBankBranchByCode(supplier.getAccoutBankno());
			supplier.setAccoutBankname(bank.getBankBranchName());
			supplierManagerService.updataSupplier(supplier);
			Long supplierId = supplier.getSupplierId();
			Integer provinceId2 = supplier.getProvinceId();
			Integer cityId2 = supplier.getCityId();
			Integer areaId2 = supplier.getAreaId();

			SupplierPartnerArea supplierPartnerArea = new SupplierPartnerArea();
			supplierPartnerArea.setSupplierId(supplierId);
			if (provinceId2 != null) {
				long provinceId = provinceId2.longValue();
				supplierPartnerArea.setProvinceId(provinceId);
				supplierPartnerArea.setType(3);

				if (cityId2 != null) {
					long cityId = cityId2.longValue();
					supplierPartnerArea.setCityId(cityId);
					supplierPartnerArea.setType(2);

					if (areaId2 != null) {
						long areaId = areaId2.longValue();
						supplierPartnerArea.setCountyId(areaId);
						supplierPartnerArea.setType(1);
					}

				}
			}

			// 通过supplierId去判断
			SupplierPartnerArea supplierPartnerArea2 = supplierManagerService.findPartnerArea(supplierId, null);
			if (supplierPartnerArea2 != null) {
				Integer row = supplierManagerService.updateSupplierPartnerAera(supplierPartnerArea);
			} else {
				supplierPartnerArea.setPartnerType(2);
				supplierPartnerArea.setStatus(1);
				int row = supplierManagerService.insertSupplierPartnerArea(supplierPartnerArea);
			}

			return Constants.SUCCESS;

		} catch (Exception e) {
			return Constants.ERROR;
		}

	}
	/*
	 * 
	 * 保存 省市区
	 */

	/**
	 * 审核商户.
	 * 
	 * @param source
	 *            int
	 * @param id1
	 *            long
	 * @param status1
	 *            int
	 * @param request
	 *            HttpServletRequest
	 * @return URL
	 */
	@RequestMapping("/user/updateCheck")
	@ResponseBody
	public String userCheck(String paymentDays, String isBillPayment, int source, long id1, int status1, int isRecord,
			String reason, int supplyType, HttpServletRequest request, String companyQy, String companyRegion,
			int type) {

		LOGGER.info(request.getParameter("id1") + "--" + "" + request.getParameter("status1") + "--"
				+ request.getParameter("source") + "------------" + reason);

		String resaultJsonString = Constants.SUCCESS;

		String isOnekeySend = request.getParameter("isOnekeySend");
		if (org.apache.commons.lang3.StringUtils.isBlank(isOnekeySend)) {
			isOnekeySend = "1";
		}

		// 加入pop的支付方式选择
		String paymentType = request.getParameter("paymentType");
		// 3 走国内支付 目前只有支付宝国内支付
		paymentType = "3";
		if (org.apache.commons.lang3.StringUtils.isBlank(paymentType)) {
			// paymentType = "1";
			paymentType = "3";
		}

		if (null == request.getParameter("id1") || null == request.getParameter("status1")
				|| null == request.getParameter("source")) {
			resaultJsonString = Constants.SAMEISNULL;

			return resaultJsonString;
		}

		try {

			if (source == Constants.INT1) {// 供应商

				Supplier supplier = new Supplier();
				Supplier findSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
						.findSupplier(id1);
				supplier.setSupplierId(id1);
				supplier.setStatus(status1);
				// supplier.setSupplyType(supplyType);
				SupplierUser user = RemoteServiceSingleton.getInstance().getSupplierManagerService()
						.findAdminUserByMerchantId(id1);

				if (user == null) {

					resaultJsonString = Constants.SAMEISNULL;

					return resaultJsonString;

				}

				String subject = "zhongjumall商户审核结果";
				if (!Common.isEmpty(reason) && status1 != Constants.INT1) {// 审核不通过
					supplier.setCheckFailReason(reason);

					StringBuffer content = new StringBuffer();
					content.append("尊贵的用户：<br> 您好！<br>");
					content.append("您 " + Common.dateToString(supplier.getCreateTime(), "yyyy年MM月dd日")
							+ "在众聚商城供应商注册页面（http://pop.zhongjumall.com/supplier/registUI)注册,但未通过审核。<br>");
					content.append("未通过原因: " + reason + "<br>");
					content.append("本邮件由众聚商城系统自动发出，请勿直接回复。<br>");
					content.append("在操作过程中如遇到任何疑问，请发送邮件至2605803377@qq.com，我们会及时为您解答。<br>");
					content.append("谢谢！");
					String email = findSupplier.getEmail();
					if (email != null) {
						SendHtmlMail.send(email, subject, content.toString());
					}
				} else {// 审核通过

					//

					String email = findSupplier.getEmail();
					StringBuffer content = new StringBuffer();
					content.append("Dear User,<br>");
					content.append("    We are happy to inform you that your supplier registration at zhongjumall");
					if (!Common.isEmpty(Common.dateToString(supplier.getCreateTime(), "yyyy年MM月dd日"))) {
						content.append(" on " + Common.dateToString(supplier.getCreateTime(), "yyyy年MM月dd日"));
					}
					;
					content.append(" has been approved.  Please find your registration detail as below:<br>");
					content.append(
							"username: " + user.getLoginName() + "  e-mail: " + findSupplier.getEmail() + "<br>");
					content.append("For your account safety, please do NOT disclose your username and password <br>");
					content.append("This is an automatically sent message.  Please do not reply directly.<br>");
					content.append(
							"Please contact us at 2605803377@qq.com should there be any further questions.   We will reply to you as soon as possible.<br>");
					content.append("Thanks!<br><br>");
					content.append("尊贵的用户：<br> 您好！<br>");
					content.append("    您 " + Common.dateToString(supplier.getCreateTime(), "yyyy年MM月dd日")
							+ "在众聚商城供应商注册页面（http://pop.zhongjumall.com/supplier/registUI)成功注册并通过审核。注册信息如下：<br>");
					content.append("用户名: " + user.getLoginName() + " 邮箱: " + findSupplier.getEmail() + "<br>");
					content.append("为保障您的账号安全，请勿将用户名和密码轻易透露给任何人。 <br>");
					content.append("本邮件由众聚商城系统自动发出，请勿直接回复。<br>");
					content.append("在操作过程中如遇到任何疑问，请发送邮件至2605803377@qq.com，我们会及时为您解答。<br>");
					content.append("谢谢！");
					if (email != null) {
						SendHtmlMail.send(email, subject, content.toString());
					}
					supplier.setActiveStatus(1); // 之前审核现在主要做激活状态
					supplier.setCheckFailReason(reason);
				}
				// RemoteServiceSingleton.getInstance().getSupplierManagerService().update(supplier);
				SupplierUser supplierUser = new SupplierUser();
				supplierUser.setSupplierId(id1);
				supplierUser.setStatus(status1);

				supplier.setIsOnekeySend(Integer.parseInt(isOnekeySend));
				supplier.setSupplyType(supplyType);
				supplier.setAuditTime(new Date());

				RemoteServiceSingleton.getInstance().getSupplierManagerService().checkMerchant(supplier, supplierUser);

				if (status1 == 1) { // 审核通过后才激活个人账号和返利
					// TODO 调取激活企业个人账号
					int activeStatus = RemoteServiceSingleton.getInstance().getSqwAccountRecordService()
							.activeUserOfQiyeAccount(id1);
					logger.info("激活个人帐号结果：result=" + activeStatus);
					// 推荐企业一次性返利
					Long qyId = id1; // 企业id
					Long p_userId = null; // 直接推荐人id
					Long pp_userId = null; // 间接推荐人id
					if (findSupplier != null && !StringUtils.isEmpty(findSupplier.getUserTj())) { // 直接推荐人
						com.mall.customer.model.User tjUser = RemoteServiceSingleton.getInstance().getUserService()
								.findUserByMobile(findSupplier.getUserTj());
						if (tjUser != null) {
							p_userId = tjUser.getUserId();

						}
					}
					BigDecimal price = null; // 返利总额
					if (findSupplier != null) {
						if (findSupplier.getNoPayhqq() != null) {
							price = findSupplier.getNoPayhqq();
						} else if (findSupplier.getFanliPrice() != null) {
							price = findSupplier.getFanliPrice();
						}
					}
					String trancNo = System.currentTimeMillis() + ""; // 交易号
					int fanLiResult = RemoteServiceSingleton.getInstance().getSqwAccountRecordService()
							.tuijianQiyeFanli(qyId, p_userId, pp_userId, price, trancNo);
					logger.info("推荐返利结果：result=" + fanLiResult);
				}

			}
		} catch (Exception e) {

			resaultJsonString = Constants.ERROR;

			LOGGER.error("审核商户. 异常  msg：" + e.getMessage(), e);
			LOGGER.error("商户Id:" + getCurrentPlatformId());
			LOGGER.error("用户:" + getCurrentUser().getUsername());
			LOGGER.error("用户ID:" + getCurrentUser().getId());

		}

		// return "redirect:/user/checklist?source="+source;
		return resaultJsonString;

	}

	@RequestMapping("/user/updateCheckDl")
	@ResponseBody
	public String userCheckDl(String paymentDays, String isBillPayment, int source, long id1, int status1, int isRecord,
			String reason, int supplyType, HttpServletRequest request, String companyQy, String companyRegion) {

		LOGGER.info(request.getParameter("id1") + "--" + "" + request.getParameter("status1") + "--"
				+ request.getParameter("source") + "------------" + reason);

		String resaultJsonString = Constants.SUCCESS;

		String isOnekeySend = request.getParameter("isOnekeySend");
		if (org.apache.commons.lang3.StringUtils.isBlank(isOnekeySend)) {
			isOnekeySend = "1";
		}

		// 加入pop的支付方式选择
		String paymentType = request.getParameter("paymentType");
		// 3 走国内支付 目前只有支付宝国内支付
		paymentType = "3";
		if (org.apache.commons.lang3.StringUtils.isBlank(paymentType)) {
			// paymentType = "1";
			paymentType = "3";
		}

		if (null == request.getParameter("id1") || null == request.getParameter("status1")
				|| null == request.getParameter("source")) {
			resaultJsonString = Constants.SAMEISNULL;

			return resaultJsonString;
		}

		try {

			if (source == Constants.INT1) {// 供应商

				Supplier supplier = new Supplier();
				Supplier findSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
						.findSupplier(id1);
				supplier.setSupplierId(id1);
				supplier.setStatus(status1);
				// supplier.setSupplyType(supplyType);
				SupplierUser user = RemoteServiceSingleton.getInstance().getSupplierManagerService()
						.findAdminUserByMerchantId(id1);

				if (user == null) {

					resaultJsonString = Constants.SAMEISNULL;

					return resaultJsonString;

				}

				String subject = "zhongjumall商户审核结果";
				if (!Common.isEmpty(reason) && status1 != Constants.INT1) {// 审核不通过
					supplier.setCheckFailReason(reason);

					StringBuffer content = new StringBuffer();
					content.append("尊贵的用户：<br> 您好！<br>");
					content.append("您 " + Common.dateToString(supplier.getCreateTime(), "yyyy年MM月dd日")
							+ "在众聚商城供应商注册页面（http://pop.zhongjumall.com/supplier/registUI)注册,但未通过审核。<br>");
					content.append("未通过原因: " + reason + "<br>");
					content.append("本邮件由众聚商城系统自动发出，请勿直接回复。<br>");
					content.append("在操作过程中如遇到任何疑问，请发送邮件至2605803377@qq.com，我们会及时为您解答。<br>");
					content.append("谢谢！");
					String email = findSupplier.getEmail();
					if (email != null) {
						SendHtmlMail.send(email, subject, content.toString());
					}
				} else {// 审核通过

					//

					String email = findSupplier.getEmail();
					StringBuffer content = new StringBuffer();
					content.append("Dear User,<br>");
					content.append("    We are happy to inform you that your supplier registration at zhongjumall");
					if (!Common.isEmpty(Common.dateToString(supplier.getCreateTime(), "yyyy年MM月dd日"))) {
						content.append(" on " + Common.dateToString(supplier.getCreateTime(), "yyyy年MM月dd日"));
					}
					;
					content.append(" has been approved.  Please find your registration detail as below:<br>");
					content.append(
							"username: " + user.getLoginName() + "  e-mail: " + findSupplier.getEmail() + "<br>");
					content.append("For your account safety, please do NOT disclose your username and password <br>");
					content.append("This is an automatically sent message.  Please do not reply directly.<br>");
					content.append(
							"Please contact us at 2605803377@qq.com should there be any further questions.   We will reply to you as soon as possible.<br>");
					content.append("Thanks!<br><br>");
					content.append("尊贵的用户：<br> 您好！<br>");
					content.append("    您 " + Common.dateToString(supplier.getCreateTime(), "yyyy年MM月dd日")
							+ "在众聚商城供应商注册页面（http://pop.zhongjumall.com/supplier/registUI)成功注册并通过审核。注册信息如下：<br>");
					content.append("用户名: " + user.getLoginName() + " 邮箱: " + findSupplier.getEmail() + "<br>");
					content.append("为保障您的账号安全，请勿将用户名和密码轻易透露给任何人。 <br>");
					content.append("本邮件由众聚商城系统自动发出，请勿直接回复。<br>");
					content.append("在操作过程中如遇到任何疑问，请发送邮件至2605803377@qq.com，我们会及时为您解答。<br>");
					content.append("谢谢！");
					if (email != null) {
						SendHtmlMail.send(email, subject, content.toString());
					}
					supplier.setActiveStatus(1); // 之前审核现在主要做激活状态
					supplier.setCheckFailReason(reason);
				}
				// RemoteServiceSingleton.getInstance().getSupplierManagerService().update(supplier);
				SupplierUser supplierUser = new SupplierUser();
				supplierUser.setSupplierId(id1);
				supplierUser.setStatus(status1);

				supplier.setIsOnekeySend(Integer.parseInt(isOnekeySend));
				supplier.setSupplyType(supplyType);
				supplier.setAuditTime(new Date());

				RemoteServiceSingleton.getInstance().getSupplierManagerService().checkMerchant(supplier, supplierUser);

				if (status1 == 1) { // 审核通过后才激活个人账号和返利
					// TODO 调取激活企业个人账号
					int activeStatus = RemoteServiceSingleton.getInstance().getSqwAccountRecordService()
							.activeUserOfQiyeAccount(id1);
					logger.info("激活个人帐号结果：result=" + activeStatus);
					// 推荐企业一次性返利
					Long qyId = id1; // 企业id
					Long p_userId = null; // 直接推荐人id
					Long pp_userId = null; // 间接推荐人id
					if (findSupplier != null && !StringUtils.isEmpty(findSupplier.getUserTj())) { // 直接推荐人
						com.mall.customer.model.User tjUser = RemoteServiceSingleton.getInstance().getUserService()
								.findUserByMobile(findSupplier.getUserTj());
						if (tjUser != null) {
							p_userId = tjUser.getUserId();

						}
					}
					BigDecimal price = null; // 返利总额
					if (findSupplier != null) {
						if (findSupplier.getNoPayhqq() != null) {
							price = findSupplier.getNoPayhqq();
						} else if (findSupplier.getFanliPrice() != null) {
							price = findSupplier.getFanliPrice();
						}
					}
					String trancNo = System.currentTimeMillis() + ""; // 交易号
					int fanLiResult = RemoteServiceSingleton.getInstance().getSqwAccountRecordService()
							.tuijianQiyeFanli(qyId, p_userId, pp_userId, price, trancNo);
					logger.info("推荐返利结果：result=" + fanLiResult);
				}

			}
		} catch (Exception e) {

			resaultJsonString = Constants.ERROR;

			LOGGER.error("审核商户. 异常  msg：" + e.getMessage(), e);
			LOGGER.error("商户Id:" + getCurrentPlatformId());
			LOGGER.error("用户:" + getCurrentUser().getUsername());
			LOGGER.error("用户ID:" + getCurrentUser().getId());

		}

		// return "redirect:/user/checklist?source="+source;
		return resaultJsonString;
	}

	@RequestMapping("/user/updateCheckSj")
	@ResponseBody
	public String userCheckSj(String paymentDays, String isBillPayment, int source, long id1, int status1, int isRecord,
			String reason, int supplyType, HttpServletRequest request, String companyQy, String companyRegion) {

		LOGGER.info(request.getParameter("id1") + "--" + "" + request.getParameter("status1") + "--"
				+ request.getParameter("source") + "------------" + reason);

		String resaultJsonString = Constants.SUCCESS;

		String isOnekeySend = request.getParameter("isOnekeySend");
		if (org.apache.commons.lang3.StringUtils.isBlank(isOnekeySend)) {
			isOnekeySend = "1";
		}

		// 加入pop的支付方式选择
		String paymentType = request.getParameter("paymentType");
		// 3 走国内支付 目前只有支付宝国内支付
		paymentType = "3";
		if (org.apache.commons.lang3.StringUtils.isBlank(paymentType)) {
			// paymentType = "1";
			paymentType = "3";
		}

		if (null == request.getParameter("id1") || null == request.getParameter("status1")
				|| null == request.getParameter("source")) {
			resaultJsonString = Constants.SAMEISNULL;

			return resaultJsonString;
		}

		try {

			if (source == Constants.INT1) {// 供应商

				Supplier supplier = new Supplier();
				Supplier findSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
						.findSupplier(id1);
				supplier.setSupplierId(id1);
				supplier.setStatus(status1);
				// supplier.setSupplyType(supplyType);
				SupplierUser user = RemoteServiceSingleton.getInstance().getSupplierManagerService()
						.findAdminUserByMerchantId(id1);

				if (user == null) {

					resaultJsonString = Constants.SAMEISNULL;

					return resaultJsonString;

				}

				String subject = "zhongjumall商户审核结果";
				if (!Common.isEmpty(reason) && status1 != Constants.INT1) {// 审核不通过
					supplier.setCheckFailReason(reason);

					StringBuffer content = new StringBuffer();
					content.append("尊贵的用户：<br> 您好！<br>");
					content.append("您 " + Common.dateToString(supplier.getCreateTime(), "yyyy年MM月dd日")
							+ "在众聚商城供应商注册页面（http://pop.zhongjumall.com/supplier/registUI)注册,但未通过审核。<br>");
					content.append("未通过原因: " + reason + "<br>");
					content.append("本邮件由众聚商城系统自动发出，请勿直接回复。<br>");
					content.append("在操作过程中如遇到任何疑问，请发送邮件至2605803377@qq.com，我们会及时为您解答。<br>");
					content.append("谢谢！");
					String email = findSupplier.getEmail();
					if (email != null) {
						SendHtmlMail.send(email, subject, content.toString());
					}
				} else {// 审核通过

					//

					String email = findSupplier.getEmail();
					StringBuffer content = new StringBuffer();
					content.append("Dear User,<br>");
					content.append("    We are happy to inform you that your supplier registration at zhongjumall");
					if (!Common.isEmpty(Common.dateToString(supplier.getCreateTime(), "yyyy年MM月dd日"))) {
						content.append(" on " + Common.dateToString(supplier.getCreateTime(), "yyyy年MM月dd日"));
					}
					;
					content.append(" has been approved.  Please find your registration detail as below:<br>");
					content.append(
							"username: " + user.getLoginName() + "  e-mail: " + findSupplier.getEmail() + "<br>");
					content.append("For your account safety, please do NOT disclose your username and password <br>");
					content.append("This is an automatically sent message.  Please do not reply directly.<br>");
					content.append(
							"Please contact us at 2605803377@qq.com should there be any further questions.   We will reply to you as soon as possible.<br>");
					content.append("Thanks!<br><br>");
					content.append("尊贵的用户：<br> 您好！<br>");
					content.append("    您 " + Common.dateToString(supplier.getCreateTime(), "yyyy年MM月dd日")
							+ "在众聚商城供应商注册页面（http://pop.zhongjumall.com/supplier/registUI)成功注册并通过审核。注册信息如下：<br>");
					content.append("用户名: " + user.getLoginName() + " 邮箱: " + findSupplier.getEmail() + "<br>");
					content.append("为保障您的账号安全，请勿将用户名和密码轻易透露给任何人。 <br>");
					content.append("本邮件由众聚商城系统自动发出，请勿直接回复。<br>");
					content.append("在操作过程中如遇到任何疑问，请发送邮件至2605803377@qq.com，我们会及时为您解答。<br>");
					content.append("谢谢！");
					if (email != null) {
						SendHtmlMail.send(email, subject, content.toString());
					}
					supplier.setActiveStatus(1); // 之前审核现在主要做激活状态
					supplier.setCheckFailReason(reason);
				}
				// RemoteServiceSingleton.getInstance().getSupplierManagerService().update(supplier);
				SupplierUser supplierUser = new SupplierUser();
				supplierUser.setSupplierId(id1);
				supplierUser.setStatus(status1);

				supplier.setIsOnekeySend(Integer.parseInt(isOnekeySend));
				supplier.setSupplyType(supplyType);
				supplier.setAuditTime(new Date());

				RemoteServiceSingleton.getInstance().getSupplierManagerService().checkMerchant(supplier, supplierUser);

				if (status1 == 1) { // 审核通过后才激活个人账号和返利
					// TODO 调取激活企业个人账号
					int activeStatus = RemoteServiceSingleton.getInstance().getSqwAccountRecordService()
							.activeUserOfQiyeAccount(id1);
					logger.info("激活个人帐号结果：result=" + activeStatus);
					// 推荐企业一次性返利
					Long qyId = id1; // 企业id
					Long p_userId = null; // 直接推荐人id
					Long pp_userId = null; // 间接推荐人id
					if (findSupplier != null && !StringUtils.isEmpty(findSupplier.getUserTj())) { // 直接推荐人
						com.mall.customer.model.User tjUser = RemoteServiceSingleton.getInstance().getUserService()
								.findUserByMobile(findSupplier.getUserTj());
						if (tjUser != null) {
							p_userId = tjUser.getUserId();

						}
					}
					BigDecimal price = null; // 返利总额
					if (findSupplier != null) {
						if (findSupplier.getNoPayhqq() != null) {
							price = findSupplier.getNoPayhqq();
						} else if (findSupplier.getFanliPrice() != null) {
							price = findSupplier.getFanliPrice();
						}
					}
					String trancNo = System.currentTimeMillis() + ""; // 交易号
					int fanLiResult = RemoteServiceSingleton.getInstance().getSqwAccountRecordService()
							.tuijianQiyeFanli(qyId, p_userId, pp_userId, price, trancNo);
					logger.info("推荐返利结果：result=" + fanLiResult);
				}

			}
		} catch (Exception e) {

			resaultJsonString = Constants.ERROR;

			LOGGER.error("审核商户. 异常  msg：" + e.getMessage(), e);
			LOGGER.error("商户Id:" + getCurrentPlatformId());
			LOGGER.error("用户:" + getCurrentUser().getUsername());
			LOGGER.error("用户ID:" + getCurrentUser().getId());

		}

		// return "redirect:/user/checklist?source="+source;
		return resaultJsonString;

	}

	/**
	 * 获取用户列表. * source 1 supplier 2.retailer 3.dealer
	 * 
	 * @param map
	 *            Map<String,Object>
	 * @param param
	 *            PlatformDto
	 * @param paramPage
	 *            PageBean
	 * @param request
	 *            HttpServletRequest
	 * @return URL 添加的用户id
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/user/checklist")
	public String userCheckList(Map<String, Object> map, String message, PlatformDto param, PageBean paramPage,
			HttpServletRequest request) {
		try {

			paramPage.setSortFields("create_time,status");
			paramPage.setOrder("desc,asc");
			if (param != null && !Common.isEmpty(param.getName())) {
				param.setName(param.getName().trim());
			}
			/*
			 * if(param!=null&&!Common.isEmpty(param.getStatus())){
			 * param.setName(param.getName().trim()); }
			 */
			if (null != param) {
				String status = param.getStatus();
				if (status != null && status.equals(Constants.DEFULTSTRING)) {
					param.setStatus(null);
				}
			}
			if (null != param) {
				String supplyType = param.getSupplyType();
				if (supplyType != null && supplyType.equals(Constants.DEFULTSTRING)) {
					param.setSupplyType(null);
				}
			}
			Integer type = param.getType();

			if (null != type && type == -1) {
				param.setType(null);
			}

			paramPage.setParameter(param);

			if (param != null && param.getSource() == Constants.INT1) {
				paramPage = RemoteServiceSingleton.getInstance().getSupplierManagerService().getPageList(paramPage);
			} /*
				 * else if(param!=null&&param.getSource()==Constants.INT2){
				 * paramPage = RemoteServiceSingleton.getInstance().
				 * getRetailerManagerService().getSimplePageList(paramPage);
				 * }else if(param!=null&&param.getSource()==Constants.INT3){
				 * paramPage=RemoteServiceSingleton.getInstance().
				 * getDealerService().getListPage(paramPage); }
				 */
			List<Supplier> supplierList = paramPage.getResult();
			if (!supplierList.isEmpty()) {
				for (Supplier s : supplierList) {

					SupplierUser supplierUser = RemoteServiceSingleton.getInstance().getSupplierUserManagerService()
							.getIsAdminUserBySupplierId(s.getSupplierId());
					if (supplierUser != null) {
						s.setUserLoginName(
								StringUtils.isEmpty(supplierUser.getLoginName()) ? "" : supplierUser.getLoginName());
					}
				}
			}
			// 获取所有入驻区域类型
			List<SupplierRegionSettings> regionList = supplierRegionService.getAllRegionSettings();
			map.put("regionList", regionList);

			map.put("page", paramPage);
			map.put("message", message);
		} catch (Exception e) {
			LOGGER.error("审核商户. 异常  msg：" + e);
		} finally {
		}
		return "user/checkAll";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/user/checklist1")
	public String userCheckList1(Map<String, Object> map, String message, PlatformDto param, PageBean paramPage,
			HttpServletRequest request) {
		try {

			paramPage.setSortFields("create_time,status");
			paramPage.setOrder("desc,asc");
			if (param != null && !Common.isEmpty(param.getName())) {
				param.setName(param.getName().trim());
			}
			/*
			 * if(param!=null&&!Common.isEmpty(param.getStatus())){
			 * param.setName(param.getName().trim()); }
			 */
			if (null != param) {
				String status = param.getStatus();
				if (status != null && status.equals(Constants.DEFULTSTRING)) {
					param.setStatus(null);
				}
			}
			if (null != param) {
				String supplyType = param.getSupplyType();
				if (supplyType != null && supplyType.equals(Constants.DEFULTSTRING)) {
					param.setSupplyType(null);
				}
			}
			Integer type = param.getType();

			if (null != type && type == -1) {
				param.setType(null);
			}

			paramPage.setParameter(param);

			if (param != null && param.getSource() == Constants.INT1) {
				paramPage = RemoteServiceSingleton.getInstance().getSupplierManagerService().getPageList1(paramPage);
			} /*
				 * else if(param!=null&&param.getSource()==Constants.INT2){
				 * paramPage = RemoteServiceSingleton.getInstance().
				 * getRetailerManagerService().getSimplePageList(paramPage);
				 * }else if(param!=null&&param.getSource()==Constants.INT3){
				 * paramPage=RemoteServiceSingleton.getInstance().
				 * getDealerService().getListPage(paramPage); }
				 */
			List<Supplier> supplierList = paramPage.getResult();
			if (!supplierList.isEmpty()) {
				for (Supplier s : supplierList) {
					SupplierUser supplierUser = RemoteServiceSingleton.getInstance().getSupplierUserManagerService()
							.getIsAdminUserBySupplierId(s.getSupplierId());
					if (supplierUser != null) {
						s.setUserLoginName(
								StringUtils.isEmpty(supplierUser.getLoginName()) ? "" : supplierUser.getLoginName());
					}
				}
			}
			// 获取所有入驻区域类型
			List<SupplierRegionSettings> regionList = supplierRegionService.getAllRegionSettings();
			map.put("regionList", regionList);

			map.put("page", paramPage);
			map.put("message", message);
		} catch (Exception e) {
			LOGGER.error("审核商户. 异常  msg：" + e);
		} finally {
		}
		return "user/check1";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/user/checklist2")
	public String userCheckList2(Map<String, Object> map, String message, PlatformDto param, PageBean paramPage,
			HttpServletRequest request) {
		try {

			paramPage.setSortFields("create_time,status");
			paramPage.setOrder("desc,asc");
			if (param != null && !Common.isEmpty(param.getName())) {
				param.setName(param.getName().trim());
			}
			/*
			 * if(param!=null&&!Common.isEmpty(param.getStatus())){
			 * param.setName(param.getName().trim()); }
			 */
			if (null != param) {
				String status = param.getStatus();
				if (status != null && status.equals(Constants.DEFULTSTRING)) {
					param.setStatus(null);
				}
			}
			if (null != param) {
				String supplyType = param.getSupplyType();
				if (supplyType != null && supplyType.equals(Constants.DEFULTSTRING)) {
					param.setSupplyType(null);
				}
			}
			Integer type = param.getType();

			if (null != type && type == -1) {
				param.setType(null);
			}

			paramPage.setParameter(param);

			if (param != null && param.getSource() == Constants.INT1) {
				paramPage = RemoteServiceSingleton.getInstance().getSupplierManagerService().getPageList2(paramPage);
			} /*
				 * else if(param!=null&&param.getSource()==Constants.INT2){
				 * paramPage = RemoteServiceSingleton.getInstance().
				 * getRetailerManagerService().getSimplePageList(paramPage);
				 * }else if(param!=null&&param.getSource()==Constants.INT3){
				 * paramPage=RemoteServiceSingleton.getInstance().
				 * getDealerService().getListPage(paramPage); }
				 */
			List<Supplier> supplierList = paramPage.getResult();
			if (!supplierList.isEmpty()) {
				for (Supplier s : supplierList) {
					SupplierUser supplierUser = RemoteServiceSingleton.getInstance().getSupplierUserManagerService()
							.getIsAdminUserBySupplierId(s.getSupplierId());
					if (supplierUser != null) {
						s.setUserLoginName(
								StringUtils.isEmpty(supplierUser.getLoginName()) ? "" : supplierUser.getLoginName());
					}
				}
			}
			// 获取所有入驻区域类型
			List<SupplierRegionSettings> regionList = supplierRegionService.getAllRegionSettings();
			map.put("regionList", regionList);

			map.put("page", paramPage);
			map.put("message", message);
		} catch (Exception e) {
			LOGGER.error("审核商户. 异常  msg：" + e);
		} finally {
		}
		return "user/check2";
	}
    //代理条件搜索
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/user/checklistAgent")
	public String userCheckListAgent(Map<String, Object> map,  PlatformDto param, PageBean paramPage, String message, 
			HttpServletRequest request) {
		try {

			paramPage.setSortFields("create_time,status");
			paramPage.setOrder("desc,asc");
			if (param != null && !Common.isEmpty(param.getName())) {
				param.setName(param.getName().trim());
			}
			
			if (null != param) {
				String status = param.getStatus();
				if (status != null && status.equals(Constants.DEFULTSTRING)) {
					param.setStatus(null);
				}
			}
			if (null != param) {
				String supplyType = param.getSupplyType();
				if (supplyType != null && supplyType.equals(Constants.DEFULTSTRING)) {
					param.setSupplyType(null);
				}
			}
			Integer type = param.getType();

			if (null != type && type == -1) {
				param.setType(null);
			}

			paramPage.setParameter(param);

			if (param != null && param.getSource() == Constants.INT1) {
				paramPage = RemoteServiceSingleton.getInstance().getSupplierManagerService().getPageListAgent(paramPage);
			} 
			List<Supplier> supplierList = paramPage.getResult();
			if (!supplierList.isEmpty()) {
				for (Supplier s : supplierList) {
					SupplierUser supplierUser = RemoteServiceSingleton.getInstance().getSupplierUserManagerService()
							.getIsAdminUserBySupplierId(s.getSupplierId());
					if (supplierUser != null) {
						s.setUserLoginName(
								StringUtils.isEmpty(supplierUser.getLoginName()) ? "" : supplierUser.getLoginName());
					}
				}
			}
			// 获取所有入驻区域类型
			List<SupplierRegionSettings> regionList = supplierRegionService.getAllRegionSettings();
			map.put("regionList", regionList);

			map.put("page", paramPage);
		} catch (Exception e) {
			LOGGER.error("审核商户. 异常  msg：" + e);
		} finally {
		}
		return "user/checkAgent";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/user/checklistPartner")
	public String userCheckListPartner(Map<String, Object> map, String message, PlatformDto param, PageBean paramPage,
			HttpServletRequest request) {
		try {

			paramPage.setSortFields("create_time,status");
			paramPage.setOrder("desc,asc");
			if (param != null && !Common.isEmpty(param.getName())) {
				param.setName(param.getName().trim());
			}
			/*
			 * if(param!=null&&!Common.isEmpty(param.getStatus())){
			 * param.setName(param.getName().trim()); }
			 */
			if (null != param) {
				String status = param.getStatus();
				if (status != null && status.equals(Constants.DEFULTSTRING)) {
					param.setStatus(null);
				}
			}
			if (null != param) {
				String supplyType = param.getSupplyType();
				if (supplyType != null && supplyType.equals(Constants.DEFULTSTRING)) {
					param.setSupplyType(null);
				}
			}
			Integer type = param.getType();

			if (null != type && type == -1) {
				param.setType(null);
			}

			paramPage.setParameter(param);

			if (param != null && param.getSource() == Constants.INT1) {
				paramPage = RemoteServiceSingleton.getInstance().getSupplierManagerService()
						.getPageListPartner(paramPage);
			} /*
				 * else if(param!=null&&param.getSource()==Constants.INT2){
				 * paramPage = RemoteServiceSingleton.getInstance().
				 * getRetailerManagerService().getSimplePageList(paramPage);
				 * }else if(param!=null&&param.getSource()==Constants.INT3){
				 * paramPage=RemoteServiceSingleton.getInstance().
				 * getDealerService().getListPage(paramPage); }
				 */
			List<Supplier> supplierList = paramPage.getResult();
			if (!supplierList.isEmpty()) {
				for (Supplier s : supplierList) {
					SupplierUser supplierUser = RemoteServiceSingleton.getInstance().getSupplierUserManagerService()
							.getIsAdminUserBySupplierId(s.getSupplierId());
					if (supplierUser != null) {
						s.setUserLoginName(
								StringUtils.isEmpty(supplierUser.getLoginName()) ? "" : supplierUser.getLoginName());
					}
				}
			}
			// 获取所有入驻区域类型
			List<SupplierRegionSettings> regionList = supplierRegionService.getAllRegionSettings();
			map.put("regionList", regionList);

			map.put("page", paramPage);
			map.put("message", message);
		} catch (Exception e) {
			LOGGER.error("审核商户. 异常  msg：" + e);
		} finally {
		}
		return "user/checklistPartner";
	}
    //商家条件搜索
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/user/checklistLine")
	public String checklistLine(Map<String, Object> map, String message, PlatformDto param, PageBean paramPage,
			HttpServletRequest request) {
		try {

			paramPage.setSortFields("create_time,status");
			paramPage.setOrder("desc,asc");
			if (param != null && !Common.isEmpty(param.getName())) {
				param.setName(param.getName().trim());
			}
			
			if (null != param) {
				String status = param.getStatus();
				if (status != null && status.equals(Constants.DEFULTSTRING)) {
					param.setStatus(null);
				}
			}
			if (null != param) {
				String supplyType = param.getSupplyType();
				if (supplyType != null && supplyType.equals(Constants.DEFULTSTRING)) {
					param.setSupplyType(null);
				}
			}
			Integer type = param.getType();

			if (null != type && type == -1) {
				param.setType(null);
			}

			paramPage.setParameter(param);

			if (param != null && param.getSource() == Constants.INT1) {
				paramPage = RemoteServiceSingleton.getInstance().getSupplierManagerService().getPageListLine(paramPage);
			} 
			List<Supplier> supplierList = paramPage.getResult();
			if (!supplierList.isEmpty()) {
				for (Supplier s : supplierList) {
					SupplierUser supplierUser = RemoteServiceSingleton.getInstance().getSupplierUserManagerService()
							.getIsAdminUserBySupplierId(s.getSupplierId());
					if (supplierUser != null) {
						s.setUserLoginName(
								StringUtils.isEmpty(supplierUser.getLoginName()) ? "" : supplierUser.getLoginName());
					}
				}
			}
			// 获取所有入驻区域类型
			List<SupplierRegionSettings> regionList = supplierRegionService.getAllRegionSettings();
			map.put("regionList", regionList);

			map.put("page", paramPage);
			map.put("message", message);
		} catch (Exception e) {
			LOGGER.error("审核商户. 异常  msg：" + e);
		} finally {
		}
		return "user/checklistLine";
	}

	/**
	 * 下载用户列表. * source 1 supplier 2.retailer 3.dealer
	 * 
	 * @param map
	 *            Map<String,Object>
	 * @param param
	 *            PlatformDto
	 * @param paramPage
	 *            PageBean
	 * @param request
	 *            HttpServletRequest
	 * @return URL 添加的用户id
	 */
	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes", "resource", "unused" })
	@RequestMapping("/user/downchecklist")
	public void downUserCheckListExcel(Map<String, Object> map, PlatformDto param, PageBean paramPage,
			HttpServletRequest request, HttpServletResponse response) {

		HSSFWorkbook book = new HSSFWorkbook();
		OutputStream os = null;
		// PageBean<RetailerQueryDto> pBean = new PageBean<RetailerQueryDto>();
		PlatformSale platformSale = new PlatformSale();
		try {

			paramPage.setSortFields("create_time,status");
			paramPage.setOrder("desc,asc");
			if (param != null && !Common.isEmpty(param.getName())) {
				param.setName(param.getName().trim());
			}
			if (null != param) {
				String status = param.getStatus();
				if (status != null && status.equals(Constants.DEFULTSTRING)) {
					param.setStatus(null);
				}
			}
			if (null != param) {
				String supplyType = param.getSupplyType();
				if (supplyType != null && supplyType.equals(Constants.DEFULTSTRING)) {
					param.setSupplyType(null);
				}
			}
			paramPage.setPageSize(Constants.NUMMAX);

			Integer type = param.getType();

			if (null != type && type == -1) {
				param.setType(null);
			}
			paramPage.setParameter(param);

			if (param != null && param.getSource() == Constants.INT1) {
				paramPage = RemoteServiceSingleton.getInstance().getSupplierManagerService().getPageList(paramPage);
			} /*
				 * else if(param!=null&&param.getSource()==Constants.INT2){ //
				 * paramPage=RemoteServiceSingleton.getInstance().
				 * getRetailerManagerService().getPageList(paramPage);
				 * paramPage=RemoteServiceSingleton.getInstance().
				 * getRetailerManagerService().getSimplePageList(paramPage);
				 * }else if(param!=null&&param.getSource()==Constants.INT3){
				 * paramPage=RemoteServiceSingleton.getInstance().
				 * getDealerService().getListPage(paramPage); }
				 */

			if (null != paramPage && null != paramPage.getResult()) {

				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DEFAULTDATE);

				List result = paramPage.getResult();

				HSSFSheet sheet = book.createSheet("commercialtenantchecklist");

				HSSFRow row = sheet.createRow((int) 0);
				HSSFCellStyle style = book.createCellStyle();
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

				HSSFCell cell = null;
				String[] strtitle = null;
				if (param != null && param.getSource() == Constants.INT2) {
					strtitle = new String[] { "ID", "商户名称", "用户名称", "座机", "移动电话", "地址", "注册日期", "审核日期", "备案状态", "状态",
							"注册地址-省", "注册地址-市", "注册地址-区", "注册来源", "注册类型", "销售人员" };
				} else {
					strtitle = new String[] { "ID", "商户名称", "座机", "移动电话", "地址", "注册日期", "审核日期", "状态" };
				}
				for (int i = 0; i < strtitle.length; i++) {
					cell = row.createCell(i);
					cell.setCellValue(strtitle[i]);
					cell.setCellStyle(style);
				}
				// 零售商导出报表变更，新增用户姓名
				if (param != null && param.getSource() == Constants.INT2) {
					List resList = paramPage.getResult();
					for (int i = 0; i < resList.size(); i++) {
						row = sheet.createRow((int) i + 1);

						String id = "";
						String commercialtenantName = "";
						String address = "";
						String creteTime = "";
						String auditTime = "";
						String statu = "";
						String phone = "";
						String mobile = "";
						String userNames = "";
						String merchantType = "";// 1 企业注册 2会员注册
						String typeName = "";// 商户来源 0pad 1pc
						String saleIdName = "";// 销售人员姓名
						String isRecordString = "";// 备案状态--是否已备案 默认0 未备案 1表示已备案

						// 零售商注册地址
						String provincename = null;
						String cityname = null;
						String countyname = null;

						// RetailerQueryDto retailerQueryDto =
						// (RetailerQueryDto) resList.get(i);
						/*
						 * try {
						 * 
						 * //销售人员姓名 if(null !=retailerQueryDto.getSaleId()){ int
						 * saleId = retailerQueryDto.getSaleId(); platformSale =
						 * RemoteServiceSingleton.getInstance().
						 * getPlatformSaleManagerService().getSaleById(saleId);
						 * saleIdName = platformSale.getSaleName(); }
						 * if(null!=retailerQueryDto.getProvinceId()){ int
						 * provinceId = retailerQueryDto.getProvinceId();
						 * provincename = RemoteServiceSingleton.getInstance()
						 * .getBaseDataServiceRpc().getProvinceById(provinceId).
						 * getProvincename(); }
						 * if(null!=retailerQueryDto.getCityId()){ int cityId =
						 * retailerQueryDto.getCityId(); cityname =
						 * RemoteServiceSingleton.getInstance()
						 * .getBaseDataServiceRpc().getCityById(cityId).
						 * getCityname(); }
						 * if(null!=retailerQueryDto.getAreaId()){ int areaId =
						 * retailerQueryDto.getAreaId(); countyname =
						 * RemoteServiceSingleton.getInstance()
						 * .getBaseDataServiceRpc().getCountyById(areaId).
						 * getCountyname();
						 * 
						 * }
						 * 
						 * } catch (Exception e) { LOGGER.info("-----------"+e);
						 * } if(null !=retailerQueryDto.getMerchantType()){ int
						 * merId = retailerQueryDto.getMerchantType(); if(merId
						 * == 1){ merchantType = "企业注册"; }else if (merId == 2) {
						 * 
						 * merchantType = "会员注册"; } } if(null !=
						 * retailerQueryDto.getType()){ int typeId =
						 * retailerQueryDto.getType(); if(typeId == 0){ typeName
						 * = "Pad注册"; }else if (typeId == 1) { typeName =
						 * "Pc注册"; } } if (null !=
						 * retailerQueryDto.getRetailerId()){ id =
						 * retailerQueryDto.getRetailerId()+""; } if (null !=
						 * retailerQueryDto.getName()){ commercialtenantName =
						 * retailerQueryDto.getName(); } if(null
						 * !=retailerQueryDto.getUserName()){ userNames =
						 * retailerQueryDto.getUserName(); } if(null !=
						 * retailerQueryDto.getPhone()){ phone =
						 * retailerQueryDto.getPhone()+""; } if(null !=
						 * retailerQueryDto.getMobile()){ mobile =
						 * retailerQueryDto.getMobile()+""; } if(null !=
						 * retailerQueryDto.getAddress()){ address =
						 * retailerQueryDto.getAddress(); }
						 * 
						 * if(null != retailerQueryDto.getCreateTime()){
						 * creteTime = simpleDateFormat.format(
						 * retailerQueryDto.getCreateTime()); }
						 * 
						 * if(null != retailerQueryDto.getAuditTime()){
						 * auditTime = simpleDateFormat.format(
						 * retailerQueryDto.getAuditTime()); int num =
						 * auditTime.indexOf("1970"); if(num!=-1){ auditTime =
						 * ""; } }
						 * 
						 * if(null !=retailerQueryDto.getIsRecord() &&
						 * retailerQueryDto.getIsRecord()== Constants.NUM1){
						 * isRecordString = "已备案"; }else { isRecordString =
						 * "未备案"; } if(null != retailerQueryDto.getStatus() &&
						 * Integer.valueOf(retailerQueryDto.getStatus()) ==
						 * Constants.INT1){ statu = "审核通过"; }else if(null !=
						 * retailerQueryDto.getStatus()
						 * &&Integer.valueOf(retailerQueryDto.getStatus()) ==
						 * Constants.INT2){ statu = "审核未通过"; }else if(null !=
						 * retailerQueryDto.getStatus()
						 * &&Integer.valueOf(retailerQueryDto.getStatus()) ==
						 * Constants.INT3){ statu = "禁用"; }else{ statu = "未审核";
						 * }
						 */
						row.createCell(Constants.SHORT0).setCellValue(id);
						row.createCell(Constants.SHORT1).setCellValue(commercialtenantName);
						row.createCell(Constants.SHORT2).setCellValue(userNames);
						row.createCell(Constants.SHORT3).setCellValue(phone);
						row.createCell(Constants.SHORT4).setCellValue(mobile);
						row.createCell(Constants.SHORT5).setCellValue(address);
						row.createCell(Constants.SHORT6).setCellValue(creteTime);
						row.createCell(Constants.SHORT7).setCellValue(auditTime);
						row.createCell(Constants.SHORT8).setCellValue(isRecordString);
						row.createCell(Constants.SHORT9).setCellValue(statu);
						if (param != null && param.getSource() == Constants.INT2) {
							row.createCell(Constants.SHORT10).setCellValue(provincename);
							row.createCell(Constants.SHORT11).setCellValue(cityname);
							row.createCell(Constants.SHORT12).setCellValue(countyname);
						}
						row.createCell(Constants.SHORT13).setCellValue(typeName);
						row.createCell(Constants.SHORT14).setCellValue(merchantType);
						row.createCell(Constants.SHORT15).setCellValue(saleIdName);

					}
				} else {

					for (int i = 0; i < result.size(); i++) {

						row = sheet.createRow((int) i + 1);

						String id = "";
						String commercialtenantName = "";
						String address = "";
						String creteTime = "";
						String auditTime = "";
						String statu = "";
						String phone = "";
						String mobile = "";

						// 零售商注册地址
						String provincename = null;
						String cityname = null;
						String countyname = null;
						// 供应商
						if (param != null && param.getSource() == Constants.INT1) {
							Supplier supplier = (Supplier) result.get(i);

							if (null != supplier.getSupplierId()) {
								id = supplier.getSupplierId() + "";
							}
							if (null != supplier.getName()) {
								commercialtenantName = supplier.getName();
							}

							if (null != supplier.getAddress()) {
								address = supplier.getAddress();
							}
							if (null != supplier.getPhone()) {
								mobile = supplier.getPhone() + "";
							}
							if (null != supplier.getCreateTime()) {
								creteTime = simpleDateFormat.format(supplier.getCreateTime());
							}

							if (null != supplier.getAuditTime()) {
								auditTime = simpleDateFormat.format(supplier.getAuditTime());
							}

							if (null != supplier.getStatus() && supplier.getStatus() == Constants.INT1) {
								statu = "审核通过";
							} else if (null != supplier.getStatus() && supplier.getStatus() == Constants.INT2) {
								statu = "审核未通过";
							} else if (null != supplier.getStatus() && supplier.getStatus() == Constants.INT3) {
								statu = "禁用";
							} else {
								statu = "未审核";
							}

							// 经销商
						} /*
							 * else
							 * if(param!=null&&param.getSource()==Constants.INT3
							 * ){ Dealer supplier = (Dealer) result.get(i);
							 * 
							 * if (null != supplier.getDealerId()){ id =
							 * supplier.getDealerId()+""; } if (null !=
							 * supplier.getCompanyName()){ commercialtenantName
							 * = supplier.getCompanyName(); }
							 * 
							 * if(null != supplier.getAddress()){ address =
							 * supplier.getAddress(); } if(null !=
							 * supplier.getPhone()){ phone =
							 * supplier.getPhone()+""; } if(null !=
							 * supplier.getMobile()){ mobile =
							 * supplier.getMobile()+""; }
							 * 
							 * if(null != supplier.getCreateTime()){ creteTime =
							 * simpleDateFormat.format(
							 * supplier.getCreateTime()); }
							 * 
							 * if(null != supplier.getAuditTime()){ auditTime =
							 * simpleDateFormat.format(
							 * supplier.getAuditTime()); }
							 * 
							 * if(null != supplier.getStatus() &&
							 * supplier.getStatus() == Constants.INT1){ statu =
							 * "审核通过"; }else if(null != supplier.getStatus()
							 * &&supplier.getStatus() == Constants.INT2){ statu
							 * = "审核未通过"; }else if(null != supplier.getStatus()
							 * &&supplier.getStatus() == Constants.INT3){ statu
							 * = "禁用"; }else{ statu = "未审核"; } }
							 */
						row.createCell(Constants.SHORT0).setCellValue(id);
						row.createCell(Constants.SHORT1).setCellValue(commercialtenantName);
						row.createCell(Constants.SHORT2).setCellValue(phone);
						row.createCell(Constants.SHORT3).setCellValue(mobile);
						row.createCell(Constants.SHORT4).setCellValue(address);
						row.createCell(Constants.SHORT5).setCellValue(creteTime);
						row.createCell(Constants.SHORT6).setCellValue(auditTime);
						row.createCell(Constants.SHORT7).setCellValue(statu);
						if (param != null && param.getSource() == Constants.INT2) {
							row.createCell(Constants.SHORT8).setCellValue(provincename);
							row.createCell(Constants.SHORT9).setCellValue(cityname);
							row.createCell(Constants.SHORT10).setCellValue(countyname);
						}

					}
				}

				LOGGER.info("拼装商户审核信息完成!");
				String filename = "WOFE-commercialtenantchecklist";

				os = response.getOutputStream();

				response.reset();

				response.setCharacterEncoding("UTF-8");

				filename = java.net.URLEncoder.encode(filename, "UTF-8");

				response.setHeader("Content-Disposition",
						"attachment;filename=" + new String(filename.getBytes("UTF-8"), "GBK") + ".xls");

				response.setContentType("application/msexcel");// 定义输出类型

				book.write(os);

				response.getOutputStream().flush();
				response.getOutputStream().close();
			}

		} catch (Exception e) {

			LOGGER.error("审核商户. 异常  msg：" + e.getMessage(), e);

		} finally {

			try {
				os.flush();
				os.close();
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}

		}
	}

	
	
	
	
	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes", "resource", "unused" })
	@RequestMapping("/user/downchecklistLine")
	public void downUserCheckListLineExcel(Map<String, Object> map, PlatformDto param, PageBean paramPage,
			HttpServletRequest request, HttpServletResponse response) {

		HSSFWorkbook book = new HSSFWorkbook();
		OutputStream os = null;
		// PageBean<RetailerQueryDto> pBean = new PageBean<RetailerQueryDto>();
		PlatformSale platformSale = new PlatformSale();
		try {

			paramPage.setSortFields("create_time,status");
			paramPage.setOrder("desc,asc");
			if (param != null && !Common.isEmpty(param.getName())) {
				param.setName(param.getName().trim());
			}
			if (null != param) {
				String status = param.getStatus();
				if (status != null && status.equals(Constants.DEFULTSTRING)) {
					param.setStatus(null);
				}
			}
			if (null != param) {
				String supplyType = param.getSupplyType();
				if (supplyType != null && supplyType.equals(Constants.DEFULTSTRING)) {
					param.setSupplyType(null);
				}
			}
			paramPage.setPageSize(Constants.NUMMAX);

			Integer type = param.getType();

			if (null != type && type == -1) {
				param.setType(null);
			}
			paramPage.setParameter(param);

			if (param != null && param.getSource() == Constants.INT1) {
				paramPage = RemoteServiceSingleton.getInstance().getSupplierManagerService().getPageListLine(paramPage);
			} 

			if (null != paramPage && null != paramPage.getResult()) {

				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DEFAULTDATE);

				List result = paramPage.getResult();

				HSSFSheet sheet = book.createSheet("commercialtenantchecklist");

				HSSFRow row = sheet.createRow((int) 0);
				HSSFCellStyle style = book.createCellStyle();
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

				HSSFCell cell = null;
				String[] strtitle = null;
				if (param != null && param.getSource() == Constants.INT2) {
					strtitle = new String[] { "ID", "商户名称", "用户名称", "座机", "移动电话", "地址", "注册日期", "审核日期", "备案状态", "状态",
							"注册地址-省", "注册地址-市", "注册地址-区", "注册来源", "注册类型", "销售人员" };
				} else {
					strtitle = new String[] {  "商户名称","商户简称","签购单名称","所属机构","客户性质","联接模式","新增类别","法人代表","法人身份证号","营业执照号码","税务登记证号","省","市","县/区",
							"注册地址","经营地址","法人","座机电话","联系人手机","经营负责人","座机电话","联系人手机","发展方","发展部门","发展人","分店所属服务区域","分店细分服务区域","终端编号",
							"终端性质","品牌","终端型号","安装地点(柜台)","接入方式","拨出电话","终端费用收取","终端备注","终端权益方","入网时间","商户MCC","商户收单机构","交易处理平台","结算处理机构",
							"交易前置平台所属机构","开通交易类型","开户行","结算账号","结算账户名","清算行行号","清算行行名","计费标准","微信公众号手续费扣率","支付宝手续费扣率","手续费最低值",
							"手续费最高值","银联境外卡手续费扣率","银联境外卡手续费最低值","银联境外卡手续费最高值","特殊计费类型","特殊计费档次","主应用的应用大类","主应用的应用小类","全民付商户编号",
							"全民付终端编号","业务受理方式","客户服务性质","拓展方式","签约(代理)机构","多应用注册方式","项目名称","项目细分","证件类型","是否需要审核","微信公众ID","连锁代码",
							"门店编号","门店名称","邮箱",
							"座机","移动电话", "地址", "注册日期", "审核日期", "状态" };
				}
				for (int i = 0; i < strtitle.length; i++) {
					cell = row.createCell(i);
					cell.setCellValue(strtitle[i]);
					cell.setCellStyle(style);
				}
				// 零售商导出报表变更，新增用户姓名
				if (param != null && param.getSource() == Constants.INT2) {
					List resList = paramPage.getResult();
					for (int i = 0; i < resList.size(); i++) {
						row = sheet.createRow((int) i + 1);

						String id = "";
						String commercialtenantName = "";
						String address = "";
						String creteTime = "";
						String auditTime = "";
						String statu = "";
						String phone = "";
						String mobile = "";
						String userNames = "";
						String merchantType = "";// 1 企业注册 2会员注册
						String typeName = "";// 商户来源 0pad 1pc
						String saleIdName = "";// 销售人员姓名
						String isRecordString = "";// 备案状态--是否已备案 默认0 未备案 1表示已备案

						// 零售商注册地址
						String provincename = null;
						String cityname = null;
						String countyname = null;

						row.createCell(Constants.SHORT0).setCellValue(id);
						row.createCell(Constants.SHORT1).setCellValue(commercialtenantName);
						row.createCell(Constants.SHORT2).setCellValue(userNames);
						row.createCell(Constants.SHORT3).setCellValue(phone);
						row.createCell(Constants.SHORT4).setCellValue(mobile);
						row.createCell(Constants.SHORT5).setCellValue(address);
						row.createCell(Constants.SHORT6).setCellValue(creteTime);
						row.createCell(Constants.SHORT7).setCellValue(auditTime);
						row.createCell(Constants.SHORT8).setCellValue(isRecordString);
						row.createCell(Constants.SHORT9).setCellValue(statu);
						if (param != null && param.getSource() == Constants.INT2) {
							row.createCell(Constants.SHORT10).setCellValue(provincename);
							row.createCell(Constants.SHORT11).setCellValue(cityname);
							row.createCell(Constants.SHORT12).setCellValue(countyname);
						}
						row.createCell(Constants.SHORT13).setCellValue(typeName);
						row.createCell(Constants.SHORT14).setCellValue(merchantType);
						row.createCell(Constants.SHORT15).setCellValue(saleIdName);

					}
				} else {

					for (int i = 0; i < result.size(); i++) {

						row = sheet.createRow((int) i + 1);

						
						String commercialtenantName = "";  //商户名称
						String nameJC = "";       //商户简称
						String qiangou="";        //签购单名称
						String organization="";   //所属机构
						String customerType="";   //客户性质
						String connectingMode=""; //联接模式
						String categoryNew="";     //新增类别
						String legalPerson="";      //法人代表名称
						String legalPersonCardno="";   //法人身份证号
						String BusinessLicenseNumber="";   //营业执照号码
						String taxLicenseno="";   //税务登记证号
						String province="";       //省
						String city="";          //市
						String area="";         //区
						String registerAddress="";   //注册地址
						String address="";      //经营地址
						String contact="";      //法人
						String contactTel="";   //座机电话
						String phone="";        //联系人手机
						String contact2="";     //经营负责人
						String contactTel2="";   //座机电话
						String phone2="";        //联系人手机
						String development ="";   //发展方
						String developmentDepartment ="";   //发展部门
						String sjSupplierId="";   //发展人
						String belongService="";   //分店所属服务区域
						String segmentationService="";   //分店细分服务区域
						String terminalNumber="";   //终端编号
						String terminalNature="";   //终端性质
						String brand="";   //品牌
						String terminalType="";   //终端型号
						String installationSite ="";   //安装地点(柜台)
						String accessWay="";   //接入方式
						String outgoingCall="";   //拨出电话
						String terminalCharge="";   //终端费用收取
						String terminalNote="";   //终端备注
						String terminalEndParty="";   //终端权益方
						String netTime="";   //入网时间
						String mcc="";   //商户MCC
						String merchantCollectionAgency="";   //商户收单机构
						String transactionProcessingPlatform="";   //交易处理平台
						String clearingHouse="";   //结算处理机构
						String beforePlatform="";   //交易前置平台所属机构
						String transactionType="";   //开通交易类型
						String openBack="";   //开户行
						String accoutNo="";   //结算账号
						String accountName="";   //结算账户名
						String clearingBankNumber="";   //清算行行号
						String clearingBankName="";   //清算行行名
						String chargingStandard="";   //计费标准
						String weixinCharge="";   //微信公众号手续费扣率
						String zhifubaoCharge="";   //支付宝手续费扣率
						String chargeLow="";   //手续费最低值
						String chargeHigh="";   //手续费最高值
						String overseasCharge="";   //银联境外卡手续费扣率
						String overseasChargeLow="";   //银联境外卡手续费最低值
						String overseasChargeHigh="";   //银联境外卡手续费最高值
						String specialCharge="";   //特殊计费类型
						String mainBig="";   //主应用的应用大类
						String mainLittle="";   //主应用的应用小类
						String qmfmerchantCode="";   //全民付商户编号
						String qmfterminalNumber="";   //全民付终端编号
						String businessAcceptance="";   //业务受理方式
						String characterCustomerService="";   //客户服务性质
						String expandWay="";   //拓展方式
						String contractor="";   //签约(代理)机构
						String registrations="";   //多应用注册方式
						String projectName="";   //项目名称
						String projectBreakdown="";   //项目细分
						String legalPersonCardtype="";   //证件类型
						String necessary="";   //是否需要审核
						String weixinID="";   //微信公众ID
						String chainCode="";   //连锁代码
						String storesNumber="";   //门店编号
						String storeName="";   //门店名称
						String email="";   //邮箱
						
						
						

						// 零售商注册地址
						
						// 供应商
						if (param != null && param.getSource() == Constants.INT1) {
							Supplier supplier = (Supplier) result.get(i);

							
							if (null != supplier.getName()) {
								commercialtenantName = supplier.getName();
							}
							//商户简称
							if (null != supplier.getNameJC()) {
								nameJC = supplier.getNameJC();
							}
							//签购单名称
                            if (null != supplier.getName()) {
                            	qiangou = supplier.getName();
							}
                            //所属机构        organization
                            
                            //客户性质
                            customerType="普通商户";
                           /* if (null != supplier.getOrganizationType()) {
								Integer type2 = supplier.getOrganizationType();
								if(type2==0){
									customerType="普通商户";
								}
								if(type2==1){
									customerType="线下商家";
								}
								if(type2==5){
									customerType="家庭号";
								}
								if(type2==6){
									customerType="会员企业号";
								}
								if(type2==11){
									customerType="区域运营商家";
								}
								if(type2==12){
									customerType="区域代理商";
								}
							}*/
                            
                          //联接模式
                            connectingMode="直联";
                          //新增类别
                            categoryNew="正常新商户";
                          //法人代表名称  
                            if (null != supplier.getLegalPerson()) {
                            	legalPerson=supplier.getLegalPerson();
							}
                          //法人身份证号
                            if (null != supplier.getLegalPersonCardNo()) {
                            	legalPersonCardno=supplier.getLegalPersonCardNo();
                            }
                          //营业执照号码
                            if (null != supplier.getBusinessLicenseNo()) {
                            	BusinessLicenseNumber=supplier.getBusinessLicenseNo();
                            }
                          //税务登记证号 
                            if (null != supplier.getBusinessLicenseNo()) {
                            	taxLicenseno=supplier.getBusinessLicenseNo();
                            }
                          //省
                            if(null!=supplier.getProvinceId()){
                            	Integer provinceId = supplier.getProvinceId();
                            	AgentProvince province2 = baseDataServiceRpc2.getProvinceById(provinceId);
                            	province=province2.getProvincename();
                            }
                            //市
                            if(null!=supplier.getCityId()){
                            	Integer cityId = supplier.getCityId();
                            	AgentCity cityById = baseDataServiceRpc2.getCityById(cityId);
                            	city=cityById.getCityname();
                            }
                            //区
                            if(null!=supplier.getAreaId()){
                            	Integer areaId = supplier.getAreaId();
                            	AgentCounty countyById = baseDataServiceRpc2.getCountyById(areaId);
                            	area=countyById.getCountyname();
                            }
                          //注册地址
                            if(null!=supplier.getRegisterAddress()){
                            	registerAddress=supplier.getRegisterAddress();
                            }
                          //经营地址
                            if(null!=supplier.getAddress()){
                            	address=supplier.getAddress();
                            }
                          //法人
                            if(null!=supplier.getContact()){
                            	contact=supplier.getContact();
                            }
                            //座机电话 
                            if(null!=supplier.getContactTel()){
                            	contactTel=supplier.getContactTel();
                            }
                            //联系人手机
                            if(null!=supplier.getPhone()){
                            	phone=supplier.getPhone();
                            }
                          //经营负责人
                            if(null!=supplier.getContact()){
                            	contact2=supplier.getContact();
                            }
                            //座机电话 
                            if(null!=supplier.getContactTel()){
                            	contactTel2=supplier.getContactTel();
                            }
                            //联系人手机
                            if(null!=supplier.getPhone()){
                            	phone2=supplier.getPhone();
                            }
                          //发展方
                            development="自行发展";
                          //发展部门
                            developmentDepartment="市场服务部";
                          //发展人
                           
                          //分店所属服务区域 
                            belongService="全部地区";
                          //分店细分服务区域 
                            segmentationService="全部地区";
                          //终端编号 
                            
                          //终端性质
                            terminalNature="普通POS";
                          //品牌
                          //终端型号
                          //安装地点(柜台) 
                          //接入方式
                            accessWay="电话拨号";
                          //拨出电话
                          //终端费用收取 
                          //终端备注
                          //终端权益方
                            terminalEndParty="银联商务上海分公司";
                          //入网时间
                            //商户MCC
                            //商户收单机构
                            merchantCollectionAgency="银联商务上海分公司";
                          //交易处理
                            transactionProcessingPlatform="中国银联";
                          //结算处理机构
                            clearingHouse="48022900";
                          //交易前置平台所属机构  
                            beforePlatform="中国银联";
                          //开通交易类型 
                            transactionType="1,2,3";
                          //开户行
                          //结算账号  
                          //结算账户名
                          //清算行行号 
                            //清算行行名
                          //计费标准
                            //微信公众号手续费扣率   
                            weixinCharge="0.35%";
                            //支付宝手续费扣率  
                            zhifubaoCharge="0.35%";
                          //手续费最低值      
                            chargeLow="0";
                          //手续费最高值   
                            chargeHigh="999999";
                          //银联境外卡手续费扣率
                            overseasCharge="2.5";
                          //银联境外卡手续费最低
                            overseasChargeLow="0";
                          //银联境外卡手续费最高值
                            overseasChargeHigh="999999";
                          //特殊计费类型
                            specialCharge="00";
                            //主应用的应用大类
                            mainBig="增值业务";
                          //主应用的应用小类
                            mainLittle="公共支付";
                          //全民付商户编号  
                          //全民付终端编号 
                          //业务受理方式
                            businessAcceptance="普通商户";
                          //客户服务性质 
                            characterCustomerService="自营收单";
                          //拓展方式
                            expandWay="自主发展";
                            //签约(代理)机构  
                            contractor="银联商务上海分公司";
                          //多应用注册方式
                            registrations="银商注册";
                          //项目名称
                          //项目细分
                          //证件类型
                            legalPersonCardtype="身份证";
                            //是否需要审核
                            necessary="是";
                          //微信公众ID
                            
                          //连锁代码 
                            chainCode="SXDUPIN";
                          //门店编号
                            storesNumber="001";
                          //门店名称 
                            storeName="commercialtenantName"; 
                            //邮箱  
							if (null != supplier.getEmail()) {
								email = supplier.getEmail();
							}
						
							// 经销商
						} 
						row.createCell(Constants.SHORT0).setCellValue(commercialtenantName);
						row.createCell(Constants.SHORT1).setCellValue(nameJC);
						row.createCell(Constants.SHORT2).setCellValue(qiangou);
						row.createCell(Constants.SHORT3).setCellValue(organization);
						row.createCell(Constants.SHORT4).setCellValue(customerType);
						row.createCell(Constants.SHORT5).setCellValue(connectingMode);
						row.createCell(Constants.SHORT6).setCellValue(categoryNew);
						row.createCell(Constants.SHORT7).setCellValue(legalPerson);
						row.createCell(Constants.SHORT8).setCellValue(legalPersonCardno);
						row.createCell(Constants.SHORT9).setCellValue(BusinessLicenseNumber);
						row.createCell(Constants.SHORT10).setCellValue(taxLicenseno);
						row.createCell(Constants.SHORT11).setCellValue(province);
						row.createCell(Constants.SHORT12).setCellValue(city);
						row.createCell(Constants.SHORT13).setCellValue(area);
						row.createCell(Constants.SHORT14).setCellValue(registerAddress);
						row.createCell(Constants.SHORT15).setCellValue(address);
						row.createCell(Constants.SHORT16).setCellValue(contact);
						row.createCell(Constants.SHORT17).setCellValue(contactTel);
						row.createCell(Constants.SHORT18).setCellValue(phone);
						row.createCell(Constants.SHORT19).setCellValue(contact2);
						row.createCell(Constants.SHORT20).setCellValue(contactTel2);
						row.createCell(Constants.SHORT21).setCellValue(phone2);
						row.createCell(Constants.SHORT22).setCellValue(development);
						row.createCell(Constants.SHORT23).setCellValue(developmentDepartment);
						row.createCell(Constants.SHORT24).setCellValue(sjSupplierId);
						row.createCell(Constants.SHORT25).setCellValue(belongService);
						row.createCell(Constants.SHORT26).setCellValue(segmentationService);
						row.createCell(Constants.SHORT27).setCellValue(terminalNumber);
						row.createCell(Constants.SHORT28).setCellValue(terminalNature);
						row.createCell(Constants.SHORT29).setCellValue(brand);
						row.createCell(Constants.SHORT30).setCellValue(terminalType);
						row.createCell(Constants.SHORT31).setCellValue(installationSite);
						row.createCell(Constants.SHORT32).setCellValue(accessWay);
						row.createCell(Constants.SHORT33).setCellValue(outgoingCall);
						row.createCell(Constants.SHORT34).setCellValue(terminalCharge);
						row.createCell(Constants.SHORT35).setCellValue(terminalNote);
						row.createCell(Constants.SHORT36).setCellValue(terminalEndParty);
						row.createCell(Constants.SHORT37).setCellValue(netTime);
						row.createCell(Constants.SHORT38).setCellValue(mcc);
						row.createCell(Constants.SHORT39).setCellValue(merchantCollectionAgency);
						row.createCell(Constants.SHORT40).setCellValue(transactionProcessingPlatform);
						row.createCell(Constants.SHORT41).setCellValue(clearingHouse);
						row.createCell(Constants.SHORT42).setCellValue(beforePlatform);
						row.createCell(Constants.SHORT43).setCellValue(transactionType);
						row.createCell(Constants.SHORT44).setCellValue(openBack);
						row.createCell(Constants.SHORT45).setCellValue(accoutNo);
						row.createCell(Constants.SHORT46).setCellValue(accountName);
						row.createCell(Constants.SHORT47).setCellValue(clearingBankNumber);
						row.createCell(Constants.SHORT48).setCellValue(clearingBankName);
						row.createCell(Constants.SHORT49).setCellValue(chargingStandard);
						row.createCell(Constants.SHORT50).setCellValue(weixinCharge);
						row.createCell(Constants.SHORT51).setCellValue(zhifubaoCharge);
						row.createCell(Constants.SHORT52).setCellValue(chargeLow);
						row.createCell(Constants.SHORT53).setCellValue(chargeHigh);
						row.createCell(Constants.SHORT54).setCellValue(overseasCharge);
						row.createCell(Constants.SHORT55).setCellValue(overseasChargeLow);
						row.createCell(Constants.SHORT56).setCellValue(overseasChargeHigh);
						row.createCell(Constants.SHORT57).setCellValue(specialCharge);
						row.createCell(Constants.SHORT58).setCellValue(mainBig);
						row.createCell(Constants.SHORT59).setCellValue(mainLittle);
						row.createCell(Constants.SHORT60).setCellValue(qmfmerchantCode);
						row.createCell(Constants.SHORT61).setCellValue(qmfterminalNumber);
						row.createCell(Constants.SHORT62).setCellValue(businessAcceptance);
						row.createCell(Constants.SHORT63).setCellValue(characterCustomerService);
						row.createCell(Constants.SHORT64).setCellValue(expandWay);
						row.createCell(Constants.SHORT65).setCellValue(contractor);
						row.createCell(Constants.SHORT66).setCellValue(registrations);
						row.createCell(Constants.SHORT67).setCellValue(projectName);
						row.createCell(Constants.SHORT68).setCellValue(projectBreakdown);
						row.createCell(Constants.SHORT69).setCellValue(legalPersonCardtype);
						row.createCell(Constants.SHORT70).setCellValue(necessary);
						row.createCell(Constants.SHORT71).setCellValue(weixinID);
						row.createCell(Constants.SHORT72).setCellValue(chainCode);
						row.createCell(Constants.SHORT73).setCellValue(storesNumber);
						row.createCell(Constants.SHORT74).setCellValue(storeName);
						row.createCell(Constants.SHORT75).setCellValue(email);
						

					}
				}

				LOGGER.info("拼装商户审核信息完成!");
				String filename = "WOFE-commercialtenantchecklist";

				os = response.getOutputStream();

				response.reset();

				response.setCharacterEncoding("UTF-8");

				filename = java.net.URLEncoder.encode(filename, "UTF-8");

				response.setHeader("Content-Disposition",
						"attachment;filename=" + new String(filename.getBytes("UTF-8"), "GBK") + ".xls");

				response.setContentType("application/msexcel");// 定义输出类型

				book.write(os);

				response.getOutputStream().flush();
				response.getOutputStream().close();
			}

		} catch (Exception e) {

			LOGGER.error("审核商户. 异常  msg：" + e.getMessage(), e);

		} finally {

			try {
				os.flush();
				os.close();
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}

		}
	}
	
	
	
	//代理商表格导出
	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes", "resource", "unused" })
	@RequestMapping("/user/downchecklistAgent")
	public void downUserCheckListAgentExcel(Map<String, Object> map, PlatformDto param, PageBean paramPage,
			HttpServletRequest request, HttpServletResponse response) {

		HSSFWorkbook book = new HSSFWorkbook();
		OutputStream os = null;
		// PageBean<RetailerQueryDto> pBean = new PageBean<RetailerQueryDto>();
		PlatformSale platformSale = new PlatformSale();
		try {

			paramPage.setSortFields("create_time,status");
			paramPage.setOrder("desc,asc");
			if (param != null && !Common.isEmpty(param.getName())) {
				param.setName(param.getName().trim());
			}
			if (null != param) {
				String status = param.getStatus();
				if (status != null && status.equals(Constants.DEFULTSTRING)) {
					param.setStatus(null);
				}
			}
			if (null != param) {
				String supplyType = param.getSupplyType();
				if (supplyType != null && supplyType.equals(Constants.DEFULTSTRING)) {
					param.setSupplyType(null);
				}
			}
			paramPage.setPageSize(Constants.NUMMAX);

			Integer type = param.getType();

			if (null != type && type == -1) {
				param.setType(null);
			}
			paramPage.setParameter(param);

			if (param != null && param.getSource() == Constants.INT1) {
				paramPage = RemoteServiceSingleton.getInstance().getSupplierManagerService().getPageListAgent(paramPage);
			} 

			if (null != paramPage && null != paramPage.getResult()) {

				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DEFAULTDATE);

				List result = paramPage.getResult();

				HSSFSheet sheet = book.createSheet("commercialtenantchecklist");

				HSSFRow row = sheet.createRow((int) 0);
				HSSFCellStyle style = book.createCellStyle();
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

				HSSFCell cell = null;
				String[] strtitle = null;
				if (param != null && param.getSource() == Constants.INT2) {
					strtitle = new String[] { "ID", "商户名称", "用户名称", "座机", "移动电话", "地址", "注册日期", "审核日期", "备案状态", "状态",
							"注册地址-省", "注册地址-市", "注册地址-区", "注册来源", "注册类型", "销售人员" };
				} else {
					strtitle = new String[] {  "商户名称","商户简称","签购单名称","所属机构","客户性质","联接模式","新增类别","法人代表","法人身份证号","营业执照号码","税务登记证号","省","市","县/区",
							"注册地址","经营地址","法人","座机电话","联系人手机","经营负责人","座机电话","联系人手机","发展方","发展部门","发展人","分店所属服务区域","分店细分服务区域","终端编号",
							"终端性质","品牌","终端型号","安装地点(柜台)","接入方式","拨出电话","终端费用收取","终端备注","终端权益方","入网时间","商户MCC","商户收单机构","交易处理平台","结算处理机构",
							"交易前置平台所属机构","开通交易类型","开户行","结算账号","结算账户名","清算行行号","清算行行名","计费标准","微信公众号手续费扣率","支付宝手续费扣率","手续费最低值",
							"手续费最高值","银联境外卡手续费扣率","银联境外卡手续费最低值","银联境外卡手续费最高值","特殊计费类型","特殊计费档次","主应用的应用大类","主应用的应用小类","全民付商户编号",
							"全民付终端编号","业务受理方式","客户服务性质","拓展方式","签约(代理)机构","多应用注册方式","项目名称","项目细分","证件类型","是否需要审核","微信公众ID","连锁代码",
							"门店编号","门店名称","邮箱",
							"座机","移动电话", "地址", "注册日期", "审核日期", "状态" };
				}
				for (int i = 0; i < strtitle.length; i++) {
					cell = row.createCell(i);
					cell.setCellValue(strtitle[i]);
					cell.setCellStyle(style);
				}
				// 零售商导出报表变更，新增用户姓名
				if (param != null && param.getSource() == Constants.INT2) {
					List resList = paramPage.getResult();
					for (int i = 0; i < resList.size(); i++) {
						row = sheet.createRow((int) i + 1);

						String id = "";
						String commercialtenantName = "";
						String address = "";
						String creteTime = "";
						String auditTime = "";
						String statu = "";
						String phone = "";
						String mobile = "";
						String userNames = "";
						String merchantType = "";// 1 企业注册 2会员注册
						String typeName = "";// 商户来源 0pad 1pc
						String saleIdName = "";// 销售人员姓名
						String isRecordString = "";// 备案状态--是否已备案 默认0 未备案 1表示已备案

						// 零售商注册地址
						String provincename = null;
						String cityname = null;
						String countyname = null;

						row.createCell(Constants.SHORT0).setCellValue(id);
						row.createCell(Constants.SHORT1).setCellValue(commercialtenantName);
						row.createCell(Constants.SHORT2).setCellValue(userNames);
						row.createCell(Constants.SHORT3).setCellValue(phone);
						row.createCell(Constants.SHORT4).setCellValue(mobile);
						row.createCell(Constants.SHORT5).setCellValue(address);
						row.createCell(Constants.SHORT6).setCellValue(creteTime);
						row.createCell(Constants.SHORT7).setCellValue(auditTime);
						row.createCell(Constants.SHORT8).setCellValue(isRecordString);
						row.createCell(Constants.SHORT9).setCellValue(statu);
						if (param != null && param.getSource() == Constants.INT2) {
							row.createCell(Constants.SHORT10).setCellValue(provincename);
							row.createCell(Constants.SHORT11).setCellValue(cityname);
							row.createCell(Constants.SHORT12).setCellValue(countyname);
						}
						row.createCell(Constants.SHORT13).setCellValue(typeName);
						row.createCell(Constants.SHORT14).setCellValue(merchantType);
						row.createCell(Constants.SHORT15).setCellValue(saleIdName);

					}
				} else {

					for (int i = 0; i < result.size(); i++) {

						row = sheet.createRow((int) i + 1);

						
						String commercialtenantName = "";  //商户名称
						String nameJC = "";       //商户简称
						String qiangou="";        //签购单名称
						String organization="";   //所属机构
						String customerType="";   //客户性质
						String connectingMode=""; //联接模式
						String categoryNew="";     //新增类别
						String legalPerson="";      //法人代表名称
						String legalPersonCardno="";   //法人身份证号
						String BusinessLicenseNumber="";   //营业执照号码
						String taxLicenseno="";   //税务登记证号
						String province="";       //省
						String city="";          //市
						String area="";         //区
						String registerAddress="";   //注册地址
						String address="";      //经营地址
						String contact="";      //法人
						String contactTel="";   //座机电话
						String phone="";        //联系人手机
						String contact2="";     //经营负责人
						String contactTel2="";   //座机电话
						String phone2="";        //联系人手机
						String development ="";   //发展方
						String developmentDepartment ="";   //发展部门
						String sjSupplierId="";   //发展人
						String belongService="";   //分店所属服务区域
						String segmentationService="";   //分店细分服务区域
						String terminalNumber="";   //终端编号
						String terminalNature="";   //终端性质
						String brand="";   //品牌
						String terminalType="";   //终端型号
						String installationSite ="";   //安装地点(柜台)
						String accessWay="";   //接入方式
						String outgoingCall="";   //拨出电话
						String terminalCharge="";   //终端费用收取
						String terminalNote="";   //终端备注
						String terminalEndParty="";   //终端权益方
						String netTime="";   //入网时间
						String mcc="";   //商户MCC
						String merchantCollectionAgency="";   //商户收单机构
						String transactionProcessingPlatform="";   //交易处理平台
						String clearingHouse="";   //结算处理机构
						String beforePlatform="";   //交易前置平台所属机构
						String transactionType="";   //开通交易类型
						String openBack="";   //开户行
						String accoutNo="";   //结算账号
						String accountName="";   //结算账户名
						String clearingBankNumber="";   //清算行行号
						String clearingBankName="";   //清算行行名
						String chargingStandard="";   //计费标准
						String weixinCharge="";   //微信公众号手续费扣率
						String zhifubaoCharge="";   //支付宝手续费扣率
						String chargeLow="";   //手续费最低值
						String chargeHigh="";   //手续费最高值
						String overseasCharge="";   //银联境外卡手续费扣率
						String overseasChargeLow="";   //银联境外卡手续费最低值
						String overseasChargeHigh="";   //银联境外卡手续费最高值
						String specialCharge="";   //特殊计费类型
						String mainBig="";   //主应用的应用大类
						String mainLittle="";   //主应用的应用小类
						String qmfmerchantCode="";   //全民付商户编号
						String qmfterminalNumber="";   //全民付终端编号
						String businessAcceptance="";   //业务受理方式
						String characterCustomerService="";   //客户服务性质
						String expandWay="";   //拓展方式
						String contractor="";   //签约(代理)机构
						String registrations="";   //多应用注册方式
						String projectName="";   //项目名称
						String projectBreakdown="";   //项目细分
						String legalPersonCardtype="";   //证件类型
						String necessary="";   //是否需要审核
						String weixinID="";   //微信公众ID
						String chainCode="";   //连锁代码
						String storesNumber="";   //门店编号
						String storeName="";   //门店名称
						String email="";   //邮箱
						
						
						

						// 零售商注册地址
						
						// 供应商
						if (param != null && param.getSource() == Constants.INT1) {
							Supplier supplier = (Supplier) result.get(i);

							
							if (null != supplier.getName()) {
								commercialtenantName = supplier.getName();
							}
							//商户简称
							if (null != supplier.getNameJC()) {
								nameJC = supplier.getNameJC();
							}
							//签购单名称
                            if (null != supplier.getName()) {
                            	qiangou = supplier.getName();
							}
                            //所属机构        organization
                            
                            //客户性质
                            customerType="普通商户";
                           /* if (null != supplier.getOrganizationType()) {
								Integer type2 = supplier.getOrganizationType();
								if(type2==0){
									customerType="普通商户";
								}
								if(type2==1){
									customerType="线下商家";
								}
								if(type2==5){
									customerType="家庭号";
								}
								if(type2==6){
									customerType="会员企业号";
								}
								if(type2==11){
									customerType="区域运营商家";
								}
								if(type2==12){
									customerType="区域代理商";
								}
							}*/
                            
                          //联接模式
                            connectingMode="直联";
                          //新增类别
                            categoryNew="正常新商户";
                          //法人代表名称  
                            if (null != supplier.getLegalPerson()) {
                            	legalPerson=supplier.getLegalPerson();
							}
                          //法人身份证号
                            if (null != supplier.getLegalPersonCardNo()) {
                            	legalPersonCardno=supplier.getLegalPersonCardNo();
                            }
                          //营业执照号码
                            if (null != supplier.getBusinessLicenseNo()) {
                            	BusinessLicenseNumber=supplier.getBusinessLicenseNo();
                            }
                          //税务登记证号 
                            if (null != supplier.getBusinessLicenseNo()) {
                            	taxLicenseno=supplier.getBusinessLicenseNo();
                            }
                          //省
                            if(null!=supplier.getProvinceId()){
                            	Integer provinceId = supplier.getProvinceId();
                            	AgentProvince province2 = baseDataServiceRpc2.getProvinceById(provinceId);
                            	province=province2.getProvincename();
                            }
                            //市
                            if(null!=supplier.getCityId()){
                            	Integer cityId = supplier.getCityId();
                            	AgentCity cityById = baseDataServiceRpc2.getCityById(cityId);
                            	city=cityById.getCityname();
                            }
                            //区
                            if(null!=supplier.getAreaId()){
                            	Integer areaId = supplier.getAreaId();
                            	AgentCounty countyById = baseDataServiceRpc2.getCountyById(areaId);
                            	area=countyById.getCountyname();
                            }
                          //注册地址
                            if(null!=supplier.getRegisterAddress()){
                            	registerAddress=supplier.getRegisterAddress();
                            }
                          //经营地址
                            if(null!=supplier.getAddress()){
                            	address=supplier.getAddress();
                            }
                          //法人
                            if(null!=supplier.getContact()){
                            	contact=supplier.getContact();
                            }
                            //座机电话 
                            if(null!=supplier.getContactTel()){
                            	contactTel=supplier.getContactTel();
                            }
                            //联系人手机
                            if(null!=supplier.getPhone()){
                            	phone=supplier.getPhone();
                            }
                          //经营负责人
                            if(null!=supplier.getContact()){
                            	contact2=supplier.getContact();
                            }
                            //座机电话 
                            if(null!=supplier.getContactTel()){
                            	contactTel2=supplier.getContactTel();
                            }
                            //联系人手机
                            if(null!=supplier.getPhone()){
                            	phone2=supplier.getPhone();
                            }
                          //发展方
                            development="自行发展";
                          //发展部门
                            developmentDepartment="市场服务部";
                          //发展人
                           
                          //分店所属服务区域 
                            belongService="全部地区";
                          //分店细分服务区域 
                            segmentationService="全部地区";
                          //终端编号 
                            
                          //终端性质
                            terminalNature="普通POS";
                          //品牌
                          //终端型号
                          //安装地点(柜台) 
                          //接入方式
                            accessWay="电话拨号";
                          //拨出电话
                          //终端费用收取 
                          //终端备注
                          //终端权益方
                            terminalEndParty="银联商务上海分公司";
                          //入网时间
                            //商户MCC
                            //商户收单机构
                            merchantCollectionAgency="银联商务上海分公司";
                          //交易处理
                            transactionProcessingPlatform="中国银联";
                          //结算处理机构
                            clearingHouse="48022900";
                          //交易前置平台所属机构  
                            beforePlatform="中国银联";
                          //开通交易类型 
                            transactionType="1,2,3";
                          //开户行
                          //结算账号  
                          //结算账户名
                          //清算行行号 
                            //清算行行名
                          //计费标准
                            //微信公众号手续费扣率   
                            weixinCharge="0.35%";
                            //支付宝手续费扣率  
                            zhifubaoCharge="0.35%";
                          //手续费最低值      
                            chargeLow="0";
                          //手续费最高值   
                            chargeHigh="999999";
                          //银联境外卡手续费扣率
                            overseasCharge="2.5";
                          //银联境外卡手续费最低
                            overseasChargeLow="0";
                          //银联境外卡手续费最高值
                            overseasChargeHigh="999999";
                          //特殊计费类型
                            specialCharge="00";
                            //主应用的应用大类
                            mainBig="增值业务";
                          //主应用的应用小类
                            mainLittle="公共支付";
                          //全民付商户编号  
                          //全民付终端编号 
                          //业务受理方式
                            businessAcceptance="普通商户";
                          //客户服务性质 
                            characterCustomerService="自营收单";
                          //拓展方式
                            expandWay="自主发展";
                            //签约(代理)机构  
                            contractor="银联商务上海分公司";
                          //多应用注册方式
                            registrations="银商注册";
                          //项目名称
                          //项目细分
                          //证件类型
                            legalPersonCardtype="身份证";
                            //是否需要审核
                            necessary="是";
                          //微信公众ID
                            
                          //连锁代码 
                            chainCode="SXDUPIN";
                          //门店编号
                            storesNumber="001";
                          //门店名称 
                            storeName="commercialtenantName"; 
                            //邮箱  
							if (null != supplier.getEmail()) {
								email = supplier.getEmail();
							}
						
							// 经销商
						} 
						row.createCell(Constants.SHORT0).setCellValue(commercialtenantName);
						row.createCell(Constants.SHORT1).setCellValue(nameJC);
						row.createCell(Constants.SHORT2).setCellValue(qiangou);
						row.createCell(Constants.SHORT3).setCellValue(organization);
						row.createCell(Constants.SHORT4).setCellValue(customerType);
						row.createCell(Constants.SHORT5).setCellValue(connectingMode);
						row.createCell(Constants.SHORT6).setCellValue(categoryNew);
						row.createCell(Constants.SHORT7).setCellValue(legalPerson);
						row.createCell(Constants.SHORT8).setCellValue(legalPersonCardno);
						row.createCell(Constants.SHORT9).setCellValue(BusinessLicenseNumber);
						row.createCell(Constants.SHORT10).setCellValue(taxLicenseno);
						row.createCell(Constants.SHORT11).setCellValue(province);
						row.createCell(Constants.SHORT12).setCellValue(city);
						row.createCell(Constants.SHORT13).setCellValue(area);
						row.createCell(Constants.SHORT14).setCellValue(registerAddress);
						row.createCell(Constants.SHORT15).setCellValue(address);
						row.createCell(Constants.SHORT16).setCellValue(contact);
						row.createCell(Constants.SHORT17).setCellValue(contactTel);
						row.createCell(Constants.SHORT18).setCellValue(phone);
						row.createCell(Constants.SHORT19).setCellValue(contact2);
						row.createCell(Constants.SHORT20).setCellValue(contactTel2);
						row.createCell(Constants.SHORT21).setCellValue(phone2);
						row.createCell(Constants.SHORT22).setCellValue(development);
						row.createCell(Constants.SHORT23).setCellValue(developmentDepartment);
						row.createCell(Constants.SHORT24).setCellValue(sjSupplierId);
						row.createCell(Constants.SHORT25).setCellValue(belongService);
						row.createCell(Constants.SHORT26).setCellValue(segmentationService);
						row.createCell(Constants.SHORT27).setCellValue(terminalNumber);
						row.createCell(Constants.SHORT28).setCellValue(terminalNature);
						row.createCell(Constants.SHORT29).setCellValue(brand);
						row.createCell(Constants.SHORT30).setCellValue(terminalType);
						row.createCell(Constants.SHORT31).setCellValue(installationSite);
						row.createCell(Constants.SHORT32).setCellValue(accessWay);
						row.createCell(Constants.SHORT33).setCellValue(outgoingCall);
						row.createCell(Constants.SHORT34).setCellValue(terminalCharge);
						row.createCell(Constants.SHORT35).setCellValue(terminalNote);
						row.createCell(Constants.SHORT36).setCellValue(terminalEndParty);
						row.createCell(Constants.SHORT37).setCellValue(netTime);
						row.createCell(Constants.SHORT38).setCellValue(mcc);
						row.createCell(Constants.SHORT39).setCellValue(merchantCollectionAgency);
						row.createCell(Constants.SHORT40).setCellValue(transactionProcessingPlatform);
						row.createCell(Constants.SHORT41).setCellValue(clearingHouse);
						row.createCell(Constants.SHORT42).setCellValue(beforePlatform);
						row.createCell(Constants.SHORT43).setCellValue(transactionType);
						row.createCell(Constants.SHORT44).setCellValue(openBack);
						row.createCell(Constants.SHORT45).setCellValue(accoutNo);
						row.createCell(Constants.SHORT46).setCellValue(accountName);
						row.createCell(Constants.SHORT47).setCellValue(clearingBankNumber);
						row.createCell(Constants.SHORT48).setCellValue(clearingBankName);
						row.createCell(Constants.SHORT49).setCellValue(chargingStandard);
						row.createCell(Constants.SHORT50).setCellValue(weixinCharge);
						row.createCell(Constants.SHORT51).setCellValue(zhifubaoCharge);
						row.createCell(Constants.SHORT52).setCellValue(chargeLow);
						row.createCell(Constants.SHORT53).setCellValue(chargeHigh);
						row.createCell(Constants.SHORT54).setCellValue(overseasCharge);
						row.createCell(Constants.SHORT55).setCellValue(overseasChargeLow);
						row.createCell(Constants.SHORT56).setCellValue(overseasChargeHigh);
						row.createCell(Constants.SHORT57).setCellValue(specialCharge);
						row.createCell(Constants.SHORT58).setCellValue(mainBig);
						row.createCell(Constants.SHORT59).setCellValue(mainLittle);
						row.createCell(Constants.SHORT60).setCellValue(qmfmerchantCode);
						row.createCell(Constants.SHORT61).setCellValue(qmfterminalNumber);
						row.createCell(Constants.SHORT62).setCellValue(businessAcceptance);
						row.createCell(Constants.SHORT63).setCellValue(characterCustomerService);
						row.createCell(Constants.SHORT64).setCellValue(expandWay);
						row.createCell(Constants.SHORT65).setCellValue(contractor);
						row.createCell(Constants.SHORT66).setCellValue(registrations);
						row.createCell(Constants.SHORT67).setCellValue(projectName);
						row.createCell(Constants.SHORT68).setCellValue(projectBreakdown);
						row.createCell(Constants.SHORT69).setCellValue(legalPersonCardtype);
						row.createCell(Constants.SHORT70).setCellValue(necessary);
						row.createCell(Constants.SHORT71).setCellValue(weixinID);
						row.createCell(Constants.SHORT72).setCellValue(chainCode);
						row.createCell(Constants.SHORT73).setCellValue(storesNumber);
						row.createCell(Constants.SHORT74).setCellValue(storeName);
						row.createCell(Constants.SHORT75).setCellValue(email);
						

					}
				}

				LOGGER.info("拼装商户审核信息完成!");
				String filename = "WOFE-commercialtenantchecklist";

				os = response.getOutputStream();

				response.reset();

				response.setCharacterEncoding("UTF-8");

				filename = java.net.URLEncoder.encode(filename, "UTF-8");

				response.setHeader("Content-Disposition",
						"attachment;filename=" + new String(filename.getBytes("UTF-8"), "GBK") + ".xls");

				response.setContentType("application/msexcel");// 定义输出类型

				book.write(os);

				response.getOutputStream().flush();
				response.getOutputStream().close();
			}

		} catch (Exception e) {

			LOGGER.error("审核商户. 异常  msg：" + e.getMessage(), e);

		} finally {

			try {
				os.flush();
				os.close();
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}

		}
	}
	
	
	/**
	 * 审核商户.
	 * 
	 * @param map
	 *            Map<String,Object>
	 * @param source
	 *            int
	 * @param id2
	 *            long
	 * @param request
	 *            HttpServletRequest
	 * @return URL
	 */
	@RequestMapping("/user/viewInfo")
	public String viewInfo(Map<String, Object> map, int source, long id2, HttpServletRequest request) {

		try {
			if (source == Constants.INT1) {
				Supplier object = RemoteServiceSingleton.getInstance().getSupplierManagerService().findSupplier(id2);
				map.put("data", object);
				SupplierProduct product = RemoteServiceSingleton.getInstance().getSupplierManagerService()
						.getProductBySupplierId(id2);
				List<TdCatePub> childrenCategoryList = new ArrayList<TdCatePub>();
				if (RemoteServiceSingleton.getInstance().getCategoryServiceRpc() != null) {
					try {
						childrenCategoryList = RemoteServiceSingleton.getInstance().getCategoryServiceRpc()
								.getTopCategoryList();
					} catch (Exception e) {
						LOGGER.error("获取类目失败！");
					}
				}
				List<String> categoryNameCn = new ArrayList<String>();
				List<String> categoryNameEn = new ArrayList<String>();
				if (!childrenCategoryList.isEmpty()) {
					for (TdCatePub tdCatePub : childrenCategoryList) {
						// tdCatePub.
						categoryNameCn.add(tdCatePub.getPubNameCn());
						categoryNameEn.add(tdCatePub.getPubName());
					}
				}

				// 如果是pop类型，取得仓库的accountType
				if (object.getSupplyType() == SupplierSouces.POP.getIndex()) {
					Warehouse warehouse = RemoteServiceSingleton.getInstance().getStockWofeService()
							.findWarehouseByWarehouseCode(Integer.valueOf(object.getSupplierId().toString()));
					if (warehouse != null && warehouse.getAccountType() != null
							&& warehouse.getAccountType().intValue() > 0) {
						map.put("paymentType", warehouse.getAccountType());
					}
				}

				// 封装国家列表
				List<TcCountry> countries = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc()
						.getTcCountries();
				// 根据上级企业supplierId获取上级企业对象
				if ((!StringUtils.isEmpty(object.getSjSupplierId())) && !object.getSjSupplierId().equals("0")) {
					Supplier sjSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
							.findSupplier(Long.valueOf(object.getSjSupplierId()));
					map.put("sjSupplier", sjSupplier);
				}
				// 获取所有入驻区域类型
				List<SupplierRegionSettings> regionList = supplierRegionService.getAllRegionSettings();
				map.put("regionList", regionList);

				map.put("allList", EnumSet.allOf(SupplierSouce.class));
				map.put("category", JSONObject.toJSONString(categoryNameCn));
				map.put("product", product);
				map.put("country", countries);

				return "user/supplierInfo";
			} 
		} catch (Exception e) {
			LOGGER.error("审核商户. 异常  msg：" + e.getMessage());
			LOGGER.error("商户Id:" + getCurrentPlatformId());
			LOGGER.error("用户:" + getCurrentUser().getUsername());
			LOGGER.error("用户ID:" + getCurrentUser().getId());
		}
		return "redirect:/user/checklist?source=" + source;

	}

	@RequestMapping("/user/viewInfonewLook")
	public String viewInfonewLook(Map<String, Object> map, int source, long id2, HttpServletRequest request, Model model)
			throws Exception {

		try {
			if (source == Constants.INT1) {
				Supplier object = RemoteServiceSingleton.getInstance().getSupplierManagerService().findSupplier(id2);
				map.put("data", object);

				
				//获取银行
				String accoutBankno = object.getAccoutBankno();
				BankBranch bb = bankService.findBankBranchByCode(accoutBankno);
				map.put("bb",bb);
				
				SupplierPartnerArea area = supplierManagerService.findPartnerArea(id2, null);
				if (area != null) {
					Long provinceId2 = area.getProvinceId();
					Long cityId2 = area.getCityId();
					Long countyId2 = area.getCountyId();

					if (provinceId2 != null) {
						int provinceId = Integer.parseInt(String.valueOf(provinceId2));
						AgentProvince province = baseDataServiceRpc2.getProvinceById(provinceId);
						model.addAttribute("province", province);
					}
					if (cityId2 != null) {
						int cityId = Integer.parseInt(String.valueOf(cityId2));
						AgentCity city = baseDataServiceRpc2.getCityById(cityId);
						model.addAttribute("city", city);
					}
					if (countyId2 != null) {
						int countyId = Integer.parseInt(String.valueOf(countyId2));
						AgentCounty county = baseDataServiceRpc2.getCountyById(countyId);
						model.addAttribute("county", county);
					}
				}

				SupplierProduct product = RemoteServiceSingleton.getInstance().getSupplierManagerService()
						.getProductBySupplierId(id2);
				List<TdCatePub> childrenCategoryList = new ArrayList<TdCatePub>();
				if (RemoteServiceSingleton.getInstance().getCategoryServiceRpc() != null) {
					try {
						childrenCategoryList = RemoteServiceSingleton.getInstance().getCategoryServiceRpc()
								.getTopCategoryList();
					} catch (Exception e) {
						LOGGER.error("获取类目失败！");
					}
				}
				List<String> categoryNameCn = new ArrayList<String>();
				List<String> categoryNameEn = new ArrayList<String>();
				if (!childrenCategoryList.isEmpty()) {
					for (TdCatePub tdCatePub : childrenCategoryList) {
						// tdCatePub.
						categoryNameCn.add(tdCatePub.getPubNameCn());
						categoryNameEn.add(tdCatePub.getPubName());
					}
				}

				// 如果是pop类型，取得仓库的accountType
				if (object.getSupplyType() == SupplierSouces.POP.getIndex()) {
					Warehouse warehouse = RemoteServiceSingleton.getInstance().getStockWofeService()
							.findWarehouseByWarehouseCode(Integer.valueOf(object.getSupplierId().toString()));
					if (warehouse != null && warehouse.getAccountType() != null
							&& warehouse.getAccountType().intValue() > 0) {
						map.put("paymentType", warehouse.getAccountType());
					}
				}

				// 封装国家列表
				List<TcCountry> countries = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc()
						.getTcCountries();
				// 根据上级企业supplierId获取上级企业对象
				if ((!StringUtils.isEmpty(object.getSjSupplierId())) && !object.getSjSupplierId().equals("0")) {
					Supplier sjSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
							.findSupplier(Long.valueOf(object.getSjSupplierId()));
					map.put("sjSupplier", sjSupplier);
				}
				// 获取所有入驻区域类型
				List<SupplierRegionSettings> regionList = supplierRegionService.getAllRegionSettings();
				map.put("regionList", regionList);

				map.put("allList", EnumSet.allOf(SupplierSouce.class));
				map.put("category", JSONObject.toJSONString(categoryNameCn));
				map.put("product", product);
				map.put("country", countries);

				return "user/supplierInfoAgent";
			}

		} catch (Exception e) {
			LOGGER.error("审核商户. 异常  msg：" + e.getMessage());
			LOGGER.error("商户Id:" + getCurrentPlatformId());
			LOGGER.error("用户:" + getCurrentUser().getUsername());
			LOGGER.error("用户ID:" + getCurrentUser().getId());
		}
		return "redirect:/user/checklistAgent?source=" + source;

	}

	@RequestMapping("/user/viewInfoLook")
	public String viewInfo(Map<String, Object> map, int source, long id2, Model model) {

		try {
			if (source == Constants.INT1) {
				//获取supplier对象
				Supplier object = RemoteServiceSingleton.getInstance().getSupplierManagerService().findSupplier(id2);
				map.put("data", object);
				
				
				//获取银行
				String accoutBankno = object.getAccoutBankno();
				BankBranch bb = bankService.findBankBranchByCode(accoutBankno);
				map.put("bb", bb);
				
				
				//获取省市区
				SupplierPartnerArea area = supplierManagerService.findPartnerArea(id2, null);
				if (area != null) {
					Long provinceId2 = area.getProvinceId();
					Long cityId2 = area.getCityId();
					Long countyId2 = area.getCountyId();

					if (provinceId2 != null) {
						int provinceId = Integer.parseInt(String.valueOf(provinceId2));
						AgentProvince province = baseDataServiceRpc2.getProvinceById(provinceId);
						model.addAttribute("province", province);
					}
					if (cityId2 != null) {
						int cityId = Integer.parseInt(String.valueOf(cityId2));
						AgentCity city = baseDataServiceRpc2.getCityById(cityId);
						model.addAttribute("city", city);
					}
					if (countyId2 != null) {
						int countyId = Integer.parseInt(String.valueOf(countyId2));
						AgentCounty county = baseDataServiceRpc2.getCountyById(countyId);
						model.addAttribute("county", county);
					}

				}
                //折扣回显
				SupplierSalesDiscount ssd = supplierDiscountService.findBySupplierId(id2);
				BigDecimal salesDiscount = ssd.getSalesDiscount();
				model.addAttribute("salesDiscount",salesDiscount);
				SupplierProduct product = RemoteServiceSingleton.getInstance().getSupplierManagerService()
						.getProductBySupplierId(id2);
				List<TdCatePub> childrenCategoryList = new ArrayList<TdCatePub>();
				if (RemoteServiceSingleton.getInstance().getCategoryServiceRpc() != null) {
					try {
						childrenCategoryList = RemoteServiceSingleton.getInstance().getCategoryServiceRpc()
								.getTopCategoryList();
					} catch (Exception e) {
						LOGGER.error("获取类目失败！");
					}
				}
				List<String> categoryNameCn = new ArrayList<String>();
				List<String> categoryNameEn = new ArrayList<String>();
				if (!childrenCategoryList.isEmpty()) {
					for (TdCatePub tdCatePub : childrenCategoryList) {
						// tdCatePub.
						categoryNameCn.add(tdCatePub.getPubNameCn());
						categoryNameEn.add(tdCatePub.getPubName());
					}
				}

				// 如果是pop类型，取得仓库的accountType
				if (object.getSupplyType() == SupplierSouces.POP.getIndex()) {
					Warehouse warehouse = RemoteServiceSingleton.getInstance().getStockWofeService()
							.findWarehouseByWarehouseCode(Integer.valueOf(object.getSupplierId().toString()));
					if (warehouse != null && warehouse.getAccountType() != null
							&& warehouse.getAccountType().intValue() > 0) {
						map.put("paymentType", warehouse.getAccountType());
					}
				}
				// 封装国家列表
				List<TcCountry> countries = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc()
						.getTcCountries();
				// 根据上级企业supplierId获取上级企业对象
				if ((!StringUtils.isEmpty(object.getSjSupplierId())) && !object.getSjSupplierId().equals("0")) {
					Supplier sjSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
							.findSupplier(Long.valueOf(object.getSjSupplierId()));
					map.put("sjSupplier", sjSupplier);
				}
				// 获取所有入驻区域类型
				List<SupplierRegionSettings> regionList = supplierRegionService.getAllRegionSettings();
				map.put("regionList", regionList);

				map.put("allList", EnumSet.allOf(SupplierSouce.class));
				
				map.put("country", countries);

				map.put("category", JSONObject.toJSONString(categoryNameCn));
				map.put("product", product);
				

				
				return "user/supplierInfoLineNew";
			} 

		} catch (Exception e) {
			LOGGER.error("审核商户. 异常  msg：" + e.getMessage());
			LOGGER.error("商户Id:" + getCurrentPlatformId());
			LOGGER.error("用户:" + getCurrentUser().getUsername());
			LOGGER.error("用户ID:" + getCurrentUser().getId());
		}
		return "redirect:/user/checklistLine?source=" + source;

	}

	@RequestMapping("/user/viewInfoPartner")
	public String viewInfoPartner(Map<String, Object> map, int source, long id2, HttpServletRequest request) {

		try {
			if (source == Constants.INT1) {
				Supplier object = RemoteServiceSingleton.getInstance().getSupplierManagerService().findSupplier(id2);
				map.put("data", object);
				SupplierProduct product = RemoteServiceSingleton.getInstance().getSupplierManagerService()
						.getProductBySupplierId(id2);
				List<TdCatePub> childrenCategoryList = new ArrayList<TdCatePub>();
				if (RemoteServiceSingleton.getInstance().getCategoryServiceRpc() != null) {
					try {
						childrenCategoryList = RemoteServiceSingleton.getInstance().getCategoryServiceRpc()
								.getTopCategoryList();
					} catch (Exception e) {
						LOGGER.error("获取类目失败！");
					}
				}
				List<String> categoryNameCn = new ArrayList<String>();
				List<String> categoryNameEn = new ArrayList<String>();
				if (!childrenCategoryList.isEmpty()) {
					for (TdCatePub tdCatePub : childrenCategoryList) {
						// tdCatePub.
						categoryNameCn.add(tdCatePub.getPubNameCn());
						categoryNameEn.add(tdCatePub.getPubName());
					}
				}

				// 如果是pop类型，取得仓库的accountType
				if (object.getSupplyType() == SupplierSouces.POP.getIndex()) {
					Warehouse warehouse = RemoteServiceSingleton.getInstance().getStockWofeService()
							.findWarehouseByWarehouseCode(Integer.valueOf(object.getSupplierId().toString()));
					if (warehouse != null && warehouse.getAccountType() != null
							&& warehouse.getAccountType().intValue() > 0) {
						map.put("paymentType", warehouse.getAccountType());
					}
				}

				// 封装国家列表
				List<TcCountry> countries = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc()
						.getTcCountries();
				// 根据上级企业supplierId获取上级企业对象
				if ((!StringUtils.isEmpty(object.getSjSupplierId())) && !object.getSjSupplierId().equals("0")) {
					Supplier sjSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
							.findSupplier(Long.valueOf(object.getSjSupplierId()));
					map.put("sjSupplier", sjSupplier);
				}
				// 获取所有入驻区域类型
				List<SupplierRegionSettings> regionList = supplierRegionService.getAllRegionSettings();
				map.put("regionList", regionList);

				map.put("allList", EnumSet.allOf(SupplierSouce.class));
				map.put("category", JSONObject.toJSONString(categoryNameCn));
				map.put("product", product);
				map.put("country", countries);

				return "user/supplierInfoPartner";
			} 

		} catch (Exception e) {
			LOGGER.error("审核商户. 异常  msg：" + e.getMessage());
			LOGGER.error("商户Id:" + getCurrentPlatformId());
			LOGGER.error("用户:" + getCurrentUser().getUsername());
			LOGGER.error("用户ID:" + getCurrentUser().getId());
		}
		return "redirect:/user/checklist?source=" + source;

	}

	@RequestMapping("/user/viewInfonew")
	public String viewInfonew(Map<String, Object> map, int source, long id2, HttpServletRequest request) {

		try {
			if (source == Constants.INT1) {
				Supplier object = RemoteServiceSingleton.getInstance().getSupplierManagerService().findSupplier(id2);
				map.put("data", object);
				SupplierProduct product = RemoteServiceSingleton.getInstance().getSupplierManagerService()
						.getProductBySupplierId(id2);
				List<TdCatePub> childrenCategoryList = new ArrayList<TdCatePub>();
				if (RemoteServiceSingleton.getInstance().getCategoryServiceRpc() != null) {
					try {

						childrenCategoryList = RemoteServiceSingleton.getInstance().getCategoryServiceRpc()
								.getTopCategoryList();
					} catch (Exception e) {
						LOGGER.error("获取类目失败！");
					}
				}
				List<String> categoryNameCn = new ArrayList<String>();
				List<String> categoryNameEn = new ArrayList<String>();
				if (!childrenCategoryList.isEmpty()) {
					for (TdCatePub tdCatePub : childrenCategoryList) {
						// tdCatePub.
						categoryNameCn.add(tdCatePub.getPubNameCn());
						categoryNameEn.add(tdCatePub.getPubName());
					}
				}

				// 如果是pop类型，取得仓库的accountType
				if (object.getSupplyType() == SupplierSouces.POP.getIndex()) {
					Warehouse warehouse = RemoteServiceSingleton.getInstance().getStockWofeService()
							.findWarehouseByWarehouseCode(Integer.valueOf(object.getSupplierId().toString()));
					if (warehouse != null && warehouse.getAccountType() != null
							&& warehouse.getAccountType().intValue() > 0) {
						map.put("paymentType", warehouse.getAccountType());
					}
				}

				// 封装国家列表
				List<TcCountry> countries = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc()
						.getTcCountries();
				// 根据上级企业supplierId获取上级企业对象
				if ((!StringUtils.isEmpty(object.getSjSupplierId())) && !object.getSjSupplierId().equals("0")) {
					Supplier sjSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
							.findSupplier(Long.valueOf(object.getSjSupplierId()));
					map.put("sjSupplier", sjSupplier);
				}
				// 获取所有入驻区域类型
				List<SupplierRegionSettings> regionList = supplierRegionService.getAllRegionSettings();
				map.put("regionList", regionList);

				map.put("allList", EnumSet.allOf(SupplierSouce.class));
				map.put("category", JSONObject.toJSONString(categoryNameCn));
				map.put("product", product);
				map.put("country", countries);

				return "user/supplierInfo3";
			} /*
				 * else if(source==Constants.INT2){
				 * 
				 * Retailer retailer = RemoteServiceSingleton.getInstance().
				 * getRetailerManagerService().findByRetailerId(id2);
				 * 
				 * RetailerFile file = RemoteServiceSingleton.getInstance().
				 * getRetailerManagerService().getRetailerFileById(id2);
				 * 
				 * List<PlatformSale> allSale =
				 * RemoteServiceSingleton.getInstance().
				 * getPlatformSaleManagerService().getAllSale();
				 * 
				 * //省 Integer provinceId = retailer.getProvinceId(); String
				 * provincename = ""; if(null != provinceId){
				 * 
				 * provincename =
				 * RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().
				 * getProvinceById(provinceId).getProvincename(); }
				 * 
				 * 
				 * //市 Integer cityId = retailer.getCityId(); String city = "";
				 * 
				 * //获取市对应的区县信息 List<AgentCounty> counties = new
				 * ArrayList<AgentCounty>(); if(null != cityId){
				 * 
				 * city =
				 * RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().
				 * getCityById(cityId).getCityname();
				 * 
				 * counties =
				 * RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().
				 * getCountiesByCityId(cityId); }
				 * 
				 * //商户销售渠道 List<PlatformChannel> list =
				 * RemoteServiceSingleton.getInstance().getChannelManagerService
				 * ().getAllChannel();
				 * 
				 * map.put("list", list); map.put("allSale", allSale);
				 * map.put("counties", counties); map.put("provincename",
				 * provincename); map.put("city", city); map.put("data",
				 * retailer); map.put("dataFile", file);
				 * map.put("picUrl",Constants.FILESERVER1); // return
				 * "user/retailerInfo"; return "user/retailerInfoNew"; }else
				 * if(source==Constants.INT3){ map.put("data",
				 * RemoteServiceSingleton.getInstance().getDealerService().
				 * findByDealerId(id2)); DealerFile dealerFile =
				 * RemoteServiceSingleton.getInstance().getDealerFileService().
				 * getFileByDealerId(id2); map.put("dataFile", dealerFile);
				 * return "user/dealerInfo"; }
				 */

		} catch (Exception e) {
			LOGGER.error("审核商户. 异常  msg：" + e.getMessage());
			LOGGER.error("商户Id:" + getCurrentPlatformId());
			LOGGER.error("用户:" + getCurrentUser().getUsername());
			LOGGER.error("用户ID:" + getCurrentUser().getId());
		}
		return "redirect:/user/1?source=" + source;

	}
    //查看   1会员商户
	@RequestMapping("/user/viewInfonew1")
	public String viewInfonew1(Map<String, Object> map, int source, long id2, HttpServletRequest request) {

		try {
			if (source == Constants.INT1) {
				Supplier object = RemoteServiceSingleton.getInstance().getSupplierManagerService().findSupplier(id2);
				map.put("data", object);
				SupplierProduct product = RemoteServiceSingleton.getInstance().getSupplierManagerService()
						.getProductBySupplierId(id2);
				List<TdCatePub> childrenCategoryList = new ArrayList<TdCatePub>();
				if (RemoteServiceSingleton.getInstance().getCategoryServiceRpc() != null) {
					try {
						childrenCategoryList = RemoteServiceSingleton.getInstance().getCategoryServiceRpc()
								.getTopCategoryList();
					} catch (Exception e) {
						LOGGER.error("获取类目失败！");
					}
				}
				List<String> categoryNameCn = new ArrayList<String>();
				List<String> categoryNameEn = new ArrayList<String>();
				if (!childrenCategoryList.isEmpty()) {
					for (TdCatePub tdCatePub : childrenCategoryList) {
						// tdCatePub.
						categoryNameCn.add(tdCatePub.getPubNameCn());
						categoryNameEn.add(tdCatePub.getPubName());
					}
				}

				// 如果是pop类型，取得仓库的accountType
				if (object.getSupplyType() == SupplierSouces.POP.getIndex()) {
					Warehouse warehouse = RemoteServiceSingleton.getInstance().getStockWofeService()
							.findWarehouseByWarehouseCode(Integer.valueOf(object.getSupplierId().toString()));
					if (warehouse != null && warehouse.getAccountType() != null
							&& warehouse.getAccountType().intValue() > 0) {
						map.put("paymentType", warehouse.getAccountType());
					}
				}

				// 封装国家列表
				List<TcCountry> countries = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc()
						.getTcCountries();
				// 根据上级企业supplierId获取上级企业对象
				if ((!StringUtils.isEmpty(object.getSjSupplierId())) && !object.getSjSupplierId().equals("0")) {
					Supplier sjSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
							.findSupplier(Long.valueOf(object.getSjSupplierId()));
					map.put("sjSupplier", sjSupplier);
				}
				// 获取所有入驻区域类型
				List<SupplierRegionSettings> regionList = supplierRegionService.getAllRegionSettings();
				map.put("regionList", regionList);

				map.put("allList", EnumSet.allOf(SupplierSouce.class));
				map.put("category", JSONObject.toJSONString(categoryNameCn));
				map.put("product", product);
				map.put("country", countries);

				return "user/supplierInfo4";
			} /*
				 * else if(source==Constants.INT2){
				 * 
				 * Retailer retailer = RemoteServiceSingleton.getInstance().
				 * getRetailerManagerService().findByRetailerId(id2);
				 * 
				 * RetailerFile file = RemoteServiceSingleton.getInstance().
				 * getRetailerManagerService().getRetailerFileById(id2);
				 * 
				 * List<PlatformSale> allSale =
				 * RemoteServiceSingleton.getInstance().
				 * getPlatformSaleManagerService().getAllSale();
				 * 
				 * //省 Integer provinceId = retailer.getProvinceId(); String
				 * provincename = ""; if(null != provinceId){
				 * 
				 * provincename =
				 * RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().
				 * getProvinceById(provinceId).getProvincename(); }
				 * 
				 * 
				 * //市 Integer cityId = retailer.getCityId(); String city = "";
				 * 
				 * //获取市对应的区县信息 List<AgentCounty> counties = new
				 * ArrayList<AgentCounty>(); if(null != cityId){
				 * 
				 * city =
				 * RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().
				 * getCityById(cityId).getCityname();
				 * 
				 * counties =
				 * RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().
				 * getCountiesByCityId(cityId); }
				 * 
				 * //商户销售渠道 List<PlatformChannel> list =
				 * RemoteServiceSingleton.getInstance().getChannelManagerService
				 * ().getAllChannel();
				 * 
				 * map.put("list", list); map.put("allSale", allSale);
				 * map.put("counties", counties); map.put("provincename",
				 * provincename); map.put("city", city); map.put("data",
				 * retailer); map.put("dataFile", file);
				 * map.put("picUrl",Constants.FILESERVER1); // return
				 * "user/retailerInfo"; return "user/retailerInfoNew3"; }else
				 * if(source==Constants.INT3){ map.put("data",
				 * RemoteServiceSingleton.getInstance().getDealerService().
				 * findByDealerId(id2)); DealerFile dealerFile =
				 * RemoteServiceSingleton.getInstance().getDealerFileService().
				 * getFileByDealerId(id2); map.put("dataFile", dealerFile);
				 * return "user/dealerInfo3"; }
				 */
		} catch (Exception e) {
			LOGGER.error("审核商户. 异常  msg：" + e.getMessage());
			LOGGER.error("商户Id:" + getCurrentPlatformId());
			LOGGER.error("用户:" + getCurrentUser().getUsername());
			LOGGER.error("用户ID:" + getCurrentUser().getId());
		}
		return "redirect:/user/1?source=" + source;

	}

	/*
	 * 已备案修改 isrecord = 1 status = 1
	 * 
	 */
	@RequestMapping("/user/viewInfo2")
	public String viewInfo2(Map<String, Object> map, int source, long id1, HttpServletRequest request) {

		try {
			/*
			 * if(source==Constants.INT2){
			 * 
			 * Retailer retailer =
			 * RemoteServiceSingleton.getInstance().getRetailerManagerService().
			 * findByRetailerId(id1);
			 * 
			 * RetailerFile file =
			 * RemoteServiceSingleton.getInstance().getRetailerManagerService().
			 * getRetailerFileById(id1);
			 * 
			 * List<PlatformSale> allSale =
			 * RemoteServiceSingleton.getInstance().
			 * getPlatformSaleManagerService().getAllSale();
			 * 
			 * //省 Integer provinceId = retailer.getProvinceId(); String
			 * provincename = ""; if(null != provinceId){
			 * 
			 * provincename =
			 * RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().
			 * getProvinceById(provinceId).getProvincename(); }
			 * 
			 * 
			 * //市 Integer cityId = retailer.getCityId(); String city = "";
			 * 
			 * //获取市对应的区县信息 List<AgentCounty> counties = new
			 * ArrayList<AgentCounty>(); if(null != cityId){
			 * 
			 * city =
			 * RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().
			 * getCityById(cityId).getCityname();
			 * 
			 * counties =
			 * RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().
			 * getCountiesByCityId(cityId); }
			 * 
			 * //商户销售渠道 List<PlatformChannel> list =
			 * RemoteServiceSingleton.getInstance().getChannelManagerService().
			 * getAllChannel();
			 * 
			 * map.put("list", list); map.put("allSale", allSale);
			 * map.put("counties", counties); map.put("provincename",
			 * provincename); map.put("city", city); map.put("data", retailer);
			 * map.put("dataFile", file);
			 * map.put("picUrl",Constants.FILESERVER1); return
			 * "user/retailerInfoXG"; }
			 */

		} catch (Exception e) {
			LOGGER.error("审核商户. 异常  msg：" + e.getMessage());
			LOGGER.error("商户Id:" + getCurrentPlatformId());
			LOGGER.error("用户:" + getCurrentUser().getUsername());
			LOGGER.error("用户ID:" + getCurrentUser().getId());
		}
		return "redirect:/user/checklist?source=" + source;

	}

	/**
	 * 销售备案.
	 * 
	 * @param map
	 *            Map<String,Object>
	 * @param source
	 *            int
	 * @param id1
	 *            long
	 * @param request
	 *            HttpServletRequest
	 * @return URL
	 */
	@RequestMapping("/user/viewInfo1")
	public String viewInfo1(Map<String, Object> map, int source, long id1, HttpServletRequest request) {
		/*
		 * List<TdCatePub> childrenCategoryList = new ArrayList<TdCatePub>();
		 * if(RemoteServiceSingleton.getInstance().getCategoryServiceRpc()!=null
		 * ){ try { childrenCategoryList =
		 * RemoteServiceSingleton.getInstance().getCategoryServiceRpc().
		 * getTopCategoryList(); } catch (Exception e) {
		 * LOGGER.error("获取类目失败！"); } } List<String> categoryNameCn = new
		 * ArrayList<String>(); List<String> categoryNameEn = new
		 * ArrayList<String>(); if(!childrenCategoryList.isEmpty()){ for
		 * (TdCatePub tdCatePub : childrenCategoryList) { // tdCatePub.
		 * categoryNameCn.add(tdCatePub.getPubNameCn());
		 * categoryNameEn.add(tdCatePub.getPubName()); } }
		 * map.put("category",JSONObject.toJSONString(categoryNameCn) );
		 */
		try {
			if (source == Constants.INT1) {
				map.put("data", RemoteServiceSingleton.getInstance().getSupplierManagerService().findSupplier(id1));
				SupplierProduct product = RemoteServiceSingleton.getInstance().getSupplierManagerService()
						.getProductBySupplierId(id1);
				map.put("product", product);
				return "user/supplierInfo";
			} /*
				 * else if(source==Constants.INT2){
				 * 
				 * Retailer retailer = RemoteServiceSingleton.getInstance().
				 * getRetailerManagerService().findByRetailerId(id1);
				 * 
				 * RetailerFile file = RemoteServiceSingleton.getInstance().
				 * getRetailerManagerService().getRetailerFileById(id1);
				 * 
				 * List<PlatformSale> allSale =
				 * RemoteServiceSingleton.getInstance().
				 * getPlatformSaleManagerService().getAllSale();
				 * 
				 * //省 Integer provinceId = retailer.getProvinceId(); String
				 * provincename = ""; if(null != provinceId){
				 * 
				 * provincename =
				 * RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().
				 * getProvinceById(provinceId).getProvincename(); }
				 * 
				 * 
				 * //市 Integer cityId = retailer.getCityId(); String city = "";
				 * 
				 * //获取市对应的区县信息 List<AgentCounty> counties = new
				 * ArrayList<AgentCounty>(); if(null != cityId){
				 * 
				 * city =
				 * RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().
				 * getCityById(cityId).getCityname();
				 * 
				 * counties =
				 * RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().
				 * getCountiesByCityId(cityId); }
				 * 
				 * //商户销售渠道 List<PlatformChannel> list =
				 * RemoteServiceSingleton.getInstance().getChannelManagerService
				 * ().getAllChannel();
				 * 
				 * map.put("list", list); map.put("allSale", allSale);
				 * map.put("counties", counties); map.put("provincename",
				 * provincename); map.put("city", city); map.put("data",
				 * retailer); map.put("dataFile", file);
				 * map.put("picUrl",Constants.FILESERVER1); // return
				 * "user/retailerInfo"; return "user/retailerInfoBN"; }else
				 * if(source==Constants.INT3){ map.put("data",
				 * RemoteServiceSingleton.getInstance().getDealerService().
				 * findByDealerId(id1)); DealerFile dealerFile =
				 * RemoteServiceSingleton.getInstance().getDealerFileService().
				 * getFileByDealerId(id1); map.put("dataFile", dealerFile);
				 * return "user/dealerInfo"; }
				 */

		} catch (Exception e) {
			LOGGER.error("审核商户. 异常  msg：" + e.getMessage());
			LOGGER.error("商户Id:" + getCurrentPlatformId());
			LOGGER.error("用户:" + getCurrentUser().getUsername());
			LOGGER.error("用户ID:" + getCurrentUser().getId());
		}
		return "redirect:/user/checklist?source=" + source;

	}

	/*
	 * 备案跳转
	 */

	@RequestMapping("/user/viewInfoBN")
	public String viewInfoBN(Map<String, Object> map, int source, Long id2, HttpServletRequest request) {
		List<TdCatePub> childrenCategoryList = new ArrayList<TdCatePub>();
		if (RemoteServiceSingleton.getInstance().getCategoryServiceRpc() != null) {
			try {
				childrenCategoryList = RemoteServiceSingleton.getInstance().getCategoryServiceRpc()
						.getTopCategoryList();
			} catch (Exception e) {
				LOGGER.error("获取类目失败！");
			}
		}
		List<String> categoryNameCn = new ArrayList<String>();
		List<String> categoryNameEn = new ArrayList<String>();
		if (!childrenCategoryList.isEmpty()) {
			for (TdCatePub tdCatePub : childrenCategoryList) {
				// tdCatePub.
				categoryNameCn.add(tdCatePub.getPubNameCn());
				categoryNameEn.add(tdCatePub.getPubName());
			}
		}
		map.put("category", JSONObject.toJSONString(categoryNameCn));
		try {
			if (source == Constants.INT1) {
				map.put("data", RemoteServiceSingleton.getInstance().getSupplierManagerService().findSupplier(id2));
				SupplierProduct product = RemoteServiceSingleton.getInstance().getSupplierManagerService()
						.getProductBySupplierId(id2);
				map.put("product", product);
				return "user/supplierInfo";
			} /*
				 * else if(source==Constants.INT2){
				 * 
				 * Retailer retailer = RemoteServiceSingleton.getInstance().
				 * getRetailerManagerService().findByRetailerId(id2);
				 * 
				 * RetailerFile file = RemoteServiceSingleton.getInstance().
				 * getRetailerManagerService().getRetailerFileById(id2);
				 * 
				 * List<PlatformSale> allSale =
				 * RemoteServiceSingleton.getInstance().
				 * getPlatformSaleManagerService().getAllSale();
				 * 
				 * //省 Integer provinceId = retailer.getProvinceId(); String
				 * provincename = ""; if(null != provinceId){
				 * 
				 * provincename =
				 * RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().
				 * getProvinceById(provinceId).getProvincename(); }
				 * 
				 * 
				 * //市 Integer cityId = retailer.getCityId(); String city = "";
				 * 
				 * //获取市对应的区县信息 List<AgentCounty> counties = new
				 * ArrayList<AgentCounty>(); if(null != cityId){
				 * 
				 * city =
				 * RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().
				 * getCityById(cityId).getCityname();
				 * 
				 * counties =
				 * RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().
				 * getCountiesByCityId(cityId); }
				 * 
				 * //商户销售渠道 List<PlatformChannel> list =
				 * RemoteServiceSingleton.getInstance().getChannelManagerService
				 * ().getAllChannel();
				 * 
				 * map.put("list", list); map.put("allSale", allSale);
				 * map.put("counties", counties); map.put("provincename",
				 * provincename); map.put("city", city); map.put("data",
				 * retailer); map.put("dataFile", file); // return
				 * "user/retailerInfo"; return "user/retailerInfoNew"; }else
				 * if(source==Constants.INT3){ map.put("data",
				 * RemoteServiceSingleton.getInstance().getDealerService().
				 * findByDealerId(id2)); DealerFile dealerFile =
				 * RemoteServiceSingleton.getInstance().getDealerFileService().
				 * getFileByDealerId(id2); map.put("dataFile", dealerFile);
				 * return "user/dealerInfo"; }
				 */

		} catch (Exception e) {
			LOGGER.error("审核商户. 异常  msg：" + e.getMessage());
			LOGGER.error("商户Id:" + getCurrentPlatformId());
			LOGGER.error("用户:" + getCurrentUser().getUsername());
			LOGGER.error("用户ID:" + getCurrentUser().getId());
		}
		return "redirect:/user/checklist?source=" + source;

	}

	/**
	 * 退出登录.
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		try {
			UserUtil.userLogout(request, response);
		} catch (Exception e) {
			LOGGER.error("商户Id:" + getCurrentPlatformId());
			LOGGER.error("用户:" + getCurrentUser().getUsername());
			LOGGER.error("用户ID:" + getCurrentUser().getId());
			LOGGER.error("退出登录异常:" + e);
		}
		return "redirect:/login";
	}

	public void removeRedisByCookieSid(HttpServletRequest request) {
		Cookie cookie = CookieUtil.getCookie(request, ConfigConstant.MEMBER);

		/** 判断cookie是否为空 */
		String sid = "";
		if (!StringUtils.isEmpty(cookie)) {
			sid = cookie.getValue();
			JedisManager.delObject(sid);
		}
	}

	/**
	 * 获取一级目录的url.
	 */
	public PlatformMenu getDefaultUrlByPid(List<PlatformMenu> list, Long pid) {
		if (null != pid) {
			for (int i = 0; i < list.size(); i++) {
				if (null != list.get(i).getParentMenuId()
						&& pid == Long.parseLong(list.get(i).getParentMenuId() + "")) {
					if (!list.get(i).getUrl().contains("/")) {
						return getDefaultUrlByPid(list, list.get(i).getMenuId());
					} else {
						return list.get(i);
					}

				}
			}
		}
		return new PlatformMenu();

	}

	/**
	 * @Description:供应商信息表下载.
	 * @param response
	 *            HttpServletResponse
	 * @param url
	 *            url参数
	 */
	@RequestMapping("/user/downloadFile")
	public void download(HttpServletResponse response, String url) {
		if (null != url && url.length() > 0) {
			try {
				download(response, url, null);
			} catch (Exception e) {
				try {
					response.getWriter().write("0");
				} catch (IOException e1) {
					LOGGER.error("下载文件错误！", e);

				}
			}
		} else {
			try {
				response.getWriter().write("0");
			} catch (IOException e) {

				LOGGER.error("下载文件错误！", e);
				LOGGER.error("商户Id:" + getCurrentPlatformId());
				LOGGER.error("用户:" + getCurrentUser().getUsername());
				LOGGER.error("用户ID:" + getCurrentUser().getId());

			}
		}
	}

	/**
	 * @return DealerList
	 */
	/*
	 * @RequestMapping(value="/user/getDealerList")
	 * 
	 * @ResponseBody public String getDealerList(String dealerName){
	 * List<Dealer> dealersList = new ArrayList<Dealer>(); if(dealerName!=null){
	 * dealersList = RemoteServiceSingleton.getInstance().
	 * getDealerService().getDealersByMerchantName(dealerName); } return
	 * JSONArray.fromObject(dealersList).toString(); }
	 */
	/**
	 * @return getSupplierList
	 */
	@RequestMapping(value = "/user/getSupplierList")
	@ResponseBody
	public String getSupplierList(String supplierName) {
		List<Supplier> suppliersList = new ArrayList<Supplier>();
		if (supplierName != null) {
			suppliersList = RemoteServiceSingleton.getInstance().getSupplierManagerService()
					.findAllSupplier(supplierName);
		}
		return JSONArray.fromObject(suppliersList).toString();
	}

	/**
	 * @return DealerList
	 */
	/*
	 * @RequestMapping(value="/user/addVatInvoices") public String
	 * addVatInvoices(int source, RetailerFile file,MultipartFile
	 * myfile,MultipartFile myfile3,Retailer newRetailer){
	 * 
	 * try { RemoteServiceSingleton.getInstance().getRetailerManagerService().
	 * updateRetailer(newRetailer); if(2==source &&
	 * 1==newRetailer.getIsRecord()){ Long long1 = newRetailer.getRetailerId();
	 * Retailer fRetailer =
	 * RemoteServiceSingleton.getInstance().getRetailerManagerService().
	 * findByRetailerId(long1); String email = fRetailer.getEmail(); String
	 * subject = "zhongjumall商户备案结果"; StringBuffer content =new StringBuffer();
	 * content.append("尊贵的用户：<br> 您好！<br>"); content.append(
	 * "&nbsp;&nbsp;您的众聚商城 商户:"+fRetailer.getName()+"在"+Common.dateToString(new
	 * Date(), "yyyy年MM月dd日 HH时mm分ss秒")+",备案成功！<br>");
	 * content.append("本邮件由众聚商城系统自动发出，请勿直接回复。<br>");
	 * content.append("在操作过程中如遇到任何疑问，请发送邮件至2605803377@qq.com，我们会及时为您解答。<br>");
	 * content.append("谢谢！"); if(email!=null){ SendHtmlMail.send(email, subject,
	 * content.toString()); LOGGER.info("发送邮件成功++++++++++++"); } } } catch
	 * (Exception e) { LOGGER.info("服务异常++++++++"+e); return
	 * "redirect:/user/toError"; }
	 * 
	 * if(Common.isNull(source)){ source=Constants.INT2; } String vatInvoicesUrl
	 * =""; String effectiveCertificateUrl =""; Long id2=file.getRetailerId();
	 * try { if(2==source && 1==newRetailer.getIsRecord()){ return
	 * "redirect:/user/checklist?source="+source; }else {
	 * if(null!=myfile&&!"".equals(Common.getFileExt2(myfile.getOriginalFilename
	 * ()))){ vatInvoicesUrl =
	 * UploadFileUtil.uploadFile(myfile.getInputStream(),
	 * Common.getFileExt2(myfile.getOriginalFilename()), null); }else
	 * if(null!=myfile3&&!"".equals(Common.getFileExt2(myfile3.
	 * getOriginalFilename()))){ effectiveCertificateUrl =
	 * UploadFileUtil.uploadFile(myfile3.getInputStream(),
	 * Common.getFileExt2(myfile3.getOriginalFilename()), null); }else{
	 * 
	 * // return "redirect:/user/viewInfo1?source="+source+"&id1="+id2; return
	 * "redirect:/user/checklist?source="+source; } } } catch (Exception e) {
	 * LOGGER.error("注册：上传文件到图片服务器出错！",e); return
	 * "redirect:/user/viewInfo?source="+source+"&message=3&id2="+id2; }
	 * if(!Common.isEmpty(vatInvoicesUrl) ||
	 * !Common.isEmpty(effectiveCertificateUrl)){ int rs=0; if
	 * (!Common.isEmpty(vatInvoicesUrl)){
	 * file.setVatInvoicesUrl(vatInvoicesUrl); } if (
	 * !Common.isEmpty(effectiveCertificateUrl)){
	 * file.setEffectiveCertificateUrl(effectiveCertificateUrl); //改是否拥有一般纳税人资质
	 * Retailer retailer = new Retailer(); retailer.setIsTaxEffective(1);
	 * retailer.setRetailerId(file.getRetailerId());
	 * RemoteServiceSingleton.getInstance().getRetailerManagerService().
	 * updateRetailer(retailer);
	 * 
	 * } rs= RemoteServiceSingleton.getInstance().getRetailerManagerService().
	 * updateRetailerFileById(file); if(rs<=0){ return
	 * "redirect:/user/viewInfo?source="+source+"&message=4&id2="+id2; }else{
	 * return "redirect:/user/checklist?source="+source+"&message=ok"; } }else{
	 * return "redirect:/user/viewInfo?source="+source+"&message=2&id2="+id2; }
	 * 
	 * }
	 */

	/**
	 * @param retailer
	 *            审核中修改
	 */
	/*
	 * @RequestMapping(value="/user/updateNew")
	 * 
	 * @ResponseBody public String updateNew(int source,Retailer newRetailer){
	 * 
	 * String resaultJsonString = Constants.SUCCESS; try {
	 * RemoteServiceSingleton.getInstance().getRetailerManagerService().
	 * updateRetailer(newRetailer);
	 * 
	 * } catch (Exception e) { LOGGER.info("-----------------"+e); return ""+0;
	 * } return resaultJsonString; }
	 */

	/**
	 * 已备案修改
	 * 
	 * @param retailer
	 * 
	 */
	/*
	 * @RequestMapping("/user/updateXG") public String updateXG(int source,
	 * RetailerFile file,MultipartFile myfile,MultipartFile myfile3,Retailer
	 * newRetailer){
	 * 
	 * Retailer retailer1 =
	 * RemoteServiceSingleton.getInstance().getRetailerManagerService().
	 * findByRetailerId(newRetailer.getRetailerId());
	 * newRetailer.setIsRecord(retailer1.getIsRecord()); //
	 * newRetailer.setStatus(retailer1.getStatus());
	 * newRetailer.setStatus(3==retailer1.getStatus()?0:retailer1.getStatus());
	 * RemoteServiceSingleton.getInstance().getRetailerManagerService().
	 * updateRetailer(newRetailer);
	 * 
	 * if(Common.isNull(source)){ source=Constants.INT2; } String vatInvoicesUrl
	 * =""; String effectiveCertificateUrl =""; Long id2=file.getRetailerId();
	 * try {
	 * 
	 * if(2==source && 1==newRetailer.getIsRecord()){ return
	 * "redirect:/user/checklist?source="+source; }else {
	 * if(null!=myfile&&!"".equals(Common.getFileExt2(myfile.getOriginalFilename
	 * ()))){ vatInvoicesUrl =
	 * UploadFileUtil.uploadFile(myfile.getInputStream(),
	 * Common.getFileExt2(myfile.getOriginalFilename()), null); }else
	 * if(null!=myfile3&&!"".equals(Common.getFileExt2(myfile3.
	 * getOriginalFilename()))){ effectiveCertificateUrl =
	 * UploadFileUtil.uploadFile(myfile3.getInputStream(),
	 * Common.getFileExt2(myfile3.getOriginalFilename()), null); }else{
	 * 
	 * return "redirect:/user/checklist?source="+source; } } } catch (Exception
	 * e) { LOGGER.error("注册：上传文件到图片服务器出错！",e); return
	 * "redirect:/user/viewInfo?source="+source+"&message=3&id2="+id2; }
	 * if(!Common.isEmpty(vatInvoicesUrl) ||
	 * !Common.isEmpty(effectiveCertificateUrl)){ int rs=0; if
	 * (!Common.isEmpty(vatInvoicesUrl)){
	 * file.setVatInvoicesUrl(vatInvoicesUrl); } if (
	 * !Common.isEmpty(effectiveCertificateUrl)){
	 * file.setEffectiveCertificateUrl(effectiveCertificateUrl); //改是否拥有一般纳税人资质
	 * Retailer retailer = new Retailer(); retailer.setIsTaxEffective(1);
	 * retailer.setRetailerId(file.getRetailerId());
	 * RemoteServiceSingleton.getInstance().getRetailerManagerService().
	 * updateRetailer(retailer);
	 * 
	 * } rs= RemoteServiceSingleton.getInstance().getRetailerManagerService().
	 * updateRetailerFileById(file); if(rs<=0){ return
	 * "redirect:/user/viewInfo?source="+source+"&message=4&id2="+id2; }else{
	 * return "redirect:/user/checklist?source="+source+"&message=ok"; } }else{
	 * return "redirect:/user/viewInfo?source="+source+"&message=2&id2="+id2; }
	 * }
	 */

	/*
	 * 调用订单接口时 获取地址的方法
	 */
	/*
	 * public String getTotalAddress(Retailer retailer){ String address =
	 * retailer.getAddress(); Long province= null; Long city= null; Long area=
	 * null; if(null!=retailer.getProvinceId()){ province =
	 * retailer.getProvinceId().longValue(); } if(null!=retailer.getCityId()){
	 * city = retailer.getCityId().longValue(); }
	 * if(null!=retailer.getAreaId()){ area = retailer.getAreaId().longValue();
	 * } BaseDataServiceRpc baseDataServiceRpc =
	 * RemoteServiceSingleton.getInstance().getBaseDataServiceRpc(); String
	 * provinceCityCountyName = ""; try { provinceCityCountyName =
	 * baseDataServiceRpc .getProvinceCityCountyName(province, city, area); }
	 * catch (Exception e) { logger.info("-------------"+e.getMessage(),e); }
	 * return provinceCityCountyName+address; }
	 */

	public static void main(String[] args) {
		EnumSet<SupplierSouce> list = EnumSet.allOf(SupplierSouce.class);
		System.out.println(list);

	}

	/**
	 * 审核商户.
	 * 
	 * @param map
	 *            Map<String,Object>
	 * @param source
	 *            int
	 * @param id2
	 *            long
	 * @param request
	 *            HttpServletRequest
	 * @return URL
	 */
	@RequestMapping("/user/viewInfo3")
	public String viewInfo3(Map<String, Object> map, int source, long id3, HttpServletRequest request) {

		try {
			if (source == Constants.INT1) {
				Supplier object = RemoteServiceSingleton.getInstance().getSupplierManagerService().findSupplier(id3);
				map.put("data", object);
				SupplierProduct product = RemoteServiceSingleton.getInstance().getSupplierManagerService()
						.getProductBySupplierId(id3);
				List<TdCatePub> childrenCategoryList = new ArrayList<TdCatePub>();
				if (RemoteServiceSingleton.getInstance().getCategoryServiceRpc() != null) {
					try {
						childrenCategoryList = RemoteServiceSingleton.getInstance().getCategoryServiceRpc()
								.getTopCategoryList();
					} catch (Exception e) {
						LOGGER.error("获取类目失败！");
					}
				}
				List<String> categoryNameCn = new ArrayList<String>();
				List<String> categoryNameEn = new ArrayList<String>();
				if (!childrenCategoryList.isEmpty()) {
					for (TdCatePub tdCatePub : childrenCategoryList) {
						// tdCatePub.
						categoryNameCn.add(tdCatePub.getPubNameCn());
						categoryNameEn.add(tdCatePub.getPubName());
					}
				}

				// 如果是pop类型，取得仓库的accountType
				if (object.getSupplyType() == SupplierSouces.POP.getIndex()) {
					Warehouse warehouse = RemoteServiceSingleton.getInstance().getStockWofeService()
							.findWarehouseByWarehouseCode(Integer.valueOf(object.getSupplierId().toString()));
					if (warehouse != null && warehouse.getAccountType() != null
							&& warehouse.getAccountType().intValue() > 0) {
						map.put("paymentType", warehouse.getAccountType());
					}
				}

				// 封装国家列表
				List<TcCountry> countries = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc()
						.getTcCountries();

				// 根据上级企业supplierId获取上级企业对象
				if ((!StringUtils.isEmpty(object.getSjSupplierId())) && !object.getSjSupplierId().equals("0")) {
					Supplier sjSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
							.findSupplier(Long.valueOf(object.getSjSupplierId()));
					map.put("sjSupplier", sjSupplier);
				}
				// 获取所有入驻区域类型
				List<SupplierRegionSettings> regionList = supplierRegionService.getAllRegionSettings();
				map.put("regionList", regionList);

				map.put("allList", EnumSet.allOf(SupplierSouce.class));
				map.put("category", JSONObject.toJSONString(categoryNameCn));
				map.put("product", product);
				map.put("country", countries);

				return "user/supplierInfo2";
			} /*
				 * else if(source==Constants.INT2){
				 * 
				 * Retailer retailer = RemoteServiceSingleton.getInstance().
				 * getRetailerManagerService().findByRetailerId(id3);
				 * 
				 * RetailerFile file = RemoteServiceSingleton.getInstance().
				 * getRetailerManagerService().getRetailerFileById(id3);
				 * 
				 * List<PlatformSale> allSale =
				 * RemoteServiceSingleton.getInstance().
				 * getPlatformSaleManagerService().getAllSale();
				 * 
				 * //省 Integer provinceId = retailer.getProvinceId(); String
				 * provincename = ""; if(null != provinceId){
				 * 
				 * provincename =
				 * RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().
				 * getProvinceById(provinceId).getProvincename(); }
				 * 
				 * 
				 * //市 Integer cityId = retailer.getCityId(); String city = "";
				 * 
				 * //获取市对应的区县信息 List<AgentCounty> counties = new
				 * ArrayList<AgentCounty>(); if(null != cityId){
				 * 
				 * city =
				 * RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().
				 * getCityById(cityId).getCityname();
				 * 
				 * counties =
				 * RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().
				 * getCountiesByCityId(cityId); }
				 * 
				 * //商户销售渠道 List<PlatformChannel> list =
				 * RemoteServiceSingleton.getInstance().getChannelManagerService
				 * ().getAllChannel();
				 * 
				 * map.put("list", list); map.put("allSale", allSale);
				 * map.put("counties", counties); map.put("provincename",
				 * provincename); map.put("city", city); map.put("data",
				 * retailer); map.put("dataFile", file);
				 * map.put("picUrl",Constants.FILESERVER1); // return
				 * "user/retailerInfo"; return "user/retailerInfoNew"; }else
				 * if(source==Constants.INT3){ map.put("data",
				 * RemoteServiceSingleton.getInstance().getDealerService().
				 * findByDealerId(id3)); DealerFile dealerFile =
				 * RemoteServiceSingleton.getInstance().getDealerFileService().
				 * getFileByDealerId(id3); map.put("dataFile", dealerFile);
				 * return "user/dealerInfo"; }
				 */

		} catch (Exception e) {
			LOGGER.error("审核商户. 异常  msg：" + e.getMessage());
			LOGGER.error("商户Id:" + getCurrentPlatformId());
			LOGGER.error("用户:" + getCurrentUser().getUsername());
			LOGGER.error("用户ID:" + getCurrentUser().getId());
		}
		return "redirect:/user/checklist?source=" + source;

	}

	@RequestMapping("/user/viewInfo8")
	public String viewInfo8(Map<String, Object> map, int source, long id3, HttpServletRequest request) {

		try {
			if (source == Constants.INT1) {
				Supplier object = RemoteServiceSingleton.getInstance().getSupplierManagerService().findSupplier(id3);
				map.put("data", object);
				SupplierProduct product = RemoteServiceSingleton.getInstance().getSupplierManagerService()
						.getProductBySupplierId(id3);
				List<TdCatePub> childrenCategoryList = new ArrayList<TdCatePub>();
				if (RemoteServiceSingleton.getInstance().getCategoryServiceRpc() != null) {
					try {
						childrenCategoryList = RemoteServiceSingleton.getInstance().getCategoryServiceRpc()
								.getTopCategoryList();
					} catch (Exception e) {
						LOGGER.error("获取类目失败！");
					}
				}
				List<String> categoryNameCn = new ArrayList<String>();
				List<String> categoryNameEn = new ArrayList<String>();
				if (!childrenCategoryList.isEmpty()) {
					for (TdCatePub tdCatePub : childrenCategoryList) {
						// tdCatePub.
						categoryNameCn.add(tdCatePub.getPubNameCn());
						categoryNameEn.add(tdCatePub.getPubName());
					}
				}

				// 如果是pop类型，取得仓库的accountType
				if (object.getSupplyType() == SupplierSouces.POP.getIndex()) {
					Warehouse warehouse = RemoteServiceSingleton.getInstance().getStockWofeService()
							.findWarehouseByWarehouseCode(Integer.valueOf(object.getSupplierId().toString()));
					if (warehouse != null && warehouse.getAccountType() != null
							&& warehouse.getAccountType().intValue() > 0) {
						map.put("paymentType", warehouse.getAccountType());
					}
				}

				// 封装国家列表
				List<TcCountry> countries = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc()
						.getTcCountries();

				// 根据上级企业supplierId获取上级企业对象
				if ((!StringUtils.isEmpty(object.getSjSupplierId())) && !object.getSjSupplierId().equals("0")) {
					Supplier sjSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
							.findSupplier(Long.valueOf(object.getSjSupplierId()));
					map.put("sjSupplier", sjSupplier);
				}
				// 获取所有入驻区域类型
				List<SupplierRegionSettings> regionList = supplierRegionService.getAllRegionSettings();
				map.put("regionList", regionList);

				map.put("allList", EnumSet.allOf(SupplierSouce.class));
				map.put("category", JSONObject.toJSONString(categoryNameCn));
				map.put("product", product);
				map.put("country", countries);

				return "user/supplierInfo66";
			} /*
				 * else if(source==Constants.INT2){
				 * 
				 * Retailer retailer = RemoteServiceSingleton.getInstance().
				 * getRetailerManagerService().findByRetailerId(id3);
				 * 
				 * RetailerFile file = RemoteServiceSingleton.getInstance().
				 * getRetailerManagerService().getRetailerFileById(id3);
				 * 
				 * List<PlatformSale> allSale =
				 * RemoteServiceSingleton.getInstance().
				 * getPlatformSaleManagerService().getAllSale();
				 * 
				 * //省 Integer provinceId = retailer.getProvinceId(); String
				 * provincename = ""; if(null != provinceId){
				 * 
				 * provincename =
				 * RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().
				 * getProvinceById(provinceId).getProvincename(); }
				 * 
				 * 
				 * //市 Integer cityId = retailer.getCityId(); String city = "";
				 * 
				 * //获取市对应的区县信息 List<AgentCounty> counties = new
				 * ArrayList<AgentCounty>(); if(null != cityId){
				 * 
				 * city =
				 * RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().
				 * getCityById(cityId).getCityname();
				 * 
				 * counties =
				 * RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().
				 * getCountiesByCityId(cityId); }
				 * 
				 * //商户销售渠道 List<PlatformChannel> list =
				 * RemoteServiceSingleton.getInstance().getChannelManagerService
				 * ().getAllChannel();
				 * 
				 * map.put("list", list); map.put("allSale", allSale);
				 * map.put("counties", counties); map.put("provincename",
				 * provincename); map.put("city", city); map.put("data",
				 * retailer); map.put("dataFile", file);
				 * map.put("picUrl",Constants.FILESERVER1); // return
				 * "user/retailerInfo"; return "user/retailerInfoNew"; }else
				 * if(source==Constants.INT3){ map.put("data",
				 * RemoteServiceSingleton.getInstance().getDealerService().
				 * findByDealerId(id3)); DealerFile dealerFile =
				 * RemoteServiceSingleton.getInstance().getDealerFileService().
				 * getFileByDealerId(id3); map.put("dataFile", dealerFile);
				 * return "user/dealerInfo"; }
				 */

		} catch (Exception e) {
			LOGGER.error("审核商户. 异常  msg：" + e.getMessage());
			LOGGER.error("商户Id:" + getCurrentPlatformId());
			LOGGER.error("用户:" + getCurrentUser().getUsername());
			LOGGER.error("用户ID:" + getCurrentUser().getId());
		}
		return "redirect:/user/checklist?source=" + source;

	}

	@RequestMapping("/user/viewInfonew8")
	public String viewInfonew8(Map<String, Object> map, int source, long id3, HttpServletRequest request) {

		try {
			if (source == Constants.INT1) {
				Supplier object = RemoteServiceSingleton.getInstance().getSupplierManagerService().findSupplier(id3);
				map.put("data", object);
				SupplierProduct product = RemoteServiceSingleton.getInstance().getSupplierManagerService()
						.getProductBySupplierId(id3);
				List<TdCatePub> childrenCategoryList = new ArrayList<TdCatePub>();
				if (RemoteServiceSingleton.getInstance().getCategoryServiceRpc() != null) {
					try {
						childrenCategoryList = RemoteServiceSingleton.getInstance().getCategoryServiceRpc()
								.getTopCategoryList();
					} catch (Exception e) {
						LOGGER.error("获取类目失败！");
					}
				}
				List<String> categoryNameCn = new ArrayList<String>();
				List<String> categoryNameEn = new ArrayList<String>();
				if (!childrenCategoryList.isEmpty()) {
					for (TdCatePub tdCatePub : childrenCategoryList) {
						// tdCatePub.
						categoryNameCn.add(tdCatePub.getPubNameCn());
						categoryNameEn.add(tdCatePub.getPubName());
					}
				}

				// 如果是pop类型，取得仓库的accountType
				if (object.getSupplyType() == SupplierSouces.POP.getIndex()) {
					Warehouse warehouse = RemoteServiceSingleton.getInstance().getStockWofeService()
							.findWarehouseByWarehouseCode(Integer.valueOf(object.getSupplierId().toString()));
					if (warehouse != null && warehouse.getAccountType() != null
							&& warehouse.getAccountType().intValue() > 0) {
						map.put("paymentType", warehouse.getAccountType());
					}
				}

				// 封装国家列表
				List<TcCountry> countries = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc()
						.getTcCountries();

				// 根据上级企业supplierId获取上级企业对象
				if ((!StringUtils.isEmpty(object.getSjSupplierId())) && !object.getSjSupplierId().equals("0")) {
					Supplier sjSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
							.findSupplier(Long.valueOf(object.getSjSupplierId()));
					map.put("sjSupplier", sjSupplier);
				}
				// 获取所有入驻区域类型
				List<SupplierRegionSettings> regionList = supplierRegionService.getAllRegionSettings();
				map.put("regionList", regionList);

				map.put("allList", EnumSet.allOf(SupplierSouce.class));
				map.put("category", JSONObject.toJSONString(categoryNameCn));
				map.put("product", product);
				map.put("country", countries);

				return "user/supplierInfo5";
			} /*
				 * else if(source==Constants.INT2){
				 * 
				 * Retailer retailer = RemoteServiceSingleton.getInstance().
				 * getRetailerManagerService().findByRetailerId(id3);
				 * 
				 * RetailerFile file = RemoteServiceSingleton.getInstance().
				 * getRetailerManagerService().getRetailerFileById(id3);
				 * 
				 * List<PlatformSale> allSale =
				 * RemoteServiceSingleton.getInstance().
				 * getPlatformSaleManagerService().getAllSale();
				 * 
				 * //省 Integer provinceId = retailer.getProvinceId(); String
				 * provincename = ""; if(null != provinceId){
				 * 
				 * provincename =
				 * RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().
				 * getProvinceById(provinceId).getProvincename(); }
				 * 
				 * 
				 * //市 Integer cityId = retailer.getCityId(); String city = "";
				 * 
				 * //获取市对应的区县信息 List<AgentCounty> counties = new
				 * ArrayList<AgentCounty>(); if(null != cityId){
				 * 
				 * city =
				 * RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().
				 * getCityById(cityId).getCityname();
				 * 
				 * counties =
				 * RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().
				 * getCountiesByCityId(cityId); }
				 * 
				 * //商户销售渠道 List<PlatformChannel> list =
				 * RemoteServiceSingleton.getInstance().getChannelManagerService
				 * ().getAllChannel();
				 * 
				 * map.put("list", list); map.put("allSale", allSale);
				 * map.put("counties", counties); map.put("provincename",
				 * provincename); map.put("city", city); map.put("data",
				 * retailer); map.put("dataFile", file);
				 * map.put("picUrl",Constants.FILESERVER1); // return
				 * "user/retailerInfo"; return "user/retailerInfoNew"; }else
				 * if(source==Constants.INT3){ map.put("data",
				 * RemoteServiceSingleton.getInstance().getDealerService().
				 * findByDealerId(id3)); DealerFile dealerFile =
				 * RemoteServiceSingleton.getInstance().getDealerFileService().
				 * getFileByDealerId(id3); map.put("dataFile", dealerFile);
				 * return "user/dealerInfo"; }
				 */

		} catch (Exception e) {
			LOGGER.error("审核商户. 异常  msg：" + e.getMessage());
			LOGGER.error("商户Id:" + getCurrentPlatformId());
			LOGGER.error("用户:" + getCurrentUser().getUsername());
			LOGGER.error("用户ID:" + getCurrentUser().getId());
		}
		return "redirect:/user/checklist?source=" + source;

	}

	/**
	 * 审核修改商户.
	 * 
	 * @param source
	 *            int
	 * @param id1
	 *            long
	 * @param status1
	 *            int
	 * @param request
	 *            HttpServletRequest
	 * @return URL
	 */
	@RequestMapping("/user/updateCheckModify")
	@ResponseBody
	public String updateCheckModify(long id1, int status1, int isRecord, String reason, int supplyType,
			String productId, HttpServletRequest request) {

		LOGGER.info(request.getParameter("id1") + "--" + "" + request.getParameter("status1") + "--"
				+ request.getParameter("source") + "------------" + reason);

		String resaultJsonString = Constants.SUCCESS;

		try {
			// 供应商

			Supplier supplier = new Supplier();
			Supplier findSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService().findSupplier(id1);
			supplier.setSupplierId(id1);

			SupplierUser user = RemoteServiceSingleton.getInstance().getSupplierManagerService()
					.findAdminUserByMerchantId(id1);
			if (user == null) {
				resaultJsonString = Constants.SAMEISNULL;
				return resaultJsonString;
			}

			String subject = "zhongjumall商户审核结果";
			if (!Common.isEmpty(reason) && status1 != Constants.INT1) {// 审核不通过
				supplier.setCheckFailReason(reason);
				supplier.setStatus(2); // 未通过
				StringBuffer content = new StringBuffer();
				content.append("尊贵的用户：<br> 您好！<br>");
				content.append("您 " + Common.dateToString(supplier.getCreateTime(), "yyyy年MM月dd日")
						+ "在众聚商城供应商基本信息修改时,但未通过审核。<br>");
				content.append("未通过原因: " + reason + "<br>");
				content.append("本邮件由众聚商城系统自动发出，请勿直接回复。<br>");
				content.append("在操作过程中如遇到任何疑问，请发送邮件至2605803377@qq.com，我们会及时为您解答。<br>");
				content.append("谢谢！");
				String email = findSupplier.getEmail();
				if (email != null) {
					SendHtmlMail.send(email, subject, content.toString());
				}
			} else {// 审核通过

				String email = findSupplier.getEmail();
				StringBuffer content = new StringBuffer();
				content.append("尊贵的用户：<br> 您好！<br>");
				content.append("    您 " + Common.dateToString(supplier.getCreateTime(), "yyyy年MM月dd日")
						+ "在众聚商城供应商注册页面（http://pop.zhongjumall.com/supplier/registUI)成功注册并通过审核。注册信息如下：<br>");
				content.append("用户名: " + user.getLoginName() + " 邮箱: " + findSupplier.getEmail() + "<br>");
				content.append("为保障您的账号安全，请勿将用户名和密码轻易透露给任何人。 <br>");
				content.append("本邮件由众聚商城系统自动发出，请勿直接回复。<br>");
				content.append("在操作过程中如遇到任何疑问，请发送邮件至2605803377@qq.com，我们会及时为您解答。<br>");
				content.append("谢谢！");
				if (email != null) {
					SendHtmlMail.send(email, subject, content.toString());
				}
				supplier.setModifyStatus(0); // 修改审核通过后置为0 原始状态没有修改
				supplier.setStatus(1); // 可以控制审核通过
				supplier.setActiveStatus(1); // 激活
				supplier.setCheckFailReason(reason);

				// TODO 调取激活企业个人账号
				int activeStatus = RemoteServiceSingleton.getInstance().getSqwAccountRecordService()
						.activeUserOfQiyeAccount(id1);
				logger.info("激活个人帐号结果：result=" + activeStatus);
				// 推荐企业一次性返利
				Long qyId = id1; // 企业id
				Long p_userId = null; // 直接推荐人id
				Long pp_userId = null; // 间接推荐人id
				if (findSupplier != null && !StringUtils.isEmpty(findSupplier.getUserTj())) { // 直接推荐人
					com.mall.customer.model.User tjUser = RemoteServiceSingleton.getInstance().getUserService()
							.findUserByMobile(findSupplier.getUserTj());
					if (tjUser != null) {
						p_userId = tjUser.getUserId();
						if (!StringUtils.isEmpty(tjUser.getTjMobile())) { // 间接推荐人
							com.mall.customer.model.User jianyeTjUser = RemoteServiceSingleton.getInstance()
									.getUserService().findUserByMobile(tjUser.getTjMobile());
							if (jianyeTjUser != null) {
								pp_userId = jianyeTjUser.getUserId();
							}
						}
					}
				}
				BigDecimal price = null; // 返利总额
				if (findSupplier != null) {
					if (findSupplier.getNoPayhqq() != null) {
						price = findSupplier.getNoPayhqq();
					} else if (findSupplier.getFanliPrice() != null) {
						price = findSupplier.getFanliPrice();
					}
				}
				String trancNo = System.currentTimeMillis() + ""; // 交易号
				int fanLiResult = RemoteServiceSingleton.getInstance().getSqwAccountRecordService()
						.tuijianQiyeFanli(qyId, p_userId, pp_userId, price, trancNo);
				logger.info("推荐返利结果：result=" + fanLiResult);

				if (!StringUtils.isEmpty(findSupplier.getManagerBackup())
						&& !(findSupplier.getManager().equals(findSupplier.getManagerBackup()))) {
					supplier.setManager(findSupplier.getManagerBackup());
				}
				if (!StringUtils.isEmpty(findSupplier.getManagerTelBackup())
						&& !(findSupplier.getManagerTel().equals(findSupplier.getManagerTelBackup()))) {
					supplier.setManagerTel(findSupplier.getManagerTelBackup());
				}
				if (!StringUtils.isEmpty(findSupplier.getKfTelBackup())
						&& !(findSupplier.getKfTel().equals(findSupplier.getKfTelBackup()))) {
					supplier.setKfTel(findSupplier.getKfTelBackup());
				}
				if (!StringUtils.isEmpty(findSupplier.getAddressBackup())
						&& !(findSupplier.getAddress().equals(findSupplier.getAddressBackup()))) {
					supplier.setAddress(findSupplier.getAddressBackup());
				}
				if (!StringUtils.isEmpty(findSupplier.getContactBackup())
						&& !(findSupplier.getContact().equals(findSupplier.getContactBackup()))) {
					supplier.setContact(findSupplier.getContactBackup());
				}
				if (!StringUtils.isEmpty(findSupplier.getPhoneBackup())
						&& !(findSupplier.getPhone().equals(findSupplier.getPhoneBackup()))) {
					supplier.setPhone(findSupplier.getPhoneBackup());
				}
				if (!StringUtils.isEmpty(findSupplier.getEmailBackup())
						&& !(findSupplier.getEmail().equals(findSupplier.getEmailBackup()))) {
					supplier.setEmail(findSupplier.getEmailBackup());
				}
				if (!StringUtils.isEmpty(findSupplier.getFaxBackup())
						&& !(findSupplier.getFax().equals(findSupplier.getFaxBackup()))) {
					supplier.setFax(findSupplier.getFaxBackup());
				}
				if (!StringUtils.isEmpty(findSupplier.getCountryAreaBackup())
						&& !(findSupplier.getCountryArea().equals(findSupplier.getCountryAreaBackup()))) {
					supplier.setCountryArea(findSupplier.getCountryAreaBackup());
				}
				if (!StringUtils.isEmpty(findSupplier.getPostBackup())
						&& !((String.valueOf(findSupplier.getPost()).equals(findSupplier.getPostBackup())))) {
					supplier.setPost(Integer.valueOf(findSupplier.getPostBackup()));
				}
				if (!StringUtils.isEmpty(findSupplier.getLogoImgurlBackup())
						&& !(findSupplier.getLogoImgurl().equals(findSupplier.getLogoImgurlBackup()))) {
					supplier.setLogoImgurl(findSupplier.getLogoImgurlBackup());
				}
				SupplierProduct product = RemoteServiceSingleton.getInstance().getSupplierManagerService()
						.getProductBySupplierId(id1);

				SupplierProduct newProduct = new SupplierProduct();
				if (product != null) {
					if (!StringUtils.isEmpty(product.getCategoriesBackup())
							&& !product.getCategories().equals(product.getCategoriesBackup())) {
						newProduct.setCategories(product.getCategoriesBackup());
					}
					if (!StringUtils.isEmpty(product.getBrandBackup())
							&& !product.getBrand().equals(product.getBrandBackup())) {
						newProduct.setBrand(product.getBrandBackup());
					}
					newProduct.setModifyStatus(0);
					newProduct.setSupplierId(Long.valueOf(id1));
					RemoteServiceSingleton.getInstance().getSupplierManagerService()
							.updateProductBackupInfo(newProduct);
				}
			}
			supplier.setSupplyType(supplyType);
			supplier.setIsOnekeySend(1);
			supplier.setAuditTime(new Date());
			RemoteServiceSingleton.getInstance().getSupplierManagerService().updateModifyInfo(supplier);

		} catch (Exception e) {

			resaultJsonString = Constants.ERROR;
			LOGGER.error("审核商户. 异常  msg：" + e.getMessage(), e);
			LOGGER.error("商户Id:" + getCurrentPlatformId());
			LOGGER.error("用户:" + getCurrentUser().getUsername());
			LOGGER.error("用户ID:" + getCurrentUser().getId());
		}
		return resaultJsonString;

	}

	/**
	 * <p>
	 * 修改等级或区域
	 * </p>
	 * 
	 * @param supplierId
	 * @param newLevel
	 * @param newCompanyQy
	 * @return
	 * @auth:zw
	 */
	@RequestMapping("/user/modifyInfo")
	@ResponseBody
	public String modifyInfo(String supplierId, String newLevelVal, String newCompanyQy) {
		int flag = 1;
		Supplier supplier = new Supplier();
		supplier.setSupplierId(Long.valueOf(supplierId));
		supplier.setIsOnekeySend(1);
		supplier.setSupplyType(51);
		if (!StringUtils.isEmpty(newLevelVal)) {
			supplier.setCompanyLevel(newLevelVal);
		}
		if (!StringUtils.isEmpty(newCompanyQy)) {
			supplier.setCompanyQy(newCompanyQy);
		}
		try {
			RemoteServiceSingleton.getInstance().getSupplierManagerService().updateModifyInfo(supplier);
		} catch (Exception e) {
			flag = 0;
			e.printStackTrace();
		}

		return JSON.toJSONString(flag);
	}

	/**
	 * <p>
	 * 创建企业账户和个人账户
	 * </p>
	 * 
	 * @param supplierId
	 * @return
	 * @auth:zw
	 */
	@RequestMapping("/user/f")
	@ResponseBody
	public String createAccount(String paymentDays, String isBillPayment, int source, long id1, int status1,
			int isRecord, String reason, int supplyType, HttpServletRequest request, String companyQy,
			String companyRegion, int type, String noPayHqq, String sjSupplierCode, String fanliPrice) {

		// 修改企业账号为可登陆状态，但不能发布商品

		String resaultJsonString = Constants.SUCCESS;

		String isOnekeySend = request.getParameter("isOnekeySend");
		if (org.apache.commons.lang3.StringUtils.isBlank(isOnekeySend)) {
			isOnekeySend = "1";
		}
		// 加入pop的支付方式选择
		String paymentType = request.getParameter("paymentType");
		// 3 走国内支付 目前只有支付宝国内支付
		paymentType = "3";
		if (org.apache.commons.lang3.StringUtils.isBlank(paymentType)) {
			// paymentType = "1";
			paymentType = "3";
		}
		if (null == request.getParameter("id1") || null == request.getParameter("status1")
				|| null == request.getParameter("source")) {
			resaultJsonString = Constants.SAMEISNULL;

			return resaultJsonString;
		}
		String name = ""; // 企业用户名
		String userName = "";
		String userMobile = "";
		String userPassword = "";
		String qiyeSmallTjMobile = ""; // 企业小号邀请码
		try {

			if (source == Constants.INT1) {// 供应商

				Supplier supplier = new Supplier();
				Supplier findSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
						.findSupplier(id1);
				supplier.setSupplierId(id1);
				supplier.setStatus(status1);
				// supplier.setSupplyType(supplyType);

				userName = findSupplier.getUserName();
				userMobile = findSupplier.getUserMobile();
				// qiyeSmallTjMobile = findSupplier.getUserTj();

				SupplierUser user = RemoteServiceSingleton.getInstance().getSupplierManagerService()
						.findAdminUserByMerchantId(id1);

				// pop商家，如果审核成功后，如果没有店铺信息，需要创建一条
				if (supplyType == SupplierSouces.POP.getIndex()) {
					SupplierStore store = supplierStoreService.findSupplierStoreBySupplierId((int) id1);
					if (store == null) {
						SupplierStore temp = new SupplierStore();
						temp.setSupplierId((int) id1);
						temp.setStoreName(findSupplier.getName());
						supplierStoreService.insertStore(temp);
					}

					// 如果是pop商家，创建一个仓库

					// 仓库名称：第三方国际仓库-商家名称
					// 经销商名称：众聚商城
					// 地址：默认北京朝阳
					if (null == RemoteServiceSingleton.getInstance().getStockWofeService()
							.findWarehouseByWarehouseCode(Integer.valueOf(findSupplier.getSupplierId().toString()))
							.getWarehouseCode()) {
						Warehouse warehouse = new Warehouse();
						warehouse.setWarehouseCode(Integer.valueOf(findSupplier.getSupplierId().toString()));
						warehouse.setWarehouseType(Short.parseShort(7 + ""));
						warehouse.setWarehouseLevelCode(Short.parseShort(1 + ""));
						warehouse.setWarehouseLevelName("一级库房");
						warehouse.setWarehouseStatus(Short.parseShort(0 + ""));
						warehouse.setWarehouseName("第三方国际仓库" + findSupplier.getName());
						warehouse.setWarehouseTypeName("第三方国际商品仓库");
						warehouse.setDealerId(7735L);// 北京世纪新干线网络技术有限公司
						warehouse.setProvinceId(0l);
						warehouse.setCityId(0l);
						warehouse.setAreaId(0l);
						warehouse.setAddress("北京 北京市 朝阳区 霄云中心B座");
						warehouse.setContact("POP");
						warehouse.setTelephone(
								findSupplier.getPhone() != null ? Long.parseLong(findSupplier.getPhone()) : 0L);
						warehouse.setChannelCode(1l);
						warehouse.setChannelName("海外直邮");
						warehouse.setAccountType(Integer.parseInt(paymentType));
						RemoteServiceSingleton.getInstance().getStockWofeService().insertWarehouse(warehouse);

						logger.info("insert pop warehouse. supplier:" + warehouse.getWarehouseCode());
					}
				}
				if (user == null) {

					resaultJsonString = Constants.SAMEISNULL;

					return resaultJsonString;

				}
				name = user.getLoginName();
				userPassword = user.getRecordPwd();
				SupplierUser supplierUser = new SupplierUser();
				supplierUser.setSupplierId(id1);
				supplierUser.setStatus(status1);

				supplier.setIsOnekeySend(Integer.parseInt(isOnekeySend));
				supplier.setSupplyType(supplyType);
				supplier.setAuditTime(new Date());

				supplier.setCompanyRegion(companyRegion);
				supplier.setCompanyQy(companyQy);
				supplier.setType(type);
				String supplierCodeType = "";
				int newSupplierCodeNum;

				// 0" 非自营企业 QD
				// 1" 子公司 QA
				// 2" 连锁企业 QE
				// 3" 连锁子公司 QB
				// 4" 自营企业 QC
				// 5" 项目产业 QF

				if (type == 0) { // 属于企业公司
					supplierCodeType = "QD";
				} else if (type == 1) { // 属于子公司
					supplierCodeType = "QA";
				} else if (type == 2) { // 属于连锁公司
					supplierCodeType = "QE";
				} else if (type == 3) {
					supplierCodeType = "QB";
				} else if (type == 4) {
					supplierCodeType = "QC";
				} else if (type == 5) {
					supplierCodeType = "QF";
				} else if (type == 11) { // 平台运营商家
					supplierCodeType = "QZ";
				} else if (type == 12) { // 普通商家
					supplierCodeType = "QX";
				} else if (type == 13) { // 基地商家
					supplierCodeType = "QY";
				} else if (type == 14) { // 合作社商家
					supplierCodeType = "QM";
				} else if (type == 15) { // 区域运营商家
					supplierCodeType = "QN";
				} else if (type == 16) { // 区域代理商家
					supplierCodeType = "QL";
				}
				int supplierCodeNum = RemoteServiceSingleton.getInstance().getSupplierManagerService()
						.getSupplierCodeNumByType(supplierCodeType);

				boolean supplierCodeFlag = true;
				while (supplierCodeFlag) {
					// 判断是否已经存在企业code
					String numCode = "";
					if (type == 2) { // 属于企业公司
						supplierCodeType = "QE";
						if (supplierCodeNum < 10) {
							numCode = "000" + supplierCodeNum;
						} else if (supplierCodeNum >= 10 && supplierCodeNum < 100) {
							numCode = "00" + supplierCodeNum;
						} else if (supplierCodeNum >= 100 && supplierCodeNum < 1000) {
							numCode = "0" + supplierCodeNum;
						} else {
							numCode = supplierCodeNum + "";
						}
					} else {
						if (supplierCodeNum < 10) {
							numCode = "00" + supplierCodeNum;
						} else if (supplierCodeNum >= 10 && supplierCodeNum < 100) {
							numCode = "0" + supplierCodeNum;
						} else {
							numCode = supplierCodeNum + "";
						}
					}
					String isExist = RemoteServiceSingleton.getInstance().getSupplierManagerService()
							.checkSupplierCodeIsExists(supplierCodeType + numCode);
					if (isExist.equals("0")) { // 不存在
						supplierCodeFlag = false;
					} else {
						supplierCodeNum = supplierCodeNum + 1;
					}
				}

				newSupplierCodeNum = supplierCodeNum + 1;
				SupplierCodeType newSupplierCodeType = new SupplierCodeType();
				newSupplierCodeType.setSupplierCodeNum(newSupplierCodeNum);
				newSupplierCodeType.setSupplierCodeType(supplierCodeType);
				try {
					RemoteServiceSingleton.getInstance().getSupplierManagerService()
							.updateSupplierCodeNumByType(newSupplierCodeType);
				} catch (Exception e) {
					LOGGER.error("修改企业编码数量supplierCodeType = " + supplierCodeType, e);
					return Constants.ERROR;

				}

				/*
				 * int supplierCodeNum = RemoteServiceSingleton.getInstance().
				 * getSupplierManagerService().getSupplierCodeNumByType(
				 * supplierCodeType); newSupplierCodeNum = supplierCodeNum+1;
				 * SupplierCodeType newSupplierCodeType = new
				 * SupplierCodeType();
				 * newSupplierCodeType.setSupplierCodeNum(newSupplierCodeNum);
				 * newSupplierCodeType.setSupplierCodeType(supplierCodeType);
				 * try { RemoteServiceSingleton.getInstance().
				 * getSupplierManagerService().updateSupplierCodeNumByType(
				 * newSupplierCodeType); } catch (Exception e) { LOGGER.error(
				 * "修改企业编码数量supplierCodeType = "+supplierCodeType,e); }
				 */
				String newNum = "";
				if (type == 2) { // 属于企业公司
					supplierCodeType = "QE";
					if (supplierCodeNum < 10) {
						newNum = "000" + supplierCodeNum;
					} else if (supplierCodeNum >= 10 && supplierCodeNum < 100) {
						newNum = "00" + supplierCodeNum;
					} else if (supplierCodeNum >= 100 && supplierCodeNum < 1000) {
						newNum = "0" + supplierCodeNum;
					} else {
						newNum = supplierCodeNum + "";
					}
				} else {
					if (supplierCodeNum < 10) {
						newNum = "00" + supplierCodeNum;
					} else if (supplierCodeNum >= 10 && supplierCodeNum < 100) {
						newNum = "0" + supplierCodeNum;
					} else {
						newNum = supplierCodeNum + "";
					}
				}
				supplier.setSupplierCode(supplierCodeType + newNum);
				supplier.setActiveStatus(-1); // -1为未激活状态
				if (!StringUtils.isEmpty(noPayHqq)) {
					supplier.setNoPayhqq(new BigDecimal(noPayHqq));
					supplier.setRemainBalanceAmount(new BigDecimal(noPayHqq));
				}
				if (!StringUtils.isEmpty(fanliPrice)) {
					supplier.setFanliPrice(new BigDecimal(fanliPrice));
				}
				if (!StringUtils.isEmpty(sjSupplierCode)) {
					Supplier modifySjSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
							.getSupplierBySupplierCode(sjSupplierCode);
					if (modifySjSupplier != null) {
						supplier.setSjSupplierId(modifySjSupplier.getSupplierId() + "");
						com.mall.customer.model.User sjUser = RemoteServiceSingleton.getInstance().getUserService()
								.findUserBySupplierId(modifySjSupplier.getSupplierId());
						if (sjUser != null && !StringUtils.isEmpty(sjUser.getMobile())) { // 企业小号推荐手机号变为上级企业的企业小号的手机号
							qiyeSmallTjMobile = sjUser.getMobile();
						}
					}
				}
				RemoteServiceSingleton.getInstance().getSupplierManagerService().checkMerchant(supplier, supplierUser);

			}
		} catch (Exception e) {

			resaultJsonString = Constants.ERROR;

			LOGGER.error("审核商户. 异常  msg：" + e.getMessage(), e);
			LOGGER.error("商户Id:" + getCurrentPlatformId());
			LOGGER.error("用户:" + getCurrentUser().getUsername());
			LOGGER.error("用户ID:" + getCurrentUser().getId());
			return resaultJsonString;

		}

		// 创建企业个人账号
		String supplierCodeType = "";
		// int newUserIdNum;
		int userIdNum;
		if (type == 0) { // 属于企业公司
			supplierCodeType = "QD";
		} else if (type == 1) { // 属于子公司
			supplierCodeType = "QA";
		} else if (type == 2) { // 属于连锁公司
			supplierCodeType = "QE";
		} else if (type == 3) {
			supplierCodeType = "QB";
		} else if (type == 4) {
			supplierCodeType = "QC";
		} else if (type == 5) {
			supplierCodeType = "QF";
		} else if (type == 11) { // 平台运营商家
			supplierCodeType = "QE";
		} else if (type == 12) { // 普通商家
			supplierCodeType = "QE";
		} else if (type == 13) { // 基地商家
			supplierCodeType = "QE";
		} else if (type == 14) { // 合作社商家
			supplierCodeType = "QE";
		} else if (type == 15) { // 区域运营商家
			supplierCodeType = "QE";
		} else if (type == 16) { // 区域代理商家
			supplierCodeType = "QE";
		}
		userIdNum = RemoteServiceSingleton.getInstance().getUserService()
				.getStartUserIdBySupplierCode(supplierCodeType);
		int saveUserFlag = 0;
		// com.mall.customer.model.User u = null;
		// boolean existUser = true;
		int bigNum = 0;
		if (type == 0) { // 属于企业公司
			bigNum = 5000;
			saveUserFlag = getStartUserIdByType(userIdNum, bigNum, supplierCodeType);
		} else if (type == 1) { // 属于子公司
			bigNum = 1000;
			saveUserFlag = getStartUserIdByType(userIdNum, bigNum, supplierCodeType);
		} else if (type == 2 || type == 11 || type == 12 || type == 13 || type == 14 || type == 15 || type == 16) { // 属于连锁公司
			bigNum = 10000;
			saveUserFlag = getStartUserIdByType(userIdNum, bigNum, supplierCodeType);
		} else if (type == 3) {
			bigNum = 2000;
			saveUserFlag = getStartUserIdByType(userIdNum, bigNum, supplierCodeType);
		} else if (type == 4) {
			bigNum = 1500;
			saveUserFlag = getStartUserIdByType(userIdNum, bigNum, supplierCodeType);
		} else if (type == 5) {
			bigNum = 2500;
			saveUserFlag = getStartUserIdByType(userIdNum, bigNum, supplierCodeType);
		}
		String username = "";
		if (saveUserFlag != 0) {
			userIdNum = saveUserFlag;
			com.mall.customer.model.User newUser = new com.mall.customer.model.User();
			newUser.setUserId(Long.valueOf(userIdNum + ""));
			username = ProduceUserName.produce(userMobile);
			newUser.setMobile(userMobile);
			newUser.setPassword(String.valueOf(userPassword));

			newUser.setUserName(username);
			newUser.setRealName(userName);
			newUser.setNickName(username);
			newUser.setSupplierFlag("1");
			newUser.setSupplierId(id1 + "");
			newUser.setTjMobile(qiyeSmallTjMobile);
			int result = 0;
			try {
				result = RemoteServiceSingleton.getInstance().getUserService().saveUser(newUser);
				logger.info("result :" + result);
			} catch (Exception e) {
				logger.error("Exception,userService.saveUser has error,message:", e);
				e.printStackTrace();
				return Constants.ERROR;
			}
			// 创建账号成功发送短信
			if (result > 0) {
				String sendResult = SendSMSUtil.sendCreateAccountMessage(userMobile, String.valueOf(userPassword), name,
						username);
				logger.info("send sms end.result:" + sendResult);
			}
		} else {
			return "-2";
		}
		return Constants.SUCCESS;
	}

	@RequestMapping("/user/createAccountLine")
	@ResponseBody
	public String createAccountLine(String paymentDays, String isBillPayment, int source, int id1, int status1,
			int isRecord, String reason, int supplyType, HttpServletRequest request, String companyQy,
			String companyRegion, int type,String noPayHqq, String sjSupplierCode,String fanliPrice) {
		// 修改企业账号为可登陆状态，但不能发布商品

				String resaultJsonString = Constants.SUCCESS;

				String isOnekeySend = request.getParameter("isOnekeySend");
				if (org.apache.commons.lang3.StringUtils.isBlank(isOnekeySend)) {
					isOnekeySend = "1";
				}
				// 加入pop的支付方式选择
				String paymentType = request.getParameter("paymentType");
				// 3 走国内支付 目前只有支付宝国内支付
				paymentType = "3";
				if (null == request.getParameter("id1") || null == request.getParameter("status1")
						|| null == request.getParameter("source")) {
					resaultJsonString = Constants.SAMEISNULL;
					return resaultJsonString;
				}
				
				String name = ""; // 企业用户名
				String userName = "";
				String userMobile = "";
				String userPassword = "";
				String qiyeSmallTjMobile = ""; // 企业小号邀请码
				try {

					if (source == Constants.INT1) {

						Supplier supplier = new Supplier();
						Supplier findSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
								.findSupplier(Long.valueOf(id1));
						supplier.setSupplierId(Long.valueOf(id1));
						supplier.setStatus(status1);
						// supplier.setSupplyType(supplyType);

						userName = findSupplier.getUserName();
						userMobile = findSupplier.getUserMobile();
						// qiyeSmallTjMobile = findSupplier.getUserTj();

						SupplierUser user = RemoteServiceSingleton.getInstance().getSupplierManagerService()
								.findAdminUserByMerchantId(Long.valueOf(id1));

						// pop商家，如果审核成功后，如果没有店铺信息，需要创建一条
						if (supplyType == SupplierSouces.POP.getIndex()) {
							SupplierStore store = supplierStoreService.findSupplierStoreBySupplierId((int) id1);
							if (store == null) {
								SupplierStore temp = new SupplierStore();
								temp.setSupplierId((int) id1);
								temp.setStoreName(findSupplier.getName());
								supplierStoreService.insertStore(temp);
							}

							// 如果是pop商家，创建一个仓库
							// 仓库名称：第三方国际仓库-商家名称
							// 经销商名称：众聚商城
							// 地址：默认北京朝阳
							if (null == RemoteServiceSingleton.getInstance().getStockWofeService()
									.findWarehouseByWarehouseCode(Integer.valueOf(findSupplier.getSupplierId().toString()))
									.getWarehouseCode()) {
								Warehouse warehouse = new Warehouse();
								warehouse.setWarehouseCode(Integer.valueOf(findSupplier.getSupplierId().toString()));
								warehouse.setWarehouseType(Short.parseShort(7 + ""));
								warehouse.setWarehouseLevelCode(Short.parseShort(1 + ""));
								warehouse.setWarehouseLevelName("一级库房");
								warehouse.setWarehouseStatus(Short.parseShort(0 + ""));
								warehouse.setWarehouseName("第三方国际仓库" + findSupplier.getName());
								warehouse.setWarehouseTypeName("第三方国际商品仓库");
								warehouse.setDealerId(7735L);// 北京世纪新干线网络技术有限公司
								warehouse.setProvinceId(0l);
								warehouse.setCityId(0l);
								warehouse.setAreaId(0l);
								warehouse.setAddress("北京 北京市 朝阳区 霄云中心B座");
								warehouse.setContact("POP");
								warehouse.setTelephone(
										findSupplier.getPhone() != null ? Long.parseLong(findSupplier.getPhone()) : 0L);
								warehouse.setChannelCode(1l);
								warehouse.setChannelName("海外直邮");
								warehouse.setAccountType(Integer.parseInt(paymentType));
								RemoteServiceSingleton.getInstance().getStockWofeService().insertWarehouse(warehouse);
								logger.info("insert pop warehouse. supplier:" + warehouse.getWarehouseCode());
							}
						}
						if (user == null) {
							resaultJsonString = Constants.SAMEISNULL;
							return resaultJsonString;

						}
						name = user.getLoginName();
						userPassword = user.getRecordPwd();
						SupplierUser supplierUser = new SupplierUser();
						supplierUser.setSupplierId(Long.valueOf(id1));
						supplierUser.setStatus(status1);

						supplier.setIsOnekeySend(Integer.parseInt(isOnekeySend));
						supplier.setSupplyType(supplyType);
						supplier.setAuditTime(new Date());

						supplier.setCompanyRegion(companyRegion);
						supplier.setCompanyQy(companyQy);
						supplier.setType(type);

						int supplierCodeNum = id1;
						String supplierCodeType = "";
						if (type < 6) {
							// 0" 非自营企业 QD
							// 1" 子公司 QA
							// 2" 连锁企业 QE
							// 3" 连锁子公司 QB
							// 4" 自营企业 QC
							// 5" 项目产业 QF
							if (type == 0) { // 属于企业公司
								supplierCodeType = "QD";
							} else if (type == 1) { // 属于子公司
								supplierCodeType = "QA";
							} else if (type == 2) { // 属于连锁公司
								supplierCodeType = "QE";
							} else if (type == 3) {
								supplierCodeType = "QB";
							} else if (type == 4) {
								supplierCodeType = "QC";
							} else if (type == 5) {
								supplierCodeType = "QF";
							}
							
							supplierCodeNum = RemoteServiceSingleton.getInstance().getSupplierManagerService()
									.getSupplierCodeNumByType(supplierCodeType);
							boolean supplierCodeFlag = true;
							
							while (supplierCodeFlag) {
								// 判断是否已经存在企业code
								String numCode = "";
								if (type == 2) { // 属于企业公司
									supplierCodeType = "QE";
									if (supplierCodeNum < 10) {
										numCode = "000" + supplierCodeNum;
									} else if (supplierCodeNum >= 10 && supplierCodeNum < 100) {
										numCode = "00" + supplierCodeNum;
									} else if (supplierCodeNum >= 100 && supplierCodeNum < 1000) {
										numCode = "0" + supplierCodeNum;
									} else {
										numCode = supplierCodeNum + "";
									}
								} else {
									if (supplierCodeNum < 10) {
										numCode = "00" + supplierCodeNum;
									} else if (supplierCodeNum >= 10 && supplierCodeNum < 100) {
										numCode = "0" + supplierCodeNum;
									} else {
										numCode = supplierCodeNum + "";
									}
								}
								
								String isExist = RemoteServiceSingleton.getInstance().getSupplierManagerService()
										.checkSupplierCodeIsExists(supplierCodeType + numCode);
								if (isExist.equals("0")) { // 不存在
									supplierCodeFlag = false;
								} else {
									supplierCodeNum = supplierCodeNum + 1;
								}
							}
		                    
							int newSupplierCodeNum = supplierCodeNum + 1;
							SupplierCodeType newSupplierCodeType = new SupplierCodeType();
							newSupplierCodeType.setSupplierCodeNum(newSupplierCodeNum);
							newSupplierCodeType.setSupplierCodeType(supplierCodeType);
							try {
								RemoteServiceSingleton.getInstance().getSupplierManagerService()
										.updateSupplierCodeNumByType(newSupplierCodeType);
							} catch (Exception e) {
								LOGGER.error("修改企业编码数量supplierCodeType = " + supplierCodeType, e);
								return Constants.ERROR;
							}
						} else {
							supplierCodeType = "M";
						}

						
						String newNum = "";
						if (type == 2) { // 属于企业公司
							supplierCodeType = "QE";
							if (supplierCodeNum < 10) {
								newNum = "000" + supplierCodeNum;
							} else if (supplierCodeNum >= 10 && supplierCodeNum < 100) {
								newNum = "00" + supplierCodeNum;
							} else if (supplierCodeNum >= 100 && supplierCodeNum < 1000) {
								newNum = "0" + supplierCodeNum;
							} else {
								newNum = supplierCodeNum + "";
							}
						} else {
							if (supplierCodeNum < 10) {
								newNum = "00" + supplierCodeNum;
							} else if (supplierCodeNum >= 10 && supplierCodeNum < 100) {
								newNum = "0" + supplierCodeNum;
							} else {
								newNum = supplierCodeNum + "";
							}
						}
		                
						supplier.setSupplierCode(supplierCodeType + newNum);
						supplier.setActiveStatus(-1); // -1为未激活状态
						if (!StringUtils.isEmpty(noPayHqq)) {
							supplier.setNoPayhqq(new BigDecimal(noPayHqq));
							supplier.setRemainBalanceAmount(new BigDecimal(noPayHqq));
						}
						if (!StringUtils.isEmpty(fanliPrice)) {
							supplier.setFanliPrice(new BigDecimal(fanliPrice));
						}
						if (!StringUtils.isEmpty(sjSupplierCode)) {
							Supplier modifySjSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
									.getSupplierBySupplierCode(sjSupplierCode);
							if (modifySjSupplier != null) {
								supplier.setSjSupplierId(modifySjSupplier.getSupplierId() + "");
								com.mall.customer.model.User sjUser = RemoteServiceSingleton.getInstance().getUserService()
										.findUserBySupplierId(modifySjSupplier.getSupplierId());
								if (sjUser != null && !StringUtils.isEmpty(sjUser.getMobile())) { // 企业小号推荐手机号变为上级企业的企业小号的手机号
									qiyeSmallTjMobile = sjUser.getMobile();
								}
							}
						}
						RemoteServiceSingleton.getInstance().getSupplierManagerService().checkMerchant(supplier, supplierUser);

					}
				} catch (Exception e) {
					resaultJsonString = Constants.ERROR;
					LOGGER.error("审核商户. 异常  msg：" + e.getMessage(), e);
					LOGGER.error("商户Id:" + getCurrentPlatformId());
					LOGGER.error("用户:" + getCurrentUser().getUsername());
					LOGGER.error("用户ID:" + getCurrentUser().getId());
					return resaultJsonString;
				}
				// 创建企业个人账号
				String supplierCodeType = "";
				// int newUserIdNum;
				int userIdNum;
				if (type == 0) { // 属于企业公司
					supplierCodeType = "QD";
				} else if (type == 1) { // 属于子公司
					supplierCodeType = "QA";
				} else if (type == 2) { // 属于连锁公司
					supplierCodeType = "QE";
				} else if (type == 3) {
					supplierCodeType = "QB";
				} else if (type == 4) {
					supplierCodeType = "QC";
				} else if (type == 5) {
					supplierCodeType = "QF";
				}
				

				
				int saveUserFlag = 0;
				// com.mall.customer.model.User u = null;
				// boolean existUser = true;
				int bigNum = 0;
				if (type == 0) { // 属于企业公司
					bigNum = 5000;
					userIdNum = RemoteServiceSingleton.getInstance().getUserService()
							.getStartUserIdBySupplierCode(supplierCodeType);
					saveUserFlag = getStartUserIdByType(userIdNum, bigNum, supplierCodeType);
				} else if (type == 1) { // 属于子公司
					bigNum = 1000;
					userIdNum = RemoteServiceSingleton.getInstance().getUserService()
							.getStartUserIdBySupplierCode(supplierCodeType);
					saveUserFlag = getStartUserIdByType(userIdNum, bigNum, supplierCodeType);
				} else if (type == 2) { // 属于连锁公司
					bigNum = 10000;
					userIdNum = RemoteServiceSingleton.getInstance().getUserService()
							.getStartUserIdBySupplierCode(supplierCodeType);
					saveUserFlag = getStartUserIdByType(userIdNum, bigNum, supplierCodeType);
				} else if (type == 3) {
					bigNum = 2000;
					userIdNum = RemoteServiceSingleton.getInstance().getUserService()
							.getStartUserIdBySupplierCode(supplierCodeType);
					saveUserFlag = getStartUserIdByType(userIdNum, bigNum, supplierCodeType);
				} else if (type == 4) {
					bigNum = 1500;
					userIdNum = RemoteServiceSingleton.getInstance().getUserService()
							.getStartUserIdBySupplierCode(supplierCodeType);
					saveUserFlag = getStartUserIdByType(userIdNum, bigNum, supplierCodeType);
				} else if (type == 5) {
					bigNum = 2500;
					userIdNum = RemoteServiceSingleton.getInstance().getUserService()
							.getStartUserIdBySupplierCode(supplierCodeType);
					saveUserFlag = getStartUserIdByType(userIdNum, bigNum, supplierCodeType);
				} else {
					saveUserFlag = -1;
				}
		        
				String username = "";
				if (saveUserFlag != 0) {
					userIdNum = saveUserFlag;
					com.mall.customer.model.User newUser = new com.mall.customer.model.User();
					if (saveUserFlag != -1) {
						newUser.setUserId(Long.valueOf(userIdNum + ""));
					}
					username = ProduceUserName.produce(userMobile);
					newUser.setMobile(userMobile);
					newUser.setPassword(String.valueOf(userPassword));

					newUser.setUserName(username);
					newUser.setRealName(userName);
					newUser.setNickName(username);
					newUser.setSupplierFlag("1");
					newUser.setSupplierId(id1 + "");
					newUser.setTjMobile(qiyeSmallTjMobile);
					int result = 0;
					try {
						result = RemoteServiceSingleton.getInstance().getUserService().saveUser(newUser);
						logger.info("result :" + result);
					} catch (Exception e) {
						logger.error("Exception,userService.saveUser has error,message:", e);
						e.printStackTrace();
						return Constants.ERROR;
					}
					// 创建账号成功发送短信
					if (result > 0) {
						String sendResult = SendSMSUtil.sendCreateAccountMessage(userMobile, String.valueOf(userPassword), name,
								username);
						logger.info("send sms end.result:" + sendResult);
					}
				} else {
					return "-2";
				}
		return Constants.SUCCESS;
	}

	//代理商创建账号
	@RequestMapping("/user/createAccountAgent")
	@ResponseBody
	public String createAccountAgent(String paymentDays, String isBillPayment, int source, int id1, int status1,
			int isRecord, String reason, int supplyType, HttpServletRequest request, String companyQy,
			String companyRegion, int type, String noPayHqq, String sjSupplierCode, String fanliPrice,Long province,Long city,Long country)
			throws Exception {

		SupplierPartnerArea supplierPartnerArea = new SupplierPartnerArea();
		supplierPartnerArea.setSupplierId((long) id1);
		if (province != null) {
			
			supplierPartnerArea.setProvinceId(province);
			supplierPartnerArea.setType(3);

			if (city != null) {
				supplierPartnerArea.setCityId(city);
				supplierPartnerArea.setType(2);

				if (country != null) {
					supplierPartnerArea.setCountyId(country);
					supplierPartnerArea.setType(1);
				}

			}
		}
		
		     // 通过supplierId去判断
					SupplierPartnerArea supplierPartnerArea2 = supplierManagerService.findPartnerArea((long) id1, null);
					if (supplierPartnerArea2 != null) {
						Integer row = supplierManagerService.updateSupplierPartnerAera(supplierPartnerArea);
					} else {
						supplierPartnerArea.setPartnerType(2);
						supplierPartnerArea.setStatus(1);
						int row = supplierManagerService.insertSupplierPartnerArea(supplierPartnerArea);
					}
		
		
		
		
		// 修改企业账号为可登陆状态，但不能发布商品

		String resaultJsonString = Constants.SUCCESS;

		String isOnekeySend = request.getParameter("isOnekeySend");
		if (org.apache.commons.lang3.StringUtils.isBlank(isOnekeySend)) {
			isOnekeySend = "1";
		}
		// 加入pop的支付方式选择
		String paymentType = request.getParameter("paymentType");
		// 3 走国内支付 目前只有支付宝国内支付
		paymentType = "3";
		if (null == request.getParameter("id1") || null == request.getParameter("status1")
				|| null == request.getParameter("source")) {
			resaultJsonString = Constants.SAMEISNULL;
			return resaultJsonString;
		}
		
		String name = ""; // 企业用户名
		String userName = "";
		String userMobile = "";
		String userPassword = "";
		String qiyeSmallTjMobile = ""; // 企业小号邀请码
		try {

			if (source == Constants.INT1) {

				Supplier supplier = new Supplier();
				Supplier findSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
						.findSupplier(Long.valueOf(id1));
				supplier.setSupplierId(Long.valueOf(id1));
				supplier.setStatus(status1);
				// supplier.setSupplyType(supplyType);

				userName = findSupplier.getUserName();
				userMobile = findSupplier.getUserMobile();
				// qiyeSmallTjMobile = findSupplier.getUserTj();

				SupplierUser user = RemoteServiceSingleton.getInstance().getSupplierManagerService()
						.findAdminUserByMerchantId(Long.valueOf(id1));

				// pop商家，如果审核成功后，如果没有店铺信息，需要创建一条
				if (supplyType == SupplierSouces.POP.getIndex()) {
					SupplierStore store = supplierStoreService.findSupplierStoreBySupplierId((int) id1);
					if (store == null) {
						SupplierStore temp = new SupplierStore();
						temp.setSupplierId((int) id1);
						temp.setStoreName(findSupplier.getName());
						supplierStoreService.insertStore(temp);
					}

					// 如果是pop商家，创建一个仓库
					// 仓库名称：第三方国际仓库-商家名称
					// 经销商名称：众聚商城
					// 地址：默认北京朝阳
					if (null == RemoteServiceSingleton.getInstance().getStockWofeService()
							.findWarehouseByWarehouseCode(Integer.valueOf(findSupplier.getSupplierId().toString()))
							.getWarehouseCode()) {
						Warehouse warehouse = new Warehouse();
						warehouse.setWarehouseCode(Integer.valueOf(findSupplier.getSupplierId().toString()));
						warehouse.setWarehouseType(Short.parseShort(7 + ""));
						warehouse.setWarehouseLevelCode(Short.parseShort(1 + ""));
						warehouse.setWarehouseLevelName("一级库房");
						warehouse.setWarehouseStatus(Short.parseShort(0 + ""));
						warehouse.setWarehouseName("第三方国际仓库" + findSupplier.getName());
						warehouse.setWarehouseTypeName("第三方国际商品仓库");
						warehouse.setDealerId(7735L);// 北京世纪新干线网络技术有限公司
						warehouse.setProvinceId(0l);
						warehouse.setCityId(0l);
						warehouse.setAreaId(0l);
						warehouse.setAddress("北京 北京市 朝阳区 霄云中心B座");
						warehouse.setContact("POP");
						warehouse.setTelephone(
								findSupplier.getPhone() != null ? Long.parseLong(findSupplier.getPhone()) : 0L);
						warehouse.setChannelCode(1l);
						warehouse.setChannelName("海外直邮");
						warehouse.setAccountType(Integer.parseInt(paymentType));
						RemoteServiceSingleton.getInstance().getStockWofeService().insertWarehouse(warehouse);
						logger.info("insert pop warehouse. supplier:" + warehouse.getWarehouseCode());
					}
				}
				if (user == null) {
					resaultJsonString = Constants.SAMEISNULL;
					return resaultJsonString;

				}
				name = user.getLoginName();
				userPassword = user.getRecordPwd();
				SupplierUser supplierUser = new SupplierUser();
				supplierUser.setSupplierId(Long.valueOf(id1));
				supplierUser.setStatus(status1);

				supplier.setIsOnekeySend(Integer.parseInt(isOnekeySend));
				supplier.setSupplyType(supplyType);
				supplier.setAuditTime(new Date());

				supplier.setCompanyRegion(companyRegion);
				supplier.setCompanyQy(companyQy);
				supplier.setType(type);

				int supplierCodeNum = id1;
				String supplierCodeType = "";
				if (type < 6) {
					// 0" 非自营企业 QD
					// 1" 子公司 QA
					// 2" 连锁企业 QE
					// 3" 连锁子公司 QB
					// 4" 自营企业 QC
					// 5" 项目产业 QF
					if (type == 0) { // 属于企业公司
						supplierCodeType = "QD";
					} else if (type == 1) { // 属于子公司
						supplierCodeType = "QA";
					} else if (type == 2) { // 属于连锁公司
						supplierCodeType = "QE";
					} else if (type == 3) {
						supplierCodeType = "QB";
					} else if (type == 4) {
						supplierCodeType = "QC";
					} else if (type == 5) {
						supplierCodeType = "QF";
					}
					
					supplierCodeNum = RemoteServiceSingleton.getInstance().getSupplierManagerService()
							.getSupplierCodeNumByType(supplierCodeType);
					boolean supplierCodeFlag = true;
					
					while (supplierCodeFlag) {
						// 判断是否已经存在企业code
						String numCode = "";
						if (type == 2) { // 属于企业公司
							supplierCodeType = "QE";
							if (supplierCodeNum < 10) {
								numCode = "000" + supplierCodeNum;
							} else if (supplierCodeNum >= 10 && supplierCodeNum < 100) {
								numCode = "00" + supplierCodeNum;
							} else if (supplierCodeNum >= 100 && supplierCodeNum < 1000) {
								numCode = "0" + supplierCodeNum;
							} else {
								numCode = supplierCodeNum + "";
							}
						} else {
							if (supplierCodeNum < 10) {
								numCode = "00" + supplierCodeNum;
							} else if (supplierCodeNum >= 10 && supplierCodeNum < 100) {
								numCode = "0" + supplierCodeNum;
							} else {
								numCode = supplierCodeNum + "";
							}
						}
						
						String isExist = RemoteServiceSingleton.getInstance().getSupplierManagerService()
								.checkSupplierCodeIsExists(supplierCodeType + numCode);
						if (isExist.equals("0")) { // 不存在
							supplierCodeFlag = false;
						} else {
							supplierCodeNum = supplierCodeNum + 1;
						}
					}
                    
					int newSupplierCodeNum = supplierCodeNum + 1;
					SupplierCodeType newSupplierCodeType = new SupplierCodeType();
					newSupplierCodeType.setSupplierCodeNum(newSupplierCodeNum);
					newSupplierCodeType.setSupplierCodeType(supplierCodeType);
					try {
						RemoteServiceSingleton.getInstance().getSupplierManagerService()
								.updateSupplierCodeNumByType(newSupplierCodeType);
					} catch (Exception e) {
						LOGGER.error("修改企业编码数量supplierCodeType = " + supplierCodeType, e);
						return Constants.ERROR;
					}
				} else {
					supplierCodeType = "M";
				}

				
				String newNum = "";
				if (type == 2) { // 属于企业公司
					supplierCodeType = "QE";
					if (supplierCodeNum < 10) {
						newNum = "000" + supplierCodeNum;
					} else if (supplierCodeNum >= 10 && supplierCodeNum < 100) {
						newNum = "00" + supplierCodeNum;
					} else if (supplierCodeNum >= 100 && supplierCodeNum < 1000) {
						newNum = "0" + supplierCodeNum;
					} else {
						newNum = supplierCodeNum + "";
					}
				} else {
					if (supplierCodeNum < 10) {
						newNum = "00" + supplierCodeNum;
					} else if (supplierCodeNum >= 10 && supplierCodeNum < 100) {
						newNum = "0" + supplierCodeNum;
					} else {
						newNum = supplierCodeNum + "";
					}
				}
                
				supplier.setSupplierCode(supplierCodeType + newNum);
				supplier.setActiveStatus(-1); // -1为未激活状态
				if (!StringUtils.isEmpty(noPayHqq)) {
					supplier.setNoPayhqq(new BigDecimal(noPayHqq));
					supplier.setRemainBalanceAmount(new BigDecimal(noPayHqq));
				}
				if (!StringUtils.isEmpty(fanliPrice)) {
					supplier.setFanliPrice(new BigDecimal(fanliPrice));
				}
				if (!StringUtils.isEmpty(sjSupplierCode)) {
					Supplier modifySjSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
							.getSupplierBySupplierCode(sjSupplierCode);
					if (modifySjSupplier != null) {
						supplier.setSjSupplierId(modifySjSupplier.getSupplierId() + "");
						com.mall.customer.model.User sjUser = RemoteServiceSingleton.getInstance().getUserService()
								.findUserBySupplierId(modifySjSupplier.getSupplierId());
						if (sjUser != null && !StringUtils.isEmpty(sjUser.getMobile())) { // 企业小号推荐手机号变为上级企业的企业小号的手机号
							qiyeSmallTjMobile = sjUser.getMobile();
						}
					}
				}
				RemoteServiceSingleton.getInstance().getSupplierManagerService().checkMerchant(supplier, supplierUser);

			}
		} catch (Exception e) {
			resaultJsonString = Constants.ERROR;
			LOGGER.error("审核商户. 异常  msg：" + e.getMessage(), e);
			LOGGER.error("商户Id:" + getCurrentPlatformId());
			LOGGER.error("用户:" + getCurrentUser().getUsername());
			LOGGER.error("用户ID:" + getCurrentUser().getId());
			return resaultJsonString;
		}
		// 创建企业个人账号
		String supplierCodeType = "";
		// int newUserIdNum;
		int userIdNum;
		if (type == 0) { // 属于企业公司
			supplierCodeType = "QD";
		} else if (type == 1) { // 属于子公司
			supplierCodeType = "QA";
		} else if (type == 2) { // 属于连锁公司
			supplierCodeType = "QE";
		} else if (type == 3) {
			supplierCodeType = "QB";
		} else if (type == 4) {
			supplierCodeType = "QC";
		} else if (type == 5) {
			supplierCodeType = "QF";
		}
		

		
		int saveUserFlag = 0;
		// com.mall.customer.model.User u = null;
		// boolean existUser = true;
		int bigNum = 0;
		if (type == 0) { // 属于企业公司
			bigNum = 5000;
			userIdNum = RemoteServiceSingleton.getInstance().getUserService()
					.getStartUserIdBySupplierCode(supplierCodeType);
			saveUserFlag = getStartUserIdByType(userIdNum, bigNum, supplierCodeType);
		} else if (type == 1) { // 属于子公司
			bigNum = 1000;
			userIdNum = RemoteServiceSingleton.getInstance().getUserService()
					.getStartUserIdBySupplierCode(supplierCodeType);
			saveUserFlag = getStartUserIdByType(userIdNum, bigNum, supplierCodeType);
		} else if (type == 2) { // 属于连锁公司
			bigNum = 10000;
			userIdNum = RemoteServiceSingleton.getInstance().getUserService()
					.getStartUserIdBySupplierCode(supplierCodeType);
			saveUserFlag = getStartUserIdByType(userIdNum, bigNum, supplierCodeType);
		} else if (type == 3) {
			bigNum = 2000;
			userIdNum = RemoteServiceSingleton.getInstance().getUserService()
					.getStartUserIdBySupplierCode(supplierCodeType);
			saveUserFlag = getStartUserIdByType(userIdNum, bigNum, supplierCodeType);
		} else if (type == 4) {
			bigNum = 1500;
			userIdNum = RemoteServiceSingleton.getInstance().getUserService()
					.getStartUserIdBySupplierCode(supplierCodeType);
			saveUserFlag = getStartUserIdByType(userIdNum, bigNum, supplierCodeType);
		} else if (type == 5) {
			bigNum = 2500;
			userIdNum = RemoteServiceSingleton.getInstance().getUserService()
					.getStartUserIdBySupplierCode(supplierCodeType);
			saveUserFlag = getStartUserIdByType(userIdNum, bigNum, supplierCodeType);
		} else {
			saveUserFlag = -1;
		}
        
		String username = "";
		if (saveUserFlag != 0) {
			userIdNum = saveUserFlag;
			com.mall.customer.model.User newUser = new com.mall.customer.model.User();
			if (saveUserFlag != -1) {
				newUser.setUserId(Long.valueOf(userIdNum + ""));
			}
			username = ProduceUserName.produce(userMobile);
			newUser.setMobile(userMobile);
			newUser.setPassword(String.valueOf(userPassword));

			newUser.setUserName(username);
			newUser.setRealName(userName);
			newUser.setNickName(username);
			newUser.setSupplierFlag("1");
			newUser.setSupplierId(id1 + "");
			newUser.setTjMobile(qiyeSmallTjMobile);
			int result = 0;
			try {
				result = RemoteServiceSingleton.getInstance().getUserService().saveUser(newUser);
				logger.info("result :" + result);
			} catch (Exception e) {
				logger.error("Exception,userService.saveUser has error,message:", e);
				e.printStackTrace();
				return Constants.ERROR;
			}
			// 创建账号成功发送短信
			if (result > 0) {
				String sendResult = SendSMSUtil.sendCreateAccountMessage(userMobile, String.valueOf(userPassword), name,
						username);
				logger.info("send sms end.result:" + sendResult);
			}
		} else {
			return "-2";
		}
        
		return Constants.SUCCESS;
	}

	/**
	 * 
	 */
	private int getStartUserIdByType(int userIdNum, int bigNum, String supplierCodeType) {
		int saveUserFlag = 0;
		int newUserIdNum = 0;
		com.mall.customer.model.User u = null;
		boolean existUser = true;
		if (userIdNum <= bigNum) {
			while (existUser) { // 判断该userId是否存在
				if (userIdNum > bigNum) { // 如果大于最大数直接return
					return 0;
				}
				u = RemoteServiceSingleton.getInstance().getUserService().findUserById(Long.parseLong(userIdNum + ""));
				if (u == null) {
					existUser = false;
					newUserIdNum = userIdNum + 1;
					RemoteServiceSingleton.getInstance().getUserService().updateTypeUserId(newUserIdNum,
							supplierCodeType);
				} else {
					++userIdNum;
				}
			}
			saveUserFlag = userIdNum;
		}
		return saveUserFlag;
	}

	/**
	 * <p>
	 * 弹出企业信息列表
	 * </p>
	 * 
	 * @param map
	 * @param message
	 * @param param
	 * @param paramPage
	 * @param request
	 * @param page
	 * @return
	 * @auth:zw
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/user/supplierList")
	public String supplierList(Map<String, Object> map, String message, Supplier param, PageBean paramPage,
			HttpServletRequest request, String page, String supplierCode, String supplierName) {
		try {
			paramPage.setSortFields("create_time,status");
			paramPage.setOrder("desc,asc");
			if (!StringUtils.isEmpty(page)) {
				paramPage.setPage(Integer.parseInt(page));
			}
			if (!StringUtils.isEmpty(supplierCode)) {
				param.setSupplierCode(supplierCode);
			}
			if (!StringUtils.isEmpty(supplierName)) {
				param.setName(supplierName);
			}
			paramPage.setParameter(param);
			paramPage = RemoteServiceSingleton.getInstance().getSupplierManagerService().getPageList(paramPage);
			List<Supplier> supplierList = paramPage.getResult();
			if (supplierList != null && supplierList.size() > 0) {
				for (Supplier s : supplierList) {
					com.mall.customer.model.User user = RemoteServiceSingleton.getInstance().getUserService()
							.findUserBySupplierId(s.getSupplierId());
					if (user != null && !StringUtils.isEmpty(user.getMobile())) {
						s.setUserId(user.getUserId());
					}
				}
			}

			map.put("page", paramPage);
			map.put("message", message);
		} catch (Exception e) {
			LOGGER.error("审核商户. 异常  msg：" + e);
		} finally {
		}
		return "user/modelPage/supplierModel";
	}

	/*
	 * 地区回显
	 * 
	 */

	@RequestMapping("/getAreaEcho")
	@ResponseBody
	public String getAreaEcho(Long supplierId) throws Exception {
		SupplierPartnerArea area = supplierManagerService.findPartnerArea(supplierId, null);
		return JSONObject.toJSONString(area);
	}

	/**
	 * 获取省区域数据
	 * 
	 * @throws Exception
	 * @throws IOException
	 * 
	 */
	@Autowired
	private BaseDataServiceRpc baseDataServiceRpc;

	@RequestMapping("/getProvinceList")
	@ResponseBody
	public String getProvinceList() throws Exception {
		List<AgentProvince> allProvices = baseDataServiceRpc.getAllProvices();
		return JSONObject.toJSONString(allProvices);
	}

	/**
	 * 获取市区域数据
	 * 
	 * @throws Exception
	 * @throws IOException
	 * 
	 */
	@RequestMapping("/getCityList")
	@ResponseBody
	public String getCityList() throws Exception {
		List<AgentCity> allCity = baseDataServiceRpc.getAllCity();
		return JSONObject.toJSONString(allCity);
	}

	/**
	 * 获取县区域数据
	 * 
	 * @throws Exception
	 * @throws IOException
	 * 
	 */
	@RequestMapping("/getCountryList")
	@ResponseBody
	public String getCountryList() throws Exception {
		List<AgentCounty> allCounty = baseDataServiceRpc.getAllCounty();
		return JSONObject.toJSONString(allCounty);
	}
	@RequestMapping("/getCategory")
	@ResponseBody
	public String getCategory(String sectorname) throws Exception {
		System.out.println(sectorname);
		String name = "";
		if ("1".equals(sectorname)) {
			name = "企业";
		}
		if ("2".equals(sectorname)) {
			name = "个体工商户";
		}
		if ("3".equals(sectorname)) {
			name = "事业单位";
		}
		List<TeSectors> tse = teSectorsService.getCategoryAll2(name);

		return JSONObject.toJSONString(tse);

	}
	
	
	/**
	 *获取所有银行数据 
	 */
	@RequestMapping("/getBankList")
	@ResponseBody
	public String getBankList() throws Exception {
		List<BankCode> list = bankService.findAllBank();
		return JSONObject.toJSONString(list);
	}
	
	/**
	 *获取所有银行的省份数据 
	 */
	@RequestMapping("/getBankProvinceList")
	@ResponseBody
	public String getBankProvinceList(Integer bankCode) throws Exception {
		 Map<String, String> map = bankService.findBankOfProvince(bankCode);
		return JSONObject.toJSONString(map);
	}
	
	/**
	 *加载该银行所在省的所有区的数据
	 */
	@RequestMapping("/getBankCityList")
	@ResponseBody
	public String getBankCityList(Integer bankCode,Integer bankProvinceId) throws Exception {
		 Map<String, String> map = bankService.findBankOfCity(bankCode, bankProvinceId);
		return JSONObject.toJSONString(map);
	}
	
	/**
	 *  加载该所在省的所有区的所有银行的数据
	 */
	@RequestMapping("/getSubBankOfCity")
	@ResponseBody
	public String getSubBankOfCity(Integer bankCode,Integer bankAreaId) throws Exception {
		List<BankBranch> list = bankService.findSubBankOfCity(bankCode, bankAreaId);
		return JSONObject.toJSONString(list);
	}
	
	
	
	
}
