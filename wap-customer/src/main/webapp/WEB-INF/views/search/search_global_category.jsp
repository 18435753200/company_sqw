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
<title><spring:message code="title_item_list" /></title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="${staticFile_s}/commons/css/base.css">
<link rel="stylesheet" href="${staticFile_s}/commons/css/list.css">
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>
<body>

<header id="header" class="head"><a href="javascript:goBack();" class="goback"><span class="icon i-goback"></span></a>
    <h2 class="h-title">商品列表</h2>
    <a href="<%=path%>/index/index.html" class="gohome"><span class="icon i-gohome"></span></a></header>
<div class="wrap">
    <div class="filter-wrap">
        <div class="filter">
            <ul>
                <li onClick="$('#J_categoryMenu').removeClass('show')"><a href="javascript:;"><span class="icon i-world"></span>全球</a></li>
                <li id="J_category" onClick="$('#J_categoryMenu').toggleClass('show')"><a href="javascript:;"><span class="icon i-menu"></span>品类</a></li>
            </ul>
        </div>
        <div class="filter-con category-menu" id="J_categoryMenu">
            <ul class="clearfix">
                <%-- <li><a href="#"><span><img src="<%=path%>/commons/images/cat_001.png" width="19" height="12" /></span><em>包包控</em></a></li>
                <li><a href="#"><span><img src="<%=path%>/commons/images/cat_002.png" width="20" height="11" /></span><em>衣帽间</em></a></li>
                <li><a href="#"><span><img src="<%=path%>/commons/images/cat_003.png" width="7" height="23" /></span><em>美妆达人</em></a></li>
                <li><a href="#"><span><img src="<%=path%>/commons/images/cat_004.png" width="11" height="11" /></span><em>酒类</em></a></li>
                <li><a href="#"><span><img src="<%=path%>/commons/images/cat_005.png" width="17" height="18" /></span><em>妈咪宝贝</em></a></li>
                <li><a href="#"><span><img src="<%=path%>/commons/images/cat_006.png" width="16" height="18" /></span><em>手表</em></a></li>
                <li><a href="#"><span><img src="<%=path%>/commons/images/cat_007.png" width="16" height="14" /></span><em>首饰迷</em></a></li>
                <li><a href="#"><span><img src="<%=path%>/commons/images/cat_008.png" width="18" height="15" /></span><em>眼镜</em></a></li>
                <li><a href="#"><span><img src="<%=path%>/commons/images/cat_009.png" width="18" height="13" /></span><em>数码电器</em></a></li>
                <li><a href="#"><span><img src="<%=path%>/commons/images/cat_010.png" width="19" height="20" /></span><em>乐生活</em></a></li>
                <li><a href="#"><span><img src="<%=path%>/commons/images/cat_011.png" width="6" height="23" /></span><em>男人帮</em></a></li>
                <li><a href="#"><span><img src="<%=path%>/commons/images/cat_012.png" width="18" height="16" /></span><em>吃货控</em></a></li> --%>
                
                <c:if test="${not empty dispCategoryInfo}">
                	<c:forEach items="${dispCategoryInfo}" var="dispCategory" varStatus="statu">
                		<li><a href="<%=path%>/searchController/toSearchResult?cdid=${firstDispId}-${dispCategory.cateDispId}"><span><img src="${picUrl}${dispCategory.picUrl}" width="18" height="16" onerror="this.src='${staticFile_s}/commons/images/ccigmall.png'" /></span><em>${dispCategory.dispNameCn}</em></a></li>
                	</c:forEach>
                </c:if>
            </ul>
        </div>
    </div>
    <div class="country">
    	<%-- <a href="#"><img src="<%=path%>/commons/images/list_country_01.jpg" /></a>
    	<a href="#"><img src="<%=path%>/commons/images/list_country_02.jpg" /></a>
    	<a href="#"><img src="<%=path%>/commons/images/list_country_03.jpg" /></a>
    	<a href="#"><img src="<%=path%>/commons/images/list_country_04.jpg" /></a>
    	<a href="#"><img src="<%=path%>/commons/images/list_country_03.jpg" /></a>
    	<a href="#"><img src="<%=path%>/commons/images/list_country_04.jpg" /></a>
    	<a href="#"><img src="<%=path%>/commons/images/list_country_03.jpg" /></a>
    	<a href="#"><img src="<%=path%>/commons/images/list_country_04.jpg" /></a> --%>
    	<c:if test="${not empty tcCountries }">
    	<ul>
           	<c:forEach items="${tcCountries}" var="country" varStatus="statu">
           		<li><a href="<%=path%>/searchController/toSearchResult?countryName=${country.name}"><img src="${picUrl}${country.img}"/></a></li>
           	</c:forEach>
         </ul>
        </c:if>
    </div>
    <input id="path" type="hidden" value="<%=path%>">
    <input id="totalPage" type="hidden" value="${totalPage}">
 	<div class="p-list">
 		<c:if test="${not empty searchResponse.items }">
 			<c:forEach items="${searchResponse.items}" var="item" varStatus="statu">
 				<div class="p-item">
		        	<a href="<%=path%>/item/get/${item.pid}">
		        		<c:if test="${item.promotion == 10}">
	        				<span class="act-icon act-xs"></span>
	        			</c:if>
			            <div class="p-item-pic"><img src="${picUrl}${item.imageurl}" /></div>
			            <div class="p-item-content">
			                <div class="item-content-from"> <em><img src="${picUrl}${item.countryImg}" /></em><span>${item.cyName}</span><i>${item.proStyleDescribe}</i></div>
			                <div class="item-content-title">${item.b2cPname}</div>
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
</div>
<a href="#top" class="backtop show"><span class="icon i-backtop"></span></a> 

<!-- js相关内容 -->
<script src="${staticFile_s}/commons/js/zepto.min.js"></script> 
<script src="${staticFile_s}/commons/js/main.js"></script>
<script src="${staticFile_s}/commons/js/search/search_global_category.js"></script>
</body>
</html>
