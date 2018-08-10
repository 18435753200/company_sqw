<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<link rel="shortcut icon" type="image/x-icon"
	href="${path}/commons/img/favicon.ico" />
<title><spring:message code="title_search_result" /></title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<link rel="stylesheet" href="${staticFile_s}/commons/css/searchhome.css">
<style>
.search-hot {
    padding: 20px 15px;
    background: #fff;
    overflow: hidden;
}
.search-input input.but {
    height: 30px;
    width: 30px;
    position: absolute;
    right: 60px;
    top: 14px;
    background-size: 14px 14px;
    cursor: pointer;
}
</style>
</head>
<body>
	<input type="hidden" id="path" value="<%=path%>" />
	<input type="hidden" id="basePath" value="<%=basePath%>" />

	<!--搜索部分-->
	<form action="" method="get">
		<div class="search-input" style="padding: 8px 55px 8px 40px;">
			<a href="javascript:goBack();"
				style="position: absolute;display:block;width: 12px;height: 12px;border: 1px solid rgba(0,0,0,0.6); border-top:none;border-right:none;-webkit-transform: rotate(45deg);left: 17px;top:17px;"></a>
			<input type="text" id="searchInput" class="text"
				placeholder="搜索您想要的商品"  style="padding-left: 25px;line-height: 30px;"> 
				<input name="" type="button"
				class="but" style="width: 13px;height: 13px;backgroud-size: contain; left: 46px;top:16px;"> <a href="javascript:void(0);"  onclick="gotoSearch();" class="cancel"  style="color: #000;font-size: 14px;top:13px;">搜索</a>
			<ul class="history-list" style="display:none;">
				<!-- 				<li><a href="#">呢子大衣</a></li> -->
				<!-- 				<li><a href="#">呢子大衣 女</a></li> -->
				<!-- 				<li><a href="#">呢子大衣 男</a></li> -->
				<!-- 				<li><a href="#">呢子大衣 prada</a></li> -->
			</ul>
		</div>
	</form>
	<div class="search-m">
		<!--搜索部分end-->

		<!-- 		<div class="search-hot">
			<h2>热门搜索</h2>
			<div class="hot-list" id="hostListDiv">
				<a href="#">防晒</a>
				<a href="#">驱蚊</a>
				<a href="#">卫生巾</a>
				<a href="#">化妆水</a>
				<a href="#">面膜</a>
				<a href="#">唇膏</a>
				<a href="#">酵素</a>
				<a href="#">纸尿裤</a>
				<a href="#">奶粉</a>
				<a href="#">洗发沐浴</a>
				<a href="#">膳魔师</a>
				<a href="#">洗洁精</a>
			</div>
		</div> -->

		<div class="search-hot">
			<h2>
				最近搜索<a href="#" class="remove-history"></a>
			</h2>
			<div class="hot-list" id="historySearchListDiv"></div>
		</div>

		<div class="search-hot commodity">
			<h2>商品推荐</h2>
			<ul class="commodity-list">
			</ul>
		</div>
	</div>
	<%@include file="/WEB-INF/views/commons/footer.jsp"%>
	<!-- js相关内容 -->
	<script src="${staticFile_s}/commons/js/zepto.min.js"></script>
	<script src="${staticFile_s}/commons/js/main.js"></script>
	<script src="${staticFile_s}/commons/js/search/search.js"></script>
	<script>
		$(function() {
			initSearchIndex();
			getCommodityList();
		});
	</script>
</body>
</html>
