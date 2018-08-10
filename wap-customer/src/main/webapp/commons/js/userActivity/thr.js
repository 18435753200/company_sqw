
function getRedPacket() {
	var mobile = $("#mobile").val();
	var verificationCode = $("#verificationCode").val();
//	var captcha = $("#captcha").val();
	var origin = $("#origin").val();
	if(!beforeSub(mobile)){
		return ;
	}
/*	if(isEmpty(captcha)) {
		showErrorMsg("图形验证码不能为空");
		return;
	}*/
	if(isEmpty(verificationCode)) {
		showErrorMsg("手机验证码不能为空");
		return;
	}
	$("#coupons").attr("disabled", "disabled");
	var _dataType = "text";
	var _type = "POST";
	var _url = $("#path").val()+"/customerActivity/redPacketThr";
	var _async = true;
	var _data = "mobile="+mobile + "&verificationCode=" + verificationCode + "&origin=" + origin;
	$.ajax({
		dataType : _dataType,
		type : _type,
		url : _url,
		data : _data,
		async : _async,
		success : function(data) {
			switch(data) {
			case'501':
				showErrorMsg("用户名或手机验证码不正确");
				$("#coupons").removeAttr("disabled");
				break;
			case'500':
				$("#verificationCodeError").show();
				$("#coupons").removeAttr("disabled");
				break;
			case'20':
				$("#redPacketInfo20").show();
				break;
			case'503':
				$("#basePage").hide();
				$("#redPacketInfoNone").show();
				break;
			case'0':
				$("#redPacketInfo0").show();
				break;
			case'504':
				showErrorMsg("领取失败，请稍后重试");
				break;
			case'480':
				$("#redPacketInfo20").show();
				break;
			default:
				location.href = data;
				break;
			}
		},
		error : function() {
			showErrorMsg("网络连接超时，请您稍后重试");
		}
	});
	
}

function confirm() {
	$("#verificationCodeError").hide();
}

function toUrl() {
	location.href = "http://m.zhongjumall.com"
}

/**
 * 获取验证码
 */
function getCode(){
	var mobile = $("#mobile").val();
	var origin=$("#origin").val();
	//var captcha = $("#captcha").val();
	if(!beforeSub(mobile)){
		return ;
	}
/*	if(isEmpty(captcha)) {
		showErrorMsg("图形验证码不能为空");
		return;
	}*/
	var _dataType = "text";
	var _type = "POST";
	var _url = $("#path").val()+"/customer/getCode";
	var _async = true;
	var _data = "mobile="+mobile + "&origin=" + origin;
	$.ajax({
		dataType : _dataType,
		type : _type,
		url : _url,
		data : _data,
		async : _async,
		success : function(res) {
			if (res == "success") {
				time = 60;
				$("#reGet").attr("disabled",true);
				window.setTimeout(callbackCode, 1000); 
			} else if (res == "mobileExist") {
			
			} else if(res == "captchaError") {
				showErrorMsg("图形验证码错误");
				$("#captchaImage").click();
			} else if (res == "repeatSend") {
				showErrorMsg("请不要重复发送");
			} else {
				showErrorMsg("发送失败，请稍后重试");
			}
		},
		error : function() {
			showErrorMsg("网络连接超时，请您稍后重试");
		}
	});

}

function callbackCode() {
	time = time - 1;
	$("#reGet").val("重新发送(" + time +"秒)" );
	if (time == 0) {
		$("#reGet").attr("disabled", null);
		$("#reGet").val("重新发送");
		return;
	}
	setTimeout(callbackCode, 1000);
}

function showErrorMsg(str) {
	
	$.dialog({
        content : str,
        title : '众聚猫提示',
        time: 1000,
	});
	return;
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
		$(".error_tips").addClass("hide");
		return true;
	}
}
 
 /**
  * //之前校验
  * 
  * @param mobile
  * @param password
  * @returns {Boolean}
  */
 function checkUserNameAndPass(mobile, password) {
 	
 	if (isEmpty(mobile)) {
 		showErrorMsg("用户名不能为空");
 		return false;
 	}
 	if (isEmpty(password)) {
 		showErrorMsg("请输入密码");
 		return false;
 	}
 	return true;

 }
 function test(){
	 var oDiva=document.querySelector('.rDialog-footer .rDialog-ok');
	 var oDivb=document.querySelector('.rDialog-footer .rDialog-cancel');
	 
	 oDivb.style.background='#FBA733';
	 oDivb.style.color='#fff';
	 oDiva.innerHTML='【返回活动】';
	 oDivb.innerHTML='【登录结算】';
 }

 