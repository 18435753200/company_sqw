package com.mall.controller.product;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.csource.upload.UploadFileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.authority.client.util.UserUtil;
import com.mall.bean.authority.User;
import com.mall.dealer.product.dto.DealerProductActivityTopicDTO;
import com.mall.dealer.product.util.DateUtil;
import com.mall.mybatis.utility.PageBean;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;

/**.
 * 商品类目Controller
 * @author zhouzb
 *
 */
/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="/activitytop")
public class ActivityTopicController extends BaseController{


	/**
	 * LOGGER.
	 */
	private static final Log LOGGER = LogFactory.getLogger(ActivityTopicController.class);

	public final String pop_view_page = "/dealerseller/TipGoods/activityTopic_model";
	public final String pop_view_page_dealer = "/dealerseller/TipGoods/activityTopicList";
	public final String pop_view_ID = "/dealerseller/TipGoods/viewTopic";
	public final String pop_EDIT_ID = "/dealerseller/TipGoods/editTopic";
	/**.
	 * 加载商品页面
	 * @return ModelAndView
	 */
	@RequestMapping(value="/getPro")
	public String getProductPage(HttpServletRequest request,String isReivew){
		
		if(null != isReivew){
			request.setAttribute("isReivew",1);
		}
		
		return pop_view_page_dealer;
	}
	
	/**条件加载话题列表
	 * @param cate  String
	 * @param page Integer
	 * @param dealerProductSelectConDTO DealerProductSelectConDTO
	 * @return null1
	 */
	@RequestMapping(value="/getActivityTopicByConditions")
	public String getActivityTopicByConditions(Model model,
			PageBean<DealerProductActivityTopicDTO> pageBean,
			DealerProductActivityTopicDTO activityTopicDto,Integer page,
			String createBeginTimeStr ,String createEndTimeStr ,String updateBeginTimeStr ,String updateEndTimeStr) {
		
		try {
			
			if (createBeginTimeStr != null
					&& !createBeginTimeStr.equals("")) {

				activityTopicDto.setCreateBeginTime(DateUtil.str2Date(
						createBeginTimeStr, null));
			}
			if (createEndTimeStr != null
					&& !createEndTimeStr.equals("")) {

				activityTopicDto.setCreateEndTime(DateUtil.str2Date(
						createEndTimeStr, null));
			}
			if (updateBeginTimeStr != null
					&& !updateBeginTimeStr.equals("")) {

				activityTopicDto.setUpdateBeginTime(DateUtil.str2Date(
						updateBeginTimeStr, null));
			}
			if (updateEndTimeStr != null
					&& !updateEndTimeStr.equals("")) {

				activityTopicDto.setUpdateEndTime(DateUtil.str2Date(
						updateEndTimeStr, null));
			}
			
			pageBean.setPageSize(Constants.PAGESIZE);
//			pageBean.setSortFields("update_time");
//			pageBean.setOrder("DESC");
			pageBean.setParameter(activityTopicDto);

			pageBean = RemoteServiceSingleton.getInstance()
					.getDealerProductTopicService().findTopicsByConditions(pageBean);

			List<DealerProductActivityTopicDTO> result = pageBean.getResult();
			if (result != null) {
				
				for (DealerProductActivityTopicDTO dealerProductActivityTopicDTO : result) {
					List<String> list = new ArrayList<String>();
					list.add(dealerProductActivityTopicDTO.getCreateby());

					List<User> listUser = UserUtil.findUserList(list);

					if (listUser != null && listUser.size() > 0) {
						dealerProductActivityTopicDTO.setCreateby(listUser.get(0).getRealName());
					}
					
					List<String> list2 = new ArrayList<String>();
					list2.add(dealerProductActivityTopicDTO.getUpdateby());

					List<User> listUser2 = UserUtil.findUserList(list);

					if (listUser2 != null && listUser2.size() > 0) {
						dealerProductActivityTopicDTO.setUpdateby(listUser2.get(0).getRealName());
					}
				}
			}

			model.addAttribute("pb", pageBean);

		} catch (Exception e) {

			LOGGER.error(e.getMessage(), e);

			return "1";

		}
			
		return pop_view_page;

	}
	
	/**条件加载话题列表
	 * @param cate  String
	 * @param page Integer
	 * @param dealerProductSelectConDTO DealerProductSelectConDTO
	 * @return null1
	 */
	@RequestMapping(value="/findActivityTopicById")
	public String findActivityTopicById(Model model,DealerProductActivityTopicDTO activityTopicDto,String target,String TopicId) {
		
		try {
			

			DealerProductActivityTopicDTO dto = RemoteServiceSingleton.getInstance()
					.getDealerProductTopicService().findActivityTopicByTopicId(TopicId);
			
			String description = dto.getDetails();
			
			if (null != description && !description.equals("")){
				
//				if(target.equals(Constants.SUCCESS)){
//					
//					description = Constants.H0 + description;
//					
//				}else{
					
					if(description!=null&&!description.equals("")){
		
						description = UploadFileUtil.DownloadFile(description);
						LOGGER.info("图文详情:"+description);
		
					}
//				}
			}
			
			dto.setDetails(description);
			
			dto.setTitleUrl(Constants.P2+dto.getTitleUrl());
			

			model.addAttribute("dto", dto);

		} catch (Exception e) {

			LOGGER.error(e.getMessage(), e);

			return "1";

		}
			
		if(target.equals("2")){
			return pop_EDIT_ID;
		}else{
			return pop_view_ID;
		}

	}
	
	/**.
	 * 添加话题页
	 * @return ModelAndView
	 */
	@RequestMapping(value="/editActivity")
	public String editActivity(HttpServletRequest request,String isReivew){
		
		if(null != isReivew){
			request.setAttribute("isReivew",1);
		}
		
		return "/dealerseller/TipGoods/createTopic";
	}
	
	/**.
	 * banner设置页
	 * @return ModelAndView
	 */
	@RequestMapping(value="/bannerSet")
	public String bannerSet(Model model,HttpServletRequest request){
		
		LOGGER.info("查询所有banner!");
		
		List<DealerProductActivityTopicDTO> activityTopicDtoList = null;
		
		try {
			activityTopicDtoList = RemoteServiceSingleton.getInstance().getDealerProductTopicService().findBannerList(3);
		} catch (Exception e) {
			LOGGER.error("查询banner错误！  msg:"+e.getMessage());
		}
		
		LOGGER.info("查询banner结束!");
		
		JSONArray returnImgUrlAndName = new JSONArray();
		
		//图片回显
		List<LinkedHashMap<String,Object>> imgUrls = new ArrayList<LinkedHashMap<String,Object>>();
		try {
			
			if(null != activityTopicDtoList && activityTopicDtoList.size() > 0){
//				List<LinkedHashMap<String,Object>> imgUrl = new ArrayList<LinkedHashMap<String,Object>>();
				LinkedHashMap<String,Object> imglist = new LinkedHashMap<String, Object>();
				List<String> urllist = null;
				Map<String,Object> xx = new HashMap<String,Object>();
				for (int i = 0; i < activityTopicDtoList.size(); i++) {
					
					urllist = new ArrayList<String>();
					
					
					String imgurl= Constants.P2+activityTopicDtoList.get(i).getBannerUrl();
					urllist.add(imgurl);
					
					xx.put(activityTopicDtoList.get(i).getBannerSkipUrl(), urllist);
					
				}
				
				imglist.put("test", xx);
				
				imgUrls.add(imglist);
			}
			
		} catch (Exception e) {
			LOGGER.error("图片回显"+e.getMessage(),e);
		}
		returnImgUrlAndName = JSONArray.fromObject(imgUrls);
		
		model.addAttribute("jsonImg",returnImgUrlAndName);
		
		return "/dealerseller/TipGoods/bannerSet";
	}
	
	/**
	 * 保存话题
	 * @param request HttpServletRequest
	 * @param objectVO DealerProductObjectDTO
	 */
	@RequestMapping(value="/saveActivityTopic")
	@ResponseBody
	public String saveActivity(HttpServletRequest request,
			DealerProductActivityTopicDTO activityTopicDto, String[] imgUrl,String activityUrl,String editor1){


		String isSaveParameter = "1";

		LOGGER.info("修改商品信息.");

		LOGGER.info("用户:"+getCurrentUser().getUsername());
		LOGGER.info("用户ID:"+getCurrentUser().getId());


		try {
			
			activityTopicDto.setStickTime(new Date());
			
			activityTopicDto.setCreateby(getCurrentUser().getId());

				//设置主图
				String imageUrl = "";
				StringBuffer buffer = new StringBuffer();
				if(imgUrl!=null){
					String[] strArr = imgUrl[0].split("_");
					new StringBuffer();
					for (int i = 1; i < strArr.length; i++) {
						if(i>1){
							buffer.append("_");
						}
						buffer.append(strArr[i]);
					}
					
					imageUrl = buffer.toString();
					
					imageUrl = imageUrl.substring(imageUrl.indexOf("group"));
				}
				
				activityTopicDto.setTitleUrl(imageUrl);
				
				LOGGER.info("设置商品主图完成!");
				/**
				 * 图文详情
				 */
				
				if(editor1!=null){
					
//					editor1 = editor1.replaceAll("href=", "");
					byte[] picByte = editor1.getBytes();
					String fileUrl = "";
					ByteArrayInputStream stream = new ByteArrayInputStream(picByte);
					fileUrl = UploadFileUtil.uploadFile(stream, null, null);

					activityTopicDto.setDetails(fileUrl);
				}
		} catch (Exception e) {
			LOGGER.error("保存话题错误！  msg:"+e.getMessage());


			LOGGER.error("用户:"+getCurrentUser().getUsername());
			LOGGER.error("用户ID:"+getCurrentUser().getId());
			isSaveParameter="0";
		}
			LOGGER.info("设置图文详情完成!");
			
			activityTopicDto.setStatus(2);
			activityTopicDto.setStickStatus(2);
			
			if(null != activityUrl && !activityUrl.equals("")){
				activityTopicDto.setViewUrl(activityUrl);
			}

			RemoteServiceSingleton.getInstance().getDealerProductTopicService().saveActivityTopic(activityTopicDto);
		
			LOGGER.info("话题保存成功!");
		
		return isSaveParameter;
//			return "redirect:/activitytop/getPro";

	}
	
	/**
	 * 保存banner
	 * @param request HttpServletRequest
	 * @param objectVO DealerProductObjectDTO
	 */
	@RequestMapping(value="/saveBanner")
	@ResponseBody
	public String saveBanner(HttpServletRequest request,
			DealerProductActivityTopicDTO activityTopicDto, String[] imgUrl,String[] viewUrl){


		String isSaveParameter = "1";

		LOGGER.info("修改商品信息.");

		LOGGER.info("用户:"+getCurrentUser().getUsername());
		LOGGER.info("用户ID:"+getCurrentUser().getId());


		try {
			
			RemoteServiceSingleton.getInstance().getDealerProductTopicService().removeAllBanner();

				//设置主图
				if(imgUrl!=null){
//					String[] strArr = imgUrl[0].split("_");
//					new StringBuffer();
//					for (int i = 1; i < strArr.length; i++) {
//						if(i>1){
//							buffer.append("_");
//						}
//						buffer.append(strArr[i]);
//					}
					
					for (int x = 0; x < imgUrl.length; x++) {
						
//						String[] prodImg = imgUrl[x].split("_");
//						StringBuffer sb = new StringBuffer();
//						for (int i = 1; i < prodImg.length; i++) {
//							if (i > 1) {
//								sb.append("_");
//							}
//							sb.append(prodImg[i]);
//						}
						
//						if(!"00".equals(prodImg[0])){
						
						String imageUrl = imgUrl[x];
						imageUrl = imageUrl.substring(imageUrl.indexOf("group"));
						
						activityTopicDto.setBannerUrl(imageUrl);
						activityTopicDto.setBannerSkipUrl(viewUrl[x]);
						
						RemoteServiceSingleton.getInstance().getDealerProductTopicService().saveBannerTitle(activityTopicDto);
						
						LOGGER.info("banner保存成功!");
//						}
						
					}
				}
				
		} catch (Exception e) {
			LOGGER.error("保存话题错误！  msg:"+e.getMessage());


			LOGGER.error("用户:"+getCurrentUser().getUsername());
			LOGGER.error("用户ID:"+getCurrentUser().getId());
			isSaveParameter="0";
		}
		
		return isSaveParameter;

	}
	
	
	/**
	 * 修改话题
	 * @param request HttpServletRequest
	 * @param objectVO DealerProductObjectDTO
	 */
	@RequestMapping(value="/updateActivityTopic")
	@ResponseBody
	public String updateActivity(HttpServletRequest request,
			DealerProductActivityTopicDTO activityTopicDto, String[] imgUrl,String editor1,String activityUrl,String delete){


		String isSaveParameter = "1";

		LOGGER.info("修改商品信息.");

		LOGGER.info("用户:"+getCurrentUser().getUsername());
		LOGGER.info("用户ID:"+getCurrentUser().getId());


		try {
			
			activityTopicDto.setUpdateby(getCurrentUser().getId());
			activityTopicDto.setUpdateTime(new Date());
			
			if(null != activityTopicDto.getStickStatus() && !"".equals(activityTopicDto.getStickStatus())){
				activityTopicDto.setStickby(getCurrentUser().getId());
				activityTopicDto.setStickTime(new Date());
			}

				//设置主图
				String imageUrl = "";
				StringBuffer buffer = new StringBuffer();
				if(imgUrl!=null){
					String[] strArr = imgUrl[0].split("_");
					new StringBuffer();
					for (int i = 1; i < strArr.length; i++) {
						if(i>1){
							buffer.append("_");
						}
						buffer.append(strArr[i]);
					}
					
					imageUrl = buffer.toString();
					imageUrl = imageUrl.substring(imageUrl.indexOf("group"));
					activityTopicDto.setTitleUrl(imageUrl);
					
					activityTopicDto.setStatus(2);
				}
				
				LOGGER.info("设置商品主图完成!");
				/**
				 * 图文详情
				 */
				
				if(editor1!=null){
					
//					editor1 = editor1.replaceAll("href=", "");
					byte[] picByte = editor1.getBytes();
					String fileUrl = "";
					ByteArrayInputStream stream = new ByteArrayInputStream(picByte);
					fileUrl = UploadFileUtil.uploadFile(stream, null, null);

					activityTopicDto.setDetails(fileUrl);
				}
		} catch (Exception e) {
			LOGGER.error("保存话题错误！  msg:"+e.getMessage());


			LOGGER.error("用户:"+getCurrentUser().getUsername());
			LOGGER.error("用户ID:"+getCurrentUser().getId());
			isSaveParameter="0";
		}
			LOGGER.info("设置图文详情完成!");
			
			if(null != activityUrl && !activityUrl.equals("")){
				activityTopicDto.setViewUrl(activityUrl);
			}
			
			if(null != delete && !"".equals(delete)){
				activityTopicDto.setStatus(3);
			}

			RemoteServiceSingleton.getInstance().getDealerProductTopicService().updateActivityTopic(activityTopicDto);
		
			LOGGER.info("话题保存成功!");
		
		return isSaveParameter;

	}

}	