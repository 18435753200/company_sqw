<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>众聚猫</title>
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
body{font-family:'Microsoft Yahei'; background:#fff;}
img{ width:100%;}
html{ font-size:20px;}
.clearfix:after{content:""; display:block; clear:both;}
.clearfix {zoom:1;}

.bg{ width:100%; position:relative; height:28.4809rem; overflow:hidden;}
.bg .tel,.bg .txt,.bg .sub,.bg .ensure{ position:absolute;}
.bg .tel{ width:10.61rem; height:1.1529rem; top:18.1475rem; left:2.69rem; border-radius:8px; line-height:1.1529rem; text-indent:1em; font-size:0.5124rem; border-top:1px solid #a4a1a2; border-left:1px solid #a4a1a2; border-right:0; border-bottom:0;}
.bg .txt{ width:5.15225rem; height:1.1529rem; left:2.69rem; border-radius:10px; line-height:1.1529rem; text-indent:1em; font-size:0.5124rem; top:19.44985rem; border-top:1px solid #a4a1a2; border-left:1px solid #a4a1a2; border-right:0; border-bottom:0;}
.bg .sub{ width:5.33rem; height:1.1529rem; top:19.44985rem; left:8.1rem;  border:0;}
.bg .sub img{width:2.8rem; height:1.1529rem;}
.bg .sub a{ font-size:0.3rem; display:block; float:right; line-height:1.21695rem; cursor:pointer; color:#fff; padding-right:10px;}
.bg .ensure{ width:10.61rem; height:1.21695rem; left:2.69rem; border:0; background:url(../commons/img/userActivity/focusMedia/02.png) no-repeat; background-size:100%; top:20.7522rem;}

.bg2{ width:100%; position:relative; height:28.4809rem; overflow:hidden;}
.bg2 .get{ position:absolute; top:18.89475rem; left:4.84645rem; width:6.3196rem; height:2.2631rem; background:url(../commons/img/userActivity/focusMedia/03.png) no-repeat; background-size:100%; border:0;}

.bg3{ width:100%; position:relative; height:28.4809rem; overflow:hidden;}
.bg3 .more{ position:absolute; width:5.2948rem; height:1.9215rem; background:url(../commons/img/userActivity/focusMedia/13.png) no-repeat; background-size:100%; top:13.557rem; left:5.3802rem;}

.bg4{ width:100%; position:relative; height:28.4809rem; overflow:hidden;}
.bg4 .more{ position:absolute; width:5.2948rem; height:1.9215rem; background:url(../commons/img/userActivity/focusMedia/13.png) no-repeat; background-size:100%; top:13.557rem; left:5.3802rem;}
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
<input type="hidden" id="inType" value="${in }" >
<div class="bg" id="redPacketInit">
	<img src="${staticFile_s }/commons/img/userActivity/focusMedia/bg.jpg">
	<input type="tel" class="tel" placeholder="输入手机号领取20元现金券" id="mobile">
	<input type="text" id="captcha" name="captcha" maxLength="7" vld="{required:true}"  class="txt" placeholder="输入验证码"/>
	<p class="sub">
	<img src="${path }/customer/getImageRegist?date="+new Date() id="captchaImage"  alt="换一张"  class="yzm"/>
	<a id="changeImage" title="换一张" class="ch">换一张</a>
	</p>
    <input type="submit" value="" class="ensure" onclick="getRedPacketFocusMedia()" id="coupons">
</div>

<div class="bg2" style="display:none" id="redPacketInfo20">
	<img src="${staticFile_s }/commons/img/userActivity/focusMedia/bg1.jpg">
    <input type="submit" value="" class="get" onclick="giveRedPacket()">
</div>

<div class="bg3" style="display:none;" id="redPacketInfoEmpty">
	<img src="${staticFile_s }/commons/img/userActivity/focusMedia/bg2.jpg">
    <a href="http://m.zhongjumall.com" class="more"></a>
</div>

<div class="bg4"  id="redPacketInfoTrue" style="display: none;">
	<img src="${staticFile_s }/commons/img/userActivity/focusMedia/bg3.jpg">
    <a href="http://m.zhongjumall.com" class="more"></a>
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





