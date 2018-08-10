package com.mall.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;

public class ExportExcelTool {
	
	private static final Log LOGGER= LogFactory.getLogger(ExportExcelTool.class);

	/**
	 * @param response
	 * @param exportFileName 导出的文件名  只支持一个标签页
	 * @param titles 表头信息  数量必须与 列值一致
	 * @param values 列值 String的数量必须与表头保持一下
	 */
	public ExportExcelTool(HttpServletResponse response,String exportFileName,String[] titles,List<String[]> values){
		OutputStream os = null;
		try {
			
			HSSFWorkbook book = new HSSFWorkbook();
			HSSFSheet sheet = book.createSheet();
			HSSFRow row = sheet.createRow(0);
			
			HSSFCellStyle style = book.createCellStyle();
			
//			style.setFillForegroundColor(HSSFColor.YELLOW.index);
			
			HSSFCell cell = null;
			
			sheet.autoSizeColumn(1);
			if ( titles.length != 0){
				for (int i = 0; i <  titles.length; i++) {
					
					cell = row.createCell(i);
	                cell.setCellValue(titles[i]);
	                cell.setCellStyle(style);
	              
				}
			}
			
			if ( values.size() > 0 ){
				for (Short i = 0 ; i < values.size() ; i++){
					String[] strings = values.get(i);
					row = sheet.createRow(i+1);
					
					row.setRowStyle(style);
					
					for (int j = 0; j < strings.length; j++) {
						row.createCell(j).setCellValue(strings[i]);
					}
					
				}
			}
			LOGGER.info("拼装Excel -->"+exportFileName+"<---完成");
			
			os = response.getOutputStream();
			
			response.reset();
			response.setCharacterEncoding("UTF-8");
			
			exportFileName = URLEncoder.encode(exportFileName,"UTF-8");
			
			response.setHeader("Content-Disposition","attachment;filename="+
					new String(exportFileName.getBytes("UTF-8"),"GBK")+".xls");
			
			response.setContentType("application/msexcel");//定义输出类型	
			
			book.write(os);  	
			
		} catch (Exception e) {
			
			LOGGER.info("拼装Excel表格"+exportFileName);
			
		}finally{
			try {
				response.getOutputStream().flush();
				response.getOutputStream().close();	
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
