/*package com.mall.controller.selloutstorage;

import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.kefu.util.ValidateUtil;
import com.mall.mybatis.utility.PageBean;
import com.mall.stock.api.rpc.WarehouseService;
import com.mall.stock.po.Warehouse;
import com.mall.supplier.order.api.rpc.NotificationOutOrderExportService;
import com.mall.supplier.order.api.rpc.NotificationOutOrderProcessService;
import com.mall.supplier.order.dto.NotificationOutOrderDTO;
import com.mall.supplier.order.dto.NotificationOutOrderVo;
import com.mall.supplier.order.exception.BusinessException;
import com.mall.supplier.order.po.NotificationOutItem;
import com.mall.supplier.order.po.NotificationOutOrder;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;
import com.mall.utils.WritePDF;

*//**
 * @ClassName NotificationOutOrderController
 * @Description: 出库通知
 * @author: LiuRangkui
 * @since: 2015-6-26 下午1:55:09
 *//*
@SuppressWarnings("all")
@Controller
@RequestMapping(value = "/selloutstorage")
public class NotificationOutOrderController extends BaseController {

	private static final Log LOGGER = LogFactory
			.getLogger(NotificationOutOrderController.class);

	*//**
	 * 此方法用于日期的转换
	 *//*
	@Override
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	*//**
	 * @Description： 跳转入库通知单页面
	 * @since: 2015-6-26 上午11:15:40
	 *//*
	@RequestMapping(value = "/getNotificationOutOrder", method = RequestMethod.GET)
	public final String getNotificationInOrder(Model model) {
		WarehouseService warehouseService = RemoteServiceSingleton
				.getInstance().getWarehouseService();
		List<Warehouse> warehouseList = warehouseService
				.findWarehouseByWareseName(null);
		model.addAttribute("warehouseList", warehouseList);
		Map<Integer, String> outTypeMap = Constants.OutType.getOutTypeMap();
        model.addAttribute("outTypeMap", outTypeMap);
		return "/dealerseller/selloutstorage/notificationOutList";
	}

	*//**
	 * @Description： 导PDF
	 * @author: QIJJ
	 * @since: 2015-6-26 下午2:19:51
	 *//*
	@RequestMapping(value = "/NotificationOutOrderPDF")
	public String NotificationOutOrderPDF(HttpServletRequest request,
			Model model) {
		String result = "";
		String[] id = request.getParameter("id").split(",");
		NotificationOutOrderProcessService notificationOutOrderProcessService = (NotificationOutOrderProcessService) RemoteServiceSingleton
				.getInstance().getAppService(
						"notificationOutOrderProcessService");
		List<NotificationOutOrderDTO> orders = new ArrayList<NotificationOutOrderDTO>();
		for (int i = 0; i < id.length; i++) {
			orders.add(notificationOutOrderProcessService
					.findNotificationOutOrder(Long.parseLong(id[i])));
		}
		WritePDF pdf = new WritePDF();
		result = pdf.getInstance(WritePDF.TYPE04, orders, request);
		return "redirect:/pchaseOrder/viewPDF?fileName=" + result;
	}

	*//**
	 * @Description： 查询出库通知单表格数据
	 * @since: 2015-6-26 上午11:15:48
	 *//*
	@RequestMapping(value = "/getNotificationOutOrderPageBean", method = RequestMethod.POST)
	public final String getNotificationOutOrderPageBean(
			HttpServletRequest request, Long warehouseCode,
			Integer statusValue, String businessType,
			String outNotificationNumber, Long orderNumber,
			String beginOutTime, String endOutTime, Integer page,
			String orderComment, String thirdOrderId, String outType,
			String otherCusType, String logisticsOrder) {

		java.text.SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		PageBean<NotificationOutOrder> pageBean = null;
		try {
			pageBean = (PageBean<NotificationOutOrder>) request
					.getAttribute("pb");
			if (pageBean == null) {
				pageBean = new PageBean<NotificationOutOrder>();
				if (page != null) {
					pageBean.setPage(page);
				} else {
					pageBean.setPage(1);
				}

				pageBean.setPageSize(10);
			}

			NotificationOutOrderVo notificationOutOrderVo = new NotificationOutOrderVo();
			notificationOutOrderVo.setWarehouseCode(warehouseCode);
			notificationOutOrderVo.setStatus(statusValue == null ? 0
					: statusValue);
			notificationOutOrderVo.setBusinessType(businessType);
			notificationOutOrderVo
					.setOutNotificationNumberChar(outNotificationNumber);
			notificationOutOrderVo.setOrderNumber(orderNumber);
			notificationOutOrderVo.setOrderComment(orderComment);
			notificationOutOrderVo.setThirdOrderId(thirdOrderId);
			// notificationOutOrderVo.setOtherCusType(otherCusType);
//			notificationOutOrderVo.setLogisticsOrder(logisticsOrder);

			if (outType.equals("2") || outType == "2") {
				if (!ValidateUtil.isEmpty(otherCusType)) {
					outType = otherCusType;
				}
			}

			notificationOutOrderVo.setOutType(outType);

			if (beginOutTime != null && !"".equals(beginOutTime)) {
				notificationOutOrderVo.setBeginOutTime(format
						.parse(beginOutTime));
			}
			if (endOutTime != null && !"".equals(endOutTime)) {
				notificationOutOrderVo.setEndOutTime(format.parse(endOutTime));
			}

			pageBean.setParameter(notificationOutOrderVo);
			NotificationOutOrderProcessService notificationOutOrderProcessService = (NotificationOutOrderProcessService) RemoteServiceSingleton
					.getInstance().getAppService(
							"notificationOutOrderProcessService");
			pageBean = notificationOutOrderProcessService
					.findNotificationOutOrderByPageBean(pageBean);
		} catch (Exception e) {
			LOGGER.error("用户:" + getCurrentUser().getUsername());
			LOGGER.error("用户ID:" + getCurrentUser().getId());
			LOGGER.error("获取通知出库单列表,错误信息:" + e.getMessage(), e);
		}
		request.getSession().setAttribute("pb", pageBean);

		return "/dealerseller/selloutstorage/modelpage/list";
	}

	*//**
	 * @Description： 查看出库通知详细内容
	 * @since: 2015-6-26 下午2:26:20
	 *//*
	@RequestMapping(value = "/getNotiticationOutItemList", method = {
			RequestMethod.POST, RequestMethod.GET })
	public final String getNotiticationOutItemList(HttpServletRequest request) {
		String notificationId = request.getParameter("notificationId");
		if (notificationId == null || "".equals(notificationId)) {
			throw new BusinessException("出库通知单为空");
		}
		NotificationOutOrderProcessService notificationOutOrderProcessService = (NotificationOutOrderProcessService) RemoteServiceSingleton
				.getInstance().getAppService(
						"notificationOutOrderProcessService");
		List<NotificationOutItem> notificationOutItemList = notificationOutOrderProcessService
				.findNotificationOutItemByNotificationId(Long
						.valueOf(notificationId));
		request.setAttribute("notificationOutItemList", notificationOutItemList);
		 request.setAttribute("notificationId", notificationId); 

		return "/dealerseller/selloutstorage/notificationOutItem";
	}

	@RequestMapping(value = "/checkNotificationOrder", method = RequestMethod.POST)
	public final String checkNotificationOrder(HttpServletRequest request) {
		String notificatioinOutId = request.getParameter("notificatioinOutId");
		if (notificatioinOutId == null || "".equals(notificatioinOutId)) {
			throw new BusinessException("出库通知单为空");
		}
		NotificationOutOrder notificationOutOrder = new NotificationOutOrder();
		notificationOutOrder.setOutNotificationNumber(Long
				.valueOf(notificatioinOutId));
		notificationOutOrder.setStatus(10);

		NotificationOutOrderDTO notificationOutOrderDTO = new NotificationOutOrderDTO();
		notificationOutOrderDTO.setNotificationOutOrder(notificationOutOrder);
		notificationOutOrderDTO.setNotificationOutItemList(null);
		NotificationOutOrderProcessService notificationOutOrderProcessService = (NotificationOutOrderProcessService) RemoteServiceSingleton
				.getInstance().getAppService(
						"notificationOutOrderProcessService");
		notificationOutOrderProcessService
				.updateNotificationOutOrder(notificationOutOrderDTO);
		return "succeed";
	}

	*//**
	 * @Description： 导出EXCEL
	 * @since: 2015-6-26 下午1:56:54
	 *//*
	@RequestMapping(value = "/exportNotifyOutOrderList", method = RequestMethod.GET)
	public final void exportNotifyOutOrderList(HttpServletRequest request,
			HttpServletResponse response, String firstDate, String lastDate,
			String warehouseName, String outNotificationNumber, String status,
			String orderNumber, String businessType, String orderComment,
			String thirdOrderId,String outType,
			String otherCusType) {

		WarehouseService warehouseService = RemoteServiceSingleton
				.getInstance().getWarehouseService();
		NotificationOutOrderVo notificationOutOrderVo = new NotificationOutOrderVo();
		// notificationOutOrderVo.setBeginOutTime(firstDate);
		// notificationOutOrderVo.setEndOutTime(lastDate);
		Map<String, Object> map = new HashMap<String, Object>();
		NotificationOutOrderExportService notificationOutOrderExportService = (NotificationOutOrderExportService) RemoteServiceSingleton
				.getInstance().getAppService(
						"notificationOutOrderExportService");
		// List<NotificationOutOrder> notificationOutOrderList =
		// notificationOutOrderExportService.findNotificationOutOrder(notificationOutOrderVo);
		map.put("beginOutTime", firstDate);
		map.put("endOutTime", lastDate);
		map.put("warehouseName", warehouseName);
		map.put("outNotificationNumber", outNotificationNumber);
		map.put("status", status);
		map.put("orderNumber", orderNumber);
		map.put("businessType", businessType);
		map.put("orderComment", orderComment);
		map.put("thirdOrderId", thirdOrderId);
		
		if (outType.equals("2") || outType == "2") {
			if (!ValidateUtil.isEmpty(otherCusType)) {
				outType = otherCusType;
			}
		}

		map.put("outType", outType);
		
		
		List<Map<String, Object>> notificationOutOrderList = notificationOutOrderExportService
				.findNoutOrderByMap(map);

		HSSFWorkbook workbook = new HSSFWorkbook();// 声明一个工作薄
		HSSFSheet sheet = workbook.createSheet("通知出库列表");// 生成一个表格
		HSSFRow row = sheet.createRow(0);

		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.YELLOW.index);

		HSSFCell cell = null;
		String[] titles = { "序号", "交易时间", "订单来源", "订单号出库申请ID","第三方订单号", "收货人", "联系电话",
				"收货地址", "商品ID", "商品编码", "条码", "商品名称","SKU_ID","仓库条码", "单位", "规格", "批次", "销售单价",
				"数量", "小计", "仓库", "赠品", "订单留言", "运费", "wms业务单据号", "领用原因" };

		sheet.setDefaultColumnWidth(24);  
		for (int i = 0; i < titles.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(titles[i].toString());
			cell.setCellStyle(style);
		}
		try {
			OutputStream os = null;
			for (int j = 0; j < notificationOutOrderList.size(); j++) {
				int lastRowIndex = sheet.getLastRowNum();

				Map<String, Object> obj = notificationOutOrderList.get(j);
				
				row = sheet.createRow(lastRowIndex + 1);

				this.geneCell(row, Constants.SHORT0, j + 1 + "");
				this.geneCell(row, Constants.SHORT1,
						obj.get("PAY_TIME") == null ? "" : obj.get("PAY_TIME")
								.toString());
				this.geneCell(
						row,
						Constants.SHORT2,
						obj.get("BUSINESS_TYPE") == null ? "" : obj.get(
								"BUSINESS_TYPE").toString());
				this.geneCell(
						row,
						Constants.SHORT3,
						obj.get("BUSINESS_NUMBER") == null ? "" : obj.get(
								"BUSINESS_NUMBER").toString());
				String THIRD_ORDERID = (String) obj.get("THIRD_ORDERID")==null ? " ":(String)obj.get("THIRD_ORDERID");
				if(THIRD_ORDERID.equals("undefined")){
					this.geneCell(
							row,
							Constants.SHORT4,"");
				}else{
					this.geneCell(
							row,
							Constants.SHORT4,
							THIRD_ORDERID == null ? "" : THIRD_ORDERID.toString());
				}
				
				this.geneCell(row, Constants.SHORT5,
						obj.get("FULL_NAME") == null ? "" : obj
								.get("FULL_NAME").toString());
				this.geneCell(row, Constants.SHORT6,
						obj.get("MOBILE") == null ? "" : obj.get("MOBILE")
								.toString());
				this.geneCell(row, Constants.SHORT7,
						obj.get("ADDRESS") == null ? "" : obj.get("ADDRESS")
								.toString());
				this.geneCell(row, Constants.SHORT8,
						obj.get("PID") == null ? "" : obj.get("PID").toString());
				this.geneCell(row, Constants.SHORT9,
						obj.get("PCODE") == null ? "" : obj.get("PCODE")
								.toString());
				this.geneCell(row, Constants.SHORT10,
						obj.get("BAR_CODE") == null ? "" : obj.get("BAR_CODE")
								.toString());
				this.geneCell(row, Constants.SHORT11,
						obj.get("PNAME") == null ? "" : obj.get("PNAME")
								.toString());
				this.geneCell(row, Constants.SHORT12,
						obj.get("SKU_ID") == null ? "" : obj.get("SKU_ID")
								.toString());
				this.geneCell(row, Constants.SHORT13,
						obj.get("BAR_SKU_ID") == null ? "" : obj.get("BAR_SKU_ID")
								.toString());
				this.geneCell(row, Constants.SHORT14,
						obj.get("UNIT") == null ? "" : obj.get("UNIT")
								.toString());
				this.geneCell(row, Constants.SHORT15,
						obj.get("FORMAT") == null ? "" : obj.get("FORMAT")
								.toString());
				this.geneCell(
						row,
						Constants.SHORT16,
						obj.get("BATCH_NUMBER") == null ? "" : obj.get(
								"BATCH_NUMBER").toString());
				this.geneCell(row, Constants.SHORT17,
						obj.get("PRICE") == null ? "" : obj.get("PRICE")
								.toString());
				this.geneCell(row, Constants.SHORT18,
						obj.get("LOCAL_QTY") == null ? "" : obj
								.get("LOCAL_QTY").toString());
				this.geneCell(
						row,
						Constants.SHORT19,
						obj.get("TOTAL_PRICE") == null ? "" : obj.get(
								"TOTAL_PRICE").toString());

				String warehouseCode = obj.get("WAREHOUSE_NAME").toString();
				if (warehouseCode != null && !warehouseCode.equals("")) {
					Warehouse warehouse = warehouseService
							.findWarehouseByCode(Integer
									.parseInt(warehouseCode));

					if (warehouse != null) {
						this.geneCell(row, Constants.SHORT20,
								warehouse.getWarehouseName());
					} else {
						this.geneCell(row, Constants.SHORT20, "");
					}
				} else {

					this.geneCell(row, Constants.SHORT20, "");
				}

				this.geneCell(row, Constants.SHORT21,
						obj.get("ISGIVE") == null ? "" : obj.get("ISGIVE")
								.toString());
				this.geneCell(
						row,
						Constants.SHORT22,
						obj.get("ORDER_COMMENT") == null ? "" : obj.get(
								"ORDER_COMMENT").toString());

				this.geneCell(
						row,
						Constants.SHORT23,
						obj.get("FREIGHT_PRICE") == null ? "" : obj.get(
								"FREIGHT_PRICE").toString());

				this.geneCell(row, Constants.SHORT24,
						obj.get("SID") == null ? "" : obj.get("SID").toString());
							
				if(null==obj.get("USE_CAUSE")){
					this.geneCell(row, Constants.SHORT25, "");
				}else{
					String useCause = obj.get("USE_CAUSE").toString();
					if(useCause.equals("1")){
						useCause = "样品出库-归还";
					}
					if(useCause.equals("2")){
						useCause = "样品出库-不归还";
					}
					if(useCause.equals("3")){
						useCause = "地推活动";
					}
					if(useCause.equals("4")){
						useCause = "礼包出库";
					}
					if(useCause.equals("5")){
						useCause = "残品出库";
					}
					if(useCause.equals("6")){
						useCause = "产品临期";
					}
					
					if(useCause.equals("")){
						useCause = "";
					}
					if(useCause.equals("undefined")){
						useCause = "";
					}
					this.geneCell(row, Constants.SHORT25,useCause);
				}
			}

			LOGGER.info("拼装出库通知单信息完成!");
			String filename = "通知出库列表";
			os = response.getOutputStream();
			response.reset();
			response.setCharacterEncoding("UTF-8");
			filename = java.net.URLEncoder.encode(filename, "UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes("UTF-8"), "GBK") + ".xls");
			response.setContentType("application/msexcel");// 定义输出类型
			workbook.write(os);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			LOGGER.error("下载出库通知单错误" + e.getMessage(), e);
			e.printStackTrace();
		}

	}

	*//**
	 * downExcel Help
	 * 
	 * @param row
	 * @param cellNum
	 * @param strCreatedate
	 * @param style
	 *//*
	@SuppressWarnings("deprecation")
	private void geneCell(HSSFRow row, Short cellNum, String strCreatedate) {
		HSSFCell cell = row.createCell(cellNum);
		cell.setCellValue(strCreatedate);
	}

}
*/