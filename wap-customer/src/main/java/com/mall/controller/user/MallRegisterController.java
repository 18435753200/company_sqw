package com.mall.controller.user;

import com.mall.annotation.AuthPassport;
import com.mall.category.api.own.TeSectorsService;
import com.mall.category.api.rpc.BankService;
import com.mall.category.api.rpc.BaseDataServiceRpc;
import com.mall.category.model.BankBranch;
import com.mall.category.model.BankCode;
import com.mall.category.po.AgentCity;
import com.mall.category.po.AgentCounty;
import com.mall.category.po.AgentProvince;
import com.mall.category.po.TeSectors;
import com.mall.constant.CommonConstant;
import com.mall.constant.ResponseContant;
import com.mall.controller.base.BaseController;
import com.mall.customer.model.ShareRelationship;
import com.mall.customer.model.User;
import com.mall.customer.service.UserService;
import com.mall.supplier.model.OffLineSupplier;
import com.mall.supplier.model.Supplier;
import com.mall.supplier.model.SupplierSalesDiscount;
import com.mall.supplier.model.SupplierUser;
import com.mall.supplier.service.SupplierDiscountService;
import com.mall.supplier.service.SupplierManagerService;
import com.mall.supplier.service.SupplierUserManagerService;
import com.mall.utils.*;
import net.rubyeye.xmemcached.MemcachedClient;
import org.apache.log4j.Logger;
import org.csource.common.MyException;
import org.csource.upload.UploadFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="mallRegister")
public class MallRegisterController extends BaseController{
    private static Logger logger = Logger.getLogger(MallRegisterController.class);

	//MP对应的type值
	public static final Integer SUPPLIERTYPEMP=1650;
	
	@Autowired
	private UserService userService;
	@Autowired
	private SupplierManagerService supplierManagerService;
	
	@Autowired
	private TeSectorsService teSectorsService;
	
	@Autowired
	private BaseDataServiceRpc baseDataServiceRpc;
	
	@Autowired
	protected MemcachedClient memcachedClient;
	
	@Autowired
	private SupplierUserManagerService supplierUserManagerService;
	@Autowired
	private BankService bankService;
	@Autowired
	private SupplierDiscountService supplierDiscountService;
	

	/**
	 * MP激活二维码
	 * rcid:二维码id
	 */
	@AuthPassport
	@ResponseBody
	@RequestMapping("/activation")
	public String activation(String rcid,HttpServletRequest request, HttpServletResponse response){
		User mpSupplierUser =getCurrentUser(request);
		if (mpSupplierUser == null || "".equals(mpSupplierUser)) {
			return ResponseContant.LOGIN;
		}
		if(mpSupplierUser!=null&&!"".equals(mpSupplierUser)){
			if(mpSupplierUser.getType()!=SUPPLIERTYPEMP){
				return "您不是众聚猫合作伙伴,当前二维码只有众聚猫合作伙伴可用!";
			}
		}else{
			return "您的个人信息有误";
		}
		if(rcid==null||"".equals(rcid)){
			return "二维码id不能为空";
		}
		Integer mpActivateBizRcode = userService.mpActivateBizRcode(rcid, Long.parseLong((mpSupplierUser.getSupplierId())));
		if(mpActivateBizRcode==0){
			return "激活成功!";
		}
		return "激活失败";
	}
	/**
	 * 激活二维码成功页面
	 */
	@AuthPassport
	@RequestMapping("/toActivation")
	public String toActivation(Model model){
		model.addAttribute("success", "您已成功激活二维码");
		return "/qrcode/jhewm";
	}
	/**
	 * 商户中心查看商家信息
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@AuthPassport
	@RequestMapping("/selectMall")
	public String selectMall(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception{
		User currentUser =getCurrentUser(request);
		User user = userService.findUserById(currentUser.getUserId());
		if(null!=user.getSupplierId()&&!"".equals(user.getSupplierId())){
			 Supplier supplier = supplierManagerService.findSupplier(Long.parseLong(user.getSupplierId()));
			 if(supplier!=null&&!"".equals(supplier)){
				 if(supplier.getProvinceId()!=null&&!"".equals(supplier.getProvinceId())){
					 AgentProvince province = baseDataServiceRpc.getProvinceById(supplier.getProvinceId());
					 model.addAttribute("province", province);
				 }
				 if(supplier.getCityId()!=null&&!"".equals(supplier.getCityId())){
					 AgentCity city = baseDataServiceRpc.getCityById(supplier.getCityId());
					 model.addAttribute("city", city);
				 }
				 if(supplier.getAreaId()!=null&&!"".equals(supplier.getAreaId())){
					 AgentCounty county = baseDataServiceRpc.getCountyById(supplier.getAreaId());
					 model.addAttribute("county", county);
				 }
				 if(supplier.getCompanyBizCategory()!=null&&!"".equals(supplier.getCompanyBizCategory())){
					 TeSectors teSectors = teSectorsService.selectByPrimaryKey(supplier.getCompanyBizCategory());
					 model.addAttribute("teSectors", teSectors);
				 }
				 if(supplier.getAccoutBankno()!=null&&!"".equals(supplier.getAccoutBankno())){
					 BankBranch bankBranch = bankService.findBankBranchByName(supplier.getAccoutBankname(),supplier.getAccoutBankno());
					 model.addAttribute("bankBranch", bankBranch);
				 }
				 if(supplier.getSupplierId()!=null&&!"".equals(supplier.getSupplierId())){
					 SupplierSalesDiscount discount = supplierDiscountService.findBySupplierId(supplier.getSupplierId());
					 model.addAttribute("discount", discount);
				 }
				 if(supplier.getStatus()==2){
					 model.addAttribute("activeStatus", "edit");
				 }
				 String status="";
				 if(supplier.getStatus()==2&&supplier.getModifyStatus()==2){
					 status="审核驳回";
					 model.addAttribute("checkFailReason", supplier.getCheckFailReason());
				 }
				 if(supplier.getStatus()==0){
					 status="审核中";
				 }
				 if(supplier.getStatus()==2&&supplier.getModifyStatus()!=2){
					 status="审核未通过";
					 model.addAttribute("checkFailReason", supplier.getCheckFailReason());
				 }
				 if(supplier.getActiveStatus()==1&&supplier.getStatus()==1){
					 status="审核通过";
				 }
				 if(supplier.getStatus()==5){
					 status="冻结";
				 }
				 model.addAttribute("status", status);
				 model.addAttribute("supplier", supplier);
			 }
		}
		return "/qrcode/selectMerchant";
	}
	/**
	 * 跳转修改商户信息页面
	 * @throws Exception 
	 */
	@AuthPassport
	@RequestMapping("/toUpdateMall")
	public String updateMall(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception{
		User currentUser =getCurrentUser(request);
		User user = userService.findUserById(currentUser.getUserId());
		if(null!=user.getSupplierId()&&!"".equals(user.getSupplierId())){
			 Supplier supplier = supplierManagerService.findSupplier(Long.parseLong(user.getSupplierId()));
			 if(supplier!=null&&!"".equals(supplier)){
					 model.addAttribute("supplier", supplier);
					 AgentProvince province = baseDataServiceRpc.getProvinceById(supplier.getProvinceId());
					 AgentCity city = baseDataServiceRpc.getCityById(supplier.getCityId());
					 AgentCounty county = baseDataServiceRpc.getCountyById(supplier.getAreaId());
					 BankBranch bankBranchByCode = bankService.findBankBranchByName(supplier.getAccoutBankname(),supplier.getAccoutBankno());
					 List<BankCode> bank = bankService.findAllBank();
					 SupplierSalesDiscount discount = supplierDiscountService.findBySupplierId(supplier.getSupplierId());
					 model.addAttribute("province", province);
					 model.addAttribute("city", city);
					 model.addAttribute("county", county);
					 model.addAttribute("bankBranchByCode", bankBranchByCode);
					 model.addAttribute("bank", bank);
					 model.addAttribute("discount", discount);
					//加载省
					List<AgentProvince> allProvices = baseDataServiceRpc.getAllProvices();
					model.addAttribute("allProvices", allProvices);
					//加载所有银行
					List<BankCode> list = bankService.findAllBank();
					model.addAttribute("list", list);
			 }
		}
		return "/qrcode/updateMerchant";
	}
	@AuthPassport
	@RequestMapping("/updateMall")
	public void updateMall (String name,String businessLicenseno,String nameJc,Integer companyBizType,Integer companyBizCategory,String rcodetxt,String rcid,String yinhangkazhaopian,
			 Integer provinceId,Integer cityId,Integer areaId,String address,String email,String companyInfo,String mobile,String loginName,String pwd,String salesDiscount,String accoutNo,
			HttpServletRequest request,HttpServletResponse response,String legalPerson,String legalPersonPhone,String contact,String contactTel,String accoutName,String bankBranchname,
			String registerAddress,String legalPersonCardno,String phone,Long supplierId,Model model,String yingyezhizhao,String mentouzhaopian,String shenfenzhengzhengmian,String shenfenzhengfanmian) throws IOException, MyException{
		User currentUser = getCurrentUser(request);
		Supplier supplier = new Supplier();
		
		if(yingyezhizhao!=null&&!"".equals(yingyezhizhao)){
			supplier.setBusinessLicense(yingyezhizhao);
		}
		if(mentouzhaopian!=null&&!"".equals(mentouzhaopian)){
			supplier.setCompanyStoreLogo(mentouzhaopian);
		}
		if(shenfenzhengzhengmian!=null&&!"".equals(shenfenzhengzhengmian)){
			supplier.setIdCardFront(shenfenzhengzhengmian);
		}
		if(shenfenzhengfanmian!=null&&!"".equals(shenfenzhengfanmian)){
			supplier.setIdCardVerso(shenfenzhengfanmian);
		}
		if(yinhangkazhaopian!=null&&!"".equals(yinhangkazhaopian)){
			supplier.setBankAccountPic(yinhangkazhaopian);
		}
		supplier.setSupplierId(supplierId);
		supplier.setName(name);
		supplier.setAddress(address);
		supplier.setRegisterAddress(registerAddress);
		supplier.setNameJC(nameJc);
		supplier.setBusinessLicenseNo(businessLicenseno);
		supplier.setLegalPerson(legalPerson);
		supplier.setLegalPersonPhone(legalPersonPhone);
		supplier.setCompanyBizType(companyBizType);
		supplier.setCompanyBizCategory(companyBizCategory);
		supplier.setEmail(email);
		supplier.setPhone(phone);
		supplier.setUserId(currentUser.getUserId());
		supplier.setUserName(currentUser.getUserName());
		supplier.setUserMobile(currentUser.getMobile());
		supplier.setContact(contact);
		supplier.setContactTel(contactTel);
		supplier.setAccountName(accoutName);
		//以,拆分  第一个是联合号,第二个是联合号名称
		String[] bankPplit = bankBranchname.split(",");
		supplier.setAccoutBankno(bankPplit[0]);
		supplier.setAccoutBankname(bankPplit[1]);
		supplier.setLegalPersonCardNo(legalPersonCardno);
		supplier.setStatus(0);
		supplier.setActiveStatus(-1);
		supplier.setModifyStatus(1);
		
		BankBranch bankBranch = bankService.findBankBranchByName(bankPplit[1],bankPplit[0]);
		if(bankBranch!=null){
			supplier.setAccoutBankname(bankBranch.getBankBranchName());
		}
		supplier.setAccountNo(accoutNo);
		supplier.setProvinceId(provinceId);
		supplier.setCityId(cityId);
		supplier.setAreaId(areaId);
		
		supplierManagerService.updataSupplier(supplier);
		
		response.sendRedirect("/mallRegister/selectMall?success=1");
	}
	/**
	 * 扫码跳转M注册页
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@AuthPassport
	@RequestMapping("/toRegister")
	public String toRegister(String rcid,String rcodetxt,Model model,HttpServletRequest request, HttpServletResponse response) throws Exception{
	
		if(rcodetxt==null||rcodetxt.equals("")){
			return "该二维码尚未激活";
		}
		User currentUser = getCurrentUser(request);
		//加载省
		List<AgentProvince> allProvices = baseDataServiceRpc.getAllProvices();
		model.addAttribute("allProvices", allProvices);
		//加载所有银行
		List<BankCode> list = bankService.findAllBank();
		model.addAttribute("rcid", rcid);
		model.addAttribute("rcodetxt", rcodetxt);
		model.addAttribute("bankList", list);
		model.addAttribute("user", currentUser);
		return "/qrcode/merchant";
	}
	/**
	 * 通过省查询市
	 * @param type
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/getCity")
	public List<AgentCity> getCity(Integer provinceid) throws Exception{
		List<AgentCity> allCity = baseDataServiceRpc.getCitiesByProvinceId(provinceid);
		return allCity;
	}
	/**
	 * 通过市查询区县
	 * @param type
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/getCountry")
	public List<AgentCounty> getCountry(Integer cityid) throws Exception{
		List<AgentCounty> allCounty = baseDataServiceRpc.getCountiesByCityId(cityid);
		return allCounty;
	}
	/**
	 * 加载企业经营类目
	 */
	@ResponseBody
	@RequestMapping("/getType")
	public List<TeSectors> getType(TeSectors teSectors){
		List<TeSectors> list = teSectorsService.getCategoryByTeSectors(teSectors);
		return list;
	}
	/**
	 *获取所有银行的省份数据 
	 */
	@RequestMapping("/getBankProvinceList")
	@ResponseBody
	public Map<String, String> getBankProvinceList(Integer bankCode) throws Exception {
		 Map<String, String> map = bankService.findBankOfProvince(bankCode);
		return map;
	}
	
	/**
	 *加载该银行所在省的所有区的数据
	 */
	@RequestMapping("/getBankCityList")
	@ResponseBody
	public Map<String, String> getBankCityList(Integer bankCode,Integer bankProvinceId) throws Exception {
		 Map<String, String> map = bankService.findBankOfCity(bankCode, bankProvinceId);
		return map;
	}
	
	/**
	 *  加载该所在省的所有区的所有银行的数据
	 */
	@RequestMapping("/getSubBankOfCity")
	@ResponseBody
	public List<BankBranch> getSubBankOfCity(Integer bankCode,Integer bankAreaId) throws Exception {
		List<BankBranch> list = bankService.findSubBankOfCity(bankCode, bankAreaId);
		return list;
	}
	/**
	 * 查询pop登录帐号是否已经注册
	 */
	@ResponseBody
	@RequestMapping("/selectUserByName")
	public String selectUserByName(String loginName){
		SupplierUser userName = supplierUserManagerService.getUserByName(loginName);
		if(userName!=null||"".equals(userName)){
            return "501";
        }
		return "200";
	}
	/**
	 * 查询M站小号是否已有线下店铺
	 */
	@AuthPassport
	@ResponseBody
	@RequestMapping("/selectSupplier")
	public String selectSupplier(HttpServletRequest request,HttpServletResponse response){
		User currentUser = getCurrentUser(request);
		List<Supplier> supplierList = supplierManagerService.findSuppliersByUserIdAndSupplierId(currentUser.getUserId(), 1);
		
		String result ="false";
		if(supplierList.size()>=1){
			return result;
		}
		return "true";
	}

	 /**
	  * 提交注册
	  * @return
	 * @throws MyException 
	 * @throws Exception 
	  */
	@AuthPassport
	@RequestMapping("/regist")
    @ResponseBody
	public ApiResult regist(String name, String businessLicenseno, String nameJc, Integer companyBizType, Integer companyBizCategory, String rcodetxt, String rcid,String yinhangkazhaopian,
                            Long provinceId, Long cityId, Long areaId, String address, String email, String companyInfo, String mobile, String loginName, String pwd, String salesDiscount, String accoutNo,
                            HttpServletRequest request, HttpServletResponse response, String legalPerson, String legalPersonPhone, String contact, String contactTel, String accoutName, String bankBranchname,
                            String registerAddress, String legalPersonCardno, String phone, String yingyezhizhao, String mentouzhaopian, String shenfenzhengzhengmian, String shenfenzhengfanmian) throws Exception{
		User currentUser = getCurrentUser(request);
        ApiResult result = new ApiResult();
        if(name==null||"".equals(name)){
        	result.setMessage("注册名称不能为空");
        	return result;
        }
        if(nameJc==null||"".equals(nameJc)){
        	result.setMessage("营业名称不能为空");
        	return result;
        }
        if(registerAddress==null||"".equals(registerAddress)){
        	result.setMessage("注册地址不能为空");
        	return result;
        }
        if(areaId==null||"".equals(areaId)||cityId==null||"".equals(cityId)||provinceId==null||"".equals(provinceId)){
        	result.setMessage("省市区名称不能为空");
        	return result;
        }
        if(address==null||"".equals(address)){
        	result.setMessage("营业地址不能为空");
        	return result;
        }
        if(businessLicenseno==null||"".equals(businessLicenseno)){
        	result.setMessage("营业执照编码不能为空");
        	return result;
        }
        if(companyBizType==null||"".equals(companyBizType)){
        	result.setMessage("企业类型不能为空");
        	return result;
        }
        if(companyBizCategory==null||"".equals(companyBizCategory)){
        	result.setMessage("企业经营类目不能为空");
        	return result;
        }
        if(legalPerson==null||"".equals(legalPerson)){
        	result.setMessage("法定代表人不能为空");
        	return result;
        }
        if(legalPersonCardno==null||"".equals(legalPersonCardno)){
        	result.setMessage("法人身份证不能为空");
        	return result;
        }
        if(legalPersonPhone==null||"".equals(legalPersonPhone)){
        	result.setMessage("法定电话不能为空");
        	return result;
        }
        if(email==null||"".equals(email)){
        	result.setMessage("邮箱不能为空");
        	return result;
        }
        if(contact==null||"".equals(contact)){
        	result.setMessage("业务联系人不能为空");
        	return result;
        }
        if(contactTel==null||"".equals(contactTel)){
        	result.setMessage("联系电话不能为空");
        	return result;
        }
        if(phone==null||"".equals(phone)){
        	result.setMessage("联系手机不能为空");
        	return result;
        }
        if(accoutName==null||"".equals(accoutName)){
        	result.setMessage("银行账户名称不能为空");
        	return result;
        }
        if(bankBranchname==null||"".equals(bankBranchname)){
        	result.setMessage("开户银行支行不能为空");
        	return result;
        }
        if(accoutNo==null||"".equals(accoutNo)){
        	result.setMessage("银行帐号不能为空");
        	return result;
        }
        if(salesDiscount==null||"".equals(salesDiscount)){
        	result.setMessage("折扣不能为空");
        	return result;
        }
        if(yingyezhizhao==null||"".equals(yingyezhizhao)){
        	result.setMessage("营业执照照片不能为空");
        	return result;
        }
        if(mentouzhaopian==null||"".equals(mentouzhaopian)){
        	result.setMessage("门头照片不能为空");
        	return result;
        }
        if(yinhangkazhaopian==null||"".equals(yinhangkazhaopian)){
        	result.setMessage("银行卡照片不能为空");
        	return result;
        }
        if(shenfenzhengzhengmian==null||"".equals(shenfenzhengzhengmian)){
        	result.setMessage("身份证正面不能为空");
        	return result;
        }
        if(shenfenzhengfanmian==null||"".equals(shenfenzhengfanmian)){
        	result.setMessage("身份证反面不能为空");
        	return result;
        }
		OffLineSupplier offLineSupplier = new OffLineSupplier();
		
		if(yingyezhizhao!=null&&!"".equals(yingyezhizhao)){
			offLineSupplier.setBusinessLicense(yingyezhizhao);
		}
		if(mentouzhaopian!=null&&!"".equals(mentouzhaopian)){
			offLineSupplier.setCompanyStoreLogo(mentouzhaopian);
		}
		if(shenfenzhengzhengmian!=null&&!"".equals(shenfenzhengzhengmian)){
			offLineSupplier.setIdCardFront(shenfenzhengzhengmian);
		}
		if(shenfenzhengfanmian!=null&&!"".equals(shenfenzhengfanmian)){
			offLineSupplier.setIdCardVerso(shenfenzhengfanmian);
		}
		if(yinhangkazhaopian!=null&&!"".equals(yinhangkazhaopian)){
			offLineSupplier.setBankAccountPic(yinhangkazhaopian);
		}
		
		String encryptPassword = CipherUtil.generatePassword(pwd);
		offLineSupplier.setName(name);
		offLineSupplier.setAddress(address);
		offLineSupplier.setRegisterAddress(registerAddress);
		offLineSupplier.setNameJC(nameJc);
		offLineSupplier.setBusinessLicenseNo(businessLicenseno);
		offLineSupplier.setLegalPerson(legalPerson);
		offLineSupplier.setLegalPersonPhone(legalPersonPhone);
		offLineSupplier.setCompanyBizType(companyBizType);
		offLineSupplier.setCompanyBizCategory(companyBizCategory);
		offLineSupplier.setEmail(email);
		offLineSupplier.setPhone(phone);
		offLineSupplier.setUserId(currentUser.getUserId());
		offLineSupplier.setUserName(currentUser.getUserName());
		offLineSupplier.setUserMobile(currentUser.getMobile());
		offLineSupplier.setContact(contact);
		offLineSupplier.setContactTel(contactTel);
		offLineSupplier.setAccountName(accoutName);
		//以,拆分  第一个是联合号,第二个是联合号名称
		String[] bankPplit = bankBranchname.split(",");
		offLineSupplier.setAccoutBankno(bankPplit[0]);
		offLineSupplier.setAccoutBankname(bankPplit[1]);
		offLineSupplier.setLegalPersonCardNo(legalPersonCardno);
		BankBranch bankBranch = bankService.findBankBranchByName(bankPplit[1],bankPplit[0]);
		if(bankBranch!=null){
			offLineSupplier.setAccoutBankname(bankBranch.getBankBranchName());
		}
		offLineSupplier.setAccountNo(accoutNo);

        long supplierId = 0;
		try {
            supplierId = supplierManagerService.userRegisterOffLineSupplier(offLineSupplier, Long.parseLong(rcodetxt), companyBizType, companyBizCategory, currentUser.getUserId(), loginName, pwd, encryptPassword, salesDiscount,currentUser.getUserId(),provinceId,cityId,areaId);

            //查询
            User mpUser = userService.findUserBySupplierId(Long.parseLong(rcodetxt));

            //查询该商户与MP之间是否已经关联
            if(mpUser!=null){
                ShareRelationship shareRelationship = new ShareRelationship();
                shareRelationship.setUserId(mpUser.getUserId());
                shareRelationship.setToUserId(supplierId);
                shareRelationship.setShareType(2);
                List<Supplier> supplier = userService.findTjPop(mpUser.getUserId(),supplierId, 2);
                if(supplier.size()<1){
                    userService.tjPop(shareRelationship);
                }
            }

            userService.BizRcodeBindSupplier(rcid,supplierId);
            // 更新在线用户的商家信息
            currentUser.setType(12);
            currentUser.setSupplierId(Long.toString(supplierId));
            String sid = getUUID.getSessionId(request, response);
            CookieTool.setCookie(response, CommonConstant.SESSION_ID, sid, CommonConstant.MAX_AGE_ONE_MONTH);
            memcachedClient.setOpTimeout(5000L);
            memcachedClient.set(CommonConstant.CUSTOMER_LOGIN + sid, CommonConstant.MEMCACHEDAGE,currentUser);
            result.setCode(0);
        }catch (Exception e){
            logger.error(e);
            result.setMessage("未知错误,请稍后再试");
            result.setCode(1);
        }
        return result;
	}
	/**
	 * 注册成功重定向到此页面
	 */
	@RequestMapping("/toSucced")
	public String toSucced(){
		return "/qrcode/toSucced";
	}
	/**
	 * 注册成功重定向到此页面
	 */
	@RequestMapping("/toFailure")
	public String toFailure(Model model,String message){
		model.addAttribute("message", message);
		return "/qrcode/toFailure";
	}
	/**
	 * 单张图片上传
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
	public String regisUploadFile(@RequestParam(required=false)MultipartFile businessLicense, @RequestParam(required=false)MultipartFile companyStoreLogo, @RequestParam(required=false)MultipartFile idCardFront,
			 @RequestParam(required=false)MultipartFile idCardVerso, @RequestParam(required=false)MultipartFile bankAccountPic) throws IOException, MyException{
		String url="";
		if(businessLicense!=null&&!"".equals(businessLicense)){
			 url = UploadFileUtil.uploadFile(businessLicense.getInputStream(),CommonUtil.getFileExt2(businessLicense.getOriginalFilename()+".jpg"), null);
		}
		if(companyStoreLogo!=null&&!"".equals(companyStoreLogo)){
			 url = UploadFileUtil.uploadFile(companyStoreLogo.getInputStream(),CommonUtil.getFileExt2(companyStoreLogo.getOriginalFilename()+".jpg"), null);
		}
		if(idCardFront!=null&&!"".equals(idCardFront)){
			 url = UploadFileUtil.uploadFile(idCardFront.getInputStream(),CommonUtil.getFileExt2(idCardFront.getOriginalFilename()+".jpg"), null);
		}
		if(idCardVerso!=null&&!"".equals(idCardVerso)){
			 url = UploadFileUtil.uploadFile(idCardVerso.getInputStream(),CommonUtil.getFileExt2(idCardVerso.getOriginalFilename()+".jpg"), null);
		}
		if(bankAccountPic!=null&&!"".equals(bankAccountPic)){
			url = UploadFileUtil.uploadFile(bankAccountPic.getInputStream(),CommonUtil.getFileExt2(bankAccountPic.getOriginalFilename()+".jpg"), null);
		}
		return url;
	}
}
