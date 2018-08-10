<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<head>
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-status-bar-style" content=" black ">
<meta name="format-detection" content="telephone=no">
<meta charset="UTF-8">
<title>商家入驻</title>
<script src="${staticFile_s}/commons/js/jquery-1.8.0.min.js"></script>
<meta name="apple-mobile-web-app-title" content="众聚猫">
<meta name="format-detection" content="telephone=no">
<%@include file="/WEB-INF/views/commons/user.jsp"%>
<script src="${staticFile_s}/commons/js/user/des.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<script src="${staticFile_s}/commons/js/rem.js"></script>
<link href="${staticFile_s}/commons/css/login.css" rel="stylesheet"
	type="text/css">
<link href="${staticFile_s}/commons/css/zepto.alert.css"
	rel="stylesheet" type="text/css">
<link rel="stylesheet" href="${staticFile_s}/commons/css/public.css"
	type="text/css">
<link rel="stylesheet" href="${staticFile_s}/commons/css/sjrz.css"
	type="text/css">
<style>
body {
	background: url(../commons/images/bg.jpg) repeat;
	background-size: 100%
}

.header {
	background: rgb(255, 255, 255, 0);
}

.hint {
	font-size: 0.8rem;
	width: 70%;
	margin: auto;
	margin-top: 5rem;
	padding: 0.75rem;
	color: #fff;
}

.jh_bot_lg {
	text-align: center;
	position: fixed;
	top: 75%;
	text-align: center;
}

.content .btn a {
	display: inline-block;
	height: 1.75rem;
	width: 86.6%;
	background: #ffffff;
	line-height: 1.75rem;
	color: #ff0303;
	border-radius: 2rem;
	font-size: .7rem;
	margin-bottom: .9rem;
}

.content {
	width: 100%;
	position: relative;
	bottom: 8rem;
}
</style>
</head>
<body>
<!-- 	<header class="header">
		 <a class="go_back" href="#"onclick="javascript:history.back(-1);"></a>
		<div class="title">入驻</div>
	</header> -->
	<div class="jh_bot_lg">
		<img src="../commons/images/zjm_lg.png" style="width: 60%" />
	</div>
	<div class="content">
		<div class="btn">
			<c:choose>
				<c:when test="${!empty message}">
					<div class="hint">${message}</div>
					<a href="#" style="background-color: #989898;color: #fff;">无法入驻</a>
				</c:when>
				<c:otherwise>
					<div class="hint" id="success"></div>
					<a href="#" onclick="determine()">确认入驻</a>
				</c:otherwise>
			</c:choose>
			<form id="form" action="${staticFile_s}/mallRegister/toRegister"
				method="post">
				<input type="hidden" id="rcid" name="rcid" value="${rcode.rcodeid}" />
				<input type="hidden" id="rcodetxt" name="rcodetxt"
					value="${rcode.rcodetxt}" />
			</form>
		</div>
	</div>
</body>
<script type="text/javascript">
	var bizid = "${rcode.bizid}";
	function determine() {
		if (rcodetxt == null || rcodetxt == '') {
			showError("该二维码尚未激活");
			return false;
		}
		if (bizid != null || bizid != '') {
			$.ajax({
				type : "POST",
				url : "${staticFile_s}/mallRegister/selectSupplier",
				success : function(e) {
					if (e == "false") {
						showError("您已经成为众聚猫合作商家");
						return false;
					} else {
						$("#form").submit();
						//window.location.href="${staticFile_s}/mallRegister/toRegister?rcid="+rcid+"&rcodetxt="+rcodetxt;
					}
				}
			})
		} else {
			showError("该二维码已有商户入驻");
			return false;
		}
	}
</script>
<script language="javascript">
	//防止页面后退
	history.pushState(null, null, document.URL);
	window.addEventListener('popstate', function() {
		history.pushState(null, null, document.URL);
	});
</script>
</html>
