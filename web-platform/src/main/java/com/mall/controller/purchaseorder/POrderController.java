/*package com.mall.controller.purchaseorder;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.mall.platform.model.PlatformUser;
import com.mall.retailer.order.po.Dictionarys;
import com.mall.stock.api.rpc.WarehouseService;
import com.mall.stock.po.Warehouse;
import com.mall.supplier.model.Supplier;
import com.mall.supplier.order.api.rpc.InfrastructureService;
import com.mall.supplier.order.api.rpc.PChaseOrderServiceRPC;
import com.mall.supplier.order.api.rpc.PurchaseCostService;
import com.mall.supplier.order.api.rpc.PurchaseRegService;
import com.mall.supplier.order.api.rpc.WaterService;
import com.mall.supplier.order.dto.PChaseOrderDTO;
import com.mall.supplier.order.dto.PChaseOrderItemDTO;
import com.mall.supplier.order.dto.PChaseOrderVO;
import com.mall.supplier.order.dto.PurchaseCostDto;
import com.mall.supplier.order.dto.PurchaseRegDto;
import com.mall.supplier.order.dto.WareHouseDTO;
import com.mall.supplier.order.po.Currency;
import com.mall.supplier.order.po.PurchaseType;
import com.mall.supplier.order.po.ServiceArchives;
import com.mall.supplier.order.po.Tax;
import com.mall.supplier.service.SupplierManagerService;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;
import com.mall.utils.WritePDF;

*//**
 * @ClassName POrderController
 * @Description: 采购模块控制层
 * @author: zsYuan
 * @since: 2015-7-25 下午12:40:54
 *//*
@Controller
@RequestMapping(value = "/pchaseOrder")
public class POrderController extends BaseController {

	private static final Log LOGGER = LogFactory.getLogger(POrderController.class);

	*//**
	 * 跳转采购单页面-查询条件
	 * 
	 * @return String
	 *//*
	@RequestMapping(value = "/getPurchaseOrder", method = RequestMethod.GET)
	public final String getPurchaseOrder(Model model) {
		// 紧急程度
		List<Dictionarys> degreeOfEmergency = new ArrayList<Dictionarys>();
		try {

			degreeOfEmergency = RemoteServiceSingleton.getInstance().getInfrastructureService().degreeOfEmergency();
		} catch (Exception e) {
			LOGGER.error("调用仓库服务异常." + e.getMessage(), e);
			e.printStackTrace();
		}
		model.addAttribute("degreeOfEmergency", degreeOfEmergency);

		return "dealerseller/purchaseorder/pchase_list";
	}

	*//**
	 * 根据条件查询采购单分页列表
	 * 
	 * @param pChaseOrderVO
	 * @param model
	 * @return String
	 *//*
	@RequestMapping(value = "/getPurchaseOrderListByCondition")
	public final String getPurchaseOrderListByCondition(PChaseOrderVO pChaseOrderVO, Model model, Integer page) {

		LOGGER.info("start to execute method <getPurchaseOrderListByCondition>!!!!");
		LOGGER.info("查询参数[制单人]:" + pChaseOrderVO.getCreateBy());
		LOGGER.info("查询参数[采购单号]:" + pChaseOrderVO.getId());
		LOGGER.info("查询参数[采购单状态]:" + pChaseOrderVO.getOrderStatus());
		LOGGER.info("查询参数[紧急程度]:" + pChaseOrderVO.getEmergency());
		LOGGER.info("查询参数[供应商名称]:" + pChaseOrderVO.getSupplierName());
		PageBean<PChaseOrderVO> pageBean = new PageBean<PChaseOrderVO>();
		PChaseOrderServiceRPC pChaseOrderServiceRPC = RemoteServiceSingleton.getInstance().getPChaseOrderServiceRPC();
		List<Dictionarys> degreeOfEmergency = new ArrayList<Dictionarys>();

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
			degreeOfEmergency = RemoteServiceSingleton.getInstance().getInfrastructureService().degreeOfEmergency();
			pageBean = pChaseOrderServiceRPC.selectByDynamicListPage(pageBean);
		} catch (Exception e) {
			LOGGER.error("PChaseOrderServiceRPC[selectByDynamicListPage()]获取采购单列表失败!!!错误信息:" + e.getMessage(), e);
			e.printStackTrace();
		}
		LOGGER.info("end<> to execute method <getPurchaseOrderListByCondition>!!!!");
		model.addAttribute("pb", pageBean);
		model.addAttribute("degreeOfEmergency", degreeOfEmergency);

		return "/dealerseller/purchaseorder/pchase_list_model";
	}

	*//**
	 * 根据采购单id查询采购单对应商品详情
	 * 
	 * @param id
	 * @param model
	 * @return String
	 *//*
	@RequestMapping(value = "/findOrderDetail", method = RequestMethod.GET)
	public final String findOrderDetail(Model model, Long id) {
		PChaseOrderServiceRPC pChaseOrderServiceRPC = (PChaseOrderServiceRPC) RemoteServiceSingleton.getInstance().getAppService("pChaseOrderServiceRPC");
		PageBean<PChaseOrderItemDTO> pageBean = new PageBean<PChaseOrderItemDTO>();
		pageBean.setPage(1);
		pageBean.setPageSize(9999);

		PChaseOrderItemDTO itemDTO = new PChaseOrderItemDTO();
		itemDTO.setPurchaseId(id);
		pageBean.setParameter(itemDTO);
		pageBean = pChaseOrderServiceRPC.selectAllItemListPage(pageBean);

		model.addAttribute("pb", pageBean);

		return "dealerseller/purchaseorder/showOrderItem";
	}

	*//**
	 * 根据采购单id查询采购单的采购费用列表
	 * 
	 * @param id
	 * @param model
	 * @return String
	 *//*
	@RequestMapping(value = "/showFeeList", method = RequestMethod.GET)
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

		return "dealerseller/purchaseorder/showFeeList";
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

		return "dealerseller/purchaseorder/showFeeList";
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
		// 查询紧急程度
		List<Dictionarys> degreeOfEmergency = new ArrayList<Dictionarys>();
		// 查询承运方式
		List<Dictionarys> transportWay = new ArrayList<Dictionarys>();
		// 查询账期计算方法
		List<Dictionarys> zhangPeriodCalculation = new ArrayList<Dictionarys>();
		// 查询承运方
		List<Dictionarys> shipper = new ArrayList<Dictionarys>();
		// 查询货物费用分摊方法
		List<Dictionarys> goodsCostAllocation = new ArrayList<Dictionarys>();
		// 采购订单的类型
		List<PurchaseType> purchaseTypeList = new ArrayList<PurchaseType>();
		// 采购公司
		List<Dictionarys> purchaseEntity = new ArrayList<Dictionarys>();
		// 货币
		List<Currency> currencys = new ArrayList<Currency>();
		// 查询税率
		List<Tax> taxs = new ArrayList<Tax>();
		// 付款方式
		List<Dictionarys> payments = new ArrayList<Dictionarys>();
		// 成交条款
		List<Dictionarys> articles = new ArrayList<Dictionarys>();
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
			zhangPeriodCalculation = RemoteServiceSingleton.getInstance().getInfrastructureService().zhangPeriodCalculation();
			shipper = RemoteServiceSingleton.getInstance().getInfrastructureService().shipper();
			goodsCostAllocation = RemoteServiceSingleton.getInstance().getInfrastructureService().goodsCostAllocation();
			currencys = RemoteServiceSingleton.getInstance().getInfrastructureService().findCurrency();
			taxs = RemoteServiceSingleton.getInstance().getInfrastructureService().findTax();
			purchaseTypeList = RemoteServiceSingleton.getInstance().getInfrastructureService().findPurchaseTypeByAll();
			purchaseEntity = RemoteServiceSingleton.getInstance().getInfrastructureService().findPurchasingUnitByAll();
			payments = RemoteServiceSingleton.getInstance().getInfrastructureService().findPaymentTermsByAll();
			articles = RemoteServiceSingleton.getInstance().getInfrastructureService().tradingTerms();
			productType = RemoteServiceSingleton.getInstance().getInfrastructureService().infrastructures("infrastructure_goodsCategory");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

		PChaseOrderServiceRPC pChaseOrderServiceRPC = RemoteServiceSingleton.getInstance().getPChaseOrderServiceRPC();
		PChaseOrderVO pChaseOrderVO = pChaseOrderServiceRPC.findOrderDetailById(id);
		if (pChaseOrderVO.getShipper() > 9) {
			pChaseOrderVO.setShipperChar("0" + pChaseOrderVO.getShipper());
		} else {
			pChaseOrderVO.setShipperChar("00" + pChaseOrderVO.getShipper());
		}
		if (pChaseOrderVO.getPurchaseType() == 2) {
			if (pChaseOrderVO.getArticle() > 9) {
				pChaseOrderVO.setArticleChar("0" + pChaseOrderVO.getArticle());
			} else {
				pChaseOrderVO.setArticleChar("00" + pChaseOrderVO.getArticle());
			}
			if (pChaseOrderVO.getPayment() > 9) {
				pChaseOrderVO.setPaymentChar("0" + pChaseOrderVO.getPayment());
			} else {
				pChaseOrderVO.setPaymentChar("00" + pChaseOrderVO.getPayment());
			}
		}

		User currentUser = getCurrentUser();
		model.addAttribute("sysdate", sysdate);
		model.addAttribute("ChaseOrderId", ChaseOrderId);
		model.addAttribute("degreeOfEmergency", degreeOfEmergency);
		model.addAttribute("transportWay", transportWay);
		model.addAttribute("zhangPeriodCalculation", zhangPeriodCalculation);
		model.addAttribute("shipper", shipper);
		model.addAttribute("goodsCostAllocation", goodsCostAllocation);
		model.addAttribute("currencys", currencys);
		model.addAttribute("taxs", taxs);
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("purchaseEntity", purchaseEntity);
		model.addAttribute("name", name);
		model.addAttribute("purchaseTypeList", purchaseTypeList);
		model.addAttribute("purchaseEntity", purchaseEntity);
		model.addAttribute("pChaseOrderVO", pChaseOrderVO);// 采购主表/对应商品/对应仓库详情信息
		model.addAttribute("type", type);
		model.addAttribute("payments", payments);
		model.addAttribute("articles", articles);
		model.addAttribute("productType", productType);
		LOGGER.info("end<> to execute method <findSupplierList()根据供应商名称查询供应商列表>");

		if (type == 1) {
			return "/dealerseller/purchaseorder/showOrderDetail";// 返回修改页面
		} else {
			return "/dealerseller/purchaseorder/checkOrderDetail";// 返回查看页面
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
		// String filePath =
		// request.getSession().getServletContext().getRealPath("/WEB-INF/views/");

		try {
			PChaseOrderServiceRPC pChaseOrderServiceRPC = RemoteServiceSingleton.getInstance().getPChaseOrderServiceRPC();
			if (orderId != null) {
				PChaseOrderVO pChaseOrderVO = pChaseOrderServiceRPC.findOrderDetailById(orderId);
				WritePDF pdf = new WritePDF();
				result = pdf.getInstance(WritePDF.TYPE01, pChaseOrderVO, request);
			}
		} catch (NumberFormatException e) {
			LOGGER.error("PChaseOrderServiceRPC[finishOrder()]批量修改采购单状态为完成失败!!!错误信息:" + e.getMessage(), e);
			e.printStackTrace();
			result = "批量修改订单完成状态失败";
		}

		return "redirect:/pchaseOrder/viewPDF?fileName=" + result;
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
		PChaseOrderServiceRPC pChaseOrderServiceRPC = RemoteServiceSingleton.getInstance().getPChaseOrderServiceRPC();
		PChaseOrderVO pChaseOrderVO = new PChaseOrderVO();
		String type = request.getParameter("zhao");

		String supplierId = request.getParameter("supplierId");
		String supplierName = request.getParameter("supplierName");
		String paymentDays = request.getParameter("paymentDays");
		// String allocationType = request.getParameter("allocationType");
		String exchangeRate = request.getParameter("exchangeRate");
		String currencyType = request.getParameter("currencyType");
		// String itemTotalPrice = request.getParameter("itemTotalPrice");
		// String totalPrice = request.getParameter("totalPrice");
		// String orderType = request.getParameter("orderType");
		// String status = request.getParameter("status");
		// String totalQty = request.getParameter("totalQty");
		String shipper = request.getParameter("shipper");
		String shipperType = request.getParameter("shipperType");
		String serviceName = request.getParameter("serviceName");
		String serviceCode = request.getParameter("serviceCode");
		String servicePeople = request.getParameter("servicePeople");
		String serviceTel = request.getParameter("serviceTel");
		String receiveAddress = request.getParameter("receiveAddress");
		String sendAdress = request.getParameter("sendAdress");
		// String currency = request.getParameter("currency");
		String contractNumber = request.getParameter("contractNumber");
		// String supplyType = request.getParameter("supplyType");
		String paymentFunction = request.getParameter("paymentFunction");
		String supplierPhone = request.getParameter("supplierPhone");
		String sendDate = request.getParameter("sendate");
		String emergency = request.getParameter("emergency");
		String createBy = request.getParameter("createBy");
		String entityName = request.getParameter("entityName");
		String purchaseEntity = request.getParameter("purchaseEntity");
		String purchaseTypeName = request.getParameter("purchaseTypeName");
		String purchaseType = request.getParameter("purchaseType");
		String article = request.getParameter("article");
		String payment = request.getParameter("payment");
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
			if (paymentDays != null && !"".equals(paymentDays)) {
				pChaseOrderVO.setPaymentDays(new Short(paymentDays));
			}
			if (type.equals("check")) {
				pChaseOrderVO.setOrderStatus((short) 10);
			}
			// pChaseOrderVO.setAllocationType(new Short(allocationType));

			// pChaseOrderVO.setOrderType(new Short(orderType));

			// pChaseOrderVO.setTotalQty(new Long(totalQty));
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
			if (remark != null) {
				pChaseOrderVO.setRemark(remark);
			}
			// pChaseOrderVO.setCurrency(new Long(currency));
			// pChaseOrderVO.setSupplyType(supplyType);
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
				// 国外采购单
				if (2 == Integer.parseInt(purchaseType)) {
					pChaseOrderVO.setExchangeRate(new BigDecimal(exchangeRate));
					pChaseOrderVO.setCurrencyType(currencyType);
					pChaseOrderVO.setArticle(new Integer(article));
					pChaseOrderVO.setPayment(new Integer(payment));
				}
				// 国内采购默认
				if (1 == Integer.parseInt(purchaseType)) {
					pChaseOrderVO.setExchangeRate(new BigDecimal("1"));
					pChaseOrderVO.setCurrencyType("RMB");
				}
			}
			pChaseOrderVO.setSupplierBy(supplierBy);

			List<PChaseOrderItemDTO> orderItems = new ArrayList<PChaseOrderItemDTO>();
			String[] skuId = request.getParameterValues("skuId");
			String[] barCode = request.getParameterValues("barCode");
			String[] pName = request.getParameterValues("pName");
			String[] format = request.getParameterValues("format");
			String[] unit = request.getParameterValues("unit");
			String[] qty = request.getParameterValues("qty");
			String[] skuPriceTax = request.getParameterValues("skuPriceTax");
			String[] dutyRate = request.getParameterValues("dutyRate");
			String[] skuPrice = request.getParameterValues("skuPrice");
			String[] subtotalPrice = request.getParameterValues("subtotalPrice");
			String[] totalPrice1 = request.getParameterValues("totalPrice1");
			String[] currencyType1 = request.getParameterValues("currencyType1");
			String[] exchangeRate1 = request.getParameterValues("exchangeRate1");
			String[] localPrice = request.getParameterValues("localPrice");// 本币单价
			String[] totalLocalPrice = request.getParameterValues("totalLocalPrice");// 本币总价
			String[] localNuTaxPrice = request.getParameterValues("localNuTaxPrice");// 本币不含税单价
			String[] totalLocalNuTaxPrice = request.getParameterValues("totalLocalNuTaxPrice");// 本币不含税总价
			// String[] allocationPrice =
			// request.getParameterValues("allocationPrice");// 分摊后单价
			// String[] allocationPrice1 =
			// request.getParameterValues("allocationPrice1");// 分摊总价
			String[] dutyCode = request.getParameterValues("dutyCode");
			// String[] tax = request.getParameterValues("dutyRate");//分摊总价
			String[] businessProdid = request.getParameterValues("businessProdid");
			String[] wareHouseName = request.getParameterValues("wareHouseName");
			String[] address = request.getParameterValues("address");
			String[] contact = request.getParameterValues("contact");
			String[] telephone = request.getParameterValues("telephone");
			String[] wareHouseCode = request.getParameterValues("wareHouseCode");
			String[] chkTrue = request.getParameter("chkTrue").split(",");// 判断是否选中状态
			for (int i = 0; i < skuId.length; i++) {
				
				PChaseOrderItemDTO pChaseOrderItemDTO = new PChaseOrderItemDTO();
//				if ("0".equals(chkTrue[i])) {
//				pChaseOrderItemDTO.setIsGive(0);
//			} else {
//				pChaseOrderItemDTO.setIsGive(1);
//			}
				pChaseOrderItemDTO.setSkuId(new Long(skuId[i]));
				pChaseOrderItemDTO.setBarCode(barCode[i]);
				pChaseOrderItemDTO.setPname(pName[i]);
				pChaseOrderItemDTO.setFormat(format[i]);
				pChaseOrderItemDTO.setUnit(unit[i]);
				pChaseOrderItemDTO.setQty(Integer.parseInt(qty[i]));
				pChaseOrderItemDTO.setSkuPriceTax(new BigDecimal(skuPriceTax[i]));
				pChaseOrderItemDTO.setDutyRate(new BigDecimal(dutyRate[i]));
				pChaseOrderItemDTO.setDutycode(dutyCode[i]);
				pChaseOrderItemDTO.setSkuPrice(new BigDecimal(skuPrice[i]));
				pChaseOrderItemDTO.setSubtotalPrice(new BigDecimal(subtotalPrice[i]));
				pChaseOrderItemDTO.setTotalPrice(new BigDecimal(totalPrice1[i]));
				pChaseOrderItemDTO.setCurrencyType(currencyType1[i]);
				pChaseOrderItemDTO.setExchangeRate(new BigDecimal(exchangeRate1[i]));
				pChaseOrderItemDTO.setLocalPrice(new BigDecimal(localPrice[i]));
				pChaseOrderItemDTO.setTotalLocalNutaxPrice(new BigDecimal(totalLocalNuTaxPrice[i]));
				pChaseOrderItemDTO.setAllocationPrice(new BigDecimal(0));
				pChaseOrderItemDTO.setAllocationTotalPriceRmb(new BigDecimal(0));
				pChaseOrderItemDTO.setLocalNutaxPrice(new BigDecimal(localNuTaxPrice[i]));
				pChaseOrderItemDTO.setTotalLocalPrice(new BigDecimal(totalLocalPrice[i]));
				pChaseOrderItemDTO.setBusinessProdid(businessProdid[i]);

				orderItems.add(pChaseOrderItemDTO);
			}

			List<WareHouseDTO> wareList = new ArrayList<WareHouseDTO>();
			for (int j = 0; j < wareHouseName.length; j++) {
				WareHouseDTO warehouse = new WareHouseDTO();
				if (wareHouseName[j] != null && !"".equals(wareHouseName[j])) {
					warehouse.setStoreName(wareHouseName[j]);
				}
				if (address[j] != null && !"".equals(address[j])) {
					warehouse.setAddress(address[j]);
				}
				if (contact[j] != null && !"".equals(contact[j])) {
					warehouse.setContact(contact[j]);
				}
				if (telephone[j] != null && !"".equals(telephone[j])) {
					warehouse.setTelephone(telephone[j]);
				}
				if (wareHouseCode[j] != null && !"".equals(wareHouseCode[j])) {
					warehouse.setStoreCode(Long.parseLong(wareHouseCode[j]));
				}
				wareList.add(warehouse);
			}

			pChaseOrderVO.setpCOItemList(orderItems);
			pChaseOrderVO.setWareHouseDTOs(wareList);
			pChaseOrderServiceRPC.updatePChaseOrderbyDTO(pChaseOrderVO);

			if (type.equals("check")) {
				result = "审核采购单成功!";
			} else if (type.equals("update")) {
				result = "修改采购单成功!";
			}
		} catch (Exception e) {
			LOGGER.error("PChaseOrderServiceRPC[updateOrder()]修改采购单失败!!!错误信息:" + e.getMessage(), e);
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
		// 查询账期计算方法
		List<Dictionarys> zhangPeriodCalculation = new ArrayList<Dictionarys>();
		// 查询承运方
		List<Dictionarys> shipper = new ArrayList<Dictionarys>();
		// 查询货物费用分摊方法
		List<Dictionarys> goodsCostAllocation = new ArrayList<Dictionarys>();
		// 采购订单的类型
		List<PurchaseType> purchaseTypeList = new ArrayList<PurchaseType>();
		// 采购公司
		List<Dictionarys> purchaseEntity = new ArrayList<Dictionarys>();
		// 付款方式
		List<Dictionarys> payments = new ArrayList<Dictionarys>();
		// 成交条款
		List<Dictionarys> articles = new ArrayList<Dictionarys>();
		// 货币
		List<Currency> currencys = new ArrayList<Currency>();
		// 查询税率
		List<Tax> taxs = new ArrayList<Tax>();
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
			zhangPeriodCalculation = RemoteServiceSingleton.getInstance().getInfrastructureService().zhangPeriodCalculation();
			shipper = RemoteServiceSingleton.getInstance().getInfrastructureService().shipper();
			goodsCostAllocation = RemoteServiceSingleton.getInstance().getInfrastructureService().goodsCostAllocation();
			currencys = RemoteServiceSingleton.getInstance().getInfrastructureService().findCurrency();
			taxs = RemoteServiceSingleton.getInstance().getInfrastructureService().findTax();
			purchaseTypeList = RemoteServiceSingleton.getInstance().getInfrastructureService().findPurchaseTypeByAll();
			purchaseEntity = RemoteServiceSingleton.getInstance().getInfrastructureService().findPurchasingUnitByAll();
			payments = RemoteServiceSingleton.getInstance().getInfrastructureService().findPaymentTermsByAll();
			articles = RemoteServiceSingleton.getInstance().getInfrastructureService().tradingTerms();
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
		model.addAttribute("zhangPeriodCalculation", zhangPeriodCalculation);
		model.addAttribute("shipper", shipper);
		model.addAttribute("goodsCostAllocation", goodsCostAllocation);
		model.addAttribute("currencys", currencys);
		model.addAttribute("taxs", taxs);
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("purchaseEntity", purchaseEntity);
		model.addAttribute("name", name);
		model.addAttribute("purchaseTypeList", purchaseTypeList);
		model.addAttribute("purchaseEntity", purchaseEntity);
		model.addAttribute("payments", payments);
		model.addAttribute("articles", articles);
		model.addAttribute("productType", productType);

		return "/dealerseller/purchaseorder/addPchaseOrder";
	}

	*//**
	 * 查询供应商列表
	 * 
	 * @param supplierName
	 * @param model
	 * @return String
	 *//*
	@RequestMapping(value = "/findSupplierListByNameAndType")
	public String findSupplierListByNameAndType(String supplierName, String supplyType, Model model) {
		LOGGER.info("start to execute method <findSupplierList()根据供应商名称查询供应商列表>!!!!");
		List<Supplier> suppliers = new ArrayList<Supplier>();
		try {
			SupplierManagerService supplierManagerService = RemoteServiceSingleton.getInstance().getSupplierManagerService();
			if (null != supplyType && "2".equals(supplyType)) {
				suppliers = supplierManagerService.findAllSupplierByNameAndType(supplierName, 50);
			} else {
				suppliers = supplierManagerService.findAllSupplierByNameAndType(supplierName, null);
			}
		} catch (Exception e) {
			LOGGER.error("SupplierManagerService[findAllSupplier() or findAllSupplier(supplierName)] is failed!!!!" + e.getMessage(), e);
		}

		model.addAttribute("supplierList", suppliers);

		return "/dealerseller/purchaseorder/supplier";
	}
	*//**
	 * 查询供应商列表
	 * 
	 * @param supplierName
	 * @param model
	 * @return String
	 *//*
	@RequestMapping(value = "/findSupplierList")
	public String findSupplierList(String supplierName, Model model) {
		LOGGER.info("start to execute method <findSupplierList()根据供应商名称查询供应商列表>!!!!");
		List<Supplier> suppliers = new ArrayList<Supplier>();
		try {
			SupplierManagerService supplierManagerService = RemoteServiceSingleton.getInstance().getSupplierManagerService();
			if (supplierName == null) {
				suppliers = supplierManagerService.findAllSupplier();
			} else {
				suppliers = supplierManagerService.findAllSupplier(supplierName);
			}
		} catch (Exception e) {
			LOGGER.error("SupplierManagerService[findAllSupplier() or findAllSupplier(supplierName)] is failed!!!!" + e.getMessage(), e);
		}
		
		model.addAttribute("supplierList", suppliers);
		
		return "/dealerseller/purchaseorder/supplier";
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
	public String findwareHouseList(String skuName, Model model, Long supplierId) {
		LOGGER.info("start to execute method <findSupplierList()根据供应商名称查询供应商列表>!!!!");
		List<SkuCodeDTO> skuList = new ArrayList<SkuCodeDTO>();
		DealerProdPoRderService dealerProdPoRderService = (DealerProdPoRderService) RemoteServiceSingleton.getInstance().getAppService("dealerProdPoRderService");
		System.out.println("当前调用service:dealerProdPoRderService======"+dealerProdPoRderService);
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

		return "/dealerseller/purchaseorder/skuList";
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

		return "/dealerseller/purchaseorder/wareHouse";
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

		return "/dealerseller/purchaseorder/service";
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
			LOGGER.error("PChaseOrderServiceRPC[finishOrder()]生成采购单编码失败!!!错误信息:" + e.getMessage(), e);
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
		PChaseOrderServiceRPC pChaseOrderServiceRPC = RemoteServiceSingleton.getInstance().getPChaseOrderServiceRPC();
		PChaseOrderVO pChaseOrderVO = new PChaseOrderVO();

		String supplierId = request.getParameter("supplierId");
		String supplierName = request.getParameter("supplierName");
		String paymentDays = request.getParameter("paymentDays");
		// String allocationType = request.getParameter("allocationType");
		String exchangeRate = request.getParameter("exchangeRate");
		String currencyType = request.getParameter("currencyType");
		String itemTotalPrice = request.getParameter("itemTotalPrice");
		// String totalPrice = request.getParameter("totalPrice");
		// String orderType = request.getParameter("orderType");
		// String status = request.getParameter("status");
		// String totalQty = request.getParameter("totalQty");
		String shipper = request.getParameter("shipper");
		String shipperType = request.getParameter("shipperType");
		String serviceName = request.getParameter("serviceName");
		String serviceCode = request.getParameter("serviceCode");
		String servicePeople = request.getParameter("servicePeople");
		String serviceTel = request.getParameter("serviceTel");
		String receiveAddress = request.getParameter("receiveAddress");
		String sendAdress = request.getParameter("sendAdress");
		// String currency = request.getParameter("currency");
		String contractNumber = request.getParameter("contractNumber");
		// String supplyType = request.getParameter("supplyType");
		String paymentFunction = request.getParameter("paymentFunction");
		String supplierPhone = request.getParameter("supplierPhone");
		String sendDate = request.getParameter("sendate");
		String emergency = request.getParameter("emergency");
		String createBy = request.getParameter("createBy");
		String entityName = request.getParameter("entityName");
		String purchaseEntity = request.getParameter("purchaseEntity");
		String purchaseTypeName = request.getParameter("purchaseTypeName");
		String purchaseType = request.getParameter("purchaseType");
		String article = request.getParameter("article");
		String payment = request.getParameter("payment");
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
			if (paymentDays != null && !"".equals(paymentDays)) {
				pChaseOrderVO.setPaymentDays(new Short(paymentDays));
			}
			// pChaseOrderVO.setAllocationType(new Short(allocationType));
			pChaseOrderVO.setStatus(new Short("1"));
			// pChaseOrderVO.setOrderType(new Short(orderType));
			// pChaseOrderVO.setTotalQty(new Long(totalQty));
			if (shipper != null) {
				pChaseOrderVO.setShipper(new Short(shipper));// 承运方
			}
			if (shipperType != null) {
				pChaseOrderVO.setShipperType(shipperType);
			}
			if (serviceName != null) {
				pChaseOrderVO.setServiceName(serviceName);
			}
			if (itemTotalPrice != null) {
				pChaseOrderVO.setItemTotalPrice(new BigDecimal(itemTotalPrice));
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
			// pChaseOrderVO.setCurrency(new Long(currency));
			// pChaseOrderVO.setSupplyType(supplyType);
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
				// 国外采购单
				if (2 == Integer.parseInt(purchaseType)) {
					pChaseOrderVO.setExchangeRate(new BigDecimal(exchangeRate));
					pChaseOrderVO.setCurrencyType(currencyType);
					pChaseOrderVO.setArticle(new Integer(article));
					pChaseOrderVO.setPayment(new Integer(payment));
				}
				// 国内采购默认
				if (1 == Integer.parseInt(purchaseType)) {
					pChaseOrderVO.setExchangeRate(new BigDecimal("1"));
					pChaseOrderVO.setCurrencyType("RMB");
				}
			}

			pChaseOrderVO.setSupplierBy(supplierBy);

			List<PChaseOrderItemDTO> orderItems = new ArrayList<PChaseOrderItemDTO>();
			String[] skuId = request.getParameterValues("skuId");
			String[] businessProdid = request.getParameterValues("businessProdid");
			String[] barCode = request.getParameterValues("barCode");
			String[] pName = request.getParameterValues("pName");
			String[] format = request.getParameterValues("format");
			String[] unit = request.getParameterValues("unit");
			String[] qty = request.getParameterValues("qty");
			String[] skuPriceTax = request.getParameterValues("skuPriceTax");
			String[] dutyRate = request.getParameterValues("dutyRate");
			String[] dutyCode = request.getParameterValues("dutyCode");
			String[] skuPrice = request.getParameterValues("skuPrice");
			String[] subtotalPrice = request.getParameterValues("subtotalPrice");
			String[] totalPrice1 = request.getParameterValues("totalPrice1");
			String[] currencyType1 = request.getParameterValues("currencyType1");
			String[] exchangeRate1 = request.getParameterValues("exchangeRate1");
			String[] localPrice = request.getParameterValues("localPrice");// 本币单价
			String[] totalLocalPrice = request.getParameterValues("totalLocalPrice");// 本币总价
			String[] localNuTaxPrice = request.getParameterValues("localNuTaxPrice");// 本币不含税单价
			String[] totalLocalNuTaxPrice = request.getParameterValues("totalLocalNuTaxPrice");// 本币不含税总价
			// String[] allocationPrice =
			// request.getParameterValues("allocationPrice");// 分摊后单价
			// String[] allocationPrice1 =
			// request.getParameterValues("allocationPrice1");// 分摊总价
			// String[] tax = request.getParameterValues("dutyRate");//分摊总价

			String[] wareHouseName = request.getParameterValues("wareHouseName");
			String[] address = request.getParameterValues("address");
			String[] contact = request.getParameterValues("contact");
			String[] telephone = request.getParameterValues("telephone");
			String[] wareHouseCode = request.getParameterValues("wareHouseCode");

			String[] chkTrue = request.getParameter("chkTrue").split(",");// 判断是否选中状态
			for (int i = 0; i < skuId.length; i++) {
				PChaseOrderItemDTO pChaseOrderItemDTO = new PChaseOrderItemDTO();
//				if ("0".equals(chkTrue[i])) {
//					pChaseOrderItemDTO.setIsGive(0);
//				} else {
//					pChaseOrderItemDTO.setIsGive(1);
//				}

				pChaseOrderItemDTO.setSkuId(new Long(skuId[i]));
				pChaseOrderItemDTO.setBarCode(barCode[i]);
				pChaseOrderItemDTO.setPname(pName[i]);
				pChaseOrderItemDTO.setFormat(format[i]);
				pChaseOrderItemDTO.setUnit(unit[i]);
				pChaseOrderItemDTO.setQty(Integer.parseInt(qty[i]));
				pChaseOrderItemDTO.setSkuPriceTax(new BigDecimal(skuPriceTax[i]));
				pChaseOrderItemDTO.setDutyRate(new BigDecimal(dutyRate[i]));
				pChaseOrderItemDTO.setDutycode(dutyCode[i]);
				pChaseOrderItemDTO.setSkuPrice(new BigDecimal(skuPrice[i]));
				pChaseOrderItemDTO.setSubtotalPrice(new BigDecimal(subtotalPrice[i]));
				pChaseOrderItemDTO.setTotalPrice(new BigDecimal(totalPrice1[i]));
				pChaseOrderItemDTO.setCurrencyType(currencyType1[i]);
				pChaseOrderItemDTO.setExchangeRate(new BigDecimal(exchangeRate1[i]));
				pChaseOrderItemDTO.setLocalPrice(new BigDecimal(localPrice[i]));
				pChaseOrderItemDTO.setTotalLocalNutaxPrice(new BigDecimal(totalLocalNuTaxPrice[i]));
				pChaseOrderItemDTO.setAllocationPrice(new BigDecimal(0));
				pChaseOrderItemDTO.setAllocationTotalPriceRmb(new BigDecimal(0));
				pChaseOrderItemDTO.setLocalNutaxPrice(new BigDecimal(localNuTaxPrice[i]));
				pChaseOrderItemDTO.setTotalLocalPrice(new BigDecimal(totalLocalPrice[i]));
				pChaseOrderItemDTO.setBusinessProdid(businessProdid[i]);

				orderItems.add(pChaseOrderItemDTO);
			}

			List<WareHouseDTO> wareList = new ArrayList<WareHouseDTO>();
			for (int j = 0; j < wareHouseName.length; j++) {
				WareHouseDTO warehouse = new WareHouseDTO();
				if (wareHouseName[j] != null && !"".equals(wareHouseName[j])) {
					warehouse.setStoreName(wareHouseName[j]);
				}
				if (address[j] != null && !"".equals(address[j])) {
					warehouse.setAddress(address[j]);
				}
				if (contact[j] != null && !"".equals(contact[j])) {
					warehouse.setContact(contact[j]);
				}
				if (telephone[j] != null && !"".equals(telephone[j])) {
					warehouse.setTelephone(telephone[j]);
				}
				if (wareHouseCode[j] != null && !"".equals(wareHouseCode[j])) {
					warehouse.setStoreCode(Long.parseLong(wareHouseCode[j]));
				}
				wareList.add(warehouse);
			}

			pChaseOrderVO.setpCOItemList(orderItems);
			// pChaseOrderVO.setAllocationType(new Short("0"));
			// pChaseOrderVO.setPaymentFunction("");
			pChaseOrderVO.setWareHouseDTOs(wareList);
			pChaseOrderServiceRPC.insertSelective(pChaseOrderVO);
			result = "保存采购单成功!";
		} catch (Exception e) {
			LOGGER.error("PChaseOrderServiceRPC[insertSelective()]添加采购单失败!!!错误信息:" + e.getMessage(), e);
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
			PChaseOrderServiceRPC pChaseOrderServiceRPC = RemoteServiceSingleton.getInstance().getPChaseOrderServiceRPC();
			if (orderIds != null && orderIds.length > 0) {
				pChaseOrderServiceRPC.updateStatusBatch(orderIds, new Short("20"));
				result = "批量修改订单完成状态成功";
			}
		} catch (NumberFormatException e) {
			LOGGER.error("PChaseOrderServiceRPC[finishOrder()]批量修改采购单状态为完成失败!!!错误信息:" + e.getMessage(), e);
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
			PChaseOrderServiceRPC pChaseOrderServiceRPC = RemoteServiceSingleton.getInstance().getPChaseOrderServiceRPC();
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
	public String savepChaseorder(PChaseOrderVO pChaseOrderVO, Long[] pid, String[] barCode, String[] pName, String[] format, String[] unit, Long[] qty, BigDecimal[] dutyRate, BigDecimal[] skuPrice,
			BigDecimal[] subtotalPriceRmb, BigDecimal[] allocationTotalPriceRmb, String[] currencyType, BigDecimal[] skuPriceRmb, String[] wareHouseName, String[] address, String[] contact,
			String[] telephone) {

		return "/dealerseller/purchaseorder/addPchaseOrder";
	}

}
*/