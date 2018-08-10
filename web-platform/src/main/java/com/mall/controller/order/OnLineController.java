package com.mall.controller.order;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.utils.OnLineTools;

@Controller
@RequestMapping(value="/OnLine")
public class OnLineController {
	
	@RequestMapping(value = "/Look")
	public String Look(String name,HttpServletRequest request,Model model){
		OnLineTools tools=new OnLineTools();
		String result="";
		String index=name.substring(name.lastIndexOf(".")+1, name.length());
		if(index.toUpperCase().equals("DOC"))
		{
			result=tools.getInstance(OnLineTools.WORD, name, "");
		}else if(index.toUpperCase().equals("XLS"))
		{
			result=tools.getInstance(OnLineTools.EXCEL, name, "");
		}
		else if(index.toUpperCase().equals("PDF"))
		{
			result=tools.getInstance(OnLineTools.PDF, name, "");
		}else if(index.toUpperCase().equals("JPG"))
		{
			result=tools.getInstance(OnLineTools.IMG, name, "");
		}else if(index.toUpperCase().equals("JPEG"))
		{
			result=tools.getInstance(OnLineTools.IMG, name, "");
		}else if(index.toUpperCase().equals("PNG"))
		{
			result=tools.getInstance(OnLineTools.IMG, name, "");
		}else if(index.toUpperCase().equals("BPM"))
		{
			result=tools.getInstance(OnLineTools.IMG, name, "");
		}else{
			result="<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"><title></title></head><body>对不起，不支持此类文件的在线阅读</body></html>";
		}
		model.addAttribute("file", result);
		return "/pdf/OnLineFile";
	}
	@RequestMapping(value = "/List")
	public String List(String name,HttpServletRequest request,Model model){
		
		return "/pdf/test";
	}
}
