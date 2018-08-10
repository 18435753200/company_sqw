/*package com.mall.controller.promotions;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.mall.category.po.SysOperateLog;
import com.mall.customer.order.utils.PKUtils;
import com.mall.mybatis.utility.PageBean;
import com.mall.promotion.api.activity.web.ActiveGiftApi;
import com.mall.promotion.api.activity.web.ActiveMasterApi;
import com.mall.promotion.api.activity.web.PlatformRuleApi;
import com.mall.promotion.api.coupon.user.UserCouponRuleApi;
import com.mall.promotion.dto.activity.RuleDto;
import com.mall.promotion.dto.b2c.UserTriggerDto;
import com.mall.promotion.po.activity.ActiveGift;
import com.mall.promotion.po.activity.ActiveGiftB2C;
import com.mall.promotion.po.activity.ActiveMaster;
import com.mall.promotion.po.activity.ActivePlatformRuleB2C;
import com.mall.promotion.po.activity.ActivePlatformRuleDetails;
import com.mall.promotion.po.activity.Channels;
import com.mall.promotion.po.activity.ChannelsCondition;
import com.mall.promotion.po.activity.PlatformRule;
import com.mall.promotion.po.coupon.CouponRule;
import com.mall.promotion.utils.ActivityConstants;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;

@Controller
@RequestMapping(value = "/platformRule")
public class PlatformRuleController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlatformRuleController.class);

	private static final String SUC_FLAG = "success";
	private static final String FAIL_FLAG = "error";

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
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	*//**
	 * 跳转到某活动的规则列表页
	 * 
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 *//*
	@RequestMapping(value = "/toRuleList")
	public String toRuleList(HttpServletRequest request, HttpServletResponse response, Long activeId, Long ruleId, Model model) {
		try {
			LOGGER.info("toRuleList");
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
		return "promotions/activity/ruleList";

	}

	*//**
	 * 查询活动下的规则列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @param platformRuleDto
	 * @return
	 *//*
	@RequestMapping(value = "/findRuleByCondition")
	public String findRuleByCondition(HttpServletRequest request, HttpServletResponse response, Integer page, RuleDto ruleDto) {
		try {
			LOGGER.info("to Rule Page By Condition!");
			PageBean<PlatformRule> pageBean = new PageBean<PlatformRule>();
			if (page != null && page != 0) {
				pageBean.setPage(page);
			} else {
				pageBean.setPage(1);
			}
			pageBean.setPageSize(Constants.PAGESIZE);
			pageBean.setParameter(ruleDto);
			// 调用服务代码
			PlatformRuleApi platformRuleApi = RemoteServiceSingleton.getInstance().getPlatformRuleApi();
			pageBean = platformRuleApi.findActiveMasterByCondition(pageBean);

			request.getSession().setAttribute("pb", pageBean);
			request.getRequestDispatcher("/WEB-INF/views/promotions/activity/modelpage/rules_model.jsp").forward(request, response);
		} catch (Exception e) {
			LOGGER.error("查询活动列表异常：" + e.getMessage(), e);
		}
		return null;
	}

	*//**
	 * 展示规则信息
	 * 
	 * @param platformRule
	 * @param model
	 * @return
	 *//*
	@RequestMapping(value = "/toShowRule")
	public String toShowRule(PlatformRule platformRule, Model model,Integer pType) {
		try {
			LOGGER.info("to platformRuleView page");
			if(pType == 0){
				platformRule = RemoteServiceSingleton.getInstance().getPlatformRuleApi().findPlatFormRuleByPFId(platformRule.getRuleId());
				model.addAttribute("bean", platformRule);
			}else if(pType == 1){
				ActivePlatformRuleB2C activePlatformRuleB2C = new ActivePlatformRuleB2C();
				activePlatformRuleB2C = RemoteServiceSingleton.getInstance().getPlatformRuleApi().findB2CPlatformRuleByRuleId(platformRule.getRuleId());
				model.addAttribute("bean", activePlatformRuleB2C);
			}
		} catch (Exception e) {
			LOGGER.error("查询活动规则失败：" + e.getMessage(), e);
		}
		return "promotions/activity/platformRuleView";
	}

	 =========== 

	*//**
	 * 跳转到平台规则编辑页
	 * 
	 * @return
	 *//*
	@RequestMapping(value = "/toPlatformRuleEdit")
	public String toPlatformRuleEdit(Long activeId, Model model) {
		try {
			LOGGER.info("to PlatformRuleEdit Page!");
			ActiveMasterApi activeMasterApi = RemoteServiceSingleton.getInstance().getActiveMasterApi();
			ActiveMaster activeMaster = activeMasterApi.viewActiveMaster(activeId);
			if (activeMaster.getStatus() == 1 || activeMaster.getStatus() == 4) {// 活动过期、活动正在启用时，不能创建修改规则
				return "redirect:/activity/toRuleList?activeId=" + activeId;
			}
			model.addAttribute("activeId", activeId);
			return "promotions/activity/platformRuleEdit";
		} catch (Exception e) {
			LOGGER.error("跳转到平台规则编辑页失败：" + e.getMessage(), e);
		}
		return "redirect:/activity/toRuleList?activeId=" + activeId;
	}
	
	*//**
	 * 跳转到平台规则编辑页
	 * 
	 * @return
	 *//*
	@RequestMapping(value = "/toPlatformRuleB2CEdit")
	public String toPlatformRuleB2CEdit(Long activeId, Model model) {
		try {
			LOGGER.info("to toPlatformRuleB2CEdit Page!");
			ActiveMasterApi activeMasterApi = RemoteServiceSingleton.getInstance().getActiveMasterApi();
			ActiveMaster activeMaster = activeMasterApi.viewActiveMaster(activeId);
			if (activeMaster.getStatus() == 1 || activeMaster.getStatus() == 4) {// 活动过期、活动正在启用时，不能创建修改规则
				return "redirect:/activity/toRuleList?activeId=" + activeId;
			}
			model.addAttribute("activeId", activeId);
			return "promotions/activity/platformRuleB2CEdit";
		} catch (Exception e) {
			LOGGER.error("跳转到平台规则编辑页失败：" + e.getMessage(), e);
		}
		return "redirect:/activity/toRuleList?activeId=" + activeId;
	}

	*//**
	 * 创建、编辑平台规则
	 * 
	 * @return
	 *//*
	@RequestMapping(value = "/editPlatformRule")
	@ResponseBody
	public String editPlatformRule(PlatformRule platformRule,ActivePlatformRuleDetails activePlatformRuleDetails) {
		String returnFlag = SUC_FLAG;
		try {
			ActiveMasterApi activeMasterApi = RemoteServiceSingleton.getInstance().getActiveMasterApi();
			ActiveMaster activeMaster = activeMasterApi.viewActiveMaster(platformRule.getActiveId());
			if (activeMaster.getStatus() == 1 || activeMaster.getStatus() == 4) {// 活动过期、活动正在启用时，不能创建或修改规则
				returnFlag = FAIL_FLAG;
				return returnFlag;
			}
			// 是否已包含注册送
			PlatformRuleApi platformRuleApi = RemoteServiceSingleton.getInstance().getPlatformRuleApi();
			PageBean<PlatformRule> pageBean = new PageBean<PlatformRule>();
			RuleDto ruleDto = new RuleDto();
			ruleDto.setActiveId(platformRule.getActiveId());
			pageBean.setParameter(ruleDto);
			pageBean = platformRuleApi.findActiveMasterByCondition(pageBean);
			List<PlatformRule> result = pageBean.getResult();
			
			if (result.size() > 0) {
				returnFlag = "一个活动只能有一个注册送的规则";
				return returnFlag;
			}
			

			String msg = checkCreateRuleInfo(platformRule); // 校验页面填写元素是否符合规范
			if (returnFlag.equals(msg)) {

				if (platformRule != null && platformRule.getRuleId() != null) { // 修改平台规则
					platformRuleApi.updatePlatformRule(platformRule);
				} else { // 创建平台规则
					platformRuleApi.createPlatformRule(platformRule,activePlatformRuleDetails);
				}
			} else {
				returnFlag = msg;
			}

		} catch (Exception e) {
			LOGGER.error("创建/编辑平台规则异常：" + e.getMessage(), e);
			returnFlag = FAIL_FLAG;
		}
		return returnFlag;
	}
	
	*//**
	 * 创建、编辑平台规则
	 * 
	 * @return
	 *//*
	@RequestMapping(value = "/editPlatformRuleB2C")
	@ResponseBody
	public String editPlatformRuleB2C(HttpServletRequest request,ActivePlatformRuleB2C activePlatformRuleB2C) {
		//渠道和礼品
		String[] channels = request.getParameterValues("channels");
		String[] split1 = channels[0].split(",");
		String[] gifts = request.getParameterValues("gifts");
		String[] split2 = gifts[0].split(",");
		
		
		String returnFlag = SUC_FLAG;
		try {
			ActiveMasterApi activeMasterApi = RemoteServiceSingleton.getInstance().getActiveMasterApi();
			ActiveMaster activeMaster = activeMasterApi.viewActiveMaster(activePlatformRuleB2C.getActiveId());
			if (activeMaster.getStatus() == 1 || activeMaster.getStatus() == 4) {// 活动过期、活动正在启用时，不能创建或修改规则
				returnFlag = FAIL_FLAG;
				return returnFlag;
			}
			// 是否已包含注册送
			PlatformRuleApi platformRuleApi = RemoteServiceSingleton.getInstance().getPlatformRuleApi();
			PageBean<PlatformRule> pageBean = new PageBean<PlatformRule>();
			RuleDto ruleDto = new RuleDto();
			ruleDto.setActiveId(activePlatformRuleB2C.getActiveId());
			pageBean.setParameter(ruleDto);
			pageBean = platformRuleApi.findActiveMasterByCondition(pageBean);
			List<PlatformRule> result = pageBean.getResult();
			if (result.size() > 0) {
				returnFlag = "一个活动只能有一个注册送的规则";
				return returnFlag;
			}

			String msg = checkCreateRuleInfo1(activePlatformRuleB2C); // 校验页面填写元素是否符合规范
			if (returnFlag.equals(msg)) {
				// 创建平台规则
				activePlatformRuleB2C.setStatus((short) 0);
				activePlatformRuleB2C.setAuditStatus((short) 0);
				
				List<ChannelsCondition> channelConditions = null;
				if(101 == activePlatformRuleB2C.getTriggerCondition()){//注册渠道送
					
					if(101 == activePlatformRuleB2C.getTriggerCondition()){//为渠道注册送时
						boolean channelError= false;
						for (int i = 0; i < split1.length; i++) {
							if(StringUtils.isEmpty(split1[i])){
								channelError = true;
							}
						}
						if(channelError){
							returnFlag = "请选择渠道";
							return returnFlag;
						}
						
						boolean giftsError = false;
						for (int i = 0; i < split2.length; i++) {
							if(StringUtils.isEmpty(split2[i])){
								giftsError = true;
							}
						}
						
						if(giftsError){
							returnFlag = "请选择赠品";
							return returnFlag;
						}
						if(split1.length != split2.length){
							returnFlag = "渠道和赠品不对应";
							return returnFlag;
						}
						channelConditions = new ArrayList<ChannelsCondition>();
						for (int i = 0; i < split1.length; i++) {
							ChannelsCondition cc = new ChannelsCondition();
							cc.setChannelCode(new Integer(split1[i]));
							cc.setGift(split2[i]);
							cc.setId(PKUtils.getLongPrimaryKey());
							cc.setRuleId(activePlatformRuleB2C.getActiveId());
							channelConditions.add(cc);
						}
						
					}
					
				}
				
				platformRuleApi.createPlatformRuleB2C(activePlatformRuleB2C,channelConditions);
			
			} else {
				returnFlag = msg;
			}

		} catch (Exception e) {
			LOGGER.error("创建/编辑平台规则异常：" + e.getMessage(), e);
			returnFlag = FAIL_FLAG;
		}
		return returnFlag;
	}
	
	
	
	*//**
	 * 停用或启用平台规则
	 * 
	 * @return
	 *//*
	@RequestMapping(value = "/stopOrEnableRule")
	@ResponseBody
	public String stopOrEnableRule(PlatformRule platformRule,HttpServletRequest request) {
		String returnFlag = SUC_FLAG;
		Integer pType = Integer.parseInt(request.getParameter("pType"));
		LOGGER.info("stopOrEnableRule   pType："+pType);
		try {
			if(pType == 0){//启停B2B规则
				if (platformRule.getStatus() == 1) { // 如果是启用规则操作
					ActiveGiftApi activeGiftApi = RemoteServiceSingleton.getInstance().getActiveGiftApi();
					List<ActiveGift> giftList = activeGiftApi.findGiftByRuleId(platformRule.getRuleId());
					if (giftList == null || giftList.isEmpty()) {
						return "不能启用活动规则，必须先绑定赠品";
					}
				}
	
				PlatformRuleApi platformRuleApi = RemoteServiceSingleton.getInstance().getPlatformRuleApi();
				platformRuleApi.stopOrEnablePlatformRule(platformRule.getRuleId(), platformRule.getStatus(),pType);
			}else if(pType == 1){//启停B2C规则
				if (platformRule.getStatus() == 1) { // 如果是启用规则操作
					ActiveGiftApi activeGiftApi = RemoteServiceSingleton.getInstance().getActiveGiftApi();
					List<ActiveGiftB2C> giftList = activeGiftApi.findAllGiftByRuleId(platformRule.getRuleId());
					if (giftList == null || giftList.isEmpty()) {
						return "不能启用活动规则，必须先绑定赠品";
					}
				}
	
				PlatformRuleApi platformRuleApi = RemoteServiceSingleton.getInstance().getPlatformRuleApi();
				platformRuleApi.stopOrEnablePlatformRule(platformRule.getRuleId(), platformRule.getStatus(),pType);
			}
		} catch (Exception e) {
			LOGGER.error("停用或启用平台规则异常：" + e.getMessage(), e);
			returnFlag = FAIL_FLAG;
		}
		return returnFlag;
	}

	*//**
	 * 删除规则（假删除）
	 * 
	 * @return
	 *//*
	@RequestMapping(value = "/deleteRule")
	@ResponseBody
	public String deleteRule(PlatformRule platformRule) {
		String returnFlag = SUC_FLAG;
		try {
			PlatformRuleApi platformRuleApi = RemoteServiceSingleton.getInstance().getPlatformRuleApi();
			PlatformRule pfRule = platformRuleApi.findPlatFormRuleByPFId(platformRule.getRuleId());
			if (pfRule.getStatus() == 1) {
				LOGGER.info("删除规则失败：启用中的规则不能删除");
				returnFlag = FAIL_FLAG;
			} else {
				ActiveGiftApi activeGiftApi = RemoteServiceSingleton.getInstance().getActiveGiftApi();
				List<ActiveGift> gList = activeGiftApi.findGiftByRuleId(platformRule.getRuleId());
				if (gList != null) {// 如果要删除的规则有赠品，先解绑赠品
					for (ActiveGift activeGift : gList) {
						activeGiftApi.deleteActiveGift(activeGift.getGiftId());
					}
				}
				RemoteServiceSingleton.getInstance().getPlatformRuleApi().deletePlatformRule(platformRule.getRuleId());
			}

		} catch (Exception e) {
			LOGGER.error("删除规则异常：" + e.getMessage(), e);
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
	private String checkCreateRuleInfo(PlatformRule platformRule) {
		String msg = SUC_FLAG;

		// 校验名称
		if (StringUtils.isNotBlank(platformRule.getRuleName())) {
			if (platformRule.getRuleName().length() > 100) {
				return "规则名称长度不能大于100！";
			}
		} else {
			return "规则名称不能为空！";
		}

		// 校验名称
		if (StringUtils.isNotBlank(platformRule.getDescription())) {
			if (platformRule.getDescription().length() > 200) {
				return "规则描述长度不能大于200！";
			}
		} else {
			return "规则描述不能为空！";
		}

		return msg;
	}
	*//**
	 * 校验页面填写元素是否符合规范
	 * 
	 * @param couponRule
	 * @return
	 *//*
	private String checkCreateRuleInfo1(ActivePlatformRuleB2C activePlatformRuleB2C) {
		String msg = SUC_FLAG;

		// 校验名称
		if (StringUtils.isNotBlank(activePlatformRuleB2C.getRuleName())) {
			if (activePlatformRuleB2C.getRuleName().length() > 100) {
				return "规则名称长度不能大于100！";
			}
		} else {
			return "规则名称不能为空！";
		}

		// 校验名称
		if (StringUtils.isNotBlank((String) activePlatformRuleB2C.getDescription())) {
			if (((String) activePlatformRuleB2C.getDescription()).length() > 200) {
				return "规则描述长度不能大于200！";
			}
		} else {
			return "规则描述不能为空！";
		}

		return msg;
	}
	
	*//**
	 * 获取下拉时的渠道
	 * @param request
	 * @return
	 *//*
	@RequestMapping(value = "/getChannels")
	@ResponseBody
	public String getChannels(HttpServletRequest request){
		PlatformRuleApi platformRuleApi = RemoteServiceSingleton.getInstance().getPlatformRuleApi();
		List<Channels>  channelList = platformRuleApi.findAllChannels();
		String json = JSONArray.fromObject(channelList).toString();
		return json;
	}
	
	*//**
	 * 获取下拉时的赠品
	 * @param request
	 * @return
	 *//*
	@RequestMapping(value = "/getgifts")
	@ResponseBody
	public String getGifts(HttpServletRequest request){
		UserCouponRuleApi userCouponRuleApi = RemoteServiceSingleton.getInstance().getUserCouponRuleApi();
		List<CouponRule>  couponRuleList = userCouponRuleApi.getB2CCouponRuleList(1);// 1 金券（优惠券）
		for (CouponRule couponRule : couponRuleList) {
			couponRule.setMainrulename(""+couponRule.getCouponruleid());
		}
		String json = JSONArray.fromObject(couponRuleList).toString();
		return json;
	}

}
*/