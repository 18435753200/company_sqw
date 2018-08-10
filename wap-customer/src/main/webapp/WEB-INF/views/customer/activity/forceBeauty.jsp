<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>

<html>
<head>
<meta charset="utf-8">
<title>众聚猫</title>
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<%@include file="/WEB-INF/views/commons/user.jsp" %>
        <link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
        <link href="${staticFile_s}/commons/css/userActivity/forceBeauty.css" rel="stylesheet" type="text/css">
        <script src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"  type="text/javascript" ></script>
        <script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script>
        <script src="${staticFile_s}/commons/js/userActivity/forceBeauty.js" type="text/javascript"></script>
<script>
window.onload=window.onresize=function(){
	document.documentElement.style.fontSize=document.documentElement.clientWidth/16+'px';	
};
document.addEventListener('DOMContentLoaded',function(){
	document.documentElement.style.fontSize=document.documentElement.clientWidth/16+'px';
});
</script>
</head>

<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<div class="limei">
    <img src="${staticFile_s }/commons/img/userActivity/forceBeauty/bj.jpg">
    <input type="tel" class="iph" placeholder="请输入手机号" id="mobile" name="mobile">
    <input type="submit" class="sbt" value="" onclick="submitForceBeauty()">
</div>

</body>
</html>



