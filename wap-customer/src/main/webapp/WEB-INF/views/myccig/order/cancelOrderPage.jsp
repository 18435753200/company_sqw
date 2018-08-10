<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
		<meta content="yes" name="apple-mobile-web-app-capable">
		<meta content="black" name="apple-mobile-web-app-status-bar-style">
		<meta content="telephone=no" name="format-detection">
		<%@include file="/WEB-INF/views/commons/base.jsp" %>
		<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
		<title>取消订单-众聚猫手机版</title>
		<!-- css -->
		<link href="${staticFile_s}/commons/css/newbese.css" rel="stylesheet" type="text/css">
		<link href="${staticFile_s}/commons/css/my.css" rel="stylesheet" type="text/css">
		<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
	</head>

	<body class="recharge-bg">
	<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>
	 	<!--头部-->
		<div class="header">
			<a href="javascript:goBack();" class="bug-go goback"></a>
			取消订单
		</div>
		
		<div class="arder-del">
			<form action="" method="post">
				<input type="hidden" name="orderId" value="${orderId}" />
				<div class="order-title">
					<h3>您确定要取消订单么？</h3>
					<p>确认取消后，当前订单将无法恢复，使用的优惠券将返还你的账户</p>
				</div>
				<h4 class="select">
					<label><i>*</i> 取消原因</label>
					<select name="orderCancelReason">
						<option value="">请选择取消理由</option>
						<option value="重复购买">重复购买</option>
						<option value="买错了">买错了</option>
						<option value="不喜欢/不好用">不喜欢/不好用</option>
						<option value="有更好的产品开团">有更好的产品开团</option>
						<option value="看到负面评价">看到负面评价</option>
						<option value="忘记使用现金券">忘记使用现金券</option>
					</select>
				</h4>
				<div class="order-text">
					<textarea name="orderCancelDetail" cols="" rows="" placeholder="请描述下具体原因，以便我们更好的为您提供服务（50字以内）"></textarea>
					<p><i>0</i>/50</p>
				</div>
				
				<a href="javascript:void(0);" onclick="cancelOrder2()"  class="order-but">确定</a>			
				
			</form>
		</div>
		<script src="js/zepto.min.js"></script>
		<script>
			$(function(){
				
				$('.order-text textarea').keyup(function () {
					 var tit=$(this).val();
				     var Newtit = tit.substring(0,50);
                     $(this).val(Newtit);
					 var len=Newtit.length;
					 $('.order-text i').text(len);
				})
				
			})
		</script>
	</body>
</html>
<script src="${staticFile_s}/commons/js/zepto.min.js" type="text/javascript"></script> 
<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script> 
<script src="${staticFile_s}/commons/js/mycenter.js" type="text/javascript"></script>
<script src="${staticFile_s}/commons/js/ccigorder/myOrder.js" type="text/javascript"></script>