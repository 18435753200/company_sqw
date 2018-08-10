package com.mall.controller.flashsale;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mall.constant.RequestContant;
import com.mall.controller.base.BaseController;
import com.mall.dealer.product.customer.api.CustomerProductApi;
import com.mall.dsearch.api.SearchService;
import com.mall.dsearch.vo.PlainProduct;
import com.mall.dsearch.vo.SearchRequest;
import com.mall.dsearch.vo.SearchResponse;
import com.mall.mybatis.utility.PageBean;
import com.mall.platform.model.PlatformActivity;
import com.mall.platform.model.PlatformActivityConfig;
import com.mall.platform.model.PlatformActivityProduct;
import com.mall.platform.service.PlatformActivityConfigService;
import com.mall.platform.service.PlatformActivityService;
import com.mall.service.CusCouponService;
import com.mall.utils.Constants;
import com.mall.wap.proxy.RemoteServiceSingleton;


@Controller
@RequestMapping("/view/activity")
public class ActivityController extends BaseController {
	
	@Autowired
	private CustomerProductApi customerProductApi;
	@Autowired
	private CusCouponService cusCouponService;
	@Autowired
	private SearchService searchService;
	@Autowired
	private PlatformActivityConfigService platformActivityConfigService; 
	
	
	private static final Logger logger = Logger.getLogger(ActivityController.class);
	/**
	 * @param activityId
	 * @param activeType  活动类型：闪购 falshsale；brand品牌专场；new上新；newcomer 新人专享；
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/get/{activityId}",method=RequestMethod.GET) 
	public String getActivityScene(@PathVariable Long activityId, Model model,HttpServletRequest req){
		logger.info("------------活动列表页activityId="+activityId);
		//获取服务接口
		PlatformActivityService platformActivityService = RemoteServiceSingleton.getInstance().getPlatformActivityService();
		//获取商品服务接口
		
		//获取活动主信息
		PlatformActivity activity = platformActivityService.getActivityById(activityId);
		//获取活动的商品
//		List<PlatformActivityProduct> productList = platformActivityService.findProductListByActivityId(activityId);
		PageBean<PlatformActivityProduct> pageBean = new PageBean<PlatformActivityProduct>();
		Map map = new HashMap();
		map.put("activityId", activityId);
		pageBean.setParameter(map);
		pageBean.setPageSize(Constants.PAGE);
		pageBean.setPage(1);
				
		pageBean = platformActivityService.findActivityProductPageList(pageBean);
		try {
			if(pageBean.getResult()!=null&&pageBean.getResult().size()>0){
				List<PlatformActivityProduct> products = pageBean.getResult();
				
				//列表页显示促销价
				List<Long> productIds = new ArrayList<Long>();
				for (PlatformActivityProduct platformActivityProduct : products) {
					productIds.add(platformActivityProduct.getProductid());
				}
				Map<Long,Long> proSku = new HashMap<Long, Long>();
				Map<Long,BigDecimal> priceMap = null;
				
				for (PlatformActivityProduct platformActivityProduct : products) {

					SearchRequest request = new SearchRequest();
					request.setPid(""+platformActivityProduct.getProductid());
					request.setB2C(true);
					String entryType = getEntryType(req);
					request.setEntryType(entryType);
					SearchResponse response = searchService.search(request);
					if(null!=response && response.getHits()>0 ){
						List<PlainProduct> items = response.getItems();
						
						PlainProduct plainProduct = items.get(0);
						platformActivityProduct.setProductTitle(plainProduct.getB2cPname());
						if(plainProduct.isBoolPromotion()){
							platformActivityProduct.setProductPrice(plainProduct.getPromotion_price());
						}else{
							platformActivityProduct.setProductPrice(plainProduct.getUnit_price());
						}
						platformActivityProduct.setProductImageUrl(Constants.FILESERVER3+plainProduct.getImageurl());
						BigDecimal unitPrice= plainProduct.getUnit_price()==null?new BigDecimal(0):plainProduct.getUnit_price();
						BigDecimal markPrice= plainProduct.getDomestic_price()==null?new BigDecimal(0):plainProduct.getDomestic_price();
						
						if(markPrice.compareTo(unitPrice)>0){
							platformActivityProduct.setMarketPrice(markPrice);
						}
						logger.info("===商品价格日志---商品id："+platformActivityProduct.getProductid()+"，市场价："+plainProduct.getUnit_price()+"-->"+platformActivityProduct.getMarketPrice()+">=单价"+platformActivityProduct.getProductPrice());
						
					}
					if(platformActivityProduct.getProductTitle()==null||platformActivityProduct.getProductImageUrl()==null
							||platformActivityProduct.getProductPrice()==null||platformActivityProduct.getMarketPrice()==null){
						logger.info("调用search 商品获取信息不全--pname="+platformActivityProduct.getProductTitle()+",price= "+platformActivityProduct.getProductPrice()
								+",ImageUrl= "+platformActivityProduct.getProductImageUrl()+",makertPrice= "+platformActivityProduct.getMarketPrice());
					}
				}
				//获取总页数
				long totalPage = pageBean.getTotalCount()%Constants.PAGE==0?pageBean.getTotalCount()/Constants.PAGE:pageBean.getTotalCount()/Constants.PAGE+1;
				model.addAttribute("totalPage", totalPage);
				model.addAttribute("activityId", activityId);
				model.addAttribute("activityType", activity.getStatus());//1  闪购 2 其他活动
				model.addAttribute("productList", pageBean);
			}else{
				logger.info("-------------ActivityProduct.size() = "+0);
			}
		} catch (Exception e) {
			logger.info("---------------调用search，封装数据过程异常"+e.getMessage());
		}
		
		
		//获取活动二级页面的url
		model.addAttribute("activityUrl", Constants.FILESERVER0+activity.getPicUrl());
		model.addAttribute("title", activity.getTitle());
		
		boolean specialFlag = checkSpecialActivity(model, activityId);
		if(specialFlag){
			return "specialactivity/banner_classifyly_activity";
		}
		
		if(activity.getStatus()==1){//闪购专场
			model.addAttribute("nowdate", System.currentTimeMillis());
			
			long allsec= activity.getEndTime().getTime()-System.currentTimeMillis();
			
			long day = allsec/(60*60*24*1000);
			long hour = (allsec-day*(60*60*24*1000))/(3600*1000);
			long min = (allsec-day*(60*60*24*1000)-hour*(3600*1000))/(60*1000);
			long sec = (allsec-day*(60*60*24*1000)-hour*(3600*1000)-min*60*1000)/1000;
			
			String d = day+"";
			String h = hour+"";
			String m = min+"";
			String s = sec+"";
			if(h.length()==1)h='0'+h;
			if(m.length()==1)m='0'+m;
			if(s.length()==1)s='0'+s;
			String timestr = "闪购倒计时 "+d+"<i class=\"color_gray\">天</i>"+h+"<i class=\"color_gray\">时</i>"+m+"<i class=\"color_gray\">分</i>"+s+"<i class=\"color_gray\">秒</i>";
			model.addAttribute("timestr", timestr);
			model.addAttribute("startTime", activity.getStartTime());
			model.addAttribute("endTime", activity.getEndTime());
			model.addAttribute("endTimes", activity.getEndTime().getTime()/1000);
			model.addAttribute("bodyclass", "sg_list");
		}else if(activity.getStatus()==2){//其他活动
			model.addAttribute("titleMsg",activity.getActivityName());
			model.addAttribute("bodyclass", "xsj_prolist");
		}
		return "zhuanti/zhuanti_scene";
	}
	@RequestMapping(value="/getscroll/{activityId}") 
	public String getActivitySceneScroll(@PathVariable Long activityId,Model model,HttpServletRequest req){
		logger.info("-----------滚动翻页activityId="+activityId);
		// 获取服务接口
		PlatformActivityService platformActivityService = RemoteServiceSingleton
				.getInstance().getPlatformActivityService();
		// 获取商品服务接口

		// 获取活动主信息
		PlatformActivity activity = platformActivityService.getActivityById(activityId);
		// 获取活动的商品
		// List<PlatformActivityProduct> productList =
		// platformActivityService.findProductListByActivityId(activityId);
		PageBean<PlatformActivityProduct> pageBean = new PageBean<PlatformActivityProduct>();
		Map map = new HashMap();
		map.put("activityId", activityId);
		pageBean.setParameter(map);
		pageBean.setPageSize(Constants.PAGE);
		Integer page = Integer.parseInt(req.getParameter("pageno"));
		if (page != null && page != 0) {
			pageBean.setPage(page);
		} else {
			pageBean.setPage(1);
		}

		pageBean = platformActivityService.findActivityProductPageList(pageBean);
		try {
			if (pageBean.getResult() != null && pageBean.getResult().size() > 0) {
				List<PlatformActivityProduct> products = pageBean.getResult();
				
				//列表页显示促销价
				List<Long> productIds = new ArrayList<Long>();
				for (PlatformActivityProduct platformActivityProduct : products) {
					productIds.add(platformActivityProduct.getProductid());
				}
				Map<Long,Long> proSku = new HashMap<Long, Long>();
				Map<Long,BigDecimal> priceMap = null;
				
				for (PlatformActivityProduct platformActivityProduct : products) {

					SearchRequest request = new SearchRequest();
					request.setPid("" + platformActivityProduct.getProductid());
					request.setB2C(true);
					String entryType = getEntryType(req);
					request.setEntryType(entryType);
					SearchResponse response = searchService.search(request);
					if(null!=response && response.getHits()>0 ){
						List<PlainProduct> items = response.getItems();
						
						PlainProduct plainProduct = items.get(0);
						platformActivityProduct.setProductTitle(plainProduct.getB2cPname());
						if(plainProduct.isBoolPromotion()){
							platformActivityProduct.setProductPrice(plainProduct.getPromotion_price());
						}else{
							platformActivityProduct.setProductPrice(plainProduct.getUnit_price());
						}
						platformActivityProduct.setProductImageUrl(Constants.FILESERVER3+plainProduct.getImageurl());
						BigDecimal unitPrice= plainProduct.getUnit_price()==null?new BigDecimal(0):plainProduct.getUnit_price();
						BigDecimal markPrice= plainProduct.getDomestic_price()==null?new BigDecimal(0):plainProduct.getDomestic_price();
						
						if(markPrice.compareTo(unitPrice)>0){
							platformActivityProduct.setMarketPrice(markPrice);
						}
						logger.info("===商品价格日志---商品id："+platformActivityProduct.getProductid()+"，市场价："+plainProduct.getUnit_price()+"-->"+platformActivityProduct.getMarketPrice()+">=单价"+platformActivityProduct.getProductPrice());
						
					}
					if(platformActivityProduct.getProductTitle()==null||platformActivityProduct.getProductImageUrl()==null
							||platformActivityProduct.getProductPrice()==null||platformActivityProduct.getMarketPrice()==null){
						logger.info("调用search 商品获取信息不全--pname="+platformActivityProduct.getProductTitle()+",price= "+platformActivityProduct.getProductPrice()
								+",ImageUrl= "+platformActivityProduct.getProductImageUrl()+",makertPrice= "+platformActivityProduct.getMarketPrice());
					}
				}
				model.addAttribute("productList", pageBean);
			} else {
				logger.info("-------------ActivityProduct.size() = " + 0);
			}
		} catch (Exception e) {
			logger.info("---------------调用search，封装数据过程异常"+ e.getMessage());
		}
				
		if(activity.getStatus()==1){//闪购专场
			model.addAttribute("startTime", activity.getStartTime());
			model.addAttribute("endTime", activity.getEndTime());
		}
		
		return "zhuanti/zhuanti_scene_scroll";
	}
	
	public boolean checkSpecialActivity(Model model,Long activityId){
		
		PlatformActivityConfig config =platformActivityConfigService.findByActivityId(activityId, 1);
		if(config==null){
			return false;
		}
		logger.info("---------特殊活动-首页轮播图分类展示--");
		List<Map> tips = platformActivityConfigService.findTipsByGroupId(config.getActivityGroupId(), 1);
		model.addAttribute("tips", tips);
		
		return true;
	}
	
	@RequestMapping(value="/getspecialscroll/{activityId}") 
	public String toSpecialActivityScroll(@PathVariable Long activityId,Model model,HttpServletRequest req){
		logger.info("-----------特殊活动activityId="+activityId);
		//获取服务接口
		PlatformActivityService platformActivityService = RemoteServiceSingleton.getInstance().getPlatformActivityService();
		//获取商品服务接口
		
		//获取活动主信息
		PlatformActivity activity = platformActivityService.getActivityById(activityId);
		//获取活动的商品
		PageBean<PlatformActivityProduct> pageBean = new PageBean<PlatformActivityProduct>();
		pageBean.setParameter(activity);
		pageBean.setPageSize(Constants.PAGE);
		
		Integer page = Integer.parseInt(req.getParameter("pageno")==null?"1":req.getParameter("pageno"));
		if (page != null && page != 0) {
			pageBean.setPage(page);
		} else {
			pageBean.setPage(1);
		}
		
		pageBean = platformActivityService.findActivityProductPageList(pageBean);
		
		try {
			
			if(pageBean.getResult()!=null&&pageBean.getResult().size()>0){
				List<PlatformActivityProduct> products = pageBean.getResult();
				for (PlatformActivityProduct platformActivityProduct : products) {
	
					SearchRequest request = new SearchRequest();
					request.setPid(""+platformActivityProduct.getProductid());
					request.setB2C(true);
					String entryType = getEntryType(req);
					request.setEntryType(entryType);
					SearchResponse response = searchService.search(request);
					if(null!=response && response.getHits()>0 ){
						List<PlainProduct> items = response.getItems();
//						logger.info("===商品价格日志---商品id："+platformActivityProduct.getProductid()+"，市场价："+items.get(0).getDomestic_price()+">=单价"+items.get(0).getUnit_price());
						platformActivityProduct.setProductTitle(items.get(0).getB2cPname());
						platformActivityProduct.setProductPrice(items.get(0).getUnit_price());
						platformActivityProduct.setProductImageUrl(Constants.FILESERVER3+items.get(0).getImageurl());
						BigDecimal unitPrice= items.get(0).getUnit_price()==null?new BigDecimal(0):items.get(0).getUnit_price();
						BigDecimal markPrice= items.get(0).getDomestic_price()==null?new BigDecimal(0):items.get(0).getDomestic_price();
						if(markPrice.compareTo(unitPrice)>0)
							platformActivityProduct.setMarketPrice(markPrice);
						logger.info("===商品价格日志---商品id："+platformActivityProduct.getProductid()+"，市场价："+items.get(0).getUnit_price()+"-->"+platformActivityProduct.getMarketPrice()+">=单价"+platformActivityProduct.getProductPrice());
					}
					if(platformActivityProduct.getProductTitle()==null||platformActivityProduct.getProductImageUrl()==null
							||platformActivityProduct.getProductPrice()==null||platformActivityProduct.getMarketPrice()==null){
						logger.info("调用search 商品获取信息不全--pname="+platformActivityProduct.getProductTitle()+",price= "+platformActivityProduct.getProductPrice()
								+",ImageUrl= "+platformActivityProduct.getProductImageUrl()+",makertPrice= "+platformActivityProduct.getMarketPrice());
					}
				}
				//获取总页数
				long totalPage = pageBean.getTotalCount()%Constants.PAGE==0?pageBean.getTotalCount()/Constants.PAGE:pageBean.getTotalCount()/Constants.PAGE+1;
				model.addAttribute("totalPage", totalPage);
			}
		} catch (Exception e) {
			System.out.println("---------------调用search，封装数据过程异常"+e.getMessage());
		}
		
		model.addAttribute("productList", pageBean);
		
		
		return "specialactivity/banner_classifyly_activity_scroll";
	}
	
}
