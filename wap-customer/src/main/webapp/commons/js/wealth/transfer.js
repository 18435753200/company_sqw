$(function(){
	$("#sub").live("click",doTrade);
	$("#next").live("click",function(){
		var account = $("#account").val();
		if(isEmpty(account)){
			showMsg("请输入账户ID");
			return false;
		}
		var realName = $("#realName").val();
		if(isEmpty(account)){
			showMsg("请输入真实姓名");
			return false;
		}
		if(realName.length > 5){
			showMsg("输入的真实姓名过长");
			return false;
		}
		var amount = $("#amount").val();
		if(isEmpty(account)){
			showMsg("请输入M券");
			return false;
		}
		var data = "account="+account +"&realName="+realName+"&amount="+amount+"&type="+1;
		validate(data,1);
	});
	
	$("#captchaStep").live("click",function(){
		var captcha= $("#captcha");
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
		var data = "captcha="+captcha.val()+"&type="+2;
		validate(data,2);
	});
});

function validate(data,type){
	var _dataType = "text";
	var _type = "POST";
	var _url = $("#path").val()+"/wealth/validate_n";
	var _async = true;
	$.ajax({
		dataType : _dataType,
		type : _type,
		url : _url,
		data:data,
		async : _async,
		dataType: 'json',
		success : function(res) {
			console.log(res)
			console.log(type)
			if (res.retCode == "1") {
				if(type == 1){
					var isCaptcha = res.isCaptcha;
					$("#step01").hide();
					$("#isCaptcha").val(isCaptcha);
					if(isCaptcha && isCaptcha == 1){
						$("#step02").show();
						$("#currStep").val("step02");
						$(".headly h3").text("手机验证");
//						getCode();
					}else{
						$("#step03").show();
						$("#currStep").val("step02");
						$(".goods-price").text($("#amount").val());
						$(".headly h3").text("支付密码");
					}
				}else{
					$("#step02").hide();
					$("#step03").show();
					$(".goods-price").text($("#amount").val());
					$("#currStep").val("step03");
					$(".headly h3").text("支付密码");
				}
			}else{
				showMsg(res.retInfo);
				if(res.errorCode && res.errorCode == "0"){
					setInterval(function(){
						window.location = $("#path").val() + "/trade/toSetting?uri="+$("#path").val() + "/wealth/transferIndex_new";
					}, 2000);
				}
			}
		},
		error : function() {
			showMsg("网络连接超时，请您稍后重试");
		}
	});
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

function doTrade(){
	var captcha= $("#captcha").val();
	var account = $("#account").val();
	var pwd= $("#pwd").val();
	var account = $("#account").val();
	var remark = $("#remark").val();
	if(isEmpty(account)){
		showMsg("请输入账户ID");
		return false;
	}
	
	var realName = $("#realName").val();
	if(isEmpty(account)){
		showMsg("请输入真实姓名");
		return false;
	}
	if(realName.length > 5){
		showMsg("输入的真实姓名过长");
		return false;
	}
	var amount = $("#amount").val();
	if(isEmpty(account)){
		showMsg("请输入M券");
		return false;
	}
	if(account <= 0){
		showMsg("M券要大于0");
		return false;
	}
	if(isEmpty(pwd)){
		showMsg("支付密码不能为空");
		return false;
	}
	if(pwd.length != 7){
		showMsg("支付密码必须为7位");
		return false;
	}
	pwd = hex_md5(pwd);
	var data = "account="+account +"&realName="+realName+"&amount="+amount+"&captcha="+captcha+"&pwd="+pwd+"&remark="+remark;
	var url = $("#path").val()+"/wealth/transfer_n";
	$.ajax({
		type: 'post',
		url:url,
		data:data,
		async:false,
		dataType: 'json',
		success: function(res){
			if (res.retCode == "1") {
				showMsg(res.retInfo);
				setInterval(function(){
					window.location = $("#path").val() + "/customer/toMy";
				}, 2000);
			}else{
				showMsg(res.retInfo);
				if(res.errorCode && res.errorCode == "2"){
					setInterval(function(){
						window.location = $("#path").val() + "/trade/toSetting?uri="+$("#path").val() + "/wealth/transferIndex_new";
					}, 2000);
				}
				if(res.errorCode && res.errorCode == "3"){
					setInterval(function(){
						window.location = $("#path").val() + "/trade/toRetrieve?uri="+$("#path").val() + "/wealth/transferIndex_new";
					}, 2000);
				}
				$("#pwd").val("");
				var lis = $('.am-password-handy-security li');
				lis.find('i').css({"visibility": "hidden"});
			}
		},error:function(result){
			showMsg("系统繁忙，请稍候重试！");
		}
	});
}

function back(){
	var currStep = $("#currStep").val();
	var isCaptcha = $("#isCaptcha").val();
	if(currStep == "step01"){
		window.history.back()
	}else{
		var title = "";
		if(currStep == "step02"){
			$("#step02").hide();
			$("#step01").show();
			$("#currStep").val("step01");
			title = "M券转账";
		}else if(currStep == "step03"){
			$("#step03").hide();
			if(isCaptcha && isCaptcha == 1){
				$("#step02").show();
				$("#currStep").val("step02");
				title = "手机验证";
			}else{
				$("#step01").show();
				$("#currStep").val("step01");
				title = "M券转账";
			}
		}
		$(".headly h3").text(title);
	}
}