package com.mall.controller.myccigmall;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.mall.annotation.AuthPassport;
import com.mall.constant.RequestContant;
import com.mall.constant.ResponseContant;
import com.mall.controller.base.BaseController;
import com.mall.customer.model.CashierRecord;
import com.mall.customer.model.User;
import com.mall.customer.service.CashierService;
import com.mall.customer.service.SqwAccountRecordService;
import com.mall.customer.service.UserService;
import com.mall.service.TeamService;
import com.mall.vo.TeamVO;
import com.mall.vo.WealthVO;

/**
 * @author cyy
 */
@Controller
@RequestMapping(value = RequestContant.CUS_TEAM)
public class CustomerTeamController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerTeamController.class);

	@Autowired
	private TeamService teamService;
	 @Autowired
	    private SqwAccountRecordService sqwAccountRecordService;
	 @Autowired
	    private CashierService cashierService;
	 @Autowired
	    private UserService userService;
	/**
	 * 我的团队
	 * 
	 * @param model
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_TEAM_INDEX, method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model model) {
		LOGGER.info("team info start...");
		User userInfo = getCurrentUser(request);
		if (userInfo == null) {
			LOGGER.info("userinfo  is null");
			return RequestContant.CUSTOMER_TO_LOGIN;
		}
		
		 //查询团队
		TeamVO team = teamService.index(userInfo);
		model.addAttribute("team", team);
		LOGGER.info("team info  finished.");
		return ResponseContant.CUS_TEAM_INDEX;
	}

	/**
	 * 加载更多详细
	 * 
	 * @param model
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_TEAM_MORE, method = RequestMethod.GET)
	@ResponseBody
	public String more(HttpServletRequest request, Integer type) {
		User userInfo = getCurrentUser(request);
		if (userInfo == null) {
			LOGGER.info("userinfo  is null");
			return RequestContant.CUSTOMER_TO_LOGIN;
		}
		TeamVO team = teamService.more(request, userInfo, type);
		return JSON.toJSONString(team);
	}
}
