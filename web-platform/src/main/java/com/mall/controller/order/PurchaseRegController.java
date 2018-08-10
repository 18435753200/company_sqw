/*package com.mall.controller.order;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mall.mybatis.utility.PageBean;
import com.mall.retailer.order.po.Dictionarys;
import com.mall.supplier.order.api.rpc.InfrastructureService;
import com.mall.supplier.order.api.rpc.NotificationOrderProcessService;
import com.mall.supplier.order.api.rpc.PChaseOrderServiceRPC;
import com.mall.supplier.order.api.rpc.PurchaseCostService;
import com.mall.supplier.order.api.rpc.PurchaseRegService;
import com.mall.supplier.order.api.rpc.ShipOrderService;
import com.mall.supplier.order.api.rpc.StockInOrderService;
import com.mall.supplier.order.dto.PChaseOrderItemDTO;
import com.mall.supplier.order.dto.PChaseOrderVO;
import com.mall.supplier.order.dto.PurchaseCostDto;
import com.mall.supplier.order.dto.PurchaseRegDto;
import com.mall.supplier.order.dto.ShipOrderDto;
import com.mall.supplier.order.dto.StockInOrderDto;
import com.mall.supplier.order.dto.StockInOrderListDto;
import com.mall.supplier.order.po.Cost;
import com.mall.supplier.order.po.Currency;
import com.mall.supplier.order.po.NotificationOrder;
import com.mall.supplier.order.po.ServiceArchives;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;

@Controller
@RequestMapping(value="/purchasereg")
public class PurchaseRegController extends BaseController{
	
	@RequestMapping(value = "/findPurchaseReg")
	public String findOutStock(Model model){
		InfrastructureService infrastructureService=RemoteServiceSingleton.getInstance().getInfrastructureService();
		//List<Currency> currencies=infrastructureService.findCurrency();
		List<Currency> currencies=new ArrayList<Currency>();
		Currency currency=new Currency();
		currency.setCurrencyType("人民币");
		currency.setExchangeRate(new BigDecimal(1));
		currency.setCode("RMB");
		currencies.add(currency);
		List<Dictionarys> dictionarys=infrastructureService.goodsCostAllocation();
		model.addAttribute("Currency", currencies);
		model.addAttribute("Dictionarys", dictionarys);
		return "/purchasereg/findPurchaseReg";	
	}
	@RequestMapping(value = "/setReg")
	public String setReg(Model model,HttpServletRequest request){
		InfrastructureService infrastructureService=RemoteServiceSingleton.getInstance().getInfrastructureService();
		List<Cost> costs=infrastructureService.findCost();
		//List<Currency> currencies=infrastructureService.findCurrency();
		List<Currency> currencies=new ArrayList<Currency>();
		Currency currency=new Currency();
		currency.setCurrencyType("人民币");
		currency.setExchangeRate(new BigDecimal(1));
		currency.setCode("RMB");
		currencies.add(currency);
		List<Dictionarys> dictionarys=infrastructureService.goodsCostAllocation();
		String id=request.getParameter("id").trim();
		String notificationId=request.getParameter("notificationId").trim();
		String shipId=request.getParameter("shipId").trim();
		String businessNumber=request.getParameter("businessNumber").trim();
		String qty=request.getParameter("qty");
		String price=request.getParameter("price");
		model.addAttribute("qty", qty);
		model.addAttribute("price", price);
		model.addAttribute("id", id);
		model.addAttribute("notificationId", notificationId);
		model.addAttribute("shipId", shipId);
		model.addAttribute("businessNumber", businessNumber);
		model.addAttribute("Cost", costs);
		model.addAttribute("Currency", currencies);
		model.addAttribute("Dictionarys", dictionarys);
		return "/purchasereg/setReg";	
	}
	
	@RequestMapping(value = "/editReg")
	public String editReg(Model model,HttpServletRequest request){
		PurchaseRegService purchaseRegService=(PurchaseRegService)RemoteServiceSingleton.getInstance().getAppService("purchaseRegService");
		InfrastructureService infrastructureService=RemoteServiceSingleton.getInstance().getInfrastructureService();
		List<Cost> costs=infrastructureService.findCost();
		//List<Currency> currencies=infrastructureService.findCurrency();
		List<Currency> currencies=new ArrayList<Currency>();
		Currency currency=new Currency();
		currency.setCurrencyType("人民币");
		currency.setExchangeRate(new BigDecimal(1));
		currency.setCode("RMB");
		currencies.add(currency);
		List<Dictionarys> dictionarys=infrastructureService.goodsCostAllocation();
		String id=request.getParameter("id").trim();
		String notificationId=request.getParameter("notificationId").trim();
		String shipId=request.getParameter("shipId").trim();
		String businessNumber=request.getParameter("businessNumber").trim();
		String qty=request.getParameter("qty");
		String price=request.getParameter("price");
		PageBean<PurchaseRegDto> pageBean=new PageBean<PurchaseRegDto>();
		PurchaseRegDto regDto=new PurchaseRegDto();
		regDto.setBusinessNumber(Long.parseLong(businessNumber));
		pageBean.setPage(1);
		pageBean.setPageSize(100);
		pageBean.setParameter(regDto);
		pageBean=purchaseRegService.findPurchaseRegDto(pageBean);
		model.addAttribute("qty", qty);
		model.addAttribute("price", price);
		model.addAttribute("id", id);
		model.addAttribute("notificationId", notificationId);
		model.addAttribute("shipId", shipId);
		model.addAttribute("businessNumber", businessNumber);
		model.addAttribute("Cost", costs);
		model.addAttribute("Currency", currencies);
		model.addAttribute("Dictionarys", dictionarys);
		model.addAttribute("pb", pageBean);
		return "/purchasereg/editReg";	
	}
	@RequestMapping(value = "/findPurchaseRegList")
	public String findPurchaseCostList(HttpServletRequest request,Integer page,Model model){
		String shipId=request.getParameter("shipId");
		String notificationId=request.getParameter("notificationId");
		String serviceName=request.getParameter("serviceName");
		String businessNumber=request.getParameter("businessNumber");
		StockInOrderListDto arg0=new StockInOrderListDto();
		if(shipId!=null&&!"".equals(shipId))
		{
			arg0.setShipId(Long.parseLong(shipId));
		}
		if(notificationId!=null&&!"".equals(notificationId))
		{
			arg0.setNotificationId(Long.parseLong(notificationId));
		}
		if(serviceName!=null&&!"".equals(serviceName))
		{
			arg0.setServiceName(serviceName);
		}
		if(businessNumber!=null&&!"".equals(businessNumber))
		{
			arg0.setBusinessNumber(Long.parseLong(businessNumber));
		}
		StockInOrderService stockInOrderService = (StockInOrderService)RemoteServiceSingleton.getInstance().getAppService("stockInOrderService");
		PageBean<StockInOrderListDto> pageBean=new PageBean<StockInOrderListDto>();
		pageBean.setParameter(arg0);
		if(page!=null)
		{
			pageBean.setPage(page);
		}else{
			pageBean.setPage(Constants.INT1);
		}
		pageBean.setPageSize(Constants.PAGESIZE);
		pageBean=stockInOrderService.selectStockInOrderListPage(pageBean);
		model.addAttribute("pb", pageBean);
		return "/purchasereg/findPurchaseRegList";	
	}
	public String getCostName(List<Cost> costs,String code)
	{
		String result="";
		for(Cost cost:costs)
		{
			if(cost.getCode().equals(code))
			{
				result=cost.getCostName();
				break;
			}
		}
		return result;
	}
	public String getCurrencyName(List<Currency> costs,String code)
	{
		String result="";
		for(Currency cost:costs)
		{
			if(cost.getCode().equals(code))
			{
				result=cost.getCurrencyType();
				break;
			}
		}
		return result;
	}
	public String getDictionarysName(List<Dictionarys> costs,String code)
	{
		String result="";
		for(Dictionarys cost:costs)
		{
			if(cost.getDictValue().equals(code))
			{
				result=cost.getDictName();
				break;
			}
		}
		return result;
	}
	@RequestMapping(value = "/savePurchaseRegDetil")
	@ResponseBody
	public String savePurchaseRegDetil(HttpServletRequest request,Integer page,Model model){
		PurchaseRegService purchaseRegService=(PurchaseRegService)RemoteServiceSingleton.getInstance().getAppService("purchaseRegService");
		java.text.SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		String id=request.getParameter("id").trim();//入库单
		String notificationId=request.getParameter("notificationId").trim();
		String shipId=request.getParameter("shipId").trim();
		String businessNumber=request.getParameter("businessNumber").trim();
		String [] createDate=request.getParameterValues("createDate");
		String [] freightCode=request.getParameterValues("freightCode");
		String [] receiveAddress=request.getParameterValues("receiveAddress");
		String [] freightPrice=request.getParameterValues("freightPrice");
		String [] allocationCode=request.getParameterValues("allocationCode");
		String [] allocationQty=request.getParameterValues("allocationQty");
		String [] allocationPrice=request.getParameterValues("allocationPrice");
		String [] currencyCode=request.getParameterValues("currencyCode");
		String [] exchangeRate=request.getParameterValues("exchangeRate");
		String [] localPrice=request.getParameterValues("localPrice");
		String [] localTotalPrice=request.getParameterValues("localTotalPrice");
		String [] imagePath=request.getParameterValues("imagePath");
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("logisticsCode", shipId);
		map.put("notificationId", notificationId);
		map.put("businessNumber", businessNumber);
		map.put("stockNumber", id);
		purchaseRegService.deletePurchaseReg(map);
		PChaseOrderServiceRPC pChaseOrderServiceRPC = (PChaseOrderServiceRPC)RemoteServiceSingleton.getInstance().getAppService("pChaseOrderServiceRPC");
		pChaseOrderServiceRPC.updatePurchaseFeeById(Long.parseLong(businessNumber), 0, 2);
		InfrastructureService infrastructureService=RemoteServiceSingleton.getInstance().getInfrastructureService();
		//List<Currency> currencies=infrastructureService.findCurrency();
		List<Currency> currencies=new ArrayList<Currency>();
		Currency currency=new Currency();
		currency.setCurrencyType("人民币");
		currency.setExchangeRate(new BigDecimal(1));
		currency.setCode("RMB");
		currencies.add(currency);
		List<Dictionarys> dictionarys=infrastructureService.goodsCostAllocation();
		try {
			for(int i=0;i<createDate.length;i++)
			{
				PurchaseRegDto arg0=new PurchaseRegDto();
				
				arg0.setLogisticsCode(Long.parseLong(shipId));
				
				
				arg0.setStockNumber(Long.parseLong(id));
				
				arg0.setNotificationId(Long.parseLong(notificationId));
				
				arg0.setBusinessNumber(Long.parseLong(businessNumber));

				arg0.setStatus(0);
				arg0.setCurrencyName(getCurrencyName(currencies,currencyCode[i]));
				arg0.setAllocationName(getDictionarysName(dictionarys,allocationCode[i]));
				if(createDate[i]!=null&&!"".equals(createDate[i]))
				{
					arg0.setCreateDate(format.parse(createDate[i]));
				}
				if(freightCode[i]!=null&&!"".equals(freightCode[i]))
				{
					arg0.setFreightCode(Long.parseLong(freightCode[i]));
				}
				if(receiveAddress[i]!=null&&!"".equals(receiveAddress[i]))
				{
					arg0.setReceiveAddress(receiveAddress[i]);
				}
				if(freightPrice[i]!=null&&!"".equals(freightPrice[i]))
				{
					arg0.setFreightPrice(new BigDecimal(freightPrice[i]));
				}
				if(allocationCode[i]!=null&&!"".equals(allocationCode[i]))
				{
					arg0.setAllocationCode(allocationCode[i]);
				}
				if(allocationQty[i]!=null&&!"".equals(allocationQty[i]))
				{
					arg0.setAllocationQty(Integer.parseInt(allocationQty[i]));
				}
				if(allocationPrice[i]!=null&&!"".equals(allocationPrice[i]))
				{
					arg0.setAllocationPrice(new BigDecimal(allocationPrice[i]));
				}
				if(currencyCode[i]!=null&&!"".equals(currencyCode[i]))
				{
					arg0.setCurrencyCode(currencyCode[i]);
				}
				if(exchangeRate[i]!=null&&!"".equals(exchangeRate[i]))
				{
					arg0.setExchangeRate(new BigDecimal(exchangeRate[i]));
				}
				if(localPrice[i]!=null&&!"".equals(localPrice[i]))
				{
					arg0.setLocalPrice(new BigDecimal(localPrice[i]));
				}
				if(localTotalPrice[i]!=null&&!"".equals(localTotalPrice[i]))
				{
					arg0.setLocalTotalPrice(new BigDecimal(localTotalPrice[i]));
				}
				if(imagePath[i]!=null&&!"".equals(imagePath[i]))
				{
					arg0.setImagePath(imagePath[i]);
				}
				
				purchaseRegService.insertPurchaseReg(arg0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "OK";
	}
	@RequestMapping(value = "/uploadCost", method = RequestMethod.POST)
	@ResponseBody
	public String uploadCost(HttpServletRequest request, HttpServletResponse response){
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; 
		MultipartFile file=multipartRequest.getFile("Filedata");
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		String ctxPath = request.getSession().getServletContext().getRealPath("/")+this.getCurrentUser().getUsername();
		File tagetfile = new File(ctxPath,file.getOriginalFilename());    
        if (!tagetfile.exists()) {    
        	tagetfile.mkdirs();    
        }
		System.out.println(ctxPath);
		try {
			file.transferTo(tagetfile); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		System.out.println(fileMap.size());
        return tagetfile.getAbsolutePath();
	}
	@RequestMapping(value = "/editPurchaseRegDetil")
	public String editPurchaseRegDetil(HttpServletRequest request,Model model){
		PurchaseRegService purchaseRegService=(PurchaseRegService)RemoteServiceSingleton.getInstance().getAppService("purchaseRegService");
		String id=request.getParameter("id").trim();
		String notificationId=request.getParameter("notificationId").trim();
		String shipId=request.getParameter("shipId").trim();
		String businessNumber=request.getParameter("businessNumber").trim();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("logisticsCode", shipId);
		map.put("notificationId", notificationId);
		map.put("businessNumber", businessNumber);
		map.put("stockNumber", id);
		purchaseRegService.updatePurchaseReg(map);
		PChaseOrderServiceRPC pChaseOrderServiceRPC = (PChaseOrderServiceRPC)RemoteServiceSingleton.getInstance().getAppService("pChaseOrderServiceRPC");
		pChaseOrderServiceRPC.updatePurchaseFeeById(Long.parseLong(businessNumber), 1, 2);
		return "redirect:/purchasereg/findPurchaseReg";
	}
	@RequestMapping(value = "/findShipList")
	public String findShipList(HttpServletRequest request,Model model,Long id){
		ShipOrderService shipOrderService=(ShipOrderService)RemoteServiceSingleton.getInstance().getAppService("shipOrderService");
		List<ShipOrderDto> shipOrderDtos=new ArrayList<ShipOrderDto>();
		if(id!=null)
		{
			shipOrderDtos=shipOrderService.selectByAlertShipID(id);
		}else{
			shipOrderDtos=shipOrderService.selectByAlertShipID(null);
		}
		model.addAttribute("shipOrder", shipOrderDtos);
		return "/shiporder/findShipList";
	}
	@RequestMapping(value = "/NotificList")
	public String NotificList(Long NotificId,Model model){
		System.out.println(NotificId);

		NotificationOrderProcessService notificationOrderProcessService = (NotificationOrderProcessService)RemoteServiceSingleton.getInstance().getAppService("notificationOrderProcessService");
		NotificationOrder notificationOrder =new NotificationOrder();
		notificationOrder.setId(NotificId);
		List<NotificationOrder> ChaseOrder=notificationOrderProcessService.findNotificationOrderByLikeId(NotificId);
		model.addAttribute("test", ChaseOrder);
		return "/shiporder/notific";	
	}
	@RequestMapping(value = "/findServiceList")
	public String findServiceList(String serviceName,Model model){
		System.out.println(serviceName);
		InfrastructureService infrastructureService = RemoteServiceSingleton.getInstance().getInfrastructureService();
		List<ServiceArchives> serviceArchives=infrastructureService.findServiceArchivesByName(serviceName==null?"":serviceName);
		model.addAttribute("serviceList", serviceArchives);
		return "/shiporder/service";	
	}
	@RequestMapping(value = "/PChaseOrderList")
	public String PChaseOrderList(Long businessNumber,Model model){
		System.out.println(businessNumber);
		PChaseOrderServiceRPC pChaseOrderServiceRPC = (PChaseOrderServiceRPC)RemoteServiceSingleton.getInstance().getAppService("pChaseOrderServiceRPC");
		List<PChaseOrderVO> ChaseOrder=new ArrayList<PChaseOrderVO>();
		if(businessNumber!=null)
		{
			ChaseOrder=pChaseOrderServiceRPC.selectOrderById(businessNumber.toString());
		}else{
			ChaseOrder=pChaseOrderServiceRPC.selectOrderById("");
		}
		model.addAttribute("ChaseOrder", ChaseOrder);
		return "/shiporder/chaseorder";	
	}
	public static void main(String[] args) {
		String[] test=new String[]{"test"};
		System.out.println(test[1]);
	}
}
*/