package com.mall.controller.user;

import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.controller.base.BaseController;
import com.mall.customer.order.api.rpc.CustomerOrderService;
import com.mall.customer.order.po.Order;
//import com.mall.customer.order.utils.EasyUIPageOrder;
import com.mall.customer.order.utils.JsonUtils;
import com.mall.dealer.product.api.DealerProductTagsService;
import com.mall.dealer.product.po.BuyLimit;
import com.mall.dealer.product.po.DictTags;
import com.mall.dealer.product.util.EasyUIPage;
//import com.mall.dealer.product.util.EasyUIPage;
//import com.mall.dealer.product.util.EasyUIPage;
import com.mall.mybatis.utility.PageBean;
import com.mall.platform.model.PlatformComplaint;
import com.mall.platform.proxy.RemoteServiceSingleton;
//import com.mall.retailer.model.Retailer;
import com.mall.supplier.enums.EasyUIPageSupplier;
import com.mall.supplier.model.EasyUIPageOrder;
import com.mall.supplier.model.Supplier;
import com.mall.supplier.model.Suppliernew;
import com.mall.supplier.service.SupplierManagerService;
import com.mall.supplier.service.SupplierUserManagerService;
import com.mall.utils.Common;
import com.mall.utils.Constants;

/**
 * 零售商反馈.
 * @author zhouzb
 *
 */
@Controller
@RequestMapping(value="/complaint_retailer")
public class PlatformToRetailerComplaintController extends BaseController{
	
	/**
	 * LOGGER.
	 */
	private static final Log LOGGER = LogFactory.getLogger(PlatformController.class);
	@Autowired
	private DealerProductTagsService dealerProductTagsService;
	@Autowired
	private SupplierUserManagerService supplierUserManagerService;
	@Autowired
	private CustomerOrderService customerOrderService;
	/**
	 * 绑定时间转换.
	 * @param binder WebDataBinder
	 */
	@InitBinder
	//此方法用于日期的转换
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	/**.
	 * 跳转页面
	 * @return URL
	 */
	@RequestMapping(value="/retailer",method=RequestMethod.GET)
	public String getRetailerPage(Model model, @RequestParam(required = false) String labelPage, @RequestParam(required = false) Integer page){
		if(labelPage == null){
			labelPage = "1";
		}
		if(page == null){
			page = 1;
		}
		model.addAttribute("labelPage",labelPage);
		model.addAttribute("page",page);
		return "/user/complaint_retailer";
	}
	/**.
	 * 跳转页面tags
	 * @return URL
	 */
	@RequestMapping(value="/pagetags",method=RequestMethod.GET)
	public String pagetags(Model model, @RequestParam(required = false) String labelPage, @RequestParam(required = false) Integer page){
		
		List<DictTags> list = dealerProductTagsService.findTagsByType(2);
		
		if(labelPage == null){
			labelPage = "1";
		}
		if(page == null){
			page = 1;
		}
		
		model.addAttribute("list",list);
		model.addAttribute("labelPage",labelPage);
		model.addAttribute("page",page);
		return "/user/buyLimitTags";
	}
	/**.
	 * 跳转页面tags
	 * @return URL
	 */
	@RequestMapping(value="/selecttags",method=RequestMethod.GET)
	public String selecttags(Model model, @RequestParam(required = false) String labelPage, @RequestParam(required = false) Integer page){
		
		List<DictTags> list = dealerProductTagsService.findTagsByType(2);
		
		if(labelPage == null){
			labelPage = "1";
		}
		if(page == null){
			page = 1;
		}
		
		model.addAttribute("list",list);
		model.addAttribute("labelPage",labelPage);
		model.addAttribute("page",page);
		return "/user/complaint_selecttags";
	}
	@RequestMapping(value="/selectSupplier",method=RequestMethod.GET)
	public String selectSupplier(Model model, @RequestParam(required = false) String labelPage, @RequestParam(required = false) Integer page){
		
		List<DictTags> list = dealerProductTagsService.findTagsByType(2);
		
		if(labelPage == null){
			labelPage = "1";
		}
		if(page == null){
			page = 1;
		}
		
		model.addAttribute("list",list);
		model.addAttribute("labelPage",labelPage);
		model.addAttribute("page",page);
		return "/user/complaint_selectSupplier";
	}
	@RequestMapping(value="/selectSupplier1",method=RequestMethod.GET)
	public String selectSupplier1(Model model, @RequestParam(required = false) String labelPage, @RequestParam(required = false) Integer page){
		
		model.addAttribute("labelPage",labelPage);
		model.addAttribute("page",page);
		return "/user/complaint_supplier1";
	}
	@RequestMapping("/querytags")
	public @ResponseBody String queryCard(DictTags dictTags,Model model,
			@RequestParam(defaultValue="1") Integer page,
			@RequestParam(defaultValue="30") Integer rows){
		EasyUIPage easyUIPage =dealerProductTagsService.querytags(dictTags,page,rows);
		String jString = JsonUtils.ObjectAsString(easyUIPage);
		return jString;
	}
	@RequestMapping("/querySupplier")
	public @ResponseBody String querySupplier(Suppliernew supplier,Model model,
			@RequestParam(defaultValue="1") Integer page,
			@RequestParam(defaultValue="30") Integer rows){
		Integer startpage=(page-1)*rows;
		EasyUIPageSupplier easyUIPage =supplierUserManagerService.querySupplier(supplier,startpage,rows);
		String jString = JsonUtils.ObjectAsString(easyUIPage);
		return jString;
	}
	@RequestMapping("/querySuppliernew")
	public @ResponseBody String querySuppliernew(String companyQy,String startTime,String endTime,Model model,
			@RequestParam(defaultValue="1") Integer page,
			@RequestParam(defaultValue="30") Integer rows){
		EasyUIPageOrder easyUIPageOrder = new EasyUIPageOrder();
		easyUIPageOrder.setCompanyQy(companyQy);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
		if (Common.isEmpty(startTime)&&Common.isEmpty(endTime)) {
			Date d = new Date(); 
			long longtime = d.getTime();
			endTime=dateFormat.format(d);
			String iString="2592000000";
			Long long1 = Long.valueOf(iString);
			long startdate=longtime-long1;
			startTime=dateFormat.format(startdate);
		}
		try {
			if (startTime!=null) {
				Date startTime1 = dateFormat.parse(startTime);
				easyUIPageOrder.setStartTime(startTime1);
			}
			if (endTime!=null) {
				Date endTime1 = dateFormat.parse(endTime);
				easyUIPageOrder.setEndTime(endTime1);
			}
			PageBean<Order> pageBean = new PageBean<Order>();
			pageBean.setParameter(easyUIPageOrder);
			pageBean.setPage(page);
			pageBean.setPageSize(rows);
			PageBean<Order> pageBean2 = customerOrderService.querySuppliernew(pageBean);
			List<Order> list = pageBean2.getResult();
			List<Supplier> list2=new ArrayList<Supplier>();
			for (Order order : list) {
				SupplierManagerService supplierManagerService = RemoteServiceSingleton.getInstance().
						getSupplierManagerService();
				Supplier supplier = supplierManagerService.findSupplier(Long.parseLong(order.getMerchantid()));
				supplier.setPriceTotal(order.getPriceTotal());
				list2.add(supplier);
			}
			EasyUIPageOrder easyUIPage = new EasyUIPageOrder();
			Long totalCount = pageBean2.getTotalCount();
			easyUIPage.setTotal(totalCount.intValue());
			easyUIPage.setRows(list2);
			String jString = JsonUtils.ObjectAsString(easyUIPage);
			return jString;
			
		} catch (Exception e) {
			return Constants.ERROR;
		}
	}
	@RequestMapping("/updateSupplier")
	public  String updateSupplier(String ids) {
		String[] split = ids.split(",");
		for (String string : split) {
			supplierUserManagerService.updateSupplier(string);
		}
		return "redirect:/complaint_retailer/selectSupplier";
	}
	@RequestMapping("/frozenSupplier")
	public  String frozenSupplier(String ids) {
		String[] split = ids.split(",");
		for (String string : split) {
			supplierUserManagerService.frozenSupplier(string);
		}
		return "redirect:/complaint_retailer/selectSupplier";
	}
	@RequestMapping("/deletetags")
	public  String deletetags(String ids,HttpServletRequest request
			){
		if (ids!=null) {
			String[] split = ids.split(",");
			for (String string : split) {
				dealerProductTagsService.deletetags(string);
			}
		}
		return "redirect:/complaint_retailer/selecttags";
	}
	@RequestMapping("/selectbyid")
	@ResponseBody
	public  DictTags selectbyid(String id,HttpServletRequest request
			){
		if (id!=null) {
			DictTags dictTags=dealerProductTagsService.selectbyid(id);
			return dictTags;
			}
		return null;
		
	}
	@RequestMapping("/updatetag")
	public  String updatetag(DictTags dictTags,HttpServletRequest request
			){
			dealerProductTagsService.updatetag(dictTags);
			return "redirect:/complaint_retailer/selecttags";
		
	}
	@RequestMapping("/addtag")
	public  String addtag(DictTags dictTags,HttpServletRequest request
			){
		dealerProductTagsService.addtag(dictTags);
		return "redirect:/complaint_retailer/selecttags";
		
	}
	/**
	 * 保存限购标签属性
	 */
	@RequestMapping(value="/xgSubmit",method=RequestMethod.POST)
	public String xgsubmit(
			BuyLimit buyLimit,HttpServletRequest request){
			dealerProductTagsService.updateXg(buyLimit);
			return "redirect:/complaint_retailer/pagetags";
	}
	@RequestMapping(value="/xgselect",method=RequestMethod.GET)
	@ResponseBody
	public BuyLimit xgselect(String tagCode){
			BuyLimit limit = dealerProductTagsService.xgselect(tagCode);
			return limit;
	}
	@RequestMapping(value="/xgdeleted",method=RequestMethod.GET)
	@ResponseBody
	public String xgdeleted(String tagCode){
		try {
			BuyLimit buyLimit = new BuyLimit();
			buyLimit.setTagCode(tagCode);
			System.out.println(tagCode);
			dealerProductTagsService.updateXg(buyLimit);
			return Constants.SUCCESS;
			
		} catch (Exception e) {
			return Constants.ERROR;
		}
	}
	/**
	 * 查询零售商反馈信息.
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param labelPage String标签页
	 * @param page Integer page页
	 * @return null
	 */
	@RequestMapping(value="/retailer",method=RequestMethod.POST)
	public String getRetailerLabelPage(HttpServletRequest request,HttpServletResponse response
			,PlatformComplaint platformComplaint,String labelPage,Integer page){
		
		
		PageBean<PlatformComplaint> pageBean = new PageBean<PlatformComplaint>();
		PageBean<PlatformComplaint> findComplaintByReferenceIdPage = new PageBean<PlatformComplaint>();

		if((labelPage==null)||labelPage.equals("")){
			labelPage = "1";
		}

		//referenceId：(零售商Id或经销商Id或供应商Id); complaintSource： 1代表零售商,2代表经销商,3代表供应商;status： 0代表未处理,1代表已处理
		if(labelPage!=null&&!labelPage.equals("")){
			
			if(labelPage.equals("1")){
				
				LOGGER.info(" -----> IN Retailer Complaint Untreated Page ! ");
				platformComplaint.setReferenceId(null);
				platformComplaint.setComplaintSource(3);
				platformComplaint.setStatus(0);
				
			}
			
			if(labelPage.equals("2")){
				
				LOGGER.info(" -----> IN Retailer Complaint Processed Page ! ");
				platformComplaint.setReferenceId(null);
				platformComplaint.setComplaintSource(3);
				platformComplaint.setStatus(1);
				
			}
			
		}
		
//		if(platformComplaint.getReferenceName() != null && !platformComplaint.getReferenceName().equals("")){
//			
//			List<Long> dealerIds = RemoteServiceSingleton.getInstance().getRetailerManagerService().
//					getRetailerIdsByName(platformComplaint.getReferenceName());
//			platformComplaint.setReferenceIds(dealerIds);
//			
//		}
		
		if(platformComplaint.getComplaintType()!=null&&platformComplaint.getComplaintType()==-1){
			platformComplaint.setComplaintType(null);
		}
		if(platformComplaint.getComplaintLevel()!=null&&platformComplaint.getComplaintLevel()==-1){
			platformComplaint.setComplaintLevel(null);
		}
		
		if(page!=null&&page!=Constants.NUM0){
			pageBean.setPage(page);
		}else{
			pageBean.setPage(Constants.NUM1);
		}
		
		pageBean.setPageSize(Constants.PAGESIZE);
		pageBean.setParameter(platformComplaint);

		
		try {
			
			LOGGER.info(" -----> IN Retailer Complaint To User getPlatformComplaintService() ! ");
			
			findComplaintByReferenceIdPage = RemoteServiceSingleton.getInstance().
						getPlatformComplaintService().findComplaintByReferenceIdPage(pageBean);
		
		} catch (Exception e) {
		
			LOGGER.error(" -----> IN Retailer Complaint Break Down A Error . Error Info : "+e.getMessage());
			LOGGER.error(" -----> IN Retailer Complaint Break Down A Error . Error Info : "+e);
		
		}
		
		if(findComplaintByReferenceIdPage!=null){
			
			List<PlatformComplaint> result = findComplaintByReferenceIdPage.getResult();
			
			if(null != result){
				
				for (int i = 0; i < result.size(); i++) {
					
					PlatformComplaint complaint = result.get(i);
					
					Long referenceId = complaint.getReferenceId();
					
					if(null != complaint.getReferenceId()){
//						Retailer retailer = RemoteServiceSingleton.getInstance().
//								getRetailerManagerService().findByRetailerId(referenceId);
//						
//						if(null != retailer){
//							
//							complaint.setReferenceName(retailer.getName());
//						
//						}
					}
					
				}
			}
			
			request.getSession().setAttribute("pb",findComplaintByReferenceIdPage);
		}
		if(labelPage!=null&&!labelPage.equals("")){
			try {
				if(labelPage.equals("1")){
					
					request.getRequestDispatcher(
							"/WEB-INF/views/user/modelPage/complant_retailer_waitmodel.jsp").forward(request, response);
				
				}
				if(labelPage.equals("2")){
				
					request.getRequestDispatcher(
							"/WEB-INF/views/user/modelPage/complant_retailer_readaymodel.jsp")
							.forward(request, response);
				}
			} catch (Exception e) {
				
				LOGGER.error("商户Id:"+getCurrentPlatformId());
				LOGGER.error("用户:"+getCurrentUser().getUsername());
				LOGGER.error("用户ID:"+getCurrentUser().getId());
				LOGGER.error(e.getMessage()+e);

			}
		}
		
		return null;
		
	}
	
	
	/**.
	 * 获取反馈详情，--查看---修改--通用．
	 * @param request HttpServletRequest.
	 * @param response HttpServletResponse.
	 * @param platformComplaint PlatformComplaint.
	 * @param statuForUse String.
	 * @return null
	 */
	@RequestMapping(value="/getComplaint",method=RequestMethod.POST)
	public String getReplyComplaintToEditOrShow(HttpServletRequest request,HttpServletResponse response,
			PlatformComplaint platformComplaint,String statuForUse){
		
		try {
			
			Long complaintId = platformComplaint.getComplaintId();
			
			PlatformComplaint findByComplaintId = RemoteServiceSingleton.getInstance()
					.getPlatformComplaintService().findByComplaintId(complaintId);
			
			if(findByComplaintId!=null){
				
				Long referenceId = findByComplaintId.getReferenceId();
				
//				Retailer retailer  = new Retailer();
				
				if(null != referenceId){

					//retailer = RemoteServiceSingleton.getInstance().getRetailerManagerService().findByRetailerId(referenceId);
				
				}
				
				findByComplaintId.setReferenceName(findByComplaintId.getComplaintBy());
				
				request.getSession().setAttribute("complaint",findByComplaintId);
				
				request.getSession().setAttribute("type","retailer");
				
			}
			
			
			
			if(findByComplaintId!=null){
				request.getSession().setAttribute("complaint",findByComplaintId);
			}
			
			if(statuForUse!=null&&!statuForUse.equals("")){
				if(statuForUse.equals("1")){
					
					LOGGER.info(" -----> IN Retailer Complaint To get complaint_info_edit.jsp Page ! ");
					
					request.getRequestDispatcher(
							"/WEB-INF/views/user/modelPage/complaint_info_edit.jsp")
							.forward(request, response);
				}
				if(statuForUse.equals("0")){
					
					LOGGER.info(" -----> IN Retailer Complaint To get complaint_info_show.jsp Page ! ");
					
					request.getRequestDispatcher(
							"/WEB-INF/views/user/modelPage/complaint_info_show.jsp")
							.forward(request, response);
				}
			}
		
		} catch (Exception e) {
			
			LOGGER.error("商户Id:"+getCurrentPlatformId());
			LOGGER.error("用户:"+getCurrentUser().getUsername());
			LOGGER.error("用户ID:"+getCurrentUser().getId());
			LOGGER.error(e.getMessage()+e);
		}
		
		
		return null;
	}
	
	/**.
	 * 保存修改促销信息.
	 * @param platformComplaint 拼装返回字符串
	 * @return 状态码,1成功  0catch错误
	 */
	@RequestMapping(value="/replyComplaint",method=RequestMethod.POST)
	@ResponseBody
	public String saveEplyComplaint(PlatformComplaint platformComplaint){
		try {
			
			LOGGER.info(" -----> " +
					"IN Retailer Complaint To Save ReplyComplaint!  User service is updatePlatformComplaint");
			
			platformComplaint.setStatus(1);
			platformComplaint.setReplyTime(new Date());
			platformComplaint.setReplyBy(getCurrentUser().getUsername());
			
			RemoteServiceSingleton.getInstance()
				.getPlatformComplaintService().updatePlatformComplaint(platformComplaint);
			return "1";
	
		}catch (Exception e) {
			
			LOGGER.error("商户Id:"+getCurrentPlatformId());
			LOGGER.error("用户:"+getCurrentUser().getUsername());
			LOGGER.error("用户ID:"+getCurrentUser().getId());
			LOGGER.error(e.getMessage()+e);
			return "0";
		}
	}
	

	/**
	 * 导出Excel.
	 * @param request
	 * @param response
	 * @param page
	 * @param orderPayItemVo
	 * @return
	 */
	@RequestMapping(value="/downRetailerExcel")
	public String downRetailerComplaintByCondition(HttpServletRequest request,HttpServletResponse response,
			PlatformComplaint platformComplaint,String labelPage){

		List<PlatformComplaint> resaultComplaintPage = new ArrayList<PlatformComplaint>();

		try{

			//referenceId：(零售商Id或经销商Id或供应商Id); complaintSource： 1代表零售商,2代表经销商,3代表供应商;status： 0代表未处理,1代表已处理
			if(labelPage!=null&&!labelPage.equals("")){

				if(labelPage.equals("1")){

					LOGGER.info(" -----> IN Retailer Complaint Untreated Page ! ");
					platformComplaint.setReferenceId(null);
					platformComplaint.setComplaintSource(3);
					platformComplaint.setStatus(0);

				}

				if(labelPage.equals("2")){

					LOGGER.info(" -----> IN Retailer Complaint Processed Page ! ");
					platformComplaint.setReferenceId(null);
					platformComplaint.setComplaintSource(3);
					platformComplaint.setStatus(1);

				}

			}
			if(platformComplaint.getComplaintType()!=null&&platformComplaint.getComplaintType()==-1){
				platformComplaint.setComplaintType(null);
			}
			if(platformComplaint.getComplaintLevel()!=null&&platformComplaint.getComplaintLevel()==-1){
				platformComplaint.setComplaintLevel(null);
			}

			resaultComplaintPage = 
					RemoteServiceSingleton.getInstance()
					.getPlatformComplaintService().downloadPlatformComplaintByConditions(platformComplaint);
			
			HSSFWorkbook book = new HSSFWorkbook();
			
			if(resaultComplaintPage!=null&&resaultComplaintPage.size()>0){
				
				Date date = new Date();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DEFAULTFORMATDATE);
				String format = simpleDateFormat.format(date);
				
				HSSFSheet sheet = book.createSheet(format+"零售商问题反馈");
				
				
				sheet.setColumnWidth(Constants.HSSFSHEETROW_0, Constants.HSSFSHEETROW_150_WIDTH);
				sheet.setColumnWidth(Constants.HSSFSHEETROW_1, Constants.HSSFSHEETROW_150_WIDTH);
				sheet.setColumnWidth(Constants.HSSFSHEETROW_2, Constants.HSSFSHEETROW_150_WIDTH);
				sheet.setColumnWidth(Constants.HSSFSHEETROW_3, Constants.HSSFSHEETROW_300_WIDTH );
			    sheet.setColumnWidth(Constants.HSSFSHEETROW_4, Constants.HSSFSHEETROW_150_WIDTH);
			    sheet.setColumnWidth(Constants.HSSFSHEETROW_5, Constants.HSSFSHEETROW_120_WIDTH );
			    sheet.setColumnWidth(Constants.HSSFSHEETROW_6, Constants.HSSFSHEETROW_150_WIDTH);
			    sheet.setColumnWidth(Constants.HSSFSHEETROW_7, Constants.HSSFSHEETROW_300_WIDTH );
			    sheet.setColumnWidth(Constants.HSSFSHEETROW_8, Constants.HSSFSHEETROW_120_WIDTH );
				
				HSSFRow row = sheet.createRow((int) 0);
				HSSFCellStyle style = book.createCellStyle();
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
				
				HSSFCell cell=null;
				String [] strtitle={"商户名称","用户名称","时间","反馈内容","反馈类型","反馈级别","回复人员","回复内容","回复日期"};
				for(int i=0;i<strtitle.length;i++){
					cell=row.createCell(i);
					cell.setCellValue(strtitle[i]);
					cell.setCellStyle(style);			
				}
				
				if(resaultComplaintPage!=null&&resaultComplaintPage.size()>0){
					
					for(int i = 0 ; i < resaultComplaintPage.size() ; i++){
						
						row = sheet.createRow((int) i + 1);
						
						PlatformComplaint complaint = resaultComplaintPage.get(i);
						
						String referenceName = "";
						if(null != complaint.getReferenceId()){
//							Retailer retailer = RemoteServiceSingleton.getInstance().getRetailerManagerService().findByRetailerId(complaint.getReferenceId());
							/*if(null != retailer){
								referenceName = retailer.getName();
							}*/
						}
						
						String complaintBy = "";
						
						if(complaint.getComplaintContent()!=null){
							complaintBy = complaint.getComplaintBy();
						}
						
						Date complaintTime = complaint.getComplaintTime();
						String simComplaintTime = "";
						if(complaintTime!=null){
							simComplaintTime = simpleDateFormat.format(complaintTime);
						}
						String complaintContent = "";
						
						if(complaint.getComplaintContent()!=null){
							complaintContent = complaint.getComplaintContent();
						}
						
						Integer complaintType = complaint.getComplaintType();
						String simComplaintType = "";
						if(complaintType==null){
							complaintType = Constants.INT0;
						}
						switch (complaintType) {
						case Constants.INT0:
							simComplaintType = "个人意见";
							break;
						case Constants.INT1:
							simComplaintType = "商品相关";
							break;
						case Constants.INT2:
							simComplaintType = "系统问题 ";
							break;
						case Constants.INT3:
							simComplaintType = "订单相关";
							break;
						case Constants.INT4:
							simComplaintType = "退货相关";
							break;
						case Constants.INT5:
							simComplaintType = "服务相关";
							break;
						case Constants.INT6:
							simComplaintType = "管理相关";
							break;

						default:
							simComplaintType = "未检测到反馈类型";
							break;
						}
						Integer complaintLevel = complaint.getComplaintLevel();
						String simComplaintLevel = "";
						if(complaintLevel==null){
							complaintLevel = Constants.INT0;
						}
						switch (complaintLevel) {
						case Constants.INT0:
							simComplaintLevel = "普通";
							break;
						case Constants.INT1:
							simComplaintLevel = "一般";
							break;
						case Constants.INT2:
							simComplaintLevel = "紧急 ";
							break;
						default:
							simComplaintLevel = "未检测到反馈级别";
							break;
						}
						String replyBy = "";
						
						if(complaint.getReplyBy()!=null){
							replyBy = complaint.getReplyBy();
						}
						
						String replyContent = "";
						
						if(complaint.getReplyContent()!=null){
							replyContent = complaint.getReplyContent();
						}
						
						Date replyTime = complaint.getReplyTime();
						String simReplyTime="";
						if(replyTime!=null){
							simReplyTime = simpleDateFormat.format(replyTime);
						}
						
						row.createCell(Constants.SHORT0).setCellValue(referenceName);
						row.createCell(Constants.SHORT1).setCellValue(complaintBy);
						row.createCell(Constants.SHORT2).setCellValue(simComplaintTime);
						row.createCell(Constants.SHORT3).setCellValue(complaintContent);
						row.createCell(Constants.SHORT4).setCellValue(simComplaintType);
						row.createCell(Constants.SHORT5).setCellValue(simComplaintLevel);
						row.createCell(Constants.SHORT6).setCellValue(replyBy);
						row.createCell(Constants.SHORT7).setCellValue(replyContent);
						row.createCell(Constants.SHORT8).setCellValue(simReplyTime);
						
					}
					
					LOGGER.info("拼装账务信息完成!");
					
					String filename = "WOFE-retailer-feedback-information";
					
					OutputStream os = response.getOutputStream();
					
					response.reset();
					
					response.setCharacterEncoding("UTF-8");
					
					filename = java.net.URLEncoder.encode(filename,"gb2312");
					
					response.setHeader("Content-Disposition","attachment;filename="+
							new String(filename.getBytes("UTF-8"),"GBK")+".xls");
					
					response.setContentType("application/msexcel");//定义输出类型	
					
					book.write(os);  	
					
					response.getOutputStream().flush();
					response.getOutputStream().close();	
					
					}
					
				}
			
		} catch (Exception e) {
			LOGGER.error("商户Id:"+getCurrentPlatformId());
			LOGGER.error("用户:"+getCurrentUser().getUsername());
			LOGGER.error("用户ID:"+getCurrentUser().getId());
			LOGGER.error(" -----> IN Retailer Complaint Break Down A Error . Error Info : "+e.getMessage(),e);
			
		}
		return null;
	}
	
}
