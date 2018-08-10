package com.mall.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.WriteAbortedException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.csource.upload.UploadFileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

//import com.mall.cms.customer.api.CustomerCmsService;
//import com.mall.cms.dto.CustomerAppIndexDto;
//import com.mall.cms.dto.CustomerAppTopicDto;
//import com.mall.cms.dto.CustomerContentDto;
import com.mall.controller.base.BaseController;
import com.mall.controller.search.SearchController;
import com.mall.utils.FreeMarkerUtil;
import com.mall.wap.proxy.RemoteServiceSingleton;

/**
 * cms create index.html
 * @author yuanxiayang
 *
 */
@Service
public class CmsService extends BaseController {
	
	private static final Logger logger = Logger.getLogger(CmsService.class);
	
	//存放静态页面所需数据
	private static Map<String,Object> root = new HashMap<String,Object>();
	
	boolean isLoadTemplate;
	
	
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
	
	/**
	 * 静态文件服务器
	 */
	@Value("${staticFile_s}")
	public String staticFile_s;

//	//create index.html
//	public boolean createIndex(HttpServletRequest request) throws Exception{
//		
//		//封装页面所需数据
//		getDateForIndexFtl(request);
//		//创建输出流
//		Writer out = getOutStream(request,indexDir,"index.html");
//		//初始化模版
//		loadTemplate(request);
//		//生成静态页面
//		FreeMarkerUtil.processTemplate("index.ftl", root, out);
//		
//		return true;
//	}
	
//	//create topic.html
//	public boolean createTopic(HttpServletRequest request,Long topicId) throws Exception{
//		
//		logger.info("---------------create topic start: topicId= "+topicId);
//		//封装页面所需数据
//		getDateForTopicFtl(request,topicId);
//		//创建输出流
//		Writer out = getOutStream(request,topicDir,"topic"+topicId+".html");
//		//初始化模版
//		loadTemplate(request);
//		//生成静态页面
//		FreeMarkerUtil.processTemplate("topic.ftl", root, out);
//		
//		return true;
//	}

//	//package date for index.ftl
//	private void getDateForIndexFtl(HttpServletRequest request) throws Exception {
//
//		//root path
//		root.put("picUrl1", picUrl1);
//		root.put("path", request.getContextPath());
//		root.put("staticFile_s", staticFile_s);
//		root.put("basePath", request.getScheme()+"://"+
//					request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/");
//		//调用服务
//		CustomerCmsService cmsServiceApi = RemoteServiceSingleton.getInstance().getCmsServiceApi();
//		CustomerAppIndexDto appCustomerIndex = cmsServiceApi.getAppCustomerIndex(1);
//		
//		//获取totalPage
//		Long totalPage = appCustomerIndex.getTotalAmount()/appCustomerIndex.getPageSize();
//		totalPage = appCustomerIndex.getTotalAmount()%appCustomerIndex.getPageSize()==0 ? totalPage : totalPage+1;
//		
//		root.put("totalPage", totalPage);
//		root.put("carousel", appCustomerIndex.getIndexContent().get("carousel"));
//		root.put("contentList", appCustomerIndex.getIndexContent().get("contentList"));
//	}
	
//	//package date for topic.ftl
//	private void getDateForTopicFtl(HttpServletRequest request,Long topicId) throws Exception {
//
//		//root path
//		root.put("picUrl1", picUrl1);
//		root.put("path", request.getContextPath());
//		root.put("staticFile_s", staticFile_s);
//		root.put("basePath", request.getScheme()+"://"+
//					request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/");
//		//调用服务
//		CustomerCmsService cmsServiceApi = RemoteServiceSingleton.getInstance().getCmsServiceApi();
//		CustomerAppTopicDto appCustomerTopic = cmsServiceApi.getAppCustomerTopic(topicId);
//		String contentPath = UploadFileUtil.DownloadFile(appCustomerTopic.getContentUrl());
//		root.put("topicDate",contentPath);
//	}

	

	/**
	 * 获取输出流
	 * @param request
	 * @param indexDir2
	 * @param string
	 * @return
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 */
	private Writer getOutStream(HttpServletRequest request, String indexDir,
			String fileName) throws UnsupportedEncodingException, FileNotFoundException {
		String dirPath = request.getSession().getServletContext().getRealPath(indexDir);
		File path = new File(dirPath);
		Writer out = null;
		out = new OutputStreamWriter(new FileOutputStream(dirPath+"/"+fileName),"UTF-8");
		return out; 
	}
	
	/**
	 * 初始化模版
	 * @param request
	 * @throws IOException 
	 */
	private void loadTemplate(HttpServletRequest request) throws IOException {
		String _templateDir = request.getSession().getServletContext().getRealPath(templateDir);
		FreeMarkerUtil.initConfig(new File(_templateDir));
		isLoadTemplate = true;
	}
	
}
