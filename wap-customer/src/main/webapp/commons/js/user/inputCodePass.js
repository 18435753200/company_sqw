$(document).ready(function() {
	time = 60;
	$("#reGetPass").attr("disabled",true);
	window.setTimeout(callbackCode, 1000); 
});

function callbackCode() {
	time = time - 1;
	$("#reGetPass").val("重新发送(" + time +"秒)" );
	if (time == 0) {
		$("#reGetPass").attr("disabled", null);
		$("#reGetPass").val("重新发送");
		return;
	}
	setTimeout(callbackCode, 1000);
}

/**
 * 提交新的密码
 */
function alertPassword() {
	var username = $("#phoneNumberPass").val();
	var pass = $("#J_password").val();
	var msgCode = $("#msgCode").val();
	if (isEmpty(msgCode)) {
		showErrorMsg("请输入验证码！");
		return;
	}
	if (isEmpty(pass)) {
		showErrorMsg("请输入密码！");
		return;
	}
	var le = pass.length;
	if(le>20||le<6){
		showErrorMsg("请输入6-20位的数字和字母组合构成的密码"); 
		return ;
	}
	//var oldRule = "/((?=.*\d)(?=.*\D)|(?=.*[a-zA-Z])(?=.*[^a-zA-Z]))^.{6,20}$/";
	if (!(/^([a-z]+(?=[0-9])|[0-9]+(?=[a-z]))[a-z0-9]+$/ig).test(pass)||pass==username) {
		okBT("请输入6-20位的数字和字母组合构成的密码");
		return;
	}
	var validData = "username=" + username + "&msgCode=" + msgCode;
	
	
	$.ajax({
		   dataType:"text",
		   type: "post",
		   url: $("#path").val()+"/customer/validCode",
		   data: validData,
		   async:true,
		   success: function(result){
			    switch(result){
			    case 'success':
			    		var _dataType = "text";
			    		var _type = "POST";
			    		var _url = $("#path").val() + "/customer/newPass";
			    		var _async = true;
			    		var _data = "username=" + username + "&pass=" + pass + "&passConfirm=" + pass;
			    		$.ajax({
			    			dataType : _dataType,
			    			type : _type,
			    			url : _url,
			    			data : _data,
			    			async : _async,
			    			success : function(data) {
			    				if (data.indexOf("modifyOk") != -1) {
			    					//删除发送验证码的权限token
			    					//setCookie:默认保存1天
			    					setCookie('piccode_find_pwd_token',null);
			    					
			    					//-----------------------------------------------
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
			    	break;
			    case '505':
			    	showErrorMsg("验证码已过期，请重新获取");
			    	break;
			    case '501':
			    	showErrorMsg("验证码输入不正确");
			    	break;
			    case '500':
			    	showErrorMsg("验证码输入不正确");
			    	break;
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
	
	
	var username = $("#phoneNumberPass").val();
	if (!beforeSub(username)) {
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
				time = 60;
				$("#reGetPass").val("重新发送(60秒)" );
				$("#reGetPass").attr("disabled",true);
				window.setTimeout(callbackCode, 1000); // 1秒
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
 		alert("ddd");
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
   * 验证手机号
   * 
   * @param mobile
   *            手机号
   * @returns {String} 结果
   */
  function verifyPhoneNumber(mobile) {
  	if (mobile.length == 0) {
  		return "请输入您的手机号";
  	}
  	if (mobile.length != 11) {
  		return "手机号格式不正确";
  	}

  	//var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
  	var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
  	if (!myreg.test(mobile)) {
  		return "手机号格式不正确";
  	}
  	return "ok";
  }

