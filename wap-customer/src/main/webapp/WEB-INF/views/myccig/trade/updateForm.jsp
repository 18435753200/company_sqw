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
<%-- <link href="${staticFile_s}/commons/css/my.css" rel="stylesheet" type="text/css">
zepto alert
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css"> --%>
<link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/grzxcss/aui.css" />
    <link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/grzxcss/grzx_zfmm.css" />
    <link rel="stylesheet" type="text/css" href="${staticFile_s}/commons/css/grzxcss/grzx_yjfk_css.css" />
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/common/common.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/main.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/person.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/md5.js"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/trade/trade.js"></script>
<title>修改支付密码</title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>
<style>
button, input, select, textarea {font: inherit; color: inherit; line-height: 0.9rem; border-radius: 10px;padding: 0.2rem 0.3rem;font-size: 0.6rem;
}
.aui-list .aui-list-item-input {width: 100%;padding: 0;padding-right: 0rem;-webkit-box-flex: 1;box-flex: 1;-webkit-flex-shrink: 1;flex-shrink: 1;}
</style>
<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>	
<input type="hidden" id="uri" value="${uri}"/>
	<!-- <div class="headly">
		<a href="javascript:;" onclick="goBack();" class="goback"><span><b></b><i></i></span></a>
	    <h3>修改支付密码</h3>
    </div> -->
    <header class="aui-bar aui-bar-nav">
        <a class="aui-pull-left aui-btn" href="javascript:;" onclick="goBack();" class="goback">
            <span class="aui-iconfont aui-icon-left"></span>
        </a>
        <div class="aui-title">支付密码</div>
</header>
<div class="aui-content aui-margin-b-15">
    <ul class="aui-list aui-form-list">
        <li class="aui-list-item">
            <div class="aui-list-item-inner">
                <div class="aui-list-item-label">
                    	手机号
                </div>
                <div class="aui-list-item-input">
                    <input type="text" class="w" value="${mobile}" readonly="readonly" placeholder="请输入手机号"/>
                </div>
            </div>
        </li>
        <li class="aui-list-item">
            <div class="aui-list-item-inner">
                <div class="aui-list-item-label">
                   	 手机验证码
                </div>
                <div class="aui-list-item-input">
                    <input type="number" class="text" width="50%" name="captcha" id="captcha" maxlength="6" placeholder="输入手机验证码" />
                	<input type="button" id="mobileCode" value="获取验证码" onclick="getCode()" class="second">
                </div>
            </div>
        </li>
         <li class="aui-list-item">
                <div class="aui-list-item-inner">
                    <div class="aui-list-item-label">
                    	支付密码
                </div>
                    <div class="aui-list-item-input">
                        <input type="tel" style="-webkit-text-security:disc" class="text" name="pwd" id="pwd" maxLength="7" placeholder="请输入7位支付密码"/>
                    </div>
                    <div class="aui-list-item-label-icon">
<!--                         <i class="aui-iconfont aui-icon-display"></i> -->
                    </div>
                </div>
            </li>
        <li class="aui-list-item">
            <div class="aui-list-item-inner">
                <div class="aui-list-item-label">
                    	确认支付密码
                </div>
                <div class="aui-list-item-input">
                    <input type="tel" style="-webkit-text-security:disc" class="text" name="confirmPwd" id="confirmPwd" maxLength="7"  placeholder="请再次输入支付密码"/>
                </div>
            </div>
        </li>
	</ul>
	</div>
	<div class="aui-btn aui-btn-danger aui-btn-block"><a href="javascript:" class="bg" style="color: white">保存</a></div> 
	
	<script type="text/javascript" src="${staticFile_s}/commons/js/api.js" ></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/aui-range.js" ></script>
<script type="text/javascript">
    var range = new auiRange({
        element:document.getElementById("range")
    },function(ret){
    })
    apiready = function(){
        api.parseTapmode();
    }

</script>
<%-- <div class="wrap" id="myinfo">
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
                <input type="text" class="text" style="width:60%" name="captcha" id="captcha" maxlength="6" placeholder="输入手机验证码" />
                <input type="button" id="mobileCode" value="获取验证码" onclick="getCode()" class="second">
            </div>
        </li>
        
        <li>
            <div class="label" style="width: 95px;">新支付密码</div>
            <div class="field"> 
                <input type="password" class="text" name="pwd" id="pwd" maxLength="7" placeholder="7位纯数字密码"/>
            </div>
        </li>
        <li>
            <div class="label" style="width: 95px;">确认密码</div>
            <div class="field"> 
                <input type="password" class="text" name="confirmPwd" id="confirmPwd" maxLength="7"  placeholder="7位纯数字密码"/>
            </div>
        </li>
    </ul>
    <div class="form-btn">
    <div class="exit">
       <a href="javascript:" class="bg">保存</a>
    </div>
    </div>
</form>
</div> --%>
</body>
<script>
	num($("#captcha"));
	num($("#pwd"));
	num($("#confirmPwd"));
	/* getCode(); */
</script>
</html>