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
   <link href="${staticFile_s}/commons/css/userActivity/friendDownload.css" rel="stylesheet" type="text/css">

<script>
;(function(win,doc){
	function change(){
		document.documentElement.style.fontSize=document.documentElement.clientWidth/16+'px';
	}
	
	win.addEventListener('resize',change,false);
	win.addEventListener('DOMContentLoaded',change,false);
})(window,document);
window.onload=function(){
	var oMask=document.querySelector('.mask')
	var oIosdown=document.querySelector('.sbt_1');
	var oAnddown=document.querySelector('.btn_1');
	
	function isWeiXin(){
		var ua = window.navigator.userAgent.toLowerCase();
		if(ua.match(/MicroMessenger/i) == 'micromessenger'){
			return true;
		}else{
			return false;
		}
	}
	
	oIosdown.onclick=oAnddown.onclick=function(){
		if(isWeiXin()){
			oMask.style.display='block';
		}
	};
}
</script>
</head>

<body>
<div class="cent">
	<input name="mobile" type="tel" placeholder="${mobile}" class="iph">
    <a class="sbt_1" href="https://itunes.apple.com/us/app/xin-shi-jie/id1000578209?l=zh&ls=1&mt=8"></a>
    <a class="btn_1" href="http://r.m.baidu.com/6bh6oj3"></a>
</div>
<div class="mask" style="display:none;">
 <img src="img/fg-bg.png">
</div>
</body>
</html>























