
var time;

$(function() {
	$("#codeId2").css("display", "none");
});


/**
 * 获取验证码
 */
function getCode(){
	alert("aaaa");
	var mobile = $("#J_forgotPassAccount").val();
	alert("mobile" + mobile);
	if(!beforeSub(mobile)){
		alert("xxx");
		return ;
	}
	
	
	var _dataType = "text";
	var _type = "POST";
	var _url = $("#path").val()+"/customer/toRegGetCode";
	var _async = true;
	var _data = "mobile="+mobile;
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
			} else if (res == "mobileExist") {
				showErrorMsg("手机号已经注册");
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
 * 校验用户名验证码
 */
function subReg(){

	var mobile = $("#J_forgotPassAccount").val();
	var msgCode = $("#J_forgotPassCode").val();
	var origin = $("#origin").val();
	var str = "";
	if(!beforeSub(mobile, msgCode)){
		return ;
	}
	if(isEmpty(msgCode)){
		showErrorMsg("验证码不能为空");
		return ;
	}
	if(!isempty(origin)){
		str = "<input type='hidden' name='origin' value='"+origin+"'/>";
	}else{
		origin="";
		str = "<input type='hidden' name='origin' value='"+origin+"'/>";
	}
	$.ajax({
	   dataType:"text",
	   type: "post",
	   url: $("#path").val()+"/customer/validRegCode",
	   data: $("#formSubmit").serialize(),
	   async:true,
	   success: function(result){
		    switch(result){
		    case '200':
		    	$("form[name='setPasswordForm']").remove();
		    	var toSetPasswordTag = "<form method='post' action='"+$("#path").val()+"/customer/toSetPassword' name='setPasswordForm'>";
		    	toSetPasswordTag += "<input type='hidden' name='mobile' value='"+mobile+"'/>";
		    	toSetPasswordTag += str;
		    	toSetPasswordTag += "</form>";
		    	$("body").append(toSetPasswordTag);
		    	$("form[name='setPasswordForm']").submit();
		    	break;
		    case '505':
		    	showErrorMsg("用户已经存在");
		    	break;
		    case '501':
		    	showErrorMsg("用户名和验证码不匹配");
		    	break;
		    case '500':
		    	showErrorMsg("验证码错误");
		    	break;
		    }
	   },
	   error:function(){
			showErrorMsg("系统繁忙，请稍后重试");
	   }
	});
}

/**
 * 提交密码
 */
function subPass(){

	var mobile=$("#mobile").val();
	var pass=$("#J_password").val();
	var origin = $("#origin").val();
	//var confirm=$("#J_Tpassword").val(); 密码由6-20位字符组成，包含至少两种以上的字母，数字或字符
	
	if(isEmpty(pass)){
		showErrorMsg("请您设置密码"); 
		return ;	
	}
	var le = pass.length;
	if(le>20||le<6){
		showErrorMsg("请输入6-20位的数字和字母组合构成的密码"); 
		return ;
	}
	if(!(/((?=.*\d)(?=.*\D)|(?=.*[a-zA-Z])(?=.*[^a-zA-Z]))^.{6,20}$/).test(pass)){
		showErrorMsg("密码不能包含下划线，空格以及特殊符号"); 
		return ;	
	}
	//alert("开始加密"+pass+":"+mobile);
	var  enResult = strEnc(pass, mobile);
	//alert(enResult);
	//$("#J_password").val(enResult);
	//alert("加密完成"+pass);   $("#formSubmit").serialize();
	var _dataType="text";
	var _type="POST";
	var  _url=$("#path").val()+"/customer/saveCustomer";
	var _async =true;
	var _data="mobile="+mobile+"&origin="+origin+"&pass="+enResult;
	$.ajax({
		   dataType:_dataType,
		   type: _type,
		   url: _url,
		   data: _data,
		   async:_async,
		   success: function(data){
			    if(data == "success"){
			    	
			    	showErrorMsg("注册成功，正在登录...");
			    	
			    	setInterval(function(){
			    		
			    		$.ajax({
			    			   dataType:"text",
			    			   type: "post",
			    			   url: $("#path").val()+"/customer/login",
			    			   data: "userName="+mobile+"&password="+enResult,
			    			   async:false,
			    			   success: function(data){
			    				   switch(data){
			    				   // 登录成功
			    				   case '200':
			    					   location.href = $("#path").val()+"/customer/toMy";
			    					   break;
			    				   
			    				   // 用户名或密码不正确
			    				   case '501':
			    					   showError("用户名或密码不正确");
			    					   break;
			    				   // 系统错误
			    				   case '500':
			    					   showError("系统繁忙，请稍候重试");
			    					   break;
			    				   // 登录成功
			    				   default:
			    					   location.href=data;
			    					   break;
			    				   }
			    			   },
			    				error:function(){
			    					showErrorMsg("系统繁忙");
			    					location.href=$("#path").val()+"/customer/toLogin";
			    			   }
			    			});
			    	}, 1000);
			    	
			    }else{
					showErrorMsg("注册失败，请稍后重试");
					return ;
				}	
			   },
			error:function(){
				showErrorMsg("系统繁忙，请稍后重试");
		   }
		});   

}


/**
 *  提交之前检验
 * @param mobile
 * @param code
 */
 function beforeSub(mobile,code){
	 alert("ccc");
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

function showErrorMsg(str) {
	//$(".error_tips").removeClass("hide");
	//$(".error_tips").html(str);
	$.dialog({
        content : str,
        title : '众聚猫提示',
        time: 1000,
	});
	return;
}
 