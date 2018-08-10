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
.bg .tel1{ position:absolute; top:18.788rem; left:2.6901rem; width:10.610rem; height:1.1529rem; text-indent:1em; font-size:0.5124rem; border-top:1px solid #a4a1a2; border-left:1px solid #a4a1a2; border-right:0; border-bottom:0; border-radius:8px; line-height:1.1529rem;}
.bg .sub1{ position:absolute; width:10.61rem; height:1.21695rem; left:2.6901rem; border:0; background:url(../commons/img/userActivity/focusMedia/10.png) no-repeat; background-size:100%; top:20.1544rem;}

.bg2{ width:100%; position:relative; height:28.4809rem; overflow:hidden;}
.bg2 .get1{ position:absolute; top:18.89475rem; left:4.84645rem; width:6.3196rem; height:2.2631rem; background:url(../commons/img/userActivity/focusMedia/11.png) no-repeat; background-size:100%; border:0;}

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
	<img src="${staticFile_s }/commons/img/userActivity/focusMedia/bg480.jpg">
    <input type="tel" class="tel1" placeholder="输入手机号领取480元红包" id="mobile"><br>
	<input type="submit" value="" class="sub1" onclick="getRedPacketFocusMediaReg()" id="coupons">
</div>

<div class="bg2" style="display:none;" id="redPacketInfo20">
	<img src="${staticFile_s }/commons/img/userActivity/focusMedia/bg4801.jpg">
    <input type="submit" value="" class="get1" onclick="giveRedPacket()">
</div>

<div class="bg3" style="display:none;" id="redPacketInfoEmpty">
	<img src="${staticFile_s }/commons/img/userActivity/focusMedia/bg2.jpg">
    <a href="http://m.zhongjumall.com" class="more"></a>
</div>

<div class="bg4" style="display:none;" id="redPacketInfoTrue">
	<img src="${staticFile_s }/commons/img/userActivity/focusMedia/bg3.jpg">
    <a href="http://m.zhongjumall.com" class="more"></a>
</div>

</body>
</html>






