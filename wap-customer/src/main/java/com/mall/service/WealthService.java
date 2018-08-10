package com.mall.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.mall.customer.dto.UserAccountDetialDto;
import com.mall.customer.dto.UserTradeDto;
import com.mall.customer.model.SqwUserAccountBalance;
import com.mall.customer.model.User;
import com.mall.customer.model.UserOperationRecord;
import com.mall.customer.model.UserTrade;
import com.mall.customer.service.PsqwAccountRecordService;
import com.mall.customer.service.SqwAccountRecordService;
import com.mall.customer.service.UserOperationRecordService;
import com.mall.customer.service.UserService;
import com.mall.mybatis.utility.PageBean;
import com.mall.pay.common.StringUtils;
import com.mall.utils.Constants;
import com.mall.vo.TradeRes;
import com.mall.vo.TradeValidateVO;
import com.mall.vo.TransferTradeVO;
import com.mall.vo.WealthVO;
//import com.mysql.fabric.xmlrpc.base.Array;

import net.rubyeye.xmemcached.MemcachedClient;

/**
 * 财富中心
 * 
 * @author cyy
 * 
 */
@Service
public class WealthService {
	private static final Logger LOGGER = LoggerFactory.getLogger(WealthService.class);

	@Autowired
	private SqwAccountRecordService sqwAccountRecordService;
	@Autowired
	private PsqwAccountRecordService psqwAccountRecordService;

	@Autowired
	private UserService userService;

	@Autowired
	private MemcachedClient memcachedClient;
	
	@Autowired
	private UserOperationRecordService userOperationRecordService;

	/**
	 * 获取财富中心数据
	 * 
	 * @return
	 */
	public WealthVO info(User userInfo) {
		try {
			// 获取用户当前可用账户余额
			BigDecimal arrivedFlagDecimal = sqwAccountRecordService.getUserAccountRecordBalance(userInfo.getUserId(),
					1);
			// 分红额度
			BigDecimal momneyRewardDecimal = sqwAccountRecordService.getUserAccountRecordBalance(userInfo.getUserId(),
					2);
			// 获取用户未到账户余额
			BigDecimal unArrivedFlagDecimal = sqwAccountRecordService.getUserAccountBalanceInvalid(userInfo.getUserId(),
					1);
			// 消费金额
			BigDecimal consumptionAmountDecimal = sqwAccountRecordService.getUserAccountBalance(userInfo.getUserId(),
					2);
			// 消费红旗券
			BigDecimal consumptionFlagDecimal = sqwAccountRecordService.getUserAccountBalance(userInfo.getUserId(), 1);
			
			// 可转账额度
			BigDecimal transferRewardDecimal = sqwAccountRecordService.getUserTransferBalanceLimit(userInfo.getUserId());
			transferRewardDecimal = transferRewardDecimal.compareTo(BigDecimal.ZERO) <= 0 ?BigDecimal.ZERO:transferRewardDecimal;
			WealthVO wealth = new WealthVO();
			wealth.setArrivedFlag(arrivedFlagDecimal);
			wealth.setMoneyReward(momneyRewardDecimal);
			wealth.setUnArrivedFlag(unArrivedFlagDecimal);
			wealth.setConsumptionAmount(consumptionAmountDecimal);
			wealth.setConsumptionFlag(consumptionFlagDecimal);
			wealth.setTransferReward(transferRewardDecimal);
			return wealth;
		} catch (Exception e) {
			LOGGER.error("info error ....");
			throw new RuntimeException("call sqwAccountRecordService error", e);
		}
	}

	public WealthVO newinfo(User userInfo) {
		try {
			// M券余额
			/*BigDecimal arrivedFlagDecimal = psqwAccountRecordService.getUserAccountRecordBalance(userInfo.getUserId(),
					3);*/
			
			
			List<Integer> list = new ArrayList<Integer>();
			list.add(3);
			list.add(5);
			list.add(6);
			
			List<SqwUserAccountBalance> balanceList = psqwAccountRecordService.getPuserAccountBalance(userInfo.getUserId(),list);
			WealthVO wealth = new WealthVO();
			if(balanceList!=null && balanceList.size()>0){
				// M券余额
				BigDecimal arrivedFlagDecimal = balanceList.get(0).getAccountBalance();
				wealth.setArrivedFlag(arrivedFlagDecimal);
			}
			
			if(balanceList!=null && balanceList.size()>1){
				// 分享额度
				BigDecimal momneyRewardDecimal = balanceList.get(1).getAccountBalance();
				wealth.setMoneyReward(momneyRewardDecimal);
			}
			
			if(balanceList!=null && balanceList.size()>2){
				// 分享收入现金账户
				BigDecimal unArrivedFlagDecimal = balanceList.get(2).getAccountBalance();
				wealth.setUnArrivedFlag(unArrivedFlagDecimal);
			}
			
			
			/*BigDecimal momneyRewardDecimal = psqwAccountRecordService.getUserAccountRecordBalance(userInfo.getUserId(),
					5);*/
			// 获取用户未到账户余额
			/*BigDecimal unArrivedFlagDecimal = psqwAccountRecordService.getUserAccountRecordBalance(userInfo.getUserId(),
					6);*/
			// 消费金额
			//BigDecimal consumptionAmountDecimal = sqwAccountRecordService.getUserAccountBalance(userInfo.getUserId(),2);
			// 消费红旗券
			//BigDecimal consumptionFlagDecimal = sqwAccountRecordService.getUserAccountBalance(userInfo.getUserId(), 1);
			
			// 可转账额度
			//BigDecimal transferRewardDecimal = sqwAccountRecordService.getUserTransferBalanceLimit(userInfo.getUserId());
			//transferRewardDecimal = transferRewardDecimal.compareTo(BigDecimal.ZERO) <= 0 ?BigDecimal.ZERO:transferRewardDecimal;
			
			
			
			
			//wealth.setConsumptionAmount(consumptionAmountDecimal);
			//wealth.setConsumptionFlag(consumptionFlagDecimal);
			//wealth.setTransferReward(transferRewardDecimal);
			return wealth;
		} catch (Exception e) {
			LOGGER.error("info error ....");
			throw new RuntimeException("call sqwAccountRecordService error", e);
		}
	}
	
	
	/**
	 * 红旗券转账效验数据有效性
	 * 
	 * @param tradeValidateVO
	 * @param userInfo
	 * @return
	 */
	public TradeRes tradeValidate(TradeValidateVO tradeValidateVO, User userInfo) {
		TradeRes response = new TradeRes();
		try {
			Integer state = sqwAccountRecordService.getUserAccountStatus(userInfo.getUserId(), 1);
			/*if (state != 2) {
				response.setRetInfo("请您激活账号后再进行转账");
				return response;
			}*/
			if (tradeValidateVO.getType() == 1) {
				UserTradeDto trade = userService.getTrade(userInfo.getUserId());
				if ("0".equals(trade.getRetCode())) {
					response.setErrorCode("0");
					response.setRetInfo("请先设置支付密码");
					return response;
				}
				User otherUser = userService.findUserByMobile(tradeValidateVO.getAccount());
				if (otherUser == null) {
					try {
						Long userId = Long.parseLong(tradeValidateVO.getAccount());
						otherUser = userService.findUserById(userId);
					} catch (Exception e) {
						LOGGER.error(e.getMessage(), e);
						response.setRetInfo("请输入合法的账户信息");
						return response;
					}
				}

				if (otherUser == null) {
					response.setRetInfo("找不到该账户信息，请核对");
					return response;
				}

				if (otherUser.getUserId().intValue() == userInfo.getUserId()) {
					response.setRetInfo("不能转账给自己");
					return response;
				}

				if (tradeValidateVO.getAmount() <= 0D) {
					response.setRetInfo("红旗券不能小于0");
					return response;
				}

				/*Integer otherState = sqwAccountRecordService.getUserAccountStatus(otherUser.getUserId(), 1);
				if (otherState != 2) {
					response.setRetInfo("此账号为未激活账号，无法完成转账");
					return response;
				}*/

				if (StringUtils.isEmpty(otherUser.getRealName()) /*|| StringUtils.isEmpty(otherUser.getIdCard())*/) {
					response.setRetInfo("因对方未做实名认证，故无法完成转券操作，请联系对方进行实名认证。");
					return response;
				}

				if (!otherUser.getRealName().equals(tradeValidateVO.getRealName())) {
					response.setRetInfo("转账账户真实姓名与实名认证不一致，故无法完成转券操作，请联系对方进行实名认证。");
					return response;
				}

				BigDecimal amountDecimal = sqwAccountRecordService.getUserAccountRecordBalance(userInfo.getUserId(),
						Constants.HQQ);
				if (amountDecimal == null
						|| tradeValidateVO.getAmount().compareTo(new Double(amountDecimal.doubleValue())) > 0) {
					response.setRetInfo("红旗券额度不足");
					return response;
				}

				if (tradeValidateVO.getAmount().compareTo(Constants.SECURITY_AMOUNT) >= 0) {
					response.setIsCaptcha("1");
				}
			} else {
				String modifyVfCode = tradeValidateVO.getCaptcha();
				String mobile = userInfo.getMobile();
				String key = "trade_captcha_mobileCode" + mobile;
				Integer memModifyVfCode = this.memcachedClient.get(key);
				String mModifyVfCode = memModifyVfCode == null ? "" : memModifyVfCode.toString();
				if (!modifyVfCode.equals(mModifyVfCode)) {
					response.setRetInfo("验证码不正确");
					return response;
				}
			}
			response.setRetCode("1");
			response.setRetInfo("验证成功");
		} catch (Exception exception) {
			response.setRetInfo("系统繁忙，请稍后再试");
			LOGGER.error("红旗券转账验证错误", exception);
		}
		return response;
	}
	/**
	 * M券转账效验数据有效性1
	 * 
	 * @param tradeValidateVO
	 * @param userInfo
	 * @return
	 */
	public TradeRes tradeValidatenew(TradeValidateVO tradeValidateVO, User userInfo) {
		TradeRes response = new TradeRes();
		try {
			Integer state = sqwAccountRecordService.getUserAccountStatus(userInfo.getUserId(), 3);
			/*if (state != 2) {
				response.setRetInfo("请您激活账号后再进行转账");
				return response;
			}*/
			if (tradeValidateVO.getType() == 1) {
				UserTradeDto trade = userService.getTrade(userInfo.getUserId());
				if ("0".equals(trade.getRetCode())) {
					response.setErrorCode("0");
					response.setRetInfo("请先设置支付密码");
					return response;
				}
				User otherUser = userService.findUserByMobile(tradeValidateVO.getAccount());
				if (otherUser == null) {
					try {
						Long userId = Long.parseLong(tradeValidateVO.getAccount());
						otherUser = userService.findUserById(userId);
					} catch (Exception e) {
						LOGGER.error(e.getMessage(), e);
						response.setRetInfo("请输入合法的账户信息");
						return response;
					}
				}
				
				if (otherUser == null) {
					response.setRetInfo("找不到该账户信息，请核对");
					return response;
				}
				
				if (otherUser.getUserId().intValue() == userInfo.getUserId()) {
					response.setRetInfo("不能转账给自己");
					return response;
				}
				
				if (tradeValidateVO.getAmount() <= 0D) {
					response.setRetInfo("M券不能小于0");
					return response;
				}
				
				/*Integer otherState = sqwAccountRecordService.getUserAccountStatus(otherUser.getUserId(), 1);
				if (otherState != 2) {
					response.setRetInfo("此账号为未激活账号，无法完成转账");
					return response;
				}*/
				
				if (StringUtils.isEmpty(otherUser.getRealName()) /*|| StringUtils.isEmpty(otherUser.getIdCard())*/) {
					response.setRetInfo("因对方未做实名认证，故无法完成转券操作，请联系对方进行实名认证。");
					return response;
				}
				
				if (!otherUser.getRealName().equals(tradeValidateVO.getRealName())) {
					response.setRetInfo("转账账户真实姓名与实名认证不一致，故无法完成转券操作，请联系对方进行实名认证。");
					return response;
				}
				
				List<Integer> list = new ArrayList<Integer>();
				list.add(3);
				List<SqwUserAccountBalance> amountDecimalList = psqwAccountRecordService.getPuserAccountBalance(userInfo.getUserId(),list);
				if(amountDecimalList!=null && amountDecimalList.size()>0){
					BigDecimal amountDecimal = amountDecimalList.get(0).getAccountBalance();
					if (amountDecimal == null || tradeValidateVO.getAmount().compareTo(new Double(amountDecimal.doubleValue())) > 0) {
						response.setRetInfo("M券额度不足");
						return response;
					}
					
				}
				/*BigDecimal amountDecimal = psqwAccountRecordService.getPuserAccountBalance(userInfo.getUserId(),
						Constants.HQQ);*/
				
				if (tradeValidateVO.getAmount().compareTo(Constants.SECURITY_AMOUNT) >= 0) {
					response.setIsCaptcha("1");
				}
			} else {
				String modifyVfCode = tradeValidateVO.getCaptcha();
				String mobile = userInfo.getMobile();
				String key = "trade_captcha_mobileCode" + mobile;
				Integer memModifyVfCode = this.memcachedClient.get(key);
				String mModifyVfCode = memModifyVfCode == null ? "" : memModifyVfCode.toString();
				if (!modifyVfCode.equals(mModifyVfCode)) {
					response.setRetInfo("验证码不正确");
					return response;
				}
			}
			response.setRetCode("1");
			response.setRetInfo("验证成功");
		} catch (Exception exception) {
			response.setRetInfo("系统繁忙，请稍后再试");
			LOGGER.error("M券转账验证错误", exception);
		}
		return response;
	}

	/**
	 * 红旗券转账
	 * 
	 * @param transferTradeVO
	 * @param user
	 * @return
	 */
	public TradeRes transfer(TransferTradeVO transferTradeVO, User user) {
		TradeRes response = new TradeRes();
		String msg = null;
		try {
			Integer state = sqwAccountRecordService.getUserAccountStatus(user.getUserId(), 1);
			/*if (state != 2) {
				response.setRetInfo("请您激活账号后再进行转账");
				return response;
			}*/
			UserTradeDto trade = userService.getTrade(user.getUserId());
			if ("0".equals(trade.getRetCode())) {
				response.setErrorCode("2");
				response.setRetInfo("请先设置支付密码");
				return response;
			}

			User otherUser = userService.findUserByMobile(transferTradeVO.getAccount());
			if (otherUser == null) {
				try {
					Long userId = Long.parseLong(transferTradeVO.getAccount());
					otherUser = userService.findUserById(userId);
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					response.setRetInfo("请输入合法的账户信息");
					return response;
				}
			}

			if (otherUser == null) {
				response.setRetInfo("找不到该账户信息，请核对");
				return response;
			}

			if (otherUser.getUserId().intValue() == user.getUserId()) {
				response.setRetInfo("不能转账给自己");
				return response;
			}

			if (transferTradeVO.getAmount() != null && transferTradeVO.getAmount() <= 0D) {
				response.setRetInfo("红旗券要大于0");
				return response;
			}

			/*Integer otherState = sqwAccountRecordService.getUserAccountStatus(otherUser.getUserId(), 1);
			if (otherState != 2) {
				response.setRetInfo("此账号为未激活账号，无法完成转账");
				return response;
			}*/

			if (StringUtils.isEmpty(otherUser.getRealName()) /*|| StringUtils.isEmpty(otherUser.getIdCard())*/) {
				response.setRetInfo("因对方未做实名认证，故无法完成转券操作，请联系对方进行实名认证。");
				return response;
			}

			if (!otherUser.getRealName().equals(transferTradeVO.getRealName())) {
				response.setRetInfo("转账账户真实姓名与实名认证不一致，故无法完成转券操作，请联系对方进行实名认证。");
				return response;
			}

			BigDecimal amountDecimal = sqwAccountRecordService.getUserAccountRecordBalance(user.getUserId(),
					Constants.HQQ);
			if (amountDecimal == null
					|| transferTradeVO.getAmount().compareTo(new Double(amountDecimal.doubleValue())) > 0) {
				response.setRetInfo("红旗券额度不足");
				return response;
			}

			String key = null;
			BigDecimal amount = new BigDecimal(transferTradeVO.getAmount());
			if (transferTradeVO.getAmount().compareTo(Constants.SECURITY_AMOUNT) >= 0) {
				if (StringUtils.isEmpty(transferTradeVO.getCaptcha())) {
					response.setRetInfo("验证码不能为空");
					return response;
				} else {
					String modifyVfCode = transferTradeVO.getCaptcha();
					String mobile = user.getMobile();
					key = "trade_captcha_mobileCode" + mobile;
					Integer memModifyVfCode = this.memcachedClient.get(key);
					String mModifyVfCode = memModifyVfCode == null ? "" : memModifyVfCode.toString();
					if (!modifyVfCode.equals(mModifyVfCode)) {
						response.setRetInfo("验证码不正确");
						return response;
					}
				}
			}
			UserTrade userTrade = new UserTrade();
			userTrade.setPwd(transferTradeVO.getPwd());
			userTrade.setUserId(user.getUserId());
			UserTradeDto userTradeDto = userService.checkTradePwd(userTrade);
			if (!"1".equals(userTradeDto.getRetCode())) {
				LOGGER.info("调用 checkTradePwd 返回信息" + JSON.toJSONString(userTradeDto));
				response.setErrorCode(userTradeDto.getRetCode());
				response.setRetInfo(userTradeDto.getRetInfo());
				return response;
			}
			String trancNo = System.currentTimeMillis() + "";
			// accountId 1红旗券，2现金额度
			Integer result = sqwAccountRecordService.transferAccountFromUserToUser(user.getUserId(),
					otherUser.getUserId(), amount, Constants.HQQ, transferTradeVO.getRealName(),
					transferTradeVO.getRemark(),trancNo);
			String code = "0";
			switch (result) {
			case 0:
			case 10:
				msg = "红旗券转账成功";
				code = "1";
				if (StringUtils.isNotEmpty(key)) {
					memcachedClient.delete(key);// 成功删除验证码
				}
				try{
					UserOperationRecord userOperationRecord = new UserOperationRecord();
					userOperationRecord.setCreateTime(new Date());
					userOperationRecord.setOperationType("transferAccounts");
					userOperationRecord.setRemark("用户转账，转账金额为：" + amount.toString());
					userOperationRecord.setUserId(user.getUserId().intValue());
					userOperationRecordService.saveUserOperationRecord(userOperationRecord);
				}catch(Exception e){
					LOGGER.error("记录用户转账操作异常："+e.getMessage(),e);
				}
				break;
			case 1:
				msg = "转账金额不能为0";
				break;
			case 2:
				msg = "账户额度不足";
				break;
			case 3:
				msg="转账人账户未激活";
				break;
			case 4:
				msg="接收人账户未激活";
				break;
			case 15:
				msg = "超过单笔交易限额 ";
				break;
			case 6:
				msg = "超过当日转账限额";
				break;
			}
			response.setRetCode(code);
			response.setRetInfo(msg);
		} catch (Exception exception) {
			response.setRetInfo("系统繁忙，请稍后再试");
			LOGGER.error("红旗券转账错误", exception);
		}
		return response;
	}
	/**
	 * M券转账1
	 * 
	 * @param transferTradeVO
	 * @param user
	 * @return
	 */
	public TradeRes transfernew(TransferTradeVO transferTradeVO, User user) {
		TradeRes response = new TradeRes();
		String msg = null;
		try {
			//Integer state = sqwAccountRecordService.getUserAccountStatus(user.getUserId(), 3);
			/*if (state != 2) {
				response.setRetInfo("请您激活账号后再进行转账");
				return response;
			}*/
			UserTradeDto trade = userService.getTrade(user.getUserId());
			if ("0".equals(trade.getRetCode())) {
				response.setErrorCode("2");
				response.setRetInfo("请先设置支付密码");
				return response;
			}
			
			User otherUser = userService.findUserById(Long.parseLong(transferTradeVO.getAccount()));
			if (otherUser == null) {
				try {
					Long userId = Long.parseLong(transferTradeVO.getAccount());
					otherUser = userService.findUserById(userId);
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					response.setRetInfo("请输入合法的账户信息");
					return response;
				}
			}
			
			if (otherUser == null) {
				response.setRetInfo("找不到该账户信息，请核对");
				return response;
			}
			
			if (otherUser.getUserId().intValue() == user.getUserId()) {
				response.setRetInfo("不能转账给自己");
				return response;
			}
			
			if (transferTradeVO.getAmount() != null && transferTradeVO.getAmount() <= 0D) {
				response.setRetInfo("M券要大于0");
				return response;
			}
			
			/*Integer otherState = sqwAccountRecordService.getUserAccountStatus(otherUser.getUserId(), 1);
			if (otherState != 2) {
				response.setRetInfo("此账号为未激活账号，无法完成转账");
				return response;
			}*/
			
			if (StringUtils.isEmpty(otherUser.getRealName()) /*|| StringUtils.isEmpty(otherUser.getIdCard())*/) {
				response.setRetInfo("因对方未做实名认证，故无法完成转券操作，请联系对方进行实名认证。");
				return response;
			}
			
			if (!otherUser.getRealName().equals(transferTradeVO.getRealName())) {
				response.setRetInfo("转账账户真实姓名与实名认证不一致，故无法完成转券操作，请联系对方进行实名认证。");
				return response;
			}
			List<Integer> list = new ArrayList<Integer>();
			list.add(3);
			List<SqwUserAccountBalance> amountDecimalList =psqwAccountRecordService.getPuserAccountBalance(user.getUserId(),list);
			BigDecimal amountDecimal = amountDecimalList.get(0).getAccountBalance();
			//BigDecimal amountDecimal = sqwAccountRecordService.getUserAccountRecordBalance(user.getUserId(),
			//		Constants.HQQ);
			if (amountDecimal == null
					|| transferTradeVO.getAmount().compareTo(new Double(amountDecimal.doubleValue())) > 0) {
				response.setRetInfo("M券额度不足");
				return response;
			}
			
			String key = null;
			BigDecimal amount = new BigDecimal(transferTradeVO.getAmount());
			if (transferTradeVO.getAmount().compareTo(Constants.SECURITY_AMOUNT) >= 0) {
				if (StringUtils.isEmpty(transferTradeVO.getCaptcha())) {
					response.setRetInfo("验证码不能为空");
					return response;
				} else {
					String modifyVfCode = transferTradeVO.getCaptcha();
					String mobile = user.getMobile();
					key = "trade_captcha_mobileCode" + mobile;
					Integer memModifyVfCode = this.memcachedClient.get(key);
					String mModifyVfCode = memModifyVfCode == null ? "" : memModifyVfCode.toString();
					if (!modifyVfCode.equals(mModifyVfCode)) {
						response.setRetInfo("验证码不正确");
						return response;
					}
				}
			}
			UserTrade userTrade = new UserTrade();
			userTrade.setPwd(transferTradeVO.getPwd());
			userTrade.setUserId(user.getUserId());
			UserTradeDto userTradeDto = userService.checkTradePwd(userTrade);
			if (!"1".equals(userTradeDto.getRetCode())) {
				LOGGER.info("调用 checkTradePwd 返回信息" + JSON.toJSONString(userTradeDto));
				response.setErrorCode(userTradeDto.getRetCode());
				response.setRetInfo(userTradeDto.getRetInfo());
				return response;
			}
			String trancNo = System.currentTimeMillis() + "";
			// accountId 1红旗券，2现金额度
			Integer result = psqwAccountRecordService.userTransferAccountToUser(trancNo, user.getUserId(), amount, otherUser.getUserId(), transferTradeVO.getRealName(), transferTradeVO.getRemark(), BigDecimal.valueOf(0.00));
			String code = "0";
			switch (result) {
			case 0:
				msg = "M券转账成功";
				code = "1";
				if (StringUtils.isNotEmpty(key)) {
					memcachedClient.delete(key);// 成功删除验证码
				}
				try{
					UserOperationRecord userOperationRecord = new UserOperationRecord();
					userOperationRecord.setCreateTime(new Date());
					userOperationRecord.setOperationType("transferAccounts");
					userOperationRecord.setRemark("用户转账，转账金额为：" + amount.toString());
					userOperationRecord.setUserId(user.getUserId().intValue());
					userOperationRecordService.saveUserOperationRecord(userOperationRecord);
				}catch(Exception e){
					LOGGER.error("记录用户转账操作异常："+e.getMessage(),e);
				}
				break;
			case 1:
				msg = "转账金额不能为0";
				break;
			case 2:
				msg = "账户额度不足";
				break;
			case 3:
				msg="转账人账户未激活";
				break;
			case 4:
				msg="接收人账户未激活";
				break;
			case 15:
				msg = "超过单笔交易限额 ";
				break;
			case 6:
				msg = "超过当日转账限额";
				break;
			}
			response.setRetCode(code);
			response.setRetInfo(msg);
		} catch (Exception exception) {
			response.setRetInfo("系统繁忙，请稍后再试");
			LOGGER.error("M券转账错误", exception);
		}
		return response;
	}

	/**
	 * 获取财富中心数据
	 * 
	 * @return
	 */
	public PageBean<UserAccountDetialDto> detailList(HttpServletRequest request, User userInfo, Integer account) {
		try {
			PageBean<UserAccountDetialDto> pageBean = new PageBean<UserAccountDetialDto>();
			String pageStr = request.getParameter("page");
			Integer page = StringUtils.isNotEmpty(pageStr) ? Integer.parseInt(pageStr) : 1;
			String pageSizeStr = request.getParameter("pageSize");
			Integer pageSize = StringUtils.isNotEmpty(pageSizeStr) ? Integer.parseInt(pageSizeStr) : 20;
			pageBean.setPage(page);
			pageBean.setPageSize(pageSize);
			pageBean.setOrder("desc");
			pageBean.setSortFields("id");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userInfo.getUserId());
			if (account != 2) {// 2：消费红旗券
				if (account == 1) {// 1：可用分红额度
					map.put("accountId", 2);
					map.put("isValided", true);
				} else if (account == 3) {// 3：未到账红旗券
					map.put("accountId", 1);
					map.put("isValided", false);
				} else if (account == 4) {// 4：可用红旗券
					map.put("accountId", 1);
					map.put("isValided", true);
				}
				pageBean.setParameter(map);
				pageBean = sqwAccountRecordService.getUserAccountDetailPageList(pageBean);
			} else {
				pageBean.setParameter(map);
				pageBean = sqwAccountRecordService.getUserConsumedHqqDetailPageList(pageBean);
			}
			return pageBean;
		} catch (Exception e) {
			LOGGER.error("detailList error ....");
			throw new RuntimeException("call sqwAccountRecordService error", e);
		}
	}
}
