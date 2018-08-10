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
<link href="${staticFile_s}/commons/css/userActivity/forceBeauty.css" rel="stylesheet" type="text/css">
<%@include file="/WEB-INF/views/commons/user.jsp" %>
<script>
window.onload=window.onresize=function(){
	document.documentElement.style.fontSize=document.documentElement.clientWidth/16+'px';	
};
document.addEventListener('DOMContentLoaded',function(){
	document.documentElement.style.fontSize=document.documentElement.clientWidth/16+'px';
	
	var aA=document.querySelectorAll('.limei a');
	var oMask=document.querySelector('.mask');
	var isIOS = navigator.userAgent.match('iPad')||navigator.userAgent.match('iPhone')||navigator.userAgent.match('iPod');
	var isAndroid = navigator.userAgent.match('Android');

	function isWeiXin(){
		var ua = window.navigator.userAgent.toLowerCase();
		if(ua.match(/MicroMessenger/i) == 'micromessenger'){
			return true;
		}else{
			return false;
		}
	}
	
	for(var i=0;i<aA.length;i++){
		aA[i].onclick=function(){
			if(isWeiXin()){
				oMask.style.display='block';
			}else if(isIOS){
				window.location.href='https://itunes.apple.com/us/app/xin-shi-jie/id1000578209?l=zh&ls=1&mt=8';
			}else if(isAndroid){
				window.location.href='http://oss.aliyuncs.com/ccigmall-appversion/com.ccigmall.b2c.androidoffcial2.apk';
			}		
		};
	}
	
});
</script>
</head>

<body>
<%-- hidden域 --%>
<input type="hidden" id="path" value="<%=path %>"/>
<div class="limei">
	<img src="${staticFile_s }/commons/img/userActivity/forceBeauty/bj.jpg">
	<input type="tel" class="iph" value="${mobile }">
    <a class="btn_1" href="https://itunes.apple.com/us/app/xin-shi-jie/id1000578209?l=zh&ls=1&mt=8"></a>
    <a class="btn_2" href="http://oss.aliyuncs.com/ccigmall-appversion/com.ccigmall.b2c.androidoffcial2.apk"></a>
</div>
<div class="mask" style="display:none;">
	<img src="${staticFile_s }/commons/img/userActivity/forceBeauty/fg-bg.png">
</div>
</body>
</html>



