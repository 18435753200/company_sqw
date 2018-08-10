<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="black" name="apple-mobile-web-app-status-bar-style">
	<meta content="telephone=no" name="format-detection">
	<title>签约</title>
	<%@include file="/WEB-INF/views/commons/base.jsp" %>
    <link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/pay.css">
    <script type="text/javascript" src="${staticFile_s}/commons/js/common/common.js"></script>
    <script type="text/javascript" src="${staticFile_s}/commons/js/common/common.ajax.js"></script>
    <script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
    <script type="text/javascript" src="${staticFile_s}/commons/js/pay/boccSigning.js"></script>
    <script type="text/javascript" src="${staticFile_s}/commons/js/cciginfo/info.js"></script>
    
</head>
<body style="background:#edecf1;">
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>
	<!-- 隐藏域 -->
	<input type="hidden" id="domain" name="domain" value="${path}"/>
	
	<c:if test="${payPlatForm == null }">
		<div class="header head-bg" style="background:#ffffff;">
			<a href="javascript:goBack();" class="bug-go goback"></a>
			中国银行跨境支付
	    </div>	
	</c:if>
	
	<form name="form_fillSigningInfo" action="${path }/cusPay/checkAuthCode" method="post">
		<!-- 隐藏域 -->
		<input type="hidden" id="orderId" name="orderId" value="${orderId}"/>
		<input type="hidden" id="act" name="act" value="${act}"/>
		<input type="hidden" id="serNo" name="serNo"/>
		<div class="pay">
			<div class="pay-title">
				使用个人协议支付功能，需要先签约，请填写以下内容进行签约。
			</div>
	
			<div class="pay-conter">
				<div class="from-box">
					<div class="form-pay">
						<label>卡号</label>
						<div class="field">
		                     <input  class="text-box pa" id="DRACC_NO" name="DRACC_NO" type="text" placeholder="储蓄卡号">
		                </div>
					</div>
					
					<div class="form-pay">
						<label>持卡人姓名</label>
						<div class="field">
		                     <input class="text-box" id="DBT_NAME" name="DBT_NAME" type="text">
		                </div>
					</div>
	
					<div class="form-pay">
						<label>身份证</label>
						<div class="field">
		                     <input  class="text-box" id="DBT_CODE" name="DBT_CODE" type="text" placeholder="身份证号码">
		                </div>
					</div>
	
					<div class="form-pay">
						<label>手机号</label>
						<div class="field">
		                     <input  class="text-box" id="DBT_PHONE" name="DBT_PHONE" type="text" placeholder="银行手机号">
		                </div>
					</div>
					
	
					<div class="form-pay  last">
						<label>验证码</label>
						<div class="field">
		                     <input  class="text-box yzm" type="text" id="messageCode" name="messageCode"  maxlength="6" onkeyup="this.value=this.value.replace(/\D/g,'')" placeholder="请输入验证码">
		                     <a href="javascript:;" class="yzm-btn" id="yzm-btn"  onclick="sendCheckCode()">获取验证码</a>
		                </div>
					</div>
					<div class="form-ment">
						<input type="checkbox" id="agreementFlag" name="agreementFlag">
						<a href="${path}/cusPay/agreement">支付客户协议</a>
					</div>
					<div class="form-but">
						<button type="button" class="next cbg" onclick="doSubmit()">下一步</button>
					</div>
					
					<div class="form-a">
						<!-- <a href="#">管理已有账户</a> -->
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>