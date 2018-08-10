/*package com.mall.controller.promotions;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.category.po.SubBrand;
import com.mall.category.po.TdCateDisp;
import com.mall.category.po.TdCateDispAttr;
import com.mall.dealer.product.dto.DealerProductSelectConDTO;
import com.mall.dealer.product.dto.DealerProductShowDTO;
import com.mall.dealer.product.dto.ProductSkuStockDto;
import com.mall.dealer.product.dto.SkuCodeDTO;
import com.mall.mybatis.utility.PageBean;
import com.mall.promotion.dto.coupon.CouponInfoDto;
import com.mall.promotion.exception.BusinessException;
import com.mall.promotion.po.activity.ActiveProductConditionDetail;
import com.mall.promotion.po.activity.ActiveProductRule;
import com.mall.promotion.po.activity.ActiveProductRuleDetail;
import com.mall.promotion.po.activity.ActiveProductRuleGift;
import com.mall.promotion.po.activity.ActivityAccessMode;
import com.mall.promotion.po.activity.ActivityArea;
import com.mall.promotion.po.activity.ActivityChannelsCondition;
import com.mall.promotion.po.activity.ActivityUserLevel;
import com.mall.retailer.order.common.PKUtils;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;
import com.mongodb.util.Hash;

@Controller
@RequestMapping(value = "/rules")
public class RulesEditController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ActivitisListController.class);
	private static final String DETAIL = "detail";
	private static final String GIFT = "gift";
	private static final String ALL = "-1";//全部
	private static final long ALL_CITY = -1;//全部市
	private static final String PLEASE_SELECT = "0";//请选择
	private static final short IS_EXCLUDE = 0;//参加
	private static final short IS_EXCLUDE_ONE = 1;//不参加
	private static final int EXCLUDE = 0;//参加
	private static final int EXCLUDE_ONE = 1;//不参加
	private static final short AUDIT_STATUS = 0;
	//活动品类的类型
	private static final int CATEGORY = 1;//品类
	private static final int BRAND = 2;//品牌
	private static final int COMMODITY = 3;//单品
	private static final String ACCESSMETHOD_NONPARTICIPATOR = "1";//所选访问方式不参加本次活动
	private static final String REGION_NONPARTICIPATOR = "2";//所选活动区域不参加本次活动
	private static final String CHANNEL_NONPARTICIPATOR = "3";//所选活动渠道不参加本次活动
	private static final String CATEGORY_NONPARTICIPATOR = "4";//所选活动品类不参加本次活动
	private static final String BRAND_NONPARTICIPATOR = "5";//所选活动品牌不参加本次活动
	private static final String COMMODITY_NONPARTICIPATOR = "6";//所选活动单品不参加本次活动
	//活动商品的7种情况
	private static final Integer GROUP0 = 0;//一个所有，两个所有，三个所有
	private static final Integer GROUP1 = 1;//品类部分
	private static final Integer GROUP2 = 2;//品牌部分
	private static final Integer GROUP3 = 3;//单品部分
	private static final Integer GROUP4 = 4;//品类和品牌部分
	private static final Integer GROUP5 = 5;//品类和单品部分
	private static final Integer GROUP6 = 6;//品牌和单品部分
	private static final Integer GROUP7 = 7;//品类、品牌、单品部分
	
	//活动类型的值
	private static final Integer RULETERM1 = 1;//满减
	private static final Integer RULETERM2 = 2;//满返
	private static final Integer RULETERM3 = 3;//限时直降
	private static final Integer RULETERM4 = 4;//满赠
	private static final Integer RULETERM5 = 5;//买赠
	
	//radio的选择value值
	private static final String RADIO1 = "1";
	private static final String RADIO2 = "2";
	private static final String RADIO3 = "3";
	private static final String RADIO4 = "4";
	
	private static final String MONEY_SUFFIX = ".00";//金额后缀
	
	private static final Integer SERIVE_RULEITEM1 = 20;//阶梯满减
	private static final Integer SERIVE_RULEITEM2 = 21;//普通满减
	private static final Integer SERIVE_RULEITEM9 = 22;//阶梯满返
	private static final Integer SERIVE_RULEITEM0 = 23;//普通满返
	private static final Integer SERIVE_RULEITEM3 = 24;//阶梯满赠
	private static final Integer SERIVE_RULEITEM4 = 25;//普通满赠
	private static final Integer SERIVE_RULEITEM5 = 26;//买赠
	private static final Integer SERIVE_RULEITEM6 = 27;//按金额直降
	private static final Integer SERIVE_RULEITEM7 = 28;//按百分比直降
	private static final Integer SERIVE_RULEITEM8 = 29;//等比满赠
	private static final Integer SERIVE_RULEITEM10 = 30;//等比满返
	
	private static final Integer SENDTYPE1 = 1;//按照数量
	private static final Integer SENDTYPE2 = 2;//按照金额
	
	private static final long activityId = 111;
	private static final int DEFAULT_PAGE = 1;
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

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveRules")
	@ResponseBody
	public String saveRules(HttpServletRequest request, ActiveProductRule rule, Long activeId) {
		activeId = activityId;
		
		Long id = PKUtils.getLongPrimaryKey();
		rule.setId(id);
		rule.setIsExclude((short)IS_EXCLUDE);
		rule.setAuditStatus(AUDIT_STATUS);
		rule.setStatus(AUDIT_STATUS);
		rule.setCreateTime(new Date());
		rule.setActiveId(activeId);
		String from = request.getParameter("productFromMethod");//商品发货途径
		rule.setSupply(Short.parseShort(from));
		//rule.getSupply()
		//获取活动会员
		String[] member = request.getParameterValues("memberValue");//活动会员
		String[] access = request.getParameterValues("accessMethodValue");//访问方式
		String[] category = request.getParameterValues("categoryValue");//活动品类
		String[] brand = request.getParameterValues("brandValue");//活动品牌
		String[] region = request.getParameterValues("regionValue");//活动区域
		String[] cannel = request.getParameterValues("cannelValue");//活动渠道
		String[] commodity = request.getParameterValues("commodityValue");//活动商品
		String[] nonparticipator = request.getParameterValues("nonparticipatorValue");//不参加的所有规则
		
		rule.setUserGrade(Short.parseShort(request.getParameter("userGrade1")));
		rule.setLimittimes(Integer.parseInt(request.getParameter("limittimes1")));
		
		List<ActivityChannelsCondition> cannelList = getActivityChannelsConditions(cannel, id, nonparticipator);
		List<ActivityUserLevel> levelList = getActivityUserLevel(member,id, rule);
		List<ActivityAccessMode> accessMethod = getAccessMethod(access, id, nonparticipator);
		List<ActivityArea> regionLIst = getRegion(region, id, nonparticipator);
		
		List<ActiveProductConditionDetail> detailList = null;
		Integer group = getGroup(category, brand, commodity);
		if(group != null) {
			if(group == GROUP1) {
				detailList = getActiveProductConditionDetailList(category, null, null, nonparticipator, activeId, id, CATEGORY, BRAND, COMMODITY, GROUP1);
			} else if(group == GROUP2) {
				detailList = getActiveProductConditionDetailList(null, brand, null, nonparticipator, activeId, id, CATEGORY, BRAND, COMMODITY, GROUP2);
			} else if(group == GROUP3) {
				detailList = getActiveProductConditionDetailList(null, null, commodity, nonparticipator, activeId, id, CATEGORY, BRAND, COMMODITY, GROUP3);
			} else if(group == GROUP4) {
				detailList = getActiveProductConditionDetailList(category, brand, null, nonparticipator,  activeId, id, CATEGORY, BRAND, COMMODITY, GROUP4);
			} else if(group == GROUP5) {
				detailList = getActiveProductConditionDetailList(category, null, commodity, nonparticipator, activeId, id, CATEGORY, BRAND, COMMODITY, GROUP5);
			} else if(group == GROUP6) {
				detailList = getActiveProductConditionDetailList(null, brand, commodity, nonparticipator, activeId, id, CATEGORY, BRAND, COMMODITY, GROUP6);
			} else if(group == GROUP7) {
				detailList = getActiveProductConditionDetailList(category, brand, commodity, nonparticipator, activeId, id, CATEGORY, BRAND, COMMODITY, GROUP7);
			} else {
				detailList = getActiveProductConditionDetailList(null, null, null, nonparticipator, activeId, id, CATEGORY, BRAND, COMMODITY, GROUP0);
			}
		}
		List<ActiveProductRuleDetail> ruleDetail  = null;
		List<ActiveProductRuleGift> giftList = null;
		Integer ruleTerm = rule.getRuleTerm();
		if(ruleTerm == RULETERM1) {//满减
			String[] fullcutValue = request.getParameterValues("fullcutValue");//
			String[] orderMoneyValue = request.getParameterValues("orderMoneyValue");
			String[] activeMoney = request.getParameterValues("activeMoney");
			String[] ingeterMultiple = request.getParameterValues("ingeterMultiple");
			String[] moneyPresent1 = request.getParameterValues("moneyPresent1");
			if(getValueByArr(fullcutValue).equals(RADIO1)) {//普通满减
				String[] parameterValues = request.getParameterValues("radioFullCutValue");
				String[] array = getArray(parameterValues);
				if(array != null) {
					if(RADIO1.equals(array[0])) {//普通满减
						ruleDetail = getRuleDetail(activeId, id, getValueByArr(orderMoneyValue), null, getValueByArr(moneyPresent1), null, null);
						rule.setRuleTerm(SERIVE_RULEITEM2);
					} else {//阶梯满减
						String[] mul = request.getParameterValues("multipleValue");
						String[] sta = request.getParameterValues("startValue");
						String[] en = request.getParameterValues("endValue");
						String[] mon = request.getParameterValues("moneyValue");
						String[] multiple = getArray(mul);
						String[] start = getArray(sta);
						String[] end = getArray(en);
						String[] money = getArray(mon);
						ruleDetail = getRuleDetailFullCatLadder(activeId, id, multiple, start, end, money);
						rule.setRuleTerm(SERIVE_RULEITEM1);//阶梯满减
					}
				}
			} else {//等比满减
				ruleDetail = getRuleDetailThree(activeId, id, getValueByArr(activeMoney), getValueByArr(ingeterMultiple), getValueByArr(moneyPresent1), null, null);
				rule.setRuleTerm(SERIVE_RULEITEM2);
			}
		} else if(ruleTerm == RULETERM2) {//满返
			//收集订单条件参数
			String[] orderCondition = request.getParameterValues("orderCondition");
			String[] arrayByArray = getArray(orderCondition);
			//收集执行形式参数
			String[] executeFormalization = request.getParameterValues("executeFormalization");
			if(arrayByArray != null) {
				if(RADIO1.equals(arrayByArray[0])) {//普通满返 按金额
					Map<String, Object> map = getDetailAndGiftByMoney(activeId, id, arrayByArray, executeFormalization, request, false, rule);
					ruleDetail = (List<ActiveProductRuleDetail>) map.get(DETAIL);
					giftList = (List<ActiveProductRuleGift>) map.get(GIFT);
				} else if(RADIO2.equals(arrayByArray[0])) {//等比满返 按金额
					Map<String, Object> map = getDetailGiftByRatioReturn(activeId, id, arrayByArray, executeFormalization, request, rule);
					ruleDetail = (List<ActiveProductRuleDetail>) map.get(DETAIL);
					giftList = (List<ActiveProductRuleGift>) map.get(GIFT);
					rule.setRuleTerm(SERIVE_RULEITEM10);
					rule.setSendtype(SENDTYPE2);
				} else if(RADIO3.equals(arrayByArray[0])) {//普通满返  按数量
					Map<String, Object> map = getDetailAndGiftByCount(activeId, id, arrayByArray, executeFormalization, request, true, rule);
					ruleDetail = (List<ActiveProductRuleDetail>) map.get(DETAIL);
					giftList = (List<ActiveProductRuleGift>) map.get(GIFT);
				} else if(RADIO4.equals(arrayByArray[0])) {//等比满返 按数量
					Map<String, Object> map = getDetailGiftByRatioReturn(activeId, id, arrayByArray, executeFormalization, request, rule);
					ruleDetail = (List<ActiveProductRuleDetail>) map.get(DETAIL);
					giftList = (List<ActiveProductRuleGift>) map.get(GIFT);
					rule.setRuleTerm(SERIVE_RULEITEM10);
					rule.setSendtype(SENDTYPE1);
				}
			}
		} else if(ruleTerm == RULETERM3) {//限时直降
			String[] parameterValues = request.getParameterValues("singleMoneyPresent");
			String[] array = getArray(parameterValues);
			ruleDetail = getDetailByRuleTerm3(activeId, id, array, rule);
		
		} else if(ruleTerm == RULETERM4) {//满赠
			//收集订单条件参数
			String[] orderCondition = request.getParameterValues("orderCondition");
			String[] arrayByArray = getArray(orderCondition);
			//收集执行形式参数
			String[] executeFormalization = request.getParameterValues("executeFormalization");
			if(arrayByArray != null) {
				if(RADIO1.equals(arrayByArray[0])) {//普通满赠和阶梯满赠  金额
					Map<String, Object> map = getDetailAndGiftByCommonAndLadder(activeId, id, arrayByArray, executeFormalization, request, false, rule);
					ruleDetail = (List<ActiveProductRuleDetail>) map.get(DETAIL);
					giftList = (List<ActiveProductRuleGift>) map.get(GIFT);
				} else if(RADIO2.equals(arrayByArray[0])) {//等比满赠 金额
					 Map<String, Object> map = getDetailAndGiftByRatio(activeId, id, arrayByArray, executeFormalization, request);
					 ruleDetail = (List<ActiveProductRuleDetail>) map.get(DETAIL);
					giftList = (List<ActiveProductRuleGift>) map.get(GIFT);
					rule.setRuleTerm(SERIVE_RULEITEM8);
					rule.setSendtype(SENDTYPE2);
				} else if(RADIO3.equals(arrayByArray[0])) {//普通满赠和阶梯满赠   数量
					Map<String, Object> map = getDetailAndGiftByCommonAndLadder(activeId, id, arrayByArray, executeFormalization, request, true, rule);
					ruleDetail = (List<ActiveProductRuleDetail>) map.get(DETAIL);
					giftList = (List<ActiveProductRuleGift>) map.get(GIFT);
				} else if(RADIO4.equals(arrayByArray[0])) {//等比满赠 数量
					Map<String, Object> map = getDetailAndGiftByRatio(activeId, id, arrayByArray, executeFormalization, request);
					ruleDetail = (List<ActiveProductRuleDetail>) map.get(DETAIL);
					giftList = (List<ActiveProductRuleGift>) map.get(GIFT);
					rule.setRuleTerm(SERIVE_RULEITEM8);
					rule.setSendtype(SENDTYPE1);
				}
			}
			
		} else if(ruleTerm == RULETERM5) {//买赠
			
			rule.setRuleTerm(SERIVE_RULEITEM5);//买赠
		} else {
			
		}
		
		List<String> skuIds = new ArrayList<String>();
		List<Long> pids = new ArrayList<Long>();
		List<String> dis = new ArrayList<String>();
		if(from.equals("12") && group == GROUP1){//保税区按照品类创建活动
			for (ActiveProductConditionDetail activeProductConditionDetail : detailList) {
				dis.add(activeProductConditionDetail.getConditionContent());
			}
			LOGGER.info("dis size----"+dis.size());
			
			skuIds = RemoteServiceSingleton.getInstance().getDealerProductSkuService().getSkuIdsByPubId(dis,12);
			LOGGER.info("skuIds size----"+skuIds.size());
		}
		if(from.equals("12") && group == GROUP3){
			for(ActiveProductConditionDetail activeProductConditionDetail : detailList){
				skuIds.add(activeProductConditionDetail.getConditionContent());
			}
		}
		try {
			if(from.equals("12") && (group == GROUP1 || group == GROUP3)){
				for(String str : skuIds){
					List<ActiveProductConditionDetail> list = new ArrayList<ActiveProductConditionDetail>();
					Long newRuleId = PKUtils.getLongPrimaryKey();
					rule.setId(newRuleId);
					Long detailId = PKUtils.getLongPrimaryKey();
					ActiveProductConditionDetail cat = new ActiveProductConditionDetail();
					cat.setConditiondetailId(detailId);
					cat.setConditionType(3);
					cat.setActiveId(activeId);
					cat.setRuleId(newRuleId);
					cat.setContentType(3);
					cat.setConditionContent(str);
					cat.setIsExclude(0);
					list.add(cat);
					if(null != ruleDetail && ruleDetail.size() > 0){
						for (ActiveProductRuleDetail activeProductRuleDetail : ruleDetail) {
							Long newRuleDetailId = PKUtils.getLongPrimaryKey();
							activeProductRuleDetail.setRuledetailId(newRuleDetailId);
							activeProductRuleDetail.setRuleId(newRuleId);
						}
					}
					if(null != cannelList && cannelList.size() > 0){
						for (ActivityChannelsCondition activityChannelsCondition : cannelList) {
							Long newChannelId = PKUtils.getLongPrimaryKey();
							activityChannelsCondition.setId(newChannelId);
							activityChannelsCondition.setRuleId(newRuleId);
						}
					}
					if(null != levelList && levelList.size() > 0){
						for (ActivityUserLevel activityUserLevel : levelList) {
							Long newUserLevelId = PKUtils.getLongPrimaryKey();
							activityUserLevel.setId(newUserLevelId);
							activityUserLevel.setRuleId(newRuleId);
						}
					}
					if(null != accessMethod && accessMethod.size() > 0){
						for (ActivityAccessMode activityAccessMode : accessMethod) {
							Long newAccessModeId = PKUtils.getLongPrimaryKey();
							activityAccessMode.setId(newAccessModeId);
							activityAccessMode.setRuleId(newRuleId);
						}
					}
					if(null != regionLIst && regionLIst.size() > 0){
						for (ActivityArea activityArea : regionLIst) {
							Long newAreaId = PKUtils.getLongPrimaryKey();
							activityArea.setId(newAreaId);
							activityArea.setRuleId(newRuleId);
						}
					}
					if(null != giftList && giftList.size() > 0){
						for (ActiveProductRuleGift activeProductRuleGift : giftList) {
							Long newProductRuleGiftId = PKUtils.getLongPrimaryKey();
							activeProductRuleGift.setWid(newProductRuleGiftId);
							activeProductRuleGift.setRuleId(newRuleId);
						}
					}
					RemoteServiceSingleton.getInstance().getActiveProductRuleApi().createProductRuleNew(rule, list, ruleDetail, null, cannelList, levelList, accessMethod, regionLIst, giftList);
				}
			}else{
				RemoteServiceSingleton.getInstance().getActiveProductRuleApi().createProductRuleNew(rule, detailList, ruleDetail, null, cannelList, levelList, accessMethod, regionLIst, giftList);
			}
		} catch (Exception e) {
			LOGGER.error("调用服务异常：" + e.getMessage(), e);
		}
		return "success";
	}
	
	*//**
	 * 阶梯满减
	 * @param activeId
	 * @param ruleId
	 * @param multiple
	 * @param start
	 * @param end
	 * @param money
	 * @return
	 *//*
	private List<ActiveProductRuleDetail> getRuleDetailFullCatLadder(Long activeId, Long ruleId, String[] multiple, String[] start, String[] end, String[] money) {
		List<ActiveProductRuleDetail> ruleDetailList = new ArrayList<ActiveProductRuleDetail>();
		if(start != null && end != null && start.length == end.length) {
			for (int i = 0; i < start.length; i++) {
				ActiveProductRuleDetail ruleDetail = new ActiveProductRuleDetail();
				ruleDetail.setRuledetailId(PKUtils.getLongPrimaryKey());
				ruleDetail.setActiveId(activeId);
				ruleDetail.setRuleId(ruleId);
				if(start[i].contains(".")) {
					ruleDetail.setTerm0(start[i]);
				} else {
					ruleDetail.setTerm0(start[i]+ MONEY_SUFFIX);
				}
				if(end[i].contains(".")) {
					ruleDetail.setTerm1(end[i]);
				} else {
					ruleDetail.setTerm1(end[i] + MONEY_SUFFIX);
				}
				if(money[i].contains(".")) {
					ruleDetail.setTerm2(money[i]);
				} else {
					ruleDetail.setTerm2(money[i] + MONEY_SUFFIX);
				}
				ruleDetailList.add(ruleDetail);
			}
		}
		return ruleDetailList;
	}
	
	private Map<String, Object>  getDetailAndGiftByRatio(Long activeId, Long ruleId, String[] orderCondition, String[] executeFormalization, HttpServletRequest request) {
		Map<String, Object> detailGift = new HashMap<String, Object>();
		List<ActiveProductRuleDetail> ruleDetailList = new ArrayList<ActiveProductRuleDetail>();
		List<ActiveProductRuleGift> giftList = new ArrayList<ActiveProductRuleGift>();
		String[] formali  = getArray(executeFormalization);
		String formalization0 = formali[0];
		String[] couponId = request.getParameterValues("couponId");
		String[] couponCount = request.getParameterValues("couponCount");
		String[] multiple = request.getParameterValues("multiple");
		String[] pname = request.getParameterValues("pname");
		String[] skuName = request.getParameterValues("skuName");
		String[] pid = request.getParameterValues("pid");
	
		String[] mul = getArray(multiple);
		String[] couId = getArray(couponId);
		String[] couCount = getArray(couponCount);
		String[] pna = getArray(pname);
		String[] skuNa = getArray(skuName);
		String[] pd = getArray(pid);
		
		if(RADIO1.equals(formalization0)) {//按阶梯赠送优惠券 
			String countOrMoney = orderCondition[0];
			String orderMoney = orderCondition[1];//单品金额大于等于
			String ingeterMultiple = orderCondition[2];//整数倍
			ActiveProductRuleDetail ruleDetail = getRuleDetailByRatio(activeId, ruleId, orderMoney, ingeterMultiple, countOrMoney);
			if(couId != null && couCount != null && couId.length == couCount.length) {
				for (int i = 0; i < couCount.length; i++) {
					ActiveProductRuleGift gift = getGiftMultiple2(activeId, ruleId, ruleDetail.getRuledetailId(), couId, couCount, i, mul, pna, skuNa, pd);
					giftList.add(gift);
				}
			}
			ruleDetailList.add(ruleDetail);
		} 
		detailGift.put(DETAIL, ruleDetailList);
		detailGift.put(GIFT, giftList);
		return detailGift;
	}
	
	
	private Map<String, Object>  getDetailGiftByRatioReturn(Long activeId, Long ruleId, String[] orderCondition, String[] executeFormalization, HttpServletRequest request, ActiveProductRule rule) {
		Map<String, Object> detailGift = new HashMap<String, Object>();
		List<ActiveProductRuleDetail> ruleDetailList = new ArrayList<ActiveProductRuleDetail>();
		List<ActiveProductRuleGift> giftList = new ArrayList<ActiveProductRuleGift>();
		String[] formali  = getArray(executeFormalization);
		String formalization0 = formali[0];
		String[] couponId = request.getParameterValues("couponId");
		String[] couponCount = request.getParameterValues("couponCount");
		String[] multiple = request.getParameterValues("multiple");
	
		String[] mul = getArray(multiple);
		String[] couId = getArray(couponId);
		String[] couCount = getArray(couponCount);
		
		if(RADIO1.equals(formalization0)) {//按阶梯赠送优惠券 
			String countOrMoney = orderCondition[0];
			String orderMoney = orderCondition[1];//单品金额大于等于
			String ingeterMultiple = orderCondition[2];//整数倍
			ActiveProductRuleDetail productObjFour = getDetailRationReturn(activeId, ruleId, orderMoney, ingeterMultiple, countOrMoney);
			if(couId != null && couCount != null && couId.length == couCount.length) {
				for (int i = 0; i < couCount.length; i++) {
					ActiveProductRuleGift gift = getGiftMultiple(activeId, ruleId, productObjFour.getRuledetailId(), couId, couCount, i, mul);
					giftList.add(gift);
				}
			}
			ruleDetailList.add(productObjFour);
		} 
		detailGift.put(DETAIL, ruleDetailList);
		detailGift.put(GIFT, giftList);
		return detailGift;
	}
	
	*//**
	 * 等比满赠
	 * @param activeId
	 * @param ruleId
	 * @param orderMoney
	 * @param ingeterMultiple
	 * @param countOrMoney
	 * @return
	 *//*
	private ActiveProductRuleDetail getRuleDetailByRatio(Long activeId, Long ruleId,String orderMoney,  String ingeterMultiple, String countOrMoney) {
		ActiveProductRuleDetail ruleDetail = new ActiveProductRuleDetail();
		ruleDetail.setRuledetailId(PKUtils.getLongPrimaryKey());
		ruleDetail.setActiveId(activeId);
		ruleDetail.setRuleId(ruleId);
		if(RADIO1.equals(countOrMoney) || RADIO2.equals(countOrMoney)) {//按金额
			ruleDetail.setTerm0(orderMoney+ MONEY_SUFFIX);
			
		} else if(RADIO3.equals(countOrMoney) || RADIO4.equals(countOrMoney)){//按数量
			ruleDetail.setTerm0(orderMoney);
		}
		ruleDetail.setTerm1(ingeterMultiple);
		return ruleDetail;
	}
	
	*//**
	 * 等比满返 detail
	 * @param activeId
	 * @param ruleId
	 * @param orderMoney
	 * @param ingeterMultiple
	 * @param countOrMoney
	 * @return
	 *//*
	private ActiveProductRuleDetail getDetailRationReturn(Long activeId, Long ruleId,String orderMoney,  String ingeterMultiple, String countOrMoney) {
		ActiveProductRuleDetail ruleDetail = new ActiveProductRuleDetail();
		ruleDetail.setRuledetailId(PKUtils.getLongPrimaryKey());
		ruleDetail.setActiveId(activeId);
		ruleDetail.setRuleId(ruleId);
		if(RADIO1.equals(countOrMoney) || RADIO2.equals(countOrMoney)) {//按金额
			ruleDetail.setTerm0(orderMoney+ MONEY_SUFFIX);
			
		} else if(RADIO3.equals(countOrMoney) || RADIO4.equals(countOrMoney)){//按数量
			ruleDetail.setTerm0(orderMoney);
		}
		ruleDetail.setTerm1(ingeterMultiple);
		return ruleDetail;
	}
	
	*//**
	 * 普通满返和阶梯满返 按金额
	 * @param activeId
	 * @param ruleId
	 * @param orderCondition
	 * @param executeFormalization
	 * @param request
	 * @param isCount
	 * @param rule
	 * @return
	 *//*
	private Map<String, Object> getDetailAndGiftByMoney(Long activeId, Long ruleId, String[] orderCondition, String[] executeFormalization, HttpServletRequest request , boolean isCount, ActiveProductRule rule) {
		Map<String, Object> detailGift = new HashMap<String, Object>();
		List<ActiveProductRuleDetail> ruleDetailList = new ArrayList<ActiveProductRuleDetail>();
		List<ActiveProductRuleGift> giftList = new ArrayList<ActiveProductRuleGift>();
		String[] formali  = getArray(executeFormalization);//执行条件
		String formalization0 = formali[0];
		String[] couponId = request.getParameterValues("couponId");
		String[] couponCount = request.getParameterValues("couponCount");
		
		
		String[] pname = request.getParameterValues("pname");
		String[] skuName = request.getParameterValues("skuName");
		String[] pid = request.getParameterValues("pid");
		String[] multiple = request.getParameterValues("multiple");
		String[] pna = getArray(pname);
		String[] skuNa = getArray(skuName);
		String[] pd = getArray(pid);
		String[] mul = getArray(multiple);
		
		
		String[] couId = getArray(couponId);
		String[] couCount = getArray(couponCount);
		
		if(RADIO1.equals(formalization0)) {//按阶梯赠送优惠券 
			String orderMoney = orderCondition[1];
			ActiveProductRuleDetail ruleDetail = getRuleDetail(activeId, ruleId, orderMoney, isCount);
			if(couId != null && couCount != null && couId.length == couCount.length) {
				for (int i = 0; i < couCount.length; i++) {
					ActiveProductRuleGift ruleGift = getGift(activeId, ruleId, ruleDetail.getRuledetailId(), couId, couCount, skuNa, pna, pd, i, mul, rule);
					giftList.add(ruleGift);
				}
			}
			ruleDetailList.add(ruleDetail);
			if(RULETERM2 == rule.getRuleTerm()) {//普通满返
				rule.setRuleTerm(SERIVE_RULEITEM0);
			} else if(RULETERM4 == rule.getRuleTerm()) {//普通满赠
				rule.setRuleTerm(SERIVE_RULEITEM4);
			}
			
			rule.setSendtype(SENDTYPE2);//按金额
		} else if(RADIO2.equals(formalization0)){//返回优惠劵(不等比) 
			
			String[] couponStart = request.getParameterValues("couponStart");
			String[] couponEnd = request.getParameterValues("couponEnd");
			String[] start = getArray(couponStart);
			String[] end  = getArray(couponEnd);
			String formalization1 = formali[1];
			
			if(couId != null && couCount != null  && start != null  && end != null && couId.length == couCount.length ) {
				for (int i = 0; i < couCount.length; i++) {
					ActiveProductRuleDetail ruleDetail = geDetailCommonReturn(
							activeId, ruleId, formalization1, couId, couCount,
							start, end, i);
					ruleDetailList.add(ruleDetail);
				}
			}
			if(RULETERM2 == rule.getRuleTerm()) {//阶梯满返
				rule.setRuleTerm(SERIVE_RULEITEM9);
			} else if(RULETERM4 == rule.getRuleTerm()) {//阶梯满赠
				rule.setRuleTerm(SERIVE_RULEITEM3);
			}
			if(RADIO1.equals(formalization1)) {//按数量
				rule.setSendtype(SENDTYPE1);//
			} else if(RADIO2.equals(formalization1)) {
				rule.setSendtype(SENDTYPE2);//
			}
		}
		detailGift.put(DETAIL, ruleDetailList);
		detailGift.put(GIFT, giftList);
		return detailGift;
	}
	
	*//**
	 * 普通满返  按数量
	 * @param activeId
	 * @param ruleId
	 * @param orderCondition
	 * @param executeFormalization
	 * @param request
	 * @param isCount
	 * @param rule
	 * @return
	 *//*
	private Map<String, Object> getDetailAndGiftByCount(Long activeId, Long ruleId, String[] orderCondition, String[] executeFormalization, HttpServletRequest request , boolean isCount, ActiveProductRule rule) {
		Map<String, Object> detailGift = new HashMap<String, Object>();
		List<ActiveProductRuleDetail> ruleDetailList = new ArrayList<ActiveProductRuleDetail>();
		List<ActiveProductRuleGift> giftList = new ArrayList<ActiveProductRuleGift>();
		String[] formali  = getArray(executeFormalization);//执行条件
		String formalization0 = formali[0];
		String[] couponId = request.getParameterValues("couponId");
		String[] couponCount = request.getParameterValues("couponCount");
		
		
		String[] pname = request.getParameterValues("pname");
		String[] skuName = request.getParameterValues("skuName");
		String[] pid = request.getParameterValues("pid");
		String[] multiple = request.getParameterValues("multiple");
		String[] pna = getArray(pname);
		String[] skuNa = getArray(skuName);
		String[] pd = getArray(pid);
		String[] mul = getArray(multiple);
		
		
		String[] couId = getArray(couponId);
		String[] couCount = getArray(couponCount);
		
		if(RADIO1.equals(formalization0)) {//按阶梯赠送优惠券 
			String orderMoney = orderCondition[1];
			ActiveProductRuleDetail ruleDetail = getRuleDetail(activeId, ruleId, orderMoney, isCount);
			if(couId != null && couCount != null && couId.length == couCount.length) {
				for (int i = 0; i < couCount.length; i++) {
					ActiveProductRuleGift ruleGift = getGift(activeId, ruleId, ruleDetail.getRuledetailId(), couId, couCount, skuNa, pna, pd, i, mul, rule);
					giftList.add(ruleGift);
				}
			}
			ruleDetailList.add(ruleDetail);
			if(RULETERM2 == rule.getRuleTerm()) {//普通满返
				rule.setRuleTerm(SERIVE_RULEITEM0);
			} else if(RULETERM4 == rule.getRuleTerm()) {//普通满赠
				rule.setRuleTerm(SERIVE_RULEITEM4);
			}
			rule.setSendtype(SENDTYPE1);//按数量
		} else if(RADIO2.equals(formalization0)){//返回优惠劵(不等比) 
			String[] couponStart = request.getParameterValues("couponStart");
			String[] couponEnd = request.getParameterValues("couponEnd");
			String[] start = getArray(couponStart);
			String[] end  = getArray(couponEnd);
			String formalization1 = formali[1];
			
			if(couId != null && couCount != null  && start != null  && end != null && couId.length == couCount.length ) {
				for (int i = 0; i < couCount.length; i++) {
					ActiveProductRuleDetail ruleDetail = getPoductObjTwo(
							activeId, ruleId, formalization1, couId, couCount,
							start, end, i);
					ruleDetailList.add(ruleDetail);
				}
			}
			if(RULETERM2 == rule.getRuleTerm()) {//阶梯满返
				rule.setRuleTerm(SERIVE_RULEITEM9);
			} else if(RULETERM4 == rule.getRuleTerm()) {//阶梯满赠
				rule.setRuleTerm(SERIVE_RULEITEM3);
			}
			if(RADIO1.equals(formalization1)) {//按数量
				rule.setSendtype(SENDTYPE1);//
			} else if(RADIO2.equals(formalization1)) {
				rule.setSendtype(SENDTYPE2);//
			}
		}
		detailGift.put(DETAIL, ruleDetailList);
		detailGift.put(GIFT, giftList);
		return detailGift;
	}
	
	*//**
	 * 普通满赠和阶梯满赠
	 * @param activeId
	 * @param ruleId
	 * @param orderCondition
	 * @param executeFormalization
	 * @param request
	 * @param isCount
	 * @param rule
	 * @return
	 *//*
	private Map<String, Object> getDetailAndGiftByCommonAndLadder(Long activeId, Long ruleId, String[] orderCondition, String[] executeFormalization, HttpServletRequest request , boolean isCount, ActiveProductRule rule) {
		Map<String, Object> detailGift = new HashMap<String, Object>();
		List<ActiveProductRuleDetail> ruleDetailList = new ArrayList<ActiveProductRuleDetail>();
		List<ActiveProductRuleGift> giftList = new ArrayList<ActiveProductRuleGift>();
		String[] formali  = getArray(executeFormalization);//执行条件
		String formalization0 = formali[0];
		String[] couponId = request.getParameterValues("couponId");
		String[] couponCount = request.getParameterValues("couponCount");
		
		
		String[] pname = request.getParameterValues("pname");
		String[] skuName = request.getParameterValues("skuName");
		String[] pid = request.getParameterValues("pid");
		String[] multiple = request.getParameterValues("multiple");
		String[] pna = getArray(pname);
		String[] skuNa = getArray(skuName);
		String[] pd = getArray(pid);
		String[] mul = getArray(multiple);
		
		
		String[] couId = getArray(couponId);
		String[] couCount = getArray(couponCount);
		
		if(RADIO1.equals(formalization0)) {//按阶梯赠送优惠券 
			String orderMoney = orderCondition[1];
			ActiveProductRuleDetail ruleDetail = getRuleDetail(activeId, ruleId, orderMoney, isCount);
			if(couId != null && couCount != null && couId.length == couCount.length) {
				for (int i = 0; i < couCount.length; i++) {
					ActiveProductRuleGift ruleGift = getGift(activeId, ruleId, ruleDetail.getRuledetailId(), couId, couCount, skuNa, pna, pd, i, mul, rule);
					giftList.add(ruleGift);
				}
			}
			ruleDetailList.add(ruleDetail);
			if(RULETERM2 == rule.getRuleTerm()) {//普通满返
				rule.setRuleTerm(SERIVE_RULEITEM0);
			} else if(RULETERM4 == rule.getRuleTerm()) {//普通满赠
				rule.setRuleTerm(SERIVE_RULEITEM4);
			}
			if(RADIO1.equals(orderCondition[0])) {//按金额
				rule.setSendtype(SENDTYPE2);
			} else if(RADIO3.equals(orderCondition[0])) {//按数量
				rule.setSendtype(SENDTYPE1);
			}
 			
		} else if(RADIO2.equals(formalization0)){//返回优惠劵(不等比) 
			String[] couponStart = request.getParameterValues("couponStart");
			String[] couponEnd = request.getParameterValues("couponEnd");
			String[] start = getArray(couponStart);
			String[] end  = getArray(couponEnd);
			String formalization1 = formali[1];
			
			//阶梯满赠不用存gift ，起点和阶点都存detail
			if(couId != null && couCount != null  && start != null  && end != null && couId.length == couCount.length ) {
				for (int i = 0; i < couCount.length; i++) {
					ActiveProductRuleDetail ruleDetail = getDetailLadder(activeId, ruleId, formalization1, couId, couCount, start, end, i, pna, pd, skuNa);
					ruleDetailList.add(ruleDetail);
				}
			}
			if(RULETERM2 == rule.getRuleTerm()) {//阶梯满返
				rule.setRuleTerm(SERIVE_RULEITEM9);
			} else if(RULETERM4 == rule.getRuleTerm()) {//阶梯满赠
				rule.setRuleTerm(SERIVE_RULEITEM3);
			}
			if(RADIO1.equals(formalization1)) {//按数量
				rule.setSendtype(SENDTYPE1);//
			} else if(RADIO2.equals(formalization1)) {
				rule.setSendtype(SENDTYPE2);//
			}
		}
		
		detailGift.put(DETAIL, ruleDetailList);
		detailGift.put(GIFT, giftList);
		return detailGift;
	}
	
	
	private Map<String, Object> getDetailAndGift(Long activeId, Long ruleId, String[] orderCondition, String[] executeFormalization, HttpServletRequest request , boolean isCount, ActiveProductRule rule) {
		Map<String, Object> detailGift = new HashMap<String, Object>();
		List<ActiveProductRuleDetail> ruleDetailList = new ArrayList<ActiveProductRuleDetail>();
		List<ActiveProductRuleGift> giftList = new ArrayList<ActiveProductRuleGift>();
		String[] formali  = getArray(executeFormalization);//执行条件
		String formalization0 = formali[0];
		String[] couponId = request.getParameterValues("couponId");
		String[] couponCount = request.getParameterValues("couponCount");
		
		
		String[] pname = request.getParameterValues("pname");
		String[] skuName = request.getParameterValues("skuName");
		String[] pid = request.getParameterValues("pid");
		String[] multiple = request.getParameterValues("multiple");
		String[] pna = getArray(pname);
		String[] skuNa = getArray(skuName);
		String[] pd = getArray(pid);
		String[] mul = getArray(multiple);
		
		
		String[] couId = getArray(couponId);
		String[] couCount = getArray(couponCount);
		
		if(RADIO1.equals(formalization0)) {//按阶梯赠送优惠券 
			String orderMoney = orderCondition[1];
			ActiveProductRuleDetail ruleDetail = getRuleDetail(activeId, ruleId, orderMoney, isCount);
			if(couId != null && couCount != null && couId.length == couCount.length) {
				for (int i = 0; i < couCount.length; i++) {
					ActiveProductRuleGift ruleGift = getGift(activeId, ruleId, ruleDetail.getRuledetailId(), couId, couCount, skuNa, pna, pd, i, mul, rule);
					giftList.add(ruleGift);
				}
			}
			ruleDetailList.add(ruleDetail);
			if(RULETERM2 == rule.getRuleTerm()) {//普通满返
				rule.setRuleTerm(SERIVE_RULEITEM0);
			} else if(RULETERM4 == rule.getRuleTerm()) {//普通满赠
				rule.setRuleTerm(SERIVE_RULEITEM4);
			}
 			
		} else if(RADIO2.equals(formalization0)){//返回优惠劵(不等比) 
			String[] couponStart = request.getParameterValues("couponStart");
			String[] couponEnd = request.getParameterValues("couponEnd");
			String[] start = getArray(couponStart);
			String[] end  = getArray(couponEnd);
			String formalization1 = formali[1];
			
			if(couId != null && couCount != null  && start != null  && end != null && couId.length == couCount.length ) {
				for (int i = 0; i < couCount.length; i++) {
					ActiveProductRuleDetail ruleDetail = getPoductObjTwo(
							activeId, ruleId, formalization1, couId, couCount,
							start, end, i);
					ruleDetailList.add(ruleDetail);
				}
			}
			if(RULETERM2 == rule.getRuleTerm()) {//阶梯满返
				rule.setRuleTerm(SERIVE_RULEITEM9);
			} else if(RULETERM4 == rule.getRuleTerm()) {//阶梯满赠
				rule.setRuleTerm(SERIVE_RULEITEM3);
			}
			if(RADIO1.equals(formalization1)) {//按数量
				rule.setSendtype(SENDTYPE1);//
			} else if(RADIO2.equals(formalization1)) {
				rule.setSendtype(SENDTYPE2);//
			}
		}
		
		detailGift.put(DETAIL, ruleDetailList);
		detailGift.put(GIFT, giftList);
		return detailGift;
	}
	
	*//**
	 * 普通满返gift
	 * @param activeId
	 * @param ruleId
	 * @param ruleDetailId
	 * @param couId
	 * @param couCount
	 * @param skuNa
	 * @param pna
	 * @param pd
	 * @param i
	 * @param mul
	 * @param rule
	 * @return
	 *//*
	private ActiveProductRuleGift getGift(long activeId, long ruleId, long ruleDetailId, String[] couId, String[] couCount, String[] skuNa, String[] pna, String[] pd, int i, String[] mul, ActiveProductRule rule) {
		
		 ActiveProductRuleGift gift = new ActiveProductRuleGift();
		 gift.setWid(PKUtils.getLongPrimaryKey());
		 gift.setActiveId(activeId);
		 gift.setRuleId(ruleId);
		 gift.setGiftid(Long.parseLong(couId[i]));//优惠券ID
		 gift.setSortNum(Long.parseLong(mul[i]));
		 gift.setRuleDetailId(ruleDetailId);
		 gift.setGiftType(3);
		 gift.setGiftQty(Integer.parseInt(couCount[i]));
		 if(RULETERM2.equals(rule.getRuleTerm())) {
			 
		 } else if(RULETERM4.equals(rule.getRuleTerm())) {
			 gift.setGiftName(skuNa[i]);
			 gift.setpName(pna[i]);
			 gift.setPid(Long.parseLong(pd[i]));
		 }
		return gift;
	}
	
	*//**
	 * 等比满返赠送优惠券
	 * @param activeId
	 * @param ruleId
	 * @param ruleDetailId
	 * @param couId
	 * @param couCount
	 * @param i
	 * @param mul
	 * @return
	 *//*
	private ActiveProductRuleGift getGiftMultiple(long activeId, long ruleId, long ruleDetailId, String[] couId, String[] couCount, int i, String[] mul) {
		 ActiveProductRuleGift gift = new ActiveProductRuleGift();
		 gift.setWid(PKUtils.getLongPrimaryKey());
		 gift.setActiveId(activeId);
		 gift.setRuleId(ruleId);
		 gift.setGiftid(Long.parseLong(couId[i]));//优惠券ID
		 gift.setSortNum(Long.parseLong(mul[i]));
		 gift.setRuleDetailId(ruleDetailId);
		 gift.setGiftQty(Integer.parseInt(couCount[i]));
		 gift.setGiftType(3);
		return gift;
	}
	
	*//**
	 * 等比满赠 赠送单品
	 * @param activeId
	 * @param ruleId
	 * @param ruleDetailId
	 * @param couId
	 * @param couCount
	 * @param i
	 * @param mul
	 * @return
	 *//*
	private ActiveProductRuleGift getGiftMultiple2(long activeId, long ruleId, long ruleDetailId, String[] couId, String[] couCount, int i, String[] mul, String[] pna, String[] skuNa, String[] pd) {
		 ActiveProductRuleGift gift = new ActiveProductRuleGift();
		 gift.setWid(PKUtils.getLongPrimaryKey());
		 gift.setActiveId(activeId);
		 gift.setRuleId(ruleId);
		 gift.setGiftid(Long.parseLong(couId[i]));//优惠券ID
		 gift.setSortNum(Long.parseLong(mul[i]));
		 gift.setRuleDetailId(ruleDetailId);
		 gift.setGiftQty(Integer.parseInt(couCount[i]));
		 gift.setGiftType(3);
		 gift.setGiftName(skuNa[i]);
		 gift.setpName(pna[i]);
		 gift.setPid(Long.parseLong(pd[i]));
		return gift;
	}
	
	*//**
	 * 限时直降
	 * @param activeId
	 * @param ruleId
	 * @param arr
	 * @return
	 *//*
	private List<ActiveProductRuleDetail> getDetailByRuleTerm3(Long activeId, Long ruleId, String[] arr, ActiveProductRule rule) {
		List<ActiveProductRuleDetail> ruleDetailList = new ArrayList<ActiveProductRuleDetail>();
		ActiveProductRuleDetail ruleDetail = new ActiveProductRuleDetail();
		String money = arr[1];
		ruleDetail.setRuledetailId(PKUtils.getLongPrimaryKey());
		ruleDetail.setActiveId(activeId);
		ruleDetail.setRuleId(ruleId);
		String single = arr[0];
		if(RADIO1.equals(single)) {//直降金额
			if(money.contains(".")){
				ruleDetail.setTerm0(money);
			}else{
				ruleDetail.setTerm0(money + MONEY_SUFFIX);
			}
			rule.setRuleTerm(SERIVE_RULEITEM6);//直降金额
		} else if(RADIO2.equals(single)) {//直降百分比
			ruleDetail.setTerm0(money);
			rule.setRuleTerm(SERIVE_RULEITEM7);//直降百分比
		}
		ruleDetail.setTerm1(arr[2]);//限购数量
		ruleDetailList.add(ruleDetail);
		return ruleDetailList;
	}
	
	*//**
	 * 普通满返detail
	 * 普通满赠detail
	 * @param activeId
	 * @param ruleId
	 * @param orderMoney
	 * @param isCount
	 * @return
	 *//*
	private ActiveProductRuleDetail getRuleDetail(Long activeId, Long ruleId,String orderMoney, boolean isCount) {
		ActiveProductRuleDetail ruleDetail = new ActiveProductRuleDetail();
		Long longPrimaryKey = PKUtils.getLongPrimaryKey();
		ruleDetail.setRuledetailId(longPrimaryKey);
		ruleDetail.setActiveId(activeId);
		ruleDetail.setRuleId(ruleId);
		if(isCount) {//是否是按数量赠送
			ruleDetail.setTerm0(orderMoney);
		} else {
			ruleDetail.setTerm0(orderMoney+ MONEY_SUFFIX);
		}
		return ruleDetail;
	}
	
	*//**
	 * 订单中包含单品，且单品金额大于等于， 返回优惠劵(不等比)
	 * @param activeId
	 * @param ruleId
	 * @param formalization1
	 * @param couId
	 * @param couCount
	 * @param start
	 * @param end
	 * @param i
	 * @return
	 *//*
	private ActiveProductRuleDetail getPoductObjTwo(Long activeId, Long ruleId,
			String formalization1, String[] couId, String[] couCount,
			String[] start, String[] end, int i) {
		ActiveProductRuleDetail ruleDetail = new ActiveProductRuleDetail();
		ruleDetail.setRuledetailId(PKUtils.getLongPrimaryKey());
		ruleDetail.setActiveId(activeId);
		ruleDetail.setRuleId(ruleId);
		if(RADIO1.equals(formalization1)) {//按数量
			ruleDetail.setTerm0(start[i]);
			ruleDetail.setTerm1(end[i]);
		} else {//按金额
			if(!start[i].contains(".")){
				ruleDetail.setTerm0(start[i]+ MONEY_SUFFIX);
			} else {
				ruleDetail.setTerm0(start[i]);
			}
			if(!end[i].contains(".")){
				ruleDetail.setTerm1(end[i]+ MONEY_SUFFIX);
			} else {
				ruleDetail.setTerm1(end[i]);
			}
		}
		ruleDetail.setTerm2(couId[i]);
		ruleDetail.setGiftQty(Integer.parseInt(couCount[i]));
		return ruleDetail;
	}
	
	*//**
	 * 阶梯满赠 detail
	 * @param activeId
	 * @param ruleId
	 * @param formalization1
	 * @param couId
	 * @param couCount
	 * @param start
	 * @param end
	 * @param i
	 * @param pna
	 * @param pd
	 * @param skuNa
	 * @return
	 *//*
	private ActiveProductRuleDetail getDetailLadder(Long activeId, Long ruleId,
			String formalization1, String[] couId, String[] couCount,
			String[] start, String[] end, int i, String[] pna, String[] pd, String[] skuNa) {
		ActiveProductRuleDetail ruleDetail = new ActiveProductRuleDetail();
		ruleDetail.setRuledetailId(PKUtils.getLongPrimaryKey());
		ruleDetail.setActiveId(activeId);
		ruleDetail.setRuleId(ruleId);
		if(RADIO1.equals(formalization1)) {//按数量
			ruleDetail.setTerm0(start[i]);
			ruleDetail.setTerm1(end[i]);
		} else {//按金额
			if(!start[i].contains(".")){
				ruleDetail.setTerm0(start[i]+ MONEY_SUFFIX);
			} else {
				ruleDetail.setTerm0(start[i]);
			}
			if(!end[i].contains(".")){
				ruleDetail.setTerm1(end[i]+ MONEY_SUFFIX);
			} else {
				ruleDetail.setTerm1(end[i]);
			}
		}
		ruleDetail.setpName(pna[i]);
		ruleDetail.setPid(Long.parseLong(pd[i]));
		ruleDetail.setGiftName(skuNa[i]);
		ruleDetail.setTerm2(couId[i]);
		ruleDetail.setGiftQty(Integer.parseInt(couCount[i]));
		return ruleDetail;
	}
	
	
	
	
	*//**
	 * 普通满返
	 * @param activeId
	 * @param ruleId
	 * @param formalization1
	 * @param couId
	 * @param couCount
	 * @param start
	 * @param end
	 * @param i
	 * @return
	 *//*
	private ActiveProductRuleDetail geDetailCommonReturn(Long activeId, Long ruleId,
			String formalization1, String[] couId, String[] couCount,
			String[] start, String[] end, int i) {
		
		ActiveProductRuleDetail ruleDetail = new ActiveProductRuleDetail();
		ruleDetail.setRuledetailId(PKUtils.getLongPrimaryKey());
		ruleDetail.setActiveId(activeId);
		ruleDetail.setRuleId(ruleId);
		if(RADIO1.equals(formalization1)) {//按数量
			ruleDetail.setTerm0(start[i]);
			ruleDetail.setTerm1(end[i]);
		} else {//按金额
			if(!start[i].contains(".")){
				ruleDetail.setTerm0(start[i]+ MONEY_SUFFIX);
			} else {
				ruleDetail.setTerm0(start[i]);
			}
			if(!end[i].contains(".")){
				ruleDetail.setTerm1(end[i]+ MONEY_SUFFIX);
			} else {
				ruleDetail.setTerm1(end[i]);
			}
		}
		ruleDetail.setTerm2(couId[i]);
		ruleDetail.setGiftQty(Integer.parseInt(couCount[i]));
		return ruleDetail;
	}
	
	*//**
	 * 返回一个数组
	 * @param arr
	 * @return
	 *//*
	private String[] getArray(String[] arr) {
		if(arr != null && arr.length > 0) {
			String[] split = arr[0].split("\\,");
			return split;
		}
		return null;
	}
	
	
	*//**
	 * 活动条件限制表  存放的是满多少，减多少，或者是满多少赠的条件
	 * @param activeId
	 * @param ruleId
	 * @param term0
	 * @param term1
	 * @return
	 *//*
	private List<ActiveProductRuleDetail> getRuleDetail(Long activeId, Long ruleId, String term0, String term1, String term2, String term3, Integer count) {
		List<ActiveProductRuleDetail> ruleDetailList = new ArrayList<ActiveProductRuleDetail>();
		ActiveProductRuleDetail ruleDetail = new ActiveProductRuleDetail();
		ruleDetail.setRuledetailId(PKUtils.getLongPrimaryKey());
		ruleDetail.setActiveId(activeId);
		ruleDetail.setRuleId(ruleId);
		if(null != term0 && term0.contains(".")){
			ruleDetail.setTerm0(term0);
		}else{
			ruleDetail.setTerm0(term0+ MONEY_SUFFIX);
		}
		ruleDetail.setTerm1(term1);
		if(null != term2 && term2.contains(".")){
			ruleDetail.setTerm2(term2);
		}else{
			ruleDetail.setTerm2(term2 + MONEY_SUFFIX);
		}
		ruleDetail.setTerm3(term3);
		if(count != null) {
			ruleDetail.setGiftQty(count);
		}
		ruleDetailList.add(ruleDetail);
		return ruleDetailList;
	}
	
	private List<ActiveProductRuleDetail> getRuleDetailThree(Long activeId, Long ruleId, String term0, String term1, String term2, String term3, Integer count) {
		List<ActiveProductRuleDetail> ruleDetailList = new ArrayList<ActiveProductRuleDetail>();
		ActiveProductRuleDetail ruleDetail = new ActiveProductRuleDetail();
		ruleDetail.setRuledetailId(PKUtils.getLongPrimaryKey());
		ruleDetail.setActiveId(activeId);
		ruleDetail.setRuleId(ruleId);
		if(term0 != null && term0.contains(".")){
			ruleDetail.setTerm0(term0);
		}else{
			ruleDetail.setTerm0(term0+ MONEY_SUFFIX);
		}
		if(term1 != null && term1.contains(".")){
			ruleDetail.setTerm1(term1);
		}else{
			ruleDetail.setTerm1(term1 + MONEY_SUFFIX);
		}
		if(term2 != null && term2.contains(".")){
			ruleDetail.setTerm2(term2);
		}else{
			ruleDetail.setTerm2(term2 + MONEY_SUFFIX);
		}
		ruleDetail.setTerm3(term3);
		if(count != null) {
			ruleDetail.setGiftQty(count);
		}
		ruleDetailList.add(ruleDetail);
		return ruleDetailList;
	}

	
	private String getValueByArr(String[] array) {
		if(array != null && array.length > 0) {
			String[] split = array[0].split(",");
			return split[0];
		}
		return "";
	}
	
	*//**
	 * 用品类、品牌、单品的参数判断分组
	 * @param category
	 * @param brand
	 * @param commodity
	 * @return
	 *//*
	private Integer getGroup(String[] category, String[] brand, String[] commodity) {
		if(getArrayValue(category) && !getArrayValue(brand) && !getArrayValue(commodity)) {//品类部分
			return GROUP1;
		} else if(!getArrayValue(category) && getArrayValue(brand) && !getArrayValue(commodity)) {
			return GROUP2;
		} else if(!getArrayValue(category) && !getArrayValue(brand) && getArrayValue(commodity)) {
			return GROUP3;
		} else if(getArrayValue(category) && getArrayValue(brand) && !getArrayValue(commodity)) {
			return GROUP4;
		} else if(getArrayValue(category) && !getArrayValue(brand) && getArrayValue(commodity)) {
			return GROUP5;
		} else if(!getArrayValue(category) && getArrayValue(brand) && getArrayValue(commodity)) {
			return GROUP6;
		} else if(getArrayValue(category) && getArrayValue(brand) && getArrayValue(commodity)) {
			return GROUP7;
		} else {
			return GROUP0;
		}
	}
	
	*//**
	 * 判断传入的数组中是否存在值-1,不存在表示部分
	 * @param array
	 * @return 不存在返回true
	 *//*
	private boolean getArrayValue(String[] array) {
		if(array != null && array.length > 0) {
			String[] split = array[0].split(",");
			for (String string : split) {
				if(!"".equals(string) && !ALL.equals(string) ) {
					return true;
				}
			}
		}
		return false;
	}
	
	*//**
	 * 判断数组里是否存在传入的字符串
	 * @param arr
	 * @param nonparticipatorValue
	 * @return 
	 *//*
	private boolean arrayIsExistByValue(String[] arr, String nonparticipatorValue) {
		if(arr != null && arr.length > 0) {
			String[] split = arr[0].split(",");
			for (String string : split) {
				if(nonparticipatorValue.equals(string)) {
					return true;
				}
			}
		}
		return false;
	}
	
	*//**
	 * 获取活动区域
	 * @param arr
	 * @param ruleId
	 * @return
	 *//*
	private List<ActivityArea> getRegion(String[] arr, Long ruleId, String[] nonparticipator) {
		List<ActivityArea> regionList = new ArrayList<ActivityArea>();
		if(arr != null && arr.length >0) {
			String[] split = arr[0].split(",");
			for (String array : split) {
				ActivityArea region = new ActivityArea();
				region.setId(PKUtils.getLongPrimaryKey());
				if(!"".equals(array)) {
					if(ALL.equals(array)) {
						long parseLong = Long.parseLong(array);
						region.setProvinceid(parseLong);
					}else {
						if(PLEASE_SELECT.equals(array)) {
							region.setCityid(ALL_CITY);
						} else {
							long parseLong = Long.parseLong(array);
							region.setCityid(parseLong);
						}
					}
				}
				region.setRuleId(ruleId);
				if(arrayIsExistByValue(nonparticipator, REGION_NONPARTICIPATOR)) {
					region.setIsExclude(IS_EXCLUDE_ONE);
				} else {
					region.setIsExclude(IS_EXCLUDE);
				}
				regionList.add(region);
			}
			return regionList;
		}
		return null;
	}
	
	*//**
	 * 获取访问方式
	 * @param arr
	 * @param ruleId
	 * @return
	 *//*
	private List<ActivityAccessMode> getAccessMethod(String[] arr, Long ruleId, String[] nonparticipator) {
		List<ActivityAccessMode> accessList = new ArrayList<ActivityAccessMode>();
		if(arr != null && arr.length > 0) {
			String[] splitAccess = arr[0].split(",");
			for (String array : splitAccess) {
				if(!"".equals(array)) {
					ActivityAccessMode access = new ActivityAccessMode();
					access.setId(PKUtils.getLongPrimaryKey());
					access.setRuleId(ruleId);
					access.setAccessmodeid(Long.parseLong(array));
					if(arrayIsExistByValue(nonparticipator, ACCESSMETHOD_NONPARTICIPATOR)) {
						access.setIsExclude(IS_EXCLUDE_ONE);
					} else {
						access.setIsExclude(IS_EXCLUDE);
					}
					accessList.add(access);
				}
			}
			return accessList;
		}
		return null;
	}
	
	*//**
	 * 用户等级
	 * @param arr
	 * @param ruleId
	 * @return
	 *//*
	private List<ActivityUserLevel> getActivityUserLevel(String[] arr, Long ruleId ,ActiveProductRule rule) {
		List<ActivityUserLevel> levelList = new ArrayList<ActivityUserLevel>();
		if(arr != null && arr.length > 0) {
			String[] split = arr[0].split(",");
			for (String array : split) {
				if(!"".equals(array)) {
					ActivityUserLevel level = new ActivityUserLevel();
					level.setId(PKUtils.getLongPrimaryKey());
					level.setLevelid(Long.parseLong(array));
					rule.setIsB2c(Short.valueOf(array));
					level.setRuleId(ruleId);
					level.setIsExclude(IS_EXCLUDE);
					levelList.add(level);
				}
			}
			return levelList;
		}
		return null;
	}
	
	*//**
	 * 活动范围限制  存放的是品牌/品类/单品信息
	 * @param arr
	 * @param activeId
	 * @param ruleId
	 * @param conditionType
	 * @return
	 *//*
	private List<ActiveProductConditionDetail> getActiveProductConditionDetailList(String[] category, String[] brand, String[] commodity, String[] nonparticipator, Long activeId, Long ruleId, Integer contentType1, Integer contentType2, Integer contentType3, Integer conditionType) {
		List<ActiveProductConditionDetail> detailList = new ArrayList<ActiveProductConditionDetail>();
		if(category != null && category.length >0) {
			String[] split = category[0].split(",");
			for (String array : split) {
				Long id = PKUtils.getLongPrimaryKey();
				ActiveProductConditionDetail cat = new ActiveProductConditionDetail();
				cat.setConditiondetailId(id);
				cat.setConditionType(conditionType);
				cat.setActiveId(activeId);
				cat.setRuleId(ruleId);
				cat.setContentType(contentType1);
				cat.setConditionContent(array);
				if(arrayIsExistByValue(nonparticipator, CATEGORY_NONPARTICIPATOR)) {
					cat.setIsExclude(EXCLUDE_ONE);
				} else {
					cat.setIsExclude(EXCLUDE);
				}
				detailList.add(cat);
			}
		}
		if(brand != null && brand.length >0) {
			String[] split = brand[0].split(",");
			List<String> list = new ArrayList<String>();
			for(String array : split){//根据主品牌获取子品牌
				//根据主品牌获取子品牌列表
				try {
					List<SubBrand> tempSubBrands = RemoteServiceSingleton.getInstance().getMyBrandService().
							getSubBrandListByPid(array);
					for(SubBrand subBrand : tempSubBrands){//添加子品牌
						list.add(subBrand.getId());
					}
					list.add(array);//添加主品牌
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			for (String array : list) {
				Long id = PKUtils.getLongPrimaryKey();
				ActiveProductConditionDetail bra = new ActiveProductConditionDetail();
				bra.setConditiondetailId(id);
				bra.setConditionType(conditionType);
				bra.setActiveId(activeId);
				bra.setRuleId(ruleId);
				bra.setContentType(contentType2);
				bra.setConditionContent(array);
				if(arrayIsExistByValue(nonparticipator, BRAND_NONPARTICIPATOR)) {
					bra.setIsExclude(EXCLUDE_ONE);
				} else {
					bra.setIsExclude(EXCLUDE);
				}
				detailList.add(bra);
			}
		}
		if(commodity != null && commodity.length >0) {
			String[] split = commodity[0].split(",");
			for (String array : split) {
				Long id = PKUtils.getLongPrimaryKey();
				ActiveProductConditionDetail com = new ActiveProductConditionDetail();
				com.setConditiondetailId(id);
				com.setConditionType(conditionType);
				com.setActiveId(activeId);
				com.setRuleId(ruleId);
				com.setContentType(contentType3);
				com.setConditionContent(array);
				if(arrayIsExistByValue(nonparticipator, COMMODITY_NONPARTICIPATOR)) {
					com.setIsExclude(EXCLUDE_ONE);
				} else {
					com.setIsExclude(EXCLUDE);
				}
				detailList.add(com);
			}
		}
		if(category == null && brand == null && commodity == null) {
			Long id = PKUtils.getLongPrimaryKey();
			ActiveProductConditionDetail com = new ActiveProductConditionDetail();
			com.setConditiondetailId(id);
			com.setConditionType(conditionType);
			com.setActiveId(activeId);
			com.setRuleId(ruleId);
			com.setConditionContent(String.valueOf(conditionType));
			detailList.add(com);
		}
		return detailList;
	}
	
	*//**
	 * 获取渠道
	 * @param arr
	 * @param ruleId
	 * @return
	 *//*
	private List<ActivityChannelsCondition> getActivityChannelsConditions(String[] arr, Long ruleId ,String[] nonparticipator) {
		List<ActivityChannelsCondition> channelList = new ArrayList<ActivityChannelsCondition>();
		if(arr != null && arr.length > 0) {
			String[] split = arr[0].split(",");
			for (String array : split) {
				if(!"".equals(array)) {
					ActivityChannelsCondition channel = new ActivityChannelsCondition();
					channel.setId(PKUtils.getLongPrimaryKey());
					channel.setRuleId(ruleId);
					channel.setChid(Long.parseLong(array));
					if(arrayIsExistByValue(nonparticipator, CHANNEL_NONPARTICIPATOR)) {
						channel.setIsExclude(IS_EXCLUDE_ONE);
					} else {
						channel.setIsExclude(IS_EXCLUDE);
					}
					channelList.add(channel);
				}
			}
		}
		return channelList;
	}
	*//**
	 * 根据优惠券名称，优惠券类型，优惠券起始、结束时间来获取优惠券列表
	 * @param request
	 * @return 优惠券列表
	 *//*
	@RequestMapping(value = "/findCoupons")
	public String findCouponsByCondition(HttpServletRequest request, HttpServletResponse response, Integer page){
		PageBean<CouponInfoDto> pageBean = new PageBean<CouponInfoDto>();
		CouponInfoDto couponInfoDto = new CouponInfoDto();
		String couponName = request.getParameter("coupnName");
		Integer couponType = Integer.parseInt(request.getParameter("couponType"));
		String flag = request.getParameter("flag");
		String couponMember = request.getParameter("couponMember");
		
		pageBean.setPageSize(Constants.PAGESIZE);
		if (page != null && page != 0) {
			pageBean.setPage(page);
		} else {
			pageBean.setPage(DEFAULT_PAGE);
		}
		
		Date startDate = null;
		Date endDate = null;
		SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			String parameter = request.getParameter("startDate");
			if(parameter != null && !"".equals(parameter)) {
				startDate = sdf.parse(parameter);
			}
			String parameter2 = request.getParameter("endDate");
			if(parameter2 != null && !"".equals(parameter2)) {
				endDate = sdf.parse(parameter2);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		couponInfoDto.setCouponName(couponName);
		couponInfoDto.setCouponType(couponType);
		couponInfoDto.setStartDate(startDate);
		couponInfoDto.setEndDate(endDate);
		couponInfoDto.setIsb2c(Integer.parseInt(couponMember));
		pageBean.setParameter(couponInfoDto);
		pageBean = RemoteServiceSingleton.getInstance().getCouponRuleService().getCouponListPage(pageBean);
		String json = JSONArray.fromObject(pageBean).toString();
		request.getSession().setAttribute("pb", pageBean);
		try {
			if(RADIO1.equals(flag)) {
				request.getRequestDispatcher(
						"/WEB-INF/views/activitis/couponsModel.jsp").forward(
						request, response);
			} else if(RADIO2.equals(flag)) {
				request.getRequestDispatcher(
						"/WEB-INF/views/activitis/couponsModelLadder.jsp").forward(
						request, response);
			}
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	*//**
	 * 查询单品信息
	 * @param request
	 * @param response
	 * @return
	 *//*
	@RequestMapping(value = "/findCommodity")
	public String findCommodityByCondition(HttpServletRequest request, HttpServletResponse response, Integer page) {
		PageBean<SkuCodeDTO> pageBean = new PageBean<SkuCodeDTO>();	
		SkuCodeDTO skuCodeDTO = new SkuCodeDTO();
			String categoryId = request.getParameter("categoryId");
			String supplierName = request.getParameter("supplierName");
			String flag = request.getParameter("flag");
			String productId = request.getParameter("productId");
			String productCode = request.getParameter("productCode");
			if(categoryId != null) {
				skuCodeDTO.setCatePubId(categoryId);
			}
			if(supplierName != null) {
				skuCodeDTO.setSupplierName(supplierName);
			}
			pageBean.setPageSize(Constants.PAGESIZE);
			if (page != null && page != 0) {
				pageBean.setPage(page);
			} else {
				pageBean.setPage(DEFAULT_PAGE);
			}
			if(productId != null && !"".equals(productId)) {
				skuCodeDTO.setProductId(Long.parseLong(productId));
			}
			if(productCode != null && !"".equals(productCode)) {
				skuCodeDTO.setBusinessProdId(productCode);
			}
			Short b2cType = Short.parseShort(request.getParameter("b2cType"));
			if(b2cType == 3){
				b2cType = 0;
			}else{
				b2cType = 1;
			}
			skuCodeDTO.setB2cTate(b2cType);
			pageBean.setParameter(skuCodeDTO);
			pageBean = RemoteServiceSingleton.getInstance().getCustomerPromotionService().findPageSkuDto(pageBean);
			request.getSession().setAttribute("pb", pageBean);
			request.getSession().setAttribute("b2cType", b2cType);
			try {
				if(RADIO1.equals(flag)) {
					request.getRequestDispatcher(
							"/WEB-INF/views/activitis/commodityModel1.jsp").forward(
							request, response);
				} else if(RADIO2.equals(flag)) {
					request.getRequestDispatcher(
							"/WEB-INF/views/activitis/commodityModel2.jsp").forward(
							request, response);
				}
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return null;
	}
}
*/