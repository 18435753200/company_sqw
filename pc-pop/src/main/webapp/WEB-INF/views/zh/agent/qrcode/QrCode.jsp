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

<c:set var="path" value="<%=path %>"/>

<script type="text/javascript" src="${path}/js/user/jquery-1.11。1.min.js"></script>
<script type="text/javascript" src="${path }/js/agent/qrcode/qrcode.js"></script>
</head>
<body style="background-color: #232323">

<input id="qrUrl" type="hidden" value="${code}" style="width:80%" /><br />
<div style="color: #fff;text-align: center;line-height: 1.6; font-size: 20px">个人二维码</div>
<div id="qrcode" style="margin:15px auto;border: 1px solid #E2E2E2;width:276px;">
</div>
<script type="text/javascript">
var qrcode = new QRCode(document.getElementById("qrcode"), {
	width : 275,
	height : 275
	
});

/*  function getUrl(){
	var userId =${userId};
	var sId=${sId};
	$.ajax({
		type : "POST",
		url : "${staticFile_s}/QrCodeContorller/createSelectQr",
		data : {bizid:userId},
		async : false,
		dataType : JSON,
		success : function(data) {
			document.getElementById("qrUrl").value=data.rcodeImgTxt;
		}
	})
} */



function makeCode () {	
	var elText = document.getElementById("qrUrl");
	if (!elText.value) {
		elText.focus();
		return;
	}
	
	qrcode.makeCode(elText.value);
}
makeCode();

$("#`").
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