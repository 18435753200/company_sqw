function goBack(){var a=window.location.href;if(/#top/.test(a)){window.history.go(-2);window.location.load(window.location.href)}else{window.history.back();window.location.load(window.location.href)}}

function loadBestPay(){
	$(".head").remove();
	$(".headly").remove();
    var iHead = document.getElementsByTagName('HEAD').item(0);   
    var iScript= document.createElement("script");   
    iScript.type = "text/javascript";   
    iScript.src=$("#basePath").val()+"/commons/js/bestpay/bestpay.api.js";   
    iHead.appendChild(iScript);  
}
//获取Cookie
function getCookie(c_name){
	//判断当前浏览器是否有cookie
	if (document.cookie.length>0){
	 //获取当前需要的cookie的key对应出现的位置
	  var c_start = document.cookie.indexOf(c_name + "=");
	  if (c_start!=-1){ 
		//定位长度  
		c_start = c_start + c_name.length+1;
		//切到第一次出现当前key对应的分号位置
		var c_end = document.cookie.indexOf(";",c_start);
		if (c_end==-1) 
			c_end=document.cookie.length;
		return unescape(document.cookie.substring(c_start,c_end));
		} 
	  }
	return "";
}
function setCookie(c_name, c_value, c_expired_date)  {
	if (null == c_name || "" == c_name) {
		return;
	}
	
	if (null == c_expired_date ||  c_expired_date == undefined) {
		c_expired_date = new Date();
		c_expired_date.setDate(c_expired_date.getDate()+1);
	}
	
	document.cookie= c_name + "=" + escape(c_value)+";expires=" + c_expired_date.toGMTString() + ";path=/";
}
function isBestPay(title){
	//获取浏览器中的COOKIE
	var paramStr = getCookie("c_r_");
	if(undefined == paramStr || null  == paramStr || '' == paramStr){
		
	}else{	
		//loadBestPay();
		$(".head").remove();
		$(".headly").remove();
		App.setTitle(title);
//		App.overrideBackPressed(true);
//		App.setKeyEventListener(function(event){
//			if('backpress' == event){
//				if(undefined == backUrl || null  == backUrl || '' == backUrl){
//					history.go(-1);
//				}else if('exit' == backUrl){
//					App.exitApp;
//				}else{
//					window.location.href = backUrl;
//				}
//				
//			}
//		});	
	}
}

function setTitle(title){
	isBestPay(title);
}


