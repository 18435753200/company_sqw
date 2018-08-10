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
<!doctype html>
<html>

	<head>
		<meta charset="utf-8">
		<title>填写收银信息</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<link rel="stylesheet" href="${staticFile_s}/commons/css/mui.min.css">
		<link rel="stylesheet" href="${staticFile_s}/commons/css/add_syy.css">
		<script type="text/javascript" src="${staticFile_s}/commons/js/jquery-1.8.0.min.js"></script>
		<style>
			.mui-bar-nav~.mui-content {
    padding-top: 50px;
}
		</style>
	</head>
	<body>
		<header id="header" class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">填写收银信息</h1>
		</header>
		<div class="mui-content">
			<form class="mui-input-group"   id="add">
					<div class="mui-input-row">
						<label>姓名</label>
						<input type="hidden" name="supplierId" value="${supplierId}">
						<input type="text" name="kfName" id="kfName" maxlength="5"  placeholder="请输入您的姓名">
					</div>
					<div class="mui-input-row">
						<label>手机号</label>
						<input type="tel" class="mui-input-clear" maxlength="11" name="mobile" id="mobile" placeholder="请输入您的手机号" data-input-clear="5"><span class="mui-icon mui-icon-clear mui-hidden"></span>
					</div>

					<div class="mui-input-row mui-plus-visible">
						<label>Input</label>
						<input type="text" class="mui-input-speech mui-input-clear" placeholder="语音输入" data-input-clear="6" data-input-speech="6"><span class="mui-icon mui-icon-clear mui-hidden"></span><span class="mui-icon mui-icon-speech"></span>
					</div>
					<div class="mui-button-row">
						<button class="mui-btn mui-btn-primary" type="button" onclick="return qr()" >确认</button>&nbsp;&nbsp;
					</div>
				</form>
	
		</div>

<input type="hidden" value="<%=path%>" id="path">
<%@include file="/WEB-INF/views/commons/footer.jsp" %>
	</body>
</html>
<script type="text/javascript">
	function qr(){
		var kfName = $("#kfName").val();
		var mobile = $("#mobile").val();
		if(kfName==null || mobile==null || kfName=="" || mobile==""){
			alert("姓名手机号不能为空");
			return false;
		}else{
			$("#add").attr("action","${path}/wealth/supplierAddShouyin");
			$("#add").submit();  
			
		}
	}

	function showMsg(content){
		$.dialog({
	        content : content,
	        title : '众聚猫提示',
	        time: 2000,
		});
	}
</script>
