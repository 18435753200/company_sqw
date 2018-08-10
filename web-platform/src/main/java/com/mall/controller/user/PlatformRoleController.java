package com.mall.controller.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.platform.model.PlatformMenu;
import com.mall.platform.model.PlatformRole;
import com.mall.platform.model.PlatformRolePopedom;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;

/**
 * 
 * @author zhouzb
 *
 */
@Controller
@RequestMapping(value="/role")
public class PlatformRoleController extends BaseController  {
	
	/**
	 * LOGGER.
	 */
	private static final Log LOGGER = LogFactory.getLogger(PlatformController.class);
	
	/**全查询所有角色.
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @param role PlatformRole
	 * @return
	 */
	@RequestMapping(value = "/list")
	public ModelAndView findAllRoles(HttpServletRequest request,HttpServletResponse response, PlatformRole role) {
		LOGGER.info("全查询所有角色.");
		 Map<String,Object> map = new HashMap<String,Object>();
		//根据当前登录用户 取其(供应商) 角色集合
		 List<PlatformRole> list = null;
		 try {
   			list = RemoteServiceSingleton.getInstance().getPlatformRoleManagerService().findAllRole();
		 } catch (Exception e) {
			e.printStackTrace();
		 }
		 //查询全部权限组成树形结构给前台
		 List<PlatformMenu> listMenu = null;
		 try {
			 listMenu = RemoteServiceSingleton.getInstance().getPlatformRoleManagerService().findAllMenus();
		 } catch (Exception e) {
			e.printStackTrace();
		 }
		 List<Long> userMenuStrList=new ArrayList<Long>();
		 if(null!=role.getRoleId()){
  			 List<PlatformMenu> userMenu= RemoteServiceSingleton.getInstance().getPlatformRoleManagerService().findMenusByRoleId(role.getRoleId());
  			 if(null!=userMenu&&userMenu.size()>0){
  				 for (PlatformMenu platformMenu : userMenu) {
  					userMenuStrList.add(platformMenu.getMenuId());
  				} 
  			 }
  			 map.put("roleId", role.getRoleId());
  		   }
         if(listMenu!=null){
    	    List<Map<String,Object>> datalist =new ArrayList<Map<String,Object>>();
    	
    	    for (int i = 0; i < listMenu.size(); i++) {
				Map<String,Object> map2=new LinkedHashMap<String, Object>();
				if(null!=listMenu.get(i).getMenuId()){
					map2.put("id", listMenu.get(i).getMenuId());
					map2.put("name", null==listMenu.get(i).getName()?"":listMenu.get(i).getName());
					map2.put("pId", null==listMenu.get(i).getParentMenuId()?0:listMenu.get(i).getParentMenuId());
					map2.put("squeuces", i+1);
					if(userMenuStrList.contains(listMenu.get(i).getMenuId())){
						map2.put("checked", true);
					}
					datalist.add(map2);
				}
    	    }    
    		map.put("tree",JSON.toJSONString(datalist));
         }
		 map.put("roleList", list);
	     return new ModelAndView("user/role",map);
	}
	
	
	/**.
	 * 验证添加或者修改的角色名称是否存在.
	 * @param  roleName  String.
	 * @return
	 */
	@RequestMapping(value="/checkRoleOnly")
	@ResponseBody
	public String checkRoleOnly(String roleName){
		int count = 1;
		try{
			LOGGER.info("验证角色名称存在异常 角色名称:"+roleName);
			count = RemoteServiceSingleton.getInstance().getPlatformRoleManagerService().countRoleByName(roleName);
			
		}catch (Exception e) {
			LOGGER.error("验证角色名称存在异常");
			
			LOGGER.error("商户Id:"+getCurrentPlatformId());
			
			LOGGER.error("用户:"+getCurrentUser().getUsername());
			LOGGER.error("用户ID:"+getCurrentUser().getId());
			LOGGER.error(e.getMessage()+e);
		}
		return count+"";
	}
	
	/**.
	 * 创建一个新角色
	 * @param dealeruser
	 * @param platformRole PlatformRole
	 * @return
	 */
	@RequestMapping(value="/save")	
	public ModelAndView insertPlatformRole(PlatformRole platformRole) {
		
		LOGGER.info("创建一个新角色");
		
		LOGGER.info("商户Id:"+getCurrentPlatformId());
		
		LOGGER.info("用户:"+getCurrentUser().getUsername());
		LOGGER.info("用户ID:"+getCurrentUser().getId());
		
		
		LOGGER.info("角色名:"+platformRole.getName());
		platformRole.setStatus(1);
		RemoteServiceSingleton.getInstance().getPlatformRoleManagerService().addRole(platformRole);
		return 	new ModelAndView("redirect:list");	
	}
 
	 /**.
	 * 修改角色名称
	 * @param request  HttpServletRequest
	 * @param platformRole PlatformRole
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/update")
	public ModelAndView updateRole(HttpServletRequest request,PlatformRole platformRole) {
		LOGGER.info("修改角色名称");
		
		LOGGER.info("商户Id:"+getCurrentPlatformId());
		
		LOGGER.info("用户:"+getCurrentUser().getUsername());
		LOGGER.info("用户ID:"+getCurrentUser().getId());
		
		
		LOGGER.info("角色名Id:"+platformRole.getRoleId());
		
		RemoteServiceSingleton.getInstance().getPlatformRoleManagerService().updateRole(platformRole);
	
		return new ModelAndView("redirect:list?roleId="+platformRole.getRoleId());
	  }
	/**
	 * 修改角色权限 .
	 * @param request HttpServletRequest
	 * @param roleId Long
	 * @param menus Long[]
	 * @return
	 */
	@RequestMapping(value = "/updateRoleMenu")
	public ModelAndView updateRoleMenu(HttpServletRequest request,Long roleId,Long[] menus) {
		List<PlatformRolePopedom> list=new ArrayList<PlatformRolePopedom>();
		PlatformRolePopedom rp=null;
		if(null!=menus&&menus.length>0){
			for (int i = 0; i < menus.length; i++) {
				if(menus[i]!=0){
					rp= new PlatformRolePopedom();
					rp.setMenuId(menus[i]);
					rp.setRoleId(roleId);
					list.add(rp);
				}
			}
		}else{
			rp= new PlatformRolePopedom();
			rp.setRoleId(roleId);
			list.add(rp);
		}
		LOGGER.info("修改角色权限");
		
		LOGGER.info("商户Id:"+getCurrentPlatformId());
		
		LOGGER.info("用户:"+getCurrentUser().getUsername());
		LOGGER.info("用户ID:"+getCurrentUser().getId());
		
		RemoteServiceSingleton.getInstance().getPlatformRoleManagerService().updateRolePopedomByRoleId(list);
		return new ModelAndView("redirect:list?roleId="+roleId);
	  }
	/**单个查询角色名称.
	 * @param response HttpServletResponse
	 * @param roleId Long
	 */
	@RequestMapping(value = "/findRoleById")
	public void findRoleById(HttpServletResponse response,Long roleId) {
		PlatformRole platformRole=RemoteServiceSingleton.getInstance().getPlatformRoleManagerService().getRoleById(roleId);
		try {
			response.getWriter().write(platformRole.getName());
		} catch (IOException e) {
			e.printStackTrace();
		}
   }
	/**删除某个角色.
	 * @param request HttpServletRequest
	 * @param roleId  Long
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/delete")
	public ModelAndView deleteByRoleId(HttpServletRequest request,Long roleId) {
		
		LOGGER.info("删除某个角色.");
		
		LOGGER.info("商户Id:"+getCurrentPlatformId());
		
		LOGGER.info("用户:"+getCurrentUser().getUsername());
		LOGGER.info("用户ID:"+getCurrentUser().getId());
		
		
		LOGGER.info("权限ID:"+roleId);
		
	    RemoteServiceSingleton.getInstance().getPlatformRoleManagerService().deleteRoleById(roleId);
	    
		return new ModelAndView("redirect:list");
		
	  }
	/**
	 * 权限名验证.
	 * 
	 * @param pin
	 *            权限名称
	 * @param response
	 *            HttpServletResponse
	 * @return int count
	 */
	@RequestMapping("/isRoleEngaged")
	@ResponseBody
	public String isRoleEngaged(Long roleId, HttpServletResponse response) {
		int count = 0;
		try{
			if (roleId!=null) {
				count = RemoteServiceSingleton.getInstance().getPlatformRoleManagerService().countUserByRoleId(roleId);
			}
		}catch (Exception e) {
			LOGGER.error(e.getMessage()+e);
		}
		return count+"";
	}
	
	
}
