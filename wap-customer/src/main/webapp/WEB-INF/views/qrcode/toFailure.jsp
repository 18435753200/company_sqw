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
<html>
<head>
<style>
	body{background:url(../commons/images/bg.jpg) repeat ;background-size: 100%}
	.ts{
    width: 85%;
    margin: 0 5%;
	padding: 0 2%;
    text-align: center;
    font-size: 3rem;
    position: fixed;
    top: 30%;
    color: #fff;
    line-height: 10rem;}
        .jh_bot_lg{
position: fixed;
    top: 65%;
    width: 100%;
    margin: 0 auto;
    text-align: center;
}
</style>
<title>系统繁忙</title>
</head>
<body>
<div class="jh_bot_lg"><img src="../commons/images/zjm_lg.png" style="width:60%"/></div>
		<div class="ts">${message}</div>
</body>
<script language="javascript">
    //防止页面后退
    history.pushState(null, null, document.URL);
    window.addEventListener('popstate', function () {
        history.pushState(null, null, document.URL);
    });
</script>
</html>