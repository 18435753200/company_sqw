package com.mall.InterceptorUtils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mall.authority.client.util.UserUtil;
import com.mall.bean.authority.User;
import com.mall.logger.CMLogger;
import com.mall.logger.CMLoggerFactory;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.platform.service.PlatformUserManagerService;
import com.mall.utils.Constants;

import net.rubyeye.xmemcached.MemcachedClient;
import net.sf.json.JSONObject;


/**
 * @author zhouzb ajax超时拦截器
 *
 */
public class AjaxTimeOutFilter implements HandlerInterceptor{

	/**
	 * LOGGER.
	 */
	private static final CMLogger LOGGER = CMLoggerFactory.getCMLogger(AjaxTimeOutFilter.class);
	/**
	 * Sping注入mencached缓存客户端.
	 */
	@Autowired
	private  MemcachedClient memCachedClient;  

	//LogService logService = null;


	/**
	 * @return MemcachedClient get
	 */
	public MemcachedClient getMemCachedClient() {
		return memCachedClient;
	}

	/**
	 * @param memCachedClient set
	 */
	public void setMemCachedClient(MemcachedClient memCachedClient) {
		this.memCachedClient = memCachedClient;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse response, Object arg2, Exception arg3)
					throws Exception {
		// TODO Auto-generated method stub
		response.setDateHeader("expires", 0);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("pragma", "no-cache");
		//String url = arg0.getRequestURI();
		//String method = arg0.getMethod();
		String url = arg0.getServletPath();
		//调试时打印输出
		boolean ifDebug = true;
		
		String ip = getClientIP(arg0);
		
		User currentUser = null;
		 if(arg0.getAttribute("loginUser")!=null){
			 currentUser = (User) arg0.getAttribute("loginUser");
	       }
		
		//增加操作日志
		if(ifDebug){
			if(currentUser != null){//登录状态下记录操作日志
				
				Map paramNames = arg0.getParameterMap(); 
				String json = JSONObject.fromObject(paramNames).toString();
				if(url.contains("/user/login")){
					json = "";
				}
				if(json.length() > 2048){
					json = json.substring(0, 2048);
				}
				
				addOptLog(currentUser,url,ip,json);	
			}			
		}
		//LOGGER.info("===================== AjaxTimeOutFilter afterCompletion  ");
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		//LOGGER.info("===================== AjaxTimeOutFilter postHandle ");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object obj) throws Exception {
			User user = UserUtil.getUserFromCookie(request, response, Constants.MEMBER);
			if(user==null){
				String header = request.getHeader("x-requested-with");
				if (header != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest"))
				{
					response.setHeader("sessionstatus", "timeout");
					LOGGER.info("AjAx 超时");
					return false;
				}
			}
		//LOGGER.info("===================== AjaxTimeOutFilter preHandle ");
		return true;
	}

	private void addOptLog(User optUser,String urlStr,String ip,String content){
		Map<String,String> param = new HashMap<String,String>();
		String ifLog = "0";//默认记录日志
		//日志功能名称 
		if(Constants.OPT_ISLOG_MAP.containsKey(urlStr)){
			param.put("funtName", Constants.OPT_ISLOG_MAP.get(urlStr).getFuntName());
			ifLog = Constants.OPT_ISLOG_MAP.get(urlStr).getIsLog();
		}else{
			param.put("funtName", null);
		}
		param.put("userId", optUser.getSequenceId()+"");
		param.put("content", content);
		param.put("userCode", optUser.getUsername());
		param.put("userName", optUser.getRealName());
		param.put("deptId", optUser.getDepId()+"");
		param.put("deptName", optUser.getDepName());
		param.put("optIp", ip);
		param.put("optUrl", urlStr);
		if("0".equals(ifLog)){//记录日志
			PlatformUserManagerService platformUserManagerService = RemoteServiceSingleton.getInstance().getPlatformUserManagerService();
			platformUserManagerService.insertSysLog(param);
		}
	}
	
	/*** 
	 * 获取客户端IP地址;这里通过了Nginx获取;X-Real-IP, 
	 * @param request 
	 * @return 
	 */  
	private static String getClientIP(HttpServletRequest request) {  
	    String fromSource = "X-Real-IP";  
	    String ip = request.getHeader("X-Real-IP");  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("X-Forwarded-For");  
	        fromSource = "X-Forwarded-For";  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	        fromSource = "Proxy-Client-IP";  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	        fromSource = "WL-Proxy-Client-IP";  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getRemoteAddr();  
	        fromSource = "request.getRemoteAddr";  
	    }  
	    //appLog.info("App Client IP: "+ip+", fromSource: "+fromSource);  
	    return ip;  
	}  

}
