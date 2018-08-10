<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<%@include file="/WEB-INF/views/commons/base.jsp" %>
<link href="${staticFile_s}/commons/css/base.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/user.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
 
 <script type="text/javascript">
	function downloadAndroid(){
		/* var url = "http://192.168.1.215:8089/b2client/findNewVersion?packageName=com.ccigmall.b2c.android&channelName=official";
		$.get(url,function(data){
			alert(data);
		}); */
		window.location.href = 'http://oss.aliyuncs.com/ccigmall-appversion/me19rqztwvnf.apk'; 
	
	}	
	function downloadIOS(){
		
	}	
 
 </script>
<title><spring:message code="title_client" /></title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>

<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>
<header id="header" class="head"><a href="javascript:goBack();" class="goback"><span class="icon i-goback"></span></a>
    <h2 class="h-title">客户端</h2>
    <a href="${path }/customer/toMy" class="gohome"><span class="icon i-gohome"></span></a> </header>
<div class="wrap" id="mycenter">
    <div class="mycenter-list pt10">
        <ul class="mycenter-item arrow-right">
             <li><a href="http://oss.aliyuncs.com/ccigmall-appversion/me19rqztwvnf.apk" onclick=""><span class=" "></span>ANDROID</a></li>
            <li><a href="javascript:alert('敬请期待');"  onclick=""><span class=" "></span>IOS</a></li>
       </ul>
    </div>
</div>
 <%@include file="/WEB-INF/views/commons/footer.jsp" %>
<script src="${staticFile_s}/commons/js/zepto.min.js" type="text/javascript"></script> 
<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script>
</body>
</html>