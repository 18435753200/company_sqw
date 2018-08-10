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

<c:set var="path" value="<%=path %>"></c:set>
<title><spring:message code="title_cusinfo_index" /></title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>

<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>
<header id="header" class="head"><a href="javascript:goBack();" class="goback"><span class="icon i-goback"></span></a>
    <h2 class="h-title">个人设置</h2>
    <a href="${path }/customer/toMy" class="gohome"><span class="icon i-gohome"></span></a> </header>
<div class="wrap" id="mycenter">
    <div class="mycenter-list pt10">
        <ul class="mycenter-item arrow-right">
             <li><a href="${path }/cusInfo/cusInfo"><span class="myicon i-man"></span>我的账户</a></li>
            <li><a href="${path }/customer/toGetpass?from=cus" class="forgot-password"><span class="myicon i-account"></span>修改密码</a></li>
            <li><a href="${path }/cusInfo/toVerify" class="forgot-password"><span class="myicon i-man"></span>实名认证</a></li>
       </ul>
    </div>
</div>
 <%@include file="/WEB-INF/views/commons/footer.jsp" %>
<script src="${staticFile_s}/commons/js/zepto.min.js" type="text/javascript"></script> 
<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script>
</body>
</html>