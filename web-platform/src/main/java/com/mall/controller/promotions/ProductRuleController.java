/*package com.mall.controller.promotions;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.category.po.Brand;
import com.mall.category.po.SubBrand;
import com.mall.category.po.TdCatePub;
import com.mall.dealer.product.dto.B2cPromotionSkuDto;
import com.mall.dealer.product.dto.DealerProductSelectConDTO;
import com.mall.dealer.product.dto.DealerProductShowDTO;
import com.mall.dealer.product.dto.SkuCodeDTO;
import com.mall.mybatis.utility.PageBean;
import com.mall.platform.model.PlatformChannel;
import com.mall.promotion.api.activity.web.ActiveMasterApi;
import com.mall.promotion.api.activity.web.ActiveProductRuleApi;
import com.mall.promotion.po.activity.ActiveMaster;
import com.mall.promotion.po.activity.ActiveProductConditionDetail;
import com.mall.promotion.po.activity.ActiveProductRule;
import com.mall.promotion.po.activity.ActiveProductRuleDetail;
import com.mall.promotion.po.activity.CanalCondition;
import com.mall.promotion.po.coupon.Coupon;
import com.mall.promotion.po.coupon.CouponRule;
import com.mall.retailer.order.common.PKUtils;
import com.mall.stock.dto.StockWofeDTO;
import com.mall.dto.GiftDto;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.CollectionUtils;
import com.mall.utils.Constants;
import com.mall.utils.DateTool;

@Controller
@RequestMapping(value = "/productrule")
public class ProductRuleController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductRuleController.class);
	
	private static final  int   LIST_SIZE_ZERO  = 0;

	private static final String SUC_FLAG = "success";
	private static final String FAIL_FLAG = "error";
	private static final String TIME_OUT = "timeout";
	
	

	
	*//**
	 * 此方法用于日期的转换.
	 * @param binder date
	 *//*
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	
	*//**
	 * 查询商品活动规则列表
	 * 
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 *//*
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, HttpServletResponse response, Long activeId, Long ruleId, Model model) {
		try {
			LOGGER.info("查询商品活动规则列表");
			ActiveMaster viewActiveMaster = null;
			ActiveMasterApi activeMasterApi = RemoteServiceSingleton.getInstance().getActiveMasterApi();
			if (activeId != null) {
				viewActiveMaster = activeMasterApi.viewActiveMaster(activeId);
			} else {
				viewActiveMaster = activeMasterApi.findActiveMasterByRuleId(ruleId);

			}
			if(viewActiveMaster.getStatus().equals(ActivityConstants.STOP_SUATUS) || 
                    viewActiveMaster.getExpiringTo().compareTo(new Date()) < 0)
			    model.addAttribute("activeMasterStatus", "(活动未启用)");
			else if (viewActiveMaster.getStatus().equals(ActivityConstants.START_SUATUS))
			    model.addAttribute("activeMasterStatus", "(活动已启用)");
			else 
			    throw new RuntimeException("活动不存在");
			
			model.addAttribute("bean", viewActiveMaster);
		} catch (Exception e) {
			LOGGER.info("查询活动信息错误：" + e.getMessage(), e);
		}
		return "promotions/activity/productruleList";

	}
	
	*//**
	 * 跳转到商品活动规则添加页面
	 * @param request
	 * @param response
	 * @param model
	 * @param activeId
	 * @return
	 *//*
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request,HttpServletResponse response,Model model,Long activeId){
		try {
			List<TdCatePub> topCategoryList = RemoteServiceSingleton.getInstance().getCategoryServiceRpc().getTopCategoryList();
			request.getSession().setAttribute("topCategoryList", topCategoryList);
			List<PlatformChannel> channelList=RemoteServiceSingleton.getInstance().getChannelManagerService().getAllChannel();
			model.addAttribute("activeId", activeId);
			model.addAttribute("channelList", channelList);
			
		} catch (Exception e) {
			LOGGER.info("查询顶级类目信息错误：" + e.getMessage(), e);
		}
		
		return "promotions/activity/productRuleEdit";
	}
	
	*//**
	 * 跳转到限时直降活动规则添加页面
	 * @param request
	 * @param response
	 * @param model
	 * @param activeId
	 * @return
	 *//*
	@RequestMapping(value = "/addPricedPromotion")
	public String addPricedPromotion(HttpServletRequest request,HttpServletResponse response,Model model,Long activeId){
		model.addAttribute("activeId", activeId);
		return "promotions/activity/pricedProductRuleEdit";
	}
	
	*//**
	 * 跳转到商品活动规则添加页面
	 * @param request
	 * @param response
	 * @param model
	 * @param activeId
	 * @return
	 *//*
	@RequestMapping(value = "/addB2C")
	public String addB2C(HttpServletRequest request,HttpServletResponse response,Model model,Long couponId,Integer coupontype){
		try {
			
			List<TdCatePub> topCategoryList = RemoteServiceSingleton.getInstance().getCategoryServiceRpc().getTopCategoryList();
			request.getSession().setAttribute("topCategoryList", topCategoryList);
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
		} catch (Exception e) {
			LOGGER.info("查询顶级类目信息错误：" + e.getMessage(), e);
		}
		
		return "promotions/coupon/couponRuleB2CEdit";
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
	 * 保存商品活动规则
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *//*
	@RequestMapping("save")
	@ResponseBody
	public String save(HttpServletRequest request,HttpServletResponse response,Model model,
			ActiveProductRule activeProductRule,ActiveProductConditionDetail activeProductConditionDetail,
			ActiveProductRuleDetail activeProductRuleDetail){
		    List<ActiveProductRuleDetail> detaillist=new ArrayList<ActiveProductRuleDetail>();      	
		try {
			
	
			Map parameterMap = request.getParameterMap();
			
		    String[] firstCondition = request.getParameterValues("firstCondition");
		    String [] secondCondition=request.getParameterValues("SecondCondition");
		    String [] thirdCondition=request.getParameterValues("thirdCondition");
		    String [] fourCondition=request.getParameterValues("fourCondition");
		    String [] giftNames = request.getParameterValues("giftName");
		    
		    
		    
			String giftType = request.getParameter("giftType");//赠品类型 1 优惠券
			String term2 = request.getParameter("term2");
			String[] brandIds = (String[])request.getParameterValues("brandIds");
			String[] catePubIds = (String[])request.getParameterValues("catePubIds");
			String[] productIds = (String[])request.getParameterValues("productIds");
			
			LOGGER.info("====brandIds====="+Arrays.toString(brandIds));
			LOGGER.info("======catePubIds==="+Arrays.toString(catePubIds));
			LOGGER.info("======prouductIds==="+Arrays.toString(productIds));
			
			activeProductRule.setId(PKUtils.getLongPrimaryKey());
			activeProductRule.setIsExclude(new Short("0"));
			activeProductRule.setAuditStatus(new Short("1"));//审核状态，已审核
			activeProductRule.setStatus(new Short("0"));//保存是默认停用状态
			//判断选择的是类目 、品牌还是商品  保存记录
			List<ActiveProductConditionDetail> list = new ArrayList<ActiveProductConditionDetail>();
			
			if(0 == activeProductRule.getRuleCondition()){//全场
				activeProductRule.setRuleType(0);
				activeProductRule.setRuleSort(9999);
			}else if(1 == activeProductRule.getRuleCondition()){//类目
				activeProductRule.setRuleType(0);
				for (String tempCatePubId : catePubIds) {
					ActiveProductConditionDetail tempActiveProductConditionDetail = new ActiveProductConditionDetail();
					tempActiveProductConditionDetail.setActiveId(activeProductRule.getActiveId());
					tempActiveProductConditionDetail.setRuleId(activeProductRule.getId());
					tempActiveProductConditionDetail.setConditionContent(tempCatePubId);
					tempActiveProductConditionDetail.setConditionType(activeProductRule.getRuleCondition());
					list.add(tempActiveProductConditionDetail);
				}
				
			}else if(2 == activeProductRule.getRuleCondition()){//品牌
				activeProductRule.setRuleType(0);
				for (String tempBrandId : brandIds) {
					//根据主品牌获取子品牌列表
					List<SubBrand> tempSubBrands = RemoteServiceSingleton.getInstance().getMyBrandService().
							getSubBrandListByPid(tempBrandId);
					if(null != tempSubBrands && LIST_SIZE_ZERO < tempSubBrands.size()){
						for (SubBrand subBrand : tempSubBrands) {
							ActiveProductConditionDetail tempActiveProductConditionDetail = new ActiveProductConditionDetail();
							tempActiveProductConditionDetail.setActiveId(activeProductRule.getActiveId());
							tempActiveProductConditionDetail.setRuleId(activeProductRule.getId());
							tempActiveProductConditionDetail.setConditionContent(subBrand.getId());
							tempActiveProductConditionDetail.setConditionType(activeProductRule.getRuleCondition());
							//添加子品牌
							list.add(tempActiveProductConditionDetail);
						}
					}
					
					ActiveProductConditionDetail tempActiveProductConditionDetail = new ActiveProductConditionDetail();
					tempActiveProductConditionDetail.setActiveId(activeProductRule.getActiveId());
					tempActiveProductConditionDetail.setRuleId(activeProductRule.getId());
					tempActiveProductConditionDetail.setConditionContent(tempBrandId);
					tempActiveProductConditionDetail.setConditionType(activeProductRule.getRuleCondition());
					//添加主品牌
					list.add(tempActiveProductConditionDetail);
				}
				
			}else if(3 == activeProductRule.getRuleCondition()){//商品
				activeProductRule.setRuleType(0);
				for (String tempProductId : productIds) {
					ActiveProductConditionDetail tempActiveProductConditionDetail = new ActiveProductConditionDetail();
					tempActiveProductConditionDetail.setActiveId(activeProductRule.getActiveId());
					tempActiveProductConditionDetail.setRuleId(activeProductRule.getId());
					tempActiveProductConditionDetail.setConditionContent(tempProductId);
					tempActiveProductConditionDetail.setConditionType(activeProductRule.getRuleCondition());
					list.add(tempActiveProductConditionDetail);
				}
			}
			
			
			//如果活动类型为满赠
			if(1 == activeProductRule.getRuleTerm()){//如果是满赠活动  1 为满赠
				if("1".equals(giftType)){//1 为赠券
					//保存券
					activeProductRuleDetail.setActiveId(activeProductRule.getActiveId());
					activeProductRuleDetail.setRuledetailId(PKUtils.getLongPrimaryKey());
					activeProductRuleDetail.setRuleId(activeProductRule.getId());//
					//赠的券规则id先写死
					activeProductRuleDetail.setTerm2(term2);
					activeProductRuleDetail.setTerm3(giftType);
					detaillist.add(activeProductRuleDetail);
				}
				
			}if(2==activeProductRule.getRuleTerm()){//如果是阶梯满赠活动
				if(3==activeProductRule.getGifttype()){//如果赠品为单品
					if(1==activeProductRule.getSendtype()){//如果按数量赠
				
						for (PromtiomConditionList promtiomConditionList : activeProductRule.getPromtiomConditionList()) {
							ActiveProductRuleDetail conditionList=new ActiveProductRuleDetail();
							conditionList.setTerm0(promtiomConditionList.getFirstCondition());
							conditionList.setTerm1(promtiomConditionList.getSecondCondition());
							conditionList.setTerm2(promtiomConditionList.getThirdCondition());
							conditionList.setGiftQty(Integer.parseInt(promtiomConditionList.getFourCondition()));
							detaillist.add(conditionList);
						}
						
					    for(int i=0;i<firstCondition.length;i++){
					    	ActiveProductRuleDetail conditionList=new ActiveProductRuleDetail();
							conditionList.setTerm0(firstCondition[i]);
							if(null == secondCondition[i] || "".equals(secondCondition[i])){
								conditionList.setTerm1(""+999999);
							}else{
								conditionList.setTerm1(secondCondition[i]);
							}
							conditionList.setTerm2(thirdCondition[i]);
							conditionList.setTerm3("3");
							conditionList.setGiftName(giftNames[i]);
							conditionList.setGiftQty(Integer.parseInt(fourCondition[i]));
							detaillist.add(conditionList);
					    	
					    }
					}if(2==activeProductRule.getSendtype()){//如果按金额赠
			
						for (PromtiomConditionList promtiomConditionList : activeProductRule.getPromtiomConditionList()) {
							ActiveProductRuleDetail conditionList=new ActiveProductRuleDetail();
							conditionList.setTerm0(promtiomConditionList.getFirstCondition());
							conditionList.setTerm1(promtiomConditionList.getSecondCondition());
							conditionList.setTerm2(promtiomConditionList.getThirdCondition());
							conditionList.setGiftQty(Integer.parseInt(promtiomConditionList.getFourCondition()));
							detaillist.add(conditionList);
						}
					    for(int i=0;i<firstCondition.length;i++){
					    	ActiveProductRuleDetail conditionList=new ActiveProductRuleDetail();
							conditionList.setTerm0(firstCondition[i]);
							if(""==secondCondition[i]||null==secondCondition[i]){
								conditionList.setTerm1("999999");
							}else{
								conditionList.setTerm1(secondCondition[i]);
							}
							
							conditionList.setTerm2(thirdCondition[i]);
							conditionList.setGiftQty(Integer.parseInt(fourCondition[i]));
							conditionList.setGiftName(giftNames[i]);
							detaillist.add(conditionList);
					    	
					    }
					}
					
					
				}
			}
			
			//保存商品活动规则 及赠品
			RemoteServiceSingleton.getInstance().getActiveProductRuleApi()
				.createProductRule(activeProductRule, list, activeProductRuleDetail, giftType);
			
		RemoteServiceSingleton.getInstance().getActiveProductRuleApi().createProductRule(activeProductRule, list, detaillist, giftType);
			
			return SUC_FLAG;
		} catch (Exception e) {
			LOGGER.error("保存商品活动规则异常：",e);
			return FAIL_FLAG;
		}

	}
	
	*//**
	 * 保存商品活动规则
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *//*
	@RequestMapping("/saveB2CRule")
	@ResponseBody
	public String saveB2CRule(HttpServletRequest request,HttpServletResponse response,Model model,
			ActiveProductRule activeProductRule,ActiveProductConditionDetail activeProductConditionDetail,
			ActiveProductRuleDetail activeProductRuleDetail){
		try {
			List<ActiveProductRuleDetail> detaillist=new ArrayList<ActiveProductRuleDetail>();
			activeProductRule.setIsB2c((short) 1);
			String[] skuIds = (String[])request.getParameterValues("skuIds");
			//阶梯满赠
			String term0 = request.getParameter("firstCondition");
			String term2 = request.getParameter("thirdCondition");
			String term_33 = request.getParameter("fourCondition");
			//满返券
			String fullAmount = request.getParameter("fullAmount");
			String couponType = request.getParameter("couponType");
			String coupons = request.getParameter("coupons");
			String isrepeate = request.getParameter("isrepeate");
			//满减
			String fullAmount1 = request.getParameter("fullAmount1");
			String cutAmount = request.getParameter("cutAmount");
			
			if(activeProductRule.getRuleTerm() == 13){
				activeProductRule.setGifttype(3);
				activeProductRuleDetail.setGiftQty(Integer.parseInt(term_33));
				activeProductRuleDetail.setTerm0(term0);
				activeProductRuleDetail.setTerm2(term2);
			}
			
			if(activeProductRule.getRuleTerm() == 14){
				activeProductRule.setIsrepeat(Integer.parseInt(isrepeate));
				activeProductRule.setGifttype(Integer.parseInt(couponType));
				activeProductRuleDetail.setTerm0(null);
				activeProductRuleDetail.setGiftQty(null);
				activeProductRuleDetail.setGiftName(null);
				activeProductRuleDetail.setTerm2(coupons);
				activeProductRuleDetail.setTerm1(fullAmount);
			}
			
			if(activeProductRule.getRuleTerm() == 15){
				activeProductRule.setIsrepeat(0);
				activeProductRuleDetail.setTerm0(null);
				activeProductRuleDetail.setTerm1(fullAmount1);
				activeProductRuleDetail.setTerm2(cutAmount);
				activeProductRuleDetail.setGiftQty(null);
				activeProductRuleDetail.setGiftName(null);
			}
			
			LOGGER.info("ruleCondition:"+activeProductRule.getRuleCondition().toString());
			LOGGER.info("======skuIds==="+Arrays.toString(skuIds));
			
			Long ruleId = PKUtils.getLongPrimaryKey();
			activeProductRule.setId(ruleId);
			activeProductRule.setIsExclude(new Short("0"));
			activeProductRule.setAuditStatus(new Short("1"));//审核状态，已审核
			activeProductRule.setStatus(new Short("0"));//保存是默认停用状态
			
			activeProductRuleDetail.setRuleId(ruleId);
			detaillist.add(activeProductRuleDetail);
			//判断选择的是类目 、品牌还是商品  保存记录
			List<ActiveProductConditionDetail> list = new ArrayList<ActiveProductConditionDetail>();
			
			if(4 == activeProductRule.getRuleCondition()){
				activeProductRule.setRuleType(0);
				for (String tempProductId : skuIds) {
					ActiveProductConditionDetail tempActiveProductConditionDetail = new ActiveProductConditionDetail();
					tempActiveProductConditionDetail.setActiveId(activeProductRule.getActiveId());
					tempActiveProductConditionDetail.setRuleId(activeProductRule.getId());
					tempActiveProductConditionDetail.setConditionContent(tempProductId);
					tempActiveProductConditionDetail.setRuleId(ruleId);
					tempActiveProductConditionDetail.setConditionType(activeProductRule.getRuleCondition());
					list.add(tempActiveProductConditionDetail);
				}
			}
			//保存商品活动规则 及赠品
			RemoteServiceSingleton.getInstance().getActiveProductRuleApi()
				.createProductRule(activeProductRule, list, detaillist, "");
			
			
			
			return SUC_FLAG;
		} catch (Exception e) {
			LOGGER.error("保存限时直降商品活动规则异常：",e.getMessage());
			return FAIL_FLAG;
		}

	}
	
	
	
	*//**
	 * 跳转到编辑商品活动规则页面
	 * @param model
	 * @param ruleId
	 * @return
	 *//*
	public String updateui(HttpServletRequest request,HttpServletResponse response,Model model,Long ruleId){
		//TODO 跳转到更新页面
		
		
		
		return "";
	}
	
	*//**
	 * 更新商品活动规则信息
	 * @param model
	 * @return
	 *//*
	public String update(HttpServletRequest request,HttpServletResponse response,Model model){
		//TODO 修改规则
		
		return "";
	}
	
	*//**
	 * 商品活动规则停用启用
	 * @param model
	 * @return
	 *//*
	public String changeStatus(HttpServletRequest request,HttpServletResponse response,Model model){
		//TODO 更新规则状态
		
		
		return "";
	}
	
	
	@RequestMapping(value = "/findProductRuleByCondition")
	public String findRuleByCondition(HttpServletRequest request, HttpServletResponse response, Integer page, ActiveProductRule activeProductRule) {
		try {
			LOGGER.info("分页查询商品活动规则列表");
			PageBean<ActiveProductRule> pageBean = new PageBean<ActiveProductRule>();
			if (page != null && page != 0) {
				pageBean.setPage(page);
			} else {
				pageBean.setPage(1);
			}
			pageBean.setPageSize(Constants.PAGESIZE);
			pageBean.setParameter(activeProductRule);
			// 调用服务代码
			ActiveProductRuleApi activeProductRuleApi = RemoteServiceSingleton.getInstance().getActiveProductRuleApi();
			pageBean = activeProductRuleApi.findActiveProductRuleByCondition(pageBean);

			request.getSession().setAttribute("pb", pageBean);
			request.getRequestDispatcher("/WEB-INF/views/promotions/activity/modelpage/productrules_model.jsp").forward(request, response);
		} catch (Exception e) {
			LOGGER.error("查询活动列表异常：" + e.getMessage(), e);
		}
		return null;
	}
	
	*//**
	 *查询品类 
	 * @param ruleId
	 * @return
	 *//*
	@RequestMapping(value = "/ajaxCategory")
	@ResponseBody
	public String ajaxCategory(String ruleId) {
		LOGGER.info("ajax获取品类,参数ruleId:"+ruleId);
		
		if(StringUtils.isEmpty(ruleId)){
			
		}else{
			Long tempRuleId = Long.parseLong(ruleId);
			try {
				List<String> list = RemoteServiceSingleton.getInstance().getActiveProductConditionDetailApi().findActiveProductRuleDetailIdsByID(tempRuleId);
				
				if(list != null && list.size()>LIST_SIZE_ZERO){
					//批量获取类目
					String[] strings = new String[list.size()];
					String[] tempCatePubIds =  list.toArray(strings);
					
					LOGGER.info("类目id数组为："+Arrays.toString(tempCatePubIds));
					
					Map<String,String> map = RemoteServiceSingleton.getInstance().getCategoryServiceRpc().getFullCatePubPath(tempCatePubIds);
					
					List<String> catePubNameList = CollectionUtils.mapTransitionList(map);
					
					return JSONArray.fromObject(catePubNameList).toString();
				}
				
			} catch (Exception e) {
				LOGGER.error("根据规则id获取规则范围异常：",e);
			}
		}
		
		return null;
	}
	
	*//**
	 * 查询品牌
	 * @param ruleId
	 * @return
	 *//*
	@RequestMapping(value = "/ajaxBrand")
	@ResponseBody
	public String ajaxBrand(String ruleId){
		LOGGER.info("ajax获取品牌,参数ruleId:"+ruleId);
		try {
			if(StringUtils.isEmpty(ruleId)){
				
			}else{
				Long tempRuleId = Long.parseLong(ruleId);
				List<String> list = RemoteServiceSingleton.getInstance().getActiveProductConditionDetailApi().findActiveProductRuleDetailIdsByID(tempRuleId);
				if(list != null && list.size()>LIST_SIZE_ZERO){
					//批量获取品牌
					String[] strings = new String[list.size()];
					String[] tempBrandIds =  list.toArray(strings);
					
					LOGGER.info("品牌id数组为："+Arrays.toString(tempBrandIds));
					
					List<Brand> brandList = RemoteServiceSingleton.getInstance().getBrandServiceRpc().getBrands(tempBrandIds);
					
					return JSONArray.fromObject(brandList).toString();
				}
			}
		} catch (Exception e) {
			LOGGER.error("查询品牌异常：",e);
		}
		return null;
	}
	
	*//**
	 * 查询商品
	 * @param ruleId
	 * @return
	 *//*
	@RequestMapping(value = "/ajaxProduct")
	@ResponseBody
	public String ajaxProduct(String ruleId){
		LOGGER.info("ajax获取品牌,参数ruleId:"+ruleId);
		try {
			if(StringUtils.isEmpty(ruleId)){
				
			}else{
				Long tempRuleId = Long.parseLong(ruleId);
				List<String> list = RemoteServiceSingleton.getInstance().getActiveProductConditionDetailApi().findActiveProductRuleDetailIdsByID(tempRuleId);
				if(list != null && list.size()>LIST_SIZE_ZERO){
					//批量获取商品
					String[] strings = new String[list.size()];
					String[] tempGoodIds =  list.toArray(strings);
					
					LOGGER.info("商品id数组为："+Arrays.toString(tempGoodIds));
					List<DealerProductShowDTO> goodList = new ArrayList<DealerProductShowDTO>();
					DealerProductSelectConDTO vo = new DealerProductSelectConDTO();
					vo.setAuditStatus((short)1);
					for (String pid : tempGoodIds) {
						PageBean pageBean = new PageBean();
						vo.setProductId(Long.parseLong(pid));
						pageBean.setParameter(vo);
						pageBean = RemoteServiceSingleton.getInstance().getDealerProductService().findProductsByConditions(pageBean);
						goodList.addAll(pageBean.getResult());
					}
					return JSONArray.fromObject(goodList).toString();
				}
			}
		} catch (Exception e) {
			LOGGER.error("查询商品异常：",e);
		}
		return null;
	}
	
	*//**
	 * 查询商品
	 * @param ruleId
	 * @return
	 *//*
	@RequestMapping(value = "/ajaxSku")
	@ResponseBody
	public String ajaxSku(String ruleId){
		LOGGER.info("ajax获取单品,参数ruleId:"+ruleId);
		try {
			if(StringUtils.isEmpty(ruleId)){
				
			}else{
				Long tempRuleId = Long.parseLong(ruleId);
				List<String> list = RemoteServiceSingleton.getInstance().getActiveProductConditionDetailApi().findActiveProductRuleDetailIdsByID(tempRuleId);
				if(list != null && list.size()>LIST_SIZE_ZERO){
					//批量获取商品
					String[] strings = new String[list.size()];
					String[] tempSkuIds =  list.toArray(strings);
					
					List<Long> skus = new ArrayList<Long>();
					for(String str : tempSkuIds){
						LOGGER.info(str);
						skus.add(Long.parseLong(str));
					}
					List<B2cPromotionSkuDto> findSkuDtosBySkuIds = RemoteServiceSingleton.getInstance().getCustomerPromotionService().findSkuDtosBySkuIds(skus);
					
					return JSONArray.fromObject(findSkuDtosBySkuIds).toString();
				}
			}
		} catch (Exception e) {
			LOGGER.error("查询商品异常：",e);
		}
		return null;
	}
	
	*//**
	 * 查询规则条件速度
	 * @param ruleId
	 * @return
	 *//*
	@RequestMapping(value ="/ajaxRuleCondition")
	@ResponseBody
	public String ajaxRuleCondition(String ruleId){
		LOGGER.info("ajax获取规则条件,参数ruleId:"+ruleId);
		try {
			if(StringUtils.isEmpty(ruleId)){
				
			}else{
				Long tempRuleId = Long.parseLong(ruleId);
				StringBuffer content = new StringBuffer();
				List<ActiveProductRuleDetail> list = RemoteServiceSingleton.getInstance().getActiveProductRuleDetailApi().findProductRuleByRuleId(tempRuleId);
				if(list != null && list.size()>LIST_SIZE_ZERO){
					List<String> result = new ArrayList<String>();
					if(list.size()==1){
						//普通满减 or 普通满赠
						
						for (ActiveProductRuleDetail activeProductRuleDetail : list) {
							if("1".equals(activeProductRuleDetail.getTerm3())){  //1 普通满赠，赠品类型为优惠券
								CouponRule couponRule = RemoteServiceSingleton.getInstance().getCouponRuleService().getCouponRulePoById(Long.parseLong(activeProductRuleDetail.getTerm2()));
								
								if(couponRule != null){
									content.append("满").append(activeProductRuleDetail.getTerm1()).append("赠").append("规则Id为").append("<span style=\"color:blue;\">").append(couponRule.getCouponruleid()).append("</span>")
									.append("券规则名称为").append("<span style=\"color:red;\">").append(couponRule.getCouponrulename()).append("</span>").append("的优惠券");
								}else{
									content.append("没有查询到规则ID为:").append(activeProductRuleDetail.getTerm2()).append("的优惠券");
								}
							}
							String str = activeProductRuleDetail.getTerm3();
							if(null == activeProductRuleDetail.getTerm3()){  //1 普通满赠，赠品类型为优惠券
								content.append("每单限时限购").append(activeProductRuleDetail.getTerm1()).append("件     每件降").append(activeProductRuleDetail.getTerm2()).append("元").append("<span style=\"color:blue;\">").append("</span>");
							}
							result.add(content.toString());
						}
					}
					if(list.size()>1){
						//默认为阶梯满赠
						for (ActiveProductRuleDetail activeProductRuleDetail : list) {
							if("3".equals(activeProductRuleDetail.getTerm3())){ 
								content.append("从").append(activeProductRuleDetail.getTerm0()).append("到").append(activeProductRuleDetail.getTerm1()).append("送").append("skuid="+activeProductRuleDetail.getTerm2()+"的赠品").append(",");
							}

						}
						result.add(content.toString());
					}
					return JSONArray.fromObject(result).toString();
				}
			}
		} catch (Exception e) {
			LOGGER.error("查询规则条件异常：",e);
		}
		return null;
	}
	
	
	
	*//**
	 * 查询规则条件
	 * @param ruleId
	 * @return
	 *//*
	@RequestMapping(value ="/ajaxModifyRuleStatus")
	@ResponseBody
	public String ajaxModifyRuleStatus(String ruleId,String status){
		LOGGER.info("ajax修改規則狀態,参数ruleId:"+ruleId+" status:"+status);
		
		ActiveProductRule activeProductRule = new ActiveProductRule();
		activeProductRule.setId(Long.parseLong(ruleId));
		activeProductRule.setStatus(Short.parseShort(status));
		
		ActiveProductRule dbActiveProductRule = RemoteServiceSingleton.getInstance().
				getActiveProductRuleApi().findProductRuleByRuleId(Long.parseLong(ruleId));
		
		int result = DateTool.compareTime(new Date(), dbActiveProductRule.getEnddate());
		if(result == 1){
			LOGGER.info("该规则时间已经结束");
			return TIME_OUT;
		}
		
		try {
			RemoteServiceSingleton.getInstance().getActiveProductRuleApi().updateProductRule(activeProductRule);
			return SUC_FLAG;
		} catch (Exception e) {
			LOGGER.error("修改規則狀態異常：",e);
			return FAIL_FLAG;
		}
	}
	
	
	*//**
	 * 查询类目、商品、品牌信息
	 * @param request
	 * @return
	 *//*
	@RequestMapping(value = "/findByConditions")
	@ResponseBody
	public String findByConditions(HttpServletRequest request){
		String oType = request.getParameter("oType");
		String flag = request.getParameter("flag");
		String json = "";
		if("brands".equals(oType)){//品牌
			try {
				List<Brand> allBrand = RemoteServiceSingleton.getInstance().getBrandServiceRpc().findAllBrand();
				json = JSONArray.fromObject(allBrand).toString();
				return json;
			} catch (Exception e) {
				LOGGER.error("查询品牌失败"+e.getMessage());
				return null;
			}
		}else if("goods".equals(oType)){
			
			DealerProductSelectConDTO vo = new DealerProductSelectConDTO();
			PageBean<DealerProductShowDTO> pageBean = new PageBean<DealerProductShowDTO>();
			vo.setAuditStatus((short)1);
			if("1".equals(flag)){
				String productName = request.getParameter("productName");
				vo.setProductName(productName);
			}
			pageBean.setParameter(vo);
			pageBean.setPageSize(10);
			pageBean = RemoteServiceSingleton.getInstance().getDealerProductService().findProductsByConditions(pageBean);
			List<DealerProductShowDTO> list = pageBean.getResult();
			
			json = JSONArray.fromObject(list).toString();
			return json;
		}else if("sort".equals(oType)){
			String parentCid = request.getParameter("parentCid");
			List<TdCatePub> childrenCategoryList;
			try {
				childrenCategoryList = RemoteServiceSingleton.getInstance().getCategoryServiceRpc().getChildrenCategoryList(parentCid);
				LOGGER.info("获取到的类目个数"+childrenCategoryList.size());
				json = JSONArray.fromObject(childrenCategoryList).toString();
				return json;
			} catch (Exception e) {
				LOGGER.error("查询子类目失败"+e.getMessage());
				return null;
			}
		}else if("skus".equals(oType)){
//			PageBean<SkuCodeDTO> pageBean = new PageBean<SkuCodeDTO>();
//			pageBean.setPage(1);
//			pageBean.setPageSize(10);
//			SkuCodeDTO skuCodeDTO1 = new SkuCodeDTO();
//			skuCodeDTO1.setB2cTate((short) 1);
//			pageBean.setParameter(skuCodeDTO1);
//			pageBean = RemoteServiceSingleton.getInstance().getCustomerPromotionService().findPageSkuDto(pageBean);
//			if(null != pageBean.getResult()){
//				LOGGER.info("获取到的单品个数"+pageBean.getResult().size());
//				LOGGER.info("unitPrice:"+pageBean.getResult().get(0).getUnitPrice().doubleValue());
//				for(SkuCodeDTO skuCodeDTO : pageBean.getResult()){
//					skuCodeDTO.setSkuCode(""+skuCodeDTO.getSkuId());
//				}
//				json = JSONArray.fromObject(pageBean).toString();
//				return json;
//			}else{
//				LOGGER.info("获取到的单品个数为0");
//				return json;
//			}
			return null;
		}else {
			return null;
		}
	}
	*//**
	 * 查询类目、商品、品牌信息
	 * @param request
	 * @return
	 *//*
	@RequestMapping(value = "/findb2bByConditions")
	@ResponseBody
	public String findb2bByConditions(HttpServletRequest request){
		String oType = request.getParameter("oType");
		String flag = request.getParameter("flag");
		String json = "";
		if("brands".equals(oType)){//品牌
			try {
				List<Brand> allBrand = RemoteServiceSingleton.getInstance().getBrandServiceRpc().findAllBrand();
				json = JSONArray.fromObject(allBrand).toString();
				return json;
			} catch (Exception e) {
				LOGGER.error("查询品牌失败"+e.getMessage());
				return null;
			}
		}else if("goods".equals(oType)){
			
			DealerProductSelectConDTO vo = new DealerProductSelectConDTO();
			PageBean<DealerProductShowDTO> pageBean = new PageBean<DealerProductShowDTO>();
			vo.setAuditStatus((short)1);
			if("1".equals(flag)){
				String productName = request.getParameter("productName");
				vo.setProductName(productName);
			}
			pageBean.setParameter(vo);
			pageBean.setPageSize(10);
			pageBean = RemoteServiceSingleton.getInstance().getDealerProductService().findProductsByConditions(pageBean);
			List<DealerProductShowDTO> list = pageBean.getResult();
			
			json = JSONArray.fromObject(list).toString();
			return json;
		}else if("sort".equals(oType)){
			String parentCid = request.getParameter("parentCid");
			List<TdCatePub> childrenCategoryList;
			try {
				childrenCategoryList = RemoteServiceSingleton.getInstance().getCategoryServiceRpc().getChildrenCategoryList(parentCid);
				LOGGER.info("获取到的类目个数"+childrenCategoryList.size());
				json = JSONArray.fromObject(childrenCategoryList).toString();
				return json;
			} catch (Exception e) {
				LOGGER.error("查询子类目失败"+e.getMessage());
				return null;
			}
		}else if("skus".equals(oType)){
			PageBean<SkuCodeDTO> pageBean = new PageBean<SkuCodeDTO>();
			if(null == request.getParameter("page")){
				pageBean.setPage(1);
			}else{
				Integer page = Integer.parseInt(request.getParameter("page"));
				if(null != page && page != 1){
					pageBean.setPage(page);
				}else{
					pageBean.setPage(1);
				}
			}
			
			pageBean.setPageSize(10);
			pageBean = RemoteServiceSingleton.getInstance().getCustomerPromotionService().findPageSkuDto(pageBean);
			if(null != pageBean.getResult()){
				LOGGER.info("获取到的单品个数"+pageBean.getResult().size());
				LOGGER.info("unitPrice:"+pageBean.getResult().get(0).getUnitPrice().doubleValue());
				for(SkuCodeDTO skuCodeDTO : pageBean.getResult()){
					skuCodeDTO.setSkuCode(""+skuCodeDTO.getSkuId());
				}
				json = JSONArray.fromObject(pageBean).toString();
				return json;
			}else{
				LOGGER.info("获取到的单品个数为0");
				return json;
			}
		}else {
			return null;
		}
	}
	*//**
	 * B2B创建优惠券规则
	 * @param request
	 * @return
	 *//*
	@RequestMapping(value = "/findb2bByConditions_new")
	@ResponseBody
	public String findb2bByConditions_new(HttpServletRequest request){
		String oType = request.getParameter("oType");
		String flag = request.getParameter("flag");
		String json = "";
		if("brands".equals(oType)){//品牌
			try {
				List<Brand> allBrand = RemoteServiceSingleton.getInstance().getBrandServiceRpc().findAllBrand();
				json = JSONArray.fromObject(allBrand).toString();
				return json;
			} catch (Exception e) {
				LOGGER.error("查询品牌失败"+e.getMessage());
				return null;
			}
		}else if("goods".equals(oType)){
			
			DealerProductSelectConDTO vo = new DealerProductSelectConDTO();
			PageBean<DealerProductShowDTO> pageBean = new PageBean<DealerProductShowDTO>();
			vo.setAuditStatus((short)1);
			if("1".equals(flag)){
				String productName = request.getParameter("productName");
				vo.setProductName(productName);
			}
			pageBean.setParameter(vo);
			pageBean.setPageSize(10);
			pageBean = RemoteServiceSingleton.getInstance().getDealerProductService().findProductsByConditions(pageBean);
			List<DealerProductShowDTO> list = pageBean.getResult();
			
			json = JSONArray.fromObject(list).toString();
			return json;
		}else if("sort".equals(oType)){
			String parentCid = request.getParameter("parentCid");
			List<TdCatePub> childrenCategoryList;
			try {
				childrenCategoryList = RemoteServiceSingleton.getInstance().getCategoryServiceRpc().getChildrenCategoryList(parentCid);
				LOGGER.info("获取到的类目个数"+childrenCategoryList.size());
				json = JSONArray.fromObject(childrenCategoryList).toString();
				return json;
			} catch (Exception e) {
				LOGGER.error("查询子类目失败"+e.getMessage());
				return null;
			}
		}else {
			return null;
		}
	}
	*//**
	 * 获取可选的优惠券集合
	 * @param request
	 * @return json串
	 *//*
	@RequestMapping(value = "/findAbleCoupon")
	@ResponseBody
	public String findAbleCoupon(HttpServletRequest request){
		Integer giftType = Integer.parseInt(request.getParameter("giftType"));
		List<CouponRule> couponRuleList = RemoteServiceSingleton.getInstance().getUserCouponRuleApi().getCouponRuleList(giftType,0);
		for (CouponRule couponRule : couponRuleList) {
			couponRule.setMainrulename(""+couponRule.getCouponruleid());
		}
		String json = JSONArray.fromObject(couponRuleList).toString();
		return json;
	}
	
	@RequestMapping(value = "/findAbleB2CCoupon")
	@ResponseBody
	public String findAbleB2CCoupon(HttpServletRequest request){
		Integer giftType = Integer.parseInt(request.getParameter("couponType"));
		List<CouponRule> couponRuleList = RemoteServiceSingleton.getInstance().getUserCouponRuleApi().getCouponRuleList(giftType,1);
		for (CouponRule couponRule : couponRuleList) {
			couponRule.setMainrulename(""+couponRule.getCouponruleid());
		}
		String json = JSONArray.fromObject(couponRuleList).toString();
		return json;
	}
	
	*//**
	 * 根据优惠券id获取可选的优惠券集合      B2B
	 * @param request
	 * @return json串
	 *//*
	@RequestMapping(value = "/findAbleCouponsByCouponId")
	@ResponseBody
	public String findAbleCouponsByCouponId(HttpServletRequest request){
		Long couponId = Long.parseLong(request.getParameter("couponId"));
		int type = Integer.parseInt(request.getParameter("type"));
		List<CouponRule> couponRuleList = RemoteServiceSingleton.getInstance().getUserCouponRuleApi().getCouponRuleListByCouponId(type,couponId);
		LOGGER.info("根据优惠券id来获取优惠券规则"+couponId);
		for (CouponRule couponRule : couponRuleList) {
			couponRule.setMainrulename(""+couponRule.getCouponruleid());
		}
		String json = JSONArray.fromObject(couponRuleList).toString();
		return json;
	}
	*//**
	 * 根据优惠券id获取可选的优惠券集合    B2C
	 * @param request
	 * @return json串
	 *//*
	@RequestMapping(value = "/findAbleB2CCouponsByCouponId")
	@ResponseBody
	public String findAbleB2CCouponsByCouponId(HttpServletRequest request){
		Long couponId = Long.parseLong(request.getParameter("couponId"));
		int type = Integer.parseInt(request.getParameter("type"));
		List<CouponRule> couponRuleList = RemoteServiceSingleton.getInstance().getUserCouponRuleApi().getB2CCouponRuleListByCouponId(type,couponId);
		LOGGER.info("根据优惠券id来获取优惠券规则"+couponId);
		for (CouponRule couponRule : couponRuleList) {
			couponRule.setMainrulename(""+couponRule.getCouponruleid());
		}
		String json = JSONArray.fromObject(couponRuleList).toString();
		return json;
	}
	
	
	*//**
	 * 查询规则条件
	 * @param ruleId
	 * @return
	 *//*
	@RequestMapping(value ="/ajaxRuleContent")
	@ResponseBody
	public String ajaxRuleContent(String ruleId){
		LOGGER.info("ajax查看规则文本,参数ruleId:"+ruleId);
		if(StringUtils.isEmpty(ruleId)){
			return null;
		}else{
			Long tempRuleId = Long.parseLong(ruleId);
			ActiveProductRule activeProductRule = RemoteServiceSingleton.getInstance().getActiveProductRuleApi().findProductRuleByRuleId(tempRuleId);
			List<ActiveProductRule> result = new ArrayList<ActiveProductRule>();
			result.add(activeProductRule);
			return JSONArray.fromObject(result).toString();
			
		}
	}
	*//**
	 * 商品列表分页查询商品信息
	 * @param request
	 * @return
	 *//*
	@RequestMapping(value = "/findByCondition")
	@ResponseBody
	public String findByCondition(HttpServletRequest request){
		String flag = request.getParameter("flag");
		String json = "";
		DealerProductSelectConDTO vo = new DealerProductSelectConDTO();
		PageBean<DealerProductShowDTO> pageBean = new PageBean<DealerProductShowDTO>();
		vo.setAuditStatus((short)1);
		if("1".equals(flag)){
			String productName = request.getParameter("productName");
			vo.setProductName(productName);
		}
		pageBean.setPage(Integer.parseInt(request.getParameter("page")));
		pageBean.setParameter(vo);
		pageBean.setPageSize(10);
		pageBean = RemoteServiceSingleton.getInstance().getDealerProductService().findProductsByConditions(pageBean);
		json = JSONArray.fromObject(pageBean).toString();
		return json;
	}
	
	*//**
	 * 商品列表分页查询商品信息
	 * @param request
	 * @return
	 *//*
	@RequestMapping(value = "/findByConditionB2C")
	@ResponseBody
	public String findByConditionB2C(HttpServletRequest request){
		String flag = request.getParameter("flag");
		String json = "";
		SkuCodeDTO skuCodeDTO = new SkuCodeDTO();
		PageBean<SkuCodeDTO> pageBean = new PageBean<SkuCodeDTO>();
		skuCodeDTO.setB2cTate((short) 1);
		pageBean.setParameter(skuCodeDTO);
		
		String prodName = request.getParameter("skuName");
		if(prodName!=null&&!"".equals(prodName)){
			skuCodeDTO.setProdName(prodName);
		}
		String productIdStr = request.getParameter("productIdStr");
		if(productIdStr!=null&&!"".equals(productIdStr)){
			productIdStr=productIdStr.replaceAll(" ", "");
			boolean matcher = matcher(productIdStr);
			if(!matcher){
				return "0";
			}
			skuCodeDTO.setProductIdStr(productIdStr);
		}
		String skuIdStr = request.getParameter("skuIdStr");
		if(skuIdStr!=null&&!"".equals(skuIdStr)){
			skuIdStr=skuIdStr.replaceAll(" ", "");
			boolean matcher = matcher(skuIdStr);
			if(!matcher){
				return "1";
			}
			skuCodeDTO.setSkuIdStr(skuIdStr);
		}
		pageBean.setPage(Integer.parseInt(request.getParameter("page")));
		pageBean.setParameter(skuCodeDTO);
		pageBean.setPageSize(20);
		pageBean = RemoteServiceSingleton.getInstance().getCustomerPromotionService().findPageSkuDto(pageBean);
		for(SkuCodeDTO skuCodeDTO1 : pageBean.getResult()){
			skuCodeDTO1.setSkuCode(""+skuCodeDTO1.getSkuId());
		}
		json = JSONArray.fromObject(pageBean).toString();
		return json;
	}
	*//**
	 * 商品列表分页查询商品信息
	 * @param request
	 * @return
	 *//*
	@RequestMapping(value = "/findskuByCondition")
	@ResponseBody
	public String findskuByCondition(HttpServletRequest request){
		String flag = request.getParameter("flag");
		String json = "";
		DealerProductSelectConDTO vo = new DealerProductSelectConDTO();
		PageBean<DealerProductShowDTO> pageBean = new PageBean<DealerProductShowDTO>();
		vo.setAuditStatus((short)1);
		if("1".equals(flag)){
			String productName = request.getParameter("productName");
			vo.setProductName(productName);
		}
		pageBean.setPage(Integer.parseInt(request.getParameter("page")));
		pageBean.setParameter(vo);
		pageBean.setPageSize(10);
		pageBean = RemoteServiceSingleton.getInstance().getDealerProductService().findProductsByConditions(pageBean);
		json = JSONArray.fromObject(pageBean).toString();
		return json;
	}
	*//**
	 * 单品列表分页查询单品信息
	 * @param request
	 * @return
	 *//*
	@RequestMapping(value = "/findSkusByCondition")
	@ResponseBody
	public String findSkusByCondition(HttpServletRequest request){
		String skuName = request.getParameter("skuName");
		String json = "";
		PageBean<SkuCodeDTO> pageBean = new PageBean<SkuCodeDTO>();
		SkuCodeDTO skuCodeDTO = new SkuCodeDTO();
		if(skuName!=null&&!"".equals(skuName)){	
			skuCodeDTO.setProdName(skuName);
		}
		String productIdStr = request.getParameter("productIdStr");
		if(productIdStr!=null&&!"".equals(productIdStr)){
			productIdStr=productIdStr.replaceAll(" ", "");
			boolean matcher = matcher(productIdStr);
			if(!matcher){
				return "0";
			}
			skuCodeDTO.setProductIdStr(productIdStr);
		}
		String skuIdStr = request.getParameter("skuIdStr");
		if(skuIdStr!=null&&!"".equals(skuIdStr)){
			skuIdStr=skuIdStr.replaceAll(" ", "");
			boolean matcher = matcher(skuIdStr);
			if(!matcher){
				return "1";
			}
			skuCodeDTO.setSkuIdStr(skuIdStr);
		}
		pageBean.setParameter(skuCodeDTO);
		pageBean.setPage(Integer.parseInt(request.getParameter("page")));
		pageBean.setPageSize(20);
		pageBean = RemoteServiceSingleton.getInstance().getCustomerPromotionService().findPageSkuDto(pageBean);
		for(SkuCodeDTO skuCodeDTO1 : pageBean.getResult()){
			skuCodeDTO1.setSkuCode(""+skuCodeDTO1.getSkuId());
		}
		json = JSONArray.fromObject(pageBean).toString();
		return json;
	}
	
	*//**
	 * B2B创建规则时ajax获取赠品
	 * @param request
	 * @return
	 *//*
	@RequestMapping("/getGift")
	@ResponseBody
	public String getGift(HttpServletRequest request){
		String json = "";
		String giftType = request.getParameter("gifttype");
		List<GiftDto> giftList = new ArrayList<GiftDto>();
		if("3".equals(giftType)){ //按单品
			GiftDto g1 = new GiftDto();
			g1.setGiftId("001");
			g1.setGiftName("小米4");
			g1.setGiftType("3");
			GiftDto g2 = new GiftDto();
			g2.setGiftId("002");
			g2.setGiftName("iPhone6S");
			g2.setGiftType("3");
			giftList.add(g1);
			giftList.add(g2);
			
			
			PageBean<StockWofeDTO> pageBean=new PageBean<StockWofeDTO>(); 
			StockWofeDTO dto=new StockWofeDTO();
			dto.setOwner(1L);
			pageBean.setParameter(dto);
			pageBean.setPageSize(999999);
			
			List<StockWofeDTO> giftList1=RemoteServiceSingleton.getInstance().getStockService().findStockWofeListPage(pageBean);
			if(giftList1!=null&&giftList1.size()>0){
				json = JSONArray.fromObject(giftList).toString();
				for (StockWofeDTO stockWofeDTO : giftList1) {
					GiftDto gift = new GiftDto();
					gift.setGiftId(""+stockWofeDTO.getSkuId());
					gift.setGiftName(""+stockWofeDTO.getpName()+"/"+stockWofeDTO.getSkuName());
					gift.setGiftType("3");
					
					giftList.add(gift);
				}
				json = JSONArray.fromObject(giftList).toString();
			}else{
				LOGGER.info("获取到的赠品个数为0");
			}
			
		}else if("".equals(giftType)){
			
		}
		return json;
	}
	
	public boolean matcher(String input) {
        for (String str3 : input.split(",")){
            if (!str3.matches("\\d+")) {
                return false;
            }
        }
	    return true;
	}

}
*/