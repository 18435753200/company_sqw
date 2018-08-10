package com.mall.controller.inventory;

/**
 * Created by Administrator on 2016/1/19.
 */

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.mybatis.utility.PageBean;
import com.mall.stock.dto.StockBatchDTO;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**.
 * WOFE库存.
 * @author Lyj.
 *
 */
@Controller
@RequestMapping(value="/batchinventory")
public class BatchInventory extends BaseController {
    /**
     * 日志打印LOGGER.
     */
    private static final Log LOGGER = LogFactory.getLogger(InventoryController.class);

    /**
     * .
     * 此方法用于日期的转换
     *
     * @param binder WebDataBinder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * @return 库存页面.
     */
    @RequestMapping(value="/getBatchInventoryPage")
    public String getBatchInventoryPage(){
        LOGGER.info("跳转库存页面");
        LOGGER.info("商户Id:"+getCurrentPlatformId());
        LOGGER.info("用户:"+getCurrentUser().getUsername());
        LOGGER.info("用户ID:"+getCurrentUser().getId());
        LOGGER.info("return batch inventory Page!");
        return "/dealerseller/batch/batch_main";
    }

    @RequestMapping(value="/getAllBatchInventoryList")
    public String getAllBatchInventoryList(Model model, StockBatchDTO stockWofeDto, Integer page){
        try {
            PageBean<StockBatchDTO> respStockBatchPage = new PageBean<StockBatchDTO>();
            PageBean<StockBatchDTO> requestStockWofeDto = new PageBean<StockBatchDTO>();
            requestStockWofeDto.setPage(page==null? Constants.INT1:page);
            requestStockWofeDto.setPageSize(Constants.PAGESIZE);
            stockWofeDto.setOwner(getCurrentUser().getSequenceId());
            requestStockWofeDto.setParameter(stockWofeDto);
            respStockBatchPage = RemoteServiceSingleton.getInstance().getStockWofeService().findBatchStockWofePage(requestStockWofeDto);
            model.addAttribute("pb", respStockBatchPage);
        } catch (Exception e) {
            LOGGER.error("查询库存信息失败,信息"+e.getMessage(),e);
        }
        return "/dealerseller/batch/batch_inventory_model";
    }

    @RequestMapping(value="/downExcel")
    public void exportBatchInventoryList(Model model, StockBatchDTO stockWofeDto, HttpServletRequest request, HttpServletResponse response){
        LOGGER.info("导出批次库存, 仓库代码：" + stockWofeDto.getWarehouseCode());
        PageBean<StockBatchDTO> respStockBatchPage = new PageBean<StockBatchDTO>();
        PageBean<StockBatchDTO> requestStockWofeDto = new PageBean<StockBatchDTO>();
        requestStockWofeDto.setPage(Constants.INT1);
        requestStockWofeDto.setPageSize(Constants.MAXPAGESIZE);
        stockWofeDto.setOwner(getCurrentUser().getSequenceId());
        requestStockWofeDto.setParameter(stockWofeDto);
        try {
            respStockBatchPage = RemoteServiceSingleton.getInstance().getStockWofeService().findBatchStockWofePage(requestStockWofeDto);
        } catch (Exception e) {
            LOGGER.error("查询批次库存信息失败,信息"+e.getMessage(), e);
        }
        try {
            if ( null != respStockBatchPage && null != respStockBatchPage.getResult()){
                List<StockBatchDTO> result = respStockBatchPage.getResult();
                String title = "BathInventoryList";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DEFAULTDATE);
                OutputStream os = response.getOutputStream();;
                HSSFWorkbook book = new HSSFWorkbook();
                HSSFSheet sheet = book.createSheet(title);
                HSSFRow row = sheet.createRow((int) 0);
                HSSFCell cell =null;
                String [] strtitle = {"商品名称", "规格", "SKU", "库存数量", "批次", "生产日期", "有效时间"};
                for (int i = 0; i < strtitle.length; i++) {
                    cell = row.createCell(i);
                    cell.setCellValue(strtitle[i]);
                }
                for (int i = 0; i < result.size(); i++) {
                    row = sheet.createRow((int) i + 1);
                    StockBatchDTO stock = result.get(i);
                    String pName = stock.getpName();
                    String skuName = stock.getSkuName();
                    String skuID = stock.getSkuId() == null?"":stock.getSkuId().toString();
                    String batchNo = stock.getBatchNo();
                    String productionDate = simpleDateFormat.format(stock.getProductionDate());
                    String expirationDate = simpleDateFormat.format(stock.getExpirationDate());
                    String qty = stock.getQty()==null?"":stock.getQty().toString();

                    row.createCell(Constants.SHORT0).setCellValue(pName);
                    row.createCell(Constants.SHORT1).setCellValue(skuName);
                    row.createCell(Constants.SHORT2).setCellValue(skuID);
                    row.createCell(Constants.SHORT3).setCellValue(qty);
                    row.createCell(Constants.SHORT4).setCellValue(batchNo);
                    row.createCell(Constants.SHORT5).setCellValue(productionDate);
                    row.createCell(Constants.SHORT6).setCellValue(expirationDate);
                }
                response.reset();
                response.setCharacterEncoding("UTF-8");
                response.setHeader("Content-Disposition", "attachment;filename="
                        + new String(title.getBytes("UTF-8"), "GBK") + ".xls");
                response.setContentType("application/msexcel");// 定义输出类型
                book.write(os);
                response.getOutputStream().flush();
                response.getOutputStream().close();

            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
        }
        model.addAttribute("pb", respStockBatchPage);
    }
}