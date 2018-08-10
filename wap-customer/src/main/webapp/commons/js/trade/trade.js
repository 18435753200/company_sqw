$(function(){
	$(".bg").live("click",submitTrade);
	$(".sub").live("click",submitRetrieveTrade);
	$("#next").live("click",function(){
		var imageCaptcha = $("#imageCaptcha").val();
		if(isEmpty(imageCaptcha)){
			showMsg("请输入验证码");
			return false;
		}
		if(imageCaptcha.length != 5 ){
			showMsg("验证码必须为5位");
			return false;
		}
		var _dataType = "text";
		var _type = "get";
		var _url = $("#path").val()+"/trade/validImageCaptcha";
		var _async = true;
		$.ajax({
			dataType : _dataType,
			type : _type,
			url : _url,
			data:"imageCaptcha="+imageCaptcha,
			async : _async,
			success : function(res) {
				if (res == "200") {
					$("#step01").hide();
					$("#step02").show();
					getCode();
				} else if (res == "501") {
					showMsg("请输入验证码");
					return false;
				} else if(res == "502"){
					showMsg("验证码不正确");
					return false;
				} else if(res == "503"){
					showMsg("系统繁忙，请稍等重试");
					return false;
				}
			},
			error : function() {
				showMsg("网络连接超时，请您稍后重试");
			}
		});
	});
});

function submitRetrieveTrade(){
	var captcha= $("#captcha");
	var pwd= $("#pwd");
	if(isEmpty(captcha.val())){
		showMsg("验证码不能为空");
		captcha.focus();
		return false;
	}
	if(captcha.val().length != 6){
		showMsg("验证码必须为6位");
		captcha.focus();
		return false;
	}
	
	if(!isNumber(captcha.val())){
		showMsg("验证码只能为数字");
		captcha.focus();
		return false;
	}
	
	if(isEmpty(pwd.val())){
		showMsg("支付密码不能为空");
		pwd.focus();
		return false;
	}
	
	if(pwd.val().length != 7 ){
		showMsg("密码必须为7位");
		pwd.focus();
		return false;
	}

	if(isSimpleNumber(pwd.val())){
		showMsg("您设置的密码过于简单，请您组合搭配7位数字密码，以防被盗。");
		pwd.focus();
		return false;
	}
	
	if(!isNumber(pwd.val())){
		showMsg("支付密码只能为数字");
		pwd.focus();
		return false;
	}
	doSubmitTrade();
}

/**
 * 获取验证码
 */
function getCode(){
	var _dataType = "text";
	var _type = "get";
	var _url = $("#path").val()+"/trade/sendTradeCaptcha";
	var _async = true;
	$.ajax({
		dataType : _dataType,
		type : _type,
		url : _url,
		async : _async,
		success : function(res) {
			if (res == "success") {
				time = 60;
				$("#mobileCode").val("重新发送("+ time +"秒)" );
				$("#mobileCode").attr("disabled",true);
				window.setTimeout(callbackCode, 1000); // 1秒
			} else if (res == "501") {
				showMsg("请先登入");
				setInterval(function(){
					window.location = $("#path").val() + "/customer/toLogin";
				}, 2000);
			} else{
				showMsg(res);
			} 
		},
		error : function() {
			showMsg("网络连接超时，请您稍后重试");
		}
	});
}

function callbackCode() {
	time = time - 1;
	$("#mobileCode").val("重新发送(" + time +"秒)" );
	if (time == 0) {
		$("#mobileCode").attr("disabled", null);
		$("#mobileCode").val("重新发送");
		return;
	}
	setTimeout(callbackCode, 1000);
}

function submitTrade(){
	if(!checkSubmitTrade()){
		return;
	}
	doSubmitTrade();
}

function checkSubmitTrade(){
	var captcha= $("#captcha");
	var pwd= $("#pwd");
	var confirmPwd= $("#confirmPwd");
	if(isEmpty(captcha.val())){
		showMsg("验证码不能为空");
		captcha.focus();
		return false;
	}
	
	if(captcha.val().length != 6 ){
		showMsg("验证码必须为6位");
		captcha.focus();
		return false;
	}
	
	if(!isNumber(captcha.val())){
		showMsg("验证码只能为数字");
		captcha.focus();
		return false;
	}
	
	if(isEmpty(pwd.val())){
		showMsg("支付密码不能为空");
		pwd.focus();
		return false;
	}
	
	if(pwd.val().length != 7 ){
		showMsg("密码必须为7位");
		pwd.focus();
		return false;
	}
	
	if(!isNumber(pwd.val())){
		showMsg("支付密码只能为数字");
		pwd.focus();
		return false;
	}
	
	if(isSimpleNumber(pwd.val())){
		showMsg("密码不能为连续或者重复的数字");
		pwd.focus();
		return false;
	}
	
	if(isEmpty(confirmPwd.val())){
		showMsg("确认支付密码不能为空");
		confirmPwd.focus();
		return false;
	}
	if(pwd.val() != confirmPwd.val()){
		showMsg("支付密码不一致");
		confirmPwd.focus();
		return false;
	}
	return true;
}

function doSubmitTrade(){
	var captcha= $("#captcha").val();
	var pwd= $("#pwd").val();
	console.log(pwd);
	pwd = hex_md5(pwd);
	console.log(pwd);
	var url = $("#path").val()+"/trade/setTrade";
	
	$.ajax({
		type: 'post',
		url:url,
		data:"pwd="+pwd +"&captcha="+captcha,
		async:false,
		dataType: 'json',
		success: function(result){
			if(result.retCode == "501"){
				showMsg(result.retInfo);
				setInterval(function(){
					window.location = $("#path").val() + "/customer/toLogin";
				}, 2000);
			}else if(result.retCode == "1"){
				showMsg("支付密码设置成功！");
				setInterval(function(){
					var returnUrl = $("#returnUrl").val();
					if(returnUrl){
						var orderId = $("#orderId").val();
						var act = $("#act").val()
						returnUrl = $("#path").val()+ returnUrl+"?orderId="+orderId+"&act="+act;
						location.href = returnUrl;
					}else{
						var uri = $("#uri").val();
						if(uri){
							location.href = uri;
						}else{
							location.href = $("#path").val()+"/customer/toMy";
						}
					}
				}, 2000);
			}else{
				showMsg(result.retInfo);
			}
		},error:function(result){
			showMsg("系统繁忙，请稍候重试！");
		}
	});
}

function isSimpleNumber(num){
	var numbers = ["1111111","2222222","3333333","4444444","5555555","6666666",
	   			"7777777","8888888","9999999","0000000","0123456","1234567","2345678","3456789",
	   			"4567890","9876543","8745632","7654321","6543210"];
	for(var n in numbers){
		if(num == numbers[n]){
			return true;
		}
	}
	return false;
}
