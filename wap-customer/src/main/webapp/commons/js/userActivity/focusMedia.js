function giveRedPacket() {
	location.href = "http://m.zhongjumall.com";
}


function getRedPacketFocusMedia() {
	var mobile = $("#mobile").val();
	var captcha = $("#captcha").val();
	var inType = $("#inType").val();
	if(!beforeSub(mobile)){
		return ;
	}
	if(isEmpty(captcha)) {
		showErrorMsg("验证码不能为空");
		return;
	}
	$("#coupons").attr("disabled", "disabled");
	var _dataType = "text";
	var _type = "POST";
	var _url = $("#path").val()+"/customerActivity/readPacketByLaGou";
	var _async = true;
	var _data = "mobile="+mobile + "&captcha=" + captcha + "&in=" + inType;
	$.ajax({
		dataType : _dataType,
		type : _type,
		url : _url,
		data : _data,
		async : _async,
		success : function(data) {
			switch(data) {
			case '502':
				showErrorMsg("验证码错误");
				$("#captchaImage").click();
				$("#coupons").removeAttr("disabled");
				break;
			case'20':
				$("#redPacketAmount").hide();
				$("#redpacketTrue20").show();
				break;
			case'100':
				$("#redPacketAmount").hide();
				$("#redpacketTrue100").show();
				break;
			case'0':
				$("#redPacketAmount").hide();
				$("#zero").show();
				break;
			case'-2':
				if("20" == inType) {
					$("#redPacketAmount").hide();
					$("#redpacketTrue20").show();
				} else {
					$("#redPacketAmount").hide();
					$("#redpacketTrue100").show();
				}
				break;
			case'504':
				showErrorMsg("领取失败");
				$("#coupons").removeAttr("disabled");
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


function getRedPacketFocusMediaReg() {
	var mobile = $("#mobile").val();
	var inType = $("#inType").val();
	if(!beforeSub(mobile)){
		return ;
	}
	
	$("#coupons").attr("disabled", "disabled");
	var _dataType = "text";
	var _type = "POST";
	var _url = $("#path").val()+"/customerActivity/readPacketByFocusMedia";
	var _async = true;
	var _data = "mobile="+mobile + "&in=" + inType;
	$.ajax({
		dataType : _dataType,
		type : _type,
		url : _url,
		data : _data,
		async : _async,
		success : function(data) {
			switch(data) {
			case'20':
				$("#redPacketInit").hide();
				$("#redPacketInfo20").show();
				break;
			case'-2':
				$("#redPacketInit").hide();
				$("#redPacketInfoTrue").show();
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
 

 