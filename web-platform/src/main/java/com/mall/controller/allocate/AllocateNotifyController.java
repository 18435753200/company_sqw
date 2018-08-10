/**  
 * @Project: web-platform
 * @Title: AllocateNotifyController.java
 * @Package: com.mall.controller.allocate
 * @Description: 商品调拨通知单控制层  
 * @author: QIJJ 
 * @since: 2015-6-29 上午10:48:51
 * @Copyright: 2015. All rights reserved.
 * @Version: v1.0   
 */
package com.mall.controller.allocate;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.commons.utils.DateUtil;
import com.mall.dealer.product.api.DealerProdPoRderService;
import com.mall.dealer.product.dto.SkuCodeDTO;
import com.mall.mybatis.utility.PageBean;
import com.mall.retailer.order.common.PKUtils;
import com.mall.stock.api.rpc.StockService;
import com.mall.stock.api.rpc.WarehouseService;
import com.mall.stock.dto.StockDetailDTO;
import com.mall.stock.po.Warehouse;
//import com.mall.supplier.order.api.rpc.TransferItemRpcService;
//import com.mall.supplier.order.api.rpc.TransferOrderRpcService;
//import com.mall.supplier.order.api.rpc.WaterService;
//import com.mall.supplier.order.dto.TransferOrderDto;
//import com.mall.supplier.order.exception.BusinessException;
//import com.mall.supplier.order.po.TransferItem;
//import com.mall.supplier.order.po.TransferOrder;
import com.mall.controller.base.BaseController;
import com.mall.controller.base.Result;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;

/**
 * @ClassName AllocateNotifyController
 * @Description: 商品调拨通知单控制层
 * @author: QIJJ
 * @since: 2015-6-29 上午10:48:51
 */
@Controller
@RequestMapping(value = "/allocateNotify")
public class AllocateNotifyController extends BaseController {

	private static final Log LOG = LogFactory.getLogger(AllocateNotifyController.class);

	/**
	 * 日期的转换
	 */
	@Override
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/**
	 * @Description： 跳转至商品调拨查询界面-Done
	 * @author: QIJJ
	 * @since: 2015-7-2 下午5:23:01
	 */
	@RequestMapping(value = "/getAllocateProductPage", method = { RequestMethod.GET, RequestMethod.POST })
	public String getAllocateProductPage(HttpServletRequest request, HttpServletResponse response, Model model) {
		LOG.info("Go into AllocateNotify - AllocateProduct Select Page");

		List<Warehouse> warehouseList = null;
		try {
			WarehouseService warehouseService = (WarehouseService) RemoteServiceSingleton.getInstance().getAppService("warehouseService");
			warehouseList = warehouseService.findWarehouseByWareseName(null);

			model.addAttribute("warehouseList", warehouseList);
		} catch (Exception e) {
			LOG.error("进入调拨商品页面." + e.getMessage(), e);
			e.printStackTrace();
		}

		return "dealerseller/allocateNotify/allocateProductPage";
	}

	/**
	 * @Description： 商品调拨单分页查询-Done
	 * @author: QIJJ
	 * @since: 2015-7-3 上午11:51:24
	 */
	/*@RequestMapping(value = "/getAllocatePrdPageBean", method = { RequestMethod.GET, RequestMethod.POST })
	public String getAllocatePrdPageBean(HttpServletRequest request, HttpServletResponse response, Model model, Integer page, Date firstDate, Date lastDate, String transferNoChar,
			String transferOutWarehouseCode, String transferOutWarehouseName, String transferInWarehouseCode, String transferInWarehouseName, Integer status) {
		LOG.info("Go into AllocateNotify - AllocateProduct Select Page");

		try {
			PageBean<TransferOrder> pageBean = new PageBean<TransferOrder>();

			TransferOrder transferOrder = new TransferOrder();// 序列化后参数接收对象
			transferOrder.setStatus(status);
			transferOrder.setTransferInWarehouseCode(transferInWarehouseCode);
			transferOrder.setTransferOutWarehouseCode(transferOutWarehouseCode);
			transferOrder.setTransferNoChar(transferNoChar);

			if (Constants.PLEASE_SELECT.equals(transferInWarehouseName)) {// 库房名称
				transferInWarehouseName = "";
			}
			transferOrder.setTransferInWarehouseName(transferInWarehouseName);
			if (Constants.PLEASE_SELECT.equals(transferOutWarehouseName)) {// 库房名称
				transferOutWarehouseName = "";
			}
			transferOrder.setTransferOutWarehouseName(transferOutWarehouseName);
			transferOrder.setFirstDate(firstDate);
			transferOrder.setLastDate(lastDate);

			if (page != null) {
				pageBean.setPage(page);
			} else {
				pageBean.setPage(1);
			}
			pageBean.setPageSize(Constants.INT10);
			pageBean.setParameter(transferOrder);// 设置分页

			TransferOrderRpcService transferOrderService = (TransferOrderRpcService) RemoteServiceSingleton.getInstance().getAppService("transferOrderRpcService");
			pageBean = transferOrderService.selectByDynamicListPage(pageBean);
			LOG.info("Return page Bean success!");

			model.addAttribute("pb", pageBean);
		} catch (Exception e) {
			LOG.error("进入调拨商品页面." + e.getMessage(), e);
			e.printStackTrace();
		}

		return "dealerseller/allocateNotify/allocateProductTablePage";
	}

	*//**
	 * @Description： 跳转至调拨单添加操作页面(显示查询条件)-Done
	 * @author: QIJJ
	 * @since: 2015-7-3 下午3:45:00
	 *//*
	@RequestMapping(value = "/getAddAllocatePrdNotifyPage", method = { RequestMethod.GET, RequestMethod.POST })
	public String getAddAllocatePrdNotifyPage(HttpServletRequest request, HttpServletResponse response, Model model) {
		LOG.info("Go into AllocateNotify - getAddAllocatePrd Page");

		List<Warehouse> warehouseList = null;
		try {
			WarehouseService warehouseService = (WarehouseService) RemoteServiceSingleton.getInstance().getAppService("warehouseService");
			warehouseList = warehouseService.findWarehouseByWareseName(null);
			WaterService waterservice = (WaterService) RemoteServiceSingleton.getInstance().getAppService("waterService");

			String num = DateUtil.toDateStr(new Date(), "yyyyMMdd");
			model.addAttribute("transferNoChar", "TB" + num + waterservice.getWater("RK"));// 生成序列号
			model.addAttribute("warehouseList", warehouseList);
			model.addAttribute("createBy", this.getCurrentUser().getUsername());

			LOG.info("Go into addAllocatePrdNotifyPage  Success");
		} catch (Exception e) {
			LOG.error("进入调拨商品页面." + e.getMessage(), e);
			e.printStackTrace();
		}

		return "dealerseller/allocateNotify/addAllocatePrdNotifyPage";
	}*/

	/**
	 * @Description： 点击操作按钮跳转到操作页面-Done
	 * @author: QIJJ
	 * @throws UnsupportedEncodingException
	 * @since: 2015-7-1 上午10:37:21
	 */
	@RequestMapping(value = "/getOperationPage", method = { RequestMethod.GET, RequestMethod.POST })
	public String getOperationPage(HttpServletRequest request, HttpServletResponse response, Model model, String transferOutWarehouseCode, String transferOutWarehouseName) {
		LOG.info("Go into AllocateNotify - allocateOperationPage.jsp");

		try {
			String name = java.net.URLDecoder.decode(transferOutWarehouseName, "UTF-8");

			model.addAttribute("transferOutWarehouseCode", transferOutWarehouseCode);
			model.addAttribute("transferOutWarehouseName", name);
			LOG.info("Go into allocateOperationPage  Success");
		} catch (Exception e) {
			LOG.error("进入库房明细操作页面:" + e.getMessage(), e);
			e.printStackTrace();
		}

		return "dealerseller/allocateNotify/allocateOperationPage";
	}

	/**
	 * @Description： 库房明细操作页面
	 * @author: QIJJ
	 * @since: 2015-7-1 下午5:47:52
	 */
	@RequestMapping(value = "/getOperationDataToTable", method = { RequestMethod.GET, RequestMethod.POST })
	public String getOperationDataToTable(HttpServletRequest request, HttpServletResponse response, Model model, String transferOutWarehouseCode, String transferOutWarehouseName, String pcode,
			String barCode, String pname, Integer isgenuine, Integer page,Long skuId,String barSkuId) {
		LOG.info("Go into AllocateNotify - allocateOperTablePage.jsp");

		try {
			String name = java.net.URLDecoder.decode(transferOutWarehouseName, "UTF-8");

			SkuCodeDTO skuCodeDTO = new SkuCodeDTO();
			skuCodeDTO.setSupplierProdName(pname);
			skuCodeDTO.setSkuCode(barCode);
			skuCodeDTO.setBusinessProdId(pcode);
			skuCodeDTO.setSkuId(skuId);
			skuCodeDTO.setBarSkuId(barSkuId);

			// 商品接口
			DealerProdPoRderService dealerProdPoRderService = (DealerProdPoRderService) RemoteServiceSingleton.getInstance().getAppService("dealerProdPoRderService");

			PageBean<SkuCodeDTO> pageBean = new PageBean<SkuCodeDTO>();
			if (page != null) {
				pageBean.setPage(page);
			} else {
				pageBean.setPage(1);
			}

			pageBean.setPageSize(Constants.INT10);
			pageBean.setParameter(skuCodeDTO);
			/** 查询商品分页 */
			PageBean<SkuCodeDTO> pageList = dealerProdPoRderService.findSkuDateByName(pageBean);

			model.addAttribute("pb", pageList);
			model.addAttribute("transferOutWarehouseCode", transferOutWarehouseCode);
			model.addAttribute("transferOutWarehouseName", name);
			model.addAttribute("barCode", barCode);
			model.addAttribute("pcode", pcode);
			model.addAttribute("pname", pname);
			model.addAttribute("skuId", skuId);
			model.addAttribute("barSkuId", barSkuId);
			model.addAttribute("isgenuine", isgenuine);
			LOG.info("Go into allocateOperTablePage Success");
		} catch (Exception e) {
			LOG.error("进入库房明细操作页面:" + e.getMessage(), e);
			e.printStackTrace();
		}

		return "dealerseller/allocateNotify/allocateOperTablePage";
	}

	/**
	 * @Description： 商品关联库存操作页面
	 * @author: QIJJ
	 * @since: 2015-7-6 下午3:14:21
	 */
	/*@RequestMapping(value = "/operatorStock", method = { RequestMethod.GET, RequestMethod.POST })
	public String operatorStock(HttpServletRequest request, HttpServletResponse response, Model model, String transferOutWarehouseCode, String transferOutWarehouseName, String pcode, String barCode,
			String pname, String isgenuine, String format, String unit, String skuId, Long productId, BigDecimal price,String barSkuId) {
		LOG.info("Go into AllocateNotify - operationStockPage.jsp");

		List<StockDetailDTO> stockDetailDtoList = null;

		try {
			List<Long> skuIdList = new ArrayList<Long>();
			skuIdList.add(Long.valueOf(skuId));

			// 获取库存对应全部批次数据
			stockDetailDtoList = RemoteServiceSingleton.getInstance().getStockService().getStockDetail(skuIdList, Integer.valueOf(transferOutWarehouseCode), Short.valueOf(isgenuine));
			List<TransferItem> transferItems = new ArrayList<TransferItem>();
			for (int i = 0; i < stockDetailDtoList.size(); i++) {
				StockDetailDTO stockDetailDTO = stockDetailDtoList.get(i);
				TransferItem transferItem = new TransferItem();
				*//** 商品接口获取 *//*
				transferItem.setBarCode(barCode);// 商品条码
				transferItem.setFormat(format);// 规格
				transferItem.setUnit(unit);// 单位
				transferItem.setPcode(pcode);// 商品编号
				transferItem.setPname(pname);// 商品名称
				transferItem.setPid(productId);// 商品ID
				transferItem.setPrice(price);// 单价

				*//** 库存接口获取 *//*
				transferItem.setIsgenuine(Integer.valueOf(isgenuine));// 商品状态
				transferItem.setSkuId(Long.valueOf(skuId));// SkuID
				transferItem.setStockQty(stockDetailDTO.getQty() - stockDetailDTO.getPreSubQty() - stockDetailDTO.getLockQty());// 库存可用数量
				transferItem.setBatchId(stockDetailDTO.getLotNo());// 生产批号
				transferItem.setBatchNumber(stockDetailDTO.getBatchNo());// 批次号
				transferItem.setProductionDate(stockDetailDTO.getProductDate());// 生产日期
				transferItem.setBarSkuId(barSkuId);
				//残品数量暂时写死（无接口返回数据）
				transferItem.setGoodsQty(stockDetailDTO.getIncompleteQty() == null?0:stockDetailDTO.getIncompleteQty().longValue());
				//残品数量暂时写死（无接口返回数据）
				transferItems.add(transferItem);
			}

			model.addAttribute("transferItems", transferItems);
			model.addAttribute("transferOutWarehouseName", transferOutWarehouseName);
			LOG.info("Go into operationStockPage Success");
		} catch (Exception e) {
			LOG.error("异步操作库存:" + e.getMessage(), e);
			e.printStackTrace();
		}

		return "dealerseller/allocateNotify/operationStockPage";
	}

	*//**
	 * @Description： 增加调拨通知
	 * @author: QIJJ
	 * @since: 2015-7-8 下午2:40:23
	 *//*
	@RequestMapping(value = "/saveAllocateNotify", method = { RequestMethod.GET, RequestMethod.POST })
	public String saveAllocateNotify(HttpServletRequest request, HttpServletResponse response, Model model, String transferNoChar, String transferOutWarehouseCodes, String transferOutWarehouseNames,
			String transferInWarehouseCodes, String transferInWarehouseNames, Date createTime, @RequestParam(required = false, defaultValue = "1") Integer status, String createBy) {
		LOG.info("Go into AllocateNotify - saveAllocateNotify");

		try {
			TransferOrderDto transferOrderDto = new TransferOrderDto();
			transferOrderDto.setSid(PKUtils.getLongPrimaryKey());
			transferOrderDto.setCreateTime(createTime);
			transferOrderDto.setStatus(status);
			transferOrderDto.setTransferInWarehouseCode(transferInWarehouseCodes);
			transferOrderDto.setTransferInWarehouseName(transferInWarehouseNames);
			transferOrderDto.setTransferNoChar(transferNoChar);
			transferOrderDto.setTransferOutWarehouseCode(transferOutWarehouseCodes);
			transferOrderDto.setTransferOutWarehouseName(transferOutWarehouseNames);
			transferOrderDto.setCreateBy(createBy);

			String[] skuName = request.getParameterValues("skuName");// checkBox
			String[] chkTrue = request.getParameter("chkTrue").split(",");// 判断是否选中状态

			String[] pcode = request.getParameterValues("pcode");// 商品编码
			String[] barCode = request.getParameterValues("barCode");// 商品条码
			String[] pname = request.getParameterValues("pname");// 商品名称
			String[] format = request.getParameterValues("format");// 规格
			String[] unit = request.getParameterValues("unit");// 单位
			String[] stockQty = request.getParameterValues("stockQty");// 库存数量
			String[] transferQty = request.getParameterValues("transferQty");// 调拨数量
			String[] isgenuine = request.getParameterValues("isgenuine");// 商品状态
			String[] batchNumber = request.getParameterValues("batchNumber");// 批次号
			String[] batchId = request.getParameterValues("batchId");// 商品批号
			String[] productionDate = request.getParameterValues("productionDate");// 生产日期
			String[] skuId = request.getParameterValues("skuId");// skuId
			String[] pid = request.getParameterValues("pid");// 商品ID
			String[] price = request.getParameterValues("price");// 商品价格

			if (skuName == null) {
				throw new BusinessException("请选择需要调拨的商品");
			}

			List<TransferItem> transferItems = new ArrayList<TransferItem>();
			for (int i = 0; i < skuName.length; i++) {
				if ("0".equals(chkTrue[i])) {
					continue;
				}

				TransferItem transferItem = new TransferItem();
				transferItem.setSid(PKUtils.getLongPrimaryKey());
				// transferItem.setTransferId(transferOrder.getSid());

				if (Constants.ISGENUINE_ZP.equals(isgenuine[i])) {
					transferItem.setIsgenuine(1);
				} else {
					transferItem.setIsgenuine(2);
				}
				transferItem.setProductionDate(DateUtil.strToDate(productionDate[i]));

				transferItem.setBarCode(barCode[i]);
				transferItem.setBatchId(batchId[i]);
				transferItem.setBatchNumber(batchNumber[i]);
				transferItem.setFormat(format[i]);
				transferItem.setPcode(pcode[i]);
				transferItem.setPid(Long.valueOf(pid[i]));
				transferItem.setPname(pname[i]);
				transferItem.setSkuId(Long.valueOf(skuId[i]));
				transferItem.setStockQty(Long.valueOf(stockQty[i]));
				transferItem.setTransferNoChar(transferNoChar);
				transferItem.setTransferQty(Long.valueOf(transferQty[i]));
				transferItem.setUnit(unit[i]);
				BigDecimal bd = new BigDecimal(price[i]);// 设置价格
				transferItem.setPrice(bd);

				transferItems.add(transferItem);
				// 添加子信息至Dto对象集合
				transferOrderDto.setTransferItems(transferItems);
			}

			TransferOrderRpcService transferOrderService = (TransferOrderRpcService) RemoteServiceSingleton.getInstance().getAppService("transferOrderRpcService");
			*//** 插入商品调拨主表信息 *//*
			transferOrderService.operationTransferOrder(transferOrderDto, Constants.DEFAULTPAGE);

			return "success";
		} catch (Exception e) {
			LOG.error("异步操作库存:" + e.getMessage(), e);
			e.printStackTrace();
		}

		return null;
	}

	*//**
	 * @Description： 跳转至修改操作页面
	 * @author: QIJJ
	 * @since: 2015-7-9 上午10:31:45
	 *//*
	@RequestMapping(value = "/showUpdatePage", method = { RequestMethod.GET, RequestMethod.POST })
	public String showUpdatePage(HttpServletRequest request, HttpServletResponse response, Model model, Long sid, @RequestParam(required = false, defaultValue = "1") Integer status) {
		LOG.info("Go into AllocateNotify - showUpdatePage Page");

		List<Warehouse> warehouseList = null;
		try {
			TransferOrderRpcService transferOrderService = (TransferOrderRpcService) RemoteServiceSingleton.getInstance().getAppService("transferOrderRpcService");
			*//** 通过主键查询调拨单 *//*
			TransferOrder transferOrder = transferOrderService.selectByPrimaryKey(sid);

			TransferItemRpcService transferItemRpcService = (TransferItemRpcService) RemoteServiceSingleton.getInstance().getAppService("transferItemRpcService");
			*//** 通过调拨单ID查询对应相信信息集合 *//*
			List<TransferItem> transferItems = transferItemRpcService.selectListByTransferId(sid);

			WarehouseService warehouseService = (WarehouseService) RemoteServiceSingleton.getInstance().getAppService("warehouseService");
			warehouseList = warehouseService.findWarehouseByWareseName(null);

			model.addAttribute("warehouseList", warehouseList);
			model.addAttribute("transferOrder", transferOrder);
			model.addAttribute("transferItems", transferItems);
			LOG.info("Go into showUpdatePage Success");
		} catch (Exception e) {
			LOG.error("进入调拨商品页面." + e.getMessage(), e);
			e.printStackTrace();
		}

		return "dealerseller/allocateNotify/showUpdatePage";
	}

	*//**
	 * @Description： 审核后查看页面
	 * @author: QIJJ
	 * @since: 2015-7-9 下午12:17:41
	 *//*
	@RequestMapping(value = "/showPage", method = { RequestMethod.GET, RequestMethod.POST })
	public String showPage(HttpServletRequest request, HttpServletResponse response, Model model, Long sid, @RequestParam(required = false, defaultValue = "10") Integer status) {
		LOG.info("Go into AllocateNotify - showPage Page");

		try {
			TransferOrderRpcService transferOrderService = (TransferOrderRpcService) RemoteServiceSingleton.getInstance().getAppService("transferOrderRpcService");
			*//** 通过主键查询调拨单 *//*
			TransferOrder transferOrder = transferOrderService.selectByPrimaryKey(sid);

			TransferItemRpcService transferItemRpcService = (TransferItemRpcService) RemoteServiceSingleton.getInstance().getAppService("transferItemRpcService");
			*//** 通过调拨单ID查询对应相信信息集合 *//*
			List<TransferItem> transferItems = transferItemRpcService.selectListByTransferId(sid);

			model.addAttribute("transferOrder", transferOrder);
			model.addAttribute("transferItems", transferItems);
			LOG.info("Go into showPage Success");
		} catch (Exception e) {
			LOG.error("进入调拨商品页面." + e.getMessage(), e);
			e.printStackTrace();
		}

		return "dealerseller/allocateNotify/showPage";
	}

	*//**
	 * @Description： 当前记录页面保存
	 * @author: QIJJ
	 * @since: 2015-7-9 下午4:04:15
	 *//*
	@RequestMapping(value = "/saveCompAllocateNotify", method = { RequestMethod.GET, RequestMethod.POST })
	public String saveCompAllocateNotify(HttpServletRequest request, HttpServletResponse response, Model model, Long sid) {
		LOG.info("Go into AllocateNotify - saveCompAllocateNotify");

		try {
			TransferOrderRpcService transferOrderService = (TransferOrderRpcService) RemoteServiceSingleton.getInstance().getAppService("transferOrderRpcService");

			TransferOrderDto transferOrderDto = new TransferOrderDto();
			transferOrderDto.setSid(sid);

			String[] checkName = request.getParameterValues("checkName");// checkBox
			String[] chkTrue = request.getParameter("chkTrue").split(",");// 判断是否选中状态
			String[] pcode = request.getParameterValues("pcode");// 商品编码
			String[] barCode = request.getParameterValues("barCode");// 商品条码
			String[] pname = request.getParameterValues("pname");// 商品名称
			String[] format = request.getParameterValues("format");// 规格
			String[] unit = request.getParameterValues("unit");// 单位
			String[] stockQty = request.getParameterValues("stockQty");// 库存数量
			String[] transferQty = request.getParameterValues("transferQty");// 调拨数量
			String[] isgenuine = request.getParameterValues("isgenuine");// 商品状态
			String[] batchNumber = request.getParameterValues("batchNumber");// 批次号
			String[] batchId = request.getParameterValues("batchId");// 商品批号
			String[] productionDate = request.getParameterValues("productionDate");// 生产日期
			String[] skuId = request.getParameterValues("skuId");// skuId
			String[] pid = request.getParameterValues("pid");// 商品ID
			String[] price = request.getParameterValues("price");// 商品价格

			if (checkName == null) {
				throw new BusinessException("请选择需要调拨的商品");
			}

			List<TransferItem> transferItems = new ArrayList<TransferItem>();
			for (int i = 0; i < checkName.length; i++) {
				if ("0".equals(chkTrue[i])) {
					continue;
				}

				TransferItem transferItem = new TransferItem();
				transferItem.setSid(PKUtils.getLongPrimaryKey());
				transferItem.setTransferId(sid);
				// transferItem.setTransferNoChar(transferOrder.getTransferNoChar());
				// transferItem.setIsgenuine(Integer.valueOf(isgenuine[i]));
				if (Constants.ISGENUINE_ZP.equals(isgenuine[i])) {
					transferItem.setIsgenuine(1);
				} else {
					transferItem.setIsgenuine(2);
				}
				transferItem.setProductionDate(DateUtil.strToDate(productionDate[i]));
				transferItem.setBarCode(barCode[i]);
				transferItem.setBatchId(batchId[i]);
				transferItem.setBatchNumber(batchNumber[i]);
				transferItem.setFormat(format[i]);
				transferItem.setPcode(pcode[i]);
				transferItem.setPid(Long.valueOf(pid[i]));
				transferItem.setPname(pname[i]);
				transferItem.setSkuId(Long.valueOf(skuId[i]));
				transferItem.setStockQty(Long.valueOf(stockQty[i]));
				transferItem.setTransferQty(Long.valueOf(transferQty[i]));
				transferItem.setUnit(unit[i]);
				BigDecimal bd = new BigDecimal(price[i]);// 设置价格
				transferItem.setPrice(bd);

				transferItems.add(transferItem);
			}
			// 添加子信息至Dto对象集合
			transferOrderDto.setTransferItems(transferItems);

			transferOrderService.modifyTransferOrder(transferOrderDto, Constants.DEFAULTPAGE);

			return "success";
		} catch (Exception e) {
			LOG.error("异步操作库存:" + e.getMessage(), e);
			e.printStackTrace();
		}

		return null;
	}

	*//**
	 * @Description： 审核当前记录
	 * @author: QIJJ
	 * @since: 2015-7-9 下午4:04:15
	 *//*
	@RequestMapping(value = "/auditCompAllocateNotify", method = { RequestMethod.GET, RequestMethod.POST })
	public String auditCompAllocateNotify(HttpServletRequest request, HttpServletResponse response, Model model, Long sid) {
		LOG.info("Go into AllocateNotify - auditCompAllocateNotify");

		try {
			TransferOrderRpcService transferOrderService = (TransferOrderRpcService) RemoteServiceSingleton.getInstance().getAppService("transferOrderRpcService");
			StockService stockService = (StockService)RemoteServiceSingleton.getInstance().getStockService();
			Map<Long,Integer> map = new HashMap<Long,Integer>();
			TransferOrderDto transferOrderDto = new TransferOrderDto();
			transferOrderDto.setSid(sid);

			String[] checkName = request.getParameterValues("checkName");// checkBox
			String[] chkTrue = request.getParameter("chkTrue").split(",");// 判断是否选中状态
			String[] pcode = request.getParameterValues("pcode");// 商品编码
			String[] barCode = request.getParameterValues("barCode");// 商品条码
			String[] pname = request.getParameterValues("pname");// 商品名称
			String[] format = request.getParameterValues("format");// 规格
			String[] unit = request.getParameterValues("unit");// 单位
			String[] stockQty = request.getParameterValues("stockQty");// 库存数量
			String[] transferQty = request.getParameterValues("transferQty");// 调拨数量
			String[] isgenuine = request.getParameterValues("isgenuine");// 商品状态
			String[] batchNumber = request.getParameterValues("batchNumber");// 批次号
			String[] batchId = request.getParameterValues("batchId");// 商品批号
			String[] productionDate = request.getParameterValues("productionDate");// 生产日期
			String[] skuId = request.getParameterValues("skuId");// skuId
			String[] pid = request.getParameterValues("pid");// 商品ID
			String[] price = request.getParameterValues("price");// 商品价格

			if (checkName == null) {
				throw new BusinessException("请选择需要调拨的商品");
			}

			List<TransferItem> transferItems = new ArrayList<TransferItem>();
			for (int i = 0; i < checkName.length; i++) {
				if ("0".equals(chkTrue[i])) {
					continue;
				}

				TransferItem transferItem = new TransferItem();
				transferItem.setSid(PKUtils.getLongPrimaryKey());
				transferItem.setTransferId(sid);
				// transferItem.setTransferNoChar(transferOrder.getTransferNoChar());
				if (Constants.ISGENUINE_ZP.equals(isgenuine[i])) {
					transferItem.setIsgenuine(1);
				} else {
					transferItem.setIsgenuine(2);
				}
				transferItem.setProductionDate(DateUtil.strToDate(productionDate[i]));
				transferItem.setBarCode(barCode[i]);
				transferItem.setBatchId(batchId[i]);
				transferItem.setBatchNumber(batchNumber[i]);
				transferItem.setFormat(format[i]);
				transferItem.setPcode(pcode[i]);
				transferItem.setPid(Long.valueOf(pid[i]));
				transferItem.setPname(pname[i]);
				transferItem.setSkuId(Long.valueOf(skuId[i]));
				transferItem.setStockQty(Long.valueOf(stockQty[i]));
				transferItem.setTransferQty(Long.valueOf(transferQty[i]));
				transferItem.setUnit(unit[i]);
				BigDecimal bd = new BigDecimal(price[i]);// 设置价格
				transferItem.setPrice(bd);
				
				map.put(skuId[i] == null ? 0L:Long.parseLong(skuId[i]),transferQty[i] == null?0:Integer.parseInt(transferQty[i]));
				

				transferItems.add(transferItem);
			}
			transferOrderDto.setTransferItems(transferItems);

			transferOrderService.modifyTransferOrder(transferOrderDto, Constants.PAGESIZE);
			
			stockService.updateInvAllocation(map, 2, "sys", String.valueOf(sid), sid);
			
			
			return "success";
		} catch (Exception e) {
			LOG.error("异步操作库存:" + e.getMessage(), e);
			e.printStackTrace();
		}

		return null;
	}

	*//**
	 * @Description： 完成调拨通知
	 * @author: QIJJ
	 * @since: 2015-7-10 上午9:54:34
	 *//*
	@RequestMapping(value = "/compleAllocateNotify", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Result compleAllocateNotify(HttpServletRequest request, HttpServletResponse response, Model model, String sids) {
		LOG.info("Go into AllocateNotify - compleAllocateNotify");

		Result rs = new Result();
		try {
			if (StringUtils.isBlank(sids)) {
				rs.setSuccess(false);
				rs.setMessage("请选择调拨记录");
			} else {
				TransferOrderRpcService transferOrderService = (TransferOrderRpcService) RemoteServiceSingleton.getInstance().getAppService("transferOrderRpcService");

				String[] str = sids.split(",");
				String conment = "";
				for (int i = 0; i < str.length; i++) {
					TransferOrder transferOrder = transferOrderService.selectByPrimaryKey(Long.valueOf(str[i]));
					if (Constants.INT10 == transferOrder.getStatus()) {
						transferOrderService.compleAllocateNotifyById(Long.valueOf(str[i]), Constants.INT15);
					} else {
						if (Constants.INT15 == transferOrder.getStatus()) {
							conment = conment + transferOrder.getTransferNoChar() + "已经调拨完成，不能重复调拨" + ",";
						} else {
							conment = conment + transferOrder.getTransferNoChar() + "未审核通过，不能调拨" + ",";
						}
					}
				}
				// transferOrderService.compleAllocateNotify(str,
				// Constants.INT15);

				if (StringUtils.isNotBlank(conment)) {
					rs.setMessage(conment.substring(0, conment.length() - 1));
				} else {
					rs.setMessage("操作成功");
				}
				rs.setSuccess(true);
			}
		} catch (Exception e) {
			rs.setSuccess(false);
			rs.setMessage("操作失败");
			LOG.error("异步操作库存:" + e.getMessage(), e);
			e.printStackTrace();
		}

		return rs;
	}

	*//**
	 * @Description： 释放库存操作
	 * @author: QIJJ
	 * @since: 2015-7-14 下午2:31:05
	 *//*
	@RequestMapping(value = "/releaseStock", method = { RequestMethod.GET, RequestMethod.POST })
	public String releaseStock(HttpServletRequest request, HttpServletResponse response, Model model, Long sid, Integer status) {
		LOG.info("Go into AllocateNotify - releaseStock");

		try {
			TransferOrderRpcService transferOrderService = (TransferOrderRpcService) RemoteServiceSingleton.getInstance().getAppService("transferOrderRpcService");
			*//** 释放/休眠库存 *//*
			transferOrderService.releaseStock(sid, status);

			return "redirect:/allocateNotify/getAllocateProductPage";
		} catch (Exception e) {
			LOG.error("异步操作库存:" + e.getMessage(), e);
			e.printStackTrace();
		}

		return null;
	}*/

	/**
	 * @Description： 测试页面操作
	 * @author: QIJJ
	 * @since: 2015-7-6 下午3:14:21
	 */
	@RequestMapping(value = "/operatorFinalData", method = { RequestMethod.GET, RequestMethod.POST })
	public String operatorFinalData(HttpServletRequest request, HttpServletResponse response, Model model, String transferOutWarehouseCode, String transferOutWarehouseName, String pcode,
			String barCode, String pname, String isgenuine, String format, String unit, Long skuId, Long productId) {
		LOG.info("Go into AllocateNotify - operatorStock Page.jsp");

		try {

		} catch (Exception e) {
			LOG.error("异步操作库存:" + e.getMessage(), e);
			e.printStackTrace();
		}

		return "dealerseller/allocateNotify/fff";
	}

}
