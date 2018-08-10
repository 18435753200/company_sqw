package com.mall.controller.order;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mall.mybatis.utility.PageBean;
//import com.mall.supplier.order.api.rpc.ExpressMgsService;
//import com.mall.supplier.order.dto.ExpressMgsDto;
//import com.mall.supplier.order.dto.StockOutOrderDto;
//import com.mall.supplier.order.po.ExpressDetail;
//import com.mall.supplier.order.po.StockOutOrder;
import com.mall.platform.proxy.RemoteServiceSingleton;

@Controller
@RequestMapping(value = "/expressMgs")
public class ExpressMgsController {
	@RequestMapping(value = "/findExpressMgs")
	public String findExpressMgs(Model model){
		return "/expressMgs/findExpressMgs";
	}
	/*@RequestMapping(value = "/detilMsg")
	public String detilMsg(HttpServletRequest request,Model model){
		String expressNumber=request.getParameter("expressNumber");
		String expressId=request.getParameter("expressId");
		String orderId=request.getParameter("orderId");
		ExpressMgsService expressMgsService=(ExpressMgsService)RemoteServiceSingleton.getInstance().getAppService("expressMgsService");
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("express_number", expressNumber);
		map.put("express_id", expressId);
		map.put("orderId", orderId);
		List<ExpressDetail> mgsDtos=expressMgsService.findExpressDetilMgs(map);
		model.addAttribute("msg", mgsDtos);
		return "/expressMgs/detilMsg";
	}*/
	
	@RequestMapping(value = "/RedirectMsg")
	public String RedirectMsg(HttpServletRequest request,HttpServletResponse response,Model model){
		String url=request.getParameter("url");
		try {
			response.sendRedirect("https://"+url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/*@RequestMapping(value = "/findExpressMgsList")
	public String findExpressMgsList(HttpServletRequest request,Integer page,Model model){
		java.text.SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		ExpressMgsService expressMgsService=(ExpressMgsService)RemoteServiceSingleton.getInstance().getAppService("expressMgsService");
		String orderId=request.getParameter("orderId");
		String expressNumber=request.getParameter("expressNumber");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		PageBean<ExpressMgsDto> pageBean=new PageBean<ExpressMgsDto>();
		ExpressMgsDto mgsDto=new ExpressMgsDto();
		try {
			if(orderId!=null&&!"".equals(orderId))
			{
				mgsDto.setOrderId(Long.parseLong(orderId));
			}
			if(expressNumber!=null&&!"".equals(expressNumber))
			{
				mgsDto.setExpressNumber(expressNumber);
			}
			if(startTime!=null&&!"".equals(startTime))
			{
				mgsDto.setStartTime(format.parse(startTime));
			}
			if(endTime!=null&&!"".equals(endTime))
			{
				mgsDto.setEndTime(format.parse(endTime));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		pageBean.setParameter(mgsDto);
		if(page!=null)
		{
			pageBean.setPage(page);
		}else{
			pageBean.setPage(1);
		}
		pageBean.setPageSize(10);
		pageBean=expressMgsService.findExpressMgs(pageBean);
		model.addAttribute("pb", pageBean);
		return "/expressMgs/findExpressMgsList";
	}
	@RequestMapping(value = "/OutPutExcel")
	public String OutPutExcel(HttpServletRequest request,HttpServletResponse response,Integer page,Model model)
	{
		java.text.SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		ExpressMgsService expressMgsService=(ExpressMgsService)RemoteServiceSingleton.getInstance().getAppService("expressMgsService");
		
		String orderId=request.getParameter("orderId");
		String expressNumber=request.getParameter("expressNumber");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		ExpressMgsDto mgsDto=new ExpressMgsDto();
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/vnd.ms-excel;charset=UTF-8"); 
			response.addHeader("Content-Disposition", "attachment;filename="+new String("发货信息列表".getBytes("gbk"),"iso-8859-1")+".xls");
			if(orderId!=null&&!"".equals(orderId))
			{
				mgsDto.setOrderId(Long.parseLong(orderId));
			}
			if(expressNumber!=null&&!"".equals(expressNumber))
			{
				mgsDto.setExpressNumber(expressNumber);
			}
			if(startTime!=null&&!"".equals(startTime))
			{
				mgsDto.setStartTime(format.parse(startTime));
			}
			if(endTime!=null&&!"".equals(endTime))
			{
				mgsDto.setEndTime(format.parse(endTime));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExpressWriteExcel(response,expressMgsService.OutputExcel(mgsDto));
		return null;
	}
	private void ExpressWriteExcel(HttpServletResponse response,
			 List<ExpressMgsDto> expressMgsDtos) {
		java.text.SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("快递信息");  
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        HSSFRow row = sheet.createRow((int) 0);  
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
  
        HSSFCell cell = row.createCell(0);  
        cell.setCellValue("序号");  
        cell.setCellStyle(style);  
        cell = row.createCell(1);  
        cell.setCellValue("订单编号");  
        cell.setCellStyle(style);  
        cell = row.createCell(2);  
        cell.setCellValue("快递单号");  
        cell.setCellStyle(style);  
        cell = row.createCell(3);  
        cell.setCellValue("快递商名称");  
        cell.setCellStyle(style);  
        cell = row.createCell(4);  
        cell.setCellValue("始发地");  
        cell.setCellStyle(style);  
        cell = row.createCell(5);  
        cell.setCellValue("目的地");  
        cell.setCellStyle(style);  
        cell = row.createCell(6);  
        cell.setCellValue("重量(kg)");  
        cell.setCellStyle(style);  
        
        for (int i = 0; i < expressMgsDtos.size(); i++)  
        {  
            row = sheet.createRow((int) i + 1);  
            ExpressMgsDto stu =expressMgsDtos.get(i);  
            // 第四步，创建单元格，并设置值  
            row.createCell(0).setCellValue((int) i + 1);  
           
            row.createCell(1).setCellValue(stu.getOrderId()==null?"":stu.getOrderId().toString());  
            row.createCell(2).setCellValue(stu.getExpressNumber()==null?"":stu.getExpressNumber()); 
            
            row.createCell(3).setCellValue(stu.getExpressName()==null?"":stu.getExpressName());
            row.createCell(4).setCellValue(stu.getStartAddress()==null?"":stu.getStartAddress());  
            
            row.createCell(5).setCellValue(stu.getEndAddress()==null?"":stu.getEndAddress()); 
            row.createCell(6).setCellValue(stu.getWeight()==null?"0":stu.getWeight().toString());  
            
        } */
       /* try {
        	OutputStream fout = response.getOutputStream();  
            wb.write(fout);  
            fout.close();  
		} catch (Exception e) {
			e.printStackTrace();
		}*/
        
	}

