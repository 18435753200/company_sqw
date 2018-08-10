package com.mall.controller.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.csource.upload.UploadFileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.category.po.Brand;
import com.mall.mybatis.utility.PageBean;
import com.mall.supplier.model.SupplierBrand;
import com.mall.supplier.model.SupplierBrandDTO;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;


/**
 * 品牌审核.
 * @author zhouzb
 */
@Controller
@RequestMapping(value="/brand")
public class BrandAuditController extends BaseController{

	/**
	 * LOGGER.
	 */
	private static final Log LOGGER = LogFactory.getLogger(BrandAuditController.class);
	
	/**
	 * @return 返回Page地址.
	 */
	@RequestMapping(value="/toBrandAuditList")
	public String toEquipmentAuditPage(){
		
		LOGGER.info("toBrandAuditList");
		
		return "/user/brandAudit";
	}
	
	/**
	 * @param request HttpServletRequest.
	 * @param response HttpServletResponse.
	 * @param page  分页页码.
	 * @param retailerEquipmentPay 参数对象 .
	 * @return 返回Page页面.
	 */
	@RequestMapping(value="/getBrandAuditList")
	public String getBrandAuditList(HttpServletRequest request,HttpServletResponse response
			,PageBean<SupplierBrandDTO> pageBean ,SupplierBrandDTO supplierBrandDto
			){
		try {
			pageBean.setSortFields("create_time");
			
			pageBean.setOrder("desc");
			
			pageBean.setPageSize(Constants.PAGESIZE);
			
			pageBean.setParameter(supplierBrandDto);
			
			pageBean = RemoteServiceSingleton.getInstance().
					getSupplierBrandManagerService().getBrandsPageList(pageBean);
			
			request.getSession().setAttribute("pb",pageBean);
			
			request.getRequestDispatcher("/WEB-INF/views/user/modelPage/brandAuditmodel.jsp")
				.forward(request, response);
			

		} catch (Exception e) {
			
			LOGGER.error("商户Id:"+getCurrentPlatformId());
			LOGGER.error("用户:"+getCurrentUser().getUsername());
			LOGGER.error("用户ID:"+getCurrentUser().getId());
			LOGGER.error("查询设备审核信息列表失败   info:"+e.getMessage(),e);
			
		}
		
		return null;
		
	}
	
	@RequestMapping(value="/getBrandDetail")
	public String getBrandDetail(Model model , Long brandId){
		
		SupplierBrandDTO brand = new SupplierBrandDTO();
		
		
		
		List<String> qualification = new ArrayList<String>();
		try {
			
			brand = RemoteServiceSingleton.getInstance().getSupplierBrandManagerService().findBrandById(brandId);

			if(null != brand){

				String qualificationstr = brand.getQualification();

				if(qualificationstr.length()>0){

					String[] qualifications = qualificationstr.split(",");

					if(null != qualifications && qualifications.length>0){

						qualification = Arrays.asList(qualifications);

						for (int i = 0; i < qualification.size(); i++) {

							String src = qualification.get(i);

							if(!src.toUpperCase().startsWith("HTTP")){

								qualification.set(i, Constants.FILESERVER1+src);

							}

						}
					}
				}
				
				
				String description = brand.getDescription();
				if (null != description){
					if(description.indexOf("group") != -1){
						description = UploadFileUtil.DownloadFile(description);
					}
				}
				
				brand.setDescription(description);
			}
		} catch (Exception e) {
			
			LOGGER.error(e.getMessage(),e);
			
		}
		
		
		
		model.addAttribute("qualification", qualification);
		model.addAttribute("brand", brand);
		
		return "/user/branddetail";
	}
	
	
	/**
	 * 品牌审核检测
	 * @return
	 */
	@RequestMapping(value="/checkBrand")
	@ResponseBody
	public String checkBrand(SupplierBrand  supplierBrand){
		
		 Long systemBrandId = supplierBrand.getSystemBrandId();
		 
		 String checkstat = Constants.SUCCESS;
		 
		 supplierBrand.setAuditTime(new Date());
		 
		 try {
			
			 Brand brand = RemoteServiceSingleton.getInstance().getBrandServiceRpc().findBrandById(systemBrandId+"");
			 
			 if(null == brand){
				 
				 String sysBrandId = RemoteServiceSingleton.getInstance().getBrandServiceRpc().brandExist(supplierBrand.getName());
				 
				 if(null != sysBrandId && !"".equals(sysBrandId)){
					 
					 supplierBrand.setSystemBrandId(Long.parseLong(sysBrandId));
					 
					 int isSuccess = RemoteServiceSingleton.getInstance().getSupplierBrandManagerService().updateBrand(supplierBrand);
					 checkstat = isSuccess + "";
				 } else {
					 checkstat = Constants.ERROR;
				 }
			 }

		 } catch (Exception e) {
			 checkstat = "-1";
			 
			 LOGGER.error("检测品牌失败！！！"+e.getMessage(),e);
		}
		
		return checkstat;
		
	}
	
	
	
	/**
	 * @return
	 */
	@RequestMapping(value="/brandAudit")
	@ResponseBody
	public String brandAudit(SupplierBrand  supplierBrand){
		
		logger.info("审核品牌！！！"+supplierBrand.getBrandId()+supplierBrand.getName()+supplierBrand.getSystemBrandId());
		
		supplierBrand.setAuditTime(new Date());
		
		String checkstat = Constants.SUCCESS;
		
		try {
			
			checkstat = RemoteServiceSingleton.getInstance().
					getSupplierBrandManagerService().updateBrand(supplierBrand)+"";

		} catch (Exception e) {
			
			 LOGGER.error("审核品牌失败！！！"+supplierBrand.getName()+ e.getMessage(),e);
			 
		}
		
		return checkstat;
	}
	
	/**
	 * @return
	 */
	@RequestMapping(value="/saveBrandENAndZHName")
	@ResponseBody
	public String saveBrandENAndZHName(SupplierBrand supplierBrand,String enBrand,String zhBrand,String systemBrandId){
		
		String stat = Constants.SUCCESS;
		
		supplierBrand.setAuditTime(new Date());
		
		int updateBrandStat = Constants.INT1;
		
		try {

			int synchronizBrandNameStat = RemoteServiceSingleton.getInstance().
					getBrandServiceRpc().synchronizBrandName(systemBrandId, zhBrand, enBrand);
			
			//0成功 -1同步失败 1中文名存在 2英文名存在 3id为空 4中文名空 5英文名空
			if(synchronizBrandNameStat == Constants.INT0){
				
				supplierBrand.setStatus(Constants.NUM1);
				
				updateBrandStat = RemoteServiceSingleton.getInstance().
						getSupplierBrandManagerService().updateBrand(supplierBrand);
			
				if(updateBrandStat == Constants.INT0){
					
					stat = Constants.SERVERISBESY;
					
				}
			}
			
			stat = synchronizBrandNameStat+"";
			
		} catch (Exception e) {
		
			LOGGER.error("审核品牌失败！！！id:"+systemBrandId+e.getMessage(),e);
			
		}
		
		return stat;
	}
	
}
