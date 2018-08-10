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
<link href="${staticFile_s}/commons/css/base.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/user.css" rel="stylesheet" type="text/css">

<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<title><spring:message code="title_comment" /></title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>

<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>
<header id="header" class="head"><a href="javascript:goBack();" class="goback"><span class="icon i-goback"></span></a>
    <h2 class="h-title">评价</h2>
</header>
<div class="wrap" id="comment">

<c:forEach var="item" items="${dtos }">
    <div class="order-item">
        <div class="order-bd">
            <div class="goods-pic"><a href="${path }/item/get/${item.pid}" class="link"><img src="${picUrl2 }${item.imgUrl}" /></a></div>
            <div class="goods-info">
                <p class="name double-row">${item.pName }</p>
                <p class="sku"><span> 购买时间： 
                <fmt:formatDate value="${item.createTime}" type="time" timeStyle="full" pattern="yyyy-MM-dd HH:mm"/>
                </span>
                </p>
                 <p class="sku">
                <span>购买数量：${item.skuQty }(${item.unit })</span>
                </p>
                <c:if test="${item.createBy == 'comment' }">已评论</c:if>
                <c:if test="${item.createBy != 'comment' }"><a href="${path }/cusComment/toCusComment?id=${fn:escapeXml(item.id)}&orderId=${fn:escapeXml(item.orderId)}"  class="order-btn cancel-btn">评价</a></c:if>
                 
                </div>
        </div>
    </div>
    </c:forEach>

</div>
<%@include file="/WEB-INF/views/commons/footer.jsp" %>
<script src="${staticFile_s}/commons/js/zepto.min.js" type="text/javascript"></script> 
<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script>
</body>
</html>