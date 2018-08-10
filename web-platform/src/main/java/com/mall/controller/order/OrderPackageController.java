/*package com.mall.controller.order;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.customer.model.User;
import com.mall.customer.service.UserService;
import com.mall.dealer.service.DealerService;
import com.mall.mybatis.utility.PageBean;
import com.mall.platform.model.PlatformSale;
import com.mall.platform.service.PlatformSaleManagerService;
import com.mall.retailer.model.Retailer;
import com.mall.retailer.model.RetailerAddressDTO;
import com.mall.retailer.model.RetailerUser;
import com.mall.retailer.order.api.rpc.RetailerOrderExportService;
import com.mall.retailer.order.api.rpc.RetailerShipOrderService;
import com.mall.retailer.order.common.RetailerConstant;
import com.mall.retailer.order.dto.OrderProductDto;
import com.mall.retailer.order.dto.ShipOrderDTO;
import com.mall.retailer.order.dto.ShipOrderItemDTO;
import com.mall.retailer.service.RetailerManagerService;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Common;
import com.mall.utils.Constants;
*//**.
 * @Description:wofe 查询期货现货的包裹
 * @author GuoFeiyan
 * @Date 2014年5月16日15:36:39
 *//*
@Controller
@RequestMapping(value = "/orderPackage")
public class OrderPackageController extends BaseController{

	
	*//**.
	 * LOGGER(日志打印).
	 *//*
	private static final Log LOGGER = LogFactory.getLogger(OrderPackageController.class);
	
	
	*//**.
	 * @Description:初始化进入包裹查询列表
	 * @Title:getOrderPackageList
	 * @return String
	 *//*
	@RequestMapping(value = "/getOrderPackageList")
	public String getOrderPackageList(){
		return "/dealerseller/orderpackage/orderpackage_list";
	}

	
	
	*//**
	 * @Description:根据条件检索包裹列表
	 * @Title:getOrderPackageList
	 * @param id
	 * @param payId
	 * @param retailerName
	 * @param dealerName
	 * @param status
	 * @param isFutures
	 * @param page
	 * @param model
	 * @return String
	 *//*
	@RequestMapping(value = "/getOrderPackagePageBeanByCondition")
	public String getOrderPackagePageBeanByCondition(Long id,Long payId, Integer orderType,String userName,
			String retailerName,String dealerName,Short status,Integer isFutures,Integer page,Model model){
		LOGGER.info("start to execute method <getOrderPackageListByCondition>!!!!");
		LOGGER.info("订单号<id>："+payId);
		LOGGER.info("包裹号<sendId>："+id);
		LOGGER.info("零售商名称<retailerName>："+retailerName);
		LOGGER.info("经销商名称<dealerName>  ："+dealerName);
		LOGGER.info("包裹状态<status>   ："+status);
		LOGGER.info("是否期货<isFutures>："+isFutures);
		LOGGER.info("订单类型<orderType>："+orderType);
		LOGGER.info("用户名称<userName>："+userName);
		ShipOrderDTO shipOrderDTO = new ShipOrderDTO();
		//shipOrderDTO.getWarehouseName()
		PageBean<ShipOrderDTO> shipOrderPageBean = new PageBean<ShipOrderDTO>();
		if(null != isFutures){
			if(isFutures==0){
				shipOrderDTO.setIsFuture(RetailerConstant.IS_FUTURES_NO);
			}else if(isFutures==1){
				shipOrderDTO.setIsFuture(RetailerConstant.IS_FUTURES_YES);
			}else {	
			}
		}
		if(null != orderType){
			shipOrderDTO.setOrderType(orderType);
		}
		if(null != id){
			shipOrderDTO.setId(id);
		}
		if(!Common.isEmpty(retailerName)){
			try {
				RetailerManagerService retailerService = RemoteServiceSingleton.getInstance().getRetailerManagerService();
				List<Long> retailerIds = retailerService.getRetailerIdsByName(retailerName);
				if(retailerIds.size()<1){
					retailerIds.add(0l);
				}
				shipOrderDTO.setRetailerIds(retailerIds);
			} catch (Exception e) {
				LOGGER.error("RetailerManagerService<getRetailerIdsByName()通过名称模糊查询Id集合> is failed!!!!"+e.getMessage(),e);
			}
		}
		if(!Common.isEmpty(userName)){
			List<Long> userIds = new ArrayList<Long>();
			UserService userService = RemoteServiceSingleton.getInstance().getUserService();
			userIds = userService.findUserIdsByUserName(userName);
			if(userIds.size()<1){
				userIds.add(0l);
			}
			shipOrderDTO.setUserIds(userIds);
		}
		if(!Common.isEmpty(dealerName)){
			try {
				DealerService dealerService = RemoteServiceSingleton.getInstance().getDealerService();
				List<Long> dealerIds = dealerService.getDealerIdsByName(dealerName);
				if(dealerIds.size()<1){
					dealerIds.add(0l);
				}
				shipOrderDTO.setDealerIds(dealerIds);
			} catch (Exception e) {
				LOGGER.error("DealerService<getDealerIdsByName()通过名称模糊查询Id集合> is failed!!!!"+e.getMessage(),e);
			}
		}
		if(null != status){
			shipOrderDTO.setStatus(status);
		}
		if(orderType == 1){
			if(null != payId){
				shipOrderDTO.setPayId(payId);
			}
		}else{
			if(null != payId){
				shipOrderDTO.setOrderId(payId);
			}
		}
		if(null != page){
			shipOrderPageBean.setPage(page);
		}else{
			shipOrderPageBean.setPage(Constants.INT1);
		}
		shipOrderDTO.setQueryType(0);
		shipOrderPageBean.setPageSize(Constants.PAGESIZE);
		shipOrderPageBean.setParameter(shipOrderDTO);
		try {
			RetailerShipOrderService shipOrderService = RemoteServiceSingleton.getInstance().getretailerShipOrderService();
			shipOrderPageBean = shipOrderService.findRetailerShipOrderList(shipOrderPageBean);
			if(null != shipOrderPageBean){
				List<ShipOrderDTO> shipOrders = shipOrderPageBean.getResult();
				if(null != shipOrders && shipOrders.size()>0){
					for (ShipOrderDTO shipOrderDTO2 : shipOrders) {
						if(null != shipOrderDTO2.getOrderProductList() && shipOrderDTO2.getOrderProductList().size()>0){
							for (OrderProductDto productInfo : shipOrderDTO2.getOrderProductList()) {
								String imageUrl = productInfo.getImgurl();
								if(!imageUrl.startsWith("http") || !imageUrl.startsWith("Http")){
									imageUrl = Constants.P1+imageUrl;
									productInfo.setImgurl(imageUrl);
								}
							}
						}
					}
				}
			}
			model.addAttribute("pageBean", shipOrderPageBean);
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("RetailerShipOrderService<findRetailerShipOrderList()> is failed!!!! "+e.getMessage(),e);
		}
		LOGGER.info("end<> to execute method <getOrderPackageListByCondition>!!!!");
		return "/dealerseller/orderpackage/orderpackage_list_model";	
	}
	
	
	
	
	*//**.
	 * @Title：getOrderPackageDetailsById
	 * @Description:根据ID查询现货发货单的详情
	 * @param Long id
	 * @return String
	 *//*
	@RequestMapping(value = "/getOrderPackageDetailsById")
	public String getOrderPackageDetailsById(Long id,Model model){
		LOGGER.info("start to execute method <getOrderPackageDetailsById>!!!!");
		LOGGER.info("发货单号<id>:"+id);
		LOGGER.info("登陆用户<userId>:"+getCurrentUser().getId());
		try {
			RetailerShipOrderService  shipOrderService = RemoteServiceSingleton.getInstance().getretailerShipOrderService();
			ShipOrderDTO shipOrderDetails = shipOrderService.findRetailerShipOrderListByShipId(id);
			if(null != shipOrderDetails){
				Map<Long, List<ShipOrderItemDTO>> productMap= shipOrderDetails.getShipOrderItemMap();
				for (Map.Entry<Long, List<ShipOrderItemDTO>> productInfo : productMap.entrySet()) {
					List<ShipOrderItemDTO> itemList = productInfo.getValue();
					for (ShipOrderItemDTO shipOrderItemDTO : itemList) {
						String imageUrl = shipOrderItemDTO.getProductUrl();
						if(!imageUrl.startsWith("http") || !imageUrl.startsWith("Http")){
							imageUrl = Constants.P1+imageUrl;
							shipOrderItemDTO.setProductUrl(imageUrl);
							LOGGER.info("发货单下的图片："+imageUrl);
						}
					}
				}
				
				Long retailerId = shipOrderDetails.getRetailerId();
				Long userId = shipOrderDetails.getUserId();
				if(null != retailerId){
					RetailerManagerService retailerManagerService = RemoteServiceSingleton.getInstance().getRetailerManagerService();
					Retailer retailer = retailerManagerService.findByRetailerId(retailerId);
					RetailerUser retailerUser = RemoteServiceSingleton.getInstance().getRetailerUserManagerService().findAdminUserByMerchantId(retailerId);
					model.addAttribute("retailerUser",retailerUser);
					model.addAttribute("retailer",retailer);
				}
				if(null != userId){
					try {
						LOGGER.info("此订单用户ID:"+userId);
						UserService userService = RemoteServiceSingleton.getInstance().getUserService();
						User user = userService.findUserById(userId);	
						model.addAttribute("user", user);
					} catch (Exception e) {
						// TODO: handle exception
						LOGGER.error("UserService<findUserById()根据用户ID查询该用户的信息> is failed!!!!"+e.getMessage(),e);
					}
				}
			}
			model.addAttribute("shipOrderDetails",shipOrderDetails);	
		} catch (Exception e) {
			LOGGER.error("RetailerShipOrderService<findRetailerShipOrderListByPayId() is failed!!!!>"+e.getMessage(),e);
		}
		LOGGER.info("end<> to execute method <getOrderPackageDetailsById>!!!!");
		return "/dealerseller/orderpackage/orderpackage_details";
	}
	
	
	
	*//**.
	 * wofe通过选择时间段导出包裹表格.	
	 * @param id
	 * @param payId
	 * @param retailerName
	 * @param dealerName
	 * @param status
	 * @param isFutures
	 * @param response
	 *//*
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/exportPackageListByConditionExcel")
	public void exportPackageListByConditionExcel(Long id,Long payId, String startTime,String endTime,Integer orderType,String userName,Integer type,
			String retailerName,String dealerName,Short status,Integer isFutures,HttpServletResponse response){
		LOGGER.info("start to execute method <exportPackageListByConditionExcel>!!!!");
		LOGGER.info("订单号<id>："+payId);
		LOGGER.info("包裹号<sendId>："+id);
		LOGGER.info("零售商名称<retailerName>："+retailerName);
		LOGGER.info("经销商名称<dealerName>  ："+dealerName);
		LOGGER.info("包裹状态<status>   ："+status);
		LOGGER.info("是否期货<isFutures>："+isFutures);
		LOGGER.info("开始时间<startTime>："+startTime);
		LOGGER.info("结束时间<endTime>："+endTime);
		ShipOrderDTO shipOrderDTO = new ShipOrderDTO();
		PageBean<ShipOrderDTO> shipOrderPageBean = new PageBean<ShipOrderDTO>();
		if(null != isFutures){
			if(isFutures==0){
				shipOrderDTO.setIsFuture(RetailerConstant.IS_FUTURES_NO);
			}else if(isFutures==1){
				shipOrderDTO.setIsFuture(RetailerConstant.IS_FUTURES_YES);
			}else {	
			}
		}
		if(null != id){
			shipOrderDTO.setId(id);
		}
		if(null != payId){
			shipOrderDTO.setPayId(payId);
		}
		if(!Common.isEmpty(startTime)){
			shipOrderDTO.setFirstDate(Common.stringToDate(startTime,"yyyy-MM-dd"));
		}
		if(!Common.isEmpty(endTime)){
			shipOrderDTO.setLastDate(Common.stringToDate(endTime+" 23:59:59","yyyy-MM-dd HH:mm:ss"));
		}
		if(!Common.isEmpty(retailerName)){
			try {
				RetailerManagerService retailerService = RemoteServiceSingleton.getInstance().getRetailerManagerService();
				List<Long> retailerIds = retailerService.getRetailerIdsByName(retailerName);
				if(retailerIds.size()<1){
					retailerIds.add(0l);
				}
				shipOrderDTO.setRetailerIds(retailerIds);
			} catch (Exception e) {
				LOGGER.error("RetailerManagerService<getRetailerIdsByName()通过名称模糊查询Id集合> is failed!!!!"+e.getMessage(),e);
			}
		}
		if(!Common.isEmpty(userName)){
			List<Long> userIds = new ArrayList<Long>();
			UserService userService = RemoteServiceSingleton.getInstance().getUserService();
			userIds = userService.findUserIdsByUserName(userName);
			if(userIds.size()<1){
				userIds.add(0l);
			}
			shipOrderDTO.setUserIds(userIds);
		}
		if(null != orderType){
			shipOrderDTO.setOrderType(orderType);
		}
		if(!Common.isEmpty(dealerName)){
			try {
				DealerService dealerService = RemoteServiceSingleton.getInstance().getDealerService();
				List<Long> dealerIds = dealerService.getDealerIdsByName(dealerName);
				if(dealerIds.size()<1){
					dealerIds.add(0l);
				}
				shipOrderDTO.setDealerIds(dealerIds);
			} catch (Exception e) {
				LOGGER.error("DealerService<getDealerIdsByName()通过名称模糊查询Id集合> is failed!!!!"+e.getMessage(),e);
			}
		}
		if(null != status){
			shipOrderDTO.setStatus(status);
		}
		shipOrderDTO.setQueryType(1);
		shipOrderPageBean.setPageSize(Constants.MAXPAGESIZE);
		shipOrderPageBean.setParameter(shipOrderDTO);
		HSSFWorkbook book = new HSSFWorkbook();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		HSSFSheet sheet = book.createSheet("packageList");
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
	    sheet.setColumnWidth(2, 5000);    
	    sheet.setColumnWidth(3, 4000);    
	    sheet.setColumnWidth(4, 5000);    
	    sheet.setColumnWidth(5, 8500);    
	    sheet.setColumnWidth(6, 6000);    
	    sheet.setColumnWidth(7, 4000); 
	    sheet.setColumnWidth(8, 3000); 
	    sheet.setColumnWidth(9, 3000); 
	    sheet.setColumnWidth(10, 3000); 
	    sheet.setColumnWidth(11, 6000); 
	    sheet.setColumnWidth(12, 6000); 
	    sheet.setColumnWidth(13, 6000);
	    sheet.setColumnWidth(14, 6000); 
	    sheet.setColumnWidth(15, 6000); 
	    sheet.setColumnWidth(16, 3000);
	    sheet.setColumnWidth(17, 3000);
	    sheet.setColumnWidth(18, 3000);
	    sheet.setColumnWidth(19, 4000);
	    sheet.setColumnWidth(20, 6000);
	    
	    if(type == 1){
	    	HSSFRow row = sheet.createRow((int)0);
			HSSFCell cell=null;
			if(orderType == 1){
				String [] strtitle={"包裹号","订单号","经销商","收货人","移动电话","收货地址","零售商","零售商ID","省","市","区","详细地址","购买商品数量","订单总计","交易状态","下单时间"};
				for(int i=0;i<strtitle.length;i++){
					cell=row.createCell(i);
					cell.setCellValue(strtitle[i]);
					cell.setCellStyle(columnHeadStyle);
				}
			}else{
				String [] strtitle={"包裹号","订单号","经销商","收货人","移动电话","收货地址","用户名","用户ID","购买商品数量","订单总计","交易状态","下单时间"};
				for(int i=0;i<strtitle.length;i++){
					cell=row.createCell(i);
					cell.setCellValue(strtitle[i]);
					cell.setCellStyle(columnHeadStyle);
				}
			}
			
			
			try {
				RetailerOrderExportService shipOrderService = RemoteServiceSingleton.getInstance().getRetailerOrderExportService();
				shipOrderPageBean = shipOrderService.exportShipOrderInfoByWofe(shipOrderPageBean);
				List<ShipOrderDTO> shipOrders = shipOrderPageBean.getResult();
				if(null !=shipOrders && shipOrders.size()>0){
					List<Long> retailerIds = new ArrayList<Long>();
					for (ShipOrderDTO shipOrderDTO2 : shipOrders) {
						retailerIds.add(shipOrderDTO2.getRetailerId());
					}
					RetailerManagerService retailerService = RemoteServiceSingleton.getInstance().getRetailerManagerService();
					Map<Long, RetailerAddressDTO> map = retailerService.getFindRetailerDtosByIds(retailerIds);
					PlatformSaleManagerService platformSaleManagerService = RemoteServiceSingleton.getInstance().getPlatformSaleManagerService();
					List<PlatformSale> platformSales = platformSaleManagerService.getAllSale();
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
					for (ShipOrderDTO shipOrderDTO2 : shipOrders) {
					List<ShipOrderItemDTO> shipOrderItemDTO = shipOrderDTO2.getShipItemList();
					if(null != shipOrderItemDTO && shipOrderItemDTO.size()>0){
						row = sheet.createRow(r++);
						String createtime = simpleDateFormat.format(shipOrderDTO2.getCreateTime());
						row.createCell(Constants.SHORT0).setCellValue(shipOrderDTO2.getId()+"");
						
						row.createCell(Constants.SHORT2).setCellValue(shipOrderDTO2.getDealerName());
						row.createCell(Constants.SHORT3).setCellValue(shipOrderDTO2.getReceiveName());
						row.createCell(Constants.SHORT4).setCellValue(shipOrderDTO2.getReceiveMobilePhone());
						row.createCell(Constants.SHORT5).setCellValue(shipOrderDTO2.getReceiveAddress());
						if(shipOrderDTO2.getOrderType() == 1){
							row.createCell(Constants.SHORT1).setCellValue(shipOrderDTO2.getPayId()+"");
							if(map.containsKey(shipOrderDTO2.getRetailerId())){
								row.createCell(Constants.SHORT6).setCellValue(map.get(shipOrderDTO2.getRetailerId()).getName());
								row.createCell(Constants.SHORT7).setCellValue(shipOrderDTO2.getRetailerId());
								row.createCell(Constants.SHORT8).setCellValue(map.get(shipOrderDTO2.getRetailerId()).getProvinceName());
								row.createCell(Constants.SHORT9).setCellValue(map.get(shipOrderDTO2.getRetailerId()).getCityName());
								row.createCell(Constants.SHORT10).setCellValue(map.get(shipOrderDTO2.getRetailerId()).getAreaName());
							}else{
								row.createCell(Constants.SHORT8).setCellValue("");
								row.createCell(Constants.SHORT9).setCellValue("");
								row.createCell(Constants.SHORT10).setCellValue("");
							}
							row.createCell(Constants.SHORT11).setCellValue(map.get(shipOrderDTO2.getRetailerId()).getAddress());
							row.createCell(Constants.SHORT12).setCellValue(shipOrderDTO2.getQty());
							row.createCell(Constants.SHORT13).setCellValue(shipOrderDTO2.getPrice()+"");
							row.createCell(Constants.SHORT15).setCellValue(createtime);
						}else{
							row.createCell(Constants.SHORT1).setCellValue(shipOrderDTO2.getOrderId()+"");
							row.createCell(Constants.SHORT6).setCellValue(shipOrderDTO2.getUserName());
							row.createCell(Constants.SHORT7).setCellValue(shipOrderDTO2.getUserId());
							row.createCell(Constants.SHORT8).setCellValue(shipOrderDTO2.getQty());
							row.createCell(Constants.SHORT9).setCellValue(shipOrderDTO2.getPrice()+"");
							row.createCell(Constants.SHORT11).setCellValue(createtime);
						}
						String statu = "";
						switch (shipOrderDTO2.getStatus()) {
						case 1:	
							statu = "待发货";
							break;
						case 2:
							statu = "已发货";
							break;
						case 3:
							statu = "已完成";
							break;
						case 9:
							statu = "已取消";
							break;
						}
						if(shipOrderDTO2.getOrderType() == 1){
							row.createCell(Constants.SHORT14).setCellValue(statu);
						}else {
							row.createCell(Constants.SHORT10).setCellValue(statu);
						}
						}
					}	
				}
			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.error("RetailerShipOrderService<findRetailerShipOrderList()> is failed!!!! "+e.getMessage(),e);
			}
			LOGGER.info("拼装信息完成!");
	    }else{
	    	HSSFRow row = sheet.createRow((int)0);
			HSSFCell cell=null;
			if(orderType == 1){
				String [] strtitle={"包裹号","订单号","经销商","收货人","移动电话","收货地址","零售商","零售商ID","省","市","区","详细地址","商品ID","条码","商品名称","规格","单价(元)","数量","小计(元)","交易状态","下单时间"};
				for(int i=0;i<strtitle.length;i++){
					cell=row.createCell(i);
					cell.setCellValue(strtitle[i]);
					cell.setCellStyle(columnHeadStyle);
				}
			}else{
				String [] strtitle={"包裹号","订单号","经销商","收货人","移动电话","收货地址","用户名","用户ID","商品ID","条码","商品名称","规格","单价(元)","数量","小计(元)","交易状态","下单时间"};
				for(int i=0;i<strtitle.length;i++){
					cell=row.createCell(i);
					cell.setCellValue(strtitle[i]);
					cell.setCellStyle(columnHeadStyle);
				}
			}
			
			try {
				RetailerOrderExportService shipOrderService = RemoteServiceSingleton.getInstance().getRetailerOrderExportService();
				shipOrderPageBean = shipOrderService.exportShipOrderInfoByWofe(shipOrderPageBean);
				List<ShipOrderDTO> shipOrders = shipOrderPageBean.getResult();
				if(null !=shipOrders && shipOrders.size()>0){
					List<Long> retailerIds = new ArrayList<Long>();
					for (ShipOrderDTO shipOrderDTO2 : shipOrders) {
						retailerIds.add(shipOrderDTO2.getRetailerId());
					}
					RetailerManagerService retailerService = RemoteServiceSingleton.getInstance().getRetailerManagerService();
					Map<Long, RetailerAddressDTO> map = retailerService.getFindRetailerDtosByIds(retailerIds);
					PlatformSaleManagerService platformSaleManagerService = RemoteServiceSingleton.getInstance().getPlatformSaleManagerService();
					List<PlatformSale> platformSales = platformSaleManagerService.getAllSale();
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
					for (ShipOrderDTO shipOrderDTO2 : shipOrders) {
						List<ShipOrderItemDTO> shipOrderItemDTO = shipOrderDTO2.getShipItemList();
						if(null != shipOrderItemDTO && shipOrderItemDTO.size()>0){
							for (ShipOrderItemDTO shipOrderItemDTO2 : shipOrderItemDTO) {
								row = sheet.createRow(r++);
								String createtime = simpleDateFormat.format(shipOrderDTO2.getCreateTime());
								String totalprice = shipOrderItemDTO2.getPirce().multiply(new BigDecimal(shipOrderItemDTO2.getQty()))+"";
								String statu = "";
								switch (shipOrderDTO2.getStatus()) {
								case 1:	
									statu = "待发货";
									break;
								case 2:
									statu = "已发货";
									break;
								case 3:
									statu = "已完成";
									break;
								case 9:
									statu = "已取消";
									break;
								}
								row.createCell(Constants.SHORT0).setCellValue(shipOrderDTO2.getId()+"");
								
								row.createCell(Constants.SHORT2).setCellValue(shipOrderDTO2.getDealerName());
								row.createCell(Constants.SHORT3).setCellValue(shipOrderDTO2.getReceiveName());
								row.createCell(Constants.SHORT4).setCellValue(shipOrderDTO2.getReceiveMobilePhone());
								row.createCell(Constants.SHORT5).setCellValue(shipOrderDTO2.getReceiveAddress());
								if(shipOrderDTO2.getOrderType() == 1){
									if(map.containsKey(shipOrderDTO2.getRetailerId())){
										row.createCell(Constants.SHORT6).setCellValue(map.get(shipOrderDTO2.getRetailerId()).getName());
										row.createCell(Constants.SHORT7).setCellValue(map.get(shipOrderDTO2.getRetailerId()).getRetailerId());
										row.createCell(Constants.SHORT8).setCellValue(map.get(shipOrderDTO2.getRetailerId()).getProvinceName());
										row.createCell(Constants.SHORT9).setCellValue(map.get(shipOrderDTO2.getRetailerId()).getCityName());
										row.createCell(Constants.SHORT10).setCellValue(map.get(shipOrderDTO2.getRetailerId()).getAreaName());
									}else{
										row.createCell(Constants.SHORT6).setCellValue("");
										row.createCell(Constants.SHORT7).setCellValue("");
										row.createCell(Constants.SHORT8).setCellValue("");
										row.createCell(Constants.SHORT9).setCellValue("");
										row.createCell(Constants.SHORT10).setCellValue("");
									}
									row.createCell(Constants.SHORT11).setCellValue(map.get(shipOrderDTO2.getRetailerId()).getAddress());
									
									row.createCell(Constants.SHORT12).setCellValue(shipOrderItemDTO2.getPid()+"");
									row.createCell(Constants.SHORT13).setCellValue(shipOrderItemDTO2.getSkuCode());
									row.createCell(Constants.SHORT14).setCellValue(shipOrderItemDTO2.getpName());
									row.createCell(Constants.SHORT15).setCellValue(shipOrderItemDTO2.getSkuName());
									row.createCell(Constants.SHORT16).setCellValue(shipOrderItemDTO2.getPirce()+"");
									row.createCell(Constants.SHORT17).setCellValue(shipOrderItemDTO2.getQty()+"");
							
									row.createCell(Constants.SHORT18).setCellValue(totalprice);
									row.createCell(Constants.SHORT20).setCellValue(createtime);
									row.createCell(Constants.SHORT19).setCellValue(statu);
									
									row.createCell(Constants.SHORT1).setCellValue(shipOrderDTO2.getPayId()+"");
								}else{
									row.createCell(Constants.SHORT1).setCellValue(shipOrderDTO2.getOrderId()+"");
									row.createCell(Constants.SHORT6).setCellValue(shipOrderDTO2.getUserName());
									row.createCell(Constants.SHORT7).setCellValue(shipOrderDTO2.getUserId());
									row.createCell(Constants.SHORT8).setCellValue(shipOrderItemDTO2.getPid()+"");
									row.createCell(Constants.SHORT9).setCellValue(shipOrderItemDTO2.getSkuCode());
									row.createCell(Constants.SHORT10).setCellValue(shipOrderItemDTO2.getpName());
									row.createCell(Constants.SHORT11).setCellValue(shipOrderItemDTO2.getSkuName());
									row.createCell(Constants.SHORT12).setCellValue(shipOrderItemDTO2.getPirce()+"");
									row.createCell(Constants.SHORT13).setCellValue(shipOrderItemDTO2.getQty()+"");
							
									row.createCell(Constants.SHORT14).setCellValue(totalprice);
									row.createCell(Constants.SHORT15).setCellValue(statu);
									row.createCell(Constants.SHORT16).setCellValue(createtime);
								}	
							}
						}	
					}
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.error("RetailerShipOrderService<findRetailerShipOrderList()> is failed!!!! "+e.getMessage(),e);
			}
			LOGGER.info("拼装信息完成!");
	    }
		
		try {
			String filename = "retailer-order-information";
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
}
*/