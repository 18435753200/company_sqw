package com.mall.controller.order;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebParam.Mode;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.category.api.rpc.BaseDataServiceRpc;
import com.mall.category.po.AgentCity;
import com.mall.category.po.AgentCounty;
import com.mall.category.po.AgentProvince;
import com.mall.platform.proxy.RemoteServiceSingleton;
/**.
 * @Title：BaseDataController
 * @Description:基础数据显示<省市区>
 * @author Guo
 * @Date 2015年2月3日16:32:47
 */
@Controller
@RequestMapping(value="/baseData")
public class BaseDataController {
	
	/**.
	 * LOGGER(日志打印)
	 */
	private static final Log LOGGER = LogFactory.getLogger(BaseDataController.class);
	
	
	/**.
	 * 接受json格式的
	 */
    private	Object json = "[]";
	
    
	/**.
	 * @Title：findAllProvince
	 * @Description:获取基础数据中所有的省份的信息
	 * @param Model model 
	 * @param HttpServletResponse response 
	 */
	@RequestMapping(value = "/findAllProvince")
	public final void findAllProvince(Model model,HttpServletResponse response){
		LOGGER.info("start to execute method <findAllProvince()加载所有省份>!!!!");
		try {
			model.addAttribute("provinceList",RemoteServiceSingleton.
					getInstance().getBaseDataServiceRpc().getAllProvices());
			json = JSONArray.toJSON(RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().getAllProvices());
			response.getWriter().write(json.toString());
		} catch (Exception e) {
			LOGGER.error("BaseDataServiceRpc<getAllProvices()>获取所有省份 service is error!!!!"+e.getMessage(),e);
		}
		LOGGER.info("end<> to execute method <findAllProvince()加载所有省份>!!!!");
	}

	
	
	/**.
	 * 获取一个省的Id查询这个省份下的所有城市的信息
	 * @param provinceId int
	 * @param model Mode
	 * @param response HttpServletResponse
	 */
	@RequestMapping(value = "/findCitiesByProvinceId")
	public final void findCitiesByProvinceId(int provinceId, Mode model,HttpServletResponse response){
		LOGGER.info("start to execute method <findCitiesByProvinceId()根据省份Id查询所有城市>!!!!");
		try {
			LOGGER.info("params:provinceId="+provinceId);
			if(provinceId!=0){
				BaseDataServiceRpc baseDataServiceRpc = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc();
				List<AgentCity> cityList = baseDataServiceRpc.getCitiesByProvinceId(provinceId);
				json = JSONArray.toJSON(cityList);
				response.getWriter().write(json.toString());
			}	
		} catch (Exception e) {
			LOGGER.error("BaseDataServiceRpc<getCitiesByProvinceId()>根据省份Id查询所有城市 service is error。。。。。"+e.getMessage(),e);
		}	
		LOGGER.info("end<> to execute method <findCitiesByProvinceId()根据省份Id查询所有城市>!!!!");
	}
	
	

	/**.
	 * 获取一个城市下的所有的县级信息
	 * @param cityId int
	 * @param model Model
	 * @param response HttpServletResponse
	 */ 
	@RequestMapping(value="/findCountiesByCityId")
	public final void findCountiesByCityId(int cityId,Model model,HttpServletResponse response){
		LOGGER.info("start to execute method <findCountiesByCityId()根据城市Id获取所有区>!!!!");
		if(cityId!=0){
			try {
				BaseDataServiceRpc baseDataServiceRpc = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc();
				LOGGER.info("params:cityId="+cityId);
				List<AgentCounty> countyList = baseDataServiceRpc.getCountiesByCityId(cityId);
				json = JSONArray.toJSON(countyList);
				response.getWriter().write(json.toString());
			} catch (Exception e) {
				LOGGER.error("BaseDataServiceRpc<getCountiesByCityId()>根据城市Id获取所有区  service is error。。。。。"+e.getMessage(),e);
			}
		}
		LOGGER.info("end<> to execute method <findCountiesByCityId()根据城市Id获取所有区>!!!!");
	}	
	
	
	/**.
	 * 获取一个城市下的所有的市级信息
	 * @param cityId int
	 * @param model Model
	 * @param response HttpServletResponse
	 */ 
	@RequestMapping(value="/findCityByIds")
	public final  Map<Long,AgentCity> findCityByIds(Long[] cityIds){
		LOGGER.info("start to execute method <findCityByIds()根据城市Id集合获取所有区>!!!!");
		 Map<Long,AgentCity> citys = new HashMap<Long, AgentCity>();
		try {
			BaseDataServiceRpc baseDataServiceRpc = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc();
			citys = baseDataServiceRpc.getCityMap(cityIds);
		} catch (Exception e) {
			LOGGER.error("BaseDataServiceRpc<getCitys()>根据城市Id集合获取所有区  service is error。。。。。"+e.getMessage(),e);
		}
		LOGGER.info("end<> to execute method <findCityByIds()根据城市Id集合获取所有区>!!!!");
		return citys;
	}
	
	
	/**.
	 * 获取一个城市下的所有的省级信息
	 * @param cityId int
	 * @param model Model
	 * @param response HttpServletResponse
	 */ 
	@RequestMapping(value="/findProvinceByIds")
	public final Map<Long,AgentProvince> findProvinceByIds(Long[] provinceIds){
		LOGGER.info("start to execute method <findProvinceByIds()根据城市Id集合获取所有省>!!!!");
		Map<Long,AgentProvince> provinces = new HashMap<Long, AgentProvince>();
		try {
			BaseDataServiceRpc baseDataServiceRpc = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc();
			provinces = baseDataServiceRpc.getProviceMap(provinceIds);
		} catch (Exception e) {
			LOGGER.error("BaseDataServiceRpc<getProvices()>根据城市Id集合获取所有区  service is error。。。。。"+e.getMessage(),e);
		}
		LOGGER.info("end<> to execute method <findProvinceByIds()根据城市Id集合获取所有省>!!!!");
		return provinces;
	}
	
	
	/**.
	 * 获取一个城市下的所有的县级信息
	 * @param cityId int
	 * @param model Model
	 * @param response HttpServletResponse
	 */ 
	@RequestMapping(value="/findCountyByIds")
	public final Map<Long,AgentCounty> findCountyByIds(Long[] countyIds){
		LOGGER.info("start to execute method <findCountyByIds()根据城市Id集合获取所有省>!!!!");
		Map<Long,AgentCounty> countys = new HashMap<Long, AgentCounty>();
		try {
			BaseDataServiceRpc baseDataServiceRpc = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc();
			countys = baseDataServiceRpc.getCountyMap(countyIds);
		} catch (Exception e) {
			LOGGER.error("BaseDataServiceRpc<getCounty()>根据城市Id集合获取所有区  service is error。。。。。"+e.getMessage(),e);
		}
		LOGGER.info("end<> to execute method <findCountyByIds()根据城市Id集合获取所有省>!!!!");
		return countys;
	}
	
	
}
