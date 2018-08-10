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
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
		<%@include file="/WEB-INF/views/commons/user.jsp" %>
		<script src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"  type="text/javascript" ></script>
		<script src="${staticFile_s}/commons/js/userActivity/flowRate.js" type="text/javascript"></script>
<style>
*{ margin:0; padding:0; list-style:none;}
body{ background:#bb1a2a; font-family:'Microsoft Yahei';}
img{ width:100%;}
html{ font-size:20px;}
.clearfix:after{content:" "; display:block; clear:both;}
.clearfix {zoom:1;}

.cent{width:100%; background:url(../commons/img/userActivity/flowRate/bg.jpg) no-repeat; background-size:100%; height:28.4809rem; position:relative; overflow:hidden;}
.iph{ position:absolute; top:19.62rem; left:2.562rem; width:10.8885rem; height:1.6653rem; border:1px solid #a59899; border-radius:0.4rem; color:#b5b5b5; text-indent:0.4rem; font-size:0.6rem;}
.sbt{ position:absolute; top:22.524rem; left:5.017rem; width:5.978rem; height:2.135rem; background:url(../commons/img/userActivity/flowRate/ensure.png) no-repeat; background-size:100%; border:0;}
</style>

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
	<input type="tel" class="iph" placeholder="请输入手机号领取牛币" id="mobile" name="mobile">
    <input type="submit" class="sbt" value="" onclick="submitFriend()">
</div>
</body>
</html>








