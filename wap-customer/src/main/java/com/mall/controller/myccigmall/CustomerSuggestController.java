/**
 * 
 */
package com.mall.controller.myccigmall;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.mall.annotation.AuthPassport;
import com.mall.check.SuggestChecks.addSuggestChecks;
import com.mall.constant.CommonConstant;
import com.mall.constant.RequestContant;
import com.mall.constant.ResponseContant;
import com.mall.controller.base.BaseController;
import com.mall.controller.impl.CustomerSuggestControllerImpl;
import com.mall.customer.model.User;
import com.mall.platform.model.PlatformComplaint;
import com.mall.platform.model.findPlatformManageDTO;
import com.mall.utils.CommonUtil;
import com.mall.vo.CustomerSuggestVO;

/**
 * @author zhengqiang.shi
 * 2015年5月12日 上午11:51:16
 */
@Controller
@RequestMapping(value=RequestContant.CUS_SUGGEST)
public class CustomerSuggestController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(CustomerSuggestController.class);
	
	@Autowired
	private CustomerSuggestControllerImpl customerSuggestControllerImpl;
	
	/**
	 *前往帮助中心
	 * @param model
	 * @return
	 */
	@RequestMapping(value=RequestContant.CUS_TOHELPCENTER)
	public String toHelpCenter(Model model) {
		
		return ResponseContant.CUS_HELP_CENTER;
	}
	
	/**
	 * 问题反馈页面
	 * 初始化反馈类型
	 * @param model
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value=RequestContant.CUS_SUGGEST_INIT,method=RequestMethod.GET)
	public String toSuggest(Model model){
		
		
		
		
		return ResponseContant.CUS_SUGGEST;
	}
	
	/**
	 * 提交问题反馈页面
	 * 初始化反馈类型
	 * @param model
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value=RequestContant.CUS_SUGGEST_INITSUGGEST,method=RequestMethod.GET)
	public String toAddSuggest(Model model){
		
		log.info("toAddSuggest start...");
		
			model.addAttribute("suggestTypeMap", CommonUtil.getSuggestType());
		
		log.info("toAddSuggest finished.");
		
		return ResponseContant.CUS_ADD_SUGGEST; 
	}
	
	
	/**
	 * 提交问题反馈
	 * @param model
	 * @param request
	 * @param customerSuggestVO
	 * @param br
	 * @return
	 */
	@AuthPassport
	@ResponseBody
	@RequestMapping(value=RequestContant.CUS_SUGGEST_ADD,method=RequestMethod.POST)
	public String addSuggest(Model model,HttpServletRequest request,@Validated({addSuggestChecks.class})CustomerSuggestVO customerSuggestVO
			,BindingResult br){
		log.info("addSuggest start...");
		
		// 检查请求参数
		checkRequest(br);

		// 获取当前用户
		User user = getCurrentUser(request);
		
		// 添加反馈信息
		int addSuggestResult = customerSuggestControllerImpl.addSuggest(user, customerSuggestVO);
		
		// 添加失败情况
		if(addSuggestResult != CommonConstant.NUMBER_1){
			log.info("addSuggest finished but has errors.return 500 failed code.");
			return "500";
		}
		
		// 添加成功情况
		log.info("addSuggest finished.return 200 success code.");
		return "200";
	}
	/**
	 * 查询问题反馈
	 * @param model
	 * @param request
	 * @param customerSuggestVO
	 * @param br
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value=RequestContant.CUS_SUGGEST_FIND)
	public String findSuggest(Model model,HttpServletRequest request,Integer states){
		
		
	
		// 获取当前用户
		User user = getCurrentUser(request);
		 Integer page=1;
		// 查询反馈信息
		findPlatformManageDTO findSuggest = customerSuggestControllerImpl.findSuggest(user.getUserId().intValue(), states, page);
		model.addAttribute("findSuggest", findSuggest);
		model.addAttribute("page", page);
		model.addAttribute("states", states);
		return ResponseContant.CUS_SUGGESTLIST; 
	}
	/**
	 * 查询问题反馈更多
	 * @param model
	 * @param request
	 * @param customerSuggestVO
	 * @param br
	 * @return
	 */
	@AuthPassport
	@ResponseBody
	@RequestMapping(value=RequestContant.CUS_SUGGEST_FIND_MORE)
	public String findSuggestmore(HttpServletRequest request,Integer states,Integer page){
		
		
		// 获取当前用户
		User user = getCurrentUser(request);
		
		// 查询反馈信息
		findPlatformManageDTO findSuggest = customerSuggestControllerImpl.findSuggest(user.getUserId().intValue(), states, page);
		return JSON.toJSONString(findSuggest); 
		
	}
	/**
	 * 根据id查询问题反馈
	 * @param model
	 * @param request
	 * @param customerSuggestVO
	 * @param br
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value=RequestContant.CUS_SUGGEST_FINDBYID)
	public String findSuggestById(Long id,Model model){
		
		
		// 查询反馈信息
		 PlatformComplaint platformComplaint = customerSuggestControllerImpl.findSuggestById(id);
		
		model.addAttribute("platformComplaint", platformComplaint);
		return ResponseContant.CUS_SUGGES_CONTENT; 
	}
}
