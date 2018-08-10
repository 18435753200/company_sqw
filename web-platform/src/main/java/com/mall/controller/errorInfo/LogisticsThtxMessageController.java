/**  
 * @Project: mall-web-platform
 * @Title: LogisticsThtxMessageController.java
 * @Package: com.mall.controller.errorInfo
 * @Description: 推送WMS数据错误信息控制层
 * @author: QIJJ 
 * @since: 2015-7-31 上午10:23:42
 * @Copyright: 2015. All rights reserved.
 * @Version: v1.0   
 *//*
package com.mall.controller.errorInfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.mybatis.utility.PageBean;
import com.mall.supplier.order.api.rpc.LogisticsThtxMessageService;
import com.mall.supplier.order.po.LogisticsThtxMessage;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;

*//**
 * @ClassName LogisticsThtxMessageController
 * @Description: 推送WMS数据错误信息控制层
 * @author: QIJJ
 * @since: 2015-7-31 上午10:23:42
 *//*
@Controller
@RequestMapping(value = "/errorInfo")
public class LogisticsThtxMessageController extends BaseController {

	private static final Log LOG = LogFactory.getLogger(LogisticsThtxMessageController.class);

	*//**
	 * 日期的转换
	 *//*
	@Override
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	*//**
	 * @Description：跳转至推送WMS数据错误信息页面
	 * @author: QIJJ
	 * @since: 2015-6-24 下午5:04:36
	 *//*
	@RequestMapping(value = "/selectLogisticsThtxMessage")
	public String selectLogisticsThtxMessage(HttpServletRequest request, HttpServletResponse response, Model model) {

		return "/errorInfo/logisticsThtxMessagePage";
	}

	*//**
	 * @Description： 通过条件查询推送WMS数据错误信息信息
	 * @author: QIJJ
	 * @since: 2015-6-24 下午5:09:37
	 *//*
	@RequestMapping(value = "/selectLogisticsThtxMessageInfo")
	public String selectLogisticsThtxMessageInfo(HttpServletRequest request, HttpServletResponse response, Model model, Integer page, Date firstDate, Date lastDate, String messageType) {
		try {
			PageBean<LogisticsThtxMessage> pageBean = new PageBean<LogisticsThtxMessage>();
			LogisticsThtxMessage logisticsThtxMessage = new LogisticsThtxMessage();
			logisticsThtxMessage.setFirstDate(firstDate);
			logisticsThtxMessage.setLastDate(lastDate);
			logisticsThtxMessage.setMessageType(messageType);

			if (page != null) {
				pageBean.setPage(page);
			} else {
				pageBean.setPage(1);
			}
			pageBean.setPageSize(Constants.INT10);
			pageBean.setParameter(logisticsThtxMessage);// 设置分页

			LogisticsThtxMessageService logisticsThtxMessageService = (LogisticsThtxMessageService) RemoteServiceSingleton.getInstance().getAppService("logisticsThtxMessageService");
			pageBean = logisticsThtxMessageService.selectByDynamicListPage(pageBean);

			model.addAttribute("pb", pageBean);
		} catch (Exception e) {
			LOG.error("进入推送WMS数据错误信息查询页面." + e.getMessage(), e);
			e.printStackTrace();
		}

		return "/errorInfo/logisticsThtxMessageTable";
	}

	*//**
	 * @Description： 通过ID查询相应信息
	 * @author: QIJJ
	 * @since: 2015-6-24 下午5:09:37
	 *//*
	@RequestMapping(value = "/selectByPrimaryKey")
	public String selectByPrimaryKey(HttpServletRequest request, HttpServletResponse response, Model model, Long messageId) {
		try {
			LogisticsThtxMessageService logisticsThtxMessageService = (LogisticsThtxMessageService) RemoteServiceSingleton.getInstance().getAppService("logisticsThtxMessageService");
			LogisticsThtxMessage logisticsThtxMessage = logisticsThtxMessageService.selectByPrimaryKey(messageId);

			model.addAttribute("logisticsThtxMessage", logisticsThtxMessage);
		} catch (Exception e) {
			LOG.error("进入推送WMS数据错误信息查询页面." + e.getMessage(), e);
			e.printStackTrace();
		}

		return "/errorInfo/logisticsMessage";
	}
}
*/