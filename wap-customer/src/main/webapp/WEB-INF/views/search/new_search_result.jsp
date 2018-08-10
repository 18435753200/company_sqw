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
<meta content="no" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<link rel="shortcut icon" type="image/x-icon"
	href="${path}/commons/img/favicon.ico" />
<title><spring:message code="title_search_result" /></title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="${staticFile_s}/commons/css/sousuocss/xsj.css">
<link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/sousuocss/aui.css" />
<link rel="stylesheet" href="${staticFile_s}/commons/css/sousuocss/search.css">
<link rel="stylesheet" href="${staticFile_s}/commons/css/sousuocss/newbese.css">
<%-- <%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %> --%>
 <script src="${staticFile_s}/commons/js/sousuojs/zepto.min.js"></script>
<script src="${staticFile_s}/commons/js/sousuojs/classify.js"></script>
<script src="${staticFile_s}/commons/js/sousuojs/main.js"></script>
<script src="${staticFile_s}/commons/js/sousuojs/newsearch.js"></script>
<%-- <%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %> --%>
<script>
	$(function() {
		//			screenheight();
		tab();
		scrol();
	})
</script>
<style type="text/css">

		.aui-bar-nav {
    top: 0;
    line-height: 2.25rem;
    background-color: #e60012;
    color: #ffffff;
    position: fixed;
}
.grabble {
    width: 100%;
    height: 3rem;
    left: 0;
    top: 0;
    background: #fff;
    z-index: 100;
    position: fixed;
    top: 2.1rem;
}
.list {
    position: relative;
    width: 100%;
    height: 100%;
    top: 5.1rem;
}
.choose {
    width: 100%;
    position: fixed;
    z-index: 50;
    background: #fff;
    top: 5.1rem;
}
.grabble ul li {
    float: left;
    width: 25%;
    height: 3rem;
    border-bottom: 1px solid #e60012;
    text-align: center;
    font-size: 0.7rem;
    line-height: 3rem;
    cursor: pointer;
    font-weight: 600;
}
.list li {
    overflow: hidden;
    border-bottom: 0.1rem solid #ececf0;
    padding: 10px;
    overflow: hidden;
    background: #fff;
}
.list li p .sbtom {
    font-size: 0.9rem;

}
.list li p .smidd {
    width: 100%;
    font-size: 1rem;
    margin: 10px 0;
    color: #000000;
    line-height: 1.2rem;
}
.list li p .sptop {
    width: 100%;
    line-height: 1.2rem;
    margin: 0.5rem 0;
}
.list li p .sbtom b {
    font-size: 0.8rem;
    font-weight: 500;
}
		.yiTuBiao {
		border-radius: 10px;
		border: 1px solid #e60012;
		text-align: center;
		font-size: 0.5rem;
		padding:2px 5px;
		color: #e60012;
		vertical-align: middle;
		}
		.spkyq{padding-left: 0.5rem;font-size: 0.6rem;font-weight: 400;color: #999;}
	</style>
</head>
<body>

	<input id="path" type="hidden" value="<%=path%>">
	<input id="totalPage" type="hidden" value="${totalPage}">
	 <header class="aui-bar aui-bar-nav">
        <a class="aui-pull-left aui-btn">
            <span class="aui-iconfont aui-icon-left" onclick="javascript:history.back(-1)"></span>
        </a>
        <div class="aui-title">商品列表</div>
        <a class="aui-pull-right aui-btn" href="<%=path%>/" >
            <span class="aui-iconfont aui-icon-home" class="home-go"></span>
        </a>
    </header>
	<header id="header" class="head">
		<div class="search-box">
			<input type="hidden" id="keyword" value="${searchRequest.keyword}">
			<input type="hidden" id="sortType" value="${searchRequest.sortType}">
			<input type="hidden" id="selectedBrandName"
				value="${searchRequest.brandName}"> <input type="hidden"
				id="selectedSupplierId" value="${searchRequest.supplierId}">
			<input type="hidden" id="selectedPriceRange"
				value="${searchRequest.priceRange}"> <input type="hidden"
				id="countryName" value="${searchRequest.countryName}"> <input
				type="hidden" id="selectedCdid" value="${searchRequest.cdid}">
			<input type="hidden" id="selectedPriceRange"
				value="${searchRequest.priceRange}"> <input type="hidden"
				id="selectedPromotion" value="${searchRequest.promotion}"> <input
				type="hidden" id="countryName" value="${searchRequest.countryName}">
			<input type="hidden" id="fcode" value="${searchRequest.fcode}">
	</header>
	<input type="hidden" id="b2csupply" value="${searchRequest.b2csupply}">


	<!--分类条-->

	<div class="grabble">
		<ul>
			<li id="selectSortType"><a href="javascript:;">分类</a><i></i></li>
			<li id="selectCdid"><a href="javascript:;">品牌</a><i></i></li>
			<li id="selectSupplierId" attrName="supplierId"><a
				href="javascript:;">企业</a><i></i></li>
			<li id="selectPromotion"><a href="javascript:;">筛选</a><i></i></li>
		</ul>
	</div>
	<div class="dresses"></div>
	<div class="choose">
		<div class="maytt">
			<c:if test="${catalogs!=null}">
				<ul class="fenlei">
					<c:forEach items="${catalogs}" var="first">
						<c:choose>
							<c:when test="${fn:length(catalogs)<=1}">
								<li>
									<p>
										<span categoryId="${first.id}">${first.name}</span>
									</p>
									<p class="second-nav">
										<c:forEach items="${first.children }" var="second">
											<span categoryId="${second.id}">${second.name}</span>
											<c:forEach items="${second.children }" var="third">
												<span categoryId="${third.id}">${third.name}</span>
											</c:forEach>
										</c:forEach>
									</p>
								</li>
							</c:when>
							<c:otherwise>
								<li>
									<p>
										<span categoryId="${first.id}">${first.name}</span>
										<c:if test="${fn:length(first.children)>=1}">
											<em class="down"></em>
										</c:if>
									</p>
									<p class="second-nav" style="display:none">
										<c:forEach items="${first.children }" var="second">
											<span categoryId="${second.id}">${second.name}</span>
											<c:forEach items="${second.children }" var="third">
												<span categoryId="${third.id}">${third.name}</span>
											</c:forEach>
										</c:forEach>
									</p>
								</li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</ul>
			</c:if>
		</div>
		<div class="may ser" id="brandNameContent">
			<%-- 			<c:if test="${not empty searchResponse.catalogs}">
			<ul class="filter-item J_filterContents" id="cdid">
				<c:forEach items="${searchResponse.catalogs}" var="catalogs" varStatus="statu1">
							<li catalogsId="${catalogs.id}"><a href="javascript:;">${catalogs.name}</a>
							</li>
				</c:forEach>
			</ul>
			</c:if> --%>
			<c:if test="${not empty searchResponse.matrix}">
				<c:forEach items="${searchResponse.matrix}" var="matrix"
					varStatus="statu3">
					<c:choose>
						<c:when test="${matrix.key.brandName}">
							<ul class="filter-item J_filterContents brandul" id="brandName">
								<c:forEach items="${matrix.value}" var="brandName"
									varStatus="statu4">
									<li brandNameId="${brandName.id}"><a href="javascript:;">${brandName.name}</a></li>
								</c:forEach>
							</ul>
						</c:when>
					</c:choose>
				</c:forEach>
			</c:if>
		</div>
		<div class="may ser" id="supplierIdContent">
			<c:if test="${not empty searchResponse.matrix}">
				<c:forEach items="${searchResponse.matrix}" var="matrix"
					varStatus="statu3">
					<c:choose>
						<c:when test="${matrix.key.supplierId}">
							<!--         					<div class="filter-item J_filterContents" id="cyid"> -->
							<ul class="filter-item J_filterContents" id="supplierId">
								<c:forEach items="${matrix.value}" var="supplierIdValues"
									varStatus="statu4">
									<li attrValId="${supplierIdValues.id}"><a
										href="javascript:;">${supplierIdValues.name}</a></li>
								</c:forEach>
							</ul>
						</c:when>
					</c:choose>
				</c:forEach>
			</c:if>
		</div>
			<div id="promotionContent">
			
			<!-- <ul class="filter-item J_filterp " id="sortTypeContent">
				<li id="priceUp" priceval="4"><a href="javascript:;">价格<i></i><b></b></li>
				<li id="priceDown" priceval="3"><a href="javascript:;">价格<i></i><em></em></li>
				<li id="sumaUp" priceval="8"><a href="javascript:;">销量<i></i><b></b></li>
				<li id="sumaDown" priceval="7"><a href="javascript:;">销量<i></i><em></em></li>
				<li id="priceNew" priceval="1"><a href="javascript:;">最新</li>
			</ul> -->
			<ul class="filter-item J_filterp " id="sortTypeContent">
				<li id="priceUp" priceval="4"><a href="javascript:;">价格<i></i><b></b></a></li><a href="javascript:;"></a>
				<li id="priceDown" priceval="3"><a href="javascript:;"></a><a href="javascript:;">价格<i></i><em></em></a></li><a href="javascript:;"></a>
				<li id="sumaUp" priceval="8"><a href="javascript:;"></a><a href="javascript:;">销量<i></i><b></b></a></li><a href="javascript:;"></a>
				<li id="sumaD-own" priceval="7"><a href="javascript:;"></a><a href="javascript:;">销量<i></i><em></em></a></li><a href="javascript:;"></a>
				<li id="priceNew" priceval="1"><a href="javascript:;"></a><a href="javascript:;">最新</a></li><a href="javascript:;"></a>
			</ul>
			
			<a href="javascript:;">
			<h2 class="price-title">价格</h2>
			</a>
			<c:if test="${not empty searchResponse.matrix}">
				<c:forEach items="${searchResponse.matrix}" var="matrix"
					varStatus="statu3">
					<c:choose>
						<c:when test="${matrix.key.priceRange}">
							<ul class="filter-item J_filterp priceul" id="price">
								<c:forEach items="${matrix.value}" var="priceValues"
									varStatus="statu4">
									<li priceValuesId="${priceValues.id}"><a
										href="javascript:;"> ${priceValues.name}</a></li>
								</c:forEach>
							</ul>
						</c:when>
					</c:choose>
				</c:forEach>
			</c:if>


			<div class="ser-but">
				<p>
					<a class="but-del">清除</a>
				</p>
				<p>
					<a class="but-go ser" style="background:#FF5E53">确定</a>
				<p>
			</div>
		</div>
	</div>

	<!--商品列表-->
	<div class="list">
		<div class="screen"></div>
		<ul class="scr-list">
			<c:if test="${not empty searchResponse.items}">
				<c:forEach items="${searchResponse.items}" var="item"
					varStatus="statu">
					<li><a href="<%=path%>/item/get/${item.pid}"> <c:if
								test="${item.promotion == 10}">
								<span class="act-icon act-xs">ffff</span>
							</c:if>
							<p class="picl">
								<img src="${picUrl1}${item.imageurl}" />
							</p>
							<p class="txtr">
							<span class="smidd" style="display:block;">
								<c:if test="${empty item.product_iconsTxT  }">
								<c:if test="${item.prod_type==6 }">
									<span class="yiTuBiao">千品华冠</span>
								</c:if>
								<c:if test="${item.prod_type==0 }">
								<!-- 	<span class="yiTuBiao">普</span> -->
								</c:if>
								</c:if>
								<font size=3>${item.b2cPname}</font>
							</span>
							<span class="sptop" style="display:block;"><b></b><em>${item.supplierName}</em><%-- <i>${item.supplierCode}</i> --%></span>
							    	<c:if test="${not empty item.product_iconsTxT }">
									<span class="sbtom"><b>${item.unit_price} </b></span>
									<c:forEach items="${item.product_iconsTxT}" var="itemii">
										<span class="yiTuBiao" >${itemii}</span>
									</c:forEach>
								</c:if>
								<c:if test="${empty item.product_iconsTxT  }">
									<c:if test="${item.prod_type==6 }">
										<span class="sbtom"><b>${item.unit_price}</b></span>
										<b class="spkyq">可用:${item.cash_Hqj}M券</b>
									</c:if>
									<c:if test="${item.prod_type==0 }">
										<span class="sbtom"><b>${item.unit_price}</b></span>
										<b class="spkyq">可用:${item.cash_Hqj}M券</b>
									</c:if>

								</c:if>
								<fmt:parseNumber type="NUMBER" var="price" value="${item.unit_price }" pattern=".00" />
								<c:if test="${item.domestic_price>price}"></c:if>			
							</p>
					</a></li>
				</c:forEach>
			</c:if>
		</ul>
		<div class="loadw">
			<c:choose>
				<c:when test="${totalPage gt 1}">
					<span>加载中...</span>
				</c:when>
				<c:otherwise>
					<span>已经到底啦!</span>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

	<!-- js相关内容 -->
</body>
</html>

<%-- <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<link rel="stylesheet" href="${staticFile_s}/commons/css/newbese.css">
<link rel="stylesheet" href="${staticFile_s}/commons/css/search.css">
<link rel="stylesheet" href="${staticFile_s}/commons/css/xsj.css">
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
<script src="${staticFile_s}/commons/js/zepto.min.js"></script>
<script src="${staticFile_s}/commons/js/search/classify.js"></script>
<script src="${staticFile_s}/commons/js/main.js"></script>
<script src="${staticFile_s}/commons/js/search/newsearch.js"></script>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
<script>
	$(function() {
		//			screenheight();
		tab();
		scrol();
	})
</script>
<style type="text/css">
.yiTuBiao {
	width: 20px;
	height: 20px;
	border-radius: 4px;
	background-color: #e60012;
	border: 1px solid #e60012;
	text-align: center;
	line-height: 20px;
	font-size: 1.5rem;
	color: #fff;
}
</style>
</head>
<body>

	<input id="path" type="hidden" value="<%=path%>">
	<input id="totalPage" type="hidden" value="${totalPage}">
	<!-- 		<div class="header">
			<a href="#" class="bug-go"></a>
			<a href="#" class="home-go"></a>
		</div> -->
	<header id="header" class="head">
		<a href="javascript:goBack();" class="goback"><span class="icon i-goback"></span></a>
<a href="<%=path %>/" class="gohome" id="goHomePage"><img src="${staticFile_s}/commons/images/foot_06.png" style="width:25px; height:25px;" /></a>
		<div class="header">
			<a href="javascript:goBack();" class="bug-go"></a> <a
				href="<%=path%>/" class="home-go"></a>
		</div>
		<div class="search-box">
			<input type="hidden" id="keyword" value="${searchRequest.keyword}">
			<input type="hidden" id="sortType" value="${searchRequest.sortType}">
			<input type="hidden" id="selectedBrandName"
				value="${searchRequest.brandName}"> <input type="hidden"
				id="selectedSupplierId" value="${searchRequest.supplierId}">
			<input type="hidden" id="selectedPriceRange"
				value="${searchRequest.priceRange}"> <input type="hidden"
				id="countryName" value="${searchRequest.countryName}"> <input
				type="hidden" id="selectedCdid" value="${searchRequest.cdid}">
			<input type="hidden" id="selectedPriceRange"
				value="${searchRequest.priceRange}"> <input type="hidden"
				id="selectedPromotion" value="${searchRequest.promotion}"> <input
				type="hidden" id="countryName" value="${searchRequest.countryName}">
			<input type="hidden" id="fcode" value="${searchRequest.fcode}">
	</header>
	<input type="hidden" id="b2csupply" value="${searchRequest.b2csupply}">
	</header>

	<!--分类条-->

	<div class="grabble">
		<ul>
			<li id="selectSortType"><a href="javascript:;">分类</a><i></i></li>
			<li id="selectCdid"><a href="javascript:;">品牌</a><i></i></li>
			<li id="selectSupplierId" attrName="supplierId"><a
				href="javascript:;">企业</a><i></i></li>
			<li id="selectPromotion"><a href="javascript:;">筛选</a><i></i></li>
		</ul>
	</div>
	<div class="dresses"></div>
	<div class="choose">
		<div class="maytt">
			<c:if test="${catalogs!=null}">
				<ul class="fenlei">
					<c:forEach items="${catalogs}" var="first">
						<c:choose>
							<c:when test="${fn:length(catalogs)<=1}">
								<li>
									<p>
										<span categoryId="${first.id}">${first.name}</span>
									</p>
									<p class="second-nav">
										<c:forEach items="${first.children }" var="second">
											<span categoryId="${second.id}">${second.name}</span>
											<c:forEach items="${second.children }" var="third">
												<span categoryId="${third.id}">${third.name}</span>
											</c:forEach>
										</c:forEach>
									</p>
								</li>
							</c:when>
							<c:otherwise>
								<li>
									<p>
										<span categoryId="${first.id}">${first.name}</span>
										<c:if test="${fn:length(first.children)>=1}">
											<em class="down"></em>
										</c:if>
									</p>
									<p class="second-nav" style="display:none">
										<c:forEach items="${first.children }" var="second">
											<span categoryId="${second.id}">${second.name}</span>
											<c:forEach items="${second.children }" var="third">
												<span categoryId="${third.id}">${third.name}</span>
											</c:forEach>
										</c:forEach>
									</p>
								</li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</ul>
			</c:if>
		</div>
		<div class="may ser" id="brandNameContent">
						<c:if test="${not empty searchResponse.catalogs}">
			<ul class="filter-item J_filterContents" id="cdid">
				<c:forEach items="${searchResponse.catalogs}" var="catalogs" varStatus="statu1">
							<li catalogsId="${catalogs.id}"><a href="javascript:;">${catalogs.name}</a>
							</li>
				</c:forEach>
			</ul>
			</c:if>
			<c:if test="${not empty searchResponse.matrix}">
				<c:forEach items="${searchResponse.matrix}" var="matrix"
					varStatus="statu3">
					<c:choose>
						<c:when test="${matrix.key.brandName}">
							<ul class="filter-item J_filterContents brandul" id="brandName">
								<c:forEach items="${matrix.value}" var="brandName"
									varStatus="statu4">
									<li brandNameId="${brandName.id}"><a href="javascript:;">${brandName.name}</a></li>

								</c:forEach>
							</ul>
						</c:when>
					</c:choose>
				</c:forEach>
			</c:if>
		</div>
		<div class="may ser" id="supplierIdContent">
			<c:if test="${not empty searchResponse.matrix}">
				<c:forEach items="${searchResponse.matrix}" var="matrix"
					varStatus="statu3">
					<c:choose>
						<c:when test="${matrix.key.supplierId}">
							<!--         					<div class="filter-item J_filterContents" id="cyid"> -->
							<ul class="filter-item J_filterContents" id="supplierId">
								<c:forEach items="${matrix.value}" var="supplierIdValues"
									varStatus="statu4">
									<li attrValId="${supplierIdValues.id}"><a
										href="javascript:;">${supplierIdValues.name}</a></li>
								</c:forEach>
							</ul>
							<!--         					</div> -->
						</c:when>
					</c:choose>
				</c:forEach>
			</c:if>
		</div>
		<div id="promotionContent">
			<ul class="filter-item J_filterp " id="sortTypeContent">
				<li id="priceUp" priceval="4"><a href="javascript:;">价格<i></i><b></b></li>
				<li id="priceDown" priceval="3"><a href="javascript:;">价格<i></i><em></em></li>
				<li id="sumaUp" priceval="8"><a href="javascript:;">销量<i></i><b></b></li>
				<li id="sumaDown" priceval="7"><a href="javascript:;">销量<i></i><em></em></li>
				<li id="priceNew" priceval="1"><a href="javascript:;">最新</li>
			</ul>
			<h2 class="price-title">价格</h2>
			<c:if test="${not empty searchResponse.matrix}">
				<c:forEach items="${searchResponse.matrix}" var="matrix"
					varStatus="statu3">
					<c:choose>
						<c:when test="${matrix.key.priceRange}">
							<ul class="filter-item J_filterp priceul" id="price">
								<c:forEach items="${matrix.value}" var="priceValues"
									varStatus="statu4">
									<li priceValuesId="${priceValues.id}"><a
										href="javascript:;"> ${priceValues.name}</a></li>
								</c:forEach>
							</ul>
						</c:when>
					</c:choose>
				</c:forEach>
			</c:if>

			            <c:if test="${not empty searchResponse.matrix}">
        		<c:forEach items="${searchResponse.matrix}" var="matrix" varStatus="statu3">
        			<c:choose>
        			    <c:when test="${matrix.key.b2csupply}">
           						 <ul class="filter-item J_filterp  priceul supplyul" id="b2csupply">
		        					<c:forEach items="${matrix.value}" var="supplyValues" varStatus="statu4">
		        						 <li supplyValuesId = "${supplyValues.id}" ><a href="javascript:;">${supplyValues.name}</a></li>
		        					</c:forEach>
        					 	</ul>
        				</c:when>
        			</c:choose>
        		</c:forEach>
            </c:if>

			<div class="ser-but">
				<p>
					<a class="but-del">清除</a>
				</p>
				<p>
					<a class="but-go ser">确定</a>
				<p>
			</div>

					<div id="promotionContent">
			<ul class="nosift" id="promotion">
 				<c:if test="${not empty searchResponse.matrix}">
				<li>优惠</li>
									<li>
					<c:forEach items="${searchResponse.matrix}" var="matrix"
						varStatus="statu7">
						<c:choose>
							<c:when test="${matrix.key.promotion}">
								<!-- <ul class="filter-item J_filterContents"> -->
										<c:forEach items="${matrix.value}" var="promotionValues" varStatus="statu8">
											<!-- begin -->
											<c:if
												test="${statu8.count eq 1 || (statu8.count-1) % 3 eq 0}">
											</c:if>
											<span promotionId = "${promotionValues.id}" > ${promotionValues.name}</span>
											<c:if test="${statu8.count % 3 eq 0 || statu4.count eq 3}">
												</li><li>
											</c:if>

											<!-- end -->
										</c:forEach>
								<!-- </ul> -->
							</c:when>
						</c:choose>
					</c:forEach>
						</li>
				</c:if> 
			</ul>
 			<ul class="nosift" id="price" >
				 <c:if test="${not empty searchResponse.matrix}">
				<li>价格</li>
									<li>
					<c:forEach items="${searchResponse.matrix}" var="matrix"
						varStatus="statu5">
						<c:choose>
							<c:when test="${matrix.key.priceRange}">
								<!-- <ul class="filter-item J_filterContents"> -->
										<c:forEach items="${matrix.value}" var="priceValues" varStatus="statu6">
											<!-- begin -->
											<c:if
												test="${statu6.count eq 1 || (statu6.count-1) % 3 eq 0}">
											</c:if>
											<span priceValuesId = "${priceValues.id}" > ${priceValues.name}</span>
											<c:if test="${statu6.count % 3 eq 0 || statu4.count eq 3}">
												</li><li>
											</c:if>

											<!-- end -->
										</c:forEach>
								<!-- </ul> -->
							</c:when>
						</c:choose>
					</c:forEach>
					</li>
				</c:if>
			</ul> 
			
			<p class="button">
				<input type="submit" value="清除" id="clear"><input
					type="submit" value="确定" id="sure">
			</p>  
		</div>
	</div>

	<!--商品列表-->
	<div class="list">
		<div class="screen"></div>
		<ul class="scr-list">
			<c:if test="${not empty searchResponse.items}">
				<c:forEach items="${searchResponse.items}" var="item"
					varStatus="statu">
					<li><a href="<%=path%>/item/get/${item.pid}"> <c:if
								test="${item.promotion == 10}">
								<span class="act-icon act-xs">ffff</span>
							</c:if>
							<p class="picl">
								<img src="${picUrl1}${item.imageurl}" />
							</p>
							<p class="txtr">
							
							    	<c:if test="${not empty item.product_iconsTxT }">
									<span class="sbtom"><b> </b>${item.unit_price}</span>
									<c:forEach items="${item.product_iconsTxT}" var="itemii">
										<span class="yiTuBiao" style="width:40px">${itemii}</span>
									</c:forEach>

								</c:if>
								<c:if test="${empty item.product_iconsTxT  }">
									<c:if test="${item.prod_type==6 }">
										<span class="sbtom"><b> </b> ${item.hqj}红旗券+${item.cash_Hqj}元</span>
										<span class="yiTuBiao">福</span><br>
									</c:if>
									<c:if test="${item.prod_type==5 }">
										<span class="sbtom"><b> </b>${item.unit_price}</span>
										<span class="yiTuBiao">易</span><br>
									</c:if>
									<c:if test="${item.prod_type==0 }">
										<span class="sbtom"><b> </b>${item.unit_price}</span>
									</c:if>

								</c:if>
								<span class="smidd" style="display:block;"><font size=3>${item.b2cPname}</font></span>
								<span class="sptop" style="display:block;"><b><img
										src="${picUrl1}${item.supplierLogoImgurl}"></b><em>${item.supplierName}</em><i>${item.supplierCode}</i></span>
								
								
								<fmt:parseNumber type="NUMBER" var="price"
									value="${item.unit_price }" pattern=".00" />
								<c:if test="${item.domestic_price>price}">
									<span class="sdome">市场价：<b> </b><i>${fn:escapeXml(item.domestic_price)}</i></span>
									<span class="sdome">市场价：<b> </b><i>${item.domestic_price}</i>${item.domestic_price}</span>
								</c:if>
								<c:if test="${fn:escapeXml(item.domestic_price)>fn:escapeXml(item.unit_price)}">
						   <span class="sdome">市场价：<b> </b><i>${fn:escapeXml(item.domestic_price)}</i></span>
					    </c:if>
								<span class="sbtom"><b> </b><i>${fn:escapeXml(item.unit_price)}</i></span>


							


							</p>
					</a></li>
				</c:forEach>
			</c:if>
		</ul>
		<div class="loadw">
			<c:choose>
				<c:when test="${totalPage gt 1}">
					<span>加载中...</span>
				</c:when>
				<c:otherwise>
					<span>已经到底啦!</span>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

	<!-- js相关内容 -->
</body>
</html> --%>