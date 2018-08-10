/*package com.mall.controller.purchasevirtualorder;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.bean.authority.User;
import com.mall.dealer.product.api.DealerProdPoRderService;
import com.mall.dealer.product.dto.SkuCodeDTO;
import com.mall.mybatis.utility.PageBean;
import com.mall.retailer.order.po.Dictionarys;
import com.mall.stock.api.rpc.StockService;
import com.mall.stock.api.rpc.WarehouseService;
import com.mall.stock.po.Warehouse;
import com.mall.supplier.model.Supplier;
import com.mall.supplier.order.api.rpc.InfrastructureService;
import com.mall.supplier.order.api.rpc.PChaseVirtualOrderServiceRPC;
import com.mall.supplier.order.api.rpc.PurchaseCostService;
import com.mall.supplier.order.api.rpc.PurchaseRegService;
import com.mall.supplier.order.api.rpc.WaterService;
import com.mall.supplier.order.common.VirtualWarehouseTypeEnum;
import com.mall.supplier.order.dto.PChaseOrderDTO;
import com.mall.supplier.order.dto.PChaseVirtualOrderItemDTO;
import com.mall.supplier.order.dto.PChaseVirtualOrderVO;
import com.mall.supplier.order.dto.PurchaseCostDto;
import com.mall.supplier.order.dto.PurchaseRegDto;
import com.mall.supplier.order.dto.WareHouseDTO;
import com.mall.supplier.order.po.PurchaseType;
import com.mall.supplier.order.po.ServiceArchives;
import com.mall.supplier.order.po.Tax;
import com.mall.supplier.service.SupplierManagerService;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;
import com.mall.utils.WritePDF;

*//**
 * @ClassName PVirtualOrderController
 * @Description: 采购模块控制层
 * @author: zsYuan
 * @since: 2015-7-25 下午12:40:54
 *//*
@Controller
@RequestMapping(value = "/pchaseVirtualOrder")
public class PVirtualOrderController extends BaseController {

	private static final Log LOGGER = LogFactory.getLogger(PVirtualOrderController.class);

	*//**
	 * 跳转采购单页面-查询条件
	 * 
	 * @return String
	 *//*
	@RequestMapping(value = "/getPurchaseVirtualOrder", method = RequestMethod.GET)
	public final String getPurchaseVirtualOrder(Model model) {
		// 紧急程度
		List<Dictionarys> degreeOfEmergency = new ArrayList<Dictionarys>();
		try {

			degreeOfEmergency = RemoteServiceSingleton.getInstance().getInfrastructureService().degreeOfEmergency();
		} catch (Exception e) {
			LOGGER.error("调用仓库服务异常." + e.getMessage(), e);
			e.printStackTrace();
		}
		model.addAttribute("degreeOfEmergency", degreeOfEmergency);

		return "dealerseller/purchasevirtualorder/pchasevitrual_list";
	}

	*//**
	 * 根据条件查询采购单分页列表
	 * 
	 * @param pChaseOrderVO
	 * @param model
	 * @return String
	 *//*
	@RequestMapping(value = "/getPurchaseVirtualOrderListByCondition")
	public final String getPurchaseVirtualOrderListByCondition(PChaseVirtualOrderVO pChaseOrderVO, Model model, Integer page) {

		LOGGER.info("start to execute method <getPurchaseOrderListByCondition>!!!!");
		LOGGER.info("查询参数[制单人]:" + pChaseOrderVO.getCreateBy());
		LOGGER.info("查询参数[采购单号]:" + pChaseOrderVO.getId());
		LOGGER.info("查询参数[供应商名称]:" + pChaseOrderVO.getSupplierName());
		PageBean<PChaseVirtualOrderVO> pageBean = new PageBean<PChaseVirtualOrderVO>();
		PChaseVirtualOrderServiceRPC pChaseOrderServiceRPC = RemoteServiceSingleton.getInstance().getPChaseVirtualOrderServiceRPC();

		if (pChaseOrderVO.getCreateBy() == "") {
			pChaseOrderVO.setCreateBy(null);
		}
		if (pChaseOrderVO.getSupplierName() == "") {
			pChaseOrderVO.setSupplierName(null);
		}
		if (pChaseOrderVO.getEmergency() == "") {
			pChaseOrderVO.setEmergency(null);
		}

		if (page != null) {
			pageBean.setPage(page);
		} else {
			pageBean.setPage(1);
		}
		pageBean.setPageSize(Constants.INT10);
		pageBean.setParameter(pChaseOrderVO);

		try {
			pageBean = pChaseOrderServiceRPC.selectByDynamicListPage(pageBean);
		} catch (Exception e) {
			LOGGER.error("PChaseVirtualOrderServiceRPC[selectByDynamicListPage()]获取采购单列表失败!!!错误信息:" + e.getMessage(), e);
			e.printStackTrace();
		}
		LOGGER.info("end<> to execute method <getPurchaseOrderListByCondition>!!!!");
		model.addAttribute("pb", pageBean);

		return "/dealerseller/purchasevirtualorder/pchase_list_model";
	}

	*//**
	 * 根据采购单id查询采购单对应商品详情
	 * 
	 * @param id
	 * @param model
	 * @return String
	 *//*
	@RequestMapping(value = "/findVitrualOrderDetail", method = RequestMethod.GET)
	public final String findVitrualOrderDetail(Model model, Long id) {
		PChaseVirtualOrderServiceRPC pChaseOrderServiceRPC = (PChaseVirtualOrderServiceRPC) RemoteServiceSingleton.getInstance().getAppService("pChaseVirtualOrderServiceRPC");
		PageBean<PChaseVirtualOrderItemDTO> pageBean = new PageBean<PChaseVirtualOrderItemDTO>();
		pageBean.setPage(1);
		pageBean.setPageSize(9999);

		PChaseVirtualOrderItemDTO itemDTO = new PChaseVirtualOrderItemDTO();
		itemDTO.setPurchaseId(id);
		pageBean.setParameter(itemDTO);
		pageBean = pChaseOrderServiceRPC.selectAllItemListPage(pageBean);

		model.addAttribute("pb", pageBean);

		return "dealerseller/purchasevirtualorder/showOrderItem";
	}

	*//**
	 * 根据采购单id查询采购单的采购费用列表
	 * 
	 * @param id
	 * @param model
	 * @return String
	 *//*
	@RequestMapping(value = "/showVirtualFeeList", method = RequestMethod.GET)
	public final String showFeeList(Model model, Long id) {
		PurchaseCostService purchaseCostService = (PurchaseCostService) RemoteServiceSingleton.getInstance().getAppService("purchaseCostService");
		PageBean<PurchaseCostDto> pageBean = new PageBean<PurchaseCostDto>();
		pageBean.setPage(1);
		pageBean.setPageSize(10);

		PurchaseCostDto costDto = new PurchaseCostDto();
		costDto.setPurchaseCode(id);
		pageBean.setParameter(costDto);
		pageBean = purchaseCostService.findPurchaseCost(pageBean);

		model.addAttribute("pb", pageBean);

		return "dealerseller/purchasevirtualorder/showFeeList";
	}

	*//**
	 * 根据采购单id查询采购单的国内运费用列表
	 * 
	 * @param id
	 * @param model
	 * @return String
	 *//*
	@RequestMapping(value = "/showInLandFee", method = RequestMethod.GET)
	public final String showInLandFee(Model model, Long id) {
		PurchaseRegService purchaseRegService = (PurchaseRegService) RemoteServiceSingleton.getInstance().getAppService("purchaseRegService");
		PageBean<PurchaseRegDto> pageBean = new PageBean<PurchaseRegDto>();
		pageBean.setPage(1);
		pageBean.setPageSize(10);

		PurchaseRegDto regDto = new PurchaseRegDto();
		regDto.setBusinessNumber(id);
		pageBean.setParameter(regDto);
		pageBean = purchaseRegService.findPurchaseRegDto(pageBean);

		model.addAttribute("pb", pageBean);

		return "dealerseller/purchasevirtualorder/showFeeList";
	}

	*//**
	 * 查看/编辑采购单
	 * 
	 * @param id
	 * @param model
	 * @param supplierId
	 * @return
	 *//*
	@RequestMapping(value = "/showOrder")
	public String showOrder(Long id, Model model, Integer type) {
		LOGGER.info("start to execute method <showOrder()查看编辑采购单>!!!!");

		String name = this.getCurrentUser().getUsername();
		PChaseOrderDTO orderDetailById = new PChaseOrderDTO();
		// 采购公司
		List<Dictionarys> purchaseEntity = new ArrayList<Dictionarys>();
		// 采购商品类型
		List<Dictionarys> productType = new ArrayList<Dictionarys>();

		Long ChaseOrderId = com.mall.retailer.order.common.PKUtils.getLongPrimaryKey();
		java.text.SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String sysdate = "";

		try {

			orderDetailById.setId(0l);
			orderDetailById.setCreateTime(new Date());
			sysdate = format.format(new Date());

			purchaseEntity = RemoteServiceSingleton.getInstance().getInfrastructureService().findPurchasingUnitByAll();
			productType = RemoteServiceSingleton.getInstance().getInfrastructureService().infrastructures("infrastructure_goodsCategory");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

		PChaseVirtualOrderServiceRPC pChaseOrderServiceRPC = RemoteServiceSingleton.getInstance().getPChaseVirtualOrderServiceRPC();
		PChaseVirtualOrderVO pChaseOrderVO = pChaseOrderServiceRPC.findOrderDetailById(id);

		User currentUser = getCurrentUser();
		model.addAttribute("sysdate", sysdate);
		model.addAttribute("ChaseOrderId", ChaseOrderId);
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("purchaseEntity", purchaseEntity);
		model.addAttribute("name", name);
		model.addAttribute("purchaseTypeList", VirtualWarehouseTypeEnum.values());
		model.addAttribute("purchaseEntity", purchaseEntity);
		model.addAttribute("pChaseOrderVO", pChaseOrderVO);// 采购主表/对应商品/对应仓库详情信息
		model.addAttribute("type", type);
		model.addAttribute("productType", productType);
		LOGGER.info("end<> to execute method <findSupplierList()根据供应商名称查询供应商列表>");

		if (type == 1) {
			return "/dealerseller/purchasevirtualorder/showOrderDetail";// 返回修改页面
		} else {
			return "/dealerseller/purchasevirtualorder/checkOrderDetail";// 返回查看页面
		}
	}

	*//**
	 * @Description： PDF展示
	 * @author: srLong
	 * @since: 2015-7-25 下午1:59:41
	 *//*
	@RequestMapping(value = "/viewPDF")
	public String viewPDF(Model model, HttpServletRequest request) {
		String fileName = request.getParameter("fileName");
		model.addAttribute("name", fileName);

		return "/pdf/ViewPdf";
	}

	*//**
	 * 查看-导出采购单的PDF
	 * 
	 * @param pChaseOrderVO
	 * @param model
	 * @param supplierId
	 * @return
	 *//*
	@RequestMapping(value = "/exportPdf")
	public String exportPdf(Model model, Long orderId, HttpServletRequest request) {
		LOGGER.info("start to execute method <exportPdf()导出采购单的pdf文件>!!!!");
		String result = "";

		try {
			PChaseVirtualOrderServiceRPC pChaseOrderServiceRPC = RemoteServiceSingleton.getInstance().getPChaseVirtualOrderServiceRPC();
			if (orderId != null) {
				PChaseVirtualOrderVO pChaseOrderVO = pChaseOrderServiceRPC.findOrderDetailById(orderId);
				WritePDF pdf = new WritePDF();
				result = pdf.getInstance(WritePDF.TYPE05, pChaseOrderVO, request);
			}
		} catch (NumberFormatException e) {
			LOGGER.error("PChaseVirtualOrderServiceRPC[finishOrder()]批量修改采购单状态为完成失败!!!错误信息:" + e.getMessage(), e);
			e.printStackTrace();
			result = "批量修改订单完成状态失败";
		}

		return "redirect:/pchaseVirtualOrder/viewPDF?fileName=" + result;
	}

	*//**
	 * 修改采购单
	 * 
	 * @param pChaseOrderVO
	 * @param model
	 * @param supplierId
	 * @return
	 *//*
	@RequestMapping(value = "/updateOrder")
	@ResponseBody
	public String updateOrder(HttpServletRequest request, Model model) {
		LOGGER.info("start to execute method <updateOrder()修改采购单>!!!!");
		java.text.SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM-dd");
		String result = "";
		PChaseVirtualOrderServiceRPC pChaseOrderServiceRPC = RemoteServiceSingleton.getInstance().getPChaseVirtualOrderServiceRPC();
		PChaseVirtualOrderVO pChaseOrderVO = new PChaseVirtualOrderVO();
		String type = request.getParameter("zhao");

		String supplierId = request.getParameter("supplierId");
		String supplierName = request.getParameter("supplierName");
		String shipper = request.getParameter("shipper");
		String shipperType = request.getParameter("shipperType");
		String serviceName = request.getParameter("serviceName");
		String serviceCode = request.getParameter("serviceCode");
		String servicePeople = request.getParameter("servicePeople");
		String serviceTel = request.getParameter("serviceTel");
		String receiveAddress = request.getParameter("receiveAddress");
		String contractNumber = request.getParameter("contractNumber");
		String paymentFunction = request.getParameter("paymentFunction");
		String supplierPhone = request.getParameter("supplierPhone");
		String sendDate = request.getParameter("sendate");
		String emergency = request.getParameter("emergency");
		String createBy = request.getParameter("createBy");
		String entityName = request.getParameter("entityName");
		String purchaseEntity = request.getParameter("purchaseEntity");
		String purchaseTypeName = request.getParameter("purchaseTypeName");
		String purchaseType = request.getParameter("purchaseType");
		String supplierBy = request.getParameter("supplierBy");
		String id = request.getParameter("id");
		String remark = request.getParameter("remark");
		String productType = request.getParameter("productType");
		String purchaseCode = request.getParameter("purchaseCode");
		String exchangeredio = request.getParameter("exchangeredio");

		try {
			if (id != null) {
				pChaseOrderVO.setId(Long.parseLong(id));
			}
			if (supplierId != null) {
				pChaseOrderVO.setSupplierId(new Long(supplierId));
			}
			if (supplierName != null) {
				pChaseOrderVO.setSupplierName(supplierName);
			}
			if (type.equals("check")) {
				pChaseOrderVO.setOrderStatus((short) 10);
			}
			if (shipper != null) {
				pChaseOrderVO.setShipper(new Short(shipper));// 承运方
			}
			if (shipperType != null) {
				pChaseOrderVO.setShipperType(shipperType);
			}
			if (serviceName != null) {
				pChaseOrderVO.setServiceName(serviceName);
			}
			if (serviceCode != null) {
				pChaseOrderVO.setServiceCode(new Long(serviceCode));
			}
			if (servicePeople != null) {
				pChaseOrderVO.setServicePeople(servicePeople);
			}
			if (serviceTel != null) {
				pChaseOrderVO.setServiceTel(serviceTel);
			}
			if (receiveAddress != null) {
				pChaseOrderVO.setReceiveAddress(receiveAddress);
			}
			if (contractNumber != null) {
				pChaseOrderVO.setContractNumber(contractNumber);
			}
			if (remark != null) {
				pChaseOrderVO.setRemark(remark);
			}
			if (paymentFunction != null) {
				pChaseOrderVO.setPaymentFunction(paymentFunction);
			}
			if (supplierPhone != null) {
				pChaseOrderVO.setSupplierPhone(supplierPhone);
			}
			if (sendDate != null && !"".equals(sendDate)) {
				pChaseOrderVO.setSendDate(Dateformat.parse(sendDate));
			}
			if (exchangeredio != null && !"".equals(exchangeredio)) {
				pChaseOrderVO.setRadioType(Integer.parseInt(exchangeredio));
			}

			pChaseOrderVO.setEmergency(emergency);
			pChaseOrderVO.setCreateBy(createBy);
			pChaseOrderVO.setCreateTime(new Date());
			pChaseOrderVO.setProductType(productType);
			pChaseOrderVO.setBusinessNumberChar(purchaseCode);

			if (purchaseEntity != null && !purchaseEntity.equals("")) {
				pChaseOrderVO.setPurchaseEntity(new Integer(purchaseEntity));
				pChaseOrderVO.setEntityName(entityName);
			}

			if (purchaseType != null && !purchaseType.equals("")) {
				pChaseOrderVO.setPurchaseType(new Integer(purchaseType));
				pChaseOrderVO.setPurchaseTypeName(purchaseTypeName);
				pChaseOrderVO.setCurrencyType("RMB");
			}
			pChaseOrderVO.setSupplierBy(supplierBy);

			List<PChaseVirtualOrderItemDTO> orderItems = new ArrayList<PChaseVirtualOrderItemDTO>();
			String[] skuId = request.getParameterValues("skuId");
			String[] barCode = request.getParameterValues("barCode");
			String[] pName = request.getParameterValues("pName");
			String[] format = request.getParameterValues("format");
			String[] unit = request.getParameterValues("unit");
			String[] qty = request.getParameterValues("qty");
			String[] businessProdid = request.getParameterValues("businessProdid");
			for (int i = 0; i < skuId.length; i++) {
				
				PChaseVirtualOrderItemDTO pChaseOrderItemDTO = new PChaseVirtualOrderItemDTO();
				pChaseOrderItemDTO.setSkuId(new Long(skuId[i]));
				pChaseOrderItemDTO.setBarCode(barCode[i]);
				pChaseOrderItemDTO.setPname(pName[i]);
				pChaseOrderItemDTO.setFormat(format[i]);
				pChaseOrderItemDTO.setUnit(unit[i]);
				pChaseOrderItemDTO.setQty(Integer.parseInt(qty[i]));
				pChaseOrderItemDTO.setBusinessProdid(businessProdid[i]);

				orderItems.add(pChaseOrderItemDTO);
			}


			pChaseOrderVO.setpCOItemList(orderItems);
			pChaseOrderServiceRPC.updatePChaseOrderbyDTO(pChaseOrderVO);

			if (type.equals("check")) {
				result = "审核采购单成功!";
			} else if (type.equals("update")) {
				result = "修改采购单成功!";
			}
		} catch (Exception e) {
			LOGGER.error("PChaseVirtualOrderServiceRPC[updateOrder()]修改采购单失败!!!错误信息:" + e.getMessage(), e);
			e.printStackTrace();
			result = "修改采购单失败!";
		}

		return result;
	}

	*//**
	 * 跳转到添加采购单页面
	 * 
	 * @param model
	 *            Model
	 * @param shipOrderId
	 *            采购单ID
	 * @param
	 * @return
	 *//*
	@RequestMapping(value = "/addpurchaseorder")
	public String addPurchaseOrder(Model model) {
		String name = this.getCurrentUser().getUsername();

		PChaseOrderDTO orderDetailById = new PChaseOrderDTO();
		// 查询紧急程度
		List<Dictionarys> degreeOfEmergency = new ArrayList<Dictionarys>();
		// 查询承运方式
		List<Dictionarys> transportWay = new ArrayList<Dictionarys>();
		// 查询承运方
		List<Dictionarys> shipper = new ArrayList<Dictionarys>();
		// 采购订单的类型
		List<PurchaseType> purchaseTypeList = new ArrayList<PurchaseType>();
		// 采购公司
		List<Dictionarys> purchaseEntity = new ArrayList<Dictionarys>();
		// 采购商品类型
		List<Dictionarys> productType = new ArrayList<Dictionarys>();

		Long ChaseOrderId = com.mall.retailer.order.common.PKUtils.getLongPrimaryKey();
		java.text.SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String sysdate = "";
		try {
			orderDetailById.setId(0l);
			orderDetailById.setCreateTime(new Date());
			sysdate = format.format(new Date());

			degreeOfEmergency = RemoteServiceSingleton.getInstance().getInfrastructureService().degreeOfEmergency();
			transportWay = RemoteServiceSingleton.getInstance().getInfrastructureService().transportWay();
			shipper = RemoteServiceSingleton.getInstance().getInfrastructureService().shipper();
			purchaseTypeList = RemoteServiceSingleton.getInstance().getInfrastructureService().findPurchaseTypeByAll();
			purchaseEntity = RemoteServiceSingleton.getInstance().getInfrastructureService().findPurchasingUnitByAll();
			productType = RemoteServiceSingleton.getInstance().getInfrastructureService().infrastructures("infrastructure_goodsCategory");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		///FIXME
		User currentUser = getCurrentUser();
		model.addAttribute("sysdate", sysdate);
		model.addAttribute("ChaseOrderId", ChaseOrderId);
		model.addAttribute("degreeOfEmergency", degreeOfEmergency);
		model.addAttribute("transportWay", transportWay);
		model.addAttribute("shipper", shipper);
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("purchaseEntity", purchaseEntity);
		model.addAttribute("name", name);
		model.addAttribute("purchaseTypeList", purchaseTypeList);
		model.addAttribute("purchaseEntity", purchaseEntity);
		model.addAttribute("productType", productType);
		model.addAttribute("virtualOrderType", VirtualWarehouseTypeEnum.values());
		
		return "/dealerseller/purchasevirtualorder/addPchaseOrder";
	}

	*//**
	 * 查询供应商列表
	 * 
	 * @param supplierName
	 * @param model
	 * @return String
	 *//*
	@RequestMapping(value = "/findSupplierList")
	public String findSupplierList(String supplierName, Integer supplyType, Model model) {
		LOGGER.info("start to execute method <findSupplierList()根据供应商名称查询供应商列表>!!!!");
		List<Supplier> suppliers = new ArrayList<Supplier>();
		try {
			SupplierManagerService supplierManagerService = RemoteServiceSingleton.getInstance().getSupplierManagerService();
			if (supplierName == null) {
				suppliers = supplierManagerService.findVirtualSupplier(null, supplyType);
			} else {
				suppliers = supplierManagerService.findVirtualSupplier(supplierName, supplyType);
			}
		} catch (Exception e) {
			LOGGER.error("SupplierManagerService[findAllSupplier() or findAllSupplier(supplierName)] is failed!!!!" + e.getMessage(), e);
		}

		model.addAttribute("supplierList", suppliers);

		return "/dealerseller/purchasevirtualorder/supplier";
	}

	*//**
	 * 采购明细中查询商品列表
	 * 
	 * @param skuName
	 * @param model
	 * @param supplierId
	 * @return
	 *//*
	@RequestMapping(value = "/findskuList")
	public String findskuList(String skuName, Model model, Long supplierId) {
		LOGGER.info("start to execute method <findSupplierList()根据供应商名称查询供应商列表>!!!!");
		List<SkuCodeDTO> skuList = new ArrayList<SkuCodeDTO>();
		DealerProdPoRderService dealerProdPoRderService = (DealerProdPoRderService) RemoteServiceSingleton.getInstance().getAppService("dealerProdPoRderService");

		try {
			PageBean<SkuCodeDTO> pageBean = new PageBean<SkuCodeDTO>();
			pageBean.setPage(1);
			pageBean.setPageSize(10000);
			SkuCodeDTO skuCodeDTO = new SkuCodeDTO();
			if (skuName != null && !"".equals(skuName)) {
				// skuCodeDTO.setSkuNameCn(skuName);
				skuCodeDTO.setProdName(skuName);
			}
			skuCodeDTO.setSupplierId(supplierId);
			pageBean.setParameter(skuCodeDTO);
			pageBean = dealerProdPoRderService.findSkuDtosBySupplierId(pageBean);
			skuList = pageBean.getResult();
		} catch (Exception e) {
			LOGGER.error("dealerProductService[findSkuDtosBySupplierId(supplierId)] is failed!!!!" + e.getMessage(), e);
			e.printStackTrace();
		}

		model.addAttribute("skuList", skuList);

		return "/dealerseller/purchasevirtualorder/skuList";
	}

	*//**
	 * 查询仓库列表
	 * 
	 * @param warehouseName
	 * @param model
	 * @return
	 *//*
	@RequestMapping(value = "/findwareHouseList")
	public String findwareHouseList(String warehouseName, Model model) {
		LOGGER.info("start to execute method <findwareHouseList()获取所有仓库信息>!!!!");
		List<Warehouse> warehouseList = new ArrayList<Warehouse>();
		try {
			WarehouseService warehouseService = RemoteServiceSingleton.getInstance().getWarehouseService();
			if (warehouseName == null) {
				warehouseName = "";
			}
			warehouseList = warehouseService.findWarehouseByWareseName(warehouseName);
		} catch (Exception e) {
			LOGGER.error("WarehouseService [findWarehouseByWareseName(warehouseName)] is failed!!!!" + e.getMessage(), e);
			e.printStackTrace();
		}

		model.addAttribute("warehouseList", warehouseList);

		return "/dealerseller/purchasevirtualorder/wareHouse";
	}

	*//**
	 * 本公司-获取服务商.
	 * 
	 * @param serviceName
	 * @param model
	 * @return String
	 *//*
	@RequestMapping(value = "/findServiceList")
	public String findServiceList(String serviceName, Model model) {
		LOGGER.info("start to execute method <findSupplierList()根据供应商名称查询供应商列表>!!!!");

		// List<ServiceArchives> serviceList = new ArrayList<ServiceArchives>();
		InfrastructureService infrastructureService = RemoteServiceSingleton.getInstance().getInfrastructureService();
		List<ServiceArchives> serviceArchives = infrastructureService.findServiceArchivesByName(serviceName == null ? "" : serviceName);

		model.addAttribute("serviceList", serviceArchives);

		return "/dealerseller/purchasevirtualorder/service";
	}

	*//**
	 * 查询税率列表
	 * 
	 * @param
	 * @param
	 * @return
	 *//*
	@RequestMapping(value = "/findTaxList")
	@ResponseBody
	public String findTaxList() {
		LOGGER.info("start to execute method <findTaxList()查询税率列表>!!!!");
		// 查询税率
		List<Tax> taxs = new ArrayList<Tax>();
		Object json = "[]";
		try {
			taxs = RemoteServiceSingleton.getInstance().getInfrastructureService().findTax();
			json = JSONArray.fromObject(taxs);
		} catch (Exception e) {
			LOGGER.error("InfrastructureService[findTax()] is failed!!!!" + e.getMessage(), e);
			e.printStackTrace();
		}

		return json.toString();
	}

	*//**
	 * 获取采购单规则生成编码
	 * 
	 * @param pChaseOrderVO
	 * @param model
	 * @param supplierId
	 * @return
	 *//*
	@RequestMapping(value = "/getPurchaseId")
	@ResponseBody
	public String getPurchaseId(Model model, String skuType, String purchaseEntity) {
		LOGGER.info("start to execute method <getPurchaseId()获取采购单规则生成编码>!!!!");
		String purchaseCode = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

		try {
			WaterService waterService = (WaterService) RemoteServiceSingleton.getInstance().getAppService("waterService");
			String waterValue = waterService.getWater("PO");
			String Date = dateFormat.format(new Date());
			purchaseCode = "PO" + "0" + purchaseEntity + skuType + Date + waterValue;

		} catch (NumberFormatException e) {
			LOGGER.error("PChaseVirtualOrderServiceRPC[finishOrder()]生成采购单编码失败!!!错误信息:" + e.getMessage(), e);
			purchaseCode = "生成采购单编码失败";
		}

		return purchaseCode;
	}

	*//**
	 * 添加采购单.
	 * 
	 * @param model
	 * @param pChaseOrderVO
	 * @return String
	 *//*
	@RequestMapping(value = "goAddpurchaseorder")
	@ResponseBody
	public String goAddpurchaseorder(HttpServletRequest request, Model model) {
		LOGGER.info("start to execute method <goAddpurchaseorder>!!!!");
		java.text.SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM-dd");
		String result = "";
		PChaseVirtualOrderServiceRPC pChaseVirtualOrderServiceRPC = RemoteServiceSingleton.getInstance().getPChaseVirtualOrderServiceRPC();
		PChaseVirtualOrderVO pChaseOrderVO = new PChaseVirtualOrderVO();

		String supplierId = request.getParameter("supplierId");
		String supplierName = request.getParameter("supplierName");
		String shipper = request.getParameter("shipper");
		String shipperType = request.getParameter("shipperType");
		String serviceName = request.getParameter("serviceName");
		String serviceCode = request.getParameter("serviceCode");
		String servicePeople = request.getParameter("servicePeople");
		String serviceTel = request.getParameter("serviceTel");
		String receiveAddress = request.getParameter("receiveAddress");
		String sendAdress = request.getParameter("sendAdress");
		String contractNumber = request.getParameter("contractNumber");
		String paymentFunction = request.getParameter("paymentFunction");
		String supplierPhone = request.getParameter("supplierPhone");
		String sendDate = request.getParameter("sendate");
		String emergency = request.getParameter("emergency");
		String createBy = request.getParameter("createBy");
		String entityName = request.getParameter("entityName");
		String purchaseEntity = request.getParameter("purchaseEntity");
		String purchaseTypeName = request.getParameter("purchaseTypeName");
		String purchaseType = request.getParameter("purchaseType");
		String supplierBy = request.getParameter("supplierBy");
		String id = request.getParameter("id");
		String remark = request.getParameter("remark");
		String productType = request.getParameter("productType");
		String purchaseCode = request.getParameter("purchaseCode");
		String exchangeredio = request.getParameter("exchangeredio");
		
		if (null == purchaseCode || purchaseCode.isEmpty()) {
			purchaseCode = getPurchaseId(null, "VM", "00");
		}

		try {
			if (id != null) {
				pChaseOrderVO.setId(Long.parseLong(id));
			}
			if (supplierId != null) {
				pChaseOrderVO.setSupplierId(new Long(supplierId));
			}
			if (supplierName != null) {
				pChaseOrderVO.setSupplierName(supplierName);
			}
			pChaseOrderVO.setStatus(new Short("10"));
			if (shipper != null) {
				pChaseOrderVO.setShipper(new Short(shipper));// 承运方
			}
			if (shipperType != null) {
				pChaseOrderVO.setShipperType(shipperType);
			}
			if (serviceName != null) {
				pChaseOrderVO.setServiceName(serviceName);
			}
			if (serviceCode != null) {
				pChaseOrderVO.setServiceCode(new Long(serviceCode));
			}
			if (servicePeople != null) {
				pChaseOrderVO.setServicePeople(servicePeople);
			}
			if (serviceTel != null) {
				pChaseOrderVO.setServiceTel(serviceTel);
			}
			if (receiveAddress != null) {
				pChaseOrderVO.setReceiveAddress(receiveAddress);
			}
			if (sendAdress != null) {
				pChaseOrderVO.setSendAdress(sendAdress);
			}
			if (contractNumber != null) {
				pChaseOrderVO.setContractNumber(contractNumber);
			}
			if (paymentFunction != null) {
				pChaseOrderVO.setPaymentFunction(paymentFunction);
			}
			if (supplierPhone != null) {
				pChaseOrderVO.setSupplierPhone(supplierPhone);
			}
			if (sendDate != null && !"".equals(sendDate)) {
				pChaseOrderVO.setSendDate(Dateformat.parse(sendDate));
			}
			if (remark != null) {
				pChaseOrderVO.setRemark(remark);
			}
			if (exchangeredio != null && !"".equals(exchangeredio)) {
				pChaseOrderVO.setRadioType(Integer.parseInt(exchangeredio));
			}

			pChaseOrderVO.setEmergency(emergency);
			pChaseOrderVO.setCreateBy(createBy);
			pChaseOrderVO.setCreateTime(new Date());
			pChaseOrderVO.setProductType(productType);
			pChaseOrderVO.setBusinessNumberChar(purchaseCode);

			if (purchaseEntity != null && !purchaseEntity.equals("")) {
				pChaseOrderVO.setPurchaseEntity(new Integer(purchaseEntity));
				pChaseOrderVO.setEntityName(entityName);
			}
			if (purchaseType != null && !purchaseType.equals("")) {
				pChaseOrderVO.setPurchaseType(new Integer(purchaseType));
				pChaseOrderVO.setPurchaseTypeName(purchaseTypeName);
				pChaseOrderVO.setCurrencyType("RMB");
			}

			pChaseOrderVO.setSupplierBy(supplierBy);

			List<PChaseVirtualOrderItemDTO> orderItems = new ArrayList<PChaseVirtualOrderItemDTO>();
			String[] skuId = request.getParameterValues("skuId");
			String[] businessProdid = request.getParameterValues("businessProdid");
			String[] barCode = request.getParameterValues("barCode");
			String[] pName = request.getParameterValues("pName");
			String[] format = request.getParameterValues("format");
			String[] unit = request.getParameterValues("unit");
			String[] qty = request.getParameterValues("qty");

			for (int i = 0; i < skuId.length; i++) {
				PChaseVirtualOrderItemDTO pChaseOrderItemDTO = new PChaseVirtualOrderItemDTO();

				pChaseOrderItemDTO.setSkuId(new Long(skuId[i]));
				pChaseOrderItemDTO.setBarCode(barCode[i]);
				pChaseOrderItemDTO.setPname(pName[i]);
				pChaseOrderItemDTO.setFormat(format[i]);
				pChaseOrderItemDTO.setUnit(unit[i]);
				pChaseOrderItemDTO.setQty(Integer.parseInt(qty[i]));
				pChaseOrderItemDTO.setBusinessProdid(businessProdid[i]);

				orderItems.add(pChaseOrderItemDTO);
			}

			List<WareHouseDTO> wareList = new ArrayList<WareHouseDTO>();
			List<Warehouse> warehouseList = new ArrayList<Warehouse>();
			try {
				WarehouseService warehouseService = RemoteServiceSingleton.getInstance().getWarehouseService();
				warehouseList = warehouseService.findWarehouseByWareseName("NEW米兰");
			} catch (Exception e) {
				LOGGER.error("WarehouseService [findWarehouseByWareseName(warehouseName)] is failed!!!!" + e.getMessage(), e);
				e.printStackTrace();
			}
			
			if (null != warehouseList && !warehouseList.isEmpty()) {
				Warehouse warehouseBo = warehouseList.get(0);
				
				WareHouseDTO warehouse = new WareHouseDTO();
				if (null != warehouseBo.getWarehouseName() && !"".equals(warehouseBo.getWarehouseName())) {
					warehouse.setStoreName(warehouseBo.getWarehouseName());
				}
				if (null != warehouseBo.getAddress() && !"".equals(warehouseBo.getAddress())) {
					warehouse.setAddress(warehouseBo.getAddress());
				}
				if (null != warehouseBo.getContact() && !"".equals(warehouseBo.getContact())) {
					warehouse.setContact(warehouseBo.getContact());
				}
				if (null != warehouseBo.getTelephone()) {
					warehouse.setTelephone(String.valueOf(warehouseBo.getTelephone()));
				}
				if (null != warehouseBo.getWarehouseCode()) {
					warehouse.setStoreCode(Long.valueOf(warehouseBo.getWarehouseCode()));
				}
				wareList.add(warehouse);
			}
			
			pChaseOrderVO.setpCOItemList(orderItems);
			pChaseOrderVO.setWareHouseDTOs(wareList);
			
			try{
				StockService stockService = RemoteServiceSingleton.getInstance().getStockService();
				Map<Long, Integer> dtoMap = new HashMap<Long, Integer>();
				for(PChaseVirtualOrderItemDTO dto : orderItems) {
					dtoMap.put(dto.getSkuId(), dto.getQty());
					// 单个调用增加库存的接口
					// stockService.updateSpecial(dto.getSkuId(), dto.getQty(), createBy);
					 System.out.println("===============skuId=" + dto.getSkuId()+ ";qty=" + dto.getQty() + ";op=" + createBy);
				}
				// 批量调用增加库存的接口
				stockService.updateSpecial(dtoMap, createBy);
			}
			catch(Exception e) {
				LOGGER.error("PChaseVirtualOrderServiceRPC[insertSelective()]添加采购单失败!!!错误信息:" + e.getMessage(), e);
				throw e;
			}
			
			pChaseVirtualOrderServiceRPC.insertSelective(pChaseOrderVO);
			result = "保存采购单成功!";
			
		} catch (Exception e) {
			LOGGER.error("PChaseVirtualOrderServiceRPC[insertSelective()]添加采购单失败!!!错误信息:" + e.getMessage(), e);
			e.printStackTrace();
			result = "保存采购单失败!";
		}

		return result;
	}

	*//**
	 * 批量修改采购单为完成状态
	 * 
	 * @param pChaseOrderVO
	 * @param model
	 * @param supplierId
	 * @return
	 *//*
	@RequestMapping(value = "/finishOrder")
	@ResponseBody
	public String finishOrder(Model model, Long[] orderIds) {
		LOGGER.info("start to execute method <finishOrder()批量修改采购单为完成状态>!!!!");
		String result = "";

		try {
			PChaseVirtualOrderServiceRPC pChaseOrderServiceRPC = RemoteServiceSingleton.getInstance().getPChaseVirtualOrderServiceRPC();
			if (orderIds != null && orderIds.length > 0) {
				pChaseOrderServiceRPC.updateStatusBatch(orderIds, new Short("20"));
				result = "批量修改订单完成状态成功";
			}
		} catch (NumberFormatException e) {
			LOGGER.error("PChaseVirtualOrderServiceRPC[finishOrder()]批量修改采购单状态为完成失败!!!错误信息:" + e.getMessage(), e);
			result = "批量修改订单完成状态失败";
		}

		return result;
	}

	*//****************************************************** 已过时 ***************************************************************//*
	*//**
	 * 审核采购单
	 * 
	 * @param id
	 * @param model
	 * @param supplierId
	 * @return
	 *//*
	@RequestMapping(value = "/confirmOrder")
	@ResponseBody
	public String confirmOrder(Long id, Model model) {
		LOGGER.info("start to execute method <confirmOrder()审核采购单>!!!!");
		String message = "";

		try {
			PChaseVirtualOrderServiceRPC pChaseOrderServiceRPC = RemoteServiceSingleton.getInstance().getPChaseVirtualOrderServiceRPC();
			pChaseOrderServiceRPC.updateStatusById(id, new Short("10"));
			message = "审核成功";
		} catch (NumberFormatException e) {
			e.printStackTrace();
			message = "审核失败";
		}

		return message;

	}

	*//**
	 * @param model
	 *            Model
	 * @param shipOrderId
	 *            采购单ID
	 * @param
	 * @return
	 *//*
	// 单价含税，//汇率
	@RequestMapping(value = "/savepChaseorder", method = RequestMethod.POST)
	public String savepChaseorder(PChaseVirtualOrderVO pChaseOrderVO, Long[] pid, String[] barCode, String[] pName, String[] format, String[] unit, Long[] qty, BigDecimal[] dutyRate, BigDecimal[] skuPrice,
			BigDecimal[] subtotalPriceRmb, BigDecimal[] allocationTotalPriceRmb, String[] currencyType, BigDecimal[] skuPriceRmb, String[] wareHouseName, String[] address, String[] contact,
			String[] telephone) {

		return "/dealerseller/purchasevirtualorder/addPchaseOrder";
	}

}
*/