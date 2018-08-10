package com.mall.controller.product;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
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
import com.mall.dealer.product.po.SearchHotWord;
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
@RequestMapping(value="/hotWords")
public class HotWordsController extends BaseController{


	/**
	 * LOGGER.
	 */
	private static final Log LOGGER = LogFactory.getLogger(HotWordsController.class);
	
	
	/**加载热词列表
	 * @param cate  String
	 * @param page Integer
	 * @param dealerProductSelectConDTO DealerProductSelectConDTO
	 * @return null1
	 */
	@RequestMapping(value="/getHotWordsList")
	public String getActivityTopicByConditions(Model model) {
		
		List<SearchHotWord> hotWords = RemoteServiceSingleton.getInstance()
					.getSearchHotWordApi().findSearchHotWords();

		 model.addAttribute("pb", hotWords);
			
		return "/dealerseller/HotWords/hotWordList";

	}
	
	/**.
	 * 添加热词页
	 * @return ModelAndView
	 */
	@RequestMapping(value="/createHotWords")
	public String createHotWords(Model model,HttpServletRequest request,String id){
		
		if(null != id){
			
			SearchHotWord hotWords = new SearchHotWord();
			hotWords.setId(new BigDecimal(id));
			SearchHotWord findHotWord = RemoteServiceSingleton.getInstance().getSearchHotWordApi().findHotWordsByID(hotWords);
			
			model.addAttribute("findHotWord", findHotWord);
		}
		
		return "/dealerseller/HotWords/createHotWords";
	}
	
	/**.
	 * 编辑热词
	 * @return ModelAndView
	 */
//	@RequestMapping(value="/editHotWords")
//	public String editHotWords(HttpServletRequest request,String isReivew){
//		
//		if(null != isReivew){
//			request.setAttribute("isReivew",1);
//		}
//		
//		return "/dealerseller/TipGoods/createTopic";
//	}
	
	
	/**
	 * 保存热词
	 * @param request HttpServletRequest
	 * @param objectVO DealerProductObjectDTO
	 */
	@RequestMapping(value="/saveHotWords")
	@ResponseBody
	public String saveHotWords(HttpServletRequest request,SearchHotWord hotWord){


		String isSaveParameter = "1";

		LOGGER.info("用户:"+getCurrentUser().getUsername());
		LOGGER.info("用户ID:"+getCurrentUser().getId());


		try {
			
			if(null != hotWord.getId() && !"".equals(hotWord.getId())){
				RemoteServiceSingleton.getInstance().getSearchHotWordApi().updateSearchHotwords(hotWord);
				LOGGER.info("修改热词成功!");
			}else{
				RemoteServiceSingleton.getInstance().getSearchHotWordApi().saveSearchHotwords(hotWord);
				LOGGER.info("保存热词成功!");
			}
			
		} catch (Exception e) {
			LOGGER.error("保存或修改热词错误！  msg:"+e.getMessage());


			LOGGER.error("用户:"+getCurrentUser().getUsername());
			LOGGER.error("用户ID:"+getCurrentUser().getId());
			isSaveParameter="0";
		}
			
		return isSaveParameter;

	}
	
	/**
	 * 删除热词
	 * @param request HttpServletRequest
	 * @param objectVO DealerProductObjectDTO
	 */
	@RequestMapping(value="/deleteHotWords")
	@ResponseBody
	public String deleteHotWords(HttpServletRequest request,String hotId){


		String isSaveParameter = "1";

		LOGGER.info("用户:"+getCurrentUser().getUsername());
		LOGGER.info("用户ID:"+getCurrentUser().getId());


		try {
			
			SearchHotWord hotWords = new SearchHotWord();
			hotWords.setId(new BigDecimal(hotId));
			
			RemoteServiceSingleton.getInstance().getSearchHotWordApi().deleteSearchHotwords(hotWords);
			
			LOGGER.info("删除热词成功!");
			
		} catch (Exception e) {
			LOGGER.error("删除热词错误！  msg:"+e.getMessage());


			LOGGER.error("用户:"+getCurrentUser().getUsername());
			LOGGER.error("用户ID:"+getCurrentUser().getId());
			isSaveParameter="0";
		}
			
		return isSaveParameter;

	}
	
	
	/**
	 * 修改热词
	 * @param request HttpServletRequest
	 * @param objectVO DealerProductObjectDTO
	 */
	@RequestMapping(value="/updateHotWords")
	@ResponseBody
	public String updateHotWords(HttpServletRequest request,SearchHotWord hotWord){


		String isSaveParameter = "1";

		LOGGER.info("用户:"+getCurrentUser().getUsername());
		LOGGER.info("用户ID:"+getCurrentUser().getId());


		try {
			
			RemoteServiceSingleton.getInstance().getSearchHotWordApi().updateSearchHotwords(hotWord);
			
			LOGGER.info("热词修改成功!");
			
		} catch (Exception e) {
			LOGGER.error("热词修改错误！  msg:"+e.getMessage());


			LOGGER.error("用户:"+getCurrentUser().getUsername());
			LOGGER.error("用户ID:"+getCurrentUser().getId());
			isSaveParameter="0";
		}
			
		return isSaveParameter;

	}

}	