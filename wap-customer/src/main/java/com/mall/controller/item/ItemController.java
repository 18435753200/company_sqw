package com.mall.controller.item;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.category.api.rpc.BaseDataServiceRpc;
import com.mall.comment.api.rpc.CommentService;
import com.mall.comment.dto.CommentRequst;
import com.mall.comment.dto.CommentResponse;
import com.mall.constant.CommonConstant;
import com.mall.controller.base.BaseController;
import com.mall.controller.impl.AddressControllerImpl;
import com.mall.customer.dto.HomeNumRecordDto;
import com.mall.customer.model.OneDividend;
import com.mall.customer.model.OneDividendRatio;
import com.mall.customer.model.User;
import com.mall.customer.order.api.rpc.CustomerCartService;
import com.mall.customer.order.api.rpc.CustomerOrderService;
import com.mall.customer.order.dto.CartGetCountRequest;
import com.mall.customer.order.po.LimitCount;
import com.mall.customer.service.DividendService;
import com.mall.customer.service.HomeNumRecordService;
import com.mall.dealer.product.api.DealerProductService;
import com.mall.dealer.product.api.pop.DealerPopProductService;
import com.mall.dealer.product.customer.api.CustomerProductApi;
import com.mall.dealer.product.dto.B2cProdAttrValShowDto;
import com.mall.dealer.product.dto.B2cProdSkuShowDto;
import com.mall.dealer.product.dto.B2cProductShowDTO;
import com.mall.dealer.product.dto.B2cProductShowNewDTO;
import com.mall.dealer.product.dto.ProdB2bStockSelectDto;
import com.mall.dealer.product.dto.ProductStockDto;
import com.mall.dealer.product.dto.SkuStockDto;
import com.mall.dealer.product.po.BuyLimit;
import com.mall.dealer.product.po.DealerProduct;
import com.mall.dealer.product.po.DealerProductPrice;
import com.mall.dealer.product.po.ProductTag;
import com.mall.dealer.product.po.ProductTags;
import com.mall.dealer.product.util.ProductStockUtil;
import com.mall.dealer.productTags.api.ProductTagsService;
import com.mall.platform.model.PlatformActivity;
import com.mall.platform.service.PlatformActivityService;
/*import com.mall.promotion.api.drools.RuleEngineApi;
import com.mall.promotion.dto.activity.ProductPromotionDto;
import com.mall.promotion.dto.activity.PromotionProductBySkuIDDto;
import com.mall.promotion.dto.activity.SkuGiftByProductDto;
import com.mall.promotion.dto.drools.PromotionInfo;
import com.mall.promotion.dto.drools.SkuConditonDto;
import com.mall.promotion.dto.ruleSkuDto.SkuConditionB2BDto;
import com.mall.promotion.utils.PromotionConstants;*/
import com.mall.stock.api.rpc.StockCustomerService;
import com.mall.stock.dto.StockDTO;
import com.mall.stock.dto.StockVO;
import com.mall.supplier.model.Supplier;
import com.mall.supplier.model.SupplierRegionSettings;
import com.mall.supplier.model.SupplierStore;
import com.mall.supplier.service.SupplierManagerService;
import com.mall.supplier.service.SupplierRegionService;
import com.mall.utils.Constants;
import com.mall.utils.CookieTool;
import com.mall.utils.CookieUtil;
import com.mall.utils.ProductUtil;
import com.mall.vo.Buy;
import com.mall.vo.FullReduction;
import com.mall.vo.Fullback;
import com.mall.vo.Gift;
import com.mall.vo.PriceDown;
import com.mall.vo.PromotionDto;
import com.mall.vo.WithIncreasing;
import com.mall.wap.proxy.RemoteServiceSingleton;

/**
 * 商品相关
 * 
 * @author yuanxiayang
 * 
 */
@Controller
@RequestMapping("/item")
public class ItemController extends BaseController {

	private static final Logger logger = Logger.getLogger(ItemController.class);

	public static final String ITEM_DETAIL = "item/detail";
	public static final String ITEM_NULL = "item/detail_null";
	public static final String ERROR_500 = "errorpage/error500";
	private static String DATE_FORMAT_yyyy_MM_dd_hh_mm_ss = "yyyy-MM-dd HH:mm:ss";
	private static final int PAGESIZE = 5;
	@Autowired
	private CustomerCartService customerCartService;

	@Autowired
	private AddressControllerImpl addressControllerImpl;

	@Autowired
	private BaseDataServiceRpc baseDataServiceRpc;

	@Autowired
	private SupplierManagerService supplierManagerService;

	@Autowired
	private DealerPopProductService dealerPopProductService;

	@Autowired
	private DividendService dividendService;

	@Autowired
	private SupplierRegionService supplierRegionService;

	@Autowired
	private ProductTagsService productTagsService;

	@Autowired
	private CustomerOrderService customerOrderService;
	
	@Autowired
	private DealerProductService dealerProductService;
	
	@Autowired
	 private HomeNumRecordService homeNumRecordService;
	

	/**
	 * 进入itemDetail 页面
	 * 
	 * @return
	 */
	@RequestMapping("/get/{Pid}")
	public String getItemDetailByPID(Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response,
			Model model, @PathVariable Long Pid) {

		logger.info("---------------find item detail start by Pid = " + Pid);

		String endTime = request.getParameter("endtime");
		// model.addAttribute("endTime", endTime);
		model.addAttribute("serverTimes", new Date().getTime() / 1000);
		logger.info("---------------" + new Date().getTime() / 1000);
		// 获取商品信息
		CustomerProductApi customerProductApi = RemoteServiceSingleton
				.getInstance().getCustomerProductApi();
		B2cProductShowNewDTO findMarkB2cProductByProdId = new B2cProductShowNewDTO();

		try {
			/**
			 * 更新地方 findMarkB2cProductByProdId = customerProductApi
			 * .findB2cProductByProdId(Pid);
			 * 
			 */
			findMarkB2cProductByProdId = ProductUtil.getProductNew(
					customerProductApi, Pid);
			if (findMarkB2cProductByProdId.getB2cIstate() == 0) {
				// 商品下架
				return ITEM_NULL;
			}
			if (findMarkB2cProductByProdId.getSupply() == 51) {
				SupplierStore supplierStore = dealerPopProductService
						.getSupplerStoreByProductId(Pid);
				Supplier supplier = supplierManagerService
						.findSupplier(new Long(supplierStore.getSupplierId()));

				List<SupplierRegionSettings> supplierRegionSettings = supplierRegionService
						.getAllRegionSettings();
				if (supplierRegionSettings != null
						&& supplierRegionSettings.size() > 0) {
					for (SupplierRegionSettings supplierRegionSetting : supplierRegionSettings) {
						if (String.valueOf(supplierRegionSetting.getRegionId())
								.equals(supplier.getCompanyQy())) {
							model.addAttribute("regionText",
									supplierRegionSetting.getRegionText());
						}
					}
				}

				model.addAttribute("supplierStore", supplierStore);
				model.addAttribute("supplier", supplier);
				//判断是不是家庭号发布的商品，5表示是家庭号商品，0普通2国内，6为幸福购商品       
				
				Integer	homeShangPin	=findMarkB2cProductByProdId.getProductType();
			
				model.addAttribute("homeShangPin",homeShangPin);
				
				Long homeUserId = supplier.getUserId();
				 //查询发布商品的用户是否购买家庭号6.7
	            Integer jiaTingStatus =0;//0 没购买  1过期 2正常使用
				//判断发布该商品的家庭号状态0未注册或商家  1正在审核或未通过或者过期2冻结3正常使用
				
				int homeStates =0;
				try {
					if(homeUserId!=null){
						//查询发布商品的用户是否购买家庭号6.7 0 没购买  1过期 2正常使用
						jiaTingStatus=homeNumRecordService.selectRecordByUserid(homeUserId.intValue()).getStatus();
						if(jiaTingStatus==2){
							//判断发布该商品的家庭号状态0未注册或商家  1正在审核或未通过或者过期2冻结3正常使用
							homeStates = supplierManagerService.findSupplierStatusByUserId(homeUserId);
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				model.addAttribute("homeStates",homeStates);
				
				//判断是不是打激活标签的产品-激活区7.28
				Integer jiHuoStates=0;
				ProductTags limitContentOrNull = productTagsService.LimitContentOrNull(
						Pid, "jhb",4); 
				if(limitContentOrNull!=null){
					jiHuoStates=1;
				}
				
				
				model.addAttribute("jiHuoStates", jiHuoStates);
				
				
				//判断是不是全券区产品  homeShangPin=0&&xjzfblbq=0
				
				
				
				
				// 商家设置现金支付比例
				ProductTags xjzfbl = productTagsService.LimitContentOrNull(Pid,
						"xjzfbl", 5);
				int xjzfblbq = 0;
				if (null != xjzfbl) {
					xjzfblbq = 1;
				}
				model.addAttribute("xjzfblbq", xjzfblbq);
				//显示图标字符
				String tuBiao=null;
				if(jiHuoStates==1){
					tuBiao="激活";
				}else if(homeShangPin==5){
					tuBiao="易";
				}else if(homeShangPin==6){
					tuBiao="千品华冠";
				}else if(homeShangPin==0){
					tuBiao="普通商品";
				}
				
				model.addAttribute("tuBiao", tuBiao);
				
				
				//返券比例
				OneDividend moneyOneDividend = dividendService
						.findOneDivByType(
								Constants.SqwAccountRecordService.MONEY_PAY,
								Integer.parseInt(supplier.getCompanyQy()), Pid);
				OneDividend hqOneDividend = dividendService.findOneDivByType(
						Constants.SqwAccountRecordService.HQ_PAY,
						Integer.parseInt(supplier.getCompanyQy()), Pid);
				OneDividend moneyAndHqOneDividend = dividendService
						.findOneDivByType(
								Constants.SqwAccountRecordService.HQ_MONEY_PAY,
								Integer.parseInt(supplier.getCompanyQy()), Pid);
				model.addAttribute("moneyOneDividend", moneyOneDividend);
				model.addAttribute("hqOneDividend", hqOneDividend);
				model.addAttribute("moneyAndHqOneDividend",
						moneyAndHqOneDividend);
			} else {
				model.addAttribute("supplierStore", null);
			}

		} catch (Exception e) {
			logger.error(e);
			// 商品不存在
			return ITEM_NULL;
		}

		// 星级限定

		// 调用service查询商品限定星级
		ProductTag p = new ProductTag();
		p.setProductId(Pid);
		p.setTagType(1);
		List<ProductTags> starlimit = productTagsService
				.selectByProductIdAndTagType(p);

		String starTip = "";
		String tag1 = "";
		String tag2 = "";
		String tag3 = "";
		String tag4 = "";
		String tag5 = "";
		if (starlimit != null && starlimit.size() > 0) {
			for (ProductTags ptag : starlimit) {
				// starTip+=ptag.getTagName().substring(0, 2)+" ";
				String tagCode = ptag.getTagCode();
				if(tagCode.equals("tag_star_0")){
					tag1 = "未激活";
				}else if (tagCode.equals("tag_star_1")) {
					tag1 = " 一星";
				} else if (tagCode.equals("tag_star_2")) {
					tag2 = " 二星";
				} else if (tagCode.equals("tag_star_3")) {
					tag3 = " 三星";
				} else if (tagCode.equals("tag_star_4")) {
					tag4 = " 四星";
				} else if (tagCode.equals("tag_star_5")) {
					tag5 = " 五星";
				}
			}
		}
		starTip = tag1 + tag2 + tag3 + tag4 + tag5 + "";
		model.addAttribute("starTip", starTip);

		// 限购
		// 调用service查询限购时间和数量

		User user = super.getCurrentUser(request);
		Integer limitnum = -1;
		BuyLimit buyLimitContent = productTagsService
				.LimitContentByProudctId(Pid);
		if (buyLimitContent != null) {
			Date startTime = buyLimitContent.getStartTime();
			Date endyTime = buyLimitContent.getEndTime();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			Integer num = buyLimitContent.getMaxNumber();
			String limitWords = buyLimitContent.getLimitWords();
			// 调用接口查询已经购买数量
			if (null != user) {
				LimitCount limitCount = new LimitCount();
				limitCount.setUserId(user.getUserId());
				limitCount.setPid(Pid);
				limitCount.setStartTime(startTime);
				limitCount.setEndTime(endyTime);

				// Integer num_already =0;
				Integer num_already = customerOrderService
						.limitBuyCount(limitCount);
				if (num_already == null) {
					num_already = 0;
				}
				System.out.println(num_already);

				// 限购数量 -查询已经购买数量>0
				limitnum = num - num_already;
				if (limitnum < 0) {
					limitnum = 0;
				}
				model.addAttribute("startTime", df.format(startTime));
				model.addAttribute("endyTime", df.format(endyTime));
				model.addAttribute("num", num);
				model.addAttribute("limitWords", limitWords);
			}
		}
		model.addAttribute("limitnum", limitnum);
		logger.info("---------------product type = "
				+ findMarkB2cProductByProdId.getSupply().toString()
				+ ";  product status = "
				+ findMarkB2cProductByProdId.getProdType());
		// 1表示上架状态，调用库存，0表示未上架，可以查看商品详情
		findMarkB2cProductByProdId = this
				.findSkuListNew(findMarkB2cProductByProdId);// 获取库存信息

		model.addAttribute("picUrl1", picUrl1);
		model.addAttribute("picUrl3", picUrl3);

		// 获取当前购物车内的商品数量
		Integer cartTotalQty = getCartTotalQty(request);
		model.addAttribute("cartTotalQty", cartTotalQty);
		logger.info("---------------find totalCartCount=" + cartTotalQty);
		// 获取促销信息
		// getPromotionInfo(findMarkB2cProductByProdId,model);
		List<PromotionDto> promotion = this.getPromotionDto(
				findMarkB2cProductByProdId, request, model);
//				List<PromotionDto> promotion =null;
		model.addAttribute("promotion", promotion);

		if (null == endTime) {
			// 商品ID获取活动id
			PlatformActivityService platformActivityService = RemoteServiceSingleton
					.getInstance().getPlatformActivityService();

			List<PlatformActivity> lActivities = platformActivityService
					.findProductListByPid(String.valueOf(Pid));

			// 匹配“特卖”的关键字,
			if (null != lActivities && lActivities.size() > 0) {
				PlatformActivity platformActivity = lActivities.get(0);
				Long times = platformActivity.getEndTime().getTime() / 1000;
				endTime = String.valueOf(times);
				if (times < new Date().getTime() / 1000) {
					endTime = null;
				}
				logger.info("" + endTime);
			}
		}
		model.addAttribute("proDetail", findMarkB2cProductByProdId);
		model.addAttribute("endTime", endTime);
		// 处理发货省市区信息
		Cookie cookie = CookieTool.getCookie(request, Constants.AREAIDKEY);
		String value = null;
		try {
			if (null != cookie) {
				value = URLDecoder.decode(cookie.getValue());
				String[] strings = value.split(",");
				String areaName = getProvinceCityCountyName(
						Long.parseLong(strings[0]), Long.parseLong(strings[1]),
						Long.parseLong(strings[2]));
				value = strings[0] + "," + strings[1] + "," + strings[2] + ","
						+ areaName;
				if (value.contains("null")) {
					value = Constants.DEFAULTAREAIDVALUE;
				}
			} else {
				value = Constants.DEFAULTAREAIDVALUE;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		CookieTool.setCookie(response, "iptv", request.getParameter("channel"),
				72 * 60 * 60);

		// 写入悦me盒子来源cookies
		String yueCh = request.getParameter("channel");
		if (StringUtils.isNotBlank(yueCh)
				&& yueCh.trim().equals(Constants.YUEME_COOKIES_VALUE)) {
			CookieTool.setCookie(response, Constants.YUEME_COOKIES_KEY, yueCh,
					-1);
		}

		// 获得用户注册渠道
		String registCh = request.getParameter("c_o");
		if (StringUtils.isNotBlank(registCh)) {
			CookieTool.setCookie(response, Constants.USERORIGIN_COOKIES_KEY,
					registCh, -1);
		}

		// 订单来源渠道 1.逸翠园家年华(ycyjnh) 2.悦me（yueme）
		String orderCh = request.getParameter("c_r");
		if (StringUtils.isNotBlank(orderCh)) {
			CookieTool.setCookie(response, Constants.ORDERORIGIN_COOKIES_KEY,
					orderCh, -1);
		}

		// User user = super.getCurrentUser(request);
		if (null != user) {
			model.addAttribute("username", user.getUserName());
		}

		model.addAttribute("areaIdKey", value);

		// 获取评论信息
		CommentRequst commentRequst = new CommentRequst();
		commentRequst.setCommentType(0);
		commentRequst.setPageNo(1);
		commentRequst.setPid(Pid);
		commentRequst.setPageSize(PAGESIZE);
		commentRequst.setPlatformType("1");
		CommentService commentApi = RemoteServiceSingleton.getInstance()
				.getCommentApi();
		CommentResponse result = commentApi.findCommentsByPid(commentRequst);
		// JSONArray results = JSONArray.fromObject(result);
		model.addAttribute("comments", result);

		logger.info("---------------find item detail success");

		return ITEM_DETAIL;
	}

	private B2cProductShowDTO findSkuList(B2cProductShowDTO product) {
		logger.info("---------------get product stock start");
		List<Long> skuIds = new ArrayList<Long>();
		// 封装skuIDs
		List<B2cProdAttrValShowDto> prodAttrVals = product.getShowProdAttr()
				.getProdAttrVals();
		List<B2cProdSkuShowDto> skuShowDtos = new ArrayList<B2cProdSkuShowDto>();
		for (B2cProdAttrValShowDto dto : prodAttrVals) {
			List<B2cProdSkuShowDto> list = product.getSkuShowMap().get(
					dto.getAttrValId());
			for (B2cProdSkuShowDto skuShowSto : list) {
				Long skuId = Long.parseLong(skuShowSto.getSkuId());
				if (!skuIds.contains(skuId))
					skuIds.add(skuId);
			}
			skuShowDtos.addAll(list);
		}
		product.setSkuShowList(skuShowDtos);

		// 库存调用
		if (product.getProdType() == (short) 1) {
			logger.info("B2C库存获取");
			List<StockDTO> stockDtos = new ArrayList<StockDTO>();
			for (Long skuId : skuIds) {
				StockDTO skuDto = new StockDTO();
				skuDto.setSkuId(skuId);
				// 封装B2C商品类型 supply取值：1表示国内发货，11表示海外直邮，12表示国内保税区发货
				skuDto.setSupplyType(product.getSupply() + "");
				stockDtos.add(skuDto);
			}
			// 组织sku对应库存信息
			Map<Long, Long> skuStockNumMap = new HashMap<Long, Long>();
			StockVO allStock = RemoteServiceSingleton.getInstance()
					.getStockCustomerService().getAllStock(stockDtos);
			Map<Long, StockDTO> skuStockMap = allStock.getSkuStockMap();
			for (Long skuId : skuIds) {
				skuStockNumMap.put(skuId,
						skuStockMap.get(skuId).getSpotQty() + 0l);
			}

			for (B2cProdAttrValShowDto dto : prodAttrVals) {
				List<B2cProdSkuShowDto> list = product.getSkuShowMap().get(
						dto.getAttrValId());
				for (B2cProdSkuShowDto skuShowSto : list) {
					// 设置库存数据
					Long skuId = Long.parseLong(skuShowSto.getSkuId());
					skuShowSto.setSkuQty(skuStockNumMap.get(skuId));
				}
			}
			// 对sku 显示进行排序
			Map<Long, List<B2cProdSkuShowDto>> map = product.getSkuShowMap();
			Map<Long, List<B2cProdSkuShowDto>> newMap = new LinkedHashMap<Long, List<B2cProdSkuShowDto>>();
			Long[] ids = new Long[map.size()];
			Long[] qtys = new Long[map.size()];
			int i = 0;
			for (Long key : map.keySet()) {
				ids[i] = key;
				qtys[i] = 0L;
				List<B2cProdSkuShowDto> list = map.get(key);
				if (list.get(0).getSkuQty() == 0 && list.size() > 0) {
					B2cProdSkuShowDto temp = null;
					int index2 = 0;
					for (int k = 0; k < list.size(); k++) {
						if (list.get(k).getSkuQty() > 0) {
							temp = list.get(k);
							index2 = k;
						}
					}
					if (temp != null) {
						list.remove(index2);
						list.add(0, temp);
					}
				}
				qtys[i] = list.get(0).getSkuQty();
				i++;
			}
			int index = 0;
			Long firstId = ids[0];
			if (qtys[0] == 0L && map.size() > 1) {
				for (int j = 0; j < qtys.length; j++) {
					if (qtys[j] > 0L) {
						index = j;
					}
				}
				if (index > 0) {
					firstId = ids[index];
					ids[index] = ids[0];
					ids[0] = firstId;
				}
				for (Long key : ids) {
					newMap.put(key, map.get(key));
				}
				product.setSkuShowMap(newMap);

			}
			List<B2cProdAttrValShowDto> prodAttrs = product.getShowProdAttr()
					.getProdAttrVals();
			List<B2cProdAttrValShowDto> newProdAttrs = new ArrayList<B2cProdAttrValShowDto>();
			for (Long id : ids) {
				for (B2cProdAttrValShowDto dto : prodAttrs) {
					if (dto.getAttrValId().equals(id)) {
						newProdAttrs.add(dto);
					}
				}
			}
			product.getShowProdAttr().setProdAttrVals(newProdAttrs);

			// List<B2cProdSkuShowDto> list =
			// product.getSkuShowMap().get(dto.getAttrValId());
			// B2cProdSkuShowDto skuShowDtoFirst=list.get(0);
			// if(skuShowDtoFirst.getSkuQty()==0&&list.size()>1){
			// B2cProdSkuShowDto temp=null;
			// int index=0;
			// for(int i=0;i<list.size();i++) {
			// B2cProdSkuShowDto ssd=list.get(i);
			// if (ssd.getSkuQty() > 0) {
			// temp = ssd;
			// index=i;
			// }
			// }
			// if(temp!=null){
			// list.remove(temp);
			// list.add(0,temp);
			// }
			// }

		} else {
			logger.info("商品下架不获取B2C库存信息");
		}
		logger.info("---------------get product stock success");
		return product;
	}

	private B2cProductShowNewDTO findSkuListNew(B2cProductShowNewDTO product) {
		logger.info("---------------get product stock start");
		List<Long> skuIds = new ArrayList<Long>();
		// 封装skuIDs
		List<B2cProdAttrValShowDto> prodAttrVals = product.getShowProdAttr()
				.getProdAttrVals();
		List<B2cProdSkuShowDto> skuShowDtos = new ArrayList<B2cProdSkuShowDto>();
		for (B2cProdAttrValShowDto dto : prodAttrVals) {
			List<B2cProdSkuShowDto> list = product.getSkuShowMap().get(
					dto.getAttrValId());
			for (B2cProdSkuShowDto skuShowSto : list) {
				Long skuId = Long.parseLong(skuShowSto.getSkuId());
				if (!skuIds.contains(skuId))
					skuIds.add(skuId);
			}
			skuShowDtos.addAll(list);
		}
		product.setSkuShowList(skuShowDtos);

		// 库存调用
		if (product.getProdType() == (short) 1) {
			logger.info("B2C库存获取");
			List<StockDTO> stockDtos = new ArrayList<StockDTO>();
			for (Long skuId : skuIds) {
				StockDTO skuDto = new StockDTO();
				skuDto.setSkuId(skuId);
				// 封装B2C商品类型 supply取值：1表示国内发货，11表示海外直邮，12表示国内保税区发货
				skuDto.setSupplyType(product.getSupply() + "");
				stockDtos.add(skuDto);
			}
			// 组织sku对应库存信息
			Map<Long, Long> skuStockNumMap = new HashMap<Long, Long>();
			StockVO allStock = RemoteServiceSingleton.getInstance()
					.getStockCustomerService().getAllStock(stockDtos);
			Map<Long, StockDTO> skuStockMap = allStock.getSkuStockMap();
			for (Long skuId : skuIds) {
				skuStockNumMap.put(skuId,
						skuStockMap.get(skuId).getSpotQty() + 0l);
			}

			for (B2cProdAttrValShowDto dto : prodAttrVals) {
				List<B2cProdSkuShowDto> list = product.getSkuShowMap().get(
						dto.getAttrValId());
				for (B2cProdSkuShowDto skuShowSto : list) {
					// 设置库存数据
					Long skuId = Long.parseLong(skuShowSto.getSkuId());
					skuShowSto.setSkuQty(skuStockNumMap.get(skuId));
				}
			}
			// 对sku 显示进行排序
			Map<Long, List<B2cProdSkuShowDto>> map = product.getSkuShowMap();
			Map<Long, List<B2cProdSkuShowDto>> newMap = new LinkedHashMap<Long, List<B2cProdSkuShowDto>>();
			Long[] ids = new Long[map.size()];
			Long[] qtys = new Long[map.size()];
			int i = 0;
			for (Long key : map.keySet()) {
				ids[i] = key;
				qtys[i] = 0L;
				List<B2cProdSkuShowDto> list = map.get(key);
				if (list.get(0).getSkuQty() == 0 && list.size() > 0) {
					B2cProdSkuShowDto temp = null;
					int index2 = 0;
					for (int k = 0; k < list.size(); k++) {
						if (list.get(k).getSkuQty() > 0) {
							temp = list.get(k);
							index2 = k;
						}
					}
					if (temp != null) {
						list.remove(index2);
						list.add(0, temp);
					}
				}
				qtys[i] = list.get(0).getSkuQty();
				i++;
			}
			int index = 0;
			Long firstId = ids[0];
			if (qtys[0] == 0L && map.size() > 1) {
				for (int j = 0; j < qtys.length; j++) {
					if (qtys[j] > 0L) {
						index = j;
					}
				}
				if (index > 0) {
					firstId = ids[index];
					ids[index] = ids[0];
					ids[0] = firstId;
				}
				for (Long key : ids) {
					newMap.put(key, map.get(key));
				}
				product.setSkuShowMap(newMap);

			}
			List<B2cProdAttrValShowDto> prodAttrs = product.getShowProdAttr()
					.getProdAttrVals();
			List<B2cProdAttrValShowDto> newProdAttrs = new ArrayList<B2cProdAttrValShowDto>();
			for (Long id : ids) {
				for (B2cProdAttrValShowDto dto : prodAttrs) {
					if (dto.getAttrValId().equals(id)) {
						newProdAttrs.add(dto);
					}
				}
			}
			product.getShowProdAttr().setProdAttrVals(newProdAttrs);

			// List<B2cProdSkuShowDto> list =
			// product.getSkuShowMap().get(dto.getAttrValId());
			// B2cProdSkuShowDto skuShowDtoFirst=list.get(0);
			// if(skuShowDtoFirst.getSkuQty()==0&&list.size()>1){
			// B2cProdSkuShowDto temp=null;
			// int index=0;
			// for(int i=0;i<list.size();i++) {
			// B2cProdSkuShowDto ssd=list.get(i);
			// if (ssd.getSkuQty() > 0) {
			// temp = ssd;
			// index=i;
			// }
			// }
			// if(temp!=null){
			// list.remove(temp);
			// list.add(0,temp);
			// }
			// }

		} else {
			logger.info("商品下架不获取B2C库存信息");
		}
		logger.info("---------------get product stock success");
		return product;
	}

	/**
	 * 获取促销信息
	 * 
	 * @param findMarkB2cProductByProdId
	 * @param model
	 */
	/*private void getPromotionInfo(B2cProductShowDTO findMarkB2cProductByProdId,
			Model model) {
		// 获取sku集合
		Map<Long, List<B2cProdSkuShowDto>> skuShowMap = findMarkB2cProductByProdId
				.getSkuShowMap();
		// 自定义list,用于存放sku的集合
		List<SkuConditonDto> skuConditonDtos = new ArrayList<SkuConditonDto>();
		// 获取sku集合
		if (null != skuShowMap && skuShowMap.size() > 0) {
			for (List<B2cProdSkuShowDto> list : skuShowMap.values()) {
				for (B2cProdSkuShowDto b2cProdSkuShowDto : list) {
					// 获取skuid
					Long skuId = Long.parseLong(b2cProdSkuShowDto.getSkuId());
					SkuConditonDto skuConditonDto = new SkuConditonDto();
					skuConditonDto.setSkuId(skuId);
					skuConditonDtos.add(skuConditonDto);
				}
			}
		}
		// 调用促销获取促销信息
		RuleEngineApi ruleEngineApi = RemoteServiceSingleton.getInstance()
				.getRuleEngineApi();
		Map<Long, List<PromotionInfo>> promotionInfoBySKuProductInfo = ruleEngineApi
				.getPromotionInfoBySKuProductInfo(skuConditonDtos);
		model.addAttribute("promotionInfoBySKuProductInfo",
				promotionInfoBySKuProductInfo);
	}
*/
	public static String getyyyy_MM_ddDateValue(Date date) {
		return new SimpleDateFormat(DATE_FORMAT_yyyy_MM_dd_hh_mm_ss)
				.format(date);
	}

	/**
	 * 促销信息获取 会更改sku单价信息
	 * 
	 * @param product
	 * @return
	 */
	private List<PromotionDto> getPromotionDto(B2cProductShowDTO product,
			HttpServletRequest request, Model model) {
//		List<SkuConditionB2BDto> listDto = new ArrayList<SkuConditionB2BDto>();

		Long userGade = 4l;// 会员等级
		String categoryId = product.getCatePubId();// 类目ID
		String brandId = product.getBrandId();// 　品牌ID
		Short supply = product.getSupply();// 渠道

		// 访问方式
//		Long accessMode = PromotionConstants.ACCESSOD_WAP;
		List<B2cProdSkuShowDto> skuShowList = product.getSkuShowList();
//		for (B2cProdSkuShowDto dto : skuShowList) {
//			listDto.add(new SkuConditionB2BDto(Long.parseLong(dto.getSkuId()),
//					categoryId, brandId, userGade, accessMode, supply, 3L));
//		}
		// 促销接口 增加用户ID
		User user = getCurrentUser(request);
		Long userIds;
		if (null != user) {
			userIds = user.getUserId();
		} else {
			userIds = 0l;
		}
		// 调用促销服务
//		List<ProductPromotionDto> productPromotionInfoByB2C = RemoteServiceSingleton
//				.getInstance().getProductByPromotionInfoService()
//				.getProductPromotionInfoByB2C(listDto, userIds);
		// 日期
		String date = "";// getyyyy_MM_ddDateValue(new Date());
		// 促销对象集合
		List<PromotionDto> list = new ArrayList<PromotionDto>();
		Map<Long, BigDecimal> map_sku = new HashMap<Long, BigDecimal>();
//		for (ProductPromotionDto b2cDto : productPromotionInfoByB2C) {
//			PromotionDto promotionDto = new PromotionDto();
//
//			Long skuId = b2cDto.getSkuid();// skuId
//			promotionDto.setSkuId(skuId + "");
//			promotionDto.setNewDate(date);
//			System.out.println("skuId===" + skuId + "=================="
//					+ b2cDto.getStraightDownPrice());
//			map_sku.put(skuId, b2cDto.getStraightDownPrice());
//			List<PromotionProductBySkuIDDto> skuPromotionList = b2cDto
//					.getSkuPromotionList();
//			if (skuPromotionList != null && skuPromotionList.size() > 0) {
//				for (PromotionProductBySkuIDDto skuIDDto : skuPromotionList) {
//
//					*//**
//					 * 满返
//					 *//*
//					List<Fullback> fullbacks = promotionDto.getFullbacks();//
//					*//**
//					 * 满减
//					 *//*
//					List<FullReduction> fullReductions = promotionDto
//							.getFullReductions();//
//					*//**
//					 * 直降
//					 *//*
//					List<PriceDown> priceDowns = promotionDto.getPriceDowns();//
//					*//**
//					 * 满增
//					 *//*
//					List<WithIncreasing> withIncreasings = promotionDto
//							.getWithIncreasings();//
//					*//**
//					 * 买增
//					 *//*
//					List<Buy> buys = promotionDto.getBuys();//
//
//					switch (skuIDDto.getPromotionType().intValue()) {
//					// 促销升级 liuquan 商品详情活动分组展示
//					case 102:// 新满减
//						fullReductions.add(this.getFullReduction(skuIDDto));
//						break;
//					case 100:// 新满赠
//						withIncreasings.add(this.getWithIncreasing(skuIDDto));
//						break;
//					case 101:// 新买赠
//						buys.add(this.getBuy(skuIDDto));
//						break;
//					case 103:// 新限时直降
//						priceDowns.add(this.getPriceDown(skuIDDto));
//						break;
//					case 104:// N元N件
//						fullReductions.add(this.getFullReduction(skuIDDto));
//						break;
//					case 105:// N件N折
//						priceDowns.add(this.getPriceDown(skuIDDto));
//						break;
//
//					case 20:// 满减
//						fullReductions.add(this.getFullReduction(skuIDDto));
//						break;
//					case 21:// 满减
//						fullReductions.add(this.getFullReduction(skuIDDto));
//						break;
//					case 22:// 满返
//						fullbacks.add(this.getFullback(skuIDDto));
//						break;
//					case 23:// 满返
//						fullbacks.add(this.getFullback(skuIDDto));
//						break;
//					case 24:// 满增
//						withIncreasings.add(this.getWithIncreasing(skuIDDto));
//						break;
//					case 25:// 满增
//						withIncreasings.add(this.getWithIncreasing(skuIDDto));
//						break;
//					case 26:// 买增
//						buys.add(this.getBuy(skuIDDto));
//						break;
//					case 27:// 直降
//						priceDowns.add(this.getPriceDown(skuIDDto));
//						break;
//					case 28:// 直降
//						priceDowns.add(this.getPriceDown(skuIDDto));
//						break;
//					case 29:// 满增
//						withIncreasings.add(this.getWithIncreasing(skuIDDto));
//						break;
//					case 30:// 满返
//						fullbacks.add(this.getFullback(skuIDDto));
//						break;
//					default:
//						logger.info("error PromotionType"
//								+ skuIDDto.getPromotionType());
//						break;
//					}
//
//				}
//				list.add(promotionDto);
//			}
//		}

		// 暂时用于促销页面上
		Map<Long, List<B2cProdSkuShowDto>> map1 = product.getSkuShowMap();
		for (Map.Entry<Long, List<B2cProdSkuShowDto>> entry : map1.entrySet()) {
			List<B2cProdSkuShowDto> list_ = entry.getValue();
			for (B2cProdSkuShowDto b2cProdSkuShowDto : list_) {
				BigDecimal bigDecimal = map_sku.get(Long
						.parseLong(b2cProdSkuShowDto.getSkuId()));
				if (bigDecimal != null && bigDecimal.doubleValue() > 0) {
					b2cProdSkuShowDto
							.setPromotionPrice(""
									+ new BigDecimal(b2cProdSkuShowDto
											.getUnitPrice())
											.subtract(
													map_sku.get(Long
															.parseLong(b2cProdSkuShowDto
																	.getSkuId())))
											.doubleValue());
				} else {
					b2cProdSkuShowDto.setPromotionPrice(b2cProdSkuShowDto
							.getUnitPrice());
				}
			}
		}
		if (list.size() != skuShowList.size()) {
			Map<String, Boolean> map = new HashMap<String, Boolean>();
			if (list != null && list.size() > 0) {
				for (PromotionDto promotionDto : list) {
					map.put(promotionDto.getSkuId(), true);
				}
			}
			for (B2cProdSkuShowDto dto : skuShowList) {
				Boolean boolean1 = map.get(dto.getSkuId());
				if (boolean1 == null) {
					PromotionDto promotionDto = new PromotionDto();
					String skuId = dto.getSkuId();// skuId
					promotionDto.setSkuId(skuId);
					promotionDto.setNewDate(date);
					list.add(promotionDto);
				}
			}
		}
		return list;

	}

	/*public FullReduction getFullReduction(PromotionProductBySkuIDDto dto) {
		String date = getyyyy_MM_ddDateValue(dto.getEndTime());
		FullReduction fullReductions = new FullReduction(
				dto.getPromotionName(), date);
		return fullReductions;
	}*/

	/*public WithIncreasing getWithIncreasing(PromotionProductBySkuIDDto dto) {
		String date = getyyyy_MM_ddDateValue(dto.getEndTime());
		WithIncreasing withIncreasing = new WithIncreasing(
				dto.getPromotionName(), date);
		List<Gift> gifts = withIncreasing.getGifts();
		for (SkuGiftByProductDto giftByProductDto : dto.getGiftList()) {
			gifts.add(new Gift(giftByProductDto.getPid() + "", giftByProductDto
					.getSkuid() + "", giftByProductDto.getpName(),
					giftByProductDto.getSkuName(), giftByProductDto.getQty()));
		}
		return withIncreasing;
	}*/

	/*public PriceDown getPriceDown(PromotionProductBySkuIDDto dto) {
		String date = getyyyy_MM_ddDateValue(dto.getEndTime());
		PriceDown priceDown = new PriceDown(dto.getPromotionName(), date);
		return priceDown;
	}*/

/*	public Fullback getFullback(PromotionProductBySkuIDDto dto) {
		String date = getyyyy_MM_ddDateValue(dto.getEndTime());
		Fullback fullback = new Fullback(dto.getPromotionName(), date);
		return fullback;
	}*/

	/*public Buy getBuy(PromotionProductBySkuIDDto dto) {
		String date = getyyyy_MM_ddDateValue(dto.getEndTime());
		Buy buy = new Buy(dto.getPromotionName(), date);
		List<Gift> gifts = buy.getGifts();
		for (SkuGiftByProductDto giftByProductDto : dto.getGiftList()) {
			gifts.add(new Gift(giftByProductDto.getPid() + "", giftByProductDto
					.getSkuid() + "", giftByProductDto.getpName(),
					giftByProductDto.getSkuName(), giftByProductDto.getQty()));
		}
		return buy;
	}*/

	/*
	 * private List<PromotionDto> getPromotion(B2cProductShowDTO product,Model
	 * model){ logger.info("---------------get promotion info start");
	 * List<PromotionDto> list = new ArrayList<PromotionDto>(); //组织sku数据
	 * List<B2cProdSkuShowDto> skuShowList = product.getSkuShowList();
	 * List<SkuConditonDto> skuDtos = new ArrayList<SkuConditonDto>();
	 * for(B2cProdSkuShowDto dto : skuShowList){ SkuConditonDto skuDto = new
	 * SkuConditonDto(); skuDto.setSkuId(Long.parseLong(dto.getSkuId()));
	 * skuDto.setOriginalPrice(new BigDecimal(dto.getUnitPriceV()));
	 * skuDtos.add(skuDto); }
	 * 
	 * //获取促销信息 Map<Long, List<PromotionInfo>> promotionInfoBySKuProductInfo =
	 * RemoteServiceSingleton
	 * .getInstance().getRuleEngineApi().getPromotionInfoBySKuProductInfo
	 * (skuDtos); for(B2cProdSkuShowDto dto : skuShowList){ PromotionDto
	 * promotionDto = new PromotionDto(); promotionDto.setSkuId(dto.getSkuId());
	 * // 促销单品促销信息 for(PromotionInfo promotionInfo :
	 * promotionInfoBySKuProductInfo.get(Long.parseLong(dto.getSkuId()))){ //
	 * 促销类型 Integer promotionType = promotionInfo.getPromotionType(); // 促销信息描述
	 * String promotionName = promotionInfo.getPromotionName(); switch
	 * (promotionType) { case PromotionConstants.PROMOTION_BAOYOU:
	 * promotionDto.setFreeShipping(true);
	 * promotionDto.setFreeShippingReason(promotionName);
	 * promotionDto.setFreeShippingDate(null); break; case
	 * PromotionConstants.PROMOTION_ZHIJIANG: // 直降金额 BigDecimal
	 * straightDownPrice = promotionInfo.getStraightDownPrice();
	 * promotionDto.setPriceDownValus(straightDownPrice.doubleValue());
	 * promotionDto.setPriceDownReason(promotionName);
	 * promotionDto.setPriceDown(true); promotionDto.setPriceDownDate(new
	 * SimpleDateFormat
	 * ("yyyy-MM-dd HH:mm:ss").format(promotionInfo.getEndDate())); //
	 * sku单价直接进行操作 由于hashCode一致不需要更改其他数据
	 * model.addAttribute("skuPrice"+dto.getSkuId(), dto.getUnitPriceV());
	 * model.addAttribute("DownPrice", straightDownPrice.doubleValue());
	 * dto.setUnitPrice
	 * ((dto.getUnitPriceV()-straightDownPrice.doubleValue())+""); // 行邮税更改
	 * dto.setTar
	 * (DateUtil.findTarByTariff(dto.getUnitPrice(),product.getTariff()
	 * ).toString());
	 * logger.info("========== sku down price skuID:"+dto.getSkuId
	 * ()+"  "+straightDownPrice.doubleValue()); break; case
	 * PromotionConstants.PROMOTION_MANZENG:// 满增
	 * promotionDto.setWithIncreasingReason(promotionName);
	 * promotionDto.setWithIncreasingDate(new
	 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss"
	 * ).format(promotionInfo.getEndDate()));
	 * promotionDto.setWithIncreasing(true); break; case
	 * PromotionConstants.PROMOTION_MANFAN:// 满返
	 * promotionDto.setFullBackReason(promotionName);
	 * promotionDto.setFullBackDate(new
	 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format
	 * (promotionInfo.getEndDate())); promotionDto.setFullback(true); break;
	 * default: //LOGGER.info(""); break; } }
	 * 
	 * list.add(promotionDto); }
	 * 
	 * logger.info("---------------get promotion info success"); return list; }
	 */

	/**
	 * 获取购物车数量
	 * 
	 * @return
	 */
	public Integer getCartTotalQty(HttpServletRequest req) {
		// 判断用户是否存在
		User currentUser = getCurrentUser(req);
		Long userId = currentUser == null ? null : currentUser.getUserId();

		// 获取购物车商品数量
		Integer count = this.getCountQty(userId, req);

		return count;
	}

	public Integer getCountQty(Long userId, HttpServletRequest request) {
		String cookie = CookieUtil.getCookieValue(request, CommonConstant.KEY);
		if (null != cookie) {

			CartGetCountRequest cartGetCountRequest = null;
			if (isYueMeRequest(request)) {
				cartGetCountRequest = new CartGetCountRequest(userId, cookie,
						CommonConstant.ACCESS_MODE_YUEME);
			} else {
				cartGetCountRequest = new CartGetCountRequest(userId, cookie,
						CommonConstant.ACCESS_MODE_WAP);
			}

			Integer cartQty = customerCartService
					.getCountQty(cartGetCountRequest);
			return cartQty;
		} else {
			return 0;
		}
	}

	/**
	 * 
	 * 通过省市区ID 商品ID 异步加载库存信息
	 * 
	 * @param productId
	 *            商品ID
	 * @param provinceId
	 *            省ID
	 * @param cityId
	 *            市ID
	 * @param districtId
	 *            区ID
	 * @param areaName
	 *            地址
	 * @return 库存 及 商品ID
	 */
	@RequestMapping(value = "/stock")
	@ResponseBody
	public String findQuantityInStock(HttpServletRequest request,
			HttpServletResponse response, Long productId, Long provinceId,
			Long cityId, Long districtId, String areaName, String callback) {

		// 参数构造方法 存放参数
		ProdB2bStockSelectDto prodB2bStockSelectDto = new ProdB2bStockSelectDto(
				productId, provinceId, cityId, districtId);
		String json = null;

		// 获得代理服务对象
		CustomerProductApi customerProductApi = null;

		try {
			customerProductApi = RemoteServiceSingleton.getInstance()
					.getCustomerProductApi();

			User user = getCurrentUser(request);
			Long userId = user == null ? null : user.getUserId();

			addressControllerImpl.setAreaToCookieAndRedis(request, response,
					provinceId, cityId, districtId, userId);

			// String valArea =
			// provinceId+","+cityId+","+districtId+","+areaName;
			// CookieTool.setCookie(response, Constants.AREAIDKEY,
			// URLEncoder.encode(valArea), Constants.OUT_TIME_COOKIE);
		} catch (Exception e1) {
			logger.error(
					"retailerProductService service get fail-------------:  "
							+ e1.getMessage(), e1);
			if (org.apache.commons.lang3.StringUtils.isBlank(callback)) {
				// josnp 为Null 时 返回 json 否则josnp格式
				// return json;
				return json = "0";
			}
			return callback + "(" + 0 + ")";
		}

		// 传入productservice接口
		prodB2bStockSelectDto.setCustomerProductService(customerProductApi);

		// 获取库存服务
		StockCustomerService stockCustomerService = null;
		try {
			stockCustomerService = RemoteServiceSingleton.getInstance()
					.getStockCustomerService();
		} catch (Exception e1) {
			logger.error(
					"stockService service get fail-------------:  "
							+ e1.getMessage(), e1);
			if (org.apache.commons.lang3.StringUtils.isBlank(callback)) {
				// josnp 为Null 时 返回 json 否则josnp格式
				// return json;
				return json = "0";
			}
			return callback + "(" + 0 + ")";
		}

		prodB2bStockSelectDto.setStockCustomerService(stockCustomerService);

		// 调用商品静态方法
		System.out.println(ProductStockUtil.class.toString());

		ProductStockDto findProdB2cStockByProdId = null;
		try {
			findProdB2cStockByProdId = ProductStockUtil
					.findProdB2cStockByProdId(prodB2bStockSelectDto);
		} catch (Exception e) {
			logger.info("调用商品静态方法异常：" + e.getMessage());
		}

		// 如果返回对象不是空的 转换对象为json格式
		if (null != findProdB2cStockByProdId) {

			List<SkuStockDto> skuStockDtos = findProdB2cStockByProdId
					.getSkuStockDtos();

			for (SkuStockDto skuStockDto : skuStockDtos) {

				skuStockDto.setSkuIdV(skuStockDto.getSkuId() + "");
				// skuStockDto.setSpotQty(10L);

			}

			json = JSONObject.fromObject(findProdB2cStockByProdId).toString();
			if (org.apache.commons.lang3.StringUtils.isBlank(callback)) {
				// josnp 为Null 时 返回 json 否则josnp格式
				return json;
			}
		}

		return callback + "(" + json + ")";
	}

	@RequestMapping(value = "/view")
	public String toView() {
		return "item/toView";
	}

	/**
	 * 获取省市区名称根据省市区ID
	 * 
	 * @param provinceId
	 * @param cityId
	 * @param areaId
	 * @return
	 */
	public String getProvinceCityCountyName(Long provinceId, Long cityId,
			Long areaId) {
		return baseDataServiceRpc.getProvinceCityCountyName(provinceId, cityId,
				areaId);
	}

}
