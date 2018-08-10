package com.mall.controller.user;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Null;

import org.csource.upload.UploadFileUtil;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.architect.redis.JedisManager;
import com.mall.commons.utils.DateUtil;
import com.mall.customer.dto.SqwAllUserAccountInfo;
import com.mall.customer.dto.SqwOneUserAccountInfo;
import com.mall.customer.dto.SqwQYAccountDetailDto;
import com.mall.customer.dto.SqwQiyeTransferRecordDto;
import com.mall.customer.dto.UserAccountDetialDto;
import com.mall.customer.model.SqwQiyeAccountBalance;
import com.mall.customer.model.SqwQiyeAccountRecode;
import com.mall.customer.model.User;
import com.mall.customer.model.UserStance;
import com.mall.mybatis.utility.PageBean;
import com.mall.platform.model.Platform;
import com.mall.platform.model.PlatformUser;
import com.mall.supplier.model.Supplier;
import com.mall.supplier.model.SupplierOperateLog;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Common;
import com.mall.utils.CommonConstant;
import com.mall.utils.Constants;
import com.mall.utils.CookieHelper;
import com.mall.utils.DateTool;
import com.mall.utils.FindUserLoginIpUtil;
import com.mall.utils.MD5;
import com.mall.utils.ProduceUserName;
import com.mall.utils.SendSMSUtil;
import com.mall.utils.VerifyCodeUtils;
import com.mall.utils.getUUID;

/**
 * 平台.
 * @author zhouzb
 *
 */
@Controller
public class PlatformController extends BaseController {
	
	/**
	 * LOGGER.
	 */
	private static final Log LOGGER = LogFactory.getLogger(PlatformController.class);
	
	/**
	 * 绑定时间转换.
	 * @param binder WebDataBinder
	 */
	@InitBinder
	//此方法用于日期的转换
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	/**
	 * 跳转注册页面.
	 * @param toLoginUI.
	 * @return 添加商户.
	 */
	@RequestMapping("/platform/registUI")
	public String toRegistUI(){
		LOGGER.info("跳转注册页面");
		return "/user/regist";
	}
	@RequestMapping(value="/platform/welcome")
	public String toWelcome(){
		LOGGER.info("登录到欢迎页面");
		LOGGER.info("商户Id:"+getCurrentPlatformId());
		
		LOGGER.info("用户:"+getCurrentUser().getUsername());
		LOGGER.info("用户ID:"+getCurrentUser().getId());
		System.out.println("redirect:/POPproduct/getPro");
		return "redirect:/POPproduct/getPro";
	}
	/**
	 * 添加商户注册.
	 * @param request HttpServletRequest
	 * @return 添加的用户id.
	 */
	@RequestMapping("/platform/regist")
	public String regist(HttpServletRequest request){
		LOGGER.info("添加商户注册");
		Platform p=new Platform();
		p.setName("test");
		long id=RemoteServiceSingleton.getInstance().getPlatformManagerService().insert(p);	
	
	    PlatformUser user=new PlatformUser();
		user.setStatus(Constants.NUM1);
		user.setPlatformId(id);
		user.setIsAdmin(Constants.NUM1);
		user.setPlatformId(getCurrentPlatformId());
	    user.setName(request.getParameter("loginName"));
		user.setPassword(MD5.encrypt(request.getParameter("password")));
		
		//getObjectFromRequest(partner,request);
		//platformManagerService.
		RemoteServiceSingleton.getInstance().getPlatformUserManagerService().add(user);
		LOGGER.info("用户名="+user.getName());
		request.setAttribute("name", user.getName());
		return "/user/regist_success";
	}
	
	/**
	 * 获取商户基本信息.
	 * @param map Map<String,Object>
	 */
	@RequestMapping("/platform/jiben")
	public String platformInfo(Map<String,Object> map){
		LOGGER.info("获取商户基本信息."+map);
		Platform platform=RemoteServiceSingleton.getInstance()
				.getPlatformManagerService().findPlatform(getCurrentPlatformId());
        map.put("data", platform);
		return "user/jiben";
	}
	/**
	 * 更新商户基本信息.
	 * @param iconUrl MultipartFile.
	 * @param map Map<String,Object>.
	 * @param platform Platform.
	 * @return
	 */
	@RequestMapping("/platform/update")
	public String updatePlatform(MultipartFile iconUrl,Map<String,Object> map ,Platform platform ){
		try {
				
			if(null!=iconUrl&&iconUrl.getSize()>0){ 
	    		String myfileUrl="";
	    		LOGGER.info("更新用户基本信息头像:"+iconUrl.getOriginalFilename()+"OriginalFilename:"+
	    								iconUrl.getName()+" size:"+iconUrl.getSize());
				try {
					myfileUrl = UploadFileUtil.uploadFile(iconUrl.getInputStream(), 
							Common.getFileExt2(iconUrl.getOriginalFilename()), null);
				} catch (Exception e) {
					e.printStackTrace();
				}
	        	platform.setIcon(Constants.FILESERVER1+myfileUrl);
	        } 
			RemoteServiceSingleton.getInstance().getPlatformManagerService().update(platform);
		    platform=RemoteServiceSingleton.getInstance()
		    		.getPlatformManagerService().findPlatform(getCurrentPlatformId());
		    //更新缓存中的商户信息
		    if(platform!=null){
			    String sid= CookieHelper.getCookie(getRequest(), Constants.SESSION_ID).getValue();
			    memCachedClient.set(sid+Constants.PLATFORM ,Constants.MAMCACHEDTIME,platform);
			}
	        map.put("data", platform);
			
		} catch (Exception e) {
			LOGGER.error("更新商户基本信息.出错 ");
			
			LOGGER.error("商户Id:"+getCurrentPlatformId());
			
			LOGGER.error("用户:"+getCurrentUser().getUsername());
			LOGGER.error("用户ID:"+getCurrentUser().getId());
			LOGGER.error(e.getMessage()+e);
		}
		return "/user/jiben";
	}
	
	/**
	 * <p>获取用户列表</p>
	 * @param map
	 * @param message
	 * @param userParam
	 * @param paramPage
	 * @param request
	 * @return
	 * @auth:zw
	 */
	@RequestMapping("/platform/userChecklist")
	public String userChecklist(Map<String,Object> map,String message,
			User userParam,PageBean<User>  paramPage,HttpServletRequest request){
		try{

			paramPage.setSortFields("create_time,status");
			paramPage.setOrder("desc,asc");
			if(userParam!=null&& userParam.getUserId() != null){
				userParam.setUserId(userParam.getUserId());
			}
			if(userParam!=null&&!Common.isEmpty(userParam.getUserName())){
				userParam.setUserName(userParam.getUserName().trim());	
			}
			if(null != userParam && !Common.isEmpty(userParam.getMobile())){
				userParam.setMobile(userParam.getMobile().trim());	
			}
			if(null != userParam && userParam.getFreezeStatus() != null){
				userParam.setFreezeStatus(userParam.getFreezeStatus());
			}
			if(null != userParam && userParam.getStars() != null){
				userParam.setStars(userParam.getStars());
			}
			if(null != userParam && Common.isEmpty(userParam.getTjMobile())){
				userParam.setTjMobile(userParam.getTjMobile());
			}
			if(null != userParam && userParam.getCreateTime()!=null){
				userParam.setCreateTime(userParam.getCreateTime());
			}
			paramPage.setParameter(userParam);
			paramPage = RemoteServiceSingleton.getInstance().getUserService().getNewPageList(paramPage);
			//获取账号的消费金额、消费红旗券、已到账红旗券、未到账红旗券、分红额度
			List<User> userList = paramPage.getResult();
			if(userList != null & userList.size() > 0){
				for(User u : userList){
					try {
						//消费红旗券 1
						BigDecimal hqq = RemoteServiceSingleton.getInstance().getSqwAccountRecordService().getUserAccountBalance(u.getUserId(), 1);
						u.setConsumeHqq(String.valueOf(hqq));
						//消费的现金 2
						BigDecimal fenhong = RemoteServiceSingleton.getInstance().getSqwAccountRecordService().getUserAccountBalance(u.getUserId(), 2);
						u.setConsumeMoney(String.valueOf(fenhong));
						//可用hqq
						BigDecimal kyHqq = RemoteServiceSingleton.getInstance().getSqwAccountRecordService().getUserAccountRecordBalance(u.getUserId(), 1);
						u.setAvailHqq(String.valueOf(kyHqq));
						//可用分红额度
						BigDecimal kyFenhong = RemoteServiceSingleton.getInstance().getSqwAccountRecordService().getUserAccountRecordBalance(u.getUserId(), 2);
						u.setKyFenHong(String.valueOf(kyFenhong));
						//未到账红旗券
						BigDecimal noToHqq = RemoteServiceSingleton.getInstance().getSqwAccountRecordService().getUserAccountBalanceInvalid(u.getUserId(), 1);
						u.setNoHqq(String.valueOf(noToHqq));
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}
			
			map.put("page", paramPage);
			map.put("message", message);
		}catch (Exception e) {
			LOGGER.error("获取用户列表. 异常  msg："+e);
		} finally {
		}
		return "user/userCheck";
	}
	/**
	 * <p>修改用于冻结状态</p>
	 * @param userId
	 * @param freezeStatus 默认0未冻结，1已冻结
	 * @return
	 * @auth:zw
	 */
	@RequestMapping("/platform/modifyFreezeStatus")
	@ResponseBody
	public String modifyFreezeStatus(Long userId,Integer freezeStatus,HttpServletRequest request){
		int flag = 1;
		try{
			User modifyUser = new User();
			if(userId != null){
				modifyUser.setUserId(userId);
			}
			if(freezeStatus != null){
				modifyUser.setFreezeStatus(freezeStatus);
			}
			int status = RemoteServiceSingleton.getInstance().getUserService().updateUserfreezeStatus(modifyUser);
			LOGGER.info("冻结操作结果 status："+status);
			SupplierOperateLog operLog = new SupplierOperateLog();
			operLog.setOperator(getCurrentUser().getUsername());
			operLog.setIp(FindUserLoginIpUtil.getIpAddrByRequest(request));
//			operLog.setIp(FindUserLoginIpUtil.getRealIp());
			operLog.setDescription("该操作来自于unicorn平台中，平台会员管理菜单下的账号冻结功能");
			if(freezeStatus == 1){		//冻结操作
				operLog.setOperatorContent("冻结账号操作,该账号信息：userId="+userId);
			}else if(freezeStatus == 0){	//取消冻结操作
				operLog.setOperatorContent("取消冻结账号操作,该账号信息：userId="+userId);
			}
			try {
				long result = RemoteServiceSingleton.getInstance().getSupplierOperateLogService().insert(operLog);
				LOGGER.info("保存log日志操作结果 result："+result);
			} catch (Exception e) {
				LOGGER.error("保存账号冻结操作. 异常  msg："+e);
				e.printStackTrace();
			}
			
		}catch (Exception e) {
			flag = 0;
			LOGGER.error("审核商户. 异常  msg："+e);
		}
		return  JSON.toJSONString(flag);
	}
	
	/**
	 * <p>获取用户列表</p>
	 * @param map
	 * @param message
	 * @param userParam
	 * @param paramPage
	 * @param request
	 * @return
	 * @auth:zw
	 */
	@RequestMapping("/platform/userUpdateMobilelist")
	public String userUpdateMobilelist(Map<String,Object> map,String message,
			User userParam,PageBean<User>  paramPage,HttpServletRequest request){
		try{

			paramPage.setSortFields("create_time,status");
			paramPage.setOrder("desc,asc");
			if(userParam!=null&& userParam.getUserId() != null){
				userParam.setUserId(userParam.getUserId());
			}
			if(userParam!=null&&!Common.isEmpty(userParam.getUserName())){
				userParam.setUserName(userParam.getUserName().trim());	
			}
			if(null != userParam && !Common.isEmpty(userParam.getMobile())){
				userParam.setMobile(userParam.getMobile().trim());	
			}
			paramPage.setParameter(userParam);
			paramPage = RemoteServiceSingleton.getInstance().getUserService().getNewPageList(paramPage);
			map.put("page", paramPage);
			map.put("message", message);
		}catch (Exception e) {
			LOGGER.error("获取用户列表. 异常  msg："+e);
		} finally {
		}
		return "user/userUpdateInfo";
	}
	/**
	 * <p>跳转到用户基本信息</p>
	 * @param map
	 * @param newUserId
	 * @param request
	 * @return
	 * @auth:zw
	 */
	@RequestMapping("/platform/toUserInfoById")
	public String toUserInfoById(Map<String,Object> map, String newUserId, HttpServletRequest request, @RequestParam(required = false) String page){
		User user = RemoteServiceSingleton.getInstance().getUserService().findUserById(Long.valueOf(newUserId));
		map.put("user", user);
		map.put("page", page);
		return "user/userInfo";
	}
	/**
	 * <p>检测手机号是否存在</p>
	 * @param tjName
	 * @return
	 * @auth:zw
	 */
	@RequestMapping("/platform/checkTJUserIsExists")
	@ResponseBody
	public String checkTJUserIsExists(String tjName) {
		int flag = 1;
		if(StringUtils.isEmpty(tjName)){
			flag = 0;
		}else{
			User tjUser = new User();
			tjUser.setMobile(tjName);;
			String tjUserExists = RemoteServiceSingleton.getInstance()
					.getUserService().isTJUserExists(tjUser);
			if(tjUserExists.equals("false")){		//不存在
				flag = 0;
			}
		}
		return JSON.toJSONString(flag);
	}
	/**
	 * <p>修改用户手机号</p>
	 * @param userId
	 * @param newMobile
	 * @return
	 * @auth:zw
	 */
	@RequestMapping("/platform/modifyUserMobile")
	@ResponseBody
	public String modifyUserMobile(String userId,String newMobile,String oldMobile) {
		int flag = 1;
		User user = new User();
		user.setUserId(Long.valueOf(userId));
		user.setMobile(newMobile);
		try {
			int result = RemoteServiceSingleton.getInstance().getUserService().updateUserfreezeStatus(user);	//修个用户手机
			logger.info("修改结果为result："+result);
		} catch (Exception e) {
			flag = 0;
			logger.error("修改手机号出现异常：e=", e);
			e.printStackTrace();
		}
		List<User> tjUserList = RemoteServiceSingleton.getInstance().getUserService().findUserByTjMobile(oldMobile);
		if(tjUserList != null && tjUserList.size()>0){
			for(User u : tjUserList){
				User newTjUser = new User();
				newTjUser.setUserId(u.getUserId());
				newTjUser.setTjMobile(newMobile);
				try {
					int status = RemoteServiceSingleton.getInstance().getUserService().updateUserfreezeStatus(newTjUser);	//修改tjMobile
					logger.info("修改结果为status："+status);
				} catch (Exception e) {
					flag = 0;
					e.printStackTrace();
				}
			}
		}
		try {
			RemoteServiceSingleton.getInstance().getSupplierManagerService().findSupplierExistAndModifyTj(newMobile,oldMobile);
		} catch (Exception e) {
			logger.error("根据手机号检测是否被用作邀请码，如有并修改新的 出现异常：e=", e);
			e.printStackTrace();
		}
		return JSON.toJSONString(flag);
	}
	/**
	 * <p>跳转用户占位页面</p>
	 * @param map
	 * @param request
	 * @return
	 * @auth:zw
	 */
	@RequestMapping("/platform/toUserStance")
	public String toUserStance(Map<String,Object> map,HttpServletRequest request,PageBean<UserStance>  paramPage){
		String currentUserName = getCurrentUser().getUsername();
		UserStance uStance = new UserStance();
		uStance.setOperator(currentUserName);
		paramPage.setParameter(uStance);
		paramPage = RemoteServiceSingleton.getInstance().getUserStanceService().findPage(paramPage);
//		List<UserStance> userStanceList = RemoteServiceSingleton.getInstance().getUserStanceService().findUserStanceSucc(currentUserName);
		map.put("page", paramPage);
		return "user/userStance";
	}
	/**
	 * <p>占位操作</p>
	 * @param tjName
	 * @return
	 * @auth:zw
	 */
	@RequestMapping("/platform/stanceUser")
	@ResponseBody
	public String stanceUser(int startUserId,int endUserId) {
		int flag = 1;
		int num = RemoteServiceSingleton.getInstance().
				getUserService().checkIsExistUser(Long.valueOf(startUserId),Long.valueOf(endUserId));
		if(num >= 1){	//表示存在
			flag = 2;
			return JSON.toJSONString(flag);
		}
		Random random = new Random();
		for(int i=startUserId;i<=endUserId;i++){
			User user = new User();
			int mobile = random.nextInt(899999) + 100000;
			String newMobile = "13800"+mobile;
			String produce = ProduceUserName.produce(String.valueOf(newMobile));
			user.setUserId(Long.valueOf(i));
			user.setMobile(newMobile);
			user.setRealName(newMobile);
			int password = random.nextInt(899999) + 100000;
			user.setPassword(String.valueOf(password));
			user.setUserName(produce);
			user.setNickName(produce);
			user.setRegType((byte) 0);
			user.setOrigin("880");
//			user.setTjMobile("13800000111");
			int result = 0;
			try {
				result = RemoteServiceSingleton.getInstance().
						getUserService().saveUser(user);
			} catch (Exception e) {
				flag = 0;	//失败
				logger.error("Exception,userService.saveUser has error,message:",e);
				e.printStackTrace();
				return JSON.toJSONString(flag);
			}
			if(result == 1){
				UserStance uStance = new UserStance();
				uStance.setStatus(0);
				uStance.setUserId(Long.valueOf(i));
				uStance.setOperatorId(Long.valueOf(getCurrentUser().getSequenceId()));
				uStance.setOperator(getCurrentUser().getUsername());
				try {
					//判断改号是否在占位表里存在
					UserStance userStance = RemoteServiceSingleton.getInstance().getUserStanceService().getUserStanceByUserId(Long.valueOf(i));
					if(userStance == null){
						int saveStatus = RemoteServiceSingleton.getInstance().getUserStanceService().saveUserStance(uStance);
						if(saveStatus != 1){
							flag = 0;	//失败
							return JSON.toJSONString(flag);
						}
					}
				} catch (Exception e) {
					flag = 0;	//失败
					logger.error("Exception,userStanceService.saveUserStance has error,message:",e);
					e.printStackTrace();
					return JSON.toJSONString(flag);
				}
			}
		}
		return JSON.toJSONString(flag);
	}
	/**
	 * <p>分发号段，并修改此占号为已发</p>
	 * @param userId
	 * @param mobile
	 * @param tjMobile
	 * @return
	 * @auth:zw
	 */
	@RequestMapping("/platform/sendUserAndModifyMobile")
	@ResponseBody
	public String sendUserAndModifyMobile(Long userId,String mobile,String tjMobile) {
		int flag = 1;
		Random random = new Random();
		User user = new User();
		user.setUserId(userId);
		String username = ProduceUserName.produce(mobile);
		user.setMobile(mobile);
		int password = random.nextInt(899999) + 100000;
		user.setPassword(String.valueOf(password));
		user.setUserName(username);
//		user.setRealName(userName);
		user.setNickName(username);
		user.setTjMobile(tjMobile);
		int mobileStatus;
		try {
			mobileStatus = RemoteServiceSingleton.getInstance().getUserService().updateUserMobileAndTj(user);
		} catch (Exception e1) {
			logger.info("分配号段异常异常：e:",e1);
			e1.printStackTrace();
			flag = 0;
			return JSON.toJSONString(flag);
		}
		if(mobileStatus == 1){
			String sendResult = SendSMSUtil.sendMessage(mobile,String.valueOf(password),username);
			logger.info("send sms end.result:"+sendResult);
			
			UserStance userStance = new UserStance();
			userStance.setOperator(getCurrentUser().getUsername());
			userStance.setUserId(userId);
			userStance.setModifyTime(new Date());
			userStance.setStatus(1);		//表示已分发此号
			try {
				RemoteServiceSingleton.getInstance().getUserStanceService().updateUserStatus(userStance);
			} catch (Exception e) {
				logger.info("修改占位为已分配异常：e:",e);
				e.printStackTrace();
				flag = 0;
				return JSON.toJSONString(flag);
			}
		}
		return JSON.toJSONString(flag);
	}
	/**
	 * <p>跳转到用户账号设置页面</p>
	 * @param map
	 * @param message
	 * @param request
	 * @return
	 * @auth:zw
	 */
	@RequestMapping("/platform/toUserAccountSet")
	public String toUserAccountSet(Map<String,Object> map,Long userId,String mobile,
			PageBean<User>  paramPage,String message,HttpServletRequest request){
		User user = new User();
		if(userId!=null && userId != 0){
			user.setUserId(userId);
		}
		if(null != mobile && !Common.isEmpty(mobile)){
			user.setMobile(mobile);	
		}
		User newUser = null;
//		paramPage.setParameter(user);
		if(userId != null || !StringUtils.isEmpty(mobile)){
//			paramPage = RemoteServiceSingleton.getInstance().getUserService().getNewPageList(paramPage);
			newUser = RemoteServiceSingleton.getInstance().getUserService().findUserByIdOrMobile(user);
			if(newUser != null && newUser.getUserId() != 0){
				String currentUserMobile = getCurrentUser().getMobile();
				map.put("currentUserMobile",currentUserMobile);
//				Date date = new Date();
//				String day = DateUtil.dateToStringDay(date);
				//红旗券
//				BigDecimal hqq = (BigDecimal) JedisManager.getObject(newUser.getUserId() + "_hqq_charge_sqw_" + day);
				try {
					Integer accountStatus = RemoteServiceSingleton.getInstance().getSqwAccountRecordService().getUserAccountStatus(newUser.getUserId(), 1);
					map.put("userStatus", accountStatus);	//用户状态
					BigDecimal hqq = RemoteServiceSingleton.getInstance().getSqwAccountRecordService().getUserChargeHqqBalance(newUser.getUserId());
					if(hqq == null){
						map.put("hqq",0);
					}else{
						map.put("hqq", hqq);
					}
				} catch (Exception e1) {
					logger.info("获取当日红旗券：e="+e1);
					e1.printStackTrace();
				}
				//分红
				try {
//					BigDecimal fenhong = RemoteServiceSingleton.getInstance().getSqwAccountRecordService().getUserAccountBalance(newUser.getUserId(), 2);
					BigDecimal fenhong = RemoteServiceSingleton.getInstance().getSqwAccountRecordService().getUserChargeFenhongBalance(newUser.getUserId());
					if(fenhong == null){
						map.put("fenhong", 0);
					}else{
						map.put("fenhong", fenhong);
					}
				} catch (Exception e) {
					logger.info("获取分红出现异常：e="+e);
					e.printStackTrace();
				}
			}
		}
		map.put("user", newUser);
		return "user/userAccountSet";
	}
	
	@RequestMapping(value="/platform/getImageRegist")
	public void getImageRegist(HttpServletRequest request, HttpServletResponse response) throws Exception{
		   response.setHeader("Pragma", "No-cache");  
	        response.setHeader("Cache-Control", "no-cache");  
	        response.setDateHeader("Expires", 0);  
	        response.setContentType("image/jpeg");  
	          
	        //生成随机字串  
	        String verifyCode = VerifyCodeUtils.generateVerifyCode(5);  
	     
	        String sid = getUUID.getSessionId(request, response);
	        memCachedClient.set("piccode_regist" + sid,3600*24, verifyCode.toLowerCase());
	        //生成图片  
	        int w = 230, h = 80;  
	        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);  
	}
	/**
	 * <p></p>
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @auth:zw
	 */
	@RequestMapping(value = "/platform/toRegGetCode")
	@ResponseBody
	public String getRegCode(Model model, HttpServletRequest request,HttpServletResponse response) {
		String mobile = request.getParameter("mobile");
		if (mobile == null || "".equals(mobile)) {
			return JSON.toJSONString(0);
		}

		// 获取传过来的验证码
		String captcha = request.getParameter("captcha");
		if (StringUtils.isEmpty(captcha)) {
			return JSON.toJSONString(0);
		}
		String sid = getUUID.getSessionId(request, response);
		String piccode_regist = null;
		try {
			piccode_regist = memCachedClient.get("piccode_regist" + sid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 比对传入的验证码和缓存中的验证码是否一致
		if (!captcha.equalsIgnoreCase(piccode_regist)) {
			try {
				memCachedClient.delete("piccode_regist" + sid);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return JSON.toJSONString(0);
		}
		String ifMessageLose = "";
		try {
			ifMessageLose = memCachedClient.get(CommonConstant.SEND_REG_MESSAGE_LOSE + sid);
		} catch (Exception e) {
			LOGGER.info("memcacheClient get  error" + e, e);
			return "";
		}
		if (!StringUtils.isEmpty(ifMessageLose) && "messageLose".equals(ifMessageLose)) {
			LOGGER.info("send message too frequent。。");
			return JSON.toJSONString(2); // 已经发送过验证码 请勿重复发送
		}
		Integer msgCode = SendSMSUtil.sendMessage(mobile);

		if (msgCode != null) {
			try {
				LOGGER.info("mobile :" + mobile + "---msgCode:" + msgCode);
				// 缓存电话
				memCachedClient.setOpTimeout(5000L);
				memCachedClient.set(CommonConstant.SEND_REG_MESSAGE + mobile + "mobile", CommonConstant.MEMCACHEDAGE,
						mobile);

				// 缓存验证码
				memCachedClient.set(CommonConstant.SEND_REG_MESSAGE + mobile, CommonConstant.MEMCACHEDAGE, msgCode);

				memCachedClient.set(CommonConstant.SEND_REG_MESSAGE_LOSE + sid,
						CommonConstant.MEMCACHEDAGE_MESSAGE_LOSE, "messageLose");

			} catch (Exception e) {
				LOGGER.info("send message error...." + mobile);
			}

			return JSON.toJSONString(1); // 发送成功
		}
		return JSON.toJSONString(-1);
	}
	
	/**
	 * <p>计算红旗券和分红额度</p>
	 * @param userId
	 * @param hqqVal
	 * @param fenhongVal
	 * @return
	 * @auth:zw
	 */
	@RequestMapping("/platform/countHqqAndFenHong")
	@ResponseBody
	public String countHqqAndFenHong(Long userId,String mobile,String captcha,
			String mobileCode,String hqqVal,String fenhongVal,
			HttpServletRequest request, HttpServletResponse response) {
		if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(captcha) || StringUtils.isEmpty(mobileCode)){
			LOGGER.info("request params: mobile = "+mobile+"&mobileCode="+mobileCode+"&captcha="+captcha);
			return JSON.toJSONString(0);
		}
		Long currentUserId = getCurrentUser().getSequenceId();		//当前系统登录用户id
		
		String result = checkMobileCode(mobile,captcha,mobileCode,request,response);
		
		if(result.equals("502")){			//图形验证码不一致
			return JSON.toJSONString(2);
		}else if(result.equals("501")){		//发送验证码的手机和传过来的手机不一致
			return JSON.toJSONString(3);
		}else if(result.equals("500")){		//手机验证码不正确
			return JSON.toJSONString(4);
		}
		
		if(StringUtils.isNotEmpty(hqqVal)){		//设置红旗券
			BigDecimal hqq = new BigDecimal(hqqVal);
			try {
				String desc = "当前用户currentUserId="+currentUserId+"给用户userId="+userId+"充值红旗券："+hqq;
				int chargeHqqStatus = RemoteServiceSingleton.getInstance().getSqwAccountRecordService()
				.chargeHqqForUser(currentUserId, userId, hqq, 1, desc, System.currentTimeMillis()+"");
				if(chargeHqqStatus != 0){
					return JSON.toJSONString(0);
				}
				SupplierOperateLog operLog = new SupplierOperateLog();
				operLog.setOperator(getCurrentUser().getUsername());
				operLog.setIp(FindUserLoginIpUtil.getIpAddrByRequest(request));
				operLog.setDescription("该操作来自于unicorn平台中，平台会员管理菜单下的会员设置红旗券操作");
				operLog.setOperatorContent("系统人员："+getCurrentUser().getUsername()+"红旗券充值操作,给：userId="+userId+"充值红旗券："+hqq);
				long logStauts = 0L;
				try {
					logStauts = RemoteServiceSingleton.getInstance().getSupplierOperateLogService().insert(operLog);
				} catch (Exception e) {
					e.printStackTrace();
					LOGGER.info("保存log日志操作结果 logStauts："+logStauts);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return JSON.toJSONString(0);
			}
		}
		if(StringUtils.isNotEmpty(fenhongVal)){		//设置分红
			BigDecimal fenhong = new BigDecimal(fenhongVal);
			try {
				String fenhongDesc = "当前用户currentUserId="+currentUserId+"给用户userId="+userId+"充值分红："+fenhong;
				int fenhongStatus = RemoteServiceSingleton.getInstance().getSqwAccountRecordService()
				.chargeHqqForUser(currentUserId, userId, fenhong, 2,fenhongDesc, System.currentTimeMillis()+"");
				if(fenhongStatus != 0){
					return JSON.toJSONString(0);
				}
				SupplierOperateLog operLog = new SupplierOperateLog();
				operLog.setOperator(getCurrentUser().getUsername());
				operLog.setIp(FindUserLoginIpUtil.getIpAddrByRequest(request));
				operLog.setDescription("该操作来自于unicorn平台中，平台会员管理菜单下的调整分红额度操作");
				operLog.setOperatorContent("系统人员："+getCurrentUser().getUsername()+"调整分红额度操作,给：userId="+userId+"调整分红额度："+fenhong);
				long logStauts = 0L;
				try {
					logStauts = RemoteServiceSingleton.getInstance().getSupplierOperateLogService().insert(operLog);
				} catch (Exception e) {
					e.printStackTrace();
					LOGGER.info("保存log日志操作结果 logStauts："+logStauts);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return JSON.toJSONString(0);
			}
		}
		return JSON.toJSONString(1);
	}
	/**
	 * 验证手机验证码和图形验证码
	 */
	private String checkMobileCode(String mobile, String captcha,
			String mobileCode,HttpServletRequest request, HttpServletResponse response) {
		if (captcha == null || "".equals(captcha)) {
			LOGGER.info("bad request vailidateRegCode captcha....");
		}

		if (mobile == null || "".equals(mobile)) {
			LOGGER.info("bad request username....");
		}
		if (mobileCode == null || "".equals(mobileCode)) {
			LOGGER.info("bad request msgReqCode....");
		}

		String sid = getUUID.getSessionId(request, response);
		Integer msgCode = null;
		String memMobile = null;
		String piccode_regist = null;
		try {
			piccode_regist = memCachedClient.get("piccode_regist" + sid);

			msgCode = memCachedClient.get(CommonConstant.SEND_REG_MESSAGE + mobile);

			memMobile = memCachedClient.get(CommonConstant.SEND_REG_MESSAGE + mobile + "mobile");

		} catch (Exception e) {
			LOGGER.info("memcache running error.." + e, e);
		}
		// 比对传入的验证码和缓存中的验证码是否一致
		if (!captcha.equalsIgnoreCase(piccode_regist)) {
			try {
				memCachedClient.delete("piccode_regist" + sid);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "502";
		}
		// 输入时的电话和 缓存中的电话不一致
		if (!mobile.equals(memMobile)) {
			return "501";
		}

		if (!mobileCode.equals(msgCode + "")) {
			return "500";
		}

		// 校验成功
		try {
			memCachedClient.delete(CommonConstant.SEND_REG_MESSAGE + sid);
			memCachedClient.delete(CommonConstant.SEND_REG_MESSAGE_LOSE);
			memCachedClient.delete("piccode_regist" + sid);
			memCachedClient.setOpTimeout(5000L);
			memCachedClient.set("register" + sid, CommonConstant.MEMCACHEDAGE_MESSAGE_LOSE, mobile);
		} catch (Exception e) {
			LOGGER.error("memcache running error...." + e, e);
		}
		LOGGER.info("校验成功  mobile :" + mobile);
		return "200";
	}
	/**
	 * <p>平台统计管理</p>
	 * @param map
	 * @param userId
	 * @param mobile
	 * @param paramPage
	 * @param message
	 * @param request
	 * @return
	 * @auth:zw
	 */
	@RequestMapping("/platform/toPlatformStatManage")
	public String toPlatformStatManage(Map<String,Object> map,Long userId,String mobile,
			PageBean<User>  paramPage,String message,HttpServletRequest request){
		User user = new User();
		if(userId!=null && userId != 0){
			user.setUserId(userId);
		}
		if(null != mobile && !Common.isEmpty(mobile)){
			user.setMobile(mobile);	
		}
		User newUser = null;
		PageBean<User> levelPage = null;
		if(userId != null || !StringUtils.isEmpty(mobile)){
			newUser = RemoteServiceSingleton.getInstance().getUserService().findUserByIdOrMobile(user);
			if(newUser != null){
				paramPage.setParameter(user);
				levelPage = RemoteServiceSingleton.getInstance().getUserService().getUserLevelByUserIdOrMobile(paramPage);
				int count = RemoteServiceSingleton.getInstance().getUserService().getUserLevelList(user);
				levelPage.setTotalCount(count);
				
			}
		}
		map.put("user", newUser);
		map.put("page", levelPage);
				
		return "user/platformStatManage";
	}
	
	/**
	 * <p>总账</p>
	 * @param map
	 * @param userId
	 * @param request
	 * @return
	 * @auth:zw
	 */
	@RequestMapping("/platform/toAccountDetail")
	public String totalAccount(Map<String,Object> map,Long userId,int type,@RequestParam(required = false)String orderId,String userName,
			PageBean<UserAccountDetialDto>  pageBean,String fromDate,String toDate,HttpServletRequest request){
		try {
			//消费红旗券 1
			BigDecimal hqq = RemoteServiceSingleton.getInstance().getSqwAccountRecordService().getUserAccountBalance(userId, 1);
			map.put("hqq", hqq);
			//消费的现金 2
			BigDecimal fenhong = RemoteServiceSingleton.getInstance().getSqwAccountRecordService().getUserAccountBalance(userId, 2);
			map.put("fenhong", fenhong);
			//可用hqq
			BigDecimal kyHqq = RemoteServiceSingleton.getInstance().getSqwAccountRecordService().getUserAccountRecordBalance(userId, 1);
			map.put("kyHqq", kyHqq);
			//可用分红额度
			BigDecimal kyFenhong = RemoteServiceSingleton.getInstance().getSqwAccountRecordService().getUserAccountRecordBalance(userId, 2);
			map.put("kyFenhong", kyFenhong);
			//未到账红旗券
			BigDecimal noToHqq = RemoteServiceSingleton.getInstance().getSqwAccountRecordService().getUserAccountBalanceInvalid(userId, 1);
			map.put("noToHqq", noToHqq);
		
			 pageBean.setOrder("desc");
			 pageBean.setSortFields("id");
			 Map<String, Object> paramsMap = new HashMap<String, Object>();
			 paramsMap.put("userId",userId);
			 if(!StringUtils.isEmpty(fromDate)){
				 paramsMap.put("fromDate",fromDate);
			 }
			 if(!StringUtils.isEmpty(fromDate)){
				 paramsMap.put("toDate", toDate);
			 }
			 if(!StringUtils.isEmpty(orderId)){
				 paramsMap.put("orderId",orderId);
			 }
			if(type == 1){		//可用分红额度
				paramsMap.put("accountId", 2);
				paramsMap.put("isValided", true);
				pageBean.setParameter(paramsMap);
				pageBean = RemoteServiceSingleton.getInstance().getSqwAccountRecordService().getUserAccountDetailPageList(pageBean);
			}else if(type == 4){	//可用红旗券
				paramsMap.put("accountId", 1);
				paramsMap.put("isValided", true);
				pageBean.setParameter(paramsMap);
				pageBean = RemoteServiceSingleton.getInstance().
						getSqwAccountRecordService().getUserAccountDetailPageList(pageBean);
			}else if(type == 2){	//消费红旗券
				pageBean.setParameter(paramsMap);
				pageBean = RemoteServiceSingleton.getInstance().
				getSqwAccountRecordService().getUserConsumedHqqDetailPageList(pageBean);
			}else if(type == 3){	//未到账红旗券	
				paramsMap.put("accountId", 1);
				paramsMap.put("isValided", false);
				pageBean.setParameter(paramsMap);
				pageBean = RemoteServiceSingleton.getInstance().
						getSqwAccountRecordService().getUserAccountDetailPageList(pageBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("page", pageBean);
		map.put("type",type);
		map.put("userId",userId);
		map.put("userName",userName);
		return "user/accountDetail";
	}
	/**
	 * <p>红旗券统计管理</p>
	 * @param map
	 * @param message
	 * @param userParam
	 * @param paramPage
	 * @param request
	 * @return
	 * @auth:zw
	 */
	@RequestMapping("/platform/hqqStatManage")
	public String hqqStatManage(Map<String,Object> map,String message,PageBean<SqwOneUserAccountInfo>  pageBean,
			String signUpFromDate,String signUpToDate,String activeFromDate,String activeToDate,String userIdOrPhone,String realName,
			String hqTotalFrom,String hqTotalTo,String hqTuijianFrom,String hqTuijianTo,String hqAccumulateFrom,String hqAccumulateTo,
			String cashAccumulateFrom,String cashAccumulateTo,String hqValidFrom,String hqValidTo,String fhBalanceFrom,String fhBalanceTo,
			HttpServletRequest request){
		
		pageBean.setOrder("desc");
		pageBean.setSortFields("a.user_id");
		if(StringUtils.isNotEmpty(signUpFromDate)){
			try {
				signUpFromDate = DateTool.format(DateTool.parse(signUpFromDate),"yyyy-MM-dd");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			map.put("signUpFromDate",signUpFromDate);		//注册开始时间
		}
		if(StringUtils.isNotEmpty(signUpToDate)){
			try {
				signUpToDate = DateTool.format(DateTool.parse(signUpToDate),"yyyy-MM-dd");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			map.put("signUpToDate",signUpToDate);		//注册end时间
		}
		if(StringUtils.isNotEmpty(activeFromDate)){
			try {
				activeFromDate = DateTool.format(DateTool.parse(activeFromDate),"yyyy-MM-dd");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			map.put("activeFromDate", activeFromDate);		//账户激活开始时间
		}
		if(StringUtils.isNotEmpty(activeToDate)){
			try {
				activeToDate = DateTool.format(DateTool.parse(activeToDate),"yyyy-MM-dd");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			map.put("activeToDate", activeToDate);		//账户激活end时间
		}
		if(StringUtils.isNotEmpty(userIdOrPhone)){
			map.put("userIdOrPhone",userIdOrPhone);		//用户ID/手机号
		}
		if(StringUtils.isNotEmpty(realName)){
			map.put("realName",realName);		//用户名
		}
		if(StringUtils.isNotEmpty(hqTotalFrom)){
			map.put("hqTotalFrom", hqTotalFrom);		//个人红旗券总资产start
		}
		if(StringUtils.isNotEmpty(hqTotalTo)){
			map.put("hqTotalTo", hqTotalTo);		//个人红旗券总资产end
		}
		if(StringUtils.isNotEmpty(hqAccumulateFrom)){
			map.put("hqAccumulateFrom", hqAccumulateFrom);		//累积红旗券消费start
		}
		if(StringUtils.isNotEmpty(hqAccumulateTo)){
			map.put("hqAccumulateTo", hqAccumulateTo);		//累积红旗券消费end
		}
		if(StringUtils.isNotEmpty(hqTuijianFrom)){
			map.put("hqTuijianFrom", hqTuijianFrom);		//会员推荐返利总额start
		}
		if(StringUtils.isNotEmpty(hqTuijianTo)){
			map.put("hqTuijianTo", hqTuijianTo);		//会员推荐返利总额end
		}
		if(StringUtils.isNotEmpty(cashAccumulateFrom)){
			map.put("cashAccumulateFrom", cashAccumulateFrom);		//累积现金消费start
		}
		if(StringUtils.isNotEmpty(cashAccumulateTo)){
			map.put("cashAccumulateTo", cashAccumulateTo);		//累积现金消费end
		}
		if(StringUtils.isNotEmpty(hqValidFrom)){
			map.put("hqValidFrom", hqValidFrom);		//未到账红旗券start
		}
		if(StringUtils.isNotEmpty(hqValidTo)){
			map.put("hqValidTo", hqValidTo);		//未到账红旗券end
		}
		if(StringUtils.isNotEmpty(fhBalanceFrom)){
			map.put("fhBalanceFrom", fhBalanceFrom);		//可用分红额度start
		}
		if(StringUtils.isNotEmpty(fhBalanceTo)){
			map.put("fhBalanceTo", fhBalanceTo);		//可用分红额度end
		}
		pageBean.setParameter(map);
		SqwAllUserAccountInfo sqwAccountInfo = RemoteServiceSingleton.getInstance().getSqwAccountRecordService().getSqwAccountInfo(pageBean);
		map.put("sqwAccount", sqwAccountInfo);
		
		pageBean = RemoteServiceSingleton.getInstance().getSqwAccountRecordService().getSqwUserAccountInfoPageList(pageBean);
		map.put("page", pageBean);
		
		return "user/hqqStatManage";
		
	}
	/**
	 * <p>企业信息结构</p>
	 * @param map
	 * @param paramPage
	 * @param message
	 * @param request
	 * @return
	 * @auth:zw
	 */
	@RequestMapping("/platform/getCompanyStatManage")
	public String getCompanyStatManage(Map<String,Object> map,String supplierCode,String name,
			PageBean<Supplier>  paramPage,String message,HttpServletRequest request){
		if(StringUtils.isEmpty(supplierCode) && StringUtils.isEmpty(name)){
			return "user/companyStatManage";
		}
		Supplier supplier = new Supplier();
		if(!StringUtils.isEmpty(supplierCode)){
			supplier.setSupplierCode(supplierCode);
		}
		if(!StringUtils.isEmpty(name)){
			supplier.setName(name);
		}
		Supplier supp = RemoteServiceSingleton.getInstance().getSupplierManagerService().getSupplier(supplier);
		if(supp != null){
			User user = RemoteServiceSingleton.getInstance().getUserService().findUserBySupplierId(supp.getSupplierId());
			if(user != null){
				supp.setUserId(user.getUserId());
				supp.setuName(user.getUserName());
			}
			if(StringUtils.isNotEmpty(supp.getSjSupplierId())){
				Supplier sjSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService().findSupplier(Long.valueOf(supp.getSjSupplierId()));
				if(sjSupplier != null && StringUtils.isNotEmpty(sjSupplier.getSupplierCode())){
					supp.setSjSupplierCode(sjSupplier.getSupplierCode());
				}
			}
		}
		
		//获取企业结构数据
		paramPage.setParameter(supp);
		paramPage = RemoteServiceSingleton.getInstance().getSupplierManagerService().getSupplierRelationBySupplierCodeOrName(paramPage);
		if(paramPage !=null && (paramPage.getResult() != null && paramPage.getResult().size()>0)){
			for(Supplier s : paramPage.getResult()){
				User user = RemoteServiceSingleton.getInstance().getUserService().findUserBySupplierId(s.getSupplierId());
				if(user != null){
					s.setUserId(user.getUserId());
					s.setuName(user.getUserName());
				}
				if(StringUtils.isNotEmpty(s.getSjSupplierId())){
					Supplier sjSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService().findSupplier(Long.valueOf(s.getSjSupplierId()));
					if(sjSupplier != null && StringUtils.isNotEmpty(sjSupplier.getSupplierCode())){
						s.setSjSupplierCode(sjSupplier.getSupplierCode());
					}
				}
			}
		}
		int count = RemoteServiceSingleton.getInstance().getSupplierManagerService().getSupplierCounts(supp);
		paramPage.setTotalCount(count);
		map.put("supplier", supp);
		map.put("page", paramPage);
		
		return "user/companyStatManage";
	}
	@RequestMapping("/platform/getCompanyStatManage1")
	public String getCompanyStatManage1(Map<String,Object> map,String supplierCode,String name,
			PageBean<Supplier>  paramPage,String message,HttpServletRequest request){
		if(StringUtils.isEmpty(supplierCode) && StringUtils.isEmpty(name)){
			return "user/homeCompanyManage1";
		}
		Supplier supplier = new Supplier();
		if(!StringUtils.isEmpty(supplierCode)){
			supplier.setSupplierCode(supplierCode);
		}
		if(!StringUtils.isEmpty(name)){
			supplier.setName(name);
		}
		Supplier supp = RemoteServiceSingleton.getInstance().getSupplierManagerService().getSupplier(supplier);
		if(supp != null){
			User user = RemoteServiceSingleton.getInstance().getUserService().findUserBySupplierId(supp.getSupplierId());
			if(user != null){
				supp.setUserId(user.getUserId());
				supp.setuName(user.getUserName());
			}
			if(StringUtils.isNotEmpty(supp.getSjSupplierId())){
				Supplier sjSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService().findSupplier(Long.valueOf(supp.getSjSupplierId()));
				if(sjSupplier != null && StringUtils.isNotEmpty(sjSupplier.getSupplierCode())){
					supp.setSjSupplierCode(sjSupplier.getSupplierCode());
				}
			}
		}
		
		//获取企业结构数据
		paramPage.setParameter(supp);
		paramPage = RemoteServiceSingleton.getInstance().getSupplierManagerService().getSupplierRelationBySupplierCodeOrName(paramPage);
		if(paramPage !=null && (paramPage.getResult() != null && paramPage.getResult().size()>0)){
			for(Supplier s : paramPage.getResult()){
				User user = RemoteServiceSingleton.getInstance().getUserService().findUserBySupplierId(s.getSupplierId());
				if(user != null){
					s.setUserId(user.getUserId());
					s.setuName(user.getUserName());
				}
				if(StringUtils.isNotEmpty(s.getSjSupplierId())){
					Supplier sjSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService().findSupplier(Long.valueOf(s.getSjSupplierId()));
					if(sjSupplier != null && StringUtils.isNotEmpty(sjSupplier.getSupplierCode())){
						s.setSjSupplierCode(sjSupplier.getSupplierCode());
					}
				}
			}
		}
		int count = RemoteServiceSingleton.getInstance().getSupplierManagerService().getSupplierCounts(supp);
		paramPage.setTotalCount(count);
		map.put("supplier", supp);
		map.put("page", paramPage);
		
		return "user/homeCompanyManage1";
	}
	/**
	 * <p>企业小号的下级会员信息</p>
	 * @param map
	 * @param userId
	 * @param paramPage
	 * @param request
	 * @return
	 * @auth:zw
	 */
	@RequestMapping("/platform/toQiYeJuniorMember")
	public String getCompanyStatManage(Map<String,Object> map,Long userId,Long uuId,String mobile,
			PageBean<User>  paramPage,HttpServletRequest request){
		paramPage.setSortFields("create_time,status");
		paramPage.setOrder("desc,asc");
		User qySmallUser = RemoteServiceSingleton.getInstance().getUserService().findUserById(userId);	//获取企业小号信息
		if(qySmallUser == null){
			return "user/qiyeJuniorMembers";
		}
		User user = new User();
		if(uuId != null){
			user.setUserId(uuId);
		}
		if(StringUtils.isNotEmpty(mobile)){
			user.setMobile(mobile);
		}
		user.setTjMobile(qySmallUser.getMobile());
		paramPage.setParameter(user);
		paramPage = RemoteServiceSingleton.getInstance().getUserService().getNewPageList(paramPage);
		map.put("page", paramPage);
		map.put("userId", userId);
		return "user/qiyeJuniorMembers";
	}
	/**
	 * <p>企业明细</p>
	 * @param map
	 * @param userId
	 * @param type
	 * @param pageBean
	 * @param fromDate
	 * @param toDate
	 * @param request
	 * @return
	 * @auth:zw
	 */
	@RequestMapping("/platform/toQyAccountDetail")
	public String toQyAccountDetail(Map<String,Object> map,Long userId,int type,Long supplierId,int hqqType,
			PageBean<SqwQiyeAccountRecode>  pageBean,PageBean<SqwQiyeTransferRecordDto> pageBean2,
			String fromDate,String toDate,HttpServletRequest request){
		try {
			
			 SqwQiyeAccountBalance qyAccountInfo = RemoteServiceSingleton.getInstance().getSqwAccountRecordService()
					 .getQYAccountInfo(supplierId);
			 map.put("qyAccount", qyAccountInfo);
			 map.put("supplierId", supplierId);
			 Map<String, Object> paramsMap = new HashMap<String, Object>();
			 if(!StringUtils.isEmpty(fromDate)){
				 paramsMap.put("fromDate",fromDate);
			 }
			 if(!StringUtils.isEmpty(fromDate)){
				 paramsMap.put("toDate", toDate);
			 }
			if(type == 1){		//商品返券明细
				pageBean.setOrder("desc");
				pageBean.setSortFields("id");
				paramsMap.put("qyId",supplierId);
				paramsMap.put("accountId", 1);
				paramsMap.put("accountRecordStatus", hqqType);
				pageBean.setParameter(paramsMap);
				pageBean = RemoteServiceSingleton.getInstance().
						getSqwAccountRecordService().getQiyeAccountBalanceDetailsPageList(pageBean);
				
				map.put("page", pageBean);
			}else if(type == 2){	//企业转账明细
				pageBean2 = new PageBean<SqwQiyeTransferRecordDto>();
				pageBean2.setOrder("desc");
				pageBean2.setSortFields("create_time");
				paramsMap.put("qiyeId",supplierId);
//				paramsMap.put("type", 2);
				pageBean2.setParameter(paramsMap);
				pageBean2 = RemoteServiceSingleton.getInstance().
						getSqwAccountRecordService().getQiyeTransferPageListV2(pageBean2);
				
				map.put("page", pageBean2);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("type",type);
		map.put("hqqType",hqqType);
		map.put("userId",userId);
		
		return "user/qyAccountDetail";
	}
	
}

