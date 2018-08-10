<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	request.setAttribute("path", path);
	request.setAttribute("basePath", basePath);
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<%-- <link href="${staticFile_s}/commons/css/newbese.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/my.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css"> --%>
<link rel="stylesheet" href="${staticFile_s}/commons/css/mui.min.css">


<title><spring:message code="title_address" /></title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
<%@include file="/WEB-INF/views/commons/commonsIco.jsp" %>
<style>
	.mui-bar {
    position: fixed;
    z-index: 10;
    right: 0;
    left: 0;
    height: 44px;
    padding-right: 10px;
    padding-left: 10px;
    border-bottom: 0;
    background-color: #e60012;
    -webkit-box-shadow: 0 0 1px rgba(0,0,0,.85);
    box-shadow: 0 0 1px rgba(0,0,0,.85);
    -webkit-backface-visibility: hidden;
    backface-visibility: hidden;
	}.mui-title {
	    color: #fff;
	}
	a {
	    text-decoration: none;
	    color: #fff;
	}
	a:active {
	    color: #ffffff;
	}
	.mui-card {
	    margin: 10px 0;
	        top: 2.5rem;
	        
	}
		.mui-card-header {
	    font-size: 17px;
	    border-radius: 2px 2px 0 0;
	    display: block;
	    text-align: center;
	}
	.mui-btn-block {
	    font-size: 18px;
	    display: block;
	    width: 90%;
	    margin: 0 auto;
	    margin-bottom: 10px;
	    padding: 15px 0;
	    line-height: 0.5rem;
	    height: 2.5rem;
	    border-radius: 50px;
	    background: #e60012;
	}
	.mui-col-xs-6 {
	    width: 24%;
	}
	.mui-table-view.mui-grid-view .mui-table-view-cell .mui-media-body {
	    font-size: 15px;
	    line-height: 15px;
	    display: block;
	    width: 100%;
	    height: 15px;
	    margin-top: 8px;
	    text-overflow: ellipsis;
	    color: #ababab;
	}
	.mui-card-content-inner {
    position: relative;
    padding: 15px 0;
	}
	
</style>
</head>
<body>
		<header id="header" class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">收银</h1>
		</header>
		<c:forEach items="${getShouyin }" var="getShouyin">
		<div class="mui-card">
				<div class="mui-card-header">${supplier.name }</div>
				<div class="mui-card-content">
					<div class="mui-card-content-inner">
		
			<ul class="mui-table-view mui-grid-view">
		        <li class="mui-table-view-cell mui-media mui-col-xs-6">
		            <a href="#">
		                <div class="mui-media-body">收银员</div></a></li>
		        <li class="mui-table-view-cell mui-media mui-col-xs-6">
		            <a href="#">
		                <div class="mui-media-body" style="color: #000;">${getShouyin.kfName }<img src="${path }/commons/img/shzxtp/dg.png" style=" vertical-align: middle;margin-left: 10px;"/></div></a></li>
		        <li class="mui-table-view-cell mui-media mui-col-xs-6">
		                <div class="mui-media-body">手机号</div></a></li>
		        <li class="mui-table-view-cell mui-media mui-col-xs-6" style="width: 30%;">
		            <a href="#">
		                <div class="mui-media-body" style="color: #000;">${getShouyin.mobile }</div></a></li>
		    </ul> 
		    
					</div>
						<button type="button" class="mui-btn mui-btn-danger mui-btn-block">开始收银</button>
				</div>
			  
		</div>
		</c:forEach> 
<input type="hidden" value="<%=path%>" id="path">
<%@include file="/WEB-INF/views/commons/footer.jsp" %>
<%-- <script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script>
<script src="${staticFile_s}/commons/js/address/address.js" type="text/javascript"></script> --%>
</body>
</html>