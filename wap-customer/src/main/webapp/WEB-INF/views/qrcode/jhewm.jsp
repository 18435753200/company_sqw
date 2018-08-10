<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<head>
	<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no">
    <meta name="apple-mobile-web-app-status-bar-style" content=" black ">
    <meta name="format-detection" content="telephone=no">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>商家激活</title>
    <script src="${staticFile_s}/commons/js/jquery-1.8.0.min.js"></script>
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
    <link rel="stylesheet" href="${staticFile_s}/commons/css/public.css"type="text/css">
    <link rel="stylesheet" href="${staticFile_s}/commons/css/sjrz.css"type="text/css">
    <script src="${staticFile_s}/commons/js/rem.js"></script>
    <style>
body{background:url(../commons/images/bg.jpg) repeat ;background-size: 100%}
.header{background:rgb(255,255,255,0);}
    .hint{font-size:0.8rem;width:70%;
    margin:auto;margin-top:5rem;padding:0.75rem;color:#fff;}
    
    .jh_bot_lg{
position: fixed;
    top: 75%;
    width: 100%;
    margin: 0 auto;
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
<header class="header">
   <!--  <a class="go_back" href="#"onclick="javascript:history.back(-1);"></a> -->
    <div class="title">激活</div>
</header>
	<div class="jh_bot_lg"><img src="../commons/images/zjm_lg.png" style="width:60%"/></div>
<div class="content">
	 <div class="btn">
	<c:choose>
		<c:when test="${!empty message}">
			 <div class="hint">${message}</div>
	  		<a href="#"  style="background-color: #989898;color: #fff;" >无法激活</a>
		</c:when>
			<c:otherwise>
			<c:choose>
				<c:when test="${!empty success}">
					<div class="hint" id="success">${success}</div>
				</c:when>
				<c:otherwise>
					<a href="#" onclick="determine()" >确认激活</a>
				</c:otherwise>
			</c:choose>
			</c:otherwise>
	</c:choose>
 </div>       
</div>
</body>
<script type="text/javascript">
	var rcid="${rcode.rcodeid}";
	var rcodetxt="${rcode.rcodetxt}";
	var bizid ="${rcode.bizid}";
	function determine(){
			if(rcodetxt!=null||rcodetxt!=''){
				 $.ajax({
					type:"POST",
					data:{rcid:rcid},
					url:"${staticFile_s}/mallRegister/activation",
					async : false,
					success : function(e) {
						window.location.href="${staticFile_s}/mallRegister/toActivation"
					}
				}) 
			}else{
				showError("该二维码已被激活");
			}
	}
</script>
<script language="javascript">
        //防止页面后退
        history.pushState(null, null, document.URL);
        window.addEventListener('popstate', function () {
            history.pushState(null, null, document.URL);
        });
    </script>
</html>

