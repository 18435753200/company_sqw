package com.mall.controller.wechat;

import com.mall.controller.base.BaseController;
import com.mall.third.api.wechat.WxNotifyService;
import com.mall.third.api.wechat.WxPortalService;
import com.mall.third.dto.WxMsgDto;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * 接收微信通知
 *
 * @author zhoushuyi
 * @date 2018/7/4
 */
@Controller
@RequestMapping("/wechat/portal")
public class WxPortalController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(WxPortalController.class);


    @Autowired(required = false)
    private WxPortalService wxPortalService;

    @Autowired(required = false)
    private WxNotifyService wxNotifyService;


    /**
     * 微信认证
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */
    @ResponseBody
    @RequestMapping(produces = "text/plain;charset=utf-8", method = RequestMethod.GET)
    public String authGet(@RequestParam(value = "signature", required = false) String signature,
                          @RequestParam(value = "timestamp", required = false) String timestamp,
                          @RequestParam(value = "nonce", required = false) String nonce,
                          @RequestParam(value = "echostr", required = false) String echostr) {

        log.info("接收到来自微信服务器的认证消息：[{}, {}, {}, {}", signature, timestamp, nonce, echostr);

        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }

        if (wxPortalService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }

        return "非法请求";
    }


    @RequestMapping("/sendWxTemMsg")
    @ResponseBody
    public String wxTemMsg(String openId, String userId) {

        return wxNotifyService.notifySupplierPaySuccess(userId, openId, "123456", BigDecimal.TEN, "987877", "订单表述");
    }



    /**
     * 接收微信事件通知及消息
     * @param requestBody
     * @param signature
     * @param encType
     * @param msgSignature
     * @param timestamp
     * @param nonce
     * @return
     */
    @ResponseBody
    @RequestMapping(produces = "application/xml; charset=UTF-8", method = RequestMethod.POST)
    public String post(@RequestBody String requestBody,
                       @RequestParam("signature") String signature,
                       @RequestParam(value = "encrypt_type", required = false) String encType,
                       @RequestParam(value = "msg_signature", required = false) String msgSignature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce) {


        log.info("接收微信请求：[signature=[{}], encType=[{}], msgSignature=[{}], timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                signature, encType, msgSignature, timestamp, nonce, requestBody);

        if (!wxPortalService.checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }

        WxMsgDto wxMsgDto = new WxMsgDto();
        wxMsgDto.setEncType(encType);
        wxMsgDto.setMsgSignature(msgSignature);
        wxMsgDto.setNonce(nonce);
        wxMsgDto.setRequestBody(requestBody);
        wxMsgDto.setSignature(signature);
        wxMsgDto.setTimestamp(timestamp);

        String result = wxPortalService.post(wxMsgDto);

        log.info("微信事件消息响应："+result);

        return "";
    }


}
