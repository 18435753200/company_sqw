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
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<link rel="stylesheet" href="${staticFile_s}/commons/css/mui.min.css">
<script type="text/javascript" src="${staticFile_s}/commons/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert2.js"></script> 

<title>修改收银员信息</title>
</head>
<body>
<input type="hidden" id="path" value="<%=path %>"/>
		<header id="header" class="mui-bar mui-bar-nav" style="background-color: #e60012";>
		<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left" href="${path}/wealth/tosupplierShouyinList" style="color: #fff";></a>
			<h1 class="mui-title" style="color: #fff";>修改收银员信息</h1>
		<button class="mui-btn mui-btn-blue mui-btn-link mui-pull-right" onclick="qd()" style="color: #fff";>确定<tton>
		</header>
		<form style="position: fixed;top: 2.5rem;">
		<div class="mui-card">
			<div class="mui-card-content">
				<div class="mui-card-content-inner">
					<input type="hidden" name="id" id="id" value="${findCUserShouyinById.id }">
					<input type="text" name="kfName" maxlength="5" id="kfName" value="${findCUserShouyinById.kfName }">
					<input type="tel" name="mobile" maxlength="11" id="mobile" value="${findCUserShouyinById.mobile }">
				</div>
			</div>
		</div>
		</form>
		
<%@include file="/WEB-INF/views/commons/footer.jsp" %>
</body>


<script type="text/javascript">
function qd(){
	var id= $("#id").val();
	var kfName= $("#kfName").val();
	var mobile= $("#mobile").val();
	if(kfName==null || mobile==null || kfName=="" || mobile==""){
		alert("请输入姓名或手机号");
		return;
	}
	$.ajax({
		type: 'POST',
		url: $("#path").val()+'/wealth/updateSyInfo',
		dataType: 'json',
		async:false,
		data:"id="+ id+ "&kfName="+ kfName+ "&mobile="+ mobile,
		success: function(data) {
			if(data==1){
				window.location = "${path}/wealth/tosupplierShouyinList";
			}else {
				alert("修改失败");
			}
			},
		error: function() {
		}
	});
	
}
function showMsg(content){
	$.dialog({
        content : content,
        title : '众聚猫提示',
        time: 2000,
	});
}

</script>
</html>