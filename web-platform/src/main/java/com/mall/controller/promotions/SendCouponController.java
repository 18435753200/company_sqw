/*package com.mall.controller.promotions;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mall.comment.exception.BusinessException;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;

@Controller
@RequestMapping(value = "/sendCoupon")
public class SendCouponController extends BaseController{
	private static final Logger LOGGER = LoggerFactory.getLogger(SendCouponController.class);
	private static final String SUC_FLAG = "success";
	private static final String FAIL_FLAG = "error";
	
	
	@RequestMapping(value = "/byTime")
	public String sendCouponByTime(HttpServletRequest request){
		try{
			RemoteServiceSingleton.getInstance().getCollectSendCouponInfoApi().insertCouponStockBatch();
			LOGGER.info("调用定时发券接口结束");
		}catch(Exception e){
			LOGGER.error("调用定时发券接口结束"+e.getMessage());
			throw new BusinessException("调用定时发券接口结束"+e.getMessage());
		}
		return SUC_FLAG;
	}

}
*/