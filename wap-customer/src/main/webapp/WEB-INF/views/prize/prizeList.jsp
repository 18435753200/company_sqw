<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <link rel="shortcut icon" type="image/x-icon" href="${path}/commons/img/favicon.ico" />
    <title>获奖记录</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <link rel="stylesheet" href="${path}/commons/css/newbase.css">
    <link rel="stylesheet" href="${path}/commons/css/draw.css">
    <link rel="stylesheet" type="text/css" href="${path}/commons/css/zepto.alert.css">
</head>
<body>
<input id="path" type="hidden" value="<%=path%>">
<div class="header">
    <a href="javascript:history.go(-1)" class="bug-go"></a>
    获奖记录
</div>
<ul class="draw-list" id="prList">

</ul>
<!-- js相关内容 -->
<script src="${path}/commons/js/zepto.min.js"></script>
<script src="${path}/commons/js/main.js"></script>
<script type="text/javascript" src="${path}/commons/js/common/common.js"></script>
<script type="text/javascript" src="${path}/commons/js/jqueryAlert/zepto.alert.js"></script>
<script src="${path}/commons/js/prize/prizeList.js"></script>
</body>
</html>