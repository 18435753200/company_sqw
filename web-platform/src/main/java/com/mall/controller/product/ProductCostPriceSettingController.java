package com.mall.controller.product;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.bean.authority.User;
import com.mall.customer.model.ProductCostPriceSetting;
import com.mall.customer.service.ProductCostPriceSettingService;
//import com.mall.pay.common.StringUtils;
import com.mall.controller.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

/**
 * .
 * 商品成本价配置Controller
 *
 * @author lipeng
 */

@Controller
@RequestMapping(value = "/setting")
public class ProductCostPriceSettingController extends BaseController {

    /**
     * LOGGER.
     */
    private static final Log LOGGER = LogFactory.getLogger(ProductCostPriceSettingController.class);

    @Autowired
    private ProductCostPriceSettingService productCostPriceSettingService;

    /**
     * 展示商品价格配置
     *
     * @return ModelAndView
     */
    @RequestMapping(value = "/showProductCostPriceSetting", method = RequestMethod.GET)
    public ModelAndView showProductCostPriceSetting(@RequestParam(value = "message", required = false) String message) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("setting/productCostPriceSetting");

        User user = getCurrentUser();
        ProductCostPriceSetting pcp = null;
        ProductCostPriceSetting pcp1 = null;
        ProductCostPriceSetting pcp2 = null;
        try {
            List<ProductCostPriceSetting> findProductCostPriceSettingAll = productCostPriceSettingService.findProductCostPriceSettingAll();
            for (ProductCostPriceSetting productCostPriceSetting : findProductCostPriceSettingAll) {
				if (productCostPriceSetting.getType()==1) {
					pcp=productCostPriceSetting;
				}else if (productCostPriceSetting.getType()==2) {
					pcp1=productCostPriceSetting;
				}else if (productCostPriceSetting.getType()==3) {
					pcp2=productCostPriceSetting;
				}
			}
        } catch (Exception e) {
            mav.addObject("code", "-1");
            LOGGER.error(" Error : supplier_id=" + user.getId());
        }
        LOGGER.info(" message = " + message);
        LOGGER.info(" showProductCostPriceSetting " + pcp);

        mav.addObject("message", StringUtils.isEmpty(message) ? null : message);
        mav.addObject("pcp", pcp);
        mav.addObject("pcp1", pcp1);
        mav.addObject("pcp2", pcp2);
        return mav;
    }

    /**
     * 保存商品价格配置
     *
     * @param costPriceMultiple
     * @return ModelAndView
     */
    @RequestMapping(value = "/saveProductCostPriceSetting")
    public ModelAndView saveProductCostPriceSetting(String costPriceId, Double costPriceMultiple,Integer type) {
        ModelAndView mav = new ModelAndView();

        User user = getCurrentUser();
        ProductCostPriceSetting pcp = new ProductCostPriceSetting();
        if (null == costPriceId || costPriceId.isEmpty()) {
        } else {
            pcp.setId(Integer.valueOf(costPriceId));
        }
        pcp.setCostPriceMultiple(new BigDecimal(costPriceMultiple));
        pcp.setOperateUser(user.getSequenceId().intValue());
        pcp.setOperateDate(Calendar.getInstance().getTime());
        pcp.setType(type);
        int effectRow = -1;
        try {
            effectRow = productCostPriceSettingService.saveOrUpdateProductCostPriceSetting(pcp);
        } catch (Exception e) {
            LOGGER.error(" Error : supplier_id=" + user.getId());
        }

        if (effectRow > 0) {
            mav.addObject("message", "success");
        } else {
            mav.addObject("message", "failed");
        }
        mav.setViewName("redirect:/setting/showProductCostPriceSetting");
        return mav;

    }
}	