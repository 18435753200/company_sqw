/*package com.mall.InterceptorUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

//import com.mall.supplier.order.dto.StockInOrderItemDto;
//import com.mall.supplier.order.dto.StockInOrderPdfDto;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

public class PdfTools {
	
	
	public static void MakePdf(StockInOrderPdfDto pdfDto)
	{
		  Rectangle rectPageSize = new Rectangle(PageSize.A4);// 定义A4页面大小
	      Document document = new Document(rectPageSize, 0, 0, 0, 0);
	      java.text.SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd"); 
	      try {
	    	  BaseFont baseFont = BaseFont.createFont(PdfTools.class.getResource("/").getFile().toString()+File.separator+"SIMSUN.TTC,1",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
	    	  Font fontTitle = new Font(baseFont);
	    	  Font fontCentext = new Font(baseFont);
	    	  Font font = new Font(baseFont);
	    	  fontTitle.setSize(25);
	    	  fontCentext.setSize(8);
	    	  font.setSize(11);
	    	  OutputStream out=new FileOutputStream("d:/test.pdf");
		      PdfWriter.getInstance(document, out);
		      document.open();
		      // 标题
		      
		      //document.addTitle("test");
		      document.addTitle("入  库  单");
              Paragraph pragraph = new Paragraph("入  库  单", fontTitle);
              pragraph.setAlignment(Paragraph.ALIGN_CENTER);
              pragraph.add(new Paragraph("   "));
              pragraph.add(new Paragraph("   "));
              document.add(pragraph);
              Table table = new Table(4);
              table.setBorderWidth(0);
              table.setAutoFillEmptyCells(true);
              table.setBorder(0);
              pragraph = new Paragraph("入库单编号:", font);
              Cell cell=new Cell(pragraph);
              cell.setBorder(0);
              cell.setUseAscender(true);
              cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
              table.addCell(cell);
              pragraph = new Paragraph(pdfDto.getId()==null?"":pdfDto.getId().toString(), font);
              
              cell=new Cell(pragraph);
              cell.setUseAscender(true);
              cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
              cell.setBorder(0);
              table.addCell(cell);
              pragraph = new Paragraph("入库通知单编号:", font);
              cell=new Cell(pragraph);
              cell.setBorder(0);
              cell.setUseAscender(true);
              cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
              table.addCell(cell);
              pragraph = new Paragraph(pdfDto.getNotificationId()==null?"":pdfDto.getNotificationId().toString(), font);
             
              cell=new Cell(pragraph);
              cell.setUseAscender(true);
              cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
              cell.setBorder(0);
              table.addCell(cell);
              pragraph = new Paragraph("入库日期:", font);
             
              cell=new Cell(pragraph);
              cell.setUseAscender(true);
              cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
              cell.setBorder(0);
              table.addCell(cell);
              pragraph = new Paragraph(pdfDto.getCreateTime()==null?"":format.format(pdfDto.getCreateTime()), font);
             
              cell=new Cell(pragraph);
              cell.setUseAscender(true);
              cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
              cell.setBorder(0);
              table.addCell(cell);
              pragraph = new Paragraph("库管员:", font);
             
              cell=new Cell(pragraph);
              cell.setUseAscender(true);
              cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
              cell.setBorder(0);
              table.addCell(cell);
              pragraph = new Paragraph(pdfDto.getCreateBy()==null?"":pdfDto.getCreateBy(), font);
             
              cell=new Cell(pragraph);
              cell.setUseAscender(true);
              cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
              cell.setBorder(0);
              table.addCell(cell);
              
              
              
              
              pragraph = new Paragraph("物流通知单编号:", font);
             
              cell=new Cell(pragraph);
              cell.setUseAscender(true);
              cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
              cell.setBorder(0);
              table.addCell(cell);
              pragraph = new Paragraph(pdfDto.getLogisticsNumber()==null?"":pdfDto.getLogisticsNumber(), font);
             
              cell=new Cell(pragraph);
              cell.setUseAscender(true);
              cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
              cell.setBorder(0);
              table.addCell(cell);
              pragraph = new Paragraph("库房名称：", font);
             
              cell=new Cell(pragraph);
              cell.setUseAscender(true);
              cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
              cell.setBorder(0);
              table.addCell(cell);
              pragraph = new Paragraph(pdfDto.getWarehouseName()==null?"":pdfDto.getWarehouseName(), font);
             
              cell=new Cell(pragraph);
              cell.setUseAscender(true);
              cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
              cell.setBorder(0);
              table.addCell(cell);
              document.add(table);
//---------------------------------------------------------------内容
              table=new Table(13);
              table.setWidth(100);
              pragraph = new Paragraph("序号", fontCentext);
              
              cell=new Cell(pragraph);
              cell.setUseAscender(true);
              
              cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
              table.addCell(cell);
              pragraph = new Paragraph("商品ID", fontCentext);
             
              cell=new Cell(pragraph);
              cell.setUseAscender(true);
              cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
              table.addCell(cell);
              pragraph = new Paragraph("商品条码", fontCentext);
             
              cell=new Cell(pragraph);
              cell.setUseAscender(true);
              cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
              table.addCell(cell);
              pragraph = new Paragraph("商品名称", fontCentext);
             
              cell=new Cell(pragraph);
              cell.setUseAscender(true);
              cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
              table.addCell(cell);
              pragraph = new Paragraph("规格", fontCentext);
             
              cell=new Cell(pragraph);
              cell.setUseAscender(true);
              cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
              table.addCell(cell);
              pragraph = new Paragraph("单位", fontCentext);
             
              cell=new Cell(pragraph);
              cell.setUseAscender(true);
              cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
              table.addCell(cell);
              
              pragraph = new Paragraph("本次通知数量", fontCentext);
              
              cell=new Cell(pragraph);
              cell.setUseAscender(true);
              cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
              table.addCell(cell);
              
              pragraph = new Paragraph("已入库数量", fontCentext);
              
              cell=new Cell(pragraph);
              cell.setUseAscender(true);
              cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
              table.addCell(cell);
              
              
              pragraph = new Paragraph("本次入库数量", fontCentext);
              
              cell=new Cell(pragraph);
              cell.setUseAscender(true);
              cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
              table.addCell(cell);
              
              pragraph = new Paragraph("库房库位", fontCentext);
              
              cell=new Cell(pragraph);
              cell.setUseAscender(true);
              cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
              table.addCell(cell);
              
              pragraph = new Paragraph("批号", fontCentext);
              
              cell=new Cell(pragraph);
              cell.setUseAscender(true);
              cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
              table.addCell(cell);
              
              pragraph = new Paragraph("批次", fontCentext);
              
              cell=new Cell(pragraph);
              cell.setUseAscender(true);
              cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
              table.addCell(cell);
              
              pragraph = new Paragraph("生产日期", fontCentext);
              
              cell=new Cell(pragraph);
              cell.setUseAscender(true);
              cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
              table.addCell(cell);
              
              List<StockInOrderItemDto> dtos=pdfDto.getDtos();
              for(int i=0;i<dtos.size();i++)
              {
            	  StockInOrderItemDto dto=dtos.get(i);
            	  pragraph = new Paragraph(String.valueOf(i+1), fontCentext);
                  
                  cell=new Cell(pragraph);
                  cell.setUseAscender(true);
                  cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
                  cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
                  table.addCell(cell);
                  pragraph = new Paragraph(dto.getSkuId()==null?"":dto.getSkuId().toString(), fontCentext);
                  
                  cell=new Cell(pragraph);
                  cell.setUseAscender(true);
                  cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
                  cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
                  table.addCell(cell);
                  
                  pragraph = new Paragraph(dto.getBarCode()==null?"":dto.getBarCode(), fontCentext);
                  
                  cell=new Cell(pragraph);
                  cell.setUseAscender(true);
                  cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
                  cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
                  table.addCell(cell);
                  
                  pragraph = new Paragraph(dto.getSkuNameCn()==null?"":dto.getSkuNameCn(), fontCentext);
                  
                  cell=new Cell(pragraph);
                  cell.setUseAscender(true);
                  cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
                  cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
                  table.addCell(cell);
                  
                  pragraph = new Paragraph(dto.getFormat()==null?"":dto.getFormat(), fontCentext);
                  
                  cell=new Cell(pragraph);
                  cell.setUseAscender(true);
                  cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
                  cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
                  table.addCell(cell);
                  
                  pragraph = new Paragraph(dto.getUnit()==null?"":dto.getUnit().toString(), fontCentext);
                  
                  cell=new Cell(pragraph);
                  cell.setUseAscender(true);
                  cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
                  cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
                  table.addCell(cell);
                  
                  pragraph = new Paragraph(dto.getQty()==null?"":dto.getQty().toString(), fontCentext);
                  
                  cell=new Cell(pragraph);
                  cell.setUseAscender(true);
                  cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
                  cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
                  table.addCell(cell);
                  
                  pragraph = new Paragraph(dto.getQtyReceived()==null?"":dto.getQtyReceived().toString(), fontCentext);
                  
                  cell=new Cell(pragraph);
                  cell.setUseAscender(true);
                  cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
                  cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
                  table.addCell(cell);
                  
                  pragraph = new Paragraph(dto.getQtyStorage()==null?"":dto.getQtyStorage().toString(), fontCentext);
                  
                  cell=new Cell(pragraph);
                  cell.setUseAscender(true);
                  cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
                  cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
                  table.addCell(cell);
                  
                  pragraph = new Paragraph(dto.getShipId()==null?"":dto.getShipId().toString(), fontCentext);
                  
                  cell=new Cell(pragraph);
                  cell.setUseAscender(true);
                  cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
                  cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
                  table.addCell(cell);
                  
                  pragraph = new Paragraph(dto.getBatchNumber()==null?"":dto.getBatchNumber().toString(), fontCentext);
                  
                  cell=new Cell(pragraph);
                  cell.setUseAscender(true);
                  cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
                  cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
                  table.addCell(cell);
                  
                  pragraph = new Paragraph(dto.getWarehouse()==null?"":dto.getWarehouse().toString(), fontCentext);
                  
                  cell=new Cell(pragraph);
                  cell.setUseAscender(true);
                  cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
                  cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
                  table.addCell(cell);
                  
                  pragraph = new Paragraph("", fontCentext);
                  
                  cell=new Cell(pragraph);
                  cell.setUseAscender(true);
                  cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
                  cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
                  table.addCell(cell);
              }
              document.add(table);
		      document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static String MakeHtml(StockInOrderPdfDto pdfDto)
	{
		java.text.SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		String html="<div class='xia'><p class='p1'><span>入库单编号:</span><input type='text' value='"+pdfDto.getId()+"'><span>入库通知单编号:</span><input type='text' value='"+pdfDto.getNotificationId()+"'><span>入库日期:</span><input type='text' value='"+format.format(pdfDto.getCreateTime())+"'></p><p class='p1'><span>库管员:</span><input type='text' value=''><span>物流通知单编号:</span><input type='text' value='"+pdfDto.getShipId()+"'><span>库房名称:</span><input type='text' value='"+pdfDto.getWarehouseName()+"'></p></div>";
		
		return html;
	}
	
}
*/