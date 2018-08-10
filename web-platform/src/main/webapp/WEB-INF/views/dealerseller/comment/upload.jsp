<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>导入评论</title>
<%@include file="/WEB-INF/views/include/base.jsp"%>
<link href="${path}/commons/css/comments.css" rel="stylesheet" type="text/css">

</head>
<body>
<%@include file="/WEB-INF/views/include/header.jsp"%>
<div class="center">
	<form action="${path }/comment/upload" method="post" enctype="multipart/form-data">
	
	excel文件：<input name="excelFile" type="file">
	<input value="导入" type="submit">
	</form>
</div>
<!-- 底部 start -->
<%@include file="/WEB-INF/views/include/foot.jsp"%>
<!-- 底部 end -->
</body>
</html>