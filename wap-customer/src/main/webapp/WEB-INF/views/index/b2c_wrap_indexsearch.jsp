<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!doctype html>
<html>
	<head>
        <meta charset="utf-8">
        <title>主页_众聚猫</title>
        <meta name="keywords" content="这里是关键词">
        <meta name="description" content="这里是描述">
        <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no">
        <meta name="apple-mobile-web-app-status-bar-style" content=" black ">
        <meta name="apple-mobile-web-app-title" content="众聚猫">
        <meta name="format-detection" content="telephone=no">
	    <link rel="stylesheet" href="${staticFile_s }/commons/css/search_index.css">
        <script type="text/javascript" src="http://www.zhongjumall.com/commons/js/jquery.min.js"></script>
        <script type="text/javascript" src="http://s.zhongjumall.com/bca/js/web_phpstat.js"></script>
	</head>
    <body>
	    <input type="text" hidden="hidden" id="path" value="${staticFile_s }"/>
	    <div class="header">
			<a class="bug-go" href="javascript:goBack();"></a>
			商品搜索
		</div>
		
		    <!--搜索部分-->
			<div class="search-input">
				<input type="text" id="keyWord" name="keyWord" class="hb-text text" autocomplete="off" placeholder="搜索品牌或商品" value="${condition.keyWord }">
				<span class="hb-tag"></span>
				<input id="keywordSearch" name="keywordSearch" type="button" class="hb-sbmit but"  onclick="search();">
		    </div>
		
		<!--搜索部分end-->
		<div class="search-hot">
			<span>热搜</span>
			<c:forEach items="${hotWord }" var="hw">
			    <a href="${staticFile_s }/searchController/toSearchResult?keyword=${hw }">${hw }</a>
			</c:forEach>
		</div>
		
		<%--<div class="search-history">
			<h2>历史搜索</h2>
			<ul class="history-list">
				<li><a href="#">SKII</a></li>
				<li><a href="#">纸尿裤</a></li>
				<li><a href="#">奶粉</a></li>
				<li><a href="#">面膜</a></li>
			</ul>
		</div>
		
		<a href="#" class="remove-history">清空历史记录</a>
		
		--%>
		<script type="text/javascript" src="${staticFile_s }/commons/js/search/yahoo-dom-event.js"></script>
        <script type="text/javascript" src="${staticFile_s }/commons/js/search/suggest.js"></script>
        <script type="text/javascript" src="${staticFile_s }/commons/js/search/search_index.js"></script>
    </body>    
</html>