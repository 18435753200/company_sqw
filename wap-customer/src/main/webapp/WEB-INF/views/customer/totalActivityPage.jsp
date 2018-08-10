<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
	<head>
        <title>活动页面</title>
        <meta name="keywords" content="这里是关键词">
        <meta name="description" content="这里是描述">
		<meta name="viewport" content="minimal-ui=yes,width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
        <meta name="apple-mobile-web-app-status-bar-style" content=" black ">
        <meta name="apple-mobile-web-app-title" content="众聚猫">
        <meta name="format-detection" content="telephone=no">
        <%@include file="/WEB-INF/views/commons/base.jsp" %>
        <%-- <%@include file="/WEB-INF/views/commons/user.jsp" %> --%>
        <link href="${staticFile_s}/commons/css/newbese.css" rel="stylesheet" type="text/css">
        <link href="${staticFile_s}/commons/css/activity.css" rel="stylesheet" type="text/css">
        <%--  <link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
        <script src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"  type="text/javascript" ></script> --%>
        <%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
	</head>
    <body>
    	<%-- hidden域 --%>
		<input type="hidden" id="path" value="<%=path %>"/>
    	<div class="activity-m">
			<h2><img src="${staticFile_s}/commons/img/activity2.jpg"></h2>
			<a class="m-go" href="${staticFile_s }/customer/toSubActivityPage?origin=${origin }">进入商城</a>
			<p class="m-my">红包已放到您众聚猫的账户，请到您的<a href="${staticFile_s }/customer/toMy">账户查看</a>和使用您的红包。</p>
		</div>
<%-- <script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script>
<script src="${staticFile_s}/commons/js/user/registCoupons.js" type="text/javascript"></script>
<script src="${staticFile_s}/commons/js/zepto.min.js" type="text/javascript"></script> --%>
	</body>
</html>


