package com.mall.controller.logistics;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

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
import com.mall.category.po.AgentCity;
import com.mall.category.po.AgentProvince;
import com.mall.dealer.product.po.LogisticTemp;
import com.mall.dealer.product.util.LogisticConstants;
import com.mall.mybatis.utility.PageBean;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;

@Controller
@RequestMapping(value = "/providerlogistics")
public class LogisticsController extends BaseController {
    /**
     * LOGGER.
     */
    private static final Log LOGGER = LogFactory.getLogger(LogisticsController.class);
    private static final String LOGISTICS = "/dealerseller/logistics/logistics";
    private static final String TEMPLATEUI = "/dealerseller/logistics/templateEdit";
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";
    private static final Short TYPE1 = 1;
    private static final Short TYPE2 = 2;

    /**
     * @return 库存页面.
     */
    @RequestMapping(value = "/getLogisticsPage")
    public String getLogisticsPage() {

        LOGGER.info("跳转物流商页面");
        LOGGER.info("商户Id:" + getCurrentPlatformId());
        LOGGER.info("用户:" + getCurrentUser().getUsername());
        LOGGER.info("用户ID:" + getCurrentUser().getId());
        LOGGER.info("return logistics Page!");

        return LOGISTICS;
    }

    /**
     * 获取物流商列表
     * 
     * @return
     */
    @RequestMapping(value = "/getPrivider")
    @ResponseBody
    public String getPrivider(HttpServletRequest request, HttpServletResponse response) {
        List<LogisticTemp> list = new ArrayList<LogisticTemp>();
        try {
            list = RemoteServiceSingleton.getInstance().getLogisticTempService().getProviders();
            LOGGER.info("获取所有的物流商");
            return JSONArray.fromObject(list).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
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

    /**
     * 获取城市列表
     * 
     * @return
     */
    @RequestMapping(value = "/getCity")
    @ResponseBody
    public String getCity(HttpServletRequest request, HttpServletResponse response, int provinceId) {
        List<AgentCity> list = new ArrayList<AgentCity>();
        try {
            list = RemoteServiceSingleton.getInstance().getBaseDataServiceRpc().getCitiesByProvinceId(provinceId);
            return JSONArray.fromObject(list).toString();
        } catch (Exception e) {
            LOGGER.error("获取城市列表异常:" + e.getMessage(), e);
            return null;
        }
    }

    @RequestMapping(value = "/getTemplate")
    public String getTemplate(HttpServletRequest request, HttpServletResponse response, Integer page, Long providerId) {
        LOGGER.info("获取物流商的运费模板：物流商id为：" + providerId);
        try {
            LogisticTemp condition = new LogisticTemp();
            PageBean<LogisticTemp> pageBean = new PageBean<LogisticTemp>();
            if (page != null && page != 0) {
                pageBean.setPage(page);
            } else {
                pageBean.setPage(1);
            }
            pageBean.setPageSize(Constants.PAGESIZE);
            if (providerId != null && providerId != 0l) {
                condition.setProviderId(providerId);
                pageBean.setParameter(condition);
            }
            pageBean = RemoteServiceSingleton.getInstance().getLogisticTempService().getTempsByProviderId(pageBean);
            // 调用服务
            request.getSession().setAttribute("pb", pageBean);
            request.getRequestDispatcher("/WEB-INF/views/dealerseller/logistics/modelpage/template_model.jsp").forward(
                    request, response);
        } catch (Exception e) {
            LOGGER.error("查询模板列表异常,物流商id=" + providerId + ":" + e.getMessage(), e);
        }
        return null;
    }

    @RequestMapping(value = "/toCreateTemplate")
    public String toCreateTemplate(Model model, Long providerId) {
        LOGGER.info("to toCreateTemplate providerId=" + providerId);
        String errorMsg = "";
        LogisticTemp logisticTemp = RemoteServiceSingleton.getInstance().getLogisticTempService()
                .getProviderByPvId(providerId);
        if (logisticTemp != null && logisticTemp.getId() != null && logisticTemp.getId() != 0l) {
        } else {
            errorMsg = "物流商信息异常，无法创建";
        }
        model.addAttribute("errorMsg", errorMsg);
        model.addAttribute("logisticTemp", logisticTemp);
        return TEMPLATEUI;
    }

    @RequestMapping(value = "/createTemplate")
    @ResponseBody
    public String createTemplate(HttpServletRequest request, HttpServletResponse response, LogisticTemp logisticTemp) {
        int createFlag = LogisticConstants.CREATE_SUC;
        String msg = SUCCESS;
        try {
            msg = this.checkTemplate(logisticTemp);
            if (!SUCCESS.equals(msg)) {
                return msg;
            }
            createFlag = RemoteServiceSingleton.getInstance().getLogisticTempService().insertTemplate(logisticTemp);
            if (LogisticConstants.CREATE_FAIL == createFlag) {
                msg = ERROR;
            } else if (LogisticConstants.REPEAT_FAIL == createFlag) {
                msg = "此物流商的发/收货地址已存在";
            }
        } catch (Exception e) {
            createFlag = LogisticConstants.CREATE_FAIL;
            msg = ERROR;
            LOGGER.error("添加运费模板，物流商是：" + logisticTemp.getProviderName());
            LOGGER.error("商户Id:" + getCurrentPlatformId());
            LOGGER.error("用户:" + getCurrentUser().getUsername());
            LOGGER.error("用户ID:" + getCurrentUser().getId());
            LOGGER.error("创建运费模板异常：" + e.getMessage(), e);
        }
        return msg;
    }

    @RequestMapping(value = "/updateStatus")
    @ResponseBody
    public String updateStatus(HttpServletRequest request, HttpServletResponse response, LogisticTemp logisticTemp) {
        int createFlag = LogisticConstants.CREATE_SUC;
        String msg = SUCCESS;
        try {
            createFlag = RemoteServiceSingleton.getInstance().getLogisticTempService().updateStatus(logisticTemp);
            if (LogisticConstants.CREATE_FAIL == createFlag) {
                msg = ERROR;
            } else if (LogisticConstants.REPEAT_FAIL == createFlag) {
                msg = "此物流商的发/收货地址已存在";
            }
        } catch (Exception e) {
            createFlag = LogisticConstants.CREATE_FAIL;
            msg = ERROR;
            LOGGER.error("添加运费模板，物流商是：" + logisticTemp.getProviderName());
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
        LogisticTemp logisticTemp = RemoteServiceSingleton.getInstance().getLogisticTempService()
                .getTemplateByTId(logisticTempId);
        if (logisticTemp != null && logisticTemp.getId() != null && logisticTemp.getId() != 0l) {
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
    public String updateTemplate(HttpServletRequest request, HttpServletResponse response, LogisticTemp logisticTemp) {
        int createFlag = LogisticConstants.CREATE_SUC;
        String msg = SUCCESS;
        try {
            msg = this.checkTemplate(logisticTemp);
            if (!SUCCESS.equals(msg)) {
                return msg;
            }
            if (logisticTemp.getLogisticTempId() != null && logisticTemp.getLogisticTempId() != 0l) {
            } else {
                msg = "运费模板id丢失";
                return msg;
            }
            createFlag = RemoteServiceSingleton.getInstance().getLogisticTempService().updateTemp(logisticTemp);
            if (LogisticConstants.CREATE_FAIL == createFlag) {
                msg = ERROR;
            } else if (LogisticConstants.REPEAT_FAIL == createFlag) {
                msg = "此物流商的发/收货地址已存在";
            }
        } catch (Exception e) {
            createFlag = LogisticConstants.CREATE_FAIL;
            msg = ERROR;
            LOGGER.error("添加运费模板，物流商是：" + logisticTemp.getProviderName());
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
    private String checkTemplate(LogisticTemp logisticTemp) {
        String msg = SUCCESS;
        if (logisticTemp.getProviderId() == null || logisticTemp.getProviderId() == 0l) {
            msg = "物流商信息丢失";
            return msg;
        } else {
            LogisticTemp temp = RemoteServiceSingleton.getInstance().getLogisticTempService()
                    .getProviderByPvId(logisticTemp.getProviderId());
            if (temp != null && temp.getId() != null && temp.getId() != 0l) {
                logisticTemp.setProviderName(temp.getProviderName());
            } else {
                msg = "物流商信息丢失";
                return msg;
            }
        }
        int repeatFlag = RemoteServiceSingleton.getInstance().getLogisticTempService().validTempRepeat(logisticTemp);
        if (LogisticConstants.REPEAT_FAIL == repeatFlag) {
            msg = "此物流商的发货/收货地址已存在";
            return msg;
        }
        return msg;
    }

    @RequestMapping(value = "/createProvider")
    @ResponseBody
    public String createProvider(HttpServletRequest request, HttpServletResponse response, LogisticTemp logisticTemp) {
        int createFlag = LogisticConstants.CREATE_SUC;
        try {
            int repeatFlag = RemoteServiceSingleton.getInstance().getLogisticTempService()
                    .validProNameRepeat(logisticTemp.getProviderName().trim());
            if (LogisticConstants.REPEAT_FAIL == repeatFlag) {
                return String.valueOf(LogisticConstants.REPEAT_FAIL);
            }
            createFlag = RemoteServiceSingleton.getInstance().getLogisticTempService().insertProvider(logisticTemp);
        } catch (Exception e) {
            createFlag = LogisticConstants.CREATE_FAIL;
            LOGGER.error("添加物流商：" + logisticTemp.getProviderName());
            LOGGER.error("商户Id:" + getCurrentPlatformId());
            LOGGER.error("用户:" + getCurrentUser().getUsername());
            LOGGER.error("用户ID:" + getCurrentUser().getId());
            LOGGER.error("创建物流商异常：" + e.getMessage(), e);
        }
        return String.valueOf(createFlag);
    }

    @RequestMapping(value = "/downLoadExcel")
    public void downLoadExcel(HttpServletRequest request, HttpServletResponse response, Integer page, Long providerId) {
        LOGGER.info("商户Id:" + getCurrentPlatformId());
        LOGGER.info("用户:" + getCurrentUser().getUsername());
        LOGGER.info("用户ID:" + getCurrentUser().getId());
        LogisticTemp logisticTemp = RemoteServiceSingleton.getInstance().getLogisticTempService()
                .getProviderByPvId(providerId);
        if (logisticTemp != null && logisticTemp.getId() != null && logisticTemp.getId() != 0l) {
            LOGGER.info("物流商名称:" + logisticTemp.getProviderName());
            LogisticTemp condition = new LogisticTemp();
            PageBean<LogisticTemp> pageBean = new PageBean<LogisticTemp>();
            pageBean.setPage(Constants.DEFAULTPAGE);
            pageBean.setPageSize(Constants.MAXPAGESIZE);
            if (providerId != null && providerId != 0l) {
                condition.setProviderId(providerId);
                pageBean.setParameter(condition);
            }
            try {
                pageBean = RemoteServiceSingleton.getInstance().getLogisticTempService().getTempsByProviderId(pageBean);
            } catch (Exception e) {
                LOGGER.error("调用服务查询运费异常：" + e.getMessage(), e);
            }
            List<LogisticTemp> listDto = pageBean.getResult();
            if (listDto != null && !listDto.isEmpty()) {
                OutputStream os = null;
                try {
                    HSSFWorkbook workbook = new HSSFWorkbook();// 声明一个工作薄
                    HSSFSheet sheet = workbook.createSheet(logisticTemp.getProviderName() + "-运费模板");// 生成一个表格
                    HSSFRow row = sheet.createRow((int) 0);

                    HSSFCellStyle style = workbook.createCellStyle();
                    style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
                    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                    style.setFillForegroundColor(HSSFColor.YELLOW.index);
                    HSSFCell cell = null;
                    String[] strtitle = { "物流商", "模板编号", "发出省/市", "目的省/市", "类型", "首重", "首费", "续重", "续费", "时效(天)", "状态" };
                    sheet.setDefaultColumnWidth(20);
                    for (int i = 0; i < strtitle.length; i++) {
                        cell = row.createCell(i);
                        cell.setCellValue(strtitle[i]);
                        cell.setCellStyle(style);
                    }
                    for (int i = 0; i < listDto.size(); i++) {
                        LogisticTemp exportDTO = listDto.get(i);
                        HSSFCellStyle styleRow = workbook.createCellStyle();
                        styleRow.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
                        row = sheet.createRow(i + 1);
                        // 物流商
                        this.geneCell(row, Constants.SHORT0, exportDTO.getProviderName(), styleRow);
                        // 名称
                        this.geneCell(row, Constants.SHORT1, exportDTO.getLogisticTempId() + "", styleRow);
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
                    LOGGER.info(logisticTemp.getProviderName() + "拼装物流信息完成!");
                    String filename = logisticTemp.getProviderName() + "-运费模板";
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
                    LOGGER.error("导出物流商运费模板错误" + e.getMessage(), e);
                }
            }
        } else {
            LOGGER.info("物流商信息异常，无法创建");
        }
    }

    @SuppressWarnings("deprecation")
    private void geneCell(HSSFRow row, Short cellNum, String strCreatedate, HSSFCellStyle style) {
        HSSFCell cell = row.createCell(cellNum);
        cell.setCellValue(strCreatedate);
        cell.setCellStyle(style);
    }
}
