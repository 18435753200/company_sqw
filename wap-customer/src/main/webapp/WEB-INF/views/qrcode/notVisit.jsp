<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	request.setAttribute("path", path);
	request.setAttribute("basePath", basePath);
	%>
<html>
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-status-bar-style" content=" black ">
<meta name="format-detection" content="telephone=no">
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<head>
<style>
body {
	background: url(../commons/images/bg.jpg) repeat;
	background-size: 100%
}

.ts {
	width: 85%;
	margin: 0 5%;
	padding: 0 2%;
	text-align: center;
	font-size: 1.2rem;
	position: fixed;
	top: 30%;
	color: #fff;
	line-height: 10rem;
}

.jh_bot_lg {
	text-align: center;
	position: fixed;
	bottom: 5%;
}
</style>
<title></title>
</head>
<body>
	<div class="ts">${message}</div>
	<div class="jh_bot_lg">
		<img src="../commons/images/zjm_lg.png" style="width: 60%" />
	</div>
</body>
<script language="javascript">
        //防止页面后退
        history.pushState(null, null, document.URL);
        window.addEventListener('popstate', function () {
            history.pushState(null, null, document.URL);
        });
    </script>
</html>