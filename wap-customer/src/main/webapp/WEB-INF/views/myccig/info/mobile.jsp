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
<link href="${staticFile_s}/commons/css/base.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/user.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/user/common.js"></script>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>

<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>
<header id="header" class="head"><a href="<%=path  %>/cusInfo/cusInfo"  class="goback"><span class="icon i-goback"></span></a>
    <h2 class="h-title">手机验证</h2>
</header>
<div class="wrap" id="myinfo">
    <div class="m-tips">请输入新手机号和新手机获取的验证码</div>
    <ul class="form-list">
        <li>
            <div class="label">手　机：</div>
            <div class="field">
                <input type="text" class="text" id="newMobile" name="newMobile" placeholder="请输入新手机号"/>
                <input type="hidden" class="text" id="userName" name="userName" value="${username }"/>
            </div>
        </li>
        <li>
            <div class="label">验证码：</div>
            <div class="field">
                <input type="text" class="text" id="validCode" name="validCode" placeholder="请输入验证码" />
                <span class="code-btn" id="codeId" onclick="getCodeMobile()">获取验证码</span> 
                <span class="code-btn" id="codeId2"  ></span> 
                </div>
        </li>
    </ul>
    <div class="error_tips hide"></div>
    <div class="form-btn">
        <input type="button" value="保存修改" class="btn" id="J_updateInfo_btn" onclick="updateMobile()" >
    </div>
</div>
<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script>
<script src="${staticFile_s}/commons/js/cciginfo/info.js" type="text/javascript"></script>
</body>
</html>