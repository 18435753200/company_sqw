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
<%-- zepto alert --%>
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/grzxcss/aui.css" />
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/suggest/suggest.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/main.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/common/common.js"></script>
<title><spring:message code="title_notice" /></title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>
<style>
.notice{
	width: 100%;
    background-size: 100%;
    overflow: hidden;
    background-color: #ffffff;
}
.notice >p{
	width:90%;
	height:100%;
	margin:5% auto;
	text-indent:2em;
}
</style>
<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>	
	<!-- <div class="headly">
<a href="javascript:;" onclick="goBack();" class="goback"><span><b></b><i></i></span></a>
	    <h3>平台反馈</h3>
    </div> -->
    <header class="aui-bar aui-bar-nav">
        <a class="aui-pull-left aui-btn" href="javascript:;"  onclick="goBack();">
            <span class="aui-iconfont aui-icon-left"></span>
        </a>
        <div class="aui-title">意见反馈</div>
</header>
	<div class="wrap" id="myinfo">
		<div class="notice">
		  <div style="background-color: #f5f5f5;;width:100%;height:50px;">
		   <p style="width:50%; height:30px; line-height:50px;text-align:center;font-size:16px; float:left;">反馈结果</p>
		    <p style="width:50%; height:30px; line-height:50px;text-align:CENTER;float:right;">
		
		    <fmt:formatDate value="${platformComplaint.replyTime }" pattern="yyyy/MM/dd HH:mm"/>
		    
		    </p>
		      </div>
			<p style="word-wrap:break-word ">${platformComplaint.replyContent}</p>
		</div>
		 
	</div>
</body>

</html>