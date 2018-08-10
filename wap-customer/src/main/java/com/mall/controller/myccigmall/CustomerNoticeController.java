/**
 * 
 */
package com.mall.controller.myccigmall;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.mall.constant.RequestContant;
import com.mall.constant.ResponseContant;
import com.mall.controller.base.BaseController;
import com.mall.customer.dto.CNoticePageDto;
import com.mall.customer.model.CNotice;
import com.mall.customer.model.User;
import com.mall.customer.service.SqwAccountRecordService;
import com.mall.service.NoticeService;

/**
 * @author zhengqiang.shi 2015年5月12日 上午11:51:16
 */
@Controller
@RequestMapping(value = RequestContant.SYS_NOTICE)
public class CustomerNoticeController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(CustomerNoticeController.class);

	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private SqwAccountRecordService sqwAccountRecordService;

	/**
	 * 获取系统通知信息列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = RequestContant.SYS_NOTICE_INFO_LIST, method = RequestMethod.GET)
	public String infolist(HttpServletRequest request,Model model) {
		log.info("notice content start...");
//		User userInfo = getCurrentUser(request);
//		try {
//			Integer state = sqwAccountRecordService.getUserAccountStatus(userInfo.getUserId(), 1);
//			model.addAttribute("state", state);
//		} catch (Exception e) {
//			log.error("cusInfo error", e);
//		}
		Integer page=1;
		CNoticePageDto noticels=noticeService.infolist(page);
		if(noticels==null){
			noticels=new CNoticePageDto();
			noticels.setPages(0);
		}
		model.addAttribute("noticels", noticels);
		model.addAttribute("page", page);
		log.info("notice content finished.");
		return ResponseContant.SYS_NOTICE_LIST;
	}
	/**
	 * 获取系统通知信息列表更多
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = RequestContant.SYS_NOTICE_INFO_LIST_MORE, method = RequestMethod.GET)
	@ResponseBody
	public String infolist(HttpServletRequest request,Integer page) {
		log.info("notice content start...");
//		User userInfo = getCurrentUser(request);
//		try {
//			Integer state = sqwAccountRecordService.getUserAccountStatus(userInfo.getUserId(), 1);
//			model.addAttribute("state", state);
//		} catch (Exception e) {
//			log.error("cusInfo error", e);
//		}
		
		CNoticePageDto noticelsmore=noticeService.infolist(page);
		
		log.info("notice content finished.");
		return JSON.toJSONString(noticelsmore);
	}
	/**
	 * 根据id获取信息内容
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = RequestContant.SYS_NOTICE_INFO, method = RequestMethod.GET)
	public String info(HttpServletRequest request,Model model,Integer id) {
		log.info("notice content start...");
//		User userInfo = getCurrentUser(request);
//		try {
//			Integer state = sqwAccountRecordService.getUserAccountStatus(userInfo.getUserId(), 1);
//			model.addAttribute("state", state);
//		} catch (Exception e) {
//			log.error("cusInfo error", e);
//		}
		
		CNotice notice = noticeService.info(id);
		
				model.addAttribute("notice", notice);
		log.info("notice content finished.");
		return ResponseContant.SYS_NOTICE;
	}

}
