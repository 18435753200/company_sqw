package com.mall.controller.inventory;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.stock.po.WarehouseChannel;
import com.mall.stock.po.WarehouseStorelevel;
import com.mall.stock.po.WarehouseStoretype;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;

/**
 * @Title：WarehouseController
 * @Description:WOFE库房管理.
 * @author Guofy
 * @Date 2015-5-20 16:43:47
 */
@Controller
@RequestMapping(value="/warehouseInfras")
public class WarehouseInfrastController extends BaseController{
	
	/**
	 * LOGGER日志打印.
	 */
	private static final Log LOGGER = LogFactory.getLogger(WarehouseInfrastController.class);
	
	/**.
	 * @Title：getAllWarehousesStorelevel
	 * @Description:获取仓库级别显示.
	 * @return 
	 */
	@RequestMapping(value="/getAllWarehousesStorelevel")
	public String getAllWarehousesStorelevel(Model model){
		
		List<WarehouseStorelevel> warehouseStorelevels = new ArrayList<WarehouseStorelevel>();
		try {
			warehouseStorelevels = RemoteServiceSingleton.getInstance().getWarehouseService().findWarehouseStorelevelByAll();

		} catch (Exception e) {
		
			LOGGER.error(e.getMessage(),e);
		
		}
		
		model.addAttribute("warehouseStorelevel",warehouseStorelevels);
		return "dealerseller/warehouse/warehouseLevel/warehouseLevel";	
	
	}
	
	
	/**.
	 * @Title：goAddWareHouse
	 * @Description:加载到添加仓库级别页面.
	 * @return addwarehouse.jsp页面
	 */
	@RequestMapping(value="/goAddWareHouseLevel")
	public String goAddWareHouseLevel(){
		LOGGER.info("start to execute method <goAddWareHouseLevel()跳转页面>!!!!");
		return "dealerseller/warehouse/warehouseLevel/addwarehouselevel";
	}
	
	
	/**.
	 * 保存库房级别
	 * @param request HttpServletRequest
	 * @param 
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/addWareHouseLevel")
	@ResponseBody
	public String addWareHouseLevel(HttpServletRequest request,WarehouseStorelevel wareHouseLevel){
		LOGGER.info("start to execute method <addCurrency()添加库房级别>!!!!");
		LOGGER.info(":"+wareHouseLevel.getLevelName());
		String result = "";
		try {
			RemoteServiceSingleton.getInstance().getWarehouseService().insertWarehouseStorelevel(wareHouseLevel);
			result = "库房级别保存成功了!";
		} catch (Exception e) {
			// TODO: handle exception
			result = "库房级别保存失败了!";
			LOGGER.error("WarehouseService<insertWarehouseStorelevel(wareHouseLevel)>获取所有库房级别 is failed!!!!"+e.getMessage(),e);
		}
		LOGGER.info("end<> to execute method <addWareHouseLevel()添加库房级别>!!!!");
		return result;
		
	}
	
	
	/**.
	 * 获取仓库级别编辑数据获取相应的值
	 * @param request HttpServletRequest
	 * @param 
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/editWareHouseLevel")

	public String editWareHouseLevel(HttpServletRequest request,HttpServletResponse response,
			Long sid){	
		WarehouseStorelevel warehouseStorelevel = new WarehouseStorelevel();
		try {
			warehouseStorelevel = RemoteServiceSingleton.getInstance().getWarehouseService().findWarehouseStorelevelBySid(sid);//getInfrastructureService().findTaxBySid(levelCode);				
		} catch (Exception e) {
			
			LOGGER.error("WarehouseService<findWarehouseStorelevelBySid(id)>获取库房级别 is failed!!!!"+e.getMessage(),e);
		}
		LOGGER.info("end<> to execute method <editWareHouseLevel()跳转修改库房级别页面>!!!!");
		request.setAttribute("warehouseStorelevel", warehouseStorelevel);
		 return "dealerseller/warehouse/warehouseLevel/editwarehouselevel";
		
	}
	
	
	/**.
	 * 保存库房级别
	 * @param request HttpServletRequest
	 * @param 
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/saveWareHouseLevel")
	@ResponseBody
	public String saveWareHouseLevel(HttpServletRequest request,WarehouseStorelevel wareHouseLevel){
		LOGGER.info("start to execute method <addCurrency()修改保存库房级别>!!!!");
		LOGGER.info(":"+wareHouseLevel.getLevelName());
		String result = "";
		try {
			RemoteServiceSingleton.getInstance().getWarehouseService().updateWarehouseStorelevelBySid(wareHouseLevel);
			result = "库房级别修改成功了!";
		} catch (Exception e) {
			// TODO: handle exception
			result = "库房级别修改失败了!";
			LOGGER.error("WarehouseService<updateWarehouseStorelevelBySid(wareHouseLevel)>获取所有库房级别 is failed!!!!"+e.getMessage(),e);
		}
		LOGGER.info("end<> to execute method <saveWareHouseLevel()修改保存库房级别>!!!!");
		return result;
		
	}
	
		
		/**.
		 * @Title：getAllWarehousesStorelevel
		 * @Description:获取仓库类型显示.
		 * @return 
		 */
		@RequestMapping(value="/getAllWarehousesStoretype")
		public String getAllWarehousesStoretype(Model model){
			
			List<WarehouseStoretype> warehouseStoretypes = new ArrayList<WarehouseStoretype>();
			try {
				warehouseStoretypes = RemoteServiceSingleton.getInstance().getWarehouseService().findWarehouseStoretypeByAll();

			} catch (Exception e) {
			
				LOGGER.error(e.getMessage(),e);
			
			}
			
			model.addAttribute("warehouseStoretype",warehouseStoretypes);
			return "dealerseller/warehouse/warehouseStoretyp/warehouseType";	
		
		}
		
	

		/**.
		 * @Title：goAddWareHouse
		 * @Description:加载到添加库房类型页面.
		 * @return addwarehouse.jsp页面
		 */
		@RequestMapping(value="/goAddWarehouseStoretype")
		public String goAddWarehouseStoretype(){
 			LOGGER.info("start to execute method <goAddWarehouseStoretype()跳转页面>!!!!");
			return "dealerseller/warehouse/warehouseStoretyp/addwarehouseStoretype";
		}
		
		
		/**.
		 * 保存库房类型
		 * @param request HttpServletRequest
		 * @param 
		 * @return ModelAndView
		 */
		@RequestMapping(value = "/addWarehouseStoretype")
		@ResponseBody
		public String addWarehouseStoretype(HttpServletRequest request,WarehouseStoretype warehouseStoretype){
			LOGGER.info("start to execute method <addCurrency()添加库房类型>!!!!");
			LOGGER.info(":"+warehouseStoretype.getTypeName());
			String result = "";
			try {
				RemoteServiceSingleton.getInstance().getWarehouseService().insertWarehouseStoretype(warehouseStoretype);
				result = "库房类型保存成功了!";
			} catch (Exception e) {
				// TODO: handle exception
				result = "库房类型保存失败了!";
				LOGGER.error("WarehouseService<insertWarehouseStoretype(warehouseStoretype)>获取所有库房类型 is failed!!!!"+e.getMessage(),e);
			}
			LOGGER.info("end<> to execute method <addWarehouseStoretype()添加库房类型>!!!!");
			return result;
			
		}
		
		
		/**.
		 * 获取仓库类型编辑数据获取相应的值
		 * @param request HttpServletRequest
		 * @param 
		 * @return ModelAndView
		 */
		@RequestMapping(value = "/editWarehouseStoretype")

		public String editWarehouseStoretype(HttpServletRequest request,HttpServletResponse response,
				Long sid){	
			WarehouseStoretype warehouseStoretype = new WarehouseStoretype();
			try {
				warehouseStoretype = RemoteServiceSingleton.getInstance().getWarehouseService().findWarehouseStoretypeBySid(sid);				
			} catch (Exception e) {
				
				LOGGER.error("WarehouseService<findWarehouseStoretypeBySid(Sid)>获取库房类型 is failed!!!!"+e.getMessage(),e);
			}
			LOGGER.info("end<> to execute method <editWarehouseStoretype()跳转修改库房类型页面>!!!!");
			request.setAttribute("warehouseStoretype", warehouseStoretype);
			 return "dealerseller/warehouse/warehouseStoretyp/editwarehouseStoretype";
			
		}
		
		
		/**.
		 * 修改保存库房类型
		 * @param request HttpServletRequest
		 * @param 
		 * @return ModelAndView
		 */
		@RequestMapping(value = "/saveWarehouseStoretype")
		@ResponseBody
		public String saveWarehouseStoretype(HttpServletRequest request,WarehouseStoretype warehouseStoretype){
			LOGGER.info("start to execute method <addCurrency()修改保存库房类型>!!!!");
			LOGGER.info(":"+warehouseStoretype.getTypeName());
			String result = "";
			try {
				RemoteServiceSingleton.getInstance().getWarehouseService().updateWarehouseStoretypeBySid(warehouseStoretype);
				result = "库房类型修改成功了!";
			} catch (Exception e) {
				// TODO: handle exception
				result = "库房类型修改失败了!";
				LOGGER.error("WarehouseService<updateWarehouseStoretypeBySid(warehouseStoretype)>获取所有库房类型 is failed!!!!"+e.getMessage(),e);
			}
			LOGGER.info("end<> to execute method <saveWarehouseStoretype()修改保存库房类型>!!!!");
			return result;
			
		}
		
		
		
		/**.
		 * @Title：getAllWarehousesStorelevel
		 * @Description:获取发货渠道显示.
		 * @return 
		 */
		@RequestMapping(value="/getAllChannels")
		public String getAllChannels(Model model){
			
			List<WarehouseChannel> warehouseChannels = new ArrayList<WarehouseChannel>();
			try {
				warehouseChannels = RemoteServiceSingleton.getInstance().getWarehouseService().findChannelByAll();
				
			} catch (Exception e) {
			
				LOGGER.error(e.getMessage(),e);
			
			}
			
			model.addAttribute("warehouseChannel",warehouseChannels);
			return "dealerseller/warehouse/channel/warehouseChannel";	
		
		}
		
		
		/**.
		 * @Title：goAddWareHouse
		 * @Description:加载到添加发货渠道页面.
		 * @return addwarehouse.jsp页面
		 */
		@RequestMapping(value="/goAddwarehouseChannel")
		public String goAddwarehouseChannel(){
 			LOGGER.info("start to execute method <goAddwarehouseChannel()跳转页面>!!!!");
			return "dealerseller/warehouse/channel/addwarehouseChannel";
		}
		
		

		/**.
		 * 保存发货渠道
		 * @param request HttpServletRequest
		 * @param 
		 * @return ModelAndView
		 */
		@RequestMapping(value = "/addWarehouseChannel")
		@ResponseBody
		public String addWarehouseChannel(HttpServletRequest request,WarehouseChannel warehouseChannel){
			LOGGER.info("start to execute method <addCurrency()添加发货渠道>!!!!");
			LOGGER.info(":"+warehouseChannel.getChannelName());
			String result = "";
			try {
				RemoteServiceSingleton.getInstance().getWarehouseService().insertChannel(warehouseChannel);
				result = "发货渠道保存成功了!";
			} catch (Exception e) {
				// TODO: handle exception
				result = "发货渠道保存失败了!";
				LOGGER.error("WarehouseService<insertChannel(warehouseChannel)>获取所有发货渠道is failed!!!!"+e.getMessage(),e);
			}
			LOGGER.info("end<> to execute method <addWarehouseChannel()添加发货渠道>!!!!");
			return result;
			
		}
		
		
		/**.
		 * 获取发货渠道编辑数据获取相应的值
		 * @param request HttpServletRequest
		 * @param 
		 * @return ModelAndView
		 */
		@RequestMapping(value = "/editWarehouseChannel")

		public String editWarehouseChannel(HttpServletRequest request,HttpServletResponse response,
				Long sid){	
			WarehouseChannel warehouseChannel = new WarehouseChannel();
			try {
				warehouseChannel = RemoteServiceSingleton.getInstance().getWarehouseService().findWarehouseChannelBySid(sid);				
			} catch (Exception e) {
				
				LOGGER.error("WarehouseService<findWarehouseChannelBySid(Sid)>获取发货渠道 is failed!!!!"+e.getMessage(),e);
			}
			LOGGER.info("end<> to execute method <editWarehouseChannel()跳转修改发货渠道页面>!!!!");
			request.setAttribute("warehouseChannel", warehouseChannel);
			 return "dealerseller/warehouse/channel/editwarehouseChannel";
			
		}
		
		
		/**.
		 * 修改保存发货渠道
		 * @param request HttpServletRequest
		 * @param 
		 * @return ModelAndView
		 */
		@RequestMapping(value = "/saveWarehouseChannel")
		@ResponseBody
		public String saveWarehouseChannel(HttpServletRequest request,WarehouseChannel warehouseChannel){
			LOGGER.info("start to execute method <addCurrency()修改保存发货渠道>!!!!");
			LOGGER.info(":"+warehouseChannel.getChannelName());
			String result = "";
			try {
				RemoteServiceSingleton.getInstance().getWarehouseService().updateWarehouseChannelBySid(warehouseChannel);
				result = "发货渠道修改成功了!";
			} catch (Exception e) {
				// TODO: handle exception
				result = "发货渠道修改失败了!";
				LOGGER.error("WarehouseService<updateWarehouseChannelBySid(warehouseChannel)>获取所有发货渠道 is failed!!!!"+e.getMessage(),e);
			}
			LOGGER.info("end<> to execute method <saveWarehouseChannel()修改保存发货渠道>!!!!");
			return result;
			
		}
		
		
}	
