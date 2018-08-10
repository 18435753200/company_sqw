package com.mall.controller.myccigmall;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.annotation.AuthPassport;
import com.mall.constant.RequestContant;
import com.mall.constant.ResponseContant;
import com.mall.controller.base.BaseController;
import com.mall.customer.model.User;
import com.mall.pay.common.StringUtils;
import com.mall.service.CustomerService;
import com.mall.utils.SensitiveInfoUtils;

/**
 * 个人信息
 * 
 * @author ccigQA01
 * 
 */
@Controller
@RequestMapping(value = RequestContant.CUS_INFO)
public class CustomerInfoController extends BaseController {
	private static final Logger log = LoggerFactory.getLogger(CustomerInfoController.class);
	@Autowired
	private CustomerService customerService;

	/**
	 * 获取个人信息
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_INFO)
	public String cusInfo(Model model, HttpServletRequest request, HttpServletResponse response) {
		log.info("cusInfo  execute....");
		User userInfo = getCurrentUser(request);
		if (userInfo == null) {
			log.info("userinfo  is null");
			return RequestContant.CUSTOMER_TO_LOGIN;
		}

		User user = customerService.findUserById(userInfo.getUserId());
		if (user.getIdCard() != null) {
			model.addAttribute("flag", "true");
		}
		user.setTjMobile(SensitiveInfoUtils.phoneNumHyposensitize(user.getTjMobile()));
		user.setMobile(SensitiveInfoUtils.phoneNumHyposensitize(user.getMobile()));
		// userInfo.setGender((byte) 1);
		model.addAttribute("userInfo", user);
		return ResponseContant.CUS_MYINFO;

	}

	/**
	 * 修改用户个人信息
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_UPDATE_INFO)
	@ResponseBody
	public String updateInfo(User user, String birth, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("updateInfo...");
		User currentUser = getCurrentUser(request);
		user.setUserId(currentUser.getUserId());
		user.setUserName(currentUser.getUserName());
		user.setloginType(currentUser.getloginType());
		user.setType(currentUser.getType());
		user.setWeixin(currentUser.getWeixin());
		if (birth != null && birth != "") {
			user.setBirthday(new Date(birth));
		}
		String updateRes = customerService.updateUser(user, request, response);

		log.info("customerService.updateUser reutrn:" + updateRes);

		try {
			response.getWriter().write(updateRes);
		} catch (IOException e) {
		}

		return null;
	}

	/**
	 * 修改用户信息时获取验证码
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_INFO_GETCODE)
	@ResponseBody
	public String infoGetCode(Model model, HttpServletRequest request, HttpServletResponse response) {
		log.info("infoGetCode .execute./...");
		User currentUser = getCurrentUser(request);
		String res = customerService.sendCheckItentiyCode(model, currentUser.getUserName(), request, response);
		return res;
	}

	/**
	 * 修改用户手机时获取验证码
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_INFO_GETCODE_MOBILE)
	@ResponseBody
	public String infoGetCodeMobile(Model model, String newMobile, String userName, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("infoGetCode .execute./...");
		String res = customerService.sendCheckItentiyCodeMobile(model, newMobile, userName, request, response);
		return res;
	}

	/**
	 * 跳转到用户修改手机页面
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_TO_UPDATE_MOBILE)
	public String toUpdateCusmobile(User user, Model model, HttpServletRequest request, HttpServletResponse response) {
		log.info("toUpdateCusmobile");
		// 判断其 从我的账户手机验证 还是 登录界面
		String from = request.getParameter("from");
		if ("cus".equals(from)) {
			User currentUser = getCurrentUser(request);
			if (currentUser == null) {
				return ResponseContant.REDIRECT + RequestContant.CUSTOMER + RequestContant.CUSTOMER_TO_LOGIN;
			}
			model.addAttribute("username", currentUser.getUserName());
			model.addAttribute("mobile", currentUser.getMobile());
		}

		model.addAttribute("from", from);
		return ResponseContant.CUS_TO_UPDATE_V_MOBILE;
	}

	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_RE_TO_UPDATE_MOBILE)
	public String reToUpdateCusmobile(User user, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("reToUpdateCusmobile");
		User currentUser = getCurrentUser(request);
		model.addAttribute("username", currentUser.getUserName());
		model.addAttribute("mobile", currentUser.getMobile());
		return ResponseContant.CUS_TO_UPDATE_MOBILE;
	}

	/**
	 * 修改用户手机
	 * 
	 * @param validCode
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_UPDATE_MOBILE)
	public String updateInfoMobile(User user, Model model, String validCode, String newMobile,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("updateMobile");
		User currentUser = getCurrentUser(request);
		// 校验验证码
		log.info("校验验证码，用户名为：{}，验证码为：{}，旧手机为：{}，新手机为：{}", currentUser.getUserName(), validCode, currentUser.getMobile(),
				newMobile);
		String validRes = customerService.validMsgCode(currentUser.getUserName(), validCode, model, request, response);
		response.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
		log.info("校验验证码结果为：{}", validRes);
		if ("success".equals(validRes)) {
			user.setUserId(currentUser.getUserId());
			user.setUserName(currentUser.getUserName());
			user.setMobile(newMobile);
			user.setloginType(currentUser.getloginType());
			user.setType(currentUser.getType());
			user.setWeixin(currentUser.getWeixin());
			String updateRes = customerService.updateUser(user, request, response);
			try {
				response.getWriter().write(updateRes);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				response.getWriter().write(validRes);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 跳转实名认证页面
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_TO_VERIFY)
	public String toVerify(Model model, HttpServletRequest request, HttpServletResponse response, String currentPage) {
		log.info("toVerify execute .....");
		User currentUser = getCurrentUser(request);
		String idcard = currentUser.getIdCard();
		if (idcard != null && !idcard.trim().equals("") && idcard.trim().length() > 14 ) {
			String mid = idcard.substring(3, 14);
			String idCard = idcard.replaceAll(mid, "***********");
			model.addAttribute("idCard", idCard);
		}
		model.addAttribute("user", currentUser);
		model.addAttribute("currentPage", currentPage);
		return ResponseContant.CUS_VERIFY;
	}

	/**
	 * 实名认证
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = RequestContant.CUS_VERIFY)
	@ResponseBody
	public String verify(String realName, String idCard, Model model, HttpServletRequest request,
			HttpServletResponse response, String currentPage) {
		log.info("verify execute....");
		
		if (StringUtils.isEmpty(realName)) {
			return "realError";
		}
		
//		if (StringUtils.isEmpty(idCard)) {
//			return "idError";
//		}
		
		User currentUser = getCurrentUser(request);
		currentUser.setRealName(realName);
		currentUser.setIdCard(idCard);
		
		return customerService.verifyInfo(currentUser, model, request, response, currentPage);
	}

	/**
	 * 跳转修改昵称页面
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestContant.CUS_CHANGE_NICKNAME)
	@AuthPassport
	public String changeNickName(Model model, HttpServletRequest request, HttpServletResponse response) {
		log.info("cal  changeNickName  execute.....");
		User user = getCurrentUser(request);
		if (user == null) {
			log.info("用户未登陆,跳转到登陆页");
			return RequestContant.REDIRECT + RequestContant.CUSTOMER + RequestContant.CUSTOMER_TO_LOGIN;
		}
		String nickName = user.getNickName();
		model.addAttribute("nickName", nickName);

		return ResponseContant.CUS_SET_NICKNAME;
	}

	/**
	 * 跳转修改邮箱页面
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestContant.CUS_CHANGE_EMAIL)
	@AuthPassport
	public String changeEmail(Model model, HttpServletRequest request, HttpServletResponse response) {
		log.info("cal  changeNickName  execute.....");
		User user = getCurrentUser(request);
		if (user == null) {
			log.info("用户未登陆,跳转到登陆页");
			return RequestContant.REDIRECT + RequestContant.CUSTOMER + RequestContant.CUSTOMER_TO_LOGIN;
		}
		String email = user.getEmail();
		model.addAttribute("email", email);

		return ResponseContant.CUS_SET_EMAIL;
	}

	/**
	 * 跳转修改学历页面
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestContant.CUS_CHANGE_EDUCATIONAL)
	@AuthPassport
	public String changeEducational(Model model, HttpServletRequest request, HttpServletResponse response) {
		log.info("cal  changeEducational  execute.....");
		User user = getCurrentUser(request);
		if (user == null) {
			log.info("用户未登陆,跳转到登陆页");
			return RequestContant.REDIRECT + RequestContant.CUSTOMER + RequestContant.CUSTOMER_TO_LOGIN;
		}
		String educational = user.getEducational();
		model.addAttribute("educational", educational);

		return ResponseContant.CUS_SET_EDUCATIONAL;
	}
	
	/**
	 * 跳转修改身份证号页面
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestContant.CUS_CHANGE_IDCARD)
	@AuthPassport
	public String changeIdCard(Model model, HttpServletRequest request, HttpServletResponse response) {
		log.info("cal  IdCard  execute.....");
		User user = getCurrentUser(request);
		if (user == null) {
			log.info("用户未登陆,跳转到登陆页");
			return RequestContant.REDIRECT + RequestContant.CUSTOMER + RequestContant.CUSTOMER_TO_LOGIN;
		}
		String idCard = user.getIdCard();
		model.addAttribute("idCard", idCard);
		return ResponseContant.CUS_SET_IDCARD;
	}
	
	/**
	 * 跳转修改微信页面
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestContant.CUS_CHANGE_WEIXIN)
	@AuthPassport
	public String changeWeixin(Model model, HttpServletRequest request, HttpServletResponse response) {
		log.info("cal  Weixin  execute.....");
		User user = getCurrentUser(request);
		if (user == null) {
			log.info("用户未登陆,跳转到登陆页");
			return RequestContant.REDIRECT + RequestContant.CUSTOMER + RequestContant.CUSTOMER_TO_LOGIN;
		}
		String weixin = user.getWeixin();
		model.addAttribute("weixin", weixin);
		return ResponseContant.CUS_SET_WEIXIN;
	}
	
	
	/**
	 * 客户端 选在下载客户端
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestContant.CUS_CLIENT_INDEX)
	public String toClient(Model model, HttpServletRequest request, HttpServletResponse response) {
		log.info(" toClient  execute。。。。");

		return ResponseContant.CUS_CLIENT_MAIN;
	}

	/**
	 * 下载android
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = RequestContant.CUS_DOWNLOAD_ANDROID)
	@AuthPassport
	public String downloadAndroid(Model model, HttpServletRequest request, HttpServletResponse response) {
		log.info(" downloadAndroid  execute。。。。");
		String fileName = "ccigmall.apk";
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		try {
			String path = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "download";// 这个download目录为啥建立在classes下的
			InputStream inputStream = new FileInputStream(
					new File("http://oss.aliyuncs.com/ccigmall-appversion/me19rqztwvnf.apk"));

			OutputStream os = response.getOutputStream();
			byte[] b = new byte[2048];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			// 这里主要关闭。
			os.close();
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 返回值要注意，要不然就出现下面这句错误！
		// java+getOutputStream() has already been called for this response
		return null;

		// return ResponseContant.CUS_CLIENT_MAIN;
	}

}
