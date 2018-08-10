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
<script src="${staticFile_s }/commons/js/zepto.min.js" type="text/javascript"></script>
<style>
*{ margin:0; padding:0; list-style:none;}
body{ background:#bb1a2a; font-family:'Microsoft Yahei'; position:relative;}
img{ width:100%;}
html{ font-size:20px;}
.clearfix:after{content:" "; display:block; clear:both;}
.clearfix {zoom:1;}

.cent{width:100%; background:url(../commons/img/userActivity/flowRate/bg.jpg) no-repeat; background-size:100%; height:28.4809rem; position:relative; overflow:hidden;}
.iph{ position:absolute; top:19.62rem; left:2.562rem; width:10.8885rem; height:1.6653rem; border:1px solid #a59899; border-radius:0.4rem; color:#b5b5b5; text-indent:0.4rem; font-size:0.6rem; line-height:1.6653rem;}
.sbt_1{ position:absolute; top:22.1613rem; left:3.138rem; width:2.8609rem; height:2.8609rem; background:url(../commons/img/userActivity/flowRate/ios.png) no-repeat; background-size:100%; border:0;}
.btn_1{ position:absolute; top:22.1613rem; left:10.034rem; width:2.8609rem; height:2.8609rem; background:url(../commons/img/userActivity/flowRate/and.png) no-repeat; background-size:100%; border:0;}
.mask{ position:fixed; top:0px; left:0px; z-index:222; opacity:0.8;background:#000; height:100%; width:100%; display:none;}
</style>
<script>
;(function(win,doc){
	function change(){
		document.documentElement.style.fontSize=document.documentElement.clientWidth/16+'px';
	}
	
	win.addEventListener('resize',change,false);
	win.addEventListener('DOMContentLoaded',change,false);
})(window,document);
window.onload=function(){
	var oMain=document.querySelector('.mask');
	var aA=document.querySelectorAll('.cent a');
	
	for(var i=0;i<aA.length;i++){
		aA[i].onclick=function(){
			if(isWeiXin()){
				oMain.style.display='block';
			}
		};
	}
};
function isWeiXin(){
	var ua = window.navigator.userAgent.toLowerCase();
	if(ua.match(/MicroMessenger/i) == 'micromessenger'){
		return true;
	}else{
		return false;
	}
} 
</script>
</head>

<body>
<div class="cent">
	<input type="tel" class="iph"  readonly="readonly" value="${mobile }">
    <a class="sbt_1" href="https://itunes.apple.com/us/app/xin-shi-jie/id1000578209?l=zh&ls=1&mt=8"></a>
    <a class="btn_1" href="http://ccigmall-appversion-test.oss-cn-hangzhou.aliyuncs.com/com.ccigmall.b2c.androidofficial21.apk"></a>
    
  <!--   <a href="https://itunes.apple.com/us/app/xin-shi-jie/id1000578209?l=zh&ls=1&mt=8" class="sbt2 ios"></a>
	<a href="http://ccigmall-appversion-test.oss-cn-hangzhou.aliyuncs.com/com.ccigmall.b2c.androidofficial21.apk" class="sbt2 android"></a> -->
							 
</div>
<div class="mask" style="display:none;">
	<img src="${staticFile_s }/commons/img/userActivity/flowRate/fg-bg.png">
</div>
</body>
</html>


