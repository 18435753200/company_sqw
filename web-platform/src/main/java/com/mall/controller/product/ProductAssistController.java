package com.mall.controller.product;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rubyeye.xmemcached.MemcachedClient;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.csource.common.MyException;
import org.csource.upload.UploadFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.baidu.ueditor.ActionEnter;
import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.authority.client.util.UserUtil;
import com.mall.category.po.TdCatePub;
import com.mall.dealer.product.api.DealerProductSkuService;
import com.mall.dealer.product.dto.DealerProductSelectConDTO;
import com.mall.dealer.product.dto.DealerProductTabExportDTO;
import com.mall.dealer.product.dto.SKUAuditExportDTO;
import com.mall.dealer.product.po.PurchasePricePO;
import com.mall.mybatis.utility.PageBean;
import com.mall.platform.model.PlatformUser;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Common;
import com.mall.utils.Constants;
import com.mall.utils.FormatBigDecimal;
import com.mall.utils.ValidateTool;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/product")
public class ProductAssistController extends BaseController {

	/**
	 * LOGGER.
	 */
	private static final Log LOGGER = LogFactory
			.getLogger(ProductAssistController.class);

	/**
	 * Sping注入mencached缓存客户端.
	 */
	@Autowired
	private MemcachedClient memCachedClient;

	
	@RequestMapping(value = "/downB2bProductExcel")
    public void downB2bProduct(HttpServletResponse response, String cate,String b2bType,String startTime,String endTime,PageBean<DealerProductTabExportDTO> pageBean,DealerProductSelectConDTO dealerProductSelectConDTO) {
	    if(StringUtils.isNotBlank(cate)){
    	    List<String> cateDispIds = new ArrayList<String>();
    	    cateDispIds.add(cate);
            dealerProductSelectConDTO.setCateDispIds(cateDispIds);
	    }
	    if(!Common.isEmpty(startTime)){
			dealerProductSelectConDTO.setStartDate(Common.stringToDate(startTime.replace("-", "/"), "yyyy/MM/dd HH:mm:ss"));
		}
		if(!Common.isEmpty(endTime)){
			dealerProductSelectConDTO.setEndDate(Common.stringToDate(endTime.replace("-", "/"), "yyyy/MM/dd HH:mm:ss"));
		}
	    pageBean.setPageSize(999999);
	    pageBean.setParameter(dealerProductSelectConDTO);
	    if("1".equals(b2bType)){//B2C导出
	        pageBean = RemoteServiceSingleton.getInstance().getDealerProductExportExcelService().findB2CProTabList(pageBean);
	    }else{// B2B导出
	        pageBean = RemoteServiceSingleton.getInstance().getDealerProductExportExcelService().findB2BProTabList(pageBean);
	    } 
	    DealerProductSkuService dealerProductSkuService = RemoteServiceSingleton.getInstance().getDealerProductSkuService();
	    if (null != pageBean && null != pageBean.getResult()) {
	        try {
                OutputStream os = null;
                List<DealerProductTabExportDTO> list = pageBean.getResult();
                HSSFWorkbook workbook = new HSSFWorkbook();// 声明一个工作薄
                HSSFSheet sheet = workbook.createSheet("productList");// 生成一个表格
                HSSFRow row = sheet.createRow(0);
                
                HSSFCellStyle style = workbook.createCellStyle();
                style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
                style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                style.setFillForegroundColor(HSSFColor.YELLOW.index);
                HSSFCell cell = null;
                String strtitle ;
                if("1".equals(b2bType)){//B2C导出
                    strtitle =  "发布日期,商品品类,商品编码,商品ID,商品名称,规格名称,条形码,箱规,销售价(￥),建议零售价(￥),原产国,制造商,供应商,保质期,商品货号,翼支付价(￥),市场价(￥),SKU_ID,商品成本价";
                }else{// B2B导出
                    strtitle =  "发布日期,商品品类,商品编码,商品ID,商品名称,规格名称,条形码,箱规,预订价(￥),批发价(￥),建议零售价(￥),现货最小起订量,期货最小起订量,原产国,制造商,供应商,保质期,SKU_ID,商品成本价";
                } 

                sheet.setDefaultColumnWidth(20);
                String[] split = strtitle.split(",");
                for (int i = 0; i < split.length; i++) {
                    cell = row.createCell(i);
                    cell.setCellValue(split[i]);
                    cell.setCellStyle(style);
                }
                for (int i = 0; i < list.size(); i++) {
                    HSSFCellStyle styleRow = null;
                    if (i % 2 == 0) {
                        styleRow = workbook.createCellStyle();
                        styleRow.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
                        styleRow.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                        styleRow.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
                    } else {
                        styleRow = workbook.createCellStyle();
                        styleRow.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
                        styleRow.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                        styleRow.setFillForegroundColor(HSSFColor.WHITE.index);
                    }
                    int lastRowIndex = sheet.getLastRowNum();
                    DealerProductTabExportDTO exportDTO = list.get(i);
                    List<SKUAuditExportDTO> skuList = exportDTO.getSkuList();
                    if (skuList != null && skuList.size() > 0) {
                        int skuLength = skuList.size();
                        for (int j = 0; j < skuLength; j++) {
                            short cellNum = Constants.SHORT0;
                            SKUAuditExportDTO skuDto = skuList.get(j);
                            row = sheet.createRow(j + lastRowIndex + 1);
                            this.geneCell(row, cellNum++,exportDTO.getStrCreatedate(), styleRow);
                            this.geneCell(row, cellNum++,exportDTO.getCatePubName(), styleRow);
                            this.geneCell(row, cellNum++,exportDTO.getBusinessProdId(), styleRow);
                            this.geneCell(row, cellNum++,exportDTO.getProductId().toString(), styleRow);
                            this.geneCell(row, cellNum++,exportDTO.getProductName(), styleRow);
                            this.geneCell(row, cellNum++,skuDto.getSkuNameCN(), styleRow);
                            this.geneCell(row, cellNum++,skuDto.getSkuCode(), styleRow);
                            if(!"1".equals(b2bType))//B2b导出
                                this.geneCell(row, cellNum++,exportDTO.getBoxNorms(), styleRow);
                            this.geneCell(row, cellNum++,skuDto.getsPrice(), styleRow);
                            this.geneCell(row, cellNum++,skuDto.getdPrice(), styleRow);
                            this.geneCell(row, cellNum++,skuDto.getRetailPrice().toString(), styleRow);
                            if(!"1".equals(b2bType)){//B2b导出
                                this.geneCell(row, cellNum++, exportDTO.getMinWholesaleQty()+"", styleRow);
                                this.geneCell(row, cellNum++, exportDTO.getMinSupplierQty()+"", styleRow);
                            }
                            this.geneCell(row, cellNum++,exportDTO.getOriginplaceName(), styleRow);
                            this.geneCell(row, cellNum++,exportDTO.getManufacturers(), styleRow);
                            this.geneCell(row, cellNum++,exportDTO.getSupplierName(), styleRow);
                            this.geneCell(row, cellNum++, this.toSheillife(exportDTO),styleRow);
                            if("1".equals(b2bType)){//B2C导出
	                            this.geneCell(row, cellNum++, skuDto.getProductCode(),styleRow);//商品货号 
	                            if(skuDto.getBestoayPrice()!=null){   	
	                            	this.geneCell(row, cellNum++, skuDto.getBestoayPrice().toString(),styleRow);//翼支付价格
	                            }else{      	
	                            	this.geneCell(row, cellNum++, "",styleRow);//翼支付价格
	                            }
	                            if(skuDto.getDomesticPrice()!=null){   	
	                            	this.geneCell(row, cellNum++, skuDto.getDomesticPrice().toString(),styleRow);//市场价
	                            }else{      	
	                            	this.geneCell(row, cellNum++, "",styleRow);//市场价
	                            }
	                            if(skuDto.getSkuId()!=null){   	
	                            	this.geneCell(row, cellNum++, skuDto.getSkuId().toString(),styleRow);//skuID
	                            	Map<String, Object> map = new HashMap<String, Object>();
	                            	map.put("skuId", skuDto.getSkuId().toString());
	                            	map.put("productId", skuDto.getProductId());
	                            	List<PurchasePricePO> purchasePricelist = dealerProductSkuService.getPurchasePriceBySkuId(map);
	                            	if(purchasePricelist!=null&&purchasePricelist.size()>0){
	                            		PurchasePricePO purchasePricePO = purchasePricelist.get(0);
	                            		this.geneCell(row, cellNum++, purchasePricePO.getPurchasePrice()+"",styleRow);//成本价
	                            	}else{
	                            		this.geneCell(row, cellNum++, "",styleRow);//成本价
	                            	}
	                            }else{      	
	                            	this.geneCell(row, cellNum++, "",styleRow);//skuID
	                            	this.geneCell(row, cellNum++, "",styleRow);//成本价
	                            }
                            }
                            if(!"1".equals(b2bType)){//B2B导出
	                            if(skuDto.getSkuId()!=null){   	
	                            	this.geneCell(row, cellNum++, skuDto.getSkuId().toString(),styleRow);//skuID
	                            	Map<String, Object> map = new HashMap<String, Object>();
	                            	map.put("skuId", skuDto.getSkuId().toString());
	                            	map.put("supplierId", exportDTO.getSupplierId()+"");
	                            	List<PurchasePricePO> purchasePricelist = dealerProductSkuService.getPurchasePriceBySkuId(map);
	                            	if(purchasePricelist!=null&&purchasePricelist.size()>0){
	                            		PurchasePricePO purchasePricePO = purchasePricelist.get(0);
	                            		this.geneCell(row, cellNum++, purchasePricePO.getPurchasePrice()+"",styleRow);//成本价
	                            	}else{
	                            		this.geneCell(row, cellNum++, "",styleRow);//成本价
	                            	}
	                            }else{      	
	                            	this.geneCell(row, cellNum++, "",styleRow);//skuID
	                            	this.geneCell(row, cellNum++, "",styleRow);//成本价
	                            }
                            }
                            cellNum = Constants.SHORT0;
                        }
                    } else {
                        short cellNum = Constants.SHORT0;
                        row = sheet.createRow(lastRowIndex + 1);
                        this.geneCell(row, cellNum++,exportDTO.getStrCreatedate(), styleRow);
                        this.geneCell(row, cellNum++,exportDTO.getCatePubName(), styleRow);
                        this.geneCell(row, cellNum++,exportDTO.getBusinessProdId(), styleRow);
                        this.geneCell(row, cellNum++, exportDTO.getProductId().toString(), styleRow);
                        this.geneCell(row, cellNum++,exportDTO.getProductName(), styleRow);
                        this.geneCell(row, cellNum++, "没规格", styleRow);
                        this.geneCell(row, cellNum++, "没规格", styleRow);
                        if(!"1".equals(b2bType)){//B2b导出
                            this.geneCell(row, cellNum++,exportDTO.getBoxNorms(), styleRow);
                        }
                        this.geneCell(row, cellNum++, "没规格", styleRow);
                        this.geneCell(row, cellNum++, "没规格", styleRow);
                        this.geneCell(row, cellNum++, "没规格", styleRow);
                        if(!"1".equals(b2bType)){//B2b导出   
                            this.geneCell(row, cellNum++, exportDTO.getMinWholesaleQty()+"", styleRow);
                            this.geneCell(row, cellNum++, exportDTO.getMinSupplierQty()+"", styleRow);
                        }
                        this.geneCell(row, cellNum++,exportDTO.getOriginplaceName(), styleRow);
                        this.geneCell(row, cellNum++,exportDTO.getManufacturers(), styleRow);
                        this.geneCell(row, cellNum++,exportDTO.getSupplierName(), styleRow);
                        this.geneCell(row, cellNum++, this.toSheillife(exportDTO),styleRow);
                        if("1".equals(b2bType)){//B2C导出
                        	this.geneCell(row, cellNum++, "", styleRow);//商品货号 
                        	this.geneCell(row, cellNum++, "", styleRow);//翼支付价格
                        	this.geneCell(row, cellNum++, "", styleRow);//市场价
                        	this.geneCell(row, cellNum++, "", styleRow);//skuId
                        	this.geneCell(row, cellNum++, "", styleRow);//成本价
                        }
                        if(!"1".equals(b2bType)){//B2B导出
                        	this.geneCell(row, cellNum++, "", styleRow);//skuId
                        	this.geneCell(row, cellNum++, "", styleRow);//成本价
                        }
                    }
                }
                
                LOGGER.info("拼装商品信息完成!");
                String filename = "productList";
                os = response.getOutputStream();
                response.reset();
                response.setCharacterEncoding("UTF-8");
                filename = java.net.URLEncoder.encode(filename, "UTF-8");
                response.setHeader("Content-Disposition","attachment;filename="+ new String(filename.getBytes("UTF-8"), "GBK")+ ".xls");
                response.setContentType("application/msexcel");// 定义输出类型
                workbook.write(os);
                response.getOutputStream().flush();
                response.getOutputStream().close();
            } catch (Exception e) {
                LOGGER.error("导出B2B商品错误" + e.getMessage(), e);
            }
	    }
	}
	
	
	@RequestMapping(value = "/POPdownProductExcel")
	public void POPdownProduct(HttpServletResponse response, String cate,PageBean<DealerProductTabExportDTO> pageBean,DealerProductSelectConDTO dealerProductSelectConDTO) {
	    if(StringUtils.isNotBlank(cate)){
    	    List<String> cateDispIds = new ArrayList<String>();
    	    cateDispIds.add(cate);
            dealerProductSelectConDTO.setCateDispIds(cateDispIds);
	    }
	    
	    //商品列表，查询wofe库
	    if(dealerProductSelectConDTO.getAuditStatus() == 1){
	    	
		    dealerProductSelectConDTO.setB2cSupply(51);
		    pageBean.setPageSize(999999);
		    pageBean.setParameter(dealerProductSelectConDTO);
		    pageBean = RemoteServiceSingleton.getInstance().getDealerProductExportExcelService().findB2CProTabListbyPOP(pageBean);
		    
		    
		    if (null != pageBean && null != pageBean.getResult()) {
		        try {
	                OutputStream os = null;
	                List<DealerProductTabExportDTO> list = pageBean.getResult();
	                HSSFWorkbook workbook = new HSSFWorkbook();// 声明一个工作薄
	                HSSFSheet sheet = workbook.createSheet("productList");// 生成一个表格
	                HSSFRow row = sheet.createRow(0);
	                
	                HSSFCellStyle style = workbook.createCellStyle();
	                style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
	                style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	                style.setFillForegroundColor(HSSFColor.YELLOW.index);
	                HSSFCell cell = null;
	                String strtitle ;
	                strtitle =  "供应商ID,SKU_ID,上下架状态,发布日期,商品品类,商品编码,商品ID,商品名称,规格名称,条形码,零售价(￥),原产国,制造商,供应商,保质期,商品货号,翼支付价格(￥),市场价(￥)";
	                FormatBigDecimal formatBigDecimal = new FormatBigDecimal();
	                sheet.setDefaultColumnWidth(20);
	                String[] split = strtitle.split(",");
	                for (int i = 0; i < split.length; i++) {
	                    cell = row.createCell(i);
	                    cell.setCellValue(split[i]);
	                    cell.setCellStyle(style);
	                }
	                for (int i = 0; i < list.size(); i++) {
	                    HSSFCellStyle styleRow = null;
	                    if (i % 2 == 0) {
	                        styleRow = workbook.createCellStyle();
	                        styleRow.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
	                        styleRow.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	                        styleRow.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
	                    } else {
	                        styleRow = workbook.createCellStyle();
	                        styleRow.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
	                        styleRow.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	                        styleRow.setFillForegroundColor(HSSFColor.WHITE.index);
	                    }
	                    int lastRowIndex = sheet.getLastRowNum();
	                    DealerProductTabExportDTO exportDTO = list.get(i);
	                    List<SKUAuditExportDTO> skuList = exportDTO.getSkuList();
	                    if (skuList != null && skuList.size() > 0) {
	                        int skuLength = skuList.size();
	                        for (int j = 0; j < skuLength; j++) {
	                            short cellNum = Constants.SHORT0;
	                            SKUAuditExportDTO skuDto = skuList.get(j);
	                            row = sheet.createRow(j + lastRowIndex + 1);
	                            
	                            this.geneCell(row, cellNum++,exportDTO.getSupplierId(), styleRow);
	                            this.geneCell(row, cellNum++,skuDto.getSkuId()+"", styleRow);
	                            
	                            if(exportDTO.getIstate() == 0){
	                            	this.geneCell(row, cellNum++,"下架", styleRow);
	                            }
	                            if(exportDTO.getIstate() == 1){
	                            	this.geneCell(row, cellNum++,"上架", styleRow);
	                            }
	                            if(exportDTO.getIstate() == 2){
	                            	this.geneCell(row, cellNum++,"删除", styleRow);
	                            }
	                            if(exportDTO.getIstate() == 3){
	                            	this.geneCell(row, cellNum++,"待出售", styleRow);
	                            }
	                            
	                            this.geneCell(row, cellNum++,exportDTO.getStrCreatedate(), styleRow);
	                            this.geneCell(row, cellNum++,exportDTO.getCatePubName(), styleRow);
	                            this.geneCell(row, cellNum++,exportDTO.getBusinessProdId(), styleRow);
	                            this.geneCell(row, cellNum++,exportDTO.getProductId().toString(), styleRow);
	                            this.geneCell(row, cellNum++,exportDTO.getProductName(), styleRow);
	                            this.geneCell(row, cellNum++,skuDto.getSkuNameCN(), styleRow);
	                            this.geneCell(row, cellNum++,skuDto.getSkuCode(), styleRow);
//	                            this.geneCell(row, cellNum++,skuDto.getsPrice(), styleRow);
	                            
	                            BigDecimal productPrice = new BigDecimal(skuDto.getdPrice());
	    						BigDecimal productRetailPrice = skuDto.getRetailPrice();

	    						BigDecimal productPriceB = formatBigDecimal
	    								.priceFormat(productPrice);
	    						BigDecimal productRetailPriceB = formatBigDecimal
	    								.priceFormat(productRetailPrice);

//	                            this.geneCell(row, cellNum++,productPriceB.toString(), styleRow);
	                            this.geneCell(row, cellNum++,productRetailPriceB.toString(), styleRow);
	                           
	                            this.geneCell(row, cellNum++,exportDTO.getOriginplaceName(), styleRow);
	                            this.geneCell(row, cellNum++,exportDTO.getManufacturers(), styleRow);
	                            this.geneCell(row, cellNum++,exportDTO.getSupplierName(), styleRow);
	                            this.geneCell(row, cellNum++, this.toSheillife(exportDTO),styleRow);
	                            this.geneCell(row, cellNum++, skuDto.getProductCode(),styleRow);//商品货号
	                            if(skuDto.getBestoayPrice()!=null){
	                            	 this.geneCell(row, cellNum++, skuDto.getBestoayPrice().toString(),styleRow);//翼支付价格
	                            }else{
	                            	 this.geneCell(row, cellNum++,"",styleRow);//翼支付价格
	                            }
	                            if(skuDto.getDomesticPrice()!=null){
	                            	this.geneCell(row, cellNum++, skuDto.getDomesticPrice().toString(),styleRow);//市场价格
	                            }else{
	                            	this.geneCell(row, cellNum++,"",styleRow);//市场价格
	                            }
	                            cellNum = Constants.SHORT0;
	                        }
	                    } else {
	                        short cellNum = Constants.SHORT0;
	                        row = sheet.createRow(lastRowIndex + 1);
	                        
	                        this.geneCell(row, cellNum++,exportDTO.getSupplierId(), styleRow);
                            this.geneCell(row, cellNum++,exportDTO.getSkuId(), styleRow);
                            
                            if(exportDTO.getIstate() == 0){
                            	this.geneCell(row, cellNum++,"下架", styleRow);
                            }
                            if(exportDTO.getIstate() == 1){
                            	this.geneCell(row, cellNum++,"上架", styleRow);
                            }
                            if(exportDTO.getIstate() == 2){
                            	this.geneCell(row, cellNum++,"删除", styleRow);
                            }
                            if(exportDTO.getIstate() == 3){
                            	this.geneCell(row, cellNum++,"待出售", styleRow);
                            }
                            
	                        this.geneCell(row, cellNum++,exportDTO.getStrCreatedate(), styleRow);
	                        this.geneCell(row, cellNum++,exportDTO.getCatePubName(), styleRow);
	                        this.geneCell(row, cellNum++,exportDTO.getBusinessProdId(), styleRow);
	                        this.geneCell(row, cellNum++, exportDTO.getProductId().toString(), styleRow);
	                        this.geneCell(row, cellNum++,exportDTO.getProductName(), styleRow);
	                        this.geneCell(row, cellNum++, "没规格", styleRow);
	                        this.geneCell(row, cellNum++, "没规格", styleRow);
	                        
	                        this.geneCell(row, cellNum++, "没规格", styleRow);
	                        this.geneCell(row, cellNum++, "没规格", styleRow);
	                        this.geneCell(row, cellNum++, "没规格", styleRow);
	                       
	                        this.geneCell(row, cellNum++,exportDTO.getOriginplaceName(), styleRow);
	                        this.geneCell(row, cellNum++,exportDTO.getManufacturers(), styleRow);
	                        this.geneCell(row, cellNum++,exportDTO.getSupplierName(), styleRow);
	                        this.geneCell(row, cellNum++, this.toSheillife(exportDTO),styleRow);
	                        this.geneCell(row, cellNum++,"",styleRow);
	                        this.geneCell(row, cellNum++,"",styleRow);
	                        this.geneCell(row, cellNum++,"",styleRow);
	                    }
	                }
	                
	                LOGGER.info("拼装商品信息完成!");
	                String filename = "productList";
	                os = response.getOutputStream();
	                response.reset();
	                response.setCharacterEncoding("UTF-8");
	                filename = java.net.URLEncoder.encode(filename, "UTF-8");
	                response.setHeader("Content-Disposition","attachment;filename="+ new String(filename.getBytes("UTF-8"), "GBK")+ ".xls");
	                response.setContentType("application/msexcel");// 定义输出类型
	                workbook.write(os);
	                response.getOutputStream().flush();
	                response.getOutputStream().close();
	            } catch (Exception e) {
	                LOGGER.error("导出B2B商品错误" + e.getMessage(), e);
	            }
		    }
	    }
	}
	
	/**
	 * 条件加载商品
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param cate
	 *            String
	 * @param page
	 *            Integer
	 * @param dealerProductSelectConDTO
	 *            DealerProductSelectConDTO
	 * @return null
	 */
	@RequestMapping(value = "/downProductListExcel")
	public void downProductListExcel(HttpServletResponse response, String cate,PageBean<DealerProductTabExportDTO> pageBean,DealerProductSelectConDTO dealerProductSelectConDTO) {

		List<String> cateList = new ArrayList<String>();

		if (cate != null) {
			cateList.add(cate);
			dealerProductSelectConDTO.setCateDispIds(cateList);
		}

		pageBean.setPageSize(Constants.MAXPAGESIZE);
		pageBean.setPage(Constants.DEFAULTPAGE);
		pageBean.setSortFields("CREATEDDATE");
		pageBean.setOrder("DESC");
		pageBean.setParameter(dealerProductSelectConDTO);

		try {
			pageBean = RemoteServiceSingleton.getInstance().getDealerProductExportExcelService().findProTabList(pageBean);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

		if (null != pageBean && null != pageBean.getResult()) {

			OutputStream os = null;
			List<DealerProductTabExportDTO> list = pageBean.getResult();
			try {
				HSSFWorkbook workbook = new HSSFWorkbook();// 声明一个工作薄
				HSSFSheet sheet = workbook.createSheet("productList");// 生成一个表格
				HSSFRow row = sheet.createRow(0);

				HSSFCellStyle style = workbook.createCellStyle();
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				style.setFillForegroundColor(HSSFColor.YELLOW.index);
				HSSFCell cell = null;
				String[] strtitle = { "发布日期", "商品品类", "商品编码", "商品ID", "商品名称",
						"规格名称", "条形码", "箱规", "预订价(￥)", "批发价(￥)", "建议零售价(￥)",
						"现货最小起订量", "期货最小起订量", "原产国", "制造商", "供应商", "保质期","商品货号"};
				sheet.setDefaultColumnWidth(20);
				for (int i = 0; i < strtitle.length; i++) {
					cell = row.createCell(i);
					cell.setCellValue(strtitle[i]);
					cell.setCellStyle(style);
				}
				for (int i = 0; i < list.size(); i++) {
					HSSFCellStyle styleRow = null;
					if (i % 2 == 0) {
						styleRow = workbook.createCellStyle();
						styleRow.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
						styleRow.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
						styleRow.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
					} else {
						styleRow = workbook.createCellStyle();
						styleRow.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
						styleRow.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
						styleRow.setFillForegroundColor(HSSFColor.WHITE.index);
					}
					int lastRowIndex = sheet.getLastRowNum();
					DealerProductTabExportDTO exportDTO = list.get(i);
					List<SKUAuditExportDTO> skuList = exportDTO.getSkuList();
					if (skuList != null && skuList.size() > 0) {
						int skuLength = skuList.size();
						for (int j = 0; j < skuLength; j++) {
							SKUAuditExportDTO skuDto = skuList.get(j);
							row = sheet.createRow(j + lastRowIndex + 1);
							this.geneCell(row, Constants.SHORT0,exportDTO.getStrCreatedate(), styleRow);
							this.geneCell(row, Constants.SHORT1,exportDTO.getCatePubName(), styleRow);
							this.geneCell(row, Constants.SHORT2,exportDTO.getBusinessProdId(), styleRow);
							this.geneCell(row, Constants.SHORT3,exportDTO.getProductId().toString(), styleRow);
							this.geneCell(row, Constants.SHORT4,exportDTO.getProductName(), styleRow);
							this.geneCell(row, Constants.SHORT5,skuDto.getSkuNameCN(), styleRow);
							this.geneCell(row, Constants.SHORT6,skuDto.getSkuCode(), styleRow);
							this.geneCell(row, Constants.SHORT7,exportDTO.getBoxNorms(), styleRow);
							this.geneCell(row, Constants.SHORT8,skuDto.getsPrice(), styleRow);
							this.geneCell(row, Constants.SHORT9,skuDto.getdPrice(), styleRow);
							this.geneCell(row, Constants.SHORT10,skuDto.getRetailPrice().toString(), styleRow);
							this.geneCell(row, Constants.SHORT11, exportDTO.getMinWholesaleQty().toString(), styleRow);
							this.geneCell(row, Constants.SHORT12, exportDTO.getMinSupplierQty().toString(), styleRow);
							this.geneCell(row, Constants.SHORT13,exportDTO.getOriginplaceName(), styleRow);
							this.geneCell(row, Constants.SHORT14,exportDTO.getManufacturers(), styleRow);
							this.geneCell(row, Constants.SHORT15,exportDTO.getSupplierName(), styleRow);
							this.geneCell(row, Constants.SHORT16, this.toSheillife(exportDTO),styleRow);
							this.geneCell(row, Constants.SHORT17, exportDTO.getProductCode(),styleRow);

						}

					} else {
						row = sheet.createRow(lastRowIndex + 1);
						this.geneCell(row, Constants.SHORT0,exportDTO.getStrCreatedate(), styleRow);
						this.geneCell(row, Constants.SHORT1,exportDTO.getCatePubName(), styleRow);
						this.geneCell(row, Constants.SHORT2,exportDTO.getBusinessProdId(), styleRow);
						this.geneCell(row, Constants.SHORT3, exportDTO.getProductId().toString(), styleRow);
						this.geneCell(row, Constants.SHORT4,exportDTO.getProductName(), styleRow);
						this.geneCell(row, Constants.SHORT5, "没规格", styleRow);
						this.geneCell(row, Constants.SHORT6, "没规格", styleRow);
						this.geneCell(row, Constants.SHORT7,exportDTO.getBoxNorms(), styleRow);
						this.geneCell(row, Constants.SHORT8, "没规格", styleRow);
						this.geneCell(row, Constants.SHORT9, "没规格", styleRow);
						this.geneCell(row, Constants.SHORT10, "没规格", styleRow);
						this.geneCell(row, Constants.SHORT11, exportDTO.getMinWholesaleQty().toString(), styleRow);
						this.geneCell(row, Constants.SHORT12, exportDTO.getMinSupplierQty().toString(), styleRow);
						this.geneCell(row, Constants.SHORT13,exportDTO.getOriginplaceName(), styleRow);
						this.geneCell(row, Constants.SHORT14,exportDTO.getManufacturers(), styleRow);
						this.geneCell(row, Constants.SHORT15,exportDTO.getSupplierName(), styleRow);
                        this.geneCell(row, Constants.SHORT16, this.toSheillife(exportDTO),styleRow);
                        this.geneCell(row, Constants.SHORT17, "",styleRow);
					}
				}
				LOGGER.info("拼装商品信息完成!");
				String filename = "productList";
				os = response.getOutputStream();
				response.reset();
				response.setCharacterEncoding("UTF-8");
				filename = java.net.URLEncoder.encode(filename, "UTF-8");
				response.setHeader(
						"Content-Disposition",
						"attachment;filename="
								+ new String(filename.getBytes("UTF-8"), "GBK")
								+ ".xls");
				response.setContentType("application/msexcel");// 定义输出类型
				workbook.write(os);
				response.getOutputStream().flush();
				response.getOutputStream().close();

			} catch (Exception e) {
				LOGGER.error("下载wofe审核商品错误" + e.getMessage(), e);
			}

		}

	}

	/**
	 * . 加载一级类目 AJAX
	 * 
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/getFirstDisp")
	@ResponseBody
	public String getFirstPub() {

		List<TdCatePub> list = new ArrayList<TdCatePub>();
		try {

			list = RemoteServiceSingleton.getInstance().getCategoryServiceRpc()
					.getTopCategoryList();
			LOGGER.info("调用一级类目");

		} catch (Exception e) {

			LOGGER.error(e.getMessage(), e);

		}

		return JSONArray.fromObject(list).toString();

	}

	/**
	 * . 加载二级或三级类目 AJAX
	 * 
	 * @param parCateDispId
	 *            String
	 * @return String
	 */
	@RequestMapping(value = "/getOtherDisp")
	@ResponseBody
	public String getOtherPub(String parCateDispId) {

		LOGGER.info("调用二三级目录");

		List<TdCatePub> list = new ArrayList<TdCatePub>();

		try {

			list = RemoteServiceSingleton.getInstance().getCategoryServiceRpc()
					.getChildrenCategoryList(parCateDispId);

		} catch (Exception e) {

			LOGGER.error(e.getMessage(), e);

		}

		return JSONArray.fromObject(list).toString();

	}

	/**
	 * 验证商品是否可修改
	 * 
	 * @param productId
	 *            Long
	 * @return 可修改状态.
	 */
	@RequestMapping(value = "/checkProductIsEdit")
	@ResponseBody
	public String checkProductIsEdit(Long productId) {

		String statu = Constants.SUCCESS;

		Map<String, String> map = new HashMap<String, String>();

		try {

			System.out.println(productId);

			Long userId = memCachedClient.get(productId + "");

			if (userId == null) {

				memCachedClient.add(productId + "", Constants.MAMCACHEDTIME,
						getCurrentUser().getSequenceId());

			} else {

				Long userIdnow = getCurrentUser().getSequenceId();

				if (userId.longValue() != userIdnow.longValue()) {
					// /FIXME 增加查找用户功能
					String name = UserUtil.findUserSqueID(
							String.valueOf(userId)).getUsername();
					if (ValidateTool.isEmpty(name)) {
						PlatformUser platformUser = RemoteServiceSingleton
								.getInstance().getPlatformUserManagerService()
								.getUserById(userId);
						if (!ValidateTool.isNull(platformUser)) {
							name = platformUser.getName();
						}
					}

					map.put("name", name);
					statu = Constants.SAMEISNULL;

				}

			}

		} catch (Exception e) {

			LOGGER.error("检测商品是否可修改失败  productId=" + productId);
			LOGGER.error(e.getMessage(), e);
			statu = Constants.SERVERISBESY;
			map.put("name", getCurrentUser().getUsername());

		}
		map.put("statu", statu);
		String json = JSONObject.fromObject(map).toString();

		return json;
	}

	/**
	 * 解锁
	 * 
	 * @param productId
	 */
	@RequestMapping(value = "/unClockProductById")
	public void unClockProductById(Long productId) {

		try {
			Long userId = null;
			if (memCachedClient.get(String.valueOf(productId)) != null) {
				userId = memCachedClient.get(String.valueOf(productId));
			}
			Long userIdnow = getCurrentUser().getSequenceId();
			if (null != userIdnow && null != userId
					&& userId.longValue() == userIdnow.longValue()) {
				memCachedClient.delete(String.valueOf(productId));
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

	}

	/**
	 * . 图片上传
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 */
	@RequestMapping("/imageUp")
	public void uploadImages(HttpServletRequest request,
			HttpServletResponse response) {

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		// 获取file框的
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

		// 遍历获取的所有文件
		List<String> picUrlS = new ArrayList<String>();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			try {
				MultipartFile mf = entity.getValue();
				// 判断文件名是否为空。为空set null值
				String myfileUrl = UploadFileUtil.uploadFile(
						mf.getInputStream(),
						Common.getFileExt2(mf.getOriginalFilename()), null);
				String newUrl = Constants.FILESERVER1 + myfileUrl;
				picUrlS.add(Constants.FILESERVER1 + myfileUrl);
				response.getWriter().write(
						"{\"success\":\"true\"," + "\"data\":{\"BaseUrl\":\""
								+ myfileUrl + "\",\"Url\":\"" + newUrl
								+ "\",\"filename\":\""
								+ mf.getOriginalFilename() + "\"}}");
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			} catch (MyException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * . 图片上传
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 */
	@RequestMapping("/jsp/imageUp")
	public void imageUp(HttpServletRequest request,
			HttpServletResponse response, String action) {

		if (action.equals("uploadimage")) {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// 获取file框的
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

			// 遍历获取的所有文件
			List<String> picUrlS = new ArrayList<String>();
			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
				try {
					MultipartFile mf = entity.getValue();
					
					//如果图片大于100k
					if(mf.getSize() > 102400){
						
						response.getWriter().print(
								"{\"state\": \"The picture size beyond 100K!\"}");
						LOGGER.error("富文本编辑器上传图片失败！！！图片超出100K。");
						return;
					}
					
					// 判断文件名是否为空。为空set null值
					String myfileUrl = UploadFileUtil.uploadFile(
							mf.getInputStream(),
							Common.getFileExt2(mf.getOriginalFilename()), null);
					String newUrl = Constants.P0 + myfileUrl;
					picUrlS.add(Constants.P0 + myfileUrl);
					String aaa = "{\"state\": \"SUCCESS\",\"title\": \"\",\"original\":\""
							+ mf.getOriginalFilename()
							+ "\",\"type\": \""
							+ Common.getFileExt(myfileUrl)
							+ "\",\"url\":\""
							+ newUrl + "\",\"size\": \"" + mf.getSize() + "\"}";
					response.getWriter().print(aaa);
				} catch (Exception e) {
					try {
						response.getWriter().print(
								"{\"state\": \"Server is Error!\"}");
					} catch (IOException e1) {
						LOGGER.error("富文本编辑器上传图片失败！！！");
					}
					LOGGER.error("富文本编辑器上传图片失败！！！");
				}
			}
		} else if (action.equals("catchimage")) {
			String[] source = request.getParameterValues("linkUp[]");
			StringBuffer status = new StringBuffer();
			status.append("{\"state\": \"SUCCESS\", list: [");
			for (int i = 0; i < source.length; i++) {
				try {
					URL url = new URL(source[i]);
					HttpURLConnection uc = (HttpURLConnection) url
							.openConnection();
					uc.setDoInput(true);// 设置是否要从 URL 连接读取数据,默认为true
					uc.connect();
					// 判断文件名是否为空。为空set null值
					String myfileUrl = UploadFileUtil.uploadFile(
							uc.getInputStream(), Common.getFileExt2(source[i]),
							null);
					String newUrl = Constants.P0 + myfileUrl;
					if (i == source.length - 1) {
						status.append("{\"state\": \"SUCCESS\",\"title\": \"\",\"source\":\""
								+ source[i]
								+ "\",\"type\": \""
								+ Common.getFileExt(myfileUrl)
								+ "\",\"url\":\""
								+ newUrl
								+ "\",\"size\": \""
								+ uc.getContentLength() + "\"}]}");
					} else {
						status.append("{\"state\": \"SUCCESS\",\"title\": \"\",\"source\":\""
								+ source[i]
								+ "\",\"type\": \""
								+ Common.getFileExt(myfileUrl)
								+ "\",\"url\":\""
								+ newUrl
								+ "\",\"size\": \""
								+ uc.getContentLength() + "\"},");
					}
				} catch (Exception e) {
					LOGGER.error("文件上传失败！", e);
				}
			}
			try {
				response.getWriter().print(status);
			} catch (IOException e) {
				LOGGER.error("输出失败！", e);
			}
		} else if (action.equals("listimage")) {
			String productId = request.getParameter("productId");
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer size = Integer.parseInt(request.getParameter("size"));
			List<String> source = null;
			productId = (null == productId ? Constants.IMAGES : productId);
			try {
				source = memCachedClient.get(getSessionId() + productId);
			} catch (Exception e) {
				LOGGER.error("获取缓存图片列表失败！");
			}
			if (null == source) {
				source = new ArrayList<String>();
			}
			StringBuilder status = new StringBuilder();
			status.append("{\"state\": \"SUCCESS\", list: [");
			List<String> findAllProdUrlByProductId = RemoteServiceSingleton
					.getInstance().getDealerProductService()
					.findAllProdUrlByProductId(Long.valueOf(productId), "");
			logger.info("获取商品图片");
			List<String> allList = new ArrayList<String>();
			if (null != findAllProdUrlByProductId) {
				for (String productid : findAllProdUrlByProductId) {
					allList.add(Constants.P0 + productid);
				}
				source.addAll(allList);
			}
			if (source.size() > start) {
				for (int i = start; i < source.size(); i++) {
					if (i == start + size) {
						status.append("{\"url\":\"" + source.get(i)
								+ "\",\"mtime\": \""
								+ System.currentTimeMillis() + "\"}");
						break;
					}
					if (i == source.size() - 1) {
						status.append("{\"url\":\"" + source.get(i)
								+ "\",\"mtime\": \""
								+ System.currentTimeMillis() + "\"}");
					} else {
						status.append("{\"url\":\"" + source.get(i)
								+ "\",\"mtime\": \""
								+ System.currentTimeMillis() + "\"},");
					}
				}
			}
			status.append("]}");
			try {
				response.getWriter().print(status);
			} catch (IOException e) {
				LOGGER.error("输出失败！", e);
			}
		} else if (action.equals("config")) {
			try {
				request.setCharacterEncoding("utf-8");
				response.setHeader("Content-Type", "text/html");
				String rootPath = request.getRealPath("/");
				String re = new ActionEnter(request, rootPath).exec();
				response.getWriter().write(re);
			} catch (IOException e1) {
				e1.printStackTrace();
				LOGGER.error("富文本编辑器！！！");
			}
		}

	}

	/**
	 * 批量下载product图片
	 * 
	 * @param request
	 * @param response
	 * @param productId
	 */
	@RequestMapping(value = "/downBatchProductImage")
	public void downBatchProductImage(HttpServletRequest request,
			HttpServletResponse response, Long productId) {

		String productZipFileName = productId.toString();

		List<String> imguri = RemoteServiceSingleton.getInstance()
				.getDealerProductService()
				.findAllProdUrlByProductId(productId, "");

		File directory = null;

		try {

			for (int i = 0; i < imguri.size(); i++) {

				ProductAssistController.saveProductFileFromURL(
						productZipFileName, Constants.ND + imguri.get(i));

			}

		} catch (Exception e) {

			LOGGER.error(e.getMessage(), e);

		}

		directory = new File(productZipFileName);

		if (directory.isDirectory()) {

			File[] listFiles = directory.listFiles();
			ZipOutputStream zos = null;
			FileInputStream fis = null;

			try {

				zos = new ZipOutputStream(new FileOutputStream(
						productZipFileName + ".zip"));

				for (int i = 0; i < listFiles.length; i++) {

					File file = listFiles[i];

					fis = new FileInputStream(file);

					zos.putNextEntry(new ZipEntry(file.getName()));

					int len;

					// 读入需要下载的文件的内容，打包到zip文件
					byte[] buffer = new byte[1024];

					while ((len = fis.read(buffer)) > 0) {

						zos.write(buffer, 0, len);

					}

				}

				if (null != zos) {

					zos.flush();
					zos.close();

				}
				if (null != fis) {

					fis.close();

				}

			} catch (Exception e) {

				LOGGER.error(e.getMessage(), e);

			} finally {

				ProductAssistController.delFileOrDirectory(productZipFileName);

			}
		}

		OutputStream out = null;

		try {

			out = response.getOutputStream();
			String filesrc = productZipFileName + ".zip";

			File file = new File(filesrc);

			if (file.exists()) {

				InputStream fis = new FileInputStream(file);
				BufferedInputStream bis = new BufferedInputStream(fis);

				BufferedOutputStream bos = new BufferedOutputStream(out);
				response.setContentType("application/x-download");
				response.setHeader(
						"Content-disposition",
						"attachment;filename="
								+ URLEncoder.encode(filesrc, "UTF-8"));

				int byteRead = 0;
				byte[] buffer = new byte[1024];

				while ((byteRead = bis.read(buffer, 0, 1024)) != -1) {

					bos.write(buffer, 0, byteRead);

				}

				bos.flush();
				fis.close();
				bis.close();
				out.close();
				bos.close();

			}

			if (file.exists()) {

				file.delete();

			}

		} catch (IOException e) {

			LOGGER.error(e.getMessage(), e);

		}

	}

	public static void delFileOrDirectory(String productId) {
		File files = new File(productId);
		if (files.exists() && files.isDirectory()) {
			File[] listFiles = files.listFiles();
			for (int i = 0; i < listFiles.length; i++) {
				File file = listFiles[i];
				if (file.isFile()) {
					file.delete();
				}
			}
		}
		files.delete();
	}

	/**
	 * downExcel Help
	 * 
	 * @param row
	 * @param cellNum
	 * @param strCreatedate
	 * @param style
	 */
	@SuppressWarnings("deprecation")
	private void geneCell(HSSFRow row, Short cellNum, String strCreatedate,
			HSSFCellStyle style) {
		HSSFCell cell = row.createCell(cellNum);
		cell.setCellValue(strCreatedate);
		cell.setCellStyle(style);
	}

	public static void saveProductFileFromURL(String productId, String imguri) {

		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		HttpURLConnection huc = null;
		try {

			URL url = new URL(imguri);

			huc = (HttpURLConnection) url.openConnection();
			huc.connect();
			bis = new BufferedInputStream(huc.getInputStream());
			File file = new File(productId);

			if (!file.exists() || !file.isDirectory()) {
				file.mkdir();
			}

			fos = new FileOutputStream(file + "/"
					+ FilenameUtils.getName(imguri));

			byte[] bufbyte = new byte[1024];
			int size = 0;
			while ((size = bis.read(bufbyte)) != -1) {
				fos.write(bufbyte, 0, size);
			}

			fos.flush();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (fos != null)
					fos.close();
				if (bis != null)
					bis.close();
				if (huc != null)
					huc.disconnect();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String toSheillife(DealerProductTabExportDTO exportDTO){ 
	    String strShelLife = "";
        
        if (exportDTO.getSheilLife() != null&& exportDTO.getSheilLife() != 0) {
            strShelLife = exportDTO.getSheilLife().toString();
            switch (exportDTO.getSheilLifeType()) {
            case com.mall.dealer.product.util.Constants.SHEILLIFE_DAY:
                strShelLife += " 天";
                break;
            case com.mall.dealer.product.util.Constants.SHEILLIFE_MON:
                strShelLife += " 月";
                break;
            case com.mall.dealer.product.util.Constants.SHEILLIFE_YEAR:
                strShelLife += " 年";
                break;
            default:
                break;
            }
        } else {
            strShelLife = "无保质期";
        }
	    return strShelLife;
	}
}