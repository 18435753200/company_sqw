/*package com.mall.controller.order;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.mybatis.utility.PageBean;
import com.mall.retailer.order.common.PKUtils;
import com.mall.retailer.order.po.Dictionarys;
import com.mall.supplier.order.api.rpc.InfrastructureService;
import com.mall.supplier.order.api.rpc.NotificationOrderProcessService;
import com.mall.supplier.order.api.rpc.PChaseOrderServiceRPC;
import com.mall.supplier.order.api.rpc.ShipOrderService;
import com.mall.supplier.order.api.rpc.StockInOrderItemService;
import com.mall.supplier.order.api.rpc.StockInOrderPDFService;
import com.mall.supplier.order.api.rpc.StockInOrderService;
import com.mall.supplier.order.dto.NotificationOrderDTO;
import com.mall.supplier.order.dto.NotificationOrderVo;
import com.mall.supplier.order.dto.PChaseOrderVO;
import com.mall.supplier.order.dto.ShipOrderDto;
import com.mall.supplier.order.dto.StockInOrderDto;
import com.mall.supplier.order.dto.StockInOrderItemDto;
import com.mall.supplier.order.dto.StockInOrderListDto;
import com.mall.supplier.order.dto.StockInOrderPdfDto;
import com.mall.supplier.order.po.NotificationOrder;
import com.mall.supplier.order.po.ServiceArchives;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mall.InterceptorUtils.PdfTools;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;
import com.mall.utils.WritePDF;

@Controller
@RequestMapping(value="/stockorder")
public class StockOrderController {
	
	@RequestMapping(value = "/findStockOrder")
	public String findShipOrder(){
		
		return "/stockorder/findStockOrder";	
	}
	
	@RequestMapping(value = "/findStockOrderItem")
	public String findShipOrderItem(Long id,Integer page,Model model){
		StockInOrderItemService stockInOrderItemService = (StockInOrderItemService)RemoteServiceSingleton.getInstance().getAppService("stockInOrderItemService");
		PageBean<StockInOrderItemDto> pageBean=stockInOrderItemService.selectStockInOrderListPage(id);
		if(page!=null)
		{
			pageBean.setPage(page);
		}else{
			pageBean.setPage(Constants.INT1);
		}
		
		pageBean.setPageSize(Constants.PAGESIZE);
		model.addAttribute("pageBean", pageBean);
		return "/stockorder/findStockOrderItem";	
	}
	
	@RequestMapping(value = "/findServiceList")
	public String findServiceList(String serviceName,Model model){
		System.out.println(serviceName);
		InfrastructureService infrastructureService = RemoteServiceSingleton.getInstance().getInfrastructureService();
		List<ServiceArchives> serviceArchives=infrastructureService.findServiceArchivesByName(serviceName==null?"":serviceName);
		model.addAttribute("serviceList", serviceArchives);
		return "/shiporder/service";	
	}
//	@RequestMapping(value = "/findServiceList")
//	public String findServiceList(String serviceName,Model model){
//		System.out.println(serviceName);
//		InfrastructureService infrastructureService = RemoteServiceSingleton.getInstance().getInfrastructureService();
//		List<ServiceArchives> serviceArchives=infrastructureService.findServiceArchivesByName(serviceName==null?"":serviceName);
//		model.addAttribute("serviceList", serviceArchives);
//		return "/shiporder/service";	
//	}
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
	
	@RequestMapping(value = "/findStockOrderList")
	public String findStockOrderList(HttpServletRequest request,Integer page,Model model){
		NotificationOrderVo orderDto=new NotificationOrderVo();
		String notificationId=request.getParameter("notificationId");
		String businessNumber=request.getParameter("businessNumber");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		String serviceName=request.getParameter("serviceName");
		String receiveAddress=request.getParameter("receiveAddress");
		java.text.SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		try {
			if(notificationId!=null&&!"".equals(notificationId))
			{
				orderDto.setNotificationChar(notificationId);
			}
			if(businessNumber!=null&&!"".equals(businessNumber))
			{
				orderDto.setBusinessNumberChar(businessNumber);
			}
			orderDto.setServiceName(serviceName);
			orderDto.setSendAddress(receiveAddress);
			if(startTime!=null&&!"".equals(startTime))
			{
				orderDto.setBeginCreateTime(format.parse(startTime));
			}
			if(endTime!=null&&!"".equals(endTime))
			{
				orderDto.setEndCreateTime(format.parse(endTime));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		PageBean<NotificationOrder> pageBean=new PageBean<NotificationOrder>();
		if(page!=null)
		{
			pageBean.setPage(page);
		}else{
			pageBean.setPage(Constants.INT1);
		}
		pageBean.setPageSize(Constants.PAGESIZE);
		pageBean.setParameter(orderDto);
		NotificationOrderProcessService notificationOrderProcessService = (NotificationOrderProcessService)RemoteServiceSingleton.getInstance().getAppService("notificationOrderProcessService");
		pageBean=notificationOrderProcessService.findNotificationOrderByPageBean(pageBean);
		List<Dictionarys> transportWays = RemoteServiceSingleton.getInstance().getInfrastructureService().transportWay();
		model.addAttribute("transportWays", transportWays);
		model.addAttribute("pb", pageBean);
		return "/stockorder/findStockOrderList";	
	}
	
	@RequestMapping(value = "/saveStockOrder")
	public String saveStockOrder(ShipOrderDto shipOrderDto,Model model){
		
		model.addAttribute("key", PKUtils.getLongPrimaryKey());
		return "/stockorder/saveStockOrder";	
	}
	
	@RequestMapping(value = "/StockOrderExportPDF")
	public String saveStockOrder(HttpServletRequest request,HttpServletResponse response,Model model){
		String[] id=request.getParameter("id").split(",");
		StockInOrderService stockInOrderService=(StockInOrderService)RemoteServiceSingleton.getInstance().getAppService("stockInOrderService");
		StockInOrderPDFService stockInOrderPDFService=(StockInOrderPDFService)RemoteServiceSingleton.getInstance().getAppService("stockInOrderPDFService");
		List<StockInOrderDto> orderDtos=new ArrayList<StockInOrderDto>();
		List<StockInOrderDto> orderPDFDtos=new ArrayList<StockInOrderDto>();
		for(int i=0;i<id.length;i++)
		{
			orderDtos.addAll(stockInOrderService.selectStockByNoId(Long.parseLong(id[i])));
		}
		for(StockInOrderDto orderDto:orderDtos)
		{
			orderPDFDtos.add(stockInOrderPDFService.selectStockInOrderPDF(orderDto.getId()));
		}
		WritePDF pdf=new WritePDF();
		String result="";
		if(orderPDFDtos.size()>0)
		{
			result=pdf.getInstance(WritePDF.TYPE02, orderPDFDtos, request);
		}
		
		return "redirect:/pchaseOrder/viewPDF?fileName="+result;	
	}
}
*/