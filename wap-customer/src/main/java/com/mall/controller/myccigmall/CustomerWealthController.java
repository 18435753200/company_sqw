package com.mall.controller.myccigmall;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.mall.annotation.AuthPassport;
import com.mall.constant.CommonConstant;
import com.mall.constant.RequestContant;
import com.mall.constant.ResponseContant;
import com.mall.controller.base.BaseController;
import com.mall.customer.dto.CashierListItemDto;
import com.mall.customer.dto.CashierRecordDto;
import com.mall.customer.dto.OrderEarningDetailDto;
import com.mall.customer.dto.ShareInterestDetailDto;
import com.mall.customer.dto.UserAccountDetialDto;
import com.mall.customer.model.BizRcode;
import com.mall.customer.model.CShoukuanWRcode;
import com.mall.customer.model.CUserShouyin;
import com.mall.customer.model.SqwAccountRecord;
import com.mall.customer.model.SqwUserAccountBalance;
import com.mall.customer.model.User;
import com.mall.customer.order.common.OrderStatusConstant;
import com.mall.customer.service.PsqwAccountRecordService;
import com.mall.customer.service.SqwAccountRecordService;
import com.mall.customer.service.UserService;
import com.mall.mybatis.utility.PageBean;
import com.mall.pay.common.StringUtils;
import com.mall.service.WealthService;
import com.mall.supplier.model.Supplier;
import com.mall.supplier.model.SupplierApplyYhq;
import com.mall.supplier.service.SupplierApplyYhqService;
import com.mall.supplier.service.SupplierManagerService;
import com.mall.utils.Oauth;
import com.mall.utils.SensitiveInfoUtils;
import com.mall.utils.StringUtil;
import com.mall.vo.TradeRes;
import com.mall.vo.TradeValidateVO;
import com.mall.vo.TransferTradeVO;
import com.mall.vo.WealthVO;

import net.rubyeye.xmemcached.exception.MemcachedException;
import net.sf.json.JSONObject;

/**
 * @author cyy
 */
@Controller
@RequestMapping(value = RequestContant.CUS_WEALTH)
public class CustomerWealthController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerWealthController.class);

	@Autowired
	private WealthService wealthService;
	@Autowired
	private PsqwAccountRecordService psqwAccountRecordService;
	@Autowired
	private SqwAccountRecordService sqwAccountRecordService;
	@Autowired
	private SupplierApplyYhqService supplierApplyYhqService;
	@Autowired
	private SupplierManagerService supplierManagerService;
	@Autowired
	private UserService userService;
	

	/**
	 * 显示财富中心信息
	 * 
	 * @param model
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_WEALTH_INFO, method = RequestMethod.GET)
	public String info(HttpServletRequest request, Model model) {
		LOGGER.info("Wealth info start...");
		User userInfo = getCurrentUser(request);
		if (userInfo == null) {
			LOGGER.info("userinfo  is null");
			return RequestContant.CUSTOMER_TO_LOGIN;
		}
		WealthVO wealth = wealthService.info(userInfo);
		model.addAttribute("user", userInfo);
		model.addAttribute("wealth", wealth);
		try {
			Integer state = sqwAccountRecordService.getUserAccountStatus(userInfo.getUserId(), 1);
			model.addAttribute("state", state);
		} catch (Exception e) {
			LOGGER.error("info error", e);
		}
		LOGGER.info("Wealth info  finished.");
		return ResponseContant.CUS_WEALTH_INFO;
	}

	/**
	 * 转账页面
	 * 
	 * @param model
	 * @return
	 *//*
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_WEALTH_TRANSFER_INDEX, method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model model) {
		User userInfo = getCurrentUser(request);
		if (userInfo == null) {
			LOGGER.info("userinfo  is null");
			return RequestContant.CUSTOMER_TO_LOGIN;
		}
		String mobile = userInfo.getMobile();
		if(mobile.length()!=11){
			model.addAttribute("userId", userInfo.getUserId());
			return "/qrcode/updateMobile";
		}
		mobile = SensitiveInfoUtils.phoneNumHyposensitize(mobile);
		try {
			// 可转账额度
			BigDecimal transferRewardDecimal = sqwAccountRecordService.getUserTransferBalanceLimit(userInfo.getUserId());
			transferRewardDecimal = transferRewardDecimal.compareTo(BigDecimal.ZERO) <= 0 ?BigDecimal.ZERO:transferRewardDecimal;
			request.setAttribute("transferReward", transferRewardDecimal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("mobile", mobile);
		return ResponseContant.CUS_WEALTH_TRANSFER_INDEX;
	}*/
	/**
	 * M转账页面跳转
	 * 
	 * @param model
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_WEALTH_TRANSFER_INDEX_NEW, method = RequestMethod.GET)
	public String indexnew(HttpServletRequest request, Model model) {
		//获取当前登录人
		User userInfo = getCurrentUser(request);
		//判断当前用户是否登陆
		if (userInfo == null) {
			LOGGER.info("userinfo  is null");
			return RequestContant.CUSTOMER_TO_LOGIN;
		}
		//根据当前用户获取当前用户手机号码
		String mobile = userInfo.getMobile();
		//判断手机号的长度是否是11位
		if(mobile.length()!=11){
			//把当前用户存在model域中
			model.addAttribute("userId", userInfo.getUserId());
			return "/qrcode/updateMobile";
		}
		//隐藏手机号码的后四位
		mobile = SensitiveInfoUtils.phoneNumHyposensitize(mobile);
		
		try {
			// 可转账额度
			SqwUserAccountBalance djqBalanceByUser = psqwAccountRecordService.selectDjqBalanceByUser(userInfo.getUserId());
			//判断代金券账户是否为空
			if(djqBalanceByUser!=null){
				request.setAttribute("accountBalance", djqBalanceByUser.getAccountBalance());
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("mobile", mobile);
		
		return ResponseContant.CUS_WEALTH_TRANSFER_INDEX_NEW;
	}

	/**
	 * 红旗券转账效验数据有效性
	 * 
	 * @param request
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_WEALTH_VALIDATE, method = RequestMethod.POST)
	@ResponseBody
	public String tradeValidate(HttpServletRequest request, TradeValidateVO tradeValidateVO) throws Throwable {
		User userInfo = getCurrentUser(request);
		if (userInfo == null) {
			LOGGER.info("userinfo  is null");
			return RequestContant.CUSTOMER_TO_LOGIN;
		}
		TradeRes response = wealthService.tradeValidate(tradeValidateVO, userInfo);
		return JSON.toJSONString(response);
	}
	/**
	 * 代金券转账效验数据有效性
	 * 
	 * @param request
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_WEALTH_VALIDATE_N, method = RequestMethod.POST)
	@ResponseBody
	public String tradeValidatenew(HttpServletRequest request, TradeValidateVO tradeValidateVO) throws Throwable {
		//获取当前登录用户
		User userInfo = getCurrentUser(request);
		//判断当前用户是否登陆
		if (userInfo == null) {
			LOGGER.info("userinfo  is null");
			return RequestContant.CUSTOMER_TO_LOGIN;
		}
		TradeRes response = wealthService.tradeValidatenew(tradeValidateVO, userInfo);
		return JSON.toJSONString(response);
	}
	


	/**
	 * 红旗券转账
	 * 
	 * @param request
	 * @return
	 * @throws Throwable
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_WEALTH_TRANSFER, method = RequestMethod.POST)
	@ResponseBody
	public String transfer(HttpServletRequest request, TransferTradeVO transferTradeVO) throws Throwable {
		User userInfo = getCurrentUser(request);
		if (userInfo == null) {
			LOGGER.info("userinfo  is null");
			return RequestContant.CUSTOMER_TO_LOGIN;
		}
		TradeRes response = wealthService.transfer(transferTradeVO, userInfo);
		return JSON.toJSONString(response);
	}
	/**
	 * 代金券转账
	 * 
	 * @param request
	 * @return
	 * @throws Throwable
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_WEALTH_TRANSFER_N, method = RequestMethod.POST)
	@ResponseBody
	public String transfernew(HttpServletRequest request, TransferTradeVO transferTradeVO) throws Throwable {
		User userInfo = getCurrentUser(request);
		if (userInfo == null) {
			LOGGER.info("userinfo  is null");
			return RequestContant.CUSTOMER_TO_LOGIN;
		}
		TradeRes response = wealthService.transfernew(transferTradeVO, userInfo);
		return JSON.toJSONString(response);
	}
	
	/**
	 * 可用红旗券记录
	 * 
	 * @param model
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_WEALTH_DETAIL, method = RequestMethod.GET)
	public String detail(HttpServletRequest request, Model model, @PathVariable("account")Integer account) {
		User userInfo = getCurrentUser(request);
		if (userInfo == null) {
			LOGGER.info("userinfo  is null");
			return RequestContant.CUSTOMER_TO_LOGIN;
		}
		PageBean<UserAccountDetialDto> pageBean = wealthService.detailList(request, userInfo, account);
		model.addAttribute("pageBean", pageBean);
		account = account == null ? 1 : account;
		String title = "";
		switch (account) {
		case 1:
			title = "可用分红额度";
			break;
		case 2:
			title = "消费红旗券";
			break;
		case 3:
			title = "未到账红旗券";
			break;
		case 4:
			title = "可用红旗券";
			break;
		}
		model.addAttribute("title", title);
		model.addAttribute("account", account);
		return ResponseContant.CUS_WEALTH_DETAIL;
	}
	
	/**
	 * 个人分享额度交易记录以及账户余额
	 * 
	 * @param model
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_WEALTH_DETAIL_FXED, method = RequestMethod.GET)
	public String fxeddetail(HttpServletRequest request, Model model) {
		User userInfo = getCurrentUser(request);
		if (userInfo == null) {
			LOGGER.info("userinfo  is null");
			return RequestContant.CUSTOMER_TO_LOGIN;
		}
			PageBean<SqwAccountRecord> pageBean = psqwAccountRecordService.selectShareLineRecordByUser(userInfo.getUserId(), 1, 10);
			
			List<SqwAccountRecord> result = pageBean.getResult();
			
			for (SqwAccountRecord sqwAccountRecord : result) {
				Long userId = sqwAccountRecord.getUserId();
				User user = userService.findUserById(userId);
				String realName = user.getRealName();
				sqwAccountRecord.setName(realName);
			}
			SqwUserAccountBalance shareLineBalanceByUser = psqwAccountRecordService.selectShareLineBalanceByUser(userInfo.getUserId());
			if(shareLineBalanceByUser!=null){
				model.addAttribute("accountBalance", shareLineBalanceByUser.getAccountBalance());
			}
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("account", 5);
			model.addAttribute("title", "分享额度");
			return ResponseContant.CUS_WEALTH_DETAIL_FXED;
	}
	
	
	/**
	 * 个人分享收入记录以及账户余额
	 * 
	 * @param model
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_WEALTH_DETAIL_GRZXFXSR, method = RequestMethod.GET)
	public String fxwjsdetail(HttpServletRequest request, Model model,Date day) {
		//获取当前登录人
		User userInfo = getCurrentUser(request);
		//判断当前用户是否登陆
		if (userInfo == null) {
			LOGGER.info("userinfo  is null");
			return RequestContant.CUSTOMER_TO_LOGIN;
		}
		if(day==null || "".equals(day)){
			day=new Date();
		}
		//查看用户的现金账户记录
		PageBean<SqwAccountRecord> pageBean = psqwAccountRecordService.selectShareInterestRecordByUserAndDay(userInfo.getUserId(), 1, 10, day);
		BigDecimal balanceSumByUserAndDay = psqwAccountRecordService.selectShareInterestBalanceSumByUserAndDay(userInfo.getUserId(), day);
		if(balanceSumByUserAndDay==null){
			balanceSumByUserAndDay=BigDecimal.valueOf(0.00);
		}
		model.addAttribute("today", day);
		model.addAttribute("balanceSumByUserAndDay", balanceSumByUserAndDay);
		//查出来的结果
		List<SqwAccountRecord> result = pageBean.getResult();
		//遍历result
		for (SqwAccountRecord sqwAccountRecord : result) {
			//获取userID
			Long userId = sqwAccountRecord.getUserId();
			//根据用户id获取user
			User user = userService.findUserById(userId);
			//根据user获取真实姓名
			String realName = user.getRealName();
			//把真实姓名存在对象里，再页面取出来
			sqwAccountRecord.setName(realName);
		}
		//根据用户查看账户信息
		SqwUserAccountBalance shareLineBalanceByUser = psqwAccountRecordService.selectShareLineBalanceByUser(userInfo.getUserId());
		//判断账户是否为空
		if(shareLineBalanceByUser!=null){
			//存在model域中
			model.addAttribute("fxsred", shareLineBalanceByUser.getAccountBalance());
		}
		//查看分享额度的余额
		SqwUserAccountBalance shareInterestBalanceByUser = psqwAccountRecordService.findShareInterestBalanceByUser(userInfo.getUserId());
		//判断分享额度余额是否为空
		if (shareInterestBalanceByUser!=null) {
			model.addAttribute("accountBalance",shareInterestBalanceByUser.getAccountBalance());
		}
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("account", 6);
		model.addAttribute("title", "分享收入");
		return ResponseContant.CUS_WEALTH_DETAIL_GRZXFXSR;
	}
	
	/**
	 * 企业代金券交易记录以及账户余额
	 * 
	 * @param model
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_WEALTH_DETAIL_SHZXDJQ, method = RequestMethod.GET)
	public String sjdjqdetail(HttpServletRequest request, Model model) {
		User userInfo = getCurrentUser(request);
		if (userInfo == null) {
			LOGGER.info("userinfo  is null");
			return RequestContant.CUSTOMER_TO_LOGIN;
		}
		PageBean<SqwAccountRecord> pageBean = psqwAccountRecordService.selectQyDJQBalanceRecord(userInfo.getUserId(),1, 10,null,null);
		
		List<SqwAccountRecord> result = pageBean.getResult();
		
		for (SqwAccountRecord sqwAccountRecord : result) {
			Long userId = sqwAccountRecord.getUserId();
			User user = userService.findUserById(userId);
			String realName = user.getRealName();
			sqwAccountRecord.setName(realName);
			
		}
		
		SqwUserAccountBalance qyDjqBalance = psqwAccountRecordService.selectQyDjqBalance(userInfo.getUserId());
		if(qyDjqBalance!=null){
			model.addAttribute("accountBalance", qyDjqBalance.getAccountBalance());
		}
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("account", 7);
		model.addAttribute("title", "企业M券");
		return ResponseContant.CUS_WEALTH_DETAIL_SHZXDJQ;
	}
	/**
	 * 商家中心订单收入金额交易记录以及账户余额
	 * 
	 * @param model
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_WEALTH_DETAIL_SHZXDDSR, method = RequestMethod.GET)
	public String sjddsrwjsdetail(HttpServletRequest request, Model model,Date day) {
		//获取当前登录用户
		User userInfo = getCurrentUser(request);
		//判断当前用户是否登陆
		if (userInfo == null) {
			LOGGER.info("userinfo  is null");
			return RequestContant.CUSTOMER_TO_LOGIN;
		}
		if(day==null || "".equals(day)){
			day = new Date();
		}
		//查看企业收入现金账户记录
		PageBean<SqwAccountRecord> pageBean = psqwAccountRecordService.selectQyCashBalanceRecordByDay(userInfo.getUserId(), 1, 10, day);
		BigDecimal earningByUserAndDay = psqwAccountRecordService.selectQyEarningByUserAndDay(userInfo.getUserId(), day);
		if(earningByUserAndDay==null){
			earningByUserAndDay=BigDecimal.valueOf(0.00);
		}
		model.addAttribute("today", day);
		model.addAttribute("earningByUserAndDay", earningByUserAndDay);
		//查出来的结果
		List<SqwAccountRecord> result = pageBean.getResult();
		//遍历result
		for (SqwAccountRecord sqwAccountRecord : result) {
			//获取用户id
			Long userId = sqwAccountRecord.getUserId();
			//根据用户id获取用户
			User user = userService.findUserById(userId);
			//根据用户获取真实姓名
			String realName = user.getRealName();
			//把真实姓名存在对象里，在页面取出来
			sqwAccountRecord.setName(realName);
			
		}
		//查看企业收入现金账户余额
		SqwUserAccountBalance qyCashBalanceByUser = psqwAccountRecordService.selectQyCashBalanceByUser(userInfo.getUserId());
		//判断用户账户余额是否为空
		if(qyCashBalanceByUser!=null){
			//把账户余额存model域中
			model.addAttribute("accountBalance", qyCashBalanceByUser.getAccountBalance());
		}
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("account", 8);
		model.addAttribute("title", "订单收入");
		return ResponseContant.CUS_WEALTH_DETAIL_SHZXDDSR;
	}

	/**
	 * 个人代金券交易记录以及账户余额
	 * 
	 * @param model
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_WEALTH_DETAIL_DJQ, method = RequestMethod.GET)
	public String newdetail(HttpServletRequest request, Model model) {
		//获取当前登录用户
		User userInfo = getCurrentUser(request);
		//判断当前用户是否登陆
		if (userInfo == null) {
			LOGGER.info("userinfo  is null");
			return RequestContant.CUSTOMER_TO_LOGIN;
		}
			//获取个人代金券的记录，每页显示十条
			PageBean<SqwAccountRecord> pageBean = psqwAccountRecordService.selectDjqRecordByTime(userInfo.getUserId(), 1, 10,null,null);
			//查出来的结果
			List<SqwAccountRecord> result = pageBean.getResult();
			//遍历result
			for (SqwAccountRecord sqwAccountRecord : result) {
				//获取userId
				Long userId = sqwAccountRecord.getUserId();
				//通过userID获取user
				User user = userService.findUserById(userId);
				//通过user获取真实姓名
				String realName = user.getRealName();
				//把真实姓名存到对象里，在页面取出
				sqwAccountRecord.setName(realName);
			}
			//获取代金券信息
			SqwUserAccountBalance djqBalanceByUser = psqwAccountRecordService.selectDjqBalanceByUser(userInfo.getUserId());
			//判断代金券账户是否为空
			if(djqBalanceByUser!=null){
				//存在model域中
				model.addAttribute("accountBalance", djqBalanceByUser.getAccountBalance());
			}
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("account", 3);
			model.addAttribute("title", "M券");
			return ResponseContant.CUS_WEALTH_DETAIL_DJQ;
	}
	
	
	/**
	 * 分享收入
	 * 
	 * @param model
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_WEALTH_FXSR, method = RequestMethod.GET)
	public String fxsr(HttpServletRequest request, Model model,Integer account) {
		User userInfo = getCurrentUser(request);
		if (userInfo == null) {
			LOGGER.info("userinfo  is null");
			return RequestContant.CUSTOMER_TO_LOGIN;
		}
		BigDecimal fxzsr = psqwAccountRecordService.selectShareInterestBalanceSumByUser(userInfo.getUserId(),null,null);
		BigDecimal fxsryjs = psqwAccountRecordService.selectShareInterestExpenditureSumByUser(userInfo.getUserId());
		List<Integer> list = new ArrayList<Integer>();
		list.add(account);
		list.add(5);
		List<SqwUserAccountBalance> listt =psqwAccountRecordService.getPuserAccountBalance(userInfo.getUserId(),list);
		for (SqwUserAccountBalance sqwUserAccountBalance : listt) {
			if(sqwUserAccountBalance.getAccountId()==5){
				model.addAttribute("fxsred", sqwUserAccountBalance.getAccountBalance());
			}
			if(sqwUserAccountBalance.getAccountId()==6){
				model.addAttribute("fxsrye", sqwUserAccountBalance.getAccountBalance());
			}
		}
	
		model.addAttribute("account", account);
		model.addAttribute("fxzsr", fxzsr);
		model.addAttribute("fxsryjs", fxsryjs);
		return ResponseContant.CUS_WEALTH_DETAIL_FXSR;
	}
	/**
	 * 加载更新详细
	 * 
	 * @param model
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_WEALTH_MORE, method = RequestMethod.GET)
	@ResponseBody
	public String more(HttpServletRequest request, Integer account) {
		User userInfo = getCurrentUser(request);
		if (userInfo == null) {
			LOGGER.info("userinfo  is null");
			return RequestContant.CUSTOMER_TO_LOGIN;
		}
		PageBean<UserAccountDetialDto> pageBean = wealthService.detailList(request, userInfo, account);
		return JSON.toJSONString(pageBean);
	}
	/**
	 * 加载更新详细1
	 * 
	 * @param model
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_WEALTH_MORE_N, method = RequestMethod.POST)
	@ResponseBody
	public String morenew(HttpServletRequest request, Integer account,Integer page) {
		User userInfo = getCurrentUser(request);
		if (userInfo == null) {
			LOGGER.info("userinfo  is null");
			return RequestContant.CUSTOMER_TO_LOGIN;
		}
		PageBean<SqwAccountRecord> pageBean = psqwAccountRecordService.getUserAccountRecord(userInfo.getUserId(), account, null, page, 10,null,null);
		List<SqwAccountRecord> result = pageBean.getResult();
		for (SqwAccountRecord sqwAccountRecord : result) {
			Long userId = sqwAccountRecord.getUserId();
			User user = userService.findUserById(userId);
			String realName = user.getRealName();
			sqwAccountRecord.setName(realName);
	
		}
		return JSON.toJSONString(pageBean);
	}
	/**
	 * 个人中心分享收入加载更新详细
	 * 
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_WEALTH_MORE_GRZXFXSR, method = RequestMethod.POST)
	@ResponseBody
	public String fxsrmorenew(HttpServletRequest request, Integer account,Integer page,String day) throws Exception {
		User userInfo = getCurrentUser(request);
		if (userInfo == null) {
			LOGGER.info("userinfo  is null");
			return RequestContant.CUSTOMER_TO_LOGIN;
		}
		Date date;
		if(day!=null&&!"".equals(day)&&!day.equals("undefined")){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			date=sdf.parse(day);
		}
		else{
			date=new Date();
		}
		//查看用户的现金账户记录
		PageBean<SqwAccountRecord> pageBean = psqwAccountRecordService.selectShareInterestRecordByUserAndDay(userInfo.getUserId(), page, 10, date);
		List<SqwAccountRecord> result = pageBean.getResult();
		for (SqwAccountRecord sqwAccountRecord : result) {
			Long userId = sqwAccountRecord.getUserId();
			User user = userService.findUserById(userId);
			String realName = user.getRealName();
			sqwAccountRecord.setName(realName);
			
		}
		return JSON.toJSONString(pageBean);
	}
	/**
	 * 商户中心订单收入加载更新详细
	 * 
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_WEALTH_MORE_SHZXDDSR, method = RequestMethod.POST)
	@ResponseBody
	public String supplierDdsrMore(HttpServletRequest request, Integer account,Integer page,String day) throws Exception {
		User userInfo = getCurrentUser(request);
		if (userInfo == null) {
			LOGGER.info("userinfo  is null");
			return RequestContant.CUSTOMER_TO_LOGIN;
		}
		Date date;
		if(day!=null&&!"".equals(day)&&!day.equals("undefined")){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			date=sdf.parse(day);
		}
		else{
			date=new Date();
		}
		PageBean<SqwAccountRecord> pageBean = psqwAccountRecordService.selectQyCashBalanceRecordByDay(userInfo.getUserId(), page, 10, date);
		List<SqwAccountRecord> result = pageBean.getResult();
		for (SqwAccountRecord sqwAccountRecord : result) {
			Long userId = sqwAccountRecord.getUserId();
			User user = userService.findUserById(userId);
			String realName = user.getRealName();
			sqwAccountRecord.setName(realName);
			
		}
		return JSON.toJSONString(pageBean);
	}
	
	/**
	 * 商户中心M券加载更新详细
	 * 
	 * @param model
	 * @return
	 */
	
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_WEALTH_MORE_SHZXDJQ, method = RequestMethod.POST)
	@ResponseBody
	public String supplierMore(HttpServletRequest request, Integer account,Integer page) {
		User userInfo = getCurrentUser(request);
		if (userInfo == null) {
			LOGGER.info("userinfo  is null");
			return RequestContant.CUSTOMER_TO_LOGIN;
		}
		PageBean<SqwAccountRecord> pageBean = psqwAccountRecordService.selectQyDJQBalanceRecord(userInfo.getUserId(), page, 10,null,null);
		List<SqwAccountRecord> result = pageBean.getResult();
		for (SqwAccountRecord sqwAccountRecord : result) {
			Long userId = sqwAccountRecord.getUserId();
			User user = userService.findUserById(userId);
			String realName = user.getRealName();
			sqwAccountRecord.setName(realName);
			
		}
		return JSON.toJSONString(pageBean);
	}
	
	/**
	 * 商户中心查看收银员交易记录
	 * 
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_WEALTH_MORE_SHOUYINJILU, method = RequestMethod.POST)
	@ResponseBody
	public String supplierShouYinJiLu(HttpServletRequest request,Integer page,Date day,int id,Model model) throws Exception {
		User userInfo = getCurrentUser(request);
		if (userInfo == null) {
			LOGGER.info("userinfo  is null");
			return RequestContant.CUSTOMER_TO_LOGIN;
		}
		Long supplierId = Long.parseLong(userInfo.getSupplierId());
		CUserShouyin findCUserShouyinById = userService.findCUserShouyinById(id);
		PageBean<CashierRecordDto> pageBean=new PageBean<CashierRecordDto>();
		if(findCUserShouyinById!=null){
			//查看商家收银员的收银记录
			pageBean = userService.getCashierRecordBySupplierAndUserAndDay(supplierId, findCUserShouyinById.getUserId(),findCUserShouyinById.getWxgzhOpenId(), day, page, 10);
		}
		
		return JSON.toJSONString(pageBean);
	}
	
	/**
	 * 用户收银记录加载更多
	 * @param request
	 * @param page
	 * @param day
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_WEALTH_MORE_SHOUYINJILUUSER, method = RequestMethod.POST)
	@ResponseBody
	public String userShouYinJiLu(HttpServletRequest request,Integer page,Date day,Long supplierId,Model model) throws Exception {
		User userInfo = getCurrentUser(request);
		if (userInfo == null) {
			LOGGER.info("userinfo  is null");
			return RequestContant.CUSTOMER_TO_LOGIN;
		}
		//查看商家收银员的收银记录
		PageBean<CashierRecordDto> pageBean = userService.getCashierRecordBySupplierAndUserAndDay(supplierId, userInfo.getUserId(),userInfo.getWeixin(), day, page, 10);
		
		return JSON.toJSONString(pageBean);
	}
	
	/**\
	 * 代金券转优惠券跳转页面
	 * @param trancNo  时间戳
	 * @param fromUserId  用户ID
	 * @param fromPrice  支出金额
	 * @param toUserId  收入ID
	 * @param toPrice 收入金额
	 * @param userRealname 真是姓名
	 * @param memo 备注
	 * @param discount  折扣
	 * @return
	 * @throws Exception
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_WEALTH_REDTRANSFER)
	public String totransferAccountFrom( BigDecimal fromPrice, 
			BigDecimal toPrice,String memo,HttpServletRequest request,Model model) throws Exception{
			User userInfo = getCurrentUser(request);
			SqwUserAccountBalance qyDjqBalance = psqwAccountRecordService.selectQyDjqBalance(userInfo.getUserId());
			if(qyDjqBalance!=null){
				model.addAttribute("accountBalance", qyDjqBalance.getAccountBalance());
			}
			return ResponseContant.CUS_WEALTH_REDTRANSFER;
	}
	
	/**\
	 * 代金券转优惠券
	 * @param account
	 * @param yhqaccount
	 * @param request
	 * @param model
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_WEALTH_REDTRANSFER_N,method = RequestMethod.POST)
	@ResponseBody
	public String transferAccountFrom( BigDecimal account, 
		BigDecimal yhqaccount,HttpServletRequest request,Model model,HttpServletResponse response) throws Exception{
		String trancNo = System.currentTimeMillis()+"";
		User user = getCurrentUser(request);
		int i = psqwAccountRecordService.supTransferAccount(trancNo, user.getUserId(), account, yhqaccount, user.getRealName(), "企业M券转优惠券", BigDecimal.valueOf(0.00));
		return JSON.toJSONString(i);
	}
	
		
	/**\
	 * 申请优惠券跳转页面
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value= RequestContant.CUS_WEALTH_APPLYCOUPONS)
	
	public String toApplyForCoupons(BigDecimal applyVal,SupplierApplyYhq supplierApplyYhq){
		return ResponseContant.CUS_WEALTH_APPLYCOUPONS;
		
	}
	
	/**\
	 * 申请优惠券
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value= RequestContant.CUS_WEALTH_APPLYCOUPONS_N, method = RequestMethod.POST)
	@ResponseBody
	public String ApplyForCoupons(BigDecimal applyVal,HttpServletRequest request){
		SupplierApplyYhq supplierApplyYhq = new SupplierApplyYhq();
		
		//设置申请数量
		supplierApplyYhq.setApplyVal(applyVal);
		User user = getCurrentUser(request);
		String supplierId = user.getSupplierId();
		Supplier supplier = new Supplier();
		supplier.setSupplierId(Long.valueOf(supplierId));
		Supplier supplier2 = supplierManagerService.getSupplier(supplier);
		supplierApplyYhq.setSupplierId(Long.valueOf(supplierId));
		supplierApplyYhq.setSupplierName(supplier2.getName());
		//设置申请时间
		Date date=new Date();
		supplierApplyYhq.setApplyDatetime(date);
		//设置申请状态
		supplierApplyYhq.setStatus(1);
		Integer count = supplierApplyYhqService.applyYhq(supplierApplyYhq);
		return JSON.toJSONString(count);
	}
	
	
	
	/**\
	 * 企业代金券转个人代金券跳转页面
	 * @param fromPrice
	 * @param toPrice
	 * @param memo
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_WEALTH_TURNVOUCHER)
	public String toTurnVoucher( BigDecimal fromPrice, 
			BigDecimal toPrice,String memo,HttpServletRequest request,Model model) throws Exception{
		User userInfo = getCurrentUser(request);
		SqwUserAccountBalance qyDjqBalance = psqwAccountRecordService.selectQyDjqBalance(userInfo.getUserId());
		if(qyDjqBalance!=null){
			model.addAttribute("accountBalance", qyDjqBalance.getAccountBalance());
		}
			return ResponseContant.CUS_WEALTH_TURNVOUCHER;
	}
	
	/**\
	 * 企业代金券转个人代金券
	 * @param account
	 * @param djqaccount
	 * @param request
	 * @param model
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_WEALTH_TURNVOUCHER_N,method = RequestMethod.POST)
	@ResponseBody
	public String TurnVoucher( BigDecimal account,BigDecimal djqaccount,HttpServletRequest request,Model model,HttpServletResponse response) throws Exception{
		//获取时间戳
		String trancNo = System.currentTimeMillis()+"";
		//获取当前用户登录
		User user = getCurrentUser(request);
		//企业代金券转个人代金券
		int n = psqwAccountRecordService.supTransferAccountToUser(trancNo, user.getUserId(), account,user.getUserId(),
				djqaccount, user.getRealName(), "企业M券转个人M券",BigDecimal.valueOf(0.00));
		//返回json格式的字符串
		return JSON.toJSONString(n);                                                          
	}
	
	/**\
	 * 查看跳转页面
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value= RequestContant.CUS_WEALTH_TOSUPPLIERUSERS)
	
	public String tolistSupplierUsers(HttpServletRequest request,Model model,Integer page){
		User user = getCurrentUser(request);
		Long userId = user.getUserId();
		User user2 = userService.findUserById(userId);
		String supplierId = user2.getSupplierId();
		if(page==null||page==0){
			page=1;
		}
		PageBean<User> pageBean = userService.findUsersBySupplierIdPageList(Long.parseLong(supplierId),(byte)2,page,10);
		model.addAttribute("pageBean", pageBean);
		return ResponseContant.CUS_WEALTH_SUPPLIERUSERS;
		
	}

	/**\
	 * 查看商家用户
	 * @param request
	 * @param supplierId
	 * @param relationType
	 * @return
	 */
	@AuthPassport
	@ResponseBody
	@RequestMapping(value = RequestContant.CUS_WEALTH_SUPPLIERUSERSMORE)
	public PageBean<User> moreSupplierUsers(HttpServletRequest request,Model model,Integer page,Integer pageSize){
		User user = getCurrentUser(request);
		Long userId = user.getUserId();
		User user2 = userService.findUserById(userId);
		String supplierId = user2.getSupplierId();
		if(page==null||page==0){
			page=1;
		}
		PageBean<User> pageBean = userService.findUsersBySupplierIdPageList(Long.parseLong(supplierId),(byte)2,page,10);
		return pageBean;
	}
	/**
	 * 订单中心页面跳转(暂时未用)
	 * @param request
	 * @param model
	 * @return
	 */
	@AuthPassport
	@RequestMapping("/toBusCenter")
	public String toBusCenter(HttpServletRequest request, Model model){
		User userInfo = getCurrentUser(request);
		//BigDecimal ddzsr = psqwAccountRecordService.selectQyEarningByUser(userInfo.getUserId());
		BigDecimal ddyjs = psqwAccountRecordService.selectQyCashExpenditureSumByUser(userInfo.getUserId());
		List<Integer> list = new ArrayList<Integer>();
		list.add(8);
		List<SqwUserAccountBalance> listt = psqwAccountRecordService.getPuserAccountBalance(userInfo.getUserId(),list);
		
		request.setAttribute("accountBalance", listt.get(0).getAccountBalance());
		//request.setAttribute("ddzsr", ddzsr);
		request.setAttribute("ddyjs", ddyjs);
		return "/myccig/order/ddsr";
	}
	
	/**\
	 * 查看业务伙伴页面
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value= RequestContant.CUS_WEALTH_TOLISTBUSINESSPARTNER)
	public String tolistBusinessPartner(HttpServletRequest request,Model model){
		User user = getCurrentUser(request);
		String supplierId = user.getSupplierId();
		if(!supplierId.equals("0")){
			Supplier parenSupplier = supplierManagerService.findParentSupplier(Long.parseLong(supplierId));
			model.addAttribute("parenSupplier", parenSupplier);	
		}
		return ResponseContant.CUS_WEALTH_LISTBUSINESSPARTNER;
	}
	
	/**
	 * 跳转到商家中心页面
	 * 
	 * 个人中心商家中心页面的跳转
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_MY_RECIVE_MONEY_N)
	public String toweaithCenter(Model model, HttpServletRequest request){
		User user = getCurrentUser(request);
		if(user==null){
			LOGGER.info("userinfo  is null");
			return RequestContant.CUSTOMER_TO_LOGIN;
		}
		Long userId = user.getUserId();
		User findUserById = userService.findUserById(userId);
		if(findUserById==null){
			return RequestContant.CUSTOMER_TO_LOGIN;
		}
		if(findUserById.getSupplierId()==null || findUserById.getSupplierId().equals("0")){
			return ResponseContant.CUS_USER_MAIN;
		}
		model.addAttribute("user", user);
		return ResponseContant.CUS_MY_RECIVE_MONEY_N;
	}
	
	/**
	 * 查看收银列表
	 * @param request
	 * @param model
	 * @param response
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value=RequestContant.CUS_WEALTH_TOSUPPLIERSHOUYINLIST)
	public String listShouYin(HttpServletRequest request,Model model,HttpServletResponse response){
		User user = getCurrentUser(request);
		//根据用户查找商家id
		String supplierId = user.getSupplierId();
		if(supplierId!=null){
			//查看商家收银员
			PageBean<CUserShouyin> pageBean = userService.selectCashierBySupplierId(Long.valueOf(supplierId),1,10);
			model.addAttribute("pageBean", pageBean);
		}
		return ResponseContant.CUS_WEALTH_SUPPLIERSHOUYINLIST;
		
	}
	
	/**
	 * 设置默认收银员
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@ResponseBody
	@AuthPassport
	@RequestMapping(value=RequestContant.CUS_WEALTH_SETDEFAULT,method = RequestMethod.POST)
	public String setDefault(HttpServletRequest request,Model model,int id){
		int shouyinById = userService.setDefaultShouYinById(id);
		return String.valueOf(shouyinById);
		
	}
	
	/**
	 * 取消默认收银员
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@ResponseBody
	@AuthPassport
	@RequestMapping(value=RequestContant.CUS_WEALTH_CANCELDEFAULT,method = RequestMethod.POST)
	public String cancelDefault(HttpServletRequest request,Model model,int id){
		int removeDefaultShouYinById = userService.removeDefaultShouYinById(id);
		return String.valueOf(removeDefaultShouYinById);
	}
	
	/**
	 * 跳转修改收银员信息
	 * @param request
	 * @param id
	 * @param model
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value=RequestContant.CUS_WEALTH_TOUPDATESYINFO)
	public String toupdateInfo(HttpServletRequest request,int id,Model model){
		CUserShouyin findCUserShouyinById = userService.findCUserShouyinById(id);
		model.addAttribute("findCUserShouyinById", findCUserShouyinById);
		return ResponseContant.CUS_WEALTH_UPDATESYINFO;
	}
	
	/**
	 * 修改收银员信息
	 * @param id
	 * @param kfName
	 * @param mobile
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@AuthPassport
	@RequestMapping(value=RequestContant.CUS_WEALTH_UPDATESYINFO,method = RequestMethod.POST)
	public String updateInfo(int id,String kfName,String mobile,Model model,HttpServletRequest request){
		int updateCUserShouyin = userService.updateCUserShouyin(id, kfName, mobile);
		return JSON.toJSONString(updateCUserShouyin);
	}
	
	/**
	 * 删除收银员
	 * @param id
	 * @return
	 */
	@ResponseBody
	@AuthPassport
	@RequestMapping(value=RequestContant.CUS_WEALTH_DELETESY,method = RequestMethod.POST)
	public String deleteShouYin(int id){
		int deleteCUserShouyin = userService.deleteCUserShouyin(id);
		return JSON.toJSONString(deleteCUserShouyin);
	}
	/**
	 * 扫描添加收银员二维码页面
	 * @param supplierId
	 * @param userId
	 * @param kfName
	 * @param mobile
	 * @param openId
	 * @return
	 */
	@RequestMapping(value=RequestContant.CUS_WEALTH_TOADDSHOUYIN)
	public String toAddShouYin(HttpServletRequest request, String rcid,Model model){
		BizRcode rcode = userService.findBzRcodeByRcodeid(rcid);
		if(rcode==null){
			model.addAttribute("message", "二维码无效！");
			return "/qrcode/notVisit";
		}
		User currentUser = getCurrentUser(request);
		
		if(currentUser==null || currentUser.getWeixin()==null){
			return "/customer/toRegisterShouyinLogin";
		}
		//根据opendId和supplierId 查看是否已经成为收银员
		CUserShouyin cUserShouyin = userService.findCashierBySupplierIdAndUserIdAndOpenId(Long.parseLong(rcode.getBizid()), currentUser.getUserId(), currentUser.getWeixin());
		if(cUserShouyin!=null){
			model.addAttribute("message", "您已经是收银员了！");
			return "/qrcode/notVisit";
		}
		model.addAttribute("supplierId", rcode.getBizid());
		return ResponseContant.CUS_WEALTH_SUPPLIERADDSHOUYIN;
	}
	
	/**
	 * 添加收银员跳转二维码页面
	 * @param supplierId
	 * @param userId
	 * @param kfName
	 * @param mobile
	 * @param openId
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value=RequestContant.CUS_WEALTH_TOADDSHOUYINEWM)
	public String toAddShouYinewm(HttpServletRequest request,Model model){
		User currentUser = getCurrentUser(request);
		if(currentUser!=null){
			model.addAttribute("userId", currentUser.getSupplierId());
			model.addAttribute("rcodeType", 6);
		}
		return "/qrcode/qrCodeShouYin";
	}
	
	/**
	 * 添加收银员
	 * @param request
	 * @param model
	 * @param supplierId
	 * @param userId
	 * @param kfName
	 * @param mobile
	 * @param openId
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value=RequestContant.CUS_WEALTH_SUPPLIERADDSHOUYIN)
	public String addShouYin(HttpServletRequest request,Model model,Long supplierId,String kfName,String mobile){
		//获取当前登录用户
		User userInfo = getCurrentUser(request);
		int addCUserShouyin = userService.addCUserShouyin(supplierId, userInfo.getUserId(), kfName, mobile, userInfo.getWeixin());
		if(addCUserShouyin==2){
			model.addAttribute("message", "添加失败,最多添加5个收银员");
			return "/qrcode/notVisit";
		}else {
			return "/qrcode/gzgzh";
		}
	}
	
	
	/**
	 * 商家收银员收银记录
	 * @param request
	 * @param model
	 * @param day
	 * @return
	 * @throws Exception 
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_WEALTH_SUPPLIERMONEYJL, method = RequestMethod.GET)
	public String supplierShouYinJL(HttpServletRequest request, Model model,String day,int id) throws Exception {
		//获取当前登录用户
		User userInfo = getCurrentUser(request);
		//判断当前用户是否登陆
		if (userInfo == null) {
			LOGGER.info("userinfo  is null");
			return RequestContant.CUSTOMER_TO_LOGIN;
		}
		Long supplierId = Long.parseLong(userInfo.getSupplierId());
		Date date;
		if(day!=null&&!"".equals(day)){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			date=sdf.parse(day);
		}
		else{
			date=new Date();
		}
		CUserShouyin findCUserShouyinById = userService.findCUserShouyinById(id);
		PageBean<CashierRecordDto> pageBean=new PageBean<CashierRecordDto>();
		BigDecimal supplierAndUserAndDay =new BigDecimal(0.00);
		
		if(findCUserShouyinById!=null){
			//查看商家收银员的收银记录
			pageBean = userService.getCashierRecordBySupplierAndUserAndDay(supplierId, findCUserShouyinById.getUserId(),findCUserShouyinById.getWxgzhOpenId(), date, 1, 10);
			//查看商家收银员的收银总金额
			supplierAndUserAndDay = userService.getCashierRecordPriceSumBySupplierAndUserAndDay(supplierId, findCUserShouyinById.getUserId(),findCUserShouyinById.getWxgzhOpenId(), date);
		}
		
		model.addAttribute("id", id);
		model.addAttribute("today", date);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("title", "收银员记录");
		model.addAttribute("supplierAndUserAndDay", supplierAndUserAndDay);
		return ResponseContant.CUS_WEALTH_SUPPLIERMONEYJL;
	}
	
	/**
	 * 用户收银记录
	 * @param request
	 * @param model
	 * @param day
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_WEALTH_USERMONEYJL, method = RequestMethod.GET)
	public String userShouYinJL(HttpServletRequest request, Model model,String day,Long supplierId) throws Exception {
		//获取当前登录用户
		User userInfo = getCurrentUser(request);
		//判断当前用户是否登陆
		if (userInfo == null) {
			LOGGER.info("userinfo  is null");
			return RequestContant.CUSTOMER_TO_LOGIN;
		}
		Date date;
		if(day!=null&&!"".equals(day)){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			date=sdf.parse(day);
		}
		else{
			date=new Date();
		}
		
		//查看商家收银员的收银记录
		PageBean<CashierRecordDto> pageBean = userService.getCashierRecordBySupplierAndUserAndDay(supplierId, userInfo.getUserId(),userInfo.getWeixin(), date, 1, 10);
		//查看商家收银员的收银总金额
		BigDecimal supplierAndUserAndDay = userService.getCashierRecordPriceSumBySupplierAndUserAndDay(supplierId, userInfo.getUserId(),userInfo.getWeixin(),date);
		
		model.addAttribute("today", date);
		model.addAttribute("supplierId", supplierId);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("title", "收银员记录");
		model.addAttribute("supplierAndUserAndDay", supplierAndUserAndDay);
		return ResponseContant.CUS_WEALTH_USERMONEYJL;
	}
	/**
	 * 查看收银员可收银商户
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = RequestContant.CUS_WEALTH_STARTSHOUYIN, method = RequestMethod.GET)
	public String startShouYin(HttpServletRequest request,Model model,int source){
		//获取当前登录用户
		User userInfo = getCurrentUser(request);
		//判断当前用户是否登陆
		if(userInfo==null || userInfo.getWeixin()==null){
			return "/customer/toRegisterShouyinLogin";
		}
		List<CUserShouyin> findCashierByOpenId = userService.findCashierByOpenId(userInfo.getWeixin());
		if(findCashierByOpenId==null || findCashierByOpenId.size()==0 ){
			model.addAttribute("message", "请先成为收银员！");
			return "/qrcode/notVisit";
		}
		List<CashierListItemDto> cashierList = userService.getCashierList(userInfo.getUserId(),userInfo.getWeixin());
		model.addAttribute("cashierList", cashierList);
		model.addAttribute("wxopenId", userInfo.getWeixin());
		return ResponseContant.CUS_WEALTH_STARTSHOUYIN;
	}
	/**
	 * 查看收银员所在商户
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = RequestContant.CUS_WEALTH_STARTSHOUYIN2, method = RequestMethod.GET)
	public String startShouYin2(HttpServletRequest request,Model model,int source){
		//获取当前登录用户
		User userInfo = getCurrentUser(request);
		//判断当前用户是否登陆
		if(userInfo==null || userInfo.getWeixin()==null){
			return "/customer/toRegisterShouyinLogin";
		}
		List<CUserShouyin> findCashierByOpenId = userService.findCashierByOpenId(userInfo.getWeixin());
		if(findCashierByOpenId==null || findCashierByOpenId.size()==0 ){
			model.addAttribute("message", "请先成为收银员！");
			return "/qrcode/notVisit";
		}
		String jsapiTicket = null;
		// 获取access_token
		// 获取jsapi_ticket
		try {
			jsapiTicket = this.memcachedClient
					.get(CommonConstant.WEIXIN_JSAPI_TICKET);
			if (StringUtils.isEmpty(jsapiTicket)) {
				String accessTokenResult = Oauth.getAccessToken();
				JSONObject accessTokenObject = JSONObject
						.fromObject(accessTokenResult);
				String accessToken = accessTokenObject
						.getString("access_token");
				// 保存memcache
				this.memcachedClient.add(CommonConstant.WEIXIN_ACCESS_TOKEN,
						CommonConstant.WEIXIN_ACCESS_TOKEN_VAILED_TIME,
						accessToken);
				String jsapiTicketResult = Oauth.getJsapiTicket(accessToken);
				JSONObject jsapiTicketObject = JSONObject
						.fromObject(jsapiTicketResult);
				jsapiTicket = jsapiTicketObject.getString("ticket");
				// 保存memcache
				this.memcachedClient.add(CommonConstant.WEIXIN_JSAPI_TICKET,
						CommonConstant.WEIXIN_JSAPI_TICKET_VAILED_TIME,
						jsapiTicket);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		// 权限验证
		String url = request.getRequestURL().toString()+"?source=1";
		Map<String, String> signResultMap = Oauth.sign(jsapiTicket, url);
		
		List<CashierListItemDto> cashierList = userService.getCashierList(userInfo.getUserId(),userInfo.getWeixin());
		model.addAttribute("cashierList", cashierList);
		model.addAttribute("wxopenId", userInfo.getWeixin());
		model.addAttribute("signResultMap", signResultMap);
		model.addAttribute("isDisplayWeiXinFlag", true);
		return ResponseContant.CUS_WEALTH_SUPPLIERSHOUYINJILU;
	}
	
	/**
	 * 开始收银
	 * @return
	 */
	@RequestMapping(value = RequestContant.CUS_WEALTH_OPENSHOUYIN, method = RequestMethod.GET)
	public String openShouYin(HttpServletRequest request,Model model,String url){
		//获取当前登录用户
		User userInfo = getCurrentUser(request);
		//判断当前用户是否登陆
		if(userInfo==null || userInfo.getWeixin()==null){
			return "/customer/toRegisterShouyinLogin";
		}
		if(url==null || ""==url){
			model.addAttribute("message", "二维码无效,请扫描收银二维码！");
			return "/qrcode/notVisit";
		}
		Map<String, String> urlSplit = StringUtil.urlSplit(url);
		String rcodeid = urlSplit.get("rcid");
		if(rcodeid==null || rcodeid==""){
			model.addAttribute("message", "二维码无效,请扫描收银二维码11！");
			return "/qrcode/notVisit";
		}
		//根据rcodeid查出二维码信息
		BizRcode bizRcode = userService.findBzRcodeByRcodeid(rcodeid);
		if(bizRcode==null || bizRcode.getBizid()==null || (bizRcode.getRcodetype()!=12 && bizRcode.getRcodetype()!=2)){
			model.addAttribute("message", "二维码无效,请扫描收银二维码12！");
			return "/qrcode/notVisit";
		}
		
		Supplier supplier = supplierManagerService.findSupplier(Long.parseLong(bizRcode.getBizid()));
		if(supplier==null){
			model.addAttribute("message", "商户不存在！");
			return "/qrcode/notVisit";
		}
		
		CShoukuanWRcode cShoukuanWRcode=new CShoukuanWRcode();
		if(bizRcode.getRcodetype()==12){
			cShoukuanWRcode = userService.findShouKuanRcodeByRcodeId(rcodeid);
			if(cShoukuanWRcode==null){
				model.addAttribute("message", "收银窗口不存在");
				return "/qrcode/notVisit";
			}
		}
		//是否是此商家的收银员
		CUserShouyin cUserShouyin = userService.findCashierBySupplierIdAndUserIdAndOpenId(Long.parseLong(bizRcode.getBizid()), userInfo.getUserId(), userInfo.getWeixin());
		if(cUserShouyin==null){
			model.addAttribute("message", "您不是此商户收银员！");
			return "/qrcode/notVisit";
		}
		
		//是否有当前在线收银员
		CUserShouyin userShouyin = userService.selectCashierByRcodeId(rcodeid);
		model.addAttribute("userShouyin", userShouyin);
		model.addAttribute("supplierName", supplier.getName());
		model.addAttribute("cShoukuanWRcode", cShoukuanWRcode);
		model.addAttribute("wxopenId", userInfo.getWeixin());
		model.addAttribute("rcodeid", rcodeid);
		model.addAttribute("supplierId", bizRcode.getBizid());
		return ResponseContant.CUS_WEALTH_STARTSHOUYIN2;
	}
	/**
	 * 收银员上线
	 * @param request
	 * @param remark
	 * @return
	 */
	@ResponseBody
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_WEALTH_SHOUYINONLINE, method = RequestMethod.POST)
	public String ShouYinOnline(HttpServletRequest request,String remark,String supplierId){
		//获取当前登录用户
		User userInfo = getCurrentUser(request);
		int cashierLogin = userService.cashierLogin(Long.parseLong(supplierId), userInfo.getUserId(), userInfo.getWeixin(), remark);
		return JSON.toJSONString(cashierLogin);
	}
	
	/**
	 * 收银员上线
	 * @param request
	 * @param remark
	 * @return
	 */
	@ResponseBody
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_WEALTH_SHOUYINONLINE2, method = RequestMethod.POST)
	public String ShouYinOnline2(HttpServletRequest request,String remark,String supplierId,String rcodeId,Model model){
		//获取当前登录用户
		User userInfo = getCurrentUser(request);
		int cashierLogin = userService.cashierLoginWithRcode(Long.valueOf(supplierId), userInfo.getUserId(), userInfo.getWeixin(), rcodeId, remark);
		return JSON.toJSONString(cashierLogin);
	}
	/**
	 * 收银员下线
	 * @param request
	 * @param remark
	 * @return
	 */
	@ResponseBody
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_WEALTH_SHOUYINNOONLINE, method = RequestMethod.POST)
	public String ShouYinNoOnline(HttpServletRequest request,String remark,String supplierId){
		//获取当前登录用户
		User userInfo = getCurrentUser(request);
		int cashierLogout = userService.cashierLogout(Long.parseLong(supplierId), userInfo.getUserId(), userInfo.getWeixin(), remark);
		return JSON.toJSONString(cashierLogout);
	}
	
	
	/**
	 * 个人中心分享收入日结算
	 * @param request
	 * @param model
	 * @param day
	 * @return
	 * @throws Exception
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_WEALTH_DETAIL_GRZXFXSR_RJS, method = RequestMethod.GET)
	public String riJieSuan(HttpServletRequest request, Model model,String day) throws Exception{
		//获取当前登录人
		User userInfo = getCurrentUser(request);
		//判断当前用户是否登陆
		if (userInfo == null) {
			LOGGER.info("userinfo  is null");
			return RequestContant.CUSTOMER_TO_LOGIN;
		}
		Date date;
		if(day!=null&&!"".equals(day)){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			date=sdf.parse(day);
		}
		else{
			date=new Date();
		}
		//查看用户的现金账户记录
		PageBean<SqwAccountRecord> pageBean = psqwAccountRecordService.selectShareInterestRecordByUserAndDay(userInfo.getUserId(), 1, 10, date);
		BigDecimal balanceSumByUserAndDay = psqwAccountRecordService.selectShareInterestBalanceSumByUserAndDay(userInfo.getUserId(), date);
		if(balanceSumByUserAndDay==null){
			balanceSumByUserAndDay=BigDecimal.valueOf(0.00);
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		model.addAttribute("today", sdf.format(date));
		model.addAttribute("balanceSumByUserAndDay", balanceSumByUserAndDay);
		//查出来的结果
		List<SqwAccountRecord> result = pageBean.getResult();
		//遍历result
		for (SqwAccountRecord sqwAccountRecord : result) {
			//获取userID
			Long userId = sqwAccountRecord.getUserId();
			//根据用户id获取user
			User user = userService.findUserById(userId);
			//根据user获取真实姓名
			String realName = user.getRealName();
			//把真实姓名存在对象里，再页面取出来
			sqwAccountRecord.setName(realName);
		}
		//根据用户查看账户信息
		SqwUserAccountBalance shareLineBalanceByUser = psqwAccountRecordService.selectShareLineBalanceByUser(userInfo.getUserId());
		//判断账户是否为空
		if(shareLineBalanceByUser!=null){
			//存在model域中
			model.addAttribute("fxsred", shareLineBalanceByUser.getAccountBalance());
		}
		//查看分享额度的余额
		SqwUserAccountBalance shareInterestBalanceByUser = psqwAccountRecordService.findShareInterestBalanceByUser(userInfo.getUserId());
		//判断分享额度余额是否为空
		if (shareInterestBalanceByUser!=null) {
			model.addAttribute("accountBalance",shareInterestBalanceByUser.getAccountBalance());
		}
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("account", 6);
		model.addAttribute("title", "分享收入");
		return ResponseContant.CUS_WEALTH_DETAIL_GRZXFXSR_RJS;
	
	}
	/**
	 * 商家中心的订单收入日结算
	 * @param request
	 * @param model
	 * @param day
	 * @return
	 * @throws Exception
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_WEALTH_DETAIL_SHZXDDSR_RJS, method = RequestMethod.GET)
	public String supplierRiJieSuan(HttpServletRequest request, Model model,String day) throws Exception {
		//获取当前登录用户
		User userInfo = getCurrentUser(request);
		//判断当前用户是否登陆
		if (userInfo == null) {
			LOGGER.info("userinfo  is null");
			return RequestContant.CUSTOMER_TO_LOGIN;
		}
		Date date;
		if(day!=null&&!"".equals(day)){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			date=sdf.parse(day);
		}
		else{
			date=new Date();
		}
		//查看企业收入现金账户记录
		PageBean<SqwAccountRecord> pageBean = psqwAccountRecordService.selectQyCashBalanceRecordByDay(userInfo.getUserId(), 1, 10, date);
		BigDecimal earningByUserAndDay = psqwAccountRecordService.selectQyEarningByUserAndDay(userInfo.getUserId(), date);
		if(earningByUserAndDay==null){
			earningByUserAndDay=BigDecimal.valueOf(0.00);
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		model.addAttribute("today", sdf.format(date));
		model.addAttribute("earningByUserAndDay", earningByUserAndDay);
		//查出来的结果
		List<SqwAccountRecord> result = pageBean.getResult();
		//遍历result
		for (SqwAccountRecord sqwAccountRecord : result) {
			//获取用户id
			Long userId = sqwAccountRecord.getUserId();
			//根据用户id获取用户
			User user = userService.findUserById(userId);
			//根据用户获取真实姓名
			String realName = user.getRealName();
			//把真实姓名存在对象里，在页面取出来
			sqwAccountRecord.setName(realName);
			
		}
		//查看企业收入现金账户余额
		SqwUserAccountBalance qyCashBalanceByUser = psqwAccountRecordService.selectQyCashBalanceByUser(userInfo.getUserId());
		//判断用户账户余额是否为空
		if(qyCashBalanceByUser!=null){
			//把账户余额存model域中
			model.addAttribute("accountBalance", qyCashBalanceByUser.getAccountBalance());
		}
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("account", 8);
		model.addAttribute("title", "订单收入");
		return ResponseContant.CUS_WEALTH_DETAIL_SHZXDDSR_RJS;
	}
	
	/**
	 * 个人中心分享收入详情
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_WEALTH_DETAIL_FXSRXQ, method = RequestMethod.GET)
	public String detailFxsrxq(HttpServletRequest request,Long recordId,Model model){
		//获取当前登录用户
		User userInfo = getCurrentUser(request);
		//判断当前用户是否登陆
		if (userInfo == null) {
			LOGGER.info("userinfo  is null");
			return RequestContant.CUSTOMER_TO_LOGIN;
		}
		
		ShareInterestDetailDto detailByRecordId = psqwAccountRecordService.selectShareInterestDetailByRecordId(recordId);
		model.addAttribute("detailByRecordId", detailByRecordId);
		return ResponseContant.CUS_WEALTH_DETAIL_FXSRXQ;
	}
	/**
	 * 商家中心订单收入详情
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_WEALTH_DETAIL_SHZXDDSRXQ, method = RequestMethod.GET)
	public String orderDdsrxq(HttpServletRequest request,Long recordId,Model model){
		//获取当前登录用户
		User userInfo = getCurrentUser(request);
		//判断当前用户是否登陆
		if (userInfo == null) {
			LOGGER.info("userinfo  is null");
			return RequestContant.CUSTOMER_TO_LOGIN;
		}
		
		OrderEarningDetailDto orderEarningDetailDto = psqwAccountRecordService.selectOrderEarningDetailByRecordId(recordId);
		model.addAttribute("orderEarningDetailDto", orderEarningDetailDto);
		return ResponseContant.CUS_WEALTH_DETAIL_SHZXDDSRXQ;
	}
	

	

}
