<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<head>
<meta charset="utf-8">
<title>绑定</title>
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-status-bar-style" content=" black ">
<meta name="format-detection" content="telephone=no">
<%@include file="/WEB-INF/views/commons/user.jsp"%>
<script src="${staticFile_s}/commons/js/user/login.js"
	type="text/javascript"></script>
<script src="${staticFile_s}/commons/js/user/des.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>

<meta http-equiv="Content-Type"
	content="multipart/form-data; charset=utf-8" />
<link type="text/css" rel="stylesheet"
	href="${staticFile_s}/commons/css/m_registter.css">
<link
	href="${staticFile_s}/commons/css/00a627dedc644d0098ace9926f5e1849.css"
	rel="stylesheet">
<link
	href="${staticFile_s}/commons/css/dfe2199acb654a47820464e94480f0c1.css"
	rel="stylesheet">
<link href="${staticFile_s}/commons/css/thzc_style.css"
	rel="stylesheet" type="text/css">
<script src="${staticFile_s}/commons/js/user/register.js"></script>


</head>
<style>
body{background:url(../commons/images/bg.jpg) repeat ;background-size: 100%}
</style>
<body>
<input type="hidden" id="path" value="<%=path%>" />
<input type="hidden" id="userId" value="${userId}" />
	<div class="regibody">
		<div class="toplogo" style="text-align: center">
			绑定
		</div>
		<div class="info">
			<div>
				<input type="tel" class="ip" placeholder="手机号" id="mobile">
			</div>
			<div>
				<input type="number" class="ip" placeholder="请输入图片验证码" id="captcha">
				<div class="changeimg prop">
					<img src="${path }/customer/getImageRegist?date=" +new
					Date() id="captchaImage" alt="换一张" width="100px" /> <a
					id="changeImage" title="换一张" class="ch">换一张</a>
				</div>
			</div>
			<div>
				<input type="number" class="ip" id="mobileCodeValue"
					placeholder="请输入验证码">
				<div class="showpwd prop">
					<input type="button" id="mobileCode" value="获取验证码"
						onclick="getCode()" class="second">
				</div>
			</div>
			<div class="submitBtnBroder">
				<div class="submitText" onclick="updateMobile()">绑定</div>
			</div>
		</div>
<script>
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
</body>
</html>