//验证码发送定时
var time;

$(function() {
	$("#codeId2").css("display", "none");

});


//获取短信验证码:之前
function getCodePasBefore(){
	//图形验证码
	var captcha=$("#captcha").val();
	//手机
	var mobile = $("#mobile").val();
	if(isEmpty(captcha)){
		showErrorMsg("图形验证码不能为空");
		return ;
	}
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
	}
	
	//清空验证码
    $("#captcha").val("");
	
	//验证图形验证码是否正确
	$.ajax({
	   dataType:"text",
	   type: "post",
	   url: $("#path").val()+"/customer/validFindPwdCode",
	   data: {
		  "mobile":mobile,
		  "captcha":captcha
	   },
	   async:true,
	   success: function(result){
		   
		   if(isEmpty(result)){
			   showErrorMsg("请求失败,请稍后再试");
			   //切换验证码
			   $("#changeImage").click();
		   }else{
			  var resultJson =  eval( "(" + result + ")" );
			   if('true' == resultJson['isOk']){
				   //设置发送短信权限的 token
				   //setCookie:默认保存1天
				   setCookie('piccode_find_pwd_token',resultJson['token']);
				   
				   //切换验证码
				   $("#changeImage").click();
				   //获取短信
				   getCodePas();
			   }else{
				   //切换验证码
				   $("#changeImage").click();
				   //
				   showErrorMsg(resultJson['msg']);
			   }
		   }
	   },
	   error:function(){
			showErrorMsg("系统繁忙，请稍后重试");
	   }
	});
	  
}



/**
 * 获取验证码
 */
function getCodePas() {
	var token = getCookie('piccode_find_pwd_token');
	if(isEmpty(token)){
		//如果不存在cookie值,则直接跳转到上个页面
		window.location.href = $("#path").val()+"/customer/toGetpass";
		return false;
	}
	
	var username = $("#mobile").val();
	if (!checkUsername(username)) {
		return;
	}

	var _dataType = "text";
	var _type = "POST";
	var _url = $("#path").val() + "/customer/toGetCode";
	var _async = true;
	//var _data = "username=" + username;
	$.ajax({
		dataType : _dataType,
		type : _type,
		url : _url,
		data : {
			"username":username,
			"token":token  //提交短信权限的token,否则无法发送验证码
		},
		async : _async,
		success : function(res) {
			if (res == "success") {
				//
				/*$("#form_toInputPassCode").find(" input[name='username']").val(username);
				$("#form_toInputPassCode").submit();
				*/
				//需要携带token
				location.href = $("#path").val() + "/customer/toInputPassCode?token="+token+"&username=" + username;
			} else if (res == "userNameNull") {
				showErrorMsg("用户名不能为空");
			} else if (res == "userNotExist") {
				showErrorMsg("您的手机号还没有注册，请先注册");

			} else if (res == "mobileIsNull") {
				showErrorMsg("用户手机号不存在");
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

/**
 * 获取验证码
 */
function getCode() {

	var username = $("#mobile").val();
	if (!checkUsername(username)) {
		return;
	}

	var _dataType = "text";
	var _type = "POST";
	var _url = $("#path").val() + "/customer/toGetCode";
	var _async = true;
	var _data = "username=" + username;
	$.ajax({
		dataType : _dataType,
		type : _type,
		url : _url,
		data : _data,
		async : _async,
		success : function(res) {
			if (res == "success") {
				time = 60;
				$("#codeId2").css("display", "block");
				$("#codeId2").html(time + "秒后重新发送");
				$("#codeId").css("display", "none");
				window.setTimeout(callbackCode, 1000); // 1秒
				$("#J-tips").show();
			} else if (res == "userNameNull") {
				showErrorMsg("用户名不能为空");
			} else if (res == "userNotExist") {
				showErrorMsg("您的手机号还没有注册，请先注册");

			} else if (res == "mobileIsNull") {
				showErrorMsg("用户手机号不存在");
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


/**
 * 校验用户名和验证码
 */
function subValid() {
	var username = $("#mobile").val();
	var msgCode = $("#J_forgotPassCode").val();
	var mobile = $("#J_forgotPassMobile").val();
	var from = $("#J_forgotPassFrom").val();
	if (isEmpty(username)) {
		showErrorMsg("您还没有输入用户名");
		return;
	}
	if (username.length != 11) {
		showErrorMsg("你输入的用户名有误");
		return;
	}
	// !isEmail(username) &&
	if (from != null && from != "") {
		if (verifyPhoneNumber(mobile) != "ok") {
			showErrorMsg("手机号输入不正确");
			return;
		}
	}
	if (isEmpty(msgCode)) {
		showErrorMsg("您还没有输入验证码");
		return;
	}
	if (isNaN(msgCode) || msgCode.length > 10) {
		showErrorMsg("验证码错误或者已经失效");
		return;
	}
	$("#formSubmit").submit();
}

/**
 * 提交新的密码
 */
function subNewPass() {

	var username = $("#username").val();
	//var opass = $("#J_Opassword").val();
	var pass = $("#J_password").val();
	var confirm = $("#J_Tpassword").val();
	var name = null;
	var from = $("#from").val();
	if(from=="cus"){
		name="newOldPass";
	}else{
		name="newPass";
	}
	if (isEmpty(pass)) {
		showErrorMsg("请输入密码");
		return;
	}
	if (isEmpty(confirm)) {
		showErrorMsg("请输入确认密码");
		return;
	}
	if (pass != confirm) {
		showErrorMsg("两次密码输入不一致");
		return;
	}

	if (!(/((?=.*\d)(?=.*\D)|(?=.*[a-zA-Z])(?=.*[^a-zA-Z]))^.{6,20}$/)
			.test(pass)||pass==username) {
		okBT("请输入6-20位的数字和字母组合构成的密码");
		return;
	}
	var _dataType = "text";
	var _type = "POST";
	var _url = $("#path").val() + "/customer/"+name;
	var _async = true;
	var _data = $("#formSubmit").serialize();
	$.ajax({
		dataType : _dataType,
		type : _type,
		url : _url,
		data : _data,
		async : _async,
		success : function(data) {
			if (data.indexOf("modifyOk") != -1) {
				var url = data.split("=")[1];
				okBT('密码修改成功', url);
				return;
			} else if (data == "modifyError") {
				showErrorMsg("密码修改失败");
			} else {
				showErrorMsg(data);
			}
		},
		error : function() {
			showErrorMsg("网络连接超时，请您稍后重试");
		}
	});
}

/**
 * // 时间回调
 */
function callbackCode() {
	$("#codeId2").css("display", "block");
	time = time - 1;
	$("#codeId2").html(time + "秒后重新发送");
	if (time == 0) {
		$("#codeId2").css("display", "none");
		$("#codeId").html("重新获取");
		$("#codeId").css("display", "block");
		return;
	}
	setTimeout(callbackCode, 1000);
}

/**
 * 发送验证码前检查用户名
 * 
 * @param username
 * @returns {Boolean}
 */
function checkUsername(username) {
	if (isEmpty(username)) {
		showErrorMsg("请输入您的手机号");
		return false;
	}
	return true;
	/*
	 * //目前只验证手机号 var res=verifyPhoneNumber(username); if(res!="ok"){
	 * showErrorMsg(res); return false; }else {
	 * $(".error_tips").addClass("hide"); return true; }
	 */

}

// 显示错误信息
function showErrorMsg(str) {
	// $(".error_tips").removeClass("hide");
	// $(".error_tips").html(str);
	$.dialog({
		content : str,
		title : '众聚猫提示',
		time : 2000,
	});
	return;
}