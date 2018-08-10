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

<title><spring:message code="title_browse_history" /></title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>

<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>
<header id="header" class="head"><a href="javascript:goBack();" class="goback"><span class="icon i-goback"></span></a>
    <h2 class="h-title">浏览历史</h2>
    <a href="index.html" class="gohome"><span class="icon i-gohome"></span></a> </header>
<div class="wrap" id="browseHistory">
    <ul class="pro-list">
        <li><a href="#">
            <div class="pic"><img src="${staticFile_s}/commons/images/history.jpg" /></div>
            <p class="double-row name">优雅针织空气层直身小A摆无袖连衣裙</p>
            <p class="price"><span class="xj"><em></em>399.3</span><span class="yj"><em></em>15.0</span></p>
            </a></li>
        <li><a href="#">
            <div class="pic"><img src="${staticFile_s}/commons/images/history.jpg" /></div>
            <p class="name">优雅针织空气层直身小A摆无袖连衣裙</p>
            <p class="price"><span class="xj"><em></em>399.3</span><span class="yj"><em></em>15.0</span></p>
            </a></li>
        <li><a href="#">
            <div class="pic"><img src="${staticFile_s}/commons/images/history.jpg" /></div>
            <p class="name">优雅针织空气层直身小A摆无袖连衣裙</p>
            <p class="price"><span class="xj"><em> </em>399.3</span><span class="yj"><em> </em>15.0</span></p>
            </a></li>
        <li><a href="#">
            <div class="pic"><img src="${staticFile_s}/commons/images/history.jpg" /></div>
            <p class="name">优雅针织空气层直身小A摆无袖连衣裙</p>
            <p class="price"><span class="xj"><em> </em>399.3</span><span class="yj"><em> </em>15.0</span></p>
            </a></li>
        <li><a href="#">
            <div class="pic"><img src="${staticFile_s}/commons/images/history.jpg" /></div>
            <p class="name">优雅针织空气层直身小A摆无袖连衣裙</p>
            <p class="price"><span class="xj"><em> </em>399.3</span><span class="yj"><em> </em>15.0</span></p>
            </a></li>
    </ul>
</div>
<%@include file="/WEB-INF/views/commons/footer.jsp" %>
<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script>
</body>
</html>