package com.mall.controller.order;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.csource.upload.UploadFileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.category.api.rpc.BaseDataServiceRpc;
import com.mall.category.po.AgentProvince;
import com.mall.customer.order.utils.PKUtils;
//import com.mall.kefu.util.ValidateUtil;
import com.mall.mybatis.utility.PageBean;
import com.mall.stock.api.rpc.WarehouseService;
import com.mall.stock.dto.StockDetailDTO;
import com.mall.stock.po.Warehouse;
//import com.mall.supplier.order.api.rpc.ApplyOutOrderItemService;
//import com.mall.supplier.order.api.rpc.ApplyOutOrderService;
//import com.mall.supplier.order.api.rpc.WaterService;
//import com.mall.supplier.order.dto.ApplyOutOrderDto;
//import com.mall.supplier.order.dto.ApplyOutOrderFileDto;
//import com.mall.supplier.order.dto.ApplyOutOrderItemDto;
//import com.mall.supplier.order.po.ApplyOutOrderItem;
import com.mall.authority.client.constant.ConfigConstant;
import com.mall.authority.client.util.UserUtil;
import com.mall.authority.client.util.ValidateUtil;
import com.mall.bean.authority.Module;
import com.mall.bean.authority.User;
import com.mall.bean.authority.request.UserForm;
import com.mall.bean.authority.response.MenuBean;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;

@Controller
@RequestMapping(value = "/Apply")
public class ApplyController extends BaseController {
	
	
	private static final Log LOGGER = LogFactory.getLogger(ApplyController.class);
	private static Map<Integer, String> outTypeMap = new LinkedHashMap<Integer, String>();
	@RequestMapping(value = "/findApplyOrder")
	public String findApplyOrder(Model model) {
		WarehouseService warehouseService = (WarehouseService) RemoteServiceSingleton
				.getInstance().getAppService("warehouseService");
		List<Warehouse> warehouses = warehouseService
				.findWarehouseByWareseName(null);
		model.addAttribute("warehouses", warehouses);
		outTypeMap = Constants.OutType.getOutTypeMap();
        model.addAttribute("outTypeMap", outTypeMap);
		return "/apply/findApplyOrder";
	}

	/*@RequestMapping(value = "/findApplyOrderList")
	public String findApplyOrderList(HttpServletRequest request,
			HttpServletResponse response, Model model, Integer page) {
		java.text.SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String applyChar = request.getParameter("applyChar");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String createBy = request.getParameter("createBy");
		String warehouseId = request.getParameter("warehouseId");
		String businessType = request.getParameter("businessType");
		String outType = request.getParameter("outType");
		String otherCusType = request.getParameter("otherCusType");
		if (outType.equals("2") || outType == "2") {
			if (!ValidateUtil.isEmpty(otherCusType)) {
				outType = otherCusType;
			}
		}
		String status = request.getParameter("status");
		PageBean<ApplyOutOrderDto> pageBean = new PageBean<ApplyOutOrderDto>();
		ApplyOutOrderService applyOutOrderService = (ApplyOutOrderService) RemoteServiceSingleton
				.getInstance().getAppService("applyOutOrderService");
		ApplyOutOrderDto dto = new ApplyOutOrderDto();
		try {
			if (applyChar != null && !"".equals(applyChar)) {
				dto.setApplyChar(applyChar);
			}
			if (businessType != null && !"".equals(businessType)) {
				dto.setBusinessType(businessType);
			}
			if (startTime != null && !"".equals(startTime)) {
				dto.setStartTime(format.parse(startTime));
			}
			if (endTime != null && !"".equals(endTime)) {
				dto.setEndTime(format.parse(endTime));
			}

			if (createBy != null && !"".equals(createBy)) {
				dto.setCreateBy(createBy);
			}
			if (status != null && !"".equals(status)) {
				dto.setStatus(Integer.parseInt(status));
			}
			if (warehouseId != null && !"".equals(warehouseId)) {
				dto.setWarehouseId(Long.parseLong(warehouseId));
			}
			if (outType != null && !"".equals(outType)) {
				dto.setOutType(outType);
			}
			pageBean.setParameter(dto);
			pageBean.setPageSize(Constants.PAGESIZE);
			if (page != null) {
				pageBean.setPage(page);
			} else {
				pageBean.setPage(Constants.INT1);
			}
			pageBean = applyOutOrderService.findApplyOutOrderListPage(pageBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("pb", pageBean);
		return "/apply/findApplyOrderList";
	}

	@RequestMapping(value = "/findApplyOrderItemList")
	public String findApplyOrderItemList(HttpServletRequest request,
			HttpServletResponse response, Model model, Long id) {
		ApplyOutOrderItemService applyOutOrderItemService = (ApplyOutOrderItemService) RemoteServiceSingleton
				.getInstance().getAppService("applyOutOrderItemService");
		List<ApplyOutOrderItemDto> itemDtos = applyOutOrderItemService
				.findApplyOutOrderItem(id);
		model.addAttribute("itemDtos", itemDtos);
		return "/apply/findApplyOrderItemList";
	}
*/
	/*@RequestMapping(value = "/saveApply")
	public String saveApply(HttpServletRequest request,
			HttpServletResponse response,Model model) {
		java.text.SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		model.addAttribute("now", format.format(new Date()));
		format = new SimpleDateFormat("yyyyMMdd");
		WarehouseService warehouseService = (WarehouseService) RemoteServiceSingleton
				.getInstance().getAppService("warehouseService");
		WaterService waterService = (WaterService) RemoteServiceSingleton
				.getInstance().getAppService("waterService");
		String number = "TB" + format.format(new Date())
				+ waterService.getWater("TB");
		model.addAttribute("number", number);
		List<Warehouse> warehouses = warehouseService
				.findWarehouseByWareseName(null);
		model.addAttribute("warehouses", warehouses);
		model.addAttribute("sid",
				com.mall.retailer.order.common.PKUtils.getLongPrimaryKey());
		model.addAttribute("createBy", this.getCurrentUser().getUsername());
		
		*//**
		 * 是否是领用出库用户
		 *//*
		boolean otoKey = false;
		
		User user =UserUtil.getUserFromCookie(request, response,ConfigConstant.MEMBER);
		
		MenuBean menu = UserUtil.findMenuListByUser(request, response, "KF", user);
		
		List<Module> module = menu.getModuleList();
		
		for (Module module2 : module) {
			
			if(module2.getUrl().equals("/selloutstorage/saleOutstorage")){
				
				otoKey = true;
				break;
			}
		}
		
		if(otoKey == true){
			model.addAttribute("isOutStorage", "1");
		}
		outTypeMap = Constants.OutType.getOutTypeMap();
        model.addAttribute("outTypeMap", outTypeMap);
		
		return "/apply/saveApply";
	}*/

	@RequestMapping(value = "/uploadApply", method = RequestMethod.POST)
	@ResponseBody
	public String uploadCost(HttpServletRequest request,
			HttpServletResponse response) {
		String imgurl = "";
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile file = multipartRequest.getFile("Filedata");

			imgurl = UploadFileUtil.uploadFile(
					file.getInputStream(),
					file.getOriginalFilename().substring(
							file.getOriginalFilename().lastIndexOf(".") + 1,
							file.getOriginalFilename().length()), null);
			System.out.println("http://image01.zhongjumall.com/" + imgurl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "http://image01.zhongjumall.com/"
				+ imgurl
				+ ";"
				+ imgurl.substring(imgurl.lastIndexOf("/") + 1, imgurl.length());
	}

	/*@RequestMapping(value = "/doSave", method = RequestMethod.POST)
	@ResponseBody
	public String doSave(HttpServletRequest request,
			HttpServletResponse response) {
		String result = "";
		ApplyOutOrderDto orderDto = new ApplyOutOrderDto();
		ApplyOutOrderService applyOutOrderService = (ApplyOutOrderService) RemoteServiceSingleton
				.getInstance().getAppService("applyOutOrderService");
		java.text.SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String applyChar = request.getParameter("applyChar");
		String sid = request.getParameter("sid");
		String createTime = request.getParameter("createTime");
		String createBy = request.getParameter("createBy");
		String warehouseId = request.getParameter("warehouse");
		String warehouseName = request.getParameter("warehouseName");
		String outType = request.getParameter("outType");
		String otherCusType = request.getParameter("otherCusType");
		String scrapType = request.getParameter("scrapType");
		String carriage = request.getParameter("carriage");
		String thirdOrderId = request.getParameter("thirdOrderId");
		String useCause = request.getParameter("useCause");
		if (outType.equals("2") || outType == "2") {
			outType = otherCusType;
		}
		if(outType.equals("4") || outType == "4"){
			useCause = scrapType;
		}
		String businessType = request.getParameter("businessType");
		String remark = request.getParameter("remark");
		String section = request.getParameter("section");
		String fullName = request.getParameter("fullName");
		String address = request.getParameter("address");
		String mobile = request.getParameter("mobile");
		String provinceId = request.getParameter("provinceId");
		String provinceName = request.getParameter("provinceName");
		String cityId = request.getParameter("cityId");
		String cityName = request.getParameter("cityName");
		String areaId = request.getParameter("areaId");
		String areaName = request.getParameter("areaName");
		try {
			if (provinceId != null && !"".equals(provinceId)) {
				orderDto.setProvinceId(Long.valueOf(provinceId));
			}
			if (provinceName != null && !"".equals(provinceName)) {
				orderDto.setProvinceName(provinceName);
			}
			if (cityId != null && !"".equals(cityId)) {
				orderDto.setCityId(Long.valueOf(cityId));
			}
			if (cityName != null && !"".equals(cityName)) {
				orderDto.setCityName(cityName);
			}
			if (areaId != null && !"".equals(areaId)) {
				orderDto.setAreaId(Long.valueOf(areaId));
			}
			if (areaName != null && !"".equals(areaName)) {
				orderDto.setAreaName(areaName);
			}
			if (applyChar != null && !"".equals(applyChar)) {
				orderDto.setApplyChar(applyChar);
			}
			if (fullName != null && !"".equals(fullName)) {
				orderDto.setFullName(fullName);
			}
			if (address != null && !"".equals(address)) {
				orderDto.setAddress(address);
			}
			if (mobile != null && !"".equals(mobile)) {
				orderDto.setMobile(mobile);
			}
			if (sid != null && !"".equals(sid)) {
				orderDto.setSid(Long.parseLong(sid));
			}
			if (createTime != null && !"".equals(createTime)) {
				orderDto.setCreateTime(format.parse(createTime));
			}
			if (createBy != null && !"".equals(createBy)) {
				orderDto.setCreateBy(createBy);
			}
			if (warehouseId != null && !"".equals(warehouseId)) {
				orderDto.setWarehouseId(Long.parseLong(warehouseId));
			}
			if (warehouseName != null && !"".equals(warehouseName)) {
				orderDto.setWarehouseName(warehouseName);
			}
			if (outType != null && !"".equals(outType)) {
				orderDto.setOutType(outType);
			}
			if (businessType != null && !"".equals(businessType)) {
				orderDto.setBusinessType(businessType);
			} else {
				orderDto.setBusinessType("209");
			}
			if (remark != null && !"".equals(remark)) {
				orderDto.setRemark(remark);
			}
			if (section != null && !"".equals(section)) {
				orderDto.setSection(section);
			}
			if (carriage != null && !"".equals(carriage)) {
				orderDto.setCarriage(new BigDecimal(carriage));
			} else {
				orderDto.setCarriage(new BigDecimal(0));
			}
			if (thirdOrderId != null && !"".equals(thirdOrderId)) {
				orderDto.setThirdOrderId(thirdOrderId);
			}
			if (useCause != null && !"".equals(useCause)) {
				orderDto.setUseCause(useCause);
			}
			orderDto.setStatus(1);
			List<ApplyOutOrderItemDto> itemDtos = new ArrayList<ApplyOutOrderItemDto>();
			itemDtos = getApplyOutOrderItem(orderDto, request);
			if(itemDtos.size()==0){
				return "error";
			}
			orderDto.setItemDtos(itemDtos);
			orderDto.setFileDtos(getApplyOutOrderFileDto(orderDto, request));
			applyOutOrderService.insertSelective(orderDto);
			result = "OK";
		} catch (Exception e) {
			e.printStackTrace();
			result = "NO";
		}

		return result;
	}

	private List<ApplyOutOrderItemDto> getApplyOutOrderItem(
			ApplyOutOrderDto orderDto, HttpServletRequest request)
			throws Exception {
		java.text.SimpleDateFormat Dateformat = new SimpleDateFormat(
				"yyyy-MM-dd");
		List<ApplyOutOrderItemDto> itemDtos = new ArrayList<ApplyOutOrderItemDto>();
		String[] chk = request.getParameter("chk").split(",");
		String[] pcode = request.getParameterValues("pcode");
		String[] barCode = request.getParameterValues("barCode");
		String[] pname = request.getParameterValues("pname");
		String[] format = request.getParameterValues("format");
		String[] unit = request.getParameterValues("unit");
		String[] stockQty = request.getParameterValues("stockQty");
		String[] transferQty = request.getParameterValues("transferQty");
		String[] isgenuine = request.getParameterValues("isgenuine");
		String[] batchNumber = request.getParameterValues("batchNumber");
		String[] batchId = request.getParameterValues("batchId");
		String[] productionDate = request.getParameterValues("productionDate");
		String[] skuId = request.getParameterValues("skuId");
		Map<String, Integer> map = new HashMap<String, Integer>();  
		//判断有重复的skuId不允许保存
        for (String str : skuId){  
            Integer num = map.get(str);  
            num = null == num ? 1 : num + 1;  
            map.put(str, num);  
        }  
          
        if (skuId.length != map.size())  
        {  
        	return itemDtos;
        }  
		String[] pid = request.getParameterValues("pid");
		String[] price = request.getParameterValues("price");
		if (request.getParameter("outType").equals("2")
				|| request.getParameter("outType") == "2") {
			price = request.getParameterValues("unitprice");
		}
		for (int i = 0; i < chk.length; i++) {
			if (!chk[i].equals("0")) {
				ApplyOutOrderItemDto itemDto = new ApplyOutOrderItemDto();
				itemDto.setSid(PKUtils.getLongPrimaryKey());
				itemDto.setApplyChar(orderDto.getApplyChar());
				itemDto.setApplyId(orderDto.getSid());
				itemDto.setApplyQty(transferQty[i] == null ? Integer
						.parseInt("0") : Integer.parseInt(transferQty[i]));
				itemDto.setBarCode(barCode[i]);
				itemDto.setBatchId(batchId[i]);
				itemDto.setBatchNumber(batchNumber[i]);
				itemDto.setFormat(format[i]);
				itemDto.setIsgenuine(isgenuine[i].equals("正品") ? 1 : 2);
				itemDto.setPcode(pcode[i]);
				itemDto.setPid(pid[i] == null ? Long.valueOf(0) : Long
						.valueOf(pid[i]));
				itemDto.setPname(pname[i]);
				itemDto.setPrice(price[i] == null ? new BigDecimal(0)
						: new BigDecimal(price[i]));
				if (productionDate[i] != null && !"".equals(productionDate[i])) {
					itemDto.setProductionDate(Dateformat
							.parse(productionDate[i]));
				}
				itemDto.setQty(stockQty[i] == null ? 0 : Integer
						.parseInt(stockQty[i]));
				itemDto.setSkuId(skuId[i] == null ? 0l : Long.valueOf(skuId[i]));
				if (price[i] != null && !"".equals(price[i])) {
					itemDto.setTotalPrice(new BigDecimal(Double
							.valueOf(price[i])
							* Integer.parseInt(transferQty[i])));
				}
				itemDto.setUnit(unit[i]);
				itemDtos.add(itemDto);
			}
		}
		return itemDtos;
	}

	private List<ApplyOutOrderFileDto> getApplyOutOrderFileDto(
			ApplyOutOrderDto orderDto, HttpServletRequest request)
			throws Exception {
		List<ApplyOutOrderFileDto> fileDtos = new ArrayList<ApplyOutOrderFileDto>();
		if (request.getParameter("images") != null
				&& !"".equals(request.getParameter("images"))) {
			String[] images = request.getParameter("images").split("\\|");
			for (int i = 0; i < images.length; i++) {
				ApplyOutOrderFileDto fileDto = new ApplyOutOrderFileDto();
				fileDto.setApplyChar(orderDto.getApplyChar());
				fileDto.setApplyId(orderDto.getSid());
				fileDto.setSid(PKUtils.getLongPrimaryKey());
				fileDto.setFileName(images[i].split(";")[2]);
				fileDto.setUrlPath(images[i].split(";")[1]);
				fileDtos.add(fileDto);
			}

		}
		return fileDtos;
	}

	@RequestMapping(value = "/ApplyUpdate", method = RequestMethod.GET)
	public String ApplyUpdate(HttpServletRequest request,
			HttpServletResponse response, Model model, Long id) {
		WarehouseService warehouseService = (WarehouseService) RemoteServiceSingleton
				.getInstance().getAppService("warehouseService");
		List<Warehouse> warehouses = warehouseService
				.findWarehouseByWareseName(null);
		ApplyOutOrderService applyOutOrderService = (ApplyOutOrderService) RemoteServiceSingleton
				.getInstance().getAppService("applyOutOrderService");
		BaseDataServiceRpc baseDataServiceRpc = (BaseDataServiceRpc) RemoteServiceSingleton
				.getInstance().getAppService("baseDataServiceRpc");
		List<AgentProvince> agentProvinces = new ArrayList<AgentProvince>();
		try {
			agentProvinces = baseDataServiceRpc.getAllProvices();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// baseDataServiceRpc.get
		ApplyOutOrderDto orderDto = applyOutOrderService
				.findApplyOutOrderDto(id);
		Long warehouseId = orderDto.getWarehouseId();
		List<ApplyOutOrderItemDto> itemDtos = orderDto.getItemDtos();
		for(ApplyOutOrderItemDto applyOutOrderItem:itemDtos){
			List<Long> skuIdList = new ArrayList<Long>();
			Long skuId = applyOutOrderItem.getSkuId();
			skuIdList.add(skuId);
			String isgenuine = applyOutOrderItem.getIsgenuine() == null ?null:applyOutOrderItem.getIsgenuine().toString();
			StockDetailDTO stockDetailDTO = RemoteServiceSingleton.getInstance().getStockService().getStockDetail(skuIdList, warehouseId.intValue(), Short.valueOf(isgenuine)).get(0);
			applyOutOrderItem.setScrapQty(stockDetailDTO.getIncompleteQty()==null?0:stockDetailDTO.getIncompleteQty().intValue());
		}
		model.addAttribute("dto", orderDto);
		model.addAttribute("agentProvinces", agentProvinces);
		model.addAttribute("warehouses", warehouses);
		
		*//**
		 * 是否是领用出库用户
		 *//*
		boolean otoKey = false;
		
		User user =UserUtil.getUserFromCookie(request, response,ConfigConstant.MEMBER);
		
		MenuBean menu = UserUtil.findMenuListByUser(request, response, "KF", user);
		
		List<Module> module = menu.getModuleList();
		
		for (Module module2 : module) {
			
			if(module2.getUrl().equals("/selloutstorage/saleOutstorage")){
				
				otoKey = true;
				break;
			}
		}
		
		if(otoKey == true){
			model.addAttribute("isOutStorage", "1");
		}
		outTypeMap = Constants.OutType.getOutTypeMap();
        model.addAttribute("outTypeMap", outTypeMap);
		return "/apply/ApplyUpdate";
	}

	@RequestMapping(value = "/ApplyFind", method = RequestMethod.GET)
	public String ApplyFind(HttpServletRequest request,
			HttpServletResponse response, Model model, Long id) {
		WarehouseService warehouseService = (WarehouseService) RemoteServiceSingleton
				.getInstance().getAppService("warehouseService");
		List<Warehouse> warehouses = warehouseService
				.findWarehouseByWareseName(null);
		ApplyOutOrderService applyOutOrderService = (ApplyOutOrderService) RemoteServiceSingleton
				.getInstance().getAppService("applyOutOrderService");
		BaseDataServiceRpc baseDataServiceRpc = (BaseDataServiceRpc) RemoteServiceSingleton
				.getInstance().getAppService("baseDataServiceRpc");
		List<AgentProvince> agentProvinces = new ArrayList<AgentProvince>();
		try {
			agentProvinces = baseDataServiceRpc.getAllProvices();
//			StockDetailDTO stockDetailDTO = RemoteServiceSingleton.getInstance().getStockService().getStockDetail(skuIdList, warehouseId.intValue(), Short.valueOf(isgenuine)).get(0);
//			applyOutOrderItem.setScrapQty(stockDetailDTO.getIncompleteQty()==null?0:stockDetailDTO.getIncompleteQty().intValue());
		} catch (Exception e) {
			e.printStackTrace();
		}

		ApplyOutOrderDto orderDto = applyOutOrderService
				.findApplyOutOrderDto(id);
		model.addAttribute("dto", orderDto);
		model.addAttribute("agentProvinces", agentProvinces);
		model.addAttribute("warehouses", warehouses);
		
		*//**
		 * 是否是领用出库用户
		 *//*
		boolean otoKey = false;
		
		User user =UserUtil.getUserFromCookie(request, response,ConfigConstant.MEMBER);
		
		MenuBean menu = UserUtil.findMenuListByUser(request, response, "KF", user);
		
		List<Module> module = menu.getModuleList();
		
		for (Module module2 : module) {
			
			if(module2.getUrl().equals("/selloutstorage/saleOutstorage")){
				
				otoKey = true;
				break;
			}
		}
		
		if(otoKey == true){
			model.addAttribute("isOutStorage", "1");
		}
		outTypeMap = Constants.OutType.getOutTypeMap();
        model.addAttribute("outTypeMap", outTypeMap);
		return "/apply/ApplyFind";
	}

	@RequestMapping(value = "/doUpdate", method = RequestMethod.POST)
	@ResponseBody
	public String doUpdate(HttpServletRequest request,
			HttpServletResponse response) {
		String result = "";
		ApplyOutOrderDto orderDto = new ApplyOutOrderDto();
		ApplyOutOrderService applyOutOrderService = (ApplyOutOrderService) RemoteServiceSingleton
				.getInstance().getAppService("applyOutOrderService");
		java.text.SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String applyChar = request.getParameter("applyChar");
		String sid = request.getParameter("sid");
		String createTime = request.getParameter("createTime");
		String createBy = request.getParameter("createBy");
		String warehouseId = request.getParameter("warehouse");
		String warehouseName = request.getParameter("warehouseName");
		String outType = request.getParameter("outType");
		String scrapType = request.getParameter("scrapType");
		String otherCusType = request.getParameter("otherCusType");
		String carriage = request.getParameter("carriage");
		String useCause = request.getParameter("useCause");
		if (outType.equals("2") || outType == "2") {
			outType = otherCusType;
		}
		if(outType.equals("4") || outType == "4"){
			useCause = scrapType;
		}
		String businessType = request.getParameter("businessType");
		String remark = request.getParameter("remark");
		String section = request.getParameter("section");
		String fullName = request.getParameter("fullName");
		String address = request.getParameter("address");
		String mobile = request.getParameter("mobile");
		String provinceId = request.getParameter("provinceId");
		String provinceName = request.getParameter("provinceName");
		String cityId = request.getParameter("cityId");
		String cityName = request.getParameter("cityName");
		String areaId = request.getParameter("areaId");
		String areaName = request.getParameter("areaName");
		try {
			if (provinceId != null && !"".equals(provinceId)) {
				orderDto.setProvinceId(Long.valueOf(provinceId));
			}
			if (provinceName != null && !"".equals(provinceName)) {
				orderDto.setProvinceName(provinceName);
			}
			if (cityId != null && !"".equals(cityId)) {
				orderDto.setCityId(Long.valueOf(cityId));
			}
			if (cityName != null && !"".equals(cityName)) {
				orderDto.setCityName(cityName);
			}
			if (areaId != null && !"".equals(areaId)) {
				orderDto.setAreaId(Long.valueOf(areaId));
			}
			if (areaName != null && !"".equals(areaName)) {
				orderDto.setAreaName(areaName);
			}
			if (applyChar != null && !"".equals(applyChar)) {
				orderDto.setApplyChar(applyChar);
			}
			if (fullName != null && !"".equals(fullName)) {
				orderDto.setFullName(fullName);
			}
			if (address != null && !"".equals(address)) {
				orderDto.setAddress(address);
			}
			if (mobile != null && !"".equals(mobile)) {
				orderDto.setMobile(mobile);
			}
			if (sid != null && !"".equals(sid)) {
				orderDto.setSid(Long.parseLong(sid));
			}
			if (createTime != null && !"".equals(createTime)) {
				orderDto.setCreateTime(format.parse(createTime));
			}
			if (createBy != null && !"".equals(createBy)) {
				orderDto.setCreateBy(createBy);
			}
			if (warehouseId != null && !"".equals(warehouseId)) {
				orderDto.setWarehouseId(Long.parseLong(warehouseId));
			}
			if (warehouseName != null && !"".equals(warehouseName)) {
				orderDto.setWarehouseName(warehouseName);
			}
			if (outType != null && !"".equals(outType)) {
				orderDto.setOutType(outType);
			}
			if (businessType != null && !"".equals(businessType)) {
				orderDto.setBusinessType(businessType);
			} else {
				orderDto.setBusinessType("209");
			}
			if (remark != null && !"".equals(remark)) {
				orderDto.setRemark(remark);
			}
			if (section != null && !"".equals(section)) {
				orderDto.setSection(section);
			}
			if (carriage != null && !"".equals(carriage)) {
				orderDto.setCarriage(new BigDecimal(carriage));
			} else {
				orderDto.setCarriage(new BigDecimal(0));
			}
			if (useCause != null && !"".equals(useCause)) {
				orderDto.setUseCause(useCause);
			}
			orderDto.setStatus(1);
			List<ApplyOutOrderItemDto> itemDtos = new ArrayList<ApplyOutOrderItemDto>();
			itemDtos  = getApplyOutOrderItem(orderDto, request);
			if(itemDtos.size()==0){
				return "error";
			}
			orderDto.setItemDtos(itemDtos);
			orderDto.setFileDtos(getApplyOutOrderFileDto(orderDto, request));
			applyOutOrderService.updateApplyOutOrderDto(orderDto);
			result = "OK";
		} catch (Exception e) {
			e.printStackTrace();
			result = "NO";
		}

		return result;
	}

	@RequestMapping(value = "/doCheck", method = RequestMethod.POST)
	@ResponseBody
	public String doCheck(HttpServletRequest request,
			HttpServletResponse response) {
		String result = "";
		ApplyOutOrderDto orderDto = new ApplyOutOrderDto();
		ApplyOutOrderService applyOutOrderService = (ApplyOutOrderService) RemoteServiceSingleton
				.getInstance().getAppService("applyOutOrderService");
		java.text.SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String applyChar = request.getParameter("applyChar");
		String sid = request.getParameter("sid");
		String createTime = request.getParameter("createTime");
		String createBy = request.getParameter("createBy");
		String warehouseId = request.getParameter("warehouse");
		String warehouseName = request.getParameter("warehouseName");
		String outType = request.getParameter("outType");
		String otherCusType = request.getParameter("otherCusType");
		String scrapType = request.getParameter("scrapType");
		String carriage = request.getParameter("carriage");
		String useCause = request.getParameter("useCause");
		if (outType.equals("2") || outType == "2") {
			outType = otherCusType;
		}
		if(outType.equals("4") || outType == "4"){
			useCause = scrapType;
		}
		String businessType = request.getParameter("businessType");
		String remark = request.getParameter("remark");
		String section = request.getParameter("section");
		String fullName = request.getParameter("fullName");
		String address = request.getParameter("address");
		String mobile = request.getParameter("mobile");
		String provinceId = request.getParameter("provinceId");
		String provinceName = request.getParameter("provinceName");
		String cityId = request.getParameter("cityId");
		String cityName = request.getParameter("cityName");
		String areaId = request.getParameter("areaId");
		String areaName = request.getParameter("areaName");
		String thirdOrderId = request.getParameter("thirdOrderId");
		try {
			if (provinceId != null && !"".equals(provinceId)) {
				orderDto.setProvinceId(Long.valueOf(provinceId));
			}
			if (provinceName != null && !"".equals(provinceName)) {
				orderDto.setProvinceName(provinceName);
			}
			if (cityId != null && !"".equals(cityId)) {
				orderDto.setCityId(Long.valueOf(cityId));
			}
			if (cityName != null && !"".equals(cityName)) {
				orderDto.setCityName(cityName);
			}
			if (areaId != null && !"".equals(areaId)) {
				orderDto.setAreaId(Long.valueOf(areaId));
			}
			if (areaName != null && !"".equals(areaName)) {
				orderDto.setAreaName(areaName);
			}
			if (applyChar != null && !"".equals(applyChar)) {
				orderDto.setApplyChar(applyChar);
			}
			if (fullName != null && !"".equals(fullName)) {
				orderDto.setFullName(fullName);
			}
			if (address != null && !"".equals(address)) {
				orderDto.setAddress(address);
			}
			if (mobile != null && !"".equals(mobile)) {
				orderDto.setMobile(mobile);
			}
			if (sid != null && !"".equals(sid)) {
				orderDto.setSid(Long.parseLong(sid));
			}
			if (createTime != null && !"".equals(createTime)) {
				orderDto.setCreateTime(format.parse(createTime));
			}
			if (createBy != null && !"".equals(createBy)) {
				orderDto.setCreateBy(createBy);
			}
			if (warehouseId != null && !"".equals(warehouseId)) {
				orderDto.setWarehouseId(Long.parseLong(warehouseId));
			}
			if (warehouseName != null && !"".equals(warehouseName)) {
				orderDto.setWarehouseName(warehouseName);
			}
			if (outType != null && !"".equals(outType)) {
				orderDto.setOutType(outType);
			}
			if (businessType != null && !"".equals(businessType)) {
				orderDto.setBusinessType(businessType);
			} else {
				orderDto.setBusinessType("209");
			}
			if (remark != null && !"".equals(remark)) {
				orderDto.setRemark(remark);
			}
			if (section != null && !"".equals(section)) {
				orderDto.setSection(section);
			}
			if (carriage != null && !"".equals(carriage)) {
				orderDto.setCarriage(new BigDecimal(carriage));
			} else {
				orderDto.setCarriage(null);
			}
			if (thirdOrderId != null && !"".equals(thirdOrderId)) {
				orderDto.setThirdOrderId(thirdOrderId);
			}
			if (useCause != null && !"".equals(useCause)) {
				orderDto.setUseCause(useCause);
			}
			orderDto.setStatus(10);
			orderDto.setItemDtos(getApplyOutOrderItem(orderDto, request));
			orderDto.setFileDtos(getApplyOutOrderFileDto(orderDto, request));
			applyOutOrderService.checkApplyOutOrderDto(orderDto);
			result = "OK";
		} catch (Exception e) {
			LOGGER.error("出库审核失败",e);
			result = "NO";
		}

		return result;
	}*/
}
