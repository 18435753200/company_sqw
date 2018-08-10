package com.mall.controller.item;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.json.JSON;
import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.controller.base.BaseController;
import com.mall.customer.order.utils.JsonUtils;
import com.mall.dealer.product.api.DealerProductMixService;
import com.mall.dealer.product.dto.B2cPromotionSkuDto;
import com.mall.dealer.product.po.DealerProductCombination;
import com.mall.dealer.product.po.DealerProductScene;
import com.mall.dsearch.api.SearchService;
import com.mall.dsearch.vo.PlainProduct;
import com.mall.dsearch.vo.SearchRequest;
import com.mall.dsearch.vo.SearchResponse;
import com.mall.mybatis.utility.PageBean;
//import com.mall.promotion.dto.activity.PromotionPriceDto;
import com.mall.service.CusCouponService;
import com.mall.utils.CmsBlockDataConfigUtil;
import com.mall.utils.Constants;
import com.mall.vo.CommodityProductVo;
import com.mall.wap.proxy.RemoteServiceSingleton;

@Controller
@RequestMapping("/view/productMix")
public class ProductMixController extends BaseController {
	private Log logger = LogFactory.getLogger(ProductMixController.class);
	@Autowired
	private DealerProductMixService productMixService;
	@Autowired
	private SearchService searchService;
	@Autowired
	private CusCouponService cusCouponService;

	@RequestMapping("/toShowList")
	public String toShowMixList(Model model, HttpServletRequest request,
			HttpServletResponse response, DealerProductScene scene) {
		PageBean<DealerProductScene> pageBean = new PageBean<DealerProductScene>();
		pageBean.setPageSize(5);
		pageBean.setParameter(null);
		pageBean = productMixService.findShowMix(pageBean);
		if (pageBean.getResult() != null && pageBean.getResult().size() > 0) {
			for (DealerProductScene scene2 : pageBean.getResult()) {
				scene2.setDetailImageUrl(picUrl1 + scene2.getDetailImageUrl());
			}
		}

		model.addAttribute("pb", pageBean);
		return "/productMix/productMixList";
	}

	@RequestMapping("/toShowMix")
	public String toShowMix(Model model, HttpServletRequest request,
			HttpServletResponse response, Long mixId) {
		DealerProductScene mix = productMixService.findProductMixById(mixId);
		Boolean flag = false;
		String type = request.getParameter("type");
		if (type != null && type.equals("wap")) {
			flag = true;
		}

		if (mix == null) {
			logger.error("情景组合异常 情景组合不存在mixId：" + mixId);
			throw new RuntimeException("情景组合异常 情景组合不存在mixId：" + mixId);
		}
		mix.setDetailImageUrl(picUrl1 + mix.getDetailImageUrl());
		List<DealerProductCombination> combinations = mix.getCombinations();
		if (combinations != null && combinations.size() > 0) {
			for (DealerProductCombination com : combinations) {
				com.setImageUrl(picUrl2 + com.getImageUrl());

				BigDecimal a = com.getDomesticPrice();
				BigDecimal b = com.getPrice();

				if (a != null && b != null) {

					if (a.compareTo(b) <= 0) {

						com.setDomesticPrice(new BigDecimal(0));
					}
				}
			}
			model.addAttribute("coms", combinations);
			Map<Long, List<DealerProductCombination>> findMapComs = this
					.findMapComs(combinations);
			for (int x = 1; x <= findMapComs.size(); x++) {
				List<DealerProductCombination> list = findMapComs.get(x);
				model.addAttribute("com" + x, list);
			}

			model.addAttribute("comMap", findMapComs);
			model.addAttribute("mixGroupNum", findMapComs.size());
		} else {
			logger.error("情景组合异常 情景组合无商品mixId：" + mixId);
			throw new RuntimeException("情景组合异常 情景组合无商品mixId：" + mixId);
		}
		model.addAttribute("mix", mix);
		model.addAttribute("flag", flag);
		return "/item/productMixDetail";
	}

	private Map<Long, List<DealerProductCombination>> findMapComs(
			List<DealerProductCombination> combinations) {
		Map<Long, List<DealerProductCombination>> map = new HashMap<Long, List<DealerProductCombination>>();
		List<Long> skuIds = new ArrayList<Long>();
		for (DealerProductCombination com : combinations) {
			List<DealerProductCombination> list = map.get(com.getGroupNum());
			if (list == null)
				list = new ArrayList<DealerProductCombination>();
			list.add(com);
			skuIds.add(com.getSkuId());
			map.put(com.getGroupNum(), list);
		}
		logger.info("获取促销价开始");
		//List<PromotionPriceDto> promotionPriceDtos = RemoteServiceSingleton.getInstance().getProductByPromotionInfoService().getPromotionPriceBySkuId(skuIds, Constants.B2C_FLAG, Constants.ACCESSMODE_WAP);
		Map<Long,BigDecimal> priceMap = new HashMap<Long,BigDecimal>();
		/*for (PromotionPriceDto promotionPriceDto : promotionPriceDtos) {
			priceMap.put(promotionPriceDto.getSkuId(), promotionPriceDto.getDiscountPrice());
		}*/
		for (DealerProductCombination com : combinations) {
			if(null != priceMap.get(com.getSkuId())){
				com.setPrice(com.getPrice().subtract(priceMap.get(com.getSkuId())));
			}
		}
		logger.info("设置促销价结束");
		return map;
	}

	@RequestMapping(value = "/toShowMixScroll", method = RequestMethod.GET)
	public String toShowMixScroll(HttpServletRequest request,
			HttpServletResponse response, Integer page, Model model) {

		PageBean<DealerProductScene> pageBean = new PageBean<DealerProductScene>();
		pageBean.setPageSize(5);
		pageBean.setPage(page);
		pageBean.setParameter(null);

		pageBean = productMixService.findShowMix(pageBean);
		if (pageBean.getResult() != null && pageBean.getResult().size() > 0) {
			for (DealerProductScene scene2 : pageBean.getResult()) {
				scene2.setDetailImageUrl(picUrl1 + scene2.getDetailImageUrl());
			}
		}

		model.addAttribute("pb", pageBean);
		return "/productMix/productMixListScroll";
	}

	// 已过时方法
	@RequestMapping("/findProductIds")
	public void uploadImages(HttpServletRequest request,
			HttpServletResponse response) {
		String date = "{";
		date += "\"priceList\":[";

		String[] productIds = request.getParameterValues("productIds");
		try {
			String callback = request.getParameter("callback");
			for (int x = 0; x < productIds.length; x++) {
				if (x != 0)
					date += ",";
				date += "{\"productId\":'" + productIds[x] + "',\"price\":";
				SearchRequest searchRequest = new SearchRequest();
				searchRequest.setPid("" + productIds[x]);
				searchRequest.setB2C(true);
				SearchResponse searchResponse = searchService
						.search(searchRequest);
				if (null != searchResponse) {
					List<PlainProduct> items = searchResponse.getItems();
					try {
						BigDecimal unit_price = items.get(0).getUnit_price();
						DecimalFormat df = new DecimalFormat("0.00");
						String format = df.format(unit_price.doubleValue());
						date += "'" + format + "'";
					} catch (Exception e) {
						logger.info(e.getMessage(), e);
						date += "'0'";
					}

				} else {
					date += "";
				}
				date += "}";
			}

			date += "]}";
			response.getWriter().write(callback + "(" + date + ")");
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	// 返回JSON参数：productID、price、domestic_price
	@RequestMapping("/findProductAttr")
	public void findPidAttr(HttpServletRequest request,
			HttpServletResponse response) {
		String date = "{";
		date += "\"priceList\":[";

		String[] productIds = request.getParameterValues("productIds");
		try {
			String callback = request.getParameter("callback");
			for (int x = 0; x < productIds.length; x++) {
				if (x != 0)
					date += ",";
				date += "{\"productId\":'" + productIds[x] + "',\"price\":";
				SearchRequest searchRequest = new SearchRequest();
				searchRequest.setPid("" + productIds[x]);
				searchRequest.setB2C(true);
				SearchResponse searchResponse = searchService
						.search(searchRequest);
				if (null != searchResponse) {
					List<PlainProduct> items = searchResponse.getItems();
					try {
						BigDecimal unit_price = items.get(0).getUnit_price();
						DecimalFormat df = new DecimalFormat("0.00");
						String format = df.format(unit_price.doubleValue());
						date += "'" + format + "'";
					} catch (Exception e) {
						logger.info(e.getMessage(), e);
						date += "'0.00'";
					}
					date += ",\"domesticPrice\":";
					try {
						BigDecimal domestic_price = items.get(0)
								.getDomestic_price();
						DecimalFormat df1 = new DecimalFormat("0.00");
						String format1 = df1.format(domestic_price
								.doubleValue());
						date += "'" + format1 + "'";
					} catch (Exception e) {
						logger.info(e.getMessage(), e);
						date += "'0.00'";
					}
				} else {
					date += "";
				}
				date += "}";
			}

			date += "]}";
			response.getWriter().write(callback + "(" + date + ")");
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	
	// 返回JSON参数：productID、price、domestic_price  
	// 对首页的价格进行赋值
	@RequestMapping("/findNewProductAttr")
	public void findNewPidAttr(HttpServletRequest request,
			HttpServletResponse response) {
		String date = "{";
		date += "\"priceList\":[";
		
		String[] productIds = request.getParameterValues("productIds");
		/**
		         渠道 type
		  	1L; pc渠道
	        2L; //android渠道
	        3L;//wap渠道
	        4L; //ios渠道
	        8L;//悦ME渠道
	        9L;//中行北分
	        10L;//翼支付
	         ;//掌聚
		 */
		String type = request.getParameter("type"); //渠道type
		
		
		try {
			String callback = request.getParameter("callback");
			for (int x = 0; x < productIds.length; x++) {
				if (x != 0)
					date += ",";
				date += "{\"productId\":'" + productIds[x] + "',\"price\":";
				SearchRequest searchRequest = new SearchRequest();
				searchRequest.setPid("" + productIds[x]);
				searchRequest.setB2C(true);
				searchRequest.setEntryType(type);
				SearchResponse searchResponse = searchService.search(searchRequest);
				if (null != searchResponse) {
					List<PlainProduct> items = searchResponse.getItems();
//					try {
						List<Long> productids = new ArrayList<Long>();
						productids.add(Long.parseLong(productIds[x]));
						if(null!=items && !items.isEmpty() && null!=items.get(0)){
							BigDecimal promation_price = new BigDecimal("0");
							//BigDecimal domestic_price = new BigDecimal("0");
							if(items.get(0).isBoolPromotion()){
								promation_price = items.get(0).getPromotion_price();
								//domestic_price = items.get(0).getUnit_price();
							}else{
								promation_price = items.get(0).getUnit_price();
							}
							
							DecimalFormat df = new DecimalFormat("0.00");
							String format = df.format(promation_price.doubleValue());
							date += "'" + format + "'";
							
							date += ",\"imgUrl\":" + "'" + this.picUrl2 + items.get(0).getImageurl() + "'";
							String pname = items.get(0).getPname();
							pname = pname.replaceAll("'", "＇");
							date += ",\"pname\":" + "'" + pname + "'";
						}
//					} catch (Exception e) {
//						logger.info(e.getMessage(), e);
//						// date += "'0.00'";
//					}
					date += ",\"domesticPrice\":";
					try {					
						BigDecimal domestic_price = items.get(0).getDomestic_price();
						DecimalFormat df1 = new DecimalFormat("0.00");
						String format1 = df1.format(domestic_price.doubleValue());
						date += "'" + format1 + "'";
					} catch (Exception e) {
						logger.info("cms页面 价格进行赋值 findNewProductAttr -> 商品 id: "+productIds[x] +" 的价格无法 从搜索中无法获取！");
						date += "'0.00'";
					}
					
				} else {
					date += "";
				}
				date += "}";
			}
			
			date += "]}";
			response.getWriter().write(callback + "(" + date + ")");
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	// 返回JSON参数：productID、price、domestic_price
	@RequestMapping(value="/findProductInfos",produces = "application/jsonp; charset=utf-8")
	@ResponseBody
	public String findProductInfos(HttpServletRequest request, HttpServletResponse response,String callback) {
		
		String productId = request.getParameter("productIds");
		String type = request.getParameter("type"); //渠道type
		
		logger.info("--->>productId:["+productId+"],type:[]"+type);
		
		String entryType = "";
		if(StringUtils.isBlank(type)){
			entryType = getEntryType(request);
		}else{
			entryType = type;
		}
		
		String[] productIds = productId.split(",");
		try {
			List<PlainProduct> list = new ArrayList<PlainProduct>();
			for (int x = 0; x < productIds.length; x++) {
				SearchRequest searchRequest = new SearchRequest();
				searchRequest.setPid(productIds[x]);
				searchRequest.setB2C(true);
				searchRequest.setEntryType(entryType);
				SearchResponse searchResponse = searchService.search(searchRequest);
				if (null != searchResponse) {
					List<PlainProduct> items = searchResponse.getItems();
					if(null != items && items.size() > 0){
						list.add(items.get(0));
					}
				}
			}
			String items = JSON.json(list).toString();
			logger.info("--------------------------------items:"+items);
			if(null != callback && !"".equals(callback)){
				//response.getWriter().write(callback + "(" + items + ")");
				return callback + "(" + items + ")";
			}else{
				//response.getWriter().write(items);
				return items;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "";
		}
	}
	
	// 返回JSON参数：productID、price、domestic_price
		@RequestMapping(value="/findProductInfosB2bWap",produces = "application/jsonp; charset=utf-8")
		@ResponseBody
		public String findProductInfosB2bWap(HttpServletRequest request, HttpServletResponse response,String callback) {
			
			String productId = request.getParameter("productIds");
			String type = request.getParameter("type"); //渠道type
			
			logger.info("--->>productId:["+productId+"],type:[]"+type);
			
			String entryType = "";
			if(StringUtils.isBlank(type)){
				entryType = type;
			}else{
				entryType = "13";
			}
			
			String[] productIds = productId.split(",");
			try {
				List<PlainProduct> list = new ArrayList<PlainProduct>();
				for (int x = 0; x < productIds.length; x++) {
					SearchRequest searchRequest = new SearchRequest();
					searchRequest.setPid(productIds[x]);
					searchRequest.setB2C(false);
					searchRequest.setEntryType(entryType);
					SearchResponse searchResponse = searchService.search(searchRequest);
					if (null != searchResponse) {
						List<PlainProduct> items = searchResponse.getItems();
						if(null != items && items.size() > 0){
							list.add(items.get(0));
						}
					}
				}
				String items = JSON.json(list).toString();
				logger.info("--------------------------------items:"+items);
				if(null != callback && !"".equals(callback)){
					//response.getWriter().write(callback + "(" + items + ")");
					return callback + "(" + items + ")";
				}else{
					//response.getWriter().write(items);
					return items;
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return "";
			}
		}	
	
	
	// 返回JSON参数：key
	@RequestMapping(value = "/findProductInfosToJson", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String findProductInfosToJson(HttpServletRequest request, HttpServletResponse response, String callback) {

		String key = request.getParameter("key");
		String type = request.getParameter("type"); // 渠道type
	
		/*Properties pro = new Properties("/cmsblockdataconfig.properties");
		
		
		String cmsKey = bundle.getString(key);*/
		CmsBlockDataConfigUtil findCmsBlockId = 
				CmsBlockDataConfigUtil.getInstance();
		String cmsKey = findCmsBlockId.findCmsBlockId(key);
		logger.info("--->>key:[" + key + "],cmsKey:["+cmsKey+"]type:[]" + type);
		Response response1 = new Response();
		response1.getResult().setResult("0");
		response1.getResult().setMessage("未找到商品");
		String resultFalse = null;
		if(cmsKey==null || "".equals(cmsKey)){
			try {
				resultFalse = JSON.json(response1).toString();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			logger.info("------------------------------配置文件cmsblockdataconfig中未找到对应key ["+key+"] 的值");
			return resultFalse;
		}
			
		
		//HttpClient获取id串
		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod("http://cms.zhongjumall.com/json/" + cmsKey + ".json");
		StringBuffer sb = new StringBuffer();
		try {
			client.executeMethod(method);
			// 打印服务器返回的状态
			logger.info(method.getStatusLine().toString());
			// 打印返回的信息
			//logger.info(method.getResponseBodyAsString());
			String jsonProductIds = method.getResponseBodyAsString();
			//判断返回是否json格式
			ScriptEngineManager sem = new ScriptEngineManager();
			ScriptEngine se = sem.getEngineByName("js");
			se.eval(jsonProductIds);//不是抛异常
			//正则取出id
			String regex = "\\d*";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(jsonProductIds);
			while (m.find()) {
				if (!"".equals(m.group())) {
					sb.append(",");
					sb.append(m.group());
				}
			}
			
			String productId = sb.substring(1).toString();
			String entryType = "";
			if (StringUtils.isBlank(type)) {
				entryType = getEntryType(request);
			} else {
				entryType = type;
			}

			String[] productIds = productId.split(",");
			//根据id查询放到list中
			List<PlainProduct> list = new ArrayList<PlainProduct>();
			for (int x = 0; x < productIds.length; x++) {
				SearchRequest searchRequest = new SearchRequest();
				searchRequest.setPid(productIds[x]);
				searchRequest.setB2C(true);
				searchRequest.setEntryType(entryType);
				SearchResponse searchResponse = searchService.search(searchRequest);
				if (null != searchResponse) {
					List<PlainProduct> items = searchResponse.getItems();
					if (null != items && items.size() > 0) {
						list.add(items.get(0));
					}
				}
			}
			if (list == null || list.size() == 0) {
				resultFalse = JSON.json(response1).toString();
				logger.info("--------------------------------result:" + resultFalse);
				return resultFalse;
			}
			response1.getResult().setResult("1");
			response1.getResult().setMessage("找到商品");
			response1.setData(list);
			String items = JSON.json(response1).toString();
			logger.info("--------------------------------items:" + items);
			return items;
		} catch (ScriptException e) {
			System.out.println("json格式有误");
			try {
				resultFalse = JSON.json(response1).toString();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			logger.info("--------------------------------result:" + resultFalse);
			return resultFalse;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "";
		} finally {
			method.releaseConnection();
		}
	}
		
		
	@RequestMapping(value="/findSkuCodeBySkuId",produces = "application/jsonp; charset=utf-8")
	@ResponseBody
	public String findSkuCodeBySkuId(HttpServletRequest request, HttpServletResponse response,String callback) {
		String str = request.getParameter("skuid");
		logger.info("------------------------------------------skuid = "+str);
		List<B2cPromotionSkuDto> b2cPromotionSkuDto = null;
		if(org.apache.commons.lang.StringUtils.isNotBlank(str)){
			List<Long> list = new ArrayList<Long>();
			list.add(Long.parseLong(str));
			try {
				b2cPromotionSkuDto = RemoteServiceSingleton.getInstance().getCustomerPromotionApi().findSkuDtosBySkuIds(list);
			} catch (Exception e) {
				logger.info("调用失败",e);
			}
			return  callback + "(" + b2cPromotionSkuDto.get(0).getPrice()+ ")";
		}
		return "";
	}
	
	@RequestMapping("/findCommodityProduct")
	public void findCommodityProduct(HttpServletRequest request, HttpServletResponse response) {
		logger.info("enter findCommodityProduct()");
		String[] productIds = request.getParameterValues("productIds");
		/**
		         渠道 type
		  	1L; pc渠道
	        2L; //android渠道
	        3L;//wap渠道
	        4L; //ios渠道
	        8L;//悦ME渠道
	        9L;//中行北分
	        10L;//翼支付
	         ;//掌聚
		 */
		String type = request.getParameter("type"); //渠道type
		
		try {
			List<CommodityProductVo> list = new ArrayList<CommodityProductVo>();
			String callback = request.getParameter("callback");
			for (int x = 0; x < productIds.length; x++) {
				SearchRequest searchRequest = new SearchRequest();
				searchRequest.setPid("" + productIds[x]);
				searchRequest.setB2C(true);
				searchRequest.setEntryType(type);
				SearchResponse searchResponse = searchService.search(searchRequest);
				if (null != searchResponse) {
					List<PlainProduct> items = searchResponse.getItems();
					if(null==items || items.isEmpty() || null==items.get(0)){
						continue;
					}
					
					CommodityProductVo cp = new CommodityProductVo();
					cp.setProductId(productIds[x]);
					cp.setPname(items.get(0).getPname());
					cp.setImgUrl(this.picUrl2 + items.get(0).getImageurl());
					
					BigDecimal promation_price = new BigDecimal("0");
					if(items.get(0).isBoolPromotion()){
						promation_price = items.get(0).getPromotion_price();
					}else{
						promation_price = items.get(0).getUnit_price();
					}
					
					DecimalFormat df = new DecimalFormat("0.00");
					String format = df.format(promation_price.doubleValue());
					cp.setPrice(format);
					
					try {
						BigDecimal domestic_price = items.get(0).getDomestic_price();
						DecimalFormat df1 = new DecimalFormat("0.00");
						String format1 = df1.format(domestic_price.doubleValue());
						cp.setDomesticPrice(format1);
					} catch (Exception e) {
						logger.info(e.getMessage(), e);
						cp.setDomesticPrice("'0.00'");
					}
					
					list.add(cp);
				} 
				else {
					logger.info("========== findCommodityProduct: search query no result ==============");
				}
			}
			response.getWriter().write(callback + "(" + JsonUtils.ObjectAsString(list) + ")");
		} catch (IOException e) {
			logger.info("====findCommodityProduct() error");
			logger.error(e.getMessage(), e);
		}
		logger.info("leave findCommodityProduct()");
	}
}
