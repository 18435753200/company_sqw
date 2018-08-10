package com.mall.controller.order;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
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

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.customer.order.api.rpc.ContainerLoadPlanSevice;
import com.mall.customer.order.api.rpc.CustomerOrderService;
import com.mall.customer.order.dto.LoadPlanDTO;
import com.mall.customer.order.dto.OrderDTO;
import com.mall.customer.order.dto.OrderItemDTO;
import com.mall.customer.order.dto.ProductDTO;
import com.mall.customer.order.dto.SkuDTO;
import com.mall.mybatis.utility.PageBean;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Common;
import com.mall.utils.Constants;


/**
 * @Title：WofePurchaseOrderController
 * @Description:经销商货物装箱单管理<海外转运仓集货装运>
 * @author Guofy
 * @date 2015年3月31日12:11:06
 */
@Controller
@RequestMapping(value = "/containerLoadPlan")
public class ContainerLoadPlanOrderController extends BaseController {

	
	/**.
	 * LOGGER(日志打印)
	 */
	private static final Log LOGGER = LogFactory.getLogger(ContainerLoadPlanOrderController.class);
	
	
	/**.
	 * 新建的货物装箱单
	 * @return String
	 */
	@RequestMapping(value = "/getContainerLoadPlanOrder")
	public String getContainerLoadPlanOrder(){
		LOGGER.info("新建的货物装箱单列表显示");
		return "/containerloadplan/containerorder_list";
	}
	
	
	/**.
	 * @Title：getCLPOrderPageBeanByCondition
	 * @Description: 根据传入的条件查询装箱单列表
	 * @param Integer page
	 * @param Model model
	 * @param Long id
	 * @param String startTime
	 * @param String endTime
	 * @param Short  status
	 * @return String
	 */
	@RequestMapping(value = "/getCLPOrderPageBeanByCondition")
	public String getCLPOrderPageBeanByCondition(Integer page,Model model,Long planId,String startTime,String endTime, String loadCode,String createBy){
		LOGGER.info("start to execute method <getCLPOrderPageBeanByCondition()根据条件查询装箱单的列表>!!!!");
		LOGGER.info("装箱单ID:"+planId);
		LOGGER.info("当前是第:"+page+"页");
		LOGGER.info("起始时间:"+startTime);
		LOGGER.info("结束时间:"+endTime);
		LOGGER.info("查询的状态:"+loadCode);
		LOGGER.info("登陆用户<userId>:"+getCurrentUser().getId());
		ContainerLoadPlanSevice containerLoadPlanSevice = RemoteServiceSingleton.getInstance().getContainerLoadPlanSevice();
		PageBean<LoadPlanDTO> pageBean = new PageBean<LoadPlanDTO>();
		LoadPlanDTO loadPlanDTO = new LoadPlanDTO();
		//代表是wofe用户查询
		loadPlanDTO.setType(Constants.SHORT1);
		if(null != planId){
			loadPlanDTO.setId(planId);
		}
		if(!Common.isEmpty(loadCode)){
			loadPlanDTO.setLoadCode(loadCode);
		}
		if(!Common.isEmpty(startTime)){
			loadPlanDTO.setStartDate(Common.stringToDate(startTime.replace("-", "/"), "yyyy/MM/dd HH:mm:ss"));
		}
		if(!Common.isEmpty(endTime)){
			loadPlanDTO.setEndDate(Common.stringToDate(endTime.replace("-", "/"), "yyyy/MM/dd HH:mm:ss"));
		}
		if(!Common.isEmpty(createBy)){
			loadPlanDTO.setCreateBy(createBy);
		}
		if(null != page){
			pageBean.setPage(page);
		}else{
			pageBean.setPage(1);
		}
		pageBean.setPageSize(Constants.PAGESIZE);
		pageBean.setParameter(loadPlanDTO);
		try {
			pageBean = containerLoadPlanSevice.findAllLoadPlanVoPage(pageBean);
			if(null != pageBean){
				List<LoadPlanDTO> LoadPlanDTOs = pageBean.getResult();
				if(null != LoadPlanDTOs && LoadPlanDTOs.size()>0){
					for (LoadPlanDTO loadPlanDTO2 : LoadPlanDTOs) {
						List<ProductDTO> productDTOList = loadPlanDTO2.getProList();
						if(null != productDTOList && productDTOList.size()>0){
							for (ProductDTO productDTO : productDTOList) {
								if(null != productDTO){
									String productImage = productDTO.getImgUrl();
									if(!Common.isEmpty(productImage)){
										if(!productImage.startsWith("http") || !productImage.startsWith("Http")){
											productImage = Constants.P1+productImage;
											productDTO.setImgUrl(productImage);
										}
									}
								}
							}
						}
					}
				}
			}
			model.addAttribute("pageBean",pageBean);
		} catch (Exception e) {
			LOGGER.info("ContainerLoadPlanSevice<findAllLoadPlanVoPage(pageBean) 根据条件查询装箱单的列表> is failed!!!!"+e.getMessage(),e);
		}
		LOGGER.info("end<> to execute method <getCLPOrderPageBeanByCondition()根据条件查询装箱单的列表>!!!!");
		return "/containerloadplan/containerorder_list_model";	
	}

	
	/**
	 * @Title：goDelLoadPlanById
	 * @Description: 经销商删除装箱单.
	 * @param Long planId
	 * @return String
	 */
	@RequestMapping(value = "/goDelLoadPlanById")
	@ResponseBody
	public String goDelLoadPlanById(Long planId){
		LOGGER.info("start to execute method <goDelLoadPlanById()根据装箱单号删除装箱单>!!!!");
		LOGGER.info("装箱单ID:"+planId);
		String message = "";
		String createBy = getCurrentUser().getUsername();
		boolean flag=true;
		try {
			ContainerLoadPlanSevice containerLoadPlanSevice = RemoteServiceSingleton.getInstance().getContainerLoadPlanSevice();
			flag = containerLoadPlanSevice.delLoadPlanById(planId, createBy, Constants.SHORT1);
			if(flag){
				message = "删除成功!";
			}else{
				message = "删除失败!";
			}
		} catch (Exception e) {
			LOGGER.error("ContainerLoadPlanSevice<delLoadPlanById()经销商删除装箱单 is failed!!!!>"+e.getMessage(),e);
			message = "删除失败!";
			
		}
		LOGGER.info("end<> to execute method <goDelLoadPlanById()根据装箱单号删除装箱单>!!!!");
		return message;
	}
	
	
	/**.
	 * @Title：showUpdateContentList
	 * @Description: 经销商根据ID获取所有待修改的东西. 
	 * @param Long planId
	 * @param Model model
	 * @param Integer page
	 * @return String
	 */
	@RequestMapping(value = "/showUpdateContentList")
	public String showUpdateContentList(Long planId,Model model,Integer page,String checkTime){
		LOGGER.info("start to execute method <goConfirmLoadPlanByPlanId()根据装箱单号查询需要修改的装箱单>!!!!");
		LOGGER.info("装箱单ID:"+planId);
		LOGGER.info("通过海关 审核时间:"+checkTime);
		LoadPlanDTO loadPlanDTO = new LoadPlanDTO(); 
		if(null != planId){
			try {
				ContainerLoadPlanSevice containerLoadPlanSevice = RemoteServiceSingleton.getInstance().getContainerLoadPlanSevice();
				loadPlanDTO = containerLoadPlanSevice.findLoadPlanByPlanId(planId);
				if(null != loadPlanDTO){
					List<ProductDTO> productList = loadPlanDTO.getProList();
					if(null != productList && productList.size()>0){
						for (ProductDTO productDTO : productList) {
							List<SkuDTO> skudto = productDTO.getSkuList();
							if(null!= skudto && skudto.size()>0){
								for (SkuDTO skuDTO2 : skudto) {
									if(null != skuDTO2){
										String imageUrl = skuDTO2.getImgUrl();
										if(!Common.isEmpty(imageUrl)){
											if(!imageUrl.startsWith("Http") || !imageUrl.startsWith("http")){
												imageUrl = Constants.P1+imageUrl;
												skuDTO2.setImgUrl(imageUrl);
											}
										}
									}
								}	
							}
						}
					}
					List<OrderDTO> orderDTOs = loadPlanDTO.getOrderIn();
					for (OrderDTO orderDTO : orderDTOs) {
						List<OrderItemDTO> orderItemDTOs = orderDTO.getOrderItemDTOs();
						if(null != orderItemDTOs && orderItemDTOs.size()>0){
							for (OrderItemDTO orderItemDTO : orderItemDTOs) {
								String imageUrl = orderItemDTO.getImgUrl();
								if(!Common.isEmpty(imageUrl)){
									if(!imageUrl.startsWith("Http") || !imageUrl.startsWith("http")){
										imageUrl = Constants.P1+imageUrl;
										orderItemDTO.setImgUrl(imageUrl);
									}
								}
							}
						}
					}
				}
				model.addAttribute("loadPlanDTO", loadPlanDTO);
			} catch (Exception e) {
				LOGGER.error("ContainerLoadPlanSevice<findLoadPlanByPlanId()>根据装箱单ID查询装单单的详情 is failed!!!!"+e.getMessage(),e);
			}
		}
		CustomerOrderService customerOrderService = RemoteServiceSingleton.getInstance().getCustomerOrderService();
		PageBean<OrderDTO> pageBean = new PageBean<OrderDTO>();
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setStatus(Constants.STATUS41);
		orderDTO.setIsBinning(Constants.SHORT0);
		if(!Common.isEmpty(checkTime)){
			orderDTO.setCheckTime(Timestamp.valueOf(checkTime));
		}
		pageBean.setPageSize(Constants.PAGESIZE);
		pageBean.setParameter(orderDTO);
		if(null != page){
			pageBean.setPage(page);
		}else{
			pageBean.setPage(1);
		}
		try {
			pageBean = customerOrderService.findNoBinNingOrders(pageBean);
			if(null != pageBean){
				List<OrderDTO> orderDTOs = pageBean.getResult();
				if(null != orderDTOs && orderDTOs.size()>0){
					for (OrderDTO orderDTO2 : orderDTOs) {
						List<OrderItemDTO> orderItemDTOs = orderDTO2.getOrderItemDTOs();
						if(null != orderItemDTOs && orderItemDTOs.size()>0){
							for (OrderItemDTO orderItemDTO : orderItemDTOs) {
								String imageUrl = orderItemDTO.getImgUrl();
								if(!Common.isEmpty(imageUrl)){
									if(!imageUrl.startsWith("Http") || !imageUrl.startsWith("http")){
										imageUrl = Constants.P1+imageUrl;
										orderItemDTO.setImgUrl(imageUrl);
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("CustomerOrderService<findCustomerOrdersByWofe()查询等待装箱的所有订单> is failed!!!"+e.getMessage(),e);
		}
		model.addAttribute("pageBean", pageBean);
		LOGGER.info("end<> to execute method <goConfirmLoadPlanByPlanId()根据装箱单号查询需要修改的装箱单>!!!!");
		if(!Common.isEmpty(checkTime)){
			return "/containerloadplan/containerorder_more_list";	
		}else{
			return "/containerloadplan/containerorder_update_list";	
		}
	}
	
	
	
	/**.
	 * @Title：goUpdateByPlanId
	 * @Description: 经销商根据ID修改装箱单.
	 * @param Long planId
	 * @return String
	 */
	@RequestMapping(value = "/goUpdateOrderByPlanId")
	@ResponseBody
	public String goUpdateOrderByPlanId(Long[] orderId,Long planId){
		LOGGER.info("start to execute method <goUpdateOrderByPlanId()根据装箱单号修改装箱单>!!!!");
		String message = "";
		String createBy = getCurrentUser().getUsername();
		LOGGER.info("修改操作人:"+createBy);
		LOGGER.info("装箱单ID:"+planId);
		List<Long> orderIds = new ArrayList<Long>();
		orderIds = Arrays.asList(orderId);
		LoadPlanDTO loadPlan = new LoadPlanDTO();
		loadPlan.setType(Constants.SHORT1);
		loadPlan.setLastEditBy(createBy);
		loadPlan.setOrderIdList(orderIds);
		loadPlan.setId(planId);
		boolean flag=true;
		try {
			ContainerLoadPlanSevice containerLoadPlanSevice = RemoteServiceSingleton.getInstance().getContainerLoadPlanSevice();
			flag = containerLoadPlanSevice.updateLoadPlanByPlanId(loadPlan);
			if(flag){
				message = "修改成功";
			}else{
				message = "修改失败";
			}
		} catch (Exception e) {
			LOGGER.error("ContainerLoadPlanSevice<updateLoadPlanByPlanId()经销商根据ID修改装箱单 is failed!!!!>"+e.getMessage(),e);
			message = "修改失败";
		}
		LOGGER.info("end<> to execute method <goUpdateOrderByPlanId()根据装箱单号修改装箱单>!!!!");
		return message;
	}
	
	
	
	/**.
	 * @Title：getLoadPlanDetailsByPlanId
	 * @Description: 根据装箱单ID查询装单单的详情.
	 * @param Long planId
	 * @param Model model
	 * @return
	 */
	@RequestMapping(value = "/getLoadPlanDetailsByPlanId" )
	public String getLoadPlanDetailsByPlanId(Long planId,Model model){
		LOGGER.info("start to execute method <getLoadPlanDetailsByPlanId()根据装箱单ID查询装单单的详情>!!!!");
		LoadPlanDTO loadPlanDTO = new LoadPlanDTO(); 
		try {
			ContainerLoadPlanSevice containerLoadPlanSevice = RemoteServiceSingleton.getInstance().getContainerLoadPlanSevice();
			loadPlanDTO = containerLoadPlanSevice.findLoadPlanByPlanId(planId);
			if(null != loadPlanDTO){
				List<ProductDTO> productDTOs = loadPlanDTO.getProList();
				
				if(null != productDTOs && productDTOs.size()>0){
					for (ProductDTO productDTO2 : productDTOs) {
						String imageUrl = productDTO2.getImgUrl();
						if(!Common.isEmpty(imageUrl)){
							if(!imageUrl.startsWith("Http") || !imageUrl.startsWith("http")){
								imageUrl = Constants.P1+imageUrl;
								productDTO2.setImgUrl(imageUrl);
							}
						}
						List<SkuDTO> skuDTOs = productDTO2.getSkuList();
						if(null != skuDTOs && skuDTOs.size()>0){
							for (SkuDTO skuDTO : skuDTOs) {
								String skuImageUrl = skuDTO.getImgUrl();
								if(!Common.isEmpty(skuImageUrl)){
									if(!skuImageUrl.startsWith("Http") || !skuImageUrl.startsWith("http")){
										skuImageUrl = Constants.P1+skuImageUrl;
										skuDTO.setImgUrl(skuImageUrl);
									}
								}	
							}
						}
					}
				}
				List<OrderDTO> orderDTOs = loadPlanDTO.getOrderIn();
				if(null != orderDTOs && orderDTOs.size()>0){
					for (OrderDTO orderDTO : orderDTOs) {
						List<OrderItemDTO> orderItemDTOs = orderDTO.getOrderItemDTOs();
						if(null != orderItemDTOs && orderItemDTOs.size()>0){
							for (OrderItemDTO orderItemDTO : orderItemDTOs) {
								String imageUrl = orderItemDTO.getImgUrl();
								if(!Common.isEmpty(imageUrl)){
									if(!imageUrl.startsWith("Http") || !imageUrl.startsWith("http")){
										imageUrl = Constants.P1+imageUrl;
										orderItemDTO.setImgUrl(imageUrl);
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("ContainerLoadPlanSevice<findLoadPlanByPlanId()>根据装箱单ID查询装单单的详情 is failed!!!!"+e.getMessage(),e);
		}
		model.addAttribute("loadPlanDTO", loadPlanDTO);
		LOGGER.info("end<> to execute method <getLoadPlanDetailsByPlanId()根据装箱单ID查询装单单的详情>!!!!");
		return "/containerloadplan/containerorder_list_details";
	}
	
	
	
	
	/**.
	 * 导出装箱订单的合同和发货单.
	 * @param response
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/exportLoadPlanListByPlanIds")
	public void exportLoadPlanListByPlanIds(Long[] planId ,HttpServletResponse response){ 
		LOGGER.info("start to execute method <exportLoadPlanListByPlanIds()根据装箱单ID集合获取导出的信息 >!!!!");
		LOGGER.info("planId"+planId);
		List<Long> planIdLists = new ArrayList<Long>();
		planIdLists = Arrays.asList(planId);
		List<LoadPlanDTO> loadPlanDTOList = new  ArrayList<LoadPlanDTO>();
		Map<String, String> map = new HashMap<String, String>();
		ContainerLoadPlanSevice containerLoadPlanSevice = RemoteServiceSingleton.getInstance().getContainerLoadPlanSevice();
		try {
			loadPlanDTOList= containerLoadPlanSevice.exportloadPlanList(planIdLists);
			map = containerLoadPlanSevice.getSaleContract();
		} catch (Exception e) {
			LOGGER.error("ContainerLoadPlanSevice<exportloadPlanList()>根据装箱单ID集合获取导出的信息 is failed!!!!"+e.getMessage(),e);
		}
		HSSFWorkbook book = new HSSFWorkbook(); 
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyyMMddHHmmss");
		HSSFSheet sheet1 = book.createSheet("Invoice");	
		HSSFSheet sheet = book.createSheet("Packing");
		HSSFSheet sheet2 = book.createSheet("Contract");	
		
		HSSFFont font = book.createFont();    
		font.setFontName("Times New Roman");    
		font.setFontHeightInPoints((short) 13);
		
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
	    style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体    
	    style.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色．    
	    style.setFillForegroundColor(HSSFColor.WHITE.index);// 设置单元格的背景颜色．
	    
	    // 另一个字体样式    
	    HSSFFont packFont = book.createFont();   
	    packFont.setFontName("Times New Roman");    
	    packFont.setFontHeightInPoints((short) 18);    
	    packFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	    
	    HSSFCellStyle packtyle = book.createCellStyle();    
	    packtyle.setFont(packFont);    
	    packtyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中    
	    packtyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中  
	    
	    // 另一个字体样式    
	    HSSFFont columnHeadFont = book.createFont();
	    columnHeadFont.setFontName("Times New Roman");    
	    columnHeadFont.setFontHeightInPoints((short) 13);    
	    
	    // 列头的样式    
	    HSSFCellStyle textStyle = book.createCellStyle();
	    textStyle.setFont(columnHeadFont);
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

	    sheet.setColumnWidth(0, 6500);    
	    sheet.setColumnWidth(1, 9000);    
	    sheet.setColumnWidth(2, 6500);    
	    sheet.setColumnWidth(3, 4000);    
	    sheet.setColumnWidth(4, 4000);   
	    sheet.setColumnWidth(5, 4000);
		HSSFRow row = sheet.createRow((int)0);
		HSSFCell cell=null;
		
		row = sheet.createRow(0);
		cell = row.createCell(Constants.SHORT0);
		cell.setCellStyle(textStyle);
		cell.setCellValue("KUA JING DIANSHANG   GROUP LIMITED");
		cell.setCellValue(map.get("sale_contract_seller"));
		
		row = sheet.createRow(1);
		cell = row.createCell(Constants.SHORT0);
		cell.setCellStyle(textStyle);
		cell.setCellValue(map.get("sale_contract_seller_address"));
		
		row = sheet.createRow(3);   
		cell = row.createCell(Constants.SHORT0);
		Region  range = new Region(3, (short)0, 3, (short)6);  
		sheet.addMergedRegion(range); 
		cell.setCellStyle(packtyle);
		cell.setCellValue("PACKING LIST");
		
		row = sheet.createRow(4);
		cell = row.createCell(Constants.SHORT0);
		cell.setCellStyle(textStyle);
		cell.setCellValue("Invoice No.:");
		cell = row.createCell(Constants.SHORT1);
		cell.setCellValue(simpleDateFormat1.format(date));
		cell.setCellStyle(textStyle);
		cell = row.createCell(Constants.SHORT2);
		cell.setCellStyle(textStyle);
		cell.setCellValue("Date:");
		cell = row.createCell(Constants.SHORT3);
		cell.setCellStyle(textStyle);
		cell.setCellValue(simpleDateFormat.format(date));
		
		row = sheet.createRow(5);
		cell = row.createCell(Constants.SHORT0);
		cell.setCellStyle(textStyle);
		cell.setCellValue("Sold To:");
		cell = row.createCell(Constants.SHORT1);
		cell.setCellStyle(textStyle);
		cell.setCellValue(map.get("sale_contract_buyer_address"));

		row = sheet.createRow(7);
		cell = row.createCell(Constants.SHORT0);
		cell.setCellStyle(textStyle);
		cell.setCellValue("Ship To:");
		cell = row.createCell(Constants.SHORT1);
		cell.setCellStyle(textStyle);
		cell.setCellValue(map.get("sale_contract_ship_to"));
		
		row = sheet.createRow(8);
		cell = row.createCell(Constants.SHORT1);
		cell.setCellStyle(textStyle);
		cell.setCellValue(map.get("sale_contract_ship_to_address"));

		row = sheet.createRow(9);
		cell = row.createCell(Constants.SHORT0);
		cell.setCellStyle(textStyle);
		cell.setCellValue("Trade Term: ");
		cell = row.createCell(Constants.SHORT2);
		cell.setCellStyle(textStyle);
		cell.setCellValue("Payment: ");

		row = sheet.createRow(10);
		cell = row.createCell(Constants.SHORT0);
		cell.setCellStyle(textStyle);
		cell.setCellValue("From: ");

		cell = row.createCell(Constants.SHORT2);
		cell.setCellStyle(textStyle);
		cell.setCellValue("To: ");

		
		row = sheet.createRow(11);
		cell = row.createCell(Constants.SHORT0);
		sheet.addMergedRegion(new Region(11, (short)0, 11, (short)1));
		cell.setCellStyle(columnHeadStyle);
		cell.setCellValue("Description");
		cell = row.createCell(Constants.SHORT1);
		cell.setCellStyle(columnHeadStyle);
		String [] strtitle={"Packing NO.","O.QTY","N.W","G.W"};
		for (int i = 0; i < strtitle.length; i++) {
			cell = row.createCell((short)i+2);
			cell.setCellValue(strtitle[i]);
			cell.setCellStyle(columnHeadStyle);
		}
		int r = 12;
		int totalCount1 = 0;
		BigDecimal totalWeight = new BigDecimal(0);
		BigDecimal totalVolume = new BigDecimal(0);
		Map<Long,String> regionMap = new HashMap<Long, String>();
		
		if(null != loadPlanDTOList && loadPlanDTOList.size()>0){
			for (LoadPlanDTO loadPlanDTO : loadPlanDTOList) {
				List<ProductDTO> proList = loadPlanDTO.getProList();
				if(null != proList && proList.size()>0){
					for (ProductDTO productDTO : proList) {
						List<SkuDTO> skuDTO = productDTO.getSkuList();
						
						if(null != skuDTO && skuDTO.size()>0){
							for (SkuDTO skuDTO2 : skuDTO) {
								if(regionMap.containsKey(loadPlanDTO.getId())){
									regionMap.put(loadPlanDTO.getId(), regionMap.get(loadPlanDTO.getId()).split("#")[0]+"#"+r);
								}else{
									regionMap.put(loadPlanDTO.getId(), r+"#"+r);
								}
								row = sheet.createRow(r++);
								totalCount1 = totalCount1+skuDTO2.getQty();
								totalVolume =  totalVolume.add(skuDTO2.getVolume());
								totalWeight =  totalWeight.add(skuDTO2.getWeight());
								cell = row.createCell(Constants.SHORT0);
								cell.setCellStyle(style);
								cell.setCellValue(skuDTO2.getSkuId()+"");
								cell = row.createCell(Constants.SHORT1);
								cell.setCellStyle(style);
								cell.setCellValue(productDTO.getpName()+skuDTO2.getSkuNameCn());
								cell = row.createCell(Constants.SHORT2);
								cell.setCellStyle(style);
								cell.setCellValue(loadPlanDTO.getId()+"");
								cell = row.createCell(Constants.SHORT3);
								cell.setCellStyle(style);
								cell.setCellValue(skuDTO2.getQty()+"");
								cell = row.createCell(Constants.SHORT4);
								cell.setCellStyle(style);
								cell.setCellValue(skuDTO2.getWeight()+"");
								cell = row.createCell(Constants.SHORT5);
								cell.setCellStyle(style);
								cell.setCellValue(skuDTO2.getVolume()+"");	
							}
						}
					}
				}
			}
			
			for(Map.Entry<Long, String> entry:regionMap.entrySet()){
				int startRow = Integer.parseInt(entry.getValue().split("#")[0]);
				int endRow = Integer.parseInt(entry.getValue().split("#")[1]);
				if(startRow!=endRow){
					sheet.addMergedRegion(new Region(startRow, Constants.SHORT2, endRow, Constants.SHORT2));
					
				}
			}
			row = sheet.createRow(r);
			sheet.addMergedRegion(new Region(r, (short)0, r, (short)1));
			cell = row.createCell(Constants.SHORT0);
			cell.setCellStyle(style);
			cell.setCellValue("TOTAL");
			cell = row.createCell(Constants.SHORT1);
			cell.setCellStyle(style);
			cell = row.createCell(Constants.SHORT2);
			cell.setCellStyle(style);
	
			cell = row.createCell(Constants.SHORT3);
			cell.setCellValue(totalCount1);
			cell.setCellStyle(style);
			cell = row.createCell(Constants.SHORT4);
			cell.setCellStyle(style);
			cell.setCellValue(totalWeight+"");
			cell = row.createCell(Constants.SHORT5);
			cell.setCellStyle(style);
			cell.setCellValue(totalVolume+"");
			
			row = sheet.createRow(r+6);
			cell = row.createCell(Constants.SHORT2);
			cell.setCellStyle(textStyle);
			cell.setCellValue("Signature:");
		}
		
		    sheet1.setColumnWidth(0, 6500);    
		    sheet1.setColumnWidth(1, 9000);    
		    sheet1.setColumnWidth(2, 6500);    
		    sheet1.setColumnWidth(3, 4000);    
		    sheet1.setColumnWidth(4, 4000);   
		    sheet1.setColumnWidth(5, 4000);
			HSSFRow row1 = sheet1.createRow((int)0);
			HSSFCell cell1=null;

			row = sheet1.createRow(0);
			cell1 = row1.createCell(Constants.SHORT0);
			cell1.setCellStyle(textStyle);
			cell1.setCellValue("KUA JING DIANSHANG   GROUP LIMITED");
			cell1.setCellValue(map.get("sale_contract_seller"));
			
			row1 = sheet1.createRow(1);
			cell1 = row1.createCell(Constants.SHORT0);
			cell1.setCellStyle(textStyle);
			cell1.setCellValue(map.get("sale_contract_seller_address"));
			
			row1 = sheet1.createRow(3);   
			cell1 = row1.createCell(Constants.SHORT0);
			sheet1.addMergedRegion(new Region(3, (short)0, 3, (short)5)); 
			cell1.setCellStyle(packtyle);
			cell1.setCellValue("C O M M E R C I A L     I N V O I C E");
			
			row1 = sheet1.createRow(4);
			cell1 = row1.createCell(Constants.SHORT0);
			cell1.setCellStyle(textStyle);
			cell1.setCellValue("Invoice No.:");
			cell1 = row1.createCell(Constants.SHORT1);
			cell1.setCellValue(simpleDateFormat1.format(date));
			cell1.setCellStyle(textStyle);
			cell1 = row1.createCell(Constants.SHORT2);
			cell1.setCellStyle(textStyle);
			cell1.setCellValue("Date:");
			cell1 = row1.createCell(Constants.SHORT3);
			cell1.setCellStyle(textStyle);
			cell1.setCellValue(simpleDateFormat.format(date));

			row1 = sheet1.createRow(5);
			cell1 = row1.createCell(Constants.SHORT0);
			cell1.setCellStyle(textStyle);
			cell1.setCellValue("Sold To:");
			cell1 = row1.createCell(Constants.SHORT1);
			cell1.setCellStyle(textStyle);
			cell1.setCellValue(map.get("sale_contract_buyer_address"));

			row1 = sheet1.createRow(7);
			cell1 = row1.createCell(Constants.SHORT0);
			cell1.setCellStyle(textStyle);
			cell1.setCellValue("Ship To:");
			cell1 = row1.createCell(Constants.SHORT1);
			cell1.setCellStyle(textStyle);
			cell1.setCellValue(map.get("sale_contract_ship_to"));

			row1 = sheet1.createRow(8);
			cell1 = row1.createCell(Constants.SHORT1);
			cell1.setCellStyle(textStyle);
			cell1.setCellValue(map.get("sale_contract_ship_to_address"));
			
			row1 = sheet1.createRow(9);
			cell1 = row1.createCell(Constants.SHORT0);
			cell1.setCellStyle(textStyle);
			cell1.setCellValue("Trade Term: ");
			cell1 = row1.createCell(Constants.SHORT2);
			cell1.setCellStyle(textStyle);
			cell1.setCellValue("Payment: ");

			row1 = sheet1.createRow(10);
			cell1 = row1.createCell(Constants.SHORT0);
			cell1.setCellStyle(textStyle);
			cell1.setCellValue("From: ");
			cell1 = row1.createCell(Constants.SHORT2);
			cell1.setCellStyle(textStyle);
			cell1.setCellValue("To: ");
			
			row1 = sheet1.createRow(11);
			cell1 = row1.createCell(Constants.SHORT0);
			sheet1.addMergedRegion(new Region(11, (short)0, 11, (short)1));
			cell1.setCellStyle(columnHeadStyle);
			cell1.setCellValue("Description");
			cell1 = row1.createCell(Constants.SHORT1);
			cell1.setCellStyle(columnHeadStyle);
			
			String [] strtitle1={"Country of Origin","Quantity","Unite Price","Total value (CNY)"};
			for (int i = 0; i < strtitle1.length; i++) {
				cell1 = row1.createCell((short)i+2);
				cell1.setCellValue(strtitle1[i]);
				cell1.setCellStyle(columnHeadStyle);
			}
			int r1 = 12;
			int totalQty = 0;
			BigDecimal totalPrice = new BigDecimal(0);
			if(null != loadPlanDTOList && loadPlanDTOList.size()>0){
				for (LoadPlanDTO loadPlanDTO : loadPlanDTOList) {
					List<ProductDTO> proList = loadPlanDTO.getProList();
					if(null != proList && proList.size()>0){
						for (ProductDTO productDTO : proList) {
							List<SkuDTO> skuDTO = productDTO.getSkuList();
							
							if(null != skuDTO && skuDTO.size()>0){
								for (SkuDTO skuDTO2 : skuDTO) {
									
									row1 = sheet1.createRow(r1++);
									cell1 = row1.createCell(Constants.SHORT0);
									cell1.setCellStyle(style);
									cell1.setCellValue(skuDTO2.getSkuId()+"");
									cell1 = row1.createCell(Constants.SHORT1);
									cell1.setCellStyle(style);
									cell1.setCellValue(productDTO.getpName()+skuDTO2.getSkuNameCn());
									cell1 = row1.createCell(Constants.SHORT2);
									cell1.setCellStyle(style);
									cell1.setCellValue(productDTO.getOriginplaceNameEn()+"");
									cell1 = row1.createCell(Constants.SHORT3);
									cell1.setCellStyle(style);
									cell1.setCellValue(skuDTO2.getQty()+"");
									cell1 = row1.createCell(Constants.SHORT4);
									cell1.setCellStyle(style);
									cell1.setCellValue(skuDTO2.getPrice()+"");
									cell1 = row1.createCell(Constants.SHORT5);
									cell1.setCellStyle(style);
									cell1.setCellValue(skuDTO2.getPrice().multiply(new BigDecimal(skuDTO2.getQty()))+"");
								}
							}
						}
					}
				}

				row1 = sheet1.createRow(r1);
				sheet1.addMergedRegion(new Region(r1, (short)0, r1, (short)1));
				cell = row1.createCell(Constants.SHORT0);
				cell.setCellStyle(style);
				cell.setCellValue("TOTAL");
				cell = row1.createCell(Constants.SHORT1);
				cell.setCellStyle(style);
				cell = row1.createCell(Constants.SHORT2);
				cell.setCellStyle(style);
				cell = row1.createCell(Constants.SHORT3);
				cell.setCellStyle(style);
				cell.setCellValue(String.valueOf(totalQty));
				cell = row1.createCell(Constants.SHORT4);
				cell.setCellStyle(style);
				cell = row1.createCell(Constants.SHORT5);
				cell.setCellStyle(style);
				cell.setCellValue(totalPrice+"");

				row1 = sheet1.createRow(r1+6);
				cell1 = row1.createCell(Constants.SHORT2);
				cell1.setCellStyle(textStyle);
				cell1.setCellValue("Signature:");
			}

			 	sheet2.setColumnWidth(0, 6500);    
			 	sheet2.setColumnWidth(1, 9000);    
			 	sheet2.setColumnWidth(2, 6500);    
			 	sheet2.setColumnWidth(3, 4000);    
			 	sheet2.setColumnWidth(4, 4000);   
			 	sheet2.setColumnWidth(5, 4000);
				HSSFRow row2 = sheet2.createRow((int)0);
				HSSFCell cell2 = null;

				row2 = sheet2.createRow(0);
				cell2 = row2.createCell(Constants.SHORT0);
				cell2.setCellStyle(textStyle);
				cell2.setCellValue(map.get("sale_contract_seller"));
				
				row2 = sheet2.createRow(1);
				cell2 = row2.createCell(Constants.SHORT0);
				cell2.setCellStyle(textStyle);
				cell2.setCellValue(map.get("sale_contract_seller_address"));
				
				row2 = sheet2.createRow(3);   
				cell2 = row2.createCell(Constants.SHORT0);
				sheet2.addMergedRegion(new Region(3, (short)0, 3, (short)5)); 
				cell2.setCellStyle(packtyle);
				cell2.setCellValue("SALE  CONTRACT");
				
				row2 = sheet2.createRow(4);
				cell2 = row2.createCell(Constants.SHORT0);
				cell2.setCellStyle(textStyle);
				cell2.setCellValue("Invoice No.:");
				cell2 = row2.createCell(Constants.SHORT1);
				cell2.setCellValue(simpleDateFormat1.format(date));
				cell2.setCellStyle(textStyle);
				cell2 = row2.createCell(Constants.SHORT2);
				cell2.setCellStyle(textStyle);
				cell2.setCellValue("Date:");
				cell2 = row2.createCell(Constants.SHORT3);
				cell2.setCellStyle(textStyle);
				cell2.setCellValue(simpleDateFormat.format(date));

				row2 = sheet2.createRow(5);
				cell2 = row2.createCell(Constants.SHORT0);
				cell2.setCellStyle(textStyle);
				cell2.setCellValue("SELLER:");
				cell2 = row2.createCell(Constants.SHORT1);
				cell2.setCellStyle(textStyle);
				cell2.setCellValue(map.get("sale_contract_seller"));
				
				row2 = sheet2.createRow(6);
				cell2 = row2.createCell(Constants.SHORT1);
				cell2.setCellStyle(textStyle);
				cell2.setCellValue(map.get("sale_contract_seller_address"));
				
				row2 = sheet2.createRow(7);
				cell2 = row2.createCell(Constants.SHORT0);
				cell2.setCellStyle(textStyle);
				cell2.setCellValue("BUYER:");
				cell2 = row2.createCell(Constants.SHORT1);
				cell2.setCellStyle(textStyle);
				cell2.setCellValue(map.get("sale_contract_buyer"));
				
				row2 = sheet2.createRow(8);
				cell2 = row2.createCell(Constants.SHORT1);
				cell2.setCellStyle(textStyle);
				cell2.setCellValue(map.get("sale_contract_buyer_address"));
				
				row2 = sheet2.createRow(9);
				cell2 = row2.createCell(Constants.SHORT0);
				cell2.setCellStyle(textStyle);
				cell2.setCellValue("SHIP TO:");
				cell2 = row2.createCell(Constants.SHORT1);
				cell2.setCellStyle(textStyle);
				cell2.setCellValue(map.get("sale_contract_ship_to"));

				row2 = sheet2.createRow(10);
				cell2 = row2.createCell(Constants.SHORT1);
				cell2.setCellStyle(textStyle);
				cell2.setCellValue(map.get("sale_contract_ship_to_address"));
				
				row2 = sheet2.createRow(11);
				cell2 = row2.createCell(Constants.SHORT0);
				cell2.setCellStyle(textStyle);
				cell2.setCellValue("Trade Term: ");
				cell2 = row2.createCell(Constants.SHORT2);
				cell2.setCellStyle(textStyle);
				cell2.setCellValue("Payment: ");

				row2 = sheet2.createRow(12);
				cell2 = row2.createCell(Constants.SHORT0);
				cell2.setCellStyle(textStyle);
				cell2.setCellValue("From: ");
				cell2 = row2.createCell(Constants.SHORT2);
				cell2.setCellStyle(textStyle);
				cell2.setCellValue("To: ");


				
				row2 = sheet2.createRow(13);
				cell2 = row2.createCell(Constants.SHORT0);
				sheet2.addMergedRegion(new Region(13, (short)0, 13, (short)1));
				cell2.setCellStyle(columnHeadStyle);
				cell2.setCellValue("Description");
				cell2 = row2.createCell(Constants.SHORT1);
				cell2.setCellStyle(columnHeadStyle);
				
		
				for (int i = 0; i < strtitle1.length; i++) {
					cell2 = row2.createCell((short)i+2);
					cell2.setCellValue(strtitle1[i]);
					cell2.setCellStyle(columnHeadStyle);
				}
				int r2 = 14;
				
				if(null != loadPlanDTOList && loadPlanDTOList.size()>0){
					for (LoadPlanDTO loadPlanDTO : loadPlanDTOList) {
						List<ProductDTO> proList = loadPlanDTO.getProList();
						if(null != proList && proList.size()>0){
							for (ProductDTO productDTO : proList) {
								List<SkuDTO> skuDTO = productDTO.getSkuList();
								r2=r2++;
								if(null != skuDTO && skuDTO.size()>0){
									for (SkuDTO skuDTO2 : skuDTO) {
										totalQty = totalQty+skuDTO2.getQty();
										totalPrice =  totalPrice.add(skuDTO2.getPrice().multiply(new BigDecimal(skuDTO2.getQty())));
										row2 = sheet2.createRow(r2++);
										cell2 = row2.createCell(Constants.SHORT0);
										cell2.setCellStyle(style);
										cell2.setCellValue(skuDTO2.getSkuId()+"");
										cell2 = row2.createCell(Constants.SHORT1);
										cell2.setCellStyle(style);
										cell2.setCellValue(productDTO.getpName()+skuDTO2.getSkuNameCn());
										cell2 = row2.createCell(Constants.SHORT2);
										cell2.setCellStyle(style);
										cell2.setCellValue(productDTO.getOriginplaceNameEn()+"");
										cell2 = row2.createCell(Constants.SHORT3);
										cell2.setCellStyle(style);
										cell2.setCellValue(skuDTO2.getQty()+"");
										cell2 = row2.createCell(Constants.SHORT4);
										cell2.setCellStyle(style);
										cell2.setCellValue(skuDTO2.getPrice()+"");
										cell2 = row2.createCell(Constants.SHORT5);
										cell2.setCellStyle(style);
										cell2.setCellValue(skuDTO2.getPrice().multiply(new BigDecimal(skuDTO2.getQty()))+"");
									}
								}
							}
						}
					}
					row2 = sheet2.createRow(r2);
					sheet2.addMergedRegion(new Region(r2, (short)0, r2, (short)2));
					cell = row2.createCell(Constants.SHORT0);
					cell.setCellStyle(style);
					cell.setCellValue("TOTAL");
					cell = row2.createCell(Constants.SHORT1);
					cell.setCellStyle(style);
					cell = row2.createCell(Constants.SHORT2);
					cell.setCellStyle(style);
					cell = row2.createCell(Constants.SHORT3);
					cell.setCellStyle(style);
					cell.setCellValue(String.valueOf(totalQty));
					cell = row2.createCell(Constants.SHORT4);
					cell.setCellStyle(style);
					cell = row2.createCell(Constants.SHORT5);
					cell.setCellStyle(style);
					cell.setCellValue(totalPrice+"");
					
					row2 = sheet2.createRow(r2+4);
					cell2 = row2.createCell(Constants.SHORT0);
					cell2.setCellStyle(textStyle);
					cell2.setCellValue("SELLER(SIGNATURE):    ");
					cell2 = row2.createCell(Constants.SHORT2);
					cell2.setCellStyle(textStyle);
					cell2.setCellValue("BUYER(SIGNATURE):  ");
		
				}
			
		try {
			String filename = "dealer_package_list"+simpleDateFormat1.format(date);
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
			LOGGER.error("下载文件时出现异常!!!!");
		}
		LOGGER.info("end<> to execute method <exportLoadPlanListByPlanIds()根据装箱单ID集合获取导出的信息 >!!!!");
	}
}
 