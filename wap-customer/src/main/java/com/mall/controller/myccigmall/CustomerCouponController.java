package com.mall.controller.myccigmall;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mall.annotation.AuthPassport;
import com.mall.constant.RequestContant;
import com.mall.constant.ResponseContant;
import com.mall.controller.base.BaseController;
import com.mall.customer.model.User;
//import com.mall.promotion.dto.coupon.MyCouponStockB2CDTO;
import com.mall.service.CusCouponService;

/**
 * 用户优惠券 金券
 * 
 * @author ccigQA01
 * 
 */
@Controller
@RequestMapping(value = RequestContant.CUS_COUPON)
public class CustomerCouponController extends BaseController {
	private static final Logger log = LoggerFactory
			.getLogger(CustomerCouponController.class);
	@Autowired
	private CusCouponService couponService;

	/**
	 * 获取优惠券
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_COUPON_GET)
	public String getCoupon(String type, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("getCoupon execute...");
		User currentUser = getCurrentUser(request);
		//List<MyCouponStockB2CDTO> counponByType = couponService.getCounponByType(type,currentUser,model,request,response);
		
		//单品活动使用限制处理逻辑开始
		//MyCouponStockB2CDTO myCouponStockB2CDTO = null;
		List<String> limtName = new ArrayList<String>();
		limtName.add("仅限iTV电视商城“百万礼券带你嗨翻金秋”专场内商品使用");
		//String limtName = "仅限iTV电视商城“百万礼券带你嗨翻金秋”专场内商品使用";
		/*if(counponByType != null && counponByType.size()>0){
			for(int i=0;i<counponByType.size();i++){
				myCouponStockB2CDTO = counponByType.get(i);
				if(myCouponStockB2CDTO.getUseType() == 4l){
					if(myCouponStockB2CDTO.getLimitName()!=null && myCouponStockB2CDTO.getLimitName().size()>0){
						myCouponStockB2CDTO.setLimitName(limtName);
					}
				}
			}
		}*/
		//单品活动使用限制处理逻辑结束
		
		//model.addAttribute("coupons", counponByType);
		model.addAttribute("type", type);
		
		return ResponseContant.CUS_TO_COUPON;

	}
	
	/**
	 * 跳转优惠券使用规则
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	 @RequestMapping(value=RequestContant.CUS_COUPON_RULE)
	 @AuthPassport
	 public String couponRule(Model model,HttpServletRequest
	 request,HttpServletResponse response){
		 log.info("couponRule  execute.....");
		 
		 return ResponseContant.CUS_TO_COUPON_RULE;
	 }

	// @RequestMapping(value=RequestContant.CUS_ADD_ADDRESS)
	// public String addAddress(Model model,HttpServletRequest
	// request,HttpServletResponse response){
	// log.info("");
	// return null;
	//
	// }

}
