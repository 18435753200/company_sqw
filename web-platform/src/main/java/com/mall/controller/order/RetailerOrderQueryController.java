/*package com.mall.controller.order;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.customer.order.api.rpc.CustomerOrderService;
import com.mall.customer.order.dto.OrderDTO;
import com.mall.customer.order.dto.OrderItemDTO;
import com.mall.mybatis.utility.PageBean;
import com.mall.platform.model.PlatformSale;
import com.mall.platform.service.PlatformSaleManagerService;
import com.mall.retailer.model.Retailer;
import com.mall.retailer.model.RetailerAddressDTO;
import com.mall.retailer.order.api.rpc.RetailerOrderExportService;
import com.mall.retailer.order.api.rpc.RetailerOrderService;
import com.mall.retailer.order.common.RetailerConstant;
import com.mall.retailer.order.dto.OrderDto;
import com.mall.retailer.order.dto.OrderItemDto;
import com.mall.retailer.order.dto.OrderPayDetailDto;
import com.mall.retailer.order.dto.OrderPayDto;
import com.mall.retailer.order.dto.OrderPayVO;
import com.mall.retailer.order.dto.ProductDto;
import com.mall.retailer.order.dto.ShipOrderDTO;
import com.mall.retailer.order.dto.ShipOrderItemDTO;
import com.mall.retailer.order.po.Order;
import com.mall.retailer.service.RetailerManagerService;
import com.mall.supplier.order.api.rpc.ShipOrderService;
import com.mall.wms.api.OrderSCancelService;
import com.mall.wms.api.OutOrderService;
import com.mall.controller.base.BaseController;
import com.mall.controller.base.Result;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Common;
import com.mall.utils.Constants;
*//**
 * @Description:wofe查询所有订单.
 * @author GuoFeiyan
 * @Date 2015年3月2日11:10:59
 *//*

@Controller
@RequestMapping(value = "/retailerOrder")
public class RetailerOrderQueryController extends BaseController {

	*//**
	 *  LOGGER(日志打印).
	 *//*
	private static final Log LOGGER = LogFactory.getLogger(RetailerOrderQueryController.class);
	
	
	*//**
	 * 初始化加载所有零售商订单.
	 * @return
	 *//*
	@RequestMapping(value = "/retailerOrderAllList")
	public String retailerOrderAllList(){
		LOGGER.info("初始化加载所有零售商订单列表!!!!");
		return "/dealerseller/B2Border/retailerorder_alllist";
	}
	
	
	*//**
	 * @Description:根据条件检索零售商所有订单.
	 * @title getRetailerOrderListByCondition
	 * @param Model model
	 * @param Long payId
	 * @param String retailerName
	 * @param String pName
	 * @param String createTime
	 * @param String endTime
	 * @param Integer page
	 * @param Short status  
	 * @return String
	 *//*
	@RequestMapping(value = "/getRetailerOrderListByCondition")
	public String getRetailerOrderListByCondition(Model model,Long payId,String retailerName,String pName,
			String createTime,String endTime,Integer page,Short status,Integer invoiceType,Integer orderPlatform){
		LOGGER.info("start to execute method <getRetailerOrderListByCondition>!!!!");
		LOGGER.info(" 订单ID"+payId);
		LOGGER.info(" 商品名称"+pName);
		LOGGER.info(" 零售商名称"+retailerName);
		LOGGER.info(" 创建时间"+createTime);
		LOGGER.info(" 结束时间"+endTime);
		LOGGER.info(" 订单状态"+status);
		LOGGER.info(" 发票类型"+invoiceType);
		LOGGER.info(" 订单来源"+orderPlatform+" [0 PC,1 PAD]");
		OrderPayDto orderPayDto = new OrderPayDto();
		OrderPayVO orderPayVO = new OrderPayVO();
		PageBean<OrderPayDto> pageBean = new PageBean<OrderPayDto>();
		if(null != payId){
			orderPayVO.setPayId(payId);
		}
		if(null != invoiceType){
			orderPayVO.setInvoiceType(invoiceType);
		}
		if(null != orderPlatform){
			orderPayVO.setOrderPlatform(orderPlatform);
		}
		if(null != status){
			List<Short> statusList = new ArrayList<Short>();
			statusList.add(status);
			orderPayDto.setStatus(statusList);
		}
		
		if(!Common.isEmpty(retailerName)){
			try {
				RetailerManagerService retailerService = RemoteServiceSingleton.getInstance().getRetailerManagerService();
				List<Long> retailerIds = retailerService.getRetailerIdsByName(retailerName);
				if(retailerIds.size()<1){
					retailerIds.add(0l);
				}
				orderPayDto.setRetailerIds(retailerIds);
			} catch (Exception e) {
				LOGGER.error("RetailerManagerService<getRetailerIdsByName()通过名称模糊查询Id集合> is failed!!!!"+e.getMessage(),e);
			}
		}
		if(!Common.isEmpty(pName)){
			orderPayVO.setpName(pName);
		}
		if(!Common.isEmpty(createTime)){
			orderPayDto.setCreateTime(Common.stringToDate(createTime.replace("-", "/"), "yyyy/MM/dd HH:mm:ss"));
		}
		if(!Common.isEmpty(endTime)){
			orderPayDto.setEndTime(Common.stringToDate(endTime.replace("-", "/"), "yyyy/MM/dd HH:mm:ss"));
		}
		if(page!=null&&page!=0){  //判断条件中是否带着分页码
			pageBean.setPage(page);
		}else {
			pageBean.setPage(1);//没有分页码的话就检索第一页
		}
		orderPayDto.setOrderPayVO(orderPayVO);
		pageBean.setPageSize(Constants.PAGESIZE);
		pageBean.setParameter(orderPayDto);
		try {
			RetailerOrderService retailerOrderService = RemoteServiceSingleton.getInstance().getRetailerOrderService();
			pageBean = retailerOrderService.findSubOrderPayForWofe(pageBean);
			if(null != pageBean){
				List<OrderPayDto> orderList = pageBean.getResult();
				if(null != orderList){
					for (OrderPayDto orderPayDto2 : orderList) {
						if(null != orderPayDto2.getProductDtos() && orderPayDto2.getProductDtos().size()>0 ){
							for (ProductDto dto : orderPayDto2.getProductDtos()) {
								if(dto.getImgUrl()!=null && dto.getImgUrl()!=""){
									if(!dto.getImgUrl().startsWith("http") || !dto.getImgUrl().startsWith("Http")){
										dto.setImgUrl(Constants.P1+dto.getImgUrl());
									}
								}
							}
						}
					}
				}				
			}
			model.addAttribute("pageBean", pageBean);
		} catch (Exception e) {
			LOGGER.error("RetailerOrderService<findOrderPayForWofe()> is failed!!!!"+e.getMessage(),e);
		}
		LOGGER.info("end<> to execute method <getRetailerOrderListByCondition>!!!!");
		return "/dealerseller/B2Border/retailerorder_alllist_model";
	}
	
	*//**.
	 * WOFE补录B2B海外直邮的订单的物流<补录海外直邮订单物流>.
	 * @title updateOverseasOrderLogisticsById
	 * @DATE  2016年8月29日10:36:06
	 * @param  orderId
	 * @param  logisticsCarriersName
	 * @param  logisticsNumber
	 * @param  logisticsCarriersId
	 * @return String
	 *//*
	@RequestMapping(value = "/updateRetailerOrderLogisticsById")
	@ResponseBody
	public String updateRetailerOrderLogisticsById(String orderId,String logisticsCarriersNames,String logisticsNumbers,String logisticsCarriersIds){
		String[] logisticsCarriersNames1 = logisticsCarriersNames.split(",");
		String[] logisticsNumbers1 = logisticsNumbers.split(",");
		String[] logisticsCarriersIds1 = logisticsCarriersIds.split(",");
		LOGGER.info("start to execute <updateOrderLogisticsById()通过订单号修改物流信息>!!!!");
		LOGGER.info("订单编号:"+orderId);
		LOGGER.info("物流商名称:"+logisticsCarriersNames);
		LOGGER.info("物流商编号:"+logisticsCarriersIds);
		LOGGER.info("物流商编码:"+logisticsNumbers);
		
		boolean flag = true;
		boolean boo = false;
		String result = "";
		try {
//			 TODO 设置物流单号需要测试，校验是否可用
			 RetailerOrderService retailerOrderService = RemoteServiceSingleton.getInstance().getRetailerOrderService();
			 // 获取订单信息
			OrderDto orderDTO = retailerOrderService.findRetailerOrderInfoByOrderId(Long.valueOf(orderId));
			// 校验物流单号是否已经存在 
			for (int i = 0; i < logisticsCarriersIds1.length; i++) {
				boolean isExist = retailerOrderService.exitsLogistics(logisticsNumbers1[i], Long.valueOf(logisticsCarriersIds1[i]));
				if (isExist) {
					return "订单物流信息录入失败，请检查物流信息是否已录入。";
				}
			}
			for (int i = 0; i < logisticsCarriersIds1.length; i++) {
			
				// 保存物流信息到海外直邮物流表
				//customerOrderService.modifylogisticsInfo(Long.valueOf(orderId),logisticsNumbers1[i], Long.valueOf(logisticsCarriersIds1[i]), logisticsCarriersNames1[i]);
			
				List<OrderItemDto> orderItemDTOs = orderDTO.getItemlist();

				ShipOrderDTO dto = new ShipOrderDTO();
				dto.setLogisticsCarriersId(Long.valueOf(logisticsCarriersIds1[i]));
				dto.setLogisticsCarriersName(logisticsCarriersNames1[i]);
				dto.setLogisticsNumber(logisticsNumbers1[i]);
				dto.setIsFuture(RetailerConstant.IS_FUTURES_NO);
				dto.setPayId(orderDTO.getPayId());
				dto.setShipperId(Long.valueOf(logisticsCarriersIds1[i]));
				dto.setWarehouseCode(11L);
				dto.setRetailerId(0L);
				dto.setOwnerType((short)0) ;
				dto.setReceiveAddress(orderDTO.getReceiveAddress());
				dto.setReceiveName(orderDTO.getReceiveName()) ;
				dto.setReceivePhone(orderDTO.getReceivePhone()) ;
				dto.setReceiveMobilePhone(orderDTO.getReceiveMobilePhone());
				dto.setReceiveProvinceId(orderDTO.getReceiveProvinceId());
				dto.setReceiveCityId(orderDTO.getReceiveCityId()) ;
				dto.setReceiveAreaId(orderDTO.getReceiveAreaId()) ;
				dto.setMessage(orderDTO.getMessage()) ;
				dto.setCreateBy("sys");
				dto.setLastEditBy("sys");
				dto.setOrderType(2);//b2c
				dto.setOrderId(orderDTO.getId());
				dto.setStatus((short)2);
				int qty = 0 ;
				BigDecimal pirce = new BigDecimal(0);
				List<ShipOrderItemDTO> itemList = new ArrayList<ShipOrderItemDTO>();
				for(OrderItemDto tmp : orderItemDTOs){
					ShipOrderItemDTO item = new ShipOrderItemDTO();
					item.setPid(tmp.getPid());
					item.setSkuId(tmp.getSkuId());
					item.setSkuName(tmp.getSkuName());
					item.setQty(tmp.getSkuQty());
					item.setpName(orderDTO.getpName());
					item.setPirce(tmp.getPrice());
					itemList.add(item);
					qty += tmp.getSkuQty();
					pirce = pirce.add(tmp.getPrice().multiply(new BigDecimal(item.getQty())));
				}
				dto.setPrice(pirce);
				dto.setQty(qty) ;
				dto.setShipItemList(itemList); 
				
				retailerOrderService.insertShipOrder(dto);
			}
			//修改订单状态
			if(orderDTO.getStatus()==(short)41 || orderDTO.getStatus()==(short)71){
				retailerOrderService.updateRetailerOrderStatus(Long.valueOf(orderId), (short)81,"sys",orderDTO.getIsFutures(),new Short[]{1});
				boo=true;
				LOGGER.info("updateRetailerOrderStatus：订单状态修改成功！已发货！");
			}else{
				LOGGER.info("updateRetailerOrderStatus：订单状态修改失败！订单状态不是'待发货'！");
				boo=false;
			}
		} catch (Exception e) {
			flag = false;
			LOGGER.error("customerOrderService.updateOrderLogisticsById()通过订单号修改物流信息 is failed!!!!"+e.getMessage(),e);
		}
		if(boo&&flag){
			result = "1";
		}else{
			result = "订单物流信息录入失败!";
		}
		LOGGER.info("end<> to execute <updateOrderLogisticsById()通过订单号修改物流信息>!!!!");
		return result;
	}

	
	*//**.
	 * 根据订单的编号查询订单的详情.
	 * @param Long payId
	 * @param Model model
	 * @return String
	 *//*
	@RequestMapping(value = "/getRetailerOrderDetailByPayId")
	public String getRetailerOrderDetailByPayId(Long payId,Model model){
		LOGGER.info("start to execute method <getRetailerOrderDetailByPayId>!!!!");
		LOGGER.info("订单号："+payId);
		try {
			RetailerOrderService retailerOrderService = RemoteServiceSingleton.getInstance().getRetailerOrderService();
			OrderPayDetailDto orderPayDetailDto = retailerOrderService.getPayDetailByPayId(payId);
			if(null != orderPayDetailDto){
				if(null != orderPayDetailDto.getOrder()){
					RetailerManagerService retailerService = RemoteServiceSingleton.getInstance().getRetailerManagerService();
					Retailer retailer = retailerService.findByRetailerId(orderPayDetailDto.getOrder().getRetailerId());
					LOGGER.info(orderPayDetailDto.getOrder().getRetailerId()+" ");
					model.addAttribute("retailer", retailer);
				}
			}
			Map<Long, String> imageUrlMap = orderPayDetailDto.getImageUrlMap();
			for(Map.Entry<Long, String> imageUrl : imageUrlMap.entrySet()){
				String imageUrlStr = imageUrl.getValue();
				if(!imageUrlStr.startsWith("http") || !imageUrlStr.startsWith("Http")){
					imageUrlStr = Constants.P1+imageUrlStr;
					imageUrlMap.put(imageUrl.getKey(), imageUrlStr);
				}
			}
			List<ShipOrderDTO> shipOrderList = orderPayDetailDto.getShipOrderDTO();
			if(null != shipOrderList && shipOrderList.size()>0){
				for (ShipOrderDTO shipOrderDTO : shipOrderList) {
					Map<Long, List<ShipOrderItemDTO>> shipOrderItemMap = shipOrderDTO.getShipOrderItemMap();
					for (Map.Entry<Long, List<ShipOrderItemDTO>> shipOrderMap : shipOrderItemMap.entrySet()) {
						if(null != shipOrderMap.getValue() && shipOrderMap.getValue().size()>0){
							for (ShipOrderItemDTO shipOrderItemDTO2 : shipOrderMap.getValue() ) {
								String imageUrl = shipOrderItemDTO2.getProductUrl();
								if(!imageUrl.startsWith("http") || !imageUrl.startsWith("Http")){
									imageUrl = Constants.P1+imageUrl;
									shipOrderItemDTO2.setProductUrl(imageUrl);
								}
							}
						}
					}	
				}
			}
			model.addAttribute("orderPayDetailDto", orderPayDetailDto);
		} catch (Exception e) {
			LOGGER.error("RetailerOrderService<getPayDetailByPayId()根据订单Id查询订单详细信息> is failed!!!!"+e.getMessage(),e);
		}
		LOGGER.info("end<> to execute method <getRetailerOrderDetailByPayId>!!!!");
		return "/dealerseller/B2Border/retailerorder_details";
		
	}
	
	
	*//**
	 * @Description:根据订单编号取消订单.
	 * @title goCancelOrderByWofe
	 * @param Long payId
	 * @return String
	 *//*
	@RequestMapping(value = "/goCancelOrderByWofe")
	@ResponseBody
	public String goCancelOrderByWofe(Long payId){
		LOGGER.info("start to execute method <goCancelOrderByWofe>wofe去取消订单!!!!");
		String createBy = getCurrentUser().getUsername();
		LOGGER.info("订单号："+payId);
		LOGGER.info("创建人："+createBy);
		String result = "";
		try {
			RetailerOrderService retailerOrderService = RemoteServiceSingleton.getInstance().getRetailerOrderService();
			retailerOrderService.cancelOrderByWofe(payId, createBy);
			result = "订单已取消!";
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("RetailerOrderService<cancelOrderByWofe()根据订单Id取消订单> is failed!!!!"+e.getMessage(),e);
			result = "订单取消失败!";
		}
		LOGGER.info("订单取消的结果："+result);
		LOGGER.info("end<> to execute method <goCancelOrderByWofe>wofe去取消订单!!!!");
		return result;
	}	
	

	*//**
	 * wofe导出期货现货订单.
	 * @param payId
	 * @param retailerName
	 * @param pName
	 * @param createTime
	 * @param endTime
	 * @param page
	 * @param status
	 * @param invoiceType
	 *//*
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/exportOrderListByConditionExcel")
	public void exportOrderListByConditionExcel(HttpServletResponse response,Long payId,String retailerName,String pName,
			String createTime,String endTime,Integer page,Short status,Integer invoiceType,Integer isFutuere,Integer orderPlatform){
		LOGGER.info("start to execute method <getRetailerOrderListByCondition>!!!!");
		LOGGER.info(" 订单ID"+payId);
		LOGGER.info(" 商品名称"+pName);
		LOGGER.info(" 零售商名称"+retailerName);
		LOGGER.info(" 创建时间"+createTime);
		LOGGER.info(" 结束时间"+endTime);
		LOGGER.info(" 订单状态"+status);
		LOGGER.info(" 发票类型"+invoiceType);
		LOGGER.info(" 期货1现货0"+isFutuere);
		LOGGER.info(" 订单来源"+orderPlatform+" [0 PC,1 PAD]");
		OrderDto orderPayVO = new OrderDto();
		PageBean<OrderDto> pageBean = new PageBean<OrderDto>();
		if(null != payId){
			orderPayVO.setPayId(payId);
		}
		if(null != invoiceType){
			orderPayVO.setInvoiceType(invoiceType);
		}
		if(null != orderPlatform){
			orderPayVO.setOrderPlatform(orderPlatform);
		}
		if(null != status){
			List<Short> statusList = new ArrayList<Short>();
			statusList.add(status);
			orderPayVO.setState(statusList);
		}
		if(isFutuere == 0){
			orderPayVO.setIsFutures(0);
		}
		if(isFutuere==1){
			orderPayVO.setIsFutures(1);
		}
		
		if(!Common.isEmpty(retailerName)){
			try {
				RetailerManagerService retailerService = RemoteServiceSingleton.getInstance().getRetailerManagerService();
				List<Long> retailerIds = retailerService.getRetailerIdsByName(retailerName);
				if(retailerIds.size()<1){
					retailerIds.add(0l);
				}
				orderPayVO.setRetailerIds(retailerIds);
			} catch (Exception e) {
				LOGGER.error("RetailerManagerService<getRetailerIdsByName()通过名称模糊查询Id集合> is failed!!!!"+e.getMessage(),e);
			}
		}
		if(!Common.isEmpty(pName)){
			orderPayVO.setpName(pName);
		}
		if(!Common.isEmpty(createTime)){
			orderPayVO.setCreateTime(Common.stringToDate(createTime.replace("-", "/"), "yyyy/MM/dd HH:mm:ss"));
		}
		if(!Common.isEmpty(endTime)){
			orderPayVO.setEndTime(Common.stringToDate(endTime.replace("-", "/"), "yyyy/MM/dd HH:mm:ss"));
		}
		orderPayVO.setQueryType(1);
		pageBean.setParameter(orderPayVO);
		pageBean.setPageSize(Constants.MAXPAGESIZE);
		
		HSSFWorkbook book = new HSSFWorkbook();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyyMMdd HHmmss");
		HSSFSheet sheet = book.createSheet("Order Inquiry-Order Details");
		HSSFSheet sheet1 = book.createSheet("Order Inquiry-Sales Summary");
		
		 // 另一个字体样式    
	    HSSFFont columnHeadFont = book.createFont();    
	    columnHeadFont.setFontName("Times New Roman");    
	    columnHeadFont.setFontHeightInPoints((short) 12);    
	   // columnHeadFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); 
	    
		// 列头的样式    
	    HSSFCellStyle columnHeadStyle = book.createCellStyle();    
	    columnHeadStyle.setFont(columnHeadFont);    
	    columnHeadStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中    
	    columnHeadStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中    
	    columnHeadStyle.setLocked(true);    
	    columnHeadStyle.setWrapText(true); 
	    columnHeadStyle.setBorderTop((short) 1);
	    columnHeadStyle.setTopBorderColor(HSSFColor.BLACK.index);
	    columnHeadStyle.setBorderLeft((short) 1);// 边框的大小
	    columnHeadStyle.setLeftBorderColor(HSSFColor.BLACK.index);// 左边框的颜色    
	    columnHeadStyle.setBorderLeft((short) 1);// 边框的大小    
	    columnHeadStyle.setRightBorderColor(HSSFColor.BLACK.index);// 右边框的颜色    
	    columnHeadStyle.setBorderRight((short) 1);// 边框的大小    
	    columnHeadStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体    
	    columnHeadStyle.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色    
	    columnHeadStyle.setFillForegroundColor(HSSFColor.BLUE.index);// 设置单元格的背景颜色．   
	    columnHeadStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	    columnHeadStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    
	    HSSFFont font = book.createFont();    
	    font.setFontName("Times New Roman");    
	    font.setFontHeightInPoints((short) 10);    
	    // 普通单元格样式    
	    HSSFCellStyle style = book.createCellStyle();    
	    style.setFont(font);    
	    style.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 左右居中    
	    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);// 上下居中    
	    style.setWrapText(true);    
	    style.setLeftBorderColor(HSSFColor.BLACK.index);    
	    style.setBorderLeft((short) 1);    
	    style.setRightBorderColor(HSSFColor.BLACK.index);    
	    style.setBorderRight((short) 1);    
	   // style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体    
	    style.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色．    
	    style.setFillForegroundColor(HSSFColor.WHITE.index);// 设置单元格的背景颜色．
	  	sheet.setColumnWidth(0, 5000);    
	    sheet.setColumnWidth(1, 5000);    
	    sheet.setColumnWidth(2, 4000);    
	    sheet.setColumnWidth(3, 10000);    
	    sheet.setColumnWidth(4, 5000);    
	    sheet.setColumnWidth(5, 3000);    
	    sheet.setColumnWidth(6, 3000);  
	    sheet.setColumnWidth(7, 3000); 
	    sheet.setColumnWidth(8, 3000); 
	    sheet.setColumnWidth(9, 3000); 
	    sheet.setColumnWidth(10, 3000); 
	    sheet.setColumnWidth(11, 5000);
	    sheet.setColumnWidth(12, 4000); 
	    sheet.setColumnWidth(13, 5000);
	    sheet.setColumnWidth(14, 5000);
	    sheet.setColumnWidth(15, 8000);
	    sheet.setColumnWidth(16, 5000); 
	    sheet.setColumnWidth(17, 3000); 
	    sheet.setColumnWidth(18, 3000);
	    sheet.setColumnWidth(19, 3000);
	    sheet.setColumnWidth(20, 3000);
	    sheet.setColumnWidth(21, 5000);
	    sheet.setColumnWidth(22, 5000);
	    sheet.setColumnWidth(23, 3000);
	    
	  	sheet1.setColumnWidth(0, 5000);    
	    sheet1.setColumnWidth(1, 5000);    
	    sheet1.setColumnWidth(2, 4000);    
	    sheet1.setColumnWidth(3, 10000);    
	    sheet1.setColumnWidth(4, 5000);    
	    sheet1.setColumnWidth(5, 3000);    
	    sheet1.setColumnWidth(6, 3000);    
	    sheet1.setColumnWidth(7, 3000);
	    sheet1.setColumnWidth(8, 3000); 
	    sheet1.setColumnWidth(9, 3000); 
	    sheet1.setColumnWidth(10, 3000); 
	    sheet1.setColumnWidth(11, 5000);
	    sheet1.setColumnWidth(12, 4000); 
	    sheet1.setColumnWidth(13, 5000);
	    sheet1.setColumnWidth(14, 3000);
	    sheet1.setColumnWidth(15, 3000);
	    sheet1.setColumnWidth(16, 3000); 
	    sheet1.setColumnWidth(17, 3000); 
	    sheet1.setColumnWidth(18, 5000);
	    sheet1.setColumnWidth(19, 5000);
	    sheet1.setColumnWidth(20, 5000);
	    sheet1.setColumnWidth(21, 5000);
	    sheet1.setColumnWidth(22, 1000);
		    
		HSSFRow row = sheet.createRow((int)0);
		HSSFRow row1 = sheet1.createRow((int)0);
		HSSFCell cell1=null;
		HSSFCell cell=null;
		String [] strtitle={"订单号","收货人","联系电话","收货地址","零售商","零售商ID","用户名称","用户类型","省","市","区","详细地址","销售人员","商品ID","国际条码","商品名称","规格","单价(元)","数量","小计(元)","交易状态","下单时间","支付时间","订单来源"};
		for(int i=0;i<strtitle.length;i++){
			cell=row.createCell(i);
			cell.setCellValue(strtitle[i]);
			cell.setCellStyle(columnHeadStyle);
		}
		String [] strtitle1={"订单号","收货人","联系电话","收货地址","零售商","零售商ID","用户名称","用户类型","省","市","区","详细地址","销售人员","购买商品数","订单总计(元)","折扣额(元)","运费收入(元)","实付金额(元)","交易状态","下单时间","付款时间","订单来源"};
		for(int i=0;i<strtitle1.length;i++){
			cell1=row1.createCell(i);
			cell1.setCellValue(strtitle1[i]);
			cell1.setCellStyle(columnHeadStyle);
		}
		try {
			RetailerOrderExportService dealerOrderService = RemoteServiceSingleton.getInstance().getRetailerOrderExportService();
			pageBean = dealerOrderService.exportRetailerOrders(pageBean);
			List<OrderDto> orderList = pageBean.getResult();
			if(null != orderList && orderList.size()>0){
				List<Long> RetailerIds = new ArrayList<Long>();
				
				Map<Long, OrderDto> orderMaps = new LinkedHashMap<Long, OrderDto>();
				for (OrderDto retailerOrderDto : orderList) {
					RetailerIds.add(retailerOrderDto.getRetailerId());
					Integer qty = retailerOrderDto.getQty();
					BigDecimal orderPrice = retailerOrderDto.getOrderPrice();
					BigDecimal paidPrice = retailerOrderDto.getPaidPrice();
					BigDecimal discountPrice = retailerOrderDto.getDiscountPrice();
					BigDecimal realTransferprice = retailerOrderDto.getRealTransferprice();
					
					if(orderMaps.containsKey(retailerOrderDto.getPayId())){
						Integer qty1 = orderMaps.get(retailerOrderDto.getPayId()).getQty();
						BigDecimal orderPrice1 = orderMaps.get(retailerOrderDto.getPayId()).getOrderPrice();
						BigDecimal paidPrice1 = orderMaps.get(retailerOrderDto.getPayId()).getPaidPrice();
						BigDecimal discountPrice1 = orderMaps.get(retailerOrderDto.getPayId()).getDiscountPrice();
						BigDecimal realTransferprice1 = orderMaps.get(retailerOrderDto.getPayId()).getRealTransferprice();
						retailerOrderDto.setQty(qty+qty1);
						retailerOrderDto.setOrderPrice(orderPrice.add(orderPrice1));
						retailerOrderDto.setPaidPrice(paidPrice.add(paidPrice1));
						retailerOrderDto.setDiscountPrice(discountPrice.add(discountPrice1));
						retailerOrderDto.setRealTransferprice(realTransferprice.add(realTransferprice1));
						orderMaps.put(retailerOrderDto.getPayId(), retailerOrderDto);
					}else{
						orderMaps.put(retailerOrderDto.getPayId(), retailerOrderDto);
					}	
				}
				RetailerManagerService retailerService = RemoteServiceSingleton.getInstance().getRetailerManagerService();
				Map<Long, RetailerAddressDTO> map = retailerService.getFindRetailerDtosByIds(RetailerIds);
				PlatformSaleManagerService platformSaleManagerService = RemoteServiceSingleton.getInstance().getPlatformSaleManagerService();
				List<PlatformSale> platformSales = platformSaleManagerService.getAllSale();
				for(Map.Entry<Long,RetailerAddressDTO> entry1: map.entrySet()) {
					entry1.getValue().getCityId();
				}
				if(null != map && map.size()>0){
					if(platformSales != null && platformSales.size()>0){
						for(Map.Entry<Long,RetailerAddressDTO> entry: map.entrySet()) {
							 for (PlatformSale platformSale : platformSales) {
								 if(entry.getValue().getSaleId() != null){
									 if(entry.getValue().getSaleId().equals(platformSale.getSaleId())){
										 entry.getValue().setRetailerCode(platformSale.getSaleName());
									 }
								 }else{
									 entry.getValue().setRetailerCode("");
								 }
							}
						}
					} 
				}
				int r = 1;
				int b = 1;
				for(Map.Entry<Long, OrderDto> entry2:orderMaps.entrySet()){
					String platform = "";
					if(entry2.getValue().getOrderPlatform().equals(RetailerConstant.PLATFORM_PC)){
						platform = "PC";
					}
					if(entry2.getValue().getOrderPlatform().equals(RetailerConstant.PLATFORM_PAD)){
						platform = "PAD";
					}
					String createtime = simpleDateFormat.format(entry2.getValue().getCreateTime());
					String payTime = "";
					if(entry2.getValue().getPayTime()!=null){
						payTime = simpleDateFormat.format(entry2.getValue().getPayTime());
					}
					String statu = "";
					switch (entry2.getValue().getStatus()) {
					case 1:	
						statu = "待支付";
						break;
					case 19:	
						statu = "待付款确认(网银转账)";
						break;
					case 20:	
						statu = "待付款确认(账期付款)";
						break;
					case 21:
						if(isFutuere.intValue() == 0){
							statu = "已付款";
						}else {
							statu = "已付首款";
						}
						break;
					case 31:
						statu = "等待WOFE合单";
						break;
					case 32:
						statu = "等待WOFE合单";
						break;
					case 33:
						statu = "等待WOFE合单";
						break;
					case 34:
						statu = "等待WOFE合单";
						break;
					case 41:
						statu = "等待供应商发货";
						break;
					case 51:
						statu = "供应商已发货";
						break;
					case 61:
						statu = "等待零售商交付余款";
						break;
					case 71:
						statu = "等待经销商发货";
						break;
					case 81:
						statu = "经销商已发货";
						break;
					case 91:
						statu = "订单完成";
						break;
					case 100:
						statu = "系统自动取消";
						break;
					case 101:
						statu = "系统默认完成";
						break;
					case 111:
						statu = "买家取消";
						break;
					case 112:
						statu = "客服取消";
						break;
					}
					row1 = sheet1.createRow(b++);
					row1.createCell(Constants.SHORT0).setCellValue(entry2.getKey()+"");
					row1.createCell(Constants.SHORT1).setCellValue(entry2.getValue().getReceiveName());
					row1.createCell(Constants.SHORT2).setCellValue(entry2.getValue().getReceiveMobilePhone());
					row1.createCell(Constants.SHORT3).setCellValue(entry2.getValue().getReceiveAddress());
					row1.createCell(Constants.SHORT5).setCellValue(entry2.getValue().getRetailerId());
					row1.createCell(Constants.SHORT6).setCellValue(entry2.getValue().getCreateBy());
					if(map.containsKey(entry2.getValue().getRetailerId())){
						//商户类型 1 商业级别商户 2 普通商户 3 支付宝登陆用户 4 qq登录用户 5 微博登录用户 6 微信登陆用户
						String merchantType = "";
						if(map.get(entry2.getValue().getRetailerId()).getMerchantType() == 1){
							merchantType = "商业级别商户";
						}else{
							merchantType = "会员用户";
						}
						row1.createCell(Constants.SHORT4).setCellValue(map.get(entry2.getValue().getRetailerId()).getName());
						row1.createCell(Constants.SHORT7).setCellValue(merchantType);
						row1.createCell(Constants.SHORT8).setCellValue(map.get(entry2.getValue().getRetailerId()).getProvinceName());
						row1.createCell(Constants.SHORT9).setCellValue(map.get(entry2.getValue().getRetailerId()).getCityName());
						row1.createCell(Constants.SHORT10).setCellValue(map.get(entry2.getValue().getRetailerId()).getAreaName());
						row1.createCell(Constants.SHORT11).setCellValue(map.get(entry2.getValue().getRetailerId()).getAddress());
					}else{
						row1.createCell(Constants.SHORT8).setCellValue("");
						row1.createCell(Constants.SHORT9).setCellValue("");
						row1.createCell(Constants.SHORT10).setCellValue("");
					}
					if(map.get(entry2.getValue().getRetailerId()).getSaleId()==null){
						row1.createCell(Constants.SHORT12).setCellValue("");
					}else{
						if(map.containsKey(entry2.getValue().getRetailerId())){
							row1.createCell(Constants.SHORT12).setCellValue(map.get(entry2.getValue().getRetailerId()).getRetailerCode());
						}
					}
					row1.createCell(Constants.SHORT13).setCellValue(entry2.getValue().getQty()+"");
					row1.createCell(Constants.SHORT14).setCellValue(entry2.getValue().getOrderPrice()+"");
					row1.createCell(Constants.SHORT15).setCellValue(entry2.getValue().getDiscountPrice()+"");
					row1.createCell(Constants.SHORT16).setCellValue(entry2.getValue().getRealTransferprice()+"");
					row1.createCell(Constants.SHORT17).setCellValue(entry2.getValue().getPaidPrice()+"");
					row1.createCell(Constants.SHORT18).setCellValue(statu);
					row1.createCell(Constants.SHORT19).setCellValue(createtime);
					row1.createCell(Constants.SHORT20).setCellValue(payTime);
					row1.createCell(Constants.SHORT21).setCellValue(platform);
				}
				for (OrderDto retailerOrderDto : orderList) {
					String platform = "";
					if(retailerOrderDto.getOrderPlatform().equals(RetailerConstant.PLATFORM_PC)){
						platform = "PC";
					}
					if(retailerOrderDto.getOrderPlatform().equals(RetailerConstant.PLATFORM_PAD)){
						platform = "PAD";
					}
					String createtime = simpleDateFormat.format(retailerOrderDto.getCreateTime());
					String payTime = "";
					if(retailerOrderDto.getPayTime()!=null){
						payTime = simpleDateFormat.format(retailerOrderDto.getPayTime());
					}
					String statu = "";
					switch (retailerOrderDto.getStatus()) {
					case 1:	
						statu = "待支付";
						break;
					case 19:	
						statu = "待付款确认(网银转账)";
						break;
					case 20:	
						statu = "待付款确认(账期付款)";
						break;
					case 21:
						if(isFutuere.intValue() == 0){
							statu = "已付款";
						}else {
							statu = "已付首款";
						}
						break;
					case 31:
						statu = "等待WOFE合单";
						break;
					case 32:
						statu = "等待WOFE合单";
						break;
					case 33:
						statu = "等待WOFE合单";
						break;
					case 34:
						statu = "等待WOFE合单";
						break;
					case 41:
						statu = "等待供应商发货";
						break;
					case 51:
						statu = "供应商已发货";
						break;
					case 61:
						statu = "等待零售商交付余款";
						break;
					case 71:
						statu = "等待经销商发货";
						break;
					case 81:
						statu = "经销商已发货";
						break;
					case 91:
						statu = "订单完成";
						break;
					case 100:
						statu = "系统自动取消";
						break;
					case 101:
						statu = "系统默认完成";
						break;
					case 111:
						statu = "买家取消";
						break;
					case 112:
						statu = "客服取消";
						break;
					}
					List<OrderItemDto> orderItemList = retailerOrderDto.getItemlist();
					for (OrderItemDto retailerOrderItemDto : orderItemList) {
						row = sheet.createRow(r++);
						String totalPrice = retailerOrderItemDto.getPrice().multiply(new BigDecimal(retailerOrderItemDto.getSkuQty()))+"";
						row.createCell(Constants.SHORT0).setCellValue(retailerOrderDto.getPayId()+"");
						row.createCell(Constants.SHORT1).setCellValue(retailerOrderDto.getReceiveName());
						row.createCell(Constants.SHORT2).setCellValue(retailerOrderDto.getReceiveMobilePhone());
						row.createCell(Constants.SHORT3).setCellValue(retailerOrderDto.getReceiveAddress());
						row.createCell(Constants.SHORT5).setCellValue(retailerOrderDto.getRetailerId());
						row.createCell(Constants.SHORT6).setCellValue(retailerOrderDto.getCreateBy());
						if(map.containsKey(retailerOrderDto.getRetailerId())){
							//商户类型 1 商业级别商户 2 普通商户 3 支付宝登陆用户 4 qq登录用户 5 微博登录用户 6 微信登陆用户
							String merchantType = "";
							if(map.get(retailerOrderDto.getRetailerId()).getMerchantType() == 1){
								merchantType = "商业级别商户";
							}else{
								merchantType = "会员用户";
							}
							row.createCell(Constants.SHORT7).setCellValue(merchantType);
							row.createCell(Constants.SHORT4).setCellValue(map.get(retailerOrderDto.getRetailerId()).getName());
							row.createCell(Constants.SHORT8).setCellValue(map.get(retailerOrderDto.getRetailerId()).getProvinceName());
							row.createCell(Constants.SHORT9).setCellValue(map.get(retailerOrderDto.getRetailerId()).getCityName());
							row.createCell(Constants.SHORT10).setCellValue(map.get(retailerOrderDto.getRetailerId()).getAreaName());
							row.createCell(Constants.SHORT11).setCellValue(map.get(retailerOrderDto.getRetailerId()).getAddress());	
						}else{
							row.createCell(Constants.SHORT8).setCellValue("");
							row.createCell(Constants.SHORT9).setCellValue("");
							row.createCell(Constants.SHORT10).setCellValue("");
						}
						if(map.get(retailerOrderDto.getRetailerId()).getSaleId()==null){
							row.createCell(Constants.SHORT12).setCellValue("");
						}else{
							if(map.containsKey(retailerOrderDto.getRetailerId())){
								row.createCell(Constants.SHORT12).setCellValue(map.get(retailerOrderDto.getRetailerId()).getRetailerCode());
							}
						}
						row.createCell(Constants.SHORT13).setCellValue(retailerOrderDto.getPid()+"");
						row.createCell(Constants.SHORT14).setCellValue(retailerOrderItemDto.getSkuCode());
						row.createCell(Constants.SHORT15).setCellValue(retailerOrderDto.getpName());
						row.createCell(Constants.SHORT16).setCellValue(retailerOrderItemDto.getSkuName());
						row.createCell(Constants.SHORT17).setCellValue(retailerOrderItemDto.getPrice()+"");
						row.createCell(Constants.SHORT18).setCellValue(retailerOrderItemDto.getSkuQty());
						row.createCell(Constants.SHORT19).setCellValue(totalPrice);
						row.createCell(Constants.SHORT20).setCellValue(statu);
						row.createCell(Constants.SHORT21).setCellValue(createtime);
						row.createCell(Constants.SHORT22).setCellValue(payTime);
						row.createCell(23).setCellValue(platform);
					}	
				}
			}
		} catch (Exception e) {
			LOGGER.error("DealerOrderService <findRetailerOrder()> is failed !!!!"+e.getMessage(),e);
		}
		try {
			String filename = "retailer-order"+simpleDateFormat1.format(new Date());
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setCharacterEncoding("UTF-8");
			filename = java.net.URLEncoder.encode(filename,"gb2312");
			response.setHeader("Content-Disposition","attachment;filename="+
					new String(filename.getBytes("UTF-8"),"GBK")+".xls");
			response.setContentType("application/msexcel");//定义输出类型	
			book.write(os);  	
			response.getOutputStream().flush();
			response.getOutputStream().close();	
		} catch (Exception e) {
		}
		LOGGER.info("end<> to execute method <exportPackageListByConditionExcel>!!!!");
	}
	*//**
	 * 初始化加载所有B取消订单(未发货)
	 * @return
	 *//*
	@RequestMapping(value = "/retailerOrderByUnfilledList")
	public String retailerOrderByUnfilledList(){
		LOGGER.info("初始化加载所有零售商订单列表!!!!");
		System.out.println();
		return "/dealerseller/B2Border/retailerorder_cancellist";
	}
	
	*//**
	 * B订单取消数据列表
	 * @param model
	 * @param payId
	 * @param retailerName
	 * @param pName
	 * @param createTime
	 * @param endTime
	 * @param page
	 * @param status
	 * @param invoiceType
	 * @param orderPlatform
	 * @return
	 *//*
	@RequestMapping(value = "/getRetailerOrderListByCancel")
	public String getRetailerOrderListByCancel(Model model,Long payId,String retailerName,String pName,
			String createTime,String endTime,Integer page,Short status,Integer invoiceType,Integer orderPlatform,String statusList){
		LOGGER.info("start to execute method <getRetailerOrderListByCondition>!!!!");
		LOGGER.info(" 订单ID"+payId);
		LOGGER.info(" 商品名称"+pName);
		LOGGER.info(" 零售商名称"+retailerName);
		LOGGER.info(" 创建时间"+createTime);
		LOGGER.info(" 结束时间"+endTime);
		LOGGER.info(" 订单状态"+status);
		LOGGER.info(" 发票类型"+invoiceType);
		LOGGER.info(" 订单来源"+orderPlatform+" [0 PC,1 PAD]");
		LOGGER.info("状态列表:"+statusList);
		OrderPayDto orderPayDto = new OrderPayDto();
		OrderPayVO orderPayVO = new OrderPayVO();
		PageBean<OrderPayDto> pageBean = new PageBean<OrderPayDto>();
		if(!Common.isEmpty(statusList)){
			List<Short> statusAll = new ArrayList<Short>();
			String[] statusArray = statusList.split(",");
			for(String statusSimple:statusArray){
				try {
					statusAll.add(Short.parseShort(statusSimple));
				} catch (NumberFormatException e) {
					LOGGER.error("getRetailerOrderListByCancel is failed!!!!数字转换异常  status:"+status,e);
				}
			}
			orderPayDto.setStatus(statusAll);
		}
		if(null != payId){
			orderPayVO.setPayId(payId);
		}
		if(null != invoiceType){
			orderPayVO.setInvoiceType(invoiceType);
		}
		if(null != orderPlatform){
			orderPayVO.setOrderPlatform(orderPlatform);
		}
		if(null != status){
			List<Short> statuslist = new ArrayList<Short>();
			if(status.equals((short)-1)){
				statuslist.add((short)21);
				statuslist.add((short)41);
				statuslist.add((short)81);
			}else{
				statuslist.add(status);
			}		
			orderPayDto.setStatus(statuslist);
		}
		
		if(!Common.isEmpty(retailerName)){
			try {
				RetailerManagerService retailerService = RemoteServiceSingleton.getInstance().getRetailerManagerService();
				List<Long> retailerIds = retailerService.getRetailerIdsByName(retailerName);
				if(retailerIds.size()<1){
					retailerIds.add(0l);
				}
				orderPayDto.setRetailerIds(retailerIds);
			} catch (Exception e) {
				LOGGER.error("RetailerManagerService<getRetailerIdsByName()通过名称模糊查询Id集合> is failed!!!!"+e.getMessage(),e);
			}
		}
		if(!Common.isEmpty(pName)){
			orderPayVO.setpName(pName);
		}
		if(!Common.isEmpty(createTime)){
			orderPayDto.setCreateTime(Common.stringToDate(createTime.replace("-", "/"), "yyyy/MM/dd HH:mm:ss"));
		}
		if(!Common.isEmpty(endTime)){
			orderPayDto.setEndTime(Common.stringToDate(endTime.replace("-", "/"), "yyyy/MM/dd HH:mm:ss"));
		}
		if(page!=null&&page!=0){  //判断条件中是否带着分页码
			pageBean.setPage(page);
		}else {
			pageBean.setPage(1);//没有分页码的话就检索第一页
		}
		orderPayDto.setOrderPayVO(orderPayVO);
		pageBean.setPageSize(Constants.PAGESIZE);
		pageBean.setParameter(orderPayDto);
		try {
			RetailerOrderService retailerOrderService = RemoteServiceSingleton.getInstance().getRetailerOrderService();
			pageBean = retailerOrderService.findOrderPayForWofeAndOrderCancelStatusNew(pageBean);
			if(null != pageBean){
				List<OrderPayDto> orderList = pageBean.getResult();
				if(null != orderList){
					for (OrderPayDto orderPayDto2 : orderList) {
						if(null != orderPayDto2.getProductDtos() && orderPayDto2.getProductDtos().size()>0 ){
							for (ProductDto dto : orderPayDto2.getProductDtos()) {
								if(dto.getImgUrl()!=null && dto.getImgUrl()!=""){
									if(!dto.getImgUrl().startsWith("http") || !dto.getImgUrl().startsWith("Http")){
										dto.setImgUrl(Constants.P1+dto.getImgUrl());
									}
								}
							}
						}
					}
				}				
			}
			model.addAttribute("pageBean", pageBean);
		} catch (Exception e) {
			LOGGER.error("RetailerOrderService<findOrderPayForWofe()> is failed!!!!"+e.getMessage(),e);
		}
		LOGGER.info("end<> to execute method <getRetailerOrderListByCondition>!!!!");
		return "/dealerseller/B2Border/retailerorder_cancellist_model";
	}
	*//**
	 * B订单取消导出excel
	 * @param response
	 * @param payId
	 * @param retailerName
	 * @param pName
	 * @param createTime
	 * @param endTime
	 * @param page
	 * @param status
	 * @param invoiceType
	 * @param isFutuere
	 * @param orderPlatform
	 *//*
	@RequestMapping(value = "/exportOrderListByCancelExcel")
	public void exportOrderListByCancelExcel(HttpServletResponse response,Long payId,String retailerName,String pName,
			String createTime,String endTime,Integer page,Short status,Integer invoiceType,Integer isFutuere,Integer orderPlatform){
		LOGGER.info("start to execute method <getRetailerOrderListByCondition>!!!!");
		LOGGER.info(" 订单ID"+payId);
		LOGGER.info(" 商品名称"+pName);
		LOGGER.info(" 零售商名称"+retailerName);
		LOGGER.info(" 创建时间"+createTime);
		LOGGER.info(" 结束时间"+endTime);
		LOGGER.info(" 订单状态"+status);
		LOGGER.info(" 发票类型"+invoiceType);
		LOGGER.info(" 期货1现货0"+isFutuere);
		LOGGER.info(" 订单来源"+orderPlatform+" [0 PC,1 PAD]");
		OrderDto orderPayVO = new OrderDto();
		PageBean<OrderDto> pageBean = new PageBean<OrderDto>();
		if(null != payId){
			orderPayVO.setPayId(payId);
		}
		if(null != invoiceType){
			orderPayVO.setInvoiceType(invoiceType);
		}
		if(null != orderPlatform){
			orderPayVO.setOrderPlatform(orderPlatform);
		}
		if(null != status){
			List<Short> statuslist = new ArrayList<Short>();
			if(status.equals((short)-1)){
				statuslist.add((short)21);
				statuslist.add((short)41);
			}else{
				statuslist.add(status);
			}
			orderPayVO.setState(statuslist);
		}
		if(isFutuere == 0){
			orderPayVO.setIsFutures(0);
		}
		if(isFutuere==1){
			orderPayVO.setIsFutures(1);
		}
		
		if(!Common.isEmpty(retailerName)){
			try {
				RetailerManagerService retailerService = RemoteServiceSingleton.getInstance().getRetailerManagerService();
				List<Long> retailerIds = retailerService.getRetailerIdsByName(retailerName);
				if(retailerIds.size()<1){
					retailerIds.add(0l);
				}
				orderPayVO.setRetailerIds(retailerIds);
			} catch (Exception e) {
				LOGGER.error("RetailerManagerService<getRetailerIdsByName()通过名称模糊查询Id集合> is failed!!!!"+e.getMessage(),e);
			}
		}
		if(!Common.isEmpty(pName)){
			orderPayVO.setpName(pName);
		}
		if(!Common.isEmpty(createTime)){
			orderPayVO.setCreateTime(Common.stringToDate(createTime.replace("-", "/"), "yyyy/MM/dd HH:mm:ss"));
		}
		if(!Common.isEmpty(endTime)){
			orderPayVO.setEndTime(Common.stringToDate(endTime.replace("-", "/"), "yyyy/MM/dd HH:mm:ss"));
		}
		orderPayVO.setQueryType(1);
		pageBean.setParameter(orderPayVO);
		pageBean.setPageSize(Constants.MAXPAGESIZE);
		
		HSSFWorkbook book = new HSSFWorkbook();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyyMMdd HHmmss");
		HSSFSheet sheet = book.createSheet("Order Inquiry-Order Details");
		HSSFSheet sheet1 = book.createSheet("Order Inquiry-Sales Summary");
		
		 // 另一个字体样式    
	    HSSFFont columnHeadFont = book.createFont();    
	    columnHeadFont.setFontName("Times New Roman");    
	    columnHeadFont.setFontHeightInPoints((short) 12);    
	   // columnHeadFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); 
	    
		// 列头的样式    
	    HSSFCellStyle columnHeadStyle = book.createCellStyle();    
	    columnHeadStyle.setFont(columnHeadFont);    
	    columnHeadStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中    
	    columnHeadStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中    
	    columnHeadStyle.setLocked(true);    
	    columnHeadStyle.setWrapText(true); 
	    columnHeadStyle.setBorderTop((short) 1);
	    columnHeadStyle.setTopBorderColor(HSSFColor.BLACK.index);
	    columnHeadStyle.setBorderLeft((short) 1);// 边框的大小
	    columnHeadStyle.setLeftBorderColor(HSSFColor.BLACK.index);// 左边框的颜色    
	    columnHeadStyle.setBorderLeft((short) 1);// 边框的大小    
	    columnHeadStyle.setRightBorderColor(HSSFColor.BLACK.index);// 右边框的颜色    
	    columnHeadStyle.setBorderRight((short) 1);// 边框的大小    
	    columnHeadStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体    
	    columnHeadStyle.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色    
	    columnHeadStyle.setFillForegroundColor(HSSFColor.BLUE.index);// 设置单元格的背景颜色．   
	    columnHeadStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	    columnHeadStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    
	    HSSFFont font = book.createFont();    
	    font.setFontName("Times New Roman");    
	    font.setFontHeightInPoints((short) 10);    
	    // 普通单元格样式    
	    HSSFCellStyle style = book.createCellStyle();    
	    style.setFont(font);    
	    style.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 左右居中    
	    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);// 上下居中    
	    style.setWrapText(true);    
	    style.setLeftBorderColor(HSSFColor.BLACK.index);    
	    style.setBorderLeft((short) 1);    
	    style.setRightBorderColor(HSSFColor.BLACK.index);    
	    style.setBorderRight((short) 1);    
	   // style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体    
	    style.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色．    
	    style.setFillForegroundColor(HSSFColor.WHITE.index);// 设置单元格的背景颜色．
	  	sheet.setColumnWidth(0, 5000);    
	    sheet.setColumnWidth(1, 5000);    
	    sheet.setColumnWidth(2, 4000);    
	    sheet.setColumnWidth(3, 10000);    
	    sheet.setColumnWidth(4, 5000);    
	    sheet.setColumnWidth(5, 3000);    
	    sheet.setColumnWidth(6, 3000);  
	    sheet.setColumnWidth(7, 3000); 
	    sheet.setColumnWidth(8, 3000); 
	    sheet.setColumnWidth(9, 3000); 
	    sheet.setColumnWidth(10, 3000); 
	    sheet.setColumnWidth(11, 5000);
	    sheet.setColumnWidth(12, 4000); 
	    sheet.setColumnWidth(13, 5000);
	    sheet.setColumnWidth(14, 5000);
	    sheet.setColumnWidth(15, 8000);
	    sheet.setColumnWidth(16, 5000); 
	    sheet.setColumnWidth(17, 3000); 
	    sheet.setColumnWidth(18, 3000);
	    sheet.setColumnWidth(19, 3000);
	    sheet.setColumnWidth(20, 3000);
	    sheet.setColumnWidth(21, 5000);
	    sheet.setColumnWidth(22, 5000);
	    sheet.setColumnWidth(23, 3000);
	    
	  	sheet1.setColumnWidth(0, 5000);    
	    sheet1.setColumnWidth(1, 5000);    
	    sheet1.setColumnWidth(2, 4000);    
	    sheet1.setColumnWidth(3, 10000);    
	    sheet1.setColumnWidth(4, 5000);    
	    sheet1.setColumnWidth(5, 3000);    
	    sheet1.setColumnWidth(6, 3000);    
	    sheet1.setColumnWidth(7, 3000);
	    sheet1.setColumnWidth(8, 3000); 
	    sheet1.setColumnWidth(9, 3000); 
	    sheet1.setColumnWidth(10, 3000); 
	    sheet1.setColumnWidth(11, 5000);
	    sheet1.setColumnWidth(12, 4000); 
	    sheet1.setColumnWidth(13, 5000);
	    sheet1.setColumnWidth(14, 3000);
	    sheet1.setColumnWidth(15, 3000);
	    sheet1.setColumnWidth(16, 3000); 
	    sheet1.setColumnWidth(17, 3000); 
	    sheet1.setColumnWidth(18, 5000);
	    sheet1.setColumnWidth(19, 5000);
	    sheet1.setColumnWidth(20, 5000);
	    sheet1.setColumnWidth(21, 5000);
	    sheet1.setColumnWidth(22, 1000);
		    
		HSSFRow row = sheet.createRow((int)0);
		HSSFRow row1 = sheet1.createRow((int)0);
		HSSFCell cell1=null;
		HSSFCell cell=null;
		String [] strtitle={"订单号","收货人","联系电话","收货地址","零售商","零售商ID","用户名称","用户类型","省","市","区","详细地址","销售人员","商品ID","国际条码","商品名称","规格","单价(元)","数量","小计(元)","交易状态","下单时间","支付时间","订单来源"};
		for(int i=0;i<strtitle.length;i++){
			cell=row.createCell(i);
			cell.setCellValue(strtitle[i]);
			cell.setCellStyle(columnHeadStyle);
		}
		String [] strtitle1={"订单号","收货人","联系电话","收货地址","零售商","零售商ID","用户名称","用户类型","省","市","区","详细地址","销售人员","购买商品数","订单总计(元)","折扣额(元)","运费收入(元)","实付金额(元)","交易状态","下单时间","付款时间","订单来源"};
		for(int i=0;i<strtitle1.length;i++){
			cell1=row1.createCell(i);
			cell1.setCellValue(strtitle1[i]);
			cell1.setCellStyle(columnHeadStyle);
		}
		try {
			RetailerOrderExportService dealerOrderService = RemoteServiceSingleton.getInstance().getRetailerOrderExportService();
			pageBean = dealerOrderService.exportRetailerOrders(pageBean);
			List<OrderDto> orderList = pageBean.getResult();
			if(null != orderList && orderList.size()>0){
				List<Long> RetailerIds = new ArrayList<Long>();
				
				Map<Long, OrderDto> orderMaps = new LinkedHashMap<Long, OrderDto>();
				for (OrderDto retailerOrderDto : orderList) {
					RetailerIds.add(retailerOrderDto.getRetailerId());
					Integer qty = retailerOrderDto.getQty();
					BigDecimal orderPrice = retailerOrderDto.getOrderPrice();
					BigDecimal paidPrice = retailerOrderDto.getPaidPrice();
					BigDecimal discountPrice = retailerOrderDto.getDiscountPrice();
					BigDecimal realTransferprice = retailerOrderDto.getRealTransferprice();
					
					if(orderMaps.containsKey(retailerOrderDto.getPayId())){
						Integer qty1 = orderMaps.get(retailerOrderDto.getPayId()).getQty();
						BigDecimal orderPrice1 = orderMaps.get(retailerOrderDto.getPayId()).getOrderPrice();
						BigDecimal paidPrice1 = orderMaps.get(retailerOrderDto.getPayId()).getPaidPrice();
						BigDecimal discountPrice1 = orderMaps.get(retailerOrderDto.getPayId()).getDiscountPrice();
						BigDecimal realTransferprice1 = orderMaps.get(retailerOrderDto.getPayId()).getRealTransferprice();
						retailerOrderDto.setQty(qty+qty1);
						retailerOrderDto.setOrderPrice(orderPrice.add(orderPrice1));
						retailerOrderDto.setPaidPrice(paidPrice.add(paidPrice1));
						retailerOrderDto.setDiscountPrice(discountPrice.add(discountPrice1));
						retailerOrderDto.setRealTransferprice(realTransferprice.add(realTransferprice1));
						orderMaps.put(retailerOrderDto.getPayId(), retailerOrderDto);
					}else{
						orderMaps.put(retailerOrderDto.getPayId(), retailerOrderDto);
					}	
				}
				RetailerManagerService retailerService = RemoteServiceSingleton.getInstance().getRetailerManagerService();
				Map<Long, RetailerAddressDTO> map = retailerService.getFindRetailerDtosByIds(RetailerIds);
				PlatformSaleManagerService platformSaleManagerService = RemoteServiceSingleton.getInstance().getPlatformSaleManagerService();
				List<PlatformSale> platformSales = platformSaleManagerService.getAllSale();
				for(Map.Entry<Long,RetailerAddressDTO> entry1: map.entrySet()) {
					entry1.getValue().getCityId();
				}
				if(null != map && map.size()>0){
					if(platformSales != null && platformSales.size()>0){
						for(Map.Entry<Long,RetailerAddressDTO> entry: map.entrySet()) {
							 for (PlatformSale platformSale : platformSales) {
								 if(entry.getValue().getSaleId() != null){
									 if(entry.getValue().getSaleId().equals(platformSale.getSaleId())){
										 entry.getValue().setRetailerCode(platformSale.getSaleName());
									 }
								 }else{
									 entry.getValue().setRetailerCode("");
								 }
							}
						}
					} 
				}
				int r = 1;
				int b = 1;
				for(Map.Entry<Long, OrderDto> entry2:orderMaps.entrySet()){
					String platform = "";
					if(entry2.getValue().getOrderPlatform().equals(RetailerConstant.PLATFORM_PC)){
						platform = "PC";
					}
					if(entry2.getValue().getOrderPlatform().equals(RetailerConstant.PLATFORM_PAD)){
						platform = "PAD";
					}
					String createtime = simpleDateFormat.format(entry2.getValue().getCreateTime());
					String payTime = "";
					if(entry2.getValue().getPayTime()!=null){
						payTime = simpleDateFormat.format(entry2.getValue().getPayTime());
					}
					String statu = "";
					switch (entry2.getValue().getStatus()) {
					case 1:	
						statu = "待支付";
						break;
					case 19:	
						statu = "待付款确认(网银转账)";
						break;
					case 20:	
						statu = "待付款确认(账期付款)";
						break;
					case 21:
						if(isFutuere.intValue() == 0){
							statu = "已付款";
						}else {
							statu = "已付首款";
						}
						break;
					case 31:
						statu = "等待WOFE合单";
						break;
					case 32:
						statu = "等待WOFE合单";
						break;
					case 33:
						statu = "等待WOFE合单";
						break;
					case 34:
						statu = "等待WOFE合单";
						break;
					case 41:
						statu = "等待供应商发货";
						break;
					case 51:
						statu = "供应商已发货";
						break;
					case 61:
						statu = "等待零售商交付余款";
						break;
					case 71:
						statu = "等待经销商发货";
						break;
					case 81:
						statu = "经销商已发货";
						break;
					case 91:
						statu = "订单完成";
						break;
					case 100:
						statu = "系统自动取消";
						break;
					case 101:
						statu = "系统默认完成";
						break;
					case 111:
						statu = "买家取消";
						break;
					case 112:
						statu = "客服取消";
						break;
					}
					row1 = sheet1.createRow(b++);
					row1.createCell(Constants.SHORT0).setCellValue(entry2.getKey()+"");
					row1.createCell(Constants.SHORT1).setCellValue(entry2.getValue().getReceiveName());
					row1.createCell(Constants.SHORT2).setCellValue(entry2.getValue().getReceiveMobilePhone());
					row1.createCell(Constants.SHORT3).setCellValue(entry2.getValue().getReceiveAddress());
					row1.createCell(Constants.SHORT5).setCellValue(entry2.getValue().getRetailerId());
					row1.createCell(Constants.SHORT6).setCellValue(entry2.getValue().getCreateBy());
					if(map.containsKey(entry2.getValue().getRetailerId())){
						//商户类型 1 商业级别商户 2 普通商户 3 支付宝登陆用户 4 qq登录用户 5 微博登录用户 6 微信登陆用户
						String merchantType = "";
						if(map.get(entry2.getValue().getRetailerId()).getMerchantType() == 1){
							merchantType = "商业级别商户";
						}else{
							merchantType = "会员用户";
						}
						row1.createCell(Constants.SHORT4).setCellValue(map.get(entry2.getValue().getRetailerId()).getName());
						row1.createCell(Constants.SHORT7).setCellValue(merchantType);
						row1.createCell(Constants.SHORT8).setCellValue(map.get(entry2.getValue().getRetailerId()).getProvinceName());
						row1.createCell(Constants.SHORT9).setCellValue(map.get(entry2.getValue().getRetailerId()).getCityName());
						row1.createCell(Constants.SHORT10).setCellValue(map.get(entry2.getValue().getRetailerId()).getAreaName());
						row1.createCell(Constants.SHORT11).setCellValue(map.get(entry2.getValue().getRetailerId()).getAddress());
					}else{
						row1.createCell(Constants.SHORT8).setCellValue("");
						row1.createCell(Constants.SHORT9).setCellValue("");
						row1.createCell(Constants.SHORT10).setCellValue("");
					}
					if(map.get(entry2.getValue().getRetailerId()).getSaleId()==null){
						row1.createCell(Constants.SHORT12).setCellValue("");
					}else{
						if(map.containsKey(entry2.getValue().getRetailerId())){
							row1.createCell(Constants.SHORT12).setCellValue(map.get(entry2.getValue().getRetailerId()).getRetailerCode());
						}
					}
					row1.createCell(Constants.SHORT13).setCellValue(entry2.getValue().getQty()+"");
					row1.createCell(Constants.SHORT14).setCellValue(entry2.getValue().getOrderPrice()+"");
					row1.createCell(Constants.SHORT15).setCellValue(entry2.getValue().getDiscountPrice()+"");
					row1.createCell(Constants.SHORT16).setCellValue(entry2.getValue().getRealTransferprice()+"");
					row1.createCell(Constants.SHORT17).setCellValue(entry2.getValue().getPaidPrice()+"");
					row1.createCell(Constants.SHORT18).setCellValue(statu);
					row1.createCell(Constants.SHORT19).setCellValue(createtime);
					row1.createCell(Constants.SHORT20).setCellValue(payTime);
					row1.createCell(Constants.SHORT21).setCellValue(platform);
				}
				for (OrderDto retailerOrderDto : orderList) {
					String platform = "";
					if(retailerOrderDto.getOrderPlatform().equals(RetailerConstant.PLATFORM_PC)){
						platform = "PC";
					}
					if(retailerOrderDto.getOrderPlatform().equals(RetailerConstant.PLATFORM_PAD)){
						platform = "PAD";
					}
					String createtime = simpleDateFormat.format(retailerOrderDto.getCreateTime());
					String payTime = "";
					if(retailerOrderDto.getPayTime()!=null){
						payTime = simpleDateFormat.format(retailerOrderDto.getPayTime());
					}
					String statu = "";
					switch (retailerOrderDto.getStatus()) {
					case 1:	
						statu = "待支付";
						break;
					case 19:	
						statu = "待付款确认(网银转账)";
						break;
					case 20:	
						statu = "待付款确认(账期付款)";
						break;
					case 21:
						if(isFutuere.intValue() == 0){
							statu = "已付款";
						}else {
							statu = "已付首款";
						}
						break;
					case 31:
						statu = "等待WOFE合单";
						break;
					case 32:
						statu = "等待WOFE合单";
						break;
					case 33:
						statu = "等待WOFE合单";
						break;
					case 34:
						statu = "等待WOFE合单";
						break;
					case 41:
						statu = "等待供应商发货";
						break;
					case 51:
						statu = "供应商已发货";
						break;
					case 61:
						statu = "等待零售商交付余款";
						break;
					case 71:
						statu = "等待经销商发货";
						break;
					case 81:
						statu = "经销商已发货";
						break;
					case 91:
						statu = "订单完成";
						break;
					case 100:
						statu = "系统自动取消";
						break;
					case 101:
						statu = "系统默认完成";
						break;
					case 111:
						statu = "买家取消";
						break;
					case 112:
						statu = "客服取消";
						break;
					}
					List<OrderItemDto> orderItemList = retailerOrderDto.getItemlist();
					for (OrderItemDto retailerOrderItemDto : orderItemList) {
						row = sheet.createRow(r++);
						String totalPrice = retailerOrderItemDto.getPrice().multiply(new BigDecimal(retailerOrderItemDto.getSkuQty()))+"";
						row.createCell(Constants.SHORT0).setCellValue(retailerOrderDto.getPayId()+"");
						row.createCell(Constants.SHORT1).setCellValue(retailerOrderDto.getReceiveName());
						row.createCell(Constants.SHORT2).setCellValue(retailerOrderDto.getReceiveMobilePhone());
						row.createCell(Constants.SHORT3).setCellValue(retailerOrderDto.getReceiveAddress());
						row.createCell(Constants.SHORT5).setCellValue(retailerOrderDto.getRetailerId());
						row.createCell(Constants.SHORT6).setCellValue(retailerOrderDto.getCreateBy());
						if(map.containsKey(retailerOrderDto.getRetailerId())){
							//商户类型 1 商业级别商户 2 普通商户 3 支付宝登陆用户 4 qq登录用户 5 微博登录用户 6 微信登陆用户
							String merchantType = "";
							if(map.get(retailerOrderDto.getRetailerId()).getMerchantType() == 1){
								merchantType = "商业级别商户";
							}else{
								merchantType = "会员用户";
							}
							row.createCell(Constants.SHORT7).setCellValue(merchantType);
							row.createCell(Constants.SHORT4).setCellValue(map.get(retailerOrderDto.getRetailerId()).getName());
							row.createCell(Constants.SHORT8).setCellValue(map.get(retailerOrderDto.getRetailerId()).getProvinceName());
							row.createCell(Constants.SHORT9).setCellValue(map.get(retailerOrderDto.getRetailerId()).getCityName());
							row.createCell(Constants.SHORT10).setCellValue(map.get(retailerOrderDto.getRetailerId()).getAreaName());
							row.createCell(Constants.SHORT11).setCellValue(map.get(retailerOrderDto.getRetailerId()).getAddress());	
						}else{
							row.createCell(Constants.SHORT8).setCellValue("");
							row.createCell(Constants.SHORT9).setCellValue("");
							row.createCell(Constants.SHORT10).setCellValue("");
						}
						if(map.get(retailerOrderDto.getRetailerId()).getSaleId()==null){
							row.createCell(Constants.SHORT12).setCellValue("");
						}else{
							if(map.containsKey(retailerOrderDto.getRetailerId())){
								row.createCell(Constants.SHORT12).setCellValue(map.get(retailerOrderDto.getRetailerId()).getRetailerCode());
							}
						}
						row.createCell(Constants.SHORT13).setCellValue(retailerOrderDto.getPid()+"");
						row.createCell(Constants.SHORT14).setCellValue(retailerOrderItemDto.getSkuCode());
						row.createCell(Constants.SHORT15).setCellValue(retailerOrderDto.getpName());
						row.createCell(Constants.SHORT16).setCellValue(retailerOrderItemDto.getSkuName());
						row.createCell(Constants.SHORT17).setCellValue(retailerOrderItemDto.getPrice()+"");
						row.createCell(Constants.SHORT18).setCellValue(retailerOrderItemDto.getSkuQty());
						row.createCell(Constants.SHORT19).setCellValue(totalPrice);
						row.createCell(Constants.SHORT20).setCellValue(statu);
						row.createCell(Constants.SHORT21).setCellValue(createtime);
						row.createCell(Constants.SHORT22).setCellValue(payTime);
						row.createCell(23).setCellValue(platform);
					}	
				}
			}
		} catch (Exception e) {
			LOGGER.error("DealerOrderService <findRetailerOrder()> is failed !!!!"+e.getMessage(),e);
		}
		try {
			String filename = "retailer-order"+simpleDateFormat1.format(new Date());
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setCharacterEncoding("UTF-8");
			filename = java.net.URLEncoder.encode(filename,"gb2312");
			response.setHeader("Content-Disposition","attachment;filename="+
					new String(filename.getBytes("UTF-8"),"GBK")+".xls");
			response.setContentType("application/msexcel");//定义输出类型	
			book.write(os);  	
			response.getOutputStream().flush();
			response.getOutputStream().close();	
		} catch (Exception e) {
		}
		LOGGER.info("end<> to execute method <exportPackageListByConditionExcel>!!!!");
	}
	*//**
	 * B订单取消（未发货） 取消订单
	 * @param payId
	 * @return
	 *//*
	@RequestMapping(value = "/toCancelOrderByWofe")
	public void toCancelOrderByWofe(HttpServletResponse response,Long payId){
		LOGGER.info("start to execute method <goCancelOrderByWofe>wofe去取消订单!!!!");
		String createBy = getCurrentUser().getUsername();
		LOGGER.info("订单号："+payId);
		LOGGER.info("创建人："+createBy);
		Result result = new Result();
		try {
			CustomerOrderService customerOrderService = RemoteServiceSingleton.getInstance().getCustomerOrderService();
			OutOrderService outOrderService = RemoteServiceSingleton.getInstance().getOutOrderService();
			boolean status = outOrderService.loadShipmentstateByOrderId(payId==null?null:payId.toString());
			if(status == true){
				String flag = customerOrderService.addOrderCancelStatusRecord(payId, "B2B",createBy);
				if(flag!=null&&"".equals(flag)){
					String updateCancleOrderStatus = customerOrderService
							.updateCancleOrderStatus(
									payId,
									Constants.B2B,
									Integer.parseInt(Constants.ORDERFAULT));
							if("".equals(updateCancleOrderStatus)){
								result.setSuccess(false);
								result.setMessage("订单取消失败,该订单已经发运!订单编号:"+payId);
								response.getWriter().write(JSONArray.toJSON(result).toString());					
							}else{
								logger.info("==============订单取消失败==========订单号"+payId+"!updateCancleOrderStatus");
								result.setSuccess(false);
								result.setMessage("订单取消失败,"+updateCancleOrderStatus+"订单编号:"+payId);
								response.getWriter().write(JSONArray.toJSON(result).toString());			
							}
				}else{
					result.setSuccess(false);
					result.setMessage("订单取消返回信息异常!订单编号:"+payId);
					response.getWriter().write(JSONArray.toJSON(result).toString());
				}	
			}else{
				String flag = customerOrderService.addOrderCancelStatusRecord(payId, "B2B",createBy);
				if(flag!=null&&"".equals(flag)){
	//				OrderSCancelService orderSCancelService = RemoteServiceSingleton.getInstance().getOrderSCancelService();
	//				String retJson = orderSCancelService.handleOrderSCancel(payId+"",createBy,payId+"","B2B");
	//				if(retJson == null){
	//					logger.info("订单取消返回信息异常!订单编号:"+payId);
	//					result.setSuccess(false);
	//					result.setMessage("订单取消返回信息异常!订单编号:"+payId);
	//					response.getWriter().write(JSONArray.toJSON(result).toString());
	//				}else{
	//					response.getWriter().write(retJson);
	//				}
					logger.info("订单正在取消!订单编号:"+payId);
					result.setSuccess(true);
					result.setMessage("订单正在取消!订单编号:"+payId);
					response.getWriter().write(JSONArray.toJSON(result).toString());
					return;
				}else{
					result.setSuccess(false);
					result.setMessage("订单取消返回信息异常!订单编号:"+payId);
					response.getWriter().write(JSONArray.toJSON(result).toString());
				}			
			} 
		}catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("RetailerOrderService<cancelOrderByWofe()根据订单Id取消订单> is failed!!!!"+e.getMessage(),e);
			try {
				result.setSuccess(false);
				result.setMessage("订单取消返回信息异常!订单编号:"+payId);
				response.getWriter().write(JSONArray.toJSON(result).toString());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return;
		}
	}
}
*/