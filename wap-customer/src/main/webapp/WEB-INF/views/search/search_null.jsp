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
	
	String keyword = request.getParameter("keyword");
%>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<link rel="shortcut icon" type="image/x-icon" href="${path}/commons/img/favicon.ico" />
<title><spring:message code="title_search_result" /></title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<link rel="stylesheet" href="${staticFile_s}/commons/css/newbese.css">
<link rel="stylesheet" href="${staticFile_s}/commons/css/search.css">

</head>
<body>
	<input type="hidden" id="path" value="<%=path %>" />
	<input type="hidden" id="basePath" value="<%=basePath %>" />
	<input id="totalPages" type="hidden" value="${totalPage}">
		
		<!--搜索部分end-->
		<div class="search-no">
		    <span></span>
			暂无此类商品
		</div>
		<div class="search-hot commodity">
			<h2>商品推荐</h2>
			<ul class="commodity-list">
				
			</ul>
		</div>
<%@include file="/WEB-INF/views/commons/footer.jsp" %>
<!-- js相关内容 -->
<script src="${staticFile_s}/commons/js/zepto.min.js"></script> 
<script src="${staticFile_s}/commons/js/main.js"></script> 
<script src="${staticFile_s}/commons/js/search/search.js"></script>
<script>
$(function(){
	initSearchNull();
	getCommodityList();
	$("#totalPage").val($("#totalPages").val());
	
});
</script>
</body>
</html>
