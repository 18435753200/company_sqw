package com.mall.controller.topic;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.controller.base.BaseController;
import com.mall.customer.model.User;
import com.mall.customer.order.api.rpc.TopicLikeItemService;
import com.mall.customer.order.po.TopicLikeItem;
import com.mall.dealer.product.api.DealerProductTopicService;
import com.mall.dealer.product.dto.DealerProductActivityTopicDTO;
import com.mall.mybatis.utility.PageBean;
import com.mall.utils.Constants;
import com.mall.utils.TopicUtil;

/**
 * 
 * @author fanxj
 * topic  页控制类
 */
@Controller
@RequestMapping("/view/topic")
public class TopicController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(TopicController.class);
	@Autowired
	DealerProductTopicService dealerProductTopicService;
	@Autowired
	TopicLikeItemService topicLikeItemService;
	
	/**
	 * 专题列表页
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/topic",method=RequestMethod.GET)
	public String list(Model model, HttpServletRequest request, HttpServletResponse response){
		
	  try {
			String ip = getIpAddr(request);
			if (ip.equals("127.0.0.1")){
				ip = InetAddress.getLocalHost().getHostAddress();
			}
			logger.info("客户端IP地址："+ip);
			PageBean<DealerProductActivityTopicDTO> pageBean=new PageBean<DealerProductActivityTopicDTO>();
			pageBean.setPageSize(5);
			pageBean.setPage(1);
			DealerProductActivityTopicDTO activityTopicDTO=new DealerProductActivityTopicDTO();
			activityTopicDTO.setStatus(1);
			pageBean.setParameter(activityTopicDTO);
			pageBean = dealerProductTopicService.findTopicsByConditions(pageBean);
			int totalPage=pageBean.getTotalPage();
			List<DealerProductActivityTopicDTO> list = pageBean.getResult();
			for (int i = 0; i < list.size(); i++) {
				DealerProductActivityTopicDTO dto = list.get(i);
				BigDecimal topicId = dto.getTopicId();
				String topic = TopicUtil.getTopic(ip+"_"+topicId);
				if(topic!=null){
					dto.setLikeStatus(1);
				}
			}
			model.addAttribute("list", list);
			model.addAttribute("totalPage", totalPage);
			//bannerList
			List<DealerProductActivityTopicDTO> bannerList = dealerProductTopicService.findBannerList(2);
			model.addAttribute("bannerList", bannerList);
			String filePath=Constants.FILESERVER0;
			model.addAttribute("filePath", filePath);
			
		} catch (Exception e) {
			logger.error("获取专题列表页异常");
			e.printStackTrace();
		}		
		return "/topic/topic";
	}
	/**
	 * banner 预览
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/previewtopic",method=RequestMethod.GET)
	public String previewlist(Model model, HttpServletRequest request, HttpServletResponse response){
		
	  try {
			String ip = getIpAddr(request);
			if (ip.equals("127.0.0.1")){
				ip = InetAddress.getLocalHost().getHostAddress();
			}
			logger.info("客户端IP地址："+ip);
			PageBean<DealerProductActivityTopicDTO> pageBean=new PageBean<DealerProductActivityTopicDTO>();
			pageBean.setPageSize(5);
			pageBean.setPage(1);
			DealerProductActivityTopicDTO activityTopicDTO=new DealerProductActivityTopicDTO();
			activityTopicDTO.setStatus(1);
			pageBean.setParameter(activityTopicDTO);
			pageBean = dealerProductTopicService.findTopicsByConditions(pageBean);
			int totalPage=pageBean.getTotalPage();
			List<DealerProductActivityTopicDTO> list = pageBean.getResult();
			for (int i = 0; i < list.size(); i++) {
				DealerProductActivityTopicDTO dto = list.get(i);
				BigDecimal topicId = dto.getTopicId();
				String topic = TopicUtil.getTopic(ip+"_"+topicId);
				if(topic!=null){
					dto.setLikeStatus(1);
				}
			}
			model.addAttribute("list", list);
			model.addAttribute("totalPage", totalPage);
			//bannerList
			List<DealerProductActivityTopicDTO> bannerList = dealerProductTopicService.findBannerList(3);
			model.addAttribute("bannerList", bannerList);
			String filePath=Constants.FILESERVER0;
			model.addAttribute("filePath", filePath);
		} catch (Exception e) {
			logger.error("获取专题列表页异常");
			e.printStackTrace();
		}		
		return "/topic/topic";
	}
	/**
	 * 专题列表页加载页面
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/topicScroll",method=RequestMethod.POST)
	public String listScroll(Model model, HttpServletRequest request, HttpServletResponse response,int page){
		
	  try {
		  String ip = getIpAddr(request);
			if (ip.equals("127.0.0.1")){
				ip = InetAddress.getLocalHost().getHostAddress();
			}
			logger.info("客户端IP地址："+ip);
			PageBean<DealerProductActivityTopicDTO> pageBean=new PageBean<DealerProductActivityTopicDTO>();
			pageBean.setPageSize(5);
			pageBean.setPage(page);
			DealerProductActivityTopicDTO activityTopicDTO=new DealerProductActivityTopicDTO();
			activityTopicDTO.setStatus(1);
			pageBean.setParameter(activityTopicDTO);
			pageBean = dealerProductTopicService.findTopicsByConditions(pageBean);
			int totalPage=pageBean.getTotalPage();
			List<DealerProductActivityTopicDTO> list = pageBean.getResult();
			for (int i = 0; i < list.size(); i++) {
				DealerProductActivityTopicDTO dto = list.get(i);
				BigDecimal topicId = dto.getTopicId();
				String topic = TopicUtil.getTopic(ip+"_"+topicId);
				if(topic!=null){
					dto.setLikeStatus(1);
				}
			}
			model.addAttribute("list", list);
			model.addAttribute("totalPage", totalPage);
			String filePath=Constants.FILESERVER0;
			model.addAttribute("filePath", filePath);
		} catch (Exception e) {
			logger.error("加载专题列表页异常");
			e.printStackTrace();
		}		
		return "/topic/topic_scroll";
	}
	@RequestMapping(value="/topicDetail",method=RequestMethod.GET)
	public String topicDetail(Model model, HttpServletRequest request, HttpServletResponse response,String topicId){
		try {
			String ip = getIpAddr(request);
			if (ip.equals("127.0.0.1")){
				ip = InetAddress.getLocalHost().getHostAddress();
			}
			logger.info("客户端IP地址："+ip);
			DealerProductActivityTopicDTO topicDTO = dealerProductTopicService.findActivityTopicByTopicId(topicId);
			//取缓存数据
			String topic = TopicUtil.getTopic(ip+"_"+topicId);
			if(topic!=null){
				topicDTO.setLikeStatus(1);
			}
			String filePath=Constants.FILESERVER0;
			model.addAttribute("filePath", filePath);
			model.addAttribute("activityTopicDto", topicDTO);
			model.addAttribute("picUrl1", picUrl1);
		} catch (Exception e) {
			logger.error("获取话题详情页异常");
			e.printStackTrace();
		}
		return "/topic/topicVideoDetail";
	}
	
	/**
	 * 点赞
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/clicklike",method = RequestMethod.POST)
	@ResponseBody
	public String clicklike (Model model, HttpServletRequest request, HttpServletResponse response,String topicId,long likeNum){
		try {
			//获取客户端IP地址
			String ip = getIpAddr(request);
			if (ip.equals("127.0.0.1")){
				ip = InetAddress.getLocalHost().getHostAddress();
			}
			logger.info("点赞IP地址："+ip);
			topicId=topicId.trim();
			//更新点赞
			DealerProductActivityTopicDTO dto=new DealerProductActivityTopicDTO();
			dto.setTopicId(new BigDecimal(topicId));
			dto.setLikeNum(likeNum+1);
			dealerProductTopicService.updateActivityTopic(dto);
			
			//保存点赞明细
			TopicLikeItem topicLikeItemDto=new TopicLikeItem();
			topicLikeItemDto.setIp(ip);
			topicLikeItemDto.setCreateTime(new Date());
			topicLikeItemDto.setTopicId(new BigDecimal(topicId));
			User user = getCurrentUser(request);
			if(user!=null){
				topicLikeItemDto.setUserId(new BigDecimal(user.getUserId()));;
			}
			topicLikeItemService.insert(topicLikeItemDto);
			
			//保存点赞到缓存
			String val=ip+"_"+topicId;
			TopicUtil.saveTopic(val);
			return "100";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "200";
		}
	}
	/**
	 * 分享
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/clickShare",method = RequestMethod.POST)
	@ResponseBody
	public String clickShare (Model model, HttpServletRequest request, HttpServletResponse response,String topicId,long shareNum){
		topicId=topicId.trim();
		DealerProductActivityTopicDTO dto=new DealerProductActivityTopicDTO();
		dto.setTopicId(new BigDecimal(topicId));
		dto.setShareNum(shareNum+1);
		dealerProductTopicService.updateActivityTopic(dto);
		return "100";
	}
	public String getIpAddr(HttpServletRequest request) { 
	    String ip = request.getHeader("x-forwarded-for"); 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getHeader("Proxy-Client-IP"); 
	    } 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getHeader("WL-Proxy-Client-IP"); 
	    } 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getRemoteAddr(); 
	    } 
	    return ip; 
	} 
}
