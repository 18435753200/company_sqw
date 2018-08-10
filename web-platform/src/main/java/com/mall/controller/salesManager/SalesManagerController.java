/*package com.mall.controller.salesManager;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.mall.mybatis.utility.PageBean;
import com.mall.platform.model.PlatformDto;
import com.mall.platform.model.PlatformSale;
import com.mall.retailer.model.RetailerQueryDto;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;

@Controller
public class SalesManagerController {
	
	private static final Logger logger = Logger.getLogger(SalesManagerController.class);

	@RequestMapping("/saleManager/toList")
	public String list(){
		return "/user/saleManager";
	}
	
	@RequestMapping("/saleManager/getModel")
	public String getList(Model model,PageBean  paramPage,HttpServletRequest request){
		
		List<PlatformSale> allSale = RemoteServiceSingleton.getInstance().getPlatformSaleManagerService().getAllSale();
		try {

//			String page = request.getParameter("page");
			paramPage.setPageSize(10);
			paramPage = RemoteServiceSingleton.getInstance().getPlatformSaleManagerService().getSalePageList(paramPage);
			allSale = paramPage.getResult();
		} catch (Exception e) {
			logger.info("------------------"+e);
		}
		model.addAttribute("allSale", allSale);
		model.addAttribute("pb", paramPage);
		return "/user/modelPage/saleManagerModel";
	}
	
	
	@RequestMapping("/saleManager/saveSale")
	@ResponseBody
	public String save(String name,String phone){
		
		PlatformSale platformSale = new PlatformSale();
		platformSale.setSaleName(name);
		platformSale.setSalePhone(phone);
		
		int insertSale = RemoteServiceSingleton.getInstance().getPlatformSaleManagerService().insertSale(platformSale);
		return "" + insertSale;
	}
	
	@RequestMapping("/saleManager/editSale")
	@ResponseBody
	public String toEdit(int id,Model model){
		PlatformSale sale = RemoteServiceSingleton.getInstance().getPlatformSaleManagerService().getSaleById(id);
		
		String jsonString = JSON.toJSONString(sale);
		return jsonString;
	}
	
	
	@RequestMapping("/saleManager/updateSale")
	@ResponseBody
	public String update(PlatformSale platformSale){
		int updateSale = RemoteServiceSingleton.getInstance().getPlatformSaleManagerService().updateSale(platformSale);
		
		return "" + updateSale;
	}
	
	@RequestMapping("/saleManager/delSale")
	@ResponseBody
	public String del(int id){
		
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
			
			if(param!=null&&param.getSource()==Constants.INT2){
				paramPage = RemoteServiceSingleton.getInstance().getRetailerManagerService().getSimplePageList(paramPage);
			}
		
		} catch (Exception e) {
			//System.out.println("+++++++++++++++++"+e);
			logger.info("+++"+e);
		}
		List<RetailerQueryDto> list = paramPage.getResult();
		for (RetailerQueryDto retailerQueryDto : list) {
			logger.info(retailerQueryDto.getSaleId());
			//System.out.println("===============++++++++"+retailerQueryDto.getSaleId());
			if(null!=retailerQueryDto.getSaleId()){
				if(id==retailerQueryDto.getSaleId()){
					return ""+0;
				}
			}
		}
		
		int deleteSale = RemoteServiceSingleton.getInstance().getPlatformSaleManagerService().deleteSale(id);
		
		return ""+deleteSale;
	}
	
	
	
}
*/