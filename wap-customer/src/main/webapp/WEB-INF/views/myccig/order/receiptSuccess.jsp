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
<link href="${staticFile_s}/commons/css/user.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>

<title><spring:message code="title_order_receipt_success" /></title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>

<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>
<header id="header" class="head"><a href="${path}/cusOrder/toMyAllOrder?pageNow=1&status=${status}" class="goback"><span class="icon i-goback"></span></a>
    <h2 class="h-title">订单收货成功</h2>
</header>
<div class="wrap" id="orderDetail">
    <div class="order-item">
        <div class="order-bd">
            <p>订单编号：${order.id }</p>
            <p>订单时间：<fmt:formatDate value="${order.createTime}" type="time" timeStyle="full" pattern="yyyy-MM-dd HH:mm"/></p>
            <p>订单金额：237.00</p>
        </div>
    </div>
    <div class="package-ft"><a href="${path}/cusOrder/orderDetail?orderId=${order.id}" class="package-btn package-btn-link">查看订单</a>
<%--     <a href="${path }/comment/go/${fn:escapeXml(order.id)}-0" class="package-btn package-btn-link">立即评价</a></div> --%>
</div>
<footer id="footer" class="footer clearfix">
    <nav class="footer-nav"><a href="index.html">首页</a><a href="cart.html">购物车</a><a href="#">客户端</a><a href="user.html">个人中心</a></nav>
    <p>COPYRIGHT@2016 北京三旗企业管理有限公司版权所有 <br />
        京ICP备案 16055863号</p>
</footer>
<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script>
</body>
</html>