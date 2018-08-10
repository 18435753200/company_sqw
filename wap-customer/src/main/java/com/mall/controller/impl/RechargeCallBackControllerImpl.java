package com.mall.controller.impl;
/**
 * 
 */
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.customer.order.api.rpc.CustomerOrderService;
import com.mall.dealer.product.api.DealerProductService;
//import com.mall.external.constants.ExternalConstants;
//import com.mall.external.dto.RechargeCallBackDTO;
//import com.mall.external.service.RechargeService;
import com.mall.vo.RechargeCallBack;

/**
 * @author zhengqiang.shi
 * 2016-4-22 下午6:21:47
 */
//@Service
public class RechargeCallBackControllerImpl extends AbstractControllerImpl {

//	private static Logger log = LoggerFactory.getLogger(RechargeCallBackControllerImpl.class);
//	
//	@Autowired
//	private CustomerOrderService customerOrderService;
//	
//	@Autowired
//	private DealerProductService dealerProductService;
//	
//	@Autowired
//	private RechargeService rechargeService;
//	
//	/**
//	 * 充值话费回调
//	 * @param requestJson
//	 * @param rechargeRequest
//	 */
//	public String rechargeCallBack(RechargeCallBack rechargeCallBack){
//		
//		RechargeCallBackDTO rechargeCallBackDTO = new RechargeCallBackDTO();
//		rechargeCallBackDTO.setMobileBalance(rechargeCallBack.getMobileBalance());
//		rechargeCallBackDTO.setOrderid(rechargeCallBack.getOrderid());
//		rechargeCallBackDTO.setOrdermoney(rechargeCallBack.getOrdermoney());
//		rechargeCallBackDTO.setStatus(rechargeCallBack.getStatus());
//		rechargeCallBackDTO.setVerifystring(rechargeCallBack.getVerifystring());
//		
//		int rechargeCallBackResult;
//		try {
//			rechargeCallBackResult = rechargeService.rechargeCallBack(rechargeCallBackDTO);
//		} catch (Exception e) {
//			log.error("rechargeService.rechargeCallBack has error.message:"+e.getMessage(),e);
//			return null;
//		}
//		
//		if(rechargeCallBackResult != 1){
//			return null;
//		}
//		
//		// 充值成功
//    	if(ExternalConstants.RECHARGE_STATUS_2.equals(rechargeCallBack.getStatus())){
//    		Long originOrderId = null;
//    		String orderId = rechargeCallBack.getOrderid();
//    		if (orderId.startsWith("re")) {
//    			String tid = rechargeService.getRechargeReorderOrderId(orderId);
//    			if (StringUtils.isNotEmpty(tid)) {
//    				originOrderId = Long.valueOf(tid);
//    			}
//    			else {
//    				log.error("rechargeService.rechargeCallBack has error.can not find origin order id.reOrderId={}", orderId);
//    			}
//    		}
//    		else {
//				originOrderId = Long.valueOf(orderId);
//    		}
//    		// 确认收货
//    		boolean customerOrderReceipt = customerOrderService.customerOrderReceipt(originOrderId, "sys");
//    		if (orderId.startsWith("re")) {
//    			rechargeService.rechargeReorderSuccess(orderId, 1);
//    		}
//    		
//    		log.info("customerOrderService.customerOrderReceipt:{}",customerOrderReceipt);
//    	}
//		
//		return rechargeCallBack.getStatus();
//		
//	}
}
