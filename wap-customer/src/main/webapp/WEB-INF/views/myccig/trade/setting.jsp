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
<title>支付密码设置</title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>
<style>
.second{cursor: pointer!important;color: #000000;font-size: 1.3rem;background: initial;width: 90px!important;}
h3{
	background-color: #e63232;
	color: #ffffff;
}
.headly span b {
    position: absolute;
    width: 1.1rem;
    height: 0;
    border-top: 0.1rem solid #ffffff;
    transform: rotate(-45deg);
    -webkit-transform: rotate(-45deg);
    top: 35%;
    left: 0.05rem;
}
.headly span i {
    position: absolute;
    width: 0;
    height: 1rem;
    border-left: 0.1rem solid #ffffff;
    transform: rotate(-45deg);
    -webkit-transform: rotate(-44deg);
    top: 50%;
    left: 0.6rem;
}

</style>
<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>	
<input type="hidden" id="returnUrl" value="${returnUrl}" />
<input type="hidden" id="orderId" value="${orderId}" />
<input type="hidden" id="act" value="${act}"/>
<input type="hidden" id="uri" value="${uri}"/>
	<div class="headly">
		<a href="javascript:;" onclick="goBack();" class="goback"><span><b></b><i></i></span></a>
	    <h3>支付密码设置</h3>
    </div>
<div class="wrap" id="myinfo">
<form action="${path}/trade/setTrade" id="tradeForm" method="post" >
    <ul class="form-list">
        <li>
            <div class="label" style="width: 95px;">手机号</div>
            <div class="field">
                <input type="text" class="w" value="${mobile}" readonly="readonly"/>
            </div>
        </li>
        <li>
            <div class="label" style="width: 95px;">手机验证码</div>
            <div class="field"> 
                <input type="text" class="text" style="width:50%" name="captcha" id="captcha" maxlength="6" placeholder="输入手机验证码" />
                <input type="button" id="mobileCode" value="获取验证码" onclick="getCode()" class="second">
            </div>
        </li>
        
        <li>
            <div class="label" style="width: 95px;">输入支付密码</div>
            <div class="field"> 
                <input type="tel" style="-webkit-text-security:disc" class="text" name="pwd" id="pwd" maxLength="7" placeholder="7位纯数字密码"/>
            </div>
        </li>
        
        <li>
            <div class="label" style="width: 95px;">确认支付密码</div>
            <div class="field"> 
                <input type="tel" style="-webkit-text-security:disc" class="text" name="confirmPwd" id="confirmPwd" maxLength="7"  placeholder="7位纯数字密码"/>
            </div>
        </li>
    </ul>
    <div class="error_tips hide"></div>
    <div class="form-btn">
    <div class="exit">
       <a href="javascript:" class="bg" style="width: 90%;margin: 0 5%;height: 3.5rem;line-height: 3.5rem;font-size: 1.5rem; border-radius: 10px;">保存</a>
    </div>
    </div>
</form>
</div>
</body>
<script>
	num($("#captcha"));
	num($("#pwd"));
	num($("#confirmPwd"));
</script>
</html>