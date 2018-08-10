package com.mall.controller.logistics;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.category.po.AgentProvince;
import com.mall.dealer.product.po.PlatformLogisticTemp;
import com.mall.dealer.product.util.LogisticConstants;
import com.mall.mybatis.utility.PageBean;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;

@Controller
@RequestMapping(value = "/platformlogist")
public class PlatformLogisticsController extends BaseController {
    /**
     * LOGGER.
     */
    private static final Log LOGGER = LogFactory.getLogger(PlatformLogisticsController.class);
    private static final String LOGISTICS = "/dealerseller/platformlogistics/platformlogistics";
    private static final String TEMPLATEUI = "/dealerseller/platformlogistics/platformtemplateEdit";
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";
    private static final Short TYPE1=1;
    private static final Short TYPE2=2;

    /**
     * @return 库存页面.
     */
    @RequestMapping(value = "/getLogisticsPage")
    public String getLogisticsPage() {

        LOGGER.info("跳转平台自定义运费模板页面");
        LOGGER.info("商户Id:" + getCurrentPlatformId());
        LOGGER.info("用户:" + getCurrentUser().getUsername());
        LOGGER.info("用户ID:" + getCurrentUser().getId());
        return LOGISTICS;
    }

    /**
     * 获取省份
     * 
     * @return
     */
    @RequestMapping(value = "/getProvince")
    @ResponseBody
    public String getProvince(HttpServletRequest request, HttpServletResponse response) {
        List<AgentProvince> list = new ArrayList<AgentProvince>();
        try {
            list = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().getAllProvices();
            return JSONArray.fromObject(list).toString();
        } catch (Exception e) {
            LOGGER.error("获取省份列表异常:" + e.getMessage(), e);
            return null;
        }
    }

    @RequestMapping(value = "/getTemplate")
    public String getTemplate(HttpServletRequest request, HttpServletResponse response, Integer page,
            PlatformLogisticTemp condition) {
        try {
            PageBean<PlatformLogisticTemp> pageBean = new PageBean<PlatformLogisticTemp>();
            if (page != null && page != 0) {
                pageBean.setPage(page);
            } else {
                pageBean.setPage(1);
            }
            pageBean.setParameter(condition);
            pageBean.setPageSize(Constants.PAGESIZE);
            pageBean = RemoteServiceSingleton.getInstance().getPlatformLogisticTempService().getTempsByPage(pageBean);
            // 调用服务
            request.getSession().setAttribute("pb", pageBean);
            request.getRequestDispatcher(
                    "/WEB-INF/views/dealerseller/platformlogistics/modelpage/platformtemplate_model.jsp").forward(
                    request, response);
        } catch (Exception e) {
            LOGGER.error("查询平台自定义运费模板列表异常:" + e.getMessage(), e);
        }
        return null;
    }

    @RequestMapping(value = "/toCreateTemplate")
    public String toCreateTemplate(Model model) {
        String errorMsg = "";
        PlatformLogisticTemp logisticTemp = null;
        model.addAttribute("errorMsg", errorMsg);
        model.addAttribute("logisticTemp", logisticTemp);
        return TEMPLATEUI;
    }

    @RequestMapping(value = "/createTemplate")
    @ResponseBody
    public String createTemplate(HttpServletRequest request, HttpServletResponse response,
            PlatformLogisticTemp platformLogisticTemp) {
        int createFlag = LogisticConstants.CREATE_SUC;
        String msg = SUCCESS;
        try {
            msg = this.checkTemplate(platformLogisticTemp);
            if (!SUCCESS.equals(msg)) {
                return msg;
            }
            createFlag = RemoteServiceSingleton.getInstance().getPlatformLogisticTempService()
                    .insertTemplate(platformLogisticTemp);
            if (LogisticConstants.CREATE_FAIL == createFlag) {
                msg = ERROR;
            } else if (LogisticConstants.REPEAT_FAIL == createFlag) {
                msg = "此模板的发/收货地址已存在";
            }
        } catch (Exception e) {
            createFlag = LogisticConstants.CREATE_FAIL;
            msg = ERROR;
            LOGGER.error("商户Id:" + getCurrentPlatformId());
            LOGGER.error("用户:" + getCurrentUser().getUsername());
            LOGGER.error("用户ID:" + getCurrentUser().getId());
            LOGGER.error("创建运费模板异常：" + e.getMessage(), e);
        }
        return msg;
    }

    @RequestMapping(value = "/updateStatus")
    @ResponseBody
    public String updateStatus(HttpServletRequest request, HttpServletResponse response,
            PlatformLogisticTemp platformLogisticTemp) {
        int createFlag = LogisticConstants.CREATE_SUC;
        String msg = SUCCESS;
        try {
            createFlag = RemoteServiceSingleton.getInstance().getPlatformLogisticTempService()
                    .updateStatus(platformLogisticTemp);
            if (LogisticConstants.CREATE_FAIL == createFlag) {
                msg = ERROR;
            } else if (LogisticConstants.REPEAT_FAIL == createFlag) {
                msg = "此物流的发/收货地址已存在";
            }
        } catch (Exception e) {
            createFlag = LogisticConstants.CREATE_FAIL;
            msg = ERROR;
            LOGGER.error("商户Id:" + getCurrentPlatformId());
            LOGGER.error("用户:" + getCurrentUser().getUsername());
            LOGGER.error("用户ID:" + getCurrentUser().getId());
            LOGGER.error("创建运费模板异常：" + e.getMessage(), e);
        }
        return msg;
    }

    @RequestMapping(value = "/toUpdateTemplate")
    public String toUpdateTemplate(Model model, Long logisticTempId) {
        LOGGER.info("to toUpdateTemplate logisticTempId=" + logisticTempId);
        String errorMsg = "";
        PlatformLogisticTemp logisticTemp = RemoteServiceSingleton.getInstance().getPlatformLogisticTempService()
                .getTemplateByTId(logisticTempId);
        if (logisticTemp != null && logisticTemp.getLogisticTempId() != null && logisticTemp.getLogisticTempId() != 0l) {
            if (1 != logisticTemp.getStatus()) {
                errorMsg = "模板处于无效状态，无法修改";
            }
        } else {
            errorMsg = "模板信息异常，无法修改";
        }
        model.addAttribute("errorMsg", errorMsg);
        model.addAttribute("logisticTemp", logisticTemp);
        return TEMPLATEUI;
    }

    @RequestMapping(value = "/updateTemplate")
    @ResponseBody
    public String updateTemplate(HttpServletRequest request, HttpServletResponse response,
            PlatformLogisticTemp platformLogisticTemp) {
        int createFlag = LogisticConstants.CREATE_SUC;
        String msg = SUCCESS;
        try {
            msg = this.checkTemplate(platformLogisticTemp);
            if (!SUCCESS.equals(msg)) {
                return msg;
            }
            if (platformLogisticTemp.getLogisticTempId() != null && platformLogisticTemp.getLogisticTempId() != 0l) {
            } else {
                msg = "运费模板id丢失";
                return msg;
            }
            createFlag = RemoteServiceSingleton.getInstance().getPlatformLogisticTempService()
                    .updateTemp(platformLogisticTemp);
            if (LogisticConstants.CREATE_FAIL == createFlag) {
                msg = ERROR;
            } else if (LogisticConstants.REPEAT_FAIL == createFlag) {
                msg = "此模板的发/收货地址已存在";
            }
        } catch (Exception e) {
            createFlag = LogisticConstants.CREATE_FAIL;
            msg = ERROR;
            LOGGER.error("商户Id:" + getCurrentPlatformId());
            LOGGER.error("用户:" + getCurrentUser().getUsername());
            LOGGER.error("用户ID:" + getCurrentUser().getId());
            LOGGER.error("创建运费模板异常：" + e.getMessage(), e);
        }
        return msg;
    }

    /**
     * 校验模板信息
     * 
     * @param logisticTemp
     * @return
     */
    private String checkTemplate(PlatformLogisticTemp platformLogisticTemp) {
        String msg = SUCCESS;
        if (StringUtils.isBlank(platformLogisticTemp.getLogisticTempName())) {
            msg = "模板名称不能为空";
            return msg;
        } else if (platformLogisticTemp.getProvinceStartId() == 0 || platformLogisticTemp.getProvinceEndId() == 0) {
            msg = "收/发货省份不能为空";
            return msg;
        } else if (platformLogisticTemp.getBaseQt() <= 0 || platformLogisticTemp.getStepQt() <= 0) {
            msg = "首重（件）、续重（件）没有正确填写";
            return msg;
        } else if (platformLogisticTemp.getBaseFee().compareTo(new BigDecimal("0")) < 0
                || platformLogisticTemp.getStepFee().compareTo(new BigDecimal("0")) < 0) {
            msg = "运费金额没有正确填写";
            return msg;
        }
        return msg;
    }

    @RequestMapping(value = "/downLoadExcel")
    public void downLoadExcel(HttpServletRequest request, HttpServletResponse response, Integer page,
            PlatformLogisticTemp condition) {
        LOGGER.info("商户Id:" + getCurrentPlatformId());
        LOGGER.info("用户:" + getCurrentUser().getUsername());
        LOGGER.info("用户ID:" + getCurrentUser().getId());
        PageBean<PlatformLogisticTemp> pageBean = new PageBean<PlatformLogisticTemp>();
        pageBean.setPage(Constants.DEFAULTPAGE);
        pageBean.setParameter(condition);
        pageBean.setPageSize(Constants.MAXPAGESIZE);
        try {
            pageBean = RemoteServiceSingleton.getInstance().getPlatformLogisticTempService().getTempsByPage(pageBean);
        } catch (Exception e) {
            LOGGER.error("调用服务查询运费异常：" + e.getMessage(), e);
        }
        List<PlatformLogisticTemp> listDto = pageBean.getResult();
        if (listDto != null && !listDto.isEmpty()) {
            OutputStream os = null;
            try {
                HSSFWorkbook workbook = new HSSFWorkbook();// 声明一个工作薄
                HSSFSheet sheet = workbook.createSheet("平台运费模板");// 生成一个表格
                HSSFRow row = sheet.createRow((int) 0);

                HSSFCellStyle style = workbook.createCellStyle();
                style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
                style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                style.setFillForegroundColor(HSSFColor.YELLOW.index);
                HSSFCell cell = null;
                String[] strtitle = { "模板编号", "模板名称", "发出省", "目的省", "类型", "首重", "首费", "续重", "续费", "时效(天)", "状态" };
                sheet.setDefaultColumnWidth(20);
                for (int i = 0; i < strtitle.length; i++) {
                    cell = row.createCell(i);
                    cell.setCellValue(strtitle[i]);
                    cell.setCellStyle(style);
                }
                for (int i = 0; i < listDto.size(); i++) {
                    PlatformLogisticTemp exportDTO = listDto.get(i);
                    HSSFCellStyle styleRow = workbook.createCellStyle();
                    styleRow.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
                    row = sheet.createRow(i + 1);
                    // 编号
                    this.geneCell(row, Constants.SHORT0, exportDTO.getLogisticTempId() + "", styleRow);
                    // 名称
                    this.geneCell(row, Constants.SHORT1, exportDTO.getLogisticTempName(), styleRow);
                    // 发出省
                    this.geneCell(row, Constants.SHORT2, exportDTO.getStartAddress(), styleRow);
                    // 目的省
                    this.geneCell(row, Constants.SHORT3, exportDTO.getEndAddress(), styleRow);
                    // 类型
                    if (exportDTO.getType() == TYPE1) {
                        this.geneCell(row, Constants.SHORT4, "按重量计费", styleRow);
                        this.geneCell(row, Constants.SHORT5, exportDTO.getBaseQt() + "", styleRow);
                        this.geneCell(row, Constants.SHORT6, exportDTO.getBaseFee() + "", styleRow);
                        this.geneCell(row, Constants.SHORT7, exportDTO.getStepQt() + "", styleRow);
                        this.geneCell(row, Constants.SHORT8, exportDTO.getStepFee() + "", styleRow);
                    } else if (exportDTO.getType() == TYPE2) {
                        this.geneCell(row, Constants.SHORT4, "按件计费", styleRow);
                        this.geneCell(row, Constants.SHORT5, (int) exportDTO.getBaseQt() + "", styleRow);
                        this.geneCell(row, Constants.SHORT6, exportDTO.getBaseFee() + "", styleRow);
                        this.geneCell(row, Constants.SHORT7, (int) exportDTO.getStepQt() + "", styleRow);
                        this.geneCell(row, Constants.SHORT8, exportDTO.getStepFee() + "", styleRow);
                    } else {
                        this.geneCell(row, Constants.SHORT4, "未知", styleRow);
                        this.geneCell(row, Constants.SHORT5, exportDTO.getBaseQt() + "", styleRow);
                        this.geneCell(row, Constants.SHORT6, exportDTO.getBaseFee() + "", styleRow);
                        this.geneCell(row, Constants.SHORT7, exportDTO.getStepQt() + "", styleRow);
                        this.geneCell(row, Constants.SHORT8, exportDTO.getStepFee() + "", styleRow);
                    }
                    // 时效
                    this.geneCell(row, Constants.SHORT9,
                            exportDTO.getTimeLimitMin() + " - " + exportDTO.getTimeLimitMax(), styleRow);
                    // 状态
                    this.geneCell(row, Constants.SHORT10, exportDTO.getStatus() == TYPE1 ? "有效" : "无效", styleRow);
                }
                LOGGER.info("拼装物流信息完成!");
                String filename = "平台运费模板";
                os = response.getOutputStream();
                response.reset();
                response.setCharacterEncoding("UTF-8");
                filename = java.net.URLEncoder.encode(filename, "UTF-8");
                response.setHeader("Content-Disposition",
                        "attachment;filename=" + new String(filename.getBytes("UTF-8"), "GBK") + ".xls");
                response.setContentType("application/msexcel");// 定义输出类型
                workbook.write(os);
                response.getOutputStream().flush();
                response.getOutputStream().close();

            } catch (Exception e) {
                LOGGER.error("导出平台运费模板错误" + e.getMessage(), e);
            }
        }

    }
    
    

    @SuppressWarnings("deprecation")
    private void geneCell(HSSFRow row, Short cellNum, String strCreatedate, HSSFCellStyle style) {
        HSSFCell cell = row.createCell(cellNum);
        cell.setCellValue(strCreatedate);
        cell.setCellStyle(style);
    }

}
