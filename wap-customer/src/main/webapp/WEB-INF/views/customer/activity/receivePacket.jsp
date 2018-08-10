<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%
	String path2 = request.getContextPath();
	request.setAttribute("path", path2);
	request.setAttribute("url", request.getServletPath());
	String basePath2 = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path2 + "/";
	
%>
<html lang="en">
<head>
	<meta charset="utf-8">
    <title>百万礼劵带你嗨翻金秋</title>
   <meta charset="utf-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="format-detection" content="telephone=no">
    <script type="text/javascript" src="${staticFile_s}/commons/js/zepto.min.js"></script>
    <script type="text/javascript" src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"></script>
    <script type="text/javascript" src="${staticFile_s}/commons/js/common/common.js"></script>
    <script type="text/javascript" src="${path }/commons/js/userActivity/receivePacket2.js"></script>
    <link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
    <link rel="icon" href="http://customer.zhongjumall.com/commons/images/favicon.ico" mce_href="http://customer.zhongjumall.com/commons/images/favicon.ico" ype="image/x-icon">
	<link href="${staticFile_s}/commons/css/bwljcss.css" rel="stylesheet" type="text/css">
</head>
<body>
<input type="hidden" id="path" value="${path }"/>
 <div class="header"><img src="${staticFile_s}/commons/img/wap/bg1.jpg"></div>
  <div class="main">
        <!--活动规则-->
		<div class="box-cent" id="box-1">
		   <h2>活动规则：</h2>
           <p>1、用户输入手机号，验证码，即可领取100元现金券，购物满199元，立即抵扣100元；</span><br />
2、用户登录iTV电视商城首页进入《百万礼券带你嗨翻金秋》专场，浏览商品后手机微信扫描商品二维码可直接购买；<br />
3、同一手机号、同一账号、同一设备号、只能领取一次；<br />
4、现金券不与其它优惠活动同享，每单仅可使用一次；<br />
5、现金券使用时间为2016年9月14日-2016年9月19日。 </p>
		   <div class="box-t">
			    <h4 style="display:none">
					<input id="captcha" name="captcha" type="text" class="tt" placeholder="验证码">
						<img src="${path}/customer/getImageRegist?date="+new Date()" id="captchaImage"  alt="换一张"  width="90px"/>
						<a id="changeImage" title="换一张" class="ch">换一张</a>
					</h4>
					<h4><input id="mobile" type="text" class="tb" placeholder="输入手机号"></h4>
					<h4><input id="code" type="text" class="bb" placeholder="输入手机验证码"><span id="codeLink"  onclick="getVerificationCode()">获取验证码</span></h4>
		   </div>
		   <a href="javascript:;" id="sub" onclick="registerCoud()" class="box-a">确认领取</a>
		   <span class="tutu"></span>
		</div>
        <!--领取成功-->
		<div class="box" id="box-2" style="display:none;">
		   <h2><img src="${staticFile_s}/commons/img/wap/title3.jpg" width="160"></h2>
		   <p>恭喜您，已成功领取100元现金券！赶紧去iTV电视商城购物吧～</p>
		   <span class="tutu"></span>
		</div>
  		<!--已领过-->
		<div class="box" id="box-3" style="display:none;">
		   <h2><img src="${staticFile_s}/commons/img/wap/title1.jpg" width="160"></h2>
		   <p>温馨提示：你的手机号已领取过优惠券，同一手机号、同一账号、同一设备号、只能领取一次。</p>
		   <span class="tutu"></span>
		</div>
		<!--已抢光-->
		<div class="box" id="box-4" style="display:none;">
		   <h2><img src="${staticFile_s}/commons/img/wap/title2.jpg"  width="160"></h2>
		   <p>温馨提示：很遗憾，活动优惠券已被全部抢光。</p>
		   <span class="tutu"></span>
		</div>
  </div>
  <div class="footer"><img src="${staticFile_s}/commons/img/wap/bg3.jpg"></div>

<script type="text/javascript">

$("#captchaImage").click(function(){
	$(this).attr("src","${path}/customerActivity/getImageRegist?date="+new Date());
});

$("#changeImage").click(function(){
	$("#captchaImage").attr("src","${path}/customerActivity/getImageRegist?date="+new Date());
});
/* function getVerificationCode(){
	//正则验证手机号
	var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
	if(!myreg.test($("#mobile").val())) { 
                    dialogStr('请输入有效的手机号码');
		return; 
	}
           var captcha = $("#captcha").val()
           if(captcha == null || captcha == ""){
                   dialogStr('请输入有效验证码');
		return; 
            }
	$(".fb-banner").css('display', '');
	$(".fb-banner2").css('display', 'none');
    //获取验证码
    var mobile=$("#mobile").val();
	var _dataType = "jsonp";
	var _type = "get";
	var _url =path+"/customer/getRegistCodeJsonp";
	$.ajax({
		dataType : _dataType,
		type : _type,
		url : _url,
		jsonp: 'jsoncallbackUCcode',
		data : {
			"mobile":mobile,"captcha":captcha 
		},
		success : function(json) {
		}
	});	

} */
</script>
<input type="hidden" id="wtype" value="${wapType }"/>
<input type="hidden" id="path" value="${path}"/>
</body>
</html>