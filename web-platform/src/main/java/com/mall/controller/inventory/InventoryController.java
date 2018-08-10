package com.mall.controller.inventory;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.mall.pay.common.StringUtils;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.mybatis.utility.PageBean;
import com.mall.stock.api.rpc.StockCustomerService;
import com.mall.stock.api.rpc.StockService;
import com.mall.stock.common.StockConstant;
import com.mall.stock.dto.StockCustomerDTO;
import com.mall.stock.dto.StockDealerLockDTO;
import com.mall.stock.dto.StockDealerLocksDTO;
import com.mall.stock.dto.StockSupplyVO;
import com.mall.stock.dto.StockWofeDTO;
import com.mall.stock.po.Stock;
import com.mall.stock.po.StockLockItems;
import com.mall.stock.po.Warehouse;
import com.mall.supplier.model.Supplier;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;
/**.
 * WOFE库存.
 * @author Guofy.
 *
 */
@Controller
@RequestMapping(value="/inventory")
public class InventoryController extends BaseController{
	/**
	 * 日志打印LOGGER.
	 */
	private static final Log LOGGER = LogFactory.getLogger(InventoryController.class);
	
	/**.
	 * 此方法用于日期的转换
	 * @param binder WebDataBinder
	 */ 
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	/**
	 * @return 库存页面.
	 */
	@RequestMapping(value="/getInventoryPage")
	public String getInventoryPage(){
		LOGGER.info("跳转库存页面");
		LOGGER.info("商户Id:"+getCurrentPlatformId());
		LOGGER.info("用户:"+getCurrentUser().getUsername());
		LOGGER.info("用户ID:"+getCurrentUser().getId());
		LOGGER.info("return inventory Page!");
		return "/dealerseller/inventory/inventory";
	}
	
	/**.
	 * 查询赠品库存列表.
	 * @param  stockWofeDto
	 * @param page
	 * @return String
	 */
	@RequestMapping(value="/findStockForGiftWofePage")
	public String findStockForGiftWofePage(Model model,StockWofeDTO stockWofeDto,Integer page){
		LOGGER.info("start to execute method <findStockForGiftWofePage()获取赠品仓库开始>!!!!");
		LOGGER.info("商品名称："+stockWofeDto.getpName());
		LOGGER.info("商品名称："+stockWofeDto.getEndTime());
		LOGGER.info("商品条码："+stockWofeDto.getSkuCode());
		try {
			PageBean<StockWofeDTO> findStockWofePage = new PageBean<StockWofeDTO>();
			PageBean<StockWofeDTO> requestStockWofeDto = new PageBean<StockWofeDTO>();
			requestStockWofeDto.setPage(page==null?Constants.INT1:page);
			requestStockWofeDto.setPageSize(Constants.PAGESIZE);
			stockWofeDto.setOwner(getCurrentUser().getSequenceId());
			requestStockWofeDto.setParameter(stockWofeDto);
			/*findStockWofePage = RemoteServiceSingleton.getInstance().getStockWofeService()
					.findStockForGiftWofePage(requestStockWofeDto);
			model.addAttribute("pb", findStockWofePage);*/
		} catch (Exception e) {
			LOGGER.error("StockWofeService<findStockForGiftWofePage(requestStockWofeDto)>获取赠品仓库结束失败!!,错误信息"+e.getMessage(),e);
		}
		LOGGER.info("end<> to execute method <findStockForGiftWofePage()获取赠品仓库结束>!!!!");
		return "/dealerseller/inventory/modelpage/inventory_merchandise_model";
	}
	
	
	/**.
	 * 查询供应商库存.
	 * @param model
	 * @param pb
	 * @param stockSupplyVO
	 * @return String
	 */
	@RequestMapping("/getSupplierStock")
	public String getSupplierStock(Model model ,PageBean<StockSupplyVO> pb,StockSupplyVO stockSupplyVO){
		LOGGER.info("start to execute method <getSupplierStock()查询供应商库存开始>!!!!");
		List<Long> supplierIdList = new ArrayList<Long>();
		stockSupplyVO.setSupplierIdList(supplierIdList);
		pb.setParameter(stockSupplyVO);
		Integer sqty = stockSupplyVO.getSqty();
		Integer eqty = stockSupplyVO.getEqty();
		if(null != eqty && null == sqty){
			stockSupplyVO.setSqty(0);
		}
		List<Long> supplierIds = new ArrayList<Long>();
		if(stockSupplyVO.getSupplierName()!=null){
			supplierIds = RemoteServiceSingleton.getInstance().getSupplierManagerService().getSupplierIdsByName(stockSupplyVO.getSupplierName());
		}
		if(null != supplierIds && supplierIds.size()>0){
			stockSupplyVO.setSupplierIdList(supplierIds);
		}
		Map<String, List<StockSupplyVO>> itemInStock = new LinkedHashMap<String, List<StockSupplyVO>>();
		try {
			if((null != stockSupplyVO.getSupplierName() && supplierIds.size()>0)||null == stockSupplyVO.getSupplierName()){
				pb = RemoteServiceSingleton.getInstance().getStockSupplyService().findItemInStockListPage(pb);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}
		List<StockSupplyVO> result = pb.getResult();
		if(null != result){
			for (StockSupplyVO stockSupplyVO2 : result) {
				Supplier supplier = new Supplier();
				if(null != stockSupplyVO2.getSupplierId()){
					supplier = RemoteServiceSingleton.getInstance().getSupplierManagerService().findSupplier(stockSupplyVO2.getSupplierId());
				}
				if(null != supplier){
					String name = supplier.getName();
					stockSupplyVO2.setSupplierName(name);
				}
				List<StockSupplyVO> list=new  ArrayList<StockSupplyVO>();
				if(!itemInStock.containsKey(stockSupplyVO2.getpName())){
					list.add(stockSupplyVO2);
					itemInStock.put(stockSupplyVO2.getpName(), list);
				}else{
					list=itemInStock.get(stockSupplyVO2.getpName());
					list.add(stockSupplyVO2);
					itemInStock.remove(stockSupplyVO2.getpName());
					itemInStock.put(stockSupplyVO2.getpName(), list);
				}	
			}
		}
		model.addAttribute("data",itemInStock);
		model.addAttribute("pb",pb);
		model.addAttribute("isItemInStock",true);
		LOGGER.info("end<> to execute method <getSupplierStock()查询供应商库存结束>!!!!");
		return "/dealerseller/inventory/modelpage/supplierStockModel";
	}
	
	
	/**.
	 * 供应商预定库存列表.
	 * @param model
	 * @param pb
	 * @param stockSupplyVO
	 * @return String
	 */
	@RequestMapping("/getSupplierCollectionStock")
	public String getSupplierCollectionStock(Model model ,PageBean<StockSupplyVO> pb ,StockSupplyVO stockSupplyVO){
		List<Long> supplierIdList = new ArrayList<Long>();
		stockSupplyVO.setSupplierIdList(supplierIdList);
		pb.setParameter(stockSupplyVO);
		List<Long> supplierIds = new ArrayList<Long>();
		if(stockSupplyVO.getSupplierName()!=null){
			supplierIds = RemoteServiceSingleton.getInstance().
					getSupplierManagerService().getSupplierIdsByName(stockSupplyVO.getSupplierName());
			
		}
		if(null != supplierIds && supplierIds.size()>0){
			stockSupplyVO.setSupplierIdList(supplierIds);
		}
		Map<String, List<StockSupplyVO>> itemInStock = new LinkedHashMap<String, List<StockSupplyVO>>();
		try{
			if((null != stockSupplyVO.getSupplierName() && supplierIds.size()>0)||
					null == stockSupplyVO.getSupplierName()){
				pb = RemoteServiceSingleton.getInstance().getStockSupplyService().findCollectionQtyListPage(pb);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}
		List<StockSupplyVO> result = pb.getResult();
		if(null != result){
			for (StockSupplyVO stockSupplyVO2 : result) {
				Supplier supplier = new Supplier();
				if(null != stockSupplyVO2.getSupplierId()){
					supplier = RemoteServiceSingleton.getInstance().getSupplierManagerService().findSupplier(stockSupplyVO2.getSupplierId());
				}
				if(null != supplier){
					String name = supplier.getName();
					stockSupplyVO2.setSupplierName(name);
				}
				List<StockSupplyVO> list=new  ArrayList<StockSupplyVO>();
				if(!itemInStock.containsKey(stockSupplyVO2.getpName())){
					list.add(stockSupplyVO2);
					itemInStock.put(stockSupplyVO2.getpName(), list);
				}else{
					list=itemInStock.get(stockSupplyVO2.getpName());
					list.add(stockSupplyVO2);
					itemInStock.remove(stockSupplyVO2.getpName());
					itemInStock.put(stockSupplyVO2.getpName(), list);
				}
			}
		}	
		model.addAttribute("data",itemInStock);
		model.addAttribute("pb",pb);
		model.addAttribute("isItemInStock",false);
		return "/dealerseller/inventory/modelpage/collectionModel";
	}
	
	
	/**.
	 * 查询到岸库存列表
	 * @param stockWofeDto
	 * @param page
	 * @return String
	 */
	@RequestMapping(value="/getAllInventoryList")
	public String getAllInventoryList(Model model,StockWofeDTO stockWofeDto,Integer page){
		try {
			PageBean<StockWofeDTO> findStockWofePage = new PageBean<StockWofeDTO>();
			PageBean<StockWofeDTO> requestStockWofeDto = new PageBean<StockWofeDTO>();
			requestStockWofeDto.setPage(page==null?Constants.INT1:page);
			requestStockWofeDto.setPageSize(Constants.PAGESIZE);
			stockWofeDto.setOwner(getCurrentUser().getSequenceId());
			requestStockWofeDto.setParameter(stockWofeDto);
			/*findStockWofePage = RemoteServiceSingleton.getInstance().getStockWofeService().findStockWofePage(requestStockWofeDto);
			model.addAttribute("pb", findStockWofePage);*/
		} catch (Exception e) {
			LOGGER.error("查询库存信息失败,信息"+e.getMessage(),e);
		}
		return "/dealerseller/inventory/modelpage/inventory_model";
	}
	
	/**.
	 * 导出到岸库存列表.
	 * @param request HttpServletRequest
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/downExcel")
	public void downExcel(Model model ,HttpServletRequest request,HttpServletResponse response
			,StockWofeDTO stockWofeDto,Integer downType){
		LOGGER.info("导出类型1为赠品导出.0为现货导出:"+downType);
		PageBean<StockWofeDTO> findStockWofePage = new PageBean<StockWofeDTO>();
		PageBean<StockWofeDTO> requestStockWofeDto = new PageBean<StockWofeDTO>();
		requestStockWofeDto.setPage(Constants.INT1);
		requestStockWofeDto.setPageSize(Constants.MAXPAGESIZE);
		stockWofeDto.setOwner(getCurrentUser().getSequenceId());
		requestStockWofeDto.setParameter(stockWofeDto);
		try {
			/*if(downType == 1){
				//导出赠品库存
				findStockWofePage = RemoteServiceSingleton.getInstance().getStockWofeService().findStockForGiftWofePage(requestStockWofeDto);
			}else{
				//导出到岸库存
				findStockWofePage = RemoteServiceSingleton.getInstance().getStockWofeService().findStockWofePage(requestStockWofeDto);
			}*/
		} catch (Exception e) {
			LOGGER.error("查询库存信息失败,信息"+e.getMessage(),e);
		}
		try {
			if ( null != findStockWofePage && null != findStockWofePage.getResult()){
				List<StockWofeDTO> result = findStockWofePage.getResult();
				String title = "inventoryList";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DEFAULTDATE);
				OutputStream os = response.getOutputStream();;
				HSSFWorkbook book = new HSSFWorkbook();
				HSSFSheet sheet = book.createSheet(title);
				HSSFRow row = sheet.createRow((int) 0);
				HSSFCell cell =null;
				String [] strtitle = {"经销商名称","商品名称","批次号","规格","条形码","库存类型","库存数量","锁定数量","订单占用数量","仓库","入库时间"};
				for (int i = 0; i < strtitle.length; i++) {
					cell = row.createCell(i);
					cell.setCellValue(strtitle[i]);
				}
				for (int i = 0; i < result.size(); i++) {
					row = sheet.createRow((int) i + 1);
					StockWofeDTO stock = result.get(i);
					String dealerName = stock.getDealerName();
					String getpName = stock.getpName();
					String batchNo = stock.getBatchNo();
					String skuName = stock.getSkuName();
					String skuCode = stock.getSkuCode();
					int ownerType = stock.getOwnerType();
					String sumqty = stock.getSumqty()+"";
					String lockQty = stock.getLockQty()+"";
					String preSubQty = stock.getPreSubQty()+"";
					String warehouseName = stock.getWarehouseName();
					String createTime = simpleDateFormat.format( stock.getCreateTime());

					row.createCell(Constants.SHORT0).setCellValue(dealerName);
					row.createCell(Constants.SHORT1).setCellValue(getpName);
					row.createCell(Constants.SHORT2).setCellValue(batchNo);
					row.createCell(Constants.SHORT3).setCellValue(skuName);
					row.createCell(Constants.SHORT4).setCellValue(skuCode);
					String strOwnerType = "";
					switch (ownerType) {
					case Constants.INT1: strOwnerType = "自由库存";
					break;
					default: strOwnerType = "其他库存";
					break;
					}
					row.createCell(Constants.SHORT5).setCellValue(strOwnerType);
					row.createCell(Constants.SHORT6).setCellValue(sumqty);
					row.createCell(Constants.SHORT7).setCellValue(lockQty);
					row.createCell(Constants.SHORT8).setCellValue(preSubQty);
					row.createCell(Constants.SHORT9).setCellValue(warehouseName);
					row.createCell(Constants.SHORT10).setCellValue(createTime);
				}
				response.reset();
				response.setCharacterEncoding("UTF-8");
				 response.setHeader("Content-Disposition", "attachment;filename="
		                    + new String(title.getBytes("UTF-8"), "GBK") + ".xls");
			     response.setContentType("application/msexcel");// 定义输出类型
			     book.write(os);
			     response.getOutputStream().flush();
			     response.getOutputStream().close();
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}
			model.addAttribute("pb", findStockWofePage);	
	}
	

	/**.
	 * 查询库存列表
	 * @param model
	 * @param stockWofeVo
	 * @param page
	 * @return String
	 */
	@RequestMapping(value="/getSupplierInventoryList")
	public String getSupplierInventoryList(Model model,StockSupplyVO stockWofeVo,Integer page){
		PageBean<StockSupplyVO> findStockWofePage = new PageBean<StockSupplyVO>();
		PageBean<StockSupplyVO> requestStockWofeVo = new PageBean<StockSupplyVO>();
		try {
			requestStockWofeVo.setPage(page==null?Constants.INT1:page);
			requestStockWofeVo.setPageSize(Constants.PAGESIZE);
			List<Long> supplierIds = new ArrayList<Long>();
			if(stockWofeVo.getSupplierName()!=null){
				supplierIds = RemoteServiceSingleton.getInstance().getSupplierManagerService().getSupplierIdsByName(stockWofeVo.getSupplierName());
			}
			if(null != supplierIds && supplierIds.size()>0){
				stockWofeVo.setSupplierIdList(supplierIds);
			}
			requestStockWofeVo.setParameter(stockWofeVo);
 			if(null != findStockWofePage.getResult()){
 				for(int i = 0 ; i < findStockWofePage.getResult().size() ; i++){
 					StockSupplyVO stockSupplyVO = findStockWofePage.getResult().get(i);
 					Supplier supplier = new Supplier();
 					if(null != stockSupplyVO.getSupplierId()){
 						supplier = RemoteServiceSingleton.getInstance().getSupplierManagerService().findSupplier(stockSupplyVO.getSupplierId());
 					}
 					if(null != supplier){
 						String name = supplier.getName();
 						findStockWofePage.getResult().get(i).setSupplierName(name);
 					}
 				}
 			}
			
    	} catch (Exception e) {
    		LOGGER.error("查询库存信息失败,信息"+e.getMessage(),e);
    		LOGGER.error("查询库存信息失败,用户:"+getCurrentUser().getUsername());
		} 
		model.addAttribute("pb", findStockWofePage);
		return "/inventory/modelpage/inventory_supplier_model";
	}
	
	/**.
	 * 
	 * @param batchNO 库存Id主键.
	 * @return String
	 */
	@RequestMapping(value="/editFindInventoryList")
	@ResponseBody
	public String editFindInventoryList(Long batchNO){
		
		Stock stock = new Stock();
	
		try {
			
			stock = RemoteServiceSingleton.getInstance().getStockWofeService().findStkWofeById(batchNO);
			
			JSONObject json = JSONObject.fromObject(stock);
			
			return json.toString();
			
		} catch (Exception e) {
    		
			LOGGER.error("查询库存信息失败,错误信息:"+e.getMessage(),e);
    		LOGGER.error("查询库存信息失败,商户ID:"+getCurrentPlatformId());
    		
    		LOGGER.error("查询库存信息失败,用户:"+getCurrentUser().getUsername());
    		LOGGER.error("查询库存信息失败,用户ID:"+getCurrentUser().getId());
    		
    		LOGGER.error("查询库存信息失败,批次号:"+batchNO);
    		
    		
			return "-1";
			
		}
	}
	
	
	/**.
	 * 修改库存
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param id Long
	 * @param qty  Integer
	 * @return status.
	 */
	@RequestMapping(value="/editStock")
	@ResponseBody
	public String editStock(HttpServletRequest request,HttpServletResponse response,
			Long id , Integer qty){

		int updateStockWofeById = Constants.INT1;
		
		try {
			
			updateStockWofeById = RemoteServiceSingleton.getInstance().getStockWofeService()
					.updateStockWofeByIdNew(id, qty,getCurrentUser().getId());
			
			return  updateStockWofeById+"";
			
		} catch (Exception e) {
			
			updateStockWofeById = Constants.INT0;
			
			LOGGER.error("修改库存错误,错误信息:"+e.getMessage(),e);
    		
			LOGGER.error("商户Id:"+getCurrentPlatformId());
			LOGGER.error("用户:"+getCurrentUser().getUsername());
			LOGGER.error("用户ID:"+getCurrentUser().getId());
    		
    		LOGGER.error("修改库存错误,库存Id:"+id);
    		
    		
		}
		
		return  updateStockWofeById+"";
		
	}
	/**.
	 * 锁定库存数量
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param id Long
	 * @param lockQty  Integer
	 * @return status.
	 */
	@RequestMapping(value="/lockStock")
	@ResponseBody
	public String lockStock(HttpServletRequest request,HttpServletResponse response,
			Long id , Integer lockQty){
		
		LOGGER.info(" 锁定库存数量");
		
		int stat = Constants.INT0;
		
		try {
			
			stat = RemoteServiceSingleton.getInstance().getStockWofeService()
					.updateStockWofeLockStockById(id,lockQty);
			
			stat = Constants.INT1;
			
		} catch (Exception e) {

			LOGGER.error("锁定库存数量,错误信息:"+e.getMessage(),e);

			LOGGER.error("商户Id:"+getCurrentPlatformId());
			LOGGER.error("用户:"+getCurrentUser().getUsername());
			LOGGER.error("用户ID:"+getCurrentUser().getId());
    		
    		LOGGER.error("锁定库存错误,库存Id:"+id);
			
		}
		
		return   stat+"";
		
	}
	
	
	@RequestMapping(value="loadLockQty")
	@ResponseBody
	public String loadLockQty(Long id, Long skuId , Long pid, String lockBatch
			,Long owner,Integer ownerType,Long dealerId){
		LOGGER.info("id:"+id);
		LOGGER.info("skuId:"+skuId);
		LOGGER.info("pid:"+pid);
		LOGGER.info("lockBatch:"+lockBatch);
		LOGGER.info("owner:"+owner);
		LOGGER.info("ownerType:"+ownerType);
		LOGGER.info("dealerId:"+dealerId);
		StockDealerLocksDTO stockLocks = new StockDealerLocksDTO();
		String jsonString = "";
		try {
			
			stockLocks = RemoteServiceSingleton.getInstance().
					getStockDealerService().findStockLock(id,skuId,pid, lockBatch,owner, ownerType, dealerId);
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			if(null != stockLocks){
				if(null != stockLocks.getStockDealerLockDTOList() && stockLocks.getStockDealerLockDTOList().size()>0){
					List<StockDealerLockDTO> stockDealerLockDTOs = stockLocks.getStockDealerLockDTOList();
					
					for (StockDealerLockDTO stockDealerLockDTO:stockDealerLockDTOs) {
						
						stockDealerLockDTO.setStrid(stockDealerLockDTO.getId()+"");
						
						if(null != stockDealerLockDTO.getCreateTime()){
							stockDealerLockDTO.setStrTime(format.format(stockDealerLockDTO.getCreateTime()));
						}else{
							stockDealerLockDTO.setStrTime("");
						}
					}
				}
				jsonString = JSONArray.toJSONString(stockLocks);
			}

		} catch (Exception e) {
			
			LOGGER.error("StockDealerService(findStockLock) is failed!!!!"+e.getMessage(),e);
			
		}
		return jsonString;
	}
   
	
	@RequestMapping(value="/delLockQty")
	@ResponseBody
	public String delLockQty(Long stockId ,Long id){
		
		String delstat = Constants.SUCCESS;
		
		try {
			
			String userId = getCurrentUser().getId()+"";
			
			
			RemoteServiceSingleton.getInstance().
				getStockDealerService().deleteStockLock(stockId, userId, id);
			
		} catch (Exception e) {
			
			delstat= Constants.ERROR;
			
			LOGGER.error(e.getMessage(),e);
		}
		
		return delstat;
		
	}
	
	
	@RequestMapping(value="/addLockQty")
	@ResponseBody
	public String addLockQty(Long[] id,Long[] dealerId,String[] lockBatch,Long[] lockQty
			,Integer[] lockReason,Integer[] ownerType,Long[] pid,String[] pName
			, Long[] skuId, String[] skuName){
		
		String lockstat = Constants.SUCCESS;
		
		List<StockLockItems> stockLockItems  = new ArrayList<StockLockItems>();
		
		try {
			if(dealerId.length>0){

				for(int i = 0 ; i < dealerId.length ; i++){

					StockLockItems stockLockItem = new StockLockItems();

					long dealerids = dealerId[i];
					long lockQtys = lockQty[i];
					long pids = pid[i];
					long ids = id[i];
					long skuIds = skuId[i];
					String lockBatchs = lockBatch[i];
					String pNames = pName[i];
					String skuNames = skuName[i];
					int lockReasons = lockReason[i];
					int ownerTypes = ownerType[i];
					
					stockLockItem.setId(ids);
					stockLockItem.setDealerId(dealerids);
					stockLockItem.setLockQty(lockQtys);
					stockLockItem.setPid(pids);
					stockLockItem.setSkuId(skuIds);
					stockLockItem.setLockBatch(lockBatchs);
					stockLockItem.setpName(pNames);
					stockLockItem.setSkuName(skuNames);
					stockLockItem.setLockReason(lockReasons);
					stockLockItem.setOwnerType(ownerTypes);
					stockLockItem.setSkuType(1l);

					stockLockItems.add(stockLockItem);
				}

				String userId = getCurrentUser().getId()+"";
				
				RemoteServiceSingleton.getInstance().
					getStockDealerService().initStockLock(stockLockItems, userId);
			}
		} catch (Exception e) {
			
			lockstat = Constants.ERROR;
			LOGGER.error(e.getMessage(),e);
	
		}

		
		return lockstat;
	}
	
	@RequestMapping(value="/loadWareSelect")
	@ResponseBody
	public String loadWareSelect(HttpServletRequest request){
		
		String loadWareSelect = "";

		String type = request.getParameter("type");//warehouse type
		
		List<Warehouse> findWarehouseEnum = new ArrayList<Warehouse>();

		if(StringUtils.isNotEmpty(type)) {
			try {

				findWarehouseEnum = RemoteServiceSingleton.getInstance().getStockWofeService().findWarehouseEnum(Short.valueOf(type));

			} catch (Exception e) {

				LOGGER.error(e.getMessage(),e);

			}
		}

		if (null != findWarehouseEnum){
			
			loadWareSelect = JSONArray.toJSONString(findWarehouseEnum);
			
		}
		
		return loadWareSelect;
		
	}
	
	

	
	/**.
	 * 查询转运仓库列表
	 * @param request HttpServletRequest
	 * @return
	 */
	@RequestMapping(value="/getTransitDepotList")
	public String getTransitDepotList(Model model ,HttpServletRequest request,HttpServletResponse response,
			StockCustomerDTO stockCustomerDTO,PageBean<StockCustomerDTO> pageBean){
		LOGGER.info("start to execute method <getTransitDepotList()查询转运仓库列表开始>!!!!");
		try {
			pageBean.setPageSize(Constants.PAGESIZE);
			stockCustomerDTO.setWarehouseType(StockConstant.WAREHOUSE_TYPE_OVERSEAS);
			pageBean.setParameter(stockCustomerDTO);
			StockCustomerService stockCustomerService = RemoteServiceSingleton.getInstance().getStockCustomerService();
			pageBean = stockCustomerService.getStkCusByWarehouseCodeListPage(pageBean);
		} catch (Exception e) {
			LOGGER.error("stockCustomerService<getStockCustomerService()>查询转运仓库列表 is failed!!!"+e.getMessage(),e);
		}
		model.addAttribute("pb", pageBean);
		LOGGER.info("end<> to execute method <getTransitDepotList()查询转运仓库列表结束>!!!!");
		return "/dealerseller/inventory/modelpage/inventory_customer_model";
	}


	/**
	 * 查询保税区列表
	 * getBondedAreaList
	 * @param model
	 * @param stockCustomerDTO
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value="/getBondedAreaList")
	public String getBondedAreaList(Model model,StockCustomerDTO stockCustomerDTO,PageBean<StockCustomerDTO> pageBean){
		LOGGER.info("start to execute method <getBondedAreaList()查询保税区列表开始>!!!!");
		try {
			stockCustomerDTO.setWarehouseType(StockConstant.WAREHOUSE_TYPE_BONDED_AREA);
			LOGGER.info("商品名称"+stockCustomerDTO.getpName());
			LOGGER.info("类型："+stockCustomerDTO.getWarehouseType());
			LOGGER.info("结束时间"+stockCustomerDTO.getEndTime());
			pageBean.setPageSize(Constants.PAGESIZE);
			pageBean.setParameter(stockCustomerDTO);
			pageBean = RemoteServiceSingleton.getInstance().
					getStockCustomerService().getStkCusByWarehouseCodeListPage(pageBean);
		} catch (Exception e) {
			LOGGER.error("查询库存信息失败,信息"+e.getMessage(),e);
		}
		model.addAttribute("pb", pageBean);
		LOGGER.info("end<> to execute method <getBondedAreaList()查询保税区列表结束>!!!!");
		return "/dealerseller/inventory/modelpage/inventory_bondedarea_model";
	}
	
	/**
	 * 查询卓悦库存列表.
	 * getZhuoYueList
	 * @param model
	 * @param stockCustomerDTO
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value="/getZhuoYueList")
	public String getZhuoYueList(Model model,StockCustomerDTO stockCustomerDTO,PageBean<StockCustomerDTO> pageBean){
		LOGGER.info("start to execute method <getZhuoYueList()查询卓悦库存列表开始>!!!!");
		try {
			stockCustomerDTO.setWarehouseType((short)4);
			LOGGER.info("商品名称"+stockCustomerDTO.getpName());
			LOGGER.info("类型："+stockCustomerDTO.getWarehouseType());
			LOGGER.info("结束时间"+stockCustomerDTO.getEndTime());
			pageBean.setPageSize(Constants.PAGESIZE);
			pageBean.setParameter(stockCustomerDTO);
			pageBean = RemoteServiceSingleton.getInstance().
					getStockCustomerService().getStkCusByWarehouseCodeListPage(pageBean);
		} catch (Exception e) {
			LOGGER.error("查询卓悦库存信息失败,信息"+e.getMessage(),e);
		}
		model.addAttribute("pb", pageBean);
		LOGGER.info("end<> to execute method <getZhuoYueList()查询卓悦库存列表结束>!!!!");
		return "/dealerseller/inventory/modelpage/inventory_zhuoyue_model";
	}
	
	
	@RequestMapping(value="/addLockQtyForCustomer")
	@ResponseBody
	public String addLockQtyForCustomer(Long id,Integer lockQty){
		String lockstat = Constants.SUCCESS;		
		try {			
			RemoteServiceSingleton.getInstance().
						getStockCustomerService ().updateLockQtyById(id, lockQty);			
		} catch (Exception e) {			
			lockstat = Constants.ERROR;
			LOGGER.error(e.getMessage(),e);	
		}		
		return lockstat;
	}
	
	/**
	 * wofe把库存转移到赠品库存中去

	 * @return String
	 */
	@RequestMapping(value="/moveToGift")
	@ResponseBody
	public String moveToGift(Long id,Integer qty){
		String moveStatus = Constants.SUCCESS;
		try {
			RemoteServiceSingleton.getInstance().
			getStockService().transferGiftNew(id, qty, getCurrentUser().getUsername());
		} catch (Exception e) {
			moveStatus = Constants.ERROR;
			LOGGER.error(e.getMessage(),e);
		}
		return moveStatus;
	}
	
	/**
	 * wofe把赠品转移到正常品中去.
	 * @param  id
	 * @param  qty
	 * @return String
	 */
	@RequestMapping(value = "moveGiftToStock")
	@ResponseBody
	public String moveGiftToStock(Long id,Integer qty){
		LOGGER.info("主键修改的ID:"+id);
		LOGGER.info("主键修改的数量:"+qty);
		LOGGER.info("start to execute method <moveGiftToStock()赠品库存转移到正常库存功能开始>!!!!");
		String result = "";
		try {
			StockService stockService = RemoteServiceSingleton.getInstance().getStockService();
			stockService.returnGiftNew(id, qty, getCurrentUser().getUsername());
			result = "操作成功!";
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("StockService<returnGift() is failed!!!!> "+e.getMessage(),e);
			result = "操作失败!";
		}
		LOGGER.info("end<> to execute method <moveGiftToStock()赠品库存转移到正常库存功能结束>!!!!");
		return result;
	}

	/**
	 * 获取POP商家库存信息.
	 * @param  page 分页
	 * @return String
	 */
	@RequestMapping(value = "getPopInventory")
	public String getPopInventory(Model model, StockWofeDTO stockWofeDto, Integer page){
		LOGGER.info("查看POP商家库存");
		try {
			PageBean<StockWofeDTO> findStockWofePage = new PageBean<StockWofeDTO>();
			PageBean<StockWofeDTO> requestStockWofeDto = new PageBean<StockWofeDTO>();
			requestStockWofeDto.setPage(page==null?Constants.INT1:page);
			requestStockWofeDto.setPageSize(Constants.PAGESIZE);
			stockWofeDto.setOwner(getCurrentUser().getSequenceId());
			requestStockWofeDto.setParameter(stockWofeDto);
			findStockWofePage = RemoteServiceSingleton.getInstance().getStockWofeService().findPopStockWofePage(requestStockWofeDto);
			model.addAttribute("pb", findStockWofePage);
		} catch (Exception e) {
			LOGGER.error("查询库存信息失败,信息"+e.getMessage(), e);
		}
		return "/dealerseller/inventory/modelpage/inventory_pop_model";
	}
}
	
