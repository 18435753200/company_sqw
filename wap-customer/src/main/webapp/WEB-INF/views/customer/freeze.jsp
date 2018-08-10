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
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<title>用户冻结</title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>
<style>
.err-wrap {width: 100%;z-index: 9; text-align: center;font-size: 0;position: absolute;top:35%;left:0;}
.err-wrap .icon { width: 5rem;height: 5rem;margin-bottom: 0.3rem;}
.err-wrap .tips {font-size: 1.5rem;font-weight: bold;margin-top: 1rem;}
</style>
<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>	
	<div class="headly">
		<a href="javascript:;" onclick="goBack();" class="goback"><span><b></b><i></i></span></a>
	    <h3>登录错误</h3>
    </div>
	<div class="wrap err-wrap">
		<img class="icon" src="${path }/commons/img/fobdnicn.png"/>
        <div class="tips">非常抱歉！</div>
        <div class="tips">您的账户存在严重违规情况，已做“冻结”帐号处理</div>
	</div>
</body>
<script>
</script>
</html>