package com.mall.controller.category;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 类目相关
 * @author yuanxiayang
 *
 */
@Controller
@RequestMapping("/categoryController")
public class CategoryController{
	
	
	public static final String SEARCH_GLOBAL_CATEGORY = "search/search_global_category";
	public static final String SEARCH_RESULT = "search/result";
	public static final String SEARCH = "search/search";
	
	@RequestMapping("/toGlobalCategory")
	public String toGlobalCategory(){
		
		return SEARCH_GLOBAL_CATEGORY;
	}
	
	/*@RequestMapping("/toGlobalCategory")
	public String toGlobalCategory(){
		
		return SEARCH_GLOBAL_CATEGORY;
	}
	
	
	@RequestMapping("/toGlobalCategory")
	public String toGlobalCategory(){
		
		return SEARCH_GLOBAL_CATEGORY;
	}*/
}
