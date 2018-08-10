package com.mall.controller.user;

import java.util.ArrayList;
import java.util.Date;
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

import com.alibaba.fastjson.JSON;
import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.authority.client.enums.SystemCodeEnum;
import com.mall.authority.client.util.UserUtil;
import com.mall.bean.authority.Module;
import com.mall.bean.authority.User;
import com.mall.bean.authority.response.MenuBean;
import com.mall.platform.model.PlatformMenu;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;
import com.mall.utils.ValidateTool;


/**
 * 品牌审核.
 * @author zhouzb
 */
@Controller
@RequestMapping(value="/menu")
public class MenuController extends BaseController{

	/**
	 * LOGGER.
	 */
	private static final Log LOGGER = LogFactory.getLogger(MenuController.class);
	
	/**
	 * @return 返回Page地址.
	 */
	@RequestMapping(value="/toMenuPage")
	public String toMenuPage(Model model){

		LOGGER.info("toMenuPage");

		//查询全部权限组成树形结构给前台
		List<Long> userMenuStrList=new ArrayList<Long>();

		List<PlatformMenu> listMenu = new ArrayList<PlatformMenu>();

		List<Map<String,Object>> datalist =new ArrayList<Map<String,Object>>();

		try {

			listMenu = RemoteServiceSingleton.getInstance().getPlatformRoleManagerService().findAllMenus();

		} catch (Exception e) {

			LOGGER.error(e.getMessage(),e);

		}

		if(listMenu!=null){

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

		}

		model.addAttribute("tree", JSON.toJSONString(datalist));
		
		return "/user/menu";
		
	}
	
	@RequestMapping(value="/loadChildrenByPID")
	public String loadChildrenByPID(Model model,Long pId){
		
		List<PlatformMenu> menusByPId = new ArrayList<PlatformMenu>();
		
		try {
			
			menusByPId = RemoteServiceSingleton.getInstance().getPlatformRoleManagerService().getMenusByPId(pId);
			
		} catch (Exception e) {
			
			LOGGER.error(e.getMessage(),e);
			
		}
		
		model.addAttribute("menus", menusByPId);
		model.addAttribute("pId", pId);
		
		return "/user/modelPage/menuModel";
	}
	
	
	@RequestMapping(value="/getMenuInfo")
	@ResponseBody
	public String getMenuInfo(Model model,Long menuId){
		
		PlatformMenu menu = new PlatformMenu();
		
		try {
			
			menu = RemoteServiceSingleton.getInstance().getPlatformRoleManagerService().getMenuByMenuId(menuId);
			
		} catch (Exception e) {
			
			LOGGER.error(e.getMessage(),e);
			
		}
		
		return JSON.toJSONString(menu);
		
	}
	
	@RequestMapping(value="/upMenuById")
	@ResponseBody
	public String upMenuById(Long menuId,Long parentMenuId,String menuName){
		
		String upstat = Constants.SUCCESS;
		
		try {
			
			List<PlatformMenu> menusByPIds = RemoteServiceSingleton.getInstance().getPlatformRoleManagerService().getMenusByPId(parentMenuId);
			
			for (PlatformMenu menusByPId:menusByPIds){
				if (menusByPId.getName().equals("menuName") && menusByPId.getStatus()==1){
					upstat =Constants.SAMEISNULL;
					break;
				}
			}
			
			if (upstat.equals(Constants.SUCCESS)){
				PlatformMenu platformMenu = new PlatformMenu();
				platformMenu.setMenuId(menuId);
				platformMenu.setStatus(1);
				upstat = RemoteServiceSingleton.getInstance().getPlatformRoleManagerService().updateMenuByMenuId(platformMenu)+"";
			}
			
		} catch (Exception e) {
			LOGGER.info(e.getMessage(),e);
		}
		
		return upstat;
	}
	
	@RequestMapping(value="/delMenuById")
	@ResponseBody
	public String delMenuById(Model model,Long menuId,String target){
		
		String delstat = Constants.SUCCESS;
		
		
		try {
			
			User currentUser = getCurrentUser();

			PlatformMenu platformMenu = new PlatformMenu();
			
			platformMenu.setMenuId(menuId);
			platformMenu.setLastModifyTime(new Date());
			platformMenu.setLastModifyBy(new Integer(currentUser.getSequenceId()+""));
			
			if (target.equals("0")){
				platformMenu.setStatus(-1);
//				delstat = RemoteServiceSingleton.getInstance().getPlatformRoleManagerService().delMenuByMenuId(menuId)+"";
			}
			if (target.equals("1")){
				platformMenu.setStatus(0);
			}
			delstat = RemoteServiceSingleton.getInstance().getPlatformRoleManagerService().updateMenuByMenuId(platformMenu)+"";
			
			
		} catch (Exception e) {
			
			delstat = Constants.ERROR;
			LOGGER.error(e.getMessage(),e);
			
		}
		
		return delstat;
		
	}
	
	
	@RequestMapping(value="/saveORupdateMenuById")
	@ResponseBody
	public String saveORupdateMenuById(Model model,PlatformMenu platformMenu){
		
		String delstat = Constants.SUCCESS;
		
		try {
			//通过parenID取到所有的同级菜单信息 已被循环判断 做插入或更新
			List<PlatformMenu> menusByPId = RemoteServiceSingleton.getInstance().getPlatformRoleManagerService().getMenusByPId(platformMenu.getParentMenuId());
				if (null != platformMenu.getMenuId()){
					for(PlatformMenu menu:menusByPId){
						String name = menu.getName();
						//菜单更新不能 更新为 同一父ID 下相同名字 未启用的菜单排除
						if(name.equals(platformMenu.getName()) && menu.getStatus() == 1 && menu.getMenuId() != platformMenu.getParentMenuId()){
							delstat =Constants.SAMEISNULL;
							break;
						}
					}
					if ( !delstat.equals(Constants.SAMEISNULL) ){
						delstat = RemoteServiceSingleton.getInstance().getPlatformRoleManagerService().updateMenuByMenuId(platformMenu)+"";
					}
	
				}else{

					
					for(PlatformMenu menu:menusByPId){
						String name = menu.getName();
						//菜单更新不能 更新为 同一父ID 下相同名字 并且都为启用的
						if(name.equals(platformMenu.getName()) && menu.getStatus() == 1){
							delstat =Constants.SAMEISNULL;
							break;
						}
					}
					
					if ( !delstat.equals(Constants.SAMEISNULL) ){
						
						platformMenu.setCreateBy(getCurrentUser().getUsername());
						platformMenu.setCreateTime(new Date());
						
						delstat = RemoteServiceSingleton.getInstance().getPlatformRoleManagerService().insertMenu(platformMenu)+"";
						
					}

				}
			
		} catch (Exception e) {
			
			delstat = Constants.ERROR;
			LOGGER.error(e.getMessage(),e);
			
		}
		
		return delstat;
		
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/load-menu")
	public String ModuleTree(Model model, HttpServletRequest request,
			HttpServletResponse response, String type) {
		String strUrl = "";
		MenuBean Allmenu = UserUtil.findMenuList(request, response,
				SystemCodeEnum.WOFE.getName());// 获取所有菜单

		List<Module> _List = Allmenu.getModuleList();
		@SuppressWarnings("rawtypes")
		Map map = new HashMap();
		for (Module module : _List) {
			map.put(module.getName(), module.getUrl());

		}
		model.addAttribute("meunMap", map);
		if (!ValidateTool.isEmpty(type)) {
			if (type.equals("/buyOrder/buyOrder")) {
				strUrl = "/dealerbuy/basePage/maiLeft";
			} else if (type.equals("/POPproduct/getPro")) {
				strUrl = "/dealerseller/leftPage";
			} else if (type.equals("/coupon/getCouponPage")) {
				strUrl = "promotions/leftPage";
			} else if (type.equals("/user/checklist")) {
				strUrl = "/include/leftUser";
			}
//			for(Object str : map.values()){
//				if(type.equals((String)str)||type==str){
//					strUrl = (String)str;
//				}
//			}
		}
		return strUrl;
	}
	
}
