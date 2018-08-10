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
<link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/grzxcss/aui.css" />
<link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/grzxcss/grzx_pttz_css.css" />
<script type="text/javascript" src="${staticFile_s}/commons/js/main.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/common/common.js"></script>
<title><spring:message code="title_notice" /></title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>
<style>
.notice{    padding-bottom: 2rem;	width: 100%;    background-size: 100%;    overflow: hidden;    background-color: #ffffff;}
.notice h3{text-align:center;line-height:2rem}
.notice span{    display: block;    text-align: right;    padding-right: 1rem;    line-height: 2rem;color: #9a9a9a;}
.notice >p{width: 90%;    margin: 0 auto;   border: 1px dashed #d4d4d4;  text-indent: 1rem; padding: 0.3rem;  line-height: 2rem;margin-top: 1rem;}
</style>
<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>	
    <header class="aui-bar aui-bar-nav">
        <a class="aui-pull-left aui-btn"  href="javascript:;" onclick="goBack();">
            <span class="aui-iconfont aui-icon-left"></span>
        </a>
        <div class="aui-title">平台通知</div>
</header>
	<div class="wrap" id="myinfo">
		<div class="notice">
		    <h3>${notice.title }</h3>
		    <span><fmt:formatDate value="${notice.createTime }" pattern="yyyy/MM/dd HH:mm"/></span>
			<p>${notice.content}</p>
		</div>
	</div>
</body>
</html>