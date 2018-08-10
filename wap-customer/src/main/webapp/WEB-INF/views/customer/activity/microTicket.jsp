<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>众聚猫</title>
<meta name="viewport" content="width=320, user-scalable=no, initial-scale=1">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<%@include file="/WEB-INF/views/commons/user.jsp" %>
<link href="${staticFile_s}/commons/css/zepto.alert.css" rel="stylesheet" type="text/css">
<script src="${staticFile_s}/commons/js/jqueryAlert/zepto.alert.js"  type="text/javascript" ></script>
<script src="${staticFile_s}/commons/js/userActivity/microTicket.js"  type="text/javascript" ></script>

<style>
*{ margin:0; padding:0; list-style:none;}
body{font-family:'Microsoft Yahei';  background: #b30d1b; }
img{ width:100%;}
html{ font-size:20px;}
.clearfix:after{content:" "; display:block; clear:both;}
.clearfix {zoom:1;}

.cent{width:100%; background-size:100%; height:28.4rem; position:relative; overflow: hidden;}
.tick{position: absolute; top:5.8rem; left:12.5%; margin: 0 auto; width: 75.5%; }
.tick .tick_c{width: 100%; height: 1.7rem; margin-bottom: 0.36rem; float: left;position: relative;}
.tick .tick_c .txt1{width: 89.5%; height: 1.3rem; line-height: 1.3rem; border:1px solid #b5b5b6; font-size: 0.59rem; font-family: 微软雅黑; padding: 0.2rem 0.59rem; border-radius: 0; color: #868281;}
.tick .tick_c .txt{width: 47%; height: 1.3rem; line-height: 1.3rem; border:1px solid #b5b5b6; font-size: 0.59rem; font-family: 微软雅黑; padding: 0.2rem 0.59rem; border-radius: 0; color: #868281;}
.tick .tick_c .txt2{width: 47%; height: 1.3rem; line-height: 1.3rem; border:1px solid #b5b5b6; float: left; font-size: 0.59rem; font-family: 微软雅黑; border-radius: 0; padding: 0.2rem 0.59rem; color: #868281;}
.tick .tick_c .yzm{ font-family: 微软雅黑; background: #ecc14d; font-size: 0.59rem; height: 1.7rem; line-height: 1.7rem; display: inline-block; border:1px solid #ecc14d; color: #8c1b24;
 text-decoration: none; text-align: center; width: 40%; float: right;}
.tick .tick_c .yzm:hover{color: #8c1b24;}

.tick .tick_c .receive{border:none; background: #ecc14d; font-size: 0.68rem; height: 1.7rem; line-height: 1.7rem; font-family: 微软雅黑; display: inline-block; color: #8c1b24; text-decoration: none; text-align: center; width: 100%;}
.tick .tick_c .receive:hover{color: #8c1b24;}

.tick .tick_c .yzm2{width:2.8rem;height: 1.5rem;  position: absolute;    left: 60.5%;    top: 0.15rem;}
a.ch{    font-size: 0.4rem;    color: #fff;    position: absolute;    right: 0px;    top: 0.5rem;}

/**/
.bgnone-w{width:100%; height:100%; position:fixed; top:0; left:0;}
.bgnone{position:absolute; width:100%; height:100%; background:#000000; opacity: 0.3;}
.bomb-box{background: #ffffff; width: 90%; margin: 0 auto; z-index: 2; position: absolute; left: 50%; margin-left: -45%; top:35%; text-align: center; border-radius: 15px;}
.bomb-box .t{padding: 0.96rem 0 0.64rem 0; font-size: 0.70rem; border-bottom:1px solid #7d7475; }
.bomb-box .qu{color: #007aff; text-decoration: none;  font-size: 0.70rem; padding: 0.96rem 0 0.64rem 0; display: inline-block;}
.bomb-box .qu:hover{color: #007aff;}

/**/
.maskly{ width:100%; height:100%; position:fixed; top:0; left:0;}
.backly{ position:absolute; width:100%; height:100%; background:#000000; opacity: 0.3;}
.bagly{position:absolute; width:16rem; height:16rem; bottom:0; left:0;}
.bagly1{width:12rem; height:12rem; position:absolute; bottom:8rem; left:50%; margin-left:-6rem;}
.personly{ position:absolute; width:8.5rem; height:1.643rem; bottom:0.3rem; left:1.6rem;}

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
<div class="cent">
	<img src="${staticFile_s }/commons/img/userActivity/microTicket/bg01.jpg">

	<div class="tick">
		<div class="tick_c">
			<input type="text" placeholder="请输入手机号" class="txt1" id="mobile" autocomplete="off">
		</div>
		<div class="tick_c">
			<input type="text" id="captcha" name="captcha" maxLength="7" vld="{required:true}"  class="txt" placeholder="请输入图形验证码"/>
			<img src="${path }/customer/getImageRegist?date="+new Date() id="captchaImage"  alt="换一张"  class="yzm2"/>
			<a id="changeImage" title="换一张" class="ch">换一张</a>
		</div>
		<div class="tick_c">
			<input type="text" placeholder="请输入短信验证码" class="txt2" id="verificationCode">
			<!-- <a href="javascript:0" class="yzm">获取验证码</a> -->
			<input name="" type="button" value="获取验证码" class="yzm" onclick="getCode()" id="reGet">
		</div>
		<div class="tick_c">
			<!-- <a href="#" class="receive">领取红包</a> -->
			<input name="" type="button" value="领取红包" class="receive" onclick="getRedPacket()" id="coupons">
		</div>
	</div>	

</div>


<div class="bgnone-w" style="display: none;" id="verificationCodeError">
	<div class="bgnone"></div>
	<div class="bomb-box">
		  <p class="t">验证码错误，请重新输入</p>
		  <a href="#" class="qu" onclick="confirm()">确定</a> 
	</div>
</div>

 <div class="maskly" id="redPacketInfo500" style="display: none;">
	<div class="backly"></div>
    <div class="bagly1">
    	<img src="${staticFile_s }/commons/img/userActivity/microTicket/h1.png" >
        <a href="http://m.zhongjumall.com/view/activity/get/248" class="personly"></a>
    </div>
</div>
<div class="maskly" id="redPacketInfo20" style="display: none;">
	<div class="backly"></div>
    <div class="bagly1">
    	<img src="${staticFile_s }/commons/img/userActivity/microTicket/h2.png" >
        <a href="http://m.zhongjumall.com/view/activity/get/248" class="personly"></a>
    </div>
</div>
<div class="maskly" id="redPacketInfoNone" style="display: none;">
	<div class="backly"></div>
    <div class="bagly1">
    	<img src="${staticFile_s }/commons/img/userActivity/microTicket/h3.png" >
        <a href="http://m.zhongjumall.com/view/activity/get/248" class="personly"></a>
    </div>
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




