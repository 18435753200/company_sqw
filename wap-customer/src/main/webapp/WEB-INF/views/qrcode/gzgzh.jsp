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
<!doctype html>
<html>

<head>
<meta charset="utf-8">
<title>关注公众号</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<link rel="stylesheet" href="${staticFile_s}/commons/css/mui.min.css">
<link rel="stylesheet" href="${staticFile_s}/commons/css/add_syy.css">
<style>
.add_syy_ewm_img {
	position: fixed;
	margin: 0 10%;
	top: 18%;
	z-index: 2;
}

.add_syy_ewm_text {
	position: fixed;
	bottom: 25%;
	text-align: center;
	margin: 0 auto;
	width: 100%;
	font-size: 1rem;
	color: #000;
	font-weight: 600;
}

.add_syy_ewm_ewm {
	position: fixed;
	margin: 0 12.5%;
	top: 19.5%;
	z-index: 2;
}
.cg_sy{   
	 position: fixed;
    z-index: 10;
    width: 100%;
    text-align: center;
    top: 10%;}
</style>
</head>
<body>
		<header id="header" class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">关注公众号</h1>
		</header>
		<div class="cg_sy">恭喜您成为收银员</div>
		<section class="add_syy_ewm">
			<img src="${staticFile_s}/commons/img/jx.png" width="80%" class="add_syy_ewm_img"/>
			<img src="${staticFile_s}/commons/img/gzhewm.jpg" width="75%" class="add_syy_ewm_ewm"/>
			<p class="add_syy_ewm_text">长按识别二维码,关注众聚猫公众号</p>
		</section>
<input type="hidden" value="<%=path%>" id="path">
<%@include file="/WEB-INF/views/commons/footer.jsp" %>
	</body>
</html>