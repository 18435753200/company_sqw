<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<%@ include file="/WEB-INF/views/commons/base.jsp"%>
<link href="${staticFile_s}/commons/css/base.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	$(document).ready(function(){
		
		setTimeout(toIndex,3000);
		
		function toIndex(){
			location.href='<%=basePath %>';
		}
	});
</script>
<title><spring:message code="title_error_404" /></title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>
<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>	
	<header id="header" class="head"><a href="javascript:goBack();" class="goback"><span class="icon i-goback"></span></a>
    <h2 class="h-title">系统错误</h2>
    <a href="index.html" class="gohome"><span class="icon i-gohome"></span></a> </header>
<div class="wrap" id="error">
    <div class="error-icon"></div>
    <div class="error-text">很抱歉，您访问的页面不存在</div>
</div>
</body>
</html>
