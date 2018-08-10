<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
	<head>
        <meta charset="utf-8">
        <title>众聚猫</title>
        <meta name="keywords" content="众聚猫 0元购">
        <meta name="description" content="众聚猫 0元购">
		<meta name="viewport" content="minimal-ui=yes,width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
        <meta name="apple-mobile-web-app-status-bar-style" content=" black ">
        <meta name="apple-mobile-web-app-title" content="众聚猫">
        <meta name="format-detection" content="telephone=no">
	</head>
	<script src="js/zepto.min.js" type="text/javascript"></script>
	<style>
	  
		*{padding:0px; margin:0px auto; font-size:12px;font-family:Arial,'Microsoft Yahei','Hiragino Sans GB',sans-serif; text-decoration:none; list-style:none; }
		input,button,textarea,select,optgroup,option {font-family: inherit;font-size: inherit;font-style: inherit;font-weight: inherit;font-size: 100%;	border: 0;resize: none;                                                      outline: none;-webkit-appearance: none;white-space: pre-wrap; border:none;}
		*:focus {outline: none;}
		img{width:100%}
		html{-webkit-text-size-adjust:none; }/*解决chrome网页字体小于12的问题*/
		body{background:#ffe8bc;min-width:320px;}
		.act-home{ padding:0px 35px; margin-top:20px;}
		.act-home h3{ font-size:17px; color:#333; text-align:center;margin-bottom:20px;}
		.act-home p{ font-size:13px; line-height:1.6; margin-top:5px;}
		.app-bg{padding:30px 30px; overflow:hidden; position:relative}
		.app-bg a{ display:block; height:35px; line-height:35px; float:left; text-align:center; color:#fff; font-size:16px; background:#ed6827; border-radius:15px; width:45%}
	    .app-bg a.az-but{ float:right}
		
	</style>
    <body>
	  
	   <div class="activity-m">
			<h2><img src="${staticFile_s}/commons/img/userActivity/zero/banner.jpg"></h2>
			<div class="act-home">
				<h3>温馨提示</h3>
				<p>抱歉：0元商品已抢光，感谢您的参与；</p>
				<p>点击【返回活动】继续抢购其他商品；</p>
				<p>点击【返回首页】查看更多优惠活动...</p>
				
			</div>			
		</div>
		<div class="app-bg">
			<%-- <a href="${staticFile_s }/cart/directBuy?skuId=${skuId}&number=${number}" >返回活动</a> --%>
			<a href="http://m.zhongjumall.com/act/0yuanqianggou.html" >返回活动</a>
			<a href="http://m.zhongjumall.com" class="az-but ">返回首页</a>
		</div>
<script>
		$(function(){
			$('.iswenxin').on('click', function() {
			  is_weixn() 
			});
			
			$('.gool').on('click', function() {
				 $('.act-list').toggle();
				 $(this).parent().toggleClass('mtop');
			});
			
			$('.del').on('click', function() {
				$('.app-bg').hide();
				$('.activity-m').removeClass('top60');
			});
			
			$('.main').on('click', function() {
				$(this).hide();
			});
			
		})
		function is_weixn(){   
			var ua = navigator.userAgent.toLowerCase();  
			if(ua.match(/MicroMessenger/i)=="micromessenger") {  
				$('.main').show();
			} else {  
				$('.main').hide();  
			}  
		}  
</script>		
	</body>
</html>




