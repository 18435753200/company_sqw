<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
	<head>
        <meta charset="utf-8">
        <title>输入验证码</title>
        <meta name="keywords" content="这里是关键词">
        <meta name="description" content="这里是描述">
        <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no">
        <meta name="apple-mobile-web-app-status-bar-style" content=" black ">
        <meta name="apple-mobile-web-app-title" content="众聚猫">
        <meta name="format-detection" content="telephone=no">
        <meta name="baidu-tc-cerfication" content="30f757b11897dc4f697eb568cb7eb2a3" />
        <meta name="baidu-site-verification" content="SgTbeKNm8i" />
        <%@include file="/WEB-INF/views/commons/user.jsp" %>
       <link href="${staticFile_s}/commons/css/newbese.css" rel="stylesheet" type="text/css">
        <link href="${staticFile_s}/commons/css/login.css" rel="stylesheet" type="text/css">
        <link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
        <%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
	</head>
    <body class="reglog">
    	<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
		<header><div class="tit">输入验证码</div></header>
    	<section class="column_body">

			<div class="login">

                <div class="login-group n">
                	<input type="hidden" value="${mobile }" id="phoneNumber">
                    <label class="la">验证码已发送至</label>
                    <div class="field"><input type="text" value="${mobilestr }" class="text-box" disabled="disabled"></div>
                </div>

                <div class="login-group">
                    <div class="field">
                        <input type="number" placeholder="请输入验证码" class="cl" id="J_forgotPassCode">
                        <input type="button"  id="reGet"  value="重新发送(60秒)" onclick="getCode()" class="cr" disabled="disabled">
                    </div>
                </div>
				
				 <div class="login-group">
                    <div class="field">
                       <input type="password" placeholder="请输入6-20位数字和字母组合构成的密码" class="text-box" id="J_password">
                    </div>
                </div>


                <div class="login-btn">
                    <input type="button" value="注册" class="btn" onclick="subReg()">
                </div>

			</div>

    	</section> 
		<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script>
		<script src="${staticFile_s}/commons/js/user/inputCode.js" type="text/javascript"></script>
		<script src="${staticFile_s}/commons/js/user/des.js" type="text/javascript"></script>
	</body>
</html>