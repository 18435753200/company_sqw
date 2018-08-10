package com.mall.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.mybatis.utility.PageBean;
//import com.mall.retailer.model.RetailerEquipmentPay;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;

/**
 * @author zhouzb
 * 设备使用费用审核.
 */

@Controller
@RequestMapping(value="/equipment")
public class EquipmentAuditController extends BaseController{
	
	/**
	 * LOGGER.
	 */
	private static final Log LOGGER = LogFactory.getLogger(EquipmentAuditController.class);

	
	/**
	 * @return 返回Page地址.
	 */
	@RequestMapping(value="/toEquipmentAuditPage")
	public String toEquipmentAuditPage(){
		LOGGER.info("toEquipmentAuditPage");
		return "/user/equipmentAudit";
	}
	/**
	 * @param request HttpServletRequest.
	 * @param response HttpServletResponse.
	 * @param page  分页页码.
	 * @param retailerEquipmentPay 参数对象 .
	 * @return 返回Page页面.
	 */
/*	@RequestMapping(value="/getEquipmentAuditList")
	public String getEquipmentAuditList(HttpServletRequest request,HttpServletResponse response
			,Integer page,RetailerEquipmentPay retailerEquipmentPay
			){
		//parameter
		PageBean<RetailerEquipmentPay> pageBean = new PageBean<RetailerEquipmentPay>();
		//Result
		PageBean<RetailerEquipmentPay> payPageList = new PageBean<RetailerEquipmentPay>();
		try {
			if(page!=null&&page!=0){
				pageBean.setPage(page);
			}else{
				pageBean.setPage(Constants.NUM1);
			}
			pageBean.setPageSize(Constants.PAGESIZE);
			
			pageBean.setParameter(retailerEquipmentPay);
			
			payPageList = RemoteServiceSingleton.getInstance().getRetailerManagerService().getPayPageList(pageBean);
			request.getSession().setAttribute("pb",payPageList);
			request.getRequestDispatcher("/WEB-INF/views/user/modelPage/equipmentAuditmodel.jsp")
				.forward(request, response);
			LOGGER.info("查询设备审核信息列表");
		} catch (Exception e) {
			LOGGER.error("查询设备审核信息列表失败 ");
			
			LOGGER.error("商户Id:"+getCurrentPlatformId());
			
			LOGGER.error("用户:"+getCurrentUser().getUsername());
			LOGGER.error("用户ID:"+getCurrentUser().getId());
			LOGGER.error(e.getMessage()+e);
		}
		return null;
	}
	
	*//**
	 * @param retailerEquipmentPay RetailerEquipmentPay参数对象
	 * @return 返回修改状态.
	 * @throws UnsupportedEncodingException 
	 *//*
	@RequestMapping(value="/changeStatus")
	@ResponseBody
	public String changeStatus(RetailerEquipmentPay retailerEquipmentPay){
		
		String resault = "1";
		try {
			resault = RemoteServiceSingleton.getInstance().getRetailerManagerService()
						.updateEquipmentPay(retailerEquipmentPay)+"";
		} catch (Exception e) {
			resault="0";
			LOGGER.error("更新设备状态出错 ");
			
			LOGGER.error("商户Id:"+getCurrentPlatformId());
			
			LOGGER.error("用户:"+getCurrentUser().getUsername());
			LOGGER.error("用户ID:"+getCurrentUser().getId());
			LOGGER.error(e.getMessage()+e);
		}
		return resault;
	}*/

	
}
