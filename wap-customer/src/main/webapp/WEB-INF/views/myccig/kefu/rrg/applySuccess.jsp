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
<title>申请退换货-众聚猫</title>
<%@include file="/WEB-INF/views/commons/base.jsp"%>
<link href="${staticFile_s}/commons/css/base.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/return.css" rel="stylesheet" type="text/css">
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>

<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>	
	<header id="header" class="head">
	<a href="<%=path%>/kf/rrg/list/1" class="goback"><span
			class="icon i-goback"></span></a>
		<h2 class="h-title">申请退换货</h2>
	</header>
	<div class="return">
        <div class="apply-ok">
            <span class="inc-ok"></span>
            <span class="wen-ok">申请成功</span>
            <p>服务单号：${serviceNo}<br />服务人员将在24小时内审核，审核结果会以短信方式通知您。</p>
        </div>
    </div>
</body>
</html>