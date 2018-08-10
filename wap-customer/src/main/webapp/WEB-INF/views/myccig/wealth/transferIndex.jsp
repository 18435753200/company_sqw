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
<script type="text/javascript" src="${staticFile_s}/commons/js/wealth/transfer.js"></script>
<title>红旗券转账</title>
<%@include file="/WEB-INF/views/commons/baidu_tongji.jsp" %>
</head>
<style>
.second{cursor: pointer!important;color: #000000;font-size: 1.3rem;background: initial;width: 90px!important;}
 .section {
            width: 100%;
        }

        .top-header {
            width: 100%;
            height: 1.3rem;
            line-height: 1.3rem;
            text-align: center;
            font-size: 0.5rem;
            border-bottom: 1px solid #d7d7da;
            position: relative;
        }

        .top-header span {
            width: 0.3rem;
            height: 0.56rem;
            background: url("../img/gobak.png") no-repeat center center;
            background-size: contain;
            position: absolute;
            top: 0.35rem;
            left: 0.5rem;
        }

        .wrap {
            width: 100%;
        }

        .goods-nam {
            width: 100%;
            margin-top: 1rem;
            text-align: center;
            font-size: 2rem;
        }

        .goods-price {
            width: 100%;
            height: 5rem;
            line-height: 5rem;
            text-align: center;
            font-size: 3rem;
            color: #da251e;
        }

        .pwd-box {
            width: 100%;
            padding: 0 5%;
        }

        .am-password-handy {
            overflow: hidden;
            position: relative;
            margin: 0;
            width: 100%;
            padding-bottom: 1px;
        }

        input[type=tel] {
            position: absolute;
            opacity: .01;
            border: 0 none !important;
            left: 0;
            -webkit-appearance: none;
            -ms-box-sizing: content-box !important;
            box-sizing: content-box !important;
            outline: none;
        }

        input[type=tel] {
            -ms-box-sizing: border-box;
            box-sizing: border-box;
            display: block;
            width: 100%;
            height: 44px;
            overflow: hidden;
            padding: 0 !important;
            border: 1px solid #ddd;
            border-radius: 4px;
            background-clip: padding-box;
            font-family: Courier, monospace;
            font-size: 24px;
            line-height: 32px;
        }

        .am-password-handy .am-password-handy-security {
            display: -webkit-box !important;
            display: -ms-flexbox !important;
            display: box !important;
            padding: 0;
            background-color: #fff;
        }

        .am-password-handy .am-password-handy-security, .am-password-handy input[type=password], .am-password-handy input[type=tel] {
            -ms-box-sizing: border-box;
            box-sizing: border-box;
            display: block;
            width: 90%;
            height: 44px;
            overflow: hidden;
            padding: 0 !important;
            border: 1px solid #ddd;
            border-radius: 4px;
            background-clip: padding-box;
            font-family: Courier, monospace;
            font-size: 24px;
            line-height: 32px;
        }

        .am-password-handy .am-password-handy-security li {
            -webkit-box-flex: 1;
            -ms-flex: 1;
            box-flex: 1;
            margin-right: -1px;
            border-right: 1px solid #ddd;
            overflow: hidden;
            text-align: center;
        }

        .am-password-handy .am-password-handy-security i:empty {
            margin: 16px 2px 0;
            width: 10px;
            height: 10px;
            border-radius: 10px;
            background-clip: padding-box;
            background-color: #000;
        }

        .am-password-handy .am-password-handy-security i {
            display: inline-block;
            width: 14px;
            overflow: hidden;
            line-height: 42px;
            font-style: normal;
            visibility: hidden;
        }

        .forgetpwd {
            margin: 0.4rem 0 0;
            padding: 0 5%;
            text-align: right;
        }

        .forgetpwd a {
            color: #878787;
            text-decoration: underline;
        }
		
		.tip-msg {
            width: 95%;
            margin: 0.4rem 0;
            padding: 0.4rem 5%;
            background: #efefef;
            color: #8f8f8f;
        }
		
		.tip-msg a{
            cursor:pointer;
            color: #da251e;
            text-decoration: underline;
        }
		
        .btn-sure {
            width: 90%;
            height: 1.3rem;
            line-height: 1.3rem;
            margin: 0.8rem auto;
            text-align: center;
            background: #da251e;
            color: #fff;
            font-size: 0.5rem;
        }

        .shw-txt {
            font-size: 0.4rem;
        }
        .wrap-footer {
		    width: 100%;
		    padding: 0.5rem 5%;
		    font-size: 1.5rem;
		}
		
</style>
<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<input type="hidden" id="basePath" value="<%=basePath %>"/>	
<input type="hidden" id="currStep" value=""/>	
<input type="hidden" id="isCaptcha" value=""/>	
	<div class="headly">
		<a href="javascript:;" onclick="goBack();" class="goback"><span><b></b><i></i></span></a>
	    <h3>红旗券转账</h3>
    </div>
<div class="wrap" id="step01">
<form action="${path}/trade/setTrade" id="tradeForm" method="post" >
    <ul class="form-list">
        <li>
            <div class="label" style="width: 95px;">对方账户</div>
            <div class="field">
                <input type="text" class="w code" id="account" maxlength="11" placeholder="输入账户ID或者手机号" />
            </div>
        </li>
        
        <li>
            <div class="label" style="width: 95px;">真实姓名</div>
            <div class="field"> 
                <input type="text" class="text" name="realName" id="realName" maxlength="5" placeholder="输入真实姓名" />
            </div>
        </li>
        
        <li style="background-color:#d7d7d7;">
            <div style="line-height:3rem;">*您的红旗券可转出金额为${transferReward }</div>
        </li>
        
        <li>
            <div class="label" style="width: 95px;">转出红旗券</div>
            <div class="field"> 
                <input type="text" class="text code" name="amount" id="amount" maxlength="10" placeholder="输入转出的红旗券"/>
            </div>
        </li>
        
        <li>
            <div class="label" style="width: 95px;">留言</div>
            <div class="field"> 
                <input type="text" class="text" name="remark" id="remark"  placeholder="输入留言"/>
            </div>
        </li>
    </ul>
    <div class="wrap-footer">*请准确填写对方账户ID或手机号，并输入真实姓名</div>
    <div class="error_tips hide"></div>
    <div class="form-btn">
	    <div class="exit">
	       <a href="javascript:" class="bg" id="next">下一步</a>
	    </div>
    </div>
</form>
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
	                <input type="text" class="text code" style="width:60%" name="captcha" id="captcha" maxlength="6" placeholder="输入手机验证码" />
	                <input type="button" id="mobileCode" value="获取验证码" onclick="getCode()" class="second">
	            </div>
	        </li>
	    </ul>
	    <div class="form-btn">
	    <div class="exit">
	       <a href="javascript:" class="bg" id="captchaStep">下一步</a>
	    </div>
	    </div>
	</div>
	
	<div id="step03" style="display:none;">
		 <div class="wrap" style="background: #fff;">
	        <!-- <div class="goods-nam">商品购买</div> -->
	        <div class="goods-price"></div>
	        <div class="pwd-box">
	            <div class="J-keyboard am-password-handy">
	                <input type="tel" class="J-pwd J-needsclick J-needsfocus code" id="pwd" name="pwd"  maxlength="7" pattern="\d*" autocomplete="off">
	                <ul class="am-password-handy-security">
	                    <li><i style="visibility: hidden;"></i></li>
	                    <li><i style="visibility: hidden;"></i></li>
	                    <li><i style="visibility: hidden;"></i></li>
	                    <li><i style="visibility: hidden;"></i></li>
	                    <li><i style="visibility: hidden;"></i></li>
	                    <li><i style="visibility: hidden;"></i></li>
	                    <li><i style="visibility: hidden;"></i></li>
	                </ul>
	            </div>
	        </div>
	        <div class="forgetpwd"><a href="<%=path %>/trade/toRetrieve?uri=${path}/wealth/transferIndex">忘记密码?</a></div>
	        <div class="tip-msg">温馨提示：尊敬的用户您好,为了提升安全等级,我们已将支付密码升级为&nbsp;7&nbsp;位,请您到<a href="${path }/trade/toSetting?uri=${path}/wealth/transferIndex">红旗券支付</a>修改密码。</div>
    	</div>
	    <div class="form-btn">
	    <div class="exit">
	       <a href="javascript:" class="bg" id="sub">确定</a>
	    </div>
	    </div>
	</div>
</body>
<script>
	num($(".code"));
	$("#pwd").on('input', function () {
        var $this = $(this);
        var valLength = $this.val().length;
        var lis = $('.am-password-handy-security li');
        lis.find('i').css({"visibility": "hidden"});
        for (var i = 0; i < valLength; i++) {
            $(lis[i]).find('i').css({"visibility": "visible"});
        }
    });
</script>
</html>