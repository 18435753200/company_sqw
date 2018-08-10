<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>评价管理</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link href="${path}/commons/css/reset.css" rel="stylesheet" type="text/css">
<link href="${path}/commons/css/comments.css" rel="stylesheet" type="text/css">

</head>
<body>
<%@include file="/WEB-INF/views/include/header.jsp"%>
<div class="center">
	<c:if test="${empty mobiles }">
		导入成功
	</c:if>
	<c:if test="${not empty mobiles }">
		导入失败的工作表和行号是： ${mobiles }
	</c:if>
</div>
<!-- 底部 start -->
<%@include file="/WEB-INF/views/include/foot.jsp"%>
<!-- 底部 end -->
</body>
</html>