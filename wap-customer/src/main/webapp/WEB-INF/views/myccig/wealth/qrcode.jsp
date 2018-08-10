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
<link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/grzxcss/aui.css" />
<link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/grzxcss/grzx_fxtj_css.css" />
<script type="text/javascript" src="${staticFile_s}/commons/js/qrcode/qrcode.js"></script> 
<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script>
<title><spring:message code="title_wealth" /></title>
</head>
<body>
   <header class="aui-bar aui-bar-nav">
        <a class="aui-pull-left aui-btn" href="javascript:;" onclick="goBack();">
            <span class="aui-iconfont aui-icon-left"></span>
        </a>
        <div class="aui-title">分享推荐</div>
</header>
<section class="aui-content">
	<div class="fxtjewm"><input id="qrUrl" type="hidden"><br>
		<div id="qrcode" >
   		</div></div>
</section>
<div class="aui-btn aui-btn-danger aui-btn-block">分享</div> 
	
<script type="text/javascript">
	var qrcode = new QRCode(document.getElementById("qrcode"), {
	
	});
	
	 function getUrl(){
		 var userId =${userId};
		var rcodeType =${rcodeType};
		$.ajax({
			type : "POST",
			url : "${staticFile_s}/QrCodeMaContorller/createSelectQr",
			data : {bizid:userId,rcodeType:rcodeType},
			async : false,
			dataType : "json",
			success : function(data) {
				document.getElementById("qrUrl").value=data.rcodeImgTxt;
			}
		});
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
</html>