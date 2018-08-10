package com.mall.controller.account;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mall.commons.utils.DateUtil;
import com.mall.constant.CommonConstant;
import com.mall.customer.constant.RecordBkStatus;
import com.mall.customer.model.SqwAccountRecord;
import com.mall.customer.order.api.rpc.CustomerOrderService;
import com.mall.customer.order.dto.OrderDTO;
import com.mall.customer.service.PsqwAccountRecordService;
import com.mall.pay.api.rpc.PayLogService;
import com.mall.pay.api.rpc.PayRecordServer;
import com.mall.pay.common.PayConstant;
import com.mall.pay.common.StringUtils;
import com.mall.pay.dto.GroupProfitDto;
import com.mall.pay.po.BankLog;
import com.mall.pay.po.PayRecord;
import com.mall.service.CusOrderPayService;
import com.mall.supplier.model.SupplierSalesDiscount;
import com.mall.supplier.service.SupplierDiscountService;
import com.mall.utils.CookieTool;
import com.mall.utils.getUUID;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 *  <pre>提供账户的各种操作</pre>
 * @author zhoushuyi
 * @date 2018/7/16
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private PayLogService payLogService;

    @Autowired
    private CustomerOrderService customerOrderService;

    @Autowired
    private PayRecordServer payRecordServer;

    @Autowired
    private PsqwAccountRecordService psqwAccountRecordService;

    @Autowired
    private CusOrderPayService cusOrderPayService;

    @Autowired
    private SupplierDiscountService supplierDiscountService;
    @Autowired
    private MemcachedClient memcachedClient;


    @RequestMapping("/rms")
    public String rmmemcached(HttpServletRequest request , HttpServletResponse  response) throws InterruptedException, MemcachedException, TimeoutException {

        String sid = getUUID.getSessionId(request, response);
        boolean b = memcachedClient.delete(CommonConstant.CUSTOMER_LOGIN + sid);


        return b ? "true":"false";
    }

    /**
     * <pre>根据orderId查询账目信息</pre>
     *
     * @param orderId
     * @return
     */
    @RequestMapping("/queryOrderAccount")
    @ResponseBody
    public JSONObject queryOrderAccount(Long orderId) {
        JSONObject result = new JSONObject();
        result.put("orderId", orderId);
        result.put("timestamp", System.currentTimeMillis());

        if (orderId == null) {
            result.put("errmsg", "orderId不可为空");
            return result;
        }


        OrderDTO order = customerOrderService.findCustomerOrderInfoByOrderId(orderId);
        if (order.getBankQfFlag() != null) {
            result.put("bankQfFlag", order.getBankQfFlag());
            result.put("userId", order.getUserId());
        }

        List<PayRecord> payRecords = payRecordServer.findPayRecordByOrderId(order.getId().toString());
        if (payRecords != null) {
            JSONArray objects = new JSONArray(3);
            objects.addAll(payRecords);
            result.put("payRecords", objects);
        }


        BankLog bankLog = new BankLog();
        bankLog.setPayFlowNo(payRecords.get(0).getPayWaterCode());
        bankLog.setContentType("request");
        bankLog.setOperateType(PayConstant.BankLogType.PAY);

        List<BankLog> bankLogs = payLogService.query(bankLog);
        if (bankLogs != null && bankLogs.size() > 0) {
            String content = bankLogs.get(0).getContent();
            JSONObject objects = JSONObject.parseObject(content);

            Object groupProfit = objects.get("groupProfit");
            GroupProfitDto groupProfitDto = JSON.parseObject(groupProfit.toString(), GroupProfitDto.class);

            result.put("bankLog", groupProfitDto);
        }


        List<SqwAccountRecord> recordByRefObjId = psqwAccountRecordService.findRecordByRefObjId(order.getId().toString());
        if (recordByRefObjId != null) {
            JSONArray accountRecord = new JSONArray();
            accountRecord.addAll(recordByRefObjId);
            result.put("accountRecord", accountRecord);
        }


        return result;
    }


    /**
     * <pre>简单分账</pre>
     * <p>提供一键分润，结算，奖励，关系。</p>
     * 仅适用order记录BankQfFlag为0，并上诉操作一个没成功执行的情况下。
     *
     * @param orderId
     * @return
     */
    @RequestMapping("/simpleQf")
    @ResponseBody
    public JSONObject simpleReplenishment(Long orderId, String qfType) throws Exception {
        JSONObject result = new JSONObject();
        if (orderId == null) {
            result.put("errmsg", "orderId不能为空");
            return result;
        }

        // 查询订单和支付订单
        OrderDTO order = customerOrderService.findCustomerOrderInfoByOrderId(orderId);
        List<PayRecord> payRecords = payRecordServer.findPayRecordByOrderId(order.getId().toString());
        PayRecord payRecord = payRecords.get(0);

        // 一键分账
        if (StringUtils.isEmpty(qfType)) {
            cusOrderPayService.separateAccounts(order, payRecord, DateUtil.dateToString(order.getCreateTime()));
            result.put("result", "ok");
            return result;
        }

        GroupProfitDto groupProfitDto = cusOrderPayService.getProfit(payRecord.getPayWaterCode());

        if (groupProfitDto == null) {
            result.put("errmsg", "无支付记录");
            return result;
        }

        Integer re = Integer.MAX_VALUE;
        if ("1".equals(qfType)){
            // 商家结算
            re = psqwAccountRecordService.supplierConsumptionSettleAccountsWithTime(Long.valueOf(order.getMerchantid()), order.getUserId(), order.getId().toString(), order.getPrice(), order.getHqjPrice(),
                    groupProfitDto, (short) 1, payRecord.getOrderTime());
        }else if ("2".equals(qfType)){
            // 消费奖励
            re = psqwAccountRecordService.consumptionRewardWithTime(Long.valueOf(order.getMerchantid()), order.getUserId(), order.getId().toString(), order.getPrice(), order.getPrice(),
                    order.getHqjPrice(), payRecord.getOrderTime());
        } else if ("3".equals(qfType)) {
            // 线下消费分润
            re = psqwAccountRecordService.cashConsumptionShareInterestWithTime(order.getUserId(), Long.valueOf(order.getMerchantid()), order.getId().toString(), order.getPrice(), order.getHqjPrice(),
                    groupProfitDto, (short) 1, payRecord.getOrderTime());
        } else if ("4".equals(qfType)) {
            // 建关系
            re = psqwAccountRecordService.createRelationship(order.getUserId(), Long.valueOf(order.getMerchantid()), order.getId().toString(), order.getPrice(), order.getHqjPrice());
        } else {
            result.put("errmsg", "qfType无效");
            return result;
        }
        result.put("result", re);
        return result;
    }


    /**
     * <pre>修改订单分账状态为已分账</pre>
     * 只修改order表中的BankQfFlag
     * @param orderId 订单ID
     * @return
     */
    @RequestMapping("/fqToOrder")
    @ResponseBody
    public JSONObject updateOrderBankQfFlag(Long orderId) {
        JSONObject result = new JSONObject();

        if (orderId == null) {
            result.put("errmsg", "orderId不能为空");
            return result;
        }

        Integer re = customerOrderService.updateOrderBankQfFlag(orderId, RecordBkStatus.RECORD_BK_STATUS_EN);
        result.put("result", re);
        return result;
    }


    /**
     * <pre>自主分账</pre>
     *  101：企业代金券转优惠券
     * 	102：企业代金券转个人代金券
     * 	103：个人代金券转个人代金券
     * 	104：提现
     * 	105：退款
     * 	106：（商家）增加优惠券
     * 	107：企业优惠券转优惠券
     * 	110：分润收入
     * 	111：现金收入
     * 	112：代金券支付
     * 	113: 分润 扣税
     * 	114: 分享额度增加
     * 	115：代金券收入
     * 	116：（商家）减少优惠券
     * 	117：分润 额度减少
     * 	118：跨界收入
     * 	119：平台代收（消费者没有商户关系或重复在一家消费时，商户分润会给平台;各级代理分润异常时，给平台）
     * 	120：取消订单 退回代金券
     *
     * 	150：系统调账
     *
     * @param userId
     * @return
     */
    @RequestMapping("/libertyAccount")
    @ResponseBody
    public JSONObject libertyAccount(Long userId,
                                 Integer accountId,
                                 Boolean isEarning,
                                 BigDecimal amount,
                                 Integer recordType,
                                 Long orderId,String date) throws Exception {
        JSONObject result = new JSONObject();

        if (userId == null) {
            result.put("errmsg","userId不能为空");
            return result;
        }
        if (accountId == null) {
            result.put("errmsg","accountId不能为空");
            return result;
        }
        if (amount == null) {
            result.put("errmsg","amount不能为空");
            return result;
        }
        if (recordType == null) {
            result.put("errmsg","recordType不能为空");
            return result;
        }
        if (orderId == null){
            result.put("errmsg","orderId不能为空");
            return result;
        }
        if (date == null) {
            result.put("errmsg","date记录时间不能为空");
            return result;
        }
        if (BooleanUtils.isNotFalse(isEarning)) {
            isEarning = true;
        }
        Date recordTiem = DateUtil.stringToDate(date);
        // 商户折扣
        BigDecimal discount = BigDecimal.ZERO;


        OrderDTO order = customerOrderService.findCustomerOrderInfoByOrderId(orderId);

        if (order != null) {
            // 查询商户折扣
            SupplierSalesDiscount salesDiscount = supplierDiscountService.findBySupplierId(Long.valueOf(order.getMerchantid()));
            if (salesDiscount == null) {
                result.put("errmsg", "此订单的商户无折扣信息");
                return result;
            }
            discount = salesDiscount.getSalesDiscount();
        }


        int re = psqwAccountRecordService.checkTheBalance(userId, accountId, isEarning, amount, recordType, 1, orderId.toString(), "系统调账", discount, (short) 1, recordTiem);

        result.put("result", re);
        return result;
    }



}
