/*package com.mall.controller.promotions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.dealer.product.customer.api.CustomerProductApi;
import com.mall.dealer.product.po.DealerProduct;
import com.mall.mybatis.utility.PageBean;
import com.mall.platform.model.PlatformActivity;
import com.mall.platform.model.PlatformActivityConfig;
import com.mall.platform.model.PlatformActivityProduct;
import com.mall.platform.service.PlatformActivityConfigService;
import com.mall.platform.service.PlatformActivityService;
import com.mall.promotion.dto.activity.ActiveMasterDto;
import com.mall.promotion.po.activity.ActiveMaster;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Common;
import com.mall.utils.Constants;

*//**
 * @author doublell
 * @version 1.0
 *//*

@Controller
@RequestMapping(value = "/appActivity")
public class APPActivityController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(APPActivityController.class);
	private static final String SUC_FLAG = "success";
	private static final String FAIL_FLAG = "error";
	private static final String IMG_ADRESS = "http://image01.zhongjumall.com/";
    *//**
     * @param Model
     * @return
     * 创建APP活动
     *//*
	@RequestMapping(value = "/createAPPActivity")
	public String toAPPActivityEdit(PlatformActivity platformActivity,Model model,Long activityId) {
		LOGGER.info("跳转到创建APP活动页面！");
		if (activityId != null) {
			PlatformActivityService platformActivityService = RemoteServiceSingleton.getInstance().getPlatformActivityService();
			PlatformActivityConfigService platformActivityConfigService= RemoteServiceSingleton.getInstance().getPlatformActivityConfigService();
			PlatformActivity PlatformActivityView = platformActivityService.getActivityById(activityId);
			PlatformActivityConfig config = platformActivityConfigService.findByActivityId(activityId, Constants.SPECIAL_APP_ACTIVITY_PC);
			model.addAttribute("bean", PlatformActivityView);
			model.addAttribute("config", config);
		}
		return "promotions/activity/appActivityEdit";
	}
	
	*//**
	 * @param Model
	 * @return
	 * 商品关联APP活动
	 *//*
	@RequestMapping(value = "/relevanceAP")
	public String appRelevanceProduct(Model model) {
		LOGGER.info("跳转到关联APP活动页面！");
		@SuppressWarnings("rawtypes")
		List<Map> list = findAllActivitiesList();
		model.addAttribute("activities", list);
		return "promotions/activity/appRelevanceAP";
	}
	
	*//**
	 * @param Model
	 * @return
	 * APP活动列表页
	 *//*
	@RequestMapping(value = "/appToList")
	public String appList(Model model) {
		LOGGER.info("跳转到APP活动列表页面！");
		return "promotions/activity/appActivityList";
	}
	
	*//**
	 * 查询APP活动列表
	 * @param pageBean
	 * @return
	 *//*
	@RequestMapping(value = "/findAPPActivityByCondition")
	public String findAPPActivityByCondition(HttpServletRequest request, HttpServletResponse response, Integer page, ActiveMasterDto param, Model model) {
		try {
			LOGGER.info("to APPActivityList Page By Condition!");
			PageBean<PlatformActivity> pageBean = new PageBean<PlatformActivity>();//PlatformActivityProduct.java
				pageBean.setParameter(param);
				pageBean.setPageSize(Constants.PAGESIZE);
			if (page != null && page != 0) {
				pageBean.setPage(page);
			} else {
				pageBean.setPage(1);
			}
			PlatformActivityService platformActivityService =  RemoteServiceSingleton.getInstance().getPlatformActivityService();
			pageBean= platformActivityService.findActivityList(pageBean);

			request.getSession().setAttribute("pb", pageBean);
			request.getRequestDispatcher("/WEB-INF/views/promotions/activity/modelpage/app_activity_model.jsp").forward(request, response);
		} catch (Exception e) {
			LOGGER.error("查询活动列表异常：" + e.getMessage(), e);
		}
		return null;
	}
	
	*//**
	 *  创建、编辑活动（暂时只有创建功能）
	 * @return
	 *//*
	@RequestMapping(value = "/appActivityEdit")
	@ResponseBody
	public String appActivityEdit(HttpServletRequest request) {
		String currUserName = null;
		String returnFlag = SUC_FLAG;
		if(getCurrentUser()!=null){
			currUserName = getCurrentUser().getUsername();
		}
		try {
			// 校验页面元素
			returnFlag = checkCreateActiveMasterInfo(request);
			if (SUC_FLAG.equals(returnFlag)) {
				PlatformActivityService platformActivityService = RemoteServiceSingleton.getInstance().getPlatformActivityService();
				PlatformActivityConfigService platformActivityConfigService= RemoteServiceSingleton.getInstance().getPlatformActivityConfigService();
				PlatformActivityConfig config =  null;
				PlatformActivity platformActivity = null;
				if(request.getParameter("activityId")!=null&&request.getParameter("activityId")!=""){
					platformActivity = platformActivityService.getActivityById(Long.valueOf(request.getParameter("activityId")));
					config = platformActivityConfigService.findByActivityId(Long.valueOf(request.getParameter("activityId")), Constants.SPECIAL_APP_ACTIVITY_PC);
					if(config==null)config = new PlatformActivityConfig();
				}else{
				    platformActivity = new PlatformActivity();
				    config = new PlatformActivityConfig();
				}
				// 页面PO元素 - 所有的元素
				// 由于页面中出现问题 - 使用request获取页面元素信息
					platformActivity.setActivityName(request.getParameter("activityName"));
					platformActivity.setCreateBy(currUserName);
					platformActivity.setMainPicUrl(request.getParameter("mainPicUrl"));
					platformActivity.setMainTitle(request.getParameter("mainTitle"));
					platformActivity.setPicUrl(request.getParameter("picUrl"));
					platformActivity.setTitle(request.getParameter("title"));
					platformActivity.setStatus(Integer.parseInt(request.getParameter("status")));
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
					platformActivity.setStartTime(simpleDateFormat.parse(request.getParameter("startTime")));
					platformActivity.setEndTime(simpleDateFormat.parse(request.getParameter("endTime")));
					
					config.setActivityGroupId(0l);
					config.setSpecialType(Constants.SPECIAL_APP_ACTIVITY_PC);
					config.setCustText01(request.getParameter("mainPicUrlpc"));
					config.setCustText02(request.getParameter("picUrlpc"));
					config.setTipName("PC图片特殊处理");
					//根据页面中是否存在活动标识判断
					if(request.getParameter("activityId")!=null&&request.getParameter("activityId")!=""){
						platformActivityService.update(platformActivity);
					}else{
						platformActivityService.add(platformActivity);
						List<PlatformActivity>  list = platformActivityService.findList(platformActivity);
						platformActivity =list.get(0);
					}
					config.setActivityId(platformActivity.getActivityId());
					if(config.getId()!=null){
						platformActivityConfigService.modifyConfig(config);
					}else{
						platformActivityConfigService.addConfig(config);
					}
					
			}
			returnFlag ="success";
		} catch (Exception e) {
			LOGGER.error("创建/编辑活动失败" + e.getMessage(), e);
			returnFlag = FAIL_FLAG;
		}
		return returnFlag;
	}
	
	*//**
	 * 查看APP活动信息
	 * 
	 * @return
	 *//*
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/toAPPActivityView")
	public String toActivityView(Long activityId, Model model) {
		try {
			List<PlatformActivityProduct> proList = null;
			@SuppressWarnings("rawtypes")
			List listToChange = new ArrayList();
			PlatformActivityService platformActivityService = RemoteServiceSingleton.getInstance().getPlatformActivityService();
			PlatformActivity platformActivity = platformActivityService.getActivityById(activityId);
			model.addAttribute("bean", platformActivity);
			PlatformActivityConfigService platformActivityConfigService= RemoteServiceSingleton.getInstance().getPlatformActivityConfigService();
			PlatformActivityConfig config = platformActivityConfigService.findByActivityId(activityId, Constants.SPECIAL_APP_ACTIVITY_PC);
			model.addAttribute("config", config);
			model.addAttribute("imgto",IMG_ADRESS);
			//目前由活动标识查询关联商品
			proList = platformActivityService.findProductListByActivityId(activityId);
			//重新组装List
			for (PlatformActivityProduct pl : proList) {
				//..
				//listToChange.add(platformActivity.getActivityName());
				//listToChange.add(pl.getActProId());
				@SuppressWarnings("rawtypes")
				Map map = new HashMap();
				map.put("actproId", pl.getActProId());
				map.put("actName", platformActivity.getActivityName());
				map.put("gPid", pl.getProductid());
				listToChange.add(map);
			}
			model.addAttribute("products", listToChange);
			//model.addAttribute("products", proList);
			return "promotions/activity/appActivityView";
		} catch (Exception e) {
			LOGGER.error("查询APP活动失败" + e.getMessage(), e);
		}
		return null;
	}
	
	
	*//**
	 * 删除APP活动信息
	 * 
	 * @return
	 *//*
	@RequestMapping(value = "/deleteAPPActivityView")
	@ResponseBody
	public String toDeleteActivityView(Long activityId, Model model) {
		try {
			if(activityId!=null){
				RemoteServiceSingleton.getInstance().getPlatformActivityService().delete(activityId);
				return SUC_FLAG;
			}else{
				return FAIL_FLAG;
			}
		} catch (Exception e) {
			LOGGER.error("查询APP活动失败" + e.getMessage(), e);
		}
		return FAIL_FLAG;
	}
	
	*//**
	 * 删除APP活动关联商品信息
	 * 
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping(value = "/deleteAPActivityView")
	public String toDeleteAPActivityView(Long actproId, Model model) {
		try {
			PlatformActivityService platformActivityService = RemoteServiceSingleton.getInstance().getPlatformActivityService();
			PlatformActivityProduct platformActivityProduct = platformActivityService.getActivityProductByActProId(actproId);
			platformActivityService.deleteProduct(actproId);
			return SUC_FLAG;
		} catch (Exception e) {
			LOGGER.error("删除AP关联活动失败" + e.getMessage(), e);
		}
		return FAIL_FLAG;
	}
	
	*//**
	 * 校验页面填写元素是否符合规范
	 * 
	 * @param platformActivity
	 * @return
	 *//*
	private String checkCreateActiveMasterInfo(HttpServletRequest request) {
		String msg = SUC_FLAG;

		// 校验名称
		if (StringUtils.isNotBlank(platformActivity.getActiveName)) {
			if (platformActivity.getActiveName().length() > 100) {
				return "名称长度不能大于100！";
			}
		} else {
			return "名称不能为空！";
		}

		// 校验开始时间与结束时间
		if (request.getParameter("startTime")== null) {
			return "开始时间不能为空！";
		}
		if (request.getParameter("startTime").compareTo(new Date()) == -1) {
			return "开始时间不能小于系统当前时间！";
		}
		if (request.getParameter("endTime") == null) {
			return "结束时间不能为空！";
		}
		if (platformActivity.getEndTime().compareTo(platformActivity.getStartTime()) <= 0) {
			return "结束时间应该大于开始时间！";
		}

		return msg;
	}
	
	*//**
	 * 
	 * @Title: uploading .
	 * @Description: TODO(上传文件)
	 * @param retailerFile
	 * @param request
	 *            void 返回类型
	 * @throws
	 *//*
	private void uploading(PlatformActivity platformActivity, HttpServletRequest request,HttpServletResponse response) {

		//获取图片地址
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartHttpServletRequest.getFileMap();
		
		if(null!=fileMap && fileMap.size()>0){
			for (Map.Entry<String, MultipartFile> multipartFile : fileMap.entrySet()) {
				if(!multipartFile.getValue().isEmpty()){
					String field = multipartFile.getKey();
					
					MultipartFile multipartFile2 = multipartFile.getValue();
					//上传文件，返回文件服务器url
					String url = "";
					 try {
						 url = UploadFileUtil.uploadFile(multipartFile2.getInputStream(),
								 Common.getFileExt2(multipartFile2.getOriginalFilename()), null);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (MyException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 //Field retailFileField =null;
					 //通过对象的属性名称，给对象的属性赋值
					try {
						response.getWriter().write(
								"{\"success\":\"true\","+"\"data\":\"url\"}");
						
					    String urls =  Constants.FILESERVER1 + url;
						platformActivity.setMainPicUrl(url);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
				
			}
		}
	}
	
	
	
	*//**增加产品活动关联
	 * @return
	 *//*
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/addActivityProduct")
	@ResponseBody
	public String activityEdit(HttpServletRequest request, HttpServletResponse response) {
		try{
			Long productId = Long.parseLong(request.getParameter("productId"));
			Long activityId =  Long.parseLong(request.getParameter("activityId"));
			CustomerProductApi customerProductApi = RemoteServiceSingleton.getInstance().getCustomerProductApi();
			DealerProduct findB2cIstate = customerProductApi.findB2cIstate(productId);
			Short b2cIsTate = null;
			if(null!=findB2cIstate){
				b2cIsTate = findB2cIstate.getB2cIsTate();
			}
			if(null==b2cIsTate||1!=b2cIsTate){
				return "error";
			}
			PlatformActivityProduct pro = new PlatformActivityProduct ();
			pro.setActivityId(activityId);
			pro.setProductid(productId);
			List exsitList = RemoteServiceSingleton.getInstance().getPlatformActivityService().findProducts(pro);
			if(exsitList!=null&&exsitList.size()>0){
				LOGGER.info("根据活动和产品找到已存在关联，不需要重复添加--");
				return "exsit";
			}
			long result = RemoteServiceSingleton.getInstance().getPlatformActivityService().addActivityProducts(pro);
			if(result>0)return "success";
		}catch(Exception e){
			e.printStackTrace();
		}
		return "error";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> findAllActivitiesList(){
		List<Map> result = new ArrayList();
		List<PlatformActivity> list = RemoteServiceSingleton.getInstance().getPlatformActivityService().findList(null);
		for(PlatformActivity act:list){
			Map map = new HashMap();
			map.put("activityName", act.getActivityName());
			map.put("activityId", act.getActivityId());
			result.add(map);
		}
		return result;
	}
	

}
*/