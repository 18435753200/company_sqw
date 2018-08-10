/*package com.mall.controller.promotions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.mybatis.utility.PageBean;
import com.mall.promotion.api.activity.web.ActiveGiftApi;
import com.mall.promotion.api.activity.web.ActiveMasterApi;
import com.mall.promotion.api.coupon.user.UserCouponRuleApi;
import com.mall.promotion.dto.activity.ActiveGiftDto;
import com.mall.promotion.po.activity.ActiveGift;
import com.mall.promotion.po.activity.ActiveGiftB2C;
import com.mall.promotion.po.activity.ActiveMaster;
import com.mall.promotion.po.activity.PlatformRule;
import com.mall.promotion.po.coupon.CouponRule;
import com.mall.promotion.utils.ActivityConstants;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;

@Controller
@RequestMapping(value = "/gift")
public class GiftController extends BaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(GiftController.class);


	*//**
	 * . 此方法用于日期的转换
	 * 
	 * @param binder
	 *            WebDataBinder
	 *//*
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	@RequestMapping(value = "/toGiftList")
	public String toGiftList(Long ruleId, Model model,Integer pType) {
		LOGGER.info("to GiftList Page!");
		model.addAttribute("ruleId", ruleId);
		model.addAttribute("pType", pType);
		PlatformRule platformRule = RemoteServiceSingleton.getInstance().getPlatformRuleApi().findPlatFormRuleByPFId(ruleId);
		model.addAttribute("giftType", platformRule.getpType());
		//是否可以增加赠品
		List<ActiveGift> findGiftByRuleId = RemoteServiceSingleton.getInstance().getActiveGiftApi().findGiftByRuleId(ruleId);
		List<ActiveGiftB2C> findGiftByRuleId1 = RemoteServiceSingleton.getInstance().getActiveGiftApi().findGiftByRuleIdB2C(ruleId);
		if((findGiftByRuleId != null && findGiftByRuleId.size() > 0)||(findGiftByRuleId1 != null && findGiftByRuleId1.size() > 0)){
		    model.addAttribute("giftFlag", false);    
		}else{
		    model.addAttribute("giftFlag", true);
		}
	
		return "promotions/activity/giftList";
	}

	*//**
	 * 通过条件查询活动规则下的赠品
	 * 
	 * @param pageBean
	 * @return
	 *//*
	@RequestMapping("/findGiftByCondition")
	public String findGiftByCondition(HttpServletRequest request,
			HttpServletResponse response, Integer page,
			ActiveGiftDto activeGiftDto, Model model,Integer pType) { 
		try {
			LOGGER.info("to findGiftByCondition Page!");
			PageBean<ActiveGift> pageBean = new PageBean<ActiveGift>();
			PageBean<ActiveGiftB2C> pageBean1 = new PageBean<ActiveGiftB2C>();
//			activeGiftDto.setGiftType((short) 1); // TODO 赠品类型暂时写死
			
			if (page != null && page != 0) {
				pageBean.setPage(page); 
				pageBean1.setPage(page);
			} else {
				pageBean.setPage(1);
				pageBean1.setPage(1);
			}
			ActiveGiftApi activeGiftApi = RemoteServiceSingleton.getInstance().getActiveGiftApi();
			if(pType == 0){
				pageBean.setParameter(activeGiftDto);
				pageBean = activeGiftApi.findActiveGift(pageBean);
				request.getSession().setAttribute("pb", pageBean);
			}else if(pType == 1){
				pageBean1.setParameter(activeGiftDto);
				pageBean1 = activeGiftApi.findActiveGiftB2C(pageBean1);
				request.getSession().setAttribute("pb", pageBean1);
			}
			
			request.getRequestDispatcher(
					"/WEB-INF/views/promotions/activity/modelpage/gift_model.jsp")
					.forward(request, response);
		} catch (Exception e) {
			LOGGER.error("查询赠品表异常：" + e.getMessage(), e);
		}
		return null;
	}

	*//**
	 * 删除赠品（假删除？）
	 * 
	 * @return
	 *//*
	@RequestMapping("/deleteGift")
	public String deleteGift(ActiveGift gift, Model model) {
		try {
			RemoteServiceSingleton.getInstance().getActiveGiftApi()
					.deleteActiveGift(gift.getGiftId());
		} catch (Exception e) {
			LOGGER.error("删除赠品失败" + e.getMessage(), e);
		}
		model.addAttribute("ruleId", gift.getRuleId());
		model.addAttribute("giftType", gift.getGiftType());
		return "forward:/gift/toGiftList";
	}

	*//**
	 * 跳转到添加赠品页面,根据不同的类型跳转到不同的展示页面
	 * 
	 * @return
	 *//*
	@RequestMapping("/toGiftAdd")
	public String toGiftAdd(Long ruleId, Short giftType, Model model,CouponRule couponRule,Integer pType) {
		ActiveGiftApi activeGiftApi = RemoteServiceSingleton.getInstance()
				.getActiveGiftApi();
		model.addAttribute("ruleId", ruleId);
		model.addAttribute("pType", pType);
		model.addAttribute("giftType", giftType);
		if(pType == 0){//校验B2B赠品
			List<ActiveGift> giftByRuleId = activeGiftApi.findGiftByRuleId(ruleId); // 判断活动规则是否已经有赠品,目前一个活动规则只有一个赠品
	        if (giftByRuleId != null && giftByRuleId.size() > 0) {
	            LOGGER.info("back giftList Page!");
	            return "promotions/activity/giftList";
	        }
		}else if(pType == 1){//校验B2C赠品
			List<ActiveGiftB2C> list = activeGiftApi.findGiftByRuleIdB2C(ruleId);
			if(list != null && list.size() > 0) {
	            LOGGER.info("back giftList Page!");
	            return "promotions/activity/giftList";
	        }
		}
		// 优惠劵礼品卡
		if(giftType.equals(ActivityConstants.GOLD_COUPON_FLAG)||giftType.equals(ActivityConstants.CCIG_COUPON_FLAG)||giftType.equals(ActivityConstants.GIFT_CARDS_FLAG)){
    		LOGGER.info("to GiftAdd Page!");
    		
    		if(couponRule.getCouponruleid() == null || couponRule.getCouponruleid() == 0)
    		    return "promotions/activity/giftAdd";
    		else{
    		    couponRule = RemoteServiceSingleton.getInstance().getCouponRuleService().getCouponRulePoById(couponRule.getCouponruleid());
    		    model.addAttribute("couponRule", couponRule);
    		    return "promotions/activity/creatGift";
    		}
    	//积分
    	}else if (giftType.equals(ActivityConstants.INTEGRAL_FLAG)){
    	    model.addAttribute("name", "增积分数量：");
    	    return "promotions/activity/creatGift";
    	//现金
    	}else if (giftType.equals(ActivityConstants.MONEY_FLAG)){
    	    model.addAttribute("name", "现金直降值：");
    	    return "promotions/activity/creatGift";
	    //折扣
        }else if (giftType.equals(ActivityConstants.DISCOUNT_FLAG)){
            model.addAttribute("name", "折扣比");
            return "";
        //包邮
        }else if (giftType.equals(ActivityConstants.FREE_SHIPPING_FLAG)){
            return "promotions/activity/creatGift";
        //sku单品
        }else if (giftType.equals(ActivityConstants.PRODUCT_SKU_FLAG)){
            return "";
        }else {
            return "";
        }
	}

	*//**
	 * 给规则添加赠品
	 * 
	 * @return
	 *//*
	@RequestMapping("/addGiftForRule")
	@ResponseBody
	public String addGiftForRule(ActiveGift gift, CouponRule couponRule,
			Model model,Integer pType) {
		try {
			LOGGER.info("------------------------pType:"+pType);
		    model.addAttribute("ruleId", gift.getRuleId());
		    ActiveGiftApi activeGiftApi = RemoteServiceSingleton
                    .getInstance().getActiveGiftApi();
		    //如果是礼品卡 金劵 鑫劵
            if (gift.getGiftType().equals(ActivityConstants.GOLD_COUPON_FLAG)
                    || gift.getGiftType().equals(ActivityConstants.CCIG_COUPON_FLAG)
                    || gift.getGiftType().equals(ActivityConstants.GIFT_CARDS_FLAG)) {
                if(pType == 0){//B2B校验是否关联了赠品
	                List<ActiveGift> findGiftByRuleId = activeGiftApi
	                        .findGiftByRuleId(gift.getRuleId());
	               
	                if (findGiftByRuleId != null && findGiftByRuleId.size() > 0) { // 目前一个规则只能绑定一个赠品
	                    return "一个规则只能绑定一个赠品";
	                }
                }else if(pType == 1){//B2C校验是否关联了赠品
                	List<ActiveGiftB2C> list = activeGiftApi.findGiftByRuleIdB2C(gift.getRuleId());
                	if(null != list && list.size() > 0){
                		return "一个规则只能绑定一个赠品";
                	}
                }
                return this.creatCouponGift(gift, couponRule,pType);
                //现金
            }else if (gift.getGiftType().equals(ActivityConstants.MONEY_FLAG)){
                activeGiftApi.createActiveGift(gift);
                return "success";
            }else if (gift.getGiftType().equals(ActivityConstants.INTEGRAL_FLAG)){
                activeGiftApi.createActiveGift(gift);
                return "success";
            //折扣
            }else if (gift.getGiftType().equals(ActivityConstants.DISCOUNT_FLAG)){
                activeGiftApi.createActiveGift(gift);
                return "success";
            //包邮
            }else if (gift.getGiftType().equals(ActivityConstants.FREE_SHIPPING_FLAG)){
                activeGiftApi.createActiveGift(gift);
                return "success";
            //sku单品
            }else if (gift.getGiftType().equals(ActivityConstants.PRODUCT_SKU_FLAG)){
                activeGiftApi.createActiveGift(gift);
                return "success";
            }else {
                return "赠品类型异常"+gift.getGiftType();
            }
            
            
		} catch (Exception e) {
			LOGGER.error("添加赠品失败" + e.getMessage(), e);
		}
		return "error";
	}
	
	private String creatCouponGift(ActiveGift gift, CouponRule couponRule,Integer pType){
	    UserCouponRuleApi userCouponRuleApi = RemoteServiceSingleton
                .getInstance().getUserCouponRuleApi();
        
        boolean findCouponStartTime = userCouponRuleApi
                .findCouponStartTime(couponRule.getCouponruleid());
        // true 为有效券规则，可以与活动规则绑定
        if (findCouponStartTime) {
            ActiveGiftApi activeGiftApi = RemoteServiceSingleton
                    .getInstance().getActiveGiftApi();
            couponRule = RemoteServiceSingleton.getInstance().getCouponRuleService().getCouponRulePoById(couponRule.getCouponruleid());
            if(pType == 0){
	            gift.setsGiftId(couponRule.getCouponruleid());
	//            gift.setGiftDesc(couponRule.getCouponrulename());
	
	            activeGiftApi.createActiveGift(gift);
            }else if(pType == 1){
            	ActiveGiftB2C activeGiftB2C = new ActiveGiftB2C();
            	activeGiftB2C.setRuleId(gift.getRuleId());
            	activeGiftB2C.setGiftType(gift.getGiftType());
            	activeGiftB2C.setStatus((short) 1);
            	activeGiftB2C.setsGiftId(couponRule.getCouponruleid());
            	activeGiftB2C.setGiftDesc(gift.getGiftDesc());
            	activeGiftApi.createActiveGiftB2C(activeGiftB2C);
            }
            
        }else {
            return "该卷已经过期";
        }
        return "success";
	}

	*//**
	 * 查询券规则
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @param ruleId
	 * @param giftType
	 * @return
	 *//*
	@RequestMapping("/findGiftByCon")
	public String findGiftByCon(HttpServletRequest request,
			HttpServletResponse response, Integer page, Long ruleId,
			Integer giftType,Integer pType) {
		try {
			LOGGER.info("to addGift Page!");
			LOGGER.info("pType:"+pType);
			PageBean<CouponRule> pageBean = new PageBean<CouponRule>();
			if (page != null && page != 0) {
				pageBean.setPage(page);
			} else {
				pageBean.setPage(1);
			}
			pageBean.setPageSize(Constants.PAGESIZE);
			ActiveMasterApi activeMasterApi = RemoteServiceSingleton
					.getInstance().getActiveMasterApi();
			ActiveMaster activeMaster = activeMasterApi
					.findActiveMasterByRuleId(ruleId);
			if(null != activeMaster){
			// 调用服务代码
				CouponRule couponRule = new CouponRule();
				couponRule.setCoupontype(giftType);
				couponRule.setActiveTime(activeMaster.getExpiringFrom());
				if(pType == 1){
					couponRule.setIsb2c(1);
				}else{
					couponRule.setIsb2c(0);
				}
				pageBean.setParameter(couponRule);
				UserCouponRuleApi userCouponRuleApi = RemoteServiceSingleton
						.getInstance().getUserCouponRuleApi();
				pageBean = userCouponRuleApi.findToCouponRuleList(pageBean);
				request.getSession().setAttribute("pb", pageBean);
				request.getSession().setAttribute("ruleId", ruleId);
				request.getSession().setAttribute("pType", pType);
				request.getRequestDispatcher(
						"/WEB-INF/views/promotions/activity/modelpage/addGift_model.jsp")
						.forward(request, response);
			}else{
				LOGGER.info("activeMaster is null");
			}
		} catch (Exception e) {
			LOGGER.error("查询赠品列表异常：" + e.getMessage(), e);
		}
		return null;
	}
}
*/