package com.mall.controller.order;
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
//import com.mall.dealer.order.dto.RetailerOrderDto;
import com.mall.mybatis.utility.PageBean;
import com.mall.platform.model.PlatformUser;
/*import com.mall.retailer.model.Retailer;
import com.mall.retailer.model.RetailerUser;*/
import com.mall.retailer.order.dto.OrderItemDetail;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;
import com.mall.utils.CookieHelper;


/**.
 * 订单Controller
 * @author zhouzb
 *
 */
@Controller
@RequestMapping(value="/orderqihuo")
public class FuturesOrderController extends BaseController{
	
	/**
	 * LOGGER.
	 */
	private static final Log LOGGER = LogFactory.getLogger(FuturesOrderController.class);

	/**
	 * Sping注入mencached缓存客户端.
	 */
	@Autowired
	private  MemcachedClient memCachedClient;

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
	
	/**.
	 * 跳转期货订单页面.
	 * @return String
	 */
	@RequestMapping(value="/getOrder",method=RequestMethod.GET)
	public String getOrder(){
		LOGGER.info(" 跳转期货订单页面.");
		
		LOGGER.info("商户Id:"+getCurrentPlatformId());
		
		LOGGER.info("用户:"+getCurrentUser().getUsername());
		LOGGER.info("用户ID:"+getCurrentUser().getId());
		
		return "/dealerseller/B2Border/order_futures_list";
	}

	/**.
	 * 获取订单信息列表.
	 * 
	 * @param request request
	 * @param response response
	 * @param dto RetailerOrderDto
	 * @param statusToList statusToList
	 * @param page page
	 * @return
	 */
	/*@RequestMapping(value="/getOrder",method=RequestMethod.POST)
	public String  getOrder(HttpServletRequest request,HttpServletResponse response,
			RetailerOrderDto dto, Short statusToList,Integer page){
		LOGGER.info("wofe select Order List");
		
		LOGGER.info("商户Id:"+getCurrentPlatformId());
		
		LOGGER.info("用户:"+getCurrentUser().getUsername());
		LOGGER.info("用户ID:"+getCurrentUser().getId());
		
		try {
			PageBean<RetailerOrderDto> findAllRetailerOrder = new PageBean<RetailerOrderDto>();
			PageBean<RetailerOrderDto> pageBean = new PageBean<RetailerOrderDto>();
			Short status = dto.getStatus();
			if(status!=null&&status.equals(Constants.STATUS91)){
				List<Short> list = new ArrayList<Short>();
				list.add(Constants.STATUS91);
				list.add(Constants.STATUS101);
				dto.setState(list);
				dto.setStatus(null);
			}
			if(status!=null&&status.equals(Constants.STATUS31)){
				List<Short> list = new ArrayList<Short>();
				list.add(Constants.STATUS31);
				list.add(Constants.STATUS32);
				list.add(Constants.STATUS33);
				list.add(Constants.STATUS34);
				dto.setState(list);
				dto.setStatus(null);
			}
//			isFutures 1期货 0 现货
			dto.setIsFutures(Constants.NUM1);
			dto.setQueryType(0);
			if(page!=null&&page!=0){
				pageBean.setPage(page);
			}else{
				pageBean.setPage(Constants.NUM1);
			}
			
			pageBean.setPageSize(Constants.PAGESIZE);
			String retailerName = dto.getRetailerName();
			List<Long> retailerIdsByName = null;
			if(retailerName!=null){
				retailerIdsByName = RemoteServiceSingleton.getInstance()
						.getRetailerManagerService().getRetailerIdsByName(retailerName);
				if(retailerIdsByName.size()<1){
					retailerIdsByName.add(0l);
				}
				dto.setRetailerIds(retailerIdsByName);
			}
			pageBean.setParameter(dto);
			
			LOGGER.info("支付ID"+dto.getPayId());
			
			findAllRetailerOrder = RemoteServiceSingleton.getInstance()
					.getDealerOrderService().findRetailerOrder(pageBean);
			
			List<RetailerOrderDto> result = findAllRetailerOrder.getResult();
			if(result!=null){
				for( int i = 0 ; i < result.size() ; i++ ){
					String imgUrl = result.get(i).getImgUrl();
					if(!imgUrl.startsWith("http")||!imgUrl.startsWith("Http")){
						imgUrl = Constants.P1+imgUrl;
						result.get(i).setImgUrl(imgUrl);
					}

					String msg = result.get(i).getMessage();
					if(msg!=null){
						result.get(i).setMessage(msg.replaceAll("\n", "<br/>"));
					}

				}
			}
			
			request.getSession().setAttribute("pb",findAllRetailerOrder);
			request.getRequestDispatcher("/WEB-INF/views/dealerseller/B2Border/order_futures_list_model.jsp")
			.forward(request, response);
				
			return null;
				
		} catch (Exception e) {
			
			LOGGER.error("获取期货订单列表错误,错误信息:"+e.getMessage());
    		
			LOGGER.error("商户Id:"+getCurrentPlatformId());
			LOGGER.error("用户:"+getCurrentUser().getUsername());
			LOGGER.error("用户ID:"+getCurrentUser().getId());
	    		
			LOGGER.error("获取期货订单列表错误,库存Id:"+dto.getOrderId());
			LOGGER.error(" 页码"+(page==null?Constants.INT1:page));
	    		
			LOGGER.error("获取期货订单列表错误,错误信息:"+e);
			
			
			return null;
		}
		
	}*/
	
	
	
	

	/**.
	 *催缴余款.
	 * @param request HttpServletRequest
	 * @param orderId Long
	 * @return String
	 */
	/*@RequestMapping(value="/confirmOrder")
	@ResponseBody
	public String confirmOrder(HttpServletRequest request,Long orderId){
		String resaultString = "";
		
		LOGGER.info("催缴余款开始");
		
		LOGGER.info("商户Id:"+getCurrentPlatformId());
		
		LOGGER.info("用户:"+getCurrentUser().getUsername());
		LOGGER.info("用户ID:"+getCurrentUser().getId());
		
		try{
			
			Cookie cookie = CookieHelper.getCookie(request,Constants.SESSION_ID);
			String sid = cookie.getValue();
			PlatformUser object =  (PlatformUser) memCachedClient.get(sid);
			
			String name = object.getName();
			Long platformId = object.getPlatformId();
			
			if(orderId!=null){
				OrderItemDetail orderItemDetail = RemoteServiceSingleton.getInstance()
						.getDealerOrderService().findRetailerOrderItemByOrderId(orderId);
				short status = orderItemDetail.getOrder().getStatus();
				if(status>Constants.STATUS51){
					
					LOGGER.info("催缴余款开始,该订单已催缴余款。,订单ID"+orderId);
					
					resaultString = "该订单已催缴余款";
					return resaultString;
				}
				boolean flag = RemoteServiceSingleton.getInstance().getDealerOrderService().handleRetailerOrder
					(orderId, Constants.STATUS61, name+"("+platformId+")");
				if (flag==true){
					resaultString="更改成功";
				}

			}else{
				resaultString="订单编号不对";
			}
		}catch (Exception e) {
			
			LOGGER.error("催缴余款,错误信息:"+e.getMessage());
	    		
			LOGGER.error("商户Id:"+getCurrentPlatformId());
			LOGGER.error("用户:"+getCurrentUser().getUsername());
			LOGGER.error("用户ID:"+getCurrentUser().getId());
	    		
			LOGGER.error("催缴余款错误,订单ID"+orderId);
	    		
			LOGGER.error("催缴余款,错误信息:"+e);
			
			resaultString="更改出错";
		}
		
		return resaultString;
	}*/
	/**.
	 * 获取期货订单详情列表.
	 * @param model model
	 * @param orderId orderID
	 * @return String
	 */
	/*@RequestMapping(value="/getOrderMinute")
	public String getOrderMinute(Model model,Long orderId){
		
		OrderService orderService = new OrderService();
		
		Retailer findByRetailerId = new Retailer();
		
		 RetailerUser retailerUser = new RetailerUser();
		
		OrderItemDetail findRetailerOrderItemByOrderId = new OrderItemDetail();
		
		String responseRealAddress = null;
		
		try{
			LOGGER.info(" 获取期货订单详情列表 orderId ==" + orderId);
			findRetailerOrderItemByOrderId = 
					RemoteServiceSingleton.getInstance()
					.getDealerOrderService().findRetailerOrderItemByOrderId(orderId);
			
			if(findRetailerOrderItemByOrderId==null){
				LOGGER.error("期货--订单详情 查询 详情为空   出错 orderId ：" + orderId);
			}
			if(findRetailerOrderItemByOrderId!=null){
				Long retailerId = findRetailerOrderItemByOrderId.getOrder().getRetailerId();
				findByRetailerId = RemoteServiceSingleton.getInstance()
						.getRetailerManagerService().findByRetailerId(retailerId); 
				responseRealAddress = orderService.getAddressByAddressId(findByRetailerId);
				String productImgeUrl = findRetailerOrderItemByOrderId.getProductImgeUrl();
				if(productImgeUrl!=null)
				findRetailerOrderItemByOrderId.setProductImgeUrl(Constants.P1+productImgeUrl);
				
				
				 retailerUser = RemoteServiceSingleton.getInstance()
					.getRetailerUserManagerService().findAdminUserByMerchantId(retailerId);
			}
			if(null != findRetailerOrderItemByOrderId){
				List<String> skuImgUrlList = findRetailerOrderItemByOrderId.getSkuImgUrlList() ;
				if(null!=skuImgUrlList){
					for( int i = 0 ; i < skuImgUrlList.size() ; i++ ){
						String imgUrl = skuImgUrlList.get(i);
						if(!imgUrl.startsWith("http")||!imgUrl.startsWith("Http")){
							imgUrl = Constants.FILESERVER1+imgUrl;
							skuImgUrlList.set(i, imgUrl);
						}
					}
				}
			}
		
		}catch (Exception e) {

			LOGGER.error("获取期货订单详情列表,");
			LOGGER.error("获取期货订单详情列表,错误信息:"+e.getMessage(),e);
			LOGGER.error("用户:"+getCurrentUser().getUsername());
	    		
			LOGGER.error("获取期货订单详情列表错误,订单ID"+orderId);
			
		}
		
		model.addAttribute("retailerUser",retailerUser);
		model.addAttribute("retailer",findByRetailerId);
		model.addAttribute("orderItem",findRetailerOrderItemByOrderId);
		model.addAttribute("responseRealAddress",responseRealAddress);
		
		return "/dealerseller/B2Border/order_futures_detail";
	}*/
}
