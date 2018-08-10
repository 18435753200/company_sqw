package com.mall.controller.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.HtmlUtils;

import com.alibaba.fastjson.JSON;
import com.mall.category.api.rpc.BaseDataServiceRpc;
import com.mall.category.api.rpc.CategoryDispService;
import com.mall.category.po.TcCountry;
import com.mall.category.po.TdCateDisp;
import com.mall.controller.base.BaseController;
import com.mall.dealer.product.customer.api.SearchHotWordApi;
import com.mall.dealer.product.po.SearchHotWord;
import com.mall.dsearch.api.SearchService;
import com.mall.dsearch.api.SuggestionService;
import com.mall.dsearch.vo.CatalogNode;
import com.mall.dsearch.vo.PlainProduct;
import com.mall.dsearch.vo.SearchRequest;
import com.mall.dsearch.vo.SearchResponse;
import com.mall.dsearch.vo.Suggestion;
import com.mall.dsearch.vo.SuggestionRequest;
import com.mall.service.CusCouponService;
import com.mall.service.WapSearchService;
import com.mall.utils.CookieUtil;
import com.mall.utils.GetObjectAttr;
import com.mall.utils.StringUtil;
import com.mall.wap.proxy.RemoteServiceSingleton;

/**
 * Search 相关
 * @author yuanxiayang
 *
 */
@Controller
@RequestMapping("/searchController")
public class SearchController extends BaseController { 
	
	private static final Logger logger = Logger.getLogger(SearchController.class);
	
	public static final String SEARCH_PAGE = "search/search";
	
	public static final String SEARCH_GLOBAL_CATEGORY = "search/search_global_category";
	
	public static final String SEARCH_RESULT = "search/new_search_result";
	public static final String SEARCH_RESULT_NULL = "search/search_null";
	public static final String SEARCH_RESULT_SCORLL = "search/search_scroll";
	
	public static final String SEARCH_COMPLEMENT = "search/search_complement";
	
	public static final String COOKIE_NAME = "near";
	
	@Autowired
	private CusCouponService cusCouponService;
	
	/**
	 * to searchPage
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/toSearchPage")
	public String toSearchPage(HttpServletRequest request,HttpServletResponse response,Model model) throws UnsupportedEncodingException{
		
		//获取热门搜索
		getHotSearch(model);
		//获取最新搜索
		getNearSearch(request,model);
		
		return SEARCH_PAGE;
	}
	
	@RequestMapping("/toSearchIndex") 
	public String toSearchIndex(HttpServletRequest request,HttpServletResponse response,Model model) {
		
		
		return "search/search_index";
	}
	
	
	/**
	 * 获取推荐搜索关键字
	 * @param keyWord
	 * @return
	 */
	@RequestMapping("/getSuggestionKeyWord")
	public String getSuggestionKeyWord(SuggestionRequest suggestionRequest,Model model){
		
		logger.info("---------------key= "+suggestionRequest.getKey());
		
		if(StringUtils.isEmpty(suggestionRequest.getKey())){
			return "";
		}
		suggestionRequest.setB2C(true);
		SuggestionService suggestionApi = RemoteServiceSingleton.getInstance().getSuggestionApi();
		
		List<Suggestion> suggestionList = suggestionApi.suggestionSearch(suggestionRequest);
		
		if(suggestionList == null || suggestionList.isEmpty()){
			logger.info("---------------hit size= "+0);
			return "";
		}
		logger.info("---------------hit size= "+suggestionList.size());
		model.addAttribute("suggestionList", suggestionList);
		return SEARCH_COMPLEMENT;
	}
	
	
	
	/**
	 * 获取热门搜索
	 * @param model
	 */
	private void getHotSearch(Model model) {
		SearchHotWordApi searchHotWordApi = RemoteServiceSingleton.getInstance().getSearchHotWordApi();
		List<SearchHotWord> findSearchHotWords = searchHotWordApi.findSearchHotWords();
		logger.info("---------------hotword="+GetObjectAttr.getFiledName(findSearchHotWords));
		model.addAttribute("findSearchHotWords", findSearchHotWords);
	}


	/**
	 * 获取最近搜索
	 * @param request
	 * @param model
	 */
	private void getNearSearch(HttpServletRequest request, Model model){
		//获取cookie
		Cookie cookie = CookieUtil.getCookie(request, COOKIE_NAME);
		if(null!=cookie){
			String value = cookie.getValue();
			String decode_value = "";
			try {
				decode_value = URLDecoder.decode(value, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			//切割字符串 
			String[] cookieVlaues = decode_value.split("_");
			//去除重复元素
			StringUtil.distinct(cookieVlaues, model);
		}
	}
	
	/**
	 * 清楚最近的搜索记录
	 * @param request
	 * @param model
	 */
	@RequestMapping("/delNearSearch")
	public String delNearSearch(HttpServletRequest request,HttpServletResponse response, Model model){
		Cookie cookie = CookieUtil.getCookie(request, "near");
		if(null!=cookie){
			CookieUtil.deleteCookie(response, cookie);
		}
		return "redirect:/searchController/toSearchPage";
	}

	/**
	 * to globlaAndcatetory page
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/toGlobalAndCategory")
	public String toGlobalAndCategory(HttpServletRequest request,HttpServletResponse response,Model model) throws Exception{
		//获取原产国信息
		BaseDataServiceRpc baseDataServiceRpcApi = RemoteServiceSingleton.getInstance().getBaseDataServiceRpcApi();
		List<TcCountry> tcCountries = baseDataServiceRpcApi.getAppDisplayCountries();
		
		model.addAttribute("picUrl",picUrl1);
		model.addAttribute("tcCountries", tcCountries);
		
		
		//获取类目信息
		CategoryDispService categoryDispApi = RemoteServiceSingleton.getInstance().getCategoryDispApi();
		List<TdCateDisp> firstCategoryDispList = categoryDispApi.getB2cFirstCategoryDispList();
		
		//获取一层类目 有且只有一个
		TdCateDisp tdCateDisp = firstCategoryDispList.get(0);
		String firstDispId = null;
		if(null!=tdCateDisp){
			firstDispId = firstCategoryDispList.get(0).getCateDispId();
		}
		List<TdCateDisp> childrenCategoryDispListByParID = categoryDispApi.getChildrenCategoryDispListByParID(firstDispId);
		model.addAttribute("firstDispId", firstDispId);
		model.addAttribute("dispCategoryInfo", childrenCategoryDispListByParID);
		
		//获取推荐商品信息
		SearchService searchApi = RemoteServiceSingleton.getInstance().getSearchApi();
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.setB2C(true);
		String entryType = getEntryType(request);
		searchRequest.setEntryType(entryType);
		
		SearchResponse searchResponse = searchApi.search(searchRequest);
		
		//获取总页数
		Integer totalPage = searchResponse.getHits()/searchRequest.getPageSize();
		totalPage = searchResponse.getHits() % searchRequest.getPageSize()==0 ? totalPage : totalPage+1;
		logger.info("---------------totalPage= "+totalPage);
		//页数
		model.addAttribute("totalPage", totalPage);
		
		model.addAttribute("searchResponse", searchResponse);
		
		return SEARCH_GLOBAL_CATEGORY;
	}
	
	/**
	 * to searchResult
	 * @return
	 */
	@RequestMapping("/toSearchResult")
	public String toSearchResult(HttpServletRequest request,HttpServletResponse response,SearchRequest searchRequest, Model model){
		
		logger.info("---------------to searchResult by searchRequest = "+GetObjectAttr.getFiledName(searchRequest));
		
		System.out.println("---------"+request.getParameter("code"));
		
		SearchService searchApi = RemoteServiceSingleton.getInstance().getSearchApi();
		//参数整合
		searchRequest.setB2C(true);
		String entryType = getEntryType(request);
		searchRequest.setEntryType(entryType);
		searchRequest.setKeyword(replaysAll(searchRequest.getKeyword()));
		searchRequest.setCdid(replaysAll(searchRequest.getCdid()));
		searchRequest.setBrandName(replaysAll(searchRequest.getBrandName()));
		searchRequest.setTagName(replaysAll(searchRequest.getTagName()));
		searchRequest.setFcode(replaysAll(searchRequest.getFcode()));
		
		SearchResponse searchResponse = searchApi.search(searchRequest);
		
		if(null!=searchResponse && searchResponse.getHits()==0){
			model.addAttribute("searchRequest", searchRequest);
			return SEARCH_RESULT_NULL;
		}
		//获取总页数
		Integer totalPage = searchResponse.getHits()/searchRequest.getPageSize();
		totalPage = searchResponse.getHits() % searchRequest.getPageSize()==0 ? totalPage : totalPage+1;
		logger.info("---------------totalPage= "+totalPage);
		//页数
		model.addAttribute("totalPage", totalPage);
		//回显
		model.addAttribute("picUrl1",picUrl3);
		
		//过滤脚本
	    searchRequest.setPromotion(HtmlUtils.htmlEscape(searchRequest.getPromotion()));
		searchRequest.setKeyword(HtmlUtils.htmlEscape(searchRequest.getKeyword()));
	    searchRequest.setCountryName(HtmlUtils.htmlEscape(searchRequest.getCountryName()));
		searchRequest.setBrandName(HtmlUtils.htmlEscape(searchRequest.getBrandName()));
		
		model.addAttribute("searchRequest", searchRequest);
		
		//获取搜索结果的促销优惠（直降）
		List<PlainProduct> items = searchResponse.getItems();
		//Map<Long, BigDecimal> priceMap = null;
		if(null != items && items.size() > 0){
			List<Long> productIds = new ArrayList<Long>();
			//Map<Long, Long> proSku = new HashMap<Long, Long>();
			for (PlainProduct plainProduct : items) {
				productIds.add(Long.parseLong(plainProduct.getPid()));
				if(plainProduct.isBoolPromotion()){
					plainProduct.setUnit_price(plainProduct.getPromotion_price());
				}
				
			}
			/*//priceMap = cusCouponService.getPromotionPriceByPids(productIds, proSku, items);
			for (PlainProduct plainProduct : items) {
				//BigDecimal discount = priceMap.get(proSku.get(Long.parseLong(plainProduct.getPid())));
				//当有促销优惠（直降），且促销优惠小于原价的时候，将价格重置
				//if(null != discount && plainProduct.getUnit_price().compareTo(discount) > 0){
				//	plainProduct.setUnit_price(plainProduct.getUnit_price().subtract(discount).setScale(2, BigDecimal.ROUND_HALF_DOWN));
				//}
				
				logger.info("促销价:" + plainProduct.getUnit_price().doubleValue() + "----市场价:" + plainProduct.getDomestic_price().doubleValue());
			}*/
		}
		model.addAttribute("searchResponse", searchResponse);
		List<CatalogNode>list=new ArrayList<CatalogNode>();
		for(CatalogNode c:searchResponse.getCatalogs()){
			if(getCategoryId(c.getId())==false){
				list.add(c);
			}	
		}
		model.addAttribute("catalogs",list);
		
		if("doublell".equals(request.getParameter("code"))){
			String decode_value = "";
			//获取cookie
			Cookie cookie = CookieUtil.getCookie(request, COOKIE_NAME);
			if(null!=cookie){
				String cookieValue = cookie.getValue();
				//关键词加入到cookie中
				cookieValue += "_"+searchRequest.getKeyword();
				//切割字符串 
				String[] cookieVlaues = cookieValue.split("_");
				//去除重复元素
				String str = StringUtil.distinct(cookieVlaues);
				System.out.println("------"+str);
				try {
					decode_value = URLDecoder.decode(str, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				cookie.setValue(decode_value);
			}else{
				try {
					decode_value = URLDecoder.decode(searchRequest.getKeyword(), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cookie = new Cookie(COOKIE_NAME,decode_value);
				cookie.setMaxAge(3600);
				//设置路径，这个路径即该工程下都可以访问该cookie 如果不设置路径，那么只有设置该cookie路径及其子路径可以访问
				cookie.setPath("/");
				response.addCookie(cookie);
			}
			
		}
		
		return SEARCH_RESULT;
	}

	/**
	 * to searchResult 页面划页、筛选条件
	 * @return
	 */
	@RequestMapping("/toSearchResultScroll")
	public String toSearchResultScroll(HttpServletRequest request,HttpServletResponse response,SearchRequest searchRequest, Model model){
		
		searchRequest.setB2C(true);
		String entryType = getEntryType(request);
		searchRequest.setEntryType(entryType);
		
		logger.info("---------------to searchResultScroll by searchRequest = "+GetObjectAttr.getFiledName(searchRequest));
		
		SearchService searchApi = RemoteServiceSingleton.getInstance().getSearchApi();
		
		SearchResponse searchResponse = searchApi.search(searchRequest);
		
		logger.info("---------------- 根据条件查询："+GetObjectAttr.getFiledName(searchResponse));
		model.addAttribute("picUrl1",picUrl3);
		model.addAttribute("searchRequest", searchRequest);
		
		//获取搜索结果的促销优惠（直降）
		List<PlainProduct> items = searchResponse.getItems();
		//Map<Long, BigDecimal> priceMap = null;
		if(null != items && items.size() > 0){
			List<Long> productIds = new ArrayList<Long>();
			//Map<Long, Long> proSku = new HashMap<Long, Long>();
			for (PlainProduct plainProduct : items) {
				productIds.add(Long.parseLong(plainProduct.getPid()));
				if(plainProduct.isBoolPromotion()){
					//plainProduct.setDomestic_price(plainProduct.getUnit_price());
					plainProduct.setUnit_price(plainProduct.getPromotion_price());
				}
			}
			/*priceMap = cusCouponService.getPromotionPriceByPids(productIds, proSku, items);
			for (PlainProduct plainProduct : items) {
				BigDecimal discount = priceMap.get(proSku.get(Long.parseLong(plainProduct.getPid())));
				//当有促销优惠（直降），且促销优惠小于原价的时候，将价格重置
				if(null != discount && plainProduct.getUnit_price().compareTo(discount) > 0){
					plainProduct.setUnit_price(plainProduct.getUnit_price().subtract(discount).setScale(2, BigDecimal.ROUND_HALF_DOWN));
				}
			}*/
		}
		model.addAttribute("searchResponse", searchResponse);
		List<CatalogNode>list=new ArrayList<CatalogNode>();
		for(CatalogNode c:searchResponse.getCatalogs()){
			if(getCategoryId(c.getId())==false){
				list.add(c);
			}	
		}
		model.addAttribute("catalogs",list);
		return SEARCH_RESULT_SCORLL;
	}
	
	/**
	 * 首页中搜索
	 */
	@RequestMapping("/b2c_wrap_indexsearch")
	public String searchIndex(Model model){
		logger.info("------从首页进入搜索页面-------");
		List<String> hotWord = WapSearchService.getHotSearch();
		logger.info("-----获取热词num:"+hotWord.size());
		model.addAttribute("hotWord", hotWord);
		return "index/b2c_wrap_indexsearch";
	}
	
	/**
	 * 处理搜索关键字提示
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/getProMessage")
	public String searchS(HttpServletRequest request,HttpServletResponse response,Model model ){
		String keyWord =request.getParameter("q");
		logger.info("------------search key:"+keyWord);
		List<Suggestion> suggestionSearch = WapSearchService.suggestionSearch(keyWord);
		List<String[]> list = new ArrayList<String[]>();
		if (suggestionSearch != null) {
			for(Suggestion sss : suggestionSearch){
				String[] str ={sss.getKeyword(),String.valueOf(sss.getCount())};
            	list.add(str);
			}
		}
		String result = JSON.toJSONString(list);
		logger.info("------------the result by key"+result);
        try {
			response.getWriter().write("KISSY.Suggest.callback({'result':"+result+"})");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("/getJson")
    public String readHtmlFile(HttpServletRequest request,HttpServletResponse response,SearchRequest searchRequest, Model model){  
    	
    	String type = request.getParameter("type");
    	
    	String sourcePath="http://cms.zhongjumall.com/json/3333.json";
    	if (!StringUtils.isEmpty(type) && "item".equals(type)) {
    		sourcePath="http://cms.zhongjumall.com/json/4069.json";
    	}
    	
        String line;  
        int lineNum=0;  
        BufferedReader reader=null;  
        String ret = "";
        try{  
            URL url = new URL(sourcePath);  
            reader = new BufferedReader(new InputStreamReader(url.openStream()));  
            while ((line = reader.readLine()) != null){  
                lineNum++;  
                ret += line;
            }  
        }  
        catch (Exception ie){  
            ie.printStackTrace();  
        }finally{  
            try{  
                if(reader != null)  
                    reader.close();  
            }catch (Exception e){  
                e.printStackTrace();  
            }  
        }  
        
        try {
			response.getWriter().write(ret);
		} catch (IOException e) {
			e.printStackTrace();
		} 
        return null;
    }  
	
	
	/**
	 * 替换特殊字符
	 */
	protected String replaysAll(String str){
		String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		if(str!=null){
			Matcher m = p.matcher(str);
			return m.replaceAll("").trim();
		}
		return str;
	}
	
	
	/**
	 * 判断不是手机类目
	 * @param cateId
	 * @return
	 */
	public boolean getCategoryId(String cateId){
		if(cateId.equals("040")||cateId.equals("041")||cateId.equals("042")||cateId.equals("043")||cateId.equals("044")||cateId.equals("045")||cateId.equals("046")||cateId.equals("047")||cateId.equals("048")){
			return true;
		}
		return false;
	}
	
	@RequestMapping("/toSearchResultScroll1")
	public String toSearchResultScroll1(HttpServletRequest request,HttpServletResponse response,SearchRequest searchRequest, Model model){
		
		searchRequest.setB2C(true);
		String entryType = getEntryType(request);
		searchRequest.setEntryType(entryType);
		
		searchRequest.setFcode(replaysAll(searchRequest.getFcode()));
		logger.info("---------------to searchResultScroll by searchRequest = "+GetObjectAttr.getFiledName(searchRequest));
		
		SearchService searchApi = RemoteServiceSingleton.getInstance().getSearchApi();
		
		SearchResponse searchResponse = searchApi.search(searchRequest);
		if(null!=searchResponse && searchResponse.getHits()==0){
			model.addAttribute("searchRequest", searchRequest);
			return SEARCH_RESULT_NULL;
		}
		//获取总页数
		Integer totalPage = searchResponse.getHits()/searchRequest.getPageSize();
		totalPage = searchResponse.getHits() % searchRequest.getPageSize()==0 ? totalPage : totalPage+1;
		logger.info("---------------totalPage= "+totalPage);
		//页数
		model.addAttribute("totalPage", totalPage);
		
		logger.info("---------------- 根据条件查询："+GetObjectAttr.getFiledName(searchResponse));
		model.addAttribute("picUrl1",picUrl3);
		model.addAttribute("searchRequest", searchRequest);
		
		//获取搜索结果的促销优惠（直降）
		List<PlainProduct> items = searchResponse.getItems();
		//Map<Long, BigDecimal> priceMap = null;
		if(null != items && items.size() > 0){
			List<Long> productIds = new ArrayList<Long>();
			//Map<Long, Long> proSku = new HashMap<Long, Long>();
			for (PlainProduct plainProduct : items) {
				productIds.add(Long.parseLong(plainProduct.getPid()));
				if(plainProduct.isBoolPromotion()){
					//plainProduct.setDomestic_price(plainProduct.getUnit_price());
					plainProduct.setUnit_price(plainProduct.getPromotion_price());
				}
			}
			/*priceMap = cusCouponService.getPromotionPriceByPids(productIds, proSku, items);
			for (PlainProduct plainProduct : items) {
				BigDecimal discount = priceMap.get(proSku.get(Long.parseLong(plainProduct.getPid())));
				//当有促销优惠（直降），且促销优惠小于原价的时候，将价格重置
				if(null != discount && plainProduct.getUnit_price().compareTo(discount) > 0){
					plainProduct.setUnit_price(plainProduct.getUnit_price().subtract(discount).setScale(2, BigDecimal.ROUND_HALF_DOWN));
				}
			}*/
		}
		model.addAttribute("searchResponse", searchResponse);
		List<CatalogNode>list=new ArrayList<CatalogNode>();
		for(CatalogNode c:searchResponse.getCatalogs()){
			if(getCategoryId(c.getId())==false){
				list.add(c);
			}	
		}
		model.addAttribute("catalogs",list);
		return SEARCH_RESULT_SCORLL;
	}
}
