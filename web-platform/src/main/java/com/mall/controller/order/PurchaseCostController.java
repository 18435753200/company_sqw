/*package com.mall.controller.order;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.customer.order.utils.PKUtils;
import com.mall.mybatis.utility.PageBean;
import com.mall.retailer.order.po.Dictionarys;
import com.mall.supplier.order.api.rpc.InfrastructureService;
import com.mall.supplier.order.api.rpc.InvoiceOrderService;
import com.mall.supplier.order.api.rpc.PChaseOrderServiceRPC;
import com.mall.supplier.order.api.rpc.PurchaseCostItemService;
import com.mall.supplier.order.api.rpc.PurchaseCostService;
import com.mall.supplier.order.api.rpc.SkuAllocationService;
import com.mall.supplier.order.api.rpc.WaterService;
import com.mall.supplier.order.dto.ConculateObject;
import com.mall.supplier.order.dto.InvoiceOrderDto;
import com.mall.supplier.order.dto.PChaseOrderItemDTO;
import com.mall.supplier.order.dto.PChaseOrderVO;
import com.mall.supplier.order.dto.PurchaseCostDto;
import com.mall.supplier.order.dto.PurchaseCostItemDto;
import com.mall.supplier.order.po.Cost;
import com.mall.supplier.order.po.Currency;
import com.mall.supplier.order.po.PurchaseCost;
import com.mall.supplier.order.po.SkuAllocation;
import com.mall.controller.base.BaseController;
import com.mall.controller.base.Result;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;

*//**
 * @ClassName PurchaseCostController
 * @Description: 分摊控制器
 * @author: SRlong
 * @update by：QIJJ
 * @since: 2015-7-16 下午6:38:45
 *//*
@Controller
@RequestMapping(value = "/purchasecost")
public class PurchaseCostController extends BaseController {

	private static final Log LOG = LogFactory.getLogger(PurchaseCostController.class);

	*//**
	 * @Description： 点击采购费用跳转页面
	 * @author: songRL
	 * @since: 2015-7-14 下午4:42:18
	 *//*
	@RequestMapping(value = "/findPurchaseCost")
	public String findOutStock(Model model) {
		List<Dictionarys> purchaseEntity = RemoteServiceSingleton.getInstance().getInfrastructureService().findPurchasingUnitByAll();
		model.addAttribute("Entity", purchaseEntity);

		return "/purchasecost/findCost";
	}
	@RequestMapping(value = "/InvoiceUpt")
	public String InvoiceUpt(Model model,Long sid) {
		InvoiceOrderService invoiceOrderService=(InvoiceOrderService)RemoteServiceSingleton.getInstance().getAppService("invoiceOrderService");
		PurchaseCostService purchaseCostService=(PurchaseCostService)RemoteServiceSingleton.getInstance().getAppService("purchaseCostService");
		InfrastructureService infrastructureService = RemoteServiceSingleton.getInstance().getInfrastructureService();
		List<Dictionarys> dictionarys = infrastructureService.goodsCostAllocation();
		InvoiceOrderDto orderDto=invoiceOrderService.selectInvoiceOrderByID(sid);
		System.out.println(orderDto);
		List<Map<String, Object>> maps=purchaseCostService.findPurchaseCostByID(sid);
		model.addAttribute("orderDto", orderDto);
		model.addAttribute("maps", maps);
		
		model.addAttribute("Function", dictionarys);
		return "/purchasecost/InvoiceUpt";
	}
	@RequestMapping(value = "/InvoiceSelect")
	public String InvoiceSelect(Model model,Long sid) {
		InvoiceOrderService invoiceOrderService=(InvoiceOrderService)RemoteServiceSingleton.getInstance().getAppService("invoiceOrderService");
		PurchaseCostService purchaseCostService=(PurchaseCostService)RemoteServiceSingleton.getInstance().getAppService("purchaseCostService");
		InfrastructureService infrastructureService = RemoteServiceSingleton.getInstance().getInfrastructureService();
		List<Dictionarys> dictionarys = infrastructureService.goodsCostAllocation();
		InvoiceOrderDto orderDto=invoiceOrderService.selectInvoiceOrderByID(sid);
		System.out.println(orderDto);
		List<Map<String, Object>> maps=purchaseCostService.findPurchaseCostByID(sid);
		model.addAttribute("orderDto", orderDto);
		model.addAttribute("maps", maps);
		
		model.addAttribute("Function", dictionarys);
		return "/purchasecost/InvoiceSelect";
	}
	@RequestMapping(value = "/setSelectCost")
	public String setSelectCost(Model model,HttpServletRequest request,String costCode,String functionCode,String sid,String invoiceTotalPrice) {
		List<Dictionarys> purchaseEntity = RemoteServiceSingleton.getInstance().getInfrastructureService().findPurchasingUnitByAll();
		InfrastructureService infrastructureService = RemoteServiceSingleton.getInstance().getInfrastructureService();
		PurchaseCostItemService purchaseCostItemService=(PurchaseCostItemService)RemoteServiceSingleton.getInstance().getAppService("purchaseCostItemService");
		List<Cost> costs = infrastructureService.findCost();
		List<Dictionarys> dictionarys = infrastructureService.goodsCostAllocation();
		String costName="";
		String functionText="";
		for(Cost cost:costs)
		{
			if(cost.getCode().equals(costCode))
			{
				costName=cost.getCostName();
				break;
			}
		}
		for(Dictionarys dd:dictionarys)
		{
			if(dd.getDictValue().equals(functionCode))
			{
				functionText=dd.getDictName();
				break;
			}
		}
		List<PurchaseCostItemDto> itemDtos=purchaseCostItemService.selectPurchaseCostItem(Long.valueOf(sid),costCode);
		model.addAttribute("Entity", purchaseEntity);
		model.addAttribute("itemDtos", itemDtos);
		model.addAttribute("costName", costName);
		model.addAttribute("costCode", costCode);
		model.addAttribute("functionCode", functionCode);
		model.addAttribute("functionText", functionText);
		model.addAttribute("sid", sid);
		model.addAttribute("invoiceTotalPrice", invoiceTotalPrice);
		return "/purchasecost/setSelectCost";
	}
	@RequestMapping(value = "/setUpCost")
	public String setUpCost(Model model,String costCode,String functionCode,String sid,String invoiceTotalPrice) {
		List<Dictionarys> purchaseEntity = RemoteServiceSingleton.getInstance().getInfrastructureService().findPurchasingUnitByAll();
		InfrastructureService infrastructureService = RemoteServiceSingleton.getInstance().getInfrastructureService();
		List<Cost> costs = infrastructureService.findCost();
		List<Dictionarys> dictionarys = infrastructureService.goodsCostAllocation();
		String costName="";
		String functionText="";
		for(Cost cost:costs)
		{
			if(cost.getCode().equals(costCode))
			{
				costName=cost.getCostName();
				break;
			}
		}
		for(Dictionarys dd:dictionarys)
		{
			if(dd.getDictValue().equals(functionCode))
			{
				functionText=dd.getDictName();
				break;
			}
		}
		model.addAttribute("Entity", purchaseEntity);
		model.addAttribute("costName", costName);
		model.addAttribute("costCode", costCode);
		model.addAttribute("functionCode", functionCode);
		model.addAttribute("functionText", functionText);
		model.addAttribute("sid", sid);
		model.addAttribute("invoiceTotalPrice", invoiceTotalPrice);
		return "/purchasecost/setUpCost";
	}
	@RequestMapping(value = "/selectPro")
	public String selectPro(Model model) {
		List<Dictionarys> purchaseEntity = RemoteServiceSingleton.getInstance().getInfrastructureService().findPurchasingUnitByAll();
		
		model.addAttribute("Entity", purchaseEntity);
		
		return "/purchasecost/selectPro";
	}
	@RequestMapping(value = "/selectProList")
	public String selectProList(Model model,HttpServletRequest request) {
		java.text.SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		PChaseOrderServiceRPC pChaseOrderServiceRPC=(PChaseOrderServiceRPC)RemoteServiceSingleton.getInstance().getAppService("pChaseOrderServiceRPC");
		String purchase_entity=request.getParameter("purchase_entity");
		String business_number_char=request.getParameter("business_number_char");
		String supplier_name=request.getParameter("supplier_name");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		String business_prodid=request.getParameter("business_prodid");
		String pname=request.getParameter("pname");
		String bar_code=request.getParameter("bar_code");
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			if(startTime!=null&&!startTime.equals(""))
			{
				map.put("startTime", format.parse(startTime));
			}
			if(endTime!=null&&!endTime.equals(""))
			{
				map.put("endTime", format.parse(endTime));
			}
			map.put("purchase_entity", purchase_entity);
			map.put("business_number_char", business_number_char);
			map.put("supplier_name", supplier_name);
			map.put("business_prodid", business_prodid);
			map.put("pname", pname);
			map.put("bar_code", bar_code);
			List<Map<String, Object>> maps=pChaseOrderServiceRPC.getShareMap(map);
			model.addAttribute("maps", maps);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/purchasecost/selectProList";
	}
	*//**
	 * @Description： 通过条件查询展示表格数据全部采购单
	 * @author: songRL
	 * @since: 2015-7-14 下午4:49:28
	 *//*
	@RequestMapping(value = "/findPurchaseCostList")
	public String findPurchaseCostList(HttpServletRequest request, Integer page, Model model) {
		java.text.SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String purchaseId = request.getParameter("purchaseId");
		String createBy = request.getParameter("createBy");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String supplierName = request.getParameter("supplierName");
		String totalPrice = request.getParameter("totalPrice");
		PChaseOrderVO arg0 = new PChaseOrderVO();

		try {
			if (StringUtils.isNotBlank(purchaseId)) {
				arg0.setId(Long.parseLong(purchaseId));
			}
			if (StringUtils.isNotBlank(createBy)) {
				arg0.setCreateBy(createBy);
			}
			if (StringUtils.isNotBlank(endTime)) {
				arg0.setCreateTime(format.parse(endTime));
			}
			if (StringUtils.isNotBlank(startTime)) {
				arg0.setCreateTime(format.parse(startTime));
			}
			if (StringUtils.isNotBlank(supplierName)) {
				arg0.setSupplierName(supplierName);
			}
			if (StringUtils.isNotBlank(totalPrice)) {
				arg0.setItemTotalPrice(new BigDecimal(totalPrice));
			}
			arg0.setOrderStatus((short) 10);
		} catch (Exception e) {
			e.printStackTrace();
		}

		PChaseOrderServiceRPC pChaseOrderServiceRPC = (PChaseOrderServiceRPC) RemoteServiceSingleton.getInstance().getAppService("pChaseOrderServiceRPC");

		PageBean<PChaseOrderVO> pageBean = new PageBean<PChaseOrderVO>();
		pageBean.setParameter(arg0);
		if (page != null) {
			pageBean.setPage(page);
		} else {
			pageBean.setPage(Constants.INT1);
		}
		pageBean.setPageSize(Constants.PAGESIZE);

		pageBean = pChaseOrderServiceRPC.selectByDynamicListPage(pageBean);
		model.addAttribute("pb", pageBean);

		return "/purchasecost/findPurchaseCostList";
	}

	*//**
	 * @Description： 跳转至分摊操作页面
	 * @author: songRL
	 * @since: 2015-7-14 下午5:03:34
	 *//*
	@RequestMapping(value = "/setCost")
	public String setCost(Model model, HttpServletRequest request) {
		InfrastructureService infrastructureService = RemoteServiceSingleton.getInstance().getInfrastructureService();
		List<Cost> costs = infrastructureService.findCost();
		List<Currency> currencies = infrastructureService.findCurrency();
		List<Dictionarys> dictionarys = infrastructureService.goodsCostAllocation();
		String ids = request.getParameter("ids");

		model.addAttribute("Cost", costs);
		model.addAttribute("ids", ids);
		model.addAttribute("Currency", currencies);
		model.addAttribute("Dictionarys", dictionarys);

		return "/purchasecost/setCost";
	}

	*//**
	 * @Description： 点击分摊跳转详细页面
	 * @author: songRL
	 * @since: 2015-7-14 下午5:45:15
	 *//*
	@RequestMapping(value = "/findSkuWindow")
	public String findSkuWindow(Model model, HttpServletRequest request) {
		PChaseOrderServiceRPC pChaseOrderServiceRPC = (PChaseOrderServiceRPC) RemoteServiceSingleton.getInstance().getAppService("pChaseOrderServiceRPC");
		String ids = request.getParameter("ids");
		String costCode = request.getParameter("costCode");
		String allocationCode = request.getParameter("allocationCode");
		String totalFee = request.getParameter("totalFee");
		String tax = request.getParameter("tax");
		String localPrice = request.getParameter("localPrice");
		String[] id = ids.split(",");

		InfrastructureService infrastructureService = RemoteServiceSingleton.getInstance().getInfrastructureService();
		List<Long> arg0 = new ArrayList<Long>();
		for (int i = 0; i < id.length; i++) {
			arg0.add(Long.valueOf(id[i]));
		}

		List<PChaseOrderItemDTO> itemDTOs = pChaseOrderServiceRPC.findItemDtosByPurIds(arg0);
		List<Cost> costs = infrastructureService.findCost();
		for (Cost cost : costs) {
			if (cost.getCode().equals(costCode)) {
				model.addAttribute("Cost", cost.getCostName());
			}
		}
		// List<Currency> currencies = infrastructureService.findCurrency();

		List<Dictionarys> dictionarys = infrastructureService.goodsCostAllocation();
		for (Dictionarys dict : dictionarys) {
			if (dict.getDictValue().equals(allocationCode)) {
				model.addAttribute("Dictionarys", dict.getDictName());
			}
		}
		model.addAttribute("costCode", costCode);
		model.addAttribute("itemDTOs", itemDTOs);
		model.addAttribute("allocationCode", allocationCode);
		model.addAttribute("totalFee", totalFee);
		model.addAttribute("tax", tax);
		model.addAttribute("localPrice", localPrice);
		model.addAttribute("ids", ids);

		return "/purchasecost/findSkuWindow";
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
		return "/purchasecost/findInvoiceOrderList";
	}
	*//**
	 * @Description： 点击计算分摊调用当前方法
	 * @author: songRL
	 * @since: 2015-7-14 下午5:48:06
	 *//*
	@RequestMapping(value = "/setallOcation", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Result setallOcation(HttpServletRequest request, HttpServletResponse response, Model model, String values) {
		Result rs = new Result();
		String function=request.getParameter("functionCode");
		String invoiceTotalPrice=request.getParameter("invoiceTotalPrice");
		String[] qty=request.getParameterValues("qty");
		String[] price=request.getParameterValues("localPrice");
		String[] sku_id=request.getParameterValues("sku_id");
		PChaseOrderServiceRPC pChaseOrderServiceRPC = (PChaseOrderServiceRPC) RemoteServiceSingleton.getInstance().getAppService("pChaseOrderServiceRPC");
		List<ConculateObject> list=getFee(function,qty,price,invoiceTotalPrice,sku_id);// 计算分摊金额

		Map<String, List<ConculateObject>> dataMap = new HashMap<String, List<ConculateObject>>();
		dataMap.put("dataMap", list);
		rs.setDataMap(dataMap);
		rs.setSuccess(true);

		return rs;
	}
	public List<ConculateObject> getFee(String function,String[] qtys,String[] moneys,String invoiceTotalPrice,String[] sku_id)
	{
		BigDecimal TotalPrice=new BigDecimal(invoiceTotalPrice);
		List<ConculateObject> conculateObjects=new ArrayList<ConculateObject>();
		if(function.equals("001"))
		{
			int qty=getTotalQty1(qtys);
			
			for(int i=0;i<qtys.length;i++)
			{
				ConculateObject Obj=new ConculateObject();
				Obj.setSkuId(Long.valueOf(sku_id[i]));
				BigDecimal b1 = new BigDecimal(qty);
		        BigDecimal b2 = new BigDecimal(qtys[i]);
				double vv=b2.divide(b1,4,BigDecimal.ROUND_HALF_EVEN).multiply(TotalPrice).doubleValue();
				Obj.setSharedFee(new BigDecimal(vv));
				conculateObjects.add(Obj);
			}
		}else if(function.equals("003")){
			BigDecimal totalPrice=getTotalPrice1(moneys);
			for(int i=0;i<moneys.length;i++)
			{
				ConculateObject Obj=new ConculateObject();
				Obj.setSkuId(Long.valueOf(sku_id[i]));
				
		        BigDecimal b2 = new BigDecimal(moneys[i]);
				double vv=b2.divide(totalPrice,4,BigDecimal.ROUND_HALF_EVEN).multiply(TotalPrice).doubleValue();
				Obj.setSharedFee(new BigDecimal(vv));
				conculateObjects.add(Obj);
			}
		}else if(function.equals("002"))
		{
			BigDecimal decimal=new BigDecimal(sku_id.length);
			double vv=TotalPrice.divide(decimal,4,BigDecimal.ROUND_HALF_EVEN).doubleValue();
			for(int i=0;i<sku_id.length;i++)
			{
				ConculateObject Obj=new ConculateObject();
				Obj.setSkuId(Long.valueOf(sku_id[i]));
				Obj.setSharedFee(new BigDecimal(vv));
				conculateObjects.add(Obj);
			}
		}
		return conculateObjects;
		
	}
	public Integer getTotalQty1(String[] qtys)
	{
		int sum=0;
		for(String str:qtys)
		{
			if(str==null&&str.equals(""))
			{
				str="0";
			}
			sum+=Integer.parseInt(str);
		}
		return sum;
	}
	public BigDecimal getTotalPrice1(String[] moneys)
	{
		BigDecimal sum=new BigDecimal(0);
		for(String str:moneys)
		{
			BigDecimal n1=new BigDecimal(str);
			sum=sum.add(n1);
		}
		return sum;
	}
	*//**
	 * @Description： 保存当前分摊明细
	 * @author: QIJJ
	 * @since: 2015-7-9 下午4:04:15
	 *//*
	@RequestMapping(value = "/saveSkuAllocation", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String saveSkuAllocation(HttpServletRequest request, HttpServletResponse response) {
		LOG.info("Go into SkuAllocation - saveSkuAllocation");
		PurchaseCostItemService purchaseCostItemService=(PurchaseCostItemService)RemoteServiceSingleton.getInstance().getAppService("purchaseCostItemService");
		String invoiceId=request.getParameter("sid");
		String costCode=request.getParameter("costCode");
		String[] business_number=request.getParameterValues("business_number");
		String[] pcode=request.getParameterValues("pcode");
		String[] barCode=request.getParameterValues("barCode");
		String[] pname=request.getParameterValues("pname");
		String[] qty=request.getParameterValues("qty");
		String[] currency_type=request.getParameterValues("currency_type");
		String[] price=request.getParameterValues("price");
		String[] localPrice=request.getParameterValues("localPrice");
		String[] allocationPrice=request.getParameterValues("allocationPrice");
		String[] sku_id=request.getParameterValues("sku_id");
		List<PurchaseCostItemDto> dtos=new ArrayList<PurchaseCostItemDto>();
		for(int i=0;i<business_number.length;i++)
		{
			PurchaseCostItemDto dto=new PurchaseCostItemDto();
			dto.setBarCode(barCode[i]);
			dto.setCostCode(costCode);
			dto.setBusinessNumberChar(business_number[i]);
			dto.setCurrencyType(currency_type[i]);
			dto.setInvoiceId(Long.valueOf(invoiceId));
			dto.setLocalQty(Integer.valueOf(qty[i]));
			dto.setLocalTotalPrice(new BigDecimal(localPrice[i]));
			dto.setPcode(pcode[i]);
			dto.setPname(pname[i]);
			dto.setSharePrice(new BigDecimal(allocationPrice[i]));
			dto.setSid(PKUtils.getLongPrimaryKey());
			dto.setSkuId(Long.valueOf(sku_id[i]));
			dto.setTotalPrice(new BigDecimal(price[i]));
			dtos.add(dto);
		}
		purchaseCostItemService.updatePurchaseCostItem(dtos, Long.valueOf(invoiceId),costCode);
		return "OK";
	}
	@RequestMapping(value = "/doSave", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String doSave(HttpServletRequest request, HttpServletResponse response) {
		LOG.info("Go into SkuAllocation - saveSkuAllocation");
		InvoiceOrderService invoiceOrderService=(InvoiceOrderService)RemoteServiceSingleton.getInstance().getAppService("invoiceOrderService");
		PurchaseCostService purchaseCostService=(PurchaseCostService)RemoteServiceSingleton.getInstance().getAppService("purchaseCostService");
		String purchase_entity=request.getParameter("purchase_entity");
		String purchase_name=request.getParameter("purchase_name");
		String invoiceChar=request.getParameter("invoiceChar");
		String createBy=request.getParameter("createBy");
		String supplierName=request.getParameter("supplierName");
		String supplierId=request.getParameter("supplierId");
		String sid=request.getParameter("sid");
		String invoiceNumber=request.getParameter("invoiceNumber");
		String remark=request.getParameter("remark");
		String invoiceTotalPrice=request.getParameter("invoiceTotalPrice");
		
		String[] costName=request.getParameterValues("costName");
		String[] costCode=request.getParameterValues("costCode");
		String[] allocationQty=request.getParameterValues("allocationQty");
		String[] localPrice=request.getParameterValues("localPrice");
		String[] localTotalPrice=request.getParameterValues("localTotalPrice");
		String[] exchangeRate=request.getParameterValues("exchangeRate");
		String[] allocationPrice=request.getParameterValues("allocationPrice");
		String[] allocationCode=request.getParameterValues("allocationCode");
		String[] chk=request.getParameter("chk").split(",");
		InvoiceOrderDto invoiceOrderDto=new InvoiceOrderDto();
		invoiceOrderDto.setBalanceType("001");
		invoiceOrderDto.setCompanyCode(purchase_entity);
		invoiceOrderDto.setCompanyName(purchase_name);
		invoiceOrderDto.setCreateby(createBy);
		invoiceOrderDto.setCreateTime(new Date());
		invoiceOrderDto.setInvoiceChar(invoiceChar);
		invoiceOrderDto.setInvoiceTotalPrice(new BigDecimal(invoiceTotalPrice));
		invoiceOrderDto.setInvoiceNumber(invoiceNumber);
		invoiceOrderDto.setInvoiceType(2);
		invoiceOrderDto.setRemark(remark);
		invoiceOrderDto.setSid(Long.valueOf(sid));
		invoiceOrderDto.setStatus(1);
		invoiceOrderDto.setSupplierCode(supplierId);
		invoiceOrderDto.setSupplierName(supplierName);
		invoiceOrderService.insert(invoiceOrderDto);
		
		List<PurchaseCostDto> dtos=new ArrayList<PurchaseCostDto>();
		for(int i=0;i<chk.length;i++)
		{
			if(chk[i].equals("1"))
			{
				PurchaseCostDto costDto=new PurchaseCostDto();
				costDto.setAllocationCode(allocationCode[i]);//分摊方法
				costDto.setAllocationPrice(new BigDecimal(allocationPrice[i]));//税额;
				costDto.setAllocationQty(Short.valueOf(allocationQty[i]));//数量;
				costDto.setCostCode(costCode[i]);
				costDto.setCostDate(new Date());
				costDto.setCostName(costName[i]);
				costDto.setLocalPrice(new BigDecimal(localPrice[i]));
				costDto.setLocalTotalPrice(new BigDecimal(localTotalPrice[i]));
				costDto.setExchangeRate(new BigDecimal(exchangeRate[i]));//税率;
				costDto.setPurchaseCode(Long.valueOf(sid));//发票号
				costDto.setStatus(1);
				dtos.add(costDto);
			}
		}
        purchaseCostService.updatePurchaseCost(dtos, Long.valueOf(sid));
		return "OK";
	}
	@RequestMapping(value = "/doUpdate", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String doUpdate(HttpServletRequest request, HttpServletResponse response) {
		LOG.info("Go into SkuAllocation - saveSkuAllocation");
		InvoiceOrderService invoiceOrderService=(InvoiceOrderService)RemoteServiceSingleton.getInstance().getAppService("invoiceOrderService");
		PurchaseCostService purchaseCostService=(PurchaseCostService)RemoteServiceSingleton.getInstance().getAppService("purchaseCostService");
		String purchase_entity=request.getParameter("purchase_entity");
		String purchase_name=request.getParameter("purchase_name");
		String invoiceChar=request.getParameter("invoiceChar");
		String createBy=request.getParameter("createBy");
		String supplierName=request.getParameter("supplierName");
		String supplierId=request.getParameter("supplierId");
		String sid=request.getParameter("sid");
		String invoiceNumber=request.getParameter("invoiceNumber");
		String remark=request.getParameter("remark");
		String invoiceTotalPrice=request.getParameter("invoiceTotalPrice");
		
		String[] costName=request.getParameterValues("costName");
		String[] costCode=request.getParameterValues("costCode");
		String[] allocationQty=request.getParameterValues("allocationQty");
		String[] localPrice=request.getParameterValues("localPrice");
		String[] localTotalPrice=request.getParameterValues("localTotalPrice");
		String[] exchangeRate=request.getParameterValues("exchangeRate");
		String[] allocationPrice=request.getParameterValues("allocationPrice");
		String[] allocationCode=request.getParameterValues("allocationCode");
		String[] chk=request.getParameter("chk").split(",");
		InvoiceOrderDto invoiceOrderDto=new InvoiceOrderDto();
		invoiceOrderDto.setBalanceType("001");
		invoiceOrderDto.setCompanyCode(purchase_entity);
		invoiceOrderDto.setCompanyName(purchase_name);
		invoiceOrderDto.setCreateby(createBy);
		invoiceOrderDto.setCreateTime(new Date());
		invoiceOrderDto.setInvoiceChar(invoiceChar);
		invoiceOrderDto.setInvoiceTotalPrice(new BigDecimal(invoiceTotalPrice));
		invoiceOrderDto.setInvoiceNumber(invoiceNumber);
		invoiceOrderDto.setInvoiceType(2);
		invoiceOrderDto.setRemark(remark);
		invoiceOrderDto.setSid(Long.valueOf(sid));
		invoiceOrderDto.setStatus(1);
		invoiceOrderDto.setSupplierCode(supplierId);
		invoiceOrderDto.setSupplierName(supplierName);
		invoiceOrderService.updateInvoiceOrder(invoiceOrderDto);
		
		List<PurchaseCostDto> dtos=new ArrayList<PurchaseCostDto>();
		for(int i=0;i<chk.length;i++)
		{
			if(chk[i].equals("1"))
			{
				PurchaseCostDto costDto=new PurchaseCostDto();
				costDto.setAllocationCode(allocationCode[i]);//分摊方法
				costDto.setAllocationPrice(new BigDecimal(allocationPrice[i]));//税额;
				costDto.setAllocationQty(Short.valueOf(allocationQty[i]));//数量;
				costDto.setCostCode(costCode[i]);
				costDto.setCostDate(new Date());
				costDto.setCostName(costName[i]);
				costDto.setLocalPrice(new BigDecimal(localPrice[i]));
				costDto.setLocalTotalPrice(new BigDecimal(localTotalPrice[i]));
				costDto.setExchangeRate(new BigDecimal(exchangeRate[i]));//税率;
				costDto.setPurchaseCode(Long.valueOf(sid));//发票号
				costDto.setStatus(1);
				dtos.add(costDto);
			}
		}
        purchaseCostService.updatePurchaseCost(dtos, Long.valueOf(sid));
		return "OK";
	}
	@RequestMapping(value = "/doCheck", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String doCheck(HttpServletRequest request, HttpServletResponse response) {
		LOG.info("Go into SkuAllocation - saveSkuAllocation");
		InvoiceOrderService invoiceOrderService=(InvoiceOrderService)RemoteServiceSingleton.getInstance().getAppService("invoiceOrderService");
		PurchaseCostService purchaseCostService=(PurchaseCostService)RemoteServiceSingleton.getInstance().getAppService("purchaseCostService");
		String purchase_entity=request.getParameter("purchase_entity");
		String purchase_name=request.getParameter("purchase_name");
		String invoiceChar=request.getParameter("invoiceChar");
		String createBy=request.getParameter("createBy");
		String supplierName=request.getParameter("supplierName");
		String supplierId=request.getParameter("supplierId");
		String sid=request.getParameter("sid");
		String invoiceNumber=request.getParameter("invoiceNumber");
		String remark=request.getParameter("remark");
		String invoiceTotalPrice=request.getParameter("invoiceTotalPrice");
		
		String[] costName=request.getParameterValues("costName");
		String[] costCode=request.getParameterValues("costCode");
		String[] allocationQty=request.getParameterValues("allocationQty");
		String[] localPrice=request.getParameterValues("localPrice");
		String[] localTotalPrice=request.getParameterValues("localTotalPrice");
		String[] exchangeRate=request.getParameterValues("exchangeRate");
		String[] allocationPrice=request.getParameterValues("allocationPrice");
		String[] allocationCode=request.getParameterValues("allocationCode");
		String[] chk=request.getParameter("chk").split(",");
		InvoiceOrderDto invoiceOrderDto=new InvoiceOrderDto();
		invoiceOrderDto.setBalanceType("001");
		invoiceOrderDto.setCompanyCode(purchase_entity);
		invoiceOrderDto.setCompanyName(purchase_name);
		invoiceOrderDto.setCreateby(createBy);
		invoiceOrderDto.setCreateTime(new Date());
		invoiceOrderDto.setInvoiceChar(invoiceChar);
		invoiceOrderDto.setInvoiceTotalPrice(new BigDecimal(invoiceTotalPrice));
		invoiceOrderDto.setInvoiceNumber(invoiceNumber);
		invoiceOrderDto.setInvoiceType(2);
		invoiceOrderDto.setRemark(remark);
		invoiceOrderDto.setSid(Long.valueOf(sid));
		invoiceOrderDto.setStatus(10);
		invoiceOrderDto.setSupplierCode(supplierId);
		invoiceOrderDto.setSupplierName(supplierName);
		invoiceOrderService.updateInvoiceOrder(invoiceOrderDto);
		
		List<PurchaseCostDto> dtos=new ArrayList<PurchaseCostDto>();
		for(int i=0;i<chk.length;i++)
		{
			if(chk[i].equals("1"))
			{
				PurchaseCostDto costDto=new PurchaseCostDto();
				costDto.setAllocationCode(allocationCode[i]);//分摊方法
				costDto.setAllocationPrice(new BigDecimal(allocationPrice[i]));//税额;
				costDto.setAllocationQty(Short.valueOf(allocationQty[i]));//数量;
				costDto.setCostCode(costCode[i]);
				costDto.setCostDate(new Date());
				costDto.setCostName(costName[i]);
				costDto.setLocalPrice(new BigDecimal(localPrice[i]));
				costDto.setLocalTotalPrice(new BigDecimal(localTotalPrice[i]));
				costDto.setExchangeRate(new BigDecimal(exchangeRate[i]));//税率;
				costDto.setPurchaseCode(Long.valueOf(sid));//发票号
				costDto.setStatus(10);
				dtos.add(costDto);
			}
		}
        purchaseCostService.updatePurchaseCost(dtos, Long.valueOf(sid));
		return "OK";
	}
	*//**
	 * @Description： 保存分摊主表信息
	 * @author: QIJJ
	 * @since: 2015-7-14 下午5:19:03
	 *//*
	@RequestMapping(value = "/savePurchaseCost")
	@ResponseBody
	public Result savePurchaseCost(HttpServletRequest request, HttpServletResponse response, Model model, String purchaseId) {
		Result rs = new Result();

		try {
			PurchaseCostService purchaseCostService = (PurchaseCostService) RemoteServiceSingleton.getInstance().getAppService("purchaseCostService");

			java.text.SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			// String purchaseId = request.getParameter("purchaseId");
			String[] delBox = request.getParameterValues("delBox");// checkBox
			String[] chkTrue = request.getParameter("chkTrue").split(",");// 判断是否选中状态

			String[] costDate = request.getParameterValues("costDate");// 费用日期
			String[] costCode = request.getParameterValues("costCode");// 费用CODE
			String[] waitAllocationPrice = request.getParameterValues("waitAllocationPrice");// 待分摊金额
			String[] tax = request.getParameterValues("tax");// 税率
			String[] localPrice = request.getParameterValues("localPrice");// 本币总金额
			String[] exchangeRate = request.getParameterValues("exchangeRate");// 汇率
			String[] currencyCode = request.getParameterValues("currencyCode");// 币种
			String[] allocationCode = request.getParameterValues("allocationCode");// 分摊方式
			String[] imagePath = request.getParameterValues("imagePath");// 凭据影像
			String[] totalFee = request.getParameterValues("totalFee");// 分摊金额

			InfrastructureService infrastructureService = RemoteServiceSingleton.getInstance().getInfrastructureService();
			List<Cost> costs = infrastructureService.findCost();
			List<Currency> currencies = infrastructureService.findCurrency();
			List<Dictionarys> dictionarys = infrastructureService.goodsCostAllocation();

			*//**
			 * purchaseCostService.deletePurchaseCost(Long.parseLong(purchaseId)
			 * ); PChaseOrderServiceRPC pChaseOrderServiceRPC =
			 * (PChaseOrderServiceRPC)
			 * RemoteServiceSingleton.getInstance().getAppService
			 * ("pChaseOrderServiceRPC");
			 * pChaseOrderServiceRPC.updatePurchaseFeeById
			 * (Long.parseLong(purchaseId), 0, 1);
			 *//*

			String[] bussNum = purchaseId.split(",");
			*//** 笛卡尔集--必须双层for循环 *//*
			for (int k = 0; k < bussNum.length; k++) {
				for (int i = 0; i < delBox.length; i++) {
					if ("0".equals(chkTrue[i])) {
						continue;
					}

					PurchaseCost purchaseCost = new PurchaseCost();
					purchaseCost.setPurchaseCode(Long.parseLong(bussNum[k]));// 采购订单号
					purchaseCost.setStatus(0);// 状态
					purchaseCost.setCostName(getCostName(costs, costCode[i]));// 费用名称
					purchaseCost.setCurrencyName(getCurrencyName(currencies, currencyCode[i]));// 币种名称
					purchaseCost.setAllocationName(getDictionarysName(dictionarys, allocationCode[i]));// 分摊方法名称

					if (StringUtils.isNotBlank(costDate[i])) {
						purchaseCost.setCostDate(format.parse(costDate[i]));// 费用日期
					}
					if (StringUtils.isNotBlank(costCode[i])) {
						purchaseCost.setCostCode(costCode[i]);// 费用CODE
					}
					if (StringUtils.isNotBlank(waitAllocationPrice[i])) {
						purchaseCost.setWaitAllocationPrice(new BigDecimal(waitAllocationPrice[i]));// 待分摊金额
					}
					if (StringUtils.isNotBlank(allocationCode[i])) {
						purchaseCost.setAllocationCode(allocationCode[i]);// 分摊方法
					}
					if (StringUtils.isNotBlank(currencyCode[i])) {
						purchaseCost.setCurrencyCode(currencyCode[i]);// 币种
					}
					if (StringUtils.isNotBlank(exchangeRate[i])) {
						purchaseCost.setExchangeRate(new BigDecimal(exchangeRate[i]));// 汇率
					}
					if (StringUtils.isNotBlank(localPrice[i])) {
						purchaseCost.setLocalTotalPrice(new BigDecimal(localPrice[i]));// 本币总价
					}
					if (StringUtils.isNotBlank(imagePath[i])) {
						purchaseCost.setImagePath(imagePath[i]);// 凭据影像
					}
					if (StringUtils.isNotBlank(totalFee[i])) {
						purchaseCost.setAllocationPrice(new BigDecimal(totalFee[i]));// 分摊金额
					}
					if (StringUtils.isNotBlank(tax[i])) {
						purchaseCost.setTax(new BigDecimal(tax[i]));// 税率
					}

					// TODO
					// 税率CODE
					// 分摊数量
					// 本币单价

					*//** 保存分摊数据 *//*
					purchaseCostService.insertPurchaseCost(purchaseCost);
				}
			}

			rs.setSuccess(true);
			rs.setMessage("操作成功");
		} catch (Exception e) {
			rs.setSuccess(false);
			rs.setMessage("操作失败");
			LOG.error("异步操作库存:" + e.getMessage(), e);
			e.printStackTrace();
		}

		return rs;
	}

	*//**
	 * @Description： 审核
	 * @author: QIJJ
	 * @since: 2015-7-21 上午11:24:48
	 *//*
	@RequestMapping(value = "/auditAllocation")
	@ResponseBody
	public Result auditAllocation(HttpServletRequest request, HttpServletResponse response, Model model) {
		Result rs = new Result();

		try {
			PurchaseCostService purchaseCostService = (PurchaseCostService) RemoteServiceSingleton.getInstance().getAppService("purchaseCostService");

			String[] purchaseCostId = request.getParameterValues("PurchaseCostId");// checkBox
			for (int i = 0; i < purchaseCostId.length; i++) {

				purchaseCostService.updatePurchaseCostById(Long.valueOf(purchaseCostId[i]), Constants.INT15);
			}

			rs.setSuccess(true);
			rs.setMessage("操作成功");
		} catch (Exception e) {
			rs.setSuccess(false);
			rs.setMessage("操作失败");
			LOG.error("异步操作库存:" + e.getMessage(), e);
			e.printStackTrace();
		}

		return rs;
	}

	*//**
	 * @Description： 验证本币金额
	 * @author: QIJJ
	 * @since: 2015-7-20 下午4:19:13
	 *//*
	@RequestMapping(value = "/vaildMoney")
	@ResponseBody
	public Result vaildMoney(HttpServletRequest request, HttpServletResponse response, Model model, String money, BigDecimal localPrice) {
		Result rs = new Result();

		try {
			BigDecimal totalPrice = new BigDecimal("0.00");// 总价
			String[] str = money.split(",");
			for (int i = 0; i < str.length; i++) {
				BigDecimal bd = new BigDecimal(str[i]);
				totalPrice = totalPrice.add(bd);
			}

			totalPrice = totalPrice.setScale(2, BigDecimal.ROUND_HALF_DOWN);// 总价保留两位
			localPrice = localPrice.setScale(2, BigDecimal.ROUND_HALF_DOWN);// 本币金额保留两位

			if (totalPrice.equals(localPrice)) {
				rs.setSuccess(true);
				rs.setMessage("true");
			} else {
				rs.setSuccess(false);
				rs.setMessage("false");
			}
		} catch (Exception e) {
			rs.setSuccess(false);
			rs.setMessage("false");
			LOG.error("异步操作库存:" + e.getMessage(), e);
			e.printStackTrace();
		}

		return rs;
	}

	*//**
	 * @Description： 价格方法辅助类
	 * @author: songRL
	 * @since: 2015-7-14 下午5:55:10
	 *//*
	public BigDecimal getTotalPrice(String[] val) {
		BigDecimal totalPrice = new BigDecimal(0);
		for (int i = 0; i < val.length; i++) {
			BigDecimal price = new BigDecimal(val[i].split(",")[2]);
			totalPrice = totalPrice.add(price);
		}

		return totalPrice;
	}

	*//**
	 * @Description： 数量方法辅助类
	 * @author: songRL
	 * @since: 2015-7-14 下午5:56:05
	 *//*
	public Integer getTotalQty(String[] val) {
		int total = 0;
		for (int i = 0; i < val.length; i++) {
			total = total + Integer.parseInt(val[i].split(",")[5]);
		}

		return total;
	}

	*//**
	 * @Description： 返回费用名称
	 * @author: songRL
	 * @since: 2015-7-17 下午3:04:28
	 *//*
	public String getCostName(List<Cost> costs, String code) {
		String result = "";
		for (Cost cost : costs) {
			if (cost.getCode().equals(code)) {
				result = cost.getCostName();
				break;
			}
		}

		return result;
	}

	*//**
	 * @Description： 返回币种名称
	 * @author: songRL
	 * @since: 2015-7-17 下午2:24:42
	 *//*
	public String getCurrencyName(List<Currency> costs, String code) {
		String result = "";
		for (Currency cost : costs) {
			if (cost.getCode().equals(code)) {
				result = cost.getCurrencyType();
				break;
			}
		}

		return result;
	}

	*//**
	 * @Description： 返回分摊方法名称
	 * @author: songRL
	 * @since: 2015-7-17 下午3:05:10
	 *//*
	public String getDictionarysName(List<Dictionarys> costs, String code) {
		String result = "";
		for (Dictionarys cost : costs) {
			if (cost.getDictValue().equals(code)) {
				result = cost.getDictName();
				break;
			}
		}

		return result;
	}

	*//************************************************* 当前过时/无用方法 ************************************************************//*
	@RequestMapping(value = "/editCost")
	public String editCost(Model model, HttpServletRequest request, Long id, Integer page) {
		PurchaseCostService purchaseCostService = (PurchaseCostService) RemoteServiceSingleton.getInstance().getAppService("purchaseCostService");
		InfrastructureService infrastructureService = RemoteServiceSingleton.getInstance().getInfrastructureService();
		List<Cost> costs = infrastructureService.findCost();
		List<Currency> currencies = infrastructureService.findCurrency();
		List<Dictionarys> dictionarys = infrastructureService.goodsCostAllocation();

		PageBean<PurchaseCostDto> pageBean = new PageBean<PurchaseCostDto>();
		PurchaseCostDto dto = new PurchaseCostDto();
		dto.setPurchaseCode(id);
		if (page != null) {
			pageBean.setPage(page);
		} else {
			pageBean.setPage(1);
		}
		pageBean.setPageSize(100);
		pageBean.setParameter(dto);
		pageBean = purchaseCostService.findPurchaseCost(pageBean);
		String qty = request.getParameter("qty");
		String price = request.getParameter("price");

		model.addAttribute("Cost", costs);
		model.addAttribute("Currency", currencies);
		model.addAttribute("Dictionarys", dictionarys);
		model.addAttribute("id", id);
		model.addAttribute("qty", qty);
		model.addAttribute("price", price);
		model.addAttribute("pb", pageBean);

		return "/purchasecost/editCost";
	}
	@RequestMapping(value = "/CostReg")
	public String CostReg(HttpServletRequest request, Long id, Model model) {
//		PurchaseCostService purchaseCostService = (PurchaseCostService) RemoteServiceSingleton.getInstance().getAppService("purchaseCostService");
//		purchaseCostService.updatePurchaseCost(id);
//		PChaseOrderServiceRPC pChaseOrderServiceRPC = (PChaseOrderServiceRPC) RemoteServiceSingleton.getInstance().getAppService("pChaseOrderServiceRPC");
//		pChaseOrderServiceRPC.updatePurchaseFeeById(id, 1, 1);
		List<Dictionarys> purchaseEntity = RemoteServiceSingleton.getInstance().getInfrastructureService().findPurchasingUnitByAll();
		
		java.text.SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		java.text.SimpleDateFormat now=new SimpleDateFormat("yyyy-MM-dd");
		WaterService waterService=(WaterService)RemoteServiceSingleton.getInstance().getAppService("waterService");
		String code="FY"+format.format(new Date())+waterService.getWater("FY");
		InfrastructureService infrastructureService = RemoteServiceSingleton.getInstance().getInfrastructureService();
		List<Cost> costs = infrastructureService.findCost();
		List<Dictionarys> dictionarys = infrastructureService.goodsCostAllocation();
		model.addAttribute("Cost", costs);
		model.addAttribute("Function", dictionarys);

		model.addAttribute("code", code);
		model.addAttribute("sid", PKUtils.getLongPrimaryKey());
		model.addAttribute("now", now.format(new Date()));
		model.addAttribute("user", this.getCurrentUser().getUsername());
		model.addAttribute("Entity", purchaseEntity);
		return "/purchasecost/CostReg";
	}
	@RequestMapping(value = "/editPurchaseCostDetil")
	public String editPurchaseCostDetil(HttpServletRequest request, Long id, Model model) {
		PurchaseCostService purchaseCostService = (PurchaseCostService) RemoteServiceSingleton.getInstance().getAppService("purchaseCostService");
		purchaseCostService.updatePurchaseCost(id);
		PChaseOrderServiceRPC pChaseOrderServiceRPC = (PChaseOrderServiceRPC) RemoteServiceSingleton.getInstance().getAppService("pChaseOrderServiceRPC");
		pChaseOrderServiceRPC.updatePurchaseFeeById(id, 1, 1);
		return "redirect:/purchasecost/findPurchaseCost";
	}

	@RequestMapping(value = "/uploadCost", method = RequestMethod.POST)
	@ResponseBody
	public String uploadCost(HttpServletRequest request, HttpServletResponse response) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("Filedata");
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		String ctxPath = request.getSession().getServletContext().getRealPath("/") + this.getCurrentUser().getUsername();
		File tagetfile = new File(ctxPath, file.getOriginalFilename());
		if (!tagetfile.exists()) {
			tagetfile.mkdirs();
		}
		System.out.println(ctxPath);
		try {
			file.transferTo(tagetfile);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(fileMap.size());
		return tagetfile.getAbsolutePath();
	}

	@RequestMapping(value = "/upload")
	public String upload(HttpServletRequest request, HttpServletResponse response) {

		return "/purchasecost/upload";
	}

	public void copyInputStreamToFile(InputStream stream, File file) {
		try {
			FileOutputStream fos = new FileOutputStream(file);

			byte[] b = new byte[1024];
			int n = 0;

			while ((n = stream.read(b)) != -1) {
				fos.write(b, 0, n);
			}

			fos.close();
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
*/