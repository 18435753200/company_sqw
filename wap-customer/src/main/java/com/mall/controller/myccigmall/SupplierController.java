package com.mall.controller.myccigmall;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.csource.common.MyException;
import org.csource.upload.UploadFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mall.annotation.AuthPassport;
import com.mall.category.api.own.TeSectorsService;
import com.mall.category.api.rpc.BaseDataServiceRpc;
import com.mall.category.po.AgentCity;
import com.mall.category.po.AgentCounty;
import com.mall.category.po.AgentProvince;
import com.mall.category.po.TeSectors;
import com.mall.constant.RequestContant;
import com.mall.constant.ResponseContant;
import com.mall.controller.base.BaseController;
import com.mall.customer.model.User;
import com.mall.customer.order.utils.HttpRequest;
import com.mall.dsearch.api.SearchService;
import com.mall.dsearch.vo.SearchSupplierDetailRequest;
import com.mall.dsearch.vo.SearchSupplierDetailResponse;
import com.mall.dsearch.vo.SearchSupplierRequest;
import com.mall.dsearch.vo.SearchSupplierResponse;
import com.mall.dsearch.vo.SupplierDetailAttr;
import com.mall.supplier.model.Supplier;
import com.mall.supplier.model.SupplierDetail;
import com.mall.supplier.service.SupplierManagerService;
import com.mall.utils.ApiResult;
import com.mall.utils.CommonUtil;
import com.mall.wap.proxy.RemoteServiceSingleton;

@Controller
@RequestMapping(value=RequestContant.SUPPLIER)
public class SupplierController extends BaseController{

	@Autowired
	private BaseDataServiceRpc baseDataServiceRpc;
	@Autowired 
	private SupplierManagerService supplierManagerService;
	@Autowired
	private  TeSectorsService teSectorsService;
	
	/**
	 * 跳转千城万店页面
	 * @param request
	 * @param model
	 * @param searchSupplierRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = RequestContant.SUPPLIER_THOUSANDCITYWANSHOP)
	public String tothousandCityWanShop(HttpServletRequest request,Model model,SearchSupplierRequest searchSupplierRequest) throws Exception{
		List<AgentProvince> allProvices = baseDataServiceRpc.getAllProvices();
		model.addAttribute("allProvices", allProvices);
		if(searchSupplierRequest.getSectorCodeName()!=null&&!"".equals(searchSupplierRequest.getSectorCodeName())){
			model.addAttribute("sectorCodeName",searchSupplierRequest.getSectorCodeName());
		}
		if(searchSupplierRequest.getCityId()!=null){
			model.addAttribute("cityId", searchSupplierRequest.getCityId());
		}else{
			model.addAttribute("cityId", "");
		}
		SearchService searchApi = RemoteServiceSingleton.getInstance().getSearchApi();
		//es搜索
		SearchSupplierResponse searchSupplier = searchApi.searchSupplier(searchSupplierRequest);
		AgentCity cityById = baseDataServiceRpc.getCityById(searchSupplierRequest.getCityId());
		model.addAttribute("city", cityById.getCityname());
		model.addAttribute("sectorCodeName", searchSupplierRequest.getSectorCodeName());
		if(searchSupplier!=null){
			
			Integer totalPage = searchSupplier.getHits()/searchSupplierRequest.getPageSize();
			totalPage = searchSupplier.getHits() % searchSupplierRequest.getPageSize()==0 ? totalPage : totalPage+1;
			model.addAttribute("page", 1);
			model.addAttribute("totalPage", totalPage);
			model.addAttribute("searchSupplier", searchSupplier);
			model.addAttribute("pageNo", searchSupplierRequest.getPageNo());
			model.addAttribute("numFetch", searchSupplier.getNumFetch());
		}
		return ResponseContant.SUPPLIER_THOUSANDCITYWANSHOP;
		
	}
	
	/**
	 * 上拉加载
	 * @param request
	 * @param searchSupplierRequest
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/ajaxPullUp")
	public ApiResult ajaxPullUp(HttpServletRequest request,SearchSupplierRequest searchSupplierRequest) throws Exception{
		ApiResult result = new ApiResult();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("page", searchSupplierRequest.getPageNo());
		SearchService searchApi = RemoteServiceSingleton.getInstance().getSearchApi();
		//es搜索
		SearchSupplierResponse searchSupplier = searchApi.searchSupplier(searchSupplierRequest);
		map.put("searchSupplier", searchSupplier);
		result.setCode(0);
		result.setData(map);
		return result;
	}
	

	
	/**
	 * 查看店铺详情
	 * @param supplierId
	 * @return
	 */
	@RequestMapping("/lookDetail")
	public String  lookDetail(Model model,Integer supplierId){
		//定义搜索查询类
		SearchSupplierDetailRequest searchSupplierDetailRequest=new SearchSupplierDetailRequest();
		searchSupplierDetailRequest.setSupplierId(supplierId);
		SearchService searchApi = RemoteServiceSingleton.getInstance().getSearchApi();
		//搜索返回数据
		SearchSupplierDetailResponse searchSupplierDetail = searchApi.searchSupplierDetail(searchSupplierDetailRequest);
		//判断商家是否上传经营环境照片(0没有,1有)
		Integer isHavaJY=0;
		//判断商家是否上传门店推荐照片(0没有,1有)
		Integer isHavaTJ=0;
		for(SupplierDetailAttr supplierDetailAttr : searchSupplierDetail.getSupplier().getAttrList()){
			if(supplierDetailAttr.getAttrType().equals(1)){
				isHavaJY=1;
			}
			if(supplierDetailAttr.getAttrType().equals(2)){
				isHavaTJ=1;
			}
		}
		
		model.addAttribute("ssd",searchSupplierDetail.getSupplier());
		model.addAttribute("isHavaJY",isHavaJY);
		model.addAttribute("isHavaTJ",isHavaTJ);
		return ResponseContant.SUPPLIER_SHOPDETAIL;
	}
	/**
	 * 跳转门店管理页面
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@AuthPassport
	@RequestMapping("/toMyStore")
	public String toMyStore(Model model,HttpServletRequest request) throws Exception{
		User user = getCurrentUser(request);
		if(user!=null){
			if(user.getSupplierId()!=null&&!"".equals(user.getSupplierId())){
				SupplierDetail supplierDetail = supplierManagerService.findSupplierDetailBySupplierId(Integer.valueOf(user.getSupplierId()));
				if(supplierDetail.getProvinceId()!=null){
					AgentProvince province = baseDataServiceRpc.getProvinceById(supplierDetail.getProvinceId());
					model.addAttribute("province", province);
				}
				if(supplierDetail.getCityId()!=null){
					AgentCity city = baseDataServiceRpc.getCityById(supplierDetail.getCityId());
					model.addAttribute("city", city);
				}
				if(supplierDetail.getAreaId()!=null){
					AgentCounty county = baseDataServiceRpc.getCountyById(supplierDetail.getAreaId());
					model.addAttribute("county", county);
				}
				model.addAttribute("ssd", supplierDetail);
			}
		}
		return ResponseContant.SUPPLIER_UPDATESHOPDETAIL;
	}
	/**
	 * 门头图片上传
	 * @param businessLicense
	 * @param companyStoreLogo
	 * @param idCardFront
	 * @param idCardVerso
	 * @return
	 * @throws IOException
	 * @throws MyException
	 */
	@ResponseBody
	@RequestMapping(value="regisUploadFile")
	public void regisUploadFile(@RequestParam(required=false)MultipartFile companyStoreLogo,HttpServletRequest request) throws IOException, MyException{
		User user = getCurrentUser(request);
		String url="";
		if(user!=null){
			if(user.getSupplierId()!=null&&!"".equals(user.getSupplierId())){
				if(companyStoreLogo!=null&&!"".equals(companyStoreLogo)){
					SupplierDetail supplierDetail = supplierManagerService.findSupplierDetailBySupplierId(Integer.valueOf(user.getSupplierId()));
					if(supplierDetail!=null){
						url = UploadFileUtil.uploadFile(companyStoreLogo.getInputStream(),CommonUtil.getFileExt2(companyStoreLogo.getOriginalFilename()+".jpg"), null);
						supplierDetail.setCompanyStoreLogo(url);
						supplierManagerService.updateShopTop(supplierDetail);
					}
				}
			}
		}
		
	}
	/**
	 * 添加/修改定位
	 * @param supplierDetail
	 */
	@ResponseBody
	@RequestMapping("/addLocation")
	public void addLocation(SupplierDetail supplierDetail){
		//设置地图经纬度类型
		supplierDetail.setLocationType(5);
		supplierManagerService.updateShopTop(supplierDetail);
	}
/**
 * 经营环境图片上传
 * @param businessLicense
 * @param companyStoreLogo
 * @param idCardFront
 * @param idCardVerso
 * @return
 * @throws IOException
 * @throws MyException
 */
@ResponseBody
@RequestMapping(value="uploadPhone")
public ApiResult uploadPhone(@RequestParam(required=false)MultipartFile companyStoreLogo,Integer attrType,HttpServletRequest request) throws IOException, MyException{
	ApiResult result = new ApiResult();
	User user = getCurrentUser(request);
	String url="";
	try{
		if(user!=null){
			if(user.getSupplierId()!=null&&!"".equals(user.getSupplierId())){
				if(companyStoreLogo!=null&&!"".equals(companyStoreLogo)){
					SupplierDetail supplierDetail = supplierManagerService.findSupplierDetailBySupplierId(Integer.valueOf(user.getSupplierId()));
					if(supplierDetail!=null){
						url = UploadFileUtil.uploadFile(companyStoreLogo.getInputStream(),CommonUtil.getFileExt2(companyStoreLogo.getOriginalFilename()+".jpg"), null);
						com.mall.supplier.model.SupplierDetailAttr supplierDetailAttr= new com.mall.supplier.model.SupplierDetailAttr();
						supplierDetailAttr.setStoreId(supplierDetail.getId());
						supplierDetailAttr.setAttrValue(url);
						if(attrType!=null){
							supplierDetailAttr.setAttrType(attrType);
						}else{
							supplierDetailAttr.setAttrType(1);
						}
						 supplierManagerService.insertDetailAttr(supplierDetailAttr);
					}
				}
			}
		}	
		result.setCode(0);
	}catch(Exception e){
	}
	return result;
}
/**
 * 删除img
 */
@ResponseBody
@RequestMapping(value="deletePhoto")
public ApiResult deletePhoto(Integer id){
	ApiResult result=new ApiResult();
	try{
		supplierManagerService.deleteOfflineStroe(id);
		result.setMessage("删除成功");
	}catch(Exception e){
		result.setMessage("未知错误");
	}
	return result;
}
/**
 * 点击跳转门店信息修改
 * @throws Exception 
 */
@RequestMapping(value="updateMdxx")
public String updateMdxx(HttpServletRequest request,Model model,Integer select) throws Exception{
	User user = getCurrentUser(request);
	if(user!=null){
		if(user.getSupplierId()!=null&&!"".equals(user.getSupplierId())){
			SupplierDetail supplierDetail = supplierManagerService.findSupplierDetailBySupplierId(Integer.valueOf(user.getSupplierId()));
			if(supplierDetail.getProvinceId()!=null){
				//查询当前省份
				AgentProvince province = baseDataServiceRpc.getProvinceById(supplierDetail.getProvinceId());
				//通过省份ID查询所有市
				List<AgentCity> allCity = baseDataServiceRpc.getCitiesByProvinceId(supplierDetail.getProvinceId());
				model.addAttribute("provinces", province);
				model.addAttribute("allCitys", allCity);
			}
			if(supplierDetail.getCityId()!=null){
				//查询当前市
				AgentCity city = baseDataServiceRpc.getCityById(supplierDetail.getCityId());
				//通过市ID查询所有县
				List<AgentCounty> allCounty = baseDataServiceRpc.getCountiesByCityId(supplierDetail.getCityId());
				model.addAttribute("city", city);
				model.addAttribute("allCountys", allCounty);
			}
			if(supplierDetail.getAreaId()!=null){
				//查询当前县
				AgentCounty county = baseDataServiceRpc.getCountyById(supplierDetail.getAreaId());
				model.addAttribute("county", county);
			}
			model.addAttribute("ssd", supplierDetail);
		}
	}
	//加载省
	List<AgentProvince> allProvices = baseDataServiceRpc.getAllProvices();
	model.addAttribute("allProvices", allProvices);
	model.addAttribute("select", select);
	return "/myccig/supplierWealth/Modifyinfo";
}

/**
 * 跳转地图定位页面
 * @param model
 * @param supplierId
 * @return
 */
@RequestMapping("/toSaveLocation")
public String toSaveLocation(Model model,Integer supplierId,String nameJC){
	model.addAttribute("supplierId", supplierId);
	model.addAttribute("nameJC", nameJC);
	return ResponseContant.SUPPLIER_UPDATESHOPLOCATION;
}
/**
 * 修改门店信息
 */
@ResponseBody
@RequestMapping(value="updateData")
public ApiResult updateData(SupplierDetail supplierDetail){
	ApiResult result = new ApiResult();
	try{
		if(supplierDetail!=null){
			
			if(supplierDetail.getNameJC()!=null&&!"".equals(supplierDetail.getNameJC())){
				if(supplierDetail.getNameJC().length()>20){
					result.setMessage("简称请限制在20字以内");
					return result;
				}
			}
			if(supplierDetail.getContact()!=null&&!"".equals(supplierDetail.getContact())){
				if(supplierDetail.getContact().length()>20){
					result.setMessage("联系人名称请限制在20字以内");
					return result;
				}
			}
			if(supplierDetail.getContactTel()!=null&&!"".equals(supplierDetail.getContactTel())){
				if(supplierDetail.getContactTel().length()>11){
					result.setMessage("联系人电话请限制在11字以内");
					return result;
				}
			}
			if(supplierDetail.getJyTS()!=null&&!"".equals(supplierDetail.getJyTS())){
				if(supplierDetail.getJyTS().length()>50){
					result.setMessage("经营特色请限制在100字以内");
					return result;
				}
			}
			if(supplierDetail.getJySJ()!=null&&!"".equals(supplierDetail.getJySJ())){
				if(supplierDetail.getJySJ().length()>20){
					result.setMessage("经营时间请限制在100字以内");
					return result;
				}
			}
			if("".equals(supplierDetail.getJyTS())){
				supplierDetail.setJyTS(null);
			}
			if("".equals(supplierDetail.getJySJ())){
				supplierDetail.setJySJ(null);
			}
		supplierManagerService.updateShopTop(supplierDetail);
		result.setCode(0);
		result.setMessage("修改成功");
	}}
	catch(Exception e){
		result.setMessage("修改失败");
	}
	return result;
}
}