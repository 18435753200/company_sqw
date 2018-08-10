package com.mall.InterceptorUtils;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rubyeye.xmemcached.MemcachedClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mall.platform.model.PlatformUser;
import com.mall.utils.Constants;
import com.mall.utils.CookieHelper;

/**.
 * 用户登陆的拦截器
 * @author       
 *
 */
public class LoginInterceptor implements HandlerInterceptor {
	/**.
	 * LOGGER 
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginInterceptor.class);

	/**
	 * Sping注入mencached缓存客户端.
	 */
	@Autowired
	private  MemcachedClient memCachedClient;  

	public MemcachedClient getMemCachedClient() {
		return memCachedClient;
	}

	public void setMemCachedClient(MemcachedClient memCachedClient) {
		this.memCachedClient = memCachedClient;
	}

	/**
	 *@return boolean
	 */
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler){

		try {
			//1、请求到登录页面 放行  
			String url = request.getServletPath();
			if(url.contains("/platform/registUI")){
				return true;
			};
			List<String> noLoginURL = isNotLogin();
			boolean  falg =	isfilterChain(url,noLoginURL);
			if(falg){
				return true ;
			}
			//3、如果用户已经登录 放行    
			Cookie cookie = CookieHelper.getCookie(request,Constants.SESSION_ID);
			if(cookie!= null) {
				//更好的实现方式的使用cookie  
				String sid = cookie.getValue();

				//更新cookie的有效期
				CookieHelper.setCookie(response, Constants.SESSION_ID, sid, Constants.COOKIETIME);

				//缓存中获取user信息
				PlatformUser object = (PlatformUser) memCachedClient.get(sid);

				//缓存中没有user重新登录
				if(null==object){
					response.sendRedirect("../user/loginUI"); 
					return false;
				}else{
					boolean flag=false;
					if(initUrl(url)){     //用户操作部分update delete save等from 不拦截
						flag= true ;
					}else if(null!=object.getIsAdmin()&&object.getIsAdmin()>0){  //是管理员不用权限拦截
						flag= true ;
					}else{                       //获取用户的角色中权限URl
						List<String> roleurl = memCachedClient.get(sid+Constants.MENUS_LIST);
						if(isfilterChain(url,roleurl)){
							flag= true ;
						}else{
							//返回到指定的页面
							// request.getRequestDispatcher("/not_role.jsp").forward(request, response);
							response.sendRedirect("../user/loginUI"); 
							flag=false;
						}
					}
					if(flag){
						memCachedClient.replace(sid,Constants.MAMCACHEDTIME,object);
						if(null!=memCachedClient.get(sid+Constants.MENUS_MAP)){
							memCachedClient.replace(sid+Constants.MENUS_MAP,Constants.MAMCACHEDTIME,memCachedClient.get(sid+Constants.MENUS_MAP)); 
						}

						if(null!=memCachedClient.get(sid+Constants.MENUS_URL_MAP)){
							memCachedClient.replace(sid+Constants.MENUS_URL_MAP,Constants.MAMCACHEDTIME,memCachedClient.get(sid+Constants.MENUS_URL_MAP)); 
						}
						
						if(null!=memCachedClient.get(sid+Constants.MENUS_LIST)){
							memCachedClient.replace(sid+Constants.MENUS_LIST,Constants.MAMCACHEDTIME,memCachedClient.get(sid+Constants.MENUS_LIST)); 
						}

						if(null!=memCachedClient.get(sid+Constants.BUTTONS_MAP)){
							memCachedClient.replace(sid+Constants.BUTTONS_MAP,Constants.MAMCACHEDTIME,memCachedClient.get(sid+Constants.BUTTONS_MAP)); 
						}

						if(null!=memCachedClient.get(sid+Constants.PLATFORM)){
							memCachedClient.replace(sid+Constants.PLATFORM,Constants.MAMCACHEDTIME,memCachedClient.get(sid+Constants.PLATFORM)); 
						}
						request.setAttribute("sid", sid);
						request.setAttribute("loginUser", object);
						request.setAttribute("defaultUrlMapReslut", memCachedClient.get( sid+Constants.MENUS_URL_MAP));
						request.setAttribute("platform",memCachedClient.get(sid+Constants.PLATFORM));
						request.setAttribute("buttonsMap",memCachedClient.get(sid+Constants.BUTTONS_MAP));
						request.setAttribute("meunMap",memCachedClient.get(sid+Constants.MENUS_MAP));
					}
					return flag;
				}
			} 
			//4、非法请求 即这些请求需要登录后才能访问  
			//重定向到登录页面  
			LOGGER.info("< ---------------- > < ---------------- > 非法登录 < ---------------- > < ---------------- > ");
			
			response.sendRedirect("../user/loginUI"); 
			
		} catch (Exception e) {
			
			LOGGER.error("LoginInterceptor Error.  msg："+e.getMessage(),e);
			
		}
		return false;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		response.setDateHeader("expires", 0);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("pragma", "no-cache");
		//LOGGER.info("控制器的方法已经执行完毕，转换成视图之前的处理；");
	}
	/**
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param handler handler
	 * @param ex Exception
	 */
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
	{
		//LOGGER.info("视图已处理完后执行的方法，通常用于释放资源；");
		response.setDateHeader("expires", 0);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("pragma", "no-cache");
	}
	/**
	 * @param url 截取.htm 的url
	 * @return String
	 */
	public  String getUrlPath(String url ){
		LOGGER.info("url:"+url);
		StringBuilder builder = new StringBuilder();
		int flag = url.indexOf(".htm");
		if(flag!=-1){
			String str =url.substring(0, flag);
			builder.append(str);
			builder.append(".htm");
		}
		return builder.toString();
	}
	/**没有登录 可以访问的URl.
	 * @return result
	 */
	public List<String> isNotLogin(){
		
		List<String> result = new ArrayList<String>();
		result.add("/user/login");
		result.add("/user/isPinEngaged");
		result.add("/group");
		result.add("/regist");
		result.add("/regist");
		result.add("/download");
		result.add("/imageUp");
		result.add("/supplier/modifyInfo");
		result.add("platform/modifyFreezeStatus");
		result.add("platform/toPlatformStatManage");
		
		return result;
	}

	/**初始化的URl 登录后可以访问的URl.
	 * @param str url
	 * @return boolean
	 */
	public boolean initUrl(String str){
		List<String> result = new ArrayList<String>();
		result.add("List");//查看列表
		result.add("welcome");//首页
		result.add("logout");//退出
		result.add("download"); //各类下载
		result.add("view");//删除按钮
		result.add("unClockProductById");//上传图片
		result.add("imageUp");//上传图片
		result.add("getProvince");//查询省份(select)
		result.add("getFirstDisp");//查询一级发布类目(select)
		result.add("getOtherDisp");//查询其他发布类目(select)
		result.add("getAllProvices");//加载省级信息(select)
		result.add("getCitiesByProvinceId");//加载市级信息(select)
		result.add("getCountiesByCityId");//加载区级信息(select)
		result.add("getSupplierList");//加载供应商信息(select)
		result.add("loadWareSelect");//加载仓库信息(select)
		result.add("getProductSKUBySupplierId");//加载SKU信息(select)
		
		return   isfilterChain(str,result);
	}
	/**是否可以访问.
	 * @param url String
	 * @param str List<String> 
	 * @return boolean
	 */
	public boolean isfilterChain(String url,List<String> str){

		boolean result = false;
		if(str==null){
			return false ;
		}
		for(String ss : str){
			if(url.indexOf(ss)!=-1){
				result = true ;
				break;
			}
		}
		return result;
	}
}
