package com.mall.controller.infrastructure;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.controller.base.BaseController;
import com.mall.customer.enums.EasyUIPageNotice;
import com.mall.customer.enums.EasyUIPageVersion;
import com.mall.customer.enums.MemberStar;
import com.mall.customer.enums.MemberType;
import com.mall.customer.model.BizRcode;
import com.mall.customer.model.CMaxnumSetting;
import com.mall.customer.model.CNotice;
import com.mall.customer.model.CVersion;
import com.mall.customer.model.CashierSettings;
import com.mall.customer.model.CompanyDividend;
import com.mall.customer.model.MemberDividend;
import com.mall.customer.model.MemberStarSetting;
import com.mall.customer.model.OneDividend;
import com.mall.customer.model.OneDividendRatio;
import com.mall.customer.model.TradeFee;
import com.mall.customer.model.User;
import com.mall.customer.order.api.rpc.CustomerOrderService;
import com.mall.customer.order.dto.FhCyclePlan;
import com.mall.customer.order.dto.FhCyclePlanImpl;
import com.mall.customer.order.dto.OrderDTO;
import com.mall.customer.order.dto.ProductDTO;
import com.mall.customer.order.utils.JsonUtils;
import com.mall.customer.service.CashierService;
import com.mall.customer.service.DividendService;
import com.mall.customer.service.MemberStarService;
import com.mall.customer.service.PsqwAccountRecordService;
import com.mall.customer.service.TradeFeeService;
import com.mall.customer.service.UserService;
import com.mall.dealer.product.api.DealerProductTagsService;
import com.mall.dealer.product.po.DictTags;
import com.mall.dealer.product.po.GiveTags;
import com.mall.dealer.product.po.LimitNum;
import com.mall.mybatis.utility.PageBean;
import com.mall.platform.proxy.RemoteServiceSingleton;
import com.mall.supplier.enums.SupplierType;
import com.mall.supplier.model.SupplierAgentType;
import com.mall.supplier.model.SupplierApplyYhq;
import com.mall.supplier.model.SupplierRegionSettings;
import com.mall.supplier.model.SupplierTypeSettings;
import com.mall.supplier.service.SupplierAgentTypeService;
import com.mall.supplier.service.SupplierApplyYhqService;
//import com.mall.supplier.order.po.Cost;
//import com.mall.supplier.order.po.Currency;
//import com.mall.supplier.order.po.ServiceArchives;
//import com.mall.supplier.order.po.Tax;
import com.mall.supplier.service.SupplierRegionService;
import com.mall.supplier.service.SupplierTypeService;
import com.mall.utils.Constants;


/**
 * .
 * 基础数据的设置.
 *
 * @author Guofy
 */
@Controller
@RequestMapping(value = "/infrastructure")
public class InfrastructureController extends BaseController{

    /**
     * LOGGER日志打印.
     */
    private static final Log LOGGER = LogFactory.getLogger(InfrastructureController.class);
    @Autowired
	private DealerProductTagsService dealerProductTagsService;
    @Autowired
    private UserService userService;
    @Autowired
    private TradeFeeService tradeFeeService;
    @Autowired
    private SupplierAgentTypeService supplierAgentTypeService ;
    @Autowired
    private SupplierApplyYhqService supplierApplyYhqService;
    @Autowired
    private PsqwAccountRecordService psqwAccountRecordService;
    /**
     * .
     * 初始加载基础设置-紧急程序设置
     *
     * @param Model model
     * @return String
     */
	@InitBinder
	//此方法用于日期的转换
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
   /* @RequestMapping(value = "/getEmergency")
    public String getInfrastructure(Model model) {
        LOGGER.info("start to execute method <getInfrastructure()获取紧急程序列表>!!!!");
        List<Dictionarys> dictionarys = new ArrayList<Dictionarys>();
        try {
            dictionarys = RemoteServiceSingleton.getInstance().getInfrastructureService().degreeOfEmergency();
        } catch (Exception e) {
            LOGGER.error("InfrastructureService degreeOfEmergency()<获取紧急程序列表> is failed!!!!" + e.getMessage(), e);
        }
        model.addAttribute("dictionarys", dictionarys);
        LOGGER.info("end<> to execute method <getInfrastructure()获取紧急程序列表>!!!!");
        return "/infrastructure/emergency";
    }

    *//**
     * .
     * 验证简码是否重复.
     *
     * @param code
     * @return
     *//*
    @RequestMapping(value = "/goValidationCode")
    @ResponseBody
    public String goValidationCode(String code, String accountingTime) {
        LOGGER.info("start to execute method <goValidationCode()验证简码是否重复>!!!!");
        LOGGER.info("简码：" + code);
        LOGGER.info("期间：" + accountingTime);
        int result = 0;
        try {
            Map<String, String> m = new HashMap<String, String>();
            m.put("code", code);
            m.put("accountingTime", accountingTime);
            result = RemoteServiceSingleton.getInstance().getInfrastructureService().findcountsCurrency(m);
        } catch (Exception e) {
            LOGGER.error("InfrastructureService findcountsCurrency()<验证简码是否重复> is failed!!!!" + e.getMessage(), e);
            result = 2;
        }
        LOGGER.info("验证结果" + result);
        LOGGER.info("end<> to execute method <goValidationCode()验证简码是否重复>!!!!");
        return result + "";
    }

    *//**
     * .
     * 初始加载基础设置-承运方式设置
     *
     * @param Model
     * @return String
     *//*
    @RequestMapping(value = "/getForwarding")
    public String getForwarding(Model model) {
        LOGGER.info("start to execute method <getForwarding()获取承运方式列表>!!!!");
        List<Dictionarys> dictionarys = new ArrayList<Dictionarys>();
        try {
            dictionarys = RemoteServiceSingleton.getInstance().getInfrastructureService().transportWay();

        } catch (Exception e) {
            LOGGER.error("InfrastructureService transportWay() <获取承运方式列表>is failed!!!" + e.getMessage(), e);
        }
        model.addAttribute("dictionarys", dictionarys);
        LOGGER.info("end<> to execute method <getForwarding()获取承运方式列表>!!!!");
        return "/infrastructure/forwarding";
    }

    *//**
     * .
     * 初始加载基础设置-货币设置
     *
     * @param
     * @param
     * @return ModelAndView
     *//*
    @RequestMapping(value = "/getCurrency")
    public String getCurrency(Model model, PageBean<Currency> currencyPage, Currency currency) {
        currencyPage.setPageSize(Constants.PAGESIZE);
        try {
            currencyPage = RemoteServiceSingleton.getInstance().getInfrastructureService().findCurrencyByPagebean(currencyPage);//findCurrency();
        } catch (Exception e) {

            LOGGER.error(e.getMessage(), e);

        }

        model.addAttribute("pb", currencyPage);

        return "/infrastructure/currency/currency";
    }


    *//**
     * .
     *
     * @return addwarehouse.jsp页面
     * @Title：goAddCurrency
     * @Description:加载到添加货币设置页面.
     *//*
    @RequestMapping(value = "/goAddCurrency")
    public String goAddCurrency() {
        LOGGER.info("start to execute method <goAddTaxrate()跳转页面>!!!!");
        return "infrastructure/currency/addcurrency";
    }


    *//**
     * .
     * 初始加载基础设置-添加货币设置
     *
     * @param request HttpServletRequest
     * @param
     * @return ModelAndView
     *//*
    @RequestMapping(value = "/addCurrency")
    @ResponseBody
    public String addCurrency(HttpServletRequest request, Currency currency) {
        LOGGER.info("start to execute method <addCurrency()添加货币设置>!!!!");
        LOGGER.info(":" + currency.getCurrencyType());
        String result = "";
        try {
            RemoteServiceSingleton.getInstance().getInfrastructureService().insertCurrency(currency);
            result = "货币设置保存成功了!";
        } catch (Exception e) {
            // TODO: handle exception
            result = "货币设置保存失败了!";
            LOGGER.error("InfrastructureService<insertCurrency(currency)>获取所有货币设置 is failed!!!!" + e.getMessage(), e);
        }
        LOGGER.info("end<> to execute method <addCurrency()添加货币设置>!!!!");
        return result;

    }

    *//**
     * .
     * 初始加载基础设置-货币设置-编辑数据获取相应的值
     *
     * @param request HttpServletRequest
     * @param
     * @return ModelAndView
     *//*
    @RequestMapping(value = "/editCurrency")

    public String editCurrency(HttpServletRequest request, HttpServletResponse response,
                               Long sid) {
        Currency currency = new Currency();

        try {
            currency = RemoteServiceSingleton.getInstance().getInfrastructureService().findCurrencyBySid(sid);
        } catch (Exception e) {

            LOGGER.error("InfrastructureService<findCurrencyBySid(sid)>获取所有货币设置 is failed!!!!" + e.getMessage(), e);
        }
        LOGGER.info("end<> to execute method <editCurrency()跳转修改货币设置页面>!!!!");
        request.setAttribute("currency", currency);
        return "infrastructure/currency/editcurrency";

    }


    *//**
     * .
     * 初始加载基础设置-修改保存货币设置
     *
     * @param request HttpServletRequest
     * @param
     * @return ModelAndView
     *//*
    @RequestMapping(value = "/saveCurrency")
    @ResponseBody
    public String saveCurrency(HttpServletRequest request, Currency currency) {
        LOGGER.info("start to execute method <addCurrency()添加货币设置>!!!!");
        LOGGER.info(":" + currency.getCode());
        String result = "";
        try {
            RemoteServiceSingleton.getInstance().getInfrastructureService().updateCurrencyBySid(currency);
            result = "货币设置修改成功了!";
        } catch (Exception e) {
            // TODO: handle exception
            result = "货币设置修改失败了!";
            LOGGER.error("InfrastructureService<updateCurrencyBySid(currency)>获取所有货币设置 is failed!!!!" + e.getMessage(), e);
        }
        LOGGER.info("end<> to execute method <saveCurrency()修改货币设置>!!!!");
        return result;

    }


    *//**
     * .
     * 初始加载基础设置-账期计算方式设置
     *
     * @param request HttpServletRequest
     * @param
     * @return ModelAndView
     *//*
    @RequestMapping(value = "/getAccount")
    public String getAccount(Model model) {
        List<Dictionarys> dictionarys = new ArrayList<Dictionarys>();
        try {
            dictionarys = RemoteServiceSingleton.getInstance().getInfrastructureService().zhangPeriodCalculation();


        } catch (Exception e) {

            LOGGER.error(e.getMessage(), e);

        }

        model.addAttribute("dictionarys", dictionarys);

        return "/infrastructure/account";

    }

    *//**
     * .
     * 初始加载基础设置-税率设置
     *
     * @param request HttpServletRequest
     * @param
     * @return ModelAndView
     *//*
    @RequestMapping(value = "/getTaxrate")
    public String getTaxrate(Model model) {
        List<Tax> taxList = new ArrayList<Tax>();
        try {
            taxList = RemoteServiceSingleton.getInstance().getInfrastructureService().findTaxAll();


        } catch (Exception e) {

            LOGGER.error(e.getMessage(), e);

        }

        model.addAttribute("taxList", taxList);

        return "/infrastructure/taxrate/taxratelist";
    }
*/

    /**
     * .
     *
     * @return addwarehouse.jsp页面
     * @Title：goAddTaxrate
     * @Description:加载到添加税率页面.
     */
    @RequestMapping(value = "/goAddTaxrate")
    public String goAddTaxrate() {
        LOGGER.info("start to execute method <goAddTaxrate()跳转页面>!!!!");
        return "infrastructure/taxrate/addtaxrate";
    }


    /**
     * .
     * 初始加载基础设置-添加税率设置
     *
     * @param request HttpServletRequest
     * @param
     * @return ModelAndView
     */
   /* @RequestMapping(value = "/addTaxrate")
    @ResponseBody
    public String addTaxrate(HttpServletRequest request, Tax taxrate) {
        LOGGER.info("start to execute method <addCurrency()添加货币设置>!!!!");
        LOGGER.info(":" + taxrate.getTax());
        taxrate.setStatus("1");
        String result = "";
        try {
            RemoteServiceSingleton.getInstance().getInfrastructureService().insertTax(taxrate);
            result = "税率设置保存成功了!";
        } catch (Exception e) {
            // TODO: handle exception
            result = "税率设置保存失败了!";
            LOGGER.error("InfrastructureService<insertTax(taxrate)>获取所有税率设置 is failed!!!!" + e.getMessage(), e);
        }
        LOGGER.info("end<> to execute method <addTaxrate()添加税率设置>!!!!");
        return result;

    }


    *//**
     * .
     * 初始加载基础设置-税率设置-编辑数据获取相应的值
     *
     * @param request HttpServletRequest
     * @param
     * @return ModelAndView
     *//*
    @RequestMapping(value = "/editTaxrate")

    public String editTaxrate(HttpServletRequest request, HttpServletResponse response,
                              Long sid) {
        Tax tax = new Tax();

        try {
            tax = RemoteServiceSingleton.getInstance().getInfrastructureService().findTaxBySid(sid);
        } catch (Exception e) {

            LOGGER.error("InfrastructureService<findTaxBySid(taxrate)>获取所有税率设置 is failed!!!!" + e.getMessage(), e);
        }
        LOGGER.info("end<> to execute method <editTaxrate()跳转修改税率设置页面>!!!!");
        request.setAttribute("tax", tax);
        return "infrastructure/taxrate/edittaxrate";

    }


    *//**
     * .
     * 初始加载基础设置-修改保存税率设置
     *
     * @param request HttpServletRequest
     * @param
     * @return ModelAndView
     *//*
    @RequestMapping(value = "/saveTaxrate")
    @ResponseBody
    public String saveTaxrate(HttpServletRequest request, Tax taxrate) {
        LOGGER.info("start to execute method <addCurrency()添加货币设置>!!!!");
        LOGGER.info(":" + taxrate.getTax());
        String result = "";
        try {
            RemoteServiceSingleton.getInstance().getInfrastructureService().updateTaxByBySid(taxrate);
            result = "税率设置修改成功了!";
        } catch (Exception e) {
            // TODO: handle exception
            result = "税率设置修改失败了!";
            LOGGER.error("InfrastructureService<updateTaxByBySid(taxrate)>获取所有税率设置 is failed!!!!" + e.getMessage(), e);
        }
        LOGGER.info("end<> to execute method <saveTaxrate()修改税率设置>!!!!");
        return result;

    }


    *//**
     * .
     * 初始加载基础设置-承运方设置
     *
     * @param request HttpServletRequest
     * @param
     * @return ModelAndView
     *//*
    @RequestMapping(value = "/getShipper")
    public String getShipper(Model model) {
        List<Dictionarys> dictionarys = new ArrayList<Dictionarys>();
        try {
            dictionarys = RemoteServiceSingleton.getInstance().getInfrastructureService().shipper();

        } catch (Exception e) {

            LOGGER.error(e.getMessage(), e);

        }

        model.addAttribute("dictionarys", dictionarys);

        return "/infrastructure/shipper";

    }


    *//**
     * .
     * 初始加载基础设置-货物费用名称
     *
     * @param request HttpServletRequest
     * @param
     * @return ModelAndView
     *//*
    @RequestMapping(value = "/getCostg")
    public String getCostg(Model model) {
        List<Cost> cost = new ArrayList<Cost>();
        try {
            cost = RemoteServiceSingleton.getInstance().getInfrastructureService().findCostByAll();


        } catch (Exception e) {

            LOGGER.error(e.getMessage(), e);

        }

        model.addAttribute("cost", cost);

        return "/infrastructure/cost/costg";


    }


    *//**
     * .
     *
     * @return addwarehouse.jsp页面
     * @Title：goAddTaxrate
     * @Description:加载到添加货物费用名称页面.
     *//*
    @RequestMapping(value = "/goAddCostg")
    public String goAddCostg() {
        LOGGER.info("start to execute method <goAddTaxrate()跳转页面>!!!!");
        return "infrastructure/cost/addcostg";
    }


    *//**
     * .
     * 初始加载基础设置-保存货物费用名称
     *
     * @param request HttpServletRequest
     * @param
     * @return ModelAndView
     *//*
    @RequestMapping(value = "/addCostg")
    @ResponseBody
    public String addCostg(HttpServletRequest request, Cost cost) {
        LOGGER.info("start to execute method <addCurrency()添加货币设置>!!!!");
        LOGGER.info(":" + cost.getCostName());
        String result = "";
        try {
            RemoteServiceSingleton.getInstance().getInfrastructureService().insertCost(cost);
            result = "货物费用名称保存成功了!";
        } catch (Exception e) {
            // TODO: handle exception
            result = "货物费用名称保存失败了!";
            LOGGER.error("InfrastructureService<insertTax(taxrate)>获取所有货物费用名称 is failed!!!!" + e.getMessage(), e);
        }
        LOGGER.info("end<> to execute method <addTaxrate()添加货物费用名称>!!!!");
        return result;

    }


    *//**
     * .
     * 初始加载基础设置-货物费用名称-编辑数据获取相应的值
     *
     * @param request HttpServletRequest
     * @param
     * @return ModelAndView
     *//*
    @RequestMapping(value = "/editCost")

    public String editCost(HttpServletRequest request, HttpServletResponse response,
                           Long sid) {
        Cost cost = new Cost();

        try {
            cost = RemoteServiceSingleton.getInstance().getInfrastructureService().findCostBySid(sid);
        } catch (Exception e) {

            LOGGER.error("InfrastructureService<findCostBySid(sid)>获取所有货物费用名称 is failed!!!!" + e.getMessage(), e);
        }
        LOGGER.info("end<> to execute method <editCost()跳转修改货物费用名称页面>!!!!");
        request.setAttribute("cost", cost);
        return "infrastructure/cost/editcost";

    }


    *//**
     * .
     * 初始加载基础设置-修改保存税率设置
     *
     * @param request HttpServletRequest
     * @param
     * @return ModelAndView
     *//*
    @RequestMapping(value = "/saveCost")
    @ResponseBody
    public String saveCost(HttpServletRequest request, Cost cost) {
        LOGGER.info("start to execute method <addCurrency()添加货物费用名称s>!!!!");
        LOGGER.info(":" + cost.getCostName());
        String result = "";
        try {
            RemoteServiceSingleton.getInstance().getInfrastructureService().updateCostBySid(cost);
            result = "货物费用名称修改成功了!";
        } catch (Exception e) {
            // TODO: handle exception
            result = "货物费用名称修改失败了!";
            LOGGER.error("InfrastructureService<updateTaxByBySid(taxrate)>获取所有货物费用名称 is failed!!!!" + e.getMessage(), e);
        }
        LOGGER.info("end<> to execute method <saveTaxrate()修改货物费用名称>!!!!");
        return result;

    }


    *//**
     * .
     * 初始加载基础设置-货物费用分摊方式设置
     *
     * @param request HttpServletRequest
     * @param
     * @return ModelAndView
     *//*
    @RequestMapping(value = "/getCost")
    public String getCost(Model model) {
        List<Dictionarys> dictionarys = new ArrayList<Dictionarys>();
        try {
            dictionarys = RemoteServiceSingleton.getInstance().getInfrastructureService().goodsCostAllocation();


        } catch (Exception e) {

            LOGGER.error(e.getMessage(), e);

        }

        model.addAttribute("dictionarys", dictionarys);

        return "/infrastructure/cost";

    }


    *//**
     * .
     * 初始加载基础设置-成交条款设置
     *
     * @param request HttpServletRequest
     * @param
     * @return ModelAndView
     *//*
    @RequestMapping(value = "/getDeal")
    public String getDeal(Model model) {
        List<Dictionarys> dictionarys = new ArrayList<Dictionarys>();
        try {
            dictionarys = RemoteServiceSingleton.getInstance().getInfrastructureService().tradingTerms();


        } catch (Exception e) {

            LOGGER.error(e.getMessage(), e);

        }

        model.addAttribute("dictionarys", dictionarys);

        return "/infrastructure/deal";

    }


    *//**
     * .
     * 初始加载基础设置-服务商档案
     *
     * @param request HttpServletRequest
     * @param
     * @return ModelAndView
     *//*
    @RequestMapping(value = "/getServiceArchives")
    public String getServiceArchive(Model model) {
        List<ServiceArchives> serviceArchives = new ArrayList<ServiceArchives>();
        try {
            serviceArchives = RemoteServiceSingleton.getInstance().getInfrastructureService().findServiceArchivesAll();

        } catch (Exception e) {

            LOGGER.error(e.getMessage(), e);

        }

        model.addAttribute("serviceArchives", serviceArchives);

        return "/infrastructure/serviceArchives/serviceArchives";

    }


    *//**
     * .
     *
     * @return addwarehouse.jsp页面
     * @Title：goAddTaxrate
     * @Description:加载到添加服务商档案页面.
     *//*
    @RequestMapping(value = "/goAddServiceArchives")
    public String goAddServiceArchives() {
        LOGGER.info("start to execute method <goAddServiceArchives()跳转页面>!!!!");
        return "infrastructure/serviceArchives/addserviceArchives";
    }


    *//**
     * .
     * 初始加载基础设置-保存服务商档案
     *
     * @param request HttpServletRequest
     * @param
     * @return ModelAndView
     *//*
    @RequestMapping(value = "/addServiceArchives")
    @ResponseBody
    public String addServiceArchives(HttpServletRequest request, ServiceArchives serviceArchives) {
        LOGGER.info("start to execute method <addCurrency()添加货币设置>!!!!");
        LOGGER.info(":" + serviceArchives.getServiceName());
        String result = "";
        try {
            RemoteServiceSingleton.getInstance().getInfrastructureService().insertServiceArchives(serviceArchives);
            result = "服务商档案保存成功了!";
        } catch (Exception e) {
            // TODO: handle exception
            result = "服务商档案保存失败了!";
            LOGGER.error("InfrastructureService<insertServiceArchives(serviceArchives)>获取所有服务商档案 is failed!!!!" + e.getMessage(), e);
        }
        LOGGER.info("end<> to execute method <addServiceArchives()添加服务商档案>!!!!");
        return result;

    }


    *//**
     * .
     * 初始加载基础设置-服务商档案-编辑数据获取相应的值
     *
     * @param request HttpServletRequest
     * @param
     * @return ModelAndView
     *//*
    @RequestMapping(value = "/editServiceArchives")

    public String editServiceArchives(HttpServletRequest request, HttpServletResponse response,
                                      Long sid) {
        ServiceArchives serviceArchives = new ServiceArchives();

        try {
            serviceArchives = RemoteServiceSingleton.getInstance().getInfrastructureService().findServiceArchivesBySid(sid);
        } catch (Exception e) {

            LOGGER.error("InfrastructureService<findServiceArchivesBySid(sid)>获取所有服务商档案 is failed!!!!" + e.getMessage(), e);
        }
        LOGGER.info("end<> to execute method <editCost()跳转修改服务商档案页面>!!!!");
        request.setAttribute("serviceArchives", serviceArchives);
        return "infrastructure/serviceArchives/editserviceArchives";

    }


    *//**
     * .
     * 初始加载基础设置-修改保存服务商档案
     *
     * @param request HttpServletRequest
     * @param
     * @return ModelAndView
     *//*
    @RequestMapping(value = "/saveServiceArchives")
    @ResponseBody
    public String saveServiceArchives(HttpServletRequest request, ServiceArchives serviceArchives) {
        LOGGER.info("start to execute method <addCurrency()添加货物费用名称s>!!!!");
        LOGGER.info(":" + serviceArchives.getServiceName());
        String result = "";
        try {
            RemoteServiceSingleton.getInstance().getInfrastructureService().updateServiceArchivesBySid(serviceArchives);
            result = "服务商档案修改成功了!";
        } catch (Exception e) {
            // TODO: handle exception
            result = "服务商档案修改失败了!";
            LOGGER.error("InfrastructureService<updateServiceArchivesBySid(serviceArchives)>获取所有服务商档案 is failed!!!!" + e.getMessage(), e);
        }
        LOGGER.info("end<> to execute method <saveServiceArchives()修改服务商档案>!!!!");
        return result;

    }


    *//**
     * .
     * 初始加载基础设置-商品分类
     *
     * @param request HttpServletRequest
     * @param
     * @return ModelAndView
     *//*
    @RequestMapping(value = "/getGoodsCategory")
    public String getGoodsCategory(Model model) {
        List<Dictionarys> dictionarys = new ArrayList<Dictionarys>();
        try {
            dictionarys = RemoteServiceSingleton.getInstance().getInfrastructureService().infrastructures("infrastructure_goodsCategory");

        } catch (Exception e) {

            LOGGER.error(e.getMessage(), e);

        }

        model.addAttribute("dictionarys", dictionarys);

        return "/infrastructure/goodsCategory";

    }*/

    @RequestMapping(value = "/selectNotice")
    public String selectNotice(CNotice cNotice) {
        
        return "/infrastructure/selectNotice";
    }
    @RequestMapping(value = "/selectVersion")
    public String selectVersion(CNotice cNotice) {
    	
    	return "/infrastructure/selectVersion";
    }
    
    @RequestMapping(value = "/selectNotices")
    @ResponseBody
    public String selectNotices(CNotice cNotice,Model model, @RequestParam(defaultValue="1") Integer page,
			@RequestParam(defaultValue="30") Integer rows) {
    	Integer startpage=(page-1)*rows;
    	EasyUIPageNotice easyUIPageNotice=userService.selectNotices(cNotice,startpage,rows);
    	String jString = JsonUtils.ObjectAsString(easyUIPageNotice);
		return jString;
    }
    @RequestMapping(value = "/selectVersions")
    @ResponseBody
    public String selectVersions(CVersion cVersion,Model model, @RequestParam(defaultValue="1") Integer page,
    		@RequestParam(defaultValue="30") Integer rows) {
    	Integer startpage=(page-1)*rows;
    	EasyUIPageVersion easyUIPageVersion = userService.selectVersions(cVersion,startpage,rows);
    	String jString = JsonUtils.ObjectAsString(easyUIPageVersion);
    	return jString;
    }
    @RequestMapping(value = "/addNotices")
    public String addNotices(CNotice cNotice,HttpServletRequest request) {
    	try {
    		Date date = new Date();
    		cNotice.setCreateTime(date);
    		userService.addNotices(cNotice);
		} catch (Exception e) {
		}
    	return "redirect:/infrastructure/selectNotice";
    }
    @RequestMapping(value = "/addVersion")
    public String addVersion(CVersion cVersion,HttpServletRequest request) {
    	try {
    		Date date = new Date();
    		cVersion.setCreateTime(date);
    		userService.addVersion(cVersion);
    	} catch (Exception e) {
    	}
    	return "redirect:/infrastructure/selectVersion";
    }
    @RequestMapping(value = "/selectbyid")
    @ResponseBody
    public CNotice selectbyid(Integer id) {
    	CNotice cNotice=userService.selectbyid(id);
    	
		return cNotice;
    }
    @RequestMapping(value = "/selectbyidversion")
    @ResponseBody
    public CVersion selectbyidversion(Integer id) {
    	CVersion cNotice=userService.selectbyidversion(id);
    	
    	return cNotice;
    }
    @RequestMapping(value = "/updateNotices")
    public String updateNotices(CNotice cNotice) {
    	Date date = new Date();
    	cNotice.setCreateTime(date);
    	userService.updateNotices(cNotice);
    	return "redirect:/infrastructure/selectNotice";
    }
    @RequestMapping(value = "/updateVersion")
    public String updateVersion(CVersion cVersion) {
    	Date date = new Date();
    	cVersion.setCreateTime(date);
    	userService.updateVersion(cVersion);
    	return "redirect:/infrastructure/selectVersion";
    }
    @RequestMapping(value = "/deleteNotice")
    public String deleteNotice(String ids) {
    	String[] id = ids.split(",");
    	for (String string : id) {
			userService.deleteNotice(Integer.parseInt(string));
		}
    	return "redirect:/infrastructure/selectNotice";
    }
    @RequestMapping(value = "/deleteVersion")
    public String deleteVersion(String ids) {
    	String[] id = ids.split(",");
    	for (String string : id) {
    		userService.deleteVersion(Integer.parseInt(string));
    	}
    	return "redirect:/infrastructure/selectVersion";
    }
    @RequestMapping(value = "/operateNotice", method = RequestMethod.POST)
    @ResponseBody
    public String operateNotice(Model model, HttpServletRequest request) {
        int type = Integer.parseInt(request.getParameter("type"));
        String content = request.getParameter("content");
        String downprostat = Constants.ERROR;
        try {
            if (type == 1) {
                int id = Integer.parseInt(request.getParameter("noticeId"));
                CNotice notice = new CNotice();
                notice.setContent(content);
                notice.setId(id);
                RemoteServiceSingleton.getInstance().getUserService().updateNotice(notice);
                downprostat = Constants.SUCCESS;
            } else if (type == 2) {
                int id = Integer.parseInt(request.getParameter("noticeId"));
                CNotice notice = new CNotice();
                notice.setId(id);
                RemoteServiceSingleton.getInstance().getUserService().deleteNoticeById(notice);
                downprostat = Constants.SUCCESS;
            } else if (type == 3) {
            	CNotice notice = new CNotice();
                notice.setContent(content);
                notice.setCreateTime(new Date());
                RemoteServiceSingleton.getInstance().getUserService().saveNotice(notice);
                downprostat = Constants.SUCCESS;
            }
        } catch (Exception e) {
            LOGGER.error("平台通知操作失败=======type：" + type + "========" + e.getMessage(), e);
        }
        return downprostat;
    }

    @RequestMapping(value = "/memberStarSettings")
    public String memberStarSettings(Model model){
        try{
            MemberStarService starService = RemoteServiceSingleton.getInstance().getMemberStarService();
            List<MemberStarSetting> starSettingList = new ArrayList<MemberStarSetting>();
            for(MemberStar ms:MemberStar.values()){
                starSettingList.add(starService.getMemberStarSetting(ms.getValue()));
            }
            model.addAttribute("starList",starSettingList);
            model.addAttribute("memberStar",MemberStar.values());
        } catch (Exception e) {
            LOGGER.error("会员星级设置查询失败！" + e.getMessage(), e);
        }
        return "/infrastructure/memberStarSettings";
    }

    @RequestMapping(value = "/updateMemberStarSettings", method = RequestMethod.POST)
    @ResponseBody
    public String updateMemberStarSettings(HttpServletRequest request,Integer ms,BigDecimal am,Integer mt,Integer nms,Integer nst,BigDecimal rm) {
        try{
            MemberStarService starService = RemoteServiceSingleton.getInstance().getMemberStarService();
            if(starService == null) new Exception("加载服务错误！");
            MemberStarSetting starSetting = new MemberStarSetting();
            starSetting.setMemberStar(ms);
            starSetting.setAmountMoney(am);
            starSetting.setMemberTotal(mt);
            starSetting.setNeedMemberStar(nms);
            starSetting.setNeedStarTotal(nst);
            starSetting.setReturnMultiple(rm);
            starService.updateMemberStarSetting(starSetting);
            return Constants.SUCCESS;
        }catch (Exception e){
            LOGGER.error("会员星级设置失败！！" + e.getMessage(), e);
            return Constants.ERROR;
        }
    }

    @RequestMapping(value = "/supplierRegionSettings")
    public String supplierRegionSettings(Model model,HttpServletRequest request){
        try{
            SupplierRegionService regionService = RemoteServiceSingleton.getInstance().getSupplierRegionService();
            PageBean<SupplierRegionSettings> regionSettingsPageBean = new PageBean<SupplierRegionSettings>();
            regionSettingsPageBean.setPageSize(Constants.PAGESIZE);
            String page = request.getParameter("page");
            if(page == null){
                page = "1";
                regionSettingsPageBean.setPage(1);
            }else{
                try{
                    regionSettingsPageBean.setPage(Integer.parseInt(page));
                }catch (Exception e){
                    page="1";
                    regionSettingsPageBean.setPage(1);
                }
            }
            regionSettingsPageBean.setParameter(new SupplierRegionSettings());
            List<SupplierRegionSettings> regionSettingsList = regionService.getRegionSettings(regionSettingsPageBean);
            regionSettingsPageBean.setResult(regionSettingsList);
            model.addAttribute("pageBean",regionSettingsPageBean);
            model.addAttribute("page",page);
        }catch (Exception e){
            LOGGER.error("企业区域设置查询失败！" + e.getMessage(), e);
        }
        return "/infrastructure/supplierRegionSettings";
    }
    @RequestMapping(value = "/supplierRegionSettingsxfg")
    public String supplierRegionSettingsxfg(Model model,HttpServletRequest request){
    	try{
    		SupplierRegionService regionService = RemoteServiceSingleton.getInstance().getSupplierRegionService();
    		PageBean<SupplierRegionSettings> regionSettingsPageBean = new PageBean<SupplierRegionSettings>();
    		regionSettingsPageBean.setPageSize(Constants.PAGESIZE);
    		String page = request.getParameter("page");
    		if(page == null){
    			page = "1";
    			regionSettingsPageBean.setPage(1);
    		}else{
    			try{
    				regionSettingsPageBean.setPage(Integer.parseInt(page));
    			}catch (Exception e){
    				page="1";
    				regionSettingsPageBean.setPage(1);
    			}
    		}
    		regionSettingsPageBean.setParameter(new SupplierRegionSettings());
    		List<SupplierRegionSettings> regionSettingsList = regionService.getRegionSettingsxfg(regionSettingsPageBean);
    		regionSettingsPageBean.setResult(regionSettingsList);
    		model.addAttribute("pageBean",regionSettingsPageBean);
    		model.addAttribute("page",page);
    	}catch (Exception e){
    		LOGGER.error("企业区域设置查询失败！" + e.getMessage(), e);
    	}
    	return "/infrastructure/supplierRegionsxfg";
    }

    
    //代理商挂管理设置查看
    @RequestMapping(value = "/manager")
    public String manager(Model model){
    	List<SupplierAgentType> list = supplierAgentTypeService.findAllSupplierAgentType();
    	model.addAttribute("list",list);
		return "/infrastructure/supplierManager";
    }
    
    //新增
    @RequestMapping(value = "/insertSupplier")
    @ResponseBody
    public void insert(Model model,SupplierAgentType supplierAgentType) throws IOException{
    	 //设置业务类型
    	 supplierAgentType.setBizType(1);
    	 //设置操作时间     
    	 Date date = new Date();
    	 supplierAgentType.setCreateDatetime(date);
    	 //设置状态
    	 supplierAgentType.setStatus(1);
    	int row = supplierAgentTypeService.insertSupplierAgentType(supplierAgentType);
    	JSON.json(row);
    }
    
    //修改
    @RequestMapping(value = "/updateSupplier")
    public void update(Model model,SupplierAgentType supplierAgentType,HttpServletResponse response) throws IOException{
    	
   	 //设置业务类型
   	 supplierAgentType.setBizType(1);
   	 //设置操作时间     
   	  Date date = new Date();
   	 supplierAgentType.setCreateDatetime(date);
   	 //设置状态
   	 supplierAgentType.setStatus(1);
     int row = supplierAgentTypeService.updateSupplierAgentType(supplierAgentType);
    	//TODOs
    	response.getWriter().write(row+"");
    }
    
    //删除
    @RequestMapping(value = "/deleteSupplier")
    @ResponseBody
    public void deleteSupplier(Model model,Integer id) throws Exception{
    	int row=supplierAgentTypeService.deleteByIdSupplierAgentType(id);
    	JSON.json(row);
    }
    
   
    
    
    //查看审批优惠券
   
   @RequestMapping(value = "/approve")
   public String approve(Model model,PageBean paramPage,Integer page){
   
    	//TODO
		if (null != page && page != 0) {
			paramPage.setPage(page);
		} else {
			paramPage.setPage(1);
		}
		SupplierApplyYhq supplierApplyYhq=new SupplierApplyYhq();
		paramPage.setParameter(supplierApplyYhq);
		paramPage.setOrder("DESC");
		paramPage.setPageSize(Constants.PAGESIZE);
		PageBean<SupplierApplyYhq> pb = supplierApplyYhqService.getYhqPageList(paramPage);
    	model.addAttribute("page",pb);
		return "/infrastructure/approve";
    	
    	
  }
    
   
   
    //审批优惠券
   @ResponseBody
   @RequestMapping(value="/checkPass")
    public int checkPass(BigDecimal validVal,long id,String supplierId) throws Exception{
	  
	   SupplierApplyYhq supplierApplyYhq=new SupplierApplyYhq ();
	   //设置id
	   supplierApplyYhq.setId(id);
	   //设置审批数量
	   supplierApplyYhq.setValidVal(validVal);
	   //设置审批时间
	   Date date=new Date();
	   supplierApplyYhq.setValidDatetime(date);
	   //设置审批状态
	   supplierApplyYhq.setStatus(2);
	  int row= supplierApplyYhqService.checkPass(supplierApplyYhq);
	
	 //修改用户的余额
	  User user = userService.findUserBySupplierId(Long.valueOf(supplierId));
	  Long userId = user.getUserId();
	  int row2 = psqwAccountRecordService.applyCoupon(userId, validVal);
	
   	return row;
    }
    
   //优惠券审核不通过
   @ResponseBody
   @RequestMapping(value="/checkNoPass")
   public int checkNoPass(String memo,long id) throws ParseException, Exception 
{
	   SupplierApplyYhq supplierApplyYhq=new SupplierApplyYhq ();
	  
	   //设置id
	   supplierApplyYhq.setId(id);
	   //设置审批时间
	   Date date=new Date();
	   supplierApplyYhq.setValidDatetime(date);
	   //设置审批状态
	   supplierApplyYhq.setStatus(3);
	   //设置审核不通过原因
	   String m = new String(memo.getBytes("iso-8859-1"),"utf-8");
	   supplierApplyYhq.setMemo(m);
	   int row=supplierApplyYhqService.checkNoPass(supplierApplyYhq);
	  
	   return row;
   }
    
    
    @RequestMapping(value = "/updateSupplierRegionSettings")
    @ResponseBody
    public String updateSupplierRegionSettings(@RequestParam(required = false) int mode, @RequestParam(required = false) int rid, @RequestParam(required = false) String text, @RequestParam(required = false) int dt,@RequestParam(required = false) BigDecimal zt){
        try{
            SupplierRegionService regionService = RemoteServiceSingleton.getInstance().getSupplierRegionService();
            SupplierRegionSettings regionSettings = new SupplierRegionSettings();
            regionSettings.setRegionId(rid);
            regionSettings.setRegionText(text);
            regionSettings.setRetDate(dt);
            regionSettings.setRetZk(zt);
            int rv=1;
            switch (mode){
                case 1:{regionService.insertRegionSettings(regionSettings);break;}
                case 2:{rv=regionService.deleteRegionSettings(regionSettings);break;}
                default:{regionService.updateRegionSettings(regionSettings);}
            }
            if(rv==-1){
                return Constants.SAMEISNULL;
            }else{
                return Constants.SUCCESS;
            }
        }catch (Exception e){
            LOGGER.error("企业区域设置修改失败！" + e.getMessage(), e);
        }
        return Constants.ERROR;
    }
    @RequestMapping(value = "/updateSupplierRegionSettingsxfg")
    @ResponseBody
    public String updateSupplierRegionSettingsxfg(@RequestParam(required = false) int mode, @RequestParam(required = false) int rid, @RequestParam(required = false) String text, @RequestParam(required = false) int dt,@RequestParam(required = false) BigDecimal zt){
    	try{
    		SupplierRegionService regionService = RemoteServiceSingleton.getInstance().getSupplierRegionService();
    		SupplierRegionSettings regionSettings = new SupplierRegionSettings();
    		regionSettings.setRegionId(rid);
    		regionSettings.setRegionText(text);
    		regionSettings.setRetDate(dt);
    		regionSettings.setRetZk(zt);
    		int rv=1;
    		switch (mode){
    		case 1:{regionService.insertRegionSettingsxfg(regionSettings);break;}
    		case 2:{rv=regionService.deleteRegionSettingsxfg(regionSettings);break;}
    		default:{regionService.updateRegionSettingsxfg(regionSettings);}
    		}
    		if(rv==-1){
    			return Constants.SAMEISNULL;
    		}else{
    			return Constants.SUCCESS;
    		}
    	}catch (Exception e){
    		LOGGER.error("企业区域设置修改失败！" + e.getMessage(), e);
    	}
    	return Constants.ERROR;
    }


    @RequestMapping(value = "/supplierRepaymentSettings")
    public String supplierRepaymentSettings(Model model){
        try{
            SupplierTypeService typeService = RemoteServiceSingleton.getInstance().getSupplierTypeService();
            List<SupplierTypeSettings> supplierTypeSettingsList = new ArrayList<SupplierTypeSettings>();
            for (SupplierType st: SupplierType.values()) {
                SupplierTypeSettings settings = new SupplierTypeSettings();
                settings.setSupplierType(st.getValue());
                settings.setProportionRepayment(typeService.getProportionRepayment(st.getValue()));
                supplierTypeSettingsList.add(settings);
            }
            model.addAttribute("supplierTypes",supplierTypeSettingsList);
        } catch (Exception e) {
            LOGGER.error("企业类型设置查询失败！" + e.getMessage(), e);
        }
        return "/infrastructure/supplierRepaymentSettings";
    }

    @RequestMapping(value = "/updateSupplierRepaymentSettings", method = RequestMethod.POST)
    @ResponseBody
    public String updateSupplierRepaymentSettings(HttpServletRequest request,Integer supplierType,BigDecimal proportionRepayment) {
        try {
            SupplierTypeService typeService = RemoteServiceSingleton.getInstance().getSupplierTypeService();
            if(typeService == null) new Exception("加载服务错误！");
            typeService.setProportionRepayment(supplierType,proportionRepayment);
            return Constants.SUCCESS;
        }catch (Exception e){
            LOGGER.error("收银系统设置失败！！" + e.getMessage(), e);
            return Constants.ERROR;
        }
    }

    @RequestMapping(value = "/cashierSettings")
    public String cashierSettings(Model model) {
        CashierSettings cashierSettings1 = null, cashierSettings2 = null, cashierSettings3 = null;
        try {
            List<CashierSettings> cashierSettingsList = RemoteServiceSingleton.getInstance().getCashierService().findCashierSettsingsAll();
            if (cashierSettingsList != null) {
                for (int i = 0; i < cashierSettingsList.size(); i++) {
                    switch (cashierSettingsList.get(i).getDividendType()) {
                        case 1: {
                            cashierSettings1 = cashierSettingsList.get(i);
                            break;
                        }
                        case 2: {
                            cashierSettings2 = cashierSettingsList.get(i);
                            break;
                        }
                        case 3: {
                            cashierSettings3 = cashierSettingsList.get(i);
                            break;
                        }
                        default: {
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("收银系统设置查询失败！" + e.getMessage(), e);
        }
        if (cashierSettings1 == null) cashierSettings1 = new CashierSettings();
        if (cashierSettings2 == null) cashierSettings2 = new CashierSettings();
        if (cashierSettings3 == null) cashierSettings3 = new CashierSettings();
        model.addAttribute("cashierSettings1", cashierSettings1);
        model.addAttribute("cashierSettings2", cashierSettings2);
        model.addAttribute("cashierSettings3", cashierSettings3);
        return "/infrastructure/cashierSettings";
    }

    @RequestMapping(value = "/updateCashierSettings", method = RequestMethod.POST)
    @ResponseBody
    public String updateCashierSettings(HttpServletRequest request,Integer cid1,BigDecimal rm1,BigDecimal pm1,BigDecimal cf1,BigDecimal gh1,BigDecimal gf1,Integer cid2,BigDecimal rm2,BigDecimal pm2,BigDecimal cf2,BigDecimal gh2,BigDecimal gf2,Integer cid3,BigDecimal rm3,BigDecimal pm3,BigDecimal cf3,BigDecimal gh3,BigDecimal gf3) {
        CashierService cashierService;
        try {
            cashierService = RemoteServiceSingleton.getInstance().getCashierService();
            if(cashierService == null) new Exception("加载服务错误！");
        }catch (Exception e){
            LOGGER.error("收银系统设置失败！！" + e.getMessage(), e);
            return Constants.ERROR;
        }
        try {
            CashierSettings cashierSettings = new CashierSettings();
            if (cid1 != null) {
                cashierSettings.setDividendType(1);
                cashierSettings.setCashierId(cid1);
                cashierSettings.setRealpayMultipe(rm1);
                cashierSettings.setPayMultiple(pm1);
                cashierSettings.setCounterFee(cf1);
                cashierSettings.setGiftHqj(gh1);
                cashierSettings.setGiftFhed(gf1);
                cashierService.updateCashierSettsingsByID(cashierSettings);
            }
            if (cid2 != null) {
                cashierSettings.setDividendType(2);
                cashierSettings.setCashierId(cid2);
                cashierSettings.setRealpayMultipe(rm2);
                cashierSettings.setPayMultiple(pm2);
                cashierSettings.setCounterFee(cf2);
                cashierSettings.setGiftHqj(gh2);
                cashierSettings.setGiftFhed(gf2);
                cashierService.updateCashierSettsingsByID(cashierSettings);
            }
            if (cid3 != null) {
                cashierSettings.setDividendType(3);
                cashierSettings.setCashierId(cid3);
                cashierSettings.setRealpayMultipe(rm3);
                cashierSettings.setPayMultiple(pm3);
                cashierSettings.setCounterFee(cf3);
                cashierSettings.setGiftHqj(gh3);
                cashierSettings.setGiftFhed(gf3);
                cashierService.updateCashierSettsingsByID(cashierSettings);
            }
            return Constants.SUCCESS;
        } catch (Exception e) {
            LOGGER.error("收银系统设置失败！！" + e.getMessage(), e);
            return Constants.ERROR;
        }
    }

    @RequestMapping(value = "/oneDividendRatio")
    public String oneDividendRatio(Model model) {
        OneDividendRatio oneDividendRatio = null;
        try {
            oneDividendRatio = RemoteServiceSingleton.getInstance().getDividendService().getOneDividendRatio();
        } catch (Exception e) {
            LOGGER.error("现金(红旗券)支付比例设置查询失败！" + e.getMessage(), e);
        }
        model.addAttribute("ratio", oneDividendRatio);
        return "/infrastructure/cashDividendRatio";
    }
    @RequestMapping(value = "/oneDividendRatio2")
    public String oneDividendRatio2(Model model) {
    	LimitNum limitNum = null;
    	try {
    		limitNum=dealerProductTagsService.findLimitNum();
    	} catch (Exception e) {
    		LOGGER.error("现金(红旗券)支付比例设置查询失败！" + e.getMessage(), e);
    	}
    	model.addAttribute("ratio", limitNum);
    	return "/infrastructure/cmaxDividendRatio2";
    }
    @RequestMapping(value = "/oneDividendRatio3")
    public String oneDividendRatio3(Model model) {
    	CMaxnumSetting cMaxnumSetting=null;
    	try {
    		cMaxnumSetting=tradeFeeService.findMaxNum();
    	} catch (Exception e) {
    		LOGGER.error("现金(红旗券)支付比例设置查询失败！" + e.getMessage(), e);
    	}
    	model.addAttribute("ratio", cMaxnumSetting);
    	return "/infrastructure/homeNumbersSet";
    }

    @RequestMapping(value = "/setOneDividendRatio", method = RequestMethod.POST)
    @ResponseBody
    public String setOneDividendRatio(HttpServletRequest request, BigDecimal cash, BigDecimal ticket) {
        try {
            OneDividendRatio oneDividendRatio = new OneDividendRatio();
            oneDividendRatio.setCashRation(cash);
            oneDividendRatio.setTicketRation(ticket);
            DividendService dividendService = RemoteServiceSingleton.getInstance().getDividendService();
            dividendService.setOneDividendRatio(oneDividendRatio);
            return Constants.SUCCESS;
        } catch (Exception e) {
            LOGGER.error("现金(红旗券)支付比例修改失败！！" + e.getMessage(), e);
            return Constants.ERROR;
        }
    }
    @RequestMapping(value = "/setOneDividendRatio2", method = RequestMethod.POST)
    @ResponseBody
    public String setOneDividendRatio2(HttpServletRequest request, BigDecimal cash) {
    	try {
    		dealerProductTagsService.setLimitNum(cash);
    		return Constants.SUCCESS;
    	} catch (Exception e) {
    		LOGGER.error("现金(红旗券)支付比例修改失败！！" + e.getMessage(), e);
    		return Constants.ERROR;
    	}
    }
    @RequestMapping(value = "/setOneDividendRatio3", method = RequestMethod.POST)
    @ResponseBody
    public String setOneDividendRatio3(HttpServletRequest request, CMaxnumSetting cMaxnumSetting) {
    	try {
    		cMaxnumSetting.setOperateTime(new Date());
    		tradeFeeService.insert(cMaxnumSetting);
    		return Constants.SUCCESS;
    	} catch (Exception e) {
    		LOGGER.error("现金(红旗券)支付比例修改失败！！" + e.getMessage(), e);
    		return Constants.ERROR;
    	}
    }

    @RequestMapping(value = "/companyDividend")
    public String companyDividend(Model model) {
        CompanyDividend comDiv = null;
        try {
            List<CompanyDividend> comDivList = RemoteServiceSingleton.getInstance().getDividendService().findCompanyDividendAll();
            if (comDivList != null && comDivList.size() > 0) {
                comDiv = comDivList.get(0);
            }
        } catch (Exception e) {
            LOGGER.error("企业分红设置查询失败！" + e.getMessage(), e);
        }
        model.addAttribute("comDiv", comDiv);
        return "/infrastructure/comDiv";
    }
    
    @RequestMapping(value = "/tradefee")
    public String tradefee(Model model) {
    	TradeFee tradeFee = null;
    	TradeFee tradeFee1 = null;
        try {
        	List<TradeFee> tradeFees = RemoteServiceSingleton.getInstance().getTradeFeeService().getTradeAll();
        	if (tradeFees.size()>0 && tradeFees!=null) {
        		for (TradeFee tradeFee2 : tradeFees) {
        			if (tradeFee2.getType()==1) {
        				tradeFee=tradeFee2;
        			}else if (tradeFee2.getType()==2) {
        				tradeFee1=tradeFee2;
        			}
        		}
			}
        } catch (Exception e) {
            LOGGER.error("平台交易费查询失败！" + e.getMessage(), e);
        }
        model.addAttribute("tradeFee", tradeFee);
        model.addAttribute("tradeFee1", tradeFee1);
        return "/infrastructure/tradefee";
    }

    @RequestMapping(value = "/updateCompanyDividend", method = RequestMethod.POST)
    @ResponseBody
    public String updateCompanyDividend(HttpServletRequest request,
                                        BigDecimal retZk, Integer retDate, Long compDividendId) {
        String downprostat = Constants.ERROR;
        if (compDividendId != null) {
            try {
                CompanyDividend comDiv = new CompanyDividend();
                comDiv.setCompDividendId(compDividendId);
                comDiv.setRetDate(retDate);
                comDiv.setRetZk(retZk);
                RemoteServiceSingleton.getInstance().getDividendService().updateCompanyDividend(comDiv);
                downprostat = Constants.SUCCESS;
                return downprostat;
            } catch (Exception e) {
                LOGGER.error("企业分红修改失败！！" + e.getMessage(), e);
                return downprostat;
            }
        } else {
            return downprostat;
        }
    }
    
    @RequestMapping(value = "/updateTradeFee")
    public String updateTradeFee(HttpServletRequest request,TradeFee tradeFee) {
      
            try {
                RemoteServiceSingleton.getInstance().getTradeFeeService().updateTradeFee(tradeFee);
            } catch (Exception e) {
                LOGGER.error("平台交易费设置失败！！" + e.getMessage(), e);
            }
            return "redirect:/infrastructure/tradefee";

    }

    @RequestMapping(value = "/memberDividend")
    public String memberDividend(Model model) {
        List<MemberDividend> memDivList = null;
        try {
            DividendService dividendService = RemoteServiceSingleton.getInstance().getDividendService();
            memDivList  = dividendService.findMemberDividendAll();
            boolean isExist;
            for (SupplierType st:SupplierType.values()){
                isExist = false;
                for (int i = 0; i < memDivList.size(); i++) {
                    if(memDivList.get(i).getAccountType()==st.getValue()){
                        isExist = true;
                        break;
                    }
                }
                if(isExist==false){
                    MemberDividend memberDividend = new MemberDividend();
                    memberDividend.setAccountType(st.getValue());
                    memDivList.add(dividendService.findMemberDividend(st.getValue()));
                }
            }
            for (MemberType mt:MemberType.values()){
                isExist = false;
                for (int i = 0; i < memDivList.size(); i++) {
                    if(memDivList.get(i).getAccountType()==mt.getValue()){
                        isExist = true;
                        break;
                    }
                }
                if(isExist==false){
                    MemberDividend memberDividend = new MemberDividend();
                    memberDividend.setAccountType(mt.getValue());
                    memDivList.add(dividendService.findMemberDividend(mt.getValue()));
                }
            }

        } catch (Exception e) {
            LOGGER.error("会员团队分红设置查询失败！" + e.getMessage(), e);
        }
        model.addAttribute("memDiv", memDivList);
        return "/infrastructure/memDiv";
    }

    @RequestMapping(value = "/updateMemberDividend", method = RequestMethod.POST)
    @ResponseBody
    public String updateMemberDividend(HttpServletRequest request,int stid,int stn,BigDecimal somd,BigDecimal simd){
        try {
            MemberDividend memDiv = new MemberDividend();
            memDiv.setAccountType(stid);
            memDiv.setTeamNumber(stn);
            memDiv.setIndirectMemberDividend(simd);
            memDiv.setOwnMemberDividend(somd);
            RemoteServiceSingleton.getInstance().getDividendService().updateMemberDividend(memDiv);
            return Constants.SUCCESS;
        } catch (Exception e) {
            LOGGER.error("会员团队分红修改失败！！" + e.getMessage(), e);
            return Constants.ERROR;
        }
    }

    /**
     * .
     * 跳转页面
     *
     * @return URL
     */
    @RequestMapping(value = "/oneDividend", method = RequestMethod.GET)
    public String getRetailerPage(Model model,@RequestParam(required = false) String companyBy) {
        try {
            List<SupplierRegionSettings> regionSettingsList = RemoteServiceSingleton.getInstance().getSupplierRegionService().getAllRegionSettings();
            model.addAttribute("regionList", regionSettingsList);
            if(companyBy == null) companyBy = "1";
            model.addAttribute("companyBy",Integer.parseInt(companyBy));
        }catch(Exception e) {
            LOGGER.error("企业区域查询失败！！" + e.getMessage(), e);
        }
        return "/infrastructure/oneDiv";
    }
    /**
     * .
     * 跳转页面
     *
     * @return URL
     */
    @RequestMapping(value = "/oneDividendzj", method = RequestMethod.GET)
    public String getRetailerPagezj(Model model,@RequestParam(required = false) String companyBy) {
    	try {
    		List<DictTags> regionSettingsList = dealerProductTagsService.selectByType(3);
    		model.addAttribute("regionList", regionSettingsList);
    		if(companyBy == null) companyBy = regionSettingsList.get(0).getTagCode();
    		model.addAttribute("companyBy",companyBy);
    	}catch(Exception e) {
    		LOGGER.error("企业区域查询失败！！" + e.getMessage(), e);
    	}
    	return "/infrastructure/zjTagsSettings";
    }

    @RequestMapping(value = "/oneDividend", method = RequestMethod.POST)
    public String getRetailerLabelPage(HttpServletRequest request, HttpServletResponse response, Integer companyBy) {
        OneDividend oneDiv = new OneDividend();
        List<OneDividend> ondDivList = new ArrayList<OneDividend>();
        try {
            if (companyBy != null) {
                oneDiv.setCompanyBy(companyBy);
                String companyText = RemoteServiceSingleton.getInstance().getSupplierRegionService().getRegionSettingsByID(companyBy).getRegionText();
                request.getSession().setAttribute("companyText",companyText);
                ondDivList = RemoteServiceSingleton.getInstance().getDividendService().findOneDividendsByType(oneDiv);
                request.getSession().setAttribute("ondDivList", ondDivList);
            }
            request.getRequestDispatcher(
                    "/WEB-INF/views/infrastructure/oneDivUpdate.jsp")
                    .forward(request, response);
        } catch (Exception e) {
            LOGGER.error("个人分红查询失败！！" + e.getMessage(), e);
        }
        return null;
    }
    @RequestMapping(value = "/oneDividendzj", method = RequestMethod.POST)
    public String getRetailerLabelPagezj(HttpServletRequest request, HttpServletResponse response, String companyBy) {
    	try {
    		if (companyBy != null) {
    			String companyText=dealerProductTagsService.findTagsBytagCode(companyBy);
    			request.getSession().setAttribute("companyText",companyText);
    			List<GiveTags> list=dealerProductTagsService.findTagsByTypecode(companyBy);
    			if (list==null||list.size()==0) {
    				for (int i = 0; i < 3; i++) {
    					GiveTags giveTags = new GiveTags();
    					giveTags.setDividendType(i+1);
    					giveTags.setTagCode(companyBy);
    					list.add(giveTags);
					}
    				
				}
    			request.getSession().setAttribute("ondDivList", list);
    		}
    		request.getRequestDispatcher(
    				"/WEB-INF/views/infrastructure/oneDivUpdatezj.jsp")
    		.forward(request, response);
    	} catch (Exception e) {
    		LOGGER.error("个人分红查询失败！！" + e.getMessage(), e);
    	}
    	return null;
    }

    @RequestMapping(value = "/updateOneDividend", method = RequestMethod.POST)
    @ResponseBody
    public String updateOneDividend(HttpServletRequest request, Long[] dividendId,
                                    BigDecimal[] giftFhed, BigDecimal[] giftHqj, Integer[] retDate) {
        try {
            for (int i = 0; i < dividendId.length; i++) {
                OneDividend oneDiv = new OneDividend();
                oneDiv.setDividendId(dividendId[i]);
                oneDiv.setGiftFhed(giftFhed[i]);
                oneDiv.setGiftHqj(giftHqj[i]);
                oneDiv.setRetDate(retDate[i]);
                RemoteServiceSingleton.getInstance().getDividendService().updateOneDividendById(oneDiv);
            }
            return "1";

        } catch (Exception e) {
            LOGGER.error("个人分红修改失败！！" + e.getMessage(), e);
            return "0";
        }
    }
    @RequestMapping(value = "/updateOneDividendzj", method = RequestMethod.POST)
    @ResponseBody
    public String updateOneDividendzj(HttpServletRequest request, String[] company,Integer[]  dividendId,
    		BigDecimal[] giftFhed, BigDecimal[] giftHqj) {
    	try {
    		for (int i = 0; i < giftFhed.length; i++) {
    			GiveTags giveTags = new GiveTags();
    			giveTags.setId(dividendId[i]);
    			giveTags.setTagCode(company[i]);
    			giveTags.setDividendType(i+1);
    			giveTags.setGiftFhed(giftFhed[i]);
    			giveTags.setGiftHqj(giftHqj[i]);
    			Date date = new Date();
    			giveTags.setOperateDate(date);
    			dealerProductTagsService.unpdatezj(giveTags);
    		}
    		return "1";
    		
    	} catch (Exception e) {
    		LOGGER.error("个人分红修改失败！！" + e.getMessage(), e);
    		return "0";
    	}
    }

    /**
     * .
     * 跳转页面到个人周期分紅設置
     *
     * @return URL
     */
    @RequestMapping(value = "/OneFhCycle", method = RequestMethod.GET)
    public String toOneFhCycle() {
        return "/infrastructure/oneFhCycle";
    }

    /**
     * .
     * 跳转页面到个人周期分紅設置
     *
     * @return URL
     */
    @RequestMapping(value = "/toAddFhCyclePlan", method = RequestMethod.GET)
    public String toAddFhCyclePlan() {
        return "/infrastructure/createFhCyclePlan";
    }

    /**
     * .
     * 跳转页面到个人周期分紅設置
     *
     * @return URL
     */
    @RequestMapping(value = "/toUpdateFhCyclePlan", method = RequestMethod.GET)
    public String toUpdateFhCyclePlan(HttpServletRequest request, Long cyclePlanId) {
        FhCyclePlan plan = RemoteServiceSingleton.getInstance().getFhCyclePlanService().findLastFhCyclePlanById(cyclePlanId);
        request.setAttribute("plan", plan);
        return "/infrastructure/updateFhCyclePlan";
    }

    /**
     * .
     * 查询最后一个周期截止时间
     *
     * @return URL
     */
    @RequestMapping(value = "/findLastCyclePlan")
    @ResponseBody
    public String findLastCyclePlan() {
        FhCyclePlan plan = RemoteServiceSingleton.getInstance().getFhCyclePlanService().findLastFhCyclePlan(null);
        if (plan != null) {
            long endTime = plan.getExecuteEndTime().getTime();
            Date nowTime = new Date();
            if (nowTime.getTime() > endTime) {
                return "1";
            } else {
                return "success";
            }
        }
        return "1";
    }

    /**
     * .
     * 查询最后一个周期截止时间
     *
     * @return URL
     */
    @RequestMapping(value = "/findCyclePlanList", method = RequestMethod.GET)
    public String findCyclePlanList(HttpServletRequest request) {
        List<FhCyclePlan> list = RemoteServiceSingleton.getInstance().getFhCyclePlanService().findFhCyclePlanList();
        LOGGER.info("-------------------------one------------------------");
        if (list != null && list.size() > 0) {
            LOGGER.info("-------------------------two------------------------");
            for (FhCyclePlan fhCyclePlan : list) {
                fhCyclePlan.setPlanTime(new Date());
            }
            LOGGER.info("-------------------------four------------------------");
        }
        LOGGER.info("-------------------------five------------------------");
        request.setAttribute("list", list);
        LOGGER.info("-------------------------six------------------------");
        return "/infrastructure/fhCyclePlanList";
    }

    /**
     * .
     * 查询最后一个周期截止时间
     *
     * @return URL
     */
    @RequestMapping(value = "/updateFhCyclePlan")
    @ResponseBody
    public String updateFhCyclePlan(HttpServletRequest request, Long cyclePlanId,
                                    Integer executeBatch,Integer orderType, Integer executeBatchTime, String orderEndTime, String executeStartTime) {
        try {
            FhCyclePlan plan = new FhCyclePlan();

            plan.setExecuteBatch(executeBatch);
            plan.setExecuteBatchTime(executeBatchTime);
            plan.setPlanTime(new Date());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            plan.setOrderEndTime(format.parse(orderEndTime));
            Date startTime = format.parse(executeStartTime);
            long time = startTime.getTime();
            time += executeBatchTime * 60 * 60 * 1000;
            Date endTime = new Date(time);
            plan.setExecuteStartTime(startTime);
            plan.setExecuteEndTime(endTime);

            if (cyclePlanId != null) {
                /**
                 * 因允许多个计划，注释掉此处
                FhCyclePlan lastPlan = RemoteServiceSingleton.getInstance().getFhCyclePlanService().findLastFhCyclePlan(cyclePlanId);
                if (lastPlan != null) {
                    if (lastPlan.getExecuteEndTime().getTime() > plan.getPlanTime().getTime()) {
                        return "error1";
                    }
                }*/
                plan.setCyclePlanId(cyclePlanId);
                boolean flag = RemoteServiceSingleton.getInstance().getFhCyclePlanService().updateFhCyclePlan(plan);
                LOGGER.info("---------------------更改计划下一步:  "+cyclePlanId+"----------------------------");
                return plan.getCyclePlanId() + "," + plan.getOrderEndTime().getTime()+","+orderType;
            } else {
                /**
                 * 因允许多个计划，注释掉此处
                FhCyclePlan lastPlan = RemoteServiceSingleton.getInstance().getFhCyclePlanService().findLastFhCyclePlan(null);
                if (lastPlan != null) {
                    if (lastPlan.getExecuteEndTime().getTime() > plan.getPlanTime().getTime()) {
                        return "error1";
                    }
                }*/
                boolean flag = RemoteServiceSingleton.getInstance().getFhCyclePlanService().saveFhCyclePlan(plan);
                FhCyclePlan newPlan = RemoteServiceSingleton.getInstance().getFhCyclePlanService().findMaxFhCyclePlan();
                LOGGER.info("---------------------创建计划下一步:  "+newPlan.getCyclePlanId()+"----------------------------");
                return newPlan.getCyclePlanId() + "," + newPlan.getOrderEndTime().getTime()+","+orderType;
            }
        } catch (Exception e) {
            LOGGER.error("updateFhCyclePlan :更新周期计划失败！ failed!!!!" + e.getMessage(), e);
            return "error2";
        }
    }

    /**
     * .
     * 查询最后一个周期截止时间
     *
     * @return URL
     */
    @RequestMapping(value = "/toAddFhCyclePlanImpl")
    public String toAddFhCyclePlanImpl(HttpServletRequest request, Model model,@RequestParam(required = false) String orderIds) {
        //cyclePlanId
        try {
            String param = request.getParameter("cyclePlanId");
            String[] params = param.split(",");
            Long cyclePlanId = Long.valueOf(params[0]);
            PageBean<OrderDTO> pageBean = new PageBean<OrderDTO>();
            OrderDTO orderDTO = new OrderDTO();
            if (params.length == 3) {
                Long orderEndTime = Long.valueOf(params[1]);
                orderDTO.setFhFlag((short) 0);
                List<Short> statusAll = new ArrayList<Short>();
                statusAll.add(Short.parseShort("21"));
                statusAll.add(Short.parseShort("31"));
                statusAll.add(Short.parseShort("41"));
                statusAll.add(Short.parseShort("81"));
                statusAll.add(Short.parseShort("91"));
                statusAll.add(Short.parseShort("101"));
                orderDTO.setEndDate(new Date(orderEndTime));
                orderDTO.setStatusList(statusAll);
            } else {
            	int page = 1;
            	try{
                    page = Integer.parseInt(request.getParameter("page"));
                }catch (Exception e){
                }
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("cyclePlanId", cyclePlanId);
               
                pageBean.setPage(page);
                pageBean.setParameter(map);
                pageBean.setPageSize(200);
              
                pageBean = RemoteServiceSingleton.getInstance().getFhCyclePlanService().getFhCyclePlanOrdersByListPage(pageBean);

                model.addAttribute("editFlag", "1");
                String[] orders;
                if(null != orderIds ) {
                    orders = orderIds.split(",");
                }else{
                    orders = new String[0];
                }
                if(null != pageBean){
                    List<OrderDTO> orderList = pageBean.getResult();
                    if(null != orderList && orderList.size()>0){
                        for (OrderDTO orderDTO2 : orderList) {
                            List<ProductDTO> productList = orderDTO2.getProductList();
                            if(null != productList && productList.size()>0){
                                for (ProductDTO productDTO : productList) {
                                    String productImgUrl = productDTO.getImgUrl();
                                    if(!productImgUrl.startsWith("http") || !productImgUrl.startsWith("Http")){
                                        productImgUrl = Constants.P1 + productImgUrl;
                                        productDTO.setImgUrl(productImgUrl);
                                    }
                                }
                            }
                            orderDTO2.setOrderCancelStatus(0);
                            for(int i=orders.length-1;i>=0;i--){
                                if(orderDTO2.getId().toString().equals(orders[i])){
                                    orders[i]="";
                                    orderDTO2.setOrderCancelStatus(1);
                                    break;
                                }
                            }
                        }
                    }
                }
                model.addAttribute("pageBean", pageBean);
                model.addAttribute("cyclePlanId", cyclePlanId);
                model.addAttribute("cyclePlanIdParams",request.getParameter("cyclePlanId"));
                model.addAttribute("orders",orders);
                String editFlag = request.getParameter("editFlag");
                if (editFlag != null) {
                    model.addAttribute("editFlag", editFlag);
                }
                LOGGER.info("---------------------查看计划    "+cyclePlanId+"----------------------------");
                return "/infrastructure/B2Corder_list_model2";
            }
            int page = 1;//没有分页码的话就检索第一页
            try{
                page = Integer.parseInt(request.getParameter("page"));
            }catch (Exception e){
            }
            pageBean.setPage(page);
            pageBean.setParameter(orderDTO);
            pageBean.setPageSize(200);
            if (!params[2].equals("null")&&params[2]!="") {
				orderDTO.setOrderType(Short.parseShort(params[2]));
				
			}
            CustomerOrderService customerOrderService = RemoteServiceSingleton.getInstance().getCustomerOrderService();
            pageBean = customerOrderService.findCustomerOrdersByFh(pageBean);
            String[] orders;
            if(null != orderIds ) {
                orders = orderIds.split(",");
            }else{
                orders = new String[0];
            }
            if(null != pageBean){
                List<OrderDTO> orderList = pageBean.getResult();
                if(null != orderList && orderList.size()>0){
                    for (OrderDTO orderDTO2 : orderList) {
                        List<ProductDTO> productList = orderDTO2.getProductList();
                        if(null != productList && productList.size()>0){
                            for (ProductDTO productDTO : productList) {
                                String productImgUrl = productDTO.getImgUrl();
                                if(!productImgUrl.startsWith("http") || !productImgUrl.startsWith("Http")){
                                    productImgUrl = Constants.P1 + productImgUrl;
                                    productDTO.setImgUrl(productImgUrl);
                                }
                            }
                        }
                        orderDTO2.setOrderCancelStatus(0);
                        for(int i=orders.length-1;i>=0;i--){
                            if(orderDTO2.getId().toString().equals(orders[i])){
                                orders[i]="";
                                orderDTO2.setOrderCancelStatus(1);
                                break;
                            }
                        }
                    }
                }
            }
            model.addAttribute("pageBean", pageBean);
            model.addAttribute("cyclePlanId", cyclePlanId);
            model.addAttribute("cyclePlanIdParams",request.getParameter("cyclePlanId"));
            model.addAttribute("orders",orders);
            String editFlag = request.getParameter("editFlag");
            if (editFlag != null) {
                model.addAttribute("editFlag", editFlag);
            }
            LOGGER.info("---------------------创建计划    "+cyclePlanId+"----------------------------");
            return "/infrastructure/B2Corder_list_model";
        } catch (Exception e) {
            LOGGER.error("toAddFhCyclePlanImpl :查看周期计划明细失败！ failed!!!!" + e.getMessage(), e);
            return "error";
        }
    }

    
    @RequestMapping(value = "/toUpdateFhCyclePlanImpl")
    public String toUpdateFhCyclePlanImpl(HttpServletRequest request, Model model,@RequestParam(required = false) String orderIds) {
        //cyclePlanId
        try {
            String param = request.getParameter("cyclePlanId");
            LOGGER.info("---------------------toUpdateFhCyclePlanImpl----------------------------");
            LOGGER.info("---------------------"+param+"----------------------------");
            String[] params = param.split(",");
            Long cyclePlanId = Long.valueOf(params[0]);
            PageBean<OrderDTO> pageBean = new PageBean<OrderDTO>();
            OrderDTO orderDTO = new OrderDTO();
            if (params.length == 3) {
                Long orderEndTime = Long.valueOf(params[1]);
                orderDTO.setFhFlag((short) 0);
                List<Short> statusAll = new ArrayList<Short>();
                statusAll.add(Short.parseShort("21"));
                statusAll.add(Short.parseShort("31"));
                statusAll.add(Short.parseShort("41"));
                statusAll.add(Short.parseShort("81"));
                statusAll.add(Short.parseShort("91"));
                statusAll.add(Short.parseShort("101"));
                orderDTO.setEndDate(new Date(orderEndTime));
                orderDTO.setStatusList(statusAll);
            } else {
            	int page = 1;
            	try{
                    page = Integer.parseInt(request.getParameter("page"));
                }catch (Exception e){
                }
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("cyclePlanId", cyclePlanId);
               
                pageBean.setPage(page);
                pageBean.setParameter(map);
                pageBean.setPageSize(200);
              
                pageBean = RemoteServiceSingleton.getInstance().getFhCyclePlanService().getFhCyclePlanOrdersByListPage(pageBean);

                model.addAttribute("editFlag", "1");
                String[] orders;
                if(null != orderIds ) {
                    orders = orderIds.split(",");
                }else{
                    orders = new String[0];
                }
                if(null != pageBean){
                    List<OrderDTO> orderList = pageBean.getResult();
                    if(null != orderList && orderList.size()>0){
                        for (OrderDTO orderDTO2 : orderList) {
                            List<ProductDTO> productList = orderDTO2.getProductList();
                            if(null != productList && productList.size()>0){
                                for (ProductDTO productDTO : productList) {
                                    String productImgUrl = productDTO.getImgUrl();
                                    if(!productImgUrl.startsWith("http") || !productImgUrl.startsWith("Http")){
                                        productImgUrl = Constants.P1 + productImgUrl;
                                        productDTO.setImgUrl(productImgUrl);
                                    }
                                }
                            }
                            orderDTO2.setOrderCancelStatus(0);
                            for(int i=orders.length-1;i>=0;i--){
                                if(orderDTO2.getId().toString().equals(orders[i])){
                                    orders[i]="";
                                    orderDTO2.setOrderCancelStatus(1);
                                    break;
                                }
                            }
                        }
                    }
                }
                model.addAttribute("pageBean", pageBean);
                model.addAttribute("cyclePlanId", cyclePlanId);
                model.addAttribute("cyclePlanIdParams",request.getParameter("cyclePlanId"));
                model.addAttribute("orders",orders);
                String editFlag = request.getParameter("editFlag");
                if (editFlag != null) {
                    model.addAttribute("editFlag", editFlag);
                }
                return "/infrastructure/B2Corder_list_model2";
            }
            int page = 1;//没有分页码的话就检索第一页
            try{
                page = Integer.parseInt(request.getParameter("page"));
            }catch (Exception e){
            }
            pageBean.setPage(page);
            //pageBean.setParameter(orderDTO);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("cyclePlanId", cyclePlanId);
            map.put("orderDTO", orderDTO);
            pageBean.setParameter(map);
            pageBean.setPageSize(200);
            if (!params[2].equals("null")&&params[2]!="") {
				orderDTO.setOrderType(Short.parseShort(params[2]));
				
			}
            CustomerOrderService customerOrderService = RemoteServiceSingleton.getInstance().getCustomerOrderService();
            pageBean = customerOrderService.updateCustomerOrdersByFh(pageBean);
            String[] orders;
            if(null != orderIds ) {
                orders = orderIds.split(",");
            }else{
                orders = new String[0];
            }
            if(null != pageBean){
                List<OrderDTO> orderList = pageBean.getResult();
                if(null != orderList && orderList.size()>0){
                    for (OrderDTO orderDTO2 : orderList) {
                        List<ProductDTO> productList = orderDTO2.getProductList();
                        if(null != productList && productList.size()>0){
                            for (ProductDTO productDTO : productList) {
                                String productImgUrl = productDTO.getImgUrl();
                                if(!productImgUrl.startsWith("http") || !productImgUrl.startsWith("Http")){
                                    productImgUrl = Constants.P1 + productImgUrl;
                                    productDTO.setImgUrl(productImgUrl);
                                }
                            }
                        }
                        orderDTO2.setOrderCancelStatus(0);
                        for(int i=orders.length-1;i>=0;i--){
                            if(orderDTO2.getId().toString().equals(orders[i])){
                                orders[i]="";
                                orderDTO2.setOrderCancelStatus(1);
                                break;
                            }
                        }
                    }
                }
            }
            model.addAttribute("pageBean", pageBean);
            model.addAttribute("cyclePlanId", cyclePlanId);
            model.addAttribute("cyclePlanIdParams",request.getParameter("cyclePlanId"));
            model.addAttribute("orders",orders);
            String editFlag = request.getParameter("editFlag");
            if (editFlag != null) {
                model.addAttribute("editFlag", editFlag);
            }
            LOGGER.info("---------------------更改计划中的订单  "+cyclePlanId+"----------------------------");
            return "/infrastructure/B2Corder_list_model";
        } catch (Exception e) {
            LOGGER.error("toAddFhCyclePlanImpl :查看周期计划明细失败！ failed!!!!" + e.getMessage(), e);
            return "error";
        }
    }
    /**
     * .
     * 给计划增加订单
     *
     * @return URL
     */
    @RequestMapping(value = "/addPlanImpl")
    @ResponseBody
    public String addPlanImpl(HttpServletRequest request, Long[] orderIds, Long cyclePlanId) {
        try {
        	Arrays.sort(orderIds);
            FhCyclePlan plan = RemoteServiceSingleton.getInstance().getFhCyclePlanService().findLastFhCyclePlanById(cyclePlanId);
            Integer batch = plan.getExecuteBatch();
            Integer batchTime = plan.getExecuteBatchTime();
            long startTime = plan.getExecuteStartTime().getTime();
            List<FhCyclePlanImpl> list = new ArrayList<FhCyclePlanImpl>();
            int c = orderIds.length / batch;
            Date executeTime = null;
            for (int i = 1; i <= batch; i++) {
                if (i == 1) {
                    int d = i * c;
                    executeTime = new Date(startTime + batchTime);
                    for (int j = 0; j < d; j++) {
                        FhCyclePlanImpl impl = new FhCyclePlanImpl();
                        impl.setOrderId(orderIds[j]);
                        impl.setCyclePlanId(cyclePlanId);
                        impl.setExecuteTime(executeTime);
                        list.add(impl);
                    }
                }
                if (i < batch - 1) {
                    int d = i * c;
                    int e = (i + 1) * c;
                    executeTime = new Date(startTime + (batchTime * 60 * 60 * 1000 / batch * i));
                    for (int j = d; j < e; j++) {
                        FhCyclePlanImpl impl = new FhCyclePlanImpl();
                        impl.setOrderId(orderIds[j]);
                        impl.setCyclePlanId(cyclePlanId);
                        impl.setExecuteTime(executeTime);
                        list.add(impl);
                    }
                }
                if (i == batch - 1) {
                    int d = (i) * c;
                    executeTime = new Date(startTime + (batchTime * 60 * 60 * 1000 / batch * i));
                    for (int j = d; j < orderIds.length; j++) {
                        FhCyclePlanImpl impl = new FhCyclePlanImpl();
                        impl.setOrderId(orderIds[j]);
                        impl.setCyclePlanId(cyclePlanId);
                        impl.setExecuteTime(executeTime);
                        list.add(impl);
                    }
                }
            }
            System.out.println("------------------");
            RemoteServiceSingleton.getInstance().getFhCyclePlanService().saveFhCyclePlanImpls(list);
            return "success";
        } catch (Exception e) {
            LOGGER.error("addPlanImpl :新增周期计划明细失败！ failed!!!!" + e.getMessage(), e);
            return "error";
        }
    }

    /**
     * .
     * 给计划增加订单
     *
     * @return URL
     */
    @RequestMapping(value = "/deletePlan")
    @ResponseBody
    public String deletePlan(HttpServletRequest request, Long cyclePlanId) {
        try {
            RemoteServiceSingleton.getInstance().getFhCyclePlanService().deleteFhCyclePlan(cyclePlanId);
            return "success";
        } catch (Exception e) {
            LOGGER.error("deletePlan :删除周期计划失败！ failed!!!!" + e.getMessage(), e);
            return "error";
        }
    }
    
    //进入二维码导出页面     入参（状态：1未打印 2已打印；page；pageSize）
    @RequestMapping(value = "/code")
    public String code(Integer page, Integer status,Integer pageSize,Model model){
    	
    	if(page == null){
    		page = 1;
    	}
    	if(pageSize==null){
    		pageSize=10;
    	}
    	if(status==null){
    		status=1;
    	}
            PageBean<BizRcode> pageBean = userService.findNullBzRcodePage(status, page, pageSize);
            model.addAttribute("page",pageBean); 
            model.addAttribute("status",status); 
    	return "/infrastructure/code";
    }
    
    
    
    //进入生成二维码页面  
    @RequestMapping(value = "/codeCreate")
    public String codeCreate(){
    	
    	return "/infrastructure/codeCreate";
    }
    
    
    //生成二维码   
    @RequestMapping(value = "/codeCreateGet")
    @ResponseBody
    public Integer codeCreateGet(int num){
    	 String id = getCurrentUser().getId();
    	Integer row = userService.creareBizRcode(num,id);
    	
    	 return row;
    }
  
    
    //Excel表格导出
    @RequestMapping(value = "/excelOut")
    public void excelOut(HttpServletRequest request, HttpServletResponse response,String ids) throws IOException{
    	List list=new ArrayList();
    	List<BizRcode> list2=new ArrayList();
    	if(ids!=null){
    		String[] split = ids.split(",");
        	
        	for (String s : split) {
    			list.add(s);
    		}
        	for (int i = 0; i < list.size(); i++) {
        		String id = (String) list.get(i);
        		BizRcode bizRcode=userService.findBzRcodeByRcodeid(id);
        		list2.add(bizRcode);
			}
    	}
    	
    	HSSFWorkbook wb=new HSSFWorkbook();
    	
    	HSSFCellStyle cellStyle = wb.createCellStyle();
    	HSSFDataFormat dataFormat = wb.createDataFormat();
    	 cellStyle.setDataFormat(dataFormat.getFormat("yyyy-MM-dd HH:mm:ss"));
    	
    	HSSFSheet sheet = wb.createSheet("二维码数据");
    	HSSFRow hr = sheet.createRow(0);
    	hr.createCell(0).setCellValue("创建id");
    	hr.createCell(1).setCellValue("创建人");
    	hr.createCell(2).setCellValue("二维码类型");
		hr.createCell(3).setCellValue("二维码地址");
		hr.createCell(4).setCellValue("创建时间");
		//rcodetype
		//遍历list集合,将集合中的数据写入Excel表格
    	for (int i = 0; i < list2.size(); i++) {
    		
    		BizRcode bizRcode = list2.get(i);
    		String rcodeid = bizRcode.getRcodeid();
    		userService.updateBizRcodeStatus(rcodeid, 2);
    		Date createDatetime = bizRcode.getCreateDatetime();
    		DateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		String dateString = formater.format(createDatetime);
    		 HSSFRow hrr = sheet.createRow(i + 1);
			//创建数据行
    		hrr.createCell(0).setCellValue(bizRcode.getId());
    		hrr.createCell(1).setCellValue(bizRcode.getCreateBy());//创建人
    		hrr.createCell(2).setCellValue("商家结算二维码");//二维码类型
    		hrr.createCell(3).setCellValue(bizRcode.getRcodeImgTxt());//二维码地址
    		HSSFCell cell = hrr.createCell(4) ;
    		cell.setCellStyle(cellStyle);
    		cell.setCellValue(dateString); //创建时间
		}
    	String filename = "二维码数据.xls";
    	response.setHeader("Content-Disposition","attachment;filename=" + filename + ".xls");
    	response.setContentType("application/msexcel");
    	ServletOutputStream out = response.getOutputStream();
    	wb.write(out);
    	
    }
	
    
    
    
  //Excel表格导出所有
    @RequestMapping(value = "/excelOutAll")
    public void excelOutAll(HttpServletRequest request, HttpServletResponse response,Integer status) throws IOException{
    	//sList list=new ArrayList();
    	//	List<BizRcode> list2=new ArrayList();
    	List<BizRcode>list=userService.findBizRcodeByStatus(status);
    
    	
    	HSSFWorkbook wb=new HSSFWorkbook();
    	
    	HSSFCellStyle cellStyle = wb.createCellStyle();
    	HSSFDataFormat dataFormat = wb.createDataFormat();
    	 cellStyle.setDataFormat(dataFormat.getFormat("yyyy-MM-dd HH:mm:ss"));

    	
    	HSSFSheet sheet = wb.createSheet("二维码数据");
    	
    	HSSFRow hr = sheet.createRow(0);
    	hr.createCell(0).setCellValue("创建id");
    	hr.createCell(1).setCellValue("创建人");
    	hr.createCell(2).setCellValue("二维码类型");
		hr.createCell(3).setCellValue("二维码地址");
		hr.createCell(4).setCellValue("创建时间");
		//rcodetype
		//遍历list集合,将集合中的数据写入Excel表格
    	for (int i = 0; i < list.size(); i++) {
    		
    		BizRcode bizRcode = list.get(i);
    		
    		String rcodeid = bizRcode.getRcodeid();
    		userService.updateBizRcodeStatus(rcodeid, 2);
    		Date createDatetime = bizRcode.getCreateDatetime();
    		DateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		String dateString = formater.format(createDatetime);
    		 HSSFRow hrr = sheet.createRow(i + 1);
			//创建数据行
//			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
    		hrr.createCell(0).setCellValue(bizRcode.getId());
    		hrr.createCell(1).setCellValue(bizRcode.getCreateBy());//创建人
    		hrr.createCell(2).setCellValue("商家结算二维码");//二维码类型
    		hrr.createCell(3).setCellValue(bizRcode.getRcodeImgTxt());//二维码地址
    		HSSFCell cell = hrr.createCell(4) ;
    		cell.setCellStyle(cellStyle);
    		cell.setCellValue(dateString); //创建时间
		}
    	String filename = "二维码数据.xls";
    	response.setHeader("Content-Disposition","attachment;filename=" + filename + ".xls");
    	response.setContentType("application/msexcel");
    	ServletOutputStream out = response.getOutputStream();
    	wb.write(out);
    	
    }
	
    
    
}
