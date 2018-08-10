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
<link href="${staticFile_s}/commons/css/my.css" rel="stylesheet" type="text/css">
<%-- zepto alert --%>
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/common/common.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/main.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/person.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/md5.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/trade/trade.js"></script>
<title>找回支付密码</title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>
<style>
.second{cursor: pointer!important;color: #000000;font-size: 1.3rem;background: initial;width: 90px!important;}
h3{
	font-size: 100%;
    font-weight: normal;
    color: #ffffff;
    background-color: #e63232;
}
</style>
<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>	
<input type="hidden" id="uri" value="${uri}"/>
	<div class="headly">
		<a href="javascript:;" onclick="goBack();" class="goback"><span><b></b><i></i></span></a>
	    <h3>找回密码</h3>
    </div>
<div class="wrap" id="myinfo">
<form action="${path}/trade/setTrade" id="tradeForm" method="post" >
	<div id="step01">
		<ul class="form-list">
	        <li>
	            <div class="label">手机号</div>
	            <div class="field">
	                <input type="text" class="w" value="${mobile}" readonly="readonly"/>
	            </div>
	        </li>
	        <li>
	        	<div class="label">验证码</div>
				<div class="field">
					<input type="number" placeholder="请输入图片验证码" class="text-box" id="imageCaptcha" name="imageCaptcha" maxLength="5">	
				</div>
				<img src="${path }/trade/getImageCaptcha?date="+new Date() id="captchaImage" style="margin-top: 10px;" alt="换一张" height="30px" width="100px"/>
				<a id="changeImage" title="换一张" class="ch">换一张</a>        
	        </li>
	    </ul>
	    <div class="form-btn">
	    <div class="exit">
	       <a href="javascript:" id="next">下一步</a>
	    </div>
	    </div>
	</div>
	
	<div id="step02" style="display: none;">
		<ul class="form-list">
	        <li>
	            <div class="label">验证码已发送至</div>
	            <div class="field">
	                <input type="text" class="w" value="+86 ${mobile}" readonly="readonly"/>
	            </div>
	        </li>
	        
	        <li>
	            <div class="label" style="width: 95px;">手机验证码</div>
	            <div class="field"> 
	                <input type="number" class="text" style="width:50%" name="captcha" id="captcha" maxlength="6" placeholder="输入手机验证码" />
	                <input type="button" id="mobileCode" value="获取验证码" onclick="getCode()" class="second">
	            </div>
	        </li>
	        
	        <li>
	            <div class="label" style="width: 95px;">新支付密码</div>
	            <div class="field"> 
	                <input type="tel" style="-webkit-text-security:disc" class="text" name="pwd" id="pwd" maxLength="7" placeholder="7位纯数字密码"/>
	            </div>
	        </li>
	    </ul>
	    <div class="form-btn">
	    <div class="exit">
	       <a href="javascript:" class="sub">确定</a>
	    </div>
	    </div>
	</div>
</form>
</div>
</body>
<script>
	num($("#captcha"));
	num($("#pwd"));
	num($("#confirmPwd"));
	$("#changeImage").click(function(){
		$("#captchaImage").attr("src","${path}/trade/getImageCaptcha?date="+new Date());
	});
</script>
</html>