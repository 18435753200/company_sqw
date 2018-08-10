/*package com.mall.controller.commoditystore;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.commons.utils.DateUtil;
import com.mall.mybatis.utility.PageBean;
import com.mall.retailer.order.common.PKUtils;
import com.mall.retailer.order.po.Dictionarys;
import com.mall.stock.api.rpc.WarehouseService;
import com.mall.stock.po.Warehouse;
import com.mall.supplier.model.Supplier;
//import com.mall.supplier.order.api.rpc.NotificationOrderProcessService;
//import com.mall.supplier.order.api.rpc.PChaseOrderServiceRPC;
//import com.mall.supplier.order.api.rpc.ShipOrderService;
//import com.mall.supplier.order.api.rpc.WaterService;
//import com.mall.supplier.order.dto.NotificationOrderDTO;
//import com.mall.supplier.order.dto.PChaseOrderItemDTO;
//import com.mall.supplier.order.dto.PChaseOrderVO;
//import com.mall.supplier.order.dto.ShipOrderDto;
//import com.mall.supplier.order.dto.ShipOrderItemDto;
//import com.mall.supplier.order.exception.BusinessException;
//import com.mall.supplier.order.po.NotificationItem;
//import com.mall.supplier.order.po.NotificationOrder;
//import com.mall.supplier.order.po.PChaseStore;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;

*//**
 * . 订单Controller
 * 
 * @author user
 * 
 *//*

@Controller
@RequestMapping(value = "/commoditystore")
public class NotificationInOrderController extends BaseController {

	private static final Log LOGGER = LogFactory.getLogger(NotificationInOrderController.class);

	private static int PAGE_SIZE = 9999;

	*//**
	 * Sping注入mencached缓存客户端.
	 *//*
	*//**
	 * @Autowired private WarehouseService warehouseService;
	 * 
	 * @Autowired private InfrastructureService infrastructureService;
	 *//*

	*//**
	 * 日期的转换
	 *//*
	@Override
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	*//**
	 * @Description： 跳转入库通知单页面
	 * @since: 2015-6-23 下午3:10:18
	 *//*
	@RequestMapping(value = "/getNotificationInOrder", method = { RequestMethod.GET, RequestMethod.POST })
	public final String getNotificationInOrder(Model model) {
		List<Dictionarys> emergencyEnum = new ArrayList<Dictionarys>();
		LOGGER.info("入库通知单页面");
		try {
			WarehouseService warehouseService = (WarehouseService) RemoteServiceSingleton.getInstance().getAppService("warehouseService");
			List<Warehouse> warehouseList = warehouseService.findWarehouseByWareseName(null);
			List<Dictionarys> businessTypeEnum = RemoteServiceSingleton.getInstance().getInfrastructureService().infrastructures("infrastructure_purchaseOrder");
			List<Dictionarys> supplyTypeEnum = RemoteServiceSingleton.getInstance().getInfrastructureService().infrastructures("infrastructure_purchasingDepartment");

			emergencyEnum = RemoteServiceSingleton.getInstance().getInfrastructureService().degreeOfEmergency();

			model.addAttribute("emergencyEnum", emergencyEnum);// 紧急程度
			model.addAttribute("warehouseList", warehouseList);// 库房名称
			model.addAttribute("businessTypeEnum", businessTypeEnum);// 订单来源
			model.addAttribute("supplyTypeEnum", supplyTypeEnum);// 入库类型
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("调用采购单服务异常." + e.getMessage(), e);
		}

		return "/dealerseller/commoditystore/notificationInList";
	}

	*//**
	 * @Description： 入库通知页面初始化请求查询采购单
	 * @since: 2015-6-23 上午10:18:20
	 *//*
	@RequestMapping(value = "/getPurchaseOrderPageBean", method = { RequestMethod.POST, RequestMethod.GET })
	public final String getPurchaseOrderPageBean(HttpServletRequest request, PageBean<PChaseOrderVO> pageBean, String supplierName, String warehouseNameText, Date firstDate, Date lastDate,
			String createBy, String purchaseId, BigDecimal itemTotalPrice, Short orderStatusValue, String emergencyValue, String businessTypeCode, String supplyTypeCode) {

		try {
			// 采购单
			PChaseOrderVO pChaseOrderVO = new PChaseOrderVO();
			if (null != supplierName && !supplierName.equals("")) {

				// 根据供应商名称查询供应商
				List<Supplier> findAllSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService().findAllSupplier(supplierName);
				List<Long> supplierIds = new ArrayList<Long>();// 供应商ID
				for (Supplier supplier : findAllSupplier) {
					Long supplierId = supplier.getSupplierId();
					supplierIds.add(supplierId);

				}
				pChaseOrderVO.setSupplierIds(supplierIds);
			}
			if ("请选择".equals(warehouseNameText)) {// 库房名称
				warehouseNameText = "";
			}
			pChaseOrderVO.setWarehouseName(warehouseNameText);
			// pChaseOrderVO.setCreateTime(firstDate);//制单时间
			pChaseOrderVO.setCreateBy(createBy);
			pChaseOrderVO.setBusinessNumberChar(purchaseId);
			pChaseOrderVO.setItemTotalPrice(itemTotalPrice);
			pChaseOrderVO.setOrderStatus(orderStatusValue == null ? 10 : orderStatusValue);
			pChaseOrderVO.setEmergency(emergencyValue);
			pChaseOrderVO.setSupplyType(supplyTypeCode);
			pChaseOrderVO.setFirstDate(firstDate);
			pChaseOrderVO.setLastDate(lastDate);
			pageBean.setParameter(pChaseOrderVO);// 设置分页
			PChaseOrderServiceRPC pChaseOrderServiceRPC = (PChaseOrderServiceRPC) RemoteServiceSingleton.getInstance().getAppService("pChaseOrderServiceRPC");
			pageBean = pChaseOrderServiceRPC.selectByDynamicListPage(pageBean);

		} catch (Exception e) {
			LOGGER.error("用户:" + getCurrentUser().getUsername());
			LOGGER.error("用户ID:" + getCurrentUser().getId());
			LOGGER.error("获取采购单列表,错误信息:" + e.getMessage(), e);
		}

		request.setAttribute("pb", pageBean);
		// 基数数据查询
		List<Dictionarys> degreeOfEmergency = RemoteServiceSingleton.getInstance().getInfrastructureService().degreeOfEmergency();
		request.setAttribute("degreeOfEmergency", degreeOfEmergency);

		return "/dealerseller/commoditystore/modelpage/list";
	}

	*//**
	 * @Description： 跳转至添加采购单操作页面(查询条件操作页面)
	 * @since: 2015-6-24 上午9:49:29
	 *//*
	@RequestMapping(value = "/getPurchaseOrder", method = RequestMethod.GET)
	public final String getPurchaseOrder(Model model, String purchaseId) {
		PChaseOrderServiceRPC pChaseOrderServiceRPC = (PChaseOrderServiceRPC) RemoteServiceSingleton.getInstance().getAppService("pChaseOrderServiceRPC");
		// 查看采购单详情
		PChaseOrderVO pChaseOrderVO = pChaseOrderServiceRPC.findOrderDetailById(Long.valueOf(purchaseId));
		// 根据采购单id查询仓库列表
		List<PChaseStore> pChaseStoreList = pChaseOrderServiceRPC.findStoreListBypCode(Long.valueOf(purchaseId));
		// PageBean<PChaseOrderItemDTO> pageBean = new
		// PageBean<PChaseOrderItemDTO>();
		List<Dictionarys> transportWay = RemoteServiceSingleton.getInstance().getInfrastructureService().transportWay();
		Dictionarys shippyTypeVO = new Dictionarys();
		for (Dictionarys dictionarys : transportWay) {
			if (pChaseOrderVO.getShipperType() != null && dictionarys.getDictValue().equals(pChaseOrderVO.getShipperType())) {
				shippyTypeVO = dictionarys;
				break;
			}
		}

		WaterService waterservice = (WaterService) RemoteServiceSingleton.getInstance().getAppService("waterService");

		// model.addAttribute("pb", pageBean);
		model.addAttribute("pChaseStoreList", pChaseStoreList);
		model.addAttribute("pChaseOrderVO", pChaseOrderVO);
		model.addAttribute("notificationId", "TR" + DateUtil.toDateStr(new Date(), "yyyyMMdd") + waterservice.getWater("RK"));
		model.addAttribute("shippyTypeVO", shippyTypeVO);

		return "/dealerseller/commoditystore/addNotificationOrder";
	}

	*//**
	 * @Description： 查询采购单详细
	 * @since: 2015-6-24 上午10:02:36
	 *//*
	@RequestMapping(value = "/getPurchaseOrderItemPageBean", method = { RequestMethod.POST, RequestMethod.GET })
	public final String getPurchaseOrderItemPageBean(HttpServletRequest request, PageBean<PChaseOrderItemDTO> pageBean, Long businessNumber) {
		try {

			if (pageBean == null) {
				pageBean = new PageBean<PChaseOrderItemDTO>();
				pageBean.setPage(1);
			}
			pageBean.setPageSize(PAGE_SIZE);
			PChaseOrderItemDTO pChaseOrderItemDTO = new PChaseOrderItemDTO();
			pChaseOrderItemDTO.setPurchaseId(businessNumber);
			pageBean.setParameter(pChaseOrderItemDTO);

			PChaseOrderServiceRPC pChaseOrderServiceRPC = (PChaseOrderServiceRPC) RemoteServiceSingleton.getInstance().getAppService("pChaseOrderServiceRPC");
			pageBean = pChaseOrderServiceRPC.selectItemListPage(pageBean);
			NotificationOrderProcessService notificationOrderProcessService = (NotificationOrderProcessService) RemoteServiceSingleton.getInstance().getAppService("notificationOrderProcessService");
			List<NotificationItem> notificationItemList = notificationOrderProcessService.findNotificationOrderByPurchaseId(businessNumber);

			// Integer totalQty = 0;
			List<PChaseOrderItemDTO> pChaseOrderItemList = pageBean.getResult();
			for (PChaseOrderItemDTO pChaseOrderItem : pChaseOrderItemList) {
				for (NotificationItem notificationItem : notificationItemList) {
					if (notificationItem.getSkuId().equals(pChaseOrderItem.getSkuId())) {
						pChaseOrderItem.setQtyReceived(notificationItem.getQtyReceived());
						continue;
					}
				}
				// totalQty = totalQty +
				// (pChaseOrderItem.getQty().intValue()-pChaseOrderItem.getQtyReceived().intValue());
			}
			// request.setAttribute("totalNotifyQty", totalQty);

		} catch (Exception e) {
			LOGGER.error("用户:" + getCurrentUser().getUsername());
			LOGGER.error("用户ID:" + getCurrentUser().getId());
			LOGGER.error("获取采购单列表,错误信息:" + e.getMessage(), e);
		}

		request.setAttribute("pb", pageBean);

		return "/dealerseller/commoditystore/modelpage/notificationorderitemlist";
	}

	*//**
	 * @Description： 保存入库通知单
	 * @since: 2015-6-24 上午10:06:43
	 *//*
	@RequestMapping(value = "/addNotificationOrder", method = RequestMethod.POST)
	public final String addNotificationOrder(HttpServletRequest request, Model model) {

		try {
			NotificationOrder notificationOrder = new NotificationOrder();
			// notificationOrder.setId(Long.valueOf(request.getParameter("notificationId")));
			notificationOrder.setNotificationChar(request.getParameter("notificationId"));
			notificationOrder.setReceiveName(request.getParameter("receiveName"));
			notificationOrder.setReceivePhone(request.getParameter("receivePhone"));
			// notificationOrder.setTotalQty(request.getParameter("totalQty"));
			// notificationOrder.setTotalQty(Integer.valueOf(request.getParameter("totalNotifyQty")));
			notificationOrder.setCreateTime(DateUtil.strToDate(request.getParameter("createTime")));
			notificationOrder.setCreateBy(getCurrentUser().getUsername());
			notificationOrder.setStatus(1);
			notificationOrder.setVersion(1L);
			notificationOrder.setWarehouseCodeChar(request.getParameter("storeCode"));
			notificationOrder.setWarehouseName(request.getParameter("storeName"));
			notificationOrder.setServiceName(request.getParameter("serviceName"));
			if (request.getParameter("serviceCode") != null && !"".equals(request.getParameter("serviceCode"))) {
				notificationOrder.setServiceCode(Long.valueOf(request.getParameter("serviceCode")));
			}

			notificationOrder.setShipperType(request.getParameter("shipperType"));
			notificationOrder.setBusinessNumber(Long.valueOf(request.getParameter("businessNumber")));
			notificationOrder.setBusinessType(request.getParameter("businessType") == null || request.getParameter("businessType") == "" ? "101" : request.getParameter("businessType"));
			notificationOrder.setSupplyType(request.getParameter("supplyType"));
			notificationOrder.setEstimateDate(DateUtil.strToDate(request.getParameter("estimateDate")));
			notificationOrder.setSupplierCode(Long.valueOf(request.getParameter("supplierCode")));
			notificationOrder.setSupplierName(request.getParameter("supplierName"));
			notificationOrder.setSendAddress(request.getParameter("sendAddress"));
			notificationOrder.setEmergencyCode(request.getParameter("emergencyCode"));
			notificationOrder.setEmergencyValue(request.getParameter("emergencyValue"));

			// notificationOrder.setTotalPrice(request.getParameter("totalPrice"));
			notificationOrder.setBusinessStatus(request.getParameter("businessStatus"));
			// notificationOrder.setTotalPrice(new BigDecimal("0"));

			notificationOrder.setBusinessNumberChar(request.getParameter("businessNumberChar"));

			Integer totalNotityQty = 0;
			BigDecimal totalPrice = new BigDecimal("0.00");

			String[] pid = request.getParameterValues("pid");
			String[] barCode = request.getParameterValues("barCode");
			String[] skuNameCn = request.getParameterValues("pname");
			String[] format = request.getParameterValues("format");
			String[] unit = request.getParameterValues("unit");
			String[] qty = request.getParameterValues("qty");
			String[] notifySendQty = request.getParameterValues("notifySendQty");
			String[] purchaseOrderItemId = request.getParameterValues("purchaseOrderItemId");
			String[] skuId = request.getParameterValues("skuId");
			// String[] bussinessProdid =
			// request.getParameterValues("bussinessProdid");
			String[] skuNameEn = request.getParameterValues("skuNameEn");
			String[] pcode = request.getParameterValues("pcode");
			String[] price = request.getParameterValues("price");
			 String[] qtyReceived = request.getParameterValues("qtyReceived"); 
			String[] itemId = request.getParameterValues("itemId");
			String[] isGive = request.getParameterValues("isGive");

			String[] chkTrue = request.getParameter("chkTrue").split(",");

			if (purchaseOrderItemId == null) {
				throw new BusinessException("请选择需要入库通知的商品");
			}
			String strItemId = StringUtils.join(purchaseOrderItemId, ",");
			List<NotificationItem> notificationItemList = new ArrayList<NotificationItem>();
			Map<Long, Integer> limitQtyMap = new HashMap<Long, Integer>();
			NotificationOrderProcessService notificationOrderProcessService = (NotificationOrderProcessService) RemoteServiceSingleton.getInstance().getAppService("notificationOrderProcessService");
			List<NotificationItem> checkNotificationItemList = notificationOrderProcessService.findNotificationOrderByPurchaseId(Long.valueOf(request.getParameter("businessNumber")));
			if (checkNotificationItemList != null || checkNotificationItemList.size() > 0) {
				for (int i = 0; i < checkNotificationItemList.size(); i++) {
					NotificationItem notificationItem = checkNotificationItemList.get(i);
					limitQtyMap.put(notificationItem.getSkuId(), notificationItem.getQtyReceived());
				}
			}
			for (int i = 0; i < pid.length; i++) {
				if (strItemId.indexOf(itemId[i]) == -1) {
					continue;
				}
				if (notifySendQty[i] == null || notifySendQty[i].equals("") || notifySendQty[i].equals("0")) {
					continue;
				}
				Integer limitQty = limitQtyMap.get(Long.valueOf(skuId[i])) == null ? 0 : limitQtyMap.get(Long.valueOf(skuId[i]));
				if (Integer.valueOf(qty[i]) < (limitQty + Integer.valueOf(notifySendQty[i]))) {
					throw new BusinessException("通知数量大于剩余数量");
				}
				if ("0".equals(chkTrue[i])) {
					continue;
				}

				NotificationItem notificationItem = new NotificationItem();
				notificationItem.setPid(pid[i] == "" || pid[i] == null ? null : Long.valueOf(pid[i]));
				// notificationItem.setNotificationId(request.getParameter("notificationId"));
				notificationItem.setSkuId(Long.valueOf(skuId[i]));
				notificationItem.setSkuNameCn(skuNameCn[i]);
				notificationItem.setSkuNameEn(skuNameEn[i]);
				notificationItem.setQty(Integer.valueOf(qty[i]));
				notificationItem.setQtyReceived(Integer.valueOf(notifySendQty[i]));
				notificationItem.setFormat(format[i]);
				notificationItem.setUnit(unit[i]);
				notificationItem.setPcode(pcode[i]);

				notificationItem.setBarCode(barCode[i]);
				notificationItem.setPrice(new BigDecimal(price[i]));

//				Integer flag = 0;
//				if (StringUtils.isNotBlank(isGive[i])) {
//					flag = Integer.valueOf(isGive[i]);
//				} else {
//					flag = 0;
//				}
//				notificationItem.setIsGive(flag);
				notificationItemList.add(notificationItem);

				totalNotityQty = totalNotityQty + Integer.valueOf(notifySendQty[i]);
				BigDecimal notifyPrice = (new BigDecimal(notifySendQty[i])).multiply(new BigDecimal(price[i]));
				totalPrice = totalPrice.add(notifyPrice);

			}
			notificationOrder.setTotalQty(totalNotityQty);
			notificationOrder.setTotalPrice(totalPrice);

			NotificationOrderDTO notificationOrderDTO = new NotificationOrderDTO();
			notificationOrderDTO.setNotificationOrder(notificationOrder);
			notificationOrderDTO.setNotificationItemList(notificationItemList);

			notificationOrderProcessService.insertNotificationOrder(notificationOrderDTO);
		} catch (Exception e) {
			LOGGER.error("用户:" + getCurrentUser().getUsername());
			LOGGER.error("用户ID:" + getCurrentUser().getId());
			LOGGER.error("保存入库通知单失败,错误信息:" + e.getMessage(), e);
		} finally {
			List<Dictionarys> emergencyEnum = RemoteServiceSingleton.getInstance().getInfrastructureService().degreeOfEmergency();
			model.addAttribute("emergencyEnum", emergencyEnum);
			model.addAttribute("supplierName", request.getParameter("supplierName"));
			// request.getSession().setAttribute("pb",null);
		}

		return "success";
	}

	*//**
	 * @Description： 审核入库通知单
	 * @since: 2015-6-25 下午3:06:38
	 *//*
	@RequestMapping(value = "/checkNotificationOrder", method = { RequestMethod.POST, RequestMethod.GET })
	public final String checkNotificationOrder(HttpServletRequest request, Model model, Long notificationId, Long[] itemId, Long[] skuId, Integer[] qty, Integer[] notifySendQty, Long purchaseId) {
		try {
			ShipOrderService shipOrderService = (ShipOrderService) RemoteServiceSingleton.getInstance().getAppService("shipOrderService");
			PChaseOrderServiceRPC pChaseOrderServiceRPC = (PChaseOrderServiceRPC) RemoteServiceSingleton.getInstance().getAppService("pChaseOrderServiceRPC");
			NotificationOrderProcessService notificationOrderProcessService = (NotificationOrderProcessService) RemoteServiceSingleton.getInstance().getAppService("notificationOrderProcessService");
			Map<Long, Integer> limitQtyMap = new HashMap<Long, Integer>();
			List<NotificationItem> checkNotificationItemList = notificationOrderProcessService.findNotificationOrderByPurchaseId(purchaseId);
			if (checkNotificationItemList != null || checkNotificationItemList.size() > 0) {
				for (int i = 0; i < checkNotificationItemList.size(); i++) {
					NotificationItem ni = checkNotificationItemList.get(i);
					limitQtyMap.put(ni.getSkuId(), ni.getQtyReceived());
				}
			}
			List<NotificationItem> notificationItemList = new ArrayList<NotificationItem>();
			for (int i = 0; i < skuId.length; i++) {
				Integer useQty = limitQtyMap.get(skuId[i]) == null ? 0 : limitQtyMap.get(skuId[i]);
				if (notifySendQty[i] > (qty[i] - useQty)) {
					Integer usableQty = qty[i] - useQty;
					throw new BusinessException("入库通知单明细(" + itemId[i] + "),通知数量超过可通知数量(" + usableQty + ")");
				}
				NotificationItem notificationItem = new NotificationItem();
				notificationItem.setId(itemId[i]);
				notificationItem.setQtyReceived(notifySendQty[i]);
				notificationItemList.add(notificationItem);

			}
			notificationOrderProcessService.updateNotificationItem(notificationItemList);

			NotificationOrder notificationOrder = new NotificationOrder();
			notificationOrder.setId(notificationId);
			notificationOrder.setStatus(10);
			notificationOrderProcessService.updateNotificationOrder(notificationOrder);
			NotificationOrder notification = notificationOrderProcessService.findNotificationOrderByLikeId(notificationId).get(0);
			PChaseOrderVO vo = pChaseOrderServiceRPC.findOrderDetailById(notification.getBusinessNumber());
			List<NotificationItem> items = notificationOrderProcessService.selectByNotificationId(notificationId);
			if (vo.getShipper() == 1) {
				java.text.SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
				ShipOrderDto shipOrderDto = new ShipOrderDto();
				shipOrderDto.setArriveDate(notification.getEstimateDate());
				shipOrderDto.setBusinessNumber(notification.getBusinessNumber());
				shipOrderDto.setBusinessNumberChar(notification.getBusinessNumberChar());
				shipOrderDto.setBusinessType(notification.getBusinessType());
				shipOrderDto.setCreateBy(notification.getCreateBy());
				shipOrderDto.setCreateTime(new Date());
				shipOrderDto.setId(PKUtils.getLongPrimaryKey());
				shipOrderDto.setNotificationId(notificationId);
				shipOrderDto.setNotificationIdChar(notification.getNotificationChar());
				shipOrderDto.setTotalPrice(notification.getTotalPrice());
				shipOrderDto.setReceiveAddress(notification.getSendAddress());
				shipOrderDto.setReceiveName(notification.getReceiveName());
				shipOrderDto.setReceivePhone(notification.getReceivePhone());
				shipOrderDto.setServiceCode(notification.getServiceCode());
				shipOrderDto.setServiceName(notification.getServiceName());
				WaterService waterService = (WaterService) RemoteServiceSingleton.getInstance().getAppService("waterService");
				String key = "TW" + format.format(new Date()) + waterService.getWater("TW");
				shipOrderDto.setShipChar(key);
				shipOrderDto.setShipperType(notification.getShipperType());
				shipOrderDto.setStatus(10);
				shipOrderDto.setSupplierName(notification.getSupplierName());
				shipOrderDto.setSupplyType(notification.getSupplyType());
				shipOrderDto.setTotalQty(notification.getTotalQty());

				List<ShipOrderItemDto> itemDtos = new ArrayList<ShipOrderItemDto>();
				for (NotificationItem item : items) {
					ShipOrderItemDto dto = new ShipOrderItemDto();
					dto.setBarCode(item.getBarCode());
					dto.setFormat(item.getFormat());
					dto.setId(PKUtils.getLongPrimaryKey());
					dto.setPcode(item.getPcode());
					dto.setPid(item.getPid());
					dto.setPrice(item.getPrice());
					dto.setPurchaseId(shipOrderDto.getId());
					dto.setQty(item.getQty());
					dto.setQtyReceived(item.getQtyReceived());
					dto.setShipId(shipOrderDto.getId());
					dto.setSkuId(item.getSkuId());
					dto.setSkuNameCn(item.getSkuNameCn());
					dto.setSkuNameEn(item.getSkuNameEn());
					dto.setUnit(item.getUnit());
					itemDtos.add(dto);

				}
				shipOrderDto.setItemDtos(itemDtos);
				shipOrderService.insertShipOrder(shipOrderDto);
			}

		} catch (Exception e) {

			LOGGER.error("用户:" + getCurrentUser().getUsername());
			LOGGER.error("用户ID:" + getCurrentUser().getId());
			LOGGER.error("审核入库通知单失败,错误信息:" + e.getMessage(), e);
			return "fail";
		}
		return "success";

	}

	*//**
	 * @Description： 通过采购订单号查询入库通知单
	 * @since: 2015-6-23 下午3:20:30
	 *//*
	@RequestMapping(value = "/getNotificationOrderPageBean", method = { RequestMethod.POST, RequestMethod.GET })
	public final String getNotificationOrderPageBean(HttpServletRequest request) {
		String purchaseId = request.getParameter("purchaseId");
		List<NotificationOrder> notificationOrderList = new ArrayList<NotificationOrder>();
		try {
			NotificationOrder notificationOrder = new NotificationOrder();
			notificationOrder.setBusinessNumber(Long.valueOf(purchaseId));

			List<Dictionarys> shipperList = RemoteServiceSingleton.getInstance().getInfrastructureService().shipper();

			// 获取入库服务
			NotificationOrderProcessService notificationOrderProcessService = (NotificationOrderProcessService) RemoteServiceSingleton.getInstance().getAppService("notificationOrderProcessService");

			// 查询全部入库单和其详细内容(关系一对多)
			List<NotificationOrderDTO> notificationOrderDTOList = notificationOrderProcessService.findNotificationOrder(notificationOrder);
			if (notificationOrderDTOList != null && notificationOrderDTOList.size() > 0) {
				// TODO 需要修改
				for (NotificationOrderDTO notificationOrderDTO : notificationOrderDTOList) {
					NotificationOrder no = notificationOrderDTO.getNotificationOrder();
					for (Dictionarys shipper : shipperList) {
						if (shipper.getDictCode().equals(no.getShipperType())) {
							no.setShipperTypeValue(shipper.getDictValue());
						}
					}
					notificationOrderList.add(no);
				}
			}

		} catch (Exception e) {
			LOGGER.error("用户:" + getCurrentUser().getUsername());
			LOGGER.error("用户ID:" + getCurrentUser().getId());
			LOGGER.error("获取采购单列表,错误信息:" + e.getMessage(), e);
		}

		request.setAttribute("notificationOrderList", notificationOrderList);
		List<Dictionarys> transportWays = RemoteServiceSingleton.getInstance().getInfrastructureService().transportWay();
		request.setAttribute("transportWays", transportWays);

		return "/dealerseller/commoditystore/notificationinitemList";
	}

	*//**
	 * @Description： 查看入库通知单(查询条件展示)
	 * @author: QIJJ
	 * @since: 2015-6-23 下午5:28:20
	 *//*
	@RequestMapping(value = "/getNotificationInOrderView", method = { RequestMethod.POST, RequestMethod.GET })
	public final String getNotificationInOrderView(HttpServletRequest request, Model model, Long notifyId) {
		try {
			NotificationOrder notificationOrder = null;
			// 通过字典表查询承运方
			List<Dictionarys> shipperList = RemoteServiceSingleton.getInstance().getInfrastructureService().shipper();
			NotificationOrderProcessService notificationOrderProcessService = (NotificationOrderProcessService) RemoteServiceSingleton.getInstance().getAppService("notificationOrderProcessService");
			List<NotificationOrder> notificationOrderList = notificationOrderProcessService.findNotificationOrderByLikeId(notifyId);
			// 通过字段查询承运方式
			List<Dictionarys> transportWays = RemoteServiceSingleton.getInstance().getInfrastructureService().transportWay();
			if (notificationOrderList != null && notificationOrderList.size() > 0) {
				notificationOrder = notificationOrderList.get(0);
				for (Dictionarys shipper : shipperList) {
					if (shipper.getDictCode().equals(notificationOrder.getShipperType())) {
						notificationOrder.setShipperTypeValue(shipper.getDictValue());
					}
				}
			}

			model.addAttribute("notificationOrder", notificationOrder);
			model.addAttribute("transportWays", transportWays);
		} catch (Exception e) {
			LOGGER.error("用户:" + getCurrentUser().getUsername());
			LOGGER.error("用户ID:" + getCurrentUser().getId());
			LOGGER.error("查看入库通知单失败,错误信息:" + e.getMessage(), e);
		}

		return "/dealerseller/commoditystore/notificationOrderView";
	}

	*//**
	 * @Description： 查看入库通知单详细
	 * @since: 2015-6-23 下午5:55:36
	 *//*
	@RequestMapping(value = "/getNotificationItemViewPageBean", method = { RequestMethod.POST, RequestMethod.GET })
	public final String getNotificationItemViewPageBean(HttpServletRequest request, PageBean<NotificationItem> pageBean, Long notificationId) {

		try {
			if (pageBean == null) {
				pageBean.setPage(1);
				pageBean.setPageSize(20);
			}
			NotificationItem notificationItem = new NotificationItem();
			notificationItem.setNotificationId(notificationId);
			pageBean.setParameter(notificationItem);
			NotificationOrderProcessService notificationOrderProcessService = (NotificationOrderProcessService) RemoteServiceSingleton.getInstance().getAppService("notificationOrderProcessService");
			pageBean = notificationOrderProcessService.findNotificationItemByPageBean(pageBean);

		} catch (Exception e) {

			LOGGER.error("用户:" + getCurrentUser().getUsername());
			LOGGER.error("用户ID:" + getCurrentUser().getId());

			LOGGER.error("获取采购单列表,错误信息:" + e.getMessage(), e);
		}

		request.setAttribute("pb", pageBean);

		return "/dealerseller/commoditystore/modelpage/notificationorderitemlist_view";

	}

	@RequestMapping(value = "/getNotificationInOrderUpdate", method = { RequestMethod.POST, RequestMethod.GET })
	public final String getNotificationInOrderUpdate(HttpServletRequest request, Model model, Long notifyId) {

		try {

			NotificationOrder notificationOrder = null;
			NotificationOrderProcessService notificationOrderProcessService = (NotificationOrderProcessService) RemoteServiceSingleton.getInstance().getAppService("notificationOrderProcessService");

			List<Dictionarys> shipperList = RemoteServiceSingleton.getInstance().getInfrastructureService().shipper();
			List<NotificationOrder> notificationOrderList = notificationOrderProcessService.findNotificationOrderByLikeId(notifyId);
			List<Dictionarys> transportWays = RemoteServiceSingleton.getInstance().getInfrastructureService().transportWay();

			if (notificationOrderList != null && notificationOrderList.size() > 0) {
				notificationOrder = notificationOrderList.get(0);
				for (Dictionarys shipper : shipperList) {
					if (shipper.getDictCode().equals(notificationOrder.getShipperType())) {
						notificationOrder.setShipperTypeValue(shipper.getDictValue());
					}
				}
			}
			model.addAttribute("notificationOrder", notificationOrder);
			model.addAttribute("transportWays", transportWays);

		} catch (Exception e) {

			LOGGER.error("用户:" + getCurrentUser().getUsername());
			LOGGER.error("用户ID:" + getCurrentUser().getId());
			LOGGER.error("查看入库通知单失败,错误信息:" + e.getMessage(), e);
		}
		return "/dealerseller/commoditystore/notificationOrderUpdate";

	}

	@RequestMapping(value = "/getNotificationItemUpdatePageBean", method = { RequestMethod.POST, RequestMethod.GET })
	public final String getNotificationItemUpdatePageBean(HttpServletRequest request, PageBean<NotificationItem> pageBean, Long notificationId) {

		List<NotificationOrder> notificationOrderList = new ArrayList<NotificationOrder>();
		try {
			if (pageBean == null) {
				pageBean.setPage(1);
				pageBean.setPageSize(20);
			}
			NotificationItem notificationItem = new NotificationItem();
			notificationItem.setNotificationId(notificationId);
			pageBean.setParameter(notificationItem);
			NotificationOrderProcessService notificationOrderProcessService = (NotificationOrderProcessService) RemoteServiceSingleton.getInstance().getAppService("notificationOrderProcessService");
			pageBean = notificationOrderProcessService.findNotificationItemByPageBean(pageBean);

		} catch (Exception e) {

			LOGGER.error("用户:" + getCurrentUser().getUsername());
			LOGGER.error("用户ID:" + getCurrentUser().getId());

			LOGGER.error("获取采购单列表,错误信息:" + e.getMessage(), e);
		}

		request.setAttribute("pb", pageBean);

		return "/dealerseller/commoditystore/modelpage/notificationorderitemlist_update";

	}

	@RequestMapping(value = "/checkReceivedQty", method = { RequestMethod.POST, RequestMethod.GET })
	public final String checkReceivedQty(HttpServletRequest request, Long skuId, Integer qty, Integer qtyReceived, Long purchaseId) {
		try {
			NotificationOrderProcessService notificationOrderProcessService = (NotificationOrderProcessService) RemoteServiceSingleton.getInstance().getAppService("notificationOrderProcessService");
			Map<Long, Integer> limitQtyMap = new HashMap<Long, Integer>();
			List<NotificationItem> checkNotificationItemList = notificationOrderProcessService.findNotificationOrderByPurchaseId(purchaseId);
			if (checkNotificationItemList != null || checkNotificationItemList.size() > 0) {
				for (int i = 0; i < checkNotificationItemList.size(); i++) {
					NotificationItem ni = checkNotificationItemList.get(i);
					limitQtyMap.put(ni.getSkuId(), ni.getQtyReceived());
				}
			}
			Integer useQty = limitQtyMap.get(skuId) == null ? 0 : limitQtyMap.get(skuId);
			if (qtyReceived > (qty - useQty)) {
				Integer usableQty = qty - limitQtyMap.get(skuId);
				return usableQty.toString();
			}

		} catch (Exception e) {

			LOGGER.error("用户:" + getCurrentUser().getUsername());
			LOGGER.error("用户ID:" + getCurrentUser().getId());

			LOGGER.error("验证通知数量异常,错误信息:" + e.getMessage(), e);
		}

		return "";
	}

	@RequestMapping(value = "/updateNotificationItem", method = { RequestMethod.POST, RequestMethod.GET })
	public final String updateNotificationItem(HttpServletRequest request, Long[] itemId, Integer[] notifySendQty) {
		List<NotificationItem> notificationOrderList = new ArrayList<NotificationItem>();
		try {
			for (int i = 0; i < itemId.length; i++) {
				NotificationItem notificationItem = new NotificationItem();
				notificationItem.setId(itemId[i]);
				notificationItem.setQtyReceived(notifySendQty[i]);
				notificationOrderList.add(notificationItem);
			}
			NotificationOrderProcessService notificationOrderProcessService = (NotificationOrderProcessService) RemoteServiceSingleton.getInstance().getAppService("notificationOrderProcessService");
			notificationOrderProcessService.updateNotificationItem(notificationOrderList);

		} catch (Exception e) {

			LOGGER.error("用户:" + getCurrentUser().getUsername());
			LOGGER.error("用户ID:" + getCurrentUser().getId());

			LOGGER.error("获取采购单列表,错误信息:" + e.getMessage(), e);
		}

		return "success";

	}

}
*/