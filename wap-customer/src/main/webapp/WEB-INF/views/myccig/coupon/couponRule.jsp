<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<%@include file="/WEB-INF/views/commons/base.jsp" %>
<link href="${staticFile_s}/commons/css/my.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<title><spring:message code="title_coupons_explain" /></title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>

<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>	
	<div class="headly">
		<a href="javascript:goBack();" class="goback"><span><b></b><i></i></span></a>
		<h3>优惠券使用说明</h3>
	</div>
	<div class="explain" id="couponRule">
	    <p class="fore"><em>一.</em><span>全场使用：不设置购买商品的范围，只要页面上有货的都可以使用</span></p>
	    <p><em>1.</em><span>限制金额：指定时间范围内，在本网站买任何商品都可以，只要购物满一定金额就可使用；</span></p>
	    <p><em>2.</em><span>无任何限制：指定时间范围内，只要购物就可以使用，不管购买金额是多少，不管购买什么商品。</span></p>
	    <p class="fore"><em>二.</em><span>品牌：购买指定单一品牌，或者指定多个品牌的时候可以使用</span></p>
	    <p><em>1.</em><span>指定时间范围内，购买指定单一品牌，或者指定多个品牌满足一定金额时，可使用；</span></p>
	    <p><em>2.</em><span>指定时间范围内，只要购买指定单一品牌，或者指定多个品牌，不设置任何金额限时，可使用。</span></p>
	    <p class="fore"><em>三.</em><span>品类：购买指定类型商品，可以使用</span></p>
	    <p><em>1.</em><span>指定时间范围内，购买指定单一品类，或者指定多个品类满足一定金额时，可使用；</span></p>
	    <p><em>2.</em><span>指定时间范围内，只要购买指定单一品类，或者指定多个品类，不设置任何金额限时，可使用。</span></p>
	    <p class="fore"><em>四.</em><span>指定单一商品：指定时间范围内，购买指定一款商品，买购一定金额或者不设置金额可以使用。</span></p>
	</div>
</body>
</html>