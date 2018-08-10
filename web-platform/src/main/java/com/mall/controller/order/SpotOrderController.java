/*package com.mall.controller.order;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rubyeye.xmemcached.MemcachedClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.authority.client.constant.ConfigConstant;
import com.mall.authority.client.util.UserUtil;
import com.mall.bean.authority.User;
import com.mall.dealer.order.dto.RetailerOrderDto;
import com.mall.dealer.order.dto.RetailerSendOrderDto;
import com.mall.dealer.order.po.RetailerSendOrder;
import com.mall.mybatis.utility.PageBean;
import com.mall.platform.model.PlatformUser;
import com.mall.retailer.model.Retailer;
import com.mall.retailer.model.RetailerUser;
import com.mall.retailer.order.dto.OrderItemDetail;
import com.mall.retailer.order.po.OrderItem;
import com.mall.stock.dto.StockDealerFindByOrderVO;
import com.mall.stock.dto.StockWofeByOrderIdVO;
import com.mall.stock.dto.StockWofeByskuDTO;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;
import com.mall.utils.CookieHelper;
import com.mall.utils.FormatBigDecimal;


*//**.
 * 订单Controller
 * @author zhouzb
 *
 *//*


@Controller
@RequestMapping(value="/orderxianhuo")
public class SpotOrderController extends BaseController{
	*//**
	 * LOGGER.
	 *//*
	private static final Log LOGGER = LogFactory.getLogger(SpotOrderController.class);
	*//**
	 * Sping注入mencached缓存客户端.
	 *//*
	@Autowired
	private  MemcachedClient memCachedClient;  

	*//**.
	 * 此方法用于日期的转换
	 * @param binder WebDataBinder
	 *//* 
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	*//**
	 * 跳转现货订单页面.
	 * @return String
	 *//*
	@RequestMapping(value="/getOrder",method=RequestMethod.GET)
	public final String getOrder(){
		
		LOGGER.info("跳转现货订单页面");
		
		LOGGER.info("商户Id:"+getCurrentPlatformId());
		
		LOGGER.info("用户:"+getCurrentUser().getUsername());
		LOGGER.info("用户ID:"+getCurrentUser().getId());
		
		return "/dealerseller/order/order_spotlist";
		
	}
	
	
	
	*//**
	 * 查询现货订单列表.
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param dto RetailerOrderDto
	 * @param statusToList String
	 * @param page Integer
	 * @return String
	 *//*
	@RequestMapping(value="/getOrder",method=RequestMethod.POST)
	public final String  getOrderListByAllCondition(
			HttpServletRequest request , HttpServletResponse response,
			RetailerOrderDto dto, Short statusToList,
			Integer page
			){
		LOGGER.info("查询现货订单列表.");
		try {
			PageBean<RetailerOrderDto> findAllRetailerOrder = new PageBean<RetailerOrderDto>();
			PageBean<RetailerOrderDto> requestPage = new PageBean<RetailerOrderDto>();
//			isFutures 1期货 0 现货
			List<Short> list = new ArrayList<Short>();
			
			if(statusToList!=null&&!statusToList.equals(Constants.SHORT0)){
				if(statusToList.equals(Constants.STATUS31)){
					dto.setStatus(Constants.STATUS31);
				}else if(statusToList.equals(Constants.SHORT2)){
					list.add(Constants.STATUS41);
					list.add(Constants.STATUS81);
					list.add(Constants.STATUS91);
					list.add(Constants.STATUS101);
					dto.setState(list);
				}else if(statusToList.equals(Constants.STATUS91)){
					list.add(Constants.STATUS91);
					list.add(Constants.STATUS101);
					dto.setState(list);
				}else{
					dto.setStatus(statusToList);
				}
			}
			
			if(page!=null&&page!=0){
				requestPage.setPage(page);
			}else{
				requestPage.setPage(Constants.NUM1);
			}
			requestPage.setPageSize(Constants.PAGESIZE);
			
			String retailerName = dto.getRetailerName();
			List<Long> retailerIdsByName = null;
			if(retailerName!=null){
				retailerIdsByName = RemoteServiceSingleton.getInstance()
						.getRetailerManagerService().getRetailerIdsByName(retailerName);
				if(retailerIdsByName.size()<1){
					retailerIdsByName.add(0l);
				}
			}
			dto.setRetailerIds(retailerIdsByName);
		
			//isFutures 1期货 0 现货
			dto.setIsFutures(Constants.NUM0);
			
			requestPage.setParameter(dto);
			
			findAllRetailerOrder =  RemoteServiceSingleton.getInstance()
					.getDealerOrderService().findRetailerOrder(requestPage);
			
			List<RetailerOrderDto> result = findAllRetailerOrder.getResult();
		
			if(result!=null){
			
				FormatBigDecimal formatBigDecimal = new FormatBigDecimal();
				
				for( int i = 0 ; i < result.size() ; i++ ){
				
					String imgUrl = result.get(i).getImgUrl();
				
					if(!imgUrl.startsWith("http")||!imgUrl.startsWith("Http")){
					
						imgUrl = Constants.P1+imgUrl;
					
						result.get(i).setImgUrl(imgUrl);
				
					}
				
					BigDecimal productPriceMin = result.get(i).getProductPriceMin();
					BigDecimal productPriceMax = result.get(i).getProductPriceMax();
					
					BigDecimal priceMax = formatBigDecimal.priceFormat(productPriceMax);
					BigDecimal priceMin = formatBigDecimal.priceFormat(productPriceMin);
					
					result.get(i).setProductPriceMin(priceMin);
					result.get(i).setProductPriceMax(priceMax);
					
					String msg = result.get(i).getMessage();
					if(msg!=null){
						result.get(i).setMessage(msg.replaceAll("\n", "<br/>"));
					}
				}
			}
			request.getSession().setAttribute("pb",findAllRetailerOrder);
			
			request.getRequestDispatcher
			("/WEB-INF/views/dealerseller/order/modelpage/order_spotmodel.jsp").forward(request, response);
		
		} catch (Exception e) {
			
			LOGGER.error("获取期货订单详情列表,错误信息:"+e.getMessage());

			LOGGER.error("商户Id:"+getCurrentPlatformId());
			LOGGER.error("用户:"+getCurrentUser().getUsername());
			LOGGER.error("用户ID:"+getCurrentUser().getId());
	    		
			LOGGER.error("获取期货订单详情列表错误,订单ID"+dto.getOrderId());
	    		
			LOGGER.error("获取期货订单详情列表,错误信息:"+e);
		}
		
		return null;
		
	}
	
	*//**
	 * 订单详情.
	 * @param model Model
	 * @param orderId Long
	 * @return  String
	 *//*
	@RequestMapping(value="/getOrderMinute")
	public final String getOrderMinute(Model model , Long orderId){
		
		LOGGER.info("订单详情.");
		
		OrderService orderService = new OrderService();
		
		Retailer findByRetailerId = new Retailer();
		
		RetailerUser retailerUser = new RetailerUser();
		
		String responseRealAddress = null;
		
		try{
			LOGGER.info("order line item   ： orderId ==" + orderId);
			OrderItemDetail findRetailerOrderItemByOrderId = RemoteServiceSingleton.getInstance()
					.getDealerOrderService().findRetailerOrderItemByOrderId(orderId);
			
			if(findRetailerOrderItemByOrderId==null){
				LOGGER.error("futures--order line item is empty ERROR ORDERID ：" + orderId);
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
				
				retailerUser = RemoteServiceSingleton.getInstance()
					.getRetailerUserManagerService().findAdminUserByMerchantId(retailerId);
				
				for( int i = 0 ; i < skuImgUrlList.size() ; i++ ){
					String imgUrl = skuImgUrlList.get(i);
					if(!imgUrl.startsWith("http")||!imgUrl.startsWith("Http")){
						imgUrl = Constants.P2+imgUrl;
						skuImgUrlList.set(i, imgUrl);
					}
				}
			}
			
			model.addAttribute("retailerUser",retailerUser);
			model.addAttribute("retailer",findByRetailerId);
			model.addAttribute("orderItem",findRetailerOrderItemByOrderId);
			model.addAttribute("responseRealAddress",responseRealAddress);
			
		}catch (Exception e) {
		
			LOGGER.error("获取期货订单详情错误 : orderId="+ orderId +"ERROR MSG" + e.getMessage());

			LOGGER.error("用户:"+getCurrentUser().getUsername());
			LOGGER.error("获取期货订单详情错误,订单ID"+orderId);
			LOGGER.error("获取期货订单详情错误,错误信息:"+e.getMessage(),e);
			
		}
		
		return "/dealerseller/order/order_spotdetail";
		
	}
	
	
	
	*//**
	 * 分单查询经销商列表.
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param orderId Long
	 * @param skuId Long 
	 * @return
	 *//*
	
	@RequestMapping(value="/biudOrderDealerList",method=RequestMethod.POST)
	@ResponseBody
	public final String biudOrderDealerList(HttpServletRequest request , 
			HttpServletResponse response,Long orderId,Long skuId){
		
		LOGGER.info("分单查询经销商列表");
		
		String str = "";
		StockWofeByskuDTO findAllDealerList = new StockWofeByskuDTO();
		
		OrderItemDetail orderItemDetail = RemoteServiceSingleton.getInstance()
				.getDealerOrderService().findRetailerOrderItemByOrderId(orderId);
		List<OrderItem> orderItemList = orderItemDetail.getOrderItemList();
		
		for (int i = 0; i < orderItemList.size(); i++) {
			OrderItem orderItem = orderItemList.get(i);
			Long skuId2 = orderItem.getSkuId();
			if(skuId.intValue()==skuId2.intValue()){
				short status = orderItem.getStatus();
				if(status>Constants.STATUS31){
					return "0";
				}
			}
		}
		try{
			findAllDealerList = RemoteServiceSingleton.getInstance()
					.getStockWofeService().findAllotDepByOrderid(orderId, skuId);
			request.getSession().setAttribute("dealerList",findAllDealerList);
			return "/dealerseller/order/modelpage/order_spotallot_model";
		}catch (Exception e) {
			
			LOGGER.error("分单查询经销商列表"+e.getMessage());
    		
			LOGGER.error("商户Id:"+getCurrentPlatformId());
			LOGGER.error("用户:"+getCurrentUser().getUsername());
			LOGGER.error("用户ID:"+getCurrentUser().getId());
	    		
			LOGGER.error("分单查询经销商列表,skuId:"+skuId);
			LOGGER.error("分单查询经销商列表,orderId:"+orderId);
	    		
			LOGGER.error("分单查询经销商列表,错误信息:"+e);
			
			str = "-1";
			return str;
		}
	}
	*//**.
	 *  
	 * @param request HttpServletRequest
	 * @param stockWofeByOrderIdVOarray String
	 * @param skuId Long
	 * @return String 保存状态
	 *//*
	@RequestMapping(value="/saveAllotOrder",method=RequestMethod.POST)
	@ResponseBody
	public final String saveAllotOrder(HttpServletRequest request,String stockWofeByOrderIdVOarray,Long skuId){
		
		LOGGER.info("保存分单信息");
		
		LOGGER.info("skuId="+skuId);
		
		LOGGER.info("商户Id:"+getCurrentPlatformId());
		
		LOGGER.info("用户:"+getCurrentUser().getUsername());
		LOGGER.info("用户ID:"+getCurrentUser().getId());
		
		
		String name = null;
		try{
			StockWofeByOrderIdVO stockWofeByOrderIdVO =null;
			List<StockWofeByOrderIdVO> resultStockWofeByOrderIdVOList = new ArrayList<StockWofeByOrderIdVO>();
			
			String [] stockWofeByOrderId = stockWofeByOrderIdVOarray.split("_");
			
			Cookie cookie = CookieHelper.getCookie(request,Constants.SESSION_ID);
			String sid = cookie.getValue();
			PlatformUser object =  (PlatformUser) memCachedClient.get(sid);
			
			name = object.getName();
			Long orderId = null;
			
			for(int i = 0 ; i < stockWofeByOrderId.length ; i++){
				stockWofeByOrderIdVO =  new StockWofeByOrderIdVO();
				String[] orderVO = stockWofeByOrderId[i].split(",");
					
				Long dealerId = Long.parseLong(orderVO[Constants.NUM0]);
				Integer newPresubQty = Integer.parseInt(orderVO[Constants.NUM1]);
				orderId = Long.parseLong(orderVO[Constants.NUM3]);
				skuId = Long.parseLong(orderVO[Constants.NUM4]);
				Long ownId = Long.parseLong(orderVO[Constants.NUM5]);
				Short ownerType = Short.parseShort(orderVO[Constants.NUM6]);
				
				if(newPresubQty!=0){
					
					stockWofeByOrderIdVO.setDealerId(dealerId);
					stockWofeByOrderIdVO.setPresubQty(newPresubQty);
					stockWofeByOrderIdVO.setSkuId(skuId);
					stockWofeByOrderIdVO.setOwnId(ownId);
					
					stockWofeByOrderIdVO.setOwnerType(ownerType);
					resultStockWofeByOrderIdVOList.add(stockWofeByOrderIdVO);
				}
			}
			OrderItemDetail orderItemDetail = RemoteServiceSingleton.getInstance()
					.getDealerOrderService().findRetailerOrderItemByOrderId(orderId);
			List<OrderItem> orderItemList = orderItemDetail.getOrderItemList();
			for (int i = 0; i < orderItemList.size(); i++) {
				OrderItem orderItem = orderItemList.get(i);
				Long skuId2 = orderItem.getSkuId();
				if(skuId.equals(skuId2)){
					short status = orderItem.getStatus();
					if(status>Constants.STATUS31){
						return "0";
					}
				}
			}
			
			RemoteServiceSingleton.getInstance()
			.getStockWofeService().saveAllotDepByPid(resultStockWofeByOrderIdVOList,orderId,skuId,name);
		
			return "1";
		}catch (Exception e) {
			
			LOGGER.error(
					"保存分单信息error mag: skuId="+ skuId  +"stockWofeByOrderIdVOarray"+ 
							stockWofeByOrderIdVOarray +"ERROR INFO："+ e.getMessage());
    		
			LOGGER.error("商户Id:"+getCurrentPlatformId());
			LOGGER.error("用户:"+getCurrentUser().getUsername());
			LOGGER.error("用户ID:"+getCurrentUser().getId());
	    		
			LOGGER.error("保存分单信息,错误信息:"+e);
			
			return e.getMessage();	
		}
		
	}
	*//**
	 * 查询已分配的经销商列表.
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param skuId  Long
	 * @param orderId Long
	 * @return Page
	 *//*
	@RequestMapping(value="/dealerList")
	@ResponseBody
	public String getDealerList(HttpServletRequest request,HttpServletResponse response,
			Long skuId ,Long orderId,Short status){ //现货分单后查询分配的经销商列表
		//查询分单记录从库存
		List<StockDealerFindByOrderVO> findStockDealeByOrder = new ArrayList<StockDealerFindByOrderVO>(); 
		//查询分单记录从订单
		List<RetailerSendOrderDto> sendOrderDto = new ArrayList<RetailerSendOrderDto>();
		
		RetailerSendOrder retailerSendOrder = new RetailerSendOrder();
		try {
			if(status!=null&&status==Constants.STATUS31){
				findStockDealeByOrder = RemoteServiceSingleton.getInstance()
						.getStockWofeService().FindStockDealeByOrder(orderId, skuId);
				request.getSession().setAttribute("dealerList",findStockDealeByOrder);
				request.getRequestDispatcher("/WEB-INF/views/dealerseller/order/modelpage/order_spotdealer_model.jsp")
					.forward(request, response);
			}else{
				retailerSendOrder.setOrderId(orderId);
				retailerSendOrder.setSkuId(skuId);
				sendOrderDto =  RemoteServiceSingleton.getInstance()
						.getDealerOrderService().selectSendOrders(retailerSendOrder);
				request.getSession().setAttribute("dealerList",sendOrderDto);
				request.getRequestDispatcher
				("/WEB-INF/views/dealerseller/order/modelpage/order_spotdealerfromdealer_model.jsp")
					.forward(request, response);
						
			}
		} catch (Exception e) {
			LOGGER.error("查询已分配的经销商列表 ERROR"+e.getMessage());
    		
			LOGGER.error("商户Id:"+getCurrentPlatformId());
			LOGGER.error("用户:"+getCurrentUser().getUsername());
			LOGGER.error("用户ID:"+getCurrentUser().getId());
	    		
			LOGGER.error("查询已分配的经销商列表,skuId:"+skuId);
			LOGGER.error("查询已分配的经销商列表,orderId:"+orderId);
	    		
			LOGGER.error("查询已分配的经销商列表,错误信息:"+e);
		}
		return null;
	}
	
}
*/