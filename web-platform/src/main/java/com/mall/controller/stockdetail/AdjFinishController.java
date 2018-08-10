/*package com.mall.controller.stockdetail;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.mybatis.utility.PageBean;
import com.mall.supplier.order.api.rpc.AdjFinishService;
import com.mall.supplier.order.dto.AdjFinishVo;
import com.mall.supplier.order.po.AdjFinish;
import com.mall.wms.constant.WmsConstant;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;
import com.mall.utils.WarehouseUtils;

@Controller
@RequestMapping(value = "/adjFinish")
public class AdjFinishController {

	private static final Log LOGGER = LogFactory
			.getLogger(AdjFinishController.class);

	@RequestMapping(value = "/adjFinishInit", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String adjFinishInit() {

		return "/dealerseller/stockdetail/adjFinish_list";
	}

	@RequestMapping(value = "/getAdjFinishListByCondition", method = {
			RequestMethod.POST, RequestMethod.GET })
	public final String getAdjFinishListByCondition(HttpServletRequest request,
			String skuId, String firstDate, String lastDate, String pName,
			String sku, String addwho, Integer page) {
		AdjFinishVo adjFinishVo = new AdjFinishVo();
		java.text.SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		PageBean<AdjFinishVo> pageBean = new PageBean<AdjFinishVo>();
		List<String> statusList = new ArrayList<String>();
		try {
			statusList.add(WmsConstant.FAILURE);
			statusList.add(WmsConstant.SUCCESS);
			adjFinishVo.setStatusList(statusList);
			if (StringUtils.isNotBlank(skuId)) {
				adjFinishVo.setSkuId(skuId);
			}
			if (StringUtils.isNotBlank(firstDate)) {
				adjFinishVo.setFirstDate(format.parse(firstDate));
			}
			if (StringUtils.isNotBlank(lastDate)) {
				adjFinishVo.setLastDate(format.parse(lastDate));
			}
			if (StringUtils.isNotBlank(pName)) {
				adjFinishVo.setpName(pName);
			}
			if (StringUtils.isNotBlank(sku)) {
				adjFinishVo.setSku(sku);
			}
			if (StringUtils.isNotBlank(addwho)) {
				adjFinishVo.setAddwho(addwho);
			}
			if (page != null) {
				pageBean.setPage(page);
			} else {
				pageBean.setPage(Constants.INT1);
			}
			pageBean.setParameter(adjFinishVo);
			pageBean.setPageSize(Constants.INT10);
			AdjFinishService adjFinishService = RemoteServiceSingleton
					.getInstance().getAdjFinishService();
			pageBean = adjFinishService
					.findAdjFinishListByPageBean(pageBean);
		} catch (Exception e) {
			LOGGER.error("库存调整查询失败", e);
		}
		request.setAttribute("pb", pageBean);
		return "/dealerseller/stockdetail/modelpage/adjFinish_list_model";
	}

	@RequestMapping(value = "/exportAdjFinishListForExcel", method = {
			RequestMethod.POST, RequestMethod.GET })
	public void exportAdjFinishListForExcel(HttpServletResponse response,
			HttpServletRequest request, PageBean<AdjFinishVo> pageBean,
			String skuId, String firstDate, String lastDate, String pName,
			String sku, String addwho) {
		AdjFinishVo adjFinishVo = new AdjFinishVo();
		java.text.SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<AdjFinishVo> adjFinishList = null;
		try {
			if (StringUtils.isNotBlank(skuId)) {
				adjFinishVo.setSkuId(skuId);
			}
			if (StringUtils.isNotBlank(firstDate)) {
				adjFinishVo.setFirstDate(format.parse(firstDate));
			}
			if (StringUtils.isNotBlank(lastDate)) {
				adjFinishVo.setLastDate(format.parse(lastDate));
			}
			if (StringUtils.isNotBlank(pName)) {
				adjFinishVo.setpName(pName);
			}
			if (StringUtils.isNotBlank(sku)) {
				adjFinishVo.setSku(sku);
			}
			if (StringUtils.isNotBlank(addwho)) {
				adjFinishVo.setAddwho(addwho);
			}
			pageBean.setParameter(adjFinishVo);
			pageBean.setPageSize(Constants.MAXPAGESIZE);
			AdjFinishService adjFinishService = RemoteServiceSingleton
					.getInstance().getAdjFinishService();
			adjFinishList = adjFinishService
					.getAdjFinishListByCondition(pageBean);

			HSSFWorkbook book = new HSSFWorkbook();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy/MM/dd HH:mm:ss");
			HSSFSheet sheet = book.createSheet("adjFinish List");

			// 另一个字体样式
			HSSFFont columnHeadFont = book.createFont();
			columnHeadFont.setFontName("Times New Roman");
			columnHeadFont.setFontHeightInPoints((short) 12);
			// columnHeadFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

			// 列头的样式
			HSSFCellStyle columnHeadStyle = book.createCellStyle();
			columnHeadStyle.setFont(columnHeadFont);
			columnHeadStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
			columnHeadStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
			columnHeadStyle.setLocked(true);
			columnHeadStyle.setWrapText(true);
			columnHeadStyle.setBorderTop((short) 1);
			columnHeadStyle.setTopBorderColor(HSSFColor.BLACK.index);
			columnHeadStyle.setBorderLeft((short) 1);// 边框的大小
			columnHeadStyle.setLeftBorderColor(HSSFColor.BLACK.index);// 左边框的颜色
			columnHeadStyle.setBorderLeft((short) 1);// 边框的大小
			columnHeadStyle.setRightBorderColor(HSSFColor.BLACK.index);// 右边框的颜色
			columnHeadStyle.setBorderRight((short) 1);// 边框的大小
			columnHeadStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体
			columnHeadStyle.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色
			columnHeadStyle.setFillForegroundColor(HSSFColor.BLUE.index);// 设置单元格的背景颜色．
			columnHeadStyle
					.setFillForegroundColor(IndexedColors.GREY_25_PERCENT
							.getIndex());
			columnHeadStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

			HSSFFont font = book.createFont();
			font.setFontName("Times New Roman");
			font.setFontHeightInPoints((short) 10);
			// 普通单元格样式
			HSSFCellStyle style = book.createCellStyle();
			style.setFont(font);
			style.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 左右居中
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);// 上下居中
			style.setWrapText(true);
			style.setLeftBorderColor(HSSFColor.BLACK.index);
			style.setBorderLeft((short) 1);
			style.setRightBorderColor(HSSFColor.BLACK.index);
			style.setBorderRight((short) 1);
			// style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体
			style.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色．
			style.setFillForegroundColor(HSSFColor.WHITE.index);// 设置单元格的背景颜色．
			sheet.setColumnWidth(0, 5000);
			sheet.setColumnWidth(1, 5000);
			sheet.setColumnWidth(2, 4000);
			sheet.setColumnWidth(3, 10000);
			sheet.setColumnWidth(4, 5000);
			sheet.setColumnWidth(5, 3000);
			sheet.setColumnWidth(6, 3000);
			sheet.setColumnWidth(7, 3000);
			sheet.setColumnWidth(8, 3000);
			HSSFCell cell = null;
			HSSFRow row = sheet.createRow((int) 0);
			String[] strtitle = { "商品编号", "商品sku", "商品条码", "商品名称", "批次号", "库位",
					"仓库", "原库存数量", "库存调整量", "现库存数量", "调整原因", "库存调整日期", "WMS调整人","库存调整状态" };
			for (int i = 0; i < strtitle.length; i++) {
				cell = row.createCell(i);
				cell.setCellValue(strtitle[i]);
				cell.setCellStyle(columnHeadStyle);
			}
			int b = 1;
			if (adjFinishList != null) {
				for (AdjFinish adjFinish : adjFinishList) {
					if(WmsConstant.SUCCESS.equals(adjFinish.getEdiflag()) || WmsConstant.FAILURE.equals(adjFinish.getEdiflag())){
						row = sheet.createRow(b++);
						row.createCell(Constants.SHORT0).setCellValue(
								adjFinish.getSkuId());
						row.createCell(Constants.SHORT1).setCellValue(
								adjFinish.getSku());
						row.createCell(Constants.SHORT2).setCellValue(
								adjFinish.getIsbnIsrc());
						row.createCell(Constants.SHORT3).setCellValue(
								adjFinish.getpName());
						row.createCell(Constants.SHORT4).setCellValue(
								adjFinish.getLot());
						row.createCell(Constants.SHORT5).setCellValue(
								adjFinish.getLoc());
						row.createCell(Constants.SHORT6).setCellValue(
								WarehouseUtils
										.getWofeWarehouseTypeValueByName(adjFinish
												.getWhseid()));
						row.createCell(Constants.SHORT7)
								.setCellValue(
										adjFinish.getSysqty()
												.subtract(adjFinish.getAdjqty())
												.toString());
						row.createCell(Constants.SHORT8).setCellValue(
								adjFinish.getAdjqty() == null ? "0" : adjFinish
										.getAdjqty().toString());
						row.createCell(Constants.SHORT9).setCellValue(
								adjFinish.getSysqty() == null ? "0" : adjFinish
										.getSysqty().toString());
						row.createCell(Constants.SHORT10).setCellValue(
								adjFinish.getCcadjreason());
						row.createCell(Constants.SHORT11).setCellValue(
								adjFinish.getAdddate());
						row.createCell(Constants.SHORT12).setCellValue(
								adjFinish.getAddwho());
						row.createCell(Constants.SHORT13).setCellValue(
								WmsConstant.SUCCESS.equals(adjFinish.getEdiflag())?"成功":"失败");
					}
				}

			}
			String filename = "adjFinish" + simpleDateFormat.format(new Date());
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setCharacterEncoding("UTF-8");
			filename = java.net.URLEncoder.encode(filename, "gb2312");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes("UTF-8"), "GBK") + ".xls");
			response.setContentType("application/msexcel");// 定义输出类型
			book.write(os);
			response.getOutputStream().flush();
			response.getOutputStream().close();

		} catch (Exception e) {
			LOGGER.error("导出库存调整失败", e);
		}
	}
}
*/