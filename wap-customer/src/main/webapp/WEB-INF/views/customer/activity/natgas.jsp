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
	<script src="${staticFile_s}/commons/js/zepto.min.js" type="text/javascript"></script>
<style>
*{ margin:0; padding:0; list-style:none;}
body{font-family:'Microsoft Yahei';  background: #42000b;}
img{ width:100%;}
html{ font-size:20px;}
.clearfix:after{content:" "; display:block; clear:both;}
.clearfix {zoom:1;}
::-webkit-input-placeholder {color:#e9bbba; text-align: center;}
	　 　:-moz-placeholder {color:#e9bbba; text-align: center;}
	　　 ::-moz-placeholder {color:#e9bbba; text-align: center;}
	　　 :-ms-input-placeholder {color:#e9bbba; text-align: center;}

.cent{width:100%; background-size:100%; height:28.4rem; position:relative; overflow: hidden;}
.sbt1{ position:absolute; top:23rem; left:2.1rem; width:11.7rem; height:1.9rem; line-height:1.9rem;color: #e9bbba; border:none;  background-size:100%; font-family: 微软雅黑; background: #c11b15; text-align: center; border:1px solid #e3b3b7; font-size: 0.8rem; border-radius: 0;}
.sbt2{ position:absolute; top:25.5rem; left:2.1rem; width:11.7rem; height:1.9rem; line-height:1.9rem;color: #fd383d; background-size:100%; font-family: 微软雅黑; background: #ffffff; text-align: center; border:1px solid #ffffff; text-decoration: none; font-size: 0.8rem;}
.sbt2:hover{color: #fd383d; }

.main{ position:fixed; top:0px; left:0px; z-index:222; opacity:0.8;background:#000; height:100%; width:100%; } 
.main img{ margin-top:30px;}
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
<input type="hidden" id="code" name="code" value="${code }">
<div class="cent">
	<img src="${staticFile_s }/commons/img/userActivity/natgas/bg01.jpg">
	
	<input type="text" placeholder="输入手机号" class="sbt1" id="mobile">
   	<a href="#" class="sbt2"  onclick="submitNatgas()">下载APP</a>
	
	<!-- <a href="https://itunes.apple.com/us/app/xin-shi-jie/id1000578209?l=zh&ls=1&mt=8" class="sbt2 ios">下载APP</a>
	<a href="http://ccigmall-appversion-test.oss-cn-hangzhou.aliyuncs.com/com.ccigmall.b2c.androidofficial18.apk" class="sbt2 android">下载APP</a>  -->
</div>
<div class="main" style="display:none;">
    	<img src="${staticFile_s }/commons/img/userActivity/natgas/fg-bg.png">
</div>
<script>
var isIos = 1;
$(function(){
	var isIOS = navigator.userAgent.match('iPad')||navigator.userAgent.match('iPhone')||navigator.userAgent.match('iPod');
	 var isAndroid = navigator.userAgent.match('Android');
	if(isIOS){
		isIos = 0;
	}else if(isAndroid){
		isIos = 1;
	}
	
	$('.main').on('click', function() {
		$(this).hide();
	});
	
});
	function is_weixn(){   
		$('.main').show();
	    var ua = navigator.userAgent.toLowerCase();  
		if(ua.match(/MicroMessenger/i)=="micromessenger") {  
			$('.main').show();
		} else {  
			$('.main').hide();  
		} 
	} 
	function submitNatgas() {
		var mobile = $("#mobile").val();
		var code = $("#code").val();
		if(!beforeSub(mobile)){
			return ;
		}
		if(isEmpty(code)) {
			return;
		}
		
		location.href = $("#path").val() + "/customerActivity/natgasDownLoad?mobile=" + mobile + "&code=" + code + "&isIos=" + isIos;
		
	}
	
	/**
	 *  提交之前检验
	 * @param mobile
	 * @param code
	 */
	 function beforeSub(mobile,code){
		if ( isEmpty(mobile) ) {
			showErrorMsg("请输入您的手机号");
			return false;
		}
		//目前只验证手机号
		var res = verifyPhoneNumber(mobile);
		if(res != "ok"){
			showErrorMsg(res);
			return false;
		}else {
			return true;
		}
	}
	
	 function showErrorMsg(str) {
			$.dialog({
		        content : str,
		        title : '众聚猫提示',
		        time: 1000,
			});
			return;
		}
</script>
</body>
</html>





