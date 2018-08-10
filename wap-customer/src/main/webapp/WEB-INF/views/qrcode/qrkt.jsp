<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<head>
	<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no">
    <meta name="apple-mobile-web-app-status-bar-style" content=" black ">
    <meta name="format-detection" content="telephone=no">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>确定开通商户</title>
    <%@include file="/WEB-INF/views/commons/user.jsp" %>
    <link rel="stylesheet" href="${staticFile_s}/commons/css/public.css"type="text/css">
    <link rel="stylesheet" href="${staticFile_s}/commons/css/ensure_openup.css"type="text/css">
</head>

<body>
<header class="header">
    <a class="go_back" href="#"></a>
    <div class="title">确定开通商户</div>
</header>
<div class="content">
  <div class="headp">
       <div class="headp_img">
       <img src="${user.icon}">
       </div>
      <div class="name">${user.nickName}</div>
  </div>
    <div class="user">
        <span class="user_name">帐号ID</span>
        <span class="user_key">${user.mobile}</span>
    </div>
    <div class="prompt">
        <div class="p1">您将同意由该用户推荐您开通商户</div>
        <div class="p2">*该用户可以获取您的帐号信息以及帮助您填写商户信息</div>
    </div>
    <div class="btn">
       <a href="#" >确认开通</a>
    </div>
</div>
</body>
</html>
<script src="${staticFile_s}/commons/js/rem.js"></script>
