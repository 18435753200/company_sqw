<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>${in }元现金抵用劵</title>
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<%@include file="/WEB-INF/views/commons/user.jsp" %>
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
<script src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"  type="text/javascript" ></script>
<script src="${staticFile_s}/commons/js/userActivity/focusMedia.js"  type="text/javascript" ></script>
<style>
*{ margin:0; padding:0; list-style:none;}
body{font-family:'Microsoft Yahei';}
img{ width:100%;}
html{ font-size:20px;}
.clearfix:after{content:""; display:block; clear:both;}
.clearfix {zoom:1;}

.bg{ width:100%; position:relative; overflow:hidden;}
.tel,.txt,.sub,.sub1,.ensure,.ensure1,.ensure2,.use{ position:absolute;}
.tel{ width:10.61rem; height:1.1529rem; top:16.6475rem; left:2.69rem; border-radius:8px; line-height:1.1529rem; text-indent:1em; font-size:0.5124rem; border-top:1px solid #a4a1a2; border-left:1px solid #a4a1a2; border-right:0; border-bottom:0;}
.txt{ width:5.15225rem; height:1.1529rem; left:2.69rem; border-radius:10px; line-height:1.1529rem; text-indent:1em; font-size:0.5124rem; top:17.94985rem; border-top:1px solid #a4a1a2; border-left:1px solid #a4a1a2; border-right:0; border-bottom:0;}
.sub{ width:3.33rem; height:1.1529rem; background:url(../commons/img/userActivity/focusMedia/01.png) no-repeat; top:17.94985rem; left:9.9918rem; background-size:100%; border:0;}

.sub1{width: 5.33rem; height: 1.1529rem; top: 17.96rem; left: 8.1rem; border: 0;}
.sub1 img{width:2.8rem; height:1.1529rem;}
.sub1 a{ font-size:0.3rem; display:block; float:right; line-height:1.21695rem; cursor:pointer; color:#fff; padding-right:10px;}

.ensure{ width:10.61rem; height:1.21695rem; left:2.69rem; border:0; background:url(../commons/img/userActivity/focusMedia/02.png) no-repeat; background-size:100%; top:19.2522rem;}
.ensure1{ top:15.89475rem; left:5.84645rem; width:4.1632rem; height:1.4945rem; background:url(../commons/img/userActivity/focusMedia/03.png) no-repeat; background-size:100%; border:0;}
.ensure2{ top:15.89475rem; left:5.2rem; width:5.6577rem; height:2.0509rem; background:url(../commons/img/userActivity/focusMedia/04.png) no-repeat; background-size:100%; border:0;}

.use{ width:100%;  top:21.44985rem;  text-align: center; color: #ffffff; font-size: 0.8rem;}
.use:hover{color: #ffffff;}
</style>
<script>
;(function(win,doc){
	function change(){
		document.documentElement.style.fontSize=document.documentElement.clientWidth/16+'px';
	}
	
	win.addEventListener('resize',change,false);
	win.addEventListener('DOMContentLoaded',change,false);
})(window,document);
</script>
</head>

<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<!--20元 现金抵用劵-->
<input type="hidden" id="inType" value="${in }" >
<c:if test="${in == 20 }">
	<div class="bg" id="redPacketAmount">
		<img src="${staticFile_s }/commons/img/userActivity/focusMedia/bg.jpg">
		<input type="tel" class="tel" placeholder="输入手机号领取20元现金券" id="mobile">
	    <!-- <input type="text" placeholder="输入验证码" class="txt"><input type="submit" value="" class="sub"> -->
	    <input type="text" id="captcha" name="captcha" maxLength="7" vld="{required:true}"  class="txt" placeholder="输入验证码"/>
		<p class="sub1">
			<img src="${path }/customer/getImageRegist?date="+new Date() id="captchaImage"  alt="换一张"  class="yzm"/>
			<a id="changeImage" title="换一张" class="ch">换一张</a>
		</p>
	    <input type="submit" value="" class="ensure" onclick="getRedPacketFocusMedia()" id="coupons">
	</div>
</c:if>

<!--100元 现金抵用劵-->
<c:if test="${in == 100 }">
	<div class="bg" id="redPacketAmount">
		<img src="${staticFile_s }/commons/img/userActivity/focusMedia/bg3.jpg">
		<input type="tel" class="tel" placeholder="输入手机号领取100元现金券" id="mobile">
	    <!-- <input type="text" placeholder="输入验证码" class="txt"><input type="submit" value="" class="sub"> -->
	    <input type="text" id="captcha" name="captcha" maxLength="7" vld="{required:true}"  class="txt" placeholder="输入验证码"/>
		<p class="sub1">
			<img src="${path }/customer/getImageRegist?date="+new Date() id="captchaImage"  alt="换一张"  class="yzm"/>
			<a id="changeImage" title="换一张" class="ch">换一张</a>
		</p>
	    <input type="submit" value="" class="ensure" onclick="getRedPacketFocusMedia()" id="coupons">
	</div>
</c:if>




<!--20元 现金已领取-->
<div class="bg" style="display: none" id="redpacketTrue20">
	<img src="${staticFile_s }/commons/img/userActivity/focusMedia/bg1.jpg">
    <input type="submit" value="" class="ensure1" onclick="giveRedPacket()">
</div>

<!--100元 现金已领取-->
<div class="bg" style="display: none" id="redpacketTrue100">
	<img src="${staticFile_s }/commons/img/userActivity/focusMedia/bg4.jpg">
    <input type="submit" value="" class="ensure1" onclick="giveRedPacket()">
</div>

<!--20元 100元 现金已领光-->
<div class="bg" style="display: none" id="zero">
	<img src="${staticFile_s }/commons/img/userActivity/focusMedia/bg2.jpg">
    <input type="submit" value="" class="ensure2" onclick="giveRedPacket()">
</div>

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




