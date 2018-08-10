package com.mall.controller.flashsale;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mall.platform.model.PlatformActivity;
import com.mall.platform.service.PlatformActivityService;
import com.mall.utils.Constants;
import com.mall.wap.proxy.RemoteServiceSingleton;

@Controller
@RequestMapping("/view/flash")
public class FlashSaleController {

	//闪购首页，默认显示进行中
	@RequestMapping(value="/sale",method=RequestMethod.GET)
	public String sale(HttpServletRequest request, HttpServletResponse response){
			
		PlatformActivityService platformActivityService =  RemoteServiceSingleton.getInstance().getPlatformActivityService();
		List<PlatformActivity> curActivites = platformActivityService.findCurrentActivityList(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		request.setAttribute("curActivites", curActivites);
		
		List<PlatformActivity> comingActivites = platformActivityService.findComingActivityList(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		request.setAttribute("comingActivites", comingActivites);
		
		request.setAttribute("picUrl", Constants.FILESERVER0);
		request.setAttribute("nowdate", System.currentTimeMillis());
		return "flashsale/beginning";
	}
	
	//尚未开始的
	@RequestMapping(value="/unsale",method=RequestMethod.GET)
	public String unsale(HttpServletRequest request, HttpServletResponse response){
		
		PlatformActivityService platformActivityService =  RemoteServiceSingleton.getInstance().getPlatformActivityService();
		List<PlatformActivity> comingActivites = platformActivityService.findComingActivityList(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		request.setAttribute("activities", comingActivites);
		request.setAttribute("picUrl", Constants.FILESERVER0);
		return "flashsale/unbegin";
	}
}
