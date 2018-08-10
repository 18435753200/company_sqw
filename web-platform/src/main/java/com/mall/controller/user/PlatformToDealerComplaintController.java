/*package com.mall.controller.user;


import java.io.IOException;
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
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.dealer.model.Dealer;
import com.mall.mybatis.utility.PageBean;
import com.mall.platform.model.PlatformComplaint;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;

*//**
 * 
 * @author zhouzb
 *
 *//*
@Controller
@RequestMapping(value="/complaint_dealer")
public class PlatformToDealerComplaintController extends BaseController{
	
	*//**
	 * LOGGER.
	 *//*
	private static final Log LOGGER = LogFactory.getLogger(PlatformToDealerComplaintController.class);

	*//**.
	 * 此方法用于日期的转换
	 * @param binder WebDataBinder
	 *//* 
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	
	@RequestMapping(value="/dealer",method=RequestMethod.GET)
	public String getDealerPage(){
		LOGGER.info(" -----> To Dealer Complaint Page ! ");
		return "/user/complaint_dealer";
	}
	
	
	*//**
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param labelPage String标签页状态码
	 * @param page Integer
	 * @return null
	 *//*
	@RequestMapping(value="/dealer",method=RequestMethod.POST)
	public String getDealerLabelPage(HttpServletRequest request,HttpServletResponse response
			,PlatformComplaint platformComplaint,String labelPage,Integer page){
		
		PageBean<PlatformComplaint> pageBean = new PageBean<PlatformComplaint>();
		PageBean<PlatformComplaint> findComplaintByReferenceIdPage = new PageBean<PlatformComplaint>();

		try{
			//referenceId：(零售商Id或经销商Id或供应商Id); complaintSource： 1代表零售商,2代表经销商,3代表供应商;status： 0代表未处理,1代表已处理
			if(labelPage!=null&&!labelPage.equals("")){
				if(labelPage.equals("1")){
					
					platformComplaint.setReferenceId(null);
					platformComplaint.setComplaintSource(Constants.NUM2);
					platformComplaint.setStatus(Constants.NUM0);
					LOGGER.info(" -----> IN Dealer Complaint Untreated Page ! ");
					
				}
				if(labelPage.equals("2")){
					
					platformComplaint.setReferenceId(null);
					platformComplaint.setComplaintSource(Constants.NUM2);
					platformComplaint.setStatus(Constants.NUM1);
					LOGGER.info(" -----> IN Dealer Complaint Processed Page ! ");
					
				}
			}

			if(platformComplaint.getReferenceName() != null && !platformComplaint.getReferenceName().equals("")){
				
				List<Long> dealerIds = RemoteServiceSingleton.getInstance().getDealerService().getDealerIdsByName(platformComplaint.getReferenceName());
				platformComplaint.setReferenceIds(dealerIds);
				
			}
	
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
				
				findComplaintByReferenceIdPage = RemoteServiceSingleton.getInstance().
						getPlatformComplaintService().findComplaintByReferenceIdPage(pageBean);
				
			} catch (Exception e) {
				
				LOGGER.error(" -----> IN Dealer Complaint Break Down A Error . Error Info : "+e.getMessage(),e);
				
			}

			if(findComplaintByReferenceIdPage!=null){
				
				List<PlatformComplaint> result = findComplaintByReferenceIdPage.getResult();
				
				if(null != result){
					for (int i = 0; i < result.size(); i++) {
						
						PlatformComplaint complaint = result.get(i);
						
						Long referenceId = complaint.getReferenceId();
						
						if(null != complaint.getReferenceId()){
							
							Dealer dealer = RemoteServiceSingleton.getInstance().getDealerService().findByDealerId(referenceId);
							
							if(null != dealer){
								complaint.setReferenceName(dealer.getCompanyName());
							}
						}
						
					}
				}
				request.getSession().setAttribute("pb",findComplaintByReferenceIdPage);
				
			}



			if(labelPage!=null&&!labelPage.equals("")){
				
				if(labelPage.equals("1")){
					request.getRequestDispatcher(
							"/WEB-INF/views/user/modelPage/complant_dealer_waitmodel.jsp")
							.forward(request, response);
				}
				
				if(labelPage.equals("2")){
					request.getRequestDispatcher(
							"/WEB-INF/views/user/modelPage/complant_dealer_readaymodel.jsp")
							.forward(request, response);
				}
				
			}
		}catch (Exception e) {
			
			LOGGER.error("经销商问题反馈 出错 msg:"+e.getMessage());
			LOGGER.error("商户Id:"+getCurrentPlatformId());
			LOGGER.error("用户:"+getCurrentUser().getUsername());
			LOGGER.error("用户ID:"+getCurrentUser().getId());
			LOGGER.error(e.getMessage(),e);
			
		}
		return null;
	}
	
	*//**
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param platformComplaint PlatformComplaint
	 * @param statuForUse String
	 * @return null
	 *//*
	@RequestMapping(value="/getReplyComplaint",method=RequestMethod.POST)
	public String getReplyComplaintToEditOrShow(HttpServletRequest request,HttpServletResponse response,
			PlatformComplaint platformComplaint,String statuForUse){
		
		try {
			
			Long complaintId = platformComplaint.getComplaintId();
			
			PlatformComplaint findByComplaintId = RemoteServiceSingleton.getInstance().
					getPlatformComplaintService().findByComplaintId(complaintId);
			
			if(findByComplaintId!=null){
				
				Long referenceId = findByComplaintId.getReferenceId();
				
				Dealer dealer = new Dealer();
				
				if(null != referenceId){

					dealer = RemoteServiceSingleton.getInstance().getDealerService().findByDealerId(referenceId);
				
				}
				
				findByComplaintId.setReferenceName(dealer.getCompanyName());
				
				request.getSession().setAttribute("complaint",findByComplaintId);
				
				request.getSession().setAttribute("type","dealer");
			}
			
			if(statuForUse!=null&&!statuForUse.equals("")){
				
				if(statuForUse.equals("1")){
					request.getRequestDispatcher(
							"/WEB-INF/views/user/modelPage/complaint_info_edit.jsp").forward(request, response);
				}
				
				if(statuForUse.equals("0")){
					request.getRequestDispatcher(
							"/WEB-INF/views/user/modelPage/complaint_info_show.jsp").forward(request, response);
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

	*//**.
	 * 回复
	 * @param platformComplaint 返回对象.
	 * @return String
	 *//*
	@RequestMapping(value = "/replyComplaint", method = RequestMethod.POST)
	@ResponseBody
	public String saveEplyComplaint(PlatformComplaint platformComplaint){
		
		try {
			platformComplaint.setStatus(1);
			platformComplaint.setReplyTime(new Date());
			platformComplaint.setReplyBy(getCurrentUser().getUsername());
			
			RemoteServiceSingleton.getInstance().getPlatformComplaintService()
				.updatePlatformComplaint(platformComplaint);
			
			return "1";
			
		} catch (Exception e) {
			LOGGER.error("商户Id:"+getCurrentPlatformId());
			LOGGER.error("用户:"+getCurrentUser().getUsername());
			LOGGER.error("用户ID:"+getCurrentUser().getId());
			LOGGER.error(e.getMessage(),e);
			return "0";
		}
	}
	
	
	*//**
	 * 导出Excel.
	 * @param request
	 * @param response
	 * @param page
	 * @param orderPayItemVo
	 * @return
	 *//*
	@RequestMapping(value="/downDealerExcel")
	public String downDealerComplaintByCondition(HttpServletRequest request,HttpServletResponse response,
			PlatformComplaint platformComplaint,String labelPage){

		List<PlatformComplaint> resaultComplaintPage = new ArrayList<PlatformComplaint>();

		try{

			//referenceId：(零售商Id或经销商Id或供应商Id); complaintSource： 1代表零售商,2代表经销商,3代表供应商;status： 0代表未处理,1代表已处理
			if(labelPage!=null&&!labelPage.equals("")){

				if(labelPage.equals("1")){
					
					platformComplaint.setReferenceId(null);
					platformComplaint.setComplaintSource(Constants.NUM2);
					platformComplaint.setStatus(Constants.NUM0);
					LOGGER.info(" -----> IN Dealer Complaint Untreated Page ! ");
					
				}
				if(labelPage.equals("2")){
					
					platformComplaint.setReferenceId(null);
					platformComplaint.setComplaintSource(Constants.NUM2);
					platformComplaint.setStatus(Constants.NUM1);
					LOGGER.info(" -----> IN Dealer Complaint Processed Page ! ");
					
				}
				
			}
			
			if(platformComplaint.getReferenceName() != null){
				
				List<Long> dealerIds = RemoteServiceSingleton.getInstance().getDealerService().getDealerIdsByName(platformComplaint.getReferenceName());
				platformComplaint.setReferenceIds(dealerIds);
				
			}
			
			
			if(platformComplaint.getComplaintType()!=null&&platformComplaint.getComplaintType()==-1){
				platformComplaint.setComplaintType(null);
			}
			if(platformComplaint.getComplaintLevel()!=null&&platformComplaint.getComplaintLevel()==-1){
				platformComplaint.setComplaintLevel(null);
			}

			resaultComplaintPage = RemoteServiceSingleton.getInstance().
					getPlatformComplaintService().downloadPlatformComplaintByConditions(platformComplaint);
			
			HSSFWorkbook book = new HSSFWorkbook();
			
			if(resaultComplaintPage!=null&&resaultComplaintPage.size()>0){
				
				Date date = new Date();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DEFAULTFORMATDATE);
				String format = simpleDateFormat.format(date);
				
				HSSFSheet sheet = book.createSheet(format+"经销商问题反馈");
				
				sheet.setColumnWidth(Constants.HSSFSHEETROW_0, Constants.HSSFSHEETROW_150_WIDTH);
				sheet.setColumnWidth(Constants.HSSFSHEETROW_0, Constants.HSSFSHEETROW_150_WIDTH);
				sheet.setColumnWidth(Constants.HSSFSHEETROW_1, Constants.HSSFSHEETROW_150_WIDTH);
				sheet.setColumnWidth(Constants.HSSFSHEETROW_2, Constants.HSSFSHEETROW_300_WIDTH );
			    sheet.setColumnWidth(Constants.HSSFSHEETROW_3, Constants.HSSFSHEETROW_150_WIDTH);
			    sheet.setColumnWidth(Constants.HSSFSHEETROW_4, Constants.HSSFSHEETROW_120_WIDTH );
			    sheet.setColumnWidth(Constants.HSSFSHEETROW_5, Constants.HSSFSHEETROW_150_WIDTH);
			    sheet.setColumnWidth(Constants.HSSFSHEETROW_6, Constants.HSSFSHEETROW_300_WIDTH );
			    sheet.setColumnWidth(Constants.HSSFSHEETROW_7, Constants.HSSFSHEETROW_120_WIDTH );
				
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
							Dealer dealer= RemoteServiceSingleton.getInstance().getDealerService().findByDealerId(complaint.getReferenceId());
							if(null != dealer){
								referenceName = dealer.getCompanyName();
							}
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
							simComplaintType = "未检测到反馈类型";
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
					String filename = "WOFE-dealer-feedback-information";
					
					OutputStream os = response.getOutputStream();
					
					response.reset();
					
					response.setCharacterEncoding("UTF-8");
					
					filename = java.net.URLEncoder.encode(filename,"gb2312");
					
					response.setHeader("Content-Disposition","attachment;filename="+
							new String(filename.getBytes("UTF-8"),"GBK")+".xls");
					
					response.setContentType("application/msexcel");//定义输出类型	
					
					book.write(os);  	
					
					
					}
					
				}
				
			
		} catch (Exception e) {
			
			LOGGER.error("商户Id:"+getCurrentPlatformId());
			LOGGER.error("用户:"+getCurrentUser().getUsername());
			LOGGER.error("用户ID:"+getCurrentUser().getId());
			LOGGER.error(e.getMessage(),e);
			
		}finally{
			
			try {
				response.getOutputStream().flush();
				response.getOutputStream().close();	
			} catch (IOException e) {
				LOGGER.error(e.getMessage(),e);
			}
			
		}
		return null;
	}
}
*/