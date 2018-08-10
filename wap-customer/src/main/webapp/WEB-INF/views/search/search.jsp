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
<title><spring:message code="title_search" /></title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="${staticFile_s}/commons/css/base.css">
<link rel="stylesheet" href="${staticFile_s}/commons/css/search.css">
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>
<body>

<input id="path" type="hidden" value="<%=path%>">
<header id="header" class="head"><a href="javascript:goBack();" class="goback"><span class="icon i-goback"></span></a>
    <div class="search-box mlr">
        <input type="search" class="search-text" id="q" name="keyWord"  placeholder="请输入关键字进行查询"/>
        <input type="button" class="search-btn" />
        <div class="search-tips">
	        <!-- <ul class="auto-tip">
	        	<li><span>约有426条</span><a href="#">面包机</a></li>
	        	<li><span>约有426条</span><a href="#">面包机</a></li>
	        	<li><span>约有426条</span><a href="#">面包机</a></li>
	        	<li><span>约有426条</span><a href="#">面包机</a></li>
	        	<li><span>约有426条</span><a href="#">面包机</a></li>
	        	<li><span>约有426条</span><a href="#">面包机</a></li>
	        </ul> -->
        </div>        
    </div>
    </header>
<div class="wrap search-wrap">
    <div class="search-hd">
        <ul id="J_searchTabs">
            <li class="current"><a href="javascript:;">热门搜索</a></li>
            <li><a href="javascript:;">最近搜索</a></li>
        </ul>
    </div>
    <div class="search-bd">
        <ul class="search-list hot-search J_searchContents">
           
            <c:if test="${not empty findSearchHotWords}">
            	<c:forEach items="${findSearchHotWords}" var="hotWord" varStatus="statu">
            		<li><a href="<%=path%>/searchController/toSearchResult?keyword=${hotWord.kw}">${hotWord.kw}</a></li>		
            	</c:forEach>
            </c:if>
        </ul>
        <ul class="search-list lately-search J_searchContents hide">
        	<c:if test="${not empty cookieVlaues }">
        		<c:forEach items="${cookieVlaues }" var="cookieVlaue" varStatus="statu"> 
        			<li class="nearSearch"><a href="javascript:;">${cookieVlaue}</a></li>
        		</c:forEach>
        		 <a href="<%=path%>/searchController/delNearSearch"><span class="search-clear">清除搜索记录</span></a>
        	</c:if>
           
        </ul>
    </div>
</div>
<div class="filter-wrap" id="J_filterWrap">
    <header id="header" class="head"><a href="javascript:;" class="goback closeBtn"><span class="icon i-goback"></span></a>
        <h2 class="h-title">品牌</h2>
    </header>
    <div class="filter-hd">
        <ul class="filter-tab" id="J_filterTabs">
            <li class="current"><a href="javascript:;">品牌</a></li>
            <li><a href="javascript:;">产地</a></li>
            <li><a href="javascript:;">价格</a></li>
            <li><a href="javascript:;">库存</a></li>
        </ul>
    </div>
    <div class="filter-bd">
        <div class="filter-item J_filterContents">
            <ul class="filterBrand">
                <li><a href="#">全部</a></li>
                <li><a href="#">星期六（St&Sat）</a></li>
                <li><a href="#">天美意（TEENMIX）</a></li>
                <li class="active"><a href="#">FaSoLa</a></li>
                <li><a href="#">恋家</a></li>
                <li><a href="#">莱尔斯丹（le saunda）</a></li>
                <li><a href="#">古奇天伦（Guciheaven）</a></li>
                <li><a href="#">爱补（ECCO）</a></li>
                <li><a href="#">惠夫人</a></li>
                <li><a href="#">耐克</a></li>
                <li><a href="#">安踏</a></li>
            </ul>
        </div>
        <div class="filter-item J_filterContents hide">
            <ul class="filterPlace">
                <li><a href="#">全球</a></li>
                <li><a href="#">丹麦</a></li>
                <li class="active"><a href="#">美国</a></li>
                <li><a href="#">比利时</a></li>
                <li><a href="#">印尼</a></li>
                <li><a href="#">马来西亚</a></li>
                <li><a href="#">菲律宾</a></li>
                <li><a href="#">瑞士</a></li>
                <li><a href="#">意大利</a></li>
                <li><a href="#">西班牙</a></li>
                <li><a href="#">奥德利</a></li>
                <li><a href="#">法国</a></li>
            </ul>
        </div>
        <div class="filter-item J_filterContents hide">
            <ul class="filterPrice">
                <li><a href="#">全部</a></li>
                <li><a href="#">0-49</a></li>
                <li><a href="#">50-199</a></li>
                <li class="active"><a href="#">200-299</a></li>
                <li><a href="#">300-499</a></li>
                <li><a href="#">500-699</a></li>
                <li><a href="#">700-1999</a></li>
                <li><a href="#">2000以上</a></li>
            </ul>
        </div>
        <div class="filter-item J_filterContents hide">
            <ul class="filterStock">
                <li class="active"><a href="#">全部</a></li>
                <li><a href="#">有货</a></li>
                <li><a href="#">无货</a></li>
            </ul>
        </div>
    </div>
</div>

<!-- js相关内容 -->
<script src="${staticFile_s}/commons/js/zepto.min.js"></script> 
<script src="${staticFile_s}/commons/js/main.js"></script> 
<script src="${staticFile_s}/commons/js/search/search.js"></script>
<script src="${staticFile_s}/commons/js/search/yahoo-dom-event.js"></script>
<script src="${staticFile_s}/commons/js/search/suggest.js"></script>
</body>
</html>
