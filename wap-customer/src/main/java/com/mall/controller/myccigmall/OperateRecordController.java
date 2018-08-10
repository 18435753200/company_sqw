/**
 *
 */
package com.mall.controller.myccigmall;

import com.alibaba.fastjson.JSON;
import com.mall.annotation.AuthPassport;
import com.mall.constant.RequestContant;
import com.mall.constant.ResponseContant;
import com.mall.controller.base.BaseController;
import com.mall.customer.dto.UserOperationRecordDto;
import com.mall.customer.model.SqwAccountTransferRecord;
import com.mall.customer.model.User;
import com.mall.customer.service.SqwAccountRecordService;
import com.mall.mybatis.utility.PageBean;
import com.mall.service.OperateRecordService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cyy
 */
@Controller
@RequestMapping(value = RequestContant.CUS_OPERATE)
public class OperateRecordController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OperateRecordController.class);

    @Autowired
    private OperateRecordService operateRecordService;

    @Autowired
    private SqwAccountRecordService sqwAccountRecordService;

    /**
     * 操作纪录/登陆记录和消费记录
     *
     * @param model
     * @returncom
     */
    @AuthPassport
    @RequestMapping(value = RequestContant.CUS_OPERATE_LIST, method = RequestMethod.GET)
    public String list(HttpServletRequest request, Model model) {
        User userInfo = getCurrentUser(request);
        String pageStr = request.getParameter("page");
        Integer page = StringUtils.isNotEmpty(pageStr) ? Integer.parseInt(pageStr) : 1;
        String pageSizeStr = request.getParameter("pageSize");
        String type = request.getParameter("type");
        
        if (StringUtils.isEmpty(type)) {
            type = "login";
        }
        Integer pageSize = StringUtils.isNotEmpty(pageSizeStr) ? Integer.parseInt(pageSizeStr) : 20;
        if (userInfo == null) {
            LOGGER.info("userinfo  is null");
            return RequestContant.CUSTOMER_TO_LOGIN;
        }
        //* 2017-3-3 by zhangcuo
        if ("transferAccounts".equalsIgnoreCase(type)) {
            PageBean<SqwAccountTransferRecord> pageBean = new PageBean<SqwAccountTransferRecord>();
            pageBean.setOrder("desc");
            pageBean.setSortFields("id");
            pageBean.setPage(page);
            pageBean.setPageSize(pageSize);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("userId", userInfo.getUserId());
            pageBean.setParameter(map);
            try {
                pageBean = sqwAccountRecordService.getUserTransferPageList(pageBean);
            } catch (Exception e) {
                e.printStackTrace();
            }
           
            request.setAttribute("pageBean", pageBean);
        } else {
            PageBean<UserOperationRecordDto> pageBean = operateRecordService.operate(userInfo, type, page, pageSize);
            request.setAttribute("pageBean", pageBean);
        }
        //*        
//        PageBean<UserOperationRecordDto> pageBean = operateRecordService.operate(userInfo, type, page, pageSize);
//        request.setAttribute("pageBean", pageBean);
       
        request.setAttribute("type", type);
        return ResponseContant.CUS_OPERATION_LIST;
    }

    /**
     * 操作纪录/转账记录
     *
     * @param model
     * @return
     */
    @AuthPassport
    @RequestMapping(value = RequestContant.CUS_OPERATE_DATA, method = RequestMethod.GET)
    @ResponseBody
    public String data(HttpServletRequest request) {
        User userInfo = getCurrentUser(request);
        String pageStr = request.getParameter("page");
        Integer page = StringUtils.isNotEmpty(pageStr) ? Integer.parseInt(pageStr) : 1;
        String pageSizeStr = request.getParameter("pageSize");
        Integer pageSize = StringUtils.isNotEmpty(pageSizeStr) ? Integer.parseInt(pageSizeStr) : 20;
        String type = request.getParameter("type");
        if (StringUtils.isEmpty(type)) {
            type = "login";
        }
        if (userInfo == null) {
            LOGGER.info("userinfo  is null");
            return RequestContant.CUSTOMER_TO_LOGIN;
        }

        if ("transferAccounts".equalsIgnoreCase(type)) {
            PageBean<SqwAccountTransferRecord> pageBean = new PageBean<SqwAccountTransferRecord>();
            pageBean.setOrder("desc");
            pageBean.setSortFields("id");
            pageBean.setPage(page);
            pageBean.setPageSize(pageSize);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("userId", userInfo.getUserId());
            pageBean.setParameter(map);
            try {
                pageBean = sqwAccountRecordService.getUserTransferPageList(pageBean);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return JSON.toJSONString(pageBean);
        } else {
            PageBean<UserOperationRecordDto> pageBean = operateRecordService.operate(userInfo, type, page, pageSize);
            return JSON.toJSONString(pageBean);
        }
    }

}
