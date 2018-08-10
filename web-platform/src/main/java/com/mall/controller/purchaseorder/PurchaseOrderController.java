/*package com.mall.controller.purchaseorder;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import net.rubyeye.xmemcached.MemcachedClient;
import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.bean.authority.User;
import com.mall.dealer.product.dto.SkuCodeDTO;
import com.mall.mybatis.utility.PageBean;
import com.mall.stock.po.Warehouse;
import com.mall.supplier.model.Supplier;
import com.mall.supplier.order.dto.PChaseOrderDTO;
import com.mall.supplier.order.dto.PChaseOrderItemDTO;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;


*//**.
 * 订单Controller
 * @author zhouzb
 *
 *//*


@Controller
@RequestMapping(value="/purchaseorder")
public class PurchaseOrderController extends BaseController{
	*//**
	 * LOGGER.
	 *//*
	private static final Log LOGGER = LogFactory.getLogger(PurchaseOrderController.class);
	*//**
	 * Sping注入mencached缓存客户端.
	 *//*
	@Autowired
	private  MemcachedClient memCachedClient;  

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
	
	*//**
	 * 跳转现货订单页面.
	 * @return String
	 *//*
	@RequestMapping(value="/getPurchaseOrder",method=RequestMethod.GET)
	public final String getPurchaseOrder(Model model){
		
		List<Warehouse> warehouseEnum  = new ArrayList<Warehouse>();
		
		try {

			warehouseEnum = RemoteServiceSingleton.getInstance().getStockWofeService().findWarehouseEnum(null);
			
		} catch (Exception e) {
			
			LOGGER.error("调用仓库服务异常."+e.getMessage(),e);
			
		}
		
		model.addAttribute("warehouseEnum", warehouseEnum);
		
		return "/dealerseller/purchaseorder/purchaseList";
		
	}
	
	
	
	*//**
	 * 查询现货订单列表.
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param dto RetailerOrderDto
	 * @param statusToList String
	 * @param page Integer
	 * @return String
	 *//*
	@RequestMapping(value="/getPurchaseOrder",method=RequestMethod.POST)
	public final String  getOrderListByAllCondition(
			HttpServletRequest request , PageBean<PChaseOrderDTO> pageBean,PChaseOrderDTO pChaseOrderDTO
			){
		
		try {
			
			String supplierName = pChaseOrderDTO.getSupplierName();
			
			if (null != supplierName && !supplierName.equals("")){
			
				List<Supplier> findAllSupplier = RemoteServiceSingleton.getInstance().getSupplierManagerService().findAllSupplier(supplierName);
				
				List<Long> supplierIds = new ArrayList<Long>();
				
				for (Supplier supplier:findAllSupplier) {
					
					Long supplierId = supplier.getSupplierId();
					supplierIds.add(supplierId);
					
				}
				
				pChaseOrderDTO.setSupplierIds(supplierIds);
			
			}
			pageBean.setParameter(pChaseOrderDTO);
			
			pageBean = RemoteServiceSingleton.getInstance().getPlatFormPChaseOrderService().selectByDynamicListPage(pageBean);
		
			
		} catch (Exception e) {
			
			LOGGER.error("用户:"+getCurrentUser().getUsername());
			LOGGER.error("用户ID:"+getCurrentUser().getId());
	    		
			LOGGER.error("获取采购单列表,错误信息:"+e.getMessage(),e);
		}

		request.getSession().setAttribute("pb",pageBean);
		
		return "/dealerseller/purchaseorder/modelpage/purchaseorder_model";
		
	}
	
	
	*//**
	 * @param model Model
	 * @param shipOrderId 采购单ID
	 * @param target 1 添加 2 修改 3 查看
	 * @return
	 *//*
	@RequestMapping(value="/addpurchaseorder")
	public String addPurchaseOrder(Model model,Long shipOrderId,String target){
		
		List<Warehouse> findWarehouseEnum = new ArrayList<Warehouse>();
		PChaseOrderDTO orderDetailById = new PChaseOrderDTO();
		
		try {
			if(target.equals("1")){
				
				findWarehouseEnum = RemoteServiceSingleton.getInstance().getStockWofeService().findWarehouseEnum(null);
				orderDetailById.setId(0l);
				orderDetailById.setCreateTime(new Date());
				
			}else{
				
				orderDetailById = RemoteServiceSingleton.getInstance().getPlatFormPChaseOrderService().findOrderDetailById(shipOrderId);
		
			}
		} catch (Exception e) {

			LOGGER.error(e.getMessage(),e);
				
		}
		///FIXME user返回到页面处理
		User currentUser = getCurrentUser();
		
		model.addAttribute("target",target);
		model.addAttribute("pChaseOrderDTO",orderDetailById);
		model.addAttribute("warehouse",findWarehouseEnum);
		model.addAttribute("currentUser",currentUser);
		
		return "/dealerseller/purchaseorder/addPurchase";
	}
	
	@RequestMapping(value="/getProductSKUBySupplierId")
	@ResponseBody
	public String getProductSKUBySupplierId(Long supplierId){
		
		List<SkuCodeDTO> skus = new ArrayList<SkuCodeDTO>();
		
		try {

			skus = RemoteServiceSingleton.getInstance().getDealerProductService().findSkuDtosBySupplierId(supplierId);
			
			if(null != skus && skus.size()>0){
				
				for (int i = 0; i < skus.size(); i++) {
				
					skus.get(i).setSkuIdValue(skus.get(i).getSkuId()+"");
					
				}
				
			}
			
		} catch (Exception e) {
			
			LOGGER.error(e.getMessage(),e);
			
		}
		
		JSONArray json = JSONArray.fromObject(skus);
		
		return json.toString();
	}
	
	
	@RequestMapping(value="/addpurchaseorder",method=RequestMethod.POST)
	@ResponseBody
	public String savePurchaseOrder(Long id,String supplierName,Long supplierId,Integer warehouseCode,String warehouseName,String createBy,Date createTime,String receiveName,String receiveAddress,String receivePhone,
			BigDecimal internationalFreightRmb, BigDecimal inlandFreight,BigDecimal customsClearanceFee, BigDecimal labelRegistrationFee,BigDecimal tagLabelingFee, BigDecimal serviceFee,
			Long[] productid,Long[] skuId,String[] skuNameCn,Long[] qty,Long sumQty,BigDecimal[] skuPriceRmb,BigDecimal[] dutyRate,BigDecimal[] consumptionTaxRate,BigDecimal[] valueAddedTaxRate){
	
		String savestat = Constants.SUCCESS;
		
		PChaseOrderDTO pChaseOrderDTO = new PChaseOrderDTO();
		
		pChaseOrderDTO.setSupplierName(supplierName);
		pChaseOrderDTO.setSupplierId(supplierId);
		pChaseOrderDTO.setWarehouseName(warehouseName);
		pChaseOrderDTO.setWarehouseCode(warehouseCode);
		pChaseOrderDTO.setWarehouseCodeChar(warehouseCode+"");
		pChaseOrderDTO.setCreateBy(createBy);
		pChaseOrderDTO.setCreateTime(createTime);
		pChaseOrderDTO.setReceiveName(receiveName);
		pChaseOrderDTO.setReceiveAddress(receiveAddress);
		pChaseOrderDTO.setReceivePhone(receivePhone);
		pChaseOrderDTO.setInternationalFreightRmb(internationalFreightRmb);
		pChaseOrderDTO.setInternationalFreight(internationalFreightRmb);
//		pChaseOrderDTO.setInlandFreight(inlandFreight);
		pChaseOrderDTO.setCustomsClearanceFee(customsClearanceFee);
		pChaseOrderDTO.setLabelRegistrationFee(labelRegistrationFee);
		pChaseOrderDTO.setTagLabelingFee(tagLabelingFee);
		pChaseOrderDTO.setServiceFee(serviceFee);
		pChaseOrderDTO.setQty(sumQty);
		pChaseOrderDTO.setExchangeRate(new BigDecimal(1));
		
		List<PChaseOrderItemDTO> pCOItemList  =new  ArrayList<PChaseOrderItemDTO>();
		
		for (int i = 0; i < skuId.length; i++) {
			
			PChaseOrderItemDTO pChaseOrderItemDTO = new PChaseOrderItemDTO();
			
			pChaseOrderItemDTO.setSkuId(skuId[i]);
			pChaseOrderItemDTO.setPid(productid[i]);
			if (skuNameCn[i].indexOf("_") != -1){
				pChaseOrderItemDTO.setPname(skuNameCn[i].split("_")[0]);
				pChaseOrderItemDTO.setSkuNameCn(skuNameCn[i].split("_")[1]);
			}else{
				pChaseOrderItemDTO.setSkuNameCn(skuNameCn[i]);
			}
//			pChaseOrderItemDTO.setQty(qty[i]);
			pChaseOrderItemDTO.setSkuPriceRmb(skuPriceRmb[i]);
			pChaseOrderItemDTO.setSkuPrice(skuPriceRmb[i]);
			pChaseOrderItemDTO.setDutyRate(dutyRate[i]);
			pChaseOrderItemDTO.setDutiesRmb(skuPriceRmb[i].multiply(new BigDecimal(qty[i])).multiply(dutyRate[i]).divide(new BigDecimal(100), 4,BigDecimal.ROUND_UP).setScale(4));
			pChaseOrderItemDTO.setDuties(skuPriceRmb[i].multiply(new BigDecimal(qty[i])).multiply(dutyRate[i]).multiply(pChaseOrderDTO.getExchangeRate().divide(new BigDecimal(100), 4,BigDecimal.ROUND_UP)).setScale(4));
			pChaseOrderItemDTO.setConsumptionTax(skuPriceRmb[i].multiply(new BigDecimal(qty[i])).multiply(consumptionTaxRate[i]).multiply(pChaseOrderDTO.getExchangeRate().divide(new BigDecimal(100), 4,BigDecimal.ROUND_UP)).setScale(4));
			pChaseOrderItemDTO.setConsumptionTaxRate(consumptionTaxRate[i]);
			pChaseOrderItemDTO.setConsumptionTaxRmb(skuPriceRmb[i].multiply(new BigDecimal(qty[i])).multiply(consumptionTaxRate[i]).divide(new BigDecimal(100), 4,BigDecimal.ROUND_UP).setScale(4));
			pChaseOrderItemDTO.setValueAddedTax(skuPriceRmb[i].multiply(new BigDecimal(qty[i])).multiply(valueAddedTaxRate[i]).multiply(pChaseOrderDTO.getExchangeRate().divide(new BigDecimal(100), 4,BigDecimal.ROUND_UP)).setScale(4));
			pChaseOrderItemDTO.setValueAddedTaxRate(valueAddedTaxRate[i]);
			pChaseOrderItemDTO.setValueAddedTaxRmb(skuPriceRmb[i].multiply(new BigDecimal(qty[i])).multiply(valueAddedTaxRate[i]).divide(new BigDecimal(100), 4,BigDecimal.ROUND_UP).setScale(4));
			
			pCOItemList.add(pChaseOrderItemDTO);
			
		}

		try {
			
			pChaseOrderDTO.setpCOItemList(pCOItemList);
		
			if (null != id && id!=0){
				
				pChaseOrderDTO.setId(id);
				RemoteServiceSingleton.getInstance().getPlatFormPChaseOrderService().updatePChaseOrderbyDTO(pChaseOrderDTO);
		
			}else{
				
				RemoteServiceSingleton.getInstance().getPlatFormPChaseOrderService().insertSelective(pChaseOrderDTO);
		
			}
			
		} catch (Exception e) {
			
			savestat = Constants.ERROR;
			LOGGER.error(e.getMessage(),e);
			
		}
		
		
		return savestat;
	}
	
	*//**
	 * 确认采购单
	 * @param orderId
	 * @return
	 *//*
	@RequestMapping(value="/confirmOrder")
	@ResponseBody
	public String confirmOrder(Long orderId){
		
		String savestat = Constants.SUCCESS;
		
		try {
			PChaseOrderDTO pChaseOrderDTO = new PChaseOrderDTO();
			pChaseOrderDTO.setStatus((short) 10);
			pChaseOrderDTO.setId(orderId);
			
			RemoteServiceSingleton.getInstance().getPlatFormPChaseOrderService().updateStatusById(pChaseOrderDTO);
			
		} catch (Exception e) {
			savestat = Constants.ERROR;
			LOGGER.error(e.getMessage(),e);
		}
		
		return savestat;
	}
	
	*//**
	 * 删除采购单
	 * @param orderId
	 * @return
	 *//*
	@RequestMapping(value="/delorder")
	@ResponseBody
	public String delorder(Long orderId){
		
		String savestat = Constants.SUCCESS;
		
		try {
			
			RemoteServiceSingleton.getInstance().getPlatFormPChaseOrderService().deleteByPrimaryKey(orderId);
			
		} catch (Exception e) {
			savestat = Constants.ERROR;
			LOGGER.error(e.getMessage(),e);
		}
		
		return savestat;
	}
	
}
*/