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
    <title>众聚猫</title>
    <meta name="keywords" content="这里是关键词">
    <meta name="description" content="这里是描述">
	<meta name="viewport" content="minimal-ui=yes,width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
    <meta name="apple-mobile-web-app-status-bar-style" content=" black ">
    <meta name="apple-mobile-web-app-title" content="众聚猫">
    <meta name="format-detection" content="telephone=no">
    
    <link href="${path }/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
    <link href="${path }/commons/css/receivePacket.css" rel="stylesheet" type="text/css">
    
    <script type="text/javascript" src="${path }/commons/js/zepto.min.js"></script>
    <script type="text/javascript" src="${path }/commons/js/jqueryAlert/zepto.alert.js"></script>
    <script type="text/javascript" src="${path }/commons/js/common/common.js"></script>
    <script type="text/javascript" src="${path }/commons/js/userActivity/receivePacket2.js"></script>
    
</head>
<style type="text/css">
	
</style>
<body>
<input type="hidden" id="path" value="${path }"/>
	 <div class="main">
        <!--活动规则-->
		<div class="box-cent" id="box-1">
		   <h2>活动规则：</h2>
           <p>1、输入手机号，验证码，<span>即可领取100元现金券，购物满199元即可使用；</span><br />
2、用户登录iptv进入众聚猫频道大促专场，浏览商品后手机扫码购买；<br />
3、同一手机号、同一账号、同一设备号、只能领取一次；<br />
4、现金券不与其它优惠活动同享，每单仅可使用一次；<br />
5、现金券使用时间为2016年9月7日-2016年9月17日。 <span class="tutu"></span></p>
		   <div class="box-t">
				    <h4>
						<input id="captcha" name="captcha" type="text" class="tt" placeholder="验证码">
						<img src="${path}/customer/getImageRegist?date="+new Date()" id="captchaImage"  alt="换一张"  width="90px"/>
						<a id="changeImage" title="换一张" class="ch">换一张</a>
					</h4>
					<h4><input id="mobile" type="text" class="tb" placeholder="输入手机号"></h4>
					<h4><input id="code" type="text" class="bb" placeholder="输入手机验证码"><span id="codeLink"  onclick="getVerificationCode()">获取验证码</span></h4>
					
		   </div>
		   <a href="javascript:;" id="sub" onclick="registerCoud()" class="box-a">确认领取</a>
		</div>
        <!--领取成功-->
		<div class="box" id="box-2" style="display:none;">
		   <h2><img src="${path }/commons/img/receivePacket/title3.jpg"></h2>
		   <p>恭喜您，已成功领取100元现金券！赶紧去IPTV商城购物吧～</p>
		   <span class="tutu"></span>
		</div>
  		<!--已领过-->
		<div class="box" id="box-3" style="display:none;">
		   <h2><img src="${path }/commons/img/receivePacket/title1.jpg"></h2>
		   <p>温馨提示：你的手机号已领取过优惠券，同一手机号、同一账号、同一设备号、只能领取一次。</p>
		   <span class="tutu"></span>
		</div>
		<!--已抢光-->
		<div class="box" id="box-4" style="display:none;">
		   <h2><img src="${path }/commons/img/receivePacket/title2.jpg" ></h2>
		   <p>温馨提示：很遗憾，活动优惠券已被全部抢光。</p>
		   <span class="tutu"></span>
		</div>
		
  </div>

<script type="text/javascript">

$("#captchaImage").click(function(){
	$(this).attr("src","${path}/customerActivity/getImageRegist?date="+new Date());
});

$("#changeImage").click(function(){
	$("#captchaImage").attr("src","${path}/customerActivity/getImageRegist?date="+new Date());
});
</script>
<input type="hidden" id="wtype" value="${wapType }"/>
<input type="hidden" id="path" value="${path}"/>
</body>
</html>