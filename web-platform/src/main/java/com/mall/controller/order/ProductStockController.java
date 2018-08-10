/*package com.mall.controller.order;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mall.mybatis.utility.PageBean;
import com.mall.retailer.order.po.Dictionarys;
import com.mall.supplier.order.api.rpc.ExpressService;
import com.mall.supplier.order.api.rpc.InfrastructureService;
import com.mall.supplier.order.api.rpc.StockOutOrderService;
import com.mall.supplier.order.api.rpc.StockOutOrderShipService;
import com.mall.supplier.order.dto.ExpressDto;
import com.mall.supplier.order.dto.StockOutOrderDto;
import com.mall.supplier.order.dto.StockOutOrderShipDto;
import com.mall.supplier.order.po.Cost;
import com.mall.supplier.order.po.Currency;
import com.mall.supplier.order.po.StockOutOrder;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;

@Controller
@RequestMapping(value = "/productStock")
public class ProductStockController {
	@RequestMapping(value = "/findProductStock")
	public String findProductStock(Model model){
		return "/productStock/findProductStock";	
	}
	
	@RequestMapping(value = "/findProductStockList")
	public String findProductStockList(HttpServletRequest request,Integer page,Model model){
		
		java.text.SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		StockOutOrderDto map=new StockOutOrderDto();
		String outId=request.getParameter("sid");
		String status=request.getParameter("status");
		String outType=request.getParameter("outType");
		String warehouseName=request.getParameter("warehouseName");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		String oStartTime=request.getParameter("oStartTime");
		String oEndTime=request.getParameter("oEndTime");
		String notificationId=request.getParameter("notificationId");
		String notificationStatus=request.getParameter("notificationStatus");
		String orderId=request.getParameter("orderId");
		String orderType=request.getParameter("orderType");
		try {
			if(outId!=null&&!"".equals(outId))
			{
				map.setStockOutChar(outId);
			}
			if(status!=null&&!"".equals(status))
			{
				map.setStatus(Integer.parseInt(status));
			}
			if(outType!=null&&!"".equals(outType))
			{
				map.setOutType(outType);
			}
			if(warehouseName!=null&&!"".equals(warehouseName))
			{
				map.setWarehouseName(warehouseName);
			}
			if(startTime!=null&&!"".equals(startTime))
			{
				map.setStartTime(format.parse(startTime));
			}
			if(endTime!=null&&!"".equals(endTime))
			{
				map.setEndTime(format.parse(endTime));
			}
			if(notificationId!=null&&!"".equals(notificationId))
			{
				map.setNotificationIdChar(notificationId);
			}
			if(notificationStatus!=null&&!"".equals(notificationStatus))
			{
				map.setNotificationStatus(Integer.parseInt(notificationStatus));
			}
			if(orderId!=null&&!"".equals(orderId))
			{
				map.setOrderId(Long.parseLong(orderId));
			}
			if(orderType!=null&&!"".equals(orderType))
			{
				map.setOrderType(Integer.parseInt(orderType));
			}
			if(oStartTime!=null&&!"".equals(oStartTime))
			{
				map.setoStartTime(format.parse(oStartTime));
			}
			if(oEndTime!=null&&!"".equals(oEndTime))
			{
				map.setoEndTime(format.parse(oEndTime));
			}
		
		PageBean<StockOutOrderDto> pageBean=new PageBean<StockOutOrderDto>();
		pageBean.setParameter(map);
		if(page!=null)
		{
			pageBean.setPage(page);
		}else{
			pageBean.setPage(Constants.INT1);
		}
		pageBean.setPageSize(Constants.PAGESIZE);
		StockOutOrderService stockOutOrderService=(StockOutOrderService)RemoteServiceSingleton.getInstance().getAppService("stockOutOrderService");
		pageBean=stockOutOrderService.findStockOutOrder(pageBean);
		
		model.addAttribute("pb", pageBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/productStock/findProductStockList";	
	}
	
	@RequestMapping(value = "/OutPutExcel")
	public String OutPutExcel(HttpServletRequest request,HttpServletResponse response,Integer page,Model model){
		try {
			java.text.SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/vnd.ms-excel;charset=UTF-8"); 
			response.addHeader("Content-Disposition", "attachment;filename="+new String("商品发货列表".getBytes("gbk"),"iso-8859-1")+".xls");
			StockOutOrderDto map=new StockOutOrderDto();
			String outId=request.getParameter("sid");
			String status=request.getParameter("status");
			String outType=request.getParameter("outType");
			String warehouseName=request.getParameter("warehouseName");
			String startTime=request.getParameter("startTime");
			String endTime=request.getParameter("endTime");
			String oStartTime=request.getParameter("oStartTime");
			String oEndTime=request.getParameter("oEndTime");
			String notificationId=request.getParameter("notificationId");
			String notificationStatus=request.getParameter("notificationStatus");
			String orderId=request.getParameter("orderId");
			String orderType=request.getParameter("orderType");
				if(outId!=null&&!"".equals(outId))
				{
					map.setStockOutChar(outId);
				}
				if(status!=null&&!"".equals(status))
				{
					map.setStatus(Integer.parseInt(status));
				}
				if(outType!=null&&!"".equals(outType))
				{
					map.setOutType(outType);
				}
				if(warehouseName!=null&&!"".equals(warehouseName))
				{
					map.setWarehouseName(warehouseName);
				}
				if(startTime!=null&&!"".equals(startTime))
				{
					map.setStartTime(format.parse(startTime));
				}
				if(endTime!=null&&!"".equals(endTime))
				{
					map.setEndTime(format.parse(endTime));
				}
				if(notificationId!=null&&!"".equals(notificationId))
				{
					map.setNotificationIdChar(notificationId);
				}
				if(notificationStatus!=null&&!"".equals(notificationStatus))
				{
					map.setNotificationStatus(Integer.parseInt(notificationStatus));
				}
				if(orderId!=null&&!"".equals(orderId))
				{
					map.setOrderId(Long.parseLong(orderId));
				}
				if(orderType!=null&&!"".equals(orderType))
				{
					map.setOrderType(Integer.parseInt(orderType));
				}
				if(oStartTime!=null&&!"".equals(oStartTime))
				{
					map.setoStartTime(format.parse(oStartTime));
				}
				if(oEndTime!=null&&!"".equals(oEndTime))
				{
					map.setoEndTime(format.parse(oEndTime));
				}
			
			PageBean<StockOutOrderDto> pageBean=new PageBean<StockOutOrderDto>();
			pageBean.setParameter(map);
			if(page!=null)
			{
				pageBean.setPage(page);
			}else{
				pageBean.setPage(Constants.INT1);
			}
			pageBean.setPageSize(Constants.PAGESIZE);
			StockOutOrderService stockOutOrderService=(StockOutOrderService)RemoteServiceSingleton.getInstance().getAppService("stockOutOrderService");
			pageBean=stockOutOrderService.findStockOutOrder(pageBean);
			StockOutOrderWriteExcel(response,pageBean.getResult());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private void StockOutOrderWriteExcel(HttpServletResponse response,
			List<StockOutOrderDto> stockOutOrders) throws IOException {
		java.text.SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("学生表一");  
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        HSSFRow row = sheet.createRow((int) 0);  
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
  
        HSSFCell cell = row.createCell(0);  
        cell.setCellValue("序号");  
        cell.setCellStyle(style);  
        cell = row.createCell(1);  
        cell.setCellValue("通知单状态");  
        cell.setCellStyle(style);  
        cell = row.createCell(2);  
        cell.setCellValue("出库单状态");  
        cell.setCellStyle(style);  
        cell = row.createCell(3);  
        cell.setCellValue("发货单状态");  
        cell.setCellStyle(style);  
        cell = row.createCell(4);  
        cell.setCellValue("发货单编号");  
        cell.setCellStyle(style);  
        cell = row.createCell(5);  
        cell.setCellValue("出库通知单编号");  
        cell.setCellStyle(style);  
        cell = row.createCell(6);  
        cell.setCellValue("收件人");  
        cell.setCellStyle(style);  
        cell = row.createCell(7);  
        cell.setCellValue("收件地址");  
        cell.setCellStyle(style); 
        cell = row.createCell(8);  
        cell.setCellValue("电话");  
        cell.setCellStyle(style); 
        cell = row.createCell(9);  
        cell.setCellValue("发货要求");  
        cell.setCellStyle(style); 
        cell = row.createCell(10);  
        cell.setCellValue("订单编号");  
        cell.setCellStyle(style); 
        for (int i = 0; i < stockOutOrders.size(); i++)  
        {  
            row = sheet.createRow((int) i + 1);  
            StockOutOrder stu =stockOutOrders.get(i);  
            // 第四步，创建单元格，并设置值  
            row.createCell(0).setCellValue((int) i + 1);  
            String NotificationStatus="";
            if(stu.getNotificationStatus().equals(1))
            {
            	NotificationStatus="待发货";
            }
            if(stu.getNotificationStatus().equals(2))
            {
            	NotificationStatus="已打包";
            }
            if(stu.getNotificationStatus().equals(100))
            {
            	NotificationStatus="已发货";
            }
            row.createCell(1).setCellValue(NotificationStatus);  
            row.createCell(2).setCellValue(""); 
            String status="";
            if(stu.getStatus().equals(10))
            {
            	status="已审核";
            }
            if(stu.getStatus().equals(1))
            {
            	status="未审核";
            }
            row.createCell(3).setCellValue(status);
            row.createCell(4).setCellValue(stu.getStockOutChar());  
            
            row.createCell(5).setCellValue(stu.getNotificationIdChar()); 
            row.createCell(6).setCellValue(stu.getReceiveName());  
            row.createCell(7).setCellValue(stu.getReceiveAddress()); 
            row.createCell(8).setCellValue(stu.getReceivePhone());  
            row.createCell(9).setCellValue(stu.getOrderComment());  
            row.createCell(10).setCellValue(stu.getOrderId().toString());  
            
        } 
        OutputStream fout = response.getOutputStream();  
        wb.write(fout);  
        fout.close();  
	}
	@RequestMapping(value = "/findProductStockShip")
	public String findProductStockShip(HttpServletRequest request,Long id,Integer page,Model model){
		
		StockOutOrderShipService stockOutOrderShipService=(StockOutOrderShipService)RemoteServiceSingleton.getInstance().getAppService("stockOutOrderShipService");
		 List<StockOutOrderShipDto> dtos=stockOutOrderShipService.findStockOutOrderShip(id);
		 model.addAttribute("ships", dtos);
		return "/productStock/findProductStockShip";
	}
	
	@RequestMapping(value = "/StockShip")
	public String StockShip(Model model){
		
		ExpressService expressService=(ExpressService)RemoteServiceSingleton.getInstance().getAppService("expressService");
		List<ExpressDto> dtos=expressService.findExpress();
		model.addAttribute("express", dtos);
		return "/productStock/StockShip";
	}
	
	@RequestMapping(value = "/findStockShipList")
	public String findStockShipList(HttpServletRequest request,Integer page,Model model){
		java.text.SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		String expressNumber=request.getParameter("expressNumber");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		PageBean<StockOutOrderShipDto> pageBean=new PageBean<StockOutOrderShipDto>();
		StockOutOrderShipService stockOutOrderShipService=(StockOutOrderShipService)RemoteServiceSingleton.getInstance().getAppService("stockOutOrderShipService");
		StockOutOrderShipDto dto=new StockOutOrderShipDto();
		try {
			if(expressNumber!=null&&!"".equals(expressNumber))
			{
				dto.setExpressNumber(expressNumber);
			}
			if(startTime!=null&&!"".equals(startTime))
			{
				dto.setStartTime(format.parse(startTime));
			}
			if(endTime!=null&&!"".equals(endTime))
			{
				dto.setEndTime(format.parse(endTime));
			}
			pageBean.setParameter(dto);
			if(page!=null)
			{
				pageBean.setPage(page);
			}else{
				pageBean.setPage(1);
			}
			pageBean.setPageSize(10);
			pageBean=stockOutOrderShipService.findStockOutOrderShipListPage(pageBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("pb", pageBean);
		return "/productStock/StockShipList";
	}
	
	@RequestMapping(value = "/OutPutShipExcel")
	public String OutPutShipExcel(HttpServletRequest request,HttpServletResponse response,Integer page,Model model){
		try {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/vnd.ms-excel;charset=UTF-8"); 
		response.addHeader("Content-Disposition", "attachment;filename="+new String("物流核对".getBytes("gbk"),"iso-8859-1")+".xls");
		java.text.SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		String expressNumber=request.getParameter("expressNumber");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		PageBean<StockOutOrderShipDto> pageBean=new PageBean<StockOutOrderShipDto>();
		StockOutOrderShipService stockOutOrderShipService=(StockOutOrderShipService)RemoteServiceSingleton.getInstance().getAppService("stockOutOrderShipService");
		StockOutOrderShipDto dto=new StockOutOrderShipDto();
		
			if(expressNumber!=null&&!"".equals(expressNumber))
			{
				dto.setExpressNumber(expressNumber);
			}
			if(startTime!=null&&!"".equals(startTime))
			{
				dto.setStartTime(format.parse(startTime));
			}
			if(endTime!=null&&!"".equals(endTime))
			{
				dto.setEndTime(format.parse(endTime));
			}
			pageBean.setParameter(dto);
			if(page!=null)
			{
				pageBean.setPage(page);
			}else{
				pageBean.setPage(1);
			}
			pageBean.setPageSize(10000000);
			pageBean=stockOutOrderShipService.findStockOutOrderShipListPage(pageBean);
			StockOutOrderShipWriteExcel(response,pageBean.getResult());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private void StockOutOrderShipWriteExcel(HttpServletResponse response,
			List<StockOutOrderShipDto> stockOutOrders) throws IOException {
		java.text.SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("物流核对");  
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        HSSFRow row = sheet.createRow((int) 0);  
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
  
        HSSFCell cell = row.createCell(0);  
        cell.setCellValue("序号");  
        cell.setCellStyle(style);  
        cell = row.createCell(1);  
        cell.setCellValue("发货单编号");  
        cell.setCellStyle(style);  
        cell = row.createCell(2);  
        cell.setCellValue("快递单编号");  
        cell.setCellStyle(style);  
        cell = row.createCell(3);  
        cell.setCellValue("快递商名称");  
        cell.setCellStyle(style);  
        cell = row.createCell(4);  
        cell.setCellValue("始发地");  
        cell.setCellStyle(style);  
        cell = row.createCell(5);  
        cell.setCellValue("目的地");  
        cell.setCellStyle(style);  
        cell = row.createCell(6);  
        cell.setCellValue("重量(kg)");  
        cell.setCellStyle(style);  
        cell = row.createCell(7);  
        cell.setCellValue("运费(元)");  
        cell.setCellStyle(style); 
        
        for (int i = 0; i < stockOutOrders.size(); i++)  
        {  
            row = sheet.createRow((int) i + 1);  
            StockOutOrderShipDto stu =stockOutOrders.get(i);  
            // 第四步，创建单元格，并设置值  
            row.createCell(0).setCellValue((int) i + 1);  
            
            row.createCell(1).setCellValue(stu.getOutId().toString());  
            row.createCell(2).setCellValue(stu.getExpressNumber()); 
            
            row.createCell(3).setCellValue(stu.getExpressName());
            row.createCell(4).setCellValue(stu.getStartAddress());  
            
            row.createCell(5).setCellValue(stu.getEndAddress()); 
            row.createCell(6).setCellValue(stu.getWeight()==null?"":stu.getWeight().toString());  
            row.createCell(7).setCellValue(stu.getFreightPrice()==null?"":stu.getFreightPrice().toString()); 
        } 
        OutputStream fout = response.getOutputStream();  
        wb.write(fout);  
        fout.close();  
	}
}
*/