/*package com.mall.controller.salesManager;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.mall.mybatis.utility.PageBean;
import com.mall.platform.model.PlatformChannel;
import com.mall.platform.model.PlatformDto;
import com.mall.retailer.model.Retailer;
import com.mall.retailer.model.RetailerQueryDto;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;

*//**
 * 
 * @author fengh
 *
 *//*

@Controller
public class SalesWayControll {
	
	private static Logger logger = LoggerFactory.getLogger(SalesWayControll.class);
	*//**获取用户列表.
	 * @param map Map<String,Object>
	 * @param paramUser PlatformChannel
	 * @param paramPage PageBean<PlatformChannel>
	 * @param 
	 * @return
	 *//*
	@RequestMapping("/saleWay/toList")
	public String list(){
		return "/user/saleWay";
	}
	*//**获取渠道 模板.
	 * @param map Map<String,Object>
	 * @param paramUser PlatformChannel
	 * @param paramPage PageBean<PlatformChannel>
	 * @param 
	 * @return
	 *//*
	@RequestMapping("/saleWay/getModel")
	public String getList(Map<String, Object> map,Model model,PageBean pageBean){
		List<PlatformChannel> allList = null;
		try {
			allList =RemoteServiceSingleton.getInstance().getChannelManagerService().getAllChannel();
			pageBean.setPageSize(10);
			pageBean = RemoteServiceSingleton.getInstance().getChannelManagerService().getChannelPageList(pageBean);
			allList = pageBean.getResult();
		} catch (Exception e) {
			logger.debug("-----------------"+e);
		}
		map.put("allList", allList);
		map.put("pb", pageBean);
		return "/user/modelPage/saleWayModel";
	}
	
	*//**新增.
	 * @param map Map<String,Object>
	 * @param paramUser PlatformChannel
	 * @param paramPage PageBean<PlatformChannel>
	 * @param 
	 * @return
	 *//*
	@RequestMapping("/saleWay/save")
	@ResponseBody
	public String save(String name,String rate,String destion){
		PlatformChannel platformChannel = new PlatformChannel();
		platformChannel.setChannelName(name);
		platformChannel.setChannelSubtractRate(Double.valueOf(rate));
		platformChannel.setDescription(destion);
		int insertSale = -1;
		try {
			insertSale = RemoteServiceSingleton.getInstance().getChannelManagerService().insertChannel(platformChannel);
		} catch (Exception e) {
			logger.debug("-----------------"+e);
		}
		return "" + insertSale;
	}
	*//**跳转.
	 * @param map Map<String,Object>
	 * @param paramUser PlatformChannel
	 * @param paramPage PageBean<PlatformChannel>
	 * @param 
	 * @return
	 *//*
	@RequestMapping("/saleWay/toEdit")
	@ResponseBody
	public String toEdit(int id,Model model){
		PlatformChannel platformChannel = null;
		try {
			platformChannel = RemoteServiceSingleton.getInstance().getChannelManagerService().getChannelById(id);
		} catch (Exception e) {
			logger.debug("-----------------"+e);
		}
		String jsonString = JSON.toJSONString(platformChannel);
		return jsonString;
	}
	*//**修改.
	 * @param map Map<String,Object>
	 * @param paramUser PlatformChannel
	 * @param paramPage PageBean<PlatformChannel>
	 * @param 
	 * @return
	 *//*
	@RequestMapping("/saleWay/toUpdate")
	@ResponseBody
	public String update(int id,String name,String rate,String destion){
		PlatformChannel platformChannel = new PlatformChannel();
		platformChannel.setChannelId(Integer.valueOf(id));
		platformChannel.setChannelName(name);
		platformChannel.setChannelSubtractRate(Double.valueOf(rate));
		platformChannel.setDescription(destion);
		int updateWay = -1;
		try {
			updateWay = RemoteServiceSingleton.getInstance().getChannelManagerService().updateChannel(platformChannel);
		} catch (Exception e) {
			logger.debug("-----------------"+e);
		}
		return ""+updateWay;
	}
	*//**删除.
	 * @param map Map<String,Object>
	 * @param paramUser PlatformChannel
	 * @param paramPage PageBean<PlatformChannel>
	 * @param 
	 * @return
	 *//*
	@RequestMapping("/saleWay/del")
	@ResponseBody
	public String del(int id){
//		RemoteServiceSingleton.getInstance().getPlatformUserManagerService().
		PageBean paramPage = new PageBean();
		try {
			Integer source =2;
			PlatformDto param = new PlatformDto();
			param.setSource(source);
			
			
			paramPage.setSortFields("create_time,status");
			paramPage.setOrder("desc,asc");
			if(null != param){
				String status = param.getStatus();
				if(status!=null && status.equals(Constants.DEFULTSTRING)){
					param.setStatus(null);
				}
			}
			Integer type = param.getType();
			
			
			if ( null != type && type == -1){
				param.setType(null);
			}
			paramPage.setParameter(param);
			paramPage.setPageSize(Constants.NUMMAX);
			if(param!=null&&param.getSource()==Constants.INT2){
				paramPage = RemoteServiceSingleton.getInstance().getRetailerManagerService().getSimplePageList(paramPage);
			}
		
		} catch (Exception e) {
			logger.debug(""+e);
		}
		//零售商查询的结果和渠道ID判断
		List<RetailerQueryDto> list = paramPage.getResult();
		for (RetailerQueryDto object : list) {
			//logger.info(String.valueOf(object.getChannelId()));
			if(null!=object.getChannelId()){
				
				if(id==object.getChannelId()){
					return ""+0;
				}
			}
		}
		
		int deleatWay = RemoteServiceSingleton.getInstance().getChannelManagerService().deleteChannel(id);
		return ""+deleatWay;
		
	}
	

}
*/