<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<%@include file="/WEB-INF/views/commons/base.jsp" %>
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>

<title><spring:message code="title_jump_unionpay" /></title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>
<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>
<c:choose>
<c:when test="${payType eq 9 }">
   <h4 class="pay-jump"><span class="i-jump"></span>正在跳转到支付宝</h4>
	<form action="${fn:escapeXml(payResult.requestBody.requestUrl)}" id="alipayForm" method="post" >
	   <input  name="_input_charset" value="${fn:escapeXml(payResult.requestBody._input_charset)}"/>
	   <input  name="service" value="${fn:escapeXml(payResult.requestBody.service)}"/>
	   <input  name="partner" value="${fn:escapeXml(payResult.requestBody.partner)}"/>
	   <input  name="return_url" value="${fn:escapeXml(payResult.requestBody.return_url)}"/>
	   <input  name="notify_url" value="${fn:escapeXml(payResult.requestBody.notify_url)}"/>
	   <input  name="out_trade_no" value="${fn:escapeXml(payResult.requestBody.out_trade_no)}"/>
	   <input  name="subject" value="${fn:escapeXml(payResult.requestBody.subject)}"/>
	   <input  name="body" value="${fn:escapeXml(payResult.requestBody.body)}"/>
	   <input  name="total_fee" value="${fn:escapeXml(payResult.requestBody.total_fee)}"/>
	   <input  name="rmb_fee" value="${fn:escapeXml(payResult.requestBody.rmb_fee)}"/>
	   <input  name="currency" value="${fn:escapeXml(payResult.requestBody.currency)}"/>
	   <input  name="supplier" value="${fn:escapeXml(payResult.requestBody.supplier)}"/>
	   <input  name="timeout_rule" value="${fn:escapeXml(payResult.requestBody.timeout_rule)}"/>
	   <input  name="out_user" value="${fn:escapeXml(payResult.requestBody.out_user)}"/>
	   <input  name="merchant_url" value="${fn:escapeXml(payResult.requestBody.merchant_url)}"/>
	   <input  name="sign" value="${fn:escapeXml(payResult.requestBody.sign)}"/>
	    <input  name="sign_type" value="${fn:escapeXml(payResult.requestBody.sign_type)}"/>
	    <input  name="requestUrl" value="${fn:escapeXml(payResult.requestBody.requestUrl)}"/> 
	</form>
 </c:when>
	
	<c:when test="${payType eq 10 }">
   <h4 class="pay-jump"><span class="i-jump"></span>正在跳转到银联</h4>
	<form action="${fn:escapeXml(payResult.action)}" method="post" >
	    
	   <input type="hidden" name="version" value="${fn:escapeXml(payResult.requestBody.version)}"/>
	   <input type="hidden" name="encoding" value="${fn:escapeXml(payResult.requestBody.encoding)}"/>
	   
	   <input type="hidden" name="certId" value="${fn:escapeXml(payResult.requestBody.certId)}"/>
	   <input type="hidden" name="signature" value="${fn:escapeXml(payResult.requestBody.signature)}"/>
	   <input type="hidden" name="signMethod" value="${fn:escapeXml(payResult.requestBody.signMethod)}"/>

	   <input type="hidden" name="txnType" value="${fn:escapeXml(payResult.requestBody.txnType)}"/>
	   <input type="hidden" name="txnSubType" value="${fn:escapeXml(payResult.requestBody.txnSubType)}"/>
	   <input type="hidden" name="bizType" value="${fn:escapeXml(payResult.requestBody.bizType)}"/>
	   <input type="hidden" name="channelType" value="${fn:escapeXml(payResult.requestBody.channelType)}"/>
	   
	   <input type="hidden" name="frontUrl" value="${fn:escapeXml(payResult.requestBody.frontUrl)}"/>
	   <input type="hidden" name="backUrl" value="${fn:escapeXml(payResult.requestBody.backUrl)}"/>
	   <input type="hidden" name="accessType" value="${fn:escapeXml(payResult.requestBody.accessType)}"/>
	   <input type="hidden" name="merId" value="${fn:escapeXml(payResult.requestBody.merId)}"/>
	   
<%-- 	   <input  name="subMerId" value="${fn:escapeXml(payResult.requestBody.subMerId)}"/>
	   <input  name="subMerName" value="${fn:escapeXml(payResult.requestBody.subMerName)}"/>
	   <input  name="subMerAbbr" value="${fn:escapeXml(payResult.requestBody.subMerAbbr)}"/> --%>
	   
	   <input type="hidden" name="orderId" value="${fn:escapeXml(payResult.requestBody.orderId)}"/>
	   <input type="hidden" name="txnTime" value="${fn:escapeXml(payResult.requestBody.txnTime)}"/>
	   
<%-- 	   <input  name="accType" value="${fn:escapeXml(payResult.requestBody.accType)}"/>
	   <input  name="accNo" value="${fn:escapeXml(payResult.requestBody.accNo)}"/> --%>
	   
	   <input type="hidden" name="txnAmt" value="${fn:escapeXml(payResult.requestBody.txnAmt)}"/>
	   <input type="hidden" name="currencyCode" value="${fn:escapeXml(payResult.requestBody.currencyCode)}"/>
	   
<%-- 	   <input  name="customerInfo" value="${fn:escapeXml(payResult.requestBody.customerInfo)}"/>
	   <input  name="orderTimeout" value="${fn:escapeXml(payResult.requestBody.orderTimeout)}"/>
	   <input  name="payTimeout" value="${fn:escapeXml(payResult.requestBody.payTimeout)}"/>
	   <input  name="termId" value="${fn:escapeXml(payResult.requestBody.termId)}"/>
	   <input  name="reqReserved" value="${fn:escapeXml(payResult.requestBody.reqReserved)}"/>
	   <input  name="reserved" value="${fn:escapeXml(payResult.requestBody.reserved)}"/>
	   <input  name="riskRateInfo" value="${fn:escapeXml(payResult.requestBody.riskRateInfo)}"/>
	   
	   <input  name="encryptCertId" value="${fn:escapeXml(payResult.requestBody.encryptCertId)}"/>
	   <input  name="frontFailUrl" value="${fn:escapeXml(payResult.requestBody.frontFailUrl)}"/>
	   <input  name="instalTransInfo" value="${fn:escapeXml(payResult.requestBody.instalTransInfo)}"/>
	   <input  name="defaultPayType" value="${fn:escapeXml(payResult.requestBody.defaultPayType)}"/>
	   <input  name="issInsCode" value="${fn:escapeXml(payResult.requestBody.issInsCode)}"/>
	   <input  name="supPayType" value="${fn:escapeXml(payResult.requestBody.supPayType)}"/>
	   <input  name="userMac" value="${fn:escapeXml(payResult.requestBody.userMac)}"/>
	   <input  name="customerIp" value="${fn:escapeXml(payResult.requestBody.customerIp)}"/>
	   <input  name="cardTransData" value="${fn:escapeXml(payResult.requestBody.cardTransData)}"/>
	   <input  name="vpcTransData" value="${fn:escapeXml(payResult.requestBody.vpcTransData)}"/> --%>
	   <input type="hidden" name="orderDesc" value="${fn:escapeXml(payResult.requestBody.orderDesc)}"/>
	   
	</form>
 </c:when>
 
 <c:when test="${payType eq 13 }">
   <h4 class="pay-jump"><span class="i-jump"></span>正在跳转到翼支付</h4>
	<form action="${fn:escapeXml(payResult.action)}" id="alipayForm" method="post" >
	   <input  name="MERCHANTID" value="${fn:escapeXml(payResult.requestBody.MERCHANTID)}"/>
	   <input  name="SUBMERCHANTID" value="${fn:escapeXml(payResult.requestBody.SUBMERCHANTID)}"/>
	   <input  name="ORDERSEQ" value="${fn:escapeXml(payResult.requestBody.ORDERSEQ)}"/>
	   <input  name="ORDERREQTRANSEQ" value="${fn:escapeXml(payResult.requestBody.ORDERREQTRANSEQ)}"/>
	   <input  name="ORDERREQTIME" value="${fn:escapeXml(payResult.requestBody.ORDERREQTIME)}"/>
	   <input  name="TRANSCODE" value="${fn:escapeXml(payResult.requestBody.TRANSCODE)}"/>
	   <input  name="ORDERAMT" value="${fn:escapeXml(payResult.requestBody.ORDERAMT)}"/>
	   <input  name="ORDERCCY" value="${fn:escapeXml(payResult.requestBody.ORDERCCY)}"/>
	   <input  name="SERVICECODE" value="${fn:escapeXml(payResult.requestBody.SERVICECODE)}"/>
	   <input  name="PRODUCTID" value="${fn:escapeXml(payResult.requestBody.PRODUCTID)}"/>
	   <input  name="PRODUCTDESC" value="${fn:escapeXml(payResult.requestBody.PRODUCTDESC)}"/>
	   <input  name="LOGINNO" value="${fn:escapeXml(payResult.requestBody.LOGINNO)}"/>
	   <input  name="PROVINCECODE" value="${fn:escapeXml(payResult.requestBody.PROVINCECODE)}"/>
	   <input  name="CITYCODE" value="${fn:escapeXml(payResult.requestBody.CITYCODE)}"/>
	   <input  name="DIVDETAILS" value="${fn:escapeXml(payResult.requestBody.DIVDETAILS)}"/>
	   <input  name="ENCODETYPE" value="${fn:escapeXml(payResult.requestBody.ENCODETYPE)}"/>
	   <input  name="MAC" value="${fn:escapeXml(payResult.requestBody.MAC)}"/>
	   <input  name="SESSIONKEY" value="${fn:escapeXml(payResult.requestBody.SESSIONKEY)}"/> 
	   <input  name="ENCODE" value="${fn:escapeXml(payResult.requestBody.ENCODE)}"/> 
	</form>
 </c:when> 
 
 <c:when test="${payType eq 15 }">
   <h4 class="pay-jump"><span class="i-jump"></span>正在跳转到中国银行网银</h4>
	<form action="${fn:escapeXml(payResult.action)}" method="post" >
	   <input type="hidden" name="merchantNo" value="${fn:escapeXml(payResult.requestBody.merchantNo)}"/>
	   <input type="hidden" name="payType" value="${fn:escapeXml(payResult.requestBody.payType)}"/>
	   <input type="hidden" name="orderNo" value="${fn:escapeXml(payResult.requestBody.orderNo)}"/>
	   <input type="hidden" name="curCode" value="${fn:escapeXml(payResult.requestBody.curCode)}"/>
	   <input type="hidden" name="orderAmount" value="${fn:escapeXml(payResult.requestBody.orderAmount)}"/>
	   <input type="hidden" name="orderTime" value="${fn:escapeXml(payResult.requestBody.orderTime)}"/>
	   <input type="hidden" name="orderNote" value="${fn:escapeXml(payResult.requestBody.orderNote)}"/>
	   <input type="hidden" name="orderUrl" value="${fn:escapeXml(payResult.requestBody.orderUrl)}"/>
	   <input type="hidden" name="orderTimeoutDate" value="${fn:escapeXml(payResult.requestBody.orderTimeoutDate)}"/>
	   <input type="hidden" name="signData" value="${fn:escapeXml(payResult.requestBody.signData)}"/>
	   
	</form>
 </c:when>
 
  <c:when test="${payType eq 16 }">
   <h4 class="pay-jump"><span class="i-jump"></span>正在跳转到中国银行信用卡网银</h4>
	<form action="${fn:escapeXml(payResult.action)}" method="post" >
	   <input type="hidden" name="pid" value="${fn:escapeXml(payResult.requestBody.pid)}"/>
	   <input type="hidden" name="merId" value="${fn:escapeXml(payResult.requestBody.merId)}"/>
	   <input type="hidden" name="termId" value="${fn:escapeXml(payResult.requestBody.termId)}"/>
	   <input type="hidden" name="orderId" value="${fn:escapeXml(payResult.requestBody.orderId)}"/>
	   <input type="hidden" name="txnTime" value="${fn:escapeXml(payResult.requestBody.txnTime)}"/>
	   <input type="hidden" name="txnAmt" value="${fn:escapeXml(payResult.requestBody.txnAmt)}"/>
	   <input type="hidden" name="isUsejf" value="${fn:escapeXml(payResult.requestBody.isUsejf)}"/>
	   <input type="hidden" name="txnType" value="${fn:escapeXml(payResult.requestBody.txnType)}"/>
	   <input type="hidden" name="txnSubType" value="${fn:escapeXml(payResult.requestBody.txnSubType)}"/>
	   <input type="hidden" name="frontUrl" value="${fn:escapeXml(payResult.requestBody.frontUrl)}"/>
	   <input type="hidden" name="ccyCode" value="${fn:escapeXml(payResult.requestBody.ccyCode)}"/>
	   <input type="hidden" name="reqReserved" value="${fn:escapeXml(payResult.requestBody.reqReserved)}"/>
	   <input type="hidden" name="signature" value="${fn:escapeXml(payResult.requestBody.signature)}"/>
	</form>
 </c:when>
 <c:when test="${payType eq 17 }">
   <h4 class="pay-jump"><span class="i-jump"></span>正在跳转到中国银行信用卡网银</h4>
	<form action="${fn:escapeXml(payResult.action)}" method="post" >
	   <input type="hidden" name="pid" value="${fn:escapeXml(payResult.requestBody.pid)}"/>
	   <input type="hidden" name="merId" value="${fn:escapeXml(payResult.requestBody.merId)}"/>
	   <input type="hidden" name="termId" value="${fn:escapeXml(payResult.requestBody.termId)}"/>
	   <input type="hidden" name="orderId" value="${fn:escapeXml(payResult.requestBody.orderId)}"/>
	   <input type="hidden" name="txnTime" value="${fn:escapeXml(payResult.requestBody.txnTime)}"/>
	   <input type="hidden" name="txnAmt" value="${fn:escapeXml(payResult.requestBody.txnAmt)}"/>
	   <input type="hidden" name="isUsejf" value="${fn:escapeXml(payResult.requestBody.isUsejf)}"/>
	   <input type="hidden" name="txnType" value="${fn:escapeXml(payResult.requestBody.txnType)}"/>
	   <input type="hidden" name="txnSubType" value="${fn:escapeXml(payResult.requestBody.txnSubType)}"/>
	   <input type="hidden" name="frontUrl" value="${fn:escapeXml(payResult.requestBody.frontUrl)}"/>
	   <input type="hidden" name="ccyCode" value="${fn:escapeXml(payResult.requestBody.ccyCode)}"/>
	   <input type="hidden" name="planId" value="${fn:escapeXml(payResult.requestBody.planId)}"/>
	   <input type="hidden" name="planNum" value="${fn:escapeXml(payResult.requestBody.planNum)}"/>
	   <input type="hidden" name="reqReserved" value="${fn:escapeXml(payResult.requestBody.reqReserved)}"/>
	   <input type="hidden" name="signature" value="${fn:escapeXml(payResult.requestBody.signature)}"/>
	</form>
 </c:when>
<c:when test="${payType eq 18 }">
<h4 class="pay-jump"><span class="i-jump"></span>正在跳转到京东支付</h4>
	<form action="${fn:escapeXml(payResult.requestBody.requestUrl)}" method="post">
		<input type="hidden" name="version" value="${payResult.requestBody.version}" />
		<input type="hidden" name="merchant" value="${payResult.requestBody.merchant}" />
		<input type="hidden" name="device" value="${payResult.requestBody.device}" />
		<input type="hidden" name="tradeNum" value="${payResult.requestBody.tradeNum}">
		<input type="hidden" name="tradeName" value="${payResult.requestBody.tradeName}">
		<input type="hidden" name="tradeDesc" value="${payResult.requestBody.tradeDesc}">
		<input type="hidden" name="tradeTime" value="${payResult.requestBody.tradeTime}">
		<input type="hidden" name="amount" value="${payResult.requestBody.amount}">
		<input type="hidden" name="currency" value="${payResult.requestBody.currency}">
		<input type="hidden" name="note" value="${payResult.requestBody.note}">
		<input type="hidden" name="callbackUrl" value="${payResult.requestBody.callbackUrl}">
		<input type="hidden" name="notifyUrl" value="${payResult.requestBody.notifyUrl}">
		<input type="hidden" name="ip" value="${payResult.requestBody.ip}">
		<input type="hidden" name="userType" value="${payResult.requestBody.userType}">
		<input type="hidden" name="userId" value="${payResult.requestBody.userId}">
		<input type="hidden" name="expireTime"	value="${payResult.requestBody.expireTime}">
		<input type="hidden" name="orderType" value="${payResult.requestBody.orderType}">
		<input type="hidden" name="industryCategoryCode" value="${payResult.requestBody.industryCategoryCode}">
		<input type="hidden" name="specCardNo" value="${payResult.requestBody.specCardNo}">
		<input type="hidden" name="specId" value="${payResult.requestBody.specId}">
		<input type="hidden" name="specName" value="${payResult.requestBody.specName}">
		<input type="hidden" name="sign" value="${payResult.requestBody.sign}">
		<input type="hidden" name="cert" value="${payResult.requestBody.cert}">
		<input type="hidden" name="vendorId" value="${payResult.requestBody.vendorId}">
		<input type="hidden" name="goodsInfo" value="${payResult.requestBody.goodsInfo}">
		<input type="hidden" name="orderGoodsNum" value="${payResult.requestBody.orderGoodsNum}">
		<input type="hidden" name="receiverInfo" value="${payResult.requestBody.receiverInfo}">
		<input type="hidden" name="termInfo" value="${payResult.requestBody.termInfo}">
	</form>
</c:when>

</c:choose>
	<script type="text/javascript">
	$(function(){
 		$("form").submit(); 
	}); 
     </script>
</body>
</html>