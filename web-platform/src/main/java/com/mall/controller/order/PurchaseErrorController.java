/*package com.mall.controller.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.retailer.order.common.PKUtils;
import com.mall.retailer.order.po.Dictionarys;
import com.mall.supplier.order.api.rpc.InfrastructureService;
import com.mall.supplier.order.api.rpc.PChaseOrderServiceRPC;
import com.mall.supplier.order.api.rpc.PurchaseErrorService;
import com.mall.supplier.order.dto.PChaseOrderItemDTO;
import com.mall.supplier.order.dto.PurchaseErrorDto;
import com.mall.platform.proxy.RemoteServiceSingleton;

@Controller
@RequestMapping(value="/purchaseError")
public class PurchaseErrorController {

	@RequestMapping(value = "/findPurchaseError")
	public String findExpressMgs(Long purchaseId,Long skuId,Model model){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("purid", purchaseId);
		map.put("skuid", skuId);
		PChaseOrderServiceRPC pChaseOrderServiceRPC=(PChaseOrderServiceRPC)RemoteServiceSingleton.getInstance().getAppService("pChaseOrderServiceRPC");
		PChaseOrderItemDTO itemDTO=pChaseOrderServiceRPC.findItemDtoByPurIdAndSkuId(purchaseId, skuId);
		PurchaseErrorService purchaseErrorService=(PurchaseErrorService)RemoteServiceSingleton.getInstance().getAppService("purchaseErrorService");
		List<PurchaseErrorDto> errorDtos=purchaseErrorService.getPurchaseErrorList(map);
		
		model.addAttribute("errorDtos", errorDtos);
		model.addAttribute("itemDTO", itemDTO);
		return "/purchaseError/findPurchaseError";
	}
	@RequestMapping(value = "/selectPurchaseError")
	public String selectPurchaseError(Long purchaseId,Long skuId,Model model){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("purid", purchaseId);
		map.put("skuid", skuId);
		PChaseOrderServiceRPC pChaseOrderServiceRPC=(PChaseOrderServiceRPC)RemoteServiceSingleton.getInstance().getAppService("pChaseOrderServiceRPC");
		PChaseOrderItemDTO itemDTO=pChaseOrderServiceRPC.findItemDtoByPurIdAndSkuId(purchaseId, skuId);
		PurchaseErrorService purchaseErrorService=(PurchaseErrorService)RemoteServiceSingleton.getInstance().getAppService("purchaseErrorService");
		List<PurchaseErrorDto> errorDtos=purchaseErrorService.getPurchaseErrorList(map);
		
		model.addAttribute("errorDtos", errorDtos);
		model.addAttribute("itemDTO", itemDTO);
		return "/purchaseError/selectPurchaseError";
	}
	
	@RequestMapping(value = "/doSave")
	@ResponseBody
	public String doSave(HttpServletRequest request,HttpServletResponse response,Model model){
		try {
			String businessNumberChar=request.getParameter("businessNumberChar");
			PurchaseErrorService purchaseErrorService=(PurchaseErrorService)RemoteServiceSingleton.getInstance().getAppService("purchaseErrorService");
			String pcode=request.getParameter("pcode");
			String barCode=request.getParameter("barCode");
			String pname=request.getParameter("pname");
			String orderQty=request.getParameter("orderQty");
			String stockInQty=request.getParameter("stockInQty");
			String purchaseId=request.getParameter("purchaseId");
			String skuId=request.getParameter("skuId");
			String[] chkTrues=request.getParameter("chkTrue").split(",");
			String[] errorCode=request.getParameterValues("errorCode");
			String[] errorName=request.getParameterValues("errorName");
			String[] errorQty=request.getParameterValues("errorQty");
			purchaseErrorService.deleteByPrimaryKey(Long.valueOf(purchaseId));
			for(int i=0;i<errorCode.length;i++)
			{
				if(chkTrues[i].equals("1"))
				{
					PurchaseErrorDto dto=new PurchaseErrorDto();
					dto.setSid(PKUtils.getLongPrimaryKey());
					dto.setBarCode(barCode);
					dto.setBusinessNumberChar(businessNumberChar);
					dto.setErrorCode(errorCode[i]);
					dto.setErrorName(errorName[i]);
					dto.setErrorQty(Integer.parseInt(errorQty[i]));
					dto.setPcode(pcode);
					dto.setPname(pname);
					dto.setPurchaseId(Long.valueOf(purchaseId));
					dto.setSkuId(Long.valueOf(skuId));
					dto.setStockInQty(Integer.parseInt(stockInQty));
					dto.setOrderQty(Integer.parseInt(orderQty));
					purchaseErrorService.insertSelective(dto);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "OK";
	}
}
*/