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
<head>
<%-- css --%>
<link href="${staticFile_s}/commons/css/newbese.css" rel="stylesheet" type="text/css">
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
<link rel="shortcut icon" type="image/x-icon" href="${staticFile_s}/commons/img/favicon.ico" />

<c:set var="path" value="<%=path %>"/>
<%-- js --%>
<script type="text/javascript" src="${staticFile_s}/commons/js/zepto.min.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/user/common.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/qrcode/qrcode.js"></script>
</head>
<body>

<input id="qrUrl" type="hidden" value="" style="width:80%" /><br />

<div id="qrcode" style="width:30%; height:30%; margin-top:15px;	text-align : center">
</div>
<script type="text/javascript">
var qrcode = new QRCode(document.getElementById("qrcode"), {

});

 function getUrl(){
	var userId =${userId};
	var sId=${sId};
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