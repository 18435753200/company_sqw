/**  
 * @Project: web-platform
 * @Title: StockOutErrorController.java
 * @Package: com.mall.controller.errorInfo
 * @Description: 库存错误信息控制层
 * @author: QIJJ 
 * @since: 2015-7-31 上午10:25:35
 * @Copyright: 2015. All rights reserved.
 * @Version: v1.0   
 */
package com.mall.controller.errorInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.mybatis.utility.PageBean;
//import com.mall.supplier.order.api.rpc.StockOutErrorService;
//import com.mall.supplier.order.po.StockOutError;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;

/**
 * @ClassName StockOutErrorController
 * @Description: 库存错误信息控制层
 * @author: QIJJ
 * @since: 2015-7-31 上午10:25:35
 */
@Controller
@RequestMapping(value = "/errorInfo")
public class StockOutErrorController extends BaseController {

	private static final Log LOG = LogFactory.getLogger(StockOutErrorController.class);

	/**
	 * @Description：跳转至库存错误信息页面
	 * @author: QIJJ
	 * @since: 2015-6-24 下午5:04:36
	 */
	@RequestMapping(value = "/selectStockOutError")
	public String selectStockOutError(HttpServletRequest request, HttpServletResponse response, Model model) {

		return "/errorInfo/stockOutErrorPage";
	}

	/**
	 * @Description： 通过条件查询库存错误信息信息
	 * @author: QIJJ
	 * @since: 2015-6-24 下午5:09:37
	 */
	/*@RequestMapping(value = "/selectStockOutErrorInfo")
	public String selectStockOutErrorInfo(HttpServletRequest request, HttpServletResponse response, Model model, Integer page, Long notificationId) {
		try {
			PageBean<StockOutError> pageBean = new PageBean<StockOutError>();
			StockOutError stockOutError = new StockOutError();
			stockOutError.setNotificationId(notificationId);

			if (page != null) {
				pageBean.setPage(page);
			} else {
				pageBean.setPage(1);
			}
			pageBean.setPageSize(Constants.INT10);
			pageBean.setParameter(stockOutError);// 设置分页

			StockOutErrorService stockOutErrorService = (StockOutErrorService) RemoteServiceSingleton.getInstance().getAppService("stockOutErrorService");
			pageBean = stockOutErrorService.selectByDynamicListPage(pageBean);

			model.addAttribute("pb", pageBean);
		} catch (Exception e) {
			LOG.error("进入库房错误信息查询页面." + e.getMessage(), e);
			e.printStackTrace();
		}

		return "/errorInfo/stockOutErrorTable";
	}*/

}
