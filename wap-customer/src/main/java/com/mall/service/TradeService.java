package com.mall.service;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.customer.dto.UserTradeDto;
import com.mall.customer.model.User;
import com.mall.customer.model.UserTrade;
import com.mall.customer.service.UserService;
import com.mall.pay.common.StringUtils;
import com.mall.vo.TradeVO;

import net.rubyeye.xmemcached.MemcachedClient;

/**
 * 支付设置
 * 
 * @author cyy
 * 
 */
@Service
public class TradeService {
	private static final Logger log = LoggerFactory.getLogger(TradeService.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MemcachedClient memcachedClient;
	
	/**
	 * 设置支付密码
	 * 
	 * @param request
	 * @param userInfo
	 * @return
	 */
	public TradeVO getTrade(User userInfo) {
		TradeVO tradeVO = new TradeVO();
		try{
			UserTradeDto tradeDto = userService.getTrade(userInfo.getUserId());
			tradeVO.setRetCode(tradeDto.getRetCode());
			tradeVO.setRetInfo(tradeDto.getRetInfo());
		}catch(Exception e){
			log.error("call userSetvice error ....");
			throw new RuntimeException(e);
		}
		return tradeVO;
	}

	/**
	 * 设置支付密码
	 * 
	 * @param request
	 * @param userInfo
	 * @return
	 */
	public TradeVO setTrade(HttpServletRequest request, User userInfo) {
		TradeVO tradeVO = new TradeVO();
		try {
			String pwd = request.getParameter("pwd");
			String captcha = request.getParameter("captcha");
			UserTradeDto tradeDto = userService.getTrade(userInfo.getUserId());
			if (StringUtils.isEmpty(pwd)) {
				tradeVO.setRetInfo("支付密码不能为空");
			}
			if (StringUtils.isEmpty(captcha)) {
				tradeVO.setRetInfo("验证码不能为空");
			}
			String modifyVfCode = captcha;
			String mobile = userInfo.getMobile();
			String key = "trade_captcha_mobileCode" + mobile;
			Integer memModifyVfCode = this.memcachedClient.get(key);
			String mModifyVfCode = memModifyVfCode == null ?"":memModifyVfCode.toString();
			if (modifyVfCode.equals(mModifyVfCode)) {
				UserTrade trade = new UserTrade();
				trade.setPwd(pwd);
				trade.setUserId(userInfo.getUserId());
				UserTradeDto userTradeDto = null;
				if ("0".equals(tradeDto.getRetCode())) {// 设置支付密码
					userTradeDto = userService.saveTrade(trade);
				} else {
					userTradeDto = userService.updateTrade(trade);
				}
				if("1".equals(userTradeDto.getRetCode())){
					memcachedClient.delete(key);
				}
				tradeVO.setRetCode(userTradeDto.getRetCode());
				tradeVO.setRetInfo(userTradeDto.getRetInfo());
			} else {
				tradeVO.setRetInfo("验证码不正确");
			}
		} catch (Exception e) {
			log.error("call userSetvice error ....");
			tradeVO.setRetInfo("系统繁忙，请稍等重试");
		}
		return tradeVO;
	}
}
