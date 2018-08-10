package com.mall.controller.order;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.customer.model.User;
import com.mall.customer.order.api.rpc.CustomerOrderExportSevice;
import com.mall.customer.order.api.rpc.CustomerOrderService;
import com.mall.customer.order.dto.B2cShipOrderDTO;
import com.mall.customer.order.dto.B2cShipOrderItemDTO;
import com.mall.customer.order.dto.OrderDTO;
import com.mall.customer.order.dto.OrderItemDTO;
import com.mall.customer.order.dto.OrderSourceEnum;
import com.mall.customer.order.dto.ProductDTO;
import com.mall.customer.order.dto.ThirdTypeEnum;
import com.mall.customer.order.po.Order;
import com.mall.customer.order.po.OrderItem;
import com.mall.customer.order.po.Overseas;
import com.mall.customer.service.UserService;
import com.mall.dealer.product.api.DealerProductSkuService;
import com.mall.dealer.product.po.LogisticTemp;
import com.mall.dealer.product.po.PurchasePricePO;
import com.mall.dealer.product.retailer.api.LogisticTempService;
//import com.mall.external.dto.RechargeReOrderDTO;
import com.mall.mybatis.utility.PageBean;
import com.mall.retailer.order.api.rpc.ShipOrderB2CService;
import com.mall.retailer.order.common.RetailerConstant;
import com.mall.retailer.order.dto.ShipOrderDTO;
import com.mall.retailer.order.dto.ShipOrderItemDTO;
import com.mall.stock.api.rpc.StockCustomerService;
import com.mall.stock.dto.HandleCustomerStockDto;
import com.mall.supplier.model.Supplier;
import com.mall.supplier.service.SupplierManagerService;
//import com.mall.wms.api.OutOrderService;
import com.mall.controller.base.BaseController;
import com.mall.controller.base.Result;
import com.mall.dto.SupplierSouces;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Common;
import com.mall.utils.Constants;

/**.
 * @Title CustomerOrderController
 * @Description: WOFE管理C客户的订单.
 * @author Guofy
 * @date 2015年4月7日10:28:58
 */
@Controller
@RequestMapping(value = "/customerOrder")
public class CustomerOrderController extends BaseController {

	/**.
	 * LOGGER日志打印.
	 */
	private static final Log LOGGER = LogFactory.getLogger(CustomerOrderController.class);
	
	
	/**.
	 * getCustomerOrderList
	 * @return String
	 */
	@RequestMapping(value = "/getCustomerOrderList",method = RequestMethod.GET)
	public String getCustomerOrderList(HttpServletRequest request){
		LOGGER.info("start to execute method <getCustomerOrderList>!!!!");
		request.setAttribute("_orderTypeList", ThirdTypeEnum.getMap());
		request.setAttribute("_orderSourceList",OrderSourceEnum.getMap());
		LOGGER.info("end<> to execute method <getCustomerOrderList>!!!!");
		return "/dealerseller/B2Corder/B2Corder_list";
	}
	
	@RequestMapping(value = "/getOverseasCorderList",method = RequestMethod.GET)
	public String getOverseasCorderList(HttpServletRequest request){
		LOGGER.info("start to execute method <getOverseasCorderList>!!!!");
		LOGGER.info("end<> to execute method <getOverseasCorderList>!!!!");
		return "/dealerseller/B2Corder/platform_overseasCorder_list";
	}
	
	@RequestMapping(value="/getOrderPushFailuerList",method = RequestMethod.GET)
	public String getCustomerOrderPushFailureList(){
		LOGGER.info("start to execute method <getCustomerOrderPushFailureList>!!!!");
		
		LOGGER.info("end<> to execute method <getCustomerOrderPushFailureList>!!!!");	
		return "/dealerseller/B2Corder/B2Corder_order_push_again";
	} 
	
	@RequestMapping(value="/platform_recharge_error_again",method = RequestMethod.GET)
	public String getCustomerRechangeErrorList(){
		LOGGER.info("start to execute method <getCustomerRechangeErrorList>!!!!");
		
		LOGGER.info("end<> to execute method <getCustomerRechangeErrorList>!!!!");	
		return "/dealerseller/B2Corder/B2Crechange_error_push_again";
	} 
	
	@RequestMapping(value="/orderCancelByUnfilledOrder",method = RequestMethod.GET)
	public String orderCancelByUnfilledOrder(){
		return "/dealerseller/B2Corder/B2Corder_order_cancel";
	} 
	
	@RequestMapping(value="/pushOrderAgain",method = RequestMethod.GET)
	public void pushOrderAgain(HttpServletResponse response,String id,String status){
		CustomerOrderService customerOrderService = RemoteServiceSingleton.getInstance().getCustomerOrderService();
		Result result = new Result();
		Order order = new Order();
		Short orderStatus = null;
		Long orderId = null;
		try {
			if(StringUtils.isBlank(id)){
				logger.info("订单编号不能为空 status:"+status);
				result.setSuccess(false);
				result.setMessage("订单编号不能为空");
				response.getWriter().write(JSONArray.toJSON(result).toString());
				return;
			}else{
				try {
					orderId = Long.parseLong(id);
					order.setId(orderId);
				} catch (NumberFormatException e) {
					logger.error("订单编号转换异常 orderId:"+id,e);
					result.setSuccess(false);
					result.setMessage("订单编号转换异常");
					response.getWriter().write(JSONArray.toJSON(result).toString());
					return;
				}			
			}
			if(StringUtils.isBlank(status)){
				logger.info("订单状态不能为空 status:"+status);
				result.setSuccess(false);
				result.setMessage("订单状态不能为空");
				response.getWriter().write(JSONArray.toJSON(result).toString());
				return;
			}else{
				try {
					orderStatus = Short.parseShort(status);
					order.setStatus(orderStatus);
				} catch (NumberFormatException e) {
					logger.error("订单状态转换异常 status:"+status,e);
					result.setSuccess(false);
					result.setMessage("订单状态转换异常");
					response.getWriter().write(JSONArray.toJSON(result).toString());
					return;
				}			
			}
			Integer resCode;
			try {
				resCode = customerOrderService.retryOrderAudit(order);
			} catch (Exception e) {
				resCode = 0;
				logger.info(e.getMessage(),e);
				result.setSuccess(false);
				result.setMessage("库存获取失败");
				response.getWriter().write(JSONArray.toJSON(result).toString());
				return;
			}
			if(resCode == 0){
				logger.info("订单状态转换异常 status:"+status);
				result.setSuccess(false);
				result.setMessage("订单状态修改失败");
				response.getWriter().write(JSONArray.toJSON(result).toString());
				return;				
			}else{
				result.setSuccess(true);
				result.setMessage("海关订单重推成功");
				response.getWriter().write(JSONArray.toJSON(result).toString());
				return;	
			}
		} catch (IOException e) {
			logger.error("pushOrderAgain is failed"+e.getMessage(),e);
		}		
	}
	
	
	/*@RequestMapping(value="/orderCancel",method = RequestMethod.GET)
	public void orderCancel(HttpServletResponse response,String id,String payId){
		Result result = new Result();
		try {
			CustomerOrderService customerOrderService = RemoteServiceSingleton.getInstance().getCustomerOrderService();
//			OutOrderService outOrderService = RemoteServiceSingleton.getInstance().getOutOrderService();
			boolean status = outOrderService.loadShipmentstateByOrderId(id);
			if(status == true){//已经发运
				String createBy = getCurrentUser().getUsername();
				String errorMsg = customerOrderService.addOrderCancelStatusRecord(Long.parseLong(id), "B2C",createBy);
				if(errorMsg != null && errorMsg.equals("")){
					String updateCancleOrderStatus = customerOrderService
							.updateCancleOrderStatus(
									Long.parseLong(id),
									Constants.B2C,
									Integer.parseInt(Constants.ORDERFAULT));
							if("".equals(updateCancleOrderStatus)){
								result.setSuccess(false);
								result.setMessage("订单取消失败,该订单已经发运!订单编号:"+id);
								response.getWriter().write(JSONArray.toJSON(result).toString());					
							}else{
								logger.info("==============订单取消失败==========订单号"+id);
								result.setSuccess(false);
								result.setMessage("订单取消失败,"+updateCancleOrderStatus+"!订单编号:"+id);
								response.getWriter().write(JSONArray.toJSON(result).toString());			
							}
				}else{
					logger.info(errorMsg+"订单编号:"+id);
					result.setSuccess(false);
					result.setMessage(errorMsg+"订单编号:"+id);
					response.getWriter().write(JSONArray.toJSON(result).toString());
				}
			}else{
				if(StringUtils.isBlank(id)){
					logger.info("订单编号不能为空 ");
					result.setSuccess(false);
					result.setMessage("订单编号不能为空");
					response.getWriter().write(JSONArray.toJSON(result).toString());
				}else{
					String createBy = getCurrentUser().getUsername();
					String errorMsg = customerOrderService.addOrderCancelStatusRecord(Long.parseLong(id), "B2C",createBy);
					if(errorMsg != null && errorMsg.equals("")){
	//					String retJson = orderSCancelService.handleOrderSCancel(id,createBy,"12312","B2C");
	//					if(retJson == null){
							logger.info("订单取消申请成功,请耐心等待程序流转!订单编号:"+id);
							result.setSuccess(true);
							result.setMessage("订单取消申请成功,请耐心等待程序流转!订单编号:"+id);
							response.getWriter().write(JSONArray.toJSON(result).toString());
	//					}else{
	//						response.getWriter().write(retJson);	
	//					}
					}else{
						logger.info(errorMsg+"订单编号:"+id);
						result.setSuccess(false);
						result.setMessage(errorMsg+"订单编号:"+id);
						response.getWriter().write(JSONArray.toJSON(result).toString());
					}
				}
			}
		} catch (IOException e) {
			logger.error("pushOrderAgain is failed"+e.getMessage(),e);
		}		
	}*/

	/**
	 * @Author Zhutaoshen
	 * @Date 2017/1/15 0015 16:42
	 * 通过参数查询订单，返回pagebean
	 * @param page
	 * @param pName
	 * @param status
	 * @param startTime
	 * @param endTime
	 * @param orderId
	 * @param receiveName
	 * @param userName
	 * @param supplyType
	 * @param payType
	 * @param shipType
	 * @param statusList
	 * @param orderPlatform
	 * @param merchantid
	 * @param orderType
	 * @param orderSource
	 * @param pageSize
	 * @return
	 */
	private PageBean<OrderDTO> getCustomerOrderPageBeanByCondition(String companyQy,Integer supplierId,String supplierName,Integer page,String pName,Short status,String startTime,String endTime,Long orderId,String receiveName,String userName,String supplyType,Integer payType,Integer shipType,String statusList,Short orderPlatform, String merchantid,String orderType,String orderSource,int pageSize){
		try {
			if (pName!=null) {
				pName = URLDecoder.decode(pName,"UTF-8");
			}
			if (userName!=null) {
				userName = URLDecoder.decode(userName,"UTF-8");
			}
			if (receiveName!=null) {
				receiveName =URLDecoder.decode(receiveName,"UTF-8");
			}
			if (supplierName!=null) {
				supplierName = URLDecoder.decode(supplierName,"UTF-8");
			}
		} catch (Exception e) {
		}
		//11.宁波海外直邮  12.宁波保税区发货 13.郑州海外直邮 14.郑州保税区发货  1.国内发货 21.韩国直邮
		LOGGER.info("start to execute method <getCustomerOrderPageBeanByCondition>!!!!");
		LOGGER.info("订单编号orderId:"+orderId);
		LOGGER.info("当前是第"+page+"页!");
		LOGGER.info("订单状态码status:"+status);
		LOGGER.info("商品名称:"+pName);
		LOGGER.info("起始时间:"+startTime);
		LOGGER.info("结束时间:"+endTime);
		LOGGER.info("收货人receiveName:"+receiveName);
		LOGGER.info("用户名userName:"+userName);
		LOGGER.info("货源种类supplyType:"+supplyType);
		LOGGER.info("支付方式payType:"+payType);
		LOGGER.info("配送方式shipType:"+shipType);
		LOGGER.info("状态列表:"+statusList);
		LOGGER.info("平台类型:"+orderPlatform);
		LOGGER.info("POP商家ID:"+merchantid);
		LOGGER.info("订单类型orderType:"+orderType);
		PageBean<OrderDTO> pageBean = new PageBean<OrderDTO>();
		OrderDTO orderDTO = new OrderDTO();
		if(!Common.isEmpty(statusList)){
			List<Short> statusAll = new ArrayList<Short>();
			String[] statusArray = statusList.split(",");
			for(String statusSimple:statusArray){
				try {
					statusAll.add(Short.parseShort(statusSimple));
				} catch (NumberFormatException e) {
					LOGGER.error("getCustomerOrderPageBeanByCondition is failed!!!!数字转换异常  status:"+status,e);
				}
			}
			orderDTO.setStatusList(statusAll);
		}
		if (Common.isEmpty(startTime)&&Common.isEmpty(endTime)) {
			Date d = new Date(); 
			long longtime = d.getTime();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
			endTime=dateFormat.format(d);
			String iString="7776000000";
			Long long1 = Long.valueOf(iString);
			long startdate=longtime-long1;
			startTime=dateFormat.format(startdate);
		}
		if(!Common.isEmpty(pName)){
			orderDTO.setpName(pName);
		}
		if(!Common.isEmpty(startTime)){
			orderDTO.setStartDate(Common.stringToDate(startTime.replace("-", "/"), "yyyy/MM/dd HH:mm:ss"));
		}
		if(!Common.isEmpty(endTime)){
			orderDTO.setEndDate(Common.stringToDate(endTime.replace("-", "/"), "yyyy/MM/dd HH:mm:ss"));
		}
		if(!Common.isEmpty(receiveName)){
			orderDTO.setReceiveName(receiveName);
		}
		if(!Common.isEmpty(userName)){
			List<Long> userIds = new ArrayList<Long>();
			try {
				UserService userService = RemoteServiceSingleton.getInstance().getUserService();
				userIds = userService.findUserIdsByUserName(userName);
				if(userIds.size()<1){
					userIds.add(0l);
				}
			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.error("UserService<findUserIdsByUserName()根据用户用户名模糊查询ID的集合> is failed!!!!"+e.getMessage(),e);
			}
			orderDTO.setUserIds(userIds);
		}
		if(null != orderId){
			orderDTO.setId(orderId);
			//orderDTO.getSupplyType()  1.国内   11.保税区  12.
		}
		if(null != orderType){
			Short sOrderType = new Short(orderType);
			orderDTO.setOrderType(sOrderType);
		}
		if(null!= orderSource){
			orderDTO.setOrderSource(orderSource);
		}
		if(null != status){
			orderDTO.setStatus(status);
		}
		if(null != supplierId){
			orderDTO.setSupplierId(supplierId);
		}
		if(null != companyQy){
			if(companyQy.equals("8")){
				short a=39;
				orderDTO.setOrderType(a);
			}else if(companyQy.equals("2")){
				short a=38;
				orderDTO.setOrderType(a);
			}else {
				orderDTO.setCompanyQy(companyQy);
			}
		}
		if(!Common.isEmpty(supplierName)){
			orderDTO.setSupplierName(supplierName);
		}
		if(!Common.isEmpty(supplyType)){
			orderDTO.setSupplyType(supplyType);

			if ("51".equals(supplyType) && !Common.isEmpty(merchantid)) {
				orderDTO.setMerchantid(merchantid);
			}
		}
		if(null != payType){
			orderDTO.setPayWay(payType.toString());
		}
		if(null!= shipType){
			orderDTO.setFiveShipType(shipType);
		}
		if(null != orderPlatform){
			orderDTO.setOrderPlatform(orderPlatform);
		}
		if(page!=null&&page!=0){  //判断条件中是否带着分页码
			pageBean.setPage(page);
		}else {
			pageBean.setPage(1);//没有分页码的话就检索第一页
		}
		pageBean.setParameter(orderDTO);
		pageBean.setPageSize(pageSize);
		try {
			CustomerOrderService customerOrderService = RemoteServiceSingleton.getInstance().getCustomerOrderService();
			PageBean<OrderDTO> pageBean1 = new PageBean<OrderDTO>();
			pageBean1 = customerOrderService.findCustomerOrdersByWofe(pageBean);
			if(null != pageBean1){
				List<OrderDTO> orderList = pageBean1.getResult();
				if(null != orderList && orderList.size()>0){
					for (OrderDTO orderDTO2 : orderList) {
						if(orderDTO2.getSupplyType().equals("11") || orderDTO2.getSupplyType().equals("12")){
							String portType = orderDTO2.getPortType();
							if(!Common.isEmpty(portType)){
								String  portTypeStr = portType.substring(0, 1);
								if(portTypeStr.equals("2")){
									orderDTO2.setPortType("guofeiyan");
								}
							}
						}else if(orderDTO2.getSupplyType().equals("51")){
							orderDTO2.setMerchantName(RemoteServiceSingleton.getInstance().getSupplierStoreService().findSupplierStoreBySupplierId(Integer.parseInt(orderDTO2.getMerchantid())).getStoreName());
						}
						List<ProductDTO> productList = orderDTO2.getProductList();
						if(null != productList && productList.size()>0){
							for (ProductDTO productDTO : productList) {
								String productImgUrl = productDTO.getImgUrl();
								if(!productImgUrl.startsWith("http") || !productImgUrl.startsWith("Http")){
									productImgUrl = Constants.P1 + productImgUrl;
									productDTO.setImgUrl(productImgUrl);
								}
							}
						}
					}
				}
			}
			return pageBean1;
		} catch (Exception e) {
			LOGGER.error("CustomerOrderService<findCustomerOrdersByWofe()> is failed!!!!"+e.getMessage(),e);
			return null;
		}
	}
	
	/**.
	 * 根据条件搜索订单列表.
	 * @Title getCustomerOrderPageBeanByCondition
	 * @param  page
	 * @param   pName
	 * @param    status
	 * @param   startTime
	 * @param   endTime
	 * @param     orderId
	 * @param   receiveName
	 * @param   userName
	 * @param    model
	 * @return String
	 */
	@RequestMapping(value = "/getCustomerOrderPageBeanByCondition")
	public String getCustomerOrderPageBeanByCondition(String companyQy,Integer page,String pName,Short status,String startTime,String endTime,Long orderId,
			String receiveName,String userName,Model model,String supplyType,Integer payType,Integer shipType,String statusList,String pageType,Short orderPlatform, String merchantid,String orderType,Integer supplierId,String supplierName,
													  String orderSource){
		PageBean<OrderDTO> orderDTOPageBean = this.getCustomerOrderPageBeanByCondition(companyQy,supplierId,supplierName,page,pName,status,startTime,endTime,orderId,receiveName,userName,supplyType,payType,shipType,statusList,orderPlatform,merchantid,orderType,orderSource,Constants.PAGESIZE);
		if(statusList != null){
			model.addAttribute("statusList",statusList);
		}
		if(pageType != null){
			model.addAttribute("pageType",pageType);
		}
		model.addAttribute("pageBean", orderDTOPageBean);
		model.addAttribute("_orderTypeList", ThirdTypeEnum.getMap());
		LOGGER.info("end<> to execute method <getCustomerOrderPageBeanByCondition>!!!!");
		return "/dealerseller/B2Corder/B2Corder_list_model";	
	}
	
	/**.
	 * 根据条件搜索订单列表.
	 * @Title getCustomerOrderPageBeanByCondition
	 * @param  page
	 * @param   pName
	 * @param    status
	 * @param   startTime
	 * @param   endTime
	 * @param     orderId
	 * @param   receiveName
	 * @param   userName
	 * @param    model
	 * @return String
	 */
	@RequestMapping(value = "/getCustomerOrderPageBeanByOrderCancelCondition")
	public String getCustomerOrderPageBeanByOrderCancelCondition(Integer page,String pName,Short status,String startTime,String endTime,Long orderId,Integer supplierId,String supplierName,
			String receiveName,String userName,Model model,String supplyType,Integer payType,Integer shipType,String statusList,String pageType,Short orderPlatform){
		//11.宁波海外直邮  12.宁波保税区发货 13.郑州海外直邮 14.郑州保税区发货  1.国内发货 21.韩国直邮
		LOGGER.info("start to execute method <getCustomerOrderPageBeanByCondition>!!!!");
		LOGGER.info("订单编号orderId:"+orderId);
		LOGGER.info("当前是第"+page+"页!");
		LOGGER.info("订单状态码status:"+status);
		LOGGER.info("商品名称:"+pName);
		LOGGER.info("起始时间:"+startTime);
		LOGGER.info("结束时间:"+endTime);
		LOGGER.info("收货人receiveName:"+receiveName);
		LOGGER.info("用户名userName:"+userName);
		LOGGER.info("货源种类supplyType:"+supplyType);
		LOGGER.info("支付方式payType:"+payType);
		LOGGER.info("配送方式shipType:"+shipType);
		LOGGER.info("状态列表:"+statusList);
		LOGGER.info("平台类型:"+orderPlatform);
		
		PageBean<OrderDTO> pageBean = new PageBean<OrderDTO>();
		OrderDTO orderDTO = new OrderDTO();
//		if(!Common.isEmpty(statusList)){
		List<Short> statusAll = new ArrayList<Short>();
		if(status != null && !status.equals("")){
			if(status == Constants.FinalNumber.ONE){
				statusAll.add(Constants.FinalNumber.ORDER_PAID);
				statusAll.add(Constants.FinalNumber.ORDER_PACKAGE);
				statusAll.add(Constants.FinalNumber.ORDER_SHIPMENTS);
			}else{
				statusAll.add(status);
			}
		}
		if(!Common.isEmpty(statusList)){
			String[] statusArray = statusList.split(",");
			for(String statusSimple:statusArray){
				try {
					statusAll.add(Short.parseShort(statusSimple));
				} catch (NumberFormatException e) {
					LOGGER.error("getCustomerOrderPageBeanByCondition is failed!!!!数字转换异常  status:"+status,e);
				}
			}
		}
			orderDTO.setStatusList(statusAll);
//		}
		if(!Common.isEmpty(pName)){
			orderDTO.setpName(pName);
		}
		if(!Common.isEmpty(startTime)){
			orderDTO.setStartDate(Common.stringToDate(startTime.replace("-", "/"), "yyyy/MM/dd HH:mm:ss"));
		}
		if(!Common.isEmpty(endTime)){
			orderDTO.setEndDate(Common.stringToDate(endTime.replace("-", "/"), "yyyy/MM/dd HH:mm:ss"));
		}
		if(!Common.isEmpty(receiveName)){
			orderDTO.setReceiveName(receiveName);
		}
		if(!Common.isEmpty(userName)){
			List<Long> userIds = new ArrayList<Long>();
			try {
				UserService userService = RemoteServiceSingleton.getInstance().getUserService();
				userIds = userService.findUserIdsByUserName(userName);
				if(userIds.size()<1){
					userIds.add(0l);
				}
			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.error("UserService<findUserIdsByUserName()根据用户用户名模糊查询ID的集合> is failed!!!!"+e.getMessage(),e);
			}
			orderDTO.setUserIds(userIds);
		}
		if(null != orderId){
			orderDTO.setId(orderId);
			//orderDTO.getSupplyType()  1.国内   11.保税区  12.
		}
		if(null != status){
			orderDTO.setStatus(status);
		}
		if(!Common.isEmpty(supplyType)){
			orderDTO.setSupplyType(supplyType);
		}
		if(null != payType){
			orderDTO.setFivePayType(payType);
		}
		if(null!= shipType){
			orderDTO.setFiveShipType(shipType);
		}
		if(null != orderPlatform){
			orderDTO.setOrderPlatform(orderPlatform);
		}
		if(page!=null&&page!=0){  //判断条件中是否带着分页码
			pageBean.setPage(page);
		}else {
			pageBean.setPage(1);//没有分页码的话就检索第一页
		}
		pageBean.setParameter(orderDTO);
		pageBean.setPageSize(Constants.PAGESIZE);
		try {
			CustomerOrderService customerOrderService = RemoteServiceSingleton.getInstance().getCustomerOrderService();
			pageBean = customerOrderService.findCustomerOrdersAndOrderCancelStatusByWofeNew (pageBean);
			if(null != pageBean){
				List<OrderDTO> orderList = pageBean.getResult();
				if(null != orderList && orderList.size()>0){
					for (OrderDTO orderDTO2 : orderList) {
						if(orderDTO2.getSupplyType().equals("11") || orderDTO2.getSupplyType().equals("12")){
							String portType = orderDTO2.getPortType();
							if(!Common.isEmpty(portType)){
								String  portTypeStr = portType.substring(0, 1);
								if(portTypeStr.equals("2")){
									orderDTO2.setPortType("guofeiyan");
								}
							}
						}
						List<ProductDTO> productList = orderDTO2.getProductList();
						if(null != productList && productList.size()>0){
							for (ProductDTO productDTO : productList) {
								String productImgUrl = productDTO.getImgUrl();
								if(!productImgUrl.startsWith("http") || !productImgUrl.startsWith("Http")){
									productImgUrl = Constants.P1 + productImgUrl;
									productDTO.setImgUrl(productImgUrl);
								}
							}
						}
					}
				}	
			}
		} catch (Exception e) {
			LOGGER.error("CustomerOrderService<findCustomerOrdersByWofe()> is failed!!!!"+e.getMessage(),e);
		}
		
		if(statusList != null){
			model.addAttribute("statusList",statusList);
		}
		if(pageType != null){
			model.addAttribute("pageType",pageType);
		}
		
		model.addAttribute("pageBean", pageBean);
		
		LOGGER.info("end<> to execute method <getCustomerOrderPageBeanByCondition>!!!!");
		return "/dealerseller/B2Corder/B2Corder_list_model_order_cancel";	
	}
	
	
	
	
	
	
	
	/**.
	 * 根据订单的ID查询订单的详情.
	 * @Title getCustomerOrderDetailsById
	 * @param   orderId
	 * @param   model
	 * @return String
	 */
	@RequestMapping(value = "/getCustomerOrderDetailsById")
	public String getCustomerOrderDetailsById(Long orderId,Model model){
		LOGGER.info("start to execute method <getCustomerOrderDetailsById>!!!!");
		LOGGER.info("订单号orderId:"+orderId);
		OrderDTO orderDTO = new OrderDTO();
		List<Overseas> overseasList = new ArrayList<Overseas>();
		try {
			CustomerOrderService customerOrderService = RemoteServiceSingleton.getInstance().getCustomerOrderService();
			orderDTO = customerOrderService.findCustomerOrderInfoByOrderId(orderId);
			if(null != orderDTO){
				overseasList = customerOrderService.selectOverseasLogisticByOrderId(orderId);
				List<OrderItemDTO> orderItemDTOs = orderDTO.getOrderItemDTOs();
				if(null != orderItemDTOs && orderItemDTOs.size()>0){
					for (OrderItemDTO orderItemDTO : orderItemDTOs) {
						String skuImageUrl = orderItemDTO.getImgUrl();
						if(!Common.isEmpty(skuImageUrl)){
							if(!skuImageUrl.startsWith("http") || !skuImageUrl.startsWith("Http")){
								skuImageUrl=Constants.P1+skuImageUrl;
								orderItemDTO.setImgUrl(skuImageUrl);
							}
						}	
					}
				}
				if(orderDTO.getSupplyType().equals("1")){
					List<B2cShipOrderDTO> shipOrderDTOs = orderDTO.getShipOrderDTOs();
					if(null != shipOrderDTOs && shipOrderDTOs.size()>0){
						for (B2cShipOrderDTO b2cShipOrderDTO : shipOrderDTOs) {
							if(null != b2cShipOrderDTO){
								List<B2cShipOrderItemDTO> shipOrderItems = b2cShipOrderDTO.getShipItemDtoList();
								if(null != shipOrderItems && shipOrderItems.size()>0){
									for (B2cShipOrderItemDTO b2cShipOrderItemDTO : shipOrderItems) {
										String imageUrl = b2cShipOrderItemDTO.getProductUrl();
										if(!Common.isEmpty(imageUrl)){
											imageUrl = Constants.P1+imageUrl;
											b2cShipOrderItemDTO.setProductUrl(imageUrl);
										}
									}
								}
								/*Map<Long, List<ShipOrderItemDTO>> shipOrderItemMap = b2cShipOrderDTO.getShipOrderItemMap();
								if(null != shipOrderItemMap && shipOrderItemMap.size()>0){
									for(Map.Entry<Long, List<ShipOrderItemDTO>> shipOrderMap : shipOrderItemMap.entrySet()){
										if(null != shipOrderMap){
											if(null != shipOrderMap.getValue() && shipOrderMap.getValue().size()>0){
												for (ShipOrderItemDTO b2cShipOrderDTO2 : shipOrderMap.getValue()) {
													if(null != b2cShipOrderDTO2){
														String imageUrl = b2cShipOrderDTO2.getProductUrl();
														if(!Common.isEmpty(imageUrl)){
															imageUrl = Constants.P1+imageUrl;
															b2cShipOrderDTO2.setProductUrl(imageUrl);
															System.out.println(imageUrl);
														}
													}
												}
											}
										}
									}
								}*/	
							}	
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("CustomerOrderService<findCustomerOrdersByWofe()> is failed!!!!"+e.getMessage(),e);
		}
		
		if(null != orderDTO){
			if(null != orderDTO.getUserId()){
				try {
					LOGGER.info("此订单用户ID:"+orderDTO.getUserId());
					UserService userService = RemoteServiceSingleton.getInstance().getUserService();
					User user = userService.findUserById(orderDTO.getUserId());	
					model.addAttribute("user", user);
				} catch (Exception e) {
					// TODO: handle exception
					LOGGER.error("UserService<findUserById()根据用户ID查询该用户的信息> is failed!!!!"+e.getMessage(),e);
				}	
			}
		}
		model.addAttribute("overseasList", overseasList);
		model.addAttribute("orderDTO", orderDTO);
		LOGGER.info("end<> to execute method <getCustomerOrderDetailsById>!!!!");
		return "/dealerseller/B2Corder/B2Corder_details";	
	}
	
	
	/**.
	 * WOFE修改过郑州海关的订单状态.
	 * @title updateOrderStatusByOrderId
	 * @DATE 2015年8月17日14:32:04
	 * @param  orderId
	 * @param  status
	 * @return String
	 */
	@RequestMapping(value = "/updateOrderStatusByOrderId")
	@ResponseBody
	public String updateOrderStatusByOrderId(Long orderId,Short status){
		LOGGER.info("start to execute <updateOrderStatusByOrderId()>!!!!");
		LOGGER.info("订单编号:"+orderId);
		LOGGER.info("订单状态:"+status);
		boolean flag = false;
		String result = "";
		try {
			CustomerOrderService customerOrderService = RemoteServiceSingleton.getInstance().getCustomerOrderService();
			flag = customerOrderService.updateOrderStatus(orderId, getCurrentPlatformId(), getCurrentUser().getUsername(), status);
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("customerOrderService.updateOrderStatus()根据订单编号修改订单状态 is failed!!!!"+e.getMessage(),e);
		}
		if(flag){
			result = "此订单操作成功!";
		}else{
			result = "此订单操作失败!";
		}
		LOGGER.info("end<> to execute <updateOrderStatusByOrderId()>!!!!");
		return result;
	}
	
	
	/**.
	 * WOFE补录过郑州海关的订单的物流<补录C订单物流>.
	 * @title updateOrderLogisticsById
	 * @DATE  2015年8月17日14:36:25
	 * @param  orderId
	 * @param  logisticsCarriersName
	 * @param  logisticsNumber
	 * @param  logisticsCarriersId
	 * @return String
	 */
	@RequestMapping(value = "/updateOrderLogisticsById")
	@ResponseBody
	public String updateOrderLogisticsById(Long orderId,String logisticsCarriersName,String logisticsNumber,Long logisticsCarriersId){
		LOGGER.info("start to execute <updateOrderLogisticsById()通过订单号修改物流信息>!!!!");
		LOGGER.info("订单编号:"+orderId);
		LOGGER.info("物流商名称:"+logisticsCarriersName);
		LOGGER.info("物流商编号:"+logisticsCarriersId);
		LOGGER.info("物流商编码:"+logisticsNumber);
		Order order = new Order();
		if(null != orderId){
			order.setId(orderId);
		}
		if(!Common.isEmpty(logisticsCarriersName)){
			order.setLogisticsCarriersName(logisticsCarriersName);
		}
		if(!Common.isEmpty(logisticsNumber)){
			order.setLogisticsNumber(logisticsNumber);	
		}
		if(null != logisticsCarriersId){
			order.setLogisticsCarriersId(logisticsCarriersId);
		}
		boolean flag = false;
		String result = "";
		try {
			CustomerOrderService customerOrderService = RemoteServiceSingleton.getInstance().getCustomerOrderService();
			flag = customerOrderService.updateOrderLogisticsById(order);
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("customerOrderService.updateOrderLogisticsById()通过订单号修改物流信息 is failed!!!!"+e.getMessage(),e);
		}
		if(flag){
			result = "1";
		}else{
			result = "订单物流信息补录失败!";
		}
		LOGGER.info("end<> to execute <updateOrderLogisticsById()通过订单号修改物流信息>!!!!");
		return result;
	}
	/**.
	 * WOFE补录海外直邮的订单的物流<补录海外直邮订单物流>.
	 * @title updateOverseasOrderLogisticsById
	 * @DATE  2016年7月19日17:14:28
	 * @param  orderId
	 * @param  logisticsCarriersName
	 * @param  logisticsNumber
	 * @param  logisticsCarriersId
	 * @return String
	 */
	@RequestMapping(value = "/updateOverseasOrderLogisticsById")
	@ResponseBody
	public String updateOverseasOrderLogisticsById(String orderId,String logisticsCarriersNames,String logisticsNumbers,String logisticsCarriersIds){
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
			CustomerOrderService customerOrderService = RemoteServiceSingleton.getInstance().getCustomerOrderService();
			// 获取订单信息
			OrderDTO orderDTO =customerOrderService.findCustomerOrderInfoByOrderId(Long.valueOf(orderId));
			// 校验物流单号是否已经存在 
			for (int i = 0; i < logisticsCarriersIds1.length; i++) {
				boolean isExist = customerOrderService.exitsOverseasLogistics(logisticsNumbers1[i], Long.valueOf(logisticsCarriersIds1[i]));
				if (isExist) {
					return "订单物流信息录入失败，请检查物流信息是否已录入。";
				}
			}
			for (int i = 0; i < logisticsCarriersIds1.length; i++) {
			
				// 校验物流单号是否已经存在 
				boolean isExist = customerOrderService.exitsOverseasLogistics(logisticsNumbers1[i], Long.valueOf(logisticsCarriersIds1[i]));
				if (isExist) {
					return "订单物流信息录入失败，物流单号已经存在。";
				}
				// 保存物流信息到海外直邮物流表
				customerOrderService.modifylogisticsInfo(Long.valueOf(orderId),logisticsNumbers1[i], Long.valueOf(logisticsCarriersIds1[i]), logisticsCarriersNames1[i]);
			
				
				List<OrderItemDTO> orderItemDTOs = orderDTO.getOrderItemDTOs();

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
				dto.setUserId(orderDTO.getUserId());
				dto.setOrderType(2);//b2c
				dto.setOrderId(orderDTO.getId());
				dto.setStatus((short)2);
				int qty = 0 ;
				BigDecimal pirce = new BigDecimal(0);
				List<ShipOrderItemDTO> itemList = new ArrayList<ShipOrderItemDTO>();
				for(OrderItemDTO tmp : orderItemDTOs){
					ShipOrderItemDTO item = new ShipOrderItemDTO();
					item.setPid(tmp.getPid());
					item.setSkuId(tmp.getSkuId());
					item.setpName(tmp.getpName());
					item.setSkuName(tmp.getSkuNameCn());
					item.setQty(tmp.getSkuQty());
					item.setPirce(tmp.getPrice());
					itemList.add(item);
					qty += tmp.getSkuQty();
					pirce = pirce.add(tmp.getPrice().multiply(new BigDecimal(item.getQty())));
				}
				dto.setPrice(pirce);
				dto.setQty(qty) ;
				dto.setShipItemList(itemList); 
				
				customerOrderService.insertShipOrderpop(dto);
			}
			//修改订单状态
			if(orderDTO.getStatus()==(short)41){
				boo = customerOrderService.updateOverseasOrderStatus(Long.valueOf(orderId), 1111L, "sys", (short)81);
			}else{
				boo=true;
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
	/**.
	 * @Title：getStockWofeType
	 * @Description:补录物流时异步加载所有物流商.
	 * @param response
	 */
	@RequestMapping(value = "/getLogistic")
	public void getLogistic(HttpServletResponse response){
		LOGGER.info("start to execute method <getLogistic()获取所有物流商>!!!!");
		Object json = "[]";
		try {
			LogisticTempService logisticTempService = RemoteServiceSingleton.getInstance().getlogisticTempService();
			List<LogisticTemp>  logisticTemps = logisticTempService.getProviders();
			json = JSONArray.toJSON(logisticTemps);
			response.getWriter().write(json.toString());
		} catch (IOException e) {
			LOGGER.error("LogisticTempService<getProviders()<获取所有物流商服务>> is failed!!!!"+e.getMessage(),e);
		}
		LOGGER.info("物流商："+json.toString());
		LOGGER.info("end<> to execute method <getLogistic()获取所有物流商>!!!!");
	}
	
	/**
	 * 获取所有POP商家
	 * @param response
	 */
	@RequestMapping("/getAllPopSupplier")
	public void getAllPopSupplier(HttpServletResponse response) {
		LOGGER.info("start to execute method <getAllPopSupplier()获取所有POP商家>!!!!");
		Object json = "[]";
		try {
			SupplierManagerService supplierManagerService = RemoteServiceSingleton.getInstance().getSupplierManagerService();
			List<Supplier>  list = supplierManagerService.findAllSupplier();

			// 只显示pop的商家
			List<Supplier> temp = new ArrayList<Supplier>();
			for (Supplier supplier:list){
				if(supplier.getSupplyType() == SupplierSouces.POP.getIndex()){
					temp.add(supplier);
				}
			}

			json = JSONArray.toJSON(temp);
			response.getWriter().write(json.toString());
		} catch (IOException e) {
			LOGGER.error("LogisticTempService<getProviders()<获取所有POP商家>> is failed!!!!"+e.getMessage(),e);
		}
		LOGGER.info("POP商家："+json.toString());
		LOGGER.info("end<> to execute method <getAllPopSupplier()获取所有POP商家>!!!!");
	}
	
	
	/**.
	 * wofe导出C订单.
	 * @Title exportCustomerOrderExcel
	 * @param   orderId
	 * @param
	 * @return void
	 */
	@RequestMapping(value = "/exportCustomerOrderExcel")
	public void exportCustomerOrderExcel(HttpServletResponse response,String pName,Short status,String startTime,String endTime,Long orderId,Integer supplierId,String supplierName,String companyQy,
			String receiveName,String userName,Integer shipType,Integer payType,String statusList,String supplyType,Short orderPlatform, String merchantid, String orderType,String orderSource){
		try {
			if (pName!=null) {
				pName = URLDecoder.decode(pName,"UTF-8");
			}
			if (userName!=null) {
				userName = URLDecoder.decode(userName,"UTF-8");
			}
			if (receiveName!=null) {
				receiveName =URLDecoder.decode(receiveName,"UTF-8");
			}
			if (supplierName!=null) {
				supplierName = URLDecoder.decode(supplierName,"UTF-8");
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		LOGGER.info("start to execute method <exportCustomerOrderExcel>!!!!");
		//11.宁波海外直邮  12.宁波保税区发货 13.郑州海外直邮 14.郑州保税区发货  1.国内发货 21.韩国直邮
		LOGGER.info("start to execute method <getCustomerOrderPageBeanByCondition>!!!!");
		LOGGER.info("订单编号orderId:"+orderId);
		LOGGER.info("订单状态码status:"+status);
		LOGGER.info("商品名称:"+pName);
		LOGGER.info("起始时间:"+startTime);
		LOGGER.info("结束时间:"+endTime);
		LOGGER.info("收货人receiveName:"+receiveName);
		LOGGER.info("用户名userName:"+userName);
		LOGGER.info("货源种类supplyType:"+supplyType);
		LOGGER.info("支付方式payType:"+payType);
		LOGGER.info("配送方式shipType:"+shipType);
		LOGGER.info("状态列表:"+statusList);
		LOGGER.info("平台类型:"+orderPlatform);
		LOGGER.info("POP商家ID:"+merchantid);
		LOGGER.info("订单类型orderType:"+orderType);
		PageBean<OrderDTO> pageBean = new PageBean<OrderDTO>();
		OrderDTO orderDTO = new OrderDTO();
		if(!Common.isEmpty(statusList)){
			List<Short> statusAll = new ArrayList<Short>();
			String[] statusArray = statusList.split(",");
			for(String statusSimple:statusArray){
				try {
					statusAll.add(Short.parseShort(statusSimple));
				} catch (NumberFormatException e) {
					LOGGER.error("getCustomerOrderPageBeanByCondition is failed!!!!数字转换异常  status:"+status,e);
				}
			}
			orderDTO.setStatusList(statusAll);
		}
		if (Common.isEmpty(startTime)&&Common.isEmpty(endTime)) {
			Date d = new Date(); 
			long longtime = d.getTime();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
			endTime=dateFormat.format(d);
			String iString="7776000000";
			Long long1 = Long.valueOf(iString);
			long startdate=longtime-long1;
			startTime=dateFormat.format(startdate);
		}
		if(!Common.isEmpty(pName)){
			orderDTO.setpName(pName);
		}
		if(!Common.isEmpty(startTime)){
			orderDTO.setStartDate(Common.stringToDate(startTime.replace("-", "/"), "yyyy/MM/dd HH:mm:ss"));
		}
		if(!Common.isEmpty(endTime)){
			orderDTO.setEndDate(Common.stringToDate(endTime.replace("-", "/"), "yyyy/MM/dd HH:mm:ss"));
		}
		if(!Common.isEmpty(receiveName)){
			orderDTO.setReceiveName(receiveName);
		}
		if(!Common.isEmpty(userName)){
			List<Long> userIds = new ArrayList<Long>();
			try {
				UserService userService = RemoteServiceSingleton.getInstance().getUserService();
				userIds = userService.findUserIdsByUserName(userName);
				if(userIds.size()<1){
					userIds.add(0l);
				}
			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.error("UserService<findUserIdsByUserName()根据用户用户名模糊查询ID的集合> is failed!!!!"+e.getMessage(),e);
			}
			orderDTO.setUserIds(userIds);
		}
		if(null != orderId){
			orderDTO.setId(orderId);
			//orderDTO.getSupplyType()  1.国内   11.保税区  12.
		}
		if(null != orderType){
			Short sOrderType = new Short(orderType);
			orderDTO.setOrderType(sOrderType);
		}
		if(null!= orderSource){
			orderDTO.setOrderSource(orderSource);
		}
		if(null != status){
			orderDTO.setStatus(status);
		}
		if(null != companyQy){
			if(companyQy.equals("8")){
				short a=39;
				orderDTO.setOrderType(a);
			}else if(companyQy.equals("2")){
				short a=38;
				orderDTO.setOrderType(a);
			}else {
				orderDTO.setCompanyQy(companyQy);
			}
		}
		if(!Common.isEmpty(supplyType)){
			orderDTO.setSupplyType(supplyType);

			if ("51".equals(supplyType) && !Common.isEmpty(merchantid)) {
				orderDTO.setMerchantid(merchantid);
			}
		}
		if(null != payType){
			orderDTO.setPayWay(payType.toString());
		}
		if(null!= shipType){
			orderDTO.setFiveShipType(shipType);
		}
		if(null != orderPlatform){
			orderDTO.setOrderPlatform(orderPlatform);
		}
		if(null != supplierId){
			orderDTO.setSupplierId(supplierId);
		}
		if(!Common.isEmpty(supplierName)){
			orderDTO.setSupplierName(supplierName);
		}
		pageBean.setParameter(orderDTO);
		pageBean.setPageSize(Constants.MAXPAGESIZE);

		//PageBean<OrderDTO> orderDTOPageBean = this.getCustomerOrderPageBeanByCondition(1,pName,status,startTime,endTime,orderId,receiveName,userName,supplyType,payType,shipType,statusList,orderPlatform,merchantid,orderType,orderSource,999);
		HSSFWorkbook book = new HSSFWorkbook();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		HSSFSheet sheet = book.createSheet("customerOrderList");
		
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
	   
		sheet.setColumnWidth(0, 5000);    
	    sheet.setColumnWidth(1, 4000);    
	    sheet.setColumnWidth(2, 4000);    
	    sheet.setColumnWidth(3, 4000);    
	    sheet.setColumnWidth(4, 5000);    
	    sheet.setColumnWidth(5, 4000);
	    sheet.setColumnWidth(6, 4000); 
	    sheet.setColumnWidth(7, 4000); 
	    sheet.setColumnWidth(8, 4000); 
	    sheet.setColumnWidth(9, 9000);    
	    sheet.setColumnWidth(10, 5000); 
	    sheet.setColumnWidth(11, 5000); 
	    sheet.setColumnWidth(12, 7000); 
	    sheet.setColumnWidth(13, 7000);
	    sheet.setColumnWidth(14, 3000); 
	    sheet.setColumnWidth(15, 3000); 
	    sheet.setColumnWidth(16, 3000);
	    sheet.setColumnWidth(17, 3000);
	    sheet.setColumnWidth(18, 6000);
	    sheet.setColumnWidth(19, 3000);
	    sheet.setColumnWidth(20, 6000);
	    sheet.setColumnWidth(21, 6000);
	    sheet.setColumnWidth(22, 6000);
		sheet.setColumnWidth(23, 6000);
		sheet.setColumnWidth(24, 6000);
		sheet.setColumnWidth(25, 6000);
		sheet.setColumnWidth(26, 6000);
		sheet.setColumnWidth(27, 6000);
		sheet.setColumnWidth(28, 6000);
		sheet.setColumnWidth(29, 6000);
		sheet.setColumnWidth(30, 6000);
		sheet.setColumnWidth(31, 6000);
		sheet.setColumnWidth(32, 6000);
		sheet.setColumnWidth(33, 6000);
		sheet.setColumnWidth(34, 6000);
		sheet.setColumnWidth(35, 6000);
	    HSSFRow row = sheet.createRow((int)0);
		HSSFCell cell=null;
		String [] strtitle={"订单号","买家","买家ID","货源种类","收货人","联系电话","省","市","区","收货地址","商品名称","商品ID","国际条形码","规格","单价(元)",
				"数量","小计(元)","应付金额","实付金额","邮费","支付现金","支付红旗卷","交易状态","支付方式","平台来源","下单时间","付款时间","审核失败时间", "商家名称","分享人","承运商","快递单号","商品货号",
				"一级品类","二级品类","三级品类","四级品类","商品成本价"};
		for(int i=0;i<strtitle.length;i++){
			cell=row.createCell(i);
			cell.setCellValue(strtitle[i]);
			cell.setCellStyle(columnHeadStyle);
		}
		try {
			CustomerOrderExportSevice customerOrderExportSevice = RemoteServiceSingleton.getInstance().getCustomerOrderExportService();
			//返回数据总数
			int resultCount = customerOrderExportSevice.getCustomerOrdersCount(orderDTO);
			int pageSize = 999;
			int totalPages = 0;
			if(resultCount>pageSize){
				pageBean.setPageSize(pageSize);
				totalPages = resultCount%pageSize==0?resultCount/pageSize:resultCount/pageSize+1;
			}else{
				totalPages = 1;
			}
			List<OrderDTO> orderDTOs = null;
			for (int i = 0; i < totalPages; i++) {
				pageBean.setPage(i+1);
				pageBean = customerOrderExportSevice.exportCustomerOrders(pageBean);
				if(i>0){
					orderDTOs.addAll(pageBean.getResult());
				}else
					orderDTOs = pageBean.getResult();
			}
			/*List<OrderDTO> orderDTOs = null;
			pageBean = customerOrderExportSevice.exportCustomerOrders(pageBean);
			orderDTOs = pageBean.getResult();*/
			UserService userService = RemoteServiceSingleton.getInstance().getUserService();
			DealerProductSkuService dealerProductSkuService = RemoteServiceSingleton.getInstance().getDealerProductSkuService();
			//LOGGER.info("获取list.size()(1:合并2:rpc)------------"+orderDTOs.size()+"   =========     "+resultCount);
			HSSFCellStyle cellStyle = book.createCellStyle();
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 水平方向的对齐方式
			if(null != orderDTOs && orderDTOs.size()>0){
				
				Map<String, String> supplierMap = new HashMap<String, String>();
//				SupplierStoreService supplierStoreService = RemoteServiceSingleton.getInstance().getSupplierStoreService();
				SupplierManagerService supplierManagerService=RemoteServiceSingleton.getInstance().getSupplierManagerService();
				int r = 1;
				for (OrderDTO orderDTO2 : orderDTOs) {
					String supplyType_temp = "";
					switch (Integer.parseInt(orderDTO2.getSupplyType())) {
						case 1:
							supplyType_temp = "国内发货";
							break;
						case 11:
							supplyType_temp = "海外直邮";
							break;
						case 12:
							supplyType_temp = "保税区发货";
							break;
						case 21:
							supplyType_temp = "韩国直邮";
							break;
						case 50:
							supplyType_temp = "海外预售";
							break;
						case 51:
							supplyType_temp = "商家发货";
							break;
					}
					String statu = "";
					boolean fail = false;
					switch (Integer.parseInt(orderDTO2.getStatus()+"")) {
					case 1:
						statu = "待支付";
						break;
					case 21:
						statu = "已支付";
						break;
					case 31:
						statu = "待海关审核";
						break;
					case 41:
						statu = "待发货";
						break;
					case 67:
						statu = "海关审核订单失败(待退款)";
						fail = true;
						break;
					case 68:
						statu = "海关支付单审核失败";
						fail = true;
						break;
					case 69:
						statu = "海关物流单审核失败";
						fail = true;
						break;
					case 70:
						statu = "海关审核失败";
						fail = true;
						break;
					case 79:
						statu = "已退款";
						break;
					case 81:
						statu = "待收货";
						break;
					case 91:
						statu = "已收货";
						break;
					case 99:
						statu = "用户取消";
						break;
					case 100:
						statu = "自动取消";
						break;
					case 33:
							statu = "取消中";
							break;
					case 98:
							statu = "已退款";
							break;
					}
					
					String shipType_ = "";
					switch (orderDTO2.getShipType()) {
					case 0:	
						shipType_ = "普通";
						break;
					case 1:
						shipType_ = "自提";
						break;
					}
					
					String payType_ = "";
					if (orderDTO2.getPayWay()!=null) {
						
					switch (orderDTO2.getPayWay().charAt(0)) {
						case '0':
							payType_ = "线上";
							break;
						case '1':
							payType_ = "货到付款";
							break;
						case '2':
							payType_ = "微信";
							break;
						case '3':
							payType_ = "红旗劵";
							break;
						case '4':
							payType_ = "京东快捷支付";
							break;
						case '5':
							payType_ = "京东网银支付";
							break;
						case '6':
							payType_ = "支付宝";
							break;
					}
					}
					String orderPlatform_ = "";
					if(orderDTO2.getOrderPlatform()!=null) {
						switch (orderDTO2.getOrderPlatform()) {
							case 1:
								orderPlatform_ = "PC";
								break;
							case 2:
								orderPlatform_ = "ANDROID";
								break;
							case 3:
								orderPlatform_ = "WAP";
								break;
							case 4:
								orderPlatform_ = "IOS";
								break;
							case 5:
								orderPlatform_ = "其他";
								break;
							case 6:
								orderPlatform_ = "5S";
								break;
						}
					}

					String _orderSource = "";
					if(orderDTO2.getOrderSource()!=null) {
						OrderSourceEnum _e = OrderSourceEnum.getOne(orderDTO2.getOrderSource());
						if(_e!=null){
							_orderSource = _e.getStr();
						}
					}
					User user = userService.findUserById(orderDTO2.getUserId());
					String idCard="";
					if(user!=null&&user.getIdCard()!=null){
						idCard=user.getIdCard();
					}
					List<OrderItemDTO> orderItemDTOs = orderDTO2.getOrderItemDTOs();
					if(null != orderItemDTOs && orderItemDTOs.size()>0){
						for (OrderItemDTO orderItemDTO : orderItemDTOs) {
							row = sheet.createRow(r++);
							row.createCell(Constants.SHORT0).setCellValue(orderDTO2.getId()+"");//订单号
							row.createCell(Constants.SHORT1).setCellValue(orderDTO2.getUserName());//买家名称
							row.createCell(Constants.SHORT2).setCellValue(orderDTO2.getUserId()+"");//买家Id
							row.createCell(Constants.SHORT3).setCellValue(supplyType_temp);//货源种类
							row.createCell(Constants.SHORT4).setCellValue(orderDTO2.getReceiveName());//收货人
							row.createCell(Constants.SHORT5).setCellValue(orderDTO2.getReceiveMobilePhone()+"");//收货人手机号
							row.createCell(Constants.SHORT6).setCellValue(orderDTO2.getProvinceName());
							row.createCell(Constants.SHORT7).setCellValue(orderDTO2.getCityName());
							row.createCell(Constants.SHORT8).setCellValue(orderDTO2.getAreaName());
							row.createCell(Constants.SHORT9).setCellValue(orderDTO2.getReceiveAddress());
							row.createCell(Constants.SHORT10).setCellValue(orderItemDTO.getpName());
							row.createCell(Constants.SHORT11).setCellValue(orderItemDTO.getPid()+"");
							row.createCell(Constants.SHORT12).setCellValue(orderItemDTO.getSkuCode());
							row.createCell(Constants.SHORT13).setCellValue(orderItemDTO.getSkuNameCn());
							row.createCell(Constants.SHORT14).setCellValue(orderItemDTO.getPrice()+"");
							row.createCell(Constants.SHORT15).setCellValue(orderItemDTO.getSkuQty());
							row.createCell(Constants.SHORT16).setCellValue(orderItemDTO.getPrice().multiply(new BigDecimal(orderItemDTO.getSkuQty()))+"");
							BigDecimal price = orderItemDTO.getPrice().multiply(new BigDecimal(orderItemDTO.getSkuQty()));
							//折扣金额
							/*if(orderItemDTO.getDiscountPrice()!=null){
								row.createCell(Constants.SHORT17).setCellValue(orderItemDTO.getDiscountPrice()+"");
								price=price.subtract(orderItemDTO.getDiscountPrice());
							}*/
							//应付金额
							row.createCell(Constants.SHORT17).setCellValue(price+"");
							row.createCell(Constants.SHORT22).setCellValue(statu);
							//row.createCell(Constants.SHORT23).setCellValue(shipType_);
							row.createCell(Constants.SHORT23).setCellValue(payType_);
							row.createCell(Constants.SHORT24).setCellValue(orderPlatform_);
							//row.createCell(Constants.SHORT26).setCellValue(_orderSource);
							row.createCell(Constants.SHORT25).setCellValue(simpleDateFormat.format(orderDTO2.getCreateTime()));
							if(orderDTO2.getPayTime() != null){
								row.createCell(Constants.SHORT26).setCellValue(simpleDateFormat.format(orderDTO2.getPayTime()));
							}
							else{
								row.createCell(Constants.SHORT26).setCellValue("");
							}
							if(fail){
								row.createCell(Constants.SHORT27).setCellValue(simpleDateFormat.format(orderDTO2.getLastEditTime()));
							}
							else{
								row.createCell(Constants.SHORT27).setCellValue("");
							}
							
							// pop才显示商家名称
							String merchantId = orderDTO2.getMerchantid();
							if ("51".equals(orderDTO2.getSupplyType()) && null != merchantId) {
								String supplyName = "";
								if (supplierMap.containsKey(merchantId)) {
									supplyName = supplierMap.get(merchantId);
								}
								else {
//									SupplierStore supplierStore = supplierStoreService.findSupplierStoreBySupplierId(Integer.valueOf(merchantId));
//									if (null != supplierStore) {
//										supplyName = supplierStore.getStoreName();
//									}
//									supplierMap.put(merchantId, supplyName);
									List<Long>  supplierIds=new ArrayList<Long>();
									Long supplierId1 = Long.valueOf(merchantId);
									supplierIds.add(supplierId1);
									Map<Long, String> supplierNameMap = supplierManagerService.getIdNameMapBySupplierIds(supplierIds);
									if (null != supplierNameMap) {
										supplyName = supplierNameMap.get(supplierId1);
									}
									supplierMap.put(merchantId, supplyName);
								}
								row.createCell(Constants.SHORT28).setCellValue(supplyName);
							}
							// 海外直邮的显示商家名称
							Long supplierId2 = orderItemDTO.getSupplierId();
							if ("11".equals(orderDTO2.getSupplyType())&&supplierId2!=null) {
								String supplyName = "";
								if (supplierMap.containsKey(supplierId2)) {
									supplyName = supplierMap.get(supplierId2);
								}
								else {
									List<Long>  supplierIds=new ArrayList<Long>();
									supplierIds.add(supplierId2);
									Map<Long, String> supplierNameMap = supplierManagerService.getIdNameMapBySupplierIds(supplierIds);
									if (null != supplierNameMap) {
										supplyName = supplierNameMap.get(supplierId2);
									}
									supplierMap.put(merchantId, supplyName);
								}
								row.createCell(Constants.SHORT28).setCellValue(supplyName);
							}
							
							//分享者手机号
							if(StringUtils.isNotBlank(orderDTO2.getShareUserPhone())){
								row.createCell(Constants.SHORT29).setCellValue(orderDTO2.getShareUserPhone());
							}
							//承运商
							if(StringUtils.isNotBlank(orderDTO2.getLogisticsCarriersName())){
								row.createCell(Constants.SHORT30).setCellValue(orderDTO2.getLogisticsCarriersName());
							}else{
								row.createCell(Constants.SHORT30).setCellValue("");
							}
							//物流单号
							if(StringUtils.isNotBlank(orderDTO2.getLogisticsNumber())){
								row.createCell(Constants.SHORT31).setCellValue(orderDTO2.getLogisticsNumber());
							}else{
								row.createCell(Constants.SHORT31).setCellValue("");
							}
							//商品货号
							if(StringUtils.isNotBlank(orderItemDTO.getProductCode())){
								row.createCell(Constants.SHORT32).setCellValue(orderItemDTO.getProductCode());
							}else{
								row.createCell(Constants.SHORT32).setCellValue("");
							}
							//一级品类
							if(StringUtils.isNotBlank(orderItemDTO.getFirstCatogery())){
								row.createCell(Constants.SHORT33).setCellValue(orderItemDTO.getFirstCatogery());
							}else{
								row.createCell(Constants.SHORT33).setCellValue("");
							}
							//二级品类
							if(StringUtils.isNotBlank(orderItemDTO.getSecondCatogery())){
								row.createCell(Constants.SHORT34).setCellValue(orderItemDTO.getSecondCatogery());
							}else{
								row.createCell(Constants.SHORT34).setCellValue("");
							}
							//三级品类
							if(StringUtils.isNotBlank(orderItemDTO.getThirdCatogery())){
								row.createCell(Constants.SHORT35).setCellValue(orderItemDTO.getThirdCatogery());
							}else{
								row.createCell(Constants.SHORT35).setCellValue("");
							}
							//四级品类
							if(StringUtils.isNotBlank(orderItemDTO.getForthCatogery())){
								row.createCell(Constants.SHORT36).setCellValue(orderItemDTO.getForthCatogery());
							}else{
								row.createCell(Constants.SHORT36).setCellValue("");
							}
							//row.createCell(Constants.SHORT35).setCellValue(idCard);
							if(orderItemDTO.getSkuId()!=null){
								Map<String, Object> map = new HashMap<String, Object>();
                            	map.put("skuId", orderItemDTO.getSkuId().toString());
                            	map.put("productId", orderItemDTO.getPid());
                            	List<PurchasePricePO> purchasePricelist = dealerProductSkuService.getPurchasePriceBySkuId(map);
                            	if(purchasePricelist!=null&&purchasePricelist.size()>0){
                            		PurchasePricePO purchasePricePO = purchasePricelist.get(0);
                            		row.createCell(Constants.SHORT37).setCellValue(purchasePricePO.getPurchasePrice()+"");//成本价
                            	}else{
                            		row.createCell(Constants.SHORT37).setCellValue("");//成本价
                            	}
							}else{
								row.createCell(Constants.SHORT37).setCellValue("");
							}
						}
					}
					//订单实付金额
					if(orderDTO2.getPaidPrice() != null){
						String paidPrice = orderDTO2.getPaidPrice()+"";
						row.createCell(Constants.SHORT18).setCellValue(paidPrice);
					}
					//订单邮费
					if(orderDTO2.getRealTransferprice() != null){
						String realRransferPrice = orderDTO2.getRealTransferprice()+"";
						row.createCell(Constants.SHORT19).setCellValue(realRransferPrice);
					}
					int intValue=0;
					if (orderDTO2.getPaidPrice()!=null) {
						intValue = orderDTO2.getPaidPrice().intValue();
					}
					int parseInt=-1;
					if (orderDTO2.getPayWay()!=null) {
						parseInt = Integer.parseInt(orderDTO2.getPayWay());
					}
						int intValue2 = orderDTO2.getCashPrice().intValue();
						int intValue3 = orderDTO2.getHqjPrice().intValue();
						int parseInt2=0;
						if (orderDTO2.getMixPayStatus()!=null) {
							parseInt2 = Integer.parseInt(orderDTO2.getMixPayStatus());
						}
					if (parseInt2==1&&intValue>0) {
						if (intValue2>0&&intValue3>0) {
							BigDecimal decimal = orderDTO2.getCashPrice();
							BigDecimal multiply = orderDTO2.getHqjPrice();
							row.createCell(Constants.SHORT20).setCellValue(decimal+"");
							row.createCell(Constants.SHORT21).setCellValue(multiply+"");
						}else {
							BigDecimal decimal = orderDTO2.getPaidPrice().multiply(orderDTO2.getCashRatio());
							BigDecimal multiply = orderDTO2.getPaidPrice().multiply(orderDTO2.getHqRatio());
							row.createCell(Constants.SHORT20).setCellValue(decimal+"");
							row.createCell(Constants.SHORT21).setCellValue(multiply+"");
						}
					}if (parseInt==3&&parseInt2!=1&&intValue>0&&intValue2==0) {
						row.createCell(Constants.SHORT21).setCellValue(orderDTO2.getPaidPrice()+"");
					}if (parseInt!=-1&&parseInt!=3&&parseInt2!=1&&intValue2==0&&intValue>0) {
						row.createCell(Constants.SHORT20).setCellValue(orderDTO2.getPaidPrice()+"");
					}
				
					//关税
					/*
					if(orderDTO2.getRealTransferprice() != null){
						String realTotalTax = orderDTO2.getRealTotalTax()+"";
						row.createCell(Constants.SHORT21).setCellValue(realTotalTax);
					}*/
					row.getCell(17).setCellStyle(cellStyle);
					row.getCell(17).setCellFormula("S"+r+"+T"+r);//应付金额
					//row.getCell(17).setCellFormula("Q"+r+"+U"+r+"+V"+r+"-R"+r+"");//应付金额
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.info("exportCustomerOrders is failed"+e.getMessage(),e);
		}
		try {
			String filename = "customer-order"+simpleDateFormat.format(new Date());
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
			LOGGER.info("数据下载异常.稍后再试。。。");
		}
		LOGGER.info("end<> to execute method <exportCustomerOrderExcel>!!!!");
	}
	
	
	/**.
	 * 根据条件搜索订单列表.
	 * @Title getCustomerOrderPageBeanByCondition
	 * @param  page
	 * @param   pName
	 * @param    status
	 * @param   startTime
	 * @param   endTime
	 * @param     orderId
	 * @param   receiveName
	 * @param   userName
	 * @param    model
	 * @return String
	 */
	@RequestMapping(value = "/getRechageErrorOrderPageBeanByCondition")
	public String getRechageErrorPageBeanByCondition(Integer page,String pName,Short status,String startTime,String endTime,Long orderId,
			String receiveName,String userName,Model model,String supplyType,Integer payType,Integer shipType,String statusList,String pageType,Short orderPlatform, String merchantid,String orderType,
													  String orderSource){
		//11.宁波海外直邮  12.宁波保税区发货 13.郑州海外直邮 14.郑州保税区发货  1.国内发货 21.韩国直邮
		LOGGER.info("start to execute method <getRechageErrorPageBeanByCondition>!!!!");
		LOGGER.info("订单编号orderId:"+orderId);
		LOGGER.info("当前是第"+page+"页!");
		LOGGER.info("订单状态码status:"+status);
		LOGGER.info("商品名称:"+pName);
		LOGGER.info("起始时间:"+startTime);
		LOGGER.info("结束时间:"+endTime);
		LOGGER.info("收货人receiveName:"+receiveName);
		LOGGER.info("用户名userName:"+userName);
		LOGGER.info("货源种类supplyType:"+supplyType);
		LOGGER.info("支付方式payType:"+payType);
		LOGGER.info("配送方式shipType:"+shipType);
		LOGGER.info("状态列表:"+statusList);
		LOGGER.info("平台类型:"+orderPlatform);
		LOGGER.info("POP商家ID:"+merchantid);
		LOGGER.info("订单类型orderType:"+orderType);
		
		PageBean<OrderDTO> pageBean = new PageBean<OrderDTO>();
		OrderDTO orderDTO = new OrderDTO();
		if(!Common.isEmpty(statusList)){
			List<Short> statusAll = new ArrayList<Short>();
			String[] statusArray = statusList.split(",");
			for(String statusSimple:statusArray){
				try {
					statusAll.add(Short.parseShort(statusSimple));
				} catch (NumberFormatException e) {
					LOGGER.error("getCustomerOrderPageBeanByCondition is failed!!!!数字转换异常  status:"+status,e);
				}
			}
			orderDTO.setStatusList(statusAll);
		}
		if(!Common.isEmpty(pName)){
			orderDTO.setpName(pName);
		}
		if(!Common.isEmpty(startTime)){
			orderDTO.setStartDate(Common.stringToDate(startTime.replace("-", "/"), "yyyy/MM/dd HH:mm:ss"));
		}
		if(!Common.isEmpty(endTime)){
			orderDTO.setEndDate(Common.stringToDate(endTime.replace("-", "/"), "yyyy/MM/dd HH:mm:ss"));
		}
		if(!Common.isEmpty(receiveName)){
			orderDTO.setReceiveName(receiveName);
		}
		if(!Common.isEmpty(userName)){
			List<Long> userIds = new ArrayList<Long>();
			try {
				UserService userService = RemoteServiceSingleton.getInstance().getUserService();
				userIds = userService.findUserIdsByUserName(userName);
				if(userIds.size()<1){
					userIds.add(0l);
				}
			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.error("UserService<findUserIdsByUserName()根据用户用户名模糊查询ID的集合> is failed!!!!"+e.getMessage(),e);
			}
			orderDTO.setUserIds(userIds);
		}
		if(null != orderId){
			orderDTO.setId(orderId);
			//orderDTO.getSupplyType()  1.国内   11.保税区  12.
		}
		if(null != orderType){
			Short sOrderType = new Short(orderType);
			orderDTO.setOrderType(sOrderType);
		}
		if(null!= orderSource){
			orderDTO.setOrderSource(orderSource);
		}
		if(null != status){
			orderDTO.setStatus(status);
		}
		if(!Common.isEmpty(supplyType)){
			orderDTO.setSupplyType(supplyType);
			
			if ("51".equals(supplyType) && !Common.isEmpty(merchantid)) {
				orderDTO.setMerchantid(merchantid);
			}
		}
		if(null != payType){
			orderDTO.setFivePayType(payType);
		}
		if(null!= shipType){
			orderDTO.setFiveShipType(shipType);
		}
		if(null != orderPlatform){
			orderDTO.setOrderPlatform(orderPlatform);
		}
		if(page!=null&&page!=0){  //判断条件中是否带着分页码
			pageBean.setPage(page);
		}else {
			pageBean.setPage(1);//没有分页码的话就检索第一页
		}
		pageBean.setParameter(orderDTO);
		pageBean.setPageSize(Constants.PAGESIZE);
		try {
			CustomerOrderService customerOrderService = RemoteServiceSingleton.getInstance().getCustomerOrderService();
			pageBean = customerOrderService.findCustomerOrdersForRechangList(pageBean);
			if(null != pageBean){
				List<OrderDTO> orderList = pageBean.getResult();
				if(null != orderList && orderList.size()>0){
					for (OrderDTO orderDTO2 : orderList) {
						if(orderDTO2.getSupplyType().equals("11") || orderDTO2.getSupplyType().equals("12")){
							String portType = orderDTO2.getPortType();
							if(!Common.isEmpty(portType)){
								String  portTypeStr = portType.substring(0, 1);
								if(portTypeStr.equals("2")){
									orderDTO2.setPortType("guofeiyan");
								}
							}
						}else if(orderDTO2.getSupplyType().equals("51")){
							orderDTO2.setMerchantName(RemoteServiceSingleton.getInstance().getSupplierStoreService().findSupplierStoreBySupplierId(Integer.parseInt(orderDTO2.getMerchantid())).getStoreName());
						}
						List<ProductDTO> productList = orderDTO2.getProductList();
						if(null != productList && productList.size()>0){
							for (ProductDTO productDTO : productList) {
								String productImgUrl = productDTO.getImgUrl();
								if(!productImgUrl.startsWith("http") || !productImgUrl.startsWith("Http")){
									productImgUrl = Constants.P1 + productImgUrl;
									productDTO.setImgUrl(productImgUrl);
								}
							}
						}
					}
				}	
			}
		} catch (Exception e) {
			LOGGER.error("CustomerOrderService<findCustomerOrdersByWofe()> is failed!!!!"+e.getMessage(),e);
		}
		
		if(statusList != null){
			model.addAttribute("statusList",statusList);
		}
		if(pageType != null){
			model.addAttribute("pageType",pageType);
		}
		
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("_orderTypeList", ThirdTypeEnum.getMap());
		LOGGER.info("end<> to execute method <getRechageErrorPageBeanByCondition>!!!!");
		return "/dealerseller/B2Corder/B2Crechage_error_order_list_model";	
	}
	
	/*@RequestMapping("/changeRechageErrorOrderDetailById")
	@ResponseBody
	public String changeRechageErrorOrderDetailById(HttpServletRequest request,HttpServletResponse response){
		
		String orderId = request.getParameter("orderId");
		String phone = request.getParameter("phone");
		if(StringUtils.isBlank(orderId)||StringUtils.isBlank(phone)){
			return "参数不完整";
		}
		RechargeReOrderDTO rechargeOrderDto = new RechargeReOrderDTO();	
		rechargeOrderDto.setOrderId(orderId);
		rechargeOrderDto.setMobile(phone);
		String result = "";
		try {
		    result = RemoteServiceSingleton.getInstance().getRechargeService().rechargeReOrder(rechargeOrderDto);
		     
		} catch (Exception e) {
			logger.error("调用充值重推接口失败",e);
			return "error";
		}
		return result;
	}*/
	
	/**.
	 * getCustomerOrderList
	 * @return String
	 */
	@RequestMapping(value = "/getSfz")
	public String getSfz(String orderId,String flag,Model model){
		LOGGER.info("查询订单号："+orderId);
		if(!"".equals(orderId) && orderId != null && orderId.length()>0){
			long orderIdL = Long.parseLong(orderId);
			Map<String,String> map = new HashMap<String,String>();
//			/long orderId = Long.parseLong(request.getParameter("orderId"));
			LOGGER.info("start to execute method <getSfz>!!!!");
			CustomerOrderService customerOrderService = RemoteServiceSingleton.getInstance().getCustomerOrderService();
			UserService userService = RemoteServiceSingleton.getInstance().getUserService();

			OrderDTO orderDTO = new OrderDTO();
			
			orderDTO = customerOrderService.findCustomerOrderInfoByOrderId(orderIdL);
			
			if(orderDTO != null){
				User user = userService.findUserById(orderDTO.getUserId());
				map.put("orderNo", orderDTO.getOrderNo()+"");
				map.put("receivename", orderDTO.getReceiveName());
				map.put("receivephone", orderDTO.getReceiveMobilePhone());
				map.put("createby", orderDTO.getCreateBy());
				if(user != null){
					map.put("username", user.getUserName());
					map.put("usermobile", user.getMobile());
					map.put("useridcard", user.getIdCard());
					map.put("userrealname", user.getRealName());
				}				
			}
			try {
			model.addAttribute("customerOrder",map);
			LOGGER.info("end<>  <getSfz>!!!!");
			} catch (Exception e) {
				LOGGER.error("customerOrder<getSfz()>获取身份证 service is error!!!!"+e.getMessage(),e);
			}
		}
		if("1".equals(flag)){
			return "/dealerseller/B2Corder/sfz_list_model";
		}else{
			return "/dealerseller/B2Corder/sfz_info";
		}
		
	}
	
	@RequestMapping(value = "/getOverseasOrderPageBeanByCondition")
	public String getOverseasOrderPageBeanByCondition(Integer page,String pName,Short status,String startTime,String endTime,Long orderId,
			String receiveName,String userName,Model model,String supplyType,Integer payType,Integer shipType,String statusList,String pageType,Short orderPlatform, String merchantid,String orderType,
													  String orderSource){
		//11.宁波海外直邮  12.宁波保税区发货 13.郑州海外直邮 14.郑州保税区发货  1.国内发货 21.韩国直邮
		LOGGER.info("start to execute method <getCustomerOrderPageBeanByCondition>!!!!");
		LOGGER.info("订单编号orderId:"+orderId);
		LOGGER.info("当前是第"+page+"页!");
		LOGGER.info("订单状态码status:"+status);
		LOGGER.info("商品名称:"+pName);
		LOGGER.info("起始时间:"+startTime);
		LOGGER.info("结束时间:"+endTime);
		LOGGER.info("收货人receiveName:"+receiveName);
		LOGGER.info("用户名userName:"+userName);
		LOGGER.info("货源种类supplyType:"+supplyType);
		LOGGER.info("支付方式payType:"+payType);
		LOGGER.info("配送方式shipType:"+shipType);
		LOGGER.info("状态列表:"+statusList);
		LOGGER.info("平台类型:"+orderPlatform);
		LOGGER.info("POP商家ID:"+merchantid);
		LOGGER.info("订单类型orderType:"+orderType);
		
		PageBean<OrderDTO> pageBean = new PageBean<OrderDTO>();
		OrderDTO orderDTO = new OrderDTO();
		if(!Common.isEmpty(statusList)){
			List<Short> statusAll = new ArrayList<Short>();
			String[] statusArray = statusList.split(",");
			for(String statusSimple:statusArray){
				try {
					statusAll.add(Short.parseShort(statusSimple));
				} catch (NumberFormatException e) {
					LOGGER.error("getCustomerOrderPageBeanByCondition is failed!!!!数字转换异常  status:"+status,e);
				}
			}
			orderDTO.setStatusList(statusAll);
		}
		if(!Common.isEmpty(pName)){
			orderDTO.setpName(pName);
		}
		if(!Common.isEmpty(startTime)){
			orderDTO.setStartDate(Common.stringToDate(startTime.replace("-", "/"), "yyyy/MM/dd HH:mm:ss"));
		}
		if(!Common.isEmpty(endTime)){
			orderDTO.setEndDate(Common.stringToDate(endTime.replace("-", "/"), "yyyy/MM/dd HH:mm:ss"));
		}
		if(!Common.isEmpty(receiveName)){
			orderDTO.setReceiveName(receiveName);
		}
		if(!Common.isEmpty(userName)){
			List<Long> userIds = new ArrayList<Long>();
			try {
				UserService userService = RemoteServiceSingleton.getInstance().getUserService();
				userIds = userService.findUserIdsByUserName(userName);
				if(userIds.size()<1){
					userIds.add(0l);
				}
			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.error("UserService<findUserIdsByUserName()根据用户用户名模糊查询ID的集合> is failed!!!!"+e.getMessage(),e);
			}
			orderDTO.setUserIds(userIds);
		}
		if(null != orderId){
			orderDTO.setId(orderId);
			//orderDTO.getSupplyType()  1.国内   11.保税区  12.
		}
		if(null != orderType){
			Short sOrderType = new Short(orderType);
			orderDTO.setOrderType(sOrderType);
		}
		if(null!= orderSource){
			orderDTO.setOrderSource(orderSource);
		}
		if(null != status){
			orderDTO.setStatus(status);
		}
		if(!Common.isEmpty(supplyType)){
			orderDTO.setSupplyType(supplyType);
			
			if ("51".equals(supplyType) && !Common.isEmpty(merchantid)) {
				orderDTO.setMerchantid(merchantid);
			}
		}
		if(null != payType){
			orderDTO.setFivePayType(payType);
		}
		if(null!= shipType){
			orderDTO.setFiveShipType(shipType);
		}
		if(null != orderPlatform){
			orderDTO.setOrderPlatform(orderPlatform);
		}
		if(page!=null&&page!=0){  //判断条件中是否带着分页码
			pageBean.setPage(page);
		}else {
			pageBean.setPage(1);//没有分页码的话就检索第一页
		}
		pageBean.setParameter(orderDTO);
		pageBean.setPageSize(Constants.PAGESIZE);
		try {
			CustomerOrderService customerOrderService = RemoteServiceSingleton.getInstance().getCustomerOrderService();
			pageBean = customerOrderService.findCustomerOrdersByWofe(pageBean);
			if(null != pageBean){
				List<OrderDTO> orderList = pageBean.getResult();
				if(null != orderList && orderList.size()>0){
					for (OrderDTO orderDTO2 : orderList) {
						if(orderDTO2.getSupplyType().equals("11") || orderDTO2.getSupplyType().equals("12")){
							String portType = orderDTO2.getPortType();
							if(!Common.isEmpty(portType)){
								String  portTypeStr = portType.substring(0, 1);
								if(portTypeStr.equals("2")){
									orderDTO2.setPortType("guofeiyan");
								}
							}
						}else if(orderDTO2.getSupplyType().equals("51")){
							orderDTO2.setMerchantName(RemoteServiceSingleton.getInstance().getSupplierStoreService().findSupplierStoreBySupplierId(Integer.parseInt(orderDTO2.getMerchantid())).getStoreName());
						}
						List<ProductDTO> productList = orderDTO2.getProductList();
						if(null != productList && productList.size()>0){
							for (ProductDTO productDTO : productList) {
								String productImgUrl = productDTO.getImgUrl();
								if(!productImgUrl.startsWith("http") || !productImgUrl.startsWith("Http")){
									productImgUrl = Constants.P1 + productImgUrl;
									productDTO.setImgUrl(productImgUrl);
								}
							}
						}
					}
				}	
			}
		} catch (Exception e) {
			LOGGER.error("CustomerOrderService<findCustomerOrdersByWofe()> is failed!!!!"+e.getMessage(),e);
		}
		
		if(statusList != null){
			model.addAttribute("statusList",statusList);
		}
		if(pageType != null){
			model.addAttribute("pageType",pageType);
		}
		
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("_orderTypeList", ThirdTypeEnum.getMap());
		LOGGER.info("end<> to execute method <getCustomerOrderPageBeanByCondition>!!!!");
		return "/dealerseller/B2Corder/overseasOrder_list_model";	
	}
}
