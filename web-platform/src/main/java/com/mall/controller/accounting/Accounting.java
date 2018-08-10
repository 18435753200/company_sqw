package com.mall.controller.accounting;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.controller.base.BaseController;
/*import com.mall.retailer.model.Retailer;*/

/**.
 * 账务
 * @author Zhouzb
 *
 */
@RequestMapping(value="/accounting")
@Controller
public class Accounting extends BaseController{
	/**.
	 * Accounting LOGGER
	 */
	private static final Log LOGGER = LogFactory.getLogger(Accounting.class);
	
	/**.
	 * 此方法用于日期的转换
	 * @param binder WebDataBinder
	 */ 
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	/**
	 * @return String
	 */
	@RequestMapping(value="/getAccountingList",method=RequestMethod.GET)
	public String getAccountingListPage(){
		return "/dealerseller/accounting/accounting";
	}
	
	/**
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param orderPayItemVo OrderPayItemVo
	 * @param page Integer
	 * @return String
	 */
	/*@RequestMapping(value="/getAccountingList",method=RequestMethod.POST)
	public String getAccountingList(
			HttpServletRequest request,HttpServletResponse response,
			OrderPayItemVo orderPayItemVo,Integer page){
		
		PageBean<OrderPayItemVo> pageBeanParameter = new PageBean<OrderPayItemVo>();
		
		try {
			if(page!=null){
				pageBeanParameter.setPage(page);			
			}else{
				pageBeanParameter.setPage(Constants.DEFAULTPAGE);		
			}
			pageBeanParameter.setPageSize(Constants.PAGESIZE);
			
			pageBeanParameter.setParameter(orderPayItemVo);
			
			PageBean<OrderPayItemVo> downAccountPage = RemoteServiceSingleton.getInstance()
					.getRetailerOrderpayService().downAccountPage(pageBeanParameter);
			List<OrderPayItemVo> result = downAccountPage.getResult();
			if(result!=null){
				LOGGER.info("查询账务信息列表result"+downAccountPage.getResult());
				FormatBigDecimal formatBigDecimal = new FormatBigDecimal();
				for(int i = 0 ; i < result.size() ; i++){
					BigDecimal orderAmount = result.get(i).getOrderAmount();
					BigDecimal priceFormat = formatBigDecimal.priceFormat(orderAmount);
					result.get(i).setOrderAmount(priceFormat);
					
					Retailer findNameByRetailerId = new Retailer();
					Long retailerId = result.get(i).getRetailerId();
					findNameByRetailerId = RemoteServiceSingleton.getInstance()
							.getRetailerManagerService().findByRetailerId(retailerId);
					result.get(i).setRetailerName(findNameByRetailerId.getName());
				}
				
			}
			
			request.getSession().setAttribute("pb",downAccountPage);
			request.getRequestDispatcher("/WEB-INF/views/dealerseller/accounting/modelpage/accounting_model.jsp")
			.forward(request, response);
			
			
		} catch (Exception e) {
			LOGGER.info("用户:"+getCurrentUser().getUsername());
			LOGGER.error("获取账务信息列表出错  msg："+e.getMessage(),e);
		}
		return null;
	}*/
	/**
	 * 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param page Integer
	 * @param orderPayItemVo OrderPayItemVo
	 * @return String
	 */
	/*@SuppressWarnings("deprecation")
	@RequestMapping(value="/downAccountByPage")
	public void downAccountByPage(HttpServletRequest request,HttpServletResponse response,
			Integer page,OrderPayItemVo orderPayItemVo){
		
		PageBean<OrderPayItemVo> pageBeanParameter = new PageBean<OrderPayItemVo>();
		HSSFWorkbook book = new HSSFWorkbook();
		OrderPayItemVo orderPay = new OrderPayItemVo();
		OutputStream os = null;
		
		try {
			
			request.setCharacterEncoding("UTF-8");
			
			if(page!=null){
				pageBeanParameter.setPage(page);			
			}else{
				pageBeanParameter.setPage(1);		
			}
			
			pageBeanParameter.setPageSize(Constants.NUM1000);
			pageBeanParameter.setParameter(orderPayItemVo);
			
			PageBean<OrderPayItemVo> downAccountPage =  RemoteServiceSingleton.getInstance()
					.getRetailerOrderpayService().downAccountPage(pageBeanParameter);
			Date date = new Date();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DEFAULTFORMATDATE);
			String format = simpleDateFormat.format(date);
			
			HSSFSheet sheet = book.createSheet(format+"账务第"+downAccountPage.getPage()+"页");
			
			sheet.setColumnWidth(Constants.HSSFSHEETROW_0, Constants.HSSFSHEETROW_150_WIDTH);
		    sheet.setColumnWidth(Constants.HSSFSHEETROW_1, Constants.HSSFSHEETROW_240_WIDTH);
		    sheet.setColumnWidth(Constants.HSSFSHEETROW_2, Constants.HSSFSHEETROW_240_WIDTH);
		    sheet.setColumnWidth(Constants.HSSFSHEETROW_3, Constants.HSSFSHEETROW_150_WIDTH);
		    sheet.setColumnWidth(Constants.HSSFSHEETROW_4, Constants.HSSFSHEETROW_120_WIDTH );
		    sheet.setColumnWidth(Constants.HSSFSHEETROW_5, Constants.HSSFSHEETROW_120_WIDTH );
		    sheet.setColumnWidth(Constants.HSSFSHEETROW_6, Constants.HSSFSHEETROW_120_WIDTH );
			
			HSSFRow row = sheet.createRow((int) 0);
			HSSFCellStyle style = book.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
			
			HSSFCell cell=null;
			String [] strtitle={"支付号","首付流水号","尾款流水号","订单号","日期","零售商","收入"};
			for(int i=0;i<strtitle.length;i++){
				cell=row.createCell(i);
				cell.setCellValue(strtitle[i]);
				cell.setCellStyle(style);			
			}
			List<OrderPayItemVo> result = downAccountPage.getResult();
			if(result!=null){
				for(int i = 0 ; i < result.size() ; i++ ){
					row = sheet.createRow((int) i + 1);
					orderPay = result.get(i);
					
					String payId = orderPay.getPayId()+"";
					String firstBankNo = orderPay.getFirstBankNo();
					if (null == firstBankNo){
						firstBankNo ="";
					}
					String lastBankNo = orderPay.getLastBankNo();
					if (null == lastBankNo){
						lastBankNo ="";
					}
					String orderId = orderPay.getOrderId()+"";
					String creatTime = simpleDateFormat.format(orderPay.getCreateTime())+"";
					String orderAmount = orderPay.getOrderAmount()+"".toString();
					
					Retailer findNameByRetailerId = new Retailer();
					Long retailerId = result.get(i).getRetailerId();
					findNameByRetailerId = RemoteServiceSingleton.getInstance()
							.getRetailerManagerService().findByRetailerId(retailerId);
					
					String retailerName = findNameByRetailerId.getName();
					
					row.createCell(Constants.SHORT0).setCellValue(payId);
					row.createCell(Constants.SHORT1).setCellValue(firstBankNo);
					row.createCell(Constants.SHORT2).setCellValue(lastBankNo);
					row.createCell(Constants.SHORT3).setCellValue(orderId);
					row.createCell(Constants.SHORT4).setCellValue(creatTime);
					row.createCell(Constants.SHORT5).setCellValue(retailerName);
					row.createCell(Constants.SHORT6).setCellValue(orderAmount);
					
				}
				LOGGER.info("拼装账务信息完成!");
				
				String filename = "WOFE-financial-management";
				
				os = response.getOutputStream();
				
				response.reset();
				
				response.setCharacterEncoding("UTF-8");
				
				filename = java.net.URLEncoder.encode(filename,"UTF-8");
				
				response.setHeader("Content-Disposition","attachment;filename="+
						new String(filename.getBytes("UTF-8"),"GBK")+".xls");
				
				response.setContentType("application/msexcel");//定义输出类型	
				
				book.write(os);  	
				
				response.getOutputStream().flush();
				response.getOutputStream().close();	
			}
			
		
			
		}catch (Exception e) {
			
			LOGGER.error("对账单下载出错  错误信息："+e.getMessage(),e);
			
		}finally{
			
			try {
				
				os.flush();
				os.close();
				
			} catch (Exception e) {
				
				LOGGER.error("下载查询的账务信息关闭流错误");
				LOGGER.error("对账单下载关闭流出错  错误信息："+e.getMessage(),e);
				
			} 
		}
		
	}*/
	/**.
	 * 
	 * @param model Model
	 * @param orderId Long
	 * @return String
	 */
	/*@RequestMapping(value="/getOrderInfo")
	public String getOrderInfo(Model model,Long orderId){
		
		LOGGER.info("账务订单详情：");
		LOGGER.info("用户:"+getCurrentUser().getUsername());
		LOGGER.info("用户ID:"+getCurrentPlatformId());
		LOGGER.info("订单ID:"+orderId);
		
		OrderService orderService = new OrderService();
		Retailer findByRetailerId = new Retailer();
		String responseRealAddress = null;
		
		try{
			OrderItemDetail findRetailerOrderItemByOrderId = 
					RemoteServiceSingleton.getInstance().getDealerOrderService().findRetailerOrderItemByOrderId(orderId);
			
			if(findRetailerOrderItemByOrderId==null){
				LOGGER.error("账务订单详情 查询 详情为空   出错 orderId ：" + orderId);
			}
			
			if(findRetailerOrderItemByOrderId!=null){
				
				Long retailerId = findRetailerOrderItemByOrderId.getOrder().getRetailerId();
				
				findByRetailerId = RemoteServiceSingleton.getInstance()
						.getRetailerManagerService().findByRetailerId(retailerId); 
				
				responseRealAddress = orderService.getAddressByAddressId(findByRetailerId);
				
				String productImgeUrl = findRetailerOrderItemByOrderId.getProductImgeUrl();
				
				if(productImgeUrl!=null){
					
					findRetailerOrderItemByOrderId.setProductImgeUrl(Constants.P1+productImgeUrl);
				
				}
				List<String> skuImgUrlList = findRetailerOrderItemByOrderId.getSkuImgUrlList() ;
				
				LOGGER.info("账务订单详情经销商Id："+retailerId);
				
				for( int i = 0 ; i < skuImgUrlList.size() ; i++ ){
					
					String imgUrl = skuImgUrlList.get(i);
					
					if(!imgUrl.startsWith("http")||!imgUrl.startsWith("Http")){
						
						imgUrl = Constants.P2+imgUrl;
						skuImgUrlList.set(i, imgUrl);
						
					}
				}
			}
			
			model.addAttribute("retailer",findByRetailerId);
			model.addAttribute("orderItem",findRetailerOrderItemByOrderId);
			model.addAttribute("responseRealAddress",responseRealAddress);
			
		}catch (Exception e) {
			
			LOGGER.error("账务订单详情错误："+e.getMessage(),e);
			LOGGER.error("用户:"+getCurrentUser().getUsername());
			LOGGER.error("订单ID:"+orderId);
			
		}
		
		return "/dealerseller/accounting/order_accountingdetail";
		
	}*/

}
