/*package com.mall.controller.order;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.mybatis.utility.PageBean;
import com.mall.retailer.order.common.PKUtils;
import com.mall.retailer.order.po.Dictionarys;
import com.mall.supplier.order.api.rpc.InfrastructureService;
import com.mall.supplier.order.api.rpc.NotificationOrderProcessService;
import com.mall.supplier.order.api.rpc.PChaseOrderServiceRPC;
import com.mall.supplier.order.api.rpc.ShipOrderService;
import com.mall.supplier.order.api.rpc.WaterService;
import com.mall.supplier.order.dto.NotificationOrderDTO;
import com.mall.supplier.order.dto.NotificationOrderVo;
import com.mall.supplier.order.dto.PChaseOrderItemDTO;
import com.mall.supplier.order.dto.PChaseOrderVO;
import com.mall.supplier.order.dto.ShipOrderDto;
import com.mall.supplier.order.dto.ShipOrderItemDto;
import com.mall.supplier.order.po.NotificationItem;
import com.mall.supplier.order.po.NotificationOrder;
import com.mall.supplier.order.po.ServiceArchives;
import com.mall.supplier.order.po.ShipOrder;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;

*//**
 * @ClassName ShipOrderController
 * @Description: 物流通知控制层
 * @author: QIJJ
 * @since: 2015-6-25 上午11:20:50
 *//*
@Controller
@RequestMapping(value = "/shiporder")
public class ShipOrderController extends BaseController {

	private static final Log LOG = LogFactory.getLogger(ShipOrderController.class);

	*//**
	 * @Description： 查询物流通知对应全部审核通过入库通知单
	 * @author: QIJJ
	 * @since: 2015-6-24 下午5:05:35
	 *//*
	@RequestMapping(value = "/findShipOrderList", method = { RequestMethod.GET, RequestMethod.POST })
	public String findShipOrderList(HttpServletRequest request, Integer page, Model model) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String businessNumber = request.getParameter("businessNumber");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String createBy = request.getParameter("createBy");
		String price = request.getParameter("price");
		String status = request.getParameter("status");
		String emergency = request.getParameter("emergency");
		String supplierName = request.getParameter("supplierName");
		NotificationOrderProcessService notificationOrderProcessService = (NotificationOrderProcessService) RemoteServiceSingleton.getInstance().getAppService("notificationOrderProcessService");
		NotificationOrderVo notificationOrder = new NotificationOrderVo();
		PageBean<NotificationOrder> pageBean = new PageBean<NotificationOrder>();

		try {
			if (businessNumber != null && !"".equals(businessNumber)) {
				notificationOrder.setBusinessNumberChar(businessNumber);
			}
			if (startTime != null && !"".equals(startTime)) {
				notificationOrder.setBeginCreateTime(format.parse(startTime));
			}
			if (endTime != null && !"".equals(endTime)) {
				notificationOrder.setEndCreateTime(format.parse(endTime));
			}
			if (price != null && !"".equals(price)) {
				notificationOrder.setTotalPrice(new BigDecimal(price));
			}
			if (createBy != null && !"".equals(createBy)) {
				notificationOrder.setCreateBy(createBy);
			}
			if (status != null && !"".equals(status)) {
				notificationOrder.setStatus(Integer.parseInt(status));
			}
			if (supplierName != null && !"".equals(supplierName)) {
				notificationOrder.setSupplierName(supplierName);
			}
			if (emergency != null && !"".equals(emergency)) {
				notificationOrder.setEmergencyCode(emergency);
			}
			pageBean.setParameter(notificationOrder);
			pageBean.setPageSize(Constants.PAGESIZE);
			if (page != null) {
				pageBean.setPage(page);
			} else {
				pageBean.setPage(Constants.INT1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		pageBean = notificationOrderProcessService.findNotificationOrderByPageBean(pageBean);
		// parameter.set
		List<Dictionarys> degreeOfEmergency = RemoteServiceSingleton.getInstance().getInfrastructureService().degreeOfEmergency();
		model.addAttribute("degreeOfEmergency", degreeOfEmergency);
		model.addAttribute("pb", pageBean);

		return "/shiporder/findShipOrderList";
	}

	*//**
	 * @Description： 点击物流通知，跳转页面
	 * @author: QIJJ
	 * @since: 2015-6-24 下午5:04:36
	 *//*
	@RequestMapping(value = "/findShipOrder")
	public String findShipOrder(Model model) {
		List<Dictionarys> degreeOfEmergency = RemoteServiceSingleton.getInstance().getInfrastructureService().degreeOfEmergency();
		model.addAttribute("degreeOfEmergency", degreeOfEmergency);

		return "/shiporder/findShipOrder";
	}

	*//**
	 * @Description： 通过采购单号查询物流通知单
	 * @author: QIJJ
	 * @since: 2015-6-24 下午5:09:37
	 *//*
	@RequestMapping(value = "/findShipOrderItem")
	public String findShipOrderItem(Long id, Integer page, Model model) {
		ShipOrderService shipOrderService = (ShipOrderService) RemoteServiceSingleton.getInstance().getAppService("shipOrderService");
		PageBean<ShipOrderDto> pageBean = shipOrderService.selectShipOrderByIDListPage(id);
		if (page != null) {
			pageBean.setPage(page);
		} else {
			pageBean.setPage(Constants.INT1);
		}

		List<Dictionarys> transportWays = RemoteServiceSingleton.getInstance().getInfrastructureService().transportWay();
		pageBean.setPageSize(Constants.PAGESIZE);
		model.addAttribute("pb", pageBean);
		model.addAttribute("transportWays", transportWays);

		return "/shiporder/findShipOrderItem";
	}

	@RequestMapping(value = "/saveShipOrder")
	public String saveShipOrder(HttpServletRequest request, ShipOrderDto shipOrderDto, Integer page, Model model) {
		model.addAttribute("businessNumber", request.getParameter("businessNumber") == null ? "" : request.getParameter("businessNumber"));
		model.addAttribute("notificationId", request.getParameter("notificationOrder") == null ? "" : request.getParameter("notificationOrder"));
		java.text.SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		WaterService waterService = (WaterService) RemoteServiceSingleton.getInstance().getAppService("waterService");
		String key = "TW" + format.format(new Date()) + waterService.getWater("TW");
		model.addAttribute("nowTime", format.format(new Date()));
		NotificationOrderProcessService notificationOrderProcessService = (NotificationOrderProcessService) RemoteServiceSingleton.getInstance().getAppService("notificationOrderProcessService");
		NotificationOrder notificationOrder = new NotificationOrder();
		notificationOrder.setId(Long.parseLong(request.getParameter("notificationOrder") == null ? "" : request.getParameter("notificationOrder")));
		List<NotificationOrderDTO> orderDTOs = notificationOrderProcessService.findNotificationOrder(notificationOrder);
		List<NotificationItem> notificationItems = new ArrayList<NotificationItem>();
		ShipOrderService shipOrderService = (ShipOrderService) RemoteServiceSingleton.getInstance().getAppService("shipOrderService");
		for (NotificationOrderDTO notificationOrderDTO : orderDTOs) {
			List<NotificationItem> items = notificationOrderDTO.getNotificationItemList();
			for (NotificationItem item : items) {
				Long qty = shipOrderService.getQtyReceived(item.getSkuId(), Long.parseLong(request.getParameter("notificationOrder") == null ? "0" : request.getParameter("notificationOrder")));
				// item.setQtyReceived(qty==null?0:qty.intValue());
				item.setPid(qty);
			}
			notificationItems.addAll(items);
		}
		PageBean<NotificationItem> pageBean = new PageBean<NotificationItem>();
		pageBean.setResult(notificationItems);
		if (page != null) {
			pageBean.setPage(page);
		} else {
			pageBean.setPage(Constants.INT1);
		}
		// List<Dictionarys> dictionarys=new ArrayList<Dictionarys>();
		List<Dictionarys> dictionarys = RemoteServiceSingleton.getInstance().getInfrastructureService().transportWay();
		String shipName = "";
		for (Dictionarys dictionary : dictionarys) {
			if (dictionary.getDictValue().equals(String.valueOf("00" + orderDTOs.get(0).getNotificationOrder().getShipperType()))) {
				model.addAttribute("dictionary", dictionary);
				break;
			}
			// model.addAttribute("dictionary", dictionary);
			// break;
		}
		pageBean.setPageSize(Constants.PAGESIZE);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("orderDTO", orderDTOs.get(0));
		model.addAttribute("shipName", shipName);
		model.addAttribute("key", PKUtils.getLongPrimaryKey());
		model.addAttribute("keyChar", key);
		return "/shiporder/saveShipOrder";
	}

	@RequestMapping(value = "/updateShipOrder")
	public String updateShipOrder(HttpServletRequest request, Long id, Long pid, Model model) {
		NotificationOrderProcessService notificationOrderProcessService = (NotificationOrderProcessService) RemoteServiceSingleton.getInstance().getAppService("notificationOrderProcessService");
		NotificationOrder notificationOrder = new NotificationOrder();
		notificationOrder.setId(pid);
		List<NotificationOrderDTO> orderDTOs = notificationOrderProcessService.findNotificationOrder(notificationOrder);
		List<NotificationItem> notificationItems = orderDTOs.get(0).getNotificationItemList();
		ShipOrderService shipOrderService = (ShipOrderService) RemoteServiceSingleton.getInstance().getAppService("shipOrderService");
		ShipOrderDto orderDto = shipOrderService.forUpdateShipOrder(id);
		List<ShipOrderItemDto> shipOrderItemDtos = orderDto.getItemDtos();
		for (NotificationItem item : notificationItems) {
			for (ShipOrderItemDto shipOrderItemDto : shipOrderItemDtos) {
				if (item.getSkuId().equals(new Long(shipOrderItemDto.getSkuId()))) {
					int qty = item.getQty() - shipOrderService.getQtyReceived(shipOrderItemDto.getSkuId(), pid).intValue();
					shipOrderItemDto.setPid(item.getQty().longValue());
					shipOrderItemDto.setSkuNameEn(String.valueOf(qty));
				}
			}
		}
		List<Dictionarys> dictionarys = RemoteServiceSingleton.getInstance().getInfrastructureService().transportWay();
		// String shipName = "";
		for (Dictionarys dictionary : dictionarys) {
			if (dictionary.getDictValue().equals(String.valueOf("00" + orderDTOs.get(0).getNotificationOrder().getShipperType()))) {
				model.addAttribute("dictionary", dictionary);
				break;
			}
			// model.addAttribute("dictionary", dictionary);
			// break;
		}
		model.addAttribute("orderDto", orderDto);
		return "/shiporder/updateShipOrder";
	}

	@RequestMapping(value = "/doUpdateShipOrder")
	public String doUpdateShipOrder(HttpServletRequest request, Model model) {
		try {
			ShipOrderService shipOrderService = (ShipOrderService) RemoteServiceSingleton.getInstance().getAppService("shipOrderService");
			// ShipOrderItemService shipOrderItemService =
			// (ShipOrderItemService)
			// RemoteServiceSingleton.getInstance().getAppService("shipOrderItemService");
			// String[] chk = request.getParameterValues("tabdetil");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String id = request.getParameter("id");
			String keyChar = request.getParameter("keyChar");
			String noChar = request.getParameter("noChar");
			String bussChar = request.getParameter("bussChar");
			String businessNumber = request.getParameter("businessNumber");
			String notificationId = request.getParameter("notificationId");
			String arriveDate = request.getParameter("arriveDate");
			String serviceName = request.getParameter("serviceName");
			String serviceCode = request.getParameter("serviceCode");
			String shipperType = request.getParameter("shipperCode");
			String receiveAddress = request.getParameter("receiveAddress");
			String receiveName = request.getParameter("receiveName");
			String receivePhone = request.getParameter("receivePhone");
			String[] tabdetil = request.getParameter("checkBox").split(",");

			String[] skuids = request.getParameterValues("skuId");
			String[] pcode = request.getParameterValues("pcode");
			String[] barCode = request.getParameterValues("barCode");
			String[] skuNameCn = request.getParameterValues("skuNameCn");
			String[] format = request.getParameterValues("format");
			String[] unit = request.getParameterValues("unit");
			String[] qty = request.getParameterValues("tallQty");
			String[] qtyReceived = request.getParameterValues("qtyReceived");
			String[] price = request.getParameterValues("price");
			ShipOrderDto shipOrderDto = new ShipOrderDto();
			shipOrderDto.setId(Long.parseLong(keyChar));
			shipOrderDto.setBusinessNumber(Long.parseLong(businessNumber));
			shipOrderDto.setNotificationId(Long.parseLong(notificationId));
			shipOrderDto.setBusinessNumberChar(bussChar);
			shipOrderDto.setNotificationIdChar(noChar);
			shipOrderDto.setShipChar(id);
			if (arriveDate != null && !"".equals(arriveDate)) {
				shipOrderDto.setArriveDate(sdf.parse(arriveDate));
			}
			shipOrderDto.setServiceName(serviceName);
			shipOrderDto.setStatus(1);
			if (serviceCode != null && !"".equals(serviceCode)) {
				shipOrderDto.setServiceCode(Long.parseLong(serviceCode));
			}
			shipOrderDto.setShipperType(shipperType);
			shipOrderDto.setReceiveAddress(receiveAddress);
			shipOrderDto.setReceiveName(receiveName);
			shipOrderDto.setReceivePhone(receivePhone);
			shipOrderDto.setTotalQty(CountQty(qtyReceived));
			shipOrderDto.setTotalPrice(CountPrice(price, qtyReceived));
			shipOrderDto.setCreateTime(new Date());
			shipOrderDto.setCreateBy(this.getCurrentUser().getUsername());
			List<ShipOrderItemDto> itemDtos = new ArrayList<ShipOrderItemDto>();
			double totalPrice = 0;
			int totalQty = 0;
			for (int i = 0; i < tabdetil.length; i++) {
				if (!qtyReceived[i].equals("0") && tabdetil[i].equals("1")) {
					ShipOrderItemDto shipOrderItemDto = new ShipOrderItemDto();
					shipOrderItemDto.setId(PKUtils.getLongPrimaryKey());
					shipOrderItemDto.setPurchaseId(Long.parseLong(keyChar));
					shipOrderItemDto.setShipId(Long.parseLong(keyChar));
					shipOrderItemDto.setSkuId(Long.parseLong(skuids[i]));
					shipOrderItemDto.setPid(Long.parseLong(skuids[i]));
					shipOrderItemDto.setBarCode(barCode[i]);
					shipOrderItemDto.setPcode(pcode[i]);
					shipOrderItemDto.setSkuNameCn(skuNameCn[i]);
					shipOrderItemDto.setFormat(format[i]);
					shipOrderItemDto.setUnit(unit[i]);
					shipOrderItemDto.setQty(Integer.parseInt(qty[i]));
					shipOrderItemDto.setQtyReceived(Integer.parseInt(qtyReceived[i]));
					totalPrice += Long.parseLong(qtyReceived[i]) * Double.parseDouble(price[i]);
					totalQty += Integer.parseInt(qtyReceived[i]);
					shipOrderItemDto.setPrice(new BigDecimal(price[i]));
					itemDtos.add(shipOrderItemDto);
				}
			}
			shipOrderDto.setTotalPrice(new BigDecimal(totalPrice));
			shipOrderDto.setTotalQty(totalQty);
			shipOrderDto.setItemDtos(itemDtos);
			if (itemDtos.size() > 0) {
				shipOrderService.updateShipOrder(shipOrderDto);
			}
			System.out.println(arriveDate);
			// System.out.println(chk.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	@RequestMapping(value = "/findServiceList")
	public String findServiceList(String serviceName, Model model) {
		System.out.println(serviceName);
		InfrastructureService infrastructureService = RemoteServiceSingleton.getInstance().getInfrastructureService();
		List<ServiceArchives> serviceArchives = infrastructureService.findServiceArchivesByName(serviceName == null ? "" : serviceName);
		model.addAttribute("serviceList", serviceArchives);
		return "/shiporder/service";
	}

	// @RequestMapping(value = "/findServiceList")
	// public String findServiceList(String serviceName,Model model){
	// System.out.println(serviceName);
	// InfrastructureService infrastructureService =
	// RemoteServiceSingleton.getInstance().getInfrastructureService();
	// List<ServiceArchives>
	// serviceArchives=infrastructureService.findServiceArchivesByName(serviceName==null?"":serviceName);
	// model.addAttribute("serviceList", serviceArchives);
	// return "/shiporder/service";
	// }
	@RequestMapping(value = "/PChaseOrderList")
	public String PChaseOrderList(Long businessNumber, Model model) {
		System.out.println(businessNumber);
		PChaseOrderServiceRPC pChaseOrderServiceRPC = (PChaseOrderServiceRPC) RemoteServiceSingleton.getInstance().getAppService("pChaseOrderServiceRPC");
		List<PChaseOrderVO> ChaseOrder = new ArrayList<PChaseOrderVO>();
		if (businessNumber != null) {
			ChaseOrder = pChaseOrderServiceRPC.selectOrderById(businessNumber.toString());
		} else {
			ChaseOrder = pChaseOrderServiceRPC.selectOrderById("");
		}
		model.addAttribute("ChaseOrder", ChaseOrder);
		return "/shiporder/chaseordernew";
	}

	@RequestMapping(value = "/NotificList")
	public String NotificList(Long NotificId, Model model) {
		System.out.println(NotificId);

		NotificationOrderProcessService notificationOrderProcessService = (NotificationOrderProcessService) RemoteServiceSingleton.getInstance().getAppService("notificationOrderProcessService");
		NotificationOrder notificationOrder = new NotificationOrder();
		notificationOrder.setId(NotificId);
		List<NotificationOrder> ChaseOrder = notificationOrderProcessService.findNotificationOrderByLikeId(NotificId);
		model.addAttribute("test", ChaseOrder);
		return "/shiporder/notific";
	}

	@RequestMapping(value = "/doSaveShipOrder", method = { RequestMethod.GET, RequestMethod.POST })
	public String doSaveShipOrder(HttpServletRequest request, Model model) {
		try {
			ShipOrderService shipOrderService = (ShipOrderService) RemoteServiceSingleton.getInstance().getAppService("shipOrderService");
			// ShipOrderItemService shipOrderItemService =
			// (ShipOrderItemService)
			// RemoteServiceSingleton.getInstance().getAppService("shipOrderItemService");
			// String[] chk = request.getParameterValues("tabdetil");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String id = request.getParameter("id");
			String keyChar = request.getParameter("keyChar");
			String noChar = request.getParameter("noChar");
			String bussChar = request.getParameter("bussChar");
			String businessNumber = request.getParameter("businessNumber");
			String notificationId = request.getParameter("notificationId");
			String arriveDate = request.getParameter("arriveDate");
			String serviceName = request.getParameter("serviceName");
			String serviceCode = request.getParameter("serviceCode");
			String shipperType = request.getParameter("shipperCode");
			String receiveAddress = request.getParameter("receiveAddress");
			String receiveName = request.getParameter("receiveName");
			String receivePhone = request.getParameter("receivePhone");
			String[] tabdetil = request.getParameter("checkBox").split(",");
			String[] skuids = request.getParameterValues("skuId");
			String[] pcode = request.getParameterValues("pcode");
			String[] barCode = request.getParameterValues("barCode");
			String[] skuNameCn = request.getParameterValues("skuNameCn");
			String[] format = request.getParameterValues("format");
			String[] unit = request.getParameterValues("unit");
			String[] qty = request.getParameterValues("tallQty");
			String[] qtyReceived = request.getParameterValues("qtyReceived");
			String[] price = request.getParameterValues("price");
			ShipOrderDto shipOrderDto = new ShipOrderDto();
			shipOrderDto.setId(Long.parseLong(id));
			shipOrderDto.setBusinessNumber(Long.parseLong(businessNumber));
			shipOrderDto.setNotificationId(Long.parseLong(notificationId));
			shipOrderDto.setBusinessNumberChar(bussChar);
			shipOrderDto.setNotificationIdChar(noChar);
			shipOrderDto.setShipChar(keyChar);
			if (arriveDate != null && !"".equals(arriveDate)) {
				shipOrderDto.setArriveDate(sdf.parse(arriveDate));
			}
			shipOrderDto.setServiceName(serviceName);
			shipOrderDto.setStatus(1);
			shipOrderDto.setBusinessType("101");
			if (serviceCode != null && !"".equals(serviceCode)) {
				shipOrderDto.setServiceCode(Long.parseLong(serviceCode));
			}
			shipOrderDto.setShipperType(shipperType);
			shipOrderDto.setReceiveAddress(receiveAddress);
			shipOrderDto.setReceiveName(receiveName);
			shipOrderDto.setReceivePhone(receivePhone);
			shipOrderDto.setTotalQty(CountQty(qtyReceived));
			shipOrderDto.setTotalPrice(CountPrice(price, qtyReceived));
			shipOrderDto.setCreateTime(new Date());
			shipOrderDto.setCreateBy(this.getCurrentUser().getUsername());
			List<ShipOrderItemDto> itemDtos = new ArrayList<ShipOrderItemDto>();
			double totalPrice = 0;
			int totalQty = 0;
			for (int i = 0; i < tabdetil.length; i++) {
				if (!qtyReceived[i].equals("0") && tabdetil[i].equals("1")) {

					ShipOrderItemDto shipOrderItemDto = new ShipOrderItemDto();
					shipOrderItemDto.setId(PKUtils.getLongPrimaryKey());
					shipOrderItemDto.setPurchaseId(Long.parseLong(id));
					shipOrderItemDto.setShipId(Long.parseLong(id));
					shipOrderItemDto.setSkuId(Long.parseLong(skuids[i]));
					shipOrderItemDto.setPid(Long.parseLong(skuids[i]));
					shipOrderItemDto.setBarCode(barCode[i]);
					shipOrderItemDto.setPcode(pcode[i]);
					shipOrderItemDto.setSkuNameCn(skuNameCn[i]);
					shipOrderItemDto.setFormat(format[i]);
					shipOrderItemDto.setUnit(unit[i]);
					shipOrderItemDto.setQty(Integer.parseInt(qty[i]));

					shipOrderItemDto.setQtyReceived(Integer.parseInt(qtyReceived[i]));
					totalPrice += Long.parseLong(qtyReceived[i]) * Double.parseDouble(price[i]);
					totalQty += Integer.parseInt(qtyReceived[i]);
					shipOrderItemDto.setPrice(new BigDecimal(price[i]));
					itemDtos.add(shipOrderItemDto);
				}
			}
			shipOrderDto.setTotalPrice(new BigDecimal(totalPrice));
			shipOrderDto.setTotalQty(totalQty);
			shipOrderDto.setItemDtos(itemDtos);
			if (itemDtos.size() > 0) {
				shipOrderService.insertShipOrder(shipOrderDto);
			}
			// System.out.println(arriveDate);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "success";
	}

	@RequestMapping(value = "/checkShipOrder")
	public String checkShipOrder(HttpServletRequest request, Model model) {
		try {
			ShipOrderService shipOrderService = (ShipOrderService) RemoteServiceSingleton.getInstance().getAppService("shipOrderService");
			// ShipOrderItemService shipOrderItemService =
			// (ShipOrderItemService)
			// RemoteServiceSingleton.getInstance().getAppService("shipOrderItemService");
			// String[] chk = request.getParameterValues("tabdetil");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String id = request.getParameter("id");
			String keyChar = request.getParameter("keyChar");
			String noChar = request.getParameter("noChar");
			String bussChar = request.getParameter("bussChar");
			String businessNumber = request.getParameter("businessNumber");
			String notificationId = request.getParameter("notificationId");
			String arriveDate = request.getParameter("arriveDate");
			String serviceName = request.getParameter("serviceName");
			String serviceCode = request.getParameter("serviceCode");
			String shipperType = request.getParameter("shipperCode");
			String receiveAddress = request.getParameter("receiveAddress");
			String receiveName = request.getParameter("receiveName");
			String receivePhone = request.getParameter("receivePhone");
			String[] tabdetil = request.getParameter("checkBox").split(",");

			String[] skuids = request.getParameterValues("skuId");
			String[] pcode = request.getParameterValues("pcode");
			String[] barCode = request.getParameterValues("barCode");
			String[] skuNameCn = request.getParameterValues("skuNameCn");
			String[] format = request.getParameterValues("format");
			String[] unit = request.getParameterValues("unit");
			String[] qty = request.getParameterValues("tallQty");
			String[] qtyReceived = request.getParameterValues("qtyReceived");
			String[] price = request.getParameterValues("price");
			ShipOrderDto shipOrderDto = new ShipOrderDto();
			shipOrderDto.setId(Long.parseLong(keyChar));
			shipOrderDto.setBusinessNumber(Long.parseLong(businessNumber));
			shipOrderDto.setNotificationId(Long.parseLong(notificationId));
			shipOrderDto.setBusinessNumberChar(bussChar);
			shipOrderDto.setNotificationIdChar(noChar);
			shipOrderDto.setShipChar(id);
			if (arriveDate != null && !"".equals(arriveDate)) {
				shipOrderDto.setArriveDate(sdf.parse(arriveDate));
			}
			shipOrderDto.setServiceName(serviceName);
			shipOrderDto.setStatus(10);
			if (serviceCode != null && !"".equals(serviceCode)) {
				shipOrderDto.setServiceCode(Long.parseLong(serviceCode));
			}
			shipOrderDto.setShipperType(shipperType);
			shipOrderDto.setReceiveAddress(receiveAddress);
			shipOrderDto.setReceiveName(receiveName);
			shipOrderDto.setReceivePhone(receivePhone);
			shipOrderDto.setTotalQty(CountQty(qtyReceived));
			shipOrderDto.setTotalPrice(CountPrice(price, qtyReceived));
			shipOrderDto.setCreateTime(new Date());
			shipOrderDto.setCreateBy(this.getCurrentUser().getUsername());
			List<ShipOrderItemDto> itemDtos = new ArrayList<ShipOrderItemDto>();
			double totalPrice = 0;
			int totalQty = 0;
			for (int i = 0; i < tabdetil.length; i++) {
				if (!qtyReceived[i].equals("0") && tabdetil[i].equals("1")) {
					ShipOrderItemDto shipOrderItemDto = new ShipOrderItemDto();
					shipOrderItemDto.setId(PKUtils.getLongPrimaryKey());
					shipOrderItemDto.setPurchaseId(Long.parseLong(keyChar));
					shipOrderItemDto.setShipId(Long.parseLong(keyChar));
					shipOrderItemDto.setSkuId(Long.parseLong(skuids[i]));
					shipOrderItemDto.setPid(Long.parseLong(skuids[i]));
					shipOrderItemDto.setBarCode(barCode[i]);
					shipOrderItemDto.setPcode(pcode[i]);
					shipOrderItemDto.setSkuNameCn(skuNameCn[i]);
					shipOrderItemDto.setFormat(format[i]);
					shipOrderItemDto.setUnit(unit[i]);
					shipOrderItemDto.setQty(Integer.parseInt(qty[i]));
					shipOrderItemDto.setQtyReceived(Integer.parseInt(qtyReceived[i]));
					totalPrice += Long.parseLong(qtyReceived[i]) * Double.parseDouble(price[i]);
					totalQty += Integer.parseInt(qtyReceived[i]);
					shipOrderItemDto.setPrice(new BigDecimal(price[i]));
					itemDtos.add(shipOrderItemDto);
				}
			}
			shipOrderDto.setTotalPrice(new BigDecimal(totalPrice));
			shipOrderDto.setTotalQty(totalQty);
			shipOrderDto.setItemDtos(itemDtos);
			if (itemDtos.size() > 0) {
				shipOrderService.updateShipOrder(shipOrderDto);
			}
			System.out.println(arriveDate);
			// System.out.println(chk.length);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "success";
	}

	public static Integer CountQty(String[] chk) {
		int qty = 0;
		for (int i = 0; i < chk.length; i++) {
			qty += Long.parseLong(chk[i]);
		}

		return qty;
	}

	public static BigDecimal CountPrice(String[] price, String[] qty) {
		double total = 0;
		for (int i = 0; i < price.length; i++) {
			total += Double.parseDouble(price[i]) * Long.parseLong(qty[i]);
		}

		return new BigDecimal(total);
	}

	@RequestMapping(value = "/checkShip")
	@ResponseBody
	public String checkShip(HttpServletRequest request, Model model) {
		String businessNumber = request.getParameter("businessNumber") == null ? "" : request.getParameter("businessNumber");
		String notificationOrder = request.getParameter("notificationOrder") == null ? "" : request.getParameter("notificationOrder");
		ShipOrderService shipOrderService = (ShipOrderService) RemoteServiceSingleton.getInstance().getAppService("shipOrderService");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("business_number", businessNumber);
		map.put("notification_id", notificationOrder);
		Long qty = shipOrderService.checkShip(map);
		if (qty > 0) {
			return "OK";
		} else {
			return "ERROR";
		}

	}

	@RequestMapping(value = "/checkBussStatus")
	@ResponseBody
	public String checkBussStatus(HttpServletRequest request, Model model) {
		String businessNumber = request.getParameter("businessNumber") == null ? "" : request.getParameter("businessNumber");
		// String notificationOrder = request.getParameter("notificationOrder")
		// == null ? "" : request.getParameter("notificationOrder");
		PChaseOrderServiceRPC pChaseOrderServiceRPC = (PChaseOrderServiceRPC) RemoteServiceSingleton.getInstance().getAppService("pChaseOrderServiceRPC");
		PChaseOrderVO pChaseOrderVO = pChaseOrderServiceRPC.findOrderDetailById(Long.parseLong(businessNumber));
		if (pChaseOrderVO.getOrderStatus().equals((short) 20)) {
			return "ERROR";
		} else {
			return "OK";
		}
	}

	*//**
	 * @Description： 查看物流通知单明细
	 * @author: QIJJ
	 * @since: 2015-6-24 下午5:19:12
	 *//*
	@RequestMapping(value = "/selectShipOrder")
	public String selectShipOrder(HttpServletRequest request, Long id, Long pid, Model model) {
		PChaseOrderServiceRPC pChaseOrderServiceRPC = (PChaseOrderServiceRPC) RemoteServiceSingleton.getInstance().getAppService("pChaseOrderServiceRPC");
		// 通过采购单号查询采购单详情
		PChaseOrderVO pChaseOrderVO = pChaseOrderServiceRPC.findOrderDetailById(pid);
		List<PChaseOrderItemDTO> pChaseOrderItemDTOs = pChaseOrderVO.getpCOItemList();

		ShipOrderService shipOrderService = (ShipOrderService) RemoteServiceSingleton.getInstance().getAppService("shipOrderService");
		// 通过物流单号查询其详情
		ShipOrderDto orderDto = shipOrderService.forUpdateShipOrder(id);
		List<ShipOrderItemDto> shipOrderItemDtos = orderDto.getItemDtos();

		// TODO 待修改
		for (PChaseOrderItemDTO orderItemDTO : pChaseOrderItemDTOs) {
			for (ShipOrderItemDto shipOrderItemDto : shipOrderItemDtos) {
				if (orderItemDTO.getSkuId().equals(new Long(shipOrderItemDto.getSkuId()))) {
					shipOrderItemDto.setPid(orderItemDTO.getQty().longValue());
					// shipOrderItemDto.setQty(orderItemDTO.getQty());
				}
			}
		}
		List<Dictionarys> dictionarys = RemoteServiceSingleton.getInstance().getInfrastructureService().transportWay();
		// String shipName = "";
		for (Dictionarys dictionary : dictionarys) {
			if (dictionary.getDictValue().equals("00" + orderDto.getShipperType())) {
				model.addAttribute("dictionary", dictionary);
				break;
			}
			// model.addAttribute("dictionary", dictionary);
			// break;
		}
		model.addAttribute("orderDto", orderDto);

		return "/shiporder/selectShipOrder";
	}

	@RequestMapping(value = "/checkStatus")
	@ResponseBody
	public String checkStatus(HttpServletRequest request, Model model) {
		String businessNumber = request.getParameter("businessNumber") == null ? "" : request.getParameter("businessNumber");
		String notificationOrder = request.getParameter("notificationOrder") == null ? "" : request.getParameter("notificationOrder");
		ShipOrderService shipOrderService = (ShipOrderService) RemoteServiceSingleton.getInstance().getAppService("shipOrderService");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("business_number", businessNumber);
		map.put("notification_id", notificationOrder);
		Long qty = shipOrderService.checkStatus(map);
		if (qty > 0) {
			return "ERROR";
		} else {
			return "OK";
		}
	}

	@RequestMapping(value = "/checkBusiness")
	@ResponseBody
	public String checkBusiness(HttpServletRequest request, Model model) {
		String businessNumber = request.getParameter("businessNumber") == null ? "" : request.getParameter("businessNumber");
		// String notificationOrder = request.getParameter("notificationOrder")
		// == null ? "" : request.getParameter("notificationOrder");
		PChaseOrderServiceRPC pChaseOrderServiceRPC = (PChaseOrderServiceRPC) RemoteServiceSingleton.getInstance().getAppService("pChaseOrderServiceRPC");
		PChaseOrderVO pChaseOrderVO = pChaseOrderServiceRPC.findOrderDetailById(Long.parseLong(businessNumber));

		if (pChaseOrderVO.getShipper().equals(new Short("1"))) {
			return "ERROR";
		} else {
			return "OK";
		}
	}

	*//******************************************************** 新增功能页面 ****************************************************************************//*
	*//**
	 * @Description： 点击物流查询，跳转页面
	 * @author: QIJJ
	 * @since: 2015-6-24 下午5:04:36
	 *//*
	@RequestMapping(value = "/selectShipOrderInfo")
	public String selectShipOrder(HttpServletRequest request, HttpServletResponse response, Model model) {

		return "/shiporder/selectShipOrderPage";
	}

	*//**
	 * @Description： 通过条件查询物流单信息
	 * @author: QIJJ
	 * @since: 2015-6-24 下午5:09:37
	 *//*
	@RequestMapping(value = "/selectShipOrderAllInfo")
	public String selectShipOrderAllInfo(HttpServletRequest request, HttpServletResponse response, Model model, Integer page, Long businessNumber, String businessType) {
		try {
			PageBean<ShipOrder> pageBean = new PageBean<ShipOrder>();
			ShipOrder shipOrder = new ShipOrder();
			shipOrder.setBusinessNumber(businessNumber);
			shipOrder.setBusinessType(businessType);

			if (page != null) {
				pageBean.setPage(page);
			} else {
				pageBean.setPage(1);
			}
			pageBean.setPageSize(Constants.INT10);
			pageBean.setParameter(shipOrder);// 设置分页

			ShipOrderService shipOrderService = (ShipOrderService) RemoteServiceSingleton.getInstance().getAppService("shipOrderService");
			pageBean = shipOrderService.selectByDynamicListPage(pageBean);

			model.addAttribute("pb", pageBean);
		} catch (Exception e) {
			LOG.error("进入物流查询页面." + e.getMessage(), e);
			e.printStackTrace();
		}

		return "/shiporder/selectShipOrderTable";
	}
}
*/