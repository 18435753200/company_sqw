/*package com.mall.controller.promotions;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.mybatis.utility.PageBean;
import com.mall.promotion.api.activity.web.ActiveMasterApi;
import com.mall.promotion.dto.activity.ActiveMasterDto;
import com.mall.promotion.po.activity.ActiveMaster;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;

@Controller
@RequestMapping(value = "/activeproduct")
public class ActiveProductController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ActiveProductController.class);
	private static final String SUC_FLAG = "success";
	private static final String FAIL_FLAG = "error";
	
	private static final String ACTIVE_PRODUCT  = "1";
	private static final Short  ACTIVE_STATUS_ON = 0;
	
	*//**
	 * 跳转到商品活动创建页面
	 * 
	 * @return
	 *//*
	@RequestMapping(value = "/add")
	public String toActivityEdit(HttpServletRequest request,HttpServletResponse response, Model model) {
		
		return "promotions/activity/activeproductadd";
	}
	
	
	*//**
	 * 保存商品活动
	 * 
	 * @return
	 *//*
	@RequestMapping(value = "/save")
	@ResponseBody
	public String activityEdit(HttpServletRequest request, ActiveMaster activeMaster) {
		String returnFlag = SUC_FLAG;
		try {
			// 校验页面元素
			returnFlag = checkCreateActiveMasterInfo(activeMaster);
			if (SUC_FLAG.equals(returnFlag)) {
				//设置类型为商品活动
				activeMaster.setMasterType(ACTIVE_PRODUCT);
				
				//设置开始时间和结束时间
				activeMaster.setExpiringFrom(new Date());
				activeMaster.setExpiringTo(new Date());
				
				activeMaster.setStatus(ACTIVE_STATUS_ON);// 0 启用
				//调用
				ActiveMasterApi activeMasterApi = RemoteServiceSingleton.getInstance().getActiveMasterApi();
				activeMasterApi.createProductActivity(activeMaster);
				
			}

		} catch (Exception e) {
			LOGGER.error("创建活动失败" + e.getMessage(), e);
			returnFlag = FAIL_FLAG;
		}
		return returnFlag;
	}
	
	
	//校验页面填写元素是否符合规范
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
	
	
	
	*//**
	 * 跳转到商品活动列表页
	 * 
	 * @return
	 *//*
	@RequestMapping(value = "/list")
	public String list() {
		LOGGER.info("跳转到商品活动列表页");
		return "promotions/activity/activeproductlist";
	}
	
	*//**
	 * 查询商品活动列表
	 * @param request
	 * @param response
	 * @param page
	 * @param param
	 * @param model
	 * @return
	 *//*
	@RequestMapping(value = "/findActivityproductByCondition")
	public String findActivityByCondition(HttpServletRequest request, HttpServletResponse response, Integer page, ActiveMasterDto param, Model model) {
		try {
			LOGGER.info("调用查询商品活动列表controller");
			PageBean<ActiveMaster> pageBean = new PageBean<ActiveMaster>();
			ActiveMaster activeMaster = new ActiveMaster();
			activeMaster.setActiveName(param.getActivityName());
			
			pageBean.setParameter(activeMaster);
			pageBean.setPageSize(Constants.PAGESIZE);

			if (page != null && page != 0) {
				pageBean.setPage(page);
			} else {
				pageBean.setPage(1);
			}
			//调用接口查询商品活动列表
			ActiveMasterApi activeMasterApi = RemoteServiceSingleton.getInstance().getActiveMasterApi();
			pageBean = activeMasterApi.findActiveProductMasterByCondition(pageBean);
			
			

			request.getSession().setAttribute("pb", pageBean);
			request.getRequestDispatcher("/WEB-INF/views/promotions/activity/modelpage/activityproduct_model.jsp").forward(request, response);
		} catch (Exception e) {
			LOGGER.error("查询活动列表异常：" + e.getMessage(), e);
		}
		return null;
	}
	
	*//**
	 * 查看商品活动信息
	 * 
	 * @return
	 *//*
	@RequestMapping(value = "/activityproductview")
	public String toActivityProductView(Long activeId, Model model) {
		LOGGER.info("查看商品活动详情 activityproductview  controller");
		try {
			ActiveMaster viewActiveMaster = RemoteServiceSingleton.getInstance().getActiveMasterApi().viewActiveMaster(activeId);
			model.addAttribute("bean", viewActiveMaster);

			return "promotions/activity/activityproductView";
		} catch (Exception e) {
			LOGGER.error("查询活动失败" + e.getMessage(), e);
		}
		return null;
	}
	

}
*/