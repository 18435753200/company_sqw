/**
 * 
 */
package com.mall.controller.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.annotation.AuthPassport;
import com.mall.check.OrderChecks.orderFindCouponsChecks;
import com.mall.check.OrderChecks.orderFindCouponsQtyChecks;
import com.mall.check.OrderChecks.orderGetDiscountCouponPriceChecks;
import com.mall.constant.CommonConstant;
import com.mall.constant.RequestContant;
import com.mall.constant.ResponseContant;
import com.mall.controller.base.BaseController;
import com.mall.controller.impl.OrderCouponsControllerImpl;
import com.mall.customer.model.User;
import com.mall.dealer.product.dto.B2cProductShopCarShowDTO;
//import com.mall.promotion.dto.coupon.MyCouponStockB2CDTO;
//import com.mall.promotion.dto.drools.CouponConditonDtoByCartForCount1;
import com.mall.service.CustomerService;
import com.mall.utils.JsonUtil;
import com.mall.vo.CouponConditionVO;
import com.mall.vo.CustomerOrderCouponsVO;
import com.mall.wap.proxy.RemoteServiceSingleton;

/**
 * @author zhengqiang.shi
 * 2015年4月16日 上午10:35:46
 */
@Controller
@RequestMapping(value=RequestContant.ORDER_COUPONS)
public class CouponController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(CouponController.class);
	
	@Autowired
	private OrderCouponsControllerImpl orderCouponsControllerImpl;
	
	@Autowired
	private CustomerService customerService;
	
	/**
	 * 获取可用优惠券
	 * @param customerOrderCouponsVO
	 * @param br
	 * @param request
	 * @return
	 */
	/*@AuthPassport
	@RequestMapping(RequestContant.ORDER_FIND_COUPONS)
	public String findCoupons(@Validated({orderFindCouponsChecks.class}) CustomerOrderCouponsVO customerOrderCouponsVO , 
			BindingResult br,HttpServletRequest request,Model model){
		
		// 请求参数检查
		checkRequest(br);
		
		// 获取当前用户
		User user = getCurrentUser(request);
		Long userId = user.getUserId();
		
		List<MyCouponStockB2CDTO> myCouponStockB2CDTOList = null;
		if(isYueMeRequest(request)){
			// 获取可用优惠券
			myCouponStockB2CDTOList = orderCouponsControllerImpl.findMyCouponStockByOrder(customerOrderCouponsVO,userId, CommonConstant.ACCESS_MODE_YUEME);
		}else{
			// 获取可用优惠券
			myCouponStockB2CDTOList = orderCouponsControllerImpl.findMyCouponStockByOrder(customerOrderCouponsVO,userId, CommonConstant.ACCESS_MODE_WAP);
		}
		
		//单品活动使用限制处理逻辑开始
				MyCouponStockB2CDTO myCouponStockB2CDTO = null;
				List<String> limtName = new ArrayList<String>();
				limtName.add("仅限iTV电视商城“百万礼券带你嗨翻金秋”专场内商品使用");
				//String limtName = "仅限iTV电视商城“百万礼券带你嗨翻金秋”专场内商品使用";
				if(myCouponStockB2CDTOList != null && myCouponStockB2CDTOList.size()>0){
					for(int i=0;i<myCouponStockB2CDTOList.size();i++){
						myCouponStockB2CDTO = myCouponStockB2CDTOList.get(i);
						if(myCouponStockB2CDTO.getUseType() == 4l){
							if(myCouponStockB2CDTO.getLimitName()!=null && myCouponStockB2CDTO.getLimitName().size()>0){
								myCouponStockB2CDTO.setLimitName(limtName);
							}
						}
					}
				}
				//单品活动使用限制处理逻辑结束
		
		model.addAttribute("myCouponStockB2CDTOList",myCouponStockB2CDTOList);
		return ResponseContant.CONFIRM_ORDER_COUPONS;
	}*/
	
	/**
	 * 订单确认页
	 * 获取每个商品分组（订单）的可用优惠券数量
	 * @param customerOrderCouponsVO
	 * @param br
	 * @param request
	 * @return
	 */
	@AuthPassport
	@ResponseBody
	@RequestMapping(RequestContant.ORDER_FIND_COUPONS_QTY)
	public String findCouponsQty(@Validated({orderFindCouponsQtyChecks.class}) CustomerOrderCouponsVO customerOrderCouponsVO , 
			BindingResult br,HttpServletRequest request,Model model){
		
		// 请求参数检查
		checkRequest(br);
		
		// 获取当前用户
		User user = getCurrentUser(request);
		Long userId = user.getUserId();
		
		//List<CouponConditonDtoByCartForCount1> allCountByCart = null;
		/*if(isYueMeRequest(request)){
			// 获取可用优惠券
			allCountByCart = orderCouponsControllerImpl.getAllCountByCart(customerOrderCouponsVO,userId, CommonConstant.ACCESS_MODE_YUEME);
		}else{
			// 获取可用优惠券
			allCountByCart = orderCouponsControllerImpl.getAllCountByCart(customerOrderCouponsVO,userId, CommonConstant.ACCESS_MODE_WAP);
		}
		
		if(allCountByCart == null || allCountByCart.isEmpty()){
			return "500";
		}*/
		
		List<CouponConditionVO> ccvList = new ArrayList<CouponConditionVO>();
		/*for(CouponConditonDtoByCartForCount1 c : allCountByCart){
			CouponConditionVO ccv = new CouponConditionVO();
			ccv.setIndex(c.getIndex());
			List<String> stockIdList = new ArrayList<String>();
			for(Long l : c.getStockid()){
				stockIdList.add(String.valueOf(l));
			}
			ccv.setStockIdList(stockIdList);
			ccvList.add(ccv);
		}*/
		
		
		return JsonUtil.serializeBeanToJsonString(ccvList);
	}
	
	/**
	 * 订单确认页，确认使用优惠券，获取优惠金额
	 * @param customerOrderCouponsVO
	 * @param br
	 * @param request
	 * @param model
	 * @return
	 */
	@AuthPassport
	@ResponseBody
	@RequestMapping(RequestContant.ORDER_GET_DISCOUNT)
	public String getDiscountCouponPrice(@Validated({orderGetDiscountCouponPriceChecks.class})CustomerOrderCouponsVO customerOrderCouponsVO,
			BindingResult br,HttpServletRequest request,Model model){
		User user = getCurrentUser(request);
		Long userId = user.getUserId();
		// 请求参数检查
		checkRequest(br);
		
		BigDecimal discountCouponPrice = null;
		/*if(isYueMeRequest(request)){
			// 获取使用优惠券金额
			discountCouponPrice = orderCouponsControllerImpl.getDiscountCouponPrice(customerOrderCouponsVO, CommonConstant.ACCESS_MODE_YUEME, userId);
		}else{
			// 获取使用优惠券金额
			discountCouponPrice = orderCouponsControllerImpl.getDiscountCouponPrice(customerOrderCouponsVO, CommonConstant.ACCESS_MODE_WAP, userId);
		}
		log.info("call service end.return:"+discountCouponPrice);*/
		
		if(discountCouponPrice == null){
			return "0";
		}
		
		return discountCouponPrice.toString();
	}
	/**
	 * 调用充值专用券列表接口
	 * @param request
	 * @param mobile 手机号
	 * @param skuId 购买的单品ID
	 * @return
	 */
	@RequestMapping(RequestContant.COUPON_FOR_CMCC)
	@ResponseBody
	public String getCouponListForCTCC(HttpServletRequest request,String mobile,String skuId,String callback,String accessMode,String userId){
		log.info("request parameter:   mobile----" + mobile + "------skuId----" + skuId + "----accessMode----" + accessMode + "----userId----" + userId);
		/*List<MyCouponStockB2CDTO> list = null;
		List<MyCouponStockB2CDTO> listBak = new ArrayList<MyCouponStockB2CDTO>();*/
		List<Object> list=new ArrayList<Object>();
		List<Object> listBak=new ArrayList<Object>();
		String json = "";
		try{
			User user = null;
			if("3".equals(accessMode)){
				user = getCurrentUser(request);
				log.info("user--------" + user);
			}
			
			List<Long> skuIds = new ArrayList<Long>();
			skuIds.add(Long.parseLong(skuId));
			List<B2cProductShopCarShowDTO> skuDtos = RemoteServiceSingleton.getInstance().getDealerProductSkuService().findB2cShopCarDtoBySkuIds(skuIds);
			/*if("3".equals(accessMode)){
				if(null != user && null != user.getUserId() && null != skuDtos){
					list = RemoteServiceSingleton.getInstance().getUserCouponRuleApi().getAbleUseCouponsForCTCC(user.getUserId());
				}else{
					log.info("WAP用户未登陆，充值查券结束");
					return "";
				}
			}else if("2".equals(accessMode) || "4".equals(accessMode)){
				if(null != userId && !"".equals(userId)){
					list = RemoteServiceSingleton.getInstance().getUserCouponRuleApi().getAbleUseCouponsForCTCC(Long.parseLong(userId));
				}else{
					log.info("APP用户未登陆，充值查券结束");
					return "";
				}
			}*/
			if(null != list && list.size() > 0){
				
				// 检查手机号是否为当前用户的手机号
				if(StringUtils.isNotBlank(mobile)){
					boolean sameAccount = isSameAccount(user, mobile, userId);
					if(!sameAccount){
						if(StringUtils.isBlank(callback)){
							json = "403";
						}else{
							json = callback + "(403)";
						}
						
						return json;
					}
				}
				
				BigDecimal price = skuDtos.get(0).getPrice();
				/*for (MyCouponStockB2CDTO myCouponStockB2CDTO : list) {
					if(price.compareTo(myCouponStockB2CDTO.getOrderLimitPrice()) >= 0){
						List<String> coupons = new ArrayList<String>();
						coupons.add("" + myCouponStockB2CDTO.getCouponstockid());
						myCouponStockB2CDTO.setLimitName(coupons);
						listBak.add(myCouponStockB2CDTO);
					}
				}*/
				log.info("获取可用充值专用优惠券列表结束");
				if(listBak.size() > 0){
					if("".equals(callback) || null == callback){
						json = JSONArray.fromObject(listBak).toString();
					}else{
						json = callback + "(" + JSONArray.fromObject(listBak).toString() + ")";
					}
				}else{
					log.info("获取到的可用充值券不满足使用条件");
				}
			}else{
				log.info("未查到可用充值券");
			}
			log.info("调用充值专用券列表接口结束，返回值：" + json);
		}catch(Exception e){
			log.info("获取充值专用券列表接口异常，" + e.getMessage());
		}
		
		if(StringUtils.isNotBlank(json)){
			return json;
		}
		
		if(StringUtils.isBlank(callback)){
			return json;
		}else{
			return callback + "(" +json+ ")";
		}
		
	}
	
	/**
	 * 是否同一账户
	 * @return
	 */
	private boolean isSameAccount(User user, String mobile, String userId){
		
		if(user != null){
			String userMobile = user.getMobile();
			if(userMobile.equals(mobile)){
				log.info("userMobile.equals(mobile) isSameAccount return true.");
				return true;
			}
		}else if(StringUtils.isNotBlank(userId) && !"0".equals(userId)){
			User userApp = customerService.findUserById(Long.valueOf(userId));
			
			if(userApp != null){
				String appUserMobile = userApp.getMobile();
				if(appUserMobile.equals(mobile)){
					log.info("appUserMobile.equals(mobile) isSameAccount return true.");
					return true;
				}
			}
		}
		
		return false;
		
	}
	
}
