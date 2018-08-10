package com.mall.controller.base;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import javax.annotation.Resource;
//import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.alibaba.dubbo.common.utils.StringUtils;
//import com.alibaba.dubbo.remoting.RemotingException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mall.constant.CommonConstant;
import com.mall.constant.RequestContant;
//import com.mall.controller.order.CartController;
import com.mall.customer.model.User;
import com.mall.customer.order.dto.CartActivityGroupVO;
import com.mall.customer.order.dto.CartDTO;
import com.mall.customer.order.dto.CartGroupVO;
import com.mall.customer.order.dto.CartResultDTO;
import com.mall.customer.order.dto.CartSkuDTO;
//import com.mall.customer.order.dto.CartSkuGroupDTO;
import com.mall.customer.order.dto.OrderDTO;
import com.mall.customer.order.dto.OrderItemDTO;
import com.mall.exception.BadRequestException;
//import com.mall.promotion.dto.b2c.B2CGiftProductDto;
//import com.mall.promotion.dto.b2c.B2CPromotionDto;
//import com.mall.promotion.dto.ruleSkuDto.SkuGiftDto;
//import com.mall.retailer.model.RetailerUser;
import com.mall.utils.CookieUtil;
//import com.mall.wap.proxy.RemoteServiceSingleton;

/**
 * Controller中公用的方法抽象到该类
 * @author yuanxiayang
 *
 */
@Controller
public  class BaseController extends MultiActionController {
	
	private static Logger logger = Logger.getLogger(BaseController.class);
	
	//加载图片预览大图url--原图
	@Value("${FILESERVER1}")
	protected String picUrl1;
	
	//200*200
	@Value("${FILESERVER2}")
	protected String picUrl2;
	
	//340*340
	@Value("${FILESERVER3}")
	protected String picUrl3;
	
	//长描
	@Value("${FILESERVER4}")
	protected String picUrl4;
	
	/**
	 * slf4j log4j
	 */
	//private static Logger logger = LoggerFactory.getLogger(BaseController.class);

	@Autowired
	protected MemcachedClient memcachedClient;
	
	protected boolean isYueMeRequest(HttpServletRequest request) {
		String cookieValue = CookieUtil.getCookieValue(request,
				CommonConstant.ORDER_FORM_KEY);

		if (!StringUtils.isBlank(cookieValue)
				&& CommonConstant.CHANNEL_FROM_VALUE_YUEME.equals(cookieValue.trim())) {
			return true;
		}

		return false;
	}
	
	protected String getEntryType(HttpServletRequest request){
		boolean entryType = isYueMeRequest(request);
		String typeStr = RequestContant.WAP_CHANUAL_TYPE;
		if(entryType){
			typeStr = RequestContant.YUEME_CHANUAL_TYPE;
		}
		return typeStr;
	}
	
	protected Long userChannelDifferent(Long userId) {
		if (userId != null) {
			StringBuffer sb = new StringBuffer(userId.toString());
			sb.append(10001);

			userId = Long.valueOf(sb.toString());
			
			return userId;
		}
		return null;
	}
	
	/**
	 * 功能：cookie中取出Session_id 
	 * 然后去memcached中取当前登录用户
	 * 
	 * @param obj
	 * @param request
	 */
	public User getCurrentUser(HttpServletRequest request) {

		logger.info("start to get current user");
		
		// 获取cookie值
		String session_id = CookieUtil.getCookieValue(request, CommonConstant.SESSION_ID);
		
		if (StringUtils.isEmpty(session_id)) {
			logger.info("no session id in cookie");
			return null;
		}
		logger.info("have session id,sessionId:"+session_id);
		
		// 从缓存服务器获取当前用户
		User user = null;

		try {
			logger.info("start to get user from memcached.");
			user=memcachedClient.get(CommonConstant.CUSTOMER_LOGIN+session_id);
			if(user != null){
				logger.info("get user from memcached success.user:"+user.getUserName());
			}
		} catch (Exception e) {
			logger.error("get user from memcached failed! message:"+e.getMessage(),e);
			return null;
		}

		return user;
	}

	/**
	 * 功能：该方法将PTO对象转化成JSON传给前台.
	 * 
	 * @param obj
	 * @param request
	 */
	public static void responseErrorMassage(String errorMessage,
			HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			JSONArray array = new JSONArray();
			array.add(JSON.parse("{\"status\":\"error\"}"));
			array.add(JSON.parse("{\"message\":\"" + errorMessage + "\"}"));
			out.write(JSONArray.toJSONString(array));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}

	/**
	 * 验证请求参数
	 * @param br
	 */
	public void checkRequest(BindingResult br){
		if(br.hasErrors()){
			
			Map<String, List<String>> errorMap = new HashMap<String, List<String>>();
			List<String> errorList = new ArrayList<String>();
			
			List<FieldError> fieldErrors = br.getFieldErrors();
			logger.info("==========start to print error message...===========");
			for(FieldError oe : fieldErrors){
				String field = oe.getField();
				String message = oe.getDefaultMessage();
				errorList.add(field+":"+message);
				logger.info(field+message);
			}
			errorMap.put("errorMessage", errorList);
			logger.info("==========end to print error message=================");
			
			String jsonErrors = JSON.toJSONString(errorMap);
			throw new BadRequestException(jsonErrors);
		}
	}
	
	/**
	 * CartDTO设置图片地址全路径
	 * @param cartDTO
	 */
	/*public void setImgUrl4CartDTO(CartDTO cartDTO){
	 * 
		if(cartDTO != null && !cartDTO.getSkuGroupDTOList().isEmpty()){
		
			List<CartSkuGroupDTO> skuGroupDTOList = cartDTO.getSkuGroupDTOList();
			
			for (CartSkuGroupDTO cartSkuGroupDTO : skuGroupDTOList) {
			
				List<B2CPromotionDto> promotionDTOList = cartSkuGroupDTO.getPromotionDTOList();
				for (B2CPromotionDto b2cPromotionDto : promotionDTOList) {
				
					// 设定商品图片地址路径
					List<CartSkuDTO> skuDtoList = b2cPromotionDto.getSkuDtoList();
					if(skuDtoList != null){
						for (CartSkuDTO cartSkuDTO : skuDtoList) {
							cartSkuDTO.setImgUrl(picUrl2+cartSkuDTO.getImgUrl());
						}
					}
					
					// 设定赠品图片地址路径
					List<B2CGiftProductDto> giftProductList = b2cPromotionDto.getGiftProductList();
					if(giftProductList != null){
						for (B2CGiftProductDto b2CGiftProductDto : giftProductList) {
							b2CGiftProductDto.setImgURL(picUrl2+b2CGiftProductDto.getImgURL());
						}
					}
				}
			}
		}
	}*/
	public void setImgUrl4CartDTO(CartDTO cartDTO){
			
			if(cartDTO != null && !cartDTO.getCartGroupVOList().isEmpty()){
	
				List<CartGroupVO> cartGroupVOList = cartDTO.getCartGroupVOList();
				
				if(cartGroupVOList != null){
					
					for (CartGroupVO cartGroupVO : cartGroupVOList) {
						
						List<CartActivityGroupVO> activityGroupList = cartGroupVO.getActivityGroupList();
						
						if(activityGroupList == null){
							continue;
						}
						for (CartActivityGroupVO cartActivityGroupVO : activityGroupList) {
							
							// 设定商品图片地址路径
							List<CartSkuDTO> skuDtoList = cartActivityGroupVO.getSkuList();
							if(skuDtoList != null){
								for (CartSkuDTO cartSkuDTO : skuDtoList) {
									cartSkuDTO.setImgUrl(picUrl3+cartSkuDTO.getImgUrl());
								}
							}
							
							// 设定赠品图片地址路径
//							List<SkuGiftDto> giftList = cartActivityGroupVO.getGiftList();
//							if(giftList != null){
//								for (SkuGiftDto skuGiftDto : giftList) {
//									skuGiftDto.setImageUrl(picUrl3+skuGiftDto.getImageUrl());
//								}
//							}
						}
					}
				}
			}
		}
	
	/**
	 * CartDTO设置图片地址全路径
	 * @param cartDTO
	 */
	public void setImgUrl4CartResultDTO(CartResultDTO cartResultDTO){
		
		if(cartResultDTO != null){
			CartDTO cartDTO = cartResultDTO.getCartDTO();
			
			if(cartDTO != null && !cartDTO.getCartGroupVOList().isEmpty()){
				
				List<CartGroupVO> cartGroupVOList = cartDTO.getCartGroupVOList();
				
				if(cartGroupVOList != null){
					for (CartGroupVO cartGroupVO : cartGroupVOList) {
						
						List<CartActivityGroupVO> activityGroupList = cartGroupVO.getActivityGroupList();
						
						if(activityGroupList == null){
							continue;
						}
						for (CartActivityGroupVO cartActivityGroupVO : activityGroupList) {
							
							// 设定商品图片地址路径
							List<CartSkuDTO> skuDtoList = cartActivityGroupVO.getSkuList();
							if(skuDtoList != null){
								for (CartSkuDTO cartSkuDTO : skuDtoList) {
									cartSkuDTO.setImgUrl(picUrl2+cartSkuDTO.getImgUrl());
								}
							}
							
							// 设定赠品图片地址路径
//							List<SkuGiftDto> giftList = cartActivityGroupVO.getGiftList();
//							if(giftList != null){
//								for (SkuGiftDto skuGiftDto : giftList) {
//									skuGiftDto.setImageUrl(picUrl2+skuGiftDto.getImageUrl());
//								}
//							}
						}
						
					}
				}
			}
		}
	}
	
	/**
	 * OrderDTO设置图片地址全路径
	 * @param cartDTO
	 */
	public void setImgUrl4OrderDTO(OrderDTO orderDTO){
		
		if(orderDTO != null && orderDTO.getOrderItemDTOs() != null && !orderDTO.getOrderItemDTOs().isEmpty()){

			List<OrderItemDTO> orderItemDTOList = orderDTO.getOrderItemDTOs();
			
			for (int i = 0; i < orderItemDTOList.size(); i++) {
				
				String imgUrl = orderItemDTOList.get(i).getImgUrl();
				orderItemDTOList.get(i).setImgUrl(picUrl2+imgUrl);
			}
		}
	}
	
	/**
	 * 是否 ajax 请求
	 * @param request
	 * @return
	 */
	public boolean isAjaxRequest(HttpServletRequest request) {
		if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request
				.getHeader("X-Requested-With") != null && request.getHeader(
				"X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
			return false;
		}
		return true;
	}
}
