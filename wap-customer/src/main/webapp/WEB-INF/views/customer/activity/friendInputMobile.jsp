<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>

<html>
<head>
<meta charset="utf-8">
<title>众聚猫</title>
<meta name="viewport" content="width=320, user-scalable=no, initial-scale=1">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
	<%@include file="/WEB-INF/views/commons/user.jsp" %>
        <link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
        <link href="${staticFile_s}/commons/css/userActivity/friend.css" rel="stylesheet" type="text/css">
        <script src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"  type="text/javascript" ></script>

<script>
;(function(win,doc){
	function change(){
		document.documentElement.style.fontSize=document.documentElement.clientWidth/16+'px';
	}
	
	win.addEventListener('resize',change,false);
	win.addEventListener('DOMContentLoaded',change,false);
})(window,document);

</script>
</head>

<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<div class="cent">
    <input name="mobile" id="mobile" type="tel" placeholder="输入手机号领取可乐" class="iph">
	<input name="" type="submit" value="" class="sbt" onclick="submitFriend()">
</div>
	<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script>
		<script src="${staticFile_s}/commons/js/userActivity/friend.js" type="text/javascript"></script>
		<script src="${staticFile_s}/commons/js/user/des.js" type="text/javascript"></script>
		<script src="${staticFile_s}/commons/js/zepto.min.js" type="text/javascript"></script>
</body>
</html>

















