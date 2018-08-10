/*package com.mall.controller.order;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.mybatis.utility.PageBean;
import com.mall.retailer.order.common.PKUtils;
import com.mall.retailer.order.po.Dictionarys;
import com.mall.supplier.order.api.rpc.InvoiceOrderService;
import com.mall.supplier.order.api.rpc.WaterService;
import com.mall.supplier.order.dto.InvoiceOrderDto;
import com.mall.supplier.order.dto.InvoiceOrderItemDto;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;

@Controller
@RequestMapping(value = "/Invoice")
public class InvoiceOrderController extends BaseController{
	@RequestMapping(value = "/findInvoiceOrder")
	public String findInvoiceOrder(Model model)
	{
		List<Dictionarys> purchaseEntity = RemoteServiceSingleton.getInstance().getInfrastructureService().findPurchasingUnitByAll();
		model.addAttribute("Entity", purchaseEntity);
		return "/Invoice/InvoiceOrder";
	}
	@RequestMapping(value = "/saveInvoiceOrder")
	public String saveInvoiceOrder(Model model)
	{
		List<Dictionarys> purchaseEntity = RemoteServiceSingleton.getInstance().getInfrastructureService().findPurchasingUnitByAll();
		model.addAttribute("Entity", purchaseEntity);
		return "/Invoice/saveInvoiceOrder";
	}
	
	@RequestMapping(value = "/findFullOrderSku")
	public String findFullOrderSku(Model model,HttpServletRequest request)
	{
		InvoiceOrderService invoiceOrderService=(InvoiceOrderService)RemoteServiceSingleton.getInstance().getAppService("invoiceOrderService");
		List<Dictionarys> purchaseEntity = RemoteServiceSingleton.getInstance().getInfrastructureService().findPurchasingUnitByAll();
		String purchase_entity=request.getParameter("purchase_entity");
		String supplier_name=request.getParameter("supplier_name");
		String business_number_char=request.getParameter("business_number_char");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		String sku_name_cn=request.getParameter("sku_name_cn");
		String account_type=request.getParameter("account_type");
		String payment_function=request.getParameter("payment_function");
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("purchase_entity", purchase_entity);
		map.put("supplier_name", supplier_name);
		map.put("business_number_char", business_number_char);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("sku_name_cn", sku_name_cn);
		map.put("account_type", account_type);
		map.put("payment_function", payment_function);
		List<Map<String, Object>> maps=invoiceOrderService.findFullOrderSku(map);
		model.addAttribute("Entity", purchaseEntity);
		model.addAttribute("maps", maps);
		return "/Invoice/findFullOrderSku";
	}
	@RequestMapping(value = "/InvoiceReg")
	public String InvoiceReg(Model model,HttpServletRequest request)
	{
		
//		String values=request.getParameter("values");
//		String []array=values.split(":");
//		String payment_function=request.getParameter("payment_function");
//		StringBuffer ids=new StringBuffer();
//		StringBuffer skus=new StringBuffer();
//		StringBuffer batchs=new StringBuffer();
//		
//		if("001".equals(payment_function))
//		{
//			for(int i=0;i<array.length;i++)
//			{
//				ids.append(array[i].split(",")[0]).append(",");
//				skus.append(array[i].split(",")[1]).append(",");
//			}
//		}
//		if("002".equals(payment_function))
//		{
//			for(int i=0;i<array.length;i++)
//			{
//				ids.append(array[i].split(",")[0]).append(",");
//				skus.append(array[i].split(",")[1]).append(",");
//				batchs.append(array[i].split(",")[2]).append(",");
//			}
//			
//		}
//		
//
        List<Dictionarys> purchaseEntity = RemoteServiceSingleton.getInstance().getInfrastructureService().findPurchasingUnitByAll();
		java.text.SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		java.text.SimpleDateFormat now=new SimpleDateFormat("yyyy-MM-dd");
		WaterService waterService=(WaterService)RemoteServiceSingleton.getInstance().getAppService("waterService");
		String code="FD"+format.format(new Date())+waterService.getWater("FD");
		model.addAttribute("code", code);
		model.addAttribute("now", now.format(new Date()));
		model.addAttribute("user", this.getCurrentUser().getUsername());
		model.addAttribute("Entity", purchaseEntity);
		return "/Invoice/InvoiceReg";
	}
	@RequestMapping(value = "/InvoiceUpt")
	public String InvoiceUpt(Model model,HttpServletRequest request)
	{
		String sid=request.getParameter("sid");
		InvoiceOrderService invoiceOrderService=(InvoiceOrderService)RemoteServiceSingleton.getInstance().getAppService("invoiceOrderService");
		InvoiceOrderDto orderDto=invoiceOrderService.selectInvoiceOrderByID(Long.valueOf(sid));
		model.addAttribute("orderDto", orderDto);
		return "/Invoice/InvoiceUpt";
	}
	@RequestMapping(value = "/InvoiceSelect")
	public String InvoiceSelect(Model model,HttpServletRequest request)
	{
		String sid=request.getParameter("sid");
		InvoiceOrderService invoiceOrderService=(InvoiceOrderService)RemoteServiceSingleton.getInstance().getAppService("invoiceOrderService");
		InvoiceOrderDto orderDto=invoiceOrderService.selectInvoiceOrderByID(Long.valueOf(sid));
		model.addAttribute("orderDto", orderDto);
		return "/Invoice/InvoiceSelect";
	}
	@RequestMapping(value = "/findByBatchOrderSku")
	public String findByBatchOrderSku(Model model,HttpServletRequest request)
	{
		InvoiceOrderService invoiceOrderService=(InvoiceOrderService)RemoteServiceSingleton.getInstance().getAppService("invoiceOrderService");
		List<Dictionarys> purchaseEntity = RemoteServiceSingleton.getInstance().getInfrastructureService().findPurchasingUnitByAll();
		String purchase_entity=request.getParameter("purchase_entity");
		String supplier_name=request.getParameter("supplier_name");
		String business_number_char=request.getParameter("business_number_char");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		String sku_name_cn=request.getParameter("sku_name_cn");
		String account_type=request.getParameter("account_type");
		String payment_function=request.getParameter("payment_function");
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("purchase_entity", purchase_entity);
		map.put("supplier_name", supplier_name);
		map.put("business_number_char", business_number_char);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("sku_name_cn", sku_name_cn);
		map.put("account_type", account_type);
		map.put("payment_function", payment_function);
		List<Map<String, Object>> maps=invoiceOrderService.findByBatchOrderSku(map);
		model.addAttribute("Entity", purchaseEntity);
		model.addAttribute("maps", maps);
		return "/Invoice/findByBatchOrderSku";
	}
	@RequestMapping(value = "/findInvoiceOrderList")
	public String findInvoiceOrderList(HttpServletRequest request,Integer page,Model model)
	{
		java.text.SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		String companyCode=request.getParameter("companyCode");
		String invoiceNumber=request.getParameter("invoiceNumber");
		String invoiceChar=request.getParameter("invoiceChar");
		String supplierName=request.getParameter("supplierName");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		String status=request.getParameter("status");
		String invoiceType=request.getParameter("invoiceType");
		InvoiceOrderDto orderDto=new InvoiceOrderDto();
		InvoiceOrderService invoiceOrderService=(InvoiceOrderService)RemoteServiceSingleton.getInstance().getAppService("invoiceOrderService");
		PageBean<InvoiceOrderDto> pageBean=new PageBean<InvoiceOrderDto>();
		try {
			if(companyCode!=null&&!companyCode.equals(""))
			{
				orderDto.setCompanyCode(companyCode);
			}
			if(invoiceNumber!=null&&!invoiceNumber.equals(""))
			{
				orderDto.setInvoiceNumber(invoiceNumber);
			}
			if(invoiceChar!=null&&!invoiceChar.equals(""))
			{
				orderDto.setInvoiceChar(invoiceChar);
			}
			if(supplierName!=null&&!supplierName.equals(""))
			{
				orderDto.setSupplierName(supplierName);
			}
			if(startTime!=null&&!startTime.equals(""))
			{
				orderDto.setStartTime(format.parse(startTime));
			}
			if(endTime!=null&&!endTime.equals(""))
			{
				orderDto.setEndTime(format.parse(endTime));
			}
			if(status!=null&&!status.equals(""))
			{
				orderDto.setStatus(Integer.parseInt(status));
			}
			if(invoiceType!=null&&!invoiceType.equals(""))
			{
				orderDto.setInvoiceType(Integer.parseInt(invoiceType));
			}
			if(page!=null)
			{
				pageBean.setPage(page);
			}else{
				pageBean.setPage(1);
			}
			pageBean.setPageSize(10);
			pageBean.setParameter(orderDto);
			pageBean=invoiceOrderService.findInvoiceOrderListPage(pageBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("pb", pageBean);
		return "/Invoice/findInvoiceOrderList";
	}
	@RequestMapping(value = "/doSave")
	@ResponseBody
	public String doSave(HttpServletRequest request,HttpServletResponse response,Model model)
	{
		java.text.SimpleDateFormat timeformat=new SimpleDateFormat("yyyy-MM-dd");
		InvoiceOrderService invoiceOrderService=(InvoiceOrderService)RemoteServiceSingleton.getInstance().getAppService("invoiceOrderService");
		String purchase_entity=request.getParameter("purchase_entity");
		String purchase_name=request.getParameter("purchase_name");
		String invoiceChar=request.getParameter("invoiceChar");
		String createTime=request.getParameter("createTime");
		String createBy=request.getParameter("createBy");
		String supplierName=request.getParameter("supplierName");
		String supplierId=request.getParameter("supplierId");
		
		String payment_function=request.getParameter("payment_function");
		String invoiceNumber=request.getParameter("invoiceNumber");
		String isPay=request.getParameter("isPay");
		String remark=request.getParameter("remark");
		
		String invoiceTotalPrice=request.getParameter("invoiceTotalPrice");
		String invoiceTotalTax=request.getParameter("invoiceTotalTax");
		String invoiceLocalPrice=request.getParameter("invoiceLocalPrice");
		
		
		String[] businessNumberChar=request.getParameterValues("businessNumberChar");
		String[] pcode=request.getParameterValues("pcode");
		String[] pname=request.getParameterValues("pname");
		String[] barCode=request.getParameterValues("barCode");
		String[] format=request.getParameterValues("format");
		String[] unit=request.getParameterValues("unit");
		String[] purchaseQty=request.getParameterValues("purchaseQty");
		String[] receivedQty=request.getParameterValues("receivedQty");
		String[] payQty=request.getParameterValues("payQty");
		String[] receivedPrice=request.getParameterValues("receivedPrice");
		String[] localQty=request.getParameterValues("localQty");
		String[] price=request.getParameterValues("price");
		String[] localTotalPrice=request.getParameterValues("localTotalPrice");
		String[] tax=request.getParameterValues("tax");
		String[] taxPrice=request.getParameterValues("taxPrice");
		String[] totalPrice=request.getParameterValues("totalPrice");
		String[] batchNumber=request.getParameterValues("batchNumber");
		String[] inStockTime=request.getParameterValues("inStockTime");
		String[] currencyType=request.getParameterValues("currencyType");
		String[] skuId=request.getParameterValues("skuId");
		String[] purchaseId=request.getParameterValues("purchaseId");
		InvoiceOrderDto invoiceOrderDto=new InvoiceOrderDto();
		try {
			invoiceOrderDto.setSid(PKUtils.getLongPrimaryKey());
			invoiceOrderDto.setBalanceType(payment_function);
			invoiceOrderDto.setCompanyCode(purchase_entity);
			invoiceOrderDto.setCompanyName(purchase_name);
			invoiceOrderDto.setCreateby(createBy);
			invoiceOrderDto.setCreateTime(new Date());
			invoiceOrderDto.setInvoiceChar(invoiceChar);
			invoiceOrderDto.setInvoiceLocalPrice(new BigDecimal(invoiceLocalPrice));
			invoiceOrderDto.setInvoiceNumber(invoiceNumber);
			invoiceOrderDto.setInvoiceTotalPrice(new BigDecimal(invoiceTotalPrice));
			invoiceOrderDto.setInvoiceTotalTax(new BigDecimal(invoiceTotalTax));
			invoiceOrderDto.setIsPay(Integer.parseInt(isPay));
			invoiceOrderDto.setRemark(remark);
			invoiceOrderDto.setStatus(1);
			invoiceOrderDto.setSupplierCode(supplierId);
			invoiceOrderDto.setSupplierName(supplierName);
			invoiceOrderDto.setInvoiceType(1);
			List<InvoiceOrderItemDto> itemDtos=new ArrayList<InvoiceOrderItemDto>();
			for(int i=0;i<businessNumberChar.length;i++)
			{
				InvoiceOrderItemDto dto=new InvoiceOrderItemDto();
				dto.setBarCode(barCode[i]);
				dto.setBatchNumber(batchNumber[i]);
				dto.setBusinessNumberChar(businessNumberChar[i]);
				dto.setCurrencyType(currencyType[i]);
				dto.setFormat(format[i]);
				dto.setInStockTime(timeformat.parse(inStockTime[i]));
				dto.setInvoiceId(invoiceOrderDto.getSid());
				dto.setLocalQty(Integer.parseInt(localQty[i]));
				dto.setLocalTotalPrice(new BigDecimal(localTotalPrice[i]));
				dto.setPayQty(Integer.parseInt(localQty[i]));
				dto.setPcode(pcode[i]);
				dto.setPname(pname[i]);
				dto.setPrice(new BigDecimal(price[i]));
				dto.setPurchaseId(Long.valueOf(purchaseId[i]));
				dto.setPurchaseQty(Integer.parseInt(purchaseQty[i]));
				dto.setReceivedPrice(new BigDecimal(localTotalPrice[i]));
				dto.setReceivedQty(Integer.parseInt(receivedQty[i]));
				dto.setSid(PKUtils.getLongPrimaryKey());
				dto.setSkuId(Long.valueOf(skuId[i]));
				dto.setTax(new BigDecimal(tax[i]));
				dto.setTaxPrice(new BigDecimal(taxPrice[i]));
				dto.setTotalPrice(new BigDecimal(totalPrice[i]));
				dto.setUnit(unit[i]);
				itemDtos.add(dto);
			}
			invoiceOrderDto.setItemDtos(itemDtos);
			invoiceOrderService.insert(invoiceOrderDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "OK";
	}
	@RequestMapping(value = "/doUpdate")
	@ResponseBody
	public String doUpdate(HttpServletRequest request,HttpServletResponse response,Model model)
	{
		java.text.SimpleDateFormat timeformat=new SimpleDateFormat("yyyy-MM-dd");
		InvoiceOrderService invoiceOrderService=(InvoiceOrderService)RemoteServiceSingleton.getInstance().getAppService("invoiceOrderService");
		String purchase_entity=request.getParameter("purchase_entity");
		String purchase_name=request.getParameter("purchase_name");
		String invoiceChar=request.getParameter("invoiceChar");
		String sid=request.getParameter("sid");
		String createBy=request.getParameter("createBy");
		String supplierName=request.getParameter("supplierName");
		String supplierId=request.getParameter("supplierId");
		
		String payment_function=request.getParameter("payment_function");
		String invoiceNumber=request.getParameter("invoiceNumber");
		String isPay=request.getParameter("isPay");
		String remark=request.getParameter("remark");
		
		String invoiceTotalPrice=request.getParameter("invoiceTotalPrice");
		String invoiceTotalTax=request.getParameter("invoiceTotalTax");
		String invoiceLocalPrice=request.getParameter("invoiceLocalPrice");
		
		
		String[] businessNumberChar=request.getParameterValues("businessNumberChar");
		String[] pcode=request.getParameterValues("pcode");
		String[] pname=request.getParameterValues("pname");
		String[] barCode=request.getParameterValues("barCode");
		String[] format=request.getParameterValues("format");
		String[] unit=request.getParameterValues("unit");
		String[] purchaseQty=request.getParameterValues("purchaseQty");
		String[] receivedQty=request.getParameterValues("receivedQty");
		String[] payQty=request.getParameterValues("payQty");
		String[] receivedPrice=request.getParameterValues("receivedPrice");
		String[] localQty=request.getParameterValues("localQty");
		String[] price=request.getParameterValues("price");
		String[] localTotalPrice=request.getParameterValues("localTotalPrice");
		String[] tax=request.getParameterValues("tax");
		String[] taxPrice=request.getParameterValues("taxPrice");
		String[] totalPrice=request.getParameterValues("totalPrice");
		String[] batchNumber=request.getParameterValues("batchNumber");
		String[] inStockTime=request.getParameterValues("inStockTime");
		String[] currencyType=request.getParameterValues("currencyType");
		String[] skuId=request.getParameterValues("skuId");
		String[] purchaseId=request.getParameterValues("purchaseId");
		InvoiceOrderDto invoiceOrderDto=new InvoiceOrderDto();
		try {
			invoiceOrderDto.setSid(Long.valueOf(sid));
			invoiceOrderDto.setBalanceType(payment_function);
			invoiceOrderDto.setCompanyCode(purchase_entity);
			invoiceOrderDto.setCompanyName(purchase_name);
			invoiceOrderDto.setCreateby(createBy);
			invoiceOrderDto.setCreateTime(new Date());
			invoiceOrderDto.setInvoiceChar(invoiceChar);
			invoiceOrderDto.setInvoiceLocalPrice(new BigDecimal(invoiceLocalPrice));
			invoiceOrderDto.setInvoiceNumber(invoiceNumber);
			invoiceOrderDto.setInvoiceTotalPrice(new BigDecimal(invoiceTotalPrice));
			invoiceOrderDto.setInvoiceTotalTax(new BigDecimal(invoiceTotalTax));
			invoiceOrderDto.setIsPay(Integer.parseInt(isPay));
			invoiceOrderDto.setRemark(remark);
			invoiceOrderDto.setStatus(1);
			invoiceOrderDto.setSupplierCode(supplierId);
			invoiceOrderDto.setSupplierName(supplierName);
			invoiceOrderDto.setInvoiceType(1);
			List<InvoiceOrderItemDto> itemDtos=new ArrayList<InvoiceOrderItemDto>();
			for(int i=0;i<businessNumberChar.length;i++)
			{
				InvoiceOrderItemDto dto=new InvoiceOrderItemDto();
				dto.setBarCode(barCode[i]);
				dto.setBatchNumber(batchNumber[i]);
				dto.setBusinessNumberChar(businessNumberChar[i]);
				dto.setCurrencyType(currencyType[i]);
				dto.setFormat(format[i]);
				dto.setInStockTime(timeformat.parse(inStockTime[i]));
				dto.setInvoiceId(invoiceOrderDto.getSid());
				dto.setLocalQty(Integer.parseInt(localQty[i]));
				dto.setLocalTotalPrice(new BigDecimal(localTotalPrice[i]));
				if(localQty[i].equals("0"))
				{
					dto.setPayQty(0);
					dto.setReceivedPrice(new BigDecimal(0));
				}else{
//					dto.setPayQty(Integer.valueOf(Integer.parseInt(payQty[i]) + Integer.parseInt(localQty[i])));
//					dto.setReceivedPrice(new BigDecimal(localTotalPrice[i]).add(new BigDecimal(receivedPrice[i])));
					dto.setPayQty(Integer.valueOf(localQty[i]));
					dto.setReceivedPrice(new BigDecimal(localTotalPrice[i]));
				}
				
				dto.setPcode(pcode[i]);
				dto.setPname(pname[i]);
				dto.setPrice(new BigDecimal(price[i]));
				dto.setPurchaseId(Long.valueOf(purchaseId[i]));
				dto.setPurchaseQty(Integer.parseInt(purchaseQty[i]));
				
				dto.setReceivedQty(Integer.parseInt(receivedQty[i]));
				dto.setSid(PKUtils.getLongPrimaryKey());
				dto.setSkuId(Long.valueOf(skuId[i]));
				dto.setTax(new BigDecimal(tax[i]));
				dto.setTaxPrice(new BigDecimal(taxPrice[i]));
				dto.setTotalPrice(new BigDecimal(totalPrice[i]));
				dto.setUnit(unit[i]);
				itemDtos.add(dto);
			}
			invoiceOrderDto.setItemDtos(itemDtos);
			invoiceOrderService.updateInvoiceOrder(invoiceOrderDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "OK";
	}
	@RequestMapping(value = "/doCheck")
	@ResponseBody
	public String doCheck(HttpServletRequest request,HttpServletResponse response,Model model)
	{
		java.text.SimpleDateFormat timeformat=new SimpleDateFormat("yyyy-MM-dd");
		InvoiceOrderService invoiceOrderService=(InvoiceOrderService)RemoteServiceSingleton.getInstance().getAppService("invoiceOrderService");
		String purchase_entity=request.getParameter("purchase_entity");
		String purchase_name=request.getParameter("purchase_name");
		String invoiceChar=request.getParameter("invoiceChar");
		String sid=request.getParameter("sid");
		String createBy=request.getParameter("createBy");
		String supplierName=request.getParameter("supplierName");
		String supplierId=request.getParameter("supplierId");
		
		String payment_function=request.getParameter("payment_function");
		String invoiceNumber=request.getParameter("invoiceNumber");
		String isPay=request.getParameter("isPay");
		String remark=request.getParameter("remark");
		
		String invoiceTotalPrice=request.getParameter("invoiceTotalPrice");
		String invoiceTotalTax=request.getParameter("invoiceTotalTax");
		String invoiceLocalPrice=request.getParameter("invoiceLocalPrice");
		
		
		String[] businessNumberChar=request.getParameterValues("businessNumberChar");
		String[] pcode=request.getParameterValues("pcode");
		String[] pname=request.getParameterValues("pname");
		String[] barCode=request.getParameterValues("barCode");
		String[] format=request.getParameterValues("format");
		String[] unit=request.getParameterValues("unit");
		String[] purchaseQty=request.getParameterValues("purchaseQty");
		String[] receivedQty=request.getParameterValues("receivedQty");
		String[] payQty=request.getParameterValues("payQty");
		String[] receivedPrice=request.getParameterValues("receivedPrice");
		String[] localQty=request.getParameterValues("localQty");
		String[] price=request.getParameterValues("price");
		String[] localTotalPrice=request.getParameterValues("localTotalPrice");
		String[] tax=request.getParameterValues("tax");
		String[] taxPrice=request.getParameterValues("taxPrice");
		String[] totalPrice=request.getParameterValues("totalPrice");
		String[] batchNumber=request.getParameterValues("batchNumber");
		String[] inStockTime=request.getParameterValues("inStockTime");
		String[] currencyType=request.getParameterValues("currencyType");
		String[] skuId=request.getParameterValues("skuId");
		String[] purchaseId=request.getParameterValues("purchaseId");
		InvoiceOrderDto invoiceOrderDto=new InvoiceOrderDto();
		try {
			invoiceOrderDto.setSid(Long.valueOf(sid));
			invoiceOrderDto.setBalanceType(payment_function);
			invoiceOrderDto.setCompanyCode(purchase_entity);
			invoiceOrderDto.setCompanyName(purchase_name);
			invoiceOrderDto.setCreateby(createBy);
			invoiceOrderDto.setCreateTime(new Date());
			invoiceOrderDto.setInvoiceChar(invoiceChar);
			invoiceOrderDto.setInvoiceLocalPrice(new BigDecimal(invoiceLocalPrice));
			invoiceOrderDto.setInvoiceNumber(invoiceNumber);
			invoiceOrderDto.setInvoiceTotalPrice(new BigDecimal(invoiceTotalPrice));
			invoiceOrderDto.setInvoiceTotalTax(new BigDecimal(invoiceTotalTax));
			invoiceOrderDto.setIsPay(Integer.parseInt(isPay));
			invoiceOrderDto.setRemark(remark);
			invoiceOrderDto.setStatus(10);
			invoiceOrderDto.setSupplierCode(supplierId);
			invoiceOrderDto.setSupplierName(supplierName);
			invoiceOrderDto.setInvoiceType(1);
			List<InvoiceOrderItemDto> itemDtos=new ArrayList<InvoiceOrderItemDto>();
			for(int i=0;i<businessNumberChar.length;i++)
			{
				InvoiceOrderItemDto dto=new InvoiceOrderItemDto();
				dto.setBarCode(barCode[i]);
				dto.setBatchNumber(batchNumber[i]);
				dto.setBusinessNumberChar(businessNumberChar[i]);
				dto.setCurrencyType(currencyType[i]);
				dto.setFormat(format[i]);
				dto.setInStockTime(timeformat.parse(inStockTime[i]));
				dto.setInvoiceId(invoiceOrderDto.getSid());
				dto.setLocalQty(Integer.parseInt(localQty[i]));
				dto.setLocalTotalPrice(new BigDecimal(localTotalPrice[i]));
				if("".equals(payQty[i])){
					payQty[i] = "0";
				}
				dto.setPayQty(Integer.parseInt(payQty[i]));
				dto.setPcode(pcode[i]);
				dto.setPname(pname[i]);
				dto.setPrice(new BigDecimal(price[i]));
				dto.setPurchaseId(Long.valueOf(purchaseId[i]));
				dto.setPurchaseQty(Integer.parseInt(purchaseQty[i]));
				dto.setReceivedPrice(new BigDecimal(receivedPrice[i]));
				dto.setReceivedQty(Integer.parseInt(receivedQty[i]));
				dto.setSid(PKUtils.getLongPrimaryKey());
				dto.setSkuId(Long.valueOf(skuId[i]));
				dto.setTax(new BigDecimal(tax[i]));
				dto.setTaxPrice(new BigDecimal(taxPrice[i]));
				dto.setTotalPrice(new BigDecimal(totalPrice[i]));
				dto.setUnit(unit[i]);
				itemDtos.add(dto);
			}
			invoiceOrderDto.setItemDtos(itemDtos);
			invoiceOrderService.updateInvoiceOrder(invoiceOrderDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "OK";
	}
}
*/