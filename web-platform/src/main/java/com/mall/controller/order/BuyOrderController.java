/*package com.mall.controller.order;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.dealer.model.Dealer;
import com.mall.dealer.product.dto.DealerProductForAuthDealerDTO;
import com.mall.dealer.product.dto.DealerProductSelectConDTO;
import com.mall.mybatis.utility.PageBean;
import com.mall.retailer.model.Retailer;
import com.mall.retailer.order.dto.OrderItemDetail;
import com.mall.retailer.order.po.Order;
import com.mall.retailer.order.po.OrderMsg;
import com.mall.supplier.model.Supplier;
import com.mall.supplier.order.common.SupplierConstant;
import com.mall.supplier.order.dto.PurchaseOrderDto;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Common;
import com.mall.utils.Constants;
*//**.
 * @Description:wofe买家中心的已合并订单<期货>
 * @author GuoFeiyan
 * @version
 * @category
 * @Date 2014年5月16日15:36:39
 *//*
@Controller
@RequestMapping(value="/buyOrder")
public class BuyOrderController extends BaseController  {	
	*//**
	 * LOGGER(日志打印).
	 *//*
	private static final Log LOGGER = LogFactory.getLogger(BuyOrderController.class);
	*//**.
	 * 此方法用于日期的转换
	 * @param binder WebDataBinder
	 *//* 
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	*//**.
	 * @Description:进入买家中心订单已经合并列表	
	 * @Title:buyOrder
	 * @return String
	 *//*
	@RequestMapping(value="/buyOrder")
    public final String buyOrder(){
		LOGGER.info("跳转已合并订单页面");
		LOGGER.info("用户:"+getCurrentUser().getUsername());
		return "/dealerbuy/buyorder";
	}
	
	*//**.
	 * @Description:根据条件查询已合并订单列表
	 * @Title:findBuyOrder
	 * @param status  状态.
	 * @param orderId 订单Id.
	 * @param pNames 商品名称,
	 * @param supplyName String.
	 * @param endTime String
	 * @param createTime String.
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param retailerOrderDto
	 * @return MODEL model
	 * @Date 2014年5月16日15:36:39
	 *//*
	@RequestMapping(value="/findBuyOrder")
	public ModelAndView findBuyOrder(HttpServletRequest request,HttpServletResponse response,Short status,Long orderId,
			String pName,String supplyName,String endTime,String createTime,Integer page,Model model){
		LOGGER.info("-----------start to execute method <findBuyOrder>!!!!------------");
		LOGGER.info(" 订单状态"+status);
		LOGGER.info(" 订单ID"+orderId);
		LOGGER.info(" 商品名称"+pName);
		LOGGER.info(" 供应商名称"+supplyName);
		LOGGER.info(" 结束时间"+endTime);
		LOGGER.info(" 创建时间"+createTime);
		LOGGER.info(" 页码"+(page==null?Constants.INT1:page));
		PurchaseOrderDto purOrderDto = new PurchaseOrderDto();
		PageBean<PurchaseOrderDto> pageBean = new PageBean<PurchaseOrderDto>();
		if(!Common.isEmpty(pName)){     //商品名称
			purOrderDto.setpName(pName);
			model.addAttribute("pName", pName);
		}
		if(!Common.isEmpty(supplyName)){//供应商名称
			List<Long> supplierIds = new ArrayList<Long>();
			supplierIds = RemoteServiceSingleton.getInstance()
					.getSupplierManagerService().getSupplierIdsByName(supplyName);//获取页面的值去调用供应商接口服务,传给订单id的集合
			
			if(supplierIds.size()<1){
				supplierIds.add(0l);
			}
			purOrderDto.setSupplierIds(supplierIds);
			model.addAttribute("supplyName", supplyName);
		}
		if(orderId!=null&&orderId!=0){ //订单编号
			purOrderDto.setPoId(orderId);	
		}
		if(!Common.isEmpty(createTime)){
			purOrderDto.setCreateTime(Common.stringToDate(createTime.replace('-', '/'),"yyyy/MM/dd HH:mm:ss"));	
			model.addAttribute("createTime", createTime);	
		}
		if(!Common.isEmpty(endTime)){
			purOrderDto.setEndTime(Common.stringToDate(endTime.replace('-', '/'),"yyyy/MM/dd HH:mm:ss"));	
			model.addAttribute("endTime", endTime);
		}
		if(status!=null){
			purOrderDto.setStatus(status);
			model.addAttribute("status", status);
		}
		
		if(page!=null&&page!=0){  //判断条件中是否带着分页码
			pageBean.setPage(page);
		}else {
			pageBean.setPage(1);//没有分页码的话就检索第一页
		}
		pageBean.setPageSize(Constants.PAGESIZE);
		pageBean.setParameter(purOrderDto);
		pageBean = RemoteServiceSingleton.getInstance().getDealerOrderService().findPurchaseOrder(pageBean);
		if(pageBean!=null){
			for (int i = 0; i < pageBean.getResult().size(); i++) {
				String imgUrl = pageBean.getResult().get(i).getImgUrl();
				if(!imgUrl.startsWith("http")||!imgUrl.startsWith("Http")){
					imgUrl = Constants.P1+imgUrl;
					pageBean.getResult().get(i).setImgUrl(imgUrl);
				}
			}
		}
			request.getSession().setAttribute("pageBean",pageBean);
		try {
			request.getRequestDispatcher("/WEB-INF/views/dealerbuy/basePage/buyorder_futureslistmodel.jsp")
				.forward(request, response);	
		} catch (Exception e) {
			LOGGER.error(" 商户Id:"+getCurrentPlatformId());
			LOGGER.error(" 用户ID:"+getCurrentUser().getId());	
			LOGGER.error(" 订单状态"+status);
			LOGGER.error(" 订单ID"+orderId);
			LOGGER.error(" 商品名称"+pName);
			LOGGER.error(" 供应商名称"+supplyName);
			LOGGER.error(" 结束时间"+endTime);
			LOGGER.error(" 创建时间"+createTime);
			LOGGER.error(" 页码"+(page==null?Constants.INT1:page));
			LOGGER.error("检索采购订单数量,错误信息:"+e.getMessage(),e);
		}
		LOGGER.info("-------end to execute method <findBuyOrder>!!!!-----------");
		return null;
	}
	
	*//**.
	 * @Title:showBuyOrderDetail
	 * @Description:根据采购单号查询采购订单的详细信息
	 * @param Long poId 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param model Model
	 * @return ModelAndView
	 * @Date 2014-5-18
	 *//*
	@RequestMapping(value="/showBuyOrderDetail")
    public final ModelAndView showBuyOrderDetail(Long poId,HttpServletRequest request,
			HttpServletResponse response,Model model){
		
		LOGGER.info("-------start to execute method <showBuyOrderDetail>!!!!-----------");
		LOGGER.info("商户Id:"+getCurrentPlatformId());
		LOGGER.info("用户ID:"+getCurrentUser().getId());
		LOGGER.info(" 采购单号："+poId);
		
		PurchaseOrderDto purchaseOrderDto = new PurchaseOrderDto();
		List<OrderMsg> msgList = new ArrayList<OrderMsg>();
		Supplier supplier = new Supplier();
		String cityString = "";
		try {
			purchaseOrderDto = RemoteServiceSingleton.getInstance()
					.getDealerOrderService().findAllRetailerOrderByPOid(poId,SupplierConstant.ORDER_MSG_WOFE);
			if(purchaseOrderDto!=null){
				if(null!=purchaseOrderDto.getDealerId()){
					Dealer dealer = RemoteServiceSingleton.getInstance().
							getDealerService().findByDealerId(purchaseOrderDto.getDealerId());
					model.addAttribute("dealer", dealer);
				}
			    String imgUrl= purchaseOrderDto.getImgUrl();
                if(!imgUrl.startsWith("http")||!imgUrl.startsWith("Http")){
                    imgUrl = Constants.P1+imgUrl;
                    purchaseOrderDto.setImgUrl(imgUrl);
                }
                if( purchaseOrderDto.getItemlist()!=null&& purchaseOrderDto.getRetailerOrderDto().size()>=0){
                    for (int i = 0; i < purchaseOrderDto.getRetailerOrderDto().size(); i++) {
                       if(!imgUrl.startsWith("http")||!imgUrl.startsWith("Http")){
                           imgUrl = Constants.P1+imgUrl;
                           purchaseOrderDto.getRetailerOrderDto().get(i).setImgUrl(imgUrl);
                       } 
                    }
                }
	            msgList = purchaseOrderDto.getRetailerOrderMsgDto();
	            Long supplierId=purchaseOrderDto.getSupplierId();
    			try {
    			    //调用供应商商户接口获取供应商信息
    	            supplier = RemoteServiceSingleton.getInstance()
    	            		.getSupplierManagerService().findSupplier(supplierId);
    	            //调用基础信息服务得到供应商所在省市传入前台
    	            if(supplier!=null){
    	            	String cityName ="";
    	            	String provinceName ="";
    	            	String areName ="";
    	                Integer cityId = supplier.getCityId(); 
                        Integer provinceId = supplier.getProvinceId(); 
                        Integer arelId = supplier.getAreaId();
                        if(cityId!=null){
                        	 cityName = RemoteServiceSingleton.getInstance()
                        			 .getBaseDataServiceRpc().getCityById(cityId).getCityname();
                        }
                        if(provinceId!=null){
                        	provinceName = RemoteServiceSingleton.getInstance()
                        			.getBaseDataServiceRpc().getProvinceById(provinceId).getProvincename();
                       }
                       
                        if(arelId!=null){
                        	areName =RemoteServiceSingleton.getInstance()
                        			.getBaseDataServiceRpc().getCountyById(arelId).getCountyname();
                       }
                        cityString = cityName+provinceName+areName;
    	            } 
    			} catch (Exception e) {
    				LOGGER.error("wofe go to BaseDataServiceRpc(CountyById)<根据区的ID查询区的名字> is failed!!!!");
    			}
			}
			model.addAttribute("areaId",cityString);
			model.addAttribute("supplier", supplier);
			model.addAttribute("purDto", purchaseOrderDto);
			model.addAttribute("orderMsg",msgList);	
			
		} catch (Exception e) {
			LOGGER.error("用户ID:"+getCurrentUser().getId());
			LOGGER.error("wofe go to findAllRetailerOrderByPOid<根据采购单查询详情> is failed!!!!"+e.getMessage(),e);
		}
		LOGGER.info("-------end to execute method <showBuyOrderDetail>!!!!-----------");
		return new ModelAndView("/dealerbuy/buyorderdetails");
	}

	*//**.
	 * @Title:joinOrder
	 * @Description:合并生成采购订单(一次性合并,选择性合并)
	 * @param String ids
	 * @param request HttpServletRequest
	 * @param String totalMoney
	 * @return String
	 * @Date 2014-5-18
	 *//*
	@RequestMapping(value="/joinOrder")
	@ResponseBody
    public final String joinOrder(String ids,HttpServletRequest request,String totalMoney){
		LOGGER.info(" 合并生成采购订单（一次性合并,选择性合并）开始");
		LOGGER.info("商户Id:"+getCurrentPlatformId());
		LOGGER.info("用户ID:"+getCurrentUser().getId());
		BigDecimal money = new BigDecimal(totalMoney);
		//获取当前登录的用户信息传入订单接口作为操作人
		String createBy;
		String result= null;
		createBy=getCurrentUser().getUsername()+"("+getCurrentUser().getId()+")";
		List<Long> idList = new ArrayList<Long>();
		boolean flag=true;
		try {
			LOGGER.info("参数："+ids+"创建人："+createBy+"总金额：totalMoney="+totalMoney);
			if(ids!=null&&!ids.equals("")){
				String[] idsa = ids.split(",");
				for (int i = 0; i < idsa.length; i++) {
					idList.add(Long.parseLong(idsa[i]));
				}
				
				LOGGER.info(" 合并生成采购订单,金额"+money);
				LOGGER.info(" 合并生成采购订单,id集合"+idList);
				LOGGER.info(" 合并生成采购订单,合并人"+createBy);
				
				flag = RemoteServiceSingleton.getInstance().
						getDealerOrderService().savePurChaseOrder(money, idList,createBy);
				
				if(flag){
					
					result ="合并订单成功了";
					
				}else{
					
					LOGGER.error(" 合并生成采购订单失败,金额"+money);
					LOGGER.error(" 合并生成采购订单失败,id集合"+idList);
					LOGGER.error(" 合并生成采购订单失败,合并人"+createBy);
					
					result = "合并失败";
				}
				
			}else {
				
				result="请选择要合并的订单";
				
			}
		} catch (Exception e) {
			
			LOGGER.error(" 合并生成采购订单失败,错误信息:"+e.getMessage());
    		
			LOGGER.error("商户Id:"+getCurrentPlatformId());
			LOGGER.error("用户:"+getCurrentUser().getUsername());
			LOGGER.error("用户ID:"+getCurrentUser().getId());
	    		
			LOGGER.error(" 合并生成采购订单失败,金额:"+money);
			LOGGER.error(" 合并生成采购订单失败,id集合:"+idList);
			
			LOGGER.error(" 合并生成采购订单失败,错误信息:"+e);
			
			
			result = "合并失败";
			
		}
		
		return result;
	}
*//**.
 * wofe分配订单(分配成功状态21(待经销商下单))
 * @param orderId String
 * @param dealerId String
 * @param request String
 * @param response HttpServletResponse
 * @return String
 *//*
	@RequestMapping(value="/divideOrder")
	@ResponseBody
	public final String divideOrder(String orderId,String dealerId,
			HttpServletRequest request,HttpServletResponse response){ 
		
		LOGGER.info(" wofe分配订单");
		
		LOGGER.info("商户Id:"+getCurrentPlatformId());
		
		LOGGER.info("用户:"+getCurrentUser().getUsername());
		LOGGER.info("用户ID:"+getCurrentUser().getId());
		
		
		
		String responseString = null;
		boolean flag = true;
		
		try {
			
			String createBy = getCurrentUser().getUsername()+"("+getCurrentUser().getId()+")";
			LOGGER.error("参数：orderId===="+orderId+"dealerId===="+dealerId+"createBy===="+createBy);
			flag = RemoteServiceSingleton.getInstance().getDealerOrderService().allocationPurchaseOrder
					(Long.parseLong(dealerId), Long.parseLong(orderId),Constants.STATUS21, createBy);
			if(flag){
				responseString="分单成功了";
			}	
		} catch (Exception e) {
		
			LOGGER.error("allocationPurchaseOrder==wofe分单失败=="+e.getMessage());
    		
			LOGGER.error("商户Id:"+getCurrentPlatformId());
			LOGGER.error("用户:"+getCurrentUser().getUsername());
			LOGGER.error("用户ID:"+getCurrentUser().getId());
	    		
			LOGGER.error("锁定库存错误,订单Id:"+orderId);
			
			LOGGER.error("allocationPurchaseOrder==wofe分单失败=="+e);
	
			responseString= "分单失败!";
	
		}
		
		return responseString;
		
	}
	
*//**.
 * 根据选择的商品Pid检索被授权的经销商列表
 * @param proId String
 * @param model String
 * @param response HttpServletResponse
 * @param request HttpServletRequest
 * @param orderId String
 * @param page int
 * @return ModelAndView
 *//*
	@RequestMapping(value="/findDealerByPid")
    public final ModelAndView findDealerByPid(String proId,String orderId,Model model,Integer page,
			HttpServletResponse response,HttpServletRequest request){
		LOGGER.info("参数列表：proId===="+proId+"orderId===="+orderId+"page===="+page);
		try {
			DealerProductSelectConDTO condition = new DealerProductSelectConDTO();
			condition.setProductId(Long.parseLong(proId));
			PageBean<DealerProductForAuthDealerDTO> pageBean = new PageBean<DealerProductForAuthDealerDTO>();
			//判断请求中是否有请求页码
			if(page!=null&&page!=0){
				pageBean.setPage(page);
			}else{
				pageBean.setPage(1);//没有的话就检索第一页
			}
			pageBean.setPageSize(Constants.NUM10);
			pageBean.setParameter(condition);
			pageBean = RemoteServiceSingleton.getInstance().
					getDealerProductAuthorizeService().findAuthDealerDTOByPId(pageBean);
			request.getSession().setAttribute("dealer", pageBean.getResult());

			//获取代理地区(省市区地址)
			//pageBean.getResult().get(0).getCityid();
			request.getSession().setAttribute("pageBean1", pageBean);
			request.getSession().setAttribute("orderId", orderId);
			request.getRequestDispatcher("/WEB-INF/views/dealerbuy/basePage/fendanList.jsp").forward(request, response);
			
		} catch (Exception e) {
			
			LOGGER.error("DealerProductAuthorizeService===被授权此商品的经销商列表接口异常"+e.getMessage());
    		
			LOGGER.error("商户Id:"+getCurrentPlatformId());
			LOGGER.error("用户:"+getCurrentUser().getUsername());
			LOGGER.error("用户ID:"+getCurrentUser().getId());
	    		
			LOGGER.error("授权的经销商列表错误,商品ID:"+proId);
			LOGGER.error("授权的经销商列表错误,订单ID:"+orderId);
	    	LOGGER.error(" 页码"+(page==null?Constants.INT1:page));
	    		
			LOGGER.error("授权的经销商列表错误,错误信息:"+e);
			
			
		}
		return null;
	}
 
	
*//**.
 * 获取采购订单零售商订单详情
 * @param orderId orderId
 * @param request request
 * @param response response
 * @param model model
 *//*
	@RequestMapping(value="/getRetailerOrderDetail")
	public final void getRetailerOrderDetail(Long orderId,HttpServletRequest request,
			HttpServletResponse response,Model model){
		
		LOGGER.info("获取采购订单零售商订单详情");
		
		Retailer findByRetailerId = new Retailer();
		Order retailerOrder = new Order();
		try{
			
			LOGGER.info("订单详情  ： orderId ==" + orderId);
			
			OrderItemDetail findRetailerOrderItemByOrderId = RemoteServiceSingleton.getInstance().
					getDealerOrderService().findRetailerOrderItemByOrderId(orderId);
			if(null!=findRetailerOrderItemByOrderId){
				retailerOrder = findRetailerOrderItemByOrderId.getOrder();
				if(null!=retailerOrder){
					Long retailerId = findRetailerOrderItemByOrderId.getOrder().getRetailerId();
					findByRetailerId = RemoteServiceSingleton.getInstance().
							getRetailerManagerService().findByRetailerId(retailerId); 
				}
				request.getSession().setAttribute("skulist", findRetailerOrderItemByOrderId.getOrderItemList());
			}
			request.getSession().setAttribute("retailerOrder", retailerOrder);
			request.getSession().setAttribute("findRetailerOrderItemByOrderId", findRetailerOrderItemByOrderId);
			request.getSession().setAttribute("retailer", findByRetailerId);
			try {
				request.getRequestDispatcher("/WEB-INF/views/dealerbuy/basePage/reOrderDetail.jsp").
				forward(request, response);
			} catch (Exception e) {
				LOGGER.error("页面跳转出了问题=====",e);
			}
		}catch (Exception e) {
			LOGGER.error("DealerOrderService采购订单下的零售商订单详情接口异常====="+e.getMessage());
    		
			LOGGER.error("商户Id:"+getCurrentPlatformId());
			LOGGER.error("用户:"+getCurrentUser().getUsername());
			LOGGER.error("用户ID:"+getCurrentUser().getId());
	    		
			LOGGER.error("订单orderId"+orderId);
	    		
			LOGGER.error("锁定库存数量,错误信息:"+e.getMessage());
		}	
	}	
}
*/