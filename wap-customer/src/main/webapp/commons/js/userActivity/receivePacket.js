var path ;
var Gtype='';
var checkResult='';
var doing = false;
$(document).ready(function(){
	
	path = $("#path").val();
	Gtype= $("#wtype").val();
	
	init();
	
	// receive red packet
	$("#sub").live("click",doSubmit);
	$("#go1").live("click",doGoway);
	$("#go2").live("click",doGoway1);
	$(".maskly").hide();
//	$(".close1").live("click",closeBox);
});

function doSubmit(){
//	if(doing){
//		return;
//	}
	doing = true;
	var mobile = $("#phNum").val();
	var imgcodes=$("#imgcode").val();
	// 检验输入参数
	if(!checkInput(mobile)){
		doing = false;
		return;
	}

	if(isEmpty(imgcodes)){
		$.dialog({
            content : '验证码不能为空',
            title : '众聚猫提示',
            time: 2000,
		});
		doing = false;
		return;
	}
	// 正常情况
	var url = path+"/customerActivity/redPacketActivity";
	var data = "mobile="+mobile+"&imgCode="+imgcodes;
	var dataType = "text";
	var type = "post";
	var srcsplit=$(".bagly1 img").attr('data');
	$.ajax({
		type:type,
		url:url,
		data:data,
		async:false,
		dataType:dataType,
		success:function(result){
			var showMessage = "";
			checkResult=result;
			console.log(result);
			switch (result) {
			case '500':// 验证码输入错误
				showMessage = "验证码输入错误！";
				break;
			case '502':// 抢红包时间还没到
				showMessage = "10点开抢，敬请期待...";
				break;
			case '1':// 替换对应的红包图片
				$(".bagly1 img").attr("src",srcsplit+"/commons/img/1.png");
				//$(".bagly1 a").attr("href","http://m.zhongjumall.com");
				$(".maskly").show();
				break;
			case '2':// 替换对应的红包图片
				$(".bagly1 img").attr("src",srcsplit+"/commons/img/2.png");
				//$(".bagly1 a").attr("href","http://m.zhongjumall.com");
				$(".maskly").show();
				break;
			case '5':// 替换对应的红包图片
				$(".bagly1 img").attr("src",srcsplit+"/commons/img/5.png");
				//$(".bagly1 a").attr("href","http://m.zhongjumall.com");
				$(".maskly").show();
				break;
			case '10':// 替换对应的红包图片
				$(".bagly1 img").attr("src",srcsplit+"/commons/img/10.png");
				//$(".bagly1 a").attr("href","http://m.zhongjumall.com");
				$(".maskly").show();
				break;
			case '50':// 替换对应的红包图片
				$(".bagly1 img").attr("src",srcsplit+"/commons/img/50.png");
				//$(".bagly1 a").attr("href","http://m.zhongjumall.com");
				$(".maskly").show();
				break;
			case '100':// 替换对应的红包图片
				$(".bagly1 img").attr("src",srcsplit+"/commons/img/100.png");
				//$(".bagly1 a").attr("href","http://m.zhongjumall.com");
				$(".maskly").show();
				break;
			case '0':// 替换对应的红包图片
				$(".bagly1 img").attr("src",srcsplit+"/commons/img/y2.png");
				//$(".bagly1 a").attr("href","http://m.zhongjumall.com");
				$(".maskly").show();
				break;
			case '-2':// 替换对应的红包图片
				$(".bagly1 img").attr("src",srcsplit+"/commons/img/y1.png");
				//$(".bagly1 a").attr("href",path+"/customer/toMy");
				$(".maskly").show();
				break;
			default:
				break;
			}
			if(showMessage != ""){
					$.dialog({
			            content : showMessage,
			            title : '众聚猫提示',
			            time: 2000,
					});
				return;
			}
			doing = false;
		},error:function(){
			$.dialog({
	            content : "系统繁忙，请稍候重试",
	            title : '众聚猫提示',
	            time: 2000,
			});
			return;
			doing = false;
		}
	});
}

function checkInput(mobile){
	
	// 检验手机号是否为空
	if(isEmpty(mobile)){
		$.dialog({
            content : '请填写手机号',
            title : '众聚猫提示',
            time: 2000,
		});
		
		return false;
	}
	
	// 检验手机号有效性
	if(!check_mobile(mobile)){
		$.dialog({
            content : '手机号格式不正确',
            title : '众聚猫提示',
            time: 2000,
		});
		
		return false;
	}
	
	return true;
}

function init(){

	window.onload=window.onresize=function(){
		document.documentElement.style.fontSize=document.documentElement.clientWidth/32+'px';	
	};
	document.addEventListener('DOMContentLoaded',function(){
		document.documentElement.style.fontSize=document.documentElement.clientWidth/32+'px';
	});

}

function closeBox(){
	$(".maskly").hide();
}
var browser = {
		versions: function() {
			var u = navigator.userAgent, app = navigator.appVersion;
			return {//移动终端浏览器版本信息
				trident: u.indexOf('Trident') > -1, //IE内核
				presto: u.indexOf('Presto') > -1, //opera内核
				webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
				gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
				mobile: !!u.match(/AppleWebKit.*Mobile.*/) || !!u.match(/AppleWebKit/), //是否为移动终端
							ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
							android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
							iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //是否为iPhone或者QQHD浏览器
							iPad: u.indexOf('iPad') > -1, //是否iPad
							webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部
							};
						}(),
				language: (navigator.browserLanguage || navigator.language).toLowerCase()
	};
function doGoway(){
	var wtype = $("#wtype").val();
/*	console.log(wtype);
	console.log(111);*/

	//是否ios 到个人中心 //如果是1 走app 不是1 走wap
	if (browser.versions.ios || browser.versions.iPhone || browser.versions.iPad) {
		if(wtype=='1'){
			window.location.href="http://api.zhongjumall.com?type=goCenter";
		}else{
			window.location.href = path+"/customer/toMy";
		}
	}else{
		if(wtype=='1'){
			window.ccigmall_b2c.goToMy();
		}else{
			window.location.href = path+"/customer/toMy";
		}
	}

}
function doGoway1(){
	var wtype = $("#wtype").val();
	//是否ios 到首页 //如果是1 走app 不是1 走wap
	if (browser.versions.ios || browser.versions.iPhone || browser.versions.iPad) {
		if(wtype=='1'){
			window.location.href="http://api.zhongjumall.com?type=gohome";
		}else{
			window.location.href=path+"/";
		}
	}else {
		if(wtype=='1'){
			window.ccigmall_b2c.goToHome();
		}else{
			window.location.href=path+"/";
		}
	}
}

