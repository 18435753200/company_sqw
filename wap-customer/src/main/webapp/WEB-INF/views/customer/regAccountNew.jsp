<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
	<head>
        <meta charset="utf-8">
        <title>注册</title>
        <meta name="keywords" content="这里是关键词">
        <meta name="description" content="这里是描述">
        <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no">
        <meta name="apple-mobile-web-app-status-bar-style" content=" black ">
        <meta name="apple-mobile-web-app-title" content="众聚猫">
        <meta name="format-detection" content="telephone=no">
        <meta name="baidu-tc-cerfication" content="30f757b11897dc4f697eb568cb7eb2a3" />
        <meta name="baidu-site-verification" content="SgTbeKNm8i" />
        <%@include file="/WEB-INF/views/commons/user.jsp" %>
        <link href="${staticFile_s}/commons/css/newbese.css" rel="stylesheet" type="text/css">
        <link href="${staticFile_s}/commons/css/login.css" rel="stylesheet" type="text/css">
        <link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
	</head>
	<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
    <body class="reglog">
    	<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
		<header><div class="tit">注册</div></header>
    	<section class="column_body">

            <div class="login">
                <div class="login-group">
                    <div class="field"><input id="J_forgotPassAccount"  type="text" placeholder="+86  请输入手机号" class="text-box"></div>
                </div>
                     <div class="login-group"  id="codeDiv">
                     <label>验证码</label>
                     <div class="field">
                      <input type="text" id="captcha" name="captcha" maxLength="7" vld="{required:true}"  class="text-b" /> </div>
						<img src="${path }/customer/getImageRegist?date="+new Date() id="captchaImage"  alt="换一张"  class="yzm"/>
						<a id="changeImage" title="换一张" class="ch">换一张</a></li>
                     </div>
    			<div class="login-g  check-box">
                  	<span><input type="checkbox"  checked="" name="sku-checkbox" id="agreement"><a href="http://api.zhongjumall.com/register_notes.jsp"  class="xi">同意《众聚猫服务协议》</a></span>
                </div>
                <div class="login-btn n">
                    <input type="button" value="获取验证码" onclick="getCodeNew()" class="btn">
                </div>

            </div>

    	</section> 
    	<script src="${staticFile_s}/commons/js/main.js" type="text/javascript"></script>
		<script src="${staticFile_s}/commons/js/user/register.js" type="text/javascript"></script>
	</body>
</html>

<script type="text/javascript">

$("#captchaImage").click(function(){
	$(this).attr("src","${path}/customer/getImageRegist?date="+new Date());
});

$("#changeImage").click(function(){
	$("#captchaImage").attr("src","${path}/customer/getImageRegist?date="+new Date());
});
</script>