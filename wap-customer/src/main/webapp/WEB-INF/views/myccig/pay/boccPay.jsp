<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="black" name="apple-mobile-web-app-status-bar-style">
	<meta content="telephone=no" name="format-detection">
	<title>支付</title>
	<%@include file="/WEB-INF/views/commons/base.jsp" %>
	
    <link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/pay.css">
    <script type="text/javascript" src="${staticFile_s}/commons/js/pay/boccPay.js"></script>
    <script type="text/javascript" src="${staticFile_s}/commons/js/common/common.js"></script>
    <script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
    <script type="text/javascript" src="${staticFile_s}/commons/js/cciginfo/info.js"></script>
    
</head>
<body style="background:#edecf1;">
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>
	<!-- 隐藏域 -->
	<input type="hidden" id="domain" name="domain" value="<%=path %>"/>
	<c:if test="${payPlatForm == null }">
		<div class="header head-bg" style="background:#ffffff;">
		<a href="javascript:goBack();" class="bug-go goback"></a>
		中国银行跨境支付
    </div>	
	</c:if>
	

	<div class="pay">
		<div class="pay-conter">
			<form id="form_payInfo" action="${path}/orderPay/toBocCrossPay" method="post">
				<input type="hidden" id="signNo" name="signNo" value="${signNo }"/>
				<div class="from-box">
	
					<div class="form-pay">
						<label>订单编号</label>
						<div class="field">
		                     <input  class="text-box" type="text" id="orderId" name="orderId" value="${orderId }" readonly="readonly">
		                </div>
					</div>
					
					<div class="form-pay">
						<label>应付金额</label>
						<div class="field">
		                     <input  class="text-box red" type="text" id="amount" name="amount" value='<fmt:formatNumber value="${amount }" pattern="#0.00" />' readonly="readonly">
		                </div>
					</div>
	
					<div class="form-pay">
						<label>商户名称</label>
						<div class="field">
		                     <input  class="text-box" type="text" id="merchantName" name="merchantName" value="${merchantName }" readonly="readonly">
		                </div>
					</div>
	
					<div class="form-pay">
						<label>卡号</label>
						<div class="field">
		                     <input  class="text-box yzm pa1 " type="text" id="accNo" name="accNo" value="${accNo }" readonly="readonly">
		                     <i onclick="chooseAccount()"></i>
		                </div>
					</div>
	
					<div class="form-pay">
						<label>手机号</label>
						<div class="field">
		                     <input  class="text-box pa2" type="text" id="phone" name="phone" value="${phone }"  readonly="readonly">
		                </div>
					</div>
	
					<div class="form-pay  last">
						<label>验证码</label>
						<div class="field">
		                     <input  class="text-box yzm pa2" type="text"  id="messageCode" name="messageCode"  maxlength="6" onkeyup="this.value=this.value.replace(/\D/g,'')" placeholder="请输入验证码">
		                     <a href="javascript:;" class="yzm-btn" id="yzm-btn" onclick="sendMobileCode()">获取验证码</a>
		                </div>
					</div>
					
					<div class="form-but">
						<input type="submit" value="确认支付" class="cbg"/>
					</div>
	
				</div>
			</form>
		</div>
	</div>
</body>
</html>