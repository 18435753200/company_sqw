/**
 * 
 */
package com.mall.controller.myccigmall;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mall.constant.RequestContant;
import com.mall.constant.ResponseContant;
import com.mall.controller.base.BaseController;

/**
 * @author cyy
 */
@Controller
@RequestMapping(value = RequestContant.CUS_SETTING)
public class SettingController extends BaseController {

	/**
	 * 账户设置
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = RequestContant.CUS_SETTING_INDEX, method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model model) {
		return ResponseContant.CUS_SETTING_INDEX;
	}
	
	/**
	 * 关于我们
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = RequestContant.CUS_ABOUT_INDEX, method = RequestMethod.GET)
	public String about(HttpServletRequest request, Model model) {
		return ResponseContant.CUS_ABOUT_INDEX;
	}

}
