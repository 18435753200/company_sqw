/*package com.mall.controller.order;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.dealer.order.dto.RetailerOrderDto;
import com.mall.dealer.product.dto.DealerProductSaleInfoForOrderDTO;
import com.mall.mybatis.utility.PageBean;
import com.mall.supplier.product.dto.SupplierProductForOrderDTO;
import com.mall.supplier.product.po.SupplierProductWholesaleRange;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;

*//**.
 * 
 * 经销商买家中心等待提交的订单汇总
 * @author Guo
 * @version
 *//*
@Controller
@RequestMapping(value="/waitOrder")
public class WaitSubmitOrderController extends BaseController{
	*//**
	 * LOGGER.
	 *//*
	private static final Log LOGGER = LogFactory.getLogger(WaitSubmitOrderController.class);
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
	 * 进入等候订单页面
	 * @return String
	 *//*

	@RequestMapping(value="/SumitOrder")
	public final String sumitOrder(){
		LOGGER.info("等候订单页面");

		LOGGER.info("商户Id:"+getCurrentPlatformId());

		LOGGER.info("用户:"+getCurrentUser().getUsername());
		LOGGER.info("用户ID:"+getCurrentUser().getId());

		return "/dealerbuy/waitorder";
	}
	*//**方法①.
	 * 加载等待合并的订单(判断起订量是否达到)
	 * @param model model
	 * @param pName pName
	 * @param supplyName supplyName
	 * @param  page  page
	 * @param response response
	 * @param request request
	 * @return list集合
	 * @throws Exception is error
	 *//*
	@RequestMapping(value="/waitSumitOrder")
	public final ModelAndView waitSumitOrder(Model model,String pName,String supplyName,Integer page,
			HttpServletResponse response,HttpServletRequest request ) throws Exception{
		
		LOGGER.info("加载等待合并的订单");
		
		PageBean<RetailerOrderDto> pageBean = new PageBean<RetailerOrderDto>();
		List<RetailerOrderDto> retailerOrderList = new ArrayList<RetailerOrderDto>();
		RetailerOrderDto dto = new RetailerOrderDto();
		try {
			LOGGER.info("参数列表：pName===="+pName+"supplyName===="+supplyName+"page===="+page);
			//检索条件商品名称
			if(pName!=null&&!pName.equals("")){
				List<Long> pidList = new ArrayList<Long>();
				pidList = RemoteServiceSingleton.getInstance().getSupplierProductOrderService().findProIdByProName(pName);
				if(null!=pidList&&pidList.size()>0){
					dto.setPids(pidList);
				}
			}
			//检索条件供应商名称
			if(supplyName!=null&&!supplyName.equals("")){
				//接收请求中的supplyName调用供应商接口返回给订单接口一个list集合
				try {
					List<Long> supplierIds = new ArrayList<Long>();
					supplierIds = RemoteServiceSingleton.getInstance()
							.getSupplierManagerService().getSupplierIdsByName(supplyName);//获取页面的值去调用供应商接口服务,传给订单id的集合
					if(supplierIds.size()<1){
						supplierIds.add(0l);
					}
					dto.setSupplierIds(supplierIds);	
				} catch (Exception e) {
					LOGGER.error("供应商接口异常======稍后再试",e);	
				}
			}
			//判断请求中是否有请求页码
			if(page!=null&&page!=Constants.NUM0){
				pageBean.setPage(page);
			}else{
				pageBean.setPage(Constants.NUM1);
			}
			//必带条件状态31(等待合并的订单)
			dto.setStatus(Constants.STATUS31);
			dto.setIsFutures(Constants.NUM1);
			if(dto.getStatus()==Constants.STATUS31){
				pageBean.setPageSize(Constants.PAGESIZE);
				pageBean.setParameter(dto);
			}
			//调用订单接口检索数据
			pageBean=RemoteServiceSingleton.getInstance().getDealerOrderService().findAllRetailerOrder(pageBean);
			if(pageBean!=null){
				//处理订单中的图片
				retailerOrderList = pageBean.getResult();
				if(retailerOrderList!=null){
					for (int i = 0; i < retailerOrderList.size(); i++) {
						String imgUrl = retailerOrderList.get(i).getImgUrl();
						if(!imgUrl.startsWith("http")||!imgUrl.startsWith("Http")){
							imgUrl = Constants.P1+imgUrl;
							retailerOrderList.get(i).setImgUrl(imgUrl);
						}
					}
				}
			}

			request.getSession().setAttribute("pageBean",pageBean);
			request.getRequestDispatcher("/WEB-INF/views/dealerbuy/basePage/buyorder_futurescombinelistmodel.jsp").forward(request, response);	
		} catch (Exception e) {
			
			LOGGER.error("加载等待合并的订单");
    		
			LOGGER.error("商户Id:"+getCurrentPlatformId());
			LOGGER.error("用户:"+getCurrentUser().getUsername());
			LOGGER.error("用户ID:"+getCurrentUser().getId());
	    		
			LOGGER.error("加载等待合并的订单,错误信息:"+e.getMessage());
			
		}
		return null;
	}

	*//**方法③.
	 * (WOFE)显示订单详情列表
	 * 包括填写用户收货信息
	 * 和下单的梯度价格
	 * @param request request
	 * @param response response
	 * @param model model
	 * @param pid pid
	 * @param micQty micQty
	 * @return 2个list集合
	 *//*
	@RequestMapping(value="/showOrderDetail")
	public final String showOrderDetail(String pid,Integer micQty,HttpServletRequest request,
			HttpServletResponse response,Model model){
		DealerProductSaleInfoForOrderDTO dto=RemoteServiceSingleton.getInstance().getDealerProductOrderService().findProSaleInfoByPId(Long.parseLong(pid));
		List<RetailerOrderDto> orderlist = new ArrayList<RetailerOrderDto>();
		List<SupplierProductWholesaleRange> productWholesaleRangeList = new ArrayList<SupplierProductWholesaleRange>();
		SupplierProductForOrderDTO supplierProductForOrderVo = new SupplierProductForOrderDTO();
		//获取一个pid下的所有订单的信息列表
		try {
			LOGGER.info("参数列表：pid===="+pid+"micQty===="+micQty);
			orderlist=RemoteServiceSingleton.getInstance().getDealerOrderService().findAllRetailerOrderByPid
					(Long.parseLong(pid), Constants.STATUS31);
			if(orderlist!=null&&orderlist.size()>0){
				for (int i = 0; i < orderlist.size(); i++) {
					String imgUrl = orderlist.get(i).getImgUrl();
					if(!imgUrl.startsWith("http")||!imgUrl.startsWith("Http")){
						imgUrl = Constants.P1+imgUrl;
						orderlist.get(i).setImgUrl(imgUrl);
					}
				}
				supplierProductForOrderVo = orderlist.get(0).getSupplierProductForOrderVo(); 

				productWholesaleRangeList = supplierProductForOrderVo.getsupplierProductWholesaleRanges();
				if(productWholesaleRangeList!=null&&productWholesaleRangeList.size()>0){
					request.setAttribute("isRangeList", 1);
					model.addAttribute("wholesaleRanges", productWholesaleRangeList);
				}else{
					model.addAttribute("skuList", JSON.toJSONString(supplierProductForOrderVo.getsupplierProductSkuDTOs()));
					BigDecimal skuTotalPrice = new BigDecimal(1.00);
					for (int i = 0; i < orderlist.size(); i++) {
						if(orderlist.get(i).getItemlist()!=null&&orderlist.get(i).getItemlist().size()!=0){
							for (int j = 0; j < orderlist.get(i).getItemlist().size(); j++) {
								BigDecimal bigDecimal = 
										new BigDecimal(orderlist.get(i).getItemlist().get(j).getSkuQty());
								skuTotalPrice = 
										bigDecimal.multiply(orderlist.get(i).getItemlist().get(j).getSkuPrice());

								orderlist.get(i).setTotalPrice(skuTotalPrice);
							}
						}	
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("等待合并的订单详情显示异常",e);
		}
		model.addAttribute("pid", pid);
		model.addAttribute("moneyUnitSymbols", supplierProductForOrderVo.getSupplierProductSaleSetting());
		model.addAttribute("orderlist", orderlist);
		model.addAttribute("dto", dto);
		model.addAttribute("micQty", micQty);
		return "/dealerbuy/buyorderqueren";
	}

	*//**.
	 * 通过勾选计算总金额	
	 * @param pid pid
	 * @param Orderqty Orderqty
	 * @param request request
	 * @param response response
	 * @param model model
	 *//*
	@RequestMapping(value="/totalNumByCount")
	public final void totalNumByCount(Long pid,String Orderqty,HttpServletRequest request,String orderIds,
			HttpServletResponse response,Model model){
		List<RetailerOrderDto> orderlist = new ArrayList<RetailerOrderDto>();
		List<SupplierProductWholesaleRange> productWholesaleRangeList = new ArrayList<SupplierProductWholesaleRange>();
		SupplierProductForOrderDTO supplierProductForOrderVo = new SupplierProductForOrderDTO();
		Long count = Long.parseLong(Orderqty);

		BigDecimal discount=null ;
		//获取一个pid下的所有订单的信息列表
		try {
			LOGGER.info("参数列表：pid===="+pid+"Orderqty="+Orderqty);
			orderlist=RemoteServiceSingleton.getInstance().getDealerOrderService().findAllRetailerOrderByPid
					(pid, Constants.STATUS31);

			if(orderlist!=null&&orderlist.size()>0){
				orderlist.get(0).getItemlist().get(0).getSkuId();
				supplierProductForOrderVo = orderlist.get(0).getSupplierProductForOrderVo();
				productWholesaleRangeList = supplierProductForOrderVo.getsupplierProductWholesaleRanges();
				if(productWholesaleRangeList!=null&&productWholesaleRangeList.size()>0){
					if(count!=null&&count!=0l){
						for (int i = 0; i < productWholesaleRangeList.size(); i++) {
							if(productWholesaleRangeList.get(i).getStartQty()<=count&&
									productWholesaleRangeList.get(i).getEndQty()>=count){
								discount = productWholesaleRangeList.get(i).getDiscount();
							}
							if(count>=productWholesaleRangeList.get((productWholesaleRangeList.size()-1)).getStartQty()&&productWholesaleRangeList.get((productWholesaleRangeList.size()-1)).getEndQty()==0){
								discount = productWholesaleRangeList.get((productWholesaleRangeList.size()-1)).getDiscount();
							}
							if(count<=productWholesaleRangeList.get(0).getStartQty()){
								discount = productWholesaleRangeList.get(0).getDiscount();
							}
						}
						response.getWriter().write(discount+"");
						request.setAttribute("discount", discount);	
						request.setAttribute("isRangeList", 1);	
					}else{
						response.getWriter().write(count+"");
					}
				}else {
					List<Long> orderIdList = new ArrayList<Long>();
					if(null!=orderIds&&!orderIds.equals("")){
						String [] idsa = orderIds.split(",");
						for (int i = 0; i < idsa.length; i++) {
							orderIdList.add(Long.parseLong(idsa[i]));
						}
						List<Map<String,Object>> retailerOrderSkuPriceList = RemoteServiceSingleton.getInstance().getDealerOrderService().getRetailerOrderSkuPriceList(orderIdList);
						JSONArray json = JSONArray.fromObject(retailerOrderSkuPriceList);
						response.getWriter().write(json.toString());
						//request.setAttribute("skuMap", RemoteServiceSingleton.getInstance().getDealerOrderService().getRetailerOrderSkuPriceList(orderIdList));
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("计算待合并订单接口异常异常   is  error   wait ",e);
		}
	}
}
*/