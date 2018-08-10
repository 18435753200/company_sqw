<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>领取红包</title>
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<%@include file="/WEB-INF/views/commons/user.jsp" %>
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
<script src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"  type="text/javascript" ></script>
<script src="${staticFile_s}/commons/js/userActivity/thr.js"  type="text/javascript" ></script>
<style>
*{ margin:0; padding:0; list-style:none;}
body{font-family:'Microsoft Yahei'; background:#c81428;}
img{ width:100%;}
html{ font-size:20px;}
.clearfix:after{content:""; display:block; clear:both;}
.clearfix {zoom:1;}

/*输入手机号领取20元代金卷*/
.bg{ width:100%; position:relative; height:25.4781rem; overflow:hidden;}
.tel,.txt,.sub,.ensure{ position:absolute;}
.tel{ width:10.61rem; height:1.1529rem; top:16.717rem; left:2.69rem; border-radius:8px; line-height:1.1529rem; text-indent:1em; font-size:0.5124rem; border-top:1px solid #a4a1a2; border-left:1px solid #a4a1a2; border-right:0; border-bottom:0;}
.txt{ width:7.15225rem; height:1.1529rem; left:2.69rem; border-radius:10px; line-height:1.1529rem; text-indent:1em; font-size:0.5124rem; top:18.04075rem; border-top:1px solid #a4a1a2; border-left:1px solid #a4a1a2; border-right:0; border-bottom:0;}
.sub{color:#c81428; width:3.33rem; height:1.1529rem; background:url(../commons/img/userActivity/thr/01.png) no-repeat; top:18.04075rem; left:9.9918rem; background-size:100%; border:0; font-size: 0.4rem;}
.ensure{ width:10.61rem; height:1.21695rem; left:2.69rem; border:0; background:url(../commons/img/userActivity/thr/02.png) no-repeat; background-size:100%; top:19.31275rem;}


/*20元抵用卷已领取 去逛逛*/
.get{ position:absolute; top:15.90575rem; left:5.9353rem; width:4.0992rem; height:1.4518rem; background:url(../commons/img/userActivity/thr/03.png) no-repeat; background-size:100%; border:0;}
.ming{ position:absolute; width:3.3733rem; height:0.8133rem; top:21.6916rem; left:6.55445rem;}
a{ background:none;}

/*验证码*/
.bgnone-w{width:100%; height:100%; position:fixed; top:0; left:0;}
.bgnone{position:absolute; width:100%; height:100%; background:#000000; opacity: 0.3;}
.bomb-box{background: #ffffff; width: 90%; margin: 0 auto; z-index: 2; position: absolute; left: 50%; margin-left: -45%; top:35%; text-align: center; border-radius: 15px;}
.bomb-box .t{padding: 0.96rem 0 0.64rem 0; font-size: 0.70rem; border-bottom:1px solid #7d7475; }
.bomb-box .qu{color: #007aff; text-decoration: none;  font-size: 0.70rem; padding: 0.96rem 0 0.64rem 0; display: inline-block;}
.bomb-box .qu:hover{color: #007aff;}


/*获得红包*/
.maskly{ width:100%; height:100%; position:fixed; top:0; left:0;}
.backly{ position:absolute; width:100%; height:100%; background:#000000; opacity: 0.3;}
.bagly{position:absolute; width:15.32593; height:16.4395rem; bottom:0; left:0;}
.bagly1{width:15.32593rem; height:16.4395rem; position:absolute; top:50%; left:50%; margin-top:-8.21975rem; margin-left:-7.662965rem;}
.personly{ position:absolute; width:11.67845rem; height:2.62605rem; bottom:0rem; left:1.708rem;}

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
<input type="hidden" value="${in }" id="origin" >
<div class="bg" id="basePage">
	<img src="${staticFile_s }/commons/img/userActivity/thr/bg.jpg">
	<input type="tel" class="tel" placeholder="输入手机号领取20元现金券" id="mobile">
    <input type="text" placeholder="输入验证码" class="txt" id="verificationCode"><input type="submit" value="获取验证码" class="sub" onclick="getCode()" id="reGet">
    <input type="submit" value="" class="ensure" onclick="getRedPacket()" id="coupons">
</div>

<div class="bg"  id="redPacketInfoNone" style="display: none;">
	<img src="${staticFile_s }/commons/img/userActivity/thr/bg1.jpg">
    <input type="submit" value="" class="get" onclick="toUrl()">
    <!-- <a href="http://m.zhongjumall.com" class="ming"></a> -->
</div>

<div class="bgnone-w" id="verificationCodeError" style="display: none;">
	<div class="bgnone"></div>
	<div class="bomb-box">
		  <p class="t">验证码错误，请重新输入</p>
		  <a href="#" class="qu" onclick="confirm()">确定</a> 
	</div>
</div>


<div class="maskly" id="redPacketInfo20" style="display: none;">
	<div class="backly"></div>
    <div class="bagly1">
    	<img src="${staticFile_s }/commons/img/userActivity/thr/h2.png">
        <a href="http://m.zhongjumall.com" class="personly"></a>
    </div>
</div>

<!-- 已领光 -->
<div class="maskly" id="redPacketInfo0" style="display: none;">
	<div class="backly"></div>
    <div class="bagly1">
    	<img src="${staticFile_s }/commons/img/userActivity/thr/h3.png">
        <a href="http://m.zhongjumall.com" class="personly"></a>
    </div>
</div>

</body>
</html>