/*package com.mall.controller.promotions;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rubyeye.xmemcached.exception.MemcachedException;
import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.json.JSONObject;
import com.mall.authority.client.constant.ConfigConstant;
import com.mall.authority.client.util.CookieUtil;
import com.mall.authority.client.util.UserUtil;
import com.mall.bean.authority.User;
import com.mall.category.api.rpc.BaseDataServiceRpc;
import com.mall.category.po.AgentCity;
import com.mall.category.po.AgentProvince;
import com.mall.category.po.Brand;
import com.mall.category.po.TdCatePub;
import com.mall.comment.exception.BusinessException;
//import com.mall.dealer.order.exception.BusinessException;
import com.mall.dealer.product.dto.SkuCodeDTO;
import com.mall.mybatis.utility.PageBean;
import com.mall.platform.model.PlatformChannel;
import com.mall.platform.model.PlatformUser;
import com.mall.promotion.dto.activity.ActivityInfo;
import com.mall.promotion.po.activity.ActiveProductRule;
import com.mall.promotion.po.activity.ActiveProductRuleDetail;
import com.mall.promotion.po.coupon.CouponRule;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;
import com.mall.utils.CookieHelper;


@Controller
@RequestMapping(value = "/activitis")
public class ActivitisListController extends BaseController{
	private static final int DEFAULT_PAGE = 1;
	private static final int PAGE_SIZE = 20;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ActivitisListController.class);
	
	@Autowired
	private BaseDataServiceRpc baseService;

	*//**
	 * 跳转到商品活动列表页
	 * 
	 * @return
	 *//*
	@RequestMapping(value = "/toList")
	public String list() {
		LOGGER.info("跳转到商品活动列表页");
		return "/activitis/activityList";
	}
	
	@RequestMapping(value = "/toActivityList")
	public String toActivityList(){
		LOGGER.info("跳转到商品活动列表页");
		return "/activitis/activityList";
	}

	*//**
	 * 保存活动
	 * 
	 * @return
	 *//*
	@RequestMapping(value = "/saveActivity")
	public String saveActivity(String activeName) {

		return "/activitis/activityList";
	}

	*//**
	 * 查询商品活动列表
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @param param
	 * @param model
	 * @return
	 *//*
	@RequestMapping(value = "/searchActivityList")
	public String findActivityByCondition(HttpServletRequest request,
			HttpServletResponse response, Integer page,String expiringFrom,String expiringTo, String activityName,String createBy,
			Model model) {
		try {
			SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			LOGGER.info("调用查询商品活动列表controller");
			PageBean<ActiveProductRule> pageBean = new PageBean<ActiveProductRule>();
			
			pageBean.setPageSize(Constants.PAGESIZE);
			ActiveProductRule activeProductRule = new ActiveProductRule();
			activeProductRule.setRuleName(activityName);
			if(null != expiringFrom){
				activeProductRule.setStartdate(sdf.parse(expiringFrom));
			}
			if(null != expiringTo){
				activeProductRule.setEnddate(sdf.parse(expiringTo));
			}
			activeProductRule.setCategoryName(createBy);
			
			pageBean.setParameter(activeProductRule);
			if (page != null && page != 0) {
				pageBean.setPage(page);
			} else {
				pageBean.setPage(1);
			}
			// 调用接口查询商品活动列表
			pageBean = RemoteServiceSingleton.getInstance().getActiveProductRuleApi().findActiveProductRulesListPage(pageBean);
			
			request.getSession().setAttribute("pb", pageBean);
			request.getRequestDispatcher("/WEB-INF/views/activitis/activityListModel.jsp").forward(request, response);
		} catch (Exception e) {
			LOGGER.error("查询活动列表异常：" + e.getMessage(), e);
		}
		return null;
	}

	*//**
	 * 查询商品活动规则列表
	 * 
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 *//*
	@RequestMapping(value = "/toAddActivity")
	public String list(HttpServletRequest request,
			HttpServletResponse response, Long activeId, Long ruleId,
			Model model) {
		try{
			User object = UserUtil.getUserFromCookie(request, response,  Constants.MEMBER);
			String name = null;
			if(null==object){
				name = "nobody";
			}else{
				name = object.getUsername();
			}
			model.addAttribute("loginName",name);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			model.addAttribute("activeId", activeId);
			model.addAttribute("createTime", dateFormat.format(new Date()));
			// 查询品牌
			try {
				List<Brand> brand = RemoteServiceSingleton.getInstance()
						.getBrandServiceRpc().findAllBrand();
				if (brand != null && !brand.isEmpty()) {
					model.addAttribute("brand", brand);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 查询类目
	
			try {
				List<TdCatePub> cate = RemoteServiceSingleton.getInstance()
						.getCategoryServiceRpc().getTopCategoryList();
				if (cate != null && !cate.isEmpty()) {
					model.addAttribute("cate", cate);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//查询省份
			try {
				List<AgentProvince> allProvices = baseService.getAllProvices();
				if(allProvices != null && !allProvices.isEmpty()) {
					model.addAttribute("provices", allProvices);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//查询渠道
			List<PlatformChannel> channelList=RemoteServiceSingleton.getInstance().getChannelManagerService().getAllChannel();
				if(channelList != null && !channelList.isEmpty()) {
					model.addAttribute("channel", channelList);
				}
			return "/activitis/rulesEdit";
		}catch(Exception e){
			throw new BusinessException(e.getMessage());
		}

	}
	
	@RequestMapping(value = "/searchBrand")
	public String brandPaging(HttpServletRequest request,
			HttpServletResponse response, Integer pageNum,com.ccigmall.mybatis.dwz.utility.PageBean<Brand> pageBean) {
		try {
			String brandName = request.getParameter("brandName");
			LOGGER.info("调用查询品牌controller");
			Brand brand = new Brand();
			pageBean.setNumPerPage(PAGE_SIZE);
			if (pageNum != null && pageNum != 0) {
				pageBean.setPageNum(pageNum);
			} else {
				pageBean.setPageNum(DEFAULT_PAGE);
			}
			if(brandName != null && !"".equals(brandName)) {
				brand.setNameCn(brandName);
			}
			pageBean.setParameter(brand);
			pageBean = RemoteServiceSingleton.getInstance().getMyBrandService().getSearchBrandListPage(pageBean);
			request.getSession().setAttribute("pb", pageBean);
			request.getRequestDispatcher("/WEB-INF/views/activitis/brandModel.jsp").forward(request, response);
		} catch (Exception e) {
			LOGGER.error("查询品牌异常：" + e.getMessage(), e);
		}
		return null;
	}
	
	

	*//**
	 * 查询商品
	 * 
	 * @param request
	 * @param response
	 * @param activeId
	 * @param ruleId
	 * @return
	 *//*
	@RequestMapping(value = "/searchProduct")
	public String searchProduct(HttpServletRequest request,
			HttpServletResponse response, Integer page) {
		String categoryId = request.getParameter("categoryId");
		String supplierName = request.getParameter("supplierName");
		String categoryCode = request.getParameter("categoryCode");//商品ID
		String productCode = request.getParameter("productCode");//商品编码
		
		try {
			LOGGER.info("调用查询商品controller");
			PageBean<SkuCodeDTO> pageBean = new PageBean<SkuCodeDTO>();
			SkuCodeDTO skuCodeDTO = new SkuCodeDTO();
			//skuCodeDTO.getBusinessProdId();//商品编码
			//skuCodeDTO.getProductId();//商品ID
			
			pageBean.setPageSize(Constants.PAGESIZE);
			if (page != null && page != 0) {
				pageBean.setPage(page);
			} else {
				pageBean.setPage(DEFAULT_PAGE);
			}
			if(categoryId != null) {
				skuCodeDTO.setCatePubId(categoryId);
			}
			if(supplierName != null) {
				skuCodeDTO.setSupplierName(supplierName);
			}
			//skuCodeDTO.getB2bProdName();
			if(categoryCode != null) {
				skuCodeDTO.setProductId(Long.parseLong(categoryCode));
			}
			if(productCode != null) {
				skuCodeDTO.setBusinessProdId(productCode);
			}
			Short b2cType = Short.parseShort(request.getParameter("b2cType"));
			if(b2cType == 3){
				b2cType = 0;
			}else{
				b2cType = 1;
			}
			skuCodeDTO.setB2cTate(b2cType);
			pageBean.setParameter(skuCodeDTO);
			pageBean = RemoteServiceSingleton.getInstance()
					.getCustomerPromotionService().findPageSkuDto(pageBean);
			request.getSession().setAttribute("pb", pageBean);
			request.getRequestDispatcher(
					"/WEB-INF/views/activitis/commodityModel.jsp").forward(
					request, response);
		} catch (Exception e) {
			LOGGER.error("查询商品异常：" + e.getMessage(), e);
		}
		return null;
	}

	*//**
	 * 品类id查询品类下级目录
	 * @param pid 品类id
	 * @return 下级目录集合
	 *//*
	@RequestMapping(value = "/searchCategoryByPid")
	@ResponseBody
	public String searchCategoryByPid(String pid ) {
		if (pid != null && !"".equals(pid)) {
			try {
				List<TdCatePub> cateone = RemoteServiceSingleton.getInstance()
						.getCategoryServiceRpc().getChildrenCategoryList(pid);
				if (cateone != null && !cateone.isEmpty()) {

					String jsond = JSONArray.fromObject(cateone).toString();
					return jsond;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	*//**
	 * 省id查询城市
	 * @param pid 省id
	 * @return 城市jison数据
	 *//*
	@RequestMapping(value = "/searchCityByProviceId")
	@ResponseBody
	public String searchCityByProviceId(int pid) {
			try {
				List<AgentCity> cities = baseService.getCitiesByProvinceId(pid);
				if(cities != null && !cities.isEmpty()) {
					String jsonCities = JSONArray.fromObject(cities).toString();
					return jsonCities;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		return null;
	}
	
	@RequestMapping(value = "/modifyStatusOfRule")
	public String modifyStatusOfRule(HttpServletRequest request,Long ruleId,Short status){
		ActiveProductRule activeProductRule = new ActiveProductRule();
		activeProductRule.setId(ruleId);
		if(status == 0){
			activeProductRule.setStatus((short) 1);
		}else{
			activeProductRule.setStatus((short) 0);
		}
		RemoteServiceSingleton.getInstance().getActiveProductRuleApi().updateProductRuleNew(activeProductRule);
		return "success";
	}
	
	@RequestMapping(value = "/viewRule")
	@ResponseBody
	public String viewRule(HttpServletRequest request,Long ruleId){
		String str = "";
		try{
			ActivityInfo activityInfo = RemoteServiceSingleton.getInstance().getActiveProductRuleApi().findActivityInfoByRuleId(ruleId);
			if(activityInfo.getActiveProductRule().getRuleTerm() == 22){//阶梯满返，需要查询一下优惠券名称
				List<ActiveProductRuleDetail> details = activityInfo.getDetails();
				for(ActiveProductRuleDetail detail : details){
					CouponRule couponRule = RemoteServiceSingleton.getInstance().getCouponRuleService().getCouponRulePoById(Long.parseLong(detail.getTerm2()));
					detail.setpName(couponRule.getCouponrulename());
				}
			}
			str = JSONArray.fromObject(activityInfo).toString();
		}catch(Exception e){
			LOGGER.error("获取活动详情异常"+e.getMessage());
		}
		return str;
	}
}
*/