package com.mall.controller.inventory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.category.po.AgentCity;
import com.mall.category.po.AgentCounty;
import com.mall.category.po.AgentProvince;
/*import com.mall.dealer.model.Dealer;*/
import com.mall.mybatis.utility.PageBean;
import com.mall.stock.po.SkuStockWarehouseArea;
import com.mall.stock.po.Warehouse;
import com.mall.stock.po.WarehouseChannel;
import com.mall.stock.po.WarehouseStorelevel;
import com.mall.stock.po.WarehouseStoretype;
import com.mall.controller.base.BaseController;
import com.mall.controller.order.BaseDataController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;

/** 
 * @Title：WarehouseController
 * @Description:WOFE库房管理.
 * @author Guofy
 * @Date 2015-5-20 16:43:47
 */
@Controller
@RequestMapping(value="/warehouse")
public class WarehouseController extends BaseController{
  
	/**
	 * LOGGER日志打印.
	 */
	private static final Log LOGGER = LogFactory.getLogger(WarehouseController.class);
	
	/**
	 * @Title：getWarehouse
	 * @Description:初次加载库房列表.
	 * @param model
	 * @return warehouse_list页面
	 */
	@RequestMapping(value="/getWarehouse")
	public String getWarehousetList(Model model){
		return "/dealerseller/warehouse/warehouse_list";
	}
	
	
	/**.
	 * @Title：goAddWareHouse
	 * @Description:加载到添加仓库页面.
	 * @return addwarehouse.jsp页面
	 */
	@RequestMapping(value="/goAddWareHouse")
	public String goAddWareHouse(){
		LOGGER.info("start to execute method <goAddWareHouse()跳转页面>!!!!");
		return "dealerseller/warehouse/addwarehouse";
	}
	
	
	/**.
	 * @Title：getWarehouseModel
	 * @Description:条件搜索库房.
	 * @param model
	 * @param warehousePage
	 * @param warehouse
	 * @return warehouse_list_model.jsp
	 */
	@RequestMapping(value="/getWarehouseModel")
	public String getWareHouseModel(Model model,PageBean<Warehouse> warehousePage ,Warehouse warehouse){
		BaseDataController baseData = new BaseDataController();
		warehousePage.setPageSize(Constants.PAGESIZE);
		warehousePage.setParameter(warehouse);
		List<Long> provinceIds = new ArrayList<Long>();
		List<Long> cityIds = new ArrayList<Long>();
		List<Long> countyIds = new ArrayList<Long>();
		List<SkuStockWarehouseArea> warehouseAreas = new ArrayList<SkuStockWarehouseArea>();
		try {
			warehousePage = RemoteServiceSingleton.getInstance().getStockWofeService().findWarehousePage(warehousePage);
			List<Warehouse> result = warehousePage.getResult();
			
			if(null != result && result.size()>0){
				Map<Long,AgentProvince> provinces = new HashMap<Long, AgentProvince>();
				Map<Long,AgentCity> citys = new HashMap<Long, AgentCity>();
				Map<Long,AgentCounty> countys = new HashMap<Long, AgentCounty>();
				for (Warehouse warehouse2 : result) {
					provinceIds.add(warehouse2.getProvinceId());
					cityIds.add(warehouse2.getCityId());
					countyIds.add(warehouse2.getAreaId());
				}
				if(null != provinceIds && provinceIds.size()>0){
					provinces = baseData.findProvinceByIds(provinceIds.toArray(new Long[provinceIds.size()]));	
				}
				if(null != cityIds && cityIds.size()>0){
					citys = baseData.findCityByIds(cityIds.toArray(new Long[cityIds.size()]));
				}
				if(null != countyIds && countyIds.size()>0){
					countys = baseData.findCountyByIds(countyIds.toArray(new Long[countyIds.size()]));
				}
				for (Warehouse warehouse2 : result) {
					String addressStr = "";
					if(provinces.get(warehouse2.getProvinceId()) != null){
						addressStr = provinces.get(warehouse2.getProvinceId()).getProvincename();
					}
					if(citys.get(warehouse2.getCityId()) != null){
						addressStr = addressStr+" "+citys.get(warehouse2.getCityId()).getCityname();
					}
					if(countys.get(warehouse2.getAreaId()) != null){
						addressStr = addressStr+" "+countys.get(warehouse2.getAreaId()).getCountyname();
					}
					if(warehouse2.getAddress() != null){
						addressStr = addressStr+" "+warehouse2.getAddress();
					}
					warehouse2.setAddress(addressStr);
				}
				
				Map<Long,AgentProvince> provinces1 = new HashMap<Long, AgentProvince>();
				Map<Long,AgentCity> citys1 = new HashMap<Long, AgentCity>();
				Map<Long,AgentCounty> countys1 = new HashMap<Long, AgentCounty>();
				List<Long> provinceIds1 = new ArrayList<Long>();
				List<Long> cityIds1 = new ArrayList<Long>();
				List<Long> countyIds1 = new ArrayList<Long>();
				for (Warehouse warehouse2 : result) {
					if(warehouse2 != null){
						warehouseAreas = warehouse2.getSkuStockWarehouseAreaList();
						if(warehouseAreas != null && warehouseAreas.size()>0){
							for (SkuStockWarehouseArea warehouseAreaList : warehouseAreas) {
								provinceIds1.add(warehouseAreaList.getProvinceId());
								cityIds1.add(warehouseAreaList.getCityId());
								countyIds1.add(warehouseAreaList.getDistrictId());
							}
						}
					}
				}	
				if(null != provinceIds1 && provinceIds1.size()>0){
					provinces1 = baseData.findProvinceByIds(provinceIds1.toArray(new Long[provinceIds1.size()]));	
				}
				if(null != cityIds1 && cityIds1.size()>0){
					citys1 = baseData.findCityByIds(cityIds1.toArray(new Long[cityIds1.size()]));
				}
				if(null != countyIds1 && countyIds1.size()>0){
					countys1 = baseData.findCountyByIds(countyIds1.toArray(new Long[countyIds1.size()]));
				}
				for (Warehouse warehouse2 : result) {
					if(warehouse2 != null){
						warehouseAreas = warehouse2.getSkuStockWarehouseAreaList();
						if(warehouseAreas != null && warehouseAreas.size()>0){
							for (SkuStockWarehouseArea warehouseAreaList : warehouseAreas) {
								String addressStr = "";
								if(provinces1.get(warehouseAreaList.getProvinceId()) != null){
									addressStr = provinces1.get(warehouseAreaList.getProvinceId()).getProvincename();
								}
								if(citys1.get(warehouseAreaList.getCityId()) != null){
									addressStr = addressStr+" "+citys1.get(warehouseAreaList.getCityId()).getCityname();
								}
								if(countys1.get(warehouseAreaList.getDistrictId()) != null){
									addressStr = addressStr+" "+countys1.get(warehouseAreaList.getDistrictId()).getCountyname();
								}
								warehouseAreaList.setAddress(addressStr);
							}
						}
					}
				}	
			}
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("StockWofeService().findWarehousePage(warehousePage) is failed!!!!"+e.getMessage(),e);
		}
		model.addAttribute("pb",warehousePage);
		return "/dealerseller/warehouse/warehouse_list_model";
	}

	
	
	/**
	 * 根据ID
	 * @param warehouse
	 * @return
	 */
	/*@RequestMapping(value="/getWareHouseById")
	public String getWareHouseById(Long id,Model model){
		LOGGER.info("start to execute method <getWareHouseById()根据ID查看需要修改的仓库信息>!!!!");
		LOGGER.info("仓库ID:"+id);
		List<WarehouseStoretype> warehouseStoretypes = new ArrayList<WarehouseStoretype>();
		warehouseStoretypes = RemoteServiceSingleton.getInstance().getWarehouseService().findWarehouseStoretypeByAll();
		List<WarehouseStorelevel> warehouseStorelevels = new ArrayList<WarehouseStorelevel>();
		warehouseStorelevels = RemoteServiceSingleton.getInstance().getWarehouseService().findWarehouseStorelevelByAll();
		List<WarehouseChannel> warehouseChannel = new ArrayList<WarehouseChannel>();
		warehouseChannel = RemoteServiceSingleton.getInstance().getWarehouseService().findChannelByAll();
		List<AgentProvince> provinces = new ArrayList<AgentProvince>();
		List<Dealer> dealers = new ArrayList<Dealer>();
		dealers = RemoteServiceSingleton.getInstance().getDealerService().findAllDealerList();
		model.addAttribute("dealers",dealers);
		try {
			provinces = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().getAllProvices();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		model.addAttribute("warehouseStoretypes",warehouseStoretypes);
		model.addAttribute("warehouseStorelevels",warehouseStorelevels);
		model.addAttribute("warehouseChannel",warehouseChannel);
		model.addAttribute("provinces",provinces);
		Warehouse warehouse = null;
		try {
			
			warehouse = RemoteServiceSingleton.getInstance().getStockWofeService().findWarehouseById(id);	
			model.addAttribute("warehouse", warehouse);
		} catch (Exception e) {
			LOGGER.error("StockWofeService().findWarehouseById(id) is failed!!!!"+e.getMessage(),e);
		}
		if(null != warehouse){
			if(warehouse.getWarehouseLevelCode() != 1){
				List<Warehouse> skuWarehourseList = new ArrayList<Warehouse>();
				skuWarehourseList = RemoteServiceSingleton.getInstance().getWarehouseService().findWarehouseByLevel(warehouse.getWarehouseLevelCode());
				model.addAttribute("skuWarehourseList",skuWarehourseList);
			}
		}
		List<AgentCity> citys = new ArrayList<AgentCity>();
		try {
			citys = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().getCitiesByProvinceId(Integer.parseInt(warehouse.getProvinceId()+""));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		model.addAttribute("citys",citys);
		
		List<AgentCounty> countys = new ArrayList<AgentCounty>();
		try {
			countys = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().getCountiesByCityId(Integer.parseInt(warehouse.getCityId()+""));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		model.addAttribute("countys",countys);
		LOGGER.info("end<> to execute method <getWareHouseById()根据ID查看需要修改的仓库信息>!!!!");
		return "dealerseller/warehouse/updatewarehouse";
	}*/
	
	
	@RequestMapping(value = "/updateWareHouse")
	@ResponseBody
	public String updateWareHouse(Warehouse warehouse,Long[] provinceId1,Long[] cityId1,Long[] arealId1){
		LOGGER.info("start to execute method <updateWareHouse()修改仓库>!!!!");
		LOGGER.info("发货渠道:"+warehouse.getChannelName());
		LOGGER.info("仓库类型:"+warehouse.getWarehouseTypeName());
		LOGGER.info("仓库级别:"+warehouse.getWarehouseLevelName());
		List<SkuStockWarehouseArea> stokAreas = new ArrayList<SkuStockWarehouseArea>();
		String result = "";
		if(provinceId1 != null && provinceId1.length>0){
			for (int i = 0; i < provinceId1.length; i++) {
				SkuStockWarehouseArea stokArea = new SkuStockWarehouseArea();
				stokArea.setProvinceId(provinceId1[i]);
				stokArea.setCityId(cityId1[i]);
				stokArea.setDistrictId(arealId1[i]);
				Integer levelMarkup = 1;
				if(!stokArea.getCityId().equals(new Long(0))){
					levelMarkup = 2 ;
				}
				if(!stokArea.getDistrictId().equals(new Long(0))){
					levelMarkup = 3 ;
				}
				stokArea.setLevelMarkup(levelMarkup);
				stokAreas.add(stokArea);
			}
		}
		try {
			warehouse.setSkuStockWarehouseAreaList(stokAreas);
			//stokAreas.get(0).getDistrictId()
			RemoteServiceSingleton.getInstance().getStockWofeService().updateWarehouseById(warehouse);
			result = "仓库修改成功了!";
		} catch (Exception e) {
			// TODO: handle exception
			result = "仓库修改失败了!";
			LOGGER.error("StockWofeService<updateWarehouseById(warehouse)>修改仓库 is failed!!!!"+e.getMessage(),e);
		}
		LOGGER.info("执行结果:"+result);
		LOGGER.info("end<> to execute method <addWareHouse()修改仓库>!!!!");
		return result;
	}
	
	
	/**.
	 * 添加仓库操作.
	 * @param warehouse
	 * @param provinceId1
	 * @param cityId1
	 * @param arealId1
	 * @return String
	 */
	@RequestMapping(value = "/addWareHouse")
	@ResponseBody
	public String addWareHouse(Warehouse warehouse,Long[] provinceId1,Long[] cityId1,Long[] arealId1){
		LOGGER.info("start to execute method <addWareHouse()添加仓库>!!!!");
		LOGGER.info("发货渠道:"+warehouse.getChannelName());
		LOGGER.info("仓库类型:"+warehouse.getWarehouseTypeName());
		LOGGER.info("仓库级别:"+warehouse.getWarehouseLevelName());
		List<SkuStockWarehouseArea> stokAreas = new ArrayList<SkuStockWarehouseArea>();
		String result = "";
		if(provinceId1 != null && provinceId1.length>0){
			for (int i = 0; i < provinceId1.length; i++) {
				SkuStockWarehouseArea stokArea = new SkuStockWarehouseArea();
				stokArea.setProvinceId(provinceId1[i]);
				stokArea.setCityId(cityId1[i]);
				stokArea.setDistrictId(arealId1[i]);
				Integer levelMarkup = 1;
				if(!stokArea.getCityId().equals(new Long(0))){ 
					levelMarkup = 2 ;
				}
				if(!stokArea.getDistrictId().equals(new Long(0))){
					levelMarkup = 3 ;
				}
				stokArea.setLevelMarkup(levelMarkup);
				stokAreas.add(stokArea);
			}
		}
		try {
			warehouse.setSkuStockWarehouseAreaList(stokAreas);
			RemoteServiceSingleton.getInstance().getStockWofeService().insertWarehouse(warehouse);
			result = "仓库保存成功了!";
		} catch (Exception e) {
			// TODO: handle exception
			result = "仓库保存失败了!";
			LOGGER.error("StockWofeService<insertWarehouse(warehouse)>获取所有仓库类型 is failed!!!!"+e.getMessage(),e);
		}
		LOGGER.info("执行结果:"+result);
		LOGGER.info("end<> to execute method <addWareHouse()添加仓库>!!!!");
		return result;
	}
	
	
	/**.
	 * @Title：goAddWareHouse
	 * @Description:加载仓库类型.
	 * @return json
	 */
	@RequestMapping(value="/getAllWarehouseStoretype")
	@ResponseBody
	public String getAllWarehouseStoretype(){
		LOGGER.info("start to execute method <getAllWarehouseStoretype()获取所有仓库类型>!!!!");
		Object json = "[]";
		List<WarehouseStoretype> warehouseStoretypes = new ArrayList<WarehouseStoretype>();
		try {
			warehouseStoretypes = RemoteServiceSingleton.getInstance().getWarehouseService().findWarehouseStoretypeByAll();
			json = JSONArray.fromObject(warehouseStoretypes);
			//warehouseStoretypes.get(0).getTypeName()
		} catch (Exception e) {
			LOGGER.error("WarehouseService<findWarehouseStoretypeByAll()>获取所有仓库类型 is failed!!!!"+e.getMessage(),e);
		}
		LOGGER.info("仓库类型:"+json.toString());
		LOGGER.info("end<> to execute method <getAllWarehouseStoretype()获取所有仓库类型>!!!!");
		return json.toString();
	}
	
	
	/**.
	 * 获取所有经销商集合.
	 * @return String json
	 */
	/*@RequestMapping(value = "/getDealerAll")
	@ResponseBody
	public String getDealerAll(){
		LOGGER.info("start to execute method <getDealerAll()获取所有经销商列表>!!!!");
		List<Dealer> dealers = new ArrayList<Dealer>();
		Object json = "[]";
		try {
			dealers = RemoteServiceSingleton.getInstance().getDealerService().findAllDealerList();
			json = JSONArray.fromObject(dealers);
			LOGGER.info("获取结果:"+json.toString());
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("DealerService<findAllDealerList()> is failed !!!!"+e.getMessage(),e);
		}
		LOGGER.info("end<> to execute method <getDealerAll()获取所有经销商列表>!!!!");
		return json.toString();
	}*/
	
	

	/**.
	 * @Title：getAllWarehouseStorelevel
	 * @Description:获取仓库级别.
	 * @return json
	 */
	@RequestMapping(value="/getAllWarehouseStorelevel")
	@ResponseBody
	public String getAllWarehouseStorelevel(){
		LOGGER.info("start to execute method <getAllWarehouseStorelevel()获取所有仓库级别>!!!!");
		Object json = "[]";
		List<WarehouseStorelevel> warehouseStorelevels = new ArrayList<WarehouseStorelevel>();
		try {
			warehouseStorelevels = RemoteServiceSingleton.getInstance().getWarehouseService().findWarehouseStorelevelByAll();
			json = JSONArray.fromObject(warehouseStorelevels);
		} catch (Exception e) {
			LOGGER.error("WarehouseService<findWarehouseStorelevelByAll()>获取所有仓库级别 is failed!!!!"+e.getMessage(),e);
		}
		LOGGER.info("仓库级别:"+json.toString());
		LOGGER.info("end<> to execute method <getAllWarehouseStorelevel()获取所有仓库级别>!!!!");
		return json.toString();
	}
	
	
	
	//获取发货渠道
	@RequestMapping(value="/getAllChannel")
	@ResponseBody
	public String getAllChannel(){
		LOGGER.info("start to execute method <getAllChannel()获取发货渠道>!!!!");
		Object json = "[]";
		List<WarehouseChannel> warehouseChannel = new ArrayList<WarehouseChannel>();
		try {
			warehouseChannel = RemoteServiceSingleton.getInstance().getWarehouseService().findChannelByAll();
			json = JSONArray.fromObject(warehouseChannel);
		} catch (Exception e) {
			LOGGER.error("WarehouseService<findChannelByAll()>获取发货渠道 is failed!!!!"+e.getMessage(),e);
		}
		LOGGER.info("发货渠道:"+json.toString());
		LOGGER.info("end<> to execute method <getAllChannel()获取发货渠道>!!!!");
		return JSONArray.fromObject(warehouseChannel).toString();
	}
	
	
	//按仓库级别获取上一级仓库
	@RequestMapping(value="/getWarehouseByLevel")
	@ResponseBody
	public String getWarehouseByLevel(Short warehouseLevelCode){
		LOGGER.info("start to execute method <getWarehouseByLevel()获取上一级仓库>!!!!");
		LOGGER.info("查询参数[warehouseLevelCode]:"+warehouseLevelCode);
		Object json = "[]";
		List<Warehouse> skuWarehourseList = new ArrayList<Warehouse>();
		try {
			skuWarehourseList = RemoteServiceSingleton.getInstance().getWarehouseService().findWarehouseByLevel(warehouseLevelCode);
			json = JSONArray.fromObject(skuWarehourseList);
			//skuWarehourseList.get(0).getWarehouseLevelName()
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}
		LOGGER.info("发货渠道:"+json.toString());
		LOGGER.info("end<> to execute method <getWarehouseByLevel()获取上一级仓库>!!!!");
		return JSONArray.fromObject(skuWarehourseList).toString();
	}
}	