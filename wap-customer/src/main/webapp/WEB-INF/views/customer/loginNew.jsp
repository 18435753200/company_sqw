<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
	<head>
        <title>登录</title>
        <meta name="keywords" content="这里是关键词">
        <meta name="description" content="这里是描述">
        <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no">
        <meta name="apple-mobile-web-app-status-bar-style" content=" black ">
        <meta name="apple-mobile-web-app-title" content="众聚猫">
        <meta name="format-detection" content="telephone=no">
        <meta name="baidu-tc-cerfication" content="30f757b11897dc4f697eb568cb7eb2a3" />
        <meta name="baidu-site-verification" content="SgTbeKNm8i" />
        <%@include file="/WEB-INF/views/commons/user.jsp" %>
        <link href="${staticFile_s}/commons/css/newlogin.css" rel="stylesheet" type="text/css">
        <link href="${staticFile_s}/commons/css/login.css" rel="stylesheet" type="text/css">
        <link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
        <c:set var="path" value="<%=path %>"></c:set>
        <%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
        <%@include file="/WEB-INF/views/commons/commonsIco.jsp" %>
	</head>
    <body class="reglog">
    	<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
		<header id="header" ><div class="tit">登录</div></header>
    	<section class="column_body">
			<div class="login">
				<input type="hidden" value="${fn:escapeXml(returnUrl)}" name="returnUrl">
				<input type="hidden" name="errorCountName" id="errorCountValue" value="0">
                <div class="login-group">
                    <label>用户名</label>
                    <div class="field"><input id="J_loginAccount_username"  type="text" name="userName"  placeholder="用户名/手机号" class="text-box"></div>
                </div>
                
                <div class="login-group">
                    <label>密码</label>
                    <div class="field"><input id="J_loginAccount_password"  type="password" name="password"  placeholder="请输入密码" class="text-box"></div>
                </div>
                     <div class="login-group" style="display: none;" id="codeDiv">
                     <label>验证码</label>
                     <div class="field">
                      <input type="text" id="captcha" name="captcha" maxLength="7" vld="{required:true}"  class="text-b" /> </div>
						<img src="${path }/customer/getImage?date="+new Date() id="captchaImage"  alt="换一张"  class="yzm"/>
						<a id="changeImage" title="换一张" class="ch">换一张</a></li>
                     </div>
                <div class="login-btn">
                    <input type="button" value="登录" class="btn" onclick="login()"  id="subId" >
                </div>
			     
                 <div class="login-password">
                     <a href="${path }/customer/toRegister?origin=&returnUrl=${fn:escapeXml(returnUrl)}" class="pwl">注册</a>
                     <a href="${path }/customer/toGetpass" class="pwr">找回密码</a>
                 </div>

			</div>
    	</section> 
    	
	</body>
</html>
<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script>
		<script src="${staticFile_s}/commons/js/user/login.js" type="text/javascript"></script>
		<script src="${staticFile_s}/commons/js/user/des.js" type="text/javascript"></script>
<script type="text/javascript">

$("#captchaImage").click(function(){
	$(this).attr("src","${path}/customer/getImage?date="+new Date());
});

$("#changeImage").click(function(){
	$("#captchaImage").attr("src","${path}/customer/getImage?date="+new Date());
});
</script>