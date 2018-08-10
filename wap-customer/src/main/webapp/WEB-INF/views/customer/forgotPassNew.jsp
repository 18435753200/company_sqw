<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<%@include file="/WEB-INF/views/commons/base.jsp"%>
<link href="${staticFile_s}/commons/css/base.css" rel="stylesheet"
	type="text/css">
<link href="${staticFile_s}/commons/css/regLogin.css" rel="stylesheet"
	type="text/css">

<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet"
	type="text/css">
<script type="text/javascript"
	src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<title><c:choose>
		<c:when test="${from == 'cus' }">
			<spring:message code="title_cusinfo_updatePass" />
		</c:when>
		<c:otherwise>
			<spring:message code="title_find_pass" />
		</c:otherwise>
	</c:choose></title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>

<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>	
	<header id="header" class="head">
		<a href="javascript:goBack();" class="goback"><span
			class="icon i-goback"></span></a>
		<h2 class="h-title">
			<c:choose>
				<c:when test="${from == 'cus' }">修改密码</c:when>
				<c:otherwise>找回密码</c:otherwise>
			</c:choose>
		</h2>
	</header>
	<form action="<%=path%>/customer/newPass" method="post" id="formSubmit"
		onsubmit="return false;">
		<input type="hidden" name="username" id="username"
			value="${username }">
		<div class="wrap rlf-wrap">
			<div class="rlf-form">
				<div class="from-box">
					<c:choose>
						<c:when test="${from == 'cus' }">
							<div class="form-group">
								<label for="J_password">旧 密 码 ：</label>
								<div class="field">
									<input id="J_Opassword" class="text-box" name="oldpass"
										type="password" placeholder="请输入旧密码"   maxlength="20"/> <input id="from"
										name="from" value="${from}" type="hidden" />
								</div>
							</div>
						</c:when>
					</c:choose>
					<div class="form-group">
						<label for="J_password">新 密 码 ：</label>
						<div class="field">
							<input id="J_password" class="text-box" name="pass"
								type="password" placeholder="请输入密码" maxlength="20">
						</div>
					</div>
					<div class="form-group">
						<label for="J_Tpassword">确认密码：</label>
						<div class="field">
							<input id="J_Tpassword" class="text-box" name="passConfirm"
								type="password" placeholder="请再次输入密码"  maxlength="20">
						</div>
					</div>
				</div>
				<div class="tips">
					<p>密码由6-20位字符组成，包含至少两种以上的字母，数字或半角字，区分大小写。</p>
				</div>
				<div class="error_tips hide"></div>
				<div class="form-btn">
					<input type="button" value="提交" class="btn" id="J_forgotPass_btn"
						onclick="subNewPass()" />
				</div>
			</div>
		</div>
	</form>
	<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script>
	<script src="${staticFile_s}/commons/js/user/getPass.js"
		type="text/javascript"></script>
</body>
</html>
