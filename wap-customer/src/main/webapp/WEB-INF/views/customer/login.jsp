<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>登录</title>
    <meta name="keywords" content="众聚猫">
    <meta name="description" content="众聚猫">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no">
    <meta name="apple-mobile-web-app-status-bar-style" content=" black ">
    <meta name="apple-mobile-web-app-title" content="众聚猫">
    <meta name="format-detection" content="telephone=no">
    <%@include file="/WEB-INF/views/commons/user.jsp" %>
    <link href="${staticFile_s}/commons/css/login.css" rel="stylesheet" type="text/css">
	<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
    <script type="text/javascript">
     $(document).ready(function(){
    	var isWeiXin = is_weixin();
    	var openId='<%=request.getAttribute("openId")%>';
    	document.getElementById("isWeiXin").value=isWeiXin;
    	if(isWeiXin&&openId=='null'){
    		var url=$("#path").val()+"/customer/toLogin?isWeiXin=true";
    		window.location.href = url;
    	}
    	function is_weixin() {
    	    var ua = navigator.userAgent.toLowerCase();
    	    if (ua.match(/MicroMessenger/i) == "micromessenger") {
    	        return true;
    	    } else {
    	        return false;
    	    }
    	}
    }) 
</script>
<style type="text/css">
html{
   	max-width: 640px;
		min-width: 320px;
 		margin: 0 auto;
}
.jh_bot_lg{
position:absolute;
    top: 62%;
    width: 100%;
    margin: 0 auto;
    text-align: center;
}
img{width:99%}
body{background:url(../commons/images/bg.jpg) repeat ;background-size: 100%}
</style>
    <%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
    <%@include file="/WEB-INF/views/commons/commonsIco.jsp" %>
</head>
<body>
	<%-- hidden域 --%>
	<input type="hidden" id="path" value="<%=path %>"/>
	<input type="hidden" id="isWeiXin" />
	<div class="back">
	<img src="../commons/images/bg.jpg">
	<div class="headly">
		<a href="javascript:goBack();" class="goback"><span><b></b><i></i></span></a>
	    <h3>登录</h3>
	</div>
	<div class="jh_bot_lg"><img src="../commons/images/zjm_lg.png" style="width:50%"/></div>
	<div class="login">
	<input type="hidden" name="errorCountName" id="errorCountValue" value="0">
	<input type="hidden" id="openId"  value="${openId }">
		<div class="login-group">
			<label>用户名</label>
			<div class="field">
				<input type="text" placeholder="用户ID号/手机号" class="text-box" id="mobile">
			</div>
		</div>
		<div class="login-group">
			<label>密码</label>
			<div class="field">
				<input type="password" placeholder="请输入密码" class="text-box" id="password">
			</div>
		</div>
		<div class="login-group" style="display: none;" id="codeDiv">
            <label>验证码</label>
            <div class="field">
             	<input type="text" id="captcha" name="captcha" maxLength="7" class="text-box" /> 
             </div>
			<img src="${path }/customer/getImage?date="+new Date() id="captchaImage"  alt="换一张"  class="yzm"/>
			<a id="changeImage" title="换一张" class="ch">换一张</a>
         </div>
		<div class="login-btn">
			<button type="button" class="btn" onclick="login()"  id="subId">登录</button>
			<input type="hidden" id="productId" value="${productId}"/>
		</div>
		<div class="login-password">
				<a href="${path}/customer/toRegister?origin=&openId=${openId}&returnUrl=${fn:escapeXml(returnUrl)}&toPay=${toPay}" class="pwl">注册</a>
           		<a href="${path }/customer/toGetpass" class="pwr">找回密码</a>
        </div>
	</div>
</div>
</body>
</html>
<script src="${staticFile_s}/commons/js/user/login.js" type="text/javascript"></script>
<script src="${staticFile_s}/commons/js/user/des.js" type="text/javascript"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<script type="text/javascript">
$("#captchaImage").click(function(){
	$(this).attr("src","${path}/customer/getImage?date="+new Date());
});

$("#changeImage").click(function(){
	$("#captchaImage").attr("src","${path}/customer/getImage?date="+new Date());
});
</script>