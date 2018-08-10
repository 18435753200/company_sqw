package com.mall.controller.product;

import java.math.BigDecimal;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mall.architect.logging.Log;
import com.mall.architect.logging.LogFactory;
import com.mall.bean.authority.User;
//import com.mall.pay.common.StringUtils;
import com.mall.controller.base.BaseController;
import com.mall.customer.model.VirementSetting;
import com.mall.customer.service.SqwAccountRecordService;
import com.mall.customer.service.VirementSettingService;


/**
 * 转账配置Controller
 *
 * @author lipeng
 */

@Controller
@RequestMapping(value = "/setting")
public class VirementSettingController extends BaseController {

    /**
     * LOGGER.
     */
    private static final Log LOGGER = LogFactory.getLogger(VirementSettingController.class);

    @Autowired
    private VirementSettingService virementSettingService;

    @Autowired
    private SqwAccountRecordService sqwAccountRecordService;


    /**
     * 展示商品价格配置
     *
     * @return ModelAndView
     */
    @RequestMapping(value = "/showVirementSetting", method = RequestMethod.GET)
    public ModelAndView showVirementSetting(@RequestParam(value = "message", required = false) String message) {
        ModelAndView mav = new ModelAndView();
        User user = getCurrentUser();
        VirementSetting vs = null;
        try {
            vs = virementSettingService.findVirementSetting();
        } catch (Exception e) {
            mav.addObject("code", "-1");
            LOGGER.error(" Error : supplier_id=" + user.getId());
        }

        mav.setViewName("setting/virementSetting");
        mav.addObject("vs", vs);
        mav.addObject("message", StringUtils.isEmpty(message) ? null : message);
        LOGGER.info(" message = " + message);
        LOGGER.info(" showVirementSetting " + vs);
        return mav;
    }

    /**
     * 保存商品价格配置
     *
     * @return ModelAndView
     */
    @RequestMapping(value = "/saveVirementSetting")
    public ModelAndView saveVirementSetting(String virementId,
                                            Double virementFee,
                                            Integer virementFeeUpperLimit,
                                            Integer virementFeeUpperLimitDay,
                                            Integer virementFeeUpperLimitDeal) {

        ModelAndView mav = new ModelAndView();
        String msg = null;

        User user = getCurrentUser();
        VirementSetting vs = new VirementSetting();
        if (null == virementId || virementId.isEmpty()) {
        } else {
            vs.setId(Integer.valueOf(virementId));
        }

        vs.setVirementFee(new BigDecimal(virementFee));
        vs.setVirementFeeUpperLimitDay(virementFeeUpperLimitDay);
        vs.setVirementFeeUpperLimit(virementFeeUpperLimit);
        vs.setVirementFeeUpperLimitDeal(virementFeeUpperLimitDeal);
        vs.setOperateUser(user.getSequenceId().intValue());
        vs.setOperateDate(Calendar.getInstance().getTime());

        int effectRow = -1;
        try {
            effectRow = virementSettingService.saveOrUpdateVirementSetting(vs);
        } catch (Exception e) {
            LOGGER.error(" Error : supplier_id=" + user.getId());
        }

        if (effectRow > 0) {
            mav.addObject("message", "success");
        } else {
            mav.addObject("message", "failed");
        }
        LOGGER.info(" effectRow = " + effectRow);
        mav.setViewName("redirect:/setting/showVirementSetting");
        return mav;

    }
}	