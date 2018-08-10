package com.mall.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.csource.upload.UploadFileUtil;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

//import com.mall.supplier.order.dto.NotificationOutOrderDTO;
//import com.mall.supplier.order.dto.PChaseOrderVO;
//import com.mall.supplier.order.dto.StockInOrderDto;
//import com.mall.supplier.order.dto.StockOutOrderDto;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

public class OnLineTools {
	public static final String WORD="WORD";
	public static final String EXCEL="EXCEL";
	public static final String IMG="IMG";
	public static final String PDF="PDF";
	public String getInstance(String type,String url, String outPutFile)
	{
		String result="";
		if(type.equals(WORD))
		{
			result=convert2Html(url,outPutFile);
		}
		if(type.equals(EXCEL))
		{
			result=convertExcel2Html(url,outPutFile);
		}
		if(type.equals(IMG))
		{
			result=convertImg2Html(url,outPutFile);
		}
		if(type.equals(PDF))
		{
			result=convertPdf2Html(url,outPutFile);
		}
		return result;
	}
	private String convertImg2Html(String fileName, String outPutFile) {
		String html="<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"><title>"+fileName.substring(0, fileName.lastIndexOf("/")+1)+"</title></head><body><img alt=\""+fileName.substring(0, fileName.lastIndexOf("/")+1)+"\" src=\""+fileName+"\"></body></html>";
		return html;
		
		
	}
	private String convertPdf2Html(String fileName, String outPutFile) {
		String html="<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"><title></title></head><body><iframe width=\"100%\" height=\"100%\" frameborder=\"no\"  src=\""+fileName+"\"></iframe></html>";
		return html;
	}
	private  String convert2Html(String fileName, String outPutFile){ 
		String result="";
		try {
			URL url = new URL(fileName);  
	        HttpURLConnection uc = (HttpURLConnection) url.openConnection();  
	        uc.setDoInput(true);//设置是否要从 URL 连接读取数据,默认为true  
	        uc.connect();
			HWPFDocument wordDocument = new HWPFDocument(uc.getInputStream());//WordToHtmlUtils.loadDoc(new FileInputStream(inputFile));  
	        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(  
	                DocumentBuilderFactory.newInstance().newDocumentBuilder()  
	                        .newDocument());   
	        wordToHtmlConverter.processDocument(wordDocument);  
	        //save pictures    
	        Document htmlDocument = wordToHtmlConverter.getDocument();  
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
	        result=new String(out.toByteArray(),"UTF-8");  
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
        
    }

	private String convertExcel2Html(String excelFilePath, String htmlFilePath) {
		String result="";
		try {
			URL url = new URL(excelFilePath);  
	        HttpURLConnection uc = (HttpURLConnection) url.openConnection();  
	        uc.setDoInput(true);//设置是否要从 URL 连接读取数据,默认为true  
	        uc.connect();
				HSSFWorkbook workBook = new HSSFWorkbook(uc.getInputStream());
				ExcelToHtmlConverter converter = new ExcelToHtmlConverter(
						DocumentBuilderFactory.newInstance()
								.newDocumentBuilder().newDocument());
				converter.setOutputColumnHeaders(false);
				converter.setOutputRowNumbers(false);
				converter.processWorkbook(workBook);
				Document htmlDocument = converter.getDocument();
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
				result=new String(out.toByteArray(),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}
	private void writeFile(String content, String path) {  
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
	public static void main(String[] args) {
		OnLineTools tools=new OnLineTools();
		String url="http://image01.zhongjumall.com/group1/M00/02/E0/wKgBO1WToRiAaO9CAAdmM2yzBe8539.doc";
		System.out.println(tools.getInstance(OnLineTools.WORD, url, "d:/pdf/"+url.substring(url.lastIndexOf("/")+1, url.length())));
	}
}
