<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>请输入手机号码重置</title>
    <meta name="keywords" content="这里是关键词">
    <meta name="description" content="这里是描述">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no">
    <meta name="apple-mobile-web-app-status-bar-style" content=" black ">
    <meta name="apple-mobile-web-app-title" content="众聚猫">
    <meta name="format-detection" content="telephone=no">
    <!-- 是用于设定禁止浏览器从本地机的缓存中调阅页面内容，设定后一旦离开网页就无法从Cache中再调出 -->
    <meta http-equiv="Pragma"  content="no-cache">
    
    <%@include file="/WEB-INF/views/commons/user.jsp" %>
    <link href="${staticFile_s}/commons/css/newbese.css" rel="stylesheet" type="text/css">
    <link href="${staticFile_s}/commons/css/login.css" rel="stylesheet" type="text/css">
    <link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
   <%--  <link rel="shortcut icon" type="image/x-icon" href="${staticFile_s}/commons/img/favicon.ico" /> --%>
    <script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
    <script src="${staticFile_s}/commons/js/user/getPass.js?v=0.1.7" type="text/javascript"></script>
    <%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
    <%@include file="/WEB-INF/views/commons/commonsIco.jsp" %>
    
    <%
    //服务器设置jsp禁用缓存
    response.setHeader( "Pragma", "no-cache" );
    response.addHeader( "Cache-Control", "must-revalidate" );
    response.addHeader( "Cache-Control", "no-cache" );
    response.addHeader( "Cache-Control", "no-store" );
    response.setDateHeader("Expires", 0);
    //禁止Chrome浏览器缓存
    response.addHeader("Cache-Control", "no-store, must-revalidate");   
    response.addHeader("Expires", "Thu, 01 Jan 1970 00:00:01 GMT");
    %>
  <style>
body{background:url(../commons/images/bg.jpg) repeat ;background-size: 100%}
img{width:99%}
.jh_bot_lg{    position: absolute;    top: 70%;   width: 100%;    margin: 0 auto;    text-align: center;}
</style>  
</head>
<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
	<div class="back">
	    <img src="../commons/images/bg.jpg">

	<div class="headly">
		<a href="javascript:goBack();" class="goback"><span><b></b><i></i></span></a>
	    <h3>找回密码</h3>
	</div>
	<div class="login">
		<!-- 图形验证码  -->
		<div class="login-group">
				<div class="field">
					<input type="number" maxlength="7" name="captcha" id="captcha" class="text-box" placeholder="请输入图片验证码">	
				</div>
				<img id="captchaImage"  alt="换一张"  width="100px"/>
				<a class="ch" title="换一张" id="changeImage">换一张</a>
		</div>
		<c:if test="${from == null or from == ''}">
			<div class="login-group">
				<div class="field">
					<input type="text" placeholder="请输入手机号码重置密码" class="text-box" id="mobile">
				</div>
			</div>
		</c:if>
		<c:if test="${from == 'cus' }">
			<div class="login-group">
				<div class="field">
					<input type="text" placeholder="请输入手机号码重置密码" value="${mobile }" class="text-box" id="mobile" disabled="disabled">
				</div>
			</div>
		</c:if>
		<div class="login-btn">
			<!-- 
			<button type="button" class="btn" onclick="getCodePas()">下一步</button>
			 -->
			<button type="button" class="btn" onclick="getCodePasBefore()">下一步</button>
		</div>
		<!-- <div class="login-password">
            <a href="javascript:0" class="pwl">注册</a>
            <a href="javascript:0" class="pwr">找回密码</a>
        </div> -->
	</div>
</div>
<div class="jh_bot_lg"><img src="../commons/images/zjm_lg.png" style="width:50%"/></div>
<!-- 表单:跳转页面 -->
<form id="form_toInputPassCode" action="<%=path %>/customer/toInputPassCode" style="display: none;">
	<input type="hidden" name="username">
</form>


<script type="text/javascript">
//图形验证码点击事件
$("#captchaImage").click(function(){
	$("#captcha").val("");
	$(this).attr("src","${path}/customer/getImageFindPwd?date="+new Date());
});
//图形验证事件-换一张
$("#changeImage").click(function(){
	$("#captcha").val("");
	$("#captchaImage").attr("src","${path}/customer/getImageFindPwd?date="+new Date());
});
//页面加载完后处理
$("#captchaImage").click();
$("#captcha").val("");
</script>

</body>
</html>

