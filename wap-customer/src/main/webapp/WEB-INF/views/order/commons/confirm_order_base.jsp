<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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

<!-- css -->
<link href="${staticFile_s}/commons/css/newbese.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/order.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/user.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/my.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/swiper.min.css" rel="stylesheet" type="text/css">
<link rel="shortcut icon" type="image/x-icon" href="${staticFile_s}/commons/img/favicon.ico" />
<c:set var="path" value="<%=path %>"/>

<!-- js -->
<script type="text/javascript" src="${staticFile_s}/commons/js/zepto.min.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/main.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/user/common.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/common/common.ajax.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/common/common.window.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/address/address.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/order/order.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/order/coupons.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/swiper.min.js"></script>


