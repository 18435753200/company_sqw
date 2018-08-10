/*package com.mall.controller.purchaseorder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.dealer.product.customer.api.CustomerPromotionApi;
import com.mall.dealer.product.dto.SkuCodeDTO;
import com.mall.mybatis.utility.PageBean;
import com.mall.supplier.order.api.rpc.PChaseOrderServiceRPC;
import com.mall.supplier.order.api.rpc.StockInOrderItemService;
import com.mall.supplier.order.dto.PChaseOrderItemDTO;
import com.mall.supplier.order.dto.PChaseOrderVO;
import com.mall.supplier.order.dto.StockInOrderItemDto;
import com.mall.wms.api.PoService;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;

@Controller
@RequestMapping(value="/purchasebuy")
public class PurchaseBuyController {
	
	private static final Log LOGGER = LogFactory.getLogger(PurchaseBuyController.class);
	
	
	@RequestMapping(value="/findPurchaseBuy",method={RequestMethod.GET,RequestMethod.POST})
	public final String findPurchaseBuy(Model model){
		
		return "/purchasebuy/findPurchaseBuy";
		
	}
	@RequestMapping(value="/findPurchaseBuyList")
	public final String findPurchaseBuyList(Integer page,PChaseOrderItemDTO pChaseOrderItemDTO,Model model){
		PChaseOrderServiceRPC pChaseOrderServiceRPC=(PChaseOrderServiceRPC)RemoteServiceSingleton.getInstance().getAppService("pChaseOrderServiceRPC");
		//ShipOrderItemService shipOrderItemService=RemoteServiceSingleton.getInstance().getShipOrderItemService();
		//List<ShipOrderItemDto> shipOrderItemsDtos=shipOrderItemService.findShipOrderItemTotal(pChaseOrderItemDTO.getId());
		PageBean<PChaseOrderItemDTO> pageBean=new PageBean<PChaseOrderItemDTO>();
		if(page!=null)
		{
			pageBean.setPage(page);
		}else{
			pageBean.setPage(Constants.INT1);
		}
		pageBean.setPageSize(Constants.PAGESIZE);
		pageBean.setParameter(pChaseOrderItemDTO);
		pageBean=pChaseOrderServiceRPC.findItemDTOByCondition(pageBean);
		model.addAttribute("pb", pageBean);
		return "/purchasebuy/findPurchaseBuyList";
		
	}
	@RequestMapping(value = "/PChaseOrderList")
	public String PChaseOrderList(Long businessNumber,Model model){
		System.out.println(businessNumber);
		PChaseOrderServiceRPC pChaseOrderServiceRPC = (PChaseOrderServiceRPC)RemoteServiceSingleton.getInstance().getAppService("pChaseOrderServiceRPC");
		List<PChaseOrderVO> ChaseOrder=new ArrayList<PChaseOrderVO>();
		if(businessNumber!=null)
		{
			ChaseOrder=pChaseOrderServiceRPC.selectOrderById(businessNumber.toString());
		}else{
			ChaseOrder=pChaseOrderServiceRPC.selectOrderById("");
		}
		model.addAttribute("ChaseOrder", ChaseOrder);
		return "/shiporder/chaseordernew";	
	}
	
	@RequestMapping(value = "/ProductList")
	public String ProductList(String skuName,Model model){
		System.out.println(skuName);
		CustomerPromotionApi customerPromotionApi=(CustomerPromotionApi)RemoteServiceSingleton.getInstance().getAppService("customerPromotionApi");
		PageBean<SkuCodeDTO> pageBean=new PageBean<SkuCodeDTO>();
		pageBean.setPage(1);
		pageBean.setPageSize(10000);
		if(skuName!=null)
		{
			SkuCodeDTO dto=new SkuCodeDTO();
			dto.setSkuNameCn(skuName);
			pageBean.setParameter(dto);
		}else{
			SkuCodeDTO dto=new SkuCodeDTO();
			pageBean.setParameter(dto);
		}
		
		pageBean=customerPromotionApi.findPageSkuDto(pageBean);
		model.addAttribute("product", pageBean.getResult());
		return "/shiporder/Product";	
	}
	
	
	@RequestMapping(value="/findPurchaseBuyItemList",method={RequestMethod.GET,RequestMethod.POST})
	public final String findPurchaseBuyItemList(Long id,Long purchaseId,Model model){
		StockInOrderItemService stockInOrderItemService=(StockInOrderItemService)RemoteServiceSingleton.getInstance().getAppService("stockInOrderItemService");
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("business_number", purchaseId);
		map.put("sku_id", id);
		
		List<StockInOrderItemDto> inOrderItemDtos=stockInOrderItemService.selectStockInOrderListPage(map);
		model.addAttribute("pb", inOrderItemDtos);
		return "/purchasebuy/findPurchaseBuyItem";
		
	}
	@RequestMapping(value="/checkPurchaseError",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public final String checkPurchaseError(String id,Model model){
		
		
		
		return "OK";
	}
	@RequestMapping(value="/savePurchaseStatus",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public final String savePurchaseStatus(String id,Model model,HttpServletResponse response){
		PChaseOrderServiceRPC pChaseOrderServiceRPC=(PChaseOrderServiceRPC)RemoteServiceSingleton.getInstance().getAppService("pChaseOrderServiceRPC");
		PoService poService = (PoService)RemoteServiceSingleton.getInstance().getAppService("poService");
		String[] val=id.split(":");
		String purchaseId = null;
		List<String> purchaseIdList = new ArrayList<String>();
		StringBuffer purchaseIdBuffer = new StringBuffer();
		purchaseIdBuffer.append("purchaseId以,分隔:");
		for(int i=0;i<val.length;i++)
		{
			
			purchaseId=val[i].split(",")[0];
			String skuid=val[i].split(",")[1];
			pChaseOrderServiceRPC.updateItemHereStatusById(Long.parseLong(purchaseId), Long.parseLong(skuid));
			purchaseIdList.add(purchaseId);
			purchaseIdBuffer.append(purchaseId).append(",");
		}
		String resMsg = null;
		try {
			resMsg = poService.cancelPurchaseOrder(purchaseIdList);
		} catch (Exception e) {
			LOGGER.error("===========savePurchaseStatus 采购单到齐接口异常===================");
			return "采购单到齐失败，但已更新wofe采购单状态，请联系管理员进行数据补全操作!";
		}
		String loggerMsg = purchaseIdBuffer.append(resMsg).toString();
		LOGGER.info(loggerMsg);
//		if(resMsg == null){
//			return "OK";
//		}
		return resMsg;
	}
}
*/