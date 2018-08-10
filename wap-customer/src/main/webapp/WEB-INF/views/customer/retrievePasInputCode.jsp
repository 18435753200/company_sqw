<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>验证码已发送</title>
    <meta name="keywords" content="这里是关键词">
    <meta name="description" content="这里是描述">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no">
    <meta name="apple-mobile-web-app-status-bar-style" content=" black ">
    <meta name="apple-mobile-web-app-title" content="众聚猫">
    <meta name="format-detection" content="telephone=no">
    <%@include file="/WEB-INF/views/commons/user.jsp" %>
    <link href="${staticFile_s}/commons/css/newbese.css" rel="stylesheet" type="text/css">
    <link href="${staticFile_s}/commons/css/login.css" rel="stylesheet" type="text/css">
    <link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
    <script src="${staticFile_s}/commons/js/user/inputCodePass.js?v=0.1.5" type="text/javascript"></script>
    <%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>
<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
    <div class="back">
	    <img src="${staticFile_s }/commons/img/user/login_bg.jpg">
	<div class="headly">
		<a href="javascript:goBack();" class="goback"><span><b></b><i></i></span></a>
	    <h3>找回密码</h3>
	</div>

	<div class="login">
		<div class="login-group">
			<input type="hidden" value="${username }" id="phoneNumberPass">
			<label class="send">验证码已发送</label>
			<div class="field">
				<input type="text" value="${mobilestr }" class="text-box fi" readonly="readonly">
			</div>
		</div>
		<div class="login-group">
			<div class="field">
				<input type="number" placeholder="请输入验证码" class="text-box pa" id="msgCode">	 
				<!-- <a href="#" class="second">118秒</a> -->
				<input type="button"  id="reGetPass"  value="重新发送(60 秒)" onclick="getCodePas()" class="second" disabled="disabled">
			</div>
          
		</div>
		<div class="login-group">
			<div class="field">
				<input type="password" placeholder="请输入6-20位数字和字母组合构成的密码" class="text-box" id="J_password">
			</div>
		</div>
		<div class="login-btn">
			<button type="button" class="btn" onclick="alertPassword()">确定</button>
		</div>
	</div>
</div>

</body>
</html>

