package com.mall.controller.user;

import java.io.IOException;
import java.rmi.Remote;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.mall.annotation.Token;
import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.category.api.rpc.BaseDataServiceRpc;
import com.mall.category.po.AgentCity;
import com.mall.category.po.AgentCounty;
import com.mall.category.po.AgentProvince;
//import com.mall.category.po.LimitMould;
import com.mall.controller.base.BaseController;
import com.mall.customer.order.utils.JsonUtils;
import com.mall.merchant.proxy.RemoteServiceSingleton;
import com.mall.supplier.model.SuplierAreaRegion;
import com.mall.supplier.model.SuplierAreaTpl;
import com.mall.supplier.model.SupplierMenu;
import com.mall.supplier.model.SupplierRole;
import com.mall.supplier.model.SupplierRolePopedom;
import com.mall.utils.Common;

@Controller
@RequestMapping("/limit")
public class LimitAreaController extends BaseController {
	private static final Log logger = LogFactory.getLogger(LimitAreaController.class);
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/list")
	@Token(saveToken=true)
	public ModelAndView findAllArea(HttpServletRequest request,HttpServletResponse response, SuplierAreaTpl tpl){
		Map<String,Object> map = new HashMap<String,Object>();
//		ArrayList list = new ArrayList(); 
		List<AgentProvince> allProvices=null;
		List<AgentCity>  allCity=null;
		List<AgentCounty>  allCounty=null;
		List<SuplierAreaTpl> list=null;
		 map.put("editable", false);
		 try {
			 list = RemoteServiceSingleton.getInstance().getSupplierRoleManagerService().selectMouldsBySupplierIds(getSupplierIds());
			
		} catch (Exception e) {
			e.getMessage();
		}
		 
		try {
			allProvices = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().getAllProvices();
			allCity = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().getAllCity();
			allCounty = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().getAllCounty();
			/*list.add(allProvices);
			list.add(allCity);
			list.add(allCounty);*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		 List<Integer> limitCountyStrList=new ArrayList<Integer>();
		 List<Integer> limitCityStrList=new ArrayList<Integer>();
		 List<Integer> limitProvinceStrList=new ArrayList<Integer>();
		 if(null!=tpl.getId()){
  			 List<SuplierAreaRegion> limitCounty= RemoteServiceSingleton.getInstance().getSupplierRoleManagerService().
  					 findAgentCountyByMould(tpl.getId());
  					
  			 if(null!=limitCounty&&limitCounty.size()>0){
  				 for (SuplierAreaRegion area : limitCounty) {
  					limitCountyStrList.add(area.getCountyId());
  					limitCityStrList.add(area.getCityId());
  					limitProvinceStrList.add(area.getProvinceId());
  				} 
  			 }
  			 map.put("roleId", tpl.getId());
  			 map.put("typ", tpl.getType());
  		   }
		if(allProvices!=null && !allProvices.isEmpty()){
			List<Map<String,Object>> datalist =new ArrayList<Map<String,Object>>();
			/*Map<String, Object> map0 = new LinkedHashMap<String, Object>();
			map0.put("id", 133);
			map0.put("name", "全国");
			map0.put("pId", "root");
			map0.put("squeuces", 0);
			datalist.add(map0);*/
			Map<String, Object> map2 = null;
			Map<String, Object> map3 = null;
			Map<String, Object> map4 = null;
			for(int i=0;i<allProvices.size();i++){
				map2=new LinkedHashMap<String, Object>();
				map2.put("id", allProvices.get(i).getProvinceid());
				map2.put("name", null==allProvices.get(i).getProvincename()?"":allProvices.get(i).getProvincename());
				map2.put("pId", "root");
				map2.put("squeuces", i);
				/*if(limitCountyStrList.size()>0){
					map2.put("checked", true);
				}*/
				if(limitProvinceStrList.contains(allProvices.get(i).getProvinceid())){
					map2.put("checked", true);
				}
				datalist.add(map2);
			}	
			for(int i=0;i<allCity.size();i++){
				map3=new LinkedHashMap<String, Object>();
				map3.put("id", allCity.get(i).getCityid()+1000);
				map3.put("name", null==allCity.get(i).getCityname()?"":allCity.get(i).getCityname());
				map3.put("pId", allCity.get(i).getProvinceid());
				map3.put("squeuces", i+allProvices.size());	
				if(limitCityStrList.contains(allCity.get(i).getCityid())){
					map3.put("checked", true);
				}
				datalist.add(map3);
			}
			
			for(int i=0;i<allCounty.size();i++){
				map4=new LinkedHashMap<String, Object>();
				map4.put("id", allCounty.get(i).getCountyid()+10000);
				map4.put("name", null==allCounty.get(i).getCountyname()?"":allCounty.get(i).getCountyname());
				map4.put("pId", allCounty.get(i).getCityid()+1000);
				map4.put("squeuces", i+allProvices.size()+allCity.size());
				if(limitCountyStrList.contains(allCounty.get(i).getCountyid())){
					map4.put("checked", true);
				}
				datalist.add(map4);
			}
			map.put("tree",JSON.toJSONString(datalist));
		/*	System.out.println(JSON.toJSONString(datalist));*/
			map.put("roleList", list);
		}
		
		 return new ModelAndView(getLanguage()+"/user/limit",map);
		
	}
	
	
	
	/*@SuppressWarnings("unchecked")
	@RequestMapping("/area")
	@Token(saveToken=true)
	public ModelAndView findAllAreaa(HttpServletRequest request,HttpServletResponse response, SuplierAreaTpl tpl){
		Map<String,Object> map = new HashMap<String,Object>();
//		ArrayList list = new ArrayList(); 
		List<AgentProvince> allProvices=null;
		List<AgentCity>  allCity=null;
		List<AgentCounty>  allCounty=null;
		List<SuplierAreaTpl> list=null;
		 map.put("editable", false);
		 try {
			 list = RemoteServiceSingleton.getInstance().getSupplierRoleManagerService().selectMouldsBySupplierIds(getSupplierIds());
			
		} catch (Exception e) {
			e.getMessage();
		}
		 
		try {
			allProvices = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().getAllProvices();
			allCity = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().getAllCity();
			allCounty = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().getAllCounty();
			list.add(allProvices);
			list.add(allCity);
			list.add(allCounty);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		 List<Integer> limitCountyStrList=new ArrayList<Integer>();
		 List<Integer> limitCityStrList=new ArrayList<Integer>();
		 List<Integer> limitProvinceStrList=new ArrayList<Integer>();
		 if(null!=tpl.getId()){
  			 List<SuplierAreaRegion> limitCounty= RemoteServiceSingleton.getInstance().getSupplierRoleManagerService().
  					 findAgentCountyByMould(tpl.getId());
  					
  			 if(null!=limitCounty&&limitCounty.size()>0){
  				 for (SuplierAreaRegion area : limitCounty) {
  					limitCountyStrList.add(area.getCountyId());
  					limitCityStrList.add(area.getCityId());
  					limitProvinceStrList.add(area.getProvinceId());
  				} 
  			 }
  			 map.put("roleId", tpl.getId());
  		   }
		if(allProvices!=null && !allProvices.isEmpty()){
			List<Map<String,Object>> datalist =new ArrayList<Map<String,Object>>();
			Map<String, Object> map0 = new LinkedHashMap<String, Object>();
			map0.put("id", 133);
			map0.put("name", "全国");
			map0.put("pId", "root");
			map0.put("squeuces", 0);
			datalist.add(map0);
			Map<String, Object> map2 = null;
			Map<String, Object> map3 = null;
			Map<String, Object> map4 = null;
			for(int i=0;i<allProvices.size();i++){
				map2=new LinkedHashMap<String, Object>();
				map2.put("id", allProvices.get(i).getProvinceid());
				map2.put("name", null==allProvices.get(i).getProvincename()?"":allProvices.get(i).getProvincename());
				map2.put("pId", "root");
				map2.put("squeuces", i);
				if(limitCountyStrList.size()>0){
					map2.put("checked", true);
				}
				if(limitProvinceStrList.contains(allProvices.get(i).getProvinceid())){
					map2.put("checked", true);
				}
				datalist.add(map2);
			}	
			for(int i=0;i<allCity.size();i++){
				map3=new LinkedHashMap<String, Object>();
				map3.put("id", allCity.get(i).getCityid()+1000);
				map3.put("name", null==allCity.get(i).getCityname()?"":allCity.get(i).getCityname());
				map3.put("pId", allCity.get(i).getProvinceid());
				map3.put("squeuces", i+allProvices.size());	
				if(limitCityStrList.contains(allCity.get(i).getCityid())){
					map3.put("checked", true);
				}
				datalist.add(map3);
			}
			
			for(int i=0;i<allCounty.size();i++){
				map4=new LinkedHashMap<String, Object>();
				map4.put("id", allCounty.get(i).getCountyid()+10000);
				map4.put("name", null==allCounty.get(i).getCountyname()?"":allCounty.get(i).getCountyname());
				map4.put("pId", allCounty.get(i).getCityid()+1000);
				map4.put("squeuces", i+allProvices.size()+allCity.size());
				if(limitCountyStrList.contains(allCounty.get(i).getCountyid())){
					map4.put("checked", true);
				}
				datalist.add(map4);
			}
			map.put("tree",JSON.toJSONString(datalist));
			System.out.println(JSON.toJSONString(datalist));
			map.put("roleList", list);
		}
		
		 return new ModelAndView(getLanguage()+"/logistic/logisticPageTpl",map);
		
	}*/
	
	
	@RequestMapping("/save")
	@ResponseBody
	public String insertLimitModoul(SuplierAreaTpl tpl){
		tpl.setSupplierId(getCurrentSupplierId());
		tpl.setStatus(1);
		int i = RemoteServiceSingleton.getInstance().getSupplierRoleManagerService().addLimitMould(tpl);
		if(i>0){
			logger.info("用户:"+getCurrentUser().getLoginName()+"添加"+tpl.getName()+"成功");
			return JSON.toJSONString(1);
		}
		else{
			logger.info("用户:"+getCurrentUser().getLoginName()+"添加"+tpl.getName()+"失败");
			return JSON.toJSONString(0);
		}
	}
	
	@RequestMapping("/isPinEngaged")
	@ResponseBody
	public String checkName(String pin,HttpServletResponse response){
		int count=1;
		if(!Common.isEmpty(pin)){
			count = RemoteServiceSingleton.getInstance().getSupplierRoleManagerService()
					.countLimitByNameAndMerchId(pin, getCurrentSupplierId());
		}
		return count+"";
		}
	
	/**
	 * 增加更改模板选中的地区
	 */
	@RequestMapping("/updateMouldRegion")
	@ResponseBody
	public String updateMouldRegion(HttpServletRequest request,Long id,Long[] menus){
		List<SuplierAreaRegion> list=new ArrayList<SuplierAreaRegion>();
		List<SuplierAreaRegion> list2=new ArrayList<SuplierAreaRegion>();
		SuplierAreaRegion mr=null;
		BaseDataServiceRpc baseDataServiceRpc = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc();
		if(menus!=null && menus.length>0){
			for (int i = 0; i < menus.length; i++) {
				/*if(menus[i]!=0 && menus[i]<50){					
					mr.setProvinceId(menus[i].intValue());
					mr.setType(1);
					mr.setMouldId(mouldId);
					list.add(mr);
				}if(menus[i]>1000 && menus[i]<1500){
					mr.setCityId(menus[i].intValue()-1000);
					mr.setType(2);
					mr.setMouldId(mouldId);
					list.add(mr);
				}*/if(menus[i]>10000 && menus[i]<14000){
					int cid=0;
					int pid=0;
					try {
						cid = baseDataServiceRpc.getCountyById(menus[i].intValue()-10000).getCityid();
						pid = baseDataServiceRpc.getCountyById(menus[i].intValue()-10000).getProvinceid();
					} catch (Exception e) {
						e.printStackTrace();
					}
					mr=new SuplierAreaRegion();
					mr.setCountyId(menus[i].intValue()-10000);
					mr.setCityId(cid);
					mr.setProvinceId(pid);
					mr.setTplId(id);
					list.add(mr);
				}
							
			}
		}else{
			
				mr= new SuplierAreaRegion();
				mr.setTplId(id);
				list.add(mr);
			
		}
		int i = RemoteServiceSingleton.getInstance().getSupplierRoleManagerService().updateMouldRegionByMouldId(list);
		
		if(i>0){
			logger.info("用户:"+getCurrentUser().getLoginName()+"更新模板id:"+id+"限购地区成功");
			return JSON.toJSONString(1);
		}else{
			logger.info("用户:"+getCurrentUser().getLoginName()+"更新模板id:"+id+"限购地区失败");
			return JSON.toJSONString(0);
		}
	}
	
	@RequestMapping("/checkMouldDel")
	@ResponseBody
	public String checkDelMould(Long roleId){
		int count=0;
		if(roleId!=null){
			count=RemoteServiceSingleton.getInstance().getSupplierRoleManagerService().countDelMould(roleId);
		}
		return count+"";
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public String deleteMould(Long mouldId){
		int i=RemoteServiceSingleton.getInstance().getSupplierRoleManagerService().deleteMould(mouldId);
		if(i>0){
			logger.info("用户:"+getCurrentUser().getLoginName()+"删除模板"+mouldId+"成功");
			return JSON.toJSONString(1);
		}else{
			logger.info("用户:"+getCurrentUser().getLoginName()+"删除模板"+mouldId+"失败");
			return JSON.toJSONString(0);
		}
	}
	
	
	@RequestMapping(value ="getMould")
	@ResponseBody
	public String getTpl(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		

		List<SuplierAreaTpl> tpl = null;
		try {
			tpl = RemoteServiceSingleton.getInstance().getSupplierRoleManagerService().selectMouldsBySupplierIds(getSupplierIds());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String a = JsonUtils.ObjectAsString(tpl);
		response.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
		try {
			response.getWriter().write(a);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value ="getProvince")
	@ResponseBody
	public String getAllProvince(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("getAllProvince execute....");

		List<AgentProvince> provices = null;
		try {
			provices = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().getAllProvices();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String a = JsonUtils.ObjectAsString(provices);
		response.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
		try {
			response.getWriter().write(a);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("getCity")
	@ResponseBody
	public String getAllCity(Integer proId, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("getAllCity execute....");
		if(proId!=null){
		List<AgentCity> cityy=null;
		try {
			cityy = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().getCitiesByProvinceId(proId);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String ci = JsonUtils.ObjectAsString(cityy);
		response.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
		try {
			response.getWriter().write(ci);
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		return null;

	}
	@RequestMapping("getCounty")
	@ResponseBody
	public String getCounty(Integer cityId, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("getAllCity execute....");
		if(cityId!=null){
		List<AgentCounty> cityy=null;
		try {
			cityy = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().getCountiesByCityId(cityId);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String ci = JsonUtils.ObjectAsString(cityy);
		response.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
		try {
			response.getWriter().write(ci);
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		return null;

	}
	
	
	@RequestMapping("update")
	@ResponseBody
	public String updateTpl(SuplierAreaTpl tpl){
		int i = RemoteServiceSingleton.getInstance().getSupplierRoleManagerService().updateTplName(tpl);
		if(i>0){
			return JSON.toJSONString("1");
		}else{
			return JSON.toJSONString("0");
		}
		
		
	}
}
