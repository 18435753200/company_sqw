/*package com.mall.controller.promotions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.mall.mybatis.utility.PageBean;
import com.mall.promotion.api.activity.web.ActiveGiftApi;
import com.mall.promotion.api.activity.web.ActiveMasterApi;
import com.mall.promotion.api.activity.web.PlatformRuleApi;
import com.mall.promotion.dto.activity.ActiveMasterDto;
import com.mall.promotion.dto.activity.RuleDto;
import com.mall.promotion.po.activity.ActiveGift;
import com.mall.promotion.po.activity.ActiveMaster;
import com.mall.promotion.po.activity.ActivePlatformRuleB2C;
import com.mall.promotion.po.activity.PlatformRule;
import com.mall.promotion.utils.ActivityConstants;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;

@Controller
@RequestMapping(value = "/activity")
public class ActivityController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ActivityController.class);
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
	 * 跳转到活动列表
	 * 
	 * @return
	 *//*
	@RequestMapping(value = "/toActivityList")
	public String toActivityList() {
		LOGGER.info("to Activity List Page!");
		return "promotions/activity/activityList";
	}

	*//**
	 * 跳转到创建、编辑活动页面 （暂时只创建）
	 * 
	 * @return
	 *//*
	@RequestMapping(value = "/toActivityEdit")
	public String toActivityEdit(ActiveMaster activeMaster, Model model) {
		if (activeMaster != null && activeMaster.getActiveId() != null) {
			ActiveMasterApi activeMasterApi = RemoteServiceSingleton.getInstance().getActiveMasterApi();
			ActiveMaster viewActiveMaster = activeMasterApi.viewActiveMaster(activeMaster.getActiveId());
			model.addAttribute("bean", viewActiveMaster);
		}
		return "promotions/activity/activityEdit";
	}

	*//**
	 * 查看活动信息
	 * 
	 * @return
	 *//*
	@RequestMapping(value = "/toActivityView")
	public String toActivityView(Long activeId, Model model) {
		try {
			ActiveMaster viewActiveMaster = RemoteServiceSingleton.getInstance().getActiveMasterApi().viewActiveMaster(activeId);
			model.addAttribute("bean", viewActiveMaster);

			return "promotions/activity/activityView";
		} catch (Exception e) {
			LOGGER.error("查询活动失败" + e.getMessage(), e);
		}
		return null;
	}

	*//**
	 * 创建、编辑活动（暂时只有创建功能）
	 * 
	 * @return
	 *//*
	@RequestMapping(value = "/activityEdit")
	@ResponseBody
	public String activityEdit(HttpServletRequest request, ActiveMaster activeMaster) {
		String returnFlag = SUC_FLAG;
		try {
			// 校验页面元素
			returnFlag = checkCreateActiveMasterInfo(activeMaster);
			if (SUC_FLAG.equals(returnFlag)) {
				activeMaster.setStatus((short) 2);
				ActiveMasterApi activeMasterApi = RemoteServiceSingleton.getInstance().getActiveMasterApi();
				activeMasterApi.createActivity(activeMaster);
			}

		} catch (Exception e) {
			LOGGER.error("创建/编辑活动失败" + e.getMessage(), e);
			returnFlag = FAIL_FLAG;
		}
		return returnFlag;
	}

	*//**
	 * 查询活动列表
	 * 
	 * @param pageBean
	 * 
	 * @return
	 *//*
	@RequestMapping(value = "/findActivityByCondition")
	public String findActivityByCondition(HttpServletRequest request, HttpServletResponse response, Integer page, ActiveMasterDto param, Model model) {
		try {
			LOGGER.info("to ActivityList Page By Condition!");
			PageBean<ActiveMaster> pageBean = new PageBean<ActiveMaster>();
			ActiveMasterDto activeMasterDto = new ActiveMasterDto();
			activeMasterDto.setActivityName(param.getActivityName());
			activeMasterDto.setActivityTime(param.getActivityTime());
			pageBean.setParameter(activeMasterDto);
			pageBean.setPageSize(Constants.PAGESIZE);

			if (page != null && page != 0) {
				pageBean.setPage(page);
			} else {
				pageBean.setPage(1);
			}
			ActiveMasterApi activeMasterApi = RemoteServiceSingleton.getInstance().getActiveMasterApi();
			pageBean = activeMasterApi.findActiveMasterByCondition(pageBean);

			request.getSession().setAttribute("pb", pageBean);
			request.getRequestDispatcher("/WEB-INF/views/promotions/activity/modelpage/activity_model.jsp").forward(request, response);
		} catch (Exception e) {
			LOGGER.error("查询活动列表异常：" + e.getMessage(), e);
		}
		return null;
	}

	*//**
	 * 停用或启用活动
	 * 
	 * @return
	 *//*
	@RequestMapping(value = "/stopOrEnableActivity")
	@ResponseBody
	public String stopOrEnableActivity(ActiveMaster activeMaster,HttpServletRequest request) {
		String returnFlag = SUC_FLAG;
		try {
			RuleDto ruleDto = new RuleDto();
			ruleDto.setActiveId(activeMaster.getActiveId());
			// 活动启用，判断活动下的规则
			//if (activeMaster.getStatus() == 1) {
				PlatformRuleApi platformRuleApi = RemoteServiceSingleton.getInstance().getPlatformRuleApi();
				PageBean<PlatformRule> pageBean = new PageBean<PlatformRule>();
				pageBean.setParameter(ruleDto);
				pageBean = platformRuleApi.findActiveMasterByCondition(pageBean);
				List<PlatformRule> result = pageBean.getResult();
				if (result.isEmpty() || result == null) {
					return "不能启用活动，最少得有一个已启用的规则";
				}
				boolean ruleFlat = false;
				for (PlatformRule platformRule : result) {
					if (platformRule.getStatus() == 1) {
						ruleFlat = true;
						break;
					}
				}
				if (!ruleFlat) {
					return "不能启用活动，最少得有一个已启用的规则";
				}
				ActiveMasterApi activeMasterApi = RemoteServiceSingleton.getInstance().getActiveMasterApi();
				activeMasterApi.stopOrEnableActiveMaster(activeMaster.getActiveId(), activeMaster.getStatus());
				//}
		} catch (Exception e) {
			LOGGER.error("启用停用异常:" + e.getMessage(), e);
			returnFlag = FAIL_FLAG;
		}
		return returnFlag;
	}
	
	
	*//**
	 * 删除活动（假删除）
	 * 
	 * @return
	 *//*
	@RequestMapping(value = "/deleteActivity")
	public String deleteActivity(ActiveMaster activeMaster) {
		try {
			ActiveMasterApi activeMasterApi = RemoteServiceSingleton.getInstance().getActiveMasterApi();
			ActiveMaster viewActiveMaster = activeMasterApi.viewActiveMaster(activeMaster.getActiveId());
			if (!viewActiveMaster.getStatus().equals(ActivityConstants.STOP_SUATUS) || viewActiveMaster.getStatus().equals(ActivityConstants.DELETE_SUATUS)) {
				LOGGER.error("删除失败: 只有停用状态的活动才能删除");
				return "redirect:/activity/toActivityList";
			}

			RuleDto ruleDto = new RuleDto();
			ruleDto.setActiveId(activeMaster.getActiveId());
			PageBean<PlatformRule> pageBean = new PageBean<PlatformRule>();
			pageBean.setParameter(ruleDto);

			PlatformRuleApi platformRuleApi = RemoteServiceSingleton.getInstance().getPlatformRuleApi();
			pageBean = RemoteServiceSingleton.getInstance().getPlatformRuleApi().findActiveMasterByCondition(pageBean);
			List<PlatformRule> result = pageBean.getResult();
			if (result != null) {
				ActiveGiftApi activeGiftApi = RemoteServiceSingleton.getInstance().getActiveGiftApi();
				for (PlatformRule platformRule : result) {
					List<ActiveGift> giftList = activeGiftApi.findGiftByRuleId(platformRule.getRuleId());
					if (giftList != null) {
						for (ActiveGift activeGift : giftList) {
							activeGiftApi.deleteActiveGift(activeGift.getGiftId());
						}
					}
					platformRuleApi.deletePlatformRule(platformRule.getRuleId());
				}

			}
			activeMasterApi.deleteActiveMaster(activeMaster.getActiveId());
			return "redirect:/activity/toActivityList";
		} catch (Exception e) {
			LOGGER.error("删除异常:" + e.getMessage(), e);
		}
		return null;

	}

	*//**
	 * 校验页面填写元素是否符合规范
	 * 
	 * @param activeMaster
	 * @return
	 *//*
	private String checkCreateActiveMasterInfo(ActiveMaster activeMaster) {
		String msg = SUC_FLAG;

		// 校验名称
		if (StringUtils.isNotBlank(activeMaster.getActiveName())) {
			if (activeMaster.getActiveName().length() > 100) {
				return "名称长度不能大于100！";
			}
		} else {
			return "名称不能为空！";
		}

		// 校验开始时间与结束时间
		if (activeMaster.getExpiringFrom() == null) {
			return "开始时间不能为空！";
		}
		if (activeMaster.getExpiringFrom().compareTo(new Date()) == -1) {
			return "开始时间不能小于系统当前时间！";
		}
		if (activeMaster.getExpiringTo() == null) {
			return "结束时间不能为空！";
		}
		if (activeMaster.getExpiringTo().compareTo(activeMaster.getExpiringFrom()) <= 0) {
			return "结束时间应该大于开始时间！";
		}
		// 描述长度校验
		if (StringUtils.isNotBlank(activeMaster.getDescription()) && activeMaster.getDescription().length() > 200) {
			return "券用途不能大于200字！";
		}
		// 广告语长度校验
		if (StringUtils.isNotBlank(activeMaster.getActiveMsg()) && activeMaster.getActiveMsg().length() > 200) {
			return "广告语不能大于200字！";
		}

		return msg;
	}

}
*/