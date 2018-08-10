/*package com.mall.controller.order;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.mybatis.utility.PageBean;
import com.mall.stock.api.rpc.WarehouseService;
import com.mall.stock.po.Warehouse;
import com.mall.supplier.order.api.rpc.StockOutOrderExcelService;
import com.mall.supplier.order.api.rpc.StockOutOrderItemService;
import com.mall.supplier.order.api.rpc.StockOutOrderPDFService;
import com.mall.supplier.order.api.rpc.StockOutOrderService;
import com.mall.supplier.order.dto.StockOutOrderDto;
import com.mall.supplier.order.dto.StockOutOrderItemDto;
import com.mall.supplier.order.po.StockOutOrder;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;
import com.mall.utils.WritePDF;

@Controller
@RequestMapping(value = "/outstock")
public class StockOutOrderController {

	private static final Log LOGGER = LogFactory.getLogger(StockOutOrderController.class);

	@RequestMapping(value = "/findOutStock")
	public String findOutStock(Model model) {
		WarehouseService warehouseService = RemoteServiceSingleton.getInstance().getWarehouseService();
		List<Warehouse> channels = warehouseService.findWarehouseByWareseName("");
		model.addAttribute("channels", channels);

		return "/outstock/findOutStock";
	}

	@RequestMapping(value = "/findOutStockList")
	public String findOutStockList(HttpServletRequest request, Integer page, Model model) {
		java.text.SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		StockOutOrderDto map = new StockOutOrderDto();
		String outId = request.getParameter("outId");
		String status = request.getParameter("status");
		String outType = request.getParameter("outType");
		String warehouseId = request.getParameter("warehouseId");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String notificationId = request.getParameter("notificationId");
		String notificationStatus = request.getParameter("notificationStatus");
		String orderId = request.getParameter("orderId");
		String orderType = request.getParameter("orderType");
		String oStartTime = request.getParameter("oStartTime");
		String oEndTime = request.getParameter("oEndTime");
		try {
			if (outId != null && !"".equals(outId)) {
				map.setStockOutChar(outId);
			}
			if (status != null && !"".equals(status)) {
				map.setStatus(Integer.parseInt(status));
			}
			if (orderType != null && !"".equals(orderType)) {
				map.setOrderType(Integer.parseInt(orderType));
			}
			if (warehouseId != null && !"".equals(warehouseId)) {
				map.setWarehouseId(Long.parseLong(warehouseId));
			}
			if (startTime != null && !"".equals(startTime)) {
				map.setStartTime(format.parse(startTime));
			}
			if (endTime != null && !"".equals(endTime)) {
				map.setEndTime(format.parse(endTime));
			}
			if (notificationId != null && !"".equals(notificationId)) {
				map.setNotificationIdChar(notificationId);
			}
			if (notificationStatus != null && !"".equals(notificationStatus)) {
				map.setNotificationStatus(Integer.parseInt(notificationStatus));
			}
			if (orderId != null && !"".equals(orderId)) {
				map.setOrderId(Long.parseLong(orderId));
			}
			if (outType != null && !"".equals(outType)) {
				map.setOutType(outType);
			}
			if (oStartTime != null && !"".equals(oStartTime)) {
				map.setoStartTime(format.parse(oStartTime));
			}
			if (oEndTime != null && !"".equals(oEndTime)) {
				map.setoEndTime(format.parse(oEndTime));
			}

			PageBean<StockOutOrderDto> pageBean = new PageBean<StockOutOrderDto>();
			pageBean.setParameter(map);
			if (page != null) {
				pageBean.setPage(page);
			} else {
				pageBean.setPage(Constants.INT1);
			}
			pageBean.setPageSize(Constants.PAGESIZE);
			StockOutOrderService stockOutOrderService = (StockOutOrderService) RemoteServiceSingleton.getInstance().getAppService("stockOutOrderService");
			pageBean = stockOutOrderService.findStockOutOrder(pageBean);

			model.addAttribute("pb", pageBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/outstock/findOutStockList";
	}

	@RequestMapping(value = "/findOutStockItemList")
	public String findOutStockItemList(Long id, Integer page, Model model) {
		PageBean<StockOutOrderItemDto> pageBean = new PageBean<StockOutOrderItemDto>();
		try {
			LOGGER.info("START findOutStockItemList");
			StockOutOrderItemService stockOutOrderItemService = (StockOutOrderItemService) RemoteServiceSingleton.getInstance().getAppService("stockOutOrderItemService");
			LOGGER.info("GET" + stockOutOrderItemService.toString());
			pageBean = stockOutOrderItemService.findStockOutOrderByID(id);
			LOGGER.info("GET" + pageBean.toString());
			if (page != null) {
				pageBean.setPage(page);
			} else {
				pageBean.setPage(Constants.INT1);
			}
			pageBean.setPageSize(Constants.PAGESIZE);
		} catch (Exception e) {
			LOGGER.info(e.getMessage(), e);
			e.printStackTrace();
		}

		model.addAttribute("pageBean", pageBean);
		LOGGER.info("/outstock/findOutStockItemList" + pageBean.toString());
		return "/outstock/findOutStockItemList";
	}

	@RequestMapping(value = "/OutPutExcel")
	public String OutPutExcel(HttpServletRequest request, HttpServletResponse response, Integer page, Model model) {
		try {
			java.text.SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + new String("通知发货列表".getBytes("gbk"), "iso-8859-1") + ".xls");
			StockOutOrderDto map = new StockOutOrderDto();
			String outId = request.getParameter("outId");
			String status = request.getParameter("status");
			String outType = request.getParameter("outType");
			String warehouseId = request.getParameter("warehouseId");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String notificationId = request.getParameter("notificationId");
			String notificationStatus = request.getParameter("notificationStatus");
			String orderId = request.getParameter("orderId");
			String orderType = request.getParameter("orderType");
			String oStartTime = request.getParameter("oStartTime");
			String oEndTime = request.getParameter("oEndTime");
			if (outId != null && !"".equals(outId)) {
				map.setSid(Long.parseLong(outId));
			}
			if (status != null && !"".equals(status)) {
				map.setStatus(Integer.parseInt(status));
			}
			if (outType != null && !"".equals(outType)) {
				map.setOrderType(Integer.parseInt(outType));
			}
			if (warehouseId != null && !"".equals(warehouseId)) {
				map.setWarehouseId(Long.parseLong(warehouseId));
			}
			if (startTime != null && !"".equals(startTime)) {
				map.setStartTime(format.parse(startTime));
			}
			if (endTime != null && !"".equals(endTime)) {
				map.setEndTime(format.parse(endTime));
			}
			if (notificationId != null && !"".equals(notificationId)) {
				map.setNotificationId(Long.parseLong(notificationId));
			}
			if (notificationStatus != null && !"".equals(notificationStatus)) {
				map.setNotificationStatus(Integer.parseInt(notificationStatus));
			}
			if (orderId != null && !"".equals(orderId)) {
				map.setOrderId(Long.parseLong(orderId));
			}
			if (orderType != null && !"".equals(orderType)) {
				map.setOrderType(Integer.parseInt(orderType));
			}
			if (oStartTime != null && !"".equals(oStartTime)) {
				map.setoStartTime(format.parse(oStartTime));
			}
			if (oEndTime != null && !"".equals(oEndTime)) {
				map.setoEndTime(format.parse(oEndTime));
			}
			StockOutOrderExcelService stockOutOrderExcelService = (StockOutOrderExcelService) RemoteServiceSingleton.getInstance().getAppService("stockOutOrderExcelService");
			PageBean<StockOutOrderDto> pageBean = new PageBean<StockOutOrderDto>();
			pageBean.setParameter(map);
			if (page != null) {
				pageBean.setPage(page);
			} else {
				pageBean.setPage(1);
			}
			pageBean.setPageSize(10000000);
			List<StockOutOrderDto> stockOutOrders = stockOutOrderExcelService.findStockOutOrder(pageBean);
			StockOutOrderWriteExcel(response, stockOutOrders);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value = "/saveOutStock")
	public String saveOutStock(HttpServletRequest request, HttpServletResponse response, Integer page, Model model) {

		return "/outstock/saveOutStock";
	}

	private void StockOutOrderWriteExcel(HttpServletResponse response, List<StockOutOrderDto> stockOutOrders) throws IOException {
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("学生表一");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow(0);
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
		cell.setCellValue("出库单编号");
		cell.setCellStyle(style);
		cell = row.createCell(4);
		cell.setCellValue("出库通知单编号");
		cell.setCellStyle(style);
		cell = row.createCell(5);
		cell.setCellValue("收件人");
		cell.setCellStyle(style);
		cell = row.createCell(6);
		cell.setCellValue("收件地址");
		cell.setCellStyle(style);
		cell = row.createCell(7);
		cell.setCellValue("电话");
		cell.setCellStyle(style);
		cell = row.createCell(8);
		cell.setCellValue("发货要求");
		cell.setCellStyle(style);
		cell = row.createCell(9);
		cell.setCellValue("订单编号");
		cell.setCellStyle(style);
		for (int i = 0; i < stockOutOrders.size(); i++) {
			row = sheet.createRow(i + 1);
			StockOutOrder stu = stockOutOrders.get(i);
			// 第四步，创建单元格，并设置值
			row.createCell(0).setCellValue(i + 1);
			String NotificationStatus = "";
			if (stu.getNotificationStatus().equals(1)) {
				NotificationStatus = "待发货";
			}
			if (stu.getNotificationStatus().equals(2)) {
				NotificationStatus = "已打包";
			}
			if (stu.getNotificationStatus().equals(100)) {
				NotificationStatus = "已发货";
			}
			row.createCell(1).setCellValue(NotificationStatus);
			String status = "";
			if (stu.getStatus().equals(10)) {
				status = "已审核";
			}
			if (stu.getStatus().equals(1)) {
				status = "未审核";
			}
			row.createCell(2).setCellValue(status);
			row.createCell(3).setCellValue(stu.getStockOutChar());
			row.createCell(4).setCellValue(stu.getNotificationIdChar());
			row.createCell(5).setCellValue(stu.getReceiveName());
			row.createCell(6).setCellValue(stu.getReceiveAddress());
			row.createCell(7).setCellValue(stu.getReceivePhone());
			row.createCell(8).setCellValue(stu.getOrderComment());
			row.createCell(9).setCellValue(stu.getOrderId().toString());
		}
		OutputStream fout = response.getOutputStream();
		wb.write(fout);
		fout.close();
	}

	@RequestMapping(value = "/StockOutOrderExportPDF")
	public String StockOutOrderExportPDF(HttpServletRequest request, HttpServletResponse response) {
		String[] id = request.getParameter("id").split(",");
		String result = "";
		StockOutOrderPDFService stockOutOrderPDFService = (StockOutOrderPDFService) RemoteServiceSingleton.getInstance().getAppService("stockOutOrderPDFService");
		List<StockOutOrderDto> outOrderDtos = new ArrayList<StockOutOrderDto>();
		for (int i = 0; i < id.length; i++) {
			outOrderDtos.add(stockOutOrderPDFService.selectByOrderId(Long.parseLong(id[i])));
		}
		WritePDF pdf = new WritePDF();
		result = pdf.getInstance(WritePDF.TYPE03, outOrderDtos, request);
		return "redirect:/pchaseOrder/viewPDF?fileName=" + result;
	}
}
*/