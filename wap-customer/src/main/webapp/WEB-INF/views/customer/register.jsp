<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>注册</title>
<meta name="keywords" content="这里是关键词">
<meta name="description" content="这里是描述">
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-status-bar-style" content=" black ">
<meta name="apple-mobile-web-app-title" content="众聚猫">
<meta name="format-detection" content="telephone=no">
<%@include file="/WEB-INF/views/commons/user.jsp"%>
<link href="${staticFile_s}/commons/css/newbese.css" rel="stylesheet"
	type="text/css">
<link rel="stylesheet" type="text/css"
	href="${staticFile_s }/commons/css/login.css">
<script src="${staticFile_s}/commons/js/user/register.js"
	type="text/javascript"></script>
<link href="${staticFile_s}/commons/css/zepto.alert.css"
	rel="stylesheet" type="text/css">
<%-- <link rel="shortcut icon" type="image/x-icon" href="${path}/commons/img/favicon.ico" /> --%>
<script type="text/javascript"
	src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<script src="${staticFile_s}/commons/js/user/des.js"
	type="text/javascript"></script>
<%@include file="/WEB-INF/views/commons/commonsIco.jsp"%>
</head>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp"%>
<style>
body{background:url(../commons/images/bg.jpg) repeat ;background-size: 100%}
img{width:99%}
.login {    position: absolute;    top: 7rem;    left: 1.5rem;    right: 1.5rem;}
 .jh_bot_lg{    position: absolute;bottom: 2rem;  width: 100%;    margin: 0 auto;    text-align: center;}
</style>
<body>
	<%-- hidden域 --%>
	<input type="hidden" id="path" value="<%=path%>" />
	<input type="hidden" id="toPay" value="${toPay}" />
	<div class="back">
		<img src="../commons/images/bg.jpg">

		<div class="headly">
			<a href="javascript:goBack();" class="goback"><span><b></b><i></i></span></a>
			<h3>注册</h3>
		</div>

		<div class="login">
			<div class="login-group">
				<div class="field">
					<input type="hidden" id="openId" value="${openId }">
					 <input
						type="tel" placeholder="请输入手机号码" class="text-box" id="mobile">
				</div>
			</div>
			<div class="login-group">
				<div class="field">
					<input type="number" placeholder="请输入图片验证码" class="text-box"
						id="captcha" name="captcha" maxLength="7">
				</div>
				<img src="${path }/customer/getImageRegist?date=" +new
					Date() id="captchaImage" alt="换一张" width="100px" /> <a
					id="changeImage" title="换一张" class="ch">换一张</a>
			</div>

			<div class="login-group">
				<div class="field">
					<input type="number" placeholder="请输入短信验证码" class="text-box co"
						id="mobileCodeValue">
					<!-- <a href="#" class="code">获取验证码</a> -->
					<input type="button" id="mobileCode" value="获取验证码"
						onclick="getCode()" class="second">
				</div>
			</div>
			<div class="login-group">
				<div class="field">
					<input type="password" placeholder="请输入6-20位数字和字母组合构成的密码"
						class="text-box pa" id="J_password"> <a
						href="javascript:void(0)" class="code" onclick="showPassword()">显示</a>
				</div>
			</div>

			<!-- <div class="login-group">
				<div class="field">
					<input type="text" placeholder="邀请码" class="text-box" id="tjMobile">
				</div>
			</div> -->

			<div class="login-g">
				<span> <input type="checkbox" id="agreement"
					name="sku-checkbox"><a
					href="${path}/customer/notes">同意《众聚猫服务协议》</a>
				</span>
			</div>

			<div class="login-btn">
				<button type="button" class="btn" onclick="register()">注册</button>
			</div>

		</div>

	</div>
<div class="jh_bot_lg"><img src="../commons/images/zjm_lg.png" style="width:50%"/></div>	
</body>
</html>
<script type="text/javascript">
	$("#captchaImage").click(
			function() {
				$(this).attr("src",
						"${path}/customer/getImageRegist?date=" + new Date());
			});

	$("#changeImage").click(
			function() {
				$("#captchaImage").attr("src",
						"${path}/customer/getImageRegist?date=" + new Date());
			});
</script>