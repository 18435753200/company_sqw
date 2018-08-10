/*package com.mall.controller.stockdetail;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.category.po.TdCatePub;
import com.mall.financial.po.SkuStockDetil;
import com.mall.mybatis.utility.PageBean;
import com.mall.stock.po.Warehouse;
import com.mall.controller.base.BaseController;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.utils.Constants;

@Controller
@RequestMapping(value = "/stockdetail")
public class SkuStockDetailController extends BaseController {
    private static final Log LOGGER = LogFactory.getLogger(SkuStockDetailController.class);
    private static final String LIST = "/dealerseller/stockdetail/stockDetailList";
    private static final String MODEL = "/WEB-INF/views/dealerseller/stockdetail/modelpage/stockdetail_model.jsp";
    private static final Integer PAGESIZE = 20;

    @RequestMapping(value = "/findStockRest", method = RequestMethod.GET)
    public String findStockRest() {
        return LIST;
    }

    @RequestMapping(value = "/getRestPage", method = RequestMethod.POST)
    public String getRestPage(HttpServletRequest request, HttpServletResponse response, Integer page,
            SkuStockDetil condition) {
        LOGGER.info("查询库存明细列表");
        try {
            PageBean<SkuStockDetil> pageBean = new PageBean<SkuStockDetil>();
            if (page != null && page != 0) {
                pageBean.setPage(page);
            } else {
                pageBean.setPage(Constants.DEFAULTPAGE);
            }
            pageBean.setParameter(condition);
            pageBean.setPageSize(PAGESIZE);
            pageBean = RemoteServiceSingleton.getInstance().getFinancStockService().findStockRestPage(pageBean);
            // 调用服务
            request.getSession().setAttribute("pb", pageBean);
            request.getSession().setAttribute("queryType", condition.getQueryType());
            request.getRequestDispatcher(MODEL).forward(request, response);
        } catch (Exception e) {
            LOGGER.error("查询库存明细列表异常:" + e.getMessage(), e);
        }
        return null;
    }

    *//**
     * . 加载一级类目 AJAX
     * 
     * @return String
     * @throws Exception
     *//*
    @RequestMapping(value = "/getFirstDisp")
    @ResponseBody
    public String getFirstPub() {
        LOGGER.info("调用一级类目");
        List<TdCatePub> list = new ArrayList<TdCatePub>();
        try {
            list = RemoteServiceSingleton.getInstance().getCategoryServiceRpc().getTopCategoryList();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return JSONArray.fromObject(list).toString();
    }

    *//**
     * . 加载二级或三级类目 AJAX
     * 
     * @param parCateDispId
     *            String
     * @return String
     *//*
    @RequestMapping(value = "/getOtherDisp")
    @ResponseBody
    public String getOtherPub(String parCateDispId) {
        LOGGER.info("调用二三四级目录");
        List<TdCatePub> list = new ArrayList<TdCatePub>();
        try {
            list = RemoteServiceSingleton.getInstance().getCategoryServiceRpc().getChildrenCategoryList(parCateDispId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return JSONArray.fromObject(list).toString();
    }

    @RequestMapping(value = "/loadWareSelect")
    @ResponseBody
    public String loadWareSelect() {
        LOGGER.info("查询库房列表");
        String loadWareSelect = "";
        List<Warehouse> findWarehouseEnum = new ArrayList<Warehouse>();
        try {
            findWarehouseEnum = RemoteServiceSingleton.getInstance().getWarehouseService()
                    .findWarehouseByWareseName("");
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        if (null != findWarehouseEnum) {
            loadWareSelect = JSONArray.fromObject(findWarehouseEnum).toString();
        }
        return loadWareSelect;
    }

    @RequestMapping(value = "/getBatchNumBlur")
    @ResponseBody
    public String getBatchNumBlur(String batchNumber) {
        // LOGGER.info("模糊查询批次号，输入=" + batchNumber);
        List<String> list = new ArrayList<String>();
        try {
            if (StringUtils.isNotBlank(batchNumber)) {
                list = RemoteServiceSingleton.getInstance().getFinancStockService().getBatchNoBlue(batchNumber);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return JSONArray.fromObject(list).toString();
    }

}
*/