/**  
 * @Project: mall-web-platform
 * @Title: ShipQtyErrorController.java
 * @Package: com.mall.controller.errorInfo
 * @Description: 物流数量错误日志控制层
 * @author: QIJJ 
 * @since: 2015-7-31 上午10:20:52
 * @Copyright: 2015. All rights reserved.
 * @Version: v1.0   
 */
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
//import com.mall.supplier.order.api.rpc.ShipQtyErrorService;
//import com.mall.supplier.order.po.ShipQtyError;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;

/**
 * @ClassName ShipQtyErrorController
 * @Description: 物流数量错误日志控制层
 * @author: QIJJ
 * @since: 2015-7-31 上午10:20:52
 */
@Controller
@RequestMapping(value = "/errorInfo")
public class ShipQtyErrorController extends BaseController {

	private static final Log LOG = LogFactory.getLogger(ShipQtyErrorController.class);

	/**
	 * 日期的转换
	 */
	@Override
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/**
	 * @Description：跳转至物流错误信息页面
	 * @author: QIJJ
	 * @since: 2015-6-24 下午5:04:36
	 */
	@RequestMapping(value = "/selectShipQtyError")
	public String selectShipQtyError(HttpServletRequest request, HttpServletResponse response, Model model) {

		return "/errorInfo/shipQtyErrorPage";
	}

	/**
	 * @Description： 通过条件查询物流错误信息信息
	 * @author: QIJJ
	 * @since: 2015-6-24 下午5:09:37
	 */
	/*@RequestMapping(value = "/selectShipQtyErrorInfo")
	public String selectShipQtyErrorInfo(HttpServletRequest request, HttpServletResponse response, Model model, Integer page, Date firstDate, Date lastDate, Long orderId, Long skuId) {
		try {
			PageBean<ShipQtyError> pageBean = new PageBean<ShipQtyError>();
			ShipQtyError shipQtyError = new ShipQtyError();
			shipQtyError.setLastDate(lastDate);
			shipQtyError.setFirstDate(firstDate);
			shipQtyError.setSkuId(skuId);
			shipQtyError.setOrderId(orderId);

			if (page != null) {
				pageBean.setPage(page);
			} else {
				pageBean.setPage(1);
			}
			pageBean.setPageSize(Constants.INT10);
			pageBean.setParameter(shipQtyError);// 设置分页

			ShipQtyErrorService shipQtyErrorService = (ShipQtyErrorService) RemoteServiceSingleton.getInstance().getAppService("shipQtyErrorService");
			pageBean = shipQtyErrorService.selectByDynamicListPage(pageBean);

			model.addAttribute("pb", pageBean);
		} catch (Exception e) {
			LOG.error("进入物流错误信息查询页面." + e.getMessage(), e);
			e.printStackTrace();
		}

		return "/errorInfo/shipQtyErrorTable";
	}*/
}
