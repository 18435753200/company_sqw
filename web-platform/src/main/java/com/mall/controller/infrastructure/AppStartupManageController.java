package com.mall.controller.infrastructure;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONSerializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.commons.utils.DateUtil;
import com.mall.customer.model.Startup;
import com.mall.customer.order.utils.JsonUtils;
import com.mall.customer.service.StartupService;
import com.mall.dto.StartupDto;



@Controller
@RequestMapping("/infrastructure")
public class AppStartupManageController {
	
	private Logger log = LoggerFactory.getLogger(AppStartupManageController.class);
	
	@Autowired
	private StartupService startupServiceImpl;

	@RequestMapping("/setStartupPageList") 
	public String setAppStartupManage(Model model) {
		
		List<Startup> list = startupServiceImpl.getStartupList(null);
		
		model.addAttribute("startupList", list);
		return "/infrastructure/startup/setStartupPageList";
	}
	
	@RequestMapping("/setStartupPage") 
	public String setStartupPage(Long id, Model model) {
		
		if (null != id && id > 0) {
			Startup startup = startupServiceImpl.getStartup(id);
			StartupDto dto = getStartupDto(startup);
			model.addAttribute("startup", dto);
		}
		
		return "/infrastructure/startup/setStartupPage";
	}
	
	@RequestMapping("/addStartup") 
	@ResponseBody
	public String addStartup(StartupDto dto){
		
		dto.setStatus("1");
		
		log.info("request params={}", JsonUtils.ObjectAsString(dto));
		Startup startup = getStartupBo(dto);
		int ret = startupServiceImpl.addStartup(startup);
		if (ret > 0) {
			return "success";
		}
		else {
			return "error";
		}
	}
	
	@RequestMapping("/updateStartup") 
	@ResponseBody
	public String updateStartup(StartupDto startup){
		
		log.info("request params={}", JsonUtils.ObjectAsString(startup));
		int ret = startupServiceImpl.updateStartup(getStartupBo(startup));
		
		if (ret > 0) {
			return "success";
		}
		else {
			return "error";
		}
	}
	
	@RequestMapping("/deleteStartup") 
	@ResponseBody
	public String deleteStartup(Long id){
		
		log.info("deleteStartup params={}", id);
		Startup startup = startupServiceImpl.getStartup(id);
		startup.setStatus("0");
		startupServiceImpl.updateStartup(startup);
		
		return "success";
	}
	
	@RequestMapping("/getStartup") 
	@ResponseBody
	public String getStartup(){
		
		List<Startup> list = startupServiceImpl.getStartupList(null);
		
		List<StartupDto> retList = new ArrayList<StartupDto>();
		if (null != list && !list.isEmpty()) {
			
			for (Startup startup : list) {
				retList.add(getStartupDto(startup));
			}
		}
		
		return JSONSerializer.toJSON(list).toString();
	}
	
	private Startup getStartupBo(StartupDto dto) {
		Startup startup = new Startup();
		startup.setId(dto.getId());
		startup.setDisplayDuration(dto.getDisplayDuration());
		startup.setImgUrl(dto.getImgUrl());
		startup.setJumpLink(dto.getJumpLink());
		startup.setPlatform(dto.getPlatform());
		startup.setTitle(dto.getTitle());
		startup.setType(dto.getType());
		startup.setStatus(dto.getStatus());
		startup.setStartTime(DateUtil.strToDate(dto.getStartTime(), "yyyy-MM-dd HH:mm:ss"));
		startup.setEndTime(DateUtil.strToDate(dto.getEndTime(), "yyyy-MM-dd HH:mm:ss"));
		
		return startup;
	}
	private StartupDto getStartupDto(Startup startup) {
		StartupDto dto = new StartupDto();
		dto.setId(startup.getId());
		dto.setDisplayDuration(startup.getDisplayDuration());
		dto.setImgUrl(startup.getImgUrl());
		dto.setJumpLink(startup.getJumpLink());
		dto.setPlatform(startup.getPlatform());
		dto.setTitle(startup.getTitle());
		dto.setType(startup.getType());
		dto.setStatus(startup.getStatus());
		dto.setStartTime(DateUtil.toDateStr(startup.getStartTime(), "yyyy-MM-dd HH:mm:ss"));
		dto.setEndTime(DateUtil.toDateStr(startup.getEndTime(), "yyyy-MM-dd HH:mm:ss"));
		
		return dto;
	}
}
