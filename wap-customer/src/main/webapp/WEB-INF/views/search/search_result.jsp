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
<title><spring:message code="title_search_result" /></title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="${staticFile_s}/commons/css/base.css">
<link rel="stylesheet" href="${staticFile_s}/commons/css/search.css">
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>
<body>

<input id="path" type="hidden" value="<%=path%>">
<input id="totalPage" type="hidden" value="${totalPage}">
<header id="header" class="head"><a href="javascript:goBack();" class="goback"><span class="icon i-goback"></span></a>
    <div class="search-box">
        <input type="search" class="search-text" name="keyword" value="${fn:escapeXml(searchRequest.keyword)}" placeholder="请输入关键字进行查询"/>
        <input type="hidden"  id="selectedBrandName" value="${searchRequest.brandName}">
        <input type="hidden"  id="selectedCyid" value="${searchRequest.cyid}">
        <input type="hidden"  id="selectedPriceRange" value="${searchRequest.priceRange}">
         <input type="hidden"  id="countryName" value="${searchRequest.countryName}">
          <input type="hidden"  id="cdid" value="${searchRequest.cdid}">
          <input type="hidden"  id="countryName" value="${searchRequest.countryName}">
		<input type="button" class="search-btn">
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
    <a href="javascript:;" class="gohome" id="J_filter">筛选</a></header>
<div class="wrap searchResult-wrap">
    <%-- <div id="content">
        <div class="p-list">
            <div class="p-item"><a href="#"><img src="<%=path%>/commons/images/list_01.jpg" /></a></div>
            <div class="p-item"><a href="#"><img src="<%=path%>/commons/images/list_02.jpg" /></a></div>
            <div class="p-item"><a href="#"><img src="<%=path%>/commons/images/list_03.jpg" /></a></div>
            <div class="p-item"><a href="#"><img src="<%=path%>/commons/images/list_04.jpg" /></a></div>
            <c:if test="${not empty searchResponse}">
            	<c:forEach items="${searchResponse.items}" var="item" varStatus="statu">
            			<div class="p-item"><a href="#"><img src="<%=path%>/commons/images/list_04.jpg" /></a></div>
            	</c:forEach>
            
            </c:if>
        </div>
    </div> --%>
    <div class="p-list">
       <!--  <div class="p-item"><a href="#">
            <div class="p-item-pic"><img src="http://img.gomein.net.cn/image/bbcimg/production_image/nimg/20140726/21/8004065308/109080589_160.jpg" /></div>
            <div class="p-item-content">
                <div class="item-content-from"> <em><img src="images/japan.png" /></em><span>日本</span> </div>
                <div class="item-content-title">囍人坊 贡缎丝棉全棉提花四件套婚庆被套件床上用品纯棉天丝床品(维也纳典 1.8M)</div>
                <div class="item-content-price">
                    <div class="main-price"><b> </b><span>170</span></div>
                    <div class="main-subPirce"><span class="price-gray">国内价：<i> 306.00</i></span></div>
                </div>
            </div>
            </a>
        </div> -->
        <c:if test="${not empty searchResponse.items}">
        	<c:forEach items="${searchResponse.items}" var="item" varStatus="statu">
        		<div class="p-item">
	        		<a href="<%=path%>/item/get/${item.pid}">
	        			<c:if test="${item.promotion == 10}">
	        				<span class="act-icon act-xs"></span>
	        			</c:if>
			            <div class="p-item-pic"><img src="${picUrl1}${item.imageurl}" /></div>
			            <div class="p-item-content">
			                <div class="item-content-from"> <em><img src="${picUrl1}${item.countryImg}" /></em><span>${item.cyName}</span><i>${item.proStyleDescribe}</i></div>
			                <div class="item-content-title">${item.highlightedPname}</div>
			                <div class="item-content-price">
			                    <div class="main-price"><b> </b><span><fmt:formatNumber value="${fn:escapeXml(item.unit_price)}" pattern="#0.00" /></span></div>
			                    <div class="main-subPirce"><p>(约 ${item.exchange})</p><span class="price-gray">国内价：<i><fmt:formatNumber value="${fn:escapeXml(item.domestic_price)}" pattern="#0.00" /></i></span></div>
			          		 </div>
	          		   </div>
	            	</a>
        		</div>
        	
        	</c:forEach>
        </c:if>
        
</div>
<div class="filter-wrap" id="J_filterWrap">
    <header id="header" class="head"><a href="javascript:;" class="goback closeBtn"><span class="icon i-goback"></span></a>
        <h2 class="h-title">属性</h2>
        <a href="javascript:;" class="gohome" id="sousou">确定</a>
    </header>
    <div class="filter-hd">
        <ul class="filter-tab" id="J_filterTabs">
            <li class="current" attrName = "brand"><a href="javascript:;">品牌</a></li>
            <li attrName = "cyid"><a href="javascript:;">产地</a></li>
            <li attrName = "price"><a href="javascript:;">价格</a></li>
            <!-- <li><a href="javascript:;">库存</a></li> -->
        </ul>
    </div>
    <div class="filter-bd">
    
    	<c:if test="${not empty searchResponse.matrix}">
        		<c:forEach items="${searchResponse.matrix}" var="matrix" varStatus="statu1">
        			<c:choose>
        				<c:when test="${matrix.key.brandName}">
        					<div class="filter-item J_filterContents" id="brand">
            					<ul class="filterBrand">
		        					<c:forEach items="${matrix.value}" var="brandValues" varStatus="statu2">
		        						 <li attrValId = "${brandValues.id}"><a href="javascript:;">${brandValues.name}</a></li>
		        					</c:forEach>
        					 	</ul>
        					</div>
        				</c:when>
        				
        				<c:when test="${matrix.key.cyid}">
        					<div class="filter-item J_filterContents hide" id="cyid">
           						 <ul class="filterPlace">
		        					<c:forEach items="${matrix.value}" var="cyidValues" varStatus="statu3">
		        						 <li attrValId = "${cyidValues.id}"><a href="javascript:;">${cyidValues.name}</a></li>
		        					</c:forEach>
        					 	</ul>
        					</div>
        				</c:when>
        				
        				<c:when test="${matrix.key.priceRange}">
        					<div class="filter-item J_filterContents hide" id="price">
           						 <ul class="filterPrice">
		        					<c:forEach items="${matrix.value}" var="priceValues" varStatus="statu4">
		        						 <li attrValId = "${priceValues.id}"><a href="javascript:;">${priceValues.name}</a></li>
		        					</c:forEach>
        					 	</ul>
        					</div>
        				</c:when>
        				
        			</c:choose>
        		</c:forEach>
        </c:if>
    </div>
</div>

<!-- js相关内容 -->
<script src="${staticFile_s}/commons/js/zepto.min.js"></script> 
<script src="${staticFile_s}/commons/js/main.js"></script> 
<script src="${staticFile_s}/commons/js/search/search.js"></script>
</body>
</html>