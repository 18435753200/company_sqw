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
import com.mall.supplier.order.api.rpc.MoveFinishPOService;
import com.mall.supplier.order.dto.AdjFinishVo;
import com.mall.supplier.order.dto.MoveFinishPODto;
import com.mall.supplier.order.po.AdjFinish;
import com.mall.supplier.order.po.MoveFinishPO;
import com.mall.wms.constant.WmsConstant;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;
import com.mall.utils.WarehouseUtils;

@Controller
@RequestMapping(value = "/moveFinish")
public class MoveFinishController {

	private static final Log LOGGER = LogFactory
			.getLogger(MoveFinishController.class);

	@RequestMapping(value = "/moveFinishInit", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String moveFinishInit() {

		return "/dealerseller/stockdetail/moveFinish_list";
	}

	@RequestMapping(value = "/getMoveFinishListByCondition", method = {
			RequestMethod.POST, RequestMethod.GET })
	public final String getAdjFinishListByCondition(HttpServletRequest request,
			String skuId, String firstDate, String lastDate, String pName,
			String sku, String addwho, Integer page) {
		MoveFinishPODto moveFinishPODto = new MoveFinishPODto();
		java.text.SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		PageBean<MoveFinishPODto> pageBean = new PageBean<MoveFinishPODto>();
		List<String> statusList = new ArrayList<String>();
		try {
			statusList.add(WmsConstant.FAILURE);
			statusList.add(WmsConstant.SUCCESS);
			moveFinishPODto.setStatusList(statusList);
			if (StringUtils.isNotBlank(skuId)) {
				moveFinishPODto.setSkuId(skuId);
			}
			if (StringUtils.isNotBlank(firstDate)) {
				moveFinishPODto.setFirstDate(format.parse(firstDate));
			}
			if (StringUtils.isNotBlank(lastDate)) {
				moveFinishPODto.setLastDate(format.parse(lastDate));
			}
			if (StringUtils.isNotBlank(pName)) {
				moveFinishPODto.setpName(pName);
			}
			if (StringUtils.isNotBlank(sku)) {
				moveFinishPODto.setSku(sku);
			}
			if (StringUtils.isNotBlank(addwho)) {
				moveFinishPODto.setAddwho(addwho);
			}
			if (page != null) {
				pageBean.setPage(page);
			} else {
				pageBean.setPage(Constants.INT1);
			}
			pageBean.setParameter(moveFinishPODto);
			pageBean.setPageSize(Constants.INT10);
			MoveFinishPOService moveFinishPOService = RemoteServiceSingleton
					.getInstance().getMoveFinishPOService();
			pageBean = moveFinishPOService
					.findMoveFinishListByPageBean(pageBean);
		} catch (Exception e) {
			LOGGER.error("库存调整查询失败", e);
		}
		request.setAttribute("pb", pageBean);
		return "/dealerseller/stockdetail/modelpage/moveFinish_list_model";
	}
	
	@RequestMapping(value = "/exportMoveFinishListForExcel", method = {
			RequestMethod.POST, RequestMethod.GET })
	public void exportAdjFinishListForExcel(HttpServletResponse response,
			HttpServletRequest request, PageBean<MoveFinishPODto> pageBean,
			String skuId, String firstDate, String lastDate, String pName,
			String sku, String addwho) {
		MoveFinishPODto moveFinishPODto = new MoveFinishPODto();
		java.text.SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<MoveFinishPODto> moveFinishList = null;
		try {
			if (StringUtils.isNotBlank(skuId)) {
				moveFinishPODto.setSkuId(skuId);
			}
			if (StringUtils.isNotBlank(firstDate)) {
				moveFinishPODto.setFirstDate(format.parse(firstDate));
			}
			if (StringUtils.isNotBlank(lastDate)) {
				moveFinishPODto.setLastDate(format.parse(lastDate));
			}
			if (StringUtils.isNotBlank(pName)) {
				moveFinishPODto.setpName(pName);
			}
			if (StringUtils.isNotBlank(sku)) {
				moveFinishPODto.setSku(sku);
			}
			if (StringUtils.isNotBlank(addwho)) {
				moveFinishPODto.setAddwho(addwho);
			}
			pageBean.setParameter(moveFinishPODto);
			pageBean.setPageSize(Constants.MAXPAGESIZE);
			MoveFinishPOService moveFinishPOService = RemoteServiceSingleton
					.getInstance().getMoveFinishPOService();
			moveFinishList = moveFinishPOService
					.getMoveFinishPOListByCondition(pageBean);

			HSSFWorkbook book = new HSSFWorkbook();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy/MM/dd HH:mm:ss");
			HSSFSheet sheet = book.createSheet("moveFinishPO List");

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
			String[] strtitle = { "商品编号", "商品sku", "商品条码", "商品名称",
					"仓库", "原产品状态", "冻结量", "现产品状态", "冻结日期", "操作人","库存冻结状态" };
			for (int i = 0; i < strtitle.length; i++) {
				cell = row.createCell(i);
				cell.setCellValue(strtitle[i]);
				cell.setCellStyle(columnHeadStyle);
			}
			int b = 1;
			if (moveFinishList != null) {
				for (MoveFinishPO moveFinishPODtoEntity : moveFinishList) {
					if(WmsConstant.SUCCESS.equals(moveFinishPODtoEntity.getEdiflag()) || WmsConstant.FAILURE.equals(moveFinishPODtoEntity.getEdiflag())){
						row = sheet.createRow(b++);
						row.createCell(Constants.SHORT0).setCellValue(
								moveFinishPODtoEntity.getSkuId());
						row.createCell(Constants.SHORT1).setCellValue(
								moveFinishPODtoEntity.getSku());
						row.createCell(Constants.SHORT2).setCellValue(
								moveFinishPODtoEntity.getIsbnIsrc());
						row.createCell(Constants.SHORT3).setCellValue(
								moveFinishPODtoEntity.getpName());
						row.createCell(Constants.SHORT4).setCellValue(
								WarehouseUtils
										.getWofeWarehouseTypeValueByName(moveFinishPODtoEntity
												.getWhseid()));
						row.createCell(Constants.SHORT5)
								.setCellValue(
										moveFinishPODtoEntity.getFromloc() == WmsConstant.SCRAP?WmsConstant.SCRAP_NAME:WmsConstant.GENUINE_NAME);
						row.createCell(Constants.SHORT6).setCellValue(
								moveFinishPODtoEntity.getQty() == null ? "0" : moveFinishPODtoEntity
										.getQty().toString());
						row.createCell(Constants.SHORT7).setCellValue(
								moveFinishPODtoEntity.getToloc() == WmsConstant.SCRAP?WmsConstant.SCRAP_NAME:WmsConstant.GENUINE_NAME);
						row.createCell(Constants.SHORT8).setCellValue(
								moveFinishPODtoEntity.getAdddate());
						row.createCell(Constants.SHORT9).setCellValue(
								moveFinishPODtoEntity.getAddwho());
						row.createCell(Constants.SHORT10).setCellValue(
								WmsConstant.SUCCESS.equals(moveFinishPODtoEntity.getEdiflag())?"成功":"失败");
					}
				}

			}
			String filename = "moveFinishPO" + simpleDateFormat.format(new Date());
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
			LOGGER.error("导出库存冻结失败", e);
		}
	}
	
	
	
}
*/