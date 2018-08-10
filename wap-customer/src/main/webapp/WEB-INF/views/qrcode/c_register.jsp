<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<head>
<meta charset="utf-8">
<title>分享注册</title>
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-status-bar-style" content=" black ">
<meta name="format-detection" content="telephone=no">
<%@include file="/WEB-INF/views/commons/user.jsp"%>
<script src="${staticFile_s}/commons/js/user/login.js"
	type="text/javascript"></script>
<script src="${staticFile_s}/commons/js/user/des.js"
	type="text/javascript"></script>
<script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
<meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="${staticFile_s}/commons/css/m_registter.css">
<link href="${staticFile_s}/commons/css/00a627dedc644d0098ace9926f5e1849.css" rel="stylesheet">
<link href="${staticFile_s}/commons/css/dfe2199acb654a47820464e94480f0c1.css" rel="stylesheet">
<link href="${staticFile_s}/commons/css/thzc_style.css" rel="stylesheet" type="text/css">
<script src="${staticFile_s}/commons/js/user/register.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	var isWeiXin = is_weixin();
	var openId="${openId}";
	if(openId==''){
		openId='null';
	}
	if(isWeiXin&&openId=='null'){
		var url="http://m.zhongjumall.com/Qr/isWxScan?isWeiXin=true";
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

</head>
<body>
<input type="hidden" id="path" value="<%=path%>" />
<input type="hidden" id="userId" value="${userId}" />
<input type="hidden" id="openId" value="${openId}" >
	<div class="regibody">
		<div class="toplogo" style="text-align: center">
		</div>
		<div class="info">
			<div>
				<input type="text" class="ip" placeholder="推荐邀请码:" id="tjMobile"
					readonly style="color: #8c8c8c;" tjmobile-data="">
				<input type="hidden" class="ip"  id="userId" value="${userId}"
					readonly style="color: #8c8c8c;" tjmobile-data="">
			</div>
			<div>
				<input type="tel" class="ip" placeholder="手机号" id="mobile">
			</div>
			<div>
				<input type="number" class="ip" placeholder="请输入图片验证码" id="captcha">
				<div class="changeimg prop">
				<img src="${path }/customer/getImageRegist?date=" +new
					Date() id="captchaImage" alt="换一张" width="100px" /> <a
					id="changeImage" title="换一张" class="ch">换一张</a>
				</div>
			</div>
			<div>
				<input type="number" class="ip" id="mobileCodeValue"
					placeholder="请输入验证码">
				<div class="showpwd prop">
					<input type="button" id="mobileCode" value="获取验证码"
						onclick="getCode()" class="second">
				</div>
			</div>
			<div class="Serviceag">
				<input type="checkbox" id="agreement" checked="" name="sku-checkbox">
				<a href="${path}/customer/notes">同意《众聚猫服务协议》</a>
			</div>
			<div class="submitBtnBroder">
				<div class="submitText" onclick="register()">确定</div>
			</div>
		</div>
<script>
$("#captchaImage").click(
		function() {
			$(this).attr("src",
					"${path}/customer/getImageRegist?date=" + new Date());
		});

$("#changeImage").click(
		function() {
			$("#captchaImage").attr("src",
					"${path}/customer/getImageRegist?date=" + new Date());
		});
$(function () {
        $('#showpwd').on('tap',function () {
             $('#J_password').attr('type', ($('#J_password').attr('type') == 'text' ? 'password' : 'text'));
             $(this).html(($(this).html() == '隐藏' ? '显示' : '隐藏'));
        });
        $('#changeimg').on('tap', function () {
            $('.changeimg img').attr('src','${path }/customer/getImageRegist?date='+new Date());
        });
        var tjMobileStr=${mobile};
       $('#tjMobile').val('推荐邀请码：'+tjMobileStr).attr({"tjMobile-data":tjMobileStr});
    });

function isEmpty(str) {
	if (str != null && str != undefined && str != "") {
		return false;
	}
	return true;
}

function splitEncodeMoblie(m) {
        var rtnmStr = '';
        if (m && m.length == 11) {
            rtnmStr = m.substr(0, 4) + '****' + m.substr(7, 4);
        }
        return rtnmStr;
    }
</script>
</body>
</html>