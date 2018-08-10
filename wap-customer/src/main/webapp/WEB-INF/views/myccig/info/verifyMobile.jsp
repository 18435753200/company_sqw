<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<title>手机验证-众聚猫手机版</title>
<%@include file="/WEB-INF/views/commons/base.jsp" %>
<link href="${staticFile_s}/commons/css/my.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/w_base.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/user/common.js"></script>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>

<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>	
	<div class="headly">
		<span onclick="goBack()"><b></b><i></i></span>
	    <h3>手机验证</h3>
    </div>
	<input type="hidden" value="${mobile }" id="mobile">
	<input type="hidden" value="${username }" id="userName">
	<input type="hidden" value="${from }" id="fro">	
	<div class="wrap">
		<div class="yzm">验证码已发送至&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ${mobile } </div>
	    <ul class="form-list">
	        <li>
	            <div class="label">验证码：</div>
	            <div class="field">
	                <input type="text" class="te" placeholder="请输入验证码" id="v_code">
	                <a href="javascript:" class="seco" id="checkCode" onclick="getCodePas()">重新发送</a>
	            </div>
	        </li>
       </ul>

	   <div class="exit">
			<a href="#" onclick="modified()">下一步</a>
	  </div>

	</div>

<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script>
<script src="${staticFile_s}/commons/js/cciginfo/info.js" type="text/javascript"></script>
</body>
</html>