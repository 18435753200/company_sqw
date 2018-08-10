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
<link href="${staticFile_s}/commons/css/base.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/regLogin.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>

<title><spring:message code="title_register" /></title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>

<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>
<header id="header" class="head"><a href="javascript:goBack();" class="goback"><span class="icon i-goback"></span></a>
    <h2 class="h-title">注册</h2>
</header>
<form action="<%=path %>/customer/newPass" method="post" id="formSubmit" onsubmit="return false;">
<input type="hidden" name="mobile" id="mobile" value="${mobile }">
<input type="hidden" name="origin" id="origin" value="${origin }">
<div class="wrap rlf-wrap">
    <div class="rlf-form">
        <div class="from-box">
            <div class="form-group">
                <label for="J_password">设置密码 ：</label>
                <div class="field">
                    <input id="J_password" class="text-box" name="pass" type="password" placeholder="请设置登录密码" autocomplete="off" maxlength="20"/>
                </div>
            </div>
        </div>
        
        <div class="tips">
            <p>密码由6-20位字符组成，包含至少两种以上的字母，数字或字符，区分大小写。</p>
        </div>
        	<div class="error_tips hide"></div>
        <div class="form-btn">
            <input type="button" value="注册并登录" class="btn" id="J_forgotPass_btn" onclick="subPass()" />
        </div>
		<div class="agreement"><label>点击注册，表示您同意众聚猫<a href="aggreement.jsp">《服务协议》</a></label></div>
    </div>
</div>
</form>
<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script>
<script src="${staticFile_s}/commons/js/user/register.js" type="text/javascript"></script>
<script src="${staticFile_s}/commons/js/user/des.js" type="text/javascript"></script>
</body>
</html>