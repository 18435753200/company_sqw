<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>手机认证</title>
<meta charset="utf-8">
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
<link href="${staticFile_s}/commons/css/zepto.alert.css"
	rel="stylesheet" type="text/css">
<script type="text/javascript"
	src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<script src="${staticFile_s}/commons/js/user/des.js"
	type="text/javascript"></script>
<%@include file="/WEB-INF/views/commons/commonsIco.jsp"%>
</head>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp"%>
<script src="${staticFile_s}/commons/js/user/renZhengRegister.js"></script>
<body>
	<%-- hidden域 --%>
	<input type="hidden" id="path" value="<%=path%>" />
	<div class="back">
		<img src="${staticFile_s }/commons/img/user/login_bg.jpg">
		<input type="hidden" value="${toPayUrl}" />
		<div class="headly">
			<a href="javascript:goBack();" class="goback"><span><b></b><i></i></span></a>
			<h3>手机认证</h3>
		</div>
		<div class="login">
			<div class="login-group">
				<div class="field">
					<input type="hidden" id="openId" value="${openId}">
					 <input type="tel" placeholder="请输入手机号码" class="text-box" id="mobile">
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
					<input type="button" id="mobileCode" value="获取验证码" onclick="getCode()"
						 class="second">
				</div>
			</div>
			<div class="login-btn">
			<!-- <font style="color: #fff" >提示:一个微信只能绑定一个手机号</font> -->
			<button type="button" class="btn" onclick="register()">确定</button>
			</div>
		</div>
	</div>
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
    //防止页面后退
    history.pushState(null, null, document.URL);
    window.addEventListener('popstate', function () {
        history.pushState(null, null, document.URL);
    });
</script>