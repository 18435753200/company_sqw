package com.mall.controller.base;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rubyeye.xmemcached.MemcachedClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.bean.authority.User;
import com.mall.platform.model.Platform;
import com.mall.utils.Common;
import com.mall.utils.Constants;

/**.
 * Controller中公用的方法抽象到该类
 * @author wangdj
 */
public  class BaseController extends MultiActionController {
	
	/**
	 * Sping注入mencached缓存客户端.
	 */
	@Autowired
	public  MemcachedClient memCachedClient;  
	/**
	 * LOGGER.
	 */
	private static final Log LOGGER = LogFactory.getLogger(BaseController.class);
	//http request请求
	/**.
	 * request HttpServletRequest
	 */
	protected HttpServletRequest request;
	
	/**.
	 * 调用Service方法要传入的参数
	 */
	Map<String, Object> pmap=new LinkedHashMap<String, Object>();
	@InitBinder
	//此方法用于日期的转换
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	/**.
	 * 获取当前供应商的id
	 * @param request HttpServletRequest
	 * @return  Map<String,Object>
	 */
	public  Map<String,Object> initParamMap(HttpServletRequest request){
		Map<String,Object> paramMap = request.getParameterMap();
		for (Object obj : paramMap.entrySet()) {
			Map.Entry entry = (Map.Entry)obj;
			String name = (String) entry.getKey();
			Object valObj = entry.getValue();
			if(null != valObj){
				if(valObj instanceof String[]){
					String[] values = (String[])valObj;
					String value="";
					for(int i=0; i< values.length; i++){
						if(value.equals("")){
							value += values[i];
						}else{
							value += ","+values[i];
						}
					}
					pmap.put(name, value);
				}else{
					pmap.put(name, valObj);
				}
			}
		}
		return pmap;
	}
	 /**.
     * 功能：该方法将request对象中的值封装到相应的pojo对象中
     * @param obj Object
     * @param request HttpServletRequest
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws ParseException
     */
    public static void getObjectFromRequest(Object obj,HttpServletRequest request) {
        Class<?> cla = obj.getClass();//获得对象类型
        Field field[] = cla.getDeclaredFields();//获得该类型中的所有属性
        for(int i=0;i<field.length;i++) {//遍历属性列表
            field[i].setAccessible(true);//禁用访问控制检查
            Class<?> fieldType = field[i].getType();//获得属性类型
            String attr = request.getParameter(field[i].getName());//获得属性值
            if(attr==null) {//如果属性值为null则不做任何处理，直接进入下一轮循环
                continue;
            }
            /**
             * 根据对象中属性类型的不同，将request对象中的字符串转换为相应的属性
             */
            try {
            	  if(fieldType==String.class) {             
                  	field[i].set(obj,attr);
                  }                 
                  else if(fieldType==int.class||fieldType==Integer.class){//当转换失败时，设置0
                      field[i].set(obj,Integer.parseInt(request.getParameter(field[i].getName())));
                  }
                  else if(fieldType==long.class||fieldType==Long.class){//当转换失败时，设置0
                      field[i].set(obj,Long.parseLong(request.getParameter(field[i].getName())));
                  }
                  else if(fieldType==Date.class) {//当转换失败时，设置为null
                      field[i].set(obj,Date.parse(request.getParameter(field[i].getName())));
                  }            
			} catch (Exception e) {
				LOGGER.error("P对象转换异常"+e);
				e.printStackTrace();
			}              
          
        }
    }
    /**.
     * 功能：cookie中取出Session_id 然后去Xmemcacaed中取当前登录用户 此步骤已在拦截器中实现并加入到requset对象中实现
     * @param obj
     * @param request
     */
    public User getCurrentUser(){
       if(getRequest().getAttribute("loginUser")!=null){
    	   return (User) request.getAttribute("loginUser");
       }
       return null;
    }
    
    /**.
     * 获取商户Id信息
     * @param obj
     * @param request
     */
    public Long getCurrentPlatformId(){
    	
//       if(getRequest().getAttribute("platform")!=null){
//    	   Platform platform=(Platform)getRequest().getAttribute("platform");
    	   return 1L;
//       }
//       return null;
    }
    /**.
     * 功能：cookie中取出Session_id
     * @return Session_id
     */
    public  String getSessionId() {
		if (getRequest().getAttribute("sid") != null) 
		{
			return (String) request.getAttribute("sid");
		}
       return null;
    }
    /**.
     * 功能：下载
     * 
     * @param filepath 图片服务器给的URL(例如：group1/M00/00/00/wKgBOlOzgFCAKzT5AAVg-j-S1to345.jpg)
     *                                (例如1：Http://192.168.1.59/group1/M00/00/00/wKgBOlOzgFCAKzT5AAVg-j-S1to345.jpg)
     * @param fileName 要显示的文件名称
     * 
     * @param response HttpServletResponse
     * 
     */
    public void download(HttpServletResponse response,String filepath,String fileName){
    	  String  old_url=filepath;
	      String realName=null;
  		  if(Common.isEmpty(fileName)){
  			  realName=  filepath.substring(filepath.lastIndexOf("/")) ;
  		  }else{
  			  realName= fileName;  
  		  }
   
	// 进行编码转换，防止不能正确显示中文名
	try {
		  response.reset();
		  if(!old_url.toUpperCase().startsWith("HTTP")){
			  old_url=  Constants.FILESERVER1+old_url;
		  }
		  URL url = new URL(old_url);  
        HttpURLConnection uc = (HttpURLConnection) url.openConnection();  
        uc.setDoInput(true);//设置是否要从 URL 连接读取数据,默认为true  
        uc.connect();  
        InputStream iputstream = uc.getInputStream();  
        //设置响应类型和响应头
        response.setContentType("application/x-msdownload;charset=UTF-8");
        response.setContentLength(uc.getContentLength());
        String userAgent = getRequest().getHeader("User-Agent");  
        // name.getBytes("UTF-8")处理safari的乱码问题  
        /*  realName = userAgent.contains("Firefox") ? 
         * new String(fileName.getBytes("GB2312"),"ISO-8859-1"):URLEncoder.encode(fileName, "UTF-8") ; 
         // 文件名外的双引号处理firefox的空格截断问题  
         response.setHeader("Content-disposition","attachment; filename="+realName);
        */
       if(userAgent.contains("Firefox"))
      	    response.addHeader("Content-Disposition","attachment;filename="+ 
       new String(realName.getBytes("GB2312"),"ISO-8859-1"));
      	else 
      		response.addHeader("Content-Disposition","attachment;filename=" + 
      	URLEncoder.encode(realName, "UTF-8"));
        //读取文件
        BufferedInputStream bis=new BufferedInputStream(iputstream);
        ServletOutputStream  output = response.getOutputStream();
        byte[] buf = new byte[Constants.BUFFEREDSTREAM];
        int r = 0;
        while ((r = bis.read(buf)) != -1) {
      	  output.write(buf, 0, r);
        } 
        //释放资源
        iputstream.close();
        bis.close();
        output.flush();
        output.close(); 
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}

}

	/** 
	 * @return HttpServletRequest
	 */
	public HttpServletRequest getRequest() {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();  
		this.request=((ServletRequestAttributes)ra).getRequest();
	    return request;  
	}

	/**
	 * @param request HttpServletRequest
	 */
	public void setRequest(HttpServletRequest request) {
		this.request=request;
	}
    
	
}
