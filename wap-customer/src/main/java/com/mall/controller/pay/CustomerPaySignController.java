package com.mall.controller.pay;

import com.mall.annotation.AuthPassport;
import com.mall.check.PayCheck.bocCrossAuthCheck;
import com.mall.check.PayCheck.bocCrossSignCheck;
import com.mall.constant.CommonConstant;
import com.mall.constant.RequestContant;
import com.mall.constant.ResponseContant;
import com.mall.constant.ThirdAccountConstant;
import com.mall.controller.base.BaseController;
import com.mall.customer.model.User;
import com.mall.customer.order.po.CreateFalseOrder;
import com.mall.pay.common.PayConstant;
import com.mall.pay.dto.PayResult;
import com.mall.pay.exception.PayException;
import com.mall.pay.po.BocCrossBorder;
import com.mall.service.CusOrderPayService;
import com.mall.service.CusOrderService;
import com.mall.utils.Constants;
import com.mall.utils.JsonUtil;
import com.mall.utils.getUUID;
import com.mall.vo.BocCrossSignVO;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.apache.commons.lang.StringUtils;
import org.jfree.util.Log;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * 订单中心
 * 
 * @author ccigQA01
 * 
 */
@Controller
@RequestMapping(value = RequestContant.CUS_PAY)
public class CustomerPaySignController extends BaseController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CustomerPaySignController.class);
	@Autowired
	private CusOrderService cusOrderService;
	// 支付先关的服务
	@Autowired
	private CusOrderPayService cusOrderPayService;

	
	@AuthPassport
	@ResponseBody
	@RequestMapping(value = RequestContant.BOC_CROSS_BORDER_SIGN,method=RequestMethod.POST)
	public String bocCrossBorderSign(@Validated({bocCrossSignCheck.class}) BocCrossSignVO bocCrossSignVO, BindingResult br, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		// 请求参数验证
		checkRequest(br);
		User user = getCurrentUser(request);
		//TODO 检查账户是否签约
		
		String loseEfficacy;
		try {
			loseEfficacy = memcachedClient.get("boc_cross_pay_LoseEfficacy" + bocCrossSignVO.getDBT_PHONE() + user.getUserId());
			if (loseEfficacy != null && "LoseEfficacy".equals(loseEfficacy)) {
				LOGGER.info("发送短信验证码过于频繁");
				return "codeNotLoseEfficacy";
			}
		} catch (TimeoutException e) {
			LOGGER.info("获取memcache超时",e);
		} catch (InterruptedException e) {
			LOGGER.info("获取memcache中断",e);
			e.printStackTrace();
		} catch (MemcachedException e) {
			LOGGER.info("获取memchache异常",e);
		}
		
		
		BocCrossBorder bocCrossBorder = cusOrderPayService.bocCrossSign(bocCrossSignVO.getDRACC_NO(), bocCrossSignVO.getDBT_NAME(), bocCrossSignVO.getDBT_CODE(),
				bocCrossSignVO.getDBT_PHONE(), user);
		try {
			memcachedClient.set("boc_cross_pay_LoseEfficacy" + bocCrossSignVO.getDBT_PHONE() + user.getUserId(), Constants.LOSE_EFFICACY_TIME_60, "1");
		} catch (TimeoutException e) {
			LOGGER.error("memcached timeout get boc_cross_pay_LoseEfficacy" + e.getMessage(), e);
		} catch (InterruptedException e) {
			LOGGER.error("memcached Interrupted get boc_cross_pay_LoseEfficacy" + e.getMessage(), e);
		} catch (MemcachedException e) {
			LOGGER.error("memcached get boc_cross_pay_LoseEfficacy" + e.getMessage(), e);
		}
		return bocCrossBorder.getTrxSerno();
			
	}

	/**
	 * 跳转签约页面
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.TO_BOC_CROSS_BORDER_SIGN)
	public String bocCrossBorderSign(Model model, HttpServletRequest request,
			HttpServletResponse response){
		String sid = getUUID.getSessionId(request, response);
        String platForm = null;
        try {
			platForm = memcachedClient.get("payPlatForm" + sid);
			model.addAttribute("payPlatForm", platForm);
		} catch (TimeoutException e) {
			LOGGER.error("memcached timeout get pay or platForm" + e.getMessage(), e);
		} catch (InterruptedException e) {
			LOGGER.error("memcached Interrupted get pay or platForm" + e.getMessage(), e);
		} catch (MemcachedException e) {
			LOGGER.error("memcached get pay or platForm" + e.getMessage(), e);
		}
		
		return "/myccig/pay/boccSigning";
			
	}
	
	/**
	 * 中行跨境协议支付签约
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws MemcachedException
	 * @throws InterruptedException
	 * @throws TimeoutException
	 */
	@AuthPassport
	@ResponseBody
	@RequestMapping(value = RequestContant.CHECK_AUTH_CODE)
	public String checkAuthCode(@Validated({bocCrossAuthCheck.class}) BocCrossSignVO bocCrossSignVO, BindingResult br, Model model, HttpServletRequest request,
			HttpServletResponse response) throws TimeoutException,
			InterruptedException, MemcachedException {
		// 请求参数验证
		checkRequest(br);
		User currentUser = getCurrentUser(request);
		String sid = getUUID.getSessionId(request, response);
        String platForm = null;
        try {
			platForm = memcachedClient.get("payPlatForm" + sid);
			model.addAttribute("payPlatForm", platForm);
		} catch (TimeoutException e) {
			LOGGER.error("memcached timeout get pay or platForm" + e.getMessage(), e);
		} catch (InterruptedException e) {
			LOGGER.error("memcached Interrupted get pay or platForm" + e.getMessage(), e);
		} catch (MemcachedException e) {
			LOGGER.error("memcached get pay or platForm" + e.getMessage(), e);
		}
		
		try {
			cusOrderPayService.checkAuthCode(bocCrossSignVO.getSerNo(), bocCrossSignVO.getMessageCode());
		} catch (Exception e) {
			LOGGER.info("验证码校验失败", e);
		}
		return checkSignStatu(bocCrossSignVO);
		
		/*model.addAttribute("orderId", bocCrossSignVO.getOrderId());
		model.addAttribute("act", bocCrossSignVO.getAct());
		return ResponseContant.REDIRECT + RequestContant.ORDER_PAY + RequestContant.ORDER_BOCC_PAY_INFO;*/
	}
	
	/**
	 * 
	 * @param bocCrossSignVO
	 * @return
	 */
	private String checkSignStatu(BocCrossSignVO bocCrossSignVO) {
		for(int i=0; i < CommonConstant.BOC_CROSS_SIGN_STATUS_CHECK_NUMBER; i++){
			try {
				String statu = cusOrderPayService.checkSignState(bocCrossSignVO.getSerNo());
				LOGGER.info("check sign statu-->" + statu);
				if(statu != null && String.valueOf(CommonConstant.BOC_CROSS_SIGN_STATUS_SUCCESS).equals(statu)){
					return ResponseContant.SUCCESS;
				}
				if(statu != null && String.valueOf(CommonConstant.BOC_CROSS_SIGN_STATUS_FAIL).equals(statu)){
					return ResponseContant.FAIL;
				}
				try {
					Thread.sleep(CommonConstant.BOC_CROSS_SIGN_STATUS_INTERVAL_TIME);
				} catch (InterruptedException e) {
					LOGGER.info("boc cross auth sign excption", e);
				}
			} catch (ParseException e) {
				LOGGER.info("检查中行跨境签约异常", e);
			}
		}
		return ResponseContant.FAIL;
	}
	
	@RequestMapping(value=RequestContant.AGREEMENT)
	public String agreement(Model model, HttpServletRequest request,
			HttpServletResponse response){
		return ResponseContant.CUS_ORDER_PAY_BOC_CROSS_AGREEMENT;
	}
	
	

	/**
	 * 获取所有 已经签约的账号信息
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@AuthPassport
	@ResponseBody
	@RequestMapping(value=RequestContant.CHOOSE_ACCOUNT)
	public String chooseAccount(Model model, HttpServletRequest request, HttpServletResponse response){
		Log.info("---->>find account begin..");
		User currUser = getCurrentUser(request);
		String sid = getUUID.getSessionId(request, response);
        String platForm = null;
        try {
			platForm = memcachedClient.get("payPlatForm" + sid);
			model.addAttribute("payPlatForm", platForm);
		} catch (TimeoutException e) {
			LOGGER.error("memcached timeout get pay or platForm" + e.getMessage(), e);
		} catch (InterruptedException e) {
			LOGGER.error("memcached Interrupted get pay or platForm" + e.getMessage(), e);
		} catch (MemcachedException e) {
			LOGGER.error("memcached get pay or platForm" + e.getMessage(), e);
		}
		
		Log.info("cusOrderPayService.findBoccSignByUserId ...");
		List<BocCrossBorder> bcbList = cusOrderPayService.findBoccSignByUserId(currUser);
		Log.info("cusOrderPayService.findBoccSignByUserId end");
		
		if(bcbList != null && !bcbList.isEmpty()){
			
			processBocCrossBorder(bcbList);
			
			String bcbListStr = JsonUtil.serializeBeanToJsonString(bcbList);
			Log.info("bcbList:"+bcbListStr);
			return bcbListStr;
		}
		Log.info("bcbListStr is null.no account info.");
		return null;
		
	}

	private void processBocCrossBorder(List<BocCrossBorder> bcbList) {
		for (BocCrossBorder bocCrossBorder : bcbList) {
			
			String draccNo = bocCrossBorder.getDraccNo();
			
			String substring = draccNo.substring(4, draccNo.length()-4);
			
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i< substring.length();i++) {
				if(i % 4 == 0){
					sb.append(" ");
				}
				sb.append("*");
			}
			bocCrossBorder.setDraccNo(draccNo.replaceAll("(\\d{4})(\\d{"+substring.length()+"})(\\d{4})", "$1 "+sb.toString()+" $3"));
		}
	}


	/**
	 * 银联商务下单
	 * @param createFalseOrder
	 * @param request
	 * @return
	 */
	@RequestMapping(RequestContant.CUS_CHINAUMSORDER)
	@ResponseBody
	public PayResult chinaumsOrder(CreateFalseOrder createFalseOrder,String rc, HttpServletRequest request) {
		PayResult payResult = null;
		try {

			if (createFalseOrder != null && createFalseOrder.getUserId() == null) {
				throw new PayException("请登陆");
			}
			if (BigDecimal.ZERO.compareTo(createFalseOrder.getPayPrice()) >= 0) {
				throw new PayException("请正确输入金额");
			}

			String payType = createFalseOrder.getPayType();

			if (ThirdAccountConstant.ACCOUNT_WEIXIN.equalsIgnoreCase(payType)) {
				createFalseOrder.setPayType(PayConstant.PayClient.CLIENT_WEIXIN);
			} else if (ThirdAccountConstant.ACCOUNT_ALIPAY.equalsIgnoreCase(payType)) {
				createFalseOrder.setPayType(PayConstant.PayClient.CLIENT_ALI);
			} else {
				throw new PayException("支付异常，请重新扫码");
			}

			if (createFalseOrder.getSupplierId() == null) {
				throw new PayException("supplierId不能为空");
			}
			if (createFalseOrder.getOpenId() == null) {
				throw new PayException("OpenId不能为空");
			}
			if (createFalseOrder.getPayPrice() == null || createFalseOrder.getPayPrice().compareTo(BigDecimal.ZERO) == 0) {
				throw new PayException("请正确输入支付金额");
			}


			LOGGER.info("扫码支付，二维码ID：{}, rc:{} , userId: {}", createFalseOrder.getQrCodeId(), rc, createFalseOrder.getUserId());

			if (createFalseOrder.getQrCodeId() == null && rc != null) {
				createFalseOrder.setQrCodeId(rc);
			}


			payResult = cusOrderPayService.chinaumsOrder(createFalseOrder, request);

		} catch (PayException e) {
			LOGGER.error(e.getMessage(), e);
			payResult = new PayResult(PayConstant.PayRecordStatus.FAIL);
			payResult.setMsg(e.getMessage());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			payResult = new PayResult(PayConstant.PayRecordStatus.FAIL);
			payResult.setMsg("支付异常");
		}

		return payResult;
	}


	
	
	
}