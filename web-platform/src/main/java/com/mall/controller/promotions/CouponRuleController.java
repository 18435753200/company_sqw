/*package com.mall.controller.promotions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.category.po.SubBrand;
import com.mall.category.po.TdCatePub;
import com.mall.comment.exception.BusinessException;
//import com.mall.retailer.model.Retailer;
import com.mall.controller.base.BaseController;
import com.mall.customer.model.User;
//import com.mall.dealer.order.exception.BusinessException;
import com.mall.mybatis.utility.PageBean;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.promotion.api.coupon.web.CouponRuleApi;
import com.mall.promotion.po.coupon.Coupon;
import com.mall.promotion.po.coupon.CouponRule;
import com.mall.promotion.po.coupon.CouponRuleCondent;
import com.mall.promotion.po.coupon.CouponRuleCondentB2C;
import com.mall.promotion.po.coupon.CouponStock;
import com.mall.utils.Constants;

import net.sf.json.JSONArray;

*//** w 
 * @author zlj 优惠券规则管理.
 * 
 *//*
@Controller
@RequestMapping(value = "/couponRule")
public class CouponRuleController extends BaseController {

    *//**
     * LOGGER
     *//*
    private static final Log LOGGER = LogFactory.getLogger(CouponRuleController.class);
    private static final String SUC_FLAG = "success";
    private static final String FAIL_FLAG = "error";

    *//**
     * 进如优惠券规则列表查询页面
     *//*
    @RequestMapping(value = "/toShowRuleList")
    public String getCouponRulePage(Model model, Long couponId,Integer coupontype) {
    	Coupon coupon = RemoteServiceSingleton.getInstance().getCouponService().getCouponPoById(couponId);
        LOGGER.info("to couponRule Page By couponId=" + couponId + " coupontype=" + coupontype);
        model.addAttribute("couponId", couponId);
        model.addAttribute("coupontype", coupon.getCoupontype());
        return "/promotions/coupon/couponRuleList";
    }

    *//**
     * 查询优惠券规则列表
     * 
     * @param request
     * @param response
     * @param page
     * @return
     *//*
    @RequestMapping(value = "/getCouponRulesByCon")
    public String getCouponRulesByCon(HttpServletRequest request, HttpServletResponse response, Integer page,
            CouponRule paramPo) {
        try {
            PageBean<CouponRule> pageBean = new PageBean<CouponRule>();
            if (page != null && page != 0) {
                pageBean.setPage(page);
            } else {
                pageBean.setPage(1);
            }
            pageBean.setPageSize(Constants.PAGESIZE);
            pageBean.setParameter(paramPo);
            pageBean = RemoteServiceSingleton.getInstance().getCouponRuleService().findCouponRuleList(pageBean);
            request.getSession().setAttribute("pb", pageBean);
            request.getSession().setAttribute("couponId", paramPo.getCouponid());
            request.getRequestDispatcher("/WEB-INF/views/promotions/coupon/modelpage/couponRules_model.jsp").forward(
                    request, response);
        } catch (Exception e) {
            LOGGER.error("查询优惠券列表异常：" + e.getMessage(), e);
        }
        return null;
    }

    *//**
     * 新增页面
     * 
     * @param request
     *            HttpServletRequest.
     * @param promotionId
     *            Long.
     * @return
     *//*
    @RequestMapping(value = "/toCreateRule")
    public String toCreateRule(HttpServletRequest request,Model model, Long couponId,Integer coupontype) {
    	try{
	    	List<TdCatePub> topCategoryList = RemoteServiceSingleton.getInstance().getCategoryServiceRpc().getTopCategoryList();
			request.getSession().setAttribute("topCategoryList", topCategoryList);
	        LOGGER.info("to toCreateRule Page:couponId=" + couponId);
	        String errorMsg = "";
	        Coupon coupon = null;
	        if (couponId == null) {
	            errorMsg = "优惠券id丢失！";
	        } else {
	            // 查询优惠券信息
	            coupon = RemoteServiceSingleton.getInstance().getCouponService().getCouponPoById(couponId);
	            errorMsg = this.checkCouponEndTime(coupon);
	
	        }
	        model.addAttribute("coupon", coupon);
	        model.addAttribute("errorMsg", errorMsg);
	        model.addAttribute("coupontype", coupontype);
	        return "/promotions/coupon/couponRuleEdit";
    	}catch(Exception e){
    		throw new BusinessException(e.getMessage());
    	}
    }
    
    *//**
     * 新增页面  B2C
     * 
     * @param request
     *            HttpServletRequest.
     * @param promotionId
     *            Long.
     * @return
     *//*
    @RequestMapping(value = "/toCreateRuleB2C")
    public String toCreateRuleB2C(Model model, Long couponId,Integer coupontype) {
        LOGGER.info("to toCreateRuleB2C Page:couponId=" + couponId);
        String errorMsg = "";
        Coupon coupon = null;
        if (couponId == null) {
            errorMsg = "优惠券id丢失！";
        } else {
            // 查询优惠券信息
            coupon = RemoteServiceSingleton.getInstance().getCouponService().getCouponPoById(couponId);
            errorMsg = this.checkCouponEndTime(coupon);

        }
        model.addAttribute("coupon", coupon);
        model.addAttribute("errorMsg", errorMsg);
        model.addAttribute("coupontype", coupontype);
        return "/promotions/coupon/couponRuleB2CEdit";
    }

    *//**
     * 新增优惠券规则
     * 
     * @param request
     * @return
     *//*
    @RequestMapping(value = "/createRule")
    @ResponseBody
    public String createRule(HttpServletRequest request, CouponRule couponRule) {
        LOGGER.info("CreateRule :couponId=" + couponRule.getCouponid());
        List<CouponRuleCondent> list = new ArrayList<CouponRuleCondent>(); 
        String returnFlag = SUC_FLAG;
        try {
        	String[] brandIds = (String[])request.getParameterValues("brandIds");
    		String[] catePubIds = (String[])request.getParameterValues("catePubIds");
    		String[] productIds = (String[])request.getParameterValues("productIds");
            // 校验页面元素
            returnFlag = checkCreateRuleInfo(couponRule);
            if (SUC_FLAG.equals(returnFlag)) {
                // 设置条件名称
                if (couponRule.getMainruletype() == 1) {
                    couponRule.setMainrulename("全场");
                }else if(Integer.parseInt(request.getParameter("mainruletype")) == 2){
                	for(int i = 0;i<catePubIds.length;i++){
                		CouponRuleCondent couponRuleCondent = new CouponRuleCondent();
                		couponRuleCondent.setCouponrulecontent(catePubIds[i]);
                		list.add(couponRuleCondent);
                	}
                	couponRule.setMainrulename("品类");
                }else if(Integer.parseInt(request.getParameter("mainruletype")) == 3){
                	for(int i = 0;i<brandIds.length;i++){
                		
                		List<SubBrand> tempSubBrands = RemoteServiceSingleton.getInstance().getMyBrandService().
    							getSubBrandListByPid(brandIds[i]);
    					for(SubBrand subBrand : tempSubBrands){//添加子品牌
    						CouponRuleCondent couponRuleCondent1 = new CouponRuleCondent();
    						couponRuleCondent1.setCouponrulecontent(subBrand.getId());
    						list.add(couponRuleCondent1);
    					}
    					//添加主品牌
                		CouponRuleCondent couponRuleCondent = new CouponRuleCondent();
                		//couponRuleCondentB2C.setRuleid(couponRule.getCouponruleid());
                		couponRuleCondent.setCouponrulecontent(brandIds[i]);
                		list.add(couponRuleCondent);
                	}
                	couponRule.setMainrulename("品牌");
                }else if(Integer.parseInt(request.getParameter("mainruletype")) == 4){
                	for(int i = 0;i<productIds.length;i++){
                		CouponRuleCondent couponRuleCondent = new CouponRuleCondent();
                		couponRuleCondent.setCouponrulecontent(productIds[i]);
                		list.add(couponRuleCondent);
                	}
                	couponRule.setMainrulename("单品");
                }
                RemoteServiceSingleton.getInstance().getCouponRuleService().insertCouponRuleCondent(couponRule,list);
                LOGGER.info("用户ID:" + getCurrentUser().getId()+", 创建了优惠券id="+couponRule.getCouponid()+" 的使用规则");
            }
        } catch (Exception e) {
            LOGGER.error("创建优惠券使用规则异常:" + e.getMessage(), e);
            LOGGER.error("优惠券id:" + couponRule.getCouponid());
            LOGGER.info("用户:" + getCurrentUser().getUsername());
            LOGGER.info("用户ID:" + getCurrentUser().getId());
            returnFlag = FAIL_FLAG;
        }
        return returnFlag;
    }
    
    *//**
     * 新增优惠券规则
     * 
     * @param request
     * @return
     *//*
    @RequestMapping(value = "/createRuleB2C")
    @ResponseBody
    public String createRuleB2C(HttpServletRequest request, CouponRule couponRule) {
        LOGGER.info("createRuleB2C :couponId=" + couponRule.getCouponid());
        String[] brandIds = (String[])request.getParameterValues("brandIds");
		String[] catePubIds = (String[])request.getParameterValues("catePubIds");
		String[] productIds = (String[])request.getParameterValues("productIds");
        String returnFlag = SUC_FLAG;
        List<CouponRuleCondentB2C> list = new ArrayList<CouponRuleCondentB2C>();
        try {
            // 校验页面元素
            returnFlag = checkCreateRuleInfo(couponRule);
            if (SUC_FLAG.equals(returnFlag)) {
                // 设置条件名称
            	couponRule.setMainruletype(Integer.parseInt(request.getParameter("ruleCondition")));
                if (Integer.parseInt(request.getParameter("ruleCondition")) == 0) {
                    couponRule.setMainrulename("全场");
                }else if(Integer.parseInt(request.getParameter("ruleCondition")) == 1){
                	for(int i = 0;i<catePubIds.length;i++){
                		CouponRuleCondentB2C couponRuleCondentB2C = new CouponRuleCondentB2C();
                		//couponRuleCondentB2C.setRuleid(couponRule.getCouponruleid());
                		couponRuleCondentB2C.setCouponrulecontent(catePubIds[i]);
                		list.add(couponRuleCondentB2C);
                	}
                	couponRule.setMainrulename("品类");
                }else if(Integer.parseInt(request.getParameter("ruleCondition")) == 2){
                	for(int i = 0;i<brandIds.length;i++){
                		LOGGER.info("main brand : "+brandIds[i]);
                		List<SubBrand> tempSubBrands = RemoteServiceSingleton.getInstance().getMyBrandService().
    							getSubBrandListByPid(brandIds[i]);
    					for(SubBrand subBrand : tempSubBrands){//添加子品牌
    						CouponRuleCondentB2C couponRuleCondentB2C1 = new CouponRuleCondentB2C();
    						LOGGER.info("sub brand : "+subBrand.getId());
    						couponRuleCondentB2C1.setCouponrulecontent(subBrand.getId());
    						list.add(couponRuleCondentB2C1);
    					}
    					//添加主品牌
                		CouponRuleCondentB2C couponRuleCondentB2C = new CouponRuleCondentB2C();
                		//couponRuleCondentB2C.setRuleid(couponRule.getCouponruleid());
                		couponRuleCondentB2C.setCouponrulecontent(brandIds[i]);
                		list.add(couponRuleCondentB2C);
                	}
                	couponRule.setMainrulename("品牌");
                }else if(Integer.parseInt(request.getParameter("ruleCondition")) == 4){
                	for(int i = 0;i<productIds.length;i++){
                		CouponRuleCondentB2C couponRuleCondentB2C = new CouponRuleCondentB2C();
                		//couponRuleCondentB2C.setRuleid(couponRule.getCouponruleid());
                		couponRuleCondentB2C.setCouponrulecontent(productIds[i]);
                		list.add(couponRuleCondentB2C);
                	}
                	couponRule.setMainrulename("单品");
                }
                couponRule.setIsb2c(1);
                
                
                
                RemoteServiceSingleton.getInstance().getCouponRuleService().insertCouponRuleCondentB2C(couponRule,list);
                LOGGER.info("用户ID:" + getCurrentUser().getId()+", 创建了优惠券id="+couponRule.getCouponid()+" 的使用规则");
            }
        } catch (Exception e) {
            LOGGER.error("创建优惠券使用规则异常:" + e.getMessage(), e);
            LOGGER.error("优惠券id:" + couponRule.getCouponid());
            LOGGER.info("用户:" + getCurrentUser().getUsername());
            LOGGER.info("用户ID:" + getCurrentUser().getId());
            returnFlag = FAIL_FLAG;
        }
        return returnFlag;
    }

    *//**
     * 停用、启用规则
     * 
     * @param request
     * @return
     *//*
    @RequestMapping(value = "/updateStatus")
    @ResponseBody
    public String updateStatus(HttpServletRequest request, CouponRule couponRule) {
        String returnFlag = SUC_FLAG;
        try {
            // 校验页面元素
            returnFlag = checkUpdateRuleInfo(couponRule);
            if (SUC_FLAG.equals(returnFlag)) {
                RemoteServiceSingleton.getInstance().getCouponRuleService().updateCouponRule(couponRule);
                String title="";
                if(couponRule.getStatus()==0){
                    title="停用";
                }else if(couponRule.getStatus()==1){
                    title="启用";
                }
                LOGGER.info("用户ID:" + getCurrentUser().getId()+","+title+"了使用规则，使用规则id="+couponRule.getCouponruleid());
            }
        } catch (Exception e) {
            LOGGER.error("修改优惠券规则状态异常:" + e.getMessage(), e);
            LOGGER.error("优惠券id:" + couponRule.getCouponid());
            LOGGER.error("优惠券规则id:" + couponRule.getCouponruleid());
            LOGGER.info("用户:" + getCurrentUser().getUsername());
            LOGGER.info("用户ID:" + getCurrentUser().getId());
            returnFlag = FAIL_FLAG;
        }
        return returnFlag;
    }

    *//**
     * 校验页面填写元素是否符合规范
     * 
     * @param couponRule
     * @return
     *//*
    private String checkCreateRuleInfo(CouponRule couponRule) {
        String msg = SUC_FLAG;

        // 校验名称
        if (StringUtils.isNotBlank(couponRule.getCouponrulename())) {
            if (couponRule.getCouponrulename().length() > 100) {
                return "优惠券规则名称长度不能大于100！";
            }
        } else {
            return "优惠券规则名称不能为空！";
        }
        // 校验金额
        if (couponRule.getMeetpiece() == null) {
            return "消费满足金额不能为空！";
        }
        if (couponRule.getMeetpiece() != null && couponRule.getMeetpiece().toString().length() > 18) {
            return "消费满足金额不能大于18位！";
        }
        if(couponRule.getCoupontype() != 2&&couponRule.getMeetpiece().compareTo(couponRule.getCouponacount())==-1){
            return "消费满足金额应不能小于优惠券金额！";
        }
        // 校验优惠券是否过期
        Coupon coupon = RemoteServiceSingleton.getInstance().getCouponService()
                .getCouponPoById(couponRule.getCouponid());
        String checkMsg = this.checkCouponEndTime(coupon);
        if (StringUtils.isNotBlank(checkMsg)) {
            return checkMsg;
        }

        return msg;
    }

    *//**
     * 校验优惠券结束时间
     * 
     * @param coupon
     * @return
     *//*
    private String checkCouponEndTime(Coupon coupon) {
        String errorMsg = "";
        if (coupon != null) {
            if (coupon.getEndtime() != null && new Date().compareTo(coupon.getEndtime()) == -1) {
                // 可以创建规则
            } else {
                errorMsg = "优惠券已过期,";
            }
        } else {
            errorMsg = "优惠券信息异常:NULL,";
        }
        return errorMsg;
    }

    *//**
     * 修改规则状态前校验
     * 
     * @param couponRule
     * @return
     *//*
    private String checkUpdateRuleInfo(CouponRule couponRule) {
        String msg = SUC_FLAG;
        String title = "";
        Coupon coupon = RemoteServiceSingleton.getInstance().getCouponService()
                .getCouponPoById(couponRule.getCouponid());
        if (coupon == null) {
            msg = "优惠券为null";
        } else {
            if (couponRule.getStatus() == 0) {
                title = "停用";
                // 校验:若券已经过期，可以随便停用，否则校验绑定活动与是否发放
                String checkMsg = this.checkCouponEndTime(coupon);
                if (StringUtils.isBlank(checkMsg)){//没有过期
                    // 停用规则需校验次规则是否已被活动绑定
                    boolean isBinding = false;// 没有被绑定
                    // 调用活动服务判断
                    isBinding = RemoteServiceSingleton.getInstance().getActivityBundlingCouponApi()
                            .checkActivityBundlingCoupon(couponRule.getCouponruleid());
                    if (isBinding) {
                        msg = "规则已被活动绑定, 不允许" + title;
                    } else {
                        // 判断券码表是否有数据
                        List<CouponStock> list = RemoteServiceSingleton.getInstance().getCouponRuleService()
                                .getCouponStockByruleID(couponRule.getCouponruleid());
                        if (list != null && list.size() > 0) {
                            msg = "此券已发放到用户, 不允许" + title;
                        }
                    }
                }
            } else if (couponRule.getStatus() == 1) {
                title = "启用";
                // 启用规则需要校验，券时间是否有效

                String checkMsg = this.checkCouponEndTime(coupon);
                if (StringUtils.isNotBlank(checkMsg)) {
                    msg = checkMsg + " 不允许" + title;
                }
            } else {
                msg = "规则状态异常，请稍后重试！";
            }
        }

        return msg;
    }
    *//**
     * 根据零售商id来查询是否是有效的零售商
     * @param request
     * @return 如果返回字符串为空串，则表示不是正常商户，否则，为正常商户，
     *//*
    @RequestMapping("/findUserByRetailerId")
    @ResponseBody
    public String findUserByName(HttpServletRequest request){
    	Long retailerId = Long.parseLong(request.getParameter("retailerId"));
//    	Retailer retailer = RemoteServiceSingleton.getInstance().getRetailerManagerService().findByRetailerId(retailerId);
    	String json = "";
    	if(retailer.getStatus() == 1){
    		json = JSONArray.fromObject(retailer).toString();
    	}
//    	json = JSONArray.fromObject(retailer).toString();
    	return json;
    }
    
    *//**
     * 根据零售商id来查询是否是有效的零售商
     * @param request
     * @return 如果返回字符串为空串，则表示不是正常商户，否则，为正常商户，
     *//*
    @RequestMapping("/findUserByUserId")
    @ResponseBody
    public String findUserByUserId(HttpServletRequest request){
    	String mobile = request.getParameter("userId");
    	User user = new User();
    	user.setMobile(mobile);
    	//user.setUserName(userName);
    	user = RemoteServiceSingleton.getInstance().getUserService().findUser(user);
    	LOGGER.info("获取B2C用户信息成功");
    	String json = "";
    	if(null == user){
    		LOGGER.info("手机号  "+mobile +"对应的用户 不存在");
    	}else if(null != user && user.getStatus() == 1){
    		json = JSONArray.fromObject(user).toString();
    	}else{
    		LOGGER.info("获取的B2C用户禁用");
    	}
    	return json;
    }
    
    @RequestMapping("/giveCouponToUser")
    @ResponseBody
    public String giveCouponToUser(HttpServletRequest request){
    	if(request.getParameter("userId") == null || request.getParameter("userId") == ""){
    		LOGGER.info("userId:"+request.getParameter("userId")+"  userId为空");
    		return FAIL_FLAG;
    	}else{
    		Integer b2cType = Integer.parseInt(request.getParameter("b2cType"));
    		Long userId = Long.parseLong(request.getParameter("userId"));
	    	Long couponRuleId = Long.parseLong(request.getParameter("couponruleid"));
	    	int type = Integer.parseInt(request.getParameter("type"));
	    	String desc = request.getParameter("desc");
    		//b2cType   0：B2B    1：B2C
	    	CouponRuleApi couponRuleApi = RemoteServiceSingleton.getInstance().getCouponRuleService();
	    	couponRuleApi.giveUsersCoupon(userId, couponRuleId, desc,type,b2cType);
    		
	    	return SUC_FLAG;
    	}
    }
    *//**
     * 跳转到向指定用户发券页面
     * @return
     *//*
    @RequestMapping("/toShowSendPage")
    public String toShowSendPage(HttpServletRequest request){
    	String couponId = request.getParameter("couponId");
    	Integer b2cType = Integer.parseInt(request.getParameter("b2cType"));
    	request.setAttribute("couponId", couponId);
    	if(b2cType == 0){
    		return "/promotions/coupon/sendCouponPage";
    	}else{
    		return "/promotions/coupon/sendB2CCouponPage";
    	}
    }
    
}
*/