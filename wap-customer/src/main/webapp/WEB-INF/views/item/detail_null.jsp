<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<link rel="shortcut icon" type="image/x-icon" href="${path}/commons/img/favicon.ico" />
<title><spring:message code="title_item_null" /></title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="${staticFile_s}/commons/css/base.css">
<link rel="stylesheet" href="${staticFile_s}/commons/css/search.css">
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
<style>
#header {
    position: relative;
    top: 0;
    left: 0;
    width: 100%;
    height: 44px;
    display: -moz-box;
    display: -webkit-box;
    display: -webkit-flex;
    display: -moz-flex;
    display: -ms-flexbox;
    display: -ms-flex;
    display: flex;
    background-color: #e60012;
    z-index: 999;
}
.header a.bug-go {
    position: absolute;
    display: block;
    width: 1rem;
    height: 1rem;
    border: 0.1rem solid rgba(255,255,255,1);
    border-top: none;
    border-right: none;
    -webkit-transform: rotate(45deg);
    left: 15px;
    top: 1.4rem;
}
.goback {
    left: 10px;
    right: auto;
    padding-right: 11px;
}
.null-text {
    padding: 20px;
    font-size: 16px;
    line-height: 30px;
    color: #313131;
    text-align: center;
}
a.goHome-btn {
    display: inline-block;
    width: 135px;
    height: 40px;
    margin: 0 10px;
    font-size: 16px;
    line-height: 40px;
    color: #fff;
    text-align: center;
    background-color: #e60012;
    border-radius: 4px;
}
</style>
</head>

<body>
	
	<input id="path" type="hidden" value="<%=path%>">
<header id="header" class="head">
<!-- <a href="javascript:goBack();" class="goback"><span class="icon i-goback"></span></a> -->
<a href="javascript:goBack();" class="bug-go goback" style="position: absolute;
    width: 0.3rem;
    height: 1rem;
    border: 1px solid rgba(255,255,255,1);
    border-top: none;
    border-right: none;
    -webkit-transform: rotate(45deg);
    left: 15px;
    top: 1rem;"></a>
<%--     <div class="search-box mlr">
        <input type="search" class="search-text" value="${fn:escapeXml(searchRequest.keyword)}" placeholder="请输入关键字进行查询"/>
        <input type="button" class="search-btn" />
        <div class="search-tips">
    	</div>
    </div> --%>
    
</header>
<div class="wrap">
    <div class="null">
        <div class="null-icon"><span class="icon i-search-null"></span></div>
        <p class="null-text">很抱歉，您查看的商品不存在，可能已下架或者被转移。</p>
        <a href="/" class="goHome-btn">返回首页</a></div>
</div>
<%@include file="/WEB-INF/views/commons/footer.jsp" %>
<script src="${staticFile_s}/commons/js/zepto.min.js"></script>
<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script> 
<script src="${staticFile_s}/commons/js/search/search.js"></script>

</body>
</html>