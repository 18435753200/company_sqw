/*package com.mall.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.csource.upload.UploadFileUtil;

import com.mall.platform.model.PlatformUser;
import com.mall.stock.api.rpc.WarehouseService;
import com.mall.stock.po.Warehouse;
//import com.mall.supplier.order.dto.NotificationOutOrderDTO;
//import com.mall.supplier.order.dto.PChaseOrderItemDTO;
//import com.mall.supplier.order.dto.PChaseOrderVO;
//import com.mall.supplier.order.dto.PChaseVirtualOrderItemDTO;
//import com.mall.supplier.order.dto.PChaseVirtualOrderVO;
//import com.mall.supplier.order.dto.StockInOrderDto;
//import com.mall.supplier.order.dto.StockInOrderItemDto;
//import com.mall.supplier.order.dto.StockOutOrderDto;
//import com.mall.supplier.order.dto.StockOutOrderItemDto;
//import com.mall.supplier.order.dto.WareHouseDTO;
//import com.mall.supplier.order.po.NotificationOutItem;
//import com.mall.supplier.order.po.NotificationOutOrder;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import com.mall.platform.proxy.RemoteServiceSingleton;


public class WritePDF {

	public static final String TYPE01="ORDER";
	public static final String TYPE02="INSTOCK";
	public static final String TYPE03="OUTSTOCK";
	public static final String TYPE04="NOOUTSTOCK";
	public static final String TYPE05="VIRTUALORDER";
	public String getInstance(String writeType,Object object,HttpServletRequest request)
	{
		String result="";
		if(writeType.equals(TYPE01))
		{
			PChaseOrderVO pChaseOrderVO=(PChaseOrderVO)object;
			result=WriteOrder(pChaseOrderVO,request);
		}
		if(writeType.equals(TYPE02))
		{
			List<StockInOrderDto> orderDtos=(List<StockInOrderDto>)object;
			result=WriteInOrder(orderDtos,request);
		}
		if(writeType.equals(TYPE03))
		{
			List<StockOutOrderDto> orderDtos=(List<StockOutOrderDto>)object;
			result=WriteOutOrder(orderDtos,request);
		}
		if(writeType.equals(TYPE04))
		{
			List<NotificationOutOrderDTO> orderDtos=(List<NotificationOutOrderDTO>)object;
			result=WriteNoOutOrder(orderDtos,request);
		}
		if(writeType.equals(TYPE05))
		{
			PChaseVirtualOrderVO pChaseOrderVO=(PChaseVirtualOrderVO)object;
			 result=WriteVirtualOrder(pChaseOrderVO,request);
		}
		return result;
	}
	
	private String getOrderStatus(short status)
	{
		if(status==1)
		{
			return "未审核";
		}else if(status==10)
		{
			return "已审核";
		}else if(status==15)
		{
			return "已到齐";
		}else if(status==20)
		{
			return "已完成";
		}else{
			return "";
		}
	}
	private String WriteOrder(PChaseOrderVO pChaseOrderVO,HttpServletRequest request)
	{
		Rectangle rectPageSize = new Rectangle(PageSize.A4);// 定义A4页面大小
	      Document document = new Document(rectPageSize, 10, 0, 10, 0);
	      java.text.SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd"); 
	      String imgurl="";
	      try {
	    	  BaseFont baseFont = BaseFont.createFont(WritePDF.class.getResource("/").getFile().toString()+File.separator+"SIMSUN.TTC,1",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
	    	  Font fontTitle = new Font(baseFont);
	    	  Font fontCentext = new Font(baseFont);
	    	  Font font = new Font(baseFont);
	    	  fontTitle.setSize(25);
	    	  fontCentext.setSize(8);
	    	  font.setSize(11);
	    	  File file=new File(request.getSession().getServletContext().getRealPath("/")+File.separator+"pdf"+File.separator);
	    	  if(!file.exists())
	    	  {
	    		  file.mkdirs();
	    	  }
	    	  OutputStream out=new FileOutputStream(file.getAbsolutePath()+File.separator+pChaseOrderVO.getBusinessNumberChar()+".pdf");
		      PdfWriter.getInstance(document, out);
		      document.open();
		      
		      Image image=Image.getInstance(request.getSession().getServletContext().getRealPath("/commons/images/")+File.separator+"pdfOrder.jpg");
		      document.add(image);
		  Paragraph pragraph = new Paragraph("众聚商城采购订单", fontTitle);
          pragraph.setAlignment(Paragraph.ALIGN_CENTER);
          pragraph.add(new Paragraph("   "));
          pragraph.add(new Paragraph("   "));
          document.add(pragraph);
          
          Table table = new Table(4);
          table.setBorder(0);
        //------------------------------------------------------------------------------------------//
          pragraph = new Paragraph("订货单号:\nOrder No.", fontCentext);
          Cell cell=new Cell(pragraph);
          cell.setBorder(0);
          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
          table.addCell(cell);
          pragraph = new Paragraph(pChaseOrderVO.getBusinessNumberChar()==null?"":pChaseOrderVO.getBusinessNumberChar(), fontCentext);
          cell=new Cell(pragraph);
          cell.setBorder(0);
          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
          table.addCell(cell);
        //------------------------------------------------------------------------------------------//
          
        //------------------------------------------------------------------------------------------//
          pragraph = new Paragraph("", fontCentext);
          cell=new Cell(pragraph);
          cell.setBorder(0);
          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
          table.addCell(cell);
          pragraph = new Paragraph("", fontCentext);
          cell=new Cell(pragraph);
          cell.setBorder(0);
          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
          table.addCell(cell);
        //------------------------------------------------------------------------------------------//
//------------------------------------------------------------------------------------------//           
          pragraph = new Paragraph("供应商名称:\nVendor Name", fontCentext);
          cell=new Cell(pragraph);
          cell.setBorder(0);
          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
          table.addCell(cell);
          pragraph = new Paragraph(pChaseOrderVO.getSupplierName()==null?"":pChaseOrderVO.getSupplierName(), fontCentext);
          cell=new Cell(pragraph);
          cell.setBorder(0);
          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居
          table.addCell(cell);
          
        //------------------------------------------------------------------------------------------//            
         
          
        //------------------------------------------------------------------------------------------//
          pragraph = new Paragraph("供应商编码:\nVendor Code", fontCentext);
          cell=new Cell(pragraph);
          cell.setBorder(0);
          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
          table.addCell(cell);
          pragraph = new Paragraph(pChaseOrderVO.getSupplierId()==null?"":pChaseOrderVO.getSupplierId().toString(), fontCentext);
          cell=new Cell(pragraph);
          cell.setBorder(0);
          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
          table.addCell(cell);
        
       
        //------------------------------------------------------------------------------------------//
          pragraph = new Paragraph("创建日期:\nCreated Date.", fontCentext);
          cell=new Cell(pragraph);
          cell.setBorder(0);
          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
          table.addCell(cell);
          pragraph = new Paragraph(pChaseOrderVO.getCreateTime()==null?"":format.format(pChaseOrderVO.getCreateTime()), fontCentext);
          cell=new Cell(pragraph);
          cell.setBorder(0);
          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
          table.addCell(cell);
        //------------------------------------------------------------------------------------------//
        //------------------------------------------------------------------------------------------//
          pragraph = new Paragraph("订单状态:\nOrder Status.", fontCentext);
          cell=new Cell(pragraph);
          cell.setBorder(0);
          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
          table.addCell(cell);
          pragraph = new Paragraph(pChaseOrderVO.getOrderStatus()==null?"":getOrderStatus(pChaseOrderVO.getOrderStatus()), fontCentext);
          cell=new Cell(pragraph);
          cell.setBorder(0);
          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
          table.addCell(cell);
        //------------------------------------------------------------------------------------------//
        //------------------------------------------------------------------------------------------//
          pragraph = new Paragraph("订单总金额:\nOrder Gross Amount.", fontCentext);
          cell=new Cell(pragraph);
          cell.setBorder(0);
          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
          table.addCell(cell);
          pragraph = new Paragraph(pChaseOrderVO.getItemTotalPrice()==null?"":pChaseOrderVO.getItemTotalPrice().toString(), fontCentext);
          cell=new Cell(pragraph);
          cell.setBorder(0);
          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
          table.addCell(cell);
        //------------------------------------------------------------------------------------------//
        //------------------------------------------------------------------------------------------//
          pragraph = new Paragraph("送货有效期:\nDelivery Period of Validity.", fontCentext);
          cell=new Cell(pragraph);
          cell.setBorder(0);
          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
          table.addCell(cell);
          pragraph = new Paragraph(pChaseOrderVO.getSendDate()==null?"":format.format(pChaseOrderVO.getSendDate()), fontCentext);
          cell=new Cell(pragraph);
          cell.setBorder(0);
          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
          table.addCell(cell);
        //------------------------------------------------------------------------------------------//
          
        //------------------------------------------------------------------------------------------//
          pragraph = new Paragraph("备注:\nRemark.", fontCentext);
          cell=new Cell(pragraph);
          cell.setBorder(0);
          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
          table.addCell(cell);
          pragraph = new Paragraph(pChaseOrderVO.getRemark()==null?"":pChaseOrderVO.getRemark(), fontCentext);
          cell=new Cell(pragraph);
          cell.setBorder(0);
          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
          table.addCell(cell);
        //------------------------------------------------------------------------------------------//
          
        //------------------------------------------------------------------------------------------//
          pragraph = new Paragraph("", fontCentext);
          cell=new Cell(pragraph);
          cell.setBorder(0);
          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
          table.addCell(cell);
          pragraph = new Paragraph("");
          cell=new Cell(pragraph);
          cell.setBorder(0);
          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
          table.addCell(cell);
        //------------------------------------------------------------------------------------------//
          document.add(table);
          
//-----------------------------------------商品详情--------------------------------------------------------//       
          pragraph = new Paragraph("\n", font);
          pragraph.setAlignment(Element.ALIGN_CENTER);
          document.add(pragraph);
          pragraph = new Paragraph("商品详情", font);
          pragraph.setAlignment(Element.ALIGN_CENTER);
          document.add(pragraph);
          
//-----------------------------------------详情列表-------------------------------------------------------//            
          table=new Table(8);
          table.setWidths(new int[]{40,150,150,160,100,100,100,100});
          pragraph = new Paragraph("序号", fontCentext);
          cell=new Cell(pragraph);
          
          cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
          table.addCell(cell);
          pragraph = new Paragraph("商品编码", fontCentext);
          cell=new Cell(pragraph);
          
          cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
          table.addCell(cell);
          pragraph = new Paragraph("商品名称", fontCentext);
          cell=new Cell(pragraph);
          
          cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
          table.addCell(cell);
          pragraph = new Paragraph("国际条形码", fontCentext);
          cell=new Cell(pragraph);
         
          cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
          table.addCell(cell);
          pragraph = new Paragraph("箱规", fontCentext);
          cell=new Cell(pragraph);
          
          cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
          table.addCell(cell);
          pragraph = new Paragraph("采购单价", fontCentext);
          cell=new Cell(pragraph);
          
          cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
          table.addCell(cell);
          pragraph = new Paragraph("采购数量", fontCentext);
          cell=new Cell(pragraph);
          
          cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
          table.addCell(cell);
          pragraph = new Paragraph("采购金额", fontCentext);
          cell=new Cell(pragraph);
          
          cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
          table.addCell(cell);
          List<PChaseOrderItemDTO> itemDTOs=pChaseOrderVO.getpCOItemList();
          for(int i=0;i<itemDTOs.size();i++)
          {
        	  PChaseOrderItemDTO itemDTO=itemDTOs.get(i);
        	  pragraph = new Paragraph(String.valueOf(i+1), fontCentext);
              cell=new Cell(pragraph);
              
              cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
              table.addCell(cell);
          	  pragraph = new Paragraph(itemDTO.getBusinessProdid()==null?"":itemDTO.getBusinessProdid().toString(), fontCentext);
              cell=new Cell(pragraph);
              
              cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
              table.addCell(cell);
              pragraph = new Paragraph(itemDTO.getPname()==null?"":itemDTO.getPname(), fontCentext);
              cell=new Cell(pragraph);
              
              cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
              table.addCell(cell);
              pragraph = new Paragraph(itemDTO.getBarCode()==null?"":itemDTO.getBarCode(), fontCentext);
              cell=new Cell(pragraph);
              
              cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
              table.addCell(cell);
              pragraph = new Paragraph(itemDTO.getFormat()==null?"":itemDTO.getFormat(), fontCentext);
              cell=new Cell(pragraph);
              
              cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
              table.addCell(cell);
              pragraph = new Paragraph(itemDTO.getSkuPrice()==null?"":itemDTO.getSkuPrice().toString(), fontCentext);
              cell=new Cell(pragraph);
              
              cell.setHorizontalAlignment(Element.ALIGN_RIGHT); //水平居中
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
              table.addCell(cell);
              pragraph = new Paragraph(itemDTO.getQty()==null?"":itemDTO.getQty().toString(), fontCentext);
              cell=new Cell(pragraph);
              
              cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
              table.addCell(cell);
              pragraph = new Paragraph(itemDTO.getTotalPrice()==null?"":itemDTO.getTotalPrice().toString(), fontCentext);
              cell=new Cell(pragraph);
              
              cell.setHorizontalAlignment(Element.ALIGN_RIGHT); //水平居中
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
              table.addCell(cell);
          }
          document.add(table);
          
          pragraph = new Paragraph("\n", font);
          pragraph.setAlignment(Element.ALIGN_CENTER);
          document.add(pragraph);
//-------------------------------------订单仓库----------------------------------------------//
          pragraph = new Paragraph("订单仓库", font);
          pragraph.setAlignment(Element.ALIGN_CENTER);
          document.add(pragraph);
//-------------------------------------仓库列表----------------------------------------------//            
          table=new Table(3);
          table.setWidths(new int[]{10,50,200});
          pragraph = new Paragraph("序号", fontCentext);
          cell=new Cell(pragraph);
          
          cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
          table.addCell(cell);
          pragraph = new Paragraph("仓库名称", fontCentext);
          cell=new Cell(pragraph);
         
          cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
          table.addCell(cell);
          pragraph = new Paragraph("仓库地址", fontCentext);
          cell=new Cell(pragraph);
          
          cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
          table.addCell(cell);
          List<WareHouseDTO> houseDTOs=pChaseOrderVO.getWareHouseDTOs();
          for(int j=0;j<houseDTOs.size();j++)
          {
        	  WareHouseDTO houseDTO=houseDTOs.get(j);
        	  pragraph = new Paragraph(String.valueOf(j+1), fontCentext);
              cell=new Cell(pragraph);
              
              cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
              table.addCell(cell);
          	pragraph = new Paragraph(houseDTO.getStoreName(), fontCentext);
              cell=new Cell(pragraph);
              
              cell.setHorizontalAlignment(Element.ALIGN_LEFT);//水平居中
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
              table.addCell(cell);
              
              pragraph = new Paragraph(houseDTO.getAddress(), fontCentext);
              cell=new Cell(pragraph);
              
              cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
              table.addCell(cell);
          }
          document.add(table);
		    document.close();
		    FileInputStream stream=new FileInputStream(file.getAbsolutePath()+File.separator+pChaseOrderVO.getBusinessNumberChar()+".pdf");
		    imgurl = UploadFileUtil.uploadFile(stream, "pdf", null);
			System.out.println("http://image01.zhongjumall.com/"+imgurl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	      
		return "http://image01.zhongjumall.com/"+imgurl;
	}
	
	private String WriteVirtualOrder(PChaseVirtualOrderVO pChaseOrderVO,HttpServletRequest request)
	{
		Rectangle rectPageSize = new Rectangle(PageSize.A4);// 定义A4页面大小
		Document document = new Document(rectPageSize, 10, 0, 10, 0);
		java.text.SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd"); 
		String imgurl="";
		try {
			BaseFont baseFont = BaseFont.createFont(WritePDF.class.getResource("/").getFile().toString()+File.separator+"SIMSUN.TTC,1",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
			Font fontTitle = new Font(baseFont);
			Font fontCentext = new Font(baseFont);
			Font font = new Font(baseFont);
			fontTitle.setSize(25);
			fontCentext.setSize(8);
			font.setSize(11);
			File file=new File(request.getSession().getServletContext().getRealPath("/")+File.separator+"pdf"+File.separator);
			if(!file.exists())
			{
				file.mkdirs();
			}
			OutputStream out=new FileOutputStream(file.getAbsolutePath()+File.separator+pChaseOrderVO.getBusinessNumberChar()+".pdf");
			PdfWriter.getInstance(document, out);
			document.open();
			
			Image image=Image.getInstance(request.getSession().getServletContext().getRealPath("/commons/images/")+File.separator+"pdfOrder.jpg");
			document.add(image);
			Paragraph pragraph = new Paragraph("众聚商城采购订单", fontTitle);
			pragraph.setAlignment(Paragraph.ALIGN_CENTER);
			pragraph.add(new Paragraph("   "));
			pragraph.add(new Paragraph("   "));
			document.add(pragraph);
			
			Table table = new Table(4);
			table.setBorder(0);
			//------------------------------------------------------------------------------------------//
			pragraph = new Paragraph("订货单号:\nOrder No.", fontCentext);
			Cell cell=new Cell(pragraph);
			cell.setBorder(0);
			//cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			table.addCell(cell);
			pragraph = new Paragraph(pChaseOrderVO.getBusinessNumberChar()==null?"":pChaseOrderVO.getBusinessNumberChar(), fontCentext);
			cell=new Cell(pragraph);
			cell.setBorder(0);
			//cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			table.addCell(cell);
			//------------------------------------------------------------------------------------------//
			
			//------------------------------------------------------------------------------------------//
			pragraph = new Paragraph("", fontCentext);
			cell=new Cell(pragraph);
			cell.setBorder(0);
			//cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			table.addCell(cell);
			pragraph = new Paragraph("", fontCentext);
			cell=new Cell(pragraph);
			cell.setBorder(0);
			//cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			table.addCell(cell);
			//------------------------------------------------------------------------------------------//
//------------------------------------------------------------------------------------------//           
			pragraph = new Paragraph("供应商名称:\nVendor Name", fontCentext);
			cell=new Cell(pragraph);
			cell.setBorder(0);
			//cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			table.addCell(cell);
			pragraph = new Paragraph(pChaseOrderVO.getSupplierName()==null?"":pChaseOrderVO.getSupplierName(), fontCentext);
			cell=new Cell(pragraph);
			cell.setBorder(0);
			//cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居
			table.addCell(cell);
			
			//------------------------------------------------------------------------------------------//            
			
			
			//------------------------------------------------------------------------------------------//
			pragraph = new Paragraph("供应商编码:\nVendor Code", fontCentext);
			cell=new Cell(pragraph);
			cell.setBorder(0);
			//cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			table.addCell(cell);
			pragraph = new Paragraph(pChaseOrderVO.getSupplierId()==null?"":pChaseOrderVO.getSupplierId().toString(), fontCentext);
			cell=new Cell(pragraph);
			cell.setBorder(0);
			//cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			table.addCell(cell);
			
			
			//------------------------------------------------------------------------------------------//
			pragraph = new Paragraph("创建日期:\nCreated Date.", fontCentext);
			cell=new Cell(pragraph);
			cell.setBorder(0);
			//cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			table.addCell(cell);
			pragraph = new Paragraph(pChaseOrderVO.getCreateTime()==null?"":format.format(pChaseOrderVO.getCreateTime()), fontCentext);
			cell=new Cell(pragraph);
			cell.setBorder(0);
			//cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			table.addCell(cell);
			//------------------------------------------------------------------------------------------//
			//------------------------------------------------------------------------------------------//
			pragraph = new Paragraph("订单状态:\nOrder Status.", fontCentext);
			cell=new Cell(pragraph);
			cell.setBorder(0);
			//cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			table.addCell(cell);
			pragraph = new Paragraph(pChaseOrderVO.getOrderStatus()==null?"":getOrderStatus(pChaseOrderVO.getOrderStatus()), fontCentext);
			cell=new Cell(pragraph);
			cell.setBorder(0);
			//cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			table.addCell(cell);
			//------------------------------------------------------------------------------------------//
			//------------------------------------------------------------------------------------------//
			pragraph = new Paragraph("订单总金额:\nOrder Gross Amount.", fontCentext);
			cell=new Cell(pragraph);
			cell.setBorder(0);
			//cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			table.addCell(cell);
			pragraph = new Paragraph(pChaseOrderVO.getItemTotalPrice()==null?"":pChaseOrderVO.getItemTotalPrice().toString(), fontCentext);
			cell=new Cell(pragraph);
			cell.setBorder(0);
			//cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			table.addCell(cell);
			//------------------------------------------------------------------------------------------//
			//------------------------------------------------------------------------------------------//
			pragraph = new Paragraph("送货有效期:\nDelivery Period of Validity.", fontCentext);
			cell=new Cell(pragraph);
			cell.setBorder(0);
			//cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			table.addCell(cell);
			pragraph = new Paragraph(pChaseOrderVO.getSendDate()==null?"":format.format(pChaseOrderVO.getSendDate()), fontCentext);
			cell=new Cell(pragraph);
			cell.setBorder(0);
			//cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			table.addCell(cell);
			//------------------------------------------------------------------------------------------//
			
			//------------------------------------------------------------------------------------------//
			pragraph = new Paragraph("备注:\nRemark.", fontCentext);
			cell=new Cell(pragraph);
			cell.setBorder(0);
			//cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			table.addCell(cell);
			pragraph = new Paragraph(pChaseOrderVO.getRemark()==null?"":pChaseOrderVO.getRemark(), fontCentext);
			cell=new Cell(pragraph);
			cell.setBorder(0);
			//cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			table.addCell(cell);
			//------------------------------------------------------------------------------------------//
			
			//------------------------------------------------------------------------------------------//
			pragraph = new Paragraph("", fontCentext);
			cell=new Cell(pragraph);
			cell.setBorder(0);
			//cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			table.addCell(cell);
			pragraph = new Paragraph("");
			cell=new Cell(pragraph);
			cell.setBorder(0);
			//cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			table.addCell(cell);
			//------------------------------------------------------------------------------------------//
			document.add(table);
			
//-----------------------------------------商品详情--------------------------------------------------------//       
			pragraph = new Paragraph("\n", font);
			pragraph.setAlignment(Element.ALIGN_CENTER);
			document.add(pragraph);
			pragraph = new Paragraph("商品详情", font);
			pragraph.setAlignment(Element.ALIGN_CENTER);
			document.add(pragraph);
			
//-----------------------------------------详情列表-------------------------------------------------------//            
			table=new Table(8);
			table.setWidths(new int[]{40,150,150,160,100,100,100,100});
			pragraph = new Paragraph("序号", fontCentext);
			cell=new Cell(pragraph);
			
			cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			table.addCell(cell);
			pragraph = new Paragraph("商品编码", fontCentext);
			cell=new Cell(pragraph);
			
			cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			table.addCell(cell);
			pragraph = new Paragraph("商品名称", fontCentext);
			cell=new Cell(pragraph);
			
			cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			table.addCell(cell);
			pragraph = new Paragraph("国际条形码", fontCentext);
			cell=new Cell(pragraph);
			
			cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			table.addCell(cell);
			pragraph = new Paragraph("箱规", fontCentext);
			cell=new Cell(pragraph);
			
			cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			table.addCell(cell);
			pragraph = new Paragraph("采购单价", fontCentext);
			cell=new Cell(pragraph);
			
			cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			table.addCell(cell);
			pragraph = new Paragraph("采购数量", fontCentext);
			cell=new Cell(pragraph);
			
			cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			table.addCell(cell);
			pragraph = new Paragraph("采购金额", fontCentext);
			cell=new Cell(pragraph);
			
			cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			table.addCell(cell);
			List<PChaseVirtualOrderItemDTO> itemDTOs=pChaseOrderVO.getpCOItemList();
			for(int i=0;i<itemDTOs.size();i++)
			{
				PChaseVirtualOrderItemDTO itemDTO=itemDTOs.get(i);
				pragraph = new Paragraph(String.valueOf(i+1), fontCentext);
				cell=new Cell(pragraph);
				
				cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
				table.addCell(cell);
				pragraph = new Paragraph(itemDTO.getBusinessProdid()==null?"":itemDTO.getBusinessProdid().toString(), fontCentext);
				cell=new Cell(pragraph);
				
				cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
				table.addCell(cell);
				pragraph = new Paragraph(itemDTO.getPname()==null?"":itemDTO.getPname(), fontCentext);
				cell=new Cell(pragraph);
				
				cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
				table.addCell(cell);
				pragraph = new Paragraph(itemDTO.getBarCode()==null?"":itemDTO.getBarCode(), fontCentext);
				cell=new Cell(pragraph);
				
				cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
				table.addCell(cell);
				pragraph = new Paragraph(itemDTO.getFormat()==null?"":itemDTO.getFormat(), fontCentext);
				cell=new Cell(pragraph);
				
				cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
				table.addCell(cell);
				pragraph = new Paragraph(itemDTO.getSkuPriceTax()==null?"":itemDTO.getSkuPriceTax().toString(), fontCentext);
				cell=new Cell(pragraph);
				
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT); //水平居中
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
				table.addCell(cell);
				pragraph = new Paragraph(itemDTO.getQty()==null?"":itemDTO.getQty().toString(), fontCentext);
				cell=new Cell(pragraph);
				
				cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
				table.addCell(cell);
				pragraph = new Paragraph(itemDTO.getTotalPrice()==null?"":itemDTO.getTotalPrice().toString(), fontCentext);
				cell=new Cell(pragraph);
				
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT); //水平居中
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
				table.addCell(cell);
			}
			document.add(table);
			
			pragraph = new Paragraph("\n", font);
			pragraph.setAlignment(Element.ALIGN_CENTER);
			document.add(pragraph);
//-------------------------------------订单仓库----------------------------------------------//
			pragraph = new Paragraph("订单仓库", font);
			pragraph.setAlignment(Element.ALIGN_CENTER);
			document.add(pragraph);
//-------------------------------------仓库列表----------------------------------------------//            
			table=new Table(3);
			table.setWidths(new int[]{10,50,200});
			pragraph = new Paragraph("序号", fontCentext);
			cell=new Cell(pragraph);
			
			cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			table.addCell(cell);
			pragraph = new Paragraph("仓库名称", fontCentext);
			cell=new Cell(pragraph);
			
			cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			table.addCell(cell);
			pragraph = new Paragraph("仓库地址", fontCentext);
			cell=new Cell(pragraph);
			
			cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			table.addCell(cell);
			List<WareHouseDTO> houseDTOs=pChaseOrderVO.getWareHouseDTOs();
			for(int j=0;j<houseDTOs.size();j++)
			{
				WareHouseDTO houseDTO=houseDTOs.get(j);
				pragraph = new Paragraph(String.valueOf(j+1), fontCentext);
				cell=new Cell(pragraph);
				
				cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
				table.addCell(cell);
				pragraph = new Paragraph(houseDTO.getStoreName(), fontCentext);
				cell=new Cell(pragraph);
				
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);//水平居中
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
				table.addCell(cell);
				
				pragraph = new Paragraph(houseDTO.getAddress(), fontCentext);
				cell=new Cell(pragraph);
				
				cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
				table.addCell(cell);
			}
			document.add(table);
			document.close();
			FileInputStream stream=new FileInputStream(file.getAbsolutePath()+File.separator+pChaseOrderVO.getBusinessNumberChar()+".pdf");
			imgurl = UploadFileUtil.uploadFile(stream, "pdf", null);
			System.out.println("http://image01.zhongjumall.com/"+imgurl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "http://image01.zhongjumall.com/"+imgurl;
	}

	private String WriteInOrder(List<StockInOrderDto> orderDtos,HttpServletRequest request) {
		Rectangle rectPageSize = new Rectangle(PageSize.A4);// 定义A4页面大小
		Document document = new Document(rectPageSize, 10, 0, 10, 0);
		String result="";
		java.text.SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			BaseFont baseFont = BaseFont.createFont(WritePDF.class.getResource("/").getFile().toString() + File.separator+ "SIMSUN.TTC,1", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			Font fontTitle = new Font(baseFont);
			Font fontCentext = new Font(baseFont);
			Font font = new Font(baseFont);
			fontTitle.setSize(25);
			fontCentext.setSize(8);
			font.setSize(11);
			File file = new File(request.getSession().getServletContext().getRealPath("/")+ File.separator + "pdf" + File.separator);
			if (!file.exists()) {
				file.mkdirs();
			}
			result=orderDtos.get(0).getId() + "-"+ orderDtos.get(orderDtos.size() - 1).getId() + ".pdf";
			OutputStream out = new FileOutputStream(file.getAbsolutePath() + File.separator + orderDtos.get(0).getId() + "-"+ orderDtos.get(orderDtos.size() - 1).getId() + ".pdf");
			PdfWriter.getInstance(document, out);
			document.open();
			for(StockInOrderDto orderDto:orderDtos)
			{
				Image image=Image.getInstance(request.getSession().getServletContext().getRealPath("/commons/images/")+File.separator+"pdfOrder.jpg");
				document.add(image);
				Paragraph pragraph = new Paragraph("入库单", fontTitle);
				pragraph.setAlignment(Paragraph.ALIGN_CENTER);
				pragraph.add(new Paragraph("   "));
				pragraph.add(new Paragraph("   "));
				document.add(pragraph);
				Table table = new Table(4);
		        table.setBorder(0);
		        

		//------------------------------------------------------------------------------------------//
		          pragraph = new Paragraph("入库单编号", fontCentext);
		          Cell cell=new Cell(pragraph);
		          cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		          table.addCell(cell);
		          pragraph = new Paragraph(orderDto.getStockInChar()==null?"":orderDto.getStockInChar(), fontCentext);
		          cell=new Cell(pragraph);
		          cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		          table.addCell(cell);
		          pragraph = new Paragraph("入库通知单编号", fontCentext);
		          cell=new Cell(pragraph);
		          cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		          table.addCell(cell);
		          pragraph = new Paragraph(orderDto.getNotificationIdChar()==null?"":orderDto.getNotificationIdChar(), fontCentext);
		          cell=new Cell(pragraph);
		          cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居
		          table.addCell(cell);
		          pragraph = new Paragraph("入库日期", fontCentext);
		          cell=new Cell(pragraph);
		          cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		          table.addCell(cell);
		          pragraph = new Paragraph(orderDto.getCreateTime()==null?"":format.format(orderDto.getCreateTime()), fontCentext);
		          cell=new Cell(pragraph);
		          cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居
		          table.addCell(cell);
		          pragraph = new Paragraph("库管员", fontCentext);
		          cell=new Cell(pragraph);
		          cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		          table.addCell(cell);
		          if(orderDto.getCreateBy()!=null&&!"".equals(orderDto.getCreateBy()))
		          {
		        	  pragraph = new Paragraph(orderDto.getCreateBy(), fontCentext);
		          }else{
		        	  if(request.getAttribute("loginUser")!=null){
			        	  
			        	  PlatformUser user= (PlatformUser) request.getAttribute("loginUser");
			        	  pragraph = new Paragraph(user.getName(), fontCentext);
			          }else{
			        	  pragraph = new Paragraph("", fontCentext);
			          }
		          }
		          cell=new Cell(pragraph);
		          cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居
		          table.addCell(cell);
		          pragraph = new Paragraph("物流通知单编号", fontCentext);
		          cell=new Cell(pragraph);
		          cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		          table.addCell(cell);
		          pragraph = new Paragraph(orderDto.getShipIdChar()==null?"":orderDto.getShipIdChar(), fontCentext);
		          cell=new Cell(pragraph);
		          cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居
		          table.addCell(cell);
		          pragraph = new Paragraph("库房名称", fontCentext);
		          cell=new Cell(pragraph);
		          cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		          table.addCell(cell);
		          pragraph = new Paragraph(orderDto.getWarehouseName()==null?"":orderDto.getWarehouseName(), fontCentext);
		          cell=new Cell(pragraph);
		          cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居
		          table.addCell(cell);
		          document.add(table);
		          pragraph = new Paragraph("\n", font);
		          pragraph.setAlignment(Element.ALIGN_CENTER);
		          document.add(pragraph);
		          pragraph = new Paragraph("商品详情", font);
		          pragraph.setAlignment(Element.ALIGN_CENTER);
		          document.add(pragraph);
		          table=new Table(10);
		          table.setWidths(new int[]{40,150,150,160,100,100,100,100,100,100});
		          pragraph = new Paragraph("序号", fontCentext);
		          cell=new Cell(pragraph);
		          
		          cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		          table.addCell(cell);
		          pragraph = new Paragraph("商品编码", fontCentext);
		          cell=new Cell(pragraph);
		          
		          cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		          table.addCell(cell);
		          pragraph = new Paragraph("商品名称", fontCentext);
		          cell=new Cell(pragraph);
		          
		          cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		          table.addCell(cell);
		          pragraph = new Paragraph("国际条形码", fontCentext);
		          cell=new Cell(pragraph);
		         
		          cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		          table.addCell(cell);
		          pragraph = new Paragraph("规格", fontCentext);
		          cell=new Cell(pragraph);
		          
		          cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		          table.addCell(cell);
		          pragraph = new Paragraph("单位", fontCentext);
		          cell=new Cell(pragraph);
		          
		          cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		          table.addCell(cell);
		          pragraph = new Paragraph("通知数量", fontCentext);
		          cell=new Cell(pragraph);
		          
		          cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		          table.addCell(cell);
		          pragraph = new Paragraph("已入库数量", fontCentext);
		          cell=new Cell(pragraph);
		          
		          cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		          table.addCell(cell);
		          pragraph = new Paragraph("本次入库数量", fontCentext);
		          cell=new Cell(pragraph);
		          cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		          table.addCell(cell);
		          pragraph = new Paragraph("批次", fontCentext);
		          cell=new Cell(pragraph);
		          cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		          cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		          table.addCell(cell);
		          List<StockInOrderItemDto> itemDTOs=orderDto.getInOrderItemDtos();
		          for(int j=0;j<itemDTOs.size();j++)
		          {
		        	  StockInOrderItemDto itemDto= itemDTOs.get(j);
		        	  pragraph = new Paragraph(String.valueOf(j+1), fontCentext);
		              cell=new Cell(pragraph);
		              
		              cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		              table.addCell(cell);
		              pragraph = new Paragraph(itemDto.getPcode()==null?"":itemDto.getPcode(), fontCentext);
		              cell=new Cell(pragraph);
		              
		              cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
		              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		              table.addCell(cell);
		              pragraph = new Paragraph(itemDto.getSkuNameCn()==null?"":itemDto.getSkuNameCn(), fontCentext);
		              cell=new Cell(pragraph);
		              
		              cell.setHorizontalAlignment(Element.ALIGN_LEFT);//水平居中
		              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		              table.addCell(cell);
		              pragraph = new Paragraph(itemDto.getBarCode()==null?"":itemDto.getBarCode(), fontCentext);
		              cell=new Cell(pragraph);
		              
		              cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
		              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		              table.addCell(cell);
		              pragraph = new Paragraph(itemDto.getFormat()==null?"":itemDto.getFormat(), fontCentext);
		              cell=new Cell(pragraph);
		              
		              cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
		              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		              table.addCell(cell);
		              pragraph = new Paragraph(itemDto.getUnit()==null?"":itemDto.getUnit(), fontCentext);
		              cell=new Cell(pragraph);
		              
		              cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
		              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		              table.addCell(cell);
		              pragraph = new Paragraph(itemDto.getQty()==null?"":itemDto.getQty().toString(), fontCentext);
		              cell=new Cell(pragraph);
		              
		              cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		              table.addCell(cell);
		              pragraph = new Paragraph(itemDto.getQtyReceived()==null?"":itemDto.getQtyReceived().toString(), fontCentext);
		              cell=new Cell(pragraph);
		              
		              cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		              table.addCell(cell);
		              pragraph = new Paragraph(itemDto.getQtyStorage()==null?"":itemDto.getQtyStorage().toString(), fontCentext);
		              cell=new Cell(pragraph);
		              
		              cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		              table.addCell(cell);
		              pragraph = new Paragraph(itemDto.getBatchNumber()==null?"":itemDto.getBatchNumber(), fontCentext);
		              cell=new Cell(pragraph);
		              
		              cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		              cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		              table.addCell(cell);
		              
		          }
		          document.add(table);
		          document.newPage();
			}
			document.close();
			FileInputStream stream=new FileInputStream(file.getAbsolutePath() + File.separator + orderDtos.get(0).getId() + "-"+ orderDtos.get(orderDtos.size() - 1).getId() + ".pdf");
			result = UploadFileUtil.uploadFile(stream, "pdf", null);
			System.out.println("http://image01.zhongjumall.com/"+result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "http://image01.zhongjumall.com/"+result;
	}
	private String WriteOutOrder(List<StockOutOrderDto> orderDtos,HttpServletRequest request) {
		Rectangle rectPageSize = new Rectangle(PageSize.A4);// 定义A4页面大小
		Document document = new Document(rectPageSize, 10, 0, 10, 0);
		String result="";
		java.text.SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			BaseFont baseFont = BaseFont.createFont(WritePDF.class.getResource("/").getFile().toString() + File.separator+ "SIMSUN.TTC,1", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			Font fontTitle = new Font(baseFont);
			Font fontCentext = new Font(baseFont);
			Font font = new Font(baseFont);
			fontTitle.setSize(25);
			fontCentext.setSize(8);
			font.setSize(11);
			File file = new File(request.getSession().getServletContext().getRealPath("/")+ File.separator + "pdf" + File.separator);
			if (!file.exists()) {
				file.mkdirs();
			}
			result=orderDtos.get(0).getSid() + "-"+ orderDtos.get(orderDtos.size() - 1).getSid() + ".pdf";
			OutputStream out = new FileOutputStream(file.getAbsolutePath() + File.separator + orderDtos.get(0).getSid() + "-"+ orderDtos.get(orderDtos.size() - 1).getSid() + ".pdf");
			PdfWriter.getInstance(document, out);
			document.open();
			for(StockOutOrderDto orderDto:orderDtos)
			{
				Image image=Image.getInstance(request.getSession().getServletContext().getRealPath("/commons/images/")+File.separator+"pdfOrder.jpg");
				document.add(image);
				Paragraph pragraph = new Paragraph("通知发货单", fontTitle);
				pragraph.setAlignment(Paragraph.ALIGN_CENTER);
				pragraph.add(new Paragraph("   "));
				pragraph.add(new Paragraph("   "));
				document.add(pragraph);
				Table table = new Table(4);
		        table.setBorder(0);
		        pragraph = new Paragraph("发货单编号", fontCentext);
		        Cell cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph(orderDto.getStockOutChar()==null?"":orderDto.getStockOutChar(), fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph("发货时间", fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph(orderDto.getOutTime()==null?"":format.format(orderDto.getOutTime()), fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph("快递商名称", fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph(orderDto.getExpressName()==null?"":orderDto.getExpressName(), fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph("快递单号", fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph(orderDto.getExpressNumber()==null?"":orderDto.getExpressNumber().toString(), fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph("出库单编号", fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph("", fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph("出库时间", fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph(orderDto.getOrderTime()==null?"":format.format(orderDto.getOrderTime()), fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph("订单编号", fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph(orderDto.getOrderId()==null?"":orderDto.getOrderId().toString(), fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph("出库通知单编号", fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph(orderDto.getNotificationIdChar()==null?"":orderDto.getNotificationIdChar(), fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph("收件人", fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph(orderDto.getReceiveName()==null?"":orderDto.getReceiveName(), fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph("电话", fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph(orderDto.getReceivePhone()==null?"":orderDto.getReceivePhone(), fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph("地址", fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph(orderDto.getReceiveAddress()==null?"":orderDto.getReceiveAddress(), fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph("邮政编码", fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph(orderDto.getZipcode()==null?"":orderDto.getZipcode(), fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph("快递重量", fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph(orderDto.getWeight()==null?"":orderDto.getWeight().toString(), fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph("", fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph("", fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        document.add(table);
		        pragraph = new Paragraph("\n", font);
		        pragraph.setAlignment(Element.ALIGN_CENTER);
		        document.add(pragraph);
		        table=new Table(2);
		        table.setBorder(0);
		        
		        pragraph = new Paragraph("发货商品明细", fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        String Total=orderDto.getTotalPrice()==null?"":orderDto.getTotalPrice().toString();
		        pragraph = new Paragraph("金额合计:"+Total+"元", fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        document.add(table);
		        table=new Table(9);
		        table.setWidths(new int[]{40,150,150,160,100,100,100,100,100});
		        pragraph = new Paragraph("序号", fontCentext);
		        cell=new Cell(pragraph);
		          
		        cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph("商品编码", fontCentext);
		        cell=new Cell(pragraph);
		          
		        cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph("商品名称", fontCentext);
		        cell=new Cell(pragraph);
		          
		        cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph("国际条形码", fontCentext);
		        cell=new Cell(pragraph);
		         
		        cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph("规格", fontCentext);
		        cell=new Cell(pragraph);
		          
		        cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph("单位", fontCentext);
		        cell=new Cell(pragraph);
		          
		        cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph("本次发货数量", fontCentext);
		        cell=new Cell(pragraph);
		          
		        cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph("销售单价", fontCentext);
		        cell=new Cell(pragraph);
		          
		        cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph("销售金额", fontCentext);
		        cell=new Cell(pragraph);
		          
		        cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        List<StockOutOrderItemDto> itemDtos=orderDto.getItemDtos();
		        for(int j=0;j<itemDtos.size();j++)
		        {
		        	StockOutOrderItemDto itemDto=itemDtos.get(j);
		        	pragraph = new Paragraph(String.valueOf(j+1), fontCentext);
			        cell=new Cell(pragraph);
			          
			        cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
			        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			        table.addCell(cell);
			        pragraph = new Paragraph(itemDto.getPcode()==null?"":itemDto.getPcode(), fontCentext);
			        cell=new Cell(pragraph);
			          
			        cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
			        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			        table.addCell(cell);
			        pragraph = new Paragraph(itemDto.getPname()==null?"":itemDto.getPname(), fontCentext);
			        cell=new Cell(pragraph);
			          
			        cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
			        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			        table.addCell(cell);
			        pragraph = new Paragraph(itemDto.getBarCode()==null?"":itemDto.getBarCode(), fontCentext);
			        cell=new Cell(pragraph);
			          
			        cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
			        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			        table.addCell(cell);
			        pragraph = new Paragraph(itemDto.getFormat()==null?"":itemDto.getFormat(), fontCentext);
			        cell=new Cell(pragraph);
			          
			        cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
			        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			        table.addCell(cell);
			        pragraph = new Paragraph(itemDto.getUnit()==null?"":itemDto.getUnit(), fontCentext);
			        cell=new Cell(pragraph);
			          
			        cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
			        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			        table.addCell(cell);
			        pragraph = new Paragraph(itemDto.getLocalQty()==null?"":itemDto.getLocalQty().toString(), fontCentext);
			        cell=new Cell(pragraph);
			          
			        cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
			        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			        table.addCell(cell);
			        pragraph = new Paragraph(itemDto.getPrice()==null?"":itemDto.getPrice().toString(), fontCentext);
			        cell=new Cell(pragraph);
			          
			        cell.setHorizontalAlignment(Element.ALIGN_RIGHT); //水平居中
			        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			        table.addCell(cell);
			        pragraph = new Paragraph(itemDto.getTotalPrice()==null?"":itemDto.getTotalPrice().toString(), fontCentext);
			        cell=new Cell(pragraph);
			          
			        cell.setHorizontalAlignment(Element.ALIGN_RIGHT); //水平居中
			        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			        table.addCell(cell);
		        }
		        document.add(table);
		        table=new Table(1);
		        table.setBorder(0);
		        String createBy="制单人 ";
		        if(orderDto.getCreateBy()!=null&&!"".equals(orderDto.getCreateBy()))
		        {
		        	createBy=createBy+orderDto.getCreateBy();
		        }else{
		        	if(request.getAttribute("loginUser")!=null){
			        	  
			        	  PlatformUser user= (PlatformUser) request.getAttribute("loginUser");
			        	  createBy = createBy+user.getName();
			          }else{
			        	  createBy = createBy+"";
			          }
		        }
		        
		        pragraph = new Paragraph(createBy, fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        document.add(table);
		        document.newPage();
			}
			document.close();
			FileInputStream stream=new FileInputStream(file.getAbsolutePath() + File.separator + orderDtos.get(0).getSid() + "-"+ orderDtos.get(orderDtos.size() - 1).getSid() + ".pdf");
			result = UploadFileUtil.uploadFile(stream, "pdf", null);
			System.out.println("http://image01.zhongjumall.com/"+result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "http://image01.zhongjumall.com/"+result;
	}
	private String WriteNoOutOrder(List<NotificationOutOrderDTO> orderDtos,
			HttpServletRequest request) {
		Rectangle rectPageSize = new Rectangle(PageSize.A4);// 定义A4页面大小
		WarehouseService warehouseService=(WarehouseService)RemoteServiceSingleton.getInstance().getAppService("warehouseService");
		Document document = new Document(rectPageSize, 10, 0, 10, 0);
		String result="";
		java.text.SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			BaseFont baseFont = BaseFont.createFont(WritePDF.class.getResource("/").getFile().toString() + File.separator+ "SIMSUN.TTC,1", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			Font fontTitle = new Font(baseFont);
			Font fontCentext = new Font(baseFont);
			Font font = new Font(baseFont);
			fontTitle.setSize(25);
			fontCentext.setSize(8);
			font.setSize(11);
			File file = new File(request.getSession().getServletContext().getRealPath("/")+ File.separator + "pdf" + File.separator);
			if (!file.exists()) {
				file.mkdirs();
			}
			result=orderDtos.get(0).getNotificationOutOrder().getSid() + "-"+ orderDtos.get(orderDtos.size() - 1).getNotificationOutOrder().getSid() + ".pdf";
			OutputStream out = new FileOutputStream(file.getAbsolutePath() + File.separator + orderDtos.get(0).getNotificationOutOrder().getSid() + "-"+ orderDtos.get(orderDtos.size() - 1).getNotificationOutOrder().getSid() + ".pdf");
			PdfWriter.getInstance(document, out);
			document.open();
			for(NotificationOutOrderDTO orderDto:orderDtos)
			{
				Image image=Image.getInstance(request.getSession().getServletContext().getRealPath("/commons/images/")+File.separator+"pdfOrder.jpg");
				document.add(image);
				Paragraph pragraph = new Paragraph("通知出库单", fontTitle);
				pragraph.setAlignment(Paragraph.ALIGN_CENTER);
				pragraph.add(new Paragraph("   "));
				pragraph.add(new Paragraph("   "));
				document.add(pragraph);
				Table table = new Table(6);
		        table.setBorder(0);
		        pragraph = new Paragraph("出库单编号", fontCentext);
		        Cell cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph(orderDto.getNotificationOutOrder().getNotificationOutChar()==null?"":orderDto.getNotificationOutOrder().getNotificationOutChar(), fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph("出库时间", fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph(orderDto.getNotificationOutOrder().getOutTime()==null?"":format.format(orderDto.getNotificationOutOrder().getOutTime()), fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph("出库类型", fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        String type="";
		        if(orderDto.getNotificationOutOrder().getBusinessType().equals("201"))
		        {
		        	type="B2B销售出库";
		        }
		        if(orderDto.getNotificationOutOrder().getBusinessType().equals("202"))
		        {
		        	type="B2C销售出库";
		        }
		        if(orderDto.getNotificationOutOrder().getBusinessType().equals("203"))
		        {
		        	type="换货出库";
		        }
		        pragraph = new Paragraph(type, fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph("订单编号", fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph(orderDto.getNotificationOutOrder().getOrderNumber()==null?"":orderDto.getNotificationOutOrder().getOrderNumber().toString(), fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph("出库通知单编号", fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph(orderDto.getNotificationOutOrder().getSid()==null?"":orderDto.getNotificationOutOrder().getSid().toString(), fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph("出库库房", fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        Warehouse warehouse=warehouseService.findWarehouseByCode(orderDto.getNotificationOutOrder().getWarehouseCode().intValue());
		        pragraph = new Paragraph(warehouse==null?"":warehouse.getWarehouseName(), fontCentext);
		        cell=new Cell(pragraph);
		        cell.setBorder(0);
		          //cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        document.add(table);
		        pragraph = new Paragraph("\n", font);
		        pragraph.setAlignment(Element.ALIGN_CENTER);
		        document.add(pragraph);
		        pragraph = new Paragraph("出库商品明细", font);
		        pragraph.setAlignment(Element.ALIGN_CENTER);
		        document.add(pragraph);
		        table=new Table(8);
		        table.setWidths(new int[]{40,150,150,160,100,100,100,100});
		        pragraph = new Paragraph("序号", fontCentext);
		        cell=new Cell(pragraph);
		          
		        cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph("商品编码", fontCentext);
		        cell=new Cell(pragraph);
		          
		        cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph("商品名称", fontCentext);
		        cell=new Cell(pragraph);
		          
		        cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph("国际条形码", fontCentext);
		        cell=new Cell(pragraph);
		         
		        cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph("规格", fontCentext);
		        cell=new Cell(pragraph);
		          
		        cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph("单位", fontCentext);
		        cell=new Cell(pragraph);
		          
		        cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph("本次出库数量", fontCentext);
		        cell=new Cell(pragraph);
		          
		        cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        pragraph = new Paragraph("批次", fontCentext);
		        cell=new Cell(pragraph);
		          
		        cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
		        table.addCell(cell);
		        List<NotificationOutItem> items=orderDto.getNotificationOutItemList();
		        for(int j=0;j<items.size();j++)
		        {
		        	NotificationOutItem item=items.get(j);
		        	pragraph = new Paragraph(String.valueOf(j+1), fontCentext);
			        cell=new Cell(pragraph);
			          
			        cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
			        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			        table.addCell(cell);
			        pragraph = new Paragraph(item.getPcode()==null?"":item.getPcode(), fontCentext);
			        cell=new Cell(pragraph);
			          
			        cell.setHorizontalAlignment(Element.ALIGN_LEFT);//水平居中
			        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			        table.addCell(cell);
			        pragraph = new Paragraph(item.getPname()==null?"":item.getPname(), fontCentext);
			        cell=new Cell(pragraph);
			          
			        cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
			        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			        table.addCell(cell);
			        pragraph = new Paragraph(item.getBarCode()==null?"":item.getBarCode(), fontCentext);
			        cell=new Cell(pragraph);
			          
			        cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
			        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			        table.addCell(cell);
			        pragraph = new Paragraph(item.getFormat()==null?"":item.getFormat(), fontCentext);
			        cell=new Cell(pragraph);
			          
			        cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
			        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			        table.addCell(cell);
			        pragraph = new Paragraph(item.getUnit()==null?"":item.getUnit(), fontCentext);
			        cell=new Cell(pragraph);
			          
			        cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
			        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			        table.addCell(cell);
			        pragraph = new Paragraph(item.getLocalQty()==null?"":item.getLocalQty().toString(), fontCentext);
			        cell=new Cell(pragraph);
			          
			        cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
			        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			        table.addCell(cell);
			        pragraph = new Paragraph(item.getBatchNumber()==null?"":item.getBatchNumber(), fontCentext);
			        cell=new Cell(pragraph);
			          
			        cell.setHorizontalAlignment(Element.ALIGN_LEFT); //水平居中
			        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
			        table.addCell(cell);
		        }
		        document.add(table);
				document.newPage();
			}
			document.close();
			FileInputStream stream=new FileInputStream(file.getAbsolutePath() + File.separator + orderDtos.get(0).getNotificationOutOrder().getSid() + "-"+ orderDtos.get(orderDtos.size() - 1).getNotificationOutOrder().getSid() + ".pdf");
			result = UploadFileUtil.uploadFile(stream, "pdf", null);
			System.out.println("http://image01.zhongjumall.com/"+result);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "http://image01.zhongjumall.com/"+result;
	}
	public static void main(String[] args) throws Exception {
		OutputStream stream=new FileOutputStream("d:/wKgBO1WToRiAaO9CAAdmM2yzBe8539.doc");
		URL url = new URL("http://image01.zhongjumall.com/group1/M00/02/E0/wKgBO1WToRiAaO9CAAdmM2yzBe8539.doc");  
        HttpURLConnection uc = (HttpURLConnection) url.openConnection();  
        uc.setDoInput(true);//设置是否要从 URL 连接读取数据,默认为true  
        uc.connect();  
		stream.write(new String(UploadFileUtil.DownloadFile("group1/M00/02/E0/wKgBO1WToRiAaO9CAAdmM2yzBe8539.doc").getBytes("GB2312"),"ISO-8859-1").getBytes());
		stream.close();
		HWPFDocument wordDocument = new HWPFDocument(uc.getInputStream());//WordToHtmlUtils.loadDoc(new FileInputStream(inputFile));  
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(  
                DocumentBuilderFactory.newInstance().newDocumentBuilder()  
                        .newDocument());   
        wordToHtmlConverter.processDocument(wordDocument);  
        //save pictures    
        org.w3c.dom.Document htmlDocument = wordToHtmlConverter.getDocument();  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        DOMSource domSource = new DOMSource(htmlDocument);  
        StreamResult streamResult = new StreamResult(out);  
  
        TransformerFactory tf = TransformerFactory.newInstance();  
        Transformer serializer = tf.newTransformer();  
        serializer.setOutputProperty(OutputKeys.ENCODING, "UTF8");  
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");  
        serializer.setOutputProperty(OutputKeys.METHOD, "html");  
        serializer.transform(domSource, streamResult);  
        out.close();  
        writeFile(new String(out.toByteArray()), "d:/wKgBO1WToRiAaO9CAAdmM2yzBe8539.html");  
		
	}
	private static void writeFile(String content, String path) {  
        FileOutputStream fos = null;  
        BufferedWriter bw = null;  
        try {  
            File file = new File(path);  
            fos = new FileOutputStream(file);  
            bw = new BufferedWriter(new OutputStreamWriter(fos,"UTF8"));  
            bw.write(content);  
        } catch (FileNotFoundException fnfe) {  
            fnfe.printStackTrace();  
        } catch (IOException ioe) {  
            ioe.printStackTrace();  
        } finally {  
            try {  
                if (bw != null)  
                    bw.close();  
                if (fos != null)  
                    fos.close();  
            } catch (IOException ie) {  
            }  
        }  
    }
}
*/