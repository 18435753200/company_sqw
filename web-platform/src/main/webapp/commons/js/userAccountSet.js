$(function() {
	$("#codeId2").css("display", "none");
});

function checkIsMobile(){
	var mobileVal =  $("#userMobileId").val().trim();
	var reg = "^0?(13|15|17|18|14)[0-9]{9}$";
	if(!mobileVal.match(reg)){
		alert("手机号不能为空或规则不正确，请重新输入!");
		$("#userMobileId").val("");
		return false;
	}
};

function checkHqq(num){
	var newHqqNum = $("#hqqModifyId").val().trim();
	var reg = '^[1-9]\\d*$';
	if(!newHqqNum.match(reg)){
		alert('不能为空或小数，请重新输入!');
		$("#hqqModifyId").val('');
		return false;
	}
	if((parseFloat(newHqqNum) + parseFloat(num)) > 5000000){
		alert('超出红旗券当日最大限额500万，请重新输入!');
		$("#hqqModifyId").val('');
		return false;
	}
};

function checkFenHong(num){
	var newFenHongVal = $("#fenHongId").val().trim();
	var reg = '^[1-9]\\d*$';
	if(!newFenHongVal.match(reg)){
		alert('不能为空或小数，请重新输入!');
		$("#fenHongId").val('');
		return false;
	}
	if((parseFloat(newFenHongVal) + parseFloat(num))>60000000){
		alert('此用户分红额度已达上限!');
		$("#fenHongId").val('');
		return false;
	}
}


/**
 * 获取验证码
 */
function getCode(){
	var mobile = $("#userMobileId").val().trim();
	var captcha=$("#captcha").val().trim();
	if(mobile == null || mobile == ''){
		alert('手机号不能为空,请认真填写!');
		return false;
	}
	if(captcha == null || captcha == ''){
		alert("图形验证码不能为空");
		return false;
	}
	var _dataType = "text";
	var _type = "POST";
	var _url = "../platform/toRegGetCode";
	var _async = true;
	var _data = "mobile="+mobile + "&captcha=" + captcha;
	$.ajax({
		dataType : _dataType,
		type : _type,
		url : _url,
		data : _data,
		async : _async,
		success : function(res) {
			if (res == 1) {
				alert("发送成功!")
				$("#mobileCode").attr("disabled",true);
			}else if (res == 2) {
				alert("请不要重复发送");
			} else if(res == 0) {
				$("#captchaImage").click();
				alert("图形验证码错误");
			} else if(res == -1){
				alert("发送失败，请稍后重试");
			}
		},
		error : function() {
			alert("网络连接超时，请您稍后重试");
		}
	});
}

function modifyAccount(userId,status){
	if(status == 1){
		alert('该账户是冻结状态,不能做调整操作!');
		return false;
	}
	var hqqModifyVal = $("#hqqModifyId").val().trim();
	var fenHongVal= $("#fenHongId").val().trim();
	if((hqqModifyVal == '' && fenHongVal == '') || (hqqModifyVal == 0 && fenHongVal == 0)){
		alert('红旗调整数和分红调整数至少要修改一处!');
		return false;
	}
	var mobileVal = $("#userMobileId").val().trim();
	var captchaVal = $("#captcha").val().trim();
	var mobileCodeVal = $("#mobileCodeValue").val().trim();
	if(mobileVal == '' || captchaVal == '' || mobileCodeVal == ''){
		alert('手机号、验证码、手机验证码是必填项！');
		return false;
	}
	var _dataType = "text";
	var _type = "POST";
	var _url = "../platform/countHqqAndFenHong";
	var _async = true;
	var _data = "userId="+userId + "&hqqVal=" + hqqModifyVal + "&fenhongVal="+fenHongVal+"&mobile="+mobileVal+"&captcha="+captchaVal+"&mobileCode="+mobileCodeVal;
	$.ajax({
		dataType : _dataType,
		type : _type,
		url : _url,
		data : _data,
		async : _async,
		success : function(res) {
			if (res == 1) {
				alert("调整成功!")
				location.href='../platform/toUserAccountSet?userId='+userId;
			} else if(res == 0) {
				alert("网络连接超时，请您稍后重试");
				location.href='../platform/toUserAccountSet?userId='+userId;
			} else if(res == 2){
				alert("图形验证码不正确,请重新输入!");
				$("#captchaImage").click();
				return false;
			} else if(res == 3){
				alert("发送验证码的手机和传过来的手机不一致,请重新输入!");
				return false;
			} else if(res == 4){
				alert("手机短信验证码不正确,请重新输入!");
				return false;
			}
		},
		error : function() {
			alert("网络连接超时，请您稍后重试");
			location.href='../platform/toUserAccountSet?userId='+userId;
		}
	});
	
}

