<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<%@include file="/WEB-INF/views/commons/base.jsp" %>
<link href="${staticFile_s}/commons/css/w_base.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/my.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/user/common.js"></script>
<title><spring:message code="title_cusinfo_cusinfo" /></title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
<style type="text/css">
h3{
	background-color: #e60012;
	color: #ffffff;
}
</style>
</head>

<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>	
	<div class="headly">
		<span onclick="goBack()"><b></b><i></i></span>
	    <h3>修改身份证<em class="rd" onclick="checkIdCard()">确定</em></h3>
	   
    </div>
	<div class="wrap">
	    <ul class="form-list">
	        <li>
	           
	            <div class="field">
	                <input type="text" class="w"  id="_idCard" value="${fn:escapeXml(email)}" placeholder="请输入身份证号"/>
	            </div>
	        </li>
       </ul>
	</div>
<%@include file="/WEB-INF/views/commons/footer.jsp" %>
<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script>
<script src="${staticFile_s}/commons/js/cciginfo/info.js" type="text/javascript"></script>
</body>
</html>	