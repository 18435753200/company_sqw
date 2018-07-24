package com.mall.controller.user;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import org.csource.upload.UploadFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.Producer;
import com.mall.annotation.Token;
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
import com.mall.controller.base.BaseController;
import com.mall.customer.dto.HomeNumRecordDto;
import com.mall.customer.dto.ShareInterestDetailDto;
import com.mall.customer.dto.activity.SupplierNumRecordDto;
import com.mall.customer.model.BizRcode;
import com.mall.customer.model.SqwAccountRecord;
import com.mall.customer.model.User;
import com.mall.customer.order.api.rpc.CustomerOrderService;
import com.mall.customer.order.dto.OrderDTO;
import com.mall.customer.service.HomeNumRecordService;
import com.mall.customer.service.PsqwAccountRecordService;
import com.mall.customer.service.SupplierNumRecordService;
import com.mall.customer.service.UserService;
import com.mall.merchant.proxy.RemoteServiceSingleton;
import com.mall.mybatis.utility.PageBean;
import com.mall.sendemail.SendHtmlMail;
import com.mall.supplier.model.AgentSupplier;
import com.mall.supplier.model.OnLineSupplier;
import com.mall.supplier.model.Supplier;
import com.mall.supplier.model.SupplierAgentType;
import com.mall.supplier.model.SupplierPartner;
import com.mall.supplier.model.SupplierPartnerArea;
import com.mall.supplier.model.SupplierProduct;
import com.mall.supplier.model.SupplierRegionSettings;
import com.mall.supplier.model.SupplierUser;
import com.mall.supplier.service.SupplierAgentTypeService;
import com.mall.supplier.service.SupplierManagerService;
import com.mall.supplier.service.SupplierRegionService;
import com.mall.supplier.service.SupplierUserManagerService;
import com.mall.utils.Common;
import com.mall.utils.Constants;
import com.mall.utils.DES;
import com.mall.utils.MD5;
import com.mall.utils.MapUtils;

import net.rubyeye.xmemcached.exception.MemcachedException;

/**
 * @author wdj
 */
@Controller
@RequestMapping(value = "/supplier")
public class SupplierController extends BaseController {

	@Autowired
	private Producer captchaProducer = null;

	@Autowired
	private SupplierRegionService regionService;

	@Autowired
	private HomeNumRecordService homeNumRecordService;

	@Autowired
	private SupplierNumRecordService supplierNumRecordService;
	@Autowired
	private SupplierUserManagerService supplierUserManagerService;
	@Autowired
	private BaseDataServiceRpc baseDataServiceRpc;
	@Autowired
	private UserService userService;
	@Autowired
	private TeSectorsService teSectorsService;
	@Autowired
	private SupplierManagerService supplierManagerService;
	@Autowired
	private SupplierAgentTypeService supplierAgentTypeService;
	@Autowired
	private PsqwAccountRecordService psqwAccountRecordService;
	@Autowired
	private BankService bankService;
	@Autowired
	private CustomerOrderService customerOrderService;

	/**
	 * log.
	 */
	private static final Logger LOGGER = Logger.getLogger(SupplierUserController.class);

	/**
	 * 修改上级代理功能
	 */
	@RequestMapping("editSupplierParentId")
	@ResponseBody
	public void editSupplierParentId(String str, Long parentId) {
		// 把前台传递的代理id字符串切割成数组,封住到集合中.
		String[] st = str.split(",");
		List<Long> list = new ArrayList<Long>();
		for (String string : st) {
			list.add(Long.parseLong(string));
		}
		supplierManagerService.updateSupplierParentId(list, parentId);
	}

	/**
	 * 修改上级代理查出该上级代理同级的所有代理
	 */
	@RequestMapping("getAgentSame")
	@ResponseBody
	public String getAgentSame(Long supplierId) {
		// 根据id查询出对象
		Supplier sup = supplierManagerService.findSupplier(supplierId);
		if (sup == null) {
			return JSONObject.toJSONString(sup);
		}
		// 根据父级id查出该父级下的所有的下级
		List<Supplier> list = supplierManagerService.getSubSuppliersByPid(sup.getParentId());
		// 提取出其中的id和name
		List<Map<String, String>> li = new ArrayList<Map<String, String>>();
		for (Supplier s : list) {
			// 必须确保代理的状态为审核通过
			if (s.getActiveStatus() == 1) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", s.getSupplierId().toString());
				map.put("name", s.getName());
				li.add(map);
			}
		}
		return JSONObject.toJSONString(li);
	}

	/**
	 * 冻结代理功能
	 */
	@RequestMapping("/editFreezeAgent")
	@ResponseBody
	public void editFreezeAgent(String id) {
		supplierUserManagerService.updateSupplier(id);
	}

	/**
	 * 解冻代理功能
	 */
	@RequestMapping("/editUnFreezeAgent")
	@ResponseBody
	public void editUnFreezeAgent(String id) {
		supplierUserManagerService.frozenSupplier(id);
	}

	/**
	 * 跳转二维码页面 查看代理
	 * 
	 * @param model
	 * @return
	 */
	// @RequestMapping("/getQrCode")
	// public String getQrCode(Model model, Long uId) {
	// User user = userService.findsingleUserBySupplierId(uId);
	// if (user != null) {
	// BizRcode br = userService.myRcodeByFortoM(user.getUserId().toString());
	// model.addAttribute("code", br.getRcodeImgTxt());
	// }
	// return getLanguage() + "/agent/qrcode/QrCode";
	// }

	/**
	 * . 加载代理列表页面
	 */
	@RequestMapping(value = "/getAgentPage")
	public String getAgentPage(Model model, String page, String parentId) {
		// 主要设置一个第几页
		if (page == null) {
			page = "1";
		}
		model.addAttribute("page", page);
		model.addAttribute("parentId", parentId);

		return "/zh/agent/agentList";

	}

	/**
	 * 判断业务员下级是否有商家
	 */
	@RequestMapping("checkJunior")
	@ResponseBody
	public String checkJunior(Long parentId) {

		// 根据supplierId去查询是否有下级
		List<Long> list = supplierManagerService.getAllSupplierIdsByPid(parentId.toString());
		// 如果查出的集合是否为空返回不同的数据(0是没有下级商家,1是有下级商家)
		String num = "";
		if (list.size() == 0) {
			num = "0";
		} else {
			num = "1";
		}
		return num;

	}

	/**
	 * 加载代理页面数据
	 */
	@RequestMapping("getAgentList")
	public String getAgentList(Model model, String parentId, Supplier supplier, Integer page, String name,
			String checkStatus) {

		LOGGER.info("getAgentList!!!supplierId:" + getCurrentSupplierId());

		// 判断是否获取到父级id,没有就使用当前登录用户的
		if (parentId == null || parentId == "") {
			parentId = getCurrentSupplierId().toString();
		}

		// 根据父级type查询下级code
		Supplier s = supplierManagerService.findSupplier(Long.parseLong(parentId));
		if (s == null) {
			model.addAttribute("ad", "0");
			return "/zh/agent/modelPage/agent_model";
		}
		SupplierAgentType supplierAgentType = supplierAgentTypeService.findChildAgentTypeByCode(s.getType());

		// 判断是否还可以添加下级代理 (1可以,0不可以)
		if (supplierAgentType == null) {
			model.addAttribute("ad", "0");
			return "/zh/agent/modelPage/agent_model";
		} else {
			if (s.getStatus() == 5) {
				model.addAttribute("ad", "0");
			} else {
				model.addAttribute("ad", "1");
			}
		}

		/**
		 * 列表页面显示代理级别,并且可以点击回到特定的代理级别页面
		 */
		// 首先查询出当前登录用户的代理级别
		Supplier sup = supplierManagerService.findSupplier(getCurrentSupplierId());
		if (sup == null) {
			model.addAttribute("ad", "0");
			return "/zh/agent/modelPage/agent_model";
		}
		SupplierAgentType st = supplierAgentTypeService.findAgentByCode(sup.getType());
		// 查询出所有的代理级别
		List<SupplierAgentType> agentList = supplierAgentTypeService.findAllSupplierAgentType();
		// 把从当前登录用户下的代理级别开始放入数组,以当前查看的代理级别为停止条件
		List<String> list = new ArrayList<String>();
		Boolean flag = false;
		for (SupplierAgentType sat : agentList) {
			if (flag == true) {
				list.add(sat.getName());
			}
			if (sat.getName().equals(st.getName())) {
				flag = true;
			}
			if (sat.getName().equals(supplierAgentType.getName())) {
				flag = false;
			}
		}
		Map<String, String> map = new LinkedHashMap<String, String>();
		// 把每一级的代理级别对应的父级id绑定起来
		Long childrenId = Long.valueOf(parentId);
		if (childrenId.equals(getCurrentSupplierId())) {
			map.put(list.get(0), childrenId.toString());
		} else {
			map.put(list.get(list.size() - 1), childrenId.toString());
			for (int i = list.size() - 2; i >= 0; i--) {
				if (childrenId.equals(getCurrentSupplierId())) {
					continue;
				}
				Supplier fs = supplierManagerService.findParentSupplier(childrenId);
				childrenId = fs.getSupplierId();
				map.put(list.get(i), fs.getSupplierId().toString());
			}
		}
		Map<String, String> agentMenu = MapUtils.reverseMap(map);

		// 分页查询数据
		supplier.setParentId(Long.parseLong(parentId));
		PageBean<Supplier> supplierPageBean = new PageBean<Supplier>();
		if (null != page && page != 0) {
			supplierPageBean.setPage(page);
		} else {
			supplierPageBean.setPage(1);
		}
		if (null != name) {
			supplier.setName(name);
		}

		// 根据status判断查询的条件
		if (null != checkStatus) {
			// 待审核
			if ("0".equals(checkStatus)) {
				supplier.setActiveStatus(-1);
				// supplier.setStatus(0);
				supplier.setStatus(6);
				// 未审核状态应该是0,但是页面列表显示时,创建账号状态也应该设置为未审核的状态,为了方便数据的查询,
				// 先把状态设为 6 ,在map中进行判断,当状态为6时,同时查询status为 0 和 1 的状态!
			}
			/*
			 * // 创建账号 if ("3".equals(checkStatus)) {
			 * supplier.setActiveStatus(-1); supplier.setStatus(1); }
			 */
			// 冻结账号
			if ("4".equals(checkStatus)) {
				supplier.setStatus(5);
			}
			// 审核不通过
			if ("1".equals(checkStatus)) {
				supplier.setActiveStatus(-1);
				supplier.setStatus(2);
			}
			// 审核通过
			if ("2".equals(checkStatus)) {
				supplier.setActiveStatus(1);
				supplier.setStatus(1);
			}
		}

		supplierPageBean.setParameter(supplier);
		supplierPageBean.setOrder("DESC");
		PageBean<Supplier> pageList = supplierManagerService.getAgentSupplierList(supplierPageBean);

		model.addAttribute("pass", pageList);
		model.addAttribute("parentId", parentId);
		model.addAttribute("ps", s.getStatus());
		model.addAttribute("sat", supplierAgentType);
		model.addAttribute("agentMenu", agentMenu);

		return "/zh/agent/modelPage/agent_model";

	}

	/**
	 * 跳转到添加代理页面
	 */
	@RequestMapping("/addAgentPage")
	public String addAgentPage(Map<String, Object> map, Long parentId)
			throws TimeoutException, InterruptedException, MemcachedException {
		String uid = UUID.randomUUID().toString();

		if (parentId == null) {
			parentId = getCurrentSupplierId();
		}
		// 根据父级type查询下级code
		Supplier s = supplierManagerService.findSupplier(parentId);
		SupplierAgentType supplierAgentType = null;
		if (s != null) {
			supplierAgentType = supplierAgentTypeService.findChildAgentTypeByCode(s.getType());
		}

		map.put("uid", uid);
		map.put("parentId", parentId);
		map.put("supplierAgentType", supplierAgentType);

		// 传递验证码
		try {
			memcachedClient.set(uid, 60 * 60, uid);
		} catch (Exception e) {
			LOGGER.error("memcachedClient获取uid报错", e);
			e.printStackTrace();
		}
		return "/zh/agent/agentRegist";
	}

	/**
	 * 跳转到添加市场合伙人页面
	 */
	@RequestMapping("/addMpAgentPage")
	public String addMpAgentPage(Map<String, Object> map, Long parentId)
			throws TimeoutException, InterruptedException, MemcachedException {
		String uid = UUID.randomUUID().toString();

		if (parentId == null) {
			parentId = getCurrentSupplierId();
		}
		// 根据父级type查询下级code
		Supplier s = supplierManagerService.findSupplier(parentId);
		SupplierAgentType supplierAgentType = null;
		if (s != null) {
			supplierAgentType = supplierAgentTypeService.findChildAgentTypeByCode(s.getType());
		}

		map.put("uid", uid);
		map.put("parentId", parentId);
		map.put("supplierAgentType", supplierAgentType);

		// 传递验证码
		try {
			memcachedClient.set(uid, 60 * 60, uid);
		} catch (Exception e) {
			LOGGER.error("memcachedClient获取uid报错", e);
			e.printStackTrace();
		}
		return "/zh/agent/agentMpRegist";
	}

	/**
	 * 跳转到修改代理页面
	 */
	@RequestMapping("/editAgent")
	public String editAgent(Map<String, Object> map, Long parentId, Long supplierId, Long isUpdate)
			throws TimeoutException, InterruptedException, MemcachedException {

		// 修改或者查看使用
		Supplier supplier = supplierManagerService.findSupplier(supplierId);
		SupplierUser supplierUser = supplierUserManagerService.getIsAdminUserBySupplierId(supplierId);

		if (parentId == null) {
			parentId = getCurrentSupplierId();
		}
		// 根据父级type查询下级code
		Supplier s = supplierManagerService.findSupplier(parentId);
		SupplierAgentType supplierAgentType = null;
		if (s != null) {
			supplierAgentType = supplierAgentTypeService.findChildAgentTypeByCode(s.getType());
		}

		// 查看银行 信息
		BankBranch bankBranch = bankService.findBankBranchByCode(supplier.getAccoutBankno());

		map.put("parentId", parentId);
		map.put("sr", supplier);
		map.put("supplierUser", supplierUser);
		map.put("supplierAgentType", supplierAgentType);
		map.put("bankBranch", bankBranch);

		if (isUpdate == null) {
			isUpdate = 2L;
		}
		map.put("isUpdate", isUpdate);

		return "/zh/agent/updateAgent";
	}

	// 查询代理收入流水详细信息
	@RequestMapping("/getFlowMore")
	@ResponseBody
	public String getFlowMore(Long id) {
		ShareInterestDetailDto shareDto = psqwAccountRecordService.selectShareInterestDetailByRecordId(id);
		return JSON.toJSONString(shareDto);
	}

	/**
	 * 跳转到修改市场合伙人页面
	 */
	@RequestMapping("/editMpAgent")
	public String editMpAgent(Map<String, Object> map, Long parentId, Long supplierId, Long isUpdate)
			throws TimeoutException, InterruptedException, MemcachedException {

		// 修改或者查看使用
		Supplier supplier = supplierManagerService.findSupplier(supplierId);
		SupplierUser supplierUser = supplierUserManagerService.getIsAdminUserBySupplierId(supplierId);

		if (parentId == null) {
			parentId = getCurrentSupplierId();
		}
		// 根据父级type查询下级code
		Supplier s = supplierManagerService.findSupplier(parentId);
		SupplierAgentType supplierAgentType = null;
		if (s != null) {
			supplierAgentType = supplierAgentTypeService.findChildAgentTypeByCode(s.getType());
		}

		// 查看银行 信息
		BankBranch bankBranch = bankService.findBankBranchByCode(supplier.getAccoutBankno());

		map.put("parentId", parentId);
		map.put("sr", supplier);
		map.put("supplierUser", supplierUser);
		map.put("supplierAgentType", supplierAgentType);
		map.put("bankBranch", bankBranch);

		if (isUpdate == null) {
			isUpdate = 2L;
		}
		map.put("isUpdate", isUpdate);

		return "/zh/agent/updateMpAgent";
	}

	/**
	 * 跳转到查看代理页面
	 */
	@RequestMapping("/getAgent")
	public String getAgent(Map<String, Object> map, HttpServletRequest request, Long parentId, Long supplierId)
			throws TimeoutException, InterruptedException, MemcachedException {

		// 修改或者查看使用
		Supplier supplier = supplierManagerService.findSupplier(supplierId);
		SupplierUser supplierUser = supplierUserManagerService.getIsAdminUserBySupplierId(supplierId);

		if (parentId == null) {
			parentId = getCurrentSupplierId();
		}

		BankBranch bankBranch = bankService.findBankBranchByCode(supplier.getAccoutBankno());
		// // 根据code查询出代理级别对象
		// if (supplier != null) {
		// SupplierAgentType supplierAgentType =
		// supplierAgentTypeService.findAgentByCode(supplier.getType());
		// map.put("supplierAgentType", supplierAgentType);
		// }

		map.put("parentId", parentId);
		map.put("sr", supplier);
		map.put("supplierUser", supplierUser);
		map.put("bankBranch", bankBranch);

		// 查看完整信息
		// return "/zh/agent/lookAgent";
		return "/zh/agent/lookAgent2";
	}

	/**
	 * 跳转到查看MP代理页面
	 */
	@RequestMapping("/getMpAgent")
	public String getMpAgent(Map<String, Object> map, HttpServletRequest request, Long parentId, Long supplierId)
			throws TimeoutException, InterruptedException, MemcachedException {

		// 修改或者查看使用
		Supplier supplier = supplierManagerService.findSupplier(supplierId);
		SupplierUser supplierUser = supplierUserManagerService.getIsAdminUserBySupplierId(supplierId);

		if (parentId == null) {
			parentId = getCurrentSupplierId();
		}

		BankBranch bankBranch = bankService.findBankBranchByCode(supplier.getAccoutBankno());

		map.put("parentId", parentId);
		map.put("sr", supplier);
		map.put("supplierUser", supplierUser);
		map.put("bankBranch", bankBranch);

		// 查看完整信息
		return "/zh/agent/lookMpAgent";
	}

	/**
	 * 获取省区域数据
	 * 
	 * @throws Exception
	 * @throws IOException
	 * 
	 */
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

	/**
	 * 回显代理区域
	 */
	@RequestMapping("/getAreaEcho")
	@ResponseBody
	public String getAreaEcho(Long supplierId) throws Exception {
		// SupplierPartnerArea supplierPartnerArea =
		// supplierManagerService.findPartnerArea(supplierId, null);
		// return JSONObject.toJSONString(supplierPartnerArea);
		return null;
	}

	/**
	 * 获取所有银行数据
	 */
	@RequestMapping("/getBankList")
	@ResponseBody
	public String getBankList() throws Exception {
		List<BankCode> list = bankService.findAllBank();
		return JSONObject.toJSONString(list);
	}

	/**
	 * 获取所有银行的省份数据
	 */
	@RequestMapping("/getBankProvinceList")
	@ResponseBody
	public String getBankProvinceList(Integer bankCode) throws Exception {
		Map<String, String> map = bankService.findBankOfProvince(bankCode);
		return JSONObject.toJSONString(map);
	}

	/**
	 * 加载该银行所在省的所有区的数据
	 */
	@RequestMapping("/getBankCityList")
	@ResponseBody
	public String getBankCityList(Integer bankCode, Integer bankProvinceId) throws Exception {
		Map<String, String> map = bankService.findBankOfCity(bankCode, bankProvinceId);
		return JSONObject.toJSONString(map);
	}

	/**
	 * 加载该所在省的所有区的所有银行的数据
	 */
	@RequestMapping("/getSubBankOfCity")
	@ResponseBody
	public String getSubBankOfCity(Integer bankCode, Integer bankAreaId) throws Exception {
		List<BankBranch> list = bankService.findSubBankOfCity(bankCode, bankAreaId);
		return JSONObject.toJSONString(list);
	}

	/**
	 * @Description:更新代理基本信息.
	 */
	@RequestMapping("/updateAgentMessage")
	public String updateAgentMessage(MultipartFile idCardFrontTo, MultipartFile idCardVersoTo,
			MultipartFile businessLicenseTo, HttpServletRequest request, MultipartFile companyStoreLogoTo,
			MultipartFile companyLegitimacy, MultipartFile companyDetailed, String name, Supplier supplier,
			Long province, Long city, Long country) {
		// 上传文件的校验
		if (idCardFrontTo == null || idCardVersoTo == null || businessLicenseTo == null) {
			request.setAttribute("isError", 1);
			if (getLanguage().equals("/en")) {
				request.setAttribute("message", "Missing Upload Files");
			} else {
				request.setAttribute("message", "缺少需要上传的文件");
			}
			LOGGER.error("注册：缺少需要上传的文件！");
			return "forward:/supplier/regisAgent";
		} else {
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
		}
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

		supplier.setModifyStatus(1);
		Supplier newSupplier = new Supplier();
		newSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
				.findSupplier(supplier.getSupplierId());
		if (newSupplier != null && newSupplier.getStatus() == 2) { // 未通过后修改信息
																	// 可以重新审核
			supplier.setStatus(1);
			supplier.setActiveStatus(-1);
		}

		supplier.setName(name);
		BankBranch bank = bankService.findBankBranchByCode(supplier.getAccoutBankno());
		supplier.setAccoutBankname(bank.getBankBranchName());

		// 修改supplier数据
		int result = RemoteServiceSingleton.getInstance().getSupplierManagerService().updataSupplier(supplier);

		// 修改代理地区数据
		// SupplierPartnerArea supplierPartnerArea = new SupplierPartnerArea();
		// if (province != null) {
		// supplierPartnerArea.setType(3);
		// supplierPartnerArea.setProvinceId(province);
		// if (city != null) {
		// supplierPartnerArea.setType(2);
		// supplierPartnerArea.setCityId(city);
		// if (country != null) {
		// supplierPartnerArea.setType(1);
		// supplierPartnerArea.setCountyId(country);
		// }
		// }
		// }

		// supplierPartnerArea.setSupplierId(supplier.getSupplierId());

		// SupplierPartnerArea sp =
		// supplierManagerService.findPartnerArea(supplier.getSupplierId(),
		// null);

		// 如果添加过就修改
		// if (sp != null) {
		// supplierManagerService.updateSupplierPartnerAera(supplierPartnerArea);
		// } else {
		// // 没有添加过就添加
		// supplierPartnerArea.setStatus(1);
		// supplierPartnerArea.setPartnerType(2);
		// supplierManagerService.insertSupplierPartnerArea(supplierPartnerArea);
		// }

		if (result > 0) {
			LOGGER.info("用户：" + getCurrentUser().getLoginName() + "更新代理基本信息成功");
			return "forward:/supplier/editAgent?parentId=" + supplier.getParentId() + "&supplierId="
					+ supplier.getSupplierId() + "&isUpdate=1";
		} else {
			LOGGER.error("用户：" + getCurrentUser().getLoginName() + "更新代理基本信息失败！");
			return "forward:/supplier/editAgent?parentId=" + supplier.getParentId() + "&supplierId="
					+ supplier.getSupplierId() + "&isUpdate=0";
		}

	}

	/**
	 * @Description:更新Mp基本信息.
	 */
	@RequestMapping("/updateMpAgentMessage")
	public String updateMpAgentMessage(MultipartFile idCardFrontTo, MultipartFile idCardVersoTo,
			MultipartFile companyStoreLogoTo, HttpServletRequest request, Supplier supplier, Long province, Long city,
			Long country) {
		// 上传文件的校验
		if (idCardFrontTo == null || idCardVersoTo == null) {
			request.setAttribute("isError", 1);
			if (getLanguage().equals("/en")) {
				request.setAttribute("message", "Missing Upload Files");
			} else {
				request.setAttribute("message", "缺少需要上传的文件");
			}
			LOGGER.error("注册：缺少需要上传的文件！");
			return "forward:/supplier/regisAgent";
		} else {
			String idCardFrontUrl = "";
			String idCardVersoUrl = "";
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
			} catch (Exception e) {
				LOGGER.error("注册：上传文件到图片服务器出错！", e);
			}
			if (null != idCardFrontTo && !"".equals(Common.getFileExt2(idCardFrontTo.getOriginalFilename()))) {
				supplier.setIdCardFront(idCardFrontUrl);
			}
			if (null != idCardVersoTo && !"".equals(Common.getFileExt2(idCardVersoTo.getOriginalFilename()))) {
				supplier.setIdCardVerso(idCardVersoUrl);
			}
			// 默认图片logo地址
			supplier.setLogoImgurl("group1/M00/00/31/CgAAElgxxt-Aa7U1AAAjS6o85T0082.jpg");
		}

		String companyStoreLogoUrl = "";
		try {
			// 经营门头照
			if (null != companyStoreLogoTo
					&& !"".equals(Common.getFileExt2(companyStoreLogoTo.getOriginalFilename()))) {
				companyStoreLogoUrl = UploadFileUtil.uploadFile(companyStoreLogoTo.getInputStream(),
						Common.getFileExt2(companyStoreLogoTo.getOriginalFilename()), null);
			}
		} catch (Exception e) {
			LOGGER.error("注册：上传文件到图片服务器出错！", e);
		}
		if (null != companyStoreLogoTo && !"".equals(Common.getFileExt2(companyStoreLogoTo.getOriginalFilename()))) {
			supplier.setCompanyStoreLogo(companyStoreLogoUrl);
		}

		supplier.setModifyStatus(1);
		Supplier newSupplier = new Supplier();
		newSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
				.findSupplier(supplier.getSupplierId());
		if (newSupplier != null && newSupplier.getStatus() == 2) { // 未通过后修改信息
			// 可以重新审核
			supplier.setStatus(1);
			supplier.setActiveStatus(-1);
		}

		// 进行部分数据的修改
		if (supplier.getRegisterAddress() != null) {
			supplier.setAddress(supplier.getRegisterAddress());
		}

		BankBranch bank = bankService.findBankBranchByCode(supplier.getAccoutBankno());
		supplier.setAccoutBankname(bank.getBankBranchName());

		// 修改supplier数据
		int result = RemoteServiceSingleton.getInstance().getSupplierManagerService().updataSupplier(supplier);

		if (result > 0) {
			LOGGER.info("用户：" + getCurrentUser().getLoginName() + "更新代理基本信息成功");
			return "forward:/supplier/editMpAgent?parentId=" + supplier.getParentId() + "&supplierId="
					+ supplier.getSupplierId() + "&isUpdate=1";
		} else {
			LOGGER.error("用户：" + getCurrentUser().getLoginName() + "更新代理基本信息失败！");
			return "forward:/supplier/editMpAgent?parentId=" + supplier.getParentId() + "&supplierId="
					+ supplier.getSupplierId() + "&isUpdate=0";
		}

	}

	/**
	 * @Description:添加代理
	 */
	@RequestMapping("/addAgentModel")
	public String addAgentModel(MultipartFile idCardFrontTo, MultipartFile idCardVersoTo,
			MultipartFile businessLicenseTo, MultipartFile companyStoreLogoTo, MultipartFile companyLegitimacy,
			MultipartFile companyDetailed, String kaptcha, Map<String, Object> map, AgentSupplier agentSupplier,
			HttpServletRequest request, String uid, Long parentId) {
		LOGGER.info("注册：supplier:" + JSON.toJSONString(agentSupplier));

		// 验证码校验
		try {
			String kaptchaExpected = memcachedClient.get(uid + Constants.KAPTCHA_SESSION_KEY);
			if (!kaptcha.toLowerCase().equals(kaptchaExpected.toLowerCase())) {
				if (getLanguage().equals("/en")) {
					request.setAttribute("message", "Verification code is wrong！");
				} else {
					request.setAttribute("message", "验证码有误！");
				}
				return "forward:/supplier/addAgentPage";
			}
		} catch (Exception e) {
			LOGGER.error("注册：  memcached中取验证码出问题！", e);
		}

		// 上传文件的校验
		if (idCardFrontTo == null || idCardVersoTo == null || businessLicenseTo == null) {
			request.setAttribute("isError", 1);
			if (getLanguage().equals("/en")) {
				request.setAttribute("message", "Missing Upload Files");
			} else {
				request.setAttribute("message", "缺少需要上传的文件");
			}
			LOGGER.error("注册：缺少需要上传的文件！");
			return "forward:/supplier/regisAgent";
		} else {
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
				agentSupplier.setIdCardFront(idCardFrontUrl);
			}
			if (null != idCardVersoTo && !"".equals(Common.getFileExt2(idCardVersoTo.getOriginalFilename()))) {
				agentSupplier.setIdCardVerso(idCardVersoUrl);
			}
			if (null != businessLicenseTo && !"".equals(Common.getFileExt2(businessLicenseTo.getOriginalFilename()))) {
				agentSupplier.setBusinessLicense(businessLicenseUrl);
			}

			// 默认图片logo地址
			agentSupplier.setLogoImgurl("group1/M00/00/31/CgAAElgxxt-Aa7U1AAAjS6o85T0082.jpg");
		}

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
			agentSupplier.setCompanyStoreLogo(companyStoreLogoUrl);
		}
		if (null != companyLegitimacy && !"".equals(Common.getFileExt2(companyLegitimacy.getOriginalFilename()))) {
			agentSupplier.setCompanyLegitimacyUrl(companyLegitimacyUrl);
		}
		if (null != companyDetailed && !"".equals(Common.getFileExt2(companyDetailed.getOriginalFilename()))) {
			agentSupplier.setCompanyDetailedUrl(companyDetailedUrl);
		}

		// 代理地区的校验
		/*
		 * if(province==null){ LOGGER.error("注册：缺少代理地区！");
		 * request.setAttribute("message","必须选择代理区域"); return
		 * "forward:/supplier/addAgentPage"; }
		 */

		// 登录pop账号的校验(判断用户名是否重复注册)
		Map<String, String> pmap = new LinkedHashMap<String, String>();
		int count = 1;
		if (!Common.isEmpty(request.getParameter("loginName"))) {
			pmap.put("loginName", request.getParameter("loginName"));
			count = RemoteServiceSingleton.getInstance().getSupplierUserManagerService().getUserByName(pmap);
		}
		if (count == 1) {
			request.setAttribute("isError", 1);
			if (getLanguage().equals("/en")) {
				request.setAttribute("message", "UserName already exists ");
			} else {
				request.setAttribute("message", "用户名已存在");
			}
			LOGGER.error("用户:" + request.getParameter("loginName") + "注册：用户名已存在！");
			return "forward:/supplier/addAgentPage";
		}

		String userName = "";
		String password = "";
		String encryptPassword = "";
		if (!Common.isEmpty(request.getParameter("loginName"))) {
			userName = request.getParameter("loginName");
			password = request.getParameter("password");
			encryptPassword = MD5.encrypt(request.getParameter("password"));
		}
		Integer agentLevel = Integer.parseInt(request.getParameter("agentCode"));

		if (parentId == null) {
			parentId = getCurrentSupplierId();
		}
		LOGGER.info("pop user regist set supplyType 51 " + userName);

		// 进行代理商的注册
		RemoteServiceSingleton.getInstance().getSupplierManagerService().registerAgentSupplier(agentSupplier, parentId,
				getCurrentSupplierId(), userName, password, encryptPassword, agentLevel);

		request.setAttribute("name", userName);
		LOGGER.info("注册成功：用户名：" + userName);
		// redirectAttributes.addFlashAttribute("parentId", parentId);
		return "forward:/supplier/getAgentPage?parentId=" + parentId;
	}

	/**
	 * @Description:添加市场合伙人
	 */
	@RequestMapping("/addMpAgentModel")
	public String addMpAgentModel(MultipartFile idCardFrontTo, MultipartFile idCardVersoTo,
			MultipartFile companyStoreLogoTo, String kaptcha, Map<String, Object> map, AgentSupplier agentSupplier,
			HttpServletRequest request, String uid, Long parentId) {
		LOGGER.info("注册：supplier:" + JSON.toJSONString(agentSupplier));

		// 验证码校验
		try {
			String kaptchaExpected = memcachedClient.get(uid + Constants.KAPTCHA_SESSION_KEY);
			if (!kaptcha.toLowerCase().equals(kaptchaExpected.toLowerCase())) {
				if (getLanguage().equals("/en")) {
					request.setAttribute("message", "Verification code is wrong！");
				} else {
					request.setAttribute("message", "验证码有误！");
				}
				return "forward:/supplier/addAgentPage";
			}
		} catch (Exception e) {
			LOGGER.error("注册：  memcached中取验证码出问题！", e);
		}

		// 上传文件的校验
		if (idCardFrontTo == null || idCardVersoTo == null) {
			request.setAttribute("isError", 1);
			if (getLanguage().equals("/en")) {
				request.setAttribute("message", "Missing Upload Files");
			} else {
				request.setAttribute("message", "缺少需要上传的文件");
			}
			LOGGER.error("注册：缺少需要上传的文件！");
			return "forward:/supplier/regisAgent";
		} else {
			String idCardFrontUrl = "";
			String idCardVersoUrl = "";
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
			} catch (Exception e) {
				LOGGER.error("注册：上传文件到图片服务器出错！", e);
			}
			if (null != idCardFrontTo && !"".equals(Common.getFileExt2(idCardFrontTo.getOriginalFilename()))) {
				agentSupplier.setIdCardFront(idCardFrontUrl);
			}
			if (null != idCardVersoTo && !"".equals(Common.getFileExt2(idCardVersoTo.getOriginalFilename()))) {
				agentSupplier.setIdCardVerso(idCardVersoUrl);
			}

			// 默认图片logo地址
			agentSupplier.setLogoImgurl("group1/M00/00/31/CgAAElgxxt-Aa7U1AAAjS6o85T0082.jpg");
		}

		String companyStoreLogoUrl = "";
		try {
			// 经营门头照
			if (null != companyStoreLogoTo
					&& !"".equals(Common.getFileExt2(companyStoreLogoTo.getOriginalFilename()))) {
				companyStoreLogoUrl = UploadFileUtil.uploadFile(companyStoreLogoTo.getInputStream(),
						Common.getFileExt2(companyStoreLogoTo.getOriginalFilename()), null);
			}
		} catch (Exception e) {
			LOGGER.error("注册：上传文件到图片服务器出错！", e);
		}
		if (null != companyStoreLogoTo && !"".equals(Common.getFileExt2(companyStoreLogoTo.getOriginalFilename()))) {
			agentSupplier.setCompanyStoreLogo(companyStoreLogoUrl);
		}

		// 登录pop账号的校验(判断用户名是否重复注册)
		Map<String, String> pmap = new LinkedHashMap<String, String>();
		int count = 1;
		if (!Common.isEmpty(request.getParameter("loginName"))) {
			pmap.put("loginName", request.getParameter("loginName"));
			count = RemoteServiceSingleton.getInstance().getSupplierUserManagerService().getUserByName(pmap);
		}
		if (count == 1) {
			request.setAttribute("isError", 1);
			if (getLanguage().equals("/en")) {
				request.setAttribute("message", "UserName already exists ");
			} else {
				request.setAttribute("message", "用户名已存在");
			}
			LOGGER.error("用户:" + request.getParameter("loginName") + "注册：用户名已存在！");
			return "forward:/supplier/addAgentPage";
		}

		String userName = "";
		String password = "";
		String encryptPassword = "";
		if (!Common.isEmpty(request.getParameter("loginName"))) {
			userName = request.getParameter("loginName");
			password = request.getParameter("password");
			encryptPassword = MD5.encrypt(request.getParameter("password"));
		}
		Integer agentLevel = Integer.parseInt(request.getParameter("agentCode"));

		if (parentId == null) {
			parentId = getCurrentSupplierId();
		}
		LOGGER.info("pop user regist set supplyType 51 " + userName);

		// 进行部分数据的添加
		agentSupplier.setName(agentSupplier.getUserName());
		agentSupplier.setNameJC(agentSupplier.getUserName());
		if (agentSupplier.getRegisterAddress() != null) {
			agentSupplier.setAddress(agentSupplier.getRegisterAddress());
		}
		agentSupplier.setContact(agentSupplier.getUserName());
		agentSupplier.setLegalPerson(agentSupplier.getUserName());
		if (agentSupplier.getUserMobile() != null) {
			agentSupplier.setLegalPersonPhone(agentSupplier.getUserMobile());
			agentSupplier.setPhone(agentSupplier.getUserMobile());
			agentSupplier.setContactTel(agentSupplier.getUserMobile());
		}

		// 进行代理商的注册
		RemoteServiceSingleton.getInstance().getSupplierManagerService().registerAgentSupplier(agentSupplier, parentId,
				getCurrentSupplierId(), userName, password, encryptPassword, agentLevel);

		request.setAttribute("name", userName);
		LOGGER.info("注册成功：用户名：" + userName);
		return "forward:/supplier/getAgentPage?parentId=" + parentId;
	}

	// 分界线(以下为以前代码)
	// ================================================================================================================================
	// ================================================================================================================================
	// ================================================================================================================================

	/**
	 * 跳转注册页面.
	 * 
	 * @param map
	 *            map
	 * @return 添加商户
	 */
	@RequestMapping("/registUI")
	public String toRegistUI(Map<String, Object> map, HttpServletRequest request) {
		map.put("template_url", request.getContextPath() + "/supplier/downloadTemp");
		List<TdCatePub> childrenCategoryList = new ArrayList<TdCatePub>();
		List<TcCountry> countries = new ArrayList<TcCountry>();
		if (RemoteServiceSingleton.getInstance().getCategoryServiceRpc() != null) {
			try {
				childrenCategoryList = RemoteServiceSingleton.getInstance().getCategoryServiceRpc()
						.getTopCategoryList();
				countries = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().getTcCountries();
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
		List<String> callingCodeCn = new ArrayList<String>();
		List<String> callingCodeEn = new ArrayList<String>();
		List<String> countryNameEn = new ArrayList<String>();
		List<String> countryNameCn = new ArrayList<String>();
		for (TcCountry country : countries) {
			countryNameCn.add(country.getName());
			countryNameEn.add(country.getDescription());
			callingCodeCn.add(Common.isEmpty(country.getCallingcode()) ? "" : "+" + country.getCallingcode());
			callingCodeEn.add(Common.isEmpty(country.getCallingcode()) ? "" : "+" + country.getCallingcode());
		}
		String uid = UUID.randomUUID().toString();
		map.put("uid", uid);
		try {
			memcachedClient.set(uid, 60 * 60, uid);
		} catch (Exception e) {
			LOGGER.error("memcachedClient获取uid报错", e);
			e.printStackTrace();
		}
		if ("/en".equals(getLanguage())) {
			map.put("category", JSONObject.toJSONString(categoryNameEn));
			map.put("country", countryNameEn);
			map.put("callingCode", callingCodeCn);
		} else {
			map.put("category", JSONObject.toJSONString(categoryNameCn));
			map.put("country", countryNameCn);
			map.put("callingCode", callingCodeEn);
		}
		return "/user/regist";
	}

	/**
	 * 普通线上商户的注册
	 * 
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping("/registGeneral")
	public String toRegistGeneral(Map<String, Object> map, HttpServletRequest request) {
		map.put("template_url", request.getContextPath() + "/supplier/downloadTemp");
		List<TdCatePub> childrenCategoryList = new ArrayList<TdCatePub>();
		List<TcCountry> countries = new ArrayList<TcCountry>();
		if (RemoteServiceSingleton.getInstance().getCategoryServiceRpc() != null) {
			try {
				childrenCategoryList = RemoteServiceSingleton.getInstance().getCategoryServiceRpc()
						.getTopCategoryList();
				countries = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().getTcCountries();
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
		List<String> callingCodeCn = new ArrayList<String>();
		List<String> callingCodeEn = new ArrayList<String>();
		List<String> countryNameEn = new ArrayList<String>();
		List<String> countryNameCn = new ArrayList<String>();
		for (TcCountry country : countries) {
			countryNameCn.add(country.getName());
			countryNameEn.add(country.getDescription());
			callingCodeCn.add(Common.isEmpty(country.getCallingcode()) ? "" : "+" + country.getCallingcode());
			callingCodeEn.add(Common.isEmpty(country.getCallingcode()) ? "" : "+" + country.getCallingcode());
		}
		String uid = UUID.randomUUID().toString();
		map.put("uid", uid);
		try {
			memcachedClient.set(uid, 60 * 60, uid);
		} catch (Exception e) {
			LOGGER.error("memcachedClient获取uid报错", e);
			e.printStackTrace();
		}
		if ("/en".equals(getLanguage())) {
			map.put("category", JSONObject.toJSONString(categoryNameEn));
			map.put("country", countryNameEn);
			map.put("callingCode", callingCodeCn);
		} else {
			map.put("category", JSONObject.toJSONString(categoryNameCn));
			map.put("country", countryNameCn);
			map.put("callingCode", callingCodeEn);
		}
		return "/user/registGeneral";
	}

	/**
	 * 区域运营商注册
	 * 
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping("/registPartner")
	public String toRegistPartner(Map<String, Object> map, HttpServletRequest request) {
		map.put("template_url", request.getContextPath() + "/supplier/downloadTemp");
		List<TdCatePub> childrenCategoryList = new ArrayList<TdCatePub>();
		List<TcCountry> countries = new ArrayList<TcCountry>();
		if (RemoteServiceSingleton.getInstance().getCategoryServiceRpc() != null) {
			try {
				childrenCategoryList = RemoteServiceSingleton.getInstance().getCategoryServiceRpc()
						.getTopCategoryList();
				countries = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().getTcCountries();
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
		List<String> callingCodeCn = new ArrayList<String>();
		List<String> callingCodeEn = new ArrayList<String>();
		List<String> countryNameEn = new ArrayList<String>();
		List<String> countryNameCn = new ArrayList<String>();
		for (TcCountry country : countries) {
			countryNameCn.add(country.getName());
			countryNameEn.add(country.getDescription());
			callingCodeCn.add(Common.isEmpty(country.getCallingcode()) ? "" : "+" + country.getCallingcode());
			callingCodeEn.add(Common.isEmpty(country.getCallingcode()) ? "" : "+" + country.getCallingcode());
		}
		String uid = UUID.randomUUID().toString();
		map.put("uid", uid);
		try {
			memcachedClient.set(uid, 60 * 60, uid);
		} catch (Exception e) {
			LOGGER.error("memcachedClient获取uid报错", e);
			e.printStackTrace();
		}
		if ("/en".equals(getLanguage())) {
			map.put("category", JSONObject.toJSONString(categoryNameEn));
			map.put("country", countryNameEn);
			map.put("callingCode", callingCodeCn);
		} else {
			map.put("category", JSONObject.toJSONString(categoryNameCn));
			map.put("country", countryNameCn);
			map.put("callingCode", callingCodeEn);
		}
		return "/user/registPartner";
	}

	/**
	 * 家庭账户注册页面
	 * 
	 * @param map
	 *            map
	 * @return 添加商户
	 */
	@RequestMapping("/registUI2")
	public String toRegistUI2(Map<String, Object> map, HttpServletRequest request, long userid) {
		/**
		 * 调接口判断用户注册够不够资格
		 */
		// 查询用户是否购买家庭号6.7
		HomeNumRecordDto homeNumRecordDto;
		try {
			homeNumRecordDto = homeNumRecordService.selectRecordByUserid((int) userid);
			Integer jiaTingStatus = homeNumRecordDto.getStatus();
			if (jiaTingStatus == 0) {
				request.setAttribute("message", "您没有资格注册 ");
				return "redirect:http://www.zhongjumall.com";
			}
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		int i = RemoteServiceSingleton.getInstance().getSupplierManagerService().selectCountUser(userid);
		if (i >= 1) {
			request.setAttribute("message", "不能重复注册！");
			return "/user/login";

		}
		map.put("template_url", request.getContextPath() + "/supplier/downloadTemp");
		List<TdCatePub> childrenCategoryList = new ArrayList<TdCatePub>();
		List<TcCountry> countries = new ArrayList<TcCountry>();
		User user = RemoteServiceSingleton.getInstance().getUserService().findUserById(userid);
		String mobile = user.getMobile();
		request.setAttribute("moblie", mobile);
		if (RemoteServiceSingleton.getInstance().getCategoryServiceRpc() != null) {
			try {
				childrenCategoryList = RemoteServiceSingleton.getInstance().getCategoryServiceRpc()
						.getTopCategoryList();
				countries = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().getTcCountries();
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
		List<String> callingCodeCn = new ArrayList<String>();
		List<String> callingCodeEn = new ArrayList<String>();
		List<String> countryNameEn = new ArrayList<String>();
		List<String> countryNameCn = new ArrayList<String>();
		for (TcCountry country : countries) {
			countryNameCn.add(country.getName());
			countryNameEn.add(country.getDescription());
			callingCodeCn.add(Common.isEmpty(country.getCallingcode()) ? "" : "+" + country.getCallingcode());
			callingCodeEn.add(Common.isEmpty(country.getCallingcode()) ? "" : "+" + country.getCallingcode());
		}
		String uid = UUID.randomUUID().toString();
		map.put("uid", uid);
		try {
			memcachedClient.set(uid, 60 * 60, uid);
		} catch (Exception e) {
			LOGGER.error("memcachedClient获取uid报错", e);
			e.printStackTrace();
		}
		if ("/en".equals(getLanguage())) {
			map.put("category", JSONObject.toJSONString(categoryNameEn));
			map.put("country", countryNameEn);
			map.put("callingCode", callingCodeCn);
		} else {
			map.put("category", JSONObject.toJSONString(categoryNameCn));
			map.put("country", countryNameCn);
			map.put("callingCode", callingCodeEn);
			request.setAttribute("userid", userid);
		}
		return "/user/regist2";
	}

	/**
	 * 企业号注册页面
	 */
	@RequestMapping("/registUI3")
	public String toRegistUI3(Map<String, Object> map, HttpServletRequest request, long userid) {
		/**
		 * 调接口判断用户注册够不够资格
		 */
		// 查询用户是否购买家庭号6.7
		HomeNumRecordDto homeNumRecordDto;
		/*
		 * try { homeNumRecordDto =
		 * homeNumRecordService.selectRecordByUserid((int) userid); Integer
		 * jiaTingStatus = homeNumRecordDto.getStatus(); if(jiaTingStatus==0){
		 * request.setAttribute("message","您没有资格注册 "); return
		 * "redirect:http://www.zhongjumall.com"; } } catch (Exception e1) {
		 * 
		 * e1.printStackTrace(); }
		 */
		SupplierNumRecordDto supplierNumRecordDto;
		try {
			supplierNumRecordDto = supplierNumRecordService.selectRecordByUserid((int) userid);
			Integer jiaTingStatus = supplierNumRecordDto.getStatus();
			if (jiaTingStatus == 0) {
				request.setAttribute("message", "您没有资格注册 ");
				return "redirect:http://www.zhongjumall.com";
			}
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		int i = RemoteServiceSingleton.getInstance().getSupplierManagerService().selectCountUser(userid);
		if (i >= 1) {
			request.setAttribute("message", "不能重复注册！");
			return "/user/login";

		}
		map.put("template_url", request.getContextPath() + "/supplier/downloadTemp");
		List<TdCatePub> childrenCategoryList = new ArrayList<TdCatePub>();
		List<TcCountry> countries = new ArrayList<TcCountry>();
		User user = RemoteServiceSingleton.getInstance().getUserService().findUserById(userid);
		String mobile = user.getMobile();
		request.setAttribute("moblie", mobile);
		if (RemoteServiceSingleton.getInstance().getCategoryServiceRpc() != null) {
			try {
				childrenCategoryList = RemoteServiceSingleton.getInstance().getCategoryServiceRpc()
						.getTopCategoryList();
				countries = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().getTcCountries();
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
		List<String> callingCodeCn = new ArrayList<String>();
		List<String> callingCodeEn = new ArrayList<String>();
		List<String> countryNameEn = new ArrayList<String>();
		List<String> countryNameCn = new ArrayList<String>();
		for (TcCountry country : countries) {
			countryNameCn.add(country.getName());
			countryNameEn.add(country.getDescription());
			callingCodeCn.add(Common.isEmpty(country.getCallingcode()) ? "" : "+" + country.getCallingcode());
			callingCodeEn.add(Common.isEmpty(country.getCallingcode()) ? "" : "+" + country.getCallingcode());
		}
		String uid = UUID.randomUUID().toString();
		map.put("uid", uid);
		try {
			memcachedClient.set(uid, 60 * 60, uid);
		} catch (Exception e) {
			LOGGER.error("memcachedClient获取uid报错", e);
			e.printStackTrace();
		}
		if ("/en".equals(getLanguage())) {
			map.put("category", JSONObject.toJSONString(categoryNameEn));
			map.put("country", countryNameEn);
			map.put("callingCode", callingCodeCn);
		} else {
			map.put("category", JSONObject.toJSONString(categoryNameCn));
			map.put("country", countryNameCn);
			map.put("callingCode", callingCodeEn);
			request.setAttribute("userid", userid);
		}
		return "/user/regist3";
	}

	/**
	 * @Description:添加商户注册.
	 * @param myfile
	 *            MultipartFile
	 * @param myfile1
	 *            MultipartFile
	 * @param map
	 *            map
	 * @param supplier
	 *            Supplier
	 * @param request
	 *            HttpServletRequest
	 * @return regist_success
	 */
	@RequestMapping("/regist")
	public String regist(MultipartFile myfile, MultipartFile myfile1, String kaptcha, Map<String, Object> map,
			Supplier supplier, HttpServletRequest request, String uid, String phoneCode, MultipartFile myfile2) {
		LOGGER.info("注册：supplier:" + JSON.toJSONString(supplier));
		try {
			String kaptchaExpected = memcachedClient.get(uid + Constants.KAPTCHA_SESSION_KEY);
			if (!kaptcha.toLowerCase().equals(kaptchaExpected.toLowerCase())) {
				if (getLanguage().equals("/en")) {
					request.setAttribute("message", "Verification code is wrong！");
				} else {
					request.setAttribute("message", "验证码有误！");
				}
				return "/user/regist";
			}
		} catch (Exception e) {
			LOGGER.error("注册：  memcached中取验证码出问题！", e);
		}
		if (null == myfile) {
			request.setAttribute("isError", 1);
			if (getLanguage().equals("/en")) {
				request.setAttribute("message", "Missing Upload Files");
			} else {
				request.setAttribute("message", "缺少需要上传的文件");
			}
			LOGGER.error("注册：缺少需要上传的文件！");
			return "/user/regist";
		} else {
			// System.out.println(
			// Common.getFileExt2(myfile1.getOriginalFilename()));
			String myfileUrl = "";
			String myfile1Url = "";
			String myfile2Url = "";
			try {
				myfileUrl = UploadFileUtil.uploadFile(myfile.getInputStream(),
						Common.getFileExt2(myfile.getOriginalFilename()), null);
				if (null != myfile1 && !"".equals(Common.getFileExt2(myfile1.getOriginalFilename()))) {
					myfile1Url = UploadFileUtil.uploadFile(myfile1.getInputStream(),
							Common.getFileExt2(myfile1.getOriginalFilename()), null);
				}
				if (null != myfile2 && !"".equals(Common.getFileExt2(myfile2.getOriginalFilename()))) {
					myfile2Url = UploadFileUtil.uploadFile(myfile2.getInputStream(),
							Common.getFileExt2(myfile2.getOriginalFilename()), null);
				}
			} catch (Exception e) {
				LOGGER.error("注册：上传文件到图片服务器出错！", e);
			}
			supplier.setCompanyLegitimacyUrl(myfileUrl);
			if (null != myfile1 && !"".equals(Common.getFileExt2(myfile1.getOriginalFilename()))) {
				supplier.setCompanyDetailedUrl(myfile1Url);
			}
			if (null != myfile2 && !"".equals(Common.getFileExt2(myfile2.getOriginalFilename()))) {
				supplier.setLogoImgurl(myfile2Url);
			} else {
				supplier.setLogoImgurl("group1/M00/00/31/CgAAElgxxt-Aa7U1AAAjS6o85T0082.jpg"); // 默认图片logo地址
			}
		}
		SupplierUser user = new SupplierUser();
		if (!Common.isEmpty(request.getParameter("loginName"))) {
			user.setIsAdmin(1);
			user.setStatus(1);
			user.setLoginName(request.getParameter("loginName"));
			user.setPassword(MD5.encrypt(request.getParameter("password")));

			user.setRecordPwd(request.getParameter("password")); // 记录明文，为了unicorn创建账号发送短信用

			Map<String, String> pmap = new LinkedHashMap<String, String>();
			int count = 1;
			if (!Common.isEmpty(request.getParameter("loginName"))) {
				pmap.put("loginName", request.getParameter("loginName"));
				count = RemoteServiceSingleton.getInstance().getSupplierUserManagerService().getUserByName(pmap);
			}
			if (count == 1) {
				request.setAttribute("isError", 1);
				if (getLanguage().equals("/en")) {
					request.setAttribute("message", "UserName already exists ");
				} else {
					request.setAttribute("message", "用户名已存在");
				}
				LOGGER.error("用户:" + request.getParameter("loginName") + "注册：用户名已存在！");
				return "/user/regist";
			}
		}
		supplier.setPhone(phoneCode + supplier.getPhone());
		supplier.setStatus(0);
		supplier.setCreateTime(new Date());
		// supplier.setName(request.getParameter("companyName"));
		supplier.setName(request.getParameter("companyName"));
		supplier.setFax(request.getParameter("fax"));
		if (Common.isEmpty(request.getParameter("post"))) {
			supplier.setPost(Common.stringToInteger(request.getParameter("post")));
		}
		// 设置pop用户为51
		supplier.setSupplyType(51);
		supplier.setType(-1); // 注册的时候默认给-1值
		supplier.setHqqPwd(MD5.encrypt(supplier.getHqqPwd())); // 红旗券支付密码
		supplier.setActiveStatus(-1); // 该企业注册未激活置为-1
		LOGGER.info("pop user regist set supplyType 51 " + user.getLoginName());

		User tjUser = null;
		Supplier sjSupplier = null;
		if (StringUtils.isEmpty(supplier.getSjSupplierId())) { // 如果上级企业代码为空
																// 置为邀请码用户里的supplierId
			/*
			 * tjUser = RemoteServiceSingleton.getInstance().getUserService().
			 * findUserByMobile(supplier.getUserTj()); if(tjUser != null &&
			 * StringUtils.isNotEmpty(tjUser.getSupplierId())){ Supplier
			 * tjSupplier =
			 * RemoteServiceSingleton.getInstance().getSupplierManagerService()
			 * .findSupplier(Long.valueOf(tjUser.getSupplierId()));
			 * if(tjSupplier != null && tjSupplier.getType() == 1){ //属于子公司
			 * supplier.setSjSupplierId(tjSupplier.getSupplierId()+""); }else{
			 * Supplier defaultSupplier =
			 * RemoteServiceSingleton.getInstance().getSupplierManagerService()
			 * .getSupplierBySupplierCode("SQ69"); if(defaultSupplier != null){
			 * supplier.setSjSupplierId(defaultSupplier.getSupplierId()+""); } }
			 * }else{ Supplier defaultSupplier =
			 * RemoteServiceSingleton.getInstance().getSupplierManagerService()
			 * .getSupplierBySupplierCode("SQ69"); if(defaultSupplier != null){
			 * supplier.setSjSupplierId(defaultSupplier.getSupplierId()+""); } }
			 */
		} else { // 不为空根据企业代码获取相对应的supplierId
			sjSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
					.getSupplierBySupplierCode(supplier.getSjSupplierId());
			if (sjSupplier != null) {
				supplier.setSjSupplierId(sjSupplier.getSupplierId() + "");
			}
		}
		/*
		 * if(StringUtils.isEmpty(supplier.getSjSupplierId())){ //如果上级企业代码为空
		 * 置为邀请码用户里的supplierId tjUser =
		 * RemoteServiceSingleton.getInstance().getUserService().
		 * findUserByMobile(supplier.getUserTj()); if(tjUser != null){
		 * supplier.setSjSupplierId(tjUser.getSupplierId()); } }else{
		 * //不为空根据企业代码获取相对应的supplierId sjSupplier =
		 * RemoteServiceSingleton.getInstance().getSupplierManagerService()
		 * .getSupplierBySupplierCode(supplier.getSjSupplierId()); if(sjSupplier
		 * != null){ supplier.setSjSupplierId(sjSupplier.getSupplierId()+""); }
		 * }
		 */
		Long supplierId = RemoteServiceSingleton.getInstance().getSupplierManagerService().insert(supplier);

		SupplierProduct product = new SupplierProduct();
		getObjectFromRequest(product, request);
		SupplierPartner partner = new SupplierPartner();
		partner.setName(request.getParameter("p_companyName"));
		partner.setAddress(request.getParameter("p_companyName"));
		partner.setContact(request.getParameter("p_companyContact"));
		partner.setEmail(request.getParameter("p_companyMail"));
		partner.setTelephone(request.getParameter("p_companyTel"));
		SupplierPartnerArea a = new SupplierPartnerArea();
		RemoteServiceSingleton.getInstance().getSupplierManagerService().register(supplierId, user, product, partner,
				a);
		request.setAttribute("name", user.getLoginName());
		String name = "";
		try {
			name = DES.encrypt(user.getLoginName(), Constants.CCIGMALL);
		} catch (Exception e) {
			LOGGER.error("注册：用户名回传成功页面出错!用户名：" + user.getLoginName(), e);
		}
		// request.setAttribute("language",getLanguage().substring(1));
		LOGGER.info("注册成功：用户名：" + user.getLoginName());
		return "redirect:/supplier/regist_success?sid=" + name;
	}

	@RequestMapping("/registPartnerAdd")
	public String registPartnerAdd(MultipartFile myfile, MultipartFile myfile1, String kaptcha, Map<String, Object> map,
			Supplier supplier, HttpServletRequest request, String uid, String phoneCode, MultipartFile myfile2,
			int registType, long productProvinceid, long productCityid, long productAreaid) {
		LOGGER.info("注册：supplier:" + JSON.toJSONString(supplier));
		try {
			String kaptchaExpected = memcachedClient.get(uid + Constants.KAPTCHA_SESSION_KEY);
			if (!kaptcha.toLowerCase().equals(kaptchaExpected.toLowerCase())) {
				if (getLanguage().equals("/en")) {
					request.setAttribute("message", "Verification code is wrong！");
				} else {
					request.setAttribute("message", "验证码有误！");
				}
				return "/user/regist";
			}
		} catch (Exception e) {
			LOGGER.error("注册：  memcached中取验证码出问题！", e);
		}
		if (null == myfile) {
			request.setAttribute("isError", 1);
			if (getLanguage().equals("/en")) {
				request.setAttribute("message", "Missing Upload Files");
			} else {
				request.setAttribute("message", "缺少需要上传的文件");
			}
			LOGGER.error("注册：缺少需要上传的文件！");
			return "/user/regist";
		} else {
			// System.out.println(
			// Common.getFileExt2(myfile1.getOriginalFilename()));
			String myfileUrl = "";
			String myfile1Url = "";
			String myfile2Url = "";
			try {
				myfileUrl = UploadFileUtil.uploadFile(myfile.getInputStream(),
						Common.getFileExt2(myfile.getOriginalFilename()), null);
				if (null != myfile1 && !"".equals(Common.getFileExt2(myfile1.getOriginalFilename()))) {
					myfile1Url = UploadFileUtil.uploadFile(myfile1.getInputStream(),
							Common.getFileExt2(myfile1.getOriginalFilename()), null);
				}
				if (null != myfile2 && !"".equals(Common.getFileExt2(myfile2.getOriginalFilename()))) {
					myfile2Url = UploadFileUtil.uploadFile(myfile2.getInputStream(),
							Common.getFileExt2(myfile2.getOriginalFilename()), null);
				}
			} catch (Exception e) {
				LOGGER.error("注册：上传文件到图片服务器出错！", e);
			}
			supplier.setCompanyLegitimacyUrl(myfileUrl);
			if (null != myfile1 && !"".equals(Common.getFileExt2(myfile1.getOriginalFilename()))) {
				supplier.setCompanyDetailedUrl(myfile1Url);
			}
			if (null != myfile2 && !"".equals(Common.getFileExt2(myfile2.getOriginalFilename()))) {
				supplier.setLogoImgurl(myfile2Url);
			} else {
				supplier.setLogoImgurl("group1/M00/00/31/CgAAElgxxt-Aa7U1AAAjS6o85T0082.jpg"); // 默认图片logo地址
			}
		}
		SupplierUser user = new SupplierUser();
		if (!Common.isEmpty(request.getParameter("loginName"))) {
			user.setIsAdmin(1);
			user.setStatus(1);
			user.setLoginName(request.getParameter("loginName"));
			user.setPassword(MD5.encrypt(request.getParameter("password")));

			user.setRecordPwd(request.getParameter("password")); // 记录明文，为了unicorn创建账号发送短信用

			Map<String, String> pmap = new LinkedHashMap<String, String>();
			int count = 1;
			if (!Common.isEmpty(request.getParameter("loginName"))) {
				pmap.put("loginName", request.getParameter("loginName"));
				count = RemoteServiceSingleton.getInstance().getSupplierUserManagerService().getUserByName(pmap);
			}
			if (count == 1) {
				request.setAttribute("isError", 1);
				if (getLanguage().equals("/en")) {
					request.setAttribute("message", "UserName already exists ");
				} else {
					request.setAttribute("message", "用户名已存在");
				}
				LOGGER.error("用户:" + request.getParameter("loginName") + "注册：用户名已存在！");
				return "/user/regist";
			}
		}
		supplier.setPhone(phoneCode + supplier.getPhone());
		supplier.setStatus(0);
		supplier.setCreateTime(new Date());
		// supplier.setName(request.getParameter("companyName"));
		supplier.setName(request.getParameter("companyName"));
		supplier.setFax(request.getParameter("fax"));
		if (Common.isEmpty(request.getParameter("post"))) {
			supplier.setPost(Common.stringToInteger(request.getParameter("post")));
		}
		// 设置pop用户为51
		supplier.setSupplyType(51);
		supplier.setType(-1); // 注册的时候默认给-1值
		supplier.setHqqPwd(MD5.encrypt(supplier.getHqqPwd())); // 红旗券支付密码
		supplier.setActiveStatus(-1); // 该企业注册未激活置为-1
		if (registType == 1) {
			// 区域运营商
			supplier.setOrganizationType(11);
		}
		if (registType == 2) {
			supplier.setOrganizationType(12);
		}
		LOGGER.info("pop user regist set supplyType 51 " + user.getLoginName());

		User tjUser = null;
		Supplier sjSupplier = null;
		if (StringUtils.isEmpty(supplier.getSjSupplierId())) { // 如果上级企业代码为空
																// 置为邀请码用户里的supplierId
			/*
			 * tjUser = RemoteServiceSingleton.getInstance().getUserService().
			 * findUserByMobile(supplier.getUserTj()); if(tjUser != null &&
			 * StringUtils.isNotEmpty(tjUser.getSupplierId())){ Supplier
			 * tjSupplier =
			 * RemoteServiceSingleton.getInstance().getSupplierManagerService()
			 * .findSupplier(Long.valueOf(tjUser.getSupplierId()));
			 * if(tjSupplier != null && tjSupplier.getType() == 1){ //属于子公司
			 * supplier.setSjSupplierId(tjSupplier.getSupplierId()+""); }else{
			 * Supplier defaultSupplier =
			 * RemoteServiceSingleton.getInstance().getSupplierManagerService()
			 * .getSupplierBySupplierCode("SQ69"); if(defaultSupplier != null){
			 * supplier.setSjSupplierId(defaultSupplier.getSupplierId()+""); } }
			 * }else{ Supplier defaultSupplier =
			 * RemoteServiceSingleton.getInstance().getSupplierManagerService()
			 * .getSupplierBySupplierCode("SQ69"); if(defaultSupplier != null){
			 * supplier.setSjSupplierId(defaultSupplier.getSupplierId()+""); } }
			 */
		} else { // 不为空根据企业代码获取相对应的supplierId
			sjSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
					.getSupplierBySupplierCode(supplier.getSjSupplierId());
			if (sjSupplier != null) {
				supplier.setSjSupplierId(sjSupplier.getSupplierId() + "");
			}
		}
		/*
		 * if(StringUtils.isEmpty(supplier.getSjSupplierId())){ //如果上级企业代码为空
		 * 置为邀请码用户里的supplierId tjUser =
		 * RemoteServiceSingleton.getInstance().getUserService().
		 * findUserByMobile(supplier.getUserTj()); if(tjUser != null){
		 * supplier.setSjSupplierId(tjUser.getSupplierId()); } }else{
		 * //不为空根据企业代码获取相对应的supplierId sjSupplier =
		 * RemoteServiceSingleton.getInstance().getSupplierManagerService()
		 * .getSupplierBySupplierCode(supplier.getSjSupplierId()); if(sjSupplier
		 * != null){ supplier.setSjSupplierId(sjSupplier.getSupplierId()+""); }
		 * }
		 */
		Long supplierId = RemoteServiceSingleton.getInstance().getSupplierManagerService().insert(supplier);
		SupplierProduct product = new SupplierProduct();
		getObjectFromRequest(product, request);
		SupplierPartner partner = new SupplierPartner();
		partner.setName(request.getParameter("p_companyName"));
		partner.setAddress(request.getParameter("p_companyName"));
		partner.setContact(request.getParameter("p_companyContact"));
		partner.setEmail(request.getParameter("p_companyMail"));
		partner.setTelephone(request.getParameter("p_companyTel"));
		SupplierPartnerArea partnerArea = new SupplierPartnerArea();
		partnerArea.setCityId(productCityid);
		partnerArea.setCountyId(productAreaid);
		partnerArea.setProvinceId(productProvinceid);
		partnerArea.setPartnerType(registType);
		partnerArea.setStatus(1);
		partnerArea.setType(1);
		RemoteServiceSingleton.getInstance().getSupplierManagerService().register(supplierId, user, product, partner,
				partnerArea);
		request.setAttribute("name", user.getLoginName());
		String name = "";
		try {
			name = DES.encrypt(user.getLoginName(), Constants.CCIGMALL);
		} catch (Exception e) {
			LOGGER.error("注册：用户名回传成功页面出错!用户名：" + user.getLoginName(), e);
		}
		// request.setAttribute("language",getLanguage().substring(1));
		LOGGER.info("注册成功：用户名：" + user.getLoginName());
		return "redirect:/supplier/regist_success?sid=" + name;
	}

	@RequestMapping("/registGeneralAdd")
	public String registGeneralAdd(MultipartFile myfile, MultipartFile myfile1, String kaptcha, Map<String, Object> map,
			Supplier supplier, HttpServletRequest request, String uid, String phoneCode, MultipartFile myfile2) {
		LOGGER.info("注册：supplier:" + JSON.toJSONString(supplier));
		try {
			String kaptchaExpected = memcachedClient.get(uid + Constants.KAPTCHA_SESSION_KEY);
			if (!kaptcha.toLowerCase().equals(kaptchaExpected.toLowerCase())) {
				if (getLanguage().equals("/en")) {
					request.setAttribute("message", "Verification code is wrong！");
				} else {
					request.setAttribute("message", "验证码有误！");
				}
				return "/user/regist";
			}
		} catch (Exception e) {
			LOGGER.error("注册：  memcached中取验证码出问题！", e);
		}
		if (null == myfile) {
			request.setAttribute("isError", 1);
			if (getLanguage().equals("/en")) {
				request.setAttribute("message", "Missing Upload Files");
			} else {
				request.setAttribute("message", "缺少需要上传的文件");
			}
			LOGGER.error("注册：缺少需要上传的文件！");
			return "/user/regist";
		} else {
			// System.out.println(
			// Common.getFileExt2(myfile1.getOriginalFilename()));
			String myfileUrl = "";
			String myfile1Url = "";
			String myfile2Url = "";
			try {
				myfileUrl = UploadFileUtil.uploadFile(myfile.getInputStream(),
						Common.getFileExt2(myfile.getOriginalFilename()), null);
				if (null != myfile1 && !"".equals(Common.getFileExt2(myfile1.getOriginalFilename()))) {
					myfile1Url = UploadFileUtil.uploadFile(myfile1.getInputStream(),
							Common.getFileExt2(myfile1.getOriginalFilename()), null);
				}
				if (null != myfile2 && !"".equals(Common.getFileExt2(myfile2.getOriginalFilename()))) {
					myfile2Url = UploadFileUtil.uploadFile(myfile2.getInputStream(),
							Common.getFileExt2(myfile2.getOriginalFilename()), null);
				}
			} catch (Exception e) {
				LOGGER.error("注册：上传文件到图片服务器出错！", e);
			}
			supplier.setCompanyLegitimacyUrl(myfileUrl);
			if (null != myfile1 && !"".equals(Common.getFileExt2(myfile1.getOriginalFilename()))) {
				supplier.setCompanyDetailedUrl(myfile1Url);
			}
			if (null != myfile2 && !"".equals(Common.getFileExt2(myfile2.getOriginalFilename()))) {
				supplier.setLogoImgurl(myfile2Url);
			} else {
				supplier.setLogoImgurl("group1/M00/00/31/CgAAElgxxt-Aa7U1AAAjS6o85T0082.jpg"); // 默认图片logo地址
			}
		}
		SupplierUser user = new SupplierUser();
		if (!Common.isEmpty(request.getParameter("loginName"))) {
			user.setIsAdmin(1);
			user.setStatus(1);
			user.setLoginName(request.getParameter("loginName"));
			user.setPassword(MD5.encrypt(request.getParameter("password")));

			user.setRecordPwd(request.getParameter("password")); // 记录明文，为了unicorn创建账号发送短信用

			Map<String, String> pmap = new LinkedHashMap<String, String>();
			int count = 1;
			if (!Common.isEmpty(request.getParameter("loginName"))) {
				pmap.put("loginName", request.getParameter("loginName"));
				count = RemoteServiceSingleton.getInstance().getSupplierUserManagerService().getUserByName(pmap);
			}
			if (count == 1) {
				request.setAttribute("isError", 1);
				if (getLanguage().equals("/en")) {
					request.setAttribute("message", "UserName already exists ");
				} else {
					request.setAttribute("message", "用户名已存在");
				}
				LOGGER.error("用户:" + request.getParameter("loginName") + "注册：用户名已存在！");
				return "/user/regist";
			}
		}
		supplier.setPhone(phoneCode + supplier.getPhone());
		supplier.setStatus(0);
		supplier.setCreateTime(new Date());
		// supplier.setName(request.getParameter("companyName"));
		supplier.setName(request.getParameter("companyName"));
		supplier.setFax(request.getParameter("fax"));
		if (Common.isEmpty(request.getParameter("post"))) {
			supplier.setPost(Common.stringToInteger(request.getParameter("post")));
		}
		// 设置pop用户为51
		supplier.setSupplyType(51);
		supplier.setType(-1); // 注册的时候默认给-1值
		supplier.setHqqPwd(MD5.encrypt(supplier.getHqqPwd())); // 红旗券支付密码
		supplier.setActiveStatus(-1); // 该企业注册未激活置为-1
		supplier.setOrganizationType(0);
		LOGGER.info("pop user regist set supplyType 51 " + user.getLoginName());

		User tjUser = null;
		Supplier sjSupplier = null;
		if (StringUtils.isEmpty(supplier.getSjSupplierId())) { // 如果上级企业代码为空
																// 置为邀请码用户里的supplierId
			/*
			 * tjUser = RemoteServiceSingleton.getInstance().getUserService().
			 * findUserByMobile(supplier.getUserTj()); if(tjUser != null &&
			 * StringUtils.isNotEmpty(tjUser.getSupplierId())){ Supplier
			 * tjSupplier =
			 * RemoteServiceSingleton.getInstance().getSupplierManagerService()
			 * .findSupplier(Long.valueOf(tjUser.getSupplierId()));
			 * if(tjSupplier != null && tjSupplier.getType() == 1){ //属于子公司
			 * supplier.setSjSupplierId(tjSupplier.getSupplierId()+""); }else{
			 * Supplier defaultSupplier =
			 * RemoteServiceSingleton.getInstance().getSupplierManagerService()
			 * .getSupplierBySupplierCode("SQ69"); if(defaultSupplier != null){
			 * supplier.setSjSupplierId(defaultSupplier.getSupplierId()+""); } }
			 * }else{ Supplier defaultSupplier =
			 * RemoteServiceSingleton.getInstance().getSupplierManagerService()
			 * .getSupplierBySupplierCode("SQ69"); if(defaultSupplier != null){
			 * supplier.setSjSupplierId(defaultSupplier.getSupplierId()+""); } }
			 */
		} else { // 不为空根据企业代码获取相对应的supplierId
			sjSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
					.getSupplierBySupplierCode(supplier.getSjSupplierId());
			if (sjSupplier != null) {
				supplier.setSjSupplierId(sjSupplier.getSupplierId() + "");
			}
		}
		/*
		 * if(StringUtils.isEmpty(supplier.getSjSupplierId())){ //如果上级企业代码为空
		 * 置为邀请码用户里的supplierId tjUser =
		 * RemoteServiceSingleton.getInstance().getUserService().
		 * findUserByMobile(supplier.getUserTj()); if(tjUser != null){
		 * supplier.setSjSupplierId(tjUser.getSupplierId()); } }else{
		 * //不为空根据企业代码获取相对应的supplierId sjSupplier =
		 * RemoteServiceSingleton.getInstance().getSupplierManagerService()
		 * .getSupplierBySupplierCode(supplier.getSjSupplierId()); if(sjSupplier
		 * != null){ supplier.setSjSupplierId(sjSupplier.getSupplierId()+""); }
		 * }
		 */
		Long supplierId = RemoteServiceSingleton.getInstance().getSupplierManagerService().insert(supplier);
		SupplierProduct product = new SupplierProduct();
		getObjectFromRequest(product, request);
		SupplierPartner partner = new SupplierPartner();
		partner.setName(request.getParameter("p_companyName"));
		partner.setAddress(request.getParameter("p_companyName"));
		partner.setContact(request.getParameter("p_companyContact"));
		partner.setEmail(request.getParameter("p_companyMail"));
		partner.setTelephone(request.getParameter("p_companyTel"));
		SupplierPartnerArea partnerArea = new SupplierPartnerArea();
		RemoteServiceSingleton.getInstance().getSupplierManagerService().register(supplierId, user, product, partner,
				partnerArea);
		request.setAttribute("name", user.getLoginName());
		String name = "";
		try {
			name = DES.encrypt(user.getLoginName(), Constants.CCIGMALL);
		} catch (Exception e) {
			LOGGER.error("注册：用户名回传成功页面出错!用户名：" + user.getLoginName(), e);
		}
		// request.setAttribute("language",getLanguage().substring(1));
		LOGGER.info("注册成功：用户名：" + user.getLoginName());
		return "redirect:/supplier/regist_success?sid=" + name;
	}

	/**
	 * @Description:家庭号商户注册
	 * @param myfile
	 *            MultipartFile
	 * @param myfile1
	 *            MultipartFile
	 * @param map
	 *            map
	 * @param supplier
	 *            Supplier
	 * @param request
	 *            HttpServletRequest
	 * @return regist_success
	 */
	@RequestMapping("/regist2")
	public String regist2(String kaptcha, Map<String, Object> map, Supplier supplier, HttpServletRequest request,
			String uid, String phoneCode) {
		int i = RemoteServiceSingleton.getInstance().getSupplierManagerService().selectCountUser(supplier.getUserId());
		if (i >= 1) {
			request.setAttribute("message", "不能重复注册！");
			return "/user/regist2";

		}
		String mobile = request.getParameter("phone");
		String msgReqCode = request.getParameter("verificationCode");
		LOGGER.info("注册：supplier:" + JSON.toJSONString(supplier));
		try {
			String kaptchaExpected = memcachedClient.get(uid + Constants.KAPTCHA_SESSION_KEY);
			if (!kaptcha.toLowerCase().equals(kaptchaExpected.toLowerCase())) {
				if (getLanguage().equals("/en")) {
					request.setAttribute("message", "Verification code is wrong！");
				} else {
					request.setAttribute("message", "验证码有误！");
				}
				return "/user/regist2";
			}

			if (msgReqCode == null || "".equals(msgReqCode)) {
				logger.info("bad request msgReqCode....");
				request.setAttribute("message", "请填写验证码！");
				return "/user/regist2";
			}

		} catch (Exception e) {
			LOGGER.error("注册：  memcached中取验证码出问题！", e);
		}
		Integer msgCode = null;
		String memMobile = null;
		try {
			msgCode = memcachedClient.get(Constants.SEND_REG_MESSAGE + mobile);

			memMobile = memcachedClient.get(Constants.SEND_REG_MESSAGE + mobile + "mobile");

		} catch (Exception e) {
			logger.info("memcache running error.." + e, e);

		}
		if (msgCode == null && msgReqCode != (msgCode + "")) {
			// 校验成功
			try {
				memcachedClient.delete(Constants.SEND_REG_MESSAGE + mobile);
				memcachedClient.delete(Constants.SEND_REG_MESSAGE + mobile + "mobile");

				memcachedClient.setOpTimeout(5000L);

			} catch (Exception e) {
				logger.error("memcache running error...." + e, e);

			}
			request.setAttribute("message", "您填写的手机接收验证码有误");
			return "/user/regist2";
		}

		/**
		 * 调接口判断用户注册够不够资格
		 */
		// 查询用户是否购买家庭号6.7
		HomeNumRecordDto homeNumRecordDto;
		try {
			homeNumRecordDto = homeNumRecordService.selectRecordByUserid((supplier.getUserId()).intValue());
			Integer jiaTingStatus = homeNumRecordDto.getStatus();
			if (jiaTingStatus == 0) {
				request.setAttribute("message", "您没有资格注册 ");
				return "/user/regist2";
			}
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		SupplierUser user = new SupplierUser();
		if (!Common.isEmpty(request.getParameter("loginName"))) {
			user.setIsAdmin(1);
			user.setStatus(1);
			user.setLoginName(request.getParameter("loginName"));
			user.setPassword(MD5.encrypt(request.getParameter("password")));

			user.setRecordPwd(request.getParameter("password")); // 记录明文，为了unicorn创建账号发送短信用

			Map<String, String> pmap = new LinkedHashMap<String, String>();
			int count = 1;
			if (!Common.isEmpty(request.getParameter("loginName"))) {
				pmap.put("loginName", request.getParameter("loginName"));
				count = RemoteServiceSingleton.getInstance().getSupplierUserManagerService().getUserByName(pmap);
			}
			if (count == 1) {
				request.setAttribute("isError", 1);
				if (getLanguage().equals("/en")) {
					request.setAttribute("message", "UserName already exists ");
				} else {
					request.setAttribute("message", "用户名已存在");
				}
				LOGGER.error("用户:" + request.getParameter("loginName") + "注册：用户名已存在！");
				return "/user/regist2";
			}
		}
		supplier.setPhone(phoneCode + supplier.getPhone());
		supplier.setStatus(0);
		supplier.setCreateTime(new Date());
		// supplier.setName(request.getParameter("companyName"));
		supplier.setName(request.getParameter("companyName"));
		supplier.setFax(request.getParameter("fax"));
		if (Common.isEmpty(request.getParameter("post"))) {
			supplier.setPost(Common.stringToInteger(request.getParameter("post")));
		}
		// 设置pop用户为51
		supplier.setSupplyType(51);
		supplier.setType(-1); // 注册的时候默认给-1值
		supplier.setOrganizationType(5);
		supplier.setHqqPwd(MD5.encrypt(supplier.getHqqPwd()));
		supplier.setCompanyQy("10");
		supplier.setUserId(supplier.getUserId());
		supplier.setActiveStatus(-1); // 该企业注册未激活置为-1
		LOGGER.info("pop user regist set supplyType 51 " + user.getLoginName());

		User tjUser = null;
		Supplier sjSupplier = null;
		if (StringUtils.isEmpty(supplier.getSjSupplierId())) { // 如果上级企业代码为空
																// 置为邀请码用户里的supplierId
			/*
			 * tjUser = RemoteServiceSingleton.getInstance().getUserService().
			 * findUserByMobile(supplier.getUserTj()); if(tjUser != null &&
			 * StringUtils.isNotEmpty(tjUser.getSupplierId())){ Supplier
			 * tjSupplier =
			 * RemoteServiceSingleton.getInstance().getSupplierManagerService()
			 * .findSupplier(Long.valueOf(tjUser.getSupplierId()));
			 * if(tjSupplier != null && tjSupplier.getType() == 1){ //属于子公司
			 * supplier.setSjSupplierId(tjSupplier.getSupplierId()+""); }else{
			 * Supplier defaultSupplier =
			 * RemoteServiceSingleton.getInstance().getSupplierManagerService()
			 * .getSupplierBySupplierCode("SQ69"); if(defaultSupplier != null){
			 * supplier.setSjSupplierId(defaultSupplier.getSupplierId()+""); } }
			 * }else{ Supplier defaultSupplier =
			 * RemoteServiceSingleton.getInstance().getSupplierManagerService()
			 * .getSupplierBySupplierCode("SQ69"); if(defaultSupplier != null){
			 * supplier.setSjSupplierId(defaultSupplier.getSupplierId()+""); } }
			 */
		} else { // 不为空根据企业代码获取相对应的supplierId
			sjSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
					.getSupplierBySupplierCode(supplier.getSjSupplierId());
			if (sjSupplier != null) {
				supplier.setSjSupplierId(sjSupplier.getSupplierId() + "");
			}
		}
		/*
		 * if(StringUtils.isEmpty(supplier.getSjSupplierId())){ //如果上级企业代码为空
		 * 置为邀请码用户里的supplierId tjUser =
		 * RemoteServiceSingleton.getInstance().getUserService().
		 * findUserByMobile(supplier.getUserTj()); if(tjUser != null){
		 * supplier.setSjSupplierId(tjUser.getSupplierId()); } }else{
		 * //不为空根据企业代码获取相对应的supplierId sjSupplier =
		 * RemoteServiceSingleton.getInstance().getSupplierManagerService()
		 * .getSupplierBySupplierCode(supplier.getSjSupplierId()); if(sjSupplier
		 * != null){ supplier.setSjSupplierId(sjSupplier.getSupplierId()+""); }
		 * }
		 */
		Long supplierId = RemoteServiceSingleton.getInstance().getSupplierManagerService().insert(supplier);
		SupplierProduct product = new SupplierProduct();
		getObjectFromRequest(product, request);
		SupplierPartner partner = new SupplierPartner();
		partner.setName(request.getParameter("p_companyName"));
		partner.setAddress(request.getParameter("p_companyName"));
		partner.setContact(request.getParameter("p_companyContact"));
		partner.setEmail(request.getParameter("p_companyMail"));
		partner.setTelephone(request.getParameter("p_companyTel"));
		SupplierPartnerArea a = new SupplierPartnerArea();
		RemoteServiceSingleton.getInstance().getSupplierManagerService().register(supplierId, user, product, partner,
				a);
		request.setAttribute("name", user.getLoginName());
		String name = "";
		try {
			name = DES.encrypt(user.getLoginName(), Constants.CCIGMALL);
		} catch (Exception e) {
			LOGGER.error("注册：用户名回传成功页面出错!用户名：" + user.getLoginName(), e);
		}
		// request.setAttribute("language",getLanguage().substring(1));
		LOGGER.info("注册成功：用户名：" + user.getLoginName());
		return "redirect:/supplier/regist_success?sid=" + name;
	}

	/**
	 * @Description:企业号商户注册
	 * @param myfile
	 *            MultipartFile
	 * @param myfile1
	 *            MultipartFile
	 * @param map
	 *            map
	 * @param supplier
	 *            Supplier
	 * @param request
	 *            HttpServletRequest
	 * @return regist_success
	 */
	@RequestMapping("/regist3")
	public String regist3(MultipartFile myfile, MultipartFile myfile1, String kaptcha, Map<String, Object> map,
			Supplier supplier, HttpServletRequest request, String uid, String phoneCode, MultipartFile myfile2) {
		LOGGER.info("注册：supplier:" + JSON.toJSONString(supplier));
		SupplierNumRecordDto supplierNumRecordDto;
		try {
			supplierNumRecordDto = supplierNumRecordService.selectRecordByUserid((supplier.getUserId()).intValue());
			Integer jiaTingStatus = supplierNumRecordDto.getStatus();
			if (jiaTingStatus == 0) {
				request.setAttribute("message", "您没有资格注册 ");
				return "/user/regist3";
			}
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		int i = RemoteServiceSingleton.getInstance().getSupplierManagerService().selectCountUser(supplier.getUserId());
		if (i >= 1) {
			request.setAttribute("message", "不能重复注册或同时注册家庭号和企业号！");
			return "/user/regist3";

		}
		try {
			String kaptchaExpected = memcachedClient.get(uid + Constants.KAPTCHA_SESSION_KEY);
			if (!kaptcha.toLowerCase().equals(kaptchaExpected.toLowerCase())) {
				if (getLanguage().equals("/en")) {
					request.setAttribute("message", "Verification code is wrong！");
				} else {
					request.setAttribute("message", "验证码有误！");
				}
				return "/user/regist3";
			}
		} catch (Exception e) {
			LOGGER.error("注册：  memcached中取验证码出问题！", e);
		}

		if (null == myfile) {
			request.setAttribute("isError", 1);
			if (getLanguage().equals("/en")) {
				request.setAttribute("message", "Missing Upload Files");
			} else {
				request.setAttribute("message", "缺少需要上传的文件");
			}
			LOGGER.error("注册：缺少需要上传的文件！");
			return "/user/regis3t";
		} else {
			// System.out.println(
			// Common.getFileExt2(myfile1.getOriginalFilename()));
			String myfileUrl = "";
			String myfile1Url = "";
			String myfile2Url = "";
			try {
				myfileUrl = UploadFileUtil.uploadFile(myfile.getInputStream(),
						Common.getFileExt2(myfile.getOriginalFilename()), null);
				if (null != myfile1 && !"".equals(Common.getFileExt2(myfile1.getOriginalFilename()))) {
					myfile1Url = UploadFileUtil.uploadFile(myfile1.getInputStream(),
							Common.getFileExt2(myfile1.getOriginalFilename()), null);
				}
				if (null != myfile2 && !"".equals(Common.getFileExt2(myfile2.getOriginalFilename()))) {
					myfile2Url = UploadFileUtil.uploadFile(myfile2.getInputStream(),
							Common.getFileExt2(myfile2.getOriginalFilename()), null);
				}
			} catch (Exception e) {
				LOGGER.error("注册：上传文件到图片服务器出错！", e);
			}
			supplier.setCompanyLegitimacyUrl(myfileUrl);
			if (null != myfile1 && !"".equals(Common.getFileExt2(myfile1.getOriginalFilename()))) {
				supplier.setCompanyDetailedUrl(myfile1Url);
			}
			if (null != myfile2 && !"".equals(Common.getFileExt2(myfile2.getOriginalFilename()))) {
				supplier.setLogoImgurl(myfile2Url);
			} else {
				supplier.setLogoImgurl("group1/M00/00/31/CgAAElgxxt-Aa7U1AAAjS6o85T0082.jpg"); // 默认图片logo地址
			}
		}
		SupplierUser user = new SupplierUser();
		if (!Common.isEmpty(request.getParameter("loginName"))) {
			user.setIsAdmin(1);
			user.setStatus(1);
			user.setLoginName(request.getParameter("loginName"));
			user.setPassword(MD5.encrypt(request.getParameter("password")));

			user.setRecordPwd(request.getParameter("password")); // 记录明文，为了unicorn创建账号发送短信用

			Map<String, String> pmap = new LinkedHashMap<String, String>();
			int count = 1;
			if (!Common.isEmpty(request.getParameter("loginName"))) {
				pmap.put("loginName", request.getParameter("loginName"));
				count = RemoteServiceSingleton.getInstance().getSupplierUserManagerService().getUserByName(pmap);
			}
			if (count == 1) {
				request.setAttribute("isError", 1);
				if (getLanguage().equals("/en")) {
					request.setAttribute("message", "UserName already exists ");
				} else {
					request.setAttribute("message", "用户名已存在");
				}
				LOGGER.error("用户:" + request.getParameter("loginName") + "注册：用户名已存在！");
				return "/user/regist3";
			}
		}
		supplier.setPhone(phoneCode + supplier.getPhone());
		supplier.setStatus(0);
		supplier.setCreateTime(new Date());
		// supplier.setName(request.getParameter("companyName"));
		supplier.setName(request.getParameter("companyName"));
		supplier.setFax(request.getParameter("fax"));
		if (Common.isEmpty(request.getParameter("post"))) {
			supplier.setPost(Common.stringToInteger(request.getParameter("post")));
		}
		// 设置pop用户为51
		supplier.setSupplyType(51);
		supplier.setType(10); // 注册的时候默认给-1值
		supplier.setHqqPwd(MD5.encrypt(supplier.getHqqPwd())); // 红旗券支付密码
		supplier.setActiveStatus(-1); // 该企业注册未激活置为-1
		supplier.setOrganizationType(6);
		supplier.setUserId(supplier.getUserId());
		LOGGER.info("pop user regist set supplyType 51 " + user.getLoginName());

		User tjUser = null;
		Supplier sjSupplier = null;
		if (StringUtils.isEmpty(supplier.getSjSupplierId())) { // 如果上级企业代码为空
																// 置为邀请码用户里的supplierId
			/*
			 * tjUser = RemoteServiceSingleton.getInstance().getUserService().
			 * findUserByMobile(supplier.getUserTj()); if(tjUser != null &&
			 * StringUtils.isNotEmpty(tjUser.getSupplierId())){ Supplier
			 * tjSupplier =
			 * RemoteServiceSingleton.getInstance().getSupplierManagerService()
			 * .findSupplier(Long.valueOf(tjUser.getSupplierId()));
			 * if(tjSupplier != null && tjSupplier.getType() == 1){ //属于子公司
			 * supplier.setSjSupplierId(tjSupplier.getSupplierId()+""); }else{
			 * Supplier defaultSupplier =
			 * RemoteServiceSingleton.getInstance().getSupplierManagerService()
			 * .getSupplierBySupplierCode("SQ69"); if(defaultSupplier != null){
			 * supplier.setSjSupplierId(defaultSupplier.getSupplierId()+""); } }
			 * }else{ Supplier defaultSupplier =
			 * RemoteServiceSingleton.getInstance().getSupplierManagerService()
			 * .getSupplierBySupplierCode("SQ69"); if(defaultSupplier != null){
			 * supplier.setSjSupplierId(defaultSupplier.getSupplierId()+""); } }
			 */
		} else { // 不为空根据企业代码获取相对应的supplierId
			sjSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
					.getSupplierBySupplierCode(supplier.getSjSupplierId());
			if (sjSupplier != null) {
				supplier.setSjSupplierId(sjSupplier.getSupplierId() + "");
			}
		}
		/*
		 * if(StringUtils.isEmpty(supplier.getSjSupplierId())){ //如果上级企业代码为空
		 * 置为邀请码用户里的supplierId tjUser =
		 * RemoteServiceSingleton.getInstance().getUserService().
		 * findUserByMobile(supplier.getUserTj()); if(tjUser != null){
		 * supplier.setSjSupplierId(tjUser.getSupplierId()); } }else{
		 * //不为空根据企业代码获取相对应的supplierId sjSupplier =
		 * RemoteServiceSingleton.getInstance().getSupplierManagerService()
		 * .getSupplierBySupplierCode(supplier.getSjSupplierId()); if(sjSupplier
		 * != null){ supplier.setSjSupplierId(sjSupplier.getSupplierId()+""); }
		 * }
		 */
		Long supplierId = RemoteServiceSingleton.getInstance().getSupplierManagerService().insert(supplier);

		SupplierProduct product = new SupplierProduct();
		getObjectFromRequest(product, request);
		SupplierPartner partner = new SupplierPartner();
		partner.setName(request.getParameter("p_companyName"));
		partner.setAddress(request.getParameter("p_companyName"));
		partner.setContact(request.getParameter("p_companyContact"));
		partner.setEmail(request.getParameter("p_companyMail"));
		partner.setTelephone(request.getParameter("p_companyTel"));
		SupplierPartnerArea a = new SupplierPartnerArea();
		RemoteServiceSingleton.getInstance().getSupplierManagerService().register(supplierId, user, product, partner,
				a);
		request.setAttribute("name", user.getLoginName());
		String name = "";
		try {
			name = DES.encrypt(user.getLoginName(), Constants.CCIGMALL);
		} catch (Exception e) {
			LOGGER.error("注册：用户名回传成功页面出错!用户名：" + user.getLoginName(), e);
		}
		// request.setAttribute("language",getLanguage().substring(1));
		LOGGER.info("注册成功：用户名：" + user.getLoginName());
		return "redirect:/supplier/regist_success?sid=" + name;
	}

	/**
	 * @Description:供应商信息表下载.
	 * @param response
	 *            HttpServletResponse
	 * @param url
	 *            url参数
	 */
	@RequestMapping("/regist_success")
	public String registSuccess(Map<String, Object> map, String sid) {

		try {
			sid = sid.replaceAll(" ", "+");
			sid = DES.decrypt(sid, Constants.CCIGMALL);
			LOGGER.info("注册成功：用户名" + sid);
		} catch (Exception e) {
			LOGGER.error("注册失败：用户名" + sid);
		}
		map.put("name", sid);
		map.put("language", getLanguage().substring(1));
		return "/user/regist_success";
	}

	/**
	 * @Description:供应商信息表下载.
	 * @param response
	 *            HttpServletResponse
	 * @param url
	 *            url参数
	 */
	@RequestMapping("/download")
	public void download(HttpServletResponse response, String url) {
		LOGGER.info("用户：" + getCurrentUser().getLoginName() + "下载文件：" + url);
		if (null != url && url.length() > 0) {
			download(response, url, null);
		} else {
			try {
				response.getWriter().write("<script language='javascript'>alert('您要下载的文件不存在!');</script>");
			} catch (IOException e) {
				LOGGER.error("下载：您要下载的文件不存在!" + e.getMessage(), e);
			}
		}
	}

	@RequestMapping("/downloadTemp")
	public void downloadTemp(HttpServletRequest request, HttpServletResponse response, String url) {
		LOGGER.info("用户下载文件：供应商信息表.xlsx");
		url = request.getSession().getServletContext().getRealPath("/");
		String realName = "供应商信息表.xlsx";
		url = url + "modelFile/supplierInfo.xlsx";
		if ("/en".equals(getLanguage())) {
			realName = "SupplierInfo.xlsx";
		}
		try {
			File f = new File(url);
			// 检查该文件是否存在
			if (!f.exists()) {
				response.sendError(404, "File not found!");
			}
			// 设置响应类型和响应头
			response.setContentType("application/x-msdownload;charset=UTF-8");
			String userAgent = getRequest().getHeader("User-Agent");
			if (userAgent.contains("Firefox")) {
				response.addHeader("Content-Disposition",
						"attachment;filename=" + new String(realName.getBytes("GB2312"), "ISO-8859-1"));
			} else {
				response.addHeader("Content-Disposition",
						"attachment;filename=" + URLEncoder.encode(realName, "UTF-8"));
			}
			// 读出文件到i/o流
			FileInputStream fis = new FileInputStream(f);
			BufferedInputStream buff = new BufferedInputStream(fis);
			byte[] b = new byte[1024];// 相当于我们的缓存
			long k = 0;// 该值用于计算当前实际下载了多少字节
			// 从response对象中得到输出流,准备下载
			OutputStream myout = response.getOutputStream();
			// 开始循环下载
			while (k < f.length()) {
				int j = buff.read(b, 0, 1024);
				k += j;
				// 将b中的数据写到客户端的内存
				myout.write(b, 0, j);
			}
			// 将写入到客户端的内存的数据,刷新到磁盘
			myout.flush();
			myout.close();
			buff.close();
		} catch (FileNotFoundException e) {
			LOGGER.error("文件不存在" + e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.error("下载供应商信息表出错" + e.getMessage(), e);
		}
	}

	/**
	 * @Description: 获取商户基本信息 .
	 * @param map
	 *            map
	 * @return 基本信息页面
	 */
	@RequestMapping("/jiben")
	@Token(saveToken = true)
	public String supplierInfo(Map<String, Object> map, String message) {
		LOGGER.info("用户：" + getCurrentUser().getLoginName() + "访问基本信息页面");
		if (!Common.isEmpty(message)) {
			map.put("message", message);
		}
		// 调用 基础接口 国家

		Supplier supplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
				.findSupplier(getCurrentSupplierId());
		map.put("data", supplier);
		map.put("language", getLanguage().substring(1));
		SupplierProduct product = RemoteServiceSingleton.getInstance().getSupplierManagerService()
				.getProductBySupplierId(getCurrentSupplierId());
		map.put("product", product);
		List<TdCatePub> childrenCategoryList = new ArrayList<TdCatePub>();
		if (RemoteServiceSingleton.getInstance().getCategoryServiceRpc() != null) {
			try {
				childrenCategoryList = RemoteServiceSingleton.getInstance().getCategoryServiceRpc()
						.getTopCategoryList();
			} catch (Exception e) {
				LOGGER.error("获取类目失败！", e);
			}
		}
		List<String> categoryNameCn = new ArrayList<String>();
		List<String> categoryNameEn = new ArrayList<String>();
		if (!childrenCategoryList.isEmpty()) {
			for (TdCatePub tdCatePub : childrenCategoryList) {
				categoryNameCn.add(tdCatePub.getPubNameCn());
				categoryNameEn.add(tdCatePub.getPubName());
			}
		}

		// 根据上级企业supplierId获取上级企业对象
		if ((!StringUtils.isEmpty(supplier.getSjSupplierId())) && !supplier.getSjSupplierId().equals("0")) {
			Supplier sjSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
					.findSupplier(Long.valueOf(supplier.getSjSupplierId()));
			map.put("sjSupplier", sjSupplier);
		}
		User user = RemoteServiceSingleton.getInstance().getUserService().findUserBySupplierId(getCurrentSupplierId());
		map.put("user", user);

		// 获取所有入驻区域类型
		List<SupplierRegionSettings> regionList = regionService.getAllRegionSettings();
		map.put("regionList", regionList);

		if ("/en".equals(getLanguage())) {
			map.put("category", JSONObject.toJSONString(categoryNameEn));
		} else {
			map.put("category", JSONObject.toJSONString(categoryNameCn));
		}
		if (supplier.getOrganizationType() != null && supplier.getOrganizationType() == 5) {
			return getLanguage() + "/user/jiben2";
		}
		if (supplier.getOrganizationType() != null && supplier.getOrganizationType() == 6) {
			return getLanguage() + "/user/jiben3";
		}
		// 代理查看信息页面
		if (supplier.getOrganizationType() != null && supplier.getOrganizationType() == 12) {
			return getLanguage() + "/agent/user/jibenForLook";
		}
		return getLanguage() + "/user/jiben";
	}

	/**
	 * @Description: 获取家庭号商户基本信息 .
	 * @param map
	 *            map
	 * @return 基本信息页面
	 */
	@RequestMapping("/jiben2")
	@Token(saveToken = true)
	public String supplierInfo2(Map<String, Object> map, String message) {
		LOGGER.info("用户：" + getCurrentUser().getLoginName() + "访问基本信息页面");
		if (!Common.isEmpty(message)) {
			map.put("message", message);
		}
		// 调用 基础接口 国家

		Supplier supplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
				.findSupplier(getCurrentSupplierId());
		map.put("data", supplier);
		map.put("language", getLanguage().substring(1));
		SupplierProduct product = RemoteServiceSingleton.getInstance().getSupplierManagerService()
				.getProductBySupplierId(getCurrentSupplierId());
		map.put("product", product);
		List<TdCatePub> childrenCategoryList = new ArrayList<TdCatePub>();
		if (RemoteServiceSingleton.getInstance().getCategoryServiceRpc() != null) {
			try {
				childrenCategoryList = RemoteServiceSingleton.getInstance().getCategoryServiceRpc()
						.getTopCategoryList();
			} catch (Exception e) {
				LOGGER.error("获取类目失败！", e);
			}
		}
		List<String> categoryNameCn = new ArrayList<String>();
		List<String> categoryNameEn = new ArrayList<String>();
		if (!childrenCategoryList.isEmpty()) {
			for (TdCatePub tdCatePub : childrenCategoryList) {
				categoryNameCn.add(tdCatePub.getPubNameCn());
				categoryNameEn.add(tdCatePub.getPubName());
			}
		}

		// 根据上级企业supplierId获取上级企业对象
		if ((!StringUtils.isEmpty(supplier.getSjSupplierId())) && !supplier.getSjSupplierId().equals("0")) {
			Supplier sjSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
					.findSupplier(Long.valueOf(supplier.getSjSupplierId()));
			map.put("sjSupplier", sjSupplier);
		}
		User user = RemoteServiceSingleton.getInstance().getUserService().findUserBySupplierId(getCurrentSupplierId());
		map.put("user", user);

		// 获取所有入驻区域类型
		List<SupplierRegionSettings> regionList = regionService.getAllRegionSettings();
		map.put("regionList", regionList);

		if ("/en".equals(getLanguage())) {
			map.put("category", JSONObject.toJSONString(categoryNameEn));
		} else {
			map.put("category", JSONObject.toJSONString(categoryNameCn));
		}
		return getLanguage() + "/user/jiben2";
	}

	@RequestMapping("/jiben3")
	@Token(saveToken = true)
	public String supplierInfo3(Map<String, Object> map, String message) {
		LOGGER.info("用户：" + getCurrentUser().getLoginName() + "访问基本信息页面");
		if (!Common.isEmpty(message)) {
			map.put("message", message);
		}
		// 调用 基础接口 国家

		Supplier supplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
				.findSupplier(getCurrentSupplierId());
		map.put("data", supplier);
		map.put("language", getLanguage().substring(1));
		SupplierProduct product = RemoteServiceSingleton.getInstance().getSupplierManagerService()
				.getProductBySupplierId(getCurrentSupplierId());
		map.put("product", product);
		List<TdCatePub> childrenCategoryList = new ArrayList<TdCatePub>();
		if (RemoteServiceSingleton.getInstance().getCategoryServiceRpc() != null) {
			try {
				childrenCategoryList = RemoteServiceSingleton.getInstance().getCategoryServiceRpc()
						.getTopCategoryList();
			} catch (Exception e) {
				LOGGER.error("获取类目失败！", e);
			}
		}
		List<String> categoryNameCn = new ArrayList<String>();
		List<String> categoryNameEn = new ArrayList<String>();
		if (!childrenCategoryList.isEmpty()) {
			for (TdCatePub tdCatePub : childrenCategoryList) {
				categoryNameCn.add(tdCatePub.getPubNameCn());
				categoryNameEn.add(tdCatePub.getPubName());
			}
		}

		// 根据上级企业supplierId获取上级企业对象
		if ((!StringUtils.isEmpty(supplier.getSjSupplierId())) && !supplier.getSjSupplierId().equals("0")) {
			Supplier sjSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
					.findSupplier(Long.valueOf(supplier.getSjSupplierId()));
			map.put("sjSupplier", sjSupplier);
		}
		User user = RemoteServiceSingleton.getInstance().getUserService().findUserBySupplierId(getCurrentSupplierId());
		map.put("user", user);

		// 获取所有入驻区域类型
		List<SupplierRegionSettings> regionList = regionService.getAllRegionSettings();
		map.put("regionList", regionList);

		if ("/en".equals(getLanguage())) {
			map.put("category", JSONObject.toJSONString(categoryNameEn));
		} else {
			map.put("category", JSONObject.toJSONString(categoryNameCn));
		}
		if (supplier.getOrganizationType() != null && supplier.getOrganizationType() == 6) {
			return getLanguage() + "/user/jiben3";
		}
		return getLanguage() + "/user/jiben3";
	}

	/**
	 * @Description:更新商户基本信息.
	 * @param iconUrl
	 *            ICON
	 * @param map
	 *            MAP
	 * @param supplier
	 *            Supplier
	 * @return 基本信息页面
	 */
	@RequestMapping("/update")
	@Token(validateToken = true)
	public String updateSupplier(MultipartFile myfile, MultipartFile myfile1, MultipartFile myfile2,
			Map<String, Object> map, Supplier supplier, SupplierProduct product, HttpServletRequest request) {
		String myfileUrl = "";
		String myfile1Url = "";
		String myfile2Url = "";
		try {
			if (null != myfile && !"".equals(Common.getFileExt2(myfile.getOriginalFilename()))) {
				myfileUrl = UploadFileUtil.uploadFile(myfile.getInputStream(),
						Common.getFileExt2(myfile.getOriginalFilename()), null);
			}
			if (null != myfile1 && !"".equals(Common.getFileExt2(myfile1.getOriginalFilename()))) {
				myfile1Url = UploadFileUtil.uploadFile(myfile1.getInputStream(),
						Common.getFileExt2(myfile1.getOriginalFilename()), null);
			}
			if (null != myfile2 && !"".equals(Common.getFileExt2(myfile2.getOriginalFilename()))) {
				myfile2Url = UploadFileUtil.uploadFile(myfile2.getInputStream(),
						Common.getFileExt2(myfile2.getOriginalFilename()), null);
			}

		} catch (Exception e) {
			LOGGER.error("注册：上传文件到图片服务器出错！", e);
		}
		// 资质文件备份
		if (null != myfile && !"".equals(Common.getFileExt2(myfile.getOriginalFilename()))) {
			supplier.setCompanyLegitimacyUrl(myfileUrl);
		}
		// 详情文件备份
		if (null != myfile1 && !"".equals(Common.getFileExt2(myfile1.getOriginalFilename()))) {
			supplier.setCompanyDetailedUrl(myfile1Url);
		}
		// 企业logo备份
		if (null != myfile2 && !"".equals(Common.getFileExt2(myfile2.getOriginalFilename()))) {
			supplier.setLogoImgurlBackup(myfile2Url);
		}

		supplier.setModifyStatus(1);
		Supplier newSupplier = new Supplier();
		newSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
				.findSupplier(supplier.getSupplierId());
		if (newSupplier != null && newSupplier.getStatus() == 2) { // 未通过后修改信息
																	// 可以重新审核
			supplier.setStatus(1);
		}
		int result = RemoteServiceSingleton.getInstance().getSupplierManagerService().updateBackupInfo(supplier);

		int updateProductStatus = 0; // 修改列表和品牌
		if (!product.getCategories().equals(product.getCategoriesBackup())
				|| !product.getBrand().equals(product.getBrandBackup())) {
			SupplierProduct newSupplierProduct = new SupplierProduct();
			newSupplierProduct.setModifyStatus(1); // 1表示修改
			newSupplierProduct.setSupplierId(supplier.getSupplierId());
			if (!product.getCategories().equals(product.getCategoriesBackup())) {
				newSupplierProduct.setCategoriesBackup(product.getCategoriesBackup());
			}
			if (!product.getBrand().equals(product.getBrandBackup())) {
				newSupplierProduct.setBrandBackup(product.getBrandBackup());
			}
			updateProductStatus = RemoteServiceSingleton.getInstance().getSupplierManagerService()
					.updateProductBackupInfo(newSupplierProduct);
		}

		if (result > 0 || updateProductStatus > 0) {
			LOGGER.info("用户：" + getCurrentUser().getLoginName() + "更新商户基本信息成功");
			return "redirect:/supplier/jiben?message=ok";
		} else {
			LOGGER.error("用户：" + getCurrentUser().getLoginName() + "更新商户基本信息失败！");
			return "redirect:/supplier/jiben?message=error";
		}

	}

	/**
	 * @Description:更新家庭号基本信息.
	 * @param iconUrl
	 *            ICON
	 * @param map
	 *            MAP
	 * @param supplier
	 *            Supplier
	 * @return 基本信息页面
	 */
	@RequestMapping("/update2")
	@Token(validateToken = true)
	public String updateSupplier(Map<String, Object> map, Supplier supplier, SupplierProduct product,
			HttpServletRequest request) {

		supplier.setModifyStatus(1);
		Supplier newSupplier = new Supplier();
		newSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
				.findSupplier(supplier.getSupplierId());
		if (newSupplier != null && newSupplier.getStatus() == 2) { // 未通过后修改信息
																	// 可以重新审核
			supplier.setStatus(1);
		}
		int result = RemoteServiceSingleton.getInstance().getSupplierManagerService().updateInfo(supplier);

		int updateProductStatus = 0; // 修改列表和品牌
		/*
		 * if(!product.getCategories().equals(product.getCategoriesBackup()) ||
		 * !product.getBrand().equals(product.getBrandBackup())){
		 * SupplierProduct newSupplierProduct = new SupplierProduct();
		 * newSupplierProduct.setModifyStatus(1); //1表示修改
		 * newSupplierProduct.setSupplierId(supplier.getSupplierId());
		 * if(!product.getCategories().equals(product.getCategoriesBackup())){
		 * newSupplierProduct.setCategoriesBackup(product.getCategoriesBackup())
		 * ; } if(!product.getBrand().equals(product.getBrandBackup())){
		 * newSupplierProduct.setBrandBackup(product.getBrandBackup()); }
		 * updateProductStatus =
		 * RemoteServiceSingleton.getInstance().getSupplierManagerService().
		 * updateProductBackupInfo(newSupplierProduct); }
		 */

		if (result > 0 || updateProductStatus > 0) {
			LOGGER.info("用户：" + getCurrentUser().getLoginName() + "更新商户基本信息成功");
			return "redirect:/supplier/jiben2?message=ok";
		} else {
			LOGGER.error("用户：" + getCurrentUser().getLoginName() + "更新商户基本信息失败！");
			return "redirect:/supplier/jiben2?message=error";
		}

	}

	/**
	 * @Description:验证码.
	 */
	@RequestMapping("/validateCode")
	public void validateCode(HttpServletRequest request, HttpServletResponse response, String uid) {
		response.setContentType("image/jpeg");
		String capText = captchaProducer.createText();
		try {
			memcachedClient.set(uid + Constants.KAPTCHA_SESSION_KEY, 180, capText);
			BufferedImage bi = captchaProducer.createImage(capText);
			ServletOutputStream out = response.getOutputStream();
			ImageIO.write(bi, "jpg", out);
			try {
				out.flush();
			} finally {
				out.close();
			}
		} catch (Exception e) {
			LOGGER.error("生成验证码失败！", e);
		}
	}

	/**
	 * @Description:找回密码Step1
	 * @param response
	 *            HttpServletResponse
	 */
	@RequestMapping("/findPwdNext")
	public String findPwdNext(String username, String authCode, String uid, Map<String, Object> map) {
		map.put("uid", uid);
		if (!Common.isEmpty(username)) {
			SupplierUser user = RemoteServiceSingleton.getInstance().getSupplierUserManagerService()
					.getUserByName(username);
			if (null != user) {
				Supplier supplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
						.findSupplier(user.getSupplierId());
				if (null != supplier) {
					if (null != supplier.getEmail()) {
						try {
							memcachedClient.set(uid, 30 * 60, uid);
							memcachedClient.set(uid + Constants.SESSION_ID, 30 * 60, username);
							memcachedClient.set(uid + Constants.SUPPLIERUSER, 30 * 60, user);
							memcachedClient.set(uid + Constants.SUPPLIER, 30 * 60, supplier.getEmail());
							map.put("username", username);
							map.put("email", supplier.getEmail().charAt(0) + "******"
									+ supplier.getEmail().substring(supplier.getEmail().indexOf("@") - 1));
						} catch (Exception e) {
							LOGGER.error("用户：" + username + "找回密码：memcachedClient Set uid username email报错", e);
						}
						return getLanguage() + "/user/sendMail";
					}
				} else {
					String newUid = UUID.randomUUID().toString();
					map.put("uid", newUid);
					if ("/en".equals(getLanguage())) {
						map.put("message", "Associated supplier information does not exist!");
					} else {
						map.put("message", "关联商户信息不存在!");
					}
					LOGGER.error("用户：" + username + "找回密码：关联商户信息不存在");
					return getLanguage() + "/user/findPwd";
				}
			} else {
				String newUid = UUID.randomUUID().toString();
				map.put("uid", newUid);
				if ("/en".equals(getLanguage())) {
					map.put("message", "The user information does not exist!");
				} else {
					map.put("message", "用户信息不存在!");
				}
				LOGGER.error("用户：" + username + "找回密码：用户信息不存在");
				return getLanguage() + "/user/findPwd";
			}
		}
		String newUid = UUID.randomUUID().toString();
		map.put("uid", newUid);
		if ("/en".equals(getLanguage())) {
			map.put("message", "login name cannot be empty !");
		} else {
			map.put("message", "用户名不能为空!");
		}
		LOGGER.info("用户：" + username + "找回密码：用户名不能为空");
		return getLanguage() + "/user/findPwd";
	}

	/**
	 * @Description:找回密码Step1
	 * @param response
	 *            HttpServletResponse
	 */
	@RequestMapping("/findPwd")
	public String findPwd(Map<String, Object> map) {
		String uid = UUID.randomUUID().toString();
		try {
			memcachedClient.set(uid, 30 * 60, uid);
		} catch (Exception e) {
			LOGGER.error("找回密码：memcachedClient Set uid报错", e);
		}
		map.put("uid", uid);
		return getLanguage() + "/user/findPwd";
	}

	/**
	 * @Description:找回密码Step1
	 * @param response
	 *            HttpServletResponse
	 */
	@RequestMapping("/sendMail")
	@ResponseBody
	public String sendMail(String uid, HttpServletRequest request, Map<String, Object> map) {
		try {
			String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath() + "/";
			String username = memcachedClient.get(uid + Constants.SESSION_ID);
			String email = memcachedClient.get(uid + Constants.SUPPLIER);
			String jiami = DES.encrypt(uid, Constants.CCIGMALL);
			baseUrl = baseUrl + "supplier/resetPwd?uid=" + jiami;
			if (!Common.isEmpty(email)) {
				if (!"/en".equals(getLanguage())) {
					String content = "<table cellspacing='0' cellpadding='0' width='620' border='0' style='margin-left: 10px;'><tbody><tr><td style='font-size: 12px; line-height: 25px; padding-top: 10px;'>"
							+ "<strong>尊敬的" + username
							+ "，您好:</strong></td></tr><tr><td style='line-height: 20px; padding-top: 0px; font-size: 12px;'> 您在众聚商城 点击了“忘记密码”按钮，故系统自动为您发送了这封邮件。"
							+ "您可以点击以下链接修改您的密码：<br><a target='_blank' href='" + baseUrl + "'>" + "" + baseUrl
							+ "</a></td>"
							+ " </tr><tr><td style='line-height: 20px; padding-top: 8px; font-size: 12px;'> 此链接有效期为30分钟，请在30分钟内点击链接进行修改。如果您不需要修改密码，或者您从未点击过“忘记密码”按钮，请忽略本邮件."
							+ "<br></td></tr><tr></tr><tr><td style='line-height: 20px; padding-top: 2px; font-size: 12px;'> <p>如有任何疑问，请联系众聚商城客服，客服热线：<span lang='EN-US' xml:lang='EN-US'>010-84932237，84937117</span></p>"
							+ "</td></tr></tbody></table>";
					SendHtmlMail.send(email, "找回密码(zhongjumall.com)", content);
				} else {
					String content = "<table cellspacing='0' cellpadding='0' width='620' border='0' style='margin-left: 10px;'><tbody><tr><td style='font-size: 12px; line-height: 25px; padding-top: 10px;'>"
							+ "<strong>Dear " + username
							+ ", Hello:</strong></td></tr><tr><td style='line-height: 20px; padding-top: 0px; font-size: 12px;'> in your zhongjumall click on the 'forgot password' button, the system automatically send you this message."
							+ "You can click on the following link to change your password：<br><a target='_blank' href='"
							+ baseUrl + "'>" + "" + baseUrl + "</a></td>"
							+ " </tr><tr><td style='line-height: 20px; padding-top: 8px; font-size: 12px;'> this link is valid for 30 minutes, please click on the link in 30 minutes to modify."
							+ "If you do not need to modify the password, or you never click on the forgot password button, please ignore this email."
							+ "<br></td></tr><tr></tr><tr><td style='line-height: 20px; padding-top: 2px; font-size: 12px;'> <p>If you have any questions, please contact zhongjumall customer service, customer service hotline: <span lang='EN-US' xml:lang='EN-US'>010-84932237，84937117</span></p>"
							+ "</td></tr></tbody></table>";
					SendHtmlMail.send(email, "Find Password(zhongjumall.com)", content);
				}
			}
			LOGGER.info("用户：" + username + "发送邮件成功");

		} catch (Exception e) {
			LOGGER.error("发送邮件报错", e);
			return "0";// 发送邮件失败
		}
		// map.put("message", "");
		return "1";// 发送邮件成功
	}

	/**
	 * @Description:找回密码Step1
	 * @param response
	 *            HttpServletResponse
	 */
	@RequestMapping("/sendMailSuccess")
	public String sendMailSucess(String uid, Map<String, Object> map) {
		map.put("uid", uid);
		return getLanguage() + "/user/sendMail";
	}

	/**
	 * @Description:找回密码Step1
	 * @param response
	 *            HttpServletResponse
	 */
	@RequestMapping("/resetPwd")
	public String resetPwd(String uid, Map<String, Object> map) {
		if (Common.isEmpty(uid)) {
			map.put("message", "");// 非法请求
			LOGGER.warn("找回密码：非法请求URL");
			return getLanguage() + "/user/findPwd";
		} else {
			map.put("uid", uid);
		}
		try {
			uid = uid.replaceAll(" ", "+");
			String jiemi = DES.decrypt(uid, Constants.CCIGMALL);
			System.err.println(jiemi);
			String uidStr = memcachedClient.get(jiemi);
			if (!Common.isEmpty(uidStr)) {
				map.put("message", 1);// 正常
				memcachedClient.deleteWithNoReply(jiemi);
				LOGGER.warn("找回密码：正常跳转");
				return getLanguage() + "/user/resetPwd";
			} else {
				map.put("message", 0);// 请求超时或者过期
				return getLanguage() + "/user/resetPwd";
			}
		} catch (Exception e) {
			map.put("message", 0);// 请求超时或者过期
		}
		return getLanguage() + "/user/resetPwd";
	}

	/**
	 * 16.1.26 跳转修改 密码页面
	 * 
	 */
	@RequestMapping("/updatePwd")
	public String updatePwd(Map<String, Object> map) {

		Supplier supplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
				.findSupplier(getCurrentSupplierId());
		map.put("data", supplier);
		map.put("language", getLanguage().substring(1));

		return getLanguage() + "/user/updatePwd";

	}

	/**
	 * @Description:找回密码Step1
	 * @param response
	 *            HttpServletResponse
	 */
	@RequestMapping("/finish")
	@ResponseBody
	public String finish(String uid, String password, Map<String, Object> map) {
		if (Common.isEmpty(uid)) {
			return "-1";
		} else if (Common.isEmpty(password)) {
			return "passwordError";
		} else {
			try {
				uid = uid.replaceAll(" ", "+");
				String jiemi = DES.decrypt(uid, Constants.CCIGMALL);
				System.err.println(jiemi);
				SupplierUser user = memcachedClient.get(jiemi + Constants.SUPPLIERUSER);
				if (null != user) {
					user.setPassword(MD5.encrypt(password));
					RemoteServiceSingleton.getInstance().getSupplierUserManagerService().update(user);
					return "ok";
				} else {
					return "timeOut";
				}
			} catch (Exception e) {
				LOGGER.error("找回密码链接超时" + e.getMessage(), e);
				return "timeOut";
			}
		}

	}

	/**
	 * @Description:找回密码Step4
	 * @param response
	 *            HttpServletResponse
	 */
	@RequestMapping("/finishSuccess")
	public String finishSucess(String uid, Map<String, Object> map) {
		map.put("uid", uid);
		return getLanguage() + "/user/finish";
	}

	/**
	 * 通过唯一的uid获取对应服务端缓存中的验证码
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/validateNum")
	@ResponseBody
	public String validateNum(HttpServletRequest request, String uid) {
		String result = "";
		try {
			result = memcachedClient.get(uid + Constants.KAPTCHA_SESSION_KEY);
			LOGGER.info("KAPTCHA:" + result);
		} catch (Exception e) {
			LOGGER.error("找回密码验证码验证时异常" + e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 判断企业代码是否存在
	 * 
	 * @param tjName
	 * @return
	 */
	@RequestMapping("/checkSupplierCodeIsExists")
	@ResponseBody
	public String checkSupplierCodeIsExists(String supplierCode) {
		int flag = 1;
		if (StringUtils.isEmpty(supplierCode)) {
			flag = 0;
		} else {
			String isSupplierCodeExists = RemoteServiceSingleton.getInstance().getSupplierManagerService()
					.checkSupplierCodeIsExists(supplierCode);
			if (isSupplierCodeExists.equals("0")) { // 不存在
				flag = 0;
			} else {
				Supplier sjSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
						.getSupplierBySupplierCode(supplierCode);
				if (sjSupplier != null) {
					User sjUser = RemoteServiceSingleton.getInstance().getUserService()
							.findUserBySupplierId(sjSupplier.getSupplierId());
					if (sjUser == null) {
						flag = 2;
					}
				}
			}
		}
		return JSON.toJSONString(flag);
	}

	/**
	 * <p>
	 * 根据企业代码获取对应公司名称
	 * </p>
	 * 
	 * @param supplierCode
	 * @return
	 * @auth:zw
	 */
	@RequestMapping("/getCompanyNameBySupplierCode")
	@ResponseBody
	public String getCompanyNameBySupplierCode(String supplierCode) {
		String name = "";
		Supplier supplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
				.getSupplierBySupplierCode(supplierCode);
		if (supplier != null) { // 不存在
			name = supplier.getName();
		}
		return name;
	}

	/**
	 * <p>
	 * 根据邀请码获取需要展示的公司名称
	 * </p>
	 * 
	 * @param mobile
	 * @return
	 * @auth:zw
	 */
	@RequestMapping("/getNameByTjUser")
	@ResponseBody
	public String getNameByTjUser(String mobile) {
		String name = "";
		String defaultSupplierCode = "SQ69"; // 默认的
		User tjUser = null;
		Supplier tjSupplier = null;
		tjUser = RemoteServiceSingleton.getInstance().getUserService().findUserByMobile(mobile);

		if (tjUser == null || StringUtils.isEmpty(tjUser.getSupplierId())) {
			tjSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
					.getSupplierBySupplierCode(defaultSupplierCode);
			if (!StringUtils.isEmpty(tjSupplier.getName())) {
				name = tjSupplier.getName();
			}
		} else {
			tjSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
					.findSupplier(Long.valueOf(tjUser.getSupplierId()));
			if (tjSupplier != null && tjSupplier.getType() == 1) { // 属于子公司
				if (!StringUtils.isEmpty(tjSupplier.getName())) {
					name = tjSupplier.getName();
				}
			} else {
				tjSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService()
						.getSupplierBySupplierCode(defaultSupplierCode);
				if (tjSupplier == null) {
					name = "";
				} else {
					if (!StringUtils.isEmpty(tjSupplier.getName())) {
						name = tjSupplier.getName();
					}
				}
			}
		}
		return name;
	}

	// 分界线(以上为以前代码)
	// ================================================================================================================================
	// ================================================================================================================================
	// ================================================================================================================================
	/**
	 * 跳转添加线下商户页面
	 */
	@RequestMapping("/addBusiness")
	public String addBusiness(Model model, Map<String, Object> map, HttpServletRequest request, Long parentId) {
		if (parentId == null) {
			parentId = getCurrentSupplierId();
		}
		model.addAttribute("parentId", parentId);
		return "/zh/modle/addBusiness";
	}

	/**
	 * 跳转添加线上商户页面
	 */
	@RequestMapping("/registOnLine")
	public String toRegistOnLine(Map<String, Object> map, HttpServletRequest request) {

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
		if (!childrenCategoryList.isEmpty()) {
			for (TdCatePub tdCatePub : childrenCategoryList) {
				categoryNameCn.add(tdCatePub.getPubNameCn());
			}
		}
		String uid = UUID.randomUUID().toString();
		map.put("uid", uid);
		try {
			memcachedClient.set(uid, 60 * 60, uid);
		} catch (Exception e) {
			LOGGER.error("memcachedClient获取uid报错", e);
			e.printStackTrace();
		}
		map.put("category", JSONObject.toJSONString(categoryNameCn));
		return "/zh/agent/user/registOnLine";
	}

	/**
	 * 注册线上的商家
	 */
	@RequestMapping("/registOnLineSupplier")
	public String registOnLineSupplier(MultipartFile idCardFrontTo, MultipartFile idCardVersoTo,
			MultipartFile businessLicenseTo, MultipartFile companyStoreLogoTo, MultipartFile companyLegitimacy,
			MultipartFile companyDetailed, String kaptcha, Map<String, Object> map, OnLineSupplier onLineSupplier,
			HttpServletRequest request, String uid) {
		LOGGER.info("注册：supplier:" + JSON.toJSONString(onLineSupplier));

		// 验证码校验
		try {
			String kaptchaExpected = memcachedClient.get(uid + Constants.KAPTCHA_SESSION_KEY);
			if (!kaptcha.toLowerCase().equals(kaptchaExpected.toLowerCase())) {
				if (getLanguage().equals("/en")) {
					request.setAttribute("message", "Verification code is wrong！");
				} else {
					request.setAttribute("message", "验证码有误！");
				}
				return "forward:/supplier/registOnLine";
			}
		} catch (Exception e) {
			LOGGER.error("注册：  memcached中取验证码出问题！", e);
		}

		// 上传文件的校验
		if (idCardFrontTo == null || idCardVersoTo == null || businessLicenseTo == null) {
			request.setAttribute("isError", 1);
			if (getLanguage().equals("/en")) {
				request.setAttribute("message", "Missing Upload Files");
			} else {
				request.setAttribute("message", "缺少需要上传的文件");
			}
			LOGGER.error("注册：缺少需要上传的文件！");
			return "forward:/supplier/registOnLine";
		} else {
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
				onLineSupplier.setIdCardFront(idCardFrontUrl);
			}
			if (null != idCardVersoTo && !"".equals(Common.getFileExt2(idCardVersoTo.getOriginalFilename()))) {
				onLineSupplier.setIdCardVerso(idCardVersoUrl);
			}
			if (null != businessLicenseTo && !"".equals(Common.getFileExt2(businessLicenseTo.getOriginalFilename()))) {
				onLineSupplier.setBusinessLicense(businessLicenseUrl);
			}

			// 默认图片logo地址
			onLineSupplier.setLogoImgurl("group1/M00/00/31/CgAAElgxxt-Aa7U1AAAjS6o85T0082.jpg");
		}

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
			onLineSupplier.setCompanyStoreLogo(companyStoreLogoUrl);
		}
		if (null != companyLegitimacy && !"".equals(Common.getFileExt2(companyLegitimacy.getOriginalFilename()))) {
			onLineSupplier.setCompanyLegitimacyUrl(companyLegitimacyUrl);
		}
		if (null != companyDetailed && !"".equals(Common.getFileExt2(companyDetailed.getOriginalFilename()))) {
			onLineSupplier.setCompanyDetailedUrl(companyDetailedUrl);
		}

		// 登录pop账号的校验(判断用户名是否重复注册)
		Map<String, String> pmap = new LinkedHashMap<String, String>();
		int count = 1;
		if (!Common.isEmpty(request.getParameter("loginName"))) {
			pmap.put("loginName", request.getParameter("loginName"));
			count = RemoteServiceSingleton.getInstance().getSupplierUserManagerService().getUserByName(pmap);
		}
		if (count == 1) {
			request.setAttribute("isError", 1);
			if (getLanguage().equals("/en")) {
				request.setAttribute("message", "UserName already exists ");
			} else {
				request.setAttribute("message", "用户名已存在");
			}
			LOGGER.error("用户:" + request.getParameter("loginName") + "注册：用户名已存在！");
			return "forward:/supplier/registOnLine";
		}

		String userName = "";
		String password = "";
		String encryptPassword = "";
		if (!Common.isEmpty(request.getParameter("loginName"))) {
			userName = request.getParameter("loginName");
			password = request.getParameter("password");
			encryptPassword = MD5.encrypt(password);
		}
		LOGGER.info("pop user regist set supplyType 51 " + userName);

		SupplierProduct product = new SupplierProduct();
		getObjectFromRequest(product, request);

		// 进行代理商的注册
		RemoteServiceSingleton.getInstance().getSupplierManagerService().registerOnLineSupplier(onLineSupplier,
				userName, encryptPassword, password, product);

		request.setAttribute("name", userName);
		String name = "";
		try {
			name = DES.encrypt(userName, Constants.CCIGMALL);
		} catch (Exception e) {
			LOGGER.error("注册：用户名回传成功页面出错!用户名：" + userName, e);
		}
		LOGGER.info("注册成功：用户名：" + userName);
		return "redirect:/supplier/regist_success?sid=" + name;
	}

	/**
	 * 查看商家二维码
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/qrCode2")
	public String qrCode2(Model model, Long supplierId) {
		if (supplierId != null) {
			BizRcode br = userService.myRcodeByFortoPay(supplierId.toString());
			if (br != null) {
				model.addAttribute("code", br.getRcodeImgTxt());
			}
		}
		return getLanguage() + "/user/QrCode1";
	}

	/**
	 * 获取二级商家类目
	 * 
	 * @throws Exception
	 * @throws IOException
	 * 
	 */
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
	 * 转跳商家列表
	 * 
	 */
	@RequestMapping("/listBusiness")
	public String listBusiness(Model model, Map<String, Object> map, HttpServletRequest request, Long parentId) {

		if (parentId == null) {
			parentId = getCurrentSupplierId();
		}

		model.addAttribute("parentId", parentId);

		return "/zh/agent/offLineSupplier/businessList";
	}

	/**
	 * 获取商家信息
	 * 
	 * @param model
	 * @param contact
	 * @param phone
	 * @param modifyStatus
	 * @param pageBean
	 * @param productName
	 * @param supplierId
	 * @param supplier
	 * @param page
	 * @param parentId
	 * @return
	 */
	@RequestMapping(value = "/getBusinessList")
	public String getBusinessList(Model model, Integer page, Long parentId, String name, String checkStatus) {

		if (parentId == null) {
			parentId = getCurrentSupplierId();
		}
		Supplier supplier = new Supplier();
		supplier.setParentId(parentId);
		supplier.setName(name);

		// 根据status判断查询的条件
		if (null != checkStatus) {
			// 待审核
			if ("0".equals(checkStatus)) {
				supplier.setActiveStatus(-1);
				// supplier.setStatus(0);
				supplier.setStatus(6);
				// 未审核状态应该是0,但是页面列表显示时,创建账号状态也应该设置为未审核的状态,为了方便数据的查询,
				// 先把状态设为 6 ,在mapper.xml中进行判断,当状态为6时,同时查询status为 0 和 1 的状态!
			}
			// 审核不通过
			if ("1".equals(checkStatus)) {
				supplier.setActiveStatus(-1);
				supplier.setStatus(2);
			}
			// 审核通过
			if ("2".equals(checkStatus)) {
				supplier.setActiveStatus(1);
				supplier.setStatus(1);
			}
		}
		PageBean<Supplier> supplierPageBean = new PageBean<Supplier>();
		if (null != page && page != 0) {
			supplierPageBean.setPage(page);
		} else {
			supplierPageBean.setPage(1);
		}
		supplierPageBean.setParameter(supplier);
		supplierPageBean.setOrder("DESC");
		supplierPageBean.setPageSize(10);
		PageBean<Supplier> pageList = supplierManagerService.getPageListOffLine(supplierPageBean);

		// 根据parentId查询代理数据
		Supplier s = supplierManagerService.findSupplier(parentId);
		SupplierAgentType supplierAgentType = supplierAgentTypeService.findChildAgentTypeByCode(s.getType());

		/**
		 * 列表页面显示代理级别,并且可以点击回到特定的代理级别页面
		 */
		// 首先查询出当前登录用户的代理级别
		Supplier sup = supplierManagerService.findSupplier(getCurrentSupplierId());
		if (sup == null) {
			model.addAttribute("ad", "0");
			return "/zh/agent/modelPage/agent_model";
		}
		SupplierAgentType st = supplierAgentTypeService.findAgentByCode(sup.getType());
		// 查询出所有的代理级别
		List<SupplierAgentType> agentList = supplierAgentTypeService.findAllSupplierAgentType();
		// 把从当前登录用户下的代理级别开始放入数组,以当前查看的代理级别为停止条件
		List<String> list = new ArrayList<String>();
		Boolean flag = false;
		for (SupplierAgentType sat : agentList) {
			if (flag == true) {
				list.add(sat.getName());
			}
			if (sat.getName().equals(st.getName())) {
				flag = true;
			}
			if (sat.getName().equals(supplierAgentType.getName())) {
				flag = false;
			}
		}
		Map<String, String> map = new LinkedHashMap<String, String>();
		// 把每一级的代理级别对应的父级id绑定起来
		Long childrenId = Long.valueOf(parentId);
		if (childrenId.equals(getCurrentSupplierId())) {
			map.put(list.get(0), childrenId.toString());
		} else {
			map.put(list.get(list.size() - 1), childrenId.toString());
			for (int i = list.size() - 2; i >= 0; i--) {
				if (childrenId.equals(getCurrentSupplierId())) {
					continue;
				}
				Supplier fs = supplierManagerService.findParentSupplier(childrenId);
				childrenId = fs.getSupplierId();
				map.put(list.get(i), fs.getSupplierId().toString());
			}
		}
		Map<String, String> agentMenu = MapUtils.reverseMap(map);

		model.addAttribute("pass", pageList);
		model.addAttribute("parentId", parentId);
		model.addAttribute("agentMenu", agentMenu);
		model.addAttribute("ps", s.getStatus());

		return "/zh/agent/modelPage/business_model";

	}

	/**
	 * 查看回显商户
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/modifyBusiness")
	public String modifyBusiness(Model model, Long supplierId, Map<String, Object> map) throws Exception {
		Supplier supplier = supplierManagerService.findSupplier(supplierId);

		// SupplierPartnerArea area
		// =supplierManagerService.findPartnerArea(supplier.getSupplierId(),
		// null);

		// 查看银行 信息
		BankBranch bankBranch = bankService.findBankBranchByCode(supplier.getAccoutBankno());

		model.addAttribute("bankBranch", bankBranch);
		map.put("bankBranch", bankBranch);

		model.addAttribute("sr", supplier);
		if (null != supplier && supplier.getActiveStatus() == 1) {
			return "/zh/modle/modifyBusiness";
		}
		return "/zh/modle/modifyBusiness";
	}

	/**
	 * 调到下级代理流水列表页面
	 * 
	 */
	@RequestMapping(value = "/getLookTradingFlow")
	public String lookTradingFlow(Model model, Long supplierId) {
		if (supplierId == null) {
			supplierId = getCurrentSupplierId();
		}
		model.addAttribute("sid", supplierId);
		return "/zh/agent/tradingFlowList";
	}

	/**
	 * 查看商家收入流水页面
	 */
	@RequestMapping(value = "/busTransRecord")
	public String busTransRecord(Model model, Long supplierId) {

		model.addAttribute("supplierId", supplierId);
		return "/zh/agent/busTransRecordPage";
	}

	/**
	 * 获取下级代理流水列表数据
	 * 
	 */
	@RequestMapping(value = "/getLookTradingFlowData")
	public String lookTradingFlow(Model model, Long supplierId, Integer page, Date startTime, Date endTime) {

		if (null == page || page == 0) {
			page = 1;
		}
		User user = userService.findUserBySupplierId(supplierId);
		if (user != null) {
			PageBean<SqwAccountRecord> flow = null;
			BigDecimal bigDecimal =null;
			if(startTime!=null && endTime!=null){
				if( startTime.equals(endTime)){
					flow=psqwAccountRecordService.selectShareInterestRecordByUserAndDay(user.getUserId(), page, 10,  startTime);
					bigDecimal=psqwAccountRecordService.selectShareInterestBalanceSumByUserAndDay(user.getUserId(), startTime);
				}else{
					flow = psqwAccountRecordService.selectShareInterestRecordByUser(user.getUserId(),
							page, 10, startTime, endTime);
					bigDecimal=psqwAccountRecordService.selectShareInterestBalanceSumByUser(user.getUserId(),
							startTime, endTime);
				}
			}else{
				flow = psqwAccountRecordService.selectShareInterestRecordByUser(user.getUserId(),
						page, 10, startTime, endTime);
				bigDecimal=psqwAccountRecordService.selectShareInterestBalanceSumByUser(user.getUserId(),
						startTime, endTime);
			}
			
			model.addAttribute("acc", flow);
			model.addAttribute("bigDecimal", bigDecimal);

		}
		Supplier s = supplierManagerService.findSupplier(supplierId);
		model.addAttribute("sid", supplierId);
		model.addAttribute("parentId", s.getParentId());
		return "/zh/agent/modelPage/tradingFlow_model";

	}

	/**
	 * 获取商家收入流水数据
	 * 
	 */
	@RequestMapping(value = "/getLookTransRecord")
	public String getLookTransRecord(Model model, Long supplierId, Integer page, Date startTime, Date endTime) {

		//若开始和结束时间相同时,查询当天数据,需要对时间进行初始化
		if (startTime != null && endTime != null && startTime.equals(endTime)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String format = sdf.format(startTime);
			String startString = format + " 00:00:00";
			String endString = format + " 23:59:59";
			try {
				startTime = sdf2.parse(startString);
				endTime = sdf2.parse(endString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if (null == page || page == 0) {
			page = 1;
		}
		if (supplierId != null) {
			PageBean<OrderDTO> order = customerOrderService.findAlreadyFinishOfflineOrderBySupplierId(supplierId, page,
					10, startTime, endTime);
			BigDecimal bigDecimal = customerOrderService.getAlreadyFinishOfflineOrderPriceSumBySupplierId(supplierId,
					startTime, endTime);
			model.addAttribute("acc", order);
			model.addAttribute("bigDecimal", bigDecimal);

		}
		Supplier s = supplierManagerService.findSupplier(supplierId);
		model.addAttribute("supplierId", supplierId);
		model.addAttribute("parentId", s.getParentId());
		return "/zh/agent/modelPage/busTransRecord_model";

	}

	/**
	 * 转跳商家优惠卷
	 */
	// @RequestMapping(value = "/rechargeCoupon")
	// public String rechargeCoupon(Model model, Long supplierId) {
	// // 获取当前的登录用户
	// Supplier supplier = getSupplier();
	// Long supplierId2 = supplier.getSupplierId();
	// User user = userService.findUserBySupplierId(supplierId2);
	// User user2 = userService.findUserBySupplierId(supplierId);
	// String name = supplierManagerService.findSupplier(supplierId).getName();
	// model.addAttribute("name", name);
	// if (user2 != null) {
	// List<SqwUserAccountBalance> balance2 =
	// psqwAccountRecordService.getPuserAccountBalance(user2.getUserId(),
	// Constants.COUPT);
	// if (balance2.size() == 0) {
	// model.addAttribute("decimal2", "请开通优惠券账户");
	//
	// } else {
	// BigDecimal decimal2 = balance2.get(0).getAccountBalance();
	// model.addAttribute("decimal2", decimal2);
	// }
	//
	// }
	// if (user != null) {
	// List<SqwUserAccountBalance> balance =
	// psqwAccountRecordService.getPuserAccountBalance(user.getUserId(),
	// Constants.COUPT);
	// if (balance.size() == 0) {
	// model.addAttribute("decimal", "0.000");
	//
	// } else {
	// BigDecimal decimal = balance.get(0).getAccountBalance();
	// model.addAttribute("decimal", decimal);
	// }
	//
	// }
	// model.addAttribute("supplierId", supplierId);
	// model.addAttribute("supplier", supplier);
	// return "/zh/agent/modelPage/rechargeCoupon_model";
	//
	// }

	/**
	 * 
	 * 充值优惠券
	 */
	// @RequestMapping(value = "/rechargeCouponYes")
	// public String rechargeCouponYes(Model model, Long supplierId, BigDecimal
	// coupon, String memo, BigDecimal decimal) {
	// try {
	// // 要转账的账户
	// User user = userService.findUserBySupplierId(supplierId);
	// model.addAttribute("supplierId", supplierId);
	// // 当前登录用户
	// Long supplierId2 = getSupplier().getSupplierId();
	// User user2 = userService.findUserBySupplierId(supplierId2);
	// String millis = System.currentTimeMillis() + "";
	// if (getSupplier().getType() == 1601) {
	// psqwAccountRecordService.supTransferAccountToSup(millis,
	// user2.getUserId(), coupon, user.getUserId(),
	// coupon, user2.getRealName(), memo, BigDecimal.valueOf(0.0));
	// } else {
	// int i = decimal.compareTo(coupon);
	// if (i != -1) {
	// psqwAccountRecordService.supTransferAccountToSup(millis,
	// user2.getUserId(), coupon,
	// user.getUserId(), coupon, user2.getRealName(), memo,
	// BigDecimal.valueOf(0.0));
	// }
	// }
	// } catch (Exception e) {
	// }
	//
	// return "/zh/agent/modelPage/rechargeCoupon_model";
	//
	// }

	/**
	 * 转跳商家优惠卷
	 */
	// @RequestMapping(value = "/rechargeCoupon1")
	// @ResponseBody
	// public String rechargeCoupon1(Model model, Long supplierId) {
	// // 获取当前的登录用户
	// Map<String, Object> map = new HashMap<String, Object>();
	//
	// Supplier supplier = getSupplier();
	// Integer type = supplier.getType();
	// Long supplierId2 = supplier.getSupplierId();
	// User user = userService.findUserBySupplierId(supplierId2);
	// User user2 = userService.findUserBySupplierId(supplierId);
	// String name = supplierManagerService.findSupplier(supplierId).getName();
	// // model.addAttribute("name", name);
	// map.put("name", name);
	// if (user2 != null) {
	// List<SqwUserAccountBalance> balance2 =
	// psqwAccountRecordService.getPuserAccountBalance(user2.getUserId(),
	// Constants.COUPT);
	// if (balance2.size() == 0) {
	// // model.addAttribute("decimal", "0.000");
	// map.put("decimal2", "0.000");
	//
	// } else {
	// BigDecimal decimal2 = balance2.get(0).getAccountBalance();
	// // model.addAttribute("decimal2", decimal2);
	// map.put("decimal2", decimal2);
	// }
	//
	// }
	// if (user != null) {
	// List<SqwUserAccountBalance> balance =
	// psqwAccountRecordService.getPuserAccountBalance(user.getUserId(),
	// Constants.COUPT);
	// if (balance.size() == 0) {
	// // model.addAttribute("decimal", "0.000");
	// map.put("decimal", "0.000");
	//
	// } else {
	// BigDecimal decimal = balance.get(0).getAccountBalance();
	// // model.addAttribute("decimal", decimal);
	// map.put("decimal", decimal);
	// }
	//
	// }
	// // model.addAttribute("supplierId", supplierId);
	// map.put("supplierId", supplierId);
	// // model.addAttribute("supplier", supplier);
	// map.put("type", type.toString());
	// String jsonString = JSONArray.toJSONString(map);
	// return jsonString;
	//
	// }

	/**
	 * 商家文件导出
	 */
	// @RequestMapping(value = "/fileExport")
	// public String fileExport(Model model, Long supplierId,
	// HttpServletResponse response) {
	// try {
	//
	// Long parentId = getCurrentSupplierId();
	// Supplier supplier1 = new Supplier();
	// supplier1.setParentId(parentId);
	// PageBean<Supplier> supplierPageBean = new PageBean<Supplier>();
	//
	// supplierPageBean.setParameter(supplier1);
	// supplierPageBean.setOrder("DESC");
	// supplierPageBean.setPageSize(10);
	// PageBean<Supplier> pageBeanlist =
	// supplierManagerService.getPageListLine(supplierPageBean);
	// List<Supplier> list = pageBeanlist.getResult();
	// HSSFWorkbook workbook = new HSSFWorkbook();
	//
	// // 创建标签页
	// HSSFSheet sheet = workbook.createSheet("商家信息");
	// HSSFRow headRow = sheet.createRow(0);
	// // 创建单元格设置单元格内容
	// headRow.createCell(0).setCellValue("企业名称");
	// headRow.createCell(1).setCellValue("编号");
	// headRow.createCell(2).setCellValue("联系人");
	// headRow.createCell(3).setCellValue("手机号");
	// headRow.createCell(4).setCellValue("公司信息");
	//
	// // sheet.autoSizeColumn(1, true);
	// for (Supplier supplier : list) {
	// HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
	// dataRow.createCell(0).setCellValue(supplier.getName());
	// dataRow.createCell(1).setCellValue(supplier.getSupplierCode());
	// dataRow.createCell(2).setCellValue(supplier.getContact());
	// dataRow.createCell(3).setCellValue(supplier.getPhone());
	// dataRow.createCell(4).setCellValue(supplier.getCompanyInfo());
	//
	// }
	// sheet.autoSizeColumn(1);
	// String filename = "sj.xls";
	//
	// // 文件加载：一个流，两个头（1、文件MIME类型 2、文件打开方式（浏览器内部打开inline，作为附件下载））
	// response.setHeader("content-Type", "application/vnd.ms-excel");
	// response.setHeader("content-disposition", "attachment;fileName=" +
	// filename);
	//
	// OutputStream stream;
	//
	// stream = response.getOutputStream();
	// workbook.write(stream);
	// workbook.close();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// return null;
	// }

	/**
	 * 
	 * 修改商户
	 */
	// @RequestMapping("/save1")
	// public String save1(MultipartFile myfile, MultipartFile myfile1,
	// HttpServletRequest request, MultipartFile myfile2,
	// Long province, Long city, Long country, MultipartFile myfile3,
	// MultipartFile myfile4, Long supplierId,
	// OffLineSupplier offLineSupplier) {
	//
	// // 修改supplier数据
	// String myfileUrl = "";
	// String myfile1Url = "";
	// String myfile2Url = "";
	// String myfile3Url = "";
	// String myfile4Url = "";
	// try {
	// myfileUrl = UploadFileUtil.uploadFile(myfile.getInputStream(),
	// Common.getFileExt2(myfile.getOriginalFilename()), null);
	// if (null != myfile1 &&
	// !"".equals(Common.getFileExt2(myfile1.getOriginalFilename()))) {
	// myfile1Url = UploadFileUtil.uploadFile(myfile1.getInputStream(),
	// Common.getFileExt2(myfile1.getOriginalFilename()), null);
	// }
	// if (null != myfile3 &&
	// !"".equals(Common.getFileExt2(myfile3.getOriginalFilename()))) {
	// myfile3Url = UploadFileUtil.uploadFile(myfile3.getInputStream(),
	// Common.getFileExt2(myfile3.getOriginalFilename()), null);
	// }
	// if (null != myfile4 &&
	// !"".equals(Common.getFileExt2(myfile4.getOriginalFilename()))) {
	// myfile4Url = UploadFileUtil.uploadFile(myfile4.getInputStream(),
	// Common.getFileExt2(myfile4.getOriginalFilename()), null);
	// }
	// if (null != myfile2 &&
	// !"".equals(Common.getFileExt2(myfile2.getOriginalFilename()))) {
	// myfile2Url = UploadFileUtil.uploadFile(myfile2.getInputStream(),
	// Common.getFileExt2(myfile2.getOriginalFilename()), null);
	// }
	// } catch (Exception e) {
	// LOGGER.error("注册：上传文件到图片服务器出错！", e);
	// }
	// try {
	// offLineSupplier.setCompanyLegitimacyUrl(myfileUrl);
	// if (null != myfile1 &&
	// !"".equals(Common.getFileExt2(myfile1.getOriginalFilename()))) {
	// offLineSupplier.setCompanyDetailedUrl(myfile1Url);
	// }
	// if (null != myfile3 &&
	// !"".equals(Common.getFileExt2(myfile3.getOriginalFilename()))) {
	// offLineSupplier.setIdCardFront(myfile3Url);
	// }
	// if (null != myfile4 &&
	// !"".equals(Common.getFileExt2(myfile4.getOriginalFilename()))) {
	// offLineSupplier.setIdCardVerso(myfile4Url);
	// }
	// if (null != myfile2 &&
	// !"".equals(Common.getFileExt2(myfile2.getOriginalFilename()))) {
	// offLineSupplier.setLogoImgurl(myfile2Url);
	// } else {
	// offLineSupplier.setLogoImgurl("group1/M00/00/31/CgAAElgxxt-Aa7U1AAAjS6o85T0082.jpg");
	// // 默认图片logo地址
	// }
	//
	// Supplier supplier = new Supplier();
	// BeanUtils.copyProperties(supplier, offLineSupplier);
	// supplier.setSupplierId(supplierId);
	// RemoteServiceSingleton.getInstance().getSupplierManagerService().updataSupplier(supplier);
	//
	// // 修改代理地区数据
	// SupplierPartnerArea supplierPartnerArea = new SupplierPartnerArea();
	// supplierPartnerArea.setType(3);
	// supplierPartnerArea.setProvinceId(province);
	// if (city != null) {
	// supplierPartnerArea.setType(2);
	// supplierPartnerArea.setCityId(city);
	// }
	// if (country != null) {
	// supplierPartnerArea.setType(1);
	// supplierPartnerArea.setCountyId(country);
	// }
	// supplierPartnerArea.setSupplierId(supplier.getSupplierId());
	//
	// supplierManagerService.updateSupplierPartnerAera(supplierPartnerArea);
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return "/zh/product/bussionlist";
	// }

	/**
	 * 添加线下商户
	 */
	// @RequestMapping("/save")
	// public String save(OffLineSupplier offLineSupplier, MultipartFile
	// idCardFrontTo, MultipartFile idCardVersoTo,
	// MultipartFile businessLicenseTo, MultipartFile companyStoreLogoTo,
	// MultipartFile companyLegitimacy,
	// MultipartFile companyDetailed, String kaptcha, Map<String, Object> map,
	// HttpServletRequest request,
	// String uid, Long parentId) {
	// LOGGER.info("注册：supplier:" + JSON.toJSONString(offLineSupplier));
	//
	// // 验证码校验
	// try {
	// String kaptchaExpected = memcachedClient.get(uid +
	// Constants.KAPTCHA_SESSION_KEY);
	// if (!kaptcha.toLowerCase().equals(kaptchaExpected.toLowerCase())) {
	// if (getLanguage().equals("/en")) {
	// request.setAttribute("message", "Verification code is wrong！");
	// } else {
	// request.setAttribute("message", "验证码有误！");
	// }
	// return "/zh/modle/addBusiness";
	// }
	// } catch (Exception e) {
	// LOGGER.error("注册： memcached中取验证码出问题！", e);
	// }
	//
	// // 上传文件的校验
	// if (idCardFrontTo == null || idCardVersoTo == null || businessLicenseTo
	// == null) {
	// request.setAttribute("isError", 1);
	// if (getLanguage().equals("/en")) {
	// request.setAttribute("message", "Missing Upload Files");
	// } else {
	// request.setAttribute("message", "缺少需要上传的文件");
	// }
	// LOGGER.error("注册：缺少需要上传的文件！");
	// return "forward:/supplier/regisAgent";
	// } else {
	// String idCardFrontUrl = "";
	// String idCardVersoUrl = "";
	// String businessLicenseUrl = "";
	// try {
	// // 身份证正面
	// if (null != idCardFrontTo &&
	// !"".equals(Common.getFileExt2(idCardFrontTo.getOriginalFilename()))) {
	// idCardFrontUrl =
	// UploadFileUtil.uploadFile(idCardFrontTo.getInputStream(),
	// Common.getFileExt2(idCardFrontTo.getOriginalFilename()), null);
	// }
	// // 身份证反面
	// if (null != idCardVersoTo &&
	// !"".equals(Common.getFileExt2(idCardVersoTo.getOriginalFilename()))) {
	// idCardVersoUrl =
	// UploadFileUtil.uploadFile(idCardVersoTo.getInputStream(),
	// Common.getFileExt2(idCardVersoTo.getOriginalFilename()), null);
	// }
	// // 营业执照
	// if (null != businessLicenseTo
	// &&
	// !"".equals(Common.getFileExt2(businessLicenseTo.getOriginalFilename())))
	// {
	// businessLicenseUrl =
	// UploadFileUtil.uploadFile(businessLicenseTo.getInputStream(),
	// Common.getFileExt2(businessLicenseTo.getOriginalFilename()), null);
	// }
	// } catch (Exception e) {
	// LOGGER.error("注册：上传文件到图片服务器出错！", e);
	// }
	// if (null != idCardFrontTo &&
	// !"".equals(Common.getFileExt2(idCardFrontTo.getOriginalFilename()))) {
	// offLineSupplier.setIdCardFront(idCardFrontUrl);
	// }
	// if (null != idCardVersoTo &&
	// !"".equals(Common.getFileExt2(idCardVersoTo.getOriginalFilename()))) {
	// offLineSupplier.setIdCardVerso(idCardVersoUrl);
	// }
	// if (null != businessLicenseTo &&
	// !"".equals(Common.getFileExt2(businessLicenseTo.getOriginalFilename())))
	// {
	// offLineSupplier.setBusinessLicense(businessLicenseUrl);
	// }
	//
	// // 默认图片logo地址
	// offLineSupplier.setLogoImgurl("group1/M00/00/31/CgAAElgxxt-Aa7U1AAAjS6o85T0082.jpg");
	// }
	//
	// String companyStoreLogoUrl = "";
	// String companyLegitimacyUrl = "";
	// String companyDetailedUrl = "";
	// try {
	// // 经营门头照
	// if (null != companyStoreLogoTo
	// &&
	// !"".equals(Common.getFileExt2(companyStoreLogoTo.getOriginalFilename())))
	// {
	// companyStoreLogoUrl =
	// UploadFileUtil.uploadFile(companyStoreLogoTo.getInputStream(),
	// Common.getFileExt2(companyStoreLogoTo.getOriginalFilename()), null);
	// }
	// // 证明企业合法性的证明文件
	// if (null != companyLegitimacy &&
	// !"".equals(Common.getFileExt2(companyLegitimacy.getOriginalFilename())))
	// {
	// companyLegitimacyUrl =
	// UploadFileUtil.uploadFile(companyLegitimacy.getInputStream(),
	// Common.getFileExt2(companyLegitimacy.getOriginalFilename()), null);
	// }
	// // 公司详情文件
	// if (null != companyDetailed &&
	// !"".equals(Common.getFileExt2(companyDetailed.getOriginalFilename()))) {
	// companyDetailedUrl =
	// UploadFileUtil.uploadFile(companyDetailed.getInputStream(),
	// Common.getFileExt2(companyDetailed.getOriginalFilename()), null);
	// }
	// } catch (Exception e) {
	// LOGGER.error("注册：上传文件到图片服务器出错！", e);
	// }
	// if (null != companyStoreLogoTo &&
	// !"".equals(Common.getFileExt2(companyStoreLogoTo.getOriginalFilename())))
	// {
	// offLineSupplier.setCompanyStoreLogo(companyStoreLogoUrl);
	// }
	// if (null != companyLegitimacy &&
	// !"".equals(Common.getFileExt2(companyLegitimacy.getOriginalFilename())))
	// {
	// offLineSupplier.setCompanyLegitimacyUrl(companyLegitimacyUrl);
	// }
	// if (null != companyDetailed &&
	// !"".equals(Common.getFileExt2(companyDetailed.getOriginalFilename()))) {
	// offLineSupplier.setCompanyDetailedUrl(companyDetailedUrl);
	// }
	//
	//
	// // 对登录pop的用户名进行校验
	// Map<String, String> pmap = new LinkedHashMap<String, String>();
	// int count = 1;
	// if (!Common.isEmpty(request.getParameter("loginName"))) {
	// pmap.put("loginName", request.getParameter("loginName"));
	// count =
	// RemoteServiceSingleton.getInstance().getSupplierUserManagerService().getUserByName(pmap);
	// } else {
	// request.setAttribute("message", "用户名为空");
	// return "/zh/modle/addBusiness";
	// }
	// if (count == 1) {
	// request.setAttribute("isError", 1);
	// if (getLanguage().equals("/en")) {
	// request.setAttribute("message", "UserName already exists ");
	// } else {
	// request.setAttribute("message", "用户名已存在");
	// }
	// LOGGER.error("用户:" + request.getParameter("loginName") + "注册：用户名已存在！");
	// return "/zh/modle/addBusiness";
	// }
	//
	// // 登录pop的账号用户名
	// String userName = "";
	// String password = "";
	// String encryptPassword = "";
	// if (!Common.isEmpty(request.getParameter("loginName"))) {
	// userName = request.getParameter("loginName");
	// password = request.getParameter("password"); // 记录明文，为了unicorn创建账号发送短信用
	// encryptPassword = MD5.encrypt(password);
	// }
	// LOGGER.info("pop user regist set supplyType 51 " + userName);
	//
	// // 代理id（即是上级父id）
	// Long proxySupplierid = Long.parseLong(request.getParameter("parentId"));
	// // 添加企业类型
	// Integer companyType =
	// Integer.parseInt(request.getParameter("companyType"));
	// // 添加企业类目
	// Integer companyCategory =
	// Integer.parseInt(request.getParameter("category"));
	//
	// // 企业折扣(保留两位小数)
	// /*
	// * DecimalFormat df = new DecimalFormat(".00"); String disCount =
	// * df.format(request.getParameter("disCount"));
	// */
	// String disCount = request.getParameter("disCount");
	//
	// // 企业省市区数据
	// Long province = Long.valueOf(request.getParameter("provinceId"));
	// Long city = Long.valueOf(request.getParameter("cityId"));
	// Long country = Long.valueOf(request.getParameter("areaId"));
	//
	// // 创建者
	// Long creatorid = getCurrentSupplierId();
	//
	// RemoteServiceSingleton.getInstance().getSupplierManagerService().registerOffLineSupplier(offLineSupplier,
	// proxySupplierid, companyType, companyCategory, creatorid, userName,
	// password, encryptPassword, disCount,
	// province, city, country);
	//
	// request.setAttribute("name", userName);
	// LOGGER.info("注册成功：用户名：" + userName);
	// return "/zh/agent/offLineSupplier/businessList";
	// }

	/**
	 * mp二维码
	 * 
	 * @param model
	 * @return
	 */
	// @RequestMapping("/qrCodeSupplier")
	// public String qrCodeSupplier(Model model) {
	// SupplierUser user = getCurrentUser();
	// if (user != null) {
	//
	// User user2 =
	// userService.findsingleUserBySupplierId(Long.parseLong(user.getSupplierId().toString()));
	// if(user2!=null){
	// BizRcode bizRode =
	// userService.myRcodeByFortoM(user2.getUserId().toString());
	// model.addAttribute("bizRode", bizRode);
	// }
	// }
	// return getLanguage() + "/user/QrCode";
	// }
}
