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
		<meta charset="utf-8">
		<title>添加收银员</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<link rel="stylesheet" href="${staticFile_s}/commons/css/mui.min.css">
		<link rel="stylesheet" href="${staticFile_s}/commons/css/add_syy.css">
		<style>
			.add_syy_ewm_img{position: fixed;margin: 0 10%;top: 18%;    z-index: 2;}
			.add_syy_ewm_text{    position: absolute;bottom: 25%;text-align: center;margin: 0 auto;width: 100%;font-size: 1rem;color: #000;font-weight: 600;}
			.add_syy_ewm_ewm{    position: fixed; margin: 0 12.5%;  top: 19%; z-index: 2;}
		</style>
	</head>
	<body>
		<header id="header" class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left" href="${path }/wealth/tosupplierShouyinList"></a>
			<h1 class="mui-title">添加收银员</h1>
		</header>
		<section class="add_syy_ewm">
			<img src="${staticFile_s}/commons/img/jx.png" width="80%" class="add_syy_ewm_img"/>
			<input id="qrUrl" type="hidden" value=""/>
			<div id="qrcode" style="position: fixed;top: 23%;z-index: 2;width: 100%;display: block;text-align: center;"></div>
			<p class="add_syy_ewm_text">扫描二维码,成为收银员</p>
		</section>

<c:set var="path" value="<%=path %>"/>
<%-- js --%>
<script type="text/javascript" src="${staticFile_s}/commons/js/zepto.min.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/user/common.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/qrcode/qrcode.js"></script>
</html>
<script type="text/javascript">
var qrcode = new QRCode(document.getElementById("qrcode"), {

});
 function getUrl(){
	var userId =${userId};
	var rcodeType =${rcodeType};
	$.ajax({
		type : "POST",
		url : "${staticFile_s}/Qr/createSelectQr",
		data : {bizid:userId,rcodeType:rcodeType},
		async : false,
		dataType : "json",
		success : function(data) {
			document.getElementById("qrUrl").value=data.rcodeImgTxt;
		}
	})
}

function makeCode () {		
	var elText = document.getElementById("qrUrl");
	if (!elText.value) {
		elText.focus();
		return;
	}
	
	qrcode.makeCode(elText.value);
}
getUrl();
makeCode();

$("#qrUrl").
	on("blur", function () {
		makeCode();
	}).
	on("keydown", function (e) {
		if (e.keyCode == 13) {
			makeCode();
		}
	});
</script>
</body>