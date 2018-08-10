<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html><head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
		<meta content="yes" name="apple-mobile-web-app-capable">
		<meta content="black" name="apple-mobile-web-app-status-bar-style">
		<meta content="telephone=no" name="format-detection">
		<%@include file="/WEB-INF/views/commons/base.jsp" %>
		<title><spring:message code="title_recharge" /></title>
		<!-- css -->
		<link href="${staticFile_s}/commons/css/recharge.css" rel="stylesheet" type="text/css">
		<link href="${staticFile_s}/commons/css/order.css" rel="stylesheet" type="text/css">

</head>
	<body class="recharge-bg">
	<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>
	 	<!--头部-->
		<div class="header">
			<a href="javascript:goBack();" class="bug-go goback"></a>
			<spring:message code="title_recharge" />
		</div>


		<!--支付成功-->
		<div class="re-ok">
			<div class="re-wen">
				<img src="${staticFile_s}/commons/newimg/re-ok.png" width="54" height="54">
				<h3>支付成功</h3>
				<p>预计10分钟内到账，充值高峰可能会有延迟</p>
			</div>
			<ul class="re-list">
				<li>充值号码<span>${receiveMobilePhone}</span></li>
				<li>充值内容<span>100元</span></li>
				<li>实付金额<span>${ back.payAmount }元</span></li>
			</ul>
			<div class="unusedly unusedly2">
			<ul>
				<li>
					<span class="unusedly_l">
						<i class="right1"></i><i class="right2"></i><i class="right3"></i><i class="right4"></i><i class="right5"></i><i class="right6"></i><i class="right7"></i><i class="right8"></i>
						<strong><span class="qian"> 5</span></strong>
					</span>
					<span class="unusedly_r">
						<i class="right1"></i><i class="right2"></i><i class="right3"></i><i class="right4"></i><i class="right5"></i><i class="right6"></i><i class="right7"></i><i class="right8"></i>
						<h2>恭喜您获得优惠券</h2>
						<h3>全场满49元可用，活动商品，临期商品除外</h3>							
					</span>
				</li>

			</ul>
	 </div>
			<div class="re-payment"><a href="${path}/view/activity/get/416" class="re-payment top45">去购物</a></div>
		</div>

       
		<%--支付失败
		<div class="re-ok" style="display: none;">
			<div class="re-wen">
				<img src="newimg/re-no.png" width="54" height="54">
				<h3>支付失败</h3>
				<p>失败原因：钱不够啦！钱不够啦钱不够啦</p>
			</div>
		</div>--%>
<script type="text/javascript" src="${staticFile_s}/commons/js/user/common.js"></script>
</body></html>
