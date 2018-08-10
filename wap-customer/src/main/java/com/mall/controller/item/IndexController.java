package com.mall.controller.item;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

//import com.mall.cms.customer.api.CustomerCmsService;
//import com.mall.cms.dto.CustomerAppIndexDto;
import com.mall.controller.base.BaseController;
import com.mall.service.CmsService;
import com.mall.utils.Constants;
import com.mall.utils.CookieUtil;
import com.mall.utils.StringUtil;
import com.mall.wap.proxy.RemoteServiceSingleton;

import freemarker.core._RegexBuiltins.matchesBI;
/**
 * 首页相关
 * @author yuanxiayang
 *
 */
@Controller
@RequestMapping("/index")
public class IndexController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(IndexController.class);
	
	public static final String INDEX = "item/index";
	public static final String CREATE_INDEX_SUCCESS = "item/create_index_success";
	public static final String CREATE_TOPIC_SUCCESS = "item/create_topic_success";
	
	/**
	 * 模板路径
	 */
	@Value("${static.templateDir}")
	private String templateDir;
	
	/**
	 * 首页静态文件路径
	 */
	@Value("${static.indexDir}")
	private String indexDir;
	
	/**
	 * 预览文件静态文件路径 
	 */
	@Value("${static.preIndexDir}")
	private String preIndexDir;
	
	/**
	 * 专题页静态文件路径
	 */
	@Value("${static.topicDir}")
	public String topicDir;
	
	@Autowired
	CmsService cmsService;
	
	/**
	 * 生成首页静态页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception 
	 */
//	@RequestMapping("/index.html")
//	public String autoCreateStaticIndex(HttpServletRequest request,HttpServletResponse response,Model model) throws Exception{
//		//确认首页是否存在
//		String dirPath = request.getSession().getServletContext().getRealPath(indexDir);
//		File path = new File(dirPath+"/index.html");
//		if(path.isFile()){
//			logger.info("---------------exist index.html ");
//			return "index/index.html";
//		}
//		logger.info("---------------create index start");
//		if(cmsService.createIndex(request)){
//			logger.info("---------------create index success");
//		}
//		return "index/index.html";
//		
//	}
	
	/**
	 * 生成专题静态页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception 
	 */
//	@RequestMapping("/topic.html")
//	public String autoCreateStaticTopic(HttpServletRequest request,HttpServletResponse response,Model model,String topicUrl,String channel) throws Exception{
//		if(null != channel){
//			//电信用户注册，保存cookie
//			saveCookie(channel,request,response);
//		}
//		//获取topicUrl,截取字符串
//		int indexOf = topicUrl.indexOf(topicDir);
//		//切割字符串获取topic名称 
//		String topicName = topicUrl.substring(indexOf+topicDir.length()+1);
//		//获取topicId
//		int indexOf2 = topicName.indexOf(".");
//		long topicId = Long.parseLong(topicName.substring(5, indexOf2));
//		//确认首页是否存在
//		String dirPath = request.getSession().getServletContext().getRealPath(topicDir);
//		File path = new File(dirPath+"/"+topicName);
//		if(path.isFile()){
//			logger.info("---------------exist topic.html,topicId="+topicId);
//			return "topic/"+topicName;
//		}
//		logger.info("---------------create topic start");
//		//获取topicid
//		//int indexOf2 = topicName.indexOf("topic");
//		if(cmsService.createTopic(request,topicId)){
//			logger.info("---------------create topic success:topic"+topicId);
//		}
//		return "topic/"+topicName;
//		
//	}
	
	//保存cookie
	private void saveCookie(String channel,HttpServletRequest request,HttpServletResponse response) {
		logger.info("channel="+channel);
		CookieUtil.setCookie(response, "channel", channel, Constants.OUT_TIME_COOKIE);

		
	}

	/**
	 * 生成首页静态页面，提供给cms
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception 
	 */
//	@RequestMapping("/createStaticIndex")
//	public String createStaticIndex(HttpServletRequest request,HttpServletResponse response,Model model) throws Exception{
//		//确认首页是否存在
//		logger.info("---------------create index start");
//		if(cmsService.createIndex(request)){
//			logger.info("---------------create index success");
//		}
//		return "index/index.html";
//	}
	
	
	/**
	 * 生成专题静态页面,提供给cms
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception 
	 */
//	@RequestMapping("/createStaticTopic")
//	public String createStaticTopic(HttpServletRequest request,HttpServletResponse response,Model model,Long topicId) throws Exception{
//		logger.info("---------------create topic start");
//		if(cmsService.createTopic(request,topicId)){
//			logger.info("---------------create topic success:topic"+topicId);
//		}
//		return CREATE_TOPIC_SUCCESS;
//		
//	}
	
	/**
	 * 处理首页分页，分页加载更多数据
	 * @return
	 * @throws Exception 
	 */
//	@RequestMapping("/indexPage")
//	public String indexPage(HttpServletRequest request,HttpServletResponse response,Model model,Integer pageNum) throws Exception{
//		
//		//获取数据
//		CustomerCmsService cmsServiceApi = RemoteServiceSingleton.getInstance().getCmsServiceApi();
//		CustomerAppIndexDto appCustomerIndex = cmsServiceApi.getAppCustomerIndex(pageNum);
//		
//		model.addAttribute("contentList", appCustomerIndex.getIndexContent().get("contentList"));
//		model.addAttribute("picUrl1", picUrl1);
//		
//		return "index/indexPage";
//	}
	
	
	/**
	 * to 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/toStaticIndex")
	public String toStaticIndex(HttpServletRequest request,HttpServletResponse response,Model model){
		
		return "html/index.html";
		//return INDEX;
	}
}
