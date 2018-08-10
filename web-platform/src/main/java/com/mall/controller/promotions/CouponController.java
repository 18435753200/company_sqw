/*package com.mall.controller.promotions;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.json.JSONObject;
import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.customer.model.User;
import com.mall.mybatis.utility.PageBean;
import com.mall.promotion.dto.coupon.UseRecordDto;
import com.mall.promotion.dto.coupon.UserCouponDTO;
import com.mall.promotion.po.coupon.Coupon;
//import com.mall.retailer.model.RetailerUser;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.CollectionUtils;
import com.mall.utils.Constants;

*//**
 * @author zlj 优惠券管理.
 * 
 *//*
@Controller
@RequestMapping(value = "/coupon")
public class CouponController extends BaseController {

    *//**
     * LOGGER
     *//*
    private static final Log LOGGER = LogFactory.getLogger(CouponController.class);

    private static final String SUC_FLAG = "success";
    private static final String FAIL_FLAG = "error";

    *//**
     * . 此方法用于日期的转换
     * 
     * @param binder
     *            WebDataBinder
     *//*
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    *//**
     * @return couponPage Path.
     *//*
    @RequestMapping(value = "/getCouponPage")
    public String getCouponPage() {

        return "/promotions/coupon/couponList";
    }

    *//**
     * 查询优惠券列表
     * 
     * @param request
     * @param response
     * @param page
     * @return
     *//*
    @RequestMapping(value = "/getCouponsByCon")
    public String getCouponsByCondition(HttpServletRequest request, HttpServletResponse response, Integer page,
            Coupon param, Integer coupontype,BigDecimal couponacount,Date starttime,Date endtime) {

        try {
            LOGGER.info("return Coupon Page!");
            PageBean<Coupon> pageBean = new PageBean<Coupon>();
            if (page != null && page != 0) {
                pageBean.setPage(page);
            } else {
                pageBean.setPage(1);
            }
            pageBean.setPageSize(Constants.PAGESIZE);
            if (coupontype != null) {
                param.setCoupontype(coupontype);
            } else {
                param.setCoupontype(0);
            }
            param.setCouponacount(couponacount);
            param.setStarttime(starttime);
            param.setEndtime(endtime);
            pageBean.setParameter(param);
            // 调用服务
            pageBean = RemoteServiceSingleton.getInstance().getCouponService().findCouponList(pageBean);
            request.getSession().setAttribute("pb", pageBean);
            return "/promotions/coupon/modelpage/coupons_model";           
        } catch (Exception e) {
            LOGGER.error("查询优惠券列表异常：" + e.getMessage(), e);
        }
        return null;
    }

    *//**
     * 创建优惠券信息
     * 
     * @param request
     *            HttpServletRequest.
     * @param promotionId
     *            Long.
     * @return
     *//*
    @RequestMapping(value = "/toCreateCoupon")
    public String toCouponEditPage(HttpServletRequest request) {
        LOGGER.info("to couponEdit Page");
        return "/promotions/coupon/couponEdit";
    }

    *//**
     * 新增优惠券
     * 
     * @param request
     * @return
     *//*
    @RequestMapping(value = "/createCoupon")
    @ResponseBody
    public String createCoupon(HttpServletRequest request, Coupon coupon) {
        String returnFlag = SUC_FLAG;
        try {
            // 校验页面元素
            returnFlag = checkCreateCouponInfo(coupon);
            if (SUC_FLAG.equals(returnFlag)) {
                RemoteServiceSingleton.getInstance().getCouponService().insertCoupon(coupon);
                LOGGER.info("创建了优惠券,名称为:"+coupon.getCouponname());
            }
        } catch (Exception e) {
            LOGGER.error("创建优惠券异常:" + e.getMessage(), e);
            returnFlag = FAIL_FLAG;
        }
        return returnFlag;

    }

    *//**
     * 校验页面填写元素是否符合规范
     * 
     * @param coupon
     * @return
     *//*
    private String checkCreateCouponInfo(Coupon coupon) {
        String msg = SUC_FLAG;

        // 校验名称
        if (StringUtils.isNotBlank(coupon.getCouponname())) {
            if (coupon.getCouponname().length() > 100) {
                return "优惠券名称长度不能大于100！";
            }
        } else {
            return "优惠券名称不能为空！";
        }
        // 校验金额
        if (coupon.getCouponacount() == null) {
            return "优惠券金额不能为空！";
        }
        if (coupon.getCouponacount() != null && coupon.getCouponacount().toString().length() > 18) {
            return "优惠券金额不能大于18位！";
        }
        if (coupon.getCouponacount() != null && coupon.getCouponacount().equals(new BigDecimal(0))) {
            return "优惠券金额不能为零！";
        }
        // 校验开始时间与结束时间
        if (coupon.getStarttime() == null) {
            return "开始时间不能为空！";
        }
        if (coupon.getStarttime() != null && coupon.getStarttime().compareTo(new Date()) == -1) {
            return "开始时间不能小于系统当前时间！";
        }
        if (coupon.getEndtime() == null) {
            return "结束时间不能为空！";
        }
        if (coupon.getEndtime() != null && coupon.getEndtime().compareTo(coupon.getStarttime()) <= 0) {
            return "结束时间应该大于开始时间！";
        }
        // 券用途长度校验
        if (StringUtils.isNotBlank(coupon.getCouponuse()) && coupon.getCouponuse().length() > 200) {
            return "券用途长度不能大于200字！";
        }
        // 描述长度校验
        if (StringUtils.isNotBlank(coupon.getCoupondesc()) && coupon.getCouponuse().length() > 200) {
            return "券用途长度不能大于200字！";
        }

        return msg;
    }

    *//**
     * 修改优惠券
     * 
     * @param request
     * @return
     *//*
    @RequestMapping(value = "/updateCoupon")
    public String updateCoupon(HttpServletRequest request) {
        LOGGER.info("to couponEdit Page");
        return "/promotions/coupon/couponEdit";

    }

    *//**
     * 查看优惠券
     * 
     * @param request
     * @return
     *//*
    @RequestMapping(value = "/toShowUI")
    public String toShowUI(Model model, Long couponId) {
        LOGGER.info("to view couponInfo page");
        try {
            Coupon coupon = RemoteServiceSingleton.getInstance().getCouponService().getCouponPoById(couponId);
            model.addAttribute("coupon", coupon);

        } catch (Exception e) {
            LOGGER.error("查询优惠券couponId=" + couponId.toString() + " 异常：" + e.getMessage(), e);
            e.printStackTrace();
        }
        return "/promotions/coupon/couponView";
    }
    
    
    
    *//**
     * 查询已发放券的列表
     * @return
     *//*
    @RequestMapping(value = "/getCouponRecord")
    public String getCouponRecord() {
    	//获取总的发放条数
    	
        return "/promotions/coupon/couponRecordList";
    }

    
    *//**
     * 查询已发放优惠券列表
     * 
     * @param request
     * @param response
     * @param page
     * @return
     *//*
    @RequestMapping(value = "/getCouponRecordsByCon")
    public String getCouponRecordsByCon(HttpServletRequest request, HttpServletResponse response, Integer page,Integer userType,
            Coupon param,Integer coupontype, Integer status,String username,BigDecimal couponacount,Date starttime,Date endtime) {

        try {
            LOGGER.info("return Coupon Page!");
            Long userid = null;
            if(StringUtils.isNotBlank(userIdTemp)){
            	try {
					userid = Long.parseLong(userIdTemp);
				} catch (Exception e) {
					userid = -1L;//随便给一个零售商不可能使用的值，保证查出来没有券记录
					LOGGER.error("查询优惠券发放记录列表异常,输入的零售商id数字转换异常：{}",e);
				}
            }
            LOGGER.info("mobile:"+username);
            if(StringUtils.isNotBlank(username)){
//            	RetailerUser rUser = RemoteServiceSingleton.getInstance().getRetailerUserManagerService().selectUserByName(username);
            	LOGGER.info("调用根据用户名查询零售商接口");
            	User user = new User();
            	user.setMobile(username);
            	user = RemoteServiceSingleton.getInstance().getUserService().findUser(user);
            	LOGGER.info("调用根据手机号查询C客户信息接口");
            	if(rUser != null){
            		userid = rUser.getRetailerId();
            	} if(user != null){
            		userid = user.getUserId();
            	}else{
            		userid = -1L;//随便给一个零售商不可能使用的值，保证查出来没有券记录
            	}
            }
            LOGGER.info("零售商id/C客户id:"+userid);
            PageBean<UserCouponDTO> pageBean = new PageBean<UserCouponDTO>();
            UserCouponDTO tempDto = new UserCouponDTO();
            tempDto.setUserid(userid);//零售商id
            tempDto.setStatus(status);
            tempDto.setCouponacount(couponacount);
            tempDto.setStarttime(starttime);
            tempDto.setEndtime(endtime);
            if(userType == 0){
            	tempDto.setUserType1(null);
            }else if(userType == 1 || userType == 2){
            	tempDto.setUserType1(userType);
            }else{
            	LOGGER.info("用户类型错误，请检查数据");
            }
            
            
            if (page != null && page != 0) {
                pageBean.setPage(page);
            } else {
                pageBean.setPage(1);
            }
            pageBean.setPageSize(Constants.PAGESIZE);
            
            pageBean.setParameter(tempDto);
            // 调用服务
            pageBean = RemoteServiceSingleton.getInstance().getCouponRuleService().getCouponRecords(pageBean);
            //总条数
            //Long totalCoupons = RemoteServiceSingleton.getInstance().getCouponService().getTotalCoupons(tempDto);
            //LOGGER.info("总条数totalCoupons:"+totalCoupons);
            //封装零售商店铺名称
            if(null != pageBean && pageBean.getResult() != null && pageBean.getResult().size()>0){
            	Set<Long> tempIds = new HashSet<Long>();
            	List<UserCouponDTO> tempList = pageBean.getResult();
            	for (UserCouponDTO userCouponDTO : tempList) {
					tempIds.add(userCouponDTO.getUserid());
				}
            	
            	//调用接口返回Map
            	List<Long> retailerIds = new ArrayList<Long>();
            	retailerIds.addAll(tempIds);
            	LOGGER.info("零售商id数组:"+ CollectionUtils.printList(retailerIds));
//            	Map<Long,String> resultMap = RemoteServiceSingleton.getInstance().getRetailerManagerService().getNameMapByRetailerIds(retailerIds);
            	
//            	for (UserCouponDTO userCouponDTO : tempList) {
//					userCouponDTO.setRetailerName(resultMap.get(userCouponDTO.getUserid()));
//					userCouponDTO.setCouponstockid(""+userCouponDTO.getId());
//				}
            	pageBean.setResult(tempList);
            	
            }
            //封装零售商店铺名称结束
            request.getSession().setAttribute("pb", pageBean);
            request.getSession().setAttribute("totalCoupons", pageBean.getTotalCount());
            request.getRequestDispatcher("/WEB-INF/views/promotions/coupon/modelpage/coupons_record_model.jsp").forward(
                    request, response);
        } catch (Exception e) {
            LOGGER.error("查询优惠券发放记录列表异常：" + e.getMessage(), e);
        }
        return null;
    }
    *//**
     * 已发放优惠券列表导出表格
     * 
     * @param request
     * @param response
     * @param page
     * @return
     *//*
    @RequestMapping(value = "/downCheckListExcel")
    public void downCheckListExcel(HttpServletRequest request, HttpServletResponse response, Integer page,
            Coupon param,Integer coupontype, Integer status,String username,BigDecimal couponacount,Date starttime,Date endtime) {

		OutputStream os = null;
    	try {
            LOGGER.info("return Coupon Page!");
            Long userid = null;
    		HSSFWorkbook book = new HSSFWorkbook();
    		UserCouponDTO userCouponTemp = new UserCouponDTO();
            if(StringUtils.isNotBlank(userIdTemp)){
            	try {
					userid = Long.parseLong(userIdTemp);
				} catch (Exception e) {
					userid = -1L;//随便给一个零售商不可能使用的值，保证查出来没有券记录
					LOGGER.error("查询优惠券发放记录列表异常,输入的零售商id数字转换异常：{}",e);
				}
            }
            LOGGER.info("userName:"+username);
            if(StringUtils.isNotBlank(username)){
//            	RetailerUser rUser = RemoteServiceSingleton.getInstance().getRetailerUserManagerService().selectUserByName(username);
//            	LOGGER.info("调用根据用户名查询零售商接口后："+rUser);
            	if(rUser != null){
            		userid = rUser.getRetailerId();
            	}else{
            		userid = -1L;//随便给一个零售商不可能使用的值，保证查出来没有券记录
            	}
            }
            LOGGER.info("零售商id:"+userid);
            PageBean<UserCouponDTO> pageBean = new PageBean<UserCouponDTO>();
            UserCouponDTO tempDto = new UserCouponDTO();
            tempDto.setUserid(userid);//零售商id
            tempDto.setStatus(status);
            tempDto.setCouponacount(couponacount);
            tempDto.setStarttime(starttime);
            tempDto.setEndtime(endtime);
            
            
            if (page != null && page != 0) {
                pageBean.setPage(page);
            } else {
                pageBean.setPage(1);
            }
            pageBean.setPageSize(Constants.NUM10000);
            
            pageBean.setParameter(tempDto);
            // 调用服务
            pageBean = RemoteServiceSingleton.getInstance().getCouponRuleService().getCouponRecords(pageBean);
            
            //封装零售商店铺名称
            if(null != pageBean && pageBean.getResult() != null && pageBean.getResult().size()>0){
            	Set<Long> tempIds = new HashSet<Long>();
            	List<UserCouponDTO> tempList = pageBean.getResult();
            	for (UserCouponDTO userCouponDTO : tempList) {
					tempIds.add(userCouponDTO.getUserid());
				}
            	
            	//调用接口返回Map
            	List<Long> retailerIds = new ArrayList<Long>();
            	retailerIds.addAll(tempIds);
            	LOGGER.info("零售商id数组:"+ CollectionUtils.printList(retailerIds));
            	Map<Long,String> resultMap = RemoteServiceSingleton.getInstance().getRetailerManagerService().getNameMapByRetailerIds(retailerIds);
            	
            	for (UserCouponDTO userCouponDTO : tempList) {
					userCouponDTO.setRetailerName(resultMap.get(userCouponDTO.getUserid()));
				}
            	pageBean.setResult(tempList);
            	
                Date date = new Date();
    			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DEFAULTFORMATDATE);
    			String format = simpleDateFormat.format(date);
    			
    			HSSFSheet sheet = book.createSheet(format+"已发放列表");
    			sheet.setColumnWidth(Constants.HSSFSHEETROW_0, Constants.HSSFSHEETROW_150_WIDTH);
    		    sheet.setColumnWidth(Constants.HSSFSHEETROW_1, Constants.HSSFSHEETROW_240_WIDTH);
    		    sheet.setColumnWidth(Constants.HSSFSHEETROW_2, Constants.HSSFSHEETROW_150_WIDTH);
    		    sheet.setColumnWidth(Constants.HSSFSHEETROW_3, Constants.HSSFSHEETROW_150_WIDTH);
    		    sheet.setColumnWidth(Constants.HSSFSHEETROW_4, Constants.HSSFSHEETROW_120_WIDTH );
    		    sheet.setColumnWidth(Constants.HSSFSHEETROW_5, Constants.HSSFSHEETROW_120_WIDTH );
    		    sheet.setColumnWidth(Constants.HSSFSHEETROW_6, Constants.HSSFSHEETROW_150_WIDTH );
    		    sheet.setColumnWidth(Constants.HSSFSHEETROW_7, Constants.HSSFSHEETROW_150_WIDTH );
    		    sheet.setColumnWidth(Constants.HSSFSHEETROW_8, Constants.HSSFSHEETROW_120_WIDTH );
    		    sheet.setColumnWidth(Constants.HSSFSHEETROW_9, Constants.HSSFSHEETROW_150_WIDTH );
    		    HSSFRow row = sheet.createRow((int) 0);
    			HSSFCellStyle style = book.createCellStyle();
    			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
    			//优惠券规则编码 券规则名称 零售商ID 零售商名称 面值金额 满足金额 开始时间 结束时间 状态 来源 
    			HSSFCell cell=null;
    			String [] strtitle={"优惠券规则编码","券规则名称","零售商ID","零售商名称","面值金额","满足金额","开始时间","结束时间","状态","来源"};
    			for(int i=0;i<strtitle.length;i++){
    				cell=row.createCell(i);
    				cell.setCellValue(strtitle[i]);
    				cell.setCellStyle(style);			
    			}
    			
    			List<UserCouponDTO> excelList = pageBean.getResult();
    			if(excelList!=null){
    				for(int i = 0 ; i < excelList.size() ; i++ ){
    					row = sheet.createRow((int) i + 1);
    					userCouponTemp = excelList.get(i);
    					
    					String couponId = userCouponTemp.getId()+"";
    					String couponRuleName = userCouponTemp.getCouponRuleName()+"";
    					String userid2 = userCouponTemp.getUserid()+"";
    					String retailerName = userCouponTemp.getRetailerName()+"";
    					String couponacount2 = userCouponTemp.getCouponacount()+"";
    					String meetpiece = userCouponTemp.getMeetpiece()+"".toString();
    					String starttime2 = simpleDateFormat.format(userCouponTemp.getStarttime())+"";
    					String endtime2 = simpleDateFormat.format(userCouponTemp.getEndtime())+"";
    	    			String status2 = userCouponTemp.getStatus()+"".toString();
    					String orgindesc = userCouponTemp.getOrgindesc();
    					
    					if(status2.equals("0")){
    						status2 = "已发放";
    					}else if(status2.equals("1")){
    						status2 = "已使用";
    					}else if(status2.equals("2")){
    						status2 = "已过期";
    					}else if(status2.equals("3")){
    						status2 = "已使用";
    					}else if(status2.equals("4")){
    						status2 = "已到期";
    					}else if(status2.equals("5")){
    						status2 = "已暂停";
    					}else if(status2.equals("6")){
    						status2 = "已作废";
    					}
    					
    					row.createCell(Constants.SHORT0).setCellValue(couponId);
    					row.createCell(Constants.SHORT1).setCellValue(couponRuleName);
    					row.createCell(Constants.SHORT2).setCellValue(userid2);
    					row.createCell(Constants.SHORT3).setCellValue(retailerName);
    					row.createCell(Constants.SHORT4).setCellValue(couponacount2);
    					row.createCell(Constants.SHORT5).setCellValue(meetpiece);
    					row.createCell(Constants.SHORT6).setCellValue(starttime2);
    					row.createCell(Constants.SHORT7).setCellValue(endtime2);
    					row.createCell(Constants.SHORT8).setCellValue(status2);
    					row.createCell(Constants.SHORT9).setCellValue(orgindesc);
    					
    				}
    				LOGGER.info("拼装信息完成!");
    				
    				String filename = "provided-coupon";
    				
    				os = response.getOutputStream();
    				
    				response.reset();
    				
    				response.setCharacterEncoding("UTF-8");
    				
    				filename = java.net.URLEncoder.encode(filename,"UTF-8");
    				
    				response.setHeader("Content-Disposition","attachment;filename="+
    						new String(filename.getBytes("UTF-8"),"GBK")+".xls");
    				
    				response.setContentType("application/msexcel");//定义输出类型	
    				
    				book.write(os);  	
    				
    				response.getOutputStream().flush();
    				response.getOutputStream().close();	
    			}
    			
    			
            }
            	
            
        } catch (Exception e) {
            LOGGER.error("查询优惠券发放记录列表异常：" + e.getMessage(), e);
        }finally{
			
			try {
				
				os.flush();
				os.close();
				
			} catch (Exception e) {
				
				LOGGER.error("下载查询的账务信息关闭流错误");
				LOGGER.error("对账单下载关闭流出错  错误信息："+e.getMessage(),e);
				
			} 
        }
    }
    
    
    *//**
     * 优惠券列表导出表格
     * 
     * @param request
     * @param response
     * @param page
     * @return
     *//*
    @RequestMapping(value = "/downCouponListExcel")
    public void downCouponListExcel(HttpServletRequest request, HttpServletResponse response, Integer page,
            Coupon param,Integer couponType, String couponName,BigDecimal couponacount,Date starttime,Date endtime) {

		OutputStream os = null;
    	try {
            LOGGER.info("return Coupon Page!");
    		HSSFWorkbook book = new HSSFWorkbook();
    		Coupon coupons = new Coupon();
            
            LOGGER.info("couponName:"+couponName);
            
            PageBean<Coupon> pageBean = new PageBean<Coupon>();
            Coupon coupon = new Coupon();
            coupon.setCoupontype(couponType);
            coupon.setCouponname(couponName);
            coupon.setCouponacount(couponacount);
            coupon.setStarttime(starttime);
            coupon.setEndtime(endtime);
            
            
            if (page != null && page != 0) {
                pageBean.setPage(page);
            } else {
                pageBean.setPage(1);
            }
            pageBean.setPageSize(Constants.NUM10000);
            
            pageBean.setParameter(coupon);
            // 调用服务
            pageBean = RemoteServiceSingleton.getInstance().getCouponService().findCouponList(pageBean);
            
            //封装零售商店铺名称
            if(null != pageBean && pageBean.getResult() != null && pageBean.getResult().size()>0){
            	List<Coupon> tempList = pageBean.getResult();
            	
            	pageBean.setResult(tempList);
            	
                Date date = new Date();
    			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DEFAULTFORMATDATE);
    			String format = simpleDateFormat.format(date);
    			
    			HSSFSheet sheet = book.createSheet(format+"优惠券列表");
    			sheet.setColumnWidth(Constants.HSSFSHEETROW_0, Constants.HSSFSHEETROW_150_WIDTH);
    		    sheet.setColumnWidth(Constants.HSSFSHEETROW_1, Constants.HSSFSHEETROW_240_WIDTH);
    		    sheet.setColumnWidth(Constants.HSSFSHEETROW_2, Constants.HSSFSHEETROW_150_WIDTH);
    		    sheet.setColumnWidth(Constants.HSSFSHEETROW_3, Constants.HSSFSHEETROW_150_WIDTH);
    		    sheet.setColumnWidth(Constants.HSSFSHEETROW_4, Constants.HSSFSHEETROW_150_WIDTH );
    		    sheet.setColumnWidth(Constants.HSSFSHEETROW_5, Constants.HSSFSHEETROW_150_WIDTH );
    		    HSSFRow row = sheet.createRow((int) 0);
    			HSSFCellStyle style = book.createCellStyle();
    			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
    			//优惠券规则编码 券规则名称 零售商ID 零售商名称 面值金额 满足金额 开始时间 结束时间 状态 来源 
    			HSSFCell cell=null;
    			String [] strtitle={"优惠券编码","优惠券名称","类型","面值金额","开始时间","结束时间"};
    			for(int i=0;i<strtitle.length;i++){
    				cell=row.createCell(i);
    				cell.setCellValue(strtitle[i]);
    				cell.setCellStyle(style);			
    			}
    			
    			List<Coupon> excelList = pageBean.getResult();
    			if(excelList!=null){
    				for(int i = 0 ; i < excelList.size() ; i++ ){
    					row = sheet.createRow((int) i + 1);
    					coupons = excelList.get(i);
    					
    					String couponId = ""+coupons.getCouponid();
    					String couponname = coupons.getCouponname()+"";
    					String coupontype = "";
    					if(coupons.getCoupontype() == 1){
    						coupontype = "金券";
    					}if(coupons.getCoupontype() == 2){
    						coupontype = "现金券";
    					}
    					String couponacount2 = coupons.getCouponacount()+"";
    					String starttime2 = simpleDateFormat.format(coupons.getStarttime())+"";
    					String endtime2 = simpleDateFormat.format(coupons.getEndtime())+"";
    					
    					row.createCell(Constants.SHORT0).setCellValue(couponId);
    					row.createCell(Constants.SHORT1).setCellValue(couponname);
    					row.createCell(Constants.SHORT2).setCellValue(coupontype);
    					row.createCell(Constants.SHORT3).setCellValue(couponacount2);
    					row.createCell(Constants.SHORT4).setCellValue(starttime2);
    					row.createCell(Constants.SHORT5).setCellValue(endtime2);
    					
    				}
    				LOGGER.info("拼装信息完成!");
    				
    				String filename = "provided-coupon";
    				
    				os = response.getOutputStream();
    				
    				response.reset();
    				
    				response.setCharacterEncoding("UTF-8");
    				
    				filename = java.net.URLEncoder.encode(filename,"UTF-8");
    				
    				response.setHeader("Content-Disposition","attachment;filename="+
    						new String(filename.getBytes("UTF-8"),"GBK")+".xls");
    				
    				response.setContentType("application/msexcel");//定义输出类型	
    				
    				book.write(os);  	
    				
    				response.getOutputStream().flush();
    				response.getOutputStream().close();	
    			}
    			
    			
            }
            	
            
        } catch (Exception e) {
            LOGGER.error("查询优惠券列表异常：" + e.getMessage(), e);
        }finally{
			
			try {
				
				os.flush();
				os.close();
				
			} catch (Exception e) {
				
				LOGGER.error("下载查询的账务信息关闭流错误");
				LOGGER.error("对账单下载关闭流出错  错误信息："+e.getMessage(),e);
				
			} 
        }
    }
    @RequestMapping(value = "/getCouponUseRecord")
    @ResponseBody
    public String getCouponUseRecord(HttpServletRequest request){
    	Long couponStockId = Long.parseLong(request.getParameter("couponstockid"));
    	Integer couponType = Integer.parseInt(request.getParameter("coupontype"));
    	Integer userType = Integer.parseInt(request.getParameter("userType"));
    	List<UseRecordDto> list = RemoteServiceSingleton.getInstance().getCouponRuleService().getCouponUseRecord(couponStockId, couponType,userType);
    	String json = JSONArray.fromObject(list).toString();
    	return json;
    }

}
*/